/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.struts.actions.BusinessHoursAction;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BusinessHourAPIUtil
/*      */ {
/*      */   public static String createBusinessHour(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/*   29 */     outputString = null;
/*      */     try {
/*   31 */       if (isJsonFormat) {
/*   32 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*   34 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*   36 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*   37 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*   40 */       AMActionForm form = new AMActionForm();
/*   41 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*   42 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/*   44 */       if (request.getParameter("name") != null) {
/*   45 */         if (!DBQueryUtil.checkForDuplicateEntry("AM_BUSINESSHOURSDETAILS", "NAME", request.getParameter("name"), "")) {
/*   46 */           form.setRulename(request.getParameter("name"));
/*      */         } else {
/*   48 */           return APIUtilities.duplicateNameResponse(request, response, "BusinessHour : create", "name");
/*      */         }
/*      */       } else {
/*   51 */         return APIUtilities.emptyParameterResponse(request, response, "BusinessHour : create", "name");
/*      */       }
/*      */       
/*   54 */       if (request.getParameter("description") != null) {
/*   55 */         form.setTaskDescription(request.getParameter("description"));
/*      */       }
/*      */       
/*   58 */       if (request.getParameter("workingdays") != null) {
/*   59 */         String[] workingdays = request.getParameter("workingdays").split(",");
/*   60 */         ArrayList<String> days = new ArrayList();
/*   61 */         for (String day : workingdays) {
/*   62 */           if (!"".equals(day.trim())) {
/*   63 */             String value = businessdayValidation(day);
/*   64 */             if ("week".equals(value)) {
/*   65 */               AMLog.debug("REST API : Businesshour create : workingdays should be a day in the week");
/*   66 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.workingday.wrong.text"), "4102");
/*      */             } else {
/*   68 */               days.add(day);
/*      */             }
/*      */           }
/*      */         }
/*   72 */         if (days.size() > 0) {
/*   73 */           form.setWorkingdays((String[])days.toArray(new String[days.size()]));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*   78 */       if (request.getParameter("mondaystarthour") != null) {
/*   79 */         if (Constants.isIntegerNumber(request.getParameter("mondaystarthour"))) {
/*   80 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("mondaystarthour")), true)) {
/*   81 */             form.setMondayStartHour(request.getParameter("mondaystarthour"));
/*      */           } else {
/*   83 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "mondaystarthour");
/*      */           }
/*      */         } else {
/*   86 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "mondaystarthour");
/*      */         }
/*      */       }
/*      */       
/*   90 */       if (request.getParameter("mondayendhour") != null) {
/*   91 */         if (Constants.isIntegerNumber(request.getParameter("mondayendhour"))) {
/*   92 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("mondayendhour")), true)) {
/*   93 */             form.setMondayEndHour(request.getParameter("mondayendhour"));
/*      */           } else {
/*   95 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "mondayendhour");
/*      */           }
/*      */         } else {
/*   98 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "mondayendhour");
/*      */         }
/*      */       }
/*      */       
/*  102 */       if (request.getParameter("mondaystartminute") != null) {
/*  103 */         if (Constants.isIntegerNumber(request.getParameter("mondaystartminute"))) {
/*  104 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("mondaystartminute")))) {
/*  105 */             form.setMondayStartMinute(request.getParameter("mondaystartminute"));
/*      */           } else {
/*  107 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "mondaystartminute");
/*      */           }
/*      */         } else {
/*  110 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "mondaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  114 */       if (request.getParameter("mondayendminute") != null) {
/*  115 */         if (Constants.isIntegerNumber(request.getParameter("mondayendminute"))) {
/*  116 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("mondayendminute")))) {
/*  117 */             form.setMondayEndMinute(request.getParameter("mondayendminute"));
/*      */           } else {
/*  119 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "mondayendminute");
/*      */           }
/*      */         } else {
/*  122 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "mondayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  127 */       if (request.getParameter("tuesdaystarthour") != null) {
/*  128 */         if (Constants.isIntegerNumber(request.getParameter("tuesdaystarthour"))) {
/*  129 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdaystarthour")), true)) {
/*  130 */             form.setTuesdayStartHour(request.getParameter("tuesdaystarthour"));
/*      */           } else {
/*  132 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "tuesdaystarthour");
/*      */           }
/*      */         } else {
/*  135 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "tuesdaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  139 */       if (request.getParameter("tuesdayendhour") != null) {
/*  140 */         if (Constants.isIntegerNumber(request.getParameter("tuesdayendhour"))) {
/*  141 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdayendhour")), true)) {
/*  142 */             form.setTuesdayEndHour(request.getParameter("tuesdayendhour"));
/*      */           } else {
/*  144 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "tuesdayendhour");
/*      */           }
/*      */         } else {
/*  147 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "tuesdayendhour");
/*      */         }
/*      */       }
/*      */       
/*  151 */       if (request.getParameter("tuesdaystartminute") != null) {
/*  152 */         if (Constants.isIntegerNumber(request.getParameter("tuesdaystartminute"))) {
/*  153 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdaystartminute")))) {
/*  154 */             form.setTuesdayStartMinute(request.getParameter("tuesdaystartminute"));
/*      */           } else {
/*  156 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "tuesdaystartminute");
/*      */           }
/*      */         } else {
/*  159 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "tuesdaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  163 */       if (request.getParameter("tuesdayendminute") != null) {
/*  164 */         if (Constants.isIntegerNumber(request.getParameter("tuesdayendminute"))) {
/*  165 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdayendminute")))) {
/*  166 */             form.setTuesdayEndMinute(request.getParameter("tuesdayendminute"));
/*      */           } else {
/*  168 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "tuesdayendminute");
/*      */           }
/*      */         } else {
/*  171 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "tuesdayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  176 */       if (request.getParameter("wednesdaystarthour") != null) {
/*  177 */         if (Constants.isIntegerNumber(request.getParameter("wednesdaystarthour"))) {
/*  178 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdaystarthour")), true)) {
/*  179 */             form.setWednesdayStartHour(request.getParameter("wednesdaystarthour"));
/*      */           } else {
/*  181 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "wednesdaystarthour");
/*      */           }
/*      */         } else {
/*  184 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "wednesdaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  188 */       if (request.getParameter("wednesdayendhour") != null) {
/*  189 */         if (Constants.isIntegerNumber(request.getParameter("wednesdayendhour"))) {
/*  190 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdayendhour")), true)) {
/*  191 */             form.setWednesdayEndHour(request.getParameter("wednesdayendhour"));
/*      */           } else {
/*  193 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "wednesdayendhour");
/*      */           }
/*      */         } else {
/*  196 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "wednesdayendhour");
/*      */         }
/*      */       }
/*      */       
/*  200 */       if (request.getParameter("wednesdaystartminute") != null) {
/*  201 */         if (Constants.isIntegerNumber(request.getParameter("wednesdaystartminute"))) {
/*  202 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdaystartminute")))) {
/*  203 */             form.setWednesdayStartMinute(request.getParameter("wednesdaystartminute"));
/*      */           } else {
/*  205 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "wednesdaystartminute");
/*      */           }
/*      */         } else {
/*  208 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "wednesdaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  212 */       if (request.getParameter("wednesdayendminute") != null) {
/*  213 */         if (Constants.isIntegerNumber(request.getParameter("wednesdayendminute"))) {
/*  214 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdayendminute")))) {
/*  215 */             form.setWednesdayEndMinute(request.getParameter("wednesdayendminute"));
/*      */           } else {
/*  217 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "wednesdayendminute");
/*      */           }
/*      */         } else {
/*  220 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "wednesdayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  225 */       if (request.getParameter("thursdaystarthour") != null) {
/*  226 */         if (Constants.isIntegerNumber(request.getParameter("thursdaystarthour"))) {
/*  227 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdaystarthour")), true)) {
/*  228 */             form.setThursdayStartHour(request.getParameter("thursdaystarthour"));
/*      */           } else {
/*  230 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "thursdaystarthour");
/*      */           }
/*      */         } else {
/*  233 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "thursdaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  237 */       if (request.getParameter("thursdayendhour") != null) {
/*  238 */         if (Constants.isIntegerNumber(request.getParameter("thursdayendhour"))) {
/*  239 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdayendhour")), true)) {
/*  240 */             form.setThursdayEndHour(request.getParameter("thursdayendhour"));
/*      */           } else {
/*  242 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "thursdayendhour");
/*      */           }
/*      */         } else {
/*  245 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "thursdayendhour");
/*      */         }
/*      */       }
/*      */       
/*  249 */       if (request.getParameter("thursdaystartminute") != null) {
/*  250 */         if (Constants.isIntegerNumber(request.getParameter("thursdaystartminute"))) {
/*  251 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdaystartminute")))) {
/*  252 */             form.setThursdayStartMinute(request.getParameter("thursdaystartminute"));
/*      */           } else {
/*  254 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "thursdaystartminute");
/*      */           }
/*      */         } else {
/*  257 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "thursdaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  261 */       if (request.getParameter("thursdayendminute") != null) {
/*  262 */         if (Constants.isIntegerNumber(request.getParameter("thursdayendminute"))) {
/*  263 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdayendminute")))) {
/*  264 */             form.setThursdayEndMinute(request.getParameter("thursdayendminute"));
/*      */           } else {
/*  266 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "thursdayendminute");
/*      */           }
/*      */         } else {
/*  269 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "thursdayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  274 */       if (request.getParameter("fridaystarthour") != null) {
/*  275 */         if (Constants.isIntegerNumber(request.getParameter("fridaystarthour"))) {
/*  276 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("fridaystarthour")), true)) {
/*  277 */             form.setFridayStartHour(request.getParameter("fridaystarthour"));
/*      */           } else {
/*  279 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "fridaystarthour");
/*      */           }
/*      */         } else {
/*  282 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "fridaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  286 */       if (request.getParameter("fridayendhour") != null) {
/*  287 */         if (Constants.isIntegerNumber(request.getParameter("fridayendhour"))) {
/*  288 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("fridayendhour")), true)) {
/*  289 */             form.setFridayEndHour(request.getParameter("fridayendhour"));
/*      */           } else {
/*  291 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "fridayendhour");
/*      */           }
/*      */         } else {
/*  294 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "fridayendhour");
/*      */         }
/*      */       }
/*      */       
/*  298 */       if (request.getParameter("fridaystartminute") != null) {
/*  299 */         if (Constants.isIntegerNumber(request.getParameter("fridaystartminute"))) {
/*  300 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("fridaystartminute")))) {
/*  301 */             form.setFridayStartMinute(request.getParameter("fridaystartminute"));
/*      */           } else {
/*  303 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "fridaystartminute");
/*      */           }
/*      */         } else {
/*  306 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "fridaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  310 */       if (request.getParameter("fridayendminute") != null) {
/*  311 */         if (Constants.isIntegerNumber(request.getParameter("fridayendminute"))) {
/*  312 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("fridayendminute")))) {
/*  313 */             form.setFridayEndMinute(request.getParameter("fridayendminute"));
/*      */           } else {
/*  315 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "fridayendminute");
/*      */           }
/*      */         } else {
/*  318 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "fridayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  323 */       if (request.getParameter("saturdaystarthour") != null) {
/*  324 */         if (Constants.isIntegerNumber(request.getParameter("saturdaystarthour"))) {
/*  325 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdaystarthour")), true)) {
/*  326 */             form.setSaturdayStartHour(request.getParameter("saturdaystarthour"));
/*      */           } else {
/*  328 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "saturdaystarthour");
/*      */           }
/*      */         } else {
/*  331 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "saturdaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  335 */       if (request.getParameter("saturdayendhour") != null) {
/*  336 */         if (Constants.isIntegerNumber(request.getParameter("saturdayendhour"))) {
/*  337 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdayendhour")), true)) {
/*  338 */             form.setSaturdayEndHour(request.getParameter("saturdayendhour"));
/*      */           } else {
/*  340 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "saturdayendhour");
/*      */           }
/*      */         } else {
/*  343 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "saturdayendhour");
/*      */         }
/*      */       }
/*      */       
/*  347 */       if (request.getParameter("saturdaystartminute") != null) {
/*  348 */         if (Constants.isIntegerNumber(request.getParameter("saturdaystartminute"))) {
/*  349 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdaystartminute")))) {
/*  350 */             form.setSaturdayStartMinute(request.getParameter("saturdaystartminute"));
/*      */           } else {
/*  352 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "saturdaystartminute");
/*      */           }
/*      */         } else {
/*  355 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "saturdaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  359 */       if (request.getParameter("saturdayendminute") != null) {
/*  360 */         if (Constants.isIntegerNumber(request.getParameter("saturdayendminute"))) {
/*  361 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdayendminute")))) {
/*  362 */             form.setSaturdayEndMinute(request.getParameter("saturdaystartminute"));
/*      */           } else {
/*  364 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "saturdayendminute");
/*      */           }
/*      */         } else {
/*  367 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "saturdayendminute");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  372 */       if (request.getParameter("sundaystarthour") != null) {
/*  373 */         if (Constants.isIntegerNumber(request.getParameter("sundaystarthour"))) {
/*  374 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("sundaystarthour")), true)) {
/*  375 */             form.setSundayStartHour(request.getParameter("sundaystarthour"));
/*      */           } else {
/*  377 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "sundaystarthour");
/*      */           }
/*      */         } else {
/*  380 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "sundaystarthour");
/*      */         }
/*      */       }
/*      */       
/*  384 */       if (request.getParameter("sundayendhour") != null) {
/*  385 */         if (Constants.isIntegerNumber(request.getParameter("sundayendhour"))) {
/*  386 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("sundayendhour")), true)) {
/*  387 */             form.setSundayEndHour(request.getParameter("sundayendhour"));
/*      */           } else {
/*  389 */             return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour create ", "sundayendhour");
/*      */           }
/*      */         } else {
/*  392 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "sundayendhour");
/*      */         }
/*      */       }
/*      */       
/*  396 */       if (request.getParameter("sundaystartminute") != null) {
/*  397 */         if (Constants.isIntegerNumber(request.getParameter("sundaystartminute"))) {
/*  398 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("sundaystartminute")))) {
/*  399 */             form.setSundayStartMinute(request.getParameter("sundaystartminute"));
/*      */           } else {
/*  401 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "sundaystartminute");
/*      */           }
/*      */         } else {
/*  404 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "sundaystartminute");
/*      */         }
/*      */       }
/*      */       
/*  408 */       if (request.getParameter("sundayendminute") != null) {
/*  409 */         if (Constants.isIntegerNumber(request.getParameter("sundayendminute"))) {
/*  410 */           if (minuteHourValidation(Integer.parseInt(request.getParameter("sundayendminute")))) {
/*  411 */             form.setSundayEndMinute(request.getParameter("sundayendminute"));
/*      */           } else {
/*  413 */             return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour create ", "sundayendminute");
/*      */           }
/*      */         } else {
/*  416 */           return APIUtilities.defaultIntegerResponse(request, response, "Businesshour create ", "sundayendminute");
/*      */         }
/*      */       }
/*      */       
/*  420 */       if ("true".equals(request.getParameter("adminAPIRequest"))) {
/*  421 */         MSreq.addParameter("adminAPIRequest", "true");
/*  422 */         MSreq.addParameter("businessid", request.getParameter("businessid"));
/*      */       }
/*      */       
/*  425 */       BusinessHoursAction business = new BusinessHoursAction();
/*      */       try {
/*  427 */         business.addBusinessDetails(null, form, MSreq, response);
/*  428 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.create.success.text"), "4201");
/*      */       }
/*      */       catch (Exception ex) {
/*  431 */         ex.printStackTrace();
/*  432 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.create.failure.text", new String[] { request.getParameter("name") }), "4201");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  439 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  437 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String updateBusinessHour(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/*  443 */     String outputString = null;
/*      */     try {
/*  445 */       if (isJsonFormat) {
/*  446 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  448 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*  451 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*  452 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*  455 */       AMActionForm form = new AMActionForm();
/*  456 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  457 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*  458 */       String businessid = null;
/*  459 */       if (request.getParameter("businessid") != null) {
/*  460 */         businessid = request.getParameter("businessid");
/*  461 */         MSreq.addParameter("sid", businessid);
/*  462 */       } else if (request.getParameter("businesshourname") != null) {
/*  463 */         String name = request.getParameter("businesshourname");
/*  464 */         int businessID = DBUtil.getBusinessHourID(name);
/*  465 */         if (businessID != -1) {
/*  466 */           MSreq.addParameter("sid", businessID + "");
/*  467 */           businessid = businessID + "";
/*      */         } else {
/*  469 */           return APIUtilities.wrongNameResponse(request, response, "Businesshour : Update", "businesshourname");
/*      */         }
/*      */       } else {
/*  472 */         return APIUtilities.emptyParameterResponse(request, response, "Businesshour : Update", "businessid or businesshourname");
/*      */       }
/*  474 */       JSONObject businessDetails = DBUtil.getBusinessHoursDetail(businessid, true);
/*      */       
/*      */ 
/*  477 */       if (businessDetails != null) {
/*  478 */         if (request.getParameter("newbusinessname") != null) {
/*  479 */           form.setRulename(request.getParameter("newbusinessname"));
/*      */         } else {
/*  481 */           form.setRulename(businessDetails.getString("name"));
/*      */         }
/*      */         
/*  484 */         if (request.getParameter("description") != null) {
/*  485 */           form.setTaskDescription(request.getParameter("description"));
/*      */         } else {
/*  487 */           form.setTaskDescription(businessDetails.getString("description"));
/*      */         }
/*  489 */         if (request.getParameter("workingdays") != null) {
/*  490 */           String[] workingdays = request.getParameter("workingdays").split(",");
/*  491 */           ArrayList<String> days = new ArrayList();
/*  492 */           for (String day : workingdays) {
/*  493 */             if (!"".equals(day.trim())) {
/*  494 */               String value = businessdayValidation(day);
/*  495 */               if ("week".equals(value)) {
/*  496 */                 AMLog.debug("REST API : Businesshour update : workingdays should be a day in the week");
/*  497 */                 outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.workingday.wrong.text"), "4102");
/*      */               } else {
/*  499 */                 days.add(day);
/*      */               }
/*      */             }
/*      */           }
/*  503 */           if (days.size() > 0) {
/*  504 */             form.setWorkingdays((String[])days.toArray(new String[days.size()]));
/*      */           }
/*      */         } else {
/*  507 */           String[] selectedDays = businessDetails.getString("selectedDays").split(",");
/*  508 */           form.setWorkingdays(selectedDays);
/*      */         }
/*      */         
/*      */ 
/*  512 */         if (request.getParameter("mondaystarthour") != null) {
/*  513 */           if (Constants.isIntegerNumber(request.getParameter("mondaystarthour"))) {
/*  514 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("mondaystarthour")), true)) {
/*  515 */               form.setMondayStartHour(request.getParameter("mondaystarthour"));
/*      */             } else {
/*  517 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "mondaystarthour");
/*      */             }
/*      */           } else {
/*  520 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "mondaystarthour");
/*      */           }
/*      */         } else {
/*  523 */           form.setMondayStartHour(businessDetails.getString("mondayStartHour"));
/*      */         }
/*      */         
/*  526 */         if (request.getParameter("mondayendhour") != null) {
/*  527 */           if (Constants.isIntegerNumber(request.getParameter("mondayendhour"))) {
/*  528 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("mondayendhour")), true)) {
/*  529 */               form.setMondayEndHour(request.getParameter("mondayendhour"));
/*      */             } else {
/*  531 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "mondayendhour");
/*      */             }
/*      */           } else {
/*  534 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "mondayendhour");
/*      */           }
/*      */         } else {
/*  537 */           form.setMondayEndHour(businessDetails.getString("mondayEndHour"));
/*      */         }
/*      */         
/*  540 */         if (request.getParameter("mondaystartminute") != null) {
/*  541 */           if (Constants.isIntegerNumber(request.getParameter("mondaystartminute"))) {
/*  542 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("mondaystartminute")))) {
/*  543 */               form.setMondayStartMinute(request.getParameter("mondaystartminute"));
/*      */             } else {
/*  545 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "mondaystartminute");
/*      */             }
/*      */           } else {
/*  548 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "mondaystartminute");
/*      */           }
/*      */         } else {
/*  551 */           form.setMondayStartMinute(businessDetails.getString("mondayStartMinute"));
/*      */         }
/*      */         
/*  554 */         if (request.getParameter("mondayendminute") != null) {
/*  555 */           if (Constants.isIntegerNumber(request.getParameter("mondayendminute"))) {
/*  556 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("mondayendminute")))) {
/*  557 */               form.setMondayEndMinute(request.getParameter("mondayendminute"));
/*      */             } else {
/*  559 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "mondayendminute");
/*      */             }
/*      */           } else {
/*  562 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "mondayendminute");
/*      */           }
/*      */         } else {
/*  565 */           form.setMondayEndMinute(businessDetails.getString("mondayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  569 */         if (request.getParameter("tuesdaystarthour") != null) {
/*  570 */           if (Constants.isIntegerNumber(request.getParameter("tuesdaystarthour"))) {
/*  571 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdaystarthour")), true)) {
/*  572 */               form.setTuesdayStartHour(request.getParameter("tuesdaystarthour"));
/*      */             } else {
/*  574 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "tuesdaystarthour");
/*      */             }
/*      */           } else {
/*  577 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "tuesdaystarthour");
/*      */           }
/*      */         } else {
/*  580 */           form.setTuesdayStartHour(businessDetails.getString("tuesdayStartHour"));
/*      */         }
/*      */         
/*  583 */         if (request.getParameter("tuesdayendhour") != null) {
/*  584 */           if (Constants.isIntegerNumber(request.getParameter("tuesdayendhour"))) {
/*  585 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdayendhour")), true)) {
/*  586 */               form.setTuesdayEndHour(request.getParameter("tuesdayendhour"));
/*      */             } else {
/*  588 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "tuesdayendhour");
/*      */             }
/*      */           } else {
/*  591 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "tuesdayendhour");
/*      */           }
/*      */         } else {
/*  594 */           form.setTuesdayEndHour(businessDetails.getString("tuesdayEndHour"));
/*      */         }
/*      */         
/*  597 */         if (request.getParameter("tuesdaystartminute") != null) {
/*  598 */           if (Constants.isIntegerNumber(request.getParameter("tuesdaystartminute"))) {
/*  599 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdaystartminute")))) {
/*  600 */               form.setTuesdayStartMinute(request.getParameter("tuesdaystartminute"));
/*      */             } else {
/*  602 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "tuesdaystartminute");
/*      */             }
/*      */           } else {
/*  605 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "tuesdaystartminute");
/*      */           }
/*      */         } else {
/*  608 */           form.setTuesdayStartMinute(businessDetails.getString("tuesdayStartMinute"));
/*      */         }
/*      */         
/*  611 */         if (request.getParameter("tuesdayendminute") != null) {
/*  612 */           if (Constants.isIntegerNumber(request.getParameter("tuesdayendminute"))) {
/*  613 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("tuesdayendminute")))) {
/*  614 */               form.setTuesdayEndMinute(request.getParameter("tuesdayendminute"));
/*      */             } else {
/*  616 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "tuesdayendminute");
/*      */             }
/*      */           } else {
/*  619 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "tuesdayendminute");
/*      */           }
/*      */         } else {
/*  622 */           form.setTuesdayEndMinute(businessDetails.getString("tuesdayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  626 */         if (request.getParameter("wednesdaystarthour") != null) {
/*  627 */           if (Constants.isIntegerNumber(request.getParameter("wednesdaystarthour"))) {
/*  628 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdaystarthour")), true)) {
/*  629 */               form.setWednesdayStartHour(request.getParameter("wednesdaystarthour"));
/*      */             } else {
/*  631 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "wednesdaystarthour");
/*      */             }
/*      */           } else {
/*  634 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "wednesdaystarthour");
/*      */           }
/*      */         } else {
/*  637 */           form.setWednesdayStartHour(businessDetails.getString("wednesdayStartHour"));
/*      */         }
/*      */         
/*  640 */         if (request.getParameter("wednesdayendhour") != null) {
/*  641 */           if (Constants.isIntegerNumber(request.getParameter("wednesdayendhour"))) {
/*  642 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdayendhour")), true)) {
/*  643 */               form.setWednesdayEndHour(request.getParameter("wednesdayendhour"));
/*      */             } else {
/*  645 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "wednesdayendhour");
/*      */             }
/*      */           } else {
/*  648 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "wednesdayendhour");
/*      */           }
/*      */         } else {
/*  651 */           form.setWednesdayEndHour(businessDetails.getString("wednesdayEndHour"));
/*      */         }
/*      */         
/*  654 */         if (request.getParameter("wednesdaystartminute") != null) {
/*  655 */           if (Constants.isIntegerNumber(request.getParameter("wednesdaystartminute"))) {
/*  656 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdaystartminute")))) {
/*  657 */               form.setWednesdayStartMinute(request.getParameter("wednesdaystartminute"));
/*      */             } else {
/*  659 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "wednesdaystartminute");
/*      */             }
/*      */           } else {
/*  662 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "wednesdaystartminute");
/*      */           }
/*      */         } else {
/*  665 */           form.setWednesdayStartMinute(businessDetails.getString("wednesdayStartMinute"));
/*      */         }
/*      */         
/*  668 */         if (request.getParameter("wednesdayendminute") != null) {
/*  669 */           if (Constants.isIntegerNumber(request.getParameter("wednesdayendminute"))) {
/*  670 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("wednesdayendminute")))) {
/*  671 */               form.setWednesdayEndMinute(request.getParameter("wednesdayendminute"));
/*      */             } else {
/*  673 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "wednesdayendminute");
/*      */             }
/*      */           } else {
/*  676 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "wednesdayendminute");
/*      */           }
/*      */         } else {
/*  679 */           form.setWednesdayEndMinute(businessDetails.getString("wednesdayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  683 */         if (request.getParameter("thursdaystarthour") != null) {
/*  684 */           if (Constants.isIntegerNumber(request.getParameter("thursdaystarthour"))) {
/*  685 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdaystarthour")), true)) {
/*  686 */               form.setThursdayStartHour(request.getParameter("thursdaystarthour"));
/*      */             } else {
/*  688 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "thursdaystarthour");
/*      */             }
/*      */           } else {
/*  691 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "thursdaystarthour");
/*      */           }
/*      */         } else {
/*  694 */           form.setThursdayStartHour(businessDetails.getString("thursdayStartHour"));
/*      */         }
/*      */         
/*  697 */         if (request.getParameter("thursdayendhour") != null) {
/*  698 */           if (Constants.isIntegerNumber(request.getParameter("thursdayendhour"))) {
/*  699 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdayendhour")), true)) {
/*  700 */               form.setThursdayEndHour(request.getParameter("thursdayendhour"));
/*      */             } else {
/*  702 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "thursdayendhour");
/*      */             }
/*      */           } else {
/*  705 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "thursdayendhour");
/*      */           }
/*      */         } else {
/*  708 */           form.setThursdayEndHour(businessDetails.getString("thursdayEndHour"));
/*      */         }
/*      */         
/*  711 */         if (request.getParameter("thursdaystartminute") != null) {
/*  712 */           if (Constants.isIntegerNumber(request.getParameter("thursdaystartminute"))) {
/*  713 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdaystartminute")))) {
/*  714 */               form.setThursdayStartMinute(request.getParameter("thursdaystartminute"));
/*      */             } else {
/*  716 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "thursdaystartminute");
/*      */             }
/*      */           } else {
/*  719 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "thursdaystartminute");
/*      */           }
/*      */         } else {
/*  722 */           form.setThursdayStartMinute(businessDetails.getString("thursdayStartMinute"));
/*      */         }
/*      */         
/*  725 */         if (request.getParameter("thursdayendminute") != null) {
/*  726 */           if (Constants.isIntegerNumber(request.getParameter("thursdayendminute"))) {
/*  727 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("thursdayendminute")))) {
/*  728 */               form.setThursdayEndMinute(request.getParameter("thursdayendminute"));
/*      */             } else {
/*  730 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "thursdayendminute");
/*      */             }
/*      */           } else {
/*  733 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "thursdayendminute");
/*      */           }
/*      */         } else {
/*  736 */           form.setThursdayEndMinute(businessDetails.getString("thursdayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  740 */         if (request.getParameter("fridaystarthour") != null) {
/*  741 */           if (Constants.isIntegerNumber(request.getParameter("fridaystarthour"))) {
/*  742 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("fridaystarthour")), true)) {
/*  743 */               form.setFridayStartHour(request.getParameter("fridaystarthour"));
/*      */             } else {
/*  745 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "fridaystarthour");
/*      */             }
/*      */           } else {
/*  748 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "fridaystarthour");
/*      */           }
/*      */         } else {
/*  751 */           form.setFridayStartHour(businessDetails.getString("fridayStartHour"));
/*      */         }
/*      */         
/*  754 */         if (request.getParameter("fridayendhour") != null) {
/*  755 */           if (Constants.isIntegerNumber(request.getParameter("fridayendhour"))) {
/*  756 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("fridayendhour")), true)) {
/*  757 */               form.setFridayEndHour(request.getParameter("fridayendhour"));
/*      */             } else {
/*  759 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "fridayendhour");
/*      */             }
/*      */           } else {
/*  762 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "fridayendhour");
/*      */           }
/*      */         } else {
/*  765 */           form.setFridayEndHour(businessDetails.getString("fridayEndHour"));
/*      */         }
/*      */         
/*  768 */         if (request.getParameter("fridaystartminute") != null) {
/*  769 */           if (Constants.isIntegerNumber(request.getParameter("fridaystartminute"))) {
/*  770 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("fridaystartminute")))) {
/*  771 */               form.setFridayStartMinute(request.getParameter("fridaystartminute"));
/*      */             } else {
/*  773 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "fridaystartminute");
/*      */             }
/*      */           } else {
/*  776 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "fridaystartminute");
/*      */           }
/*      */         } else {
/*  779 */           form.setFridayStartMinute(businessDetails.getString("fridayStartMinute"));
/*      */         }
/*      */         
/*  782 */         if (request.getParameter("fridayendminute") != null) {
/*  783 */           if (Constants.isIntegerNumber(request.getParameter("fridayendminute"))) {
/*  784 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("fridayendminute")))) {
/*  785 */               form.setFridayEndMinute(request.getParameter("fridayendminute"));
/*      */             } else {
/*  787 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "fridayendminute");
/*      */             }
/*      */           } else {
/*  790 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "fridayendminute");
/*      */           }
/*      */         } else {
/*  793 */           form.setFridayEndMinute(businessDetails.getString("fridayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  797 */         if (request.getParameter("saturdaystarthour") != null) {
/*  798 */           if (Constants.isIntegerNumber(request.getParameter("saturdaystarthour"))) {
/*  799 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdaystarthour")), true)) {
/*  800 */               form.setSaturdayStartHour(request.getParameter("saturdaystarthour"));
/*      */             } else {
/*  802 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "saturdaystarthour");
/*      */             }
/*      */           } else {
/*  805 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "saturdaystarthour");
/*      */           }
/*      */         } else {
/*  808 */           form.setSaturdayStartHour(businessDetails.getString("saturdayStartHour"));
/*      */         }
/*      */         
/*  811 */         if (request.getParameter("saturdayendhour") != null) {
/*  812 */           if (Constants.isIntegerNumber(request.getParameter("saturdayendhour"))) {
/*  813 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdayendhour")), true)) {
/*  814 */               form.setSaturdayEndHour(request.getParameter("saturdayendhour"));
/*      */             } else {
/*  816 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "saturdayendhour");
/*      */             }
/*      */           } else {
/*  819 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "saturdayendhour");
/*      */           }
/*      */         } else {
/*  822 */           form.setSaturdayEndHour(businessDetails.getString("saturdayEndHour"));
/*      */         }
/*      */         
/*  825 */         if (request.getParameter("saturdaystartminute") != null) {
/*  826 */           if (Constants.isIntegerNumber(request.getParameter("saturdaystartminute"))) {
/*  827 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdaystartminute")))) {
/*  828 */               form.setSaturdayStartMinute(request.getParameter("saturdaystartminute"));
/*      */             } else {
/*  830 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "saturdaystartminute");
/*      */             }
/*      */           } else {
/*  833 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "saturdaystartminute");
/*      */           }
/*      */         } else {
/*  836 */           form.setSaturdayStartMinute(businessDetails.getString("saturdayStartMinute"));
/*      */         }
/*      */         
/*  839 */         if (request.getParameter("saturdayendminute") != null) {
/*  840 */           if (Constants.isIntegerNumber(request.getParameter("saturdayendminute"))) {
/*  841 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("saturdayendminute")))) {
/*  842 */               form.setSaturdayEndMinute(request.getParameter("saturdaystartminute"));
/*      */             } else {
/*  844 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "saturdayendminute");
/*      */             }
/*      */           } else {
/*  847 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "saturdayendminute");
/*      */           }
/*      */         } else {
/*  850 */           form.setSaturdayEndMinute(businessDetails.getString("saturdayEndMinute"));
/*      */         }
/*      */         
/*      */ 
/*  854 */         if (request.getParameter("sundaystarthour") != null) {
/*  855 */           if (Constants.isIntegerNumber(request.getParameter("sundaystarthour"))) {
/*  856 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("sundaystarthour")), true)) {
/*  857 */               form.setSundayStartHour(request.getParameter("sundaystarthour"));
/*      */             } else {
/*  859 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "sundaystarthour");
/*      */             }
/*      */           } else {
/*  862 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "sundaystarthour");
/*      */           }
/*      */         } else {
/*  865 */           form.setSundayStartHour(businessDetails.getString("sundayStartHour"));
/*      */         }
/*      */         
/*  868 */         if (request.getParameter("sundayendhour") != null) {
/*  869 */           if (Constants.isIntegerNumber(request.getParameter("sundayendhour"))) {
/*  870 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("sundayendhour")), true)) {
/*  871 */               form.setSundayEndHour(request.getParameter("sundayendhour"));
/*      */             } else {
/*  873 */               return APIUtilities.wrongBusinessHourValue(request, response, "Businesshour update ", "sundayendhour");
/*      */             }
/*      */           } else {
/*  876 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "sundayendhour");
/*      */           }
/*      */         } else {
/*  879 */           form.setSundayEndHour(businessDetails.getString("sundayEndHour"));
/*      */         }
/*      */         
/*  882 */         if (request.getParameter("sundaystartminute") != null) {
/*  883 */           if (Constants.isIntegerNumber(request.getParameter("sundaystartminute"))) {
/*  884 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("sundaystartminute")))) {
/*  885 */               form.setSundayStartMinute(request.getParameter("sundaystartminute"));
/*      */             } else {
/*  887 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "sundaystartminute");
/*      */             }
/*      */           } else {
/*  890 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "sundaystartminute");
/*      */           }
/*      */         } else {
/*  893 */           form.setSundayStartMinute(businessDetails.getString("sundayStartMinute"));
/*      */         }
/*      */         
/*  896 */         if (request.getParameter("sundayendminute") != null) {
/*  897 */           if (Constants.isIntegerNumber(request.getParameter("sundayendminute"))) {
/*  898 */             if (minuteHourValidation(Integer.parseInt(request.getParameter("sundayendminute")))) {
/*  899 */               form.setSundayEndMinute(request.getParameter("sundayendminute"));
/*      */             } else {
/*  901 */               return APIUtilities.wrongBusinessMinuteValue(request, response, "Businesshour update ", "sundayendminute");
/*      */             }
/*      */           } else {
/*  904 */             return APIUtilities.defaultIntegerResponse(request, response, "Businesshour update ", "sundayendminute");
/*      */           }
/*      */         } else {
/*  907 */           form.setSundayEndMinute(businessDetails.getString("sundayEndMinute"));
/*      */         }
/*      */         
/*  910 */         MSreq.addParameter("adminAPIRequest", "true");
/*  911 */         BusinessHoursAction business = new BusinessHoursAction();
/*      */         try
/*      */         {
/*  914 */           business.updateBusinessDetails(null, form, MSreq, response);
/*  915 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.update.success.text"), "4201");
/*      */         }
/*      */         catch (Exception ex) {
/*  918 */           ex.printStackTrace();
/*  919 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.delete.failure.text"), "4201");
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  928 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  931 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteBusinessHour(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/*  935 */     outputString = null;
/*      */     try {
/*  937 */       if (isJsonFormat) {
/*  938 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/*  940 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/*  943 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/*  944 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/*  947 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  948 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*      */       
/*  950 */       if (request.getParameter("businessid") != null) {
/*  951 */         String[] thresholdList = request.getParameter("businessid").split(",");
/*  952 */         for (String id : thresholdList) {
/*  953 */           MSreq.addParameter("scheduleids", id);
/*      */         }
/*  955 */       } else if (request.getParameter("businesshourname") != null) {
/*  956 */         String name = request.getParameter("businesshourname");
/*  957 */         int thresholdID = DBUtil.getBusinessHourID(name);
/*  958 */         if (thresholdID != -1) {
/*  959 */           MSreq.addParameter("scheduleids", thresholdID + "");
/*      */         } else {
/*  961 */           return APIUtilities.wrongNameResponse(request, response, "Businesshour : delete", "businesshourname");
/*      */         }
/*      */       } else {
/*  964 */         return APIUtilities.emptyParameterResponse(request, response, "Businesshour : delete", "businessid or businesshourname");
/*      */       }
/*      */       
/*  967 */       MSreq.addParameter("adminAPIRequest", "true");
/*  968 */       BusinessHoursAction business = new BusinessHoursAction();
/*      */       try
/*      */       {
/*  971 */         business.removeBusinessDetails(null, null, MSreq, response);
/*  972 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.delete.success.text"), "4201");
/*      */       }
/*      */       catch (Exception ex) {
/*  975 */         ex.printStackTrace();
/*  976 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.delete.failure.text"), "4201");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  984 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  981 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void deleteBusinessHourstoSynch(String businessids)
/*      */   {
/*  988 */     HashMap<String, String> parameters = new HashMap();
/*      */     try {
/*  990 */       parameters.put("businessid", businessids);
/*  991 */       parameters.put("TO_DELETE", "true");
/*  992 */       MASSyncUtil.addTasktoSync(parameters, "/AppManager/xml/businesshours", "all", "post", 7, 1);
/*      */     } catch (Exception ex) {
/*  994 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean minuteHourValidation(int value) {
/*  999 */     return minuteHourValidation(value, false);
/*      */   }
/*      */   
/*      */   private static boolean minuteHourValidation(int value, boolean ishour) {
/* 1003 */     boolean isValidate = false;
/* 1004 */     int starttime = 0;
/* 1005 */     int endtime = 60;
/* 1006 */     if (ishour) {
/* 1007 */       endtime = 24;
/*      */     }
/* 1009 */     if ((value >= starttime) && (value <= endtime)) {
/* 1010 */       isValidate = true;
/*      */     }
/* 1012 */     return isValidate;
/*      */   }
/*      */   
/*      */   private static String businessdayValidation(String day) {
/* 1016 */     String value = "week";
/*      */     try {
/* 1018 */       if ("monday".equalsIgnoreCase(day)) {
/* 1019 */         return "Monday";
/*      */       }
/* 1021 */       if ("tuesday".equalsIgnoreCase(day)) {
/* 1022 */         return "Tuesday";
/*      */       }
/* 1024 */       if ("wednesday".equalsIgnoreCase(day)) {
/* 1025 */         return "Wednesday";
/*      */       }
/* 1027 */       if ("thursday".equalsIgnoreCase(day)) {
/* 1028 */         return "Thursday";
/*      */       }
/* 1030 */       if ("friday".equalsIgnoreCase(day)) {
/* 1031 */         return "Friday";
/*      */       }
/* 1033 */       if ("saturday".equalsIgnoreCase(day)) {
/* 1034 */         return "Saturday";
/*      */       }
/* 1036 */       if ("sunday".equalsIgnoreCase(day)) {
/* 1037 */         return "Sunday";
/*      */       }
/*      */     } catch (Exception ex) {
/* 1040 */       ex.printStackTrace();
/*      */     }
/* 1042 */     return value;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void addBusinessHourtoSynch(AMActionForm am, String businessid, boolean toupdate)
/*      */   {
/* 1048 */     HashMap<String, String> businessDetails = new HashMap();
/*      */     
/*      */ 
/* 1051 */     String selecteddays = Constants.getResourceIDWithComma(am.getWorkingdays());
/* 1052 */     if (toupdate) {
/* 1053 */       businessDetails.put("newbusinessname", am.getRulename());
/*      */     } else {
/* 1055 */       businessDetails.put("name", am.getRulename());
/*      */     }
/* 1057 */     businessDetails.put("description", am.getTaskDescription());
/* 1058 */     businessDetails.put("workingdays", selecteddays);
/* 1059 */     businessDetails.put("adminAPIRequest", "true");
/* 1060 */     businessDetails.put("businessid", businessid);
/*      */     
/* 1062 */     businessDetails.put("mondaystarthour", am.getMondayStartHour());
/* 1063 */     businessDetails.put("mondayendhour", am.getMondayEndHour());
/* 1064 */     businessDetails.put("mondaystartminute", am.getMondayStartMinute());
/* 1065 */     businessDetails.put("mondayendminute", am.getMondayEndMinute());
/*      */     
/* 1067 */     businessDetails.put("tuesdaystarthour", am.getTuesdayStartHour());
/* 1068 */     businessDetails.put("tuesdayendhour", am.getTuesdayEndHour());
/* 1069 */     businessDetails.put("tuesdaystartminute", am.getTuesdayStartMinute());
/* 1070 */     businessDetails.put("tuesdayendminute", am.getTuesdayEndMinute());
/*      */     
/* 1072 */     businessDetails.put("wednesdaystarthour", am.getWednesdayStartHour());
/* 1073 */     businessDetails.put("wednesdayendhour", am.getWednesdayEndHour());
/* 1074 */     businessDetails.put("wednesdaystartminute", am.getWednesdayStartMinute());
/* 1075 */     businessDetails.put("wednesdayendminute", am.getWednesdayEndMinute());
/*      */     
/* 1077 */     businessDetails.put("thursdaystarthour", am.getThursdayStartHour());
/* 1078 */     businessDetails.put("thursdayendhour", am.getThursdayEndHour());
/* 1079 */     businessDetails.put("thursdaystartminute", am.getThursdayStartMinute());
/* 1080 */     businessDetails.put("thursdayendminute", am.getThursdayEndMinute());
/*      */     
/* 1082 */     businessDetails.put("fridaystarthour", am.getFridayStartHour());
/* 1083 */     businessDetails.put("fridayendhour", am.getFridayEndHour());
/* 1084 */     businessDetails.put("fridaystartminute", am.getFridayStartMinute());
/* 1085 */     businessDetails.put("fridayendminute", am.getFridayEndMinute());
/*      */     
/* 1087 */     businessDetails.put("saturdaystarthour", am.getSaturdayStartHour());
/* 1088 */     businessDetails.put("saturdayendhour", am.getSaturdayEndHour());
/* 1089 */     businessDetails.put("saturdaystartminute", am.getSaturdayStartMinute());
/* 1090 */     businessDetails.put("saturdayendminute", am.getSaturdayEndMinute());
/*      */     
/* 1092 */     businessDetails.put("sundaystarthour", am.getSundayStartHour());
/* 1093 */     businessDetails.put("sundayendhour", am.getSundayEndHour());
/* 1094 */     businessDetails.put("sundaystartminute", am.getSundayStartMinute());
/* 1095 */     businessDetails.put("sundayendminute", am.getSundayEndMinute());
/* 1096 */     if (toupdate) {
/* 1097 */       MASSyncUtil.addTasktoSync(businessDetails, "/AppManager/xml/businesshours", "all", "post", 7, 1);
/*      */     } else {
/* 1099 */       MASSyncUtil.addTasktoSync(businessDetails, "/AppManager/xml/businesshours", "all", "post", 7, 1);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String listBusinessHour(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 1104 */     String outputString = null;
/* 1105 */     ResultSet rs = null;
/* 1106 */     String uri = request.getRequestURI();
/* 1107 */     ArrayList<Hashtable<String, String>> businessHourList = new ArrayList();
/*      */     try {
/* 1109 */       if (isJsonFormat) {
/* 1110 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 1112 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 1115 */       if ((!CommonAPIUtil.isAdminRole(request)) && (!CommonAPIUtil.isDelegatedAdmin(request))) {
/* 1116 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 1117 */         return outputString;
/*      */       }
/* 1119 */       rs = AMConnectionPool.executeQueryStmt("SELECT ID,NAME,STATUS,DESCRIPTION FROM AM_BUSINESSHOURSDETAILS ORDER BY NAME");
/* 1120 */       while (rs.next()) {
/* 1121 */         Object businessHour = new Hashtable();
/* 1122 */         ((Hashtable)businessHour).put("BUSINESSID", rs.getString("ID"));
/* 1123 */         ((Hashtable)businessHour).put("BUSINESSHOUR", rs.getString("NAME"));
/* 1124 */         ((Hashtable)businessHour).put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 1125 */         businessHourList.add(businessHour);
/*      */       }
/* 1127 */       if (businessHourList.size() == 0) {
/* 1128 */         Hashtable msg = new Hashtable();
/* 1129 */         msg.put("Message", FormatUtil.getString("am.webclient.api.businesshour.list.empty.text"));
/* 1130 */         businessHourList.add(msg);
/*      */       }
/* 1132 */       HashMap results = new HashMap();
/* 1133 */       results.put("response-code", "4000");
/* 1134 */       results.put("uri", uri);
/* 1135 */       results.put("result", businessHourList);
/* 1136 */       results.put("sortingParam", "BUSINESSHOUR");
/* 1137 */       results.put("parentNode", "BusinessHours");
/* 1138 */       results.put("nodeName", "BusinessHour");
/* 1139 */       outputString = CommonAPIUtil.getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 1141 */       ex.printStackTrace();
/*      */     } finally {
/* 1143 */       if (rs != null) {
/* 1144 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 1147 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String businessHourAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*      */   {
/* 1152 */     if (request.getMethod().equals("GET")) {
/* 1153 */       return listBusinessHour(request, response, isJsonFormat);
/*      */     }
/* 1155 */     return businessHourPostOperations(request, response, isJsonFormat);
/*      */   }
/*      */   
/*      */   public static String businessHourPostOperations(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws Exception
/*      */   {
/* 1161 */     if (request.getParameter("name") != null)
/*      */     {
/* 1163 */       return createBusinessHour(request, response, isJsonFormat); }
/* 1164 */     if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*      */     {
/* 1166 */       return deleteBusinessHour(request, response, isJsonFormat);
/*      */     }
/*      */     
/* 1169 */     return updateBusinessHour(request, response, isJsonFormat);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\BusinessHourAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */