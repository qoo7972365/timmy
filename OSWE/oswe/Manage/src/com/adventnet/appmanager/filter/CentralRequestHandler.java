/*     */ package com.adventnet.appmanager.filter;
/*     */ 
/*     */ import HTTPClient.HTTPConnection;
/*     */ import HTTPClient.HttpOutputStream;
/*     */ import HTTPClient.NVPair;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.ConcurrentHttpClient;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.httpservice.common.CommunicationObject;
/*     */ import com.manageengine.httpservice.server.HTTPService;
/*     */ import com.manageengine.httpservice.servlets.ProcessRequestServlet;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.httpclient.Header;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpMethod;
/*     */ import org.apache.commons.httpclient.NameValuePair;
/*     */ import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
/*     */ import org.apache.commons.httpclient.methods.GetMethod;
/*     */ import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.util.URIUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CentralRequestHandler
/*     */   extends ProcessRequestServlet
/*     */ {
/*  48 */   public static boolean pushImageFromProbe = true;
/*     */   
/*  50 */   public static int timeout = 300;
/*     */   
/*     */   static
/*     */   {
/*  54 */     String pushImageFromProbeValue = System.getProperty("pushImageFromProbe", "true");
/*  55 */     if (pushImageFromProbeValue != null) {
/*  56 */       pushImageFromProbe = Boolean.valueOf(pushImageFromProbeValue).booleanValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public void processRequest(CommunicationObject commObject, String responseURl)
/*     */   {
/*     */     try
/*     */     {
/*  64 */       Properties props = commObject.getProperties();
/*  65 */       String operation = props.getProperty("operation");
/*  66 */       if ((operation != null) && (operation.equals("requestURL"))) {
/*  67 */         sendURLrequestResponse(commObject, responseURl);
/*  68 */       } else if ((operation != null) && (operation.equals("dataSynchup")))
/*     */       {
/*  70 */         sendURLrequestResponse(commObject, responseURl);
/*  71 */       } else if ((operation != null) && (operation.equals("executeTask"))) {
/*  72 */         executeURlRequest(commObject, responseURl);
/*     */       }
/*     */     } catch (Exception ex) {
/*  75 */       AMLog.debug("problem while processing request from probe: " + ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void processDataSynchupRequest(Properties props, String responseURl)
/*     */   {
/*  83 */     PostMethod methodTogetProbeData = null;
/*     */     try
/*     */     {
/*  86 */       CommunicationObject commObjForresponse = new CommunicationObject();
/*  87 */       String operationType = props.getProperty("operationType");
/*  88 */       String operationID = props.getProperty("operationID");
/*  89 */       commObjForresponse.setOperationID(operationID);
/*  90 */       commObjForresponse.setOperationType(operationType);
/*  91 */       String url = props.getProperty("url");
/*  92 */       HttpOutputStream OutputStream = null;
/*  93 */       methodTogetProbeData = new PostMethod(url);
/*  94 */       HttpClient client = ConcurrentHttpClient.getHttpClient();
/*  95 */       int respCode1 = client.executeMethod(methodTogetProbeData);
/*  96 */       BufferedReader br1 = null;
/*  97 */       if (respCode1 == 200) {
/*  98 */         br1 = new BufferedReader(new InputStreamReader(methodTogetProbeData.getResponseBodyAsStream()));
/*  99 */         NVPair[] header_data = new NVPair[2];
/* 100 */         header_data[0] = new NVPair("commObject", commObjForresponse.toString());
/* 101 */         header_data[1] = new NVPair("statusCode", respCode1 + "");
/* 102 */         OutputStream = new HttpOutputStream();
/* 103 */         HTTPConnection con = new HTTPConnection(new URL(responseURl));
/* 104 */         con.Post(responseURl, OutputStream, header_data);
/* 105 */         String line = null;
/* 106 */         String lineSeparater = System.getProperty("line.separator");
/* 107 */         while ((line = br1.readLine()) != null) {
/* 108 */           OutputStream.write(line.getBytes());
/* 109 */           OutputStream.write(lineSeparater.getBytes());
/* 110 */           OutputStream.flush();
/*     */         }
/* 112 */         OutputStream.close();
/* 113 */         br1.close();
/*     */       }
/*     */     } catch (Exception ex) {
/* 116 */       AMLog.debug("problem while synching data to central server" + ex);
/*     */     } finally {
/* 118 */       if (methodTogetProbeData != null) {
/* 119 */         methodTogetProbeData.releaseConnection();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void sendErrorResponse(String responseURL, int error_code, CommunicationObject commData)
/*     */   {
/* 127 */     GetMethod methodTogetProbeData = null;
/*     */     try {
/* 129 */       HttpClient client = ConcurrentHttpClient.getHttpClient();
/* 130 */       methodTogetProbeData = new GetMethod(responseURL);
/* 131 */       NameValuePair param1 = new NameValuePair("statusCode", URIUtil.encodeQuery(error_code + ""));
/* 132 */       NameValuePair param2 = new NameValuePair("commObject", URIUtil.encodeQuery(commData.toString()));
/* 133 */       NameValuePair[] params = { param1, param2 };
/* 134 */       methodTogetProbeData.setQueryString(params);
/* 135 */       client.executeMethod(methodTogetProbeData);
/*     */     }
/*     */     catch (Exception ex) {
/* 138 */       AMLog.debug("problem in sending error response to central:" + ex);
/*     */     } finally {
/* 140 */       if (methodTogetProbeData != null) {
/* 141 */         methodTogetProbeData.releaseConnection();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static HttpMethod getHttpMethod(CommunicationObject commObject) throws Exception
/*     */   {
/* 148 */     HttpMethod methodTogetProbeData = null;
/*     */     try {
/* 150 */       String url = commObject.getURL();
/* 151 */       String httpMethod = commObject.getMethod();
/* 152 */       Properties formProps = commObject.getFormProperties();
/* 153 */       Properties headerProps = commObject.getHeaderProperties();
/* 154 */       StringBuffer buff = new StringBuffer(url);
/* 155 */       String tempUrl = "";
/* 156 */       String queryString = "";
/* 157 */       if (url.indexOf("?", 0) != -1) {
/* 158 */         tempUrl = buff.substring(0, url.indexOf("?", 0));
/* 159 */         queryString = buff.substring(url.indexOf("?", 0) + 1);
/*     */       } else {
/* 161 */         tempUrl = url;
/*     */       }
/* 163 */       URL test = new URL(url);
/* 164 */       if (httpMethod.trim().equals("Post")) {
/* 165 */         methodTogetProbeData = new PostMethod(tempUrl);
/* 166 */         if (formProps != null) {
/* 167 */           Enumeration keys = formProps.keys();
/* 168 */           while (keys.hasMoreElements()) {
/* 169 */             String key = (String)keys.nextElement();
/* 170 */             String value = formProps.getProperty(key);
/* 171 */             PostMethod post = (PostMethod)methodTogetProbeData;
/* 172 */             post.setParameter(key, value);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 179 */         methodTogetProbeData = new GetMethod(tempUrl);
/* 180 */         StringBuffer tempQuery = new StringBuffer();
/* 181 */         if ((queryString != null) && (!queryString.trim().equals(""))) {
/* 182 */           tempQuery.append(queryString);
/*     */         }
/*     */         
/* 185 */         if (formProps != null) {
/* 186 */           Enumeration keys = formProps.keys();
/* 187 */           while (keys.hasMoreElements()) {
/* 188 */             if (!tempQuery.toString().trim().equals("")) {
/* 189 */               tempQuery.append("&");
/*     */             }
/* 191 */             String key = (String)keys.nextElement();
/* 192 */             String value = formProps.getProperty(key);
/* 193 */             tempQuery.append(key + "=" + value);
/*     */           }
/*     */         }
/* 196 */         queryString = tempQuery.toString();
/*     */       }
/*     */       
/* 199 */       if (headerProps != null) {
/* 200 */         Enumeration keys = headerProps.keys();
/* 201 */         while (keys.hasMoreElements()) {
/* 202 */           String key = (String)keys.nextElement();
/* 203 */           String value = headerProps.getProperty(key);
/* 204 */           methodTogetProbeData.setRequestHeader(key, value);
/*     */         }
/*     */       }
/* 207 */       if ((queryString != null) && (!queryString.trim().equals(""))) {
/* 208 */         methodTogetProbeData.setQueryString(URIUtil.encodeQuery(queryString));
/*     */       }
/* 210 */       methodTogetProbeData.setFollowRedirects(false);
/*     */     } catch (Exception ex) {
/* 212 */       throw ex;
/*     */     }
/* 214 */     return methodTogetProbeData;
/*     */   }
/*     */   
/*     */   public static void sendURLrequestResponse(CommunicationObject commObject, String responseURl)
/*     */   {
/* 219 */     Properties props = commObject.getProperties();
/* 220 */     BufferedReader br1 = null;
/* 221 */     HttpMethod methodTogetProbeData = null;
/* 222 */     CommunicationObject responseData = null;
/* 223 */     HttpClient adminServerClient = ConcurrentHttpClient.getHttpClient();
/* 224 */     PostMethod methodToPostResponse = null;
/* 225 */     FileInputStream stream = null;
/*     */     try
/*     */     {
/* 228 */       responseURl = URIUtil.encodeQuery(responseURl);
/* 229 */       AMLog.info("Request from Central:" + props);
/* 230 */       responseData = new CommunicationObject();
/* 231 */       String operationType = props.getProperty("operationType");
/* 232 */       String operationID = props.getProperty("operationID");
/* 233 */       responseData.setOperationID(operationID);
/* 234 */       responseData.setOperationType(operationType);
/* 235 */       String productName = props.getProperty("extProdName");
/* 236 */       String opmanURL = props.getProperty("opmanURL");
/* 237 */       if (opmanURL == null) {
/* 238 */         opmanURL = HTTPService.probeProtocol + "://" + HTTPService.probeHostName + ":" + HTTPService.probePort;
/*     */       }
/* 240 */       String probeId = props.getProperty("probeId");
/* 241 */       responseData.setProbeID(probeId);
/* 242 */       String nmshome = props.getProperty("nmshome");
/* 243 */       HttpClient client = ConcurrentHttpClient.getHttpClient();
/* 244 */       methodTogetProbeData = getHttpMethod(commObject);
/* 245 */       int respCode1 = client.executeMethod(methodTogetProbeData);
/* 246 */       if (respCode1 == 200)
/*     */       {
/* 248 */         String contentTypeValue = "UTF-8";
/* 249 */         if (methodTogetProbeData.getResponseHeader("Content-Type") != null) {
/* 250 */           contentTypeValue = methodTogetProbeData.getResponseHeader("Content-Type").getValue();
/*     */         }
/*     */         
/* 253 */         methodToPostResponse = new PostMethod(responseURl);
/*     */         
/*     */ 
/* 256 */         methodToPostResponse.setRequestHeader("commObject", responseData.toString());
/* 257 */         methodToPostResponse.setRequestHeader("statusCode", respCode1 + "");
/* 258 */         methodToPostResponse.setRequestHeader("Content-Type", contentTypeValue);
/*     */         
/*     */ 
/* 261 */         if (methodTogetProbeData.getResponseHeader("MAS_PushModelSyncStatus") != null) {
/* 262 */           String pushModelSyncStatus = methodTogetProbeData.getResponseHeader("MAS_PushModelSyncStatus").getValue();
/* 263 */           methodToPostResponse.setRequestHeader("MAS_PushModelSyncStatus", pushModelSyncStatus);
/*     */         }
/*     */         
/* 266 */         if (methodTogetProbeData.getResponseHeader("MAS_PushModelEnabledStatus") != null) {
/* 267 */           String pushModelEnableStatus = methodTogetProbeData.getResponseHeader("MAS_PushModelEnabledStatus").getValue();
/* 268 */           methodToPostResponse.setRequestHeader("MAS_PushModelEnabledStatus", pushModelEnableStatus);
/*     */         }
/*     */         
/*     */ 
/* 272 */         String line = null;
/* 273 */         String imagePath = props.getProperty("imagePath");
/* 274 */         String lineSeparater = System.getProperty("line.separator");
/* 275 */         if (contentTypeValue.indexOf("image") == -1) {
/* 276 */           if ((pushImageFromProbe) && (contentTypeValue.indexOf("text/html") != -1)) {
/* 277 */             br1 = new BufferedReader(new InputStreamReader(methodTogetProbeData.getResponseBodyAsStream()));
/* 278 */             File file = new File("input_" + System.currentTimeMillis());
/* 279 */             FileWriter fw = new FileWriter(file);
/* 280 */             while ((line = br1.readLine()) != null) {
/* 281 */               processImages(line, responseURl, imagePath, productName, opmanURL, probeId, nmshome);
/* 282 */               fw.write(line);
/* 283 */               fw.write(lineSeparater);
/*     */             }
/* 285 */             fw.close();
/* 286 */             br1.close();
/* 287 */             stream = new FileInputStream(file);
/* 288 */             methodToPostResponse.setRequestEntity(new InputStreamRequestEntity(stream, -1L));
/* 289 */             adminServerClient.executeMethod(methodToPostResponse);
/* 290 */             stream.close();
/* 291 */             file.delete();
/*     */           } else {
/* 293 */             InputStream istream = methodTogetProbeData.getResponseBodyAsStream();
/* 294 */             methodToPostResponse.setRequestEntity(new InputStreamRequestEntity(istream, -1L));
/* 295 */             adminServerClient.executeMethod(methodToPostResponse);
/* 296 */             istream.close();
/*     */           }
/*     */         } else {
/* 299 */           byte[] buf = getBytes(methodTogetProbeData.getResponseBodyAsStream());
/* 300 */           methodToPostResponse.setRequestEntity(new ByteArrayRequestEntity(buf));
/* 301 */           adminServerClient.executeMethod(methodToPostResponse);
/*     */         }
/*     */       } else {
/* 304 */         sendErrorResponse(responseURl, respCode1, responseData);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 308 */       ex.printStackTrace();
/* 309 */       AMLog.debug("problem in sending error response to central:" + ex);
/* 310 */       sendErrorResponse(responseURl, 500, responseData);
/*     */     }
/*     */     finally {
/* 313 */       if (methodToPostResponse != null) {
/*     */         try {
/* 315 */           methodToPostResponse.releaseConnection();
/* 316 */           if (stream != null)
/*     */           {
/* 318 */             stream.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 322 */           Logger.getLogger(CentralRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
/*     */         }
/*     */       }
/*     */       
/* 326 */       if (methodTogetProbeData != null) {
/* 327 */         methodTogetProbeData.releaseConnection();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static byte[] getBytes(InputStream is)
/*     */     throws IOException
/*     */   {
/* 335 */     int size = 1024;
/*     */     int len;
/*     */     byte[] buf;
/* 338 */     if ((is instanceof ByteArrayInputStream)) {
/* 339 */       size = is.available();
/* 340 */       byte[] buf = new byte[size];
/* 341 */       len = is.read(buf, 0, size);
/*     */     } else {
/* 343 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 344 */       buf = new byte[size];
/* 345 */       int len; while ((len = is.read(buf, 0, size)) != -1) {
/* 346 */         bos.write(buf, 0, len);
/*     */       }
/* 348 */       buf = bos.toByteArray();
/*     */     }
/*     */     
/* 351 */     return buf;
/*     */   }
/*     */   
/*     */   public static void executeURlRequest(CommunicationObject commObject, String responseURl)
/*     */   {
/* 356 */     Properties props = commObject.getProperties();
/* 357 */     String url = props.getProperty("url");
/* 358 */     HttpOutputStream OutputStream = null;
/*     */     
/* 360 */     PostMethod methodTogetProbeData = new PostMethod(url);
/* 361 */     int propsSize = props.size();
/* 362 */     NameValuePair[] params = new NameValuePair[propsSize];
/* 363 */     Enumeration parameters = props.keys();
/* 364 */     int i = 0;
/* 365 */     while (parameters.hasMoreElements()) {
/* 366 */       String key = (String)parameters.nextElement();
/* 367 */       String value = props.getProperty(key);
/* 368 */       NameValuePair param = new NameValuePair(key, value);
/* 369 */       params[i] = param;
/* 370 */       i++;
/*     */     }
/* 372 */     methodTogetProbeData.setQueryString(params);
/*     */     try {
/* 374 */       CommunicationObject commObjForresponse = new CommunicationObject();
/* 375 */       String operationType = props.getProperty("operationType");
/* 376 */       String operationID = props.getProperty("operationID");
/* 377 */       commObjForresponse.setOperationID(operationID);
/* 378 */       commObjForresponse.setOperationType(operationType);
/* 379 */       HttpClient client = ConcurrentHttpClient.getHttpClient();
/* 380 */       int respCode1 = client.executeMethod(methodTogetProbeData);
/* 381 */       if (respCode1 == 200)
/*     */       {
/* 383 */         NVPair[] header_data = new NVPair[2];
/* 384 */         header_data[0] = new NVPair("commObject", commObjForresponse.toString());
/* 385 */         header_data[1] = new NVPair("statusCode", respCode1 + "");
/* 386 */         OutputStream = new HttpOutputStream();
/* 387 */         HTTPConnection con = new HTTPConnection(new URL(responseURl));
/* 388 */         con.Post(responseURl, OutputStream, header_data);
/* 389 */         OutputStream.write("success".getBytes());
/* 390 */         OutputStream.flush();
/* 391 */         OutputStream.close();
/*     */       }
/*     */     } catch (Exception ex) {
/* 394 */       AMLog.debug("problem in executeURlRequest:" + ex);
/*     */     } finally {
/* 396 */       if (methodTogetProbeData != null) {
/* 397 */         methodTogetProbeData.releaseConnection();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void processImages(String line, String responseURl, String imagePath, String productName, String opmanagerurl, String masId, String nmshome)
/*     */   {
/* 406 */     if (productName == null) {
/* 407 */       productName = "AppManager";
/*     */     }
/* 409 */     if (productName.equals("AppManager")) {
/* 410 */       if (line.indexOf("/webclient/temp/") != -1) {
/* 411 */         writeMASImageIntoCentralServer(responseURl, line, imagePath);
/*     */       }
/*     */     }
/* 414 */     else if ((productName.equals("OpManager")) || (productName.equals("NetflowAnalyzer")))
/*     */     {
/* 416 */       if ((line.indexOf(".png") == -1) && (line.indexOf(".jpg") == -1) && (line.indexOf(".gif") == -1)) {
/* 417 */         return;
/*     */       }
/* 419 */       String tostore = null;
/* 420 */       boolean toprint = false;
/* 421 */       boolean isStoreFile = false;
/* 422 */       boolean islineWithCssFile = false;
/* 423 */       String toqueryfromOpmanager = null;
/* 424 */       boolean checkservlet = false;
/* 425 */       boolean isJSwithDotDO = false;
/*     */       
/* 427 */       String dotDo = ".do?";
/*     */       try {
/* 429 */         String stringToReplace = "/webclient/devices/images/";
/*     */         
/* 431 */         String chartStr = "/servlet/OpManagerDisplayChart?filename=";
/* 432 */         if (!Constants.isIt360) {
/* 433 */           chartStr = "/servlet/app_cached/";
/*     */         }
/*     */         
/* 436 */         String cssStr = "/webclient/common/css/";
/* 437 */         String it360CssStr = "/opmfiles/common/css/";
/* 438 */         String commonImage = "/webclient/common/images/";
/* 439 */         String commonJS = "/webclient/common/js/";
/* 440 */         String it360CommonJS = "/opmfiles/common/js/";
/* 441 */         String devicesJS = "/webclient/devices/js/";
/* 442 */         String it360DevicesJS = "/opmfiles/devices/js/";
/* 443 */         String nfaImage = "../images/";
/* 444 */         String nfaScript = "../script/";
/* 445 */         String nfaCss = "../css/";
/* 446 */         String tempImages = "/temp/opmanager-";
/*     */         
/* 448 */         String nmshome1 = System.getProperty("webnms.rootdir");
/* 449 */         String it360TempImages = "/opmtempfiles/";
/*     */         
/* 451 */         String opmanagerImageLocation = nmshome1 + File.separator + ".." + File.separator + ".." + File.separator + "networks" + File.separator + "temp";
/* 452 */         if (productName.equals("NetflowAnalyzer")) {
/* 453 */           stringToReplace = "/netflow/images/";
/* 454 */           commonJS = "/netflow/script/";
/* 455 */           cssStr = "/netflow/css/";
/*     */           
/*     */ 
/* 458 */           dotDo = ".jsp?";
/* 459 */           chartStr = "/netflow/servlet/DisplayChart?filename=";
/* 460 */           chartStr = "/servlet/app_cached/";
/*     */         }
/* 462 */         if (line.indexOf(tempImages) != -1) {
/* 463 */           isStoreFile = true;
/* 464 */           int startIndexForResource = line.indexOf(tempImages);
/* 465 */           int endIndexForResource = -1;
/* 466 */           if (line.indexOf("png") != -1) {
/* 467 */             endIndexForResource = line.indexOf("png", startIndexForResource + tempImages.length());
/* 468 */             endIndexForResource += 3;
/* 469 */           } else if (line.indexOf("jpg") != -1) {
/* 470 */             endIndexForResource = line.indexOf("jpg", startIndexForResource + tempImages.length());
/* 471 */             endIndexForResource += 3;
/* 472 */           } else if (line.indexOf("gif") != -1) {
/* 473 */             endIndexForResource = line.indexOf("gif", startIndexForResource + tempImages.length());
/* 474 */             endIndexForResource += 3;
/*     */           }
/* 476 */           tostore = tempImages + line.substring(startIndexForResource + tempImages.length(), endIndexForResource);
/* 477 */           toqueryfromOpmanager = tostore;
/* 478 */           if (Constants.isIt360) {
/* 479 */             toqueryfromOpmanager = it360TempImages + line.substring(startIndexForResource + tempImages.length(), endIndexForResource);
/*     */           }
/*     */         }
/* 482 */         if (line.indexOf(nfaImage) != -1) {
/* 483 */           line = line.replace(nfaImage, stringToReplace);
/*     */         }
/* 485 */         if (line.indexOf(nfaScript) != -1) {
/* 486 */           line = line.replace(nfaScript, commonJS);
/*     */         }
/* 488 */         if (line.indexOf(nfaCss) != -1) {
/* 489 */           line = line.replace(nfaCss, cssStr);
/*     */         }
/* 491 */         if (((!Constants.isIt360) || (!productName.equals("OpManager"))) && (line.indexOf(stringToReplace) != -1))
/*     */         {
/* 493 */           int startIndexForResource = line.indexOf(stringToReplace);
/* 494 */           int endIndexForResource = -1;
/* 495 */           if (line.indexOf(".png") != -1) {
/* 496 */             endIndexForResource = line.indexOf(".png", startIndexForResource + stringToReplace.length());
/* 497 */             endIndexForResource += 4;
/* 498 */           } else if (line.indexOf(".jpg") != -1) {
/* 499 */             endIndexForResource = line.indexOf(".jpg", startIndexForResource + stringToReplace.length());
/* 500 */             endIndexForResource += 4;
/* 501 */           } else if (line.indexOf(".gif") != -1) {
/* 502 */             endIndexForResource = line.indexOf(".gif", startIndexForResource + stringToReplace.length());
/* 503 */             endIndexForResource += 4;
/*     */           }
/* 505 */           if (endIndexForResource != -1) {
/* 506 */             isStoreFile = true;
/* 507 */             tostore = stringToReplace + line.substring(startIndexForResource + stringToReplace.length(), endIndexForResource);
/* 508 */             toqueryfromOpmanager = tostore;
/*     */           }
/*     */         }
/* 511 */         if ((chartStr != null) && (line.indexOf(chartStr) != -1))
/*     */         {
/* 513 */           int startIndexForResource = line.indexOf(chartStr);
/* 514 */           int endIndexForResource = -1;
/* 515 */           if (line.indexOf(".png") != -1) {
/* 516 */             endIndexForResource = line.indexOf(".png", startIndexForResource + chartStr.length());
/* 517 */             endIndexForResource += 4;
/*     */           }
/* 519 */           if (endIndexForResource != -1) {
/* 520 */             checkservlet = true;
/* 521 */             isStoreFile = true;
/* 522 */             tostore = "/servlet/app_cached/" + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/* 523 */             boolean temp = true;
/* 524 */             if (temp) {
/* 525 */               toqueryfromOpmanager = tostore;
/* 526 */               if ((Constants.isIt360) && (productName.equals("OpManager"))) {
/* 527 */                 toqueryfromOpmanager = "/opmtempfiles/" + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/*     */               }
/*     */             } else {
/* 530 */               toqueryfromOpmanager = chartStr + line.substring(startIndexForResource + chartStr.length(), endIndexForResource);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 537 */         if ((!Constants.isIt360) && (line.indexOf(commonImage) != -1)) {
/* 538 */           int startIndexForResource = line.indexOf(commonImage);
/* 539 */           int endIndexForResource = -1;
/* 540 */           if (line.indexOf(".png") != -1) {
/* 541 */             endIndexForResource = line.indexOf(".png", startIndexForResource + commonImage.length());
/* 542 */             endIndexForResource += 4;
/* 543 */           } else if (line.indexOf(".jpg") != -1) {
/* 544 */             endIndexForResource = line.indexOf(".jpg", startIndexForResource + commonImage.length());
/* 545 */             endIndexForResource += 4;
/* 546 */           } else if (line.indexOf(".gif") != -1) {
/* 547 */             endIndexForResource = line.indexOf(".gif", startIndexForResource + commonImage.length());
/* 548 */             endIndexForResource += 4;
/*     */           }
/* 550 */           if (endIndexForResource != -1) {
/* 551 */             isStoreFile = true;
/* 552 */             tostore = commonImage + line.substring(startIndexForResource + commonImage.length(), endIndexForResource);
/* 553 */             toqueryfromOpmanager = tostore;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 558 */         if ((!isStoreFile) || (tostore == null)) {
/* 559 */           return;
/*     */         }
/* 561 */         String extprodFiles = "";
/* 562 */         if (Constants.isIt360) {
/* 563 */           extprodFiles = "extprodfiles/" + masId + "/";
/*     */         }
/* 565 */         String extProductDir = (extprodFiles + productName + "files").toLowerCase();
/* 566 */         tostore = FormatUtil.findReplace(tostore, "/", File.separator);
/* 567 */         tostore = FormatUtil.findReplace(tostore, "/", File.separator);
/* 568 */         String url = opmanagerurl + toqueryfromOpmanager;
/* 569 */         if ((!Constants.isIt360) || (!productName.equals("OpManager"))) {
/* 570 */           url = opmanagerurl + "/" + extProductDir + "/" + toqueryfromOpmanager;
/*     */         }
/*     */         
/* 573 */         String fileName = tostore.substring(tostore.lastIndexOf(File.separator));
/* 574 */         File f = new File(nmshome + File.separator + findAndReplaceAll(extProductDir, "/", File.separator) + tostore);
/* 575 */         String imagesLocation = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(File.separator));
/*     */         
/* 577 */         if (productName.equals("NetflowAnalyzer"))
/*     */         {
/* 579 */           synchFilesSend(responseURl, url, fileName, imagesLocation);
/*     */         } else {
/* 581 */           sendFilesToCentral(responseURl, fileName, opmanagerImageLocation, imagesLocation);
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {
/* 585 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static String findAndReplaceAll(String str, String find, String replace) {
/* 591 */     StringBuffer des = new StringBuffer("");
/* 592 */     while (str.indexOf(find) != -1) {
/* 593 */       des.append(str.substring(0, str.indexOf(find)));
/* 594 */       des.append(replace);
/* 595 */       str = str.substring(str.indexOf(find) + find.length());
/*     */     }
/* 597 */     des.append(str);
/* 598 */     return des.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public static void writeMASImageIntoCentralServer(String responseURL, String line, String mas_location)
/*     */   {
/*     */     try
/*     */     {
/* 606 */       String nmshome = System.getProperty("webnms.rootdir");
/* 607 */       int startingIndex = line.indexOf("jfreechart");
/* 608 */       int endIndex = line.indexOf("png");
/* 609 */       String imageFilleName = line.substring(startingIndex, endIndex + 3);
/* 610 */       String imagesLocation = mas_location;
/* 611 */       String localPath = nmshome + File.separator + "webclient" + File.separator + "temp";
/* 612 */       sendFilesToCentral(responseURL, imageFilleName, localPath, imagesLocation);
/*     */     } catch (Exception e) {
/* 614 */       AMLog.debug("problem in writeMASImageIntoCentralServer:" + e);
/*     */     }
/*     */   }
/*     */   
/*     */   public byte[] toByteArray(Object obj) {
/* 619 */     bytes = null;
/* 620 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 621 */     ObjectOutputStream oos = null;
/*     */     try {
/* 623 */       oos = new ObjectOutputStream(bos);
/* 624 */       oos.writeObject(obj);
/* 625 */       oos.flush();
/*     */       
/* 627 */       bos.close();
/* 628 */       return bos.toByteArray();
/*     */     } catch (IOException ex) {
/* 630 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       try {
/* 634 */         oos.close();
/*     */       } catch (IOException ex) {
/* 636 */         Logger.getLogger(CentralRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleExdProdURLRequest(Properties props, String responseURl) {}
/*     */   
/*     */   public void sendFilesToCentralServer(String responseURL, String line, String mas_location) {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\CentralRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */