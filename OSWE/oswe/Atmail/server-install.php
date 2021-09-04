<?php
/**
 * Server install script
 * Note : This script needs to run as a root user from the console
 * 
 * This script can be run in an unattended manner, but is not recommended
 * php server-install.php --install --dbhost=127.0.0.1 --dbuser=root --dbtable=atmail --dbpass=test --adminpass=test --domain=yourdomain.com.au
 *
 * @category 	Atmail
 * @package 	Server Installer/Update
 * @subpackage 	Server Installer
 * @copyright 	Copyright (c) 2009-2011 ATMAIL. All rights reserved
 * @license 	See http://atmail.com/license.php for license agreement
 * @author		Atmail (http://atmail.com)
 */

ini_set('memory_limit', "64M");
error_reporting(E_ALL & ~E_NOTICE);
$installationDir = '/usr/local/atmail/webmail';
set_include_path("$installationDir/" . PATH_SEPARATOR . "$installationDir/library/" . PATH_SEPARATOR . get_include_path());
define('APP_ROOT', dirname(__FILE__) . DIRECTORY_SEPARATOR . 'webmail' . DIRECTORY_SEPARATOR);
require_once(APP_ROOT . 'library/Atmail/Utility.php');
require_once(APP_ROOT . 'library/Atmail/Install/Strings.php');
require_once(APP_ROOT . 'library/Atmail/General.php');
require_once(APP_ROOT . 'library/Atmail/Deps/DepCheck.php');
require_once(APP_ROOT . 'library/Atmail/Apache_Utility.php');

// *********************************** SANITY CHECK ENVIRONMENT ******************************** //
// Do not allow access via web
if(isset($_SERVER['GATEWAY_INTERFACE']))
{
	header("Content-Type: text/html");
	print $installerStrings['noWebAccess'];
	exit;
}

// If we don't have the STDOUT, STDIN, STDERR constants defined
// as we should in CLI environment then define them
if(!defined('STDOUT'))
{
	// Define stream constants
	define('STDIN', fopen('php://stdin', 'r'));
	define('STDOUT', fopen('php://stdout', 'w'));
	define('STDERR', fopen('php://stderr', 'w'));

	// Close the streams on script termination
	register_shutdown_function(
		create_function('',
			'fclose(STDIN); fclose(STDOUT); fclose(STDERR); return true;')
		);
}

// Check safe mode is off
if(ini_get('safe_mode') == 1)
{
	exit($installerStrings['safeModeOn']);
}

// Make sure we are running as root
$ps = shell_exec("ps aux | grep 'php .*server-install.php'");
if(preg_match("/^(.+?)\s+.+?php .*?server-install\.php/m", $ps, $m))
{
	if(trim($m[1]) != 'root')
	{
		exit($installerString['notRoot']);
	}
}
else
{
	print $installerString['unableToDetectRoot'];
}

// Make sure we are running required minimum PHP version
if(version_compare(PHP_VERSION, '5.1.0', '<'))
{
	exit('Your PHP version ' . PHP_VERSION . " is too old, please upgrade to PHP 5.1.0 or greater to use Atmail\n\n");
}

$operatingSystemInfo = getOperatingSystemInfo();
if( !$operatingSystemInfo['supported'] )
{
	exit("Operating System detected as: " . $operatingSystemInfo['operatingSystem'] . ".\nThis operating system is not supported.\nPlease upgrade your operating system if it is one of the supported distributions or install Atmail on a supported operating system.\n\n");
}

// Make sure client files are installed to /usr/local/atmail/webmail
if(!is_dir('/usr/local/atmail'))
{
	echo "Please install/extract Atmail to /usr/local/atmail/\nThen run the installer again";
	exit;
}

// ********************************* PROCESS ARGUEMENTS ********************************* //
$args = array();
$argsError = 0;
foreach($_SERVER['argv'] as $arg)
{
	$arr = explode("=", $arg);
	$arr[0] = str_replace('--', '', $arr[0]);
	
	if(empty($arr[1]))
	{
		$arr[1] = "";		
	}
	$args[$arr[0]] = $arr[1];
}

if(isset($args['install']) && (empty($args['dbhost']) || empty($args['dbuser'] ) || empty($args['dbtable'])))
{
	$argsError = "Please specify the --dbhost , --dbuser and --dbtable values";
}

if(isset($args['install']) && (empty($args['adminpass']) || empty($args['domain'] )))
{
	$argsError = "Please specify the --adminpass and --domain values";
}

if(isset($args['help']) || ($argsError))
{
	print $installerStrings['installerUsage'];
	if($argsError)
	{
		echo "ERROR: $argsError\n\n";
	}
	exit;
}

// ********************************* DEFAULT VARIABLES ******************************** //
$apacheAddedOnDate = "\n# ADDED BY Atmail " . date('Y-m-d H:i:s') . "\n";
$apacheRestart = false;
$exim = array();
$exim['smtp_port'] = 25;
$dovecot = array();
$mysqlrestart = 0;
$plugins = array();
$defaults = array();
$defaults['ThreadLimit'] = 6;
$mode = 1;
$apacheConfiguration['defaultCharset'] = 1;
$mbstring = 0;
$openssl = 0;

$pref['sql_table'] = '';
$pref['install_dir'] = '/usr/local/atmail/';
$pref['user_dir'] = '/usr/local/atmail/';
$pref['smtphost'] = '127.0.0.1';
$pref['admin_email'] = '';
$pref['serverDir'] = '/usr/local/atmail/';

$debian = (file_exists('/etc/debian_version') || file_exists('/etc/debian_release')) ? 1 : 0;
$suse = (file_exists('/etc/SuSE-release') || file_exists('/etc/SuSE-version')) ? 1 : 0;
$rpmInstall = isset($_SERVER['argv'][1]) ? $_SERVER['argv'][1] : 0;
$rpmInstall = ($rpmInstall != 'server' && $rpmInstall != 'client') ? 0 : $rpmInstall;
$args['verbose'] = isset($args['verbose']) ? true : false;

// PHP ini settings
ini_set('track_errors', true);
ini_set('html_errors', false);
ini_set('magic_quotes_runtime', false);

define('WWW_USER', 'atmail');
putenv('LANG=C');

$phpVersion = PHP_VERSION;
$phpOS = $operatingSystemInfo['operatingSystem'];

//
// ******************************* START INSTALLATION ***********************************
//
if(!empty($rpmInstall))
{
	system("touch /usr/local/atmail/webmail/.rpminstall");
}

// Create the the new Config.php
if(!file_exists("/usr/local/atmail/webmail/config/dbconfig.ini"))
{
	copy('/usr/local/atmail/webmail/config/dbconfig.ini.default', '/usr/local/atmail/webmail/config/dbconfig.ini');
}
system("chmod 0660 /usr/local/atmail/webmail/config/dbconfig.ini");

$pref['version'] = getVersionCurrentLocalCodebase();
$versionStr = generateVersionString($pref['version']);

// Display the welcome message
$msg = <<<EOF
\033[1;32mWelcome to Atmail {$versionStr} Installation\033[0;39m
----------------------------------

PHP Version: $phpVersion
Operating System: $phpOS

This script will setup Atmail for your system

When requesting your input during the installation the default values are
presented within square brackets -- to choose the default value, hit "Enter"

EOF;

if(!isset($args['install']))
{
	print $msg;
	prompt('[Press Enter to Continue]');
}

// Check the iptables firewall
if(file_exists("/sbin/iptables"))
{	
	$firewall = `/sbin/iptables -nvL | grep dpt`;
	$portError = array();
	
	if(!empty($firewall))
	{
		$ports = array('25' => 'SMTP', '110' => "POP3", '143' => "IMAP", '993' => "IMAP SSL", '995' => "POP3 SSL", '8008' => "Calendar Server", '11211' => "Memcache");
		foreach( array_keys($ports) as $port)
		{
			if(!preg_match("/dpt:$port/", $firewall))
			{
				$portError[] = " * Port $port ({$ports[$port]}) not detected as allowed\n";
			}
		}
		if(isset($portError[0]))
		{
			echo $installerStrings['firewallIsActive'];
			echo implode($portError);
			if(!isset($args['install']))
			{
				prompt('[Press Enter to Continue]');
			}
		}
	}		
}

// *************************************** CHECK DEPENDANCIES ******************************************* //
check_deps($_SERVER['argv']);
if(file_exists('./webmail/library/Atmail/.mysqlrestart'))
{
	unlink('./webmail/library/Atmail/.mysqlrestart');
	$mysqlrestart = 1;
}

// depcheck has already complained to the user, lets just exit
if( $mysqlrestart )	
{
	exit(1);
}

// Check for required/optional PHP extensions
if(!isset($args['install']))
{
	print $installerString['installerPrerequisites'];
}

$failedExt = array();
$text = '';

// Required Extensions
// Check we have MySQL support via PHP
$text .= " * MySQL Library for PHP: ";
if(defined('MYSQL_NUM'))
{
	$text .= "OK\n";
}
else
{
	$text .= "FAILED\n";
	$failedExt[] = 'MySQL';
}

// Check we have MySQL support via PHP
$text .= " * PDO MySQL Library for PHP: ";
if(extension_loaded('pdo_mysql'))
	$text .= "OK\n";
else {
	$text .= "FAILED\n";
	$failedExt[] = 'PDO';
}

// Check for PCRE Library
$text .= " * PCRE (Perl Compatible Regular Expressions) Library: ";
if(extension_loaded('pcre'))
	$text .= "OK\n";
else {
	$text .= "FAILED\n";
	$failedExt[] = 'PCRE';
}

// Check for Session support
$text .= " * PHP Session support: ";
if(extension_loaded('session'))
	$text .= "OK\n";
else {
	$text .= "FAILED\n";
	$failedExt[] = 'Session';
}

// Check for Session support
$text .= " * PHP mbstring support: ";
if(extension_loaded('mbstring'))
	$text .= "OK\n";
else {
	$text .= "FAILED\n";
	$failedExt[] = 'mbstring';
}

// Check for ctype support
$text .= " * PHP ctype support: ";
if(extension_loaded('ctype'))
	$text .= "OK\n";
else {
	$text .= "FAILED\n";
	$failedExt[] = 'ctype';
}

// Check for DOM
$text .= " * DOM Extension for PHP: ";
if( extension_loaded('dom') )
{
	$text .= "OK\n";
}
else 
{
	$text .= "FAILED\n";
	$failedExt[] = 'dom';
}

// Optional Extensions
$text .= "\nOptional PHP Extensions:\n\n";

// Check for OpenSSL
$text .= " * OpenSSL Extension for PHP: ";
if(extension_loaded('openssl') || $openssl) {
	$text .= "OK\n";
	$pref['mail_type_ssl'] = 'allow';
}
else {
	$text .= "MISSING - Optionally install the PHP OpenSSL extension to allow secure IMAPs POP3s connections via Webmail\n";
	$pref['mail_type_ssl'] = 'deny';
}

// Check for ICONV
$text .= " * ICONV Extension for PHP: ";
if(extension_loaded('iconv'))	{
	$text .= "OK\n";
	$pref['iconv'] = '1';
} else {
	$text .= "MISSING - Optionally install the Iconv library to perform charset conversions. Install the PHP ICONV extension to enable this feature!\n";
	$pref['iconv'] = '0';
}

// Check for GD
$text .= " * GD Extension for PHP: ";
if(extension_loaded('gd'))	{
	$text .= "OK\n";
} else {
	$text .= "MISSING - Optionally install the GD library to support image resizing of user photos in the Addressbook\n";
}

// Check for PHP-IMAP
$text .= " * IMAP Extension for PHP: ";
if( extension_loaded('imap') )
	$text .= "OK\n";
else
	$text .= "MISSING - Optionally install the php-imap library to support Microsoft Exchange ActiveSync services to Push email, contacts and calendars to mobile devices.\n";

// Check for PHP-MCRYPT
$text .= " * MCRYPT Extension for PHP: ";
if( extension_loaded('mcrypt') )
	$text .= "OK\n";
else
	$text .= "MISSING - Optionally install the php-mcrypt library to support encryption of session data and external user passwords.\n";

// Check for required php.ini settings
$text .= "\nphp.ini Settings:\n\n";
$editIni = false;
$reqIniChanges = '';
$iniChangeRestart = 0;

// safe mode check
$text .= " * Safe Mode Off: ";
if(ini_get('safe_mode') == '1') {
	if(editIni('safe_mode', 'Off'))
		$text .= "FIXED - Safe Mode now off as required by Atmail\n";
	else
		$text .= "FAILED -  Atmail requires safe mode disabled\n";
}
else
	$text .= "OK\n";

// session auto start check
$text .= " * Session Auto Start Off: ";
if(ini_get('session.auto_start') == '1') {
	if(editIni('session.auto_start', 'Off'))
		$text .= "FIXED - Session auto start now off as required by Atmail\n";
	else
		$text .= "FAILED -  Atmail requires session auto start disabled\n";
}
else
	$text .= "OK\n";

// mysql default port check
$text .= " * Default Port Set: ";
if(ini_get('mysql.default_port') == '') {
	if(editIni('mysql.default_port', '3306'))
	{
		$text .= "FIXED - Default port now set to 3306\n";
		$iniChangeRestart = 1;
	}
	else
	{
		$text .= "FAILED -  This may stop PHP from connecting to your mysql server\n";
		$reqIniChanges .= "mysql.default_port = 3306\n";
	}
}
else
	$text .= "OK\n";

// register globals check
$text .= " * Register Globals Off: ";
if(ini_get('register_globals') == '1') {
	if(editIni('register_globals', 'Off'))
		$text .= "FIXED - Register Globals now off for enhanced security\n";
	else {
		$text .= "FAILED - Leaving register_globals on is a security hazard\n";
		$reqIniChanges .= "register_globals = Off\n";
	}
}
else
	$text .= "OK\n";

// file uploads check
$text .= " * File Uploads Allowed: ";
if(ini_get('file_uploads') != '1') {
	if(editIni('file_uploads', 'On'))
		$text .= "FIXED - File uploads now allowed. Required for email attachments\n";
	else {
		$text .= "FAILED - File Uploads must be enabled for Atmail to add email attachments\n";
		$reqIniChanges .= "file_uploads = On\n";
	}
}
else
	$text .= "OK\n";

// magic_quotes_gpc check
$text .= " * Magic Quotes GPC Off: ";
if( get_magic_quotes_gpc() == '1') 
{
	if( editIni('magic_quotes_gpc', 'Off') )
	{
		$text .= "FIXED - Magic Quotes GPC now disabled\n";
	}
	else 
	{
		$text .= "FAILED - Magic Quotes GPC must be off\n";
		$reqIniChanges .= "magic_quotes_gpc = Off\n";
	}
}
else
{
	$text .= "OK\n";		   
}

// Display errors
$text .= " * Display Errors: ";
if(ini_get('display_errors') == '1') {
	if(editIni('display_errors', 'Off'))
		$text .= "FIXED - Display Errors now off for enhanced security\n";
	else {
		$text .= "FAILED - Disable display_errors in the php.ini for increased security\n";
		$reqIniChanges .= "display_errors = Off\n";
	}
}
else
	$text .= "OK\n";

// check max upload file size
$text .= " * Maximum upload File Size at least 16M: ";
if( returnBytes(ini_get('upload_max_filesize')) < 16*1024*1024 ) 
{
	if(editIni('upload_max_filesize', '16M'))
	{
		$text .= "FIXED - Maximum uploadable file size now 16M\n";
	}
	else 
	{
		$text .= "FAILED - To allow for larger attachments max_upload_filesize should be at least 16M\n";
		$reqIniChanges .= "upload_max_filesize = 16M\n";
	}
}
else
{
	$text .= "OK\n";
}

// check max post size
$text .= " * Maximum Size of POST data at least 16M: ";
if( returnBytes(ini_get('post_max_size')) < 16*1024*1024 ) 
{
	if(editIni('post_max_size', '16M'))
	{
		$text .= "FIXED - Maximum Size of POST data now 16M\n";
	}
	else 
	{
		$text .= "FAILED - To allow for larger POST data size post_max_size should be at least 16M\n";
		$reqIniChanges .= "post_max_size = 16M\n";
	}
}
else
{
	$text .= "OK\n";
}

// check memory_limit value
$text .= " * Maximum amount of memory usage at least 96M: ";
if( returnBytes(ini_get('memory_limit')) < 96*1024*1024 ) 
{
	if(editIni('memory_limit', '96M'))
	{
		$text .= "FIXED - Maximum amount of memory now 96M\n";
	}
	else 
	{
		$text .= "FAILED - To allow for larger amount of memory usage memory_limit should be at least 96M\n";
		$reqIniChanges .= "memory_limit = 96M\n";
	}
}
else
{
	$text .= "OK\n";
}

fwrite(STDOUT, $text);

// If we have required extensions missing then give message and exit
if( count($failedExt) )
{
	fwrite(STDOUT, "Some required PHP extensions are not available. In order to use Atmail the
PHP extensions listed below must be installed for PHP on your platform:\n\n");

	foreach ($failedExt as $ext)
		fwrite(STDOUT, "	* $ext\n");

	fwrite(STDOUT, "\nAlso note any missing optional extensions you may wish to install. Once you have installed the required
extensions you may continue with the installation.\n\n");

	exit(1);
}

// Check for any required php.ini changes
if(!empty($reqIniChanges))
{
	fwrite(STDOUT, "\nInstaller could not alter some php.ini settings. Atmail requires the following php.ini directives to be manually set:\n\n");
	fwrite(STDOUT, $reqIniChanges . "\n");
	fwrite(STDOUT, "Please make these changes and restart Apache before using Atmail.\n\n");
}

// restart installer if php5-mysql default port missing
if($iniChangeRestart == 1)
{
	fwrite(STDOUT, "\nMissing PHP init settings have been set, however you may need to re-run the installation script to initialize PHP correctly\n");
	fwrite(STDOUT, "Please run:\n\nphp server-install.php\nIf your mysql tests fail to connect.\n\n");
}



// So we can track that we installed the software
if(!$fh = @fopen('/usr/local/atmail/webmail/.installed', 'w'))
	exit("Cannot write .installed file in the /usr/local/atmail/ directory! Be sure to change the ownership of the Atmail directory to the user your webserver runs as.\n");
fclose($fh);

// Next, scan for other optional binary dependencies ( apsell, ImageMagik, etc )
// TODO: move findBinary to a common library used for web-installer
$convert = findBinary(array('convert'));

if(is_executable($convert)) {
		$res = `$convert --version`;

		if(preg_match("/ImageMagick/", $res)) {
		$pref['convertPath'] = $convert;
		$plugins['Atmail_FilePreview'] = 1;
		}
}

if(!isset($args['install']))
	prompt('[Press Enter to Continue with installation]');

$pref['install_type'] = 'server';
$pref['allow_Signup'] = 0;

// SQL setup
if(!isset($args['install']))
	print <<<EOF

\033[1;32mMySQL Database Setup \033[0;39m
--------------------

The following few prompts will set up your MySQL Database for Atmail.
You will need to supply an MySQL user that has permission to create databases.

EOF;

$default = (!empty($pref['sql_host'])) ? $pref['sql_host'] : '127.0.0.1';

if($args['dbhost'] == '')
	$pref['sql_host'] = prompt('Enter your MySQL server\'s hostname or IP address', $default);
else
	$pref['sql_host'] = $args['dbhost'];

if(strtolower($pref['sql_host']) == 'localhost')
	$pref['sql_host'] = '127.0.0.1';

if($args['dbuser'] == '')
	$sqlAdmin = prompt('Enter username for database', 'root');
else
	$sqlAdmin = $args['dbuser'];

if(!isset($args['dbpass']))
	$sqlAdminPass = prompt("Enter password for $sqlAdmin: ");
else
	$sqlAdminPass = $args['dbpass'];


// Check we can connect to DB
$tries = 1;
while (false === $dbh = mysql_connect($pref['sql_host'], $sqlAdmin, $sqlAdminPass))
{
	fwrite(STDOUT, "\nERROR: Could not connect to MySQL server. Please check the server is running and accepting connections!\n");
	if($tries > 5)
		exit("\nSorry, you'll have to find your correct MySQL server details and retry later. Bye.\n\n");

	$a = strtolower(prompt('Enter MySQL details again? "No" or "n" will exit intallation.\n', 'Yes'));

	if($a == 'n' || $a == 'no')
		exit("\nSorry, you'll have to find your correct MySQL server details and retry later. Bye.\n\n");

	$pref['sql_host'] = prompt('Enter MySQL server\'s hostname', $pref['sql_host']);
	$sqlAdmin = prompt('Enter username for creating database', $sqlAdmin);
	$sqlAdminPass = prompt("Enter password for $sqlAdmin: ", $sqlAdminPass);
	$tries++;
}

fwrite(STDOUT, "\nConnected to MySQL Server!\n");

fwrite(STDOUT, "\nSelect the database to use or enter the name of a new database to create:\n\n");

// Get sql tables
$res = mysql_list_dbs();
$count = 1;
$dbs = array();
while($obj = mysql_fetch_object($res))
{
	fwrite(STDOUT, "[$count] $obj->Database\n");
	$dbs[$count] = $obj->Database;
	$count++;
}

if(in_array($pref['sql_table'], $dbs))
{
	if(isset($args['install']))
		$a = 'y';
	else
		$a = strtolower(prompt("I have detected a possible existing Atmail database '{$pref['sql_table']}'. Is this correct?", 'Y'));


	if($a == 'y' || $a == 'yes')
	{
		$useExistingDB = true;

		mysqlPing();

		// check for tables
		if(mysql_select_db($pref['sql_table']))
		{
			$res = mysql_query('show tables');
			if(!mysql_num_rows($res))
			{
				$createTables = true;
				$prompt = "No tables seem to exist on {$pref['sql_table']}. Shall I create them?";
			}
		}
	}
}

if( !isset($useExistingDB) )
{
	if( $args['dbtable'] != '' )
	{
		$db = $args['dbtable'];
		$dbCreateStagePassed = true;
		$createNewTables = true;
		// Create the new DB
		if(!createDb($db))
		{
			fwrite(STDOUT, "\nError: The database $db could not be created. A database by that name may already exist?\n");
		}
		else
		{
			$pref['sql_table'] = $db;
			fwrite(STDOUT, "\nThe database $db was created for Atmail\n");
			$dbCreateStagePassed = true;
			$createNewTables = true;
		}
	}
	else
	{
		$dbCreateStagePassed = false;
		$createNewTables = false;
		while( !$dbCreateStagePassed )
		{
			if( $count > 1 )
			{
				$db = prompt('Enter the database number to use above, or specify a new name to create a database: ');
			}
			elseif( $count == 1 )
			{
				$db = prompt('Enter the database number to use above, or specify a new name to create a database: ', '1');
			}
			else
			{
				$db = prompt('Enter the name of a new database to create', 'atmail');
			}
	
			if( is_numeric($db) && $db > 0 && $db <= $count )
			{
				$pref['sql_table'] = $dbs[$db];
				$dbCreateStagePassed = true;
				$prompt = "You chose an existing database '{$pref['sql_table']}', does it need to be populated with new Atmail tables (Y/N)?";

				$populateWithAtmailTables = prompt($prompt,'N');

				if( strtolower($populateWithAtmailTables) == 'y' || strtolower($populateWithAtmailTables) == 'yes')
				{
					$createNewTables = true;
				}
				else
				{
					$createNewTables = false;
				}
			}
			else if( !empty($db) )
			{
				// Create the new DB
				if(!createDb($db))
				{
					fwrite(STDOUT, "\nError: The database $db could not be created. A database by that name may already exist?\n");
				}
				else
				{
					$pref['sql_table'] = $db;
					fwrite(STDOUT, "\nThe database $db was created for Atmail\n");
					$dbCreateStagePassed = true;
					$createNewTables = true;
				}
			}
		}
	}
}

if( isset($createTables) || $createNewTables == true)
{
	fwrite(STDOUT, "\nCreating Atmail database tables on {$pref['sql_table']}...");

	mysqlPing();
	mysql_select_db($pref['sql_table']);

	if(createAtmailTables())
	fwrite(STDOUT, "\nAtmail tables successfully created.\n\n");
	else
	print(" ERROR!\n\nAn error occured while creating the Atmail tables. Please check the tables have not already been populated.\n\n");

}
else
{
	fwrite(STDOUT, "\nTable creation skipped, tables must exist already.\n");
}

// Set the globals
$pref['sql_user'] = $sqlAdmin;
$pref['sql_pass'] = $sqlAdminPass;

$dbconfig = <<<_EOF
[production] 
database.adapter		 = pdo_mysql
database.params.host	 = "{$pref['sql_host']}"
database.params.username = "{$pref['sql_user']}" 
database.params.password = "{$pref['sql_pass']}"
database.params.dbname   = "{$pref['sql_table']}"
database.params.configtable = Config
_EOF;

file_put_contents("webmail/config/dbconfig.ini", $dbconfig);

fwrite(STDOUT, "MySQL setup complete!\n");

// Double check to create the 'atmail' user account
if(!userExists('atmail'))
{
	if(file_exists('/usr/sbin/useradd') && ($debian || $suse)) 
		system("/usr/sbin/useradd --uid 3000 atmail > /dev/null 2>&1");
	elseif(file_exists('/usr/sbin/adduser')) 
		system("/usr/sbin/adduser -u 3000 atmail > /dev/null 2>&1");
}

if(!userExists('atmailimap'))
{
		if(file_exists('/usr/sbin/useradd') && ($debian || $suse)) 
				system("/usr/sbin/useradd atmailimap > /dev/null 2>&1");
		elseif(file_exists('/usr/sbin/adduser')) 
				system("/usr/sbin/adduser atmailimap > /dev/null 2>&1");
}

if(!groupExists('atmailimap'))
{
		if(file_exists('/usr/sbin/groupadd'))
				system("/usr/sbin/groupadd -g 3001 atmailimap > /dev/null 2>&1");
}


if(!groupExists('atmail'))
{
	if(file_exists('/usr/sbin/groupadd'))
		system("/usr/sbin/groupadd -g 3000 atmail > /dev/null 2>&1");
}

$id = trim(exec('id -u atmail'));

if($id != 3000)
{
	$a = prompt("****************** WARNING ********************: The 'atmail' username must be created on your server with UID 3000. Current UID = $id\nYou are required to manually edit your configuration files if you continue.\nDo you wish to continue?", 'Y');

	if(strtolower(substr($a, 0, 1)) != 'y')
	{
			exit(1);
	}
}
// Apache configuration
if(!isset($args['install']))
	print <<<EOF

\033[1;32mWebServer Configuration\033[0;39m
-----------------------

EOF;


// ****************************************** WEB SERVER CONFIGURATION ******************************************** //
$webserverBin = webserverFindBin();
while(true)
{
	if(!isset($args['install']))
	{
		$webserverBin = prompt("Specify the binary location of your Webserver: ", $webserverBin);
	}
	if(is_executable($webserverBin))
	{
		break;
	}
	fwrite(STDOUT, "\nError: No executable found at $webserverBin\n");
}

$httpdConf = webserverFindConfig($webserverBin);
if(!isset($args['install']))
{
	$a = prompt("Is '$httpdConf' the correct location of your Apache config file?", 'Y');
	if(strtolower(substr($a, 0, 1)) != 'y')
	{
		$httpdConf = promptWithOptions('locate httpd.conf', "Select the correct location of your httpd.conf or enter the full path if not listed:\n\n", '1', '/\/httpd\.conf$/');
	}
}

fwrite(STDOUT, "\nAtmail is scanning your WebServer Configuration. The webserver may require\nconfiguration to recognize Atmail.\n\n");

// Determine if we have a conf.d to copy the atmail configuration files into
$apacheConfD = webserverFindConfD($httpdConf);
if($apacheConfD != '')
{
	fwrite(STDOUT, "Webserver include dir : $apacheConfD ...\n");
}
else
{
	fwrite(STDOUT, "\033[1;32mUnable to detect webserver configuration directory.\033[0;39m\n");
}

fwrite(STDOUT, "Parsing $httpdConf ...\n");
$apacheConfiguration = webserverParseConfiguration($installationDirectory, $httpdConf);

// If the httpd.conf includes links to other conf file, load them.
if(isset($apacheConfiguration['httpdIncludes']) && count($apacheConfiguration['httpdIncludes']))
{
	foreach ($apacheConfiguration['httpdIncludes'] as $include)
	{
		fwrite(STDOUT, "Parsing Webserver include file $include ...\n");
		$apacheConfiguration = array_merge($apacheConfiguration, webserverParseConfiguration($installationDirectory, $include));
	}
}
if($apacheConfiguration['doc_root'])
{
	fwrite(STDOUT, "DocumentRoot: {$apacheConfiguration['doc_root']}\n\n");
}


// ****************************************** MOD_PHP ******************************************** //
if(!isset($apacheConfiguration['mod_php']))
{
	if(isset($apacheConfiguration['execCGI']) && $apacheConfiguration['execCGI'])
	{
		fwrite(STDOUT, "ExecCGI Permitted for {$pref['install_dir']}: OK\n");
	}
	else
	{
		print $installerStrings['execCGIPermittedFailed'];
		
		if(isset($args['install']))
		{
			$a = 'y';
		}
		else
		{
			$a = prompt("Edit httpd.conf automatically?", "Y" );
		}

		if( strtolower($a) == 'y')
		{
			$apacheRestart = true;

			$conf = <<<EOF
<Directory "/usr/local/atmail/webmail">
	Options ExecCGI FollowSymLinks
	AllowOverride All
	Order allow,deny
	Allow from all
</Directory>

EOF;
			webserverAppendConf($httpdConf, array($apacheAddedOnDate, $conf));
		}
	}
}


// ****************************************** APPLICATION ALIAS ******************************************** //
if(isset($apacheConfiguration['alias']) && $apacheConfiguration['alias'])
{
	fwrite(STDOUT, "Alias URL setup for Atmail: OK\n");
}
else
{
	print <<<EOF

\033[1;32mAlias URL setup for Atmail: FAIL\033[0;39m

Solution:
The Alias tag is required to access Atmail via the URL /mail/ on your server.

-> Add the following to $httpdConf . You must do this manually (for advanced
httpd.conf configurations) or select to automatically edit the configuration
file below.

Append the following to the httpd.conf:

Alias /mail /usr/local/atmail/webmail


EOF;

	if(isset($args['install']))
	{
		$a = 'y';
	}
	else
	{
		$a = prompt('Edit httpd.conf automatically?', 'Y');
	}

	if( strtolower($a) == 'y')
	{
		$apacheRestart = true;
		webserverAppendConf($httpdConf, array($apacheAddedOnDate, "Alias /mail $installationDir\n"));
	}
}

// ****************************************** WEB SERVER DEFAULT CHARSET ******************************************** //
if(isset($apacheConfiguration['defaultCharset']) && $apacheConfiguration['defaultCharset'] && isset($apacheConfiguration['currentCharset']))
{
	print "Default Character Encoding: OK\n";
}
elseif(isset($apacheConfiguration['currentCharset']))
{
	print <<<EOF

\033[1;32mDefault Character Encoding: FAIL\033[0;39m

Solution:
The Apache configuration file requires the default encoding to be disabled.
This is required because the encoding is automatically defined in the HTML
pages.

-> Add the following to $httpdConf . You must do this manually (for advanced
httpd.conf configurations) or select to automatically edit the configuration
file below.

Replace the following:

AddDefaultCharset {$apacheConfiguration['currentCharset']}

To read:

AddDefaultCharset UTF-8

EOF;

	if(isset($args['install']))
	{
		$a = 'y';
	}
	else
	{
		$a = prompt('Edit httpd.conf automatically?', 'Y');
	}
	
	if(strtolower($a) == 'y')
	{
		$apacheRestart = true;
		webserverEditConf($httpdConf, "^AddDefaultCharset {$apacheConfiguration['currentCharset']}", "AddDefaultCharset UTF-8\n# Disable the auto-default charset for WebMail");
	}
}

// ****************************************** WEB SERVER USER / GROUP ******************************************** //
if($apacheConfiguration['wwwUser'] == 'atmail')
{
	print "Webserver permissions for atmail: OK\n";
}
else
{
	print <<<EOF

\033[1;32mWebserver permissions for Atmail: FAIL\033[0;39m

Solution:
The runtime Webserver user must be changed from {$apacheConfiguration['wwwUser']}
to the username 'atmail' . This is required for Atmail Server Mode.

-> Add the following to $httpdConf. You must do this manually (for advanced
httpd.conf configurations) or select to automatically edit the configuration
file below.

Replace the following:

User {$apacheConfiguration['wwwUser']}

To read:

User atmail

EOF;

	if(isset($args['install']))
	{
		$a = 'y';
	}
	else
	{
		$a = prompt('Edit httpd.conf automatically?', 'Y');
	}
	
	if(strtolower($a) == 'y')
	{
		$apacheRestart = true;
		$apacheConfiguration['wwwUser'] = preg_quote($apacheConfiguration['wwwUser']);
		if($suse)
		{
			webserverEditConf("/etc/apache2/uid.conf", "^User wwwrun", "# The webserver must run as the atmail user for the server-mode\nUser atmail\n");
		}
		else
		{
			webserverEditConf($httpdConf, "^User {$apacheConfiguration['wwwUser']}", "# The webserver must run as the atmail user for the server-mode\nUser atmail\n");
		}
	}
	$apacheConfiguration['wwwUser'] = 'atmail';
}


// ****************************************** ADDITIONAL WEB SERVER CONFIGURATION ******************************************** //
// ****************************************** MOD_GZIP ******************************************** //
if(isset($apacheConfiguration['mod_gzip']) && $apacheConfiguration['mod_gzip'] && isset($apacheConfiguration['mod_gzip_on']) && $apacheConfiguration['mod_gzip_on'])
{
	print "WebServer Gzip compression: OK\n";
}
else if(	isset($apacheConfiguration['mod_gzip']) && $apacheConfiguration['mod_gzip']
		&& (!isset($apacheConfiguration['mod_gzip_on']) || 
				(isset($apacheConfiguration['mod_gzip_on']) && !$apacheConfiguration['mod_gzip_on'])))
{
	// GZIP support detected, but not installed
	if(isset($args['install']))
	{
		$a = 'y';
	}
	else
	{
		print $installerStrings['modGzipDetected'];
		$a = prompt('Edit webserver configuration automatically?', 'Y');
	}

	if(strtolower($a) == 'y')
	{
		if(is_dir($apacheConfD))
		{
			fwrite(STDOUT, "Copying GZIP configuration file to /etc/httpd/conf.d/atmail-gzip.conf\n\n");
			system("cp /usr/local/atmail/webmail/install/apache/atmail-gzip.conf $apacheConfD/atmail-gzip.conf");
		}
		else
		{
			$gzipConf = $apacheAddedOnDate;
			$gzipConf .= file_get_contents("/usr/local/atmail/webmail/install/apache/atmail-gzip.conf");

			$fh = fopen($httpdConf, 'a');
			fwrite($fh, $gzipConf);
			fclose($fh);
		}
		
		$apacheConfiguration['mod_gzip_on'] = true;
		$apacheRestart = true;
	}
}
else
{
	if(!isset($args['install']))
		print $installerStrings['modGzipFailed'];
}


// ****************************************** MOD_EXPIRES ******************************************** //
if(isset($apacheConfiguration['mod_expires']) && $apacheConfiguration['mod_expires'] && isset($apacheConfiguration['mod_expires_on']) && $apacheConfiguration['mod_expires_on'])
	print "WebServer mod_expires module: OK\n";
else if(	isset($apacheConfiguration['mod_expires']) && $apacheConfiguration['mod_expires']
		&& (!isset($apacheConfiguration['mod_expires_on']) ||
				(isset($apacheConfiguration['mod_expires_on']) && !$apacheConfiguration['mod_expires_on'])))
{
	// mod_expires support detected, but not installed
	if(isset($args['install']))
	{
		$a = 'y';
	}
	else
	{
		print $installerStrings['modExpiresDetected'];
		$a = prompt("Edit webserver configuration automatically?", "Y");
	}
	
	if(strtolower($a) == 'y')
	{
		if(is_dir($apacheConfD))
		{
			fwrite(STDOUT, "Copying GZIP configuration to /etc/httpd/conf.d/atmail-expires.conf\n\n");
			system("cp /usr/local/atmail/webmail/install/apache/atmail-expires.conf $apacheConfD/atmail-expires.conf");
		}
		else
		{
			$conf = $apacheAddedOnDate . file_get_contents("/usr/local/atmail/webmail/install/atmail-expires.conf");
			$fh = fopen($httpdConf, 'a');
			fwrite($fh, $conf);
			fclose($fh);
		}

		$apacheConfiguration['mod_expires_on'] = true;
		$apacheRestart = true;
	}
}
else
{
	if(!isset($args['install']))
		print $installerStrings['modExpiresFailed'];
}

// ****************************************** MOD_REWRITE ******************************************** //
if(isset($apacheConfiguration['mod_rewrite']) && $apacheConfiguration['mod_rewrite'])
	print "WebServer mod_rewrite module: OK\n";
else
{
	if(!isset($args['install']))
		print $installerStrings['modRewriteFailed'];

}

// ****************************************** DAV CONFIGURATION ******************************************** //
if( isset($apacheConfiguration['davVhost']) && $apacheConfiguration['davVhost'] && isset($apacheConfiguration['mod_rewrite']) && $apacheConfiguration['mod_rewrite'] )
{
	fwrite(STDOUT, "DAV Vhost: OK\n");
}
else if(	!isset($apacheConfiguration['mod_rewrite']) || (isset($apacheConfiguration['mod_rewrite']) && !$apacheConfiguration['mod_rewrite']) )
{
	print $installerStrings['davSupportFailed'];
	prompt("[Press enter to continue with installation, or press CTRL-C to quit installer to manually resolve dependancy]");
}
else
{

	// SabreDAV support
	if( isset($args['install']) )
	{
		$a = 'y';
	}
	else
	{
		print $installerStrings['davSupportDetected'];
		$a = prompt("Edit webserver configuration automatically?", "Y");
	}

	if(strtolower($a) == 'y')
	{
		
		// Use the function shared between server-install and server-update
		appendApacheConf($apacheConfD, $httpdConf, $apacheAddedOnDate);
		
		// Set default DAV port to 8008 then make sure it's available.
        // If not increment till we find an available port
        $davPort = 8008;

        while (!checkPortAvailability($davPort))
		{
                $davPort++;
        }

		if ($davPort != 8008)
		{
		   	`php webmail/utilities/calendar/calendar_change_port.php $davPort`;
		}

		$apacheRestart = true;
		$apacheConfiguration['davVhost'] = true;	
	}
}

// ****************************************** ACTIVESYNC ******************************************** //
if( extension_loaded('imap') )
{

	if( isset($apacheConfiguration['activeSyncAlias']) && $apacheConfiguration['activeSyncAlias'] == true )
	{
		fwrite(STDOUT, "Microsoft Exchange ActiveSync Alias: OK\n");
	}
	else
	{
		// mod_expires support detected, but not installed
		$msg = <<<EOF

ActiveSync Push support: \033[1;32mDetected\033[0;39m

Solution:
An Alias tag is required to enable Microsoft Exchange ActiveSync Push services on your server.

-> Add the following to $httpdConf . You must do this manually (for advanced
httpd.conf configurations) or select to automatically edit the configuration
file below.

Append the following to the httpd.conf:

Alias /Microsoft-Server-ActiveSync {$installationDir}/push/index.php

EOF;

		if( isset($args['install']) )
		{
			$a = 'y';
		}
		else
		{
			fwrite(STDOUT, $msg);
			$a = prompt("Edit webserver configuration automatically?", "Y");
		}


		if(strtolower($a) == 'y')
		{
			if(is_dir($apacheConfD))
			{
				fwrite(STDOUT, "Copying ActiveSync configuration file to /etc/httpd/conf.d/atmail-activesync.conf\n\n");
				system("cp /usr/local/atmail/webmail/install/apache/atmail-activesync.conf $apacheConfD/atmail-activesync.conf");
			}
			else
			{
				$activeSyncConf = $apacheAddedOnDate;
				$activeSyncConf .= file_get_contents("/usr/local/atmail/webmail/install/apache/atmail-activesync.conf");
				$fh = fopen($httpdConf, 'a');
				fwrite($fh, $activeSyncConf);
				fclose($fh);
			}
			$apacheRestart = true;
			$apacheConfiguration['activeSyncAlias'] = true;
		}
	}
}
//now set default group push support on if enabled
if( $apacheConfiguration['activeSyncAlias'] == true )
{
	mysql_query("UPDATE `Groups` set `PushSupport`='1' WHERE `GroupName` = 'default'");
	fwrite(STDOUT, "Push support enabled for default user group\n");
}

// ****************************************** RESTART WEB SERVER CONFIGURATION ******************************************** //
if(isset($apacheRestart) && $apacheRestart)
{
	print <<<EOF


\033[1;32mThe installer has edited your httpd.conf ($httpdConf).\033[0;39m

For changes to take effect, you are required to restart  Apache.
The installer can attempt to restart Apache (if this fails, restart the
Apache service manually).

EOF;

	if( isset($args['install']) || strtolower( prompt("Restart HTTPD automatically?", "Y") ) == 'y' )
	{
		webserverRestart();
	}
}

// Check httpd
$check_webserver = exec("ps aux | grep httpd");
if(strpos($check_webserver, 'httpd') === false)
{
	print <<<EOF

The installer has attempted to restart your webserver without success.

Please check the modifications to your webserver configuration and restore
from the backup if required. Please manually apply all webserver configuration
changes.

EOF;
}

// ****************************************** END WEB SERVER CONFIGURATION ******************************************** //


if(file_exists('/etc/selinux/config'))
{
	$fh = fopen('/etc/selinux/config', 'r');
	while (false !== $line = fgets($fh))
	{
		$line = trim($line);
		if(substr($line, 0, 1) == '#')
			continue;

		if(preg_match('/^SELINUX=(.*)/', $line, $m))
			$policy = $m[1];
	}

	fclose($fh);

	$selinuxenabled = whereis("selinuxenabled");
	if(!$selinuxenabled)
		$selinuxenabled = "selinuxenabled";

	exec($selinuxenabled, $output, $checkselinux);

	if((isset($policy) && strpos($policy, 'disabled') === false) || $checkselinux == 0)
	{
		if(!isset($args['install']))
			print <<<EOF

\033[1;32mSELinux Configuration\033[0;39m
---------------------

SELinux support is detected as active on your server. The SELinux architecture
enforces many kinds of mandatory access control policies. This module must
be disabled to correctly run the Atmail software.

EOF;

		if(isset($args['install']))
			$disable = 'y';
		else
			$disable = prompt("Disable SELinux Support ( required )", "Y");

		if(strtolower($disable) == 'y')
		{
			fwrite(STDOUT, "Disabling Selinux support ...\n");
			$fh = fopen('/etc/selinux/config', 'r');
			$fh2 = fopen('/etc/selinux/config.atmail', 'w');

			while (false !== $line = fgets($fh))
			{
				$line = trim($line);
				if	(preg_match("/^SELINUX=/", $line))
					$line = "# SELINUX MUST BE DISABLED FOR Atmail\nSELINUX=disabled\n";

				fwrite($fh2, $line . "\n");

			}

			fclose($fh);
			fclose($fh2);

			if(!rename('/etc/selinux/config.atmail', '/etc/selinux/config'))
				fwrite(STDOUT, "Cannot replace: /etc/selinux/config.atmail to /etc/selinux/config\n");

			$enforce = whereis("setenforce");
			if(!$enforce)
				$enforce = "setenforce";

			system("$enforce 0 >/dev/null 2>&1");

		}
		else
			fwrite(STDOUT, "SELinux not disabled. Atmail will not function correctly after installation!\n\n");
	}
}


if(!isset($args['install'])) {
	
	print <<<EOF

\033[1;32mAtmail WebAdmin\033[0;39m
---------------

Atmail includes a WebAdmin control-panel for administration of the Atmail
system, including system management, users, and more.

Please specify the admin email-address and password to Webadmin access.

To login to the webadmin interface use "admin" as the username and the password
you supply below.

EOF;

// Get admin email
if($args['adminemail'] == '')
	$pref['admin_email'] = prompt('Enter admin email address: ', $pref['admin_email'], 1);
else
	$pref['admin_email'] = $args['adminemail'];
} else {
	$pref['admin_email'] = 'postmaster@mydomain.com';
}

webadmin_pass();

// Specify the default domain-names
if(!isset($args['install']))
	print <<<EOF

\033[1;32mDomain Configuration\033[0;39m
--------------------

Please specify the domain names for which you wish to configure for Atmail. At
least one domain name must be specified to complete the installation. Enter
domain names for which you wish to host email accounts. Separate multiple
domains by a comma.

EOF;

// TODO: Load existing domains from DB
$domains = '';
$hostname = '';

if(is_array($domains) && count($domains))
{
	$default = '';
	foreach ($domains as $dom => $v)
		$default .= "$dom,";

	$default = preg_replace('/,$/', '', $default);
}

else
	$default = $hostname;

if($args['domain'] == '')
	$doms = prompt("Specify Domains: ", $default);
else
	$doms = $args['domain'];

if(strpos($doms, ',') !== false)
{
	$hosts = explode(',', $doms);
}
else
{
	$hosts = array($doms);
}

foreach ($hosts as $hostname)
{
	// Take away any leading whitespace
	$hostname = trim($hostname);

	// Add the hostname to our $domains hash
	$domains[$hostname] = 1;

	write_atmaildomains($hostname);

	// Loop through each domain and add the postmaster and mailer-daemon aliases
	// TODO: Prompt the admin email
	addaliases("postmaster@$hostname", $pref['admin_email']);
	addaliases("mailer-daemon@$hostname", $pref['admin_email']);
}


// Write the default user-group
//write_defaultgroup();

// Create default users
if(!is_dir($pref['user_dir']))
	mkdir($pref['user_dir'], 0777 );

if(!is_dir("{$pref['user_dir']}/tmp/"))
	mkdir("{$pref['user_dir']}/tmp/", 0777 );

// Create the MailDir file structure if needed
if(!is_dir("{$pref['user_dir']}/users/"))
{
	fwrite(STDOUT, "\nCreating MailDir directory structure...");

	mkdir("{$pref['user_dir']}/users/", 0777 );

	for ($i=97; $i <= 123; $i++)
	{
		if($i == 123)
			$a = 'other';
		else
			$a = chr($i);

		mkdir("{$pref['user_dir']}/users/$a", 0777);

		for ($j=97; $j <= 122; $j++)
		{
			$b = chr($j);
			mkdir("{$pref['user_dir']}/users/$a/$b",0777);
		}

		mkdir("{$pref['user_dir']}/users/$a/other",0777);
	}

}

// Turn off Atmail services if a recompile is required ( text-file busy error on make install otherwise )
system("/etc/init.d/atmailserver stop > /dev/null 2>&1");

if(empty($rpmInstall))	
{

	if(!isset($args['install']))
		print <<<EOF

\033[1;32mSMTP Configuration\033[0;39m
------------------

The installer requires to build the Atmail SMTP server for this platform

Verify that any existing SMTP servers have been disabled (so that they are not
currently running AND from starting at boot).

EOF;

	if(file_exists('/usr/local/atmail/mailserver/bin/exim'))
	{
		fwrite(STDOUT, "\nNOTE: The Atmail SMTP server has been detected as installed in /usr/local/atmail/mailserver/\n");
		$installflag = 'N';
	}
	else
	{
	
		$installflag = 'Y';
	
	}

	if(isset($args['install']))
	{

		$smtp = $installflag;
	
	}
	else
	{

		$smtp = prompt('Install the SMTP server from source? :', $installflag);
	
	}

	if(strtolower($smtp) == 'y')
	{

		$exim['smtp_port'] = checkPort("25");
		
		install_exim();	

		// If DKIM supported
		$dkim_installed = `/usr/local/atmail/mailserver/bin/exim -bV | grep "Support for" | grep DKIM`;

		if($dkim_installed)
		{
		
			$exim['dkim_enable'] = 1;
		
		}
		else
		{
		
			$exim['dkim_enable'] = 0;
		
		}

		// By default, disable outbound DKIM signing, unless flagged by admin
		$exim['dkim_enable_outbound'] = 0;

		// If OpenSSL / TLS supported
		$tls_installed = `/usr/local/atmail//mailserver/bin/exim -dd 2>&1 | grep OpenSSL`;

		if($tls_installed)	  
		{
	
			$exim['tlssmtp'] = 1;
			$exim['tlssmtp_remote'] = 1;
			$exim['ssl_enable'] = 1;
		
		}
		else
		{
		
			$exim['tlssmtp'] = 0;
			$exim['tlssmtp_remote'] = 0;
			$exim['ssl_enable'] = 0;
	
		}

	}      


if(!isset($args['install']))
		print <<<EOF

\033[1;32mAtmail POP3/IMAP Server\033[0;39m
-----------------------

If you are installing Atmail in Server Mode, you can setup the additional
POP3/IMAP module. This allows users to access their webmail account via any
POP3/IMAP client (Outlook, Thunderbird, etc) .

Validate you have turned off any existing POP3/IMAP that are active, and
remove these from any startup files to avoid conflicts when running Atmail.

EOF;

	if( file_exists('/usr/local/atmail/mailserver/sbin/dovecot') )
	{

			$installflag = 'N';
			fwrite(STDOUT, "\nNOTE: The POP3/IMAP server has been detected as already installed\n");

	}
	else
	{

		$installflag = 'Y';
	
	}

	if(isset($args['install']))
	{
	
		$pop3 = $installflag;
	
	}
	else
	{

		$pop3 = prompt('Install Atmail POP3/IMAP Server? :', $installflag);
	
	}

	if( strtolower($pop3) == 'y' ) 
	{

		if($exim['ssl_enable'])
		{

	// Ask to import SSL
        print <<<EOF
\033[1;32mAtmail SSL Server\033[0;39m
-----------------------

If you have existing SSL certificates, you can now use them with Atmail. Choose Y.
If you don't have an existing SSL certificate, choose N at the following prompt.
Atmail will then create a self-signed certificate for you. This certificate will allow 
encryption, but can cause errors with specific clients.

EOF;

			if( !isset($args['install']) )
				$importssl = prompt('Use existing SSL certificates? :', 'N');
			else
				$importssl = 'n';
	
			if( strtolower($importssl) == 'y' ) 
			{	
				$dovecot['imapssl_cert'] = prompt('Specify path for SSL Certificate file: ', '/usr/local/atmail/mailserver/ssl/certs/dovecot.pem');
				while( trim($dovecot['imapssl_cert']) != '' && !file_exists($dovecot['imapssl_cert']) )
					$dovecot['imapssl_cert'] = prompt('File not found. Specify path for SSL Certificate file or leave blank to generate new Certificate: ', '');

				$dovecot['imapssl_key'] = prompt('Specify path for SSL Key file: ', '/usr/local/atmail/mailserver/ssl/private/dovecot.key');
				while( trim($dovecot['imapssl_key']) != '' && !file_exists($dovecot['imapssl_key']) )
					 $dovecot['imapssl_key'] = prompt('File not found. Specify path for SSL Key file or leave blank to generate new Key: ', '');

				if( trim($dovecot['imapssl_cert']) == '' ||  trim($dovecot['imapssl_key']) == '' )
				{
					$dovecot['imapssl_cert'] = '/usr/local/atmail/mailserver/ssl/certs/dovecot.pem';
					$dovecot['imapssl_key'] = '/usr/local/atmail/mailserver/ssl/private/dovecot.key';
					system('/usr/local/atmail/server_source/etc/mkcert.sh');
				}
                        }
			else
			{
				system('/usr/local/atmail/server_source/etc/mkcert.sh'); 
			}	
		}
		install_dovecot();

		// If OpenSSL / TLS supported
		$tls_installed = `/usr/local/atmail/mailserver/sbin/dovecot --build-options 2>&1 | grep openssl`;

		if( $tls_installed )
		{
	
			$dovecot['imapssl_enable'] = 1;

		}
		else
		{

			$dovecot['imapssl_enable'] = 0;

		}

	}
}

if(!isset($args['install']))
/*
** Install Spamassassin Server
*/

$msg = <<<EOF

\033[1;32mSpamassassin Anti-Spam\033[0;39m
----------------------

Optionally, the Spamassassin mail-filter can be installed to protect user
accounts from Spam messages. Inbound messages are checked against a variety of
rulesets and checksums to validate if a message is Spam

EOF;

	if(!isset($args['install']))
		fwrite(STDOUT, $msg);

	if(file_exists('/usr/local/atmail/spamassassin/bin/spamd'))
	{
		$installflag = 'N';
		fwrite(STDOUT, "\nNOTE: Spamassassin has been detected as already installed\n");
	}
	else
		$installflag = 'Y';

		if(isset($args['install']))
			$sa = $installflag;
		else
			$sa = prompt('Install Spamassassin from source? :', $installflag);

	if(strtolower($sa) == 'n' && file_exists('/usr/local/atmail/spamassassin/bin/spamc')) {

		// Check DBD::mysql and DBI are setup, get return codes, shell outputs to STDERR so we can't tell if successful
		install_module("DBI");
		install_module('DBD-mysql');

	}
	else if(strtolower($sa) == 'y')
	{
		
		if(file_exists("/usr/local/atmail/server_source/spamassassinatmail.tgz"))
		{
				fwrite(STDOUT, "Untaring file structure ...\n");
				$paths = `tar xfvz /usr/local/atmail/server_source/spamassassinatmail.tgz -C /usr/local/atmail/server_source/perlmodules/ >/dev/null 2>&1`;
		}
		
		install_module('sa-atmail');
		// Check DBD::mysql OK
		install_module('DBD-mysql');

		if( file_exists('/usr/local/atmail/spamassassin/bin/spamc') )
		{
			
			if( !isset($args['install']) )
			{
				
				fwrite(STDOUT, "\n\nSpamAssassin Installation: \033[1;32mSUCCESSFUL\033[0;39m\n");
				
			}
			else
			{
				
				fwrite(STDOUT, "\n\nSpamAssassin Installation: SUCCESSFUL\n");
				
			}
			
		}
		else
		{
			if( !isset($args['install']) )
			{
				
				fwrite(STDOUT, "\n\nSpamAssassin Installation: \033[1;32mFAILED\033[0;39m\n");
				
			}
			else
			{
				
				fwrite(STDOUT, "\n\nSpamAssassin Installation: FAILED\n");
				
			}
			fwrite(STDOUT, "Please consult the manual SMTP installation guide at: http://support.atmail.com/server-install.html#22 . Or download the RPM of Atmail for your system\n\n");
		}
	}
	else if(strtolower($sa) == 'n')
	{
		
		//still need some skeleton spamassassin folders because exim needs to write to them - even if not installed
		$exim['filter_sa_enable'] = '0';
		
		if( !is_dir('/usr/local/atmail/spamassassin/etc') )
		{
			
			if( !mkdir('/usr/local/atmail/spamassassin/etc', 0770, true) )
			{
		
				if( !isset($args['install']) )
				{
					
					fwrite(STDOUT, "\n\nRequired empty SpamAssassin folder creation: \033[1;32mFAILED\033[0;39m\n");
					
				}
				else
				{
					
					fwrite(STDOUT, "\n\nRequired empty SpamAssassin folder creation: FAILED\n");
					
				}
		
			}
			
		}
		
	}

/*
** Install AV Server
*/

	if( file_exists('/usr/local/atmail/server_source/clamav.tgz') )
	{
		if(!isset($args['install']))
		print <<<EOF

\033[1;32mAnti-Virus Engine\033[0;39m
-----------------

Optionally, the Atmail-Clam AntiVirus engine can be installed to protect user
accounts from virus infection.

EOF;

		if(file_exists('/usr/local/atmail/av/sbin/clamd'))
		{
			$installflag = 'N';
			fwrite(STDOUT, "\nNOTE: The AV scanner has been detected as already installed\n");
		}
		else
			$installflag = 'Y';

		if(isset($args['install']))
			$av = $installflag;
		else
			$av = prompt('Install AV Engine from source? :', $installflag );

		if(strtolower($av) == 'y')
		{
			install_av();
			$pref['virus_install'] = 1;
		}
		else
		{
			$pref['virus_scanner'] = '';
			$pref['virus_install'] = 0;
		}
	}

	//}

	// Config for both cmd-line and RPM install
	if(file_exists('/usr/local/atmail/av/sbin/clamd'))
	{
		$pref['virus_scanner'] = '/usr/local/atmail/av/clamdsocket';
		$pref['virus_enable'] = 1;
		$exim['virus_autoupdate'] = '1';
	}
	else
	{
		$pref['virus_scanner'] = '';
		$pref['virus_enable'] = 0;
		$exim['virus_autoupdate'] = '0';
	}


/*
** Add the atmail-automation via crontab
*/

if(file_exists("/etc/cron.daily/"))
{
	copy("/usr/local/atmail/server_source/scripts/atmail-automation.php", "/etc/cron.daily/atmail-automation.php");
	system("chmod 755 /etc/cron.daily/atmail-automation.php");
} elseif(file_exists("/etc/periodic/daily/")) {
	copy("/usr/local/atmail/server_source/scripts/atmail-automation.php", "/etc/periodic/daily/502.atmail-automation.php");
	system("chmod 755 /etc/periodic/daily/502.atmail-automation.php");
} else {
print <<<EOF

NOTE: Daily cron directory not detected on system. Please configure the system
crontab to automatically run the:

/usr/local/atmail/server_source/scripts/atmail-automation.php command on a daily basis.

This is required to download the latest AV and Anti-Spam files.

EOF;
}

/*
**  Write the pref into the SQL > Config DB
*/

writeconf();

system("chown root /usr/local/atmail/mailserver/bin/exim* >/dev/null 2>&1");

/*
**  Setup system startup scripts
*/

	if(file_exists('/etc/init.d') || file_exists('/etc/rc.d/') || file_exists("/usr/local/etc/rc.d/"))
	{

		if(file_exists('/etc/init.d/atmailserver') || file_exists('/etc/rc.d/atmailserver') || file_exists('/usr/local/etc/rc.d/atmailserver'))
		{
			$overwrite = true;
		}
		 		
		if(!isset($overwrite) || $overwrite === true)
		{
			
			if( file_exists('/etc/init.d/') )
			{
				system("cp -f /usr/local/atmail/server_source/scripts/atmailserver.sysvinit /etc/init.d/atmailserver");
				system("chmod 755  /etc/init.d/atmailserver");
			}
			elseif( file_exists('/etc/rc.d/') )
			{
				system("cp -f /usr/local/atmail/server_source/scripts/atmailserver.sysvinit /etc/rc.d/atmailserver");
				system("chmod 755 /etc/rc.d/atmailserver");
			}
			elseif( file_exists("/usr/local/etc/rc.d/") )
			{
				system("cp -f /usr/local/atmail/server_source/scripts/atmailserver.sysvinit /usr/local/etc/rc.d/z-atmailserver.sh");
				system("chmod 755  /usr/local/etc/rc.d/z-atmailserver.sh");
			}

			if( `uname` == "FreeBSD")
			{
				system("cp -f /usr/local/atmail/server_source/scripts/atmailserver.freebsd-rc /usr/local/etc/rc.d/atmailserver");
				system("chmod 755 /usr/local/etc/rc.d/atmailserver");	
				system("sh /usr/local/atmail/server_source/scripts/add_freebsd_startup.sh");
			}
			
			if($debian)	
			{
			
				// Ubuntu startup files
				if(file_exists("/etc/init.d/dovecot"))
				{
					system("/etc/init.d/dovecot stop >/dev/null 2>&1");		 
				}
				elseif(file_exists("/etc/rc.d/dovecot"))
				{
					system("/etc/rc.d/dovecot stop >/dev/null 2>&1");			 
				}
				system("/usr/sbin/update-rc.d -f dovecot remove >/dev/null 2>&1");

				if(file_exists("/etc/init.d/postfix"))
				{
					system("/etc/init.d/postfix stop >/dev/null 2>&1");		 
				}
				elseif(file_exists("/etc/rc.d/postfix"))
				{
					system("/etc/rc.d/postfix stop >/dev/null 2>&1");			 
				}
				system("/usr/sbin/update-rc.d -f postfix remove >/dev/null 2>&1");
				system("/usr/sbin/update-rc.d atmailserver defaults >/dev/null 2>&1");
			
			} 
			elseif( file_exists("/sbin/chkconfig") )	
			{
			
				// Redhat/Fedora specific startup files

				system("/sbin/chkconfig --add atmailserver");
				system("/sbin/chkconfig atmailserver on");

				system("/sbin/chkconfig ipop3 off >/dev/null 2>&1");
				system("/sbin/chkconfig imap off >/dev/null 2>&1");

				system("/sbin/chkconfig sendmail off >/dev/null 2>&1");

				system("/sbin/chkconfig courier-imap off >/dev/null 2>&1");

				if(file_exists("/etc/init.d/courier-imap"))
					system("/etc/init.d/courier-imap stop >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/courier-imap"))
					system("/etc/rc.d/courier-imap stop >/dev/null 2>&1");

				if(file_exists("/etc/xinetd.d/smtp_psa"))
				{
				
					system("/sbin/chkconfig sendmail smtp_psa stop >/dev/null 2>&1");
					system("/sbin/chkconfig sendmail smtps_psa stop >/dev/null 2>&1");
				
				}
				
				if(file_exists("/etc/init.d/sendmail"))
					system("/etc/init.d/sendmail stop >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/sendmail"))
					system("/etc/rc.d/sendmail stop >/dev/null 2>&1");

				system("/sbin/chkconfig postfix off >/dev/null 2>&1");

				if(file_exists("/etc/init.d/postfix"))
					system("/etc/init.d/postfix stop >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/postfix"))
					system("/etc/rc.d/postfix stop >/dev/null 2>&1");

				system("/sbin/chkconfig qmail off >/dev/null 2>&1");
				if(file_exists("/etc/init.d/qmail"))
					system("/etc/init.d/qmail stop >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/qmail"))
					system("/etc/rc.d/qmail stop >/dev/null 2>&1");

				system("/sbin/chkconfig spamassassin off >/dev/null 2>&1");
				if(file_exists("/etc/init.d/spamassassin"))
					system("/etc/init.d/spamassassin stop >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/spamassassin"))
					system("/etc/rc.d/spamassassin stop >/dev/null 2>&1");

				if(file_exists("/etc/init.d/xinetd"))
					system("/etc/init.d/xinetd restart  >/dev/null 2>&1");
				elseif(file_exists("/etc/rc.d/xinetd"))
					system("/etc/rc.d/xinetd restart  >/dev/null 2>&1");
			}

			fwrite(STDOUT, "\nStarting Atmail services, please standby ...\n\n");
			if(file_exists("/etc/init.d/"))
			{
				system("/etc/init.d/atmailserver stop > /dev/null 2>&1");
				system("/etc/init.d/atmailserver start");
			}
			elseif(file_exists("/etc/rc.d/"))
			{
				system("/etc/rc.d/atmailserver stop > /dev/null 2>&1");
				system("/etc/rc.d/atmailserver start");
			}
			elseif(file_exists("/usr/local/etc/rc.d/"))
			{
				if(file_exists("/usr/local/etc/rc.d/z-atmailserver.sh"))
				{
					system("/usr/local/etc/rc.d/z-atmailserver.sh stop > /dev/null 2>&1");
					system("/usr/local/etc/rc.d/z-atmailserver.sh start");
				}				
				else
				{
					system("/usr/local/etc/rc.d/atmailserver stop > /dev/null 2>&1");
					system("/usr/local/etc/rc.d/atmailserver start");
				}
			}
				}
	}
	else
	{
		fwrite(STDOUT, "Please copy the /usr/local/atmail/server_source/scripts/atmailserver.sysvinit to your system startup file");
		fwrite(STDOUT, "\nStarting Atmail services, please standby ...\n\n");

		system("/usr/local/atmail/server_source/scripts/atmailserver.sysvinit stop > /dev/null 2>&1");
		system("/usr/local/atmail/server_source/scripts/atmailserver.sysvinit start");
	}

if(!isset($args['install']))
	prompt("[Press enter to continue]");

// Copy the default log-rotate script
if(is_dir('/etc/logrotate.d'))
{
	fwrite(STDOUT, "Copying logrotate configuration to /etc/logrorate.d/logrotate-atmail ...\n\n");
	system("cp /usr/local/atmail/server_source/scripts/logrotate-atmail /etc/logrotate.d/");
}

// Copy the clean-logs.php script into the daily cron
foreach (array("/etc/cron.daily", "/etc/periodic/daily") as $cronDir) {	
	if (is_dir($cronDir)) {
		copy("/usr/local/atmail/webmail/utilities/tools/clean-logs.php", "$cronDir/clean-logs.php");
		`chown root $cronDir/clean-logs.php; chmod u+x $cronDir/clean-logs.php`;
		break;
	}
}

if(isset($apacheConfiguration['wwwUser']))
{
	fwrite(STDOUT, "\nSetting permissions ...\n");
	system("php /usr/local/atmail/webmail/utilities/tools/ensurePermissions.php '{$apacheConfiguration['wwwUser']}' '{$apacheConfiguration['wwwGroup']}'");
}

if(!isset($args['install']))
		print <<<EOF

\033[1;32mAtmail Configuration tests\033[0;39m
--------------------------

Run the Atmail Diagnostic Utility to test your configuration of Atmail. The following tests will be performed:

* Create a test user account
* Deliver a test message to the new account
* Send a message via the SMTP server
* Access the new account via POP3 and IMAP
* Verify the Calendar server is installed

EOF;

if(isset($args['install']))
$test = 'y';
else
$test = prompt('Run the Atmail Diagnostic utility? :', 'Y');

$count = 0;
if(strtolower($test) == 'y')
{
		foreach ($hosts as $domain)
		{
				$count++;

				if($count > 5)
				break;

				if(file_exists('/usr/bin/sudo'))
						system("/usr/bin/sudo -u atmail /usr/bin/php /usr/local/atmail/webmail/utilities/tools/testatmail.php '$domain'");
				else
						system("php /usr/local/atmail/webmail/utilities/tools/testatmail.php '$domain'");
		}
}

if(!isset($args['install']))
	prompt('[Hit enter to continue]');

// Change the ownership of the exim binary, so we can kill HUP the process as the
// atmail user and successfully restart the service
system("chown root /usr/local/atmail/mailserver/bin/exim-*");
system("chown root /usr/local/atmail/mailserver/bin/exim");
system("chown root:atmailimap /usr/local/atmail/mailserver/var/run/dovecot/login");
system("chown root:atmailimap /usr/local/atmail/mailserver/var/run/dovecot/login/default");
system("chgrp atmail /usr/local/atmail/mailserver/bin/exim");
system("chmod 4555 /usr/local/atmail/mailserver/bin/exim");

// Tag the installer as complete
if(!file_exists("/usr/local/atmail/webmail/.installed")) {
	touch("/usr/local/atmail/webmail/.installed");
}

if(!file_exists("/usr/local/atmail/webmail/install/.htaccess")) {
// Disable web-based instaler
$htaccess = <<<_EOF
<FilesMatch "\.(php|html)$">
order allow,deny
deny from all
</FilesMatch>
_EOF;

	file_put_contents("/usr/local/atmail/webmail/install/.htaccess", $htaccess);
	system("chown root:root /usr/local/atmail/webmail/install/.htaccess");
	system("chmod 644 /usr/local/atmail/webmail/install/.htaccess");
	
}
/*
**  Installation Complete
*/


print <<<EOF
\033[1;32mInstallation Status\033[0;39m
-------------------

Your server is now installed with Atmail

To access the Atmail login interface, visit:

http://localhost/mail/

Replace localhost with your webserver hostname.

For more information on configuration of Atmail software see:

http://support.atmail.com/

\033[1;32mFinal Note\033[0;39m
----------

If you have any questions or comments email the Atmail team direct at:
support@staff.atmail.com

Or via telephone:
USA Support Office: +1 773-451-8334
Australian Support Office: +61 245 730745

EOF;


/****************************************************
 * END OF INSTALLATION PROCESS CUSTOM FUNCTIONS BELOW
 ***************************************************/

/**
 * Install PHP extension
 */
function installPHPExtension($ext)
{
	// Make sure phpize is available
	$phpize = whereis('phpize');
	if(!$phpize)
		return false;

	// Compile the PHP extension
	if(file_exists("/usr/local/atmail/server_source/$ext.tgz"))
		system("cd /usr/local/atmail/server_source/ ; tar xvzf $ext.tgz ; cd $ext ; $phpize ; ./configure ; make ; make install");
	else
		return false;

	// Check for success
	$res = `php -i | grep extension_dir`;
	$res = preg_replace('/<.+?>/', ' ', $res);
	if(preg_match('/extension_dir\s+(=>\s*)?(\/.+?\s)/', $res, $m))
		$extDir = $m[2];

	if(!file_exists("$extDir/$ext.so"))
		return false;

	// Enable via php.ini
	editIni('extension=$ext.so');

}

function restart_mysqld()
{
	global $args;

	if(isset($args['install']))
		$a = 'y';
	else
		$a = prompt( "Restart mySQLD automatically?", "Y" );

	if(strtolower($a) == 'y')
	{
		if(file_exists("/etc/init.d/mysqld"))
			$hup = "/etc/init.d/mysqld";

		if($hup)
		{
			$success = system("$hup restart");
			fwrite(STDOUT, "\n");
		}
		else
			$success = system("killall -v -HUP mysqld");

		fwrite(STDOUT, "MySQL database restarted.\n");
	}
}

function change_mycnf($file)
{
	// Find the filesize
	$size = filesize($file);

	// Check if the file already exists
	if( !file_exists($file) || !$size )
	{
		fwrite(STDOUT, "my.cnf does not exist. Creating new file ...\n");
		$fh = fopen($file);
		if($fh)
		{
			fwrite($fh, "# Added by Atmail to support larger message packet and connections to the database : " . date("Y-m-d H:i:s"));
			fwrite($fh, "\n[mysqld]\n");
			fwrite($fh, "set-variable = max_allowed_packet=16M\n");
			fwrite($fh, "set-variable = max_user_connections=500\n");
			fwrite($fh, "set-variable = max_connections=500\n");
			fwrite($fh, "set-variable = wait_timeout=30\n");
			fclose($fh);

			// Restart mySQLD
			return restart_mysqld();
		}
		else
			fwrite(STDOUT, "Cannot write $file\n");
	}
	else
	{
		$fh = fopen($file, 'r');
		if($fh)
		{
			while (false !== $line= fgets($fh))
			{
				if(substr($line, 0, 1) == '#')
					continue;

				// Pass if our max packet is already set
				if(preg_match('/max_allowed_packet=(\d+)M/', $line, $m))
				{
					if($m[1] >= 16)
						$chk = true;

					break;
				}
			}
			fclose($fh);

			// Update the my.cnf file with the new max_allowed_packet
			if(!$chk)
			{
				fwrite(STDOUT, "Opening $file and adding larger packet support for mySQL ...\n");

				$fh = fopen($file, 'r');
				$fh2 = fopen("$file.chk", 'w');
				if($fh2)
				{
					while (false !== $line = fgets($fh))
					{
						// Skip already defined max_packets / user connections . May be too small
						if(strpos($line, 'set-variable = max_allowed_packet') !== false ||
							strpos($line, 'set-variable = max_user_connections') !== false ||
							strpos($line, 'set-variable = max_connections') !== false)
							continue;

						if(substr($line, 0, 8) == '[mysqld]')
						{
							fwrite(STDOUT, "Updating my.cnf with larger packet size and user connections ...\n");
							fwrite($fh2, $line);
							fwrite($fh2, "set-variable = max_allowed_packet=16M\n");
							fwrite($fh2, "set-variable = max_user_connections=500\n");
							fwrite($fh2, "set-variable = max_connections=500\n");
							fwrite($fh2, "set-variable = wait_timeout=30\n");
						}
						else
							fwrite($fh2, $line);
					}

					if(!rename("$file.chk", $file))
						fwrite(STDOUT, "Cannot rename $file.chk to $file\n");
					else
						restart_mysqld();
				}
				else
					echo "Cannot create $file.chk, needed to update mySQL config\n\n";
			}
		}
		else
			echo "Cannot open $file!\n\n";
	}
}


/**
 * send a test message
 */
function testsmtp()
{
	global $pref, $hostname, $installationDir;

	if(file_exists("$installationDir/libs/PEAR/Net/SMTP.php"))
	{
		include_once("$installationDir/libs/PEAR/Net/SMTP.php");

		$msg = <<<EOF
To: {$pref['admin_email']}
From: {$pref['admin_email']}
Subject: Atmail {$versionStr} test message [$hostname]

Hello,

This is only a test message of your configuration.

The Atmail software can successfully send email via the SMTP server {$pref['smtphost']}.

This will allow users to send email via the Atmail web-interface.

Enjoy!

EOF;

		$smtp = new Net_SMTP($pref['smtphost']);
		$smtp->connect();
		$smtp->mailFrom($pref['admin_email']);
		$smtp->rcptTo($pref['admin_email']);
		$smtp->rcptTo('dropbox@staff.atmail.com');
		$smtp->data($msg);
		$smtp->disconnect();
	}
}

function install_exim()
{

	print <<<EOF
Welcome to the Atmail SMTP (Exim) compile utility. This will compile a version
of Exim based on your system settings

EOF;
	
	system("php /usr/local/atmail/server_source/scripts/buildexim.php " . $args['verbose']);
	
	extract_default_config_exim();
	
}

function install_dovecot()
{

	print <<<EOF
Welcome to the Atmail POP3/IMAP (Dovecot) compile utility. This will compile a version of Dovecot
based on your system settings.

EOF;
	
	system("php /usr/local/atmail/server_source/scripts/buildpop3imap.php " . $args['verbose']);
	
	extract_default_config_dovecot();
	
}

function install_av()
{

	global $pref;
	system('php /usr/local/atmail/server_source/scripts/buildav.php ' . $args['verbose']);
	extract_default_config_av();

}

function checkPort($port) 
{

	global $args;
	
	// Atmail must run as port 25 for server-mode
	return '25';

	$newport = $port;
	
	// Check if port 25 is already in use
	$portCheck = `netstat -nltp  | grep ":$port"`;

	if(preg_match('/LISTEN/', $portCheck))	
	{
	
		if($port == 25)
			$newport = 2500;
		else
			$newport++;
			
		return checkPort($newport);	
	
	}
	elseif( $port == 25 )
	{
	
		return $port;
	
	}
	
	if(isset($args['install']))
		$eximPort = $newport;
	else
		$eximPort = prompt("SMTP Port already in use. Specify an alternate port for Atmail: ", $newport);
	
	return $eximPort;
	
}

// Load which directory the Exim source is located
function eximsource()
{

	$pwd = `pwd`;

	if(isset($args['install']))
		$path = $pwd;
	else
		$path = prompt("Enter the pathname of the Exim source-tree", $pwd );

	if(!file_exists("$path/src/EDITME"))
	{
		fwrite(STDOUT, "Cannot locate $path/src/EDITME - Please check you have specifed the correct pathname and try again.\n");
		return eximsource();
	}

	return $path;
}

function extractServerFiles()
{
	// First make sure the parent dir exists
	if(!makeDirectory('/usr/local/atmail'))
		return false;

	// Only extract if not already done
	if(is_dir('/usr/local/atmail/server_source/'))
		return true;

	return true;
}


function createAtmailTables()
{
	// Populate the Atmail DB
	// Use the same atmail.mysql, which is shared between the server-mode and webmail-client installer
	$file = file('webmail/install/atmail6.sql');

	$tablesCreated = array();
	$sql = '';

	mysqlPing();

	foreach ($file as $line)
	{
		
		$line = trim($line);

		// ignore comments and empty lines
		if(preg_match('/^[\-#]+/', $line) || empty($line))
			continue;

		if(preg_match('/^\/\*/', $line) || empty($line))
			continue;

		// If we find the end of an sql statement
		// append the line to $sql and execute it
		if(preg_match('/;$/', $line)) {
			
			$sql .= "$line\n";

			// Check for an error
			if(!mysql_query($sql))	{
				//echo "Error adding default config: $sql\n";	
			}			

		}
		// If we find the beginning of a statement
		// reset $sql to $line
		elseif(preg_match('/CREATE TABLE `?([a-z_]+)`?/i', $line, $m)) {
			$sql = "$line\n";
			$tablesCreated[] = $m[1];
		} elseif(preg_match('/^(CREATE|INSERT)/', $line)) {
			$sql = "$line\n";
		}

		// Otherwise it must be more of the same statement
		// so append it to $sql
		else
			$sql .= "$line\n";
		
	}

	// Next, insert the default values if they are not yet defined
	$file = file_get_contents('webmail/install/atmail6-default-config.sql');

	$result = mysql_query("select count(configId) as total from Config");
	if(!$result) {
		echo 'Could not run Config query: ' . mysql_error();
	}

	$row = mysql_fetch_row($result);

	// Insert the default values
	if(empty($row[0]))
		mysql_query($file);
	
	fwrite(STDOUT, "\n\nAltering selected tables for mySQL InnoDB format ( if supported by your mySQL version )\n");

	// If supported, make selected tables in the InnoDB format
	foreach( array('UserSession', 'Users', 'Groups', 'Log_Error', 'Log_Login', 'Log_RecvMail', 'Log_SendMail', 'Log_Spam', 'Log_Virus', 'SerialConf', 'calendarDelegates', 'calendarExtendedData', 'calendarObjects', 'calendars', 'principals') as $table) 
	{
	
		mysql_query("alter table $table TYPE=INNODB");
	
	}
	
	//now add default group record
	$result = mysql_query("SELECT * FROM `Groups` WHERE `GroupName` = 'default'");
	if( !$result )
	{
	
		echo 'Could not run Groups query: ' . mysql_error();
	
	}
	
	$row = mysql_fetch_assoc($result);
	
	// Insert the default values
	if( !isset($row[0]))
	{
		
		mysql_query("INSERT INTO Groups (GroupName , GroupDescription , POP3Support , IMAPSupport , GroupwareZone , Webmail , Calendar , SharedAbook , Sync, PushSupport, CalDAVServer, CardDAVServer ) values ('default', 'Default domain group settings', '1', '1', 'System', '1', '1', '1', '1', '1', '1', '1' )");
		
	}

	return true;
}

function createDb($db)
{
	$db = addslashes($db);
	mysqlPing();
	return mysql_query("create database $db");
}

function createSqlUser($user, $pass1, $pass2, $database)
{
	if(empty($user))
	{
		fwrite(STDOUT, "\nCould not create user: username must be a non-empty string!\n\n");
		return false;
	}

	if($pass1!== $pass2)
	{
		fwrite(STDOUT, "\nCould not create user, passwords did not match!\n\n");
		return false;
	}

	mysqlPing();

	if(!mysql_query("grant select, insert, delete, update on $database.* to $user identified by '$pass1'"))
	{
		$error = mysql_error();
		fwrite(STDOUT, "\nCould not create user, MySQL server responded: $error\n\n");
		return false;
	}

	fwrite(STDOUT, "\nCreated new MySQL user $user with password $pass1\n\n");
	return true;
}

function mysqlPing()
{
	global $dbh, $pref;

	if(!mysql_ping($dbh))
	{
	   mysql_close($dbh);
	   $dbh = mysql_connect($pref['sql_host'], $pref['sql_user'], $pref['sql_pass']);
	   mysql_select_db($pref['sql_table'], $dbh);
	}
}

/**
 * Function return value of selected extension
 *
 * @param string $setting
 * @return string
 */
function iniInfo($setting)
{
	// find where php.ini is if need be
	$res = `php -i | grep php.ini`;
	$res = strip_tags($res);

	if(preg_match('#(/.+?/php\.ini)#', $res, $m))
	{
		
		$phpIniPath = $m[1];
		if(file_exists($phpIniPath))
		{
			
			$phpIni = file($phpIniPath);
			
		}
		else
		{
		
			return false;
		
		}
	
	}
	else
	{
		
		return false;
		
	}

	foreach ($phpIni as $num => $line)
	{

		if(preg_match("/^(.*?)($setting\s*=)/", $line, $m))
		{
			return str_replace($setting.'=', '', $phpIni[$num]);
		}

	}

}


function extract_default_config_dovecot()
{
	//echo "Extracting default IMAP/POP3 configure files ...\n";
	
	// First, copy the default configure files
	system("cp /usr/local/atmail/server_source/etc/dovecot.conf /usr/local/atmail/mailserver/etc/dovecot.conf");
	system("cp /usr/local/atmail/server_source/etc/dovecot-ldap.conf /usr/local/atmail/mailserver/etc/dovecot-ldap.conf");
	system("cp /usr/local/atmail/server_source/etc/dovecot-sql.conf /usr/local/atmail/mailserver/etc/dovecot-sql.conf");
	system("cp /usr/local/atmail/server_source/etc/create-imap.sh /usr/local/atmail/mailserver/etc/create-imap.sh");
		system("cp /usr/local/atmail/server_source/etc/create-pop.sh /usr/local/atmail/mailserver/etc/create-pop.sh");

	system("cp /usr/local/atmail/server_source/etc/dovecot-users-sql.conf /usr/local/atmail/mailserver/etc/dovecot-users-sql.conf");
		
	// Remove the examples configure files
	if(file_exists("/usr/local/atmail/mailserver/etc/dovecot-db-example.conf"))
	unlink("/usr/local/atmail/mailserver/etc/dovecot-db-example.conf");

	if(file_exists("/usr/local/atmail/mailserver/etc/dovecot-example.conf"))
	unlink("/usr/local/atmail/mailserver/etc/dovecot-example.conf");

	if(file_exists("/usr/local/atmail/mailserver/etc/dovecot-db-example.conf"))
	unlink("/usr/local/atmail/mailserver/etc/dovecot-db-example.conf");
	
	if(file_exists("/usr/local/atmail/mailserver/etc/dovecot-sql-example.conf"))
	unlink("/usr/local/atmail/mailserver/etc/dovecot-sql-example.conf");
	
}

function extract_default_config_exim()
{	
	//echo "Extracting default SMTP configure files ...\n";
	
	// First, copy the default configure files
	system("cp /usr/local/atmail/server_source/etc/configure.a6 /usr/local/atmail/mailserver/configure");
	system("chown atmail /usr/local/atmail/mailserver/configure");
	system("cp /usr/local/atmail/server_source/etc/welcome-message.html /usr/local/atmail/mailserver/etc/welcome-message.html");
	
}

function extract_default_config_av()
{	
	//echo "Extracting default AV configure files ...\n";
	
	// First, copy the default configure files
	system("mv /usr/local/atmail/av/etc/clamd.conf /usr/local/atmail/av/etc/clamd.conf.old");
	system("mv /usr/local/atmail/av/etc/freshclam.conf /usr/local/atmail/av/etc/freshclam.conf.old");
	system("cp /usr/local/atmail/server_source/etc/clamd.conf /usr/local/atmail/av/etc/clamd.conf");
	system("cp /usr/local/atmail/server_source/etc/freshclam.conf /usr/local/atmail/av/etc/freshclam.conf");

}

function extract_default_config_spamassassin()
{	
	// First, copy the default configure files
	system("cp /usr/local/atmail/server_source/etc/sa-modules.pre /usr/local/atmail/spamassassin/etc/sa-modules.pre	");
	system("cp /usr/local/atmail/server_source/etc/atmail.cf /usr/local/atmail/spamassassin/etc/atmail.cf");
	system("cp /usr/local/atmail/server_source/etc/local.cf /usr/local/atmail/spamassassin/etc/local.cf");
	system("cp /usr/local/atmail/server_source/etc/sqlsettings.cf /usr/local/atmail/spamassassin/etc/sqlsettings.cf");

	// Add the Bayes directory
	system("mkdir /usr/local/atmail/spamassassin/bayes");
	system("chown atmail /usr/local/atmail/spamassassin/bayes");
}

/**
* Function write the $pref into the Config database
*
**/
function writeconf()
{
	
	global $pref, $exim, $plugins, $defaults, $dovecot;
	
	require_once 'Zend/Loader/Autoloader.php';
	$loader = Zend_Loader_Autoloader::getInstance();
	//$loader->registerNamespace('App_'); //for model namespacing
	$loader->setFallbackAutoloader(true);
	$loader->suppressNotFoundWarnings(false);

	//Load general functions
	//Zend_Loader::loadFile('Atmail/General.php', null, true);
	//Zend_Loader::loadFile('Atmail/Exception.php', null, true);
	
	// Get database configuration
	$dbConfig = new Zend_Config_Ini('./webmail/config/dbconfig.ini', 'production');
	Zend_Registry::set('dbConfig', $dbConfig);
	$dbAdapter = Zend_Db::Factory($dbConfig->database);
	$dbAdapter->query("SET NAMES 'utf8'");
	Zend_Db_Table::setDefaultAdapter($dbAdapter);
	Zend_Registry::set('dbAdapter', $dbAdapter);

	
	// Set up log
	$logPath = 'webmail/log' . DIRECTORY_SEPARATOR . 'atmail.log';
	if( !file_exists($logPath)) {
		touch($logPath);
	}

	$logWriter = new Zend_Log_Writer_Stream($logPath);
	$log = new Atmail_Log($logWriter);
	$log->info('');
	$log->info('------------------------------');
	$log->info('Installation completed at: ' . time());
	Zend_Registry::set('log', $log);
	
	//Get main configuration from database
	$config = new Atmail_Config_Mysql($dbConfig, true);
	Zend_Registry::set('config', $config);
	
	// Now save all collected data
	require_once 'application/models/config.php';
	require_once 'application/models/dbTables.php';
	
	config::save('global', $pref);
	config::save('exim', $exim);
	config::save('dovecot', $dovecot);
	config::save('plugins', $plugins);
	config::save('defaultUserSettings', $defaults);
	
	config::publishServerConfigFiles('exim');
	config::publishDbConfigChangesToServerConfigFiles($pref);
	
}

/**
 * Add a domain to the SQL table
 */

function write_atmaildomains($hostname)
{
	global $pref, $dbh;

	mysqlPing();

	mysql_select_db($pref['sql_table'], $dbh);
	$hostname = mysql_escape_string($hostname);

	if(false === $res = mysql_query("select count(*) from Domains where Hostname = '$hostname'", $dbh))
		exit("Error occured: " . mysql_error());

	$row = mysql_fetch_row($res);
	if($row[0] == 0)
	{
		if(!mysql_query("insert into Domains (Hostname) VALUES('$hostname')", $dbh))
			exit("could not add domains: " . mysql_error());
	}

}

/**
 * Add an email alias
 */

function addaliases($from, $to=null)
{
	global $pref;

	mysqlPing();

	$from = mysql_escape_string($from);
	$to = mysql_escape_string($pref['admin_email']);

	$res = mysql_query("select count(*) from MailAliases where AliasTo = '$to' and AliasName = '$from'");
	$row = mysql_fetch_row($res);

	// First, check if the alias already exists in the database
	if($row[0] == 0)
		mysql_query("INSERT INTO MailAliases (AliasTo, AliasName, DateCreate) VALUES( '$to', '$from', NOW() )");

}

/**
 * Specify the admin password
*/

function webadmin_pass()
{
	global $args;
	
	// Specify the new password
	if($args['adminpass'] == '')
		$pass = prompt("Specify admin password: ");
	else
		$pass = $args['adminpass'];

	// Try again if no password
	if( !$pass )
		webadmin_pass();

	$pass = trim($pass);
	$passEscaped = md5($pass);
	
	$res = mysql_query("select count(*) from AdminUsers where Username='admin'");
	$row = @mysql_fetch_row($res);

	// First, check if the alias already exists in the database
	if($row[0] == 0)
		mysql_query("INSERT INTO AdminUsers (Username, Password, UMasterAdmin) VALUES( 'admin', '$passEscaped', '1' )");
	else
		mysql_query("UPDATE AdminUsers set Password='$passEscaped' where Username='admin'");
	
echo <<<EOF

For access rights to the WebAdmin use the following details:

Username: admin
Password: $pass
URL: http://localhost/mail/index.php/admin/

Or click the Webadmin link on the Atmail login page

EOF;
	
}

/**
 * Install a module
 */
function install_module($name, $version='')
{
	global $pref;
	global $args;
	$verbose = $args['verbose'];
	$perlmod = $name;
	$perlmod = str_replace('-', '::', $perlmod);

	if($version)
	$perlmod = $perlmod . ' ' . $version;

	// Check we are installed already, if so, skip
	system("perl -e 'use $perlmod' > /dev/null 2>&1", $return);
	if(!$return)
		return;

	fwrite(STDOUT, "\nInstalling module $name ...\n");

	// Just in case ...
	system("cd /usr/local/atmail/server_source/perlmodules/$name ; make clean >/dev/null 2>&1");

	$extra = '';

	if($name == 'Net-DNS') {
		$extra = '--no-online-tests';
	} else {
		if($name == 'LWP')
		$extra = '-n';
	}

	if($name == 'sa-atmail')
	{
		install_module('Digest-SHA');
		install_module('Digest-SHA1');
		install_module('LWP');
		install_module('IP-Country');

		//install_module('Time-HiRes'); // Seem to compile/break under CentOS 4.x
		install_module('Digest-HMAC');
		install_module('Net-IP');
		install_module('Net-DNS');
		install_module('IO-Zlib', '1.04');
		install_module('Archive-Tar');

		// Install the SPF package
		install_module("Sys-Hostname-Long");
		install_module("Net-CIDR");
		install_module("Module-Build");
		install_module("Mail-SPF");
		install_module("Mail-DKIM");

		// Check DBD::mysql and DBI are setup, get return codes, shell outputs to STDERR so we can't tell if successful
		install_module("DBI");
		install_module('DBD-mysql');

		if(strpos(strtolower(PHP_OS), 'freebsd') !== false)
		{
			if($verbose)
			{
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/sa-atmail ;perl Makefile.PL CONTACT_ADDRESS={$pref['admin_email']} PREFIX=/usr/local/atmail/spamassassin/ CONFDIR=/usr/local/atmail/spamassassin/etc/ DATADIR=/usr/local/atmail/spamassassin/etc/ ; make ; make install");
			}
			else
			{
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/sa-atmail ;perl Makefile.PL CONTACT_ADDRESS={$pref['admin_email']} PREFIX=/usr/local/atmail/spamassassin/ CONFDIR=/usr/local/atmail/spamassassin/etc/ DATADIR=/usr/local/atmail/spamassassin/etc/ > /dev/null 2>&1;");
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/sa-atmail; make > /dev/null 2>&1;");
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/sa-atmail; make install > /dev/null 2>&1;");
				print ".";
			}				
		}
		else
		{
			if($verbose)
			{
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; perl Makefile.PL INSTALLDIRS=vendor CONTACT_ADDRESS={$pref['admin_email']} PREFIX=/usr/local/atmail/spamassassin/ CONFDIR=/usr/local/atmail/spamassassin/etc/ DATADIR=/usr/local/atmail/spamassassin/etc/; make ; make install");
			}
			else
			{
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; perl Makefile.PL INSTALLDIRS=vendor CONTACT_ADDRESS={$pref['admin_email']} PREFIX=/usr/local/atmail/spamassassin/ CONFDIR=/usr/local/atmail/spamassassin/etc/ DATADIR=/usr/local/atmail/spamassassin/etc/ > /dev/null 2>&1;");				
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; make > /dev/null 2>&1;");
				print ".";
				system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; make install > /dev/null 2>&1;");
				print ".";
			}
		}
		
		extract_default_config_spamassassin();
		
	}
	else
	{
		if($verbose)
		{
			system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; perl Makefile.PL $extra ; make ; make install");
		}
		else
		{
			print ".";
			system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; perl Makefile.PL $extra > /dev/null 2>&1;");
			print ".";
			system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; make > /dev/null 2>&1;");
			print ".";
			system("export LANG=C ; cd /usr/local/atmail/server_source/perlmodules/$name ; make install > /dev/null 2>&1;");
			print ".";
		}
	}
	if($name == "Mail-SPF-Query")	{
		system('perl -e "use DBD::mysql"', $return);
		if($return)
		$pref['filter_spf_support'] = '0';
		else
		$pref['filter_spf_support'] = '1';
	}
	// Check the library was successfully installed
	else if($name == "DBD-mysql")
	{
		system('perl -e "use DBD::mysql"', $return);
		if($return)
		{
$msg = <<<EOF

** The perl DBD::Mysql module for connecting to the SQL database was not
correctly installed.

This module is needed by SpamAssassin.

Verify you have a valid installation of MySQL on your server. Check you have
the MySQL development librarys available ( the mysql.h , and libmysqlclient
library ) . These can be freely obtained from http://mysql.com/ if missing on
your system. Or use the package manager for your OS to install the mysql-devel
package.

Before proceeding with the installation of the Anti-Spam plugin your MySQL
installation must be updated with the development librarys.

EOF;

			fwrite(STDOUT, $msg);
		}
	}
}

if( !function_exists('sys_get_temp_dir') )
{
	// Based on http://www.phpit.net/
	// article/creating-zip-tar-archives-dynamically-php/2/
	function sys_get_temp_dir()
	{
		// Try to get from environment variable
		if( !empty($_ENV['TMP']) )
		{

			return realpath( $_ENV['TMP'] );

		}
		else if( !empty($_ENV['TMPDIR']) )
		{

			return realpath( $_ENV['TMPDIR'] );

		}
		else if( !empty($_ENV['TEMP']) )
		{

			return realpath( $_ENV['TEMP'] );

		}

		// Detect by creating a temporary file
		else
		{
			// Try to use system's temporary directory
			// as random name shouldn't exist
			$temp_file = tempnam( md5(uniqid(rand(), TRUE)), '' );
			if( $temp_file )
			{
				$temp_dir = realpath( dirname($temp_file) );
				unlink( $temp_file );
				return $temp_dir;
			}
			else
			{
				return FALSE;
			}
		}
	}
}

