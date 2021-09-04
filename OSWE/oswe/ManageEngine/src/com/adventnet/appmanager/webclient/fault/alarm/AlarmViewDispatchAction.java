/*     */ package com.adventnet.appmanager.webclient.fault.alarm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.AMActionExecuter;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMFileDataCollector;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.ExtConnectorUtil;
/*     */ import com.adventnet.appmanager.util.OPMAlarmOperationsHandler;
/*     */ import com.adventnet.appmanager.util.SANUtil;
/*     */ import com.adventnet.nms.util.GenericUtility;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.webclient.fault.alarm.AlarmOperationsUtility;
/*     */ import com.me.apm.cmdb.APMHelpDeskUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
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
/*     */ 
/*     */ public class AlarmViewDispatchAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward executeActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  80 */     String actionType = request.getParameter("selectView");
/*  81 */     String entity = request.getParameter("selectedEntity");
/*     */     
/*  83 */     if ("form".equalsIgnoreCase(actionType))
/*     */     {
/*  85 */       APMHelpDeskUtil.raiseATicket(request);
/*     */ 
/*     */     }
/*  88 */     else if ("Action".equalsIgnoreCase(actionType))
/*     */     {
/*  90 */       String actionId = request.getParameter("actionId");
/*  91 */       StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*  92 */       String persist = request.getParameter("persist");
/*  93 */       while (tokenize.hasMoreTokens())
/*     */       {
/*  95 */         String tempEntity = tokenize.nextToken();
/*  96 */         HashMap<String, String> alertInfo = FaultUtil.getLatestEvent(tempEntity);
/*  97 */         if ((alertInfo != null) && (alertInfo.size() > 0))
/*     */         {
/*  99 */           int severity = Integer.parseInt((String)alertInfo.get("SEVERITY"));
/* 100 */           int attrId = Integer.parseInt((String)alertInfo.get("CATEGORY"));
/* 101 */           int source = Integer.parseInt((String)alertInfo.get("RESOURCE"));
/* 102 */           long time = Long.parseLong((String)alertInfo.get("TIME"));
/* 103 */           String message = (String)alertInfo.get("MESSAGE");
/*     */           try {
/* 105 */             List<Integer> actions = new ArrayList();
/* 106 */             actions.add(Integer.valueOf(Integer.parseInt(actionId)));
/* 107 */             AMActionExecuter.executeAction(source, attrId, severity, message, time, actions);
/* 108 */             if ("true".equalsIgnoreCase(persist))
/*     */             {
/*     */               try
/*     */               {
/* 112 */                 String qry = "insert into AM_ATTRIBUTEACTIONMAPPER (ID, ATTRIBUTE, SEVERITY, ACTIONID) values (";
/* 113 */                 AMConnectionPool.executeUpdateStmt(qry + source + "," + attrId + "," + 1 + "," + actionId + ")");
/* 114 */                 if (!FaultUtil.isAvailabilityId(Integer.toString(attrId)))
/*     */                 {
/* 116 */                   AMConnectionPool.executeUpdateStmt(qry + source + "," + attrId + "," + 4 + "," + actionId + ")");
/*     */                 }
/* 118 */                 AMConnectionPool.executeUpdateStmt(qry + source + "," + attrId + "," + 5 + "," + actionId + ")");
/*     */                 
/* 120 */                 if (FaultUtil.isHealthId(Integer.toString(attrId)))
/*     */                 {
/*     */ 
/* 123 */                   DBUtil.insertValuesForAttributeActionStatus(source, attrId, 1, 0);
/* 124 */                   DBUtil.insertValuesForAttributeActionStatus(source, attrId, 4, 0);
/*     */                 }
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 129 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {
/* 133 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 138 */     String viewId = request.getParameter("viewId");
/* 139 */     String redirectto = request.getParameter("redirectto");
/* 140 */     request.setAttribute("viewId", viewId);
/* 141 */     return mapping.findForward("executeActions");
/*     */   }
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
/*     */   public ActionForward clearAlarm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 160 */     ActionErrors errors = new ActionErrors();
/* 161 */     ActionMessages messages = new ActionMessages();
/* 162 */     AMLog.debug("FAULTD Clear Alarm called");
/* 163 */     String userName = (String)request.getSession().getAttribute("userName");
/* 164 */     String entity = request.getParameter("selectedEntity");
/* 165 */     String fromCentral = request.getParameter("actionFromCentral");
/* 166 */     String adminServerRequest = request.getParameter("requestFromAdmin");
/* 167 */     String viewId = request.getParameter("viewId");
/* 168 */     request.setAttribute("viewId", viewId);
/* 169 */     String haid = request.getParameter("haid");
/* 170 */     String monitor = request.getParameter("monitor");
/* 171 */     String alertdetails = request.getParameter("alertdetails");
/* 172 */     String tab = "";
/* 173 */     String category = "";
/* 174 */     String source = "";
/* 175 */     String redirectto = "";
/* 176 */     if ((request.getParameter("tab") != null) && (!request.getParameter("tab").equals("null")))
/* 177 */       tab = request.getParameter("tab");
/* 178 */     if ((request.getParameter("category") != null) && (!request.getParameter("category").equals("null")))
/* 179 */       category = request.getParameter("category");
/* 180 */     if ((request.getParameter("source") != null) && (!request.getParameter("source").equals("null")))
/* 181 */       source = request.getParameter("source");
/* 182 */     if ((request.getParameter("redirectto") != null) && (!request.getParameter("redirectto").equals("null")))
/* 183 */       redirectto = request.getParameter("redirectto");
/* 184 */     if ((!isTokenValid(request)) && (fromCentral == null) && (adminServerRequest == null))
/*     */     {
/* 186 */       if ((haid == null) && (monitor == null))
/*     */       {
/* 188 */         AMLog.debug("FAULTD : both null");
/* 189 */         return mapping.findForward("viewAlarm");
/*     */       }
/* 191 */       if ((haid.equals("")) && (monitor.equals("")))
/*     */       {
/* 193 */         AMLog.debug("FAULTD : both empty");
/* 194 */         return mapping.findForward("viewAlarm");
/*     */       }
/* 196 */       if ((haid.equals("null")) && (monitor.equals("null")))
/*     */       {
/* 198 */         AMLog.debug("FAULTD : equals null");
/* 199 */         return mapping.findForward("viewAlarm");
/*     */       }
/*     */       
/*     */ 
/* 203 */       return mapping.findForward("viewAMAlarm");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 208 */     AMLog.debug("FAULTD : Reset Token");
/* 209 */     resetToken(request);
/*     */     
/* 211 */     int i = -1;
/* 212 */     String clearStatus = "";
/* 213 */     Hashtable failure = new Hashtable();
/* 214 */     ArrayList<String> success = new ArrayList();
/* 215 */     String isadminrequest = request.getParameter("requestFromAdmin");
/* 216 */     String statusUsername = request.getRemoteUser();
/* 217 */     if ("true".equalsIgnoreCase(isadminrequest)) {
/* 218 */       statusUsername = request.getParameter("adminUser");
/*     */     }
/*     */     try {
/* 221 */       if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")) || ((request.isUserInRole("OPERATOR")) && (DBUtil.getGlobalConfigValueasBoolean("allowClearAlarms"))) || ((Constants.isIt360) && (request.isUserInRole("OPERATOR"))) || ((fromCentral != null) && (fromCentral.equals("true"))) || ("true".equalsIgnoreCase(adminServerRequest)))
/*     */       {
/* 223 */         AMLog.debug("FAULTD : Authorized");
/* 224 */         StringTokenizer tokenize = new StringTokenizer(entity, ",");
/* 225 */         while (tokenize.hasMoreTokens())
/*     */         {
/* 227 */           String tempEntity = tokenize.nextToken();
/* 228 */           AMLog.debug("FAULTD Entities involved" + tempEntity);
/*     */           
/* 230 */           clearStatus = "" + FaultUtil.clearAlert(tempEntity, statusUsername);
/* 231 */           AMLog.debug("FAULTD Events clear" + clearStatus);
/* 232 */           if (!clearStatus.equals("true"))
/*     */           {
/* 234 */             failure.put(tempEntity, clearStatus);
/*     */           }
/*     */           else
/*     */           {
/* 238 */             success.add(tempEntity);
/* 239 */             String resourceId = entity.substring(0, entity.indexOf('_'));
/* 240 */             String resourceName = Constants.getResName(resourceId);
/*     */             
/* 242 */             AMFileDataCollector.deleteResourcefromCache(resourceId);
/*     */             
/* 244 */             if ((Constants.isIt360) && (SANUtil.isOpStorDevice(resourceName)))
/*     */             {
/* 246 */               String loggedInUser = EnterpriseUtil.getLoggedInUserName(request);
/* 247 */               SANUtil.updateOpStorActions("clearAlarm", resourceName, loggedInUser);
/*     */             }
/* 249 */             if (ExtConnectorUtil.isPushEnabled) {
/*     */               try
/*     */               {
/* 252 */                 OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 253 */                 opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_CLEARED");
/*     */               } catch (Exception ex) {
/* 255 */                 ex.printStackTrace();
/*     */ 
/*     */               }
/*     */               
/*     */ 
/*     */             }
/*     */             
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 274 */         AMLog.debug("FAULTD : UnAuthorized");
/*     */         
/* 276 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Sorry ! You are not authorized to perform this operation"));
/* 277 */         saveErrors(request, errors);
/* 278 */         return mapping.findForward("viewAlarm");
/*     */       }
/*     */     } catch (Exception e) {
/* 281 */       e.printStackTrace();
/*     */     }
/* 283 */     if (!failure.isEmpty())
/*     */     {
/* 285 */       request.setAttribute("success", success);
/* 286 */       request.setAttribute("failure", failure);
/* 287 */       return mapping.findForward("responsePage");
/*     */     }
/* 289 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("webclient.fault.alarm.clear.status.success"));
/* 290 */     saveMessages(request, messages);
/*     */     
/* 292 */     if ((alertdetails != null) && (!alertdetails.equals("null")))
/*     */     {
/* 294 */       return new ActionForward("AlarmDetails.do?method=traversePage&tab=tabOne&entity=" + entity + "&source=" + source + "&category=" + category + "&haid=" + haid + "redirectto=" + redirectto + "&monitor=", true);
/*     */     }
/* 296 */     if ((haid == null) && (monitor == null))
/*     */     {
/* 298 */       return mapping.findForward("viewAlarm");
/*     */     }
/* 300 */     if ((haid.equals("")) && (monitor.equals("")))
/*     */     {
/* 302 */       return mapping.findForward("viewAlarm");
/*     */     }
/*     */     
/* 305 */     if ((haid.equals("null")) && (monitor.equals("null")))
/*     */     {
/* 307 */       return mapping.findForward("viewAlarm");
/*     */     }
/*     */     
/*     */ 
/* 311 */     return mapping.findForward("viewAMAlarm");
/*     */   }
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
/*     */   public ActionForward deleteAlarm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 331 */     String userName = (String)request.getSession().getAttribute("userName");
/*     */     
/* 333 */     String entity = request.getParameter("selectedEntity");
/* 334 */     String viewId = request.getParameter("viewId");
/* 335 */     String haid = request.getParameter("haid");
/* 336 */     String monitor = request.getParameter("monitor");
/* 337 */     request.setAttribute("viewId", viewId);
/*     */     
/* 339 */     int i = -1;
/* 340 */     String deleteStatus = "";
/* 341 */     Hashtable failure = new Hashtable();
/* 342 */     ArrayList success = new ArrayList();
/*     */     
/* 344 */     if (GenericUtility.isAuthorized(userName, "Delete Alerts"))
/*     */     {
/* 346 */       StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*     */       
/* 348 */       while (tokenize.hasMoreTokens())
/*     */       {
/* 350 */         String tempEntity = tokenize.nextToken();
/* 351 */         deleteStatus = AlarmOperationsUtility.getInstance().deleteTheAlert(userName, tempEntity);
/* 352 */         FaultUtil.deleteAlertsForEntity(tempEntity);
/* 353 */         if (!deleteStatus.equals("true"))
/*     */         {
/* 355 */           failure.put(tempEntity, deleteStatus);
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 361 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt("delete from Event where ENTITY='" + tempEntity + "'");
/*     */           }
/*     */           catch (SQLException ue) {}
/*     */           
/*     */ 
/*     */ 
/* 367 */           success.add(tempEntity);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 373 */       request.setAttribute("unauthorized", "Sorry ! You are not authorized to perform this operation");
/* 374 */       return mapping.findForward("viewAlarm");
/*     */     }
/* 376 */     if (!failure.isEmpty())
/*     */     {
/* 378 */       request.setAttribute("success", success);
/* 379 */       request.setAttribute("failure", failure);
/* 380 */       return mapping.findForward("responsePage");
/*     */     }
/* 382 */     String operationStatus = NmsUtil.GetString("webclient.fault.alarm.delete.status.success");
/* 383 */     request.setAttribute("success", operationStatus);
/* 384 */     if ((haid == null) && (monitor == null))
/*     */     {
/* 386 */       return mapping.findForward("viewAlarm");
/*     */     }
/* 388 */     if ((haid.equals("null")) && (monitor.equals("null")))
/* 389 */       return mapping.findForward("viewAlarm");
/* 390 */     if ((haid.equals("")) && (monitor.equals(""))) {
/* 391 */       return mapping.findForward("viewAlarm");
/*     */     }
/*     */     
/*     */ 
/* 395 */     return mapping.findForward("viewAMAlarm");
/*     */   }
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
/*     */   public ActionForward pickUpAlarm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 417 */     ActionMessages messages = new ActionMessages();
/* 418 */     String userName = request.getRemoteUser();
/* 419 */     String adminUser = request.getParameter("actionFromadmin");
/* 420 */     String adminServerRequest = request.getParameter("requestFromAdmin");
/* 421 */     if ("loginuser".equals(userName))
/*     */     {
/* 423 */       userName = request.getParameter("uName");
/*     */     }
/*     */     
/* 426 */     if ("true".equalsIgnoreCase(adminUser)) {
/* 427 */       userName = "systemadmin_enterprise";
/*     */     }
/*     */     
/* 430 */     if ("true".equalsIgnoreCase(adminServerRequest)) {
/* 431 */       userName = request.getParameter("adminUser");
/*     */     }
/*     */     
/*     */ 
/* 435 */     String entity = request.getParameter("selectedEntity");
/* 436 */     String viewId = request.getParameter("viewId");
/* 437 */     String redirectto = request.getParameter("redirectto");
/* 438 */     request.setAttribute("viewId", viewId);
/*     */     
/* 440 */     int i = -1;
/* 441 */     String pickupStatus = "";
/* 442 */     Hashtable failure = new Hashtable();
/* 443 */     ArrayList success = new ArrayList();
/* 444 */     StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*     */     
/* 446 */     while (tokenize.hasMoreTokens())
/*     */     {
/* 448 */       String tempEntity = tokenize.nextToken();
/* 449 */       pickupStatus = AlarmOperationsUtility.getInstance().pickTheAlert(userName, tempEntity, true);
/*     */       
/* 451 */       if (!pickupStatus.equals("true"))
/*     */       {
/* 453 */         failure.put(tempEntity, pickupStatus);
/*     */       }
/*     */       else
/*     */       {
/* 457 */         success.add(tempEntity);
/* 458 */         if ((EnterpriseUtil.isManagedServer()) && (!"true".equalsIgnoreCase(adminServerRequest)) && (!"true".equalsIgnoreCase(adminUser))) {
/* 459 */           String alertSynchQuery = "update Alert set WHO = '" + userName + "' where ENTITY='" + tempEntity + "'";
/* 460 */           EnterpriseUtil.addUpdateQueryToFile(alertSynchQuery);
/* 461 */           AMLog.logSyncDebug("pick Up Alarm synched to Admin=" + alertSynchQuery);
/*     */         }
/* 463 */         if (ExtConnectorUtil.isPushEnabled) {
/*     */           try
/*     */           {
/* 466 */             OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 467 */             opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_PICKED", userName);
/*     */           } catch (Exception ex) {
/* 469 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 474 */     if (!failure.isEmpty())
/*     */     {
/* 476 */       request.setAttribute("success", success);
/* 477 */       request.setAttribute("failure", failure);
/* 478 */       return mapping.findForward("responsePage");
/*     */     }
/*     */     
/*     */ 
/* 482 */     if ("true".equals(request.getParameter("ajaxrequest"))) {
/*     */       try {
/* 484 */         response.setContentType("text/json;charset=UTF-8");
/* 485 */         JSONObject userDetails = new JSONObject();
/* 486 */         PrintWriter out = response.getWriter();
/* 487 */         userDetails.put("username", userName);
/* 488 */         String userid = (String)DBUtil.username_userid_mapping.get(userName);
/* 489 */         userDetails.put("imageLocation", DBUtil.getImageStatus(userName, userid));
/* 490 */         out.println(userDetails);
/* 491 */         out.flush();
/* 492 */         return null;
/*     */       } catch (Exception ex) {
/* 494 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 498 */     String operationStatus = NmsUtil.GetString("webclient.fault.alarm.pickup.status.success");
/* 499 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(operationStatus));
/* 500 */     saveMessages(request, messages);
/* 501 */     if ((redirectto != null) && (!redirectto.equalsIgnoreCase("null")))
/*     */     {
/* 503 */       String statusMessage = "";
/* 504 */       if (redirectto.indexOf("?") == -1)
/*     */       {
/* 506 */         statusMessage = "?technician=pickUpAlarm";
/*     */       }
/*     */       else
/*     */       {
/* 510 */         statusMessage = "&technician=pickUpAlarm";
/*     */       }
/* 512 */       return new ActionForward("Applications Manager", redirectto + statusMessage, true, true);
/*     */     }
/* 514 */     return mapping.findForward("viewAlarm");
/*     */   }
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
/*     */   public ActionForward unPickAlarm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 533 */     ActionMessages messages = new ActionMessages();
/* 534 */     String userName = request.getRemoteUser();
/* 535 */     String adminUser = request.getParameter("actionFromadmin");
/* 536 */     String adminServerRequest = request.getParameter("requestFromAdmin");
/* 537 */     if ("loginuser".equals(userName))
/*     */     {
/* 539 */       userName = request.getParameter("uName");
/*     */     }
/*     */     
/* 542 */     if ("true".equalsIgnoreCase(adminUser)) {
/* 543 */       userName = request.getParameter("uName");
/*     */     }
/*     */     
/* 546 */     if ("true".equalsIgnoreCase(adminServerRequest)) {
/* 547 */       userName = request.getParameter("adminUser");
/*     */     }
/*     */     
/*     */ 
/* 551 */     String entity = request.getParameter("selectedEntity");
/* 552 */     String viewId = request.getParameter("viewId");
/* 553 */     String redirectto = request.getParameter("redirectto");
/* 554 */     request.setAttribute("viewId", viewId);
/*     */     
/* 556 */     int i = -1;
/* 557 */     String unPickStatus = "";
/* 558 */     Hashtable failure = new Hashtable();
/* 559 */     ArrayList success = new ArrayList();
/*     */     
/* 561 */     StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*     */     
/* 563 */     while (tokenize.hasMoreTokens())
/*     */     {
/* 565 */       String tempEntity = tokenize.nextToken();
/* 566 */       unPickStatus = AlarmOperationsUtility.getInstance().unPickTheAlert(userName, tempEntity, true);
/*     */       
/* 568 */       if (!unPickStatus.equals("true"))
/*     */       {
/* 570 */         failure.put(tempEntity, unPickStatus);
/*     */       }
/*     */       else
/*     */       {
/* 574 */         success.add(tempEntity);
/* 575 */         if ((EnterpriseUtil.isManagedServer()) && (!"true".equalsIgnoreCase(adminServerRequest)) && (!"true".equalsIgnoreCase(adminUser))) {
/* 576 */           String alertSynchQuery = "update Alert set WHO = 'NULL' where ENTITY='" + tempEntity + "'";
/* 577 */           EnterpriseUtil.addUpdateQueryToFile(alertSynchQuery);
/* 578 */           AMLog.logSyncDebug("Unpick Alarm is synched to Admin=" + alertSynchQuery);
/*     */         }
/* 580 */         if (ExtConnectorUtil.isPushEnabled) {
/*     */           try
/*     */           {
/* 583 */             OPMAlarmOperationsHandler opmAlertHan = new OPMAlarmOperationsHandler();
/* 584 */             opmAlertHan.doAlertChangesInOPM(request, entity, "ALERT_UNPICKED");
/*     */           } catch (Exception ex) {
/* 586 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 592 */     if (!failure.isEmpty())
/*     */     {
/* 594 */       request.setAttribute("success", success);
/* 595 */       request.setAttribute("failure", failure);
/* 596 */       return mapping.findForward("responsePage");
/*     */     }
/*     */     
/* 599 */     if ("true".equals(request.getParameter("ajaxrequest"))) {
/*     */       try {
/* 601 */         response.setContentType("text/json;charset=UTF-8");
/* 602 */         JSONObject userDetails = new JSONObject();
/* 603 */         PrintWriter out = response.getWriter();
/* 604 */         userDetails.put("username", "None");
/* 605 */         userDetails.put("imageLocation", "/images/icon_alarm_user.png");
/* 606 */         out.println(userDetails);
/* 607 */         out.flush();
/* 608 */         return null;
/*     */       } catch (Exception ex) {
/* 610 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 614 */     String operationStatus = NmsUtil.GetString("webclient.fault.alarm.unpick.status.success");
/* 615 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(operationStatus));
/* 616 */     saveMessages(request, messages);
/* 617 */     if ((redirectto != null) && (!redirectto.equalsIgnoreCase("null")))
/*     */     {
/* 619 */       String statusMessage = "";
/* 620 */       if (redirectto.indexOf("?") == -1)
/*     */       {
/* 622 */         statusMessage = "?technician=unPickAlarm";
/*     */       }
/*     */       else
/*     */       {
/* 626 */         statusMessage = "&technician=unPickAlarm";
/*     */       }
/* 628 */       return new ActionForward("Applications Manager", redirectto + statusMessage, true, true);
/*     */     }
/* 630 */     return mapping.findForward("viewAlarm");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\webclient\fault\alarm\AlarmViewDispatchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */