/*      */ package com.adventnet.appmanager.struts.form;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLogController;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.GroupComponent;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.me.helpdesk.object.TicketSettings.CloseTicketPolicy;
/*      */ import com.me.helpdesk.object.TicketSettings.ReOpenTicketPolicy;
/*      */ import java.io.File;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ import org.apache.struts.util.LabelValueBean;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AMActionForm
/*      */   extends ActionForm
/*      */ {
/*      */   private FormFile theFile;
/*      */   private FormFile reportLogo;
/*      */   private int id;
/*   43 */   private String fromaddress = Constants.EMAIL_ADDRESS;
/*   44 */   private String fromaddress1 = "";
/*   45 */   private String mNo = "";
/*   46 */   private String actionSel = "mail";
/*   47 */   private String SMSPort = "";
/*   48 */   private String stype = "sms";
/*   49 */   private String toaddress = "";
/*      */   
/*      */ 
/*      */ 
/*   53 */   private String subject = intializeSubject();
/*   54 */   private String message = initializeMessage();
/*      */   
/*   56 */   private String mailMsg = FormatUtil.getString("am.mail.server.msg");
/*   57 */   private String smtpserver = null;
/*   58 */   private String hostname = null;
/*   59 */   private int retries = 0;
/*   60 */   private int ptimeout = 5;
/*   61 */   private int smtpport = 25;
/*   62 */   private String SMTPServerUserName = "";
/*   63 */   private String SMTPServerPassword = "";
/*   64 */   private String emailAddress = "";
/*   65 */   private String adminemailAddress = "";
/*   66 */   private String smtpsecserver = null;
/*   67 */   private boolean seccheck = false;
/*   68 */   private boolean prmTlsAuth = false;
/*   69 */   private boolean secTlsAuth = false;
/*   70 */   private boolean prmSSLAuth = false;
/*   71 */   private boolean secSSLAuth = false;
/*   72 */   private boolean masMailServer = false;
/*   73 */   private int smtpsecport = 25;
/*   74 */   private String SMTPsecServerUserName = "";
/*   75 */   private String SMTPsecServerPassword = "";
/*   76 */   private String secemailAddress = "";
/*   77 */   private String method = "createEmailAction";
/*   78 */   private String displayname = "";
/*   79 */   private String query = "";
/*   80 */   private String trapDestinationAddress = "localhost";
/*   81 */   private String trapCommunity = "public";
/*   82 */   private int trapDestinationPort = 162;
/*   83 */   private String snmpVersionList = "11";
/*   84 */   private String v1TrapGenericType = "0";
/*   85 */   private String v1TrapSpecificType = "0";
/*   86 */   private String v1TrapEnterprise = ".1.3.6.1.4.1.2162";
/*   87 */   private String v2SNMPTrapOID = "";
/*   88 */   private String v3SNMPTrapOID = "";
/*   89 */   private String v3TrapAuthProtocol = "MD5";
/*   90 */   private String v3TrapAuthPassword = "";
/*   91 */   private String v3TrapUser = "";
/*   92 */   private String v3TrapContextName = "";
/*   93 */   private String v3TrapPrivPassword = "";
/*   94 */   private String v3TrapEngineID = "1234";
/*   95 */   private String globalTrapAddress = "localhost";
/*   96 */   private int globalTrapPort = 1621;
/*   97 */   private String globalTrapCommunity = "public";
/*   98 */   private String addHostToHolisticApplication = "true";
/*   99 */   private String discoverHostAlso = "true";
/*  100 */   private String enableRCA = "true";
/*  101 */   private String enableServerSnapshot = "true";
/*  102 */   private String enableConsolidatedMail = "false";
/*  103 */   private String enableErrorMail = "false";
/*  104 */   private String errorpollCount = "3";
/*  105 */   private String mibName = "./mibs/APPLICATION-MANAGER-MIB ./mibs/RFC1213-MIB ";
/*  106 */   private String trapVarbinds = FormatUtil.getString("am.webclient.mail.trap.default.text", new String[] { OEMUtil.getOEMString("product.name") });
/*  107 */   private String messageFormat = "2";
/*  108 */   private String objectID = "1.5.0";
/*  109 */   private String haid = null;
/*  110 */   private String monitor = "-";
/*  111 */   private String uploadDir = "../lib/ext/";
/*  112 */   private String host = "";
/*  113 */   private String category = "";
/*  114 */   private String subCategory = "";
/*  115 */   private String item = "";
/*  116 */   private String externalhost = "";
/*  117 */   private String priority = "";
/*  118 */   private String group = "";
/*  119 */   private String technician = "";
/*  120 */   private String reqName = "";
/*  121 */   private String accountName = "";
/*  122 */   private String siteName = "";
/*  123 */   private String port = "";
/*  124 */   private String search = "";
/*  125 */   private String username = "";
/*  126 */   private String password = "";
/*  127 */   private String protocol = "http";
/*  128 */   private String os = "";
/*  129 */   private String instance = "";
/*  130 */   private int timeout = 5;
/*  131 */   private String snmpCommunityString = "public";
/*  132 */   private String jndiurl = "/jmxconnector";
/*  133 */   private String jmxurl = "";
/*  134 */   private boolean jmxEnabled = false;
/*  135 */   private String networkAddress = "192.168.9.0";
/*  136 */   private String startingIP = "192.168.9.0";
/*  137 */   private String endingIP = "192.168.9.40";
/*  138 */   private String snmptelnetport = "";
/*  139 */   private String appmanageros = "";
/*  140 */   private String mode = "";
/*  141 */   private String enableupload = "true";
/*  142 */   private int criticalpollscount = 1;
/*  143 */   private int warningpollscount = 1;
/*  144 */   private int clearpollscount = 1;
/*  145 */   private int min_criticalpollscount = 1;
/*  146 */   private int min_warningpollscount = 1;
/*  147 */   private int min_clearpollscount = 1;
/*  148 */   private String version = "unknown";
/*  149 */   private String role = "none";
/*  150 */   private String gettingstarted = "true";
/*  151 */   private boolean loginSlider = true;
/*  152 */   private boolean loginFeatures = true;
/*  153 */   private boolean loginTopLinks = true;
/*      */   
/*  155 */   private boolean auto_restart = false;
/*  156 */   private boolean presales_emails = false;
/*  157 */   private boolean easyUpgrade = false;
/*  158 */   private boolean selfMonitoring = false;
/*  159 */   private boolean showSalesForce = false;
/*  160 */   private boolean senderrormail = false;
/*  161 */   private String actionsEnabled = "true";
/*  162 */   private String pluginActionsEnabled = "true";
/*  163 */   private String dailyPerfReportCollection = "true";
/*  164 */   private String dbAvailabilityCheck = "false";
/*  165 */   private String hourlyDataFileCollection = "true";
/*  166 */   private String alertJobSkip = "false";
/*  167 */   private String dailyVlfCollection = "true";
/*  168 */   private String backupCollectionPeriod = "7";
/*  169 */   private String reportDelivery = "pdf";
/*  170 */   private String enableCompleteInfoForSMS = "true";
/*  171 */   private String gatewayCheckStatus = "false";
/*  172 */   private String gatewayUrlStatus = "false";
/*  173 */   private String availabilityUpUnderMaintenance = "false";
/*  174 */   private String healthClearUnderMaintenance = "false";
/*  175 */   private String unmanageMonitorAfterMaintenance = "false";
/*  176 */   private String transferEncoding = "base64";
/*  177 */   private String serverpath = null;
/*  178 */   private String gatewayName = null;
/*  179 */   private String gatewayUrlName = null;
/*  180 */   private String logConfig = AMLogController.LOGGING_CONFIG_VALUE + "";
/*  181 */   private String deleteexistingthresholds = "false";
/*  182 */   private String deleteexistinganomaly = "false";
/*  183 */   private String deleteexistingactions = "false";
/*  184 */   private String delimiter = "=";
/*  185 */   private String outputfile = "output.txt";
/*  186 */   private String workingdirectory = "";
/*  187 */   private String serverside = "";
/*  188 */   private String repeatAvailabilityActions = "false";
/*  189 */   private String repeatHealthActions = "false";
/*  190 */   private String repeatAttributeActions = "false";
/*  191 */   private String consecutive_criticalpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  192 */   private String consecutive_warningpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  193 */   private String consecutive_clearpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  194 */   private String consecutive_mincriticalpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  195 */   private String consecutive_minwarningpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  196 */   private String consecutive_minclearpolls = FormatUtil.getString("am.webclient.threshold.critcal.text");
/*  197 */   private String[] exchangeservice = new String[0];
/*  198 */   private String numeric_att = "";
/*  199 */   private String string_att = "";
/*  200 */   private String qengineHome = null;
/*  201 */   private String execProgExecDir = System.getProperty("user.dir");
/*  202 */   private String seventhirtyMA = "1";
/*  203 */   private String hourlyMA = "3";
/*      */   
/*  205 */   private String wtaport = "55555";
/*  206 */   private boolean WTEnabled = false;
/*  207 */   private boolean useWebServerPort = false;
/*  208 */   private boolean instrumentationEnabled = false;
/*  209 */   private int samplingFactor = 100;
/*  210 */   private int maxURL = 10;
/*  211 */   private int maxChildren = 25;
/*  212 */   private int maxDepth = 10;
/*  213 */   private boolean tracingEnabled = false;
/*  214 */   private int fullTraceCount = 0;
/*  215 */   private boolean packageInclude = true;
/*  216 */   private String packageList = "";
/*  217 */   private int dataclean = 90;
/*  218 */   private int dailyclean = 365;
/*  219 */   private int unsolTrapsCleanHrs = 5;
/*  220 */   private int alertclean = 10000;
/*  221 */   private int eventalert = 30;
/*  222 */   private String settings = null;
/*      */   private String advancedUser;
/*      */   private String sendmail;
/*      */   private String gmapkey;
/*      */   private String gmapheight;
/*      */   private String gmapwidth;
/*      */   private String zoomlevel;
/*      */   private int locationid;
/*      */   private ArrayList gmapcountries;
/*      */   private String zoomlocation;
/*  232 */   private String showFeedback = "true";
/*  233 */   private String allowManage = "";
/*  234 */   private String allowExecute = "";
/*      */   private String eventid;
/*      */   private String source;
/*      */   private String eventtype;
/*  238 */   private String eventtype_any = "false";
/*  239 */   private String eventtype_error = "false";
/*  240 */   private String eventtype_warning = "false";
/*  241 */   private String eventtype_information = "false";
/*      */   private String rulescope;
/*      */   private String selectedservertype;
/*      */   private String ruletype;
/*      */   private String errorCode;
/*      */   private String logCategory;
/*      */   private ArrayList logCategoryList;
/*      */   private String errorMessage;
/*      */   private String status;
/*      */   private String matchRules;
/*  251 */   private String log_startTime = "";
/*  252 */   private String log_endTime = "";
/*  253 */   private String camname = null;
/*  254 */   private String camdesc = null;
/*      */   private boolean eventlog_status;
/*  256 */   private boolean stringChanged = false;
/*  257 */   private boolean numericChanged = false;
/*  258 */   private String monitoringmode = "";
/*  259 */   private String prompt = "$";
/*  260 */   private String choosehost = null;
/*  261 */   private String files = null;
/*  262 */   private String content = null;
/*  263 */   private String filepath = "Specify the absolute path";
/*  264 */   private String filename = null;
/*  265 */   private String mtype = null;
/*  266 */   private String addmaintenance = null;
/*  267 */   private String montype = null;
/*  268 */   private String mgTemplateType = null;
/*  269 */   private String serversite = "local";
/*  270 */   private boolean opfile = false;
/*  271 */   private boolean authEnabled = false;
/*  272 */   private boolean resolveDNS = false;
/*  273 */   private boolean smtpauth = false;
/*  274 */   private String snmpVersion = "v1";
/*  275 */   private boolean apacheauth = false;
/*  276 */   private String apacheUserName = "";
/*  277 */   private String apachepassword = "";
/*  278 */   private boolean serverstatusurl = false;
/*  279 */   private boolean sshkey = false;
/*  280 */   private String apacheurl = "";
/*  281 */   private String tomcatmanagerurl = "/manager";
/*  282 */   private boolean diskIOLinux = false;
/*  283 */   private boolean diskIOWindows = false;
/*  284 */   private boolean diskIOMacOS = false;
/*  285 */   private boolean diskIOAix = false;
/*  286 */   private boolean diskIOSun = false;
/*  287 */   private boolean diskIOFreeBsd = false;
/*  288 */   private boolean diskIOHPUX = false;
/*      */   
/*  290 */   private boolean winDiskLocal = true;
/*  291 */   private boolean winDiskNetwork = false;
/*  292 */   private boolean winDiskMount = false;
/*  293 */   private boolean hostProcDown = false;
/*  294 */   private boolean winServDown = false;
/*  295 */   private boolean irixModeOffLinux = false;
/*  296 */   private boolean errorAlertDisk = false;
/*  297 */   private boolean errorAlertProcessRestart = false;
/*  298 */   private boolean errorAlertNwInter = false;
/*  299 */   private boolean errorAlertNwAdapter = false;
/*  300 */   private boolean errorAlertRestart = false;
/*  301 */   private boolean enableNetAdapterMonitor = false;
/*  302 */   private boolean errorAlertScheduledTask = false;
/*      */   
/*  304 */   private int snmpRetryVal = 3;
/*      */   
/*  306 */   private boolean oracleDiskReads = false;
/*  307 */   private boolean oracleBufferGets = false;
/*  308 */   private boolean oracleLockWaits = false;
/*  309 */   private boolean oracleAverageExecutions = false;
/*  310 */   private String mysqlTableData = "every";
/*  311 */   private String sybaseDBDetails = "every";
/*  312 */   private boolean failedScheduledBackupJobs = true;
/*  313 */   private String postgresDBDetails = "every";
/*  314 */   private boolean mssqlScheduledJobs = false;
/*  315 */   private boolean mssqlBackup = false;
/*  316 */   private String WSDLUrl = "";
/*  317 */   private boolean proxyRequired = false;
/*  318 */   private String dateformat = "default";
/*  319 */   private String optmail = null;
/*  320 */   private String ack = null;
/*      */   
/*  322 */   private boolean usedRouterString = false;
/*  323 */   private String routerString = "";
/*  324 */   private String logonClient = "";
/*  325 */   private String language = "";
/*  326 */   private String systemNumber = "";
/*      */   
/*  328 */   private String[] exchangeservice2k7 = new String[0];
/*  329 */   private String predictioncondition = "";
/*  330 */   private String groupType = "all";
/*  331 */   private boolean urlDebug = false;
/*  332 */   private boolean urlResponses = false;
/*      */   private ArrayList businessHourNames;
/*  334 */   private boolean businessHourAssociatedToAction = false;
/*      */   private String selectedBusinessHourID;
/*  336 */   private String businessType = "1";
/*  337 */   private String healthActionCount = "1";
/*      */   
/*      */ 
/*  340 */   private String winServiceActionName = "";
/*  341 */   private String winServActionTask = "301";
/*  342 */   private String winServActionApplyTo = "1";
/*  343 */   private String sendMail = "";
/*      */   
/*      */ 
/*  346 */   private String sqlUserName = "";
/*  347 */   private String sqlUserPassword = "";
/*  348 */   private String amazonS3BucketTableData = "every";
/*  349 */   private String doNotGoToLogoutPage = "false";
/*      */   
/*      */ 
/*  352 */   private String amazonEC2PrimaryKey = "instanceId";
/*  353 */   private String ec2MergeEnabled = "false";
/*  354 */   private boolean ec2AlertTerminatedInstance = true;
/*  355 */   private boolean signatureVersion4SigningEnabled = false;
/*      */   
/*      */ 
/*  358 */   private String configOption = "";
/*      */   
/*  360 */   private String usagestatistics = "false";
/*  361 */   private boolean showUsageStatistics = false;
/*  362 */   private boolean showMapView = false;
/*  363 */   private String mapFileName = "";
/*      */   
/*      */ 
/*  366 */   private String restApiKey = null;
/*  367 */   private String ticketingType = "restapi";
/*      */   
/*      */ 
/*  370 */   private String helpDeskProduct = "";
/*  371 */   private String servicenowInstance = "";
/*  372 */   private String servicenowUserName = "";
/*  373 */   private String servicenowPassword = "";
/*      */   
/*      */ 
/*  376 */   private String snmpVersionValue = "v1v2";
/*  377 */   private String snmpPort = "161";
/*  378 */   private String snmpUserName = "";
/*  379 */   private String snmpContextName = "";
/*  380 */   private String snmpAuthProtocol = "MD5";
/*  381 */   private String snmpAuthPassword = "";
/*  382 */   private String snmpPrivPassword = "";
/*  383 */   private String snmpPrivProtocol = "DES";
/*  384 */   private String snmpSecurityLevel = "NoAuthNoPriv";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  389 */   private String globalTrapUserName = "";
/*  390 */   private String globalTrapContextName = "";
/*  391 */   private String globalTrapEngineID = "";
/*  392 */   private String globalTrapAuthProtocol = "";
/*  393 */   private String globalTrapAuthPassword = "";
/*  394 */   private String globalTrapPrivProtocol = "";
/*  395 */   private String globalTrapPrivPassword = "";
/*  396 */   private String globalTrapSecurityLevel = "noAuthNoPriv";
/*      */   
/*  398 */   private String hostHwMonitoring = "enable";
/*      */   
/*      */ 
/*      */ 
/*  402 */   private String apikey = "";
/*  403 */   private String email = "";
/*  404 */   private int pollInter = 5;
/*      */   
/*  406 */   private String gettingsync = "true";
/*  407 */   private String importall = "true";
/*      */   
/*  409 */   private int synchHr = 12;
/*  410 */   private int synchMin = 0;
/*  411 */   private String addDeviceDefault = "false";
/*      */   
/*  413 */   private String licFromAddress = "";
/*  414 */   private String licToAddress = "";
/*      */   
/*  416 */   private String passphrase = "";
/*  417 */   private String displaynamelength = "";
/*  418 */   private boolean resFulWebservice = false;
/*  419 */   private String smsMailServer = "1";
/*  420 */   private ArrayList mailServerList = new ArrayList();
/*      */   
/*      */ 
/*      */ 
/*  424 */   private String xsltInput = "";
/*  425 */   private String jsonSchema = "";
/*      */   
/*      */   public void setXsltInput(String xsltInput)
/*      */   {
/*  429 */     this.xsltInput = xsltInput;
/*      */   }
/*      */   
/*      */   public String getXsltInput()
/*      */   {
/*  434 */     return this.xsltInput;
/*      */   }
/*      */   
/*      */   public void setJsonSchema(String jsonSchema)
/*      */   {
/*  439 */     this.jsonSchema = jsonSchema;
/*      */   }
/*      */   
/*      */   public String getJsonSchema()
/*      */   {
/*  444 */     return this.jsonSchema;
/*      */   }
/*      */   
/*      */   public ArrayList getMailServerList() {
/*  448 */     return this.mailServerList;
/*      */   }
/*      */   
/*      */   public void setMailServerList(ArrayList mailServerList) {
/*  452 */     this.mailServerList = mailServerList;
/*      */   }
/*      */   
/*      */   public String getSmsMailServer() {
/*  456 */     return this.smsMailServer;
/*      */   }
/*      */   
/*      */   public void setSmsMailServer(String smsMailServer) {
/*  460 */     this.smsMailServer = smsMailServer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  465 */   private String masGroupName = "";
/*      */   
/*  467 */   private String masSelectType = "0";
/*      */   
/*  469 */   private String pollstoretry = "1";
/*  470 */   private String clearevent = "-1";
/*  471 */   private String thresholdprofileid = "-1";
/*  472 */   private String matchcount = "1";
/*      */   
/*      */   public String getMatchcount()
/*      */   {
/*  476 */     return this.matchcount;
/*      */   }
/*      */   
/*      */   public void setMatchcount(String matches)
/*      */   {
/*  481 */     this.matchcount = matches;
/*      */   }
/*      */   
/*      */   public String getThresholdprofileid()
/*      */   {
/*  486 */     return this.thresholdprofileid;
/*      */   }
/*      */   
/*      */   public void setThresholdprofileid(String profileid)
/*      */   {
/*  491 */     this.thresholdprofileid = profileid;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getClearevent()
/*      */   {
/*  497 */     return this.clearevent;
/*      */   }
/*      */   
/*      */   public void setClearevent(String event)
/*      */   {
/*  502 */     this.clearevent = event;
/*      */   }
/*      */   
/*      */   public String getMasSelectType() {
/*  506 */     return this.masSelectType;
/*      */   }
/*      */   
/*  509 */   public void setMasSelectType(String value) { this.masSelectType = value; }
/*      */   
/*      */   public String getMasGroupName()
/*      */   {
/*  513 */     return this.masGroupName;
/*      */   }
/*      */   
/*  516 */   public void setMasGroupName(String value) { this.masGroupName = value; }
/*      */   
/*  518 */   private boolean isCommand = false;
/*  519 */   private boolean isOutputFromFile = false;
/*      */   
/*      */   public boolean getisCommand() {
/*  522 */     return this.isCommand;
/*      */   }
/*      */   
/*      */   public void setisCommand(boolean isCommand) {
/*  526 */     this.isCommand = isCommand;
/*      */   }
/*      */   
/*      */   public String getImportall()
/*      */   {
/*  531 */     return this.importall;
/*      */   }
/*      */   
/*      */   public String setImportall(String impall)
/*      */   {
/*  536 */     return this.importall = impall;
/*      */   }
/*      */   
/*      */   public String getGettingsync()
/*      */   {
/*  541 */     return this.gettingsync;
/*      */   }
/*      */   
/*      */   public String setGettingsync(String synching)
/*      */   {
/*  546 */     return this.gettingsync = synching;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getPollInter()
/*      */   {
/*  552 */     return this.pollInter;
/*      */   }
/*      */   
/*      */   public void setPollInter(int time) {
/*  556 */     this.pollInter = time;
/*      */   }
/*      */   
/*      */   public String getAddDeviceDefault() {
/*  560 */     return this.addDeviceDefault;
/*      */   }
/*      */   
/*      */   public void setAddDeviceDefault(String addDeviceDefault) {
/*  564 */     this.addDeviceDefault = addDeviceDefault;
/*      */   }
/*      */   
/*      */   public int getSynchHr() {
/*  568 */     return this.synchHr;
/*      */   }
/*      */   
/*      */   public void setSynchHr(int synchHr) {
/*  572 */     this.synchHr = synchHr;
/*      */   }
/*      */   
/*      */   public int getSynchMin() {
/*  576 */     return this.synchMin;
/*      */   }
/*      */   
/*      */   public void setSynchMin(int synchMin) {
/*  580 */     this.synchMin = synchMin;
/*      */   }
/*      */   
/*      */   public String getApikey() {
/*  584 */     return this.apikey;
/*      */   }
/*      */   
/*      */   public void setApikey(String temp) {
/*  588 */     this.apikey = temp;
/*      */   }
/*      */   
/*      */   public String getEmail() {
/*  592 */     return this.email;
/*      */   }
/*      */   
/*      */   public void setEmail(String temp)
/*      */   {
/*  597 */     this.email = temp;
/*      */   }
/*      */   
/*  600 */   public static ArrayList queryMonitorErrors = new ArrayList();
/*      */   
/*      */   public void setGlobalTrapEngineID(String globalTrapEngineID)
/*      */   {
/*  604 */     this.globalTrapEngineID = globalTrapEngineID;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapEngineID()
/*      */   {
/*  609 */     return this.globalTrapEngineID;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapSecurityLevel(String globalTrapSecurityLvl)
/*      */   {
/*  614 */     this.globalTrapSecurityLevel = globalTrapSecurityLvl;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapSecurityLevel()
/*      */   {
/*  619 */     return this.globalTrapSecurityLevel;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapUserName(String globalTrapUserName)
/*      */   {
/*  624 */     this.globalTrapUserName = globalTrapUserName;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapUserName()
/*      */   {
/*  629 */     return this.globalTrapUserName;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapContextName(String globalTrapContexName)
/*      */   {
/*  634 */     this.globalTrapContextName = globalTrapContexName;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapContextName()
/*      */   {
/*  639 */     return this.globalTrapContextName;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapAuthProtocol(String globalAuthProto)
/*      */   {
/*  644 */     this.globalTrapAuthProtocol = globalAuthProto;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapAuthProtocol()
/*      */   {
/*  649 */     return this.globalTrapAuthProtocol;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapAuthPassword(String globalAuthPwd)
/*      */   {
/*  654 */     this.globalTrapAuthPassword = globalAuthPwd;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapAuthPassword()
/*      */   {
/*  659 */     return this.globalTrapAuthPassword;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapPrivProtocol(String globalPrivProto)
/*      */   {
/*  664 */     this.globalTrapPrivProtocol = globalPrivProto;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapPrivProtocol()
/*      */   {
/*  669 */     return this.globalTrapPrivProtocol;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapPrivPassword(String globalTrapPrivPwd)
/*      */   {
/*  674 */     this.globalTrapPrivPassword = globalTrapPrivPwd;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapPrivPassword()
/*      */   {
/*  679 */     return this.globalTrapPrivPassword;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSnmpVersionValue()
/*      */   {
/*  685 */     return this.snmpVersionValue;
/*      */   }
/*      */   
/*      */   public void setSnmpVersionValue(String snmpVerionValue)
/*      */   {
/*  690 */     this.snmpVersionValue = snmpVerionValue;
/*      */   }
/*      */   
/*      */   public String getSnmpPort()
/*      */   {
/*  695 */     return this.snmpPort;
/*      */   }
/*      */   
/*      */   public void setSnmpPort(String port)
/*      */   {
/*  700 */     this.snmpPort = port;
/*      */   }
/*      */   
/*      */   public String getSnmpUserName()
/*      */   {
/*  705 */     return this.snmpUserName;
/*      */   }
/*      */   
/*      */   public void setSnmpUserName(String userName)
/*      */   {
/*  710 */     this.snmpUserName = userName;
/*      */   }
/*      */   
/*      */   public String getSnmpContextName()
/*      */   {
/*  715 */     return this.snmpContextName;
/*      */   }
/*      */   
/*      */   public void setSnmpContextName(String contextName)
/*      */   {
/*  720 */     this.snmpContextName = contextName;
/*      */   }
/*      */   
/*      */   public String getSnmpSecurityLevel()
/*      */   {
/*  725 */     return this.snmpSecurityLevel;
/*      */   }
/*      */   
/*      */   public void setSnmpSecurityLevel(String securityLevel)
/*      */   {
/*  730 */     this.snmpSecurityLevel = securityLevel;
/*      */   }
/*      */   
/*      */   public String getSnmpAuthProtocol()
/*      */   {
/*  735 */     return this.snmpAuthProtocol;
/*      */   }
/*      */   
/*      */   public void setSnmpAuthProtocol(String authProtocol)
/*      */   {
/*  740 */     this.snmpAuthProtocol = authProtocol;
/*      */   }
/*      */   
/*      */   public String getSnmpAuthPassword()
/*      */   {
/*  745 */     return this.snmpAuthPassword;
/*      */   }
/*      */   
/*      */   public void setSnmpAuthPassword(String authPassword)
/*      */   {
/*  750 */     this.snmpAuthPassword = authPassword;
/*      */   }
/*      */   
/*      */   public String getSnmpPrivProtocol()
/*      */   {
/*  755 */     return this.snmpPrivProtocol;
/*      */   }
/*      */   
/*      */   public void setSnmpPrivProtocol(String privProtocol)
/*      */   {
/*  760 */     this.snmpPrivProtocol = privProtocol;
/*      */   }
/*      */   
/*      */   public String getSnmpPrivPassword()
/*      */   {
/*  765 */     return this.snmpPrivPassword;
/*      */   }
/*      */   
/*      */   public void setSnmpPrivPassword(String privPassword)
/*      */   {
/*  770 */     this.snmpPrivPassword = privPassword;
/*      */   }
/*      */   
/*      */   public void setMapFileName(String filename)
/*      */   {
/*  775 */     this.mapFileName = filename;
/*      */   }
/*      */   
/*      */   public String getMapFileName() {
/*  779 */     return this.mapFileName;
/*      */   }
/*      */   
/*      */   public void setShowMapView(boolean usage)
/*      */   {
/*  784 */     this.showMapView = usage;
/*      */   }
/*      */   
/*      */   public boolean getShowMapView() {
/*  788 */     return this.showMapView;
/*      */   }
/*      */   
/*      */   public void setUsagestatistics(String usage) {
/*  792 */     this.usagestatistics = usage;
/*      */   }
/*      */   
/*  795 */   public String getUsagestatistics() { return this.usagestatistics; }
/*      */   
/*      */   public void setShowUsageStatistics(boolean usage)
/*      */   {
/*  799 */     this.showUsageStatistics = usage;
/*      */   }
/*      */   
/*      */   public boolean getShowUsageStatistics() {
/*  803 */     return this.showUsageStatistics;
/*      */   }
/*      */   
/*      */   public void setWinServiceActionName(String s) {
/*  807 */     this.winServiceActionName = s;
/*      */   }
/*      */   
/*      */   public String getWinServiceActionName() {
/*  811 */     return this.winServiceActionName;
/*      */   }
/*      */   
/*      */   public void setWinServActionTask(String s) {
/*  815 */     this.winServActionTask = s;
/*      */   }
/*      */   
/*      */   public String getWinServActionTask() {
/*  819 */     return this.winServActionTask;
/*      */   }
/*      */   
/*      */   public void setWinServActionApplyTo(String s) {
/*  823 */     this.winServActionApplyTo = s;
/*      */   }
/*      */   
/*      */   public String getWinServActionApplyTo() {
/*  827 */     return this.winServActionApplyTo;
/*      */   }
/*      */   
/*      */   public void setSendMail(String s) {
/*  831 */     this.sendMail = s;
/*      */   }
/*      */   
/*      */   public String getSendMail() {
/*  835 */     return this.sendMail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  840 */   private String sqlJobActionName = "";
/*  841 */   private String sqlJobActionTask = "401";
/*  842 */   private String sqlJobActionApplyTo = "1";
/*  843 */   private String sqlJobMail = "";
/*      */   
/*      */   public void setSqlJobActionName(String s) {
/*  846 */     this.sqlJobActionName = s;
/*      */   }
/*      */   
/*      */   public String getSqlJobActionName() {
/*  850 */     return this.sqlJobActionName;
/*      */   }
/*      */   
/*      */   public void setSqlJobActionTask(String s) {
/*  854 */     this.sqlJobActionTask = s;
/*      */   }
/*      */   
/*      */   public String getSqlJobActionTask() {
/*  858 */     return this.sqlJobActionTask;
/*      */   }
/*      */   
/*      */   public void setSqlJobActionApplyTo(String s) {
/*  862 */     this.winServActionApplyTo = s;
/*      */   }
/*      */   
/*      */   public String getSqlJobActionApplyTo() {
/*  866 */     return this.winServActionApplyTo;
/*      */   }
/*      */   
/*      */   public void setSqlJobMail(String s) {
/*  870 */     this.sqlJobMail = s;
/*      */   }
/*      */   
/*      */   public String getSqlJobMail() {
/*  874 */     return this.sqlJobMail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getHealthActionCount()
/*      */   {
/*  881 */     return this.healthActionCount;
/*      */   }
/*      */   
/*      */   public void setHealthActionCount(String i)
/*      */   {
/*  886 */     this.healthActionCount = i;
/*      */   }
/*      */   
/*      */   public String getBusinessType()
/*      */   {
/*  891 */     return this.businessType;
/*      */   }
/*      */   
/*      */   public void setBusinessType(String type)
/*      */   {
/*  896 */     this.businessType = type;
/*      */   }
/*      */   
/*      */   public String getSelectedBusinessHourID()
/*      */   {
/*  901 */     return this.selectedBusinessHourID;
/*      */   }
/*      */   
/*      */   public void setSelectedBusinessHourID(String id)
/*      */   {
/*  906 */     this.selectedBusinessHourID = id;
/*      */   }
/*      */   
/*      */   public ArrayList getBusinessHourNames()
/*      */   {
/*  911 */     return this.businessHourNames;
/*      */   }
/*      */   
/*      */   public void setBusinessHourNames(ArrayList list)
/*      */   {
/*  916 */     this.businessHourNames = list;
/*      */   }
/*      */   
/*      */   public boolean getBusinessHourAssociatedToAction()
/*      */   {
/*  921 */     return this.businessHourAssociatedToAction;
/*      */   }
/*      */   
/*      */   public void setBusinessHourAssociatedToAction(boolean flag)
/*      */   {
/*  926 */     this.businessHourAssociatedToAction = flag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  931 */   private String javaaction = "1";
/*      */   private String selectedjre;
/*      */   private String selectedhost;
/*  934 */   private int tdcount = 2;
/*  935 */   private int tddelay = 30;
/*      */   private ArrayList maillist;
/*      */   private ArrayList mglist;
/*      */   private ArrayList jrelist;
/*      */   private ArrayList hostlist;
/*      */   private String selectedMG;
/*  941 */   private String jtaskMethod = "ThreadDump";
/*  942 */   private String selectAllAgents = null;
/*  943 */   private String[] selectedAgents = null;
/*      */   private String selectedEumInSchedule;
/*  945 */   private ArrayList managedServers = new ArrayList();
/*  946 */   private String selectedServer = null;
/*      */   
/*      */   private ArrayList ec2Instance;
/*      */   
/*      */   private String selectedhypervhost;
/*  951 */   private String hostType = "0";
/*      */   private ArrayList hypervhostlist;
/*      */   private ArrayList hypervVMList;
/*      */   private String selectedHyperVVM;
/*      */   
/*      */   public String getSelectedhypervhost()
/*      */   {
/*  958 */     return this.selectedhypervhost;
/*      */   }
/*      */   
/*      */   public void setSelectedhypervhost(String selectedhypervhost) {
/*  962 */     this.selectedhypervhost = selectedhypervhost;
/*      */   }
/*      */   
/*      */   public String getHostType() {
/*  966 */     return this.hostType;
/*      */   }
/*      */   
/*      */   public void setHypervhostlist(ArrayList hypervhostlist) {
/*  970 */     this.hypervhostlist = hypervhostlist;
/*      */   }
/*      */   
/*      */   public ArrayList getHypervhostlist() {
/*  974 */     return this.hypervhostlist;
/*      */   }
/*      */   
/*      */   public void setHypervVMList(ArrayList hypervVMList)
/*      */   {
/*  979 */     this.hypervVMList = hypervVMList;
/*      */   }
/*      */   
/*      */   public ArrayList getHypervVMList() {
/*  983 */     return this.hypervVMList;
/*      */   }
/*      */   
/*      */   public String getSelectedHyperVVM() {
/*  987 */     return this.selectedHyperVVM;
/*      */   }
/*      */   
/*      */   public void setSelectedHyperVVM(String selectedHyperVVM) {
/*  991 */     this.selectedHyperVVM = selectedHyperVVM;
/*      */   }
/*      */   
/*      */ 
/*      */   private ArrayList xenVMList;
/*      */   
/*      */   private ArrayList xenHostList;
/*      */   
/*      */   private String selectedXenVM;
/*      */   private String selectedXenHost;
/*      */   public ArrayList getXenVMList()
/*      */   {
/* 1003 */     return this.xenVMList;
/*      */   }
/*      */   
/*      */   public void setXenVMList(ArrayList list) {
/* 1007 */     this.xenVMList = list;
/*      */   }
/*      */   
/*      */   public ArrayList getXenHostList() {
/* 1011 */     return this.xenHostList;
/*      */   }
/*      */   
/*      */   public void setXenHostList(ArrayList list) {
/* 1015 */     this.xenHostList = list;
/*      */   }
/*      */   
/*      */   public String getSelectedXenHost() {
/* 1019 */     return this.selectedXenHost;
/*      */   }
/*      */   
/*      */   public void setSelectedXenHost(String host) {
/* 1023 */     this.selectedXenHost = host;
/*      */   }
/*      */   
/*      */   public String getSelectedXenVM() {
/* 1027 */     return this.selectedXenVM;
/*      */   }
/*      */   
/*      */   public void setSelectedXenVM(String vm) {
/* 1031 */     this.selectedXenVM = vm;
/*      */   }
/*      */   
/*      */   public String getGroupType()
/*      */   {
/* 1036 */     return this.groupType;
/*      */   }
/*      */   
/*      */   public void setGroupType(String groupType) {
/* 1040 */     this.groupType = groupType;
/*      */   }
/*      */   
/*      */ 
/*      */   private String intializeSubject()
/*      */   {
/* 1046 */     String toRetSubject = null;
/* 1047 */     if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */     {
/* 1049 */       toRetSubject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") });
/*      */     }
/*      */     else
/*      */     {
/* 1053 */       toRetSubject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") });
/*      */     }
/* 1055 */     return toRetSubject;
/*      */   }
/*      */   
/*      */   private String initializeMessage() {
/* 1059 */     String toRetMessage = null;
/* 1060 */     if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */     {
/* 1062 */       toRetMessage = FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") });
/*      */     }
/*      */     else
/*      */     {
/* 1066 */       toRetMessage = FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("product.name") });
/*      */     }
/* 1068 */     return toRetMessage;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] getExchangeservice2k7()
/*      */   {
/* 1074 */     return this.exchangeservice2k7;
/*      */   }
/*      */   
/*      */   public void setExchangeservice2k7(String[] v)
/*      */   {
/* 1079 */     this.exchangeservice2k7 = v;
/*      */   }
/*      */   
/*      */   public boolean isMssqlScheduledJobs() {
/* 1083 */     return this.mssqlScheduledJobs;
/*      */   }
/*      */   
/*      */   public void setMssqlScheduledJobs(boolean mssqlScheduledJobs) {
/* 1087 */     this.mssqlScheduledJobs = mssqlScheduledJobs;
/*      */   }
/*      */   
/*      */   public void setMysqlTableData(String temp) {
/* 1091 */     this.mysqlTableData = temp;
/*      */   }
/*      */   
/*      */   public String getMysqlTableData() {
/* 1095 */     return this.mysqlTableData;
/*      */   }
/*      */   
/*      */   public void setSybaseDBDetails(String temp) {
/* 1099 */     this.sybaseDBDetails = temp;
/*      */   }
/*      */   
/*      */   public String getSybaseDBDetails() {
/* 1103 */     return this.sybaseDBDetails;
/*      */   }
/*      */   
/*      */   public void setPostgresDBDetails(String temp) {
/* 1107 */     this.postgresDBDetails = temp;
/*      */   }
/*      */   
/*      */   public String getPostgresDBDetails() {
/* 1111 */     return this.postgresDBDetails;
/*      */   }
/*      */   
/*      */   public void setFailedScheduledBackupJobs(boolean failedScheduledBackupJobs) {
/* 1115 */     this.failedScheduledBackupJobs = failedScheduledBackupJobs;
/*      */   }
/*      */   
/*      */   public boolean isFailedScheduledBackupJobs() {
/* 1119 */     return this.failedScheduledBackupJobs;
/*      */   }
/*      */   
/*      */   public void setAmazonS3BucketTableData(String temp) {
/* 1123 */     this.amazonS3BucketTableData = temp;
/*      */   }
/*      */   
/*      */   public String getAmazonS3BucketTableData() {
/* 1127 */     return this.amazonS3BucketTableData;
/*      */   }
/*      */   
/* 1130 */   public boolean isUrlDebug() { return this.urlDebug; }
/*      */   
/*      */   public void setUrlDebug(boolean temp)
/*      */   {
/* 1134 */     this.urlDebug = temp;
/*      */   }
/*      */   
/*      */   public boolean isUrlResponses() {
/* 1138 */     return this.urlResponses;
/*      */   }
/*      */   
/*      */   public void setUrlResponses(boolean temp) {
/* 1142 */     this.urlResponses = temp;
/*      */   }
/*      */   
/*      */   public boolean isMssqlBackup() {
/* 1146 */     return this.mssqlBackup;
/*      */   }
/*      */   
/*      */   public void setMssqlBackup(boolean mssqlBackup) {
/* 1150 */     this.mssqlBackup = mssqlBackup;
/*      */   }
/*      */   
/*      */   public void setOptmail(String optmail)
/*      */   {
/* 1155 */     this.optmail = optmail;
/*      */   }
/*      */   
/*      */   public String getOptmail() {
/* 1159 */     return this.optmail;
/*      */   }
/*      */   
/*      */   public void setAck(String ack)
/*      */   {
/* 1164 */     this.ack = ack;
/*      */   }
/*      */   
/*      */   public String getAck() {
/* 1168 */     return this.ack;
/*      */   }
/*      */   
/*      */   public boolean isWinDiskLocal() {
/* 1172 */     return this.winDiskLocal;
/*      */   }
/*      */   
/*      */   public void setWinDiskLocal(boolean winDiskLocal) {
/* 1176 */     this.winDiskLocal = winDiskLocal;
/*      */   }
/*      */   
/*      */   public boolean isWinDiskNetwork() {
/* 1180 */     return this.winDiskNetwork;
/*      */   }
/*      */   
/*      */   public void setWinDiskNetwork(boolean winDiskNetwork) {
/* 1184 */     this.winDiskNetwork = winDiskNetwork;
/*      */   }
/*      */   
/*      */   public boolean isWinDiskMount() {
/* 1188 */     return this.winDiskMount;
/*      */   }
/*      */   
/*      */   public void setWinDiskMount(boolean winDiskMount) {
/* 1192 */     this.winDiskMount = winDiskMount;
/*      */   }
/*      */   
/*      */   public boolean isWinServDown() {
/* 1196 */     return this.winServDown;
/*      */   }
/*      */   
/*      */   public void setWinServDown(boolean winServDown) {
/* 1200 */     this.winServDown = winServDown;
/*      */   }
/*      */   
/*      */   public boolean isHostProcDown() {
/* 1204 */     return this.hostProcDown;
/*      */   }
/*      */   
/*      */   public void setHostProcDown(boolean hostProcDown) {
/* 1208 */     this.hostProcDown = hostProcDown;
/*      */   }
/*      */   
/*      */   public boolean isIrixModeOffLinux() {
/* 1212 */     return this.irixModeOffLinux;
/*      */   }
/*      */   
/*      */   public void setIrixModeOffLinux(boolean irixModeOffLinux) {
/* 1216 */     this.irixModeOffLinux = irixModeOffLinux;
/*      */   }
/*      */   
/*      */   public boolean isErrorAlertDisk() {
/* 1220 */     return this.errorAlertDisk;
/*      */   }
/*      */   
/* 1223 */   public void setErrorAlertDisk(boolean errorAlertDisk) { this.errorAlertDisk = errorAlertDisk; }
/*      */   
/*      */   public boolean isErrorAlertProcessRestart()
/*      */   {
/* 1227 */     return this.errorAlertProcessRestart;
/*      */   }
/*      */   
/* 1230 */   public void setErrorAlertProcessRestart(boolean errorAlertProcessRestart) { this.errorAlertProcessRestart = errorAlertProcessRestart; }
/*      */   
/*      */   public boolean isErrorAlertNwInter()
/*      */   {
/* 1234 */     return this.errorAlertNwInter;
/*      */   }
/*      */   
/*      */   public void setErrorAlertNwInter(boolean errorAlertNwInter) {
/* 1238 */     this.errorAlertNwInter = errorAlertNwInter;
/*      */   }
/*      */   
/*      */   public boolean isErrorAlertNwAdapter() {
/* 1242 */     return this.errorAlertNwAdapter;
/*      */   }
/*      */   
/*      */   public void setErrorAlertNwAdapter(boolean errorAlertNwAdapter) {
/* 1246 */     this.errorAlertNwAdapter = errorAlertNwAdapter;
/*      */   }
/*      */   
/*      */   public boolean isErrorAlertRestart() {
/* 1250 */     return this.errorAlertRestart;
/*      */   }
/*      */   
/*      */   public void setErrorAlertRestart(boolean errorAlertRestart) {
/* 1254 */     this.errorAlertRestart = errorAlertRestart;
/*      */   }
/*      */   
/*      */   public boolean isEnableNetAdapterMonitor() {
/* 1258 */     return this.enableNetAdapterMonitor;
/*      */   }
/*      */   
/*      */   public void setEnableNetAdapterMonitor(boolean enableNetAdapterMonitor) {
/* 1262 */     this.enableNetAdapterMonitor = enableNetAdapterMonitor;
/*      */   }
/*      */   
/*      */   public boolean isErrorAlertScheduledTask() {
/* 1266 */     return this.errorAlertScheduledTask;
/*      */   }
/*      */   
/*      */   public void setErrorAlertScheduledTask(boolean errorAlertScheduledTask) {
/* 1270 */     this.errorAlertScheduledTask = errorAlertScheduledTask;
/*      */   }
/*      */   
/*      */   public int getSnmpRetryVal() {
/* 1274 */     return this.snmpRetryVal;
/*      */   }
/*      */   
/* 1277 */   public void setSnmpRetryVal(int count) { this.snmpRetryVal = count; }
/*      */   
/*      */ 
/*      */   public boolean isDiskIOLinux()
/*      */   {
/* 1282 */     return this.diskIOLinux;
/*      */   }
/*      */   
/*      */   public void setDiskIOLinux(boolean diskIOLinux) {
/* 1286 */     this.diskIOLinux = diskIOLinux;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOWindows() {
/* 1290 */     return this.diskIOWindows;
/*      */   }
/*      */   
/*      */   public void setDiskIOWindows(boolean diskIOWindows) {
/* 1294 */     this.diskIOWindows = diskIOWindows;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOMacOS() {
/* 1298 */     return this.diskIOMacOS;
/*      */   }
/*      */   
/*      */   public void setDiskIOMacOS(boolean diskIOMacOS) {
/* 1302 */     this.diskIOMacOS = diskIOMacOS;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOAix() {
/* 1306 */     return this.diskIOAix;
/*      */   }
/*      */   
/*      */   public void setDiskIOAix(boolean diskIOAix) {
/* 1310 */     this.diskIOAix = diskIOAix;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOFreeBsd() {
/* 1314 */     return this.diskIOFreeBsd;
/*      */   }
/*      */   
/*      */   public void setDiskIOFreeBsd(boolean diskIOFreeBsd) {
/* 1318 */     this.diskIOFreeBsd = diskIOFreeBsd;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOSun() {
/* 1322 */     return this.diskIOSun;
/*      */   }
/*      */   
/*      */   public void setDiskIOSun(boolean diskIOSun) {
/* 1326 */     this.diskIOSun = diskIOSun;
/*      */   }
/*      */   
/*      */   public boolean isDiskIOHPUX() {
/* 1330 */     return this.diskIOHPUX;
/*      */   }
/*      */   
/*      */   public void setDiskIOHPUX(boolean diskIOHPUX) {
/* 1334 */     this.diskIOHPUX = diskIOHPUX;
/*      */   }
/*      */   
/*      */   public boolean isOracleDiskReads() {
/* 1338 */     return this.oracleDiskReads;
/*      */   }
/*      */   
/*      */   public void setOracleDiskReads(boolean temp) {
/* 1342 */     this.oracleDiskReads = temp;
/*      */   }
/*      */   
/*      */   public boolean isOracleBufferGets() {
/* 1346 */     return this.oracleBufferGets;
/*      */   }
/*      */   
/*      */   public void setOracleBufferGets(boolean temp) {
/* 1350 */     this.oracleBufferGets = temp;
/*      */   }
/*      */   
/*      */   public boolean isOracleLockWaits() {
/* 1354 */     return this.oracleLockWaits;
/*      */   }
/*      */   
/*      */   public void setOracleLockWaits(boolean temp) {
/* 1358 */     this.oracleLockWaits = temp;
/*      */   }
/*      */   
/* 1361 */   public boolean isOracleAverageExecutions() { return this.oracleAverageExecutions; }
/*      */   
/*      */   public void setOracleAverageExecutions(boolean temp)
/*      */   {
/* 1365 */     this.oracleAverageExecutions = temp;
/*      */   }
/*      */   
/*      */   public boolean isAuthEnabled() {
/* 1369 */     return this.authEnabled;
/*      */   }
/*      */   
/*      */   public void setAuthEnabled(boolean authEnabled) {
/* 1373 */     this.authEnabled = authEnabled;
/*      */   }
/*      */   
/*      */   public boolean isResolveDNS() {
/* 1377 */     return this.resolveDNS;
/*      */   }
/*      */   
/*      */   public void setResolveDNS(boolean resolveDNS) {
/* 1381 */     this.resolveDNS = resolveDNS;
/*      */   }
/*      */   
/*      */   public boolean isSmtpauth() {
/* 1385 */     return this.smtpauth;
/*      */   }
/*      */   
/*      */   public void setSmtpauth(boolean smtpauth) {
/* 1389 */     this.smtpauth = smtpauth;
/*      */   }
/*      */   
/*      */   public void setSnmpVersion(String version)
/*      */   {
/* 1394 */     this.snmpVersion = version;
/*      */   }
/*      */   
/*      */   public String getSnmpVersion()
/*      */   {
/* 1399 */     return this.snmpVersion;
/*      */   }
/*      */   
/*      */   public void setHostHwMonitoring(String status)
/*      */   {
/* 1404 */     this.hostHwMonitoring = status;
/*      */   }
/*      */   
/*      */   public String getHostHwMonitoring()
/*      */   {
/* 1409 */     return this.hostHwMonitoring;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAdvancedUser()
/*      */   {
/* 1418 */     return this.advancedUser;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAdvancedUser(String v)
/*      */   {
/* 1427 */     this.advancedUser = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getExecProgExecDir()
/*      */   {
/* 1436 */     return this.execProgExecDir;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setExecProgExecDir(String v)
/*      */   {
/* 1445 */     this.execProgExecDir = v;
/*      */   }
/*      */   
/*      */   public String[] getExchangeservice()
/*      */   {
/* 1450 */     return this.exchangeservice;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setExchangeservice(String[] v)
/*      */   {
/* 1459 */     this.exchangeservice = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getRepeatAttributeActions()
/*      */   {
/* 1468 */     return this.repeatAttributeActions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRepeatAttributeActions(String v)
/*      */   {
/* 1477 */     this.repeatAttributeActions = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getRepeatHealthActions()
/*      */   {
/* 1487 */     return this.repeatHealthActions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRepeatHealthActions(String v)
/*      */   {
/* 1496 */     this.repeatHealthActions = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getRepeatAvailabilityActions()
/*      */   {
/* 1506 */     return this.repeatAvailabilityActions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRepeatAvailabilityActions(String v)
/*      */   {
/* 1515 */     this.repeatAvailabilityActions = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWorkingdirectory()
/*      */   {
/* 1525 */     return this.workingdirectory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWorkingdirectory(String v)
/*      */   {
/* 1533 */     this.workingdirectory = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getServerpath()
/*      */   {
/* 1541 */     return this.serverpath;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setServerpath(String v)
/*      */   {
/* 1550 */     this.serverpath = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDeleteexistingactions()
/*      */   {
/* 1558 */     return this.deleteexistingactions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDeleteexistingactions(String v)
/*      */   {
/* 1567 */     this.deleteexistingactions = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDeleteexistingthresholds()
/*      */   {
/* 1577 */     return this.deleteexistingthresholds;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDeleteexistingthresholds(String v)
/*      */   {
/* 1586 */     this.deleteexistingthresholds = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLogConfig()
/*      */   {
/* 1597 */     return this.logConfig;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLogConfig(String v)
/*      */   {
/* 1606 */     this.logConfig = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getActionsEnabled()
/*      */   {
/* 1616 */     return this.actionsEnabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setActionsEnabled(String v)
/*      */   {
/* 1625 */     this.actionsEnabled = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPluginActionEnabled()
/*      */   {
/* 1634 */     return this.pluginActionsEnabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPluginActionEnabled(String v)
/*      */   {
/* 1643 */     this.pluginActionsEnabled = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDailyPerfReportCollection()
/*      */   {
/* 1652 */     return this.dailyPerfReportCollection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDailyPerfReportCollection(String v)
/*      */   {
/* 1661 */     this.dailyPerfReportCollection = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDbAvailabilityCheck()
/*      */   {
/* 1670 */     return this.dbAvailabilityCheck;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDbAvailabilityCheck(String v)
/*      */   {
/* 1679 */     this.dbAvailabilityCheck = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHourlyDataFileCollection()
/*      */   {
/* 1689 */     return this.hourlyDataFileCollection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setHourlyDataFileCollection(String v)
/*      */   {
/* 1698 */     this.hourlyDataFileCollection = v;
/*      */   }
/*      */   
/*      */   public String getAlertJobSkip()
/*      */   {
/* 1703 */     return this.alertJobSkip;
/*      */   }
/*      */   
/*      */   public void setAlertJobSkip(String v)
/*      */   {
/* 1708 */     this.alertJobSkip = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDailyVlfCollection()
/*      */   {
/* 1716 */     return this.dailyVlfCollection;
/*      */   }
/*      */   
/*      */   public void setDailyVlfCollection(String v) {
/* 1720 */     this.dailyVlfCollection = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getBackupCollectionPeriod()
/*      */   {
/* 1726 */     return this.backupCollectionPeriod;
/*      */   }
/*      */   
/*      */   public void setBackupCollectionPeriod(String v) {
/* 1730 */     this.backupCollectionPeriod = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSqlUserName()
/*      */   {
/* 1738 */     return this.sqlUserName;
/*      */   }
/*      */   
/*      */   public void setSqlUserName(String v) {
/* 1742 */     this.sqlUserName = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSqlUserPassword()
/*      */   {
/* 1750 */     return this.sqlUserPassword;
/*      */   }
/*      */   
/*      */   public String getPollstoretry() {
/* 1754 */     return this.pollstoretry;
/*      */   }
/*      */   
/*      */   public void setPollstoretry(String polls)
/*      */   {
/* 1759 */     this.pollstoretry = polls;
/*      */   }
/*      */   
/*      */   public void setSqlUserPassword(String v) {
/* 1763 */     this.sqlUserPassword = v;
/*      */   }
/*      */   
/*      */   public boolean getEnforcePasswordPolicy() {
/* 1767 */     return this.enforcePasswordPolicy;
/*      */   }
/*      */   
/*      */   public void setEnforcePasswordPolicy(boolean v) {
/* 1771 */     this.enforcePasswordPolicy = v;
/*      */   }
/*      */   
/*      */   public boolean getEnforcePasswordExpiry() {
/* 1775 */     return this.enforcePasswordExpiry;
/*      */   }
/*      */   
/*      */   public void setEnforcePasswordExpiry(boolean v) {
/* 1779 */     this.enforcePasswordExpiry = v;
/*      */   }
/*      */   
/*      */   public boolean getEnforcePasswordChange() {
/* 1783 */     return this.enforcePasswordChange;
/*      */   }
/*      */   
/*      */   public void setEnforcePasswordChange(boolean v) {
/* 1787 */     this.enforcePasswordChange = v;
/*      */   }
/*      */   
/*      */   public String getConfigOption() {
/* 1791 */     return this.configOption;
/*      */   }
/*      */   
/*      */   public void setConfigOption(String v) {
/* 1795 */     this.configOption = v;
/*      */   }
/*      */   
/*      */   public boolean getLoginDisabled() {
/* 1799 */     return this.loginDisabled;
/*      */   }
/*      */   
/*      */   public void setLoginDisabled(boolean v) {
/* 1803 */     this.loginDisabled = v;
/*      */   }
/*      */   
/*      */   public boolean getSysadmin() {
/* 1807 */     return this.sysadmin;
/*      */   }
/*      */   
/*      */   public void setSysadmin(boolean v) {
/* 1811 */     this.sysadmin = v;
/*      */   }
/*      */   
/*      */   public boolean getSecurityadmin() {
/* 1815 */     return this.securityadmin;
/*      */   }
/*      */   
/*      */   public void setSecurityadmin(boolean v) {
/* 1819 */     this.securityadmin = v;
/*      */   }
/*      */   
/*      */   public boolean getServeradmin() {
/* 1823 */     return this.serveradmin;
/*      */   }
/*      */   
/*      */   public void setServeradmin(boolean v) {
/* 1827 */     this.serveradmin = v;
/*      */   }
/*      */   
/*      */   public boolean getSetupadmin() {
/* 1831 */     return this.setupadmin;
/*      */   }
/*      */   
/*      */   public void setSetupadmin(boolean v) {
/* 1835 */     this.setupadmin = v;
/*      */   }
/*      */   
/*      */   public boolean getProcessadmin() {
/* 1839 */     return this.processadmin;
/*      */   }
/*      */   
/*      */   public void setProcessadmin(boolean v) {
/* 1843 */     this.processadmin = v;
/*      */   }
/*      */   
/*      */   public boolean getDiskadmin() {
/* 1847 */     return this.diskadmin;
/*      */   }
/*      */   
/*      */   public void setDiskadmin(boolean v) {
/* 1851 */     this.diskadmin = v;
/*      */   }
/*      */   
/*      */   public boolean getDbcreator() {
/* 1855 */     return this.dbcreator;
/*      */   }
/*      */   
/*      */   public void setDbcreator(boolean v) {
/* 1859 */     this.dbcreator = v;
/*      */   }
/*      */   
/*      */   public boolean getBulkadmin() {
/* 1863 */     return this.bulkadmin;
/*      */   }
/*      */   
/*      */   public void setBulkadmin(boolean v) {
/* 1867 */     this.bulkadmin = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getVersion()
/*      */   {
/* 1879 */     return this.version;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVersion(String v)
/*      */   {
/* 1888 */     this.version = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getRole()
/*      */   {
/* 1896 */     return this.role;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRole(String v)
/*      */   {
/* 1905 */     this.role = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getClearpollscount()
/*      */   {
/* 1914 */     return this.clearpollscount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setClearpollscount(int v)
/*      */   {
/* 1923 */     this.clearpollscount = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWarningpollscount()
/*      */   {
/* 1933 */     return this.warningpollscount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWarningpollscount(int v)
/*      */   {
/* 1942 */     this.warningpollscount = v;
/*      */   }
/*      */   
/*      */   public void setMin_criticalpollscount(int v)
/*      */   {
/* 1947 */     this.min_criticalpollscount = v;
/*      */   }
/*      */   
/*      */   public int getMin_criticalpollscount()
/*      */   {
/* 1952 */     return this.min_criticalpollscount;
/*      */   }
/*      */   
/*      */   public void setMin_warningpollscount(int v)
/*      */   {
/* 1957 */     this.min_warningpollscount = v;
/*      */   }
/*      */   
/*      */   public int getMin_warningpollscount()
/*      */   {
/* 1962 */     return this.min_warningpollscount;
/*      */   }
/*      */   
/*      */   public void setMin_clearpollscount(int v)
/*      */   {
/* 1967 */     this.min_clearpollscount = v;
/*      */   }
/*      */   
/*      */   public int getMin_clearpollscount()
/*      */   {
/* 1972 */     return this.min_clearpollscount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getCriticalpollscount()
/*      */   {
/* 1981 */     return this.criticalpollscount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCriticalpollscount(int v)
/*      */   {
/* 1990 */     this.criticalpollscount = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEnableupload()
/*      */   {
/* 1999 */     return this.enableupload;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEnableupload(String v)
/*      */   {
/* 2008 */     this.enableupload = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMode()
/*      */   {
/* 2017 */     return this.mode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMode(String v)
/*      */   {
/* 2026 */     this.mode = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setJtaskMethod(String jtaskMethod)
/*      */   {
/* 2032 */     this.jtaskMethod = jtaskMethod;
/*      */   }
/*      */   
/*      */   public String getJtaskMethod() {
/* 2036 */     return this.jtaskMethod;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSelectedMG()
/*      */   {
/* 2042 */     return this.selectedMG;
/*      */   }
/*      */   
/*      */   public void setSelectedMG(String selectedMG) {
/* 2046 */     this.selectedMG = selectedMG;
/*      */   }
/*      */   
/*      */   public void setMaillist(ArrayList ml)
/*      */   {
/* 2051 */     this.maillist = ml;
/*      */   }
/*      */   
/*      */   public ArrayList getMaillist() {
/* 2055 */     return this.maillist;
/*      */   }
/*      */   
/*      */   public void setMglist(ArrayList mgl)
/*      */   {
/* 2060 */     this.mglist = mgl;
/*      */   }
/*      */   
/*      */   public ArrayList getMglist() {
/* 2064 */     return this.mglist;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setJrelist(ArrayList jl)
/*      */   {
/* 2070 */     this.jrelist = jl;
/*      */   }
/*      */   
/*      */   public ArrayList getJrelist() {
/* 2074 */     return this.jrelist;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setHostlist(ArrayList hl)
/*      */   {
/* 2081 */     this.hostlist = hl;
/*      */   }
/*      */   
/*      */   public void setHostType(String hostType) {
/* 2085 */     this.hostType = hostType;
/*      */   }
/*      */   
/*      */   public ArrayList getHostlist() {
/* 2089 */     return this.hostlist;
/*      */   }
/*      */   
/*      */   public int getTdcount() {
/* 2093 */     return this.tdcount;
/*      */   }
/*      */   
/*      */   public void setTdcount(int tc) {
/* 2097 */     this.tdcount = tc;
/*      */   }
/*      */   
/*      */   public int getTddelay()
/*      */   {
/* 2102 */     return this.tddelay;
/*      */   }
/*      */   
/*      */   public void setTddelay(int td) {
/* 2106 */     this.tddelay = td;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getJavaaction()
/*      */   {
/* 2112 */     return this.javaaction;
/*      */   }
/*      */   
/*      */   public void setJavaaction(String ja)
/*      */   {
/* 2117 */     this.javaaction = ja;
/*      */   }
/*      */   
/*      */   public String getSelectedjre()
/*      */   {
/* 2122 */     return this.selectedjre;
/*      */   }
/*      */   
/*      */   public void setSelectedjre(String seljre)
/*      */   {
/* 2127 */     this.selectedjre = seljre;
/*      */   }
/*      */   
/*      */   public String getSelectedhost()
/*      */   {
/* 2132 */     return this.selectedhost;
/*      */   }
/*      */   
/*      */   public void setSelectedhost(String selhost) {
/* 2136 */     this.selectedhost = selhost;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAppmanageros()
/*      */   {
/* 2146 */     return this.appmanageros;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDelimiter()
/*      */   {
/* 2155 */     return this.delimiter;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDelimiter(String v)
/*      */   {
/* 2164 */     this.delimiter = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getOutputfile()
/*      */   {
/* 2174 */     return this.outputfile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOutputfile(String v)
/*      */   {
/* 2183 */     this.outputfile = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAppmanageros(String v)
/*      */   {
/* 2192 */     this.appmanageros = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSnmptelnetport()
/*      */   {
/* 2201 */     return this.snmptelnetport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSnmptelnetport(String v)
/*      */   {
/* 2210 */     this.snmptelnetport = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNetworkAddress()
/*      */   {
/* 2219 */     return this.networkAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNetworkAddress(String v)
/*      */   {
/* 2228 */     this.networkAddress = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getStartingIP()
/*      */   {
/* 2238 */     return this.startingIP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStartingIP(String v)
/*      */   {
/* 2247 */     this.startingIP = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEndingIP()
/*      */   {
/* 2257 */     return this.endingIP;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEndingIP(String v)
/*      */   {
/* 2266 */     this.endingIP = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getInstance()
/*      */   {
/* 2274 */     return this.instance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setInstance(String v)
/*      */   {
/* 2283 */     this.instance = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getOs()
/*      */   {
/* 2293 */     return this.os;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOs(String v)
/*      */   {
/* 2302 */     this.os = v;
/*      */   }
/*      */   
/*      */   public String getProtocol()
/*      */   {
/* 2307 */     return this.protocol;
/*      */   }
/*      */   
/*      */   public void setProtocol(String p)
/*      */   {
/* 2312 */     this.protocol = p;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUsername()
/*      */   {
/* 2322 */     return this.username;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUsername(String v)
/*      */   {
/* 2331 */     this.username = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPassword()
/*      */   {
/* 2340 */     return this.password;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPassword(String v)
/*      */   {
/* 2349 */     this.password = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUploadDir()
/*      */   {
/* 2358 */     return this.uploadDir;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUploadDir(String v)
/*      */   {
/* 2367 */     this.uploadDir = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHaid()
/*      */   {
/* 2377 */     return this.haid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setHaid(String v)
/*      */   {
/* 2386 */     this.haid = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getOrganization()
/*      */   {
/* 2395 */     return this.organization;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOrganization(String v)
/*      */   {
/* 2404 */     this.organization = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMonitor()
/*      */   {
/* 2415 */     return this.monitor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMonitor(String v)
/*      */   {
/* 2424 */     this.monitor = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public FormFile getTheFile()
/*      */   {
/* 2432 */     return this.theFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setTheFile(FormFile theFile)
/*      */   {
/* 2439 */     this.theFile = theFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public FormFile getQqfile()
/*      */   {
/* 2446 */     return this.theFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setQqfile(FormFile theFile)
/*      */   {
/* 2453 */     this.theFile = theFile;
/*      */   }
/*      */   
/*      */ 
/*      */   public FormFile getReportLogo()
/*      */   {
/* 2459 */     return this.reportLogo;
/*      */   }
/*      */   
/*      */   public void setReportLogo(FormFile reportLogo) {
/* 2463 */     this.reportLogo = reportLogo;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getObjectID()
/*      */   {
/* 2471 */     return this.objectID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setObjectID(String v)
/*      */   {
/* 2480 */     this.objectID = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2486 */   private String[] emailcheckbox = new String[0];
/*      */   
/*      */   public String getMailMsg()
/*      */   {
/* 2490 */     return this.mailMsg;
/*      */   }
/*      */   
/*      */   public void setMailMsg(String msg)
/*      */   {
/* 2495 */     this.mailMsg = msg;
/*      */   }
/*      */   
/* 2498 */   private String netmask = "255.255.255.0";
/*      */   
/* 2500 */   private String organization = "-";
/*      */   
/* 2502 */   private int PollInterval = 5;
/*      */   
/*      */   public int getPollInterval()
/*      */   {
/* 2506 */     return this.PollInterval;
/*      */   }
/*      */   
/*      */   public void setPollInterval(int time) {
/* 2510 */     this.PollInterval = time;
/*      */   }
/*      */   
/* 2513 */   private int queryCount = 5;
/*      */   
/*      */   public int getQueryCount()
/*      */   {
/* 2517 */     return this.queryCount;
/*      */   }
/*      */   
/*      */   public void setQueryCount(int count) {
/* 2521 */     this.queryCount = count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setnetmask(String temp)
/*      */   {
/* 2529 */     this.netmask = temp;
/*      */   }
/*      */   
/*      */   public String getnetmask()
/*      */   {
/* 2534 */     return this.netmask;
/*      */   }
/*      */   
/*      */   public String[] getEmailcheckbox()
/*      */   {
/* 2539 */     return this.emailcheckbox;
/*      */   }
/*      */   
/*      */   public void setEmailcheckbox(String[] arg) {
/* 2543 */     this.emailcheckbox = arg;
/*      */   }
/*      */   
/*      */   public int getId() {
/* 2547 */     return this.id;
/*      */   }
/*      */   
/*      */   public void setId(int temp)
/*      */   {
/* 2552 */     this.id = temp;
/*      */   }
/*      */   
/*      */   public String getMNo()
/*      */   {
/* 2557 */     return this.mNo;
/*      */   }
/*      */   
/*      */   public void setMNo(String temp)
/*      */   {
/* 2562 */     this.mNo = temp;
/*      */   }
/*      */   
/* 2565 */   public String getFromaddress() { return this.fromaddress; }
/*      */   
/*      */ 
/*      */   public void setFromaddress(String temp)
/*      */   {
/* 2570 */     this.fromaddress = temp;
/*      */   }
/*      */   
/*      */   public String getFromaddress1()
/*      */   {
/* 2575 */     return this.fromaddress1;
/*      */   }
/*      */   
/*      */   public void setFromaddress1(String temp)
/*      */   {
/* 2580 */     this.fromaddress1 = temp;
/*      */   }
/*      */   
/* 2583 */   public String getToaddress() { return this.toaddress; }
/*      */   
/*      */ 
/*      */   public void setToaddress(String temp)
/*      */   {
/* 2588 */     this.toaddress = temp;
/*      */   }
/*      */   
/* 2591 */   public String getSubject() { return this.subject; }
/*      */   
/*      */ 
/*      */   public void setSubject(String temp)
/*      */   {
/* 2596 */     this.subject = temp;
/*      */   }
/*      */   
/*      */   public String getMessage() {
/* 2600 */     return this.message;
/*      */   }
/*      */   
/*      */   public void setMessage(String temp)
/*      */   {
/* 2605 */     this.message = temp;
/*      */   }
/*      */   
/* 2608 */   public String getSmtpserver() { return this.smtpserver; }
/*      */   
/*      */ 
/*      */   public void setSmtpserver(String temp)
/*      */   {
/* 2613 */     this.smtpserver = temp;
/*      */   }
/*      */   
/* 2616 */   public int getSmtpport() { return this.smtpport; }
/*      */   
/*      */ 
/*      */   public void setSmtpport(int temp)
/*      */   {
/* 2621 */     this.smtpport = temp;
/*      */   }
/*      */   
/*      */   public String getSmtpsecserver() {
/* 2625 */     return this.smtpsecserver;
/*      */   }
/*      */   
/*      */   public void setSmtpsecserver(String temp)
/*      */   {
/* 2630 */     this.smtpsecserver = temp;
/*      */   }
/*      */   
/* 2633 */   public int getSmtpsecport() { return this.smtpsecport; }
/*      */   
/*      */   public void setSmtpsecport(int temp)
/*      */   {
/* 2637 */     this.smtpsecport = temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSeccheck(boolean temp)
/*      */   {
/* 2643 */     this.seccheck = temp;
/*      */   }
/*      */   
/*      */   public boolean getSeccheck() {
/* 2647 */     return this.seccheck;
/*      */   }
/*      */   
/*      */   public void setPrmTlsAuth(boolean temp) {
/* 2651 */     this.prmTlsAuth = temp;
/*      */   }
/*      */   
/*      */   public boolean getPrmTlsAuth() {
/* 2655 */     return this.prmTlsAuth;
/*      */   }
/*      */   
/*      */   public void setSecTlsAuth(boolean temp) {
/* 2659 */     this.secTlsAuth = temp;
/*      */   }
/*      */   
/*      */   public boolean getSecTlsAuth() {
/* 2663 */     return this.secTlsAuth;
/*      */   }
/*      */   
/*      */   public String getMethod() {
/* 2667 */     return this.method;
/*      */   }
/*      */   
/*      */   public void setMethod(String method) {
/* 2671 */     this.method = method;
/*      */   }
/*      */   
/*      */   public String getDisplayname()
/*      */   {
/* 2676 */     return this.displayname;
/*      */   }
/*      */   
/*      */   public void setDisplayname(String displayname) {
/* 2680 */     this.displayname = displayname;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getQuery()
/*      */   {
/* 2686 */     return this.query;
/*      */   }
/*      */   
/*      */   public void setQuery(String query) {
/* 2690 */     this.query = query;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getAbortafter()
/*      */   {
/* 2696 */     return this.abortafter;
/*      */   }
/*      */   
/*      */   public void setAbortafter(String abortafter) {
/* 2700 */     this.abortafter = abortafter;
/*      */   }
/*      */   
/*      */   public String getAppendout() {
/* 2704 */     return this.appendout;
/*      */   }
/*      */   
/*      */   public void setAppendout(String appendout) {
/* 2708 */     this.appendout = appendout;
/*      */   }
/*      */   
/*      */   public String getAppenderror()
/*      */   {
/* 2713 */     return this.appenderror;
/*      */   }
/*      */   
/*      */   public void setAppenderror(String appenderror) {
/* 2717 */     this.appenderror = appenderror;
/*      */   }
/*      */   
/*      */   public String getCommand() {
/* 2721 */     return this.command;
/*      */   }
/*      */   
/*      */   public void setCommand(String command) {
/* 2725 */     this.command = command;
/*      */   }
/*      */   
/* 2728 */   private String command = "";
/* 2729 */   private String appenderror = "";
/* 2730 */   private String appendout = "";
/* 2731 */   private String abortafter = "10";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2738 */   private String type = "1";
/*      */   
/* 2740 */   private String description = "";
/*      */   
/* 2742 */   private String conditionJoiner = "OR";
/*      */   
/* 2744 */   private String secondarycriticalexist = "false";
/*      */   
/* 2746 */   private String secondarywarningexist = "false";
/*      */   
/* 2748 */   private String secondaryinfoexist = "false";
/*      */   
/* 2750 */   private String secondarycriticalthresholdcondition = "GT";
/*      */   
/* 2752 */   private String criticalthresholdcondition = "GT";
/*      */   
/* 2754 */   private String criticalthresholdvalue = "5";
/*      */   
/* 2756 */   private String secondarycriticalthresholdvalue = "5";
/*      */   
/* 2758 */   private String criticalthresholdmessage = FormatUtil.getString("am.webclient.alertmessage.critcal.text");
/*      */   
/* 2760 */   private String warningthresholdcondition = "EQ";
/*      */   
/* 2762 */   private String warningthresholdvalue = "5";
/*      */   
/* 2764 */   private String warningthresholdmessage = FormatUtil.getString("am.webclient.alertmessage.warning.text");
/*      */   
/* 2766 */   private String secondarywarningthresholdcondition = "EQ";
/*      */   
/* 2768 */   private String secondarywarningthresholdvalue = "5";
/*      */   
/*      */ 
/* 2771 */   private String infothresholdcondition = "LT";
/*      */   
/* 2773 */   private String infothresholdvalue = "5";
/*      */   
/* 2775 */   private String infothresholdmessage = FormatUtil.getString("am.webclient.alertmessage.clear.text");
/*      */   
/* 2777 */   private String secondaryinfothresholdcondition = "LT";
/*      */   
/* 2779 */   private String secondaryinfothresholdvalue = "5";
/* 2780 */   private String criticalconditionJoiner = "OR";
/* 2781 */   private String warningconditionJoiner = "OR";
/* 2782 */   private String infoconditionJoiner = "OR";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String secondaryexist;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getType()
/*      */   {
/* 2796 */     return this.type;
/*      */   }
/*      */   
/*      */   public void setType(String temp)
/*      */   {
/* 2801 */     this.type = temp;
/*      */   }
/*      */   
/* 2804 */   public String getDescription() { return this.description; }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setDescription(String temp)
/*      */   {
/* 2810 */     temp = temp.replaceAll("&quot;", "\"");
/* 2811 */     temp = temp.replaceAll("&#39;", "'");
/* 2812 */     temp = temp.replaceAll("&#96;", "`");
/* 2813 */     temp = temp.replaceAll("&lt;", "<");
/* 2814 */     temp = temp.replaceAll("&gt;", ">");
/*      */     
/* 2816 */     this.description = temp;
/*      */   }
/*      */   
/*      */   public String getCriticalconditionjoiner()
/*      */   {
/* 2821 */     return this.criticalconditionJoiner;
/*      */   }
/*      */   
/*      */   public void setCriticalconditionjoiner(String conditionjoiner)
/*      */   {
/* 2826 */     this.criticalconditionJoiner = conditionjoiner;
/*      */   }
/*      */   
/*      */   public String getWarningconditionjoiner()
/*      */   {
/* 2831 */     return this.warningconditionJoiner;
/*      */   }
/*      */   
/*      */   public void setWarningconditionjoiner(String conditionjoiner)
/*      */   {
/* 2836 */     this.warningconditionJoiner = conditionjoiner;
/*      */   }
/*      */   
/*      */   public String getInfoconditionjoiner()
/*      */   {
/* 2841 */     return this.infoconditionJoiner;
/*      */   }
/*      */   
/*      */   public void setInfoconditionjoiner(String conditionjoiner)
/*      */   {
/* 2846 */     this.infoconditionJoiner = conditionjoiner;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getCriticalthresholdcondition()
/*      */   {
/* 2852 */     return this.criticalthresholdcondition;
/*      */   }
/*      */   
/*      */   public void setCriticalthresholdcondition(String temp)
/*      */   {
/* 2857 */     this.criticalthresholdcondition = temp;
/*      */   }
/*      */   
/*      */   public String getSecondarycriticalthresholdcondition()
/*      */   {
/* 2862 */     return this.secondarycriticalthresholdcondition;
/*      */   }
/*      */   
/*      */   public void setSecondarycriticalthresholdcondition(String temp)
/*      */   {
/* 2867 */     this.secondarycriticalthresholdcondition = temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getCriticalthresholdvalue()
/*      */   {
/* 2873 */     return this.criticalthresholdvalue;
/*      */   }
/*      */   
/*      */   public void setCriticalthresholdvalue(String temp) {
/* 2877 */     this.criticalthresholdvalue = temp;
/*      */   }
/*      */   
/*      */   public String getSecondarycriticalthresholdvalue() {
/* 2881 */     return this.secondarycriticalthresholdvalue;
/*      */   }
/*      */   
/*      */   public void setSecondarycriticalthresholdvalue(String secCriticalValue) {
/* 2885 */     this.secondarycriticalthresholdvalue = secCriticalValue;
/*      */   }
/*      */   
/*      */   public String getCriticalthresholdmessage() {
/* 2889 */     return this.criticalthresholdmessage;
/*      */   }
/*      */   
/*      */   public void setCriticalthresholdmessage(String temp)
/*      */   {
/* 2894 */     this.criticalthresholdmessage = temp;
/*      */   }
/*      */   
/*      */   public String getWarningthresholdcondition() {
/* 2898 */     return this.warningthresholdcondition;
/*      */   }
/*      */   
/*      */   public void setWarningthresholdcondition(String temp)
/*      */   {
/* 2903 */     this.warningthresholdcondition = temp;
/*      */   }
/*      */   
/*      */   public String getSecondarywarningthresholdcondition() {
/* 2907 */     return this.secondarywarningthresholdcondition;
/*      */   }
/*      */   
/*      */   public void setSecondarywarningthresholdcondition(String temp)
/*      */   {
/* 2912 */     this.secondarywarningthresholdcondition = temp;
/*      */   }
/*      */   
/*      */   public String getWarningthresholdvalue() {
/* 2916 */     return this.warningthresholdvalue;
/*      */   }
/*      */   
/*      */   public void setWarningthresholdvalue(String temp)
/*      */   {
/* 2921 */     this.warningthresholdvalue = temp;
/*      */   }
/*      */   
/*      */   public String getSecondarywarningthresholdvalue()
/*      */   {
/* 2926 */     return this.secondarywarningthresholdvalue;
/*      */   }
/*      */   
/*      */   public void setSecondarywarningthresholdvalue(String secCriticalValue) {
/* 2930 */     this.secondarywarningthresholdvalue = secCriticalValue;
/*      */   }
/*      */   
/*      */   public String getWarningthresholdmessage() {
/* 2934 */     return this.warningthresholdmessage;
/*      */   }
/*      */   
/*      */   public void setWarningthresholdmessage(String temp) {
/* 2938 */     this.warningthresholdmessage = temp;
/*      */   }
/*      */   
/*      */   public String getInfothresholdcondition() {
/* 2942 */     return this.infothresholdcondition;
/*      */   }
/*      */   
/*      */   public void setInfothresholdcondition(String temp)
/*      */   {
/* 2947 */     this.infothresholdcondition = temp;
/*      */   }
/*      */   
/*      */   public String getSecondarycriticalexist()
/*      */   {
/* 2952 */     return this.secondarycriticalexist;
/*      */   }
/*      */   
/*      */   public void setSecondarycriticalexist(String secondaryexist)
/*      */   {
/* 2957 */     this.secondarycriticalexist = secondaryexist;
/*      */   }
/*      */   
/*      */   public String getSecondarywarningexist()
/*      */   {
/* 2962 */     return this.secondarywarningexist;
/*      */   }
/*      */   
/*      */   public void setSecondarywarningexist(String secondaryexist)
/*      */   {
/* 2967 */     this.secondarywarningexist = secondaryexist;
/*      */   }
/*      */   
/*      */   public String getSecondaryinfoexist()
/*      */   {
/* 2972 */     return this.secondaryinfoexist;
/*      */   }
/*      */   
/*      */   public void setSecondaryinfoexist(String secondaryexist)
/*      */   {
/* 2977 */     this.secondaryinfoexist = secondaryexist;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSecondaryinfothresholdcondition()
/*      */   {
/* 2983 */     return this.secondaryinfothresholdcondition;
/*      */   }
/*      */   
/*      */   public void setSecondaryinfothresholdcondition(String temp)
/*      */   {
/* 2988 */     this.secondaryinfothresholdcondition = temp;
/*      */   }
/*      */   
/*      */   public String getInfothresholdvalue() {
/* 2992 */     return this.infothresholdvalue;
/*      */   }
/*      */   
/*      */   public void setInfothresholdvalue(String temp)
/*      */   {
/* 2997 */     this.infothresholdvalue = temp;
/*      */   }
/*      */   
/*      */   public String getSecondaryinfothresholdvalue() {
/* 3001 */     return this.secondaryinfothresholdvalue;
/*      */   }
/*      */   
/*      */   public void setSecondaryinfothresholdvalue(String secCriticalValue) {
/* 3005 */     this.secondaryinfothresholdvalue = secCriticalValue;
/*      */   }
/*      */   
/*      */   public String getInfothresholdmessage() {
/* 3009 */     return this.infothresholdmessage;
/*      */   }
/*      */   
/*      */   public void setInfothresholdmessage(String temp)
/*      */   {
/* 3014 */     this.infothresholdmessage = temp;
/*      */   }
/*      */   
/*      */   public String getCategory() {
/* 3018 */     return this.category;
/*      */   }
/*      */   
/*      */   public void setCategory(String temp)
/*      */   {
/* 3023 */     this.category = temp;
/*      */   }
/*      */   
/*      */   public String getSubCategory() {
/* 3027 */     return this.subCategory;
/*      */   }
/*      */   
/*      */   public void setSubCategory(String subCategory) {
/* 3031 */     this.subCategory = subCategory;
/*      */   }
/*      */   
/*      */   public String getItem() {
/* 3035 */     return this.item;
/*      */   }
/*      */   
/*      */   public void setItem(String item) {
/* 3039 */     this.item = item;
/*      */   }
/*      */   
/*      */   public String getExternalhost() {
/* 3043 */     return this.externalhost;
/*      */   }
/*      */   
/*      */   public void setExternalhost(String temp)
/*      */   {
/* 3048 */     this.externalhost = temp;
/*      */   }
/*      */   
/*      */   public String getPriority()
/*      */   {
/* 3053 */     return this.priority;
/*      */   }
/*      */   
/*      */   public void setPriority(String temp) {
/* 3057 */     this.priority = temp;
/*      */   }
/*      */   
/*      */   public String getGroup() {
/* 3061 */     return this.group;
/*      */   }
/*      */   
/*      */   public void setGroup(String temp) {
/* 3065 */     this.group = temp;
/*      */   }
/*      */   
/*      */   public String getTechnician() {
/* 3069 */     return this.technician;
/*      */   }
/*      */   
/*      */   public void setTechnician(String temp)
/*      */   {
/* 3074 */     this.technician = temp;
/*      */   }
/*      */   
/*      */   public String getHost()
/*      */   {
/* 3079 */     return this.host;
/*      */   }
/*      */   
/*      */   public void setHost(String temp)
/*      */   {
/* 3084 */     this.host = temp;
/*      */   }
/*      */   
/*      */   public String getHostname() {
/* 3088 */     return this.hostname;
/*      */   }
/*      */   
/*      */   public void setHostname(String temp)
/*      */   {
/* 3093 */     this.hostname = temp;
/*      */   }
/*      */   
/*      */   public int getRetries() {
/* 3097 */     return this.retries;
/*      */   }
/*      */   
/*      */   public void setRetries(int cnt) {
/* 3101 */     this.retries = cnt;
/*      */   }
/*      */   
/*      */   public int getPtimeout()
/*      */   {
/* 3106 */     return this.ptimeout;
/*      */   }
/*      */   
/*      */   public void setPtimeout(int ptimeout) {
/* 3110 */     this.ptimeout = ptimeout;
/*      */   }
/*      */   
/* 3113 */   public String getPort() { return this.port; }
/*      */   
/*      */ 
/*      */   public void setPort(String temp)
/*      */   {
/* 3118 */     this.port = temp;
/*      */   }
/*      */   
/* 3121 */   public String getSearch() { return this.search; }
/*      */   
/*      */ 
/*      */   public void setSearch(String temp)
/*      */   {
/* 3126 */     this.search = temp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List getPredictions()
/*      */   {
/* 3135 */     String[] conditionValues = { "hour", "day", "month" };
/* 3136 */     String[] conditionText = { "with in 6 hrs", "with in 1 day", "with in 1 month" };
/* 3137 */     ArrayList list = new ArrayList();
/* 3138 */     for (int i = 0; i < conditionValues.length; i++)
/*      */     {
/* 3140 */       list.add(new Option(conditionText[i], conditionValues[i]));
/*      */     }
/* 3142 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList getRBMAgents()
/*      */   {
/* 3149 */     ArrayList agentList = new ArrayList();
/*      */     
/* 3151 */     agentList.add(new Option(FormatUtil.getString("am.webclient.rbm.selectagent.text"), "-"));
/*      */     
/*      */     try
/*      */     {
/* 3155 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3156 */       String qry = "select AGENTID,DISPLAYNAME from AM_RBMAGENTDATA";
/* 3157 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 3158 */       while (rs.next())
/*      */       {
/* 3160 */         String id = "" + rs.getObject(1);
/* 3161 */         String val = rs.getString(2);
/* 3162 */         agentList.add(new Option(val, id));
/*      */       }
/*      */       
/* 3165 */       AMConnectionPool.closeStatement(rs);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 3171 */       e.printStackTrace();
/*      */     }
/* 3173 */     return agentList;
/*      */   }
/*      */   
/*      */ 
/*      */   public List getRBMScripts()
/*      */   {
/* 3179 */     ArrayList list = new ArrayList();
/* 3180 */     list.add(new Option(FormatUtil.getString("am.webclient.rbm.selectscript.text"), "-"));
/*      */     
/*      */     try
/*      */     {
/* 3184 */       File f = new File("." + File.separator + "projects" + File.separator + "rbmsuite" + File.separator + "webscripts");
/* 3185 */       if (f.exists())
/*      */       {
/* 3187 */         File[] all = f.listFiles();
/* 3188 */         for (int i = 0; i < all.length; i++)
/*      */         {
/* 3190 */           File wcsFile = new File(all[i].getPath() + File.separator + all[i].getName() + ".wcs");
/* 3191 */           if (wcsFile.exists())
/*      */           {
/* 3193 */             if (!all[i].isFile())
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3199 */               list.add(new Option(all[i].getName(), all[i].getName()));
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3210 */       e.printStackTrace();
/*      */     }
/*      */     
/* 3213 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public List getConditions()
/*      */   {
/* 3219 */     String[] conditionValues = { "LT", "GT", "EQ", "NE", "LE", "GE" };
/* 3220 */     String[] conditionText = { "<", ">", "=", "!=", "<=", ">=" };
/* 3221 */     ArrayList list = new ArrayList();
/* 3222 */     for (int i = 0; i < conditionValues.length; i++)
/*      */     {
/* 3224 */       list.add(new Option(conditionText[i], conditionValues[i]));
/*      */     }
/* 3226 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public List getConditionjoinerlist()
/*      */   {
/* 3232 */     String[] conditionValues = { "OR", "AND" };
/* 3233 */     String[] conditionText = { "OR", "AND" };
/* 3234 */     ArrayList list = new ArrayList();
/* 3235 */     for (int i = 0; i < conditionValues.length; i++)
/*      */     {
/* 3237 */       list.add(new Option(conditionText[i], conditionValues[i]));
/*      */     }
/* 3239 */     return list;
/*      */   }
/*      */   
/*      */   public List getPatternMatcherConditions()
/*      */   {
/* 3244 */     String[] conditionValues = { "CT", "DC", "QL", "NQ", "SW", "EW" };
/* 3245 */     String[] conditionText = { FormatUtil.getString("am.fault.conditions.string.contains"), FormatUtil.getString("am.fault.conditions.string.doesnotcontain"), FormatUtil.getString("am.fault.conditions.string.equalto"), FormatUtil.getString("am.fault.conditions.string.notequalto"), FormatUtil.getString("am.fault.conditions.string.startswith"), FormatUtil.getString("am.fault.conditions.string.endswith") };
/* 3246 */     ArrayList list = new ArrayList();
/* 3247 */     for (int i = 0; i < conditionValues.length; i++)
/*      */     {
/* 3249 */       list.add(new Option(conditionText[i], conditionValues[i]));
/*      */     }
/* 3251 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean validNumber(String number)
/*      */   {
/*      */     try
/*      */     {
/* 3269 */       Double.parseDouble(number);
/* 3270 */       return true;
/*      */     }
/*      */     catch (NumberFormatException ne) {}
/*      */     
/* 3274 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 3279 */   private String popPort = "110";
/*      */   
/*      */   public String getPopPort()
/*      */   {
/* 3283 */     return this.popPort;
/*      */   }
/*      */   
/*      */   public void setPopPort(String popPort)
/*      */   {
/* 3288 */     this.popPort = popPort;
/*      */   }
/*      */   
/*      */ 
/* 3292 */   private boolean popenabled = false;
/*      */   
/*      */   public boolean ispopenabled() {
/* 3295 */     return this.popenabled;
/*      */   }
/*      */   
/*      */   public void setpopenabled(boolean popenabled) {
/* 3299 */     this.popenabled = popenabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrapDestinationAddress()
/*      */   {
/* 3310 */     return this.trapDestinationAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTrapDestinationAddress(String trapDestinationAddress)
/*      */   {
/* 3319 */     this.trapDestinationAddress = trapDestinationAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrapCommunity()
/*      */   {
/* 3329 */     return this.trapCommunity;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTrapCommunity(String v)
/*      */   {
/* 3338 */     this.trapCommunity = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTrapDestinationPort()
/*      */   {
/* 3348 */     return this.trapDestinationPort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTrapDestinationPort(int v)
/*      */   {
/* 3357 */     this.trapDestinationPort = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getGlobalTrapAddress()
/*      */   {
/* 3367 */     return this.globalTrapAddress;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapAddress(String v) {
/* 3371 */     this.globalTrapAddress = v;
/*      */   }
/*      */   
/*      */   public int getGlobalTrapPort() {
/* 3375 */     return this.globalTrapPort;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapPort(int v) {
/* 3379 */     this.globalTrapPort = v;
/*      */   }
/*      */   
/*      */   public String getGlobalTrapCommunity() {
/* 3383 */     return this.globalTrapCommunity;
/*      */   }
/*      */   
/*      */   public void setGlobalTrapCommunity(String v) {
/* 3387 */     this.globalTrapCommunity = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSnmpVersionList()
/*      */   {
/* 3397 */     return this.snmpVersionList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSnmpVersionList(String v)
/*      */   {
/* 3406 */     this.snmpVersionList = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV1TrapGenericType()
/*      */   {
/* 3416 */     return this.v1TrapGenericType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV1TrapGenericType(String v)
/*      */   {
/* 3425 */     this.v1TrapGenericType = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV1TrapSpecificType()
/*      */   {
/* 3435 */     return this.v1TrapSpecificType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV1TrapSpecificType(String v)
/*      */   {
/* 3444 */     this.v1TrapSpecificType = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getv1TrapEnterprise()
/*      */   {
/* 3458 */     return this.v1TrapEnterprise;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV1TrapEnterprise(String v)
/*      */   {
/* 3471 */     this.v1TrapEnterprise = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV2SNMPTrapOID()
/*      */   {
/* 3481 */     return this.v2SNMPTrapOID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV2SNMPTrapOID(String v)
/*      */   {
/* 3490 */     this.v2SNMPTrapOID = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3SNMPTrapOID()
/*      */   {
/* 3499 */     return this.v3SNMPTrapOID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3SNMPTrapOID(String v)
/*      */   {
/* 3508 */     this.v3SNMPTrapOID = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3TrapAuthProtocol()
/*      */   {
/* 3517 */     return this.v3TrapAuthProtocol;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3TrapAuthProtocol(String v)
/*      */   {
/* 3526 */     this.v3TrapAuthProtocol = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3TrapAuthPassword()
/*      */   {
/* 3536 */     return this.v3TrapAuthPassword;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3TrapAuthPassword(String v)
/*      */   {
/* 3545 */     this.v3TrapAuthPassword = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3TrapUser()
/*      */   {
/* 3555 */     return this.v3TrapUser;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3TrapUser(String v)
/*      */   {
/* 3564 */     this.v3TrapUser = v;
/*      */   }
/*      */   
/*      */   public String getV3TrapContextName()
/*      */   {
/* 3569 */     return this.v3TrapContextName;
/*      */   }
/*      */   
/*      */   public void setV3TrapContextName(String v3TrapContextName)
/*      */   {
/* 3574 */     this.v3TrapContextName = v3TrapContextName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3TrapPrivPassword()
/*      */   {
/* 3584 */     return this.v3TrapPrivPassword;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3TrapPrivPassword(String v)
/*      */   {
/* 3593 */     this.v3TrapPrivPassword = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getV3TrapEngineID()
/*      */   {
/* 3604 */     return this.v3TrapEngineID;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setV3TrapEngineID(String v)
/*      */   {
/* 3613 */     this.v3TrapEngineID = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAddHostToHolisticApplication()
/*      */   {
/* 3623 */     return this.addHostToHolisticApplication;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAddHostToHolisticApplication(String v)
/*      */   {
/* 3632 */     this.addHostToHolisticApplication = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getDiscoverHostAlso()
/*      */   {
/* 3639 */     return this.discoverHostAlso;
/*      */   }
/*      */   
/*      */   public void setDiscoverHostAlso(String v)
/*      */   {
/* 3644 */     this.discoverHostAlso = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEnableRCA()
/*      */   {
/* 3653 */     return this.enableRCA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEnableRCA(String v)
/*      */   {
/* 3662 */     this.enableRCA = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMibName()
/*      */   {
/* 3672 */     return this.mibName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMibName(String v)
/*      */   {
/* 3681 */     this.mibName = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrapVarbinds()
/*      */   {
/* 3690 */     return this.trapVarbinds;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTrapVarbinds(String v)
/*      */   {
/* 3699 */     this.trapVarbinds = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setMessageFormat(String messageFormat)
/*      */   {
/* 3705 */     this.messageFormat = messageFormat;
/*      */   }
/*      */   
/*      */   public String getMessageFormat()
/*      */   {
/* 3710 */     return this.messageFormat;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSMSPort()
/*      */   {
/* 3716 */     return this.SMSPort;
/*      */   }
/*      */   
/*      */   public void setSMSPort(String port)
/*      */   {
/* 3721 */     this.SMSPort = port;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSMTPServerUserName()
/*      */   {
/* 3731 */     return this.SMTPServerUserName;
/*      */   }
/*      */   
/*      */   public String getSMTPsecServerUserName() {
/* 3735 */     return this.SMTPsecServerUserName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSMTPServerUserName(String v)
/*      */   {
/* 3743 */     this.SMTPServerUserName = v;
/*      */   }
/*      */   
/*      */   public void setSMTPsecServerUserName(String v) {
/* 3747 */     this.SMTPsecServerUserName = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSMTPServerPassword()
/*      */   {
/* 3756 */     return this.SMTPServerPassword;
/*      */   }
/*      */   
/*      */   public String getSMTPsecServerPassword() {
/* 3760 */     return this.SMTPsecServerPassword;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSMTPServerPassword(String v)
/*      */   {
/* 3769 */     this.SMTPServerPassword = v;
/*      */   }
/*      */   
/*      */   public String getEmailAddress()
/*      */   {
/* 3774 */     return this.emailAddress;
/*      */   }
/*      */   
/*      */   public void setEmailAddress(String email)
/*      */   {
/* 3779 */     this.emailAddress = email;
/*      */   }
/*      */   
/*      */   public String getAdminemailAddress() {
/* 3783 */     return this.adminemailAddress;
/*      */   }
/*      */   
/*      */   public void setAdminemailAddress(String email)
/*      */   {
/* 3788 */     this.adminemailAddress = email;
/*      */   }
/*      */   
/*      */   public void setSMTPsecServerPassword(String v) {
/* 3792 */     this.SMTPsecServerPassword = v;
/*      */   }
/*      */   
/*      */   public String getSecemailAddress()
/*      */   {
/* 3797 */     return this.secemailAddress;
/*      */   }
/*      */   
/*      */   public void setSecemailAddress(String email)
/*      */   {
/* 3802 */     this.secemailAddress = email;
/*      */   }
/*      */   
/* 3805 */   public int getTimeout() { return this.timeout; }
/*      */   
/*      */   public void setTimeout(int time) {
/* 3808 */     this.timeout = time;
/*      */   }
/*      */   
/*      */   public String getSnmpCommunityString() {
/* 3812 */     return this.snmpCommunityString;
/*      */   }
/*      */   
/*      */   public String setSnmpCommunityString(String s) {
/* 3816 */     return this.snmpCommunityString = s;
/*      */   }
/*      */   
/*      */   public String getJndiurl() {
/* 3820 */     return this.jndiurl;
/*      */   }
/*      */   
/*      */   public String setJndiurl(String s) {
/* 3824 */     return this.jndiurl = s;
/*      */   }
/*      */   
/*      */   public String getJmxurl() {
/* 3828 */     return this.jmxurl;
/*      */   }
/*      */   
/*      */   public String setJmxurl(String s) {
/* 3832 */     return this.jmxurl = s;
/*      */   }
/*      */   
/*      */   public boolean isJmxEnabled() {
/* 3836 */     return this.jmxEnabled;
/*      */   }
/*      */   
/*      */   public void setJmxEnabled(boolean jmxEnabled) {
/* 3840 */     this.jmxEnabled = jmxEnabled;
/*      */   }
/*      */   
/*      */   public boolean getLoginSlider() {
/* 3844 */     return this.loginSlider;
/*      */   }
/*      */   
/*      */   public boolean setLoginSlider(boolean loginSlider) {
/* 3848 */     return this.loginSlider = loginSlider;
/*      */   }
/*      */   
/*      */   public boolean getLoginFeatures() {
/* 3852 */     return this.loginFeatures;
/*      */   }
/*      */   
/*      */   public boolean setLoginFeatures(boolean loginFeatures) {
/* 3856 */     return this.loginFeatures = loginFeatures;
/*      */   }
/*      */   
/*      */   public boolean getLoginTopLinks() {
/* 3860 */     return this.loginTopLinks;
/*      */   }
/*      */   
/*      */   public boolean setLoginTopLinks(boolean loginTopLinks) {
/* 3864 */     return this.loginTopLinks = loginTopLinks;
/*      */   }
/*      */   
/*      */   public String getGettingstarted()
/*      */   {
/* 3869 */     return this.gettingstarted;
/*      */   }
/*      */   
/*      */   public String setGettingstarted(String gettingstarted)
/*      */   {
/* 3874 */     return this.gettingstarted = gettingstarted;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDoNotGoToLogoutPage()
/*      */   {
/* 3889 */     return this.doNotGoToLogoutPage;
/*      */   }
/*      */   
/*      */   public void setDoNotGoToLogoutPage(String doNotGoToLogoutPage) {
/* 3893 */     this.doNotGoToLogoutPage = doNotGoToLogoutPage;
/*      */   }
/*      */   
/* 3896 */   private String wizhelp = "true";
/*      */   
/*      */   public String getWizHelp() {
/* 3899 */     return this.wizhelp;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 3904 */   public String setWizHelp(String wizhelp) { return this.wizhelp = wizhelp; }
/*      */   
/* 3906 */   private String defaultmonitorsview = "";
/*      */   
/*      */   public String getDefaultmonitorsview() {
/* 3909 */     return this.defaultmonitorsview;
/*      */   }
/*      */   
/*      */ 
/* 3913 */   public void setDefaultmonitorsview(String view) { this.defaultmonitorsview = view; }
/*      */   
/* 3915 */   private String pophost = "";
/*      */   private ArrayList applications;
/*      */   
/* 3918 */   public String getPopHost() { return this.pophost; }
/*      */   
/*      */ 
/*      */   public void setPopHost(String pophost)
/*      */   {
/* 3923 */     this.pophost = pophost;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setApplications(ArrayList app)
/*      */   {
/* 3929 */     this.applications = app;
/*      */   }
/*      */   
/*      */ 
/* 3933 */   public ArrayList getApplications() { return this.applications; }
/*      */   
/* 3935 */   private String smtpUserName = "";
/* 3936 */   private String smtpPassword = "";
/* 3937 */   private String emailid = "";
/* 3938 */   private boolean smtpauthenabled = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEmailid()
/*      */   {
/* 3945 */     return this.emailid;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setEmailid(String emailid)
/*      */   {
/* 3951 */     this.emailid = emailid;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSmtpPassword()
/*      */   {
/* 3957 */     return this.smtpPassword;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSmtpPassword(String smtpPassword)
/*      */   {
/* 3963 */     this.smtpPassword = smtpPassword;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSmtpUserName()
/*      */   {
/* 3969 */     return this.smtpUserName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setSmtpUserName(String smtpUserName)
/*      */   {
/* 3976 */     this.smtpUserName = smtpUserName;
/*      */   }
/*      */   
/*      */   public List getResourceTypes() {
/* 3980 */     return getResourceTypes(null);
/*      */   }
/*      */   
/*      */ 
/*      */   public List getResourceTypes(HttpServletRequest request)
/*      */   {
/* 3986 */     ArrayList list = new ArrayList();
/* 3987 */     list.add(new Option(FormatUtil.getString("am.monitortab.allmonitors.text"), "All"));
/* 3988 */     HashMap restypedisplaynameMapping = new HashMap();
/*      */     
/* 3990 */     ArrayList addedList = new ArrayList();
/* 3991 */     boolean sanAvailable = false;
/* 3992 */     boolean nwdAvailable = false;
/* 3993 */     boolean emAvailable = false;
/*      */     try
/*      */     {
/* 3996 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3997 */       String groupCondition = "";
/* 3998 */       if (!this.groupType.equals("all"))
/*      */       {
/*      */ 
/* 4001 */         HashMap typeToAdd = GroupComponent.getResourceTypeToSelect(this.groupType);
/* 4002 */         if (typeToAdd.size() > 0)
/*      */         {
/* 4004 */           list.add(new Option((String)typeToAdd.get("name"), (String)typeToAdd.get("value")));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4009 */       String resIds = "";
/* 4010 */       String qryAppend = "";
/* 4011 */       Vector resIds_vector = null;
/* 4012 */       boolean isUserResourceEnabled = false;
/* 4013 */       String loginUserid = null;
/* 4014 */       if ((request != null) && (request.isUserInRole("OPERATOR")))
/*      */       {
/* 4016 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 4018 */           resIds_vector = ClientDBUtil.getResourceIdentity(this.username, request, null);
/*      */ 
/*      */ 
/*      */         }
/* 4022 */         else if (Constants.isUserResourceEnabled()) {
/* 4023 */           isUserResourceEnabled = true;
/* 4024 */           loginUserid = Constants.getLoginUserid(request);
/*      */         } else {
/* 4026 */           resIds_vector = ClientDBUtil.getResourceIdentity(this.username);
/*      */         }
/*      */       }
/*      */       
/* 4030 */       if ((resIds_vector != null) && (resIds_vector.size() != 0))
/*      */       {
/* 4032 */         for (int i = 0; i < resIds_vector.size(); i++)
/*      */         {
/* 4034 */           resIds = resIds + (String)resIds_vector.get(i) + ",";
/*      */         }
/* 4036 */         resIds = resIds.substring(0, resIds.length() - 1);
/* 4037 */         qryAppend = "and AM_ManagedObject.RESOURCEID in (" + resIds + ")";
/*      */       }
/* 4039 */       String qry = null;
/* 4040 */       if (isUserResourceEnabled) {
/* 4041 */         qry = "select  DISTINCT(AM_ManagedObject.TYPE),SUBGROUP,  AM_ManagedResourceType.DISPLAYNAME  from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and TYPE in " + Constants.resourceTypes + " and  RESOURCEGROUP not in ('NWD','EMO','SAN') " + groupCondition + " order by AM_ManagedResourceType.DISPLAYNAME";
/*      */       } else {
/* 4043 */         qry = "select  DISTINCT(AM_ManagedObject.TYPE),SUBGROUP,  AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where TYPE in " + Constants.resourceTypes + " and  RESOURCEGROUP not in ('NWD','EMO','SAN') " + qryAppend + " " + groupCondition + " order by AM_ManagedResourceType.DISPLAYNAME";
/*      */       }
/* 4045 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 4046 */       String qry1 = null;
/* 4047 */       if (isUserResourceEnabled) {
/* 4048 */         qry1 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('NWD') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4050 */         qry1 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('NWD')" + qryAppend;
/*      */       }
/* 4052 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry1);
/* 4053 */       if (rs1.next()) {
/* 4054 */         nwdAvailable = true;
/*      */       }
/* 4056 */       AMConnectionPool.closeStatement(rs1);
/* 4057 */       String qry3 = null;
/* 4058 */       if (isUserResourceEnabled) {
/* 4059 */         qry3 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('SAN') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4061 */         qry3 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('SAN')" + qryAppend;
/*      */       }
/* 4063 */       ResultSet rs3 = AMConnectionPool.executeQueryStmt(qry3);
/* 4064 */       if (rs3.next()) {
/* 4065 */         sanAvailable = true;
/*      */       }
/* 4067 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/* 4069 */       String qry2 = null;
/* 4070 */       if (isUserResourceEnabled) {
/* 4071 */         qry2 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('EMO') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4073 */         qry2 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('EMO')" + qryAppend;
/*      */       }
/* 4075 */       rs1 = AMConnectionPool.executeQueryStmt(qry2);
/* 4076 */       if (rs1.next()) {
/* 4077 */         emAvailable = true;
/*      */       }
/* 4079 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/* 4081 */       while (rs.next())
/*      */       {
/* 4083 */         if (rs.getString("SUBGROUP").equals("File System Monitor"))
/*      */         {
/* 4085 */           if (!addedList.contains("File System Monitor"))
/*      */           {
/* 4087 */             addedList.add(rs.getString("SUBGROUP"));
/* 4088 */             restypedisplaynameMapping.put(rs.getString("SUBGROUP"), rs.getString("DISPLAYNAME"));
/*      */           }
/*      */           
/*      */         }
/* 4092 */         else if (rs.getString("SUBGROUP").equals("Windows"))
/*      */         {
/* 4094 */           if (!addedList.contains("Windows"))
/*      */           {
/* 4096 */             addedList.add(rs.getString("SUBGROUP"));
/* 4097 */             restypedisplaynameMapping.put(rs.getString("SUBGROUP"), "Windows");
/*      */           }
/*      */         }
/*      */         else {
/* 4101 */           addedList.add(rs.getString("SUBGROUP"));
/* 4102 */           restypedisplaynameMapping.put(rs.getString("SUBGROUP"), rs.getString("DISPLAYNAME"));
/*      */         } }
/* 4104 */       rs.close();
/* 4105 */       rs1.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4109 */       exc.printStackTrace();
/*      */     }
/* 4111 */     for (int i = 0; i < addedList.size(); i++)
/*      */     {
/* 4113 */       if ((!OEMUtil.isOEM()) || (!((String)addedList.get(i)).equals("RMI")))
/*      */       {
/*      */ 
/*      */ 
/* 4117 */         if (Constants.resourceTypes.indexOf((String)addedList.get(i)) != -1)
/*      */         {
/* 4119 */           list.add(new Option(FormatUtil.getString((String)restypedisplaynameMapping.get((String)addedList.get(i))), (String)addedList.get(i)));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4124 */     if (sanAvailable)
/*      */     {
/* 4126 */       list.add(new Option("Storage Devices", "SAN"));
/*      */     }
/* 4128 */     if (nwdAvailable)
/*      */     {
/* 4130 */       list.add(new Option("Network Devices", "NWD"));
/*      */     }
/* 4132 */     if (emAvailable)
/*      */     {
/* 4134 */       list.add(new Option("Site24x7 Monitors", "EMO"));
/*      */     }
/* 4136 */     if (DBUtil.getServerConfigValueasBoolean("am.mg.processAndService.associate"))
/*      */     {
/* 4138 */       list.add(new Option(FormatUtil.getString("am.monitortab.process.text"), "Process"));
/* 4139 */       list.add(new Option(FormatUtil.getString("am.monitortab.service.text"), "Service"));
/*      */     }
/*      */     
/* 4142 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getResourceTypes_listview()
/*      */   {
/* 4147 */     return getResourceTypes_listview(null);
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getResourceTypes_listview(HttpServletRequest request)
/*      */   {
/* 4153 */     String[] label = Constants.categoryTitle;
/* 4154 */     String[] values = Constants.categoryLink;
/* 4155 */     if ((Constants.isIt360) && (request != null))
/*      */     {
/* 4157 */       String selectedTab = EnterpriseUtil.getSelectedTab(request);
/* 4158 */       if (selectedTab != null)
/*      */       {
/* 4160 */         if (selectedTab.equals("Server"))
/*      */         {
/* 4162 */           List<String> serLabelList = new ArrayList();
/* 4163 */           List<String> servaluesList = new ArrayList();
/* 4164 */           String serverResGpTypesStr = Constants.serverResGpTypes;
/* 4165 */           serverResGpTypesStr = serverResGpTypesStr.substring(1, serverResGpTypesStr.length() - 1);
/* 4166 */           StringTokenizer stSer = new StringTokenizer(serverResGpTypesStr, ",");
/* 4167 */           while (stSer.hasMoreTokens())
/*      */           {
/* 4169 */             String token = stSer.nextToken();
/* 4170 */             token = token.substring(1, token.length() - 1);
/* 4171 */             for (int i = 0; i < values.length; i++)
/*      */             {
/* 4173 */               if (values[i].equals(token))
/*      */               {
/* 4175 */                 serLabelList.add(label[i]);
/* 4176 */                 servaluesList.add(values[i]);
/*      */               }
/*      */             }
/*      */           }
/* 4180 */           label = new String[serLabelList.size()];
/* 4181 */           label = (String[])serLabelList.toArray(label);
/* 4182 */           values = new String[servaluesList.size()];
/* 4183 */           values = (String[])servaluesList.toArray(values);
/*      */         }
/* 4185 */         else if (selectedTab.equals("Application"))
/*      */         {
/* 4187 */           List<String> appLabelList = new ArrayList();
/* 4188 */           List<String> appvaluesList = new ArrayList();
/* 4189 */           String nonApplicationResGpTypesStr = Constants.nonApplicationResGpTypes;
/* 4190 */           nonApplicationResGpTypesStr = nonApplicationResGpTypesStr.substring(1, nonApplicationResGpTypesStr.length() - 1);
/* 4191 */           StringTokenizer stApp = new StringTokenizer(nonApplicationResGpTypesStr, ",");
/* 4192 */           List<String> tokensList = new ArrayList();
/* 4193 */           while (stApp.hasMoreTokens())
/*      */           {
/* 4195 */             String token = stApp.nextToken();
/* 4196 */             token = token.substring(1, token.length() - 1);
/* 4197 */             tokensList.add(token);
/*      */           }
/* 4199 */           for (int i = 0; i < values.length; i++)
/*      */           {
/* 4201 */             if (!tokensList.contains(values[i]))
/*      */             {
/* 4203 */               appLabelList.add(label[i]);
/* 4204 */               appvaluesList.add(values[i]);
/*      */             }
/*      */           }
/* 4207 */           label = new String[appLabelList.size()];
/* 4208 */           label = (String[])appLabelList.toArray(label);
/* 4209 */           values = new String[appvaluesList.size()];
/* 4210 */           values = (String[])appvaluesList.toArray(values);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4215 */     Hashtable ht = new Hashtable();
/* 4216 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 4218 */       ht.put(values[i], label[i]);
/*      */     }
/* 4220 */     ArrayList list = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4226 */     ArrayList sublist = new ArrayList();
/* 4227 */     sublist.add(FormatUtil.getString("am.monitortab.allmonitors.text"));
/* 4228 */     sublist.add("All");
/* 4229 */     list.add(sublist);
/*      */     
/*      */ 
/* 4232 */     HashMap restypedisplaynameMapping = new HashMap();
/* 4233 */     boolean sanAvailable = false;
/* 4234 */     boolean nwdAvailable = false;
/* 4235 */     boolean emAvailable = false;
/*      */     try
/*      */     {
/* 4238 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4239 */       String resIds = "";
/* 4240 */       String qryAppend = "";
/* 4241 */       Vector resIds_vector = null;
/* 4242 */       boolean isUserResourceEnabled = false;
/* 4243 */       String loginUserid = null;
/* 4244 */       if ((request != null) && (request.isUserInRole("OPERATOR")))
/*      */       {
/* 4246 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 4248 */           resIds_vector = ClientDBUtil.getResourceIdentity(this.username, request, null);
/*      */ 
/*      */ 
/*      */         }
/* 4252 */         else if (Constants.isUserResourceEnabled()) {
/* 4253 */           isUserResourceEnabled = true;
/* 4254 */           loginUserid = Constants.getLoginUserid(request);
/*      */         } else {
/* 4256 */           resIds_vector = ClientDBUtil.getResourceIdentity(this.username, request, null);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4261 */       if ((resIds_vector != null) && (resIds_vector.size() != 0))
/*      */       {
/* 4263 */         for (int i = 0; i < resIds_vector.size(); i++)
/*      */         {
/* 4265 */           resIds = resIds + (String)resIds_vector.get(i) + ",";
/*      */         }
/* 4267 */         resIds = resIds.substring(0, resIds.length() - 1);
/* 4268 */         qryAppend = "and AM_ManagedObject.RESOURCEID in (" + resIds + ")";
/*      */       }
/* 4270 */       String qry1 = null;
/* 4271 */       if (isUserResourceEnabled) {
/* 4272 */         qry1 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('NWD') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4274 */         qry1 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('NWD')" + qryAppend;
/*      */       }
/* 4276 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry1);
/* 4277 */       if (rs1.next()) {
/* 4278 */         nwdAvailable = true;
/*      */       }
/* 4280 */       AMConnectionPool.closeStatement(rs1);
/* 4281 */       if (isUserResourceEnabled) {
/* 4282 */         qry1 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('SAN') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4284 */         qry1 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('SAN')" + qryAppend;
/*      */       }
/* 4286 */       rs1 = AMConnectionPool.executeQueryStmt(qry1);
/* 4287 */       if (rs1.next()) {
/* 4288 */         sanAvailable = true;
/*      */       }
/* 4290 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/* 4292 */       if (isUserResourceEnabled) {
/* 4293 */         qry1 = "select RESOURCEGROUP from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('EMO') where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 4295 */         qry1 = "select RESOURCEGROUP from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCEGROUP IN ('EMO')" + qryAppend;
/*      */       }
/*      */       
/* 4298 */       rs1 = AMConnectionPool.executeQueryStmt(qry1);
/* 4299 */       if (rs1.next()) {
/* 4300 */         emAvailable = true;
/*      */       }
/* 4302 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/*      */ 
/* 4305 */       HashMap<String, ArrayList> CategoryView = new HashMap();
/* 4306 */       if (isUserResourceEnabled) {
/* 4307 */         this.query = ("select DISTINCT(AM_ManagedObject.TYPE),AM_ManagedResourceType.RESOURCEGROUP,SUBGROUP,AM_ManagedResourceType.DISPLAYNAME  from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where TYPE in " + Constants.resourceTypes + " and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " order by AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.DISPLAYNAME");
/*      */       } else {
/* 4309 */         this.query = ("select DISTINCT(AM_ManagedObject.TYPE),AM_ManagedResourceType.RESOURCEGROUP,SUBGROUP,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where TYPE in " + Constants.resourceTypes + " " + qryAppend + " order by AM_ManagedResourceType.RESOURCEGROUP,AM_ManagedResourceType.DISPLAYNAME");
/*      */       }
/* 4311 */       rs1 = AMConnectionPool.executeQueryStmt(this.query);
/* 4312 */       while (rs1.next())
/*      */       {
/* 4314 */         ArrayList alllst = new ArrayList();
/* 4315 */         if (CategoryView.containsKey(rs1.getString(2))) {
/* 4316 */           ArrayList subCategory = (ArrayList)CategoryView.get(rs1.getString(2));
/* 4317 */           ArrayList sub = new ArrayList();
/* 4318 */           sub.add(rs1.getString(1));
/* 4319 */           sub.add(rs1.getString(3));
/* 4320 */           sub.add(rs1.getString(4));
/* 4321 */           subCategory.add(sub);
/* 4322 */           CategoryView.put(rs1.getString(2), subCategory);
/*      */         } else {
/* 4324 */           ArrayList sub = new ArrayList();
/* 4325 */           sub.add(rs1.getString(1));
/* 4326 */           sub.add(rs1.getString(3));
/* 4327 */           sub.add(rs1.getString(4));
/* 4328 */           alllst.add(sub);
/* 4329 */           CategoryView.put(rs1.getString(2), alllst);
/*      */         }
/*      */       }
/* 4332 */       AMConnectionPool.closeStatement(rs1);
/*      */       
/*      */ 
/* 4335 */       String query = null;
/* 4336 */       if (isUserResourceEnabled) {
/* 4337 */         query = "select RESOURCEGROUP  from AM_USERRESOURCESTABLE, AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and RESOURCEGROUP NOT IN ('NET','HAI','NWD','EMO','SAN') group by RESOURCEGROUP";
/*      */       } else {
/* 4339 */         query = "select RESOURCEGROUP  from AM_ManagedObject  inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  where RESOURCEGROUP NOT IN ('NET','HAI','NWD','EMO','SAN') " + qryAppend + " group by RESOURCEGROUP";
/*      */       }
/* 4341 */       ResultSet rst = AMConnectionPool.executeQueryStmt(query);
/* 4342 */       while (rst.next())
/*      */       {
/* 4344 */         ArrayList addedList = new ArrayList();
/* 4345 */         sublist = new ArrayList();
/* 4346 */         if (ht.get(rst.getString("RESOURCEGROUP")) != null)
/*      */         {
/* 4348 */           sublist.add(FormatUtil.getString(String.valueOf(ht.get(rst.getString("RESOURCEGROUP")))));
/* 4349 */           sublist.add(rst.getString("RESOURCEGROUP"));
/* 4350 */           list.add(sublist);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4359 */           if (CategoryView.containsKey(rst.getString("RESOURCEGROUP"))) {
/* 4360 */             ArrayList resgroupList = (ArrayList)CategoryView.get(rst.getString("RESOURCEGROUP"));
/* 4361 */             String subGroup = null;
/* 4362 */             String disName = null;
/* 4363 */             for (int i = 0; i < resgroupList.size(); i++)
/*      */             {
/* 4365 */               subGroup = (String)((ArrayList)resgroupList.get(i)).get(1);
/* 4366 */               disName = (String)((ArrayList)resgroupList.get(i)).get(2);
/* 4367 */               if (subGroup.equals("File System Monitor")) {
/* 4368 */                 if (!addedList.contains("File System Monitor")) {
/* 4369 */                   addedList.add(subGroup);
/* 4370 */                   restypedisplaynameMapping.put(subGroup, disName);
/*      */                 }
/*      */                 
/*      */               }
/* 4374 */               else if (subGroup.equals("Windows")) {
/* 4375 */                 if (!addedList.contains("Windows")) {
/* 4376 */                   addedList.add(subGroup);
/* 4377 */                   restypedisplaynameMapping.put(subGroup, "Windows");
/*      */                 }
/*      */                 
/*      */               }
/* 4381 */               else if (subGroup.equals("VirtualMachine")) {
/* 4382 */                 if (!addedList.contains("VirtualMachine")) {
/* 4383 */                   addedList.add(subGroup);
/* 4384 */                   restypedisplaynameMapping.put(subGroup, "VirtualMachine");
/*      */                 }
/*      */                 
/*      */               }
/* 4388 */               else if (subGroup.equals("Container")) {
/* 4389 */                 if (!addedList.contains("Container")) {
/* 4390 */                   addedList.add(subGroup);
/* 4391 */                   restypedisplaynameMapping.put(subGroup, "Container");
/*      */                 }
/*      */               }
/*      */               else {
/* 4395 */                 addedList.add(subGroup);
/* 4396 */                 restypedisplaynameMapping.put(subGroup, disName);
/*      */               }
/*      */             }
/*      */           }
/* 4400 */           for (int i = 0; i < addedList.size(); i++)
/*      */           {
/* 4402 */             if ((!OEMUtil.isOEM()) || (!((String)addedList.get(i)).equals("RMI")))
/*      */             {
/*      */ 
/*      */ 
/* 4406 */               if (Constants.resourceTypes.indexOf((String)addedList.get(i)) != -1)
/*      */               {
/* 4408 */                 sublist = new ArrayList();
/* 4409 */                 sublist.add(FormatUtil.getString((String)restypedisplaynameMapping.get((String)addedList.get(i))));
/* 4410 */                 sublist.add((String)addedList.get(i));
/* 4411 */                 list.add(sublist);
/*      */ 
/*      */               }
/* 4414 */               else if (((String)addedList.get(i)).equals("Unknown"))
/*      */               {
/* 4416 */                 sublist = new ArrayList();
/* 4417 */                 sublist.add(FormatUtil.getString((String)restypedisplaynameMapping.get((String)addedList.get(i))));
/* 4418 */                 sublist.add((String)addedList.get(i));
/* 4419 */                 list.add(sublist);
/*      */               } }
/*      */           }
/*      */         }
/*      */       }
/* 4424 */       rst.close();
/* 4425 */       rs1.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4429 */       exc.printStackTrace();
/*      */     }
/* 4431 */     if ((nwdAvailable) && (!Constants.isIt360))
/*      */     {
/* 4433 */       sublist = new ArrayList();
/* 4434 */       sublist.add("Network Devices");
/* 4435 */       sublist.add("NWD");
/* 4436 */       list.add(sublist);
/*      */     }
/*      */     
/* 4439 */     if ((sanAvailable) && (!Constants.isIt360))
/*      */     {
/* 4441 */       sublist = new ArrayList();
/* 4442 */       sublist.add("Storage Devices");
/* 4443 */       sublist.add("SAN");
/* 4444 */       list.add(sublist);
/*      */     }
/*      */     
/*      */ 
/* 4448 */     sublist = new ArrayList();
/* 4449 */     sublist.add(FormatUtil.getString("am.webclient.site24x7.heading"));
/* 4450 */     sublist.add("EMO");
/* 4451 */     list.add(sublist);
/* 4452 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public List getResourceTypeNames()
/*      */   {
/* 4459 */     String[] resourceValuesNew = { "All Services", "AIX", "AS400/iSeries", "RMI", "Apache-server", "DB2-server", "Exchange-server", "File System Monitor", "FreeBSD / OpenBSD", "HP-UX / Tru64", "IIS-server", "JBOSS-server", "Custom-Application", "JMX1.2-MX4J-RMI", "JDK1.5", "Linux", "Mac OS", "MAIL-server", ".Net", "MSSQL-DB-server", "SYBASE-DB-server", "MYSQL-DB-server", "ORACLE-DB-server", "ORACLE-APP-server", "PHP", "Ping Monitor", "Port-Test", "Script Monitor", "SNMP", "Sun Solaris", "TELNET", "Tomcat-server", "Tru64 UNIX", "WTA", "Unknown", "UrlMonitor", "UrlSeq", "WEB-server", "WEBLOGIC-server", "WEBLOGIC-Integration", "WebSphere-server", "Windows", "Web Service", "Generic WMI", "Novell", "RBM" };
/* 4460 */     String[] resourceTextNew = { FormatUtil.getString("All Services"), FormatUtil.getString("AIX"), FormatUtil.getString("AS400/iSeries"), FormatUtil.getString("am.webclient.monitortype.adventnetjmxagent.text"), FormatUtil.getString("am.webclient.monitortype.apache.text"), FormatUtil.getString("am.monitortab.tableview.DB2.text"), FormatUtil.getString("Exchange Server"), FormatUtil.getString("File / Directory Monitor"), FormatUtil.getString("am.webclient.hostdiscovery.bsd"), FormatUtil.getString("am.webclient.hostdiscovery.hp"), FormatUtil.getString("IIS Server"), FormatUtil.getString("am.webclient.monitortype.jbossservers.text"), FormatUtil.getString("JMX/SNMP Dashboard"), FormatUtil.getString("JMX Applications"), FormatUtil.getString("am.monitortab.tableview.jdk15.text"), FormatUtil.getString("Linux"), FormatUtil.getString("Mac OS"), FormatUtil.getString("Mail Server"), FormatUtil.getString("Microsoft .NET"), FormatUtil.getString("MS SQL"), FormatUtil.getString("Sybase"), FormatUtil.getString("MySQL"), FormatUtil.getString("Oracle"), FormatUtil.getString("am.webclient.monitortype.oracleappservers.text"), FormatUtil.getString("am.webclient.monitortype.phpmonitoring.text"), FormatUtil.getString("Ping Monitor"), FormatUtil.getString("Service Monitoring"), FormatUtil.getString("Script Monitor"), FormatUtil.getString("am.reporttab.heading.snmp.text"), FormatUtil.getString("am.webclient.hostdiscovery.sunsolaris"), FormatUtil.getString("am.webclient.hostdiscovery.telnet"), FormatUtil.getString("am.webclient.monitortype.tomcatservers.text"), FormatUtil.getString("am.webclient.monitortype.hptru64servers.text"), FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction"), FormatUtil.getString("Unknown"), FormatUtil.getString("am.reporttab.shortname.urlmonitoring.text"), FormatUtil.getString("am.webclient.urlseq.type.text"), FormatUtil.getString("Web Server"), FormatUtil.getString("am.webclient.monitortype.weblogicservers.text"), FormatUtil.getString("am.webclient.wli.name.text"), FormatUtil.getString("am.webclient.monitortype.websphereservers.text"), FormatUtil.getString("Windows"), FormatUtil.getString("Web Service"), FormatUtil.getString("am.webclient.wmi.wmimonitorname.text"), FormatUtil.getString("Novell"), FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text") };
/* 4461 */     String[] resourceImagePath = { "icon_monitors_all.gif", "icon_monitors_aix.gif", "icon_monitors_as400.gif", "icon_monitors_adventnet.gif", "icon_monitors_apache.gif", "icon_monitors_db2.gif", "icon_monitors_exchange.gif", "icon_monitors_filesystem.gif", "icon_monitors_freebsd.gif", "icon_monitors_hpunix.gif", "icon_monitors_iis.gif", "icon_monitors_jboss.gif", "icon_monitors_cam.gif", "icon_monitors_mx4jjmx1_2.gif", "icon_monitors_jdk.gif", "icon_monitors_linux.gif", "icon_monitors_macos.gif", "icon_monitors_mailserver.gif", "icon_monitors_dotnet.gif", "icon_monitors_mssql.gif", "icon_monitors_sybase.gif", "icon_monitors_mysql.gif", "icon_monitors_oracle.gif", "/icon_monitor_oracle.gif", "icon_php.gif", "icon_monitors_ping.gif", "icon_monitors_servicemonitoring.gif", "ScriptMonitoring.gif", "icon_monitors_snmp.gif", "icon_monitors_solaris.gif", "icon_monitors_telnet.gif", "icon_monitors_tomcat.gif", "icon_monitors_hpunix.gif", "icon_monitors_wta.gif", "icon_monitors_unknown.gif", "icon_monitors_urlmonitor.gif", "icon_monitors_urlmonitor.gif", "icon_monitors_webserver.gif", "icon_monitors_weblogic.gif", "icon_monitors_weblogic.gif", "icon_monitors_websphere.gif", "icon_monitors_windows.gif", "icon_monitors_webservices.gif", "icon_monitor_WMI.gif", "icon_monitor_novell.gif", "icon_monitor_rbm.gif" };
/*      */     
/* 4463 */     ArrayList list = new ArrayList();
/* 4464 */     ArrayList sublist = new ArrayList();
/* 4465 */     sublist.add("All Services");
/* 4466 */     sublist.add("All");
/* 4467 */     sublist.add("icon_monitors_all.gif");
/* 4468 */     list.add(sublist);
/* 4469 */     int i = 1;
/* 4470 */     while (i < resourceValuesNew.length)
/*      */     {
/* 4472 */       if (Constants.resourceTypes.indexOf(resourceValuesNew[i]) != -1)
/*      */       {
/* 4474 */         sublist = new ArrayList();
/*      */         
/* 4476 */         sublist.add(resourceTextNew[i]);
/* 4477 */         sublist.add(resourceValuesNew[i]);
/* 4478 */         sublist.add(resourceImagePath[i]);
/* 4479 */         list.add(sublist);
/*      */       }
/* 4481 */       i++;
/*      */     }
/* 4483 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public List getResourceTypes_alarmOnly()
/*      */   {
/* 4489 */     String[] resourceValues = { "-", "All", "AIX", "AS400/iSeries", "Exchange-server", "File System Monitor", "FreeBSD / OpenBSD", "RMI", "Apache-server", "DB2-server", "HP-UX / Tru64", "IIS-server", "JBOSS-server", "Custom-Application", "Linux", "Mac OS", "MAIL-server", ".Net", "MSSQL-DB-server", "SYBASE-DB-server", "JMX1.2-MX4J-RMI", "JDK1.5", "MYSQL-DB-server", "ORACLE-APP-server", "ORACLE-DB-server", "PHP", "Ping Monitor", "Port-Test", "Script Monitor", "SNMP", "Sun Solaris", "TELNET", "Tomcat-server", "WTA", "Unknown", "UrlMonitor", "UrlSeq", "WEB-server", "WEBLOGIC-server", "WebSphere-server", "Windows", "Web Service", "Generic WMI", "WEBLOGIC-Integration", "files", "directories", "Novell", "RBM" };
/* 4490 */     String[] resourceText = { FormatUtil.getString("am.webclient.selectmonitor.alert.text"), FormatUtil.getString("am.monitortab.allmonitors.text"), FormatUtil.getString("AIX"), FormatUtil.getString("AS400/iSeries"), FormatUtil.getString("Exchange Server"), FormatUtil.getString("File / Directory Monitor"), FormatUtil.getString("FreeBSD / OpenBSD"), FormatUtil.getString("am.webclient.monitortype.adventnetjmxagent.text"), FormatUtil.getString("am.webclient.monitortype.apache.text"), FormatUtil.getString("am.monitortab.tableview.DB2.text"), FormatUtil.getString("am.webclient.hostdiscovery.hp"), FormatUtil.getString("IIS Server"), FormatUtil.getString("am.webclient.monitortype.jbossservers.text"), FormatUtil.getString("JMX/SNMP Dashboard"), FormatUtil.getString("Linux"), FormatUtil.getString("Mac OS"), FormatUtil.getString("Mail Server"), FormatUtil.getString("Microsoft .NET"), FormatUtil.getString("MS SQL"), FormatUtil.getString("Sybase"), FormatUtil.getString("JMX Applications"), FormatUtil.getString("am.webclient.jdk15.servertype"), FormatUtil.getString("MySQL"), FormatUtil.getString("am.webclient.monitortype.oracleappservers.text"), FormatUtil.getString("Oracle"), FormatUtil.getString("am.webclient.monitortype.phpmonitoring.text"), FormatUtil.getString("Ping Monitor"), FormatUtil.getString("Service Monitoring"), FormatUtil.getString("Script Monitor"), FormatUtil.getString("am.reporttab.heading.snmp.text"), FormatUtil.getString("am.webclient.hostdiscovery.sunsolaris"), FormatUtil.getString("am.webclient.hostdiscovery.telnet"), FormatUtil.getString("am.webclient.monitortype.tomcatservers.text"), FormatUtil.getString("J2EE Web Transactions"), FormatUtil.getString("Unknown"), FormatUtil.getString("am.reporttab.shortname.urlmonitoring.text"), FormatUtil.getString("am.webclient.urlseq.type.text"), FormatUtil.getString("Web Server"), FormatUtil.getString("am.webclient.monitortype.weblogicservers.text"), FormatUtil.getString("am.webclient.monitortype.websphereservers.text"), FormatUtil.getString("Windows"), FormatUtil.getString("Web Service"), FormatUtil.getString("am.webclient.wmi.wmimonitorname.text"), FormatUtil.getString("am.webclient.wli.text"), FormatUtil.getString("files"), FormatUtil.getString("directories"), FormatUtil.getString("Novell"), FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text") };
/* 4491 */     ArrayList list = new ArrayList();
/* 4492 */     list.add(new Option(resourceText[0], resourceValues[0]));
/* 4493 */     list.add(new Option(resourceText[1], resourceValues[1]));
/*      */     
/* 4495 */     for (int i = 2; i < resourceValues.length; i++)
/*      */     {
/* 4497 */       if ((!OEMUtil.isOEM()) || (!resourceValues[i].equals("RMI")))
/*      */       {
/*      */ 
/*      */ 
/* 4501 */         if (Constants.resourceTypes.indexOf(resourceValues[i]) != -1)
/*      */         {
/* 4503 */           list.add(new Option(resourceText[i], resourceValues[i]));
/*      */         }
/*      */       }
/*      */     }
/*      */     try {
/* 4508 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4509 */       String qry = "select SUBGROUP ,DISPLAYNAME from AM_MONITOR_TYPES,AM_ManagedResourceType where typename=resourcetype group by SUBGROUP";
/* 4510 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 4511 */       while (rs.next())
/*      */       {
/* 4513 */         list.add(new Option(rs.getString(2), rs.getString(1)));
/*      */       }
/* 4515 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4519 */       exc.printStackTrace();
/*      */     }
/* 4521 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public List getResourceTypes_alarm()
/*      */   {
/* 4527 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4528 */     ArrayList list = new ArrayList();
/* 4529 */     list.add(new Option(FormatUtil.getString("am.webclient.selectmonitor.alert.text"), "-"));
/* 4530 */     list.add(new Option(FormatUtil.getString("am.monitortab.allmonitors.text"), "All"));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 4565 */       String query = "(select distinct(SUBGROUP),AM_ManagedResourceType.DISPLAYNAME from AM_ManagedResourceType left outer join AM_ManagedObject on resourcetype=type where SUBGROUP <> 'HAI' AND SUBGROUP <> 'Network' AND SUBGROUP NOT LIKE 'OpManager-%' AND  type is not null) UNION (select distinct(CATEGORY) as SUBGROUP,SUBSTRING(CATEGORY,11,200) as DISPLAYNAME from ExternalDeviceDetails) ORDER BY SUBGROUP";
/* 4566 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 4567 */       boolean alreadyAdded = false;
/* 4568 */       while (rs.next()) {
/* 4569 */         String displayname = rs.getString(2);
/* 4570 */         if (rs.getString(1).startsWith("Windows")) {
/* 4571 */           displayname = rs.getString(1);
/* 4572 */           if (!alreadyAdded)
/*      */           {
/*      */ 
/*      */ 
/* 4576 */             alreadyAdded = true;
/*      */           }
/*      */         } else {
/* 4579 */           list.add(new Option(FormatUtil.getString(displayname), rs.getString(1)));
/*      */         } }
/* 4581 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception exc) {
/* 4584 */       exc.getMessage();
/*      */     }
/* 4586 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEnableCompleteInfoForSMS()
/*      */   {
/* 4599 */     return this.enableCompleteInfoForSMS;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEnableCompleteInfoForSMS(String v)
/*      */   {
/* 4608 */     this.enableCompleteInfoForSMS = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getGatewayName()
/*      */   {
/* 4618 */     return this.gatewayName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGatewayName(String v)
/*      */   {
/* 4627 */     this.gatewayName = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setServerside(String v)
/*      */   {
/* 4633 */     this.serverside = v;
/*      */   }
/*      */   
/*      */   public String getServerside() {
/* 4637 */     return this.serverside;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getGatewayCheckStatus()
/*      */   {
/* 4646 */     return this.gatewayCheckStatus;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGatewayCheckStatus(String v)
/*      */   {
/* 4655 */     this.gatewayCheckStatus = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAvailabilityUpUnderMaintenance()
/*      */   {
/* 4664 */     return this.availabilityUpUnderMaintenance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAvailabilityUpUnderMaintenance(String v)
/*      */   {
/* 4673 */     this.availabilityUpUnderMaintenance = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getHealthClearUnderMaintenance()
/*      */   {
/* 4680 */     return this.healthClearUnderMaintenance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setHealthClearUnderMaintenance(String v)
/*      */   {
/* 4687 */     this.healthClearUnderMaintenance = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getUnmanageMonitorAfterMaintenance()
/*      */   {
/* 4693 */     return this.unmanageMonitorAfterMaintenance;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setUnmanageMonitorAfterMaintenance(String v)
/*      */   {
/* 4700 */     this.unmanageMonitorAfterMaintenance = v;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getTransferEncoding()
/*      */   {
/* 4706 */     return this.transferEncoding;
/*      */   }
/*      */   
/*      */   public void setTransferEncoding(String t)
/*      */   {
/* 4711 */     this.transferEncoding = t;
/*      */   }
/*      */   
/* 4714 */   private ArrayList availableResources = null;
/*      */   
/*      */ 
/*      */   public void setAvailableResources(ArrayList availableResources)
/*      */   {
/* 4719 */     this.availableResources = availableResources;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailableResources()
/*      */   {
/* 4724 */     return this.availableResources;
/*      */   }
/*      */   
/* 4727 */   private ArrayList availableResourceTypes = null;
/*      */   
/*      */ 
/*      */   public void setAvailableResourceTypes(ArrayList availableResourceTypes)
/*      */   {
/* 4732 */     this.availableResourceTypes = availableResourceTypes;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailableResourceTypes()
/*      */   {
/* 4737 */     return this.availableResourceTypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4742 */   private String resourceid = null;
/*      */   
/*      */ 
/*      */   public String getResourceid()
/*      */   {
/* 4747 */     return this.resourceid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setResourceid(String resourceid)
/*      */   {
/* 4754 */     this.resourceid = resourceid;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 4759 */   private boolean sslenabled = false;
/*      */   
/*      */ 
/*      */   public boolean isSslenabled()
/*      */   {
/* 4764 */     return this.sslenabled;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSslenabled(boolean sslenabled)
/*      */   {
/* 4770 */     this.sslenabled = sslenabled;
/*      */   }
/*      */   
/* 4773 */   private ArrayList Databases = null;
/*      */   
/*      */ 
/*      */ 
/*      */   public void setDatabases(ArrayList Databases)
/*      */   {
/* 4779 */     this.Databases = Databases;
/*      */   }
/*      */   
/*      */   public ArrayList getDatabases()
/*      */   {
/* 4784 */     return this.Databases;
/*      */   }
/*      */   
/* 4787 */   private String databasesId = null;
/*      */   
/*      */   public void setDatabasesId(String databasesId) {
/* 4790 */     this.databasesId = databasesId;
/*      */   }
/*      */   
/*      */   public String getDatabasesId()
/*      */   {
/* 4795 */     return this.databasesId;
/*      */   }
/*      */   
/* 4798 */   private String rulename = "";
/* 4799 */   private String[] mgroups = new String[0];
/* 4800 */   private String[] select = new String[0];
/* 4801 */   private int rulestatus = 1;
/* 4802 */   private String monitorseverity = "1";
/* 4803 */   private int monitortype = 1;
/* 4804 */   private int timeperiod = 6;
/* 4805 */   private String timeunit = "Hours";
/* 4806 */   private String rulefrom = "false";
/* 4807 */   private String graphType = "true";
/*      */   private String ruleto;
/*      */   private String rulesub;
/*      */   private String rulemessage;
/* 4811 */   private int timeinterval = 10;
/* 4812 */   private String taskName = "";
/* 4813 */   private String taskDescription = "";
/* 4814 */   private String taskStatus = "enable";
/* 4815 */   private String statusRule = "enable";
/* 4816 */   private String taskMethod = "daily";
/* 4817 */   private String viewBy = "monitors";
/* 4818 */   private String taskGroup = "allmonitors";
/* 4819 */   private String taskStartTime = "";
/* 4820 */   private String taskEndTime = "";
/* 4821 */   private String taskEffectFrom = "";
/* 4822 */   private String taskCount = "";
/* 4823 */   private String customTaskStartTime = "";
/* 4824 */   private String customTaskEndTime = "";
/* 4825 */   private String timezone = "";
/* 4826 */   private Properties startDay = new Properties();
/* 4827 */   private Properties endDay = new Properties();
/* 4828 */   private Properties startTime = new Properties();
/* 4829 */   private Properties endTime = new Properties();
/* 4830 */   private ArrayList toAdd = new ArrayList();
/* 4831 */   private ArrayList toAddg = new ArrayList();
/* 4832 */   private ArrayList toAddSG = new ArrayList();
/* 4833 */   private ArrayList toAddSC = new ArrayList();
/* 4834 */   private ArrayList toAddItem = new ArrayList();
/* 4835 */   private ArrayList present = new ArrayList();
/* 4836 */   private ArrayList presentg = new ArrayList();
/* 4837 */   private ArrayList ticketTitle = new ArrayList();
/* 4838 */   private ArrayList ticketDescription = new ArrayList();
/* 4839 */   private String[] maintenanceCombo1 = new String[0];
/* 4840 */   private String[] maintenanceCombo2 = new String[0];
/*      */   
/*      */ 
/* 4843 */   private boolean enforcePasswordPolicy = false;
/* 4844 */   private boolean enforcePasswordExpiry = false;
/* 4845 */   private boolean enforcePasswordChange = false;
/* 4846 */   private boolean loginDisabled = false;
/* 4847 */   private boolean sysadmin = false;
/* 4848 */   private boolean securityadmin = false;
/* 4849 */   private boolean serveradmin = false;
/* 4850 */   private boolean setupadmin = false;
/* 4851 */   private boolean processadmin = false;
/* 4852 */   private boolean diskadmin = false;
/* 4853 */   private boolean dbcreator = false;
/* 4854 */   private boolean bulkadmin = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setRulename(String rulename)
/*      */   {
/* 4862 */     this.rulename = rulename;
/*      */   }
/*      */   
/*      */   public String getRulename() {
/* 4866 */     return this.rulename;
/*      */   }
/*      */   
/*      */   public String[] getMgroups() {
/* 4870 */     return this.mgroups;
/*      */   }
/*      */   
/*      */   public void setMgroups(String[] mgroups) {
/* 4874 */     this.mgroups = mgroups;
/*      */   }
/*      */   
/*      */   public String[] getSelect() {
/* 4878 */     return this.select;
/*      */   }
/*      */   
/*      */   public void setSelect(String[] select) {
/* 4882 */     this.select = select;
/*      */   }
/*      */   
/*      */   public void setRulestatus(int rulestatus)
/*      */   {
/* 4887 */     this.rulestatus = rulestatus;
/*      */   }
/*      */   
/*      */   public int getRulestatus() {
/* 4891 */     return this.rulestatus;
/*      */   }
/*      */   
/*      */   public void setMonitorseverity(String monitorseverity)
/*      */   {
/* 4896 */     this.monitorseverity = monitorseverity;
/*      */   }
/*      */   
/*      */   public String getMonitorseverity() {
/* 4900 */     return this.monitorseverity;
/*      */   }
/*      */   
/*      */   public void setMonitortype(int monitortype)
/*      */   {
/* 4905 */     this.monitortype = monitortype;
/*      */   }
/*      */   
/*      */   public int getMonitortype() {
/* 4909 */     return this.monitortype;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setTimeperiod(int timeperiod)
/*      */   {
/* 4915 */     this.timeperiod = timeperiod;
/*      */   }
/*      */   
/*      */   public int getTimeperiod() {
/* 4919 */     return this.timeperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setTimeinterval(int timeinterval)
/*      */   {
/* 4926 */     this.timeinterval = timeinterval;
/*      */   }
/*      */   
/*      */   public int getTimeinterval() {
/* 4930 */     return this.timeinterval;
/*      */   }
/*      */   
/*      */   public void setTimeunit(String timeunit)
/*      */   {
/* 4935 */     this.timeunit = timeunit;
/*      */   }
/*      */   
/*      */   public String getTimeunit() {
/* 4939 */     return this.timeunit;
/*      */   }
/*      */   
/*      */   public void setRulefrom(String rulefrom) {
/* 4943 */     this.rulefrom = rulefrom;
/*      */   }
/*      */   
/*      */   public String getRulefrom() {
/* 4947 */     return this.rulefrom;
/*      */   }
/*      */   
/*      */   public void setGraphType(String graphType)
/*      */   {
/* 4952 */     this.graphType = graphType;
/*      */   }
/*      */   
/*      */   public String getGraphType() {
/* 4956 */     return this.graphType;
/*      */   }
/*      */   
/*      */   public void setRuleto(String ruleto) {
/* 4960 */     this.ruleto = ruleto;
/*      */   }
/*      */   
/*      */   public String getRuleto() {
/* 4964 */     return this.ruleto;
/*      */   }
/*      */   
/*      */   public void setRulesub(String rulesub)
/*      */   {
/* 4969 */     this.rulesub = rulesub;
/*      */   }
/*      */   
/*      */   public String getRulesub() {
/* 4973 */     return this.rulesub;
/*      */   }
/*      */   
/*      */   public void setRulemessage(String rulemessage)
/*      */   {
/* 4978 */     this.rulemessage = rulemessage;
/*      */   }
/*      */   
/*      */   public String getRulemessage() {
/* 4982 */     return this.rulemessage;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTaskName(String taskName)
/*      */   {
/* 4991 */     this.taskName = taskName;
/*      */   }
/*      */   
/*      */   public String getTaskName() {
/* 4995 */     return this.taskName;
/*      */   }
/*      */   
/*      */   public void setTaskDescription(String taskDescription)
/*      */   {
/* 5000 */     this.taskDescription = taskDescription;
/*      */   }
/*      */   
/*      */   public String getTaskDescription() {
/* 5004 */     return this.taskDescription;
/*      */   }
/*      */   
/*      */   public void setTaskStatus(String taskStatus)
/*      */   {
/* 5009 */     this.taskStatus = taskStatus;
/*      */   }
/*      */   
/*      */   public String getTaskStatus() {
/* 5013 */     return this.taskStatus;
/*      */   }
/*      */   
/*      */   public void setTaskMethod(String taskMethod)
/*      */   {
/* 5018 */     this.taskMethod = taskMethod;
/*      */   }
/*      */   
/*      */   public String getTaskMethod() {
/* 5022 */     return this.taskMethod;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setViewBy(String viewBy)
/*      */   {
/* 5028 */     this.viewBy = viewBy;
/*      */   }
/*      */   
/*      */   public String getViewBy() {
/* 5032 */     return this.viewBy;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setTaskGroup(String taskGroup)
/*      */   {
/* 5038 */     this.taskGroup = taskGroup;
/*      */   }
/*      */   
/*      */   public String getTaskGroup() {
/* 5042 */     return this.taskGroup;
/*      */   }
/*      */   
/*      */   public void setTaskStartTime(String taskStartTime)
/*      */   {
/* 5047 */     this.taskStartTime = taskStartTime;
/*      */   }
/*      */   
/*      */   public String getTaskStartTime() {
/* 5051 */     return this.taskStartTime;
/*      */   }
/*      */   
/*      */   public void setTaskEffectFrom(String taskEffectFrom)
/*      */   {
/* 5056 */     this.taskEffectFrom = taskEffectFrom;
/*      */   }
/*      */   
/*      */   public String getTaskEffectFrom() {
/* 5060 */     return this.taskEffectFrom;
/*      */   }
/*      */   
/*      */   public void setTaskEndTime(String taskEndTime)
/*      */   {
/* 5065 */     this.taskEndTime = taskEndTime;
/*      */   }
/*      */   
/*      */   public String getTaskEndTime() {
/* 5069 */     return this.taskEndTime;
/*      */   }
/*      */   
/*      */   public void setTimezone(String timezone) {
/* 5073 */     this.timezone = timezone;
/*      */   }
/*      */   
/*      */   public String getTimezone() {
/* 5077 */     return this.timezone;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setStartDay(String key, String value)
/*      */   {
/* 5083 */     this.startDay.put(key, value);
/*      */   }
/*      */   
/*      */   public String getStartDay(String key) {
/* 5087 */     return this.startDay.getProperty(key);
/*      */   }
/*      */   
/*      */   public void setEndDay(String key, String value)
/*      */   {
/* 5092 */     this.endDay.put(key, value);
/*      */   }
/*      */   
/*      */   public String getEndDay(String key) {
/* 5096 */     return this.endDay.getProperty(key);
/*      */   }
/*      */   
/*      */   public void setStartTime(String key, String value)
/*      */   {
/* 5101 */     this.startTime.put(key, value);
/*      */   }
/*      */   
/*      */   public String getStartTime(String key) {
/* 5105 */     return this.startTime.getProperty(key);
/*      */   }
/*      */   
/*      */   public void setEndTime(String key, String value)
/*      */   {
/* 5110 */     this.endTime.put(key, value);
/*      */   }
/*      */   
/*      */   public String getEndTime(String key) {
/* 5114 */     return this.endTime.getProperty(key);
/*      */   }
/*      */   
/*      */   public void setTaskCount(String taskCount)
/*      */   {
/* 5119 */     this.taskCount = taskCount;
/*      */   }
/*      */   
/*      */   public String getTaskCount() {
/* 5123 */     return this.taskCount;
/*      */   }
/*      */   
/*      */   public void setCustomTaskStartTime(String customTaskStartTime)
/*      */   {
/* 5128 */     this.customTaskStartTime = customTaskStartTime;
/*      */   }
/*      */   
/*      */   public String getCustomTaskStartTime() {
/* 5132 */     return this.customTaskStartTime;
/*      */   }
/*      */   
/*      */   public void setCustomTaskEndTime(String customTaskEndTime)
/*      */   {
/* 5137 */     this.customTaskEndTime = customTaskEndTime;
/*      */   }
/*      */   
/*      */   public String getCustomTaskEndTime() {
/* 5141 */     return this.customTaskEndTime;
/*      */   }
/*      */   
/*      */   public void setToAdd(ArrayList toAdd)
/*      */   {
/* 5146 */     this.toAdd = toAdd;
/*      */   }
/*      */   
/*      */   public ArrayList getToAdd() {
/* 5150 */     return this.toAdd;
/*      */   }
/*      */   
/* 5153 */   public ArrayList getToAddItem() { return this.toAddItem; }
/*      */   
/*      */   public void setToAddItem(ArrayList toAddItem)
/*      */   {
/* 5157 */     this.toAddItem = toAddItem;
/*      */   }
/*      */   
/* 5160 */   public ArrayList getToAddSC() { return this.toAddSC; }
/*      */   
/*      */   public void setToAddSC(ArrayList toAddSC)
/*      */   {
/* 5164 */     this.toAddSC = toAddSC;
/*      */   }
/*      */   
/*      */   public void setToAddSG(ArrayList toAddSG)
/*      */   {
/* 5169 */     this.toAddSG = toAddSG;
/*      */   }
/*      */   
/*      */   public ArrayList getToAddSG() {
/* 5173 */     return this.toAddSG;
/*      */   }
/*      */   
/*      */   public void setToAddg(ArrayList toAddg)
/*      */   {
/* 5178 */     this.toAddg = toAddg;
/*      */   }
/*      */   
/*      */   public ArrayList getToAddg() {
/* 5182 */     return this.toAddg;
/*      */   }
/*      */   
/*      */   public void setPresent(ArrayList present)
/*      */   {
/* 5187 */     this.present = present;
/*      */   }
/*      */   
/*      */   public ArrayList getPresent() {
/* 5191 */     return this.present;
/*      */   }
/*      */   
/*      */   public void setPresentg(ArrayList presentg) {
/* 5195 */     this.presentg = presentg;
/*      */   }
/*      */   
/*      */   public ArrayList getPresentg() {
/* 5199 */     return this.presentg;
/*      */   }
/*      */   
/*      */   public String[] getMaintenanceCombo1()
/*      */   {
/* 5204 */     return this.maintenanceCombo1;
/*      */   }
/*      */   
/*      */   public void setMaintenanceCombo1(String[] maintenanceCombo1) {
/* 5208 */     this.maintenanceCombo1 = maintenanceCombo1;
/*      */   }
/*      */   
/*      */   public String[] getMaintenanceCombo2()
/*      */   {
/* 5213 */     return this.maintenanceCombo2;
/*      */   }
/*      */   
/*      */   public void setMaintenanceCombo2(String[] maintenanceCombo2) {
/* 5217 */     this.maintenanceCombo2 = maintenanceCombo2;
/*      */   }
/*      */   
/*      */   public ArrayList getDaysOfWeek()
/*      */   {
/* 5222 */     String[] values = { "1", "2", "3", "4", "5", "6", "7" };
/* 5223 */     String[] label = { FormatUtil.getString("am.webclient.maintenance.sunday"), FormatUtil.getString("am.webclient.maintenance.monday"), FormatUtil.getString("am.webclient.maintenance.tuesday"), FormatUtil.getString("am.webclient.maintenance.wednesday"), FormatUtil.getString("am.webclient.maintenance.thursday"), FormatUtil.getString("am.webclient.maintenance.friday"), FormatUtil.getString("am.webclient.maintenance.saturday") };
/* 5224 */     ArrayList list = new ArrayList();
/* 5225 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 5227 */       Properties p = new Properties();
/* 5228 */       p.setProperty("label", label[i]);
/* 5229 */       p.setProperty("value", values[i]);
/* 5230 */       list.add(p);
/*      */     }
/* 5232 */     return list;
/*      */   }
/*      */   
/* 5235 */   private String trapName = "";
/* 5236 */   private String trapStatus = "enable";
/* 5237 */   private String trapVersion = "v1";
/* 5238 */   private String trapType = "coldStart";
/* 5239 */   private String specificType = "0";
/* 5240 */   private String enterpriseOID = "";
/* 5241 */   private String trapOID = "";
/* 5242 */   private String severity = "Critical";
/* 5243 */   private String threshold = "1";
/* 5244 */   private String chkTrapHost = "true";
/* 5245 */   private String trapHost = "";
/* 5246 */   private String[] trapActionsCombo1 = new String[0];
/* 5247 */   private String[] trapActionsCombo2 = new String[0];
/* 5248 */   private String chkCustomizeVarbinds = "false";
/* 5249 */   private String trapCustomVarbinds = "";
/* 5250 */   private String chkAssociateTrapSeverity = "true";
/* 5251 */   private String v3UserName = "";
/*      */   
/*      */   public void setV3UserName(String v3UserName)
/*      */   {
/* 5255 */     this.v3UserName = v3UserName;
/*      */   }
/*      */   
/*      */   public String getV3UserName()
/*      */   {
/* 5260 */     return this.v3UserName;
/*      */   }
/*      */   
/*      */   public void setTrapName(String trapName)
/*      */   {
/* 5265 */     this.trapName = trapName;
/*      */   }
/*      */   
/*      */   public String getTrapName() {
/* 5269 */     return this.trapName;
/*      */   }
/*      */   
/*      */   public void setTrapStatus(String trapStatus)
/*      */   {
/* 5274 */     this.trapStatus = trapStatus;
/*      */   }
/*      */   
/*      */   public String getTrapStatus() {
/* 5278 */     return this.trapStatus;
/*      */   }
/*      */   
/*      */   public void setTrapVersion(String trapVersion)
/*      */   {
/* 5283 */     this.trapVersion = trapVersion;
/*      */   }
/*      */   
/*      */   public String getTrapVersion() {
/* 5287 */     return this.trapVersion;
/*      */   }
/*      */   
/*      */   public void setTrapType(String trapType)
/*      */   {
/* 5292 */     this.trapType = trapType;
/*      */   }
/*      */   
/*      */   public String getTrapType() {
/* 5296 */     return this.trapType;
/*      */   }
/*      */   
/*      */   public ArrayList getGenericType()
/*      */   {
/* 5301 */     String[] values = { "0", "1", "2", "3", "4", "5", "6" };
/* 5302 */     String[] label = { FormatUtil.getString("am.webclient.alertmessage.coldstart.text"), FormatUtil.getString("am.webclient.alertmessage.warmstart.text"), FormatUtil.getString("am.webclient.alertmessage.linkdown.text"), FormatUtil.getString("am.webclient.alertmessage.linkup.text"), FormatUtil.getString("am.webclient.alertmessage.authfailure.text"), FormatUtil.getString("am.webclient.alertmessage.egpNeighborLoss.text"), FormatUtil.getString("am.webclient.alertmessage.enterpriseSpecific.text") };
/* 5303 */     ArrayList list = new ArrayList();
/* 5304 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 5306 */       Properties p = new Properties();
/* 5307 */       p.setProperty("label", label[i]);
/* 5308 */       p.setProperty("value", values[i]);
/* 5309 */       list.add(p);
/*      */     }
/* 5311 */     return list;
/*      */   }
/*      */   
/*      */   public void setSpecificType(String specificType)
/*      */   {
/* 5316 */     this.specificType = specificType;
/*      */   }
/*      */   
/*      */   public String getSpecificType() {
/* 5320 */     return this.specificType;
/*      */   }
/*      */   
/*      */   public void setEnterpriseOID(String enterpriseOID)
/*      */   {
/* 5325 */     this.enterpriseOID = enterpriseOID;
/*      */   }
/*      */   
/*      */   public String getEnterpriseOID() {
/* 5329 */     return this.enterpriseOID;
/*      */   }
/*      */   
/*      */   public void setTrapOID(String trapOID)
/*      */   {
/* 5334 */     this.trapOID = trapOID;
/*      */   }
/*      */   
/*      */   public String getTrapOID() {
/* 5338 */     return this.trapOID;
/*      */   }
/*      */   
/*      */   public ArrayList getAlertSeverity()
/*      */   {
/* 5343 */     String[] values = { "1", "4", "5" };
/* 5344 */     String[] label = { FormatUtil.getString("Critical"), FormatUtil.getString("Warning"), FormatUtil.getString("Clear") };
/* 5345 */     ArrayList list = new ArrayList();
/* 5346 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 5348 */       Properties p = new Properties();
/* 5349 */       p.setProperty("label", label[i]);
/* 5350 */       p.setProperty("value", values[i]);
/* 5351 */       list.add(p);
/*      */     }
/* 5353 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSeverity(String severity)
/*      */   {
/* 5359 */     this.severity = severity;
/*      */   }
/*      */   
/*      */   public String getSeverity() {
/* 5363 */     return this.severity;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setThresholdList(String threshold)
/*      */   {
/* 5369 */     this.threshold = threshold;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getThresholdList()
/*      */   {
/* 5375 */     return this.threshold;
/*      */   }
/*      */   
/*      */   public void setChkTrapHost(String chkTrapHost)
/*      */   {
/* 5380 */     this.chkTrapHost = chkTrapHost;
/*      */   }
/*      */   
/*      */   public String getChkTrapHost() {
/* 5384 */     return this.chkTrapHost;
/*      */   }
/*      */   
/*      */   public void setTrapHost(String trapHost)
/*      */   {
/* 5389 */     this.trapHost = trapHost;
/*      */   }
/*      */   
/*      */   public String getTrapHost() {
/* 5393 */     return this.trapHost;
/*      */   }
/*      */   
/*      */   public String[] getTrapActionsCombo1()
/*      */   {
/* 5398 */     return this.trapActionsCombo1;
/*      */   }
/*      */   
/*      */   public void setTrapActionsCombo1(String[] trapActionsCombo1) {
/* 5402 */     this.trapActionsCombo1 = trapActionsCombo1;
/*      */   }
/*      */   
/*      */   public String[] getTrapActionsCombo2()
/*      */   {
/* 5407 */     return this.trapActionsCombo2;
/*      */   }
/*      */   
/*      */   public void setTrapActionsCombo2(String[] trapActionsCombo2) {
/* 5411 */     this.trapActionsCombo2 = trapActionsCombo2;
/*      */   }
/*      */   
/* 5414 */   private String mailFormat = "0";
/*      */   
/*      */   public void setMailFormat(String mailFormat) {
/* 5417 */     this.mailFormat = mailFormat;
/*      */   }
/*      */   
/*      */   public String getMailFormat() {
/* 5421 */     return this.mailFormat;
/*      */   }
/*      */   
/* 5424 */   private String appendMessage = "1";
/*      */   private String dailyhour;
/*      */   private String dailyminute;
/*      */   private String monday;
/*      */   private String tuesday;
/*      */   private String wednesday;
/*      */   private String thursday;
/*      */   
/*      */   public String getConsecutive_criticalpolls() {
/* 5433 */     return this.consecutive_criticalpolls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setConsecutive_criticalpolls(String v)
/*      */   {
/* 5442 */     this.consecutive_criticalpolls = v;
/*      */   }
/*      */   
/*      */   public void setConsecutive_mincriticalpolls(String critical) {
/* 5446 */     this.consecutive_mincriticalpolls = critical;
/*      */   }
/*      */   
/*      */   public String getConsecutive_mincriticalpolls() {
/* 5450 */     return this.consecutive_mincriticalpolls;
/*      */   }
/*      */   
/*      */   public void setConsecutive_minwarningpolls(String warning) {
/* 5454 */     this.consecutive_minwarningpolls = warning;
/*      */   }
/*      */   
/*      */   public String getConsecutive_minwarningpolls() {
/* 5458 */     return this.consecutive_minwarningpolls;
/*      */   }
/*      */   
/*      */   public void setConsecutive_minclearpolls(String clear) {
/* 5462 */     this.consecutive_minclearpolls = clear;
/*      */   }
/*      */   
/*      */   public String getConsecutive_minclearpolls() {
/* 5466 */     return this.consecutive_minclearpolls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getConsecutive_warningpolls()
/*      */   {
/* 5475 */     return this.consecutive_warningpolls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setConsecutive_warningpolls(String v)
/*      */   {
/* 5484 */     this.consecutive_warningpolls = v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getConsecutive_clearpolls()
/*      */   {
/* 5494 */     return this.consecutive_clearpolls;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setConsecutive_clearpolls(String v)
/*      */   {
/* 5503 */     this.consecutive_clearpolls = v;
/*      */   }
/*      */   
/*      */   public void setAppendMessage(String appendMessage)
/*      */   {
/* 5508 */     this.appendMessage = appendMessage;
/*      */   }
/*      */   
/*      */   public String getAppendMessage() {
/* 5512 */     return this.appendMessage;
/*      */   }
/*      */   
/*      */   public String getNumeric_att() {
/* 5516 */     return this.numeric_att;
/*      */   }
/*      */   
/*      */   public void setNumeric_att(String numeric_att) {
/* 5520 */     this.numeric_att = numeric_att;
/*      */   }
/*      */   
/*      */   public String getString_att() {
/* 5524 */     return this.string_att;
/*      */   }
/*      */   
/*      */   public void setString_att(String string_att) {
/* 5528 */     this.string_att = string_att;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQengineHome()
/*      */   {
/* 5535 */     return this.qengineHome;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setQengineHome(String qengineHome)
/*      */   {
/* 5541 */     this.qengineHome = qengineHome;
/*      */   }
/*      */   
/*      */   public boolean isAuto_restart() {
/* 5545 */     return this.auto_restart;
/*      */   }
/*      */   
/*      */   public void setAuto_restart(boolean auto_restart) {
/* 5549 */     this.auto_restart = auto_restart;
/*      */   }
/*      */   
/*      */   public boolean isPresales_emails() {
/* 5553 */     return this.presales_emails;
/*      */   }
/*      */   
/*      */   public void setPresales_emails(boolean presales_emails) {
/* 5557 */     this.presales_emails = presales_emails;
/*      */   }
/*      */   
/*      */   public boolean getEasyUpgrade() {
/* 5561 */     return this.easyUpgrade;
/*      */   }
/*      */   
/*      */   public void setEasyUpgrade(boolean easyupgrade) {
/* 5565 */     this.easyUpgrade = easyupgrade;
/*      */   }
/*      */   
/*      */   public boolean getSelfMonitoring() {
/* 5569 */     return this.selfMonitoring;
/*      */   }
/*      */   
/*      */   public void setSelfMonitoring(boolean selfMonitoring) {
/* 5573 */     this.selfMonitoring = selfMonitoring;
/*      */   }
/*      */   
/*      */   public boolean getShowSalesForce() {
/* 5577 */     return this.showSalesForce;
/*      */   }
/*      */   
/*      */   public void setShowSalesForce(boolean showSalesForce) {
/* 5581 */     this.showSalesForce = showSalesForce;
/*      */   }
/*      */   
/*      */   public boolean isSenderrormail() {
/* 5585 */     return this.senderrormail;
/*      */   }
/*      */   
/*      */   public void setSenderrormail(boolean senderrormail) {
/* 5589 */     this.senderrormail = senderrormail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isSmtpauthenabled()
/*      */   {
/* 5596 */     return this.smtpauthenabled;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setSmtpauthenabled(boolean smtpauthenabled)
/*      */   {
/* 5603 */     this.smtpauthenabled = smtpauthenabled;
/*      */   }
/*      */   
/*      */   public String getWtaport() {
/* 5607 */     return this.wtaport;
/*      */   }
/*      */   
/*      */   public void setWtaport(String wtaport) {
/* 5611 */     this.wtaport = wtaport;
/*      */   }
/*      */   
/*      */   public boolean isWTEnabled() {
/* 5615 */     return this.WTEnabled;
/*      */   }
/*      */   
/*      */   public void setWTEnabled(boolean WTEnabled) {
/* 5619 */     this.WTEnabled = WTEnabled;
/*      */   }
/*      */   
/*      */   public boolean isUseWebServerPort() {
/* 5623 */     return this.useWebServerPort;
/*      */   }
/*      */   
/*      */   public void setUseWebServerPort(boolean useWebServerPort) {
/* 5627 */     this.useWebServerPort = useWebServerPort;
/*      */   }
/*      */   
/*      */   public boolean isInstrumentationEnabled() {
/* 5631 */     return this.instrumentationEnabled;
/*      */   }
/*      */   
/*      */   public void setInstrumentationEnabled(boolean instrumentationEnabled) {
/* 5635 */     this.instrumentationEnabled = instrumentationEnabled;
/*      */   }
/*      */   
/*      */   public int getSamplingFactor() {
/* 5639 */     return this.samplingFactor;
/*      */   }
/*      */   
/*      */   public void setSamplingFactor(int samplingFactor) {
/* 5643 */     this.samplingFactor = samplingFactor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaxURL()
/*      */   {
/* 5651 */     return this.maxURL;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMaxURL(int maxURL)
/*      */   {
/* 5658 */     this.maxURL = maxURL;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getMaxChildren()
/*      */   {
/* 5665 */     return this.maxChildren;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMaxChildren(int maxChildren)
/*      */   {
/* 5672 */     this.maxChildren = maxChildren;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getMaxDepth()
/*      */   {
/* 5679 */     return this.maxDepth;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setMaxDepth(int maxDepth)
/*      */   {
/* 5686 */     this.maxDepth = maxDepth;
/*      */   }
/*      */   
/*      */   public boolean isTracingEnabled() {
/* 5690 */     return this.tracingEnabled;
/*      */   }
/*      */   
/*      */   public void setTracingEnabled(boolean tracingEnabled) {
/* 5694 */     this.tracingEnabled = tracingEnabled;
/*      */   }
/*      */   
/*      */   public int getFullTraceCount() {
/* 5698 */     return this.fullTraceCount;
/*      */   }
/*      */   
/*      */   public void setFullTraceCount(int fullTraceCount) {
/* 5702 */     this.fullTraceCount = fullTraceCount;
/*      */   }
/*      */   
/*      */   public boolean isPackageInclude() {
/* 5706 */     return this.packageInclude;
/*      */   }
/*      */   
/*      */   public void setPackageInclude(boolean packageInclude) {
/* 5710 */     this.packageInclude = packageInclude;
/*      */   }
/*      */   
/*      */   public String getPackageList() {
/* 5714 */     return this.packageList;
/*      */   }
/*      */   
/*      */   public void setPackageList(String packageList) {
/* 5718 */     this.packageList = packageList;
/*      */   }
/*      */   
/*      */   public void setLicFromAddress(String fromAddress) {
/* 5722 */     this.licFromAddress = fromAddress;
/*      */   }
/*      */   
/*      */   public String getLicFromAddress() {
/* 5726 */     return this.licFromAddress;
/*      */   }
/*      */   
/*      */   public void setLicToAddress(String toAddress) {
/* 5730 */     this.licToAddress = toAddress;
/*      */   }
/*      */   
/*      */   public String getLicToAddress() {
/* 5734 */     return this.licToAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getDataclean()
/*      */   {
/* 5742 */     return this.dataclean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDataclean(int dataclean)
/*      */   {
/* 5750 */     this.dataclean = dataclean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getAlertclean()
/*      */   {
/* 5758 */     return this.alertclean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAlertclean(int alertclean)
/*      */   {
/* 5766 */     this.alertclean = alertclean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getDailyclean()
/*      */   {
/* 5774 */     return this.dailyclean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDailyclean(int dailyclean)
/*      */   {
/* 5782 */     this.dailyclean = dailyclean;
/*      */   }
/*      */   
/*      */   public void setUnsolTrapsCleanHrs(int unsolTrapsCleanHrs) {
/* 5786 */     this.unsolTrapsCleanHrs = unsolTrapsCleanHrs;
/*      */   }
/*      */   
/*      */   public int getUnsolTrapsCleanHrs() {
/* 5790 */     return this.unsolTrapsCleanHrs;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getEventalert()
/*      */   {
/* 5798 */     return this.eventalert;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEventalert(int eventalert)
/*      */   {
/* 5806 */     this.eventalert = eventalert;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSettings()
/*      */   {
/* 5814 */     return this.settings;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSettings(String settings)
/*      */   {
/* 5822 */     this.settings = settings;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getGatewayUrlStatus()
/*      */   {
/* 5830 */     return this.gatewayUrlStatus;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGatewayUrlStatus(String gatewayUrlStatus)
/*      */   {
/* 5838 */     this.gatewayUrlStatus = gatewayUrlStatus;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getGatewayUrlName()
/*      */   {
/* 5846 */     return this.gatewayUrlName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setGatewayUrlName(String gatewayUrlName)
/*      */   {
/* 5854 */     this.gatewayUrlName = gatewayUrlName;
/*      */   }
/*      */   
/*      */   public String getSendmail()
/*      */   {
/* 5859 */     return this.sendmail;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSendmail(String sendmail)
/*      */   {
/* 5865 */     this.sendmail = sendmail;
/*      */   }
/*      */   
/*      */   public String getgmapkey()
/*      */   {
/* 5870 */     return this.gmapkey;
/*      */   }
/*      */   
/*      */   public void setgmapkey(String key)
/*      */   {
/* 5875 */     this.gmapkey = key;
/*      */   }
/*      */   
/*      */   public void setGmapheight(String height) {
/* 5879 */     this.gmapheight = height;
/*      */   }
/*      */   
/* 5882 */   public String getGmapheight() { return this.gmapheight; }
/*      */   
/*      */   public String getGmapwidth()
/*      */   {
/* 5886 */     return this.gmapwidth;
/*      */   }
/*      */   
/* 5889 */   public void setGmapwidth(String width) { this.gmapwidth = width; }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getZoomlevel()
/*      */   {
/* 5895 */     return this.zoomlevel;
/*      */   }
/*      */   
/*      */   public void setZoomlevel(String zoom)
/*      */   {
/* 5900 */     this.zoomlevel = zoom;
/*      */   }
/*      */   
/*      */   public int getLocationid()
/*      */   {
/* 5905 */     return this.locationid;
/*      */   }
/*      */   
/*      */   public void setLocationid(int locationid) {
/* 5909 */     this.locationid = locationid;
/*      */   }
/*      */   
/*      */   public void setZoomlocation(String zoomlocation) {
/* 5913 */     this.zoomlocation = zoomlocation;
/*      */   }
/*      */   
/*      */   public String getZoomlocation()
/*      */   {
/* 5918 */     return this.zoomlocation;
/*      */   }
/*      */   
/*      */   public ArrayList getGmapcountries()
/*      */   {
/* 5923 */     return this.gmapcountries;
/*      */   }
/*      */   
/*      */   public void setGmapcountries(ArrayList gmapcountries) {
/* 5927 */     this.gmapcountries = gmapcountries;
/*      */   }
/*      */   
/*      */   public String getEventid() {
/* 5931 */     return this.eventid;
/*      */   }
/*      */   
/*      */   public void setEventid(String eventid) {
/* 5935 */     this.eventid = eventid;
/*      */   }
/*      */   
/*      */   public String getSource()
/*      */   {
/* 5940 */     return this.source;
/*      */   }
/*      */   
/*      */   public void setSource(String source) {
/* 5944 */     this.source = source;
/*      */   }
/*      */   
/*      */   public String getEventtype() {
/* 5948 */     return this.eventtype;
/*      */   }
/*      */   
/*      */   public void setEventtype(String eventtype) {
/* 5952 */     this.eventtype = eventtype;
/*      */   }
/*      */   
/*      */   public String getEventtype_warning()
/*      */   {
/* 5957 */     return this.eventtype_warning;
/*      */   }
/*      */   
/*      */   public void setEventtype_warning(String eventtype) {
/* 5961 */     this.eventtype_warning = eventtype;
/*      */   }
/*      */   
/*      */   public String getEventtype_information()
/*      */   {
/* 5966 */     return this.eventtype_information;
/*      */   }
/*      */   
/*      */   public void setEventtype_information(String eventtype) {
/* 5970 */     this.eventtype_information = eventtype;
/*      */   }
/*      */   
/*      */   public String getEventtype_any()
/*      */   {
/* 5975 */     return this.eventtype_any;
/*      */   }
/*      */   
/*      */   public void setEventtype_any(String eventtype) {
/* 5979 */     this.eventtype_any = eventtype;
/*      */   }
/*      */   
/*      */   public String getEventtype_error()
/*      */   {
/* 5984 */     return this.eventtype_error;
/*      */   }
/*      */   
/*      */   public void setEventtype_error(String eventtype) {
/* 5988 */     this.eventtype_error = eventtype;
/*      */   }
/*      */   
/*      */   public String getRuletype()
/*      */   {
/* 5993 */     return this.ruletype;
/*      */   }
/*      */   
/*      */   public void setRuletype(String ruletype) {
/* 5997 */     this.ruletype = ruletype;
/*      */   }
/*      */   
/*      */   public String getLogCategory() {
/* 6001 */     return this.logCategory;
/*      */   }
/*      */   
/*      */   public void setLogCategory(String name) {
/* 6005 */     this.logCategory = name;
/*      */   }
/*      */   
/*      */   public ArrayList getLogCategoryList() {
/* 6009 */     return this.logCategoryList;
/*      */   }
/*      */   
/*      */   public void setLogCategoryList(ArrayList list) {
/* 6013 */     this.logCategoryList = list;
/*      */   }
/*      */   
/*      */   public String getErrorCode() {
/* 6017 */     return this.errorCode;
/*      */   }
/*      */   
/*      */   public void setErrorCode(String code) {
/* 6021 */     this.errorCode = code;
/*      */   }
/*      */   
/*      */   public String getErrorMessage() {
/* 6025 */     return this.errorMessage;
/*      */   }
/*      */   
/*      */   public void setErrorMessage(String message) {
/* 6029 */     this.errorMessage = message;
/*      */   }
/*      */   
/*      */   public String getStatus() {
/* 6033 */     return this.status;
/*      */   }
/*      */   
/*      */   public void setMatchRules(String rules)
/*      */   {
/* 6038 */     this.matchRules = rules;
/*      */   }
/*      */   
/*      */   public String getMatchRules() {
/* 6042 */     return this.matchRules;
/*      */   }
/*      */   
/*      */   public void setLog_startTime(String logtime) {
/* 6046 */     this.log_startTime = logtime;
/*      */   }
/*      */   
/*      */   public String getLog_startTime() {
/* 6050 */     return this.log_startTime;
/*      */   }
/*      */   
/*      */   public void setLog_endTime(String endTime) {
/* 6054 */     this.log_endTime = endTime;
/*      */   }
/*      */   
/*      */   public String getLog_endTime() {
/* 6058 */     return this.log_endTime;
/*      */   }
/*      */   
/*      */   public void setStatus(String status)
/*      */   {
/* 6063 */     this.status = status;
/*      */   }
/*      */   
/*      */   public boolean isEventlog_status() {
/* 6067 */     return this.eventlog_status;
/*      */   }
/*      */   
/*      */   public void setEventlog_status(boolean eventlog_status)
/*      */   {
/* 6072 */     this.eventlog_status = eventlog_status;
/*      */   }
/*      */   
/* 6075 */   public void setRuleScope(String scope) { this.rulescope = scope; }
/*      */   
/*      */   public String getRuleScope() {
/* 6078 */     return this.rulescope;
/*      */   }
/*      */   
/*      */   public String getSelectedservertype() {
/* 6082 */     return this.selectedservertype;
/*      */   }
/*      */   
/*      */   public void setSelectedservertype(String servertype) {
/* 6086 */     this.selectedservertype = servertype;
/*      */   }
/*      */   
/*      */   public ArrayList getHour()
/*      */   {
/* 6091 */     String[] values = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
/* 6092 */     String[] label = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
/* 6093 */     ArrayList list = new ArrayList();
/* 6094 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 6096 */       Properties p = new Properties();
/* 6097 */       p.setProperty("label", label[i]);
/* 6098 */       p.setProperty("value", values[i]);
/* 6099 */       list.add(p);
/*      */     }
/* 6101 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getMinute()
/*      */   {
/* 6106 */     String[] values = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
/* 6107 */     String[] label = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
/* 6108 */     ArrayList list = new ArrayList();
/* 6109 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 6111 */       Properties p = new Properties();
/* 6112 */       p.setProperty("label", label[i]);
/* 6113 */       p.setProperty("value", values[i]);
/* 6114 */       list.add(p);
/*      */     }
/* 6116 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getAllMinute()
/*      */   {
/* 6121 */     ArrayList list = new ArrayList();
/* 6122 */     for (int i = 0; i <= 59; i++)
/*      */     {
/* 6124 */       String l = "";
/* 6125 */       if (i < 10) {
/* 6126 */         l = "0" + i;
/*      */       } else {
/* 6128 */         l = String.valueOf(i);
/*      */       }
/*      */       
/* 6131 */       Properties p = new Properties();
/* 6132 */       p.setProperty("label", l);
/* 6133 */       p.setProperty("value", String.valueOf(i));
/* 6134 */       list.add(p);
/*      */     }
/* 6136 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getDaysForMonthly()
/*      */   {
/* 6141 */     String[] values = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
/* 6142 */     String[] label = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
/* 6143 */     ArrayList list = new ArrayList();
/* 6144 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6147 */       Properties p = new Properties();
/* 6148 */       p.setProperty("label", label[i]);
/* 6149 */       p.setProperty("value", values[i]);
/* 6150 */       list.add(p);
/*      */     }
/* 6152 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getReportTypes()
/*      */   {
/* 6157 */     String[] values = { "availability", "health", "attribute", "event", "downtime", "summary", "custom", "hasnapshot", "hasnapshotHost", "availabilitysnapshot", "weeklyoutage", "availabilitytrend", "availabilitytrenddowntime", "glancereport", "eumGlancereport", "eumSummary", "sla", "dashboard", "forecast", "sqlperformance" };
/* 6158 */     String[] label = { FormatUtil.getString("Availability"), FormatUtil.getString("Health"), FormatUtil.getString("Attribute"), FormatUtil.getString("Alert"), FormatUtil.getString("DowntimeHistory"), FormatUtil.getString("Summary"), FormatUtil.getString("CustomAttribute"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitysnapshot.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitycriticalsnapshot.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilityhistory.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.outagecomparison.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitytrend.text"), FormatUtil.getString("am.webclient.reports.AvailabilityTrend&DowntimeReport.text"), FormatUtil.getString("am.webclient.reports.ataglance.report"), FormatUtil.getString("am.webclient.eum.glancereport"), FormatUtil.getString("am.webclient.eum.summaryreport"), FormatUtil.getString("SLA"), FormatUtil.getString("am.webclient.reports.schedulereport.dashboardReport.text"), FormatUtil.getString("am.webclient.forecast.report.text"), FormatUtil.getString("am.webclient.performance.report.mssql.text") };
/*      */     
/* 6160 */     if (Constants.sqlManager)
/*      */     {
/* 6162 */       values = new String[] { "availability", "health", "attribute", "event", "downtime", "summary", "hasnapshot", "hasnapshotHost", "availabilitysnapshot", "weeklyoutage", "availabilitytrend", "glancereport" };
/* 6163 */       label = new String[] { FormatUtil.getString("Availability"), FormatUtil.getString("Health"), FormatUtil.getString("Attribute"), FormatUtil.getString("Alert"), FormatUtil.getString("DowntimeHistory"), FormatUtil.getString("Summary"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitysnapshot.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitycriticalsnapshot.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilityhistory.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.outagecomparison.text"), FormatUtil.getString("am.webclient.reports.schedulereport.display.availabilitytrend.text"), FormatUtil.getString("am.webclient.reports.ataglance.report") };
/*      */     }
/*      */     
/* 6166 */     ArrayList list = new ArrayList();
/* 6167 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6170 */       Properties p = new Properties();
/* 6171 */       p.setProperty("label", label[i]);
/* 6172 */       p.setProperty("value", values[i]);
/* 6173 */       list.add(p);
/*      */     }
/* 6175 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getReportPeriod() {
/* 6179 */     String[] values = { "0", "3", "6", "1", "12", "7", "2", "11", "9", "8", "5" };
/* 6180 */     String[] label = { FormatUtil.getString("Today"), FormatUtil.getString("Yesterday"), FormatUtil.getString("This Week"), FormatUtil.getString("Last 7 Days"), FormatUtil.getString("Last Week"), FormatUtil.getString("This Month"), FormatUtil.getString("Last 30 Days"), FormatUtil.getString("Last Month"), FormatUtil.getString("This Quarter"), FormatUtil.getString("This Year"), FormatUtil.getString("Last 1 Year") };
/* 6181 */     ArrayList list = new ArrayList();
/* 6182 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6185 */       Properties p = new Properties();
/* 6186 */       p.setProperty("label", label[i]);
/* 6187 */       p.setProperty("value", values[i]);
/* 6188 */       list.add(p);
/*      */     }
/* 6190 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getForecastReportPeriod()
/*      */   {
/* 6196 */     String[] values = { "1", "2", "16", "17", "5" };
/* 6197 */     String[] label = { FormatUtil.getString("Last 7 Days"), FormatUtil.getString("Last 30 Days"), FormatUtil.getString("am.webclient.historydata.period.last3months.text"), FormatUtil.getString("am.webclient.historydata.period.last6months.text"), FormatUtil.getString("Last 1 Year") };
/* 6198 */     ArrayList list = new ArrayList();
/* 6199 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6202 */       Properties p = new Properties();
/* 6203 */       p.setProperty("label", label[i]);
/* 6204 */       p.setProperty("value", values[i]);
/* 6205 */       list.add(p);
/*      */     }
/* 6207 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getReportPeriodForAvailabilityTrend()
/*      */   {
/* 6213 */     String[] values = { "day", "week", "month" };
/* 6214 */     String[] label = { FormatUtil.getString("Day"), FormatUtil.getString("Week"), FormatUtil.getString("Month") };
/* 6215 */     ArrayList list = new ArrayList();
/* 6216 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6219 */       Properties p = new Properties();
/* 6220 */       p.setProperty("label", label[i]);
/* 6221 */       p.setProperty("value", values[i]);
/* 6222 */       list.add(p);
/*      */     }
/* 6224 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getReportPeriodForOutage()
/*      */   {
/* 6229 */     String[] values = { "week", "month" };
/* 6230 */     String[] label = { FormatUtil.getString("Week"), FormatUtil.getString("Month") };
/* 6231 */     ArrayList list = new ArrayList();
/* 6232 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6235 */       Properties p = new Properties();
/* 6236 */       p.setProperty("label", label[i]);
/* 6237 */       p.setProperty("value", values[i]);
/* 6238 */       list.add(p);
/*      */     }
/* 6240 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getReportAttribute()
/*      */   {
/* 6245 */     String[] values = { "responseTime", "jvm", "jdbc", "thread", "session", "throughput", "webappthroughput", "connectionTime", "buffer", "cache", "cpuid", "mem", "disk", "operationExecutionTime", "jdkcpuid", "memmb", "sapcpu", "sapmemory", "sapdisk", "sappagein", "sappageout", "sutilization", "butilization", "sapferestime", "sapenqreq" };
/* 6246 */     String[] label = { FormatUtil.getString("Response Time"), FormatUtil.getString("JVM Details"), FormatUtil.getString("am.webclient.jboss.jdbcconnectionpool.text"), FormatUtil.getString("Thread Details"), FormatUtil.getString("HTTP Session"), FormatUtil.getString("Request Throughput"), FormatUtil.getString("Web Application Throughput"), FormatUtil.getString("Connection Time"), FormatUtil.getString("Buffer Hit Ratio"), FormatUtil.getString("Cache Hit Ratio"), FormatUtil.getString("CPU Usage"), FormatUtil.getString("Memory Usage"), FormatUtil.getString("Disk Usage"), FormatUtil.getString("am.reporttab.shortname.operationexecutiontime.text"), FormatUtil.getString("Java Runtime-CPU Usage"), FormatUtil.getString("Java Runtime-Memory Usage"), FormatUtil.getString("am.schreporting.sap.cpuusage"), FormatUtil.getString("am.schreporting.sap.memusage"), FormatUtil.getString("am.schreporting.sap.diskusage"), FormatUtil.getString("am.schreporting.sap.pagein"), FormatUtil.getString("am.schreporting.sap.pageout"), FormatUtil.getString("am.schreporting.sap.spoolusage"), FormatUtil.getString("am.schreporting.sap.bgusage"), FormatUtil.getString("am.schreporting.sap.frontendresptime"), FormatUtil.getString("am.schreporting.sap.enquerequest") };
/* 6247 */     if (Constants.getCategorytype().equals("LAMP"))
/*      */     {
/* 6249 */       values = new String[] { "responseTime", "connectionTime", "buffer", "cache", "cpuid", "mem", "disk" };
/* 6250 */       label = new String[] { "Response Time", "Connection Time", "Buffer Hit Ratio", "Cache Hit Ratio", "CPU Usage", "Memory Usage", "Disk Usage" };
/*      */     }
/* 6252 */     else if (Constants.getCategorytype().equals("DATABASE"))
/*      */     {
/* 6254 */       values = new String[] { "responseTime", "connectionTime", "buffer", "cache", "cpuid", "mem", "disk" };
/* 6255 */       label = new String[] { "Response Time", "Connection Time", "Buffer Hit Ratio", "Cache Hit Ratio", "CPU Usage", "Memory Usage", "Disk Usage" };
/*      */     }
/* 6257 */     if (Constants.getCategorytype().equals("CLOUD"))
/*      */     {
/* 6259 */       values = new String[] { "responseTime", "jvm", "jdbc", "thread", "session", "throughput", "webappthroughput", "connectionTime", "buffer", "cache", "cpuid", "mem", "disk", "operationExecutionTime", "jdkcpuid", "memmb" };
/* 6260 */       label = new String[] { FormatUtil.getString("Response Time"), FormatUtil.getString("JVM Details"), FormatUtil.getString("am.webclient.jboss.jdbcconnectionpool.text"), FormatUtil.getString("Thread Details"), FormatUtil.getString("HTTP Session"), FormatUtil.getString("Request Throughput"), FormatUtil.getString("Web Application Throughput"), FormatUtil.getString("Connection Time"), FormatUtil.getString("Buffer Hit Ratio"), FormatUtil.getString("Cache Hit Ratio"), FormatUtil.getString("CPU Usage"), FormatUtil.getString("Memory Usage"), FormatUtil.getString("Disk Usage"), FormatUtil.getString("am.reporttab.shortname.operationexecutiontime.text"), FormatUtil.getString("Java Runtime-CPU Usage"), FormatUtil.getString("Java Runtime-Memory Usage") };
/*      */     }
/* 6262 */     ArrayList list = new ArrayList();
/* 6263 */     for (int i = 0; i < values.length; i++)
/*      */     {
/*      */ 
/* 6266 */       Properties p = new Properties();
/* 6267 */       p.setProperty("label", label[i]);
/* 6268 */       p.setProperty("value", values[i]);
/* 6269 */       list.add(p);
/*      */     }
/* 6271 */     ReportForm rf = new ReportForm();
/* 6272 */     if (Constants.sqlManager)
/*      */     {
/* 6274 */       ArrayList listsql = new ArrayList();
/* 6275 */       values = new String[] { "responseTime", "connectionTime", "buffer", "cache", "cpuid", "mem", "disk" };
/* 6276 */       label = new String[] { "Response Time", "Connection Time", "Buffer Hit Ratio", "Cache Hit Ratio", "CPU Usage", "Memory Usage", "Disk Usage" };
/* 6277 */       for (int i = 0; i < values.length; i++)
/*      */       {
/* 6279 */         Properties p = new Properties();
/* 6280 */         p.setProperty("label", label[i]);
/* 6281 */         p.setProperty("value", values[i]);
/* 6282 */         listsql.add(p);
/*      */       }
/* 6284 */       ArrayList db = rf.getDbArrayAttribute();
/* 6285 */       ArrayList allArrays = new ArrayList();
/* 6286 */       allArrays.add(db);
/* 6287 */       for (int g = 0; g < allArrays.size(); g++) {
/* 6288 */         ArrayList R1 = (ArrayList)allArrays.get(g);
/* 6289 */         for (int h = 0; h < R1.size(); h++) {
/* 6290 */           Properties p1 = (Properties)R1.get(h);
/* 6291 */           listsql.add(p1);
/*      */         }
/*      */       }
/* 6294 */       return listsql;
/*      */     }
/*      */     
/* 6297 */     ArrayList app = rf.getAppArrayAttribute();
/* 6298 */     ArrayList db = rf.getDbArrayAttribute();
/* 6299 */     ArrayList sys = rf.getServerArrayAttribute();
/* 6300 */     ArrayList webser = rf.getWebserviceArrayAttribute();
/*      */     
/*      */ 
/* 6303 */     ArrayList urls = rf.getUrlsArrayAttribute();
/* 6304 */     ArrayList ser = rf.getServicesArrayAttribute();
/* 6305 */     ArrayList tm = rf.getTransactionArrayAttribute();
/* 6306 */     String category = Constants.getCategorytype();
/*      */     
/* 6308 */     ArrayList ms = rf.getMailserverArrayAttribute();
/*      */     
/* 6310 */     ArrayList vir = rf.getVirserverArrayAttribute();
/* 6311 */     ArrayList cld = rf.getCloudAppsArrayAttribute();
/* 6312 */     ArrayList allArrays = new ArrayList();
/* 6313 */     ArrayList erp = new ArrayList();
/* 6314 */     ArrayList mom = new ArrayList();
/* 6315 */     if (!category.equalsIgnoreCase("CLOUD"))
/*      */     {
/* 6317 */       erp = rf.getErpArrayAttribute();
/* 6318 */       mom = rf.getMomArrayAttribute();
/*      */     }
/* 6320 */     allArrays.add(app);
/* 6321 */     allArrays.add(db);
/* 6322 */     allArrays.add(sys);
/* 6323 */     allArrays.add(webser);
/*      */     
/* 6325 */     allArrays.add(urls);
/* 6326 */     allArrays.add(ser);
/* 6327 */     allArrays.add(tm);
/* 6328 */     if (!category.equalsIgnoreCase("CLOUD"))
/*      */     {
/* 6330 */       allArrays.add(erp);
/* 6331 */       allArrays.add(mom);
/*      */     }
/* 6333 */     allArrays.add(ms);
/*      */     
/* 6335 */     allArrays.add(vir);
/* 6336 */     allArrays.add(cld);
/*      */     
/*      */ 
/* 6339 */     for (int g = 0; g < allArrays.size(); g++) {
/* 6340 */       ArrayList R1 = (ArrayList)allArrays.get(g);
/* 6341 */       for (int h = 0; h < R1.size(); h++) {
/* 6342 */         Properties p1 = (Properties)R1.get(h);
/* 6343 */         list.add(p1);
/*      */       }
/*      */     }
/*      */     
/* 6347 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getForecastReportAttribute()
/*      */   {
/* 6352 */     String[] values = null;
/* 6353 */     String[] label = null;
/* 6354 */     ArrayList list = new ArrayList();
/* 6355 */     ReportForm rf = new ReportForm();
/*      */     
/*      */ 
/* 6358 */     ArrayList app = rf.getAppArrayAttribute();
/* 6359 */     ArrayList db = rf.getDbArrayAttribute();
/* 6360 */     ArrayList sys = rf.getServerArrayAttribute();
/* 6361 */     ArrayList webser = rf.getWebserviceArrayAttribute();
/*      */     
/*      */ 
/* 6364 */     ArrayList urls = rf.getUrlsArrayAttribute();
/* 6365 */     ArrayList ser = rf.getServicesArrayAttribute();
/* 6366 */     ArrayList tm = rf.getTransactionArrayAttribute();
/* 6367 */     String category = Constants.getCategorytype();
/*      */     
/* 6369 */     ArrayList ms = rf.getMailserverArrayAttribute();
/*      */     
/* 6371 */     ArrayList vir = rf.getVirserverArrayAttribute();
/* 6372 */     ArrayList cld = rf.getCloudAppsArrayAttribute();
/* 6373 */     ArrayList allArrays = new ArrayList();
/* 6374 */     ArrayList erp = new ArrayList();
/* 6375 */     ArrayList mom = new ArrayList();
/* 6376 */     if (!category.equalsIgnoreCase("CLOUD"))
/*      */     {
/* 6378 */       erp = rf.getErpArrayAttribute();
/* 6379 */       mom = rf.getMomArrayAttribute();
/*      */     }
/* 6381 */     allArrays.add(app);
/* 6382 */     allArrays.add(db);
/* 6383 */     allArrays.add(sys);
/* 6384 */     allArrays.add(webser);
/*      */     
/* 6386 */     allArrays.add(urls);
/* 6387 */     allArrays.add(ser);
/* 6388 */     allArrays.add(tm);
/* 6389 */     if (!category.equalsIgnoreCase("CLOUD"))
/*      */     {
/* 6391 */       allArrays.add(erp);
/* 6392 */       allArrays.add(mom);
/*      */     }
/* 6394 */     allArrays.add(ms);
/*      */     
/* 6396 */     allArrays.add(vir);
/* 6397 */     allArrays.add(cld);
/*      */     
/*      */ 
/* 6400 */     for (int g = 0; g < allArrays.size(); g++) {
/* 6401 */       ArrayList R1 = (ArrayList)allArrays.get(g);
/* 6402 */       for (int h = 0; h < R1.size(); h++) {
/* 6403 */         Properties p1 = (Properties)R1.get(h);
/* 6404 */         list.add(p1);
/*      */       }
/*      */     }
/* 6407 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getResourcesForReports()
/*      */   {
/* 6412 */     ArrayList scheduleRepList = new ArrayList();
/*      */     try {
/* 6414 */       scheduleRepList = ReportUtil.getScheduleRepList();
/*      */     }
/*      */     catch (Exception e) {
/* 6417 */       e.printStackTrace();
/*      */     }
/* 6419 */     if (OEMUtil.isRemove("am.wmimonitors.remove"))
/*      */     {
/* 6421 */       String[] values1 = { "JBOSS-server','Tomcat-server','WEBLOGIC-server','WebSphere-server','ORACLE-APP-server','GlassFish','SilverStream','MYSQL-DB-server','ORACLE-DB-server','MSSQL-DB-server','SYBASE-DB-server','Memcached','DB2-server','Script Monitor','Apache-server','IIS-server','RMI','MAIL-server','PHP','ActiveDirectory','DNSMonitor','FTPMonitor','LDAP Server','JMX1.2-MX4J-RMI','JDK1.5','Port-Test','TELNET','SNMP','WEB-server','UrlMonitor','UrlSeq','RBM','Custom-Application','Novell','Linux','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012','SUN','SUN PC','Sun Solaris','WLS-Cluster','AIX','AS400/iSeries','HP-UX','HP-TRU64','HP-UX / Tru64','FreeBSD / OpenBSD','Mac OS','WTA','Web Service','WebsphereMQ','OfficeSharePointServer','WEBLOGIC-Integration','QueryMonitor','VMWare ESX/ESXi','VirtualMachine','Amazon','EC2Instance','RDSInstance", "WEBLOGIC-server','JBOSS-server','WebSphere-server','Tomcat-server','ORACLE-APP-server','GlassFish','SilverStream", "JBOSS-server", "Tomcat-server", "WEBLOGIC-server','WLS-Cluster", "WebSphere-server", "ORACLE-APP-server", "GlassFish", "SilverStream", "DB2-server','PostgreSQL','MSSQL-DB-server','SYBASE-DB-server','MYSQL-DB-server','ORACLE-DB-server','Memcached", "DB2-server", "PostgreSQL", "MSSQL-DB-server", "SYBASE-DB-server", "MYSQL-DB-server", "ORACLE-DB-server", "Memcached", "Apache-server','IIS-server','PHP','UrlMonitor','UrlSeq','RBM','WEB-server','Web Service", "Apache-server", "IIS-server", "PHP", "UrlMonitor", "UrlSeq", "RBM", "WEB-server", "Web Service", "AIX','AS400/iSeries','HP-UX','HP-TRU64','HP-UX / Tru64','FreeBSD / OpenBSD','Mac OS','Novell','Linux','Sun Solaris','SUN','SUN PC','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012','Node','snmp-node", "AIX", "AS400/iSeries", "HP-UX", "HP-TRU64','HP-UX / Tru64", "FreeBSD / OpenBSD", "Mac OS", "Linux", "Sun Solaris','SUN','SUN PC", "Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012", "MAIL-server", "MAIL-server", "ActiveDirectory','JMX1.2-MX4J-RMI','Port-Test','TELNET','RMI','SNMP','Ping Monitor','DNSMonitor','FTPMonitor','LDAP Server", "ActiveDirectory", "DNSMonitor", "JMX1.2-MX4J-RMI", "JDK1.5", "Port-Test", "SNMP", "TELNET", "FTPMonitor", "LDAP Server", "WebsphereMQ','OfficeSharePointServer','WEBLOGIC-Integration", "WebsphereMQ", "OfficeSharePointServer", "WEBLOGIC-Integration", "Custom-Application','Script Monitor','QueryMonitor", "Custom-Application", "Script Monitor", "QueryMonitor", "Novell", "RBM", "VMWare ESX/ESXi','VirtualMachine", "VMWare ESX/ESXi", "VirtualMachine", "Amazon','EC2Instance','RDSInstance", "Amazon", "EC2Instance", "RDSInstance" };
/* 6422 */       String[] label1 = { FormatUtil.getString("am.monitortab.allmonitors.text"), FormatUtil.getString("Application Servers"), FormatUtil.getString("JBoss server"), FormatUtil.getString("Tomcat-server"), FormatUtil.getString("Weblogic server"), FormatUtil.getString("WebSphere server"), FormatUtil.getString("Oracle Server"), FormatUtil.getString("GlassFish"), FormatUtil.getString("SilverStream"), FormatUtil.getString("Database Servers"), FormatUtil.getString("DB2"), FormatUtil.getString("PostgreSQL"), FormatUtil.getString("MS SQL"), FormatUtil.getString("Sybase"), FormatUtil.getString("MySQL"), FormatUtil.getString("Oracle"), FormatUtil.getString("Memcached"), FormatUtil.getString("Web Services"), FormatUtil.getString("Apache Server"), FormatUtil.getString("IIS server"), FormatUtil.getString("PHP"), FormatUtil.getString("HTTP-URLs"), FormatUtil.getString("HTTP-URL Sequence"), FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text"), FormatUtil.getString("Web Server"), FormatUtil.getString("Web Service"), FormatUtil.getString("Servers"), FormatUtil.getString("AIX"), FormatUtil.getString("AS400/iSeries"), FormatUtil.getString("HP-UX"), FormatUtil.getString("Tru64 UNIX"), FormatUtil.getString("FreeBSD / OpenBSD"), FormatUtil.getString("Mac OS"), FormatUtil.getString("Linux"), FormatUtil.getString("Sun Solaris"), FormatUtil.getString("Windows"), FormatUtil.getString("Mail Servers"), FormatUtil.getString("Mail Server"), FormatUtil.getString("Services"), FormatUtil.getString("am.webclient.AdMonitor.text"), FormatUtil.getString("am.webclient.reports.dns.text"), FormatUtil.getString("JMX Applications"), FormatUtil.getString("JDK1.5"), FormatUtil.getString("Service Monitoring"), FormatUtil.getString("SNMP"), FormatUtil.getString("am.webclient.hostdiscovery.telnet"), FormatUtil.getString("am.webclient.reports.ftp.text"), FormatUtil.getString("am.webclient.reports.ldap.text"), FormatUtil.getString("Middleware/Portal"), FormatUtil.getString("WebsphereMQ"), FormatUtil.getString("OfficeSharePointServer"), FormatUtil.getString("WebLogic Integration"), FormatUtil.getString("Custom Monitors"), FormatUtil.getString("JMX/SNMP Dashboard"), FormatUtil.getString("Script Monitor"), FormatUtil.getString("SQL Query Monitor"), FormatUtil.getString("Novell"), FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text"), FormatUtil.getString("am.webclient.monitorgroupsecond.category.virtualserver"), FormatUtil.getString("VMWare ESX/ESXi"), FormatUtil.getString("Virtual Machine"), FormatUtil.getString("am.webclient.monitorgroupsecond.category.cloudapps"), FormatUtil.getString("Amazon"), FormatUtil.getString("EC2Instance"), FormatUtil.getString("RDSInstance") };
/* 6423 */       int length1 = Constants.resourceTypes_array.length + 1;
/* 6424 */       String[] values_back1 = new String[length1];
/* 6425 */       String[] label_back1 = new String[length1];
/* 6426 */       if (Constants.isMinimizedversion())
/*      */       {
/* 6428 */         values_back1[0] = "JBOSS-server','Tomcat-server','WEBLOGIC-server','WebSphere-server','ORACLE-APP-server','MYSQL-DB-server','ORACLE-DB-server','MSSQL-DB-server','SYBASE-DB-server','DB2-server','Script Monitor','Apache-server','IIS-server','RMI','MAIL-server','PHP','JMX1.2-MX4J-RMI','JDK1.5','Port-Test','TELNET','SNMP','WEB-server','UrlMonitor','UrlSeq','Custom-Application','Linux','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012','SUN','SUN PC','Sun Solaris','Node','snmp-node','WLS-Cluster','AIX','AS400/iSeries','HP','FreeBSD / OpenBSD','Mac OS','WTA','Web Service','Novell,'RBM";
/* 6429 */         label_back1[0] = "All";
/* 6430 */         for (int i = 0; i < Constants.resourceTypes_array.length; i++)
/*      */         {
/* 6432 */           values_back1[(i + 1)] = Constants.resourceTypes_array[i];
/* 6433 */           int j = 0;
/* 6434 */           for (j = 1; j < label1.length; j++)
/*      */           {
/* 6436 */             if (Constants.resourceTypes_array[i].equals(values1[j]))
/*      */               break;
/*      */           }
/* 6439 */           if (j < label1.length)
/*      */           {
/* 6441 */             label_back1[(i + 1)] = label1[j];
/*      */           }
/*      */           else
/*      */           {
/* 6445 */             label_back1[(i + 1)] = values_back1[(i + 1)];
/*      */           }
/*      */         }
/*      */         
/* 6449 */         values1 = values_back1;
/* 6450 */         label1 = label_back1;
/*      */       }
/*      */       
/* 6453 */       ArrayList list1 = new ArrayList();
/* 6454 */       for (int i = 0; i < values1.length; i++)
/*      */       {
/*      */ 
/* 6457 */         Properties p1 = new Properties();
/* 6458 */         p1.setProperty("label", label1[i]);
/* 6459 */         p1.setProperty("value", values1[i]);
/* 6460 */         list1.add(p1);
/*      */       }
/* 6462 */       return list1;
/*      */     }
/* 6464 */     String[] values = { "JBOSS-server','Tomcat-server','WEBLOGIC-server','WebSphere-server','ORACLE-APP-server','GlassFish','SilverStream','MYSQL-DB-server','ORACLE-DB-server','MSSQL-DB-server','SYBASE-DB-server','DB2-server','PostgreSQL','Memcached','Script Monitor','Apache-server','IIS-server','RMI','MAIL-server','PHP','ActiveDirectory','DNSMonitor','FTPMonitor','LDAP Server','JMX1.2-MX4J-RMI','JDK1.5','Port-Test','TELNET','SNMP','WEB-server','UrlMonitor','UrlSeq','RBM','Custom-Application','Linux','Novell','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012','SUN','SUN PC','Sun Solaris','WLS-Cluster','AIX','AS400/iSeries','HP-UX','HP-TRU64','HP-UX / Tru64','FreeBSD / OpenBSD','Mac OS','Exchange-server','WTA','.Net','Web Service','Generic WMI','WebsphereMQ','OfficeSharePointServer','WEBLOGIC-Integration','QueryMonitor','VMWare ESX/ESXi','VirtualMachine','Amazon','EC2Instance','RDSInstance", "WEBLOGIC-server','JBOSS-server','WebSphere-server','Tomcat-server','.Net','ORACLE-APP-server','GlassFish','SilverStream", "JBOSS-server", ".Net", "Tomcat-server", "WEBLOGIC-server','WLS-Cluster", "WebSphere-server", "ORACLE-APP-server", "GlassFish", "SilverStream", "DB2-server','PostgreSQL','MSSQL-DB-server','SYBASE-DB-server','MYSQL-DB-server','ORACLE-DB-server','Memcached", "DB2-server", "PostgreSQL", "MSSQL-DB-server", "SYBASE-DB-server", "MYSQL-DB-server", "ORACLE-DB-server", "Memcached", "Apache-server','IIS-server','PHP','UrlMonitor','UrlSeq','RBM','WEB-server','Web Service", "Apache-server", "IIS-server", "PHP", "UrlMonitor", "UrlSeq", "RBM", "WEB-server", "Web Service", "AIX','AS400/iSeries','HP-UX','HP-TRU64','HP-UX / Tru64','FreeBSD / OpenBSD','Mac OS','Linux','Novell','Sun Solaris','SUN','SUN PC','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Node','snmp-node", "AIX", "AS400/iSeries", "HP-UX", "HP-TRU64','HP-UX / Tru64", "FreeBSD / OpenBSD", "Mac OS", "Linux", "Novell", "Sun Solaris','SUN','SUN PC", "Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows 2003','Windows 7','Windows 2008", "Exchange-server','MAIL-server", "Exchange-server", "MAIL-server", "ActiveDirectory','JMX1.2-MX4J-RMI','Port-Test','TELNET','RMI','SNMP','Ping Monitor','DNSMonitor','FTPMonitor','LDAP Server", "ActiveDirectory", "DNSMonitor", "JMX1.2-MX4J-RMI", "JDK1.5", "Port-Test", "RMI", "SNMP", "TELNET", "FTPMonitor", "LDAP Server", "WebsphereMQ','OfficeSharePointServer','WEBLOGIC-Integration", "WebsphereMQ", "OfficeSharePointServer", "WEBLOGIC-Integration", "Custom-Application','Script Monitor','Generic WMI','QueryMonitor", "Custom-Application", "Script Monitor", "Generic WMI", "QueryMonitor", "VMWare ESX/ESXi','VirtualMachine", "VMWare ESX/ESXi", "VirtualMachine", "Amazon','EC2Instance','RDSInstance", "Amazon", "EC2Instance", "RDSInstance" };
/* 6465 */     String[] label = { FormatUtil.getString("am.monitortab.allmonitors.text"), FormatUtil.getString("Application Servers"), FormatUtil.getString("JBoss server"), FormatUtil.getString("Microsoft .NET"), FormatUtil.getString("Tomcat-server"), FormatUtil.getString("Weblogic server"), FormatUtil.getString("WebSphere server"), FormatUtil.getString("Oracle Server"), FormatUtil.getString("GlassFish"), FormatUtil.getString("SilverStream"), FormatUtil.getString("Database Servers"), FormatUtil.getString("DB2"), FormatUtil.getString("PostgreSQL"), FormatUtil.getString("MS SQL"), FormatUtil.getString("Sybase"), FormatUtil.getString("MySQL"), FormatUtil.getString("Oracle"), FormatUtil.getString("Memcached"), FormatUtil.getString("Web Services"), FormatUtil.getString("Apache Server"), FormatUtil.getString("IIS server"), FormatUtil.getString("PHP"), FormatUtil.getString("HTTP-URLs"), FormatUtil.getString("HTTP-URL Sequence"), FormatUtil.getString("am.reporttab.shortname.rbmmonitoring.text"), FormatUtil.getString("Web Server"), FormatUtil.getString("Web Service"), FormatUtil.getString("Servers"), FormatUtil.getString("AIX"), FormatUtil.getString("AS400/iSeries"), FormatUtil.getString("HP-UX"), FormatUtil.getString("Tru64 UNIX"), FormatUtil.getString("FreeBSD / OpenBSD"), FormatUtil.getString("Mac OS"), FormatUtil.getString("Linux"), FormatUtil.getString("Novell"), FormatUtil.getString("Sun Solaris"), FormatUtil.getString("Windows"), FormatUtil.getString("Mail Servers"), FormatUtil.getString("Exchange Server"), FormatUtil.getString("Mail Server"), FormatUtil.getString("Services"), FormatUtil.getString("am.webclient.AdMonitor.text"), FormatUtil.getString("am.webclient.reports.dns.text"), FormatUtil.getString("JMX Applications"), FormatUtil.getString("JDK1.5"), FormatUtil.getString("Service Monitoring"), FormatUtil.getString("am.webclient.monitortype.adventnetjmxagent.text"), FormatUtil.getString("SNMP"), FormatUtil.getString("am.webclient.hostdiscovery.telnet"), FormatUtil.getString("am.webclient.reports.ftp.text"), FormatUtil.getString("am.webclient.reports.ldap.text"), FormatUtil.getString("Middleware/Portal"), FormatUtil.getString("WebsphereMQ"), FormatUtil.getString("OfficeSharePointServer"), FormatUtil.getString("WebLogic Integration"), FormatUtil.getString("Custom Monitors"), FormatUtil.getString("JMX/SNMP Dashboard"), FormatUtil.getString("Script Monitor"), FormatUtil.getString("Windows Performance Counters"), FormatUtil.getString("SQL Query Monitor"), FormatUtil.getString("am.webclient.monitorgroupsecond.category.virtualserver"), FormatUtil.getString("VMWare ESX/ESXi"), FormatUtil.getString("Virtual Machine"), FormatUtil.getString("am.webclient.monitorgroupsecond.category.cloudapps"), FormatUtil.getString("Amazon"), FormatUtil.getString("EC2Instance"), FormatUtil.getString("RDSInstance") };
/* 6466 */     int length = Constants.resourceTypes_array.length + 1;
/* 6467 */     String[] values_back = new String[length];
/* 6468 */     String[] label_back = new String[length];
/* 6469 */     ArrayList list = new ArrayList();
/* 6470 */     if (Constants.isMinimizedversion())
/*      */     {
/* 6472 */       values_back[0] = "JBOSS-server','Tomcat-server','WEBLOGIC-server','WebSphere-server','ORACLE-APP-server','MYSQL-DB-server','ORACLE-DB-server','MSSQL-DB-server','SYBASE-DB-server','DB2-server','Script Monitor','Apache-server','IIS-server','RMI','MAIL-server','PHP','JMX1.2-MX4J-RMI','JDK1.5','Port-Test','TELNET','SNMP','WEB-server','UrlMonitor','UrlSeq','Custom-Application','Linux','Windows','Windows95','WindowsNT','WindowsNT_Server','Windows 2000','Windows XP','Windows Vista','Windows 2003','Windows 7','Windows 2008','Windows 8','Windows 10','Windows 2012','SUN','SUN PC','Sun Solaris','snmp-node','WLS-Cluster','AIX','AS400/iSeries','HP','FreeBSD / OpenBSD','Mac OS','Exchange-server','WTA','.Net','Web Service','Novell','RBM";
/* 6473 */       label_back[0] = "All";
/* 6474 */       for (int i = 0; i < Constants.resourceTypes_array.length; i++)
/*      */       {
/* 6476 */         values_back[(i + 1)] = Constants.resourceTypes_array[i];
/* 6477 */         int j = 0;
/* 6478 */         for (j = 1; j < label.length; j++)
/*      */         {
/* 6480 */           if (Constants.resourceTypes_array[i].equals(values[j]))
/*      */             break;
/*      */         }
/* 6483 */         if (j < label.length)
/*      */         {
/* 6485 */           label_back[(i + 1)] = label[j];
/*      */         }
/*      */         else
/*      */         {
/* 6489 */           label_back[(i + 1)] = values_back[(i + 1)];
/*      */         }
/*      */       }
/*      */       
/* 6493 */       values = values_back;
/* 6494 */       label = label_back;
/* 6495 */       for (int i = 0; i < values.length; i++)
/*      */       {
/*      */ 
/* 6498 */         Properties p = new Properties();
/* 6499 */         p.setProperty("label", label[i]);
/* 6500 */         p.setProperty("value", values[i]);
/* 6501 */         list.add(p);
/*      */       }
/*      */     }
/*      */     else {
/*      */       try {
/* 6506 */         Iterator it = scheduleRepList.iterator();
/* 6507 */         String infoLabel = "";
/* 6508 */         String infoValue = "";
/* 6509 */         while (it.hasNext()) {
/* 6510 */           Properties serverProps = new Properties();
/* 6511 */           serverProps = (Properties)it.next();
/* 6512 */           Set serverset = serverProps.keySet();
/* 6513 */           Iterator propIt = serverset.iterator();
/* 6514 */           while (propIt.hasNext()) {
/* 6515 */             infoLabel = (String)propIt.next();
/* 6516 */             infoValue = serverProps.getProperty(infoLabel);
/* 6517 */             Properties p = new Properties();
/* 6518 */             if ((!infoLabel.equalsIgnoreCase("Unknown")) && (!infoLabel.equalsIgnoreCase("QEngine Script Monitor"))) {
/* 6519 */               p.setProperty("label", infoLabel);
/* 6520 */               p.setProperty("value", infoValue);
/*      */             }
/* 6522 */             list.add(p);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 6527 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 6531 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getEumResTypes()
/*      */   {
/* 6537 */     ArrayList eumResTypes = new ArrayList();
/*      */     try
/*      */     {
/* 6540 */       eumResTypes = ReportUtil.getEumMonitorTypes();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6544 */       e.printStackTrace();
/*      */     }
/* 6546 */     return eumResTypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDailyhour()
/*      */   {
/* 6554 */     return this.dailyhour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDailyhour(String dailyhour)
/*      */   {
/* 6562 */     this.dailyhour = dailyhour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDailyminute()
/*      */   {
/* 6570 */     return this.dailyminute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDailyminute(String dailyminute)
/*      */   {
/* 6578 */     this.dailyminute = dailyminute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMonday()
/*      */   {
/* 6586 */     return this.monday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMonday(String monday)
/*      */   {
/* 6594 */     this.monday = monday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTuesday()
/*      */   {
/* 6602 */     return this.tuesday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTuesday(String tuesday)
/*      */   {
/* 6610 */     this.tuesday = tuesday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWednesday()
/*      */   {
/* 6618 */     return this.wednesday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWednesday(String wednesday)
/*      */   {
/* 6626 */     this.wednesday = wednesday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThursday()
/*      */   {
/* 6634 */     return this.thursday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThursday(String thursday)
/*      */   {
/* 6642 */     this.thursday = thursday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFriday()
/*      */   {
/* 6650 */     return this.friday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFriday(String friday)
/*      */   {
/* 6658 */     this.friday = friday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSaturday()
/*      */   {
/* 6666 */     return this.saturday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSaturday(String saturday)
/*      */   {
/* 6674 */     this.saturday = saturday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSunday()
/*      */   {
/* 6682 */     return this.sunday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSunday(String sunday)
/*      */   {
/* 6690 */     this.sunday = sunday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getJanuary()
/*      */   {
/* 6698 */     return this.january;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setJanuary(String january)
/*      */   {
/* 6706 */     this.january = january;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFebruary()
/*      */   {
/* 6714 */     return this.february;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFebruary(String february)
/*      */   {
/* 6722 */     this.february = february;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMarch()
/*      */   {
/* 6730 */     return this.march;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMarch(String march)
/*      */   {
/* 6738 */     this.march = march;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getApril()
/*      */   {
/* 6746 */     return this.april;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setApril(String april)
/*      */   {
/* 6754 */     this.april = april;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMay()
/*      */   {
/* 6762 */     return this.may;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMay(String may)
/*      */   {
/* 6770 */     this.may = may;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getJune()
/*      */   {
/* 6778 */     return this.june;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setJune(String june)
/*      */   {
/* 6786 */     this.june = june;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getJuly()
/*      */   {
/* 6794 */     return this.july;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setJuly(String july)
/*      */   {
/* 6802 */     this.july = july;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAugust()
/*      */   {
/* 6810 */     return this.august;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAugust(String august)
/*      */   {
/* 6818 */     this.august = august;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSeptember()
/*      */   {
/* 6826 */     return this.september;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSeptember(String september)
/*      */   {
/* 6834 */     this.september = september;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getOctober()
/*      */   {
/* 6842 */     return this.october;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setOctober(String october)
/*      */   {
/* 6850 */     this.october = october;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getNovember()
/*      */   {
/* 6858 */     return this.november;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setNovember(String november)
/*      */   {
/* 6866 */     this.november = november;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDecember()
/*      */   {
/* 6874 */     return this.december;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDecember(String december)
/*      */   {
/* 6882 */     this.december = december;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSelectday()
/*      */   {
/* 6890 */     return this.selectday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSelectday(String selectday)
/*      */   {
/* 6898 */     this.selectday = selectday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMonthlyday()
/*      */   {
/* 6906 */     return this.monthlyday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMonthlyday(String monthlyday)
/*      */   {
/* 6914 */     this.monthlyday = monthlyday;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getReportmonitor()
/*      */   {
/* 6923 */     return this.reportmonitor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setReportmonitor(String reportmonitor)
/*      */   {
/* 6931 */     this.reportmonitor = reportmonitor;
/*      */   }
/*      */   
/*      */   public String getSlatype() {
/* 6935 */     return this.slatype;
/*      */   }
/*      */   
/*      */   public void setSlatype(String slatype) {
/* 6939 */     this.slatype = slatype;
/*      */   }
/*      */   
/*      */   public String[] getScheduleReportResCombo1()
/*      */   {
/* 6944 */     return this.scheduleReportResCombo1;
/*      */   }
/*      */   
/*      */   public void setScheduleReportResCombo1(String[] scheduleReportResCombo1) {
/* 6948 */     this.scheduleReportResCombo1 = scheduleReportResCombo1;
/*      */   }
/*      */   
/*      */   public String[] getScheduleReportResCombo2()
/*      */   {
/* 6953 */     return this.scheduleReportResCombo2;
/*      */   }
/*      */   
/*      */   public void setScheduleReportResCombo2(String[] scheduleReportResCombo2) {
/* 6957 */     this.scheduleReportResCombo2 = scheduleReportResCombo2;
/*      */   }
/*      */   
/*      */   public String[] getScheduleReportDashboardCombo1()
/*      */   {
/* 6962 */     return this.scheduleReportDashboardCombo1;
/*      */   }
/*      */   
/*      */   public void setScheduleReportDashboardCombo1(String[] scheduleReportDashboardCombo1) {
/* 6966 */     this.scheduleReportDashboardCombo1 = scheduleReportDashboardCombo1;
/*      */   }
/*      */   
/*      */   public String[] getScheduleReportDashboardCombo2()
/*      */   {
/* 6971 */     return this.scheduleReportDashboardCombo2;
/*      */   }
/*      */   
/*      */   public void setScheduleReportDashboardCombo2(String[] scheduleReportDashboardCombo2) {
/* 6975 */     this.scheduleReportDashboardCombo2 = scheduleReportDashboardCombo2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPublishreport()
/*      */   {
/* 6985 */     return this.publishreport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPublishreport(String publishreport)
/*      */   {
/* 6993 */     this.publishreport = publishreport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeofreport()
/*      */   {
/* 7001 */     return this.typeofreport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTypeofreport(String typeofreport)
/*      */   {
/* 7009 */     this.typeofreport = typeofreport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeofperiod()
/*      */   {
/* 7017 */     return this.typeofperiod;
/*      */   }
/*      */   
/*      */   public void setForecastTypeofperiod(String typeofperiod)
/*      */   {
/* 7022 */     this.forecastTypeofperiod = typeofperiod;
/*      */   }
/*      */   
/*      */   public String getForecastTypeofperiod()
/*      */   {
/* 7027 */     return this.forecastTypeofperiod;
/*      */   }
/*      */   
/*      */   public String getTypeofforecastattribute() {
/* 7031 */     return this.typeofforecastattribute;
/*      */   }
/*      */   
/*      */   public void setTypeofforecastattribute(String typeofforecastattribute) {
/* 7035 */     this.typeofforecastattribute = typeofforecastattribute;
/*      */   }
/*      */   
/*      */   public String getTypeofperformance() {
/* 7039 */     return this.typeofperformance;
/*      */   }
/*      */   
/*      */   public void setTypeofperformance(String typeofperformance) {
/* 7043 */     this.typeofperformance = typeofperformance;
/*      */   }
/*      */   
/*      */   public String getTypeofperformanceperiod() {
/* 7047 */     return this.typeofperformanceperiod;
/*      */   }
/*      */   
/*      */   public void setTypeofperformanceperiod(String typeofperformanceperiod) {
/* 7051 */     this.typeofperformanceperiod = typeofperformanceperiod;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSelectedMSSQLResource()
/*      */   {
/* 7057 */     return this.selectedMSSQLResource;
/*      */   }
/*      */   
/*      */   public void setSelectedMSSQLResource(String selectedMSSQLResource) {
/* 7061 */     this.selectedMSSQLResource = selectedMSSQLResource;
/*      */   }
/*      */   
/*      */   public String getSqlDBforPerformance() {
/* 7065 */     return this.sqlDBforPerformance;
/*      */   }
/*      */   
/*      */   public void setSqlDBforPerformance(String sqlDBforPerformance) {
/* 7069 */     this.sqlDBforPerformance = sqlDBforPerformance;
/*      */   }
/*      */   
/*      */   public String getSqlDBPerf()
/*      */   {
/* 7074 */     return this.sqlDBPerf;
/*      */   }
/*      */   
/*      */   public void setSqlDBPerf(String sqlDBPerf) {
/* 7078 */     this.sqlDBPerf = sqlDBPerf;
/*      */   }
/*      */   
/*      */   public void setSqlServerPerf(String sqlServerPerf)
/*      */   {
/* 7083 */     this.sqlServerPerf = sqlServerPerf;
/*      */   }
/*      */   
/*      */   public String getSqlServerPerf() {
/* 7087 */     return this.sqlServerPerf;
/*      */   }
/*      */   
/*      */   public ArrayList getReportPerformance() {
/* 7091 */     String[] values = { "QUERYBYCPU", "QUERYBYIO", "QUERYBYCLR", "QUERYBYSRQ", "QUERYBYMOE", "QUERYBYMOB", "QUERYBYLPR", "QUERYBYCMI", "MEMORY", "WAITSTATS" };
/* 7092 */     String[] label = { FormatUtil.getString("am.webclient.mssql.costlyquerybycpu.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybyclr.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybymob.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.tableheading"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.tableheading"), FormatUtil.getString("am.webclient.mssql.memoryusage.tableheading"), FormatUtil.getString("am.webclient.mssql.waitstats.tableheading") };
/*      */     
/*      */ 
/*      */ 
/* 7096 */     ArrayList list = new ArrayList();
/* 7097 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 7099 */       Properties p = new Properties();
/* 7100 */       p.setProperty("label", label[i]);
/* 7101 */       p.setProperty("value", values[i]);
/* 7102 */       list.add(p);
/*      */     }
/* 7104 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getReportPeriodForPerformance()
/*      */   {
/* 7109 */     String[] values = { "0", "10", "20", "30", "40", "50", "100" };
/* 7110 */     String[] label = { FormatUtil.getString("am.webclient.common.toppolledvalues.text"), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "10" }), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "20" }), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "30" }), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "40" }), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "50" }), FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { "100" }) };
/* 7111 */     ArrayList list = new ArrayList();
/* 7112 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 7114 */       Properties p = new Properties();
/* 7115 */       p.setProperty("label", label[i]);
/* 7116 */       p.setProperty("value", values[i]);
/* 7117 */       list.add(p);
/*      */     }
/* 7119 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTypeofperiod(String typeofperiod)
/*      */   {
/* 7127 */     this.typeofperiod = typeofperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeofattribute()
/*      */   {
/* 7135 */     return this.typeofattribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTypeofattribute(String typeofattribute)
/*      */   {
/* 7143 */     this.typeofattribute = typeofattribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getResourcetype()
/*      */   {
/* 7151 */     return this.resourceType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setResourcetype(String resourceType)
/*      */   {
/* 7159 */     this.resourceType = resourceType;
/*      */   }
/*      */   
/*      */ 
/*      */   private String friday;
/*      */   
/*      */   private String saturday;
/*      */   private String sunday;
/*      */   private String january;
/*      */   private String february;
/*      */   private String march;
/*      */   public String[] getResourcestypes()
/*      */   {
/* 7172 */     return this.resourcestypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setResourcestypes(String[] resourcestypes)
/*      */   {
/* 7180 */     this.resourcestypes = resourcestypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getDays()
/*      */   {
/* 7188 */     return this.days;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDays(String[] days)
/*      */   {
/* 7196 */     this.days = days;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getMonths()
/*      */   {
/* 7204 */     return this.months;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMonths(String[] months)
/*      */   {
/* 7212 */     this.months = months;
/*      */   }
/*      */   
/*      */   public String getShowFeedback() {
/* 7216 */     return this.showFeedback;
/*      */   }
/*      */   
/*      */   public void setShowFeedback(String showFeedback) {
/* 7220 */     this.showFeedback = showFeedback;
/*      */   }
/*      */   
/*      */   public String getAllowManage() {
/* 7224 */     return this.allowManage;
/*      */   }
/*      */   
/*      */   public void setAllowManage(String allowManage) {
/* 7228 */     this.allowManage = allowManage;
/*      */   }
/*      */   
/*      */   public String getAllowExecute() {
/* 7232 */     return this.allowExecute;
/*      */   }
/*      */   
/*      */   public void setAllowExecute(String allowExecute) {
/* 7236 */     this.allowExecute = allowExecute;
/*      */   }
/*      */   
/*      */   public String getCamname()
/*      */   {
/* 7241 */     return this.camname;
/*      */   }
/*      */   
/*      */   public void setCamname(String camname) {
/* 7245 */     this.camname = camname;
/*      */   }
/*      */   
/*      */   public String getCamdesc() {
/* 7249 */     return this.camdesc;
/*      */   }
/*      */   
/*      */   public void setCamdesc(String camdesc) {
/* 7253 */     this.camdesc = camdesc;
/*      */   }
/*      */   
/*      */ 
/*      */   private String april;
/*      */   
/*      */   private String may;
/*      */   
/*      */   private String june;
/*      */   
/*      */   private String july;
/*      */   
/*      */   private String august;
/*      */   
/*      */   private String september;
/*      */   
/*      */   private String october;
/*      */   
/*      */   private String november;
/*      */   
/*      */   private String december;
/*      */   
/*      */   private String monthlyday;
/*      */   
/*      */   private String selectday;
/*      */   private String publishreport;
/* 7279 */   private String reportmonitor = "monitorgroup";
/* 7280 */   private String slatype = "allba";
/* 7281 */   private String[] scheduleReportResCombo1 = new String[0];
/* 7282 */   private String[] scheduleReportResCombo2 = new String[0];
/* 7283 */   private String[] scheduleReportDashboardCombo1 = new String[0];
/* 7284 */   private String[] scheduleReportDashboardCombo2 = new String[0];
/*      */   private String typeofreport;
/*      */   private String typeofperiod;
/*      */   private String forecastTypeofperiod;
/*      */   private String typeoftrendperiod;
/*      */   private String typeofoutageperiod;
/*      */   private String typeofattribute;
/*      */   private String typeofforecastattribute;
/*      */   private String typeofperformance;
/*      */   private String typeofperformanceperiod;
/* 7294 */   private String selectedMSSQLResource = "";
/* 7295 */   private String sqlDBforPerformance = "";
/* 7296 */   private String sqlDBPerf = "";
/* 7297 */   private String sqlServerPerf = "";
/* 7298 */   private String resourceType = "";
/* 7299 */   private String[] resourcestypes = new String[0];
/* 7300 */   private String[] days = new String[0];
/* 7301 */   private String[] workingdays = new String[0];
/* 7302 */   private String[] months = new String[0];
/* 7303 */   private String mondayStartHour = "08";
/* 7304 */   private String mondayEndHour = "20";
/* 7305 */   private String tuesdayStartHour = "08";
/* 7306 */   private String tuesdayEndHour = "20";
/* 7307 */   private String wednesdayStartHour = "08";
/* 7308 */   private String wednesdayEndHour = "20";
/* 7309 */   private String thursdayStartHour = "08";
/* 7310 */   private String thursdayEndHour = "20";
/* 7311 */   private String fridayStartHour = "08";
/* 7312 */   private String fridayEndHour = "20";
/*      */   private String saturdayStartHour;
/*      */   private String saturdayEndHour;
/*      */   private String sundayStartHour;
/*      */   private String sundayEndHour;
/* 7317 */   private String mondayStartMinute = "0";
/* 7318 */   private String mondayEndMinute = "0";
/*      */   
/* 7320 */   private String tuesdayStartMinute = "0";
/* 7321 */   private String tuesdayEndMinute = "0";
/*      */   
/* 7323 */   private String wednesdayStartMinute = "0";
/* 7324 */   private String wednesdayEndMinute = "0";
/*      */   
/* 7326 */   private String thursdayStartMinute = "0";
/* 7327 */   private String thursdayEndMinute = "0";
/*      */   
/* 7329 */   private String fridayStartMinute = "0";
/* 7330 */   private String fridayEndMinute = "0";
/*      */   
/* 7332 */   private String saturdayStartMinute = "0";
/* 7333 */   private String saturdayEndMinute = "0";
/*      */   
/* 7335 */   private String sundayStartMinute = "0";
/* 7336 */   private String sundayEndMinute = "0";
/*      */   
/* 7338 */   private String forecastTrend = "1";
/*      */   
/*      */   public boolean isStringChanged() {
/* 7341 */     return this.stringChanged;
/*      */   }
/*      */   
/*      */   public void setStringChanged(boolean stringChanged) {
/* 7345 */     this.stringChanged = stringChanged;
/*      */   }
/*      */   
/*      */   public boolean isNumericChanged() {
/* 7349 */     return this.numericChanged;
/*      */   }
/*      */   
/*      */   public void setForecastTrend(String trend) {
/* 7353 */     this.forecastTrend = trend;
/*      */   }
/*      */   
/*      */   public String getForecastTrend() {
/* 7357 */     return this.forecastTrend;
/*      */   }
/*      */   
/*      */   public void setNumericChanged(boolean numericChanged) {
/* 7361 */     this.numericChanged = numericChanged;
/*      */   }
/*      */   
/*      */   public String getMonitoringmode() {
/* 7365 */     return this.monitoringmode;
/*      */   }
/*      */   
/*      */   public void setMonitoringmode(String monitoringmode) {
/* 7369 */     this.monitoringmode = monitoringmode;
/*      */   }
/*      */   
/*      */   public String getPrompt() {
/* 7373 */     return this.prompt;
/*      */   }
/*      */   
/*      */   public void setPrompt(String prompt) {
/* 7377 */     this.prompt = prompt;
/*      */   }
/*      */   
/*      */   public String getChoosehost() {
/* 7381 */     return this.choosehost;
/*      */   }
/*      */   
/*      */   public void setChoosehost(String choosehost) {
/* 7385 */     this.choosehost = choosehost;
/*      */   }
/*      */   
/*      */   public String getMtype() {
/* 7389 */     return this.mtype;
/*      */   }
/*      */   
/*      */   public void setMtype(String mtype) {
/* 7393 */     this.mtype = mtype;
/*      */   }
/*      */   
/* 7396 */   public String getAddmaintenance() { return this.addmaintenance; }
/*      */   
/*      */   public void setAddmaintenance(String addmaintenance)
/*      */   {
/* 7400 */     this.addmaintenance = addmaintenance;
/*      */   }
/*      */   
/* 7403 */   public String getFiles() { return this.files; }
/*      */   
/*      */   public void setFiles(String files)
/*      */   {
/* 7407 */     this.files = files;
/*      */   }
/*      */   
/*      */   public String getFilepath() {
/* 7411 */     return this.filepath;
/*      */   }
/*      */   
/*      */   public void setFilepath(String filepath) {
/* 7415 */     if (filepath.endsWith("\\")) {
/* 7416 */       filepath = filepath.substring(0, filepath.lastIndexOf("\\"));
/* 7417 */       if (filepath.endsWith(":")) {
/* 7418 */         filepath = filepath + "\\";
/*      */       }
/*      */     }
/* 7421 */     this.filepath = filepath;
/*      */   }
/*      */   
/*      */   public String getFilename() {
/* 7425 */     return this.filename;
/*      */   }
/*      */   
/*      */   public void setFilename(String filename) {
/* 7429 */     this.filename = filename;
/*      */   }
/*      */   
/* 7432 */   private boolean isCredManager = false;
/*      */   private String credentialID;
/*      */   
/* 7435 */   public boolean getIsCredManager() { return this.isCredManager; }
/*      */   
/*      */   private String contentChk;
/* 7438 */   public void setIsCredManager(boolean isCredManager) { this.isCredManager = isCredManager; }
/*      */   
/*      */ 
/*      */   private String regexChk;
/*      */   private int statusType;
/*      */   private String ruleType;
/*      */   public void setCredentialID(String credentialID) {
/* 7445 */     this.credentialID = credentialID;
/*      */   }
/*      */   
/*      */   public String getCredentialID() {
/* 7449 */     return this.credentialID;
/*      */   }
/*      */   
/*      */   public String getCcontent() {
/* 7453 */     return this.content;
/*      */   }
/*      */   
/*      */ 
/* 7457 */   public void setCcontent(String content) { this.content = content; }
/*      */   
/*      */   private String countval;
/*      */   private int fchecktype;
/*      */   private String checkEmpty;
/*      */   private String fileDirAge;
/*      */   private int monstatus;
/*      */   private int changeType;
/* 7465 */   public void setContentChk(String contentChk) { this.contentChk = contentChk; }
/*      */   
/*      */ 
/*      */   public String getContentChk() {
/* 7469 */     return this.contentChk;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setRegexChk(String regexChk)
/*      */   {
/* 7475 */     this.regexChk = regexChk;
/*      */   }
/*      */   
/*      */   public String getRegexChk() {
/* 7479 */     return this.regexChk;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSelectStatusType(int statusType)
/*      */   {
/* 7485 */     this.statusType = statusType;
/*      */   }
/*      */   
/*      */   public int getSelectStatusType() {
/* 7489 */     return this.statusType;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSelectRuleType(String ruleType)
/*      */   {
/* 7495 */     this.ruleType = ruleType;
/*      */   }
/*      */   
/*      */   public String getSelectRuleType() {
/* 7499 */     return this.ruleType;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setCountval(String countval)
/*      */   {
/* 7505 */     this.countval = countval;
/*      */   }
/*      */   
/*      */   public String getCountval() {
/* 7509 */     return this.countval;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setFileCheckType(int fchecktype)
/*      */   {
/* 7515 */     this.fchecktype = fchecktype;
/*      */   }
/*      */   
/*      */   public int getFileCheckType() {
/* 7519 */     return this.fchecktype;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setCheckEmpty(String checkEmpty)
/*      */   {
/* 7525 */     this.checkEmpty = checkEmpty;
/*      */   }
/*      */   
/*      */   public String getCheckEmpty() {
/* 7529 */     return this.checkEmpty;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setFileDirAge(String fileDirAge)
/*      */   {
/* 7535 */     this.fileDirAge = fileDirAge;
/*      */   }
/*      */   
/*      */   public String getFileDirAge() {
/* 7539 */     return this.fileDirAge;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSelectMonStatus(int monstatus)
/*      */   {
/* 7545 */     this.monstatus = monstatus;
/*      */   }
/*      */   
/*      */   public int getSelectMonStatus() {
/* 7549 */     return this.monstatus;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSelectChangeType(int changeType)
/*      */   {
/* 7555 */     this.changeType = changeType;
/*      */   }
/*      */   
/*      */   public int getSelectChangeType() {
/* 7559 */     return this.changeType;
/*      */   }
/*      */   
/* 7562 */   private int timeval = 0;
/*      */   private String timeUnit;
/*      */   
/* 7565 */   public void setTimeval(int timeval) { this.timeval = timeval; }
/*      */   
/*      */   public int getTimeval()
/*      */   {
/* 7569 */     return this.timeval;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setTimeUnit(String timeUnit)
/*      */   {
/* 7575 */     this.timeUnit = timeUnit;
/*      */   }
/*      */   
/*      */   public String getTimeUnit() {
/* 7579 */     return this.timeUnit;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setSubDirCntChk(String subDirCntChk)
/*      */   {
/* 7585 */     this.subDirCntChk = subDirCntChk;
/*      */   }
/*      */   
/*      */   public String getSubDirCntChk() {
/* 7589 */     return this.subDirCntChk;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getServersite()
/*      */   {
/* 7595 */     return this.serversite;
/*      */   }
/*      */   
/*      */   public void setServersite(String serversite) {
/* 7599 */     this.serversite = serversite;
/*      */   }
/*      */   
/*      */   public void setMontype(String mtype)
/*      */   {
/* 7604 */     this.montype = mtype;
/*      */   }
/*      */   
/*      */   public String getMontype() {
/* 7608 */     return this.montype;
/*      */   }
/*      */   
/*      */   public void setMgTemplateType(String montype) {
/* 7612 */     this.mgTemplateType = montype;
/*      */   }
/*      */   
/*      */   public String getMgTemplateType() {
/* 7616 */     return this.mgTemplateType;
/*      */   }
/*      */   
/*      */   public String getStype() {
/* 7620 */     return this.stype;
/*      */   }
/*      */   
/*      */   public void setStype(String stype) {
/* 7624 */     this.stype = stype;
/*      */   }
/*      */   
/*      */   public boolean isOpfile()
/*      */   {
/* 7629 */     return this.opfile;
/*      */   }
/*      */   
/*      */   public void setOpfile(boolean opfile) {
/* 7633 */     this.opfile = opfile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSeventhirtyMA()
/*      */   {
/* 7641 */     return this.seventhirtyMA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSeventhirtyMA(String seventhirtyMA)
/*      */   {
/* 7649 */     this.seventhirtyMA = seventhirtyMA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHourlyMA()
/*      */   {
/* 7657 */     return this.hourlyMA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setHourlyMA(String HourlyMA)
/*      */   {
/* 7665 */     this.hourlyMA = HourlyMA;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getEnableServerSnapshot()
/*      */   {
/* 7673 */     return this.enableServerSnapshot;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEnableServerSnapshot(String enableServerSnapshot)
/*      */   {
/* 7681 */     this.enableServerSnapshot = enableServerSnapshot;
/*      */   }
/*      */   
/*      */   public String getEnableConsolidatedMail() {
/* 7685 */     return this.enableConsolidatedMail;
/*      */   }
/*      */   
/*      */   public void setEnableConsolidatedMail(String enableConsolidatedMail) {
/* 7689 */     this.enableConsolidatedMail = enableConsolidatedMail;
/*      */   }
/*      */   
/*      */   public String getEnableErrorMail()
/*      */   {
/* 7694 */     return this.enableErrorMail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setEnableErrorMail(String enableErrorMail)
/*      */   {
/* 7701 */     this.enableErrorMail = enableErrorMail;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getErrorpollCount()
/*      */   {
/* 7707 */     return this.errorpollCount;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setErrorpollCount(String errorpollCount)
/*      */   {
/* 7714 */     this.errorpollCount = errorpollCount;
/*      */   }
/*      */   
/*      */   public boolean isApacheauth()
/*      */   {
/* 7719 */     return this.apacheauth;
/*      */   }
/*      */   
/*      */   public void setApacheauth(boolean apacheauth) {
/* 7723 */     this.apacheauth = apacheauth;
/*      */   }
/*      */   
/*      */   public String getApacheUserName() {
/* 7727 */     return this.apacheUserName;
/*      */   }
/*      */   
/*      */   public void setApacheUserName(String apacheUserName) {
/* 7731 */     this.apacheUserName = apacheUserName;
/*      */   }
/*      */   
/*      */   public String getApachepassword() {
/* 7735 */     return this.apachepassword;
/*      */   }
/*      */   
/*      */   public void setApachepassword(String apachepassword) {
/* 7739 */     this.apachepassword = apachepassword;
/*      */   }
/*      */   
/*      */   public boolean isServerstatusurl() {
/* 7743 */     return this.serverstatusurl;
/*      */   }
/*      */   
/*      */   public void setServerstatusurl(boolean serverstatusurl) {
/* 7747 */     this.serverstatusurl = serverstatusurl;
/*      */   }
/*      */   
/* 7750 */   public boolean isSshkey() { return this.sshkey; }
/*      */   
/*      */   public void setSshkey(boolean sshkey)
/*      */   {
/* 7754 */     this.sshkey = sshkey;
/*      */   }
/*      */   
/*      */   public String getApacheurl() {
/* 7758 */     return this.apacheurl;
/*      */   }
/*      */   
/* 7761 */   public void setApacheurl(String apacheurl) { this.apacheurl = apacheurl; }
/*      */   
/*      */   public String getTomcatmanagerurl() {
/* 7764 */     return this.tomcatmanagerurl;
/*      */   }
/*      */   
/* 7767 */   public void setTomcatmanagerurl(String tomcatmanagerurl) { this.tomcatmanagerurl = tomcatmanagerurl; }
/*      */   
/*      */   public String getWSDLUrl() {
/* 7770 */     return this.WSDLUrl;
/*      */   }
/*      */   
/*      */   public void setWSDLUrl(String WSDLUrl) {
/* 7774 */     this.WSDLUrl = WSDLUrl;
/*      */   }
/*      */   
/*      */   public boolean isProxyRequired() {
/* 7778 */     return this.proxyRequired;
/*      */   }
/*      */   
/*      */   public void setProxyRequired(boolean needsProxy) {
/* 7782 */     this.proxyRequired = needsProxy;
/*      */   }
/*      */   
/*      */   public void setDateformat(String dateformat)
/*      */   {
/* 7787 */     this.dateformat = dateformat;
/*      */   }
/*      */   
/*      */   public String getDateformat()
/*      */   {
/* 7792 */     return this.dateformat;
/*      */   }
/*      */   
/*      */   public void setUsedRouterString(boolean usedRouterString) {
/* 7796 */     this.usedRouterString = usedRouterString;
/*      */   }
/*      */   
/* 7799 */   public boolean getUsedRouterString() { return this.usedRouterString; }
/*      */   
/*      */   public void setRouterString(String routerString) {
/* 7802 */     this.routerString = routerString;
/*      */   }
/*      */   
/* 7805 */   public String getRouterString() { return this.routerString; }
/*      */   
/*      */   public void setLogonClient(String logonClient) {
/* 7808 */     this.logonClient = logonClient;
/*      */   }
/*      */   
/*      */   public String getLogonClient() {
/* 7812 */     return this.logonClient;
/*      */   }
/*      */   
/*      */   public void setLanguage(String language) {
/* 7816 */     this.language = language;
/*      */   }
/*      */   
/*      */   public String getLanguage() {
/* 7820 */     return this.language;
/*      */   }
/*      */   
/*      */   public void setSystemNumber(String systemNumber) {
/* 7824 */     this.systemNumber = systemNumber;
/*      */   }
/*      */   
/*      */   public String getSystemNumber() {
/* 7828 */     return this.systemNumber;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeofoutageperiod()
/*      */   {
/* 7836 */     return this.typeofoutageperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTypeofoutageperiod(String typeofoutageperiod)
/*      */   {
/* 7844 */     this.typeofoutageperiod = typeofoutageperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeoftrendperiod()
/*      */   {
/* 7852 */     return this.typeoftrendperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTypeoftrendperiod(String typeoftrendperiod)
/*      */   {
/* 7860 */     this.typeoftrendperiod = typeoftrendperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getStatusRule()
/*      */   {
/* 7868 */     return this.statusRule;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStatusRule(String statusRule)
/*      */   {
/* 7876 */     this.statusRule = statusRule;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getWorkingdays()
/*      */   {
/* 7884 */     return this.workingdays;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWorkingdays(String[] workingdays)
/*      */   {
/* 7892 */     this.workingdays = workingdays;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMondayStartHour()
/*      */   {
/* 7900 */     return this.mondayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMondayStartHour(String mondayStartHour)
/*      */   {
/* 7908 */     this.mondayStartHour = mondayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMondayEndHour()
/*      */   {
/* 7916 */     return this.mondayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMondayEndHour(String mondayEndHour)
/*      */   {
/* 7924 */     this.mondayEndHour = mondayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */   private String subDirCntChk;
/*      */   
/*      */   private ArrayList businessrules;
/*      */   public String getTuesdayStartHour()
/*      */   {
/* 7933 */     return this.tuesdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTuesdayStartHour(String tuesdayStartHour)
/*      */   {
/* 7942 */     this.tuesdayStartHour = tuesdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTuesdayEndHour()
/*      */   {
/* 7950 */     return this.tuesdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTuesdayEndHour(String tuesdayEndHour)
/*      */   {
/* 7958 */     this.tuesdayEndHour = tuesdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWednesdayStartHour()
/*      */   {
/* 7966 */     return this.wednesdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWednesdayStartHour(String wednesdayStartHour)
/*      */   {
/* 7974 */     this.wednesdayStartHour = wednesdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWednesdayEndHour()
/*      */   {
/* 7982 */     return this.wednesdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWednesdayEndHour(String wednesdayEndHour)
/*      */   {
/* 7990 */     this.wednesdayEndHour = wednesdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThursdayStartHour()
/*      */   {
/* 7998 */     return this.thursdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThursdayStartHour(String thursdayStartHour)
/*      */   {
/* 8006 */     this.thursdayStartHour = thursdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThursdayEndHour()
/*      */   {
/* 8014 */     return this.thursdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThursdayEndHour(String thursdayEndHour)
/*      */   {
/* 8022 */     this.thursdayEndHour = thursdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFridayStartHour()
/*      */   {
/* 8030 */     return this.fridayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFridayStartHour(String fridayStartHour)
/*      */   {
/* 8038 */     this.fridayStartHour = fridayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFridayEndHour()
/*      */   {
/* 8046 */     return this.fridayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFridayEndHour(String fridayEndHour)
/*      */   {
/* 8054 */     this.fridayEndHour = fridayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSaturdayStartHour()
/*      */   {
/* 8062 */     return this.saturdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSaturdayStartHour(String saturdayStartHour)
/*      */   {
/* 8070 */     this.saturdayStartHour = saturdayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSaturdayEndHour()
/*      */   {
/* 8078 */     return this.saturdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSaturdayEndHour(String saturdayEndHour)
/*      */   {
/* 8086 */     this.saturdayEndHour = saturdayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSundayStartHour()
/*      */   {
/* 8094 */     return this.sundayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSundayStartHour(String sundayStartHour)
/*      */   {
/* 8102 */     this.sundayStartHour = sundayStartHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSundayEndHour()
/*      */   {
/* 8110 */     return this.sundayEndHour;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSundayEndHour(String sundayEndHour)
/*      */   {
/* 8118 */     this.sundayEndHour = sundayEndHour;
/*      */   }
/*      */   
/*      */   public void setBusinessrules(ArrayList app)
/*      */   {
/* 8123 */     this.businessrules = app;
/*      */   }
/*      */   
/*      */   public ArrayList getBusinessrules() {
/* 8127 */     return this.businessrules;
/*      */   }
/*      */   
/* 8130 */   private ArrayList businessRuleNames = null;
/*      */   
/* 8132 */   public ArrayList getBusinessRuleNames() { ArrayList table = new ArrayList();
/* 8133 */     ArrayList list = getBusinessrules();
/* 8134 */     Properties dataProps1 = new Properties();
/* 8135 */     dataProps1.setProperty("label", "-----" + FormatUtil.getString("am.schreporting.select.brule.default") + "------");
/* 8136 */     dataProps1.setProperty("value", "oni");
/* 8137 */     table.add(dataProps1);
/* 8138 */     for (int i = 0; i < list.size(); i++) {
/* 8139 */       Properties props = (Properties)list.get(i);
/* 8140 */       table.add(props);
/*      */     }
/*      */     
/*      */ 
/* 8144 */     return table;
/*      */   }
/*      */   
/*      */ 
/* 8148 */   public void setBusinessRuleNames(ArrayList t) { this.businessRuleNames = t; }
/*      */   
/* 8150 */   private String businessperiod = "oni";
/*      */   
/*      */   public void setBusinessPeriod(String businessperiod) {
/* 8153 */     this.businessperiod = businessperiod;
/*      */   }
/*      */   
/*      */   public String getBusinessPeriod() {
/* 8157 */     return this.businessperiod;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getReportDelivery()
/*      */   {
/* 8165 */     return this.reportDelivery;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setReportDelivery(String reportDelivery)
/*      */   {
/* 8173 */     this.reportDelivery = reportDelivery;
/*      */   }
/*      */   
/*      */ 
/* 8177 */   private ArrayList resourceTypeNamesForReports = null;
/*      */   
/* 8179 */   public ArrayList getResourceTypeNamesForReports() { ArrayList table = new ArrayList();
/* 8180 */     ArrayList list = getResourceTypeForReports();
/*      */     
/* 8182 */     for (int i = 0; i < list.size(); i++) {
/* 8183 */       Properties props = (Properties)list.get(i);
/* 8184 */       props.setProperty("label", FormatUtil.getString(props.getProperty("label")));
/* 8185 */       table.add(props);
/*      */     }
/*      */     
/*      */ 
/* 8189 */     return table;
/*      */   }
/*      */   
/*      */   public void setResourceTypeNamesForReports(ArrayList t)
/*      */   {
/* 8194 */     this.resourceTypeNamesForReports = t;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setResourceTypeForReports(ArrayList app)
/*      */   {
/* 8200 */     this.resourceTypeForReports = app;
/*      */   }
/*      */   
/*      */   public ArrayList getResourceTypeForReports() {
/* 8204 */     return this.resourceTypeForReports;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getResTypeValue()
/*      */   {
/* 8212 */     return this.resTypeValue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setResTypeValue(String resTypeValue)
/*      */   {
/* 8220 */     this.resTypeValue = resTypeValue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMondayStartMinute()
/*      */   {
/* 8228 */     return this.mondayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMondayStartMinute(String mondayStartMinute)
/*      */   {
/* 8236 */     this.mondayStartMinute = mondayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMondayEndMinute()
/*      */   {
/* 8244 */     return this.mondayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setMondayEndMinute(String mondayEndMinute)
/*      */   {
/* 8252 */     this.mondayEndMinute = mondayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTuesdayStartMinute()
/*      */   {
/* 8260 */     return this.tuesdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTuesdayStartMinute(String tuesdayStartMinute)
/*      */   {
/* 8268 */     this.tuesdayStartMinute = tuesdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTuesdayEndMinute()
/*      */   {
/* 8276 */     return this.tuesdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTuesdayEndMinute(String tuesdayEndMinute)
/*      */   {
/* 8284 */     this.tuesdayEndMinute = tuesdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ArrayList resourceTypeForReports;
/*      */   
/*      */   public String getThursdayStartMinute()
/*      */   {
/* 8293 */     return this.thursdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThursdayStartMinute(String thursdayStartMinute)
/*      */   {
/* 8301 */     this.thursdayStartMinute = thursdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getThursdayEndMinute()
/*      */   {
/* 8309 */     return this.thursdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setThursdayEndMinute(String thursdayEndMinute)
/*      */   {
/* 8317 */     this.thursdayEndMinute = thursdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFridayStartMinute()
/*      */   {
/* 8325 */     return this.fridayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFridayStartMinute(String fridayStartMinute)
/*      */   {
/* 8333 */     this.fridayStartMinute = fridayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFridayEndMinute()
/*      */   {
/* 8341 */     return this.fridayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFridayEndMinute(String fridayEndMinute)
/*      */   {
/* 8349 */     this.fridayEndMinute = fridayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSaturdayStartMinute()
/*      */   {
/* 8357 */     return this.saturdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSaturdayStartMinute(String saturdayStartMinute)
/*      */   {
/* 8365 */     this.saturdayStartMinute = saturdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSaturdayEndMinute()
/*      */   {
/* 8373 */     return this.saturdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSaturdayEndMinute(String saturdayEndMinute)
/*      */   {
/* 8381 */     this.saturdayEndMinute = saturdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSundayStartMinute()
/*      */   {
/* 8389 */     return this.sundayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSundayStartMinute(String sundayStartMinute)
/*      */   {
/* 8397 */     this.sundayStartMinute = sundayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSundayEndMinute()
/*      */   {
/* 8405 */     return this.sundayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSundayEndMinute(String sundayEndMinute)
/*      */   {
/* 8413 */     this.sundayEndMinute = sundayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWednesdayStartMinute()
/*      */   {
/* 8421 */     return this.wednesdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWednesdayStartMinute(String wednesdayStartMinute)
/*      */   {
/* 8429 */     this.wednesdayStartMinute = wednesdayStartMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWednesdayEndMinute()
/*      */   {
/* 8437 */     return this.wednesdayEndMinute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWednesdayEndMinute(String wednesdayEndMinute)
/*      */   {
/* 8445 */     this.wednesdayEndMinute = wednesdayEndMinute;
/*      */   }
/*      */   
/* 8448 */   private String resTypeValue = "zxy";
/*      */   
/* 8450 */   public String getPredictioncondition() { return this.predictioncondition; }
/*      */   
/*      */ 
/*      */ 
/* 8454 */   public void setPredictioncondition(String predictioncondition) { this.predictioncondition = predictioncondition; }
/*      */   
/* 8456 */   private String anomalyName = "";
/* 8457 */   private String alarmType = "1";
/* 8458 */   private String alarmTypeExpression = "1";
/* 8459 */   private String loweralarmType = "4";
/* 8460 */   private String loweralarmTypeExpression = "4";
/* 8461 */   private String higherValue = "20";
/* 8462 */   private String lowerValue = "30";
/* 8463 */   private String higherPercentage = "1";
/* 8464 */   private String lowerPercentage = "1";
/* 8465 */   private String comparisonType = "1";
/* 8466 */   private String baseWeek = "";
/* 8467 */   private String monthYears = "";
/* 8468 */   private String anomalyId = "";
/* 8469 */   private String baselineType = "1";
/* 8470 */   private ArrayList yearsList = new ArrayList();
/* 8471 */   private String baseformulaType = "1";
/* 8472 */   private String leftexp1 = "$LastHourValue";
/* 8473 */   private String leftexp2 = "$LastHourValue";
/* 8474 */   private String leftselect = "";
/* 8475 */   private String rightexp1 = FormatUtil.getString("am.webclient.anomaly.expression.sampleexp.text");
/* 8476 */   private String rightexp2 = FormatUtil.getString("am.webclient.anomaly.expression.sampleexp1.text");
/* 8477 */   private String rightselect = "";
/*      */   
/*      */   public String getAnomalyName()
/*      */   {
/* 8481 */     setYearsList();
/* 8482 */     return this.anomalyName;
/*      */   }
/*      */   
/*      */   public void setAnomalyName(String anomalyName) {
/* 8486 */     this.anomalyName = anomalyName;
/*      */   }
/*      */   
/*      */   public ArrayList getAnomalySeverity() {
/* 8490 */     String[] values = { "1", "4" };
/* 8491 */     String[] label = { FormatUtil.getString("Critical"), FormatUtil.getString("Warning") };
/* 8492 */     ArrayList list = new ArrayList();
/* 8493 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 8495 */       Properties p = new Properties();
/* 8496 */       p.setProperty("label", label[i]);
/* 8497 */       p.setProperty("value", values[i]);
/* 8498 */       list.add(p);
/*      */     }
/* 8500 */     return list;
/*      */   }
/*      */   
/*      */   public String getAlarmType() {
/* 8504 */     return this.alarmType;
/*      */   }
/*      */   
/*      */   public void setAlarmType(String alarmType) {
/* 8508 */     this.alarmType = alarmType;
/*      */   }
/*      */   
/*      */   public String getLoweralarmType() {
/* 8512 */     return this.loweralarmType;
/*      */   }
/*      */   
/*      */   public void setLoweralarmType(String loweralarmType) {
/* 8516 */     this.loweralarmType = loweralarmType;
/*      */   }
/*      */   
/*      */   public String getHigherValue() {
/* 8520 */     return this.higherValue;
/*      */   }
/*      */   
/*      */   public void setHigherValue(String higherValue) {
/* 8524 */     this.higherValue = higherValue;
/*      */   }
/*      */   
/*      */   public String getLowerValue() {
/* 8528 */     return this.lowerValue;
/*      */   }
/*      */   
/*      */   public void setLowerValue(String lowerValue) {
/* 8532 */     this.lowerValue = lowerValue;
/*      */   }
/*      */   
/*      */   public String getHigherPercentage() {
/* 8536 */     return this.higherPercentage;
/*      */   }
/*      */   
/*      */   public void setHigherPercentage(String higherPercentage) {
/* 8540 */     this.higherPercentage = higherPercentage;
/*      */   }
/*      */   
/*      */   public String getLowerPercentage() {
/* 8544 */     return this.lowerPercentage;
/*      */   }
/*      */   
/*      */   public void setLowerPercentage(String lowerPercentage) {
/* 8548 */     this.lowerPercentage = lowerPercentage;
/*      */   }
/*      */   
/*      */   public String getComparisonType() {
/* 8552 */     return this.comparisonType;
/*      */   }
/*      */   
/*      */   public void setComparisonType(String comparisonType) {
/* 8556 */     this.comparisonType = comparisonType;
/*      */   }
/*      */   
/*      */   public String getBaseWeek() {
/* 8560 */     return this.baseWeek;
/*      */   }
/*      */   
/*      */   public void setBaseWeek(String baseWeek) {
/* 8564 */     this.baseWeek = baseWeek;
/*      */   }
/*      */   
/*      */   public ArrayList getWeekValues() {
/* 8568 */     String[] values = { "1", "2", "3", "4" };
/* 8569 */     String[] label = { FormatUtil.getString("am.webclient.anomaly.week1.text"), FormatUtil.getString("am.webclient.anomaly.week2.text"), FormatUtil.getString("am.webclient.anomaly.week3.text"), FormatUtil.getString("am.webclient.anomaly.week4.text") };
/* 8570 */     ArrayList list = new ArrayList();
/* 8571 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 8573 */       Properties p = new Properties();
/* 8574 */       p.setProperty("label", label[i]);
/* 8575 */       p.setProperty("value", values[i]);
/* 8576 */       list.add(p);
/*      */     }
/* 8578 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getCompareValues() {
/* 8582 */     String[] values = { ">", "<", ">=", "<=", "==", "!=" };
/* 8583 */     String[] label = { FormatUtil.getString(">"), FormatUtil.getString("<"), FormatUtil.getString(">="), FormatUtil.getString("<="), FormatUtil.getString("="), FormatUtil.getString("!=") };
/* 8584 */     ArrayList list = new ArrayList();
/* 8585 */     for (int i = 0; i < values.length; i++)
/*      */     {
/* 8587 */       Properties p = new Properties();
/* 8588 */       p.setProperty("label", label[i]);
/* 8589 */       p.setProperty("value", values[i]);
/* 8590 */       list.add(p);
/*      */     }
/* 8592 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getEmailActions() {
/* 8596 */     return ReportDataUtilities.getActionsList();
/*      */   }
/*      */   
/*      */   public void setYearsList() {
/* 8600 */     this.yearsList = ReportDataUtilities.getYearLists();
/* 8601 */     if ((this.yearsList == null) || (this.yearsList.isEmpty()))
/* 8602 */       this.baselineType = "0";
/*      */   }
/*      */   
/*      */   public ArrayList getYearsList() {
/* 8606 */     return this.yearsList;
/*      */   }
/*      */   
/*      */   public String getMonthYears() {
/* 8610 */     return this.monthYears;
/*      */   }
/*      */   
/*      */   public void setMonthYears(String monthYears) {
/* 8614 */     this.monthYears = monthYears;
/*      */   }
/*      */   
/*      */   public String getAnomalyId() {
/* 8618 */     return this.anomalyId;
/*      */   }
/*      */   
/*      */   public void setAnomalyId(String anomalyId) {
/* 8622 */     this.anomalyId = anomalyId;
/*      */   }
/*      */   
/* 8625 */   public String getBaselineType() { return this.baselineType; }
/*      */   
/*      */   public void setBaselineType(String baselineType)
/*      */   {
/* 8629 */     this.baselineType = baselineType;
/*      */   }
/*      */   
/*      */   public String getDeleteexistinganomaly() {
/* 8633 */     return this.deleteexistinganomaly;
/*      */   }
/*      */   
/*      */   public void setDeleteexistinganomaly(String deleteexistinganomaly) {
/* 8637 */     this.deleteexistinganomaly = deleteexistinganomaly;
/*      */   }
/*      */   
/*      */   public String getBaseformulaType() {
/* 8641 */     return this.baseformulaType;
/*      */   }
/*      */   
/*      */   public void setBaseformulaType(String baseformulaType) {
/* 8645 */     this.baseformulaType = baseformulaType;
/*      */   }
/*      */   
/*      */   public String getLeftexp1() {
/* 8649 */     return this.leftexp1;
/*      */   }
/*      */   
/*      */   public void setLeftexp1(String leftexp1) {
/* 8653 */     this.leftexp1 = leftexp1;
/*      */   }
/*      */   
/*      */   public String getLeftexp2() {
/* 8657 */     return this.leftexp2;
/*      */   }
/*      */   
/*      */   public void setLeftexp2(String leftexp2) {
/* 8661 */     this.leftexp2 = leftexp2;
/*      */   }
/*      */   
/*      */   public String getLeftselect() {
/* 8665 */     return this.leftselect;
/*      */   }
/*      */   
/*      */   public void setLeftselect(String leftselect) {
/* 8669 */     this.leftselect = leftselect;
/*      */   }
/*      */   
/*      */   public String getRightexp1() {
/* 8673 */     return this.rightexp1;
/*      */   }
/*      */   
/*      */   public void setRightexp1(String rightexp1) {
/* 8677 */     this.rightexp1 = rightexp1;
/*      */   }
/*      */   
/*      */   public String getRightexp2() {
/* 8681 */     return this.rightexp2;
/*      */   }
/*      */   
/*      */   public void setRightexp2(String rightexp2) {
/* 8685 */     this.rightexp2 = rightexp2;
/*      */   }
/*      */   
/*      */   public String getRightselect() {
/* 8689 */     return this.rightselect;
/*      */   }
/*      */   
/*      */   public void setRightselect(String rightselect) {
/* 8693 */     this.rightselect = rightselect;
/*      */   }
/*      */   
/*      */   public String getAlarmTypeExpression() {
/* 8697 */     return this.alarmTypeExpression;
/*      */   }
/*      */   
/*      */   public void setAlarmTypeExpression(String alarmTypeExpression) {
/* 8701 */     this.alarmTypeExpression = alarmTypeExpression;
/*      */   }
/*      */   
/*      */   public String getLoweralarmTypeExpression() {
/* 8705 */     return this.loweralarmTypeExpression;
/*      */   }
/*      */   
/*      */   public void setLoweralarmTypeExpression(String loweralarmTypeExpression) {
/* 8709 */     this.loweralarmTypeExpression = loweralarmTypeExpression;
/*      */   }
/*      */   
/*      */   public void setBusinessHoursFromJSONString(String jsonStr) throws JSONException
/*      */   {
/* 8714 */     JSONObject obj = new JSONObject(jsonStr);
/* 8715 */     this.rulename = ((String)obj.get("name"));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 8722 */     String selectedDays = (String)obj.get("selectedDays");
/* 8723 */     this.workingdays = selectedDays.split(",");
/* 8724 */     this.mondayStartHour = ((String)obj.get("mondayStartHour"));
/* 8725 */     if (Integer.parseInt(this.mondayStartHour) < 10)
/*      */     {
/* 8727 */       this.mondayStartHour = ("0" + this.mondayStartHour);
/*      */     }
/* 8729 */     this.mondayEndHour = ((String)obj.get("mondayEndHour"));
/* 8730 */     if (Integer.parseInt(this.mondayEndHour) < 10)
/*      */     {
/* 8732 */       this.mondayEndHour = ("0" + this.mondayEndHour);
/*      */     }
/* 8734 */     this.tuesdayStartHour = ((String)obj.get("tuesdayStartHour"));
/* 8735 */     if (Integer.parseInt(this.tuesdayStartHour) < 10)
/*      */     {
/* 8737 */       this.tuesdayStartHour = ("0" + this.tuesdayStartHour);
/*      */     }
/* 8739 */     this.tuesdayEndHour = ((String)obj.get("tuesdayEndHour"));
/* 8740 */     if (Integer.parseInt(this.tuesdayEndHour) < 10)
/*      */     {
/* 8742 */       this.tuesdayEndHour = ("0" + this.tuesdayEndHour);
/*      */     }
/* 8744 */     this.wednesdayStartHour = ((String)obj.get("wednesdayStartHour"));
/* 8745 */     if (Integer.parseInt(this.wednesdayStartHour) < 10)
/*      */     {
/* 8747 */       this.wednesdayStartHour = ("0" + this.wednesdayStartHour);
/*      */     }
/* 8749 */     this.wednesdayEndHour = ((String)obj.get("wednesdayEndHour"));
/* 8750 */     if (Integer.parseInt(this.wednesdayEndHour) < 10)
/*      */     {
/* 8752 */       this.wednesdayEndHour = ("0" + this.wednesdayEndHour);
/*      */     }
/* 8754 */     this.thursdayStartHour = ((String)obj.get("thursdayStartHour"));
/* 8755 */     if (Integer.parseInt(this.thursdayStartHour) < 10)
/*      */     {
/* 8757 */       this.thursdayStartHour = ("0" + this.thursdayStartHour);
/*      */     }
/* 8759 */     this.thursdayEndHour = ((String)obj.get("thursdayEndHour"));
/* 8760 */     if (Integer.parseInt(this.thursdayEndHour) < 10)
/*      */     {
/* 8762 */       this.thursdayEndHour = ("0" + this.thursdayEndHour);
/*      */     }
/* 8764 */     this.fridayStartHour = ((String)obj.get("fridayStartHour"));
/* 8765 */     if (Integer.parseInt(this.fridayStartHour) < 10)
/*      */     {
/* 8767 */       this.fridayStartHour = ("0" + this.fridayStartHour);
/*      */     }
/* 8769 */     this.fridayEndHour = ((String)obj.get("fridayEndHour"));
/* 8770 */     if (Integer.parseInt(this.fridayEndHour) < 10)
/*      */     {
/* 8772 */       this.fridayEndHour = ("0" + this.fridayEndHour);
/*      */     }
/* 8774 */     this.saturdayStartHour = ((String)obj.get("saturdayStartHour"));
/* 8775 */     if (Integer.parseInt(this.saturdayStartHour) < 10)
/*      */     {
/* 8777 */       this.saturdayStartHour = ("0" + this.saturdayStartHour);
/*      */     }
/* 8779 */     this.saturdayEndHour = ((String)obj.get("saturdayEndHour"));
/* 8780 */     if (Integer.parseInt(this.saturdayEndHour) < 10)
/*      */     {
/* 8782 */       this.saturdayEndHour = ("0" + this.saturdayEndHour);
/*      */     }
/* 8784 */     this.sundayStartHour = ((String)obj.get("sundayStartHour"));
/* 8785 */     if (Integer.parseInt(this.sundayStartHour) < 10)
/*      */     {
/* 8787 */       this.sundayStartHour = ("0" + this.sundayStartHour);
/*      */     }
/* 8789 */     this.sundayEndHour = ((String)obj.get("sundayEndHour"));
/* 8790 */     if (Integer.parseInt(this.sundayEndHour) < 10)
/*      */     {
/* 8792 */       this.sundayEndHour = ("0" + this.sundayEndHour);
/*      */     }
/* 8794 */     this.mondayStartMinute = ((String)obj.get("mondayStartMinute"));
/* 8795 */     this.mondayEndMinute = ((String)obj.get("mondayEndMinute"));
/* 8796 */     this.tuesdayStartMinute = ((String)obj.get("tuesdayStartMinute"));
/* 8797 */     this.tuesdayEndMinute = ((String)obj.get("tuesdayEndMinute"));
/* 8798 */     this.wednesdayStartMinute = ((String)obj.get("wednesdayStartMinute"));
/* 8799 */     this.wednesdayEndMinute = ((String)obj.get("wednesdayEndMinute"));
/* 8800 */     this.thursdayStartMinute = ((String)obj.get("thursdayStartMinute"));
/* 8801 */     this.thursdayEndMinute = ((String)obj.get("thursdayEndMinute"));
/* 8802 */     this.fridayStartMinute = ((String)obj.get("fridayStartMinute"));
/* 8803 */     this.fridayEndMinute = ((String)obj.get("fridayEndMinute"));
/* 8804 */     this.saturdayStartMinute = ((String)obj.get("saturdayStartMinute"));
/* 8805 */     this.saturdayEndMinute = ((String)obj.get("saturdayEndMinute"));
/* 8806 */     this.sundayStartMinute = ((String)obj.get("sundayStartMinute"));
/* 8807 */     this.sundayEndMinute = ((String)obj.get("sundayEndMinute"));
/*      */   }
/*      */   
/*      */   public void setReqName(String requesterName)
/*      */   {
/* 8812 */     this.reqName = requesterName;
/*      */   }
/*      */   
/*      */   public String getReqName()
/*      */   {
/* 8817 */     return this.reqName;
/*      */   }
/*      */   
/*      */   public void setAccountName(String accName)
/*      */   {
/* 8822 */     this.accountName = accName;
/*      */   }
/*      */   
/*      */   public String getAccountName()
/*      */   {
/* 8827 */     return this.accountName;
/*      */   }
/*      */   
/*      */   public void setSiteName(String stName) {
/* 8831 */     this.siteName = stName;
/*      */   }
/*      */   
/*      */   public String getSiteName()
/*      */   {
/* 8836 */     return this.siteName;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] getSelectedAgents()
/*      */   {
/* 8842 */     return this.selectedAgents;
/*      */   }
/*      */   
/*      */   public void setSelectedAgents(String[] selectedAgents)
/*      */   {
/* 8847 */     this.selectedAgents = selectedAgents;
/*      */   }
/*      */   
/*      */   public String getSelectAllAgents()
/*      */   {
/* 8852 */     return this.selectAllAgents;
/*      */   }
/*      */   
/*      */   public void setSelectAllAgents(String selectAllAgents)
/*      */   {
/* 8857 */     this.selectAllAgents = selectAllAgents;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getSelectedEumInSchedule()
/*      */   {
/* 8863 */     return this.selectedEumInSchedule;
/*      */   }
/*      */   
/*      */   public void setSelectedEumInSchedule(String selectedEumInSchedule) {
/* 8867 */     this.selectedEumInSchedule = selectedEumInSchedule;
/*      */   }
/*      */   
/* 8870 */   public void setManagedServers(ArrayList managedServers) { this.managedServers = managedServers; }
/*      */   
/*      */   public ArrayList getManagedServers() {
/* 8873 */     return this.managedServers;
/*      */   }
/*      */   
/* 8876 */   public String getSelectedServer() { return this.selectedServer; }
/*      */   
/*      */   public void setSelectedServer(String selectedServer) {
/* 8879 */     this.selectedServer = selectedServer;
/*      */   }
/*      */   
/*      */   public void setEc2Instance(ArrayList ec2Inst)
/*      */   {
/* 8884 */     this.ec2Instance = ec2Inst;
/*      */   }
/*      */   
/*      */   public ArrayList getEc2Instance() {
/* 8888 */     return this.ec2Instance;
/*      */   }
/*      */   
/*      */ 
/* 8892 */   private ArrayList accNamesArr = new ArrayList();
/*      */   
/*      */   public void setAccNamesArr(ArrayList tempArr) {
/* 8895 */     this.accNamesArr = tempArr;
/*      */   }
/*      */   
/*      */   public ArrayList getAccNamesArr()
/*      */   {
/* 8900 */     return this.accNamesArr;
/*      */   }
/*      */   
/* 8903 */   private ArrayList siteNameArr = new ArrayList();
/*      */   
/*      */   public void setSiteNameArr(ArrayList tempArr) {
/* 8906 */     this.siteNameArr = tempArr;
/*      */   }
/*      */   
/*      */   public ArrayList getSiteNameArr()
/*      */   {
/* 8911 */     return this.siteNameArr;
/*      */   }
/*      */   
/* 8914 */   private ArrayList reqNameArr = new ArrayList();
/*      */   
/*      */   public void setReqNameArr(ArrayList tempArr) {
/* 8917 */     this.reqNameArr = tempArr;
/*      */   }
/*      */   
/*      */   public ArrayList getReqNameArr()
/*      */   {
/* 8922 */     return this.reqNameArr;
/*      */   }
/*      */   
/*      */ 
/* 8926 */   private String jobName = "";
/* 8927 */   private String jobOwner = "";
/* 8928 */   private ArrayList jobOwnerlist = null;
/* 8929 */   private String jobCategory = "";
/* 8930 */   private ArrayList jobCategorylist = null;
/* 8931 */   private String jobDescription = "No description available";
/* 8932 */   private int jobStatus = 1;
/*      */   
/*      */ 
/*      */   public void setJobName(String jn)
/*      */   {
/* 8937 */     this.jobName = jn;
/*      */   }
/*      */   
/*      */   public String getJobName() {
/* 8941 */     return this.jobName;
/*      */   }
/*      */   
/*      */   public void setJobOwner(String jo)
/*      */   {
/* 8946 */     this.jobOwner = jo;
/*      */   }
/*      */   
/*      */   public String getJobOwner() {
/* 8950 */     return this.jobOwner;
/*      */   }
/*      */   
/*      */   public void setJobOwnerlist(ArrayList jol)
/*      */   {
/* 8955 */     this.jobOwnerlist = jol;
/*      */   }
/*      */   
/*      */   public ArrayList getJobOwnerlist()
/*      */   {
/* 8960 */     return this.jobOwnerlist;
/*      */   }
/*      */   
/*      */   public void setJobCategory(String jc)
/*      */   {
/* 8965 */     this.jobCategory = jc;
/*      */   }
/*      */   
/*      */   public String getJobCategory() {
/* 8969 */     return this.jobCategory;
/*      */   }
/*      */   
/*      */   public void setJobCategorylist(ArrayList jc) {
/* 8973 */     this.jobCategorylist = jc;
/*      */   }
/*      */   
/*      */   public ArrayList getJobCategorylist() {
/* 8977 */     return this.jobCategorylist;
/*      */   }
/*      */   
/*      */   public void setJobDescription(String jd)
/*      */   {
/* 8982 */     this.jobDescription = jd;
/*      */   }
/*      */   
/*      */   public String getJobDescription() {
/* 8986 */     return this.jobDescription;
/*      */   }
/*      */   
/*      */   public void setJobStatus(int js)
/*      */   {
/* 8991 */     this.jobStatus = js;
/*      */   }
/*      */   
/*      */   public int getJobStatus() {
/* 8995 */     return this.jobStatus;
/*      */   }
/*      */   
/* 8998 */   private String authTypes = "sql";
/*      */   
/* 9000 */   public void setAuthTypes(String auth) { this.authTypes = auth; }
/*      */   
/*      */   public String getAuthTypes() {
/* 9003 */     return this.authTypes;
/*      */   }
/*      */   
/*      */   public ArrayList getAuthTypesValues() {
/* 9007 */     String[] conditionValues = { "SQL", "Windows" };
/* 9008 */     String[] conditionText = { FormatUtil.getString("am.webclient.addmssql.sqlmode"), FormatUtil.getString("am.webclient.addmssql.windowsmode") };
/* 9009 */     ArrayList list = new ArrayList();
/* 9010 */     for (int i = 0; i < conditionValues.length; i++) {
/* 9011 */       list.add(new Option(conditionText[i], conditionValues[i]));
/*      */     }
/* 9013 */     return list;
/*      */   }
/*      */   
/* 9016 */   private boolean instanceCheckBox = false;
/*      */   
/* 9018 */   public void setInstanceCheckBox(boolean checkbox) { this.instanceCheckBox = checkbox; }
/*      */   
/*      */   public boolean getInstanceCheckBox() {
/* 9021 */     return this.instanceCheckBox;
/*      */   }
/*      */   
/* 9024 */   private boolean authCheckBox = false;
/*      */   
/* 9026 */   public void setAuthCheckBox(boolean checkbox) { this.authCheckBox = checkbox; }
/*      */   
/*      */ 
/* 9029 */   public boolean getAuthCheckBox() { return this.authCheckBox; }
/*      */   
/* 9031 */   private String processMonsEnablingStatusInDS = "false";
/*      */   
/*      */   public String getProcessMonsEnablingStatusInDS() {
/* 9034 */     return this.processMonsEnablingStatusInDS;
/*      */   }
/*      */   
/*      */   public void setProcessMonsEnablingStatusInDS(String v)
/*      */   {
/* 9039 */     this.processMonsEnablingStatusInDS = v;
/*      */   }
/*      */   
/*      */   public void setRestApiKey(String keyVal)
/*      */   {
/* 9044 */     this.restApiKey = keyVal;
/*      */   }
/*      */   
/*      */   public String getRestApiKey()
/*      */   {
/* 9049 */     return this.restApiKey;
/*      */   }
/*      */   
/*      */   public void setTicketingType(String ticketType)
/*      */   {
/* 9054 */     this.ticketingType = ticketType;
/*      */   }
/*      */   
/*      */   public String getServicenowInstance() {
/* 9058 */     return this.servicenowInstance;
/*      */   }
/*      */   
/*      */   public void setServicenowInstance(String servicenowInstance) {
/* 9062 */     this.servicenowInstance = servicenowInstance;
/*      */   }
/*      */   
/*      */   public String getServicenowUserName() {
/* 9066 */     return this.servicenowUserName;
/*      */   }
/*      */   
/*      */   public void setServicenowUserName(String servicenowUserName) {
/* 9070 */     this.servicenowUserName = servicenowUserName;
/*      */   }
/*      */   
/*      */   public String getServicenowPassword() {
/* 9074 */     return this.servicenowPassword;
/*      */   }
/*      */   
/*      */   public void setServicenowPassword(String servicenowPassword) {
/* 9078 */     this.servicenowPassword = servicenowPassword;
/*      */   }
/*      */   
/*      */   public String getTicketingType()
/*      */   {
/* 9083 */     return this.ticketingType;
/*      */   }
/*      */   
/* 9086 */   public String getHelpDeskProduct() { return this.helpDeskProduct; }
/*      */   
/*      */   public void setHelpDeskProduct(String helpDeskProduct)
/*      */   {
/* 9090 */     this.helpDeskProduct = helpDeskProduct;
/*      */   }
/*      */   
/*      */   public void setQueryMonitorErrors(ArrayList err) {
/* 9094 */     queryMonitorErrors = err;
/*      */   }
/*      */   
/*      */   public ArrayList getQueryMonitorErrors() {
/* 9098 */     return queryMonitorErrors;
/*      */   }
/*      */   
/*      */   public void setPrmSSLAuth(boolean prmSSL) {
/* 9102 */     this.prmSSLAuth = prmSSL;
/*      */   }
/*      */   
/*      */   public boolean getPrmSSLAuth() {
/* 9106 */     return this.prmSSLAuth;
/*      */   }
/*      */   
/*      */   public void setSecSSLAuth(boolean secSSL) {
/* 9110 */     this.secSSLAuth = secSSL;
/*      */   }
/*      */   
/*      */   public boolean getSecSSLAuth() {
/* 9114 */     return this.secSSLAuth;
/*      */   }
/*      */   
/*      */   public void setMasMailServer(boolean mailserver) {
/* 9118 */     this.masMailServer = mailserver;
/*      */   }
/*      */   
/*      */   public boolean getMasMailServer() {
/* 9122 */     return this.masMailServer;
/*      */   }
/*      */   
/*      */ 
/* 9126 */   private boolean fan = false;
/* 9127 */   private boolean processor = false;
/* 9128 */   private boolean disk = false;
/* 9129 */   private boolean array = false;
/* 9130 */   private boolean chassis = false;
/* 9131 */   private boolean power = false;
/* 9132 */   private boolean temperature = false;
/* 9133 */   private boolean memorydevice = false;
/* 9134 */   private boolean voltage = false;
/* 9135 */   private boolean battery = false;
/*      */   
/* 9137 */   private String hwCriticalStatusMessage = "";
/* 9138 */   private String hwWarningStatusMessage = "";
/* 9139 */   private String hwClearStatusMessage = "";
/* 9140 */   private boolean mspDesk = false;
/*      */   private int webServicesOperationTime;
/*      */   private String customHeaders;
/*      */   
/* 9144 */   public boolean isMspDesk() { return this.mspDesk; }
/*      */   
/*      */   public void setMspDesk(boolean mspDesk)
/*      */   {
/* 9148 */     this.mspDesk = mspDesk;
/*      */   }
/*      */   
/* 9151 */   public String getHwCriticalStatusMessage() { return this.hwCriticalStatusMessage; }
/*      */   
/*      */   public void setHwCriticalStatusMessage(String message)
/*      */   {
/* 9155 */     this.hwCriticalStatusMessage = message;
/*      */   }
/*      */   
/*      */   public String getHwWarningStatusMessage() {
/* 9159 */     return this.hwWarningStatusMessage;
/*      */   }
/*      */   
/*      */   public void setHwWarningStatusMessage(String message) {
/* 9163 */     this.hwWarningStatusMessage = message;
/*      */   }
/*      */   
/*      */   public String getHwClearStatusMessage() {
/* 9167 */     return this.hwClearStatusMessage;
/*      */   }
/*      */   
/*      */   public void setHwClearStatusMessage(String message) {
/* 9171 */     this.hwClearStatusMessage = message;
/*      */   }
/*      */   
/*      */   public boolean isFan()
/*      */   {
/* 9176 */     return this.fan;
/*      */   }
/*      */   
/*      */   public void setFan(boolean fan) {
/* 9180 */     this.fan = fan;
/*      */   }
/*      */   
/*      */   public boolean isTemperature() {
/* 9184 */     return this.temperature;
/*      */   }
/*      */   
/*      */   public void setTemperature(boolean temperature) {
/* 9188 */     this.temperature = temperature;
/*      */   }
/*      */   
/*      */   public boolean isProcessor() {
/* 9192 */     return this.processor;
/*      */   }
/*      */   
/*      */   public void setProcessor(boolean processor) {
/* 9196 */     this.processor = processor;
/*      */   }
/*      */   
/*      */   public boolean isDisk() {
/* 9200 */     return this.disk;
/*      */   }
/*      */   
/*      */   public void setDisk(boolean disk) {
/* 9204 */     this.disk = disk;
/*      */   }
/*      */   
/*      */   public boolean isArray() {
/* 9208 */     return this.array;
/*      */   }
/*      */   
/*      */   public void setArray(boolean array) {
/* 9212 */     this.array = array;
/*      */   }
/*      */   
/*      */   public boolean isChassis() {
/* 9216 */     return this.chassis;
/*      */   }
/*      */   
/*      */   public void setChassis(boolean chassis) {
/* 9220 */     this.chassis = chassis;
/*      */   }
/*      */   
/*      */   public boolean isMemorydevice() {
/* 9224 */     return this.memorydevice;
/*      */   }
/*      */   
/*      */   public void setMemorydevice(boolean memorydevice) {
/* 9228 */     this.memorydevice = memorydevice;
/*      */   }
/*      */   
/*      */   public boolean isVoltage() {
/* 9232 */     return this.voltage;
/*      */   }
/*      */   
/*      */   public void setVoltage(boolean voltage) {
/* 9236 */     this.voltage = voltage;
/*      */   }
/*      */   
/*      */   public boolean isBattery() {
/* 9240 */     return this.battery;
/*      */   }
/*      */   
/*      */   public void setBattery(boolean battery) {
/* 9244 */     this.battery = battery;
/*      */   }
/*      */   
/*      */   public boolean isPower() {
/* 9248 */     return this.power;
/*      */   }
/*      */   
/*      */   public void setPower(boolean power) {
/* 9252 */     this.power = power;
/*      */   }
/*      */   
/*      */   public void setAmazonEC2PrimaryKey(String temp)
/*      */   {
/* 9257 */     this.amazonEC2PrimaryKey = temp;
/*      */   }
/*      */   
/*      */   public String getAmazonEC2PrimaryKey()
/*      */   {
/* 9262 */     return this.amazonEC2PrimaryKey;
/*      */   }
/*      */   
/*      */   public String getEc2MergeEnabled()
/*      */   {
/* 9267 */     return this.ec2MergeEnabled;
/*      */   }
/*      */   
/*      */   public void setEc2MergeEnabled(String value)
/*      */   {
/* 9272 */     this.ec2MergeEnabled = value;
/*      */   }
/*      */   
/*      */   public void setEc2AlertTerminatedInstance(boolean ec2AlertTerminatedInstance) {
/* 9276 */     this.ec2AlertTerminatedInstance = ec2AlertTerminatedInstance;
/*      */   }
/*      */   
/*      */   public boolean isEc2AlertTerminatedInstance() {
/* 9280 */     return this.ec2AlertTerminatedInstance;
/*      */   }
/*      */   
/*      */   public void setSignatureVersion4SigningEnabled(boolean signatureVersion4SigningEnabled) {
/* 9284 */     this.signatureVersion4SigningEnabled = signatureVersion4SigningEnabled;
/*      */   }
/*      */   
/*      */   public boolean isSignatureVersion4SigningEnabled() {
/* 9288 */     return this.signatureVersion4SigningEnabled;
/*      */   }
/*      */   
/*      */   public String getPassphrase() {
/* 9292 */     return this.passphrase;
/*      */   }
/*      */   
/* 9295 */   public void setPassphrase(String phrase) { this.passphrase = phrase; }
/*      */   
/*      */   public void setDisplaynamelength(String dispLength)
/*      */   {
/* 9299 */     this.displaynamelength = dispLength;
/*      */   }
/*      */   
/* 9302 */   public String getDisplaynamelength() { return this.displaynamelength; }
/*      */   
/*      */   public void setResFulWebservice(boolean isRestFul)
/*      */   {
/* 9306 */     this.resFulWebservice = isRestFul;
/*      */   }
/*      */   
/* 9309 */   public boolean isResFulWebservice() { return this.resFulWebservice; }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWebServicesOperationTime()
/*      */   {
/* 9316 */     return this.webServicesOperationTime;
/*      */   }
/*      */   
/*      */   public void setWebServicesOperationTime(int time) {
/* 9320 */     this.webServicesOperationTime = time;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getCustomHeaders()
/*      */   {
/* 9327 */     return this.customHeaders;
/*      */   }
/*      */   
/*      */   public void setCustomHeaders(String headers) {
/* 9331 */     this.customHeaders = headers;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getPingPackToSend()
/*      */   {
/* 9338 */     return this.pingPackToSend;
/*      */   }
/*      */   
/*      */   public void setPingPackToSend(int packCount) {
/* 9342 */     this.pingPackToSend = packCount;
/*      */   }
/*      */   
/*      */   public void setEndPointUrl(String endPointUrl)
/*      */   {
/* 9347 */     this.endPointUrl = endPointUrl;
/*      */   }
/*      */   
/* 9350 */   public String getEndPointUrl() { return this.endPointUrl; }
/*      */   
/*      */ 
/*      */   private int pingPackToSend;
/*      */   
/*      */   private String endPointUrl;
/*      */   
/* 9357 */   private boolean ciToBeDeleted = true;
/*      */   
/*      */ 
/* 9360 */   private boolean ciAttributesToBeSynced = true;
/* 9361 */   private boolean ciLinksToBeShown = true;
/*      */   
/*      */ 
/* 9364 */   private List<LabelValueBean> firstLevelMonitorTypesOptions = new ArrayList();
/* 9365 */   private List<LabelValueBean> excludeFirstLevelMonitorTypesOptions = new ArrayList();
/*      */   
/*      */ 
/* 9368 */   private List<LabelValueBean> secondLevelMonitorTypesOptions = new ArrayList();
/* 9369 */   private List<LabelValueBean> includeSecondLevelMonitorTypesOptions = new ArrayList();
/*      */   
/* 9371 */   private String[] excludeFirstLevelMonitorTypes = new String[0];
/* 9372 */   private String[] includeSecondLevelMonitorTypes = new String[0];
/*      */   
/* 9374 */   private boolean cISyncEnabled = false;
/* 9375 */   private boolean ciRlMapalongWithListView = false;
/*      */   
/*      */ 
/* 9378 */   boolean ticketingEnabled = false;
/* 9379 */   boolean ticketLinkToBeShown = true;
/* 9380 */   boolean ticketToBeMappedToCI = true;
/* 9381 */   boolean dynamicTicketingUsingAction = true;
/*      */   
/* 9383 */   boolean readonlyTicket = false;
/*      */   
/* 9385 */   boolean dynamicTicketingUsingForm = true;
/* 9386 */   boolean notesToBeAddedForTicket = true;
/* 9387 */   boolean updateTicketForchangeInStatusAlone = true;
/* 9388 */   boolean allowOverWriteOfReqTemplate = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 9393 */   String closeTicketPolicy = TicketSettings.CloseTicketPolicy.CLOSE_TICKET_UPDATE_NOTES.toString();
/* 9394 */   String reOpenTicketPolicy = TicketSettings.ReOpenTicketPolicy.REOPEN_TICKET_CUSTOM_PERIOD.toString();
/* 9395 */   long reOpenPeriod = 2L;
/*      */   
/*      */   public boolean isUpdateTicketForchangeInStatusAlone()
/*      */   {
/* 9399 */     return this.updateTicketForchangeInStatusAlone;
/*      */   }
/*      */   
/*      */   public void setUpdateTicketForchangeInStatusAlone(boolean updateTicketForchangeInStatusAlone)
/*      */   {
/* 9404 */     this.updateTicketForchangeInStatusAlone = updateTicketForchangeInStatusAlone;
/*      */   }
/*      */   
/*      */   public boolean iscISyncEnabled() {
/* 9408 */     return this.cISyncEnabled;
/*      */   }
/*      */   
/*      */   public void setcISyncEnabled(boolean cISyncEnabled) {
/* 9412 */     this.cISyncEnabled = cISyncEnabled;
/*      */   }
/*      */   
/*      */   public boolean isReadonlyTicket() {
/* 9416 */     return this.readonlyTicket;
/*      */   }
/*      */   
/*      */   public void setReadonlyTicket(boolean readonlyTicket) {
/* 9420 */     this.readonlyTicket = readonlyTicket;
/*      */   }
/*      */   
/* 9423 */   public String[] getExcludeFirstLevelMonitorTypes() { return this.excludeFirstLevelMonitorTypes; }
/*      */   
/*      */ 
/*      */   public void setExcludeFirstLevelMonitorTypes(String[] excludeFirstLevelMonitorTypes)
/*      */   {
/* 9428 */     this.excludeFirstLevelMonitorTypes = excludeFirstLevelMonitorTypes;
/*      */   }
/*      */   
/* 9431 */   public String[] getIncludeSecondLevelMonitorTypes() { return this.includeSecondLevelMonitorTypes; }
/*      */   
/*      */ 
/*      */   public void setIncludeSecondLevelMonitorTypes(String[] includeSecondLevelMonitorTypes)
/*      */   {
/* 9436 */     this.includeSecondLevelMonitorTypes = includeSecondLevelMonitorTypes;
/*      */   }
/*      */   
/* 9439 */   public List<LabelValueBean> getFirstLevelMonitorTypesOptions() { return this.firstLevelMonitorTypesOptions; }
/*      */   
/*      */   public void setFirstLevelMonitorTypesOptions(List<LabelValueBean> firstLevelMonitorTypes)
/*      */   {
/* 9443 */     this.firstLevelMonitorTypesOptions = firstLevelMonitorTypes;
/*      */   }
/*      */   
/*      */   public boolean isNotesToBeAddedForTicket() {
/* 9447 */     return this.notesToBeAddedForTicket;
/*      */   }
/*      */   
/*      */   public void setNotesToBeAddedForTicket(boolean notedToBeAddedForTicket) {
/* 9451 */     this.notesToBeAddedForTicket = notedToBeAddedForTicket;
/*      */   }
/*      */   
/* 9454 */   public boolean isDynamicTicketingUsingAction() { return this.dynamicTicketingUsingAction; }
/*      */   
/*      */   public void setDynamicTicketingUsingAction(boolean dynamicTicketingUsingAction)
/*      */   {
/* 9458 */     this.dynamicTicketingUsingAction = dynamicTicketingUsingAction;
/*      */   }
/*      */   
/*      */   public boolean isDynamicTicketingUsingForm() {
/* 9462 */     return this.dynamicTicketingUsingForm;
/*      */   }
/*      */   
/*      */   public void setDynamicTicketingUsingForm(boolean dynamicTicketingUsingForm) {
/* 9466 */     this.dynamicTicketingUsingForm = dynamicTicketingUsingForm;
/*      */   }
/*      */   
/* 9469 */   public List<LabelValueBean> getIncludeSecondLevelMonitorTypesOptions() { return this.includeSecondLevelMonitorTypesOptions; }
/*      */   
/*      */ 
/*      */   public void setIncludeSecondLevelMonitorTypesOptions(List<LabelValueBean> includeSecondLevelMonitorTypes)
/*      */   {
/* 9474 */     this.includeSecondLevelMonitorTypesOptions = includeSecondLevelMonitorTypes;
/*      */   }
/*      */   
/* 9477 */   public List<LabelValueBean> getExcludeFirstLevelMonitorTypesOptions() { return this.excludeFirstLevelMonitorTypesOptions; }
/*      */   
/*      */ 
/*      */   public void setExcludeFirstLevelMonitorTypesOptions(List<LabelValueBean> excludeFirstLevelMonitorTypes)
/*      */   {
/* 9482 */     this.excludeFirstLevelMonitorTypesOptions = excludeFirstLevelMonitorTypes;
/*      */   }
/*      */   
/* 9485 */   public boolean isCiToBeDeleted() { return this.ciToBeDeleted; }
/*      */   
/*      */   public void setCiToBeDeleted(boolean ciToBeDeleted)
/*      */   {
/* 9489 */     this.ciToBeDeleted = ciToBeDeleted;
/*      */   }
/*      */   
/*      */   public boolean isCiAttributesToBeSynced() {
/* 9493 */     return this.ciAttributesToBeSynced;
/*      */   }
/*      */   
/*      */   public void setCiAttributesToBeSynced(boolean ciAttributesToBeSynced) {
/* 9497 */     this.ciAttributesToBeSynced = ciAttributesToBeSynced;
/*      */   }
/*      */   
/*      */   public boolean isCiLinksToBeShown() {
/* 9501 */     return this.ciLinksToBeShown;
/*      */   }
/*      */   
/*      */   public void setCiLinksToBeShown(boolean ciLinksToBeShown) {
/* 9505 */     this.ciLinksToBeShown = ciLinksToBeShown;
/*      */   }
/*      */   
/*      */   public List<LabelValueBean> getSecondLevelMonitorTypesOptions() {
/* 9509 */     return this.secondLevelMonitorTypesOptions;
/*      */   }
/*      */   
/*      */   public void setSecondLevelMonitorTypesOptions(List<LabelValueBean> secondLevelMonitorTypes) {
/* 9513 */     this.secondLevelMonitorTypesOptions = secondLevelMonitorTypes;
/*      */   }
/*      */   
/*      */   public boolean isCiRlMapalongWithListView()
/*      */   {
/* 9518 */     return this.ciRlMapalongWithListView;
/*      */   }
/*      */   
/* 9521 */   public void setCiRlMapalongWithListView(boolean ciRlMapalongWithListView) { this.ciRlMapalongWithListView = ciRlMapalongWithListView; }
/*      */   
/*      */   public boolean isTicketingEnabled()
/*      */   {
/* 9525 */     return this.ticketingEnabled;
/*      */   }
/*      */   
/*      */   public void setTicketingEnabled(boolean ticketingEnabled) {
/* 9529 */     this.ticketingEnabled = ticketingEnabled;
/*      */   }
/*      */   
/*      */   public boolean isTicketLinkToBeShown() {
/* 9533 */     return this.ticketLinkToBeShown;
/*      */   }
/*      */   
/*      */   public void setTicketLinkToBeShown(boolean ticketLinkToBeShown) {
/* 9537 */     this.ticketLinkToBeShown = ticketLinkToBeShown;
/*      */   }
/*      */   
/*      */   public boolean isTicketToBeMappedToCI() {
/* 9541 */     return this.ticketToBeMappedToCI;
/*      */   }
/*      */   
/*      */   public void setTicketToBeMappedToCI(boolean ticketToBeMappedToCI) {
/* 9545 */     this.ticketToBeMappedToCI = ticketToBeMappedToCI;
/*      */   }
/*      */   
/*      */   public String getCloseTicketPolicy() {
/* 9549 */     return this.closeTicketPolicy;
/*      */   }
/*      */   
/*      */   public void setCloseTicketPolicy(String closeTicketPolicy) {
/* 9553 */     this.closeTicketPolicy = closeTicketPolicy;
/*      */   }
/*      */   
/*      */   public String getReOpenTicketPolicy() {
/* 9557 */     return this.reOpenTicketPolicy;
/*      */   }
/*      */   
/*      */   public void setReOpenTicketPolicy(String reOpenTicketPolicy) {
/* 9561 */     this.reOpenTicketPolicy = reOpenTicketPolicy;
/*      */   }
/*      */   
/*      */   public long getReOpenPeriod() {
/* 9565 */     return this.reOpenPeriod;
/*      */   }
/*      */   
/*      */   public void setReOpenPeriod(long reOpenPeriod) {
/* 9569 */     this.reOpenPeriod = reOpenPeriod;
/*      */   }
/*      */   
/* 9572 */   private String reqTemplate = "";
/* 9573 */   private String tokenAndOperation = "";
/*      */   
/*      */   public String getReqTemplate() {
/* 9576 */     return this.reqTemplate;
/*      */   }
/*      */   
/*      */   public void setReqTemplate(String reqTemplate) {
/* 9580 */     this.reqTemplate = reqTemplate;
/*      */   }
/*      */   
/* 9583 */   public boolean isAllowOverWriteOfReqTemplate() { return this.allowOverWriteOfReqTemplate; }
/*      */   
/*      */   public void setAllowOverWriteOfReqTemplate(boolean allowOverWriteOfReqTemplate) {
/* 9586 */     this.allowOverWriteOfReqTemplate = allowOverWriteOfReqTemplate;
/*      */   }
/*      */   
/* 9589 */   private boolean isEditAllowed = true;
/*      */   private FormFile rbmTCfile;
/*      */   
/* 9592 */   public boolean getIsEditAllowed() { return this.isEditAllowed; }
/*      */   
/*      */   public void setIsEditAllowed(boolean isEditAllowed)
/*      */   {
/* 9596 */     this.isEditAllowed = isEditAllowed;
/*      */   }
/*      */   
/*      */ 
/*      */   private String rbmDisplayName;
/*      */   
/*      */   public void setChkCustomizeVarbinds(String chkCustomizeVarbinds)
/*      */   {
/* 9604 */     this.chkCustomizeVarbinds = chkCustomizeVarbinds;
/*      */   }
/*      */   
/*      */   public String getChkCustomizeVarbinds() {
/* 9608 */     return this.chkCustomizeVarbinds;
/*      */   }
/*      */   
/*      */   public void setTrapCustomVarbinds(String trapCustomVarbinds)
/*      */   {
/* 9613 */     this.trapCustomVarbinds = trapCustomVarbinds;
/*      */   }
/*      */   
/*      */   public String getTrapCustomVarbinds() {
/* 9617 */     return this.trapCustomVarbinds;
/*      */   }
/*      */   
/*      */   public void setChkAssociateTrapSeverity(String chkAssociateTrapSeverity)
/*      */   {
/* 9622 */     this.chkAssociateTrapSeverity = chkAssociateTrapSeverity;
/*      */   }
/*      */   
/*      */   public String getChkAssociateTrapSeverity() {
/* 9626 */     return this.chkAssociateTrapSeverity;
/*      */   }
/*      */   
/*      */ 
/*      */   public FormFile getRbmTCfile()
/*      */   {
/* 9632 */     return this.rbmTCfile;
/*      */   }
/*      */   
/*      */   public void setRbmTCfile(FormFile rbmTCfile) {
/* 9636 */     this.rbmTCfile = rbmTCfile;
/*      */   }
/*      */   
/*      */   public String getRbmDisplayName() {
/* 9640 */     return this.rbmDisplayName;
/*      */   }
/*      */   
/*      */   public void setRbmDisplayName(String rbmDisplayName) {
/* 9644 */     this.rbmDisplayName = rbmDisplayName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 9649 */   private int rbmPollingInterval = 10;
/*      */   
/*      */   public int getRbmPollingInterval() {
/* 9652 */     return this.rbmPollingInterval;
/*      */   }
/*      */   
/*      */   public void setRbmPollingInterval(int rbmPollingInterval) {
/* 9656 */     this.rbmPollingInterval = rbmPollingInterval;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setTokenAndOperation(String tokenAndOperation)
/*      */   {
/* 9662 */     this.tokenAndOperation = tokenAndOperation;
/*      */   }
/*      */   
/*      */   public String getTokenAndOperation() {
/* 9666 */     return this.tokenAndOperation;
/*      */   }
/*      */   
/* 9669 */   private int clearCondition = 0;
/*      */   
/* 9671 */   public void setClearCondition(int condn) { this.clearCondition = condn; }
/*      */   
/*      */   public int getClearCondition() {
/* 9674 */     return this.clearCondition;
/*      */   }
/*      */   
/* 9677 */   String clearConditionRuleType = "0";
/*      */   
/* 9679 */   public void setClearConditionRuleType(String ruleT) { this.clearConditionRuleType = ruleT; }
/*      */   
/*      */   public String getClearConditionRuleType() {
/* 9682 */     return this.clearConditionRuleType;
/*      */   }
/*      */   
/* 9685 */   String clearConditionCountVal = "1";
/*      */   
/* 9687 */   public void setClearConditionCountVal(String ruleVal) { this.clearConditionCountVal = ruleVal; }
/*      */   
/*      */   public String getClearConditionCountVal() {
/* 9690 */     return this.clearConditionCountVal;
/*      */   }
/*      */   
/* 9693 */   private String clearConditionContent = "";
/*      */   
/* 9695 */   public void setClearConditionContent(String content) { this.clearConditionContent = content; }
/*      */   
/*      */   public String getClearConditionContent() {
/* 9698 */     return this.clearConditionContent;
/*      */   }
/*      */   
/* 9701 */   private String clearConditionRegEx = "off";
/*      */   
/* 9703 */   public void setClearConditionRegEx(String regex) { this.clearConditionRegEx = regex; }
/*      */   
/*      */   public String getClearConditionRegEx() {
/* 9706 */     return this.clearConditionRegEx;
/*      */   }
/*      */   
/*      */   public ArrayList getSQLServers()
/*      */   {
/* 9711 */     ArrayList list = new ArrayList();
/* 9712 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 9715 */       rs = AMConnectionPool.executeQueryStmt("SELECT resourceid, displayname from AM_ManagedObject where type='MSSQL-DB-server' order by displayname");
/* 9716 */       while (rs.next())
/*      */       {
/* 9718 */         Properties p = new Properties();
/* 9719 */         p.setProperty("label", rs.getString("displayname"));
/* 9720 */         p.setProperty("value", rs.getString("resourceid"));
/* 9721 */         list.add(p);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 9726 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 9730 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 9732 */     return list;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\AMActionForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */