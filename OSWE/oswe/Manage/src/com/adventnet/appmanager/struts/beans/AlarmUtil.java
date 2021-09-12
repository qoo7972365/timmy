/*      */ package com.adventnet.appmanager.struts.beans;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
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
/*      */ public class AlarmUtil
/*      */ {
/*   39 */   private Hashtable table = new Hashtable();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Hashtable getApplicationAlerts(String severity)
/*      */   {
/*   46 */     return getApplicationAlerts(severity, "");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Hashtable getApplicationAlerts(String severity, String viewId)
/*      */   {
/*   53 */     String severitycondition = "";
/*   54 */     ManagedApplication mo = new ManagedApplication();
/*   55 */     if (severity != null)
/*      */     {
/*   57 */       severitycondition = " and severity ='" + severity + "'";
/*      */     }
/*   59 */     if (viewId.equals("Alerts.2"))
/*      */     {
/*   61 */       severitycondition = " and MODTIME > " + (System.currentTimeMillis() - 3600000L);
/*      */     }
/*   63 */     else if (viewId.equals("Alerts.4"))
/*      */     {
/*   65 */       severitycondition = " and MODTIME > " + (System.currentTimeMillis() - 86400000L);
/*      */     }
/*      */     
/*   68 */     String haidalertcountquery = "select HAID, count(*)  from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_ManagedObject,Alert where source=AM_ManagedObject.RESOURCEID and parentid=AM_HOLISTICAPPLICATION.HAID and childid=AM_ManagedObject.RESOURCEID " + severitycondition + " AND GROUPNAME='" + "AppManager" + "' group by HAID";
/*   69 */     ArrayList list = mo.getRows(haidalertcountquery);
/*   70 */     if (list.size() == 0)
/*      */     {
/*   72 */       return null;
/*      */     }
/*   74 */     Hashtable whAlertcount = new Hashtable(list.size());
/*   75 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   77 */       ArrayList temp = (ArrayList)list.get(i);
/*   78 */       String resourceid = (String)temp.get(0);
/*   79 */       String alertcount = (String)temp.get(1);
/*   80 */       whAlertcount.put(resourceid, alertcount);
/*      */     }
/*   82 */     return whAlertcount;
/*      */   }
/*      */   
/*      */   public static int getApplicationAlertsForHA(String haid, String severity)
/*      */   {
/*   87 */     int count = 0;
/*   88 */     String severitycondition = "";
/*   89 */     ManagedApplication mo = new ManagedApplication();
/*   90 */     if (!severity.equals("All"))
/*      */     {
/*   92 */       severitycondition = " and severity ='" + severity + "'";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*   97 */     String haidalertcountquery = "select  count(*)  from AM_PARENTCHILDMAPPER , AM_HOLISTICAPPLICATION , AM_ManagedObject,Alert where source=AM_ManagedObject.RESOURCEID\tand parentid=AM_HOLISTICAPPLICATION.HAID and childid=AM_ManagedObject.RESOURCEID " + severitycondition + " and AM_HOLISTICAPPLICATION.HAID='" + haid + "' AND GROUPNAME='" + "AppManager" + "'";
/*   98 */     ArrayList list = mo.getRows(haidalertcountquery);
/*   99 */     if (list.size() != 0)
/*      */     {
/*  101 */       ArrayList list1 = (ArrayList)list.get(0);
/*  102 */       count = Integer.parseInt((String)list1.get(0));
/*      */     }
/*  104 */     return count;
/*      */   }
/*      */   
/*      */   public static void removeNMSAlerts()
/*      */   {
/*  109 */     ManagedApplication mo = new ManagedApplication();
/*  110 */     mo.executeUpdateStmt("delete from Alert where GroupName is NULL");
/*      */   }
/*      */   
/*      */   public static ArrayList getApplications()
/*      */   {
/*  115 */     return getApplications(null, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getApplications(HttpServletRequest request)
/*      */   {
/*  120 */     return getApplications(request, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getApplications(HttpServletRequest request, boolean onlyAdminMG)
/*      */   {
/*  125 */     ManagedApplication mo = new ManagedApplication();
/*  126 */     String bsgFilterCondn = "";
/*  127 */     String bsgType = "0";
/*  128 */     String adminMGcondn = "";
/*  129 */     if (onlyAdminMG)
/*      */     {
/*  131 */       adminMGcondn = " and RESOURCEID <" + EnterpriseUtil.RANGE;
/*      */     }
/*  133 */     String query = "select AM_ManagedObject.RESOURCENAME , AM_ManagedObject.RESOURCEID from AM_ManagedObject , AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + adminMGcondn + " ORDER BY RESOURCENAME";
/*  134 */     if (request != null)
/*      */     {
/*  136 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  138 */         bsgType = "1";
/*  139 */         Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  140 */         bsgFilterCondn = " and RESOURCEID is not null and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*  141 */         query = "select AM_ManagedObject.RESOURCENAME , AM_ManagedObject.RESOURCEID from AM_ManagedObject , AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + adminMGcondn + " ORDER BY RESOURCENAME";
/*  142 */       } else if (ClientDBUtil.isPrivilegedUser(request)) {
/*  143 */         if (Constants.isUserResourceEnabled()) {
/*  144 */           String userid = Constants.getLoginUserid(request);
/*  145 */           query = "select AM_ManagedObject.RESOURCENAME , AM_ManagedObject.RESOURCEID from AM_USERRESOURCESTABLE,AM_ManagedObject , AM_HOLISTICAPPLICATION where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + adminMGcondn + " ORDER BY RESOURCENAME";
/*      */         } else {
/*  147 */           Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  148 */           bsgFilterCondn = " and RESOURCEID is not null and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resids);
/*  149 */           query = "select AM_ManagedObject.RESOURCENAME , AM_ManagedObject.RESOURCEID from AM_ManagedObject , AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + adminMGcondn + " ORDER BY RESOURCENAME";
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  154 */     ArrayList list = mo.getRows(query);
/*  155 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getConfiguredGroups()
/*      */   {
/*  160 */     return getConfiguredGroups(null, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getConfiguredGroups(HttpServletRequest request) {
/*  164 */     return getConfiguredGroups(request, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getConfiguredGroups(HttpServletRequest request, boolean admingrponly) {
/*  168 */     return getConfiguredGroups(request, admingrponly, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getConfiguredGroups(HttpServletRequest request, boolean admingrponly, boolean treeStructure) {
/*  172 */     return getConfiguredGroups(request, admingrponly, treeStructure, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getConfiguredGroups(HttpServletRequest request, boolean admingrponly, boolean treeStructure, boolean removeVMMGs) {
/*  176 */     ArrayList list = new ArrayList();
/*  177 */     if (Constants.subGroupsEnabled.equals("true"))
/*      */     {
/*  179 */       ManagedApplication mo = new ManagedApplication();
/*  180 */       String bsgFilterCondn = "";
/*  181 */       String mgTypeFilterCondn = "";
/*  182 */       boolean isUserResourceEnabled = false;
/*  183 */       String loginUserId = null;
/*  184 */       if (request != null)
/*      */       {
/*  186 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  188 */           Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  189 */           bsgFilterCondn = " WHERE RESOURCEID is not null and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*  190 */         } else if (ClientDBUtil.isPrivilegedUser(request)) {
/*  191 */           if (Constants.isUserResourceEnabled()) {
/*  192 */             isUserResourceEnabled = true;
/*  193 */             loginUserId = Constants.getLoginUserid(request);
/*      */           } else {
/*  195 */             Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  196 */             bsgFilterCondn = " WHERE RESOURCEID is not null and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resids);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  201 */       String adminGroupFilter = "";
/*  202 */       if (admingrponly)
/*      */       {
/*  204 */         adminGroupFilter = " AM_ManagedObject.RESOURCEID < " + EnterpriseUtil.RANGE;
/*  205 */         if ((bsgFilterCondn != null) && (bsgFilterCondn.trim().length() > 0))
/*      */         {
/*  207 */           bsgFilterCondn = bsgFilterCondn + " and " + adminGroupFilter;
/*      */         }
/*      */         else
/*      */         {
/*  211 */           bsgFilterCondn = " where " + adminGroupFilter;
/*      */         }
/*      */       }
/*  214 */       if (removeVMMGs)
/*      */       {
/*  216 */         mgTypeFilterCondn = " AM_HOLISTICAPPLICATION.GROUPTYPE NOT IN (3,4,1009,1010,1012,1013) ";
/*  217 */         if ((bsgFilterCondn != null) && (bsgFilterCondn.trim().length() > 0))
/*      */         {
/*  219 */           bsgFilterCondn = bsgFilterCondn + " and " + mgTypeFilterCondn;
/*      */         }
/*      */         else
/*      */         {
/*  223 */           bsgFilterCondn = " where " + mgTypeFilterCondn;
/*      */         }
/*      */       }
/*  226 */       String query = null;
/*  227 */       if (isUserResourceEnabled)
/*      */       {
/*  229 */         query = "select RESOURCENAME,AM_USERRESOURCESTABLE.RESOURCEID,PARENTID,DISPLAYNAME,AM_ManagedObject.RESOURCEID as MGTREE  from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserId + " " + bsgFilterCondn + " order by PARENTID,UPPER(DISPLAYNAME)";
/*      */       } else {
/*  231 */         query = "select RESOURCENAME,RESOURCEID,PARENTID,DISPLAYNAME,RESOURCEID as MGTREE  from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID " + bsgFilterCondn + " order by PARENTID,UPPER(DISPLAYNAME)";
/*      */       }
/*      */       
/*  234 */       list = mo.getRows(query);
/*      */       
/*  236 */       if (!EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  238 */         list = getDifferntiatedList(list, "&nbsp;&nbsp;", treeStructure);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  243 */     else if (request != null)
/*      */     {
/*  245 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  247 */         list = getApplications(request);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  252 */       list = getApplications();
/*      */     }
/*      */     
/*  255 */     System.out.println("the configured groups list is===>" + list);
/*  256 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getDifferntiatedList(ArrayList allGroups)
/*      */   {
/*  261 */     return getDifferntiatedList(allGroups, "&nbsp;&nbsp;");
/*      */   }
/*      */   
/*      */   public static ArrayList getDifferntiatedList(ArrayList allGroups, String spacerToAppend) {
/*  265 */     return ReportUtil.getDifferntiatedList(allGroups, spacerToAppend, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getDifferntiatedList(ArrayList allGroups, String spacerToAppend, boolean treeStructure)
/*      */   {
/*  270 */     return ReportUtil.getDifferntiatedList(allGroups, spacerToAppend, treeStructure);
/*      */   }
/*      */   
/*      */ 
/*      */   public static void getSortedList(String targetId, ArrayList allGroups, ArrayList diffList, String spacer, String spacerToAppend)
/*      */   {
/*  276 */     ReportUtil.getSortedList(targetId, allGroups, diffList, spacer, spacerToAppend);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getConfiguredMonitorGroups()
/*      */   {
/*  284 */     ArrayList list = new ArrayList();
/*  285 */     if (Constants.subGroupsEnabled.equals("true"))
/*      */     {
/*  287 */       ManagedApplication mo = new ManagedApplication();
/*  288 */       list = mo.getRows("select RESOURCENAME,RESOURCEID,PARENTID from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME");
/*  289 */       list = getDifferntiatedMonitorList(list);
/*      */     }
/*      */     else
/*      */     {
/*  293 */       list = getApplications();
/*      */     }
/*  295 */     System.out.println("the configured groups list is===>" + list);
/*  296 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getDifferntiatedMonitorList(ArrayList allGroups)
/*      */   {
/*  301 */     return getDifferntiatedMonitorList(allGroups, "-->");
/*      */   }
/*      */   
/*      */   public static ArrayList getDifferntiatedMonitorList(ArrayList allGroups, String spacerToAppend) {
/*  305 */     return ReportUtil.getDifferntiatedMonitorList(allGroups, spacerToAppend);
/*      */   }
/*      */   
/*      */ 
/*      */   public static void getSortedMonitorList(String targetId, String targetName, ArrayList allGroups, ArrayList diffList, String spacer, String spacerToAppend)
/*      */   {
/*  311 */     ReportUtil.getSortedMonitorList(targetId, targetName, allGroups, diffList, spacer, spacerToAppend);
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getApplicationsForOwner(String owner)
/*      */   {
/*  317 */     return getApplicationsForOwner(owner, null);
/*      */   }
/*      */   
/*      */   public static ArrayList getApplicationsForOwner(String owner, HttpServletRequest request)
/*      */   {
/*  322 */     ArrayList ResultList = new ArrayList();
/*      */     try
/*      */     {
/*  325 */       if (!Constants.subGroupsEnabled.equals("true"))
/*      */       {
/*  327 */         ManagedApplication mo = new ManagedApplication();
/*  328 */         if (Constants.isUserResourceEnabled()) {
/*  329 */           String loginUserid = Constants.getLoginUserid(request);
/*  330 */           ResultList = mo.getRows("select DISPLAYNAME,AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=USERRESOURCESTABLE.RESOURCEID and AM_HOLISTICAPPLICATION.TYPE=0 and USERRESOURCESTABLE.USERID=" + loginUserid + " order by AM_ManagedObject.RESOURCEID");
/*      */         } else {
/*  332 */           ResultList = mo.getRows("select DISPLAYNAME,RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION.TYPE=0 and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "' order by RESOURCEID");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  337 */         ManagedApplication mo = new ManagedApplication();
/*  338 */         String query = null;
/*  339 */         if (Constants.isUserResourceEnabled()) {
/*  340 */           String loginUserid = Constants.getLoginUserid(request);
/*  341 */           query = "select DISPLAYNAME,AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " order by AM_HOLISTICAPPLICATION.TYPE,DISPLAYNAME,AM_ManagedObject.RESOURCEID";
/*      */         } else {
/*  343 */           query = "select DISPLAYNAME,RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "' order by AM_HOLISTICAPPLICATION.TYPE,DISPLAYNAME,RESOURCEID";
/*      */         }
/*  345 */         ArrayList list = mo.getRows(query);
/*      */         
/*  347 */         ArrayList groupsList = mo.getRows("select DISPLAYNAME,RESOURCEID,PARENTID,RESOURCENAME from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME");
/*      */         
/*  349 */         for (int i = 0; i < list.size(); i++)
/*      */         {
/*  351 */           ArrayList tempList = new ArrayList();
/*  352 */           ArrayList InsideList = (ArrayList)list.get(i);
/*  353 */           tempList.add((String)InsideList.get(0));
/*  354 */           tempList.add((String)InsideList.get(1));
/*  355 */           boolean addToList = true;
/*  356 */           for (int k = 0; k < ResultList.size(); k++) {
/*  357 */             String resultListresid = (String)((ArrayList)ResultList.get(k)).get(1);
/*  358 */             int resultListStrLen = ((ArrayList)ResultList.get(k)).get(0).toString().length();
/*  359 */             String tempListresid = (String)tempList.get(1);
/*  360 */             int tempListStrLen = tempList.get(0).toString().length();
/*  361 */             if (resultListresid.equals(tempListresid)) {
/*  362 */               if (tempListStrLen > resultListStrLen) {
/*  363 */                 addToList = true;
/*  364 */                 ResultList.remove(k);
/*      */               }
/*      */               else {
/*  367 */                 addToList = false;
/*      */               }
/*      */             }
/*      */           }
/*  371 */           if (addToList) {
/*  372 */             ResultList.add(tempList);
/*      */           }
/*  374 */           ReportUtil.getSortedList((String)InsideList.get(1), groupsList, ResultList, "", "--");
/*      */         }
/*  376 */         System.out.println("the alert groups list==>" + ResultList);
/*      */         
/*  378 */         for (int i = 0; i < ResultList.size() - 1; i++)
/*      */         {
/*      */ 
/*  381 */           String sample = (String)((ArrayList)ResultList.get(i)).get(1);
/*  382 */           String sampleResid = (String)((ArrayList)ResultList.get(i)).get(0);
/*  383 */           for (int j = ResultList.size() - 1; j > i; j--)
/*      */           {
/*  385 */             String target = (String)((ArrayList)ResultList.get(j)).get(1);
/*      */             
/*  387 */             if (target.equals(sample))
/*      */             {
/*      */ 
/*  390 */               ResultList.remove(j);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  395 */         System.out.println("the trimmed alert groups list==>" + ResultList);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  400 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  404 */     return ResultList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getAlertsForResource(int resourceid)
/*      */   {
/*  414 */     return getAlertsForResource(resourceid, false);
/*      */   }
/*      */   
/*  417 */   public static ArrayList getAlertsForResource(int resourceid, boolean removeHaid) { return getAlertsForResource(resourceid, removeHaid, null); }
/*      */   
/*      */ 
/*      */   public static Properties getAlertProperties(String userName, String entity)
/*      */   {
/*  422 */     ResultSet rs = null;
/*  423 */     Properties toReturn = new Properties();
/*  424 */     String preSevAsString = "5";
/*  425 */     String sevAsString = "-1";
/*      */     
/*  427 */     String query = "select WHO,SOURCE,PREVIOUSSEVERITY,MODTIME,ID,CATEGORY,ENTITY,STAGE,PRIORITY,CREATETIME,GROUPNAME,MMESSAGE,SEVERITY from Alert where ENTITY='" + entity + "'";
/*      */     
/*      */     try
/*      */     {
/*  431 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  432 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  434 */       if (rs.next())
/*      */       {
/*  436 */         if (rs.getString("PREVIOUSSEVERITY").equals("1"))
/*      */         {
/*      */ 
/*  439 */           preSevAsString = "Critical";
/*  440 */         } else if (rs.getString("PREVIOUSSEVERITY").equals("4"))
/*      */         {
/*  442 */           preSevAsString = "Warning";
/*      */         }
/*  444 */         else if (rs.getString("PREVIOUSSEVERITY").equals("5"))
/*      */         {
/*  446 */           preSevAsString = "Clear";
/*      */         }
/*      */         
/*      */ 
/*  450 */         if (rs.getString("SEVERITY").equals("1"))
/*      */         {
/*  452 */           sevAsString = "Critical";
/*      */         }
/*  454 */         else if (rs.getString("SEVERITY").equals("4"))
/*      */         {
/*      */ 
/*  457 */           sevAsString = "Warning";
/*      */         }
/*  459 */         else if (rs.getString("SEVERITY").equals("5"))
/*      */         {
/*  461 */           sevAsString = "Clear";
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  466 */         toReturn.setProperty("source", rs.getString("SOURCE"));
/*  467 */         toReturn.setProperty("previousSeverity", rs.getString("PREVIOUSSEVERITY"));
/*  468 */         toReturn.setProperty("modTime", rs.getString("MODTIME"));
/*  469 */         toReturn.setProperty("modTimeMillis", "" + rs.getLong("MODTIME"));
/*  470 */         toReturn.setProperty("id", rs.getString("ID"));
/*  471 */         toReturn.setProperty("category", rs.getString("CATEGORY"));
/*  472 */         toReturn.setProperty("entity", rs.getString("ENTITY"));
/*  473 */         toReturn.setProperty("stage", rs.getString("STAGE"));
/*  474 */         toReturn.setProperty("stringpreviousseverity", preSevAsString);
/*  475 */         toReturn.setProperty("stringseverity", sevAsString);
/*  476 */         toReturn.setProperty("priority", rs.getString("PRIORITY"));
/*  477 */         toReturn.setProperty("createTime", rs.getString("CREATETIME"));
/*  478 */         toReturn.setProperty("groupName", rs.getString("GROUPNAME"));
/*  479 */         toReturn.setProperty("message", EnterpriseUtil.decodeString(rs.getString("MMESSAGE")));
/*  480 */         toReturn.setProperty("severity", rs.getString("SEVERITY"));
/*  481 */         toReturn.setProperty("who", rs.getString("WHO"));
/*      */       } else {
/*  483 */         return null;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  489 */       ex.printStackTrace();
/*  490 */       return toReturn;
/*      */     }
/*      */     
/*      */ 
/*  494 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static ArrayList getAlertsForResource(int resourceid, boolean removeHaid, HttpServletRequest request)
/*      */   {
/*  499 */     Vector resourceids = null;
/*  500 */     boolean isUserResourceEnabled = false;
/*  501 */     String loginUserid = null;
/*  502 */     if (request != null) {
/*  503 */       if (Constants.isUserResourceEnabled()) {
/*  504 */         isUserResourceEnabled = true;
/*  505 */         loginUserid = Constants.getLoginUserid(request);
/*      */       } else {
/*  507 */         resourceids = DependantMOUtil.getDependantResourceIDSforOperator(String.valueOf(resourceid), removeHaid, request.getRemoteUser());
/*      */       }
/*      */     }
/*      */     else {
/*  511 */       resourceids = DependantMOUtil.getDependantResourceIDS(String.valueOf(resourceid), removeHaid);
/*      */     }
/*  513 */     String query = null;
/*  514 */     if (isUserResourceEnabled) {
/*  515 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from AM_USERRESOURCESTABLE,Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE AM_USERRESOURCESTABLE.RESOURCEID=Alert.SOURCE and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4')";
/*      */     } else {
/*  517 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4')";
/*      */     }
/*      */     
/*  520 */     toreturn = new ArrayList();
/*  521 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  524 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  525 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  526 */       while (rs.next())
/*      */       {
/*  528 */         ArrayList list = new ArrayList();
/*  529 */         list.add(rs.getString("CATEGORY"));
/*  530 */         list.add(rs.getString("SEVERITY"));
/*  531 */         list.add(rs.getString("ENTITY"));
/*  532 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  533 */         list.add(rs.getString("DISPLAYNAME"));
/*  534 */         list.add(rs.getString("MODTIME"));
/*  535 */         list.add(rs.getString("SOURCE"));
/*  536 */         list.add(rs.getString("TYPE"));
/*  537 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  538 */         list.add(rs.getString("SHORTNAME"));
/*  539 */         list.add(rs.getString("ANNOTATION"));
/*  540 */         list.add(rs.getString("WHO"));
/*  541 */         toreturn.add(list);
/*      */       }
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
/*  559 */       return toreturn;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  546 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  552 */         rs.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  556 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getTopNAlertsForResource(int resourceid, boolean haid, HttpServletRequest request, String topNVal)
/*      */   {
/*  569 */     Vector resourceids = null;
/*  570 */     boolean isUserResourceEnabled = false;
/*  571 */     String loginUserid = null;
/*  572 */     if (request != null) {
/*  573 */       if (Constants.isUserResourceEnabled()) {
/*  574 */         isUserResourceEnabled = true;
/*  575 */         loginUserid = Constants.getLoginUserid(request);
/*      */       } else {
/*  577 */         resourceids = DependantMOUtil.getDependantResourceIDSforOperator(String.valueOf(resourceid), haid, request.getRemoteUser());
/*      */       }
/*      */     } else {
/*  580 */       resourceids = DependantMOUtil.getDependantResourceIDS(String.valueOf(resourceid), haid);
/*      */     }
/*  582 */     String query = null;
/*  583 */     if (isUserResourceEnabled) {
/*  584 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from  AM_USERRESOURCESTABLE, Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE Alert.SOURCE=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4')";
/*      */     } else {
/*  586 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4')";
/*      */     }
/*  588 */     query = DBQueryUtil.getTopNValues(query, topNVal);
/*  589 */     toreturn = new ArrayList();
/*  590 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  593 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  594 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  595 */       while (rs.next())
/*      */       {
/*  597 */         ArrayList list = new ArrayList();
/*  598 */         list.add(rs.getString("CATEGORY"));
/*  599 */         list.add(rs.getString("SEVERITY"));
/*  600 */         list.add(rs.getString("ENTITY"));
/*  601 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  602 */         list.add(rs.getString("DISPLAYNAME"));
/*  603 */         list.add(rs.getString("MODTIME"));
/*  604 */         list.add(rs.getString("SOURCE"));
/*  605 */         list.add(rs.getString("TYPE"));
/*  606 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  607 */         list.add(rs.getString("SHORTNAME"));
/*  608 */         list.add(rs.getString("ANNOTATION"));
/*  609 */         list.add(rs.getString("WHO"));
/*  610 */         toreturn.add(list);
/*      */       }
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
/*  628 */       return toreturn;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  615 */       AMLog.fatal("Exception occured in getTopNAlertsForResource while getting values from db : " + e);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  621 */         rs.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  625 */         AMLog.fatal("Exception occured in getTopNAlertsForResource : " + e);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getAlertsForOwner(String owner)
/*      */   {
/*  635 */     return getAlertsForOwner(owner, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList getAlertsForOwner(String owner, HttpServletRequest request)
/*      */   {
/*  642 */     String usertype = "";
/*  643 */     String query = null;
/*  644 */     if (request != null)
/*      */     {
/*  646 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  648 */         It360SPUserManagementUtil urmgUtil = new It360SPUserManagementUtil();
/*  649 */         String userType = urmgUtil.getUserType(owner);
/*  650 */         if (!"bsguser".equals(userType))
/*      */         {
/*  652 */           Vector resourceids = ClientDBUtil.getResourceIdentity(owner, request, null);
/*  653 */           query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE   " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') order by MODTIME ";
/*      */         }
/*      */       }
/*  656 */       else if (Constants.isUserResourceEnabled()) {
/*  657 */         String userid = Constants.getLoginUserid(request);
/*  658 */         query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type join  AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID = " + userid + "  WHERE    GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') order by MODTIME ";
/*      */       } else {
/*  660 */         Vector resourceids = ClientDBUtil.getResourceIdentity(owner);
/*  661 */         query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type WHERE   " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') order by MODTIME ";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  668 */     ArrayList toreturn = new ArrayList();
/*      */     try
/*      */     {
/*  671 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  672 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  673 */       while (rs.next())
/*      */       {
/*  675 */         ArrayList list = new ArrayList();
/*  676 */         list.add(rs.getString("CATEGORY"));
/*  677 */         list.add(rs.getString("SEVERITY"));
/*  678 */         list.add(rs.getString("ENTITY"));
/*  679 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  680 */         list.add(rs.getString("DISPLAYNAME"));
/*  681 */         list.add(rs.getString("MODTIME"));
/*  682 */         list.add(rs.getString("SOURCE"));
/*  683 */         list.add(rs.getString("TYPE"));
/*  684 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  685 */         list.add(rs.getString("SHORTNAME"));
/*  686 */         list.add(rs.getString("ANNOTATION"));
/*  687 */         list.add(rs.getString("WHO"));
/*  688 */         toreturn.add(list);
/*      */       }
/*  690 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  695 */       e.printStackTrace();
/*      */     }
/*  697 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentAlertsForOwner(HttpServletRequest request, int limit)
/*      */   {
/*  704 */     ArrayList toreturn = new ArrayList();
/*      */     try
/*      */     {
/*  707 */       String query = null;
/*  708 */       if (Constants.isUserResourceEnabled()) {
/*  709 */         String loginUserId = Constants.getLoginUserid(request);
/*  710 */         query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from AM_USERRESOURCESTABLE join  Alert on AM_USERRESOURCESTABLE.RESOURCEID=Alert.SOURCE and AM_USERRESOURCESTABLE.USERID =" + loginUserId + " join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') order by MODTIME ";
/*      */       } else {
/*  712 */         Vector resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  713 */         query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE   " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') order by MODTIME ";
/*      */       }
/*      */       
/*  716 */       query = DBQueryUtil.getTopNValues(query, limit);
/*      */       
/*  718 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  719 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  720 */       while (rs.next())
/*      */       {
/*  722 */         ArrayList list = new ArrayList();
/*  723 */         list.add(rs.getString("CATEGORY"));
/*  724 */         list.add(rs.getString("SEVERITY"));
/*  725 */         list.add(rs.getString("ENTITY"));
/*  726 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  727 */         list.add(rs.getString("DISPLAYNAME"));
/*  728 */         list.add(rs.getString("MODTIME"));
/*  729 */         list.add(rs.getString("SOURCE"));
/*  730 */         list.add(rs.getString("TYPE"));
/*  731 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  732 */         list.add(rs.getString("SHORTNAME"));
/*  733 */         list.add(rs.getString("ANNOTATION"));
/*  734 */         list.add(rs.getString("WHO"));
/*  735 */         toreturn.add(list);
/*      */       }
/*  737 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  742 */       e.printStackTrace();
/*      */     }
/*  744 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentAlertsForOwner(HttpServletRequest request, int limit, String order)
/*      */   {
/*  755 */     return getRecentAlertsForOwner(request, limit, order, new HashMap(), "ALL");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentAlertsForOwner(HttpServletRequest request, int limit, String order, HashMap siteAndCustomerMap, String associatedTypeVals)
/*      */   {
/*  763 */     Vector resourceids = null;
/*  764 */     boolean isUserResourceEnabled = false;
/*  765 */     String loginUserid = null;
/*  766 */     if (associatedTypeVals.equalsIgnoreCase("ALL"))
/*      */     {
/*  768 */       associatedTypeVals = " NOT IN ('NET')";
/*      */     }
/*      */     
/*  771 */     if ((EnterpriseUtil.isIt360MSPEdition) && (!siteAndCustomerMap.isEmpty()))
/*      */     {
/*  773 */       resourceids = CustomerManagementAPI.filterResourceIds(siteAndCustomerMap);
/*      */ 
/*      */ 
/*      */     }
/*  777 */     else if (Constants.isUserResourceEnabled()) {
/*  778 */       isUserResourceEnabled = true;
/*  779 */       loginUserid = Constants.getLoginUserid(request);
/*      */     } else {
/*  781 */       resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */     }
/*      */     
/*      */ 
/*  785 */     String query = null;
/*  786 */     if (isUserResourceEnabled) {
/*  787 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from AM_USERRESOURCESTABLE join Alert on AM_USERRESOURCESTABLE.RESOURCEID=Alert.SOURCE and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE  GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') AND AM_ManagedResourceType.RESOURCEGROUP " + associatedTypeVals + Constants.excludeIntf + " order by MODTIME " + order;
/*      */     } else {
/*  789 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  WHERE   " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') AND AM_ManagedResourceType.RESOURCEGROUP " + associatedTypeVals + Constants.excludeIntf + " order by MODTIME " + order;
/*      */     }
/*      */     
/*  792 */     query = DBQueryUtil.getTopNValues(query, limit);
/*      */     
/*      */ 
/*  795 */     ArrayList toreturn = new ArrayList();
/*      */     try
/*      */     {
/*  798 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  799 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  800 */       while (rs.next())
/*      */       {
/*  802 */         ArrayList list = new ArrayList();
/*  803 */         list.add(rs.getString("CATEGORY"));
/*  804 */         list.add(rs.getString("SEVERITY"));
/*  805 */         list.add(rs.getString("ENTITY"));
/*  806 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  807 */         list.add(rs.getString("DISPLAYNAME"));
/*  808 */         list.add(rs.getString("MODTIME"));
/*  809 */         list.add(rs.getString("SOURCE"));
/*  810 */         list.add(rs.getString("TYPE"));
/*  811 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  812 */         list.add(rs.getString("SHORTNAME"));
/*  813 */         list.add(rs.getString("ANNOTATION"));
/*  814 */         list.add(rs.getString("WHO"));
/*  815 */         toreturn.add(list);
/*      */       }
/*  817 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  822 */       e.printStackTrace();
/*      */     }
/*  824 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentExtProdAlerts(int limit, String category, String extProd)
/*      */   {
/*  831 */     String sqlappend = "";
/*  832 */     String alertCategory = "";
/*  833 */     if (category.equalsIgnoreCase("ALL"))
/*      */     {
/*      */ 
/*  836 */       if ((extProd != null) && (extProd.equalsIgnoreCase("OpManager")))
/*      */       {
/*  838 */         sqlappend = " and ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */       }
/*      */     }
/*  841 */     else if ((category != null) && (!category.trim().equalsIgnoreCase("")))
/*      */     {
/*      */ 
/*  844 */       sqlappend = " and AM_ManagedResourceType.RESOURCEGROUP " + category + " and AM_ManagedResourceType.SUBGROUP not like 'OpManager-Interface%' ";
/*      */     }
/*      */     else
/*      */     {
/*  848 */       sqlappend = " and AM_ManagedResourceType.RESOURCEGROUP in ('NWD','SAN') and AM_ManagedResourceType.SUBGROUP not like 'OpManager-Interface%' ";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  856 */     if (category.indexOf("Trap") != -1)
/*      */     {
/*  858 */       alertCategory = "or (Alert.CATEGORY = '3200' and SEVERITY in ('1','4'))";
/*      */     }
/*      */     
/*      */     String query;
/*      */     String query;
/*  863 */     if (limit == 0)
/*      */     {
/*      */       String query;
/*      */       
/*      */ 
/*      */ 
/*  869 */       if ((DBQueryUtil.getDBType().equals("mysql")) || (DBQueryUtil.isPgsql()))
/*      */       {
/*  871 */         query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, 'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on AM_ManagedObject.resourceid =Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  where (GROUPNAME='AppManager') AND SEVERITY IN('1','4') " + sqlappend + " " + alertCategory + " order by MODTIME ";
/*      */       }
/*      */       else
/*      */       {
/*  875 */         query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, 'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on CAST(AM_ManagedObject.resourceid AS char)=Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  where (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap') AND SEVERITY IN('1','4')  " + alertCategory + " " + sqlappend + " order by MODTIME ";
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  880 */       String oldquery = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE,if(AM_ManagedObject.TYPE='Trap','Trap',AM_ManagedResourceType.DISPLAYNAME) as TYPEDISPLAYNAME,if(AM_ManagedObject.TYPE='Trap','Trap',AM_ManagedResourceType.SHORTNAME) as SHORTNAME, if(ANNOTATION.ENTITY,'YES','NO') as ANNOTATION, Alert.WHO from Alert, AM_ManagedObject left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type left outer join ANNOTATION on ANNOTATION.ENTITY=Alert.ENTITY where CAST(AM_ManagedObject.resourceid AS char)=Alert.source AND (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap') AND SEVERITY IN('1','4') group by Alert.ENTITY order by MODTIME desc limit " + limit;
/*      */       
/*      */       String query;
/*  883 */       if ((DBQueryUtil.getDBType().equals("mysql")) || (DBQueryUtil.isPgsql()))
/*      */       {
/*  885 */         query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE,case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, 'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on AM_ManagedObject.resourceid =Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  where (GROUPNAME='AppManager') AND SEVERITY IN('1','4') " + sqlappend + " " + alertCategory + " order by MODTIME desc";
/*      */       }
/*      */       else
/*      */       {
/*  889 */         query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE,case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, 'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on CAST(AM_ManagedObject.resourceid AS char)=Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  where (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap') AND SEVERITY IN('1','4') " + alertCategory + " " + sqlappend + " order by MODTIME desc";
/*      */       }
/*  891 */       query = DBQueryUtil.getTopNValues(query, limit);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  897 */     toreturn = new ArrayList();
/*  898 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  901 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  902 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  903 */       while (rs.next())
/*      */       {
/*  905 */         ArrayList list = new ArrayList();
/*  906 */         list.add(rs.getString("CATEGORY"));
/*  907 */         list.add(rs.getString("SEVERITY"));
/*  908 */         list.add(rs.getString("ENTITY"));
/*  909 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/*  910 */         list.add(rs.getString("DISPLAYNAME"));
/*  911 */         list.add(rs.getString("MODTIME"));
/*  912 */         list.add(rs.getString("SOURCE"));
/*  913 */         list.add(rs.getString("TYPE"));
/*  914 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/*  915 */         String shortname = rs.getString("SHORTNAME");
/*  916 */         if (shortname != null) {
/*  917 */           list.add(shortname);
/*      */         } else {
/*  919 */           list.add("");
/*      */         }
/*  921 */         list.add(rs.getString("ANNOTATION"));
/*  922 */         list.add(rs.getString("WHO"));
/*  923 */         toreturn.add(list);
/*      */       }
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
/*  944 */       return toreturn;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  928 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  934 */         if (rs != null)
/*      */         {
/*  936 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentExtProdAlertsForOwner(HttpServletRequest request, int limit, String order, HashMap siteAndCustomerMap, String associatedTypeVals, String extProd)
/*      */   {
/*  950 */     Vector resourceids = null;
/*  951 */     String criteria = "";
/*      */     
/*  953 */     if (associatedTypeVals.equalsIgnoreCase("ALL"))
/*      */     {
/*  955 */       if (extProd == null)
/*      */       {
/*  957 */         criteria = "";
/*      */       }
/*  959 */       if (extProd.equalsIgnoreCase("OpManager"))
/*      */       {
/*  961 */         criteria = " ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */       }
/*      */       
/*      */     }
/*  965 */     else if ((associatedTypeVals != null) && (!associatedTypeVals.trim().equalsIgnoreCase("")))
/*      */     {
/*  967 */       criteria = " ExternalDeviceDetails.CATEGORY " + associatedTypeVals + " ";
/*      */     }
/*      */     else
/*      */     {
/*  971 */       criteria = " ExternalDeviceDetails.CATEGORY in ('NWD','SAN') and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */     }
/*      */     
/*  974 */     boolean isUserResourceEnabled = false;
/*  975 */     String loginUserid = null;
/*  976 */     if ((EnterpriseUtil.isIt360MSPEdition) && (!siteAndCustomerMap.isEmpty()))
/*      */     {
/*  978 */       resourceids = CustomerManagementAPI.filterResourceIds(siteAndCustomerMap);
/*      */ 
/*      */ 
/*      */     }
/*  982 */     else if (Constants.isUserResourceEnabled()) {
/*  983 */       isUserResourceEnabled = true;
/*  984 */       loginUserid = Constants.getLoginUserid(request);
/*      */     } else {
/*  986 */       resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*      */     }
/*      */     
/*      */ 
/*  990 */     String query = null;
/*  991 */     if (isUserResourceEnabled) {
/*  992 */       query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from AM_USERRESOURCESTABLE join Alert on AM_USERRESOURCESTABLE.RESOURCEID=Alert.SOURCE and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  WHERE GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') AND " + criteria + " order by MODTIME " + order;
/*      */     } else {
/*  994 */       query = "select Alert.CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.type,AM_ManagedResourceType.DISPLAYNAME as TYPEDISPLAYNAME,AM_ManagedResourceType.SHORTNAME,'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on  AM_ManagedObject.resourceid=Alert.source join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID  WHERE   " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "' AND SEVERITY IN('1','4') AND " + criteria + " order by MODTIME " + order;
/*      */     }
/*      */     
/*  997 */     query = DBQueryUtil.getTopNValues(query, limit);
/*      */     
/*      */ 
/* 1000 */     toreturn = new ArrayList();
/* 1001 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1004 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1005 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1006 */       while (rs.next())
/*      */       {
/* 1008 */         ArrayList list = new ArrayList();
/* 1009 */         list.add(rs.getString("CATEGORY"));
/* 1010 */         list.add(rs.getString("SEVERITY"));
/* 1011 */         list.add(rs.getString("ENTITY"));
/* 1012 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/* 1013 */         list.add(rs.getString("DISPLAYNAME"));
/* 1014 */         list.add(rs.getString("MODTIME"));
/* 1015 */         list.add(rs.getString("SOURCE"));
/* 1016 */         list.add(rs.getString("TYPE"));
/* 1017 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/* 1018 */         list.add(rs.getString("SHORTNAME"));
/* 1019 */         list.add(rs.getString("ANNOTATION"));
/* 1020 */         list.add(rs.getString("WHO"));
/* 1021 */         toreturn.add(list);
/*      */       }
/* 1023 */       rs.close();
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
/* 1044 */       return toreturn;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1028 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1034 */         if (rs != null)
/*      */         {
/* 1036 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecent5Alerts()
/*      */   {
/* 1052 */     return getRecentAlerts(5);
/*      */   }
/*      */   
/*      */   public static ArrayList getRecentTenAlerts()
/*      */   {
/* 1057 */     return getRecentAlerts(10);
/*      */   }
/*      */   
/*      */   public static ArrayList getRecentAlerts(int limit)
/*      */   {
/* 1062 */     return getRecentAlerts(limit, "ALL");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getRecentAlerts(int limit, String category)
/*      */   {
/* 1073 */     String sqlappend = "";
/* 1074 */     String alertCategory = "";
/* 1075 */     if (category.equalsIgnoreCase("ALL"))
/*      */     {
/* 1077 */       category = " NOT IN ('NET')";
/* 1078 */       alertCategory = "or (CATEGORY = '3200' and SEVERITY in ('1','4'))";
/*      */     }
/* 1080 */     if (Constants.sqlManager)
/*      */     {
/* 1082 */       category = "IN ('DBS','HAI')";
/* 1083 */       sqlappend = " AND AM_ManagedObject.TYPE in ('MSSQL-DB-server','HAI') ";
/*      */     }
/*      */     
/* 1086 */     if (category.indexOf("Trap") != -1)
/*      */     {
/* 1088 */       alertCategory = "or (CATEGORY = '3200' and SEVERITY in ('1','4'))";
/*      */     }
/*      */     
/*      */     String query;
/*      */     
/*      */     String query;
/* 1094 */     if ((DBQueryUtil.isPgsql()) || (DBQueryUtil.isMysql()))
/*      */     {
/* 1096 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, case when anno.ENTITY is null then 'NO' else 'YES' end as ANNOTATION, Alert.WHO from Alert left join (select DISTINCT(ENTITY) from ANNOTATION) anno on anno.ENTITY=Alert.ENTITY join AM_ManagedObject on AM_ManagedObject.resourceid =Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type where (GROUPNAME='AppManager') AND SEVERITY IN('1','4') and AM_ManagedResourceType.RESOURCEGROUP " + category + Constants.excludeIntf + " " + alertCategory + " order by MODTIME desc";
/*      */     }
/*      */     else
/*      */     {
/* 1100 */       query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, case when anno.ENTITY is null then 'NO' else 'YES' end as ANNOTATION, Alert.WHO from Alert left join (select DISTINCT(ENTITY) from ANNOTATION) anno on anno.ENTITY=Alert.ENTITY join AM_ManagedObject on CAST(AM_ManagedObject.resourceid AS char)=Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  where (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap') AND SEVERITY IN('1','4') AND AM_ManagedResourceType.RESOURCEGROUP " + category + Constants.excludeIntf + " " + alertCategory + " " + sqlappend + " order by MODTIME desc";
/*      */     }
/*      */     
/* 1103 */     if (limit > 0)
/*      */     {
/* 1105 */       query = DBQueryUtil.getTopNValues(query, limit);
/* 1106 */       AMLog.debug("##DEBUG## Query after appending limit condition::" + query);
/*      */     }
/* 1108 */     toreturn = new ArrayList();
/* 1109 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1112 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1113 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1114 */       while (rs.next())
/*      */       {
/* 1116 */         ArrayList list = new ArrayList();
/* 1117 */         list.add(rs.getString("CATEGORY"));
/* 1118 */         list.add(rs.getString("SEVERITY"));
/* 1119 */         list.add(rs.getString("ENTITY"));
/* 1120 */         list.add(FormatUtil.findReplace(rs.getString("MMESSAGE"), "'", "\\'"));
/* 1121 */         list.add(rs.getString("DISPLAYNAME"));
/* 1122 */         list.add(rs.getString("MODTIME"));
/* 1123 */         list.add(rs.getString("SOURCE"));
/* 1124 */         list.add(rs.getString("TYPE"));
/* 1125 */         list.add(rs.getString("TYPEDISPLAYNAME"));
/* 1126 */         String shortname = rs.getString("SHORTNAME");
/* 1127 */         if (shortname != null) {
/* 1128 */           list.add(shortname);
/*      */         } else {
/* 1130 */           list.add("");
/*      */         }
/* 1132 */         list.add(rs.getString("ANNOTATION"));
/* 1133 */         list.add(rs.getString("WHO"));
/* 1134 */         toreturn.add(list);
/*      */       }
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
/* 1155 */       return toreturn;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1139 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1145 */         if (rs != null)
/*      */         {
/* 1147 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getThresholdDetailsForAttribute(String attributeid, String resourceid)
/*      */   {
/* 1160 */     ManagedApplication mo = new ManagedApplication();
/* 1161 */     ArrayList list = mo.getRows("select NAME , DESCRIPTION , CRITICALTHRESHOLDCONDITION , CRITICALTHRESHOLDVALUE , WARNINGTHRESHOLDCONDITION , WARNINGTHRESHOLDVALUE ,INFOTHRESHOLDCONDITION , INFOTHRESHOLDVALUE from AM_THRESHOLDCONFIG , AM_ATTRIBUTETHRESHOLDMAPPER where AM_THRESHOLDCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=" + attributeid + " and AM_ATTRIBUTETHRESHOLDMAPPER.ID=" + resourceid + "");
/* 1162 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getActionDetailsForAttribute(String attributeid, String resourceid)
/*      */   {
/* 1167 */     ManagedApplication mo = new ManagedApplication();
/* 1168 */     ArrayList list = mo.getRows("select AM_ATTRIBUTEACTIONMAPPER.SEVERITY , AM_ACTIONPROFILE.NAME , case AM_ACTIONPROFILE.TYPE when '1' THEN 'EMail' when '2' THEN 'SMS' when '3' THEN 'Execute Command' when '11' THEN 'SNMP V1' when '12' THEN 'SNMP V2c' END , AM_ACTIONPROFILE.ID  from AM_ACTIONPROFILE , AM_ATTRIBUTEACTIONMAPPER where AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=" + attributeid + " and AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceid + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID");
/* 1169 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void dumpHAResourceId(long timestamp)
/*      */   {
/* 1178 */     ManagedApplication mo = new ManagedApplication();
/*      */     
/* 1180 */     String tempquery = "insert into AM_Temp_HA_ResourceMapper select " + timestamp + ",parentid,childid from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION WHERE PARENTID=haid";
/*      */     
/* 1182 */     String selectquery = "select childid,parentid from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION WHERE PARENTID=haid";
/* 1183 */     mo.executeUpdateStmt(tempquery);
/*      */     
/* 1185 */     tempquery = "insert into AM_Temp_HA_ResourceMapper select " + timestamp + ",haid,haid from AM_HOLISTICAPPLICATION";
/* 1186 */     mo.executeUpdateStmt(tempquery);
/* 1187 */     ArrayList childs = mo.getRows(selectquery);
/* 1188 */     if (childs.size() == 0)
/*      */     {
/* 1190 */       return;
/*      */     }
/*      */     
/* 1193 */     Hashtable table = new Hashtable();
/* 1194 */     for (int i = 0; i < childs.size(); i++)
/*      */     {
/* 1196 */       ArrayList rows = (ArrayList)childs.get(i);
/* 1197 */       String chidid = (String)rows.get(0);
/* 1198 */       String haid = (String)rows.get(1);
/* 1199 */       ArrayList children = (ArrayList)table.get(haid);
/* 1200 */       if (children == null)
/*      */       {
/* 1202 */         children = new ArrayList();
/* 1203 */         table.put(haid, children);
/*      */       }
/* 1205 */       children.add(chidid);
/*      */     }
/* 1207 */     Enumeration enume = table.keys();
/* 1208 */     while (enume.hasMoreElements())
/*      */     {
/* 1210 */       String key = (String)enume.nextElement();
/* 1211 */       ArrayList children = (ArrayList)table.get(key);
/* 1212 */       if (children.size() != 0)
/*      */       {
/*      */ 
/*      */ 
/* 1216 */         dumpHAResourceId(timestamp, key, convertListToBuffer(children));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static String convertListToBuffer(ArrayList list)
/*      */   {
/* 1224 */     StringBuffer children = new StringBuffer();
/* 1225 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*      */ 
/* 1228 */       String chidid = (String)list.get(i);
/* 1229 */       children.append(chidid);
/* 1230 */       if (i != list.size() - 1)
/*      */       {
/* 1232 */         children.append(",");
/*      */       }
/*      */     }
/* 1235 */     return children.toString();
/*      */   }
/*      */   
/*      */   public static void dumpHAResourceId(long timestamp, String haid, String parents)
/*      */   {
/* 1240 */     ManagedApplication mo = new ManagedApplication();
/* 1241 */     String tempquery = "insert into AM_Temp_HA_ResourceMapper select " + timestamp + "," + haid + ",childid from AM_PARENTCHILDMAPPER WHERE PARENTID IN (" + parents + ")";
/* 1242 */     String selectquery = "select childid," + haid + " from AM_PARENTCHILDMAPPER WHERE PARENTID IN (" + parents + ")";
/*      */     
/*      */ 
/* 1245 */     mo.executeUpdateStmt(tempquery);
/* 1246 */     ArrayList childs = mo.getRows(selectquery);
/* 1247 */     if (childs.size() == 0)
/*      */     {
/* 1249 */       return;
/*      */     }
/*      */     
/* 1252 */     StringTokenizer st = new StringTokenizer(parents, ",");
/* 1253 */     String tmp = "";
/* 1254 */     List allParents = new ArrayList();
/* 1255 */     while (st.hasMoreTokens()) {
/* 1256 */       tmp = st.nextToken();
/* 1257 */       allParents.add(tmp);
/*      */     }
/*      */     
/* 1260 */     StringBuffer children = new StringBuffer();
/* 1261 */     for (int i = 0; i < childs.size(); i++)
/*      */     {
/* 1263 */       ArrayList rows = (ArrayList)childs.get(i);
/* 1264 */       String chidid = (String)rows.get(0);
/*      */       
/* 1266 */       if (!allParents.contains(chidid)) {
/* 1267 */         if (children.length() == 0) {
/* 1268 */           children.append(chidid);
/*      */         } else {
/* 1270 */           children.append(",");
/* 1271 */           children.append(chidid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1279 */     dumpHAResourceId(timestamp, haid, children.toString());
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getAlertsCountForResource(String resourceid)
/*      */   {
/* 1285 */     ManagedApplication mo = new ManagedApplication();
/* 1286 */     Vector resourceids = DependantMOUtil.getDependantResourceIDS(String.valueOf(resourceid));
/* 1287 */     String query = "select count(*)  from Alert ,AM_ManagedObject WHERE AM_ManagedObject.resourceid=Alert.source  AND " + DependantMOUtil.getCondition("SOURCE", resourceids) + " AND GROUPNAME='" + "AppManager" + "'";
/* 1288 */     ArrayList rows = mo.getRows(query);
/* 1289 */     if (rows.size() > 0)
/*      */     {
/* 1291 */       rows = (ArrayList)rows.get(0);
/* 1292 */       return (String)rows.get(0);
/*      */     }
/*      */     
/* 1295 */     return "0";
/*      */   }
/*      */   
/*      */   public static ArrayList getCustomerNames()
/*      */   {
/* 1300 */     ManagedApplication mo = new ManagedApplication();
/* 1301 */     ArrayList list = mo.getRows("select CUSTOMERNAME,CUSTOMERID FROM CUSTOMERINFO");
/* 1302 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getSiteMonitorGroups(String customerID)
/*      */   {
/* 1308 */     ManagedApplication mo = new ManagedApplication();
/* 1309 */     if (customerID.equals("-"))
/*      */     {
/* 1311 */       customerID = "-1";
/*      */     }
/* 1313 */     ArrayList list = mo.getRows("select SITENAME,SITEID FROM SITEINFO where CUSTOMERID='" + customerID + "'");
/* 1314 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getSiteMonitorGroups()
/*      */   {
/* 1319 */     ManagedApplication mo = new ManagedApplication();
/* 1320 */     ArrayList list = mo.getRows("select SITENAME,SITEID,CUSTOMERID FROM SITEINFO");
/* 1321 */     return list;
/*      */   }
/*      */   
/*      */   public static ArrayList getApplicationsForAdmin() {
/* 1325 */     return getApplicationsForAdmin(null);
/*      */   }
/*      */   
/*      */   public static ArrayList getApplicationsForAdmin(HttpServletRequest request)
/*      */   {
/* 1330 */     ArrayList ResultList = new ArrayList();
/* 1331 */     ArrayList list = new ArrayList();
/* 1332 */     if (Constants.subGroupsEnabled.equals("true"))
/*      */     {
/* 1334 */       ManagedApplication mo = new ManagedApplication();
/* 1335 */       String bsgFilterCondn = "";
/* 1336 */       if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 1338 */         Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 1339 */         bsgFilterCondn = " WHERE RESOURCEID is not null and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*      */       } else {
/* 1341 */         bsgFilterCondn = " WHERE RESOURCEID is not null";
/*      */       }
/* 1343 */       list = mo.getRows("select DISPLAYNAME,RESOURCEID,PARENTID,RESOURCENAME from AM_HOLISTICAPPLICATION join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID " + bsgFilterCondn + " order by PARENTID,RESOURCENAME");
/* 1344 */       ArrayList groupsList = mo.getRows("select DISPLAYNAME,RESOURCEID,PARENTID,DISPLAYNAME,RESOURCENAME from AM_HOLISTICAPPLICATION join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID,RESOURCENAME");
/* 1345 */       for (int i = 0; i < list.size(); i++)
/*      */       {
/* 1347 */         ArrayList tempList = new ArrayList();
/* 1348 */         ArrayList InsideList = (ArrayList)list.get(i);
/* 1349 */         tempList.add((String)InsideList.get(0));
/* 1350 */         tempList.add((String)InsideList.get(1));
/* 1351 */         boolean addToList = true;
/* 1352 */         for (int k = 0; k < ResultList.size(); k++) {
/* 1353 */           String resultListresid = (String)((ArrayList)ResultList.get(k)).get(1);
/* 1354 */           int resultListStrLen = ((ArrayList)ResultList.get(k)).get(0).toString().length();
/* 1355 */           String tempListresid = (String)tempList.get(1);
/* 1356 */           int tempListStrLen = tempList.get(0).toString().length();
/* 1357 */           if (resultListresid.equals(tempListresid)) {
/* 1358 */             if (tempListStrLen > resultListStrLen) {
/* 1359 */               addToList = true;
/* 1360 */               ResultList.remove(k);
/*      */             }
/*      */             else {
/* 1363 */               addToList = false;
/*      */             }
/*      */           }
/*      */         }
/* 1367 */         if (addToList) {
/* 1368 */           ResultList.add(tempList);
/*      */         }
/* 1370 */         ReportUtil.getSortedList((String)InsideList.get(1), groupsList, ResultList, "", "--");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1375 */     else if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 1377 */       ResultList = getApplications(request);
/*      */     }
/*      */     else
/*      */     {
/* 1381 */       ResultList = getApplications();
/*      */     }
/*      */     
/* 1384 */     return ResultList;
/*      */   }
/*      */   
/*      */   public static void updateAdminPickupStatus(String username, String tempentity) {
/* 1388 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/* 1391 */         String resID = tempentity.substring(0, tempentity.indexOf("_"));
/* 1392 */         int resIDInt = Integer.parseInt(resID);
/* 1393 */         if (resIDInt > EnterpriseUtil.RANGE) {
/*      */           try {
/* 1395 */             AMConnectionPool.executeUpdateStmt("update Alert set WHO='" + username + "' where ENTITY='" + tempentity + "'");
/*      */           } catch (Exception ex) {
/* 1397 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/* 1401 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getParentId(String categoryid) {
/* 1407 */     String query = "select PARENTID from AM_PARENTCHILDMAPPER where CHILDID=" + categoryid;
/* 1408 */     String parentId = CustomerManagementAPI.getSingleStringValueFromDB(query);
/* 1409 */     return parentId;
/*      */   }
/*      */   
/*      */   public String getCustomerName(String categoryId) {
/* 1413 */     String query = "";
/* 1414 */     String customerName = "";
/* 1415 */     int maxLevels = getMaxBSGLevels();
/* 1416 */     int i = 0;
/* 1417 */     while (((customerName == null) || (customerName.equals(""))) && (i < maxLevels)) {
/* 1418 */       categoryId = getParentId(categoryId);
/* 1419 */       query = "select CUSTOMERNAME from customerinfo where CUSTOMERID = " + categoryId;
/* 1420 */       customerName = CustomerManagementAPI.getSingleStringValueFromDB(query);
/* 1421 */       i++;
/*      */     }
/*      */     
/* 1424 */     return customerName;
/*      */   }
/*      */   
/*      */   private int getMaxBSGLevels() {
/* 1428 */     maxLevels = 10;
/* 1429 */     Properties props = new Properties();
/* 1430 */     FileInputStream FI = null;
/*      */     try {
/* 1432 */       File bsgProps = new File(BSGPROPSFILE);
/* 1433 */       if (bsgProps.exists())
/*      */       {
/* 1435 */         FI = new FileInputStream(bsgProps);
/* 1436 */         props.load(FI); }
/* 1437 */       return Integer.parseInt(props.getProperty("max.bsg.levels"));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1441 */       AMLog.fatal("#--# Exception while reading BSG.properties:" + e.getMessage());
/*      */     }
/*      */     finally {
/* 1444 */       if (FI != null) {
/*      */         try {
/* 1446 */           FI.close();
/*      */         } catch (Exception e) {
/* 1448 */           AMLog.fatal("#--# Exception while closing FI inputstream:" + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1454 */   private static final String BSGPROPSFILE = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + "conf" + File.separator + "BSG.properties";
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\AlarmUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */