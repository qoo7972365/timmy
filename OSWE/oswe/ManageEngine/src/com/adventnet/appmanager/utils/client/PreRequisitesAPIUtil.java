/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.server.framework.RepairFatalErrors;
/*     */ import com.adventnet.appmanager.server.framework.statuspoll.Smtp;
/*     */ import com.adventnet.appmanager.struts.actions.AdminActions;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.struts.form.ProxyConfiguration;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.MASSyncUtil;
/*     */ import com.adventnet.appmanager.util.ProxyUtil;
/*     */ import com.adventnet.appmanager.util.SmtpEMailer;
/*     */ import com.adventnet.security.authorization.Coding;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.mock.MockHttpServletRequest;
/*     */ 
/*     */ 
/*     */ public class PreRequisitesAPIUtil
/*     */ {
/*     */   private static AMActionForm amform;
/*  37 */   private static final String CONFIG_FILE = "conf" + File.separator + "SMSServer.conf";
/*     */   
/*     */   public static String mailSettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*     */   {
/*  41 */     if (request.getMethod().equals("GET")) {
/*  42 */       return viewMailSettingsAPI(request, response, isJsonFormat);
/*     */     }
/*     */     
/*  45 */     return updateOrAddMailSettingsAPI(request, response, isJsonFormat);
/*     */   }
/*     */   
/*     */   public static String proxySettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*     */     throws Exception
/*     */   {
/*  51 */     if (request.getMethod().equals("GET")) {
/*  52 */       return viewProxySettingsAPI(request, response, isJsonFormat);
/*     */     }
/*     */     
/*  55 */     return configureProxyServer(request, response, isJsonFormat);
/*     */   }
/*     */   
/*     */   public static String smsSettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*     */     throws Exception
/*     */   {
/*  61 */     if (request.getMethod().equals("GET")) {
/*  62 */       return viewSMSSettingsAPI(request, response, isJsonFormat);
/*     */     }
/*     */     
/*  65 */     return configureSMSServer(request, response, isJsonFormat);
/*     */   }
/*     */   
/*     */ 
/*     */   public static String updateOrAddMailSettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*     */     throws Exception
/*     */   {
/*  72 */     Properties mailProps = new Properties();
/*  73 */     boolean valid = false;boolean smtpStatus = false;boolean insert = true;
/*  74 */     String ParameterNames = "";
/*  75 */     String message = "";String error = "";
/*  76 */     Properties dbProps = SmtpEMailer.getSmtpProperties();
/*     */     
/*     */ 
/*  79 */     if (!CommonAPIUtil.isAdminRole(request)) {
/*  80 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */     }
/*  82 */     for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
/*  83 */       ParameterNames = (String)e.nextElement();
/*  84 */       if (!ParameterNames.equalsIgnoreCase("apikey")) {
/*  85 */         if (((ParameterNames.endsWith("mtpServer")) || (ParameterNames.endsWith("mtpPort"))) && (request.getParameter(ParameterNames).isEmpty()))
/*     */         {
/*  87 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.confmon.jscheck.empty", new String[] { ParameterNames }), "4076");
/*     */         }
/*  89 */         String parameterValue = request.getParameter(ParameterNames);
/*  90 */         if ((("true".equals(request.getParameter("fromAdminServer"))) && ("smtpPassword".equals(ParameterNames))) || ("secSmtpPassword".equals(ParameterNames))) {
/*  91 */           parameterValue = Coding.convertFromBase(parameterValue);
/*     */         }
/*  93 */         mailProps.setProperty(ParameterNames, parameterValue);
/*     */       }
/*     */     }
/*  96 */     if (mailProps.size() < 1) {
/*  97 */       return URITree.generateXML(request, response, FormatUtil.getString("mailserver.config.valid.parameters.failed"), "4217");
/*     */     }
/*  99 */     if ((dbProps.size() == 0) && 
/* 100 */       (mailProps.getProperty("smtpServer") == null)) {
/* 101 */       if (mailProps.getProperty("secSmtpServer") != null) {
/* 102 */         return URITree.generateXML(request, response, FormatUtil.getString("mailserver.config.secondary.update.failure"), "4071");
/*     */       }
/* 104 */       return URITree.generateXML(request, response, FormatUtil.getString("mailserver.config.update.failure"), "4071");
/*     */     }
/*     */     try
/*     */     {
/* 108 */       if (!SmtpEMailer.isSMTPConfigured(1)) {
/* 109 */         valid = isValidSmtpProps(mailProps, 1);
/* 110 */         RepairFatalErrors.updateMailServerConfig(mailProps.getProperty("smtpServer"), mailProps.getProperty("smtpPort") + "", mailProps.getProperty("smtpUserName"), mailProps.getProperty("smtpPassword"));
/* 111 */         insert = true;
/*     */       }
/*     */       else {
/* 114 */         dbProps.putAll(mailProps);
/* 115 */         valid = isValidSmtpProps(dbProps, 1);
/* 116 */         if ((mailProps.containsKey("smtpServer")) || (mailProps.containsKey("smtpPort"))) {
/* 117 */           RepairFatalErrors.updateMailServerConfig(dbProps.getProperty("smtpServer"), dbProps.getProperty("smtpPort") + "", dbProps.getProperty("smtpUserName"), dbProps.getProperty("smtpPassword"));
/*     */         }
/* 119 */         insert = false;
/*     */       }
/* 121 */       if (valid) {
/* 122 */         SmtpEMailer.updateSmtpProperties(mailProps, 1, insert);
/* 123 */         smtpStatus = true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 127 */       return URITree.generateXML(request, response, FormatUtil.getString("mailserver.config.updation.primary.failed") + " " + FormatUtil.getString("mailserver.config.errormsg1"), "4074");
/*     */     }
/* 129 */     if (((dbProps.size() == 0) && (mailProps.getProperty("secSmtpServer") != null)) || ((dbProps.size() != 0) && ((mailProps.getProperty("secSmtpServer") != null) || ("true".equals(dbProps.getProperty("secSmtpStatus")))))) {
/* 130 */       valid = false;
/*     */       try {
/* 132 */         if (!SmtpEMailer.isSMTPConfigured(2)) {
/* 133 */           valid = isValidSmtpProps(mailProps, 2);
/* 134 */           if (!smtpStatus) {
/* 135 */             RepairFatalErrors.updateMailServerConfig(mailProps.getProperty("secSmtpServer"), mailProps.getProperty("secSmtpPort") + "", mailProps.getProperty("secSmtpUserName"), mailProps.getProperty("secSmtpPassword"));
/*     */           }
/* 137 */           insert = true;
/*     */         }
/*     */         else {
/* 140 */           dbProps.putAll(mailProps);
/* 141 */           valid = isValidSmtpProps(dbProps, 2);
/* 142 */           if ((!smtpStatus) && ((mailProps.containsKey("secSmtpServer")) || (mailProps.containsKey("secSmtpPort")))) {
/* 143 */             RepairFatalErrors.updateMailServerConfig(dbProps.getProperty("secSmtpServer"), dbProps.getProperty("secSmtpPort") + "", dbProps.getProperty("secSmtpUserName"), dbProps.getProperty("smtpPassword"));
/*     */           }
/* 145 */           insert = false;
/*     */         }
/* 147 */         if (valid) {
/* 148 */           SmtpEMailer.updateSmtpProperties(mailProps, 2, insert);
/* 149 */           if (!smtpStatus) {
/* 150 */             smtpStatus = true;
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 155 */         return URITree.generateXML(request, response, FormatUtil.getString("mailserver.config.updation.second.failed") + " " + FormatUtil.getString("mailserver.config.errormsg1"), "4074");
/*     */       }
/*     */     }
/* 158 */     if (smtpStatus) {
/* 159 */       ((Hashtable)request.getSession().getServletContext().getAttribute("globalconfig")).put("mailserverconfigured", "true");
/* 160 */       Constants.putGlobalObject("SMTP", "true");
/* 161 */       return viewMailSettingsAPI(request, response, isJsonFormat);
/*     */     }
/*     */     
/* 164 */     return URITree.generateXML(request, response, error, "4444");
/*     */   }
/*     */   
/*     */   public static boolean isValidSmtpProps(Properties mailProps, int id) throws Exception
/*     */   {
/* 169 */     boolean ssl = false;boolean tls = false;
/* 170 */     if (id == 1) {
/* 171 */       tls = "true".equals(mailProps.getProperty("prmTlsAuth"));
/* 172 */       if (!tls) {
/* 173 */         ssl = "true".equals(mailProps.getProperty("prmSslAuth"));
/* 174 */         if (ssl) {
/* 175 */           Smtp smtp = new Smtp(mailProps.getProperty("smtpServer"), Integer.parseInt(mailProps.getProperty("smtpPort")));
/* 176 */           smtp.connectThroughSSLFactory();
/* 177 */           return true;
/*     */         }
/*     */         
/* 180 */         Smtp smtp = new Smtp();
/* 181 */         smtp.connect(mailProps.getProperty("smtpServer"), Integer.parseInt(mailProps.getProperty("smtpPort")));
/* 182 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 186 */       return true;
/*     */     }
/*     */     
/* 189 */     if (id == 2) {
/* 190 */       tls = "true".equals(mailProps.getProperty("secTlsAuth"));
/* 191 */       if (!tls) {
/* 192 */         ssl = "true".equals(mailProps.getProperty("secSslAuth"));
/* 193 */         if (ssl) {
/* 194 */           Smtp smtp = new Smtp(mailProps.getProperty("secSmtpServer"), Integer.parseInt(mailProps.getProperty("secSmtpPort")));
/* 195 */           smtp.connectThroughSSLFactory();
/* 196 */           return true;
/*     */         }
/*     */         
/* 199 */         Smtp smtp = new Smtp();
/* 200 */         smtp.connect(mailProps.getProperty("secSmtpServer"), Integer.parseInt(mailProps.getProperty("secSmtpPort")));
/* 201 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 205 */       return true;
/*     */     }
/*     */     
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   public static String viewMailSettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*     */   {
/* 213 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 214 */     String ParameterNames = null;
/* 215 */     Properties mailProps = new Properties();
/* 216 */     for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
/* 217 */       ParameterNames = (String)e.nextElement();
/* 218 */       if (!ParameterNames.equalsIgnoreCase("apikey")) {
/* 219 */         mailProps.setProperty(ParameterNames, request.getParameter(ParameterNames));
/*     */       }
/*     */     }
/* 222 */     if (usersList.size() == 0) {
/* 223 */       String query = "select ID,HOST,PORT,USERNAME," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD,TLSEnabled ,SSLEnabled  from AM_MAILSETTINGS";
/* 224 */       ResultSet set = null;
/*     */       try {
/* 226 */         set = AMConnectionPool.executeQueryStmt(query);
/* 227 */         while (set.next()) {
/* 228 */           Hashtable<String, String> smtpDetails = new Hashtable();
/* 229 */           int id = set.getInt("ID");
/* 230 */           if (id == 1) {
/* 231 */             smtpDetails.put("type", "primary");
/*     */           }
/*     */           else {
/* 234 */             smtpDetails.put("type", "secondary");
/*     */           }
/* 236 */           smtpDetails.put("hostName", set.getString("HOST"));
/* 237 */           smtpDetails.put("port", set.getString("PORT"));
/* 238 */           smtpDetails.put("userName", set.getString("USERNAME") == null ? "" : set.getString("USERNAME"));
/*     */           
/* 240 */           if (set.getBytes("PASSWORD") != null) {
/* 241 */             smtpDetails.put("password", new String(set.getBytes("PASSWORD")));
/*     */           }
/* 243 */           smtpDetails.put("tlsAuth", set.getInt("TLSEnabled") == 1 ? "true" : "false");
/* 244 */           smtpDetails.put("sslAuth", set.getInt("SSLEnabled") == 1 ? "true" : "false");
/* 245 */           if (id == 1) {
/* 246 */             String globalEmail = DBUtil.getGlobalConfigValue("GlobalEMailAddress");
/* 247 */             globalEmail = (globalEmail != null) && (!globalEmail.equalsIgnoreCase("null")) ? globalEmail : "";
/* 248 */             smtpDetails.put("emailAddress", DBUtil.getGlobalConfigValue("GlobalEMailAddress"));
/*     */           }
/*     */           else {
/* 251 */             String secGlobalEmail = DBUtil.getGlobalConfigValue("SecGlobalEMailAddress");
/* 252 */             secGlobalEmail = (secGlobalEmail != null) && (!secGlobalEmail.equalsIgnoreCase("null")) ? secGlobalEmail : "";
/* 253 */             smtpDetails.put("emailAddress", DBUtil.getGlobalConfigValue("SecGlobalEMailAddress"));
/*     */           }
/* 255 */           Hashtable msg = new Hashtable();
/* 256 */           usersList.add(smtpDetails);
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 260 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 263 */         AMConnectionPool.closeStatement(set);
/*     */       }
/*     */     }
/* 266 */     String uri = request.getRequestURI();
/* 267 */     HashMap results = new HashMap();
/* 268 */     results.put("response-code", "4000");
/* 269 */     results.put("uri", uri);
/* 270 */     results.put("result", usersList);
/* 271 */     results.put("sortingParam", "type");
/* 272 */     results.put("parentNode", "MailServers");
/* 273 */     results.put("nodeName", "MailServer");
/* 274 */     return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*     */   }
/*     */   
/*     */   public static void synchMailSettingstoMAS(Properties prop, boolean updatesecServer) {
/*     */     try {
/* 279 */       HashMap<String, String> mailSettings = new HashMap();
/* 280 */       mailSettings.put("fromAdminServer", "true");
/* 281 */       Enumeration e = prop.propertyNames();
/* 282 */       while (e.hasMoreElements()) {
/* 283 */         String key = (String)e.nextElement();
/* 284 */         if ((updatesecServer) || (!key.startsWith("sec")))
/*     */         {
/*     */ 
/* 287 */           String value = prop.getProperty(key);
/* 288 */           if (("smtpPassword".equals(key)) || ("secSmtpPassword".equals(key))) {
/* 289 */             value = Coding.convertToNewBase(value);
/*     */           }
/* 291 */           mailSettings.put(key, value);
/*     */         } }
/* 293 */       MASSyncUtil.addTasktoSync(mailSettings, "/AppManager/xml/MailServer", "all", "post", 8, 1);
/*     */     } catch (Exception ex) {
/* 295 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static Properties getSmtpPropsFromActionform(AMActionForm amform) {
/* 300 */     Properties smtpDetails = new Properties();
/* 301 */     smtpDetails.put("smtpServer", amform.getSmtpserver());
/* 302 */     smtpDetails.put("smtpPort", Integer.toString(amform.getSmtpport()));
/* 303 */     smtpDetails.put("smtpUserName", amform.getSMTPServerUserName());
/* 304 */     smtpDetails.put("smtpPassword", amform.getSMTPServerPassword());
/* 305 */     smtpDetails.put("prmTlsAuth", String.valueOf(amform.getPrmTlsAuth()));
/* 306 */     smtpDetails.put("prmSslAuth", String.valueOf(amform.getPrmSSLAuth()));
/* 307 */     smtpDetails.put("smtpEmail", amform.getEmailAddress());
/* 308 */     smtpDetails.put("secSmtpServer", amform.getSmtpsecserver());
/* 309 */     smtpDetails.put("secSmtpPort", Integer.toString(amform.getSmtpsecport()));
/* 310 */     smtpDetails.put("secSmtpUserName", amform.getSMTPsecServerUserName());
/* 311 */     smtpDetails.put("secSmtpPassword", amform.getSMTPsecServerPassword());
/* 312 */     smtpDetails.put("secTlsAuth", String.valueOf(amform.getSecTlsAuth()));
/* 313 */     smtpDetails.put("secSslAuth", String.valueOf(amform.getSecSSLAuth()));
/* 314 */     smtpDetails.put("secSmtpEmail", amform.getSecemailAddress());
/* 315 */     return smtpDetails;
/*     */   }
/*     */   
/*     */   public static String configureProxyServer(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 319 */     ProxyConfiguration proxyconf = new ProxyConfiguration();
/* 320 */     String host = request.getParameter("host");
/* 321 */     String port = request.getParameter("port");
/* 322 */     String userId = request.getParameter("username");
/* 323 */     String password = request.getParameter("password");
/* 324 */     Properties proxyProps = null;
/*     */     
/* 326 */     String useproxy = request.getParameter("useproxy");
/* 327 */     boolean isBypassProxy = true;
/* 328 */     String dontProxyList = "127.0.0.1;";
/* 329 */     String nullError = "";
/* 330 */     String proxyMessage = "";
/* 331 */     boolean add = true;
/* 332 */     proxyProps = ProxyUtil.getProxyProps();
/* 333 */     if (proxyProps.size() < 1) {
/* 334 */       add = true;
/*     */     }
/* 336 */     proxyProps.putAll(ProxyUtil.getProxyProps(true));
/* 337 */     if ((useproxy == null) || (useproxy.length() < 1)) {
/* 338 */       useproxy = proxyProps.getProperty("useproxy");
/*     */     }
/* 340 */     proxyProps.setProperty("useproxy", useproxy);
/* 341 */     if ("true".equals(useproxy.toLowerCase())) {
/* 342 */       if (add) {
/* 343 */         if ((host == null) || (host.length() < 1)) {
/* 344 */           nullError = nullError + FormatUtil.getString("am.webclient.api.host.notemptymessage") + " ";
/*     */         }
/* 346 */         if ((port == null) || (port.length() < 1)) {
/* 347 */           nullError = nullError + FormatUtil.getString("am.webclient.api.port.notemptymessage") + " ";
/*     */         }
/* 349 */         if (nullError.length() > 0) {
/* 350 */           return URITree.generateXML(request, response, nullError, "4071");
/*     */         }
/*     */       }
/* 353 */       if (((userId != null) && (userId.length() > 1)) || ((password != null) && (password.length() < 1))) {
/* 354 */         proxyProps.setProperty("updateCredentials", "true");
/*     */       }
/*     */       else {
/* 357 */         proxyProps.setProperty("updateCredentials", "false");
/*     */       }
/* 359 */       String ParameterNames = "";
/* 360 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*     */       {
/* 362 */         ParameterNames = (String)e.nextElement();
/* 363 */         if (!ParameterNames.equalsIgnoreCase("apikey")) {
/* 364 */           proxyProps.setProperty(ParameterNames, request.getParameter(ParameterNames));
/* 365 */           request.setAttribute(ParameterNames, request.getParameter(ParameterNames));
/*     */         }
/*     */       }
/* 368 */       isBypassProxy = "true".equals(request.getParameter("bypassproxy"));
/* 369 */       dontProxyList = request.getParameter("dontProxyList");
/* 370 */       if ((dontProxyList == null) || (dontProxyList.length() < 1)) {
/* 371 */         dontProxyList = "127.0.0.1;";
/*     */       }
/* 373 */       proxyProps.setProperty("bypassproxy", Boolean.toString(isBypassProxy));
/* 374 */       proxyProps.setProperty("dontProxyList", dontProxyList);
/*     */     }
/*     */     else
/*     */     {
/* 378 */       boolean proxyResult = ProxyUtil.setProxyConfiguration(request, proxyProps);
/* 379 */       if (proxyResult) {
/* 380 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.autosettings"), "4000");
/*     */       }
/*     */       
/* 383 */       return URITree.generateXML(request, response, FormatUtil.getString("proxyconf.failure"), "4605");
/*     */     }
/*     */     
/* 386 */     if ((host != null) && (host.length() >= 1) && 
/* 387 */       (!proxyconf.validHost(request.getParameter("host"))))
/*     */     {
/* 389 */       return URITree.generateXML(request, response, FormatUtil.getString("error.host.required"), "4217");
/*     */     }
/*     */     
/* 392 */     if ((port != null) && (port.length() >= 1)) {
/*     */       try
/*     */       {
/* 395 */         portNumber = Integer.parseInt(request.getParameter("port"));
/*     */       }
/*     */       catch (Exception ex) {
/*     */         int portNumber;
/* 399 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*     */       }
/*     */     }
/* 402 */     boolean proxyResult = ProxyUtil.setProxyConfiguration(request, proxyProps);
/* 403 */     if (proxyResult) {
/* 404 */       return viewProxySettingsAPI(request, response, isJsonFormat);
/*     */     }
/*     */     
/* 407 */     return URITree.generateXML(request, response, FormatUtil.getString("proxyconf.failure"), "4073");
/*     */   }
/*     */   
/*     */   public static String viewProxySettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*     */   {
/* 412 */     boolean configured = true;
/* 413 */     Properties proxyProps = ProxyUtil.getProxyProps();
/* 414 */     if (proxyProps.size() < 1) {
/* 415 */       configured = false;
/*     */     }
/* 417 */     proxyProps.putAll(ProxyUtil.getProxyProps(true));
/* 418 */     boolean useproxy = "true".equals(proxyProps.getProperty("useproxy"));
/* 419 */     if (!useproxy) {
/* 420 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.proxy.autoconfigured"), "4000");
/*     */     }
/* 422 */     if (!configured) {
/* 423 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.proxy.notconfigured"), "4072");
/*     */     }
/* 425 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 426 */     Hashtable msg = new Hashtable();
/* 427 */     for (String name : proxyProps.stringPropertyNames()) {
/* 428 */       msg.put(name, proxyProps.getProperty(name));
/*     */     }
/* 430 */     usersList.add(msg);
/* 431 */     String uri = request.getRequestURI();
/* 432 */     HashMap results = new HashMap();
/* 433 */     results.put("response-code", "4000");
/* 434 */     results.put("uri", uri);
/* 435 */     results.put("result", usersList);
/* 436 */     results.put("parentNode", "proxyServer");
/* 437 */     results.put("nodeName", "ProxyServer");
/* 438 */     return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*     */   }
/*     */   
/*     */   public static String configureSMSServer(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 442 */     AdminActions adminActions = new AdminActions();
/* 443 */     amform = new AMActionForm();
/* 444 */     ActionMapping actionMap = new ActionMapping();
/* 445 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 446 */     MSreq.setContentType("text/xml; charset=UTF-8");
/* 447 */     MSreq.addParameter("OK", "OK");
/* 448 */     String port = request.getParameter("SMSPort");
/*     */     
/* 450 */     if (!CommonAPIUtil.isAdminRole(request)) {
/* 451 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */     }
/* 453 */     if (System.getProperty("os.name").startsWith("Linux"))
/*     */     {
/* 455 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclinet.smsmodemrules.windowscheck"), "4212");
/*     */     }
/* 457 */     if ((port == null) || (port.length() < 1)) {
/* 458 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*     */     }
/*     */     
/* 461 */     MSreq.addParameter("SMSPort", port);
/*     */     try
/*     */     {
/* 464 */       String message = null;
/* 465 */       ActionForward af = adminActions.SMSServerConfiguration(actionMap, amform, MSreq, response);
/* 466 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.smsserver.restart"), "4000");
/*     */     }
/*     */     catch (Exception e) {}
/* 469 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.smsserver.restart"), "4075");
/*     */   }
/*     */   
/*     */   public static String viewSMSSettingsAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*     */   {
/* 474 */     AdminActions adminAct = new AdminActions();
/* 475 */     Properties modemProps = new Properties();
/* 476 */     modemProps.load(new FileInputStream(CONFIG_FILE));
/* 477 */     String port = modemProps.getProperty("serial.port");
/* 478 */     Properties smsProps = adminAct.modemDetails(port, request);
/*     */     try {
/* 480 */       ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 481 */       Hashtable msg = new Hashtable();
/* 482 */       for (String name : smsProps.stringPropertyNames()) {
/* 483 */         if (!"SignalImage".equals(name)) {
/* 484 */           msg.put(name, smsProps.getProperty(name));
/*     */         }
/*     */       }
/* 487 */       if (msg.size() < 1) {
/* 488 */         return URITree.generateXML(request, response, FormatUtil.getString("am.serverdown.modemmessage.text"), "4075");
/*     */       }
/* 490 */       msg.put("port", port);
/* 491 */       usersList.add(msg);
/* 492 */       String uri = request.getRequestURI();
/* 493 */       HashMap results = new HashMap();
/* 494 */       results.put("response-code", "4000");
/* 495 */       results.put("uri", uri);
/* 496 */       results.put("result", usersList);
/* 497 */       results.put("parentNode", "SMSServer");
/* 498 */       results.put("nodeName", "SMSServer");
/* 499 */       return CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*     */     }
/*     */     catch (Exception e) {}
/* 502 */     return URITree.generateXML(request, response, FormatUtil.getString("am.serverdown.modemmessage.text"), "4075");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\PreRequisitesAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */