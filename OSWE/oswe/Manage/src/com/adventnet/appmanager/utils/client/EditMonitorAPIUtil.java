/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.struts.actions.NewMonitorConf;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.struts.urlmonitor.CreateUrlMonitor;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.mock.MockHttpServletRequest;
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
/*     */ public class EditMonitorAPIUtil
/*     */ {
/*     */   public static String editMonitor(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  50 */     response.setContentType("text/xml; charset=UTF-8");
/*  51 */     AMActionForm amform = new AMActionForm();
/*  52 */     AMLog.debug("REST API : inside EditMonitorAPIUtil");
/*  53 */     String xmlString = "";
/*  54 */     String montype = "";
/*  55 */     if ((request.getParameter("type") != null) && (!request.getParameter("type").equals(""))) {
/*  56 */       montype = request.getParameter("type");
/*     */     }
/*     */     else {
/*  59 */       AMLog.debug("REST API : The type should not be empty.");
/*  60 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.emptymessage"), "4202");
/*  61 */       return xmlString;
/*     */     }
/*     */     try {
/*  64 */       if (montype.equalsIgnoreCase("UrlMonitor")) {
/*  65 */         xmlString = editUrlMonitor(request, response);
/*  66 */       } else if ((montype.equalsIgnoreCase("DNSMonitor")) || (montype.equalsIgnoreCase("SSLCertificateMonitor")) || (montype.equalsIgnoreCase("Service Monitoring")) || (montype.equalsIgnoreCase("Telnet"))) {
/*  67 */         xmlString = editConfMonitor(request, response);
/*     */       } else {
/*  69 */         AMLog.debug("REST API : The type mentioned in the request URL is not supported." + request.getParameter("type"));
/*  70 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.notsupportedmessage"), "4215");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  74 */       e.printStackTrace();
/*  75 */       String errorMsg = e.getMessage();
/*  76 */       AMLog.debug("REST API : Error: " + errorMsg);
/*  77 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + errorMsg, "4444");
/*  78 */       AMLog.debug("REST API : Error in adding monitor by API" + errorMsg);
/*  79 */       return xmlString;
/*     */     }
/*  81 */     return xmlString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String editUrlMonitor(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  90 */     String xmlString = "";
/*  91 */     String monType = "UrlMonitor";
/*  92 */     int pollInterval = 5;
/*  93 */     int timeOut = 5;
/*  94 */     String query1 = "";
/*  95 */     String query2 = "";
/*  96 */     String resID = "";
/*  97 */     String reqParams = "";
/*  98 */     String displayname = "";
/*  99 */     String httpCondition = "";
/* 100 */     String httpValue = "";
/* 101 */     String strUrl = "";
/* 102 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 103 */     MSreq.setContentType("text/xml; charset=UTF-8");
/* 104 */     AMActionForm amform = new AMActionForm();
/* 105 */     MSreq.addParameter("actionmethod", "updateUrlMonitor");
/* 106 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 107 */     ResultSet rs1 = null;
/* 108 */     ResultSet rs2 = null;
/*     */     try {
/* 110 */       AMLog.debug("REST API : inside editUrlMonitor");
/* 111 */       if (request.getParameter("resourceid") != null) {
/* 112 */         query1 = "select AM_ManagedObject.DISPLAYNAME as displayname,AM_URL.URLID as resourceid, AM_URL.URL as url, AM_URL.USERID as userName, " + DBQueryUtil.decodeBytes("password") + " as password, AM_URL.QUERYSTRING as reqParams, AM_URL.METHOD as urlMethod,AM_URL.AVAILABILITYSTRING as checkForContent,AM_URL.PollInterval as pollInterval, AM_URL.UNAVAILABILITYSTRING as errorIfMatch, AM_URL.VERIFY as verifyError,AM_URL.TIMEOUT as timeout from AM_ManagedObject, AM_URL where AM_ManagedObject.RESOURCEID=AM_URL.URLID and AM_ManagedObject.TYPE='UrlMonitor' and AM_URL.URLID='" + request.getParameter("resourceid") + "'";
/*     */       }
/* 114 */       else if (request.getParameter("url") != null) {
/* 115 */         query1 = "select AM_ManagedObject.DISPLAYNAME as displayname,AM_URL.URLID as resourceid, AM_URL.URL as url, AM_URL.USERID as userName, " + DBQueryUtil.decodeBytes("password") + " as password, AM_URL.QUERYSTRING as reqParams, AM_URL.METHOD as urlMethod,AM_URL.AVAILABILITYSTRING as checkForContent,AM_URL.PollInterval as pollInterval, AM_URL.UNAVAILABILITYSTRING as errorIfMatch, AM_URL.VERIFY as verifyError,AM_URL.TIMEOUT as timeout from AM_ManagedObject, AM_URL where AM_ManagedObject.RESOURCEID=AM_URL.URLID and AM_ManagedObject.TYPE='UrlMonitor' and AM_URL.URL like '" + request.getParameter("url") + "'";
/*     */       }
/* 117 */       else if (request.getParameter("displayname") != null) {
/* 118 */         query1 = "select AM_ManagedObject.DISPLAYNAME as displayname,AM_URL.URLID as resourceid, AM_URL.URL as url, AM_URL.USERID as userName, " + DBQueryUtil.decodeBytes("password") + " as password, AM_URL.QUERYSTRING as reqParams, AM_URL.METHOD as urlMethod,AM_URL.AVAILABILITYSTRING as checkForContent,AM_URL.PollInterval as pollInterval, AM_URL.UNAVAILABILITYSTRING as errorIfMatch, AM_URL.VERIFY as verifyError,AM_URL.TIMEOUT as timeout from AM_ManagedObject, AM_URL where AM_ManagedObject.RESOURCEID=AM_URL.URLID and AM_ManagedObject.TYPE='UrlMonitor' and AM_ManagedObject.DISPLAYNAME='" + request.getParameter("displayname") + "'";
/*     */       }
/*     */       else {
/* 121 */         AMLog.debug("REST API : Some parameter missing for edit URL.For editing URL monitor either one of the following 'type with resourceid' or 'type with url' or 'type with displayname' is needed.");
/* 122 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editURL.paramMissing"), "4314");
/* 123 */         return xmlString;
/*     */       }
/*     */       
/* 126 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 127 */       String verifyError; if (rs1.next()) {
/* 128 */         resID = rs1.getString("resourceid");
/* 129 */         reqParams = rs1.getString("reqParams") != null ? rs1.getString("reqParams") : "";
/* 130 */         displayname = rs1.getString("displayname");
/* 131 */         query2 = "select NAME, CRITICALTHRESHOLDCONDITION as httpCondition, CRITICALTHRESHOLDVALUE as httpValue from AM_THRESHOLDCONFIG where DESCRIPTION='##Threshod for URL##'  and NAME='Threshold" + resID + "' order by ID desc";
/*     */         
/* 133 */         MSreq.addParameter("urlid", rs1.getString("resourceid"));
/* 134 */         strUrl = rs1.getString("url");
/* 135 */         if (request.getParameter("timeout") != null) {
/*     */           try {
/* 137 */             timeOut = Integer.parseInt(request.getParameter("timeout"));
/* 138 */             MSreq.addParameter("timeout", "" + timeOut);
/*     */           } catch (Exception exc) {
/* 140 */             AMLog.debug("REST API : The timeout should be a valid one.");
/* 141 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.timeout.notvalidmessage"), "4216");
/* 142 */             return xmlString;
/*     */           }
/*     */         } else {
/* 145 */           timeOut = Integer.parseInt(rs1.getString("timeout"));
/* 146 */           timeOut /= 1000;
/* 147 */           MSreq.addParameter("timeout", "" + timeOut);
/*     */         }
/* 149 */         amform.setTimeout(timeOut);
/* 150 */         if (request.getParameter("pollInterval") != null) {
/*     */           try {
/* 152 */             pollInterval = Integer.parseInt(request.getParameter("pollInterval"));
/*     */             
/* 154 */             MSreq.addParameter("pollInterval", "" + pollInterval);
/*     */           } catch (Exception ex) {
/* 156 */             AMLog.debug("REST API : The pollInterval should be a valid whole number.");
/* 157 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pollinter.notvalidmessage"), "4201");
/* 158 */             return xmlString;
/*     */           }
/*     */         } else {
/* 161 */           pollInterval = Integer.parseInt(rs1.getString("pollInterval"));
/* 162 */           pollInterval /= 60000;
/*     */           
/* 164 */           MSreq.addParameter("pollInterval", "" + pollInterval);
/*     */         }
/* 166 */         amform.setPollInterval(pollInterval);
/* 167 */         if (request.getParameter("urlMethod") != null) {
/* 168 */           if ((request.getParameter("urlMethod").equals("P")) || (request.getParameter("urlMethod").equals("G"))) {
/* 169 */             MSreq.addParameter("method", request.getParameter("urlMethod"));
/*     */           } else {
/* 171 */             AMLog.debug("REST API : The urlMethod should be P or G.");
/* 172 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.urlmethodempty.msg"), "4311");
/* 173 */             return xmlString;
/*     */           }
/*     */         } else {
/* 176 */           MSreq.addParameter("method", rs1.getString("urlMethod"));
/*     */         }
/* 178 */         if (request.getParameter("verifyError") != null) {
/* 179 */           if (request.getParameter("verifyError").equals("true")) {
/* 180 */             MSreq.addParameter("verifyerror", "true");
/* 181 */           } else if (request.getParameter("verifyError").equals("false")) {
/* 182 */             MSreq.addParameter("verifyerror", "");
/*     */           } else {
/* 184 */             AMLog.debug("REST API : The verifyError should be true or false.");
/* 185 */             xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editURL.verifyErrorMsg"), "4315");
/* 186 */             return xmlString;
/*     */           }
/*     */         } else {
/* 189 */           verifyError = "1".equals(rs1.getString("verifyError")) ? "true" : "";
/* 190 */           MSreq.addParameter("verifyerror", verifyError);
/*     */         }
/* 192 */         MSreq.addParameter("userid", request.getParameter("userName") != null ? request.getParameter("userName") : rs1.getString("userName"));
/* 193 */         MSreq.addParameter("errorcontent", request.getParameter("errorIfMatch") != null ? request.getParameter("errorIfMatch") : rs1.getString("errorIfMatch"));
/* 194 */         MSreq.addParameter("checkfor", request.getParameter("checkForContent") != null ? request.getParameter("checkForContent") : rs1.getString("checkForContent"));
/* 195 */         MSreq.addParameter("password", request.getParameter("password") != null ? request.getParameter("password") : rs1.getString("password"));
/* 196 */         MSreq.addParameter("requestparams", request.getParameter("requestParams") != null ? request.getParameter("requestParams") : reqParams);
/*     */       } else {
/* 198 */         AMLog.debug("REST API : The url is not created before.");
/* 199 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editURL.errorMsg"), "4316");
/* 200 */         return xmlString;
/*     */       }
/* 202 */       if (request.getParameter("displayname") != null) {
/* 203 */         displayname = request.getParameter("displayname");
/*     */       }
/* 205 */       if (request.getParameter("url") != null) {
/* 206 */         strUrl = request.getParameter("url");
/*     */       }
/* 208 */       MSreq.addParameter("monitorname", displayname);
/* 209 */       MSreq.addParameter("url", strUrl);
/* 210 */       int httpvalueInt = 200;
/* 211 */       httpCondition = "GT";
/* 212 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 213 */       if (rs2.next())
/*     */       {
/* 215 */         httpCondition = rs2.getString("httpCondition");
/* 216 */         httpvalueInt = Integer.parseInt(rs2.getString("httpValue"));
/*     */       }
/*     */       
/* 219 */       if (request.getParameter("httpCondition") != null) {
/* 220 */         if ((request.getParameter("httpCondition").equals("LT")) || (request.getParameter("httpCondition").equals("GT")) || (request.getParameter("httpCondition").equals("EQ")) || (request.getParameter("httpCondition").equals("NE")) || (request.getParameter("httpCondition").equals("LE")) || (request.getParameter("httpCondition").equals("GE")))
/*     */         {
/* 222 */           httpCondition = request.getParameter("httpCondition");
/*     */         } else {
/* 224 */           AMLog.debug("REST API : The httpCondition should be LT or GT or EQ or NE or LE or GE.");
/* 225 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpcondition.msg"), "4313");
/* 226 */           return xmlString;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 231 */       if (request.getParameter("httpValue") != null) {
/*     */         try {
/* 233 */           httpvalueInt = Integer.parseInt(request.getParameter("httpValue"));
/*     */         } catch (Exception ex) {
/* 235 */           AMLog.debug("REST API : The httpValue should be a valid Integer.");
/* 236 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.httpvalue.integermessage"), "4312");
/* 237 */           return xmlString;
/*     */         }
/*     */       }
/*     */       
/* 241 */       MSreq.addParameter("httpcondition", httpCondition);
/* 242 */       MSreq.addParameter("httpvalue", "" + httpvalueInt);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 250 */         if (rs1 != null) {
/* 251 */           rs1.close();
/*     */         }
/* 253 */         if (rs2 != null) {
/* 254 */           rs2.close();
/*     */         }
/*     */       } catch (Exception ex1) {
/* 257 */         ex1.printStackTrace();
/*     */       }
/*     */       try
/*     */       {
/* 261 */         MSreq.addParameter("type", monType);
/* 262 */         MSreq.addParameter("montype", monType);
/* 263 */         MSreq.addParameter("isAPI", "true");
/* 264 */         amform.setType(monType);
/* 265 */         amform.setDisplayname(displayname);
/*     */         
/* 267 */         CreateUrlMonitor creUrlMon = new CreateUrlMonitor();
/* 268 */         creUrlMon.updateUrlMonitor(null, null, MSreq, response);
/* 269 */         return AddMonitorAPIUtil.MessageXML(request, response, "editUrlMonitor", FormatUtil.getString("URL Monitor edited successfully."));
/*     */       }
/*     */       catch (Exception ex3) {
/* 272 */         ex3.printStackTrace();
/* 273 */         String errorMsg = ex3.getMessage();
/* 274 */         AMLog.debug("REST API : Error: " + errorMsg);
/* 275 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("Error") + ": " + errorMsg, "4444");
/* 276 */         AMLog.debug("REST API : Error in editing monitor by API" + errorMsg);
/*     */       }
/*     */     }
/*     */     catch (Exception ex2)
/*     */     {
/* 244 */       ex2.printStackTrace();
/* 245 */       AMLog.debug("REST API : Server error.");
/* 246 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/* 247 */       return xmlString;
/*     */     } finally {
/*     */       try {
/* 250 */         if (rs1 != null) {
/* 251 */           rs1.close();
/*     */         }
/* 253 */         if (rs2 != null) {
/* 254 */           rs2.close();
/*     */         }
/*     */       } catch (Exception ex1) {
/* 257 */         ex1.printStackTrace();
/*     */       }
/*     */     }
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
/* 277 */     return xmlString;
/*     */   }
/*     */   
/*     */   private static String editConfMonitor(HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 282 */     String xmlString = new String();
/* 283 */     String type = request.getParameter("type");
/* 284 */     if (type.equalsIgnoreCase("Service Monitoring")) {
/* 285 */       type = "Port-Test";
/* 286 */     } else if (type.equalsIgnoreCase("Telnet")) {
/* 287 */       type = "TELNET";
/*     */     }
/*     */     try
/*     */     {
/* 291 */       PreparedStatement ps = null;
/* 292 */       String monTypeTable = "AM_ARGS_" + Constants.getTypeId(type);
/* 293 */       if (request.getParameter("resourceid") != null) {
/* 294 */         AMLog.debug("REST API : Edit " + type + " called for monitor with resourceid : " + request.getParameter("resourceid"));
/* 295 */         StringBuilder query = new StringBuilder("select AM_ManagedObject.RESOURCENAME, AM_ManagedObject.DISPLAYNAME, AM_ScriptArgs.pollinterval, ").append(monTypeTable).append(".*").append(" from AM_ManagedObject, AM_ScriptArgs, ").append(monTypeTable).append(" where AM_ManagedObject.TYPE='").append(type).append("' and AM_ManagedObject.RESOURCEID=AM_ScriptArgs.resourceid and AM_ManagedObject.RESOURCEID=").append(monTypeTable).append(".RESOURCEID and AM_ManagedObject.RESOURCEID=?");
/* 296 */         ps = AMConnectionPool.getConnection().prepareStatement(query.toString());
/* 297 */         ps.setInt(1, Integer.parseInt(request.getParameter("resourceid")));
/* 298 */       } else if (request.getParameter("displayname") != null) {
/* 299 */         AMLog.debug("REST API : Edit " + type + " called for monitor with displayname : " + request.getParameter("displayname"));
/* 300 */         StringBuilder query = new StringBuilder("select AM_ManagedObject.RESOURCENAME, AM_ManagedObject.DISPLAYNAME, AM_ScriptArgs.pollinterval, ").append(monTypeTable).append(".*").append(" from AM_ManagedObject, AM_ScriptArgs, ").append(monTypeTable).append(" where AM_ManagedObject.TYPE='").append(type).append("' and AM_ManagedObject.RESOURCEID=AM_ScriptArgs.resourceid and AM_ManagedObject.RESOURCEID=").append(monTypeTable).append(".RESOURCEID and AM_ManagedObject.DISPLAYNAME=?");
/* 301 */         ps = AMConnectionPool.getConnection().prepareStatement(query.toString());
/* 302 */         ps.setString(1, request.getParameter("displayname"));
/*     */       } else {
/* 304 */         AMLog.debug("REST API : Parameter for edit " + type + " missing. For editing either 'type with resourceid' or 'type with displayname' is needed.");
/* 305 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editMonitor.paramMissing"), "4321");
/*     */       }
/*     */       
/*     */ 
/* 309 */       int resID = 10000000;
/* 310 */       String resourceName = new String();
/* 311 */       String displayName = new String();
/* 312 */       int pollInterval = 5;
/* 313 */       int timeout = 30;
/* 314 */       int port = 443;
/* 315 */       String targetAddress = new String();
/* 316 */       String lookupAddress = new String();
/* 317 */       String recordType = new String("-None-");
/* 318 */       String searchField = new String("-None-");
/* 319 */       String searchValue = new String();
/* 320 */       String isParent = new String("true");
/* 321 */       String isAgentEnabled = new String("NO");
/* 322 */       String isAgentAssociated = new String("false");
/* 323 */       String domain = new String();
/* 324 */       String isProxyNeeded = new String("False");
/* 325 */       String ignoreHostNameError = new String("False");
/* 326 */       String host = new String();
/* 327 */       String command = new String();
/* 328 */       String search = new String();
/* 329 */       boolean isAssociatedtoLocal = false;
/* 330 */       AMActionForm amform = new AMActionForm();
/* 331 */       ResultSet rs = null;
/*     */       try {
/* 333 */         rs = ps.executeQuery();
/* 334 */         String str1; if (rs.next()) {
/* 335 */           resID = rs.getInt("RESOURCEID");
/* 336 */           resourceName = rs.getString("RESOURCENAME");
/* 337 */           displayName = rs.getString("DISPLAYNAME");
/* 338 */           pollInterval = rs.getInt("pollinterval") / 60;
/* 339 */           timeout = Integer.parseInt(rs.getString("Timeout"));
/*     */           
/* 341 */           if (type.equalsIgnoreCase("DNSMonitor")) {
/* 342 */             targetAddress = rs.getString("Target Address");
/* 343 */             lookupAddress = rs.getString("Lookup Address");
/* 344 */             recordType = rs.getString("Record Type");
/* 345 */             searchField = rs.getString("Search Field");
/* 346 */             searchValue = rs.getString("Search Value");
/* 347 */             isParent = rs.getString("isParent");
/*     */           }
/*     */           
/* 350 */           if (type.equalsIgnoreCase("SSLCertificateMonitor")) {
/* 351 */             domain = rs.getString("domain");
/* 352 */             port = Integer.parseInt(rs.getString("port"));
/* 353 */             isProxyNeeded = rs.getString("isProxyNeeded");
/* 354 */             ignoreHostNameError = rs.getString("ignoreHostNameError");
/*     */           }
/*     */           
/* 357 */           if (type.equalsIgnoreCase("Port-Test")) {
/* 358 */             host = rs.getString("host");
/* 359 */             port = Integer.parseInt(rs.getString("port"));
/* 360 */             command = rs.getString("command");
/* 361 */             search = rs.getString("search");
/*     */           }
/*     */           
/* 364 */           if (type.equalsIgnoreCase("Telnet")) {
/* 365 */             host = rs.getString("host");
/* 366 */             port = Integer.parseInt(rs.getString("port"));
/*     */           }
/*     */         }
/*     */         else {
/* 370 */           AMLog.debug("REST API : Edit " + type + " for resourceid : " + request.getParameter("resourceid") + " / display name : " + request.getParameter("displayname") + " does not exist");
/* 371 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editMonitor.noMonitorError"), "4322");
/* 372 */           return xmlString;
/*     */         }
/* 374 */         if (!"true".equalsIgnoreCase(isParent)) {
/* 375 */           AMLog.debug("REST API : Edit " + type + " for resourceid : " + resID + " / resource name : " + resourceName + " does not belongs to parent monitor");
/* 376 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editEUM.notparent"), "4326");
/* 377 */           return xmlString;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 385 */         AMConnectionPool.closeStatement(rs);
/*     */         try {
/* 387 */           if (ps != null) {
/* 388 */             ps.close();
/*     */           }
/*     */         } catch (Exception e1) {
/* 391 */           e1.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 396 */         if (request.getParameter("newdisplayname") == null) {
/*     */           break label1308;
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 380 */         AMLog.debug("REST API : Error occured while editing " + type + " for resourceid : " + request.getParameter("resourceid") + " / display name : " + request.getParameter("displayname") + ". Error : " + e.getMessage());
/* 381 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editMonitor.errorMsg", new String[] { e.getMessage() }), "4320");
/* 382 */         e.printStackTrace();
/* 383 */         return xmlString;
/*     */       } finally {
/* 385 */         AMConnectionPool.closeStatement(rs);
/*     */         try {
/* 387 */           if (ps != null) {
/* 388 */             ps.close();
/*     */           }
/*     */         } catch (Exception e1) {
/* 391 */           e1.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 397 */       displayName = request.getParameter("newdisplayname");
/*     */       
/*     */       label1308:
/* 400 */       if (request.getParameter("pollInterval") != null) {
/*     */         try {
/* 402 */           pollInterval = Integer.parseInt(request.getParameter("pollInterval"));
/*     */         } catch (Exception e) {
/* 404 */           AMLog.debug("REST API : Error occured while editing poll interval value in " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : " + e.getMessage());
/* 405 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pollinter.notvalidmessage"), "4201");
/* 406 */           e.printStackTrace();
/* 407 */           return xmlString;
/*     */         }
/*     */       }
/*     */       
/* 411 */       if (request.getParameter("timeout") != null) {
/*     */         try {
/* 413 */           timeout = Integer.parseInt(request.getParameter("timeout"));
/*     */         } catch (Exception e) {
/* 415 */           AMLog.debug("REST API : Error occured while editing timeout value in " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : " + e.getMessage());
/* 416 */           xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.timeout.notvalidmessage", new String[] { e.getMessage() }), "4216");
/* 417 */           e.printStackTrace();
/* 418 */           return xmlString;
/*     */         }
/*     */       }
/*     */       
/* 422 */       if ((type.equalsIgnoreCase("DNSMonitor")) || (type.equalsIgnoreCase("SSLCertificateMonitor"))) {
/* 423 */         isAgentEnabled = ConfMonitorConfiguration.getInstance().getTypeDescription(type).getProperty("IS-AGENT-ENABLED");
/* 424 */         if ("YES".equalsIgnoreCase(isAgentEnabled)) {
/*     */           try {
/* 426 */             HashMap associationMap = NewMonitorUtil.getAssociatedAgents(String.valueOf(resID), type);
/* 427 */             ArrayList<String> associatedAgents = (ArrayList)associationMap.get("associatedAgents");
/* 428 */             String[] selectedAgents = new String[0];
/* 429 */             if ((associatedAgents != null) && (!associatedAgents.isEmpty())) {
/* 430 */               selectedAgents = (String[])associatedAgents.toArray(selectedAgents);
/* 431 */               amform.setSelectedAgents(selectedAgents);
/* 432 */               isAgentAssociated = "true";
/*     */             }
/* 434 */             isAssociatedtoLocal = ((Boolean)associationMap.get("isAssociatedtoLocal")).booleanValue();
/*     */           } catch (Exception e) {
/* 436 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 442 */       if (type.equalsIgnoreCase("DNSMonitor")) {
/* 443 */         if (request.getParameter("TargetAddress") != null) {
/* 444 */           targetAddress = request.getParameter("TargetAddress");
/*     */         }
/* 446 */         if (request.getParameter("LookupAddress") != null) {
/* 447 */           lookupAddress = request.getParameter("LookupAddress");
/*     */         }
/* 449 */         if (request.getParameter("RecordType") != null) {
/* 450 */           Object validRecordType = Arrays.asList(new String[] { "-None-", "A", "AAAA", "CNAME", "MX", "NS", "PTR", "SOA", "SPF", "SRV", "TXT" });
/* 451 */           if (((List)validRecordType).contains(request.getParameter("RecordType").trim())) {
/* 452 */             recordType = request.getParameter("RecordType").trim();
/*     */           } else {
/* 454 */             AMLog.debug("REST API : Error occured while editing " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : Record Type given - " + request.getParameter("RecordType") + " is invalid");
/* 455 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editDNSMonitor.recordtype.error"), "4323");
/*     */           }
/*     */         }
/*     */         
/* 459 */         if (request.getParameter("SearchField") != null) {
/* 460 */           Object validsearchField = Arrays.asList(new String[] { "-None-", "Record Name", "Address", "Additional Name", "Target", "Admin", "Host", "Alias", "Port", "Priority" });
/* 461 */           if (((List)validsearchField).contains(request.getParameter("SearchField").trim())) {
/* 462 */             searchField = request.getParameter("SearchField").trim();
/*     */           } else {
/* 464 */             AMLog.debug("REST API : Error occured while editing " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : Search Field given - " + request.getParameter("SearchField") + " is invalid");
/* 465 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editDNSMonitor.searchfield.error"), "4324");
/*     */           }
/*     */         }
/*     */         
/* 469 */         if (request.getParameter("SearchValue") != null) {
/* 470 */           searchValue = request.getParameter("SearchValue");
/*     */         }
/*     */       }
/* 473 */       if (type.equalsIgnoreCase("SSLCertificateMonitor")) {
/* 474 */         if (request.getParameter("domain") != null) {
/* 475 */           if (request.getParameter("domain").equals("")) {
/* 476 */             AMLog.debug("REST API : Error occured while editing " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : Search Field given - " + request.getParameter("SearchField") + " is invalid");
/* 477 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.domain.notempty"), "4325");
/*     */           }
/*     */           
/* 480 */           domain = request.getParameter("domain");
/*     */         }
/* 482 */         if (request.getParameter("isProxyNeeded") != null) {
/* 483 */           isProxyNeeded = request.getParameter("isProxyNeeded");
/* 484 */           if ("Yes".equalsIgnoreCase(isProxyNeeded)) {
/* 485 */             isProxyNeeded = "Yes";
/*     */           } else {
/* 487 */             isProxyNeeded = "False";
/*     */           }
/*     */         }
/* 490 */         if (request.getParameter("hostNameError") != null) {
/* 491 */           ignoreHostNameError = request.getParameter("hostNameError");
/* 492 */           if (("Yes".equalsIgnoreCase(ignoreHostNameError)) || ("True".equalsIgnoreCase(ignoreHostNameError))) {
/* 493 */             ignoreHostNameError = "Yes";
/*     */           } else {
/* 495 */             ignoreHostNameError = "False";
/*     */           }
/*     */         }
/* 498 */         if (request.getParameter("port") != null) {
/*     */           try {
/* 500 */             port = Integer.parseInt(request.getParameter("port"));
/*     */           } catch (Exception ex) {
/* 502 */             AMLog.debug("REST API : Error occured while editing port value in " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : " + ex.getMessage());
/* 503 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*     */           }
/*     */         }
/*     */         
/* 507 */         amform.setPort(String.valueOf(port));
/*     */       }
/*     */       
/* 510 */       if (type.equalsIgnoreCase("Port-Test")) {
/* 511 */         if (request.getParameter("command") != null) {
/* 512 */           command = request.getParameter("command");
/*     */         }
/* 514 */         if (request.getParameter("search") != null) {
/* 515 */           search = request.getParameter("search");
/*     */         }
/* 517 */         if (request.getParameter("host") != null) {
/* 518 */           host = request.getParameter("host");
/*     */         }
/* 520 */         if (request.getParameter("port") != null) {
/*     */           try {
/* 522 */             port = Integer.parseInt(request.getParameter("port"));
/*     */           } catch (Exception ex) {
/* 524 */             AMLog.debug("REST API : Error occured while editing port value in " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : " + ex.getMessage());
/* 525 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*     */           }
/*     */         }
/*     */         
/* 529 */         amform.setPort(String.valueOf(port));
/*     */       }
/*     */       
/* 532 */       if (type.equalsIgnoreCase("Telnet")) {
/* 533 */         if (request.getParameter("host") != null) {
/* 534 */           host = request.getParameter("host");
/*     */         }
/* 536 */         if (request.getParameter("port") != null) {
/*     */           try {
/* 538 */             port = Integer.parseInt(request.getParameter("port"));
/*     */           } catch (Exception ex) {
/* 540 */             AMLog.debug("REST API : Error occured while editing port value in " + type + " for resourceid : " + resID + " / resource name : " + resourceName + ". Error : " + ex.getMessage());
/* 541 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.port.notemptymessage"), "4218");
/*     */           }
/*     */         }
/*     */         
/* 545 */         amform.setPort(String.valueOf(port));
/*     */       }
/*     */       
/*     */ 
/* 549 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 550 */       MSreq.setContentType("text/xml; charset=UTF-8");
/* 551 */       MSreq.addParameter("montype", type);
/* 552 */       MSreq.addParameter("resourceid", String.valueOf(resID));
/* 553 */       MSreq.addParameter("method", "updateMonitor");
/* 554 */       MSreq.addParameter("haid", "-");
/* 555 */       MSreq.addParameter("UT", String.valueOf(System.currentTimeMillis()));
/* 556 */       MSreq.addParameter("displayname", displayName);
/* 557 */       MSreq.addParameter("pollinterval", String.valueOf(pollInterval));
/* 558 */       MSreq.addParameter("Timeout", String.valueOf(timeout));
/* 559 */       MSreq.addParameter("isAgentEnabled", isAgentEnabled);
/* 560 */       MSreq.addParameter("isAgentAssociated", isAgentAssociated);
/* 561 */       if (isAssociatedtoLocal) {
/* 562 */         MSreq.addParameter("runOnServer", "runOnServer");
/*     */       }
/*     */       
/* 565 */       if (type.equalsIgnoreCase("DNSMonitor")) {
/* 566 */         MSreq.addParameter("Target Address", targetAddress);
/* 567 */         MSreq.addParameter("Lookup Address", lookupAddress);
/* 568 */         MSreq.addParameter("Record Type", recordType);
/* 569 */         MSreq.addParameter("Search Field", searchField);
/* 570 */         MSreq.addParameter("Search Value", searchValue);
/* 571 */         MSreq.addParameter("isParent", isParent);
/*     */       }
/*     */       
/* 574 */       if (type.equalsIgnoreCase("SSLCertificateMonitor")) {
/* 575 */         MSreq.addParameter("domain", domain);
/* 576 */         MSreq.addParameter("port", String.valueOf(port));
/* 577 */         MSreq.addParameter("isProxyNeeded", isProxyNeeded);
/* 578 */         MSreq.addParameter("ignoreHostNameError", ignoreHostNameError);
/*     */       }
/*     */       
/* 581 */       if (type.equalsIgnoreCase("Port-Test")) {
/* 582 */         MSreq.addParameter("host", host);
/* 583 */         MSreq.addParameter("command", command);
/* 584 */         MSreq.addParameter("search", search);
/* 585 */         MSreq.addParameter("port", String.valueOf(port));
/* 586 */         MSreq.addParameter("timeout", String.valueOf(timeout));
/*     */       }
/*     */       
/* 589 */       if (type.equalsIgnoreCase("Telnet")) {
/* 590 */         MSreq.addParameter("host", host);
/* 591 */         MSreq.addParameter("port", String.valueOf(port));
/* 592 */         MSreq.addParameter("timeout", String.valueOf(timeout));
/*     */       }
/*     */       
/* 595 */       NewMonitorConf newMonitorConf = new NewMonitorConf();
/* 596 */       newMonitorConf.updateMonitor(null, amform, MSreq, response);
/*     */       
/* 598 */       ActionErrors errors = (ActionErrors)MSreq.getAttribute("org.apache.struts.action.ERROR");
/* 599 */       if (errors != null) {
/* 600 */         Iterator i = errors.properties();
/* 601 */         if (i.hasNext()) {
/* 602 */           String name = (String)i.next();
/* 603 */           Iterator it = errors.get(name);
/* 604 */           if (it.hasNext()) {
/* 605 */             ActionError a = (ActionError)it.next();
/* 606 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editMonitor.errorMsg", new String[] { a.getKey() }), "4320");
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 611 */       xmlString = AddMonitorAPIUtil.MessageXML(request, response, "editServiceMonitor", FormatUtil.getString((type.equalsIgnoreCase("Port-Test") ? "Service Monitoring" : type) + " edited successfully."));
/*     */     } catch (Exception exp) {
/* 613 */       AMLog.debug("REST API : Error occured while editing polling interval value of " + type + " for resourceid : " + request.getParameter("resourceid") + " / display name : " + request.getParameter("displayname") + ". Error : " + exp.getMessage());
/* 614 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.editMonitor.errorMsg", new String[] { exp.getMessage() }), "4320");
/* 615 */       exp.printStackTrace();
/* 616 */       return xmlString;
/*     */     }
/* 618 */     return xmlString;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\EditMonitorAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */