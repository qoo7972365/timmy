/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.struts.actions.DownTimeSchedulerAction;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.TimeZone;
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
/*      */ public class MaintenanceTaskAPIUtil
/*      */ {
/*      */   public static String deleteMaintenanceTaskXML(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   30 */     String uri = request.getRequestURI();
/*   31 */     boolean isJsonFormat = uri.contains("json");
/*   32 */     if (isJsonFormat)
/*      */     {
/*   34 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/*   38 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*   40 */     String message = "";
/*   41 */     String errorCode = "4000";
/*   42 */     String outputString = "";
/*   43 */     String strtaskid = request.getParameter("taskid");
/*   44 */     String[] strArray = new String[0];
/*   45 */     int userId = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey")));
/*   46 */     boolean isPriviledgedUser = Constants.isPrivilegedUser(request);
/*   47 */     if (strtaskid != null)
/*      */     {
/*   49 */       strArray = strtaskid.split(",");
/*      */       try
/*      */       {
/*   52 */         for (int i = 0; i < strArray.length; i++)
/*      */         {
/*   54 */           Integer.parseInt(strArray[i]);
/*   55 */           if ((isPriviledgedUser) && (!DelegatedUserRoleUtil.isOwnedByDelegatedUser(Integer.parseInt(strArray[i]), userId, 5)))
/*      */           {
/*   57 */             AMLog.debug("REST API : The user is not authorized to access the specified resource");
/*   58 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*   65 */         AMLog.debug("REST API : The specified request URI is incorrect:the tasklid specified is null or not a integer");
/*   66 */         message = FormatUtil.getString("am.webclient.api.notinttaskid.message");
/*   67 */         errorCode = "4048";
/*   68 */         return URITree.generateXML(request, response, message, errorCode);
/*      */       }
/*      */     }
/*      */     
/*   72 */     if (validateMaintenanceTask(request, response, strtaskid, "DeleteMaintenanceTask"))
/*      */     {
/*   74 */       MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*   75 */       MSreq.setContentType("text/xml; charset=UTF-8");
/*   76 */       String ParameterNames = "";
/*   77 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*   79 */         ParameterNames = (String)e.nextElement();
/*   80 */         MSreq.addParameter(ParameterNames, request.getParameter(ParameterNames));
/*      */       }
/*   82 */       for (int i = 0; i < strArray.length; i++)
/*      */       {
/*   84 */         MSreq.addParameter("checkbox", strArray[i]);
/*      */       }
/*   86 */       DownTimeSchedulerAction am = new DownTimeSchedulerAction();
/*   87 */       am.deleteMaintenanceTask(null, null, MSreq, response);
/*   88 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.delmain.succmsg"), errorCode, true);
/*   89 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*   93 */     AMLog.debug("REST API : The specified request URI is incorrect:the taskid specified is wrong");
/*   94 */     message = FormatUtil.getString("am.webclient.api.wrongtaskidparam.message");
/*   95 */     errorCode = "4024";
/*   96 */     outputString = URITree.generateXML(request, response, message, errorCode);
/*   97 */     return outputString;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getTimezone(String offset)
/*      */   {
/*  103 */     if (offset == null)
/*      */     {
/*  105 */       return "null";
/*      */     }
/*  107 */     TimeZone tz = TimeZone.getDefault();
/*  108 */     int h = Integer.parseInt(offset.substring(4, 6));
/*  109 */     int m = Integer.parseInt(offset.substring(7));
/*  110 */     int value = h * 60 * 60 * 1000 + m * 60 * 1000;
/*  111 */     String[] strs = TimeZone.getAvailableIDs(value);
/*  112 */     if (strs.length != 0)
/*      */     {
/*  114 */       return strs[0];
/*      */     }
/*  116 */     return tz.getID();
/*      */   }
/*      */   
/*      */   public static String createMaintenanceTaskXML(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  121 */     response.setContentType("text/xml; charset=UTF-8");
/*  122 */     String message = "";
/*  123 */     String errorCode = "4000";
/*  124 */     String outputString = "";
/*  125 */     String taskname = request.getParameter("taskName");
/*  126 */     String taskstatus = request.getParameter("taskStatus");
/*  127 */     String taskmethod = request.getParameter("taskMethod");
/*  128 */     String taskType = request.getParameter("taskType");
/*  129 */     String resId = request.getParameter("resourceid");
/*  130 */     String offset = request.getParameter("offset");
/*  131 */     String timezone = getTimezone(offset);
/*  132 */     DownTimeSchedulerAction am = new DownTimeSchedulerAction();
/*  133 */     AMActionForm amform = new AMActionForm();
/*  134 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  135 */     MSreq.setContentType("text/xml; charset=UTF-8");
/*  136 */     if ((Constants.isPrivilegedUser(request)) && (!isAuthorized(resId, request)))
/*      */     {
/*  138 */       AMLog.debug("REST API : The user doesn't have access to the given resources.");
/*  139 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*  140 */       return outputString;
/*      */     }
/*  142 */     if ((taskname == null) || (taskname.equals("")))
/*      */     {
/*  144 */       AMLog.debug("REST API : The taskName cannot be empty.");
/*  145 */       message = FormatUtil.getString("am.webclient.api.maintskname.empty");
/*  146 */       errorCode = "4033";
/*  147 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  148 */       return outputString;
/*      */     }
/*  150 */     if (!isTaskNameExist(taskname))
/*      */     {
/*  152 */       AMLog.debug("REST API : The taskName already exist.");
/*  153 */       message = FormatUtil.getString("am.webclient.api.maintskname.exist");
/*  154 */       errorCode = "4034";
/*  155 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  156 */       return outputString;
/*      */     }
/*      */     
/*  159 */     if ((taskstatus != null) && ((request.getParameter("taskStatus").equalsIgnoreCase("enable")) || (request.getParameter("taskStatus").equalsIgnoreCase("disable"))))
/*      */     {
/*  161 */       taskstatus = request.getParameter("taskStatus");
/*      */     }
/*      */     else
/*      */     {
/*  165 */       AMLog.debug("REST API : The taskStatus should be either enable or disable.");
/*  166 */       message = FormatUtil.getString("am.webclient.api.maintsksts.msg");
/*  167 */       errorCode = "4035";
/*  168 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  169 */       return outputString;
/*      */     }
/*  171 */     if ((taskType != null) && ((taskType.equalsIgnoreCase("monitor")) || (taskType.equalsIgnoreCase("group"))))
/*      */     {
/*  173 */       String[] strArray = new String[0];
/*  174 */       strArray = resId.split(",");
/*  175 */       String ParameterNames = "";
/*  176 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*  178 */         ParameterNames = (String)e.nextElement();
/*  179 */         MSreq.addParameter(ParameterNames, request.getParameter(ParameterNames));
/*      */       }
/*  181 */       MSreq.addParameter("timezone", timezone);
/*  182 */       if (taskType.equalsIgnoreCase("monitor"))
/*      */       {
/*  184 */         amform.setTaskGroup("allmonitors");
/*  185 */         for (int i = 0; i < strArray.length; i++)
/*      */         {
/*  187 */           MSreq.addParameter("maintenanceCombo2", strArray[i]);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  192 */         amform.setTaskGroup("allgroups");
/*  193 */         for (int i = 0; i < strArray.length; i++)
/*      */         {
/*  195 */           MSreq.addParameter("select", strArray[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  201 */       AMLog.debug("REST API : The taskType should be either group or monitor.");
/*  202 */       message = FormatUtil.getString("am.webclient.api.maintsktyp.msg");
/*  203 */       errorCode = "4036";
/*  204 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  205 */       return outputString;
/*      */     }
/*  207 */     if (!isResTrue(resId, "CreateMaintenanceTasK", taskType))
/*      */     {
/*  209 */       AMLog.debug("REST API : Improper resourceid in the request.");
/*  210 */       message = FormatUtil.getString("am.webclient.api.mainresiderr.msg");
/*  211 */       errorCode = "4037";
/*  212 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  213 */       return outputString;
/*      */     }
/*  215 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("daily")))
/*      */     {
/*  217 */       String starttime = request.getParameter("taskStartTime");
/*  218 */       String endtime = request.getParameter("taskEndTime");
/*  219 */       String effectfrom = request.getParameter("taskEffectFrom");
/*  220 */       if ((starttime == null) || (starttime.equalsIgnoreCase("")) || (!isTimeTrue(starttime)))
/*      */       {
/*  222 */         AMLog.debug("REST API : The taskStartTime should be of the format (HH:MM).");
/*  223 */         message = FormatUtil.getString("am.webclient.api.mainstarttime.msg");
/*  224 */         errorCode = "4038";
/*  225 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  226 */         return outputString;
/*      */       }
/*  228 */       if ((endtime == null) || (endtime.equalsIgnoreCase("")) || (!isTimeTrue(endtime)))
/*      */       {
/*  230 */         AMLog.debug("REST API : The taskEndTime should be of the format (HH:MM).");
/*  231 */         message = FormatUtil.getString("am.webclient.api.mainendtime.msg");
/*  232 */         errorCode = "4039";
/*  233 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  234 */         return outputString;
/*      */       }
/*  236 */       if ((effectfrom == null) || (effectfrom.equalsIgnoreCase("")) || (!isDateTrue(effectfrom)))
/*      */       {
/*  238 */         AMLog.debug("REST API : The taskEffectFrom should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  239 */         message = FormatUtil.getString("am.webclient.api.maineffrom.msg");
/*  240 */         errorCode = "4041";
/*  241 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  242 */         return outputString;
/*      */       }
/*  244 */       if (!startEndTimeCheck(starttime, endtime))
/*      */       {
/*  246 */         AMLog.debug("REST API : The taskStartTime should less than taskEndTime.");
/*  247 */         message = FormatUtil.getString("The taskStartTime should less than taskEndTime.");
/*  248 */         errorCode = "4250";
/*  249 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  250 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  254 */       MSreq.addParameter("userId", request.getRemoteUser());
/*  255 */       am.createMaintenanceTask(null, amform, MSreq, response);
/*  256 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maincreatesucc.msg"), errorCode, true);
/*  257 */       return outputString;
/*      */     }
/*      */     
/*  260 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("weekly")))
/*      */     {
/*  262 */       int totalNumber = 0;
/*      */       try
/*      */       {
/*  265 */         totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  269 */         AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  270 */         message = FormatUtil.getString("am.webclient.api.maintsknom.msg");
/*  271 */         errorCode = "4043";
/*  272 */         return URITree.generateXML(request, response, message, errorCode);
/*      */       }
/*      */       
/*  275 */       if ((totalNumber >= 1) && (totalNumber <= 7))
/*      */       {
/*  277 */         MSreq.addParameter("numbers", "" + totalNumber);
/*      */       }
/*      */       else
/*      */       {
/*  281 */         AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  282 */         message = FormatUtil.getString("am.webclient.api.maintsknom.msg");
/*  283 */         errorCode = "4043";
/*  284 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  285 */         return outputString;
/*      */       }
/*  287 */       if (weeklyCheckTrue(request, totalNumber))
/*      */       {
/*  289 */         if (!startEndWeeklyCheck(request, totalNumber))
/*      */         {
/*  291 */           AMLog.debug("REST API : Check for the date time configuration of weekly.");
/*  292 */           message = FormatUtil.getString("Check for the date time configuration of weekly.");
/*  293 */           errorCode = "4251";
/*  294 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*  295 */           return outputString;
/*      */         }
/*  297 */         for (int i = 0; i < totalNumber; i++)
/*      */         {
/*  299 */           MSreq.addParameter("startDay(" + i + ")", getDayVal(request.getParameter("startDay" + (i + 1))));
/*  300 */           MSreq.addParameter("startTime(" + i + ")", request.getParameter("startTime" + (i + 1)));
/*  301 */           MSreq.addParameter("endDay(" + i + ")", getDayVal(request.getParameter("endDay" + (i + 1))));
/*  302 */           MSreq.addParameter("endTime(" + i + ")", request.getParameter("endTime" + (i + 1)));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  307 */         AMLog.debug("REST API : The startDay,startTime,endDay,endTime for weekly Maintenance are incorrect.");
/*  308 */         message = FormatUtil.getString("am.webclient.api.mainweek.msg");
/*  309 */         errorCode = "4046";
/*  310 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  311 */         return outputString;
/*      */       }
/*  313 */       String effectfrom = request.getParameter("taskEffectFrom");
/*  314 */       if ((effectfrom != null) && (isDateTrue(effectfrom)))
/*      */       {
/*  316 */         am.createMaintenanceTask(null, amform, MSreq, response);
/*  317 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maincreatesucc.msg"), errorCode, true);
/*  318 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  322 */       AMLog.debug("REST API : The effectFrom should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  323 */       message = FormatUtil.getString("am.webclient.api.maineffrom.msg");
/*  324 */       errorCode = "4041";
/*  325 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  326 */       return outputString;
/*      */     }
/*      */     
/*  329 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("once")))
/*      */     {
/*  331 */       if ((request.getParameter("customTaskStartTime") == null) || (!isDateTrue(request.getParameter("customTaskStartTime"))))
/*      */       {
/*  333 */         AMLog.debug("REST API : The customTaskStartTime should be of the format (YYYY-MM-DD HH:MM).");
/*  334 */         message = FormatUtil.getString("am.webclient.api.maincusstrtime.msg");
/*  335 */         outputString = URITree.generateXML(request, response, message, "4044");
/*  336 */         return outputString;
/*      */       }
/*  338 */       if ((request.getParameter("customTaskEndTime") == null) || (!isDateTrue(request.getParameter("customTaskEndTime"))))
/*      */       {
/*  340 */         AMLog.debug("REST API : The customTaskEndTime should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  341 */         message = FormatUtil.getString("am.webclient.api.maincusendtime.msg");
/*  342 */         outputString = URITree.generateXML(request, response, message, "4045");
/*  343 */         return outputString;
/*      */       }
/*  345 */       if (!customStartEndDateTimeCheck(request.getParameter("customTaskStartTime"), request.getParameter("customTaskEndTime")))
/*      */       {
/*  347 */         AMLog.debug("REST API : The customTaskStartTime should be less than customTaskEndTime.");
/*  348 */         message = FormatUtil.getString("The customTaskStartTime should be less than customTaskEndTime.");
/*  349 */         outputString = URITree.generateXML(request, response, message, "4252");
/*  350 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  354 */       am.createMaintenanceTask(null, amform, MSreq, response);
/*  355 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maincreatesucc.msg"), "4252");
/*  356 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  361 */     AMLog.debug("REST API : Task Method should be any one among Daily,Weekly or Once.");
/*  362 */     message = FormatUtil.getString("am.webclient.api.maintskmth.msg");
/*  363 */     errorCode = "4042";
/*  364 */     outputString = URITree.generateXML(request, response, message, errorCode);
/*  365 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String editMaintenanceTaskXML(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  371 */     response.setContentType("text/xml; charset=UTF-8");
/*  372 */     String message = "";
/*  373 */     String errorCode = "4000";
/*  374 */     String outputString = "";
/*  375 */     String taskname = request.getParameter("taskName");
/*  376 */     String taskstatus = request.getParameter("taskStatus");
/*  377 */     String taskmethod = request.getParameter("taskMethod");
/*  378 */     String taskType = request.getParameter("taskType");
/*  379 */     String resId = request.getParameter("resourceid");
/*  380 */     String taskid = request.getParameter("taskid");
/*  381 */     String offset = request.getParameter("offset");
/*  382 */     String timezone = getTimezone(offset);
/*  383 */     int userId = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey")));
/*  384 */     if ((Constants.isPrivilegedUser(request)) && (!DelegatedUserRoleUtil.isOwnedByDelegatedUser(Integer.parseInt(taskid), userId, 5)))
/*      */     {
/*  386 */       AMLog.debug("REST API : The user is not authorized to edit the given task.");
/*  387 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*  388 */       return outputString;
/*      */     }
/*      */     try
/*      */     {
/*  392 */       Integer.parseInt(taskid);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  396 */       AMLog.debug("REST API : The given taskid in the URL is not an integer.");
/*  397 */       message = FormatUtil.getString("am.webclient.api.notinttaskid.message");
/*  398 */       errorCode = "4048";
/*  399 */       return URITree.generateXML(request, response, message, errorCode);
/*      */     }
/*      */     
/*  402 */     if (!validateMaintenanceTask(request, response, taskid, "EditMaintenanceTask"))
/*      */     {
/*  404 */       AMLog.debug("REST API : The given taskid in the request URL is wrong.");
/*  405 */       message = FormatUtil.getString("am.webclient.api.wrongtaskidparam.message");
/*  406 */       errorCode = "4024";
/*  407 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  408 */       return outputString;
/*      */     }
/*  410 */     if (resId == null)
/*      */     {
/*  412 */       if ((taskstatus != null) && (resId == null))
/*      */       {
/*  414 */         int status = 1;
/*  415 */         if ((request.getParameter("taskStatus").equalsIgnoreCase("enable")) || (request.getParameter("taskStatus").equalsIgnoreCase("disable")))
/*      */         {
/*  417 */           if (taskstatus.equalsIgnoreCase("disable"))
/*      */           {
/*  419 */             status = 0;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  424 */           AMLog.debug("REST API : The taskStatus should be either enable or disable.");
/*  425 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maintsksts.msg"), "4035");
/*  426 */           return outputString;
/*      */         }
/*  428 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  431 */           String query = "";
/*  432 */           query = "update AM_MAINTENANCECONFIG set STATUS = '" + status + "' where TASKID=" + taskid;
/*  433 */           AMConnectionPool.executeUpdateStmt(query);
/*  434 */           AMConnectionPool.closeStatement(rs);
/*  435 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  439 */           ex.printStackTrace();
/*  440 */           AMLog.debug("REST API : Server error");
/*  441 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */         }
/*      */         finally
/*      */         {
/*  445 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*  448 */       if ((taskmethod != null) && (resId == null))
/*      */       {
/*  450 */         String query = "";
/*  451 */         ResultSet rs = null;
/*  452 */         if ((taskmethod != null) && ((taskmethod.equalsIgnoreCase("once")) || (taskmethod.equalsIgnoreCase("weekly")) || (taskmethod.equalsIgnoreCase("daily"))))
/*      */         {
/*  454 */           String tasktype = "";
/*  455 */           if (taskmethod.equalsIgnoreCase("daily"))
/*      */           {
/*  457 */             tasktype = "1";
/*      */           }
/*  459 */           else if (taskmethod.equalsIgnoreCase("weekly"))
/*      */           {
/*  461 */             tasktype = "2";
/*      */           }
/*      */           else
/*      */           {
/*  465 */             tasktype = "3";
/*      */           }
/*  467 */           if (!validateTaskidTaskmethod(taskid, tasktype))
/*      */           {
/*  469 */             AMLog.debug("REST API : The specified taskmethod and taskid does not match.");
/*  470 */             outputString = URITree.generateXML(request, response, "The specified taskMethod and taskid does not match.", "4249");
/*  471 */             return outputString;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  476 */           AMLog.debug("REST API : The specified request URI is incorrect:the taskMethod is not specified or wrong. taskMethod should be either daily or weekly or once");
/*  477 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maintskmth.msg"), "4042");
/*  478 */           return outputString;
/*      */         }
/*  480 */         if (taskmethod.equalsIgnoreCase("daily"))
/*      */         {
/*  482 */           String starttime = request.getParameter("taskStartTime");
/*  483 */           String endtime = request.getParameter("taskEndTime");
/*  484 */           String effectfrom = request.getParameter("taskEffectFrom");
/*  485 */           if ((starttime == null) || (starttime.equalsIgnoreCase("")) || (!isTimeTrue(starttime)))
/*      */           {
/*  487 */             AMLog.debug("REST API : The taskStartTime should be of the format (HH:MM).");
/*  488 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainstarttime.msg"), "4038");
/*  489 */             return outputString;
/*      */           }
/*  491 */           if ((endtime == null) || (endtime.equalsIgnoreCase("")) || (!isTimeTrue(endtime)))
/*      */           {
/*  493 */             AMLog.debug("REST API : The taskEndTime should be of the format (HH:MM).");
/*  494 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainendtime.msg"), "4039");
/*  495 */             return outputString;
/*      */           }
/*  497 */           if ((effectfrom == null) || (effectfrom.equalsIgnoreCase("")) || (!isDateTrue(effectfrom)))
/*      */           {
/*  499 */             AMLog.debug("REST API : The taskEffectFrom should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  500 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineffrom.msg"), "4041");
/*  501 */             return outputString;
/*      */           }
/*  503 */           if (!startEndTimeCheck(starttime, endtime))
/*      */           {
/*  505 */             AMLog.debug("REST API : The taskStartTime should less than taskEndTime.");
/*  506 */             message = FormatUtil.getString("The taskStartTime should less than taskEndTime.");
/*  507 */             errorCode = "4250";
/*  508 */             outputString = URITree.generateXML(request, response, message, errorCode);
/*  509 */             return outputString;
/*      */           }
/*      */           
/*      */ 
/*  513 */           effectfrom = effectfrom + ":00";
/*      */           try
/*      */           {
/*  516 */             query = "update AM_DAILYMAINTENANCE set STARTTIME = '" + starttime + "',ENDTIME = '" + endtime + "',EFFECTFROMTIME = '" + effectfrom + "' where TASKID=" + taskid;
/*  517 */             AMConnectionPool.executeUpdateStmt(query);
/*  518 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  522 */             e.printStackTrace();
/*  523 */             AMLog.debug("REST API : Server error");
/*  524 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */           }
/*      */           finally
/*      */           {
/*  528 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */           
/*      */         }
/*  532 */         else if (taskmethod.equalsIgnoreCase("weekly"))
/*      */         {
/*  534 */           int totalNumber = 0;
/*      */           try
/*      */           {
/*  537 */             totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  541 */             AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  542 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maintsknom.msg"), "4043");
/*      */           }
/*      */           
/*  545 */           if ((totalNumber < 1) && (totalNumber > 7))
/*      */           {
/*  547 */             AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  548 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maintsknom.msg"), "4043");
/*  549 */             return outputString;
/*      */           }
/*  551 */           if (weeklyCheckTrue(request, totalNumber))
/*      */           {
/*  553 */             if (!startEndWeeklyCheck(request, totalNumber))
/*      */             {
/*  555 */               AMLog.debug("REST API : Check for the date time configuration of weekly.");
/*  556 */               message = FormatUtil.getString("Check for the date time configuration of weekly.");
/*  557 */               errorCode = "4251";
/*  558 */               outputString = URITree.generateXML(request, response, message, errorCode);
/*  559 */               return outputString;
/*      */             }
/*  561 */             AMConnectionPool.executeUpdateStmt("delete from AM_WEEKLYMAINTENANCE where TASKID='" + taskid + "'");
/*  562 */             for (int i = 0; i < totalNumber; i++)
/*      */             {
/*      */               try
/*      */               {
/*  566 */                 query = "insert into AM_WEEKLYMAINTENANCE (TASKID,STARTDAY,STARTTIME,ENDDAY,ENDTIME) values('" + taskid + "','" + request.getParameter(new StringBuilder().append("startDay").append(i + 1).toString()) + "','" + request.getParameter(new StringBuilder().append("startTime").append(i + 1).toString()) + "','" + request.getParameter(new StringBuilder().append("endDay").append(i + 1).toString()) + "','" + request.getParameter(new StringBuilder().append("endTime").append(i + 1).toString()) + "')";
/*  567 */                 AMConnectionPool.executeUpdateStmt(query);
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*  571 */                 e.printStackTrace();
/*  572 */                 AMLog.debug("REST API : Server error");
/*  573 */                 outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */               }
/*      */             }
/*  576 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*      */           }
/*      */           else
/*      */           {
/*  580 */             AMLog.debug("REST API : The specified request URI is incorrect: startDay,startDay,startDay,endTime for daily Maintenance are incorrect");
/*  581 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainweek.msg"), "4044");
/*  582 */             return outputString;
/*      */           }
/*      */         }
/*  585 */         else if (taskmethod.equalsIgnoreCase("once"))
/*      */         {
/*  587 */           if ((request.getParameter("customTaskStartTime") == null) || (!isDateTrue(request.getParameter("customTaskStartTime"))))
/*      */           {
/*  589 */             AMLog.debug("REST API : The customTaskStartTime should be of the format (YYYY-MM-DD HH:MM).");
/*  590 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maincusstrtime.msg"), "4044");
/*  591 */             return outputString;
/*      */           }
/*  593 */           if ((request.getParameter("customTaskEndTime") == null) || (!isDateTrue(request.getParameter("customTaskEndTime"))))
/*      */           {
/*  595 */             AMLog.debug("REST API : The customTaskEndTime should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  596 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maincusendtime.msg"), "4045");
/*  597 */             return outputString;
/*      */           }
/*  599 */           if (!customStartEndDateTimeCheck(request.getParameter("customTaskStartTime"), request.getParameter("customTaskEndTime")))
/*      */           {
/*  601 */             AMLog.debug("REST API : The customTaskStartTime should be less than customTaskEndTime.");
/*  602 */             message = FormatUtil.getString("The customTaskStartTime should be less than customTaskEndTime.");
/*  603 */             outputString = URITree.generateXML(request, response, message, "4252");
/*  604 */             return outputString;
/*      */           }
/*      */           try
/*      */           {
/*  608 */             String starttime = request.getParameter("customTaskStartTime") + ":00";
/*  609 */             String endtime = request.getParameter("customTaskEndTime") + ":00";
/*  610 */             query = "update AM_CUSTOMMAINTENANCE set STARTTIME = '" + starttime + "',ENDTIME = '" + endtime + "' where TASKID=" + taskid;
/*  611 */             AMConnectionPool.executeUpdateStmt(query);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  615 */             e.printStackTrace();
/*  616 */             AMLog.debug("REST API : Server error");
/*  617 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */           }
/*  619 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*      */         }
/*      */       }
/*  622 */       return outputString;
/*      */     }
/*  624 */     DownTimeSchedulerAction am = new DownTimeSchedulerAction();
/*  625 */     AMActionForm amform = new AMActionForm();
/*  626 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/*  627 */     MSreq.setContentType("text/xml; charset=UTF-8");
/*  628 */     if ((taskname == null) || (taskname.equals("")))
/*      */     {
/*  630 */       AMLog.debug("REST API : The taskName cannot be empty.");
/*  631 */       message = FormatUtil.getString("am.webclient.api.maintskname.empty");
/*  632 */       errorCode = "4033";
/*  633 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  634 */       return outputString;
/*      */     }
/*  636 */     if (!editTaskNameCheck(taskname, taskid))
/*      */     {
/*      */ 
/*  639 */       AMLog.debug("REST API : The specified taskname in the URL is already exist or empty.");
/*  640 */       message = FormatUtil.getString("am.webclient.api.mainedittsk.msg");
/*  641 */       errorCode = "4025";
/*  642 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  643 */       return outputString;
/*      */     }
/*  645 */     if ((taskstatus != null) && ((request.getParameter("taskStatus").equalsIgnoreCase("enable")) || (request.getParameter("taskStatus").equalsIgnoreCase("disable"))))
/*      */     {
/*  647 */       taskstatus = request.getParameter("taskStatus");
/*      */     }
/*      */     else
/*      */     {
/*  651 */       AMLog.debug("REST API : The taskStatus should be either enable or disable.");
/*  652 */       message = FormatUtil.getString("am.webclient.api.maintsksts.msg");
/*  653 */       errorCode = "4035";
/*  654 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  655 */       return outputString;
/*      */     }
/*  657 */     if ((taskType != null) && ((taskType.equalsIgnoreCase("monitor")) || (taskType.equalsIgnoreCase("group"))))
/*      */     {
/*  659 */       String[] strArray = new String[0];
/*  660 */       strArray = resId.split(",");
/*  661 */       String ParameterNames = "";
/*  662 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*  664 */         ParameterNames = (String)e.nextElement();
/*  665 */         MSreq.addParameter(ParameterNames, request.getParameter(ParameterNames));
/*      */       }
/*  667 */       MSreq.addParameter("timezone", timezone);
/*  668 */       if (taskType.equalsIgnoreCase("monitor"))
/*      */       {
/*  670 */         amform.setTaskGroup("allmonitors");
/*  671 */         for (int i = 0; i < strArray.length; i++)
/*      */         {
/*  673 */           MSreq.addParameter("maintenanceCombo2", strArray[i]);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  678 */         amform.setTaskGroup("allgroups");
/*  679 */         for (int i = 0; i < strArray.length; i++)
/*      */         {
/*  681 */           MSreq.addParameter("select", strArray[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  687 */       AMLog.debug("REST API : The taskType should be either group or monitor.");
/*  688 */       message = FormatUtil.getString("am.webclient.api.maintsktyp.msg");
/*  689 */       errorCode = "4036";
/*  690 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  691 */       return outputString;
/*      */     }
/*  693 */     if (!isResTrue(resId, "EditMaintenanceTasK", taskType))
/*      */     {
/*  695 */       AMLog.debug("REST API : Improper resourceid in the request.");
/*  696 */       message = FormatUtil.getString("am.webclient.api.mainresiderr.msg");
/*  697 */       errorCode = "4037";
/*  698 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  699 */       return outputString;
/*      */     }
/*  701 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("daily")))
/*      */     {
/*  703 */       String starttime = request.getParameter("taskStartTime");
/*  704 */       String endtime = request.getParameter("taskEndTime");
/*  705 */       String effectfrom = request.getParameter("taskEffectFrom");
/*  706 */       if ((starttime == null) || (starttime.equalsIgnoreCase("")) || (!isTimeTrue(starttime)))
/*      */       {
/*  708 */         AMLog.debug("REST API : The startTime should be of the format (HH:MM).");
/*  709 */         message = FormatUtil.getString("am.webclient.api.mainstarttime.msg");
/*  710 */         errorCode = "4038";
/*  711 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  712 */         return outputString;
/*      */       }
/*  714 */       if ((endtime == null) || (endtime.equalsIgnoreCase("")) || (!isTimeTrue(endtime)))
/*      */       {
/*  716 */         AMLog.debug("REST API : The endTime should be of the format (HH:MM).");
/*  717 */         message = FormatUtil.getString("am.webclient.api.mainendtime.msg");
/*  718 */         errorCode = "4039";
/*  719 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  720 */         return outputString;
/*      */       }
/*  722 */       if ((effectfrom == null) || (effectfrom.equalsIgnoreCase("")) || (!isDateTrue(effectfrom)))
/*      */       {
/*  724 */         AMLog.debug("REST API : The effectFrom should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  725 */         message = FormatUtil.getString("am.webclient.api.maineffrom.msg");
/*  726 */         errorCode = "4041";
/*  727 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  728 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  732 */       MSreq.addParameter("checkbox", taskid);
/*  733 */       MSreq.addParameter("userId", request.getRemoteUser());
/*  734 */       am.editMaintenanceTask(null, amform, MSreq, response);
/*  735 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*  736 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*  740 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("weekly")))
/*      */     {
/*  742 */       int totalNumber = 0;
/*      */       try
/*      */       {
/*  745 */         totalNumber = Integer.parseInt(request.getParameter("totalNumber"));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  749 */         AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  750 */         message = FormatUtil.getString("am.webclient.api.maintsknom.msg");
/*  751 */         errorCode = "4043";
/*  752 */         return URITree.generateXML(request, response, message, errorCode);
/*      */       }
/*      */       
/*  755 */       if ((totalNumber >= 1) && (totalNumber <= 7))
/*      */       {
/*  757 */         MSreq.addParameter("numbers", "" + totalNumber);
/*      */       }
/*      */       else
/*      */       {
/*  761 */         AMLog.debug("REST API : The totalNumber should be between 1 to 7 only.");
/*  762 */         message = FormatUtil.getString("am.webclient.api.maintsknom.msg");
/*  763 */         errorCode = "4043";
/*  764 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  765 */         return outputString;
/*      */       }
/*  767 */       if (weeklyCheckTrue(request, totalNumber))
/*      */       {
/*  769 */         if (!startEndWeeklyCheck(request, totalNumber))
/*      */         {
/*  771 */           AMLog.debug("REST API : Check for the date time configuration of weekly.");
/*  772 */           message = FormatUtil.getString("Check for the date time configuration of weekly.");
/*  773 */           errorCode = "4251";
/*  774 */           outputString = URITree.generateXML(request, response, message, errorCode);
/*  775 */           return outputString;
/*      */         }
/*  777 */         for (int i = 0; i < totalNumber; i++)
/*      */         {
/*  779 */           MSreq.addParameter("startDay(" + i + ")", getDayVal(request.getParameter("startDay" + (i + 1))));
/*  780 */           MSreq.addParameter("startTime(" + i + ")", request.getParameter("startTime" + (i + 1)));
/*  781 */           MSreq.addParameter("endDay(" + i + ")", getDayVal(request.getParameter("endDay" + (i + 1))));
/*  782 */           MSreq.addParameter("endTime(" + i + ")", request.getParameter("endTime" + (i + 1)));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  787 */         AMLog.debug("REST API : The specified request URI is incorrect: startDay,startDay,startDay,endTime for daily Maintenance are incorrect");
/*  788 */         message = FormatUtil.getString("am.webclient.api.mainweek.msg");
/*  789 */         errorCode = "4044";
/*  790 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  791 */         return outputString;
/*      */       }
/*  793 */       String effectfrom = request.getParameter("taskEffectFrom");
/*  794 */       if ((taskstatus != null) && (effectfrom != null))
/*      */       {
/*  796 */         MSreq.addParameter("checkbox", taskid);
/*  797 */         am.deleteMaintenanceTask(null, null, MSreq, response);
/*  798 */         am.editMaintenanceTask(null, amform, MSreq, response);
/*  799 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*  800 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  804 */       AMLog.debug("REST API : The effectFrom should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  805 */       message = FormatUtil.getString("am.webclient.api.maineffrom.msg");
/*  806 */       errorCode = "4041";
/*  807 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*  808 */       return outputString;
/*      */     }
/*      */     
/*  811 */     if ((taskmethod != null) && (taskmethod.equalsIgnoreCase("once")))
/*      */     {
/*  813 */       if ((request.getParameter("customTaskStartTime") == null) || (!isDateTrue(request.getParameter("customTaskStartTime"))))
/*      */       {
/*  815 */         AMLog.debug("REST API : The customTaskStartTime should be of the format (YYYY-MM-DD HH:MM).");
/*  816 */         message = FormatUtil.getString("am.webclient.api.maincusstrtime.msg");
/*  817 */         outputString = URITree.generateXML(request, response, message, "4044");
/*  818 */         return outputString;
/*      */       }
/*  820 */       if ((request.getParameter("customTaskEndTime") == null) || (!isDateTrue(request.getParameter("customTaskEndTime"))))
/*      */       {
/*  822 */         AMLog.debug("REST API : The customTaskEndTime should be a valid date format like (YYYY-MM-DD HH:MM).");
/*  823 */         message = FormatUtil.getString("am.webclient.api.maincusendtime.msg");
/*  824 */         outputString = URITree.generateXML(request, response, message, "4045");
/*  825 */         return outputString;
/*      */       }
/*  827 */       if (!customStartEndDateTimeCheck(request.getParameter("customTaskStartTime"), request.getParameter("customTaskEndTime")))
/*      */       {
/*  829 */         AMLog.debug("REST API : The customTaskStartTime should be less than customTaskEndTime.");
/*  830 */         message = FormatUtil.getString("The customTaskStartTime should be less than customTaskEndTime.");
/*  831 */         outputString = URITree.generateXML(request, response, message, "4252");
/*  832 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*  836 */       MSreq.addParameter("checkbox", taskid);
/*  837 */       am.deleteMaintenanceTask(null, null, MSreq, response);
/*  838 */       am.createMaintenanceTask(null, amform, MSreq, response);
/*  839 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.maineditsucc.msg"), errorCode, true);
/*  840 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  845 */     AMLog.debug("REST API : The specified request URI is incorrect:the taskMethod is not specified or wrong. taskMethod should be either daily or weekly or once");
/*  846 */     message = FormatUtil.getString("am.webclient.api.maintskmth.msg");
/*  847 */     errorCode = "4042";
/*  848 */     outputString = URITree.generateXML(request, response, message, errorCode);
/*  849 */     return outputString;
/*      */   }
/*      */   
/*      */   public static boolean validateMaintenanceTask(HttpServletRequest request, HttpServletResponse response, String valueName, String method)
/*      */     throws Exception
/*      */   {
/*  855 */     ResultSet rs = null;
/*  856 */     boolean result = false;
/*  857 */     AMLog.debug("REST API : inside validateMaintenanceTask");
/*  858 */     String checkquery = "";
/*      */     try
/*      */     {
/*  861 */       if ((method.equals("DeleteMaintenanceTask")) || (method.equals("EditMaintenanceTask")))
/*      */       {
/*  863 */         checkquery = "select TASKID from AM_TASKIDRESOURCEIDMAPPER where TASKID in (" + valueName + ")";
/*      */       }
/*  865 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/*  866 */       if (rs.next())
/*      */       {
/*  868 */         result = true;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  873 */       ex.printStackTrace();
/*  874 */       AMLog.debug("REST API : ValidateMaintenanceTask: Server error");
/*      */     }
/*      */     finally
/*      */     {
/*  878 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  880 */     return result;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isTimeTrue(String timeValue)
/*      */     throws Exception
/*      */   {
/*  923 */     String[] TimeArr = timeValue.split(":");
/*  924 */     int HourInt = Integer.parseInt(TimeArr[0]);
/*  925 */     int MinInt = Integer.parseInt(TimeArr[1]);
/*  926 */     if ((24 > HourInt) && (HourInt >= 0) && (60 > MinInt) && (MinInt >= 0))
/*      */     {
/*  928 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  932 */     AMLog.debug("REST API : not a proper time");
/*  933 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isDateTrue(String dateValue)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  941 */       String[] DateArr = dateValue.split(" ");
/*  942 */       String timeVal = DateArr[1];
/*  943 */       String[] dateVal = DateArr[0].split("-");
/*  944 */       int yearVal = Integer.parseInt(dateVal[0]);
/*  945 */       int monthVal = Integer.parseInt(dateVal[1]);
/*  946 */       int dayVal = Integer.parseInt(dateVal[2]);
/*  947 */       if (((monthVal == 1) || (monthVal == 3) || (monthVal == 5) || (monthVal == 7) || (monthVal == 8) || (monthVal == 10) || (monthVal == 12)) && (32 > dayVal) && (dayVal > 0) && (isTimeTrue(timeVal)))
/*      */       {
/*  949 */         return true;
/*      */       }
/*  951 */       if (((monthVal == 4) || (monthVal == 6) || (monthVal == 9) || (monthVal == 11)) && (31 > dayVal) && (dayVal > 0) && (isTimeTrue(timeVal)))
/*      */       {
/*  953 */         return true;
/*      */       }
/*  955 */       if ((monthVal == 2) && (29 > dayVal) && (dayVal > 0) && (yearVal % 4 != 0) && (isTimeTrue(timeVal)))
/*      */       {
/*  957 */         return true;
/*      */       }
/*  959 */       if ((monthVal == 2) && (30 > dayVal) && (dayVal > 0) && (yearVal % 4 == 0) && (isTimeTrue(timeVal)))
/*      */       {
/*  961 */         return true;
/*      */       }
/*      */       
/*      */ 
/*  965 */       AMLog.debug("REST API : not a proper date");
/*  966 */       return false;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  971 */       ex.printStackTrace();
/*  972 */       AMLog.debug("REST API : date time error"); }
/*  973 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isResTrue(String resid, String methodName, String type)
/*      */     throws Exception
/*      */   {
/*  979 */     boolean BlnManaged = false;
/*  980 */     int count = 0;
/*  981 */     String checkquery = "";
/*  982 */     String[] resourceid = resid.split(",");
/*  983 */     if ((methodName.equalsIgnoreCase("CreateMaintenanceTasK")) || (methodName.equalsIgnoreCase("EditMaintenanceTasK")))
/*      */     {
/*  985 */       if (1 <= resourceid.length)
/*      */       {
/*  987 */         if (type.equalsIgnoreCase("monitor"))
/*      */         {
/*  989 */           checkquery = "select count(RESOURCEID) from AM_ManagedObject where TYPE in " + Constants.resourceTypes + " and RESOURCEID in (" + resid + ")";
/*      */         }
/*  991 */         else if (type.equalsIgnoreCase("group"))
/*      */         {
/*  993 */           checkquery = "select count(RESOURCEID) from AM_ManagedObject where TYPE = 'HAI' and RESOURCEID in (" + resid + ")";
/*      */         }
/*      */         
/*  996 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  999 */           rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 1000 */           while (rs.next())
/*      */           {
/* 1002 */             count = rs.getInt(1);
/*      */           }
/* 1004 */           if (resourceid.length == count)
/*      */           {
/* 1006 */             BlnManaged = true;
/*      */           }
/*      */           else
/*      */           {
/* 1010 */             BlnManaged = false;
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1015 */           ex.printStackTrace();
/* 1016 */           AMLog.debug("REST API : Server error");
/* 1017 */           BlnManaged = false;
/*      */         }
/*      */         finally
/*      */         {
/* 1021 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1026 */         BlnManaged = false;
/*      */       }
/*      */     }
/* 1029 */     return BlnManaged;
/*      */   }
/*      */   
/*      */   public static boolean isAuthorized(String resid, HttpServletRequest request)
/*      */   {
/* 1034 */     Vector<String> resIds_owned = DelegatedUserRoleUtil.getResIDsForUser(request);
/* 1035 */     String[] resourceid = resid.split(",");
/* 1036 */     Vector<String> resources = new Vector(Arrays.asList(resourceid));
/* 1037 */     if (!resIds_owned.containsAll(resources))
/*      */     {
/* 1039 */       return false;
/*      */     }
/* 1041 */     return true;
/*      */   }
/*      */   
/*      */   public static boolean isTaskNameExist(String taskName) throws Exception
/*      */   {
/* 1046 */     ResultSet rs = null;
/* 1047 */     boolean task = false;
/* 1048 */     int count = -1;
/* 1049 */     String checkquery = "select count(TASKNAME) from AM_MAINTENANCECONFIG where TASKNAME='" + taskName + "'";
/*      */     try
/*      */     {
/* 1052 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 1053 */       while (rs.next())
/*      */       {
/* 1055 */         count = rs.getInt(1);
/*      */       }
/* 1057 */       if (count == 0)
/*      */       {
/* 1059 */         task = true;
/*      */       }
/* 1061 */       else if ((count == -1) || (count == 1))
/*      */       {
/* 1063 */         task = false;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1068 */       ex.printStackTrace();
/* 1069 */       AMLog.debug("REST API : Server error");
/* 1070 */       task = false;
/*      */     }
/*      */     finally
/*      */     {
/* 1074 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1076 */     return task;
/*      */   }
/*      */   
/*      */   public static boolean editTaskNameCheck(String taskName, String taskid) throws Exception
/*      */   {
/* 1081 */     int count = -1;
/* 1082 */     ResultSet rs = null;
/* 1083 */     boolean task = false;
/* 1084 */     String checkquery = "select count(TASKNAME) from AM_MAINTENANCECONFIG where TASKNAME='" + taskName + "' and TASKID !='" + taskid + "'";
/*      */     try
/*      */     {
/* 1087 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 1088 */       while (rs.next())
/*      */       {
/* 1090 */         count = rs.getInt(1);
/*      */       }
/* 1092 */       if (count == 0)
/*      */       {
/* 1094 */         task = true;
/*      */       }
/* 1096 */       else if ((count == -1) || (count == 1))
/*      */       {
/* 1098 */         task = false;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1103 */       ex.printStackTrace();
/* 1104 */       AMLog.debug("REST API : Server error");
/* 1105 */       task = false;
/*      */     }
/*      */     finally
/*      */     {
/* 1109 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1111 */     return task;
/*      */   }
/*      */   
/*      */   public static boolean weeklyCheckTrue(HttpServletRequest request, int totalNumber) throws Exception
/*      */   {
/* 1116 */     boolean task = true;
/*      */     try
/*      */     {
/* 1119 */       for (int i = 0; i < totalNumber; i++)
/*      */       {
/* 1121 */         if ((request.getParameter("endTime" + (i + 1)) != null) && (request.getParameter("endDay" + (i + 1)) != null) && (request.getParameter("startTime" + (i + 1)) != null) && (request.getParameter("startDay" + (i + 1)) != null) && (isTimeTrue(request.getParameter("endTime" + (i + 1)))) && (isTimeTrue(request.getParameter("startTime" + (i + 1)))) && (isDayTrue(request.getParameter("endDay" + (i + 1)))) && (isDayTrue(request.getParameter("startDay" + (i + 1)))))
/*      */         {
/* 1123 */           AMLog.debug("REST API : ");
/*      */         }
/*      */         else
/*      */         {
/* 1127 */           task = false;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1133 */       ex.printStackTrace();
/* 1134 */       task = false;
/*      */     }
/* 1136 */     return task;
/*      */   }
/*      */   
/*      */   public static String getDayVal(String dayValue) throws Exception
/*      */   {
/* 1141 */     String Val = "";
/* 1142 */     if (dayValue.equalsIgnoreCase("Sunday"))
/*      */     {
/* 1144 */       Val = "1";
/*      */     }
/* 1146 */     else if (dayValue.equalsIgnoreCase("Monday"))
/*      */     {
/* 1148 */       Val = "2";
/*      */     }
/* 1150 */     else if (dayValue.equalsIgnoreCase("Tuesday"))
/*      */     {
/* 1152 */       Val = "3";
/*      */     }
/* 1154 */     else if (dayValue.equalsIgnoreCase("Wednesday"))
/*      */     {
/* 1156 */       Val = "4";
/*      */     }
/* 1158 */     else if (dayValue.equalsIgnoreCase("Thursday"))
/*      */     {
/* 1160 */       Val = "5";
/*      */     }
/* 1162 */     else if (dayValue.equalsIgnoreCase("Friday"))
/*      */     {
/* 1164 */       Val = "6";
/*      */     }
/* 1166 */     else if (dayValue.equalsIgnoreCase("Saturday"))
/*      */     {
/* 1168 */       Val = "7";
/*      */     }
/* 1170 */     return Val;
/*      */   }
/*      */   
/*      */   public static boolean isDayTrue(String dayValue) throws Exception
/*      */   {
/* 1175 */     if ((dayValue.equalsIgnoreCase("Sunday")) || (dayValue.equalsIgnoreCase("Monday")) || (dayValue.equalsIgnoreCase("Tuesday")) || (dayValue.equalsIgnoreCase("Wednesday")) || (dayValue.equalsIgnoreCase("Thursday")) || (dayValue.equalsIgnoreCase("Friday")) || (dayValue.equalsIgnoreCase("Saturday")))
/*      */     {
/* 1177 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1181 */     AMLog.debug("REST API : wrong day");
/* 1182 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean startEndTimeCheck(String startDay, String endDay)
/*      */     throws Exception
/*      */   {
/* 1188 */     String[] startArr = startDay.split(":");
/* 1189 */     String[] endArr = endDay.split(":");
/* 1190 */     int startHourInt = Integer.parseInt(startArr[0]);
/* 1191 */     int startMinInt = Integer.parseInt(startArr[1]);
/* 1192 */     int endHourInt = Integer.parseInt(endArr[0]);
/* 1193 */     int endMinInt = Integer.parseInt(endArr[1]);
/* 1194 */     if (startHourInt < endHourInt)
/*      */     {
/* 1196 */       return true;
/*      */     }
/* 1198 */     if ((startHourInt == endHourInt) && (startMinInt < endMinInt))
/*      */     {
/* 1200 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1204 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean customStartEndDateTimeCheck(String start, String end)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1212 */       String[] startDateArr = start.split(" ");
/* 1213 */       String[] startDateVal = startDateArr[0].split("-");
/* 1214 */       int startYearVal = Integer.parseInt(startDateVal[0]);
/* 1215 */       int startMonthVal = Integer.parseInt(startDateVal[1]);
/* 1216 */       int startDayVal = Integer.parseInt(startDateVal[2]);
/*      */       
/* 1218 */       String[] startTimeArr = startDateArr[1].split(":");
/* 1219 */       int startHourVal = Integer.parseInt(startTimeArr[0]);
/* 1220 */       int startMinVal = Integer.parseInt(startTimeArr[1]);
/*      */       
/* 1222 */       String[] endDateArr = end.split(" ");
/* 1223 */       String[] endDateVal = endDateArr[0].split("-");
/* 1224 */       int endYearVal = Integer.parseInt(endDateVal[0]);
/* 1225 */       int endMonthVal = Integer.parseInt(endDateVal[1]);
/* 1226 */       int endDayVal = Integer.parseInt(endDateVal[2]);
/*      */       
/* 1228 */       String[] endTimeArr = endDateArr[1].split(":");
/* 1229 */       int endHourVal = Integer.parseInt(endTimeArr[0]);
/* 1230 */       int endMinVal = Integer.parseInt(endTimeArr[1]);
/*      */       
/* 1232 */       if (startYearVal > endYearVal)
/*      */       {
/* 1234 */         return false;
/*      */       }
/* 1236 */       if ((startYearVal == endYearVal) && (startMonthVal > endMonthVal))
/*      */       {
/* 1238 */         return false;
/*      */       }
/* 1240 */       if ((startYearVal == endYearVal) && (startMonthVal == endMonthVal) && (startDayVal > endDayVal))
/*      */       {
/* 1242 */         return false;
/*      */       }
/* 1244 */       if ((startYearVal == endYearVal) && (startMonthVal == endMonthVal) && (startDayVal == endDayVal) && (startHourVal > endHourVal))
/*      */       {
/* 1246 */         return false;
/*      */       }
/* 1248 */       if ((startYearVal == endYearVal) && (startMonthVal == endMonthVal) && (startDayVal == endDayVal) && (startHourVal == endHourVal) && (startMinVal > endMinVal))
/*      */       {
/* 1250 */         return false;
/*      */       }
/* 1252 */       if ((startYearVal == endYearVal) && (startMonthVal == endMonthVal) && (startDayVal == endDayVal) && (startHourVal == endHourVal) && (startMinVal == endMinVal))
/*      */       {
/* 1254 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1258 */       return true;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1263 */       ex.printStackTrace();
/* 1264 */       AMLog.debug("REST API : date time error"); }
/* 1265 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean startEndWeeklyCheck(HttpServletRequest request, int totalNumber)
/*      */     throws Exception
/*      */   {
/* 1271 */     boolean task = true;
/*      */     try
/*      */     {
/* 1274 */       for (int i = 0; i < totalNumber; i++)
/*      */       {
/* 1276 */         String startTime = request.getParameter("startTime" + (i + 1));
/* 1277 */         String startDay = request.getParameter("startDay" + (i + 1));
/* 1278 */         String endTime = request.getParameter("endTime" + (i + 1));
/* 1279 */         String endDay = request.getParameter("endDay" + (i + 1));
/* 1280 */         if ((startDay.equals(endDay)) && (!startEndTimeCheck(startTime, endTime)))
/*      */         {
/* 1282 */           task = false;
/*      */         }
/*      */         else
/*      */         {
/* 1286 */           task = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1292 */       ex.printStackTrace();
/* 1293 */       task = false;
/*      */     }
/* 1295 */     return task;
/*      */   }
/*      */   
/*      */   public static boolean validateTaskidTaskmethod(String taskid, String tasktype) throws Exception
/*      */   {
/* 1300 */     ResultSet rs = null;
/* 1301 */     boolean task = false;
/* 1302 */     int count = -1;
/* 1303 */     String checkquery = "select count(*) from AM_MAINTENANCECONFIG where TASKID='" + taskid + "' and TYPE='" + tasktype + "'";
/*      */     
/*      */     try
/*      */     {
/* 1307 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 1308 */       while (rs.next())
/*      */       {
/* 1310 */         count = rs.getInt(1);
/*      */       }
/* 1312 */       if (count >= 1)
/*      */       {
/* 1314 */         task = true;
/*      */       }
/*      */       else
/*      */       {
/* 1318 */         task = false;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1323 */       ex.printStackTrace();
/* 1324 */       AMLog.debug("REST API : Server error");
/* 1325 */       task = false;
/*      */     }
/*      */     finally
/*      */     {
/* 1329 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1331 */     return task;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\MaintenanceTaskAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */