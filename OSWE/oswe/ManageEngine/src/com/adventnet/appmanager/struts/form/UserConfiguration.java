/*      */ package com.adventnet.appmanager.struts.form;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UserConfiguration
/*      */   extends ActionForm
/*      */ {
/*   24 */   private String userid = null;
/*   25 */   private String username = null;
/*   26 */   private String password = null;
/*   27 */   private String email = null;
/*   28 */   private String description = null;
/*      */   private FormFile theFile;
/*   30 */   private boolean change = false;
/*   31 */   private boolean deletePic = false;
/*      */   
/*   33 */   private String[] groups = null;
/*   34 */   private String allowManage = "false";
/*   35 */   private String allowReset = "false";
/*   36 */   private String allowUpdateIP = "false";
/*   37 */   private String allowExecute = "false";
/*   38 */   private String allowServices = "false";
/*   39 */   private String allowEdit = "false";
/*   40 */   private String allowUpdateConfig = "false";
/*   41 */   private String allowManageDB = "false";
/*   42 */   private String allowOPRTelnet = "false";
/*   43 */   private String allowOPRProcess = "true";
/*   44 */   private String createDomainUser = "false";
/*   45 */   private String allowClearAlarms = "false";
/*   46 */   private String enableRestrictedAdmin = "false";
/*   47 */   private String allowNonAdminUsersEditUsername = "true";
/*   48 */   private String allowDAdminViewAllThresholds = "false";
/*   49 */   private String allowDAdminViewAllActions = "false";
/*   50 */   private String allowAdminTelnet = "false";
/*   51 */   private String allowJumptoLink = "true";
/*   52 */   private String allowDownTimeSchedule = "false";
/*   53 */   private String allowOprViewAllDownTimeSchedule = "false";
/*   54 */   private String updateChk = "false";
/*   55 */   private String delegatedAdmin = "false";
/*   56 */   private String drilldown = "false";
/*   57 */   private String allowAdminWindowsServices = "false";
/*   58 */   private String allowOperatorEditTabs = "true";
/*      */   
/*      */ 
/*   61 */   private String allowML = "false";
/*   62 */   private String allowNA = "false";
/*   63 */   private String allowDT = "false";
/*   64 */   private String allowSC = "false";
/*   65 */   private String allowLL = "false";
/*   66 */   private String allowCS = "false";
/*   67 */   private String allowAL = "false";
/*   68 */   private String allowCSU = "false";
/*   69 */   private String allowCMD = "false";
/*   70 */   private String allowJOB = "false";
/*   71 */   private String allowMSG = "false";
/*   72 */   private String allowSPL = "false";
/*   73 */   private String allowSUB = "false";
/*   74 */   private String allowAS400 = "false";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   80 */   private String accLockout = "false";
/*   81 */   private String singleSession = "false";
/*   82 */   private String pwdPolicy = "false";
/*   83 */   private String accLockoutTimeout = "30";
/*   84 */   private String accLockOutCount = "3";
/*      */   
/*      */ 
/*      */ 
/*   88 */   private String ssoEnable = "false";
/*   89 */   private boolean sslenabled = false;
/*   90 */   private String userRole = "ADMIN";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getssoenable()
/*      */   {
/*   97 */     return this.ssoEnable;
/*      */   }
/*      */   
/*      */   public void setssoenable(String isSsoEnable)
/*      */   {
/*  102 */     this.ssoEnable = isSsoEnable;
/*      */   }
/*      */   
/*      */   public boolean getsslenable()
/*      */   {
/*  107 */     return this.sslenabled;
/*      */   }
/*      */   
/*      */   public void setsslenable(boolean sslenabled)
/*      */   {
/*  112 */     this.sslenabled = sslenabled;
/*      */   }
/*      */   
/*      */   public String getAccLockoutTimeout()
/*      */   {
/*  117 */     return this.accLockoutTimeout;
/*      */   }
/*      */   
/*      */   public void setAccLockoutTimeout(String accLockoutTimeout)
/*      */   {
/*  122 */     this.accLockoutTimeout = accLockoutTimeout;
/*      */   }
/*      */   
/*      */   public String getAccLockOutCount()
/*      */   {
/*  127 */     return this.accLockOutCount;
/*      */   }
/*      */   
/*      */   public void setAccLockOutCount(String accLockOutCount)
/*      */   {
/*  132 */     this.accLockOutCount = accLockOutCount;
/*      */   }
/*      */   
/*      */   public String getAccLockout()
/*      */   {
/*  137 */     return this.accLockout;
/*      */   }
/*      */   
/*      */   public void setAccLockout(String accLockout)
/*      */   {
/*  142 */     this.accLockout = accLockout;
/*      */   }
/*      */   
/*      */   public String getSingleSession()
/*      */   {
/*  147 */     return this.singleSession;
/*      */   }
/*      */   
/*      */   public void setSingleSession(String singleSession)
/*      */   {
/*  152 */     this.singleSession = singleSession;
/*      */   }
/*      */   
/*      */   public String getPwdPolicy()
/*      */   {
/*  157 */     return this.pwdPolicy;
/*      */   }
/*      */   
/*      */   public void setPwdPolicy(String pwdPolicy)
/*      */   {
/*  162 */     this.pwdPolicy = pwdPolicy;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUpdateChk()
/*      */   {
/*  171 */     return this.updateChk;
/*      */   }
/*      */   
/*      */   public void setUpdateChk(String updateChk) {
/*  175 */     this.updateChk = updateChk;
/*  176 */     System.out.println("the updatChk is set to::===>" + updateChk);
/*      */   }
/*      */   
/*      */   public String getDelegatedAdmin() {
/*  180 */     return this.delegatedAdmin;
/*      */   }
/*      */   
/*      */   public void setDelegatedAdmin(String delegate) {
/*  184 */     this.delegatedAdmin = delegate;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getUserid()
/*      */   {
/*  190 */     return this.userid;
/*      */   }
/*      */   
/*      */   public String getPassword() {
/*  194 */     return this.password;
/*      */   }
/*      */   
/*      */   public void setPassword(String password) {
/*  198 */     this.password = password;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setUserid(String userid)
/*      */   {
/*  204 */     this.userid = userid;
/*      */   }
/*      */   
/*      */   public String[] getGroups()
/*      */   {
/*  209 */     return this.groups;
/*      */   }
/*      */   
/*      */   public void setGroups(String[] groups)
/*      */   {
/*  214 */     this.groups = groups;
/*      */   }
/*      */   
/*      */   public String getDescription()
/*      */   {
/*  219 */     return this.description;
/*      */   }
/*      */   
/*      */   public void setDescription(String description)
/*      */   {
/*  224 */     this.description = description;
/*      */   }
/*      */   
/*      */   public String getEmail()
/*      */   {
/*  229 */     return this.email;
/*      */   }
/*      */   
/*      */   public void setEmail(String email)
/*      */   {
/*  234 */     this.email = email;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getUserName()
/*      */   {
/*  240 */     return this.username;
/*      */   }
/*      */   
/*      */   public void setUserName(String username)
/*      */   {
/*  245 */     this.username = username;
/*      */   }
/*      */   
/*      */   public boolean getChange()
/*      */   {
/*  250 */     return this.change;
/*      */   }
/*      */   
/*      */   public void setChange(boolean changeph)
/*      */   {
/*  255 */     this.change = changeph;
/*      */   }
/*      */   
/*      */   public boolean getDeletePic()
/*      */   {
/*  260 */     return this.deletePic;
/*      */   }
/*      */   
/*      */   public void setDeletePic(boolean deleteph)
/*      */   {
/*  265 */     this.deletePic = deleteph;
/*      */   }
/*      */   
/*      */   public FormFile getTheFile() {
/*  269 */     return this.theFile;
/*      */   }
/*      */   
/*      */   public void setTheFile(FormFile theFile) {
/*  273 */     this.theFile = theFile;
/*      */   }
/*      */   
/*      */   public ArrayList getAvailableGroups()
/*      */   {
/*  278 */     ArrayList rows = new ArrayList(3);
/*  279 */     Properties props = new Properties();
/*      */     
/*  281 */     if (!OEMUtil.isRemove("am.operatorrole.remove"))
/*      */     {
/*  283 */       if (("ADMIN".equals(this.userRole)) || ("OPERATOR".equals(this.userRole))) {
/*  284 */         props = new Properties();
/*  285 */         props.setProperty("label", FormatUtil.getString("am.webclient.role.operator.text"));
/*  286 */         props.setProperty("value", "OPERATOR");
/*  287 */         rows.add(props);
/*      */       }
/*      */     }
/*      */     
/*  291 */     if (!OEMUtil.isRemove("am.userrole.remove"))
/*      */     {
/*  293 */       if (("ADMIN".equals(this.userRole)) || ("USERS".equals(this.userRole))) {
/*  294 */         props = new Properties();
/*  295 */         props.setProperty("label", FormatUtil.getString("am.webclient.role.user.text"));
/*  296 */         props.setProperty("value", "USERS");
/*  297 */         rows.add(props);
/*      */       }
/*      */     }
/*  300 */     if (!OEMUtil.isRemove("am.adminrole.remove"))
/*      */     {
/*  302 */       if (("ADMIN".equals(this.userRole)) || ("DELEGATEDADMIN".equals(this.userRole))) {
/*  303 */         props = new Properties();
/*  304 */         props.setProperty("label", FormatUtil.getString("am.webclient.user.Administrator.text"));
/*  305 */         props.setProperty("value", "ADMIN");
/*  306 */         rows.add(props);
/*      */       }
/*      */     }
/*      */     
/*  310 */     if (!OEMUtil.isRemove("am.managerrole.remove"))
/*      */     {
/*  312 */       if (("ADMIN".equals(this.userRole)) || ("MANAGER".equals(this.userRole))) {
/*  313 */         props = new Properties();
/*  314 */         props.setProperty("label", FormatUtil.getString("am.webclient.role.manager.text"));
/*  315 */         props.setProperty("value", "MANAGER");
/*  316 */         rows.add(props);
/*      */       }
/*      */     }
/*  319 */     return rows;
/*      */   }
/*      */   
/*      */   public String getAllowManage()
/*      */   {
/*  324 */     return this.allowManage;
/*      */   }
/*      */   
/*      */   public void setAllowManage(String allowManage)
/*      */   {
/*  329 */     this.allowManage = allowManage;
/*      */   }
/*      */   
/*      */   public String getAllowReset()
/*      */   {
/*  334 */     return this.allowReset;
/*      */   }
/*      */   
/*      */   public void setAllowReset(String allowReset)
/*      */   {
/*  339 */     this.allowReset = allowReset;
/*      */   }
/*      */   
/*      */   public String getAllowUpdateIP()
/*      */   {
/*  344 */     return this.allowUpdateIP;
/*      */   }
/*      */   
/*      */   public void setAllowUpdateIP(String allowUpdateIP)
/*      */   {
/*  349 */     this.allowUpdateIP = allowUpdateIP;
/*      */   }
/*      */   
/*      */   public String getAllowEdit()
/*      */   {
/*  354 */     return this.allowEdit;
/*      */   }
/*      */   
/*      */   public void setAllowEdit(String allowEdit)
/*      */   {
/*  359 */     this.allowEdit = allowEdit;
/*      */   }
/*      */   
/*      */   public String getAllowExecute()
/*      */   {
/*  364 */     return this.allowExecute;
/*      */   }
/*      */   
/*      */   public void setAllowExecute(String allowExecute)
/*      */   {
/*  369 */     this.allowExecute = allowExecute;
/*      */   }
/*      */   
/*      */   public String getAllowServices()
/*      */   {
/*  374 */     return this.allowServices;
/*      */   }
/*      */   
/*      */   public void setAllowServices(String allowServices)
/*      */   {
/*  379 */     this.allowServices = allowServices;
/*      */   }
/*      */   
/*      */   public String getallowOPRTelnet()
/*      */   {
/*  384 */     return this.allowOPRTelnet;
/*      */   }
/*      */   
/*      */   public void setallowOPRTelnet(String allowOPRTelnet)
/*      */   {
/*  389 */     this.allowOPRTelnet = allowOPRTelnet;
/*      */   }
/*      */   
/*      */   public String getallowOPRProcess()
/*      */   {
/*  394 */     return this.allowOPRProcess;
/*      */   }
/*      */   
/*      */   public void setallowOPRProcess(String allowOPRProcess)
/*      */   {
/*  399 */     this.allowOPRProcess = allowOPRProcess;
/*      */   }
/*      */   
/*      */   public String getCreateDomainUser()
/*      */   {
/*  404 */     return this.createDomainUser;
/*      */   }
/*      */   
/*      */   public void setCreateDomainUser(String user)
/*      */   {
/*  409 */     this.createDomainUser = user;
/*      */   }
/*      */   
/*      */   public String getAllowClearAlarms() {
/*  413 */     return this.allowClearAlarms;
/*      */   }
/*      */   
/*      */   public void setAllowClearAlarms(String allowClearAlarms) {
/*  417 */     this.allowClearAlarms = allowClearAlarms;
/*      */   }
/*      */   
/*      */   public String getEnableRestrictedAdmin() {
/*  421 */     return this.enableRestrictedAdmin;
/*      */   }
/*      */   
/*      */   public void setEnableRestrictedAdmin(String admin) {
/*  425 */     this.enableRestrictedAdmin = admin;
/*      */   }
/*      */   
/*      */   public String getAllowNonAdminUsersEditUsername() {
/*  429 */     return this.allowNonAdminUsersEditUsername;
/*      */   }
/*      */   
/*      */   public void setAllowNonAdminUsersEditUsername(String allowEditUsername) {
/*  433 */     this.allowNonAdminUsersEditUsername = allowEditUsername;
/*      */   }
/*      */   
/*      */   public String getAllowDAdminViewAllThresholds() {
/*  437 */     return this.allowDAdminViewAllThresholds;
/*      */   }
/*      */   
/*      */   public void setAllowDAdminViewAllThresholds(String allow) {
/*  441 */     this.allowDAdminViewAllThresholds = allow;
/*      */   }
/*      */   
/*      */   public String getAllowDAdminViewAllActions() {
/*  445 */     return this.allowDAdminViewAllActions;
/*      */   }
/*      */   
/*      */   public void setAllowDAdminViewAllActions(String admin) {
/*  449 */     this.allowDAdminViewAllActions = admin;
/*      */   }
/*      */   
/*      */   public String getallowAdminTelnet()
/*      */   {
/*  454 */     return this.allowAdminTelnet;
/*      */   }
/*      */   
/*      */   public void setallowAdminTelnet(String allowAdminTelnet)
/*      */   {
/*  459 */     this.allowAdminTelnet = allowAdminTelnet;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getallowJumptoLink()
/*      */   {
/*  465 */     return this.allowJumptoLink;
/*      */   }
/*      */   
/*      */   public void setallowJumptoLink(String allowJumptoLink)
/*      */   {
/*  470 */     this.allowJumptoLink = allowJumptoLink;
/*      */   }
/*      */   
/*      */   public String getallowDownTimeSchedule()
/*      */   {
/*  475 */     return this.allowDownTimeSchedule;
/*      */   }
/*      */   
/*      */   public void setallowDownTimeSchedule(String allowDownTimeSchedule)
/*      */   {
/*  480 */     this.allowDownTimeSchedule = allowDownTimeSchedule;
/*      */   }
/*      */   
/*      */   public String getallowOprViewAllDownTimeSchedule()
/*      */   {
/*  485 */     return this.allowOprViewAllDownTimeSchedule;
/*      */   }
/*      */   
/*      */   public void setallowOprViewAllDownTimeSchedule(String allowOprViewAllDownTimeSchedule)
/*      */   {
/*  490 */     this.allowOprViewAllDownTimeSchedule = allowOprViewAllDownTimeSchedule;
/*      */   }
/*      */   
/*      */   public void setAllowAdminWindowsServices(String allowAdminWindowsServices)
/*      */   {
/*  495 */     this.allowAdminWindowsServices = allowAdminWindowsServices;
/*      */   }
/*      */   
/*      */   public String getAllowAdminWindowsServices()
/*      */   {
/*  500 */     return this.allowAdminWindowsServices;
/*      */   }
/*      */   
/*      */   public String getAllowSUB()
/*      */   {
/*  505 */     return this.allowSUB;
/*      */   }
/*      */   
/*      */   public void setAllowSUB(String allowSUB)
/*      */   {
/*  510 */     this.allowSUB = allowSUB;
/*      */   }
/*      */   
/*      */   public String getAllowSPL()
/*      */   {
/*  515 */     return this.allowSPL;
/*      */   }
/*      */   
/*      */   public void setAllowSPL(String allowSPL)
/*      */   {
/*  520 */     this.allowSPL = allowSPL;
/*      */   }
/*      */   
/*      */   public String getAllowMSG()
/*      */   {
/*  525 */     return this.allowMSG;
/*      */   }
/*      */   
/*      */   public void setAllowMSG(String allowMSG)
/*      */   {
/*  530 */     this.allowMSG = allowMSG;
/*      */   }
/*      */   
/*      */   public String getAllowJOB()
/*      */   {
/*  535 */     return this.allowJOB;
/*      */   }
/*      */   
/*      */   public void setAllowJOB(String allowJOB)
/*      */   {
/*  540 */     this.allowJOB = allowJOB;
/*      */   }
/*      */   
/*      */   public String getAllowCMD()
/*      */   {
/*  545 */     return this.allowCMD;
/*      */   }
/*      */   
/*      */   public void setAllowCMD(String allowCMD)
/*      */   {
/*  550 */     this.allowCMD = allowCMD;
/*      */   }
/*      */   
/*      */   public String getAllowCSU()
/*      */   {
/*  555 */     return this.allowCSU;
/*      */   }
/*      */   
/*      */   public void setAllowCSU(String allowCSU)
/*      */   {
/*  560 */     this.allowCSU = allowCSU;
/*      */   }
/*      */   
/*      */   public String getAllowAL()
/*      */   {
/*  565 */     return this.allowAL;
/*      */   }
/*      */   
/*      */   public void setAllowAL(String allowAL)
/*      */   {
/*  570 */     this.allowAL = allowAL;
/*      */   }
/*      */   
/*      */   public String getAllowCS()
/*      */   {
/*  575 */     return this.allowCS;
/*      */   }
/*      */   
/*      */   public void setAllowCS(String allowCS)
/*      */   {
/*  580 */     this.allowCS = allowCS;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getAllowLL()
/*      */   {
/*  586 */     return this.allowLL;
/*      */   }
/*      */   
/*      */   public void setAllowLL(String allowLL)
/*      */   {
/*  591 */     this.allowLL = allowLL;
/*      */   }
/*      */   
/*      */   public String getAllowSC()
/*      */   {
/*  596 */     return this.allowSC;
/*      */   }
/*      */   
/*      */   public void setAllowSC(String allowSC)
/*      */   {
/*  601 */     this.allowSC = allowSC;
/*      */   }
/*      */   
/*      */   public String getAllowDT()
/*      */   {
/*  606 */     return this.allowDT;
/*      */   }
/*      */   
/*      */   public void setAllowDT(String allowDT)
/*      */   {
/*  611 */     this.allowDT = allowDT;
/*      */   }
/*      */   
/*      */   public String getAllowML()
/*      */   {
/*  616 */     return this.allowML;
/*      */   }
/*      */   
/*      */   public void setAllowML(String allowML)
/*      */   {
/*  621 */     this.allowML = allowML;
/*      */   }
/*      */   
/*      */   public String getAllowNA()
/*      */   {
/*  626 */     return this.allowNA;
/*      */   }
/*      */   
/*      */   public void setAllowNA(String allowNA)
/*      */   {
/*  631 */     this.allowNA = allowNA;
/*      */   }
/*      */   
/*      */   public String getAllowAS400() {
/*  635 */     return this.allowAS400;
/*      */   }
/*      */   
/*      */   public void setAllowAS400(String allowAS400)
/*      */   {
/*  640 */     this.allowAS400 = allowAS400;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDrilldown()
/*      */   {
/*  651 */     return this.drilldown;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setDrilldown()
/*      */   {
/*  658 */     System.out.println("setDrilldown is not called at all");
/*  659 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/*  661 */       String qry = "select value from AM_GLOBALCONFIG WHERE NAME='am.webclient.mgdrilldown'";
/*  662 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  663 */       if (rs.next()) {
/*  664 */         String value = rs.getString(1);
/*  665 */         this.drilldown = value;
/*      */       }
/*      */       else {
/*  668 */         this.drilldown = "false";
/*      */       }
/*  670 */       rs.close();
/*  671 */       System.out.println("The drilldown value in the action class==>" + this.drilldown);
/*      */     }
/*      */     catch (Exception exc) {
/*  674 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  680 */   private String domainName = null;
/*  681 */   private String domainController = null;
/*  682 */   private String domainUsername = null;
/*  683 */   private String domainPassword = null;
/*  684 */   private String domainUserRole = "OPERATOR";
/*  685 */   private String authType = null;
/*  686 */   private String domainValue = null;
/*  687 */   private String domainID = null;
/*  688 */   private String searchFilter = null;
/*  689 */   private String usergroupName = null;
/*  690 */   private String ldapdomainValue = null;
/*  691 */   private String ldapdomainName = null;
/*  692 */   private String ldapdomainController = null;
/*  693 */   private String ldapdomainUsername = null;
/*  694 */   private String ldapsearchFilter = null;
/*  695 */   private String ldapdomainPassword = null;
/*  696 */   private String ldapsearchBase = null;
/*  697 */   private int domainPort = 389;
/*  698 */   private String userGroupUsers = null;
/*  699 */   private String groupid = null;
/*  700 */   private String associatedUserGroups = null;
/*  701 */   private String domainAuthentication = "ACTIVE DIRECTORY";
/*  702 */   private String userGroupRole = "OPERATOR";
/*      */   
/*  704 */   private String newDomainName = null;
/*  705 */   private String newDomainController = null;
/*  706 */   private int newDomainPort = 389;
/*  707 */   private String newDomainService = null;
/*  708 */   private String newDomainAutoLogin = "false";
/*  709 */   private int newDomainPermission = 0;
/*      */   
/*      */   public void setUserGroupRole(String role) {
/*  712 */     this.userGroupRole = role;
/*      */   }
/*      */   
/*      */   public String getUserGroupRole() {
/*  716 */     return this.userGroupRole;
/*      */   }
/*      */   
/*      */   public void setNewDomainName(String domainname) {
/*  720 */     this.newDomainName = domainname;
/*      */   }
/*      */   
/*      */   public String getNewDomainName() {
/*  724 */     return this.newDomainName;
/*      */   }
/*      */   
/*      */   public void setNewDomainController(String controller) {
/*  728 */     this.newDomainController = controller;
/*      */   }
/*      */   
/*      */   public String getNewDomainController() {
/*  732 */     return this.newDomainController;
/*      */   }
/*      */   
/*      */   public void setNewDomainPort(int port) {
/*  736 */     this.newDomainPort = port;
/*      */   }
/*      */   
/*      */   public int getNewDomainPort() {
/*  740 */     return this.newDomainPort;
/*      */   }
/*      */   
/*      */   public void setNewDomainService(String service) {
/*  744 */     this.newDomainService = service;
/*      */   }
/*      */   
/*      */   public String getNewDomainService() {
/*  748 */     return this.newDomainService;
/*      */   }
/*      */   
/*      */   public void setNewDomainAutoLogin(String login) {
/*  752 */     this.newDomainAutoLogin = login;
/*      */   }
/*      */   
/*      */   public String getNewDomainAutoLogin() {
/*  756 */     return this.newDomainAutoLogin;
/*      */   }
/*      */   
/*      */   public void setNewDomainPermission(int permission) {
/*  760 */     this.newDomainPermission = permission;
/*      */   }
/*      */   
/*      */   public int getNewDomainPermission() {
/*  764 */     return this.newDomainPermission;
/*      */   }
/*      */   
/*      */   public void setDomainAuthentication(String auth) {
/*  768 */     this.domainAuthentication = auth;
/*      */   }
/*      */   
/*      */   public String getDomainAuthentication() {
/*  772 */     return this.domainAuthentication;
/*      */   }
/*      */   
/*      */   public void setAssociatedUserGroups(String groups) {
/*  776 */     this.associatedUserGroups = groups;
/*      */   }
/*      */   
/*      */   public String getAssociatedUserGroups() {
/*  780 */     return this.associatedUserGroups;
/*      */   }
/*      */   
/*      */   public void setGroupid(String groupid) {
/*  784 */     this.groupid = groupid;
/*      */   }
/*      */   
/*      */   public String getGroupid() {
/*  788 */     return this.groupid;
/*      */   }
/*      */   
/*      */   public void setUserGroupUsers(String users) {
/*  792 */     this.userGroupUsers = users;
/*      */   }
/*      */   
/*      */   public String getUserGroupUsers() {
/*  796 */     return this.userGroupUsers;
/*      */   }
/*      */   
/*      */   public void setLdapsearchBase(String searchbase)
/*      */   {
/*  801 */     this.ldapsearchBase = searchbase;
/*      */   }
/*      */   
/*      */   public String getLdapsearchBase() {
/*  805 */     return this.ldapsearchBase;
/*      */   }
/*      */   
/*      */   public void setLdapdomainValue(String domainvalue) {
/*  809 */     this.ldapdomainValue = domainvalue;
/*      */   }
/*      */   
/*      */   public String getLdapdomainValue() {
/*  813 */     return this.ldapdomainValue;
/*      */   }
/*      */   
/*      */   public void setDomainPort(int port) {
/*  817 */     this.domainPort = port;
/*      */   }
/*      */   
/*      */   public int getDomainPort() {
/*  821 */     return this.domainPort;
/*      */   }
/*      */   
/*      */   public void setLdapdomainName(String domainname) {
/*  825 */     this.ldapdomainName = domainname;
/*      */   }
/*      */   
/*      */   public String getLdapdomainName() {
/*  829 */     return this.ldapdomainName;
/*      */   }
/*      */   
/*      */   public void setLdapdomainController(String domaincontroller)
/*      */   {
/*  834 */     this.ldapdomainController = domaincontroller;
/*      */   }
/*      */   
/*      */   public String getLdapdomainController() {
/*  838 */     return this.ldapdomainController;
/*      */   }
/*      */   
/*      */   public void setLdapdomainUsername(String domainusername) {
/*  842 */     this.ldapdomainUsername = domainusername;
/*      */   }
/*      */   
/*      */   public String getLdapdomainUsername() {
/*  846 */     return this.ldapdomainUsername;
/*      */   }
/*      */   
/*      */   public void setLdapdomainPassword(String domainpassword) {
/*  850 */     this.ldapdomainPassword = domainpassword;
/*      */   }
/*      */   
/*      */   public String getLdapdomainPassword() {
/*  854 */     return this.ldapdomainPassword;
/*      */   }
/*      */   
/*      */   public void setLdapsearchFilter(String filter)
/*      */   {
/*  859 */     this.ldapsearchFilter = filter;
/*      */   }
/*      */   
/*      */   public String getLdapsearchFilter() {
/*  863 */     return this.ldapsearchFilter;
/*      */   }
/*      */   
/*      */   public String getUsergroupName()
/*      */   {
/*  868 */     return this.usergroupName;
/*      */   }
/*      */   
/*      */   public void setUsergroupName(String name)
/*      */   {
/*  873 */     this.usergroupName = name;
/*      */   }
/*      */   
/*      */   public String getDomainName()
/*      */   {
/*  878 */     return this.domainName;
/*      */   }
/*      */   
/*      */   public void setDomainName(String domainName)
/*      */   {
/*  883 */     this.domainName = domainName;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getDomainController()
/*      */   {
/*  889 */     return this.domainController;
/*      */   }
/*      */   
/*      */   public void setDomainController(String domainController)
/*      */   {
/*  894 */     this.domainController = domainController;
/*      */   }
/*      */   
/*      */   public String getDomainUsername()
/*      */   {
/*  899 */     return this.domainUsername;
/*      */   }
/*      */   
/*      */   public void setDomainUsername(String domainUsername)
/*      */   {
/*  904 */     this.domainUsername = domainUsername;
/*      */   }
/*      */   
/*      */   public String getDomainPassword()
/*      */   {
/*  909 */     return this.domainPassword;
/*      */   }
/*      */   
/*      */   public void setDomainPassword(String domainPassword)
/*      */   {
/*  914 */     this.domainPassword = domainPassword;
/*      */   }
/*      */   
/*      */   public String getDomainUserRole()
/*      */   {
/*  919 */     return this.domainUserRole;
/*      */   }
/*      */   
/*      */   public void setDomainUserRole(String domainUserRole)
/*      */   {
/*  924 */     this.domainUserRole = domainUserRole;
/*      */   }
/*      */   
/*      */   public String getAuthType() {
/*  928 */     return this.authType;
/*      */   }
/*      */   
/*      */   public void setAuthType(String authType)
/*      */   {
/*  933 */     this.authType = authType;
/*      */   }
/*      */   
/*      */   public ArrayList<Properties> getLdapdomainList() {
/*  937 */     ResultSet rs = null;
/*  938 */     ArrayList<Properties> list = new ArrayList();
/*      */     try {
/*  940 */       Properties temp1 = new Properties();
/*  941 */       temp1.setProperty("label", FormatUtil.getString("am.webclient.admintab.adduser.importad.select.domain"));
/*  942 */       temp1.setProperty("value", "select");
/*  943 */       list.add(temp1);
/*  944 */       Properties temp2 = new Properties();
/*  945 */       temp2.setProperty("label", FormatUtil.getString("am.webclient.admintab.adduser.importad.select.adddomain"));
/*  946 */       temp2.setProperty("value", "new");
/*  947 */       list.add(temp2);
/*  948 */       String qry = "select ID,DOMAINNAME,USERNAME,DNSHOST,PORT from AM_DOMAINCONTROLLERS where AUTHENTICATION='LDAP'";
/*  949 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  950 */       while (rs.next())
/*      */       {
/*  952 */         Properties p = new Properties();
/*  953 */         p.setProperty("label", rs.getString("DOMAINNAME"));
/*  954 */         String username = rs.getString("USERNAME") != null ? rs.getString("USERNAME") : "";
/*  955 */         p.setProperty("value", rs.getString("DNSHOST") + "##" + rs.getString("ID") + "##" + username + "##" + rs.getString("PORT"));
/*  956 */         list.add(p);
/*      */       }
/*      */     } catch (Exception ex) {
/*  959 */       ex.printStackTrace();
/*      */     } finally {
/*  961 */       if (rs != null) {
/*  962 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  965 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getDomainList()
/*      */   {
/*  970 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  971 */     ResultSet rs = null;
/*  972 */     ArrayList list = new ArrayList();
/*      */     try {
/*  974 */       Properties temp1 = new Properties();
/*  975 */       temp1.setProperty("label", FormatUtil.getString("am.webclient.admintab.adduser.importad.select.domain"));
/*  976 */       temp1.setProperty("value", "select");
/*  977 */       list.add(temp1);
/*  978 */       Properties temp2 = new Properties();
/*      */       
/*      */ 
/*      */ 
/*  982 */       String qry = "select ID,DOMAINNAME,USERNAME,DNSHOST,PORT,AUTHENTICATION,SSLENABLED from AM_DOMAINCONTROLLERS";
/*  983 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  984 */       while (rs.next())
/*      */       {
/*  986 */         Properties p = new Properties();
/*  987 */         p.setProperty("label", rs.getString("DOMAINNAME"));
/*  988 */         String username = rs.getString("USERNAME") != null ? rs.getString("USERNAME") : "";
/*  989 */         String sslenabled = rs.getInt("SSLENABLED") == 1 ? "true" : "false";
/*  990 */         p.setProperty("value", rs.getString("DNSHOST") + "##" + rs.getString("ID") + "##" + username + "##" + rs.getString("PORT") + "##" + rs.getString("AUTHENTICATION") + "##" + sslenabled);
/*  991 */         list.add(p);
/*      */       }
/*      */     }
/*      */     catch (Exception ee) {
/*  995 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/*  998 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1000 */     return list;
/*      */   }
/*      */   
/*      */   public void setDomainValue(String domainValue)
/*      */   {
/* 1005 */     this.domainValue = domainValue;
/*      */   }
/*      */   
/*      */   public String getDomainValue()
/*      */   {
/* 1010 */     return this.domainValue;
/*      */   }
/*      */   
/*      */   public String getDomainID()
/*      */   {
/* 1015 */     return this.domainID;
/*      */   }
/*      */   
/*      */   public void setDomainID(String domainID)
/*      */   {
/* 1020 */     this.domainID = domainID;
/*      */   }
/*      */   
/*      */   public String getSearchFilter()
/*      */   {
/* 1025 */     return this.searchFilter;
/*      */   }
/*      */   
/*      */   public void setSearchFilter(String searchFilter)
/*      */   {
/* 1030 */     this.searchFilter = searchFilter;
/*      */   }
/*      */   
/*      */   public String getAllowUpdateConfig()
/*      */   {
/* 1035 */     return this.allowUpdateConfig;
/*      */   }
/*      */   
/*      */   public void setAllowUpdateConfig(String allowUpdateConfig)
/*      */   {
/* 1040 */     this.allowUpdateConfig = allowUpdateConfig;
/*      */   }
/*      */   
/*      */   public String getAllowManageDB()
/*      */   {
/* 1045 */     return this.allowManageDB;
/*      */   }
/*      */   
/*      */   public void setAllowManageDB(String allowManageDB)
/*      */   {
/* 1050 */     this.allowManageDB = allowManageDB;
/*      */   }
/*      */   
/*      */   public String getAllowOperatorEditTabs() {
/* 1054 */     return this.allowOperatorEditTabs;
/*      */   }
/*      */   
/*      */   public void setAllowOperatorEditTabs(String allowOperatorEditTabs) {
/* 1058 */     this.allowOperatorEditTabs = allowOperatorEditTabs;
/*      */   }
/*      */   
/*      */   public void reset(ActionMapping mapping, HttpServletRequest request) {
/* 1062 */     if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))) {
/* 1063 */       if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 1064 */         this.userRole = "DELEGATEDADMIN";
/*      */       } else {
/* 1066 */         this.userRole = "ADMIN";
/*      */       }
/* 1068 */     } else if (request.isUserInRole("OPERATOR")) {
/* 1069 */       this.userRole = "OPERATOR";
/* 1070 */     } else if (request.isUserInRole("USERS")) {
/* 1071 */       this.userRole = "USERS";
/* 1072 */     } else if (request.isUserInRole("MANAGER")) {
/* 1073 */       this.userRole = "MANAGER";
/* 1074 */     } else if (request.isUserInRole("DEMO")) {
/* 1075 */       this.userRole = "DEMO";
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\UserConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */