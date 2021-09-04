<?php

class Mail_ComposemessageController extends Atmail_Controller_Action
{
	public function preDispatch()
	{

		// Setup filters (this can ultimately be moved to Atmail_Controller_Action once filter rules class created for each controller)
		$this->filter = Atmail_Filter_Input_Controller::filterInput($this->getRequest());

		require_once('library/jQuery/jQuery.php');
		require_once('Mail/RFC822.php');
		require_once('class.html2text.inc');
		$this->initView();

		if( $this->getRequest()->isXmlHttpRequest() )
		{

			$this->isAjax = true;
			$this->view->jsonIdsToRender = array();
			$this->view->jsonIdsToRender = array();
			$this->view->notices = array();
			$this->view->errors = array();

		}
		else
		{

			$this->isAjax = false;

		}

		// check if we have been authenticated... and redirect if not
		if( !Atmail_FormAuth::authenticated() )
		{

			// we have most probably timed out, so lets kick them to the timeout bootstrap page
			// this will poison json responses, which will fire the php.error ( or ajax.error ) which can detect the special page
			$this->_forward('timeout', 'index', 'default');
			return;

		}
		else
		{
			$this->view->setEncoding('UTF-8');
			$requestParams = $this->getRequest()->getParams();
			$this->view->siteBaseUrl = Zend_Registry::get('siteBaseUrl');
			$this->view->appBaseUrl = $this->getRequest()->getBaseUrl() . (strpos($this->getRequest()->getBaseUrl(),'index.php')?'':'/index.php');
			$this->view->moduleBaseUrl = $this->view->appBaseUrl . ($requestParams['module']=='default'?'':DIRECTORY_SEPARATOR . $requestParams['module']);
			$this->view->thisActionURL = $this->view->moduleBaseUrl . '/' . $requestParams['controller'] . '/' . $requestParams['action'];
			$this->session = new Zend_Session_Namespace(ATMAIL_NAMESPACE);
			$this->userData = Zend_Auth::getInstance()->getStorage()->read();
			$this->userData = Atmail_Password::processUser($this->userData);
			
			$this->_globalConfig = Zend_Registry::get('config')->global;
			$this->view->UserSettings = Zend_Registry::get('UserSettings');
			$this->view->Signature = $this->view->UserSettings['Signature'];
			$this->view->autocompleteFetchThreshold = (isset($this->_globalConfig['autocompleteFetchThreshold']) ? $this->_globalConfig['autocompleteFetchThreshold'] : 50);
			$this->view->autocompleteCacheSize = (isset($this->_globalConfig['autocompleteCacheSize']) ? $this->_globalConfig['autocompleteCacheSize'] : 100);
			$this->view->autocompleteFetchSize = (isset($this->_globalConfig['autocompleteFetchSize']) ? $this->_globalConfig['autocompleteFetchSize'] : 25);
			$this->view->autocompleteMinFetchLength = (isset($this->_globalConfig['autocompleteMinFetchLength']) ? $this->_globalConfig['autocompleteMinFetchLength'] : 2);

			//if this hit refers to a folder, connect to the store configured with this folder
			//TODO: this must be refactored in then refactoring cache
			$this->_currentConfig = array();

			if( isset($requestParams['folder']) )
			{

				$this->_currentConfig['folder'] = urldecode( urldecode( $requestParams['folder'] ) ); //normally POST so have to double URL decode because ZF doesnt currently auto urldecode POST
				$this->folderUTF7 = $this->_currentConfig['folder'];

			}

			$this->_mailStoreConfig = array_merge($this->_globalConfig, $this->userData, $this->_currentConfig);
            
			$this->_mailStoreConfig['UseSSL'] = $this->view->UserSettings['UseSSL'];
			$this->AtmailMailStorage = Atmail_Mail_Storage::getInstance($this->_mailStoreConfig);
			$this->AtmailMailStorageMain = &$this->AtmailMailStorage->getMailStore($this->_mailStoreConfig['namespaceName']);

			try
			{

				$this->view->UserSettings = Zend_Registry::get('UserSettings');
				date_default_timezone_set($this->view->UserSettings['TimeZone']);

			}
			catch ( Exception $e )
			{

				//dont bother trying to set user timezone if UserSettings not jet available

			}

		}
		//Needed for error logging, not just debugging so strip debugging log lines out when done
		$this->view->log = Zend_Registry::get('log');

	}

	public function indexAction()
	{

		$requestParams = $this->getRequest()->getParams();

		if( isset($requestParams['standalone']) && $requestParams['standalone'] == 'true' )
		{

			$this->view->standalone = true;

		}
		else
		{

			$this->view->standalone = false;

		}
		//CONSIDER: should sanitise even although coming from our own controlled abook, we could upgrade this in future to accept external links
		if( isset($requestParams['to']) )
		{
			if( is_array($requestParams['to']) )
			{
				$this->view->to = implode(", ", $requestParams['to']);
			}
			else
			{
				$this->view->to = str_replace(',', '', urldecode($requestParams['to']) );
			}
		}
		
		$this->view->tabId = 'viewmessageTab' . $requestParams['viewmessageTabNumber'];
		$this->render('index');

	}

	public function editAction()
	{
		//TODO: unpack original source folder and UIDs from draft headers for composer for sent flagging
		$requestParams = $this->getRequest()->getParams();

		$viewmessageTabNumber = $requestParams['viewmessageTabNumber'];
		$uniqueId = $requestParams['uniqueId'];

		//at this point we are in the correct folder
		$this->AtmailMailStorageMain->connect();

	
		$draftMessage = $this->AtmailMailStorageMain->getMessage($uniqueId);
		$messageArray = messageHandling::returnPreparedMessageArray($draftMessage, array('unpackAllAttachments' => true, 'tmpFolderBaseName' => users::getTmpFolder(), 'id' => $uniqueId, 'folder' => $this->folderUTF7, 'uniqueId' => $uniqueId));
		
		if( isset($viewmessageTabNumber) && $viewmessageTabNumber > 0 )
		{
			//composer opening up in a tab so send html content to correct tab id
			$this->view->jsonIdsToRender['viewmessageTab' . $viewmessageTabNumber ] = 'composemessage/index.phtml';
		}
		else
		{
			if(isset($requestParams['resultContext']))
			{
				$this->view->jsonIdsToRender[$requestParams['resultContext'] . ' #mail_info'] = 'composemessage/composeform.phtml';
			}
			else
			{
				//compact so send html to compact main email id
				$this->view->jsonIdsToRender['Email #mail_info'] = 'composemessage/composeform.phtml';			
			}
		}
		$this->view->messageArray = $messageArray;
		$this->view->relatedMessageMessageId = $messageArray['headers']['in-reply-to'];
		$this->view->tabId = 'viewmessageTab' . $viewmessageTabNumber;
		$this->view->relatedDraftUID = $uniqueId;
		$this->view->editingDraft = true;
	
		$this->render('global/jsonresponse', null, true);
		$this->_helper->pluginCall('postDraftEditRender', $this->getResponse());
	}

	private function getSafeFileNameFromSubject( $subject )
	{

		return str_replace('.','_', str_replace('/','', str_replace('\\', '', strip_tags($subject))));

	}

	public function replyAction()
	{

		$requestParams = $this->getRequest()->getParams();

		if( !array_key_exists('folder', $requestParams) || strlen($requestParams['folder']) == 0 )
		{

			$this->log->err(__METHOD__ . ' #' . __LINE__ . "Application error replying to message: message source folder not set.\n Arguments:\n" . print_r($requestParams,1) );
			throw new Exception( $this->view->translate('Application error replying to message:') . $this->view->translate('message source folder not set.') . $this->view->translate('Please notify administrator') );

		}

		if( !array_key_exists('uniqueId', $requestParams) || strlen($requestParams['uniqueId']) == 0 )
		{

			$this->log->err(__METHOD__ . "Application error forwarding message: message source id not set.\n Arguments:\n" . print_r($requestParams,1));
			throw new Exception( $this->view->translate('Application error forwarding message:') . $this->view->translate('message source id not set.') . $this->view->translate('Please notify administrator') );

		}

		if( !array_key_exists('viewmessageTabNumber', $requestParams) || strlen($requestParams['viewmessageTabNumber']) == 0 )
		{

			$this->log->err(__METHOD__ . "Application error forwarding message: viewmessageTabNumber not set.\n Arguments:\n" . print_r($requestParams,1));
			throw new Exception( $this->view->translate('Application error forwarding message:') . $this->view->translate('message viewmessageTabNumber not set.') . $this->view->translate('Please notify administrator') );

		}

		$this->AtmailMailStorageMain->connect();

		$viewmessageTabNumber = $requestParams['viewmessageTabNumber'];
		$uniqueId = $requestParams['uniqueId'];

		$forcedInlines = array();

		$mail = $this->AtmailMailStorageMain->getMessage($uniqueId);
		$mail->getHeaders();
		$mail->processHeaders( array('uniqueid' => $uniqueId) );
		$messageArray = messageHandling::returnPreparedMessageArray($mail, array('tmpFolderBaseName' => users::getTmpFolder(), 'id' => $uniqueId, 'folder' => $this->folderUTF7, 'uniqueId' => $uniqueId));

		$Account = $this->userData['Account'];
		$contacts = new contacts( array('Account' => $Account) );
		$selfContactId = $contacts->searchContact( array('UserEmail' => $Account) );
		try
		{

			$selfContact = $contacts->getContact( $selfContactId );

		}
		catch ( Exception $e )
		{

			//catch contact not existing or not unique
			$selfContact = array('UserFirstName' => '', 'UserLastName' => '', 'Account' => $Account);

		}
		//TODO: Users should really be related to Abook by an int (non user modifyable) key, currently business rule is Account contact details stored in Abook (makes sense) but the only way to uniquely ID the self record is via UserEmail == Account (this breaks integrity if user anyone changed UserEmail - which they are logically allowed to change (primary contactable email for contact, but is linked to Account))

		$originalSent = $messageArray['headers']['date'];

		$originalFrom = $messageArray['headers']['from'];

		//strip commas out for now until UI recipient handling can be fixed
		$originalFrom = str_replace(',', '', $originalFrom );

		$originalTo = $messageArray['headers']['to'];

		if( isset($messageArray['headers']['cc']) )
		{

			$originalCc = $messageArray['headers']['cc'];

		}
		else
		{

			$originalCc = '';

		}

		$originalSubject = htmlentities( $messageArray['headers']['subject'], ENT_COMPAT, 'utf-8' );

		$replyHeader .= "<br />\n" .
							$this->view->translate('----- Original Message -----') . "<br />\n" .
							"<div class=\"replyForwardedMainHeader\">" .
								"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageFromField\">" . $this->view->translate('From:') . "</div> " .
								htmlentities(htmlentities($originalFrom, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . 
							"</div><br />\n" .
							"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageToField\">" . $this->view->translate('To:') . "</div> " . htmlentities(htmlentities($originalTo, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . "<br />\n" .
							"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageCcField\">" . $this->view->translate('Cc:') . "</div> " . htmlentities(htmlentities($originalCc, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . "<br />\n" .
							"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageSentField\">" . $this->view->translate('Sent:') . "</div> " . $originalSent . "<br />\n" .
							"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageSubjectField\">" . $this->view->translate('Subject:') . "</div> " . $originalSubject . "<br />\n";

		$messageArray['bodyPreparedHtml'] =  $replyHeader . $messageArray['bodyPreparedHtml'];

		$messageArray['headers']['subject'] = htmlentities($this->AtmailMailStorageMain->reReply($messageArray['headers']['subject']), ENT_COMPAT, 'utf-8');

		//we are going to reuse the preparedMessageArray cos alot the same, but must drop and recompile the attachments
		$date = $messageArray['headers']['date'];

		if( $requestParams['type'] == 'reply' )
		{

			$messageArray['headers']['to'] = $originalFrom;
			$messageArray['headers']['cc'] = '';

		}
		else
		{

			//TODO: remove self address from originalTo (originalTo could contain other recipients)
			$messageArray['headers']['to'] = $originalFrom . ', ' . $originalTo;
			$messageArray['headers']['cc'] = $originalCc;

		}
		$messageArray['headers']['from'] = '"' . $selfContact['UserFirstName'] . ' ' . $selfContact['UserLastName'] . '" ' . $selfContact['Account'];
		$messageArray['forcedInlines'] = array();
		$messageArray['attachmentsList'] = array();
		
		if( isset($viewmessageTabNumber) && $viewmessageTabNumber > 0 )
		{
			
			//composer opening up in a tab so send html content to correct tab id
			$this->view->jsonIdsToRender['viewmessageTab' . $viewmessageTabNumber ] = 'composemessage/index.phtml';

		} 
		else 
		{
		
			//compact so send html to compact main id
			$this->view->jsonIdsToRender['mail_info'] = 'composemessage/composeform.phtml';
		
		}

		//compile all view vars
		$this->view->tabId = 'viewmessageTab' . $viewmessageTabNumber;
		$this->view->relatedMessageFolder = $this->folderUTF7;
		$this->view->relatedMessageUIDs = $uniqueId; //CSV String
		$this->view->relatedMessageMessageId = $messageArray['headers']['message-id'];

		$this->view->type = $requestParams['type']; // reply or replyAll - TODO revise var name so not ambiguous
		$this->view->relatedMessageResponseType = 'reply';
		$this->view->messageArray = $messageArray;
		$this->render('global/jsonresponse', null, true);

	}

	public function forwardAction()
	{

		$requestParams = $this->getRequest()->getParams();

		//check that required args are set
		if( !array_key_exists('folder', $requestParams) || strlen($requestParams['folder']) == 0 )
		{

			$this->log->err(__METHOD__ . "Application error forwarding message: message source folder not set.\n Arguments:\n" . print_r($requestParams,1));
			throw new Exception( $this->view->translate('Application error forwarding message:') . $this->view->translate('message source folder not set.') . $this->view->translate('Please notify administrator') );

		}

		if( !array_key_exists('uniqueIds', $requestParams) || count($requestParams['uniqueIds']) == 0 )
		{

			$this->log->err(__METHOD__ . "Application error forwarding message: message source ids not set.\n Arguments:\n" . print_r($requestParams,1));
			throw new Exception( $this->view->translate('Application error forwarding message:') . $this->view->translate('message source ids not set.') . $this->view->translate('Please notify administrator') );

		}

		if( !array_key_exists('viewmessageTabNumber', $requestParams) || strlen($requestParams['viewmessageTabNumber']) == 0 )
		{

			$this->log->err(__METHOD__ . "Application error forwarding message: viewmessageTabNumber not set.\n Arguments:\n" . print_r($requestParams,1));
			throw new Exception( $this->view->translate('Application error forwarding message:') . $this->view->translate('message viewmessageTabNumber not set.') . $this->view->translate('Please notify administrator') );

		}
		
		if( array_key_exists('forwardAsAttachments', $requestParams) && $requestParams['forwardAsAttachments'] == '1' )
		{
			
			$forwardAsAttachments = true;
		}                               
		else
		{
			
			$forwardAsAttachments = false;
			
		}

		$this->AtmailMailStorageMain->connect();

		$viewmessageTabNumber = $requestParams['viewmessageTabNumber'];
		$uniqueIds = $requestParams['uniqueIds'];

		$attachmentsList = array();
		$forwardedHeader = '';
		//catch somewhere passing not as array, causing hard crash
		if( !is_array($uniqueIds) )
		{

			$uniqueIds = array($uniqueIds);// add single item to array
			Zend_Registry::get('log')->info("uniqueIds argument not passed as array, near " . __METHOD__);

		}
		
		//force forwarded emails to be as attachments if more than one email being forwarded
		if( count($uniqueIds) > 1)
		{
			
			$forwardAsAttachments = true;
			
		}

		//unpack required emails to disk as attachments, then open composer
		$manualAttachmentCounter = 1;
		foreach($uniqueIds as $uniqueId )
		{

	   		$message = $this->AtmailMailStorageMain->getMessage($uniqueId);


			//get subject to use as email filename
			$message->getHeaders();
			$message->processHeaders( array('uniqueid' => $uniqueId) );
			$messageArray = messageHandling::returnPreparedMessageArray($message, array('unpackAllAttachments' => true, 'tmpFolderBaseName' => users::getTmpFolder(), 'folder' => $this->folderUTF7, 'uniqueId' => $uniqueId));
			
			$rawSubject = $messageArray['headers']['subject'];
			$subject = htmlentities($messageArray['headers']['subject'], ENT_COMPAT, 'utf-8');
            
			if( $forwardAsAttachments )
			{

				$filename = $this->getSafeFileNameFromSubject($rawSubject) . '.eml';
				//compose manual filename if subject was not suitable
				if( $filename == '.eml' )
					$filename = 'email' . $manualAttachmentCounter++ . '.eml';


				//TODO: fetch raw message content from cached file
				$messageHeader = $this->AtmailMailStorageMain->getRawHeader($uniqueId);
				$messageContent = $this->AtmailMailStorageMain->getRawContent($uniqueId);
								
				$messageHeader = preg_replace('/X-Atmail-Bcc:.*\n/','',$messageHeader); 
				$rawemail = $messageHeader . $messageContent;

				$bytesWritten =  file_put_contents (APP_ROOT . users::getTmpFolder() . $filename, $rawemail );
				$forcedAttachment = array();
				$forcedAttachment['mimeType'] = 'message/rfc822';
				$forcedAttachment['filenameFS'] = $filename;
				$forcedAttachment['filenameOriginal'] = $filename;
				$forcedAttachment['iconClass'] = 'default';

				$forcedAttachment['tmpUrl'] = APP_ROOT . users::getTmpFolder() . $filename;
				$forcedAttachment['sizeRaw'] = $bytesWritten;
				$attachmentsList[] = $forcedAttachment;

			}   
			else
			{
				
				//force all nonbody mime parts into $messageArray['forcedAttachments']
				if(!isset($messageArray['attachmentsList']))
				{
					$messageArray['attachmentsList'] = array();
				}
				$attachmentsList = $messageArray['attachmentsList'];//array_merge($messageArray['forcedInlines'])
				
			}
			
			//TODO: confirm on what side to append the translated FW: for RTL language
			$Account = $this->userData['Account'];
			$contacts = new contacts( array('Account' => $Account) );
			$selfContactId = $contacts->searchContact( array('UserEmail' => $Account) );
			try
			{

				$selfContact = $contacts->getContact( $selfContactId );

			}
			catch ( Exception $e )
			{

				//catch contact not existing or not unique
				if( $e->getMessage() == 'Single match for Contact id was not found.' )
				{

					$selfContact = array('UserFirstName' => '', 'UserLastName' => '', 'Account' => $Account);

				}

			}
			//TODO: Users should really be related to Abook by an int (non user modifyable) key, currently business rule is Account contact details stored in Abook (makes sense) but the only way to uniquely ID the self record is via UserEmail == Account (this breaks integrity if user anyone changed UserEmail - which they are logically allowed to change (primary contactable email for contact, but is linked to Account))

			$from = $messageArray['headers']['from'];

			//strip commas out for now until UI recipient handling can be fixed
			$from = str_replace(',', '', $from );
			$to = $messageArray['headers']['to'];

			if(!isset($messageArray['headers']['cc']))
			{
				$messageArray['headers']['cc'] = '';
			}
			$cc = $messageArray['headers']['cc'];
			$sent = $messageArray['headers']['date'];

            //NB need to double encode the address fields because AJAX decodes by default (we have HTML AND special chars in our forward body)
			$forwardedHeader .= "<br />\n" .
								$this->view->translate('----- Original Message -----') . "<br />\n" .
								"<div class=\"replyForwardedMainHeader\">" .
									"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageFromField\">" . $this->view->translate('From:') . "</div> " .
									htmlentities(htmlentities($from, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . 
								"</div><br />\n" .
								"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageToField\">" . $this->view->translate('To:') . "</div> " . htmlentities(htmlentities($to, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . "<br />\n" .
								"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageCcField\">" . $this->view->translate('Cc:') . "</div> " . htmlentities(htmlentities($cc, ENT_QUOTES, 'utf-8'), ENT_QUOTES, 'utf-8') . "<br />\n" .
								"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageSentField\">" . $this->view->translate('Sent:') . "</div> " . $sent . "<br />\n" .
								"<div class=\"replyForwardedMessageHeaders\" id=\"originalMessageSubjectField\">" . $this->view->translate('Subject:') . "</div> " . $subject . "<br />\n";

			if( $forwardAsAttachments )
			{
				
				$forwardedHeader .= "Attached<br /><br />";
				
			}
			else
			{
				
				$forwardedHeader .= $messageArray['bodyPreparedHtml'];
				
			}
			
		} //EO foreach($uniqueIds as $uniqueId )
		$messageArray['bodyPreparedHtml'] =  $forwardedHeader;

		if( count($uniqueIds) > 1 )
		{
			
			$messageArray['headers']['subject'] = $this->view->translate('Forwarding multiple attachments');
			
		}
		else
		{
			
			$messageArray['headers']['subject'] = $this->view->translate('Fwd:') . ' ' . $messageArray['headers']['subject'];
			
		}

		$messageArray['headers']['subject'] = htmlentities($messageArray['headers']['subject'], ENT_COMPAT, 'utf-8');

		//we are going to reuse the preparedMessageArray cos alot the same, but must drop and recompile the attachments
		$from = '"' . $selfContact['UserFirstName'] . ' ' . $selfContact['UserLastName'] . '" ' . $selfContact['Account'];
		$date = $messageArray['headers']['date'];
		$messageArray['headers']['to'] = '';
		$messageArray['headers']['cc'] = '';
		if(!isset($messageArray['headers']['bcc']))
		{
			$messageArray['headers']['bcc'] = '';
		}
		$messageArray['headers']['from'] = $from;
		
		//replace attachments of message with email attachments if inline
		$messageArray['attachmentsList'] = $attachmentsList;
		
		//other attachments are moved or removed from forwarded messages (message forwarded online will have all inline attachments forced to normal attachments)
		$messageArray['forcedAttachments'] = array();
		$messageArray['forcedInlines'] = array();
		
		if( isset($viewmessageTabNumber) && $viewmessageTabNumber > 0 )
		{

			//composer opening up in a tab so send html content to correct tab id
			$this->view->jsonIdsToRender['viewmessageTab' . $viewmessageTabNumber ] = 'composemessage/index.phtml';

		}
		else
		{

			//compact so send html to compact main id
			$this->view->jsonIdsToRender['mail_info'] = 'composemessage/composeform.phtml';

		}

		$this->view->relatedMessageFolder = $this->folderUTF7;
		$this->view->relatedMessageResponseType = 'forward';
		$this->view->tabId = 'viewmessageTab' . $viewmessageTabNumber;
		$this->view->relatedMessageUIDs = implode(',', $uniqueIds); //CSV String
		if(!isset($messageArray['_basicHeaders']))
		{
			$messageArray['_basicHeaders']['message-id'] = '';
		}
		$this->view->relatedMessageMessageId = $messageArray['_basicHeaders']['message-id'];
		$this->view->messageArray = $messageArray;

		$this->render('global/jsonresponse', null, true);

	}

	/**
	* This action is designed to be called from 2 forms distinguished by inlineComposer == 'true'
	* the function is essentially the same except that the non-inlineComposer is also designed to handle multiple related messages so e.g. forwarding and replying must the flags on multiple messages
	* the fields differ as follows:
	*
	* 	<input type="hidden" name="inlineComposer" id="inlineComposer" value="true" />
	*	<input type="hidden" name="relatedMessageFolder" id="relatedMessageFolder" value="<?php echo $this->AtmailMailStorageMain->getCurrentFolder() ?>" />
	*	<input type="hidden" name="relatedMessageUID" id="relatedMessageUID" value="<?php echo $this->uniqueId; ?>" />
	*	<input type="hidden" name="relatedMessageMessageId" id="relatedMessageMessageId" value="<?php echo $latestThreadMessageBasicHeaders['message-id'] ?>" />
	*	<input type="hidden" name="relatedMessageResponseType" class="relatedMessageResponseType" value="reply" />
	*
	*AND
	*	<input type="hidden" name="relatedMessageFolder" class="relatedMessageFolder" value="<?php echo $this->relatedMessageFolder ?>" />
	*	<input type="hidden" name="relatedMessageUIDs" class="relatedMessageUIDs" value="<?php echo $this->relatedMessageUIDs ?>" />
	*	<input type="hidden" name="relatedMessageMessageId" class="relatedMessageMessageId" value="<?php echo $this->messageArray['headers']['message-id'] ?>" />
	*	<input type="hidden" name="relatedMessageResponseType" class="relatedMessageResponseType" value="<?php echo $this->relatedMessageResponseType ?>" />
	*	<input type="hidden" name="relatedDraftUID" class="relatedDraftUID" value="<?php echo $this->relatedDraftUID ?>" />
	*
	*	This action handles sending of new message, reply single message, replyAll single message, forward single message, forward multiple messages, from inline composer and normal composer
	*/



	// TODO: Support text only messages
	// TODO: disable forwarding of Drafts (not real messages yet so (even although for practicality thay are stored on the IMAP server) ilogical to allow)
	public function sendAction()
	{

		// Call preEmailSend plugin hook which can override settings used to create the email
		$this->_helper->pluginCall('preEmailSend');
		$requestParams = $this->getRequest()->getParams();

		//recipient fields may contain \" for quotes in recipient fields so decode here (jQuery or Browser encoding/serializing data before POST)
		foreach( array( 'emailTo', 'emailCc', 'emailBcc') as $recipientField )
			$requestParams[ $recipientField ] = stripslashes( $requestParams[$recipientField] );
		
		$this->charset = 'UTF-8';
		
		//if charset !UTF-8 then need to translate incoming UTF-8 subject
		if( $this->charset != 'UTF-8' )
			foreach( $requestParams as &$param )
				$param = iconv('UTF-8', strtoupper($this->charset).'//IGNORE', $param);
		
		if( array_key_exists('inlineComposer', $requestParams) && ($requestParams['inlineComposer'] == true || $requestParams['inlineComposer'] == 'true' || $requestParams['inlineComposer'] == 1 ) )
			$fromInlineComposerForm = true;
		else
			$fromInlineComposerForm = false;
		
		$noErrorYet = true;

		// Decide if to enable SMTP auth or regular connection
		if( !empty($this->_globalConfig['smtpauth_username']) && !empty($this->_globalConfig['smtpauth_password']) && !empty($this->_globalConfig['smtp_auth']) )
		{
			$config = array(
							'auth' => 'login',
							'username' => $this->_globalConfig['smtpauth_username'],
							'password' => $this->_globalConfig['smtpauth_password']
	   		);
		}
		else
			$config = array( );
		
		if(PHP_OS == "Darwin")
			$config['name'] = php_uname('n');
		
		$transportClass = $this->_helper->pluginCall("smtpTransportClass");
		if (empty($transportClass))
			$transportClass = "Atmail_Mail_Transport_Smtp";
		
		$transport = new $transportClass($this->_globalConfig['smtphost'], $config);
		$this->_helper->pluginCall("postCreateSmtpTransport", $transport);
		$newMessage = new Atmail_Mail( $this->charset );
		$newMessage->setMessageId(true);

		// Set the from, first to the ReplyTo defined in the settings, else the account login name
		// First process sender details and compile complete from and optional replyto address
		// At this stage dont accept custom replyTo or emailFrom from the compose form, take from userSettings

		//format propper replyto address if short Account
		if( strpos($this->view->UserSettings['Account'], '@') !== false )
			$requestParams['fromAddress'] = $this->view->UserSettings['Account'];
		else
		{
			$configDovecot = Zend_Registry::get('config')->dovecot;
			if( !isset($configDovecot['default_domain']) || $configDovecot['default_domain'] == '' )
				throw new Exception( $this->view->translate('Unable to send, Administrator needs to set Default Domain in Admin > Services > POP3/IMAP') );
			$requestParams['fromAddress'] = $this->view->UserSettings['Account'] . '@' . $configDovecot['default_domain'];
		}

		if( isset($this->view->UserSettings['RealName']) && strlen($this->view->UserSettings['RealName']) > 0 )
		{
			// ZF does not quote the realname, so lets do it
			$requestParams['fromRealname'] = $this->view->UserSettings['RealName'];
			$requestParams['fromRealname'] = trim($requestParams['fromRealname'], " \t\n\r\0\x0B");
			//$requestParams['fromRealname'] = '"' . $requestParams['fromRealname'] . '"';
		}
		else
			$requestParams['fromRealname'] = '';
		
		if( strlen($requestParams['fromRealname']) > 0 )
			$newMessage->setFrom( $requestParams['fromAddress'], $requestParams['fromRealname'] );
		else
			$newMessage->setFrom( $requestParams['fromAddress']);
		
		if( isset($this->view->UserSettings['ReplyTo']) && strlen($this->view->UserSettings['ReplyTo']) > 0 )
			$requestParams['replyToAddress'] = $this->view->UserSettings['ReplyTo'];
		else
			$requestParams['replyToAddress'] = '';
		
		if( strlen( $requestParams['replyToAddress']) > 0 )
		{
			
			//not natively supported in current ZF version
			if( strlen($requestParams['fromRealname']) > 0 )
				$newMessage->setReplyTo( $requestParams['replyToAddress'], $requestParams['fromRealname'] );
			else
				$newMessage->setReplyTo( $requestParams['replyToAddress']);

		}

		$final_recipients = array();
		$rcptLog = array();
		$bccsProcessed = array();
		foreach (array('emailTo', 'emailCc', 'emailBcc') as $addressType)
		{
			//decode posted form data ready for processing
			$requestParams[$addressType] = html_entity_decode($requestParams[$addressType], ENT_COMPAT, 'UTF-8');

			if ($requestParams[$addressType] != '')
			{
				$addressObjectsArray = getProcessedRecipientObjects( $requestParams[$addressType] );

				foreach ($addressObjectsArray as $addressObject)
				{
					$personal = '';
					if (is_string($addressObject))
					{
						preg_match('/(.*?)<(.*?)>/', $addressObject, $m);
						$personal = trim($m[1]);
						$mail = $m[2];
					}
					else
					{
						$personal = $addressObject->personalUTF8;
						$mail = $addressObject->address;
					}
					
					$final_recipients[] = (strlen($personal)>0?$personal . ' <':'') . $mail . (strlen($personal)>0?'>':'');
					
					//add the cleaned up recipient to the message
					if( $addressType == 'emailBcc' )
					{
					
						$newMessage->addBcc($mail, (strlen($personal)>0?$personal:null) );
						$bccsProcessed[] = (strlen($personal)>0?$personal . ' <':'') . $mail . (strlen($personal)>0?'>':'');
					
					}	
					elseif( $addressType == 'emailCc' )
						$newMessage->addCc($mail, (strlen($personal)>0?$personal:null) );
					else
						$newMessage->addTo($mail, (strlen($personal)>0?$personal:null) );
					
					// Create an array of all recipients for logging
					$rcptLog[] = $mail;
				}
			}
		}

		// Set the X-Mailer to the Atmail version
		$newMessage->addHeader('X-Mailer', "Atmail " . $this->_globalConfig['version'] );

		// Set in-reply-to header
		if( $fromInlineComposerForm )
			$newMessage->addHeader('in-reply-to', $requestParams['relatedMessageMessageId'] );
		elseif( array_key_exists('relatedMessageMessageId', $requestParams) && strlen($requestParams['relatedMessageMessageId']) > 0 )
			$newMessage->addHeader('in-reply-to', $requestParams['relatedMessageMessageId'] );
		
		// Set the subject and misc fields
		//$emailSubject = html_entity_decode( $requestParams['emailSubject'] , ENT_QUOTES, 'UTF-8' ); //subject comes from an input so use verbatim (all chars legal)
		$newMessage->setSubject( $requestParams['emailSubject'] );

		//add reply requested if set
		if( array_key_exists('readReceiptRequested', $requestParams) && $requestParams['readReceiptRequested'] == 'true' )
			$newMessage->addHeader('X-Confirm-Reading-To', (strlen($requestParams['replyToAddress'])>0?$requestParams['replyToAddress']:$requestParams['fromAddress']) );
		
		// Append the global footer
		// Only add footer if not already in the last 1k, else looks bad on inter A6 chatter
		if( strpos($requestParams['emailBodyHtml'], substr($this->_globalConfig['footer_msg'], -1024)) === false )
			$requestParams['emailBodyHtml'] .= $this->_globalConfig['footer_msg'];
		
		// Add the HTML and text parts
		$html2text = new html2text($requestParams['emailBodyHtml']);
		$emailBodyText = $html2text->get_text();
		$newMessage->setBodyText($emailBodyText);

		//check/wrap HTML body with <html><body></html></body>
		if( !preg_match("'<body[^>]*>(.*?)</body>'i", $requestParams['emailBodyHtml'] ) )
			$requestParams['emailBodyHtml'] = '<body>' . $requestParams['emailBodyHtml'] . '</body>';
		
		if( !preg_match("'<html[^>]*>(.*?)</html>'i", $requestParams['emailBodyHtml'] ) )
			$requestParams['emailBodyHtml'] = '<html>' . $requestParams['emailBodyHtml'] . '</html>';
		
		$newMessage->setBodyHtml($requestParams['emailBodyHtml']);

		//Handle attachments
		if( $noErrorYet && isset($requestParams['attachments']) )
		{
			foreach( $requestParams['attachments'] as $attachment )
			{
				$filename = urldecode($attachment['filenameFS']);
				$filenameOriginal = urldecode( $attachment['filenameOriginal'] );

				if( file_exists( APP_ROOT . users::getTmpFolder() . $filename) )
				{
					if( $fileContents = file_get_contents( APP_ROOT . users::getTmpFolder() . $filename ) )
					{
						//encode attachment filename if needed
						$preferences = array(
							"input-charset" => "UTF-8",
							"output-charset" => "UTF-8",
							"line-length" => 76,
							"scheme" => "B"
						);
						$attachmentFilename = substr(iconv_mime_encode("", $filenameOriginal, $preferences),2);
						//according to RFC 2046 message/rfc822 mimme types must be 7bit, 8bit, or binary encoded
						$attachmentEncoding = ($attachment['mimeType'] == "message/rfc822") ? Zend_Mime::ENCODING_8BIT : Zend_Mime::ENCODING_BASE64 ;
						//google likes message/rfc822 attachments to be inline
						$attachmentDisposition = ($attachment['mimeType'] == "message/rfc822") ? Zend_Mime::DISPOSITION_INLINE : Zend_Mime::DISPOSITION_ATTACHMENT ;
						$multiPart = $newMessage->createAttachment(
								$fileContents,
								$attachment['mimeType'],
								$attachmentDisposition,
								$attachmentEncoding,
								$attachmentFilename
						);
					}
				}
			}
		}

		if( $noErrorYet )
		{
			// Send the message
			try
			{
				$this->_helper->pluginCall("preEmailSend", $newMessage);
				$newMessage->send($transport);
			}
			catch (Exception $e)
			{
				
				$this->_helper->pluginCall("emailSendFailed", $e);
				$noErrorYet = false;
				$error = $e->getMessage();
                
				if( strpos($error, 'Blocked') !== false )
					$error = $this->view->translate('The server has rejected the email address. If you feel this address is legitimate please contact your administrator.');
				else if( $error == 'No recipient forward path has been supplied' )
					$error = $this->view->translate('Email address not valid. Please use an email address which looks like user@example.com');
			}
		}
        
		//If succeeded, then transport will contain raw message parts needed for append
		if( $noErrorYet )
		{
			// If we are running the Webmail client, log the action to the DB - If server, the log-daemon process will log the action for us
			if($this->_globalConfig['install_type'] != 'server')
			{
				require_once('log.php');
				$log = new logEntry($this->view->UserSettings['Account']);

				foreach($rcptLog as $rcpt)
					$log->insert("Log_SendMail", array('LogIP' => $_SERVER['REMOTE_ADDR'], 'EmailTo' => $rcpt, 'Account' => $this->view->UserSettings['Account']));
			}
			
			$final_recipients = join($final_recipients, ',');
			// update popularity/usage count of address
			$Account = $this->userData['Account'];
			$contacts = new contacts( array('Account' => $Account) );
			$contacts->updateUsage($final_recipients, 1);
			$remembered = $contacts->remember($final_recipients, 1);
			if(count($remembered) > 0)
			{
				// live update abook if open
				//jQuery('li[GroupID=' . GROUP_AUTO . '] .unread strong')->updateCount( $remembered );
				
				// reset the autocomplete cache
				jQuery::evalScript("$.jCacher.remove('autocomplete'); $.jCacher.remove('autocomplete_global');");
			}
			//TODO: confirm APPENDUID response handled properly
			try
			{
                
				//append Bcc header before saving to sent
				if( count($bccsProcessed) > 0 )
				{
					
					$newMessage->addHeader('X-Atmail-Bcc', implode(", ", $bccsProcessed) );
					
				}

				
				$sentFolderName = $this->AtmailMailStorageMain->getMainFolderName('Sent');
				$msg = $transport->getRawMessage($newMessage);

				$this->AtmailMailStorageMain->appendMessage($msg, $sentFolderName );
				unset($newMessage);
				unset($transport);
				jQuery::evalScript("$('.sendEmail, .saveAsDraft, .addAttachment').enableActionSimpler();");
			}
			catch ( Exception $e)
			{
				$noErrorYet = false;
				$error = $e->getMessage();
                
				if( strpos($error, 'cannot send literal string') !== false )
				{
					$error = $this->view->translate('Successfully sent message, but unable to save in Sent folder. Quota limit possibly reached?');
					jQuery::addMessage( $error );
				}
				else
				{
					$error = $this->view->translate('Unable to save in Sent.');
					jQuery::addError( $error );
				}
			}

			//only close the tab if the composer was in a tab
			// and the tab is not a search pane
			if( isset($requestParams['tabId']) && strlen($requestParams['tabId'])>0 && $requestParams['tabId'] != 'messageList' && !preg_match('/viewMessage/', $requestParams['composeID']) && strstr($requestParams['tabId'], 'searchResultsTab') == null)
			{
				jQuery::evalScript("$('#navMessages').tabs('select', 0);");
				jQuery::evalScript("$('#nav').tabs('select', 0);");
				jQuery::evalScript("removeTab({name:'#" . $requestParams['tabId'] . "'});");
			}
			else if(!empty($requestParams['inlineComposer']))
			{
				// If using the 3-pane interface, display the sent DIV and hide the editor
                // Display sent message, or sent reply for inline
				if(empty($requestParams['relatedMessageUID']))
				{
					jQuery::addMessage( $this->view->translate('Successfully sent message') );
				}
				else
				{
					jQuery::addMessage( $this->view->translate('Successfully sent reply') );
				}

				// Hide the email editor, so the user-experience that the message is sent feel right
				if(isset($requestParams['tabId']))
				{
					jQuery::evalScript("$('#primary_content_inner .message.reply', '#" . $requestParams['tabId'] . "').hide();");
					//scroll to top to see response message
					//2 pane
					jQuery::evalScript("$('#primary_content_inner', '#" . $requestParams['tabId'] . "').scrollTop(0);");
					//3 pane
					jQuery::evalScript("$('#primary_content #mail_info', '#" . $requestParams['tabId'] . "').scrollTop(0);");
				}
				else
				{
					jQuery::evalScript("$('#primary_content_inner .message.reply').hide();");
					//scroll to top to see response message
					//2 pane
					jQuery::evalScript("$('#primary_content_inner:first').scrollTop(0);");
					//3 pane
					jQuery::evalScript("$('#primary_content:first #mail_info').scrollTop(0);");
				}
			}

			$previousFolder = $this->AtmailMailStorageMain->getCurrentFolder();
			$draftsFolderNameGlobal = $this->AtmailMailStorageMain->getMainFolderName('Drafts');

			// Remove the message out of drafts if there was one
			if( isset($requestParams['relatedDraftUID']) && strlen($requestParams['relatedDraftUID']) > 0 )
			{
				if($previousFolder != $draftsFolderNameGlobal)
				{
					$this->AtmailMailStorageMain->selectFolder($draftsFolderNameGlobal);
				}
				
				$this->AtmailMailStorageMain->removeMessage( $requestParams['relatedDraftUID'] );

				if($previousFolder == $draftsFolderNameGlobal)
				{
					jQuery('#Email div[foldernameglobal=' . $draftsFolderNameGlobal . '] a')->click();
				}
			}

			//Flag original source messages with replyed to, all replyed to, or forwarded
		   	//unpack list of UIDs to frlag on success
			if( $fromInlineComposerForm && strlen($requestParams['relatedMessageUID']) > 0 )
			{
				$UIDs = array($requestParams['relatedMessageUID']);
			}
			elseif( strlen($requestParams['relatedMessageUIDs']) > 0 )
			{
				$UIDs = explode(',', $requestParams['relatedMessageUIDs']);
			}
			else
			{
				$UIDs = array();
			}

			if( count($UIDs) > 0 )
			{
				if( strlen( $requestParams['relatedMessageFolder']) > 0 )
				{
					$relatedMessageFolder = $requestParams['relatedMessageFolder'];
					$currentFolder = $this->AtmailMailStorageMain->getCurrentFolder();
					if( $currentFolder != $relatedMessageFolder )
					{
						$this->AtmailMailStorageMain->selectFolder( $relatedMessageFolder );
					}
					
					foreach( $UIDs as $UID)
					{
						// CONSIDER: add more general flags currently no official flags for forwarded, repliedtoall
						$response = $this->AtmailMailStorageMain->setFlags($UID, array(Zend_Mail_Storage::FLAG_ANSWERED, Zend_Mail_Storage::FLAG_SEEN) );
						//set replied flag in message list only if still in same folder
						jQuery::evalScript("if( $('#listFolder', '#messageList').attr('value') == '" . $relatedMessageFolder . "') { $('#" . $UID . " #msgIcon', '#messageList').addClass('reply'); }");
					}
				}
				else
				{
					$engError = 'Message sent, but unable to properly flag original message. Original folder unknown.';
					$this->log->err($engError);
					$error = $this->view->translate('Message sent, but unable to properly flag original message. Original folder unknown.');
					jQuery::addMessage( $error );
				}
			}

			//Handle attachments
			if( isset($requestParams['attachments']) )
			{
				foreach( $requestParams['attachments'] as $attachment )
				{
					$filename = urldecode($attachment['filenameFS']);
					if( file_exists( APP_ROOT . users::getTmpFolder() . $filename) && !is_dir( APP_ROOT . users::getTmpFolder() . $filename ) )
					{
						@unlink(APP_ROOT . users::getTmpFolder() . $filename);
					}
				}
			}
		}
		else
		{
			//TODO: On message send failure, possibly go back to email composition view to attempt to resolve problem or serve error on that screen
			if(preg_match("|\<[^@]*@[^\>]*\>|", $error) != 0)
			{
				// malformed email
				$error = $this->view->translate('Email address not valid. Please use an email address which looks like user@example.com');
				jQuery::addError($error);
			}
			else
			{

				// display the generic error message
				jQuery::addError('Failure sending message: ' . $error);

			}
			jQuery::evalScript("$('.sendEmail, .saveAsDraft, .addAttachment').enableActionSimpler();");
		}

		//if standalone composer then show success and close after a few secs
		if( isset($requestParams['standalone']) && $requestParams['standalone'] == 'true' )
		{
			jQuery::addMessage( $this->view->translate('Message sent successfully.') );
			//we could look at closing the window here but would need to bounce it through our own js to open the tab else no perms to close it
		}

		$this->render('global/jsonresponse', null, true);

	}

	public function savedraftAction()
	{
		$requestParams = $this->getRequest()->getParams();

		if( count($requestParams) < 8 )
		{
			$this->render('global/jsonresponse', null, true);
			return;
		}
		
		if($requestParams['emailTo'] == '' && $requestParams['emailSubject'] == $this->view->translate('No Subject') && strlen($requestParams['emailBodyHtml']) < 18 )
		{				
			$this->render('global/jsonresponse', null, true);
			return;
		}
		
		$composeClass = new compose( array('Account' => $this->userData['Account'], 'view' => $this->view) );
		
		try
		{
			$result = $composeClass->saveDraft( $requestParams );
			if( !empty($result['status']) && $result['status'] == 'failed' )
			{
				//failed saving draft so dissable
				if( array_key_exists('auto',$requestParams) && $requestParams['auto'] == 'true' && $this->view->UserSettings['SaveDrafts'] != '0' )
				{
					users::saveAllUserData($this->userData['Account'], array('UserSettings' => array('SaveDrafts' => '0')));
				}
				throw new Exception($result['message']);
			}
			$relatedDraftUID = $result['relatedDraftUID'];
		}
		catch (Exception $e )
		{
			
			//failed saving draft so dissable
			$errorMessage = "Saving Draft failed: " . $e->getMessage();
			Zend_Registry::get('log')->debug( __METHOD__ . ' #' . __LINE__ . ": " . $errorMessage );
			//if autosaving then show warning message in time slot, else show Flash warning
			if( array_key_exists('auto',$requestParams) && $requestParams['auto'] == 'true' ) 
			{
				jQuery::evalScript("$('.draftsSavedMessageSlot', '#" . $requestParams['tabId'] . "').show();");
				jQuery::evalScript("$('.draftsSavedMessageSlot', '#" . $requestParams['tabId'] . "').html('" . addslashes($errorMessage) . "');");
			}
			else
			{
				jQuery::addError($errorMessage);
			}
			$this->render('global/jsonresponse', null, true);
			return;
		}
		
		if( $result['incrementDraftsUITotal'] )
		{
			jQuery('#Email div[foldernameglobal=' . $draftsFolderNameGlobal . '] .unread strong')->updateCount( 1 );
		}
		
		jQuery::evalScript("var composer = $('#" . $requestParams['tabId'] . " .relatedDraftUID'); if(!composer.length) composer = $('." . $requestParams['composeID'] . " .relatedDraftUID'); composer.attr('value', '" . $relatedDraftUID . "');");

		if( $this->view->UserSettings['SaveDrafts'] == '1' ) 
		{
			jQuery::evalScript("$('.draftsSavedMessageSlot', '#" . $requestParams['tabId'] . "').show();");
			jQuery::evalScript("nowts = new Date(); time = nowts.getHours() + ':' + ('0' + nowts.getMinutes()).slice(-2); $('.timeago', '#" . $requestParams['tabId'] . "').html( time );");
		}

		//reenable send/save buttons
		jQuery::evalScript("$('.sendEmail, #topRightSendButton, .saveAsDraft, .addAttachment', '#" . $requestParams['tabId'] . "').enableActionSimpler();");
		jQuery::evalScript("$('.sendEmail, #topRightSendButton, .saveAsDraft, .addAttachment', '." . $requestParams['composeID'] . "').enableActionSimpler();");

		$this->render('global/jsonresponse', null, true);
	}

	public function addattachmentAction()
	{

		$this->_helper->viewRenderer->setNoRender();

		$requestParams = $this->getRequest()->getParams();

		$type = str_replace('/', '_', $_FILES['newAttachment']['type']);
		$filenameOriginal = urldecode( $_FILES['newAttachment']['name'] );
        $filenameOriginal = preg_replace("/^[\/.]+/", "", $filenameOriginal);
        $filenameOriginal = str_replace("../", "", $filenameOriginal);
        
		$filenameFS = $type . '-' . $requestParams['composeID'] . '-' . $filenameOriginal;
        
		$filenameFSABS = APP_ROOT . users::getTmpFolder() . $filenameFS;
        
        // Make sure the file will be saved to the user's tmp folder
        if (realpath(dirname($filenameFSABS)) != realpath(APP_ROOT . users::getTmpFolder())) {
            echo $this->view->translate("illegal filename");
            return;
        }
        
		if ( $_FILES["newAttachment"]["error"] == UPLOAD_ERR_OK )
		{

			if ( !@move_uploaded_file($_FILES['newAttachment']['tmp_name'], $filenameFSABS) )
			{

				if( strpos( $php_errormsg, 'File name too long' ) !== false )
				{

					$errorMessage = $this->view->translate('Attachment filename too long.');

				}
				elseif( strlen($php_errormsg) == 0 )
				{

					$errorMessage = $this->view->translate('Unable to move uploaded attachment, possible problem with filename.');

				}
				else
				{

					$errorMessage = substr($php_errormsg, 0, 99);

				}
				echo $errorMessage;
				return;

			}

		}
		else
		{

			$errorMessages = array();
			$errorMessages[0] = $this->view->translate('Attachment upload successful.');
			$errorMessages[1] = $this->view->translate('Attachment is too large.');
			$errorMessages[2] = $this->view->translate('Attachment is too large.');
			$errorMessages[3] = $this->view->translate('The attachment was only partially uploaded.');
			$errorMessages[4] = $this->view->translate('No attachment file was uploaded.');
			$errorMessages[5] = $this->view->translate('Unable to upload the attachment.');
			$errorMessages[6] = $this->view->translate('Unable to store the attachment on the server.');
			$errorMessages[7] = $this->view->translate('Unable to store the attachment on the server.');
			$errorMessages[8] = $this->view->translate('Attachment upload stopped due to security restrictions.');

			/*

			UPLOAD_ERR_OK
			Value: 0; There is no error, the file uploaded with success.
			UPLOAD_ERR_INI_SIZE
			Value: 1; The uploaded file exceeds the upload_max_filesize directive in php.ini.
			UPLOAD_ERR_FORM_SIZE
			Value: 2; The uploaded file exceeds the MAX_FILE_SIZE directive that was specified in the HTML form.
			UPLOAD_ERR_PARTIAL
			Value: 3; The uploaded file was only partially uploaded.
			UPLOAD_ERR_NO_FILE
			Value: 4; No file was uploaded.
			UPLOAD_ERR_NO_TMP_DIR
			Value: 6; Missing a temporary folder. Introduced in PHP 4.3.10 and PHP 5.0.3.
			UPLOAD_ERR_CANT_WRITE
			Value: 7; Failed to write file to disk. Introduced in PHP 5.1.0.
			UPLOAD_ERR_EXTENSION
			Value: 8; File upload stopped by extension. Introduced in PHP 5.2.0.

			*/
			echo $errorMessages[$_FILES["newAttachment"]["error"]];
			exit;

		}


		$filenameOriginalUrlencoded = urlencode( $filenameOriginal );
		$filenameFSUrlencoded = urlencode( $filenameFS );
		$mimeType = $_FILES['newAttachment']['type'];
		$sizeHuman = bytesToHumanReadable($_FILES['newAttachment']['size']);

		//systematically try match a related attachment icon to put next to attachment in the ui
		if( $mimeType == 'image/jpg' || $mimeType == 'image/jpeg' || strpos($mimeType,'image') !== false )
			$icon = 'img';
		elseif( $mimeType == 'application/pdf' )
			$icon = 'pdf';
		elseif( strpos($mimeType, 'powerpoint') !== false )
			$icon = 'ppt';
		elseif( strpos($mimeType, 'text') !== false )
			$icon = 'txt';
		elseif( strpos($mimeType, 'word') !== false )
			$icon = 'doc';
		elseif( strpos($mimeType, 'compress') !== false ||  strpos($mimeType, 'zip') !== false )
			$icon = 'zip';
		else
			$icon = 'default';

		$uniqueAttachmentKeyArbitrary = md5( $filenameFS );
		$html = '<div class="attachment" filenamefs="' . $filenameFSUrlencoded . '" filenameOriginal="' . $filenameOriginalUrlencoded . '" mimeType="' . $mimeType . '" ></div>
					<img class="type" src="' . $this->view->siteBaseUrl . 'images/icons/file-type-' . $icon . '.png" width="16" height="16" />
					<span>' . htmlentities( $filenameOriginal, ENT_QUOTES, 'utf-8' ) . ' ' . $sizeHuman . '</span>
					<a class="attach-btn" href="#" onClick="removeAttachment(\'' . htmlentities(addslashes($filenameOriginal) , ENT_QUOTES, 'UTF-8') . '\', \'' . $filenameFSUrlencoded . '\', $(this).parent().parent()); return false;">
						<img class="attach" src="' . $this->view->siteBaseUrl . 'images/remove-attachment-trans.png" width="12" height="12" alt="'. addslashes($this->view->translate("remove attachment")) . '" />
					</a>
				 ';
		echo $html;

	}

	public function removeattachmentAction()
	{
        
		$requestParams = $this->getRequest()->getParams();

		$filenameFS = urldecode( urldecode( $requestParams['filenameFS'] ) );
        
        // filter filename
        $filenameFS = preg_replace("/^[\/.]+/", "", $filenameFS);
        $filenameFS = str_replace("../", "", $filenameFS);
        
        $path = APP_ROOT . users::getTmpFolder() . $filenameFS;

        // Do not let the path be outside the user's temp dir and do not let it be a directory
		if( dirname(realpath($path)) == realpath(APP_ROOT . users::getTmpFolder()) && !is_dir($path) && @unlink( $path ) )
		{

			$error = 0;

		}
		else
		{

			$error = 1;

		}
		echo Zend_Json::encode( array('error' => $error) );
        
	}

}
