/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.PasswordDecoder;
/*     */ import com.adventnet.nms.commonbe.GenericBEAPI;
/*     */ import com.adventnet.nms.commonbe.GenericBEAPIImpl;
/*     */ import com.adventnet.nms.util.PureServerUtilsFE;
/*     */ import com.adventnet.tools.prevalent.Wield;
/*     */ import com.sun.net.ssl.internal.ssl.Provider;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.security.Security;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class FailOverHelperServlet
/*     */   extends HttpServlet
/*     */ {
/*  42 */   private String configuredUserName = "admin";
/*  43 */   private String confiuredPassword = "opmanager";
/*  44 */   private static Map<String, Long> filenameVsLastModifiedtime = new HashMap();
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*     */   {
/*  48 */     ServletOutputStream out = response.getOutputStream();
/*  49 */     response.setContentType("text/html");
/*  50 */     String operation = request.getParameter("operation");
/*  51 */     AMLog.debug(" Requested operation is  :: " + operation);
/*  52 */     out.println(operation);
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*     */   {
/*  57 */     ServletOutputStream out = response.getOutputStream();
/*  58 */     response.setContentType("text/html");
/*  59 */     String operation = request.getParameter("operation");
/*  60 */     AMLog.debug(" Requested operation is  :: " + operation);
/*  61 */     if (operation != null)
/*     */     {
/*  63 */       GenericBEAPI api = GenericBEAPIImpl.getInstance();
/*  64 */       if (operation.equals("checkheartbeat"))
/*     */       {
/*     */ 
/*  67 */         long heartBeat = api.checkHeartBeat();
/*  68 */         System.setProperty("secondaryconnecttime", "" + System.currentTimeMillis());
/*  69 */         AMLog.debug("HeartBeat Interval from FailOverHelper .... " + heartBeat);
/*  70 */         out.println(heartBeat);
/*     */       }
/*  72 */       else if (operation.equals("copyfile"))
/*     */       {
/*  74 */         long prevMODtime = 0L;
/*  75 */         boolean isFileModified = true;
/*  76 */         String filename = request.getParameter("fileName");
/*     */         try {
/*  78 */           long lastMODtime = new File(filename).lastModified();
/*  79 */           if (filenameVsLastModifiedtime.containsKey(filename))
/*     */           {
/*  81 */             prevMODtime = ((Long)filenameVsLastModifiedtime.get(filename)).longValue();
/*  82 */             if (lastMODtime == prevMODtime) {
/*  83 */               isFileModified = false;
/*     */             }
/*     */           }
/*  86 */           filenameVsLastModifiedtime.put(filename, Long.valueOf(lastMODtime));
/*  87 */           if (isFileModified) {
/*  88 */             AMLog.debug("copyFile from FailOverHelper .... " + filename);
/*  89 */             if (filename != null)
/*     */             {
/*  91 */               copyFile(response.encodeURL(filename), response);
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  96 */           AMLog.debug("Exception while copying the file :  " + e.getMessage());
/*     */         }
/*     */       }
/*  99 */       else if (operation.equals("getfailover"))
/*     */       {
/* 101 */         Properties proplic = new Properties();
/* 102 */         boolean isEnabled = true;
/*     */         try
/*     */         {
/* 105 */           Wield wd = Wield.getInstance();
/* 106 */           proplic = wd.getModuleProperties("ServerFramework");
/* 107 */           if (proplic != null)
/*     */           {
/* 109 */             String str = (String)proplic.get("BEFailOver");
/* 110 */             if (str != null)
/*     */             {
/* 112 */               if (str.equalsIgnoreCase("false"))
/*     */               {
/* 114 */                 isEnabled = false;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 121 */           e.printStackTrace();
/*     */         }
/*     */         
/* 124 */         AMLog.debug("FailOver Interval from FailOverHelper .... " + isEnabled);
/* 125 */         out.println(isEnabled);
/*     */       }
/* 127 */       else if (operation.equals("registerstandy"))
/*     */       {
/* 129 */         boolean allow = authenticateTheUser(request, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 134 */         String standbyProps = request.getParameter("standbyprops");
/* 135 */         Properties standByProps = parseProperties(standbyProps);
/* 136 */         String standbyID = api.registerForStandByServer(standByProps);
/* 137 */         out.println(standbyID);
/*     */       }
/* 139 */       else if (operation.equals("listdirectory"))
/*     */       {
/* 141 */         String rootDirectory = request.getParameter("rootDirectory");
/* 142 */         String currentDirectory = ".";
/* 143 */         String absolutePath = new File(currentDirectory).getAbsolutePath();
/* 144 */         absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
/* 145 */         String dirAbsolutePath = new File(rootDirectory).getAbsolutePath();
/* 146 */         AMLog.debug("Directory to List actual absolutePath::::" + absolutePath + " from url absolutePath :::" + dirAbsolutePath);
/* 147 */         if ((!dirAbsolutePath.startsWith(absolutePath)) || (rootDirectory.indexOf("..\\") != -1) || (rootDirectory.indexOf("../") != -1))
/*     */         {
/* 149 */           AMLog.debug("Directory path is not allowed to copy");
/* 150 */           return;
/*     */         }
/*     */         
/* 153 */         if (rootDirectory != null)
/*     */         {
/* 155 */           File file = new File(rootDirectory);
/* 156 */           String AbsolutePath = file.getAbsolutePath();
/* 157 */           out.println(AbsolutePath);
/* 158 */           getFilesListInDirectory(rootDirectory, out);
/*     */         }
/*     */         
/*     */       }
/* 162 */       else if (operation.equals("unregisterstandby"))
/*     */       {
/* 164 */         boolean allow = authenticateTheUser(request, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 171 */           String standbyID = request.getParameter("standbyID");
/* 172 */           api.unregisterForStandByServer(standbyID);
/*     */ 
/*     */ 
/*     */         }
/*     */         catch (Exception ex) {}
/*     */ 
/*     */       }
/* 179 */       else if (operation.equals("requestToPutTruststore"))
/*     */       {
/* 181 */         String serverHost = request.getParameter("serverhost");
/* 182 */         String serverPort = request.getParameter("serverport");
/* 183 */         String webProtocol = request.getParameter("webprotocol");
/* 184 */         AMLog.debug(" Request operation is requestToPutTruststore serverHost : " + serverHost + " serverPort : " + serverPort);
/* 185 */         boolean isHttps = true;
/* 186 */         if (webProtocol != null)
/*     */         {
/* 188 */           isHttps = webProtocol.equalsIgnoreCase("https");
/*     */         }
/* 190 */         String fileName = "conf" + File.separator + "https.truststore";
/* 191 */         backUpTruststoreFromStandByServer(fileName, serverHost, serverPort, isHttps);
/* 192 */         fileName = "conf" + File.separator + "probe" + File.separator + "defaultToNewProbes" + File.separator + "conf" + File.separator + "https.truststore";
/* 193 */         backUpTruststoreFromStandByServer(fileName, serverHost, serverPort, isHttps);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean authenticateTheUser(HttpServletRequest request, boolean checkUserInDB)
/*     */   {
/* 202 */     boolean allow = false;
/* 203 */     String userName = request.getParameter("userName");
/* 204 */     String passwd = request.getParameter("password");
/* 205 */     if ((userName != null) && (passwd != null))
/*     */     {
/* 207 */       String remoteHost = request.getRemoteHost();
/* 208 */       if ((remoteHost == null) || (remoteHost.trim().equals("")))
/*     */       {
/* 210 */         remoteHost = request.getRemoteAddr();
/*     */       }
/*     */       try
/*     */       {
/* 214 */         PasswordDecoder opmPass = new PasswordDecoder();
/* 215 */         String decUserName = opmPass.decrypt(URLDecoder.decode(userName, "UTF-8"));
/* 216 */         String decPasswd = opmPass.decrypt(URLDecoder.decode(passwd, "UTF-8"));
/* 217 */         if (checkUserInDB)
/*     */         {
/* 219 */           return checkUserNameAndPassword(userName, passwd, remoteHost);
/*     */         }
/*     */         
/* 222 */         if ((decUserName.equals(this.configuredUserName)) && (decPasswd.equals(this.confiuredPassword)))
/*     */         {
/* 224 */           allow = true;
/*     */         }
/*     */       }
/*     */       catch (Exception decExp)
/*     */       {
/* 229 */         AMLog.debug(" Exception while decrypt the userName and password. " + decExp);
/*     */       }
/*     */     }
/* 232 */     return allow;
/*     */   }
/*     */   
/*     */ 
/*     */   private void getFilesListInDirectory(String sourceDirectory, ServletOutputStream out)
/*     */   {
/* 238 */     sourceDirectory = sourceDirectory.replace('\\', '/');
/* 239 */     File sourcefile = new File(sourceDirectory);
/* 240 */     String AbsolutePath = sourcefile.getAbsolutePath();
/* 241 */     String[] listOfFiles = sourcefile.list();
/*     */     
/* 243 */     for (int i = 0; i < listOfFiles.length; i++)
/*     */     {
/*     */       try
/*     */       {
/* 247 */         File file = new File(AbsolutePath + File.separator + listOfFiles[i]);
/* 248 */         if (file.isDirectory())
/*     */         {
/* 250 */           getFilesListInDirectory(AbsolutePath + File.separator + listOfFiles[i], out);
/*     */         }
/*     */         else
/*     */         {
/* 254 */           out.println(AbsolutePath + File.separator + listOfFiles[i]);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 259 */         AMLog.debug("Exception ex " + ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void copyFile(String fileName, ServletResponse response)
/*     */   {
/* 267 */     BufferedInputStream in = null;
/* 268 */     ByteArrayOutputStream outStream = null;
/*     */     try
/*     */     {
/* 271 */       fileName = fileName.replace('\\', '/');
/* 272 */       String currentDirectory = ".";
/* 273 */       String absolutePath = new File(currentDirectory).getAbsolutePath();
/* 274 */       absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
/* 275 */       String fileAbsolutePath = new File(fileName).getAbsolutePath();
/* 276 */       AMLog.debug("copyfile actual absoluteFilepath ::::" + absolutePath + " from url fileAbsolutePath :::" + fileAbsolutePath);
/* 277 */       if ((!fileAbsolutePath.startsWith(absolutePath)) || (fileName.indexOf("..\\") != -1) || (fileName.indexOf("../") != -1))
/*     */       {
/* 279 */         AMLog.debug("File path is not allowed to copy");
/*     */       }
/*     */       else {
/* 282 */         ServletOutputStream out = response.getOutputStream();
/* 283 */         response.setContentLength((int)new File(fileName).length());
/* 284 */         in = new BufferedInputStream(new FileInputStream(fileName));
/* 285 */         outStream = new ByteArrayOutputStream(512);
/*     */         int val;
/* 287 */         while ((val = in.read()) != -1)
/*     */         {
/* 289 */           outStream.write(val);
/*     */         }
/*     */         
/* 292 */         outStream.writeTo(out);
/*     */       }
/*     */       return;
/*     */     } catch (Exception ex) {
/* 296 */       AMLog.debug("Exception while reading the file " + fileName);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 302 */         if (in != null)
/*     */         {
/* 304 */           in.close();
/*     */         }
/* 306 */         if (outStream != null)
/*     */         {
/* 308 */           outStream.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 313 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Properties parseProperties(String standbyProps)
/*     */   {
/* 322 */     if (standbyProps != null)
/*     */     {
/* 324 */       if ((standbyProps.startsWith("{")) && (standbyProps.endsWith("}")))
/*     */       {
/* 326 */         standbyProps = standbyProps.substring(1, standbyProps.length() - 1);
/*     */       }
/*     */     }
/*     */     
/* 330 */     Properties standByProps = new Properties();
/* 331 */     StringTokenizer tokens = new StringTokenizer(standbyProps, ",");
/* 332 */     while (tokens.hasMoreElements())
/*     */     {
/* 334 */       String token = (String)tokens.nextElement();
/*     */       
/* 336 */       StringTokenizer keyValueTokens = new StringTokenizer(token, "=");
/* 337 */       String key = ((String)keyValueTokens.nextElement()).trim();
/* 338 */       String value = "";
/* 339 */       if (keyValueTokens.hasMoreElements())
/*     */       {
/* 341 */         value = ((String)keyValueTokens.nextElement()).trim();
/*     */       }
/* 343 */       standByProps.setProperty(key, value);
/*     */     }
/*     */     
/* 346 */     return standByProps;
/*     */   }
/*     */   
/*     */   public boolean checkUserNameAndPassword(String userName, String passwd, String remoteHost)
/*     */   {
/* 351 */     boolean allow = false;
/*     */     try
/*     */     {
/* 354 */       PasswordDecoder opmPass = new PasswordDecoder();
/* 355 */       AMLog.debug("username:" + userName);
/* 356 */       AMLog.debug("password:" + passwd);
/* 357 */       String decUserName = opmPass.decrypt(userName);
/* 358 */       String decPasswd = opmPass.decrypt(passwd);
/* 359 */       AMLog.debug("dec username:" + decUserName);
/* 360 */       AMLog.debug("dec password:" + decPasswd);
/* 361 */       Properties userProps = new Properties();
/* 362 */       userProps.setProperty("hostname", remoteHost);
/* 363 */       allow = PureServerUtilsFE.isPasswordCorrect(decUserName, decPasswd, userProps);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 367 */       e.printStackTrace();
/*     */     }
/* 369 */     return allow;
/*     */   }
/*     */   
/*     */   private void backUpTruststoreFromStandByServer(String fileName, String standbyServer, String standbyPort, boolean isHttps) throws IOException
/*     */   {
/* 374 */     AMLog.debug("truststore file backup = " + fileName);
/* 375 */     AMLog.debug("standbyServer = " + standbyServer);
/* 376 */     AMLog.debug("standbyPort = " + standbyPort);
/* 377 */     AMLog.debug("ishttps = " + isHttps);
/* 378 */     String[] params = { "operation=copyfile", "fileName=" + fileName };
/* 379 */     FileOutputStream output = null;
/*     */     
/*     */     try
/*     */     {
/* 383 */       BufferedReader in = new BufferedReader(new InputStreamReader(sendRequestToRemoteWebServer(standbyServer, standbyPort, params, "POST", isHttps)));
/* 384 */       String currentDirectory = ".";
/* 385 */       String absolutePath = new File(currentDirectory).getAbsolutePath();
/* 386 */       File downloadFileName = new File(absolutePath + File.separator + fileName);
/* 387 */       output = new FileOutputStream(downloadFileName);
/*     */       int response;
/* 389 */       while ((response = in.read()) != -1) {
/* 390 */         output.write(response);
/*     */       }
/*     */       return;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 396 */       ex.printStackTrace();
/* 397 */       throw new IOException(" Exception when backUp the standBy Server https.truststore." + ex.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 403 */         output.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 407 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private InputStream sendRequestToRemoteWebServer(String host, String port, String[] parameters, String method, boolean isHttps) throws Exception
/*     */   {
/* 414 */     String params = "";
/* 415 */     for (int i = 0; i < parameters.length; i++)
/*     */     {
/* 417 */       params = params + parameters[i] + "&";
/*     */     }
/* 419 */     if (params.endsWith("&"))
/*     */     {
/* 421 */       params = params.substring(0, params.length() - 1);
/*     */     }
/*     */     
/* 424 */     String urlToOpen = null;
/* 425 */     if (isHttps)
/*     */     {
/* 427 */       urlToOpen = "https://" + host + ":" + port + "/servlet/com.adventnet.it360.failover.servlet.FailOverHelperServlet";
/*     */       
/*     */ 
/* 430 */       Security.addProvider(new Provider());
/*     */       
/*     */ 
/* 433 */       HostnameVerifier hv = new HostnameVerifier() {
/*     */         public boolean verify(String urlHostName, SSLSession session) {
/* 435 */           AMLog.debug("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
/* 436 */           return true;
/*     */         }
/* 438 */       };
/* 439 */       HttpsURLConnection.setDefaultHostnameVerifier(hv);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 444 */       urlToOpen = "http://" + host + ":" + port + "/servlet/com.adventnet.it360.failover.servlet.FailOverHelperServlet";
/*     */     }
/*     */     
/* 447 */     if ((method != null) && (method.equals("GET")) && (params.length() > 0))
/*     */     {
/* 449 */       urlToOpen = urlToOpen + "?" + params;
/*     */     }
/*     */     
/* 452 */     URL url = new URL(urlToOpen);
/*     */     
/* 454 */     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/* 455 */     conn.setRequestMethod(method);
/* 456 */     conn.setDoOutput(true);
/* 457 */     conn.setDoInput(true);
/* 458 */     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
/*     */     
/* 460 */     if ((method != null) && (method.equals("POST")) && (params.length() > 0))
/*     */     {
/* 462 */       out.write(params);
/*     */     }
/*     */     
/* 465 */     out.flush();
/* 466 */     out.close();
/*     */     
/* 468 */     if (conn.getResponseCode() != 200)
/*     */     {
/* 470 */       throw new Exception(" Problem to connecting the Remote Server and the Error code is  " + conn.getResponseMessage());
/*     */     }
/* 472 */     return conn.getInputStream();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\FailOverHelperServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */