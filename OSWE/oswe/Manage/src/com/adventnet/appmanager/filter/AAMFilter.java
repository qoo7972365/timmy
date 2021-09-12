/*      */ package com.adventnet.appmanager.filter;
/*      */ 
/*      */ import HTTPClient.CookieModule;
/*      */ import HTTPClient.HTTPConnection;
/*      */ import HTTPClient.NVPair;
/*      */ import HTTPClient.URI;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.json.JSONUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.servlets.APIServlet;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.util.ClientCommThreadLocal;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.CommonAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.URITree;
/*      */ import com.adventnet.nms.webclient.fault.alarm.AlarmOperationsUtility;
/*      */ import com.manageengine.apminsight.apm.client.util.ApmInsightProxyUtil;
/*      */ import com.manageengine.apminsight.client.util.APMInsightClientUtilAPI;
/*      */ import com.manageengine.apminsight.common.util.StringUtils;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URL;
/*      */ import java.net.URLDecoder;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.servlet.Filter;
/*      */ import javax.servlet.FilterChain;
/*      */ import javax.servlet.FilterConfig;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.ServletRequest;
/*      */ import javax.servlet.ServletResponse;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.httpclient.methods.PostMethod;
/*      */ import org.jasig.cas.client.authentication.AttributePrincipal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AAMFilter
/*      */   implements Filter
/*      */ {
/*   82 */   private FilterConfig config = null;
/*   83 */   private ServletContext moServletContext = null;
/*   84 */   private boolean needFilter = false;
/*   85 */   private static Hashtable<String, String> probesTickets = new Hashtable();
/*      */   
/*      */   public void init(FilterConfig config) throws ServletException {
/*   88 */     this.config = config;
/*   89 */     this.moServletContext = config.getServletContext();
/*   90 */     this.needFilter = (EnterpriseUtil.isAdminServer());
/*   91 */     AMLog.debug("AAMFilter : Filter to take effect ?  " + this.needFilter);
/*   92 */     CookieModule.setCookiePolicyHandler(new AAMFilterCookiePolicyHandler());
/*      */   }
/*      */   
/*      */   public void destroy() {
/*   96 */     this.config = null;
/*      */   }
/*      */   
/*      */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
/*      */   {
/*  101 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  104 */       HttpServletRequest hreq = (HttpServletRequest)request;
/*  105 */       Properties req_resp_prop = new Properties();
/*  106 */       req_resp_prop.put("resp_obj", response);
/*  107 */       ClientCommThreadLocal.clientCommTLObj.set(req_resp_prop);
/*      */       try {
/*  109 */         if (com.adventnet.appmanager.util.Constants.trackQuery)
/*      */         {
/*  111 */           Thread.currentThread().setName("http " + hreq.getRequestURI()); }
/*      */       } catch (Exception exc) {
/*  113 */         exc.printStackTrace(); }
/*  114 */       String uri = hreq.getRequestURI();
/*  115 */       String avoidFilter = null;
/*  116 */       avoidFilter = hreq.getParameter("avoidFilter");
/*  117 */       if ((!this.needFilter) || (!(request instanceof HttpServletRequest)) || ("true".equals(avoidFilter))) {
/*  118 */         chain.doFilter(request, response);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  123 */         boolean mysqlargs = false;
/*  124 */         int resIDInt = -1;
/*  125 */         String queryString = hreq.getQueryString();
/*  126 */         String resID = hreq.getParameter("resourceid");
/*  127 */         String mgId = hreq.getParameter("haid");
/*  128 */         String annotateurl = uri + "?" + queryString;
/*  129 */         String annotationFwd = hreq.getParameter("annotationFwd") != null ? hreq.getParameter("annotationFwd") : "";
/*  130 */         String query = null;
/*  131 */         String ipaddress = null;
/*  132 */         String instanceid = null;
/*  133 */         String APIKey = null;
/*  134 */         String apikey = request.getParameter("apikey");
/*  135 */         if ((uri.startsWith("/fault/AlarmOperations.do")) && ("executeActions".equalsIgnoreCase(hreq.getParameter("methodCall"))))
/*      */         {
/*  137 */           chain.doFilter(request, response);
/*      */ 
/*      */         }
/*  140 */         else if ((uri.startsWith("/showapplication.do")) && (queryString != null) && (queryString.contains("method=getUserDetails&username=")) && ("true".equals(request.getParameter("restApi"))) && ("true".equals(request.getParameter("adminServer")))) {
/*  141 */           AMLog.debug("inside admin authenticator check in Admin server AAMFilter.java");
/*      */           
/*  143 */           chain.doFilter(request, response);
/*      */         }
/*      */         else
/*      */         {
/*  147 */           if (resID == null)
/*      */           {
/*  149 */             resID = hreq.getParameter("resId");
/*  150 */             if (resID == null)
/*      */             {
/*  152 */               resID = hreq.getParameter("resids");
/*  153 */               if (resID == null)
/*      */               {
/*  155 */                 resID = hreq.getParameter("net_resourceid");
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  161 */           HashSet<Integer> resourceList = new HashSet();
/*  162 */           if ((resID == null) && (uri.startsWith("/DebugInfo.do"))) {
/*  163 */             HashMap detailMap = parseDebugInfoURLrequest(hreq, request);
/*  164 */             if (detailMap.containsKey("resID")) {
/*  165 */               resID = (String)detailMap.get("resID");
/*      */             }
/*  167 */             if (detailMap.containsKey("apikey")) {
/*  168 */               apikey = (String)detailMap.get("apikey");
/*      */             }
/*  170 */             resourceList = (HashSet)detailMap.get("resourceList");
/*  171 */             mysqlargs = ((Boolean)detailMap.get("mysqlargs")).booleanValue();
/*      */           }
/*      */           
/*  174 */           if (resourceList.size() > 1) {
/*      */             try {
/*  176 */               queryString = queryString + "&synUrls=true";
/*  177 */               for (Iterator i$ = resourceList.iterator(); i$.hasNext();) { int resid = ((Integer)i$.next()).intValue();
/*  178 */                 processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resid + "", hreq, request, response, chain);
/*      */               }
/*      */               return;
/*      */             } catch (Exception etd) {
/*  182 */               etd.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  187 */           if ((resID == null) && (uri.startsWith("/wsm.do"))) {
/*  188 */             instanceid = request.getParameter("operationId");
/*  189 */             if (instanceid != null) {
/*  190 */               query = "select PARENTID from AM_PARENTCHILDMAPPER where CHILDID=" + instanceid;
/*  191 */               rs = AMConnectionPool.executeQueryStmt(query);
/*  192 */               if (rs.next()) {
/*  193 */                 resID = rs.getString("PARENTID");
/*      */               }
/*  195 */               if (rs != null) {
/*  196 */                 AMConnectionPool.closeStatement(rs);
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  203 */           if (uri.startsWith("/apminsight/"))
/*      */           {
/*  205 */             resID = ApmInsightProxyUtil.checkForProxyExecution(request);
/*  206 */             if (resID == null)
/*      */             {
/*  208 */               chain.doFilter(request, response); return;
/*      */             }
/*      */             
/*  211 */             if (hreq.isUserInRole("OPERATOR"))
/*      */             {
/*  213 */               hreq.setAttribute("op_instances", APMInsightClientUtilAPI.getUserSpecficResouces(hreq));
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  218 */           if ((uri.startsWith("/showresource.do")) && (hreq.getParameter("type") != null) && (hreq.getParameter("type").equalsIgnoreCase("APM-Insight-Instance")) && (hreq.getParameter("method") != null) && (hreq.getParameter("method").equalsIgnoreCase("showDetails")))
/*      */           {
/*      */ 
/*      */ 
/*  222 */             HttpServletResponse hResp = (HttpServletResponse)response;
/*  223 */             hResp.sendRedirect(hResp.encodeRedirectURL("/apminsight/overview.do?method=showDetails&resourceId=" + resID + "&scope=INSTANCE&twID=H#I/O/" + resID + "/TW=H"));
/*      */           }
/*      */           else {
/*      */             try {
/*  227 */               if ((resID != null) && (uri.startsWith("/showHistoryData.do")) && (hreq.getParameter("method") != null) && (hreq.getParameter("method").equals("getData")) && (hreq.getParameter("period") != null) && (hreq.getParameter("period").equals("20"))) {
/*  228 */                 processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain); return;
/*      */               }
/*      */             }
/*      */             catch (Exception e) {
/*  232 */               e.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*  236 */             if (((resID == null) || ((!uri.startsWith("/showConfigurationData.do")) && (!uri.startsWith("/jsp/AddDockerContainers.jsp")) && (!uri.startsWith("/showresource.do")))) && ((!uri.startsWith("/manageConfMons.do")) || ((!"editMonitor".equalsIgnoreCase(hreq.getParameter("method"))) && (!"editPreConfMonitor".equalsIgnoreCase(hreq.getParameter("method"))))) && ((!uri.startsWith("/updateUrl.do")) || (!"editUrlMonitor".equalsIgnoreCase(hreq.getParameter("actionmethod")))) && ((!uri.startsWith("/updateScript.do")) || (!"editScript".equalsIgnoreCase(hreq.getParameter("method")))) && (!uri.startsWith("/manageMQ.do")) && (!uri.startsWith("/WTA.do")) && (!uri.startsWith("/manageEC2Instances.do")) && (!uri.startsWith("/manageVMInstances.do")) && (!uri.startsWith("/amazonActions.do")) && (!uri.startsWith("/jsp/DiskSpace.jsp")) && (!uri.startsWith("/Exchange.do")) && (!uri.startsWith("/manageHyperVMs.do")) && (!uri.startsWith("/showCustom.do")) && ((!uri.startsWith("/confActions.do")) || ((!hreq.getParameter("method").equals("getEventLogForWindowsCluster")) && (!hreq.getParameter("method").equals("dockerContainerActions")) && (!hreq.getParameter("method").equals("addContainers")) && (!hreq.getParameter("method").equals("getDockerContainers")))) && ((!uri.startsWith("/GlobalActions.do")) || (!queryString.startsWith("method=pollNow"))) && (!uri.startsWith("/jsp/PopUp_Graph.jsp")) && (!uri.startsWith("/jsp/ServicesInHost.jsp")) && (!uri.startsWith("/jsp/DiskIoStats.jsp")) && (!uri.startsWith("/assignOSNames.do")) && (!uri.startsWith("/DotNet.do")) && (!uri.startsWith("/Tomcat.do")) && (!uri.startsWith("/showWebAppServlets.do")) && (!uri.startsWith("/HostResource.do")) && (!uri.startsWith("/jsp/JBossEJBDetailsUserArea.jsp")) && (!uri.startsWith("/jsp/JBossJDBCDetailsUserArea.jsp")) && (!uri.startsWith("/jsp/JBossWebAppDetailsUserArea.jsp")) && (!uri.startsWith("/jsp/OracleTablespaceStatus.jsp")) && (!uri.startsWith("/jsp/OracleDataFiles.jsp")) && (!uri.startsWith("/jsp/OracleTablespaceDetails.jsp")) && (!uri.startsWith("/jsp/OracleSessionDetails.jsp")) && (!uri.startsWith("/jsp/OracleSessionWaits.jsp")) && (!uri.startsWith("/jsp/OracleRollbackSegment.jsp")) && (!uri.startsWith("/jsp/OracleBufferGets.jsp")) && (!uri.startsWith("/jsp/OracleDiskReads.jsp")) && (!uri.startsWith("/jsp/OracleProcess.jsp")) && (!uri.startsWith("/jsp/OracleScheduledJobs.jsp")) && (!uri.startsWith("/jsp/OracleASMDetails.jsp")) && (!uri.startsWith("/JavaRuntime.do")) && (!uri.startsWith("/Debug-Info/ThreadDump")) && (!uri.startsWith("/html/Data/JREMonitor/ThreadDump")) && (!uri.startsWith("/html/Data/JBOSSMonitor/ThreadDump")) && (!uri.startsWith("/MySql.do")) && (!uri.startsWith("/Debug-Info/MySqlProcess")) && (!uri.startsWith("/jsp/OracleLockDetails.jsp")) && (!uri.startsWith("/jsp/OracleSessionSummary.jsp")) && (!uri.startsWith("/sap.do")) && (!uri.startsWith("/as400.do")) && (!uri.startsWith("/MSSql.do")) && (!uri.startsWith("/MSSqlDispatch.do")) && (!uri.startsWith("/showMSSQLReports.do")) && (!uri.startsWith("/showDCComponentsNonConf.do")) && (!uri.startsWith("/jsp/RCA.jsp")) && (!uri.startsWith("/jsp/RCATop.jsp")) && (!uri.startsWith("/jsp/sgadetails.jsp")) && (!uri.startsWith("/showAllEventLogs.do")) && (!uri.startsWith("/HostResourceDispatch.do")) && (!uri.startsWith("/jsp/HostDiskDetails.jsp")) && (!uri.startsWith("/showapplication.do")) && (!uri.startsWith("/fault/AlarmOperations.do")) && (EnterpriseUtil.isAdminServer()) && (((!annotateurl.startsWith("/fault/AlarmDetails.do?method=editAnnotation")) && (!annotateurl.startsWith("/fault/AlarmDetails.do?method=setAnnotation")) && (!annotateurl.startsWith("/fault/AlarmDetails.do?method=deleteAnnotation")) && (EnterpriseUtil.isAdminServer())) || ((annotationFwd.equalsIgnoreCase("true")) && (!uri.startsWith("/apminsight/")) && (!uri.startsWith("/Ingres.do")) && (!uri.startsWith("/DebugInfo.do")) && (!uri.startsWith("/showNetworkResource.do")) && ((!uri.startsWith("/dataTableAction.do")) || ((!hreq.getParameter("method").equals("initXenAppEventLogTable")) && (!hreq.getParameter("method").equals("getEventLogForXenApp")))) && ((!uri.startsWith("/dataTableAction.do")) || ((!hreq.getParameter("method").equals("initDynamicsCRMEventLogTable")) && (!hreq.getParameter("method").equals("getEventLogForDynamicsCRM")))) && ((!uri.startsWith("/wsm.do")) || (!hreq.getParameter("method").equalsIgnoreCase("getSOAPInfo"))))))
/*      */             {
/*  238 */               if ((uri.startsWith("/ShowCAM.do")) || (uri.startsWith("/jsp/cam_graphs.jsp")))
/*      */               {
/*  240 */                 if ((hreq.getParameter("method") != null) && (hreq.getParameter("method").equals("showSingleGraphScreen")))
/*      */                 {
/*      */ 
/*  243 */                   resID = hreq.getParameter("attributeid");
/*      */                 }
/*  245 */                 else if (hreq.getParameter("camIDI") != null)
/*      */                 {
/*  247 */                   resID = hreq.getParameter("camIDI");
/*      */                 }
/*      */               }
/*  250 */               else if (uri.startsWith("/jsp/AddDockerContainers.jsp"))
/*      */               {
/*  252 */                 resID = hreq.getParameter("resourceid");
/*      */               }
/*  254 */               else if (uri.startsWith("/myFields.do"))
/*      */               {
/*  256 */                 if ((hreq.getParameter("method") != null) && (hreq.getParameter("method").equals("getUserAssociation"))) {
/*  257 */                   resID = hreq.getParameter("resourceid");
/*      */                 } else {
/*  259 */                   chain.doFilter(request, response);
/*      */                 }
/*      */               }
/*      */               else {
/*  263 */                 if (needFilterForREST(uri))
/*      */                 {
/*  265 */                   resID = hreq.getParameter("resourceid");
/*  266 */                   if ((resID == null) && ((uri.contains("ManageMonitor")) || (uri.contains("UnmanageMonitor")) || (uri.contains("UnmanageAndResetMonitor")))) {
/*  267 */                     String ipAddress = hreq.getParameter("IP");
/*  268 */                     String port = hreq.getParameter("PORTNO");
/*  269 */                     String type = hreq.getParameter("TYPE");
/*  270 */                     resID = DBUtil.getResourceidForIP(ipAddress, port, type);
/*  271 */                   } else if ((resID == null) && (uri.contains("AddMonitor")) && (!uri.contains("AddMonitorGroup"))) {
/*  272 */                     ArrayList<String> chkOut = new ArrayList();
/*  273 */                     HashMap<String, String> map = new HashMap();
/*  274 */                     if (CommonAPIUtil.isDelegatedAdminAPIRequest(hreq)) {
/*  275 */                       if ((request.getParameter("groupID") != null) || (request.getParameter("haid") != null)) {
/*  276 */                         boolean checkHaid = CommonAPIUtil.checkResourceidforDelegatedAdmin(hreq, request.getParameter("groupID") != null ? request.getParameter("groupID") : request.getParameter("haid"));
/*  277 */                         if (checkHaid) {
/*  278 */                           chain.doFilter(request, response); return;
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/*  284 */                         HashMap monitorAdderStatus = new HashMap();
/*  285 */                         String outputFormat = "xml";
/*  286 */                         monitorAdderStatus.put("response-code", "4580");
/*  287 */                         monitorAdderStatus.put("uri", "/AppManager/xml/AddMonitor");
/*  288 */                         monitorAdderStatus.put("message", FormatUtil.getString("am.webclient.api.addmon.delegatedAdmin.mgmandatory.check"));
/*  289 */                         monitorAdderStatus.put("nodeName", "message");
/*  290 */                         monitorAdderStatus.put("needTextNode", "false");
/*  291 */                         boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  292 */                         outputFormat = isJsonFormat ? "json" : outputFormat;
/*  293 */                         String appendxmlToresponse = URITree.generateResp(monitorAdderStatus, isJsonFormat);
/*  294 */                         HttpServletResponse hresponse = (HttpServletResponse)response;
/*  295 */                         clientOutput(hresponse, appendxmlToresponse, outputFormat); return;
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/*  300 */                     for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */                     {
/*  302 */                       String param = (String)e.nextElement();
/*  303 */                       map.put(param, request.getParameter(param));
/*      */                     }
/*  305 */                     chkOut = DBUtil.getResourceidanddspNameForIP(map);
/*  306 */                     if (!chkOut.isEmpty()) {
/*  307 */                       resID = (String)chkOut.get(0);
/*      */ 
/*      */ 
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */                   }
/*  316 */                   else if ((uri.contains("AddSubGroup")) || (uri.contains("AssociateMonitortoMG")) || (uri.contains("AddMonitorGroup")) || (uri.contains("SyncMonitors"))) {
/*  317 */                     resID = "1";
/*  318 */                   } else if ((resID == null) && ((uri.contains("group/delete")) || (uri.contains("subgroup/delete"))))
/*      */                   {
/*  320 */                     if ((hreq.getParameter("haid") != null) && (!hreq.getParameter("haid").equals("")))
/*  321 */                       resID = hreq.getParameter("haid");
/*  322 */                     if ((hreq.getParameter("name") != null) && (!hreq.getParameter("name").equals(""))) {
/*  323 */                       resID = DBUtil.getHaid(hreq.getParameter("name"), uri);
/*      */                     }
/*      */                   }
/*  326 */                   else if (uri.contains("removemonitor/group")) {
/*  327 */                     resID = "";
/*  328 */                     if ((hreq.getParameter("haid") != null) && (!hreq.getParameter("haid").equals(""))) {
/*  329 */                       resID = hreq.getParameter("haid");
/*      */                     }
/*  331 */                     else if ((hreq.getParameter("mgname") != null) && (!hreq.getParameter("mgname").equals(""))) {
/*  332 */                       resID = DBUtil.getHaid(hreq.getParameter("mgname"), uri);
/*      */                     }
/*  334 */                     if ((resID == null) || (resID.equals(""))) {
/*  335 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                     
/*  338 */                     if ((!CommonAPIUtil.isAdminRole(apikey)) && (!CommonAPIUtil.isDelegatedAdmin(apikey))) {
/*  339 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                   }
/*  342 */                   else if (uri.contains("group/move")) {
/*  343 */                     if ((hreq.getParameter("tohaid") != null) && (!hreq.getParameter("tohaid").equals(""))) {
/*  344 */                       resID = hreq.getParameter("tohaid");
/*      */                     }
/*  346 */                     if ((resID == null) || (resID.equals(""))) {
/*  347 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                     
/*  350 */                     if ((!CommonAPIUtil.isAdminRole(apikey)) && (!CommonAPIUtil.isDelegatedAdmin(apikey))) {
/*  351 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                   }
/*  354 */                   else if (uri.contains("group/edit")) {
/*  355 */                     if ((hreq.getParameter("haid") != null) && (!hreq.getParameter("haid").equals(""))) {
/*  356 */                       resID = hreq.getParameter("haid");
/*      */                     }
/*  358 */                     if ((resID == null) || (resID.equals(""))) {
/*  359 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                     
/*  362 */                     if ((!CommonAPIUtil.isAdminRole(apikey)) && (!CommonAPIUtil.isDelegatedAdmin(apikey))) {
/*  363 */                       chain.doFilter(request, response);
/*      */                     }
/*      */                   }
/*  366 */                   apikey = hreq.getParameter("apikey");
/*  367 */                   if ((resID == null) && (uri.contains("getRBMPlaybackStatus")))
/*      */                   {
/*  369 */                     processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain); return;
/*      */                   }
/*      */                   try
/*      */                   {
/*  373 */                     if (resID != null) {
/*  374 */                       int resourceValue = Integer.parseInt(resID);
/*  375 */                       if ((resourceValue > com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && 
/*  376 */                         (CommonAPIUtil.checkResourceidforDelegatedAdmin(hreq, resID))) {
/*  377 */                         chain.doFilter(request, response); return;
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   catch (Exception num)
/*      */                   {
/*  383 */                     num.printStackTrace();
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*  388 */                 chain.doFilter(request, response); return;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  393 */             if ((uri.startsWith("/showapplication.do")) && (mgId != null))
/*      */             {
/*  395 */               String mgTypeQuery = "select GROUPTYPE from AM_HOLISTICAPPLICATION where HAID=" + mgId;
/*  396 */               ResultSet rsMG = null;
/*      */               try
/*      */               {
/*  399 */                 rsMG = AMConnectionPool.executeQueryStmt(mgTypeQuery);
/*  400 */                 for (; rsMG.next(); return) {
/*      */                   label3309:
/*  402 */                   resID = mgId;
/*  403 */                   if ((rsMG.getInt("GROUPTYPE") == 1012) || (rsMG.getInt("GROUPTYPE") == 1010) || (rsMG.getInt("GROUPTYPE") == 1009) || (rsMG.getInt("GROUPTYPE") == 3) || (rsMG.getInt("GROUPTYPE") == 1013))
/*      */                     break label3309;
/*  405 */                   chain.doFilter(request, response);
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/*  412 */                 ee.printStackTrace();
/*      */               }
/*      */               finally {}
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  420 */             if ((uri.startsWith("/fault/AlarmOperations.do")) && (EnterpriseUtil.isAdminServer()) && (!"executeActions".equalsIgnoreCase(hreq.getParameter("methodCall"))))
/*      */             {
/*      */               try
/*      */               {
/*  424 */                 String entity = hreq.getParameter("selectedEntity");
/*  425 */                 String probresId = segmentEntity(entity).get("probresId").toString();
/*  426 */                 String centresId = segmentEntity(entity).get("centresId").toString();
/*  427 */                 if ((probresId != null) && (!probresId.equals("")))
/*      */                 {
/*  429 */                   queryString = reformURL(queryString, probresId);
/*  430 */                   resID = probresId.substring(0, probresId.indexOf("_"));
/*  431 */                   processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain);
/*      */                 }
/*      */                 
/*  434 */                 if ((centresId != null) && (!centresId.equals("")))
/*      */                 {
/*  436 */                   queryString = reformURL(queryString, centresId);
/*  437 */                   resID = centresId.substring(0, centresId.indexOf("_"));
/*  438 */                   processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain);
/*      */                 }
/*      */                 return;
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*  444 */                 e.printStackTrace();
/*      */               }
/*      */               finally {}
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  451 */             if ((uri.startsWith("/fault/AlarmDetails.do")) && (EnterpriseUtil.isAdminServer()))
/*      */             {
/*      */               try
/*      */               {
/*  455 */                 String method = hreq.getParameter("method") != null ? hreq.getParameter("method") : "false";
/*  456 */                 if ((method.equalsIgnoreCase("editAnnotation")) || (method.equalsIgnoreCase("setAnnotation")) || (method.equalsIgnoreCase("deleteAnnotation")))
/*      */                 {
/*      */ 
/*  459 */                   String entity = hreq.getParameter("entity");
/*  460 */                   String probresId = segmentEntity(entity).get("probresId").toString();
/*  461 */                   String centresId = segmentEntity(entity).get("centresId").toString();
/*  462 */                   if ((probresId != null) && (!probresId.equals("")))
/*      */                   {
/*  464 */                     queryString = reformURL(queryString, probresId);
/*  465 */                     resID = probresId.substring(0, probresId.indexOf("_"));
/*  466 */                     processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain);
/*      */                   }
/*      */                   
/*  469 */                   if ((centresId != null) && (!centresId.equals("")))
/*      */                   {
/*  471 */                     queryString = reformURL(queryString, centresId);
/*  472 */                     resID = centresId.substring(0, centresId.indexOf("_"));
/*  473 */                     processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain);
/*      */                   }
/*      */                   
/*      */                   return;
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*  481 */                 e.printStackTrace();
/*      */               }
/*      */               finally {}
/*      */             }
/*      */             
/*      */ 
/*  487 */             processRequest(mysqlargs, APIKey, apikey, uri, queryString, mgId, resID, hreq, request, response, chain);
/*      */           }
/*      */         }
/*      */       }
/*  491 */     } catch (Exception e) { e.printStackTrace();
/*  492 */       AMLog.debug("AAMFilter : serverType " + e);
/*      */     }
/*      */     finally {
/*  495 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties segmentEntity(String entity)
/*      */   {
/*  504 */     StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*  505 */     StringBuffer centresId = new StringBuffer();
/*  506 */     StringBuffer probresId = new StringBuffer();
/*      */     
/*  508 */     Properties segmentedEntity = new Properties();
/*  509 */     while (tokenize.hasMoreTokens())
/*      */     {
/*  511 */       String tempEntity = tokenize.nextToken();
/*  512 */       if (Integer.parseInt(tempEntity.substring(0, tempEntity.indexOf("_"))) <= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/*  513 */         if (centresId.length() > 0)
/*      */         {
/*  515 */           centresId.append(",");
/*      */         }
/*  517 */         centresId.append(tempEntity);
/*      */       }
/*      */       else {
/*  520 */         if (probresId.length() > 0)
/*      */         {
/*  522 */           probresId.append(",");
/*      */         }
/*  524 */         probresId.append(tempEntity);
/*      */       }
/*      */     }
/*  527 */     segmentedEntity.put("probresId", probresId);
/*  528 */     segmentedEntity.put("centresId", centresId);
/*  529 */     return segmentedEntity;
/*      */   }
/*      */   
/*      */   public String reformURL(String queryString, String resId)
/*      */   {
/*  534 */     if (queryString.indexOf("&selectedEntity") != -1)
/*      */     {
/*  536 */       int entityStartIndex = queryString.indexOf("&selectedEntity");
/*  537 */       String temp1 = queryString.substring(entityStartIndex + 1);
/*  538 */       if ((temp1 != null) && (!temp1.trim().equals("")))
/*      */       {
/*  540 */         String test = temp1.substring(temp1.indexOf("=") + 1, temp1.indexOf("&"));
/*  541 */         queryString = queryString.replace(test, resId.toString());
/*      */       }
/*      */     }
/*  544 */     return queryString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void processRequest(boolean mysqlargs, String apiKey, String apikey, String uri, String queryString, String mgId, String resID, HttpServletRequest hreq, ServletRequest request, ServletResponse response, FilterChain chain)
/*      */   {
/*  551 */     int resIDInt = -1;
/*  552 */     ResultSet rs = null;
/*  553 */     String query = null;
/*  554 */     String outputFormat = "xml";
/*      */     try {
/*  556 */       if (resID != null)
/*      */       {
/*  558 */         if (resID.indexOf(",") != -1)
/*      */         {
/*  560 */           StringTokenizer st = new StringTokenizer(resID, ",");
/*  561 */           if (st.hasMoreTokens())
/*      */           {
/*  563 */             resID = st.nextToken();
/*      */           }
/*      */         }
/*      */         
/*  567 */         resIDInt = Integer.parseInt(resID);
/*      */         
/*  569 */         if (resIDInt <= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/*  570 */           chain.doFilter(request, response); return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  576 */       List listOfManagedServers = CommDBUtil.getDistributedServers();
/*      */       
/*  578 */       if ((listOfManagedServers == null) || (listOfManagedServers.isEmpty()))
/*      */       {
/*  580 */         chain.doFilter(request, response);
/*      */       }
/*      */       else {
/*  583 */         String managedServerId = request.getParameter("serverid");
/*  584 */         HashMap<String, String> addProp = new HashMap();
/*  585 */         String state = null;
/*  586 */         if (((managedServerId != null) && (!managedServerId.equals(""))) || (resIDInt != -1))
/*      */         {
/*  588 */           if (resIDInt != -1)
/*      */           {
/*  590 */             managedServerId = String.valueOf(EnterpriseUtil.getManagedServerIndex(resIDInt));
/*      */           }
/*      */         }
/*  593 */         for (int i = 0; i < listOfManagedServers.size(); i++)
/*      */         {
/*  595 */           HashMap<String, String> masInfo = (HashMap)listOfManagedServers.get(i);
/*  596 */           String tmpMasID = (String)masInfo.get("SERVERID");
/*  597 */           if (tmpMasID.equals(managedServerId))
/*      */           {
/*  599 */             state = (String)masInfo.get("STATE");
/*  600 */             break;
/*      */           }
/*      */         }
/*      */         
/*  604 */         if ((state != null) && (!"1000".equals(state)))
/*      */         {
/*  606 */           String hostname = request.getServerName();
/*  607 */           String protocol = request.getScheme();
/*  608 */           int port = request.getServerPort();
/*  609 */           String url = "/jsp/MASError404.jsp";
/*  610 */           HttpServletResponse hresp = (HttpServletResponse)response;
/*  611 */           hresp.setContentType("text/html; charset=UTF-8");
/*  612 */           if (protocol.equalsIgnoreCase("http"))
/*      */           {
/*  614 */             hresp.sendRedirect(hresp.encodeRedirectURL("http://" + hostname + ":" + port + url));
/*      */           }
/*  616 */           else if (protocol.equalsIgnoreCase("https"))
/*      */           {
/*  618 */             hresp.sendRedirect(hresp.encodeRedirectURL("https://" + hostname + ":" + port + url));
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  623 */         else if ((uri.contains("AddMonitor")) && (request.getParameter("serverid") == null)) {
/*  624 */           HashMap<String, String> validationResults = APIServlet.isValidAPIKeyinRequest(apikey);
/*  625 */           AMLog.debug("AAMFilter.processRequest():validationResults::::" + validationResults);
/*  626 */           if ((validationResults != null) && (validationResults.size() > 0)) {
/*  627 */             HashMap monitorAdderStatus = new HashMap();
/*  628 */             monitorAdderStatus.put("response-code", validationResults.get("errorCode"));
/*  629 */             monitorAdderStatus.put("uri", "/AppManager/xml/AddMonitor");
/*  630 */             monitorAdderStatus.put("message", FormatUtil.getString((String)validationResults.get("errorDesc"), new String[] { apikey }));
/*  631 */             monitorAdderStatus.put("nodeName", "message");
/*  632 */             monitorAdderStatus.put("needTextNode", "false");
/*  633 */             boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  634 */             outputFormat = isJsonFormat ? "json" : outputFormat;
/*  635 */             String appendxmlToresponse = URITree.generateResp(monitorAdderStatus, isJsonFormat);
/*  636 */             HttpServletResponse hresponse = (HttpServletResponse)response;
/*  637 */             clientOutput(hresponse, appendxmlToresponse, outputFormat); return;
/*      */           }
/*      */           
/*      */ 
/*  641 */           Properties argsprops = new Properties();
/*  642 */           String masSelectType = "0";
/*  643 */           for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */           {
/*  645 */             String param = (String)e.nextElement();
/*  646 */             if (!argsprops.containsKey(param))
/*      */             {
/*  648 */               argsprops.setProperty(param, request.getParameter(param));
/*  649 */               if (param.equals("ManagedServerID"))
/*      */               {
/*  651 */                 argsprops.setProperty("selectedServer", request.getParameter(param));
/*  652 */                 masSelectType = "3";
/*      */               }
/*  654 */               else if (param.equals("ManagedServerGroupName"))
/*      */               {
/*  656 */                 argsprops.setProperty("masGroupName", request.getParameter(param));
/*  657 */                 masSelectType = "1";
/*      */               }
/*  659 */               else if (param.equals("type"))
/*      */               {
/*  661 */                 argsprops.setProperty("monitorType", request.getParameter(param));
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*  666 */           argsprops.setProperty("masSelectType", masSelectType);
/*  667 */           argsprops.setProperty("DuplicationCheckDone", "false");
/*  668 */           argsprops.setProperty("fromRESTAPI", "true");
/*  669 */           HashMap monitorAdderStatus = AAMMonitorAdder.addMonitor(argsprops);
/*  670 */           boolean isMonitorAdded = Boolean.valueOf((String)monitorAdderStatus.get("addStatus")).booleanValue();
/*  671 */           monitorAdderStatus.put("response-code", (String)monitorAdderStatus.get("response-code"));
/*  672 */           monitorAdderStatus.put("uri", "/AppManager/xml/AddMonitor");
/*  673 */           monitorAdderStatus.put("message", (String)monitorAdderStatus.get("message"));
/*  674 */           monitorAdderStatus.put("nodeName", "message");
/*  675 */           monitorAdderStatus.put("needTextNode", "false");
/*  676 */           if (monitorAdderStatus.get("existingResid") != null) {
/*  677 */             monitorAdderStatus.put("resourceid", (String)monitorAdderStatus.get("existingResid"));
/*      */           }
/*  679 */           if (isMonitorAdded) {
/*  680 */             monitorAdderStatus.put("TargetMasDispName", (String)monitorAdderStatus.get("managedServerDispName"));
/*      */           }
/*  682 */           boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  683 */           outputFormat = isJsonFormat ? "json" : outputFormat;
/*  684 */           String appendxmlToresponse = URITree.generateResp(monitorAdderStatus, isJsonFormat);
/*  685 */           HttpServletResponse hresponse = (HttpServletResponse)response;
/*  686 */           clientOutput(hresponse, appendxmlToresponse, outputFormat);
/*      */         }
/*      */         else
/*      */         {
/*  690 */           List listOfMas = CommDBUtil.getDistributedServers();
/*      */           
/*  692 */           if ((listOfMas == null) || (listOfMas.isEmpty()))
/*      */           {
/*  694 */             chain.doFilter(request, response); return;
/*      */           }
/*      */           
/*  697 */           EnterpriseUtil.initializeMonitorCountCache(listOfMas);
/*  698 */           String masId = request.getParameter("serverid");
/*  699 */           HashMap<String, String> additionalProp = new HashMap();
/*  700 */           if (((masId != null) && (!masId.equals(""))) || (resIDInt != -1))
/*      */           {
/*  702 */             if (resIDInt != -1)
/*      */             {
/*  704 */               masId = String.valueOf(EnterpriseUtil.getManagedServerIndex(resIDInt));
/*      */             }
/*  706 */             additionalProp.put("ManagedServerID", masId);
/*      */           }
/*  708 */           Properties masProp = EnterpriseUtil.getManagedServerProperties(additionalProp, listOfMas);
/*      */           
/*  710 */           if ((masProp == null) || (masProp.isEmpty()))
/*      */           {
/*  712 */             chain.doFilter(request, response); return;
/*      */           }
/*      */           
/*  715 */           String host = masProp.getProperty("MAS_HOST");
/*  716 */           String port = masProp.getProperty("MAS_PORT");
/*  717 */           String sslport = masProp.getProperty("MAS_SSLPORT");
/*  718 */           apiKey = masProp.getProperty("MAS_APIKEY");
/*      */           
/*  720 */           HttpServletResponse hresponse = (HttpServletResponse)response;
/*  721 */           hresponse.setContentType("text/html; charset=UTF-8");
/*      */           
/*      */ 
/*      */ 
/*  725 */           String url = uri + "?";
/*  726 */           StringBuffer sbf = new StringBuffer();
/*      */           
/*      */ 
/*      */ 
/*  730 */           if ((APIServlet.validateAPIKey(apikey)) && (queryString.indexOf("apikey") >= 0))
/*      */           {
/*  732 */             query = null;
/*      */             
/*  734 */             StringTokenizer st = new StringTokenizer(queryString, "&");
/*  735 */             while (st.hasMoreTokens())
/*      */             {
/*  737 */               String token = st.nextToken();
/*  738 */               if (token.indexOf("apikey") >= 0) {
/*  739 */                 sbf.append("apikey=").append(apiKey).append("&");
/*      */               } else {
/*  741 */                 sbf.append(token).append("&");
/*      */               }
/*      */             }
/*  744 */             url = url + sbf.substring(0, sbf.length() - 1).toString();
/*      */           }
/*      */           else
/*      */           {
/*  748 */             url = url + queryString;
/*      */           }
/*  750 */           if (mysqlargs) {
/*  751 */             url = url + "&args=" + request.getParameter("args");
/*      */           }
/*  753 */           AMLog.info("REST API : modified rest api request url ::" + url);
/*  754 */           if ((uri.startsWith("/fault/AlarmOperations.do")) && (EnterpriseUtil.isAdminServer()))
/*      */           {
/*  756 */             if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  757 */               url = url + "&uName=" + "systemadmin_enterprise" + "&actionFromadmin=true";
/*  758 */               url = url + "&adminUser=" + ((HttpServletRequest)request).getRemoteUser() + "-Central&requestFromAdmin=true";
/*      */             }
/*      */             else
/*      */             {
/*  762 */               url = url + "&adminUser=" + ((HttpServletRequest)request).getRemoteUser() + "-AdminServer&requestFromAdmin=true";
/*      */             }
/*      */             
/*  765 */             AMLog.debug("urlToFetch Managed Server Data AlarmOperations :::: " + url);
/*      */           }
/*      */           
/*  768 */           String redirect = hreq.getParameter("redirect") != null ? hreq.getParameter("redirect") : "";
/*  769 */           String method = hreq.getParameter("method") != null ? hreq.getParameter("method") : "false";
/*  770 */           if ((uri.startsWith("/fault/AlarmDetails.do")) && (EnterpriseUtil.isAdminServer())) {
/*  771 */             if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  772 */               url = url + "&actionFromCentral=true&uName=" + ((HttpServletRequest)request).getRemoteUser() + "-Central";
/*      */             } else {
/*  774 */               url = url + "&actionFromCentral=true&uName=" + ((HttpServletRequest)request).getRemoteUser() + "-AdminServer";
/*      */             }
/*      */             
/*  777 */             if ((method.equalsIgnoreCase("editAnnotation")) || (method.equalsIgnoreCase("setAnnotation")))
/*      */             {
/*  779 */               String annotationText = hreq.getParameter("text");
/*  780 */               String modTime = hreq.getParameter("time");
/*  781 */               url = url + "&time=" + modTime + "&text=" + URLEncoder.encode(annotationText);
/*      */             }
/*  783 */             if (method.equalsIgnoreCase("deleteAnnotation"))
/*      */             {
/*  785 */               String[] modTime = hreq.getParameterValues("checkbox");
/*  786 */               if (modTime != null)
/*      */               {
/*  788 */                 String modTimeString = "";
/*  789 */                 for (int i = 0; i < modTime.length; i++)
/*      */                 {
/*  791 */                   modTimeString = modTimeString + modTime[i];
/*  792 */                   if (i != modTime.length - 1)
/*      */                   {
/*  794 */                     modTimeString = modTimeString + ",";
/*      */                   }
/*      */                 }
/*  797 */                 url = url + "&modTimeString=" + modTimeString + "&redirect=" + URLEncoder.encode(redirect);
/*      */               }
/*      */             }
/*  800 */             AMLog.debug("urlToFetch Managed Server Data AlarmDetails :::: " + url);
/*      */           }
/*      */           
/*  803 */           String type = "null";String group = "";
/*  804 */           if (hreq.getParameter("type") != null) {
/*  805 */             type = hreq.getParameter("type");
/*      */           }
/*      */           
/*  808 */           if (hreq.getParameter("group") != null) {
/*  809 */             group = hreq.getParameter("group");
/*      */           }
/*      */           else
/*      */           {
/*  813 */             group = hreq.getParameter("viewType");
/*      */           }
/*      */           
/*  816 */           boolean status = false;
/*  817 */           if ((!com.adventnet.appmanager.util.Constants.isIt360) && ("true".equals(hreq.getParameter("aam_jump"))))
/*      */           {
/*  819 */             if (("true".equals(hreq.getParameter("useHTTP"))) && (!AMAutomaticPortChanger.isSsoEnabled()))
/*      */             {
/*  821 */               status = forwardRequest(resID, url, host, port, hresponse, true);
/*      */             }
/*      */             else
/*      */             {
/*  825 */               status = forwardRequest(resID, url, host, sslport, hresponse, false);
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/*  830 */             status = proxyRequest(resID, url, host, port, sslport, hresponse, hreq, mgId);
/*      */           }
/*      */           
/*  833 */           if (!status)
/*      */           {
/*      */ 
/*  836 */             if (url.startsWith("/apminsight/"))
/*      */             {
/*  838 */               hreq.setAttribute("managedServersForm", ApmInsightProxyUtil.fillManagedServersSelectionComboBox(hreq));
/*  839 */               hreq.setAttribute("managedServerDownMessage", FormatUtil.getString("apminsight.managedserver.downmsg", new Object[] { host, sslport }));
/*  840 */               chain.doFilter(hreq, hresponse);
/*      */             }
/*  842 */             writeServerDownMessage(url, host, sslport, hresponse, type, group, -1);
/*      */           }
/*      */         }
/*      */       }
/*  846 */     } catch (Exception e) { e.printStackTrace();
/*      */     }
/*      */     finally {
/*  849 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public FilterConfig getFilterConfig()
/*      */   {
/*  857 */     return this.config;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setFilterConfig(FilterConfig cfg)
/*      */   {
/*      */     try
/*      */     {
/*  865 */       init(cfg);
/*      */     }
/*      */     catch (ServletException ee) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getcontext(String uri)
/*      */   {
/*  874 */     StringTokenizer st = new StringTokenizer(uri, "/");
/*      */     String to;
/*  876 */     if (st.hasMoreTokens()) {
/*  877 */       String to = st.nextToken();
/*  878 */       if (!st.hasMoreTokens())
/*      */       {
/*      */ 
/*  881 */         if (uri.indexOf("/", 1) <= 2)
/*      */         {
/*      */ 
/*  884 */           to = "/";
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  889 */       to = "/";
/*      */     }
/*  891 */     return to;
/*      */   }
/*      */   
/*      */   public static String findAndReplaceAll(String str, String find, String replace)
/*      */   {
/*  896 */     StringBuffer des = new StringBuffer("");
/*  897 */     if ((str != null) && (str.trim().length() > 0)) {
/*  898 */       while (str.indexOf(find) != -1)
/*      */       {
/*  900 */         des.append(str.substring(0, str.indexOf(find)));
/*  901 */         des.append(replace);
/*  902 */         str = str.substring(str.indexOf(find) + find.length());
/*      */       }
/*  904 */       des.append(str);
/*      */     }
/*  906 */     return des.toString();
/*      */   }
/*      */   
/*      */   public static boolean writeServerDownMessage(String url, String mas_host, String mas_port, HttpServletResponse hresponse, String type, String group, int ID) throws Exception
/*      */   {
/*  911 */     if (!type.equals("null"))
/*      */     {
/*  913 */       if (type.equals("SUN"))
/*      */       {
/*  915 */         type = "Sun Solaris";
/*      */       }
/*  917 */       else if (type.startsWith("Windows"))
/*      */       {
/*  919 */         type = "Windows";
/*      */       }
/*  921 */       else if (type.equals("Node"))
/*      */       {
/*  923 */         type = "Unknown";
/*      */       }
/*      */     }
/*  926 */     String message1 = "The server " + mas_host + ":" + mas_port + " may be down or the request to the same may have timed out.";
/*      */     
/*  928 */     if ((group != null) && (group.equals("showIconsView")))
/*      */     {
/*  930 */       hresponse.sendRedirect(hresponse.encodeRedirectURL("/showresource.do?method=showIconsView&mas_host=" + mas_host + "&mas_port=" + mas_port + "&serverId=" + ID));
/*      */     }
/*  932 */     else if ((group != null) && (group.equals("showDetailsView")))
/*      */     {
/*  934 */       hresponse.sendRedirect(hresponse.encodeRedirectURL("/showresource.do?method=showDetailsView&mas_host=" + mas_host + "&mas_port=" + mas_port + "&serverId=" + ID));
/*      */     }
/*  936 */     else if ((group != null) && ((group.equals("All")) || (group.equals("showResourceTypesAll"))))
/*      */     {
/*  938 */       hresponse.sendRedirect(hresponse.encodeRedirectURL("/showresource.do?method=showResourceTypesAll&group=All&mas_host=" + mas_host + "&mas_port=" + mas_port + "&serverId=" + ID));
/*      */ 
/*      */ 
/*      */     }
/*  942 */     else if ((!url.contains("UnmanageMonitor")) && (!url.contains("ManageMonitor")) && (!url.contains("UnmanageAndResetMonitor"))) {
/*  943 */       hresponse.sendRedirect(hresponse.encodeRedirectURL("/showresource.do?method=showResourceTypesAll&group=All&mas_host=" + mas_host + "&mas_port=" + mas_port + "&serverId=" + ID));
/*      */     }
/*      */     
/*  946 */     AMLog.debug(message1);
/*  947 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean forwardRequest(String resID, String url, String mas_host, String mas_port, HttpServletResponse hresponse, boolean useHttp) throws Exception
/*      */   {
/*      */     try {
/*  953 */       hresponse.addHeader(com.adventnet.appmanager.server.framework.comm.Constants.HEADER_KEY, com.adventnet.appmanager.server.framework.comm.Constants.HEADER_VAL);
/*  954 */       if (useHttp)
/*      */       {
/*  956 */         if (mas_port.equals("80")) {
/*  957 */           hresponse.sendRedirect(hresponse.encodeRedirectURL("http://" + mas_host + url));
/*      */         }
/*      */         else {
/*  960 */           hresponse.sendRedirect(hresponse.encodeRedirectURL("http://" + mas_host + ":" + mas_port + url));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  965 */         AMLog.debug("Forward Request Host " + mas_host + " Port " + mas_port + " Url " + url);
/*  966 */         if (mas_port.equals("443")) {
/*  967 */           hresponse.sendRedirect(hresponse.encodeRedirectURL("https://" + mas_host + url));
/*      */         }
/*      */         else {
/*  970 */           hresponse.sendRedirect(hresponse.encodeRedirectURL("https://" + mas_host + ":" + mas_port + url));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  976 */       e.printStackTrace();
/*  977 */       AMLog.debug("AAMValve : " + e);
/*  978 */       return false;
/*      */     }
/*  980 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean proxyRequest(String resID, String url, String mas_host, String port, String mas_port, HttpServletResponse hresponse, HttpServletRequest hreq, String mgId) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  987 */       boolean canDeleteMas_ImageDirFiles = false;
/*  988 */       String uriToTest = hreq.getRequestURI();
/*  989 */       if ((uriToTest.startsWith("/showresource.do")) || (uriToTest.startsWith("/apminsight/")))
/*      */       {
/*  991 */         canDeleteMas_ImageDirFiles = true;
/*      */       }
/*      */       
/*      */ 
/*  995 */       HTTPConnection con = null;
/*  996 */       HttpSession session = hreq.getSession();
/*  997 */       boolean useProxyForMASImage = true;
/*  998 */       ResultSet set = null;
/*      */       try
/*      */       {
/* 1001 */         String passwordSelectQuery = "select VALUE from AM_GLOBALCONFIG where NAME='useProxyForMASImage'";
/* 1002 */         set = AMConnectionPool.executeQueryStmt(passwordSelectQuery);
/* 1003 */         while (set.next())
/*      */         {
/* 1005 */           useProxyForMASImage = Boolean.valueOf(set.getString(1)).booleanValue();
/* 1006 */           EnterpriseUtil.setUseProxyForMASImage(useProxyForMASImage);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1011 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1015 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       
/* 1018 */       String nmshome = System.getProperty("webnms.rootdir");
/* 1019 */       File dir = null;
/*      */       
/* 1021 */       String loginName = "admin";String remoteHost = "remotehost";
/* 1022 */       if (hreq.getRemoteUser() != null)
/*      */       {
/* 1024 */         loginName = hreq.getRemoteUser();
/*      */       }
/*      */       
/* 1027 */       if (hreq.getRemoteHost() != null)
/*      */       {
/* 1029 */         remoteHost = hreq.getRemoteHost();
/*      */       }
/*      */       
/* 1032 */       if (remoteHost.indexOf(":") != -1)
/*      */       {
/* 1034 */         remoteHost = remoteHost.replaceAll(":", "-");
/*      */       }
/*      */       
/* 1037 */       if (remoteHost.indexOf("%") != -1)
/*      */       {
/* 1039 */         remoteHost = remoteHost.replaceAll("%", "-");
/*      */       }
/*      */       
/* 1042 */       loginName = findAndReplaceAll(loginName, " ", "_");
/* 1043 */       loginName = findAndReplaceAll(loginName, File.separator, "/");
/* 1044 */       loginName = loginName.toLowerCase();
/* 1045 */       String newMASLocation = loginName + "" + remoteHost;
/* 1046 */       String tempMasLocation = newMASLocation;
/* 1047 */       dir = new File(nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + newMASLocation);
/* 1048 */       if (dir.exists())
/*      */       {
/*      */ 
/*      */ 
/* 1052 */         if (canDeleteMas_ImageDirFiles)
/*      */         {
/* 1054 */           if (dir.isDirectory())
/*      */           {
/* 1056 */             String[] children = dir.list();
/* 1057 */             for (int i = 0; i < children.length; i++)
/*      */             {
/* 1059 */               new File(nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + newMASLocation + File.separator + children[i]).delete();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/* 1066 */         new File(nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + newMASLocation).mkdirs();
/*      */       }
/*      */       
/* 1069 */       dir = new File(nmshome + File.separator + "users" + File.separator + "temp");
/* 1070 */       if (dir.exists())
/*      */       {
/*      */ 
/*      */ 
/* 1074 */         if ((canDeleteMas_ImageDirFiles) && (dir.isDirectory()))
/*      */         {
/* 1076 */           String[] children = dir.list();
/* 1077 */           for (int i = 0; i < children.length; i++)
/*      */           {
/* 1079 */             new File(nmshome + File.separator + "users" + File.separator + "temp" + File.separator + children[i]).delete();
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 1086 */         new File(nmshome + File.separator + "users" + File.separator + "temp").mkdirs();
/*      */       }
/*      */       
/* 1089 */       String selectedscheme = "default";
/* 1090 */       if (session.getAttribute("selectedscheme") != null)
/*      */       {
/* 1092 */         selectedscheme = (String)session.getAttribute("selectedscheme");
/*      */       }
/* 1094 */       String selectedSkin = "Blue";
/* 1095 */       if (session.getAttribute("selectedskin") != null)
/*      */       {
/* 1097 */         selectedSkin = (String)session.getAttribute("selectedskin");
/*      */       }
/* 1099 */       String replaceSkin = "/images/Blue/";
/* 1100 */       if ((uriToTest.startsWith("/showresource.do")) || (uriToTest.startsWith("/showCustom.do"))) {
/* 1101 */         Cookie[] cookies = hreq.getCookies();
/* 1102 */         String scrWidth = "1280";
/* 1103 */         if ((cookies != null) && (cookies.length > 0))
/*      */         {
/*      */ 
/* 1106 */           for (int i = 0; i < cookies.length; i++)
/*      */           {
/* 1108 */             if (cookies[i].getName().equals("ScreenWidth")) {
/*      */               try {
/* 1110 */                 scrWidth = cookies[i].getValue();
/*      */               }
/*      */               catch (Exception e) {
/* 1113 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1119 */         url = url + "&screenWidth=" + scrWidth;
/*      */       }
/* 1121 */       String tempUri = url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/* 1122 */       String urlScheme = "https://" + mas_host + ":" + mas_port + url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/* 1123 */       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */       {
/* 1125 */         if (EnterpriseUtil.isAdminServer)
/*      */         {
/* 1127 */           url = url + "&fromCentral=true";
/*      */         }
/* 1129 */         return proxyRequestForIT360(url, mas_host, port, mas_port, hresponse, hreq, mgId, selectedscheme, selectedSkin, canDeleteMas_ImageDirFiles, newMASLocation, replaceSkin, loginName);
/*      */       }
/*      */       
/*      */ 
/* 1133 */       String tmpurl = "https://" + mas_host + ":" + mas_port + url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin + "&proxy=true";
/*      */       
/* 1135 */       String proxyTicket = null;
/* 1136 */       if (AMAutomaticPortChanger.isSsoEnabled()) {
/* 1137 */         AttributePrincipal principal = (AttributePrincipal)hreq.getUserPrincipal();
/* 1138 */         if (principal != null) {
/* 1139 */           if (mas_port.equals("443")) {
/* 1140 */             tmpurl = "https://" + mas_host + url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin + "&proxy=true";
/*      */           }
/* 1142 */           proxyTicket = principal.getProxyTicketFor(tmpurl);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1147 */       URL server = new URL(tmpurl);
/* 1148 */       con = new HTTPConnection(server);
/* 1149 */       NVPair nvpairNew1 = new NVPair("Content-Type", "text/html");
/* 1150 */       NVPair nvpairNew2 = new NVPair(com.adventnet.appmanager.server.framework.comm.Constants.HEADER_KEY, com.adventnet.appmanager.server.framework.comm.Constants.HEADER_VAL);
/* 1151 */       NVPair nvpairNew3 = new NVPair("Referer", tempUri);
/* 1152 */       NVPair nvpairNew4 = new NVPair("Pragma", "no-cache");
/* 1153 */       NVPair[] headers1 = { nvpairNew1, nvpairNew2, nvpairNew3, nvpairNew4 };
/*      */       
/* 1155 */       if ((AMAutomaticPortChanger.isSsoEnabled()) && (proxyTicket != null))
/*      */       {
/* 1157 */         nvpairNew1 = new NVPair("Content-Type", "text/html");
/* 1158 */         nvpairNew2 = new NVPair(com.adventnet.appmanager.server.framework.comm.Constants.HEADER_KEY, com.adventnet.appmanager.server.framework.comm.Constants.HEADER_VAL);
/*      */         
/* 1160 */         nvpairNew4 = new NVPair("Pragma", "no-cache");
/* 1161 */         headers1 = new NVPair[] { nvpairNew1, nvpairNew2, nvpairNew4 };
/*      */       }
/*      */       
/* 1164 */       List<NVPair> headersList = new ArrayList(Arrays.asList(headers1));
/*      */       
/* 1166 */       String xRequestedWith = hreq.getHeader("X-Requested-With");
/* 1167 */       if (xRequestedWith != null)
/*      */       {
/* 1169 */         NVPair ajaxRequests = new NVPair("X-Requested-With", xRequestedWith);
/* 1170 */         headersList.add(ajaxRequests);
/*      */       }
/* 1172 */       if (hreq.getAttribute("op_instances") != null)
/*      */       {
/* 1174 */         ArrayList<Long> operator_insight_instances = (ArrayList)hreq.getAttribute("op_instances");
/* 1175 */         NVPair insight_op_instances = new NVPair("op_instances", StringUtils.getAsCSV(operator_insight_instances.toArray(), false));
/* 1176 */         headersList.add(insight_op_instances);
/*      */       }
/*      */       
/* 1179 */       headers1 = (NVPair[])headersList.toArray(new NVPair[headersList.size()]);
/*      */       
/* 1181 */       HTTPClient.HTTPResponse rsp1 = null;
/* 1182 */       System.out.println("hreq.getMethod()========" + hreq.getMethod());
/* 1183 */       if (hreq.getMethod() == "POST")
/*      */       {
/* 1185 */         NVPair nvpairNew5 = new NVPair("selectedscheme", selectedscheme);
/* 1186 */         NVPair nvpairNew6 = new NVPair("selectedSkin", selectedSkin);
/* 1187 */         NVPair[] formElements = { nvpairNew5, nvpairNew6 };
/* 1188 */         rsp1 = con.Post(getURI(urlScheme), formElements, headers1);
/*      */       }
/* 1190 */       else if (hreq.getMethod() == "GET")
/*      */       {
/*      */ 
/*      */ 
/* 1194 */         if ((AMAutomaticPortChanger.isSsoEnabled()) && (proxyTicket != null)) {
/* 1195 */           tmpurl = tmpurl + "&ticket=" + proxyTicket;
/* 1196 */           rsp1 = con.Get(tmpurl.trim(), "", headers1);
/*      */         }
/*      */         else {
/* 1199 */           rsp1 = con.Get(tempUri.trim(), "", headers1);
/*      */         }
/*      */       }
/*      */       
/* 1203 */       int responseStatusCode = rsp1.getStatusCode();
/* 1204 */       boolean returnStatus = adminAlarmOperations(url, responseStatusCode, hreq, hresponse);
/*      */       
/* 1206 */       if (returnStatus) {
/* 1207 */         return true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1213 */       if ((rsp1 != null) && (rsp1.getHeader("Content-Disposition") != null)) {
/* 1214 */         BufferedInputStream bufInputStream = null;
/*      */         try {
/* 1216 */           hresponse.setContentType(rsp1.getHeader("Content-Type"));
/* 1217 */           hresponse.setHeader("Content-Disposition", rsp1.getHeader("Content-Disposition"));
/* 1218 */           bufInputStream = new BufferedInputStream(rsp1.getInputStream());
/* 1219 */           OutputStream responseOutputStream = hresponse.getOutputStream();
/*      */           
/* 1221 */           byte[] data = new byte['Ѐ'];
/*      */           int len;
/* 1223 */           while ((len = bufInputStream.read(data, 0, data.length)) != -1) {
/* 1224 */             responseOutputStream.write(data, 0, len);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1233 */           return true;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1227 */           e.printStackTrace();
/*      */         } finally {
/*      */           try {
/* 1230 */             bufInputStream.close();
/*      */           }
/*      */           catch (Exception e) {}
/*      */         }
/*      */       }
/*      */       
/* 1236 */       BufferedReader br = new BufferedReader(new InputStreamReader(rsp1.getInputStream()));
/*      */       
/* 1238 */       String line = null;
/*      */       
/*      */ 
/* 1241 */       ArrayList<String> list1 = new ArrayList();
/*      */       
/* 1243 */       while ((line = br.readLine()) != null)
/*      */       {
/* 1245 */         list1.add(line);
/* 1246 */         if (line.indexOf("The server is currently starting up") != -1)
/*      */         {
/* 1248 */           String type = "null";
/* 1249 */           if (hreq.getParameter("type") != null)
/*      */           {
/* 1251 */             type = hreq.getParameter("type");
/*      */           }
/* 1253 */           String group = "";
/* 1254 */           if (hreq.getParameter("group") != null)
/*      */           {
/* 1256 */             group = hreq.getParameter("group");
/*      */           }
/*      */           else
/*      */           {
/* 1260 */             group = hreq.getParameter("viewType");
/*      */           }
/*      */           
/* 1263 */           writeServerDownMessage(url, mas_host, mas_port, hresponse, type, group, -1);
/*      */         } else {
/* 1265 */           if ((line.indexOf("j_security_check") != -1) && (line.indexOf("loginForm") != -1))
/*      */           {
/* 1267 */             String passWord = "admin";
/* 1268 */             int ID = -1;
/*      */             try
/*      */             {
/* 1271 */               String passwordSelectQuery = "select " + DBQueryUtil.decode("PASSWORD") + ",ID from AM_MAS_SERVER where HOST='" + mas_host + "' AND SSLPORT=" + mas_port;
/* 1272 */               set = AMConnectionPool.executeQueryStmt(passwordSelectQuery);
/* 1273 */               while (set.next())
/*      */               {
/* 1275 */                 passWord = PluginUtil.isPlugin() ? "admin@opm" : set.getString(1);
/* 1276 */                 ID = Integer.parseInt(set.getString(2));
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1281 */               e.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/* 1285 */               AMConnectionPool.closeStatement(set);
/*      */             }
/* 1287 */             String newserver = "https://" + mas_host + ":" + mas_port + "/j_security_check?j_username=" + "systemadmin_enterprise" + "&j_password=" + passWord;
/* 1288 */             String requestUrl = "https://" + mas_host + ":" + mas_port + "/j_security_check";
/* 1289 */             URI uri = new URI(getHostPort(requestUrl));
/* 1290 */             HTTPConnection con1 = new HTTPConnection(uri);
/*      */             
/* 1292 */             NVPair nvpair2 = new NVPair("j_username", "systemadmin_enterprise");
/* 1293 */             NVPair nvpair3 = new NVPair("j_password", passWord);
/* 1294 */             Map paramMap = hreq.getParameterMap();
/* 1295 */             NVPair[] formelements = new NVPair[2 + paramMap.size()];
/* 1296 */             formelements[0] = nvpair2;
/* 1297 */             formelements[1] = nvpair3;
/* 1298 */             NVPair nvpair1 = new NVPair("Pragma", "no-cache");
/* 1299 */             NVPair[] headers = new NVPair[1 + paramMap.size()];
/* 1300 */             headers[0] = nvpair1;
/* 1301 */             int startIndex = 1;
/* 1302 */             for (Object o : paramMap.keySet())
/*      */             {
/* 1304 */               headers[(startIndex++)] = new NVPair(o.toString(), paramMap.get(o).toString());
/* 1305 */               formelements[startIndex] = new NVPair(o.toString(), paramMap.get(o).toString());
/*      */             }
/*      */             
/* 1308 */             HTTPClient.HTTPResponse rsp = con1.Post(getURI(requestUrl), formelements, headers);
/*      */             
/*      */ 
/*      */ 
/* 1312 */             BufferedReader br1 = new BufferedReader(new InputStreamReader(rsp.getInputStream()));
/* 1313 */             String line1 = null;
/* 1314 */             ArrayList<String> list = new ArrayList();
/* 1315 */             while ((line1 = br1.readLine()) != null)
/*      */             {
/* 1317 */               list.add(line1);
/* 1318 */               if ((line1.indexOf("/webclient/temp/") != -1) || (line1.indexOf("projects/webscripts/ScreenShots") != -1) || ((line.indexOf("projects/webscripts/") != -1) && (line.indexOf(".json") != -1)))
/*      */               {
/* 1320 */                 if (EnterpriseUtil.getUseProxyForMASImage())
/*      */                 {
/* 1322 */                   writeMASImageIntoAdminServer(line1, mas_host, mas_port, port, newMASLocation, hreq);
/*      */                 }
/*      */                 
/*      */               }
/* 1326 */               else if (line1.indexOf(FormatUtil.getString("Invalid username and/or password")) != -1)
/*      */               {
/* 1328 */                 String type = "null";
/* 1329 */                 if (hreq.getParameter("type") != null)
/*      */                 {
/* 1331 */                   type = hreq.getParameter("type");
/*      */                 }
/* 1333 */                 String group = "";
/* 1334 */                 if (hreq.getParameter("group") != null)
/*      */                 {
/* 1336 */                   group = hreq.getParameter("group");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1340 */                   group = hreq.getParameter("viewType");
/*      */                 }
/* 1342 */                 writeServerDownMessage(url, mas_host, mas_port, hresponse, type, group, ID);
/* 1343 */               } else if (line1.indexOf("/users") != -1)
/*      */               {
/* 1345 */                 newMASLocation = "myfields";
/* 1346 */                 writeMASImageIntoAdminServer(line1, mas_host, mas_port, port, newMASLocation);
/*      */               }
/*      */               
/* 1349 */               newMASLocation = tempMasLocation;
/*      */             }
/* 1351 */             br1.close();
/* 1352 */             clientOutput(newserver, mas_host, port, mas_port, replaceSkin, loginName, hresponse, hreq, list, mgId, newMASLocation);
/* 1353 */             return true;
/*      */           }
/*      */           
/* 1356 */           if ((line.indexOf("/webclient/temp/") != -1) || (line.indexOf("projects/webscripts/ScreenShots") != -1) || ((line.indexOf("projects/webscripts/") != -1) && (line.indexOf(".json") != -1)))
/*      */           {
/* 1358 */             if (EnterpriseUtil.getUseProxyForMASImage())
/*      */             {
/* 1360 */               writeMASImageIntoAdminServer(line, mas_host, mas_port, port, newMASLocation);
/*      */             }
/*      */           }
/* 1363 */           else if (line.indexOf("/users") != -1)
/*      */           {
/* 1365 */             newMASLocation = "myfields";
/* 1366 */             writeMASImageIntoAdminServer(line, mas_host, mas_port, port, newMASLocation);
/*      */           } }
/* 1368 */         newMASLocation = tempMasLocation;
/*      */       }
/* 1370 */       br.close();
/* 1371 */       String server2 = "https://" + mas_host + ":" + mas_port + url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/* 1372 */       clientOutput(server2, mas_host, port, mas_port, replaceSkin, loginName, hresponse, hreq, list1, mgId, newMASLocation);
/*      */       
/* 1374 */       return true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1378 */       e.printStackTrace();
/* 1379 */       AMLog.debug("AAMValve : " + e);
/* 1380 */       String type = "null";
/* 1381 */       if (hreq.getParameter("type") != null)
/*      */       {
/* 1383 */         type = hreq.getParameter("type");
/*      */       }
/* 1385 */       String group = "";
/* 1386 */       if (hreq.getParameter("group") != null)
/*      */       {
/* 1388 */         group = hreq.getParameter("group");
/*      */       }
/*      */       else
/*      */       {
/* 1392 */         group = hreq.getParameter("viewType");
/*      */       }
/* 1394 */       if ((e.getMessage() != null) && (e.getMessage().indexOf("Connection refused") == -1))
/*      */       {
/* 1396 */         writeServerDownMessage(url, mas_host, mas_port, hresponse, type, group, 0); }
/*      */     }
/* 1398 */     return false;
/*      */   }
/*      */   
/*      */   public static void clientOutput(HttpServletResponse hresponse, String addpendToResponseString, String contentType)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1406 */       PrintWriter out = hresponse.getWriter();
/* 1407 */       if (contentType.equals("xml")) {
/* 1408 */         hresponse.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       else {
/* 1411 */         hresponse.setContentType("text/html; charset=UTF-8");
/*      */       }
/* 1413 */       out.println(addpendToResponseString);
/*      */     }
/*      */     catch (Exception e) {
/* 1416 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void clientOutput(String urlToSend, String mas_host, String port, String mas_port, String replaceSkin, String loginName, HttpServletResponse hresponse, HttpServletRequest hreq, ArrayList list, String mgId, String newMASLocation) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1424 */       PrintWriter out = hresponse.getWriter();
/* 1425 */       hresponse.setContentType("text/html; charset=UTF-8");
/* 1426 */       String roleName = "ENTERPRISEADMIN";
/* 1427 */       if (hreq.isUserInRole("ENTERPRISEADMIN"))
/*      */       {
/* 1429 */         roleName = "ENTERPRISEADMIN";
/*      */       }
/* 1431 */       else if (hreq.isUserInRole("OPERATOR"))
/*      */       {
/* 1433 */         roleName = "OPERATOR";
/*      */       }
/* 1435 */       else if (hreq.isUserInRole("USERS"))
/*      */       {
/* 1437 */         roleName = "USERS";
/*      */       }
/* 1439 */       String line = null;
/* 1440 */       String stringToReplace = "/webclient/temp/";
/* 1441 */       String rbmImagesToReplace = "/projects/webscripts/ScreenShots/";
/* 1442 */       String rbmJSONToReplace = "/projects/webscripts";
/* 1443 */       String imageToReplace = "/users/";
/* 1444 */       String httpStr = "http://" + mas_host + ":" + port + "/";
/* 1445 */       String httpsUrlStr = "https://" + mas_host + ":" + mas_port + "/";
/* 1446 */       String replaceStr = httpStr + "/webclient/temp/";
/* 1447 */       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */       {
/* 1449 */         replaceStr = httpsUrlStr + "/webclient/temp/";
/*      */       }
/*      */       
/*      */ 
/* 1453 */       String empty = "";
/* 1454 */       String editMonitor = FormatUtil.getString("am.webclient.dotnet.edit");
/* 1455 */       String configureAlerts = FormatUtil.getString("am.webclient.admin.configurealerts.link");
/* 1456 */       int k = 0;
/* 1457 */       String mgName = null;
/* 1458 */       while (k < list.size())
/*      */       {
/* 1460 */         line = (String)list.get(k);
/* 1461 */         k++;
/*      */         
/* 1463 */         if (line.contains("<input type='hidden' name='managedServerId' value='-1'/>"))
/*      */         {
/* 1465 */           line = ApmInsightProxyUtil.fillManagedServersSelectionComboBox(hreq);
/*      */         }
/* 1467 */         if (((line.indexOf("&name=null&method=showdetails&tabtoselect=") != -1) && (line.indexOf("&haid=") != -1)) || ((line.indexOf("&haid=") != -1) && (line.indexOf("&appName=null&") != -1)))
/*      */         {
/* 1469 */           ResultSet set = null;
/*      */           try
/*      */           {
/* 1472 */             String mgNameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + mgId;
/* 1473 */             set = AMConnectionPool.executeQueryStmt(mgNameQuery);
/* 1474 */             while (set.next())
/*      */             {
/* 1476 */               mgName = set.getString("RESOURCENAME");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/* 1487 */               set.close();
/*      */             }
/*      */             catch (Exception e) {}
/*      */             
/*      */ 
/*      */ 
/* 1493 */             line = findAndReplaceAll(line, "null", mgName);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1481 */             e.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 1487 */               set.close();
/*      */             }
/*      */             catch (Exception e) {}
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1496 */         if (line.indexOf("EE MAS Header starts") != -1)
/*      */         {
/* 1498 */           int z = 0;
/*      */           do {
/* 1500 */             line = findAndReplaceAll(line, line, "");
/* 1501 */             list.remove(k);
/* 1502 */             out.println(line);
/* 1503 */             z++;
/* 1504 */             line = (String)list.get(k);
/* 1505 */           } while (line.indexOf("EE MAS Header ends") == -1);
/*      */           
/*      */ 
/* 1508 */           if (!PluginUtil.isPlugin()) {
/* 1509 */             RequestDispatcher rd = hreq.getRequestDispatcher("/jsp/header.jsp");
/* 1510 */             rd.include(hreq, hresponse);
/*      */           }
/*      */         }
/* 1513 */         if (line.indexOf(imageToReplace) != -1)
/*      */         {
/* 1515 */           String temp = line.substring(line.indexOf(imageToReplace), line.indexOf("jpg") + 3);
/* 1516 */           String replace = temp.substring(7);
/* 1517 */           replace = replace.substring(replace.indexOf("/") + 1);
/* 1518 */           line = findAndReplaceAll(line, temp, "/users/temp/" + replace);
/*      */         }
/* 1520 */         if ((line.indexOf("showapplication.do?haid=") != -1) && (line.indexOf("&method=showApplication") != -1) && (line.indexOf("null") != -1))
/*      */         {
/* 1522 */           if (mgName == null)
/*      */           {
/* 1524 */             ResultSet set = null;
/*      */             try
/*      */             {
/* 1527 */               String mgNameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + mgId;
/* 1528 */               set = AMConnectionPool.executeQueryStmt(mgNameQuery);
/* 1529 */               while (set.next())
/*      */               {
/* 1531 */                 mgName = set.getString("RESOURCENAME");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 1542 */                 set.close();
/*      */               }
/*      */               catch (Exception e) {}
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 1549 */               line = findAndReplaceAll(line, "null", mgName);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1536 */               e.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 1542 */                 set.close();
/*      */               }
/*      */               catch (Exception e) {}
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1552 */         if (line.indexOf("systemadmin_enterprise") != -1)
/*      */         {
/* 1554 */           line = findAndReplaceAll(line, "systemadmin_enterprise", loginName);
/*      */         }
/* 1556 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (roleName.equals("USERS")) || (roleName.equals("OPERATOR"))) && (line.indexOf(editMonitor) != -1))
/*      */         {
/* 1558 */           line = findAndReplaceAll(line, line, empty);
/*      */         }
/* 1560 */         if (((roleName.equals("USERS")) || (roleName.equals("OPERATOR"))) && (line.indexOf("dontDelete") != -1))
/*      */         {
/* 1562 */           line = "";
/*      */         }
/* 1564 */         if ((line.indexOf(stringToReplace) != -1) || (line.indexOf(rbmImagesToReplace) != -1) || ((line.indexOf(rbmJSONToReplace) != -1) && (line.indexOf(".json") != -1))) {
/* 1565 */           if (EnterpriseUtil.getUseProxyForMASImage())
/*      */           {
/* 1567 */             if (line.indexOf(rbmImagesToReplace) != -1) {
/* 1568 */               line = line.replaceAll(rbmImagesToReplace, "/webclient/temp/" + newMASLocation + "/");
/*      */             }
/* 1570 */             else if ((line.indexOf(rbmJSONToReplace) != -1) && (line.indexOf(".json") != -1)) {
/* 1571 */               line = line.replaceAll(rbmJSONToReplace, "/webclient/temp/" + newMASLocation + "/");
/*      */             }
/*      */             else {
/* 1574 */               line = line.replaceAll(stringToReplace, "/webclient/temp/" + newMASLocation + "/");
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/* 1579 */             line = line.replaceAll(stringToReplace, replaceStr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 1594 */         else if (line.indexOf("mnu_normal_end.gif") != -1)
/*      */         {
/*      */ 
/* 1597 */           out.println(line);
/* 1598 */           out.println("</td><td colspan=\"17\" align=\"right\" class=\"txtGlobal\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td align=\"right\" class=\"txtGlobal\"><div id=\"dropmenudiv\" style=\"position:absolute; visibility:hidden;left:650px; top:150px;z-index:2 \"onMouseover=\"clearhidemenu()\" onMouseout=\"dynamichide(event)\"><span class=\"txtGlobal\"style=\"color:#aaa; text-decoration:none; padding-top:5px\"><script>");
/*      */           
/* 1600 */           ArrayList<String> masHostList = new ArrayList();
/* 1601 */           ArrayList<String> masPortList = new ArrayList();
/* 1602 */           Object masUrlList = new ArrayList();
/* 1603 */           String jumpToUrl = null;
/* 1604 */           ResultSet set1 = null;
/*      */           try
/*      */           {
/* 1607 */             String masServerQuery = "select HOST , SSLPORT from AM_MAS_SERVER where SERVERSTATUS=1";
/* 1608 */             set1 = AMConnectionPool.executeQueryStmt(masServerQuery);
/* 1609 */             while (set1.next())
/*      */             {
/* 1611 */               masHostList.add(set1.getString(1));
/* 1612 */               masPortList.add(set1.getString(2));
/* 1613 */               jumpToUrl = "https://" + set1.getString(1) + ":" + set1.getString(2);
/* 1614 */               ((ArrayList)masUrlList).add(jumpToUrl);
/*      */             }
/* 1616 */             set1.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1620 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/* 1623 */             AMConnectionPool.closeStatement(set1);
/*      */           }
/* 1625 */           line = "var menu3=new Array()";
/* 1626 */           out.println(line);
/* 1627 */           out.println("\n");
/* 1628 */           line = "menu3[0]='<table width=100% border=0 cellspacing=0 cellpadding=0 class=\"lrtbdarkborder\"><tr><td bgcolor=#FCFCCE colspan=2 class=bodytext style=border-bottom:1px solid #ccc> Jump to :</td></tr>'";
/* 1629 */           out.println(line);
/* 1630 */           out.println("\n");
/* 1631 */           int j = 3;
/* 1632 */           if (((ArrayList)masUrlList).size() > 0)
/*      */           {
/* 1634 */             for (int count = 0; count < ((ArrayList)masUrlList).size(); count++)
/*      */             {
/* 1636 */               line = "menu3[" + j + "]='<tr><td width=2% bgcolor=#FCFCCE><img src=/images/jump_managedserver.gif width=16 height=14 hspace=10 vspace=5></td><td width=98% bgcolor=#FCFCCE><a href=" + (String)((ArrayList)masUrlList).get(count) + " class=txtGlobal  style=display:block target=_blank>" + (String)masHostList.get(count) + ":" + (String)masPortList.get(count) + "</a></td></tr>'";
/* 1637 */               out.println(line);
/* 1638 */               out.println("\n");
/* 1639 */               j++;
/*      */             }
/*      */           }
/* 1642 */           line = "menu3[" + j + "]='</table>'";
/* 1643 */           out.println(line);
/* 1644 */           out.println("</script></span></div></td>");
/* 1645 */           line = findAndReplaceAll(line, line, "<td class=\"bodytext\" align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;Jump to :<a href=\"#\" onclick=\"return clickreturnvalue()\" onmouseover=\"dropdownmenu(this, event,menu3,'180px')\" onmouseout=\"delayhidemenu()\"><img src=\"/images/jumpto.gif\" alt=\"\" align=\"middle\" border=\"0\" height=\"20\" hspace=\"2\" vspace=\"2\"></a></td>");
/*      */         }
/* 1647 */         if ((com.adventnet.appmanager.util.Constants.isIt360) && (roleName.equals("OPERATOR")))
/*      */         {
/* 1649 */           if (line.indexOf(configureAlerts) != -1)
/*      */           {
/* 1651 */             line = findAndReplaceAll(line, configureAlerts, empty);
/* 1652 */             if (line.indexOf("images/icon_associateaction.gif") != -1)
/*      */             {
/* 1654 */               line = findAndReplaceAll(line, "icon_associateaction.gif", "spacer.gif");
/*      */             }
/*      */           }
/* 1657 */           if (line.indexOf("images/icon_associateaction.gif") != -1)
/*      */           {
/* 1659 */             line = findAndReplaceAll(line, "icon_associateaction.gif", "spacer.gif");
/*      */           }
/*      */         }
/*      */         
/* 1663 */         if (line.indexOf("queryEdit") != -1) {
/* 1664 */           line = findAndReplaceAll(line, line, "<td height=\"26\" colspan=\"2\"  width=\"25%\" class=\"tableheading\" align=\"right\" colspan=\"1\"> </td>");
/*      */         }
/*      */         
/*      */ 
/* 1668 */         if ((line.indexOf("New Monitor Group") != -1) && (roleName.equals("OPERATOR"))) {
/* 1669 */           line = findAndReplaceAll(line, line, "&nbsp; | &nbsp;<a href=\"\" style=\"color:#888888\">New Monitor Group</a>");
/*      */         }
/*      */         
/* 1672 */         if ((line.indexOf("operator text") != -1) && (roleName.equals("OPERATOR"))) {
/* 1673 */           line = findAndReplaceAll(line, line, "<td background=\"/images/bg_mnu_normal.gif\"></td>");
/*      */         }
/*      */         
/*      */ 
/* 1677 */         if ((line.indexOf("not needed") != -1) && (roleName.equals("OPERATOR"))) {
/* 1678 */           int z = 0;
/*      */           do {
/* 1680 */             line = findAndReplaceAll(line, line, "");
/* 1681 */             list.remove(k);
/* 1682 */             out.println(line);
/* 1683 */             z++;
/* 1684 */             line = (String)list.get(k);
/* 1685 */           } while (line.indexOf("</form>") == -1);
/*      */         }
/*      */         
/*      */ 
/* 1689 */         if ((line.indexOf("operator EE starts") != -1) && (roleName.equals("OPERATOR"))) {
/* 1690 */           int z = 0;
/*      */           do {
/* 1692 */             line = findAndReplaceAll(line, line, "");
/* 1693 */             list.remove(k);
/* 1694 */             out.println(line);
/* 1695 */             z++;
/* 1696 */             line = (String)list.get(k);
/* 1697 */           } while (line.indexOf("operator EE ends") == -1);
/*      */         }
/*      */         
/* 1700 */         if (line.indexOf("scripttable disable starts") != -1) {
/* 1701 */           int z = 0;
/*      */           do {
/* 1703 */             line = findAndReplaceAll(line, line, "");
/* 1704 */             list.remove(k);
/* 1705 */             out.println(line);
/* 1706 */             z++;
/* 1707 */             line = (String)list.get(k);
/* 1708 */           } while (line.indexOf("scripttable disable ends") == -1);
/*      */         }
/* 1710 */         if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf("<title>") != -1))
/*      */         {
/* 1712 */           String toBeReplaced = line.substring(line.indexOf("<title>") + 7, line.indexOf("</title>"));
/* 1713 */           if (line.indexOf('-') != -1)
/*      */           {
/*      */ 
/* 1716 */             toBeReplaced = line.substring(line.indexOf("<title>") + 7, line.indexOf('-') - 1);
/*      */           }
/* 1718 */           String replaceWith = EnterpriseUtil.getTitle();
/* 1719 */           line = findAndReplaceAll(line, toBeReplaced, replaceWith);
/*      */         }
/* 1721 */         if ((com.adventnet.appmanager.util.Constants.isIt360) && (line.indexOf("browser-title") != -1))
/*      */         {
/* 1723 */           String toBeReplaced = line.substring(line.indexOf("<span id=\"browser-title\" style=\"display: none;\">") + 48, line.indexOf("</span>"));
/* 1724 */           String replaceWith = EnterpriseUtil.getTitle();
/* 1725 */           line = findAndReplaceAll(line, toBeReplaced, replaceWith);
/*      */         }
/*      */         
/* 1728 */         out.println(line);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1733 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void myout(String s)
/*      */   {
/*      */     try
/*      */     {
/* 1744 */       PrintWriter pw = new PrintWriter(new FileWriter("gkm_valve_log.txt", true));
/* 1745 */       pw.println(s);
/* 1746 */       pw.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1750 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void writeMASImageIntoAdminServer(String line, String mas_host, String mas_port, String port, String mas_location)
/*      */   {
/* 1756 */     writeMASImageIntoAdminServer(line, mas_host, mas_port, port, mas_location, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void writeMASImageIntoAdminServer(String line, String mas_host, String mas_port, String port, String mas_location, HttpServletRequest hreq)
/*      */   {
/* 1763 */     com.manageengine.appmanager.comm.HTTPResponse rsp = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1768 */       String nmshome = System.getProperty("webnms.rootdir");
/* 1769 */       String imageFilleName = null;
/* 1770 */       int startingIndex = 0;
/* 1771 */       int endIndex = 0;
/* 1772 */       String userImageName = null;
/* 1773 */       if (mas_location.equals("myfields")) {
/* 1774 */         startingIndex = line.indexOf("/users");
/* 1775 */         endIndex = line.indexOf("jpg");
/* 1776 */         imageFilleName = line.substring(startingIndex, endIndex + 3);
/* 1777 */         userImageName = imageFilleName.substring(7);
/* 1778 */         userImageName = userImageName.substring(userImageName.indexOf("/") + 1);
/* 1779 */       } else if (line.indexOf("projects/webscripts/ScreenShots") != -1) {
/* 1780 */         startingIndex = line.indexOf("projects/webscripts/ScreenShots") + 31;
/* 1781 */         if (line.indexOf("jpg") > 0)
/*      */         {
/* 1783 */           endIndex = line.indexOf("jpg");
/*      */         }
/*      */         else
/*      */         {
/* 1787 */           endIndex = line.indexOf("png");
/*      */         }
/* 1789 */         imageFilleName = line.substring(startingIndex, endIndex + 3);
/* 1790 */       } else if ((line.indexOf("projects/webscripts/") != -1) && (line.indexOf(".json") != -1))
/*      */       {
/* 1792 */         startingIndex = line.indexOf("projects/webscripts/") + 20;
/* 1793 */         endIndex = line.indexOf(".json");
/* 1794 */         imageFilleName = line.substring(startingIndex, endIndex + 5);
/*      */       } else {
/* 1796 */         startingIndex = line.indexOf("jfreechart");
/* 1797 */         if (line.indexOf("jpg") > 0)
/*      */         {
/* 1799 */           endIndex = line.indexOf("jpg");
/*      */         }
/*      */         else
/*      */         {
/* 1803 */           endIndex = line.indexOf("png");
/*      */         }
/*      */         
/* 1806 */         if (startingIndex < 0)
/*      */         {
/* 1808 */           startingIndex = line.indexOf("/webclient/temp/") + 16;
/*      */         }
/* 1810 */         AMLog.info("Image path===>" + line.substring(startingIndex, endIndex + 3));
/* 1811 */         imageFilleName = line.substring(startingIndex, endIndex + 3);
/*      */       }
/*      */       
/* 1814 */       String url = "https://" + mas_host + ":" + mas_port + "/webclient/temp/" + imageFilleName;
/* 1815 */       if (mas_location.equals("myfields")) {
/* 1816 */         url = "https://" + mas_host + ":" + mas_port + imageFilleName;
/* 1817 */       } else if (line.indexOf("projects/webscripts/ScreenShots") != -1) {
/* 1818 */         url = "https://" + mas_host + ":" + mas_port + "/projects/webscripts/ScreenShots/" + imageFilleName;
/* 1819 */       } else if ((line.indexOf("projects/webscripts/") != -1) && (line.indexOf(".json") != -1)) {
/* 1820 */         url = "https://" + mas_host + ":" + mas_port + "/projects/webscripts/" + imageFilleName;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1825 */       String proxyTicket = null;
/* 1826 */       Properties formprops = new Properties();
/*      */       
/* 1828 */       if ((hreq != null) && (AMAutomaticPortChanger.isSsoEnabled())) {
/*      */         try {
/* 1830 */           AttributePrincipal principal = (AttributePrincipal)hreq.getUserPrincipal();
/*      */           
/*      */ 
/* 1833 */           proxyTicket = principal.getProxyTicketFor(url);
/* 1834 */           formprops.setProperty("ticket", proxyTicket);
/* 1835 */           formprops.setProperty("proxy", "true");
/*      */         } catch (Exception exc) {
/* 1837 */           AMLog.debug("SSO: Unable to fetch image url " + url, exc);
/*      */         }
/*      */       }
/* 1840 */       rsp = EnterpriseUtil.sendAsynchRequestToProbe(url, "Get", null, null, 0);
/* 1841 */       if (rsp.getStatusCode() == 200) {
/* 1842 */         byte[] data = rsp.getData();
/* 1843 */         rsp.releaseConnection();
/*      */         
/*      */ 
/*      */ 
/* 1847 */         String directory = nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + mas_location + File.separator + imageFilleName;
/* 1848 */         if (mas_location.equals("myfields")) {
/* 1849 */           directory = nmshome + File.separator + "users" + File.separator + "temp" + File.separator + userImageName;
/*      */         }
/* 1851 */         else if (imageFilleName.contains("/")) {
/* 1852 */           String[] folders = imageFilleName.split("/");
/* 1853 */           directory = nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + mas_location + File.separator;
/* 1854 */           for (int i = 0; i < folders.length - 1; i++) {
/* 1855 */             directory = directory + folders[i] + File.separator;
/*      */           }
/* 1857 */           new File(directory).mkdirs();
/* 1858 */           directory = directory + folders[(folders.length - 1)];
/*      */         }
/*      */         
/* 1861 */         File f = new File(directory);
/* 1862 */         FileOutputStream out = new FileOutputStream(f);
/* 1863 */         out.write(data);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1868 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1872 */       if (rsp != null) {
/* 1873 */         rsp.releaseConnection();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static String getHostPort(String url)
/*      */   {
/* 1881 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/* 1882 */     String http = tokens.nextToken();
/* 1883 */     String hostport = tokens.nextToken();
/* 1884 */     return http + "//" + hostport + "/";
/*      */   }
/*      */   
/*      */   private static String getURI(String url)
/*      */   {
/* 1889 */     StringTokenizer tokens = new StringTokenizer(url, "/");
/* 1890 */     StringBuffer uri = new StringBuffer();
/* 1891 */     int i = 0;
/* 1892 */     for (i = 0; tokens.hasMoreTokens(); i++)
/*      */     {
/* 1894 */       if (i < 2)
/*      */       {
/* 1896 */         tokens.nextToken();
/*      */       }
/*      */       else {
/* 1899 */         uri.append("/");
/* 1900 */         uri.append(tokens.nextToken());
/*      */       } }
/* 1902 */     if (i == 0)
/*      */     {
/* 1904 */       uri = new StringBuffer("/");
/*      */     }
/* 1906 */     return uri.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean proxyRequestForIT360(String url, String mas_host, String port, String mas_port, HttpServletResponse hresponse, HttpServletRequest hreq, String mgId, String selectedscheme, String selectedSkin, boolean isReqContainsShowResource, String newMASLocation, String replaceSkin, String loginName)
/*      */   {
/* 1917 */     PostMethod methodTogetProbeData = null;
/* 1918 */     boolean isSuccuess = false;
/* 1919 */     String urlToFetchProbeData = ("true".equals(System.getProperty("server.secure")) ? "https://" : "http://") + mas_host + new StringBuilder().append(":").append("true".equals(System.getProperty("server.secure")) ? mas_port : port).toString();
/* 1920 */     String probeUrl = urlToFetchProbeData.trim();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1925 */       String probeTicket = (String)EnterpriseUtil.probeUserTickets.get(probeUrl);
/*      */       
/*      */ 
/* 1928 */       if ((probeTicket == null) || (probeTicket.trim().equals("")))
/*      */       {
/* 1930 */         makeADummyRequestToTheProbe(mas_host, port, mas_port);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1935 */         probeTicket = (String)EnterpriseUtil.probeUserTickets.get(probeUrl);
/*      */         
/*      */ 
/*      */ 
/* 1939 */         if ((probeTicket == null) || (probeTicket.trim().equals("")))
/*      */         {
/*      */ 
/* 1942 */           writeServerDownMessage(url, mas_host, mas_port, hresponse, "null", null, 0);
/* 1943 */           return true;
/*      */         }
/*      */       }
/* 1946 */       if (url.startsWith("/fault/AlarmOperations.do"))
/*      */       {
/* 1948 */         urlToFetchProbeData = urlToFetchProbeData + URLDecoder.decode(url, "UTF-8") + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/* 1949 */         AMLog.debug("urlToFetchProbeData : " + urlToFetchProbeData);
/*      */       } else {
/* 1951 */         urlToFetchProbeData = urlToFetchProbeData + url + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/*      */       }
/*      */       
/* 1954 */       temp = "";
/* 1955 */       if (urlToFetchProbeData.indexOf("&ticket") != -1)
/*      */       {
/* 1957 */         int ticketStartIndex = urlToFetchProbeData.indexOf("&ticket");
/* 1958 */         String temp1 = urlToFetchProbeData.substring(ticketStartIndex + 1);
/* 1959 */         if ((temp1 != null) && (!temp1.trim().equals("")))
/*      */         {
/* 1961 */           int leftOutQueryStringIndex = temp1.indexOf("&");
/* 1962 */           temp = temp1.substring(leftOutQueryStringIndex);
/*      */         }
/* 1964 */         urlToFetchProbeData = urlToFetchProbeData.substring(0, ticketStartIndex) + temp;
/*      */       }
/*      */       
/* 1967 */       boolean printerFriendly = true;
/* 1968 */       boolean includeCentralHeader = false;
/* 1969 */       if ((url != null) && (url.startsWith("/jsp/")))
/*      */       {
/* 1971 */         printerFriendly = false;
/*      */       }
/*      */       else
/*      */       {
/* 1975 */         String[] printerFriendlyExcludeUrls = { "/WMIPerfCounters.do", "/HostResourceDispatch.do", "/HostResource.do?NetworkInterface=true", "/manageEC2Instances.do", "/assignOSNames.do", "/ShowCAM.do", "/sap.do", "/JavaRuntime.do", "/as400.do", "/showAllEventLogs.do", "/Exchange.do", "/amazonActions.do", "/manageHyperVMs.do", "/Debug-Info/MySqlProcess", "/Debug-Info/ThreadDump", "/showCustom.do", "/dashboard.do", "/apminsight/" };
/* 1976 */         for (int i = 0; i < printerFriendlyExcludeUrls.length; i++)
/*      */         {
/* 1978 */           if (url.startsWith(printerFriendlyExcludeUrls[i]))
/*      */           {
/* 1980 */             printerFriendly = false;
/* 1981 */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1985 */       String oldTab = "3";
/* 1986 */       if (printerFriendly)
/*      */       {
/* 1988 */         String resourceID = hreq.getParameter("resourceid");
/* 1989 */         if (resourceID != null)
/*      */         {
/* 1991 */           String resGroup = com.adventnet.appmanager.util.Constants.getResgroupForResourceid(resourceID);
/* 1992 */           if ((resGroup != null) && (resGroup.equals("SYS")))
/*      */           {
/* 1994 */             oldTab = "2";
/*      */           }
/*      */         }
/* 1997 */         if ((hreq.getParameter("PRINTER_FRIENDLY") == null) || (!hreq.getParameter("PRINTER_FRIENDLY").equals("true")))
/*      */         {
/* 1999 */           includeCentralHeader = true;
/*      */         }
/* 2001 */         urlToFetchProbeData = urlToFetchProbeData + "&PRINTER_FRIENDLY=true";
/*      */       }
/*      */       
/* 2004 */       AMLog.debug("proxyRequestForIT360 :: URL AFTER ADDING TICKET FROM PROBE ==>" + urlToFetchProbeData);
/*      */       
/*      */ 
/* 2007 */       ArrayList<String> responseSrtingList = null;
/*      */       
/*      */ 
/* 2010 */       int respCode1 = -1;
/*      */       
/*      */ 
/* 2013 */       com.manageengine.appmanager.comm.HTTPResponse ress = null;
/*      */       String nmshome;
/*      */       String imagePath;
/*      */       try
/*      */       {
/* 2018 */         Properties formProps = new Properties();
/* 2019 */         nmshome = System.getProperty("webnms.rootdir");
/* 2020 */         String probeID = EnterpriseUtil.getMasID(mas_host, Integer.parseInt(mas_port));
/* 2021 */         imagePath = nmshome + File.separator + "webclient" + File.separator + "temp" + File.separator + newMASLocation;
/* 2022 */         formProps.setProperty("probeId", probeID);
/* 2023 */         formProps.setProperty("newMASLocation", newMASLocation);
/* 2024 */         formProps.setProperty("imagePath", imagePath);
/* 2025 */         if (urlToFetchProbeData.indexOf("apminsight/home.do") != -1) {
/* 2026 */           Properties headerProps = new Properties();
/* 2027 */           String xRequestedWith = hreq.getHeader("X-Requested-With");
/*      */           
/* 2029 */           if (xRequestedWith != null)
/*      */           {
/* 2031 */             headerProps.setProperty("X-Requested-With", xRequestedWith);
/*      */           }
/* 2033 */           ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchProbeData, "Post", formProps, headerProps, 0);
/*      */         }
/*      */         else
/*      */         {
/* 2037 */           ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchProbeData, "Post", formProps, null, 0);
/*      */         }
/* 2039 */         respCode1 = ress.getStatusCode();
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/* 2045 */         e.printStackTrace();
/* 2046 */         AMLog.debug("Exception while executing PostMethod :: Response Code :: " + respCode1);
/*      */       }
/*      */       
/*      */ 
/* 2050 */       boolean returnStatus = adminAlarmOperations(urlToFetchProbeData, respCode1, hreq, hresponse);
/* 2051 */       if (returnStatus) {
/* 2052 */         return 1;
/*      */       }
/*      */       
/* 2055 */       if (respCode1 == 200)
/*      */       {
/* 2057 */         if (includeCentralHeader)
/*      */         {
/* 2059 */           RequestDispatcher rd = hreq.getRequestDispatcher("/jsp/header.jsp?oldtab=" + oldTab);
/* 2060 */           rd.include(hreq, hresponse);
/*      */         }
/*      */         
/*      */ 
/* 2064 */         BufferedReader br1 = new BufferedReader(new InputStreamReader(ress.getInputStream()));
/*      */         
/*      */ 
/*      */ 
/* 2068 */         String line1 = null;
/* 2069 */         responseSrtingList = new ArrayList();
/* 2070 */         while ((line1 = br1.readLine()) != null)
/*      */         {
/* 2072 */           responseSrtingList.add(line1);
/* 2073 */           if ((line1.indexOf("/webclient/temp/") != -1) && (EnterpriseUtil.getUseProxyForMASImage()))
/*      */           {
/* 2075 */             writeMASImageIntoAdminServer(line1, mas_host, mas_port, port, newMASLocation);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2080 */         br1.close();
/* 2081 */         ress.releaseConnection();
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 2087 */         BufferedReader br1 = new BufferedReader(new InputStreamReader(ress.getInputStream()));
/*      */         
/*      */ 
/*      */ 
/* 2091 */         String line1 = null;
/* 2092 */         responseSrtingList = new ArrayList();
/* 2093 */         while ((line1 = br1.readLine()) != null)
/*      */         {
/* 2095 */           responseSrtingList.add(line1);
/* 2096 */           if ((line1.indexOf("/webclient/temp/") != -1) && 
/*      */           
/* 2098 */             (EnterpriseUtil.getUseProxyForMASImage()))
/*      */           {
/* 2100 */             writeMASImageIntoAdminServer(line1, mas_host, mas_port, port, newMASLocation);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2105 */         br1.close();
/* 2106 */         ress.releaseConnection();
/*      */         
/*      */ 
/* 2109 */         if ((responseSrtingList.isEmpty()) || (respCode1 == 302))
/*      */         {
/* 2111 */           AMLog.debug("response from the probe for the url " + urlToFetchProbeData + " leads to status " + respCode1 + ".Hence deleting the ticket of the probe from memory");
/*      */           
/*      */ 
/*      */ 
/* 2115 */           EnterpriseUtil.probeUserTickets.remove(probeUrl);
/*      */           
/* 2117 */           if (respCode1 == 302)
/*      */           {
/* 2119 */             AMLog.debug("Reconstructing url for new request to fetch ticket for the second time");
/* 2120 */             AMLog.debug("Before making recursive call to proxyRequestForIT360 - urlToFetchProbeData1 with ticket: " + url);
/* 2121 */             proxyRequestForIT360(url, mas_host, port, mas_port, hresponse, hreq, mgId, selectedscheme, selectedSkin, isReqContainsShowResource, newMASLocation, replaceSkin, loginName);
/*      */           }
/*      */           else
/*      */           {
/*      */             try
/*      */             {
/* 2127 */               writeServerDownMessage(url, mas_host, mas_port, hresponse, "null", null, 0);
/* 2128 */               return 1;
/*      */             }
/*      */             catch (Exception e1) {
/* 2131 */               e1.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2136 */       clientOutput(urlToFetchProbeData, mas_host, port, mas_port, replaceSkin, loginName, hresponse, hreq, responseSrtingList, mgId, newMASLocation);
/* 2137 */       isSuccuess = true;
/*      */     }
/*      */     catch (Exception e) {
/*      */       String temp;
/* 2141 */       e.printStackTrace();
/*      */       
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 2147 */         EnterpriseUtil.probeUserTickets.remove(probeUrl);
/*      */         
/*      */ 
/*      */ 
/* 2151 */         writeServerDownMessage(url, mas_host, mas_port, hresponse, "null", null, 0);
/* 2152 */         return true;
/*      */       } catch (Exception e1) {
/* 2154 */         e1.printStackTrace();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2159 */         if (methodTogetProbeData == null) break label1525;
/*      */       }
/* 2161 */       methodTogetProbeData.releaseConnection();
/*      */     }
/*      */     finally
/*      */     {
/* 2159 */       if (methodTogetProbeData != null)
/*      */       {
/* 2161 */         methodTogetProbeData.releaseConnection(); }
/*      */     }
/*      */     label1525:
/* 2164 */     return isSuccuess;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void makeADummyRequestToTheProbe(String mas_host, String port, String mas_port)
/*      */   {
/* 2173 */     String ticket = EnterpriseUtil.getTicketFromProbe(mas_host, mas_port, "loginuser", "U2FuZ2VldGhN");
/* 2174 */     if ((ticket != null) && (!ticket.trim().equals("")))
/*      */     {
/* 2176 */       String urlToFetchProbeData = ("true".equals(System.getProperty("server.secure")) ? "https://" : "http://") + mas_host + new StringBuilder().append(":").append("true".equals(System.getProperty("server.secure")) ? mas_port : port).toString();
/* 2177 */       String probeUrl = urlToFetchProbeData;
/* 2178 */       urlToFetchProbeData = urlToFetchProbeData + "/skinSelection.do?ticket=" + ticket.trim();
/*      */       
/*      */ 
/* 2181 */       int respCode1 = 0;
/* 2182 */       com.manageengine.appmanager.comm.HTTPResponse ress = null;
/*      */       
/*      */       try
/*      */       {
/* 2186 */         String probeID = EnterpriseUtil.getMasID(mas_host, Integer.parseInt(mas_port));
/* 2187 */         Properties formProps = new Properties();
/* 2188 */         formProps.setProperty("probeId", probeID);
/* 2189 */         ress = EnterpriseUtil.sendAsynchRequestToProbe(urlToFetchProbeData, "Post", formProps, null, 0);
/* 2190 */         respCode1 = ress.getStatusCode();
/* 2191 */         ress.releaseConnection();
/*      */         
/*      */ 
/* 2194 */         if (respCode1 == 200)
/*      */         {
/*      */ 
/*      */ 
/* 2198 */           EnterpriseUtil.probeUserTickets.put(probeUrl.trim(), ticket.trim());
/*      */           
/*      */ 
/* 2201 */           AMLog.debug("Made a dummy request to set the ticket for the probe " + urlToFetchProbeData);
/*      */         }
/*      */         else
/*      */         {
/* 2205 */           AMLog.debug("Made a dummy request to set the ticket but we didn't get the response from the probe");
/*      */         }
/*      */       } catch (Exception e) {
/* 2208 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 2211 */         if (ress != null)
/*      */         {
/* 2213 */           ress.releaseConnection();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static boolean adminAlarmOperations(String url, int responseStatusCode, HttpServletRequest hreq, HttpServletResponse hresponse)
/*      */   {
/*      */     try
/*      */     {
/* 2225 */       if ((url.contains("/fault/AlarmOperations.do")) && (EnterpriseUtil.isAdminServer()))
/*      */       {
/*      */ 
/* 2228 */         AMLog.debug("The response status code AlarmOperations.do " + responseStatusCode);
/* 2229 */         int checkResponse = 200;
/* 2230 */         if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2231 */           checkResponse = 302;
/*      */         }
/* 2233 */         if ((responseStatusCode == checkResponse) && (!"clearAlarm".equals(hreq.getParameter("methodCall"))))
/*      */         {
/* 2235 */           updateAdminAlarmPickupStatus(hreq);
/*      */         }
/* 2237 */         String redirectTo = hreq.getParameter("redirectto");
/* 2238 */         if (("clearAlarm".equals(hreq.getParameter("methodCall"))) && (!"true".equals(hreq.getParameter("bulkmonitor")))) {
/* 2239 */           hresponse.sendRedirect(hresponse.encodeRedirectURL("/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=" + hreq.getParameter("selectedEntity") + "&adminrequest=true&monitortype=" + hreq.getParameter("monitortype")));
/*      */         }
/* 2241 */         if ((redirectTo != null) && (!"null".equalsIgnoreCase(redirectTo)))
/*      */         {
/* 2243 */           hreq.getSession().setAttribute("showAlarmActionStatus", "true");
/* 2244 */           hresponse.sendRedirect(hresponse.encodeRedirectURL(redirectTo));
/*      */         }
/*      */         else
/*      */         {
/* 2248 */           hresponse.sendRedirect(hresponse.encodeRedirectURL(hreq.getRequestURI() + "?" + hreq.getQueryString()));
/*      */         }
/* 2250 */         return true;
/*      */       }
/*      */       
/* 2253 */       if ((url.contains("/fault/AlarmDetails.do")) && (EnterpriseUtil.isAdminServer()))
/*      */       {
/* 2255 */         AMLog.debug("The response status code AlarmDetails.do " + responseStatusCode);
/* 2256 */         String redirect = hreq.getParameter("redirect") != null ? hreq.getParameter("redirect") : "";
/* 2257 */         String method = hreq.getParameter("method") != null ? hreq.getParameter("method") : "false";
/* 2258 */         String redirectTo = hreq.getParameter("redirectto");
/* 2259 */         if (method.equalsIgnoreCase("deleteAnnotation"))
/*      */         {
/* 2261 */           if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2262 */             redirectTo = "/fault" + redirect + "&alertMessage=" + URLEncoder.encode(FormatUtil.getString("webclient.fault.annotationresponse.annotation.delete"), "UTF-8");
/*      */           } else {
/* 2264 */             redirectTo = "/fault" + redirect + "&alertMessage=" + URLEncoder.encode(FormatUtil.getString("webclient.fault.annotationresponse.annotation.delete.apm.message"), "UTF-8");
/*      */           }
/*      */         }
/*      */         
/* 2268 */         if ((method.equalsIgnoreCase("editAnnotation")) || (method.equalsIgnoreCase("setAnnotation")) || (method.equalsIgnoreCase("deleteAnnotation")))
/*      */         {
/* 2270 */           if (redirectTo != null)
/*      */           {
/* 2272 */             hresponse.sendRedirect(hresponse.encodeRedirectURL(redirectTo));
/*      */           }
/*      */           else
/*      */           {
/* 2276 */             hresponse.sendRedirect(hresponse.encodeRedirectURL(hreq.getRequestURI() + "?" + hreq.getQueryString()));
/*      */           }
/*      */           
/* 2279 */           return true;
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 2283 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 2286 */     return false;
/*      */   }
/*      */   
/*      */   private static void updateAdminAlarmPickupStatus(HttpServletRequest hreq)
/*      */   {
/*      */     try
/*      */     {
/* 2293 */       String remoteUser = hreq.getRemoteUser();
/*      */       
/* 2295 */       if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2296 */         remoteUser = remoteUser + "-Central";
/*      */       } else {
/* 2298 */         remoteUser = remoteUser + "-AdminServer";
/*      */       }
/*      */       
/*      */ 
/* 2302 */       String entity = hreq.getParameter("selectedEntity");
/* 2303 */       StringTokenizer tokenize = new StringTokenizer(entity, ",");
/* 2304 */       String methodCall = hreq.getParameter("methodCall");
/*      */       
/* 2306 */       if ((methodCall != null) && (methodCall.equals("pickUpAlarm")))
/*      */       {
/* 2308 */         while (tokenize.hasMoreTokens())
/*      */         {
/* 2310 */           String tempEntity = tokenize.nextToken();
/* 2311 */           AlarmUtil.updateAdminPickupStatus(remoteUser, tempEntity);
/*      */         }
/*      */       }
/*      */       
/* 2315 */       if ((methodCall != null) && (methodCall.equals("unPickAlarm")))
/*      */       {
/* 2317 */         while (tokenize.hasMoreTokens())
/*      */         {
/* 2319 */           String tempEntity = tokenize.nextToken();
/* 2320 */           if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2321 */             AlarmOperationsUtility.getInstance().unPickTheAlert(remoteUser, tempEntity, true);
/*      */           } else {
/* 2323 */             AlarmUtil.updateAdminPickupStatus("NULL", tempEntity);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 2329 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean needFilterForREST(String uri) {
/* 2334 */     if (this.needFilter)
/*      */     {
/* 2336 */       if (((uri.startsWith("/AppManager")) && (!uri.contains("Usergroup/delete")) && ((uri.contains("Ping")) || (uri.contains("AlarmAction")) || (uri.contains("ExecuteAction")) || (uri.contains("ListMonitorData")) || (uri.contains("showPolledData")) || (uri.contains("ManageMonitor")) || (uri.contains("UnmanageMonitor")) || (uri.contains("UnmanageAndResetMonitor")) || (uri.contains("AddMonitor")) || (uri.contains("AddSubGroup")) || (uri.contains("AssociateMonitortoMG")) || (uri.contains("AddMonitorGroup")) || (uri.contains("group/delete")) || (uri.contains("subgroup/delete")) || (uri.contains("SyncMonitors")))) || (uri.contains("removemonitor/group")) || (uri.contains("group/move")) || (uri.contains("group/edit")) || (uri.contains("GetMonitorData")) || (uri.contains("DeleteMonitor")) || (uri.contains("process/add")) || (uri.contains("service/add")) || (uri.contains("getRBMPlaybackStatus")))
/*      */       {
/* 2338 */         return true;
/*      */       }
/*      */       
/*      */ 
/* 2342 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2347 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static HashMap parseDebugInfoURLrequest(HttpServletRequest hreq, ServletRequest request)
/*      */   {
/* 2354 */     String method = hreq.getParameter("method");
/* 2355 */     HashSet<Integer> resourceList = new HashSet();
/* 2356 */     HashMap detailMap = new HashMap();
/* 2357 */     String ipaddress = null;
/* 2358 */     String query = null;
/* 2359 */     ResultSet rs = null;
/* 2360 */     String resID = null;
/* 2361 */     String apikey = null;
/* 2362 */     boolean mysqlargs = false;
/*      */     try {
/* 2364 */       if (("triggerThreadDump".equalsIgnoreCase(method)) || ("showThreadDump".equalsIgnoreCase(method)))
/*      */       {
/* 2366 */         ipaddress = request.getParameter("IP");
/* 2367 */         String pid = request.getParameter("PID");
/* 2368 */         if ((ipaddress != null) && (!ipaddress.equals("null"))) {
/* 2369 */           query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, CollectData where TARGETADDRESS='" + ipaddress + "' and COMPONENTNAME='JDK1.5' and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/* 2370 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2371 */           while (rs.next()) {
/* 2372 */             resID = rs.getString("RESOURCEID");
/* 2373 */             int resid = Integer.parseInt(resID) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE * com.adventnet.appmanager.server.framework.comm.Constants.RANGE + 1;
/* 2374 */             resourceList.add(Integer.valueOf(resid));
/*      */           }
/* 2376 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         
/* 2379 */         if ((pid != null) && (!pid.equals("null"))) {
/* 2380 */           query = "select RESOURCENAME,AM_ManagedObject.RESOURCEID from AM_CONFIGURATION_INFO,AM_ManagedObject where AM_CONFIGURATION_INFO.ATTRIBUTEID=5103 and AM_CONFIGURATION_INFO.LATEST=1  and AM_CONFIGURATION_INFO.CONFVALUE IN(" + pid + ") and AM_CONFIGURATION_INFO.RESOURCEID=AM_ManagedObject.RESOURCEID";
/* 2381 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2382 */           if (rs.next()) {
/* 2383 */             resID = rs.getString("RESOURCEID");
/*      */           }
/* 2385 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 2387 */       } else if (("triggerProcessList".equalsIgnoreCase(method)) || ("showProcessList".equalsIgnoreCase(method)))
/*      */       {
/*      */ 
/* 2390 */         String type = request.getParameter("type");
/* 2391 */         String args = request.getParameter("args");
/* 2392 */         if (args != null) {
/* 2393 */           Hashtable debugParams = (Hashtable)JSONUtil.getObject(args);
/* 2394 */           ipaddress = (String)debugParams.get("ip");
/* 2395 */           apikey = (String)debugParams.get("apikey");
/* 2396 */           detailMap.put("apikey", apikey);
/*      */         } else {
/* 2398 */           ipaddress = request.getParameter("IP");
/*      */         }
/*      */         
/*      */ 
/* 2402 */         if ("mysql".equalsIgnoreCase(type))
/*      */         {
/* 2404 */           if ((ipaddress != null) && (!ipaddress.equals("null"))) {
/* 2405 */             query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, InetService where TARGETADDRESS='" + ipaddress + "' and TYPE='MYSQL-DB-server' and AM_ManagedObject.RESOURCENAME=InetService.NAME";
/* 2406 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 2407 */             while (rs.next()) {
/* 2408 */               resID = rs.getString("RESOURCEID");
/* 2409 */               int resid = Integer.parseInt(resID) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE * com.adventnet.appmanager.server.framework.comm.Constants.RANGE + 1;
/* 2410 */               resourceList.add(Integer.valueOf(resid));
/*      */             }
/* 2412 */             AMConnectionPool.closeStatement(rs);
/* 2413 */             mysqlargs = true;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 2418 */         else if ((ipaddress != null) && (!ipaddress.equals("null"))) {
/* 2419 */           query = "select RESOURCEID from AM_ManagedObject where resourcename='" + ipaddress + "' and TYPE='Ingres'";
/* 2420 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 2421 */           while (rs.next()) {
/* 2422 */             resID = rs.getString("RESOURCEID");
/* 2423 */             int resid = Integer.parseInt(resID) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE * com.adventnet.appmanager.server.framework.comm.Constants.RANGE + 1;
/* 2424 */             resourceList.add(Integer.valueOf(resid));
/*      */           }
/* 2426 */           AMConnectionPool.closeStatement(rs);
/* 2427 */           mysqlargs = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2433 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2436 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2438 */     if (resID != null) {
/* 2439 */       detailMap.put("resID", resID);
/*      */     }
/* 2441 */     detailMap.put("mysqlargs", Boolean.valueOf(mysqlargs));
/* 2442 */     detailMap.put("resourceList", resourceList);
/* 2443 */     return detailMap;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\AAMFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */