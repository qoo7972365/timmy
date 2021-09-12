/*     */ package com.me.apm.client.selenium.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.RealBrowserUtil;
/*     */ import com.adventnet.utilities.xml.dom.XMLDataReader;
/*     */ import com.adventnet.utilities.xml.dom.XMLNode;
/*     */ import com.me.apm.server.selenium.datacollection.RealBrowserDC;
/*     */ import com.me.apm.server.selenium.datacollection.RealBrowserException;
/*     */ import com.me.apm.server.selenium.util.HTMLToJythonConverter;
/*     */ import com.me.apm.server.selenium.util.RBMConstants;
/*     */ import com.me.apm.server.selenium.util.RealBrowserFileUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.collections.MultiHashMap;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.HttpException;
/*     */ import org.apache.commons.httpclient.methods.MultipartPostMethod;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateRealBrowserTest
/*     */   extends HttpServlet
/*     */ {
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  58 */     super.doGet(req, resp);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  65 */     playback(request, response);
/*     */   }
/*     */   
/*     */   public void playback(HttpServletRequest request, HttpServletResponse response) throws IOException
/*     */   {
/*  70 */     Long monitorId = null;
/*  71 */     Long lastUpdatedTime = null;
/*  72 */     boolean isAddMonitor = false;
/*  73 */     request.setCharacterEncoding("UTF-8");
/*     */     try {
/*  75 */       checkEUMAgent();
/*     */     } catch (RealBrowserException rbe) {
/*  77 */       JSONObject object = new JSONObject();
/*     */       try {
/*  79 */         object.put("ST", "Failure");
/*  80 */         object.put("RES", rbe.getMessage());
/*     */       } catch (JSONException e) {
/*  82 */         e.printStackTrace();
/*     */       }
/*  84 */       response.getWriter().print(object.toString());
/*  85 */       response.getWriter().flush();
/*  86 */       return;
/*     */     }
/*     */     
/*  89 */     RealBrowserFileUtil rbmFileUtil = new RealBrowserFileUtil();
/*     */     
/*  91 */     boolean isMultipart = ServletFileUpload.isMultipartContent(request);
/*     */     
/*  93 */     if (isMultipart)
/*     */     {
/*  95 */       boolean isScriptExist = true;
/*  96 */       String zipFilePath = null;
/*  97 */       String scriptFileLocation = null;
/*  98 */       String operation = null;
/*  99 */       String resourceName = null;
/* 100 */       String pollingId = null;
/* 101 */       String agentIds = null;
/* 102 */       Properties requestParams = null;
/* 103 */       String isAdminMO = null;
/*     */       try
/*     */       {
/* 106 */         requestParams = rbmFileUtil.handleMultipartRequest(request);
/* 107 */         AMLog.debug("Request Params : " + requestParams);
/* 108 */         zipFilePath = requestParams.getProperty("fileName");
/* 109 */         operation = requestParams.getProperty("OPRN");
/* 110 */         pollingId = requestParams.getProperty("PID");
/*     */         try {
/* 112 */           agentIds = requestParams.getProperty("LID");
/*     */         } catch (NullPointerException e1) {
/* 114 */           e1.printStackTrace();
/*     */         }
/* 116 */         if (agentIds != null) {
/* 117 */           agentIds = requestParams.getProperty("SL");
/*     */         }
/* 119 */         if ("ADDMON".equalsIgnoreCase(requestParams.getProperty("OPRN"))) {
/* 120 */           String encodedResourceName = requestParams.getProperty("DN");
/* 121 */           resourceName = new String(encodedResourceName.getBytes("iso-8859-1"), "UTF-8");
/*     */           
/*     */ 
/* 124 */           if (checkResourceAlreadyExist(resourceName)) {
/* 125 */             JSONObject object = new JSONObject();
/*     */             try {
/* 127 */               object.put("ST", "Failure");
/* 128 */               object.put("RES", FormatUtil.getString("am.rbm.mo.already.exists", new String[] { resourceName }));
/*     */             } catch (JSONException e) {
/* 130 */               e.printStackTrace();
/*     */             }
/* 132 */             response.getWriter().print(object.toString());
/* 133 */             response.getWriter().flush();
/* 134 */             return;
/*     */           }
/* 136 */           scriptFileLocation = RBMConstants.getScriptLocation() + File.separator + resourceName;
/* 137 */           isAddMonitor = true;
/* 138 */           isAdminMO = requestParams.getProperty("isAdminMO") != null ? requestParams.getProperty("isAdminMO") : "false";
/*     */         } else {
/* 140 */           scriptFileLocation = RBMConstants.getTestScriptLocation();
/*     */         }
/*     */       } catch (RealBrowserException e1) {
/* 143 */         e1.printStackTrace();
/* 144 */         if ("No file exist in request".equals(e1.getMessage())) {
/* 145 */           JSONObject object = new JSONObject();
/*     */           try {
/* 147 */             object.put("ST", "Failure");
/* 148 */             object.put("RES", FormatUtil.getString("am.rbm.script.not.available"));
/*     */           } catch (JSONException e) {
/* 150 */             e.printStackTrace();
/*     */           }
/* 152 */           response.getWriter().print(object.toString());
/* 153 */           response.getWriter().flush();
/* 154 */           return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 162 */       String scriptFile = null;
/*     */       try {
/* 164 */         scriptFile = rbmFileUtil.unzipFile(zipFilePath);
/*     */       } catch (IOException e1) {
/* 166 */         e1.printStackTrace();
/* 167 */         JSONObject object = new JSONObject();
/*     */         try {
/* 169 */           object.put("ST", "Failure");
/* 170 */           object.put("RES", e1.getMessage());
/*     */         } catch (JSONException e) {
/* 172 */           e.printStackTrace();
/*     */         }
/* 174 */         response.getWriter().print(object.toString());
/* 175 */         response.getWriter().flush();
/* 176 */         return;
/*     */       }
/*     */       
/* 179 */       if (scriptFile == null) {
/* 180 */         JSONObject object = new JSONObject();
/*     */         try {
/* 182 */           object.put("ST", "Failure");
/* 183 */           object.put("RES", FormatUtil.getString("am.rbm.script.zip.empty"));
/*     */         } catch (JSONException e) {
/* 185 */           e.printStackTrace();
/*     */         }
/* 187 */         response.getWriter().print(object.toString());
/* 188 */         response.getWriter().flush();
/* 189 */         return;
/*     */       }
/*     */       
/*     */ 
/* 193 */       String testCaseSourceAsString = rbmFileUtil.readFileContentToString(scriptFile);
/* 194 */       testCaseSourceAsString = StringEscapeUtils.unescapeHtml4(testCaseSourceAsString);
/* 195 */       AMLog.debug("File String : " + testCaseSourceAsString);
/* 196 */       Properties props = new HTMLToJythonConverter().getJythonScript(testCaseSourceAsString, isAddMonitor);
/* 197 */       boolean errorParsingScript = ((Boolean)props.get("errorParsingScript")).booleanValue();
/* 198 */       AMLog.debug("File props : " + props);
/*     */       
/* 200 */       ArrayList<Properties> parsedTestCase = (ArrayList)props.get("parsedTestCase");
/*     */       
/* 202 */       if (errorParsingScript)
/*     */       {
/* 204 */         JSONObject object = new JSONObject();
/*     */         try {
/* 206 */           object.put("ST", "Failure");
/* 207 */           object.put("RES", FormatUtil.getString("am.rbm.script.parsing.error"));
/*     */         } catch (JSONException e) {
/* 209 */           e.printStackTrace();
/*     */         }
/* 211 */         response.getWriter().print(object.toString());
/* 212 */         response.getWriter().flush();
/* 213 */         return;
/*     */       }
/*     */       
/* 216 */       String baseURL = (String)props.get("baseURL");
/* 217 */       AMLog.debug("baseURL : " + baseURL);
/*     */       
/*     */ 
/*     */ 
/* 221 */       Long collectionTime = Long.valueOf(System.currentTimeMillis());
/* 222 */       lastUpdatedTime = collectionTime;
/* 223 */       monitorId = collectionTime;
/*     */       
/* 225 */       String scriptFileName = "";
/*     */       
/* 227 */       if (isAddMonitor) {
/* 228 */         scriptFileName = resourceName + "." + RBMConstants.getFileExtension();
/*     */       } else {
/* 230 */         File recorderFile = new File(scriptFile);
/* 231 */         String recFileName = RealBrowserUtil.removeExtension(recorderFile.getName());
/* 232 */         scriptFileName = recFileName + "." + RBMConstants.getFileExtension();
/*     */       }
/*     */       
/*     */ 
/* 236 */       String jythonScriptFile = scriptFileLocation + File.separator + scriptFileName;
/*     */       
/* 238 */       AMLog.debug("jythonScriptFile Name :" + jythonScriptFile);
/*     */       
/* 240 */       HashMap<String, String> urlMapper = (HashMap)props.get("stepURLMapper");
/*     */       
/* 242 */       String modifiedScript = new RealBrowserUtil().appendURLMapperInStep(props.getProperty("jythonScript"), urlMapper);
/*     */       
/*     */       try
/*     */       {
/* 246 */         rbmFileUtil.saveAsFileInToDisk(jythonScriptFile, modifiedScript);
/*     */       } catch (IOException e) {
/* 248 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 253 */       if (EnterpriseUtil.isAdminServer) {
/* 254 */         response.getWriter().print(adminServerHandling(requestParams, operation));
/* 255 */         response.getWriter().flush();
/* 256 */         return;
/*     */       }
/*     */       
/* 259 */       response.setCharacterEncoding("UTF-8");
/* 260 */       String apiKey = "";
/*     */       
/* 262 */       int thinkTime = 10;
/*     */       try
/*     */       {
/* 265 */         thinkTime = Integer.parseInt(requestParams.getProperty("TT"));
/* 266 */         if (thinkTime > 10) {
/* 267 */           thinkTime = 10;
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException e) {}
/*     */       
/*     */ 
/* 273 */       int pageLoadTime = 30;
/*     */       try {
/* 275 */         pageLoadTime = Integer.parseInt(requestParams.getProperty("PGLT"));
/* 276 */         if (pageLoadTime > 30)
/*     */         {
/* 278 */           pageLoadTime = 30;
/*     */         }
/*     */       }
/*     */       catch (NumberFormatException e) {}
/*     */       
/*     */ 
/* 284 */       props.put("monitorId", monitorId);
/* 285 */       props.put("lastUpdatedTime", lastUpdatedTime);
/* 286 */       props.put("userId", "-1");
/* 287 */       props.put("collectionTime", collectionTime);
/* 288 */       props.put("browserType", Integer.valueOf(1));
/* 289 */       props.put("apiKey", apiKey);
/* 290 */       props.put("thinkTime", Integer.valueOf(thinkTime));
/* 291 */       props.put("pageLoadTime", Integer.valueOf(pageLoadTime));
/* 292 */       props.put("acceptUntrustedCert", requestParams.getProperty("acceptUntrustedCert"));
/* 293 */       props.setProperty("scriptName", scriptFileName);
/* 294 */       if (isAddMonitor) {
/* 295 */         props.setProperty("resourceName", resourceName);
/* 296 */         props.setProperty("displayName", resourceName);
/* 297 */         props.setProperty("pollingId", pollingId);
/* 298 */         props.setProperty("agentIds", agentIds);
/* 299 */         props.setProperty("scriptLocation", scriptFileLocation);
/* 300 */         props.setProperty("isAdminMO", isAdminMO);
/* 301 */         String haid = requestParams.getProperty("haid");
/* 302 */         if ((haid != null) && (!"null".equalsIgnoreCase(haid)) && (!"".equalsIgnoreCase(haid)))
/*     */         {
/* 304 */           props.setProperty("haid", requestParams.getProperty("haid"));
/*     */         }
/*     */       }
/* 307 */       props.setProperty("agentIds", agentIds);
/* 308 */       props.setProperty("script", modifiedScript);
/*     */       
/* 310 */       RealBrowserUtil.setScriptPropsCache(scriptFileName, props);
/*     */       
/* 312 */       PrintWriter writer = new PrintWriter(response.getOutputStream());
/*     */       
/* 314 */       RealBrowserDC rbmDC = new RealBrowserDC();
/*     */       
/* 316 */       String xmlResponse = "";
/* 317 */       if (isAddMonitor) {
/* 318 */         if ("true".equals(isAdminMO)) {
/* 319 */           writer.print(rbmDC.createMonitor(props));
/*     */         } else {
/*     */           try {
/* 322 */             JSONObject result = rbmDC.createMonitor(props);
/* 323 */             writer.print(result.getString("status"));
/*     */           } catch (JSONException e) {
/* 325 */             e.printStackTrace();
/*     */           }
/*     */         }
/* 328 */         writer.flush();
/* 329 */         return;
/*     */       }
/*     */       try {
/* 332 */         xmlResponse = rbmDC.testPlayBack(props);
/*     */       } catch (RealBrowserException e) {
/* 334 */         e.printStackTrace();
/*     */         try {
/* 336 */           JSONObject object = new JSONObject();
/* 337 */           object.put("ST", "Failure");
/* 338 */           object.put("RES", e.getMessage());
/* 339 */           writer.print(object.toString());
/* 340 */           writer.flush();
/*     */         } catch (JSONException e1) {
/* 342 */           e1.printStackTrace();
/*     */         }
/* 344 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 352 */         JSONObject object = new JSONObject();
/* 353 */         JSONArray result = getJSONRespFromXML(xmlResponse, parsedTestCase);
/* 354 */         JSONObject trResult = result.getJSONObject(0);
/* 355 */         JSONArray modifiedArray = removeJSONElement(result, 0);
/* 356 */         String status = trResult.getString("ST");
/* 357 */         if (status.equals("1"))
/*     */         {
/* 359 */           status = "Success";
/* 360 */           object.put("result", modifiedArray);
/*     */         } else {
/* 362 */           status = "Failure";
/*     */         }
/*     */         
/* 365 */         String reason = trResult.getString("RES");
/* 366 */         object.put("ST", status);
/* 367 */         object.put("RES", reason);
/*     */         
/* 369 */         AMLog.debug("Create Real Browser Test : JSON Response : " + object.toString());
/* 370 */         writer.print(object.toString());
/* 371 */         writer.flush();
/*     */       } catch (JSONException e) {
/* 373 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private JSONArray removeJSONElement(JSONArray result, int index) throws JSONException {
/* 379 */     JSONArray modifiedArray = new JSONArray();
/* 380 */     for (int i = 0; i < result.length(); i++) {
/* 381 */       if (i != index) {
/* 382 */         modifiedArray.put(result.get(i));
/*     */       }
/*     */     }
/* 385 */     return modifiedArray;
/*     */   }
/*     */   
/*     */   private boolean checkResourceAlreadyExist(String resourceName)
/*     */   {
/* 390 */     if (DBUtil.getResourceIdForResourceName(resourceName) != null) {
/* 391 */       return true;
/*     */     }
/* 393 */     return false;
/*     */   }
/*     */   
/*     */   public void checkEUMAgent() throws RealBrowserException
/*     */   {
/* 398 */     String qry = "select * from AM_RBMAGENTDATA where STATUS!=1 and AGENTID > " + EnterpriseUtil.getDistributedStartResourceId() + " and AGENTNAME NOT LIKE ('%(Local)')";
/* 399 */     boolean noAgent = true;
/* 400 */     ResultSet rs = null;
/*     */     try {
/* 402 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 403 */       while ((rs != null) && (rs.next()))
/*     */       {
/* 405 */         noAgent = false;
/*     */         try {
/* 407 */           if (Integer.parseInt(rs.getString("AGENTVERSION").replace(".", "")) > Constants.eumAppAgent) {
/*     */             return;
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/* 412 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 415 */       if (!noAgent)
/*     */       {
/* 417 */         throw new RealBrowserException(FormatUtil.getString("am.rbm.agents.notsupported"));
/*     */       }
/*     */     } catch (Exception e) {
/* 420 */       e.printStackTrace();
/*     */     } finally {
/* 422 */       AMConnectionPool.closeResultSet(rs);
/*     */     }
/* 424 */     throw new RealBrowserException(FormatUtil.getString("am.rbm.agents.notrunning"));
/*     */   }
/*     */   
/*     */   private static JSONArray getJSONRespFromXML(String xmlResp, ArrayList<Properties> al)
/*     */   {
/* 429 */     JSONArray cmdArray = new JSONArray();
/*     */     try
/*     */     {
/* 432 */       if ((!xmlResp.startsWith("<?xml")) && (xmlResp.indexOf("<?xml") != -1))
/*     */       {
/* 434 */         xmlResp = xmlResp.substring(xmlResp.indexOf("<?xml"));
/*     */       }
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
/* 450 */       ArrayList<Properties> stepsRespList = new ArrayList();
/* 451 */       AMLog.debug("Create Real Browser Test : xmlResponse : " + xmlResp);
/* 452 */       XMLDataReader x = new XMLDataReader(new ByteArrayInputStream(xmlResp.getBytes()));
/* 453 */       XMLNode node = x.getRootNode();
/* 454 */       AMLog.debug("Node Name : " + node.getNodeName());
/* 455 */       if (node.getNodeName().equals("DC"))
/*     */       {
/* 457 */         Hashtable rootNodeProp = node.getAttributeList();
/* 458 */         String availability = "" + rootNodeProp.get("availability");
/* 459 */         String error_code = "" + rootNodeProp.get("error_code");
/* 460 */         String fSId = "" + rootNodeProp.get("fSId");
/* 461 */         String fAId = "" + rootNodeProp.get("fAId");
/* 462 */         String reason = "" + rootNodeProp.get("RES");
/* 463 */         String dfsbid = "" + rootNodeProp.get("dfsbid");
/* 464 */         String dfsfp = "" + rootNodeProp.get("dfsfp");
/*     */         
/* 466 */         Long errorCode = Long.valueOf(Long.parseLong(error_code));
/* 467 */         if (errorCode.longValue() != 0L) {
/* 468 */           reason = new RealBrowserUtil().getMessageForErrorCode(rootNodeProp);
/*     */         }
/* 470 */         Properties scriptResp = new Properties();
/* 471 */         scriptResp.put("AVAIL", availability);
/* 472 */         scriptResp.put("FAILED_STEP_ID", fSId);
/* 473 */         scriptResp.put("FAILED_ACTION_ID", fAId);
/* 474 */         scriptResp.put("FAILED_REASON", reason);
/* 475 */         stepsRespList.add(scriptResp);
/*     */         
/* 477 */         JSONObject transactionResponse = new JSONObject();
/* 478 */         transactionResponse.put("ST", availability);
/* 479 */         transactionResponse.put("RES", reason);
/* 480 */         if (availability.equals("0"))
/*     */         {
/* 482 */           if ((dfsbid != null) && (dfsfp != null))
/*     */           {
/* 484 */             transactionResponse.put("dfsbid", dfsbid);
/* 485 */             transactionResponse.put("dfsfp", dfsfp);
/*     */           }
/*     */           else
/*     */           {
/* 489 */             transactionResponse.put("dfsbid", "");
/* 490 */             transactionResponse.put("dfsfp", "");
/*     */           }
/*     */         }
/*     */         
/* 494 */         cmdArray.put(transactionResponse);
/*     */         
/* 496 */         Vector cNodes = node.getChildNodes();
/* 497 */         if (cNodes != null)
/*     */         {
/* 499 */           for (int i = 0; i < cNodes.size(); i++)
/*     */           {
/* 501 */             XMLNode mNode = (XMLNode)cNodes.elementAt(i);
/* 502 */             Hashtable stepProps = mNode.getAttributeList();
/* 503 */             String mid1 = "" + stepProps.get("mid");
/* 504 */             JSONArray candidateArr = new JSONArray(stepProps.get("candidate").toString());
/* 505 */             JSONArray intelligentCandidateArr = new JSONArray(stepProps.get("intelligentPlayCandidates").toString());
/* 506 */             String respTime1 = "" + stepProps.get("respTime");
/* 507 */             String acc = "" + stepProps.get("acc");
/* 508 */             String ucc = "" + stepProps.get("ucc");
/* 509 */             Properties props = new Properties();
/* 510 */             props.put("STEP_ID", mid1);
/* 511 */             props.put("STEP_CANDIDATES", candidateArr);
/* 512 */             props.put("INTELLIGENT_CANDIDATES", intelligentCandidateArr);
/* 513 */             props.put("STEP_RESP_TIME", respTime1);
/* 514 */             props.put("STEP_AVAIL_STR", acc);
/* 515 */             props.put("STEP_UNAVAIL_STR", ucc);
/* 516 */             String removeCmds = "" + stepProps.get("removecmds");
/* 517 */             props.put("STEP_REMOVE_CMDS", removeCmds);
/*     */             
/* 519 */             stepsRespList.add(props);
/*     */           }
/*     */         }
/*     */         
/* 523 */         boolean isFailedStep = Boolean.FALSE.booleanValue();
/* 524 */         Properties scriptResp1 = (Properties)stepsRespList.get(0);
/* 525 */         String status = (String)scriptResp1.get("AVAIL");
/* 526 */         String reason1 = (String)scriptResp1.get("FAILED_REASON");
/* 527 */         String failedStep = (String)scriptResp1.get("FAILED_STEP_ID");
/* 528 */         String failedActionId = (String)scriptResp1.get("FAILED_ACTION_ID");
/*     */         
/* 530 */         String status1 = "Success";
/* 531 */         String reason11 = "-";
/*     */         
/* 533 */         int stepInd = 0;
/* 534 */         boolean textPresentFailed = false;
/* 535 */         boolean textNotPresentFailed = false;
/* 536 */         int candInd = 0;
/* 537 */         int intelligentCandInd = 0;
/* 538 */         int actionInd = 0;
/* 539 */         Properties scriptResp2 = new Properties();
/* 540 */         JSONArray candArr = new JSONArray();
/* 541 */         JSONArray intelligentCandArr = new JSONArray();
/*     */         
/* 543 */         for (int i = 0; i < al.size(); i++)
/*     */         {
/* 545 */           if (i > 1)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 551 */             JSONObject jo = new JSONObject();
/* 552 */             Properties props = (Properties)al.get(i);
/* 553 */             Object cmdName = props.get("CN");
/* 554 */             Object cmdTar = props.get("CT");
/* 555 */             Object cmdTarCandidates = props.get("CTC");
/*     */             
/* 557 */             if (cmdName != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 562 */               if (cmdName.toString().trim().equals("newStep"))
/*     */               {
/* 564 */                 scriptResp2 = null;
/* 565 */                 candInd = 0;
/* 566 */                 intelligentCandInd = 0;
/* 567 */                 actionInd = 0;
/* 568 */                 stepInd++;
/*     */                 
/* 570 */                 if (isFailedStep) {
/*     */                   break;
/*     */                 }
/*     */                 
/*     */ 
/* 575 */                 if (stepInd < stepsRespList.size())
/*     */                 {
/* 577 */                   scriptResp2 = (Properties)stepsRespList.get(stepInd);
/* 578 */                   String str = (String)scriptResp2.get("STEP_AVAIL_STR");
/* 579 */                   if (str != null)
/*     */                   {
/* 581 */                     str = str.trim();
/* 582 */                     if ((!str.equals("null")) && (str.equals("false")))
/*     */                     {
/* 584 */                       textPresentFailed = true;
/*     */                     }
/*     */                   }
/* 587 */                   str = (String)scriptResp2.get("STEP_UNAVAIL_STR");
/* 588 */                   if (str != null)
/*     */                   {
/* 590 */                     str = str.trim();
/* 591 */                     if ((!str.equals("null")) && (str.equals("false")))
/*     */                     {
/* 593 */                       textNotPresentFailed = true;
/*     */                     }
/*     */                   }
/*     */                 }
/* 597 */                 if (cmdTar.toString().trim().equals(failedStep.trim()))
/*     */                 {
/* 599 */                   isFailedStep = Boolean.TRUE.booleanValue();
/* 600 */                   status1 = "failed";
/* 601 */                   reason11 = reason1;
/*     */                 }
/* 603 */                 props.put("ST", status1);
/* 604 */                 if ((scriptResp2 != null) && (scriptResp2.containsKey("STEP_REMOVE_CMDS")))
/*     */                 {
/* 606 */                   props.put("STEP_REMOVE_CMDS", (String)scriptResp2.get("STEP_REMOVE_CMDS"));
/*     */                 }
/*     */                 
/* 609 */                 if ((!isFailedStep) && (scriptResp2 != null))
/*     */                 {
/* 611 */                   candArr = (JSONArray)scriptResp2.get("STEP_CANDIDATES");
/* 612 */                   intelligentCandArr = (JSONArray)scriptResp2.get("INTELLIGENT_CANDIDATES");
/* 613 */                   props.put("RT", scriptResp2.get("STEP_RESP_TIME"));
/*     */                 }
/*     */                 else
/*     */                 {
/* 617 */                   props.put("RES", reason11);
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 622 */                 actionInd++;
/* 623 */                 props.put("RT", "");
/* 624 */                 if ((status1.equals("failed")) && (actionInd == Integer.parseInt(failedActionId)))
/*     */                 {
/* 626 */                   props.put("RES", reason);
/* 627 */                   props.put("ST", status1);
/*     */                 }
/*     */                 
/* 630 */                 if ((cmdName.toString().trim().equals("verifyTextPresent")) && (textPresentFailed))
/*     */                 {
/* 632 */                   props.put("ST", "trouble");
/* 633 */                   textPresentFailed = false;
/*     */                 }
/* 635 */                 if ((cmdName.toString().trim().equals("verifyTextNotPresent")) && (textNotPresentFailed))
/*     */                 {
/* 637 */                   props.put("ST", "trouble");
/* 638 */                   textNotPresentFailed = false;
/*     */                 }
/* 640 */                 if ((cmdTarCandidates != null) && (cmdTarCandidates.toString().trim().length() > 0))
/*     */                 {
/*     */ 
/* 643 */                   if ((candArr.length() > 0) && (candInd < candArr.length()))
/*     */                   {
/* 645 */                     props.put("CT", (String)candArr.get(candInd++));
/*     */                   }
/* 647 */                   if ((intelligentCandArr.length() > 0) && (intelligentCandInd < intelligentCandArr.length()))
/*     */                   {
/* 649 */                     props.put("CTC", (String)intelligentCandArr.get(intelligentCandInd++));
/*     */                   }
/*     */                 }
/*     */               }
/* 653 */               jo.put("0", props);
/* 654 */               cmdArray.put(jo);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 661 */       e.printStackTrace();
/*     */     }
/* 663 */     return cmdArray;
/*     */   }
/*     */   
/*     */   public String adminServerHandling(Properties reqProps, String operation)
/*     */   {
/*     */     try {
/* 669 */       String agentIds = reqProps.getProperty("SL");
/* 670 */       if (agentIds == null) {
/* 671 */         agentIds = reqProps.getProperty("LID");
/*     */       }
/*     */       
/* 674 */       int eumParentId = -1;
/* 675 */       if ("ADDMON".equals(operation)) {
/* 676 */         String resourceName = reqProps.getProperty("DN");
/* 677 */         reqProps.setProperty("resourceName", resourceName);
/* 678 */         eumParentId = new RealBrowserDC().createMO(reqProps);
/* 679 */         reqProps.setProperty("isAdminMO", "true");
/*     */       }
/*     */       
/* 682 */       JSONObject error = new JSONObject();
/* 683 */       StringBuffer errorMsg = new StringBuffer();
/* 684 */       MultiHashMap managedServerVsAgentIds = groupAgents(agentIds);
/* 685 */       Iterator iterater = managedServerVsAgentIds.keySet().iterator();
/* 686 */       while (iterater.hasNext()) {
/* 687 */         int serverId = ((Integer)iterater.next()).intValue();
/* 688 */         Properties masProps = EnterpriseUtil.getManagedServerProperties(String.valueOf(serverId * EnterpriseUtil.RANGE));
/* 689 */         String masURL = "https://" + masProps.getProperty("HOST") + ":" + masProps.getProperty("SSLPORT") + "/AppManager/json/AddMonitor";
/* 690 */         HttpClient httpClient = new HttpClient();
/* 691 */         MultipartPostMethod method = new MultipartPostMethod(masURL);
/* 692 */         File attachedFile = new File(reqProps.getProperty("fileName"));
/* 693 */         method.addParameter(attachedFile.getName(), attachedFile);
/* 694 */         method.addParameter("type", "RBM");
/* 695 */         Iterator keys = reqProps.keySet().iterator();
/* 696 */         while (keys.hasNext()) {
/* 697 */           String key = (String)keys.next();
/* 698 */           method.addParameter(key, reqProps.getProperty(key));
/*     */         }
/* 700 */         httpClient.executeMethod(method);
/* 701 */         String responseFromMAS = method.getResponseBodyAsString();
/* 702 */         AMLog.debug("Response From Managed Server : " + responseFromMAS);
/*     */         try {
/* 704 */           JSONObject responseObject = new JSONObject(responseFromMAS);
/* 705 */           if ("ADDMON".equals(operation)) {
/* 706 */             if ("success".equals(responseObject.get("status"))) {
/* 707 */               JSONArray childMOs = responseObject.getJSONArray("childMos");
/*     */               
/* 709 */               for (int i = 0; i < childMOs.length(); i++) {
/* 710 */                 int childID = childMOs.getInt(i);
/* 711 */                 mapParentChildRelation(eumParentId, childID);
/*     */               }
/*     */             } else {
/* 714 */               errorMsg.append(FormatUtil.getString("am.rbm.addmonitor.mas.failed", new String[] { masProps.getProperty("HOST"), masProps.getProperty("SSLPORT") })).append("<br>");
/*     */             }
/*     */           } else {
/* 717 */             return responseFromMAS;
/*     */           }
/*     */         } catch (Exception e) {
/* 720 */           e.printStackTrace();
/* 721 */           return e.getMessage();
/*     */         }
/*     */       }
/*     */       
/* 725 */       if (errorMsg.length() > 0) {
/*     */         try {
/* 727 */           error.put("ST", "Failure");
/* 728 */           error.put("RES", errorMsg);
/*     */         } catch (JSONException e) {
/* 730 */           e.printStackTrace();
/*     */         }
/* 732 */         return error.toString();
/*     */       }
/* 734 */       return "success";
/*     */     } catch (NumberFormatException e) {
/* 736 */       e.printStackTrace();
/*     */     } catch (FileNotFoundException e) {
/* 738 */       e.printStackTrace();
/*     */     } catch (HttpException e) {
/* 740 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 742 */       e.printStackTrace();
/*     */     }
/* 744 */     return null;
/*     */   }
/*     */   
/*     */   private MultiHashMap groupAgents(String agentIds) {
/* 748 */     MultiHashMap serverVsAgentMap = new MultiHashMap();
/* 749 */     for (String agentId : agentIds.split(",")) {
/* 750 */       int serverId = Integer.parseInt(agentId) / EnterpriseUtil.RANGE;
/* 751 */       serverVsAgentMap.put(Integer.valueOf(serverId), agentId);
/*     */     }
/* 753 */     if (serverVsAgentMap.isEmpty()) {
/* 754 */       return null;
/*     */     }
/* 756 */     return serverVsAgentMap;
/*     */   }
/*     */   
/*     */   public void mapParentChildRelation(int parentId, int resourceid) {
/* 760 */     AMLog.debug("Adding EUM Dependencies for resid : " + resourceid);
/*     */     try {
/* 762 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 763 */       DBUtil.insertParentChildMapper(parentId, resourceid);
/* 764 */       DBUtil.insertEUMParentChildTable(parentId, resourceid);
/* 765 */       adder.addEUMDependentAttributes(parentId, resourceid, "RBM");
/*     */     } catch (SQLException e) {
/* 767 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\client\selenium\servlets\CreateRealBrowserTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */