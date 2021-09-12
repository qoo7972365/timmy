/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.filter.UriCollector;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.discovery.Discovery;
/*      */ import com.adventnet.appmanager.server.discovery.DiscoveryUtil;
/*      */ import com.adventnet.appmanager.server.discovery.StartDiscovery;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.CredentialAPIUtil;
/*      */ import com.adventnet.security.authorization.Coding;
/*      */ import com.google.gson.Gson;
/*      */ import com.manageengine.appmanager.comm.HTTPResponse;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
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
/*      */ public class NewDiscoveryAction
/*      */   extends DispatchAction
/*      */ {
/*   61 */   String isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1 ? "true" : "false";
/*   62 */   CredentialManagerUtil credUtil = CredentialManagerUtil.getInstance();
/*   63 */   DiscoveryUtil discoveryUtil = new DiscoveryUtil();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   68 */   Map<String, Map> discoveryIDVsDetailsMap = new HashMap();
/*      */   
/*   70 */   Discovery disc = null;
/*      */   
/*      */ 
/*      */   public void NewDiscoveryAction() {}
/*      */   
/*      */ 
/*      */   public ActionForward discoveryNameExistsCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   78 */     String enteredDiscoveryName = request.getParameter("discoveryName");
/*   79 */     String isPresent = "false";
/*   80 */     ResultSet rs = null;
/*   81 */     String query = "select * from DISCOVERYINFO where LOWER(DISCOVERYNAME) = LOWER('" + enteredDiscoveryName + "')";
/*      */     try {
/*   83 */       rs = AMConnectionPool.executeQueryStmt(query);
/*   84 */       if (rs.next()) {
/*   85 */         isPresent = "true";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*   89 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*   92 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*      */     try
/*      */     {
/*   96 */       response.setContentType("text/html; charset=UTF-8");
/*   97 */       PrintWriter out = response.getWriter();
/*   98 */       out.println(isPresent);
/*   99 */       out.flush();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  103 */       ex.printStackTrace();
/*      */     }
/*  105 */     return null;
/*      */   }
/*      */   
/*      */   public List getDiscoveryList() {
/*  109 */     ResultSet rs = null;
/*  110 */     List discoveryList = new ArrayList();
/*  111 */     Map<String, String> HostNameVsRangeMap = CommDBUtil.getHostNamForAllotedRange();
/*  112 */     String queryToGetDiscoveryDetails = "select DISCOVERYINFO.DISCOVERYID,DISCOVERYDETAILS,TRIGGEREDFROM,STARTTIME,ENDTIME,SCHEDULETIME,SCANSUMMARY from DISCOVERYINFO JOIN DISCOVERYSCANDETAILS ON DISCOVERYINFO.DISCOVERYID=DISCOVERYSCANDETAILS.DISCOVERYID where DISCOVERYINFO.MARKEDFORDELETE!='true'";
/*      */     try
/*      */     {
/*  115 */       rs = AMConnectionPool.executeQueryStmt(queryToGetDiscoveryDetails);
/*  116 */       while (rs.next())
/*      */       {
/*  118 */         Properties discoveryTableProps = new Properties();
/*  119 */         String triggeredFrom = rs.getString("TRIGGEREDFROM");
/*  120 */         long discoveryID = rs.getLong("DISCOVERYID");
/*  121 */         String discoveryDetails = rs.getString("DISCOVERYDETAILS");
/*  122 */         JSONObject jsonObjectString = new JSONObject(discoveryDetails);
/*  123 */         discoveryTableProps.setProperty("discoveryID", discoveryID + "");
/*  124 */         discoveryTableProps.setProperty("discoveryName", jsonObjectString.getString("discoveryName"));
/*  125 */         String discoveryType = jsonObjectString.getString("discoveryType");
/*  126 */         discoveryTableProps.setProperty("discoveryType", discoveryType);
/*  127 */         String startTime = new Date(rs.getLong("STARTTIME")).toString();
/*  128 */         String endTime = rs.getLong("ENDTIME") == -1L ? "-1" : new Date(rs.getLong("ENDTIME")).toString();
/*      */         
/*  130 */         long scheduleTimeLongVal = rs.getLong("SCHEDULETIME");
/*      */         
/*  132 */         String scheduleTime = scheduleTimeLongVal == -1L ? FormatUtil.getString("am.webclient.link.schedule.discovery.creation") : new Date(scheduleTimeLongVal).toString();
/*  133 */         String scanSummary = rs.getString("SCANSUMMARY");
/*  134 */         if ("cidr".equalsIgnoreCase(discoveryType))
/*      */         {
/*  136 */           String netWorkAddress = jsonObjectString.getString("netWorkAddress");
/*  137 */           String cidr = jsonObjectString.getString("cidr");
/*  138 */           String rangeToShow = netWorkAddress + "/" + cidr;
/*      */           
/*      */ 
/*  141 */           discoveryTableProps.setProperty("rangeToShow", rangeToShow);
/*      */         }
/*  143 */         else if ("range".equalsIgnoreCase(discoveryType))
/*      */         {
/*      */ 
/*  146 */           String fromAddress = jsonObjectString.getString("fromAddress");
/*  147 */           String toAddress = jsonObjectString.getString("toAddress");
/*  148 */           String rangeToShow = fromAddress + " - " + toAddress;
/*  149 */           discoveryTableProps.setProperty("rangeToShow", rangeToShow);
/*      */         }
/*      */         else {
/*  152 */           String virtualHost = jsonObjectString.getString("virtualHost");
/*  153 */           discoveryTableProps.setProperty("rangeToShow", virtualHost);
/*      */         }
/*  155 */         discoveryTableProps.setProperty("startTime", startTime);
/*  156 */         discoveryTableProps.setProperty("endTime", endTime);
/*  157 */         discoveryTableProps.setProperty("scheduleTime", scheduleTime);
/*  158 */         discoveryTableProps.setProperty("scanSummary", scanSummary);
/*  159 */         if (EnterpriseUtil.isAdminServer)
/*      */         {
/*  161 */           triggeredFrom = triggeredFrom.equalsIgnoreCase("Admin Server") ? triggeredFrom : (String)HostNameVsRangeMap.get(triggeredFrom);
/*      */         }
/*      */         else
/*      */         {
/*  165 */           triggeredFrom = triggeredFrom.equalsIgnoreCase("Admin Server") ? triggeredFrom : "Local Server";
/*      */         }
/*  167 */         discoveryTableProps.setProperty("TRIGGEREDFROM", triggeredFrom);
/*  168 */         discoveryList.add(discoveryTableProps);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  173 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  177 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  179 */     return discoveryList;
/*      */   }
/*      */   
/*      */   public ActionForward saveScheduleDiscoveryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*  183 */     String forwardPage = "discoveryProfilesPage";
/*  184 */     long discoveryID = -1L;
/*      */     
/*  186 */     boolean success = false;
/*  187 */     String toRet = "";
/*  188 */     discoveryID = Long.parseLong(request.getParameter("discoveryID"));
/*  189 */     String scheduleType = request.getParameter("scheduleType");
/*  190 */     JSONObject scheduleTimeDetails = new JSONObject();
/*      */     try
/*      */     {
/*  193 */       if ("disabled".equalsIgnoreCase(scheduleType))
/*      */       {
/*  195 */         AMLog.logDiscoveryInfo(scheduleType);
/*  196 */         scheduleTimeDetails.put("scheduleType", "disabled");
/*  197 */         if (EnterpriseUtil.isAdminServer)
/*      */         {
/*  199 */           JSONObject json = new JSONObject();
/*  200 */           HTTPResponse responseFromMAS = null;
/*  201 */           int masId = (int)(discoveryID / 10000000L);
/*  202 */           Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(masId);
/*  203 */           String masHost = (String)masHostPort.get("HOST");
/*  204 */           String masPort = (String)masHostPort.get("PORT");
/*  205 */           String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */           try
/*      */           {
/*  208 */             json.put("discoveryID", discoveryID);
/*  209 */             json.put("apikey", masAPIKey);
/*  210 */             json.put("scheduleTimeDetails", scheduleTimeDetails);
/*  211 */             String url = this.discoveryUtil.frameURLForDisableScheduleDiscovery(masHost, masPort);
/*      */             try {
/*  213 */               String disabledMsg = EnterpriseUtil.sendRequestAndGetResponse(url, json);
/*  214 */               response.setContentType("text/html; charset=UTF-8");
/*      */               
/*  216 */               PrintWriter out = response.getWriter();
/*  217 */               out.println(disabledMsg.trim());
/*  218 */               out.flush();
/*      */             } catch (Exception e) {
/*  220 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (JSONException e)
/*      */           {
/*  225 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  230 */           scheduleTimeDetails.put("scheduleType", "disabled");
/*  231 */           success = this.discoveryUtil.disableScheduleDiscovery(discoveryID, scheduleTimeDetails);
/*  232 */           toRet = String.valueOf(success);
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  238 */         String hours = request.getParameter("hour");
/*  239 */         String min = request.getParameter("min");
/*      */         
/*      */ 
/*      */         try
/*      */         {
/*  244 */           scheduleTimeDetails.put("scheduleType", scheduleType);
/*  245 */           scheduleTimeDetails.put("discoveryID", discoveryID);
/*  246 */           scheduleTimeDetails.put("hours", hours);
/*  247 */           scheduleTimeDetails.put("min", min);
/*      */           
/*  249 */           if ("weekly".equalsIgnoreCase(scheduleType))
/*      */           {
/*  251 */             String day = request.getParameter("day");
/*  252 */             scheduleTimeDetails.put("day", day);
/*      */           }
/*  254 */           else if ("monthly".equalsIgnoreCase(scheduleType))
/*      */           {
/*  256 */             String date = request.getParameter("date");
/*  257 */             scheduleTimeDetails.put("date", date);
/*      */           }
/*      */           try
/*      */           {
/*  261 */             if (EnterpriseUtil.isAdminServer)
/*      */             {
/*      */               try {
/*  264 */                 scheduleTimeDetails.put("discoveryID", discoveryID);
/*  265 */                 String isScheduled = this.discoveryUtil.updateScheduleTimeForAdminServer(scheduleTimeDetails);
/*  266 */                 response.setContentType("text/html; charset=UTF-8");
/*      */                 
/*  268 */                 PrintWriter out = response.getWriter();
/*  269 */                 out.println(isScheduled.trim());
/*  270 */                 out.flush();
/*      */               }
/*      */               catch (JSONException e) {
/*  273 */                 e.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  280 */               long nextScheduleTime = this.discoveryUtil.scheduleTimeGeneration(scheduleTimeDetails);
/*  281 */               scheduleTimeDetails.put("scheduledTime", nextScheduleTime);
/*  282 */               this.discoveryUtil.updateScheduleDetails(discoveryID, scheduleTimeDetails);
/*  283 */               success = this.discoveryUtil.updateScheduleDiscoveryTime(discoveryID, nextScheduleTime);
/*  284 */               toRet = String.valueOf(success);
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  288 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  293 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exe)
/*      */     {
/*  299 */       exe.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/*  303 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/*  305 */       PrintWriter out = response.getWriter();
/*  306 */       out.println(toRet.trim());
/*  307 */       out.flush();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  311 */       exc.printStackTrace();
/*      */     }
/*  313 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getDiscoveryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*  317 */     String pageToShow = "discoveryProfilesPage";
/*  318 */     List discoveryList = getDiscoveryList();
/*  319 */     if (!this.discoveryUtil.checkWhetherMoreDevicesCanBeAdded())
/*      */     {
/*  321 */       AMLog.debug("From NewDiscoveryAction : getDiscoveryDetails() : User cannot add more monitors as current monitor count exceeded allowed monitor count.");
/*  322 */       UriCollector.setAccess(true);
/*      */     }
/*      */     
/*  325 */     String discId = "";
/*  326 */     String discName = "";
/*  327 */     String isDiscoveryStopped = request.getParameter("isDiscoveryStopped") != null ? request.getParameter("isDiscoveryStopped") : "false";
/*  328 */     if ("true".equalsIgnoreCase(isDiscoveryStopped))
/*      */     {
/*  330 */       discId = request.getParameter("discId");
/*  331 */       String scanSummaryMsg = this.discoveryUtil.getMessageForThisDiscoveryID(discId);
/*      */       
/*  333 */       if (scanSummaryMsg.indexOf(FormatUtil.getString("am.webclient.discovery.canceled.by.user.msg")) != -1)
/*      */       {
/*  335 */         isDiscoveryStopped = "false";
/*      */       }
/*      */       else
/*      */       {
/*  339 */         discName = this.discoveryUtil.getDiscoveryNameForDiscId(discId);
/*  340 */         request.setAttribute("discName", discName);
/*      */       }
/*      */     }
/*      */     
/*  344 */     boolean isADDMAddOnEnabled = FreeEditionDetails.getFreeEditionDetails().isADDMAllowed();
/*  345 */     String addmMessage = FreeEditionDetails.addmMessage != null ? FreeEditionDetails.addmMessage : "";
/*  346 */     String isFreeEdition = "f".equalsIgnoreCase(FreeEditionDetails.getFreeEditionDetails().getUserType()) ? "true" : "false";
/*  347 */     request.setAttribute("discoveryStopped", isDiscoveryStopped);
/*      */     
/*  349 */     request.setAttribute("discoveryTableList", discoveryList);
/*  350 */     request.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/*  351 */     request.setAttribute("isADDMAddOnEnabled", Boolean.valueOf(isADDMAddOnEnabled));
/*  352 */     request.setAttribute("addmMessage", addmMessage);
/*  353 */     request.setAttribute("isFreeEdition", isFreeEdition);
/*  354 */     return mapping.findForward(pageToShow);
/*      */   }
/*      */   
/*      */   public List getNetMaskListWithSelectedValue(String selectedValue)
/*      */   {
/*  359 */     ArrayList netMaskList = new ArrayList(Arrays.asList(new String[] { "255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0", "255.252.0.0", "255.254.0.0", "255.255.0.0", "255.255.128.0", "255.255.192.0", "255.255.224.0", "255.255.240.0", "255.255.248.0", "255.255.252.0", "255.255.254.0", "255.255.255.0", "255.255.255.128", "255.255.255.192", "255.255.255.224", "255.255.255.240", "255.255.255.248", "255.255.255.252" }));
/*      */     
/*      */ 
/*  362 */     List netMaskListToReturn = new ArrayList();
/*  363 */     for (int i = 0; i < netMaskList.size(); i++)
/*      */     {
/*  365 */       HashMap eachNetMaskDetails = new HashMap();
/*  366 */       String netmaskValue = (String)netMaskList.get(i);
/*  367 */       eachNetMaskDetails.put("netMaskValue", netmaskValue);
/*  368 */       eachNetMaskDetails.put("netMaskDisplayValue", netmaskValue);
/*  369 */       eachNetMaskDetails.put("selected", netmaskValue.equalsIgnoreCase(selectedValue) ? "selected" : "false");
/*  370 */       netMaskListToReturn.add(eachNetMaskDetails);
/*      */     }
/*  372 */     return netMaskListToReturn;
/*      */   }
/*      */   
/*      */   public ActionForward updateDiscoveryInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  377 */     String forwardPage = "discoveryProfilesPage";
/*  378 */     long discoveryID = Long.parseLong(request.getParameter("discoveryID"));
/*  379 */     this.discoveryUtil.updateDiscoveryInfo(discoveryID, false);
/*  380 */     return new ActionForward("/newDiscoveryAction.do?method=getDiscoveryDetails");
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
/*      */   public ActionForward stopDiscoveryProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  490 */     long discoveryID = Long.parseLong(request.getParameter("discoveryID"));
/*  491 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  493 */       JSONObject json = new JSONObject();
/*  494 */       int masId = (int)(discoveryID / 10000000L);
/*  495 */       Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(masId);
/*  496 */       String masHost = (String)masHostPort.get("HOST");
/*  497 */       String masPort = (String)masHostPort.get("PORT");
/*  498 */       String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */       try
/*      */       {
/*  501 */         json.put("discoveryID", discoveryID);
/*  502 */         json.put("apikey", masAPIKey);
/*  503 */         String url = this.discoveryUtil.frameURLForStopDiscovery(masHost, masPort);
/*  504 */         String res = EnterpriseUtil.sendRequestAndGetResponse(url, json);
/*      */         
/*  506 */         response.setContentType("text/html; charset=UTF-8");
/*  507 */         PrintWriter out = response.getWriter();
/*  508 */         out.println(String.valueOf(res));
/*  509 */         out.flush();
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  514 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  523 */         boolean isDiscoveryStopped = this.discoveryUtil.stopDiscovery(discoveryID);
/*  524 */         response.setContentType("text/html; charset=UTF-8");
/*  525 */         PrintWriter out = response.getWriter();
/*  526 */         out.println(String.valueOf(isDiscoveryStopped));
/*  527 */         out.flush();
/*      */       }
/*      */       catch (Exception e) {
/*  530 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  534 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward discoveredDevicesTableView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  539 */     String forwardPage = "discoveredDevicesPage";
/*  540 */     String devsAdded = "";
/*  541 */     int applicationsAdded = 0;
/*  542 */     String fromUIViewIcon = "";
/*  543 */     String discoveryName = "";
/*  544 */     String tableElementsHasValues = "true";
/*      */     
/*  546 */     Map<String, List> discoveredDevicesMap = new HashMap();
/*  547 */     List<String> servicesList = new ArrayList();
/*      */     
/*  549 */     DiscoveryUtil discUtil = new DiscoveryUtil();
/*  550 */     LinkedHashMap masterMap = new LinkedHashMap();
/*  551 */     Map<String, String> imgMap = new HashMap();
/*  552 */     List<String> tableHeaders = new ArrayList();
/*  553 */     String discID = "";
/*      */     try {
/*  555 */       fromUIViewIcon = request.getParameter("fromView") != null ? request.getParameter("fromView") : "false";
/*  556 */       discID = request.getParameter("discoveryID");
/*  557 */       Map argMap = new HashMap();
/*  558 */       argMap.put("fromUIViewIcon", fromUIViewIcon);
/*  559 */       AMLog.logDiscoveryInfo("NewDiscoveryAction :: fromView::" + fromUIViewIcon);
/*  560 */       if ("true".equalsIgnoreCase(fromUIViewIcon))
/*      */       {
/*  562 */         Map resultMap = discUtil.getDiscoveredDevicesDetailsFromDB(discID);
/*      */         
/*  564 */         discoveredDevicesMap = (Map)resultMap.get("devicesMap");
/*  565 */         List<String> servicesListFromDB = (List)resultMap.get("servicesList");
/*  566 */         discoveryName = (String)resultMap.get("discoveryName");
/*  567 */         applicationsAdded = Integer.parseInt(resultMap.get("applicationCount").toString());
/*      */         
/*  569 */         devsAdded = String.valueOf(resultMap.get("devsAdded"));
/*  570 */         String discoveryType = (String)resultMap.get("discoveryType");
/*  571 */         String discoveryCateg = (String)resultMap.get("discoveryCateg");
/*  572 */         argMap.put("discoveredDevicesMap", discoveredDevicesMap);
/*  573 */         argMap.put("servicesList", servicesListFromDB);
/*  574 */         argMap.put("discoveryType", discoveryType);
/*  575 */         argMap.put("discoveryCateg", discoveryCateg);
/*  576 */         AMLog.logDiscoveryInfo("Discovered Devices Map from view::" + discoveredDevicesMap);
/*  577 */         AMLog.logDiscoveryInfo("Services list from view::" + servicesListFromDB);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  582 */         Map discoveryDetailsMap = (Map)this.discoveryIDVsDetailsMap.get(discID);
/*  583 */         discoveredDevicesMap = (Map)discoveryDetailsMap.get("discoveredDevicesMap");
/*  584 */         servicesList = (List)discoveryDetailsMap.get("servicesList");
/*  585 */         applicationsAdded = Integer.parseInt(discoveryDetailsMap.get("noOfAddedApplications").toString());
/*  586 */         discoveryName = (String)discoveryDetailsMap.get("discoveryName");
/*  587 */         devsAdded = request.getParameter("devsAdded");
/*  588 */         argMap.put("discoveredDevicesMap", discoveredDevicesMap);
/*  589 */         argMap.put("servicesList", servicesList);
/*  590 */         argMap.put("discoveryCateg", discoveryDetailsMap.get("discoveryCateg"));
/*  591 */         argMap.put("discoveryType", discoveryDetailsMap.get("discoveryType"));
/*      */         
/*  593 */         AMLog.logDiscoveryInfo("Discovered Devices Map after discovery::" + discoveredDevicesMap);
/*  594 */         AMLog.logDiscoveryInfo("Services list after discovery::" + servicesList);
/*      */       }
/*      */       
/*  597 */       Map toReturnMap = discUtil.getElementsForTableView(argMap);
/*  598 */       tableHeaders = (List)toReturnMap.get("tableHeaders");
/*  599 */       masterMap = (LinkedHashMap)toReturnMap.get("tableElements");
/*      */       
/*  601 */       imgMap = (Map)toReturnMap.get("imgMap");
/*  602 */       AMLog.logDiscoveryInfo(" TableView :: tableElements==>" + masterMap);
/*  603 */       AMLog.logDiscoveryInfo(" TableView :: DevDetails Map==>" + imgMap);
/*      */       
/*      */ 
/*  606 */       tableElementsHasValues = masterMap.size() > 0 ? "true" : "false";
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  611 */       e.printStackTrace();
/*      */     }
/*  613 */     request.setAttribute("tableHeaders", tableHeaders);
/*  614 */     request.setAttribute("tableElements", masterMap);
/*  615 */     request.setAttribute("tableElementsHasValues", tableElementsHasValues);
/*  616 */     request.setAttribute("noOfAddedDevices", devsAdded);
/*  617 */     request.setAttribute("noOfAddedApplications", String.valueOf(applicationsAdded));
/*  618 */     request.setAttribute("imgMap", imgMap);
/*  619 */     request.setAttribute("fromViewIcon", fromUIViewIcon);
/*  620 */     request.setAttribute("discoveryName", discoveryName);
/*  621 */     boolean isADDMAddOnEnabled = FreeEditionDetails.getFreeEditionDetails().isADDMAllowed();
/*  622 */     String addmMessage = FreeEditionDetails.addmMessage != null ? FreeEditionDetails.addmMessage : "";
/*  623 */     String isFreeEdition = "f".equalsIgnoreCase(FreeEditionDetails.getFreeEditionDetails().getUserType()) ? "true" : "false";
/*  624 */     request.setAttribute("isADDMAddOnEnabled", Boolean.valueOf(isADDMAddOnEnabled));
/*  625 */     request.setAttribute("addmMessage", addmMessage);
/*  626 */     request.setAttribute("isFreeEdition", isFreeEdition);
/*  627 */     return new ActionForward("/showTile.do?TileName=Tile.DiscoveredDevices");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showDiscoveryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  633 */     String forwardPage = "newDiscoveryPage";
/*  634 */     long discoveryID = -1L;
/*  635 */     int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/*  636 */     boolean isPrivileged = Constants.isPrivilegedUser(request);
/*  637 */     List credentialList = getCredentialNamesAndIds(discoveryID, userID, isPrivileged);
/*  638 */     if ((credentialList.isEmpty()) && 
/*  639 */       (addCredentialBydefault(request, response))) {
/*  640 */       credentialList = getCredentialNamesAndIds(discoveryID, userID, isPrivileged);
/*      */     }
/*      */     
/*  643 */     request.setAttribute("list", credentialList);
/*  644 */     request.setAttribute("credentialDetailsSelected", "selectCredentials");
/*  645 */     request.setAttribute("netMaskList", getNetMaskListWithSelectedValue("255.255.255.0"));
/*  646 */     request.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/*  647 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/*  650 */         request.setAttribute("masDetailsList", this.discoveryUtil.getDistributedServers());
/*  651 */         request.setAttribute("moCountMap", DBUtil.loadMOCountForMAS());
/*      */       }
/*      */       catch (Exception e) {
/*  654 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  657 */     request.setAttribute("discoveryID", "-1");
/*  658 */     return mapping.findForward(forwardPage);
/*      */   }
/*      */   
/*      */   private boolean addCredentialBydefault(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  665 */       String username = request.getRemoteUser();
/*  666 */       String apikey = Constants.getUserAPIkey(username);
/*  667 */       String SERVLET_PATH = "/AppManager";
/*  668 */       String PATH_INFO = "/xml/credential";
/*  669 */       MockHttpServletRequest MSreq = new MockHttpServletRequest("", "/AppManager", "/xml/credential", "");
/*  670 */       MSreq.addParameter("type", "SNMP v1v2");
/*  671 */       MSreq.addParameter("credentialDescr", "test");
/*  672 */       MSreq.addParameter("credentialName", "public");
/*  673 */       MSreq.addParameter("snmpCommunityString", "public");
/*  674 */       MSreq.addParameter("timeout", "5");
/*  675 */       MSreq.setMethod("POST");
/*  676 */       MSreq.addParameter("apikey", apikey);
/*  677 */       String result = CredentialAPIUtil.credentialAPI(MSreq, response, false);
/*  678 */       AMLog.debug("ADD default credential:::" + result);
/*  679 */       if (result.indexOf(FormatUtil.getString("am.webclient.api.credential.add.success")) != -1) {
/*  680 */         return true;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  685 */       ex.printStackTrace();
/*      */     }
/*  687 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward deleteDiscoveryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  693 */     String forwardPage = "discoveryProfilesPage";
/*  694 */     long discoveryID = -1L;
/*  695 */     discoveryID = Long.parseLong(request.getParameter("discoveryID"));
/*  696 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  698 */       JSONObject json = new JSONObject();
/*  699 */       HTTPResponse responseFromMAS = null;
/*  700 */       int masId = (int)(discoveryID / 10000000L);
/*  701 */       Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(masId);
/*  702 */       String masHost = (String)masHostPort.get("HOST");
/*  703 */       String masPort = (String)masHostPort.get("PORT");
/*  704 */       String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */       try
/*      */       {
/*  707 */         this.discoveryUtil.updateDiscoveryInfo(discoveryID, true);
/*  708 */         json.put("discoveryID", discoveryID);
/*  709 */         json.put("apikey", masAPIKey);
/*  710 */         String url = this.discoveryUtil.frameURLForgetDeleteDiscovery(masHost, masPort);
/*      */         try {
/*  712 */           String isDeleted = EnterpriseUtil.sendRequestAndGetResponse(url, json);
/*  713 */           response.setContentType("text/html; charset=UTF-8");
/*      */           
/*  715 */           PrintWriter out = response.getWriter();
/*  716 */           out.println(isDeleted.trim());
/*  717 */           out.flush();
/*      */         } catch (Exception e) {
/*  719 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       catch (JSONException e)
/*      */       {
/*  724 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       try {
/*  730 */         boolean isDeleted = this.discoveryUtil.deleteDiscoveryDetails(discoveryID);
/*  731 */         response.setContentType("text/html; charset=UTF-8");
/*  732 */         PrintWriter out = response.getWriter();
/*  733 */         out.println(String.valueOf(isDeleted));
/*  734 */         out.flush();
/*      */       }
/*      */       catch (Exception e) {
/*  737 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  740 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward rediscovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  745 */     String forwardPage = "newDiscoveryPage";
/*  746 */     String discoveryID = request.getParameter("discoveryID");
/*  747 */     String rediscovery = "true";
/*  748 */     request.setAttribute("discoveryID", discoveryID);
/*  749 */     request.setAttribute("rediscovery", rediscovery);
/*  750 */     return mapping.findForward(forwardPage);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showEditDiscoveryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  756 */     String forwardPage = "newDiscoveryPage";
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  761 */       String discoveryID = request.getParameter("discoveryID");
/*  762 */       int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/*  763 */       boolean isPrivileged = Constants.isPrivilegedUser(request);
/*  764 */       request.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/*  765 */       long discoveryIDLong = new Long(discoveryID).longValue();
/*  766 */       String rediscover = request.getParameter("rediscover") != null ? request.getParameter("rediscover") : "false";
/*  767 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  769 */         int masID = EnterpriseUtil.getManagedServerIndex((int)discoveryIDLong);
/*  770 */         request.setAttribute("masDetailsList", this.discoveryUtil.getMASListWithSelectedMAS(masID));
/*  771 */         request.setAttribute("moCountMap", DBUtil.loadMOCountForMAS());
/*      */       }
/*  773 */       Map<String, String> discoveryIDDetails = this.discoveryUtil.getDetailsForDiscID(discoveryIDLong);
/*      */       
/*      */ 
/*  776 */       String discName = (String)discoveryIDDetails.get("discoveryName");
/*      */       
/*  778 */       request.setAttribute("discoveryName", discName);
/*  779 */       request.setAttribute("rediscover", rediscover);
/*  780 */       String discoveryDetails = (String)discoveryIDDetails.get("discoveryDetails");
/*  781 */       JSONObject discoveryDetailsAsJSONObject = new JSONObject(discoveryDetails);
/*  782 */       String discoveryType = discoveryDetailsAsJSONObject.getString("discoveryType");
/*  783 */       String isAllCred = discoveryDetailsAsJSONObject.getString("isAllCred");
/*  784 */       if ("range".equalsIgnoreCase(discoveryType))
/*      */       {
/*  786 */         String fromAddress = discoveryDetailsAsJSONObject.getString("fromAddress");
/*  787 */         String toAddress = discoveryDetailsAsJSONObject.getString("toAddress");
/*  788 */         String subNetMask = discoveryDetailsAsJSONObject.getString("netMask");
/*  789 */         String[] fromAddressArray = fromAddress.split("\\.");
/*  790 */         request.setAttribute("fromAddress1", fromAddressArray[0]);
/*  791 */         request.setAttribute("fromAddress2", fromAddressArray[1]);
/*  792 */         request.setAttribute("fromAddress3", fromAddressArray[2]);
/*  793 */         request.setAttribute("fromAddress4", fromAddressArray[3]);
/*  794 */         String[] toAddressArray = toAddress.split("\\.");
/*  795 */         request.setAttribute("toAddress1", toAddressArray[0]);
/*  796 */         request.setAttribute("toAddress2", toAddressArray[1]);
/*  797 */         request.setAttribute("toAddress3", toAddressArray[2]);
/*  798 */         request.setAttribute("toAddress4", toAddressArray[3]);
/*  799 */         request.setAttribute("netMaskList", getNetMaskListWithSelectedValue(subNetMask));
/*  800 */         request.setAttribute("discoveryType", "range");
/*      */       }
/*  802 */       else if ("cidr".equalsIgnoreCase(discoveryType))
/*      */       {
/*  804 */         String netWorkAddress = discoveryDetailsAsJSONObject.getString("netWorkAddress");
/*  805 */         String[] netWorkAddressArray = netWorkAddress.split("\\.");
/*  806 */         request.setAttribute("netMaskList", getNetMaskListWithSelectedValue("255.255.255.0"));
/*  807 */         request.setAttribute("netWorkAddress1", netWorkAddressArray[0]);
/*  808 */         request.setAttribute("netWorkAddress2", netWorkAddressArray[1]);
/*  809 */         request.setAttribute("netWorkAddress3", netWorkAddressArray[2]);
/*  810 */         request.setAttribute("netWorkAddress4", netWorkAddressArray[3]);
/*  811 */         String cidr = discoveryDetailsAsJSONObject.getString("cidr");
/*  812 */         request.setAttribute("cidr", cidr);
/*  813 */         request.setAttribute("discoveryType", "cidr");
/*      */ 
/*      */       }
/*  816 */       else if ("virtual".equalsIgnoreCase(discoveryType)) {
/*  817 */         String password = discoveryDetailsAsJSONObject.getString("virtualPassword");
/*      */         
/*  819 */         request.setAttribute("virtualHost", discoveryDetailsAsJSONObject.getString("virtualHost"));
/*  820 */         request.setAttribute("virtualPort", discoveryDetailsAsJSONObject.getString("virtualPort"));
/*  821 */         request.setAttribute("virtualUserName", discoveryDetailsAsJSONObject.getString("virtualUserName"));
/*  822 */         request.setAttribute("virtualPassword", Coding.convertFromBase(password));
/*  823 */         request.setAttribute("discoveryType", discoveryType);
/*  824 */         String subNetMask = discoveryDetailsAsJSONObject.getString("netMask");
/*  825 */         String createMGView = discoveryDetailsAsJSONObject.getString("createMGview");
/*  826 */         request.setAttribute("netMaskList", getNetMaskListWithSelectedValue(subNetMask));
/*  827 */         request.setAttribute("createMGView", createMGView);
/*      */       }
/*  829 */       request.setAttribute("discoveryID", discoveryID);
/*      */       
/*  831 */       String credentialDetailsSelected = isAllCred.equalsIgnoreCase("true") ? "allCredentials" : "selectCredentials";
/*      */       
/*      */ 
/*  834 */       List credentialList = getCredentialNamesAndIds(discoveryIDLong, userID, isPrivileged);
/*  835 */       List credentialsNotPresent = getCredentialsNotUsedInThisDiscovery(discoveryIDLong, userID, isPrivileged);
/*      */       
/*  837 */       request.setAttribute("list", credentialList);
/*  838 */       request.setAttribute("notPresentList", credentialsNotPresent);
/*      */       
/*  840 */       request.setAttribute("credentialDetailsSelected", credentialDetailsSelected);
/*  841 */       request.setAttribute("actionTodo", "edit");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  845 */       ex.printStackTrace();
/*      */     }
/*  847 */     return mapping.findForward(forwardPage);
/*      */   }
/*      */   
/*      */   public ActionForward getDiscoveryStatusMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  852 */     JSONObject jsonObjectToClient = new JSONObject();
/*  853 */     String logMessage = "";
/*  854 */     int totalDeviceCount = 0;
/*  855 */     int noOfCompletedRequests = 0;
/*  856 */     int noOfAddedDevices = 0;
/*  857 */     int esxCount = 0;
/*  858 */     int noOfAddedEsxHosts = 0;
/*  859 */     int reqReceivedForEsx = 0;
/*  860 */     String discoveryType = "";
/*      */     
/*  862 */     int noOfAddedApplications = 0;
/*  863 */     String discoveryCateg = "";
/*  864 */     String discoveryName = "";
/*  865 */     response.setContentType("text/json; charset=UTF-8");
/*  866 */     String discoveryID = request.getParameter("discoveryID");
/*      */     
/*  868 */     long id = Long.parseLong(discoveryID);
/*      */     
/*  870 */     if (EnterpriseUtil.isAdminServer)
/*      */     {
/*  872 */       JSONObject json = new JSONObject();
/*  873 */       int masId = (int)(id / 10000000L);
/*  874 */       Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(masId);
/*  875 */       String masHost = (String)masHostPort.get("HOST");
/*  876 */       String masPort = (String)masHostPort.get("PORT");
/*  877 */       String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */       try
/*      */       {
/*  880 */         json.put("discoveryID", discoveryID);
/*  881 */         json.put("apikey", masAPIKey);
/*  882 */         String url = this.discoveryUtil.frameURLForgetDiscoveryStatus(masHost, masPort);
/*  883 */         String res = EnterpriseUtil.sendRequestAndGetResponse(url, json);
/*      */         
/*      */ 
/*  886 */         String delimiter = "#:";
/*  887 */         String[] temp = res.split(delimiter);
/*      */         
/*  889 */         logMessage = temp[0];
/*  890 */         totalDeviceCount = Integer.parseInt(temp[1]);
/*  891 */         noOfCompletedRequests = Integer.parseInt(temp[2]);
/*  892 */         noOfAddedDevices = Integer.parseInt(temp[3]);
/*      */         
/*  894 */         esxCount = Integer.parseInt(temp[4]);
/*  895 */         noOfAddedEsxHosts = Integer.parseInt(temp[5]);
/*  896 */         reqReceivedForEsx = Integer.parseInt(temp[6]);
/*  897 */         discoveryType = temp[7];
/*  898 */         noOfAddedApplications = Integer.parseInt(temp[10]);
/*      */         
/*      */ 
/*  901 */         if (logMessage.equalsIgnoreCase(FormatUtil.getString("am.webclient.discovery.completed")))
/*      */         {
/*  903 */           String pvchild = temp[8];
/*  904 */           AMLog.logDiscoveryInfo("pvchild::" + pvchild);
/*  905 */           JSONObject parentVsChild = new JSONObject(temp[8]);
/*  906 */           String services = temp[9];
/*      */           
/*  908 */           discoveryCateg = temp[11];
/*  909 */           discoveryName = temp[12];
/*  910 */           AMLog.logDiscoveryInfo("##TEST## parentVsChild :: from MAS::" + parentVsChild);
/*  911 */           Map discoveredDevicesMap = convertJSONObjtoMap(parentVsChild);
/*  912 */           List<String> servicesList = convertStringToList(services);
/*      */           
/*  914 */           Map discoveryDetailsMap = new HashMap();
/*  915 */           discoveryDetailsMap.put("discoveredDevicesMap", discoveredDevicesMap);
/*  916 */           discoveryDetailsMap.put("servicesList", servicesList);
/*  917 */           discoveryDetailsMap.put("noOfAddedApplications", Integer.valueOf(noOfAddedApplications));
/*  918 */           discoveryDetailsMap.put("discoveryCateg", discoveryCateg);
/*  919 */           discoveryDetailsMap.put("discoveryName", discoveryName);
/*  920 */           discoveryDetailsMap.put("discoveryType", discoveryType);
/*  921 */           this.discoveryIDVsDetailsMap.put(discoveryID, discoveryDetailsMap);
/*      */         }
/*  923 */         jsonObjectToClient.put("logMessage", logMessage);
/*  924 */         jsonObjectToClient.put("totalDevices", totalDeviceCount);
/*  925 */         jsonObjectToClient.put("noOfCompletedRequests", noOfCompletedRequests);
/*  926 */         jsonObjectToClient.put("noOfAddedDevices", noOfAddedDevices);
/*  927 */         jsonObjectToClient.put("noOfAddedApplications", noOfAddedApplications);
/*      */         
/*  929 */         jsonObjectToClient.put("esxCount", esxCount);
/*  930 */         jsonObjectToClient.put("noOfAddedEsxHosts", noOfAddedEsxHosts);
/*  931 */         jsonObjectToClient.put("reqReceivedForEsx", reqReceivedForEsx);
/*  932 */         jsonObjectToClient.put("discoveryType", discoveryType);
/*      */         
/*      */         try
/*      */         {
/*  936 */           PrintWriter out = response.getWriter();
/*  937 */           out.println(jsonObjectToClient);
/*  938 */           out.flush();
/*      */         } catch (IOException e) {
/*  940 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       catch (JSONException e)
/*      */       {
/*  946 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  951 */       this.disc = this.discoveryUtil.getInstanceForDiscID(id);
/*  952 */       discoveryType = this.discoveryUtil.getDiscoveryTypeForDiscoveryID(id);
/*  953 */       logMessage = this.disc.getLogMessage();
/*  954 */       totalDeviceCount = this.disc.getTotalDevicesCount();
/*  955 */       noOfCompletedRequests = this.disc.getCompletedRequests();
/*  956 */       noOfAddedDevices = this.disc.getNoOfAddedDevices();
/*  957 */       noOfAddedApplications = this.disc.getNoOfAddedApplications();
/*      */       
/*      */ 
/*  960 */       if ("virtual".equalsIgnoreCase(discoveryType))
/*      */       {
/*  962 */         esxCount = this.disc.getESXCountFromDiscovery();
/*  963 */         noOfAddedEsxHosts = this.disc.getNoOfAddedESXHosts();
/*  964 */         reqReceivedForEsx = this.disc.getReqReceivedCountForEsx();
/*      */       }
/*      */       
/*  967 */       if (logMessage.equals(FormatUtil.getString("am.webclient.discovery.completed")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  972 */         discoveryCateg = this.discoveryUtil.getDiscoveryCategForDiscoveryID(id);
/*  973 */         discoveryName = this.discoveryUtil.getDiscoveryNameFromInstanceMap(id);
/*      */         
/*  975 */         Map discoveryDetailsMap = new HashMap();
/*  976 */         discoveryDetailsMap.put("discoveredDevicesMap", this.disc.getParentVsChild());
/*  977 */         discoveryDetailsMap.put("servicesList", this.disc.getServicesList());
/*  978 */         discoveryDetailsMap.put("noOfAddedApplications", Integer.valueOf(noOfAddedApplications));
/*  979 */         discoveryDetailsMap.put("discoveryCateg", discoveryCateg);
/*  980 */         discoveryDetailsMap.put("discoveryName", discoveryName);
/*  981 */         discoveryDetailsMap.put("discoveryType", discoveryType);
/*      */         
/*  983 */         this.discoveryIDVsDetailsMap.put(discoveryID, discoveryDetailsMap);
/*  984 */         AMLog.logDiscoveryInfo("DiscoveredDevicesMap received from discovery.java===>" + this.discoveryIDVsDetailsMap);
/*      */         
/*  986 */         this.discoveryUtil.clearIDFromInstanceMap(id);
/*  987 */         this.discoveryUtil.clearSchedulersForDiscoveryId(id);
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/*  993 */         jsonObjectToClient.put("logMessage", logMessage);
/*  994 */         jsonObjectToClient.put("totalDevices", totalDeviceCount);
/*  995 */         jsonObjectToClient.put("noOfCompletedRequests", noOfCompletedRequests);
/*  996 */         jsonObjectToClient.put("noOfAddedDevices", noOfAddedDevices);
/*  997 */         jsonObjectToClient.put("noOfAddedApplications", noOfAddedApplications);
/*  998 */         jsonObjectToClient.put("esxCount", esxCount);
/*  999 */         jsonObjectToClient.put("noOfAddedEsxHosts", noOfAddedEsxHosts);
/* 1000 */         jsonObjectToClient.put("reqReceivedForEsx", reqReceivedForEsx);
/* 1001 */         jsonObjectToClient.put("discoveryType", discoveryType);
/* 1002 */         PrintWriter out = response.getWriter();
/* 1003 */         out.println(jsonObjectToClient);
/* 1004 */         out.flush();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1008 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1011 */     return null;
/*      */   }
/*      */   
/*      */   private List<String> convertStringToList(String services)
/*      */   {
/* 1016 */     String replace = services.replace("[", "");
/* 1017 */     String servicesListStr = replace.replace("]", "");
/* 1018 */     List servicesList = new ArrayList(Arrays.asList(servicesListStr.split(",")));
/*      */     
/*      */ 
/* 1021 */     return servicesList;
/*      */   }
/*      */   
/*      */ 
/*      */   private Map convertJSONObjtoMap(JSONObject parentVsChild)
/*      */   {
/* 1027 */     Map map = new HashMap();
/* 1028 */     if (parentVsChild != JSONObject.NULL) {
/*      */       try {
/* 1030 */         map = toMap(parentVsChild);
/*      */ 
/*      */       }
/*      */       catch (JSONException e)
/*      */       {
/* 1035 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1038 */     return map;
/*      */   }
/*      */   
/*      */   public Map toMap(JSONObject object) throws JSONException
/*      */   {
/* 1043 */     Map<String, Object> map = new HashMap();
/*      */     
/* 1045 */     Iterator<String> keysItr = object.keys();
/* 1046 */     while (keysItr.hasNext()) {
/* 1047 */       String key = (String)keysItr.next();
/* 1048 */       Object value = object.get(key);
/*      */       
/* 1050 */       if ((value instanceof JSONArray)) {
/* 1051 */         value = toList((JSONArray)value);
/*      */ 
/*      */ 
/*      */       }
/* 1055 */       else if ((value instanceof JSONObject)) {
/* 1056 */         value = toMap((JSONObject)value);
/*      */       }
/* 1058 */       map.put(key, value);
/*      */     }
/*      */     
/* 1061 */     return map;
/*      */   }
/*      */   
/*      */   public List toList(JSONArray array) throws JSONException {
/* 1065 */     List<Object> list = new ArrayList();
/* 1066 */     for (int i = 0; i < array.length(); i++) {
/* 1067 */       Object value = array.get(i);
/* 1068 */       if ((value instanceof JSONArray)) {
/* 1069 */         value = toList((JSONArray)value);
/*      */ 
/*      */       }
/* 1072 */       else if ((value instanceof JSONObject)) {
/* 1073 */         value = toMap((JSONObject)value);
/*      */       }
/* 1075 */       list.add(value);
/*      */     }
/* 1077 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */   public JSONObject getPortsAsJson(String[] portArray)
/*      */   {
/* 1083 */     JSONObject portObject = new JSONObject();
/*      */     try
/*      */     {
/* 1086 */       for (int i = 0; i < portArray.length; i++)
/*      */       {
/* 1088 */         portObject.put(portArray[i].split("#1:")[0], portArray[i].split("#1:")[1]);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1093 */       ex.printStackTrace();
/*      */     }
/* 1095 */     return portObject;
/*      */   }
/*      */   
/*      */   public ActionForward performRediscovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 1100 */       String discoveryID = request.getParameter("discoveryID");
/* 1101 */       Map<String, String> discoveryDetails = this.discoveryUtil.getDetailsForDiscID(Long.parseLong(discoveryID));
/* 1102 */       String discDet = (String)discoveryDetails.get("discoveryDetails");
/* 1103 */       JSONObject networkDetails = new JSONObject(discDet);
/* 1104 */       networkDetails.put("discoveryCateg", "rediscovery");
/* 1105 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1110 */         boolean isTriggeredFromAdmin = Boolean.parseBoolean((String)discoveryDetails.get("isTriggeredFromAdmin"));
/* 1111 */         String masHost = "";
/* 1112 */         String masPort = "";
/* 1113 */         if (isTriggeredFromAdmin)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1118 */           masHost = (String)networkDetails.get("masHost");
/* 1119 */           masPort = (String)networkDetails.get("masPort");
/*      */         }
/*      */         else
/*      */         {
/* 1123 */           int masId = (int)(Long.parseLong(discoveryID) / 10000000L);
/*      */           
/* 1125 */           Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(masId);
/*      */           
/* 1127 */           masHost = (String)masHostPort.get("HOST");
/* 1128 */           masPort = (String)masHostPort.get("PORT");
/* 1129 */           String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */           
/* 1131 */           networkDetails.put("masId", masId);
/* 1132 */           networkDetails.put("masHost", masHost);
/* 1133 */           networkDetails.put("masPort", masPort);
/* 1134 */           networkDetails.put("apikey", masAPIKey);
/*      */         }
/* 1136 */         String url = this.discoveryUtil.frameURLForRediscovery(masHost, masPort);
/*      */         try {
/* 1138 */           String id = EnterpriseUtil.sendRequestAndGetResponse(url, networkDetails);
/* 1139 */           AMLog.logDiscoveryInfo("Received Discovery ID<Rediscovery (UI)>:" + id);
/* 1140 */           response.setContentType("text/html; charset=UTF-8");
/* 1141 */           PrintWriter out = response.getWriter();
/* 1142 */           out.println(id + "");
/* 1143 */           out.flush();
/*      */         }
/*      */         catch (Exception e) {
/* 1146 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1151 */         callStartDiscovery(networkDetails, Long.parseLong(discoveryID), response);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1156 */       e.printStackTrace();
/*      */     }
/* 1158 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getDiscoveryParamsAndShowDiscoveryProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1166 */       JSONObject networkDetails = new JSONObject();
/* 1167 */       JSONArray networkDetailsArray = new JSONArray();
/* 1168 */       String discoveryName = request.getParameter("discoveryName");
/*      */       
/* 1170 */       String discoveryType = request.getParameter("discoveryType");
/*      */       
/* 1172 */       String listOfCredentials = "";
/* 1173 */       String isAllCred = request.getParameter("isAllCred");
/* 1174 */       String portDetails = request.getParameter("portDetails");
/*      */       
/* 1176 */       String discoveryCateg = request.getParameter("discoveryCateg");
/*      */       
/*      */ 
/* 1179 */       String portString = portDetails.endsWith("#2") ? portDetails.substring(0, portDetails.lastIndexOf("#2")) : portDetails;
/* 1180 */       String[] portArray = portString.split("#2,");
/* 1181 */       JSONObject portJsonString = getPortsAsJson(portArray);
/*      */       
/* 1183 */       networkDetails.put("portDetails", portJsonString);
/* 1184 */       listOfCredentials = request.getParameter("selectedCredentials");
/*      */       
/*      */ 
/* 1187 */       if (discoveryType.equalsIgnoreCase("range")) {
/* 1188 */         String fromAddress = request.getParameter("fromAddress");
/* 1189 */         String toAddress = request.getParameter("toAddress");
/* 1190 */         String netMask = request.getParameter("netMask");
/*      */         
/* 1192 */         networkDetails.put("fromAddress", fromAddress);
/* 1193 */         networkDetails.put("toAddress", toAddress);
/* 1194 */         networkDetails.put("discoveryType", "range");
/* 1195 */         networkDetails.put("netMask", netMask);
/*      */       }
/* 1197 */       else if (discoveryType.equalsIgnoreCase("cidr")) {
/* 1198 */         String netWorkAddress = request.getParameter("netWorkAddress");
/* 1199 */         String cidr = request.getParameter("cidr");
/* 1200 */         networkDetails.put("discoveryType", "cidr");
/* 1201 */         networkDetails.put("netWorkAddress", netWorkAddress);
/* 1202 */         networkDetails.put("cidr", cidr);
/*      */       }
/* 1204 */       else if (discoveryType.equalsIgnoreCase("virtual")) {
/* 1205 */         String virtualPassword = request.getParameter("virtualPassword");
/* 1206 */         networkDetails.put("discoveryType", "virtual");
/* 1207 */         networkDetails.put("virtualHost", request.getParameter("virtualHost"));
/* 1208 */         networkDetails.put("virtualPort", request.getParameter("virtualPort"));
/* 1209 */         networkDetails.put("virtualUserName", request.getParameter("virtualUserName"));
/* 1210 */         networkDetails.put("virtualPassword", Coding.convertToNewBase(virtualPassword));
/* 1211 */         networkDetails.put("createMGview", request.getParameter("createMGview"));
/* 1212 */         networkDetails.put("netMask", request.getParameter("netMask"));
/*      */       }
/*      */       
/*      */ 
/* 1216 */       networkDetails.put("isAllCred", isAllCred);
/* 1217 */       networkDetails.put("credentialsSelected", listOfCredentials);
/* 1218 */       networkDetails.put("discoveryName", discoveryName);
/*      */       
/* 1220 */       networkDetails.put("discoveryCateg", discoveryCateg);
/*      */       
/* 1222 */       String discID = "";
/* 1223 */       if ("edit".equalsIgnoreCase(discoveryCateg))
/*      */       {
/* 1225 */         discID = request.getParameter("discoveryID");
/* 1226 */         networkDetails.put("discoveryID", discID);
/*      */       }
/* 1228 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/* 1230 */         String masID = request.getParameter("masID");
/* 1231 */         Map<String, String> masHostPort = this.discoveryUtil.getMasDetailsForURL(Integer.parseInt(masID));
/* 1232 */         String masHost = (String)masHostPort.get("HOST");
/* 1233 */         String masPort = (String)masHostPort.get("PORT");
/* 1234 */         String masAPIKey = (String)masHostPort.get("APIKEY");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1240 */         networkDetails.put("masId", masID);
/* 1241 */         networkDetails.put("masHost", masHost);
/* 1242 */         networkDetails.put("masPort", masPort);
/* 1243 */         networkDetails.put("apikey", masAPIKey);
/*      */         
/* 1245 */         String url = this.discoveryUtil.frameURLForStartDiscovery(masHost, masPort);
/*      */         try {
/* 1247 */           networkDetails.put("TRIGGEREDFROM", "Admin Server");
/* 1248 */           String id = EnterpriseUtil.sendRequestAndGetResponse(url, networkDetails);
/* 1249 */           AMLog.logDiscoveryInfo("Received Discovery ID<Discovery/Edit>:" + id);
/* 1250 */           response.setContentType("text/html; charset=UTF-8");
/* 1251 */           PrintWriter out = response.getWriter();
/* 1252 */           out.println(id + "");
/* 1253 */           out.flush();
/*      */         }
/*      */         catch (Exception e) {
/* 1256 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1261 */         if (!discID.isEmpty())
/*      */         {
/*      */ 
/* 1264 */           boolean isRunning = this.discoveryUtil.isDiscoveryInProgress(Long.parseLong(discID));
/* 1265 */           if (isRunning)
/*      */           {
/* 1267 */             response.setContentType("text/html; charset=UTF-8");
/* 1268 */             PrintWriter out = response.getWriter();
/* 1269 */             out.println("0");
/* 1270 */             out.flush();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1276 */         networkDetails.put("TRIGGEREDFROM", EnterpriseUtil.getDistributedStartResourceId().toString());
/* 1277 */         long discoveryID = this.discoveryUtil.insertDiscoveryDetailsToDB(networkDetails);
/* 1278 */         if (discoveryID != -1L)
/*      */         {
/* 1280 */           callStartDiscovery(networkDetails, discoveryID, response);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */       try
/*      */       {
/* 1289 */         response.setContentType("text/html; charset=UTF-8");
/* 1290 */         PrintWriter out = response.getWriter();
/* 1291 */         out.println("-1");
/* 1292 */         out.flush();
/*      */       }
/*      */       catch (Exception ex1) {}
/*      */       
/*      */ 
/*      */ 
/* 1298 */       ex.printStackTrace();
/*      */     }
/* 1300 */     return null;
/*      */   }
/*      */   
/*      */   public void deleteDiscoveryDetails(Long discoveryID, HttpServletResponse response)
/*      */   {
/*      */     try {
/* 1306 */       boolean isDeleted = this.discoveryUtil.deleteDiscoveryDetails(discoveryID.longValue());
/* 1307 */       response.setContentType("text/html; charset=UTF-8");
/* 1308 */       PrintWriter out = response.getWriter();
/* 1309 */       out.println(String.valueOf(isDeleted));
/* 1310 */       out.flush();
/*      */     }
/*      */     catch (IOException e) {
/* 1313 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void disableScheduleDiscovery(Long discoveryID, JSONObject scheduleTimeDetails, HttpServletResponse response)
/*      */   {
/*      */     try {
/* 1320 */       String toRet = "";
/* 1321 */       boolean isDisabled = this.discoveryUtil.disableScheduleDiscovery(discoveryID.longValue(), scheduleTimeDetails);
/* 1322 */       if (isDisabled)
/*      */       {
/* 1324 */         toRet = FormatUtil.getString("am.webclient.discovery.disabled.managedserver");
/*      */       }
/*      */       else
/*      */       {
/* 1328 */         toRet = FormatUtil.getString("am.discovery.details.schedule.error");
/*      */       }
/* 1330 */       response.setContentType("text/html; charset=UTF-8");
/* 1331 */       PrintWriter out = response.getWriter();
/* 1332 */       out.println(toRet);
/* 1333 */       out.flush();
/*      */     }
/*      */     catch (IOException e) {
/* 1336 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void ScheduleDiscovery(JSONObject scheduleTimeDetails, HttpServletResponse response) {
/*      */     try {
/* 1342 */       boolean isScheduled = false;
/* 1343 */       long scheduledTimeMsec = -1L;
/* 1344 */       String toRet = "";
/* 1345 */       long discoveryID = -1L;
/*      */       try
/*      */       {
/* 1348 */         discoveryID = scheduleTimeDetails.getLong("discoveryID");
/* 1349 */         scheduledTimeMsec = scheduleTimeDetails.getLong("scheduledTime");
/* 1350 */         this.discoveryUtil.updateScheduleDetails(discoveryID, scheduleTimeDetails);
/* 1351 */         isScheduled = this.discoveryUtil.updateScheduleDiscoveryTime(discoveryID, scheduledTimeMsec);
/*      */       }
/*      */       catch (JSONException e)
/*      */       {
/* 1355 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1360 */       if (isScheduled)
/*      */       {
/* 1362 */         toRet = FormatUtil.getString("am.webclient.discovery.scheduled.managedserver");
/*      */       }
/*      */       else
/*      */       {
/* 1366 */         toRet = FormatUtil.getString("am.discovery.details.schedule.error");
/*      */       }
/*      */       
/* 1369 */       response.setContentType("text/html; charset=UTF-8");
/* 1370 */       PrintWriter out = response.getWriter();
/* 1371 */       out.println(toRet);
/* 1372 */       out.flush();
/*      */     }
/*      */     catch (IOException e) {
/* 1375 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void getDiscoveryStatusMsgInMAS(JSONObject json, HttpServletResponse response) throws IOException
/*      */   {
/*      */     try {
/* 1382 */       String id = (String)json.get("discoveryID");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1388 */       int noOfAddedApplications = 0;
/* 1389 */       String parentVsChild = null;
/* 1390 */       String servicesList = null;
/* 1391 */       String discoveryCateg = "";
/* 1392 */       String discoveryName = "";
/* 1393 */       String discoveryType = "";
/* 1394 */       int esxCount = 0;
/* 1395 */       int noOfAddedEsxHosts = 0;
/* 1396 */       int reqReceivedForEsx = 0;
/*      */       
/* 1398 */       this.disc = this.discoveryUtil.getInstanceForDiscID(Long.parseLong(id));
/* 1399 */       discoveryType = this.discoveryUtil.getDiscoveryTypeForDiscoveryID(Long.parseLong(id));
/*      */       
/* 1401 */       String logMessage = this.disc.getLogMessage();
/* 1402 */       int totalDeviceCount = this.disc.getTotalDevicesCount();
/* 1403 */       int noOfCompletedRequests = this.disc.getCompletedRequests();
/* 1404 */       int noOfAddedDevices = this.disc.getNoOfAddedDevices();
/* 1405 */       noOfAddedApplications = this.disc.getNoOfAddedApplications();
/*      */       
/* 1407 */       if ("virtual".equalsIgnoreCase(discoveryType))
/*      */       {
/* 1409 */         esxCount = this.disc.getESXCountFromDiscovery();
/* 1410 */         noOfAddedEsxHosts = this.disc.getNoOfAddedESXHosts();
/* 1411 */         reqReceivedForEsx = this.disc.getReqReceivedCountForEsx();
/*      */       }
/*      */       
/*      */ 
/* 1415 */       if (logMessage.equalsIgnoreCase(FormatUtil.getString("am.webclient.discovery.completed")))
/*      */       {
/*      */ 
/* 1418 */         Gson gson = new Gson();
/* 1419 */         parentVsChild = gson.toJson(this.disc.getParentVsChild());
/* 1420 */         AMLog.logDiscoveryInfo("##TEST## parentVsChild:: In MAS::" + parentVsChild);
/*      */         
/*      */ 
/* 1423 */         servicesList = this.disc.getServicesList().toString();
/* 1424 */         discoveryCateg = this.discoveryUtil.getDiscoveryCategForDiscoveryID(Long.parseLong(id));
/* 1425 */         discoveryName = this.discoveryUtil.getDiscoveryNameFromInstanceMap(Long.parseLong(id));
/* 1426 */         this.discoveryUtil.clearIDFromInstanceMap(Long.parseLong(id));
/* 1427 */         this.discoveryUtil.clearSchedulersForDiscoveryId(Long.parseLong(id));
/*      */       }
/* 1429 */       String resStr = logMessage + "#:" + totalDeviceCount + "#:" + noOfCompletedRequests + "#:" + noOfAddedDevices + "#:" + esxCount + "#:" + noOfAddedEsxHosts + "#:" + reqReceivedForEsx + "#:" + discoveryType + "#:" + parentVsChild + "#:" + servicesList + "#:" + noOfAddedApplications + "#:" + discoveryCateg + "#:" + discoveryName;
/*      */       
/* 1431 */       response.setContentType("text/html; charset=UTF-8");
/* 1432 */       PrintWriter out = response.getWriter();
/* 1433 */       out.println(resStr);
/* 1434 */       out.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1438 */       e.printStackTrace();
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
/*      */   public void callStartDiscovery(JSONObject networkDetails, long discoveryID, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1485 */       response.setContentType("text/html; charset=UTF-8");
/* 1486 */       PrintWriter out = response.getWriter();
/* 1487 */       boolean isRunning = this.discoveryUtil.isDiscoveryInProgress(discoveryID);
/*      */       
/* 1489 */       if (isRunning)
/*      */       {
/* 1491 */         AMLog.logDiscoveryInfo("Already Running discoveryID" + discoveryID);
/* 1492 */         out.println("0");
/*      */       }
/*      */       else
/*      */       {
/* 1496 */         StartDiscovery startDiscovery = new StartDiscovery();
/*      */         
/* 1498 */         startDiscovery.networkDetails = networkDetails;
/* 1499 */         startDiscovery.discoveryID = discoveryID;
/* 1500 */         startDiscovery.start();
/* 1501 */         out.println(discoveryID + "");
/*      */       }
/* 1503 */       out.flush();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1519 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public List getCredentialsNotUsedInThisDiscovery(long discoveryID, int userID, boolean isPrivileged)
/*      */   {
/* 1525 */     List credentialList = new ArrayList();
/* 1526 */     Vector credIdList = new Vector();
/* 1527 */     String filtercon = "";
/* 1528 */     if ((userID > 0) && (isPrivileged)) {
/* 1529 */       credIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userID, 4);
/* 1530 */       filtercon = " and " + DBUtil.getCondition("CREDENTIALID", credIdList);
/*      */     }
/* 1532 */     String queryToExecute = "select CREDENTIALID,NAME from CREDENTIALMANAGER where CREDENTIALID not in (select CREDENTIALID from DISCOVERYTOCREDENTIALMAPPING where discoveryid=" + discoveryID + ")";
/* 1533 */     queryToExecute = queryToExecute + filtercon;
/* 1534 */     AMLog.debug("queryToExecute-->old" + queryToExecute);
/* 1535 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1538 */       rs = AMConnectionPool.executeQueryStmt(queryToExecute);
/* 1539 */       while (rs.next())
/*      */       {
/* 1541 */         Properties prop = new Properties();
/* 1542 */         prop.setProperty("credentialID", rs.getInt("CREDENTIALID") + "");
/* 1543 */         prop.setProperty("credentialName", rs.getString("NAME"));
/* 1544 */         credentialList.add(prop);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1549 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1553 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1555 */     return credentialList;
/*      */   }
/*      */   
/*      */   public List getCredentialNamesAndIds(long discoveryID, int userID, boolean isPrivileged)
/*      */   {
/* 1560 */     List credentialList = new ArrayList();
/* 1561 */     Vector credIdList = new Vector();
/* 1562 */     String filtercon = "";
/*      */     
/* 1564 */     String queryToExecute = "select CREDENTIALID,NAME from CREDENTIALMANAGER";
/* 1565 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/* 1569 */       if (discoveryID != -1L)
/*      */       {
/* 1571 */         queryToExecute = "select NAME,CREDENTIALMANAGER.CREDENTIALID from DISCOVERYTOCREDENTIALMAPPING LEFT JOIN CREDENTIALMANAGER on DISCOVERYTOCREDENTIALMAPPING.CREDENTIALID=CREDENTIALMANAGER.CREDENTIALID where DISCOVERYID=" + discoveryID;
/*      */       }
/*      */       
/* 1574 */       if ((userID > 0) && (isPrivileged)) {
/* 1575 */         credIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userID, 4);
/*      */         
/* 1577 */         if (discoveryID == -1L) {
/* 1578 */           filtercon = DBUtil.getCondition("CREDENTIALID", credIdList);
/* 1579 */           queryToExecute = queryToExecute + " where " + filtercon;
/*      */         }
/*      */         else
/*      */         {
/* 1583 */           filtercon = DBUtil.getCondition("CREDENTIALMANAGER.CREDENTIALID", credIdList);
/* 1584 */           queryToExecute = queryToExecute + " and " + filtercon;
/*      */         }
/*      */       }
/* 1587 */       AMLog.debug("queryToExecute-->" + queryToExecute);
/* 1588 */       rs = AMConnectionPool.executeQueryStmt(queryToExecute);
/* 1589 */       while (rs.next())
/*      */       {
/* 1591 */         Properties prop = new Properties();
/* 1592 */         prop.setProperty("credentialID", rs.getInt("CREDENTIALID") + "");
/* 1593 */         prop.setProperty("credentialName", rs.getString("NAME"));
/* 1594 */         credentialList.add(prop);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1599 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1603 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1605 */     return credentialList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public JSONArray getDefaultPortsAsJSONArray(Properties portProps, Properties defaultPorts)
/*      */   {
/* 1612 */     JSONArray portArray = new JSONArray();
/*      */     try
/*      */     {
/* 1615 */       Enumeration portTypes = defaultPorts.keys();
/* 1616 */       List<String> list = Collections.list(portTypes);
/* 1617 */       Collections.sort(list);
/* 1618 */       for (String portName : list)
/*      */       {
/* 1620 */         JSONObject portObject = new JSONObject();
/* 1621 */         if (portProps.containsKey(portName))
/*      */         {
/* 1623 */           String portValue = portProps.getProperty(portName);
/* 1624 */           portValue = portValue.replaceAll("\\s+", "");
/* 1625 */           portObject.put("portName", portName);
/* 1626 */           portObject.put("portValue", portValue);
/* 1627 */           portObject.put("isEnabled", "true");
/*      */         }
/*      */         else
/*      */         {
/* 1631 */           String portValue = defaultPorts.getProperty(portName);
/* 1632 */           portValue = portValue.replaceAll("\\s+", "");
/* 1633 */           portObject.put("portName", portName);
/* 1634 */           portObject.put("portValue", portValue);
/* 1635 */           portObject.put("isEnabled", "false");
/*      */         }
/* 1637 */         portArray.put(portObject);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1642 */       ex.printStackTrace();
/*      */     }
/* 1644 */     return portArray;
/*      */   }
/*      */   
/*      */   public ActionForward getDefaultPorts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1649 */     Properties portProps = new Properties();
/* 1650 */     Properties defaultPorts = new Properties();
/* 1651 */     JSONArray portArray = new JSONArray();
/*      */     try {
/* 1653 */       String discoveryID = "-1";
/* 1654 */       discoveryID = request.getParameter("discoveryID");
/* 1655 */       defaultPorts = this.discoveryUtil.getDefaultPortDetails();
/* 1656 */       if (discoveryID.equals("-1"))
/*      */       {
/* 1658 */         AMLog.debug("New Discovery Ports");
/* 1659 */         portProps.putAll(defaultPorts);
/*      */       }
/*      */       else
/*      */       {
/* 1663 */         portProps = this.discoveryUtil.getPortsForThisDiscoveryID(discoveryID);
/*      */       }
/* 1665 */       portArray = getDefaultPortsAsJSONArray(portProps, defaultPorts);
/* 1666 */       AMLog.logDiscoveryInfo("portArray ====>" + portArray);
/* 1667 */       response.setContentType("text/json;charset=UTF-8");
/* 1668 */       PrintWriter out = response.getWriter();
/* 1669 */       out.println(portArray);
/* 1670 */       out.flush();
/*      */     } catch (Exception ex) {
/* 1672 */       ex.printStackTrace();
/*      */     }
/* 1674 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getScheduleDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1679 */     JSONObject scheduleDetails = new JSONObject();
/*      */     try {
/* 1681 */       String discoveryID = "-1";
/* 1682 */       discoveryID = request.getParameter("discoveryID");
/* 1683 */       scheduleDetails = this.discoveryUtil.getScheduleDetails(Long.parseLong(discoveryID));
/* 1684 */       AMLog.logDiscoveryInfo("scheduleDetails for discoveryID ====>" + discoveryID + ":" + scheduleDetails);
/* 1685 */       response.setContentType("text/json;charset=UTF-8");
/* 1686 */       PrintWriter out = response.getWriter();
/* 1687 */       out.println(scheduleDetails);
/* 1688 */       out.flush();
/*      */     } catch (Exception ex) {
/* 1690 */       ex.printStackTrace();
/*      */     }
/* 1692 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\NewDiscoveryAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */