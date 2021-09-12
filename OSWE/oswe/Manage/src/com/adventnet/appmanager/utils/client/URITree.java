/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.datacorrection.AMDataCorrectionUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*      */ import com.adventnet.appmanager.struts.actions.AdminTools;
/*      */ import com.adventnet.appmanager.struts.actions.DataCollectionController;
/*      */ import com.adventnet.appmanager.struts.actions.MyPageAction;
/*      */ import com.adventnet.appmanager.struts.actions.RegisterAction;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.AppManagerPing;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtConnectorUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MGUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.OPMAlarmOperationsHandler;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.PingTestConstants;
/*      */ import com.adventnet.appmanager.util.RealBrowserUtil;
/*      */ import com.adventnet.appmanager.webclient.fault.alarm.AlarmOperationsDispatchAction;
/*      */ import com.adventnet.nms.webclient.fault.alarm.AlarmOperationsUtility;
/*      */ import com.adventnet.tools.prevalent.Wield;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.client.selenium.servlets.CreateRealBrowserTest;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.apache.commons.fileupload.MultipartStream;
/*      */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONObject;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Text;
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
/*      */ public class URITree
/*      */ {
/*      */   private static final String TYPE_HPUX = "HP-UX,HP-TRU64,HP-UX / Tru64";
/*      */   private static final String TYPE_FREEBSD = "FreeBSD,OpenBSD,FreeBSD / OpenBSD";
/*      */   private static final String TYPE_SUNSOLARIS = "SUN,SUN PC,Sun Solaris";
/*      */   private static final String TYPE_VIRTUALMACHINE = "VirtualMachine,HyperVVirtualMachine,XenServerVM";
/*      */   private static final String TYPE_CONTAINER = "Container,Docker Container";
/*      */   private static final String TYPE_FILESYSTEM = "File System Monitor,file,directory";
/*  127 */   public static HashMap<String, String> categMap = loadCategMap();
/*      */   
/*      */ 
/*      */ 
/*      */   public static String generateXML(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  134 */     return generateXML(request, response, false);
/*      */   }
/*      */   
/*      */   public static HashMap<String, String> loadCategMap() {
/*  138 */     HashMap<String, String> hMap = new HashMap();
/*  139 */     String eumApps = Constants.resourceTypesEUM.substring(1, Constants.resourceTypesEUM.length() - 2).replaceAll("'", "");
/*  140 */     ResultSet rs = null;
/*      */     try {
/*  142 */       String categQuery = "select RESOURCETYPE, RESOURCEGROUP from AM_ManagedResourceType order by RESOURCEGROUP";
/*  143 */       rs = AMConnectionPool.executeQueryStmt(categQuery);
/*  144 */       while (rs.next()) {
/*  145 */         String resGroup = rs.getString("RESOURCEGROUP");
/*  146 */         String resType = rs.getString("RESOURCETYPE");
/*  147 */         if (hMap.containsKey(resGroup)) {
/*  148 */           String resTypes = ((String)hMap.get(resGroup)).trim();
/*  149 */           if (!resTypes.contains(resType)) {
/*  150 */             resTypes = resTypes + (resTypes.length() > 0 ? "," + resType : resType);
/*      */           }
/*  152 */           hMap.put(resGroup, resTypes);
/*      */         }
/*      */         else {
/*  155 */           hMap.put(resGroup, resType);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  160 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  163 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  165 */     hMap.put("EUM", eumApps);
/*  166 */     AMLog.debug("URITree: loading Categories Map:" + hMap);
/*  167 */     return hMap;
/*      */   }
/*      */   
/*      */   public static String generateXML(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/*  172 */     AMLog.debug("REST API : inside URITree");
/*  173 */     String message = "";
/*  174 */     String errorCode = "";
/*  175 */     String outputString = "";
/*  176 */     String uri = request.getRequestURI();
/*  177 */     String[] nodes = uri.split("/");
/*      */     
/*  179 */     String methodName = nodes[3];
/*  180 */     String monitorName = request.getParameter("monitorname");
/*  181 */     String colTime = request.getParameter("time");
/*  182 */     String topN = request.getParameter("topN");
/*  183 */     String resId = request.getParameter("resourceid");
/*  184 */     String HAid = request.getParameter("haid");
/*      */     
/*  186 */     if (methodName == null)
/*      */     {
/*  188 */       AMLog.debug("REST API : The specified request URI is incorrect:the method specified is wrong");
/*  189 */       message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/*  190 */       errorCode = "4016";
/*  191 */       return generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/*  194 */     AMLog.debug("REST API : API CALL Method:" + methodName);
/*      */     
/*      */ 
/*      */ 
/*  198 */     if ((resId != null) && (!isValidResourceId(resId)) && (!"getMonitorGroupAvailability".equals(methodName)))
/*      */     {
/*  200 */       AMLog.debug("REST API : The specified request URI is incorrect:the resourceid specified is wrong");
/*  201 */       message = FormatUtil.getString("am.webclient.apikey.wrongresidparam.message");
/*  202 */       errorCode = "4002";
/*  203 */       outputString = generateXML(request, response, message, errorCode);
/*  204 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  210 */     if (methodName.equals("ApplyLicense"))
/*      */     {
/*  212 */       outputString = ApplyLicense(request, response, isJsonFormat);
/*      */     }
/*  214 */     else if (methodName.equals("LicenseInfo"))
/*      */     {
/*  216 */       outputString = LicenseInfoXML(request, response);
/*      */     }
/*  218 */     else if (methodName.equals("getMonitorGroupAvailability"))
/*      */     {
/*  220 */       outputString = getMonitorGroupAvailability(request);
/*      */     }
/*  222 */     else if (methodName.equals("ListDashboards"))
/*      */     {
/*  224 */       outputString = ListDashboards(request, response, isJsonFormat);
/*      */     }
/*  226 */     else if (methodName.equals("ListActions"))
/*      */     {
/*  228 */       outputString = ListActions(request, response, isJsonFormat);
/*      */     }
/*  230 */     else if (methodName.equals("ListAgents"))
/*      */     {
/*  232 */       outputString = ListAgents(request, response, isJsonFormat);
/*      */     }
/*  234 */     else if (methodName.equals("AlarmAction"))
/*      */     {
/*  236 */       outputString = executeAlarmAction(request, response, isJsonFormat);
/*      */     }
/*  238 */     else if ((methodName.equals("ManageMonitor")) || (methodName.equals("UnmanageMonitor")) || (methodName.equals("UnmanageAndResetMonitor")))
/*      */     {
/*  240 */       if (resId == null)
/*      */       {
/*      */         try
/*      */         {
/*  244 */           String ipaddress = request.getParameter("IP");
/*  245 */           String port = request.getParameter("PORTNO");
/*  246 */           String type = request.getParameter("TYPE");
/*  247 */           resId = DBUtil.getResourceidForIP(ipaddress, port, type);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  251 */           e.printStackTrace();
/*      */         }
/*      */       }
/*  254 */       if ((HAid == null) && (resId == null))
/*      */       {
/*  256 */         AMLog.debug("REST API : The ResourceID in request URI is null or empty.");
/*  257 */         message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/*  258 */         errorCode = "4032";
/*  259 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */       else
/*      */       {
/*  263 */         if (CommonAPIUtil.checkResourceidforDelegatedAdmin(request, resId)) {
/*  264 */           outputString = generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/*  265 */           return outputString;
/*      */         }
/*  267 */         if (!EnterpriseUtil.isAdminServer())
/*      */         {
/*  269 */           boolean allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOperatorManage");
/*  270 */           if (((CommonAPIUtil.isOperatorRole(request)) && (allowManage)) || (CommonAPIUtil.isAdminRole(request)))
/*      */           {
/*  272 */             outputString = ManageUnmanageXML(request, response, methodName, resId);
/*      */           }
/*      */           else
/*      */           {
/*  276 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  281 */           message = FormatUtil.getString("am.webclient.apikey.adminapi.message");
/*  282 */           errorCode = "4540";
/*  283 */           outputString = generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*      */     }
/*  287 */     else if (methodName.equals("Ping"))
/*      */     {
/*  289 */       String host = request.getParameter("host");
/*  290 */       AMLog.info("RESTAPI: Ping resourceid:" + resId);
/*  291 */       if ((resId != null) || (host != null))
/*      */       {
/*  293 */         outputString = pingResultXML(request, response, resId, host, isJsonFormat);
/*      */       }
/*      */       else
/*      */       {
/*  297 */         AMLog.debug("REST API : The ResourceID in request URI is null or empty.");
/*  298 */         message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/*  299 */         errorCode = "4032";
/*  300 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/*  303 */     else if (methodName.equals("ListMonitor"))
/*      */     {
/*      */       try
/*      */       {
/*  307 */         outputString = getMonitorsList(request, response, isJsonFormat);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  311 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  314 */     else if (methodName.equals("ListMonitorTypes"))
/*      */     {
/*  316 */       AMLog.info("Inside list monitor types..");
/*      */       try
/*      */       {
/*  319 */         outputString = getMonitorTypesList(request, response, isJsonFormat);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  323 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  326 */     else if (methodName.equals("ListAlarms"))
/*      */     {
/*      */       try
/*      */       {
/*  330 */         outputString = getAlarmsList(request, response, monitorName, colTime, topN, resId);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  334 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  337 */     else if (methodName.equals("ListServer"))
/*      */     {
/*      */       try
/*      */       {
/*  341 */         String ipaddress = request.getParameter("ipaddress");
/*  342 */         outputString = getServerServicesList(request, response, ipaddress);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  346 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  349 */     else if (methodName.equals("ExecuteAction"))
/*      */     {
/*  351 */       String actionId = request.getParameter("ActionId");
/*  352 */       AMLog.info("RESTAPI: ActionID:" + actionId);
/*      */       try
/*      */       {
/*  355 */         if (actionId != null)
/*      */         {
/*  357 */           Integer.parseInt(actionId);
/*  358 */           outputString = executeActionXML(request, response, actionId, isJsonFormat);
/*      */         }
/*      */         else
/*      */         {
/*  362 */           AMLog.debug("REST API : The ActionID param in request URI is null or empty.");
/*  363 */           message = FormatUtil.getString("am.webclient.apikey.wrongparam.message");
/*  364 */           errorCode = "4032";
/*  365 */           outputString = generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  370 */         AMLog.debug("REST API : The ActionID value is not a valid one.");
/*  371 */         message = FormatUtil.getString("am.webclient.apikey.validparam.message");
/*  372 */         errorCode = "4032";
/*  373 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/*  376 */     else if (methodName.equals("GetDowntimeDetails"))
/*      */     {
/*  378 */       String type = request.getParameter("type");
/*  379 */       AMLog.info("RESTAPI: type:" + type + " resId:" + resId);
/*      */       try
/*      */       {
/*  382 */         if (resId != null)
/*      */         {
/*  384 */           Integer.parseInt(resId);
/*  385 */           outputString = getDowntimeDetails(request, response, type, resId, isJsonFormat);
/*      */         }
/*  387 */         else if (type != null)
/*      */         {
/*  389 */           if (type.equals("monitors"))
/*      */           {
/*  391 */             outputString = getDowntimeDetails(request, response, type, resId, isJsonFormat);
/*      */           }
/*  393 */           else if (type.equals("HAI"))
/*      */           {
/*  395 */             outputString = getDowntimeDetails(request, response, type, resId, isJsonFormat);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  400 */           AMLog.debug("REST API : The ActionID param in request URI is null or empty.");
/*  401 */           message = FormatUtil.getString("am.webclient.apikey.wrongparam.message");
/*  402 */           errorCode = "4032";
/*  403 */           outputString = generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  408 */         AMLog.debug("REST API : The ActionID value is not a valid one.");
/*  409 */         message = FormatUtil.getString("am.webclient.apikey.validparam.message");
/*  410 */         errorCode = "4032";
/*  411 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/*  414 */     else if (methodName.equals("getAllCustomerInfoOnly"))
/*      */     {
/*      */       try
/*      */       {
/*  418 */         outputString = CustomerManagementAPI.getAllCustomersWithSitesAndBSGs(request, response, true, true);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  422 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  425 */     else if (methodName.equals("getAllCustomersWithSitesAndBSGs"))
/*      */     {
/*      */       try
/*      */       {
/*  429 */         outputString = CustomerManagementAPI.getAllCustomersWithSitesAndBSGs(request, response, true, false);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  433 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*  436 */     else if (methodName.equals("getSingleCustomersWithSitesAndBSGs"))
/*      */     {
/*      */       try
/*      */       {
/*  440 */         outputString = CustomerManagementAPI.getSingleCustomersWithSitesAndBSGs(request, response, true);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  444 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  449 */       AMLog.debug("REST API : The specified request URI is incorrect: the method specified is wrong");
/*  450 */       message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/*  451 */       errorCode = "4016";
/*  452 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*  454 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String getDowntimeDetails(HttpServletRequest request, HttpServletResponse response, String type, String resId, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/*  460 */     String reasonCode = "4000";
/*  461 */     String outputString = "";
/*  462 */     String condition = "";
/*  463 */     String operatorCondition = CommonAPIUtil.getConditionForOperator(request);
/*      */     try
/*      */     {
/*  466 */       ArrayList<Hashtable> downtimeDetails = getDowntimeDetails(request);
/*  467 */       String subNodes = request.getAttribute("downtimeSummary") != null ? "Downtimes,DowntimeSummary" : "Downtimes";
/*  468 */       AMLog.info("REST API: DowntimeData==>subnode:" + request.getAttribute("downtimeSummary"));
/*  469 */       HashMap dataMap = new HashMap();
/*  470 */       dataMap.put("response-code", reasonCode);
/*  471 */       dataMap.put("uri", request.getRequestURI());
/*  472 */       dataMap.put("result", downtimeDetails);
/*  473 */       dataMap.put("sortingParam", "DisplayName");
/*  474 */       dataMap.put("sortingOrder", "desc");
/*  475 */       dataMap.put("parentNode", "Downtimes");
/*  476 */       dataMap.put("nodeName", "Monitor");
/*  477 */       dataMap.put("subNodeName", subNodes);
/*      */       
/*  479 */       outputString = CommonAPIUtil.getOutputAsString(dataMap, isJsonFormat);
/*      */     }
/*      */     catch (NullPointerException ne) {
/*  482 */       ne.printStackTrace();
/*  483 */       message = FormatUtil.getString("am.webclient.nodata.text");
/*  484 */       reasonCode = "4009";
/*  485 */       outputString = generateXML(request, response, message, reasonCode);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  489 */       e.printStackTrace();
/*  490 */       AMLog.debug("REST API : The specified request URI is incorrect: the method specified is wrong");
/*  491 */       String message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/*  492 */       reasonCode = "4016";
/*  493 */       outputString = generateXML(request, response, message, reasonCode);
/*      */     }
/*  495 */     return outputString;
/*      */   }
/*      */   
/*      */   private static ArrayList<Hashtable> getDowntimeDetails(HttpServletRequest request)
/*      */   {
/*  500 */     boolean firstTime = true;
/*  501 */     String startdate = null;String enddate = null;
/*  502 */     long starttime = 0L;long endtime = 0L;
/*  503 */     String resID = request.getParameter("resourceid");
/*  504 */     String period = request.getParameter("period");
/*  505 */     String bid = request.getParameter("bhid");
/*  506 */     boolean isServiceGroup = false;
/*      */     
/*  508 */     if (bid == null) {
/*  509 */       bid = "oni";
/*      */     }
/*      */     
/*  512 */     if ("true".equalsIgnoreCase(request.getParameter("ServiceGroup"))) {
/*  513 */       isServiceGroup = true;
/*      */     }
/*  515 */     ArrayList<Hashtable> downtimeDetails = new ArrayList();
/*  516 */     Hashtable<String, String> downtimeDetailsTable = new Hashtable();
/*      */     
/*  518 */     AMLog.info("RESTAPI: URITree: getDowntimeDetails:INside getDowntimeDetails....");
/*  519 */     for (int i = 0; i < 2; i++)
/*      */     {
/*  521 */       ResultSet set = null;
/*      */       
/*      */       try
/*      */       {
/*  525 */         long customstartTime = 0L;
/*  526 */         long customendTime = 0L;
/*  527 */         long[] time = ReportUtilities.getTimeStamp(period);
/*  528 */         long mintimeindb = ReportUtilities.getMinTimeInDB(resID);
/*  529 */         long startTime = 0L;
/*  530 */         long endTime = 0L;
/*  531 */         long totalDuration = 0L;
/*      */         
/*      */         long currenttime;
/*  534 */         if (period.equals("4"))
/*      */         {
/*  536 */           customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  537 */           customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*  538 */           if (mintimeindb > customstartTime)
/*      */           {
/*  540 */             startTime = mintimeindb;
/*      */           }
/*  542 */           else if (mintimeindb != 0L)
/*      */           {
/*  544 */             startTime = customstartTime;
/*      */           }
/*  546 */           currenttime = System.currentTimeMillis();
/*  547 */           if (customendTime > currenttime)
/*      */           {
/*  549 */             endTime = currenttime;
/*      */           }
/*      */           else
/*      */           {
/*  553 */             endTime = customendTime;
/*      */           }
/*  555 */           totalDuration = endTime - startTime;
/*  556 */           AMLog.debug(" ######### GetWLSGraph : Availabilty : Period @ " + period + " START TIME = " + startTime + " END TIME = " + endTime);
/*      */         }
/*  558 */         else if (period.equals("-1"))
/*      */         {
/*  560 */           startTime = starttime;
/*  561 */           endTime = endtime;
/*  562 */           if (endTime > startTime)
/*      */           {
/*  564 */             totalDuration = endTime - startTime;
/*      */           }
/*      */           else
/*      */           {
/*  568 */             return null;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  573 */         else if (mintimeindb > time[0])
/*      */         {
/*  575 */           startTime = mintimeindb;
/*  576 */           endTime = time[1];
/*  577 */           if (endTime > startTime)
/*      */           {
/*  579 */             totalDuration = endTime - startTime;
/*      */           }
/*      */           else
/*      */           {
/*  583 */             return null;
/*      */           }
/*      */         }
/*  586 */         else if (mintimeindb != 0L)
/*      */         {
/*  588 */           startTime = time[0];
/*  589 */           endTime = time[1];
/*  590 */           totalDuration = endTime - startTime;
/*      */         }
/*      */         
/*      */ 
/*  594 */         String down = "";
/*  595 */         String up = "";
/*  596 */         String unmanage = "";
/*  597 */         String schedule = "";
/*  598 */         DBUtil db = new DBUtil();
/*  599 */         float downPercent = 0.0F;
/*  600 */         float upPercent = 0.0F;
/*  601 */         float unmanagePercent = 0.0F;
/*  602 */         float schedulePercent = 0.0F;
/*  603 */         ReportUtilities rep = new ReportUtilities();
/*  604 */         String DBVal = db.getGlobalConfigValueForMGAvailability();
/*  605 */         if (rep.isHAI(resID)) {
/*  606 */           long unmanagedtime = 0L;
/*  607 */           long scheduledtime = 0L;
/*  608 */           Properties p1 = ReportUtilities.getMonitorGroupAvailability(resID, period, startTime, endTime, bid, null, isServiceGroup);
/*  609 */           down = p1.getProperty("downtime");
/*  610 */           up = p1.getProperty("uptime");
/*  611 */           unmanage = p1.getProperty("unmanagedtime") == null ? ReportUtilities.format(unmanagedtime) : p1.getProperty("unmanagedtime");
/*  612 */           schedule = p1.getProperty("scheduledtime") == null ? ReportUtilities.format(scheduledtime) : p1.getProperty("scheduledtime");
/*  613 */           downPercent = Float.parseFloat(p1.getProperty("unavailable"));
/*  614 */           upPercent = Float.parseFloat(p1.getProperty("available"));
/*  615 */           unmanagePercent = Float.parseFloat(p1.getProperty("ServicesUnMgPercent"));
/*  616 */           schedulePercent = Float.parseFloat(p1.getProperty("ServicesSchPercent"));
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
/*      */         }
/*      */         else
/*      */         {
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
/*  640 */           String query = "select RESID, TYPE, sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where RESID in (" + resID + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE order by TotalDownTime desc";
/*      */           
/*  642 */           set = AMConnectionPool.executeQueryStmt(query);
/*  643 */           AMLog.info("query " + query);
/*  644 */           int typeID = -1;
/*  645 */           long unmanagedtime = 0L;
/*  646 */           long scheduledtime = 0L;
/*  647 */           long totdowntime = 0L;
/*  648 */           while (set.next())
/*      */           {
/*  650 */             typeID = set.getInt("TYPE");
/*  651 */             if (typeID == 1)
/*      */             {
/*  653 */               totdowntime = set.getLong("TotalDownTime");
/*      */             }
/*  655 */             else if (typeID == 2)
/*      */             {
/*  657 */               unmanagedtime = set.getLong("TotalDownTime");
/*      */             }
/*      */             else
/*      */             {
/*  661 */               scheduledtime = set.getLong("TotalDownTime");
/*      */             }
/*      */           }
/*      */           
/*  665 */           long uptime = totalDuration - (totdowntime + unmanagedtime + scheduledtime);
/*  666 */           down = ReportUtilities.format(totdowntime);
/*  667 */           up = ReportUtilities.format(uptime);
/*  668 */           unmanage = ReportUtilities.format(unmanagedtime);
/*  669 */           schedule = ReportUtilities.format(scheduledtime);
/*  670 */           upPercent = (float)uptime / (float)totalDuration * 100.0F;
/*  671 */           downPercent = (float)totdowntime / (float)totalDuration * 100.0F;
/*  672 */           unmanagePercent = (float)unmanagedtime / (float)totalDuration * 100.0F;
/*  673 */           schedulePercent = (float)scheduledtime / (float)totalDuration * 100.0F;
/*  674 */           if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */           {
/*  676 */             uptime = totalDuration - totdowntime;
/*  677 */             down = ReportUtilities.format(totdowntime);
/*  678 */             up = ReportUtilities.format(uptime);
/*  679 */             unmanage = ReportUtilities.format(0L);
/*  680 */             schedule = ReportUtilities.format(0L);
/*  681 */             upPercent = (float)uptime / (float)totalDuration * 100.0F;
/*  682 */             downPercent = (float)totdowntime / (float)totalDuration * 100.0F;
/*  683 */             unmanagePercent = 0.0F;
/*  684 */             schedulePercent = 0.0F;
/*      */           }
/*      */           
/*  687 */           String dateQuery = "select count(*) from AM_MO_DowntimeData where (uptime > " + System.currentTimeMillis() + " or downtime > " + System.currentTimeMillis() + ") and RESID in (" + resID + ")";
/*  688 */           boolean dateInconsistent = false;
/*  689 */           ResultSet dateSet = null;
/*      */           try
/*      */           {
/*  692 */             dateSet = AMConnectionPool.executeQueryStmt(dateQuery);
/*  693 */             if ((dateSet.next()) && (dateSet.getInt(1) > 0))
/*      */             {
/*  695 */               dateInconsistent = true;
/*      */             }
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/*  700 */             exc.printStackTrace();
/*      */           }
/*      */           finally {}
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  707 */           if ((upPercent < 0.0F) || (upPercent > 100.0F) || (downPercent < 0.0F) || (downPercent > 100.0F) || (uptime < 0L) || (totdowntime < 0L) || (dateInconsistent) || (unmanagePercent < 0.0F) || (unmanagePercent > 100.0F) || (schedulePercent < 0.0F) || (schedulePercent > 100.0F))
/*      */           {
/*  709 */             AMLog.debug("**********************************************************************************");
/*  710 */             AMLog.debug("URITree :  Resource ID: " + resID + " uptime: " + uptime + " downtime: " + totdowntime);
/*  711 */             AMLog.debug("**********************************************************************************");
/*  712 */             if (firstTime)
/*      */             {
/*  714 */               firstTime = false;
/*  715 */               AMDataCorrectionUtil.correctResourceForAvailability(Integer.parseInt(resID));
/*  716 */               AMDataCorrectionUtil.correctOverlappingEntriesForAvailability(Integer.parseInt(resID));
/*  717 */               AMDataCorrectionUtil.correctInconsistentDataForAvailability(Integer.parseInt(resID));
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
/*  812 */               AMConnectionPool.closeStatement(set); continue;
/*      */             }
/*  722 */             throw new IllegalStateException("The data is inconsistent to plot the chart.");
/*      */           }
/*      */           
/*      */ 
/*      */           try
/*      */           {
/*  728 */             AMDataCorrectionUtil.checkFalseDowntime(Integer.parseInt(resID), null);
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/*  732 */             exc.printStackTrace();
/*      */           }
/*      */           
/*  735 */           if (totalDuration == 0L)
/*      */           {
/*  737 */             throw new IllegalStateException(FormatUtil.getString("am.webclient.urlmonitor.nodata.message"));
/*      */           }
/*      */         }
/*      */         
/*  741 */         if (("false".equalsIgnoreCase(DBVal)) && (rep.isHAI(resID)))
/*      */         {
/*  743 */           if (!down.startsWith("0"))
/*      */           {
/*  745 */             downPercent = Math.round(downPercent * 100.0F) / 100.0F;
/*      */           }
/*  747 */           if (!up.startsWith("0"))
/*      */           {
/*  749 */             upPercent = Math.round(upPercent * 100.0F) / 100.0F;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  754 */           if (!down.startsWith("0"))
/*      */           {
/*  756 */             downPercent = Math.round(downPercent * 100.0F) / 100.0F;
/*      */           }
/*  758 */           if (!up.startsWith("0"))
/*      */           {
/*  760 */             upPercent = Math.round(upPercent * 100.0F) / 100.0F;
/*      */           }
/*      */           
/*  763 */           if (!unmanage.startsWith("0"))
/*      */           {
/*  765 */             unmanagePercent = Math.round(unmanagePercent * 100.0F) / 100.0F;
/*      */           }
/*  767 */           if (!schedule.startsWith("0"))
/*      */           {
/*  769 */             schedulePercent = Math.round(schedulePercent * 100.0F) / 100.0F;
/*      */           }
/*      */         }
/*  772 */         HashMap<String, String> resourceHADetails = CommonAPIUtil.getHealthAvailabilityDetails(resID, "HAI");
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
/*  789 */         downtimeDetailsTable.put("Period", period);
/*  790 */         downtimeDetailsTable.put("DownPercent", downPercent + "");
/*  791 */         downtimeDetailsTable.put("UpPercent", upPercent + "");
/*  792 */         downtimeDetailsTable.put("UnmanagePercent", unmanagePercent + "");
/*  793 */         downtimeDetailsTable.put("SchedulePercent", schedulePercent + "");
/*  794 */         downtimeDetailsTable.put("DisplayName", getResourceName(resID));
/*  795 */         downtimeDetailsTable.put("ResourceId", resID);
/*  796 */         downtimeDetailsTable.put("AvailabilitySeverity", resourceHADetails.get("AVAILABILITYSEVERITY"));
/*  797 */         downtimeDetailsTable.put("AvailabilityMessage", resourceHADetails.get("AVAILABILITYMESSAGE"));
/*  798 */         AMLog.debug("RESTAPI: URITree: hashtable===>" + downtimeDetailsTable);
/*      */       }
/*      */       catch (IllegalStateException ise)
/*      */       {
/*  802 */         ise.printStackTrace();
/*  803 */         throw ise;
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  807 */         AMLog.fatal("Exception while getting availability graph", exp);
/*  808 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  812 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*  814 */       break;
/*      */     }
/*      */     
/*  817 */     downtimeDetails.add(downtimeDetailsTable);
/*  818 */     AMLog.info("RESTAPI: URITree==>" + request.getParameter("showFullDetails"));
/*      */     
/*  820 */     if ("true".equals(request.getParameter("showFullDetails")))
/*      */     {
/*  822 */       getDetailedDowntimesForResourceID(resID, period, downtimeDetails);
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  828 */         ArrayList<Hashtable> downtimeSummary = new ArrayList();
/*  829 */         Properties props = ReportUtilities.calculateAvailabilityDetails(resID, starttime, endtime);
/*  830 */         ArrayList<Hashtable<String, String>> tempDowntimeSummary = new ArrayList();
/*  831 */         tempDowntimeSummary = getDowntimeSummary(resID, endtime, starttime, props);
/*  832 */         downtimeSummary.addAll(tempDowntimeSummary);
/*  833 */         AMLog.info("RESTAPI: URITree===downtmeSummary==>" + downtimeSummary);
/*  834 */         if (downtimeSummary.size() > 0)
/*      */         {
/*  836 */           ((Hashtable)downtimeDetails.get(0)).put("DowntimeSummary", downtimeSummary);
/*  837 */           request.setAttribute("downtimeSummary", "true");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  842 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  846 */     AMLog.debug("RESTAPI: URITree: downtimeDetails===>" + downtimeDetails);
/*      */     
/*  848 */     return downtimeDetails;
/*      */   }
/*      */   
/*      */   private static void getDetailedDowntimesForResourceID(String resId, String period, ArrayList<Hashtable> downtimeDetails)
/*      */   {
/*  853 */     long curTime = System.currentTimeMillis();
/*  854 */     long last24Hrs = 86400000L;
/*      */     
/*  856 */     String[] timeStamps = getLastNDaysForPeriod(period);
/*      */     
/*  858 */     for (String test : timeStamps)
/*      */     {
/*  860 */       AMLog.info("RESTAPI: URITree: timestamps==>" + test);
/*      */     }
/*      */     try
/*      */     {
/*  864 */       if (timeStamps.length >= 2)
/*      */       {
/*  866 */         int n = Integer.parseInt(timeStamps[0]);
/*      */         
/*  868 */         Timestamp startTimestamp = new Timestamp(Long.valueOf(timeStamps[1]).longValue());
/*  869 */         long starttime = startTimestamp.getTime();
/*  870 */         long endtime = curTime;
/*      */         
/*  872 */         if (timeStamps.length > 2)
/*      */         {
/*  874 */           endtime = Long.valueOf(timeStamps[2]).longValue();
/*      */         }
/*      */         else
/*      */         {
/*  878 */           Timestamp endTimestamp = new Timestamp(starttime);
/*  879 */           endTimestamp.setHours(23);
/*  880 */           endTimestamp.setMinutes(59);
/*  881 */           endTimestamp.setSeconds(59);
/*  882 */           endtime = endTimestamp.getTime();
/*      */         }
/*      */         
/*  885 */         ArrayList<Hashtable<String, Object>> Downtime = new ArrayList();
/*  886 */         for (int i = 0; i < n; i++)
/*      */         {
/*      */           try
/*      */           {
/*  890 */             Hashtable<String, Object> downDetailsOfDay = new Hashtable();
/*  891 */             AMLog.debug("RESTAPI Downtime data:: ResourceId:" + resId + " startTime:" + starttime + " Endtime:" + endtime);
/*  892 */             Properties props = ReportUtilities.calculateAvailabilityDetails(resId, starttime, endtime);
/*  893 */             DateFormat dataformat = DateFormat.getDateInstance(1);
/*      */             
/*      */ 
/*  896 */             downDetailsOfDay.put("DownPercent", props.getProperty("DowntimePercentage"));
/*  897 */             downDetailsOfDay.put("UpPercent", props.getProperty("uptimepercentage"));
/*  898 */             downDetailsOfDay.put("UnmanagePercent", props.getProperty("UnManagedPercentage"));
/*  899 */             downDetailsOfDay.put("SchedulePercent", props.getProperty("ScheduledPercentage"));
/*  900 */             downDetailsOfDay.put("Date", dataformat.format(new Date(starttime)));
/*  901 */             ArrayList<Hashtable<String, String>> downtimeSummary = new ArrayList();
/*      */             try
/*      */             {
/*  904 */               downtimeSummary = getDowntimeSummary(resId, starttime, endtime, props);
/*  905 */               AMLog.info("RESTAPI: URITree===downtmeSummary==>" + downtimeSummary);
/*  906 */               downDetailsOfDay.put("DowntimeSummary", downtimeSummary);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  910 */               e.printStackTrace();
/*  911 */               downDetailsOfDay.put("DowntimeSummary", downtimeSummary);
/*      */             }
/*  913 */             Downtime.add(downDetailsOfDay);
/*      */ 
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  918 */             e.printStackTrace();
/*      */           }
/*  920 */           starttime -= last24Hrs;
/*  921 */           endtime -= last24Hrs;
/*      */         }
/*  923 */         if (Downtime.size() > 0)
/*      */         {
/*  925 */           ((Hashtable)downtimeDetails.get(0)).put("Downtimes", Downtime);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  930 */         Timestamp startTimestamp = new Timestamp(curTime);
/*  931 */         startTimestamp.setHours(0);
/*  932 */         startTimestamp.setMinutes(0);
/*  933 */         startTimestamp.setSeconds(1);
/*  934 */         long starttime = startTimestamp.getTime();
/*      */         
/*  936 */         Timestamp endTimestamp = new Timestamp(curTime);
/*  937 */         endTimestamp.setHours(23);
/*  938 */         endTimestamp.setMinutes(59);
/*  939 */         endTimestamp.setSeconds(59);
/*  940 */         endtime = endTimestamp.getTime();
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*      */       long endtime;
/*  945 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static ArrayList<Hashtable<String, String>> getDowntimeSummary(String resourceid, long startTime, long endTime, Properties summary)
/*      */   {
/*  951 */     ArrayList<Hashtable<String, String>> downtimeSummary = new ArrayList();
/*  952 */     ResultSet set = null;
/*      */     try
/*      */     {
/*  955 */       long unmanagedtime = 0L;
/*  956 */       long scheduledtime = 0L;
/*  957 */       long totdowntime = 0L;
/*  958 */       DateFormat timeformat = DateFormat.getTimeInstance();
/*  959 */       String query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       
/*  961 */       if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */       {
/*  963 */         query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       }
/*      */       
/*  966 */       AMLog.debug("StartTime:" + new Date(startTime) + " endtime:" + new Date(endTime));
/*  967 */       AMLog.debug("##Query-->" + query);
/*  968 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  970 */       while (set.next())
/*      */       {
/*  972 */         Hashtable<String, String> rows = new Hashtable();
/*  973 */         rows.put("DownTime", timeformat.format(new Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("DownTime")))).toString());
/*  974 */         if (set.getLong("UpTime") == endTime)
/*      */         {
/*  976 */           rows.put("UpTime", timeformat.format(new Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime")))).toString());
/*      */         }
/*      */         else
/*      */         {
/*  980 */           rows.put("UpTime", timeformat.format(new Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime")))).toString());
/*      */         }
/*  982 */         String desc = set.getString("SHORT_DESCRIPTION");
/*  983 */         rows.put("Downtime_Reason", desc != null ? desc : "No Information Available.");
/*  984 */         rows.put("DownTimeMillis", Long.toString(set.getLong("DownTime")));
/*      */         
/*  986 */         rows.put("ReasonID", set.getString("REASONID"));
/*      */         
/*      */ 
/*      */ 
/*  990 */         if (("true".equalsIgnoreCase(summary.getProperty("AppCluster"))) && (set.getInt("TYPE") == 1))
/*      */         {
/*  992 */           if (set.getInt("TYPE") == 1)
/*      */           {
/*  994 */             totdowntime += set.getLong("TotalDownTime");
/*      */           }
/*  996 */           if (totdowntime != 0L) {
/*  997 */             rows.put("TotalDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*      */           }
/*      */         }
/*      */         
/* 1001 */         if (("true".equalsIgnoreCase(summary.getProperty("Monitor"))) || ("true".equalsIgnoreCase(summary.getProperty("MGService"))))
/*      */         {
/*      */ 
/* 1004 */           if (set.getInt("TYPE") == 1)
/*      */           {
/* 1006 */             totdowntime += set.getLong("TotalDownTime");
/*      */           }
/* 1008 */           else if (set.getInt("TYPE") == 2)
/*      */           {
/* 1010 */             unmanagedtime += set.getLong("TotalDownTime");
/*      */           }
/*      */           else
/*      */           {
/* 1014 */             scheduledtime += set.getLong("TotalDownTime");
/*      */           }
/*      */           
/* 1017 */           if (set.getString("SHORT_DESCRIPTION") == null)
/*      */           {
/* 1019 */             if (set.getInt("TYPE") == 2)
/*      */             {
/* 1021 */               rows.put("Downtime_Reason", "Monitor is Unmanaged");
/*      */             }
/* 1023 */             if (set.getInt("TYPE") == 3)
/*      */             {
/* 1025 */               rows.put("Downtime_Reason", "Scheduled Maintenance");
/*      */             }
/*      */           }
/*      */           
/* 1029 */           if (totdowntime != 0L) {
/* 1030 */             rows.put("TotalDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*      */           }
/* 1032 */           if (unmanagedtime != 0L) {
/* 1033 */             rows.put("UnManagedTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(unmanagedtime)));
/*      */           }
/* 1035 */           if (scheduledtime != 0L) {
/* 1036 */             rows.put("ScheduledTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(scheduledtime)));
/*      */           }
/*      */         }
/* 1039 */         downtimeSummary.add(rows);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1044 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1048 */       AMConnectionPool.closeStatement(set);
/*      */     }
/* 1050 */     return downtimeSummary;
/*      */   }
/*      */   
/*      */   private static String[] getLastNDaysForPeriod(String period)
/*      */   {
/* 1055 */     String[] timeStamps = new String[2];
/* 1056 */     long curTime = System.currentTimeMillis();
/*      */     
/* 1058 */     Calendar cal = Calendar.getInstance();
/* 1059 */     int date = cal.get(5);
/* 1060 */     int dow = cal.get(7);
/* 1061 */     long last24Hrs = 86400000L;
/*      */     
/* 1063 */     Timestamp startTimestamp = new Timestamp(curTime);
/* 1064 */     startTimestamp.setHours(0);
/* 1065 */     startTimestamp.setMinutes(0);
/* 1066 */     startTimestamp.setSeconds(1);
/* 1067 */     long starttime = startTimestamp.getTime();
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
/* 1082 */     if (period.equals("4"))
/*      */     {
/* 1084 */       timeStamps[0] = "4";
/*      */     }
/* 1086 */     else if (period.equals("0"))
/*      */     {
/* 1088 */       timeStamps[0] = "1";
/* 1089 */       timeStamps[1] = (curTime + "");
/*      */     }
/* 1091 */     else if (period.equals("3"))
/*      */     {
/* 1093 */       starttime -= last24Hrs;
/* 1094 */       timeStamps[0] = "1";
/* 1095 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1097 */     else if (period.equals("6"))
/*      */     {
/* 1099 */       timeStamps[0] = (dow - 1 + "");
/* 1100 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1102 */     else if (period.equals("1"))
/*      */     {
/* 1104 */       timeStamps[0] = "7";
/* 1105 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1107 */     else if (period.equals("12"))
/*      */     {
/* 1109 */       starttime -= dow * last24Hrs;
/* 1110 */       timeStamps[0] = "7";
/* 1111 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1113 */     else if (period.equals("7"))
/*      */     {
/* 1115 */       timeStamps[0] = (date + "");
/* 1116 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1118 */     else if (period.equals("2"))
/*      */     {
/* 1120 */       timeStamps[0] = "30";
/* 1121 */       timeStamps[1] = (starttime + "");
/*      */     }
/* 1123 */     else if (period.equals("11"))
/*      */     {
/* 1125 */       starttime -= date * last24Hrs;
/* 1126 */       timeStamps[0] = "30";
/* 1127 */       timeStamps[1] = (starttime + "");
/*      */     }
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
/* 1141 */     return timeStamps;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getServerServicesList(HttpServletRequest request, HttpServletResponse response, String ipAddress)
/*      */     throws Exception, UnknownHostException
/*      */   {
/* 1148 */     String outputString = "";
/* 1149 */     String condition = "";
/* 1150 */     String category = null;
/* 1151 */     if (request.getParameter("type") != null) {
/* 1152 */       category = request.getParameter("type").trim();
/*      */     }
/* 1154 */     String operatorCondition = CommonAPIUtil.getConditionForOperator(request);
/* 1155 */     String checkquery = "select IpAddress.PARENTNODE resname,AM_ManagedObject.DISPLAYNAME DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.RESOURCETYPE as RESOURCETYPE, AM_ManagedResourceType.SHORTNAME as TYPE, AM_ManagedObject.RESOURCEID RESOURCEID,AM_ManagedObject.RESOURCENAME RESOURCENAME,AM_ManagedObject.DESCRIPTION DESCRIPTION,AM_ATTRIBUTES.ATTRIBUTEID attributeid from InetService,AM_ManagedObject,AM_ATTRIBUTES,IpAddress,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and InetService.INTERFACENAME=IpAddress.NAME and InetService.NAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.Type = AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.TYPE in " + Constants.resourceTypes + operatorCondition;
/* 1156 */     String qry = "select IpAddress.PARENTNODE, IpAddress.PARENTNET,AM_ManagedObject.RESOURCEID,AM_ManagedObject.TYPE,AM_ManagedObject.DISPLAYNAME  from IpAddress,AM_ManagedObject where AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNODE " + operatorCondition + " condition_String" + " group by RESOURCEID,PARENTNODE, PARENTNET,DISPLAYNAME,TYPE";
/*      */     
/* 1158 */     if ((category != null) && (ipAddress == null))
/*      */     {
/* 1160 */       AMLog.debug("REST API : ListServer : Inside category");
/* 1161 */       if (category.equalsIgnoreCase("all"))
/*      */       {
/* 1163 */         if (Constants.sqlManager) {
/* 1164 */           condition = " and AM_ManagedObject.Type!='Node' ";
/*      */         }
/* 1166 */         qry = qry.replaceAll("condition_String", condition);
/* 1167 */         outputString = ListServerXML(request, response, checkquery, "byallmonitors", qry);
/*      */ 
/*      */ 
/*      */       }
/* 1171 */       else if (validateMonitorDetails(request, response, category, "PARENTNODE", "ListServer"))
/*      */       {
/* 1173 */         condition = " and IpAddress.PARENTNODE='" + category + "'";
/*      */         try
/*      */         {
/* 1176 */           InetAddress inetAdd = InetAddress.getByName(category);
/* 1177 */           if (!inetAdd.equals(category))
/*      */           {
/* 1179 */             String canonicalHostName = inetAdd.getCanonicalHostName();
/* 1180 */             condition = " and (IpAddress.PARENTNODE='" + category + "' or IpAddress.PARENTNODE='" + canonicalHostName + "') ";
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1186 */           AMLog.info("RESTAPI: ListServer: Exception in getting the Fully qualified hostname for monitor:" + category + "; but no need to worry about that");
/*      */         }
/* 1188 */         checkquery = checkquery + condition;
/* 1189 */         qry = qry.replaceAll("condition_String", condition);
/* 1190 */         outputString = ListServerXML(request, response, checkquery, "byparticularserver", qry);
/*      */       }
/*      */       else
/*      */       {
/* 1194 */         AMLog.debug("REST API : The specified request URI is incorrect:the monitor in request URI is incorrect.");
/* 1195 */         String message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/* 1196 */         String errorCode = "4032";
/* 1197 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */       
/*      */     }
/* 1201 */     else if ((ipAddress != null) && (category == null))
/*      */     {
/* 1203 */       if (validateMonitorDetails(request, response, ipAddress, "ipaddress", "ListServer"))
/*      */       {
/* 1205 */         InetAddress inetAdd = InetAddress.getByName(ipAddress);
/* 1206 */         category = inetAdd.getHostName();
/* 1207 */         category = category != null ? category.toLowerCase() : category;
/* 1208 */         condition = " and IpAddress.PARENTNODE='" + category + "'";
/* 1209 */         checkquery = checkquery + condition;
/* 1210 */         qry = qry.replaceAll("condition_String", condition);
/* 1211 */         outputString = ListServerXML(request, response, checkquery, "ipaddress", qry);
/*      */       } else {
/* 1213 */         AMLog.debug("REST API : The specified request URI is incorrect:the monitor in request URI is incorrect.");
/* 1214 */         String message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/* 1215 */         String errorCode = "4032";
/* 1216 */         outputString = generateXML(request, response, message, errorCode);
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1222 */       AMLog.debug("REST API : The specified request URI is incorrect:the type specified is wrong type:" + category + " ipaddress:" + ipAddress);
/* 1223 */       String message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/* 1224 */       String errorCode = "4032";
/* 1225 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/* 1227 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getAlarmsList(HttpServletRequest request, HttpServletResponse response, String monitorName, String timeMilliSec, String topN, String resId)
/*      */     throws Exception
/*      */   {
/* 1234 */     String category = request.getParameter("type");
/* 1235 */     String categoryCond = null;
/*      */     try {
/* 1237 */       String groupName = request.getParameter("groupName");
/* 1238 */       if ((resId == null) && (groupName != null)) {
/* 1239 */         String haid = MGUtil.getMonitorGroupId(groupName);
/* 1240 */         if ((haid != null) && (validateMonitorDetails(request, response, haid, "RESOURCEID", "ListAlarms"))) {
/* 1241 */           return ListAlarms(request, response, haid);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1245 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1248 */     if (category != null)
/*      */     {
/* 1250 */       category = category.toLowerCase();
/* 1251 */       if (category.contains(","))
/*      */       {
/* 1253 */         if (category.contains("all"))
/*      */         {
/* 1255 */           categoryCond = "'1','4','5'";
/*      */         }
/*      */         else
/*      */         {
/* 1259 */           if (category.contains("critical"))
/*      */           {
/* 1261 */             categoryCond = "'1'";
/*      */           }
/* 1263 */           if (category.contains("warning"))
/*      */           {
/* 1265 */             categoryCond = categoryCond != null ? categoryCond + ",'4'" : "'4'";
/*      */           }
/* 1267 */           if (category.contains("clear"))
/*      */           {
/* 1269 */             categoryCond = categoryCond != null ? categoryCond + ",'5'" : "'5'";
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1274 */     String operatorCondition = CommonAPIUtil.getConditionForOperator(request);
/* 1275 */     String query = "select CATEGORY , SEVERITY ,Alert.ENTITY,MMESSAGE,AM_ManagedObject.DISPLAYNAME ,Alert.MODTIME ,SOURCE,AM_ManagedObject.TYPE, AM_ManagedResourceType.IMAGEPATH, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.DISPLAYNAME end as TYPEDISPLAYNAME, case when AM_ManagedObject.TYPE='Trap' then 'Trap' else AM_ManagedResourceType.SHORTNAME end as SHORTNAME, 'YES' as ANNOTATION, Alert.WHO from Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE= AM_ManagedObject.type  where (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap')  " + operatorCondition;
/*      */     
/* 1277 */     String custFieldName = request.getParameter("customFieldName");
/* 1278 */     if (timeMilliSec != null)
/*      */     {
/* 1280 */       long timeMs = System.currentTimeMillis();
/* 1281 */       long time = Long.parseLong(timeMilliSec);
/* 1282 */       if ((time <= timeMs) && (time > 0L))
/*      */       {
/* 1284 */         query = query + " AND SEVERITY IN('1','4','5') and Alert.MODTIME >= '" + time + "' order by MODTIME DESC";
/*      */       }
/*      */       else
/*      */       {
/* 1288 */         AMLog.debug("REST API : The specified request URI is incorrect:the time specified is wrong");
/* 1289 */         String message = FormatUtil.getString("am.webclient.apikey.wrongtime.message");
/* 1290 */         String errorCode = "4512";
/* 1291 */         return generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/* 1294 */     else if ((resId != null) && (category == null) && (validateMonitorDetails(request, response, resId, "RESOURCEID", "ListAlarms")))
/*      */     {
/* 1296 */       query = query + " AND SEVERITY IN('1','4','5') AND AM_ManagedObject.RESOURCEID='" + resId + "' order by MODTIME DESC";
/*      */     }
/* 1298 */     else if ((category != null) && (category.equalsIgnoreCase("all")))
/*      */     {
/* 1300 */       query = query + " AND SEVERITY IN('1','4','5') order by MODTIME DESC";
/*      */     }
/* 1302 */     else if ((category != null) && (category.equalsIgnoreCase("allmonitors")))
/*      */     {
/* 1304 */       query = query + " AND SEVERITY IN('1','4','5') and AM_ManagedObject.type != 'HAI' order by MODTIME DESC";
/*      */     }
/* 1306 */     else if ((category != null) && (category.equalsIgnoreCase("clear")))
/*      */     {
/* 1308 */       query = query + " AND SEVERITY ='5' order by MODTIME DESC";
/*      */     }
/* 1310 */     else if ((category != null) && (category.equalsIgnoreCase("warning")))
/*      */     {
/* 1312 */       query = query + " AND SEVERITY ='4' order by MODTIME DESC";
/*      */     }
/* 1314 */     else if ((category != null) && (category.equalsIgnoreCase("critical")))
/*      */     {
/* 1316 */       query = query + " AND SEVERITY ='1' order by MODTIME DESC";
/*      */     }
/* 1318 */     else if ((category != null) && (categoryCond != null))
/*      */     {
/* 1320 */       query = query + " AND SEVERITY IN(" + categoryCond + ") order by MODTIME DESC";
/*      */     }
/* 1322 */     else if ((category != null) && (validateMonitorDetails(request, response, category, "TYPE", "ListAlarms")))
/*      */     {
/* 1324 */       query = query + " AND SEVERITY IN('1','4','5') and " + DBQueryUtil.castasCitext("AM_ManagedObject.TYPE") + " = '" + category + "' order by MODTIME DESC";
/*      */     }
/* 1326 */     else if (custFieldName != null) {
/* 1327 */       query = query + " AND SEVERITY IN('1','4','5') order by MODTIME DESC";
/*      */     }
/* 1329 */     else if ((topN != null) && (category == null) && (monitorName == null) && (resId == null) && (custFieldName == null)) {
/* 1330 */       query = DBQueryUtil.getDBQuery("am.uritree.topnalarms.query", new String[] { "AppManager", "AppManager_Trap", topN });
/*      */     }
/*      */     else
/*      */     {
/* 1334 */       AMLog.debug("REST API : The specified request URI is incorrect:the monitor in request URI is incorrect.");
/* 1335 */       String message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/*      */       
/* 1337 */       String errorCode = "4032";
/* 1338 */       return generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/* 1341 */     if ((topN != null) && (!query.toLowerCase().contains("limit")) && (!query.toLowerCase().contains("top")))
/*      */     {
/* 1343 */       query = DBQueryUtil.getTopNValues(query, topN);
/*      */     }
/* 1345 */     String customFieldName = request.getParameter("customFieldName");
/* 1346 */     String customFieldValue = request.getParameter("customFieldValue");
/* 1347 */     if (customFieldName != null) {
/* 1348 */       if (customFieldValue == null) {
/* 1349 */         String errorMessage = FormatUtil.getString("am.webclient.apikey.listalarm.emptycustomvalue.message");
/* 1350 */         String errorCode = "4550";
/* 1351 */         return generateXML(request, response, errorMessage, errorCode);
/*      */       }
/* 1353 */       List<String> ids = new ArrayList();
/* 1354 */       if (customFieldValue.indexOf(",") != -1) {
/* 1355 */         List<String> fieldValues = Arrays.asList(customFieldValue.split(","));
/* 1356 */         for (String fieldValue : fieldValues) {
/* 1357 */           List<String> temp = MyFields.getMonitorsforCustomField(fieldValue, customFieldValue);
/* 1358 */           ids.addAll(temp);
/*      */         }
/*      */       } else {
/* 1361 */         ids = MyFields.getMonitorsforCustomField(customFieldName, customFieldValue);
/*      */       }
/* 1363 */       StringBuilder customFilter = new StringBuilder();
/* 1364 */       if (ids.size() > 0) {
/* 1365 */         customFilter.append(" and AM_ManagedObject.RESOURCEID in (");
/* 1366 */         for (String monId : ids) {
/* 1367 */           customFilter.append(monId).append(",");
/*      */         }
/* 1369 */         customFilter = customFilter.deleteCharAt(customFilter.length() - 1);
/* 1370 */         customFilter.append(") ");
/* 1371 */         query = query.substring(0, query.indexOf("order by MODTIME DESC")) + customFilter + " order by MODTIME DESC";
/*      */       } else {
/* 1373 */         String errorMessage = FormatUtil.getString("am.webclient.apikey.listalarm.nomatch.message");
/* 1374 */         String errorCode = "4551";
/* 1375 */         return generateXML(request, response, errorMessage, errorCode);
/*      */       }
/*      */     }
/* 1378 */     return ListAlarmsXML(request, response, query);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getMonitorsList(HttpServletRequest request, HttpServletResponse response, String filterList, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 1386 */     String category = request.getParameter("type");
/* 1387 */     if (category != null)
/*      */     {
/* 1389 */       if (category.equalsIgnoreCase("windows"))
/*      */       {
/* 1391 */         category = Constants.server_windows;
/*      */       }
/* 1393 */       else if (category.equalsIgnoreCase("unknown"))
/*      */       {
/* 1395 */         category = "Node";
/*      */       }
/* 1397 */       else if ("FreeBSD,OpenBSD,FreeBSD / OpenBSD".contains(category))
/*      */       {
/* 1399 */         category = "FreeBSD,OpenBSD,FreeBSD / OpenBSD";
/*      */       }
/* 1401 */       else if ("HP-UX,HP-TRU64,HP-UX / Tru64".contains(category))
/*      */       {
/* 1403 */         category = "HP-UX,HP-TRU64,HP-UX / Tru64";
/*      */       }
/* 1405 */       else if ("SUN,SUN PC,Sun Solaris".contains(category))
/*      */       {
/* 1407 */         category = "SUN,SUN PC,Sun Solaris";
/*      */       }
/* 1409 */       else if ("File System Monitor,file,directory".contains(category))
/*      */       {
/* 1411 */         category = "File System Monitor,file,directory";
/*      */       }
/* 1413 */       else if ("VirtualMachine,HyperVVirtualMachine,XenServerVM".toLowerCase().contains(category.toLowerCase()))
/*      */       {
/* 1415 */         category = "VirtualMachine,HyperVVirtualMachine,XenServerVM";
/*      */       }
/* 1417 */       else if (("Container,Docker Container".toLowerCase().contains(category.toLowerCase())) && (!category.toLowerCase().equals("docker")))
/*      */       {
/* 1419 */         category = "Container,Docker Container";
/*      */       }
/* 1421 */       else if (category.equalsIgnoreCase("appservers"))
/*      */       {
/* 1423 */         category = (String)categMap.get("APP");
/*      */       }
/* 1425 */       else if (category.equalsIgnoreCase("dbservers"))
/*      */       {
/* 1427 */         category = (String)categMap.get("DBS");
/*      */       }
/* 1429 */       else if (category.equalsIgnoreCase("webservers"))
/*      */       {
/* 1431 */         category = (String)categMap.get("URL");
/*      */       }
/* 1433 */       else if (category.equalsIgnoreCase("msgsystems"))
/*      */       {
/* 1435 */         category = (String)categMap.get("MOM");
/*      */       }
/* 1437 */       else if (category.equalsIgnoreCase("cloudapps"))
/*      */       {
/* 1439 */         category = (String)categMap.get("CLD");
/*      */       }
/* 1441 */       else if (category.equalsIgnoreCase("erpapps"))
/*      */       {
/* 1443 */         category = (String)categMap.get("ERP");
/*      */       }
/* 1445 */       else if (category.equalsIgnoreCase("eumapps"))
/*      */       {
/* 1447 */         category = (String)categMap.get("EUM");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1452 */     String monitorName = request.getParameter("monitorname");
/* 1453 */     String resId = request.getParameter("resourceid");
/* 1454 */     String operatorCondition = CommonAPIUtil.getConditionForOperator(request);
/*      */     
/*      */ 
/* 1457 */     String checkquery = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME as typeshortname,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.DESCRIPTION,(case when AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID then 'false' else 'true' end) as Managed, Alert.MODTIME, CollectData.TARGETADDRESS, InetService.TARGETNAME,CollectData.APPLNDISCPORT FROM AM_ManagedResourceType,AM_ManagedObject  LEFT JOIN AM_UnManagedNodes ON (AM_ManagedObject .RESOURCEID = AM_UnManagedNodes.resid) LEFT OUTER JOIN Alert ON (AM_ManagedObject .RESOURCEID = Alert .SOURCE and (GROUPNAME='AppManager' or GROUPNAME='AppManager_Trap')) LEFT OUTER JOIN CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME LEFT OUTER JOIN InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedObject.TYPE in " + Constants.resourceTypes + operatorCondition;
/* 1458 */     String orderCondition = " order by DisplayName,Type";
/*      */     String outputString;
/* 1460 */     String outputString; if ((resId != null) && (category == null) && (monitorName == null))
/*      */     {
/*      */       String outputString;
/*      */       
/*      */ 
/* 1465 */       if (validateMonitorDetails(request, response, resId, "RESOURCEID", "ListMonitor"))
/*      */       {
/* 1467 */         checkquery = checkquery + " and AM_ManagedObject.RESOURCEID in (" + resId + ")";
/* 1468 */         outputString = ListMonitorXML(request, response, checkquery, "byparticularmonitor", isJsonFormat);
/*      */       }
/*      */       else
/*      */       {
/* 1472 */         AMLog.debug("REST API : The specified resourceid in request URI is wrong.");
/* 1473 */         String message = FormatUtil.getString("am.webclient.api.wrongresid.msg");
/* 1474 */         outputString = generateXML(request, response, message, "4003");
/*      */       }
/*      */     } else { String outputString;
/* 1477 */       if ((monitorName != null) && (category == null) && (resId == null))
/*      */       {
/*      */         String outputString;
/*      */         
/*      */ 
/* 1482 */         if (validateMonitorDetails(request, response, monitorName, "DISPLAYNAME", "ListMonitor"))
/*      */         {
/* 1484 */           checkquery = checkquery + " and AM_ManagedObject.DISPLAYNAME ='" + monitorName + "'";
/* 1485 */           outputString = ListMonitorXML(request, response, checkquery, "bymonitorname", isJsonFormat);
/*      */         }
/*      */         else
/*      */         {
/* 1489 */           AMLog.debug("REST API : The specified monitorname in request URI is wrong.");
/* 1490 */           String message = FormatUtil.getString("am.webclient.api.wrongmonname.msg");
/* 1491 */           outputString = generateXML(request, response, message, "4007");
/*      */         }
/*      */       } else { String outputString;
/* 1494 */         if ((category != null) && (resId == null) && (monitorName == null))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1499 */           AMLog.info("inside categeory-->" + category + "'");
/*      */           
/*      */           String outputString;
/* 1502 */           if (category.equalsIgnoreCase("all"))
/*      */           {
/* 1504 */             if (!filterList.isEmpty()) {
/* 1505 */               checkquery = checkquery + " and AM_ManagedObject.RESOURCEID NOT IN (" + filterList + ")";
/*      */             }
/* 1507 */             checkquery = checkquery + orderCondition;
/* 1508 */             outputString = ListMonitorXML(request, response, checkquery, "type", isJsonFormat);
/*      */           } else { String outputString;
/* 1510 */             if (validateMonitorDetails(request, response, category, "TYPE", "ListMonitor"))
/*      */             {
/* 1512 */               String typeCondition = " and AM_ManagedObject.TYPE = ('" + category + "')";
/* 1513 */               if (category.contains(","))
/*      */               {
/* 1515 */                 category = category.replaceAll(",", "','");
/* 1516 */                 typeCondition = " and AM_ManagedObject.TYPE in ('" + category + "')";
/*      */               }
/* 1518 */               else if (category.equals("NWD"))
/*      */               {
/* 1520 */                 typeCondition = " and (AM_ManagedObject.TYPE like 'OpManager-%')";
/*      */               }
/* 1522 */               else if (category.equals("SAN"))
/*      */               {
/* 1524 */                 typeCondition = " and (AM_ManagedObject.TYPE like 'OpStor-%')";
/*      */               }
/* 1526 */               else if (category.equals("EMO"))
/*      */               {
/* 1528 */                 typeCondition = " and (AM_ManagedObject.TYPE like '%Site24x7%')";
/*      */               }
/*      */               
/* 1531 */               checkquery = checkquery + typeCondition;
/* 1532 */               outputString = ListMonitorXML(request, response, checkquery, "type", isJsonFormat);
/*      */             }
/*      */             else
/*      */             {
/* 1536 */               AMLog.debug("REST API : The specified type in request URI is wrong.");
/* 1537 */               String message = FormatUtil.getString("am.webclient.api.wrongtyp.msg");
/* 1538 */               outputString = generateXML(request, response, message, "4005");
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 1543 */           AMLog.debug("REST API : The specified request URI is incorrect:the parameter specified is wrong");
/* 1544 */           String message = FormatUtil.getString("am.webclient.apikey.wrongmonparam.message");
/* 1545 */           String errorCode = "4032";
/* 1546 */           outputString = generateXML(request, response, message, errorCode);
/*      */         } } }
/* 1548 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getMonitorsList(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1553 */     return getMonitorsList(request, response, "", isJsonFormat);
/*      */   }
/*      */   
/*      */   private static boolean isValidResourceId(String resId)
/*      */   {
/*      */     try
/*      */     {
/* 1560 */       if (resId != null)
/*      */       {
/* 1562 */         String[] Resids = resId.split(",");
/* 1563 */         for (int i = 0; i < Resids.length; i++)
/*      */         {
/* 1565 */           Integer.parseInt(Resids[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1571 */       e.printStackTrace();
/* 1572 */       return false;
/*      */     }
/* 1574 */     return true;
/*      */   }
/*      */   
/*      */   public static String generateXMLForCustomFields(HttpServletRequest request, HttpServletResponse response, HashMap fieldSynchstatus) throws Exception {
/* 1578 */     String outputString = "";
/* 1579 */     AMLog.debug("CUSTOM FIELDS : inside generateXMLForCustomFields xml");
/* 1580 */     String uri = request.getRequestURI();
/* 1581 */     response.setContentType("text/xml; charset=UTF-8");
/* 1582 */     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 1583 */     DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 1584 */     Document doc = docBuilder.newDocument();
/* 1585 */     AMLog.debug("Custom Fields : root node uri" + uri);
/* 1586 */     Element rootNode = doc.createElement("ManagedServer-response");
/* 1587 */     rootNode.setAttribute("uri", uri);
/* 1588 */     doc.appendChild(rootNode);
/* 1589 */     Element resultNode = doc.createElement("Result");
/* 1590 */     rootNode.appendChild(resultNode);
/* 1591 */     Element responseNode = doc.createElement("Response");
/* 1592 */     responseNode.setAttribute("response-code", "4000");
/* 1593 */     resultNode.appendChild(responseNode);
/*      */     try
/*      */     {
/* 1596 */       Iterator<String> it = fieldSynchstatus.keySet().iterator();
/* 1597 */       while (it.hasNext())
/*      */       {
/* 1599 */         String fieldId = (String)it.next();
/* 1600 */         Properties prop = (Properties)fieldSynchstatus.get(fieldId);
/* 1601 */         String status = prop.getProperty("STATUS");
/* 1602 */         String action = prop.getProperty("ACTION");
/* 1603 */         Element nodeMonitor = doc.createElement("SYNCHSTATUS");
/* 1604 */         nodeMonitor.setAttribute("FIELDID", fieldId);
/* 1605 */         nodeMonitor.setAttribute("ACTION", action);
/* 1606 */         nodeMonitor.setAttribute("STATUS", status);
/* 1607 */         responseNode.appendChild(nodeMonitor);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1611 */       e.printStackTrace();
/*      */     }
/* 1613 */     TransformerFactory factory = TransformerFactory.newInstance();
/* 1614 */     Transformer transformer = factory.newTransformer();
/* 1615 */     transformer.setOutputProperty("indent", "yes");
/* 1616 */     StringWriter sw = new StringWriter();
/* 1617 */     StreamResult result = new StreamResult(sw);
/* 1618 */     DOMSource source = new DOMSource(doc);
/* 1619 */     transformer.transform(source, result);
/* 1620 */     outputString = sw.toString();
/* 1621 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String generateXML(HttpServletRequest request, HttpServletResponse response, String message, String errorCode) throws Exception
/*      */   {
/* 1626 */     return generateXML(request, response, message, errorCode, true);
/*      */   }
/*      */   
/*      */   public static String generateXML(HttpServletRequest request, HttpServletResponse response, String message, String errorCode, boolean needTextNode)
/*      */     throws Exception
/*      */   {
/* 1632 */     String uri = "xml";
/*      */     try {
/* 1634 */       uri = request.getRequestURI();
/*      */     } catch (Exception e) {
/* 1636 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1639 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1640 */     if (isJsonFormat) {
/* 1641 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/* 1644 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/* 1647 */     HashMap<String, String> errorMap = new HashMap();
/* 1648 */     errorMap.put("response-code", errorCode);
/* 1649 */     errorMap.put("uri", uri);
/* 1650 */     errorMap.put("message", message);
/* 1651 */     errorMap.put("nodeName", "message");
/* 1652 */     errorMap.put("needTextNode", needTextNode ? "true" : "false");
/* 1653 */     if (request.getAttribute("resourceid") != null) {
/* 1654 */       errorMap.put("resourceid", (String)request.getAttribute("resourceid"));
/*      */     }
/* 1656 */     return generateResp(errorMap, isJsonFormat);
/*      */   }
/*      */   
/*      */   public static String generateResp(HashMap<String, String> results, boolean isJsonFormat) throws Exception
/*      */   {
/* 1661 */     String toReturn = "";
/*      */     try
/*      */     {
/* 1664 */       String responseCode = (String)results.get("response-code");
/* 1665 */       String uri = (String)results.get("uri");
/* 1666 */       String nodeName = (String)results.get("nodeName");
/* 1667 */       String message = (String)results.get("message");
/* 1668 */       String textnode = (String)results.get("needTextNode");
/* 1669 */       String masDispName = (String)results.get("TargetMasDispName");
/* 1670 */       AMLog.info("RESTAPI: OutputString : Results:" + results);
/*      */       
/* 1672 */       if (isJsonFormat)
/*      */       {
/* 1674 */         JSONObject jsonOutput = new JSONObject();
/* 1675 */         JSONObject jsonResponse = new JSONObject();
/* 1676 */         JSONArray jsonResultArray = new JSONArray();
/*      */         
/*      */         try
/*      */         {
/* 1680 */           jsonResultArray.put(new JSONObject().put("message", message));
/* 1681 */           if ((EnterpriseUtil.isAdminServer()) && (masDispName != null) && (masDispName.length() > 0)) {
/* 1682 */             jsonResultArray.put(new JSONObject().put("managed-server", masDispName));
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1687 */           AMLog.debug("REST API : Error in generating JSON Feed!");
/* 1688 */           responseCode = "40xx";
/* 1689 */           jsonResultArray = new JSONArray();
/* 1690 */           jsonResultArray.put(new JSONObject().put("message", FormatUtil.getString("Error in generating JSON Feed!")));
/* 1691 */           e.printStackTrace();
/*      */         }
/* 1693 */         jsonOutput.put("response-code", responseCode);
/* 1694 */         jsonResponse.put("uri", uri);
/* 1695 */         jsonResponse.put("result", jsonResultArray);
/* 1696 */         jsonOutput.put("response", jsonResponse);
/* 1697 */         if (results.containsKey("resourceid"))
/*      */         {
/* 1699 */           jsonOutput.put("resourceid", (String)results.get("resourceid"));
/*      */         }
/* 1701 */         toReturn = jsonOutput.toString();
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 1707 */           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 1708 */           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 1709 */           Document doc = docBuilder.newDocument();
/*      */           
/* 1711 */           AMLog.debug("REST API : root node uri" + uri);
/* 1712 */           Element rootNode = doc.createElement("AppManager-response");
/* 1713 */           rootNode.setAttribute("uri", uri);
/* 1714 */           doc.appendChild(rootNode);
/* 1715 */           Element resultNode = doc.createElement("result");
/* 1716 */           rootNode.appendChild(resultNode);
/*      */           
/* 1718 */           Element responseNode = doc.createElement("response");
/* 1719 */           responseNode.setAttribute("response-code", responseCode);
/* 1720 */           if (results.containsKey("resourceid"))
/*      */           {
/* 1722 */             responseNode.setAttribute("resourceid", (String)results.get("resourceid"));
/*      */           }
/* 1724 */           resultNode.appendChild(responseNode);
/*      */           
/* 1726 */           Element nodeElement = doc.createElement(nodeName);
/* 1727 */           if (textnode.equals("true"))
/*      */           {
/* 1729 */             Text textMSG = doc.createTextNode(message);
/* 1730 */             nodeElement.appendChild(textMSG);
/*      */           }
/*      */           else
/*      */           {
/* 1734 */             nodeElement.setAttribute("message", message);
/* 1735 */             if ((EnterpriseUtil.isAdminServer()) && (masDispName != null) && (masDispName.length() > 0)) {
/* 1736 */               nodeElement.setAttribute("managed-server", masDispName);
/*      */             }
/*      */           }
/* 1739 */           responseNode.appendChild(nodeElement);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1744 */           TransformerFactory factory = TransformerFactory.newInstance();
/* 1745 */           Transformer transformer = factory.newTransformer();
/* 1746 */           transformer.setOutputProperty("indent", "yes");
/* 1747 */           StringWriter sw = new StringWriter();
/* 1748 */           StreamResult streamResult = new StreamResult(sw);
/* 1749 */           DOMSource source = new DOMSource(doc);
/* 1750 */           transformer.transform(source, streamResult);
/* 1751 */           toReturn = sw.toString();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1755 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1760 */       e.printStackTrace();
/*      */     }
/* 1762 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static String ListMonitorXML(HttpServletRequest request, HttpServletResponse response, String query, String type, boolean isJsonFormat) throws Exception
/*      */   {
/* 1767 */     String outputString = "";
/* 1768 */     String errorCode = "";
/* 1769 */     String message = "";
/* 1770 */     AMLog.debug("REST API : ListMonitor Query:" + query);
/* 1771 */     AMLog.debug("REST API : inside listmonitor method " + type);
/* 1772 */     boolean avoidParentDetails = (request.getParameter("avoidGroupDetailsToSpeedUp") != null ? Boolean.valueOf(request.getParameter("avoidGroupDetailsToSpeedUp")) : Boolean.FALSE).booleanValue();
/* 1773 */     if (isJsonFormat)
/*      */     {
/* 1775 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/* 1779 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1781 */     String uri = request.getRequestURI();
/* 1782 */     String sortby = request.getParameter("SortBy") != null ? request.getParameter("SortBy") : "HEALTHSEVERITY,AVAILABILITYSEVERITY,DISPLAYNAME,RESOURCEID";
/* 1783 */     ResultSet rs = null;
/*      */     
/* 1785 */     HashMap results = new HashMap();
/* 1786 */     results.put("response-code", "4000");
/* 1787 */     results.put("uri", uri);
/*      */     
/* 1789 */     results.put("sortingParam", sortby);
/* 1790 */     if (request.getParameter("SortOrder") != null)
/*      */     {
/* 1792 */       results.put("sortingOrder", request.getParameter("SortOrder").toLowerCase());
/*      */     }
/*      */     
/* 1795 */     HashMap extDeviceMap = null;
/* 1796 */     if (Constants.isExtDeviceConfigured())
/*      */     {
/* 1798 */       if (Constants.isIt360)
/*      */       {
/* 1800 */         extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", true);
/*      */       }
/*      */       else
/*      */       {
/* 1804 */         extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */       }
/*      */     }
/*      */     
/* 1808 */     String resourceType = "";
/* 1809 */     String resourceId = "";
/* 1810 */     String healthid = "";
/* 1811 */     String availid = "";
/*      */     try
/*      */     {
/* 1814 */       ArrayList<Hashtable<String, String>> monitors = new ArrayList();
/* 1815 */       urlList = new StringBuilder();
/* 1816 */       StringBuilder dnsList = new StringBuilder();
/* 1817 */       StringBuilder serviceList = new StringBuilder();
/* 1818 */       StringBuilder sslCertificateMonitorList = new StringBuilder();
/* 1819 */       HashMap confMonitorsList = new HashMap();
/* 1820 */       Hashtable<String, Hashtable<String, String>> h = new Hashtable();
/* 1821 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1822 */       boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 1823 */       while (rs.next())
/*      */       {
/* 1825 */         Hashtable<String, String> monitorDetails = new Hashtable();
/* 1826 */         resourceId = rs.getString("RESOURCEID");
/* 1827 */         resourceType = rs.getString("TYPE");
/* 1828 */         boolean isConfMonitor = ConfMonitorConfiguration.getInstance().isConfMonitor(resourceType);
/* 1829 */         String displayname = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 1830 */         if (EnterpriseUtil.isAdminServer()) {
/* 1831 */           displayname = displayname + "_" + CommDBUtil.getManagedServerNameWithPort(resourceId);
/*      */         }
/* 1833 */         monitorDetails.put("DISPLAYNAME", displayname);
/* 1834 */         monitorDetails.put("RESOURCEID", resourceId);
/* 1835 */         monitorDetails.put("TYPESHORTNAME", rs.getString("typeshortname"));
/* 1836 */         monitorDetails.put("TYPE", rs.getString("TYPE"));
/* 1837 */         monitorDetails.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 1838 */         monitorDetails.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 1839 */         monitorDetails.put("Managed", rs.getString("Managed"));
/* 1840 */         monitorDetails.put("IMAGEPATH", rs.getString("IMAGEPATH"));
/* 1841 */         monitorDetails.put("LASTALARMTIME", rs.getString("MODTIME") != null ? rs.getString("MODTIME") : "0");
/* 1842 */         monitorDetails.put("PORT", rs.getString("APPLNDISCPORT") != null ? rs.getString("APPLNDISCPORT") : "-1");
/* 1843 */         healthid = AMAttributesCache.getHealthId(resourceType);
/* 1844 */         availid = AMAttributesCache.getAvailabilityId(resourceType);
/* 1845 */         HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(resourceId, resourceType);
/* 1846 */         String monitorDetailsURL = "/showresource.do?resourceid=" + resourceId + "&method=showResourceForResourceID&PRINTER_FRIENDLY=true";
/* 1847 */         if (resourceType.equals("HAI"))
/*      */         {
/* 1849 */           monitorDetailsURL = "/showapplication.do?method=showApplication&haid=" + resourceId + "&PRINTER_FRIENDLY=true";
/* 1850 */         } else if ("UrlMonitor".equals(monitorDetails.get("TYPE"))) {
/* 1851 */           urlList.append(resourceId).append(",");
/* 1852 */         } else if (("DNSMonitor".equals(monitorDetails.get("TYPE"))) && (!EnterpriseUtil.isAdminServer())) {
/* 1853 */           dnsList.append(resourceId).append(",");
/* 1854 */         } else if (("Port-Test".equals(monitorDetails.get("TYPE"))) && (!EnterpriseUtil.isAdminServer())) {
/* 1855 */           serviceList.append(resourceId).append(",");
/* 1856 */         } else if (("SSLCertificateMonitor".equals(monitorDetails.get("TYPE"))) && (!EnterpriseUtil.isAdminServer())) {
/* 1857 */           sslCertificateMonitorList.append(resourceId).append(",");
/* 1858 */         } else if ((extDeviceMap != null) && (extDeviceMap.get(resourceId) != null))
/*      */         {
/* 1860 */           monitorDetailsURL = (String)extDeviceMap.get(resourceId);
/*      */         }
/* 1862 */         else if (isConfMonitor) {
/* 1863 */           StringBuilder confIds = confMonitorsList.containsKey(resourceType) ? (StringBuilder)confMonitorsList.get(resourceType) : new StringBuilder();
/* 1864 */           if (confIds.length() > 0) {
/* 1865 */             confIds.append(",");
/*      */           }
/* 1867 */           confIds.append(resourceId);
/* 1868 */           confMonitorsList.put(resourceType, confIds);
/*      */         }
/* 1870 */         String rcaURL = "/jsp/RCA.jsp?resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + healthid;
/*      */         
/* 1872 */         monitorDetails.put("DetailsPageURL", monitorDetailsURL);
/* 1873 */         monitorDetails.put("RCAPageURL", rcaURL);
/* 1874 */         monitorDetails.put("HEALTHATTRIBUTEID", healthid);
/* 1875 */         monitorDetails.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 1876 */         monitorDetails.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/* 1877 */         monitorDetails.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/* 1878 */         monitorDetails.put("AVAILABILITYATTRIBUTEID", availid);
/* 1879 */         monitorDetails.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 1880 */         monitorDetails.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 1881 */         monitorDetails.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 1882 */         if (!avoidParentDetails) {
/* 1883 */           String associatedGroups = getAssociatedMGroupsNameWithId(resourceId);
/* 1884 */           monitorDetails.put("ASSOCIATEDGROUPS", associatedGroups);
/*      */         }
/* 1886 */         if (isConfMonitor) {
/* 1887 */           String hostName = ConfMonitorUtil.getHostName(resourceType, resourceId, false);
/* 1888 */           String hostIp = "";
/* 1889 */           if (hostName != null) {
/*      */             try {
/* 1891 */               hostIp = InetAddress.getByName(hostName).getHostAddress();
/*      */             } catch (Exception e) {
/* 1893 */               AMLog.debug("REST API : ListMonitor API : Unable to resolve Ip for Hostname : " + hostName + ", Type : " + resourceType + " and ResourceID : " + resourceId + ". Error Message " + e.getMessage());
/*      */             }
/*      */           }
/* 1896 */           monitorDetails.put("HOSTNAME", hostName != null ? hostName : "");
/* 1897 */           monitorDetails.put("HOSTIP", hostIp);
/* 1898 */         } else if (Constants.serverTypes.indexOf(resourceType) != -1) {
/* 1899 */           monitorDetails.put("HOSTNAME", rs.getString("RESOURCENAME"));
/* 1900 */           monitorDetails.put("HOSTIP", rs.getString("TARGETADDRESS") != null ? rs.getString("TARGETADDRESS") : "");
/*      */         } else {
/* 1902 */           monitorDetails.put("HOSTNAME", rs.getString("TARGETNAME") != null ? rs.getString("TARGETNAME") : "");
/* 1903 */           monitorDetails.put("HOSTIP", rs.getString("TARGETADDRESS") != null ? rs.getString("TARGETADDRESS") : "");
/*      */         }
/* 1905 */         monitors.add(monitorDetails);
/* 1906 */         h.put(resourceId, monitorDetails);
/*      */       }
/* 1908 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1910 */       if (urlList.length() != 0) {
/*      */         try {
/* 1912 */           String detailsQuery = DBQueryUtil.getDBQuery("am.restapi.listmonitor.query", new String[] { urlList.substring(0, urlList.length() - 1) });
/* 1913 */           AMLog.info("REST API : List URL Monitor Query : " + detailsQuery);
/* 1914 */           rs = AMConnectionPool.executeQueryStmt(detailsQuery);
/* 1915 */           while (rs.next()) {
/* 1916 */             Hashtable<String, String> monitorDetails = (Hashtable)h.get(rs.getString("URLID"));
/* 1917 */             monitorDetails.put("URL", rs.getString("URL"));
/* 1918 */             monitorDetails.put("REQUESTPARAMETERS", rs.getString("QUERYSTRING"));
/* 1919 */             monitorDetails.put("USERNAME", rs.getString("USERID"));
/* 1920 */             monitorDetails.put("METHOD", rs.getString("METHOD"));
/* 1921 */             monitorDetails.put("POLL", String.valueOf(rs.getInt("POLL")));
/* 1922 */             monitorDetails.put("TIMEOUT", String.valueOf(rs.getInt("TIMEOUT")));
/* 1923 */             monitorDetails.put("CHECKCONTENT", rs.getString("AVAILABILITYSTRING"));
/* 1924 */             monitorDetails.put("ERRORCONTENT", rs.getString("UNAVAILABILITYSTRING"));
/* 1925 */             monitorDetails.put("RETRY", rs.getString("VERIFY"));
/*      */           }
/*      */         } catch (Exception e) {
/* 1928 */           AMLog.debug("REST API : Error occured in listmonitor URL details method. Error Message : " + e.getMessage());
/* 1929 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/* 1935 */       if (dnsList.length() != 0) {
/*      */         try {
/* 1937 */           int baseid = NewMonitorUtil.getBaseId("DNSMonitor");
/* 1938 */           String detailsQuery = "select AM_ARGS_" + baseid + ".*,pollinterval from AM_ARGS_" + baseid + ", AM_ScriptArgs where AM_ARGS_" + baseid + ".RESOURCEID=AM_ScriptArgs.resourceid and AM_ARGS_" + baseid + ".RESOURCEID in (" + dnsList.substring(0, dnsList.length() - 1) + ")";
/* 1939 */           AMLog.info("REST API : List DNS Monitor Query : " + detailsQuery);
/* 1940 */           rs = AMConnectionPool.executeQueryStmt(detailsQuery);
/* 1941 */           while (rs.next()) {
/* 1942 */             Hashtable<String, String> monitorDetails = (Hashtable)h.get(rs.getString("RESOURCEID"));
/* 1943 */             monitorDetails.put("TARGETADDRESS", rs.getString("Target Address"));
/* 1944 */             monitorDetails.put("LOOKUPADDRESS", rs.getString("Lookup Address"));
/* 1945 */             monitorDetails.put("TIMEOUT", rs.getString("Timeout"));
/* 1946 */             if ("-None-".equals(rs.getString("Record Type").trim())) {
/* 1947 */               monitorDetails.put("RECORDTYPE", "");
/*      */             } else {
/* 1949 */               monitorDetails.put("RECORDTYPE", rs.getString("Record Type"));
/*      */             }
/* 1951 */             if ("-None-".equals(rs.getString("Search Field").trim())) {
/* 1952 */               monitorDetails.put("SEARCHFIELD", "");
/*      */             } else {
/* 1954 */               monitorDetails.put("SEARCHFIELD", rs.getString("Search Field"));
/*      */             }
/* 1956 */             monitorDetails.put("SEARCHVALUE", rs.getString("Search Value"));
/* 1957 */             monitorDetails.put("POLL", String.valueOf(Integer.valueOf(rs.getInt("pollinterval") / 60)));
/*      */           }
/*      */         } catch (Exception e) {
/* 1960 */           AMLog.debug("REST API : Error occured in listmonitor DNS details method. Error Message : " + e.getMessage());
/* 1961 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/* 1967 */       if (serviceList.length() != 0) {
/*      */         try
/*      */         {
/* 1970 */           int baseid = NewMonitorUtil.getBaseId("Port-Test");
/* 1971 */           String detailsQuery = "select AM_ARGS_" + baseid + ".*,pollinterval from AM_ARGS_" + baseid + ", AM_ScriptArgs where AM_ARGS_" + baseid + ".RESOURCEID=AM_ScriptArgs.resourceid and AM_ARGS_" + baseid + ".RESOURCEID in (" + serviceList.substring(0, serviceList.length() - 1) + ")";
/* 1972 */           AMLog.info("REST API : List Service Monitor Query : " + detailsQuery);
/* 1973 */           rs = AMConnectionPool.executeQueryStmt(detailsQuery);
/* 1974 */           while (rs.next()) {
/* 1975 */             Hashtable<String, String> monitorDetails = (Hashtable)h.get(rs.getString("RESOURCEID"));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1980 */             monitorDetails.put("HOST", rs.getString("host"));
/* 1981 */             monitorDetails.put("COMMAND", rs.getString("command"));
/* 1982 */             monitorDetails.put("SEARCH", rs.getString("search"));
/* 1983 */             monitorDetails.put("PORT", rs.getString("port"));
/* 1984 */             monitorDetails.put("TIMEOUT", rs.getString("timeout"));
/* 1985 */             monitorDetails.put("POLL", String.valueOf(Integer.valueOf(rs.getInt("pollinterval") / 60)));
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 1989 */           AMLog.debug("REST API : Error occured in listmonitor for Service Monitor details method. Error Message : " + e.getMessage());
/* 1990 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/* 1996 */       if (sslCertificateMonitorList.length() != 0) {
/*      */         try {
/* 1998 */           int baseid = NewMonitorUtil.getBaseId("SSLCertificateMonitor");
/* 1999 */           String detailsQuery = "select AM_ARGS_" + baseid + ".*,pollinterval from AM_ARGS_" + baseid + ", AM_ScriptArgs where AM_ARGS_" + baseid + ".RESOURCEID=AM_ScriptArgs.resourceid and AM_ARGS_" + baseid + ".RESOURCEID in (" + sslCertificateMonitorList.substring(0, sslCertificateMonitorList.length() - 1) + ")";
/* 2000 */           AMLog.info("REST API : List SSL Certificate Monitor Query : " + detailsQuery);
/* 2001 */           rs = AMConnectionPool.executeQueryStmt(detailsQuery);
/* 2002 */           while (rs.next()) {
/* 2003 */             Hashtable<String, String> monitorDetails = (Hashtable)h.get(rs.getString("RESOURCEID"));
/* 2004 */             monitorDetails.put("DOMAIN", rs.getString("domain"));
/* 2005 */             monitorDetails.put("PORT", rs.getString("port"));
/* 2006 */             monitorDetails.put("PROXYNEEDED", rs.getString("isProxyNeeded"));
/* 2007 */             monitorDetails.put("HOSTNAMEERROR", rs.getString("ignoreHostNameError"));
/* 2008 */             monitorDetails.put("TIMEOUT", rs.getString("Timeout"));
/* 2009 */             monitorDetails.put("POLL", String.valueOf(Integer.valueOf(rs.getInt("pollinterval") / 60)));
/*      */           }
/*      */         } catch (Exception e) {
/* 2012 */           AMLog.debug("REST API : Error occured in listmonitor for SSL Certificate Monitor details method. Error Message : " + e.getMessage());
/* 2013 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/* 2018 */       if (!confMonitorsList.isEmpty())
/*      */       {
/* 2020 */         Iterator monitorTypes = confMonitorsList.keySet().iterator();
/* 2021 */         while (monitorTypes.hasNext()) {
/* 2022 */           String typeName = (String)monitorTypes.next();
/* 2023 */           StringBuilder residList = (StringBuilder)confMonitorsList.get(typeName);
/* 2024 */           String baseid = Constants.getTypeId(typeName);
/* 2025 */           HashMap uniqueParamsVsRestAPIKey = ConfMonitorConfiguration.getInstance().getUniqueParamsVsRestAPIKey(typeName);
/*      */           
/* 2027 */           if ((uniqueParamsVsRestAPIKey != null) && (residList.length() != 0)) {
/* 2028 */             String detailsQuery = "select AM_ARGS_" + baseid + ".* from AM_ARGS_" + baseid + " where AM_ARGS_" + baseid + ".RESOURCEID in (" + residList + ")";
/* 2029 */             AMLog.info("REST API : List conf monitor Monitor Query : " + detailsQuery);
/* 2030 */             ResultSet confrs = null;
/*      */             try {
/* 2032 */               confrs = AMConnectionPool.executeQueryStmt(detailsQuery);
/* 2033 */               while (confrs.next()) {
/* 2034 */                 Hashtable<String, String> monitorDetails = (Hashtable)h.get(confrs.getString("RESOURCEID"));
/* 2035 */                 Iterator colValues = uniqueParamsVsRestAPIKey.keySet().iterator();
/* 2036 */                 while (colValues.hasNext()) {
/* 2037 */                   String colName = (String)colValues.next();
/* 2038 */                   monitorDetails.put(colName, confrs.getString(colName));
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception e) {
/* 2043 */               AMLog.debug("REST API : Error occured in listmonitor for " + typeName + " Monitor details method. Error Message : " + e.getMessage());
/* 2044 */               e.printStackTrace();
/*      */             }
/*      */             finally {}
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2052 */       results.put("result", monitors);
/* 2053 */       results.put("nodeName", "Monitor");
/* 2054 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */       StringBuilder urlList;
/* 2059 */       ex.printStackTrace();
/* 2060 */       AMLog.debug("REST API : Server error");
/* 2061 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 2062 */       errorCode = "4128";
/* 2063 */       return generateXML(request, response, message, errorCode);
/*      */     }
/*      */     finally
/*      */     {
/* 2067 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2070 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getImmediateParentGroupNames(String resourceid)
/*      */   {
/*      */     try {
/* 2076 */       Vector<String> parentresids = new Vector();
/* 2077 */       ParentChildRelationalUtil.getAllParentIds(parentresids, resourceid, false);
/* 2078 */       if (parentresids.size() > 0)
/*      */       {
/* 2080 */         Iterator<String> indresid = parentresids.iterator();
/* 2081 */         StringBuilder residlist = new StringBuilder();
/* 2082 */         while (indresid.hasNext())
/*      */         {
/* 2084 */           residlist.append(getResourceName((String)indresid.next()) + ",");
/*      */         }
/* 2086 */         return residlist.substring(0, residlist.length() - 1);
/*      */       }
/* 2088 */       return "-";
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2092 */       e.printStackTrace(); }
/* 2093 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getAssociatedMGroupsNameWithId(String monitorId)
/*      */   {
/*      */     try
/*      */     {
/* 2105 */       Vector<String> associatedMGroupIds = new Vector();
/* 2106 */       ParentChildRelationalUtil.getAllParentIds(associatedMGroupIds, monitorId, false);
/* 2107 */       if (associatedMGroupIds.size() == 0) {
/* 2108 */         return "-";
/*      */       }
/* 2110 */       StringBuilder groupDetails = new StringBuilder();
/* 2111 */       Iterator<String> itr = associatedMGroupIds.iterator();
/* 2112 */       while (itr.hasNext()) {
/* 2113 */         String id = (String)itr.next();
/* 2114 */         groupDetails.append("Name").append(":").append(getResourceName(id)).append(";");
/* 2115 */         groupDetails.append("Id").append(":").append(id).append(",");
/*      */       }
/* 2117 */       groupDetails.deleteCharAt(groupDetails.length() - 1);
/* 2118 */       return groupDetails.toString();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2122 */       e.printStackTrace(); }
/* 2123 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getResourceName(String resourceId)
/*      */   {
/* 2129 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2132 */       rs = AMConnectionPool.executeQueryStmt("select DisplayName from AM_ManagedObject where resourceid=" + resourceId);
/* 2133 */       if (rs.next())
/*      */       {
/* 2135 */         return EnterpriseUtil.decodeString(rs.getString("DisplayName"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2140 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2144 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2146 */     return null;
/*      */   }
/*      */   
/*      */   public static String ApplyLicense(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 2151 */     AMLog.debug("REST API: ApplyLicense called!");
/*      */     try
/*      */     {
/* 2154 */       String loginUsername = CommonAPIUtil.getUsername(request.getParameter("apikey"));
/* 2155 */       boolean isdelegAdmin = DBUtil.isDelegatedAdmin(loginUsername);
/* 2156 */       if (isdelegAdmin) {
/* 2157 */         return generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/* 2159 */       String licFilePath = System.getProperty("user.dir") + File.separator + "classes" + File.separator;
/* 2160 */       String fileName = "RegisteredLicense.xml";
/* 2161 */       if (PluginUtil.isPlugin()) {
/* 2162 */         licFilePath = licFilePath + "ApmPlugin_AdventNetLicense.xml";
/*      */       }
/*      */       else {
/* 2165 */         licFilePath = licFilePath + fileName;
/* 2166 */         boolean isMultipart = ServletFileUpload.isMultipartContent(request);
/* 2167 */         OutputStream out = new FileOutputStream(new File(licFilePath));
/* 2168 */         if (isMultipart) {
/* 2169 */           int boundaryIndex = request.getContentType().indexOf("boundary=");
/* 2170 */           byte[] boundary = request.getContentType().substring(boundaryIndex + 9).getBytes();
/* 2171 */           MultipartStream multipartStream = new MultipartStream(request.getInputStream(), boundary);
/* 2172 */           multipartStream.readHeaders();
/* 2173 */           OutputStream data = new FileOutputStream(new File(licFilePath));
/* 2174 */           multipartStream.readBodyData(data);
/* 2175 */           data.close();
/*      */         }
/*      */         else {
/* 2178 */           String filePath = request.getParameter("filePath");
/* 2179 */           if ((filePath == null) || (filePath.equals(""))) {
/* 2180 */             return generateXML(request, response, FormatUtil.getString("am.webclient.valid.filepath.text"), "4444");
/*      */           }
/*      */           
/* 2183 */           InputStream in = new FileInputStream(new File(filePath));
/* 2184 */           byte[] buf = new byte['Ѐ'];
/*      */           int len;
/* 2186 */           while ((len = in.read(buf)) > 0) {
/* 2187 */             out.write(buf, 0, len);
/*      */           }
/* 2189 */           in.close();
/*      */         }
/*      */         
/* 2192 */         out.close();
/*      */       }
/* 2194 */       File f = new File(licFilePath);
/* 2195 */       fileName = f.getName();
/* 2196 */       RegisterAction regAction = new RegisterAction("aa");
/* 2197 */       AMLog.debug("RESTAPI: Registering LicenseFile NAME:" + f.getName());
/* 2198 */       boolean status = regAction.registerLicense(fileName);
/*      */       
/* 2200 */       AMLog.debug("RESTAPI: Registering LicenseFile RESULT:" + status);
/*      */       
/* 2202 */       HashMap<String, String> resultMap = new HashMap();
/* 2203 */       resultMap.put("response-code", status ? "4000" : "4444");
/* 2204 */       resultMap.put("uri", request.getRequestURI());
/* 2205 */       resultMap.put("message", status ? FormatUtil.getString("license.registration.api.success") : FormatUtil.getString("license.registration.api.failed"));
/* 2206 */       resultMap.put("nodeName", "Message");
/* 2207 */       resultMap.put("needTextNode", "true");
/* 2208 */       return generateResp(resultMap, isJsonFormat);
/*      */     }
/*      */     catch (Exception e) {
/* 2211 */       e.printStackTrace();
/* 2212 */       AMLog.debug("URITree: Exception occurred in ApplyLicense" + e.getMessage()); }
/* 2213 */     return generateXML(request, response, FormatUtil.getString("license.registration.api.failed"), "4444");
/*      */   }
/*      */   
/*      */   public static String LicenseInfoXML(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2219 */     AMLog.debug("REST API : inside LicenseInfo method");
/* 2220 */     FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 2221 */     Wield w = Wield.getInstance();
/*      */     
/* 2223 */     String uri = request.getRequestURI();
/* 2224 */     boolean isJsonFormat = uri.contains("json");
/* 2225 */     if (isJsonFormat)
/*      */     {
/* 2227 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/* 2231 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/* 2234 */     String evaluationExpiryDate = w.getEvaluationExpiryDate();
/* 2235 */     String outputString = "";String query = "";String numberofnetworkdevices = "";String LicenseType = "";
/* 2236 */     String sapStatus = "false";String sapCCMSStatus = "false";String As400Status = "false";String As400AdminStatus = "false";String OpmanStatus = "false";String WebTransactionStatus = "false";
/* 2237 */     String MqSeriesStatus = "false";String SharePointStatus = "false";String AnomalyStatus = "false";String RBMStatus = "false";String OpStorStatus = "false";String OracleEBSStatus = "false";String siebelStatus = "false";
/*      */     
/* 2239 */     String sapMsg = "";String sapCCMSMsg = "";String As400Msg = "";String As400AdminMsg = "";String OpmanMSG = "";String WebTransactionMSG = "";
/* 2240 */     String AnomalyMSG = "";String SharePointMSG = "";String MqSeriesMSG = "";String RBMMSG = "";String OpStorMSG = "";String OracleEBSMSG = "";String siebelMsg = "";
/*      */     
/* 2242 */     int unmanagedcount = 0;
/* 2243 */     int totalmonitors = DBUtil.getNumberOfMonitors();
/*      */     
/* 2245 */     ResultSet rs = null;
/*      */     
/* 2247 */     query = "select count(*) as COUNT from AM_UnManagedNodes umo,AM_ManagedObject mo where mo.type in " + Constants.resourceTypes + " and mo.resourceid=umo.resid";
/* 2248 */     rs = AMConnectionPool.executeQueryStmt(query);
/*      */     try
/*      */     {
/* 2251 */       if (rs.next())
/*      */       {
/* 2253 */         unmanagedcount = Integer.parseInt(rs.getString("COUNT"));
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2258 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2262 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2265 */     query = "select count(*) as COUNT from AM_AssociatedExtDevices inner join AM_ManagedObject on  AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID";
/* 2266 */     rs = AMConnectionPool.executeQueryStmt(query);
/*      */     try
/*      */     {
/* 2269 */       if (rs.next())
/*      */       {
/* 2271 */         numberofnetworkdevices = rs.getString("COUNT");
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2276 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2280 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2283 */     StringTokenizer st = new StringTokenizer(evaluationExpiryDate);
/* 2284 */     if (st.countTokens() == 3)
/*      */     {
/* 2286 */       String year = st.nextToken();
/* 2287 */       String month = st.nextToken();
/* 2288 */       String day = st.nextToken();
/* 2289 */       String[] Months = { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/*      */       try
/*      */       {
/* 2292 */         month = Months[Integer.parseInt(month)];
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 2296 */         exp.printStackTrace();
/*      */       }
/* 2298 */       evaluationExpiryDate = month + " " + day + ", " + year;
/*      */     }
/*      */     
/* 2301 */     if (fd.getUserType().equals("R"))
/*      */     {
/* 2303 */       LicenseType = "Registered";
/* 2304 */       if ((fd.isSAPAddOnPresent()) || (FreeEditionDetails.sapMessage != null))
/*      */       {
/* 2306 */         sapStatus = "true";
/* 2307 */         sapMsg = FreeEditionDetails.sapMessage != null ? FreeEditionDetails.sapMessage : "";
/*      */       }
/* 2309 */       if ((fd.isSAPCCMSAddOnPresent()) || (FreeEditionDetails.sapccmsMessage != null))
/*      */       {
/* 2311 */         sapCCMSStatus = "true";
/* 2312 */         sapCCMSMsg = FreeEditionDetails.sapccmsMessage != null ? FreeEditionDetails.sapccmsMessage : "";
/*      */       }
/* 2314 */       if ((fd.isAS400AddOnPresent()) || (FreeEditionDetails.as400Message != null))
/*      */       {
/* 2316 */         As400Status = "true";
/* 2317 */         As400Msg = FreeEditionDetails.as400Message != null ? FreeEditionDetails.as400Message : "";
/*      */       }
/* 2319 */       if ((fd.isAS400adminAddOnPresent()) || (FreeEditionDetails.as400Message != null))
/*      */       {
/* 2321 */         As400AdminStatus = "true";
/* 2322 */         As400Msg = FreeEditionDetails.as400Message != null ? FreeEditionDetails.as400Message : "";
/*      */       }
/* 2324 */       if (fd.isOpmanagerConnectorPresent())
/*      */       {
/* 2326 */         OpmanStatus = "true";
/* 2327 */         OpmanMSG = FreeEditionDetails.addOnEvalDaysProps.getProperty("NetworkMonitoringConnector") == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("NetworkMonitoringConnector") });
/*      */       }
/* 2329 */       if (fd.isWebTransaction())
/*      */       {
/* 2331 */         WebTransactionStatus = "true";
/* 2332 */         WebTransactionMSG = FreeEditionDetails.addOnEvalDaysProps.getProperty("J2EEWebTransaction") == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("J2EEWebTransaction") });
/*      */       }
/* 2334 */       if (fd.isMqSeries())
/*      */       {
/* 2336 */         MqSeriesStatus = "true";
/* 2337 */         MqSeriesMSG = FreeEditionDetails.addOnEvalDaysProps.getProperty("WebSphereMQ") == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("WebSphereMQ") });
/*      */       }
/* 2339 */       if (fd.isSharePoint())
/*      */       {
/* 2341 */         SharePointStatus = "true";
/* 2342 */         SharePointMSG = FreeEditionDetails.addOnEvalDaysProps.getProperty("OfficeSharePoint") == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("OfficeSharePoint") });
/*      */       }
/* 2344 */       if ((fd.isAnomalyAddOnPresent()) || (FreeEditionDetails.anomalyMessage != null))
/*      */       {
/* 2346 */         AnomalyStatus = "true";
/* 2347 */         AnomalyMSG = FreeEditionDetails.anomalynodrMessage == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.anomalynodrMessage });
/*      */       }
/* 2349 */       if ((fd.isRBMAddOnPresent()) || (FreeEditionDetails.rbmMessage != null))
/*      */       {
/* 2351 */         RBMStatus = "true";
/* 2352 */         RBMMSG = FreeEditionDetails.rbmnodrMessage == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.rbmnodrMessage });
/*      */       }
/* 2354 */       if (fd.isOpstorConnectorPresent())
/*      */       {
/* 2356 */         OpStorStatus = "true";
/* 2357 */         OpStorMSG = FreeEditionDetails.addOnEvalDaysProps.getProperty("OpStorConnector") == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.addOnEvalDaysProps.getProperty("OpStorConnector") });
/*      */       }
/* 2359 */       if (fd.isOracleEBSAllowed())
/*      */       {
/* 2361 */         OracleEBSStatus = "true";
/* 2362 */         OracleEBSMSG = FreeEditionDetails.oracleEBSMessage1 == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.oracleEBSMessage1 });
/*      */       }
/* 2364 */       if (fd.isSiebelAllowed())
/*      */       {
/* 2366 */         siebelStatus = "true";
/* 2367 */         siebelMsg = FreeEditionDetails.siebelMessage == null ? "" : FormatUtil.getString("am.license.expires.text", new String[] { FreeEditionDetails.siebelMessage });
/*      */       }
/*      */     }
/* 2370 */     else if (fd.getUserType().equals("T"))
/*      */     {
/* 2372 */       LicenseType = "Evaluation";
/*      */     }
/* 2374 */     else if (fd.getUserType().equals("F"))
/*      */     {
/* 2376 */       LicenseType = "Free Edition";
/*      */     }
/* 2378 */     else if (fd.getUserType().equals("RT"))
/*      */     {
/* 2380 */       LicenseType = "Runtime";
/*      */     }
/*      */     
/* 2383 */     String responseCode = "4000";
/* 2384 */     AMLog.debug("REST API : root node uri" + uri);
/* 2385 */     AMLog.debug("REST API : inside method LicenseInfo API");
/*      */     
/* 2387 */     Object License = new ArrayList();
/* 2388 */     Hashtable<String, Object> LicenseInfo = new Hashtable();
/* 2389 */     LicenseInfo.put("Product", OEMUtil.getOEMString("product.name") + " " + OEMUtil.getOEMString("product.version"));
/* 2390 */     LicenseInfo.put("BuildNumber", OEMUtil.getOEMString("product.build.number"));
/* 2391 */     LicenseInfo.put("ServicePackDetails", AMAutomaticPortChanger.getServicePackVersion());
/* 2392 */     LicenseInfo.put("ExpiryDetails", evaluationExpiryDate);
/* 2393 */     LicenseInfo.put("CurrentlyUsedNumberOfMonitors", "" + totalmonitors);
/* 2394 */     LicenseInfo.put("Managed", "" + (totalmonitors - unmanagedcount));
/* 2395 */     LicenseInfo.put("UnManaged", "" + unmanagedcount);
/* 2396 */     LicenseInfo.put("CurrentlyUsedNumberOfUsers", "" + DBUtil.getNumberOfUsers());
/* 2397 */     LicenseInfo.put("NumberOfMonitorsForLicensing", "" + Constants.getNoofMonitors_withoutnatives());
/* 2398 */     LicenseInfo.put("NumberOfNetworkDevices", "" + numberofnetworkdevices);
/* 2399 */     LicenseInfo.put("LicenseType", LicenseType);
/* 2400 */     LicenseInfo.put("NumberOfDaysRemaining", String.valueOf(fd.getEvaluationDays()));
/*      */     
/* 2402 */     if (fd.getUserType().equals("R"))
/*      */     {
/* 2404 */       LicenseInfo.put("UnUsedMonitorsForLicensing", "" + (Constants.getNoofMonitors_withoutnatives() - DBUtil.getNumberOfUsers()));
/* 2405 */       LicenseInfo.put("CompanyName", w.getCompanyName());
/*      */     }
/*      */     
/* 2408 */     if (fd.getUserType().equals("R"))
/*      */     {
/* 2410 */       ArrayList<Hashtable<String, String>> addonList = new ArrayList();
/* 2411 */       Hashtable<String, String> addonDetails = new Hashtable();
/*      */       
/* 2413 */       addonDetails.put("Name", "SAPAddOn");
/* 2414 */       addonDetails.put("enabled", sapStatus);
/* 2415 */       sapMsg = sapMsg.equals("") ? "" : (String)addonDetails.put("Message", sapMsg);
/* 2416 */       addonList.add(addonDetails);
/*      */       
/* 2418 */       addonDetails = new Hashtable();
/* 2419 */       addonDetails.put("Name", "SAPAddOnCCMS");
/* 2420 */       addonDetails.put("enabled", sapCCMSStatus);
/* 2421 */       sapCCMSMsg = sapCCMSMsg.equals("") ? "" : (String)addonDetails.put("Message", sapCCMSMsg);
/* 2422 */       addonList.add(addonDetails);
/*      */       
/* 2424 */       addonDetails = new Hashtable();
/* 2425 */       addonDetails.put("Name", "AS400AddOn");
/* 2426 */       addonDetails.put("enabled", As400Status);
/* 2427 */       As400Msg = As400Msg.equals("") ? "" : (String)addonDetails.put("Message", As400Msg);
/* 2428 */       addonList.add(addonDetails);
/*      */       
/* 2430 */       addonDetails = new Hashtable();
/* 2431 */       addonDetails.put("Name", "AS400AddOnAdmin");
/* 2432 */       addonDetails.put("enabled", As400AdminStatus);
/* 2433 */       As400AdminMsg = As400AdminMsg.equals("") ? "" : (String)addonDetails.put("Message", As400AdminMsg);
/* 2434 */       addonList.add(addonDetails);
/*      */       
/* 2436 */       addonDetails = new Hashtable();
/* 2437 */       addonDetails.put("Name", "OpManagerAddOn");
/* 2438 */       addonDetails.put("enabled", OpmanStatus);
/* 2439 */       OpmanMSG = OpmanMSG.equals("") ? "" : (String)addonDetails.put("Message", OpmanMSG);
/* 2440 */       addonList.add(addonDetails);
/*      */       
/* 2442 */       addonDetails = new Hashtable();
/* 2443 */       addonDetails.put("Name", "J2EEWebTransactionsAddOn");
/* 2444 */       addonDetails.put("enabled", WebTransactionStatus);
/* 2445 */       WebTransactionMSG = WebTransactionMSG.equals("") ? "" : (String)addonDetails.put("Message", WebTransactionMSG);
/* 2446 */       addonList.add(addonDetails);
/*      */       
/* 2448 */       addonDetails = new Hashtable();
/* 2449 */       addonDetails.put("Name", "IBMWebsphereMQAddOn");
/* 2450 */       addonDetails.put("enabled", MqSeriesStatus);
/* 2451 */       MqSeriesMSG = MqSeriesMSG.equals("") ? "" : (String)addonDetails.put("Message", MqSeriesMSG);
/* 2452 */       addonList.add(addonDetails);
/*      */       
/* 2454 */       addonDetails = new Hashtable();
/* 2455 */       addonDetails.put("Name", "AnomalyAddOn");
/* 2456 */       addonDetails.put("enabled", AnomalyStatus);
/* 2457 */       AnomalyMSG = AnomalyMSG.equals("") ? "" : (String)addonDetails.put("Message", AnomalyMSG);
/* 2458 */       addonList.add(addonDetails);
/*      */       
/* 2460 */       addonDetails = new Hashtable();
/* 2461 */       addonDetails.put("Name", "RBMAddOn");
/* 2462 */       addonDetails.put("enabled", RBMStatus);
/* 2463 */       RBMMSG = RBMMSG.equals("") ? "" : (String)addonDetails.put("Message", RBMMSG);
/* 2464 */       addonList.add(addonDetails);
/*      */       
/* 2466 */       addonDetails = new Hashtable();
/* 2467 */       addonDetails.put("Name", "OpStorAddOn");
/* 2468 */       addonDetails.put("enabled", OpStorStatus);
/* 2469 */       OpStorMSG = OpStorMSG.equals("") ? "" : (String)addonDetails.put("Message", OpStorMSG);
/* 2470 */       addonList.add(addonDetails);
/*      */       
/* 2472 */       addonDetails = new Hashtable();
/* 2473 */       addonDetails.put("Name", "OracleEBSAddOn");
/* 2474 */       addonDetails.put("enabled", OracleEBSStatus);
/* 2475 */       OracleEBSMSG = OracleEBSMSG.equals("") ? "" : (String)addonDetails.put("Message", OracleEBSMSG);
/* 2476 */       addonList.add(addonDetails);
/*      */       
/* 2478 */       addonDetails = new Hashtable();
/* 2479 */       addonDetails.put("Name", "SiebelAddOn");
/* 2480 */       addonDetails.put("enabled", siebelStatus);
/* 2481 */       siebelMsg = OracleEBSMSG.equals("") ? "" : (String)addonDetails.put("Message", siebelMsg);
/* 2482 */       addonList.add(addonDetails);
/*      */       
/* 2484 */       addonDetails = new Hashtable();
/* 2485 */       addonDetails.put("Name", "MSOfficeSharePointAddOn");
/* 2486 */       addonDetails.put("enabled", SharePointStatus);
/* 2487 */       SharePointMSG = SharePointMSG.equals("") ? "" : (String)addonDetails.put("Message", SharePointMSG);
/* 2488 */       addonList.add(addonDetails);
/*      */       
/* 2490 */       LicenseInfo.put("AddOn", addonList);
/*      */     }
/* 2492 */     ((ArrayList)License).add(LicenseInfo);
/*      */     
/* 2494 */     HashMap<String, Object> results = new HashMap();
/* 2495 */     results.put("response-code", responseCode);
/* 2496 */     results.put("uri", uri);
/* 2497 */     results.put("result", License);
/* 2498 */     results.put("sortingParam", "Name");
/* 2499 */     results.put("nodeName", "LicenseInfo");
/* 2500 */     results.put("subNodeName", "AddOn");
/* 2501 */     outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     
/* 2503 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String ListServerXML(HttpServletRequest request, HttpServletResponse response, String query, String type, String qry) throws Exception
/*      */   {
/* 2508 */     String outputString = "";
/* 2509 */     String message = "";
/* 2510 */     String errorCode = "";
/* 2511 */     boolean isAdminRole = !CommonAPIUtil.isOperatorRole(request);
/* 2512 */     String owner = isAdminRole ? "admin" : CommonAPIUtil.getOwnerName(request);
/*      */     
/* 2514 */     AMLog.debug("REST API : inside ListServerXML method" + type);
/* 2515 */     AMLog.info("REST API : ListServer Qry:" + query);
/*      */     
/* 2517 */     String uri = request.getRequestURI();
/* 2518 */     boolean isJsonFormat = uri.contains("json");
/* 2519 */     if (isJsonFormat)
/*      */     {
/* 2521 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/* 2525 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 2527 */     String responseCode = "4000";
/* 2528 */     ResultSet rs = null;
/* 2529 */     ArrayList<Hashtable> serverList = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 2533 */       AMLog.debug("REST API : root node uri" + uri);
/* 2534 */       AMLog.debug("REST API : inside method ListServer API");
/* 2535 */       String Sname = "";
/* 2536 */       ArrayList<String> serverArray = new ArrayList();
/* 2537 */       ArrayList<String> serverNetArray = new ArrayList();
/* 2538 */       ArrayList<String> serverResIDArray = new ArrayList();
/* 2539 */       ArrayList<String> serverTypeArray = new ArrayList();
/* 2540 */       ArrayList<String> serverDispArray = new ArrayList();
/* 2541 */       ArrayList<String> serverIPArray = new ArrayList();
/* 2542 */       String serverName = "";
/* 2543 */       String serverNet = "";
/* 2544 */       String serverResID = "";
/* 2545 */       String serverType = "";
/* 2546 */       String serverDisp = "";
/* 2547 */       String ipAddrStr = "";
/*      */       
/* 2549 */       if (!EnterpriseUtil.isAdminServer())
/*      */       {
/* 2551 */         AMLog.debug("RESTAPI: ListServer : servers qry :" + qry);
/* 2552 */         rs = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/*      */         try
/*      */         {
/* 2556 */           while (rs.next())
/*      */           {
/* 2558 */             serverName = rs.getString("PARENTNODE");
/* 2559 */             serverNet = rs.getString("PARENTNET");
/* 2560 */             serverResID = rs.getString("RESOURCEID");
/* 2561 */             serverType = rs.getString("TYPE");
/* 2562 */             serverDisp = rs.getString("DISPLAYNAME");
/* 2563 */             serverArray.add(serverName);
/* 2564 */             serverNetArray.add(serverNet);
/* 2565 */             serverResIDArray.add(serverResID);
/* 2566 */             serverTypeArray.add(serverType);
/* 2567 */             serverDispArray.add(serverDisp);
/*      */             try
/*      */             {
/* 2570 */               ipAddrStr = "";
/* 2571 */               InetAddress addr = InetAddress.getByName(serverName);
/* 2572 */               byte[] ipAddr = addr.getAddress();
/*      */               
/*      */ 
/* 2575 */               for (int j = 0; j < ipAddr.length; j++)
/*      */               {
/* 2577 */                 if (j > 0)
/*      */                 {
/* 2579 */                   ipAddrStr = ipAddrStr + ".";
/*      */                 }
/* 2581 */                 ipAddrStr = ipAddrStr + (ipAddr[j] & 0xFF);
/*      */               }
/*      */             }
/*      */             catch (UnknownHostException e)
/*      */             {
/* 2586 */               ipAddrStr = "UnKnownHost";
/* 2587 */               e.printStackTrace();
/*      */             }
/* 2589 */             serverIPArray.add(ipAddrStr);
/*      */           }
/* 2591 */           AMLog.debug("RESTAPI: ListServer : servers list : " + serverIPArray);
/*      */         }
/*      */         catch (Exception ex1)
/*      */         {
/* 2595 */           ex1.printStackTrace();
/*      */         } finally {
/* 2597 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 2599 */         Hashtable serverDetails = new Hashtable();
/* 2600 */         for (int i = 0; i < serverArray.size(); i++)
/*      */         {
/* 2602 */           serverName = (String)serverArray.get(i);
/* 2603 */           serverNet = (String)serverNetArray.get(i);
/* 2604 */           serverResID = (String)serverResIDArray.get(i);
/* 2605 */           serverType = (String)serverTypeArray.get(i);
/* 2606 */           serverDisp = (String)serverDispArray.get(i);
/* 2607 */           ipAddrStr = (String)serverIPArray.get(i);
/*      */           
/* 2609 */           String qryToExecute = query + " and IpAddress.PARENTNODE='" + serverName + "'";
/* 2610 */           AMLog.info("REST API: qryToExecute: " + qryToExecute);
/* 2611 */           rs = AMConnectionPool.executeQueryStmt(qryToExecute);
/*      */           try
/*      */           {
/* 2614 */             Object serviceList = new ArrayList();
/* 2615 */             while (rs.next())
/*      */             {
/* 2617 */               Hashtable<String, String> serviceDetails = new Hashtable();
/* 2618 */               String resid = rs.getString("RESOURCEID");
/* 2619 */               serviceDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 2620 */               serviceDetails.put("TYPE", rs.getString("TYPE"));
/* 2621 */               serviceDetails.put("RESOURCEID", resid);
/* 2622 */               serviceDetails.put("RESOURCETYPE", FormatUtil.getString(rs.getString("RESOURCETYPE")));
/* 2623 */               serviceDetails.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 2624 */               serviceDetails.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 2625 */               serviceDetails.put("IMAGEPATH", rs.getString("IMAGEPATH"));
/* 2626 */               serviceDetails.put("ATTRIBUTEID", rs.getString("attributeid"));
/* 2627 */               serviceDetails.put("DetailsPageLink", "/showresource.do?resourceid=" + rs.getString("RESOURCEID") + "&method=showResourceForResourceID");
/* 2628 */               serviceDetails.put("RCALink", "/jsp/RCA.jsp?resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + rs.getString("attributeid"));
/*      */               
/* 2630 */               String healthid = AMAttributesCache.getHealthId(rs.getString("RESOURCETYPE"));
/* 2631 */               String availid = AMAttributesCache.getAvailabilityId(rs.getString("RESOURCETYPE"));
/* 2632 */               HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(rs.getString("RESOURCEID"), rs.getString("RESOURCETYPE"));
/*      */               
/* 2634 */               serviceDetails.put("HEALTHATTRIBUTEID", healthid);
/* 2635 */               serviceDetails.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 2636 */               serviceDetails.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/* 2637 */               serviceDetails.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/* 2638 */               serviceDetails.put("AVAILABILITYATTRIBUTEID", availid);
/* 2639 */               serviceDetails.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 2640 */               serviceDetails.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 2641 */               serviceDetails.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 2642 */               ((ArrayList)serviceList).add(serviceDetails);
/*      */             }
/*      */             
/*      */             try
/*      */             {
/* 2647 */               List<Map<String, String>> extraServicesInHost = ClientDBUtil.getAllMonitorsInHost(serverName, serverResID, owner, isAdminRole, false);
/* 2648 */               if ((extraServicesInHost != null) && (extraServicesInHost.size() > 0)) {
/* 2649 */                 for (Map<String, String> service : extraServicesInHost) {
/* 2650 */                   ((ArrayList)serviceList).add(getServiceDetails((String)service.get("RESOURCEID")));
/*      */                 }
/*      */               }
/* 2653 */               AMLog.debug("Monitors for server:" + serverName + " :: " + extraServicesInHost);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2657 */               e.printStackTrace();
/*      */             }
/*      */             
/* 2660 */             if (!Sname.equals(serverName))
/*      */             {
/* 2662 */               serverDetails = new Hashtable();
/* 2663 */               Sname = serverName;
/* 2664 */               serverType = serverType.equals("Node") ? "Unknown" : serverType;
/*      */               
/* 2666 */               serverDetails.put("Name", serverName);
/* 2667 */               serverDetails.put("PARENTIP", serverNet);
/* 2668 */               serverDetails.put("RESOURCEID", serverResID);
/* 2669 */               serverDetails.put("TYPE", serverType);
/* 2670 */               serverDetails.put("DISPLAYNAME", serverDisp);
/* 2671 */               serverDetails.put("IPADDRESS", ipAddrStr);
/* 2672 */               String associatedgroups = getImmediateParentGroupNames(serverResID);
/* 2673 */               serverDetails.put("ASSOCIATEDGROUPS", associatedgroups);
/*      */               
/* 2675 */               if (((ArrayList)serviceList).size() > 0)
/*      */               {
/* 2677 */                 serverDetails.put("Service", serviceList);
/*      */               }
/* 2679 */               serverList.add(serverDetails);
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 2684 */             ex.printStackTrace();
/* 2685 */             AMLog.debug("REST API : Server error");
/* 2686 */             message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 2687 */             errorCode = "4128";
/* 2688 */             outputString = generateXML(request, response, message, errorCode);
/*      */           }
/*      */           finally
/*      */           {
/* 2692 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */         }
/*      */       }
/* 2696 */       else if (EnterpriseUtil.isAdminServer())
/*      */       {
/* 2698 */         String MASName = "";
/* 2699 */         String MASDisName = "";
/* 2700 */         String MASPort = "";
/* 2701 */         String MASsslPort = "";
/* 2702 */         int serverOwner = -1;
/* 2703 */         String serverOwr = "";
/* 2704 */         rs = AMConnectionPool.executeQueryStmt(qry);
/*      */         try
/*      */         {
/* 2707 */           while (rs.next())
/*      */           {
/* 2709 */             serverName = rs.getString("PARENTNODE");
/* 2710 */             serverNet = rs.getString("PARENTNET");
/* 2711 */             serverResID = rs.getString("RESOURCEID");
/* 2712 */             serverType = rs.getString("TYPE");
/* 2713 */             serverDisp = rs.getString("DISPLAYNAME");
/* 2714 */             serverArray.add(serverName);
/* 2715 */             serverNetArray.add(serverNet);
/* 2716 */             serverResIDArray.add(serverResID);
/* 2717 */             serverTypeArray.add(serverType);
/* 2718 */             serverDispArray.add(serverDisp);
/*      */           }
/*      */           
/* 2721 */           for (int i = 0; i < serverArray.size(); i++)
/*      */           {
/* 2723 */             Hashtable serverDetails = new Hashtable();
/* 2724 */             serverName = (String)serverArray.get(i);
/* 2725 */             serverNet = (String)serverNetArray.get(i);
/* 2726 */             serverResID = (String)serverResIDArray.get(i);
/* 2727 */             serverType = (String)serverTypeArray.get(i);
/* 2728 */             serverDisp = (String)serverDispArray.get(i);
/* 2729 */             serverOwner = Integer.parseInt(serverResID) / 10000000;
/* 2730 */             serverOwr = Integer.toString(serverOwner);
/* 2731 */             serverType = serverType.equals("Node") ? "Unknown" : serverType;
/*      */             
/* 2733 */             if (serverOwr.equals("0"))
/*      */             {
/* 2735 */               serverOwr = "NULL";
/* 2736 */               MASName = "-";
/* 2737 */               MASDisName = "-";
/* 2738 */               MASPort = "-";
/* 2739 */               MASsslPort = "-";
/*      */             }
/*      */             else
/*      */             {
/* 2743 */               rs = AMConnectionPool.executeQueryStmt("select AM_MAS_SERVER.HOST, AM_MAS_SERVER.PORT, AM_MAS_SERVER.ALLOTED_GLOBAL_RANGE, AM_MAS_SERVER.DISPLAYNAME, AM_MAS_SERVER.SSLPORT from AM_MAS_SERVER where AM_MAS_SERVER.ALLOTED_GLOBAL_RANGE ='" + serverOwner * 10000000 + "'");
/* 2744 */               while (rs.next())
/*      */               {
/* 2746 */                 MASName = rs.getString("HOST");
/* 2747 */                 MASDisName = rs.getString("DISPLAYNAME");
/* 2748 */                 MASPort = rs.getString("PORT");
/* 2749 */                 MASsslPort = rs.getString("SSLPORT");
/*      */               }
/*      */             }
/*      */             
/* 2753 */             InetAddress addr = InetAddress.getByName(serverName);
/* 2754 */             byte[] ipAddr = addr.getAddress();
/*      */             
/*      */ 
/* 2757 */             for (int j = 0; j < ipAddr.length; j++)
/*      */             {
/* 2759 */               if (j > 0)
/*      */               {
/* 2761 */                 ipAddrStr = ipAddrStr + ".";
/*      */               }
/* 2763 */               ipAddrStr = ipAddrStr + (ipAddr[j] & 0xFF);
/*      */             }
/*      */             
/* 2766 */             serverDetails.put("Name", serverName);
/* 2767 */             serverDetails.put("PARENTIP", serverNet);
/* 2768 */             serverDetails.put("RESOURCEID", serverResID);
/* 2769 */             serverDetails.put("TYPE", serverType);
/* 2770 */             serverDetails.put("DISPLAYNAME", serverDisp);
/* 2771 */             serverDetails.put("IPADDRESS", ipAddrStr);
/* 2772 */             serverDetails.put("ManagedServerHOST", MASName);
/* 2773 */             serverDetails.put("ManagedServerDISPLAYNAME", MASDisName);
/* 2774 */             serverDetails.put("ManagedServerPORT", MASPort);
/* 2775 */             serverDetails.put("ManagedServerSSLPORT", MASsslPort);
/* 2776 */             serverDetails.put("ManagedServerID", "" + serverOwner);
/*      */             
/*      */ 
/* 2779 */             query = "select InetService.TARGETNAME as resname , InetService.NAME as DISPLAYNAME, AM_ManagedObject.TYPE,AM_ManagedObject.RESOURCEID, AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DESCRIPTION, AM_ATTRIBUTES.ATTRIBUTEID, InetService.OWNERNAME from IpAddress, AM_ATTRIBUTES,InetService,AM_ManagedObject where AM_ManagedObject.RESOURCENAME=InetService.NAME and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " and AM_ManagedObject.Type = AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and InetService.TARGETNAME='" + serverName + "'  and InetService.OWNERNAME='" + serverOwr + "' group by AM_ManagedObject.RESOURCEID,InetService.TARGETNAME,InetService.NAME,InetService.OWNERNAME,AM_ATTRIBUTES.ATTRIBUTEID,InetService.NAME,AM_ManagedObject.TYPE,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DESCRIPTION";
/* 2780 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 2781 */             ArrayList<Hashtable<String, String>> serviceList = new ArrayList();
/* 2782 */             while (rs.next())
/*      */             {
/* 2784 */               Hashtable<String, String> serviceDetails = new Hashtable();
/* 2785 */               serviceDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 2786 */               serviceDetails.put("TYPE", rs.getString("TYPE"));
/* 2787 */               serviceDetails.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 2788 */               serviceDetails.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 2789 */               serviceDetails.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 2790 */               serviceDetails.put("ATTRIBUTEID", rs.getString("attributeid"));
/* 2791 */               serviceDetails.put("DetailsPageLink", "/showresource.do?resourceid=" + rs.getString("RESOURCEID") + "&method=showResourceForResourceID");
/* 2792 */               serviceDetails.put("RCALink", "/jsp/RCA.jsp?resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + rs.getString("attributeid"));
/*      */               
/* 2794 */               String healthid = AMAttributesCache.getHealthId(rs.getString("TYPE"));
/* 2795 */               String availid = AMAttributesCache.getAvailabilityId(rs.getString("TYPE"));
/* 2796 */               HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(rs.getString("RESOURCEID"), rs.getString("TYPE"));
/*      */               
/* 2798 */               serviceDetails.put("HEALTHATTRIBUTEID", healthid);
/* 2799 */               serviceDetails.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 2800 */               serviceDetails.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/* 2801 */               serviceDetails.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/* 2802 */               serviceDetails.put("AVAILABILITYATTRIBUTEID", availid);
/* 2803 */               serviceDetails.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 2804 */               serviceDetails.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 2805 */               serviceDetails.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 2806 */               serviceList.add(serviceDetails);
/*      */             }
/*      */             
/*      */             try
/*      */             {
/* 2811 */               List<Map<String, String>> extraServicesInHost = ClientDBUtil.getAllMonitorsInHost(serverName, serverResID, owner, isAdminRole, false);
/* 2812 */               if ((extraServicesInHost != null) && (extraServicesInHost.size() > 0)) {
/* 2813 */                 for (Map<String, String> service : extraServicesInHost) {
/* 2814 */                   serviceList.add(getServiceDetails((String)service.get("RESOURCEID")));
/*      */                 }
/*      */               }
/* 2817 */               AMLog.debug("Monitors for server:" + serverName + " :: " + extraServicesInHost);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2821 */               e.printStackTrace();
/*      */             }
/* 2823 */             if (serviceList.size() > 0)
/*      */             {
/* 2825 */               serverDetails.put("Service", serviceList);
/*      */             }
/* 2827 */             serverList.add(serverDetails);
/*      */           }
/*      */         }
/*      */         catch (Exception ex1)
/*      */         {
/* 2832 */           ex1.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 2836 */       HashMap results = new HashMap();
/* 2837 */       results.put("response-code", responseCode);
/* 2838 */       results.put("uri", uri);
/* 2839 */       results.put("result", serverList);
/* 2840 */       results.put("sortingParam", "DISPLAYNAME");
/* 2841 */       results.put("nodeName", "Server");
/* 2842 */       results.put("subNodeName", "Service");
/* 2843 */       outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2847 */       e.printStackTrace();
/*      */     }
/* 2849 */     return outputString;
/*      */   }
/*      */   
/*      */   private static Hashtable<String, String> getServiceDetails(String resourceId) {
/* 2853 */     Hashtable<String, String> serviceDetails = new Hashtable();
/* 2854 */     ResultSet rs = null;
/* 2855 */     String qryForServiceDetails = "select distinct AM_ManagedObject.DISPLAYNAME DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.RESOURCETYPE as RESOURCETYPE, AM_ManagedResourceType.SHORTNAME as TYPE, AM_ManagedObject.RESOURCEID RESOURCEID,AM_ManagedObject.RESOURCENAME RESOURCENAME,AM_ManagedObject.DESCRIPTION DESCRIPTION,AM_ATTRIBUTES.ATTRIBUTEID attributeid from AM_ManagedObject,AM_ATTRIBUTES,IpAddress,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.Type = AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.RESOURCEID =" + resourceId;
/*      */     try
/*      */     {
/* 2858 */       rs = AMConnectionPool.executeQueryStmt(qryForServiceDetails);
/* 2859 */       if (rs.next())
/*      */       {
/* 2861 */         serviceDetails.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 2862 */         serviceDetails.put("TYPE", rs.getString("TYPE"));
/* 2863 */         serviceDetails.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 2864 */         serviceDetails.put("RESOURCETYPE", FormatUtil.getString(rs.getString("RESOURCETYPE")));
/* 2865 */         serviceDetails.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 2866 */         serviceDetails.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 2867 */         serviceDetails.put("IMAGEPATH", rs.getString("IMAGEPATH"));
/* 2868 */         serviceDetails.put("ATTRIBUTEID", rs.getString("attributeid"));
/* 2869 */         serviceDetails.put("DetailsPageLink", "/showresource.do?resourceid=" + rs.getString("RESOURCEID") + "&method=showResourceForResourceID");
/* 2870 */         serviceDetails.put("RCALink", "/jsp/RCA.jsp?resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + rs.getString("attributeid"));
/*      */         
/* 2872 */         String healthid = AMAttributesCache.getHealthId(rs.getString("RESOURCETYPE"));
/* 2873 */         String availid = AMAttributesCache.getAvailabilityId(rs.getString("RESOURCETYPE"));
/* 2874 */         HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(rs.getString("RESOURCEID"), rs.getString("RESOURCETYPE"));
/*      */         
/* 2876 */         serviceDetails.put("HEALTHATTRIBUTEID", healthid);
/* 2877 */         serviceDetails.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 2878 */         serviceDetails.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/* 2879 */         serviceDetails.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/* 2880 */         serviceDetails.put("AVAILABILITYATTRIBUTEID", availid);
/* 2881 */         serviceDetails.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 2882 */         serviceDetails.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 2883 */         serviceDetails.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2887 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2890 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2892 */     return serviceDetails;
/*      */   }
/*      */   
/*      */   public static String ListAlarmsXML(HttpServletRequest request, HttpServletResponse response, String query) throws Exception
/*      */   {
/* 2897 */     String outputString = "";
/* 2898 */     String message = "";
/* 2899 */     String errorCode = "";
/* 2900 */     String topN = request.getParameter("topN");
/* 2901 */     AMLog.debug("REST API : Query-->" + query);
/* 2902 */     AMLog.debug("REST API : inside ListAlarmsXML method");
/* 2903 */     String uri = request.getRequestURI();
/* 2904 */     boolean isJsonFormat = uri.contains("json");
/* 2905 */     if (isJsonFormat)
/*      */     {
/* 2907 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/* 2911 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 2913 */     String responseCode = "4000";
/* 2914 */     ResultSet rs = null;
/*      */     
/* 2916 */     ArrayList<Hashtable> result = new ArrayList();
/* 2917 */     HashMap extDeviceMap = null;
/* 2918 */     if (Constants.isExtDeviceConfigured())
/*      */     {
/* 2920 */       if (Constants.isIt360)
/*      */       {
/* 2922 */         extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", true);
/*      */       }
/*      */       else
/*      */       {
/* 2926 */         extDeviceMap = IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */       }
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 2932 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2933 */       while (rs.next())
/*      */       {
/* 2935 */         Hashtable alarm = new Hashtable();
/* 2936 */         String alarmStatus = "";
/* 2937 */         String alarmSeverity = "";
/* 2938 */         String strdisp = "-";
/* 2939 */         String strtypedisp = "-";
/* 2940 */         String resourceId = "";
/*      */         
/* 2942 */         if (rs.getString("DISPLAYNAME") != null)
/*      */         {
/* 2944 */           strdisp = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/*      */         }
/* 2946 */         if (rs.getString("TYPEDISPLAYNAME") != null)
/*      */         {
/* 2948 */           strtypedisp = rs.getString("TYPEDISPLAYNAME");
/*      */         }
/*      */         
/* 2951 */         alarmSeverity = rs.getString("SEVERITY");
/* 2952 */         if (alarmSeverity.equals("5"))
/*      */         {
/* 2954 */           alarmStatus = "clear";
/*      */         }
/* 2956 */         else if (alarmSeverity.equals("4"))
/*      */         {
/* 2958 */           alarmStatus = "warning";
/*      */         }
/* 2960 */         else if (alarmSeverity.equals("1"))
/*      */         {
/* 2962 */           alarmStatus = "critical";
/*      */         }
/* 2964 */         String resourceType = rs.getString("TYPE");
/* 2965 */         resourceId = rs.getString("SOURCE");
/*      */         
/* 2967 */         String monitorDetailsPage = "/showresource.do?resourceid=" + resourceId + "&method=showResourceForResourceID&PRINTER_FRIENDLY=true";
/* 2968 */         if (resourceType.equals("HAI"))
/*      */         {
/* 2970 */           monitorDetailsPage = "/showapplication.do?method=showApplication&haid=" + resourceId + "&PRINTER_FRIENDLY=true";
/*      */         }
/* 2972 */         else if ((extDeviceMap != null) && (extDeviceMap.get(resourceId) != null))
/*      */         {
/* 2974 */           monitorDetailsPage = (String)extDeviceMap.get(resourceId);
/*      */         }
/*      */         
/* 2977 */         String healthid = AMAttributesCache.getHealthId(resourceType);
/* 2978 */         String availid = AMAttributesCache.getAvailabilityId(resourceType);
/* 2979 */         HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(resourceId, resourceType);
/* 2980 */         String availStatus = "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY");
/* 2981 */         String shortMessage = getShortMsgForAlert(alarmSeverity, availStatus);
/*      */         
/* 2983 */         String imgPath = rs.getString("IMAGEPATH") != null ? rs.getString("IMAGEPATH") : "";
/* 2984 */         alarm.put("RESOURCEID", resourceId);
/* 2985 */         alarm.put("DISPLAYNAME", strdisp);
/* 2986 */         alarm.put("TYPE", resourceType);
/* 2987 */         alarm.put("TYPEDISPLAYNAME", strtypedisp);
/* 2988 */         alarm.put("DetailsPageURL", monitorDetailsPage);
/* 2989 */         alarm.put("HEALTHSEVERITY", alarmSeverity);
/* 2990 */         alarm.put("AVAILABILITYSEVERITY", availStatus);
/* 2991 */         alarm.put("STATUS", alarmStatus);
/* 2992 */         alarm.put("MESSAGE", rs.getString("MMESSAGE"));
/* 2993 */         alarm.put("SHORTMESSAGE", shortMessage);
/* 2994 */         alarm.put("IMAGEPATH", imgPath);
/* 2995 */         alarm.put("ATTRIBUTEID", rs.getString("CATEGORY"));
/*      */         
/* 2997 */         String alarmModtimeS = rs.getString("MODTIME");
/* 2998 */         Date date = new Date(Long.parseLong(alarmModtimeS));
/* 2999 */         SimpleDateFormat sdf = new SimpleDateFormat("MMM d hh:mm a", Locale.getDefault());
/* 3000 */         sdf.setTimeZone(TimeZone.getDefault());
/* 3001 */         String formattedDate = sdf.format(date);
/* 3002 */         alarm.put("MODTIME", alarmModtimeS);
/* 3003 */         alarm.put("FORMATTEDDATE", formattedDate);
/* 3004 */         String tech = rs.getString("WHO");
/* 3005 */         alarm.put("TECHNICIAN", !tech.equals("NULL") ? tech : "None");
/* 3006 */         alarm.put("ANNOTATION", rs.getString("ANNOTATION"));
/*      */         
/* 3008 */         result.add(alarm);
/*      */       }
/* 3010 */       if (result.size() == 0)
/*      */       {
/* 3012 */         Hashtable msg = new Hashtable();
/* 3013 */         msg.put("Message", FormatUtil.getString("am.webclient.apikey.noalarms.message"));
/* 3014 */         result.add(msg);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3020 */       ex.printStackTrace();
/* 3021 */       AMLog.debug("REST API : Server error");
/* 3022 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 3023 */       errorCode = "4128";
/* 3024 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*      */     finally
/*      */     {
/* 3028 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3031 */     HashMap dataMap = new HashMap();
/* 3032 */     dataMap.put("response-code", responseCode);
/* 3033 */     dataMap.put("uri", uri);
/* 3034 */     dataMap.put("result", result);
/* 3035 */     dataMap.put("sortingParam", "MODTIME,DISPLAYNAME");
/* 3036 */     dataMap.put("sortingOrder", "desc");
/* 3037 */     dataMap.put("parentNode", "Alarms");
/* 3038 */     dataMap.put("nodeName", "Alarm");
/*      */     
/* 3040 */     outputString = getOutputAsString(dataMap, isJsonFormat);
/*      */     
/* 3042 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String ManageUnmanageXML(HttpServletRequest request, HttpServletResponse response, String strmanaged, String resid) throws Exception
/*      */   {
/* 3047 */     String outputString = "";
/* 3048 */     String message = "";
/* 3049 */     String errorCode = "";
/* 3050 */     AMLog.debug("REST API : inside ManageUnmanageXML method");
/* 3051 */     String uri = request.getRequestURI();
/* 3052 */     int count = 0;
/* 3053 */     String mgID = request.getParameter("haid") != null ? request.getParameter("haid") : null;
/* 3054 */     Vector<String> mgchildIds = new Vector();
/* 3055 */     if ((resid == null) && (mgID != null))
/*      */     {
/* 3057 */       resid = mgID;
/* 3058 */       ParentChildRelationalUtil.getAllChildMapper(mgchildIds, mgID, true);
/* 3059 */       Enumeration e = mgchildIds.elements();
/* 3060 */       while (e.hasMoreElements()) {
/* 3061 */         resid = resid + "," + e.nextElement();
/*      */       }
/*      */     }
/* 3064 */     else if ((resid != null) && (mgID == null))
/*      */     {
/* 3066 */       resid = resid;
/*      */     }
/* 3068 */     else if ((resid != null) && (mgID != null))
/*      */     {
/* 3070 */       resid = resid + "," + mgID;
/*      */     }
/*      */     else
/*      */     {
/* 3074 */       return generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/*      */     }
/* 3076 */     String[] resourceid = resid.split(",");
/* 3077 */     String unmanageResetResid = resid;
/* 3078 */     String strMessage = "";
/* 3079 */     boolean BlnManaged = false;
/* 3080 */     String checkquery = "select RESOURCEID from AM_ManagedObject where (TYPE in " + Constants.resourceTypes + " or TYPE='HAI' or TYPE IN ('Process','Service')) and RESOURCEID='" + resid + "'";
/* 3081 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3084 */       if (1 < resourceid.length)
/*      */       {
/* 3086 */         checkquery = "select count(RESOURCEID) from AM_ManagedObject where (TYPE in " + Constants.resourceTypes + " or TYPE='HAI' or TYPE IN ('Process','Service')) and RESOURCEID in (" + resid + ")";
/*      */         try
/*      */         {
/* 3089 */           rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3090 */           while (rs.next())
/*      */           {
/* 3092 */             count = rs.getInt(1);
/*      */           }
/* 3094 */           if (resourceid.length == count)
/*      */           {
/* 3096 */             BlnManaged = true;
/*      */           }
/*      */           else
/*      */           {
/* 3100 */             BlnManaged = false;
/* 3101 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/* 3102 */             return outputString;
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 3107 */           ((Exception)ex).printStackTrace();
/* 3108 */           AMLog.debug("REST API : Server error");
/* 3109 */           message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 3110 */           errorCode = "4128";
/* 3111 */           outputString = generateXML(request, response, message, errorCode);
/*      */         }
/* 3113 */         resid = null;
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 3119 */           rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3120 */           if (rs.next())
/*      */           {
/* 3122 */             BlnManaged = true;
/*      */           }
/*      */           else
/*      */           {
/* 3126 */             BlnManaged = false;
/* 3127 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/* 3128 */             return outputString;
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 3133 */           ex.printStackTrace();
/* 3134 */           AMLog.debug("REST API : Server error");
/* 3135 */           message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 3136 */           errorCode = "4128";
/* 3137 */           outputString = generateXML(request, response, message, errorCode);
/* 3138 */           return outputString;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 3144 */       exp.printStackTrace();
/* 3145 */       AMLog.debug("REST API : Server error");
/* 3146 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 3147 */       errorCode = "4128";
/* 3148 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*      */     finally
/*      */     {
/* 3152 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     try
/*      */     {
/* 3156 */       if ((strmanaged.equals("ManageMonitor")) && (BlnManaged))
/*      */       {
/* 3158 */         DataCollectionControllerUtil.updateManageMonitors(resourceid, resid);
/* 3159 */         strMessage = FormatUtil.getString("am.webclient.apikey.managesuccess.message");
/*      */       }
/* 3161 */       else if ((strmanaged.equals("UnmanageMonitor")) && (BlnManaged))
/*      */       {
/* 3163 */         DataCollectionControllerUtil.updateUnManageMonitors(resourceid, resid);
/* 3164 */         strMessage = FormatUtil.getString("am.webclient.apikey.unmanagesuccess.message");
/*      */       }
/* 3166 */       else if ((strmanaged.equals("UnmanageAndResetMonitor")) && (BlnManaged))
/*      */       {
/* 3168 */         strMessage = unmanageAndRest(request, response, unmanageResetResid);
/* 3169 */         if (mgID != null) {
/* 3170 */           FaultUtil.deleteAlertsForResource(mgID);
/* 3171 */           Object subHaid = new Vector();
/* 3172 */           ParentChildRelationalUtil.getAllSubMG((Vector)subHaid, mgID);
/* 3173 */           Enumeration e1 = ((Vector)subHaid).elements();
/* 3174 */           while (e1.hasMoreElements()) {
/* 3175 */             FaultUtil.deleteAlertsForResource("" + e1.nextElement());
/*      */           }
/*      */         }
/*      */         
/* 3179 */         strMessage = FormatUtil.getString("am.webclient.apikey.unmanagereset.succ.message");
/*      */       }
/*      */       else
/*      */       {
/* 3183 */         return generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/*      */       }
/*      */       
/* 3186 */       outputString = generateXML(request, response, strMessage, "4000");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3190 */       ex.printStackTrace();
/* 3191 */       AMLog.debug("REST API : Server error");
/* 3192 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 3193 */       errorCode = "4128";
/* 3194 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/* 3197 */     return outputString;
/*      */   }
/*      */   
/*      */   public static boolean validateMonitorDetails(HttpServletRequest request, HttpServletResponse response, String monitorName, String referenceColumn, String method) throws Exception
/*      */   {
/* 3202 */     ResultSet rs = null;
/* 3203 */     String checkquery = "";
/*      */     try
/*      */     {
/* 3206 */       if ((method.equals("ListMonitor")) || (method.equals("ListAlarms")))
/*      */       {
/* 3208 */         checkquery = "select " + referenceColumn + " from AM_ManagedObject where " + referenceColumn + "='" + monitorName + "'and (AM_ManagedObject.TYPE in " + Constants.resourceTypes + ")";
/* 3209 */         if (referenceColumn.equalsIgnoreCase("RESOURCEID"))
/*      */         {
/* 3211 */           String mgCondition = "";
/* 3212 */           if (method.equals("ListAlarms"))
/*      */           {
/* 3214 */             mgCondition = "AM_ManagedObject.TYPE='HAI' OR ";
/*      */           }
/* 3216 */           checkquery = "select " + referenceColumn + " from AM_ManagedObject where " + referenceColumn + " IN (" + monitorName + ") and (" + mgCondition + "AM_ManagedObject.TYPE in " + Constants.resourceTypes + ")";
/*      */         }
/* 3218 */         if (referenceColumn.equalsIgnoreCase("TYPE"))
/*      */         {
/* 3220 */           String typeCondition = "and " + DBQueryUtil.castasCitext("AM_ManagedObject.TYPE") + " = ('" + monitorName + "')";
/* 3221 */           if (monitorName.contains(","))
/*      */           {
/* 3223 */             monitorName = monitorName.replaceAll(",", "','");
/* 3224 */             typeCondition = "and " + DBQueryUtil.castasCitext("AM_ManagedObject.TYPE") + " in ('" + monitorName + "')";
/*      */           }
/* 3226 */           else if (monitorName.equals("NWD"))
/*      */           {
/* 3228 */             typeCondition = " and (AM_ManagedObject.TYPE like 'OpManager-%')";
/*      */           }
/* 3230 */           else if (monitorName.equals("SAN"))
/*      */           {
/* 3232 */             typeCondition = " and (AM_ManagedObject.TYPE like 'OpStor-%')";
/*      */           }
/* 3234 */           else if (monitorName.equals("EMO"))
/*      */           {
/* 3236 */             typeCondition = "and (AM_ManagedObject.TYPE like '%Site24x7%')";
/*      */           }
/* 3238 */           checkquery = "select AM_ManagedObject.TYPE from AM_ManagedObject,AM_ManagedResourceType where (AM_ManagedResourceType.SUBGROUP=AM_ManagedObject.TYPE OR AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE) " + typeCondition + " and AM_ManagedObject.TYPE in " + Constants.resourceTypes;
/*      */         }
/*      */       }
/* 3241 */       else if (method.equals("ListServer"))
/*      */       {
/* 3243 */         if (referenceColumn.equals("ipaddress"))
/*      */         {
/* 3245 */           checkquery = "select VALUESTRING from DBINTERFACES where VALUESTRING='" + monitorName + "'";
/*      */         }
/*      */         else
/*      */         {
/* 3249 */           checkquery = "select " + referenceColumn + " from IpAddress where " + referenceColumn + "='" + monitorName.toLowerCase() + "'";
/*      */           try
/*      */           {
/* 3252 */             InetAddress inetAdd = InetAddress.getByName(monitorName);
/* 3253 */             if (!inetAdd.equals(monitorName))
/*      */             {
/* 3255 */               monitorName = inetAdd.getCanonicalHostName();
/*      */               
/* 3257 */               monitorName = monitorName != null ? monitorName.toLowerCase() : monitorName;
/* 3258 */               checkquery = checkquery + " or " + referenceColumn + "='" + monitorName + "'";
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 3264 */             AMLog.info("RESTAPI: ListServer: Exception while getting Fully qualified hostname:" + monitorName + "; but no need to worry abt that");
/*      */           }
/*      */         }
/*      */       }
/* 3268 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3269 */       if (rs.next())
/*      */       {
/* 3271 */         return 1;
/*      */       }
/* 3273 */       AMLog.debug("REST API : validateMonitorDetails check failed API method,monitorName,referenceColumn,query-->" + method + "," + monitorName + "," + referenceColumn + "," + checkquery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3277 */       ex.printStackTrace();
/* 3278 */       AMLog.debug("REST API : Server error in validateMonitorDetails");
/*      */     }
/*      */     finally
/*      */     {
/* 3282 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 3284 */     return false;
/*      */   }
/*      */   
/*      */   private static String getShortMsgForAlert(String alarmSeverity, String availStatus)
/*      */   {
/* 3289 */     String shrtMsg = null;
/* 3290 */     if (alarmSeverity.equals("1"))
/*      */     {
/* 3292 */       shrtMsg = FormatUtil.getString("am.fault.rca.healthiscritical.text");
/* 3293 */       if (availStatus.equals("1"))
/*      */       {
/* 3295 */         shrtMsg = FormatUtil.getString("am.fault.rca.resourceisdown.text");
/*      */       }
/*      */     }
/* 3298 */     else if (alarmSeverity.equals("4"))
/*      */     {
/* 3300 */       shrtMsg = FormatUtil.getString("am.fault.rca.healthiswarning.text");
/*      */     }
/* 3302 */     else if (alarmSeverity.equals("5"))
/*      */     {
/* 3304 */       shrtMsg = FormatUtil.getString("am.fault.rca.healthisclear.text");
/*      */     }
/*      */     else
/*      */     {
/* 3308 */       shrtMsg = FormatUtil.getString("am.mypage.health.unknown.text");
/*      */     }
/* 3310 */     return shrtMsg;
/*      */   }
/*      */   
/*      */   private static String getMonitorTypesList(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 3315 */     AMLog.info("Inside getMonitorTypesList..method");
/*      */     
/* 3317 */     String responseCode = null;
/* 3318 */     String outputString = "";
/* 3319 */     String category = request.getParameter("type");
/*      */     
/*      */     try
/*      */     {
/* 3323 */       HashMap dataMap = new HashMap();
/* 3324 */       ArrayList<Hashtable<String, String>> result = new ArrayList();
/* 3325 */       Hashtable<String, String> messageTable = new Hashtable();
/* 3326 */       ArrayList<String> associatedTypes = new ArrayList();
/*      */       
/* 3328 */       if (category != null)
/*      */       {
/*      */ 
/*      */ 
/* 3332 */         if ((!category.equalsIgnoreCase("all")) && (!category.equalsIgnoreCase("network")) && (!category.equalsIgnoreCase("server")) && (!category.equalsIgnoreCase("application")))
/*      */         {
/* 3334 */           String[] types = category.split(",");
/* 3335 */           for (int i = 0; i < types.length; i++)
/*      */           {
/* 3337 */             if (!validateMonitorDetails(request, response, types[i], "TYPE", "ListMonitor"))
/*      */             {
/* 3339 */               AMLog.debug("REST API : The specified type in request URI is wrong.");
/* 3340 */               String message = FormatUtil.getString("am.webclient.api.wrongtyp.msg");
/* 3341 */               return generateXML(request, response, message, "4005");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 3347 */         associatedTypes = getAssocaitedTypes(category);
/* 3348 */         MyPageAction mypageAction = new MyPageAction();
/* 3349 */         String role = CommonAPIUtil.isOperatorRole(request) ? "operator" : "user";
/* 3350 */         String owner = CommonAPIUtil.getOwnerName(request);
/* 3351 */         HashMap monTypesMap = new HashMap();
/* 3352 */         monTypesMap = mypageAction.getAllAddedMonitorTypes(owner, role, associatedTypes, true, category, request);
/*      */         
/* 3354 */         request.setAttribute("totalMonitorsCount", monTypesMap.get("totalMonCnt"));
/* 3355 */         monTypesMap.remove("totalMonCnt");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3360 */         validateAndRemoveMonTypes(monTypesMap);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 3367 */           responseCode = "4000";
/* 3368 */           result.addAll(monTypesMap.values());
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3372 */           AMLog.debug("REST API : Error in generating JSON Feed!");
/* 3373 */           responseCode = "40xx";
/* 3374 */           messageTable.put("message", FormatUtil.getString("Error in generating JSON Feed!"));
/* 3375 */           result.add(messageTable);
/* 3376 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3381 */         AMLog.debug("REST API : The specified request URI is incorrect:the parameter specified is wrong");
/* 3382 */         responseCode = "4032";
/* 3383 */         messageTable.put("message", FormatUtil.getString("am.webclient.apikey.wrongmonparam.message"));
/* 3384 */         result.add(messageTable);
/*      */       }
/* 3386 */       dataMap.put("response-code", responseCode);
/* 3387 */       dataMap.put("uri", "/AppManager/json/ListMonitorTypes");
/* 3388 */       dataMap.put("result", result);
/* 3389 */       dataMap.put("sortingParam", "HEALTHSEVERITY,DISPLAYNAME");
/* 3390 */       dataMap.put("nodeName", "MonitorType");
/*      */       
/* 3392 */       outputString = getOutputAsString(dataMap, isJsonFormat);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3396 */       e.printStackTrace();
/*      */     }
/*      */     
/* 3399 */     return outputString;
/*      */   }
/*      */   
/*      */   private static void validateAndRemoveMonTypes(HashMap monTypesMap)
/*      */   {
/* 3404 */     HashMap monitorTypesMap = new HashMap();
/*      */     try
/*      */     {
/* 3407 */       Iterator<String> monTypeIter = monTypesMap.keySet().iterator();
/* 3408 */       while (monTypeIter.hasNext())
/*      */       {
/* 3410 */         String monType = (String)monTypeIter.next();
/*      */         
/* 3412 */         Hashtable<String, String> monTypeProps = (Hashtable)monTypesMap.get(monType);
/* 3413 */         String subgroup = (String)monTypeProps.get("subgroup");
/* 3414 */         String monitorType = (String)monTypeProps.get("displayname");
/* 3415 */         if ((subgroup != null) && (!monTypeProps.contains("EC2oscount")) && (!monTypeProps.contains("VMoscount")))
/*      */         {
/*      */ 
/*      */ 
/* 3419 */           if ("Network Devices".equals(monitorType))
/*      */           {
/* 3421 */             monTypeProps.put("subgroup", "NWD");
/*      */           }
/* 3423 */           else if ("Storage Devices".equals(monitorType))
/*      */           {
/* 3425 */             monTypeProps.put("subgroup", "SAN");
/*      */           }
/* 3427 */           else if ("Site24x7 Monitors".equals(monitorType))
/*      */           {
/* 3429 */             monTypeProps.put("subgroup", "EMO");
/*      */           }
/* 3431 */           String availSeverity = (String)monTypeProps.remove("severity");
/* 3432 */           String healthmsg = (String)monTypeProps.remove("alertmsg");
/* 3433 */           String img = (String)monTypeProps.remove("img");
/* 3434 */           if (availSeverity != null)
/*      */           {
/* 3436 */             monTypeProps.put("availSeverity", availSeverity);
/*      */           }
/* 3438 */           if (healthmsg != null)
/*      */           {
/* 3440 */             monTypeProps.put("healthmsg", healthmsg);
/*      */           }
/* 3442 */           if (img != null)
/*      */           {
/* 3444 */             monTypeProps.put("image", img);
/*      */           }
/*      */           
/*      */ 
/* 3448 */           ArrayList<String> keysList = new ArrayList(monTypeProps.keySet());
/* 3449 */           for (int i = 0; i < keysList.size(); i++)
/*      */           {
/* 3451 */             String key = (String)keysList.get(i);
/* 3452 */             String value = (String)monTypeProps.remove(key);
/* 3453 */             monTypeProps.put(key.toUpperCase(), value);
/*      */           }
/* 3455 */           monitorTypesMap.put(monType, monTypeProps);
/*      */         } }
/* 3457 */       monTypesMap.clear();
/* 3458 */       monTypesMap.putAll(monitorTypesMap);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3462 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static String ListActions(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 3470 */     String outputString = "";
/* 3471 */     String responseCode = "4000";
/* 3472 */     String uri = request.getRequestURI();
/* 3473 */     String type = request.getParameter("type") != null ? request.getParameter("type") : "all";
/*      */     
/* 3475 */     ArrayList<Hashtable> actionTypesList = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 3479 */       actionTypesList = getListOfActionForTypes(request, type);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3483 */       e.printStackTrace();
/* 3484 */       AMLog.debug("REST API : The specified request URI is incorrect: the method specified is wrong");
/* 3485 */       String message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/* 3486 */       String errorCode = "4016";
/* 3487 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/* 3490 */     HashMap dataMap = new HashMap();
/* 3491 */     dataMap.put("response-code", responseCode);
/* 3492 */     dataMap.put("uri", uri);
/* 3493 */     dataMap.put("result", actionTypesList);
/* 3494 */     dataMap.put("sortingParam", "DisplayName");
/* 3495 */     dataMap.put("parentNode", "Actions");
/* 3496 */     dataMap.put("nodeName", "Actions");
/* 3497 */     dataMap.put("subNodeName", "Action");
/*      */     
/* 3499 */     outputString = CommonAPIUtil.getOutputAsString(dataMap, isJsonFormat);
/* 3500 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String ListAgents(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 3507 */     String outputString = "";
/* 3508 */     String responseCode = "4000";
/* 3509 */     String uri = request.getRequestURI();
/* 3510 */     JSONObject agentProps = null;
/*      */     try
/*      */     {
/* 3513 */       CreateRealBrowserTest ct = new CreateRealBrowserTest();
/* 3514 */       agentProps = RealBrowserUtil.getEUMAgentList();
/* 3515 */       agentProps.put("apikey", true);
/* 3516 */       agentProps.put("status", "success");
/* 3517 */       agentProps.put("response-code", "4000");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3521 */       e.printStackTrace();
/* 3522 */       AMLog.debug("REST API : No EUM Agent is in running state");
/* 3523 */       String message = FormatUtil.getString("No EUM Agent is in running state");
/* 3524 */       String errorCode = "4016";
/* 3525 */       return generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/* 3528 */     outputString = agentProps.toString();
/* 3529 */     return outputString;
/*      */   }
/*      */   
/*      */   private static ArrayList<Hashtable> getListOfActionForTypes(HttpServletRequest request, String type) throws Exception
/*      */   {
/* 3534 */     ArrayList<Hashtable> actionTypesList = new ArrayList();
/* 3535 */     ArrayList<String> availableActionTypes = new ArrayList();
/* 3536 */     Hashtable<String, ArrayList<ArrayList<String>>> availableActions = new Hashtable();
/* 3537 */     ClientDBUtil.getActionProfiles(request, CommonAPIUtil.getOwnerName(request), CommonAPIUtil.isOperatorRole(request));
/*      */     
/* 3539 */     Hashtable actionProfiles = (Hashtable)request.getAttribute("actionProfiles");
/* 3540 */     AMLog.info("REST API:: Actions: Action Profiles==>" + actionProfiles);
/* 3541 */     request.removeAttribute("actionProfiles");
/* 3542 */     request.removeAttribute("actionIDForBusinessHourConfiguredAction");
/* 3543 */     Enumeration requestAttrbs = request.getAttributeNames();
/* 3544 */     while (requestAttrbs.hasMoreElements())
/*      */     {
/* 3546 */       Hashtable singleActionType = new Hashtable();
/* 3547 */       String actionType = requestAttrbs.nextElement().toString();
/* 3548 */       AMLog.info("REST API:: Actions: Action type :::::==>" + actionType);
/* 3549 */       if ((!actionType.startsWith("javax")) && (!actionType.startsWith("org.apache")))
/*      */       {
/* 3551 */         if ((!actionType.equals("selected")) && (!actionType.startsWith("winServiceResInfo_")))
/*      */         {
/*      */ 
/*      */           try
/*      */           {
/*      */ 
/*      */ 
/* 3558 */             ArrayList<ArrayList<String>> actionsForType = (ArrayList)request.getAttribute(actionType);
/* 3559 */             if ((actionsForType == null) || (actionsForType.size() != 0))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 3564 */               AMLog.info("REST API:: Actions: Action Attrs ::::==>" + (Hashtable)actionProfiles.get(actionType));
/* 3565 */               AMLog.info("REST API:: Actions:" + actionType + ":::::==>" + actionsForType);
/* 3566 */               availableActionTypes.add(actionType);
/*      */               
/* 3568 */               ArrayList<String> actionAttrs = (ArrayList)((Hashtable)actionProfiles.get(actionType)).get("actionProps");
/* 3569 */               ArrayList<Hashtable<String, String>> actionsList = new ArrayList();
/* 3570 */               String actionTypeDisplayName = ((Hashtable)actionProfiles.get(actionType)).get("Displayname").toString();
/*      */               
/* 3572 */               if ((actionTypeDisplayName.contains(type)) || (actionTypeDisplayName.equalsIgnoreCase(type)) || (type.equalsIgnoreCase("all")))
/*      */               {
/* 3574 */                 for (int i = 0; i < actionsForType.size(); i++)
/*      */                 {
/* 3576 */                   Hashtable singleAction = new Hashtable();
/* 3577 */                   String actionId = null;
/* 3578 */                   Hashtable<String, String> singleActionProps = new Hashtable();
/* 3579 */                   ArrayList<String> action = (ArrayList)actionsForType.get(i);
/* 3580 */                   AMLog.info("REST API:: Actions:  $Action==>" + action);
/* 3581 */                   for (int j = 0; j < actionAttrs.size(); j++)
/*      */                   {
/* 3583 */                     String key = (String)actionAttrs.get(j);
/* 3584 */                     String value = (action.get(j) != null) && (!((String)action.get(j)).equals("null")) ? (String)action.get(j) : "";
/* 3585 */                     if ((key.equalsIgnoreCase("Name")) || (key.equalsIgnoreCase("Id")))
/*      */                     {
/* 3587 */                       singleAction.put(key, value);
/* 3588 */                       if (key.equalsIgnoreCase("id"))
/*      */                       {
/* 3590 */                         actionId = value;
/*      */                       }
/*      */                     }
/* 3593 */                     else if ((!key.equalsIgnoreCase("type")) && (!key.equalsIgnoreCase("atype")))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3599 */                       if (key.equalsIgnoreCase("delay"))
/*      */                       {
/* 3601 */                         key = key + " (sec)";
/*      */                       }
/* 3603 */                       else if (key.equals("ENAME"))
/*      */                       {
/* 3605 */                         key = "EMAIL ACTION";
/*      */                       }
/* 3607 */                       singleActionProps.put(key, value);
/*      */                     }
/*      */                   }
/* 3610 */                   singleAction.put("ExecuteActionPath", getExecuteActionPath(actionType, actionId));
/* 3611 */                   singleAction.put("ActionProps", singleActionProps);
/* 3612 */                   actionsList.add(singleAction);
/*      */                 }
/*      */                 
/* 3615 */                 singleActionType.put("DisplayName", actionTypeDisplayName);
/* 3616 */                 singleActionType.put("Action", actionsList);
/* 3617 */                 actionTypesList.add(singleActionType);
/*      */               }
/*      */               else
/*      */               {
/*      */                 continue;
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 3626 */             AMLog.info("REST API: Actions Error Occurred for type==>" + actionType);
/* 3627 */             e.printStackTrace();
/*      */           } }
/*      */       }
/*      */     }
/* 3631 */     return actionTypesList;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String ListDashboards(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 3638 */     String outputString = "";
/* 3639 */     ResultSet rs = null;
/* 3640 */     String responseCode = "4000";
/* 3641 */     String uri = request.getRequestURI();
/*      */     
/* 3643 */     ArrayList<Hashtable> dashboardMap = new ArrayList();
/*      */     
/* 3645 */     String outputQuery = "SELECT MAX(AM_MYPAGES.PAGEID) as PAGEID,AM_MYPAGES.PAGENAME, AM_MYPAGE_WIDGETS.WIDGETID, AM_MYPAGE_WIDGETS.DISPLAYNAME, COALESCE(TABORDERID,999) as TABORDERID FROM AM_MYPAGES LEFT OUTER JOIN AM_MYPAGE_WIDGET_MAPPING ON AM_MYPAGES.PAGEID =  AM_MYPAGE_WIDGET_MAPPING.PAGEID LEFT OUTER JOIN AM_MYPAGE_WIDGETS ON AM_MYPAGE_WIDGETS.WIDGETID = AM_MYPAGE_WIDGET_MAPPING.WIDGETID  LEFT OUTER JOIN AM_DASHBOARDS on AM_MYPAGES.PAGEID=AM_DASHBOARDS.PAGEID where AM_MYPAGES.PAGENAME NOT IN ('Business View','Availability','QoS Worldwide(sample)') GROUP BY AM_MYPAGES.PAGENAME, AM_MYPAGE_WIDGETS.WIDGETID, AM_MYPAGE_WIDGETS.DISPLAYNAME, TABORDERID ORDER BY TABORDERID asc";
/* 3646 */     AMLog.info("RestAPI : Dashboards Query====>" + outputQuery);
/*      */     try
/*      */     {
/* 3649 */       rs = AMConnectionPool.executeQueryStmt(outputQuery);
/*      */       
/* 3651 */       String dashboardId = null;
/* 3652 */       Hashtable dashboardDetails = new Hashtable();
/* 3653 */       ArrayList<Hashtable<String, String>> widgetsList = new ArrayList();
/*      */       
/* 3655 */       while (rs.next())
/*      */       {
/* 3657 */         String pageId = rs.getString("PAGEID");
/* 3658 */         String pageName = rs.getString("PAGENAME");
/* 3659 */         String widgetId = rs.getString("WIDGETID");
/* 3660 */         String widgetName = rs.getString("DISPLAYNAME");
/* 3661 */         if (dashboardId == null)
/*      */         {
/* 3663 */           dashboardId = pageId;
/*      */         }
/*      */         
/* 3666 */         if (!dashboardId.equals(pageId))
/*      */         {
/* 3668 */           dashboardDetails.put("Widget", widgetsList);
/* 3669 */           dashboardMap.add(dashboardDetails);
/*      */           
/*      */ 
/* 3672 */           dashboardDetails = new Hashtable();
/* 3673 */           widgetsList = new ArrayList();
/*      */         }
/*      */         
/* 3676 */         dashboardDetails.put("DashboardId", pageId);
/* 3677 */         dashboardDetails.put("DashboardName", pageName);
/*      */         
/* 3679 */         Hashtable<String, String> widgetDetails = new Hashtable();
/* 3680 */         widgetDetails.put("WidgetId", widgetId);
/* 3681 */         widgetDetails.put("WidgetName", widgetName);
/* 3682 */         widgetDetails.put("WidgetURL", "/MyPage.do?method=getWidget&pageid=" + pageId + "&widgetid=" + widgetId);
/* 3683 */         widgetsList.add(widgetDetails);
/*      */         
/* 3685 */         dashboardId = pageId;
/*      */       }
/*      */       
/* 3688 */       dashboardDetails.put("Widget", widgetsList);
/* 3689 */       dashboardMap.add(dashboardDetails);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3693 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3697 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3700 */     HashMap dataMap = new HashMap();
/* 3701 */     dataMap.put("response-code", responseCode);
/* 3702 */     dataMap.put("uri", uri);
/* 3703 */     dataMap.put("result", dashboardMap);
/* 3704 */     dataMap.put("skipSorting", "true");
/* 3705 */     dataMap.put("parentNode", "Dashboards");
/* 3706 */     dataMap.put("nodeName", "Dashboard");
/* 3707 */     dataMap.put("subNodeName", "Widget");
/*      */     
/* 3709 */     outputString = CommonAPIUtil.getOutputAsString(dataMap, isJsonFormat);
/* 3710 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String executeActionXML(HttpServletRequest request, HttpServletResponse response, String actionId, boolean isJsonFormat) throws Exception
/*      */   {
/* 3715 */     String output = null;
/* 3716 */     AMLog.info("REST API : ExecuteActionXML");
/* 3717 */     String userid = CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey"));
/* 3718 */     ArrayList<String> assocActionsList = ClientDBUtil.getActionsForOwner(CommonAPIUtil.getOwnerName(request), userid, CommonAPIUtil.isOperatorRole(request));
/* 3719 */     if ((CommonAPIUtil.isAdminRole(request)) || (assocActionsList.contains(actionId)))
/*      */     {
/* 3721 */       int typeId = 0;
/* 3722 */       String actionName = "";
/* 3723 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 3726 */         rs = AMConnectionPool.executeQueryStmt("Select * from AM_ACTIONPROFILE where ID = " + actionId);
/* 3727 */         if (rs.next())
/*      */         {
/* 3729 */           typeId = rs.getInt("TYPE");
/* 3730 */           actionName = rs.getString("NAME");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3735 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3739 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/* 3741 */       Hashtable<String, String> actionExecResult = getActionProps(typeId, actionId, actionName);
/* 3742 */       actionExecResult.put("ActionName", actionName);
/* 3743 */       actionExecResult.put("ActionID", actionId);
/* 3744 */       String message = (String)actionExecResult.remove("message");
/* 3745 */       String actionTypeId = (String)actionExecResult.remove("actiontype");
/* 3746 */       String status = (String)actionExecResult.remove("success");
/* 3747 */       actionExecResult.remove("name");
/* 3748 */       actionExecResult.put("Message", message);
/* 3749 */       actionExecResult.put("Status", status.equals("true") ? "Success" : "Failed");
/* 3750 */       actionExecResult.put("ActionTypeID", actionTypeId);
/* 3751 */       AMLog.info("RESTAPI : ExecuteAction : actionExecResult:" + actionExecResult);
/* 3752 */       ArrayList<Hashtable<String, String>> result = new ArrayList();
/* 3753 */       result.add(actionExecResult);
/*      */       
/* 3755 */       HashMap execActionMap = new HashMap();
/* 3756 */       execActionMap.put("response-code", "4000");
/* 3757 */       execActionMap.put("uri", request.getRequestURI());
/* 3758 */       execActionMap.put("result", result);
/* 3759 */       execActionMap.put("nodeName", "ExecuteAction");
/* 3760 */       output = getOutputAsString(execActionMap, isJsonFormat);
/*      */     }
/*      */     else
/*      */     {
/* 3764 */       AMLog.debug("REST API : The specified action id is not associated to the user.");
/* 3765 */       output = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.actionid.notassociated.message"), "4016");
/*      */     }
/* 3767 */     return output;
/*      */   }
/*      */   
/*      */   private static String pingResultXML(HttpServletRequest request, HttpServletResponse response, String resId, String host, boolean isJsonFormat)
/*      */   {
/* 3772 */     String outputString = null;String ipAddress = "";
/* 3773 */     ResultSet rs = null;
/* 3774 */     ArrayList<Hashtable<String, String>> result = new ArrayList();
/* 3775 */     Hashtable<String, String> pingResultTable = new Hashtable();
/* 3776 */     HashMap pingMap = new HashMap();
/*      */     try
/*      */     {
/* 3779 */       if (isJsonFormat) {
/* 3780 */         response.setContentType("text/plain;charset=UTF-8");
/*      */       }
/*      */       
/* 3783 */       String hostName = "";
/* 3784 */       if ((host == null) && (resId != null))
/*      */       {
/* 3786 */         String operatorCondition = "";
/* 3787 */         if (CommonAPIUtil.isPrivilegedUser(request)) {
/* 3788 */           Vector<String> resIdsOwned = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey")));
/* 3789 */           if (!resIdsOwned.contains(resId)) {
/* 3790 */             AMLog.debug("REST API : The user is not authorized to do this operation.");
/* 3791 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 3792 */             return outputString;
/*      */           }
/* 3794 */           operatorCondition = DBUtil.getCondition(" and AM_ManagedObject.RESOURCEID ", resIdsOwned);
/*      */         }
/*      */         
/* 3797 */         String qry = "select RESOURCENAME, RESOURCEID from AM_ManagedObject, AM_MONITOR_TYPES where AM_ManagedObject.type not in (select typename from AM_MONITOR_TYPES where basetype='Script Monitor') and AM_ManagedObject.Resourceid =" + resId + " and AM_ManagedObject.TYPE in " + Constants.resourceTypes + operatorCondition + " group by RESOURCENAME, RESOURCEID";
/* 3798 */         AMLog.info("REST API: Ping RESID with Qry:" + qry);
/* 3799 */         rs = AMConnectionPool.executeQueryStmt(qry);
/* 3800 */         if (rs.next())
/*      */         {
/* 3802 */           String resourceName = rs.getString("RESOURCENAME");
/* 3803 */           hostName = resourceName.contains("_") ? resourceName.substring(0, resourceName.indexOf("_")) : resourceName;
/* 3804 */           host = hostName.replaceAll("IF-", "");
/*      */         }
/*      */         else
/*      */         {
/* 3808 */           String resourceType = DBUtil.getResourceType(resId);
/* 3809 */           hostName = CommonAPIUtil.getHostName(resourceType, resId);
/* 3810 */           if ((hostName == null) || (hostName.equals("")))
/*      */           {
/*      */ 
/* 3813 */             AMLog.debug("REST API : Not able to find the host details for given Resourceid.");
/* 3814 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.ping.nohostdetails.message"), "4016");
/*      */           }
/* 3816 */           host = hostName;
/*      */         }
/*      */       }
/*      */       
/* 3820 */       InetAddress inetAdd = InetAddress.getByName(host);
/* 3821 */       if (inetAdd.equals(host))
/*      */       {
/* 3823 */         hostName = host;
/*      */       }
/*      */       else
/*      */       {
/* 3827 */         hostName = inetAdd.getCanonicalHostName();
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 3833 */         ipAddress = inetAdd.getHostAddress().toString();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3837 */         e.printStackTrace();
/* 3838 */         ipAddress = "";
/*      */       }
/*      */       
/*      */ 
/* 3842 */       Properties pingResults = AppManagerPing.ping(hostName, PingTestConstants.PING_RETRIES, 1);
/* 3843 */       pingResultTable.put("Host", hostName);
/* 3844 */       pingResultTable.put("IPAddress", ipAddress);
/* 3845 */       pingResultTable.put("Output", pingResults.getProperty("output").replaceAll("\\r\\n", "<br>").replaceFirst("<br>", ""));
/* 3846 */       result.add(pingResultTable);
/*      */       
/* 3848 */       pingMap.put("response-code", "4000");
/* 3849 */       pingMap.put("uri", request.getRequestURI());
/* 3850 */       pingMap.put("result", result);
/* 3851 */       pingMap.put("nodeName", "PingResult");
/* 3852 */       outputString = getOutputAsString(pingMap, isJsonFormat);
/*      */       
/* 3854 */       AMLog.info(pingResults);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3858 */       e.printStackTrace();
/*      */       
/*      */       try
/*      */       {
/* 3862 */         AMLog.debug("REST API : Not able to find the host details for given Resourceid.");
/* 3863 */         outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.ping.nohostdetails.message"), "4016");
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3867 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 3872 */       if (rs != null)
/*      */       {
/* 3874 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 3877 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String executeAlarmAction(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 3882 */     String message = "";
/*      */     
/* 3884 */     String outputString = "";
/* 3885 */     ResultSet rs = null;
/* 3886 */     String responseCode = "4000";
/* 3887 */     boolean needTextNode = Boolean.TRUE.booleanValue();
/* 3888 */     String uri = request.getRequestURI();
/* 3889 */     HashMap responseMap = new HashMap();
/*      */     
/* 3891 */     String requestedUserName = CommonAPIUtil.getOwnerName(request);
/* 3892 */     boolean isRequestFromAdmin = CommonAPIUtil.isAdminRole(request);
/* 3893 */     String userName = (request.getParameter("username") != null) && (isRequestFromAdmin) ? request.getParameter("username").trim() : CommonAPIUtil.getOwnerName(request);
/* 3894 */     if ((userName == null) || (userName.length() == 0)) {
/* 3895 */       userName = requestedUserName;
/*      */     }
/* 3897 */     String actiontype = "AlarmAction";
/*      */     
/* 3899 */     String entity = request.getParameter("entity");
/* 3900 */     String actionToBeExecuted = request.getParameter("action");
/* 3901 */     String resId = getResourceIdForEntity(request.getParameter("entity"));
/* 3902 */     boolean isUserAllowedForOperation = checkUserAccessToResource(request, userName, resId);
/*      */     
/* 3904 */     if (!isUserAllowedForOperation) {
/* 3905 */       outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.alarmaction.nopermission.msg", new String[] { userName }), "4080");
/* 3906 */       return outputString;
/*      */     }
/*      */     try
/*      */     {
/*      */       MockHttpServletRequest mReq;
/* 3911 */       if (actionToBeExecuted.equals("PickupAlarm"))
/*      */       {
/* 3913 */         String pickUpStatus = "";
/* 3914 */         pickUpStatus = AlarmOperationsUtility.getInstance().pickTheAlert(userName, entity, true);
/* 3915 */         if (pickUpStatus.equals("true"))
/*      */         {
/* 3917 */           message = FormatUtil.getString("am.webclient.apikey.pickalarm.success.txt");
/* 3918 */           if (ExtConnectorUtil.isPushEnabled)
/*      */           {
/*      */             try
/*      */             {
/* 3922 */               OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 3923 */               opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_PICKED");
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 3927 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3933 */           outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongpickup.message"), "4006");
/* 3934 */           return outputString;
/*      */         }
/*      */       }
/* 3937 */       else if (actionToBeExecuted.equals("UnpickupAlarm"))
/*      */       {
/* 3939 */         String unPickUpStatus = "";
/* 3940 */         unPickUpStatus = AlarmOperationsUtility.getInstance().unPickTheAlert(userName, entity, true);
/* 3941 */         if (unPickUpStatus.equals("true"))
/*      */         {
/* 3943 */           message = FormatUtil.getString("am.webclient.apikey.unpickalarm.success.txt");
/* 3944 */           if (ExtConnectorUtil.isPushEnabled)
/*      */           {
/*      */             try
/*      */             {
/* 3948 */               OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 3949 */               opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_UNPICKED");
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 3953 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3959 */           outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongunpickup.message"), "4006");
/* 3960 */           return outputString;
/*      */         }
/*      */       }
/* 3963 */       else if (actionToBeExecuted.equals("ClearAlarm"))
/*      */       {
/* 3965 */         AMLog.debug("FAULT : Clear Alert called for user" + userName);
/* 3966 */         String alertClearStatus; if (isRequestFromAdmin)
/*      */         {
/* 3968 */           request.setAttribute("entity", entity);
/*      */           
/* 3970 */           alertClearStatus = "" + FaultUtil.clearAlert(entity, new StringBuilder().append("").append(request.getRemoteUser()).toString());
/* 3971 */           AMLog.debug("FAULT : Alert cleared ??" + alertClearStatus);
/* 3972 */           if (alertClearStatus.equals("true"))
/*      */           {
/* 3974 */             message = FormatUtil.getString("am.webclient.apikey.clearalarm.success.txt");
/* 3975 */             if (ExtConnectorUtil.isPushEnabled)
/*      */             {
/*      */               try
/*      */               {
/* 3979 */                 OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 3980 */                 opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_CLEARED");
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/* 3984 */                 ex.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3990 */             outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongclear.message"), "4006");
/* 3991 */             return outputString;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3996 */           outputString = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.clear.noperm.message"), "4006");
/* 3997 */           return outputString;
/*      */         }
/*      */       }
/* 4000 */       else if (actionToBeExecuted.equals("AddAnnotation"))
/*      */       {
/* 4002 */         String annotationMessage = request.getParameter("message");
/* 4003 */         message = FormatUtil.getString("am.webclient.apikey.add.annotation.success");
/* 4004 */         MockHttpServletRequest mReq = new MockHttpServletRequest();
/* 4005 */         mReq.addParameter("entity", entity);
/* 4006 */         mReq.addParameter("text", annotationMessage);
/* 4007 */         mReq.addParameter("isApiRequest", "true");
/* 4008 */         mReq.addParameter("username", userName != null ? userName : request.getRemoteUser());
/* 4009 */         AlarmOperationsDispatchAction alarmOperations = new AlarmOperationsDispatchAction();
/* 4010 */         alarmOperations.setAnnotation(null, null, mReq, response);
/* 4011 */         boolean isAlertAnnotated = ((Boolean)mReq.getAttribute("isAlertAnnotated")).booleanValue();
/* 4012 */         if (!isAlertAnnotated) {
/* 4013 */           responseCode = "4077";
/* 4014 */           message = FormatUtil.getString("am.webclient.apikey.add.annotation.failed");
/*      */         }
/*      */       }
/* 4017 */       else if (actionToBeExecuted.equals("ListAnnotations"))
/*      */       {
/* 4019 */         mReq = new MockHttpServletRequest();
/* 4020 */         mReq.addParameter("entity", entity);
/* 4021 */         mReq.addParameter("isApiRequest", "true");
/* 4022 */         mReq.addParameter("username", userName != null ? userName : request.getRemoteUser());
/* 4023 */         AlarmOperationsDispatchAction alarmOperations = new AlarmOperationsDispatchAction();
/* 4024 */         alarmOperations.viewAnnotationAndHistory(null, null, mReq, response);
/* 4025 */         Vector<Properties> annPropList = (Vector)mReq.getAttribute("annotationValue");
/* 4026 */         if (annPropList.size() > 0) {
/* 4027 */           needTextNode = Boolean.FALSE.booleanValue();
/* 4028 */           ArrayList<Hashtable<String, String>> result = new ArrayList();
/* 4029 */           for (Properties annotProp : annPropList) {
/* 4030 */             Hashtable<String, String> annotation = new Hashtable();
/* 4031 */             annotation.put("Message", annotProp.getProperty("notes", ""));
/* 4032 */             annotation.put("ModTime", annotProp.getProperty("modTime", "-1"));
/* 4033 */             annotation.put("LongTime", annotProp.getProperty("longTime", "-1"));
/* 4034 */             annotation.put("User", annotProp.getProperty("who", ""));
/* 4035 */             result.add(annotation);
/*      */           }
/* 4037 */           responseMap.put("result", result);
/* 4038 */           responseMap.put("sortingParam", "Time");
/* 4039 */           responseMap.put("sortingOrder", "desc");
/* 4040 */           responseMap.put("parentNode", "Annotations");
/* 4041 */           responseMap.put("nodeName", "Annotation");
/*      */         }
/*      */         else {
/* 4044 */           message = FormatUtil.getString("am.webclient.apikey.annotates.empty.txt");
/*      */         }
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
/* 4056 */       responseMap.put("response-code", responseCode);
/* 4057 */       responseMap.put("uri", uri);
/* 4058 */       if (needTextNode) {
/* 4059 */         responseMap.put("message", message);
/* 4060 */         responseMap.put("nodeName", "message");
/* 4061 */         responseMap.put("needTextNode", String.valueOf(needTextNode));
/* 4062 */         outputString = generateResp(responseMap, isJsonFormat);
/*      */       }
/*      */       else {
/* 4065 */         outputString = getOutputAsString(responseMap, isJsonFormat);
/*      */       }
/* 4067 */       return outputString;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4071 */       e.printStackTrace();
/* 4072 */       AMLog.debug("REST API : The specified request URI is incorrect: the method specified is wrong");
/* 4073 */       message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/* 4074 */       String errorCode = "4016";
/* 4075 */       outputString = generateXML(request, response, message, errorCode);
/*      */     }
/*      */     finally
/*      */     {
/* 4079 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 4081 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean checkUserAccessToResource(HttpServletRequest request, String userName, String resId)
/*      */   {
/* 4090 */     boolean hasAccessToResource = Boolean.TRUE.booleanValue();
/* 4091 */     Hashtable<String, Boolean> userPrivileges = (Hashtable)request.getSession().getServletContext().getAttribute("userPrivileges");
/* 4092 */     Hashtable<String, String> userDetails = CommonAPIUtil.getUserDetails(userName);
/* 4093 */     hasAccessToResource = userDetails != null;
/* 4094 */     if ((userDetails != null) && (Constants.isPrivilegedUser(userName, (String)userDetails.get("GROUPNAME"), userPrivileges))) {
/* 4095 */       Vector<String> resIdList = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 4096 */       if (resIdList != null) {
/* 4097 */         hasAccessToResource = resIdList.contains(resId);
/*      */       }
/*      */     }
/* 4100 */     return hasAccessToResource;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getResourceIdForEntity(String entity)
/*      */   {
/* 4108 */     String resId = "";
/*      */     try {
/* 4110 */       if ((entity != null) && (entity.trim().length() > 0) && (entity.contains("_"))) {
/* 4111 */         resId = entity.substring(0, entity.indexOf("_"));
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 4115 */       e.printStackTrace();
/*      */     }
/* 4117 */     return resId;
/*      */   }
/*      */   
/*      */   private static String getExecuteActionPath(String actionType, String actionId)
/*      */   {
/* 4122 */     String pathToReturn = "";
/* 4123 */     if (actionId != null)
/*      */     {
/* 4125 */       if ((actionType.contains("dumpActions")) || (actionType.contains("performgcActions")))
/*      */       {
/* 4127 */         pathToReturn = "/common/executeAction.do?method=testthreaddumpAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4129 */       else if (actionType.contains("execprogActions"))
/*      */       {
/* 4131 */         pathToReturn = "/common/executeScript.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4133 */       else if (actionType.contains("smsActions"))
/*      */       {
/* 4135 */         pathToReturn = "/common/executeSMS.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4137 */       else if (actionType.contains("emailActions"))
/*      */       {
/* 4139 */         pathToReturn = "/common/executeAction.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4141 */       else if (actionType.contains("TrapActions"))
/*      */       {
/* 4143 */         pathToReturn = "/common/executeTrap.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4145 */       else if (actionType.contains("MopActions"))
/*      */       {
/* 4147 */         pathToReturn = "/common/executeMBeanOperation.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/* 4149 */       else if (actionType.contains("winServiceActions"))
/*      */       {
/* 4151 */         pathToReturn = "/HostResourceDispatch.do?method=showWinServActDetails&remote=true&actionid=" + actionId;
/*      */       }
/* 4153 */       else if (actionType.contains("VmActions"))
/*      */       {
/* 4155 */         pathToReturn = "/testVMAction.do?method=collectVMs&remote=true&actionID=" + actionId;
/*      */       }
/* 4157 */       else if (actionType.contains("ec2"))
/*      */       {
/* 4159 */         pathToReturn = "/manageEC2Instances.do?method=testamazonactions&remote=true&actionID=" + actionId;
/*      */       }
/* 4161 */       else if (actionType.contains("ticket"))
/*      */       {
/* 4163 */         pathToReturn = "/common/executeTicket.do?method=testAction&remote=true&actionID=" + actionId;
/*      */       }
/*      */     }
/* 4166 */     return pathToReturn;
/*      */   }
/*      */   
/*      */   private static String getOutputAsString(HashMap results, boolean isJsonFormat) throws Exception
/*      */   {
/* 4171 */     String toReturn = "";
/*      */     try
/*      */     {
/* 4174 */       String responseCode = (String)results.get("response-code");
/* 4175 */       String uri = (String)results.get("uri");
/* 4176 */       String nodeName = (String)results.get("nodeName");
/* 4177 */       String sortByParam = results.get("sortingParam") != null ? (String)results.get("sortingParam") : "DISPLAYNAME";
/* 4178 */       AMLog.debug("RESTAPI:Sorting param==>" + sortByParam);
/* 4179 */       AMLog.info("RESTAPI:Data==>" + results);
/* 4180 */       ArrayList<Hashtable> result = (ArrayList)results.get("result");
/* 4181 */       AMLog.info("RESTAPI: OutputString : Results:" + results);
/* 4182 */       ArrayList<Hashtable> sortedResult = CommonAPIUtil.getSortedList(result, sortByParam);
/* 4183 */       String sortingOrder = (String)results.get("sortingOrder");
/* 4184 */       if ((sortingOrder != null) && (sortingOrder.equals("desc")))
/*      */       {
/* 4186 */         Collections.reverse(sortedResult);
/*      */       }
/* 4188 */       AMLog.info("RESTAPI: OutputString : SortedResults:" + sortedResult);
/*      */       
/* 4190 */       if (isJsonFormat)
/*      */       {
/* 4192 */         JSONObject jsonOutput = new JSONObject();
/* 4193 */         JSONObject jsonResponse = new JSONObject();
/* 4194 */         JSONArray jsonResultArray = new JSONArray();
/*      */         
/*      */         try
/*      */         {
/* 4198 */           for (int i = 0; i < sortedResult.size(); i++)
/*      */           {
/* 4200 */             jsonResultArray.put(new JSONObject((Map)sortedResult.get(i)));
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 4205 */           AMLog.debug("REST API : Error in generating JSON Feed!");
/* 4206 */           responseCode = "40xx";
/* 4207 */           jsonResultArray = new JSONArray();
/* 4208 */           jsonResultArray.put(new JSONObject().put("message", FormatUtil.getString("Error in generating JSON Feed!")));
/* 4209 */           e.printStackTrace();
/*      */         }
/* 4211 */         jsonOutput.put("response-code", responseCode);
/* 4212 */         jsonResponse.put("uri", uri);
/* 4213 */         jsonResponse.put("result", jsonResultArray);
/* 4214 */         jsonOutput.put("response", jsonResponse);
/*      */         
/* 4216 */         toReturn = jsonOutput.toString();
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 4222 */           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 4223 */           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 4224 */           Document doc = docBuilder.newDocument();
/*      */           
/* 4226 */           AMLog.debug("REST API : root node uri" + uri);
/* 4227 */           Element rootNode = doc.createElement("AppManager-response");
/* 4228 */           rootNode.setAttribute("uri", uri);
/* 4229 */           doc.appendChild(rootNode);
/* 4230 */           Element resultNode = doc.createElement("result");
/* 4231 */           rootNode.appendChild(resultNode);
/*      */           
/* 4233 */           Element responseNode = doc.createElement("response");
/* 4234 */           responseNode.setAttribute("response-code", responseCode);
/* 4235 */           resultNode.appendChild(responseNode);
/*      */           
/* 4237 */           for (int i = 0; i < result.size(); i++)
/*      */           {
/* 4239 */             Element nodeElement = doc.createElement(nodeName);
/* 4240 */             ArrayList<String> keys = new ArrayList(((Hashtable)result.get(i)).keySet());
/*      */             
/* 4242 */             for (int k = 0; k < keys.size(); k++)
/*      */             {
/*      */               try
/*      */               {
/* 4246 */                 String key = (String)keys.get(k);
/* 4247 */                 String value = (String)((Hashtable)result.get(i)).get(keys.get(k));
/* 4248 */                 if ((!key.endsWith("count")) || (value != null))
/*      */                 {
/*      */ 
/*      */ 
/* 4252 */                   nodeElement.setAttribute(key, value);
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*      */ 
/* 4260 */                 e.printStackTrace();
/*      */               }
/*      */             }
/* 4263 */             responseNode.appendChild(nodeElement);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 4269 */           TransformerFactory factory = TransformerFactory.newInstance();
/* 4270 */           Transformer transformer = factory.newTransformer();
/* 4271 */           transformer.setOutputProperty("indent", "yes");
/* 4272 */           StringWriter sw = new StringWriter();
/* 4273 */           StreamResult streamResult = new StreamResult(sw);
/* 4274 */           DOMSource source = new DOMSource(doc);
/* 4275 */           transformer.transform(source, streamResult);
/* 4276 */           toReturn = sw.toString();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 4280 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4286 */       e.printStackTrace();
/*      */     }
/* 4288 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getActionProps(int typeId, String actionId, String actionName)
/*      */   {
/* 4293 */     String actionType = null;
/* 4294 */     String actionExecPath = null;
/* 4295 */     Hashtable<String, String> actionResult = new Hashtable();
/*      */     
/* 4297 */     switch (typeId)
/*      */     {
/*      */ 
/*      */     case 1: 
/* 4301 */       actionType = "E-mail Action(s)";
/* 4302 */       actionExecPath = "/common/executeAction.do?method=testAction&actionID=" + actionId;
/* 4303 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4304 */       break;
/*      */     
/*      */ 
/*      */     case 6: 
/* 4308 */       actionType = "Log a Ticket(s)";
/* 4309 */       actionExecPath = "/common/executeTicket.do?method=testAction&actionID=" + actionId;
/* 4310 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4311 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*      */     case 5: 
/* 4316 */       actionType = "SMS Action(s)";
/* 4317 */       actionExecPath = "/common/executeSMS.do?method=testAction&actionID=" + actionId;
/* 4318 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4319 */       break;
/*      */     
/*      */ 
/*      */     case 3: 
/* 4323 */       actionType = "Execute Program Action(s)";
/* 4324 */       actionExecPath = "/common/executeScript.do?method=testAction&actionID=" + actionId;
/* 4325 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4326 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/* 4330 */       actionType = "Execute MBean Operation Action(s)";
/* 4331 */       actionExecPath = "/common/executeMBeanOperation.do?method=testAction&actionID=" + actionId;
/* 4332 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4333 */       break;
/*      */     
/*      */ 
/*      */     case 11: 
/*      */     case 12: 
/*      */     case 13: 
/* 4339 */       actionType = "SNMP Trap Action(s)";
/* 4340 */       actionExecPath = "/common/executeTrap.do?method=testAction&actionID=" + actionId;
/* 4341 */       actionResult = AdminTools.getActionExecResp("testAction", actionId);
/* 4342 */       break;
/*      */     
/*      */ 
/*      */     case 7: 
/* 4346 */       actionType = "Java Thread Dump";
/* 4347 */       actionExecPath = "/common/executeAction.do?method=testthreaddumpAction&actionID=" + actionId;
/* 4348 */       actionResult = AdminTools.getActionExecResp("testthreaddumpAction", actionId);
/* 4349 */       break;
/*      */     
/*      */ 
/*      */     case 8: 
/* 4353 */       actionType = "Java Heap Dump";
/* 4354 */       actionExecPath = "/common/executeAction.do?method=testthreaddumpAction&actionID=" + actionId;
/* 4355 */       actionResult = AdminTools.getActionExecResp("testthreaddumpAction", actionId);
/* 4356 */       break;
/*      */     
/*      */ 
/*      */     case 9: 
/* 4360 */       actionType = "Perform Java GC";
/* 4361 */       actionExecPath = "/common/executeAction.do?method=testthreaddumpAction&actionID=" + actionId;
/* 4362 */       actionResult = AdminTools.getActionExecResp("testthreaddumpAction", actionId);
/* 4363 */       break;
/*      */     
/*      */ 
/*      */     case 14: 
/*      */     case 15: 
/*      */     case 16: 
/* 4369 */       actionType = "Amazon EC2 Instance Action";
/* 4370 */       actionExecPath = "/manageEC2Instances.do?method=testamazonactions&actionID=" + actionId;
/* 4371 */       Object[] arglist = { actionId, "true" };
/* 4372 */       getExecuteActionResp("com.manageengine.appmanager.amazon.client.actions.EC2InstanceManageActions", "getAmazonActionResp", arglist);
/* 4373 */       break;
/*      */     
/*      */ 
/*      */     case 101: 
/*      */     case 102: 
/*      */     case 103: 
/*      */     case 201: 
/*      */     case 202: 
/*      */     case 203: 
/*      */     case 801: 
/*      */     case 802: 
/*      */     case 803: 
/* 4385 */       actionType = "VM Action(s)";
/* 4386 */       actionExecPath = "/testVMAction.do?method=collectVMs&actionID=" + actionId;
/* 4387 */       Object[] arglist = { actionId, "true" };
/* 4388 */       actionResult = getExecuteActionResp("com.manageengine.appmanager.vservers.vmware.actions.VMManageActions", "getVMActionResp", arglist);
/* 4389 */       break;
/*      */     
/*      */ 
/*      */     case 301: 
/*      */     case 302: 
/*      */     case 303: 
/* 4395 */       actionType = "Windows Services Action";
/* 4396 */       actionExecPath = "/HostResourceDispatch.do?method=showWinServActDetails&actionid=" + actionId;
/* 4397 */       Object[] arglist = { actionId, actionName, "true" };
/* 4398 */       actionResult = getExecuteActionResp("com.adventnet.appmanager.hostresource.struts.HostResourceDispatchAction", "getExecWinServiceActionResp", arglist);
/* 4399 */       break;
/*      */     }
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
/* 4420 */     actionResult.put("ActionType", actionType);
/* 4421 */     actionResult.put("ActionExecPath", actionExecPath);
/* 4422 */     return actionResult;
/*      */   }
/*      */   
/*      */   private static Hashtable<String, String> getExecuteActionResp(String className, String methodName, Object[] arglist)
/*      */   {
/* 4427 */     Hashtable<String, String> toReturn = new Hashtable();
/*      */     try
/*      */     {
/* 4430 */       Class executeActionClass = Class.forName(className);
/* 4431 */       Method executeActionMethodToBeInvoked = executeActionClass.getDeclaredMethod(methodName, new Class[0]);
/* 4432 */       return (Hashtable)executeActionMethodToBeInvoked.invoke(executeActionClass, arglist);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4436 */       e.printStackTrace();
/*      */     }
/* 4438 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ArrayList<String> getAssocaitedTypes(String type)
/*      */   {
/* 4447 */     ArrayList<String> associatedTypes = new ArrayList();
/*      */     
/* 4449 */     if (type.equals("server"))
/*      */     {
/* 4451 */       associatedTypes = getListFromResGpTypesStr((String)Constants.it360ResGpTypes.get("server"));
/*      */     }
/* 4453 */     else if (type.equals("network"))
/*      */     {
/* 4455 */       associatedTypes = getListFromResGpTypesStr((String)Constants.it360ResGpTypes.get("network"));
/*      */     }
/* 4457 */     else if (type.equals("application"))
/*      */     {
/*      */ 
/*      */ 
/* 4461 */       associatedTypes = getListFromResGpTypesStr((String)Constants.it360ResGpTypes.get("application"));
/*      */     }
/* 4463 */     return associatedTypes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ArrayList<String> getListFromResGpTypesStr(String resGpTypes)
/*      */   {
/* 4472 */     ArrayList<String> listToReturn = new ArrayList();
/*      */     
/* 4474 */     Matcher matcher = Pattern.compile("\\w+").matcher(resGpTypes);
/* 4475 */     while (matcher.find()) {
/* 4476 */       listToReturn.add(matcher.group());
/*      */     }
/* 4478 */     return listToReturn;
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
/*      */   public static String ListAlarms(HttpServletRequest request, HttpServletResponse response, String resourceId)
/*      */     throws Exception
/*      */   {
/* 4506 */     String outputString = "";
/* 4507 */     String message = "";
/* 4508 */     String errorCode = "";
/* 4509 */     String uri = request.getRequestURI();
/* 4510 */     boolean isJsonFormat = uri.contains("json");
/* 4511 */     if (isJsonFormat) {
/* 4512 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 4514 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 4516 */     String responseCode = "4000";
/* 4517 */     ArrayList<Hashtable> result = new ArrayList();
/* 4518 */     ResultSet rs = null;
/*      */     try {
/* 4520 */       Hashtable alarm = new Hashtable();
/* 4521 */       String monitorDetailsPage = "/showapplication.do?method=showApplication&haid=" + resourceId + "&PRINTER_FRIENDLY=true";
/* 4522 */       String healthid = AMAttributesCache.getHealthId("HAI");
/* 4523 */       String availid = AMAttributesCache.getAvailabilityId("HAI");
/* 4524 */       HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(resourceId, "HAI");
/* 4525 */       String availStatus = "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY");
/* 4526 */       String shortMessage = getShortMsgForAlert(availStatus, availStatus);
/* 4527 */       alarm.put("RESOURCEID", resourceId);
/* 4528 */       alarm.put("DISPLAYNAME", request.getParameter("groupName"));
/* 4529 */       alarm.put("TYPE", "HAI");
/* 4530 */       alarm.put("TYPEDISPLAYNAME", "Monitor Group");
/* 4531 */       alarm.put("DetailsPageURL", monitorDetailsPage);
/* 4532 */       alarm.put("AVAILABILITYSEVERITY", availStatus);
/* 4533 */       alarm.put("AVAILABILITYMESSAGE", healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 4534 */       alarm.put("STATUS", availStatus);
/* 4535 */       alarm.put("MESSAGE", healthAvailEntitys.get("HEALTHMESSAGE"));
/* 4536 */       alarm.put("SHORTMESSAGE", shortMessage);
/* 4537 */       alarm.put("HEALTHMESSAGE", healthAvailEntitys.get("HEALTHMESSAGE"));
/* 4538 */       alarm.put("HEALTHSEVERITY", healthAvailEntitys.get("HEALTHSEVERITY"));
/* 4539 */       alarm.put("AVAILABILITYMODTIME", healthAvailEntitys.get("AVAILABILITYMODTIME"));
/* 4540 */       alarm.put("HEALTHMODTIME", healthAvailEntitys.get("HEALTHMODTIME"));
/* 4541 */       result.add(alarm);
/*      */       
/*      */ 
/* 4544 */       String category = request.getParameter("type");
/* 4545 */       if (category != null)
/*      */       {
/* 4547 */         String categoryCond = null;
/* 4548 */         if (category.contains(",")) {
/* 4549 */           if (category.contains("all")) {
/* 4550 */             categoryCond = "'1','4','5'";
/*      */           } else {
/* 4552 */             if (category.contains("critical"))
/* 4553 */               categoryCond = "'1'";
/* 4554 */             if (category.contains("warning"))
/* 4555 */               categoryCond = categoryCond != null ? categoryCond + ",'4'" : "'4'";
/* 4556 */             if (category.contains("clear")) {
/* 4557 */               categoryCond = categoryCond != null ? categoryCond + ",'5'" : "'5'";
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 4562 */         Vector<String> mgchildIds = new Vector();
/* 4563 */         ParentChildRelationalUtil.getAllChildMapper(mgchildIds, resourceId);
/* 4564 */         String query = "select RESOURCEID,DISPLAYNAME,TYPE from Alert join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source where " + DBUtil.getCondition("AM_ManagedObject.RESOURCEID", mgchildIds) + " and Alert.groupname='AppManager' ";
/* 4565 */         if (categoryCond != null) {
/* 4566 */           query = query + " AND SEVERITY IN(" + categoryCond + ")";
/*      */         }
/* 4568 */         else if (category.equalsIgnoreCase("critical")) {
/* 4569 */           query = query + " AND SEVERITY ='1'";
/*      */         }
/* 4571 */         else if (category.equalsIgnoreCase("warning")) {
/* 4572 */           query = query + " AND SEVERITY ='4'";
/*      */         }
/* 4574 */         else if (category.equalsIgnoreCase("clear")) {
/* 4575 */           query = query + " AND SEVERITY ='5'";
/*      */         }
/* 4577 */         else if (category.equalsIgnoreCase("all"))
/*      */         {
/* 4579 */           query = query + " AND SEVERITY IN('1','4','5')";
/*      */         }
/*      */         
/* 4582 */         HashMap<String, String> healthAvailEntity = null;
/* 4583 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 4584 */         while (rs.next()) {
/* 4585 */           Hashtable monitorInfo = new Hashtable();
/* 4586 */           String type = rs.getString("TYPE");
/* 4587 */           String resId = rs.getString("RESOURCEID");
/* 4588 */           String avStatus = "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY");
/* 4589 */           healthAvailEntity = CommonAPIUtil.getHealthAvailabilityDetails(resId, type);
/* 4590 */           monitorInfo.put("RESOURCEID", resId);
/* 4591 */           monitorInfo.put("DISPLAYNAME", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 4592 */           monitorInfo.put("TYPE", type);
/* 4593 */           monitorInfo.put("TYPEDISPLAYNAME", "Monitor");
/* 4594 */           monitorInfo.put("AVAILABILITYSEVERITY", avStatus);
/* 4595 */           monitorInfo.put("AVAILABILITYMESSAGE", healthAvailEntity.get("AVAILABILITYMESSAGE"));
/* 4596 */           monitorInfo.put("STATUS", avStatus);
/* 4597 */           monitorInfo.put("MESSAGE", healthAvailEntity.get("HEALTHMESSAGE"));
/* 4598 */           monitorInfo.put("HEALTHMESSAGE", healthAvailEntity.get("HEALTHMESSAGE"));
/* 4599 */           monitorInfo.put("HEALTHSEVERITY", healthAvailEntity.get("HEALTHSEVERITY"));
/* 4600 */           monitorInfo.put("AVAILABILITYMODTIME", healthAvailEntity.get("AVAILABILITYMODTIME"));
/* 4601 */           monitorInfo.put("HEALTHMODTIME", healthAvailEntity.get("HEALTHMODTIME"));
/* 4602 */           result.add(monitorInfo);
/*      */         }
/* 4604 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/* 4607 */       if (result.size() == 0)
/*      */       {
/* 4609 */         Hashtable msg = new Hashtable();
/* 4610 */         msg.put("Message", FormatUtil.getString("am.webclient.apikey.noalarms.message"));
/* 4611 */         result.add(msg);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4617 */       ex.printStackTrace();
/* 4618 */       AMLog.debug("REST API : Server error");
/* 4619 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 4620 */       errorCode = "4128";
/* 4621 */       outputString = generateXML(request, response, message, errorCode);
/*      */     } finally {
/* 4623 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 4626 */     HashMap dataMap = new HashMap();
/* 4627 */     dataMap.put("response-code", responseCode);
/* 4628 */     dataMap.put("uri", uri);
/* 4629 */     dataMap.put("result", result);
/* 4630 */     dataMap.put("sortingParam", "DISPLAYNAME");
/* 4631 */     dataMap.put("sortingOrder", "desc");
/* 4632 */     dataMap.put("parentNode", "Alarms");
/* 4633 */     dataMap.put("nodeName", "Alarm");
/* 4634 */     outputString = getOutputAsString(dataMap, isJsonFormat);
/*      */     
/* 4636 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String unmanageAndRest(HttpServletRequest request, HttpServletResponse response, String resid)
/*      */     throws Exception
/*      */   {
/* 4642 */     String msg = "";
/* 4643 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 4644 */     MSreq.setContentType("text/xml; charset=UTF-8");
/* 4645 */     MSreq.addParameter("method", "unManageMonitors");
/* 4646 */     MSreq.addParameter("selectid", resid);
/* 4647 */     MSreq.addParameter("operation", "unManageAndReset");
/* 4648 */     MSreq.addParameter("isReset", "true");
/* 4649 */     MSreq.addParameter("isAPI", "true");
/* 4650 */     MSreq.addParameter("listview", "false");
/* 4651 */     MSreq.addParameter("type", "null");
/* 4652 */     MSreq.addParameter("viewmontype", "null");
/* 4653 */     MSreq.addParameter("group", "null");
/* 4654 */     MSreq.addParameter("showmanage", "null");
/* 4655 */     MSreq.addParameter("noOfRows", "null");
/* 4656 */     MSreq.addParameter("network", "null");
/*      */     
/* 4658 */     if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request)))
/*      */     {
/* 4660 */       AMLog.debug("REST API : Only ADMIN user can perform this action.");
/* 4661 */       msg = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.notadmin.message"), "4550");
/*      */     }
/*      */     try
/*      */     {
/* 4665 */       DataCollectionController dcc = new DataCollectionController();
/* 4666 */       dcc.unManageMonitors(null, null, MSreq, response);
/* 4667 */       msg = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.unmanagereset.succ.message"), "4000");
/*      */     }
/*      */     catch (Exception e) {
/* 4670 */       e.printStackTrace();
/* 4671 */       AMLog.debug("REST API : Server error");
/* 4672 */       msg = generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/* 4674 */     return null;
/*      */   }
/*      */   
/*      */   public static String getMonitorGroupAvailability(HttpServletRequest httpservletrequest)
/*      */   {
/* 4679 */     String resourceid = null;
/*      */     
/*      */ 
/* 4682 */     HashMap mgAvailabilityInfo = new HashMap();
/* 4683 */     AMLog.info("inside getMonitorGroupAvailability ");
/* 4684 */     resourceid = httpservletrequest.getParameter("ResourceId");
/* 4685 */     String type = httpservletrequest.getParameter("Type");
/* 4686 */     Boolean boolean1 = Boolean.valueOf(httpservletrequest.getParameter("ServiceAvailability"));
/* 4687 */     Boolean isCustomTime = Boolean.valueOf(httpservletrequest.getParameter("CustomTime"));
/* 4688 */     Boolean includeSubGroup = Boolean.valueOf(httpservletrequest.getParameter("IncludeSubGroup"));
/* 4689 */     String reportPeriod = httpservletrequest.getParameter("ReportPeriod");
/* 4690 */     if (reportPeriod == null) {
/* 4691 */       reportPeriod = "0";
/*      */     }
/* 4693 */     long startTime = 0L;
/* 4694 */     long endTime = 0L;
/* 4695 */     if (isCustomTime.booleanValue())
/*      */     {
/* 4697 */       reportPeriod = "-99";
/* 4698 */       startTime = Long.valueOf(httpservletrequest.getParameter("StartTime")).longValue();
/* 4699 */       endTime = Long.valueOf(httpservletrequest.getParameter("EndTime")).longValue();
/*      */     }
/*      */     
/* 4702 */     ArrayList<String> mgIdList = new ArrayList();
/* 4703 */     String qry = "select HAID from AM_HOLISTICAPPLICATION";
/* 4704 */     ResultSet resultset = null;
/* 4705 */     if (!includeSubGroup.booleanValue()) {
/* 4706 */       qry = qry + " where TYPE=0";
/*      */     }
/*      */     try {
/* 4709 */       resultset = AMConnectionPool.executeQueryStmt(qry);
/* 4710 */       while (resultset.next()) {
/* 4711 */         mgIdList.add(resultset.getString(1));
/*      */       }
/*      */     } catch (Exception e) {
/* 4714 */       e.printStackTrace();
/*      */     } finally {
/* 4716 */       AMConnectionPool.closeStatement(resultset);
/*      */     }
/*      */     
/* 4719 */     for (String mgID : mgIdList) {
/* 4720 */       if (isCustomTime.booleanValue()) {
/* 4721 */         mgAvailabilityInfo.put(mgID, ReportUtilities.getMonitorGroupAvailability(mgID, reportPeriod, startTime, endTime));
/*      */       } else {
/* 4723 */         mgAvailabilityInfo.put(mgID, ReportUtilities.getMonitorGroupAvailability(mgID, reportPeriod));
/*      */       }
/*      */     }
/*      */     
/* 4727 */     AMLog.audit(mgAvailabilityInfo.toString());
/* 4728 */     return convertToCSV(mgAvailabilityInfo, reportPeriod);
/*      */   }
/*      */   
/*      */   public static String convertToCSV(HashMap mgAvailabilityInfo, String reportPeriod)
/*      */   {
/* 4733 */     Iterator iterator = mgAvailabilityInfo.keySet().iterator();
/* 4734 */     StringBuffer stringbuffer = new StringBuffer();
/* 4735 */     stringbuffer.append(getAvailabilityHeader(reportPeriod)).append("\n\n");
/* 4736 */     stringbuffer.append("DisplayName,").append("Uptime %,").append("Downtime %").append("\n\n");
/*      */     
/* 4738 */     while (iterator.hasNext())
/*      */     {
/* 4740 */       String mgId = (String)iterator.next();
/* 4741 */       Properties properties = (Properties)mgAvailabilityInfo.get(mgId);
/* 4742 */       stringbuffer.append(DBUtil.getDisplaynameforResourceID(mgId)).append(",");
/* 4743 */       stringbuffer.append(properties.getProperty("available")).append("%").append(",");
/* 4744 */       stringbuffer.append(properties.getProperty("unavailable")).append("%").append("\n");
/*      */     }
/* 4746 */     return stringbuffer.toString();
/*      */   }
/*      */   
/*      */   public static String getAvailabilityHeader(String reportPeriod)
/*      */   {
/* 4751 */     String[] reportHeader = { "Today", "Last 7 Days", "Last 30 Days", "Yesterday", "NA", "Last 1 Year", "This Week", "This Month", "This Year", "This Quarter", "Last 6 Months", "Last Month", "Last Week" };
/* 4752 */     return reportHeader[Integer.parseInt(reportPeriod)] + " Availability Report";
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\URITree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */