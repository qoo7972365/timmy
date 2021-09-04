<?php
class Admin_SettingsController extends Zend_Controller_Action
{
	public function init()
	{
		$this->view->addHelperPath('library/Atmail/View/Helper/', 'Atmail_View_Helper');
		$this->_helper->pluginCall('initAdminController', $this);
		 
		$this->request  = $this->getRequest();
		$this->requestParams = $this->request->getParams();
		
		$this->view->baseUrl = $this->request->getBaseUrl();
		$this->view->appBaseUrl = $this->request->getBaseUrl() . (strpos($this->request->getBaseUrl(),'index.php')?'':DIRECTORY_SEPARATOR . 'index.php');
		$this->view->siteBaseUrl = Zend_Registry::get('siteBaseUrl');
		$this->view->moduleBaseUrl = $this->view->appBaseUrl . ($this->request->module == 'default'?'':DIRECTORY_SEPARATOR . $this->request->module);
		$this->view->global = Zend_Registry::get('config')->global;
		$this->dbAdapter = Zend_Registry::get('dbAdapter');
        $this->dbTables = new dbTables();

		require_once 'application/models/admin.php';
		
		$this->admin = new admin();
		
		// check is an XML request or not only allow index to be rendered normally (i.e. full page load)
		if ($this->request->action == 'index' )
		{
			return;
			
		}
			
		if( !isset($this->view->errors) )
		{
			    
			$this->view->errors = array();
			
		}
		if( !isset($this->view->notices) )    
		{
			
			$this->view->notices = array();
			
		}
	 	if( !isset($this->view->jsonIdsToRender) )    
		{
			
			$this->view->jsonIdsToRender = array();
			
		}
		require_once 'library/jQuery/jQuery.php';
		
	}

	public function preDispatch()
	{
		
		// Check the session is active, otherwise redirect to the login page
		$this->view->userData = $this->admin->getCurrentAdminData();
		
		if( empty($this->view->userData) ) 
		{

			$this->_forward( 'timeout', 'index', 'default' );
			return;			

		}

		if( $this->view->userData['UMasterAdmin'] != '1')
		{
			throw new Exception('MasterAdmin rights required.');	
		}
		
		Atmail_Locale::setupLocaleAndTranslation( $this->view->userData['Language'] );
		
	}

	public function indexAction()
	{
	}

	public function globalAction()
	{

		$this->view->global = Zend_Registry::get('config')->global;
		$this->view->dovecot = Zend_Registry::get('config')->dovecot;
		$dbConfig = new Zend_Config_Ini('config/dbconfig.ini', 'production');
		
		$this->view->sql_user = $dbConfig->database->params->username;
		$this->view->sql_table = $dbConfig->database->params->dbname;
		$this->view->sql_host = $dbConfig->database->params->host;
		
		// Discover if the clean-logs.php cron script is in place
		$dailyCronDirs = array("/etc/cron.daily", "/etc/periodic/daily");
		$this->view->cleanLogsScript = "";
		$this->view->dailyCronDir = "";
		foreach ($dailyCronDirs as $cronDir)
		{
			if (is_dir($cronDir))
			{
				$this->view->dailyCronDir = $cronDir;
				if (file_exists("$cronDir/clean-logs.php"))
				{
					$this->view->cleanLogsScript = "$cronDir/clean-logs.php";
					break;
				}
			}
		}
		$this->view->haveMcrypt = function_exists('mcrypt_encrypt') && function_exists('mcrypt_decrypt');
		$this->view->haveMasterKey = isset(Zend_Registry::get('dbConfig')->database->params->masterkey);
	}

	public function globalsaveAction()
	{	
		if( $this->view->global['demo'] == 1 )
			throw new Exception('Disabled in online demo');
        
		//get existing encryption type
		//change password encryption if selected.
        $doPasswordConvert = true;
		$userPasswordEncryptionTypeCurrent = Zend_Registry::get('config')->global['userPasswordEncryptionType'];
		$externalUserPasswordEncryptionTypeCurrent = Zend_Registry::get('config')->global['externalUserPasswordEncryptionType'];
		$userPasswordEncryptionType = isset($this->requestParams['fields']['userPasswordEncryptionType']) ? $this->requestParams['fields']['userPasswordEncryptionType'] : false;
		$externalUserPasswordEncryptionType = isset($this->requestParams['fields']['externalUserPasswordEncryptionType']) ? $this->requestParams['fields']['externalUserPasswordEncryptionType'] : false;
		$master_key = (isset($this->requestParams['fields']['master_key'])) ? $this->requestParams['fields']['master_key'] : (isset(Zend_Registry::get('dbConfig')->database->params->masterkey) ? false : '');
		if(isset($this->requestParams['fields']['master_key']))
		{
			unset($this->requestParams['fields']['master_key']);
		}
		
		if( 
				($userPasswordEncryptionType && $userPasswordEncryptionType == Atmail_Enum::PASSWORD_ENCRYPTED && $userPasswordEncryptionType != $userPasswordEncryptionTypeCurrent)
			||	($externalUserPasswordEncryptionType && $externalUserPasswordEncryptionType == Atmail_Enum::PASSWORD_ENCRYPTED && $externalUserPasswordEncryptionType != $externalUserPasswordEncryptionTypeCurrent) )
		{
			if($master_key == '')
			{
				if(!isset(Zend_Registry::get('dbConfig')->database->params->masterkey))
				{
					throw new Atmail_Config_Exception("No master key has been provided.");
				}
				else
				{
					$master_key = false;
				}
			}
		}
		        
		try
		{
		
			if($this->requestParams['dovecot']['authType'] == 'ldap') 
			{

				// First check, can we connect to LDAP?
				$options = array(
					'host'              => $this->requestParams['dovecot']['ldap_host']
				);

				$ldap = new Zend_Ldap($options);
				
				// Trigger an auth fail, however required to throw an exception if the LDAP server does not connect
				$var = $ldap->bind('atmailtest', 'test');
				$doPasswordConvert = false;
			}
			
		}
		catch( Exception $e )
		{
			
			$error = $e->getMessage();
			
			preg_match('/0x51/', $error, $m);
			
			// Show if the LDAP server fails, network error (0x51)
			if($m[0] == '0x51')
				throw new Exception('LDAP server failed: ' . $e->getMessage());
				
		}
		
		// Else, continue as normal if LDAP or SQL
		
		try
		{
			
			$failure = false;
			require_once 'application/models/config.php';
					
			//if password unchanged then no change
			####如果變量未設置或md5的那值,就取數據庫中的變量
			if( !isset($this->requestParams['fields']['sql_pass']) || $this->requestParams['fields']['sql_pass'] == md5('__UNCHANGED__') )
				$this->requestParams['fields']['sql_pass'] = Zend_Registry::get('config')->global['sql_pass'];
			
			$dbArray = 	array(
						'host'     => $this->requestParams['fields']['sql_host'],
						'username' => $this->requestParams['fields']['sql_user'],
						'password' => $this->requestParams['fields']['sql_pass'],
						'dbname'   => $this->requestParams['fields']['sql_table']
					);
			
			// Attempt connection to SQL server
			require_once('library/Zend/Db/Adapter/Pdo/Mysql.php');
			try
	        {
				
				$db = new Zend_Db_Adapter_Pdo_Mysql($dbArray);
				$db->getConnection();
				
			}
			catch (Exception $e)
			{
			    
				throw new Atmail_Config_Exception("Unable to connect to the provided SQL server with supplied settings");
			    
			}		
			
			config::save( 'global', $this->requestParams['fields'] );
			if( $this->view->global['install_type'] == 'server' )
				config::save( 'dovecot', $this->requestParams['dovecot'] );
					
			// First, update the SQL config files on disk
			config::publishDbConfigChangesToServerConfigFiles( Zend_Registry::get('config')->global );
			
			// publish db config
			if( !config::saveIniChanges(Zend_Registry::get('config')->global, 'database.params') )
				$failure = true;
			if( $this->view->global['install_type'] == 'server' )
			{
			
				// Next, update Dovecot ( LDAP or SQL auth used )
				config::publishServerConfigFiles('dovecot');

				// Required to publish SMTP auth ( LDAP or SQL )
				config::publishServerConfigFiles('exim');
			
				// Which type is the user using, write to the new file
				if($this->request->dovecot['authType'] == 'ldap') 
					config::publishServerConfigFiles('dovecotldap');
				else
					config::publishServerConfigFiles('dovecotsql');
			
			}
			
			if($master_key !== false && $master_key != '')
			{
				// new master key
				// this is used for all session functions if found
				// make sure when this is processed that we update the sessions with encryption
				if(!config::saveIniChanges(array_merge(array('master_key' => $master_key), Zend_Registry::get('config')->global), 'database.params') )
				{
					$failure = true;
				}
				else
				{				
					jQuery::evalScript("$('#userPasswordMasterKey').hide();");

					// configuration has been pushed
					// lets encrypt the sessions
					Atmail_Session_SaveHandler_DbUserSession::enableEncryptedSessions($master_key);
					Atmail_Session_SaveHandler_DbAdminSession::enableEncryptedSessions($master_key);
				}
			}
			
			//change password encryption is changed.
			if( 	$doPasswordConvert
				&&  (
						($userPasswordEncryptionType && $userPasswordEncryptionType != $userPasswordEncryptionTypeCurrent)
					||	($externalUserPasswordEncryptionType && $externalUserPasswordEncryptionType != $externalUserPasswordEncryptionTypeCurrent)
					)
				)
			{

				if(!$failure)
				{
					if($userPasswordEncryptionType && $userPasswordEncryptionType != $userPasswordEncryptionTypeCurrent)
					{
						// changeing user
						users::encryptPasswords( $userPasswordEncryptionTypeCurrent, $userPasswordEncryptionType );						
					}
					if($externalUserPasswordEncryptionType && $externalUserPasswordEncryptionType != $externalUserPasswordEncryptionTypeCurrent)
					{
						// external user update
						users::encryptPasswords( $externalUserPasswordEncryptionTypeCurrent, $externalUserPasswordEncryptionType );						
					}

				}
			}
			// Prompt to save/reset services
			if($failure)
			{
				throw new Atmail_Config_Exception("There was a failure saving your changes");
			}
			else
			{

				// Success!
				jQuery::evalScript("$.simpleFlash('Settings saved. Restart Atmail services for changes to take effect','important');");
			}
		}
		catch( Exception $e )
		{

			jQuery::evalScript("$('#licence-error',  '#global').hide();");
			throw new Exception('Failed saving config: ' . $e->getMessage());	

		}
		$this->render('global/jsonresponse', null, true);
		
	}

	public function webmailAction()
	{

		$this->view->global = Zend_Registry::get('config')->global;
        $groupDefaultResult = $this->admin->groupGet('default');

		if( $groupDefaultResult['status'] == 'success' )
		{
			$this->view->groupDefault = $groupDefaultResult['response']['results'];
		}                                
		else
		{
			throw new Exception( $groupDefaultResult['response']['message'] );
		}
	}

	public function webmailsaveAction()
	{

		if( $this->view->global['demo'] == 1 )
		{

			throw new Exception('Disabled in online demo');

		}
		
		// Turn off SMTP auth user/password if toggled off
		if( empty($this->request->fields['smtp_auth']) )
		{
			$this->request->fields['smtpauth_password'] = "";
			$this->request->fields['smtpauth_username'] = "";			
		}
		
		// If smtp auth is not defined, set as null
		if( empty($this->request->fields['smtpauth_password']) )
		{
			$this->request->fields['smtpauth_password'] = "";
		}

		if( empty($this->request->fields['smtpauth_username']) )
		{
			$this->request->fields['smtpauth_username'] = "";
		}

		// Verify the submitted data// Verify the submitted data
		try
		{
			$smtpHost = $this->request->fields['smtphost'];
			if (empty($smtpHost))
				$smtpHost = 'localhost';
				
			// Try to connect to SMTP host
			require_once('Zend/Mail/Protocol/Smtp/Auth/Login.php');
			require_once('Zend/Mail/Protocol/Smtp.php');

			if ($this->request->fields['smtpauth_username'] != "" && $this->request->fields['smtpauth_password'] != "")
			{
	    			$protocol = new Zend_Mail_Protocol_Smtp_Auth_Login($smtpHost, null, array('username' => $this->request->fields['smtpauth_username'], 'password' => $this->request->fields['smtpauth_password']));
			}
			else
			{
	   			$protocol = new Zend_Mail_Protocol_Smtp($smtpHost);
			}
		
		    $protocol->connect();
		    if(PHP_OS == "Darwin")
		   	{
		   		$protocol->helo(php_uname('n'));
		    }
		    else
		    {
  		   		$protocol->helo($smtpHost);
		    }
		    try{
		        $protocol->mail('root');
		    	$protocol->rcpt('root');
			}
			catch(Zend_Exception $e)
			{
				if(strpos($e->getMessage(), 'need fully-qualified hostname')!=FALSE)
					throw($e);
			}
			if($this->request->fields['remoteServersOverwrite'])
			{
				$this->request->fields['remoteServers'] = '';
			}

			if($this->request->fields['remoteDomainsOverwrite'])
			{
				$this->request->fields['remoteDomains'] = '';
			}
			$this->request->fields['remoteDomains'] = str_replace(" ", "\n", $this->request->fields['remoteDomains']);
			$this->request->fields['remoteServers'] = str_replace(" ", "\n", $this->request->fields['remoteServers']);
			
			//pull out groupDefault data before pushing rest to Config
			$defaultSettings = array();
			if( array_key_exists('Webmail', $this->request->fields) )
			{
				
				$defaultSettings = array('Webmail' => $this->request->fields['Webmail']);
				unset( $this->request->fields['Webmail'] );
				$this->admin->groupUpdate('default', $defaultSettings);
				
			}

			require_once 'application/models/config.php';
			config::save('global', $this->request->fields);
			jQuery::addMessage( $this->view->translate('Settings saved.'));
		
		}
	    catch (Zend_Exception $e)
	    { 	
			$error = $e->getMessage();

	    	if($this->request->fields['smtpauth_username'] != "" && $this->request->fields['smtpauth_password'] != "")
	    	{
	    		$error .= "<br><br>The SMTP authentication details provided are incorrect. Please verify you have the correct username and password. Check your SMTP configuration for further details, or use an SMTP server which you have IP relay permissions.";
	    	}
			
			if(strpos($error, 'need fully-qualified hostname') != FALSE)
			{
				// fully qualified domain name for helo required (probably osx)
				// so lets tell the user in a human readable way
				$error .= "<br><br>The SMTP authentication details provided are incorrect for this server. Please verify you are using the fully qualified hostname for the smtp server. I.e. 'example.hostname' rather then 'localhost'.<br><br>It appears that your machines hostname is : " . php_uname('n');
			}
	
			jQuery::addError('Failed saving config: ' . $error);
		}

		$this->render('global/jsonresponse', null, true);
		
	}

	public function groupwareAction()
	{

		//$this->view->global = Zend_Registry::get('config')->global;
        $groupDefaultResult = $this->admin->groupGet('default');
		
		if( $groupDefaultResult['status'] == 'success' )
		{
			
			$this->view->groupDefault = $groupDefaultResult['response']['results'];
			
		}                                
		else
		{
			
			throw new Exception( $groupDefaultResult['response']['message'] );
			
		}

	}

	public function groupwaresaveAction()
	{
		if( $this->view->global['demo'] == 1 )
		{
			
			throw new Exception('Disabled in online demo');
			
		}
		
		try
		{
		
			//pull out groupDefault data before pushing rest to Config
			$defaultSettings = array();
			if( array_key_exists('GroupwareZone', $this->request->fields) || array_key_exists('Sync', $this->request->fields) )
			{
				
				if( array_key_exists('GroupwareZone', $this->request->fields) )
				{
					
					$defaultSettings['GroupwareZone'] = $this->request->fields['GroupwareZone'];
					unset( $this->request->fields['GroupwareZone'] );
					
				}
				if( array_key_exists('Sync', $this->request->fields) )
				{
					
					$defaultSettings['Sync'] = $this->request->fields['Sync'];
					unset( $this->request->fields['Sync'] );
					
				}
				if( array_key_exists('WebSyncShared', $this->request->fields) )
				{
					
					$defaultSettings['WebSyncShared'] = $this->request->fields['WebSyncShared'];
					unset( $this->request->fields['WebSyncShared'] );
					
				}
				if( array_key_exists('WebSyncGlobal', $this->request->fields) )
				{
					
					$defaultSettings['WebSyncGlobal'] = $this->request->fields['WebSyncGlobal'];
					unset( $this->request->fields['WebSyncGlobal'] );
					
				}
				if( array_key_exists('SharedAbook', $this->request->fields) )
				{
					
					$defaultSettings['SharedAbook'] = $this->request->fields['SharedAbook'];
					unset( $this->request->fields['SharedAbook'] );
					
				}
				if( count($defaultSettings) > 0 )
				{
					
					$this->admin->groupUpdate('default', $defaultSettings); 
				}
				
			}
			
			//require_once 'application/models/config.php';
			//config::save('global', $this->request->fields);
			
			jQuery::addMessage( $this->view->translate('The settings have been updated.') );
		
		}
		catch( Exception $e )
		{
		
			jQuery::addMessage('Failed saving config: ' . $e->getMessage());
		
		}

		$this->render('global/jsonresponse', null, true);
		
	}
	
	public function brandingAction()
	{
		
		$this->view->global = Zend_Registry::get('config')->global;
		
	}

	public function brandingsaveAction()
	{
		if( $this->view->global['demo'] == 1 )
		{
			
			throw new Exception('Disabled in online demo');
			
		}
		
		// Set the following fields as blank in the DB if the admin specifies
		foreach(array('footer_msg', 'welcome_msg', 'webmail_url', 'mailserver_hostname') as $field)
		{
		
			if(!isset($this->request->fields[$field]))
			{
		
				$this->request->fields[$field] = "";
		
			}
		
		}

		try
		{
			require_once 'application/models/config.php';
			config::save('global', $this->request->fields);
			jQuery::addMessage( $this->view->translate('The settings have been updated.') );
		
		}
		catch( Exception $e )
		{
			
			jQuery::addMessage('Failed saving config: ' . $e->getMessage());
			
		}

		$this->render('global/jsonresponse', null, true);
		
	}

	public function defaultsAction()
	{
		
		// Load the languages available
		$this->view->localeString = $this->view->UserSettings['Language'];
		$this->view->availableLocales = Atmail_Locale::getAvailableLocales();
		
		$this->view->defaultUserSettings = Zend_Registry::get('config')->defaultUserSettings;
		$this->view->global = Zend_Registry::get('config')->global;
		
		require_once 'application/models/calendar.php';
		
		$tzList =  calendar::getZoneInfoList();
		$this->view->timezoneList = '';
		
		foreach($tzList as $tz)
		{
			$this->view->timezoneList .= '<option value="' . $tz . '">' . $tz . '</option>';
		}
		
		//get list of available themes
		$this->view->availableThemes = findAvailableThemes();
		
	}

	public function defaultssaveAction()
	{
		
		if( $this->view->global['demo'] == 1 )
		{
			
			throw new Exception('Disabled in online demo');
			
		}

		// Set the following fields as blank in the DB if the admin specifies
		foreach(array('CalDavUrl', 'CardDavUrl') as $field)
		{
			
			if( !isset($this->request->fields[$field]) )
			{
			
				$this->request->fields[$field] = "";
			
			}
			
		}

		if( empty($this->request->fields['ViewThreads']) )
		{
			
			$this->request->fields['ViewThreads'] = 0;
			
		}
		
		//process and remove themeEnabled saving to global
		require_once 'application/models/config.php';
		if( isset($this->requestParams['fields']['themeEnabled']) )
		{
			
			try
			{

				config::save('global', array('themeEnabled' => $this->requestParams['fields']['themeEnabled']) );
				
				//reset existing user themes if changing is disabled
				if( (String)$this->requestParams['fields']['themeEnabled'] === '0' )
				{
					
					$this->dbAdapter->update($this->dbTables->UserSettings, array('cssStyleTheme' => $this->requestParams['fields']['cssStyleTheme']) );
					
				}
			    unset($this->requestParams['fields']['themeEnabled']);
				
			}
			catch( Exception $e )
			{

				jQuery::addMessage('Failed saving themeEnabled: ' . $e->getMessage());
                $this->render('global/jsonresponse', null, true);
				return;
				
			}

		}

		try
		{
		
			config::save('defaultUserSettings', $this->requestParams['fields']);
			jQuery::addMessage( $this->view->translate('The settings have been updated.') );
		
		}
		catch( Exception $e )
		{
			
			jQuery::addMessage('Failed saving config: ' . $e->getMessage());
			
		}

		$this->render('global/jsonresponse', null, true);
		
	}

        public function ldapAction()
        {

                $this->view->global = Zend_Registry::get('config')->global;

        }

        public function ldapsaveAction()
        {

                if( $this->view->global['demo'] == 1 )
                {

                        throw new Exception('Disabled in online demo');

                }

                try
                {

                        require_once 'application/models/config.php';
                        config::save('global', $this->request->fields);
                	config::publishServerConfigFiles('dovecotldap');
		        jQuery::addMessage( $this->view->translate('The LDAP settings have been updated.') );

                }
                catch( Exception $e )
                {

                        jQuery::addMessage('Failed saving config: ' . $e->getMessage());

                }

                $this->render('global/jsonresponse', null, true);

        }

	public function uploadlogoAction()
	{
		
		if( $this->view->global['demo'] == 1 )
			throw new Exception('Disabled in online demo');
		
		$filename = APP_ROOT . "images/themes/Blue-Steel/logo.png";
		$result = move_uploaded_file($_FILES['Logo']['tmp_name'], $filename);
		if( $result !== false )
		{
			
			$filename2 = APP_ROOT . "images/themes/Granite/logo.png";
			$result = copy($filename, $filename2);
			if( $result !== false )
				jQuery::addMessage( $this->view->translate('The branding logo has been updated.') );
		
		} 
		else 
		{
			
			// TODO: Throw exception
			jQuery::addMessage('Failed saving branding logo: ' . $e->getMessage());
			
		}
                $this->render('global/jsonresponse', null, true);
	}
	
}
