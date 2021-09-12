/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*     */ import com.adventnet.appmanager.fault.AMThresholdApplier;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMScriptDataCollector;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.DockerActionUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.vserver.VMUtil;
/*     */ import com.manageengine.appmanager.windowsazure.internal.db.WindowsAzureDBUtil;
/*     */ import com.manageengine.eum.docker.datacollection.DockerUtil;
/*     */ import com.manageengine.eum.openstack.datacollection.OpenStackEUMDataCollector;
/*     */ import com.manageengine.eum.url.datacollection.URLDataCollector;
/*     */ import com.me.apm.cluster.windows.util.WindowsClusterUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringReader;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ public final class ConfMonitorAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward changeStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  60 */     String status = request.getParameter("Status");
/*  61 */     String actionResult = "Success";
/*  62 */     String actionMessage = "Action successfully.";
/*  63 */     if ((status != null) && (status.equals("Stopped"))) {
/*  64 */       actionMessage = "Service started successfully.";
/*     */     }
/*  66 */     else if ((status != null) && (status.equals("Running"))) {
/*  67 */       actionMessage = "Service stoped successfully.";
/*     */     }
/*  69 */     returnActionStatus(response, actionResult, actionMessage);
/*  70 */     return null;
/*     */   }
/*     */   
/*  73 */   public void returnActionStatus(HttpServletResponse response, String result, String message) throws Exception { PrintWriter pw = response.getWriter();
/*  74 */     pw.print(result);
/*  75 */     pw.print("|");
/*  76 */     pw.print(FormatUtil.getString(message));
/*     */   }
/*     */   
/*     */   public ActionForward getTabDetailsForAzure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  80 */     try { String resId = request.getParameter("resourceid");
/*  81 */       int resourceID = Integer.parseInt(resId);
/*  82 */       int startIndex = 0;
/*  83 */       int selectedPage = 1;
/*  84 */       int noOfRows = 25;
/*  85 */       int tabId = 1;
/*  86 */       if (request.getParameter("tabId") != null) {
/*  87 */         tabId = Integer.parseInt(request.getParameter("tabId"));
/*     */       }
/*  89 */       response.setContentType("text/html");
/*  90 */       response.setCharacterEncoding("UTF-8");
/*  91 */       String actionPath = (String)request.getAttribute("actionPath");
/*  92 */       request.setAttribute("actionPath", actionPath);
/*  93 */       if (request.getParameter("selectedPage") != null)
/*     */       {
/*  95 */         selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*     */       }
/*  97 */       if (request.getParameter("noOfRows") != null)
/*     */       {
/*  99 */         noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*     */       }
/* 101 */       startIndex = (selectedPage - 1) * noOfRows;
/* 102 */       int totalCount = 0;
/* 103 */       if (tabId == 2)
/*     */       {
/* 105 */         totalCount = WindowsAzureDBUtil.getInstance().getTotalCount("WindowsAzureEventLogs", resourceID);
/* 106 */         ArrayList<Properties> eventLogData = WindowsAzureDBUtil.getInstance().getWindowsAzureEventLogMonitorData(resourceID, startIndex, noOfRows, totalCount);
/* 107 */         request.setAttribute("WindowsAzureEventLogData", eventLogData);
/* 108 */         request.setAttribute("TotalCount", new Integer(totalCount));
/* 109 */         AMLog.debug("Total number of Log Data :" + totalCount);
/* 110 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/WindowsAzureEventLog.jsp");
/* 111 */         rd.include(request, response);
/*     */       }
/* 113 */       else if (tabId == 3)
/*     */       {
/* 115 */         totalCount = WindowsAzureDBUtil.getInstance().getTotalCount("AzureTraceLogs", resourceID);
/* 116 */         ArrayList<Properties> traceLogData = WindowsAzureDBUtil.getInstance().getWindowsAzureTraceLogMonitorData(resourceID, startIndex, noOfRows, totalCount);
/* 117 */         request.setAttribute("WindowsAzureTraceLogData", traceLogData);
/* 118 */         request.setAttribute("TotalCount", new Integer(totalCount));
/* 119 */         AMLog.debug("Total number of Log Data :" + totalCount);
/* 120 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/WindowsAzureTraceLog.jsp");
/* 121 */         rd.include(request, response);
/*     */       }
/* 123 */       else if (tabId == 4)
/*     */       {
/* 125 */         totalCount = WindowsAzureDBUtil.getInstance().getTotalCount("AzureDiagnosticLogs", resourceID);
/* 126 */         ArrayList<Properties> diagnosticLogData = WindowsAzureDBUtil.getInstance().getWindowsAzureDiagnosticInfrastructureLogMonitorData(resourceID, startIndex, noOfRows, totalCount);
/* 127 */         request.setAttribute("WindowsAzureDiagnosticLogData", diagnosticLogData);
/* 128 */         request.setAttribute("TotalCount", new Integer(totalCount));
/* 129 */         AMLog.debug("Total number of Log Data :" + totalCount);
/* 130 */         RequestDispatcher rd = request.getRequestDispatcher("/jsp/WindowsAzureDiagnosticInfrastructureLog.jsp");
/* 131 */         rd.include(request, response);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 138 */       e.printStackTrace();
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getEventLogForWindowsCluster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 145 */     String resId = request.getParameter("resourceid");
/*     */     try
/*     */     {
/* 148 */       PrintWriter out = null;
/*     */       
/* 150 */       AMLog.debug("Getting Windows Cluster Eventlog for " + resId);
/*     */       
/* 152 */       response.setContentType("text/html");
/* 153 */       response.setCharacterEncoding("UTF-8");
/* 154 */       String actionPath = (String)request.getAttribute("actionPath");
/* 155 */       request.setAttribute("actionPath", actionPath);
/*     */       
/* 157 */       JSONObject resultant = new JSONObject();
/* 158 */       JSONArray dataArray = new JSONArray();
/*     */       
/* 160 */       int lengthRet = 10;
/* 161 */       int startRet = 0;
/* 162 */       int drawRet = 0;
/* 163 */       int sortColumn = 0;
/*     */       
/* 165 */       String ruleName = "";
/* 166 */       String logFileType = "";
/* 167 */       String nodeName = "";
/* 168 */       String source = "";
/* 169 */       String eventID = "";
/* 170 */       String type = "";
/* 171 */       String userName = "";
/* 172 */       String description = "";
/* 173 */       String generatedTime = "";
/*     */       
/* 175 */       String sortDir = "desc";
/* 176 */       String start = request.getParameter("start");
/* 177 */       String length = request.getParameter("length");
/* 178 */       String draw = request.getParameter("draw");
/* 179 */       String sCol = request.getParameter("order[0][column]");
/* 180 */       String sdir = request.getParameter("order[0][dir]");
/*     */       
/* 182 */       ruleName = request.getParameter("columns[0][search][value]");
/* 183 */       logFileType = request.getParameter("columns[1][search][value]");
/* 184 */       nodeName = request.getParameter("columns[2][search][value]");
/* 185 */       source = request.getParameter("columns[3][search][value]");
/* 186 */       eventID = request.getParameter("columns[4][search][value]");
/* 187 */       type = request.getParameter("columns[5][search][value]");
/* 188 */       userName = request.getParameter("columns[6][search][value]");
/* 189 */       description = request.getParameter("columns[7][search][value]");
/* 190 */       generatedTime = request.getParameter("columns[8][search][value]");
/*     */       
/* 192 */       StringBuilder columnSearch = new StringBuilder();
/*     */       
/* 194 */       WindowsClusterUtil wcUtil = WindowsClusterUtil.getInstance();
/*     */       
/* 196 */       if (!ruleName.equals(""))
/*     */       {
/* 198 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(0) + " like %" + ruleName + "% ");
/*     */       }
/*     */       
/* 201 */       if (!logFileType.equals(""))
/*     */       {
/* 203 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(1) + " like %" + logFileType + "% ");
/*     */       }
/*     */       
/* 206 */       if (!nodeName.equals(""))
/*     */       {
/* 208 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(2) + " like %" + nodeName + "% ");
/*     */       }
/*     */       
/* 211 */       if (!source.equals(""))
/*     */       {
/* 213 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(3) + " like %" + source + "% ");
/*     */       }
/*     */       
/* 216 */       if (!eventID.equals(""))
/*     */       {
/* 218 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(4) + " like %" + eventID + "% ");
/*     */       }
/*     */       
/* 221 */       if (!type.equals(""))
/*     */       {
/* 223 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(5) + " like %" + type + "% ");
/*     */       }
/*     */       
/* 226 */       if (!userName.equals(""))
/*     */       {
/* 228 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(6) + " like %" + userName + "% ");
/*     */       }
/*     */       
/* 231 */       if (!description.equals(""))
/*     */       {
/* 233 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(7) + " like %" + description + "% ");
/*     */       }
/*     */       
/*     */ 
/* 237 */       if (!generatedTime.equals(""))
/*     */       {
/* 239 */         columnSearch.append(" and " + wcUtil.getEventLogColumnName(8) + " like %" + generatedTime + "% ");
/*     */       }
/*     */       
/*     */ 
/* 243 */       if (start != null) {
/* 244 */         startRet = Integer.parseInt(start);
/* 245 */         if (startRet < 0) {
/* 246 */           startRet = 0;
/*     */         }
/*     */       }
/* 249 */       if (length != null) {
/* 250 */         lengthRet = Integer.parseInt(length);
/* 251 */         if ((lengthRet < 10) || (lengthRet > 100)) {
/* 252 */           lengthRet = 10;
/*     */         }
/*     */       }
/* 255 */       if (draw != null) {
/* 256 */         drawRet = Integer.parseInt(draw);
/*     */       }
/* 258 */       if (sCol != null) {
/* 259 */         sortColumn = Integer.parseInt(sCol);
/* 260 */         if ((sortColumn < 0) || (sortColumn > wcUtil.getEventLogColumnCount() - 1)) {
/* 261 */           sortColumn = 0;
/*     */         }
/*     */       }
/*     */       
/* 265 */       if ((sdir != null) && 
/* 266 */         (sdir.equals("asc")))
/*     */       {
/* 268 */         sortDir = "asc";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 273 */       long lastDCTime = ConfMonitorUtil.getInstance().getLastDCTime(resId);
/*     */       
/* 275 */       int total = wcUtil.getEventLogCountForCluster(resId, lastDCTime);
/*     */       
/* 277 */       int totalAfterFilter = total;
/*     */       
/*     */       try
/*     */       {
/* 281 */         out = response.getWriter();
/* 282 */         String searchStr = request.getParameter("search[value]");
/*     */         
/* 284 */         String orderColumnName = wcUtil.getEventLogColumnName(sortColumn);
/*     */         
/* 286 */         String eventLogQuery = wcUtil.getEventLogQueryForCluster(resId, searchStr, columnSearch.toString(), lastDCTime);
/*     */         
/* 288 */         if ((searchStr != null) && (!"".equals(searchStr)))
/*     */         {
/* 290 */           totalAfterFilter = DBUtil.getCount(DBUtil.getCountQuery(eventLogQuery));
/*     */         }
/*     */         
/* 293 */         dataArray = wcUtil.getEventLogDataForClutser(eventLogQuery, startRet, lengthRet, sortDir, orderColumnName);
/*     */         
/*     */ 
/* 296 */         resultant.put("draw", drawRet);
/* 297 */         resultant.put("recordsTotal", total);
/* 298 */         resultant.put("recordsFiltered", totalAfterFilter);
/* 299 */         resultant.put("data", dataArray);
/* 300 */         response.setContentType("application/json");
/* 301 */         response.setHeader("Cache-Control", "no-store");
/* 302 */         out.println(resultant);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 306 */         resultant.put("draw", drawRet);
/* 307 */         resultant.put("recordsTotal", total);
/* 308 */         resultant.put("recordsFiltered", totalAfterFilter);
/* 309 */         resultant.put("data", dataArray);
/* 310 */         response.setContentType("application/json");
/* 311 */         response.setHeader("Cache-Control", "no-store");
/* 312 */         out.println(resultant);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 318 */       AMLog.debug("Problem when getting the eventlog details for windows cluster " + resId, ex);
/*     */     }
/* 320 */     return null;
/*     */   }
/*     */   
/* 323 */   public ActionForward addContainers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { PrintWriter out = null;
/*     */     try
/*     */     {
/* 326 */       String message = FormatUtil.getString("am.webclient.docker.add.container.msg");
/* 327 */       AMLog.debug("Adding Docker Containers for " + request.getParameter("resourceid"));
/*     */       
/*     */ 
/* 330 */       String[] containerList = request.getParameterValues("containerId");
/* 331 */       String parentId = request.getParameter("resourceId");
/*     */       
/* 333 */       StringBuilder output = new StringBuilder();
/* 334 */       output.append("<--childresource Docker Container starts-->\n");
/* 335 */       output.append("Resource Name|Display Name\n");
/* 336 */       for (int i = 0; i < containerList.length; i++) {
/*     */         try {
/* 338 */           String currentValue = containerList[i];
/* 339 */           int delimiterIndex = currentValue.indexOf("_");
/* 340 */           String resourceName = delimiterIndex != -1 ? currentValue.substring(0, delimiterIndex) : currentValue;
/* 341 */           String displayName = delimiterIndex != -1 ? currentValue.substring(delimiterIndex + 1, currentValue.length()) : resourceName;
/* 342 */           output.append(resourceName).append("|").append(displayName).append("\n");
/*     */         }
/*     */         catch (Exception e) {
/* 345 */           e.printStackTrace();
/* 346 */           AMLog.debug("Could not add Docker Container ::" + containerList[i] + " as caught in exception ::" + e.getMessage());
/*     */         }
/*     */       }
/*     */       
/* 350 */       output.append("\n<--childresource Docker Container ends-->\n");
/*     */       
/* 352 */       StringTokenizer newliner = new StringTokenizer(output.toString(), "\n");
/* 353 */       String currentLine = newliner.nextToken();
/* 354 */       HashMap parentProps = new HashMap();
/* 355 */       parentProps.put("parenttype", "Docker");
/* 356 */       parentProps.put("parentid", Integer.valueOf(Integer.parseInt(parentId)));
/* 357 */       String baseId = Constants.getTypeId("Docker");
/*     */       try {
/* 359 */         parentProps.put("parentbaseid", Integer.valueOf(Integer.parseInt(baseId)));
/*     */       }
/*     */       catch (Exception e) {
/* 362 */         e.printStackTrace();
/* 363 */         parentProps.put("parentbaseid", Integer.valueOf(-1));
/*     */       }
/*     */       
/*     */ 
/* 367 */       AMScriptDataCollector scripDc = new AMScriptDataCollector();
/* 368 */       scripDc.parseAndAddChildResource(newliner, currentLine, parentProps);
/*     */       
/* 370 */       response.setContentType("text/html");
/* 371 */       response.setCharacterEncoding("UTF-8");
/* 372 */       response.setContentType("application/json");
/* 373 */       response.setHeader("Cache-Control", "no-store");
/* 374 */       out = response.getWriter();
/* 375 */       out.println(message);
/*     */     }
/*     */     catch (Exception e) {
/* 378 */       e.printStackTrace();
/*     */     }
/* 380 */     return null;
/*     */   }
/*     */   
/* 383 */   public ActionForward dockerContainerActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { ActionMessages messages = new ActionMessages();
/* 384 */     String parentId = request.getParameter("parentId");
/* 385 */     if ((request.getParameter("resIds") != null) && (request.getParameter("action") != null) && (request.getParameter("resIds").trim() != "") && (request.getParameter("action").trim() != ""))
/*     */     {
/*     */ 
/* 388 */       String action = request.getParameter("action");
/* 389 */       String resIds = request.getParameter("resIds");
/*     */       
/*     */ 
/* 392 */       HashMap<String, HashMap> returnedOutput = DockerActionUtil.triggerAction(resIds, action, parentId);
/* 393 */       HashMap<String, String[]> vmsResults = (HashMap)returnedOutput.get("ResultToClient");
/* 394 */       HashMap<String, Long> availabilityResult = (HashMap)returnedOutput.get("availabilityResult");
/* 395 */       AMThresholdApplier thresholdApplier = new AMThresholdApplier();
/* 396 */       Iterator it = availabilityResult.keySet().iterator();
/* 397 */       while (it.hasNext()) {
/* 398 */         String resourceId = (String)it.next();
/* 399 */         long availability = ((Long)availabilityResult.get(resourceId)).longValue();
/* 400 */         long currentTime = System.currentTimeMillis();
/* 401 */         if ((!DataCollectionControllerUtil.underMaintenance(resourceId, 19500, currentTime)) && (!DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceId))))
/*     */         {
/*     */ 
/* 404 */           thresholdApplier.applyThreshold(new Integer(resourceId).intValue(), 19500, availability, currentTime);
/* 405 */           new AMRCAnalyser().applyRCA(new Integer(resourceId).intValue(), 19501, currentTime);
/*     */         }
/*     */       }
/*     */       
/* 409 */       String setMessage = null;
/* 410 */       request.setAttribute("action", action);
/*     */       
/* 412 */       request.setAttribute("vmsResults", vmsResults);
/* 413 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(setMessage));
/* 414 */       saveMessages(request, messages);
/* 415 */       request.setAttribute("SUCCESS", "true");
/*     */     }
/*     */     else
/*     */     {
/* 419 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.amazon.ec2instances.startstopreboot.default.errtext")));
/* 420 */       saveMessages(request, messages);
/*     */       
/* 422 */       request.setAttribute("SUCCESS", "false");
/*     */     }
/*     */     
/*     */ 
/* 426 */     return mapping.findForward("DockerAction");
/*     */   }
/*     */   
/*     */   public ActionForward executeDockerTestAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 430 */     ActionMessages messages = new ActionMessages();
/* 431 */     ActionErrors errors = new ActionErrors();
/* 432 */     String actionId = request.getParameter("actionID");
/* 433 */     String executeVMActions = request.getParameter("executeVMActions");
/* 434 */     String message = FormatUtil.getString("am.vm.testaction.success");
/* 435 */     boolean success = true;
/*     */     try {
/* 437 */       if (Boolean.valueOf(executeVMActions).booleanValue()) {
/* 438 */         DockerActionUtil.triggerAction(Integer.parseInt(actionId), null);
/*     */       }
/*     */       else {
/* 441 */         HashMap<String, String> result = DockerActionUtil.triggerAction(Integer.parseInt(actionId));
/* 442 */         success = Boolean.valueOf((String)result.get("success")).booleanValue();
/* 443 */         message = (String)result.get("message");
/*     */       }
/*     */       
/* 446 */       request.setAttribute("message", message);
/* 447 */       if (success) {
/* 448 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.vm.testaction.success")));
/* 449 */         saveMessages(request, messages);
/*     */       } else {
/* 451 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", message));
/* 452 */         saveErrors(request, errors);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 456 */       e.printStackTrace();
/*     */     }
/*     */     
/* 459 */     return mapping.findForward("TestDockerAction");
/*     */   }
/*     */   
/*     */   public ActionForward testDockerContainerActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 464 */     String actionId = request.getParameter("actionID");
/* 465 */     if (actionId != null) {
/* 466 */       request.setAttribute("resourceType", "Docker");
/* 467 */       request.setAttribute("actionId", actionId);
/* 468 */       String actionAndMail = "";
/* 469 */       String belowInstances = "";
/*     */       try {
/* 471 */         ArrayList<String> containerList = DockerActionUtil.getContainerListForTestAction(Integer.parseInt(actionId));
/* 472 */         if ((containerList != null) && (!containerList.isEmpty())) {
/* 473 */           request.setAttribute("vmList", containerList);
/*     */         }
/* 475 */         String[] actionDetails = VMUtil.getActionDetails(Integer.parseInt(actionId));
/* 476 */         Integer actionType = Integer.valueOf(new Integer(actionDetails[3]).intValue());
/* 477 */         String action = 851 == actionType.intValue() ? "am.docker.container.action.stop" : 850 == actionType.intValue() ? "am.docker.container.action.start" : "am.docker.container.action.restart";
/* 478 */         actionAndMail = FormatUtil.getString("am.vm.test.actionandmail", new String[] { FormatUtil.getString(action) });
/* 479 */         String actionText = 851 == actionType.intValue() ? "am.vm.action.stopped" : 850 == actionType.intValue() ? "am.vm.action.started" : "am.vm.action.restarted";
/* 480 */         belowInstances = FormatUtil.getString("am.docker.action.belowContainers", new String[] { FormatUtil.getString(actionText) });
/*     */       }
/*     */       catch (Exception e) {
/* 483 */         e.printStackTrace();
/*     */       }
/*     */       
/* 486 */       request.setAttribute("actionAndMail", actionAndMail);
/* 487 */       request.setAttribute("belowInstances", belowInstances);
/*     */     }
/*     */     
/* 490 */     return mapping.findForward("TestDockerAction");
/*     */   }
/*     */   
/* 493 */   public ActionForward getDockerContainers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { PrintWriter out = null;
/*     */     
/*     */     try
/*     */     {
/* 497 */       AMLog.debug("Getting Docker Containers for " + request.getParameter("resourceid"));
/*     */       
/*     */ 
/* 500 */       response.setCharacterEncoding("UTF-8");
/*     */       
/* 502 */       JSONObject resultant = new JSONObject();
/* 503 */       JSONArray dataArray = new JSONArray();
/* 504 */       String parentId = request.getParameter("resourceid");
/* 505 */       Properties props = NewMonitorUtil.getArgsasProps("Docker", parentId);
/* 506 */       Hashtable args = new Hashtable();
/* 507 */       args.putAll(props);
/* 508 */       boolean allContainers = request.getParameter("All") != null;
/* 509 */       if (props != null) {}
/*     */       
/*     */ 
/* 512 */       response.setContentType("application/json");
/* 513 */       ArrayList alreadyAddedContainersList = DBUtil.getRowsForSingleColumn("select RESOURCENAME from AM_ManagedObject join AM_PARENTCHILDMAPPER on CHILDID=RESOURCEID where parentid=" + parentId + " and type='Docker Container'");
/* 514 */       dataArray = DockerUtil.getContainersList(args, allContainers, alreadyAddedContainersList);
/* 515 */       resultant.put("data", dataArray);
/* 516 */       response.setHeader("Cache-Control", "no-store");
/* 517 */       out = response.getWriter();
/* 518 */       out.println(resultant);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 522 */       e.printStackTrace();
/*     */     }
/* 524 */     return null;
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
/*     */   public ActionForward getDataTablesLanguageSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 544 */       response.setContentType("application/json; charset=UTF-8");
/* 545 */       response.setHeader("Cache-Control", "no-store");
/* 546 */       PrintWriter out = response.getWriter();
/* 547 */       JSONObject datatablesLang = OEMUtil.getDataTablesLanguageSettings();
/* 548 */       datatablesLang.put("emptyTable", FormatUtil.getString("am.add.container.datatables.emptyTable"));
/* 549 */       out.println(datatablesLang);
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 554 */       AMLog.info("Exception while getting the Datatables Language Details", ex);
/*     */     }
/*     */     
/* 557 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward openStackInstancesAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 562 */     ActionMessages actionMessage = new ActionMessages();
/*     */     try
/*     */     {
/* 565 */       ArrayList<String> listOfInstanceIDs = new ArrayList();
/*     */       
/* 567 */       URLDataCollector urlDataCollector = new URLDataCollector();
/*     */       
/* 569 */       String instanceIDs = request.getParameter("rowIds");
/* 570 */       String typeID = request.getParameter("typeId");
/* 571 */       String action = request.getParameter("action");
/*     */       String resourceID;
/* 573 */       if (isNumeric(request.getParameter("resourceId"))) {
/* 574 */         resourceID = request.getParameter("resourceId");
/*     */       } else {
/* 576 */         request.setAttribute("SUCCESS", "false");
/* 577 */         return mapping.findForward("OpenStackAction"); }
/*     */       String resourceID;
/* 579 */       JSONObject bodyContent = getJSONBodyContent(action);
/* 580 */       StringTokenizer commaSeperatedIDs = new StringTokenizer(instanceIDs, ",");
/* 581 */       if (commaSeperatedIDs.countTokens() > 0) {
/* 582 */         while (commaSeperatedIDs.hasMoreTokens()) {
/* 583 */           listOfInstanceIDs.add(commaSeperatedIDs.nextToken());
/*     */         }
/*     */       }
/* 586 */       StringBuilder keysQuery = new StringBuilder().append("select *,").append(DBQueryUtil.decode("PASSWORD")).append(" as ORG_PASSWORD from AM_ARGS_").append(typeID).append(" where RESOURCEID = ").append(resourceID);
/*     */       
/* 588 */       Properties openStackCredentials = new Properties();
/* 589 */       ResultSet rs = null;
/*     */       try {
/* 591 */         rs = AMConnectionPool.executeQueryStmt(keysQuery.toString());
/* 592 */         if ((rs != null) && (rs.next()))
/*     */         {
/* 594 */           openStackCredentials.setProperty("resourceid", rs.getString("RESOURCEID"));
/* 595 */           openStackCredentials.setProperty("baseAuthURL", rs.getString("BASEAUTHURL"));
/* 596 */           openStackCredentials.setProperty("tenantName", rs.getString("TENANTNAME"));
/* 597 */           openStackCredentials.setProperty("credentialDetails", rs.getString("CREDENTIALDETAILS"));
/* 598 */           openStackCredentials.setProperty("cmValue", rs.getString("CMVALUE"));
/* 599 */           openStackCredentials.setProperty("username", rs.getString("USERNAME"));
/* 600 */           openStackCredentials.setProperty("isProxyNeeded", rs.getString("ISPROXYNEEDED"));
/*     */           
/* 602 */           openStackCredentials.setProperty("password", rs.getString("ORG_PASSWORD"));
/*     */         }
/*     */       } catch (SQLException e) {
/* 605 */         e.printStackTrace();
/*     */       } finally {
/* 607 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 609 */       OpenStackEUMDataCollector openStackEUMDataCollector = new OpenStackEUMDataCollector();
/* 610 */       openStackEUMDataCollector.checkAuthentication(openStackCredentials);
/* 611 */       String computeURL = openStackCredentials.getProperty("compute");
/* 612 */       HashMap<String, String[]> vmsResult = new HashMap();
/* 613 */       if (computeURL != null) {
/* 614 */         for (String instanceID : listOfInstanceIDs) {
/*     */           try {
/* 616 */             String resourceName = DBUtil.getResourceNameFromId(instanceID);
/* 617 */             String displayName = DBUtil.getDisplaynameforResourceID(instanceID);
/* 618 */             String[] strs = resourceName != null ? resourceName.split("_") : new String[0];
/* 619 */             String serverID = strs[1];
/* 620 */             openStackCredentials.setProperty("requestBodyContent", bodyContent != null ? bodyContent.toString() : null);
/* 621 */             openStackCredentials.setProperty("URL", computeURL + "/servers/" + serverID + "/action");
/* 622 */             openStackCredentials.setProperty("httpMethod", "J");
/* 623 */             openStackCredentials.setProperty("headerNames", "X-Auth-Token");
/* 624 */             openStackCredentials.setProperty("headerValues", openStackCredentials.getProperty("tokenID"));
/*     */             
/* 626 */             HashMap hm = urlDataCollector.startDatacollection(openStackEUMDataCollector.setProperties(openStackCredentials));
/* 627 */             String xmlResponse = (String)hm.get("xmlResponse");
/* 628 */             String webContent = (String)hm.get("htmlresponse");
/* 629 */             AMLog.debug("OpenStack | Action | xmlResponse : " + xmlResponse);
/* 630 */             AMLog.debug("OpenStack | Action | WebContent : " + webContent);
/* 631 */             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 632 */             DocumentBuilder builder = factory.newDocumentBuilder();
/* 633 */             InputSource is = new InputSource(new StringReader(xmlResponse));
/* 634 */             Document doc = builder.parse(is);
/* 635 */             int responseCode = Integer.parseInt(doc.getDocumentElement().getAttribute("responseCode"));
/*     */             
/* 637 */             if (responseCode < 400) {
/* 638 */               vmsResult.put(displayName, new String[] { FormatUtil.getString("Success"), FormatUtil.getString("am.webclient.openstack.instance." + action + ".success") });
/* 639 */               request.setAttribute("SUCCESS", "true");
/*     */             }
/*     */             else {
/* 642 */               vmsResult.put(displayName, new String[] { FormatUtil.getString("Success"), openStackEUMDataCollector.getErrMsg(webContent) });
/* 643 */               request.setAttribute("SUCCESS", "false");
/*     */             }
/* 645 */             actionMessage.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(null));
/* 646 */             saveMessages(request, actionMessage);
/* 647 */             request.setAttribute("action", action);
/* 648 */             request.setAttribute("vmsResults", vmsResult);
/*     */           }
/*     */           catch (Exception e) {
/* 651 */             AMLog.debug("OpenStack | " + instanceID + " | Action | Error : " + e.getMessage());
/* 652 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       } else {
/* 656 */         actionMessage.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.openstack.info1")));
/* 657 */         saveMessages(request, actionMessage);
/* 658 */         AMLog.debug("OpenStack | Action | Error : " + actionMessage);
/* 659 */         request.setAttribute("action", action);
/* 660 */         request.setAttribute("SUCCESS", "false");
/* 661 */         vmsResult.put("", new String[] { FormatUtil.getString("Failure"), FormatUtil.getString(openStackCredentials.getProperty("error")) });
/* 662 */         request.setAttribute("vmsResults", vmsResult);
/*     */       }
/*     */     } catch (Exception e) {
/* 665 */       e.printStackTrace();
/*     */     }
/* 667 */     return mapping.findForward("OpenStackAction");
/*     */   }
/*     */   
/*     */   private JSONObject getJSONBodyContent(String action)
/*     */   {
/* 672 */     JSONObject jsonBodyContent = new JSONObject();
/* 673 */     JSONObject actionType = new JSONObject();
/*     */     try {
/* 675 */       actionType.put("type", "SOFT");
/* 676 */       if ("reboot".equalsIgnoreCase(action)) {
/* 677 */         jsonBodyContent.put(action, actionType);
/*     */       } else {
/* 679 */         jsonBodyContent.put(action, "null");
/*     */       }
/*     */     } catch (JSONException e) {
/* 682 */       e.printStackTrace();
/*     */     } catch (Exception e) {
/* 684 */       e.printStackTrace();
/*     */     }
/*     */     
/* 687 */     return jsonBodyContent;
/*     */   }
/*     */   
/*     */   public static boolean isNumeric(String str) {
/*     */     try {
/* 692 */       d = Double.parseDouble(str);
/*     */     } catch (NumberFormatException nfe) { double d;
/* 694 */       return false;
/*     */     }
/* 696 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ConfMonitorAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */