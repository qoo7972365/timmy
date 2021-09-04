/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DowntimeScheduleUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchAction
/*     */   extends DispatchAction
/*     */ {
/*  42 */   private String defaultSearchOption = "displayname,monitortype,ipaddress,customfields,all";
/*  43 */   public static boolean isCentralRunning = false;
/*     */   
/*     */ 
/*     */ 
/*  47 */   private static HashMap<String, String> defaultTemplateValues = new HashMap();
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  52 */     String qry = null;
/*     */     
/*  54 */     request.setAttribute("searchRequest", "true");
/*     */     
/*  56 */     String old = request.getParameter("old");
/*  57 */     if (old == null) { old = "false";
/*     */     }
/*  59 */     if (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true"))
/*     */     {
/*  61 */       RequestDispatcher dispatcher = request.getRequestDispatcher("/EnterpriseSearch");
/*  62 */       return mapping.findForward("EnterpriseSearch");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */     String searchCondition = "";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  94 */     ActionMessages messages = new ActionMessages();
/*  95 */     ActionErrors errors = new ActionErrors();
/*  96 */     StringBuilder tempsearch = new StringBuilder();
/*  97 */     String searchQ = "";
/*     */     try
/*     */     {
/* 100 */       if (request.getParameter("query") != null)
/*     */       {
/* 102 */         searchQ = request.getParameter("query").trim();
/*     */       }
/*     */       
/* 105 */       if (searchQ == null)
/*     */       {
/* 107 */         searchQ = "";
/*     */       }
/* 109 */       searchCondition = request.getParameter("searchOptionValue");
/* 110 */       Cookie[] cookies = request.getCookies();
/* 111 */       for (int i = 0; i < cookies.length; i++) {
/* 112 */         if ((cookies[i].getName().equals("searchOption")) && (cookies[i].getValue() != null)) {
/* 113 */           searchCondition = cookies[i].getValue();
/*     */         }
/*     */       }
/*     */       
/* 117 */       DataCollectionControllerUtil DCCU = new DataCollectionControllerUtil();
/* 118 */       ArrayList list = new ArrayList();
/* 119 */       String url = "";
/* 120 */       if (request.getParameter("url") != null)
/*     */       {
/* 122 */         url = request.getParameter("url");
/*     */       }
/*     */       
/* 125 */       if (url.indexOf("MaintenanceTaskListView") != -1)
/*     */       {
/* 127 */         if ((searchQ.equalsIgnoreCase(FormatUtil.getString("am.webclient.downtimescheduler.idle.txt"))) || (searchQ.equalsIgnoreCase(FormatUtil.getString("am.webclient.downtimescheduler.running.txt"))) || (searchQ.equalsIgnoreCase(FormatUtil.getString("am.webclient.downtimescheduler.expired.txt"))) || (searchQ.equalsIgnoreCase(FormatUtil.getString("am.webclient.downtimescheduler.nomonitor.txt"))))
/*     */         {
/* 129 */           list = DataCollectionControllerUtil.getDowntimeList("");
/* 130 */           if (request.isUserInRole("OPERATOR"))
/*     */           {
/* 132 */             list = DowntimeScheduleUtil.getTaskListofUser(request.getRemoteUser(), list, "");
/*     */           }
/*     */           
/* 135 */           request.setAttribute("list", list);
/* 136 */           return new ActionForward("/jsp/MaintenanceTaskListView.jsp?searchQ=" + searchQ);
/*     */         }
/*     */         
/* 139 */         list = DataCollectionControllerUtil.getDowntimeList(searchQ);
/* 140 */         if (request.isUserInRole("OPERATOR"))
/*     */         {
/* 142 */           list = DowntimeScheduleUtil.getTaskListofUser(request.getRemoteUser(), list, searchQ);
/*     */         }
/*     */         
/* 145 */         request.setAttribute("list", list);
/*     */         
/* 147 */         return new ActionForward("/jsp/MaintenanceTaskListView.jsp");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */       ResultSet rs = null;
/*     */       
/*     */       try
/*     */       {
/* 161 */         qry = searchQuery(request, searchQ, searchCondition, "", false);
/* 162 */         rs = AMConnectionPool.executeQueryStmt(qry);
/* 163 */         while (rs.next())
/*     */         {
/* 165 */           if (tempsearch.indexOf(rs.getString("RESOURCEID")) == -1)
/*     */           {
/* 167 */             tempsearch.append(rs.getString(1)).append(",");
/*     */           }
/*     */         }
/* 170 */         tempsearch.append("-1");
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 175 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 179 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 185 */       String tempsearchKeys = null;
/* 186 */       String isFromEnterpriseSearch = request.getParameter("isFromEnterpriseSearch");
/* 187 */       if ((isFromEnterpriseSearch != null) && (isFromEnterpriseSearch.equalsIgnoreCase("true")))
/*     */       {
/* 189 */         tempsearchKeys = request.getParameter("tempsearch");
/* 190 */         request.setAttribute("isFromEnterpriseSearch", "true");
/*     */       }
/*     */       else
/*     */       {
/* 194 */         tempsearchKeys = tempsearch.toString();
/*     */       }
/* 196 */       request.setAttribute("searchresourceids", tempsearchKeys);
/*     */       
/* 198 */       request.setAttribute("searchString", searchQ);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Throwable ee)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 247 */       ee.printStackTrace();
/* 248 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while searching. Ignoring and continuing."));
/*     */     }
/*     */     
/* 251 */     if (!errors.isEmpty()) {
/* 252 */       saveErrors(request, errors);
/*     */     }
/*     */     
/*     */ 
/* 256 */     if (Constants.sqlManager)
/*     */     {
/* 258 */       return new ActionForward("/showresource.do?method=showResourceMSSQL&group=All&search=true");
/*     */     }
/* 260 */     return new ActionForward("/showresource.do?method=showResourceTypesAll&group=All&search=true");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String searchQuery(HttpServletRequest request, String searchQ, String searchCondition, String notinCondition, boolean isRestApi)
/*     */   {
/* 356 */     if (Constants.isIt360)
/*     */     {
/* 358 */       Vector resourceids = null;
/* 359 */       String userCondition = "";
/* 360 */       String filterCustomerSite = " and AM_ManagedObject.RESOURCEID not in (select CUSTOMERID from CustomerInfo) and AM_ManagedObject.RESOURCEID not in (select SITEID from SiteInfo) ";
/* 361 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 363 */         resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 364 */         userCondition = " and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceids);
/*     */       }
/*     */       
/* 367 */       return "select RESOURCEID from AM_ManagedObject, CollectData where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and (CollectData.TARGETADDRESS like '%" + searchQ + "%' or CollectData.DISPLAYNAME like '%" + searchQ + "%'  or CollectData.RESOURCETYPE like '%" + searchQ + "%' or AM_ManagedObject.DISPLAYNAME like '%" + searchQ + "%') " + userCondition + filterCustomerSite + " union select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where (AM_ManagedObject.TYPE like '%" + searchQ + "%' or AM_ManagedObject.RESOURCENAME like '%" + searchQ + "%' or AM_ManagedObject.DISPLAYNAME like '%" + searchQ + "%') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + userCondition + filterCustomerSite + " and (AM_ManagedResourceType.RESOURCETYPE NOT LIKE 'OpManager-Interface%' and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','Network')) union select RESOURCEID from AM_ManagedObject,ExternalDeviceDetails where ExternalDeviceDetails.IPADDRESS  like '%" + searchQ + "%' and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME" + userCondition + filterCustomerSite + " and AM_ManagedObject.TYPE not like 'OpManager-Interface%'";
/*     */     }
/* 369 */     Vector resourceids = null;
/* 370 */     String userCondition = "";
/* 371 */     if (Constants.isPrivilegedUser(request)) {
/* 372 */       if (Constants.isUserResourceEnabled()) {
/* 373 */         String userid = Constants.getLoginUserid(request);
/* 374 */         userCondition = " and AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + userid + ")";
/*     */       } else {
/* 376 */         resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 377 */         userCondition = " and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceids);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 382 */     if ((searchCondition == null) || ("null".equalsIgnoreCase(searchCondition)) || (searchCondition.trim().length() == 0)) {
/* 383 */       searchCondition = "displayname";
/* 384 */       if (isRestApi) {
/* 385 */         searchCondition = "all";
/*     */       }
/*     */     }
/* 388 */     if ((isRestApi) && (!this.defaultSearchOption.contains(searchCondition.toLowerCase()))) {
/* 389 */       searchCondition = "all";
/*     */     }
/*     */     
/* 392 */     String types = Constants.resourceTypes;
/*     */     
/* 394 */     if (searchQ.equalsIgnoreCase("critical"))
/*     */     {
/* 396 */       return "select AM_ManagedObject.RESOURCEID from Alert, AM_ManagedObject where Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY=1 " + userCondition + " and AM_ManagedObject.TYPE in " + types;
/*     */     }
/* 398 */     if (searchQ.equalsIgnoreCase("warning"))
/*     */     {
/* 400 */       return "select AM_ManagedObject.RESOURCEID from Alert, AM_ManagedObject where Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY=4 " + userCondition + " and AM_ManagedObject.TYPE in " + types;
/*     */     }
/* 402 */     if (searchQ.equalsIgnoreCase("clear"))
/*     */     {
/* 404 */       return "select AM_ManagedObject.RESOURCEID from Alert, AM_ManagedObject where Alert.SOURCE=AM_ManagedObject.RESOURCEID and Alert.SEVERITY=5 " + userCondition + " and AM_ManagedObject.TYPE in " + types;
/*     */     }
/*     */     
/*     */ 
/* 408 */     StringBuilder query = new StringBuilder();
/*     */     
/* 410 */     String likeString = "like";
/*     */     
/* 412 */     if (DBQueryUtil.isPgsql()) {
/* 413 */       likeString = "ilike";
/*     */     }
/*     */     
/* 416 */     if ("all".equalsIgnoreCase(searchCondition))
/*     */     {
/* 418 */       String resNameCondition = getResNameCondition(searchQ, likeString);
/* 419 */       query.append("select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where (AM_ManagedObject.DISPLAYNAME " + likeString + " '%" + searchQ + "%' or AM_ManagedObject.TYPE " + likeString + " '%" + searchQ + "%' or " + resNameCondition + ") and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.TYPE <> 'Network'" + notinCondition + userCondition);
/* 420 */       if (!Constants.sqlManager) {
/* 421 */         query.append(" union select RESOURCEID from AM_ManagedObject,CollectData where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and (CollectData.TARGETADDRESS " + likeString + " '%" + searchQ + "%' or CollectData.DISPLAYNAME " + likeString + " '%" + searchQ + "%' ) " + userCondition);
/* 422 */         query.append(" union select RESOURCEID from AM_ManagedObject,InetService,AM_ManagedResourceType where AM_ManagedObject.RESOURCENAME=InetService.NAME and (InetService.TARGETADDRESS " + likeString + " '%" + searchQ + "%' or InetService.TARGETNAME " + likeString + " '%" + searchQ + "%' ) and InetService.NAME in (select InetService.NAME from InetService left outer join CollectData on InetService.NAME=CollectData.RESOURCENAME where CollectData.RESOURCENAME is null) " + notinCondition + userCondition);
/* 423 */         query.append(" union select RESOURCEID from AM_CONFIGURATION_INFO where LATEST=1 and ATTRIBUTEID in (7615,15538,15017,9819,9820) and CONFVALUE " + likeString + " '%" + searchQ + "%'");
/*     */       } else {
/* 425 */         query.append(" union select RESOURCEID from AM_ManagedObject,CollectData,AM_ManagedResourceType where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and (CollectData.TARGETADDRESS " + likeString + " '%" + searchQ + "%' or CollectData.DISPLAYNAME " + likeString + " '%" + searchQ + "%' ) " + notinCondition + userCondition);
/*     */       }
/*     */     }
/* 428 */     else if ("displayname".equalsIgnoreCase(searchCondition))
/*     */     {
/* 430 */       query.append("select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.DISPLAYNAME " + likeString + " '%" + searchQ + "%' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE " + notinCondition + userCondition);
/*     */     }
/* 432 */     else if ("monitortype".equalsIgnoreCase(searchCondition))
/*     */     {
/* 434 */       query.append(" select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND  AM_ManagedObject.TYPE " + likeString + " '%" + searchQ + "%' " + notinCondition + userCondition);
/*     */     }
/* 436 */     else if ("ipaddress".equalsIgnoreCase(searchCondition)) {
/* 437 */       if (!Constants.sqlManager)
/*     */       {
/* 439 */         String resNameCondition = getResNameCondition(searchQ, likeString);
/* 440 */         query.append(" select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where (" + resNameCondition + ") and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.TYPE <> 'Network'" + notinCondition + userCondition);
/* 441 */         query.append(" union select RESOURCEID from AM_ManagedObject,CollectData where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and CollectData.TARGETADDRESS " + likeString + " '%" + searchQ + "%' " + userCondition);
/* 442 */         query.append(" union select RESOURCEID from AM_ManagedObject,InetService,AM_ManagedResourceType where AM_ManagedObject.RESOURCENAME=InetService.NAME and (InetService.TARGETADDRESS " + likeString + " '%" + searchQ + "%' or InetService.TARGETNAME " + likeString + " '%" + searchQ + "%' ) and InetService.NAME in (select InetService.NAME from InetService left outer join CollectData on InetService.NAME=CollectData.RESOURCENAME where CollectData.RESOURCENAME is null) " + notinCondition + userCondition);
/* 443 */         query.append(" union select RESOURCEID from AM_CONFIGURATION_INFO where LATEST=1 and ATTRIBUTEID in (7615,15538,15017,9819,9820) and CONFVALUE " + likeString + " '%" + searchQ + "%'");
/*     */       }
/*     */       else {
/* 446 */         query.append(" select RESOURCEID from AM_ManagedObject,CollectData,AM_ManagedResourceType where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and CollectData.TARGETADDRESS " + likeString + " '%" + searchQ + "%' " + notinCondition + userCondition);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 454 */     if ((searchCondition.equalsIgnoreCase("customfields")) || (searchCondition.equalsIgnoreCase("all")))
/*     */     {
/* 456 */       StringBuilder systemDataListColumn = new StringBuilder();
/* 457 */       StringBuilder systemDataTextColumn = new StringBuilder();
/* 458 */       StringBuilder userdataListColumn = new StringBuilder();
/* 459 */       StringBuilder userdataTextColumn = new StringBuilder();
/*     */       
/* 461 */       ResultSet rs = null;
/*     */       try {
/* 463 */         rs = AMConnectionPool.executeQueryStmt("select  ALIASNAME, DATATABLE, FIELDTYPE from AM_MYFIELDS_METADATA where DATATABLE in ('AM_MYFIELDS_SYSTEMDATA','AM_MYFIELDS_USERDATA') and ENABLED = 1");
/* 464 */         while (rs.next())
/*     */         {
/* 466 */           String dataTable = rs.getString("DATATABLE");
/* 467 */           int fieldType = rs.getInt("FIELDTYPE");
/* 468 */           if (dataTable.equals("AM_MYFIELDS_SYSTEMDATA"))
/*     */           {
/* 470 */             if (fieldType == 2) {
/* 471 */               systemDataListColumn.append("AM_MYFIELDS_TEMPLATEDATA.VALUEID =" + dataTable + "." + rs.getString("ALIASNAME") + " or ");
/*     */             } else {
/* 473 */               systemDataTextColumn.append(dataTable + "." + rs.getString("ALIASNAME") + " " + likeString + " '%" + searchQ + "%' or ");
/*     */             }
/*     */           }
/* 476 */           else if (fieldType == 2) {
/* 477 */             userdataListColumn.append("AM_MYFIELDS_TEMPLATEDATA.VALUEID =" + dataTable + "." + rs.getString("ALIASNAME") + " or ");
/*     */           } else {
/* 479 */             userdataTextColumn.append(dataTable + "." + rs.getString("ALIASNAME") + " " + likeString + " '%" + searchQ + "%' or ");
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 490 */         if (defaultTemplateValues.isEmpty()) {
/* 491 */           rs = AMConnectionPool.executeQueryStmt("select VALUE from AM_MYFIELDS_TEMPLATEDATA where VALUEID < 9");
/* 492 */           while (rs.next()) {
/* 493 */             String keyvalue = rs.getString("VALUE");
/* 494 */             String formatedString = FormatUtil.getString(keyvalue);
/* 495 */             defaultTemplateValues.put(formatedString, keyvalue);
/*     */           }
/*     */         }
/* 498 */         String dropdownvalue = null;
/* 499 */         String keysearchString = "";
/* 500 */         Iterator<String> ite = defaultTemplateValues.keySet().iterator();
/* 501 */         while (ite.hasNext()) {
/* 502 */           String value = (String)ite.next();
/*     */           
/* 504 */           if (value.toLowerCase().contains(searchQ.toLowerCase())) {
/* 505 */             dropdownvalue = (String)defaultTemplateValues.get(value);
/* 506 */             break;
/*     */           }
/*     */         }
/*     */         
/* 510 */         if (dropdownvalue != null) {
/* 511 */           keysearchString = "AM_MYFIELDS_TEMPLATEDATA.VALUE " + likeString + " '%" + dropdownvalue + "%' or ";
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 517 */         if (systemDataListColumn.length() > 0) {
/* 518 */           systemDataListColumn.delete(systemDataListColumn.length() - 4, systemDataListColumn.length());
/* 519 */           query.append(" union select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_MYFIELDS_SYSTEMDATA,AM_MYFIELDS_TEMPLATEDATA where AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and (" + systemDataListColumn.toString() + ") and (" + keysearchString + " AM_MYFIELDS_TEMPLATEDATA.VALUE " + likeString + " '%" + searchQ + "%') ");
/*     */         }
/*     */         
/* 522 */         if (systemDataTextColumn.length() > 0) {
/* 523 */           systemDataTextColumn.delete(systemDataTextColumn.length() - 4, systemDataTextColumn.length());
/* 524 */           query.append(" union select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_MYFIELDS_SYSTEMDATA where AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and (" + systemDataTextColumn.toString() + ")");
/*     */         }
/*     */         
/* 527 */         if (userdataListColumn.length() > 0) {
/* 528 */           userdataListColumn.delete(userdataListColumn.length() - 4, userdataListColumn.length());
/* 529 */           query.append(" union select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_MYFIELDS_USERDATA,AM_MYFIELDS_TEMPLATEDATA where AM_MYFIELDS_USERDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and (" + userdataListColumn.toString() + ") and AM_MYFIELDS_TEMPLATEDATA.VALUE " + likeString + " '%" + searchQ + "%' ");
/*     */         }
/*     */         
/* 532 */         if (userdataTextColumn.length() > 0) {
/* 533 */           userdataTextColumn.delete(userdataTextColumn.length() - 4, userdataTextColumn.length());
/* 534 */           query.append(" union select AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_MYFIELDS_USERDATA where AM_MYFIELDS_USERDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and (" + userdataTextColumn.toString() + ")");
/*     */         }
/*     */         
/*     */ 
/* 538 */         query.append(" union select AM_MYFIELDS_LABELDATA.RESOURCEID from AM_MYFIELDS_LABELDATA, AM_ManagedObject, AM_MYFIELDS_TEMPLATEDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_LABELDATA.RESOURCEID " + userCondition + " and AM_MYFIELDS_LABELDATA.VALUEID= AM_MYFIELDS_TEMPLATEDATA.VALUEID and AM_MYFIELDS_TEMPLATEDATA.VALUE " + likeString + " '%" + searchQ + "%' ");
/*     */         
/* 540 */         query.append(" union select AM_ManagedObject.RESOURCEID from AM_MYFIELDS_ENTITYDATA, AM_ManagedObject , AM_MYFIELDS_LOCATION where AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and AM_MYFIELDS_LOCATION.LOCATION_NAME " + likeString + " '%" + searchQ + "%' and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION'");
/*     */         
/* 542 */         query.append(" union select AM_ManagedObject.RESOURCEID from AM_MYFIELDS_ENTITYDATA, AM_ManagedObject , AM_UserPasswordTable where AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_ManagedObject.RESOURCEID " + userCondition + " and AM_UserPasswordTable.USERNAME " + likeString + " '%" + searchQ + "%' and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_UserPasswordTable'");
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 547 */           if (rs != null) {
/* 548 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */         } catch (Exception ex) {
/* 551 */           ex.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 556 */         if (!searchCondition.equalsIgnoreCase("customfields")) {
/*     */           break label2371;
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 544 */         ex.printStackTrace();
/*     */       } finally {
/*     */         try {
/* 547 */           if (rs != null) {
/* 548 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */         } catch (Exception ex) {
/* 551 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 557 */       AMLog.debug("SearchAction.searchQuery() : customfields query : " + query.substring(7));
/* 558 */       return query.substring(7);
/*     */     }
/*     */     label2371:
/* 561 */     AMLog.debug("SearchAction.searchQuery() : searchCondition query : " + query.toString());
/* 562 */     return query.toString();
/*     */   }
/*     */   
/*     */   private String getResNameCondition(String searchQ, String likeString)
/*     */   {
/* 567 */     StringBuilder resNameCondition = new StringBuilder();
/* 568 */     resNameCondition.append(" (AM_ManagedObject.RESOURCENAME  " + likeString + " '%" + searchQ + "%') ");
/*     */     try
/*     */     {
/* 571 */       String hostIp = searchQ;
/*     */       try
/*     */       {
/* 574 */         InetAddress ip = InetAddress.getByName(searchQ);
/* 575 */         String[] ipValues = new String[3];
/* 576 */         ipValues[0] = ip.getCanonicalHostName();
/* 577 */         ipValues[1] = ip.getHostName().split("\\.")[0];
/* 578 */         ipValues[2] = ip.getHostAddress();
/* 579 */         if ((ipValues[2] != null) && (!ipValues[2].startsWith("0.")))
/*     */         {
/* 581 */           hostIp = ipValues[2];
/* 582 */           for (int i = 0; i < 3; i++)
/*     */           {
/* 584 */             if (ipValues[i] != null)
/*     */             {
/* 586 */               resNameCondition.append(" or (AM_ManagedObject.RESOURCENAME  " + likeString + " '%" + ipValues[i] + "%') ");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (UnknownHostException u)
/*     */       {
/* 593 */         u.printStackTrace();
/*     */       }
/* 595 */       if (Constants.ipAddress.toLowerCase().contains(hostIp.toLowerCase()))
/*     */       {
/* 597 */         resNameCondition.append(" or (AM_ManagedObject.RESOURCENAME = 'localhost') ");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 603 */       ex.printStackTrace();
/*     */     }
/* 605 */     return resNameCondition.toString();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\SearchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */