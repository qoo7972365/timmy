/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMBatchStmtExecutor;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMPredefinedActionConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.AMPredefinedThresholdConfiguration;
/*      */ import com.adventnet.appmanager.struts.actions.AdminActions;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
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
/*      */ public class ThresholdActionsAPIUtil
/*      */ {
/*      */   public static String createThreshold(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/*   47 */     outputString = null;
/*      */     try
/*      */     {
/*   50 */       if (isJsonFormat) {
/*   51 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*   53 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*   56 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*   57 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*   60 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*   61 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*   62 */       MSreq.addParameter("method", "createThresholdAction");
/*   63 */       MSreq.addParameter("cancel", "false");
/*      */       
/*   65 */       int thresholdType = 0;
/*      */       
/*   67 */       if (request.getParameter("thresholdname") != null) {
/*   68 */         if (!DBQueryUtil.checkForDuplicateEntry("AM_THRESHOLDCONFIG", "NAME", request.getParameter("thresholdname"), "")) {
/*   69 */           MSreq.addParameter("displayname", request.getParameter("thresholdname"));
/*      */         } else {
/*   71 */           return APIUtilities.duplicateNameResponse(request, response, "Threshold : create", "Threshold Name");
/*      */         }
/*      */       } else {
/*   74 */         return APIUtilities.emptyParameterResponse(request, response, "Threshold : create", "thresholdname");
/*      */       }
/*      */       
/*      */ 
/*   78 */       if (request.getParameter("type") != null) {
/*   79 */         if (Constants.isIntegerNumber(request.getParameter("type"))) {
/*   80 */           thresholdType = Integer.parseInt(request.getParameter("type"));
/*   81 */           if ((thresholdType != 1) && (thresholdType != 3) && (thresholdType != 4)) {
/*   82 */             AMLog.debug("REST API : Threshold Type should be either 1 3 or 4");
/*   83 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.type.wrong.text"), "4102");
/*      */           }
/*      */         }
/*      */         else {
/*   87 */           return APIUtilities.defaultIntegerResponse(request, response, "Threshold create", "type");
/*      */         }
/*      */       }
/*      */       else {
/*   91 */         return APIUtilities.emptyParameterResponse(request, response, "Threshold : create", "type");
/*      */       }
/*      */       
/*      */ 
/*   95 */       if (request.getParameter("description") != null) {
/*   96 */         MSreq.addParameter("description", request.getParameter("description"));
/*      */       }
/*      */       
/*      */ 
/*  100 */       setThresholdParams(request, response, MSreq, thresholdType, null, false);
/*      */       
/*      */ 
/*  103 */       if (MSreq.getAttribute("errorResponse") != null) {
/*  104 */         MSreq.removeAttribute("errorString");
/*  105 */         return (String)MSreq.getAttribute("errorResponse");
/*      */       }
/*      */       
/*      */ 
/*  109 */       convertSecondaryCondtionAPIParamstoActionParams(request, response, MSreq);
/*  110 */       Object errorString = MSreq.getAttribute("errorString");
/*  111 */       if (errorString != null) {
/*  112 */         MSreq.removeAttribute("errorString");
/*  113 */         return String.valueOf(errorString);
/*      */       }
/*      */       
/*  116 */       if (thresholdType == 4) {
/*  117 */         MSreq.addParameter("select", "thresholdFloat");
/*      */       }
/*      */       
/*  120 */       MSreq.addParameter("returnpath", "");
/*      */       
/*  122 */       if ("true".equals(request.getParameter("adminAPIRequest"))) {
/*  123 */         MSreq.addParameter("adminAPIRequest", "true");
/*  124 */         MSreq.addParameter("thresholdid", request.getParameter("thresholdid"));
/*      */       }
/*      */       else
/*      */       {
/*  128 */         MSreq.addParameter("apikey", request.getParameter("apikey"));
/*      */       }
/*  130 */       AdminActions action = new AdminActions();
/*      */       try {
/*  132 */         if (thresholdType == 1) {
/*  133 */           action.createThresholdAction(null, null, MSreq, response);
/*      */         } else {
/*  135 */           action.createPatternAction(null, null, MSreq, response);
/*      */         }
/*  137 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.create.success.text"), "4000");
/*      */       }
/*      */       catch (Exception ex) {
/*  140 */         ex.printStackTrace();
/*  141 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.create.failure.text", new String[] { request.getParameter("thresholdname") }), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  149 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  147 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static void convertSecondaryCondtionAPIParamstoActionParams(HttpServletRequest request, HttpServletResponse response, MockHttpServletRequest msReq)
/*      */   {
/*  154 */     int[] severityArr = { 1, 4, 5 };
/*  155 */     int thresholdType = Integer.parseInt(request.getParameter("type"));
/*  156 */     boolean isEdit = (request.getParameter("thresholdname") != null) || (request.getParameter("id") != null);
/*  157 */     String errorString = null;
/*  158 */     for (int severity : severityArr)
/*      */     {
/*  160 */       String severityStr = severity == 4 ? "warning" : severity == 1 ? "critical" : "info";
/*  161 */       String secondarythjoiner = request.getParameter(severityStr + "conditionjoiner");
/*  162 */       if (secondarythjoiner != null)
/*      */       {
/*      */ 
/*      */ 
/*  166 */         msReq.addParameter("secondary" + severityStr + "exist", "true");
/*  167 */         msReq.addParameter(severityStr + "conditionjoiner", secondarythjoiner);
/*  168 */         if (request.getParameter("secondary" + severityStr + "thresholdcondition") != null)
/*      */         {
/*  170 */           if (Constants.getThresholdCondition(request.getParameter("secondary" + severityStr + "thresholdcondition"), thresholdType))
/*      */           {
/*  172 */             msReq.addParameter("secondary" + severityStr + "thresholdcondition", request.getParameter("secondary" + severityStr + "thresholdcondition"));
/*  173 */             msReq.addParameter("secondary" + severityStr + "thresholdvalue", request.getParameter("secondary" + severityStr + "thresholdvalue"));
/*      */           }
/*      */           else
/*      */           {
/*  177 */             AMLog.debug("REST API : Wrong " + severityStr + " Threshold Condition mentioned");
/*      */             try {
/*  179 */               errorString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.condition.numeric.wrong.text", new String[] { "secondarycriticalcondition" }), "4102");
/*  180 */               if (thresholdType == 3)
/*      */               {
/*  182 */                 errorString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.condition.pattern.wrong.text", new String[] { "secondarycriticalcondition" }), "4102");
/*      */               }
/*      */               
/*  185 */               msReq.setAttribute("errorString", errorString);
/*      */             } catch (Exception exc) {
/*  187 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  193 */           AMLog.debug("REST API : Critical threshold condition should be given");
/*      */           try {
/*  195 */             errorString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "secondary" + severityStr + "condition" }), "4100");
/*  196 */             msReq.setAttribute("errorString", errorString);
/*      */           } catch (Exception exc) {
/*  198 */             exc.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static MockHttpServletRequest setThresholdParams(HttpServletRequest request, HttpServletResponse response, MockHttpServletRequest msReq, int thresholdType, HashMap<String, String> thresholdDetails, boolean update)
/*      */   {
/*      */     try {
/*  208 */       int[] severities = { 1, 4, 5 };
/*  209 */       for (int severity : severities) {
/*  210 */         String paramValue = null;
/*  211 */         String attributeValue = null;
/*  212 */         if (severity == 1) {
/*  213 */           paramValue = "critical";
/*  214 */           attributeValue = "critical";
/*  215 */         } else if (severity == 4) {
/*  216 */           paramValue = "warning";
/*  217 */           attributeValue = "warning";
/*      */         } else {
/*  219 */           paramValue = "clear";
/*  220 */           attributeValue = "info";
/*      */         }
/*      */         
/*  223 */         if (request.getParameter(paramValue + "value") != null) {
/*  224 */           if (thresholdType == 1) {
/*  225 */             if (Constants.isIntegerNumber(request.getParameter(paramValue + "value"))) {
/*  226 */               msReq.addParameter(attributeValue + "thresholdvalue", request.getParameter(paramValue + "value"));
/*      */             } else {
/*  228 */               AMLog.debug("REST API : " + paramValue + " threshold value should be an integer");
/*  229 */               String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { paramValue + "value" }), "4103");
/*  230 */               msReq.setAttribute("errorResponse", outputString);
/*  231 */               return msReq;
/*      */             }
/*  233 */           } else if (thresholdType == 4) {
/*  234 */             if (Constants.isFloat(request.getParameter(paramValue + "value"))) {
/*  235 */               msReq.addParameter(attributeValue + "thresholdvalue", request.getParameter(paramValue + "value"));
/*      */             } else {
/*  237 */               AMLog.debug("REST API : " + paramValue + " threshold value should be a float");
/*  238 */               String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.float.message.text", new String[] { "criticalvalue" }), "4104");
/*  239 */               msReq.setAttribute("errorResponse", outputString);
/*  240 */               return msReq;
/*      */             }
/*      */           }
/*      */           else {
/*  244 */             msReq.addParameter(attributeValue + "thresholdvalue", request.getParameter(paramValue + "value"));
/*      */           }
/*      */           
/*      */         }
/*  248 */         else if (update) {
/*  249 */           msReq.addParameter(attributeValue + "thresholdvalue", (String)thresholdDetails.get(paramValue + "value"));
/*      */         } else {
/*  251 */           if (severity == 1) {
/*  252 */             AMLog.debug("REST API : " + paramValue + " Threshold value should given");
/*  253 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { paramValue + "value" }), "4100");
/*  254 */             msReq.setAttribute("errorResponse", outputString);
/*  255 */             return msReq;
/*      */           }
/*  257 */           if (thresholdType != 4) {
/*  258 */             msReq.addParameter(attributeValue + "thresholdvalue", "5");
/*      */           } else {
/*  260 */             msReq.addParameter(attributeValue + "thresholdvalue", "5.0");
/*      */           }
/*      */           
/*  263 */           AMLog.debug("REST API : No " + paramValue + " threshold value given");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  268 */         if (request.getParameter(paramValue + "condition") != null) {
/*  269 */           if (Constants.getThresholdCondition(request.getParameter(paramValue + "condition"), thresholdType)) {
/*  270 */             msReq.addParameter(attributeValue + "thresholdcondition", request.getParameter(paramValue + "condition"));
/*      */           } else {
/*  272 */             AMLog.debug("REST API : Wrong " + paramValue + " Threshold Condition mentioned");
/*  273 */             String outputString = null;
/*  274 */             if (thresholdType == 3) {
/*  275 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.condition.pattern.wrong.text", new String[] { paramValue + "condition" }), "4102");
/*      */             } else {
/*  277 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.condition.numeric.wrong.text", new String[] { paramValue + "condition" }), "4102");
/*      */             }
/*      */             
/*  280 */             msReq.setAttribute("errorResponse", outputString);
/*  281 */             return msReq;
/*      */           }
/*      */           
/*      */         }
/*  285 */         else if (update) {
/*  286 */           msReq.addParameter(attributeValue + "thresholdcondition", (String)thresholdDetails.get(paramValue + "condition"));
/*      */         } else {
/*  288 */           if (severity == 1) {
/*  289 */             AMLog.debug("REST API : " + paramValue + " threshold condition should be given");
/*  290 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { paramValue + "condition" }), "4100");
/*  291 */             msReq.setAttribute("errorResponse", outputString);
/*  292 */             return msReq;
/*      */           }
/*  294 */           String condition = "EQ";
/*  295 */           if (severity == 5) {
/*  296 */             condition = "LT";
/*      */           }
/*  298 */           if ((thresholdType == 1) || (thresholdType == 4)) {
/*  299 */             msReq.addParameter(attributeValue + "thresholdcondition", condition);
/*      */           } else {
/*  301 */             msReq.addParameter(attributeValue + "thresholdcondition", "CT");
/*      */           }
/*      */           
/*  304 */           AMLog.debug("REST API : No " + paramValue + " threshold condition is given");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  309 */         if ((request.getParameter(paramValue + "polls") != null) && (!"".equals(request.getParameter(paramValue + "polls")))) {
/*  310 */           if (Constants.isIntegerNumber(request.getParameter(paramValue + "polls"))) {
/*  311 */             msReq.addParameter("consecutive_" + paramValue + "polls", request.getParameter(paramValue + "polls"));
/*      */           } else {
/*  313 */             AMLog.debug("REST API : Consecutive " + paramValue + " Polls to try should be an integer");
/*  314 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { paramValue + "polls" }), "4103");
/*  315 */             msReq.setAttribute("errorResponse", outputString);
/*  316 */             return msReq;
/*      */           }
/*  318 */         } else if (update) {
/*  319 */           msReq.addParameter("consecutive_" + paramValue + "polls", (String)thresholdDetails.get(paramValue + "polls"));
/*      */         }
/*      */         
/*  322 */         if ((request.getParameter("min_" + paramValue + "polls") != null) && (!"".equals(request.getParameter("min_" + paramValue + "polls")))) {
/*  323 */           if (Constants.isIntegerNumber(request.getParameter("min_" + paramValue + "polls"))) {
/*  324 */             String maxPollstoTry = request.getParameter(paramValue + "polls");
/*  325 */             if ((maxPollstoTry != null) && (!"".equals(maxPollstoTry))) {
/*  326 */               int maxPolls = Integer.valueOf(maxPollstoTry).intValue();
/*  327 */               int minPolls = Integer.valueOf(request.getParameter("min_" + paramValue + "polls")).intValue();
/*  328 */               if ((minPolls != -1) && (minPolls > maxPolls)) {
/*  329 */                 String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.pollstotry.message.text", new String[] { paramValue + "polls", "min_" + paramValue + "polls" }), "4103");
/*  330 */                 msReq.setAttribute("errorResponse", outputString);
/*  331 */                 return msReq;
/*      */               }
/*  333 */               msReq.addParameter("consecutive_min" + paramValue + "polls", request.getParameter("min_" + paramValue + "polls"));
/*      */             }
/*      */             else {
/*  336 */               String outputString = APIUtilities.emptyParameterResponse(request, response, "Threshold : create", paramValue + "polls");
/*  337 */               msReq.setAttribute("errorResponse", outputString);
/*  338 */               return msReq;
/*      */             }
/*      */           } else {
/*  341 */             AMLog.debug("REST API : Consecutive min " + paramValue + " Polls to try should be an integer");
/*  342 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "min_" + paramValue + "polls" }), "4103");
/*  343 */             msReq.setAttribute("errorResponse", outputString);
/*  344 */             return msReq;
/*      */           }
/*  346 */         } else if (update) {
/*  347 */           msReq.addParameter("consecutive_min" + paramValue + "polls", (String)thresholdDetails.get("min_" + paramValue + "polls"));
/*      */         }
/*      */         
/*  350 */         if (request.getParameter(paramValue + "message") != null) {
/*  351 */           msReq.addParameter(attributeValue + "thresholdmessage", request.getParameter(paramValue + "message"));
/*      */         }
/*  353 */         else if (update) {
/*  354 */           msReq.addParameter(attributeValue + "thresholdmessage", (String)thresholdDetails.get(paramValue + "message"));
/*      */         } else {
/*  356 */           msReq.addParameter(attributeValue + "thresholdmessage", FormatUtil.getString("am.webclient.alertmessage." + paramValue + ".text"));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  361 */       ex.printStackTrace();
/*      */     }
/*  363 */     return msReq;
/*      */   }
/*      */   
/*      */   public static void synchConfigureAlarmstoMAS(HttpServletRequest request, HashMap<String, String> actionParams) {
/*  367 */     HashMap<String, String> parameterValues = new HashMap();
/*      */     try {
/*  369 */       boolean sendToAllMas = true;
/*  370 */       String groupTemplate = request.getParameter("groupTemplate");
/*  371 */       parameterValues.put("groupTemplate", request.getParameter("groupTemplate"));
/*  372 */       parameterValues.put("haid", request.getParameter("haid"));
/*  373 */       HashMap<String, String> masServerIDMap = new HashMap();
/*  374 */       if (((actionParams.get("newThreshold") == null) || (!"true".equals(actionParams.get("newThreshold")))) && ("true".equals(groupTemplate))) {
/*  375 */         String haid = request.getParameter("haid");
/*  376 */         sendToAllMas = false;
/*      */         try {
/*  378 */           int haidValue = Integer.parseInt(haid);
/*  379 */           if ((haidValue < 10000000) && (actionParams.get("applySelected") != null)) {
/*  380 */             masServerIDMap = EnterpriseUtil.maptoManagedServers((String)actionParams.get("applytoMonitors"));
/*  381 */           } else if (haidValue > 10000000) {
/*  382 */             int serverid = EnterpriseUtil.getManagedServerIndex(haidValue);
/*  383 */             masServerIDMap.put(serverid + "", actionParams.get("applytoMonitors"));
/*      */           }
/*      */         } catch (Exception ex) {
/*  386 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  390 */       String[] criticalActions = request.getParameterValues("selectedactions_critical");
/*  391 */       String[] warningActions = request.getParameterValues("selectedactions_warning");
/*  392 */       String[] clearActions = request.getParameterValues("selectedactions_clear");
/*      */       
/*  394 */       StringBuilder cricActions = new StringBuilder();
/*  395 */       StringBuilder warActions = new StringBuilder();
/*  396 */       StringBuilder cleActions = new StringBuilder();
/*      */       
/*  398 */       if (criticalActions != null) {
/*  399 */         for (String critical : criticalActions) {
/*  400 */           cricActions.append(critical).append(",");
/*      */         }
/*      */       }
/*      */       
/*  404 */       if (warningActions != null) {
/*  405 */         for (String warning : warningActions) {
/*  406 */           warActions.append(warning).append(",");
/*      */         }
/*      */       }
/*      */       
/*  410 */       if (clearActions != null) {
/*  411 */         for (String clear : clearActions) {
/*  412 */           cleActions.append(clear).append(",");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  417 */       if (cricActions.length() > 0) {
/*  418 */         parameterValues.put("criticalactionid", cricActions.substring(0, cricActions.length() - 1));
/*      */       }
/*  420 */       if (warActions.length() > 0) {
/*  421 */         parameterValues.put("warningactionid", warActions.substring(0, warActions.length() - 1));
/*      */       }
/*  423 */       if (cleActions.length() > 0) {
/*  424 */         parameterValues.put("clearactionid", cleActions.substring(0, cleActions.length() - 1));
/*      */       }
/*  426 */       parameterValues.put("removeRCA", actionParams.get("removeRCA"));
/*  427 */       parameterValues.put("similarmonitors", actionParams.get("applytoMonitors"));
/*      */       
/*  429 */       parameterValues.put("requesttype", request.getParameter("requesttype"));
/*  430 */       parameterValues.put("thresholdactionparams", request.getParameter("thresholdactionparams"));
/*      */       
/*  432 */       parameterValues.put("thresholdid", request.getParameter("thresholdList"));
/*      */       
/*  434 */       parameterValues.put("resourceid", request.getParameter("resourceid"));
/*      */       
/*  436 */       parameterValues.put("multimonitors", request.getParameter("multimonitors"));
/*  437 */       parameterValues.put("overrideConfig", request.getParameter("overrideConfig"));
/*  438 */       parameterValues.put("deleteConfigType", request.getParameter("deleteConfigType"));
/*      */       
/*  440 */       parameterValues.put("attributeid", request.getParameter("attributeList"));
/*      */       
/*  442 */       if (actionParams.get("newThreshold") != null)
/*      */       {
/*  444 */         parameterValues.put("displayname", request.getParameter("displayname"));
/*  445 */         parameterValues.put("type", actionParams.get("type"));
/*  446 */         parameterValues.put("description", request.getParameter("description"));
/*  447 */         parameterValues.put("newthresholdid", actionParams.get("thresholdid"));
/*      */         
/*      */ 
/*  450 */         parameterValues.put("criticalthresholdvalue", request.getParameter("criticalthresholdvalue"));
/*  451 */         parameterValues.put("criticalthresholdcondition", request.getParameter("criticalthresholdcondition"));
/*  452 */         parameterValues.put("consecutive_criticalpolls", request.getParameter("consecutive_criticalpolls"));
/*  453 */         parameterValues.put("criticalthresholdmessage", request.getParameter("criticalthresholdmessage"));
/*      */         
/*  455 */         parameterValues.put("warningthresholdvalue", request.getParameter("warningthresholdvalue"));
/*  456 */         parameterValues.put("warningthresholdcondition", request.getParameter("warningthresholdcondition"));
/*  457 */         parameterValues.put("consecutive_warningpolls", request.getParameter("consecutive_warningpolls"));
/*  458 */         parameterValues.put("warningthresholdmessage", request.getParameter("warningthresholdmessage"));
/*      */         
/*  460 */         parameterValues.put("infothresholdvalue", request.getParameter("infothresholdvalue"));
/*  461 */         parameterValues.put("infothresholdcondition", request.getParameter("infothresholdcondition"));
/*  462 */         parameterValues.put("consecutive_clearpolls", request.getParameter("consecutive_clearpolls"));
/*  463 */         parameterValues.put("infothresholdmessage", request.getParameter("infothresholdmessage"));
/*      */       }
/*  465 */       parameterValues.put("adminAPIRequest", "true");
/*  466 */       if (!sendToAllMas) {
/*  467 */         Iterator<String> it = masServerIDMap.keySet().iterator();
/*  468 */         while (it.hasNext()) {
/*  469 */           String serverid = (String)it.next();
/*  470 */           parameterValues.put("similarmonitors_selected", masServerIDMap.get(serverid));
/*  471 */           MASSyncUtil.addTasktoSync(parameterValues, "/AppManager/xml/configurealarms", serverid, "post", 9, 1);
/*      */         }
/*      */       } else {
/*  474 */         MASSyncUtil.addTasktoSync(parameterValues, "/AppManager/xml/configurealarms", "all", "post", 9, 1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  478 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String associateThreshold(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  485 */     String outputString = null;
/*      */     try {
/*  487 */       String uri = request.getRequestURI();
/*  488 */       boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  489 */       if (isJsonFormat) {
/*  490 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  492 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*  494 */       int resourceid = -1;
/*  495 */       int thresholdid = -1;
/*  496 */       ArrayList<String> criticalids = new ArrayList();
/*  497 */       ArrayList<String> warningids = new ArrayList();
/*  498 */       ArrayList<String> clearids = new ArrayList();
/*  499 */       boolean isAPICallFromAAM = (request.getParameter("apicallfrom") != null) && (request.getParameter("apicallfrom").equals("admin"));
/*  500 */       boolean removeAlarms = (request.getParameter("removeAlarms") != null) && (request.getParameter("removeAlarms").equals("true"));
/*  501 */       if (request.getParameter("resourceid") != null) {
/*      */         try {
/*  503 */           resourceid = Integer.parseInt(request.getParameter("resourceid"));
/*      */         } catch (NumberFormatException num) {
/*  505 */           num.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  509 */       if (request.getParameter("thresholdid") != null) {
/*  510 */         thresholdid = Integer.parseInt(request.getParameter("thresholdid"));
/*  511 */       } else if (request.getParameter("thresholdname") != null) {
/*  512 */         String thresholdname = request.getParameter("thresholdname");
/*  513 */         thresholdid = DBUtil.getThresholdID(thresholdname);
/*  514 */         if (thresholdid == -1) {
/*  515 */           return APIUtilities.wrongNameResponse(request, response, "Configure Alarms Threshold", "thresholdname");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  520 */       String[] attributeList = new String[1];
/*  521 */       if (request.getParameter("attributeid") != null) {
/*  522 */         attributeList = request.getParameter("attributeid").split(",");
/*  523 */       } else if ((request.getParameter("monitortype") != null) && (request.getParameter("attributename") != null)) {
/*  524 */         String monitorType = request.getParameter("monitortype");
/*  525 */         String attributename = request.getParameter("attributename");
/*  526 */         int attribute = DBUtil.getAttributeId(monitorType, attributename);
/*  527 */         attributeList = new String[] { attribute + "" };
/*      */       }
/*      */       
/*  530 */       if ((isAPICallFromAAM) && (removeAlarms))
/*      */       {
/*  532 */         boolean status = removeAlarms(resourceid, attributeList);
/*  533 */         if (status) {
/*  534 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.alarm.remove.success.text"), "4575");
/*      */         }
/*  536 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.alarm.remove.failure.text"), "4576");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  544 */         if (request.getParameter("criticalactionid") != null) {
/*  545 */           String[] criticalAction = request.getParameter("criticalactionid").split(",");
/*  546 */           for (String critical : criticalAction) {
/*  547 */             criticalids.add(critical);
/*      */           }
/*  549 */         } else if (request.getParameter("criticalactionname") != null) {
/*  550 */           String criticalActionName = request.getParameter("criticalactionname");
/*  551 */           criticalids = DBUtil.getActionIDfromName(criticalActionName);
/*      */         }
/*      */       } catch (Exception ex) {
/*  554 */         ex.printStackTrace();
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  559 */         if (request.getParameter("warningactionid") != null) {
/*  560 */           String[] warningAction = request.getParameter("warningactionid").split(",");
/*  561 */           for (String warning : warningAction) {
/*  562 */             warningids.add(warning);
/*      */           }
/*  564 */         } else if (request.getParameter("warningactionname") != null) {
/*  565 */           String warningActionName = request.getParameter("warningactionname");
/*  566 */           warningids = DBUtil.getActionIDfromName(warningActionName);
/*      */         }
/*      */       } catch (Exception ex) {
/*  569 */         ex.printStackTrace();
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  574 */         if (request.getParameter("clearactionid") != null) {
/*  575 */           String[] clearAction = request.getParameter("clearactionid").split(",");
/*  576 */           for (String clear : clearAction) {
/*  577 */             clearids.add(clear);
/*      */           }
/*  579 */         } else if (request.getParameter("clearactionname") != null) {
/*  580 */           String clearActionName = request.getParameter("clearactionname");
/*  581 */           clearids = DBUtil.getActionIDfromName(clearActionName);
/*      */         }
/*      */       } catch (Exception ex) {
/*  584 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  587 */       for (String attr : attributeList) {
/*  588 */         if (attr.trim().length() > 0) {
/*      */           try {
/*  590 */             int attribute = Integer.parseInt(attr);
/*  591 */             if (thresholdid != -1) {
/*  592 */               AMPredefinedThresholdConfiguration.updateThresholdMapper(resourceid, thresholdid, attribute);
/*      */             }
/*  594 */             ArrayList<HashMap<String, Integer>> actionsList = new ArrayList();
/*  595 */             if (criticalids.size() > 0) {
/*  596 */               for (int i = 0; i < criticalids.size(); i++) {
/*  597 */                 HashMap<String, Integer> actions = new HashMap();
/*  598 */                 actions.put("resourceid", Integer.valueOf(resourceid));
/*  599 */                 actions.put("attributeid", Integer.valueOf(attribute));
/*  600 */                 actions.put("severity", Integer.valueOf(1));
/*  601 */                 actions.put("actionid", Integer.valueOf(Integer.parseInt((String)criticalids.get(i))));
/*  602 */                 actionsList.add(actions);
/*      */               }
/*      */             }
/*  605 */             if (warningids.size() > 0) {
/*  606 */               for (int i = 0; i < warningids.size(); i++) {
/*  607 */                 HashMap<String, Integer> actions = new HashMap();
/*  608 */                 actions.put("resourceid", Integer.valueOf(resourceid));
/*  609 */                 actions.put("attributeid", Integer.valueOf(attribute));
/*  610 */                 actions.put("severity", Integer.valueOf(4));
/*  611 */                 actions.put("actionid", Integer.valueOf(Integer.parseInt((String)warningids.get(i))));
/*  612 */                 actionsList.add(actions);
/*      */               }
/*      */             }
/*  615 */             if (clearids.size() > 0) {
/*  616 */               for (int i = 0; i < clearids.size(); i++) {
/*  617 */                 HashMap<String, Integer> actions = new HashMap();
/*  618 */                 actions.put("resourceid", Integer.valueOf(resourceid));
/*  619 */                 actions.put("attributeid", Integer.valueOf(attribute));
/*  620 */                 actions.put("severity", Integer.valueOf(5));
/*  621 */                 actions.put("actionid", Integer.valueOf(Integer.parseInt((String)clearids.get(i))));
/*  622 */                 actionsList.add(actions);
/*      */               }
/*      */             }
/*  625 */             AMPredefinedActionConfiguration.insertPredefinedConfigurations(actionsList);
/*      */           } catch (Exception num) {
/*  627 */             num.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*  631 */       return URITree.generateXML(request, response, FormatUtil.getString("Thresholds configured successfully"), "4000");
/*      */     }
/*      */     catch (Exception ex) {
/*  634 */       ex.printStackTrace();
/*      */     }
/*  636 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String configureThresholdandActions(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  640 */     outputString = null;
/*      */     try
/*      */     {
/*  643 */       String uri = request.getRequestURI();
/*  644 */       boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  645 */       if (isJsonFormat) {
/*  646 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  648 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*  651 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  652 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/*      */ 
/*  655 */       if (request.getParameter("resourceid") != null) {
/*  656 */         String resourceid = request.getParameter("resourceid");
/*  657 */         if ((resourceid != null) && (resourceid.contains(","))) {
/*  658 */           String[] ids = resourceid.split(",");
/*  659 */           boolean similarMonitor = false;
/*  660 */           for (String id : ids) {
/*  661 */             if (similarMonitor) {
/*  662 */               MSreq.addParameter("similarmonitors_selected", id);
/*      */             } else {
/*  664 */               MSreq.addParameter("resourceid", id);
/*  665 */               similarMonitor = true;
/*      */             }
/*      */           }
/*      */         } else {
/*  669 */           MSreq.addParameter("resourceid", request.getParameter("resourceid"));
/*      */         }
/*  671 */       } else if (request.getParameter("monitorname") != null) {
/*  672 */         String monitorname = request.getParameter("monitorname");
/*  673 */         ResultSet rs = null;
/*      */         try {
/*  675 */           rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_ManagedObject where DISPLAYNAME='" + monitorname + "'");
/*  676 */           if (rs.next()) {
/*  677 */             MSreq.addParameter("resourceid", rs.getString("RESOURCEID"));
/*      */           }
/*      */         } catch (Exception ex) {
/*  680 */           ex.printStackTrace();
/*      */         } finally {
/*  682 */           if (rs != null) {
/*  683 */             AMConnectionPool.closeResultSet(rs);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  688 */       String[] attributeList = new String[1];
/*  689 */       if (request.getParameter("attributeid") != null) {
/*  690 */         attributeList = request.getParameter("attributeid").split(",");
/*  691 */       } else if ((request.getParameter("monitortype") != null) && (request.getParameter("attributename") != null)) {
/*  692 */         String monitorType = request.getParameter("monitortype");
/*  693 */         String attributename = request.getParameter("attributename");
/*  694 */         int attributeid = DBUtil.getAttributeId(monitorType, attributename);
/*  695 */         attributeList = new String[] { attributeid + "" };
/*      */       }
/*      */       
/*      */ 
/*  699 */       if (request.getParameter("thresholdid") != null) {
/*  700 */         MSreq.addParameter("thresholdList", request.getParameter("thresholdid"));
/*  701 */       } else if (request.getParameter("thresholdname") != null) {
/*  702 */         String thresholdname = request.getParameter("thresholdname");
/*  703 */         int thresholdid = DBUtil.getThresholdID(thresholdname);
/*  704 */         if (thresholdid != -1) {
/*  705 */           MSreq.addParameter("thresholdList", thresholdid + "");
/*      */         } else {
/*  707 */           return APIUtilities.wrongNameResponse(request, response, "Configure Alarms Threshold", "thresholdname");
/*      */         }
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  713 */         String[] criticalAction = new String[1];
/*  714 */         if (request.getParameter("criticalactionid") != null) {
/*  715 */           criticalAction = request.getParameter("criticalactionid").split(",");
/*  716 */           for (String critical : criticalAction) {
/*  717 */             MSreq.addParameter("selectedactions_critical", critical);
/*      */           }
/*  719 */         } else if (request.getParameter("criticalactionname") != null) {
/*  720 */           String criticalActionName = request.getParameter("criticalactionname");
/*  721 */           ArrayList<String> criticalids = DBUtil.getActionIDfromName(criticalActionName);
/*  722 */           for (int i = 0; i < criticalids.size(); i++) {
/*  723 */             MSreq.addParameter("selectedactions_critical", (String)criticalids.get(i));
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/*  727 */         ex.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/*  733 */         String[] warningAction = new String[1];
/*  734 */         if (request.getParameter("warningactionid") != null) {
/*  735 */           warningAction = request.getParameter("warningactionid").split(",");
/*  736 */           for (String warning : warningAction) {
/*  737 */             MSreq.addParameter("selectedactions_warning", warning);
/*      */           }
/*  739 */         } else if (request.getParameter("warningactionname") != null) {
/*  740 */           String warningActionName = request.getParameter("warningactionname");
/*  741 */           ArrayList<String> warningids = DBUtil.getActionIDfromName(warningActionName);
/*  742 */           for (int i = 0; i < warningids.size(); i++) {
/*  743 */             MSreq.addParameter("selectedactions_warning", (String)warningids.get(i));
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/*  747 */         ex.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/*  753 */         String[] clearAction = new String[1];
/*  754 */         if (request.getParameter("clearactionid") != null) {
/*  755 */           clearAction = request.getParameter("clearactionid").split(",");
/*  756 */           for (String clear : clearAction) {
/*  757 */             MSreq.addParameter("selectedactions_clear", clear);
/*      */           }
/*  759 */         } else if (request.getParameter("clearactionname") != null) {
/*  760 */           String clearActionName = request.getParameter("clearactionname");
/*  761 */           ArrayList<String> clearids = DBUtil.getActionIDfromName(clearActionName);
/*  762 */           for (int i = 0; i < clearids.size(); i++) {
/*  763 */             MSreq.addParameter("selectedactions_clear", (String)clearids.get(i));
/*      */           }
/*      */         }
/*      */         
/*  767 */         if ((request.getParameter("monitorgroupname") != null) && (!request.getParameter("monitorgroupname").equals("")))
/*      */         {
/*  769 */           String haid = DBUtil.getResourceIdForResourceName(request.getParameter("monitorgroupname"));
/*  770 */           MSreq.addParameter("haid", haid);
/*      */         }
/*  772 */         else if (request.getParameter("haid") != null) {
/*  773 */           MSreq.addParameter("haid", request.getParameter("haid"));
/*      */         }
/*      */         
/*  776 */         if (request.getParameter("requesttype") != null) {
/*  777 */           MSreq.addParameter("requesttype", request.getParameter("requesttype"));
/*      */         }
/*      */         
/*  780 */         if (request.getParameter("thresholdactionparams") != null) {
/*  781 */           MSreq.addParameter("thresholdactionparams", request.getParameter("thresholdactionparams"));
/*      */         }
/*      */         
/*  784 */         if (request.getParameter("multimonitors") != null) {
/*  785 */           MSreq.addParameter("multimonitors", request.getParameter("multimonitors"));
/*      */         }
/*      */         
/*  788 */         if (request.getParameter("removeRCA") != null) {
/*  789 */           MSreq.addParameter("removeRCA", request.getParameter("removeRCA"));
/*      */         }
/*      */         
/*  792 */         if (request.getParameter("similarmonitors") != null) {
/*  793 */           String[] similarmonitors = request.getParameter("similarmonitors").split(",");
/*  794 */           for (String monitor : similarmonitors) {
/*  795 */             MSreq.addParameter("similarmonitors_selected", monitor);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  801 */         if (request.getParameter("displayname") != null) {
/*  802 */           MSreq.addParameter("displayname", request.getParameter("displayname"));
/*      */         }
/*      */         
/*  805 */         if (request.getParameter("type") != null) {
/*  806 */           MSreq.addParameter("type", request.getParameter("type"));
/*      */         }
/*      */         
/*  809 */         if (request.getParameter("description") != null) {
/*  810 */           MSreq.addParameter("description", request.getParameter("description"));
/*      */         }
/*      */         
/*  813 */         if (request.getParameter("thresholdid") != null) {
/*  814 */           MSreq.addParameter("thresholdid", request.getParameter("thresholdid"));
/*      */         }
/*      */         
/*  817 */         if (request.getParameter("newthresholdid") != null) {
/*  818 */           MSreq.addParameter("newthresholdid", request.getParameter("newthresholdid"));
/*      */         }
/*      */         
/*  821 */         if (request.getParameter("criticalthresholdvalue") != null) {
/*  822 */           MSreq.addParameter("criticalthresholdvalue", request.getParameter("criticalthresholdvalue"));
/*      */         }
/*      */         
/*  825 */         if (request.getParameter("criticalthresholdcondition") != null) {
/*  826 */           MSreq.addParameter("criticalthresholdcondition", request.getParameter("criticalthresholdcondition"));
/*      */         }
/*      */         
/*  829 */         if (request.getParameter("consecutive_criticalpolls") != null) {
/*  830 */           MSreq.addParameter("consecutive_criticalpolls", request.getParameter("consecutive_criticalpolls"));
/*      */         }
/*      */         
/*  833 */         if (request.getParameter("criticalthresholdmessage") != null) {
/*  834 */           MSreq.addParameter("criticalthresholdmessage", request.getParameter("criticalthresholdmessage"));
/*      */         }
/*      */         
/*  837 */         if (request.getParameter("warningthresholdvalue") != null) {
/*  838 */           MSreq.addParameter("warningthresholdvalue", request.getParameter("warningthresholdvalue"));
/*      */         }
/*      */         
/*  841 */         if (request.getParameter("warningthresholdcondition") != null) {
/*  842 */           MSreq.addParameter("warningthresholdcondition", request.getParameter("warningthresholdcondition"));
/*      */         }
/*      */         
/*  845 */         if (request.getParameter("consecutive_warningpolls") != null) {
/*  846 */           MSreq.addParameter("consecutive_warningpolls", request.getParameter("consecutive_warningpolls"));
/*      */         }
/*      */         
/*  849 */         if (request.getParameter("warningthresholdmessage") != null) {
/*  850 */           MSreq.addParameter("warningthresholdmessage", request.getParameter("warningthresholdmessage"));
/*      */         }
/*      */         
/*  853 */         if (request.getParameter("infothresholdvalue") != null) {
/*  854 */           MSreq.addParameter("infothresholdvalue", request.getParameter("infothresholdvalue"));
/*      */         }
/*      */         
/*  857 */         if (request.getParameter("infothresholdcondition") != null) {
/*  858 */           MSreq.addParameter("infothresholdcondition", request.getParameter("infothresholdcondition"));
/*      */         }
/*      */         
/*  861 */         if (request.getParameter("consecutive_clearpolls") != null) {
/*  862 */           MSreq.addParameter("consecutive_clearpolls", request.getParameter("consecutive_clearpolls"));
/*      */         }
/*      */         
/*  865 */         if (request.getParameter("infothresholdmessage") != null) {
/*  866 */           MSreq.addParameter("infothresholdmessage", request.getParameter("infothresholdmessage"));
/*      */         }
/*      */         
/*  869 */         if ("true".equals(request.getParameter("adminAPIRequest"))) {
/*  870 */           MSreq.addParameter("adminAPIRequest", request.getParameter("adminAPIRequest"));
/*      */         }
/*      */       } catch (Exception ex) {
/*  873 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  876 */       if ("true".equals(request.getParameter("groupTemplate"))) {
/*  877 */         MSreq.addParameter("groupTemplate", request.getParameter("groupTemplate"));
/*      */       }
/*      */       
/*  880 */       if (request.getParameter("overrideConfig") != null) {
/*  881 */         MSreq.addParameter("overrideConfig", request.getParameter("overrideConfig"));
/*      */       }
/*      */       
/*  884 */       if (request.getParameter("deleteConfigType") != null) {
/*  885 */         MSreq.addParameter("deleteConfigType", request.getParameter("deleteConfigType"));
/*      */       }
/*      */       
/*  888 */       AdminActions action = new AdminActions();
/*      */       try
/*      */       {
/*  891 */         if (attributeList.length > 1)
/*      */         {
/*  893 */           for (String attributeid : attributeList) {
/*  894 */             Map requestParameters = MSreq.getParameterMap();
/*  895 */             requestParameters.remove("attributeList");
/*  896 */             MSreq.addParameter("attributeList", attributeid);
/*  897 */             action.AddThresholdActionConfiguration(null, null, MSreq, response);
/*      */           }
/*  899 */           return URITree.generateXML(request, response, FormatUtil.getString("actions created successfully"), "4000");
/*      */         }
/*      */         
/*  902 */         MSreq.addParameter("attributeList", attributeList[0]);
/*  903 */         action.AddThresholdActionConfiguration(null, null, MSreq, response);
/*  904 */         return URITree.generateXML(request, response, FormatUtil.getString("actions created successfully"), "4000");
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  909 */         ex.printStackTrace();
/*  910 */         return URITree.generateXML(request, response, FormatUtil.getString("problem in creating actions"), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  917 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  915 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String deleteThreshold(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/*  921 */     outputString = null;
/*      */     try {
/*  923 */       if (isJsonFormat) {
/*  924 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  926 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*  929 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*  930 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*  933 */       String apiKey = request.getParameter("apikey");
/*  934 */       int userId = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(apiKey));
/*  935 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  936 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*  937 */       MSreq.addParameter("method", "createEmailAction");
/*  938 */       MSreq.addParameter("apikey", apiKey);
/*  939 */       if (request.getParameter("thresholdid") != null) {
/*  940 */         String[] thresholdList = request.getParameter("thresholdid").split(",");
/*  941 */         for (String id : thresholdList) {
/*  942 */           if ((!"".equals(id.trim())) && 
/*  943 */             (DelegatedUserRoleUtil.isOwnedByDelegatedUser(Integer.parseInt(id), userId, 1)))
/*      */           {
/*  945 */             MSreq.addParameter("checkbox", id);
/*      */           }
/*      */         }
/*      */       }
/*  949 */       else if (request.getParameter("thresholdname") != null) {
/*  950 */         String name = request.getParameter("thresholdname");
/*  951 */         int thresholdID = DBUtil.getThresholdID(name);
/*  952 */         if (thresholdID != -1) {
/*  953 */           if (DelegatedUserRoleUtil.isOwnedByDelegatedUser(thresholdID, userId, 1))
/*      */           {
/*  955 */             MSreq.addParameter("checkbox", thresholdID + "");
/*      */           }
/*      */         } else {
/*  958 */           return APIUtilities.wrongNameResponse(request, response, "Threshold : delete", "thresholdname");
/*      */         }
/*      */       } else {
/*  961 */         return APIUtilities.emptyParameterResponse(request, response, "Threshold : delete", "thresholdid or thresholdname");
/*      */       }
/*      */       
/*  964 */       MSreq.addParameter("apirequest", "true");
/*  965 */       AdminActions action = new AdminActions();
/*      */       try
/*      */       {
/*  968 */         action.deleteThresholds(null, null, MSreq, response);
/*  969 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.delete.success.text"), "4000");
/*      */       }
/*      */       catch (Exception ex) {
/*  972 */         ex.printStackTrace();
/*  973 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.delete.failure.text"), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  980 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  978 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String createEmailAction(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/*  984 */     String outputString = null;
/*      */     try {
/*  986 */       if (isJsonFormat) {
/*  987 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  989 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*  992 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*  993 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*      */ 
/*  997 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  998 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*  999 */       MSreq.addParameter("method", "createEmailAction");
/* 1000 */       MSreq.addParameter("cancel", "false");
/* 1001 */       String emailactionname = request.getParameter("emailactionname");
/* 1002 */       if (request.getParameter("actionid") != null) {
/* 1003 */         int id = Integer.parseInt(request.getParameter("actionid"));
/* 1004 */         if (id >= 10000)
/*      */         {
/* 1006 */           emailactionname = emailactionname + "(Admin)";
/*      */         }
/*      */       }
/* 1009 */       if (request.getParameter("emailactionname") != null) {
/* 1010 */         if (!DBQueryUtil.checkForDuplicateEntry("AM_ACTIONPROFILE", "NAME", emailactionname, "")) {
/* 1011 */           MSreq.addParameter("displayname", emailactionname);
/*      */         } else {
/* 1013 */           return APIUtilities.duplicateNameResponse(request, response, "EmailAction - create", "emailactionname");
/*      */         }
/*      */       } else {
/* 1016 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "emailactionname");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1021 */       if (request.getParameter("fromaddress") != null) {
/* 1022 */         MSreq.addParameter("fromaddress", request.getParameter("fromaddress"));
/*      */       } else {
/* 1024 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "fromaddress");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1029 */       if (request.getParameter("toaddress") != null) {
/* 1030 */         MSreq.addParameter("toaddress", request.getParameter("toaddress"));
/*      */       } else {
/* 1032 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "toaddress");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1037 */       if (request.getParameter("subject") != null) {
/* 1038 */         MSreq.addParameter("subject", request.getParameter("subject"));
/*      */       } else {
/* 1040 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "subject");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1045 */       if (request.getParameter("message") != null) {
/* 1046 */         MSreq.addParameter("message", request.getParameter("message"));
/*      */       } else {
/* 1048 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "message");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1053 */       if (request.getParameter("smtpserver") != null) {
/* 1054 */         MSreq.addParameter("smtpserver", request.getParameter("smtpserver"));
/*      */       } else {
/* 1056 */         MSreq.addParameter("smtpserver", "smtp");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1061 */       if (request.getParameter("smtpport") != null) {
/* 1062 */         if (Constants.isIntegerNumber(request.getParameter("smtpport"))) {
/* 1063 */           MSreq.addParameter("smtpport", request.getParameter("smtpport"));
/*      */         } else {
/* 1065 */           APIUtilities.defaultIntegerResponse(request, response, "EmailAction - create", "smtpport");
/*      */         }
/*      */       }
/*      */       else {
/* 1069 */         MSreq.addParameter("smtpport", "25");
/*      */       }
/*      */       
/*      */ 
/* 1073 */       if (request.getParameter("mailformat") != null) {
/* 1074 */         if ("plaintext".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1075 */           MSreq.addParameter("mailFormat", "1");
/* 1076 */         } else if ("html".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1077 */           MSreq.addParameter("mailFormat", "2");
/* 1078 */         } else if ("both".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1079 */           MSreq.addParameter("mailFormat", "0");
/*      */         }
/*      */       } else {
/* 1082 */         MSreq.addParameter("mailformat", "0");
/*      */       }
/*      */       
/*      */ 
/* 1086 */       if ((request.getParameter("appendmessage") != null) && ("true".equals(request.getParameter("appendmessage")))) {
/* 1087 */         MSreq.addParameter("appendMessage", "true");
/*      */       }
/*      */       
/*      */ 
/* 1091 */       if ((request.getParameter("businesshouraction") != null) && ("true".equals(request.getParameter("businesshouraction")))) {
/* 1092 */         if (request.getParameter("businessid") != null) {
/* 1093 */           MSreq.addParameter("selectedBusinessHourID", request.getParameter("businessid"));
/* 1094 */         } else if (request.getParameter("businesshourname") != null) {
/* 1095 */           int businessid = DBUtil.getBusinessHourID(request.getParameter("businesshourname"));
/* 1096 */           if (businessid == -1) {
/* 1097 */             return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessid");
/*      */           }
/* 1099 */           MSreq.addParameter("selectedBusinessHourID", businessid + "");
/*      */         }
/*      */         else {
/* 1102 */           return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessid");
/*      */         }
/*      */         
/* 1105 */         if (request.getParameter("businesstype") != null) {
/* 1106 */           MSreq.addParameter("businessType", request.getParameter("businesstype"));
/*      */         } else {
/* 1108 */           return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessType");
/*      */         }
/* 1110 */         MSreq.addParameter("businessHourAssociatedToAction", "enabled");
/*      */       }
/*      */       
/* 1113 */       MSreq.addParameter("returnpath", "");
/* 1114 */       if ("true".equals(request.getParameter("adminAPIRequest"))) {
/* 1115 */         MSreq.addParameter("adminAPIRequest", "true");
/* 1116 */         MSreq.addParameter("actionid", request.getParameter("actionid"));
/*      */       }
/*      */       
/* 1119 */       AdminActions action = new AdminActions();
/*      */       try {
/* 1121 */         action.createEmailAction(null, null, MSreq, response);
/* 1122 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.create.success.text"), "4000");
/*      */       }
/*      */       catch (Exception ex) {
/* 1125 */         ex.printStackTrace();
/* 1126 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.create.failure.text"), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1134 */       return null;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1132 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void synchEmailActions(HttpServletRequest request, int actionid, boolean toUpdate)
/*      */   {
/* 1138 */     HashMap<String, String> parameters = new HashMap();
/*      */     try {
/* 1140 */       parameters.put("adminAPIRequest", "true");
/*      */       
/* 1142 */       if (toUpdate) {
/* 1143 */         parameters.put("emailactionid", actionid + "");
/* 1144 */         parameters.put("displayname", request.getParameter("displayname"));
/*      */       } else {
/* 1146 */         parameters.put("actionid", actionid + "");
/* 1147 */         parameters.put("emailactionname", request.getParameter("displayname"));
/*      */       }
/*      */       
/* 1150 */       parameters.put("fromaddress", request.getParameter("fromaddress"));
/* 1151 */       parameters.put("toaddress", request.getParameter("toaddress"));
/* 1152 */       parameters.put("subject", request.getParameter("subject"));
/* 1153 */       parameters.put("message", request.getParameter("message"));
/* 1154 */       parameters.put("smtpserver", request.getParameter("smtpserver"));
/* 1155 */       parameters.put("smtpport", request.getParameter("smtpport"));
/* 1156 */       String mailFormat = request.getParameter("mailFormat");
/* 1157 */       if ("2".equals(mailFormat)) {
/* 1158 */         parameters.put("mailformat", "html");
/* 1159 */       } else if ("1".equals(mailFormat)) {
/* 1160 */         parameters.put("mailformat", "plaintext");
/*      */       } else {
/* 1162 */         parameters.put("mailformat", "both");
/*      */       }
/* 1164 */       if (request.getParameter("appendMessage") != null) {
/* 1165 */         parameters.put("appendmessage", "true");
/*      */       } else {
/* 1167 */         parameters.put("appendmessage", "false");
/*      */       }
/*      */       
/* 1170 */       if (request.getParameter("businessHourAssociatedToAction") != null) {
/* 1171 */         parameters.put("businesshouraction", "true");
/*      */       }
/* 1173 */       parameters.put("businessid", request.getParameter("selectedBusinessHourID"));
/* 1174 */       parameters.put("businesstype", request.getParameter("businessType"));
/* 1175 */       if (toUpdate) {
/* 1176 */         MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/actions/emailaction", "all", "post", 8, 1);
/*      */       } else {
/* 1178 */         MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/actions/emailaction", "all", "post", 8, 1);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1181 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void deleteEmailActiotoSynch(String actionids) {
/* 1186 */     HashMap<String, String> parameters = new HashMap();
/*      */     try {
/* 1188 */       parameters.put("emailactionid", actionids);
/* 1189 */       parameters.put("TO_DELETE", "true");
/* 1190 */       parameters.put("adminAPIRequest", "true");
/* 1191 */       MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/actions/emailaction", "all", "post", 8, 1);
/*      */     } catch (Exception ex) {
/* 1193 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void deleteThresholdProfiletoSynch(String thresholdids) {
/* 1198 */     HashMap<String, String> parameters = new HashMap();
/*      */     try
/*      */     {
/* 1201 */       parameters.put("thresholdid", thresholdids);
/* 1202 */       parameters.put("TO_DELETE", "true");
/* 1203 */       MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/threshold", "all", "post", 6, 1);
/*      */     } catch (Exception ex) {
/* 1205 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void addThresholdProfiletoSynch(HttpServletRequest request, String thresholdid, String type, boolean isupdate) {
/* 1210 */     HashMap<String, String> parameters = new HashMap();
/*      */     try
/*      */     {
/* 1213 */       if (isupdate) {
/* 1214 */         parameters.put("newthresholdname", request.getParameter("displayname"));
/*      */       } else {
/* 1216 */         parameters.put("thresholdname", request.getParameter("displayname"));
/*      */       }
/*      */       
/* 1219 */       parameters.put("type", type);
/* 1220 */       parameters.put("description", request.getParameter("description"));
/* 1221 */       parameters.put("thresholdid", thresholdid);
/* 1222 */       parameters.put("adminAPIRequest", "true");
/* 1223 */       convertThConditionActionParamsToApiParams(request, parameters);
/* 1224 */       if ((request.getParameter("consecutive_criticalpolls") == null) || ("".equals(request.getParameter("consecutive_criticalpolls")))) {
/* 1225 */         parameters.put("criticalpolls", "-1");
/*      */       } else {
/* 1227 */         parameters.put("criticalpolls", request.getParameter("consecutive_criticalpolls"));
/*      */       }
/*      */       
/* 1230 */       if ((request.getParameter("consecutive_mincriticalpolls") == null) || ("".equals(request.getParameter("consecutive_mincriticalpolls")))) {
/* 1231 */         parameters.put("min_criticalpolls", "-1");
/*      */       } else {
/* 1233 */         parameters.put("min_criticalpolls", request.getParameter("consecutive_mincriticalpolls"));
/*      */       }
/*      */       
/* 1236 */       parameters.put("criticalmessage", request.getParameter("criticalthresholdmessage"));
/*      */       
/* 1238 */       parameters.put("warningvalue", request.getParameter("warningthresholdvalue"));
/* 1239 */       parameters.put("warningcondition", request.getParameter("warningthresholdcondition"));
/*      */       
/* 1241 */       if ((request.getParameter("consecutive_warningpolls") == null) || ("".equals(request.getParameter("consecutive_warningpolls")))) {
/* 1242 */         parameters.put("warningpolls", "-1");
/*      */       } else {
/* 1244 */         parameters.put("warningpolls", request.getParameter("consecutive_warningpolls"));
/*      */       }
/* 1246 */       if ((request.getParameter("consecutive_minwarningpolls") == null) || ("".equals(request.getParameter("consecutive_minwarningpolls")))) {
/* 1247 */         parameters.put("min_warningpolls", "-1");
/*      */       } else {
/* 1249 */         parameters.put("min_warningpolls", request.getParameter("consecutive_minwarningpolls"));
/*      */       }
/*      */       
/* 1252 */       parameters.put("warningmessage", request.getParameter("warningthresholdmessage"));
/*      */       
/* 1254 */       parameters.put("clearvalue", request.getParameter("infothresholdvalue"));
/* 1255 */       parameters.put("clearcondition", request.getParameter("infothresholdcondition"));
/*      */       
/* 1257 */       if ((request.getParameter("consecutive_clearpolls") == null) || ("".equals(request.getParameter("consecutive_clearpolls")))) {
/* 1258 */         parameters.put("clearpolls", "-1");
/*      */       } else {
/* 1260 */         parameters.put("clearpolls", request.getParameter("consecutive_clearpolls"));
/*      */       }
/*      */       
/* 1263 */       if ((request.getParameter("consecutive_minclearpolls") == null) || ("".equals(request.getParameter("consecutive_minclearpolls")))) {
/* 1264 */         parameters.put("min_clearpolls", "-1");
/*      */       } else {
/* 1266 */         parameters.put("min_clearpolls", request.getParameter("consecutive_minclearpolls"));
/*      */       }
/*      */       
/* 1269 */       parameters.put("clearmessage", request.getParameter("infothresholdmessage"));
/* 1270 */       if (isupdate) {
/* 1271 */         MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/threshold", "all", "post", 6, 1);
/*      */       } else {
/* 1273 */         MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/threshold", "all", "post", 6, 1);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1276 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static void convertThConditionActionParamsToApiParams(HttpServletRequest request, HashMap<String, String> parameters)
/*      */   {
/* 1283 */     parameters.put("criticalvalue", request.getParameter("criticalthresholdvalue"));
/* 1284 */     parameters.put("criticalcondition", request.getParameter("criticalthresholdcondition"));
/* 1285 */     String secondaryCexist = request.getParameter("secondarycriticalexist");
/* 1286 */     if ((secondaryCexist != null) && ("true".equals(secondaryCexist)))
/*      */     {
/* 1288 */       parameters.put("secondarycriticalexist", secondaryCexist);
/* 1289 */       parameters.put("criticalconditionjoiner", request.getParameter("criticalconditionjoiner"));
/* 1290 */       parameters.put("secondarycriticalthresholdcondition", request.getParameter("secondarycriticalthresholdcondition"));
/* 1291 */       parameters.put("secondarycriticalthresholdvalue", request.getParameter("secondarycriticalthresholdvalue"));
/*      */     }
/*      */     
/* 1294 */     parameters.put("warningvalue", request.getParameter("warningthresholdvalue"));
/* 1295 */     parameters.put("warningcondition", request.getParameter("warningthresholdcondition"));
/* 1296 */     String secondaryWexist = request.getParameter("secondarywarningexist");
/* 1297 */     if ((secondaryWexist != null) && ("true".equals(secondaryWexist)))
/*      */     {
/* 1299 */       parameters.put("secondarywarningexist", secondaryWexist);
/* 1300 */       parameters.put("warningconditionjoiner", request.getParameter("warningconditionjoiner"));
/* 1301 */       parameters.put("secondarywarningthresholdcondition", request.getParameter("secondarywarningthresholdcondition"));
/* 1302 */       parameters.put("secondarywarningthresholdvalue", request.getParameter("secondarywarningthresholdvalue"));
/*      */     }
/*      */     
/* 1305 */     parameters.put("clearvalue", request.getParameter("infothresholdvalue"));
/* 1306 */     parameters.put("clearcondition", request.getParameter("infothresholdcondition"));
/* 1307 */     String secondaryIexist = request.getParameter("secondaryinfoexist");
/* 1308 */     if ((secondaryIexist != null) && ("true".equals(secondaryIexist)))
/*      */     {
/* 1310 */       parameters.put("secondaryinfoexist", secondaryIexist);
/* 1311 */       parameters.put("infoconditionjoiner", request.getParameter("infoconditionjoiner"));
/* 1312 */       parameters.put("secondaryinfothresholdcondition", request.getParameter("secondaryinfothresholdcondition"));
/* 1313 */       parameters.put("secondaryinfothresholdvalue", request.getParameter("secondaryinfothresholdvalue"));
/*      */     }
/*      */   }
/*      */   
/*      */   public static String updateEmailAction(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1319 */     String outputString = null;
/*      */     try {
/* 1321 */       if (isJsonFormat) {
/* 1322 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1324 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 1327 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/* 1328 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 1331 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1332 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/* 1334 */       AMActionForm actionform = new AMActionForm();
/* 1335 */       int actionid = -1;
/* 1336 */       if (request.getParameter("emailactionid") != null) {
/*      */         try {
/* 1338 */           actionid = Integer.parseInt(request.getParameter("emailactionid"));
/* 1339 */           actionform.setId(actionid);
/*      */         } catch (NumberFormatException num) {
/* 1341 */           num.printStackTrace();
/*      */         }
/*      */       }
/* 1344 */       else if (request.getParameter("emailactionname") != null) {
/* 1345 */         String actionname = request.getParameter("emailactionname");
/* 1346 */         if (!actionname.matches("^[ A-Za-z0-9_@./#+&-]*$")) {
/* 1347 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.specialchar.alert.displayname"), "4037");
/*      */         }
/* 1349 */         actionid = DBUtil.getActionId(actionname, 1);
/* 1350 */         if (actionid != -1) {
/* 1351 */           actionform.setId(actionid);
/*      */         } else {
/* 1353 */           return APIUtilities.wrongNameResponse(request, response, "EmailAction : update", "emailactionname");
/*      */         }
/*      */       } else {
/* 1356 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction : update", "emailactionid or emailactionname");
/*      */       }
/*      */       
/* 1359 */       ArrayList<ArrayList> details = DBUtil.getActionDetails(actionid, 1);
/* 1360 */       if (details.size() > 0) {
/* 1361 */         ArrayList<String> mailAction = (ArrayList)details.get(0);
/*      */         
/* 1363 */         if (request.getParameter("displayname") != null) {
/* 1364 */           MSreq.addParameter("displayname", request.getParameter("displayname"));
/*      */         } else {
/* 1366 */           MSreq.addParameter("displayname", (String)mailAction.get(0));
/*      */         }
/*      */         
/* 1369 */         if (request.getParameter("fromaddress") != null) {
/* 1370 */           MSreq.addParameter("fromaddress", request.getParameter("fromaddress"));
/* 1371 */           actionform.setFromaddress(request.getParameter("fromaddress"));
/*      */         } else {
/* 1373 */           actionform.setFromaddress((String)mailAction.get(1));
/*      */         }
/*      */         
/* 1376 */         if (request.getParameter("toaddress") != null) {
/* 1377 */           MSreq.addParameter("toaddress", request.getParameter("toaddress"));
/* 1378 */           actionform.setToaddress(request.getParameter("toaddress"));
/*      */         } else {
/* 1380 */           actionform.setToaddress((String)mailAction.get(2));
/*      */         }
/*      */         
/*      */ 
/* 1384 */         if (request.getParameter("subject") != null) {
/* 1385 */           MSreq.addParameter("subject", request.getParameter("subject"));
/* 1386 */           actionform.setSubject(request.getParameter("subject"));
/*      */         } else {
/* 1388 */           actionform.setSubject((String)mailAction.get(3));
/*      */         }
/*      */         
/* 1391 */         if (request.getParameter("message") != null) {
/* 1392 */           MSreq.addParameter("message", request.getParameter("message"));
/* 1393 */           actionform.setMessage(request.getParameter("message"));
/*      */         } else {
/* 1395 */           actionform.setMessage((String)mailAction.get(4));
/*      */         }
/*      */         
/* 1398 */         if (request.getParameter("smtpserver") != null) {
/* 1399 */           actionform.setSmtpserver(request.getParameter("smtpserver"));
/*      */         } else {
/* 1401 */           actionform.setSmtpserver((String)mailAction.get(6));
/*      */         }
/*      */         
/* 1404 */         if (request.getParameter("smtpport") != null) {
/* 1405 */           int smtpport = 25;
/*      */           try {
/* 1407 */             smtpport = Integer.parseInt(request.getParameter("smtpport"));
/*      */           } catch (NumberFormatException num) {
/* 1409 */             num.printStackTrace();
/*      */           }
/* 1411 */           actionform.setSmtpport(smtpport);
/*      */         } else {
/* 1413 */           actionform.setSmtpport(Integer.parseInt((String)mailAction.get(7)));
/*      */         }
/*      */         
/* 1416 */         if (request.getParameter("mailformat") != null)
/*      */         {
/* 1418 */           if ("plaintext".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1419 */             actionform.setMailFormat("1");
/* 1420 */             MSreq.addParameter("mailformat", "1");
/* 1421 */           } else if ("html".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1422 */             actionform.setMailFormat("2");
/* 1423 */             MSreq.addParameter("mailformat", "2");
/* 1424 */           } else if ("both".equalsIgnoreCase(request.getParameter("mailformat"))) {
/* 1425 */             actionform.setMailFormat("0");
/* 1426 */             MSreq.addParameter("mailformat", "0");
/*      */           }
/*      */         } else {
/* 1429 */           actionform.setMailFormat((String)mailAction.get(5));
/*      */         }
/*      */         
/* 1432 */         if (request.getParameter("appemdmessage") == null) {
/* 1433 */           String mailFormat = (String)mailAction.get(8);
/* 1434 */           if ("1".equals(mailFormat)) {
/* 1435 */             MSreq.addParameter("appendMessage", "true");
/*      */           }
/* 1437 */         } else if ((request.getParameter("appendmessage") != null) && ("true".equals(request.getParameter("appendmessage")))) {
/* 1438 */           MSreq.addParameter("appendMessage", "true");
/*      */         }
/*      */         
/* 1441 */         if (mailAction.get(9) != null) {
/* 1442 */           MSreq.addParameter("actualBussinessID", (String)mailAction.get(9));
/*      */         }
/*      */         
/* 1445 */         if ((request.getParameter("businesshouraction") != null) && ("true".equals(request.getParameter("businesshouraction")))) {
/* 1446 */           if (request.getParameter("businessid") != null) {
/* 1447 */             MSreq.addParameter("selectedBusinessHourID", request.getParameter("businessid"));
/* 1448 */           } else if (request.getParameter("businesshourname") != null) {
/* 1449 */             int businessid = DBUtil.getBusinessHourID(request.getParameter("businesshourname"));
/* 1450 */             if (businessid == -1) {
/* 1451 */               return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessid");
/*      */             }
/* 1453 */             MSreq.addParameter("selectedBusinessHourID", businessid + "");
/*      */           }
/*      */           else {
/* 1456 */             return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessid");
/*      */           }
/*      */           
/* 1459 */           if (request.getParameter("businesstype") != null) {
/* 1460 */             MSreq.addParameter("businessType", request.getParameter("businesstype"));
/*      */           } else {
/* 1462 */             return APIUtilities.emptyParameterResponse(request, response, "EmailAction - create", "businessType");
/*      */           }
/* 1464 */           MSreq.addParameter("businessHourAssociatedToAction", "enabled");
/*      */         }
/*      */         
/*      */ 
/* 1468 */         MSreq.addParameter("adminAPIRequest", "true");
/* 1469 */         AdminActions action = new AdminActions();
/*      */         try {
/* 1471 */           action.editEmailAction(null, actionform, MSreq, response);
/* 1472 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.update.success.text"), "4000");
/*      */         }
/*      */         catch (Exception ex) {
/* 1475 */           ex.printStackTrace();
/* 1476 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.delete.failure.text"), "5009");
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1483 */       ex.printStackTrace();
/*      */     }
/* 1485 */     return null;
/*      */   }
/*      */   
/*      */   public static String deleteEmailAction(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1489 */     String outputString = null;
/*      */     try {
/* 1491 */       if (isJsonFormat) {
/* 1492 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1494 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/* 1496 */       String apiKey = request.getParameter("apikey");
/* 1497 */       int userId = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(apiKey));
/* 1498 */       boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(CommonAPIUtil.getUsername(apiKey));
/* 1499 */       Vector<String> actionIdsOwned = null;
/* 1500 */       if (isDelegatedAdmin) {
/* 1501 */         actionIdsOwned = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userId, 2);
/*      */       }
/*      */       
/* 1504 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/* 1505 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 1508 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1509 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/* 1511 */       if (request.getParameter("emailactionid") != null) {
/* 1512 */         String[] actionid = request.getParameter("emailactionid").split(",");
/* 1513 */         for (String id : actionid) {
/* 1514 */           if (((isAdminEmailAction(Integer.parseInt(id))) || ((actionIdsOwned != null) && (actionIdsOwned.contains(id)))) && (!"true".equals(request.getParameter("adminAPIRequest"))))
/*      */           {
/* 1516 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */           }
/* 1518 */           MSreq.addParameter("emailcheckbox", id);
/*      */         }
/* 1520 */       } else if (request.getParameter("emailactionname") != null) {
/* 1521 */         String actionname = request.getParameter("emailactionname");
/* 1522 */         int actionid = DBUtil.getActionId(actionname, 1);
/* 1523 */         if (((isAdminEmailAction(actionid)) || ((actionIdsOwned != null) && (actionIdsOwned.contains(Integer.toString(actionid))))) && (!"true".equals(request.getParameter("adminAPIRequest"))))
/*      */         {
/* 1525 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */         }
/* 1527 */         if (actionid != -1) {
/* 1528 */           MSreq.addParameter("emailcheckbox", actionid + "");
/*      */         } else {
/* 1530 */           return APIUtilities.wrongNameResponse(request, response, "EmailAction : delete", "emailactionname");
/*      */         }
/*      */       } else {
/* 1533 */         return APIUtilities.emptyParameterResponse(request, response, "EmailAction : delete", "emailactionid or emailactionname");
/*      */       }
/*      */       
/*      */ 
/* 1537 */       MSreq.addParameter("adminAPIRequest", "true");
/* 1538 */       AdminActions action = new AdminActions();
/*      */       try {
/* 1540 */         action.deleteEmailAction(null, null, MSreq, response);
/* 1541 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.delete.success.text"), "4000");
/*      */       }
/*      */       catch (Exception ex) {
/* 1544 */         ex.printStackTrace();
/* 1545 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.emailaction.delete.failure.text"), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1554 */       return null;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1552 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isAdminEmailAction(int actionId)
/*      */   {
/* 1558 */     return (EnterpriseUtil.isManagedServer()) && (10000 >= actionId) && (actionId < EnterpriseUtil.RANGE);
/*      */   }
/*      */   
/*      */   public static String updateThreshold(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1562 */     outputString = null;
/*      */     try
/*      */     {
/* 1565 */       if (isJsonFormat) {
/* 1566 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1568 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 1571 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/* 1572 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 1575 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1576 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/* 1578 */       int thresholdType = 0;
/* 1579 */       int thresholdID = 0;
/*      */       
/*      */ 
/* 1582 */       String thresholdName = null;
/*      */       
/* 1584 */       AMActionForm actionForm = new AMActionForm();
/*      */       
/* 1586 */       if (request.getParameter("thresholdid") != null) {
/* 1587 */         if (Constants.isIntegerNumber(request.getParameter("thresholdid"))) {
/* 1588 */           thresholdID = Integer.parseInt(request.getParameter("thresholdid"));
/* 1589 */           ResultSet rs = null;
/*      */           try {
/* 1591 */             rs = AMConnectionPool.executeQueryStmt("select ID from AM_THRESHOLDCONFIG where ID=" + thresholdID);
/* 1592 */             if (!rs.next()) {
/* 1593 */               AMLog.debug("REST API : Threshold ID does not exists");
/* 1594 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "id" }), "4103");
/* 1595 */               return outputString;
/*      */             }
/*      */           } catch (Exception ex) {
/* 1598 */             ((Exception)ex).printStackTrace();
/*      */           } finally {
/* 1600 */             if (rs != null) {
/* 1601 */               AMConnectionPool.closeResultSet(rs);
/*      */             }
/*      */           }
/* 1604 */           actionForm.setId(thresholdID);
/*      */         } else {
/* 1606 */           AMLog.debug("REST API : Threshold ID should be integer");
/* 1607 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "id" }), "4103");
/*      */         }
/*      */         
/*      */       }
/* 1611 */       else if (request.getParameter("thresholdname") != null) {
/* 1612 */         ResultSet rs = null;
/*      */         try {
/* 1614 */           thresholdName = request.getParameter("thresholdname");
/*      */           
/*      */ 
/*      */ 
/* 1618 */           if (thresholdName == null)
/* 1619 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.configurealert.alertthresholdname"), "4350");
/* 1620 */           if ("".equalsIgnoreCase(thresholdName))
/* 1621 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.thresholdname.empty.text"), "4351");
/* 1622 */           if (thresholdName.contains("'")) {
/* 1623 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.threshold.alertsingleqoute"), "4352");
/*      */           }
/* 1625 */           rs = AMConnectionPool.executeQueryStmt("select ID from AM_THRESHOLDCONFIG where NAME = '" + thresholdName + "'");
/* 1626 */           if (rs.next()) {
/* 1627 */             thresholdID = rs.getInt("ID");
/*      */           } else {
/* 1629 */             AMLog.debug("REST API : Wrong threshold name mentioned");
/* 1630 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.thresholdname.notexists.text", new String[] { "type" }), "4103");
/* 1631 */             return outputString;
/*      */           }
/*      */         } catch (Exception ex) {
/* 1634 */           ex.printStackTrace();
/*      */         } finally {
/* 1636 */           if (rs != null) {
/* 1637 */             AMConnectionPool.closeResultSet(rs);
/*      */           }
/*      */         }
/* 1640 */         actionForm.setId(thresholdID);
/*      */       } else {
/* 1642 */         AMLog.debug("REST API : Threshold id or Threshold name should be mentioned");
/* 1643 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "name" }), "4100");
/*      */       }
/*      */       
/* 1646 */       String apiKey = request.getParameter("apikey");
/* 1647 */       int userId = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(apiKey));
/* 1648 */       if (!DelegatedUserRoleUtil.isOwnedByDelegatedUser(thresholdID, userId, 1))
/*      */       {
/* 1650 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 1653 */       if (request.getParameter("type") != null) {
/* 1654 */         if (Constants.isIntegerNumber(request.getParameter("type"))) {
/* 1655 */           thresholdType = Integer.parseInt(request.getParameter("type"));
/* 1656 */           if ((thresholdType != 1) && (thresholdType != 3) && (thresholdType != 4)) {
/* 1657 */             AMLog.debug("REST API : Threshold Type should be either 1 3 or 4");
/* 1658 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.type.wrong.text"), "4102");
/*      */           }
/*      */         }
/*      */         else {
/* 1662 */           AMLog.debug("REST API : Threshold type should be integer");
/* 1663 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "type" }), "4103");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1668 */         AMLog.debug("REST API : Threshold type should be mentioned");
/* 1669 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "type" }), "4100");
/*      */       }
/*      */       
/*      */ 
/* 1673 */       Object thresholdDetails = getThresholdDetails(thresholdID, thresholdType);
/*      */       
/* 1675 */       if (request.getParameter("newthresholdname") != null) {
/* 1676 */         if (!DBQueryUtil.checkForDuplicateEntry("AM_THRESHOLDCONFIG", "NAME", request.getParameter("newthresholdname"), " and ID != " + thresholdID)) {
/* 1677 */           MSreq.addParameter("displayname", request.getParameter("newthresholdname"));
/*      */         } else {
/* 1679 */           AMLog.debug("REST API : Threshold name already exists");
/* 1680 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.value.exists.text", new String[] { "Threshold Name" }), "4101");
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 1685 */         MSreq.addParameter("displayname", (String)((HashMap)thresholdDetails).get("displayname"));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1690 */       if (request.getParameter("description") != null) {
/* 1691 */         MSreq.addParameter("description", request.getParameter("description"));
/*      */       } else {
/* 1693 */         MSreq.addParameter("description", (String)((HashMap)thresholdDetails).get("description"));
/*      */       }
/*      */       
/*      */ 
/* 1697 */       setThresholdParams(request, response, MSreq, thresholdType, (HashMap)thresholdDetails, true);
/* 1698 */       if (MSreq.getAttribute("errorResponse") != null) {
/* 1699 */         MSreq.removeAttribute("errorString");
/* 1700 */         return (String)MSreq.getAttribute("errorResponse");
/*      */       }
/* 1702 */       convertSecondaryCondtionAPIParamstoActionParams(request, response, MSreq);
/*      */       
/*      */ 
/* 1705 */       if (thresholdType == 4) {
/* 1706 */         MSreq.addParameter("select", "thresholdFloat");
/*      */       }
/*      */       
/* 1709 */       MSreq.addParameter("apirequest", "true");
/* 1710 */       MSreq.addParameter("returnpath", "");
/* 1711 */       AdminActions action = new AdminActions();
/*      */       try {
/* 1713 */         if (thresholdType == 1) {
/* 1714 */           action.editThresholdAction(null, actionForm, MSreq, response);
/*      */         } else {
/* 1716 */           action.editPatternAction(null, actionForm, MSreq, response);
/*      */         }
/* 1718 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.update.success.text"), "4000");
/*      */       }
/*      */       catch (Exception ex) {
/* 1721 */         ex.printStackTrace();
/* 1722 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.threshold.update.failure.text"), "5009");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1729 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1727 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static HashMap<String, String> getThresholdDetails(int thresholdid, int type)
/*      */   {
/* 1733 */     ResultSet rs = null;
/* 1734 */     HashMap<String, String> thresholdDetails = new HashMap();
/*      */     try {
/* 1736 */       String query = null;
/* 1737 */       if (type == 1) {
/* 1738 */         query = "SELECT NAME,DESCRIPTION,CRITICALTHRESHOLDCONDITION,CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE,WARNINGTHRESHOLDCONDITION,WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE,INFOTHRESHOLDCONDITION,INFOTHRESHOLDVALUE,INFOTHRESHOLDMESSAGE,CASE WHEN CRITICAL_POLLSTOTRY != - 1  THEN CRITICAL_POLLSTOTRY ELSE 0 END as CRITICALPOLLS,CASE WHEN WARNING_POLLSTOTRY != - 1  THEN WARNING_POLLSTOTRY ELSE 0 END AS WARNINGPOLLS ,CASE WHEN CLEAR_POLLSTOTRY != - 1  THEN CLEAR_POLLSTOTRY ELSE 0 END AS CLEARPOLLS,CASE WHEN MIN_CRITICAL_POLLSTOTRY = NULL then 0 when MIN_CRITICAL_POLLSTOTRY != -1 THEN MIN_CRITICAL_POLLSTOTRY ELSE 0 END as MINCRITICALPOLLS,CASE WHEN MIN_WARNING_POLLSTOTRY = NULL then 0 when MIN_WARNING_POLLSTOTRY != -1 THEN MIN_WARNING_POLLSTOTRY ELSE 0 END as MINWARNINGPOLLS,CASE WHEN MIN_CLEAR_POLLSTOTRY = NULL then 0 when MIN_CLEAR_POLLSTOTRY != -1 THEN MIN_CLEAR_POLLSTOTRY ELSE 0 END as MINCLEARPOLLS FROM  AM_THRESHOLDCONFIG WHERE ID=" + thresholdid;
/* 1739 */       } else if (type == 4) {
/* 1740 */         query = "select NAME, DESCRIPTION, CRITICALTHRESHOLDCONDITION,AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE, CRITICALTHRESHOLDMESSAGE, WARNINGTHRESHOLDCONDITION,AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE, WARNINGTHRESHOLDMESSAGE, INFOTHRESHOLDCONDITION, AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE, INFOTHRESHOLDMESSAGE,CASE WHEN CRITICAL_POLLSTOTRY != - 1  THEN CRITICAL_POLLSTOTRY ELSE '0' END as CRITICALPOLLS,CASE WHEN WARNING_POLLSTOTRY != - 1  THEN WARNING_POLLSTOTRY  ELSE '0' END as WARNINGPOLLS,CASE WHEN CLEAR_POLLSTOTRY != - 1  THEN CLEAR_POLLSTOTRY ELSE '0' END as CLEARPOLLS,CASE WHEN MIN_CRITICAL_POLLSTOTRY = NULL then 0 when MIN_CRITICAL_POLLSTOTRY != -1 THEN MIN_CRITICAL_POLLSTOTRY ELSE 0 END as MINCRITICALPOLLS,CASE WHEN MIN_WARNING_POLLSTOTRY = NULL then 0 when MIN_WARNING_POLLSTOTRY != -1 THEN MIN_WARNING_POLLSTOTRY ELSE 0 END as MINWARNINGPOLLS,CASE WHEN MIN_CLEAR_POLLSTOTRY = NULL then 0 when MIN_CLEAR_POLLSTOTRY != -1 THEN MIN_CLEAR_POLLSTOTRY ELSE 0 END as MINCLEARPOLLS  from AM_THRESHOLDCONFIG,AM_FLOAT_THRESHOLDCONFIG where AM_THRESHOLDCONFIG.ID=AM_FLOAT_THRESHOLDCONFIG.ID and  AM_THRESHOLDCONFIG.ID=" + thresholdid;
/*      */       } else {
/* 1742 */         query = "select NAME, DESCRIPTION, CRITICALTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE, CRITICALTHRESHOLDMESSAGE, WARNINGTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE, WARNINGTHRESHOLDMESSAGE, INFOTHRESHOLDCONDITION, AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE, INFOTHRESHOLDMESSAGE,CASE WHEN CRITICAL_POLLSTOTRY != - 1  THEN CRITICAL_POLLSTOTRY ELSE '0' END as CRITICALPOLLS,CASE WHEN WARNING_POLLSTOTRY != - 1  THEN WARNING_POLLSTOTRY  ELSE '0' END as WARNINGPOLLS,CASE WHEN CLEAR_POLLSTOTRY != - 1  THEN CLEAR_POLLSTOTRY ELSE '0' END as CLEARPOLLS,CASE WHEN MIN_CRITICAL_POLLSTOTRY = NULL then 0 when MIN_CRITICAL_POLLSTOTRY != -1 THEN MIN_CRITICAL_POLLSTOTRY ELSE 0 END as MINCRITICALPOLLS,CASE WHEN MIN_WARNING_POLLSTOTRY = NULL then 0 when MIN_WARNING_POLLSTOTRY != -1 THEN MIN_WARNING_POLLSTOTRY ELSE 0 END as MINWARNINGPOLLS,CASE WHEN MIN_CLEAR_POLLSTOTRY = NULL then 0 when MIN_CLEAR_POLLSTOTRY != -1 THEN MIN_CLEAR_POLLSTOTRY ELSE 0 END as MINCLEARPOLLS  from AM_THRESHOLDCONFIG,AM_PATTERNMATCHERCONFIG where AM_THRESHOLDCONFIG.ID=AM_PATTERNMATCHERCONFIG.ID and  AM_THRESHOLDCONFIG.ID=" + thresholdid;
/*      */       }
/* 1744 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1745 */       if (rs.next()) {
/* 1746 */         thresholdDetails.put("displayname", rs.getString("NAME"));
/* 1747 */         thresholdDetails.put("description", rs.getString("DESCRIPTION"));
/* 1748 */         thresholdDetails.put("criticalcondition", rs.getString("CRITICALTHRESHOLDCONDITION"));
/* 1749 */         thresholdDetails.put("criticalvalue", rs.getString("CRITICALTHRESHOLDVALUE"));
/* 1750 */         thresholdDetails.put("criticalmessage", rs.getString("CRITICALTHRESHOLDMESSAGE"));
/* 1751 */         thresholdDetails.put("warningcondition", rs.getString("WARNINGTHRESHOLDCONDITION"));
/* 1752 */         thresholdDetails.put("warningvalue", rs.getString("WARNINGTHRESHOLDVALUE"));
/* 1753 */         thresholdDetails.put("warningmessage", rs.getString("WARNINGTHRESHOLDMESSAGE"));
/* 1754 */         thresholdDetails.put("clearcondition", rs.getString("INFOTHRESHOLDCONDITION"));
/* 1755 */         thresholdDetails.put("clearvalue", rs.getString("INFOTHRESHOLDVALUE"));
/* 1756 */         thresholdDetails.put("clearmessage", rs.getString("INFOTHRESHOLDMESSAGE"));
/* 1757 */         thresholdDetails.put("criticalpolls", rs.getString("CRITICALPOLLS"));
/* 1758 */         thresholdDetails.put("warningpolls", rs.getString("WARNINGPOLLS"));
/* 1759 */         thresholdDetails.put("clearpolls", rs.getString("CLEARPOLLS"));
/* 1760 */         thresholdDetails.put("min_criticalpolls", rs.getString("MINCRITICALPOLLS"));
/* 1761 */         thresholdDetails.put("min_warningpolls", rs.getString("MINWARNINGPOLLS"));
/* 1762 */         thresholdDetails.put("min_clearpolls", rs.getString("MINCLEARPOLLS"));
/*      */       }
/*      */     } catch (Exception ex) {
/* 1765 */       ex.printStackTrace();
/*      */     } finally {
/* 1767 */       if (rs != null) {
/* 1768 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 1771 */     return thresholdDetails;
/*      */   }
/*      */   
/*      */   public static String emailActionAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1776 */     if (request.getMethod().equals("GET"))
/*      */     {
/* 1778 */       return listEmailActions(request, response, isJsonFormat);
/*      */     }
/* 1780 */     return emailActionPostOperations(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */   public static String listEmailActions(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1785 */     String outputString = null;
/* 1786 */     ArrayList<Hashtable<String, String>> emailActionsList = new ArrayList();
/* 1787 */     ResultSet rs = null;
/*      */     try {
/* 1789 */       String uri = request.getRequestURI();
/*      */       
/* 1791 */       boolean isPrivilegedUser = CommonAPIUtil.isPrivilegedUser(request);
/* 1792 */       String query = "select AM_EMAILACTION.ID,NAME,FROMADDRESS, TOADDRESS, SUBJECT from AM_EMAILACTION,AM_ACTIONPROFILE where AM_EMAILACTION.ID =AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.TYPE=1 order by AM_ACTIONPROFILE.NAME";
/* 1793 */       if (isPrivilegedUser)
/*      */       {
/* 1795 */         Vector<String> resids = new Vector();
/* 1796 */         Vector<String> configIds = new Vector();
/*      */         
/* 1798 */         Hashtable<String, String> userDetails = CommonAPIUtil.getUserNameForAPIKey(request.getParameter("apikey"));
/* 1799 */         String userName = (String)userDetails.get("USERNAME");
/* 1800 */         String userId = (String)userDetails.get("USERID");
/*      */         
/* 1802 */         resids = DelegatedUserRoleUtil.getMonitorsForUser(userId);
/* 1803 */         boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(userName);
/* 1804 */         if (isDelegatedAdmin) {
/* 1805 */           if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/* 1806 */             configIds = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(Integer.parseInt(userId), 2);
/*      */           }
/*      */           else {
/* 1809 */             configIds = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(Integer.parseInt(userId), 2);
/*      */           }
/*      */         }
/* 1812 */         String configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or prof.ID", configIds) : "";
/* 1813 */         query = "select email.ID,NAME,FROMADDRESS, TOADDRESS, SUBJECT from AM_EMAILACTION email,AM_ACTIONPROFILE prof where prof.ID=email.ID and prof.TYPE=1 and (email.ID in (select distinct ACTIONID from AM_ATTRIBUTEACTIONMAPPER aam,AM_ACTIONPROFILE prof where " + DependantMOUtil.getCondition("aam.ID", resids) + " and prof.ID=aam.ACTIONID)" + configIdCond + ")";
/*      */       }
/* 1815 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1816 */       while (rs.next()) {
/* 1817 */         Hashtable<String, String> emailAction = new Hashtable();
/* 1818 */         emailAction.put("ACTIONNAME", rs.getString("NAME"));
/* 1819 */         emailAction.put("ACTIONID", rs.getString("ID"));
/* 1820 */         emailAction.put("SUBJECT", rs.getString("SUBJECT"));
/* 1821 */         emailAction.put("FROMADDRESS", rs.getString("FROMADDRESS"));
/* 1822 */         emailAction.put("TOADDRESS", rs.getString("TOADDRESS"));
/* 1823 */         emailActionsList.add(emailAction);
/*      */       }
/* 1825 */       if (emailActionsList.size() == 0) {
/* 1826 */         Hashtable<String, String> msg = new Hashtable();
/* 1827 */         msg.put("Message", FormatUtil.getString("am.webclient.api.emailaction.list.empty.text"));
/* 1828 */         emailActionsList.add(msg);
/*      */       }
/* 1830 */       HashMap results = new HashMap();
/* 1831 */       results.put("response-code", "4000");
/* 1832 */       results.put("uri", uri);
/* 1833 */       results.put("result", emailActionsList);
/* 1834 */       results.put("sortingParam", "ACTIONNAME");
/* 1835 */       results.put("parentNode", "EmailActionsList");
/* 1836 */       results.put("nodeName", "EmailAction");
/* 1837 */       outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 1839 */       ex.printStackTrace();
/*      */     } finally {
/* 1841 */       if (rs != null) {
/* 1842 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 1845 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String emailActionPostOperations(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1850 */     if (request.getParameter("emailactionname") != null)
/*      */     {
/* 1852 */       String actionName = request.getParameter("emailactionname");
/* 1853 */       if (!actionName.matches("^[ A-Za-z0-9_@./#+&-]*$")) {
/* 1854 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.specialchar.alert.displayname"), "4037");
/*      */       }
/* 1856 */       String id = request.getParameter("actionid");
/* 1857 */       if (id == null) {
/* 1858 */         return createEmailAction(request, response, isJsonFormat);
/*      */       }
/* 1860 */       boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_ACTIONPROFILE", "ID", id, "");
/* 1861 */       if (!exists) {
/* 1862 */         return createEmailAction(request, response, isJsonFormat);
/*      */       }
/* 1864 */       return updateEmailAction(request, response, isJsonFormat);
/*      */     }
/*      */     
/* 1867 */     if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*      */     {
/* 1869 */       return deleteEmailAction(request, response, isJsonFormat);
/*      */     }
/* 1871 */     String id = request.getParameter("emailactionid");
/* 1872 */     boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_ACTIONPROFILE", "ID", id, "");
/* 1873 */     if (!exists)
/*      */     {
/* 1875 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1876 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/* 1878 */       Map map = request.getParameterMap();
/* 1879 */       for (Object key : map.keySet())
/*      */       {
/* 1881 */         String keyStr = (String)key;
/* 1882 */         String[] value = (String[])map.get(keyStr);
/* 1883 */         MSreq.addParameter((String)key, value[0].toString());
/*      */       }
/* 1885 */       MSreq.addParameter("actionid", request.getParameter("emailactionid"));
/* 1886 */       MSreq.addParameter("emailactionname", request.getParameter("displayname"));
/* 1887 */       return createEmailAction(MSreq, response, isJsonFormat);
/*      */     }
/* 1889 */     return updateEmailAction(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */ 
/*      */   public static String thresholdConfigAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 1896 */     if (request.getMethod().equals("GET"))
/*      */     {
/* 1898 */       return listThreshold(request, response, isJsonFormat);
/*      */     }
/* 1900 */     return thresholdPostOperations(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */   public static String thresholdPostOperations(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 1906 */     if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*      */     {
/* 1908 */       return deleteThreshold(request, response, isJsonFormat); }
/* 1909 */     if (request.getParameter("thresholdname") != null)
/*      */     {
/* 1911 */       String thresholdName = request.getParameter("thresholdname");
/*      */       
/*      */ 
/*      */ 
/* 1915 */       if (thresholdName == null)
/* 1916 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.configurealert.alertthresholdname"), "4350");
/* 1917 */       if ("".equalsIgnoreCase(thresholdName))
/* 1918 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.thresholdname.empty.text"), "4351");
/* 1919 */       if (thresholdName.contains("'")) {
/* 1920 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.threshold.alertsingleqoute"), "4352");
/*      */       }
/* 1922 */       boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_THRESHOLDCONFIG", "NAME", thresholdName, "");
/* 1923 */       if (!exists) {
/* 1924 */         return createThreshold(request, response, isJsonFormat);
/*      */       }
/* 1926 */       return updateThreshold(request, response, isJsonFormat);
/*      */     }
/*      */     
/*      */ 
/* 1930 */     if (request.getParameter("thresholdid") != null) {
/* 1931 */       String thresholdid = request.getParameter("thresholdid");
/* 1932 */       boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_THRESHOLDCONFIG", "ID", thresholdid, "");
/* 1933 */       if (!exists) {
/* 1934 */         String thresholdname = request.getParameter("newthresholdname");
/*      */         
/* 1936 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1937 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 1938 */         Map map = request.getParameterMap();
/* 1939 */         for (Object key : map.keySet())
/*      */         {
/* 1941 */           String keyStr = (String)key;
/* 1942 */           String[] value = (String[])map.get(keyStr);
/* 1943 */           MSreq.addParameter((String)key, value[0].toString());
/*      */         }
/* 1945 */         MSreq.addParameter("thresholdname", thresholdname);
/* 1946 */         return createThreshold(MSreq, response, isJsonFormat);
/*      */       }
/*      */     }
/* 1949 */     return updateThreshold(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String getThresholdCondition(String str)
/*      */   {
/* 1956 */     return " CASE " + str + " when 'LT' then '<' WHEN 'GT' THEN '>' when 'EQ' then '=' WHEN 'NE' THEN '!=' when 'LE' then '<=' WHEN 'GE' THEN '>=' WHEN 'CT' THEN 'contains' WHEN 'DC' THEN 'does not contain' WHEN 'QL' THEN 'equals' WHEN 'NQ' THEN 'not equal to' WHEN 'DC' THEN 'does not contain' WHEN 'SW' THEN 'starts with' WHEN 'EW' THEN 'ends with' END  ";
/*      */   }
/*      */   
/*      */   public static String listThreshold(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1960 */     String outputString = null;
/* 1961 */     ArrayList<Hashtable> thresholdList = new ArrayList();
/* 1962 */     ResultSet rs = null;
/*      */     try {
/* 1964 */       String uri = request.getRequestURI();
/* 1965 */       String query = "SELECT AM_THRESHOLDCONFIG.ID,NAME,DESCRIPTION," + getThresholdCondition("CRITICALTHRESHOLDCONDITION") + " as CRITICALTHRESHOLDCONDITION,CASE WHEN TYPE = 3  THEN AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE WHEN TYPE = 4  THEN AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE ELSE AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE END as CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE," + getThresholdCondition("WARNINGTHRESHOLDCONDITION") + " as WARNINGTHRESHOLDCONDITION,CASE WHEN TYPE = 3  THEN AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE WHEN  TYPE = 4  THEN AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE ELSE AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE END as WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE," + getThresholdCondition("INFOTHRESHOLDCONDITION") + " as INFOTHRESHOLDCONDITION,CASE\tWHEN TYPE = 3  THEN AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE  WHEN TYPE = 4  THEN AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE  ELSE AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE END as INFOTHRESHOLDVALUE, INFOTHRESHOLDMESSAGE,TYPE  FROM  AM_THRESHOLDCONFIG LEFT OUTER JOIN AM_PATTERNMATCHERCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_PATTERNMATCHERCONFIG.ID   LEFT OUTER JOIN AM_FLOAT_THRESHOLDCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_FLOAT_THRESHOLDCONFIG.ID WHERE AM_THRESHOLDCONFIG.NAME  NOT IN ( 'Marker THRESHOLD','Availability Threshold','Health Threshold') AND AM_THRESHOLDCONFIG.ID  > 2 AND\tDESCRIPTION  <> '##Threshod for URL##'";
/*      */       
/* 1967 */       if (DBQueryUtil.isPgsql()) {
/* 1968 */         query = "SELECT AM_THRESHOLDCONFIG.ID,NAME,DESCRIPTION," + getThresholdCondition("CRITICALTHRESHOLDCONDITION") + " as CRITICALTHRESHOLDCONDITION,CASE WHEN TYPE = 3  THEN cast(AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE as varchar)  WHEN TYPE = 4  THEN cast(AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE as varchar) ELSE cast(AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE as varchar) END as CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE," + getThresholdCondition("WARNINGTHRESHOLDCONDITION") + " as WARNINGTHRESHOLDCONDITION,CASE WHEN TYPE = 3  THEN cast(AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE as varchar)  WHEN TYPE = 4  THEN cast(AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE as varchar) ELSE cast(AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE as varchar) END as WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE," + getThresholdCondition("INFOTHRESHOLDCONDITION") + " as INFOTHRESHOLDCONDITION,CASE        WHEN TYPE = 3  THEN cast(AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE as varchar)  WHEN TYPE = 4  THEN cast(AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE as varchar)  ELSE cast(AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE as varchar) END as INFOTHRESHOLDVALUE, INFOTHRESHOLDMESSAGE,TYPE  FROM  AM_THRESHOLDCONFIG LEFT OUTER JOIN AM_PATTERNMATCHERCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_PATTERNMATCHERCONFIG.ID   LEFT OUTER JOIN AM_FLOAT_THRESHOLDCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_FLOAT_THRESHOLDCONFIG.ID  WHERE AM_THRESHOLDCONFIG.NAME  NOT IN ( 'Marker THRESHOLD','Availability Threshold','Health Threshold') AND AM_THRESHOLDCONFIG.ID  > 2 AND        DESCRIPTION  <> '##Threshod for URL##'";
/*      */       }
/* 1970 */       else if (DBQueryUtil.isMssql())
/*      */       {
/* 1972 */         query = "SELECT AM_THRESHOLDCONFIG.ID,NAME,DESCRIPTION," + getThresholdCondition("CRITICALTHRESHOLDCONDITION") + " as CRITICALTHRESHOLDCONDITION,CASE WHEN TYPE = 3  THEN Convert(varchar(1000),AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE)  WHEN TYPE = 4  THEN Convert(varchar(1000),AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE) ELSE Convert(varchar(1000),AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE) END as CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE," + getThresholdCondition("WARNINGTHRESHOLDCONDITION") + " as WARNINGTHRESHOLDCONDITION ,CASE WHEN TYPE = 3  THEN Convert(varchar(1000),AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE)  WHEN TYPE = 4  THEN Convert(varchar(1000),AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE) ELSE Convert(varchar(1000),AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE) END as WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE," + getThresholdCondition("INFOTHRESHOLDCONDITION") + " as INFOTHRESHOLDCONDITION ,CASE        WHEN TYPE = 3  THEN Convert(varchar(1000),AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE)  WHEN TYPE = 4  THEN Convert(varchar(1000),AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE)  ELSE Convert(varchar(1000),AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE) END as INFOTHRESHOLDVALUE, INFOTHRESHOLDMESSAGE,TYPE  FROM  AM_THRESHOLDCONFIG LEFT OUTER JOIN AM_PATTERNMATCHERCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_PATTERNMATCHERCONFIG.ID   LEFT OUTER JOIN AM_FLOAT_THRESHOLDCONFIG ON AM_THRESHOLDCONFIG.ID  = AM_FLOAT_THRESHOLDCONFIG.ID  WHERE AM_THRESHOLDCONFIG.NAME  NOT IN ( 'Marker THRESHOLD','Availability Threshold','Health Threshold') AND AM_THRESHOLDCONFIG.ID  > 2 AND        DESCRIPTION  <> '##Threshod for URL##'";
/*      */       }
/* 1974 */       String apiKey = request.getParameter("apikey");
/* 1975 */       boolean isPrivilegedUser = CommonAPIUtil.isPrivilegedUser(apiKey);
/* 1976 */       if (isPrivilegedUser)
/*      */       {
/* 1978 */         int userID = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(apiKey));
/* 1979 */         Vector filterRESID = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(String.valueOf(userID));
/* 1980 */         String tempQuery = "select Distinct THRESHOLDCONFIGURATIONID from AM_ATTRIBUTETHRESHOLDMAPPER LEFT OUTER JOIN AM_ManagedObject MOChild ON  AM_ATTRIBUTETHRESHOLDMAPPER.ID=MOChild.RESOURCEID  LEFT OUTER JOIN AM_PARENTCHILDMAPPER pcm on pcm.CHILDID=MOChild.RESOURCEID LEFT OUTER JOIN AM_ManagedObject MOParent on MOParent.RESOURCEID=pcm.PARENTID JOIN AM_ATTRIBUTES ON AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=AM_ATTRIBUTES.ATTRIBUTEID and (" + DBUtil.getCondition("MOParent.RESOURCEID", filterRESID) + " or " + DBUtil.getCondition("MOChild.RESOURCEID", filterRESID) + ")";
/* 1981 */         Vector filterConfID = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(userID, 1, DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllThresholds"));
/* 1982 */         query = query + "AND (AM_THRESHOLDCONFIG.ID in (" + tempQuery + ") or " + DBUtil.getCondition("AM_THRESHOLDCONFIG.ID", filterConfID) + ")";
/*      */       }
/* 1984 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1985 */       while (rs.next()) {
/* 1986 */         Hashtable<String, String> thresholds = new Hashtable();
/* 1987 */         thresholds.put("THRESHOLDID", rs.getString("ID"));
/* 1988 */         thresholds.put("THRESHOLDNAME", rs.getString("NAME"));
/* 1989 */         thresholds.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 1990 */         thresholds.put("CRITICALTHRESHOLDCONDITION", rs.getString("CRITICALTHRESHOLDCONDITION"));
/* 1991 */         thresholds.put("CRITICALTHRESHOLDVALUE", rs.getString("CRITICALTHRESHOLDVALUE"));
/* 1992 */         thresholds.put("CRITICALTHRESHOLDMESSAGE", rs.getString("CRITICALTHRESHOLDMESSAGE"));
/* 1993 */         thresholds.put("WARNINGTHRESHOLDCONDITION", rs.getString("WARNINGTHRESHOLDCONDITION"));
/* 1994 */         thresholds.put("WARNINGTHRESHOLDVALUE", rs.getString("WARNINGTHRESHOLDVALUE"));
/* 1995 */         thresholds.put("WARNINGTHRESHOLDMESSAGE", rs.getString("WARNINGTHRESHOLDMESSAGE"));
/* 1996 */         thresholds.put("CLEARTHRESHOLDCONDITION", rs.getString("INFOTHRESHOLDCONDITION"));
/* 1997 */         thresholds.put("CLEARTHRESHOLDVALUE", rs.getString("INFOTHRESHOLDVALUE"));
/* 1998 */         thresholds.put("CLEARTHRESHOLDMESSAGE", rs.getString("INFOTHRESHOLDMESSAGE"));
/* 1999 */         String type = rs.getString("TYPE");
/* 2000 */         if ("4".equalsIgnoreCase(type)) {
/* 2001 */           thresholds.put("CRITICALTHRESHOLDVALUE", rs.getFloat("CRITICALTHRESHOLDVALUE") + "");
/* 2002 */           thresholds.put("WARNINGTHRESHOLDVALUE", rs.getFloat("WARNINGTHRESHOLDVALUE") + "");
/* 2003 */           thresholds.put("CLEARTHRESHOLDVALUE", rs.getFloat("INFOTHRESHOLDVALUE") + "");
/*      */         }
/* 2005 */         thresholdList.add(thresholds);
/*      */       }
/* 2007 */       if (thresholdList.size() == 0) {
/* 2008 */         Hashtable msg = new Hashtable();
/* 2009 */         msg.put("Message", FormatUtil.getString("am.webclient.api.threshold.list.empty.message.text"));
/* 2010 */         thresholdList.add(msg);
/*      */       }
/* 2012 */       HashMap results = new HashMap();
/* 2013 */       results.put("response-code", "4000");
/* 2014 */       results.put("uri", uri);
/* 2015 */       results.put("result", thresholdList);
/* 2016 */       results.put("sortingParam", "THRESHOLDNAME");
/* 2017 */       results.put("parentNode", "Thresholds");
/* 2018 */       results.put("nodeName", "Threshold");
/* 2019 */       outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 2021 */       ex.printStackTrace();
/*      */     } finally {
/* 2023 */       if (rs != null) {
/* 2024 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 2027 */     return outputString;
/*      */   }
/*      */   
/*      */   public static void checkAndPopulateThresholdForm(String thcondition, String thvalue, AMActionForm amform, int severity)
/*      */   {
/* 2032 */     String condition1 = thcondition;
/* 2033 */     String thvalue1 = thvalue;
/* 2034 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(thcondition, false);
/* 2035 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 6)) {
/* 2036 */       condition1 = (String)criticalThresholdValues.get(0);
/* 2037 */       thvalue1 = (String)criticalThresholdValues.get(1);
/*      */       
/* 2039 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2040 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2041 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/* 2042 */       if (severity == 1) {
/* 2043 */         amform.setSecondarycriticalexist("true");
/* 2044 */         amform.setCriticalconditionjoiner(conditionjoiner);
/* 2045 */         amform.setSecondarycriticalthresholdcondition(condition2);
/* 2046 */         amform.setSecondarycriticalthresholdvalue(thvalue2);
/* 2047 */       } else if (severity == 4) {
/* 2048 */         amform.setSecondarywarningexist("true");
/* 2049 */         amform.setWarningconditionjoiner(conditionjoiner);
/* 2050 */         amform.setSecondarywarningthresholdcondition(condition2);
/* 2051 */         amform.setSecondarywarningthresholdvalue(thvalue2);
/*      */       } else {
/* 2053 */         amform.setSecondaryinfoexist("true");
/* 2054 */         amform.setInfoconditionjoiner(conditionjoiner);
/* 2055 */         amform.setSecondaryinfothresholdcondition(condition2);
/* 2056 */         amform.setSecondaryinfothresholdvalue(thvalue2);
/*      */       }
/*      */     }
/*      */     
/* 2060 */     if (severity == 1) {
/* 2061 */       amform.setCriticalthresholdcondition(condition1);
/* 2062 */       amform.setCriticalthresholdvalue(thvalue1);
/* 2063 */     } else if (severity == 4) {
/* 2064 */       amform.setWarningthresholdcondition(condition1);
/* 2065 */       amform.setWarningthresholdvalue(thvalue1);
/*      */     } else {
/* 2067 */       amform.setInfothresholdcondition(condition1);
/* 2068 */       amform.setInfothresholdvalue(thvalue1);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void checkAndpopulateThresholdValues(String thcondition, String thValue, List arr)
/*      */   {
/* 2074 */     List<String> criticalThresholdValues = AMRegexUtil.getThresholdGroups(thcondition, true);
/*      */     
/* 2076 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 6)) {
/* 2077 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2078 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2079 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2080 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2081 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2084 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2085 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2086 */       arr.add(multiplecondition.toString());
/* 2087 */       arr.add("");
/*      */     } else {
/* 2089 */       arr.add(thcondition);
/* 2090 */       arr.add(thValue);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean removeAlarms(int resourceId, String[] attributeId)
/*      */   {
/*      */     try
/*      */     {
/* 2103 */       StringBuilder queryBuilder = new StringBuilder();
/* 2104 */       ArrayList<String> aListQueries = new ArrayList();
/* 2105 */       for (int i = 0; i < attributeId.length; i++)
/*      */       {
/*      */ 
/* 2108 */         queryBuilder.append("DELETE FROM AM_ATTRIBUTETHRESHOLDMAPPER where  ID=").append(resourceId).append(" and ATTRIBUTE=").append(attributeId[i]);
/* 2109 */         aListQueries.add(queryBuilder.toString());
/* 2110 */         queryBuilder.delete(0, queryBuilder.length());
/*      */         
/* 2112 */         queryBuilder.append("DELETE FROM AM_ATTRIBUTEACTIONMAPPER where ID=").append(resourceId).append(" AND ATTRIBUTE=").append(attributeId[i]);
/* 2113 */         aListQueries.add(queryBuilder.toString());
/* 2114 */         queryBuilder.delete(0, queryBuilder.length());
/*      */       }
/* 2116 */       if (aListQueries.size() != 0) {
/* 2117 */         AMBatchStmtExecutor.executeBatch(aListQueries);
/*      */       }
/*      */     } catch (Exception ex) {
/* 2120 */       ex.printStackTrace();
/* 2121 */       return false;
/*      */     }
/* 2123 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\ThresholdActionsAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */