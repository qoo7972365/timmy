/*      */ package com.adventnet.appmanager.servlets;
/*      */ 
/*      */ import HTTPClient.HTTPConnection;
/*      */ import HTTPClient.HTTPResponse;
/*      */ import HTTPClient.NVPair;
/*      */ import HTTPClient.URI;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.fault.DependentDeviceUtil;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.comm.EnterpriseCommUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.RealBrowserUtil;
/*      */ import com.adventnet.appmanager.util.SSORestClient;
/*      */ import com.adventnet.appmanager.utils.client.AddMonitorAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.CommonAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.EditMonitorAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.GetCurrentDataUtil;
/*      */ import com.adventnet.appmanager.utils.client.MaintenanceTaskAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.PreRequisitesAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.ProcessAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.ThresholdActionsAPIUtil;
/*      */ import com.adventnet.appmanager.utils.client.URITree;
/*      */ import com.adventnet.appmanager.utils.client.UserConfigurationUtil;
/*      */ import com.manageengine.apm.pns.AMNotificationDevice;
/*      */ import com.manageengine.apm.pns.AMPushNotificationManager;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.client.selenium.servlets.CreateRealBrowserTest;
/*      */ import com.me.apm.server.selenium.datacollection.RealBrowserDC;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import javapns.devices.Device;
/*      */ import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServlet;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ public class APIServlet extends HttpServlet
/*      */ {
/*   60 */   private static String listOfAPIs_URITree = "ApplyLicense,ListAlarms,ListDashboards,ListMonitor,ListMonitorTypes,ListServer,ListAgents,ListActions,LicenseInfo,Ping,ManageMonitor,UnmanageMonitor,CreateTrapAction,AlarmAction,ExecuteAction,GetDowntimeDetails,UnmanageAndResetMonitor,getAllCustomerInfoOnly,getAllCustomersWithSitesAndBSGs,getSingleCustomersWithSitesAndBSGs,getMonitorGroupAvailability";
/*      */   
/*   62 */   private static String restrictedAccessList = "AssociateMonitortoMG,DeleteMaintenanceTask,CreateMaintenanceTask,AddMonitor,EditMonitor,DeleteMonitor,AddSubGroup,AddMonitorGroup,groupdelete,subgroupdelete,GetUserDetails,UnmanageMonitor,ManageMonitor";
/*      */   
/*      */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*   65 */     process(req, resp, true);
/*      */   }
/*      */   
/*      */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*      */   {
/*   70 */     process(req, resp, false);
/*      */   }
/*      */   
/*      */   private void process(HttpServletRequest req, HttpServletResponse resp, boolean isGet)
/*      */   {
/*   75 */     String message = "";
/*   76 */     String errorCode = "";
/*   77 */     String xmlOut = "";
/*      */     try
/*      */     {
/*   80 */       boolean isMultipart = org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(req);
/*   81 */       if (isMultipart)
/*      */       {
/*      */         try
/*      */         {
/*   85 */           new CreateRealBrowserTest().playback(req, resp);
/*   86 */           return;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*   90 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*   94 */       if (req.getRequestURI().toLowerCase().contains("xml")) {
/*   95 */         resp.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       else {
/*   98 */         resp.setContentType("text/plain; charset=UTF-8");
/*      */       }
/*  100 */       PrintWriter out = resp.getWriter();
/*  101 */       resp.setCharacterEncoding("UTF-8");
/*  102 */       String apiKey = req.getParameter("apikey");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  110 */       if (((apiKey == null) || (apiKey.equalsIgnoreCase("null"))) && (CommonAPIUtil.skipValidationForPlugin(req)) && (!CommonAPIUtil.skipRESTAPIKeyValidation(req)))
/*      */       {
/*      */ 
/*      */         try
/*      */         {
/*  115 */           new PluginUtil().firstTimeSync();
/*      */           
/*  117 */           String localHostName = InetAddress.getLocalHost().getCanonicalHostName();
/*  118 */           localHostName = localHostName.endsWith(".") ? localHostName.substring(0, localHostName.length() - 1) : localHostName;
/*  119 */           if ((req.getRemoteHost().equalsIgnoreCase(localHostName)) && (!"false".equals(req.getSession().getAttribute("allow_apireq_once"))))
/*      */           {
/*  121 */             req.getSession().setAttribute("allow_apireq_once", "false");
/*  122 */             xmlOut = validateAndReturnAPIKey(req, resp);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  127 */           e.printStackTrace();
/*  128 */           message = FormatUtil.getString("am.webclient.apikey.wrongkey.message", new String[] { apiKey });
/*  129 */           errorCode = "4004";
/*  130 */           xmlOut = URITree.generateXML(req, resp, message, errorCode);
/*      */         }
/*  132 */         out.println(xmlOut);
/*  133 */         out.close();
/*  134 */         return;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  144 */       if ((apiKey == null) && (!CommonAPIUtil.skipRESTAPIKeyValidation(req)))
/*      */       {
/*  146 */         AMLog.debug("REST API : APIKey null");
/*  147 */         if (CommonAPIUtil.isAPIKeyRequest(req))
/*      */         {
/*  149 */           xmlOut = validateAndReturnAPIKey(req, resp);
/*      */         }
/*      */         else
/*      */         {
/*  153 */           message = FormatUtil.getString("am.webclient.apikey.nokey.message");
/*  154 */           errorCode = "4008";
/*  155 */           xmlOut = URITree.generateXML(req, resp, message, errorCode);
/*      */         }
/*      */       }
/*  158 */       else if ((validateAPIKey(req)) || (CommonAPIUtil.skipRESTAPIKeyValidation(req)))
/*      */       {
/*      */         try {
/*  161 */           HttpServletResponse hresp = resp;
/*  162 */           String crossdomainUrl = req.getHeader("Origin");
/*  163 */           String apmUrl = req.getHeader("Host");
/*  164 */           if ((crossdomainUrl != null) && (!crossdomainUrl.contains(apmUrl))) {
/*  165 */             hresp.addHeader("Access-Control-Allow-Origin", crossdomainUrl);
/*  166 */             hresp.addHeader("Access-Control-Allow-Methods", "GET,OPTIONS");
/*  167 */             hresp.addHeader("Access-Control-Allow-Credentials", "true");
/*  168 */             hresp.addHeader("Access-Control-Max-Age", "60");
/*  169 */             AMLog.debug("CORS HEADER SET for URI  " + req.getRequestURI() + " Access Allowed to " + crossdomainUrl);
/*      */           }
/*      */         }
/*      */         catch (Exception exc) {
/*  173 */           exc.printStackTrace();
/*      */         }
/*  175 */         AMLog.debug("REST API : Validation true");
/*  176 */         xmlOut = validateMethod(req, resp);
/*      */       }
/*      */       else
/*      */       {
/*  180 */         AMLog.debug("REST API : Validation false");
/*  181 */         message = FormatUtil.getString("am.webclient.apikey.wrongkey.message", new String[] { apiKey });
/*  182 */         errorCode = "4004";
/*  183 */         xmlOut = URITree.generateXML(req, resp, message, errorCode);
/*      */       }
/*  185 */       out.println(xmlOut);
/*  186 */       out.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  190 */       e.printStackTrace();
/*      */     }
/*      */     catch (Throwable e1)
/*      */     {
/*  194 */       e1.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private String validateAndReturnAPIKey(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  200 */     String output = "";String responseCode = "4000";
/*  201 */     String protocol = request.getScheme();
/*  202 */     String host = Constants.getAppHostName();
/*  203 */     int port = request.isSecure() ? Integer.parseInt(System.getProperty("ssl.port")) : Integer.parseInt(System.getProperty("webserver.port"));
/*  204 */     boolean isJson = request.getRequestURI().toLowerCase().contains("json");
/*  205 */     String username = request.getParameter("j_username") != null ? request.getParameter("j_username") : "";
/*  206 */     String password = request.getParameter("j_password") != null ? request.getParameter("j_password") : "";
/*      */     
/*  208 */     if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*      */     {
/*      */       try
/*      */       {
/*  212 */         int demoport = Integer.parseInt(System.getProperty("DEMOPORT"));
/*  213 */         if (request.isSecure())
/*      */         {
/*  215 */           demoport = Integer.parseInt(System.getProperty("DEMOSSLPORT"));
/*      */         }
/*  217 */         port = demoport;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  221 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  224 */     String hostPortURL = protocol + "://" + host + ":" + port;
/*  225 */     AMLog.debug("URL To Connect for Authentication:" + hostPortURL);
/*  226 */     String domain_name = null;String user_name = null;
/*  227 */     if (username.contains("/"))
/*      */     {
/*  229 */       user_name = username.substring(username.indexOf("/") + 1);
/*  230 */       domain_name = username.substring(0, username.indexOf("/"));
/*      */     }
/*  232 */     else if (username.contains("\\"))
/*      */     {
/*  234 */       user_name = username.substring(username.indexOf("\\") + 1);
/*  235 */       domain_name = username.substring(0, username.indexOf("\\"));
/*      */     }
/*      */     else
/*      */     {
/*  239 */       user_name = username;
/*      */     }
/*      */     
/*  242 */     if ((domain_name != null) && (ADAuthenticationUtil.getDomainID(domain_name.trim()) == 0)) {
/*  243 */       user_name = username;
/*  244 */       domain_name = null;
/*      */     }
/*      */     
/*  247 */     String domainString = domain_name != null ? "&domain=" + domain_name : "";
/*      */     
/*  249 */     String conUri = "/showapplication.do?method=getUserDetails&username=" + user_name + "&restApi=true&adminServer=" + EnterpriseUtil.isAdminServer() + domainString;
/*  250 */     if (AMAutomaticPortChanger.isSsoEnabled()) {
/*  251 */       hostPortURL = "https://" + AMAutomaticPortChanger.getSSOHost() + ":" + AMAutomaticPortChanger.getSSOPort();
/*  252 */       String serviceUrl = hostPortURL + conUri;
/*  253 */       String serviceTicket = new SSORestClient(user_name, password).generateServiceTicket(serviceUrl);
/*  254 */       conUri = conUri + "&ticket=" + serviceTicket;
/*      */     }
/*      */     
/*      */ 
/*  258 */     if (request.isSecure())
/*      */     {
/*      */       try
/*      */       {
/*  262 */         AMLog.debug("RESTAPI : Autheticator : " + conUri);
/*  263 */         URI uri = new URI(hostPortURL);
/*  264 */         AMLog.debug("RESTAPI : Autheticator hostPortURL : " + hostPortURL);
/*  265 */         HTTPConnection con = new HTTPConnection(uri);
/*  266 */         NVPair nvpair1 = new NVPair("Pragma", "no-cache");
/*  267 */         NVPair nvpair2 = new NVPair("Accept-Encoding", "gzip");
/*  268 */         NVPair nvpair3 = new NVPair("Referer", conUri);
/*  269 */         NVPair[] headers = { nvpair1, nvpair2, nvpair3 };
/*  270 */         HTTPResponse rsp = null;
/*  271 */         con.setContext(System.currentTimeMillis() + "");
/*  272 */         rsp = con.Get(conUri, "", headers);
/*  273 */         byte[] apiResponse = rsp.getData();
/*  274 */         String connResponse = new String(apiResponse);
/*  275 */         AMLog.info("REST API: Authenticator RESPONSE: " + connResponse);
/*  276 */         if ((!connResponse.contains("Authentication:")) && (((connResponse.indexOf("j_security_check") != -1) && (connResponse.indexOf("loginForm") != -1)) || (connResponse.indexOf("Live Demo") != -1)))
/*      */         {
/*  278 */           if (ADAuthenticationUtil.getDomainList().size() > 0)
/*      */           {
/*  280 */             int domainId = domain_name != null ? ADAuthenticationUtil.getDomainID(domain_name) : 0;
/*  281 */             user_name = domainId + "\\" + user_name;
/*      */           }
/*  283 */           AMLog.debug("RESTAPI: Authenticator : username header:" + user_name);
/*      */           
/*  285 */           NVPair nvpair4 = new NVPair("j_username", user_name);
/*  286 */           NVPair nvpair5 = new NVPair("j_password", password);
/*  287 */           NVPair[] formelements = { nvpair4, nvpair5 };
/*  288 */           rsp = con.Get("/j_security_check", formelements, headers);
/*  289 */           apiResponse = rsp.getData();
/*  290 */           connResponse = new String(apiResponse);
/*  291 */           AMLog.info("RESTAPI: Authenticator : connResponse on retry :" + connResponse);
/*      */         }
/*      */         
/*  294 */         if (connResponse.contains("Authentication:"))
/*      */         {
/*  296 */           AMLog.debug("REST API: Authenticator : Conn Response:" + connResponse);
/*  297 */           ArrayList<Hashtable<String, String>> userDetails = new ArrayList();
/*  298 */           Hashtable<String, String> val = new Hashtable();
/*  299 */           String[] responseValues = connResponse.substring(connResponse.indexOf(";") + 1).split(";");
/*  300 */           for (int i = 0; i < responseValues.length; i++)
/*      */           {
/*  302 */             String line = responseValues[i];
/*  303 */             if (line.contains(":"))
/*      */             {
/*  305 */               String[] temp = line.split(":");
/*  306 */               val.put(temp[0], temp[1]);
/*      */             }
/*      */           }
/*  309 */           if ((domain_name != null) && (!"select".equals(domain_name)))
/*      */           {
/*  311 */             val.put("DomainName", domain_name);
/*      */           }
/*  313 */           val.put("BuildNo", FormatUtil.getString("product.build.number"));
/*  314 */           responseCode = connResponse.contains("Authentication:Failure") ? "4008" : responseCode;
/*  315 */           if ((request.getParameter("isAdmin") != null) && (request.getParameter("isAdmin").equalsIgnoreCase("true")))
/*      */           {
/*  317 */             String apiKey = (String)val.get("APIKey");
/*  318 */             AMLog.info("APIKey received : " + apiKey);
/*  319 */             boolean isDelegated = CommonAPIUtil.isDelegatedAdmin(apiKey);
/*  320 */             if ((CommonAPIUtil.isAdminRole(apiKey)) || (isDelegated))
/*      */             {
/*  322 */               val.put("isAdmin", "true");
/*  323 */               val.put("isDelegated", String.valueOf(isDelegated));
/*      */             }
/*      */             else
/*      */             {
/*  327 */               String message = FormatUtil.getString("Only Admins are authorized access.");
/*  328 */               AMLog.debug("REST API: Authenticator : isAdmin : false " + apiKey);
/*  329 */               responseCode = "4008";
/*  330 */               return URITree.generateXML(request, response, message, responseCode);
/*      */             }
/*      */           }
/*      */           
/*  334 */           userDetails.add(val);
/*  335 */           AMLog.info("UserDetails:" + userDetails);
/*  336 */           HashMap<String, Object> result = new HashMap();
/*  337 */           result.put("response-code", responseCode);
/*  338 */           result.put("result", userDetails);
/*  339 */           result.put("uri", request.getRequestURI());
/*  340 */           result.put("nodeName", "UserDetails");
/*  341 */           result.put("skipSorting", Boolean.valueOf(true));
/*  342 */           output = CommonAPIUtil.getOutputAsString(result, isJson);
/*      */         }
/*      */         else
/*      */         {
/*  346 */           AMLog.debug("REST API : The username or password specified is incorrect. Kindly verify it.");
/*  347 */           AMLog.info("REST API : Failed authentication : " + connResponse);
/*  348 */           String message = FormatUtil.getString("am.webclient.api.invalid.login");
/*  349 */           String errorCode = "4008";
/*      */           try
/*      */           {
/*  352 */             output = URITree.generateXML(request, response, message, errorCode);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  356 */             e.printStackTrace();
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  364 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  369 */     AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong. Kindly use secured mode (https) to get the API Key.");
/*  370 */     String message = FormatUtil.getString("Kindly use secured mode (https) to get the API Key ");
/*  371 */     String errorCode = "4008";
/*      */     try
/*      */     {
/*  374 */       output = URITree.generateXML(request, response, message, errorCode);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  378 */       e.printStackTrace();
/*      */     }
/*      */     
/*  381 */     return output;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean validateAPIKey(HttpServletRequest request)
/*      */   {
/*  390 */     return validateAPIKey(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static boolean validateAPIKey(String apiKey)
/*      */   {
/*  395 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  398 */       if (apiKey != null)
/*      */       {
/*  400 */         if (CommonAPIUtil.apikeys.containsKey(apiKey))
/*      */         {
/*  402 */           return true;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  407 */         PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("select * from AM_UserPasswordTable where APIKEY = ?");
/*  408 */         ps.setString(1, apiKey);
/*  409 */         rs = ps.executeQuery();
/*  410 */         if (rs.next())
/*      */         {
/*  412 */           AMLog.info("REST API : Inside validate API Key method for key:" + rs.getString("APIKEY"));
/*  413 */           return true;
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  420 */       ex.printStackTrace();
/*  421 */       AMLog.audit("validateAPIKey ------->" + ex.getMessage());
/*      */     }
/*      */     finally
/*      */     {
/*  425 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  427 */     return false;
/*      */   }
/*      */   
/*      */   public static String validateMethod(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  432 */     AMLog.debug("REST API : Inside validateMethod");
/*  433 */     String uri = request.getRequestURI();
/*  434 */     String[] nodes = uri.split("/");
/*  435 */     String message = "";
/*  436 */     String errorCode = "";
/*  437 */     String outputString = "";
/*  438 */     FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/*  439 */     if ((fd.getUserType().equals("F")) && (!isApiAllowedForFreeEdition(request)))
/*      */     {
/*  441 */       AMLog.debug("REST API : API not supported for Free Edition");
/*  442 */       message = FormatUtil.getString("am.webclient.apikey.wrongedition.message");
/*  443 */       errorCode = "4064";
/*  444 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  450 */         if ((nodes.length >= 3) && (nodes[1].equals("AppManager")) && ((nodes[2].equals("xml")) || (nodes[2].equals("json"))))
/*      */         {
/*  452 */           boolean isJsonFormat = nodes[2].equals("json");
/*  453 */           String method = nodes[3].equals("ApmAdminServices") ? nodes[4] : nodes[3];
/*      */           
/*  455 */           if ((method.equals("group")) || (method.equals("subgroup"))) {
/*  456 */             method = method + nodes[4];
/*      */           }
/*  458 */           outputString = validateAndGatherData(request, response, method, isJsonFormat);
/*      */         }
/*      */         else
/*      */         {
/*  462 */           AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/*  463 */           message = FormatUtil.getString("am.webclient.apikey.wronguri.message");
/*  464 */           errorCode = "4008";
/*  465 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  470 */         e.printStackTrace();
/*  471 */         AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/*  472 */         message = FormatUtil.getString("am.webclient.apikey.wronguri.message");
/*  473 */         errorCode = "4008";
/*  474 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/*  477 */     return outputString;
/*      */   }
/*      */   
/*      */   private static boolean isApiAllowedForFreeEdition(HttpServletRequest req) {
/*  481 */     String uri = req.getRequestURI();
/*      */     
/*      */ 
/*  484 */     if ((uri.contains("License")) || (uri.contains("DeleteMonitor")) || (uri.contains("UnmanageMonitor")) || (PluginUtil.isPlugin())) {
/*  485 */       return true;
/*      */     }
/*  487 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean getOperatorCondition(String methodName)
/*      */   {
/*  492 */     boolean allowManage = false;
/*      */     try {
/*  494 */       if ((methodName.equals("ManageMonitor")) || (methodName.equals("UnmanageMonitor")))
/*      */       {
/*  496 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOperatorManage");
/*      */       }
/*  498 */       else if (methodName.equals("EditMonitor"))
/*      */       {
/*  500 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOperatorEdit");
/*      */       }
/*  502 */       else if (methodName.equals("UnmanageAndResetMonitor"))
/*      */       {
/*  504 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOperatorUnmanageAndReset");
/*      */       }
/*  506 */       else if (methodName.equals("ExecuteAction"))
/*      */       {
/*  508 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOperatorExecuteAction");
/*      */       }
/*  510 */       else if ((methodName.equals("CreateMaintenanceTask")) || (methodName.equals("EditMaintenanceTask")) || (methodName.equals("DeleteMaintenanceTask")) || (methodName.equals("ListMaintenanceTaskDetails")))
/*      */       {
/*  512 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowDownTimeSchedule");
/*      */       }
/*  514 */       else if (methodName.equals("ListMaintenanceTaskDetails"))
/*      */       {
/*  516 */         allowManage = DBUtil.getGlobalConfigValueasBoolean("allowOprViewAllDownTimeSchedule");
/*      */       }
/*      */     } catch (Exception e) {
/*  519 */       e.printStackTrace();
/*      */     }
/*      */     
/*  522 */     return allowManage;
/*      */   }
/*      */   
/*      */   private static String validateAndGatherData(HttpServletRequest request, HttpServletResponse response, String method, boolean isJsonFormat)
/*      */   {
/*  527 */     String outputString = "";String errorCode = "";String message = "";
/*  528 */     boolean isOperatorAllowed = request.isUserInRole("OPERATOR") ? getOperatorCondition(method) : false;
/*      */     
/*      */     try
/*      */     {
/*  532 */       if ((!CommonAPIUtil.skipRESTAPIKeyValidation(request)) && (!CommonAPIUtil.isAdminRole(request)) && (restrictedAccessList.contains(method)) && (!isOperatorAllowed) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*  533 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*  536 */       if (method.equals("DeleteMaintenanceTask"))
/*      */       {
/*  538 */         outputString = MaintenanceTaskAPIUtil.deleteMaintenanceTaskXML(request, response);
/*      */       }
/*  540 */       else if (method.equals("CreateMaintenanceTask"))
/*      */       {
/*  542 */         outputString = MaintenanceTaskAPIUtil.createMaintenanceTaskXML(request, response);
/*      */       }
/*  544 */       else if (method.equals("EditMaintenanceTask"))
/*      */       {
/*  546 */         outputString = MaintenanceTaskAPIUtil.editMaintenanceTaskXML(request, response);
/*      */       }
/*  548 */       else if ((method.equals("GetMonitorData")) || (method.equals("ListMonitorData")))
/*      */       {
/*  550 */         outputString = GetCurrentDataUtil.getCurrentData(request, response);
/*      */       }
/*  552 */       else if (method.equals("AddMonitor"))
/*      */       {
/*  554 */         outputString = AddMonitorAPIUtil.addNewMonitor(request, response);
/*      */       }
/*  556 */       else if (method.equals("credential"))
/*      */       {
/*  558 */         outputString = com.adventnet.appmanager.utils.client.CredentialAPIUtil.credentialAPI(request, response, isJsonFormat);
/*      */       }
/*  560 */       else if (method.equals("EditMonitor"))
/*      */       {
/*  562 */         outputString = EditMonitorAPIUtil.editMonitor(request, response);
/*      */       }
/*  564 */       else if (method.equals("GetQueryOutput"))
/*      */       {
/*  566 */         outputString = CommonAPIUtil.getQueryOutput(request, response);
/*      */       }
/*  568 */       else if (method.equals("AddMonitorGroup"))
/*      */       {
/*  570 */         outputString = CommonAPIUtil.addMonitorGroup(request, response);
/*      */       }
/*  572 */       else if (method.equals("AddSubGroup"))
/*      */       {
/*  574 */         outputString = CommonAPIUtil.AddSubGroup(request, response);
/*      */       }
/*  576 */       else if (method.equals("AssociateMonitortoMG"))
/*      */       {
/*  578 */         outputString = CommonAPIUtil.AssociateMonitorstoGroup(request, response);
/*      */       }
/*  580 */       else if (method.equals("SyncMonitors"))
/*      */       {
/*  582 */         outputString = CommonAPIUtil.SyncMonitors(request, response);
/*      */       }
/*  584 */       else if (method.equals("DeleteMonitor"))
/*      */       {
/*  586 */         outputString = CommonAPIUtil.deleteMonitor(request, response);
/*      */       }
/*  588 */       else if (method.equals("EnableDisableAlarmsAction"))
/*      */       {
/*  590 */         outputString = CommonAPIUtil.enableDisableAlarmsAction(request, response);
/*      */       }
/*  592 */       else if (method.equals("groupdelete"))
/*      */       {
/*  594 */         outputString = CommonAPIUtil.deleteGroup(request, response);
/*      */       }
/*  596 */       else if (method.equals("subgroupdelete"))
/*      */       {
/*  598 */         outputString = CommonAPIUtil.deleteSubGroup(request, response);
/*      */       }
/*  600 */       else if (method.equals("PollNow"))
/*      */       {
/*  602 */         outputString = CommonAPIUtil.pollNow(request, response);
/*      */       }
/*  604 */       else if (method.equals("ListMonitorGroups"))
/*      */       {
/*  606 */         outputString = CommonAPIUtil.getMGList(request, response);
/*      */       }
/*  608 */       else if (method.equals("ListMGDetails"))
/*      */       {
/*  610 */         outputString = CommonAPIUtil.getMGDetails(request, response);
/*      */       }
/*  612 */       else if (method.equals("DomainDetails"))
/*      */       {
/*  614 */         outputString = CommonAPIUtil.getDomainDetails(request, response);
/*      */       }
/*  616 */       else if ((method.equals("GetUserDetails")) || (method.equals("ListUserDetails")))
/*      */       {
/*  618 */         outputString = CommonAPIUtil.getUserDetails(request, response);
/*      */       }
/*  620 */       else if (method.equals("UsergroupDetails"))
/*      */       {
/*  622 */         outputString = CommonAPIUtil.getUsergroupDetails(request, response);
/*      */       }
/*  624 */       else if ((method.equals("GetMaintenanceTaskDetails")) || (method.equals("ListMaintenanceTaskDetails")))
/*      */       {
/*  626 */         outputString = CommonAPIUtil.getDowntimeSchedulesDetail(request, response);
/*      */       }
/*  628 */       else if (method.equals("CheckRecorderCompatibility"))
/*      */       {
/*  630 */         outputString = CommonAPIUtil.getRecorderCompatibility(request, response);
/*      */       }
/*  632 */       else if (listOfAPIs_URITree.contains(method))
/*      */       {
/*  634 */         outputString = URITree.generateXML(request, response, isJsonFormat);
/*      */       }
/*  636 */       else if (method.equalsIgnoreCase("ShowPolledData"))
/*      */       {
/*  638 */         GetCurrentDataUtil Gcd = new GetCurrentDataUtil();
/*  639 */         outputString = Gcd.getHistoryDetails(request, response, isJsonFormat);
/*      */       }
/*  641 */       else if (method.equalsIgnoreCase("Search"))
/*      */       {
/*  643 */         outputString = CommonAPIUtil.searchMO(request, response, isJsonFormat);
/*      */       }
/*  645 */       else if (method.equals("RegisterForPNS"))
/*      */       {
/*  647 */         outputString = RegisterForPNS(request, response, isJsonFormat);
/*      */       }
/*  649 */       else if (method.equals("DeRegisterForPNS"))
/*      */       {
/*  651 */         outputString = DeRegisterForPNS(request, response, isJsonFormat);
/*      */       }
/*  653 */       else if (method.equals("ListManagedServers"))
/*      */       {
/*  655 */         outputString = CommonAPIUtil.getManagedServerList(request, response);
/*      */       }
/*  657 */       else if (method.equals("refreshUserResources"))
/*      */       {
/*  659 */         outputString = CommonAPIUtil.refreshUserResources(request, response);
/*      */       }
/*  661 */       else if (method.equals("diagnosticinfo"))
/*      */       {
/*  663 */         outputString = CommonAPIUtil.diagnosticConfigAPI(request, response, isJsonFormat);
/*  664 */       } else if (method.equals("clearDiagnosticAlert"))
/*      */       {
/*  666 */         outputString = CommonAPIUtil.clearDiagnosticAlert(request, response, isJsonFormat);
/*      */       }
/*  668 */       else if (method.equals("threshold")) {
/*  669 */         outputString = ThresholdActionsAPIUtil.thresholdConfigAPI(request, response, isJsonFormat);
/*      */       }
/*  671 */       else if (method.equals("actions"))
/*      */       {
/*  673 */         String actionName = "actions";
/*  674 */         String[] nodes = request.getRequestURI().split("/");
/*  675 */         if (nodes.length > 4) {
/*  676 */           actionName = nodes[4];
/*      */         }
/*  678 */         if ("emailaction".equals(actionName)) {
/*  679 */           outputString = ThresholdActionsAPIUtil.emailActionAPI(request, response, isJsonFormat);
/*      */         }
/*      */       }
/*  682 */       else if (method.equals("domain")) {
/*  683 */         if ((Constants.isSsoEnabled()) && (EnterpriseUtil.isManagedServer()) && (!CommonAPIUtil.isEESyncRequest(request))) {
/*  684 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.sso.operation.disabled"), "5010");
/*      */         }
/*      */         
/*  687 */         if (!CommonAPIUtil.isAdminRole(request)) {
/*  688 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */         }
/*      */         
/*  691 */         outputString = UserConfigurationUtil.domainConfigAPI(request, response, isJsonFormat);
/*  692 */       } else if (method.equals("businesshours")) {
/*  693 */         outputString = com.adventnet.appmanager.utils.client.BusinessHourAPIUtil.businessHourAPI(request, response, isJsonFormat);
/*  694 */       } else if (method.equals("configurealarms")) {
/*  695 */         outputString = ThresholdActionsAPIUtil.configureThresholdandActions(request, response);
/*      */       }
/*  697 */       else if (method.equals("associatethresholdactions")) {
/*  698 */         outputString = ThresholdActionsAPIUtil.associateThreshold(request, response);
/*      */       }
/*  700 */       else if ("logrule".equals(method))
/*      */       {
/*  702 */         outputString = CommonAPIUtil.eventLogRuleAPI(request, response, isJsonFormat);
/*      */       }
/*  704 */       else if ("logfile".equals(method))
/*      */       {
/*  706 */         outputString = CommonAPIUtil.eventLogFileAPI(request, response, isJsonFormat);
/*      */ 
/*      */       }
/*  709 */       else if (method.equalsIgnoreCase("Users")) {
/*  710 */         if ((Constants.isSsoEnabled()) && (EnterpriseUtil.isManagedServer()) && (!CommonAPIUtil.isEESyncRequest(request))) {
/*  711 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.sso.operation.disabled"), "5010");
/*      */         }
/*      */         
/*  714 */         String action = "usersList";
/*  715 */         String[] nodes = request.getRequestURI().split("/");
/*  716 */         if (nodes.length > 4) {
/*  717 */           action = nodes[4];
/*      */         }
/*      */         
/*  720 */         if (action.equals("create")) {
/*  721 */           outputString = CommonAPIUtil.createUser(request, response);
/*  722 */         } else if (action.equals("delete")) {
/*  723 */           outputString = CommonAPIUtil.deleteUser(request, response);
/*  724 */         } else if (action.equals("update")) {
/*  725 */           outputString = CommonAPIUtil.updateUser(request, response);
/*  726 */         } else if (action.equals("roles")) {
/*  727 */           outputString = UserConfigurationUtil.getAllAPMRoles(request, isJsonFormat);
/*      */         } else {
/*  729 */           outputString = CommonAPIUtil.getUsersList(request, response);
/*      */         }
/*  731 */       } else if (method.equals("Usergroup")) {
/*  732 */         if ((Constants.isSsoEnabled()) && (EnterpriseUtil.isManagedServer()) && (!CommonAPIUtil.isEESyncRequest(request))) {
/*  733 */           return URITree.generateXML(request, response, FormatUtil.getString("am.api.sso.operation.disabled"), "5010");
/*      */         }
/*      */         
/*  736 */         String action = "usersList";
/*  737 */         String[] nodes = request.getRequestURI().split("/");
/*  738 */         if (nodes.length > 4) {
/*  739 */           action = nodes[4];
/*      */         }
/*      */         
/*  742 */         if (action.equals("create")) {
/*  743 */           outputString = CommonAPIUtil.createUsergroup(request, response);
/*  744 */         } else if (action.equals("delete")) {
/*  745 */           outputString = CommonAPIUtil.deleteUsergroup(request, response);
/*  746 */         } else if (action.equals("update")) {
/*  747 */           outputString = CommonAPIUtil.updateUsergroup(request, response);
/*      */         } else {
/*  749 */           outputString = CommonAPIUtil.getUsergroup(request, response);
/*      */         }
/*      */       }
/*  752 */       else if (method.equals("MailServer")) {
/*  753 */         outputString = PreRequisitesAPIUtil.mailSettingsAPI(request, response, isJsonFormat);
/*      */       }
/*  755 */       else if (method.equals("ProxyServer")) {
/*  756 */         outputString = PreRequisitesAPIUtil.proxySettingsAPI(request, response, isJsonFormat);
/*      */       }
/*  758 */       else if (method.equals("SMSServer")) {
/*  759 */         outputString = PreRequisitesAPIUtil.smsSettingsAPI(request, response, isJsonFormat);
/*      */       }
/*  761 */       else if ((method.equals("CustomerSiteHandler")) && (EnterpriseUtil.isIt360MSPManagedServer()))
/*      */       {
/*  763 */         String module = request.getParameter("module");
/*  764 */         if (module.equals("CustomerManagement"))
/*      */         {
/*  766 */           String param = request.getParameter("parameterss");
/*  767 */           JSONObject json = new JSONObject(param);
/*  768 */           String subModule = json.getString("subModule");
/*  769 */           AMLog.debug("[SITE DELETION] subModule " + subModule);
/*  770 */           if ((subModule.equals("addSite")) || (subModule.equals("addCustomer")))
/*      */           {
/*  772 */             errorCode = EnterpriseCommUtil.addCustomerAndSiteInfo(request, response);
/*      */           }
/*  774 */           else if (subModule.equals("deleteSite"))
/*      */           {
/*  776 */             errorCode = EnterpriseCommUtil.deleteSite(request, response, json);
/*      */           }
/*  778 */           else if (subModule.equals("deleteCustomer"))
/*      */           {
/*  780 */             errorCode = EnterpriseCommUtil.deleteCustomer(request, response, json);
/*      */           }
/*  782 */           else if (subModule.equals("addReseller"))
/*      */           {
/*  784 */             errorCode = EnterpriseCommUtil.addResellerInfo(request, response);
/*  785 */             outputString = URITree.generateXML(request, response, message, errorCode);
/*      */           }
/*  787 */           else if ((subModule.equals("updateResellerRebrandInfo")) || (subModule.equals("updateCustomerRebrandInfo")))
/*      */           {
/*  789 */             errorCode = EnterpriseCommUtil.updateRebrandInfo(request, response);
/*  790 */             outputString = URITree.generateXML(request, response, message, errorCode);
/*      */           }
/*  792 */           else if (subModule.equals("deleteReseller"))
/*      */           {
/*  794 */             errorCode = EnterpriseCommUtil.deleteReseller(request, response, json);
/*  795 */             outputString = URITree.generateXML(request, response, message, errorCode);
/*      */           }
/*  797 */           else if (subModule.equals("updateMSPRebrandInfo"))
/*      */           {
/*  799 */             errorCode = EnterpriseCommUtil.updateMSPRebrandInfo(request, response);
/*  800 */             outputString = URITree.generateXML(request, response, message, errorCode);
/*      */           }
/*  802 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*  805 */       else if ((method.equals("CustomerMgmt")) && (EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/*  807 */         AMLog.debug("API Servlet Customer Mgmt Request received");
/*  808 */         outputString = CustomerManagementAPI.getInstance().processAPIRequest(request, response);
/*      */       }
/*  810 */       else if (method.equals("clearAlerts"))
/*      */       {
/*  812 */         outputString = CommonAPIUtil.clearAlerts(request, response);
/*      */       }
/*  814 */       else if (method.equals("removemonitor"))
/*      */       {
/*  816 */         outputString = CommonAPIUtil.removeMonitorFrmMG(request, response);
/*      */       }
/*  818 */       else if (method.equals("groupmove")) {
/*  819 */         outputString = CommonAPIUtil.moveSubGroup(request, response);
/*      */       }
/*  821 */       else if (method.equals("groupedit")) {
/*  822 */         outputString = CommonAPIUtil.editGroup(request, response);
/*      */       }
/*  824 */       else if (method.equals("process")) {
/*  825 */         String action = new String();
/*  826 */         String[] nodes = request.getRequestURI().split("/");
/*  827 */         if (nodes.length > 4) {
/*  828 */           action = nodes[4];
/*      */         }
/*  830 */         if (action.equals("add")) {
/*  831 */           outputString = ProcessAPIUtil.addProcess(request, response);
/*      */         }
/*  833 */         if ("delete".equals(action))
/*      */         {
/*  835 */           outputString = ProcessAPIUtil.deleteProcess(request, response);
/*      */         }
/*  837 */         if ("edit".equals(action))
/*      */         {
/*  839 */           outputString = ProcessAPIUtil.editProcess(request, response);
/*      */         }
/*      */       }
/*  842 */       else if (method.equals("processattributelist")) {
/*  843 */         outputString = ProcessAPIUtil.addThresholdtoProcess(request, response);
/*      */       }
/*  845 */       else if (method.equals("service")) {
/*  846 */         String action = new String();
/*  847 */         String[] nodes = request.getRequestURI().split("/");
/*  848 */         if (nodes.length > 4) {
/*  849 */           action = nodes[4];
/*      */         }
/*  851 */         if (action.equals("add")) {
/*  852 */           outputString = CommonAPIUtil.addService(request, response);
/*      */         }
/*  854 */       } else if (method.equals("globalconfig")) {
/*  855 */         String action = "donothing";
/*  856 */         String[] nodes = request.getRequestURI().split("/");
/*  857 */         if (nodes.length > 4) {
/*  858 */           action = nodes[4];
/*      */         }
/*  860 */         if (action.equals("insert")) {
/*  861 */           outputString = CommonAPIUtil.insertGlobalConfigValue(request, response);
/*  862 */         } else if (action.equals("update")) {
/*  863 */           outputString = CommonAPIUtil.updateGlobalConfigValue(request, response);
/*  864 */         } else if (action.equals("delete")) {
/*  865 */           outputString = CommonAPIUtil.deleteGlobalConfigValue(request, response);
/*      */         } else {
/*  867 */           outputString = FormatUtil.getString("am.webclient.api.rest.invalid.request");
/*      */         }
/*  869 */       } else if (method.equals("ssoprops")) {
/*  870 */         String action = new String();
/*  871 */         String[] nodes = request.getRequestURI().split("/");
/*  872 */         if (nodes.length > 4) {
/*  873 */           action = nodes[4];
/*      */         }
/*  875 */         if (action.equals("update")) {
/*  876 */           outputString = CommonAPIUtil.updateSSOInPropFile(request, response);
/*      */         }
/*  878 */       } else if (method.equals("confFileProps")) {
/*  879 */         String action = new String();
/*  880 */         String[] nodes = request.getRequestURI().split("/");
/*  881 */         if (nodes.length > 4) {
/*  882 */           action = nodes[4];
/*      */         }
/*  884 */         if (action.equals("update")) {
/*  885 */           outputString = CommonAPIUtil.updateConfFileConfigurationInMAS(request, response);
/*      */         }
/*  887 */       } else if (method.equals("serverConfigs")) {
/*  888 */         String action = new String();
/*  889 */         String[] nodes = request.getRequestURI().split("/");
/*  890 */         if (nodes.length > 4) {
/*  891 */           action = nodes[4];
/*      */         }
/*  893 */         if (action.equals("update")) {
/*  894 */           outputString = CommonAPIUtil.updateServerConfigInMAS(request, response);
/*      */         }
/*  896 */       } else if (method.equals("dcComponentsConfigs")) {
/*  897 */         String action = new String();
/*  898 */         String[] nodes = request.getRequestURI().split("/");
/*  899 */         if (nodes.length > 4) {
/*  900 */           action = nodes[4];
/*      */         }
/*  902 */         if (action.equals("update")) {
/*  903 */           outputString = CommonAPIUtil.updatedcComponentStatusInMAS(request, response);
/*      */         }
/*  905 */       } else if (method.equals("perfConfigs")) {
/*  906 */         String action = new String();
/*  907 */         String[] nodes = request.getRequestURI().split("/");
/*  908 */         if (nodes.length > 4) {
/*  909 */           action = nodes[4];
/*      */         }
/*  911 */         if (action.equals("update")) {
/*  912 */           outputString = CommonAPIUtil.updatePerformancePollingDataInMAS(request, response);
/*      */         }
/*  914 */       } else if (method.equals("dbpoll")) {
/*  915 */         outputString = CommonAPIUtil.updateDbPollTable(request, response);
/*      */       }
/*  917 */       else if (method.equals("getRBMPlaybackStatus"))
/*      */       {
/*  919 */         String scriptName = request.getParameter("scriptName").toString();
/*  920 */         RealBrowserDC rbdc = new RealBrowserDC();
/*  921 */         String status = rbdc.getTestPlaybackStatus(scriptName);
/*  922 */         JSONObject resp = new JSONObject();
/*  923 */         resp.put("status", status);
/*  924 */         resp.put("apikey", true);
/*  925 */         resp.put("response-code", "4000");
/*  926 */         outputString = resp.toString();
/*      */       }
/*  928 */       else if (method.equals("modifyRBMScript"))
/*      */       {
/*  930 */         String script = request.getParameter("script");
/*  931 */         script = URLDecoder.decode(script, "UTF-8");
/*  932 */         String resourceId = request.getParameter("resourceid");
/*  933 */         RealBrowserUtil.modiFyRBMScript(resourceId, script);
/*      */       }
/*  935 */       else if (method.equals("isEUMAllowed"))
/*      */       {
/*  937 */         JSONObject resp = new JSONObject();
/*  938 */         resp.put("isRBMAllowed", FreeEditionDetails.getFreeEditionDetails().isEUMAllowed());
/*  939 */         outputString = resp.toString();
/*      */       }
/*  941 */       else if (method.equals("GetRemoteHost"))
/*      */       {
/*  943 */         outputString = getRemoteHostOfScriptMonitor(request);
/*      */       } else {
/*  945 */         if (isJsonFormat)
/*      */         {
/*      */ 
/*  948 */           return validateAndGenerateJSONFeed(request, response, method);
/*      */         }
/*  950 */         if ((EnterpriseUtil.isIt360MSPManagedServer()) && (method.equals("CustomerSiteHandler")))
/*      */         {
/*      */ 
/*  953 */           errorCode = EnterpriseCommUtil.getCustomSiteHandlerResponse(request, response);
/*  954 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*      */         }
/*  956 */         else if ((EnterpriseUtil.isIt360MSPEdition()) && (method.equals("CustomerMgmt")))
/*      */         {
/*      */ 
/*  959 */           AMLog.debug("API Servlet Customer Mgmt Request received");
/*  960 */           outputString = CustomerManagementAPI.getInstance().processAPIRequest(request, response);
/*      */         }
/*      */         else
/*      */         {
/*  964 */           AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/*  965 */           message = FormatUtil.getString("am.webclient.apikey.wronguri.message");
/*  966 */           errorCode = "4008";
/*  967 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  972 */       e.printStackTrace();
/*      */     }
/*  974 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getRemoteHostOfScriptMonitor(HttpServletRequest request)
/*      */   {
/*  982 */     String hostname = "";
/*  983 */     String outputString = "";
/*      */     try
/*      */     {
/*  986 */       String resourceid = request.getParameter("resourceid");
/*  987 */       if (resourceid != null)
/*      */       {
/*  989 */         hostname = FaultUtil.getRemoteHostName(resourceid);
/*      */       }
/*  991 */       ArrayList<Hashtable> output = new ArrayList();
/*  992 */       Hashtable host = new Hashtable();
/*  993 */       host.put("hostname", hostname);
/*  994 */       output.add(host);
/*  995 */       HashMap<String, Object> results = new HashMap();
/*  996 */       results.put("response-code", "4000");
/*  997 */       results.put("uri", request.getRequestURI());
/*  998 */       results.put("result", output);
/*  999 */       outputString = CommonAPIUtil.getOutputAsString(results, true);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1003 */       ex.printStackTrace();
/*      */     }
/* 1005 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String DeRegisterForPNS(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */   {
/* 1011 */     String outputString = null;
/*      */     try
/*      */     {
/* 1014 */       String deviceId = request.getParameter("deviceId");
/* 1015 */       if (deviceId != null)
/*      */       {
/*      */         try
/*      */         {
/* 1019 */           Integer.parseInt(deviceId);
/* 1020 */           Properties deviceProps = new Properties();
/* 1021 */           deviceProps.setProperty("deviceId", deviceId);
/* 1022 */           AMPushNotificationManager.removeDevice(deviceId);
/*      */           
/* 1024 */           ArrayList<Hashtable<String, String>> deviceDetails = new ArrayList();
/* 1025 */           Hashtable<String, String> deviceTable = new Hashtable();
/* 1026 */           deviceTable.put("DeviceId", deviceId);
/*      */           
/*      */ 
/*      */ 
/* 1030 */           deviceDetails.add(deviceTable);
/*      */           
/* 1032 */           HashMap<String, Object> result = new HashMap();
/* 1033 */           result.put("response-code", "4000");
/* 1034 */           result.put("result", deviceDetails);
/* 1035 */           result.put("uri", request.getRequestURI());
/* 1036 */           result.put("nodeName", "DeviceDetails");
/* 1037 */           result.put("skipSorting", Boolean.valueOf(true));
/* 1038 */           outputString = CommonAPIUtil.getOutputAsString(result, isJsonFormat);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1042 */           e.printStackTrace();
/* 1043 */           AMLog.debug("REST API : The specified deviceId " + deviceId + " is not a valid Id.");
/* 1044 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.wrongdeviceid.message"), "4008");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1049 */         AMLog.debug("REST API : The specified deviceId " + deviceId + " is not a valid Id.");
/* 1050 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.wrongdeviceid.message"), "4008");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1055 */       e.printStackTrace();
/*      */     }
/* 1057 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String RegisterForPNS(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */   {
/* 1062 */     String outputString = null;
/*      */     try
/*      */     {
/* 1065 */       String owner = CommonAPIUtil.getOwnerName(request);
/* 1066 */       String deviceId = request.getParameter("deviceId");
/* 1067 */       String deviceToken = request.getParameter("deviceToken");
/* 1068 */       String deviceType = request.getParameter("deviceType");
/* 1069 */       boolean isAndroid = false;
/* 1070 */       if ("android".equalsIgnoreCase(deviceType)) {
/* 1071 */         isAndroid = true;
/*      */       }
/* 1073 */       String enabledNotifications = request.getParameter("enabledNotifications") != null ? request.getParameter("enabledNotifications") : "H1";
/* 1074 */       if ((deviceToken == null) || (deviceToken.toLowerCase().contains("null")))
/*      */       {
/* 1076 */         AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/* 1077 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguri.message"), "4008");
/*      */       }
/*      */       
/*      */ 
/* 1081 */       Properties deviceProps = new Properties();
/* 1082 */       if (deviceId != null)
/*      */       {
/* 1084 */         deviceProps.setProperty("deviceId", deviceId);
/*      */       }
/* 1086 */       deviceProps.setProperty("deviceToken", deviceToken);
/* 1087 */       deviceProps.setProperty("userName", owner);
/* 1088 */       deviceProps.setProperty("isAndroid", Boolean.toString(isAndroid));
/* 1089 */       deviceProps.setProperty("lastRegister", System.currentTimeMillis() + "");
/* 1090 */       deviceProps.setProperty("deviceNotification", enabledNotifications);
/*      */       try
/*      */       {
/* 1093 */         Device device = AMPushNotificationManager.updateDevice(deviceProps);
/*      */         
/* 1095 */         if (device != null)
/*      */         {
/* 1097 */           ArrayList<Hashtable<String, String>> deviceDetails = new ArrayList();
/* 1098 */           Hashtable<String, String> deviceTable = new Hashtable();
/* 1099 */           deviceTable.put("DeviceId", device.getDeviceId());
/* 1100 */           deviceTable.put("DeviceToken", device.getToken());
/* 1101 */           deviceTable.put("UserName", ((AMNotificationDevice)device).getUserName());
/* 1102 */           deviceTable.put("DeviceNotifications", ((AMNotificationDevice)device).getConfiguredNotifications());
/* 1103 */           deviceDetails.add(deviceTable);
/*      */           
/* 1105 */           HashMap<String, Object> result = new HashMap();
/* 1106 */           result.put("response-code", "4000");
/* 1107 */           result.put("result", deviceDetails);
/* 1108 */           result.put("uri", request.getRequestURI());
/* 1109 */           result.put("nodeName", "DeviceDetails");
/* 1110 */           result.put("skipSorting", Boolean.valueOf(true));
/* 1111 */           outputString = CommonAPIUtil.getOutputAsString(result, isJsonFormat);
/*      */         }
/*      */         else
/*      */         {
/* 1115 */           AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/* 1116 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguri.message"), "4008");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (InvalidDeviceTokenFormatException ite)
/*      */       {
/*      */ 
/* 1124 */         AMLog.debug("REST API : The deviceToken specified in the request in not a valid token");
/* 1125 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.invalidToken.message"), "4008");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1131 */       e.printStackTrace();
/*      */     }
/* 1133 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String validateAndGenerateJSONFeed(HttpServletRequest request, HttpServletResponse response, String method)
/*      */   {
/* 1138 */     String outputString = "";
/*      */     try
/*      */     {
/* 1141 */       if (method.equals("latestEvent"))
/*      */       {
/* 1143 */         String params = request.getParameter("entity");
/* 1144 */         boolean getEventForDependency = Boolean.parseBoolean(request.getParameter("getEventForDependency"));
/* 1145 */         if ((params != null) && (!params.equals("")) && (getEventForDependency))
/*      */         {
/* 1147 */           JSONObject toRet = new JSONObject();
/*      */           try
/*      */           {
/* 1150 */             JSONObject obj = new JSONObject(params);
/* 1151 */             String resourceNames = obj.getString("resourceNames");
/* 1152 */             int monitorType = obj.getInt("monitorType");
/* 1153 */             String monitorGroupName = obj.getString("monitorGroupName");
/* 1154 */             int count = obj.getInt("count");
/* 1155 */             String entities = obj.getString("entities");
/* 1156 */             String textToAppend = obj.getString("textToAppend");
/* 1157 */             String resourceIdFromMAS = obj.getString("resourceIdFromMAS");
/* 1158 */             boolean isSuppressAlert = obj.getBoolean("isSuppressAlert");
/* 1159 */             HashMap<String, String> tempMap = DependentDeviceUtil.getInstance().getEventMessage(entities, count, resourceNames, textToAppend, monitorType, monitorGroupName, isSuppressAlert, resourceIdFromMAS);
/* 1160 */             if (tempMap != null)
/*      */             {
/* 1162 */               toRet.put("message", tempMap.get("message"));
/* 1163 */               toRet.put("resourceNames", tempMap.get("resourceNames"));
/* 1164 */               toRet.put("count", tempMap.get("count"));
/* 1165 */               toRet.put("status", "true");
/* 1166 */               toRet.put("isSuppressAlert", tempMap.get("isSuppressAlert"));
/* 1167 */               toRet.put("resourceIDToBeRemoved", tempMap.get("resourceIDToBeRemoved"));
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1172 */             toRet.put("status", false);
/* 1173 */             AMLog.debug("Exception while getting the latest events from Admin Server");
/* 1174 */             e.printStackTrace();
/*      */           }
/* 1176 */           outputString = toRet.toString();
/*      */         }
/*      */         else
/*      */         {
/* 1180 */           String entity = request.getParameter("entity");
/* 1181 */           return new JSONObject(FaultUtil.getLatestEvent(entity)).toString();
/*      */         }
/*      */       }
/* 1184 */       else if (method.equals("checkResourceID"))
/*      */       {
/* 1186 */         String resourceIds = request.getParameter("resourceIds");
/* 1187 */         StringTokenizer tokens = new StringTokenizer(resourceIds, ",");
/* 1188 */         ArrayList<String> list = new ArrayList();
/* 1189 */         while (tokens.hasMoreTokens())
/*      */         {
/* 1191 */           list.add(tokens.nextToken());
/*      */         }
/* 1193 */         if (!list.isEmpty())
/*      */         {
/* 1195 */           outputString = DependentDeviceUtil.getInstance().checkResourceIDAndGetListToBeRemoved(resourceIds, list);
/*      */         }
/*      */       }
/* 1198 */       else if (method.equals("checkDependentDevice"))
/*      */       {
/* 1200 */         String resourceid = request.getParameter("resourceid");
/* 1201 */         HashMap<String, String> depDeviceCheckResult = DependentDeviceUtil.getInstance().getDependentDeviceInfo(resourceid);
/* 1202 */         outputString = new JSONObject(depDeviceCheckResult).toString();
/*      */       }
/* 1204 */       else if (method.equals("adminMonitorGroup"))
/*      */       {
/* 1206 */         String resid = request.getParameter("resId");
/* 1207 */         outputString = CommonAPIUtil.getAdminMG(resid);
/*      */       }
/* 1209 */       else if (method.equals("ExecuteWorkflowTask"))
/*      */       {
/* 1211 */         outputString = com.adventnet.appmanager.utils.client.WorkflowTaskAPIUtil.executeWorkflowTask(request, response);
/*      */       }
/*      */       else
/*      */       {
/* 1215 */         AMLog.debug("REST API : The specified request URI is incorrect:the uri specified is wrong");
/* 1216 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguri.message"), "4008");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1221 */       e.printStackTrace();
/*      */     }
/* 1223 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static HashMap<String, String> isValidAPIKeyinRequest(String apiKey)
/*      */   {
/* 1233 */     HashMap<String, String> errorDetials = null;
/* 1234 */     if ((apiKey == null) || (apiKey.trim().length() == 0)) {
/* 1235 */       errorDetials = new HashMap();
/* 1236 */       errorDetials.put("errorCode", "4008");
/* 1237 */       errorDetials.put("errorDesc", "am.webclient.apikey.nokey.message");
/* 1238 */     } else if (!validateAPIKey(apiKey)) {
/* 1239 */       errorDetials = new HashMap();
/* 1240 */       errorDetials.put("errorCode", "4004");
/* 1241 */       errorDetials.put("errorDesc", "am.webclient.apikey.wrongkey.message");
/* 1242 */     } else if ((!CommonAPIUtil.isAdminRole(apiKey)) && (!CommonAPIUtil.isDelegatedAdmin(apiKey))) {
/* 1243 */       errorDetials = new HashMap();
/* 1244 */       errorDetials.put("errorCode", "4037");
/* 1245 */       errorDetials.put("errorDesc", "am.webclient.api.user.violation");
/*      */     }
/* 1247 */     return errorDetials;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\APIServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */