/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.CustomExpressionUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.utils.client.BusinessHourAPIUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.htmlparser.util.Translate;
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
/*     */ public class BusinessHoursAction
/*     */   extends DispatchAction
/*     */ {
/*  82 */   private static Log log = LogFactory.getLog("WebClient");
/*     */   
/*  84 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  94 */     response.setContentType("text/html; charset=UTF-8");
/*  95 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  96 */     AMActionForm am = (AMActionForm)form;
/*  97 */     ActionMessages messages = new ActionMessages();
/*  98 */     ActionErrors errors = new ActionErrors();
/*  99 */     ResultSet rs = null;
/* 100 */     String error = null;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 105 */       String sname = request.getParameter("schedulename");
/* 106 */       String edit = request.getParameter("edit");
/* 107 */       if ((edit != null) && (edit.equals("false")))
/*     */       {
/* 109 */         String query = "SELECT ID FROM AM_BUSINESSHOURSDETAILS where NAME='" + sname + "'";
/* 110 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 111 */         if (rs.next())
/*     */         {
/* 113 */           error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { sname });
/* 114 */           PrintWriter pw = response.getWriter();
/* 115 */           pw.print(error);
/*     */         }
/* 117 */         rs.close();
/*     */       }
/*     */     }
/*     */     catch (Exception es)
/*     */     {
/* 122 */       es.printStackTrace();
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward removeBusinessDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 130 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 131 */     AMActionForm am = (AMActionForm)form;
/* 132 */     ActionMessages messages = new ActionMessages();
/* 133 */     ActionErrors errors = new ActionErrors();
/* 134 */     String businesshourIds = "";
/*     */     try
/*     */     {
/* 137 */       String[] sids = request.getParameterValues("scheduleids");
/* 138 */       for (int i = 0; i < sids.length; i++)
/*     */       {
/* 140 */         String sid = sids[i];
/* 141 */         businesshourIds = businesshourIds + sid + ",";
/* 142 */         deleteBusinessHours(sid);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 147 */       ex.printStackTrace();
/*     */     }
/* 149 */     if (EnterpriseUtil.isAdminServer()) {
/* 150 */       BusinessHourAPIUtil.deleteBusinessHourstoSynch(businesshourIds);
/*     */     }
/* 152 */     if ("true".equals(request.getParameter("adminAPIRequest"))) {
/* 153 */       return null;
/*     */     }
/* 155 */     showBusinessHours(mapping, form, request, response);
/* 156 */     return new ActionForward("/businessHours.do?method=showBusinessHours", true);
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
/*     */   public ActionForward updateGlobalSettingForAnomalyIntroTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 218 */       ex.printStackTrace();
/*     */     }
/* 220 */     return new ActionForward("/showTile.do?TileName=.ThresholdConf&haid=null&isanomaly=true");
/*     */   }
/*     */   
/*     */   public ActionForward newBusinessHours(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 224 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 225 */     AMActionForm am = (AMActionForm)form;
/* 226 */     ActionMessages messages = new ActionMessages();
/* 227 */     ActionErrors errors = new ActionErrors();
/*     */     try {
/* 229 */       String[] totaldays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
/* 230 */       am.setWorkingdays(totaldays);
/*     */     } catch (Exception ex) {
/* 232 */       ex.printStackTrace();
/*     */     }
/* 234 */     return new ActionForward("/jsp/BusinessHours.jsp");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward addBusinessDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 242 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 243 */     AMActionForm am = (AMActionForm)form;
/* 244 */     ActionMessages messages = new ActionMessages();
/* 245 */     ActionErrors errors = new ActionErrors();
/* 246 */     String name = am.getRulename();
/* 247 */     boolean isname = false;
/* 248 */     ResultSet rs1 = null;
/* 249 */     String error = null;
/*     */     
/*     */     try
/*     */     {
/* 253 */       String query = "SELECT ID FROM AM_BUSINESSHOURSDETAILS where NAME='" + name + "'";
/* 254 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 255 */       if (rs1.next())
/*     */       {
/* 257 */         error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { name });
/* 258 */         isname = true;
/*     */       }
/* 260 */       rs1.close();
/*     */ 
/*     */     }
/*     */     catch (Exception es)
/*     */     {
/* 265 */       es.printStackTrace();
/*     */     }
/* 267 */     if (isname)
/*     */     {
/* 269 */       request.setAttribute("message", error);
/* 270 */       String[] resourcestypes1 = am.getResourcestypes();
/* 271 */       am.setResourcestypes(resourcestypes1);
/* 272 */       return new ActionForward("/businessHours.do?method=newBusinessHours&edit=check");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 279 */       String description = am.getTaskDescription();
/* 280 */       String status = am.getStatusRule();
/* 281 */       String[] days = am.getWorkingdays();
/* 282 */       String monstarttime = am.getMondayStartHour();
/* 283 */       String monendtime = am.getMondayEndHour();
/* 284 */       String tuestarttime = am.getTuesdayStartHour();
/* 285 */       String tueendtime = am.getTuesdayEndHour();
/* 286 */       String wedstarttime = am.getWednesdayStartHour();
/* 287 */       String wedendtime = am.getWednesdayEndHour();
/* 288 */       String thustarttime = am.getThursdayStartHour();
/* 289 */       String thuendtime = am.getThursdayEndHour();
/* 290 */       String fristarttime = am.getFridayStartHour();
/* 291 */       String friendtime = am.getFridayEndHour();
/* 292 */       String satstarttime = am.getSaturdayStartHour();
/* 293 */       String satendtime = am.getSaturdayEndHour();
/* 294 */       String sunstarttime = am.getSundayStartHour();
/* 295 */       String sunendtime = am.getSundayEndHour();
/*     */       
/* 297 */       String monstartminute = am.getMondayStartMinute();
/* 298 */       String monendminute = am.getMondayEndMinute();
/* 299 */       String tuestartminute = am.getTuesdayStartMinute();
/* 300 */       String tueendminute = am.getTuesdayEndMinute();
/* 301 */       String wedstartminute = am.getWednesdayStartMinute();
/* 302 */       String wedendminute = am.getWednesdayEndMinute();
/* 303 */       String thustartminute = am.getThursdayStartMinute();
/* 304 */       String thuendminute = am.getThursdayEndMinute();
/* 305 */       String fristartminute = am.getFridayStartMinute();
/* 306 */       String friendminute = am.getFridayEndMinute();
/* 307 */       String satstartminute = am.getSaturdayStartMinute();
/* 308 */       String satendminute = am.getSaturdayEndMinute();
/* 309 */       String sunstartminute = am.getSundayStartMinute();
/* 310 */       String sunendminute = am.getSundayEndMinute();
/*     */       
/* 312 */       String selecteddays = Constants.getResourceIDWithComma(days);
/* 313 */       int id = DBQueryUtil.getIncrementedID("ID", "AM_BUSINESSHOURSDETAILS");
/*     */       
/* 315 */       if ((EnterpriseUtil.isAdminServer()) && (id < 10000)) {
/* 316 */         id = 10000;
/*     */       }
/*     */       
/* 319 */       if ((EnterpriseUtil.isManagedServer()) && (id < 10000000)) {
/* 320 */         int serverIndex = EnterpriseUtil.getManagedServerIndex();
/* 321 */         id = serverIndex * 10000000;
/*     */       }
/*     */       
/* 324 */       if ("true".equals(request.getParameter("adminAPIRequest"))) {
/*     */         try {
/* 326 */           id = Integer.parseInt(request.getParameter("businessid"));
/*     */         } catch (NumberFormatException num) {
/* 328 */           num.getStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 332 */       String firstquery = "INSERT INTO AM_BUSINESSHOURSDETAILS(ID,NAME,STATUS,SUNDAYSTARTTIME,SUNDAYENDTIME,MONDAYSTARTTIME,MONDAYENDTIME,TUESDAYSTARTTIME,TUESDAYENDTIME,WEDNESDAYSTARTTIME,WEDNESDAYENDTIME,THURSDAYSTARTTIME,THURSDAYENDTIME,FRIDAYSTARTTIME,FRIDAYENDTIME,SATURDAYSTARTTIME,SATURDAYENDTIME,SUNDAYSTARTMINUTE,SUNDAYENDMINUTE,MONDAYSTARTMINUTE,MONDAYENDMINUTE,TUESDAYSTARTMINUTE,TUESDAYENDMINUTE,WEDNESDAYSTARTMINUTE,WEDNESDAYENDMINUTE,THURSDAYSTARTMINUTE,THURSDAYENDMINUTE,FRIDAYSTARTMINUTE,FRIDAYENDMINUTE,SATURDAYSTARTMINUTE,SATURDAYENDMINUTE,SELECTEDDAYS,DESCRIPTION) VALUES (" + id + ",'" + name + "','" + status + "','" + sunstarttime + "','" + sunendtime + "','" + monstarttime + "','" + monendtime + "','" + tuestarttime + "','" + tueendtime + "','" + wedstarttime + "','" + wedendtime + "','" + thustarttime + "','" + thuendtime + "','" + fristarttime + "','" + friendtime + "','" + satstarttime + "','" + satendtime + "','" + sunstartminute + "','" + sunendminute + "','" + monstartminute + "','" + monendminute + "','" + tuestartminute + "','" + tueendminute + "','" + wedstartminute + "','" + wedendminute + "','" + thustartminute + "','" + thuendminute + "','" + fristartminute + "','" + friendminute + "','" + satstartminute + "','" + satendminute + "','" + selecteddays + "','" + description + "')";
/* 333 */       AMConnectionPool.executeUpdateStmt(firstquery);
/*     */       
/* 335 */       if (EnterpriseUtil.isAdminServer()) {
/* 336 */         BusinessHourAPIUtil.addBusinessHourtoSynch(am, id + "", false);
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 342 */       ex.printStackTrace();
/*     */     }
/* 344 */     request.setAttribute("tabtoselect", "3");
/* 345 */     showBusinessHours(mapping, form, request, response);
/* 346 */     String redirectUrl = request.getParameter("redirectPage");
/* 347 */     if ((redirectUrl != null) && (!redirectUrl.equals("")) && (!redirectUrl.equals("null")))
/*     */     {
/* 349 */       return new ActionForward(redirectUrl, true);
/*     */     }
/* 351 */     return new ActionForward("/businessHours.do?method=showBusinessHours", true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void deleteBusinessHours(String sid)
/*     */   {
/* 358 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/* 361 */       String query1 = "DELETE FROM AM_BUSINESSHOURSDETAILS WHERE ID=" + Integer.parseInt(sid);
/* 362 */       AMConnectionPool.executeUpdateStmt(query1);
/* 363 */       DBUtil.deleteTimeFromActionTimeMapper(sid);
/*     */     }
/*     */     catch (Exception ep)
/*     */     {
/* 367 */       ep.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward updateBusinessDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 377 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 378 */     AMActionForm am = (AMActionForm)form;
/* 379 */     ActionMessages messages = new ActionMessages();
/* 380 */     ActionErrors errors = new ActionErrors();
/* 381 */     String operation = request.getParameter("operation");
/* 382 */     boolean updateStatus = false;
/* 383 */     if (operation != null)
/*     */     {
/* 385 */       String updatedData = request.getParameter("updatedData");
/* 386 */       am.setBusinessHoursFromJSONString(Translate.decode(updatedData));
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 392 */       String sid = request.getParameter("sid");
/* 393 */       String getid = null;
/* 394 */       String error = null;
/* 395 */       String query = "SELECT ID FROM AM_BUSINESSHOURSDETAILS where NAME='" + am.getRulename() + "'";
/*     */       
/* 397 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/* 398 */       if (rs1.next())
/*     */       {
/* 400 */         getid = rs1.getString("ID");
/*     */       }
/*     */       
/* 403 */       rs1.close();
/* 404 */       if ((getid != null) && (!getid.equals(sid)))
/*     */       {
/* 406 */         error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { am.getTaskName() });
/* 407 */         request.setAttribute("message", error);
/* 408 */         return new ActionForward("/businessHours.do?method=editBusinessHours&sid=" + sid);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 415 */         String name = am.getRulename();
/* 416 */         String description = am.getTaskDescription();
/* 417 */         String status = am.getStatusRule();
/* 418 */         String[] days = am.getWorkingdays();
/* 419 */         String monstarttime = am.getMondayStartHour();
/* 420 */         String monendtime = am.getMondayEndHour();
/* 421 */         String tuestarttime = am.getTuesdayStartHour();
/* 422 */         String tueendtime = am.getTuesdayEndHour();
/* 423 */         String wedstarttime = am.getWednesdayStartHour();
/* 424 */         String wedendtime = am.getWednesdayEndHour();
/* 425 */         String thustarttime = am.getThursdayStartHour();
/* 426 */         String thuendtime = am.getThursdayEndHour();
/* 427 */         String fristarttime = am.getFridayStartHour();
/* 428 */         String friendtime = am.getFridayEndHour();
/* 429 */         String satstarttime = am.getSaturdayStartHour();
/* 430 */         String satendtime = am.getSaturdayEndHour();
/* 431 */         String sunstarttime = am.getSundayStartHour();
/* 432 */         String sunendtime = am.getSundayEndHour();
/*     */         
/* 434 */         String monstartminute = am.getMondayStartMinute();
/* 435 */         String monendminute = am.getMondayEndMinute();
/* 436 */         String tuestartminute = am.getTuesdayStartMinute();
/* 437 */         String tueendminute = am.getTuesdayEndMinute();
/* 438 */         String wedstartminute = am.getWednesdayStartMinute();
/* 439 */         String wedendminute = am.getWednesdayEndMinute();
/* 440 */         String thustartminute = am.getThursdayStartMinute();
/* 441 */         String thuendminute = am.getThursdayEndMinute();
/* 442 */         String fristartminute = am.getFridayStartMinute();
/* 443 */         String friendminute = am.getFridayEndMinute();
/* 444 */         String satstartminute = am.getSaturdayStartMinute();
/* 445 */         String satendminute = am.getSaturdayEndMinute();
/* 446 */         String sunstartminute = am.getSundayStartMinute();
/* 447 */         String sunendminute = am.getSundayEndMinute();
/*     */         
/*     */ 
/* 450 */         String selecteddays = Constants.getResourceIDWithComma(days);
/*     */         
/* 452 */         String updateQuery = "UPDATE AM_BUSINESSHOURSDETAILS SET NAME='" + name + "' ,SUNDAYSTARTTIME='" + sunstarttime + "',SUNDAYENDTIME='" + sunendtime + "',MONDAYSTARTTIME='" + monstarttime + "',MONDAYENDTIME='" + monendtime + "',TUESDAYSTARTTIME='" + tuestarttime + "',TUESDAYENDTIME='" + tueendtime + "',WEDNESDAYSTARTTIME='" + wedstarttime + "',WEDNESDAYENDTIME='" + wedendtime + "',THURSDAYSTARTTIME='" + thustarttime + "',THURSDAYENDTIME='" + thuendtime + "',FRIDAYSTARTTIME='" + fristarttime + "',FRIDAYENDTIME='" + friendtime + "',SATURDAYSTARTTIME='" + satstarttime + "',SATURDAYENDTIME='" + satendtime + "',SUNDAYSTARTMINUTE='" + sunstartminute + "',SUNDAYENDMINUTE='" + sunendminute + "',MONDAYSTARTMINUTE='" + monstartminute + "',MONDAYENDMINUTE='" + monendminute + "',TUESDAYSTARTMINUTE='" + tuestartminute + "',TUESDAYENDMINUTE='" + tueendminute + "',WEDNESDAYSTARTMINUTE='" + wedstartminute + "',WEDNESDAYENDMINUTE='" + wedendminute + "',THURSDAYSTARTMINUTE='" + thustartminute + "',THURSDAYENDMINUTE='" + thuendminute + "',FRIDAYSTARTMINUTE='" + fristartminute + "',FRIDAYENDMINUTE='" + friendminute + "',SATURDAYSTARTMINUTE='" + satstartminute + "',SATURDAYENDMINUTE='" + satendminute + "',SELECTEDDAYS='" + selecteddays + "',DESCRIPTION='" + description + "' where ID='" + sid + "'";
/*     */         
/* 454 */         AMConnectionPool.executeUpdateStmt(updateQuery);
/* 455 */         updateStatus = true;
/* 456 */         if (EnterpriseUtil.isAdminServer()) {
/* 457 */           BusinessHourAPIUtil.addBusinessHourtoSynch(am, getid, true);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 462 */         ex.printStackTrace();
/* 463 */         updateStatus = false;
/*     */       }
/* 465 */       if (operation != null)
/*     */       {
/* 467 */         PrintWriter out = response.getWriter();
/* 468 */         response.setContentType("text/json");
/* 469 */         response.setCharacterEncoding("UTF-8");
/*     */         try
/*     */         {
/* 472 */           JSONObject responseObj = new JSONObject();
/* 473 */           responseObj.put("result", updateStatus);
/* 474 */           out.print(responseObj);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 478 */           e.printStackTrace();
/*     */         }
/* 480 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 484 */       return new ActionForward("/businessHours.do?method=showBusinessHours", true);
/*     */ 
/*     */     }
/*     */     catch (Exception es)
/*     */     {
/* 489 */       es.printStackTrace();
/*     */     }
/* 491 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward editBusinessHours(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 499 */     response.setContentType("text/html; charset=UTF-8");
/* 500 */     String operation = request.getParameter("operation");
/*     */     
/* 502 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 503 */     AMActionForm am = (AMActionForm)form;
/* 504 */     ActionMessages messages = new ActionMessages();
/* 505 */     ActionErrors errors = new ActionErrors();
/* 506 */     ResultSet rs = null;
/* 507 */     String sid = request.getParameter("sid");
/* 508 */     String actionID = request.getParameter("actionId");
/* 509 */     if ((actionID != null) && (!actionID.equals("")))
/*     */     {
/* 511 */       sid = DBUtil.getBusinessHourIDForActionID(actionID);
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 516 */       int businessID = Integer.parseInt(sid);
/*     */       
/* 518 */       if ((EnterpriseUtil.isManagedServer()) && (businessID >= 10000) && (businessID < 10000000)) {
/* 519 */         request.setAttribute("adminBusinessHour", Boolean.valueOf(true));
/*     */       }
/*     */       
/* 522 */       String sname = null;
/* 523 */       String status = null;
/* 524 */       String desc = null;
/* 525 */       String monst = null;
/* 526 */       String moned = null;
/* 527 */       String tuest = null;
/* 528 */       String tueed = null;
/* 529 */       String wedst = null;
/* 530 */       String weded = null;
/* 531 */       String thust = null;
/* 532 */       String thued = null;
/* 533 */       String frist = null;
/* 534 */       String fried = null;
/* 535 */       String satst = null;
/* 536 */       String sated = null;
/* 537 */       String sunst = null;
/* 538 */       String suned = null;
/*     */       
/* 540 */       String monstmin = null;
/* 541 */       String monedmin = null;
/* 542 */       String tuestmin = null;
/* 543 */       String tueedmin = null;
/* 544 */       String wedstmin = null;
/* 545 */       String wededmin = null;
/* 546 */       String thustmin = null;
/* 547 */       String thuedmin = null;
/* 548 */       String fristmin = null;
/* 549 */       String friedmin = null;
/* 550 */       String satstmin = null;
/* 551 */       String satedmin = null;
/* 552 */       String sunstmin = null;
/* 553 */       String sunedmin = null;
/*     */       
/* 555 */       String selecteddays = null;
/* 556 */       String editquery1 = "SELECT * from AM_BUSINESSHOURSDETAILS where ID=" + Integer.parseInt(sid);
/* 557 */       rs = AMConnectionPool.executeQueryStmt(editquery1);
/* 558 */       if (rs.next())
/*     */       {
/* 560 */         sname = rs.getString("NAME");
/* 561 */         status = rs.getString("STATUS");
/* 562 */         desc = rs.getString("DESCRIPTION");
/* 563 */         monst = rs.getString("MONDAYSTARTTIME");
/* 564 */         if (Integer.parseInt(monst) < 10) {
/* 565 */           monst = "0" + monst;
/*     */         }
/* 567 */         moned = rs.getString("MONDAYENDTIME");
/* 568 */         if (Integer.parseInt(moned) < 10) {
/* 569 */           moned = "0" + moned;
/*     */         }
/* 571 */         tuest = rs.getString("TUESDAYSTARTTIME");
/* 572 */         if (Integer.parseInt(tuest) < 10) {
/* 573 */           tuest = "0" + tuest;
/*     */         }
/* 575 */         tueed = rs.getString("TUESDAYENDTIME");
/* 576 */         if (Integer.parseInt(tueed) < 10) {
/* 577 */           tueed = "0" + tueed;
/*     */         }
/* 579 */         wedst = rs.getString("WEDNESDAYSTARTTIME");
/* 580 */         if (Integer.parseInt(wedst) < 10) {
/* 581 */           wedst = "0" + wedst;
/*     */         }
/* 583 */         weded = rs.getString("WEDNESDAYENDTIME");
/* 584 */         if (Integer.parseInt(weded) < 10) {
/* 585 */           weded = "0" + weded;
/*     */         }
/* 587 */         thust = rs.getString("THURSDAYSTARTTIME");
/* 588 */         if (Integer.parseInt(thust) < 10) {
/* 589 */           thust = "0" + thust;
/*     */         }
/* 591 */         thued = rs.getString("THURSDAYENDTIME");
/* 592 */         if (Integer.parseInt(thued) < 10) {
/* 593 */           thued = "0" + thued;
/*     */         }
/* 595 */         frist = rs.getString("FRIDAYSTARTTIME");
/* 596 */         if (Integer.parseInt(frist) < 10) {
/* 597 */           frist = "0" + frist;
/*     */         }
/* 599 */         fried = rs.getString("FRIDAYENDTIME");
/* 600 */         if (Integer.parseInt(fried) < 10) {
/* 601 */           fried = "0" + fried;
/*     */         }
/* 603 */         satst = rs.getString("SATURDAYSTARTTIME");
/* 604 */         if (Integer.parseInt(satst) < 10) {
/* 605 */           satst = "0" + satst;
/*     */         }
/* 607 */         sated = rs.getString("SATURDAYENDTIME");
/* 608 */         if (Integer.parseInt(sated) < 10) {
/* 609 */           sated = "0" + sated;
/*     */         }
/* 611 */         sunst = rs.getString("SUNDAYSTARTTIME");
/* 612 */         if (Integer.parseInt(sunst) < 10) {
/* 613 */           sunst = "0" + sunst;
/*     */         }
/* 615 */         suned = rs.getString("SUNDAYENDTIME");
/* 616 */         if (Integer.parseInt(suned) < 10) {
/* 617 */           suned = "0" + suned;
/*     */         }
/* 619 */         monstmin = rs.getString("MONDAYSTARTMINUTE");
/*     */         
/* 621 */         monedmin = rs.getString("MONDAYENDMINUTE");
/*     */         
/* 623 */         tuestmin = rs.getString("TUESDAYSTARTMINUTE");
/*     */         
/* 625 */         tueedmin = rs.getString("TUESDAYENDMINUTE");
/*     */         
/* 627 */         wedstmin = rs.getString("WEDNESDAYSTARTMINUTE");
/*     */         
/* 629 */         wededmin = rs.getString("WEDNESDAYENDMINUTE");
/*     */         
/* 631 */         thustmin = rs.getString("THURSDAYSTARTMINUTE");
/*     */         
/* 633 */         thuedmin = rs.getString("THURSDAYENDMINUTE");
/*     */         
/* 635 */         fristmin = rs.getString("FRIDAYSTARTMINUTE");
/*     */         
/* 637 */         friedmin = rs.getString("FRIDAYENDMINUTE");
/*     */         
/* 639 */         satstmin = rs.getString("SATURDAYSTARTMINUTE");
/*     */         
/* 641 */         satedmin = rs.getString("SATURDAYENDMINUTE");
/*     */         
/* 643 */         sunstmin = rs.getString("SUNDAYSTARTMINUTE");
/*     */         
/* 645 */         sunedmin = rs.getString("SUNDAYENDMINUTE");
/*     */         
/* 647 */         selecteddays = rs.getString("SELECTEDDAYS");
/*     */       }
/*     */       
/* 650 */       rs.close();
/* 651 */       if ((operation != null) && (operation.equals("ajaxOperation")))
/*     */       {
/* 653 */         request.setAttribute("operation", operation);
/* 654 */         PrintWriter out = response.getWriter();
/* 655 */         response.setContentType("text/json");
/* 656 */         response.setCharacterEncoding("UTF-8");
/*     */         try
/*     */         {
/* 659 */           JSONObject responseObj = new JSONObject();
/* 660 */           responseObj.put("selectedDays", selecteddays);
/* 661 */           responseObj.put("mondayStartHour", monst);
/* 662 */           responseObj.put("mondayStartMinute", monstmin);
/* 663 */           responseObj.put("mondayEndHour", moned);
/* 664 */           responseObj.put("mondayEndMinute", monedmin);
/* 665 */           responseObj.put("tuesdayStartHour", tuest);
/* 666 */           responseObj.put("tuesdayStartMinute", tuestmin);
/* 667 */           responseObj.put("tuesdayEndHour", tueed);
/* 668 */           responseObj.put("tuesdayEndMinute", tueedmin);
/* 669 */           responseObj.put("wednesdayStartHour", wedst);
/* 670 */           responseObj.put("wednesdayStartMinute", wedstmin);
/* 671 */           responseObj.put("wednesdayEndHour", weded);
/* 672 */           responseObj.put("wednesdayEndMinute", wededmin);
/* 673 */           responseObj.put("thursdayStartHour", thust);
/* 674 */           responseObj.put("thursdayStartMinute", thustmin);
/* 675 */           responseObj.put("thursdayEndHour", thued);
/* 676 */           responseObj.put("thursdayEndMinute", thuedmin);
/* 677 */           responseObj.put("fridayStartHour", frist);
/* 678 */           responseObj.put("fridayStartMinute", fristmin);
/* 679 */           responseObj.put("fridayEndHour", fried);
/* 680 */           responseObj.put("fridayEndMinute", friedmin);
/* 681 */           responseObj.put("saturdayStartHour", satst);
/* 682 */           responseObj.put("saturdayStartMinute", satstmin);
/* 683 */           responseObj.put("saturdayEndHour", sated);
/* 684 */           responseObj.put("saturdayEndMinute", satedmin);
/* 685 */           responseObj.put("sundayStartHour", sunst);
/* 686 */           responseObj.put("sundayStartMinute", sunstmin);
/* 687 */           responseObj.put("sundayEndHour", suned);
/* 688 */           responseObj.put("sundayEndMinute", sunedmin);
/* 689 */           out.print(responseObj);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 693 */           e.printStackTrace();
/*     */         }
/* 695 */         return null;
/*     */       }
/*     */       
/* 698 */       String[] totaldays = selecteddays.split(",");
/* 699 */       am.setRulename(sname);
/* 700 */       am.setTaskDescription(desc);
/* 701 */       am.setStatusRule(status);
/*     */       
/* 703 */       am.setMondayStartHour(monst);
/* 704 */       am.setMondayEndHour(moned);
/* 705 */       am.setTuesdayStartHour(tuest);
/* 706 */       am.setTuesdayEndHour(tueed);
/* 707 */       am.setWednesdayStartHour(wedst);
/* 708 */       am.setWednesdayEndHour(weded);
/* 709 */       am.setThursdayStartHour(thust);
/* 710 */       am.setThursdayEndHour(thued);
/* 711 */       am.setFridayStartHour(frist);
/* 712 */       am.setFridayEndHour(fried);
/* 713 */       am.setSaturdayStartHour(satst);
/* 714 */       am.setSaturdayEndHour(sated);
/* 715 */       am.setSundayStartHour(sunst);
/* 716 */       am.setSundayEndHour(suned);
/*     */       
/* 718 */       am.setMondayStartMinute(monstmin);
/* 719 */       am.setMondayEndMinute(monedmin);
/* 720 */       am.setTuesdayStartMinute(tuestmin);
/* 721 */       am.setTuesdayEndMinute(tueedmin);
/* 722 */       am.setWednesdayStartMinute(wedstmin);
/* 723 */       am.setWednesdayEndMinute(wededmin);
/* 724 */       am.setThursdayStartMinute(thustmin);
/* 725 */       am.setThursdayEndMinute(thuedmin);
/* 726 */       am.setFridayStartMinute(fristmin);
/* 727 */       am.setFridayEndMinute(friedmin);
/* 728 */       am.setSaturdayStartMinute(satstmin);
/* 729 */       am.setSaturdayEndMinute(satedmin);
/* 730 */       am.setSundayStartMinute(sunstmin);
/* 731 */       am.setSundayEndMinute(sunedmin);
/*     */       
/*     */ 
/* 734 */       am.setWorkingdays(totaldays);
/*     */       
/* 736 */       request.setAttribute("editschedule", "edit");
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 740 */       ex.printStackTrace();
/*     */     }
/* 742 */     if ((operation != null) && (operation.equals("showBusinessHourDetails")))
/*     */     {
/* 744 */       return mapping.findForward("detailsPage");
/*     */     }
/* 746 */     return new ActionForward("/jsp/BusinessHours.jsp?edit=true&sid=" + sid);
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showBusinessHours(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 753 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 754 */     AMActionForm am = (AMActionForm)form;
/* 755 */     ActionMessages messages = new ActionMessages();
/* 756 */     ActionErrors errors = new ActionErrors();
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 762 */       String query = "SELECT ID,NAME,STATUS,DESCRIPTION FROM AM_BUSINESSHOURSDETAILS ORDER BY NAME";
/* 763 */       AMLog.debug("Show Business Reports query : " + query);
/* 764 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 765 */       ArrayList row = new ArrayList();
/* 766 */       while (rs.next())
/*     */       {
/* 768 */         ArrayList data = new ArrayList();
/* 769 */         String id = rs.getString("ID");
/* 770 */         String name = rs.getString("NAME");
/* 771 */         String status = rs.getString("STATUS");
/* 772 */         String desc = rs.getString("DESCRIPTION");
/*     */         
/* 774 */         data.add(id);
/* 775 */         data.add(name);
/* 776 */         data.add(status);
/* 777 */         data.add(desc);
/*     */         
/*     */ 
/* 780 */         int businessID = rs.getInt("ID");
/* 781 */         if ((EnterpriseUtil.isManagedServer()) && (businessID >= 10000) && (businessID < 10000000)) {
/* 782 */           data.add(Boolean.valueOf(true));
/*     */         } else {
/* 784 */           data.add(Boolean.valueOf(false));
/*     */         }
/* 786 */         row.add(data);
/*     */       }
/* 788 */       rs.close();
/* 789 */       request.setAttribute("data", row);
/* 790 */       request.setAttribute("tabtoselect", "3");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 794 */       e.printStackTrace();
/*     */     }
/* 796 */     return new ActionForward("/jsp/ShowBusinessHoursDetails.jsp");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\BusinessHoursAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */