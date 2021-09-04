/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.util.StartUtil;
/*      */ import com.adventnet.appmanager.util.UserSessionHandler;
/*      */ import com.adventnet.security.authorization.Coding;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.File;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.naming.directory.DirContext;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class UserConfigurationUtil
/*      */ {
/*   48 */   public static final List<String> USER_GROUPS = Arrays.asList(new String[] { "ADMIN", "OPERATOR", "USERS", "MANAGER" });
/*      */   
/*      */   public static String CheckNewUserAvailability() {
/*   51 */     String result = "true";
/*      */     try {
/*   53 */       FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*   54 */       String usertype = fd.getUserType();
/*   55 */       int numberOfUsers = fd.getNumberOfUsersPermitted();
/*      */       
/*   57 */       if ((usertype.equals("R")) && (numberOfUsers > 0))
/*      */         try {
/*   59 */           ArrayList<ArrayList> rows = new ArrayList();
/*   60 */           rows = DBUtil.getRows("select USERID from AM_UserPasswordTable where USERNAME NOT IN  ('reportadmin','systemadmin_enterprise')");
/*      */           
/*   62 */           if (rows.size() >= numberOfUsers) {
/*   63 */             if (rows.size() > numberOfUsers) {
/*   64 */               AMLog.debug(" License Check Failed : The number of users allowed for this Registered License is " + numberOfUsers + ". But currently there are " + rows.size() + " users, so kindly delete the other users.");
/*   65 */               result = FormatUtil.getString("am.webclient.api.userparametermissing.msg", new String[] { String.valueOf(numberOfUsers), String.valueOf(rows.size()) });
/*      */             } else {
/*   67 */               AMLog.debug(" License Check Failed : The number of users allowed for this Registered License is only " + numberOfUsers + ", so you could not create any more users."); }
/*   68 */             return FormatUtil.getString("user.creation.exceeds", new String[] { String.valueOf(numberOfUsers) });
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*   74 */           ex.printStackTrace();
/*      */         }
/*   76 */       if (usertype.equals("F")) {
/*      */         try
/*      */         {
/*   79 */           ArrayList<ArrayList> rows = new ArrayList();
/*   80 */           rows = DBUtil.getRows("select USERID from AM_UserPasswordTable where USERNAME NOT IN  ('reportadmin','systemadmin_enterprise')");
/*   81 */           if (rows.size() >= numberOfUsers) {
/*   82 */             if (rows.size() > numberOfUsers)
/*   83 */               AMLog.debug(" License Check Failed : The number of users allowed for this Free License is " + numberOfUsers + ". But currently there are " + rows.size() + " users, so kindly delete the other users.");
/*   84 */             return FormatUtil.getString("user.present.creation.exceedslimit", new String[] { String.valueOf(numberOfUsers), String.valueOf(rows.size()) });
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*   89 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/*   93 */       e.printStackTrace();
/*      */     }
/*   95 */     return result;
/*      */   }
/*      */   
/*      */   public static Boolean isUserExist(String username, String userId)
/*      */   {
/*  100 */     Boolean result = Boolean.valueOf(false);
/*  101 */     ArrayList<ArrayList> rows = DBUtil.getRows("select * from AM_UserPasswordTable where USERNAME='" + username + "'");
/*  102 */     if (rows.size() > 0) {
/*  103 */       result = Boolean.valueOf(true);
/*  104 */       if ((userId != null) && (userId.equals(((ArrayList)rows.get(0)).get(0)))) {
/*  105 */         result = Boolean.valueOf(false);
/*      */       }
/*      */     }
/*  108 */     return result;
/*      */   }
/*      */   
/*      */   public static int insertUserDetails(Properties userProps) {
/*      */     try {
/*  113 */       String md5Password = userProps.getProperty("md5Password");
/*  114 */       boolean isAPICallFromAAM = (userProps.getProperty("isAPICallFromAAM") != null) && (userProps.getProperty("isAPICallFromAAM").equals("true"));
/*  115 */       StartUtil.printStr("UserConfigurationUtil.insertUserDetails():isAPICallFromAAM::::" + isAPICallFromAAM);
/*  116 */       AMLog.debug("API_---email :" + userProps.getProperty("email"));
/*      */       
/*      */ 
/*  119 */       int aamUserId = -1;
/*  120 */       String apikey = "";
/*  121 */       if (isAPICallFromAAM) {
/*      */         try {
/*  123 */           aamUserId = Integer.parseInt(userProps.getProperty("aamUserId"));
/*  124 */           StartUtil.printStr("UserConfigurationUtil.insertUserDetails():aamUserId::::" + aamUserId);
/*      */         } catch (Exception ex) {
/*  126 */           StartUtil.printStr("UserConfigurationUtil.insertUserDetails(): Invalid user id " + userProps.getProperty("aamUserId") + " from Admin server");
/*      */         }
/*  128 */         apikey = userProps.getProperty("apikeyofuser");
/*      */       }
/*  130 */       boolean isSynchingAPIToMAS = (EnterpriseUtil.isManagedServer()) && (AMAutomaticPortChanger.isSsoEnabled()) && (!apikey.equalsIgnoreCase("")) && (isAPICallFromAAM);
/*      */       
/*      */       int tempUserID;
/*      */       int tempUserID;
/*  134 */       if ((EnterpriseUtil.isManagedServer()) && (aamUserId != -1)) {
/*  135 */         tempUserID = aamUserId; } else { int tempUserID;
/*  136 */         if (EnterpriseUtil.isManagedServer())
/*      */         {
/*      */ 
/*  139 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable", "USERID < 10000000");
/*      */         } else {
/*  141 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable");
/*      */         }
/*      */       }
/*  144 */       String insertquery = "insert into AM_UserPasswordTable  (USERID,USERNAME,PASSWORD,EMAILID,DESCRIPTION,RESTRICTEDADMIN) values(" + tempUserID + ",'" + userProps.getProperty("userName") + "', ";
/*  145 */       if (isSynchingAPIToMAS) {
/*  146 */         insertquery = "insert into AM_UserPasswordTable  (USERID,USERNAME,PASSWORD,EMAILID,DESCRIPTION,RESTRICTEDADMIN,APIKEY) values(" + tempUserID + ",'" + userProps.getProperty("userName") + "', ";
/*      */       }
/*  148 */       if ((EnterpriseUtil.isManagedServer()) && (md5Password != null) && (md5Password.equals("true")))
/*      */       {
/*  150 */         boolean isMsSql = DBQueryUtil.isMssql();
/*  151 */         if (isMsSql)
/*      */         {
/*  153 */           insertquery = insertquery + "CONVERT(varbinary(200),'" + userProps.getProperty("password") + "',2)";
/*      */         } else {
/*  155 */           insertquery = insertquery + "'" + userProps.getProperty("password") + "'";
/*      */         }
/*      */       } else {
/*  158 */         insertquery = insertquery + DBQueryUtil.MD5(userProps.getProperty("password"));
/*      */       }
/*  160 */       insertquery = insertquery + ",'" + DBQueryUtil.addEscapeSequence(userProps.getProperty("email")) + "','" + DBQueryUtil.addEscapeSequence(userProps.getProperty("description")) + "'," + userProps.getProperty("restrictedAdmin");
/*      */       
/*  162 */       if (isSynchingAPIToMAS) {
/*  163 */         insertquery = insertquery + ",'" + userProps.getProperty("apikeyofuser") + "')";
/*      */       } else {
/*  165 */         insertquery = insertquery + " )";
/*      */       }
/*      */       
/*  168 */       AMConnectionPool.executeUpdateStmt(insertquery);
/*  169 */       DBUtil.username_userid_mapping.put(userProps.getProperty("userName"), Integer.toString(tempUserID));
/*  170 */       if (!isSynchingAPIToMAS) {
/*  171 */         DataCollectionDBUtil.insertAPIKey(userProps.getProperty("userName"));
/*      */       }
/*  173 */       long time = System.currentTimeMillis();
/*      */       
/*  175 */       String insertHistoryQuery = "insert into AM_UserPasswordHistory values(" + tempUserID + "," + DBQueryUtil.MD5(userProps.getProperty("password")) + "," + time + ")";
/*      */       
/*  177 */       AMConnectionPool.executeUpdateStmt(insertHistoryQuery);
/*      */       
/*      */ 
/*  180 */       if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  181 */         userProps.setProperty("aamUserId", String.valueOf(tempUserID));
/*  182 */         String apikeyforMAS = EnterpriseUtil.getAPIKey(userProps.getProperty("userName"));
/*  183 */         userProps.setProperty("apikeyofuser", apikeyforMAS);
/*  184 */         MASSyncUtil.pushUserDetailsToMAS(userProps, "add");
/*      */       }
/*  186 */       return tempUserID;
/*      */     } catch (Exception ex) {
/*  188 */       ex.printStackTrace();
/*      */     }
/*  190 */     return -1;
/*      */   }
/*      */   
/*      */   public static int createDomainUser(HashMap<String, String> userDetails) {
/*  194 */     int tempUserID = 0;
/*      */     try {
/*  196 */       String userName = (String)userDetails.get("username");
/*  197 */       String userRole = (String)userDetails.get("role");
/*  198 */       String dn = (String)userDetails.get("dn");
/*  199 */       String domainid = (String)userDetails.get("domainid");
/*  200 */       String delegatedAdmin = (String)userDetails.get("restrictedAdmin");
/*  201 */       int currentUser = DBUtil.getNumberOfUsers();
/*  202 */       FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  203 */       int allowedUsers = fd.getNumberOfUsersPermitted();
/*  204 */       boolean isAPICallFromAAM = (userDetails.get("isAPICallFromAAM") != null) && (((String)userDetails.get("isAPICallFromAAM")).equals("true"));
/*  205 */       StartUtil.printStr("UserConfigurationUtil.createDomainUser():isAPICallFromAAM::::" + isAPICallFromAAM);
/*  206 */       if ((allowedUsers == -1) || (currentUser < allowedUsers))
/*      */       {
/*  208 */         int aamUserId = -1;
/*  209 */         if (isAPICallFromAAM) {
/*      */           try {
/*  211 */             aamUserId = Integer.parseInt((String)userDetails.get("aamUserId"));
/*  212 */             StartUtil.printStr("UserConfigurationUtil.createDomainUser():aamUserId::::" + aamUserId);
/*      */           } catch (Exception ex) {
/*  214 */             StartUtil.printStr("UserConfigurationUtil.createDomainUser(): Invalid user id " + (String)userDetails.get("aamUserId") + " from Admin server");
/*      */           }
/*      */         }
/*      */         
/*  218 */         if ((EnterpriseUtil.isManagedServer()) && (aamUserId != -1)) {
/*  219 */           tempUserID = aamUserId;
/*  220 */         } else if (EnterpriseUtil.isManagedServer())
/*      */         {
/*      */ 
/*  223 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable", "USERID < 10000000");
/*      */         } else {
/*  225 */           tempUserID = DBQueryUtil.getIncrementedID("USERID", "AM_UserPasswordTable");
/*      */         }
/*  227 */         String insertquery = "insert into AM_UserPasswordTable  (USERID,USERNAME,RESTRICTEDADMIN) values(" + tempUserID + ",'" + userName + "'," + delegatedAdmin + ")";
/*  228 */         AMConnectionPool.executeUpdateStmt(insertquery);
/*  229 */         DBUtil.username_userid_mapping.put(userName, Integer.toString(tempUserID));
/*  230 */         DataCollectionDBUtil.insertAPIKey(userName);
/*  231 */         AMConnectionPool.executeUpdateStmt("insert into AM_UserGroupTable values ('" + userName + "','" + userRole + "')");
/*  232 */         if (userRole.equalsIgnoreCase("ADMIN")) {
/*  233 */           String query = "insert into AM_UserGroupTable   values ('" + userName + "','USERS')";
/*  234 */           AMConnectionPool.executeUpdateStmt(query);
/*      */         }
/*      */         
/*  237 */         String[] domainidList = domainid.split(",");
/*  238 */         for (String id : domainidList) {
/*      */           try {
/*  240 */             AMConnectionPool.executeUpdateStmt("insert into AM_DOMAINUSER_MAPPER values (" + tempUserID + "," + id + ",'" + dn + "')");
/*      */           } catch (Exception ex) {
/*  242 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  247 */         if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  248 */           userDetails.put("aamUserId", String.valueOf(tempUserID));
/*  249 */           MASSyncUtil.pushDomainUserDetailsToMAS(userDetails, "add");
/*      */         }
/*      */       }
/*      */       else {
/*  253 */         AMLog.debug(" License is permitted for only " + allowedUsers + " number of Users");
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  257 */       ex.printStackTrace();
/*      */     }
/*  259 */     return tempUserID;
/*      */   }
/*      */   
/*      */   public static void updateUserPrivileges(HttpServletRequest request, String userName) {
/*  263 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  266 */       Hashtable<String, Boolean> users = null;
/*      */       try
/*      */       {
/*  269 */         if (request != null)
/*      */         {
/*  271 */           users = (Hashtable)request.getSession().getServletContext().getAttribute("userPrivileges");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  276 */         e.printStackTrace();
/*      */       }
/*  278 */       String query = "select AM_UserPasswordTable.USERNAME,AM_HOLISTICAPPLICATION_OWNERS.OWNERID,RESTRICTEDADMIN as RESTRICTEDADMIN from AM_UserGroupTable,AM_UserPasswordTable left outer join AM_HOLISTICAPPLICATION_OWNERS on AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and (AM_UserGroupTable.GROUPNAME = 'ADMIN' or AM_UserGroupTable.GROUPNAME = 'ENTERPRISEADMIN') and AM_UserPasswordTable.USERNAME != 'admin' and AM_UserPasswordTable.USERNAME != 'systemadmin_enterprise' group by AM_UserPasswordTable.USERNAME,AM_HOLISTICAPPLICATION_OWNERS.OWNERID,RESTRICTEDADMIN";
/*  279 */       if ((userName != null) && (userName.trim().length() > 0)) {
/*  280 */         query = "select AM_UserPasswordTable.USERNAME,AM_HOLISTICAPPLICATION_OWNERS.OWNERID,RESTRICTEDADMIN as RESTRICTEDADMIN from AM_UserGroupTable,AM_UserPasswordTable left outer join AM_HOLISTICAPPLICATION_OWNERS on AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and (AM_UserGroupTable.GROUPNAME = 'ADMIN' or AM_UserGroupTable.GROUPNAME = 'ENTERPRISEADMIN') and AM_UserPasswordTable.USERNAME = '" + userName + "' group by AM_UserPasswordTable.USERNAME,AM_HOLISTICAPPLICATION_OWNERS.OWNERID,RESTRICTEDADMIN";
/*      */       }
/*  282 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  283 */       while (rs.next()) {
/*  284 */         String user = rs.getString("USERNAME");
/*  285 */         String ownerID = rs.getString("OWNERID");
/*  286 */         String restrictedAdmin = rs.getString("RESTRICTEDADMIN");
/*  287 */         boolean isPrivilege = false;
/*  288 */         if ("0".equals(restrictedAdmin)) {
/*  289 */           isPrivilege = true;
/*      */         }
/*  291 */         if (users != null)
/*      */         {
/*  293 */           users.put(user + "_privilege", Boolean.valueOf(isPrivilege));
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  297 */       ex.printStackTrace();
/*      */     } finally {
/*  299 */       if (rs != null) {
/*  300 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void updateGroups(String username, String[] groups) throws Exception
/*      */   {
/*  307 */     AMConnectionPool.executeUpdateStmt("delete from AM_UserGroupTable where  USERNAME='" + username + "'");
/*  308 */     for (String groupName : groups) {
/*  309 */       if (groupName.equalsIgnoreCase("ADMIN")) {
/*  310 */         String insertquery = "insert into AM_UserGroupTable   values ('" + username + "','USERS')";
/*  311 */         AMConnectionPool.executeUpdateStmt(insertquery);
/*      */       }
/*  313 */       groupName = (EnterpriseUtil.isAdminServer()) && (groupName.equalsIgnoreCase("ADMIN")) ? "ENTERPRISEADMIN" : groupName;
/*  314 */       AMConnectionPool.executeUpdateStmt("insert into AM_UserGroupTable values ('" + username + "','" + groupName + "')");
/*      */     }
/*      */   }
/*      */   
/*      */   public static ArrayList<String> updateOwnerUserGroups(String[] usergroups, int userid, boolean toDelete)
/*      */   {
/*  320 */     StringBuilder usergroup = new StringBuilder();
/*  321 */     ArrayList<String> haids = new ArrayList();
/*  322 */     if (toDelete) {
/*      */       try {
/*  324 */         AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_OWNERS where USERID=" + userid);
/*      */       } catch (Exception ex) {
/*  326 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  329 */     if (usergroups.length > 0) {
/*  330 */       for (String groupid : usergroups) {
/*      */         try {
/*  332 */           usergroup.append(groupid).append(",");
/*  333 */           AMConnectionPool.executeUpdateStmt("insert into AM_USERGROUP_OWNERS VALUES (" + groupid + "," + userid + ")");
/*      */         } catch (Exception ex) {
/*  335 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*  339 */     String associatedGroups = "";
/*  340 */     if (usergroup.toString().trim().length() > 0) {
/*  341 */       usergroup.append(-1).append(")");
/*  342 */       associatedGroups = "(" + usergroup.toString();
/*  343 */       ResultSet rs = null;
/*      */       try {
/*  345 */         rs = AMConnectionPool.executeQueryStmt("select HAID from AM_USERGROUP_MAPPING WHERE GROUPID IN " + associatedGroups);
/*      */         
/*  347 */         while (rs.next()) {
/*  348 */           haids.add(rs.getString("HAID"));
/*      */         }
/*      */       } catch (Exception ex) {
/*  351 */         ex.printStackTrace();
/*      */       } finally {
/*  353 */         if (rs != null) {
/*  354 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  359 */     return haids;
/*      */   }
/*      */   
/*      */   public static void updateMonitorGroupDetails(String userName, String userId, String groupName, List<String> selectedMonitor)
/*      */   {
/*  364 */     Boolean delete = Boolean.valueOf(false);
/*  365 */     int uid = -1;
/*  366 */     ResultSet rs = null;
/*  367 */     ArrayList<String> alreadyPresent = new ArrayList();
/*  368 */     ArrayList<String> associatedGroups = new ArrayList();
/*      */     try
/*      */     {
/*  371 */       if ((selectedMonitor != null) && ((groupName.equals("OPERATOR")) || (groupName.equals("MANAGER")) || (groupName.equals("ADMIN")) || (groupName.equals("ENTERPRISEADMIN")))) {
/*  372 */         if (userId == null) {
/*  373 */           String userIdQuery = "select USERID from AM_UserPasswordTable where USERNAME='" + userName + "'";
/*      */           
/*  375 */           rs = AMConnectionPool.executeQueryStmt(userIdQuery);
/*  376 */           if (rs.next()) {
/*  377 */             uid = rs.getInt(1);
/*      */           }
/*      */         } else {
/*  380 */           uid = Integer.parseInt(userId);
/*      */         }
/*      */         
/*  383 */         if (userId != null) {
/*  384 */           String query = "select HAID from AM_HOLISTICAPPLICATION_OWNERS where OWNERID=" + userId;
/*  385 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  386 */           while (rs.next())
/*      */           {
/*  388 */             alreadyPresent.add(rs.getString(1));
/*      */           }
/*  390 */           rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_MYFIELDS_ENTITYDATA where DATATABLE='AM_UserPasswordTable' and VALUEID=" + userId);
/*  391 */           while (rs.next()) {
/*  392 */             associatedGroups.add(rs.getString("RESOURCEID"));
/*      */           }
/*  394 */           if ((groupName.equals("ADMIN")) || (groupName.equals("ENTERPRISEADMIN"))) {
/*      */             try {
/*  396 */               AMConnectionPool.executeUpdateStmt("update AM_UserPasswordTable set RESTRICTEDADMIN=0 where USERID=" + userId);
/*      */             } catch (Exception ex) {
/*  398 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*  402 */         int relationid = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA") - 1;
/*  403 */         for (String groupId : selectedMonitor)
/*      */         {
/*  405 */           if (!alreadyPresent.contains(groupId))
/*      */           {
/*  407 */             String query1 = "insert into AM_HOLISTICAPPLICATION_OWNERS values(" + groupId + "," + uid + ")";
/*      */             try {
/*  409 */               AMConnectionPool.executeUpdateStmt(query1);
/*      */             } catch (Exception ex) {
/*  411 */               ex.printStackTrace();
/*      */             }
/*  413 */             if (!associatedGroups.contains(groupId)) {
/*  414 */               relationid += 1;
/*  415 */               String userquery = "insert into AM_MYFIELDS_ENTITYDATA values(" + relationid + "," + groupId + ",'AM_UserPasswordTable'," + uid + ")";
/*      */               try {
/*  417 */                 AMConnectionPool.executeUpdateStmt(userquery);
/*      */               } catch (Exception ex) {
/*  419 */                 ex.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  425 */         for (String alreadyPresentId : alreadyPresent) {
/*  426 */           delete = Boolean.valueOf(true);
/*  427 */           for (String selectedGroupId : selectedMonitor) {
/*  428 */             if (alreadyPresentId.equals(selectedGroupId)) {
/*  429 */               delete = Boolean.valueOf(false);
/*  430 */               break;
/*      */             }
/*      */           }
/*      */           
/*  434 */           if (delete.booleanValue()) {
/*  435 */             String deleteQuery = "delete from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + alreadyPresentId + " and OWNERID=" + uid;
/*  436 */             AMLog.debug("Delete Query:=====>" + deleteQuery);
/*      */             try {
/*  438 */               AMConnectionPool.executeUpdateStmt(deleteQuery);
/*      */             } catch (Exception ex) {
/*  440 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  447 */         String deleteQuery = "delete from AM_HOLISTICAPPLICATION_OWNERS where OWNERID=" + userId;
/*  448 */         AMLog.debug("query:" + deleteQuery);
/*  449 */         AMConnectionPool.executeUpdateStmt(deleteQuery);
/*      */       }
/*      */     } catch (Exception ex) {
/*  452 */       ex.printStackTrace();
/*      */     } finally {
/*  454 */       if (rs != null) {
/*  455 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void deleteUserGroupDetails(List<String> usergroupIdList) {
/*      */     try {
/*  462 */       StringBuilder usergroupIdsToDelete = new StringBuilder();
/*  463 */       for (String groupid : usergroupIdList) {
/*  464 */         usergroupIdsToDelete.append(groupid).append(",");
/*      */         
/*  466 */         ArrayList<String> associatedUserIds = new ArrayList();
/*  467 */         if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.getServerType().equals(EnterpriseUtil.NORMAL_SERVER)))
/*      */         {
/*  469 */           String userQuery = "select AM_UserPasswordTable.USERID from AM_USERGROUP_OWNERS,AM_UserPasswordTable where AM_USERGROUP_OWNERS.USERID=AM_UserPasswordTable.USERID and AM_USERGROUP_OWNERS.GROUPID=" + groupid;
/*  470 */           associatedUserIds = DBUtil.getRowsForSingleColumn(userQuery);
/*      */         }
/*      */         
/*  473 */         String usergroupConfig = "delete from AM_USERGROUP_CONFIG where GROUPID=" + groupid;
/*  474 */         String usergroupowners = "delete from AM_USERGROUP_OWNERS where GROUPID=" + groupid;
/*  475 */         String usergroupmapping = "delete from AM_USERGROUP_MAPPING where GROUPID=" + groupid;
/*  476 */         String usergroupdomain = "delete from AM_DOMAINUSERGROUP_MAPPING where GROUPID=" + groupid;
/*  477 */         String haidMapping = "delete from AM_HOLISTICAPPLICATION_OWNERS using AM_USERGROUP_OWNERS , AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and AM_USERGROUP_MAPPING.GROUPID=AM_USERGROUP_OWNERS.GROUPID AND   AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_USERGROUP_MAPPING.HAID and AM_USERGROUP_OWNERS.GROUPID=" + groupid;
/*  478 */         if (!DBQueryUtil.isPgsql()) {
/*  479 */           haidMapping = "delete AM_HOLISTICAPPLICATION_OWNERS from AM_HOLISTICAPPLICATION_OWNERS, AM_USERGROUP_OWNERS,AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and AM_USERGROUP_MAPPING.GROUPID=AM_USERGROUP_OWNERS.GROUPID and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_USERGROUP_MAPPING.HAID and  AM_USERGROUP_OWNERS.GROUPID=" + groupid;
/*      */         }
/*  481 */         AMConnectionPool.executeUpdateStmt(haidMapping);
/*  482 */         AMConnectionPool.executeUpdateStmt(usergroupConfig);
/*  483 */         AMConnectionPool.executeUpdateStmt(usergroupowners);
/*  484 */         AMConnectionPool.executeUpdateStmt(usergroupmapping);
/*  485 */         AMConnectionPool.executeUpdateStmt(usergroupdomain);
/*      */         
/*      */ 
/*  488 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */         {
/*      */           try
/*      */           {
/*      */ 
/*  493 */             if (!associatedUserIds.isEmpty())
/*      */             {
/*  495 */               AMLog.debug("[UserConfigurationUtil::(deleteUserGroupDetails)]ruser(s) : " + associatedUserIds);
/*  496 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(associatedUserIds);
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  501 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  504 */         DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(-1, -1, Integer.parseInt(groupid), -1);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  509 */       if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled()) && (usergroupIdsToDelete.length() > 0)) {
/*  510 */         String s1 = usergroupIdsToDelete.substring(0, usergroupIdsToDelete.length() - 1);
/*  511 */         HashMap<String, String> paramsToMas = new HashMap();
/*  512 */         paramsToMas.put("usergroupId", s1);
/*  513 */         paramsToMas.put("apicallfrom", "admin");
/*  514 */         MASSyncUtil.addTasktoSync(paramsToMas, "/AppManager/xml/Usergroup/delete", "all", "POST", 11, 3);
/*      */       }
/*      */     } catch (Exception ex) {
/*  517 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void deleteUserDetails(List<String> userIdList, String currentUserName) throws Exception {
/*  522 */     HashMap<String, String> hmap = DBUtil.username_userid_mapping;
/*  523 */     HashMap<String, String> restKeyMap = new HashMap();
/*  524 */     StringBuilder userIdsToDelete = new StringBuilder();
/*  525 */     ResultSet rs = null;
/*  526 */     for (String userId : userIdList)
/*      */     {
/*  528 */       boolean isRestrictedRole = RestrictedUsersViewUtil.isRestrictedRole(userId);
/*      */       
/*  530 */       userIdsToDelete.append(userId).append(",");
/*  531 */       String selectUserGroup = "select AM_UserPasswordTable.USERNAME,AM_UserPasswordTable.APIKEY from AM_UserGroupTable,AM_UserPasswordTable  where AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME and  AM_UserGroupTable.USERNAME<>'admin' and AM_UserGroupTable.USERNAME<>'" + currentUserName + "' and AM_UserPasswordTable.USERID=" + userId;
/*      */       try {
/*  533 */         rs = AMConnectionPool.executeQueryStmt(selectUserGroup);
/*  534 */         if (rs.next())
/*      */         {
/*  536 */           restKeyMap.put(rs.getString("USERNAME"), rs.getString("APIKEY"));
/*      */         }
/*      */       } catch (Exception e) {
/*  539 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  542 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/*  545 */       String deleteUserGroup = "delete AM_UserGroupTable from AM_UserGroupTable JOIN AM_UserPasswordTable ON AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME where  AM_UserGroupTable.USERNAME NOT IN ('admin','" + currentUserName + "') and AM_UserPasswordTable.USERID=" + userId;
/*  546 */       if (DBQueryUtil.isPgsql()) {
/*  547 */         deleteUserGroup = "delete from AM_UserGroupTable using AM_UserPasswordTable where AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME and  AM_UserGroupTable.USERNAME NOT IN ('admin','" + currentUserName + "') and AM_UserPasswordTable.USERID=" + userId;
/*      */       }
/*  549 */       AMConnectionPool.executeUpdateStmt(deleteUserGroup);
/*  550 */       String deleteUserQuery = "delete from AM_UserPasswordTable where USERNAME NOT IN ('admin','" + currentUserName + "') and USERID=" + userId;
/*  551 */       if ((EnterpriseUtil.isManagedServer) && (Constants.isSsoEnabled())) {
/*  552 */         deleteUserQuery = "delete from AM_UserPasswordTable where USERID=" + userId;
/*      */       }
/*  554 */       AMConnectionPool.executeUpdateStmt(deleteUserQuery);
/*  555 */       String deleteQuery = "delete from AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + userId;
/*  556 */       AMConnectionPool.executeUpdateStmt(deleteQuery);
/*  557 */       AMLog.debug("delete query:" + deleteQuery);
/*      */       
/*      */ 
/*      */ 
/*  561 */       String deletePwdHistoryQuery = "delete from AM_UserPasswordHistory where USERID=" + userId;
/*  562 */       AMConnectionPool.executeUpdateStmt(deletePwdHistoryQuery);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  568 */       String deleteDomainMapper = "delete from AM_DOMAINUSER_MAPPER where USERID=" + userId;
/*  569 */       AMConnectionPool.executeUpdateStmt(deleteDomainMapper);
/*      */       
/*  571 */       String deleteUserGroupowners = "delete from AM_USERGROUP_OWNERS where USERID=" + userId;
/*  572 */       AMConnectionPool.executeUpdateStmt(deleteUserGroupowners);
/*      */       
/*      */ 
/*  575 */       Set<String> set = hmap.keySet();
/*  576 */       Iterator<String> iter = set.iterator();
/*  577 */       while (iter.hasNext()) {
/*  578 */         String username = (String)iter.next();
/*  579 */         String userid = (String)hmap.get(username);
/*  580 */         int userid1 = Integer.parseInt(userid);
/*  581 */         int temp_userid = Integer.parseInt(userId);
/*  582 */         if (userid1 == temp_userid) {
/*  583 */           String home = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();
/*  584 */           String delDir = home + File.separator + "working" + File.separator + "users" + File.separator + username + File.separator;
/*  585 */           if (new File(delDir).exists()) {
/*  586 */             deleteDirectory(new File(delDir));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  594 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*      */         try
/*      */         {
/*  598 */           if (isRestrictedRole)
/*      */           {
/*  600 */             RestrictedUsersViewUtil.deleteFromAMUserResourcesTable(userId);
/*  601 */             RestrictedUsersViewUtil.userVsIsRoleRestrictedMap.remove(userId);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  606 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  610 */       DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(-1, Integer.parseInt(userId), -1, -1);
/*      */     }
/*      */     
/*      */ 
/*  614 */     Set<String> restKeyset = restKeyMap.keySet();
/*  615 */     Iterator<String> restKeyIter = restKeyset.iterator();
/*  616 */     while (restKeyIter.hasNext()) {
/*  617 */       String username = (String)restKeyIter.next();
/*  618 */       String apiKey = (String)restKeyMap.get(username);
/*  619 */       DBUtil.username_userid_mapping.remove(username);
/*  620 */       CommonAPIUtil.removeUserDetails(apiKey);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  625 */     if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled()) && (userIdsToDelete.length() > 0)) {
/*  626 */       String s1 = userIdsToDelete.substring(0, userIdsToDelete.length() - 1);
/*  627 */       HashMap<String, String> paramsToMas = new HashMap();
/*  628 */       paramsToMas.put("userId", s1);
/*  629 */       paramsToMas.put("apicallfrom", "admin");
/*  630 */       MASSyncUtil.addTasktoSync(paramsToMas, "/AppManager/xml/Users/delete", "all", "POST", 11, 3);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String updateUserDetails(Properties userProps)
/*      */   {
/*  637 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*  638 */     String userId = (String)userProps.get("userId");
/*  639 */     String updateChk = (String)userProps.get("updateChk");
/*  640 */     String userName = (String)userProps.get("userName");
/*  641 */     String loginUser = (String)userProps.get("loginUser");
/*  642 */     String oldUserName = (String)userProps.get("oldUserName");
/*  643 */     String password = (String)userProps.get("password");
/*  644 */     String oldPassword = (String)userProps.get("oldPassword");
/*  645 */     String description = (String)userProps.get("description");
/*  646 */     String email = (String)userProps.get("email");
/*  647 */     String errType = "noerror";
/*  648 */     String restrictedAdmin = (String)userProps.get("restrictedAdmin");
/*  649 */     AMLog.debug("api_user configuration details, User ID :" + userId + ", userName : " + userName + ", loginUser : " + loginUser);
/*  650 */     ResultSet rs = null;
/*  651 */     boolean pwdMatchCheck = true;
/*  652 */     boolean policyEnabledStatus = false;
/*      */     try {
/*  654 */       if ((!EnterpriseUtil.isAdminServer()) && (userId.equals("1")) && (!updateChk.equals("false"))) {
/*  655 */         String updateEnterpriseRolequery = "update AM_UserPasswordTable  set password= " + DBQueryUtil.MD5(password) + " where AM_UserPasswordTable.USERNAME='" + "systemadmin_enterprise" + "'";
/*  656 */         AMConnectionPool.executeUpdateStmt(updateEnterpriseRolequery);
/*      */       }
/*      */       
/*  659 */       String updatequery = "";
/*  660 */       if (!updateChk.equals("false"))
/*      */       {
/*  662 */         updatequery = "update AM_UserPasswordTable  set PASSWORD= " + DBQueryUtil.MD5(password) + ",EMAILID='" + DBQueryUtil.addEscapeSequence(email) + "',DESCRIPTION='" + DBQueryUtil.addEscapeSequence(description) + "' ,RESTRICTEDADMIN = " + restrictedAdmin + " where USERID='" + userId + "'";
/*      */       } else {
/*  664 */         updatequery = "update AM_UserPasswordTable  set EMAILID='" + DBQueryUtil.addEscapeSequence(email) + "',DESCRIPTION='" + DBQueryUtil.addEscapeSequence(description) + "',RESTRICTEDADMIN = " + restrictedAdmin + " where USERID='" + userId + "'";
/*      */       }
/*      */       
/*  667 */       if (userName == null) {
/*  668 */         Constants.ADMIN_EMAIL_ADDRESS = email;
/*      */       }
/*      */       
/*  671 */       if (userName != null)
/*      */       {
/*  673 */         if (!updateChk.equals("false")) {
/*  674 */           updatequery = "update AM_UserPasswordTable  set PASSWORD= " + DBQueryUtil.MD5(password) + ",EMAILID='" + DBQueryUtil.addEscapeSequence(email) + "',DESCRIPTION='" + DBQueryUtil.addEscapeSequence(description) + "',USERNAME='" + userName + "', RESTRICTEDADMIN = " + restrictedAdmin + " where USERID='" + userId + "'";
/*      */         } else {
/*  676 */           updatequery = "update AM_UserPasswordTable  set EMAILID='" + DBQueryUtil.addEscapeSequence(email) + "',DESCRIPTION='" + DBQueryUtil.addEscapeSequence(description) + "',USERNAME='" + userName + "', RESTRICTEDADMIN = " + restrictedAdmin + " where USERID='" + userId + "'";
/*      */         }
/*      */         
/*  679 */         if (userName.equals("admin")) {
/*  680 */           com.adventnet.appmanager.server.framework.RepairFatalErrors.adminEmailid = (String)userProps.get("email");
/*  681 */           Constants.ADMIN_EMAIL_ADDRESS = (String)userProps.get("email");
/*      */         }
/*      */       }
/*  684 */       if ((oldUserName != null) && (loginUser != null) && (oldUserName.equals(loginUser)) && (oldPassword != null) && (!updateChk.equals("false")) && (!PluginUtil.isPlugin())) {
/*  685 */         String checkquery = "select * from AM_UserPasswordTable , AM_UserGroupTable where AM_UserPasswordTable.USERNAME='" + oldUserName + "' and AM_UserPasswordTable.PASSWORD=" + DBQueryUtil.MD5(oldPassword) + " and AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME ";
/*  686 */         rs = AMConnectionPool.executeQueryStmt(checkquery);
/*      */         
/*      */         String str1;
/*  689 */         if (rs.next())
/*      */         {
/*  691 */           if (policyEnabledStatus) {
/*  692 */             pwdMatchCheck = ush.passwordToCheck(oldPassword, password);
/*      */           }
/*      */           
/*  695 */           if (pwdMatchCheck) {
/*  696 */             AMConnectionPool.executeUpdateStmt(updatequery);
/*  697 */             ush.updatePwdHistoryTable(Integer.parseInt(userId), userName, password);
/*      */           } else {
/*  699 */             errType = "pwdinconchar";
/*  700 */             return errType;
/*      */           }
/*      */         }
/*      */         else {
/*  704 */           errType = "oldpwdnotmatch";
/*  705 */           return errType;
/*      */         }
/*      */       } else {
/*  708 */         AMConnectionPool.executeUpdateStmt(updatequery);
/*  709 */         if ((password != null) && (!password.equals(""))) {
/*  710 */           ush.updatePwdHistoryTable(Integer.parseInt(userId), userName, password);
/*      */         }
/*      */       }
/*      */       
/*  714 */       CommonAPIUtil.getUserDetails(userName);
/*  715 */       if (userName != null)
/*      */       {
/*  717 */         if (DBQueryUtil.isPgsql()) {
/*  718 */           AMConnectionPool.executeUpdateStmt("delete from AM_UserGroupTable using AM_UserPasswordTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and AM_UserPasswordTable.USERID='" + userId + "'");
/*      */         } else {
/*  720 */           AMConnectionPool.executeUpdateStmt("delete AM_UserGroupTable from AM_UserGroupTable join AM_UserPasswordTable on AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME where AM_UserPasswordTable.USERID='" + userId + "'");
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  724 */       ex.printStackTrace();
/*  725 */       errType = "Exception has been occured";
/*      */     } finally {
/*  727 */       if (rs != null) {
/*  728 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  733 */     if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/*  734 */       MASSyncUtil.pushUserDetailsToMAS(userProps, "update");
/*      */     }
/*  736 */     return errType;
/*      */   }
/*      */   
/*      */   public static HashMap<String, ArrayList<HashMap<String, String>>> getAllUserGroup() {
/*  740 */     ResultSet rs = null;
/*  741 */     HashMap<String, ArrayList<HashMap<String, String>>> usergroups = new HashMap();
/*      */     try {
/*  743 */       ArrayList<HashMap<String, String>> localgroup = new ArrayList();
/*  744 */       ArrayList<HashMap<String, String>> domaingroup = new ArrayList();
/*  745 */       rs = AMConnectionPool.executeQueryStmt("select GROUPNAME,AM_USERGROUP_CONFIG.GROUPID, max(case when DOMAINID IS NULL then 'local' else 'domain' END) as GROUPADD from AM_USERGROUP_CONFIG left outer join AM_DOMAINUSERGROUP_MAPPING on AM_DOMAINUSERGROUP_MAPPING.GROUPID=AM_USERGROUP_CONFIG.GROUPID group by GROUPNAME,AM_USERGROUP_CONFIG.GROUPID");
/*  746 */       while (rs.next()) {
/*  747 */         HashMap<String, String> details = new HashMap();
/*  748 */         String auth = rs.getString("GROUPADD");
/*  749 */         details.put("groupname", rs.getString("GROUPNAME"));
/*  750 */         details.put("groupid", rs.getString("GROUPID"));
/*      */         
/*  752 */         if ("local".equalsIgnoreCase(auth)) {
/*  753 */           localgroup.add(details);
/*      */         } else {
/*  755 */           domaingroup.add(details);
/*      */         }
/*      */       }
/*  758 */       usergroups.put("local", localgroup);
/*  759 */       usergroups.put("domain", domaingroup);
/*      */     } catch (Exception ex) {
/*  761 */       ex.printStackTrace();
/*      */     } finally {
/*  763 */       if (rs != null) {
/*  764 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  767 */     return usergroups;
/*      */   }
/*      */   
/*      */   public static ArrayList<Hashtable> getUsergroupDetails(int groupid) {
/*  771 */     ArrayList<Hashtable> usergroupDetails = new ArrayList();
/*  772 */     String query = "select GROUPID,GROUPNAME from AM_USERGROUP_CONFIG where GROUPID=" + groupid;
/*  773 */     ArrayList<ArrayList> list = DBUtil.getRows(query);
/*  774 */     for (ArrayList<String> usergroup : list) {
/*  775 */       String usergroupid = (String)usergroup.get(0);
/*  776 */       String usergroupname = (String)usergroup.get(1);
/*  777 */       String mgQuery = "select AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.RESOURCENAME from AM_USERGROUP_MAPPING,AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_USERGROUP_MAPPING.GROUPID=" + usergroupid;
/*  778 */       ArrayList<ArrayList> mgList = DBUtil.getRows(mgQuery);
/*  779 */       ArrayList<Hashtable<String, String>> groupList = new ArrayList();
/*  780 */       for (ArrayList<String> monitorGroup : mgList) {
/*  781 */         Hashtable<String, String> mgDetails = new Hashtable();
/*  782 */         mgDetails.put("MonitorGroupID", monitorGroup.get(0));
/*  783 */         mgDetails.put("MonitorGroupName", monitorGroup.get(1));
/*  784 */         groupList.add(mgDetails);
/*      */       }
/*  786 */       String userQuery = "select AM_UserPasswordTable.USERID,AM_UserPasswordTable.USERNAME from AM_USERGROUP_OWNERS,AM_UserPasswordTable where AM_USERGROUP_OWNERS.USERID=AM_UserPasswordTable.USERID and AM_USERGROUP_OWNERS.GROUPID=" + usergroupid;
/*  787 */       ArrayList<ArrayList> userList = DBUtil.getRows(userQuery);
/*  788 */       ArrayList<Hashtable<String, String>> associatedUserList = new ArrayList();
/*  789 */       for (ArrayList<String> user : userList) {
/*  790 */         Hashtable<String, String> userDetails = new Hashtable();
/*  791 */         userDetails.put("USERID", user.get(0));
/*  792 */         userDetails.put("USERNAME", user.get(1));
/*  793 */         associatedUserList.add(userDetails);
/*      */       }
/*  795 */       Hashtable userGroup = new Hashtable();
/*  796 */       userGroup.put("GroupID", usergroupid);
/*  797 */       userGroup.put("GroupName", usergroupname);
/*  798 */       userGroup.put("AssociatedMonitorGroup", groupList);
/*  799 */       userGroup.put("AssociatedUsers", associatedUserList);
/*  800 */       usergroupDetails.add(userGroup);
/*      */     }
/*      */     
/*  803 */     return usergroupDetails;
/*      */   }
/*      */   
/*      */   public static ArrayList<Hashtable<String, String>> getUserListDetails(int userId) {
/*  807 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/*  808 */     String userQuery = "select AM_UserPasswordTable.USERID,min(AM_UserGroupTable.USERNAME)USERNAME,min(GROUPNAME)GROUPNAME,min(EMAILID)EMAILID,min(description)description,min(apikey)apikey  FROM  AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME  = AM_UserGroupTable.USERNAME where  AM_UserPasswordTable.USERNAME  NOT IN  ('reportadmin','systemadmin_enterprise')  group by AM_UserPasswordTable.USERID";
/*  809 */     if ((userId != -1) && (userId != 0)) {
/*  810 */       userQuery = "select AM_UserPasswordTable.USERID,min(AM_UserGroupTable.USERNAME)USERNAME,min(GROUPNAME)GROUPNAME,min(EMAILID)EMAILID,min(description)description,min(apikey)apikey  FROM  AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME  = AM_UserGroupTable.USERNAME where AM_UserPasswordTable.USERID=" + userId + " AND AM_UserPasswordTable.USERNAME  NOT IN  ('reportadmin','systemadmin_enterprise')  group by AM_UserPasswordTable.USERID";
/*      */     }
/*  812 */     else if (userId == 0) {
/*  813 */       userQuery = "select AM_UserPasswordTable.USERID,min(AM_UserGroupTable.USERNAME)USERNAME,min(GROUPNAME)GROUPNAME,min(EMAILID)EMAILID,min(description)description,min(apikey)apikey  FROM  AM_UserPasswordTable JOIN AM_UserGroupTable ON AM_UserPasswordTable.USERNAME  = AM_UserGroupTable.USERNAME where  AM_UserPasswordTable.USERNAME  NOT IN  ('reportadmin','systemadmin_enterprise','admin')  group by AM_UserPasswordTable.USERID";
/*      */     }
/*  815 */     ArrayList<ArrayList> lists = DBUtil.getRows(userQuery);
/*  816 */     String id = "";
/*  817 */     String userName = "";
/*  818 */     String role = "";
/*  819 */     String email = "-";
/*  820 */     String description = "-";
/*  821 */     String apikey = "";
/*  822 */     for (ArrayList<String> userDetails : lists)
/*      */     {
/*  824 */       id = (String)userDetails.get(0);
/*  825 */       userName = (String)userDetails.get(1);
/*  826 */       role = (String)userDetails.get(2);
/*      */       
/*  828 */       if (userDetails.get(3) != null) {
/*  829 */         email = (String)userDetails.get(3);
/*      */       } else {
/*  831 */         email = "-";
/*      */       }
/*      */       
/*  834 */       if (userDetails.get(4) != null) {
/*  835 */         description = (String)userDetails.get(4);
/*      */       } else {
/*  837 */         description = "-";
/*      */       }
/*      */       
/*  840 */       if (userDetails.get(5) != null) {
/*  841 */         apikey = (String)userDetails.get(5);
/*      */       } else {
/*  843 */         apikey = "";
/*      */       }
/*      */       
/*  846 */       ArrayList<Hashtable> groupList = new ArrayList();
/*      */       
/*  848 */       ArrayList<ArrayList> List = new ArrayList();
/*  849 */       List = DBUtil.getRows("select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + userName + "'");
/*  850 */       String groupName = "";
/*  851 */       if (((List.size() == 0) && ("ADMIN".equals(role))) || ("USERS".equals(role))) {
/*  852 */         Hashtable<String, String> group = new Hashtable();
/*  853 */         group.put("groupId", "-");
/*  854 */         group.put("groupName", "All Monitor Groups");
/*  855 */         groupList.add(group);
/*      */       } else {
/*  857 */         for (ArrayList<String> groupDetails : List) {
/*  858 */           Hashtable<String, String> group = new Hashtable();
/*  859 */           group.put("groupId", groupDetails.get(0));
/*  860 */           group.put("groupName", groupDetails.get(1));
/*  861 */           groupList.add(group);
/*      */         }
/*      */       }
/*  864 */       ArrayList<ArrayList> userGroup = DBUtil.getRows("select AM_USERGROUP_CONFIG.GROUPID,AM_USERGROUP_CONFIG.GROUPNAME from AM_USERGROUP_CONFIG,AM_USERGROUP_OWNERS where AM_USERGROUP_CONFIG.GROUPID=AM_USERGROUP_OWNERS.GROUPID and AM_USERGROUP_OWNERS.USERID=" + id);
/*  865 */       ArrayList<Hashtable<String, String>> userGroupList = new ArrayList();
/*  866 */       for (ArrayList<String> groups : userGroup) {
/*  867 */         Hashtable<String, String> details = new Hashtable();
/*  868 */         details.put("id", groups.get(0));
/*  869 */         details.put("name", groups.get(1));
/*  870 */         userGroupList.add(details);
/*      */       }
/*  872 */       Hashtable user = new Hashtable();
/*  873 */       user.put("userId", id);
/*  874 */       user.put("userName", userName);
/*  875 */       user.put("role", role);
/*  876 */       user.put("email", email);
/*  877 */       user.put("description", description);
/*  878 */       user.put("apikey", apikey);
/*  879 */       user.put("AssociatedGroups", groupList);
/*  880 */       user.put("AssociatedUserGroups", userGroupList);
/*  881 */       usersList.add(user);
/*      */     }
/*  883 */     return usersList;
/*      */   }
/*      */   
/*      */   public static boolean checkDuplicateEntry(String tablename, String columnname, String valuetoCheck, String whereCondition) {
/*  887 */     boolean isDuplicate = false;
/*  888 */     ResultSet rs = null;
/*      */     try {
/*  890 */       String query = "select " + columnname + " from " + tablename + whereCondition;
/*  891 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  892 */       while (rs.next()) {
/*  893 */         String value = rs.getString(1);
/*  894 */         if (value.equalsIgnoreCase(valuetoCheck)) {
/*  895 */           isDuplicate = true;
/*  896 */           break;
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  900 */       ex.printStackTrace();
/*      */     } finally {
/*  902 */       if (rs != null) {
/*  903 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  906 */     return isDuplicate;
/*      */   }
/*      */   
/*      */   public static boolean deleteDirectory(File path) {
/*  910 */     File[] files = path.listFiles();
/*  911 */     for (int i = 0; i < files.length; i++) {
/*  912 */       if (files[i].isDirectory()) {
/*  913 */         deleteDirectory(files[i]);
/*      */       } else {
/*  915 */         files[i].delete();
/*      */       }
/*      */     }
/*      */     
/*  919 */     return path.delete();
/*      */   }
/*      */   
/*      */   public static String getAllAPMRoles(HttpServletRequest request, boolean isJsonFormat) {
/*  923 */     String uri = request.getRequestURI();
/*  924 */     String outputString = "";
/*  925 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/*  926 */     List<String> list = USER_GROUPS;
/*  927 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  929 */       Hashtable<String, String> userDetails = new Hashtable();
/*  930 */       String name = (String)list.get(i);
/*  931 */       userDetails.put("Role", name);
/*  932 */       userDetails.put("displayName", getDisplayName(name));
/*  933 */       userDetails.put("description", getDescription(name));
/*  934 */       usersList.add(userDetails);
/*      */     }
/*      */     
/*  937 */     HashMap<String, Object> results = new HashMap();
/*  938 */     results.put("response-code", "4000");
/*  939 */     results.put("uri", uri);
/*  940 */     results.put("result", usersList);
/*  941 */     results.put("sortingParam", "UserName");
/*  942 */     results.put("parentNode", "UserRoles");
/*  943 */     results.put("nodeName", "Role");
/*      */     try {
/*  945 */       outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception e) {
/*  948 */       e.printStackTrace();
/*      */     }
/*  950 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String getDescription(String grpName)
/*      */   {
/*  955 */     if (grpName.equals("USERS"))
/*      */     {
/*  957 */       return FormatUtil.getString("am.webclient.role.user.description");
/*      */     }
/*  959 */     if (grpName.equals("OPERATOR"))
/*      */     {
/*  961 */       return FormatUtil.getString("am.webclient.role.operator.description");
/*      */     }
/*  963 */     if (grpName.equals("ADMIN"))
/*      */     {
/*  965 */       return FormatUtil.getString("am.webclient.role.admin.description");
/*      */     }
/*  967 */     if (grpName.equals("MANAGER"))
/*      */     {
/*  969 */       return FormatUtil.getString("am.webclient.role.manager.description");
/*      */     }
/*      */     
/*      */ 
/*  973 */     return FormatUtil.getString("am.webclient.role.notavailable");
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getDisplayName(String grpName)
/*      */   {
/*  979 */     if (grpName.equals("USERS"))
/*      */     {
/*  981 */       return FormatUtil.getString("User");
/*      */     }
/*  983 */     if (grpName.equals("OPERATOR"))
/*      */     {
/*  985 */       return FormatUtil.getString("Operator");
/*      */     }
/*  987 */     if (grpName.equals("ADMIN"))
/*      */     {
/*  989 */       return FormatUtil.getString("Administrator");
/*      */     }
/*  991 */     if (grpName.equals("MANAGER"))
/*      */     {
/*  993 */       return FormatUtil.getString("Manager");
/*      */     }
/*      */     
/*      */ 
/*  997 */     return FormatUtil.getString("am.webclient.role.notavailable");
/*      */   }
/*      */   
/*      */   public static String createDomainConfig(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1002 */     String outputString = null;
/*      */     try {
/* 1004 */       if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (CommonAPIUtil.getOwnerRole(request).equals("ADMIN")))
/*      */       {
/* 1006 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */       }
/* 1008 */       request.getParameterMap();
/* 1009 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 1010 */       boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 1011 */       String uri = request.getRequestURI();
/* 1012 */       if (isJsonFormat) {
/* 1013 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1015 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/* 1017 */       Map<String, String> domainDetails = new HashMap();
/* 1018 */       int domainPort = -1;
/* 1019 */       int permission = 0;
/*      */       
/* 1021 */       if (request.getParameter("domainname") != null) {
/* 1022 */         int oldDomain = ADAuthenticationUtil.getDomainID(request.getParameter("domainname"));
/* 1023 */         if (oldDomain > 0) {
/* 1024 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.duplicate.text"), "4101");
/*      */         }
/*      */         
/* 1027 */         domainDetails.put("domainName", request.getParameter("domainname"));
/*      */       } else {
/* 1029 */         return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "domainname");
/*      */       }
/*      */       
/* 1032 */       if (request.getParameter("domaincontroller") != null) {
/* 1033 */         domainDetails.put("domainController", request.getParameter("domaincontroller"));
/*      */       } else {
/* 1035 */         return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "domaincontroller");
/*      */       }
/*      */       
/* 1038 */       if (request.getParameter("domainport") != null) {
/* 1039 */         if (Constants.isIntegerNumber(request.getParameter("domainport"))) {
/* 1040 */           domainPort = Integer.parseInt(request.getParameter("domainport"));
/*      */         } else {
/* 1042 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig create", "domainport");
/*      */         }
/*      */       } else {
/* 1045 */         return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "domainport");
/*      */       }
/*      */       
/* 1048 */       if (request.getParameter("service") != null) {
/* 1049 */         String authentication = request.getParameter("service");
/* 1050 */         if (("AD".equalsIgnoreCase(authentication)) || ("OpenLDAP".equalsIgnoreCase(authentication))) {
/* 1051 */           if ("AD".equalsIgnoreCase(authentication)) {
/* 1052 */             domainDetails.put("authentication", "ACTIVE DIRECTORY");
/*      */           } else {
/* 1054 */             domainDetails.put("authentication", "LDAP");
/*      */           }
/*      */         }
/*      */         else {
/* 1058 */           AMLog.debug("REST API : Domain Create wrong Service");
/* 1059 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.add.authenticatetype.text"), "4102");
/*      */         }
/*      */       }
/*      */       else {
/* 1063 */         return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "service");
/*      */       }
/*      */       
/* 1066 */       if (request.getParameter("permission") != null) {
/* 1067 */         if (Constants.isIntegerNumber(request.getParameter("permission"))) {
/* 1068 */           permission = Integer.parseInt(request.getParameter("permission"));
/*      */         } else {
/* 1070 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig create", "permission");
/*      */         }
/*      */       }
/*      */       else {
/* 1074 */         permission = 0;
/*      */       }
/*      */       
/* 1077 */       boolean isUsername = false;
/* 1078 */       if (request.getParameter("username") != null) {
/* 1079 */         domainDetails.put("domainUsername", request.getParameter("username"));
/* 1080 */         isUsername = true;
/*      */       }
/*      */       
/* 1083 */       if (request.getParameter("password") != null) {
/* 1084 */         if (isUsername) {
/* 1085 */           domainDetails.put("domainPassword", request.getParameter("password"));
/* 1086 */           if (isAPICallFromAAM)
/*      */           {
/* 1088 */             String domainPassword = (String)domainDetails.get("domainPassword");
/* 1089 */             domainPassword = Coding.convertFromBase(domainPassword);
/* 1090 */             domainDetails.put("domainPassword", domainPassword);
/*      */           }
/*      */         } else {
/* 1093 */           return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "username");
/*      */         }
/* 1095 */       } else if (isUsername) {
/* 1096 */         return APIUtilities.emptyParameterResponse(request, response, "DomainConfig : create", "password");
/*      */       }
/* 1098 */       String aamDomainId = request.getParameter("aamDomainId");
/* 1099 */       if ((aamDomainId != null) && (aamDomainId.trim().length() > 0)) {
/* 1100 */         domainDetails.put("aamDomainId", aamDomainId);
/*      */       }
/* 1102 */       domainDetails.put("isAPICallFromAAM", String.valueOf(isAPICallFromAAM));
/*      */       
/* 1104 */       ADAuthenticationUtil.setPortNumber(domainPort);
/* 1105 */       domainDetails.put("domainPermission", permission + "");
/* 1106 */       if ("true".equalsIgnoreCase(request.getParameter("forceadd"))) {
/* 1107 */         isUsername = false;
/*      */       }
/*      */       
/* 1110 */       domainDetails.put("sslenabled", "false");
/* 1111 */       if (request.getParameter("sslenabled") != null) {
/* 1112 */         domainDetails.put("sslenabled", request.getParameter("sslenabled"));
/*      */       }
/*      */       
/* 1115 */       int domainid = -1;
/* 1116 */       if (isUsername) {
/* 1117 */         DirContext ctx = ADAuthenticationUtil.ldapAuthentication(domainDetails);
/* 1118 */         if (ctx != null) {
/* 1119 */           domainid = ADAuthenticationUtil.insertDomainDetails(domainDetails, true);
/*      */         } else {
/* 1121 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.add.failure.text"), "4102");
/*      */         }
/*      */       }
/*      */       else {
/* 1125 */         domainid = ADAuthenticationUtil.insertDomainDetails(domainDetails);
/*      */       }
/*      */       
/*      */ 
/* 1129 */       if (domainid != -1) {
/* 1130 */         ArrayList<Hashtable> domainConfig = new ArrayList();
/* 1131 */         ArrayList addedDetails = ADAuthenticationUtil.getDomainDetails(String.valueOf(domainid));
/* 1132 */         Hashtable details = new Hashtable();
/* 1133 */         details.put("DomainID", domainid + "");
/* 1134 */         details.put("DomainName", addedDetails.get(1));
/* 1135 */         details.put("DomainController", addedDetails.get(2));
/* 1136 */         details.put("DomainPort", String.valueOf(addedDetails.get(3)));
/* 1137 */         details.put("DomainPermission", addedDetails.get(7) + "");
/*      */         
/* 1139 */         domainConfig.add(details);
/*      */         
/* 1141 */         HashMap results = new HashMap();
/* 1142 */         results.put("response-code", "4000");
/* 1143 */         results.put("uri", uri);
/* 1144 */         results.put("result", domainConfig);
/* 1145 */         results.put("sortingParam", "DomainID");
/* 1146 */         results.put("parentNode", "DomainConfiguration");
/* 1147 */         results.put("nodeName", "Details");
/* 1148 */         return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */       }
/*      */       
/* 1151 */       return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.add.failure.text"), "4102");
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1157 */       ex.printStackTrace();
/*      */     }
/* 1159 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String updateDomainConfig(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1163 */     String outputString = null;
/*      */     try {
/* 1165 */       if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (CommonAPIUtil.getOwnerRole(request).equals("ADMIN")))
/*      */       {
/* 1167 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */       }
/* 1169 */       String uri = request.getRequestURI();
/* 1170 */       if (isJsonFormat) {
/* 1171 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1173 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 1176 */       int domainid = 0;
/* 1177 */       int domainPort = 389;
/* 1178 */       Map paramMap = new HashMap();
/* 1179 */       ArrayList domainDetails = new ArrayList();
/* 1180 */       if (request.getParameter("domainname") != null) {
/* 1181 */         String domainname = request.getParameter("domainname");
/* 1182 */         domainid = ADAuthenticationUtil.getDomainID(domainname);
/* 1183 */         if (domainid == 0) {
/* 1184 */           AMLog.debug("REST API : The Domain Name does not exist");
/* 1185 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.namenotexist"), "4102");
/*      */         }
/*      */         
/* 1188 */         domainDetails = ADAuthenticationUtil.getDomainDetails(String.valueOf(domainid));
/*      */       }
/* 1190 */       else if (request.getParameter("domainid") != null) {
/* 1191 */         String id = request.getParameter("domainid");
/* 1192 */         if (Constants.isIntegerNumber(id)) {
/* 1193 */           domainDetails = ADAuthenticationUtil.getDomainDetails(id);
/* 1194 */           if (domainDetails.size() == 0) {
/* 1195 */             AMLog.debug("REST API : The Domain id does not exist");
/* 1196 */             return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.idnotexist"), "4102");
/*      */           }
/*      */           
/* 1199 */           domainid = ((Integer)domainDetails.get(0)).intValue();
/*      */         }
/*      */         else {
/* 1202 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig delete", "domainid");
/*      */         }
/*      */       } else {
/* 1205 */         return APIUtilities.emptyParameterResponse(request, response, "Domain config : update", FormatUtil.getString("am.api.domain.emptyparameter.response"));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1210 */       paramMap.put("domainID", Integer.valueOf(domainid));
/* 1211 */       paramMap.put("domainName", (String)domainDetails.get(1));
/*      */       
/* 1213 */       if (request.getParameter("domaincontroller") != null) {
/* 1214 */         paramMap.put("domainController", request.getParameter("domaincontroller"));
/*      */       } else {
/* 1216 */         paramMap.put("domainController", domainDetails.get(2));
/*      */       }
/*      */       
/* 1219 */       if (request.getParameter("domainport") != null) {
/* 1220 */         if (Constants.isIntegerNumber(request.getParameter("domainport"))) {
/* 1221 */           paramMap.put("domainPort", Integer.valueOf(Integer.parseInt(request.getParameter("domainport"))));
/*      */         } else {
/* 1223 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig update", "domainport");
/*      */         }
/*      */       } else {
/* 1226 */         paramMap.put("domainPort", (Integer)domainDetails.get(3));
/*      */       }
/* 1228 */       if (request.getParameter("sslenabled") != null) {
/* 1229 */         paramMap.put("sslenabled", request.getParameter("sslenabled"));
/*      */       } else {
/* 1231 */         paramMap.put("sslenabled", domainDetails.get(8));
/*      */       }
/* 1233 */       if (request.getParameter("service") != null) {
/* 1234 */         String authentication = request.getParameter("service");
/* 1235 */         if (("AD".equalsIgnoreCase(authentication)) || ("OpenLDAP".equalsIgnoreCase(authentication))) {
/* 1236 */           if ("AD".equalsIgnoreCase(authentication)) {
/* 1237 */             paramMap.put("authentication", "ACTIVE DIRECTORY");
/*      */           } else {
/* 1239 */             paramMap.put("authentication", "LDAP");
/*      */           }
/*      */         } else {
/* 1242 */           AMLog.debug("REST API : Domain Update wrong authentication type");
/* 1243 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.add.authenticatetype.text"), "4102");
/*      */         }
/*      */       }
/*      */       else {
/* 1247 */         paramMap.put("authentication", domainDetails.get(6));
/*      */       }
/* 1249 */       int permission = -1;
/* 1250 */       if (request.getParameter("permission") != null) {
/* 1251 */         if (Constants.isIntegerNumber(request.getParameter("permission"))) {
/* 1252 */           permission = Integer.parseInt(request.getParameter("permission"));
/* 1253 */           if ((permission != 0) && (permission != 1)) {
/* 1254 */             AMLog.debug("REST API : Wrong domain permission");
/* 1255 */             return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.update.permission.wrong.text"), "4102");
/*      */           }
/*      */           
/* 1258 */           paramMap.put("domainPermission", Integer.valueOf(permission));
/*      */         } else {
/* 1260 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig Update", "permission");
/*      */         }
/*      */       } else {
/* 1263 */         paramMap.put("domainPermission", (Integer)domainDetails.get(7));
/*      */       }
/*      */       
/* 1266 */       boolean updateUsername = false;
/* 1267 */       if (request.getParameter("username") != null) {
/* 1268 */         paramMap.put("domainUsername", request.getParameter("username"));
/* 1269 */         updateUsername = true;
/*      */       }
/*      */       
/* 1272 */       if (request.getParameter("password") != null) {
/* 1273 */         if (updateUsername) {
/* 1274 */           paramMap.put("domainPassword", request.getParameter("password"));
/* 1275 */           String apiCallFromAAM = request.getParameter("apicallfrom");
/* 1276 */           if ((apiCallFromAAM != null) && (apiCallFromAAM.equals("admin")))
/*      */           {
/* 1278 */             String domainPassword = (String)paramMap.get("domainPassword");
/* 1279 */             domainPassword = Coding.convertFromBase(domainPassword);
/* 1280 */             paramMap.put("domainPassword", domainPassword);
/*      */           }
/*      */         } else {
/* 1283 */           return APIUtilities.emptyParameterResponse(request, response, "Domain config : update", "username");
/*      */         }
/* 1285 */       } else if (updateUsername) {
/* 1286 */         return APIUtilities.emptyParameterResponse(request, response, "Domain config : update", "password");
/*      */       }
/*      */       
/* 1289 */       if ("true".equalsIgnoreCase(request.getParameter("forceadd"))) {
/* 1290 */         updateUsername = false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1295 */       boolean updated = false;
/*      */       
/* 1297 */       if (updateUsername) {
/* 1298 */         ADAuthenticationUtil.setPortNumber(((Integer)paramMap.get("domainPort")).intValue());
/* 1299 */         DirContext ctx = ADAuthenticationUtil.ldapAuthentication(paramMap);
/* 1300 */         if (ctx != null) {
/* 1301 */           updated = ADAuthenticationUtil.updateDomainDetails(paramMap, true);
/*      */         } else {
/* 1303 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.update.failure.text"), "4500");
/*      */         }
/*      */         
/*      */       }
/* 1307 */       else if (paramMap.get("domainUsername") != null) {
/* 1308 */         updated = ADAuthenticationUtil.updateDomainDetails(paramMap, true);
/*      */       } else {
/* 1310 */         updated = ADAuthenticationUtil.updateDomainDetails(paramMap, false);
/*      */       }
/*      */       
/*      */ 
/* 1314 */       if (updated) {
/* 1315 */         ArrayList<Hashtable> domainConfig = new ArrayList();
/* 1316 */         ArrayList addedDetails = ADAuthenticationUtil.getDomainDetails(String.valueOf(domainid));
/* 1317 */         Hashtable details = new Hashtable();
/* 1318 */         details.put("DomainID", domainid + "");
/* 1319 */         details.put("DomainName", addedDetails.get(1));
/* 1320 */         details.put("DomainController", addedDetails.get(2));
/* 1321 */         details.put("DomainPort", String.valueOf(addedDetails.get(3)));
/* 1322 */         if (addedDetails.get(5) != null) {
/* 1323 */           details.put("DomainUsername", addedDetails.get(5));
/*      */         } else {
/* 1325 */           details.put("DomainUsername", "");
/*      */         }
/* 1327 */         details.put("DomainPermission", addedDetails.get(7) + "");
/* 1328 */         String authentication = String.valueOf(addedDetails.get(6));
/* 1329 */         if ("LDAP".equalsIgnoreCase(authentication)) {
/* 1330 */           details.put("sslenabled", addedDetails.get(8) + "");
/*      */         }
/* 1332 */         domainConfig.add(details);
/*      */         
/* 1334 */         HashMap results = new HashMap();
/* 1335 */         results.put("response-code", "4000");
/* 1336 */         results.put("uri", uri);
/* 1337 */         results.put("result", domainConfig);
/* 1338 */         results.put("sortingParam", "DomainID");
/* 1339 */         results.put("parentNode", "DomainConfiguration");
/* 1340 */         results.put("nodeName", "Details");
/* 1341 */         return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */       }
/*      */       
/*      */ 
/* 1345 */       return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.update.failure.text"), "4500");
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/* 1353 */       ex.printStackTrace();
/*      */     }
/* 1355 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String domainConfigurationDetails(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1359 */     String outputString = null;
/*      */     try {
/* 1361 */       String uri = request.getRequestURI();
/* 1362 */       if (isJsonFormat) {
/* 1363 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1365 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 1368 */       ArrayList<Hashtable<String, String>> domainDetails = new ArrayList();
/* 1369 */       ManagedApplication mo = new ManagedApplication();
/* 1370 */       ArrayList<ArrayList<String>> lists = mo.getRows("select ID,DOMAINNAME,DNSHOST,PORT,DEFAULTNAMINGCONTEXT,USERNAME,AUTHENTICATION,PERMISSION from AM_DOMAINCONTROLLERS");
/* 1371 */       for (int i = 0; i < lists.size(); i++) {
/* 1372 */         Hashtable<String, String> domain = new Hashtable();
/* 1373 */         ArrayList<String> details = (ArrayList)lists.get(i);
/* 1374 */         domain.put("DOMAINID", details.get(0));
/* 1375 */         domain.put("DOMAINNAME", details.get(1));
/* 1376 */         domain.put("DOMAINCONTROLLER", details.get(2));
/* 1377 */         domain.put("DOMAINPORT", (String)details.get(3) + "");
/* 1378 */         if (details.get(5) != null) {
/* 1379 */           domain.put("USERNAME", details.get(5));
/*      */         } else {
/* 1381 */           domain.put("USERNAME", "");
/*      */         }
/* 1383 */         domain.put("DIRECTORYSERVICE", details.get(6));
/* 1384 */         domain.put("PERMISSION", details.get(7));
/* 1385 */         domain.put("SSLENABLED", "false");
/* 1386 */         String sslenabled = (String)details.get(8);
/*      */         
/* 1388 */         if ("1".equals(sslenabled)) {
/* 1389 */           domain.put("SSLENABLED", "true");
/*      */         }
/* 1391 */         domainDetails.add(domain);
/*      */       }
/*      */       
/* 1394 */       if (lists.size() == 0) {
/* 1395 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.useradministration.domain.api.empty.text"), "4000");
/*      */       }
/*      */       
/*      */ 
/* 1399 */       HashMap<String, Object> results = new HashMap();
/* 1400 */       results.put("response-code", "4000");
/* 1401 */       results.put("uri", uri);
/* 1402 */       results.put("result", domainDetails);
/* 1403 */       results.put("sortingParam", "DOMAINID");
/* 1404 */       results.put("parentNode", "DomainConfiguration");
/* 1405 */       results.put("nodeName", "DomainDetails");
/* 1406 */       return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception ex) {
/* 1409 */       ex.printStackTrace();
/*      */     }
/* 1411 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteDomainConfig(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1415 */     String outputString = null;
/*      */     try {
/* 1417 */       if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (CommonAPIUtil.getOwnerRole(request).equals("ADMIN")))
/*      */       {
/* 1419 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */       }
/* 1421 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 1422 */       boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 1423 */       if (isJsonFormat) {
/* 1424 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1426 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/* 1428 */       int domainid = -1;
/* 1429 */       if (request.getParameter("domainname") != null) {
/* 1430 */         String domainname = request.getParameter("domainname");
/* 1431 */         domainid = ADAuthenticationUtil.getDomainID(domainname);
/* 1432 */         if (domainid == 0) {
/* 1433 */           AMLog.debug("REST API : The Domain Name does not exist");
/* 1434 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.namenotexist"), "4102");
/*      */         }
/*      */       }
/* 1437 */       else if (request.getParameter("domainid") != null) {
/* 1438 */         String id = request.getParameter("domainid");
/* 1439 */         if ((isAPICallFromAAM) && (id.equals("all"))) {
/* 1440 */           boolean isSuccess = ADAuthenticationUtil.deleteAllDomainDetails();
/* 1441 */           if (isSuccess) {
/* 1442 */             return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.success.text"), "4000");
/*      */           }
/*      */           
/* 1445 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.failure.text"), "4500");
/*      */         }
/*      */         
/*      */ 
/* 1449 */         if (Constants.isIntegerNumber(id)) {
/* 1450 */           ArrayList details = ADAuthenticationUtil.getDomainDetails(id);
/* 1451 */           if (details.size() == 0) {
/* 1452 */             AMLog.debug("REST API : The Domain id does not exist");
/* 1453 */             return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.idnotexist"), "4102");
/*      */           }
/*      */           
/* 1456 */           domainid = ((Integer)details.get(0)).intValue();
/*      */         }
/*      */         else {
/* 1459 */           return APIUtilities.defaultIntegerResponse(request, response, "DomainConfig delete", "domainid");
/*      */         }
/*      */       }
/*      */       else {
/* 1463 */         return APIUtilities.emptyParameterResponse(request, response, "Domain config : delete", FormatUtil.getString("am.api.domain.emptyparameter.response"));
/*      */       }
/*      */       
/* 1466 */       Hashtable<String, String> ownerDetails = CommonAPIUtil.getUserNameForAPIKey(request.getParameter("apikey"));
/* 1467 */       String username = (String)ownerDetails.get("USERNAME");
/* 1468 */       List domainList = new ArrayList();
/* 1469 */       domainList.add(domainid + "");
/* 1470 */       boolean deleteStatus = deleteDomainDetails(domainList, username);
/* 1471 */       if (deleteStatus) {
/* 1472 */         AMLog.debug("REST API : Domain id " + domainid + " deleted successfully");
/* 1473 */         return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.success.text"), "4000");
/*      */       }
/*      */       
/* 1476 */       AMLog.debug("REST API : Cannot delete the domainid : " + domainid);
/* 1477 */       return URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.failure.text"), "4500");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1481 */       ex.printStackTrace();
/*      */     }
/* 1483 */     return outputString;
/*      */   }
/*      */   
/*      */   public static boolean deleteDomainDetails(List<String> domainList, String username) {
/* 1487 */     boolean deleteStatus = false;
/*      */     try {
/* 1489 */       for (String domainid : domainList) {
/* 1490 */         ResultSet rs = null;
/*      */         try {
/* 1492 */           String query = "select USERID from AM_DOMAINUSER_MAPPER where DOMAINID=" + domainid;
/* 1493 */           List users = new ArrayList();
/* 1494 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1495 */           while (rs.next()) {
/* 1496 */             users.add(rs.getString("USERID"));
/*      */           }
/* 1498 */           deleteUserDetails(users, username);
/*      */         } catch (Exception ex) {
/* 1500 */           ex.printStackTrace();
/*      */         } finally {
/* 1502 */           if (rs != null) {
/* 1503 */             AMConnectionPool.closeResultSet(rs);
/*      */           }
/*      */         }
/* 1506 */         String usergroupdomain = "delete from AM_DOMAINUSERGROUP_MAPPING where DOMAINID=" + domainid;
/* 1507 */         String domainusermapping = "delete from AM_DOMAINUSER_MAPPER where DOMAINID=" + domainid;
/* 1508 */         String domainController = "delete from AM_DOMAINCONTROLLERS where ID=" + domainid;
/* 1509 */         AMConnectionPool.executeUpdateStmt(usergroupdomain);
/* 1510 */         AMConnectionPool.executeUpdateStmt(domainusermapping);
/* 1511 */         AMConnectionPool.executeUpdateStmt(domainController);
/* 1512 */         deleteStatus = true;
/*      */         
/*      */ 
/* 1515 */         if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled())) {
/* 1516 */           HashMap<String, String> paramsToMas = new HashMap();
/* 1517 */           paramsToMas.put("domainid", domainid);
/* 1518 */           paramsToMas.put("TO_DELETE", "true");
/* 1519 */           paramsToMas.put("apicallfrom", "admin");
/* 1520 */           MASSyncUtil.addTasktoSync(paramsToMas, "/AppManager/xml/domain", "all", "POST", 11, 3);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1524 */       ex.printStackTrace();
/*      */     }
/* 1526 */     return deleteStatus;
/*      */   }
/*      */   
/*      */   public static String domainConfigAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1531 */     if (request.getMethod().equals("GET")) {
/* 1532 */       return domainConfigurationDetails(request, response, isJsonFormat);
/*      */     }
/* 1534 */     return domainConfigPostOperations(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */   public static String domainConfigPostOperations(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 1540 */     if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/* 1541 */       return deleteDomainConfig(request, response, isJsonFormat);
/* 1542 */     if (request.getParameter("domainname") != null) {
/* 1543 */       String domainname = request.getParameter("domainname");
/* 1544 */       int oldDomain = ADAuthenticationUtil.getDomainID(domainname);
/* 1545 */       if (oldDomain > 0) {
/* 1546 */         return updateDomainConfig(request, response, isJsonFormat);
/*      */       }
/* 1548 */       return createDomainConfig(request, response, isJsonFormat);
/*      */     }
/* 1550 */     if (request.getParameter("domainid") != null) {
/* 1551 */       String domainid = request.getParameter("domainid");
/* 1552 */       return updateDomainConfig(request, response, isJsonFormat);
/*      */     }
/*      */     
/* 1555 */     return domainConfigurationDetails(request, response, isJsonFormat);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\UserConfigurationUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */