/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.SmtpMailer;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.framework.reporting.ScheduleReportThread;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.json.JSONObject;
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
/*      */ public class ScheduleReportAction
/*      */   extends DispatchAction
/*      */ {
/*   87 */   private static Log log = LogFactory.getLog("WebClient");
/*      */   
/*   89 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*   93 */   private static String htmlMailTpl = getHTMLMailTpl();
/*      */   
/*      */ 
/*      */   private final void getActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   99 */     ResultSet set = null;
/*  100 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  101 */     Vector<String> actionIdList = new Vector();
/*  102 */     String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/*  103 */     boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*  104 */     if (isDelegatedAdmin) {
/*  105 */       Vector<String> resIds = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(DBUtil.getUserID(request.getRemoteUser()));
/*      */       
/*  107 */       if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/*  108 */         actionIdList = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(DelegatedUserRoleUtil.getLoginUserid(request), 2);
/*      */       }
/*      */       else {
/*  111 */         actionIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(DelegatedUserRoleUtil.getLoginUserid(request), 2);
/*      */       }
/*      */       
/*      */ 
/*  115 */       String qry = "select ACTIONID from AM_ATTRIBUTEACTIONMAPPER where " + DBUtil.getCondition("AM_ATTRIBUTEACTIONMAPPER.ID", resIds) + " and AM_ATTRIBUTEACTIONMAPPER.ACTIONID not in ";
/*  116 */       actionIdList = DelegatedUserRoleUtil.getCompleteConfigIds(qry, actionIdList);
/*  117 */       query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND " + DBUtil.getCondition("AM_EMAILACTION.ID", actionIdList);
/*      */     }
/*  119 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  121 */       query = query + " and AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */     }
/*      */     try
/*      */     {
/*  125 */       set = AMConnectionPool.executeQueryStmt(query);
/*  126 */       AMLog.debug("Schedule Reports : " + query);
/*  127 */       ArrayList rows = new ArrayList();
/*  128 */       while (set.next())
/*      */       {
/*  130 */         String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/*  131 */         Properties dataProps = new Properties();
/*  132 */         dataProps.setProperty("label", labelvalue);
/*  133 */         dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/*  134 */         rows.add(dataProps);
/*      */       }
/*  136 */       set.close();
/*  137 */       String quer = "Select ID,Name from AM_BUSINESSHOURSDETAILS";
/*  138 */       ArrayList bh = new ArrayList();
/*  139 */       set = AMConnectionPool.executeQueryStmt(quer);
/*      */       
/*  141 */       while (set.next()) {
/*  142 */         Properties dataProps1 = new Properties();
/*  143 */         dataProps1.setProperty("label", set.getString("Name"));
/*  144 */         dataProps1.setProperty("value", set.getString("ID"));
/*  145 */         bh.add(dataProps1);
/*      */       }
/*  147 */       ((AMActionForm)form).setApplications(rows);
/*  148 */       ((AMActionForm)form).setBusinessrules(bh);
/*      */     }
/*      */     catch (Exception exp) {
/*  151 */       AMLog.fatal("Schedule Reports :  Exception ", exp);
/*  152 */       exp.printStackTrace();
/*  153 */       throw new Exception(exp);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward newScheduleReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  163 */       ManagedApplication mo = new ManagedApplication();
/*      */       
/*  165 */       request.setAttribute("mosize", getMoSize());
/*      */       
/*  167 */       getActions(mapping, form, request, response);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  172 */       e.printStackTrace();
/*      */     }
/*  174 */     return new ActionForward("/jsp/ScheduleReportDetails.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showScheduleReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  184 */     if ((Constants.getUserType() != null) && (Constants.getUserType().equals("F")))
/*      */     {
/*  186 */       request.setAttribute("tabtoselect", "6");
/*  187 */       request.setAttribute("helpkey", "showScheduleReports");
/*  188 */       return new ActionForward("/jsp/helpmessages_container.jsp");
/*      */     }
/*  190 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  191 */     AMActionForm am = (AMActionForm)form;
/*  192 */     ActionMessages messages = new ActionMessages();
/*  193 */     ActionErrors errors = new ActionErrors();
/*  194 */     ComparingSla cs = new ComparingSla();
/*      */     
/*      */     try
/*      */     {
/*  198 */       getActions(mapping, form, request, response);
/*  199 */       String query = "SELECT AM_SCHEDULEDETAILS.ID,NAME,STATUS,REPORTPERIOD,SCHEDULEPERIOD,NEXTSCHEDULE,SCHEDULEHOUR,SCHEDULEMINUTE,LASTEXECUTED,COMMENTS ,CASE  WHEN AM_UserPasswordTable.USERNAME IS NULL THEN 'admin' ELSE AM_UserPasswordTable.USERNAME END as USERNAME from AM_SCHEDULEDETAILS left outer join AM_SCHEDULERTIME on AM_SCHEDULEDETAILS.ID=AM_SCHEDULERTIME.SCHEDULEID left outer join AM_CONFIGTOUSER_MAPPING on (AM_SCHEDULEDETAILS.ID=AM_CONFIGTOUSER_MAPPING.CONFIGID and AM_CONFIGTOUSER_MAPPING.CONFIGTYPE=6) left outer join AM_UserPasswordTable on AM_CONFIGTOUSER_MAPPING.USERID=AM_UserPasswordTable.USERID order by UPPER(NAME)";
/*  200 */       String userName = request.getRemoteUser();
/*  201 */       if (DBUtil.isDelegatedAdmin(userName)) {
/*  202 */         int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/*  203 */         Vector scheduleIDsVec = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userID, 6);
/*  204 */         query = "SELECT AM_SCHEDULEDETAILS.ID,NAME,STATUS,REPORTPERIOD,SCHEDULEPERIOD,NEXTSCHEDULE,SCHEDULEHOUR,SCHEDULEMINUTE,LASTEXECUTED,COMMENTS   FROM AM_SCHEDULEDETAILS,AM_SCHEDULERTIME where AM_SCHEDULEDETAILS.ID=SCHEDULEID AND " + DBUtil.getCondition("AM_SCHEDULEDETAILS.ID", scheduleIDsVec) + " ORDER BY UPPER(NAME)";
/*      */       }
/*  206 */       AMLog.debug("Show Schedule Reports query : " + query);
/*  207 */       ArrayList scheduleList = this.mo.getRows(query);
/*  208 */       ArrayList row = new ArrayList();
/*  209 */       for (int i = 0; i < scheduleList.size(); i++)
/*      */       {
/*  211 */         ArrayList eachROW = (ArrayList)scheduleList.get(i);
/*  212 */         ArrayList data = new ArrayList();
/*  213 */         String id = (String)eachROW.get(0);
/*  214 */         String name = (String)eachROW.get(1);
/*  215 */         String status = (String)eachROW.get(2);
/*  216 */         String reportperiod = (String)eachROW.get(3);
/*  217 */         String scheduleperiod = (String)eachROW.get(4);
/*  218 */         String schedulehour = (String)eachROW.get(6);
/*  219 */         String scheduleminute = (String)eachROW.get(7);
/*      */         
/*  221 */         long lastexecTime = Long.parseLong((String)eachROW.get(8));
/*  222 */         String comments = (String)eachROW.get(9);
/*  223 */         String createdUser = new String();
/*      */         
/*  225 */         String reporttype = getReportTypeForDisplay(id);
/*  226 */         String periodvalue = cs.getValueForPeriod(reportperiod);
/*  227 */         if ("MS SQL Performance Report".equalsIgnoreCase(FormatUtil.getString(reporttype))) {
/*  228 */           if ("0".equals(reportperiod)) {
/*  229 */             periodvalue = FormatUtil.getString("am.webclient.common.toppolledvalues.text");
/*      */           } else {
/*  231 */             periodvalue = FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { reportperiod });
/*      */           }
/*  233 */           periodvalue = periodvalue.concat(" ").concat(FormatUtil.getString("Values"));
/*      */         }
/*  235 */         data.add(id);
/*  236 */         data.add(name);
/*  237 */         data.add(status);
/*  238 */         data.add(reporttype);
/*  239 */         data.add(periodvalue);
/*  240 */         data.add(scheduleperiod);
/*  241 */         data.add(schedulehour);
/*  242 */         data.add(scheduleminute);
/*  243 */         Date lastExecDate = new Date(lastexecTime);
/*  244 */         if (lastexecTime != -1L)
/*      */         {
/*  246 */           data.add(lastExecDate.toString().substring(4, 16));
/*      */         }
/*      */         else
/*      */         {
/*  250 */           data.add("-");
/*      */         }
/*  252 */         if (comments != null)
/*      */         {
/*  254 */           data.add(comments);
/*      */         }
/*      */         else
/*      */         {
/*  258 */           data.add("-");
/*      */         }
/*  260 */         if (eachROW.size() == 11) {
/*  261 */           createdUser = (String)eachROW.get(10);
/*  262 */           data.add(createdUser);
/*      */         }
/*  264 */         row.add(data);
/*      */       }
/*  266 */       request.setAttribute("data", row);
/*  267 */       request.setAttribute("tabtoselect", "3");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  271 */       e.printStackTrace();
/*      */     }
/*      */     
/*  274 */     return new ActionForward("/jsp/ShowScheduleReportsDetails.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward addScheduleDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  281 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  282 */     AMActionForm am = (AMActionForm)form;
/*  283 */     ActionMessages messages = new ActionMessages();
/*  284 */     ActionErrors errors = new ActionErrors();
/*  285 */     String name = am.getTaskName();
/*  286 */     boolean isname = false;
/*  287 */     ResultSet rs1 = null;
/*  288 */     String error = null;
/*      */     
/*      */     try
/*      */     {
/*  292 */       String query = "SELECT ID FROM AM_SCHEDULEDETAILS where NAME='" + name + "'";
/*  293 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/*  294 */       if (rs1.next())
/*      */       {
/*  296 */         error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { name });
/*  297 */         isname = true;
/*      */       }
/*  299 */       rs1.close();
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  304 */       es.printStackTrace();
/*      */     }
/*  306 */     if (isname)
/*      */     {
/*  308 */       request.setAttribute("message", error);
/*  309 */       String[] resourcestypes1 = am.getResourcestypes();
/*  310 */       am.setResourcestypes(resourcestypes1);
/*  311 */       return new ActionForward("/scheduleReports.do?method=newScheduleReports&edit=check");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  318 */       String description = am.getTaskDescription();
/*  319 */       String status = am.getTaskStatus();
/*  320 */       String reporttype = am.getTypeofreport();
/*  321 */       String forecastTrend = am.getForecastTrend();
/*  322 */       String period = am.getTypeofperiod();
/*  323 */       String businessrule = am.getBusinessPeriod();
/*  324 */       String monitortype = am.getReportmonitor();
/*  325 */       String tempMonitorType = monitortype;
/*  326 */       String attribute = "availability";
/*  327 */       if ((reporttype != null) && (reporttype.equalsIgnoreCase("attribute")))
/*      */       {
/*  329 */         attribute = am.getTypeofattribute();
/*      */       }
/*  331 */       if ("forecast".equalsIgnoreCase(reporttype))
/*      */       {
/*  333 */         attribute = am.getTypeofforecastattribute();
/*  334 */         period = am.getForecastTypeofperiod();
/*      */       }
/*  336 */       if ("availabilitytrend".equals(reporttype)) {
/*  337 */         period = am.getTypeoftrendperiod();
/*      */       }
/*  339 */       if ("availabilitytrenddowntime".equals(reporttype)) {
/*  340 */         period = am.getTypeoftrendperiod();
/*      */       }
/*  342 */       if ("weeklyoutage".equals(reporttype)) {
/*  343 */         period = am.getTypeofoutageperiod();
/*      */       }
/*  345 */       if ("sqlperformance".equals(reporttype)) {
/*  346 */         period = am.getTypeofperformanceperiod();
/*  347 */         attribute = "sqlperformance#" + am.getTypeofperformance() + "&" + am.getSqlDBforPerformance();
/*      */       }
/*      */       
/*  350 */       if (("eumGlancereport".equals(reporttype)) || ("summary".equals(reporttype)) || ("downtime".equals(reporttype)) || ("custom".equals(reporttype)) || ("sqlperformance".equals(reporttype)) || (("glancereport".equalsIgnoreCase(reporttype)) && ("indimonitor".equals(monitortype)))) {
/*  351 */         monitortype = "monitor";
/*      */       }
/*  353 */       String timetogenerate = am.getTaskMethod();
/*  354 */       String dailyhour = am.getDailyhour();
/*  355 */       String dailymin = am.getDailyminute();
/*  356 */       String[] days = am.getDays();
/*  357 */       String[] months = am.getMonths();
/*  358 */       String allmonths = am.getMode();
/*  359 */       String sendactionas = am.getActionsEnabled();
/*  360 */       String sendReportDelivery = am.getReportDelivery();
/*  361 */       String sendmail = am.getSendmail();
/*  362 */       String[] resourcestypes = am.getResourcestypes();
/*      */       
/*  364 */       String customFieldId = request.getParameter("filterScheduleReport") != null ? request.getParameter("filterScheduleReport").substring(request.getParameter("filterScheduleReport").indexOf("$") + 1) : "";
/*  365 */       String customFieldValueId = request.getParameter("selectFieldVal") != null ? request.getParameter("selectFieldVal").substring(request.getParameter("selectFieldVal").indexOf("$") + 1) : "";
/*      */       
/*  367 */       if ((Constants.sqlManager) && (resourcestypes.length == 0) && (tempMonitorType.equalsIgnoreCase("monitor")))
/*      */       {
/*  369 */         resourcestypes = new String[] { "MSSQL-DB-server", "Windows 2003" };
/*      */       }
/*      */       
/*  372 */       if ((Constants.sqlManager) && (reporttype.equals("glancereport")) && (tempMonitorType.equalsIgnoreCase("monitor")))
/*      */       {
/*  374 */         resourcestypes = new String[] { "MSSQL-DB-server", "Windows 2003" };
/*      */       }
/*      */       
/*  377 */       for (int i = 0; i < resourcestypes.length; i++) {
/*  378 */         String s = resourcestypes[i];
/*  379 */         if ((s.equals("WEBLOGIC-Integration")) || (s.equals("Middleware Servers"))) {
/*  380 */           if ("thread".equals(attribute)) {
/*  381 */             attribute = "wlithread";
/*  382 */           } else if ("session".equals(attribute)) {
/*  383 */             attribute = "wlisession";
/*  384 */           } else if ("jdbc".equals(attribute)) {
/*  385 */             attribute = "wlijdbc";
/*  386 */           } else if ("jvm".equals(attribute)) {
/*  387 */             attribute = "wlijvm";
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  393 */       if ("sla".equals(reporttype)) {
/*  394 */         monitortype = am.getSlatype();
/*  395 */         attribute = "sla";
/*      */       }
/*  397 */       if ("dashboard".equals(reporttype)) {
/*  398 */         attribute = "dashboard";
/*  399 */         monitortype = "";
/*      */       }
/*  401 */       String firstquery = "INSERT INTO AM_SCHEDULEDETAILS(ID,NAME,DESCRIPTION,STATUS,REPORTPERIOD,SCHEDULEPERIOD,RESOURCEGROUP,BUSINESSRULE)  VALUES (" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULEDETAILS") + ", '" + name + "','" + description + "','" + status + "','" + period + "','" + timetogenerate + "','" + monitortype + "','" + businessrule + "')";
/*  402 */       if ((customFieldId != null) && (!customFieldId.trim().equals("")) && (customFieldValueId != null) && (!customFieldValueId.trim().equals(""))) {
/*  403 */         firstquery = "INSERT INTO AM_SCHEDULEDETAILS(ID,NAME,DESCRIPTION,STATUS,REPORTPERIOD,SCHEDULEPERIOD,RESOURCEGROUP,BUSINESSRULE,CUSTOMFIELDID,CUSTOMFIELDVALUEID)  VALUES (" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULEDETAILS") + ", '" + name + "','" + description + "','" + status + "','" + period + "','" + timetogenerate + "','" + monitortype + "','" + businessrule + "','" + customFieldId + "','" + customFieldValueId + "')";
/*      */       }
/*      */       
/*  406 */       AMConnectionPool.executeUpdateStmt(firstquery);
/*  407 */       String sid = null;
/*  408 */       String secondquery = "SELECT ID FROM AM_SCHEDULEDETAILS WHERE NAME='" + name + "'";
/*  409 */       long scheduletime = getTimeInMillis(dailyhour, dailymin);
/*  410 */       String timeinformat = ReportUtilities.format(scheduletime);
/*      */       
/*  412 */       ResultSet rs = AMConnectionPool.executeQueryStmt(secondquery);
/*  413 */       if (rs.next())
/*      */       {
/*  415 */         sid = rs.getString("ID");
/*      */       }
/*  417 */       rs.close();
/*  418 */       int scheduleid = Integer.parseInt(sid);
/*      */       
/*  420 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  422 */         String insCustQuery = "insert into SCHEDULEDETAILS_CUSTOMER values(" + scheduleid + ",'" + request.getParameter("organization") + "','" + request.getParameter("haid") + "')";
/*  423 */         AMConnectionPool.executeUpdateStmt(insCustQuery);
/*      */       }
/*      */       
/*      */ 
/*  427 */       if (timetogenerate != null)
/*      */       {
/*  429 */         String timetoschedulequery = "INSERT INTO AM_SCHEDULERTIME (ID,SCHEDULEID,TIMETOSCHEDULE,SCHEDULEHOUR,SCHEDULEMINUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULERTIME") + "," + scheduleid + ",'" + scheduletime + "','" + dailyhour + "','" + dailymin + "')";
/*      */         
/*  431 */         AMConnectionPool.executeUpdateStmt(timetoschedulequery);
/*  432 */         if (timetogenerate.equalsIgnoreCase("Daily"))
/*      */         {
/*  434 */           String dailyquery = "INSERT INTO AM_DAILYTIME (ID,SCHEDULEID,SCHEDULETIME) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_DAILYTIME") + "," + scheduleid + ",'" + scheduletime + "')";
/*  435 */           AMConnectionPool.executeUpdateStmt(dailyquery);
/*      */         }
/*  437 */         else if (timetogenerate.equalsIgnoreCase("Weekly"))
/*      */         {
/*  439 */           for (int i = 0; i < days.length; i++)
/*      */           {
/*  441 */             String weeklyquery = "INSERT INTO AM_WEEKLYTIME (ID,SCHEDULEID,DAYSTOSCHEDULE,SCHEDULETIME) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_WEEKLYTIME") + "," + scheduleid + ",'" + days[i] + "','" + scheduletime + "')";
/*  442 */             AMConnectionPool.executeUpdateStmt(weeklyquery);
/*      */           }
/*      */         }
/*  445 */         else if (timetogenerate.equalsIgnoreCase("Monthly"))
/*      */         {
/*  447 */           for (int i = 0; i < months.length; i++)
/*      */           {
/*  449 */             String weeklyquery = "INSERT INTO AM_MONTHLYTIME (ID,SCHEDULEID,DAYSTOSCHEDULE,MONTHTOSCHEDULE,SCHEDULETIME) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_MONTHLYTIME") + "," + scheduleid + ",'" + am.getSelectday() + "','" + months[i] + "','" + scheduletime + "')";
/*  450 */             AMConnectionPool.executeUpdateStmt(weeklyquery);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  458 */       int reportid = getReportType(reporttype, monitortype);
/*  459 */       if ("glancereport".equals(reporttype))
/*      */       {
/*  461 */         reportid = getReportType(reporttype, tempMonitorType);
/*      */       }
/*  463 */       String reportmappingquery = null;
/*  464 */       String actionmappingquery = "INSERT INTO AM_SCHEDULE_ACTION_MAPPING (ID,SCHEDULEID,ACTIONID,REPORTGENERATION,REPORTDELIVERY) VALUES (" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULE_ACTION_MAPPING") + "," + scheduleid + "," + Integer.parseInt(sendmail) + ",'" + sendactionas + "','" + sendReportDelivery + "')";
/*      */       
/*  466 */       AMConnectionPool.executeUpdateStmt(actionmappingquery);
/*  467 */       if ((reporttype.equalsIgnoreCase("downtime")) || (reporttype.equalsIgnoreCase("summary")) || (reporttype.equalsIgnoreCase("event")) || (reporttype.equalsIgnoreCase("hasnapshot")) || (reporttype.equalsIgnoreCase("hasnapshotHost")) || (reporttype.equalsIgnoreCase("sqlperformance")) || (monitortype.equalsIgnoreCase("monitorgroup")) || (reporttype.equalsIgnoreCase("eumSummary")) || (reporttype.equalsIgnoreCase("eumGlancereport")) || ((tempMonitorType.equalsIgnoreCase("indimonitor")) && (reporttype.equalsIgnoreCase("glancereport"))))
/*      */       {
/*      */ 
/*  470 */         if (("hasnapshot".equals(reporttype)) || ("hasnapshotHost".equals(reporttype)) || ("availabilitysnapshot".equals(reporttype)) || ("weeklyoutage".equals(reporttype)) || ("availabilitytrend".equals(reporttype)))
/*      */         {
/*  472 */           String ids = getResourceIDWithComma(resourcestypes);
/*      */           
/*  474 */           reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + ids + "','" + attribute + "')";
/*  475 */           AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*  476 */           String resourcemappingquery = "INSERT INTO AM_SCHEDULER_RESOURCETYPE_MAPPING(ID,RESOURCEID,SCHEDULEID) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_RESOURCETYPE_MAPPING") + ",'" + ids + "'," + scheduleid + ")";
/*  477 */           AMConnectionPool.executeUpdateStmt(resourcemappingquery);
/*      */         }
/*  479 */         else if (reporttype.equalsIgnoreCase("eumSummary")) {
/*  480 */           reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'Null','" + attribute + "')";
/*  481 */           AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*      */         }
/*      */         else
/*      */         {
/*  485 */           if (reporttype.equalsIgnoreCase("sqlperformance")) {
/*  486 */             resourcestypes = new String[] { am.getSelectedMSSQLResource() };
/*      */           }
/*  488 */           for (int k = 0; k < resourcestypes.length; k++)
/*      */           {
/*  490 */             reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + resourcestypes[k] + "','" + attribute + "')";
/*  491 */             if (reporttype.equalsIgnoreCase("forecast")) {
/*  492 */               reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + resourcestypes[k] + "','" + forecastTrend + "','" + attribute + "')";
/*      */             }
/*  494 */             AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*  495 */             String resourcemappingquery = "INSERT INTO AM_SCHEDULER_RESOURCETYPE_MAPPING(ID,RESOURCEID,SCHEDULEID) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_RESOURCETYPE_MAPPING") + ",'" + resourcestypes[k] + "'," + scheduleid + ")";
/*  496 */             AMConnectionPool.executeUpdateStmt(resourcemappingquery);
/*      */           }
/*      */         }
/*      */       }
/*  500 */       else if (reporttype.equalsIgnoreCase("custom"))
/*      */       {
/*      */ 
/*      */ 
/*  504 */         String resourceid = null;
/*  505 */         String attributeid = null;
/*  506 */         String queryinrepmap = null;
/*  507 */         String queryinresmap = null;
/*  508 */         for (int h = 0; h < resourcestypes.length; h++)
/*      */         {
/*  510 */           StringTokenizer st = new StringTokenizer(resourcestypes[h], ":");
/*  511 */           while (st.hasMoreTokens())
/*      */           {
/*  513 */             resourceid = st.nextToken();
/*      */             
/*  515 */             attributeid = st.nextToken();
/*      */           }
/*      */           
/*  518 */           attribute = getCustomAttributeName(attributeid);
/*  519 */           queryinrepmap = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE,ATTRIBUTEID) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + resourceid + "','" + attribute + "'," + Integer.parseInt(attributeid) + ")";
/*  520 */           AMConnectionPool.executeUpdateStmt(queryinrepmap);
/*  521 */           queryinresmap = "INSERT INTO AM_SCHEDULER_RESOURCETYPE_MAPPING(ID,RESOURCEID,SCHEDULEID) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_RESOURCETYPE_MAPPING") + ",'" + resourceid + "'," + scheduleid + ")";
/*  522 */           AMConnectionPool.executeUpdateStmt(queryinresmap);
/*      */         }
/*      */       }
/*  525 */       else if ("sla".equalsIgnoreCase(reporttype))
/*      */       {
/*  527 */         resourcestypes = am.getScheduleReportResCombo2();
/*  528 */         StringBuilder resIds = new StringBuilder();
/*  529 */         for (String str : resourcestypes) {
/*  530 */           resIds.append(str).append(",");
/*      */         }
/*  532 */         reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + resIds.substring(0, resIds.length() - 1) + "','" + attribute + "')";
/*  533 */         AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*  534 */         String resourcemappingquery = "INSERT INTO AM_SCHEDULER_RESOURCETYPE_MAPPING(ID,RESOURCEID,SCHEDULEID) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_RESOURCETYPE_MAPPING") + ",'" + resIds.substring(0, resIds.length() - 1) + "'," + scheduleid + ")";
/*  535 */         AMConnectionPool.executeUpdateStmt(resourcemappingquery);
/*  536 */       } else if ("dashboard".equalsIgnoreCase(reporttype))
/*      */       {
/*  538 */         resourcestypes = am.getScheduleReportDashboardCombo2();
/*  539 */         StringBuilder resIds = new StringBuilder();
/*  540 */         for (String str : resourcestypes) {
/*  541 */           resIds.append(str).append(",");
/*      */         }
/*  543 */         reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + resIds.substring(0, resIds.length() - 1) + "','" + attribute + "')";
/*  544 */         AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  550 */         ArrayList resourceid = getResourceId(resourcestypes);
/*  551 */         for (int j = 0; j < resourceid.size(); j++)
/*      */         {
/*  553 */           String rid = (String)resourceid.get(j);
/*  554 */           String tempAtt = attribute;
/*  555 */           if ("1".equals(rid)) {
/*  556 */             if ("wlithread".equals(attribute)) {
/*  557 */               tempAtt = "thread";
/*  558 */             } else if ("wlisession".equals(attribute)) {
/*  559 */               tempAtt = "session";
/*  560 */             } else if ("wlijdbc".equals(attribute)) {
/*  561 */               tempAtt = "jdbc";
/*  562 */             } else if ("wlijvm".equals(attribute)) {
/*  563 */               tempAtt = "jvm";
/*      */             }
/*      */           }
/*  566 */           reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + rid + "','" + tempAtt + "')";
/*  567 */           if ("forecast".equalsIgnoreCase(reporttype)) {
/*  568 */             reportmappingquery = "INSERT INTO AM_SCHEDULER_REPORTTYPE_MAPPING(ID,REPORTID,SCHEDULEID,RESOURCEID,ATTRIBUTEID,ATTRIBUTE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_SCHEDULER_REPORTTYPE_MAPPING") + "," + reportid + "," + scheduleid + ",'" + rid + "','" + forecastTrend + "','" + tempAtt + "')";
/*      */           }
/*  570 */           AMConnectionPool.executeUpdateStmt(reportmappingquery);
/*      */         }
/*      */       }
/*  573 */       DelegatedUserRoleUtil.addEntryToConfigUserTable(request, scheduleid, 6);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  577 */       ex.printStackTrace();
/*      */     }
/*  579 */     request.setAttribute("tabtoselect", "3");
/*  580 */     showScheduleReports(mapping, form, request, response);
/*  581 */     return new ActionForward("/scheduleReports.do?method=showScheduleReports", true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getCustomAttributeName(String id)
/*      */   {
/*  589 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  590 */     String attname = null;
/*      */     try
/*      */     {
/*  593 */       String query = "select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID='" + id + "'";
/*  594 */       ResultSet res = AMConnectionPool.executeQueryStmt(query);
/*  595 */       if (res.next())
/*      */       {
/*  597 */         attname = res.getString("ATTRIBUTENAME");
/*      */       }
/*  599 */       res.close();
/*      */ 
/*      */     }
/*      */     catch (Exception eg)
/*      */     {
/*  604 */       eg.printStackTrace();
/*      */     }
/*  606 */     return attname;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getReportType(String reporttype, String monitortype)
/*      */   {
/*  613 */     if (reporttype != null)
/*      */     {
/*  615 */       if ((reporttype.equalsIgnoreCase("availability")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  617 */         return 1;
/*      */       }
/*  619 */       if ((reporttype.equalsIgnoreCase("availability")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  621 */         return 2;
/*      */       }
/*  623 */       if ((reporttype.equalsIgnoreCase("health")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  625 */         return 3;
/*      */       }
/*  627 */       if ((reporttype.equalsIgnoreCase("health")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  629 */         return 4;
/*      */       }
/*  631 */       if ((reporttype.equalsIgnoreCase("attribute")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  633 */         return 5;
/*      */       }
/*  635 */       if ((reporttype.equalsIgnoreCase("attribute")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  637 */         return 6;
/*      */       }
/*  639 */       if ((reporttype.equalsIgnoreCase("downtime")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  641 */         return 7;
/*      */       }
/*  643 */       if ((reporttype.equalsIgnoreCase("summary")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  645 */         return 8;
/*      */       }
/*  647 */       if ((reporttype.equalsIgnoreCase("event")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  649 */         return 9;
/*      */       }
/*  651 */       if ((reporttype.equalsIgnoreCase("custom")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  653 */         return 10;
/*      */       }
/*  655 */       if ((reporttype.equalsIgnoreCase("hasnapshot")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  657 */         return 11;
/*      */       }
/*  659 */       if ((reporttype.equalsIgnoreCase("availabilitysnapshot")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  661 */         return 12;
/*      */       }
/*  663 */       if ((reporttype.equalsIgnoreCase("weeklyoutage")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  665 */         return 13;
/*      */       }
/*  667 */       if ((reporttype.equalsIgnoreCase("availabilitytrend")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  669 */         return 14;
/*      */       }
/*  671 */       if ((reporttype.equalsIgnoreCase("hasnapshotHost")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  673 */         return 15;
/*      */       }
/*  675 */       if ((reporttype.equalsIgnoreCase("glancereport")) && ((monitortype.equalsIgnoreCase("monitorgroup")) || (monitortype.equalsIgnoreCase("monitor"))))
/*      */       {
/*  677 */         return 16;
/*      */       }
/*  679 */       if ((reporttype.equalsIgnoreCase("glancereport")) && (monitortype.equalsIgnoreCase("indimonitor")))
/*      */       {
/*  681 */         return 17;
/*      */       }
/*  683 */       if ((reporttype.equalsIgnoreCase("availabilitytrenddowntime")) && (monitortype.equalsIgnoreCase("monitorgroup")))
/*      */       {
/*  685 */         return 18;
/*      */       }
/*  687 */       if ((reporttype.equalsIgnoreCase("eumGlancereport")) && (monitortype.equalsIgnoreCase("monitor")))
/*      */       {
/*  689 */         return 19;
/*      */       }
/*  691 */       if (reporttype.equalsIgnoreCase("eumSummary"))
/*      */       {
/*  693 */         return 20;
/*      */       }
/*  695 */       if (reporttype.equalsIgnoreCase("sla"))
/*      */       {
/*  697 */         return 21;
/*      */       }
/*  699 */       if (reporttype.equalsIgnoreCase("dashboard"))
/*      */       {
/*  701 */         return 22;
/*      */       }
/*  703 */       if (reporttype.equalsIgnoreCase("forecast"))
/*      */       {
/*  705 */         return 23;
/*      */       }
/*  707 */       if (reporttype.equalsIgnoreCase("sqlperformance"))
/*      */       {
/*  709 */         return 24;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  714 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  720 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getCondition(String column, String[] resources)
/*      */   {
/*  726 */     return getCondition(column, resources, false);
/*      */   }
/*      */   
/*      */   public static String getCondition(String column, String[] resources, boolean toLowerCase)
/*      */   {
/*  731 */     String condition = column + " in ('";
/*  732 */     if (resources == null)
/*      */     {
/*  734 */       return column + " in (' ') ";
/*      */     }
/*  736 */     if ((resources != null) && (resources.length == 0))
/*      */     {
/*  738 */       return column + " in (' ') ";
/*      */     }
/*      */     
/*      */ 
/*  742 */     for (int i = 0; i < resources.length; i++)
/*      */     {
/*  744 */       String resource = toLowerCase ? resources[i].toLowerCase() : resources[i];
/*  745 */       if (i + 1 == resources.length)
/*      */       {
/*  747 */         condition = condition + resource + "')";
/*      */       }
/*      */       else
/*      */       {
/*  751 */         condition = condition + resource + "','";
/*      */       }
/*      */     }
/*      */     
/*  755 */     return condition;
/*      */   }
/*      */   
/*      */   public static String getResourceIDWithComma(String[] resources)
/*      */   {
/*  760 */     StringBuffer condition = new StringBuffer("");
/*      */     
/*  762 */     for (int i = 0; i < resources.length; i++)
/*      */     {
/*  764 */       if (i + 1 == resources.length)
/*      */       {
/*  766 */         condition.append(resources[i] + "");
/*      */       }
/*      */       else
/*      */       {
/*  770 */         condition.append(resources[i] + ",");
/*      */       }
/*      */     }
/*      */     
/*  774 */     return condition.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList getResourceId(String[] resourcetype)
/*      */   {
/*  781 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  782 */     ResultSet rs = null;
/*  783 */     String rid = null;
/*  784 */     ArrayList row = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/*  788 */       String queryid = "SELECT ID FROM AM_SCHEDULER_RESOURCETYPE WHERE LOWER(RESOURCETYPE) ";
/*  789 */       queryid = getCondition(queryid, resourcetype, true);
/*  790 */       rs = AMConnectionPool.executeQueryStmt(queryid);
/*  791 */       while (rs.next())
/*      */       {
/*  793 */         rid = rs.getString("ID");
/*  794 */         row.add(rid);
/*      */       }
/*  796 */       rs.close();
/*  797 */       String removeid = "SELECT ID FROM AM_SCHEDULER_RESOURCETYPE WHERE LOWER(RESOURCEVALUE) ";
/*  798 */       removeid = getCondition(removeid, resourcetype, true);
/*  799 */       rs = AMConnectionPool.executeQueryStmt(removeid);
/*  800 */       while (rs.next())
/*      */       {
/*  802 */         row.remove(rs.getString("ID"));
/*      */       }
/*  804 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  809 */       es.printStackTrace();
/*      */     }
/*      */     
/*  812 */     return row;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList setResourceId(String[] resourcetype)
/*      */   {
/*  818 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  819 */     ResultSet rs = null;
/*  820 */     String rid = null;
/*  821 */     ArrayList row = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/*  825 */       if (resourcetype.length > 0)
/*      */       {
/*  827 */         String queryid = "SELECT RESOURCETYPE FROM AM_SCHEDULER_RESOURCETYPE WHERE LOWER(RESOURCETYPE) ";
/*  828 */         queryid = getCondition(queryid, resourcetype, true);
/*  829 */         rs = AMConnectionPool.executeQueryStmt(queryid);
/*  830 */         while (rs.next())
/*      */         {
/*  832 */           rid = rs.getString("RESOURCETYPE");
/*  833 */           row.add(rid);
/*      */         }
/*  835 */         String removeid = "SELECT RESOURCETYPE FROM AM_SCHEDULER_RESOURCETYPE WHERE LOWER(RESOURCEVALUE) ";
/*  836 */         removeid = getCondition(removeid, resourcetype, true);
/*  837 */         rs = AMConnectionPool.executeQueryStmt(removeid);
/*  838 */         while (rs.next())
/*      */         {
/*  840 */           row.add(rs.getString("RESOURCETYPE"));
/*      */         }
/*      */         
/*  843 */         rs.close();
/*      */       }
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  848 */       es.printStackTrace();
/*      */     }
/*  850 */     return row;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getReportTypeForDisplay(String id)
/*      */   {
/*  857 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  858 */     ResultSet rs = null;
/*  859 */     String rtype = null;
/*      */     
/*      */     try
/*      */     {
/*  863 */       String queryid = "SELECT DISTINCT REPORTTYPE FROM AM_SCHEDULER_REPORTTYPE,AM_SCHEDULER_REPORTTYPE_MAPPING,AM_SCHEDULEDETAILS WHERE AM_SCHEDULER_REPORTTYPE_MAPPING.REPORTID=AM_SCHEDULER_REPORTTYPE.ID AND AM_SCHEDULER_REPORTTYPE_MAPPING.SCHEDULEID=AM_SCHEDULEDETAILS.ID AND AM_SCHEDULEDETAILS.ID='" + id + "'";
/*  864 */       rs = AMConnectionPool.executeQueryStmt(queryid);
/*  865 */       if (rs.next())
/*      */       {
/*  867 */         rtype = rs.getString("REPORTTYPE");
/*      */       }
/*      */       
/*  870 */       rs.close();
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  874 */       es.printStackTrace();
/*      */     }
/*  876 */     return rtype;
/*      */   }
/*      */   
/*      */   public ActionForward removeScheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  882 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  883 */     AMActionForm am = (AMActionForm)form;
/*  884 */     ActionMessages messages = new ActionMessages();
/*  885 */     ActionErrors errors = new ActionErrors();
/*      */     try
/*      */     {
/*  888 */       String[] sids = request.getParameterValues("scheduleids");
/*  889 */       for (int i = 0; i < sids.length; i++)
/*      */       {
/*  891 */         String sid = sids[i];
/*  892 */         deleteScheduler(request, sid);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  897 */       ex.printStackTrace();
/*      */     }
/*  899 */     showScheduleReports(mapping, form, request, response);
/*  900 */     return new ActionForward("/scheduleReports.do?method=showScheduleReports", true);
/*      */   }
/*      */   
/*      */ 
/*      */   public void deleteScheduler(HttpServletRequest request, String sid)
/*      */   {
/*  906 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/*  909 */       String query1 = "DELETE FROM AM_SCHEDULEDETAILS WHERE ID=" + Integer.parseInt(sid);
/*  910 */       AMConnectionPool.executeUpdateStmt(query1);
/*  911 */       String query2 = "DELETE FROM AM_SCHEDULERTIME WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  912 */       AMConnectionPool.executeUpdateStmt(query2);
/*  913 */       String query3 = "DELETE FROM AM_SCHEDULER_REPORTTYPE_MAPPING WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  914 */       AMConnectionPool.executeUpdateStmt(query3);
/*  915 */       String query4 = "DELETE FROM AM_SCHEDULER_RESOURCETYPE_MAPPING  WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  916 */       AMConnectionPool.executeUpdateStmt(query4);
/*  917 */       String query5 = "DELETE FROM  AM_WEEKLYTIME WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  918 */       AMConnectionPool.executeUpdateStmt(query5);
/*  919 */       String query6 = "DELETE FROM  AM_MONTHLYTIME WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  920 */       AMConnectionPool.executeUpdateStmt(query6);
/*  921 */       String query7 = "DELETE FROM  AM_DAILYTIME WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  922 */       AMConnectionPool.executeUpdateStmt(query7);
/*  923 */       String query8 = "DELETE FROM  AM_SCHEDULE_ACTION_MAPPING WHERE SCHEDULEID=" + Integer.parseInt(sid);
/*  924 */       AMConnectionPool.executeUpdateStmt(query8);
/*  925 */       DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(Integer.parseInt(sid), 6);
/*      */       
/*  927 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  929 */         String query9 = "delete from SCHEDULEDETAILS_CUSTOMER where ID=" + Integer.parseInt(sid);
/*  930 */         AMConnectionPool.executeUpdateStmt(query9);
/*      */       }
/*      */     }
/*      */     catch (Exception ep)
/*      */     {
/*  935 */       ep.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public String getMoSize()
/*      */   {
/*  941 */     String size = "";
/*      */     try
/*      */     {
/*  944 */       String query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject,AM_HOLISTICAPPLICATION where RESOURCEID=HAID";
/*  945 */       ArrayList al = this.mo.getRows(query);
/*  946 */       size = String.valueOf(al.size());
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  952 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  955 */     return size;
/*      */   }
/*      */   
/*      */   public ActionForward editScheduler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  960 */     response.setContentType("text/html; charset=UTF-8");
/*  961 */     request.setAttribute("mosize", getMoSize());
/*  962 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  963 */     AMActionForm am = (AMActionForm)form;
/*  964 */     ActionMessages messages = new ActionMessages();
/*  965 */     ActionErrors errors = new ActionErrors();
/*  966 */     ResultSet rs = null;
/*  967 */     String sid = request.getParameter("sid");
/*      */     
/*      */     try
/*      */     {
/*  971 */       String sname = null;
/*  972 */       String status = null;
/*  973 */       String desc = null;
/*  974 */       String rperiod = null;
/*  975 */       String brule = "oni";
/*  976 */       String speriod = null;
/*  977 */       String shour = null;
/*  978 */       String smin = null;
/*  979 */       String stask = null;
/*  980 */       String mdays = null;
/*  981 */       String montype = null;
/*  982 */       String customFieldId = null;
/*  983 */       String customFieldValueId = null;
/*  984 */       String editquery1 = "SELECT NAME,STATUS,DESCRIPTION,REPORTPERIOD,SCHEDULEPERIOD,RESOURCEGROUP,BUSINESSRULE,CUSTOMFIELDID,CUSTOMFIELDVALUEID from AM_SCHEDULEDETAILS where ID=" + Integer.parseInt(sid);
/*  985 */       System.out.println("########### Schedule Edit : editquery1 =============================>" + editquery1);
/*  986 */       rs = AMConnectionPool.executeQueryStmt(editquery1);
/*  987 */       if (rs.next())
/*      */       {
/*  989 */         sname = rs.getString("NAME");
/*  990 */         status = rs.getString("STATUS");
/*  991 */         desc = rs.getString("DESCRIPTION");
/*  992 */         rperiod = rs.getString("REPORTPERIOD");
/*  993 */         brule = rs.getString("BUSINESSRULE");
/*  994 */         speriod = rs.getString("SCHEDULEPERIOD");
/*  995 */         montype = rs.getString("RESOURCEGROUP");
/*  996 */         customFieldId = rs.getString("CUSTOMFIELDID");
/*  997 */         customFieldValueId = rs.getString("CUSTOMFIELDVALUEID");
/*  998 */         if (speriod != null)
/*      */         {
/* 1000 */           String edittimequery = "SELECT SCHEDULEHOUR,SCHEDULEMINUTE FROM AM_SCHEDULERTIME WHERE SCHEDULEID=" + sid;
/* 1001 */           System.out.println("########### Schedule Edit : edittimequery =============================>" + edittimequery);
/*      */           
/* 1003 */           rs = AMConnectionPool.executeQueryStmt(edittimequery);
/* 1004 */           if (rs.next())
/*      */           {
/* 1006 */             shour = rs.getString("SCHEDULEHOUR");
/* 1007 */             smin = rs.getString("SCHEDULEMINUTE");
/*      */           }
/* 1009 */           rs.close();
/* 1010 */           am.setDailyhour(shour);
/* 1011 */           am.setDailyminute(smin);
/* 1012 */           if (speriod.equalsIgnoreCase("Daily"))
/*      */           {
/* 1014 */             stask = "Daily";
/*      */           }
/* 1016 */           else if (speriod.equalsIgnoreCase("Weekly"))
/*      */           {
/* 1018 */             stask = "Weekly";
/* 1019 */             String editweeklyquery = "SELECT DAYSTOSCHEDULE FROM AM_WEEKLYTIME WHERE SCHEDULEID=" + sid;
/* 1020 */             rs = AMConnectionPool.executeQueryStmt(editweeklyquery);
/* 1021 */             ArrayList days = new ArrayList();
/* 1022 */             while (rs.next())
/*      */             {
/* 1024 */               String wd = rs.getString("DAYSTOSCHEDULE");
/* 1025 */               days.add(wd);
/*      */             }
/* 1027 */             rs.close();
/* 1028 */             String[] wdays = new String[days.size()];
/* 1029 */             for (int d = 0; d < days.size(); d++)
/*      */             {
/* 1031 */               wdays[d] = ((String)days.get(d));
/*      */             }
/* 1033 */             am.setDays(wdays);
/*      */           }
/*      */           else
/*      */           {
/* 1037 */             stask = "Monthly";
/* 1038 */             String editmonthlyquery = "SELECT DAYSTOSCHEDULE,MONTHTOSCHEDULE FROM AM_MONTHLYTIME WHERE SCHEDULEID=" + sid;
/* 1039 */             rs = AMConnectionPool.executeQueryStmt(editmonthlyquery);
/* 1040 */             ArrayList months = new ArrayList();
/* 1041 */             while (rs.next())
/*      */             {
/* 1043 */               mdays = rs.getString("DAYSTOSCHEDULE");
/* 1044 */               String mo = rs.getString("MONTHTOSCHEDULE");
/* 1045 */               months.add(mo);
/*      */             }
/* 1047 */             rs.close();
/* 1048 */             if (months.size() == 12)
/*      */             {
/* 1050 */               am.setMode("All");
/*      */             }
/* 1052 */             String[] mon = new String[months.size()];
/* 1053 */             for (int m = 0; m < months.size(); m++)
/*      */             {
/* 1055 */               mon[m] = ((String)months.get(m));
/*      */             }
/* 1057 */             am.setMonths(mon);
/* 1058 */             am.setSelectday(mdays);
/*      */           }
/* 1060 */           am.setTaskMethod(stask);
/*      */         }
/*      */       }
/*      */       
/* 1064 */       rs.close();
/* 1065 */       am.setTaskName(sname);
/* 1066 */       am.setTaskDescription(desc);
/* 1067 */       am.setTaskStatus(status);
/* 1068 */       am.setTypeofperiod(rperiod);
/* 1069 */       am.setTypeoftrendperiod(rperiod);
/* 1070 */       am.setTypeofoutageperiod(rperiod);
/* 1071 */       am.setTypeofperformanceperiod(rperiod);
/* 1072 */       am.setBusinessPeriod(brule);
/*      */       
/* 1074 */       String editquery2 = "SELECT DISTINCT AM_SCHEDULER_REPORTTYPE.ID as ID ,AM_SCHEDULER_REPORTTYPE.ATTRIBUTE AS REPORTVALUE,AM_SCHEDULER_REPORTTYPE_MAPPING.ATTRIBUTE,AM_SCHEDULER_REPORTTYPE_MAPPING.ATTRIBUTEID FROM AM_SCHEDULER_REPORTTYPE,AM_SCHEDULER_REPORTTYPE_MAPPING where AM_SCHEDULER_REPORTTYPE.ID=AM_SCHEDULER_REPORTTYPE_MAPPING.REPORTID AND AM_SCHEDULER_REPORTTYPE_MAPPING.SCHEDULEID=" + sid;
/* 1075 */       System.out.println("########### Schedule Edit : editquery2 =============================>" + editquery2);
/*      */       
/* 1077 */       rs = AMConnectionPool.executeQueryStmt(editquery2);
/* 1078 */       String reporttype = null;
/* 1079 */       String attribute = null;
/* 1080 */       String growthTrend = null;
/* 1081 */       int reportTypeID = 0;
/* 1082 */       String reportdelivery = "pdf";
/* 1083 */       if (rs.next())
/*      */       {
/* 1085 */         reporttype = rs.getString("REPORTVALUE");
/* 1086 */         attribute = rs.getString("ATTRIBUTE");
/* 1087 */         reportTypeID = rs.getInt("ID");
/* 1088 */         growthTrend = rs.getString("ATTRIBUTEID");
/*      */       }
/*      */       
/* 1091 */       if (reporttype.equals("forecast")) {
/* 1092 */         am.setForecastTrend(growthTrend);
/* 1093 */         am.setForecastTypeofperiod(rperiod);
/* 1094 */         request.setAttribute("growthTrend", growthTrend);
/*      */       }
/*      */       
/* 1097 */       rs.close();
/*      */       
/* 1099 */       if ((reportTypeID == 17) && ("monitor".equals(montype)))
/*      */       {
/* 1101 */         am.setReportmonitor("indimonitor");
/*      */       }
/*      */       else
/*      */       {
/* 1105 */         am.setReportmonitor(montype);
/*      */       }
/* 1107 */       if ("sla".equals(reporttype)) {
/* 1108 */         am.setSlatype(montype);
/*      */       }
/*      */       
/* 1111 */       String attID = null;
/* 1112 */       if ((attribute != null) && (attribute.contains("#"))) {
/* 1113 */         String[] st = null;
/* 1114 */         st = attribute.split("#");
/* 1115 */         attID = st[1];
/*      */       } else {
/* 1117 */         attID = attribute;
/*      */       }
/* 1119 */       String dbperf = null;
/* 1120 */       if (reporttype.equalsIgnoreCase("sqlperformance")) {
/* 1121 */         String[] values = null;
/* 1122 */         values = attID.split("&");
/* 1123 */         am.setTypeofperformance(values[0]);
/* 1124 */         dbperf = values[1];
/* 1125 */         ResultSet rsperf = null;
/*      */         try {
/* 1127 */           rsperf = AMConnectionPool.executeQueryStmt("SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID='" + sid + "'");
/* 1128 */           while (rsperf.next()) {
/* 1129 */             String resid = rsperf.getString("RESOURCEID");
/* 1130 */             am.setSqlServerPerf(resid);
/* 1131 */             am.setSqlDBPerf(dbperf);
/*      */           }
/*      */         } catch (SQLException e) {
/* 1134 */           e.printStackTrace();
/*      */         } finally {
/* 1136 */           AMConnectionPool.closeStatement(rsperf);
/*      */         }
/*      */       }
/*      */       
/* 1140 */       am.setTypeofreport(reporttype);
/* 1141 */       if ("wlithread".equals(attribute)) {
/* 1142 */         attribute = "thread";
/* 1143 */       } else if ("wlisession".equals(attribute)) {
/* 1144 */         attribute = "session";
/* 1145 */       } else if ("wlijdbc".equals(attribute)) {
/* 1146 */         attribute = "jdbc";
/* 1147 */       } else if ("wlijvm".equals(attribute)) {
/* 1148 */         attribute = "jvm";
/*      */       }
/* 1150 */       am.setTypeofattribute(attribute);
/* 1151 */       if (reporttype.equals("forecast")) {
/* 1152 */         am.setTypeofforecastattribute(attribute);
/*      */       }
/* 1154 */       String editquery3 = null;
/* 1155 */       getActions(mapping, form, request, response);
/* 1156 */       String editactionquery2 = "SELECT ACTIONID,REPORTGENERATION,REPORTDELIVERY  FROM AM_SCHEDULE_ACTION_MAPPING where SCHEDULEID=" + sid;
/* 1157 */       rs = AMConnectionPool.executeQueryStmt(editactionquery2);
/* 1158 */       String reportgen = null;
/* 1159 */       String actid = null;
/* 1160 */       if (rs.next())
/*      */       {
/* 1162 */         reportgen = rs.getString("REPORTGENERATION");
/* 1163 */         actid = rs.getString("ACTIONID");
/* 1164 */         reportdelivery = rs.getString("REPORTDELIVERY");
/*      */       }
/* 1166 */       rs.close();
/* 1167 */       am.setActionsEnabled(reportgen);
/* 1168 */       am.setSendmail(actid);
/* 1169 */       am.setReportDelivery(reportdelivery);
/* 1170 */       request.setAttribute("editschedule", "edit");
/* 1171 */       String customFieldAliasName = DBUtil.getSingleDataFromDB("select ALIASNAME from AM_MYFIELDS_METADATA where FIELDID=" + customFieldId);
/* 1172 */       if ((customFieldAliasName != null) && (!customFieldAliasName.trim().equals(""))) {
/* 1173 */         request.setAttribute("customFieldID", customFieldId);
/* 1174 */         request.setAttribute("customFieldValueID", customFieldAliasName + "$" + customFieldValueId);
/*      */       }
/*      */       
/* 1177 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 1179 */         String cust = null;
/* 1180 */         String custName = "";
/* 1181 */         String site = null;
/* 1182 */         String siteName = "";
/* 1183 */         ArrayList al = EnterpriseUtil.getCustomerSiteFromScheduleDetails(sid);
/* 1184 */         if ((al != null) && (al.size() >= 2))
/*      */         {
/* 1186 */           cust = al.get(0).toString();
/* 1187 */           site = al.get(1).toString();
/*      */         }
/*      */         
/* 1190 */         ResultSet rs1 = null;
/*      */         
/*      */         try
/*      */         {
/* 1194 */           String custNameQuery = "select CUSTOMERNAME from CUSTOMERINFO where CUSTOMERID=" + cust;
/* 1195 */           rs1 = AMConnectionPool.executeQueryStmt(custNameQuery);
/* 1196 */           if (rs1.next())
/*      */           {
/* 1198 */             custName = rs1.getString("CUSTOMERNAME");
/*      */           }
/*      */           
/* 1201 */           String siteNameQuery = "select SITENAME from SITEINFO where SITEID=" + site;
/* 1202 */           rs1 = AMConnectionPool.executeQueryStmt(siteNameQuery);
/*      */           
/* 1204 */           if (rs1.next())
/*      */           {
/* 1206 */             siteName = rs1.getString("SITENAME");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/* 1217 */             if (rs1 != null)
/*      */             {
/* 1219 */               rs1.close();
/*      */             }
/*      */           }
/*      */           catch (Exception ex2)
/*      */           {
/* 1224 */             ex2.printStackTrace();
/*      */           }
/*      */           
/*      */ 
/* 1228 */           request.setAttribute("CUSTOMER", cust);
/*      */         }
/*      */         catch (Exception ex1)
/*      */         {
/* 1211 */           ex1.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*      */           try
/*      */           {
/* 1217 */             if (rs1 != null)
/*      */             {
/* 1219 */               rs1.close();
/*      */             }
/*      */           }
/*      */           catch (Exception ex2)
/*      */           {
/* 1224 */             ex2.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1229 */         request.setAttribute("CUSTOMERNAME", custName);
/* 1230 */         request.setAttribute("SITE", site);
/* 1231 */         request.setAttribute("SITENAME", siteName);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1237 */       ex.printStackTrace();
/*      */     }
/* 1239 */     return new ActionForward("/jsp/ScheduleReportDetails.jsp?edit=true&sid=" + sid);
/*      */   }
/*      */   
/*      */   public ActionForward updateScheduleDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1245 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1246 */     AMActionForm am = (AMActionForm)form;
/* 1247 */     ActionMessages messages = new ActionMessages();
/* 1248 */     ActionErrors errors = new ActionErrors();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1253 */       String sid = request.getParameter("sid");
/* 1254 */       String getid = null;
/* 1255 */       String error = null;
/* 1256 */       String query = "SELECT ID FROM AM_SCHEDULEDETAILS where NAME='" + am.getTaskName() + "'";
/*      */       
/* 1258 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/* 1259 */       if (rs1.next())
/*      */       {
/* 1261 */         getid = rs1.getString("ID");
/*      */       }
/*      */       
/* 1264 */       rs1.close();
/*      */       
/* 1266 */       if ((getid != null) && (!getid.equals(sid)))
/*      */       {
/*      */ 
/* 1269 */         error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { am.getTaskName() });
/* 1270 */         request.setAttribute("message", error);
/* 1271 */         return new ActionForward("/scheduleReports.do?method=editScheduler&sid=" + sid);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1276 */       deleteScheduler(request, sid);
/* 1277 */       addScheduleDetails(mapping, form, request, response);
/*      */       
/*      */ 
/*      */ 
/* 1281 */       return new ActionForward("/scheduleReports.do?method=showScheduleReports", true);
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/* 1286 */       es.printStackTrace();
/*      */     }
/* 1288 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward checkScheduleName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1296 */     response.setContentType("text/html; charset=UTF-8");
/* 1297 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1298 */     AMActionForm am = (AMActionForm)form;
/* 1299 */     ActionMessages messages = new ActionMessages();
/* 1300 */     ActionErrors errors = new ActionErrors();
/* 1301 */     ResultSet rs = null;
/* 1302 */     String error = null;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1307 */       String sname = request.getParameter("schedulename");
/* 1308 */       String edit = request.getParameter("edit");
/* 1309 */       if ((edit != null) && (edit.equals("false")))
/*      */       {
/* 1311 */         String query = "SELECT ID FROM AM_SCHEDULEDETAILS where NAME='" + sname + "'";
/* 1312 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1313 */         if (rs.next())
/*      */         {
/* 1315 */           error = FormatUtil.getString("am.webclient.schedulereport.jsalertforsamename.text", new String[] { sname });
/* 1316 */           PrintWriter pw = response.getWriter();
/* 1317 */           pw.print(error);
/*      */         }
/* 1319 */         rs.close();
/*      */       }
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/* 1324 */       es.printStackTrace();
/*      */     }
/* 1326 */     return null;
/*      */   }
/*      */   
/*      */   public Properties getResourceGroup(String restype)
/*      */   {
/* 1331 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1332 */     Properties toReturn = new Properties();
/* 1333 */     String resgroup = "";
/* 1334 */     String resgroupname = "";
/* 1335 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1338 */       if (("windows".equalsIgnoreCase(restype)) || ("Sun Solaris".equalsIgnoreCase(restype)) || ("HP-UX".equalsIgnoreCase(restype)) || ("HP-UX / Tru64".equalsIgnoreCase(restype)) || ("FreeBSD / OpenBSD".equalsIgnoreCase(restype))) {
/* 1339 */         resgroup = "SYS";
/* 1340 */         toReturn.setProperty("ResourceGroup", resgroup);
/*      */ 
/*      */       }
/* 1343 */       else if ("HAI".equalsIgnoreCase(restype))
/*      */       {
/* 1345 */         resgroup = "VIR";
/* 1346 */         toReturn.setProperty("ResourceGroup", resgroup);
/*      */       }
/*      */       else {
/* 1349 */         String query = "select RESOURCEGROUP from AM_ManagedResourceType where RESOURCETYPE='" + restype + "'";
/* 1350 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1352 */         if (rs.next()) {
/* 1353 */           resgroup = rs.getString("RESOURCEGROUP");
/* 1354 */           toReturn.setProperty("ResourceGroup", resgroup);
/*      */         }
/*      */       }
/* 1357 */       if ("APP".equals(resgroup)) {
/* 1358 */         resgroupname = "Application Servers";
/* 1359 */       } else if ("DBS".equals(resgroup)) {
/* 1360 */         resgroupname = "Database Servers";
/* 1361 */       } else if ("SYS".equals(resgroup)) {
/* 1362 */         resgroupname = "Servers";
/* 1363 */       } else if ("URL".equals(resgroup)) {
/* 1364 */         resgroupname = "Web Services";
/* 1365 */       } else if ("ERP".equals(resgroup)) {
/* 1366 */         resgroupname = "ERP Monitors";
/* 1367 */       } else if ("MS".equals(resgroup)) {
/* 1368 */         resgroupname = "Mail Servers";
/* 1369 */       } else if ("SER".equals(resgroup)) {
/* 1370 */         resgroupname = "Services";
/* 1371 */       } else if ("TM".equals(resgroup)) {
/* 1372 */         resgroupname = "Transaction Monitors";
/* 1373 */       } else if ("MOM".equals(resgroup)) {
/* 1374 */         resgroupname = "Middleware Servers";
/*      */       }
/* 1376 */       else if (("VIR".equals(resgroup)) && ("HAI".equals(restype)))
/*      */       {
/* 1378 */         resgroupname = "am.webclient.vmware.clusters.text";
/*      */       }
/* 1380 */       else if ("VIR".equals(resgroup)) {
/* 1381 */         resgroupname = "Virtual Servers";
/*      */       }
/* 1383 */       else if ("CLD".equals(resgroup)) {
/* 1384 */         resgroupname = "Cloud Apps";
/*      */       }
/* 1386 */       toReturn.setProperty("ResourceGroupName", resgroupname);
/* 1387 */       Map AllResorcetypes = new HashMap();
/* 1388 */       Hashtable allservers = ReportUtil.getserversTypes();
/* 1389 */       AllResorcetypes.put("APP", (String)allservers.get("Application servers"));
/* 1390 */       AllResorcetypes.put("DBS", (String)allservers.get("Database Servers"));
/* 1391 */       AllResorcetypes.put("SYS", (String)allservers.get("Servers"));
/* 1392 */       AllResorcetypes.put("URL", (String)allservers.get("Web Services"));
/* 1393 */       AllResorcetypes.put("TM", (String)allservers.get("Java-Transactions"));
/* 1394 */       AllResorcetypes.put("ERP", (String)allservers.get("ERP Servers"));
/* 1395 */       AllResorcetypes.put("MS", (String)allservers.get("Mail Servers"));
/* 1396 */       AllResorcetypes.put("SER", (String)allservers.get("Services"));
/* 1397 */       AllResorcetypes.put("MOM", (String)allservers.get("Middleware Servers"));
/* 1398 */       AllResorcetypes.put("VIR", (String)allservers.get("Virtualization"));
/* 1399 */       AllResorcetypes.put("CLD", (String)allservers.get("Cloud Apps"));
/* 1400 */       String RType = AllResorcetypes.get(resgroup).toString();
/*      */       
/* 1402 */       toReturn.setProperty("ResourceType", RType);
/*      */     }
/*      */     catch (Exception ex) {
/* 1405 */       ex.printStackTrace();
/*      */     }
/* 1407 */     return toReturn;
/*      */   }
/*      */   
/*      */   public ActionForward sendCountDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1411 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1412 */     AMActionForm am = (AMActionForm)form;
/*      */     try {
/* 1414 */       response.setContentType("text/html; charset=UTF-8");
/* 1415 */       ActionMessages messages = new ActionMessages();
/* 1416 */       ActionErrors errors = new ActionErrors();
/* 1417 */       Properties pq = DBUtil.getRawValue();
/* 1418 */       String resourcegroup = "APP";
/* 1419 */       Map AllResorcetypes = new HashMap();
/* 1420 */       AllResorcetypes.put("Application Servers", "APP");
/* 1421 */       AllResorcetypes.put("Database Servers", "DBS");
/* 1422 */       AllResorcetypes.put("Servers", "SYS");
/* 1423 */       AllResorcetypes.put("Web Services", "URL");
/* 1424 */       AllResorcetypes.put("Transaction Monitors", "TM");
/* 1425 */       AllResorcetypes.put("ERP Monitors", "ERP");
/* 1426 */       AllResorcetypes.put("Mail Servers", "MS");
/* 1427 */       AllResorcetypes.put("Services", "SER");
/* 1428 */       AllResorcetypes.put("Middleware Servers", "MOM");
/* 1429 */       AllResorcetypes.put("Virtual Servers", "VIR");
/* 1430 */       AllResorcetypes.put("Cloud Apps", "CLD");
/* 1431 */       if (request.getParameter("resgroup") != null)
/*      */       {
/* 1433 */         resourcegroup = request.getParameter("resgroup");
/*      */         
/* 1435 */         if (AllResorcetypes.containsKey(resourcegroup)) {
/* 1436 */           resourcegroup = AllResorcetypes.get(resourcegroup).toString();
/*      */         }
/*      */       }
/* 1439 */       String count = pq.getProperty(resourcegroup);
/* 1440 */       PrintWriter pw = response.getWriter();
/* 1441 */       pw.print(count);
/*      */     } catch (Exception ex) {
/* 1443 */       ex.printStackTrace();
/*      */     }
/* 1445 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward sendAjaxDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1451 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1452 */     AMActionForm am = (AMActionForm)form;
/*      */     
/* 1454 */     response.setContentType("text/html; charset=UTF-8");
/* 1455 */     ActionMessages messages = new ActionMessages();
/* 1456 */     ActionErrors errors = new ActionErrors();
/* 1457 */     ResultSet rs = null;
/* 1458 */     String temp = null;
/* 1459 */     String sid = null;
/* 1460 */     String edit = null;
/* 1461 */     String report = null;
/* 1462 */     String resource = null;
/* 1463 */     String attribute = null;
/* 1464 */     String resourcetypes = null;
/* 1465 */     String oldresourcetypes = null;
/* 1466 */     String isCustom = "false";
/* 1467 */     String resourcegroup = null;
/* 1468 */     String resourcegroupname = null;
/* 1469 */     ArrayList resids = new ArrayList();
/* 1470 */     ArrayList resourceids = new ArrayList();
/*      */     try
/*      */     {
/* 1473 */       if ((request.getParameter("sid") != null) && (request.getParameter("sid").equals("undefined")))
/*      */       {
/* 1475 */         sid = "0";
/*      */       }
/* 1477 */       else if ((request.getParameter("sid") != null) || (request.getParameterMap().containsKey("sid")))
/*      */       {
/* 1479 */         sid = request.getParameter("sid");
/*      */       }
/*      */       
/* 1482 */       edit = request.getParameter("edit");
/* 1483 */       report = request.getParameter("report");
/* 1484 */       resource = request.getParameter("resource");
/* 1485 */       attribute = request.getParameter("attribute");
/* 1486 */       resourcetypes = request.getParameter("resourcetypes");
/*      */       
/*      */ 
/* 1489 */       isCustom = request.getParameter("customAttribute");
/* 1490 */       oldresourcetypes = resourcetypes;
/*      */       
/*      */ 
/* 1493 */       if ("true".equals(isCustom)) {
/* 1494 */         Properties props = getResourceGroup(resourcetypes);
/* 1495 */         resourcegroup = props.getProperty("ResourceGroup");
/* 1496 */         resourcegroupname = props.getProperty("ResourceGroupName");
/* 1497 */         resourcetypes = props.getProperty("ResourceType");
/*      */       }
/*      */       
/* 1500 */       String editquery3 = null;
/* 1501 */       if ((sid != null) && (!sid.equals("null")))
/*      */       {
/* 1503 */         if (("allba".equalsIgnoreCase(resource)) || ("slaba".equalsIgnoreCase(resource)) || ("allserver".equalsIgnoreCase(resource)) || ("slaserver".equalsIgnoreCase(resource))) {
/* 1504 */           BussinessAction bAction = new BussinessAction();
/* 1505 */           String condition = "";
/* 1506 */           String query = null;
/* 1507 */           boolean isPrivilegedUser = false;
/* 1508 */           if (ClientDBUtil.isPrivilegedUser(request)) {
/* 1509 */             isPrivilegedUser = true;
/*      */           }
/*      */           
/* 1512 */           if ((resource.equalsIgnoreCase("slaba")) || (resource.equalsIgnoreCase("slaserver"))) {
/* 1513 */             condition = "join AM_SLA_RESID_MAPPER on  AM_SLA_RESID_MAPPER.RESID=mo.RESOURCEID";
/*      */           }
/*      */           
/* 1516 */           String editCondition = "";
/* 1517 */           StringBuilder resId = new StringBuilder("-1,");
/* 1518 */           query = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID=" + sid;
/* 1519 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1520 */           if (rs.next()) {
/* 1521 */             resId.append(rs.getString("RESOURCEID"));
/*      */           }
/* 1523 */           AMConnectionPool.closeStatement(rs);
/*      */           
/* 1525 */           if (resId.length() > 3) {
/* 1526 */             editCondition = " and mo.RESOURCEID not in (" + resId.substring(0, resId.length()) + ")";
/*      */             
/* 1528 */             query = "SELECT RESOURCEID,DISPLAYNAME FROM  AM_ManagedObject where RESOURCEID in (" + resId.substring(0, resId.length()) + ")";
/* 1529 */             ArrayList data = null;
/* 1530 */             if ((resource.equalsIgnoreCase("allba")) || (resource.equalsIgnoreCase("slaba"))) {
/* 1531 */               data = bAction.getMonitorGroups(query, true);
/* 1532 */               data = bAction.updateSGNameswithTree(data);
/*      */             } else {
/* 1534 */               data = bAction.getMonitorGroups(query, false);
/*      */             }
/* 1536 */             am.setPresent(data);
/*      */           }
/*      */           
/*      */ 
/* 1540 */           if ((resource.equalsIgnoreCase("allba")) || (resource.equalsIgnoreCase("slaba")))
/*      */           {
/* 1542 */             String cond = "";
/* 1543 */             if (Constants.subGroupsEnabled.equals("false")) {
/* 1544 */               cond = " AND AM_HOLISTICAPPLICATION.TYPE  = 0";
/*      */             }
/*      */             
/* 1547 */             if (!isPrivilegedUser) {
/* 1548 */               query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME FROM  AM_ManagedObject mo " + condition + " JOIN AM_HOLISTICAPPLICATION ON mo.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID " + cond + editCondition;
/*      */             } else {
/* 1550 */               query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME FROM  AM_ManagedObject mo " + condition + " join  AM_HOLISTICAPPLICATION on mo.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID JOIN AM_HOLISTICAPPLICATION_OWNERS AS  hao on AM_HOLISTICAPPLICATION.HAID  = hao.HAID JOIN AM_UserPasswordTable AS  upt on hao.OWNERID  = upt.USERID WHERE upt.USERNAME ='" + request.getRemoteUser() + "' " + cond + editCondition;
/*      */             }
/* 1552 */             ArrayList rows = bAction.getMonitorGroups(query, true);
/* 1553 */             rows = bAction.updateSGNameswithTree(rows);
/* 1554 */             am.setToAdd(rows);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1559 */           if ((resource.equalsIgnoreCase("allserver")) || (resource.equalsIgnoreCase("slaserver")))
/*      */           {
/* 1561 */             if (!isPrivilegedUser) {
/* 1562 */               query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME FROM AM_ManagedObject mo " + condition + " JOIN AM_ManagedResourceType ON mo.TYPE=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') " + editCondition + " ORDER BY mo.DISPLAYNAME";
/*      */             } else {
/* 1564 */               String mancondn = ReportUtilities.getQueryCondition("mo.RESOURCEID", request.getRemoteUser());
/* 1565 */               if (!mancondn.trim().equals("AM_ManagedObject.RESOURCEID in (-1)")) {
/* 1566 */                 query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME FROM  AM_ManagedObject mo " + condition + " JOIN AM_ManagedResourceType on mo.TYPE  = AM_ManagedResourceType.RESOURCETYPE  WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'SYS'  AND\tAM_ManagedResourceType.RESOURCETYPE  NOT IN('Node' ,'snmp-node') AND " + mancondn + editCondition + "  ORDER BY mo.DISPLAYNAME";
/*      */               } else {
/* 1568 */                 query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME FROM  AM_ManagedObject mo " + condition + " JOIN AM_ManagedResourceType on mo.TYPE  = AM_ManagedResourceType.RESOURCETYPE  WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'SYS' AND AM_ManagedResourceType.RESOURCETYPE  NOT IN ( 'Node' ,'snmp-node') " + editCondition + " ORDER BY mo.DISPLAYNAME";
/*      */               }
/*      */             }
/* 1571 */             ArrayList a2 = bAction.getMonitorGroups(query, false);
/* 1572 */             am.setToAdd(a2);
/*      */           }
/*      */           
/*      */         }
/* 1576 */         else if ("dashboard".equalsIgnoreCase(report)) {
/* 1577 */           String condition = "";String query = "";String editCondition = "where PAGEID not in (-1)";
/* 1578 */           ResultSet rs1 = null;
/* 1579 */           StringBuilder resId = new StringBuilder("-1,");
/* 1580 */           query = "SELECT RESOURCEID FROM AM_SCHEDULER_REPORTTYPE_MAPPING where SCHEDULEID=" + sid;
/* 1581 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1582 */           if (rs.next()) {
/* 1583 */             resId.append(rs.getString("RESOURCEID"));
/*      */           }
/* 1585 */           AMConnectionPool.closeStatement(rs);
/*      */           
/*      */ 
/* 1588 */           if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */           {
/* 1590 */             condition = "and (USERID=" + DBUtil.getUserID(request.getRemoteUser()) + " or (PAGEID%" + EnterpriseUtil.RANGE + ")<5)";
/*      */           }
/* 1592 */           else if ((DBUtil.isDelegatedAdminEnabled()) && (!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*      */           {
/* 1594 */             condition = DBUtil.getCondition("and USERID ", DelegatedUserRoleUtil.getNonDelegtedAdminIDs());
/*      */           }
/* 1596 */           if (resId.length() > 3) {
/* 1597 */             ArrayList data = new ArrayList();
/* 1598 */             editCondition = "where PAGEID not in (" + resId.substring(0, resId.length()) + ")";
/* 1599 */             String selectquery = "select PAGEID,PAGENAME from AM_MYPAGES where PAGEID in (" + resId.substring(0, resId.length()) + ") " + condition + "  order by PAGENAME";
/* 1600 */             AMLog.debug("sendAjaxDetails :: selectquery :" + selectquery);
/*      */             try
/*      */             {
/* 1603 */               rs1 = AMConnectionPool.executeQueryStmt(selectquery);
/* 1604 */               while (rs1.next())
/*      */               {
/* 1606 */                 String pageId = rs1.getString("PAGEID");
/* 1607 */                 String pageName = rs1.getString("PAGENAME");
/* 1608 */                 Properties p = new Properties();
/* 1609 */                 p.setProperty("label", pageName);
/* 1610 */                 p.setProperty("value", pageId);
/* 1611 */                 data.add(p);
/*      */               }
/*      */             }
/*      */             catch (Exception exec)
/*      */             {
/* 1616 */               exec.printStackTrace();
/*      */             }
/*      */             finally {
/* 1619 */               AMConnectionPool.closeStatement(rs1);
/*      */             }
/* 1621 */             am.setPresentg(data);
/*      */           }
/* 1623 */           String allquery = "select PAGEID,PAGENAME from AM_MYPAGES " + editCondition + condition + "  order by PAGENAME";
/* 1624 */           AMLog.debug("sendAjaxDetails :: allquery :" + allquery);
/* 1625 */           ArrayList a2 = new ArrayList();
/*      */           try
/*      */           {
/* 1628 */             rs1 = AMConnectionPool.executeQueryStmt(allquery);
/* 1629 */             while (rs1.next())
/*      */             {
/* 1631 */               String pageId = rs1.getString("PAGEID");
/* 1632 */               String pageName = rs1.getString("PAGENAME");
/* 1633 */               Properties p = new Properties();
/* 1634 */               p.setProperty("label", pageName);
/* 1635 */               p.setProperty("value", pageId);
/* 1636 */               a2.add(p);
/*      */             }
/*      */           }
/*      */           catch (Exception exec)
/*      */           {
/* 1641 */             exec.printStackTrace();
/*      */           }
/*      */           finally {
/* 1644 */             AMConnectionPool.closeStatement(rs1);
/*      */           }
/* 1646 */           am.setToAddg(a2);
/*      */         }
/* 1648 */         else if ((resource != null) && ((resource.equalsIgnoreCase("monitorgroup")) || (report.equalsIgnoreCase("eumGlancereport")) || (report.equalsIgnoreCase("eumSummary")) || (report.equalsIgnoreCase("downtime")) || (report.equalsIgnoreCase("summary")) || (resource.equalsIgnoreCase("indimonitor"))))
/*      */         {
/*      */ 
/* 1651 */           if (("hasnapshot".equals(report)) || ("hasnapshotHost".equals(report)) || ("availabilitysnapshot".equals(report)) || ("weeklyoutage".equals(report)) || ("availabilitytrend".equals(report)) || ("availabilitytrenddowntime".equals(report)))
/*      */           {
/* 1653 */             editquery3 = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID='" + sid + "'";
/* 1654 */             rs = AMConnectionPool.executeQueryStmt(editquery3);
/* 1655 */             String resIds = " ";
/* 1656 */             if (rs.next())
/*      */             {
/* 1658 */               resIds = rs.getString("RESOURCEID");
/*      */             }
/*      */             
/* 1661 */             String[] temp1 = resIds.split(",");
/* 1662 */             if (temp1.length > 0) {
/* 1663 */               for (int k = 0; k < temp1.length; k++)
/*      */               {
/*      */ 
/* 1666 */                 resids.add(temp1[k].trim());
/*      */               }
/*      */             }
/*      */             
/* 1670 */             rs.close();
/*      */           }
/*      */           else
/*      */           {
/* 1674 */             editquery3 = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID='" + sid + "'";
/* 1675 */             rs = AMConnectionPool.executeQueryStmt(editquery3);
/* 1676 */             while (rs.next())
/*      */             {
/* 1678 */               String resid = rs.getString("RESOURCEID");
/*      */               
/* 1680 */               resids.add(resid);
/*      */             }
/*      */             
/* 1683 */             rs.close();
/*      */           }
/*      */         }
/* 1686 */         else if ((report != null) && (report.equalsIgnoreCase("custom")))
/*      */         {
/*      */ 
/*      */ 
/* 1690 */           editquery3 = "SELECT RESOURCEID,ATTRIBUTEID FROM AM_SCHEDULER_REPORTTYPE_MAPPING where SCHEDULEID='" + sid + "'";
/* 1691 */           rs = AMConnectionPool.executeQueryStmt(editquery3);
/* 1692 */           while (rs.next())
/*      */           {
/* 1694 */             String resid = rs.getString("RESOURCEID");
/* 1695 */             String attid = rs.getString("ATTRIBUTEID");
/* 1696 */             String attvalue = resid + ":" + attid;
/* 1697 */             resids.add(attvalue);
/*      */           }
/* 1699 */           rs.close();
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 1705 */           editquery3 = DBQueryUtil.getDBQuery("am.schedulereportaction.sendajaxdetails.query3", new String[] { sid });
/* 1706 */           rs = AMConnectionPool.executeQueryStmt(editquery3);
/* 1707 */           while (rs.next())
/*      */           {
/* 1709 */             String restype = rs.getString("RESOURCETYPE");
/* 1710 */             resourceids.add(restype);
/*      */           }
/* 1712 */           rs.close();
/* 1713 */           String[] id = new String[resourceids.size()];
/* 1714 */           for (int r = 0; r < resourceids.size(); r++)
/*      */           {
/* 1716 */             id[r] = ((String)resourceids.get(r));
/*      */           }
/* 1718 */           resids = setResourceId(id);
/*      */         }
/*      */         
/*      */ 
/* 1722 */         String[] rids = new String[resids.size()];
/* 1723 */         for (int k = 0; k < resids.size(); k++)
/*      */         {
/* 1725 */           rids[k] = ((String)resids.get(k));
/*      */         }
/*      */         
/* 1728 */         am.setResourcestypes(rids);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*      */ 
/* 1736 */       es.printStackTrace();
/*      */     }
/*      */     
/* 1739 */     oldresourcetypes = oldresourcetypes.replaceAll("&#39;", "'");
/* 1740 */     resourcetypes = resourcetypes.replaceAll("&#39;", "'");
/* 1741 */     return new ActionForward("/jsp/ScheduleReportsResources.jsp?report=" + report + "&resource=" + resource + "&attribute=" + attribute + "&resourcetypes=" + resourcetypes + "&oldresourcetypes=" + oldresourcetypes + "&edit=" + edit + "&isCustom=" + isCustom + "&resgroup=" + resourcegroup + "&resgroupname=" + resourcegroupname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward sendActionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1749 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1750 */     AMActionForm am = (AMActionForm)form;
/* 1751 */     ActionMessages messages = new ActionMessages();
/* 1752 */     ActionErrors errors = new ActionErrors();
/* 1753 */     response.setContentType("text/html; charset=UTF-8");
/* 1754 */     ResultSet rs = null;
/* 1755 */     String temp = null;
/* 1756 */     String sname = null;
/* 1757 */     String emailid = null;
/* 1758 */     String htmlmess = getHTMLMailTemplate();
/* 1759 */     String host = InetAddress.getLocalHost().getHostName();
/* 1760 */     String port = System.getProperty("webserver.port");
/* 1761 */     boolean isHTTPS = false;
/* 1762 */     String fromAddr = "";
/* 1763 */     ResultSet rsForFrmAddr = null;
/*      */     
/* 1765 */     String protocol = "http";
/* 1766 */     if (port.equals("0"))
/*      */     {
/* 1768 */       protocol = "https";
/* 1769 */       isHTTPS = true;
/* 1770 */       port = System.getProperty("ssl.port");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1775 */       emailid = request.getParameter("emailid");
/* 1776 */       fromAddr = emailid;
/* 1777 */       int i = 0;
/* 1778 */       String actionquery = null;
/* 1779 */       if (emailid != null)
/*      */       {
/* 1781 */         rsForFrmAddr = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='GlobalEMailAddress'");
/* 1782 */         if (rsForFrmAddr.next())
/*      */         {
/* 1784 */           fromAddr = rsForFrmAddr.getString("VALUE");
/*      */         }
/* 1786 */         String q1 = "select HOST from AM_MAILSETTINGS ";
/* 1787 */         ResultSet rst = AMConnectionPool.executeQueryStmt(q1);
/* 1788 */         String returnVal = null;
/* 1789 */         String sentactid = null;
/* 1790 */         if (rst.next())
/*      */         {
/* 1792 */           String fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.schedulemail.topheading.text"));
/* 1793 */           String mailheading = FormatUtil.findReplace(fline, "~Name~", FormatUtil.getString("Scheduler Name"));
/* 1794 */           String startmail = FormatUtil.findReplace(mailheading, "~source~", FormatUtil.getString("TEST"));
/* 1795 */           String attr = FormatUtil.findReplace(startmail, "~Report~", FormatUtil.getString("Report Name"));
/* 1796 */           String messa = FormatUtil.findReplace(attr, "~attribute~", FormatUtil.getString("Availability Report"));
/* 1797 */           String schper = FormatUtil.findReplace(messa, "~Period~", FormatUtil.getString("Report Period"));
/* 1798 */           String schmess = FormatUtil.findReplace(schper, "~message~", FormatUtil.getString("Today"));
/* 1799 */           String schgen = FormatUtil.findReplace(schmess, "~Time~", FormatUtil.getString("Report Generated at"));
/* 1800 */           String genat = FormatUtil.findReplace(schgen, "~date~", FormatUtil.getString("Time of report generation"));
/* 1801 */           String mytab = FormatUtil.findReplace(genat, "~mytable~", "");
/* 1802 */           String user = FormatUtil.findReplace(mytab, "~userinfo~", "");
/* 1803 */           String addinfo = FormatUtil.findReplace(user, "~addinfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 1804 */           String repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 1805 */           String protocolFilled = FormatUtil.findReplace(repby, "~protocol~", protocol);
/* 1806 */           String hostFilled = FormatUtil.findReplace(protocolFilled, "~host~", host);
/*      */           
/*      */ 
/* 1809 */           String nameFilled = null;
/* 1810 */           if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */           {
/* 1812 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*      */           }
/*      */           else
/*      */           {
/* 1816 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*      */           }
/*      */           
/*      */ 
/* 1820 */           String portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/* 1821 */           SmtpMailer mailer = new SmtpMailer(fromAddr, emailid, "", FormatUtil.getString("Test Mail for Schedule Reports"));
/*      */           
/* 1823 */           if (isHTTPS)
/*      */           {
/* 1825 */             portFilled = FormatUtil.findReplace(portFilled, "http:", "https:");
/*      */           }
/* 1827 */           returnVal = mailer.sendMessage("", null, portFilled);
/*      */           
/* 1829 */           if (returnVal == null)
/*      */           {
/* 1831 */             sname = request.getParameter("emailname");
/* 1832 */             sname = sname + "_Action";
/* 1833 */             actionquery = "INSERT INTO AM_ACTIONPROFILE (ID,NAME,TYPE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + ",'" + sname + "','1')";
/* 1834 */             AMConnectionPool.executeUpdateStmt(actionquery);
/* 1835 */             String getid = "SELECT ID FROM AM_ACTIONPROFILE WHERE NAME= '" + sname + "' AND TYPE = '1'";
/* 1836 */             ResultSet r1 = AMConnectionPool.executeQueryStmt(getid);
/* 1837 */             String actid = "";
/* 1838 */             if (r1.next())
/*      */             {
/* 1840 */               actid = r1.getString("ID");
/*      */             }
/* 1842 */             r1.close();
/* 1843 */             String subject = null;
/* 1844 */             if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */             {
/* 1846 */               subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") });
/*      */             }
/*      */             else
/*      */             {
/* 1850 */               subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") });
/*      */             }
/*      */             
/* 1853 */             String act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE,SMTPPORT) values (" + actid + ",'" + fromAddr + "','" + emailid + "','" + subject + "','This message is generated for scheduling reports',25)";
/* 1854 */             AMConnectionPool.executeUpdateStmt(act2);
/* 1855 */             int loginUserID = DelegatedUserRoleUtil.getLoginUserid(request);
/* 1856 */             DelegatedUserRoleUtil.addEntryToConfigUserTable(Integer.parseInt(actid), loginUserID, 0, 2);
/* 1857 */             if (EnterpriseUtil.isAdminServer()) {
/* 1858 */               MASSyncUtil.pushEmailActionToMAS(actid);
/*      */             }
/* 1860 */             Properties pro = new Properties();
/* 1861 */             ArrayList rows = new ArrayList();
/* 1862 */             pro.setProperty("label", sname);
/* 1863 */             pro.setProperty("value", actid);
/* 1864 */             rows.add(pro);
/* 1865 */             getActions(mapping, form, request, response);
/* 1866 */             am.setSendmail(actid);
/* 1867 */             sentactid = returnVal + "," + actid;
/* 1868 */             request.setAttribute("tabtoselect", "3");
/* 1869 */             PrintWriter pw = response.getWriter();
/* 1870 */             pw.print(sentactid);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1875 */             request.setAttribute("tabtoselect", "3");
/* 1876 */             getActions(mapping, form, request, response);
/*      */             
/* 1878 */             returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.mailmessage.text");
/* 1879 */             sentactid = returnVal + ",0";
/* 1880 */             PrintWriter pw = response.getWriter();
/* 1881 */             pw.print(sentactid);
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1887 */           returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.smtpmailmessage.text");
/* 1888 */           getActions(mapping, form, request, response);
/* 1889 */           sentactid = returnVal + ",0";
/* 1890 */           PrintWriter pw = response.getWriter();
/* 1891 */           pw.print(sentactid);
/*      */         }
/*      */         
/* 1894 */         rst.close();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*      */ 
/* 1904 */       es.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1908 */       AMConnectionPool.closeStatement(rsForFrmAddr);
/*      */     }
/* 1910 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long getTimeInMillis(String hour, String min)
/*      */   {
/* 1917 */     int hr = Integer.parseInt(hour);
/* 1918 */     int mt = Integer.parseInt(min);
/*      */     
/* 1920 */     long curTime = System.currentTimeMillis();
/* 1921 */     Date currentTimeAsDate = new Date(curTime);
/* 1922 */     Calendar cldr = Calendar.getInstance();
/* 1923 */     cldr.setTime(currentTimeAsDate);
/* 1924 */     cldr.set(11, hr);
/* 1925 */     cldr.set(13, 0);
/* 1926 */     cldr.set(14, 0);
/* 1927 */     cldr.set(12, mt);
/* 1928 */     return cldr.getTime().getTime();
/*      */   }
/*      */   
/*      */   public ActionForward sendEnableDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1934 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1935 */     AMActionForm am = (AMActionForm)form;
/* 1936 */     ActionMessages messages = new ActionMessages();
/* 1937 */     ActionErrors errors = new ActionErrors();
/* 1938 */     ResultSet rs = null;
/*      */     
/* 1940 */     String value = null;
/* 1941 */     String id = null;
/*      */     
/*      */     try
/*      */     {
/* 1945 */       id = request.getParameter("id");
/* 1946 */       int i = 0;
/* 1947 */       String valuequery = null;
/* 1948 */       if (id != null)
/*      */       {
/* 1950 */         String getvaluequery = "SELECT STATUS FROM AM_SCHEDULEDETAILS WHERE ID='" + id + "'";
/* 1951 */         rs = AMConnectionPool.executeQueryStmt(getvaluequery);
/* 1952 */         if (rs.next())
/*      */         {
/* 1954 */           value = rs.getString("STATUS");
/*      */         }
/*      */         
/*      */ 
/* 1958 */         String sendvalue = value;
/*      */         
/* 1960 */         if ((value != null) && (value.equalsIgnoreCase("disable")))
/*      */         {
/* 1962 */           valuequery = "UPDATE AM_SCHEDULEDETAILS SET STATUS='enable' where ID='" + id + "'";
/* 1963 */           sendvalue = "enable";
/*      */         }
/*      */         else
/*      */         {
/* 1967 */           valuequery = "UPDATE AM_SCHEDULEDETAILS SET STATUS='disable' where ID='" + id + "'";
/* 1968 */           sendvalue = "disable";
/*      */         }
/* 1970 */         rs.close();
/* 1971 */         AMConnectionPool.executeUpdateStmt(valuequery);
/* 1972 */         String valuetoresponse = sendvalue + "," + id;
/* 1973 */         request.setAttribute("tabtoselect", "3");
/* 1974 */         PrintWriter pw = response.getWriter();
/* 1975 */         pw.print(valuetoresponse);
/*      */       }
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/* 1980 */       es.printStackTrace();
/*      */     }
/* 1982 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getDBServersForResID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1987 */     ResultSet rs = null;
/* 1988 */     JSONObject obj = new JSONObject();
/*      */     try
/*      */     {
/* 1991 */       obj.put("All", "All");
/* 1992 */       String resourceid = request.getParameter("resourceID");
/* 1993 */       String dbQuery = "select DISPLAYNAME from AM_ManagedObject MO JOIN AM_PARENTCHILDMAPPER PC ON MO.RESOURCEID=PC.CHILDID WHERE MO.TYPE='Database' AND PC.PARENTID=" + resourceid + " order by DISPLAYNAME";
/* 1994 */       rs = AMConnectionPool.executeQueryStmt(dbQuery);
/* 1995 */       while (rs.next())
/*      */       {
/* 1997 */         obj.put(rs.getString("DISPLAYNAME"), rs.getString("DISPLAYNAME"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2002 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2006 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2008 */     PrintWriter out = null;
/*      */     try {
/* 2010 */       out = response.getWriter();
/* 2011 */       response.setContentType("application/json");
/* 2012 */       response.setHeader("Cache-Control", "no-cache");
/* 2013 */       out.println(obj);
/* 2014 */       out.flush();
/*      */     } catch (IOException e) {
/* 2016 */       e.printStackTrace();
/*      */     }
/* 2018 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getSQLServersForUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2022 */     ResultSet rs = null;
/* 2023 */     JSONObject obj = new JSONObject();
/* 2024 */     boolean isPrivilegedUser = Constants.isPrivilegedUser(request);
/* 2025 */     String loginUserid = Constants.getLoginUserid(request);
/* 2026 */     Vector resourceids = null;
/* 2027 */     if (isPrivilegedUser) {
/* 2028 */       if (Constants.isUserResourceEnabled()) {
/* 2029 */         resourceids = Constants.getUserResourceID(loginUserid);
/*      */       } else {
/* 2031 */         resourceids = ReportUtilities.getResourceIdentity(request.getRemoteUser());
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 2036 */       String resourceid = request.getParameter("resourceID");
/* 2037 */       String dbQuery = null;
/* 2038 */       if (resourceids != null) {
/* 2039 */         dbQuery = "SELECT RESOURCEID, DISPLAYNAME from AM_ManagedObject where type='MSSQL-DB-server' and " + DBUtil.getCondition("RESOURCEID", resourceids) + " order by displayname";
/*      */       } else {
/* 2041 */         dbQuery = "SELECT RESOURCEID, DISPLAYNAME from AM_ManagedObject where type='MSSQL-DB-server' order by displayname";
/*      */       }
/* 2043 */       rs = AMConnectionPool.executeQueryStmt(dbQuery);
/* 2044 */       while (rs.next())
/*      */       {
/* 2046 */         obj.put(rs.getString("RESOURCEID"), rs.getString("DISPLAYNAME"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2051 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2055 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2057 */     PrintWriter out = null;
/*      */     try {
/* 2059 */       out = response.getWriter();
/* 2060 */       response.setContentType("application/json");
/* 2061 */       response.setHeader("Cache-Control", "no-cache");
/* 2062 */       out.println(obj);
/* 2063 */       out.flush();
/*      */     } catch (IOException e) {
/* 2065 */       e.printStackTrace();
/*      */     }
/* 2067 */     return null;
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
/*      */   private static String getHTMLMailTpl()
/*      */   {
/*      */     try
/*      */     {
/* 2136 */       return FormatUtil.getContentsAsString("./conf/SchedulerMail.html");
/*      */     }
/*      */     catch (IOException io)
/*      */     {
/* 2140 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template"); }
/* 2141 */     return "error in sending mail";
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getHTMLMailTemplate()
/*      */   {
/* 2147 */     return htmlMailTpl;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward testAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2157 */     ActionMessages messages = new ActionMessages();
/* 2158 */     ActionErrors errors = new ActionErrors();
/* 2159 */     String scheduleID = request.getParameter("sid");
/* 2160 */     Calendar cal = Calendar.getInstance();
/* 2161 */     int month = cal.get(2);
/*      */     try {
/* 2163 */       ScheduleReportThread sthread = new ScheduleReportThread(scheduleID);
/* 2164 */       sthread.currentmonth = String.valueOf(month + 1);
/* 2165 */       sthread.run();
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 2170 */       ee.printStackTrace();
/*      */     }
/* 2172 */     return new ActionForward("/scheduleReports.do?method=showScheduleReports", true);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ScheduleReportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */