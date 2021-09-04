<?php
/**
 * @category 	Atmail
 * @package 	Models
 * @subpackage	Users
 * @copyright 	Copyright (c) 2009-2011 ATMAIL. All rights reserved
 * @license 	See http://atmail.com/license.php for license agreement
 * @author		Allan Wrethman allan@staff.atmail.com
 * @author		Atmail (http://atmail.com)
 */

require_once 'Atmail/Password.php';
require_once 'Atmail/Enum.php';

class users
{
	public function encryptPasswords( $currentType, $newType ) 
	{
		$cryptFunction = false;
		$externalUsersOnly = false;

		if( $newType == Atmail_Enum::PASSWORD_PLAIN )
		{
			// we are decrypting
			// but only if our current type is two way
			if($currentType == Atmail_Enum::PASSWORD_ENCRYPTED)
			{
				$cryptFunction = 'hexaes_decrypt';
				$externalUsersOnly = true;
			}
			else
			{
				return false;	
			}
		}
		else
		{		
			switch($newType)
			{
				case Atmail_Enum::PASSWORD_ENCRYPTED:
				{
					$cryptFunction = 'hexaes_encrypt';
					$externalUsersOnly = true;
				} break;
				
				case Atmail_Enum::PASSWORD_MD5:
				{
					$cryptFunction = 'md5';
				} break;	
	
				case Atmail_Enum::PASSWORD_MD5_CRYPT:
				{
					$cryptFunction = 'crypt';
				} break;	
			}	
		}
		
		if ($currentType == $newType)
		{
			return true;
		}
		
		$dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter'); 
		$allUsers = $dbAdapter->select()
							->from($dbTables->UserSession, array('Account', 'Password'))
							->query()
							->fetchAll();

		foreach( $allUsers as $user )
		{
			if($cryptFunction !== false)
			{
				if(strpos($cryptFunction, 'hexaes_') !== false)
				{
					$user['Password'] = Atmail_Password::$cryptFunction($user['Password']);
				}
				else
				{
					$user['Password'] = $cryptFunction($user['Password']);
				}
			}
			$user['SessionData'] = null;

			$externalUser = self::isExternal($user['Account']);
			if(($externalUsersOnly && $externalUser) || (!$externalUsersOnly && !$externalUser))
			{
				$dbAdapter->update($dbTables->UserSession, $user, $dbAdapter->quoteInto('Account = ?', $user['Account']) );
			}
		}					
	}
	
	public static function getUserMailFolder( $mailbox )
	{

		$globalConfig = Zend_Registry::get('config')->global;
		if( !isset($globalConfig['usersFolderBaseName']) )
			throw new Atmail_Mail_Exception('Compulsory usersFolderBaseName not found in Config');
		
		$usersFolderBaseName = rtrim($globalConfig['usersFolderBaseName'], '/'); //get user folder base name with trailing / trimmed
		if (preg_match('/^([A-Z]{1})/i', $mailbox, $m))
			$accountFirstLetter = $m[1];
		else
			$accountFirstLetter = 'other';
        if (preg_match('/^.{1}([A-Z]{1})/i', $mailbox, $m))
			$accountSecondLetter = $m[1];
		else
			$accountSecondLetter = 'other';
		$userFolder = $usersFolderBaseName . DIRECTORY_SEPARATOR . $accountFirstLetter . DIRECTORY_SEPARATOR . $accountSecondLetter . DIRECTORY_SEPARATOR . $mailbox;
		if( is_dir($userFolder) )
			return $userFolder;
		else
			return false;

	}
	
	/** 
	 * @returns user tmp folder name, (Config) tmpFolderBaseName . (FS Safe) Account
	 */
	public static function getTmpFolder( $subFolder = '', $user = null )
	{
	   	
		$globalConfig = Zend_Registry::get('config')->global;
		if( !isset($globalConfig['tmpFolderBaseName']) )
		{
			
			throw new Atmail_Mail_Exception('Compulsory tmpFolderBaseName not found in Config');
			
		}
		###$tmp_dir=tmp/
		$tmp_dir = $globalConfig['tmpFolderBaseName'];
		$userData = null;
		if($user == null)
		{
			$userData = Zend_Auth::getInstance()->getStorage()->read();
			if(is_array($userData) && isset($userData['user']))
			{
				$safeUser = simplifyString($userData['user']);
			}
			else
			{
				// something went wrong.
				// return global temp directory
				return APP_ROOT . 'tmp/';
			}
		}
		else
		{
		    ###$safeUser=adminoffseclocal
			$safeUser = simplifyString($user);
		}
		##accountFirstLetter = a
		$accountFirstLetter = $safeUser[0];
		##accountSecondLetter = d
		$accountSecondLetter = $safeUser[1];
		$range = range('a,','z');
		if(!in_array($accountFirstLetter, $range))
		{
			$accountFirstLetter = 'other';
		}
		
		if(!in_array($accountSecondLetter, $range))
		{
			$accountSecondLetter = 'other';
		}
		##如果/usr/local/atmail/webmail/tmp/非存在,就將tmp改為空
		if( !is_dir(APP_ROOT . $tmp_dir) )
			$tmp_dir = '';
        ###tmp/a/
		$tmp_dir .= $accountFirstLetter . DIRECTORY_SEPARATOR;
        ###如果/usr/local/atmail/webmail/tmp/a/非存在,就創建資料夾/usr/local/atmail/webmail/tmp/a/
		if( !is_dir(APP_ROOT . $tmp_dir) )
		{
			
			@mkdir(APP_ROOT . $tmp_dir); 
			if( !is_dir(APP_ROOT . $tmp_dir) )
				throw new Exception('Failure creating folders in tmp directory. Web server user must own ' . $tmp_dir . ' and sub folders and have access permissions');
			
		}
		###tmp/a/d/
		$tmp_dir .= $accountSecondLetter . DIRECTORY_SEPARATOR;
		if( !is_dir(APP_ROOT . $tmp_dir) )
		{
        ###如果/usr/local/atmail/webmail/tmp/a/d/非存在,就創建資料夾/usr/local/atmail/webmail/tmp/a/d/
			@mkdir(APP_ROOT . $tmp_dir);
			if( !is_dir(APP_ROOT . $tmp_dir) )
				throw new Exception('Failure creating folders in tmp directory. Web server user must own ' . $tmp_dir . ' and sub folders and have access permissions');
			
		}
		###tmp/a/d/adminoffseclocal/
		$tmp_dir .= $safeUser . DIRECTORY_SEPARATOR;
		if( !is_dir(APP_ROOT . $tmp_dir) )
		{
		 ###如果/usr/local/atmail/webmail/tmp/a/d/adminoffseclocal非存在,就創建資料夾/usr/local/atmail/webmail/tmp/a/d/adminoffseclocal
			@mkdir(APP_ROOT . $tmp_dir);
			if( !is_dir(APP_ROOT . $tmp_dir) )
				throw new Exception('Failure creating folders in tmp directory. Web server user must own ' . $tmp_dir . ' and sub folders and have access permissions');
			
		}
		
		if( $subFolder != '' ) 
		{

			$tmp_dir .= $subFolder . DIRECTORY_SEPARATOR;
			if( !is_dir(APP_ROOT . $tmp_dir) )
			{
			
				@mkdir(APP_ROOT . $tmp_dir);
				if( !is_dir(APP_ROOT . $tmp_dir) )
					throw new Exception('Failure creating folders in tmp directory. Web server user must own ' . $tmp_dir . ' and sub folders and have access permissions');
				
			}

		}
		if( is_dir(APP_ROOT . $tmp_dir) )
			return $tmp_dir;
		else
			throw new Exception('Unable to create tmp user folder (check correct permissions for tmp folders): ' . $tmp_dir);

	}
	
	public static function getList($sort = null, $order = 'ASC', $limit = 0, $offset = 0) 
	{
	
	    $dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter'); 
		$select = $dbAdapter->select()
							->from($dbTables->Users)
							->join($dbTables->UserSession, "{$dbTables->Users}.Account = {$dbTables->UserSession}.Account")
							->order("{$dbTables->Users}.Account ASC");
		//TODO: sort, order, limit and offset
		$query = $select->query();
		return $query->fetchAll();
	}
	
	public static function getDomainUsers( $domain )
	{
		
		$dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter'); 
		$select = $dbAdapter->select()
							->from($dbTables->Users, array('Account') )
							->where('Account LIKE ?', '%@' . $domain)
							->order("Account ASC");
		return $dbAdapter->fetchAll( $select );
		
	}
	
	public static function getGroupUsers( $groupName, $sorted = true )
	{
		
		$dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter'); 
		$select = $dbAdapter->select()
							->from($dbTables->Users, array('Account') )
							->where('Ugroup = ?', $groupName );
		if( $sorted )
		{
		
			$select->order("Account ASC");
			
		}
		return $dbAdapter->fetchAll( $select );
		
	}
	
	public function getAccountById($id) {
		if( $id == null )
			throw new Atmail_Mail_Exception(__METHOD__ . ' must be called with valid arguments');
		
	    $dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$select = Zend_Registry::get('dbAdapter')->select()
												 ->from($dbTables->Users)
												 ->where('id = ' . $dbAdapter->quote($id));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		
		$rows = $result->fetchAll();
		
		if( count($rows) != 1)
			throw new Atmail_Mail_Exception('Single match for User id was not found.');
		else
			return $rows[0]['Account'];
	}
	
	public function changeAccountId($account, $newaccount)
	{
		if($account == $newaccount)
			return true;
		
		if( $account == null || $newaccount == null || $account == '' || $newaccount == '' )
			throw new Atmail_Mail_Exception(__METHOD__ . ' must be called with valid arguments');
		
		// generate maildir paths
		$currentMaildir = users::getMaildir($account);
		$newMaildir = users::getMaildir($newaccount);
		
	    $dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter');
		
		$tablesToModifyFields = array(
			'UserSession' => array('SessionData' => 'NULL'),
			'UserSession' =>  array('SessionID' => 'NULL'),
			'Users' => array('MailDir' => $newMaildir)
		);
		
		$tablesToUpdateFields = array(
			'Abook' => array('Account', 'UserEmail', 'UserEmail2', 'UserEmail3', 'UserEmail4', 'UserEmail5') ,
			'AbookGroup' => 'Account',
			'AbookGroupNames' => 'Account',
			'AbookPermissions' => 'Account',
			'AbookServers' => 'Account',
			'Log_Error' => 'Account',
			'Log_Login' => 'Account',		
			'Log_RecvMail' => array( 'Account', 'EmailFrom'),
			'Log_SendMail' => array( 'Account', 'EmailTo'),
			'Log_Spam' => array('Account', 'EmailFrom'),
			'Log_Virus' => array('Account', 'EmailFrom'),
			'MailAliases' => 'AliasTo',
			'SerialConf' => 'Account',
			'SharedLookup' => 'Account',
			'SpamSettings' => 'username',
			'UserSession' => 'Account',
			'UserSettings' => 'Account',
			'Users' => 'Account',
			'awl' => array('username', 'email'),
			'calendarExtendedData' => 'Account',
			'calendars' => array('principaluri', 'type' => 'like', 'where' => $dbAdapter->quote('%/' . $account)),
			'principals' => array(
								array('email', 'type' => 'like', 'where' => $dbAdapter->quote($account)),
								array('uri', 'type' => 'like', 'where' => $dbAdapter->quote('%/' . $account))
								)
		);
		
		// Start a transaction explicitly.
		$db = Zend_Registry::get('dbAdapter');
				
		$result = $db->query("select * from UserSession where Account = " . $dbAdapter->quote($newaccount) )->fetchAll();
		if(count($result))
		{
			// account exists already
			return "Duplicate Account";
		}

		$db->beginTransaction();
		
		try
		{
			foreach($tablesToModifyFields as $table => $fields)
			{	
				$fields = (!is_array($fields) ? array($fields) : $fields);
				foreach($fields as $field => $value)
				{
					$query = 'update ' . $table . ' set ' . $field . ' = ' . $dbAdapter->quote($value) . ' where Account = ' . $dbAdapter->quote($account);
					Zend_Registry::get('dbAdapter')->query($query);
				}
			}
			
			foreach($tablesToUpdateFields as $table => $fields)
			{	
				$fields = (!is_array($fields) ? array($fields) : $fields);
				$where = $dbAdapter->quote($account);
				$type = '=';
				if(isset($fields['where']))
				{
					$where = $fields['where'];
					unset($fields['where']);	
				}
				if(isset($fields['type']))
				{
					$type = $fields['type'];
					unset($fields['type']);	
				}
				foreach($fields as $innerFields)
				{
					$innerFields = (!is_array($innerFields) ? array($innerFields) : $innerFields);
					if(isset($innerFields['where']))
					{
						$where = $innerFields['where'];
						unset($innerFields['where']);	
					}
					if(isset($innerFields['type']))
					{
						$type = $innerFields['type'];
						unset($innerFields['type']);	
					}
					foreach($innerFields as $innerField)
					{
						$query = 'update ' . $table . ' set ' . $innerField . ' = replace('. $innerField . ',' . $dbAdapter->quote($account) . ',' . $dbAdapter->quote($newaccount) . ') where ' . $innerField . ' ' . $type . ' ' . $where;
						Zend_Registry::get('dbAdapter')->query($query);
					}
				}
			}

		    // If all succeed, commit the transaction and all changes
			// are committed at once.
    		$db->commit();
		}
		catch(Exception $e)
		{
			$db->rollBack();
			return $e->getMessage();	
		}
	
		// success!
		if($currentMaildir != '' && is_dir($currentMaildir))
		{
			// move the maildir over
			while($currentMaildir != $newMaildir)
			{
				if(!@rename($currentMaildir, $newMaildir)) break;
				$currentMaildir = dirname($currentMaildir);
				$newMaildir = dirname($newMaildir);
			}
		}
		return true;
	}
		
	public static function get( $account ) 
	{
	
		if( $account == null )
		{
			
			throw new Atmail_Mail_Exception(__METHOD__ . ' must be called with valid arguments');
			
		}
		
	    $dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$select = $dbAdapter->select()
							->from($dbTables->Users)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		
		$rows = $result->fetchAll();
		
		if( count($rows) != 1)
		{
			
			throw new Atmail_Mail_Exception('Single match for user was not found.');
			
		}
		else
		{
			
			return $rows[0];
			
		}
			
	}
	
	public static function set( $account, $row )
	{
		
		$allowedFields = array( 'PasswordQuestion', 'Ugroup', 'UserStatus', 'MailDir', 'Forward', 'AutoReply', 'UserQuota');
		foreach( $row as $k => $v )
		{
			
			if( in_array($k, $allowedFields) )
			{
				
				$filteredRow[$k] = $v;
				
			}
			
		}
		$row = $filteredRow;
		$dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$where = $dbAdapter->quoteInto('Account = ?', $account);
		$rowsAffectedNumber = $dbAdapter->update( $dbTables->Users, $row, $where);
		
	}
		
	public function getUserQuota($account)
	{
		// Find the original users disk quota
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$quota = $dbAdapter->fetchOne("select UserQuota from Users where Account=" . $dbAdapter->quote($account));
		return $quota;
	}
	
	public function describeAllUserFields() //returns table base names as keys field names as subarray keys and default values as values
	{
		$userDescription = array( 'UserSession' => array(), 'Users' => array(), 'UserSettings' => array() );
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$default = Zend_Registry::get('config')->defaultUserSettings;
		$dbTables = new dbTables();
		$userDescription['UserSession'] = $dbAdapter->describeTable($dbTables->UserSession);
		foreach( $userDescription['UserSession'] as $key => $field)
			$userDescription['UserSession'][$key]['DEFAULT'] = isset($default[$field['COLUMN_NAME']])?$default[$field['COLUMN_NAME']]:'';
		
		$userDescription['Users'] = $dbAdapter->describeTable($dbTables->Users);
		foreach( $userDescription['Users'] as $key => $field)
			$userDescription['Users'][$key]['DEFAULT'] = isset($default[$field['COLUMN_NAME']])?$default[$field['COLUMN_NAME']]:'';
		
		$userDescription['UserSettings'] = $dbAdapter->describeTable($dbTables->UserSettings);
		foreach( $userDescription['UserSettings'] as $key => $field)
			$userDescription['UserSettings'][$key]['DEFAULT'] = isset($default[$field['COLUMN_NAME']])?$default[$field['COLUMN_NAME']]:'';
		
		return $userDescription;
	}

	public static function getUserSettings($account=false)
	{
		if( $account == false )
		{
			throw new Atmail_Mail_Exception(__METHOD__ . ' Invalid argument ');	
		}
		
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$dbTables = new dbTables();
		$select = $dbAdapter->select()
							->from($dbTables->UserSettings)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$settings = $result->fetch();
		
		if (!is_array($settings)) {
			throw new Exception("No user settings found for $account");
		}
		
		return $settings;
	}
	
	
	public static function &getAllUserData( $account = false ) 
	{
	
		if( $account == false )
		{
			
			throw new Atmail_Mail_Exception(__METHOD__ . ' Invalid argument ');
			
		}
		
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$dbTables = new dbTables();
		//if edit called then allow id to come from GET, else only accept from POST to reduce risk of abuse
		$select = $dbAdapter->select()
							->from($dbTables->Users)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UsersRows = $result->fetchAll();
		
		if(count($UsersRows) != 1)
		{
			
			throw new Atmail_Mail_Exception('Single match for Users was not found.');
			
		}
		
		$select = $dbAdapter->select()
							->from($dbTables->UserSession)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UserSessionRows = $result->fetchAll();
		
		if( count($UserSessionRows) != 1)
		{
			
			throw new Atmail_Mail_Exception('Single match for UserSession was not found.');
			
		}
		
		$select = $dbAdapter->select()
							->from($dbTables->UserSettings)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UserSettingsRows = $result->fetchAll();
		
		if( count($UserSettingsRows) != 1)
		{
			
			throw new Atmail_Mail_Exception('Single match for ' . $dbTables->UserSettings . ' was not found.');
			
		}
		
		$userData = array( 'UserSession' => $UserSessionRows[0], 
					  	   'Users' => $UsersRows[0], 
					  	   'UserSettings' => $UserSettingsRows[0] );
		
		$userData['UserSession'] = Atmail_Password::processUser($userData['UserSession']);
		
		$groupInfo = groups::get( $UsersRows[0]['Ugroup'] ); //will return false if not found
		if( $groupInfo !== false )
			$userData['Groups'] = $groupInfo;
		else
		{
		
			//TODO: move house keeping into webadmin login warning screen
			//if group not found do some house keeping
			$result = $dbAdapter->update( $dbTables->Users, array('Ugroup' => 'default'), $dbAdapter->quoteInto('Account = ?', $account) );
			$groupDefaultInfo = groups::get( 'default' );
			if( $groupDefaultInfo === false )
				throw new Exception('Default group not found');
			$userData['Groups'] = $groupDefaultInfo;
		
		}

		return $userData;

	}
	
	public static function add($account, $userArray) {
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$dbTables = new dbTables();
		$eximConfig = Zend_Registry::get('config')->exim;
		
		//try find existing Account
		$select = $dbAdapter->select()
							->from($dbTables->Users)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UsersRows = $result->fetchAll();
		if( count($UsersRows) > 0)
			throw new Atmail_Exception($account . ' already exists in Users.');
		
		$select = $dbAdapter->select()
							->from($dbTables->UserSession)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UsersRows = $result->fetchAll();
		if( count($UsersRows) > 0)
			throw new Atmail_Mail_Exception($account . ' already exists in UserSession.');
		
		$select = $dbAdapter->select()
							->from($dbTables->UserSettings)
							->where('Account = ' . $dbAdapter->quote($account));
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$result = $select->query();
		$UsersRows = $result->fetchAll();
		if( count($UsersRows) > 0)
			throw new Atmail_Mail_Exception($account . ' already exists in ' . $dbTables->UserSettings);
				
		//add blank records and then update records with save method
		try { 
			$domain = explode('@', $account);
			if(!isset($domain[1]))
			{
				// no domain for this user
				$domain[1] = '';
			}
		    $dbAdapter->beginTransaction(); 
			$dbAdapter->insert( $dbTables->UserSession, array('Account' => $account, 'lifetime' => 120) );
			$dbAdapter->insert( $dbTables->Users, array( 'Account' => $account, 'DateCreate' => new Zend_Db_Expr('NOW()') ) );
			$dbAdapter->insert( $dbTables->UserSettings,  array('Account' => $account) );
			// Insert Spam settings
			// TODO: Check if local domain
			$dbAdapter->insert( $dbTables->SpamSettings, array('username' => $account, 'preference' => 'required_score', 'value' => $eximConfig['filter_required_hits'], 'domain' => $domain[1]) );
			$dbAdapter->insert( $dbTables->SpamSettings, array('username' => $account, 'preference' => 'rewrite_header', 'value' => 'subject ' . $eximConfig['filter_subject_tag'], 'domain' => $domain[1]) );
			$dbAdapter->insert( $dbTables->SpamSettings, array('username' => $account, 'preference' => 'spam_treatment', 'value' => $eximConfig['filter_spam_treatment'], 'domain' => $domain[1]) );
			$dbAdapter->insert( $dbTables->SpamSettings, array('username' => $account, 'preference' => 'report_safe', 'value' => '1', 'domain' => $domain[1] ));

			$dbAdapter->commit(); 
		}
		catch (Exception $e) 
		{ 
		
		    $dbAdapter->rollBack(); 
		    throw new Atmail_Exception('Add user failed' .$e->getMessage()); 
		
		}
		
		self::saveAllUserData($account, $userArray);
		
	}
	
	public static function saveAllUserData($oldAccount, $updatedUserArray) 
	{
	
		if( $oldAccount == null )
		{
			
			throw new Atmail_Mail_Exception(__METHOD__ . ' Invalid argument: ' . $oldAccount);
			
		}

		$dbAdapter = Zend_Registry::get('dbAdapter');
        $dbTables = new dbTables();
        
		//prepare arrays of fields we will allow to be updated (usually same as was sent to the form)
		$oldUserArray = &self::getAllUserData($oldAccount);

		// to handle changes to UserSession['Account'] also being updated in dependant tables
		$newAccount = $oldAccount; 
		try 
		{
		
			$dbAdapter->beginTransaction(); 
			if( isset($updatedUserArray['UserSession']) && is_array($updatedUserArray['UserSession']) )
			{
			
				$newRecord = &intersectArray( $oldUserArray['UserSession'], $updatedUserArray['UserSession'] );
				if (Zend_Registry::get('config')->global['externalUserPasswordEncryptionType'] == Atmail_Enum::PASSWORD_ENCRYPTED && users::isExternal($oldAccount))
				{
					if (isset($newRecord['Password'])) $newRecord['Password'] = Atmail_Password::hexaes_encrypt($newRecord['Password']);
				}
				$result = $dbAdapter->update( $dbTables->UserSession, $newRecord, $dbAdapter->quoteInto('Account = ?', $oldAccount) );
			
				//pass changes to Account down to remaining updates
				if( isset($newRecord['Account']) && $newRecord['Account'] != $oldAccount )
					$newAccount = $newRecord['Account'];  
				//if Account has changed ignore Account update if UserSession has not also been updated
			
			}
		
			if( isset($updatedUserArray['Users']) &&  is_array($updatedUserArray['Users']) )
			{

				//allow db to automatically update data modified
				unset( $oldUserArray['Users']['DateModified'] );
				//prevent modification of DateCreate
				unset( $oldUserArray['Users']['DateCreate'] );
				//$newRecord = &intersectArray( $oldUserArray['Users'], $updatedUserArray['Users'] );
                $newRecord = $updatedUserArray['Users'];

				if( $newAccount != $oldAccount || isset($newRecord['Account']) ) // double check to enforce Account id not corrupted
					$newRecord['Account'] = $newAccount;
					
				$result = $dbAdapter->update( $dbTables->Users, $newRecord, $dbAdapter->quoteInto('Account = ?',$oldAccount) );

			}
		          
			if( isset($updatedUserArray['UserSettings']) && is_array($updatedUserArray['UserSettings']) ) 
			{

				$newRecord = $updatedUserArray['UserSettings'];
				
				// Table name is dependant on Account first character so delete old record from old table and add to new table
				$newRecord['Account'] = $newAccount;
				$result = $dbAdapter->update( $dbTables->UserSettings, $newRecord, $dbAdapter->quoteInto('Account = ?',$oldAccount) );
            
			}
			$dbAdapter->commit();

		} 
		catch (Exception $e) 
		{ 
		
		    $dbAdapter->rollBack(); 
		    throw new Atmail_Mail_Exception('Update user failed' .$e->getMessage()); 
		
		}
		
	}
	
	public static function deleteUser($account) 
	{

		//API should use this method
		if( $account == null )
			throw new Atmail_Mail_Exception(__METHOD__ . ' Invalid argument: ' . $account);

		$dbAdapter = Zend_Registry::get('dbAdapter');
		require_once 'dbTables.php';
		$dbTables = new dbTables();
		
		// Step 1. Remove data in SQL and related calendar tables
		try 
		{
			// Address Book
			$dbAdapter->delete( $dbTables->Abook, $dbAdapter->quoteInto('Account = ?', $account) );
			$dbAdapter->delete( $dbTables->AbookGroup, $dbAdapter->quoteInto('Account = ?', $account) );
			$dbAdapter->delete( $dbTables->AbookGroupNames, $dbAdapter->quoteInto('Account = ?', $account) );
			$dbAdapter->delete( $dbTables->AbookPermissions, $dbAdapter->quoteInto('Account = ?', $account) );
			$dbAdapter->delete( $dbTables->AbookServers, $dbAdapter->quoteInto('Account = ?', $account) );

			// Calendars
			$dbAdapter->delete( $dbTables->calendarExtendedData, $dbAdapter->quoteInto('Account = ?',$account) );
            $dbAdapter->query("DELETE FROM calendarObjects USING calendarObjects LEFT JOIN calendars ON calendars.id = calendarObjects.calendarid WHERE principaluri = ?", array("principals/users/" . $account));
            $dbAdapter->query("DELETE FROM calendarDelegates USING calendarDelegates LEFT JOIN calendars ON calendars.id = calendarDelegates.calendarid WHERE principaluri = ?", array("principals/users/" . $account));
            $dbAdapter->query("DELETE FROM calendars WHERE principaluri = ?", array("principals/users/" . $account));
            $dbAdapter->query("DELETE FROM calendarDelegates USING calendarDelegates LEFT JOIN principals ON calendarDelegates.principalid = principals.id WHERE principals.uri = ?", array("principals/users/" . $account));
            $dbAdapter->query("DELETE FROM principals WHERE uri = ?", array("principals/users/" . $account));

			// User/Session/Settings
			$dbAdapter->delete( $dbTables->UserSession, $dbAdapter->quoteInto('Account = ?',$account) );
			$dbAdapter->delete( $dbTables->Users, $dbAdapter->quoteInto('Account = ?',$account) );
			$dbAdapter->delete( $dbTables->UserSettings, $dbAdapter->quoteInto('Account = ?',$account) );
			$dbAdapter->delete( $dbTables->SerialConf, $dbAdapter->quoteInto('Account = ?',$account) );
			$dbAdapter->delete( $dbTables->SpamSettings, $dbAdapter->quoteInto('username = ?',$account) );
		}
		catch (Exception $e) 
		{ 

		    throw new Atmail_Mail_Exception('Delete user failed ' .$e->getMessage()); 

		}
		
		// Step 2. Remove tmp files on disk and maildir
		// Remove maildir
		if( @is_dir(users::getMaildir($account)) )
			deleteDirectory(users::getMaildir($account));

		// Remove users webmail tmp dir
		if(@is_dir(users::getTmpFolder('', $account)))
		{
			deleteDirectory(users::getTmpFolder('',$account));
		}
		
		if(@is_dir(realpath(users::getTmpFolder($account, $account))))
		{
			deleteDirectory(realpath(users::getTmpFolder($account, $account)));
		}
		
	}
	
	public function generateMailDir($name = false, $usersFolderBaseName = false) 
	{
		
		$maildir = self::getMaildir($name, $usersFolderBaseName);
		if( !is_dir($maildir) )
		{
			
			if( !@mkdir($maildir, 0700, true) )
			{
				
				throw new Exception('Failed creating base maildir directory. Are permissions set properly?');
				
			}
			
		}        
		chown($maildir, "atmail");
		
		return $maildir;

	}
	
	public function getMaildir($name = false, $usersFolderBaseName = false)
	{
		
		if( $name == false )
		{
			
			throw new Exception('Name argument missing');
			
		}
			
		if( $usersFolderBaseName == false )
		{
			
			$usersFolderBaseName = Zend_Registry::get('config')->global['usersFolderBaseName'];
			
		}
		
		if (preg_match('/^([A-Z])/i', $name, $m))
		{
			
			$first = $m[1];
			
		}
		else
		{
			
			$first = 'other';
			
		}

		if (preg_match('/^[A-Z]([A-Z])/i', $name, $m))
		{
			
			$second = $m[1];
			
		}
		else
		{
			
			$second = 'other';
			
		}
			
		return $usersFolderBaseName . $first . '/' . $second . '/' . $name . '/';
		
	}
	
	public function generateCalendarDir($name = false, $usersFolderBaseName = false) {
		
		if( $name == false)
			throw new Exception('Name argument missing');
			
		if( $usersFolderBaseName == false )
			$usersFolderBaseName = '/usr/local/atmail/calendarserver/server/twistedcaldav/atmail/data/calendars/__uids__/';
					
		$caldir = $usersFolderBaseName . $name[0] . $name[1] . '/' . $name[2] . $name[3] . '/' . str_replace('@', '_', $name) . '/';
		
		return $caldir;

	}
	
	public static function changePassword($account, $newPassword) 
	{
		
		$dbAdapter = Zend_Registry::get('dbAdapter');
        $dbTables = new dbTables();

		//crypt Password field if crypt enabled
		if(users::isExternal($account)) return false;

		if( Zend_Registry::get('config')->global['userPasswordEncryptionType'] == Atmail_Enum::PASSWORD_MD5_CRYPT )
		{
			$newPassword = crypt($newPassword);
		}
		elseif( Zend_Registry::get('config')->global['userPasswordEncryptionType'] == Atmail_Enum::PASSWORD_MD5 )
		{
			$newPassword = md5($newPassword);
		}
		return $dbAdapter->update( $dbTables->UserSession, array('Password' => $newPassword), $dbAdapter->quoteInto('Account = ?',$account) );
		
	}
	
	public function getSpamSettings($account)
	{
		try
		{
			$dbAdapter = Zend_Registry::get('dbAdapter');
        		$dbTables = new dbTables();
			$select = $dbAdapter->select()
							->from($dbTables->SpamSettings)
							->where("username = " . $dbAdapter->quote($account));

			$query = $select->query();
			$rows = $query->fetchAll();
		}
		catch (Exception $e)
		{
			return array();	
		}

		$spamSettings = array();
		
		foreach(array_keys($rows) as $row)
		{
			$name = $rows[$row]['preference'];
			$value = $rows[$row]['value'];
			
			if($name == 'blacklist_from' || $name == 'whitelist_from')
			$spamSettings[$name][] = $value;
			else
			$spamSettings[$name] = $value;	
		}
		
		// Cleanup some fields
		if(!empty($spamSettings['rewrite_header']))
			$spamSettings['rewrite_header'] = str_replace('subject ', '', $spamSettings['rewrite_header']);
		
		if(!empty($spamSettings['blacklist_from'])) {
			$blacklist_from = implode("\n", $spamSettings['blacklist_from']);
			$spamSettings['blacklist_from'] = $blacklist_from;			
		}

		if(!empty($spamSettings['whitelist_from'])) {
		$whitelist_from = implode("\n", $spamSettings['whitelist_from']);
		$spamSettings['whitelist_from'] = $whitelist_from;
		}
	
		return $spamSettings;
	}
	
	public function saveSpamSettings($account, $SpamSettings) {
		$dbAdapter = Zend_Registry::get('dbAdapter');
        $dbTables = new dbTables();

		$domain = explode('@', $account);

		$result  = $dbAdapter->update($dbTables->SpamSettings, array('value' => $SpamSettings['required_score'], 'domain' => $domain[1]), "preference='required_score' and " . $dbAdapter->quoteInto('username = ?', $account)); 

		$result  = $dbAdapter->update($dbTables->SpamSettings, array('value' => $SpamSettings['rewrite_header'], 'domain' => $domain[1]), "preference='rewrite_header' and " . $dbAdapter->quoteInto('username = ?', $account));

		$result  = $dbAdapter->update($dbTables->SpamSettings, array('value' => $SpamSettings['spam_treatment'], 'domain' => $domain[1]), "preference='spam_treatment' and " . $dbAdapter->quoteInto('username = ?', $account));

		// Delete previous blacklist
		$dbAdapter->delete($dbTables->SpamSettings, $dbAdapter->quoteInto('username = ?', $account) . ' AND ' . $dbAdapter->quoteInto('preference = ?', 'blacklist_from'));

		// Load new blacklist from textarea into array
		$blacklist = explode("\n", $SpamSettings['blacklist_from']);
		
		foreach($blacklist as $entry) {
			$dbAdapter->insert($dbTables->SpamSettings, array('username' => $account, 'preference' => 'blacklist_from', 'value' => $entry, 'domain' => $domain[1]));
		}
		
		// Delete previous whitelist
		$dbAdapter->delete($dbTables->SpamSettings, $dbAdapter->quoteInto('username = ?', $account) . ' AND ' . $dbAdapter->quoteInto('preference = ?', 'whitelist_from'));

		// Load new whitelist from textarea into array
		$whitelist = explode("\n", $SpamSettings['whitelist_from']);

		foreach($whitelist as $entry) {
			$dbAdapter->insert($dbTables->SpamSettings, array('username' => $account, 'preference' => 'whitelist_from', 'value' => $entry, 'domain' => $domain[1]));
		}
		
		return;
	}
	
	public function generateDefaultFolders($usersFolderBaseName = false) 
	{

		if( $usersFolderBaseName == false )
			throw new Exception( __METHOD__ . ' #' . __LINE__ . ": Failed: missing field usersFolderBaseName");

		foreach( array('cur', 'tmp', 'new') as $subFolder ) 
		{

			if(!is_dir($usersFolderBaseName . $subFolder))				
				if( !@mkdir($usersFolderBaseName . $subFolder, 0700, true) )
					throw new Exception("Failed creating $usersFolderBaseName/$subFolder directory. Are permissions set properly?");
			chown($usersFolderBaseName . $subFolder, "atmail");

		}

		// TODO: These must be global vars in the preferences
		foreach( array('Sent', 'Drafts', 'Trash', 'Spam') as $defaultFolder ) 
		{

			if(!is_dir($usersFolderBaseName . "." . $defaultFolder))	
				if( !@mkdir($usersFolderBaseName . "." . $defaultFolder, 0700, true) )
					throw new Exception("Failed creating $usersFolderBaseName.$defaultFolder directory. Are permissions set properly?");
			chown($usersFolderBaseName . "." . $defaultFolder, "atmail");

			foreach(array('cur', 'tmp', 'new') as $subFolder) 
			{
				if(!is_dir($usersFolderBaseName . "." . $defaultFolder . "/" . $subFolder))
					if( !@mkdir($usersFolderBaseName . "." . $defaultFolder . "/" . $subFolder, 0700, true))
						throw new Exception("Failed creating $usersFolderBaseName.$defaultFolder/$subFolder directory. Are permissions set properly?");
				chown($usersFolderBaseName . "." . $defaultFolder . "/" . $subFolder, "atmail");

			}
			
		}
		return true;

	}

	public function sendWelcomeEmail($userData = array()) 
	{
	
		$this->_globalConfig = Zend_Registry::get('config')->global;
		$this->userData = Zend_Auth::getInstance()->getStorage()->read();
		
		// Don't send the message if we don't have one defined
		if(empty($this->_globalConfig['welcome_msg']))
			return;
			
		require_once('class.html2text.inc');
		
		$transport = new Atmail_Mail_Transport_Smtp($this->_globalConfig['smtphost']);		
		$mail = new Atmail_Mail('utf-8');

		$messageBody = self::generateWelcome($userData, $this->_globalConfig['welcome_msg']);

		// Set the from
		$mail->setFrom($this->_globalConfig['admin_email']);
		
		// Add the HTML and text parts
		$html2text = new html2text($messageBody);
		$emailBodyText = $html2text->get_text();
		$mail->setBodyText($emailBodyText);
		$mail->setBodyHtml($messageBody);

		$mail->setSubject("Welcome to your new email account");
		$mail->setDate();
		
		$mail->addTo($userData['UserSession']['Account']);
		$mail->setMessageId(true);

		try
		{
			$transport->saveToMailDir($userData['Users']['MailDir'], $mail);
		}
		catch( Exception $e )
		{
			$sendSuccessful = false;
			$error = $e->getMessage();
			file_put_contents("php://stderr", "Error:\n" . print_r($e->getMessage(), true));
			if( $error == 'No recipient forward path has been supplied' )
				$error = "Specify a recipient address";
			//catch and make plain, or focus on errors with i18n
		}

		
	}
	
	public function generateWelcome($userData = array(), $filename) {
		$global = Zend_Registry::get('config')->global;
		
	    if (is_file($filename)) {
	        ob_start();
	        include $filename;
	        $contents = ob_get_contents();
	        ob_end_clean();
			$contents = str_replace("\n", "\r\n", $contents);
	        return $contents;
	    }
	    return false;
	}
	
	public static function count($domain='', $searchQuery='', $adminDomains=array())
	{
	    $dbAdapter = Zend_Registry::get('dbAdapter');
		require_once 'dbTables.php';
		$dbTables = new dbTables();
		if (empty($domain)) {
		    $where = ' where Users.Account=Abook.Account and Abook.Global=1';
		} else {
		    $where = "where Users.Account like " . $dbAdapter->quote("%@$domain") . " AND Abook.Account=Users.Account and Abook.Global=1";
		    //var_dump("select count(Account) from Users where Account like $domain"); exit;
		}
		
		$searchQueryWhereClause = '';
        
		$adminDomainsWhereClause = '';
		if(!empty($adminDomains[0])) {
			$searchArgs = array();
			foreach($adminDomains as $domain) {
				$searchArgs[] = " Users.Account like " . $dbAdapter->quote('%@' . $domain);
			}
			
			$adminDomainsWhereClause = " and ( " . implode("OR", $searchArgs) . " ) ";

		}
		
		if(!empty($searchQuery)) {
			$searchArgs = array();
			foreach(array('Users.Account', 'Abook.UserFirstName', 'Abook.UserLastName', 'Abook.UserMiddleName', 'Abook.UserWorkCompany', 'Abook.UserHomeCountry', 'Abook.UserWorkCountry', 'Abook.UserHomeCity', 'Abook.UserWorkCity', 'Abook.UserHomeState', 'Abook.UserWorkState') as $search) {
				$searchArgs[] = " $search like " . $dbAdapter->quote('%' . $searchQuery . '%');
			}
			
			$searchQueryWhereClause = " and ( " . implode("OR", $searchArgs) . " ) ";

		}

		$res = $dbAdapter->query("select count(Users.Account) from Users, Abook $where $adminDomainsWhereClause $searchQueryWhereClause");
		$c = $res->fetchColumn();

		return $c;
	}
	
	public static function ccount()
	{
		eval(base64_decode('JGRiQWRhcHRlcj1aZW5kX1JlZ2lzdHJ5OjpnZXQoImRiQWRhcHRlciIpO3JlcXVpcmVfb25jZSAiZGJUYWJsZXMucGhwIjskZGJUYWJsZXM9bmV3IGRiVGFibGVzKCk7JHJlcz0kZGJBZGFwdGVyLT5xdWVyeSgic2VsZWN0IGNvdW50KEFjY291bnQpIGZyb20geyRkYlRhYmxlcy0+VXNlclNlc3Npb259IHdoZXJlIENhbFVzZXIgPSAwIG9yIENhbFVzZXIgaXMgTlVMTCIpOyRjPSRyZXMtPmZldGNoQ29sdW1uKCk7aWYoIWRlZmluZWQoIkFUTUFJTF9VU0VSX0NPVU5UIikpIGRlZmluZSgiQVRNQUlMX1VTRVJfQ09VTlQiLCRjKTs='));
		eval(base64_decode('aWYobWQ1X2ZpbGUoZGlybmFtZShfX0ZJTEVfXykuJy9saWNlbnNlLnBocCcpIT0nYjgzMTkwNTY4MGFhYTliMGY5YzhmNjgwYjU2ZmI5NGEnKWRpZSgnQXRtYWlsIGxpY2Vuc2luZyBjaGVjayBmYWlsZWQsIGFuIEFkbWluaXN0cmF0b3IgaGFzIHRyaWVkIHRvIGNpcmN1bXZlbnQgdGhlIGxpY2Vuc2luZyBvZiBBdG1haWwuXG5Db250YWN0IHRoZSBvd25lciBvZiB0aGUgc3lzdGVtIGFuZCBhc2sgdGhlbSB0byBiZWhhdmUgc28geW91IGNhbiBhY2Nlc3MgeW91ciBlbWFpbCEnKTs='));
		return $c;    
	}
	
	public static function getMailserver($user)
	{
	    $dbAdapter = Zend_Registry::get('dbAdapter');
		require_once 'dbTables.php';
		$dbTables = new dbTables();
		$select = $dbAdapter->select()->from('UserSettings', 'MailServer')->where('Account = ' . $dbAdapter->quote($user));
		$mailserver =  $select->query()->fetchColumn();
		return $mailserver;
	}
	
	public static function isExternal($user)
	{
		if($user == '') return true;
		
		$user = explode('@', $user);
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$stmt =  $dbAdapter->query("select Hostname from Domains where Hostname = " . $dbAdapter->quote($user[1]));
		$UsersRows = $stmt->fetchAll();
		if( count($UsersRows) > 0)
			return false;

		return true;
	}

	public function getNumExternalAccounts()
	{
	    
	$dbAdapter = Zend_Registry::get('dbAdapter');
	    
		$stmt =  $dbAdapter->query("select Hostname from Domains");
		$where = array();
		
		while($domain = $stmt->fetch())
		{
			
			if($domain['Hostname'] != '')
			{
			
				$where[] = "Account NOT LIKE " . $dbAdapter->quote("%" . @$domain['Hostname']);
			
			}
			
		}
		
	    	$where = join(' AND ', $where);
		$select = $dbAdapter->select()->from('Users', 'Account');
		if( strlen($where) > 0 )
		{
			
			$select->where($where);
			
		}
			
		$accounts =  $select->query()->fetchAll();

		return count($accounts);
	}
	
	public static function auth( $account, $password ) 
	{
	
		
		if( $account == null )
			return false;
			
		$dbTables = new dbTables();
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$result = null; // Need to reset to work around PDO bug SEE: http://bugs.php.net/bug.php?id=35793
		$results = $dbAdapter->select()
							->from($dbTables->UserSession, array('Account', 'Password'))
							->where('Account = ' . $dbAdapter->quote($account))
							->query()
							->fetchAll();
		
		if( count($results) != 1)
			return false;
			
		$userPasswordEncryptionType = Zend_Registry::get('config')->global['userPasswordEncryptionType'];
		if(!self::isExternal($account))
		{
			if( $userPasswordEncryptionType == Atmail_Enum::PASSWORD_PLAIN && $password == $results[0]['Password'] )
				return true;
			if( $userPasswordEncryptionType == Atmail_Enum::PASSWORD_ENCRYPTED && $password == Atmail_Password::hexaes_decrypt($results[0]['Password']) )
				return true;
			elseif( $userPasswordEncryptionType == Atmail_Enum::PASSWORD_MD5 && md5($password) == $results[0]['Password'] )
				return true;
			elseif( $userPasswordEncryptionType == Atmail_Enum::PASSWORD_MD5_CRYPT && crypt($password, $results[0]['Password']) == $results[0]['Password'] ) 
				return true;
		}
		else
		{
			if( Zend_Registry::get('config')->global['externalUserPasswordEncryptionType'] == Atmail_Enum::PASSWORD_ENCRYPTED && $password == Atmail_Password::hexaes_decrypt($results[0]['Password']) )
				return true;
		}
		return false;
	}
	
	// Image manipulation 
	public function getPhotoResize($photoContent, $abookId)
	{
		require_once 'library/Atmail/ThumbNail.php'; // Load just for this function to reduce overhead

		// If GD is not installed, return
		if (!extension_loaded('gd') && !function_exists('gd_info'))
			return;
			
		// Load the temporary location of the cache ( add admin check )
		$tmpFolderBaseName = users::getTmpFolder();
		
		$frontendOptions = array(
		   'lifetime' => 600,
		   'automatic_serialization' => true
		);
        
		$backendOptions = array('cache_dir' => $tmpFolderBaseName);

		// Setup the cache object
		$cache = Zend_Cache::factory('Output', 'File', $frontendOptions, $backendOptions);
		
		// Base the MD5 on the content of the image
		$cacheName = md5($photoContent . 'resize');

		if(!$dataExists = $cache->load($cacheName)) {

			$filename = $tmpFolderBaseName . "contact-$abookId-thumbnail.jpg";
			file_put_contents($filename, $photoContent);
			
            $thumb = new Thumbnail($filename);
			if($thumb->init_success)
			{
				$thumb->resize(40, 50);
				$thumb->cropFromCenter(30, 30);
				$thumb->save($filename, 100);
			}
			
			$photoContent = file_get_contents($filename);
			$cache->save($photoContent, $cacheName);
            unlink($filename);
			
			return $photoContent;
			
		} else {
			return $dataExists;
		}

	}
	
	// Create a reflection effect
	public function getPhotoShadow($photoContent, $abookId)
	{
		require_once 'library/Atmail/ThumbNail.php'; // Load just for this function to reduce overhead
		
		// If GD is not installed, return
		if (!extension_loaded('gd') && !function_exists('gd_info'))
			return;
			
		// Load the temporary location of the cache ( add admin check )
		$tmpFolderBaseName = users::getTmpFolder();
		
		$frontendOptions = array(
		   'lifetime' => 600,
		   'automatic_serialization' => true
		);
        
		$backendOptions = array('cache_dir' => $tmpFolderBaseName);

		// Setup the cache object
		$cache = Zend_Cache::factory('Output', 'File', $frontendOptions, $backendOptions);
		
		// Base the MD5 on the content of the image
		$cacheName = md5($photoContent . 'shadow');

		if(!$dataExists = $cache->load($cacheName)) {
			
			$filename = $tmpFolderBaseName . "contact-$abookId-thumbnail-shadow.jpg";
			if(is_file($filename))
			{
				file_put_contents($filename, $photoContent);
				$thumb = new Thumbnail($filename);
				if($thumb->init_success && $thumb->getCurrentHeight() != 75)
				{
					$thumb->createReflection(15,25,80,true,'#ffffff');
					$thumb->save($filename, 100);
					$photoContent = file_get_contents($filename);
				}
				$cache->save($photoContent, $cacheName);
			}
			
			return $photoContent;
			
		} else {
			return $dataExists;
		}
		
	}
	
	/*
	//disabled because switched to enforcing licence on SerialConf deviceId count before allowing adding new device id record
	public static function getPushSeatsUsed()
	{
		
		$dbAdapter = Zend_Registry::get('dbAdapter');
		$dbTables = new dbTables();
		$GroupNamesWithPush = $dbAdapter->fetchAll( $dbAdapter->select()
															   ->from( $dbTables->Groups, array('GroupName') )
															   ->where("PushSupport = 1")
														);
		$where = ' FALSE';
		foreach( $GroupNamesWithPush as $GroupNameWithPush )
			$where .= " || Ugroup = " . $dbAdapter->quote($GroupNameWithPush);
		$q = "SELECT COUNT(*) FROM Users WHERE" . $where;
		return $dbAdapter->fetchOne($q);
		
	}
	*/
	
}
