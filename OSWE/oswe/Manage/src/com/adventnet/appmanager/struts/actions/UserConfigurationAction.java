/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.personalize.AMPersonalize;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.struts.form.UserConfiguration;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.util.UserSessionHandler;
/*      */ import com.adventnet.appmanager.utils.client.UserConfigurationUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ 
/*      */ public final class UserConfigurationAction extends DispatchAction
/*      */ {
/*   57 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public ActionForward createUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*   63 */       UserConfiguration uc = (UserConfiguration)form;
/*   64 */       UserSessionHandler ush = UserSessionHandler.getInstance();
/*   65 */       String[] selectedMonitor = request.getParameterValues("select");
/*   66 */       String[] userGroups = new String[0];
/*   67 */       if ((uc.getAssociatedUserGroups() != null) && (uc.getAssociatedUserGroups().trim().length() > 0)) {
/*   68 */         userGroups = uc.getAssociatedUserGroups().split(",");
/*      */       }
/*   70 */       String method = "createUser";
/*   71 */       ActionMessages messages = new ActionMessages();
/*   72 */       ActionErrors errors = new ActionErrors();
/*   73 */       String updateChk = uc.getUpdateChk();
/*   74 */       boolean policyEnabledStatus = false;
/*   75 */       String restrictedAdmin = "1";
/*   76 */       if ("on".equals(uc.getDelegatedAdmin())) {
/*   77 */         restrictedAdmin = "0";
/*      */       }
/*   79 */       if ((uc.getUserName() != null) && (uc.getUserName().length() > 50))
/*      */       {
/*   81 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.creation.namelengthexceed.text", uc.getUserName()));
/*   82 */         saveMessages(request, messages);
/*   83 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*   87 */       String result = UserConfigurationUtil.CheckNewUserAvailability();
/*   88 */       if (!result.equals("true")) {
/*   89 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(result));
/*   90 */         saveErrors(request, errors);
/*   91 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*   95 */       if (UserConfigurationUtil.isUserExist(uc.getUserName(), null).booleanValue()) {
/*   96 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.creation.nameexists", uc.getUserName()));
/*   97 */         saveMessages(request, messages);
/*   98 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  103 */       String err = "noerror";
/*  104 */       String userName = uc.getUserName();
/*  105 */       String confirmPassword = request.getParameter("confirmpassword");
/*  106 */       String password = uc.getPassword();
/*      */       
/*      */ 
/*  109 */       if ((uc.getAuthType() != null) && (uc.getAuthType().equals("ADAuthentication")))
/*      */       {
/*  111 */         return createADUser(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  116 */       policyEnabledStatus = ush.getPwdPolicyEnabledStatus();
/*  117 */       if (policyEnabledStatus)
/*      */       {
/*  119 */         err = validatePassword(userName, password, confirmPassword, method, updateChk);
/*      */       }
/*      */       
/*  122 */       AMLog.debug("User Management Error" + err);
/*  123 */       String data = null;
/*  124 */       if ((!err.equals("")) && (err != null) && (err.equals("noerror"))) {
/*      */         int tempUserID;
/*      */         int tempUserID;
/*  127 */         if (EnterpriseUtil.isManagedServer())
/*      */         {
/*      */ 
/*  130 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable", "USERID < 10000000");
/*      */         } else {
/*  132 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable");
/*      */         }
/*  134 */         String fileName = null;
/*      */         try
/*      */         {
/*  137 */           String home = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();
/*      */           
/*  139 */           InputStream stream = null;
/*  140 */           FormFile imgfile = uc.getTheFile();
/*  141 */           fileName = imgfile.getFileName();
/*  142 */           if ((imgfile != null) && (fileName != null) && (fileName.length() > 0))
/*      */           {
/*  144 */             stream = imgfile.getInputStream();
/*      */             
/*  146 */             String uploadDir = home + File.separator + "working" + File.separator + "users" + File.separator + uc.getUserName() + File.separator;
/*  147 */             Boolean dirCreated = Boolean.valueOf(new File(uploadDir).mkdir());
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  157 */             OutputStream bos = new FileOutputStream(uploadDir + tempUserID + ".jpg");
/*  158 */             int bytesRead = 0;
/*  159 */             byte[] buffer = new byte[' '];
/*  160 */             while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/*  161 */               bos.write(buffer, 0, bytesRead);
/*      */             }
/*  163 */             bos.close();
/*  164 */             data = "The file has been written to \"" + uploadDir + tempUserID + ".jpg\"";
/*      */             
/*  166 */             stream.close();
/*  167 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.success", fileName));
/*  168 */             saveMessages(request, messages);
/*      */           }
/*      */         }
/*      */         catch (FileNotFoundException fnfe) {
/*  172 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/*  173 */           saveMessages(request, messages);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  178 */         Properties userProps = new Properties();
/*  179 */         userProps.setProperty("userName", uc.getUserName());
/*  180 */         userProps.setProperty("password", uc.getPassword());
/*  181 */         userProps.setProperty("email", uc.getEmail());
/*  182 */         userProps.setProperty("description", uc.getDescription());
/*  183 */         userProps.setProperty("restrictedAdmin", restrictedAdmin);
/*  184 */         if (uc.getGroups() != null) {
/*  185 */           StringBuilder builder = new StringBuilder();
/*  186 */           for (int i = 0; i < uc.getGroups().length; i++) {
/*  187 */             builder.append(uc.getGroups()[i]);
/*  188 */             if (i != uc.getGroups().length - 1) {
/*  189 */               builder.append(",");
/*      */             }
/*      */           }
/*  192 */           userProps.setProperty("role", builder.toString());
/*      */         }
/*  194 */         if (userGroups != null) {
/*  195 */           userProps.setProperty("usergroupId", uc.getAssociatedUserGroups());
/*      */         }
/*  197 */         int newUserid = UserConfigurationUtil.insertUserDetails(userProps);
/*      */         
/*      */ 
/*      */ 
/*  201 */         UserConfigurationUtil.updateGroups(uc.getUserName(), uc.getGroups());
/*  202 */         ArrayList<String> monitorGroupIds = new ArrayList();
/*  203 */         if (selectedMonitor != null) {
/*  204 */           monitorGroupIds = new ArrayList(Arrays.asList(selectedMonitor));
/*      */         }
/*      */         try {
/*  207 */           if (userGroups != null) {
/*  208 */             ArrayList<String> mgids = UserConfigurationUtil.updateOwnerUserGroups(userGroups, newUserid, false);
/*  209 */             for (String id : mgids) {
/*  210 */               if (!monitorGroupIds.contains(id)) {
/*  211 */                 monitorGroupIds.add(id);
/*      */               }
/*      */             }
/*      */           }
/*      */         } catch (Exception ex) {
/*  216 */           ex.printStackTrace();
/*      */         }
/*      */         try
/*      */         {
/*  220 */           if (monitorGroupIds.size() > 0) {
/*  221 */             UserConfigurationUtil.updateMonitorGroupDetails(uc.getUserName(), null, request.getParameter("groups"), monitorGroupIds);
/*      */           }
/*      */         } catch (Exception ex) {
/*  224 */           ex.printStackTrace();
/*      */         }
/*  226 */         UserConfigurationUtil.updateUserPrivileges(request, uc.getUserName());
/*  227 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.creation.success"));
/*  228 */         saveMessages(request, messages);
/*      */         
/*      */ 
/*  231 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */         {
/*  233 */           String ownerId = DBUtil.getSingleDataFromDB("select USERID from AM_UserPasswordTable where  USERNAME='" + uc.getUserName() + "'");
/*  234 */           if ((ownerId != null) && (RestrictedUsersViewUtil.isRestrictedRole(ownerId)))
/*      */           {
/*  236 */             AMLog.debug("[UserConfigurationAction::(createUser)]ruser(s) : " + ownerId);
/*  237 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(ownerId);
/*  238 */             RestrictedUsersViewUtil.deleteFromAMUserResourcesTable(ownerId);
/*  239 */             RestrictedUsersViewUtil.insertAllResourcesOfOwner(ownerId);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  244 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*  248 */       String str = "/showTile.do?TileName=Tile.usergroups.Conf&haid=null&errtype=" + err;
/*  249 */       return new ActionForward(str);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  253 */       ee.printStackTrace();
/*      */     }
/*  255 */     return new ActionForward("/showTile.do?TileName=Tile.usergroups.Conf&haid=null");
/*      */   }
/*      */   
/*      */   public ActionForward getGroupUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  261 */       Map domainDetails = new HashMap();
/*  262 */       String domainName = null;
/*  263 */       String domainID = request.getParameter("domainID");
/*  264 */       String domainPassword = request.getParameter("ldapdomainPassword");
/*  265 */       String searchBase = request.getParameter("ldapsearchBase");
/*  266 */       String authentication = null;
/*      */       
/*  268 */       String[] userGroups = request.getParameterValues("adUserSelect");
/*      */       
/*  270 */       PrintWriter out = response.getWriter();
/*      */       
/*  272 */       domainDetails.put("domainId", domainID);
/*  273 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  276 */         rs = AMConnectionPool.executeQueryStmt("select * from AM_DOMAINCONTROLLERS where ID=" + domainID);
/*  277 */         if (rs.next()) {
/*  278 */           domainName = rs.getString("DOMAINNAME");
/*  279 */           domainDetails.put("domainName", domainName);
/*  280 */           domainDetails.put("domainController", rs.getString("DNSHOST"));
/*  281 */           domainDetails.put("domainUsername", rs.getString("USERNAME"));
/*  282 */           domainDetails.put("domainPassword", domainPassword);
/*  283 */           String sslenabled = rs.getInt("SSLENABLED") == 1 ? "true" : "false";
/*  284 */           domainDetails.put("sslenabled", sslenabled);
/*  285 */           ADAuthenticationUtil.setPortNumber(rs.getInt("PORT"));
/*  286 */           authentication = rs.getString("AUTHENTICATION");
/*  287 */           if ("LDAP".equalsIgnoreCase(authentication)) {
/*  288 */             domainDetails.put("server", "ldap");
/*      */           } else {
/*  290 */             domainDetails.put("server", "ad");
/*      */           }
/*      */           
/*  293 */           domainDetails.put("searchbase", searchBase);
/*      */         }
/*      */       } catch (Exception ex) {
/*  296 */         ex.printStackTrace();
/*      */       } finally {
/*  298 */         if (rs != null) {
/*  299 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*  302 */       domainDetails.put("importdata", "usergroup");
/*  303 */       ArrayList arr = ADAuthenticationUtil.fetchADUserList(domainDetails, userGroups);
/*  304 */       StringBuilder optionList = new StringBuilder();
/*      */       
/*      */ 
/*  307 */       for (int i = 0; i < arr.size(); i++) {
/*  308 */         StringBuilder groupUsers = new StringBuilder();
/*  309 */         Hashtable table = (Hashtable)arr.get(i);
/*  310 */         String displayName = (String)table.get("displayName");
/*  311 */         String usergroupSAMNAME = (String)table.get("sAMAccountName");
/*  312 */         int attLength = table.get("groupMembersAttLength") != null ? ((Integer)table.get("groupMembersAttLength")).intValue() : 0;
/*      */         
/*      */ 
/*  315 */         for (int j = 0; j < attLength; j++) {
/*  316 */           if (table.get("groupMembers" + j) != null) {
/*  317 */             ArrayList memberList = (ArrayList)table.get("groupMembers" + j);
/*  318 */             for (int z = 0; z < memberList.size(); z++) {
/*  319 */               String distinguishedName = (String)memberList.get(z);
/*  320 */               groupUsers.append("(").append(distinguishedName.split(",", 2)[0]).append(")");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  325 */         String groupTokenId = (String)table.get("grouptokenid");
/*  326 */         if ((groupTokenId != null) && (groupTokenId.trim().length() > 0)) {
/*  327 */           String userGroupid = (String)domainDetails.get("userGroupid");
/*  328 */           groupUsers.append("(").append(userGroupid).append("=").append(groupTokenId).append(")");
/*      */         }
/*  330 */         if (groupUsers.toString().trim().length() > 0) {
/*  331 */           groupUsers.append(")");
/*  332 */           domainDetails.put("groupSearchFilter", "(|" + groupUsers.toString());
/*  333 */           ArrayList<Hashtable<String, String>> users = ADAuthenticationUtil.usersInGroup(domainDetails);
/*  334 */           int usersLength = users.size();
/*  335 */           if (usersLength > 0) {
/*  336 */             optionList.append("<optgroup label=\"" + displayName + "\">");
/*      */           }
/*  338 */           for (int u = 0; u < usersLength; u++) {
/*  339 */             Hashtable user = (Hashtable)users.get(u);
/*  340 */             String distinguishedname = (String)user.get("dn");
/*  341 */             String dispname = (String)user.get("displayName");
/*  342 */             String sAMname = (String)user.get("sAMAccountName");
/*  343 */             String existinguser = (String)user.get("existingValue");
/*  344 */             String disabled = "";
/*  345 */             if ("added".equalsIgnoreCase(existinguser)) {
/*  346 */               dispname = (String)user.get("sAMAccountName");
/*  347 */               disabled = "disabled";
/*  348 */               sAMname = FormatUtil.getString("am.webclient.admintab.user.imported.text");
/*  349 */             } else if ("valueAdded".equalsIgnoreCase(existinguser)) {
/*  350 */               dispname = (String)user.get("sAMAccountName");
/*  351 */               disabled = "disabled";
/*  352 */               sAMname = FormatUtil.getString("am.webclient.useradministration.usergroup.imported.anotherdomain.text");
/*  353 */             } else if ("nameExists".equalsIgnoreCase(existinguser)) {
/*  354 */               dispname = (String)user.get("sAMAccountName");
/*  355 */               disabled = "disabled";
/*  356 */               sAMname = FormatUtil.getString("am.webclient.useradministration.user.name.exists");
/*      */             }
/*      */             
/*      */ 
/*  360 */             optionList.append("<option  " + disabled + " value=\"" + usergroupSAMNAME + "##" + sAMname + "##" + distinguishedname + "\">" + dispname + "&nbsp;(" + sAMname + ")</option>");
/*      */           }
/*  362 */           if (optionList.toString().length() > 0) {
/*  363 */             optionList.append("</optgroup>");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  369 */       out.println(optionList.toString());
/*  370 */       out.flush();
/*      */     } catch (Exception ex) {
/*  372 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  376 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward createLDAPUserGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  382 */       String[] userGroups = request.getParameterValues("adUserSelect");
/*  383 */       String[] monitorGroups = request.getParameterValues("selectedGroup");
/*  384 */       String domainID = request.getParameter("domainID");
/*  385 */       String domainName = request.getParameter("domainName");
/*  386 */       String domainRole = request.getParameter("domainUserGroupRole");
/*  387 */       String delegatedAdmin = request.getParameter("isDelegatedAdmin");
/*  388 */       if ((EnterpriseUtil.isAdminServer()) && ("admin".equalsIgnoreCase(domainRole)))
/*      */       {
/*  390 */         domainRole = "ENTERPRISEADMIN";
/*      */       }
/*  392 */       String[] users = request.getParameterValues("multipleSelect");
/*  393 */       HashMap<String, ArrayList<Integer>> userMapping = new HashMap();
/*  394 */       if (users != null) {
/*  395 */         for (String user : users) {
/*  396 */           String[] userList = user.split("##");
/*  397 */           HashMap<String, String> userDetails = new HashMap();
/*  398 */           userDetails.put("username", userList[1]);
/*  399 */           userDetails.put("domainid", domainID);
/*  400 */           userDetails.put("dn", userList[2]);
/*  401 */           userDetails.put("role", domainRole);
/*  402 */           String userGroupName = userList[0];
/*  403 */           String restrictedAdmin = "1";
/*      */           
/*  405 */           if ("on".equals(delegatedAdmin)) {
/*  406 */             restrictedAdmin = "0";
/*      */           }
/*  408 */           userDetails.put("restrictedAdmin", restrictedAdmin);
/*  409 */           int userid = UserConfigurationUtil.createDomainUser(userDetails);
/*  410 */           if (userMapping.get(userGroupName) != null) {
/*  411 */             ArrayList arr = (ArrayList)userMapping.get(userGroupName);
/*  412 */             arr.add(Integer.valueOf(userid));
/*      */           } else {
/*  414 */             ArrayList arr = new ArrayList();
/*  415 */             arr.add(Integer.valueOf(userid));
/*  416 */             userMapping.put(userGroupName, arr);
/*      */           }
/*      */         }
/*      */       }
/*  420 */       for (String usergroup : userGroups) {
/*  421 */         int tempGroupid = DBQueryUtil.getIncrementedID("GROUPID", "AM_USERGROUP_CONFIG");
/*  422 */         String mappingQuery = "insert into AM_USERGROUP_CONFIG (GROUPID,GROUPNAME) values (" + tempGroupid + ",'" + usergroup + "')";
/*  423 */         AMConnectionPool.executeUpdateStmt(mappingQuery);
/*  424 */         String domainmapping = "insert into AM_DOMAINUSERGROUP_MAPPING values(" + tempGroupid + "," + domainID + ")";
/*  425 */         AMConnectionPool.executeUpdateStmt(domainmapping);
/*      */         
/*  427 */         HashMap<String, String> userGroupDetails = new HashMap();
/*  428 */         userGroupDetails.put("usergroupName", usergroup);
/*  429 */         userGroupDetails.put("domainID", domainID);
/*      */         
/*  431 */         if (monitorGroups != null) { String monitorgroup;
/*  432 */           Iterator i$; for (monitorgroup : monitorGroups) {
/*  433 */             String groupMapping = "insert into AM_USERGROUP_MAPPING values (" + tempGroupid + "," + monitorgroup + ")";
/*      */             try {
/*  435 */               AMConnectionPool.executeUpdateStmt(groupMapping);
/*      */             } catch (Exception ex) {
/*  437 */               ex.printStackTrace();
/*      */             }
/*  439 */             if (userMapping.get(usergroup) != null) {
/*  440 */               for (i$ = ((ArrayList)userMapping.get(usergroup)).iterator(); i$.hasNext();) { int userid = ((Integer)i$.next()).intValue();
/*  441 */                 String userquery = "insert into AM_HOLISTICAPPLICATION_OWNERS values (" + monitorgroup + "," + userid + ")";
/*  442 */                 AMConnectionPool.executeUpdateStmt(userquery);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  447 */         if (userMapping.get(usergroup) != null) {
/*  448 */           StringBuilder builder = new StringBuilder();
/*  449 */           for (Iterator i$ = ((ArrayList)userMapping.get(usergroup)).iterator(); i$.hasNext();) { int userid = ((Integer)i$.next()).intValue();
/*  450 */             String userquery = "insert into AM_USERGROUP_OWNERS values (" + tempGroupid + "," + userid + ")";
/*  451 */             AMConnectionPool.executeUpdateStmt(userquery);
/*  452 */             builder.append(userid).append(",");
/*      */           }
/*  454 */           if (builder.length() > 0) {
/*  455 */             String temp = builder.substring(0, builder.length() - 1);
/*  456 */             userGroupDetails.put("userGroupUsers", temp.toString());
/*      */           }
/*      */         }
/*      */         
/*  460 */         if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  461 */           userGroupDetails.put("aamUsergroupId", String.valueOf(tempGroupid));
/*  462 */           MASSyncUtil.pushUserGroupDetailsToMAS(userGroupDetails, "add");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  468 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  471 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward createUserGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  477 */       UserConfiguration uc = (UserConfiguration)form;
/*  478 */       String groupname = uc.getUsergroupName();
/*  479 */       String[] selectedMonitorGroup = request.getParameterValues("select");
/*  480 */       String[] users = new String[0];
/*  481 */       if ((uc.getUserGroupUsers() != null) && (uc.getUserGroupUsers().trim().length() > 0)) {
/*  482 */         users = uc.getUserGroupUsers().split(",");
/*      */       }
/*  484 */       ActionMessages messages = new ActionMessages();
/*  485 */       request.setAttribute("showTab", "usergroup");
/*  486 */       if (groupname != null) {
/*  487 */         boolean isGroupNameExists = UserConfigurationUtil.checkDuplicateEntry("AM_USERGROUP_CONFIG", "GROUPNAME", groupname, "");
/*  488 */         if (isGroupNameExists) {
/*  489 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.useradministration.usergroup.duplicate.message.text", groupname));
/*  490 */           saveMessages(request, messages);
/*  491 */           return showUsers(mapping, form, request, response);
/*      */         }
/*      */       }
/*      */       
/*  495 */       int tempGroupid = DBQueryUtil.getIncrementedID("GROUPID", "AM_USERGROUP_CONFIG");
/*  496 */       String query = "insert into AM_USERGROUP_CONFIG (GROUPID,GROUPNAME) values (" + tempGroupid + ",'" + groupname + "')";
/*  497 */       AMConnectionPool.executeUpdateStmt(query);
/*  498 */       if (selectedMonitorGroup != null) {
/*  499 */         for (String monitorgroup : selectedMonitorGroup) {
/*  500 */           String mappingQuery = "insert into AM_USERGROUP_MAPPING values (" + tempGroupid + "," + monitorgroup + ")";
/*      */           try {
/*  502 */             AMConnectionPool.executeUpdateStmt(mappingQuery);
/*      */           } catch (Exception ex) {
/*  504 */             ex.printStackTrace();
/*      */           }
/*  506 */           for (String userid : users) {
/*  507 */             String userquery = "insert into AM_HOLISTICAPPLICATION_OWNERS values (" + monitorgroup + "," + userid + ")";
/*      */             try {
/*  509 */               AMConnectionPool.executeUpdateStmt(userquery);
/*      */             } catch (Exception ex) {
/*  511 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  517 */       for (String userid : users) {
/*  518 */         String userquery = "insert into AM_USERGROUP_OWNERS values (" + tempGroupid + "," + userid + ")";
/*      */         try {
/*  520 */           AMConnectionPool.executeUpdateStmt(userquery);
/*      */         } catch (Exception ex) {
/*  522 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*  525 */       UserConfigurationUtil.updateUserPrivileges(request, null);
/*      */       
/*      */ 
/*  528 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate)) {
/*      */         try
/*      */         {
/*  531 */           if (users.length > 0)
/*      */           {
/*  533 */             List<String> tempUList = new ArrayList();
/*  534 */             AMLog.debug("[<UserConfigurationAction>:<createUserGroup>] going to update am_userresourcestable...");
/*  535 */             for (String userid : users)
/*      */             {
/*  537 */               if ((userid != null) && (RestrictedUsersViewUtil.isRestrictedRole(userid)))
/*      */               {
/*  539 */                 tempUList.add(userid);
/*      */               }
/*      */             }
/*  542 */             if (!tempUList.isEmpty())
/*      */             {
/*  544 */               AMLog.debug("[UserConfigurationAction::(createUserGroup)]ruser(s) : " + tempUList);
/*  545 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(tempUList);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  551 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  556 */       if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  557 */         HashMap<String, String> userGroupDetails = new HashMap();
/*  558 */         userGroupDetails.put("usergroupName", groupname);
/*  559 */         userGroupDetails.put("userGroupUsers", uc.getUserGroupUsers());
/*  560 */         userGroupDetails.put("aamUsergroupId", String.valueOf(tempGroupid));
/*  561 */         MASSyncUtil.pushUserGroupDetailsToMAS(userGroupDetails, "add");
/*      */       }
/*  563 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.useradministration.usergroup.creation.success"));
/*  564 */       saveMessages(request, messages);
/*      */     } catch (Exception ex) {
/*  566 */       ex.printStackTrace();
/*      */     }
/*  568 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String updatepwdCheck(String userName, String password, String confirmPassword, String method)
/*      */   {
/*  577 */     boolean state = true;boolean samepwd = true;
/*  578 */     String errType = "noerror";
/*  579 */     int pwdLen = password.length();
/*  580 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*      */     
/*  582 */     if ((pwdLen < 8) || (pwdLen > 255))
/*      */     {
/*  584 */       errType = "pwdlen";
/*  585 */       return errType;
/*      */     }
/*  587 */     if ((password == null) || (password.equals("")))
/*      */     {
/*  589 */       errType = "pwdempty";
/*  590 */       return errType;
/*      */     }
/*  592 */     if ((confirmPassword == null) || (confirmPassword.equals("")))
/*      */     {
/*  594 */       errType = "confirmpwdempty";
/*  595 */       return errType;
/*      */     }
/*  597 */     if (!password.equals(confirmPassword))
/*      */     {
/*  599 */       errType = "pwdnotequal";
/*  600 */       return errType;
/*      */     }
/*  602 */     state = ush.checkUserNameAsPwd(userName, password);
/*  603 */     if (!state)
/*      */     {
/*  605 */       errType = "sameasuser";
/*  606 */       return errType;
/*      */     }
/*  608 */     state = ush.checkwithRegEx(password);
/*  609 */     if (!state)
/*      */     {
/*  611 */       errType = "notRegEx";
/*  612 */       return errType;
/*      */     }
/*      */     
/*  615 */     if (method.equals("updateUser"))
/*      */     {
/*  617 */       state = ush.checkForPwdHistory(userName, password);
/*      */       
/*  619 */       if (!state)
/*      */       {
/*  621 */         errType = "pwdinhistory";
/*  622 */         return errType;
/*      */       }
/*      */     }
/*      */     
/*  626 */     return errType;
/*      */   }
/*      */   
/*      */ 
/*      */   public String validatePassword(String userName, String password, String confirmPassword, String method, String updateChk)
/*      */   {
/*  632 */     String errType = "noerror";
/*  633 */     if ((userName == null) || (userName.equals("")))
/*      */     {
/*  635 */       errType = "userNameempty";
/*  636 */       return errType;
/*      */     }
/*      */     
/*  639 */     if ((method != null) && (!method.equals("")) && (method.equals("createUser")))
/*      */     {
/*  641 */       errType = updatepwdCheck(userName, password, confirmPassword, method);
/*  642 */       return errType;
/*      */     }
/*      */     
/*  645 */     if ((method != null) && (!method.equals("")) && (method.equals("updateUser")))
/*      */     {
/*      */ 
/*  648 */       if ((updateChk != null) && (!updateChk.equals("false")))
/*      */       {
/*  650 */         errType = updatepwdCheck(userName, password, confirmPassword, method);
/*  651 */         return errType;
/*      */       }
/*      */     }
/*  654 */     return errType;
/*      */   }
/*      */   
/*      */   public ActionForward updateUserGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/*  659 */       UserConfiguration uc = (UserConfiguration)form;
/*  660 */       String groupid = uc.getGroupid();
/*  661 */       String groupName = uc.getUsergroupName();
/*      */       
/*  663 */       String[] selectedMonitorGroup = request.getParameterValues("select");
/*  664 */       String[] users = new String[0];
/*  665 */       String[] domains = new String[0];
/*  666 */       String domainUserGroup = uc.getLdapdomainName();
/*  667 */       if ((uc.getUserGroupUsers() != null) && (uc.getUserGroupUsers().trim().length() > 0)) {
/*  668 */         users = uc.getUserGroupUsers().split(",");
/*      */       }
/*  670 */       if ((uc.getLdapdomainValue() != null) && (uc.getLdapdomainValue().trim().length() > 0)) {
/*  671 */         domains = uc.getLdapdomainValue().split(",");
/*      */       }
/*      */       
/*  674 */       ArrayList<String> ownersToBeUpdated = new ArrayList();
/*  675 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*  677 */         ownersToBeUpdated = DBUtil.getRowsForSingleColumn("select USERID from AM_USERGROUP_OWNERS WHERE GROUPID = " + groupid);
/*      */       }
/*      */       
/*      */ 
/*  681 */       if (DBQueryUtil.isPgsql()) {
/*  682 */         AMConnectionPool.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS using AM_USERGROUP_OWNERS,AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and  AM_USERGROUP_OWNERS.GROUPID=AM_USERGROUP_MAPPING.GROUPID  and AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_USERGROUP_OWNERS.GROUPID =" + groupid);
/*      */       } else {
/*  684 */         AMConnectionPool.executeUpdateStmt("delete AM_HOLISTICAPPLICATION_OWNERS from   AM_HOLISTICAPPLICATION_OWNERS, AM_USERGROUP_OWNERS, AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and  AM_USERGROUP_OWNERS.GROUPID=AM_USERGROUP_MAPPING.GROUPID  and AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_USERGROUP_OWNERS.GROUPID =" + groupid);
/*      */       }
/*      */       
/*  687 */       AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_MAPPING where GROUPID=" + groupid);
/*  688 */       AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_OWNERS where GROUPID=" + groupid);
/*      */       
/*  690 */       PreparedStatement ps = null;
/*      */       try {
/*  692 */         if ((groupName != null) && (groupName.trim().length() > 0)) {
/*  693 */           String groupRole = uc.getUserGroupRole();
/*  694 */           ps = AMConnectionPool.getConnection().prepareStatement("update AM_USERGROUP_CONFIG set GROUPNAME=?,USERLOGINROLE=? where GROUPID=?");
/*  695 */           ps.setString(1, groupName.trim());
/*  696 */           ps.setString(2, groupRole);
/*  697 */           ps.setString(3, groupid);
/*  698 */           ps.executeUpdate();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  705 */         if (ps != null) {
/*      */           try {
/*  707 */             ps.close();
/*      */           }
/*      */           catch (Exception ex) {}
/*      */         }
/*      */         
/*      */ 
/*  713 */         if (selectedMonitorGroup == null) {
/*      */           break label663;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  702 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  705 */         if (ps != null) {
/*      */           try {
/*  707 */             ps.close();
/*      */           }
/*      */           catch (Exception ex) {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  714 */       for (String monitorgroup : selectedMonitorGroup) {
/*  715 */         String mappingQuery = "insert into AM_USERGROUP_MAPPING values (" + groupid + "," + monitorgroup + ")";
/*      */         try {
/*  717 */           AMConnectionPool.executeUpdateStmt(mappingQuery);
/*      */         } catch (Exception ex) {
/*  719 */           ex.printStackTrace();
/*      */         }
/*  721 */         for (String userid : users) {
/*  722 */           String userquery = "insert into AM_HOLISTICAPPLICATION_OWNERS values (" + monitorgroup + "," + userid + ")";
/*      */           try {
/*  724 */             AMConnectionPool.executeUpdateStmt(userquery);
/*      */             
/*  726 */             if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */             {
/*      */               try
/*      */               {
/*  730 */                 if ((RestrictedUsersViewUtil.isRestrictedRole(userid)) && (!ownersToBeUpdated.contains(userid)))
/*      */                 {
/*  732 */                   ownersToBeUpdated.add(userid);
/*      */                 }
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  737 */                 e1.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  743 */             ex.printStackTrace();
/*      */           }
/*      */           
/*      */ 
/*  747 */           refreshMonitorGroupsForUser(userid);
/*      */         }
/*      */       }
/*      */       
/*      */       label663:
/*  752 */       if ("true".equals(domainUserGroup)) {
/*  753 */         String userGroupRole = uc.getUserGroupRole();
/*  754 */         AMConnectionPool.executeUpdateStmt("update AM_USERGROUP_CONFIG set USERLOGINROLE = '" + userGroupRole + "' where GROUPID=" + groupid);
/*  755 */         AMConnectionPool.executeUpdateStmt("delete from AM_DOMAINUSERGROUP_MAPPING where GROUPID=" + groupid);
/*  756 */         for (String domainid : domains) {
/*  757 */           String userquery = "insert into AM_DOMAINUSERGROUP_MAPPING (GROUPID,DOMAINID) values (" + groupid + "," + domainid + ")";
/*      */           try {
/*  759 */             AMConnectionPool.executeUpdateStmt(userquery);
/*      */           } catch (Exception ex) {
/*  761 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  767 */       for (String userid : users) {
/*  768 */         String userquery = "insert into AM_USERGROUP_OWNERS values (" + groupid + "," + userid + ")";
/*      */         try {
/*  770 */           AMConnectionPool.executeUpdateStmt(userquery);
/*      */         } catch (Exception ex) {
/*  772 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*  775 */       UserConfigurationUtil.updateUserPrivileges(request, null);
/*      */       
/*  777 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*      */         try
/*      */         {
/*      */ 
/*  782 */           if (!ownersToBeUpdated.isEmpty())
/*      */           {
/*  784 */             AMLog.debug("[UserConfigurationAction::(updateUserGroup)]ruser(s) : " + ownersToBeUpdated);
/*  785 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(ownersToBeUpdated);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  790 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  794 */       AMConnectionPool.executeUpdateStmt("update AM_USERGROUP_CONFIG set GROUPNAME='" + request.getParameter("usergroupName") + "' where GROUPID=" + groupid);
/*      */       
/*      */ 
/*  797 */       if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  798 */         HashMap<String, String> userGroupDetails = new HashMap();
/*  799 */         userGroupDetails.put("userGroupId", groupid);
/*  800 */         if ((uc.getUserGroupUsers() != null) && (uc.getUserGroupUsers().trim().length() > 0)) {
/*  801 */           userGroupDetails.put("userGroupUsers", uc.getUserGroupUsers());
/*      */         }
/*  803 */         if (("true".equals(domainUserGroup)) && 
/*  804 */           (uc.getLdapdomainValue() != null) && (uc.getLdapdomainValue().trim().length() > 0)) {
/*  805 */           userGroupDetails.put("domainusergroup", "true");
/*  806 */           userGroupDetails.put("usergroupdomains", uc.getLdapdomainValue());
/*  807 */           userGroupDetails.put("usergrouprole", uc.getUserGroupRole());
/*      */         }
/*      */         
/*  810 */         String usergroupName = request.getParameter("usergroupName");
/*  811 */         if ((usergroupName != null) && (usergroupName.trim().length() > 0)) {
/*  812 */           userGroupDetails.put("usergroupName", usergroupName);
/*      */         }
/*  814 */         MASSyncUtil.pushUserGroupDetailsToMAS(userGroupDetails, "update");
/*      */       }
/*      */     } catch (Exception ex) {
/*  817 */       ex.printStackTrace();
/*      */     }
/*  819 */     request.setAttribute("showTab", "usergroup");
/*  820 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void refreshMonitorGroupsForUser(String userId)
/*      */   {
/*      */     try
/*      */     {
/*  829 */       String missingGroupForMappingQry = "select AM_USERGROUP_MAPPING.HAID from AM_USERGROUP_MAPPING LEFT OUTER JOIN AM_USERGROUP_OWNERS ON AM_USERGROUP_MAPPING.GROUPID=AM_USERGROUP_OWNERS.GROUPID WHERE AM_USERGROUP_OWNERS.USERID=" + userId + " AND AM_USERGROUP_MAPPING.HAID NOT IN (select HAID from AM_HOLISTICAPPLICATION_OWNERS where ownerid=" + userId + ")";
/*  830 */       ArrayList<ArrayList> missingGroupsList = DBUtil.getRows(missingGroupForMappingQry);
/*  831 */       if (missingGroupsList.size() > 0) {
/*  832 */         ArrayList<String> missingGroupsForMapping = (ArrayList)missingGroupsList.get(0);
/*  833 */         for (String groupId : missingGroupsForMapping) {
/*      */           try {
/*  835 */             String userGroupMapQry = "insert into AM_HOLISTICAPPLICATION_OWNERS values (" + groupId + "," + userId + ")";
/*  836 */             AMConnectionPool.executeUpdateStmt(userGroupMapQry);
/*      */           }
/*      */           catch (SQLException e) {
/*  839 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  845 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward updateUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  852 */     if (!isTokenValid(request)) {
/*  853 */       return showUsers(mapping, form, request, response);
/*      */     }
/*  855 */     UserConfiguration uc = (UserConfiguration)form;
/*      */     try {
/*  857 */       ActionMessages messages = new ActionMessages();
/*  858 */       String[] selectedMonitor = request.getParameterValues("select");
/*  859 */       String groupname = request.getParameter("groups");
/*  860 */       String customfield = request.getParameter("CustomField");
/*  861 */       String resourceid = null;
/*  862 */       if ((customfield != null) && (customfield.equals("NewUser"))) {
/*  863 */         resourceid = request.getParameter("resourceid");
/*      */       }
/*      */       
/*  866 */       UserSessionHandler ush = UserSessionHandler.getInstance();
/*  867 */       boolean policyEnabledStatus = false;
/*  868 */       boolean change = false;
/*  869 */       boolean deletePic = false;
/*  870 */       change = uc.getChange();
/*  871 */       deletePic = uc.getDeletePic();
/*  872 */       int userID = Integer.parseInt(uc.getUserid());
/*      */       
/*  874 */       String errstate = "noerror";
/*  875 */       String confirmPassword = request.getParameter("confirmpassword");
/*  876 */       String userName = "";
/*  877 */       String password = "";
/*  878 */       if (uc.getPassword() != null) {
/*  879 */         password = DBQueryUtil.MD5(uc.getPassword());
/*      */       }
/*  881 */       String username = uc.getUserName();
/*  882 */       String loginuser = request.getRemoteUser();
/*  883 */       String oldpassword = request.getParameter("oldpassword");
/*  884 */       String newpassword = uc.getPassword();
/*  885 */       String method = "updateUser";
/*  886 */       String updateChk = uc.getUpdateChk();
/*  887 */       String errType = "noerror";
/*  888 */       String restrictedAdmin = "1";
/*  889 */       if ("on".equals(uc.getDelegatedAdmin())) {
/*  890 */         restrictedAdmin = "0";
/*      */       }
/*      */       
/*  893 */       if (uc.getUserName().length() > 50)
/*      */       {
/*  895 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.creation.namelengthexceed.text", uc.getUserName()));
/*  896 */         saveMessages(request, messages);
/*  897 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*  900 */       if (UserConfigurationUtil.isUserExist(username, String.valueOf(userID)).booleanValue()) {
/*  901 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.creation.nameexists", uc.getUserName()));
/*  902 */         saveMessages(request, messages);
/*  903 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/*      */ 
/*  907 */       if ((EnterpriseUtil.isManagedServer()) && (userID == 1))
/*      */       {
/*  909 */         userName = "systemadmin_enterprise";
/*      */       }
/*      */       else
/*      */       {
/*  913 */         userName = uc.getUserName();
/*      */       }
/*      */       
/*  916 */       String home = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();
/*  917 */       String fileName = null;
/*      */       int bytesRead;
/*  919 */       try { if ((change) || (deletePic)) {
/*  920 */           InputStream stream = null;
/*  921 */           FormFile imgfile = uc.getTheFile();
/*  922 */           fileName = imgfile.getFileName();
/*  923 */           if ((imgfile != null) && (fileName != null) && (fileName.length() > 0))
/*      */           {
/*  925 */             stream = imgfile.getInputStream();
/*      */           }
/*      */           else {
/*  928 */             String filepath = null;
/*  929 */             if (((change) && (deletePic)) || (deletePic)) {
/*  930 */               filepath = home + File.separator + "working" + File.separator + "images" + File.separator + "icon_user.gif";
/*      */               
/*  932 */               stream = new java.io.FileInputStream(filepath);
/*  933 */               fileName = "icon_user.gif";
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  939 */           String uploadDir = home + File.separator + "working" + File.separator + "users" + File.separator + uc.getUserName() + File.separator;
/*  940 */           Boolean dirCreated = Boolean.valueOf(new File(uploadDir).mkdir());
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
/*  951 */           if ((deletePic) || ((change) && (imgfile != null) && (fileName != null) && (fileName.length() > 0))) {
/*  952 */             OutputStream bos = new FileOutputStream(uploadDir + uc.getUserid() + ".jpg");
/*  953 */             bytesRead = 0;
/*  954 */             byte[] buffer = new byte[' '];
/*  955 */             while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/*  956 */               bos.write(buffer, 0, bytesRead);
/*      */             }
/*  958 */             bos.close();
/*  959 */             String data = "The file has been written to \"" + uploadDir + uc.getUserid() + ".jpg\"";
/*      */             
/*      */ 
/*  962 */             stream.close();
/*  963 */             if ((imgfile != null) && (fileName != null) && (fileName.length() > 0)) {
/*  964 */               messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.success", fileName));
/*  965 */               saveMessages(request, messages);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (FileNotFoundException fnfe)
/*      */       {
/*  973 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("file.upload.failed", fileName));
/*  974 */         saveMessages(request, messages);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  979 */       policyEnabledStatus = ush.getPwdPolicyEnabledStatus();
/*  980 */       if (policyEnabledStatus)
/*      */       {
/*  982 */         errstate = validatePassword(userName, newpassword, confirmPassword, method, updateChk);
/*      */       }
/*  984 */       AMLog.debug("Error Status" + errstate);
/*      */       
/*  986 */       if ("noerror".equals(errstate))
/*      */       {
/*      */         try
/*      */         {
/*  990 */           ArrayList<String> rowList = this.mo.getRowsForSingleColumn("select USERNAME from AM_UserPasswordTable where USERID =" + userID);
/*  991 */           boolean nonAdminUser = false;
/*  992 */           String userRole = DBUtil.getUserRole(request.getRemoteUser());
/*      */           
/*  994 */           if (("USERS".equals(userRole)) || ("OPERATOR".equals(userRole)) || ("MANAGER".equals(userRole))) {
/*  995 */             nonAdminUser = true;
/*      */           }
/*  997 */           Properties userProps = new Properties();
/*  998 */           userProps.setProperty("userId", String.valueOf(userID));
/*  999 */           userProps.setProperty("updateChk", updateChk);
/* 1000 */           userProps.setProperty("userName", uc.getUserName());
/* 1001 */           userProps.setProperty("loginUser", loginuser);
/* 1002 */           userProps.setProperty("oldUserName", (String)rowList.get(0));
/* 1003 */           if ((nonAdminUser) && (!((String)rowList.get(0)).equalsIgnoreCase(uc.getUserName())) && ("false".equalsIgnoreCase(DBUtil.getServerConfigValue("allowNonAdminUsersEditUsername")))) {
/* 1004 */             String str = "/userconfiguration.do?method=editUser&userid=" + userID + "&username=" + (String)rowList.get(0) + "&errtype=restrictUpdateUsername";
/* 1005 */             Hashtable userDetailsCache; return new ActionForward(str);
/*      */           }
/* 1007 */           if (uc.getPassword() != null) {
/* 1008 */             userProps.setProperty("password", uc.getPassword());
/*      */           }
/* 1010 */           if (oldpassword != null) {
/* 1011 */             userProps.setProperty("oldPassword", oldpassword);
/*      */           }
/* 1013 */           userProps.setProperty("description", uc.getDescription());
/* 1014 */           userProps.setProperty("email", uc.getEmail());
/* 1015 */           userProps.setProperty("restrictedAdmin", restrictedAdmin);
/*      */           
/* 1017 */           if ((uc.getAssociatedUserGroups() != null) && (uc.getAssociatedUserGroups().trim().length() > 0))
/*      */           {
/* 1019 */             userProps.setProperty("associateUsergroupId", uc.getAssociatedUserGroups());
/*      */           }
/* 1021 */           if (uc.getGroups() != null) {
/* 1022 */             StringBuilder builder = new StringBuilder();
/* 1023 */             for (int i = 0; i < uc.getGroups().length; i++) {
/* 1024 */               builder.append(uc.getGroups()[i]);
/* 1025 */               if (i != uc.getGroups().length - 1) {
/* 1026 */                 builder.append(",");
/*      */               }
/*      */             }
/* 1029 */             userProps.setProperty("role", builder.toString());
/*      */           }
/*      */           
/* 1032 */           String domainUser = uc.getLdapdomainName();
/* 1033 */           if (("true".equals(domainUser)) && 
/* 1034 */             (uc.getLdapdomainValue() != null) && (uc.getLdapdomainValue().trim().length() > 0)) {
/* 1035 */             String[] domains = new String[0];
/* 1036 */             domains = uc.getLdapdomainValue().split(",");
/* 1037 */             userProps.setProperty("domainuser", "true");
/* 1038 */             userProps.setProperty("userdomainids", uc.getLdapdomainValue());
/* 1039 */             AMConnectionPool.executeUpdateStmt("delete from AM_DOMAINUSER_MAPPER where USERID=" + userID);
/* 1040 */             for (String id : domains) {
/*      */               try {
/* 1042 */                 AMConnectionPool.executeUpdateStmt("insert into AM_DOMAINUSER_MAPPER (USERID,DOMAINID) values (" + userID + "," + id + ")");
/*      */               } catch (Exception ex) {
/* 1044 */                 ex.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 1049 */           String errorType = UserConfigurationUtil.updateUserDetails(userProps);
/*      */           
/* 1051 */           if ((errorType != null) && (!errorType.equals("noerror"))) {
/* 1052 */             String str = "/admin/userconfiguration.do?method=editUser&userid=" + userID + "&username=" + userName + "&errtype=" + errType;
/* 1053 */             if ("true".equals(request.getParameter("updateProfile")))
/* 1054 */               str = "/userconfiguration.do?method=editUser&username=" + userName + "&userid=" + userID;
/*      */             Hashtable userDetailsCache;
/* 1056 */             return new ActionForward(str);
/*      */           }
/*      */           
/*      */ 
/* 1060 */           ArrayList<String> monitorGroups = new ArrayList();
/* 1061 */           if (selectedMonitor != null) {
/* 1062 */             monitorGroups = new ArrayList(Arrays.asList(selectedMonitor));
/*      */           }
/* 1064 */           String[] userGroups = new String[0];
/* 1065 */           if ((uc.getAssociatedUserGroups() != null) && (uc.getAssociatedUserGroups().trim().length() > 0)) {
/* 1066 */             userGroups = uc.getAssociatedUserGroups().split(",");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1071 */           ArrayList<String> mgIds = new ArrayList();
/* 1072 */           if (("ADMIN".equals(groupname)) && ("1".equals(restrictedAdmin))) {
/* 1073 */             mgIds = UserConfigurationUtil.updateOwnerUserGroups(new String[0], Integer.valueOf(uc.getUserid()).intValue(), true);
/*      */           } else {
/* 1075 */             mgIds = UserConfigurationUtil.updateOwnerUserGroups(userGroups, Integer.valueOf(uc.getUserid()).intValue(), true);
/*      */           }
/* 1077 */           for (String id : mgIds) {
/* 1078 */             if (!monitorGroups.contains(id)) {
/* 1079 */               monitorGroups.add(id);
/*      */             }
/*      */           }
/*      */           try
/*      */           {
/* 1084 */             if (("ADMIN".equals(groupname)) && ("1".equals(restrictedAdmin))) {
/* 1085 */               UserConfigurationUtil.updateMonitorGroupDetails(username, uc.getUserid(), groupname, null);
/*      */             } else {
/* 1087 */               UserConfigurationUtil.updateMonitorGroupDetails(username, uc.getUserid(), groupname, monitorGroups);
/*      */             }
/*      */           } catch (Exception ex) {
/* 1090 */             ex.printStackTrace();
/*      */           }
/*      */           
/* 1093 */           if (uc.getUserName() != null) {
/* 1094 */             UserConfigurationUtil.updateGroups(uc.getUserName(), uc.getGroups());
/*      */           }
/* 1096 */           AMPersonalize.loadUsernameRoleMap();
/* 1097 */           UserConfigurationUtil.updateUserPrivileges(request, uc.getUserName());
/* 1098 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.updation.success"));
/* 1099 */           saveMessages(request, messages);
/*      */           
/*      */           String userId;
/* 1102 */           if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */           {
/* 1104 */             if (uc.getUserid() != null)
/*      */             {
/* 1106 */               userId = uc.getUserid();
/* 1107 */               RestrictedUsersViewUtil.userVsIsRoleRestrictedMap.remove(userId);
/*      */               
/* 1109 */               if (RestrictedUsersViewUtil.isRestrictedRole(userId)) {
/* 1110 */                 AMLog.debug("[UserConfigurationAction::(udpateUser)]ruser(s) : " + userId);
/* 1111 */                 RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(userId);
/* 1112 */                 RestrictedUsersViewUtil.deleteFromAMUserResourcesTable(userId);
/* 1113 */                 RestrictedUsersViewUtil.insertAllResourcesOfOwner(userId);
/*      */               }
/*      */             }
/*      */           }
/*      */           Hashtable userDetailsCache;
/* 1118 */           if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 1119 */             if (request.isUserInRole("ENTERPRISEADMIN")) { Hashtable userDetailsCache;
/* 1120 */               return new ActionForward("Tile.EnterpriseAdminConf");
/*      */             }
/* 1122 */             return new ActionForward("Tile.AdminConf");
/*      */           }
/* 1124 */           String str = "/admin/userconfiguration.do?method=showUsers&PRINTER_FRIENDLY=true&CustomField=NewUser&resourceid=" + resourceid;
/* 1125 */           if ("true".equals(request.getParameter("updateProfile"))) {
/* 1126 */             str = "/userconfiguration.do?method=editUser&username=" + uc.getUserName() + "&userid=" + uc.getUserid();
/* 1127 */             Hashtable userDetailsCache; return new ActionForward(str);
/*      */           }
/*      */           
/* 1130 */           if ((customfield != null) && (customfield.equals("NewUser"))) { Hashtable userDetailsCache;
/* 1131 */             return new ActionForward(str);
/*      */           }
/*      */           Hashtable userDetailsCache;
/* 1134 */           return showUsers(mapping, form, request, response);
/*      */ 
/*      */         }
/*      */         finally
/*      */         {
/* 1139 */           Hashtable userDetailsCache = com.adventnet.appmanager.utils.client.CommonAPIUtil.getUserDetails(username);
/* 1140 */           AMLog.info("userdetails updated in cache" + userDetailsCache);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1146 */       String str = "/admin/userconfiguration.do?method=editUser&userid=" + userID + "&username=" + userName + "&errtype=" + errstate;
/* 1147 */       return new ActionForward(str);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1151 */       ee.printStackTrace();
/*      */       
/* 1153 */       AMPersonalize.loadUsernameRoleMap(); }
/* 1154 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward editUserGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1162 */     ArrayList<Properties> selectedMonitor = new ArrayList();
/* 1163 */     UserConfiguration uc = (UserConfiguration)form;
/*      */     
/*      */     try
/*      */     {
/* 1167 */       String groupid = request.getParameter("groupid");
/* 1168 */       String associatedGroup = "select AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.RESOURCENAME from AM_HOLISTICAPPLICATION,AM_ManagedObject, AM_USERGROUP_MAPPING where AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  and AM_USERGROUP_MAPPING.GROUPID=" + groupid;
/* 1169 */       ResultSet mgSet = null;
/*      */       try {
/* 1171 */         mgSet = AMConnectionPool.executeQueryStmt(associatedGroup);
/* 1172 */         while (mgSet.next()) {
/* 1173 */           Properties individualMonitor = new Properties();
/* 1174 */           individualMonitor.put("value", mgSet.getString("HAID"));
/* 1175 */           individualMonitor.put("label", mgSet.getString("RESOURCENAME"));
/* 1176 */           selectedMonitor.add(individualMonitor);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1181 */         ex.printStackTrace();
/*      */       } finally {
/* 1183 */         if (mgSet != null) {
/* 1184 */           AMConnectionPool.closeStatement(mgSet);
/*      */         }
/*      */       }
/*      */       
/* 1188 */       String groupnameQuery = "select GROUPNAME,USERLOGINROLE from AM_USERGROUP_CONFIG where GROUPID=" + groupid;
/* 1189 */       ResultSet nameSet = null;
/*      */       try {
/* 1191 */         nameSet = AMConnectionPool.executeQueryStmt(groupnameQuery);
/* 1192 */         if (nameSet.next()) {
/* 1193 */           uc.setUsergroupName(nameSet.getString("GROUPNAME"));
/* 1194 */           uc.setUserGroupRole(nameSet.getString("USERLOGINROLE"));
/*      */         }
/*      */       } catch (Exception ex) {
/* 1197 */         ex.printStackTrace();
/*      */       } finally {
/* 1199 */         if (nameSet != null) {
/* 1200 */           AMConnectionPool.closeStatement(nameSet);
/*      */         }
/*      */       }
/* 1203 */       ArrayList domainList = new ArrayList();
/* 1204 */       String domainQuery = "select ID,AUTHENTICATION,DOMAINNAME from AM_DOMAINCONTROLLERS,AM_DOMAINUSERGROUP_MAPPING WHERE AM_DOMAINUSERGROUP_MAPPING.DOMAINID=AM_DOMAINCONTROLLERS.ID and AM_DOMAINUSERGROUP_MAPPING.GROUPID=" + groupid;
/* 1205 */       ResultSet rs = null;
/* 1206 */       boolean isDomainUserGroup = false;
/*      */       try {
/* 1208 */         rs = AMConnectionPool.executeQueryStmt(domainQuery);
/* 1209 */         while (rs.next()) {
/* 1210 */           String authenticationType = rs.getString("AUTHENTICATION");
/* 1211 */           uc.setLdapdomainValue(rs.getString("DOMAINNAME"));
/* 1212 */           domainList.add(rs.getString("ID"));
/* 1213 */           if ("LDAP".equals(authenticationType)) {
/* 1214 */             request.setAttribute("isOpenLdapGroup", Boolean.valueOf(true));
/* 1215 */             break;
/*      */           }
/*      */         }
/*      */         
/* 1219 */         if (domainList.size() > 0) {
/* 1220 */           isDomainUserGroup = true;
/*      */         }
/*      */         
/* 1223 */         request.setAttribute("isDomainGroup", Boolean.valueOf(isDomainUserGroup));
/*      */       } catch (Exception ex) {
/* 1225 */         ex.printStackTrace();
/*      */       } finally {
/* 1227 */         if (rs != null) {
/* 1228 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       
/* 1232 */       String usersQuery = "select AM_UserPasswordTable.USERID from AM_UserPasswordTable,AM_USERGROUP_OWNERS,AM_USERGROUP_CONFIG where AM_USERGROUP_CONFIG.GROUPID=AM_USERGROUP_OWNERS.GROUPID and AM_UserPasswordTable.USERID=AM_USERGROUP_OWNERS.USERID and AM_USERGROUP_CONFIG.GROUPID=" + groupid;
/* 1233 */       ResultSet userSet = null;
/* 1234 */       ArrayList<String> selectedList = new ArrayList();
/*      */       try {
/* 1236 */         userSet = AMConnectionPool.executeQueryStmt(usersQuery);
/* 1237 */         while (userSet.next()) {
/* 1238 */           selectedList.add(userSet.getString("USERID"));
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/* 1242 */         ex.printStackTrace();
/*      */       } finally {
/* 1244 */         if (userSet != null) {
/* 1245 */           AMConnectionPool.closeStatement(userSet);
/*      */         }
/*      */       }
/* 1248 */       request.setAttribute("selectedUserGroupMg", selectedMonitor);
/* 1249 */       request.setAttribute("selectedUserList", selectedList);
/* 1250 */       request.setAttribute("selectedDomainList", domainList);
/* 1251 */       request.setAttribute("editUserGroup", Boolean.valueOf(true));
/*      */     } catch (Exception ex) {
/* 1253 */       ex.printStackTrace();
/*      */     }
/* 1255 */     return new ActionForward("Tile.usergroups.Conf");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1265 */     if ((DBUtil.isDelegatedAdmin(request.getRemoteUser())) || ((EnterpriseUtil.isManagedServer()) && (Constants.ssoEnabled)))
/*      */     {
/* 1267 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/*      */     
/*      */ 
/* 1271 */     String userRole = DBUtil.getUserRole(request.getRemoteUser());
/* 1272 */     if ("DEMO".equals(userRole)) {
/* 1273 */       AMLog.debug("UserConfigurationAction : User with Demo role not allowed to edit User Details");
/* 1274 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/* 1276 */     if ((("USERS".equals(userRole)) || ("OPERATOR".equals(userRole)) || ("MANAGER".equals(userRole)) || ("REPORTER".equals(userRole))) && 
/* 1277 */       (!request.getParameter("userid").equals(DBUtil.getUserID(request.getRemoteUser())))) {
/* 1278 */       AMLog.debug("UserConfigurationAction : Request userid " + DBUtil.getUserID(request.getRemoteUser()) + " is different from userid in parameter " + request.getParameter("userid") + ". User role : " + userRole + ", Username : " + request.getParameter("username") + " and Remote Username : " + request.getRemoteUser());
/* 1279 */       return new ActionForward("/jsp/formpages/UserUpdate.jsp", true);
/*      */     }
/*      */     
/* 1282 */     String changeImg = request.getParameter("ChangeImg");
/* 1283 */     request.setAttribute("changeImg", changeImg);
/* 1284 */     request.setAttribute("HelpKey", "User Administration");
/* 1285 */     UserConfiguration uc = (UserConfiguration)form;
/*      */     
/* 1287 */     if (((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) || ((DBUtil.isDelegatedAdmin(request.getRemoteUser())) && (request.getRequestURI().startsWith("/userconfiguration")))) {
/* 1288 */       request.setAttribute("updateProfile", "true");
/*      */     }
/* 1290 */     ArrayList selectedMonitor = new ArrayList();
/* 1291 */     ArrayList users = this.mo.getRows("select USERID,Min(AM_UserGroupTable.USERNAME) as USERNAME,Min(EMAILID) as EMAILID,Min(GROUPNAME) as GROUPNAME,'',Min(description)AS DESCRIPTION,RESTRICTEDADMIN from AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME WHERE  userid=" + uc.getUserid() + " GROUP BY USERID,RESTRICTEDADMIN");
/* 1292 */     if (users.size() == 0)
/*      */     {
/*      */ 
/*      */ 
/* 1296 */       return showUsers(mapping, form, request, response);
/*      */     }
/*      */     try
/*      */     {
/* 1300 */       String monitor = "select AM_HOLISTICAPPLICATION_OWNERS.HAID,AM_ManagedObject.RESOURCENAME from AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedObject where AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + uc.getUserid() + " and AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI' order by AM_ManagedObject.TYPE";
/* 1301 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(monitor);
/* 1302 */       System.out.println("query for edit:" + monitor);
/*      */       
/* 1304 */       while (rs.next())
/*      */       {
/* 1306 */         Properties individualMonitor = new Properties();
/* 1307 */         individualMonitor.put("value", rs.getString(1));
/* 1308 */         individualMonitor.put("label", rs.getString(2));
/* 1309 */         selectedMonitor.add(individualMonitor);
/*      */       }
/* 1311 */       rs.close();
/* 1312 */       System.out.println("selectedMonitor:====>" + selectedMonitor);
/* 1313 */       request.setAttribute("selectedMonitor", selectedMonitor);
/*      */     } catch (Exception ex) {
/* 1315 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 1318 */     ArrayList domainList = new ArrayList();
/* 1319 */     String domainQuery = "select ID,AUTHENTICATION,DOMAINNAME from AM_DOMAINCONTROLLERS,AM_DOMAINUSER_MAPPER where USERID=" + uc.getUserid() + " and DOMAINID=ID";
/* 1320 */     ResultSet dSet = null;
/*      */     try {
/* 1322 */       AMConnectionPool.getInstance();dSet = AMConnectionPool.executeQueryStmt(domainQuery);
/* 1323 */       while (dSet.next())
/*      */       {
/* 1325 */         String authenticationType = dSet.getString("AUTHENTICATION");
/* 1326 */         uc.setDomainValue(dSet.getString("DOMAINNAME"));
/* 1327 */         domainList.add(dSet.getString("ID"));
/* 1328 */         if ("LDAP".equals(authenticationType)) {
/* 1329 */           request.setAttribute("isOpenLdapUser", Boolean.valueOf(true));
/* 1330 */           break;
/*      */         }
/*      */       }
/* 1333 */       if (domainList.size() > 0) {
/* 1334 */         uc.setAuthType("ADAuthentication");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1341 */       ee.printStackTrace();
/*      */     }
/*      */     finally {
/* 1344 */       AMConnectionPool.closeStatement(dSet);
/*      */     }
/*      */     
/* 1347 */     ResultSet usergroup = null;
/* 1348 */     Object usergroupList = new ArrayList();
/*      */     try {
/* 1350 */       usergroup = AMConnectionPool.executeQueryStmt("select GROUPID from AM_USERGROUP_OWNERS where USERID=" + uc.getUserid());
/* 1351 */       while (usergroup.next()) {
/* 1352 */         ((ArrayList)usergroupList).add(usergroup.getString("GROUPID"));
/*      */       }
/*      */     } catch (Exception ex) {
/* 1355 */       ex.printStackTrace();
/*      */     } finally {
/* 1357 */       if (usergroup != null) {
/* 1358 */         AMConnectionPool.closeStatement(usergroup);
/*      */       }
/*      */     }
/*      */     
/* 1362 */     request.setAttribute("selectedDomainList", domainList);
/* 1363 */     request.setAttribute("selectedUsergroupList", usergroupList);
/* 1364 */     request.setAttribute("disableRestrictedAdmin", DBUtil.getGlobalConfigValue("disableRestrictedAdmin"));
/* 1365 */     users = (ArrayList)users.get(0);
/* 1366 */     uc.setUserName((String)users.get(1));
/* 1367 */     uc.setEmail((String)users.get(2));
/* 1368 */     String role = (String)users.get(3);
/* 1369 */     if ((EnterpriseUtil.isAdminServer()) && ("ENTERPRISEADMIN".equals(role))) {
/* 1370 */       role = "ADMIN";
/*      */     }
/* 1372 */     uc.setGroups(new String[] { role });
/* 1373 */     uc.setPassword((String)users.get(4));
/* 1374 */     uc.setDescription((String)users.get(5));
/* 1375 */     if ("0".equals((String)users.get(6))) {
/* 1376 */       uc.setDelegatedAdmin("on");
/*      */     }
/*      */     
/*      */ 
/* 1380 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */     {
/* 1382 */       if (uc.getUserid() != null)
/*      */       {
/* 1384 */         AMLog.debug("[UserConfigurationAction::(editUser)]ruser(s) : " + uc.getUserid());
/* 1385 */         RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(uc.getUserid());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1390 */     return new ActionForward("Tile.usergroups.Conf");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward unlockUserStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1397 */     UserConfiguration uc = (UserConfiguration)form;
/* 1398 */     String userid = request.getParameter("userid");
/* 1399 */     ArrayList users = this.mo.getRows("select USERID from AM_UserPasswordTable, AM_UserGroupTable where AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME and userid=" + userid + "");
/* 1400 */     if (users.size() == 0)
/*      */     {
/* 1402 */       String str = "/admin/userconfiguration.do?method=showUsers&unlockMessage=false";
/* 1403 */       return new ActionForward(str, true);
/*      */     }
/*      */     try {
/* 1406 */       System.out.println("query for delete user status");
/* 1407 */       String deleteUserStatusQuery = "delete from AM_UserStatus where USERID=" + userid;
/* 1408 */       this.mo.executeUpdateStmt(deleteUserStatusQuery);
/* 1409 */       int count = 0;
/* 1410 */       UserSessionHandler ush = UserSessionHandler.getInstance();
/* 1411 */       ush.setLoginFailCount(userid, count);
/*      */     } catch (Exception ex) {
/* 1413 */       ex.printStackTrace();
/*      */     }
/* 1415 */     String str = "/admin/userconfiguration.do?method=showUsers&unlockMessage=true";
/* 1416 */     return new ActionForward(str, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean deleteDirectory(File path)
/*      */   {
/* 1422 */     File[] files = path.listFiles();
/* 1423 */     for (int i = 0; i < files.length; i++) {
/* 1424 */       if (files[i].isDirectory()) {
/* 1425 */         deleteDirectory(files[i]);
/*      */       }
/*      */       else {
/* 1428 */         files[i].delete();
/*      */       }
/*      */     }
/*      */     
/* 1432 */     return path.delete();
/*      */   }
/*      */   
/*      */   public ActionForward deleteUserGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1437 */       ActionErrors errors = new ActionErrors();
/* 1438 */       if (!isTokenValid(request)) {
/* 1439 */         return showUsers(mapping, form, request, response);
/*      */       }
/* 1441 */       ActionMessages messages = new ActionMessages();
/* 1442 */       String[] values = request.getParameterValues("usergroupcheckbox");
/* 1443 */       if ((values == null) || (values.length == 0))
/*      */       {
/*      */ 
/*      */ 
/* 1447 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.useradministration.usergroup.deletion.failed"));
/* 1448 */         saveErrors(request, errors);
/* 1449 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/* 1452 */       UserConfigurationUtil.deleteUserGroupDetails(Arrays.asList(values));
/* 1453 */       UserConfigurationUtil.updateUserPrivileges(request, null);
/* 1454 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.useradministration.usergroup.delete.success"));
/* 1455 */       saveMessages(request, messages);
/*      */     } catch (Exception ex) {
/* 1457 */       ex.printStackTrace();
/*      */     }
/* 1459 */     request.setAttribute("showTab", "usergroup");
/* 1460 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward deleteDomainConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1465 */       ActionErrors errors = new ActionErrors();
/* 1466 */       if (!isTokenValid(request)) {
/* 1467 */         return showUsers(mapping, form, request, response);
/*      */       }
/* 1469 */       ActionMessages messages = new ActionMessages();
/* 1470 */       String[] values = request.getParameterValues("domaincheckbox");
/* 1471 */       if ((values == null) || (values.length == 0))
/*      */       {
/* 1473 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.useradministration.domain.deletion.failed"));
/* 1474 */         saveErrors(request, errors);
/* 1475 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/* 1478 */       UserConfigurationUtil.deleteDomainDetails(Arrays.asList(values), request.getRemoteUser());
/* 1479 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.useradministration.usergroup.delete.success"));
/* 1480 */       saveMessages(request, messages);
/*      */     } catch (Exception ex) {
/* 1482 */       ex.printStackTrace();
/*      */     }
/* 1484 */     request.setAttribute("showTab", "domaindetails");
/* 1485 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward deleteUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1490 */       ActionErrors errors = new ActionErrors();
/* 1491 */       if (!isTokenValid(request)) {
/* 1492 */         return showUsers(mapping, form, request, response);
/*      */       }
/* 1494 */       ActionMessages messages = new ActionMessages();
/* 1495 */       String[] values = request.getParameterValues("usercheckbox");
/* 1496 */       if ((values == null) || (values.length == 0))
/*      */       {
/*      */ 
/*      */ 
/* 1500 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("user.deletion.failed"));
/* 1501 */         saveErrors(request, errors);
/* 1502 */         return showUsers(mapping, form, request, response);
/*      */       }
/*      */       
/* 1505 */       UserConfigurationUtil.deleteUserDetails(Arrays.asList(values), request.getRemoteUser());
/*      */       
/* 1507 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("user.deletion.success"));
/* 1508 */       saveMessages(request, messages);
/*      */     }
/*      */     catch (Exception e) {
/* 1511 */       e.printStackTrace();
/*      */     }
/* 1513 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward showUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1521 */     if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */     {
/* 1523 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/* 1525 */     request.setAttribute("HelpKey", "User Administration");
/* 1526 */     request.setAttribute("productEdition", Constants.getCategorytype());
/* 1527 */     String customfields = request.getParameter("CustomField");
/* 1528 */     String resourceid = request.getParameter("resourceid");
/*      */     
/* 1530 */     String showtab = request.getParameter("showtab");
/* 1531 */     if ("usergroup".equalsIgnoreCase(showtab)) {
/* 1532 */       request.setAttribute("showTab", "usergroup");
/* 1533 */     } else if ("domaindetails".equalsIgnoreCase(showtab)) {
/* 1534 */       request.setAttribute("showTab", "domaindetails");
/*      */     }
/*      */     
/* 1537 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/* 1538 */     UserConfiguration uc = (UserConfiguration)form;
/* 1539 */     if ("true".equals(request.getParameter("updateProfile"))) {
/* 1540 */       request.setAttribute("updateProfile", "true");
/* 1541 */       String str = "/userconfiguration.do?method=editUser&username=" + uc.getUserName() + "&userid=" + uc.getUserid();
/* 1542 */       return new ActionForward(str);
/*      */     }
/* 1544 */     ResultSet rs = null;
/* 1545 */     String query = null;
/* 1546 */     ArrayList userids = new ArrayList();
/*      */     
/* 1548 */     if ((customfields != null) && (customfields.equals("NewUser"))) {
/* 1549 */       query = "select VALUEID from AM_MYFIELDS_ENTITYDATA where RESOURCEID='" + resourceid + "'";
/*      */       try
/*      */       {
/* 1552 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1553 */         while (rs.next())
/*      */         {
/* 1555 */           userids.add(Integer.valueOf(rs.getInt(1)));
/*      */         }
/*      */       } catch (Exception e) {
/* 1558 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 1561 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/* 1564 */       request.setAttribute("addedUsers", userids);
/*      */     }
/*      */     
/* 1567 */     ArrayList users = new ArrayList();
/* 1568 */     int maxLockTime = ush.getAccLockTimeoutValue();
/* 1569 */     if (!EnterpriseUtil.isAdminServer())
/*      */     {
/* 1571 */       users = this.mo.getRows("select AM_UserPasswordTable.USERID,min(AM_UserGroupTable.USERNAME) as USERNAME,min(EMAILID) EMAILID,min(GROUPNAME) GROUPNAME,min(description) description,status,(time+(" + maxLockTime + " * (60 * 1000))),min(AM_DOMAINUSER_MAPPER.DOMAINID) as DOMAINID,min(AM_DOMAINCONTROLLERS.AUTHENTICATION),RESTRICTEDADMIN FROM  AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME  = AM_UserGroupTable.USERNAME LEFT OUTER JOIN AM_UserStatus ON AM_UserPasswordTable.USERID=AM_UserStatus.USERID  LEFT OUTER JOIN  AM_DOMAINUSER_MAPPER ON AM_UserPasswordTable.USERID=AM_DOMAINUSER_MAPPER.USERID LEFT OUTER JOIN AM_DOMAINCONTROLLERS on AM_DOMAINCONTROLLERS.ID=AM_DOMAINUSER_MAPPER.DOMAINID where  AM_UserPasswordTable.USERNAME  NOT IN  ('reportadmin','" + "systemadmin_enterprise" + "','" + OEMUtil.getOEMString("am.hiddenuser.username") + "','" + OEMUtil.getOEMString("am.hiddenuser1.username") + "') group by AM_UserPasswordTable.USERID,AM_UserStatus.STATUS,AM_UserStatus.TIME,RESTRICTEDADMIN order by USERNAME ");
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1576 */       users = this.mo.getRows("select AM_UserPasswordTable.USERID,min(AM_UserGroupTable.USERNAME) as USERNAME,min(EMAILID) EMAILID,min(GROUPNAME) GROUPNAME,min(description) description,status,(time+(" + maxLockTime + " * (60 * 1000))),min(AM_DOMAINUSER_MAPPER.DOMAINID) as DOMAINID,min(AM_DOMAINCONTROLLERS.AUTHENTICATION),RESTRICTEDADMIN FROM  AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME  = AM_UserGroupTable.USERNAME LEFT OUTER JOIN AM_UserStatus ON AM_UserPasswordTable.USERID=AM_UserStatus.USERID LEFT OUTER JOIN  AM_DOMAINUSER_MAPPER ON AM_UserPasswordTable.USERID=AM_DOMAINUSER_MAPPER.USERID LEFT OUTER JOIN AM_DOMAINCONTROLLERS on AM_DOMAINCONTROLLERS.ID=AM_DOMAINUSER_MAPPER.DOMAINID where  AM_UserPasswordTable.USERNAME NOT IN  ('reportadmin','" + OEMUtil.getOEMString("am.hiddenuser.username") + "','" + OEMUtil.getOEMString("am.hiddenuser1.username") + "') group by AM_UserPasswordTable.USERID,AM_UserStatus.STATUS,AM_UserStatus.TIME,RESTRICTEDADMIN order by USERNAME");
/*      */     }
/* 1578 */     if (users.size() > 0) {
/* 1579 */       request.setAttribute("users", users);
/*      */     }
/*      */     
/*      */ 
/* 1583 */     Properties mgAssignedForOwner = new Properties();
/* 1584 */     Properties userGroupsAssignedForOwner = new Properties();
/* 1585 */     Properties domainsAssignedForOwner = new Properties();
/* 1586 */     for (int i = 0; i < users.size(); i++)
/*      */     {
/* 1588 */       String userid = (String)((ArrayList)users.get(i)).get(0);
/* 1589 */       ArrayList monitorGroupQuery = new ArrayList();
/* 1590 */       if (Constants.isSsoEnabled()) {
/* 1591 */         monitorGroupQuery = this.mo.getRows("select AM_ManagedObject.DISPLAYNAME from AM_USERRESOURCESTABLE,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and type='HAI' and AM_USERRESOURCESTABLE.USERID='" + ((ArrayList)users.get(i)).get(0) + "'");
/*      */       } else {
/* 1593 */         monitorGroupQuery = this.mo.getRows("select AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + ((ArrayList)users.get(i)).get(1) + "'");
/*      */       }
/* 1595 */       StringBuilder groupName = new StringBuilder();
/* 1596 */       int haidsize = monitorGroupQuery.size();
/* 1597 */       for (int j = 0; j < haidsize; j++)
/*      */       {
/* 1599 */         groupName.append((String)((ArrayList)monitorGroupQuery.get(j)).get(0)).append(", ");
/*      */       }
/*      */       
/*      */ 
/* 1603 */       if (haidsize > 0) {
/* 1604 */         mgAssignedForOwner.put(userid, groupName.substring(0, groupName.length() - 2));
/*      */       }
/*      */       
/* 1607 */       ArrayList userAssociatedGroups = new ArrayList();
/*      */       
/* 1609 */       userAssociatedGroups = this.mo.getRows("select AM_USERGROUP_CONFIG.GROUPNAME from AM_USERGROUP_CONFIG,AM_USERGROUP_OWNERS where AM_USERGROUP_CONFIG.GROUPID=AM_USERGROUP_OWNERS.GROUPID and AM_USERGROUP_OWNERS.USERID=" + userid + "");
/* 1610 */       StringBuilder userGroups = new StringBuilder();
/* 1611 */       int size = userAssociatedGroups.size();
/*      */       
/* 1613 */       for (int k = 0; k < size; k++) {
/* 1614 */         userGroups.append((String)((ArrayList)userAssociatedGroups.get(k)).get(0)).append(", ");
/*      */       }
/*      */       
/* 1617 */       if (size > 0) {
/* 1618 */         userGroupsAssignedForOwner.put(userid, userGroups.substring(0, userGroups.length() - 2));
/*      */       }
/*      */       
/* 1621 */       ArrayList domainList = this.mo.getRows("select DOMAINNAME from AM_DOMAINCONTROLLERS,AM_DOMAINUSER_MAPPER where AM_DOMAINCONTROLLERS.ID=AM_DOMAINUSER_MAPPER.DOMAINID AND AM_DOMAINUSER_MAPPER.USERID=" + userid);
/*      */       
/* 1623 */       if (domainList.size() > 0) {
/* 1624 */         String domains = domainList.toString().replace("[", "");
/* 1625 */         domains = domains.toString().replace("]", "");
/* 1626 */         domainsAssignedForOwner.put(userid, domains);
/*      */       }
/*      */     }
/*      */     
/* 1630 */     request.setAttribute("mgsForOwner", mgAssignedForOwner);
/* 1631 */     request.setAttribute("usergroupsForOwner", userGroupsAssignedForOwner);
/* 1632 */     request.setAttribute("domainsForOwner", domainsAssignedForOwner);
/* 1633 */     request.setAttribute("disableRestrictedAdmin", DBUtil.getGlobalConfigValue("disableRestrictedAdmin"));
/* 1634 */     ArrayList userGroups = new ArrayList();
/*      */     
/* 1636 */     userGroups = this.mo.getRows("select AM_USERGROUP_CONFIG.GROUPID,GROUPNAME,min(DOMAINID) as DOMAINID,min(AM_DOMAINCONTROLLERS.AUTHENTICATION),min(USERLOGINROLE) from AM_USERGROUP_CONFIG left outer join AM_DOMAINUSERGROUP_MAPPING on AM_USERGROUP_CONFIG.GROUPID=AM_DOMAINUSERGROUP_MAPPING.GROUPID LEFT OUTER JOIN AM_DOMAINCONTROLLERS ON AM_DOMAINCONTROLLERS.ID=AM_DOMAINUSERGROUP_MAPPING.DOMAINID group by AM_USERGROUP_CONFIG.GROUPID, GROUPNAME ORDER BY GROUPNAME");
/* 1637 */     request.setAttribute("usergroups", userGroups);
/*      */     
/* 1639 */     ArrayList domainDetails = new ArrayList();
/* 1640 */     domainDetails = this.mo.getRows("select ID,DOMAINNAME,DNSHOST,PORT,AUTHENTICATION,PERMISSION,SSLENABLED from AM_DOMAINCONTROLLERS");
/* 1641 */     request.setAttribute("domainDetails", domainDetails);
/* 1642 */     Properties mgAssignedforUserGroup = new Properties();
/* 1643 */     Properties usergroupUsersList = new Properties();
/* 1644 */     Properties usergroupDomainList = new Properties();
/* 1645 */     for (int m = 0; m < userGroups.size(); m++) {
/* 1646 */       StringBuilder assignedGroupName = new StringBuilder();
/* 1647 */       ArrayList mgQuery = new ArrayList();
/* 1648 */       String groupid = (String)((ArrayList)userGroups.get(m)).get(0);
/* 1649 */       mgQuery = this.mo.getRows("select AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_USERGROUP_MAPPING,AM_USERGROUP_CONFIG where AM_ManagedObject.RESOURCEID=AM_USERGROUP_MAPPING.HAID and AM_USERGROUP_MAPPING.GROUPID=AM_USERGROUP_CONFIG.GROUPID and AM_USERGROUP_CONFIG.GROUPID=" + groupid);
/* 1650 */       int mgsize = mgQuery.size();
/* 1651 */       for (int j = 0; j < mgsize; j++) {
/* 1652 */         assignedGroupName.append((String)((ArrayList)mgQuery.get(j)).get(0)).append(", ");
/*      */       }
/* 1654 */       if (mgsize > 0) {
/* 1655 */         mgAssignedforUserGroup.put(groupid, assignedGroupName.substring(0, assignedGroupName.length() - 2));
/*      */       }
/*      */       
/* 1658 */       ArrayList ugUserList = new ArrayList();
/* 1659 */       ugUserList = this.mo.getRows("select AM_UserPasswordTable.USERNAME from AM_UserPasswordTable,AM_USERGROUP_OWNERS,AM_USERGROUP_CONFIG where AM_USERGROUP_CONFIG.GROUPID=AM_USERGROUP_OWNERS.GROUPID and AM_UserPasswordTable.USERID=AM_USERGROUP_OWNERS.USERID and AM_USERGROUP_CONFIG.GROUPID=" + groupid);
/* 1660 */       int userlistsize = ugUserList.size();
/* 1661 */       StringBuilder userList = new StringBuilder();
/* 1662 */       for (int l = 0; l < ugUserList.size(); l++) {
/* 1663 */         userList.append((String)((ArrayList)ugUserList.get(l)).get(0)).append(", ");
/*      */       }
/* 1665 */       if (userlistsize > 0) {
/* 1666 */         usergroupUsersList.put(groupid, userList.substring(0, userList.length() - 2));
/*      */       }
/*      */       
/* 1669 */       ArrayList usergroupDomains = new ArrayList();
/* 1670 */       usergroupDomains = this.mo.getRows("select DOMAINNAME from AM_DOMAINUSERGROUP_MAPPING, AM_DOMAINCONTROLLERS where AM_DOMAINUSERGROUP_MAPPING.DOMAINID=AM_DOMAINCONTROLLERS.ID and AM_DOMAINUSERGROUP_MAPPING.GROUPID=" + groupid);
/* 1671 */       if (usergroupDomains.size() > 0) {
/* 1672 */         String domains = usergroupDomains.toString().replace("[", "");
/* 1673 */         domains = domains.toString().replace("]", "");
/* 1674 */         usergroupDomainList.put(groupid, domains);
/*      */       }
/*      */     }
/*      */     
/* 1678 */     request.setAttribute("mgsforUserGroup", mgAssignedforUserGroup);
/* 1679 */     request.setAttribute("userGroupUsersList", usergroupUsersList);
/* 1680 */     request.setAttribute("userGroupDomainList", usergroupDomainList);
/*      */     
/*      */ 
/* 1683 */     ActionErrors errors = new ActionErrors();
/* 1684 */     ActionMessages messages = new ActionMessages();
/*      */     try
/*      */     {
/* 1687 */       Properties licenseinfo = com.adventnet.appmanager.struts.beans.ClientDBUtil.getLicenseInfo();
/* 1688 */       String usertype = licenseinfo.getProperty("licensetype");
/* 1689 */       int numberOfUsers = 1;
/* 1690 */       if (usertype.equals("F"))
/*      */       {
/*      */         try
/*      */         {
/* 1694 */           if (users.size() > numberOfUsers)
/*      */           {
/* 1696 */             System.out.println(" License Check Failed : The number of users allowed for this Free License is " + numberOfUsers + ". But currently there are " + users.size() + " users, so kindly delete the other users.");
/* 1697 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("user.present.creation.exceedslimit", String.valueOf(numberOfUsers), String.valueOf(users.size())));
/* 1698 */             saveErrors(request, errors);
/*      */           }
/*      */           else
/*      */           {
/* 1702 */             String m1 = FormatUtil.getString("free.user.exceeds", new String[] { OEMUtil.getOEMString("product.name") });
/* 1703 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(m1, String.valueOf(numberOfUsers)));
/* 1704 */             saveMessages(request, messages);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1709 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1715 */       e.printStackTrace();
/*      */     }
/*      */     try {
/* 1718 */       if (Constants.sqlManager) {
/* 1719 */         ArrayList perm = this.mo.getRows("select VALUE from AM_GLOBALCONFIG where NAME='allowOPRTelnet' or NAME='allowOperatorManage' or NAME='allowOperatorExecuteAction' or NAME='allowOperatorServices' or NAME='allowOperatorUpdateIP' or NAME='allowOperatorEdit' or NAME='allowAdminTelnet' or NAME='allowJumptoLink' or NAME='allowOperatorUnmanageAndReset' or NAME='allowDownTimeSchedule' or NAME='allowAdminWindowsServices' or NAME='allowOprViewAllDownTimeSchedule' or NAME='allowUpdateConfig' or NAME='allowManageDB' or NAME='createDomainUser' or NAME='allowClearAlarms' order by NAME");
/* 1720 */         AMLog.debug("Audit------------->" + perm);
/* 1721 */         String allowAdminTelnet = (String)((ArrayList)perm.get(0)).get(0);
/* 1722 */         String allowAdminWindowsServices = (String)((ArrayList)perm.get(1)).get(0);
/* 1723 */         String allowDownTimeSchedule = (String)((ArrayList)perm.get(3)).get(0);
/* 1724 */         String allowJumptoLink = (String)((ArrayList)perm.get(4)).get(0);
/* 1725 */         String allowEdit = (String)((ArrayList)perm.get(6)).get(0);
/* 1726 */         String allowExecute = (String)((ArrayList)perm.get(7)).get(0);
/* 1727 */         String allowManage = (String)((ArrayList)perm.get(8)).get(0);
/* 1728 */         String allowServices = (String)((ArrayList)perm.get(9)).get(0);
/* 1729 */         String allowReset = (String)((ArrayList)perm.get(10)).get(0);
/* 1730 */         String allowUpdateIP = (String)((ArrayList)perm.get(11)).get(0);
/* 1731 */         String allowOPRTelnet = (String)((ArrayList)perm.get(12)).get(0);
/* 1732 */         String allowOprViewAllDownTimeSchedule = (String)((ArrayList)perm.get(13)).get(0);
/* 1733 */         uc.setAllowManage(allowManage);
/* 1734 */         uc.setAllowReset(allowReset);
/* 1735 */         uc.setallowJumptoLink(allowJumptoLink);
/* 1736 */         uc.setAllowExecute(allowExecute);
/* 1737 */         uc.setAllowServices(allowServices);
/* 1738 */         uc.setAllowEdit(allowEdit);
/* 1739 */         uc.setAllowUpdateIP(allowUpdateIP);
/* 1740 */         uc.setallowOPRTelnet(allowOPRTelnet);
/* 1741 */         uc.setallowAdminTelnet(allowAdminTelnet);
/* 1742 */         uc.setDrilldown();
/* 1743 */         uc.setallowDownTimeSchedule(allowDownTimeSchedule);
/* 1744 */         uc.setallowOprViewAllDownTimeSchedule(allowOprViewAllDownTimeSchedule);
/* 1745 */         uc.setAllowAdminWindowsServices(allowAdminWindowsServices);
/* 1746 */         String allowManageDB = (String)((ArrayList)perm.get(4)).get(0);
/* 1747 */         String allowUpdateConfig = (String)((ArrayList)perm.get(14)).get(0);
/* 1748 */         String createDomainUser = (String)((ArrayList)perm.get(15)).get(0);
/* 1749 */         String allowClearAlarms = (String)((ArrayList)perm.get(2)).get(0);
/* 1750 */         uc.setAllowUpdateConfig(allowUpdateConfig);
/* 1751 */         uc.setAllowManageDB(allowManageDB);
/* 1752 */         uc.setCreateDomainUser(createDomainUser);
/* 1753 */         uc.setAllowClearAlarms(allowClearAlarms);
/*      */       } else {
/* 1755 */         ArrayList perm = this.mo.getRows(DBQueryUtil.getDBQuery("am.users.permission.query"));
/* 1756 */         AMLog.debug("Audit------------->" + perm);
/* 1757 */         String allowAdminTelnet = (String)((ArrayList)perm.get(0)).get(0);
/* 1758 */         String allowAdminWindowsServices = (String)((ArrayList)perm.get(1)).get(0);
/* 1759 */         String allowDownTimeSchedule = (String)((ArrayList)perm.get(3)).get(0);
/* 1760 */         String allowJumptoLink = (String)((ArrayList)perm.get(4)).get(0);
/* 1761 */         String allowEdit = (String)((ArrayList)perm.get(5)).get(0);
/* 1762 */         String allowExecute = (String)((ArrayList)perm.get(6)).get(0);
/* 1763 */         String allowManage = (String)((ArrayList)perm.get(7)).get(0);
/* 1764 */         String allowServices = (String)((ArrayList)perm.get(8)).get(0);
/* 1765 */         String allowReset = (String)((ArrayList)perm.get(9)).get(0);
/* 1766 */         String allowUpdateIP = (String)((ArrayList)perm.get(10)).get(0);
/* 1767 */         String allowOPRProcess = (String)((ArrayList)perm.get(11)).get(0);
/* 1768 */         String allowOPRTelnet = (String)((ArrayList)perm.get(12)).get(0);
/* 1769 */         String allowOprViewAllDownTimeSchedule = (String)((ArrayList)perm.get(13)).get(0);
/* 1770 */         String createDomainUser = (String)((ArrayList)perm.get(14)).get(0);
/* 1771 */         String allowClearAlarms = (String)((ArrayList)perm.get(2)).get(0);
/* 1772 */         String restrictedAdmin = (String)((ArrayList)perm.get(15)).get(0);
/* 1773 */         String enableRestrictedAdmin = "false".equals(restrictedAdmin) ? "true" : "false";
/* 1774 */         String allowNonAdminUsersEditUsername = "".equals(DBUtil.getServerConfigValue("allowNonAdminUsersEditUsername")) ? "true" : DBUtil.getServerConfigValue("allowNonAdminUsersEditUsername");
/*      */         
/* 1776 */         uc.setAllowManage(allowManage);
/* 1777 */         uc.setAllowReset(allowReset);
/* 1778 */         uc.setallowJumptoLink(allowJumptoLink);
/* 1779 */         uc.setAllowExecute(allowExecute);
/* 1780 */         uc.setAllowServices(allowServices);
/* 1781 */         uc.setAllowEdit(allowEdit);
/* 1782 */         uc.setAllowUpdateIP(allowUpdateIP);
/* 1783 */         uc.setallowOPRTelnet(allowOPRTelnet);
/* 1784 */         uc.setallowOPRProcess(allowOPRProcess);
/* 1785 */         uc.setallowAdminTelnet(allowAdminTelnet);
/* 1786 */         uc.setDrilldown();
/* 1787 */         uc.setallowDownTimeSchedule(allowDownTimeSchedule);
/* 1788 */         uc.setallowOprViewAllDownTimeSchedule(allowOprViewAllDownTimeSchedule);
/* 1789 */         uc.setAllowAdminWindowsServices(allowAdminWindowsServices);
/* 1790 */         uc.setCreateDomainUser(createDomainUser);
/* 1791 */         uc.setAllowClearAlarms(allowClearAlarms);
/* 1792 */         uc.setEnableRestrictedAdmin(enableRestrictedAdmin);
/* 1793 */         uc.setAllowNonAdminUsersEditUsername(allowNonAdminUsersEditUsername);
/*      */         
/* 1795 */         uc.setAllowDAdminViewAllThresholds(DBUtil.getGlobalConfigValue("allowDAdminViewAllThresholds"));
/* 1796 */         uc.setAllowDAdminViewAllActions(DBUtil.getGlobalConfigValue("allowDAdminViewAllActions"));
/* 1797 */         uc.setAllowOperatorEditTabs("".equals(DBUtil.getGlobalConfigValue("allowOperatorEditTabs")) ? "true" : DBUtil.getGlobalConfigValue("allowOperatorEditTabs"));
/*      */       }
/* 1799 */     } catch (Exception ee) { ee.printStackTrace();
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1804 */       ArrayList perm1 = this.mo.getRows("select VALUE from AM_GLOBALCONFIG where NAME='allowAL' or NAME='allowCMD' or NAME='allowCS'  or NAME='allowCSU' or NAME='allowDT' or NAME='allowJOB' or NAME='allowLL' or NAME='allowML' or NAME='allowMSG' or NAME='allowNA' or NAME='allowSC' or NAME='allowSPL' or NAME='allowSUB' or NAME='allowAS400' order by NAME");
/* 1805 */       System.out.println("AS400 Audit------------->" + perm1);
/* 1806 */       String allowAL = (String)((ArrayList)perm1.get(0)).get(0);
/* 1807 */       String allowAS400 = (String)((ArrayList)perm1.get(1)).get(0);
/* 1808 */       String allowCMD = (String)((ArrayList)perm1.get(2)).get(0);
/* 1809 */       String allowCS = (String)((ArrayList)perm1.get(3)).get(0);
/* 1810 */       String allowCSU = (String)((ArrayList)perm1.get(4)).get(0);
/* 1811 */       String allowDT = (String)((ArrayList)perm1.get(5)).get(0);
/* 1812 */       String allowJOB = (String)((ArrayList)perm1.get(6)).get(0);
/* 1813 */       String allowLL = (String)((ArrayList)perm1.get(7)).get(0);
/* 1814 */       String allowML = (String)((ArrayList)perm1.get(8)).get(0);
/* 1815 */       String allowMSG = (String)((ArrayList)perm1.get(9)).get(0);
/* 1816 */       String allowNA = (String)((ArrayList)perm1.get(10)).get(0);
/* 1817 */       String allowSC = (String)((ArrayList)perm1.get(11)).get(0);
/* 1818 */       String allowSPL = (String)((ArrayList)perm1.get(12)).get(0);
/* 1819 */       String allowSUB = (String)((ArrayList)perm1.get(13)).get(0);
/*      */       
/* 1821 */       uc.setAllowAL(allowAL);
/* 1822 */       uc.setAllowCMD(allowCMD);
/* 1823 */       uc.setAllowCS(allowCS);
/* 1824 */       uc.setAllowCSU(allowCSU);
/* 1825 */       uc.setAllowDT(allowDT);
/* 1826 */       uc.setAllowJOB(allowJOB);
/* 1827 */       uc.setAllowLL(allowLL);
/* 1828 */       uc.setAllowML(allowML);
/* 1829 */       uc.setAllowMSG(allowMSG);
/* 1830 */       uc.setAllowNA(allowNA);
/* 1831 */       uc.setAllowSC(allowSC);
/* 1832 */       uc.setAllowSPL(allowSPL);
/* 1833 */       uc.setAllowSUB(allowSUB);
/* 1834 */       uc.setAllowAS400(allowAS400);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1839 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1847 */       ArrayList accPolicy = this.mo.getRows("select VALUE from AM_GLOBALCONFIG where NAME='am.admin.usermgmt.accountlockout.enabled' or NAME='am.admin.usermgmt.pwdpolicy.enabled' or NAME='am.admin.usermgmt.singleusersession.enabled' or NAME='am.admin.usermgmt.accountlockout.count' or NAME='am.admin.usermgmt.accountlockout.timeout' order by NAME");
/*      */       
/* 1849 */       System.out.println("Account Policy Feature Enabled status------------->" + accPolicy);
/*      */       
/* 1851 */       String accLockOutCount = (String)((ArrayList)accPolicy.get(0)).get(0);
/* 1852 */       String accLockout = (String)((ArrayList)accPolicy.get(1)).get(0);
/* 1853 */       String accLockoutTimeout = (String)((ArrayList)accPolicy.get(2)).get(0);
/* 1854 */       String pwdPolicy = (String)((ArrayList)accPolicy.get(3)).get(0);
/* 1855 */       String singleSession = (String)((ArrayList)accPolicy.get(4)).get(0);
/*      */       
/*      */ 
/* 1858 */       uc.setPwdPolicy(pwdPolicy);
/* 1859 */       uc.setSingleSession(singleSession);
/* 1860 */       uc.setAccLockout(accLockout);
/* 1861 */       uc.setAccLockoutTimeout(accLockoutTimeout);
/* 1862 */       uc.setAccLockOutCount(accLockOutCount);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1867 */       e.printStackTrace();
/*      */     }
/* 1869 */     uc.setssoenable(DBUtil.getGlobalConfigValue("am.sso.enabled"));
/*      */     
/*      */ 
/* 1872 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */   private void updateGroups(UserConfiguration uc)
/*      */   {
/* 1877 */     String[] groups = uc.getGroups();
/*      */     
/* 1879 */     String username = uc.getUserName();
/*      */     
/* 1881 */     this.mo.executeUpdateStmt("delete from AM_UserGroupTable where  USERNAME='" + username + "'");
/*      */     
/*      */ 
/* 1884 */     for (int i = 0; i < groups.length; i++)
/*      */     {
/* 1886 */       if (groups[i].equalsIgnoreCase("ADMIN"))
/*      */       {
/* 1888 */         String insertquery = "insert into AM_UserGroupTable   values ('" + uc.getUserName() + "','USERS')";
/* 1889 */         this.mo.executeUpdateStmt(insertquery);
/*      */       }
/*      */       
/* 1892 */       if ((EnterpriseUtil.isAdminServer()) && (groups[i].equalsIgnoreCase("ADMIN")))
/*      */       {
/* 1894 */         this.mo.executeUpdateStmt("insert into AM_UserGroupTable values ('" + username + "','ENTERPRISEADMIN')");
/*      */       }
/*      */       else
/*      */       {
/* 1898 */         this.mo.executeUpdateStmt("insert into AM_UserGroupTable values ('" + username + "','" + groups[i] + "')");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward updateView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1908 */       HttpSession session = request.getSession();
/* 1909 */       ServletContext ctx = session.getServletContext();
/* 1910 */       String drilldown = request.getParameter("drilldown");
/* 1911 */       String query = null;
/* 1912 */       System.out.println("The drilldown value in the updateview is ===>" + drilldown);
/* 1913 */       if ((drilldown != null) && (drilldown.equals("true"))) {
/* 1914 */         query = "update AM_GLOBALCONFIG SET VALUE='true' WHERE NAME='am.webclient.mgdrilldown'";
/* 1915 */         ctx.setAttribute("mgdrilldown", "true");
/* 1916 */       } else if ((drilldown != null) && (drilldown.equals("false"))) {
/* 1917 */         query = "update AM_GLOBALCONFIG SET VALUE='false' WHERE NAME='am.webclient.mgdrilldown'";
/* 1918 */         ctx.setAttribute("mgdrilldown", "false");
/*      */       }
/* 1920 */       if (query != null) {
/* 1921 */         this.mo.executeUpdateStmt(query);
/*      */       }
/*      */     }
/*      */     catch (Exception exc) {
/* 1925 */       exc.printStackTrace();
/*      */     }
/* 1927 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updatePermission(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1938 */       String allowManage = request.getParameter("allowManage");
/* 1939 */       String allowReset = request.getParameter("allowReset");
/* 1940 */       String allowExecute = request.getParameter("allowExecute");
/* 1941 */       String allowServices = request.getParameter("allowServices");
/* 1942 */       String allowUpdateIP = request.getParameter("allowUpdateIP");
/* 1943 */       String allowEdit = request.getParameter("allowEdit");
/* 1944 */       String allowOPRTelnet = request.getParameter("allowOPRTelnet");
/* 1945 */       String allowOPRProcess = request.getParameter("allowOPRProcess");
/* 1946 */       String allowAdminTelnet = request.getParameter("allowAdminTelnet");
/* 1947 */       String allowJumptoLink = request.getParameter("allowJumptoLink");
/* 1948 */       String allowDownTimeSchedule = request.getParameter("allowDownTimeSchedule");
/* 1949 */       String allowOprViewAllDownTimeSchedule = request.getParameter("allowOprViewAllDownTimeSchedule");
/* 1950 */       String allowAdminWindowsServices = request.getParameter("allowAdminWindowsServices");
/* 1951 */       String allowUpdateConfig = request.getParameter("allowUpdateConfig");
/* 1952 */       String allowManageDB = request.getParameter("allowManageDB");
/* 1953 */       String createDomainUser = request.getParameter("createDomainUser");
/* 1954 */       String allowClearAlarms = request.getParameter("allowClearAlarms");
/* 1955 */       String restricedAdmin = request.getParameter("disableRestrictedAdmin");
/* 1956 */       String allowDAdminViewAllThresholds = request.getParameter("allowDAdminViewAllThresholds");
/* 1957 */       String allowDAdminViewAllActions = request.getParameter("allowDAdminViewAllActions");
/* 1958 */       String allowOperatorEditTabs = request.getParameter("allowOperatorEditTabs");
/* 1959 */       String allowNonAdminUsersEditUsername = request.getParameter("allowNonAdminUsersEditUsername");
/*      */       
/* 1961 */       DBUtil.updateGlobalConfigValue("allowDAdminViewAllThresholds", allowDAdminViewAllThresholds);
/* 1962 */       DBUtil.updateGlobalConfigValue("allowDAdminViewAllActions", allowDAdminViewAllActions);
/* 1963 */       DBUtil.updateGlobalConfigValue("allowOperatorManage", allowManage);
/* 1964 */       DBUtil.updateGlobalConfigValue("allowOperatorUnmanageAndReset", allowReset);
/* 1965 */       DBUtil.updateGlobalConfigValue("allowOperatorExecuteAction", allowExecute);
/* 1966 */       DBUtil.updateGlobalConfigValue("allowOperatorServices", allowServices);
/* 1967 */       DBUtil.updateGlobalConfigValue("allowOperatorUpdateIP", allowUpdateIP);
/* 1968 */       DBUtil.updateGlobalConfigValue("allowOperatorEdit", allowEdit);
/* 1969 */       DBUtil.updateGlobalConfigValue("allowUpdateConfig", allowUpdateConfig);
/* 1970 */       DBUtil.updateGlobalConfigValue("allowManageDB", allowManageDB);
/*      */       
/* 1972 */       if (allowNonAdminUsersEditUsername != null) {
/* 1973 */         DBUtil.insertOrUpdateServerConfigValue("allowNonAdminUsersEditUsername", allowNonAdminUsersEditUsername, 4);
/*      */       }
/* 1975 */       if (createDomainUser != null) {
/* 1976 */         DBUtil.updateGlobalConfigValue("createDomainUser", createDomainUser);
/*      */       }
/*      */       
/* 1979 */       if (allowClearAlarms != null) {
/* 1980 */         DBUtil.updateGlobalConfigValue("allowClearAlarms", allowClearAlarms);
/*      */       }
/*      */       
/* 1983 */       if (allowOPRTelnet != null) {
/* 1984 */         DBUtil.updateGlobalConfigValue("allowOPRTelnet", allowOPRTelnet);
/*      */       }
/*      */       
/* 1987 */       if (allowOPRProcess != null) {
/* 1988 */         DBUtil.updateGlobalConfigValue("allowOPRProcess", allowOPRProcess);
/*      */       }
/*      */       
/* 1991 */       if (allowAdminTelnet != null) {
/* 1992 */         DBUtil.updateGlobalConfigValue("allowAdminTelnet", allowAdminTelnet);
/*      */       }
/*      */       
/* 1995 */       if (allowAdminWindowsServices != null) {
/* 1996 */         DBUtil.updateGlobalConfigValue("allowAdminWindowsServices", allowAdminWindowsServices);
/*      */       }
/*      */       
/* 1999 */       if (allowJumptoLink != null) {
/* 2000 */         DBUtil.updateGlobalConfigValue("allowJumptoLink", allowJumptoLink);
/*      */       }
/*      */       
/* 2003 */       if (allowDownTimeSchedule != null) {
/* 2004 */         DBUtil.updateGlobalConfigValue("allowDownTimeSchedule", allowDownTimeSchedule);
/*      */       }
/*      */       
/* 2007 */       if (allowOprViewAllDownTimeSchedule != null) {
/* 2008 */         DBUtil.updateGlobalConfigValue("allowOprViewAllDownTimeSchedule", allowOprViewAllDownTimeSchedule);
/*      */       }
/*      */       
/* 2011 */       if (allowOperatorEditTabs != null) {
/* 2012 */         DBUtil.updateGlobalConfigValue("allowOperatorEditTabs", allowOperatorEditTabs);
/*      */       }
/*      */       
/* 2015 */       if (restricedAdmin != null) {
/* 2016 */         String oldvalue = DBUtil.getGlobalConfigValue("disableRestrictedAdmin");
/* 2017 */         if (!oldvalue.equalsIgnoreCase(restricedAdmin)) {
/* 2018 */           DBUtil.updateGlobalConfigValue("disableRestrictedAdmin", restricedAdmin);
/* 2019 */           if ((Constants.isSsoEnabled()) && (EnterpriseUtil.isAdminServer())) {
/* 2020 */             HashMap<String, String> params = new HashMap();
/* 2021 */             params.put("key", "disableRestrictedAdmin");
/* 2022 */             params.put("value", restricedAdmin);
/* 2023 */             params.put("apicallfrom", "admin");
/* 2024 */             MASSyncUtil.addTasktoSync(params, "/AppManager/xml/globalconfig/update", "all", "POST", 9, 2);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2029 */       Hashtable hash = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 2030 */       hash.put("allowDAdminViewAllThresholds", allowDAdminViewAllThresholds);
/* 2031 */       hash.put("allowDAdminViewAllActions", allowDAdminViewAllActions);
/* 2032 */       hash.put("allowOperatorManage", allowManage);
/* 2033 */       hash.put("allowOperatorUnmanageAndReset", allowReset);
/* 2034 */       hash.put("allowOperatorExecuteAction", allowExecute);
/* 2035 */       hash.put("allowOperatorServices", allowServices);
/* 2036 */       hash.put("allowOperatorUpdateIP", allowUpdateIP);
/* 2037 */       hash.put("allowOperatorEdit", allowEdit);
/* 2038 */       if (allowOPRTelnet != null) {
/* 2039 */         hash.put("allowOPRTelnet", allowOPRTelnet);
/*      */       }
/* 2041 */       if (allowOPRProcess != null) {
/* 2042 */         hash.put("allowOPRProcess", allowOPRProcess);
/*      */       }
/* 2044 */       if (allowAdminTelnet != null) {
/* 2045 */         hash.put("allowAdminTelnet", allowAdminTelnet);
/*      */       }
/* 2047 */       if (allowJumptoLink != null) {
/* 2048 */         hash.put("allowJumptoLink", allowJumptoLink);
/*      */       }
/* 2050 */       if (allowDownTimeSchedule != null) {
/* 2051 */         hash.put("allowDownTimeSchedule", allowDownTimeSchedule);
/*      */       }
/* 2053 */       if (allowOprViewAllDownTimeSchedule != null) {
/* 2054 */         hash.put("allowOprViewAllDownTimeSchedule", allowOprViewAllDownTimeSchedule);
/*      */       }
/* 2056 */       if (allowAdminWindowsServices != null) {
/* 2057 */         hash.put("allowAdminWindowsServices", allowAdminWindowsServices);
/*      */       }
/* 2059 */       if (allowUpdateConfig != null) {
/* 2060 */         hash.put("allowUpdateConfig", allowUpdateConfig);
/*      */       }
/* 2062 */       if (allowManageDB != null) {
/* 2063 */         hash.put("allowManageDB", allowManageDB);
/*      */       }
/* 2065 */       if (createDomainUser != null) {
/* 2066 */         hash.put("createDomainUser", createDomainUser);
/*      */       }
/* 2068 */       if (allowClearAlarms != null) {
/* 2069 */         hash.put("allowClearAlarms", allowClearAlarms);
/*      */       }
/* 2071 */       if (restricedAdmin != null) {
/* 2072 */         hash.put("disableRestrictedAdmin", restricedAdmin);
/*      */       }
/* 2074 */       if (allowOperatorEditTabs != null) {
/* 2075 */         hash.put("allowOperatorEditTabs", allowOperatorEditTabs);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2080 */       e.printStackTrace();
/*      */     }
/* 2082 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updatePermissionForTelnetClient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2090 */     String query = "update AM_GLOBALCONFIG set VALUE='false' where NAME='allowAdminTelnet'";
/* 2091 */     this.mo.executeUpdateStmt(query);
/* 2092 */     Hashtable hash = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 2093 */     hash.put("allowAdminTelnet", "false");
/* 2094 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateas400Permission(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 2106 */       String allowAL = request.getParameter("allowAL");
/* 2107 */       String allowCMD = request.getParameter("allowCMD");
/* 2108 */       String allowCS = request.getParameter("allowCS");
/* 2109 */       String allowCSU = request.getParameter("allowCSU");
/* 2110 */       String allowDT = request.getParameter("allowDT");
/* 2111 */       String allowJOB = request.getParameter("allowJOB");
/* 2112 */       String allowLL = request.getParameter("allowLL");
/* 2113 */       String allowML = request.getParameter("allowML");
/* 2114 */       String allowMSG = request.getParameter("allowMSG");
/* 2115 */       String allowNA = request.getParameter("allowNA");
/* 2116 */       String allowSC = request.getParameter("allowSC");
/* 2117 */       String allowSPL = request.getParameter("allowSPL");
/* 2118 */       String allowSUB = request.getParameter("allowSUB");
/* 2119 */       String allowAS400 = request.getParameter("allowAS400");
/*      */       
/*      */ 
/* 2122 */       String query = "update AM_GLOBALCONFIG set VALUE='" + allowAL + "' where NAME='allowAL'";
/* 2123 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2125 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowCMD + "' where NAME='allowCMD'";
/* 2126 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2128 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowCS + "' where NAME='allowCS'";
/* 2129 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2131 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowCSU + "' where NAME='allowCSU'";
/* 2132 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2134 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowDT + "' where NAME='allowDT'";
/* 2135 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2137 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowJOB + "' where NAME='allowJOB'";
/* 2138 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2140 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowLL + "' where NAME='allowLL'";
/* 2141 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2143 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowML + "' where NAME='allowML'";
/* 2144 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2146 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowMSG + "' where NAME='allowMSG'";
/* 2147 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2149 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowNA + "' where NAME='allowNA'";
/* 2150 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2152 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowSC + "' where NAME='allowSC'";
/* 2153 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2155 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowSPL + "' where NAME='allowSPL'";
/* 2156 */       this.mo.executeUpdateStmt(query);
/*      */       
/*      */ 
/* 2159 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowSUB + "' where NAME='allowSUB'";
/* 2160 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2162 */       query = "update AM_GLOBALCONFIG set VALUE='" + allowAS400 + "' where NAME='allowAS400'";
/* 2163 */       this.mo.executeUpdateStmt(query);
/*      */       
/* 2165 */       Hashtable hash = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 2166 */       hash.put("allowAL", allowAL);
/* 2167 */       hash.put("allowCMD", allowCMD);
/* 2168 */       hash.put("allowCS", allowCS);
/* 2169 */       hash.put("allowCSU", allowCSU);
/* 2170 */       hash.put("allowDT", allowDT);
/* 2171 */       hash.put("allowJOB", allowJOB);
/* 2172 */       hash.put("allowLL", allowLL);
/* 2173 */       hash.put("allowML", allowML);
/* 2174 */       hash.put("allowMSG", allowMSG);
/* 2175 */       hash.put("allowNA", allowNA);
/* 2176 */       hash.put("allowSC", allowSC);
/* 2177 */       hash.put("allowSPL", allowSPL);
/* 2178 */       hash.put("allowSUB", allowSUB);
/* 2179 */       hash.put("allowAS400", allowAS400);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2184 */       e.printStackTrace();
/*      */     }
/* 2186 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateSSO(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2194 */     String enablesso = request.getParameter("ssoenable");
/*      */     try {
/* 2196 */       String confFilePath = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + "conf" + File.separator + "AMServer.properties";
/* 2197 */       AMCacheHandler.updateOrInsertPropinConfFile(confFilePath, "am.sso.enabled", enablesso, "=");
/* 2198 */       AMCacheHandler.updateOrInsertPropinConfFile(confFilePath, "am.user.resource.enabled", enablesso, "=");
/* 2199 */       DBUtil.updateGlobalConfigValue("am.sso.enabled", enablesso);
/* 2200 */       if (EnterpriseUtil.isAdminServer()) {
/* 2201 */         HashMap<String, String> params = new HashMap();
/* 2202 */         params.put("am.sso.enabled", enablesso);
/* 2203 */         params.put("apicallfrom", "admin");
/* 2204 */         Vector serverids = MASSyncUtil.getallServerIDS();
/* 2205 */         for (int i = 0; i < serverids.size(); i++) {
/* 2206 */           MASSyncUtil.addTasktoSync(params, "/AppManager/xml/ssoprops/update", serverids.get(i).toString(), "POST", 9, 2);
/*      */         }
/*      */       }
/*      */     } catch (Exception e1) {
/* 2210 */       e1.printStackTrace();
/*      */     }
/* 2212 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateAccPolicy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 2222 */       String accLockout = request.getParameter("accLockout");
/* 2223 */       String singleSession = request.getParameter("singleSession");
/* 2224 */       String pwdPolicy = request.getParameter("pwdPolicy");
/* 2225 */       String accLockoutTimeout = request.getParameter("accLockoutTimeout");
/* 2226 */       String accLockOutCount = request.getParameter("accLockOutCount");
/*      */       
/* 2228 */       DBUtil.updateGlobalConfigValue("am.admin.usermgmt.accountlockout.enabled", accLockout);
/* 2229 */       DBUtil.updateGlobalConfigValue("am.admin.usermgmt.singleusersession.enabled", singleSession);
/* 2230 */       DBUtil.updateGlobalConfigValue("am.admin.usermgmt.pwdpolicy.enabled", pwdPolicy);
/* 2231 */       DBUtil.updateGlobalConfigValue("am.admin.usermgmt.accountlockout.timeout", accLockoutTimeout);
/* 2232 */       DBUtil.updateGlobalConfigValue("am.admin.usermgmt.accountlockout.count", accLockOutCount);
/*      */       
/* 2234 */       Hashtable hash = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 2235 */       hash.put("accLockout", accLockout);
/* 2236 */       hash.put("accLockoutTimeout", accLockoutTimeout);
/* 2237 */       hash.put("accLockOutCount", accLockOutCount);
/* 2238 */       hash.put("singleSession", singleSession);
/* 2239 */       hash.put("pwdPolicy", pwdPolicy);
/*      */       
/*      */ 
/*      */ 
/* 2243 */       UserSessionHandler.accLockoutStatus = Boolean.parseBoolean(accLockout);
/* 2244 */       UserSessionHandler.singleUserStatus = Boolean.parseBoolean(singleSession);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 2250 */       e.printStackTrace();
/*      */     }
/* 2252 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward fetchLDAPUserGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 2258 */       String domainName = request.getParameter("domainName");
/* 2259 */       String domainController = request.getParameter("domainController");
/* 2260 */       String domainUsername = request.getParameter("domainUsername");
/* 2261 */       String domainPassword = request.getParameter("domainPassword");
/* 2262 */       String domainValue = request.getParameter("domainValue");
/* 2263 */       String searchFilter = request.getParameter("searchFilter");
/* 2264 */       String searchbase = request.getParameter("searchbase");
/* 2265 */       String fetchLdap = request.getParameter("fetchValue");
/*      */       
/* 2267 */       int ldapport = 389;
/* 2268 */       int domainID = 0;
/* 2269 */       ArrayList domainDetails = new ArrayList();
/*      */       try {
/* 2271 */         ldapport = Integer.valueOf(request.getParameter("port")).intValue();
/*      */       } catch (NumberFormatException num) {
/* 2273 */         num.printStackTrace();
/*      */       }
/* 2275 */       if ((domainValue != null) && (!"new".equals(domainValue)))
/*      */       {
/* 2277 */         domainID = Integer.parseInt(domainValue);
/* 2278 */         domainDetails = ADAuthenticationUtil.getDomainDetails(domainValue);
/* 2279 */         if (domainDetails != null)
/*      */         {
/* 2281 */           domainName = (String)domainDetails.get(1);
/* 2282 */           domainController = (String)domainDetails.get(2);
/*      */         }
/*      */       }
/*      */       
/* 2286 */       ADAuthenticationUtil.setPortNumber(ldapport);
/* 2287 */       Map paramMap = new HashMap();
/* 2288 */       paramMap.put("domainId", Integer.valueOf(domainID));
/* 2289 */       paramMap.put("domainName", domainName);
/* 2290 */       paramMap.put("domainController", domainController);
/* 2291 */       paramMap.put("domainUsername", domainUsername);
/* 2292 */       paramMap.put("domainPassword", domainPassword);
/* 2293 */       paramMap.put("searchbase", searchbase);
/* 2294 */       paramMap.put("searchText", searchFilter);
/* 2295 */       paramMap.put("server", "ldap");
/* 2296 */       paramMap.put("importdata", fetchLdap);
/* 2297 */       ArrayList userList = ADAuthenticationUtil.fetchADUserList(paramMap);
/*      */       
/* 2299 */       if ((userList != null) && (userList.size() > 0) && (domainID == 0))
/*      */       {
/* 2301 */         domainID = ADAuthenticationUtil.getDomainID(domainName);
/*      */         
/* 2303 */         if (domainID == 0) {
/* 2304 */           Map detailsMap = new HashMap();
/* 2305 */           detailsMap.put("domainName", domainName);
/* 2306 */           detailsMap.put("domainController", domainController);
/* 2307 */           detailsMap.put("domainUsername", domainUsername);
/* 2308 */           detailsMap.put("authentication", "LDAP");
/* 2309 */           detailsMap.put("domainPassword", domainPassword);
/* 2310 */           domainID = ADAuthenticationUtil.insertDomainDetails(detailsMap);
/*      */         }
/*      */       } else {
/* 2313 */         if ((userList == null) || (userList.size() == 0))
/*      */         {
/* 2315 */           request.setAttribute("errorMessage", " Authorization error ; Kindly reconfigure.");
/*      */           
/* 2317 */           return new ActionForward("/jsp/ADUsersList.jsp");
/*      */         }
/* 2319 */         if ((userList != null) && (userList.size() > 0) && (domainID > 0))
/*      */         {
/* 2321 */           Map detailsMap = new HashMap();
/* 2322 */           detailsMap.put("domainUsername", domainUsername);
/* 2323 */           detailsMap.put("domainController", domainController);
/* 2324 */           detailsMap.put("domainID", Integer.valueOf(domainID));
/* 2325 */           detailsMap.put("domainPort", Integer.valueOf(ldapport));
/* 2326 */           detailsMap.put("authentication", domainDetails.get(6));
/* 2327 */           detailsMap.put("domainPassword", domainPassword);
/* 2328 */           detailsMap.put("domainPermission", domainDetails.get(7));
/*      */           
/*      */ 
/* 2331 */           ADAuthenticationUtil.updateDomainDetails(detailsMap, true);
/*      */         } }
/* 2333 */       int maxUsers = FreeEditionDetails.getFreeEditionDetails().getNumberOfUsersPermitted();
/* 2334 */       int permittdUsers = -1;
/* 2335 */       if (maxUsers != -1) {
/* 2336 */         permittdUsers = maxUsers - DBUtil.getNumberOfUsers();
/*      */       }
/* 2338 */       request.setAttribute("maximumUsers", Integer.valueOf(permittdUsers));
/* 2339 */       request.setAttribute("domainID", Integer.valueOf(domainID));
/* 2340 */       request.setAttribute("adUsersList", userList);
/* 2341 */       request.setAttribute("domainPassword", domainPassword);
/* 2342 */       request.setAttribute("searchBase", searchbase);
/* 2343 */       request.setAttribute("showlist", fetchLdap);
/* 2344 */       request.setAttribute("domainName", domainName);
/* 2345 */       request.setAttribute("authenticateMode", "Ldap");
/*      */       
/* 2347 */       request.setAttribute("operatorAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.operatorrole.remove")));
/* 2348 */       request.setAttribute("userAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.userrole.remove")));
/* 2349 */       request.setAttribute("adminAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.adminrole.remove")));
/* 2350 */       request.setAttribute("managerAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.managerrole.remove")));
/*      */     }
/*      */     catch (Exception ex) {
/* 2353 */       ex.printStackTrace();
/*      */     }
/* 2355 */     return new ActionForward("/jsp/ADUsersList.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward editDomainConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2360 */     ResultSet rs = null;
/*      */     try {
/* 2362 */       String domainid = request.getParameter("domainid");
/* 2363 */       UserConfiguration uc = (UserConfiguration)form;
/* 2364 */       HashMap<String, String> details = new HashMap();
/* 2365 */       rs = AMConnectionPool.executeQueryStmt("select DOMAINNAME,DNSHOST,PORT,AUTHENTICATION,PERMISSION,SSLENABLED from AM_DOMAINCONTROLLERS where ID=" + domainid);
/* 2366 */       if (rs.next()) {
/* 2367 */         uc.setNewDomainName(rs.getString("DOMAINNAME"));
/* 2368 */         uc.setNewDomainController(rs.getString("DNSHOST"));
/* 2369 */         uc.setNewDomainPort(rs.getInt("PORT"));
/* 2370 */         uc.setNewDomainService(rs.getString("AUTHENTICATION"));
/* 2371 */         uc.setNewDomainPermission(rs.getInt("PERMISSION"));
/* 2372 */         boolean sslenabled = rs.getInt("SSLENABLED") == 1;
/* 2373 */         uc.setsslenable(sslenabled);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2377 */       ex.printStackTrace();
/*      */     } finally {
/* 2379 */       if (rs != null) {
/* 2380 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/*      */     
/* 2384 */     request.setAttribute("editDomainConfig", Boolean.valueOf(true));
/* 2385 */     return new ActionForward("Tile.usergroups.Conf");
/*      */   }
/*      */   
/*      */   public ActionForward updateDomainConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 2391 */       UserConfiguration uc = (UserConfiguration)form;
/* 2392 */       String domainid = uc.getDomainID();
/* 2393 */       Map details = new HashMap();
/* 2394 */       details.put("domainName", uc.getNewDomainName());
/* 2395 */       details.put("domainController", uc.getNewDomainController());
/* 2396 */       details.put("domainPort", Integer.valueOf(uc.getNewDomainPort()));
/* 2397 */       details.put("authentication", uc.getNewDomainService());
/* 2398 */       details.put("domainID", Integer.valueOf(Integer.parseInt(domainid)));
/* 2399 */       details.put("domainPermission", Integer.valueOf(uc.getNewDomainPermission()));
/* 2400 */       details.put("sslenabled", String.valueOf(uc.getsslenable()));
/* 2401 */       status = ADAuthenticationUtil.updateDomainDetails(details, false);
/*      */     } catch (Exception ex) { boolean status;
/* 2403 */       ex.printStackTrace();
/*      */     }
/* 2405 */     request.setAttribute("showTab", "domaindetails");
/* 2406 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward addDomainConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 2412 */       UserConfiguration uc = (UserConfiguration)form;
/* 2413 */       ADAuthenticationUtil.setPortNumber(uc.getNewDomainPort());
/* 2414 */       Map detailsMap = new HashMap();
/* 2415 */       detailsMap.put("domainName", uc.getNewDomainName());
/* 2416 */       detailsMap.put("domainController", uc.getNewDomainController());
/* 2417 */       detailsMap.put("authentication", uc.getNewDomainService());
/* 2418 */       detailsMap.put("domainPermission", uc.getNewDomainPermission() + "");
/* 2419 */       detailsMap.put("sslenabled", String.valueOf(uc.getsslenable()));
/* 2420 */       int domainID = ADAuthenticationUtil.insertDomainDetails(detailsMap);
/* 2421 */       request.setAttribute("showTab", "domaindetails");
/*      */     }
/*      */     catch (Exception ex) {
/* 2424 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2427 */     return showUsers(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward fetchADUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2432 */     String domainName = request.getParameter("domainName");
/* 2433 */     String domainController = request.getParameter("domainController");
/* 2434 */     String domainUsername = request.getParameter("domainUsername");
/* 2435 */     String domainPassword = org.htmlparser.util.Translate.decode(request.getParameter("domainPassword"));
/* 2436 */     String domainValue = request.getParameter("domainValue");
/* 2437 */     String searchFilter = request.getParameter("searchFilter");
/* 2438 */     String fetchad = request.getParameter("fetchValue");
/* 2439 */     String authType = request.getParameter("authType");
/* 2440 */     String authenticationMode = "ad";
/*      */     
/* 2442 */     int port = 389;
/* 2443 */     ArrayList domainDetails = new ArrayList();
/* 2444 */     String sslenabled = "false";
/*      */     try {
/* 2446 */       port = Integer.valueOf(request.getParameter("port")).intValue();
/*      */     } catch (NumberFormatException num) {
/* 2448 */       num.printStackTrace();
/*      */     }
/* 2450 */     ADAuthenticationUtil.setPortNumber(port);
/* 2451 */     int domainID = 0;
/*      */     try
/*      */     {
/* 2454 */       if ((domainValue != null) && (!"new".equals(domainValue)))
/*      */       {
/* 2456 */         domainID = Integer.parseInt(domainValue);
/* 2457 */         domainDetails = ADAuthenticationUtil.getDomainDetails(domainValue);
/* 2458 */         if (domainDetails != null)
/*      */         {
/* 2460 */           domainName = (String)domainDetails.get(1);
/* 2461 */           domainController = (String)domainDetails.get(2);
/* 2462 */           sslenabled = (String)domainDetails.get(8);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2467 */       if ("LDAP".equalsIgnoreCase(authType)) {
/* 2468 */         authenticationMode = "ldap";
/*      */       }
/*      */       
/* 2471 */       Map paramMap = new HashMap();
/* 2472 */       paramMap.put("domainName", domainName);
/* 2473 */       paramMap.put("domainController", domainController);
/* 2474 */       paramMap.put("domainUsername", domainUsername);
/* 2475 */       paramMap.put("domainPassword", domainPassword);
/* 2476 */       paramMap.put("searchText", searchFilter);
/* 2477 */       paramMap.put("server", authenticationMode);
/* 2478 */       paramMap.put("importdata", fetchad);
/* 2479 */       paramMap.put("domainId", Integer.valueOf(domainID));
/* 2480 */       paramMap.put("sslenabled", sslenabled);
/*      */       
/* 2482 */       ArrayList userList = ADAuthenticationUtil.fetchADUserList(paramMap);
/*      */       
/* 2484 */       if ((userList != null) && (userList.size() > 0) && (domainID == 0))
/*      */       {
/* 2486 */         domainID = ADAuthenticationUtil.getDomainID(domainName);
/*      */         
/* 2488 */         if (domainID == 0)
/*      */         {
/* 2490 */           Map<String, String> detailsMap = new HashMap();
/* 2491 */           detailsMap.put("domainName", domainName);
/* 2492 */           detailsMap.put("domainController", domainController);
/* 2493 */           detailsMap.put("domainUsername", domainUsername);
/* 2494 */           detailsMap.put("authentication", authType);
/* 2495 */           detailsMap.put("domainPassword", domainPassword);
/* 2496 */           detailsMap.put("domainPort", String.valueOf(port));
/* 2497 */           detailsMap.put("sslenabled", String.valueOf(sslenabled));
/* 2498 */           domainID = ADAuthenticationUtil.insertDomainDetails(detailsMap);
/*      */         }
/*      */       } else {
/* 2501 */         if ((userList == null) || (userList.size() == 0))
/*      */         {
/* 2503 */           request.setAttribute("errorMessage", " Authorization error ; Kindly reconfigure.");
/*      */           
/* 2505 */           return new ActionForward("/jsp/ADUsersList.jsp");
/*      */         }
/* 2507 */         if ((userList != null) && (userList.size() > 0) && (domainID > 0))
/*      */         {
/* 2509 */           Map detailsMap = new HashMap();
/* 2510 */           detailsMap.put("domainUsername", domainUsername);
/* 2511 */           detailsMap.put("domainController", domainController);
/* 2512 */           detailsMap.put("domainID", Integer.valueOf(domainID));
/* 2513 */           detailsMap.put("domainPort", Integer.valueOf(port));
/* 2514 */           detailsMap.put("authentication", authType);
/* 2515 */           detailsMap.put("domainPassword", domainPassword);
/* 2516 */           detailsMap.put("domainPermission", domainDetails.get(7));
/* 2517 */           detailsMap.put("sslenabled", sslenabled);
/* 2518 */           ADAuthenticationUtil.updateDomainDetails(detailsMap, true);
/*      */         } }
/* 2520 */       int maxUsers = FreeEditionDetails.getFreeEditionDetails().getNumberOfUsersPermitted();
/* 2521 */       int permittdUsers = -1;
/* 2522 */       if (maxUsers != -1) {
/* 2523 */         permittdUsers = maxUsers - DBUtil.getNumberOfUsers();
/*      */       }
/* 2525 */       request.setAttribute("maximumUsers", Integer.valueOf(permittdUsers));
/* 2526 */       request.setAttribute("domainID", Integer.valueOf(domainID));
/* 2527 */       request.setAttribute("adUsersList", userList);
/* 2528 */       request.setAttribute("domainName", domainName);
/* 2529 */       request.setAttribute("domainController", domainController);
/* 2530 */       request.setAttribute("domainUsername", domainUsername);
/* 2531 */       request.setAttribute("domainPassword", domainPassword);
/* 2532 */       request.setAttribute("searchFilter", searchFilter);
/* 2533 */       request.setAttribute("authenticateMode", authenticationMode);
/* 2534 */       request.setAttribute("showlist", fetchad);
/* 2535 */       ArrayList applications = com.adventnet.appmanager.struts.beans.AlarmUtil.getConfiguredGroups(null, false, true);
/* 2536 */       request.setAttribute("mgapplications", applications);
/* 2537 */       request.setAttribute("applications", applications);
/* 2538 */       HashMap<String, ArrayList<HashMap<String, String>>> usergroupList = UserConfigurationUtil.getAllUserGroup();
/* 2539 */       request.setAttribute("usergroupList", usergroupList);
/* 2540 */     } catch (Exception ee) { ee.printStackTrace(); }
/* 2541 */     request.setAttribute("operatorAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.operatorrole.remove")));
/* 2542 */     request.setAttribute("userAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.userrole.remove")));
/* 2543 */     request.setAttribute("adminAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.adminrole.remove")));
/* 2544 */     request.setAttribute("managerAllowed", Boolean.valueOf(!OEMUtil.isRemove("am.managerrole.remove")));
/* 2545 */     return new ActionForward("/jsp/ADUsersList.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward createADUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2553 */     UserConfiguration uc = (UserConfiguration)form;
/* 2554 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*      */     
/*      */ 
/* 2557 */     ActionMessages messages = new ActionMessages();
/* 2558 */     ActionErrors errors = new ActionErrors();
/*      */     
/* 2560 */     String domainID = uc.getDomainID();
/* 2561 */     String domainUserRole = uc.getDomainUserRole();
/* 2562 */     String domainName = uc.getDomainName();
/* 2563 */     String canonicalName = ADAuthenticationUtil.toCanonicalDomainName(domainName);
/* 2564 */     if ((EnterpriseUtil.isAdminServer()) && ("admin".equalsIgnoreCase(domainUserRole)))
/*      */     {
/* 2566 */       domainUserRole = "ENTERPRISEADMIN";
/*      */     }
/* 2568 */     request.setAttribute("domainID", domainID);
/* 2569 */     HashMap<String, String> userDetails = new HashMap();
/* 2570 */     userDetails.put("role", domainUserRole);
/* 2571 */     userDetails.put("domainid", domainID);
/* 2572 */     String delegatedAdmin = request.getParameter("isDelegatedAdmin");
/* 2573 */     String restrictedAdmin = "1";
/*      */     
/* 2575 */     if ("on".equals(delegatedAdmin)) {
/* 2576 */       restrictedAdmin = "0";
/*      */     }
/* 2578 */     userDetails.put("restrictedAdmin", restrictedAdmin);
/*      */     
/* 2580 */     String[] aduser = request.getParameterValues("adUserSelect");
/* 2581 */     String[] selectedMonitor = request.getParameterValues("selectedGroup");
/* 2582 */     ArrayList<String> monitorGroupIds = new ArrayList();
/* 2583 */     if (selectedMonitor != null) {
/* 2584 */       monitorGroupIds = new ArrayList(Arrays.asList(selectedMonitor));
/*      */     }
/*      */     try
/*      */     {
/* 2588 */       for (int n = 0; n < aduser.length; n++)
/*      */       {
/*      */ 
/* 2591 */         if (DBUtil.username_userid_mapping.get(aduser[n]) == null)
/*      */         {
/* 2593 */           String userName = aduser[n];
/* 2594 */           String distinguishedName = request.getParameter(userName);
/* 2595 */           userDetails.put("username", userName);
/* 2596 */           userDetails.put("dn", distinguishedName);
/* 2597 */           int userid = UserConfigurationUtil.createDomainUser(userDetails);
/*      */           try {
/* 2599 */             if (monitorGroupIds.size() > 0) {
/* 2600 */               UserConfigurationUtil.updateMonitorGroupDetails(userName, String.valueOf(userid), domainUserRole, monitorGroupIds);
/*      */             }
/*      */           } catch (Exception ex) {
/* 2603 */             ex.printStackTrace();
/*      */           }
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
/* 2616 */           if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */           {
/* 2618 */             AMLog.debug("[UserConfigurationAction::(createADUser)]ruser(s) : " + userid);
/* 2619 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(String.valueOf(userid));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception eee) {
/* 2625 */       eee.printStackTrace(); }
/* 2626 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.admintab.adduser.importad.alert.useradd.success"));
/* 2627 */     saveMessages(request, messages);
/*      */     
/* 2629 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward checkUserGroupName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2636 */     ResultSet rs = null;
/*      */     try {
/* 2638 */       response.setContentType("text/html; charset=UTF-8");
/* 2639 */       PrintWriter out = response.getWriter();
/* 2640 */       String usergroupname = request.getParameter("groupname");
/* 2641 */       boolean duplicateGroupName = UserConfigurationUtil.checkDuplicateEntry("AM_USERGROUP_CONFIG", "GROUPNAME", usergroupname, "");
/* 2642 */       String nameExists = duplicateGroupName ? "true" : "false";
/* 2643 */       out.println(nameExists.trim());
/* 2644 */       out.flush();
/*      */     } catch (Exception ex) {
/* 2646 */       ex.printStackTrace();
/*      */     } finally {
/* 2648 */       if (rs != null) {
/* 2649 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 2652 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward checkDomainName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2656 */     ResultSet rs = null;
/*      */     try {
/* 2658 */       response.setContentType("text/html; charset=UTF-8");
/* 2659 */       PrintWriter out = response.getWriter();
/* 2660 */       String domanName = request.getParameter("domainname");
/* 2661 */       boolean duplicateGroupName = UserConfigurationUtil.checkDuplicateEntry("AM_DOMAINCONTROLLERS", "DOMAINNAME", domanName, "");
/* 2662 */       String nameExists = duplicateGroupName ? "true" : "false";
/* 2663 */       out.println(nameExists.trim());
/* 2664 */       out.flush();
/*      */     } catch (Exception ex) {
/* 2666 */       ex.printStackTrace();
/*      */     } finally {
/* 2668 */       if (rs != null) {
/* 2669 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 2672 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward ssoJarCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2676 */     PrintWriter out = response.getWriter();
/* 2677 */     response.setContentType("text/html; charset=UTF-8");
/*      */     try {
/* 2679 */       if (new File(System.getProperty("webnms.rootdir", ".") + File.separator + "apache" + File.separator + "tomcat" + File.separator + "webapps" + File.separator + "cas.war").exists()) {
/* 2680 */         out.write("true");
/*      */       } else {
/* 2682 */         out.write("false");
/*      */       }
/*      */     } catch (Exception e) {
/* 2685 */       out.write("false");
/*      */     }
/* 2687 */     out.flush();
/* 2688 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\UserConfigurationAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */