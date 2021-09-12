/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConfigurationDataAction
/*      */   extends DispatchAction
/*      */ {
/*   46 */   private final long sevenDaysInMillis = 604800000L;
/*   47 */   private final long thirtyDaysInMillis = 2592000000L;
/*   48 */   private final long oneYearInMillis = 31536000000L;
/*   49 */   private final long lastquatarInMillis = 7776000000L;
/*   50 */   private final long lasthalfInMillis = 15552000000L;
/*   51 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public ActionForward getConfigurationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   56 */     ReportForm rf = (ReportForm)form;
/*   57 */     ActionMessages messages = new ActionMessages();
/*   58 */     ActionErrors errors = new ActionErrors();
/*   59 */     ResultSet set = null;
/*      */     
/*   61 */     List historyinfo = new ArrayList();
/*   62 */     String resourceType = request.getParameter("restype");
/*   63 */     String mode = request.getParameter("mode");
/*   64 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*   65 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/*   66 */     boolean isRowLevelData = (request.getParameter("childType") != null) && (request.getParameter("childType").trim().equals(""));
/*   67 */     String tblName = null;
/*   68 */     String query = null;
/*      */     
/*   70 */     tblName = "AM_CONFIGURATION_INFO";
/*   71 */     if ((resourceType != null) && (Constants.serverTypesArray.contains(resourceType.toLowerCase()))) {
/*   72 */       tblName = "AM_HOST_CONFIG_INFO";
/*   73 */     } else if ((resourceType == null) && (request.getParameter("tblname") != null)) {
/*   74 */       tblName = request.getParameter("tblname");
/*      */     }
/*      */     
/*   77 */     String frompop = request.getParameter("frompop");
/*   78 */     String businessRule = request.getParameter("businessPeriod");
/*   79 */     String period = request.getParameter("period");
/*      */     
/*   81 */     request.setAttribute("period", period);
/*   82 */     String startdate = rf.getStartDate();
/*   83 */     String enddate = rf.getEndDate();
/*   84 */     String status = null;
/*   85 */     long customstartTime = 0L;
/*   86 */     long customendTime = 0L;
/*   87 */     boolean forbHr = false;
/*   88 */     System.out.println(period + "^^^^^^^^^^^^^^^^ PERIOD PRINT ^^^^^^^^^^^^^^^^ START ---> " + startdate + " End date -----> " + enddate);
/*   89 */     long startTime = 0L;
/*   90 */     long endTime = System.currentTimeMillis();
/*   91 */     Hashtable bHourDetail = new Hashtable();
/*   92 */     if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */     {
/*   94 */       bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/*      */       
/*   96 */       forbHr = true;
/*   97 */       request.setAttribute("businessPeriod", businessRule);
/*   98 */       rf.setBusinessPeriod(businessRule);
/*   99 */       request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/*      */     }
/*      */     else
/*      */     {
/*  103 */       request.setAttribute("businessPeriod", "oni");
/*  104 */       rf.setBusinessPeriod(businessRule);
/*      */     }
/*      */     
/*      */ 
/*  108 */     Properties rawproperties = DBUtil.getRawValue();
/*  109 */     request.setAttribute("rawvalues", rawproperties);
/*  110 */     if (period.equals("4")) {
/*  111 */       request.setAttribute("endDate", enddate);
/*  112 */       request.setAttribute("startDate", startdate);
/*  113 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  114 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*  115 */       if (customstartTime > customendTime) {
/*  116 */         status = "Start Time " + new Date(customstartTime) + " is greater than the End Time " + new Date(customendTime);
/*  117 */         startTime = customstartTime;
/*  118 */         endTime = customendTime;
/*      */       }
/*      */       else {
/*  121 */         startTime = customstartTime;
/*  122 */         endTime = customendTime;
/*  123 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/*  124 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*  125 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new Date(startTime).toString(), new Date(endTime).toString() });
/*      */       }
/*      */     }
/*      */     else {
/*  129 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/*  130 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/*  131 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/*  133 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new Date(startTime).toString(), new Date(endTime).toString() });
/*      */     }
/*  135 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  136 */     ArrayList list = new ArrayList();
/*  137 */     ArrayList avgList = new ArrayList();
/*  138 */     String attName = null;
/*  139 */     long min = -1L;
/*  140 */     long max = -1L;
/*  141 */     float avg = -1.0F;
/*      */     
/*      */     try
/*      */     {
/*  145 */       String resNameQuery = "select DISPLAYNAME,TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/*  146 */       set = AMConnectionPool.executeQueryStmt(resNameQuery);
/*  147 */       allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*  148 */       if (set.next()) {
/*  149 */         String resName = set.getString("DISPLAYNAME");
/*  150 */         resourceType = (resourceType != null) && (!resourceType.equals("null")) ? resourceType : set.getString("TYPE");
/*  151 */         tblName = Constants.serverTypesArray.contains(resourceType.toLowerCase()) ? "AM_HOST_CONFIG_INFO" : tblName;
/*      */         
/*  153 */         rf.setSevenThirtyAttribCln(getAttributeCollection(String.valueOf(resID), String.valueOf(attID), set.getString("TYPE"), tblName, isRowLevelData));
/*  154 */         rf.setSevenThirtyAttrib(resID + "#" + attID);
/*      */         
/*  156 */         if (allSecondLevelAttribute.contains(attID + ""))
/*      */         {
/*  158 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/*  159 */           String dname2 = displayname + "_" + resName;
/*  160 */           request.setAttribute("resourcename", dname2);
/*      */         }
/*      */         else
/*      */         {
/*  164 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  169 */       AMConnectionPool.closeStatement(set);
/*  170 */       request.setAttribute("tblname", tblName);
/*  171 */       request.setAttribute("restype", resourceType);
/*      */       
/*  173 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/*  174 */       set = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */       
/*  176 */       if (set.next()) {
/*  177 */         String title = set.getString("DISPLAYNAME");
/*      */         
/*      */ 
/*      */ 
/*  181 */         if ((resourceType != null) && (resourceType.indexOf("windows") != -1)) {
/*  182 */           if (attID == 10063) {
/*  183 */             title = "Total Virtual Memory";
/*  184 */           } else if (attID == 10064) {
/*  185 */             title = "Available Virtual Memory";
/*      */           }
/*      */         }
/*      */         
/*  189 */         request.setAttribute("monitortype", title);
/*  190 */         attName = set.getString("ATTRIBUTE");
/*  191 */         String unit = set.getString("UNITS");
/*  192 */         if (unit == null) {
/*  193 */           unit = "";
/*      */         }
/*  195 */         request.setAttribute("unit", unit);
/*      */       }
/*  197 */       AMConnectionPool.closeStatement(set);
/*      */       
/*  199 */       if ((period != null) && (period.equals("14")))
/*      */       {
/*  201 */         ArrayList raw = new ArrayList();
/*  202 */         String RawVal = rawproperties.getProperty("rawvalue");
/*  203 */         long TI = Long.parseLong(RawVal) * 24L * 60L * 60L * 1000L;
/*  204 */         long ET = System.currentTimeMillis();
/*  205 */         long ST = ET - TI;
/*  206 */         String q1 = null;
/*      */         
/*      */ 
/*  209 */         set = AMConnectionPool.executeQueryStmt(q1);
/*  210 */         while (set.next())
/*      */         {
/*  212 */           long collectionTime = set.getLong("COLLECTIONTIME");
/*  213 */           int rValue = set.getInt("VALUE");
/*  214 */           Properties prop = new Properties();
/*  215 */           prop.put("COLLECTIONTIME", new Long(collectionTime));
/*  216 */           prop.put("VALUE", new Integer(rValue));
/*  217 */           raw.add(prop);
/*      */         }
/*  219 */         request.setAttribute("rawdata", raw);
/*      */         
/*  221 */         if (raw.size() == 0)
/*      */         {
/*      */ 
/*      */ 
/*  225 */           request.setAttribute("STATUS", status);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  231 */           status = "SUCCESS";
/*  232 */           request.setAttribute("starttime", new Date(ST));
/*  233 */           request.setAttribute("endtime", new Date(ET));
/*      */           
/*      */ 
/*      */ 
/*  237 */           request.setAttribute("STATUS", status);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  242 */       long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, tblName);
/*      */       
/*      */ 
/*  245 */       query = "select ATTRIBUTEID,COLLECTIONTIME,CONFVALUE,REASON  from " + tblName + " where RESOURCEID=" + resID + " and ATTRIBUTEID=" + attID + "  and COLLECTIONTIME <= " + endTime + " and COLLECTIONTIME >= " + startTime + " order by COLLECTIONTIME desc";
/*      */       
/*      */ 
/*      */ 
/*  249 */       request.setAttribute("STIME", timeStamp[0] + "");
/*  250 */       request.setAttribute("ETIME", timeStamp[1] + "");
/*  251 */       request.setAttribute("dailyStime", timeStamp[2] + "");
/*  252 */       request.setAttribute("dailyEtime", timeStamp[3] + "");
/*      */       
/*      */ 
/*  255 */       set = AMConnectionPool.executeQueryStmt(query);
/*  256 */       double minAvgValue = -1.0D;
/*  257 */       double maxAvgValue = -1.0D;
/*  258 */       Calendar cal = Calendar.getInstance();
/*  259 */       while (set.next()) {
/*  260 */         boolean isbHrdata = false;
/*      */         try {
/*  262 */           if (bHourDetail.size() != 0)
/*      */           {
/*  264 */             cal.setTimeInMillis(set.getLong("COLLECTIONTIME"));
/*  265 */             int weekday = cal.get(7) - 1;
/*  266 */             int hour = cal.get(11);
/*  267 */             int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/*  268 */             int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/*  269 */             if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*      */             {
/*  271 */               isbHrdata = true;
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  277 */           System.out.println("############# Error in Bhour ############" + ex.toString());
/*      */         }
/*      */         
/*  280 */         if ((!forbHr) || ((forbHr) && (isbHrdata)))
/*      */         {
/*  282 */           long archivedTime = set.getLong("COLLECTIONTIME");
/*  283 */           Properties prop = new Properties();
/*  284 */           prop.put("ATTRIBUTEID", set.getString("ATTRIBUTEID"));
/*  285 */           prop.put("COLLECTIONTIME", new Long(archivedTime));
/*  286 */           prop.put("CONFVALUE", set.getString("CONFVALUE"));
/*      */           
/*  288 */           if (set.getString("REASON") != null)
/*      */           {
/*  290 */             prop.put("REASON", set.getString("REASON"));
/*      */           }
/*      */           else
/*      */           {
/*  294 */             prop.put("REASON", "-");
/*      */           }
/*  296 */           list.add(prop);
/*      */         }
/*      */       }
/*      */       
/*  300 */       request.setAttribute("data", list);
/*      */       
/*  302 */       if (list.size() == 0) {
/*  303 */         request.setAttribute("STATUS", status);
/*      */       }
/*      */       else
/*      */       {
/*  307 */         status = "SUCCESS";
/*  308 */         Properties prp = (Properties)list.get(0);
/*  309 */         Long l = (Long)prp.get("COLLECTIONTIME");
/*  310 */         request.setAttribute("endtime", new Date(endTime));
/*      */         
/*  312 */         prp = (Properties)list.get(list.size() - 1);
/*  313 */         l = (Long)prp.get("COLLECTIONTIME");
/*  314 */         request.setAttribute("starttime", new Date(l.longValue()));
/*      */         
/*      */ 
/*  317 */         request.setAttribute("STATUS", status);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  322 */       String brQuery = "select ID,NAME from AM_BUSINESSHOURSDETAILS where STATUS='enable'";
/*  323 */       set = AMConnectionPool.executeQueryStmt(brQuery);
/*  324 */       ArrayList brules = new ArrayList();
/*  325 */       while (set.next())
/*      */       {
/*  327 */         Properties dataProps = new Properties();
/*  328 */         dataProps.setProperty("label", set.getString("NAME"));
/*  329 */         dataProps.setProperty("value", set.getString("ID"));
/*  330 */         brules.add(dataProps);
/*      */       }
/*  332 */       AMConnectionPool.closeStatement(set);
/*  333 */       rf.setBusinessrules(brules);
/*      */       
/*      */ 
/*  336 */       ArrayList thresholdDetails = FaultUtil.getThresholdDetailsForHistoryData(resID, attID, true);
/*  337 */       request.setAttribute("thresholdDetails", thresholdDetails);
/*  338 */       ComparingSla cs = new ComparingSla();
/*  339 */       String valueforperiod = cs.getValueForPeriod(period);
/*  340 */       ReportUtilities rep = new ReportUtilities();
/*  341 */       String valueforpdf = rep.getValueForPeriodForPDF(period);
/*      */       
/*  343 */       request.setAttribute("note", "1");
/*  344 */       request.setAttribute("period", period);
/*  345 */       String yaxis = (String)request.getAttribute("monitortype") + " " + (String)request.getAttribute("unit");
/*  346 */       HistoryDataGraphUtil graph = new HistoryDataGraphUtil();
/*      */       ActionForward localActionForward;
/*  348 */       if ("pdf".equals(rf.getReporttype())) {
/*  349 */         request.setAttribute("report-type-template", "report.configurationhistory");
/*  350 */         request.setAttribute("period", period);
/*  351 */         request.setAttribute("periodvalue", valueforpdf);
/*  352 */         request.setAttribute("sp-report-type", "pdf");
/*      */         
/*  354 */         return mapping.findForward("historydata.success.pdf");
/*      */       }
/*      */       
/*      */ 
/*  358 */       if (frompop != null)
/*      */       {
/*  360 */         request.setAttribute("frompop", Boolean.valueOf(true));
/*      */       }
/*  362 */       request.setAttribute("tabsel", Integer.valueOf(1));
/*      */       
/*  364 */       return mapping.findForward("historydata.success");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*      */       List allSecondLevelAttribute;
/*  369 */       exp.printStackTrace();
/*  370 */       request.setAttribute("note", "1");
/*  371 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("historydata.exception", exp.toString()));
/*  372 */       saveErrors(request, errors);
/*  373 */       return mapping.getInputForward();
/*      */     }
/*      */     finally {
/*  376 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getTimePeriod(long startTime, long endTime, String period)
/*      */   {
/*  386 */     Calendar cal = new GregorianCalendar();
/*  387 */     Calendar cal1 = new GregorianCalendar();
/*      */     
/*  389 */     int dayofweek = cal1.get(7);
/*  390 */     Properties prop = new Properties();
/*  391 */     if (period.equals("0"))
/*      */     {
/*  393 */       Date currentTimeAsDate = new Date(endTime);
/*  394 */       Calendar cldr = Calendar.getInstance();
/*  395 */       cldr.setTime(currentTimeAsDate);
/*  396 */       cldr.set(11, 0);
/*  397 */       cldr.set(13, 0);
/*  398 */       cldr.set(14, 0);
/*  399 */       cldr.set(12, 0);
/*  400 */       startTime = cldr.getTime().getTime();
/*      */     }
/*  402 */     else if ((period.equals("-7")) || (period.equals("1")))
/*      */     {
/*  404 */       startTime = endTime - 604800000L;
/*      */     }
/*  406 */     else if ((period.equals("-30")) || (period.equals("2")))
/*      */     {
/*  408 */       startTime = endTime - 2592000000L;
/*      */     }
/*  410 */     else if ((period.equals("-5")) || (period.equals("5")))
/*      */     {
/*  412 */       startTime = endTime - 31536000000L;
/*      */ 
/*      */     }
/*  415 */     else if (period.equals("6")) {
/*  416 */       cal.set(7, 2);
/*  417 */       cal.set(11, 0);
/*  418 */       cal.set(12, 0);
/*  419 */       cal.set(13, 0);
/*  420 */       cal.set(14, 0);
/*      */       
/*  422 */       long lday = cal.getTimeInMillis();
/*  423 */       startTime = lday;
/*      */       
/*      */ 
/*  426 */       if (dayofweek == 7) {
/*  427 */         cal.set(7, 1);
/*  428 */         cal.set(11, 0);
/*  429 */         cal.set(12, 0);
/*  430 */         cal.set(13, 0);
/*  431 */         cal.set(14, 0);
/*      */         
/*  433 */         long lweek1 = cal.getTimeInMillis();
/*  434 */         startTime = lweek1;
/*  435 */         startTime += 86400000L;
/*  436 */         endTime = startTime + 432000000L;
/*      */       }
/*  438 */       if (dayofweek == 1) {
/*  439 */         cal.set(7, 1);
/*  440 */         cal.set(11, 0);
/*  441 */         cal.set(12, 0);
/*  442 */         cal.set(13, 0);
/*  443 */         cal.set(14, 0);
/*  444 */         long lweek1 = cal.getTimeInMillis();
/*  445 */         startTime = lweek1 - 604800000L;
/*  446 */         startTime += 86400000L;
/*  447 */         endTime = startTime + 432000000L;
/*      */       }
/*      */       
/*      */     }
/*  451 */     else if (period.equals("7")) {
/*  452 */       cal.set(5, 1);
/*  453 */       cal.set(11, 0);
/*  454 */       cal.set(12, 0);
/*  455 */       cal.set(13, 0);
/*  456 */       cal.set(14, 0);
/*      */       
/*  458 */       long lmonth = cal.getTimeInMillis();
/*  459 */       startTime = lmonth;
/*      */ 
/*      */     }
/*  462 */     else if (period.equals("11")) {
/*  463 */       int i = cal.get(2);
/*      */       
/*  465 */       cal.set(2, i - 1);
/*      */       
/*  467 */       cal.set(5, 1);
/*      */       
/*  469 */       cal.set(11, 0);
/*  470 */       cal.set(12, 0);
/*  471 */       cal.set(13, 0);
/*  472 */       cal.set(14, 0);
/*      */       
/*  474 */       long lastmonth = cal.getTimeInMillis();
/*  475 */       startTime = lastmonth;
/*  476 */       int maxDaysInMonth = cal.getActualMaximum(5);
/*  477 */       endTime = startTime + maxDaysInMonth * 24 * 60 * 60 * 1000L;
/*      */ 
/*      */ 
/*      */     }
/*  481 */     else if (period.equals("12"))
/*      */     {
/*  483 */       cal.set(7, 1);
/*  484 */       cal.set(11, 0);
/*  485 */       cal.set(12, 0);
/*  486 */       cal.set(13, 0);
/*  487 */       cal.set(14, 0);
/*      */       
/*  489 */       long lweek = cal.getTimeInMillis();
/*  490 */       startTime = lweek - 604800000L;
/*  491 */       endTime = startTime + 604800000L;
/*      */ 
/*      */ 
/*      */     }
/*  495 */     else if (period.equals("8")) {
/*  496 */       cal.set(6, 1);
/*  497 */       cal.set(11, 0);
/*  498 */       cal.set(12, 0);
/*  499 */       cal.set(13, 0);
/*  500 */       cal.set(14, 0);
/*      */       
/*  502 */       long lyear = cal.getTimeInMillis();
/*  503 */       startTime = lyear;
/*      */ 
/*      */     }
/*  506 */     else if (period.equals("9")) {
/*  507 */       int i = cal.get(2);
/*  508 */       if (i < 3) {
/*  509 */         cal.set(2, 0);
/*      */       }
/*  511 */       else if (i < 6) {
/*  512 */         cal.set(2, 3);
/*      */       }
/*  514 */       else if (i < 9) {
/*  515 */         cal.set(2, 6);
/*      */       }
/*      */       else {
/*  518 */         cal.set(2, 9);
/*      */       }
/*  520 */       cal.set(5, 1);
/*  521 */       cal.set(11, 0);
/*  522 */       cal.set(12, 0);
/*  523 */       cal.set(13, 0);
/*  524 */       cal.set(14, 0);
/*      */       
/*  526 */       long lqatar = cal.getTimeInMillis();
/*  527 */       startTime = lqatar;
/*      */ 
/*      */     }
/*  530 */     else if (period.equals("3"))
/*      */     {
/*  532 */       Date currentTimeAsDate = new Date(endTime);
/*  533 */       Calendar cldr = Calendar.getInstance();
/*  534 */       cldr.setTime(currentTimeAsDate);
/*  535 */       cldr.add(7, -1);
/*  536 */       cldr.set(11, 0);
/*  537 */       cldr.set(13, 0);
/*  538 */       cldr.set(14, 0);
/*  539 */       cldr.set(12, 0);
/*  540 */       startTime = cldr.getTime().getTime();
/*  541 */       cldr.setTime(currentTimeAsDate);
/*  542 */       cldr.set(11, 0);
/*  543 */       cldr.set(13, 0);
/*  544 */       cldr.set(14, 0);
/*  545 */       cldr.set(12, 0);
/*  546 */       endTime = cldr.getTime().getTime();
/*      */     }
/*      */     
/*  549 */     prop.put("STARTTIME", new Long(startTime));
/*  550 */     prop.put("ENDTIME", new Long(endTime));
/*  551 */     return prop;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void closeResultSet(ResultSet set)
/*      */   {
/*  559 */     if (set != null)
/*      */       try {
/*  561 */         AMConnectionPool.closeStatement(set);
/*      */       } catch (Exception ex) {
/*  563 */         ex.printStackTrace();
/*      */       }
/*      */   }
/*      */   
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType, String tblName) {
/*  568 */     return getAttributeCollection(resID, attID, resType, tblName, false);
/*      */   }
/*      */   
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType, String tblName, boolean isRowLevelData) {
/*  572 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  573 */     ArrayList retlist = new ArrayList();
/*  574 */     List attributeList = ReportUtil.getAttributeList();
/*  575 */     List secondLevelAttributes = ReportUtil.getAllSecondLevelAttribute();
/*  576 */     List attList = null;
/*      */     
/*  578 */     if ((secondLevelAttributes.contains(attID)) && (!isRowLevelData))
/*      */     {
/*  580 */       ResultSet parentSet = null;
/*  581 */       String parentidquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID,AM_ManagedObject.TYPE FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.CHILDID='" + resID + "' AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID ";
/*      */       try
/*      */       {
/*  584 */         parentSet = AMConnectionPool.executeQueryStmt(parentidquery);
/*  585 */         if (parentSet.next())
/*      */         {
/*  587 */           resID = parentSet.getString("RESID");
/*  588 */           resType = parentSet.getString("TYPE");
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*  594 */         exc.printStackTrace();
/*      */       }
/*      */       finally {
/*  597 */         AMConnectionPool.closeStatement(parentSet);
/*      */       }
/*      */     }
/*      */     
/*  601 */     attList = getConfigAttributesForResourcetype(resType, tblName, resID);
/*  602 */     ArrayList attributeid = new ArrayList();
/*  603 */     ArrayList displayname = new ArrayList();
/*  604 */     for (int j = 0; j < attList.size(); j++) {
/*  605 */       String res = attList.get(j).toString();
/*  606 */       String[] temp = res.split("#");
/*  607 */       boolean addAttrib = false;
/*  608 */       Properties data = new Properties();
/*  609 */       data.setProperty("label", temp[1]);
/*  610 */       data.setProperty("value", resID + "#" + temp[0]);
/*  611 */       retlist.add(data);
/*      */     }
/*  613 */     return retlist;
/*      */   }
/*      */   
/*      */   public List getConfigAttributesForResourcetype(String restype, String tblName, String resID)
/*      */   {
/*  618 */     List attList = new ArrayList();
/*  619 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  620 */     ResultSet rs = null;
/*  621 */     String query = null;
/*      */     try {
/*  623 */       query = "select distinct(" + tblName + ".ATTRIBUTEID),DISPLAYNAME,UNITS  from AM_ATTRIBUTES," + tblName + " where " + tblName + ".ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and " + tblName + ".RESOURCEID=" + resID + " and AM_ATTRIBUTES.ATTRIBUTEID!=10008 order by DISPLAYNAME";
/*  624 */       ArrayList attributes = new ArrayList();
/*  625 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  626 */       while (rs.next()) {
/*  627 */         String aid = rs.getString("ATTRIBUTEID");
/*  628 */         String aname = FormatUtil.getString(rs.getString("DISPLAYNAME").trim());
/*  629 */         String unit = FormatUtil.getString(rs.getString("UNITS"));
/*  630 */         if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*      */         {
/*  632 */           attList.add(aid + "#" + aname + " " + FormatUtil.getString("in") + " " + unit);
/*      */         }
/*      */         else
/*      */         {
/*  636 */           attList.add(aid + "#" + aname);
/*      */         }
/*      */       }
/*  639 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception ex) {
/*  642 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  645 */       closeResultSet(rs);
/*      */     }
/*  647 */     return attList;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getGlobalViewData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  654 */     ReportForm rf = (ReportForm)form;
/*  655 */     ActionMessages messages = new ActionMessages();
/*  656 */     ActionErrors errors = new ActionErrors();
/*  657 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  658 */     ArrayList relatedattributes = new ArrayList();
/*  659 */     ResultSet rs = null;
/*  660 */     String query = null;
/*      */     try
/*      */     {
/*  663 */       int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  664 */       String attributeid = request.getParameter("attributeid");
/*      */       
/*  666 */       String resourceName = null;
/*  667 */       String tblName = null;
/*      */       
/*  669 */       query = "select TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/*  670 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  671 */       if (rs.next()) {
/*  672 */         resourceName = rs.getString(1);
/*      */       }
/*  674 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  676 */       String groupid = request.getParameter("groupid");
/*  677 */       String relatedAttributes = request.getParameter("selectedattrid");
/*  678 */       String altattributeid = request.getParameter("altattributeid");
/*  679 */       ArrayList resids = new ArrayList();
/*  680 */       ArrayList attrids = new ArrayList();
/*  681 */       ArrayList applications = new ArrayList();
/*      */       
/*      */ 
/*  684 */       if (((altattributeid != null) && (!altattributeid.equals("true"))) || ((attributeid != null) && (!attributeid.equals(""))))
/*      */       {
/*  686 */         attrids.add(attributeid);
/*      */       }
/*      */       else
/*      */       {
/*  690 */         String[] tempAttributes = relatedAttributes.split(",");
/*  691 */         if (tempAttributes.length > 0) {
/*  692 */           for (int k = 0; k < tempAttributes.length; k++) {
/*  693 */             attrids.add(tempAttributes[k]);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  699 */       if ((attrids.size() > 0) && ((attributeid == null) || (attributeid.equals(""))))
/*      */       {
/*  701 */         attributeid = (String)attrids.get(0);
/*      */       }
/*  703 */       String resourceType = request.getParameter("restype") != null ? request.getParameter("restype") : resourceName;
/*  704 */       tblName = request.getParameter("tblname") != null ? request.getParameter("tblname") : "AM_CONFIGURATION_INFO";
/*  705 */       if ((resourceType != null) && (Constants.serverTypesArray.contains(resourceType.toLowerCase()))) {
/*  706 */         tblName = "AM_HOST_CONFIG_INFO";
/*      */       }
/*      */       
/*      */ 
/*  710 */       if ((request.isUserInRole("OPERATOR")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/*  712 */         applications = AlarmUtil.getApplicationsForOwner(request.getRemoteUser(), request);
/*      */ 
/*      */ 
/*      */       }
/*  716 */       else if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  718 */         applications = AlarmUtil.getConfiguredGroups(request);
/*      */       }
/*      */       else
/*      */       {
/*  722 */         applications = AlarmUtil.getConfiguredGroups();
/*      */       }
/*      */       
/*      */ 
/*  726 */       if (applications != null)
/*      */       {
/*  728 */         request.setAttribute("applications1", applications);
/*      */       }
/*      */       
/*      */ 
/*  732 */       if ((groupid != null) && (!groupid.equals("")))
/*      */       {
/*  734 */         query = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.TYPE  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_PARENTCHILDMAPPER.PARENTID=" + groupid + " and AM_ManagedObject.TYPE='" + resourceName + "'";
/*      */       }
/*  736 */       else if (request.isUserInRole("OPERATOR"))
/*      */       {
/*  738 */         String condition = ReportUtilities.getQueryCondition("mo.RESOURCEID", request.getRemoteUser());
/*  739 */         query = "select RESOURCEID  from AM_ManagedObject as mo where TYPE='" + resourceName + "' and " + condition;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  744 */         query = "select RESOURCEID  from AM_ManagedObject where TYPE='" + resourceName + "'";
/*      */       }
/*  746 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  747 */       while (rs.next()) {
/*  748 */         resids.add(rs.getString(1));
/*      */       }
/*  750 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  752 */       request.setAttribute("outputData", getOutputData(resids, attrids, tblName));
/*  753 */       rf.setSelectedMonitorType(resourceName);
/*  754 */       setUserConfiguredMonitorTypes(form, request);
/*      */       
/*  756 */       query = "select distinct(" + tblName + ".ATTRIBUTEID),DISPLAYNAME,UNITS  from AM_ATTRIBUTES," + tblName + " where " + tblName + ".ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and " + tblName + ".RESOURCEID=" + resID + " and AM_ATTRIBUTES.ATTRIBUTEID!=10008 order by DISPLAYNAME";
/*  757 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  758 */       while (rs.next())
/*      */       {
/*  760 */         String units = rs.getString("UNITS");
/*  761 */         if ((units != null) && (!units.equals("")))
/*      */         {
/*  763 */           units = "(" + FormatUtil.getString(units) + ")";
/*      */         }
/*  765 */         ArrayList singlerow = new ArrayList();
/*  766 */         singlerow.add(rs.getString("ATTRIBUTEID"));
/*  767 */         singlerow.add(FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*  768 */         singlerow.add(units);
/*  769 */         relatedattributes.add(singlerow);
/*      */       }
/*  771 */       AMConnectionPool.closeStatement(rs);
/*  772 */       request.setAttribute("relatedattributes", relatedattributes);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  776 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  779 */       closeResultSet(rs);
/*      */     }
/*      */     
/*  782 */     System.out.println("PDF Generator for Configuration View Details=================>" + rf.getReporttype());
/*  783 */     if ("pdf".equals(rf.getReporttype())) {
/*  784 */       request.setAttribute("report-type-template", "report.configurationglobalview");
/*  785 */       request.setAttribute("sp-report-type", "pdf");
/*      */       
/*  787 */       return mapping.findForward("historydata.success.pdf");
/*      */     }
/*      */     
/*  790 */     return mapping.findForward("historydata.globalview");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap getOutputData(ArrayList resourceids, ArrayList attributeids, String tblName)
/*      */   {
/*  799 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  800 */     ResultSet rs = null;
/*  801 */     HashMap outputData = new HashMap();
/*  802 */     StringBuilder resids = new StringBuilder();
/*  803 */     StringBuilder attrids = new StringBuilder();
/*      */     try
/*      */     {
/*  806 */       for (int i = 0; i < resourceids.size(); i++)
/*      */       {
/*  808 */         resids.append(resourceids.get(i)).append(",");
/*      */       }
/*      */       
/*  811 */       for (int i = 0; i < attributeids.size(); i++)
/*      */       {
/*  813 */         attrids.append(attributeids.get(i)).append(",");
/*      */       }
/*      */       
/*  816 */       if ((resids.length() != 0) && (attrids.length() != 0))
/*      */       {
/*  818 */         String query = "select A.RESOURCEID,A.ATTRIBUTEID,B.DISPLAYNAME,A.CONFVALUE,A.REASON from " + tblName + " A,AM_ATTRIBUTES B where A.RESOURCEID in (" + resids.substring(0, resids.length() - 1) + ") and  LATEST=1 and B.ATTRIBUTEID in (" + attrids.substring(0, attrids.length() - 1) + ") and A.ATTRIBUTEID=B.ATTRIBUTEID";
/*  819 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  820 */         while (rs.next())
/*      */         {
/*  822 */           Properties existing = (Properties)outputData.get(rs.getString("RESOURCEID"));
/*  823 */           if (existing != null)
/*      */           {
/*  825 */             if (rs.getString("REASON") != null)
/*      */             {
/*  827 */               existing.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")) + "_REASON", rs.getString("REASON"));
/*      */             }
/*      */             else
/*      */             {
/*  831 */               existing.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")) + "_REASON", " ");
/*      */             }
/*  833 */             existing.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")), rs.getString("CONFVALUE"));
/*  834 */             outputData.put(rs.getString("RESOURCEID"), existing);
/*      */           }
/*      */           else
/*      */           {
/*  838 */             Properties newProp = new Properties();
/*  839 */             if (rs.getString("REASON") != null)
/*      */             {
/*  841 */               newProp.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")) + "_REASON", rs.getString("REASON"));
/*      */             }
/*      */             else
/*      */             {
/*  845 */               newProp.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")) + "_REASON", " ");
/*      */             }
/*      */             
/*  848 */             newProp.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")), rs.getString("CONFVALUE"));
/*  849 */             outputData.put(rs.getString("RESOURCEID"), newProp);
/*      */           }
/*      */         }
/*  852 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  854 */         query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where RESOURCEID IN(" + resids.substring(0, resids.length() - 1) + ")";
/*  855 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  856 */         while (rs.next())
/*      */         {
/*  858 */           Properties existing = (Properties)outputData.get(rs.getString("RESOURCEID"));
/*  859 */           if (existing != null)
/*      */           {
/*  861 */             existing.setProperty("DISPLAYNAME", FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*  862 */             outputData.put(rs.getString("RESOURCEID"), existing);
/*      */           }
/*      */           else
/*      */           {
/*  866 */             Properties newProp = new Properties();
/*  867 */             newProp.setProperty("DISPLAYNAME", FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*  868 */             outputData.put(rs.getString("RESOURCEID"), newProp);
/*      */           }
/*      */         }
/*  871 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  873 */         query = "select DISPLAYNAME,UNITS from AM_ATTRIBUTES WHERE ATTRIBUTEID IN(" + attrids.substring(0, attrids.length() - 1) + ") order by DISPLAYNAME desc";
/*  874 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  875 */         ArrayList attrdname = new ArrayList();
/*  876 */         HashMap attrUnits = new HashMap();
/*  877 */         while (rs.next()) {
/*  878 */           attrdname.add(FormatUtil.getString(rs.getString("DISPLAYNAME")));
/*  879 */           if ((rs.getString("UNITS") != null) && (!rs.getString("UNITS").equals(""))) {
/*  880 */             attrUnits.put(FormatUtil.getString(rs.getString("DISPLAYNAME")), "(" + rs.getString("UNITS") + ")");
/*      */           }
/*      */           else {
/*  883 */             attrUnits.put(FormatUtil.getString(rs.getString("DISPLAYNAME")), "");
/*      */           }
/*      */         }
/*  886 */         AMConnectionPool.closeStatement(rs);
/*  887 */         outputData.put("ATTRDISPNAME", attrdname);
/*  888 */         outputData.put("attrUnits", attrUnits);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  893 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  896 */       closeResultSet(rs);
/*      */     }
/*  898 */     return outputData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void setUserConfiguredMonitorTypes(ActionForm form, HttpServletRequest request)
/*      */   {
/*  905 */     ReportForm rf = (ReportForm)form;
/*  906 */     ArrayList availMonitorTypes = new ArrayList();
/*  907 */     AMConnectionPool pool = new AMConnectionPool();
/*  908 */     Vector resIds_vector = null;
/*  909 */     ResultSet rs = null;
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  915 */       String nwdcondition = "";
/*  916 */       String businessDashboardTypesCondition = "";
/*      */       
/*  918 */       nwdcondition = " and AM_ManagedResourceType.RESOURCEGROUP  not in ('NWD','SAN','EMO') ";
/*      */       
/*      */ 
/*  921 */       String monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where AM_ManagedObject.TYPE in " + Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME order by AM_ManagedResourceType.DISPLAYNAME";
/*  922 */       if (Constants.isPrivilegedUser(request))
/*      */       {
/*  924 */         if (Constants.isUserResourceEnabled()) {
/*  925 */           String userid = Constants.getLoginUserid(request);
/*  926 */           monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE inner join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID where AM_USERRESOURCESTABLE.USERID=" + userid + " and  AM_ManagedObject.TYPE in " + Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME  order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         } else {
/*  928 */           resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  929 */           monitortypeQuery = "select TYPE,AM_ManagedResourceType.DISPLAYNAME  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where   AM_ManagedObject.TYPE in " + Constants.resourceTypes + nwdcondition + businessDashboardTypesCondition + " and " + ManagedApplication.getCondition("RESOURCEID", resIds_vector) + " group by TYPE,AM_ManagedResourceType.DISPLAYNAME  order by AM_ManagedResourceType.DISPLAYNAME";
/*      */         }
/*      */       }
/*      */       
/*  933 */       System.out.println("setUserConfiguredMonitorTypes:monitortypeQuery" + monitortypeQuery);
/*  934 */       String defaultMonitorType = "";
/*      */       
/*      */ 
/*  937 */       rs = AMConnectionPool.executeQueryStmt(monitortypeQuery);
/*  938 */       boolean isWindowsAdded = false;
/*  939 */       while (rs.next())
/*      */       {
/*  941 */         if (defaultMonitorType.equals(""))
/*      */         {
/*  943 */           defaultMonitorType = rs.getString("TYPE");
/*      */         }
/*  945 */         String resourcetype = rs.getString("TYPE");
/*  946 */         String displayname = FormatUtil.getString(rs.getString("DISPLAYNAME"));
/*  947 */         if (resourcetype.indexOf("Windows") != -1)
/*      */         {
/*  949 */           if (isWindowsAdded) {
/*      */             continue;
/*      */           }
/*      */           
/*  953 */           isWindowsAdded = true;
/*  954 */           resourcetype = "$ComplexType_Windows";
/*  955 */           displayname = FormatUtil.getString("Windows");
/*      */         }
/*  957 */         else if (resourcetype.equals("file"))
/*      */         {
/*  959 */           displayname = FormatUtil.getString("am.monitortype.file.text");
/*      */         }
/*  961 */         else if (resourcetype.equals("directory"))
/*      */         {
/*  963 */           displayname = FormatUtil.getString("am.monitortype.directory.text");
/*      */         }
/*  965 */         Properties dataProps = new Properties();
/*  966 */         dataProps.setProperty("label", displayname);
/*  967 */         dataProps.setProperty("value", resourcetype);
/*  968 */         availMonitorTypes.add(dataProps);
/*      */       }
/*      */       
/*      */ 
/*  972 */       rf.setAvailMonitorTypes(availMonitorTypes);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  977 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  980 */       closeResultSet(rs);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward saveNote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  990 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  991 */     ResultSet set = null;
/*  992 */     String note = null;
/*  993 */     String selectQuery = null;
/*  994 */     int typeID = -1;
/*      */     try {
/*  996 */       response.setContentType("text/html;charset=UTF-8");
/*  997 */       int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/*  998 */       int attributeid = Integer.parseInt(request.getParameter("reasonid"));
/*  999 */       long collectiontime = Long.parseLong(request.getParameter("downtime"));
/* 1000 */       note = URLDecoder.decode(request.getParameter("textValue"));
/* 1001 */       String tblName = request.getParameter("tblname");
/* 1002 */       PrintWriter out1 = response.getWriter();
/*      */       
/* 1004 */       if (note != null)
/*      */       {
/* 1006 */         String updateQuery = "update " + tblName + " set REASON = '" + note + "' where RESOURCEID = " + resourceid + " and ATTRIBUTEID=" + attributeid + " and COLLECTIONTIME=" + collectiontime;
/*      */         try {
/* 1008 */           AMConnectionPool.executeUpdateStmt(updateQuery);
/* 1009 */           out1.println(note);
/*      */         }
/*      */         catch (Exception e) {
/* 1012 */           out1.println("-");
/* 1013 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1018 */       out1.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1022 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1025 */       closeResultSet(set);
/*      */     }
/* 1027 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward cancelNote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1034 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1035 */     ResultSet set = null;
/* 1036 */     String note = null;
/* 1037 */     String selectQuery = null;
/* 1038 */     int typeID = -1;
/*      */     try {
/* 1040 */       response.setContentType("text/html;charset=UTF-8");
/* 1041 */       int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/* 1042 */       int attributeid = Integer.parseInt(request.getParameter("reasonid"));
/* 1043 */       long collectiontime = Long.parseLong(request.getParameter("downtime"));
/* 1044 */       note = URLDecoder.decode(request.getParameter("textValue"));
/* 1045 */       String tblName = request.getParameter("tblname");
/* 1046 */       PrintWriter out1 = response.getWriter();
/*      */       
/* 1048 */       if (note != null)
/*      */       {
/* 1050 */         String Query = "select REASON from " + tblName + " where RESOURCEID = " + resourceid + " and ATTRIBUTEID=" + attributeid + " and COLLECTIONTIME=" + collectiontime;
/*      */         try {
/* 1052 */           set = AMConnectionPool.executeQueryStmt(Query);
/*      */           
/* 1054 */           if (set.next())
/*      */           {
/* 1056 */             if (set.getString("REASON") != null)
/*      */             {
/* 1058 */               out1.println(set.getString("REASON"));
/*      */             }
/*      */             else
/*      */             {
/* 1062 */               out1.println("");
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/* 1067 */             out1.println("");
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1072 */           out1.println("-");
/* 1073 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1078 */       out1.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1082 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1085 */       closeResultSet(set);
/*      */     }
/* 1087 */     return null;
/*      */   }
/*      */   
/*      */   public HashMap configurationDetails(int resourceid, int attributeid)
/*      */   {
/* 1092 */     HashMap confDetails = new HashMap();
/* 1093 */     HashMap ConfProp = new HashMap();
/* 1094 */     Properties propLnk = new Properties();
/* 1095 */     ResultSet rs = null;
/* 1096 */     String query = null;
/* 1097 */     String tblName = null;
/*      */     try {
/* 1099 */       tblName = "AM_CONFIGURATION_INFO";
/*      */       
/* 1101 */       query = "select A.ATTRIBUTEID,B.DISPLAYNAME,A.CONFVALUE from  " + tblName + " A,AM_ATTRIBUTES B where A.ATTRIBUTEID=B.ATTRIBUTEID and A.LATEST=1 and A.RESOURCEID=" + resourceid + " order by DISPLAYNAME";
/* 1102 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1103 */       while (rs.next()) {
/* 1104 */         ConfProp.put(FormatUtil.getString(rs.getString("DISPLAYNAME")), rs.getString("CONFVALUE"));
/* 1105 */         propLnk.setProperty(FormatUtil.getString(rs.getString("DISPLAYNAME")), rs.getString("ATTRIBUTEID"));
/*      */       }
/* 1107 */       AMConnectionPool.closeStatement(rs);
/* 1108 */       confDetails.put("ConfProp", ConfProp);
/* 1109 */       confDetails.put("Proplnk", propLnk);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1113 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1116 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1118 */     return confDetails;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ConfigurationDataAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */