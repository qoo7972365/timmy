/*     */ package com.me.apm.client.selenium.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AgentUtil;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.struts.actions.ShowCustomDetails;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.RealBrowserUtil;
/*     */ import com.me.apm.server.selenium.datacollection.RealBrowserDC;
/*     */ import com.me.apm.server.selenium.datacollection.RealBrowserException;
/*     */ import com.me.apm.server.selenium.util.HTMLToJythonConverter;
/*     */ import com.me.apm.server.selenium.util.RealBrowserFileUtil;
/*     */ import com.me.apm.server.selenium.util.ScriptUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ import org.apache.struts.util.LabelValueBean;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
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
/*     */ public class SeleniumActions
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward authenticate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  90 */     Enumeration<String> names = request.getParameterNames();
/*     */     
/*  92 */     String username = request.getParameter("username");
/*  93 */     String password = request.getParameter("password");
/*  94 */     ResultSet result = null;
/*  95 */     JSONObject toReturn = null;
/*  96 */     PrintWriter writer = null;
/*     */     try {
/*  98 */       result = AMConnectionPool.executeQueryStmt("select GROUPNAME,APIKEY from AM_UserPasswordTable,AM_UserGroupTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and AM_UserPasswordTable.USERNAME='" + username + "' and PASSWORD=" + DBQueryUtil.MD5(password));
/*  99 */       toReturn = new JSONObject();
/* 100 */       writer = response.getWriter();
/* 101 */       if (result.next()) {
/* 102 */         String role = result.getString("GROUPNAME");
/* 103 */         if (role.equals("ADMIN")) {
/* 104 */           toReturn.put("state", "success");
/* 105 */           toReturn.put("apikey", result.getString("APIKEY"));
/*     */         } else {
/* 107 */           toReturn.put("error", username + "," + FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/*     */         }
/*     */       } else {
/* 110 */         toReturn.put("error", FormatUtil.getString("webclient.login.loginfailed.message"));
/*     */       }
/*     */     } catch (SQLException e) {
/* 113 */       e.printStackTrace();
/*     */     } catch (JSONException e) {
/* 115 */       e.printStackTrace();
/*     */     } finally {
/* 117 */       AMConnectionPool.closeStatement(result);
/*     */     }
/*     */     
/* 120 */     writer.print(toReturn.toString());
/* 121 */     writer.flush();
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getAgentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
/*     */   {
/*     */     try
/*     */     {
/*     */       try {
/* 130 */         JSONObject agentProps = RealBrowserUtil.getEUMAgentList();
/* 131 */         AMLog.debug("Agent List Props : " + agentProps);
/* 132 */         agentProps.put("apikey", true);
/* 133 */         agentProps.put("status", "success");
/*     */         
/* 135 */         PrintWriter writer = new PrintWriter(response.getOutputStream());
/*     */         
/* 137 */         writer.print(agentProps.toString());
/* 138 */         writer.flush();
/*     */       } catch (JSONException e) {
/* 140 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (SQLException e) {
/* 144 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 148 */       e.printStackTrace();
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward saveScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 159 */     if ((!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN"))) {
/* 160 */       request.setAttribute("errMessage", FormatUtil.getString("am.webclient.selenium.script.edit.restricted"));
/* 161 */       return new ActionForward("/jsp/RealBrowserScriptViewer.jsp");
/*     */     }
/* 163 */     String resourceId = request.getParameter("resourceid");
/*     */     
/* 165 */     String script = request.getParameter("RBMscript");
/* 166 */     String scriptLocation = RealBrowserUtil.getScriptPathForResource(resourceId);
/* 167 */     ScriptUtil scriptUtil = new ScriptUtil();
/*     */     try {
/* 169 */       boolean showPassword = Boolean.valueOf(request.getParameter("showPassword")).booleanValue();
/* 170 */       AMLog.info("SeleniumActions : saveScript : script : " + script);
/* 171 */       AMLog.audit("RealBrowser : Script Edit : By user : " + request.getRemoteUser() + "  From host : " + request.getRemoteHost());
/* 172 */       AMLog.audit("RealBrowser : Script Edit : Edited content : " + script);
/* 173 */       script = StringEscapeUtils.unescapeHtml4(script);
/* 174 */       script = scriptUtil.validateScript(script, resourceId, !showPassword);
/* 175 */       RealBrowserUtil.modiFyRBMScript(resourceId, script);
/* 176 */       request.setAttribute("message", FormatUtil.getString("am.webclient.selenium.script.modified.success"));
/*     */     } catch (Exception e) {
/* 178 */       script = new ScriptUtil().getScript(scriptLocation);
/* 179 */       e.printStackTrace();
/* 180 */       request.setAttribute("errMessage", FormatUtil.getString("am.webclient.selenium.script.modified.failed", new String[] { e.getMessage() }));
/*     */     }
/*     */     try {
/* 183 */       script = scriptUtil.scriptPasswordManager(script, "hidePassword");
/*     */     } catch (RealBrowserException re) {
/* 185 */       re.printStackTrace();
/*     */     }
/* 187 */     request.setAttribute("showPassword", "true");
/* 188 */     request.setAttribute("script", script);
/* 189 */     request.setAttribute("resourceid", resourceId);
/*     */     
/* 191 */     return new ActionForward("/jsp/RealBrowserScriptViewer.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward showSeleniumTestCaseImporter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 199 */     AMActionForm sform = (AMActionForm)form;
/* 200 */     Collection agents = new ArrayList();
/* 201 */     ArrayList<Properties> eumAgents = RealBrowserUtil.getRunningEUMAgentList();
/* 202 */     for (Properties agentProps : eumAgents) {
/* 203 */       agents.add(new LabelValueBean(agentProps.getProperty("agentName"), agentProps.getProperty("agentId")));
/*     */     }
/*     */     
/* 206 */     request.setAttribute("agents", agents);
/* 207 */     request.setAttribute("isRBM", "true");
/* 208 */     return new ActionForward("/jsp/RealBrowserSeleniumTCImporter.jsp");
/*     */   }
/*     */   
/*     */   public ActionForward importSeleniumTestCase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 214 */     AMActionForm fForm = (AMActionForm)form;
/* 215 */     FormFile file = fForm.getRbmTCfile();
/* 216 */     String filePath = RealBrowserUtil.getTestScriptLocation();
/* 217 */     File folder = new File(filePath);
/* 218 */     if (!folder.exists()) {
/* 219 */       folder.mkdirs();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 226 */     if ((file.getFileSize() < 1) || (file.getFileSize() > 5242880)) {
/* 227 */       request.setAttribute("errMessage", FormatUtil.getString("am.eumAgent.istc.filesize"));
/* 228 */       return new ActionForward("/jsp/RealBrowserSeleniumTCImporter.jsp");
/*     */     }
/*     */     
/* 231 */     boolean isTestPlayback = false;
/* 232 */     if ("true".equals(request.getParameter("testPlay"))) {
/* 233 */       isTestPlayback = true;
/*     */     }
/* 235 */     String moName = fForm.getRbmDisplayName();
/*     */     
/* 237 */     if (isTestPlayback) {
/* 238 */       moName = moName + "-TestSource" + System.currentTimeMillis();
/*     */     }
/* 240 */     String[] agentIdArray = fForm.getSelectedAgents();
/* 241 */     String fileName = file.getFileName();
/* 242 */     StringBuffer agentIds = new StringBuffer();
/*     */     
/*     */ 
/* 245 */     if (isTestPlayback) {
/* 246 */       agentIds.append(agentIdArray[0]);
/*     */     } else {
/* 248 */       for (String agentId : agentIdArray) {
/* 249 */         agentIds.append(agentId).append(",");
/*     */       }
/*     */     }
/*     */     
/* 253 */     File newFile = new File(filePath, fileName);
/*     */     
/* 255 */     if (!newFile.exists()) {
/* 256 */       FileOutputStream fos = null;
/*     */       try {
/* 258 */         fos = new FileOutputStream(newFile);
/* 259 */         fos.write(file.getFileData());
/* 260 */         fos.flush();
/*     */         
/*     */ 
/*     */ 
/* 264 */         if (fos != null) {
/*     */           try {
/* 266 */             fos.close();
/*     */           } catch (Exception e) {
/* 268 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 276 */         rfu = new RealBrowserFileUtil();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 262 */         e.printStackTrace();
/*     */       } finally {
/* 264 */         if (fos != null) {
/*     */           try {
/* 266 */             fos.close();
/*     */           } catch (Exception e) {
/* 268 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     RealBrowserFileUtil rfu;
/*     */     
/* 277 */     String content = rfu.readFileContentToString(newFile);
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 282 */       RealBrowserUtil.validateFile(content);
/* 283 */       HTMLToJythonConverter htmlToJythonConverter = new HTMLToJythonConverter();
/* 284 */       htmlToJythonConverter.setSeleniumTestCase(true);
/* 285 */       Properties pythonScriptProps = htmlToJythonConverter.getJythonScript(content);
/* 286 */       String script = pythonScriptProps.getProperty("jythonScript");
/* 287 */       String scriptFile = null;
/*     */       try {
/* 289 */         scriptFile = RealBrowserUtil.getScriptLocation() + File.separator + moName + File.separator + moName + "." + RealBrowserUtil.getFileExtension();
/* 290 */         if (isTestPlayback) {
/* 291 */           scriptFile = RealBrowserUtil.getTestScriptLocation() + File.separator + moName + "." + RealBrowserUtil.getFileExtension();
/*     */         }
/* 293 */         rfu.saveAsFileInToDisk(scriptFile, script);
/*     */       } catch (Exception e) {
/* 295 */         e.printStackTrace();
/*     */       }
/*     */       
/* 298 */       Properties newMOProps = new Properties();
/* 299 */       newMOProps.setProperty("script", script);
/* 300 */       newMOProps.setProperty("thinkTime", "3");
/* 301 */       newMOProps.setProperty("browserType", "1");
/* 302 */       newMOProps.setProperty("resourceName", moName);
/* 303 */       newMOProps.setProperty("displayName", moName);
/* 304 */       newMOProps.setProperty("agentIds", agentIds.toString());
/* 305 */       newMOProps.setProperty("collectionTime", String.valueOf(System.currentTimeMillis()));
/*     */       
/*     */ 
/*     */ 
/* 309 */       RealBrowserDC realBrowserDC = new RealBrowserDC();
/* 310 */       if (isTestPlayback) {
/* 311 */         String testResult = null;
/*     */         try {
/* 313 */           String scriptFileName = moName + "." + RealBrowserUtil.getFileExtension();
/* 314 */           RealBrowserUtil.setScriptPropsCache(scriptFileName, newMOProps);
/* 315 */           newMOProps.setProperty("scriptName", scriptFileName);
/* 316 */           testResult = realBrowserDC.testPlayBack(newMOProps);
/* 317 */           Hashtable testPlaybackProps = RealBrowserUtil.getAttributes(testResult);
/* 318 */           request.setAttribute("script", script);
/* 319 */           request.setAttribute("resourceid", Long.valueOf(System.currentTimeMillis()));
/*     */           
/* 321 */           String errorMsg = new RealBrowserUtil().getMessageForErrorCode(testPlaybackProps);
/* 322 */           if (errorMsg != null) {
/* 323 */             request.setAttribute("errMessage", errorMsg);
/*     */           } else {
/* 325 */             request.setAttribute("message", FormatUtil.getString("am.webclient.rbm.testPlayback.success"));
/*     */           }
/*     */         } catch (RealBrowserException e) {
/* 328 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/*     */         try {
/* 332 */           if (RealBrowserUtil.checkResourceAlreadyExist(moName)) {
/* 333 */             request.setAttribute("errMessage", FormatUtil.getString("am.eumAgent.mo.exist", new String[] { moName }));
/*     */           } else {
/* 335 */             int moID = realBrowserDC.createMO(newMOProps);
/* 336 */             request.setAttribute("message", FormatUtil.getString("am.webclient.api.addmonitor.succmessage"));
/*     */           }
/*     */         } catch (Exception e) {
/* 339 */           request.setAttribute("errMessage", FormatUtil.getString("am.webclient.rbm.addmo.failure", new String[] { e.getMessage() }));
/* 340 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 344 */       e.printStackTrace();
/* 345 */       request.setAttribute("errMessage", FormatUtil.getString("am.webclient.rbm.addmo.failure", new String[] { e.getMessage() }));
/*     */     }
/*     */     
/* 348 */     Collection agents = new ArrayList();
/* 349 */     Object eumAgents = RealBrowserUtil.getRunningEUMAgentList();
/* 350 */     for (Properties agentProps : (ArrayList)eumAgents) {
/* 351 */       agents.add(new LabelValueBean(agentProps.getProperty("agentName"), agentProps.getProperty("agentId")));
/*     */     }
/*     */     
/* 354 */     ((AMActionForm)form).setRbmTCfile(file);
/* 355 */     request.setAttribute("agents", agents);
/*     */     
/* 357 */     return new ActionForward("/jsp/RealBrowserSeleniumTCImporter.jsp");
/*     */   }
/*     */   
/*     */   public ActionForward showScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 363 */     String resourceId = request.getParameter("resourceid");
/* 364 */     String childId = "";
/* 365 */     if (!NewMonitorUtil.isEUMParent(resourceId, "RBM")) {
/* 366 */       childId = resourceId;
/* 367 */       resourceId = DBUtil.getParentID(Integer.parseInt(resourceId), "RBM");
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 373 */         childId = DBUtil.getChildIdsFromParentChildMapper(Integer.parseInt(resourceId)).get(0).toString();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 377 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try {
/* 381 */       ScriptUtil scriptUtil = new ScriptUtil();
/* 382 */       String scriptLocation = RealBrowserUtil.getScriptPathForResource(resourceId);
/* 383 */       if (scriptLocation == null)
/*     */       {
/* 385 */         if (childId != "")
/*     */         {
/* 387 */           String query = "select SCRIPT from AM_RBMDATA where RESOURCEID=" + childId;
/* 388 */           ResultSet rst = null;
/*     */           try
/*     */           {
/* 391 */             rst = AMConnectionPool.executeQueryStmt(query);
/* 392 */             if (rst.next())
/*     */             {
/* 394 */               return new ActionForward("/jsp/RBMScriptManager.jsp?from=urlseqdetails&bcname=" + rst.getString(1) + "&tab=webscripttab");
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 399 */             e.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/* 403 */             AMConnectionPool.closeResultSet(rst);
/*     */           }
/* 405 */           throw new Exception(FormatUtil.getString("am.rbm.script.notavailable"));
/*     */         }
/*     */         
/*     */ 
/* 409 */         throw new Exception(FormatUtil.getString("am.rbm.script.notavailable"));
/*     */       }
/*     */       
/* 412 */       String script = new ScriptUtil().getScript(scriptLocation);
/* 413 */       if ("true".equals(request.getParameter("showPassword"))) {
/* 414 */         script = scriptUtil.scriptPasswordManager(script, "showPassword");
/* 415 */         request.setAttribute("showPassword", "false");
/*     */       } else {
/* 417 */         script = scriptUtil.scriptPasswordManager(script, "hidePassword");
/* 418 */         request.setAttribute("showPassword", "true");
/*     */       }
/* 420 */       AMLog.info("SeleniumActions : showScript : script : " + script);
/* 421 */       request.setAttribute("script", script);
/* 422 */       request.setAttribute("resourceid", resourceId);
/*     */     } catch (Exception e) {
/* 424 */       e.printStackTrace();
/* 425 */       request.setAttribute("errMessage", e.getMessage());
/*     */     }
/* 427 */     return new ActionForward("/jsp/RealBrowserScriptViewer.jsp");
/*     */   }
/*     */   
/*     */   public ActionForward showScreenShots(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 433 */     String resourceId = request.getParameter("resourceid");
/* 434 */     ArrayList<String> imgPaths = new ArrayList();
/* 435 */     Properties argsAsProps = NewMonitorUtil.getArgsasProps("RBM", resourceId);
/* 436 */     if ((!"true".equalsIgnoreCase(argsAsProps.getProperty("isScreenShot"))) && (!"true".equalsIgnoreCase(argsAsProps.getProperty("showPerformance")))) {
/* 437 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 441 */     response.setContentType("text/html;charset=UTF-8");
/* 442 */     String perfFilePath = "";
/* 443 */     boolean showHistory = false;
/* 444 */     if ("true".equalsIgnoreCase(argsAsProps.getProperty("isScreenShot")))
/*     */     {
/* 446 */       ResultSet rs = null;
/* 447 */       String imgPath = null;
/* 448 */       String sTime = request.getParameter("sTime");
/* 449 */       if (request.getParameter("showHistory") != null) {
/* 450 */         showHistory = Boolean.valueOf(request.getParameter("showHistory")).booleanValue();
/*     */       }
/* 452 */       request.setAttribute("sTimes", RealBrowserUtil.getAllSSCTimes(resourceId));
/*     */       try {
/* 454 */         String imgPathQry = "select IMAGELOCATION,COLLECTIONTIME from AM_SELENIUMIMAGEDATA where RESOURCEID=" + resourceId + " order by COLLECTIONTIME desc";
/* 455 */         if (sTime != null) {
/* 456 */           imgPathQry = "select IMAGELOCATION,COLLECTIONTIME from AM_SELENIUMIMAGEDATA where RESOURCEID=" + resourceId + " and COLLECTIONTIME=" + sTime;
/*     */         } else {
/* 458 */           imgPathQry = DBQueryUtil.getTopNValues(imgPathQry, 1);
/*     */         }
/*     */         
/* 461 */         rs = AMConnectionPool.executeQueryStmt(imgPathQry);
/* 462 */         if (rs.next()) {
/* 463 */           imgPath = rs.getString("IMAGELOCATION");
/* 464 */           sTime = rs.getString("COLLECTIONTIME");
/*     */         }
/*     */       } catch (SQLException e) {
/* 467 */         e.printStackTrace();
/*     */       } finally {
/* 469 */         AMConnectionPool.closeResultSet(rs);
/*     */       }
/* 471 */       request.setAttribute("selTime", sTime);
/* 472 */       if (imgPath != null) {
/* 473 */         String imgDirLoc = RealBrowserUtil.getScreenshotsLocation() + File.separator + imgPath;
/*     */         
/* 475 */         File imgDir = new File(imgDirLoc);
/* 476 */         if (imgDir.isDirectory())
/*     */         {
/* 478 */           imgPath = URLEncoder.encode(imgPath, "UTF-8").replace("+", "%20");
/* 479 */           Collection<File> imgFiles = FileUtils.listFiles(imgDir, new String[] { "png" }, true);
/* 480 */           for (File file : imgFiles) {
/* 481 */             String filePath = imgPath + "/" + file.getName();
/* 482 */             imgPaths.add(filePath);
/*     */           }
/*     */         }
/*     */       }
/* 486 */       if (showHistory) {
/* 487 */         request.setAttribute("showHistory", Boolean.valueOf(showHistory));
/*     */       }
/*     */     }
/* 490 */     if (!imgPaths.isEmpty()) {
/* 491 */       request.setAttribute("images", imgPaths);
/*     */     }
/* 493 */     if (("true".equalsIgnoreCase(argsAsProps.getProperty("showPerformance"))) && (!showHistory))
/*     */     {
/* 495 */       String scriptLocation = RealBrowserUtil.getScriptPathForResource(resourceId, true);
/* 496 */       String[] fileNames = scriptLocation.split("\\\\");
/* 497 */       String parentName = "";
/*     */       try
/*     */       {
/* 500 */         File file = new File(scriptLocation);
/* 501 */         parentName = file.getName();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 505 */         AMLog.debug("Error in file creation for : " + scriptLocation);
/* 506 */         e.printStackTrace();
/*     */       }
/*     */       
/* 509 */       long collTime = new ShowCustomDetails().getMaxCollectionTime(resourceId, "RBM", false);
/* 510 */       if (collTime != 0L)
/*     */       {
/* 512 */         SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
/* 513 */         String fileName = sdf.format(new Date(collTime));
/*     */         
/* 515 */         if ("true".equalsIgnoreCase(System.getProperty("DEMOUSER")))
/*     */         {
/* 517 */           fileName = "31_05_2016_23_58";
/* 518 */           AMLog.info("Demo user setup so performance metrics file name is : " + fileName);
/*     */         }
/* 520 */         String monName = DBUtil.getResourceNameFromId(resourceId);
/* 521 */         perfFilePath = parentName + "/" + monName + "/Perf_Metrics/" + fileName + ".json";
/* 522 */         AMLog.debug("Selenium Actions : Performance File Path :" + perfFilePath);
/* 523 */         File perfFile = new File("./projects/webscripts/" + perfFilePath);
/* 524 */         if (perfFile.exists())
/*     */         {
/*     */ 
/* 527 */           perfFilePath = (URLEncoder.encode(parentName, "UTF-8") + "/" + URLEncoder.encode(monName, "UTF-8") + "/Perf_Metrics/" + fileName + ".json").replace("+", "%20");
/* 528 */           request.setAttribute("perfFileLoc", perfFilePath);
/* 529 */           AMLog.debug("Selenium Actions : Encoded Performance File Path :" + perfFilePath);
/*     */         }
/*     */         else
/*     */         {
/* 533 */           String agentId = RealBrowserUtil.getAgentId(resourceId);
/* 534 */           String agentVer = new AgentUtil().getAgentVersion(agentId);
/* 535 */           if (Integer.parseInt(agentVer.replace(".", "")) < 1200)
/*     */           {
/* 537 */             request.setAttribute("errMsg", FormatUtil.getString("am.webclient.eum.perfmetrics.upgrade.required"));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 542 */     request.setAttribute("resourceid", resourceId);
/* 543 */     RequestDispatcher rd = request.getRequestDispatcher("/jsp/RealBrowserImageViewer.jsp");
/* 544 */     rd.include(request, response);
/*     */     
/* 546 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward doTestPlayBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 552 */     String script = request.getParameter("RBMscript");
/* 553 */     script = StringEscapeUtils.unescapeHtml4(script);
/* 554 */     String resourceId = request.getParameter("resourceid");
/* 555 */     HashMap agentMap = NewMonitorUtil.getAssociatedAgents(resourceId, "RBM");
/*     */     
/* 557 */     String currentTime = System.currentTimeMillis() + "";
/* 558 */     String scriptName = "testSource_" + System.currentTimeMillis() + "." + RealBrowserUtil.getFileExtension();
/* 559 */     new RealBrowserFileUtil().saveAsFileInToDisk(RealBrowserUtil.getTestScriptLocation() + File.separator + scriptName, script);
/*     */     
/* 561 */     String agentId = RealBrowserUtil.getAgentId(resourceId);
/* 562 */     ScriptUtil scriptUtil = new ScriptUtil();
/*     */     try {
/* 564 */       if ("true".equals(request.getParameter("showPassword"))) {
/* 565 */         script = scriptUtil.scriptPasswordManager(script, "showPassword");
/* 566 */         request.setAttribute("showPassword", "false");
/*     */       } else {
/* 568 */         script = scriptUtil.scriptPasswordManager(script, "hidePassword");
/* 569 */         request.setAttribute("showPassword", "true");
/*     */       }
/*     */     } catch (RealBrowserException e) {
/* 572 */       e.printStackTrace();
/* 573 */       request.setAttribute("errMessage", e.getMessage());
/* 574 */       return new ActionForward("/jsp/RealBrowserScriptViewer.jsp");
/*     */     }
/*     */     
/* 577 */     if (agentId == null) {
/* 578 */       agentId = "-1";
/*     */     }
/* 580 */     int thinkTime = 10;
/* 581 */     int pageLoadTime = 30;
/* 582 */     Properties props = new Properties();
/* 583 */     props.put("monitorId", currentTime);
/* 584 */     props.put("lastUpdatedTime", currentTime);
/* 585 */     props.put("userId", "-1");
/* 586 */     props.put("collectionTime", currentTime);
/* 587 */     props.put("browserType", Integer.valueOf(1));
/* 588 */     props.put("thinkTime", Integer.valueOf(thinkTime));
/* 589 */     props.put("pageLoadTime", Integer.valueOf(pageLoadTime));
/*     */     
/* 591 */     props.setProperty("scriptName", scriptName);
/* 592 */     props.setProperty("agentIds", agentId);
/* 593 */     RealBrowserUtil.setScriptPropsCache(scriptName, props);
/* 594 */     String pbResponse = "";
/*     */     try
/*     */     {
/* 597 */       pbResponse = new RealBrowserDC().testPlayBack(props);
/*     */     }
/*     */     catch (RealBrowserException e)
/*     */     {
/* 601 */       e.printStackTrace();
/*     */     }
/* 603 */     AMLog.debug("Test Playback Result : " + pbResponse);
/* 604 */     Hashtable testPlaybackProps = RealBrowserUtil.getAttributes(pbResponse);
/* 605 */     request.setAttribute("script", script);
/* 606 */     request.setAttribute("resourceid", resourceId);
/*     */     
/* 608 */     String errorMsg = new RealBrowserUtil().getMessageForErrorCode(testPlaybackProps);
/* 609 */     if (errorMsg != null) {
/* 610 */       request.setAttribute("errMessage", errorMsg);
/*     */     } else {
/* 612 */       request.setAttribute("message", FormatUtil.getString("am.webclient.rbm.testPlayback.success"));
/*     */     }
/* 614 */     return new ActionForward("/jsp/RealBrowserScriptViewer.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */   public void isEUMParent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/* 622 */       PrintWriter out = response.getWriter();
/* 623 */       out.println(DBUtil.isEUMParent(request.getParameter("resid")));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 627 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\client\selenium\actions\SeleniumActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */