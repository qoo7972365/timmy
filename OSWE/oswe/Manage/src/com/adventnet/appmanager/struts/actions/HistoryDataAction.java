/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import com.adventnet.awolf.chart.ChartInfo;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HistoryDataAction
/*      */   extends DispatchAction
/*      */ {
/*   71 */   private final long sevenDaysInMillis = 604800000L;
/*      */   
/*   73 */   private final long thirtyDaysInMillis = 2592000000L;
/*      */   
/*   75 */   private final long oneYearInMillis = 31536000000L;
/*   76 */   private final long lastquatarInMillis = 7776000000L;
/*   77 */   private final long lasthalfInMillis = 15552000000L;
/*      */   
/*   79 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */   public ActionForward getIndividualURLandCompareReportsData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   85 */     ReportForm rf = (ReportForm)form;
/*   86 */     ActionMessages messages = new ActionMessages();
/*   87 */     ActionErrors errors = new ActionErrors();
/*   88 */     ResultSet rs_inds = null;
/*   89 */     ResultSet rs_urls = null;
/*   90 */     ResultSet resNameSet = null;
/*   91 */     ResultSet titleSet = null;
/*      */     
/*      */ 
/*   94 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*   95 */     String attID = request.getParameter("attributeid");
/*   96 */     String childID = request.getParameter("childid");
/*   97 */     String period = request.getParameter("period");
/*   98 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(attID);
/*   99 */     String archivedTableName = (String)attribDetails.get(0);
/*  100 */     String expression1 = (String)attribDetails.get(1);
/*  101 */     request.setAttribute("period", period);
/*  102 */     String startdate = request.getParameter("startdate");
/*      */     
/*  104 */     if (startdate == null) {
/*  105 */       startdate = rf.getStartDate();
/*      */     }
/*      */     else
/*      */     {
/*  109 */       rf.setStartDate(startdate);
/*      */     }
/*      */     
/*  112 */     String enddate = request.getParameter("enddate");
/*      */     
/*  114 */     if (enddate == null) {
/*  115 */       enddate = rf.getEndDate();
/*      */     }
/*      */     else
/*      */     {
/*  119 */       rf.setEndDate(enddate);
/*      */     }
/*      */     
/*  122 */     String status = null;
/*  123 */     String attName = null;
/*  124 */     ReportUtilities rep = new ReportUtilities();
/*  125 */     String valueforpdf = rep.getValueForPeriodForPDF(period);
/*  126 */     long customstartTime = 0L;
/*  127 */     long customendTime = 0L;
/*  128 */     long startTime = 0L;
/*  129 */     long endTime = System.currentTimeMillis();
/*  130 */     String businessRule = request.getParameter("businessPeriod");
/*  131 */     System.out.println("^^^^^^^^^^^^^^^^ B RULE COMPARE^^^^^^^^^^^^^^^^^^^ " + businessRule);
/*  132 */     Hashtable bHourDetail = new Hashtable();
/*  133 */     boolean forbHr = false;
/*  134 */     if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */     {
/*  136 */       bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/*  137 */       forbHr = true;
/*  138 */       request.setAttribute("businessPeriod", businessRule);
/*  139 */       rf.setBusinessPeriod(businessRule);
/*  140 */       request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/*  141 */       request.setAttribute("bhourDetail", bHourDetail);
/*      */     }
/*      */     else
/*      */     {
/*  145 */       request.setAttribute("businessPeriod", "oni");
/*  146 */       rf.setBusinessPeriod(businessRule);
/*      */     }
/*  148 */     request.setAttribute("startdate", startdate);
/*  149 */     request.setAttribute("enddate", enddate);
/*  150 */     System.out.println(startdate + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% START--- END %%%%%%%%%%%%%%%%%%%" + enddate);
/*      */     
/*  152 */     if (period.equals("4"))
/*      */     {
/*  154 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  155 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*  156 */       if (customstartTime > customendTime) {
/*  157 */         status = "Start Time " + new java.util.Date(customstartTime) + " is greater than the End Time " + new java.util.Date(customendTime);
/*  158 */         startTime = customstartTime;
/*  159 */         endTime = customendTime;
/*      */       }
/*      */       else {
/*  162 */         startTime = customstartTime;
/*  163 */         endTime = customendTime;
/*  164 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/*  165 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*      */         
/*  167 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(startTime).toString(), new java.util.Date(endTime).toString() });
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  172 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/*  173 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/*  174 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/*  176 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(startTime).toString(), new java.util.Date(endTime).toString() });
/*      */     }
/*  178 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/*  180 */       String brQuery = "select ID,NAME from AM_BUSINESSHOURSDETAILS where STATUS='enable'";
/*  181 */       rs_br = AMConnectionPool.executeQueryStmt(brQuery);
/*  182 */       ArrayList brules = new ArrayList();
/*  183 */       while (rs_br.next())
/*      */       {
/*  185 */         Properties dataProps = new Properties();
/*  186 */         dataProps.setProperty("label", rs_br.getString("NAME"));
/*  187 */         dataProps.setProperty("value", rs_br.getString("ID"));
/*  188 */         brules.add(dataProps);
/*      */       }
/*  190 */       AMConnectionPool.closeStatement(rs_br);
/*  191 */       rf.setBusinessrules(brules);
/*      */       
/*      */ 
/*  194 */       if (resID != -1)
/*      */       {
/*      */ 
/*  197 */         String resNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resID;
/*  198 */         String typequery = "select type from AM_ManagedObject where resourceid=" + resID;
/*  199 */         ResultSet rs = null;
/*  200 */         resNameSet = AMConnectionPool.executeQueryStmt(resNameQuery);
/*  201 */         if (resNameSet.next()) {
/*  202 */           String resName = EnterpriseUtil.decodeString(resNameSet.getString("DISPLAYNAME"));
/*  203 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  208 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID IN (" + attID + ")";
/*  209 */       titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */       
/*  211 */       while (titleSet.next()) {
/*  212 */         String title = titleSet.getString("DISPLAYNAME");
/*  213 */         attName = titleSet.getString("ATTRIBUTE");
/*  214 */         request.setAttribute("monitortype", title);
/*  215 */         String unit = titleSet.getString("UNITS");
/*  216 */         if (unit == null) {
/*  217 */           unit = "";
/*      */         }
/*  219 */         request.setAttribute("unit", unit);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  225 */       ArrayList list1 = new ArrayList();
/*  226 */       ArrayList pdfdata = new ArrayList();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  232 */       String query12 = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.ATTRIBUTEID from AM_ManagedObject,AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID IN (" + childID + ") and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.ATTRIBUTEID in (" + attID + ")ORDER BY AM_ManagedObject.RESOURCEID";
/*      */       int minValue;
/*      */       try {
/*  235 */         rs_urls = AMConnectionPool.executeQueryStmt(query12);
/*      */         
/*  237 */         List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*  238 */         while (rs_urls.next()) {
/*  239 */           ArrayList l1 = new ArrayList();
/*  240 */           String rid = rs_urls.getString("RESOURCEID");
/*  241 */           String attid = rs_urls.getString("ATTRIBUTEID");
/*  242 */           String name = rs_urls.getString("DISPLAYNAME");
/*      */           
/*      */ 
/*  245 */           if (allSecondLevelAttribute.contains(attid))
/*      */           {
/*  247 */             String displayname = ReportUtil.getDisplayNameForAttribute(Integer.parseInt(rid));
/*      */             
/*  249 */             name = displayname + "_" + name;
/*      */           }
/*  251 */           l1.add(rid + "_" + attid);
/*      */           
/*  253 */           if (name.indexOf("\\") != -1)
/*      */           {
/*  255 */             name = FormatUtil.findReplace(name, "\\", "/");
/*      */           }
/*  257 */           l1.add(name);
/*  258 */           list1.add(l1);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  263 */         long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/*      */         
/*      */ 
/*      */ 
/*  267 */         String url_qry = DBQueryUtil.getDBQuery("am.historyDataAction.getCustomApplications.query", new String[] { archivedTableName, childID, attID, expression1 });
/*      */         
/*      */ 
/*  270 */         String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME >='" + startTime + "' and ARCHIVEDTIME <='" + endTime + "' order by ARCHIVEDTIME desc";
/*  271 */         if (timeStamp[2] > 0L)
/*      */         {
/*  273 */           dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " or DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ") order by ARCHIVEDTIME desc";
/*      */         }
/*      */         
/*  276 */         url_qry = url_qry + dailyRptCondition;
/*      */         
/*  278 */         request.setAttribute("STIME", timeStamp[0] + "");
/*  279 */         request.setAttribute("ETIME", timeStamp[1] + "");
/*  280 */         request.setAttribute("dailyStime", timeStamp[2] + "");
/*  281 */         request.setAttribute("dailyEtime", timeStamp[3] + "");
/*      */         
/*      */ 
/*  284 */         rs_inds = AMConnectionPool.executeQueryStmt(url_qry);
/*  285 */         ArrayList times = new ArrayList();
/*  286 */         HashMap dataprops = null;
/*  287 */         Calendar cal = Calendar.getInstance();
/*  288 */         while (rs_inds.next())
/*      */         {
/*  290 */           boolean isbHrdata = false;
/*      */           try {
/*  292 */             if (bHourDetail.size() != 0)
/*      */             {
/*  294 */               cal.setTimeInMillis(rs_inds.getLong("ARCHIVEDTIME"));
/*  295 */               int weekday = cal.get(7) - 1;
/*  296 */               int hour = cal.get(11);
/*      */               
/*      */ 
/*  299 */               int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/*  300 */               int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/*  301 */               if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*      */               {
/*  303 */                 isbHrdata = true;
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  310 */             System.out.println("############# Error in Bhour ############" + ex.toString());
/*      */           }
/*  312 */           if ((!forbHr) || ((forbHr) && (isbHrdata)))
/*      */           {
/*  314 */             long archivedTime = rs_inds.getLong("ARCHIVEDTIME");
/*  315 */             minValue = rs_inds.getInt("MINVALUE");
/*  316 */             int maxValue = rs_inds.getInt("MAXVALUE");
/*  317 */             double avgValue = rs_inds.getDouble("AVG");
/*  318 */             int attributeId = rs_inds.getInt("ATTRIBUTEID");
/*      */             
/*      */ 
/*      */             try
/*      */             {
/*  323 */               if ((attributeId == 224) || (attributeId == 11)) {
/*  324 */                 minValue /= 1024;
/*  325 */                 maxValue /= 1024;
/*  326 */                 avgValue /= 1024.0D;
/*      */               }
/*      */             }
/*      */             catch (Exception exc) {
/*  330 */               exc.printStackTrace();
/*      */             }
/*      */             
/*  333 */             String resid = rs_inds.getString("RESID");
/*  334 */             String attid = rs_inds.getString("ATTRIBUTEID");
/*  335 */             String key = resid + "_" + attid;
/*  336 */             Properties prop = new Properties();
/*  337 */             prop.setProperty("MINVALUE", String.valueOf(minValue));
/*  338 */             prop.setProperty("MAXVALUE", String.valueOf(maxValue));
/*  339 */             prop.setProperty("AVGVALUE", String.valueOf((float)Math.round(avgValue * 100.0D) / 100.0F));
/*      */             
/*      */ 
/*  342 */             ArrayList a1 = checkArchivedtime(pdfdata, archivedTime);
/*  343 */             if (a1 == null) {
/*  344 */               a1 = new ArrayList();
/*  345 */               a1.add(new Long(archivedTime));
/*  346 */               dataprops = new HashMap();
/*  347 */               a1.add(dataprops);
/*  348 */               pdfdata.add(a1);
/*      */             }
/*  350 */             dataprops.put(key, prop);
/*      */           }
/*      */         }
/*      */         
/*  354 */         rs_urls.close();
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*      */ 
/*  360 */         exc.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*  364 */       request.setAttribute("pdfdata", pdfdata);
/*  365 */       request.setAttribute("list", list1);
/*  366 */       request.setAttribute("attID", String.valueOf(attID));
/*  367 */       request.setAttribute("period", period);
/*  368 */       request.setAttribute("CompareUrl", "true");
/*  369 */       if ((list1.size() == 0) || (pdfdata.size() == 0))
/*      */       {
/*  371 */         request.setAttribute("STATUS", status);
/*      */       }
/*      */       else
/*      */       {
/*  375 */         status = "SUCCESS";
/*  376 */         request.setAttribute("starttime", new java.util.Date(startTime));
/*  377 */         request.setAttribute("endtime", new java.util.Date(endTime));
/*      */         
/*  379 */         request.setAttribute("STATUS", status);
/*      */       }
/*      */       
/*      */ 
/*  383 */       String yaxis = (String)request.getAttribute("monitortype") + " " + (String)request.getAttribute("unit");
/*  384 */       if ("pdf".equals(rf.getReporttype())) {
/*  385 */         ChartInfo cinfo = new ChartInfo();
/*  386 */         SummaryBean sumgraph = new SummaryBean();
/*  387 */         String startTime1 = (String)request.getAttribute("STIME");
/*  388 */         String endTime1 = (String)request.getAttribute("ETIME");
/*  389 */         String dailyRptStarttime = (String)request.getAttribute("dailyStime");
/*  390 */         String dailyRptEndtime = (String)request.getAttribute("dailyEtime");
/*  391 */         sumgraph.setResid(childID);
/*  392 */         sumgraph.setAttributeid(attID);
/*      */         
/*      */ 
/*  395 */         sumgraph.setStarttime(startTime1);
/*  396 */         sumgraph.setEndtime(endTime1);
/*      */         
/*  398 */         if ((dailyRptStarttime != null) && (!dailyRptStarttime.equals("0")))
/*      */         {
/*  400 */           sumgraph.setDailyRptStarttime(dailyRptStarttime);
/*  401 */           sumgraph.setDailyRptEndtime(dailyRptEndtime);
/*      */         }
/*  403 */         sumgraph.setArchivedforUrl(true);
/*  404 */         sumgraph.setCompareUrls(true);
/*  405 */         sumgraph.setBhrDetails(bHourDetail);
/*  406 */         cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*  407 */         cinfo.setYaxisLabel(yaxis);
/*  408 */         cinfo.setShape(true);
/*  409 */         cinfo.setCustomDateAxis(true);
/*  410 */         cinfo.setCustomAngle(270.0D);
/*  411 */         cinfo.setDataSet(sumgraph);
/*  412 */         cinfo.setHeight("220");
/*  413 */         int sizeofseq = list1.size();
/*  414 */         if (sizeofseq < 5) {
/*  415 */           cinfo.setHeight("300");
/*  416 */           cinfo.setWidth("700");
/*      */         }
/*  418 */         else if ((sizeofseq >= 5) && (sizeofseq < 10)) {
/*  419 */           cinfo.setHeight("500");
/*  420 */           cinfo.setWidth("700");
/*      */ 
/*      */         }
/*  423 */         else if ((sizeofseq >= 10) && (sizeofseq < 20)) {
/*  424 */           cinfo.setHeight("800");
/*  425 */           cinfo.setWidth("700");
/*      */         }
/*  427 */         else if ((sizeofseq >= 20) && (sizeofseq < 30)) {
/*  428 */           cinfo.setHeight("900");
/*  429 */           cinfo.setWidth("700");
/*      */         }
/*  431 */         else if ((sizeofseq >= 30) && (sizeofseq < 40)) {
/*  432 */           cinfo.setHeight("1000");
/*  433 */           cinfo.setWidth("800");
/*      */         }
/*      */         else {
/*  436 */           cinfo.setHeight("1100");
/*  437 */           cinfo.setWidth("900");
/*      */         }
/*      */         
/*      */ 
/*  441 */         request.setAttribute("reportname", "SevenThirtyAttributeReport");
/*      */         
/*  443 */         request.setAttribute("height", cinfo.getHeight());
/*  444 */         request.setAttribute("report-type-template", "report.individualURLandComparision");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  449 */         String image = cinfo.getTimeChartAsJPG();
/*      */         
/*      */ 
/*      */ 
/*  453 */         request.setAttribute("attributeImage", image);
/*      */         
/*      */ 
/*      */ 
/*  457 */         request.setAttribute("period", period);
/*  458 */         request.setAttribute("periodvalue", valueforpdf);
/*  459 */         request.setAttribute("sp-report-type", "pdf");
/*  460 */         String reportType = (String)request.getAttribute("report-type-template");
/*      */         
/*      */ 
/*  463 */         return mapping.findForward("historydata.success.pdf"); }
/*      */       BufferedInputStream bis;
/*  465 */       if (rf.getReporttype().equalsIgnoreCase("csv"))
/*      */       {
/*  467 */         bis = null;
/*  468 */         BufferedOutputStream bos = null;
/*  469 */         OutputStream out = null;
/*      */         try
/*      */         {
/*  472 */           String reportValue = (String)request.getAttribute("monitortype");
/*  473 */           String reportName = "SevenThirtyAttributeReport_" + new java.sql.Date(System.currentTimeMillis()) + ".csv";
/*  474 */           response.setContentType("application/vnd.ms-excel");
/*  475 */           response.setHeader("Content-disposition", "attachment; filename=" + reportName);
/*  476 */           StringBuilder builder = new StringBuilder();
/*      */           
/*  478 */           builder.append(FormatUtil.getString("am.webclient.urlattributetitlePDF", new String[] { valueforpdf, reportValue })).append("\n");
/*      */           
/*  480 */           for (int i = 0; i < list1.size(); i++)
/*      */           {
/*  482 */             ArrayList d1 = (ArrayList)list1.get(i);
/*  483 */             builder.append(d1.get(1));
/*  484 */             if (i < list1.size() - 1)
/*      */             {
/*  486 */               builder.append(",");
/*      */             }
/*      */           }
/*  489 */           builder.append("\n");
/*  490 */           for (int i = 0; i < pdfdata.size(); i++)
/*      */           {
/*  492 */             ArrayList a1 = (ArrayList)pdfdata.get(i);
/*  493 */             long archivedTime = ((Long)a1.get(0)).longValue();
/*  494 */             SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
/*  495 */             String dateStr = sdf.format(new java.util.Date(archivedTime));
/*  496 */             builder.append(dateStr).append(",");
/*  497 */             for (int k = 1; k < a1.size(); k++)
/*      */             {
/*  499 */               for (int m = 0; m < list1.size(); m++)
/*      */               {
/*  501 */                 ArrayList q1 = (ArrayList)list1.get(m);
/*  502 */                 String key = (String)q1.get(0);
/*  503 */                 HashMap h1 = (HashMap)a1.get(k);
/*  504 */                 String avgvalues = null;
/*      */                 
/*  506 */                 Properties p1 = (Properties)h1.get(key);
/*      */                 
/*  508 */                 if (p1 != null)
/*      */                 {
/*  510 */                   avgvalues = p1.getProperty("AVGVALUE");
/*      */                 }
/*      */                 else
/*      */                 {
/*  514 */                   avgvalues = "-";
/*      */                 }
/*  516 */                 builder.append(avgvalues);
/*  517 */                 if (m < list1.size() - 1)
/*      */                 {
/*  519 */                   builder.append(",");
/*      */                 }
/*      */               }
/*      */             }
/*  523 */             if (i < pdfdata.size() - 1)
/*      */             {
/*  525 */               builder.append("\n");
/*      */             }
/*      */           }
/*  528 */           String outputData = builder.toString();
/*      */           try
/*      */           {
/*  531 */             out = response.getOutputStream();
/*      */           }
/*      */           catch (IOException ioe)
/*      */           {
/*  535 */             ioe.printStackTrace();
/*      */           }
/*  537 */           bis = new BufferedInputStream(new ByteArrayInputStream(outputData.getBytes()));
/*  538 */           bos = new BufferedOutputStream(out);
/*  539 */           byte[] buff = new byte['ࠀ'];
/*      */           int bytesRead;
/*  541 */           while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
/*      */           {
/*  543 */             bos.write(buff, 0, bytesRead);
/*  544 */             bos.flush();
/*      */           }
/*  546 */           out.flush();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  550 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  554 */           if (out != null)
/*      */           {
/*  556 */             out.close();
/*      */           }
/*  558 */           if (bis != null)
/*      */           {
/*  560 */             bis.close();
/*      */           }
/*  562 */           if (bos != null)
/*      */           {
/*  564 */             bos.close();
/*      */           }
/*      */         }
/*  567 */         return null;
/*      */       }
/*      */       
/*  570 */       System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% LAST %%%%%%%%%%%%%%%%%%%");
/*  571 */       return mapping.findForward("historydata.compare.success");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*      */       ResultSet rs_br;
/*  576 */       request.setAttribute("note", "1");
/*  577 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("historydata.exception", exp.toString()));
/*  578 */       saveErrors(request, errors);
/*  579 */       return mapping.getInputForward();
/*      */     }
/*      */     finally {
/*  582 */       closeResultSet(rs_urls);
/*  583 */       closeResultSet(rs_inds);
/*  584 */       closeResultSet(resNameSet);
/*  585 */       closeResultSet(titleSet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  595 */     ReportForm rf = (ReportForm)form;
/*  596 */     ActionMessages messages = new ActionMessages();
/*  597 */     ActionErrors errors = new ActionErrors();
/*  598 */     ResultSet set = null;
/*  599 */     ResultSet set1 = null;
/*  600 */     boolean isConfMonitor = false;
/*  601 */     ResultSet resNameSet = null;
/*  602 */     ResultSet titleSet = null;
/*  603 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/*  604 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/*  605 */     System.out.println(resID + " ---->RESID^^^^^^^^^^^^^^^^ GETDATA ^^^^^^^^^^^^^^^^^^^ ATTID ---> " + attID);
/*  606 */     String durationCondition = " and DURATION=1";
/*  607 */     String frompop = request.getParameter("frompop");
/*  608 */     String businessRule = request.getParameter("businessPeriod");
/*  609 */     System.out.println("^^^^^^^^^^^^^^^^ B RULE ^^^^^^^^^^^^^^^^^^^ " + businessRule);
/*      */     
/*  611 */     String period = request.getParameter("period");
/*  612 */     request.setAttribute("period", period);
/*  613 */     String startdate = rf.getStartDate();
/*      */     
/*      */ 
/*  616 */     String enddate = rf.getEndDate();
/*      */     
/*  618 */     String status = null;
/*  619 */     long customstartTime = 0L;
/*  620 */     long customendTime = 0L;
/*  621 */     boolean forbHr = false;
/*  622 */     System.out.println(period + "^^^^^^^^^^^^^^^^ PERIOD PRINT ^^^^^^^^^^^^^^^^ START ---> " + startdate + " End date -----> " + enddate);
/*  623 */     long startTime = 0L;
/*  624 */     long endTime = System.currentTimeMillis();
/*  625 */     Hashtable bHourDetail = new Hashtable();
/*  626 */     if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */     {
/*  628 */       bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/*      */       
/*  630 */       forbHr = true;
/*  631 */       request.setAttribute("businessPeriod", businessRule);
/*  632 */       rf.setBusinessPeriod(businessRule);
/*  633 */       request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/*      */     }
/*      */     else
/*      */     {
/*  637 */       request.setAttribute("businessPeriod", "oni");
/*  638 */       rf.setBusinessPeriod(businessRule);
/*      */     }
/*      */     
/*  641 */     Properties rawproperties = DBUtil.getRawValue();
/*  642 */     request.setAttribute("rawvalues", rawproperties);
/*  643 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(String.valueOf(attID));
/*  644 */     String archivedTableName = (String)attribDetails.get(0);
/*  645 */     String expression1 = (String)attribDetails.get(1);
/*  646 */     if (period.equals("4"))
/*      */     {
/*  648 */       request.setAttribute("endDate", enddate);
/*  649 */       request.setAttribute("startDate", startdate);
/*  650 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  651 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*      */       
/*  653 */       if (customstartTime > customendTime)
/*      */       {
/*  655 */         status = "Start Time " + new java.util.Date(customstartTime) + " is greater than the End Time " + new java.util.Date(customendTime);
/*  656 */         startTime = customstartTime;
/*  657 */         endTime = customendTime;
/*      */       }
/*      */       else
/*      */       {
/*  661 */         startTime = customstartTime;
/*  662 */         endTime = customendTime;
/*  663 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/*  664 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*      */         
/*  666 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/*  672 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/*  673 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/*  674 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/*  676 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */     }
/*      */     
/*  679 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  680 */     ArrayList list = new ArrayList();
/*  681 */     ArrayList avgList = new ArrayList();
/*  682 */     String attName = null;
/*  683 */     long min = -1L;
/*  684 */     long max = -1L;
/*  685 */     float avg = -1.0F;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  690 */       String resNameQuery = "select DISPLAYNAME,TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/*  691 */       typequery = "select type from AM_ManagedObject where resourceid=" + resID;
/*  692 */       ResultSet rs = null;
/*      */       
/*  694 */       resNameSet = AMConnectionPool.executeQueryStmt(resNameQuery);
/*  695 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*  696 */       if (resNameSet.next())
/*      */       {
/*  698 */         String resName = EnterpriseUtil.decodeString(resNameSet.getString("DISPLAYNAME"));
/*  699 */         rf.setSevenThirtyAttribCln(getAttributeCollection(String.valueOf(resID), String.valueOf(attID), resNameSet.getString("TYPE")));
/*  700 */         rf.setSevenThirtyAttrib(resID + "#" + attID);
/*      */         
/*  702 */         rf.setAllPerformanceAttrbsCln(getAttributeCollection(String.valueOf(resID), String.valueOf(attID), resNameSet.getString("TYPE"), true));
/*  703 */         rf.setAllPerformanceAttrbs(resID + "#" + attID);
/*      */         
/*  705 */         if (attID == 711)
/*      */         {
/*      */           try {
/*  708 */             HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/*  709 */             AMLog.info("HistoryDataAction : getData (" + resID + "," + attID + "): getDisplayNameForDisk ==============>" + alldisplayname);
/*  710 */             String name = resName;
/*  711 */             AMLog.info("HistoryDataAction : getData (" + resID + "," + attID + "): name ==============>" + name);
/*  712 */             String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/*  713 */             String[] temp = dname1.split(":", 2);
/*  714 */             String s = alldisplayname.get(temp[0]).toString();
/*  715 */             if ((s != null) && (temp.length > 1))
/*      */             {
/*  717 */               dname1 = s + ":" + temp[1];
/*      */             }
/*  719 */             AMLog.info("HistoryDataAction : getData (" + resID + "," + attID + "): dname1 ==============>" + dname1);
/*  720 */             request.setAttribute("resourcename", dname1);
/*      */           }
/*      */           catch (Exception ee)
/*      */           {
/*  724 */             ee.printStackTrace();
/*  725 */             AMLog.info("HistoryDataAction : getData (" + resID + "," + attID + "): ERROR WHILE PROCESSING DISK : " + resName + " ==============>" + ee.getMessage());
/*  726 */             request.setAttribute("resourcename", resName);
/*      */           }
/*      */         }
/*  729 */         else if (allSecondLevelAttribute.contains(attID + ""))
/*      */         {
/*  731 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/*  732 */           String dname2 = displayname + "_" + resName;
/*  733 */           if (dname2.indexOf("\\") != -1)
/*      */           {
/*  735 */             dname2 = FormatUtil.findReplace(dname2, "\\", "/");
/*      */           }
/*  737 */           request.setAttribute("resourcename", dname2);
/*      */         }
/*      */         else
/*      */         {
/*  741 */           if (resName.indexOf("\\") != -1)
/*      */           {
/*  743 */             resName = FormatUtil.findReplace(resName, "\\", "/");
/*      */           }
/*  745 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/*  748 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS,RESOURCETYPE from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/*      */       try {
/*  750 */         titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */         
/*  752 */         if (titleSet.next())
/*      */         {
/*  754 */           String title = titleSet.getString("DISPLAYNAME");
/*  755 */           attName = titleSet.getString("ATTRIBUTE");
/*  756 */           request.setAttribute("monitortype", title);
/*  757 */           String unit = titleSet.getString("UNITS");
/*  758 */           if (unit == null)
/*      */           {
/*  760 */             unit = "";
/*      */           }
/*  762 */           request.setAttribute("unit", unit);
/*  763 */           String serverType = titleSet.getString("RESOURCETYPE");
/*  764 */           isConfMonitor = ConfMonitorConfiguration.getInstance().isConfMonitor(serverType);
/*  765 */           request.setAttribute("servertype", serverType);
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*  770 */         ee.printStackTrace();
/*  771 */         AMLog.info("HistoryDataAction : getData (" + resID + "," + attID + "): ERROR WHILE GETTING ATTRIBUTE details: ==============>" + ee.getMessage());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  776 */       String brQuery = "select ID,NAME from AM_BUSINESSHOURSDETAILS where STATUS='enable'";
/*  777 */       ResultSet rs_br = null;
/*  778 */       ArrayList brules = new ArrayList();
/*      */       try
/*      */       {
/*  781 */         rs_br = AMConnectionPool.executeQueryStmt(brQuery);
/*      */         
/*  783 */         while (rs_br.next())
/*      */         {
/*  785 */           Properties dataProps = new Properties();
/*  786 */           dataProps.setProperty("label", rs_br.getString("NAME"));
/*  787 */           dataProps.setProperty("value", rs_br.getString("ID"));
/*  788 */           brules.add(dataProps);
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {
/*  792 */         ee.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  796 */         AMConnectionPool.closeStatement(rs_br);
/*      */       }
/*  798 */       AMLog.info("HistoryDataAction : getData : setBusinessrules ======>" + brules);
/*  799 */       rf.setBusinessrules(brules);
/*      */       
/*  801 */       if ((period != null) && (period.equals("14")))
/*      */       {
/*  803 */         ArrayList raw = new ArrayList();
/*  804 */         String RawVal = rawproperties.getProperty("rawvalue");
/*  805 */         long TI = Long.parseLong(RawVal) * 24L * 60L * 60L * 1000L;
/*  806 */         long ET = System.currentTimeMillis();
/*  807 */         long ST = ET - TI;
/*  808 */         String q1 = null;
/*  809 */         if ((attID == 1357) || (attID == 1377) || (attID == 1457) || (attID == 1473) || (attID == 708) || (attID == 1394) || (attID == 1107) || (attID == 1207) || (attID == 1307) || (attID == 1657) || (attID == 807))
/*      */         {
/*  811 */           q1 = "select CPUUTIL AS VALUE,COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID=" + resID + " and COLLECTIONTIME<=" + ET + " and COLLECTIONTIME>=" + ST + " and CPUUTIL>=0 order by COLLECTIONTIME desc";
/*      */         }
/*      */         
/*  814 */         if ((attID == 1352) || (attID == 1372) || (attID == 1452) || (attID == 1472) || (attID == 702) || (attID == 1392) || (attID == 1102) || (attID == 1202) || (attID == 803) || (attID == 1302) || (attID == 1652))
/*      */         {
/*  816 */           q1 = "select PHYMEMUTIL AS VALUE,COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID=" + resID + " and COLLECTIONTIME<=" + ET + " and COLLECTIONTIME>=" + ST + " and PHYMEMUTIL>=0 order by COLLECTIONTIME desc";
/*      */         }
/*      */         
/*  819 */         set = AMConnectionPool.executeQueryStmt(q1);
/*  820 */         while (set.next())
/*      */         {
/*  822 */           long collectionTime = set.getLong("COLLECTIONTIME");
/*  823 */           int rValue = set.getInt("VALUE");
/*  824 */           Properties prop = new Properties();
/*  825 */           prop.put("COLLECTIONTIME", new Long(collectionTime));
/*  826 */           prop.put("VALUE", new Integer(rValue));
/*  827 */           raw.add(prop);
/*      */         }
/*  829 */         request.setAttribute("rawdata", raw);
/*      */         
/*  831 */         if (raw.size() == 0)
/*      */         {
/*  833 */           request.setAttribute("STATUS", status);
/*      */         }
/*      */         else
/*      */         {
/*  837 */           status = "SUCCESS";
/*  838 */           request.setAttribute("starttime", new java.util.Date(ST));
/*  839 */           request.setAttribute("endtime", new java.util.Date(ET));
/*  840 */           request.setAttribute("STATUS", status);
/*      */         }
/*      */       }
/*      */       int hour;
/*  844 */       if ((period != null) && (period.equals("20")))
/*      */       {
/*  846 */         ArrayList raw = new ArrayList();
/*  847 */         String ResName = "";
/*  848 */         String tableName = "";
/*  849 */         String resourceId = "";
/*  850 */         String valueCol = "";
/*  851 */         String attidCol = "";
/*  852 */         String colTime = "";
/*  853 */         String stTime = "";
/*  854 */         String enTime = "";
/*  855 */         String Condition = "";
/*  856 */         String expression = "";
/*  857 */         DecimalFormat twoDecPer = new DecimalFormat("##.##");
/*  858 */         String paramQuery = "select Datatable,resid_col,value_col,coltime_val,attid_col,expression from AM_ATTRIBUTES_EXT where attributeid=" + attID;
/*  859 */         System.out.println("paramQuery=======>" + paramQuery);
/*  860 */         set = AMConnectionPool.executeQueryStmt(paramQuery);
/*  861 */         ArrayList dbtables = new ArrayList();
/*  862 */         isConfMonitor = (!isConfMonitor) && (request.getParameter("confStartTime") != null) && (!request.getParameter("confStartTime").trim().equals("")) ? true : isConfMonitor;
/*      */         
/*  864 */         String confStartTime = request.getParameter("confStartTime") != null ? request.getParameter("confStartTime") : "";
/*  865 */         String confEndTime = request.getParameter("confEndTime") != null ? request.getParameter("confEndTime") : "";
/*      */         
/*  867 */         request.setAttribute("confStartTime", confStartTime);
/*  868 */         request.setAttribute("isConfMonitor", Boolean.valueOf(isConfMonitor));
/*  869 */         request.setAttribute("confEndTime", confEndTime);
/*      */         
/*  871 */         while (set.next())
/*      */         {
/*  873 */           tableName = set.getString("Datatable");
/*  874 */           resourceId = set.getString("resid_col");
/*  875 */           valueCol = set.getString("value_col");
/*  876 */           colTime = set.getString("coltime_val");
/*  877 */           attidCol = set.getString("attid_col");
/*  878 */           expression = set.getString("expression");
/*  879 */           if (isConfMonitor) {
/*  880 */             if ((tableName.equals("AM_ManagedObjectData")) && (valueCol.equalsIgnoreCase("RESPONSETIME"))) {
/*  881 */               ArrayList dbtableNameList = ConfMonitorUtil.getInstance().getCurrentTable((String)request.getAttribute("servertype"), "");
/*  882 */               tableName = (String)dbtableNameList.get(0);
/*  883 */               resourceId = "RESOURCEID";
/*  884 */               attidCol = "-1";
/*  885 */               valueCol = "responsetime";
/*      */             }
/*      */             
/*  888 */             if ((!confStartTime.trim().equals("")) && (!confEndTime.trim().equals("")))
/*      */             {
/*  890 */               HashMap prefixVsTableListMap = null;
/*  891 */               if (valueCol.equals("responsetime")) {
/*  892 */                 prefixVsTableListMap = ConfMonitorUtil.getInstance().getTables((String)request.getAttribute("servertype"), "", Long.parseLong(confStartTime), Long.parseLong(confEndTime));
/*      */               }
/*      */               else {
/*  895 */                 prefixVsTableListMap = ConfMonitorUtil.getInstance().getTablesForAttribute(attID + "", Long.parseLong(confStartTime), Long.parseLong(confEndTime));
/*      */               }
/*      */               
/*  898 */               Iterator prefixes = prefixVsTableListMap.keySet().iterator();
/*  899 */               while (prefixes.hasNext()) {
/*  900 */                 String tablePrefix = (String)prefixes.next();
/*  901 */                 ArrayList attListInPrefixTables = ConfMonitorUtil.getInstance().getAttlistForPrefix(tablePrefix);
/*      */                 try {
/*  903 */                   if (attListInPrefixTables.contains(valueCol)) {
/*  904 */                     dbtables = (ArrayList)prefixVsTableListMap.get(tablePrefix);
/*  905 */                     break;
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ex) {
/*  909 */                   ex.printStackTrace();
/*  910 */                   AMLog.debug("[ConfMonitors] Might be a prroblem with cache :: Prefix cache :" + attListInPrefixTables);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  917 */             String charToAppend = ConfMonitorUtil.getSpecialCharToAppend();
/*  918 */             valueCol = charToAppend + valueCol + charToAppend + expression;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  924 */         if (!attidCol.equals("-1"))
/*      */         {
/*  926 */           Condition = " and attributeid=" + attID;
/*      */         }
/*  928 */         System.out.println("businessRUle" + businessRule);
/*  929 */         if (businessRule != null)
/*      */         {
/*  931 */           if (!businessRule.equals("oni"))
/*      */           {
/*  933 */             startTime = endTime - 18000000L;
/*  934 */             if ((isConfMonitor) && (!confStartTime.trim().equals("")) && (!confEndTime.trim().equals(""))) {
/*  935 */               startTime = Long.parseLong(request.getParameter("confStartTime"));
/*  936 */               endTime = Long.parseLong(request.getParameter("confEndTime"));
/*      */             }
/*  938 */             Properties props = ReportUtilities.getWorkingTotalHours(startTime, endTime, businessRule);
/*  939 */             stTime = props.getProperty("downtime");
/*  940 */             enTime = props.getProperty("uptime");
/*  941 */             String action = props.getProperty("toremove");
/*  942 */             if (action == "toremove")
/*      */             {
/*  944 */               request.setAttribute("STATUS", status);
/*      */             }
/*      */             else
/*      */             {
/*  948 */               String valueQuery = "";
/*  949 */               if ((isConfMonitor) && (dbtables.size() > 0)) {
/*  950 */                 valueQuery = getValueQuery(dbtables, colTime, valueCol, resourceId, resID, Condition, request.getParameter("confStartTime"), request.getParameter("confEndTime"));
/*      */               }
/*      */               else {
/*  953 */                 valueQuery = "select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "=" + resID + Condition + " and " + colTime + ">=" + stTime + " and " + colTime + "<=" + enTime + " order by " + colTime + " desc";
/*      */               }
/*  955 */               if (resourceId.equals("RESOURCENAME"))
/*      */               {
/*  957 */                 String NameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resID;
/*  958 */                 set = AMConnectionPool.executeQueryStmt(NameQuery);
/*  959 */                 if (set.next())
/*      */                 {
/*  961 */                   ResName = set.getString("RESOURCENAME");
/*  962 */                   valueQuery = "select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "='" + ResName + "' and " + colTime + ">=" + stTime + " and " + colTime + "<=" + enTime + Condition + " order by " + colTime + " desc";
/*      */                 }
/*      */               }
/*      */               
/*  966 */               System.out.println("the value query====>" + valueQuery);
/*  967 */               set = AMConnectionPool.executeQueryStmt(valueQuery);
/*  968 */               Calendar cal = Calendar.getInstance();
/*  969 */               while (set.next())
/*      */               {
/*  971 */                 Properties prop = new Properties();
/*  972 */                 boolean isbHrdata = true;
/*  973 */                 long collectionTime = set.getLong(1);
/*      */                 try
/*      */                 {
/*  976 */                   if ((isConfMonitor) && (bHourDetail.size() != 0))
/*      */                   {
/*  978 */                     cal.setTimeInMillis(collectionTime);
/*  979 */                     int weekday = cal.get(7) - 1;
/*  980 */                     int hour = cal.get(11);
/*  981 */                     int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/*  982 */                     int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/*  983 */                     if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start <= hour) && (BHr_End > hour))
/*      */                     {
/*  985 */                       isbHrdata = true;
/*      */                     }
/*      */                     else {
/*  988 */                       isbHrdata = false;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ex)
/*      */                 {
/*  995 */                   ex.printStackTrace();
/*      */                 }
/*  997 */                 if ((!isConfMonitor) || ((forbHr) && (isbHrdata)))
/*      */                 {
/*  999 */                   double rValue = set.getDouble(2);
/* 1000 */                   String rValuestr = rValue >= 0.0D ? twoDecPer.format(rValue) : "-";
/* 1001 */                   prop.put("COLLECTIONTIME", new Long(collectionTime));
/* 1002 */                   prop.put("VALUE", rValuestr);
/* 1003 */                   raw.add(prop);
/*      */                 }
/*      */               }
/* 1006 */               request.setAttribute("rawdata", raw);
/* 1007 */               if (raw.size() == 0)
/*      */               {
/*      */ 
/* 1010 */                 enTime = String.valueOf(System.currentTimeMillis());
/* 1011 */                 long beginTime = Long.parseLong(enTime) - 18000000L;
/* 1012 */                 String error = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(beginTime).toString(), new java.util.Date(Long.parseLong(enTime)).toString() });
/* 1013 */                 System.out.println("the status==>" + error);
/* 1014 */                 request.setAttribute("STATUS", error);
/* 1015 */                 request.setAttribute("start", Long.valueOf(beginTime));
/* 1016 */                 request.setAttribute("end", enTime);
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 1021 */                 request.setAttribute("STATUS", "SUCCESS");
/* 1022 */                 System.out.println("####$$$###====>" + stTime + "&&" + enTime);
/* 1023 */                 request.setAttribute("start", stTime);
/* 1024 */                 request.setAttribute("end", enTime);
/*      */               }
/*      */               
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 1034 */             String valueQuery = "";
/* 1035 */             if ((isConfMonitor) && (dbtables.size() > 0)) {
/* 1036 */               valueQuery = getValueQuery(dbtables, colTime, valueCol, resourceId, resID, Condition, confStartTime, confEndTime);
/*      */ 
/*      */             }
/* 1039 */             else if (tableName.equals("AM_CAM_DC_ATTRIBUTES")) {
/* 1040 */               valueQuery = "select COLLECTIONTIME,VALUE from AM_CAM_COLUMNAR_DATA where ROWID=" + resID + " and ATTRIBUTEID=" + attID + " order by COLLECTIONTIME desc";
/*      */             } else {
/* 1042 */               valueQuery = "select " + colTime + "," + DBQueryUtil.escapeColumn(valueCol, new StringBuilder().append(attID).append("").toString()) + expression + " from " + tableName + " where " + resourceId + "=" + resID + Condition + " order by " + colTime + " desc";
/*      */             }
/*      */             
/* 1045 */             if (resourceId.equals("RESOURCENAME"))
/*      */             {
/* 1047 */               String NameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resID;
/* 1048 */               set = AMConnectionPool.executeQueryStmt(NameQuery);
/* 1049 */               if (set.next())
/*      */               {
/* 1051 */                 ResName = set.getString("RESOURCENAME");
/* 1052 */                 valueQuery = "select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "='" + ResName + "'" + Condition + " order by " + colTime + " desc";
/*      */               }
/*      */             }
/*      */             
/*      */             try
/*      */             {
/* 1058 */               System.out.println("the value query====>" + valueQuery);
/* 1059 */               set = AMConnectionPool.executeQueryStmt(valueQuery);
/* 1060 */               while (set.next())
/*      */               {
/* 1062 */                 Properties prop = new Properties();
/* 1063 */                 long collectionTime = set.getLong(1);
/*      */                 
/*      */                 double rValueD;
/*      */                 double rValueD;
/* 1067 */                 if (tableName.equals("AM_CAM_DC_ATTRIBUTES")) {
/* 1068 */                   rValueD = Double.parseDouble(set.getString(2));
/*      */                 }
/*      */                 else
/*      */                 {
/* 1072 */                   rValueD = set.getDouble(2);
/*      */                 }
/* 1074 */                 if (!set.wasNull()) {
/* 1075 */                   String rValuestr = rValueD >= 0.0D ? twoDecPer.format(rValueD) : "-";
/*      */                   
/* 1077 */                   prop.put("COLLECTIONTIME", new Long(collectionTime));
/*      */                   
/* 1079 */                   prop.put("VALUE", rValuestr);
/* 1080 */                   raw.add(prop);
/*      */                 }
/*      */               }
/* 1083 */               request.setAttribute("rawdata", raw);
/* 1084 */               if ((raw == null) || ((raw != null) && (raw.size() == 0)))
/*      */               {
/* 1086 */                 enTime = String.valueOf(System.currentTimeMillis());
/* 1087 */                 long beginTime = Long.parseLong(enTime) - 18000000L;
/* 1088 */                 String error = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(beginTime).toString(), new java.util.Date(Long.parseLong(enTime)).toString() });
/* 1089 */                 System.out.println("the status error==>" + error);
/* 1090 */                 request.setAttribute("STATUS", error);
/*      */                 
/* 1092 */                 request.setAttribute("start", Long.valueOf(beginTime));
/* 1093 */                 request.setAttribute("end", enTime);
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 1098 */                 enTime = String.valueOf(System.currentTimeMillis());
/* 1099 */                 request.setAttribute("STATUS", "SUCCESS");
/*      */                 
/* 1101 */                 stTime = ((Properties)raw.get(raw.size() - 1)).get("COLLECTIONTIME").toString();
/* 1102 */                 enTime = ((Properties)raw.get(0)).get("COLLECTIONTIME").toString();
/*      */                 
/* 1104 */                 System.out.println("####$$$###====>" + stTime + "&&" + enTime);
/* 1105 */                 request.setAttribute("start", stTime);
/* 1106 */                 request.setAttribute("end", enTime);
/*      */               }
/* 1108 */               System.out.println("############## raw data oni==>" + raw);
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 1112 */               ex.printStackTrace();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1120 */         long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/*      */         
/*      */ 
/*      */ 
/* 1124 */         String query = DBQueryUtil.getDBQuery("am.historyDataAction.getData.query", new String[] { expression1, archivedTableName, "" + resID, "" + attID });
/* 1125 */         String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime + " order by ARCHIVEDTIME desc";
/*      */         
/* 1127 */         if (timeStamp[2] > 0L)
/*      */         {
/* 1129 */           dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " OR DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ") order by ARCHIVEDTIME desc";
/*      */         }
/* 1131 */         query = query + dailyRptCondition;
/*      */         
/*      */ 
/* 1134 */         request.setAttribute("STIME", timeStamp[0] + "");
/* 1135 */         request.setAttribute("ETIME", timeStamp[1] + "");
/* 1136 */         request.setAttribute("dailyStime", timeStamp[2] + "");
/* 1137 */         request.setAttribute("dailyEtime", timeStamp[3] + "");
/*      */         try {
/* 1139 */           set = AMConnectionPool.executeQueryStmt(query);
/* 1140 */           double minAvgValue = -1.0D;
/* 1141 */           double maxAvgValue = -1.0D;
/*      */           
/* 1143 */           Calendar cal = Calendar.getInstance();
/*      */           
/* 1145 */           while (set.next())
/*      */           {
/* 1147 */             boolean isbHrdata = false;
/*      */             try
/*      */             {
/* 1150 */               if (bHourDetail.size() != 0)
/*      */               {
/* 1152 */                 cal.setTimeInMillis(set.getLong("ARCHIVEDTIME"));
/*      */                 
/* 1154 */                 int weekday = cal.get(7) - 1;
/* 1155 */                 hour = cal.get(11);
/* 1156 */                 int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/* 1157 */                 int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/* 1158 */                 if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*      */                 {
/* 1160 */                   isbHrdata = true;
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 1166 */               System.out.println("############# Error in Bhour ############" + ex.toString());
/*      */             }
/*      */             
/* 1169 */             if ((!forbHr) || ((forbHr) && (isbHrdata)))
/*      */             {
/* 1171 */               long archivedTime = set.getLong("ARCHIVEDTIME");
/* 1172 */               long minValue = set.getLong("MINVALUE");
/* 1173 */               long maxValue = set.getLong("MAXVALUE");
/* 1174 */               double avgValue = set.getDouble("AVG");
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
/* 1189 */               if (avgValue > maxAvgValue) {
/* 1190 */                 maxAvgValue = avgValue;
/*      */               }
/* 1192 */               if ((minAvgValue == -1.0D) || (avgValue < minAvgValue)) {
/* 1193 */                 minAvgValue = avgValue;
/*      */               }
/* 1195 */               Properties prop = new Properties();
/* 1196 */               prop.put("ARCHIVEDTIME", new Long(archivedTime));
/* 1197 */               prop.put("MINVALUE", new Long(minValue));
/* 1198 */               prop.put("MAXVALUE", new Long(maxValue));
/* 1199 */               prop.put("AVGVALUE", new Double(avgValue));
/* 1200 */               list.add(prop);
/*      */               
/* 1202 */               avgList.add(new Float(avgValue));
/*      */             }
/*      */           }
/*      */           
/* 1206 */           request.setAttribute("data", list);
/* 1207 */           request.setAttribute("minAvgValue", new Float(minAvgValue));
/* 1208 */           request.setAttribute("maxAvgValue", new Float(maxAvgValue));
/* 1209 */           request.setAttribute("NFPERCENTILE", getnfPercentile(avgList));
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 1213 */           System.out.println("HistoryDataAction : Error Occured ====>" + ee.getMessage());
/*      */         }
/*      */         
/* 1216 */         if (list.size() == 0)
/*      */         {
/* 1218 */           request.setAttribute("STATUS", status);
/*      */         }
/*      */         else
/*      */         {
/* 1222 */           status = "SUCCESS";
/* 1223 */           Properties prp = (Properties)list.get(0);
/* 1224 */           Long l = (Long)prp.get("ARCHIVEDTIME");
/* 1225 */           request.setAttribute("endtime", new java.util.Date(endTime));
/*      */           
/* 1227 */           prp = (Properties)list.get(list.size() - 1);
/* 1228 */           l = (Long)prp.get("ARCHIVEDTIME");
/* 1229 */           request.setAttribute("starttime", new java.util.Date(l.longValue()));
/*      */           
/* 1231 */           request.setAttribute("STATUS", status);
/*      */         }
/*      */         
/*      */ 
/* 1235 */         if ((min == -1L) && (status.equals("SUCCESS")))
/*      */         {
/* 1237 */           String bhr_condition = "";
/* 1238 */           if (forbHr)
/*      */           {
/* 1240 */             ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(startTime, endTime, resID, attID, archivedTableName, bHourDetail);
/* 1241 */             for (int i = 0; i < blist.size(); i++)
/*      */             {
/* 1243 */               Hashtable time_stamps = (Hashtable)blist.get(i);
/* 1244 */               if ((bhr_condition != null) && (bhr_condition.length() > 1))
/*      */               {
/* 1246 */                 bhr_condition = bhr_condition + " or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*      */               }
/*      */               else
/*      */               {
/* 1250 */                 bhr_condition = "(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*      */               }
/*      */             }
/*      */             
/* 1254 */             query = DBQueryUtil.getDBQuery("am.historyDataAction.getData.query2", new String[] { expression1, archivedTableName, "" + resID, "" + attID, bhr_condition });
/* 1255 */             System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MIN-MAX-AVG CONDITION $$$$$ " + query);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 1261 */             query = DBQueryUtil.getDBQuery("am.historyDataAction.getData.query3", new String[] { expression1, archivedTableName, "" + resID, "" + attID, "" + endTime, "" + startTime });
/* 1262 */             System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MIN-MAX-AVG QUERY $$$$$ " + query);
/*      */           }
/* 1264 */           if (timeStamp[2] > 0L)
/*      */           {
/* 1266 */             durationCondition = " and (DURATION=1 or DURATION=2)";
/*      */           }
/* 1268 */           query = query + durationCondition;
/* 1269 */           AMLog.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MIN-MAX-AVG QUERY $$$$$$$$$$ " + query);
/* 1270 */           set1 = AMConnectionPool.executeQueryStmt(query);
/* 1271 */           if (set1.next())
/*      */           {
/* 1273 */             min = set1.getLong("MIN");
/* 1274 */             max = set1.getLong("MAX");
/* 1275 */             avg = set1.getFloat("AVG");
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
/* 1288 */             request.setAttribute("minvalue", String.valueOf(min));
/* 1289 */             request.setAttribute("maxvalue", String.valueOf(max));
/* 1290 */             request.setAttribute("avgvalue", String.valueOf(avg));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1297 */       ArrayList thresholdDetails = FaultUtil.getThresholdDetailsForHistoryData(resID, attID, true);
/* 1298 */       request.setAttribute("thresholdDetails", thresholdDetails);
/*      */       
/* 1300 */       ComparingSla cs = new ComparingSla();
/* 1301 */       String valueforperiod = cs.getValueForPeriod(period);
/* 1302 */       ReportUtilities rep = new ReportUtilities();
/* 1303 */       String valueforpdf = rep.getValueForPeriodForPDF(period);
/*      */       
/* 1305 */       request.setAttribute("note", "1");
/* 1306 */       request.setAttribute("period", period);
/*      */       
/* 1308 */       String yaxis = (String)request.getAttribute("monitortype") + " " + (String)request.getAttribute("unit");
/* 1309 */       HistoryDataGraphUtil graph = new HistoryDataGraphUtil();
/*      */       
/* 1311 */       if ("pdf".equals(rf.getReporttype()))
/*      */       {
/* 1313 */         ChartInfo cinfo = new ChartInfo();
/* 1314 */         SummaryBean sumgraph = new SummaryBean();
/*      */         
/* 1316 */         System.out.println("the period==>" + period + "& bRule===>" + businessRule);
/* 1317 */         if ((period != null) && (period.equals("4")))
/*      */         {
/* 1319 */           if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */           {
/* 1321 */             graph.setParam(String.valueOf(resID), String.valueOf(attID), period, Long.toString(customstartTime), Long.toString(customendTime), businessRule);
/*      */           }
/*      */           else
/*      */           {
/* 1325 */             graph.setParam(String.valueOf(resID), String.valueOf(attID), period, Long.toString(customstartTime), Long.toString(customendTime));
/*      */           }
/*      */         }
/* 1328 */         else if ((period != null) && (period.equals("14")))
/*      */         {
/* 1330 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period);
/*      */ 
/*      */ 
/*      */         }
/* 1334 */         else if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */         {
/* 1336 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period, businessRule);
/*      */ 
/*      */ 
/*      */         }
/* 1340 */         else if ((period != null) && (period.equals("20")))
/*      */         {
/* 1342 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period, businessRule);
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1351 */         String image = null;
/* 1352 */         cinfo.setDataSet(graph);
/* 1353 */         cinfo.setHeight("220");
/* 1354 */         cinfo.setWidth("600");
/* 1355 */         cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/* 1356 */         cinfo.setYaxisLabel(yaxis);
/* 1357 */         cinfo.setShape(true);
/* 1358 */         cinfo.setCustomDateAxis(true);
/* 1359 */         cinfo.setCustomAngle(270.0D);
/*      */         
/* 1361 */         if (period.equals("20"))
/*      */         {
/* 1363 */           request.setAttribute("report-type-template", "report.showpolldata.attribute");
/*      */         }
/*      */         else
/*      */         {
/* 1367 */           cinfo.setMovingAverage(FormatUtil.getString("am.webclient.730attribute.legendmovingaverage.text"));
/* 1368 */           cinfo.setMovingAverageName(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"));
/* 1369 */           request.setAttribute("report-type-template", "report.attribute");
/*      */         }
/*      */         
/*      */ 
/* 1373 */         image = cinfo.getTimeChartAsJPG();
/*      */         
/* 1375 */         request.setAttribute("attributeImage", image);
/* 1376 */         request.setAttribute("period", period);
/* 1377 */         request.setAttribute("periodvalue", valueforpdf);
/* 1378 */         request.setAttribute("sp-report-type", "pdf");
/*      */         
/*      */ 
/* 1381 */         return mapping.findForward("historydata.success.pdf");
/*      */       }
/*      */       
/* 1384 */       if ("csv".equals(rf.getReporttype())) {
/*      */         try {
/* 1386 */           request.setAttribute("isHistoryReport", Boolean.valueOf(true));
/* 1387 */           String stime = FormatUtil.formatDT(startTime + "");
/* 1388 */           String etime = FormatUtil.formatDT(endTime + "");
/* 1389 */           ArrayList headingList = new ArrayList();
/* 1390 */           String tableheading = "";
/* 1391 */           if (period.equals("20")) {
/* 1392 */             ArrayList rawvalues = (ArrayList)request.getAttribute("rawdata");
/* 1393 */             Properties STime = (Properties)rawvalues.get(rawvalues.size() - 1);
/* 1394 */             stime = FormatUtil.formatDT(((Long)STime.get("COLLECTIONTIME")).longValue() + "");
/* 1395 */             Properties ETime = (Properties)rawvalues.get(0);
/* 1396 */             etime = FormatUtil.formatDT(((Long)ETime.get("COLLECTIONTIME")).longValue() + "");
/* 1397 */             tableheading = FormatUtil.getString("am.webclient.historydatareport.polledDataheading.text", new String[] { FormatUtil.getString((String)request.getAttribute("monitortype")), stime, etime });
/*      */             
/* 1399 */             headingList.add("COLLECTIONTIME");
/* 1400 */             headingList.add("VALUE");
/*      */           }
/*      */           else {
/* 1403 */             tableheading = FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString((String)request.getAttribute("monitortype")), valueforperiod });
/* 1404 */             if (period.equals("4")) {
/* 1405 */               tableheading = FormatUtil.getString("am.webclient.historydatareport.customtimeheading.text", new String[] { FormatUtil.getString((String)request.getAttribute("monitortype")), stime, etime });
/*      */             }
/* 1407 */             headingList.add("ARCHIVEDTIME");
/* 1408 */             headingList.add("MINVALUE");
/* 1409 */             headingList.add("MAXVALUE");
/* 1410 */             headingList.add("AVGVALUE");
/*      */           }
/*      */           
/* 1413 */           request.setAttribute("tableheading", tableheading);
/* 1414 */           request.setAttribute("headingList", headingList);
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/* 1420 */           e.printStackTrace();
/*      */         }
/* 1422 */         return mapping.findForward("historydata.success.csv");
/*      */       }
/*      */       
/*      */ 
/* 1426 */       if (frompop != null)
/*      */       {
/* 1428 */         request.setAttribute("frompop", Boolean.valueOf(true));
/*      */       }
/* 1430 */       request.setAttribute("tabsel", Integer.valueOf(1));
/* 1431 */       return mapping.findForward("historydata.success");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*      */       String typequery;
/* 1436 */       exp.printStackTrace();
/* 1437 */       request.setAttribute("note", "1");
/* 1438 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("historydata.exception", exp.toString()));
/* 1439 */       saveErrors(request, errors);
/* 1440 */       return mapping.getInputForward();
/*      */     }
/*      */     finally {
/* 1443 */       closeResultSet(set);
/* 1444 */       closeResultSet(set1);
/* 1445 */       closeResultSet(resNameSet);
/* 1446 */       closeResultSet(titleSet);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList checkArchivedtime(ArrayList a1, long archivedTime)
/*      */   {
/* 1454 */     if ((a1 != null) && (a1.size() > 0))
/*      */     {
/* 1456 */       for (int i = 0; i < a1.size(); i++)
/*      */       {
/* 1458 */         ArrayList a2 = (ArrayList)a1.get(i);
/* 1459 */         long l1 = ((Long)a2.get(0)).longValue();
/* 1460 */         if (l1 == archivedTime)
/*      */         {
/* 1462 */           return a2;
/*      */         }
/*      */       }
/*      */     }
/* 1466 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getPercentileAvg(ArrayList avgList)
/*      */   {
/* 1473 */     String percentileAvg = "";
/*      */     
/* 1475 */     int length = avgList.size();
/* 1476 */     if (length > 0) {
/* 1477 */       Integer[] array = (Integer[])avgList.toArray(new Integer[length]);
/* 1478 */       Arrays.sort(array);
/*      */       
/* 1480 */       int _95percentileLength = length * 95 / 100;
/*      */       
/* 1482 */       int _95percentileAvg = array[0].intValue();
/* 1483 */       if (_95percentileLength > 0) {
/* 1484 */         int sum = 0;
/* 1485 */         for (int i = 0; i < _95percentileLength; i++) {
/* 1486 */           sum += array[i].intValue();
/*      */         }
/* 1488 */         _95percentileAvg = sum / _95percentileLength;
/*      */       }
/* 1490 */       percentileAvg = String.valueOf(_95percentileAvg);
/*      */     }
/* 1492 */     return percentileAvg;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Properties getTimePeriod(long startTime, long endTime, String period)
/*      */   {
/* 1499 */     Calendar cal = new GregorianCalendar();
/* 1500 */     Calendar cal1 = new GregorianCalendar();
/*      */     
/* 1502 */     int dayofweek = cal1.get(7);
/* 1503 */     Properties prop = new Properties();
/* 1504 */     if (period.equals("0"))
/*      */     {
/* 1506 */       java.util.Date currentTimeAsDate = new java.util.Date(endTime);
/* 1507 */       Calendar cldr = Calendar.getInstance();
/* 1508 */       cldr.setTime(currentTimeAsDate);
/* 1509 */       cldr.set(11, 0);
/* 1510 */       cldr.set(13, 0);
/* 1511 */       cldr.set(14, 0);
/* 1512 */       cldr.set(12, 0);
/* 1513 */       startTime = cldr.getTime().getTime();
/*      */     }
/* 1515 */     else if ((period.equals("-7")) || (period.equals("1")))
/*      */     {
/* 1517 */       startTime = endTime - 604800000L;
/*      */     }
/* 1519 */     else if ((period.equals("-30")) || (period.equals("2")))
/*      */     {
/* 1521 */       startTime = endTime - 2592000000L;
/*      */     }
/* 1523 */     else if ((period.equals("-5")) || (period.equals("5")))
/*      */     {
/* 1525 */       startTime = endTime - 31536000000L;
/*      */ 
/*      */     }
/* 1528 */     else if (period.equals("6")) {
/* 1529 */       cal.set(7, 2);
/* 1530 */       cal.set(11, 0);
/* 1531 */       cal.set(12, 0);
/* 1532 */       cal.set(13, 0);
/* 1533 */       cal.set(14, 0);
/*      */       
/* 1535 */       long lday = cal.getTimeInMillis();
/* 1536 */       startTime = lday;
/*      */       
/*      */ 
/* 1539 */       if (dayofweek == 7)
/*      */       {
/* 1541 */         cal.set(7, 1);
/* 1542 */         cal.set(11, 0);
/* 1543 */         cal.set(12, 0);
/* 1544 */         cal.set(13, 0);
/* 1545 */         cal.set(14, 0);
/*      */         
/* 1547 */         long lweek1 = cal.getTimeInMillis();
/* 1548 */         startTime = lweek1;
/* 1549 */         startTime += 86400000L;
/* 1550 */         endTime = startTime + 432000000L;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1555 */       if (dayofweek == 1) {
/* 1556 */         cal.set(7, 1);
/* 1557 */         cal.set(11, 0);
/* 1558 */         cal.set(12, 0);
/* 1559 */         cal.set(13, 0);
/* 1560 */         cal.set(14, 0);
/*      */         
/* 1562 */         long lweek1 = cal.getTimeInMillis();
/* 1563 */         startTime = lweek1 - 604800000L;
/* 1564 */         startTime += 86400000L;
/* 1565 */         endTime = startTime + 432000000L;
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 1571 */     else if (period.equals("7")) {
/* 1572 */       cal.set(5, 1);
/* 1573 */       cal.set(11, 0);
/* 1574 */       cal.set(12, 0);
/* 1575 */       cal.set(13, 0);
/* 1576 */       cal.set(14, 0);
/*      */       
/* 1578 */       long lmonth = cal.getTimeInMillis();
/* 1579 */       startTime = lmonth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1585 */     else if (period.equals("11")) {
/* 1586 */       int i = cal.get(2);
/*      */       
/* 1588 */       cal.set(2, i - 1);
/*      */       
/* 1590 */       cal.set(5, 1);
/*      */       
/* 1592 */       cal.set(11, 0);
/* 1593 */       cal.set(12, 0);
/* 1594 */       cal.set(13, 0);
/* 1595 */       cal.set(14, 0);
/*      */       
/* 1597 */       long lastmonth = cal.getTimeInMillis();
/* 1598 */       startTime = lastmonth;
/* 1599 */       int maxDaysInMonth = cal.getActualMaximum(5);
/* 1600 */       endTime = startTime + maxDaysInMonth * 24 * 60 * 60 * 1000L;
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
/*      */     }
/* 1616 */     else if (period.equals("12"))
/*      */     {
/* 1618 */       cal.set(7, 1);
/* 1619 */       cal.set(11, 0);
/* 1620 */       cal.set(12, 0);
/* 1621 */       cal.set(13, 0);
/* 1622 */       cal.set(14, 0);
/*      */       
/* 1624 */       long lweek = cal.getTimeInMillis();
/* 1625 */       startTime = lweek - 604800000L;
/* 1626 */       endTime = startTime + 604800000L;
/*      */ 
/*      */ 
/*      */     }
/* 1630 */     else if (period.equals("8")) {
/* 1631 */       cal.set(6, 1);
/* 1632 */       cal.set(11, 0);
/* 1633 */       cal.set(12, 0);
/* 1634 */       cal.set(13, 0);
/* 1635 */       cal.set(14, 0);
/*      */       
/* 1637 */       long lyear = cal.getTimeInMillis();
/* 1638 */       startTime = lyear;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1644 */     else if (period.equals("9")) {
/* 1645 */       int i = cal.get(2);
/* 1646 */       if (i < 3) {
/* 1647 */         cal.set(2, 0);
/*      */       }
/* 1649 */       else if (i < 6) {
/* 1650 */         cal.set(2, 3);
/*      */       }
/* 1652 */       else if (i < 9) {
/* 1653 */         cal.set(2, 6);
/*      */       }
/*      */       else {
/* 1656 */         cal.set(2, 9);
/*      */       }
/* 1658 */       cal.set(5, 1);
/* 1659 */       cal.set(11, 0);
/* 1660 */       cal.set(12, 0);
/* 1661 */       cal.set(13, 0);
/* 1662 */       cal.set(14, 0);
/*      */       
/* 1664 */       long lqatar = cal.getTimeInMillis();
/* 1665 */       startTime = lqatar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1674 */     else if (period.equals("3"))
/*      */     {
/* 1676 */       java.util.Date currentTimeAsDate = new java.util.Date(endTime);
/* 1677 */       Calendar cldr = Calendar.getInstance();
/* 1678 */       cldr.setTime(currentTimeAsDate);
/* 1679 */       cldr.add(7, -1);
/* 1680 */       cldr.set(11, 0);
/* 1681 */       cldr.set(13, 0);
/* 1682 */       cldr.set(14, 0);
/* 1683 */       cldr.set(12, 0);
/* 1684 */       startTime = cldr.getTime().getTime();
/* 1685 */       cldr.setTime(currentTimeAsDate);
/* 1686 */       cldr.set(11, 0);
/* 1687 */       cldr.set(13, 0);
/* 1688 */       cldr.set(14, 0);
/* 1689 */       cldr.set(12, 0);
/* 1690 */       endTime = cldr.getTime().getTime();
/*      */     }
/*      */     
/* 1693 */     prop.put("STARTTIME", new Long(startTime));
/* 1694 */     prop.put("ENDTIME", new Long(endTime));
/* 1695 */     return prop;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Properties getConfigDetails(int resourceId, int attributeId)
/*      */   {
/* 1702 */     Properties prop = new Properties();
/* 1703 */     ResultSet set = null;
/*      */     try {
/* 1705 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1706 */       String query = "select TABLENAME,ATTRIBUTECOLUMNNAME,RESOURCECOLUMNNAME from AM_ArchiverConfig where ATTRIBUTEID=" + attributeId;
/* 1707 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1708 */       if (set.next()) {
/* 1709 */         prop.setProperty("TABLENAME", set.getString("TABLENAME"));
/* 1710 */         prop.setProperty("ATTRIBUTECOLUMNNAME", set.getString("ATTRIBUTECOLUMNNAME"));
/* 1711 */         prop.setProperty("RESOURCECOLUMNNAME", set.getString("RESOURCECOLUMNNAME"));
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1715 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 1718 */       closeResultSet(set);
/*      */     }
/* 1720 */     return prop;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ArrayList getDataList(Properties configProp, ArrayList list)
/*      */   {
/* 1727 */     ResultSet set = null;
/*      */     try {
/* 1729 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1730 */       String tableName = configProp.getProperty("TABLENAME");
/* 1731 */       String attColName = configProp.getProperty("ATTRIBUTECOLUMNNAME");
/* 1732 */       String resColName = configProp.getProperty("RESOURCECOLUMNNAME");
/* 1733 */       String query = "select min(COLLECTIONTIME) as STARTTIME, max(COLLECTIONTIME) as ARCHIVEDTIME, min(" + attColName + ") as MINVALUE,max(" + attColName + ") as MAXVALUE,avg(" + attColName + ") as AVGVALUE from " + tableName + " where " + resColName + "=" + configProp.getProperty("RESOURCEID");
/*      */       
/* 1735 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1736 */       if (set.next()) {
/* 1737 */         long archivedTime = set.getLong("ARCHIVEDTIME");
/* 1738 */         if (archivedTime != 0L) {
/* 1739 */           long startTime = set.getLong("STARTTIME");
/* 1740 */           int minValue = set.getInt("MINVALUE");
/* 1741 */           int maxValue = set.getInt("MAXVALUE");
/* 1742 */           int avgValue = set.getInt("AVGVALUE");
/* 1743 */           Properties prop = new Properties();
/* 1744 */           prop.put("ARCHIVEDTIME", new Long(archivedTime));
/* 1745 */           prop.put("MINVALUE", new Integer(minValue));
/* 1746 */           prop.put("MAXVALUE", new Integer(maxValue));
/* 1747 */           prop.put("AVGVALUE", new Integer(avgValue));
/* 1748 */           prop.put("STARTTIME", new Long(startTime));
/* 1749 */           list.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1754 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 1757 */       closeResultSet(set);
/*      */     }
/* 1759 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private ArrayList getHostDataList(Properties configProp, ArrayList list)
/*      */   {
/* 1766 */     ResultSet set = null;
/*      */     try {
/* 1768 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1769 */       String tableName = "HostDataCollected";
/* 1770 */       String attColName = "CURVALUE";
/* 1771 */       int attributeId = Integer.parseInt(configProp.getProperty("ATTRIBUTEID"));
/*      */       
/* 1773 */       String entity = null;
/* 1774 */       if ((attributeId == 1107) || (attributeId == 1207) || (attributeId == 708)) {
/* 1775 */         entity = "CPUUtilization";
/*      */       }
/* 1777 */       else if ((attributeId == 1202) || (attributeId == 803) || (attributeId == 702)) {
/* 1778 */         entity = "PhysicalMemUtilization";
/*      */       }
/*      */       else {
/* 1781 */         entity = "SwapMemUtilization";
/*      */       }
/*      */       
/* 1784 */       String query = "select min(COLLECTIONTIME) as STARTTIME, max(COLLECTIONTIME) as ARCHIVEDTIME, min(" + attColName + ") as MINVALUE,max(" + attColName + ") as MAXVALUE,avg(" + attColName + ") as AVGVALUE from " + tableName + ",AM_ManagedObject where AM_ManagedObject.RESOURCENAME=HostDataCollected.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + configProp.getProperty("RESOURCEID") + " and ENTITY='" + entity + "'";
/*      */       
/* 1786 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1787 */       if (set.next()) {
/* 1788 */         long archivedTime = set.getLong("ARCHIVEDTIME");
/* 1789 */         if (archivedTime != 0L) {
/* 1790 */           long startTime = set.getLong("STARTTIME");
/* 1791 */           int minValue = set.getInt("MINVALUE");
/* 1792 */           int maxValue = set.getInt("MAXVALUE");
/* 1793 */           int avgValue = set.getInt("AVGVALUE");
/* 1794 */           Properties prop = new Properties();
/* 1795 */           prop.put("ARCHIVEDTIME", new Long(archivedTime));
/* 1796 */           prop.put("MINVALUE", new Integer(minValue));
/* 1797 */           prop.put("MAXVALUE", new Integer(maxValue));
/* 1798 */           prop.put("AVGVALUE", new Integer(avgValue));
/* 1799 */           prop.put("STARTTIME", new Long(startTime));
/* 1800 */           list.add(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1805 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 1808 */       closeResultSet(set);
/*      */     }
/* 1810 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final void closeResultSet(ResultSet set)
/*      */   {
/* 1817 */     if (set != null) {
/*      */       try {
/* 1819 */         AMConnectionPool.closeStatement(set);
/*      */       } catch (Exception ex) {
/* 1821 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getAvailabilityData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1828 */     ReportForm reportform = (ReportForm)form;
/* 1829 */     ActionMessages messages = new ActionMessages();
/* 1830 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1831 */     ResultSet set = null;
/* 1832 */     String startdate = reportform.getStartDate();
/* 1833 */     String enddate = reportform.getEndDate();
/* 1834 */     String period = request.getParameter("period");
/* 1835 */     String resourceid = request.getParameter("resourceid");
/* 1836 */     long[] time = ReportUtilities.getTimeStamp(period);
/* 1837 */     long customstartTime = 0L;
/* 1838 */     long customendTime = 0L;
/* 1839 */     long startTime = 0L;
/* 1840 */     long endTime = 0L;
/* 1841 */     long totalDuration = 0L;
/* 1842 */     long unmanagedtime = 0L;
/* 1843 */     long scheduledtime = 0L;
/* 1844 */     long totdowntime = 0L;
/* 1845 */     ArrayList downtimesummary = new ArrayList();
/* 1846 */     ArrayList downtimehistory = new ArrayList();
/* 1847 */     Properties summary = null;
/* 1848 */     long tempST = 0L;
/* 1849 */     long tempET = 0L;
/* 1850 */     ResultSet displayNameSet = null;
/*      */     
/*      */     try
/*      */     {
/* 1854 */       if (period != null) {
/* 1855 */         if (period.equals("-7"))
/*      */         {
/* 1857 */           period = "1";
/*      */         }
/* 1859 */         if (period.equals("-30"))
/*      */         {
/* 1861 */           period = "2";
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1866 */         String displayNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 1867 */         displayNameSet = AMConnectionPool.executeQueryStmt(displayNameQuery);
/* 1868 */         if (displayNameSet.next()) {
/* 1869 */           displayName = EnterpriseUtil.decodeString(displayNameSet.getString("DISPLAYNAME"));
/* 1870 */           request.setAttribute("resourcename", displayName);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1875 */         log.fatal("Exception in getting RESOURCENAME details ", ex);
/*      */       }
/* 1877 */       long mintimeindb = ReportUtilities.getMinTimeInDB(resourceid);
/* 1878 */       long[] temptime = ReportUtilities.getTimeStamp("0");
/* 1879 */       Properties props4; if (period.equals("4")) {
/* 1880 */         request.setAttribute("period", period);
/* 1881 */         customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 1882 */         customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*      */         
/* 1884 */         ActionErrors errors = new ActionErrors();
/*      */         
/* 1886 */         if (customstartTime > customendTime) {
/* 1887 */           period = "0";
/* 1888 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("availability.error.bothmismatch", new java.util.Date(customstartTime), new java.util.Date(customendTime)));
/* 1889 */           saveErrors(request, errors);
/*      */         }
/* 1891 */         else if (customendTime < mintimeindb) {
/* 1892 */           period = "0";
/* 1893 */           java.util.Date errorendtime = new java.util.Date(mintimeindb);
/* 1894 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("availability.error.endmismatch", new java.util.Date(customstartTime), new java.util.Date(customendTime)));
/* 1895 */           saveErrors(request, errors);
/*      */         }
/* 1897 */         else if (customstartTime > System.currentTimeMillis()) {
/* 1898 */           period = "0";
/* 1899 */           java.util.Date errorstarttime = new java.util.Date(System.currentTimeMillis());
/* 1900 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("availability.error.startmismatch", new java.util.Date(customstartTime), errorstarttime));
/* 1901 */           saveErrors(request, errors);
/*      */         }
/*      */         else {
/* 1904 */           if (mintimeindb > customstartTime) {
/* 1905 */             startTime = mintimeindb;
/*      */           }
/* 1907 */           else if (mintimeindb != 0L) {
/* 1908 */             startTime = customstartTime;
/*      */           }
/* 1910 */           long currenttime = System.currentTimeMillis();
/*      */           
/* 1912 */           if (customendTime > currenttime) {
/* 1913 */             endTime = currenttime;
/*      */           }
/*      */           else {
/* 1916 */             endTime = customendTime;
/* 1917 */             totalDuration = endTime - startTime;
/*      */           }
/* 1919 */           String showstartDate = FormatUtil.formatDT(String.valueOf(startTime));
/* 1920 */           String showendDate = FormatUtil.formatDT(String.valueOf(endTime));
/* 1921 */           props4 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 1922 */           props4.setProperty("period", "From " + showstartDate + " to " + showendDate);
/*      */           
/* 1924 */           request.setAttribute("timeperiod", FormatUtil.getString("am.webclient.availablityreport.customtimeheading.fromtotext", new String[] { showstartDate, showendDate }));
/* 1925 */           request.setAttribute("summary", props4);
/* 1926 */           summary = props4;
/* 1927 */           tempST = startTime;
/* 1928 */           tempET = endTime;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1934 */       request.setAttribute("period", period);
/*      */       
/* 1936 */       if (mintimeindb > temptime[0])
/*      */       {
/* 1938 */         startTime = mintimeindb;
/* 1939 */         endTime = temptime[1];
/* 1940 */         totalDuration = endTime - startTime;
/*      */       }
/* 1942 */       else if (mintimeindb != 0L)
/*      */       {
/* 1944 */         startTime = temptime[0];
/* 1945 */         endTime = temptime[1];
/* 1946 */         totalDuration = endTime - startTime;
/*      */       }
/* 1948 */       Properties props = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 1949 */       props.setProperty("period", "Today");
/* 1950 */       downtimehistory.add(props);
/* 1951 */       if (period.equals("0"))
/*      */       {
/* 1953 */         request.setAttribute("timeperiod", FormatUtil.getString("Today's"));
/* 1954 */         request.setAttribute("summary", props);
/* 1955 */         summary = props;
/* 1956 */         tempST = startTime;
/* 1957 */         tempET = endTime;
/*      */       }
/* 1959 */       long todayST = temptime[0];
/*      */       
/* 1961 */       temptime = ReportUtilities.getTimeStamp("3");
/* 1962 */       if (mintimeindb > todayST)
/*      */       {
/* 1964 */         Properties props3 = new Properties();
/* 1965 */         props3.setProperty("period", "Yesterday");
/* 1966 */         props3.setProperty("yesterdaydata", "Not Applicable");
/* 1967 */         downtimehistory.add(props3);
/* 1968 */         if (period.equals("3"))
/*      */         {
/*      */ 
/*      */ 
/* 1972 */           String status1 = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(temptime[0]).toString(), new java.util.Date(temptime[1]).toString() });
/* 1973 */           request.setAttribute("STATUS", status1);
/* 1974 */           return mapping.findForward("availabilitydata.success");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1979 */         if ((mintimeindb > temptime[0]) && (mintimeindb < todayST))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1986 */           startTime = mintimeindb;
/* 1987 */           endTime = temptime[1];
/* 1988 */           totalDuration = endTime - startTime;
/*      */         }
/* 1990 */         else if (mintimeindb != 0L)
/*      */         {
/* 1992 */           startTime = temptime[0];
/* 1993 */           endTime = temptime[1];
/* 1994 */           totalDuration = endTime - startTime;
/*      */         }
/* 1996 */         Properties props3 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 1997 */         props3.setProperty("period", FormatUtil.getString("Yesterday"));
/* 1998 */         props3.setProperty("yesterdaydata", "Applicable");
/* 1999 */         downtimehistory.add(props3);
/*      */         
/*      */ 
/* 2002 */         if (period.equals("3"))
/*      */         {
/* 2004 */           request.setAttribute("timeperiod", FormatUtil.getString("Yesterday's"));
/* 2005 */           request.setAttribute("summary", props3);
/* 2006 */           summary = props3;
/* 2007 */           tempST = startTime;
/* 2008 */           tempET = endTime;
/*      */         }
/*      */       }
/* 2011 */       temptime = ReportUtilities.getTimeStamp("1");
/* 2012 */       if (mintimeindb > temptime[0]) {
/* 2013 */         startTime = mintimeindb;
/* 2014 */         endTime = temptime[1];
/* 2015 */         totalDuration = endTime - startTime;
/*      */       }
/* 2017 */       else if (mintimeindb != 0L) {
/* 2018 */         startTime = temptime[0];
/* 2019 */         endTime = temptime[1];
/* 2020 */         totalDuration = endTime - startTime;
/*      */       }
/* 2022 */       Properties props1 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2023 */       props1.setProperty("period", "Last 7 Days");
/* 2024 */       if (period.equals("1")) {
/* 2025 */         request.setAttribute("timeperiod", "Last 7 Days");
/* 2026 */         request.setAttribute("summary", props1);
/* 2027 */         summary = props1;
/* 2028 */         tempST = startTime;
/* 2029 */         tempET = endTime;
/*      */       }
/* 2031 */       temptime = ReportUtilities.getTimeStamp("2");
/* 2032 */       if (mintimeindb > temptime[0]) {
/* 2033 */         startTime = mintimeindb;
/* 2034 */         endTime = temptime[1];
/* 2035 */         totalDuration = endTime - startTime;
/*      */       }
/* 2037 */       else if (mintimeindb != 0L) {
/* 2038 */         startTime = temptime[0];
/* 2039 */         endTime = temptime[1];
/* 2040 */         totalDuration = endTime - startTime;
/*      */       }
/* 2042 */       Properties props2 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2043 */       props2.setProperty("period", "Last 30 Days");
/* 2044 */       if (period.equals("2")) {
/* 2045 */         request.setAttribute("timeperiod", "Last 30 Days");
/* 2046 */         request.setAttribute("summary", props2);
/* 2047 */         summary = props2;
/* 2048 */         tempST = startTime;
/* 2049 */         tempET = endTime;
/*      */       }
/* 2051 */       temptime = ReportUtilities.getTimeStamp("5");
/* 2052 */       if (mintimeindb > temptime[0]) {
/* 2053 */         startTime = mintimeindb;
/* 2054 */         endTime = temptime[1];
/* 2055 */         totalDuration = endTime - startTime;
/*      */       }
/* 2057 */       else if (mintimeindb != 0L) {
/* 2058 */         startTime = temptime[0];
/* 2059 */         endTime = temptime[1];
/* 2060 */         totalDuration = endTime - startTime;
/*      */       }
/* 2062 */       Properties props5 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2063 */       props5.setProperty("period", "Last One Year");
/* 2064 */       if (period.equals("5")) {
/* 2065 */         request.setAttribute("timeperiod", FormatUtil.getString("Last 1 Year's"));
/* 2066 */         request.setAttribute("summary", props5);
/* 2067 */         summary = props5;
/* 2068 */         tempST = startTime;
/* 2069 */         tempET = endTime;
/*      */       }
/*      */       
/* 2072 */       temptime = ReportUtilities.getTimeStamp("6");
/*      */       
/*      */ 
/*      */ 
/* 2076 */       if (mintimeindb > temptime[0]) {
/* 2077 */         startTime = mintimeindb;
/* 2078 */         endTime = temptime[1];
/* 2079 */         totalDuration = endTime - startTime;
/*      */       }
/* 2081 */       else if (mintimeindb != 0L) {
/* 2082 */         startTime = temptime[0];
/* 2083 */         endTime = temptime[1];
/* 2084 */         totalDuration = endTime - startTime;
/*      */       }
/*      */       
/* 2087 */       Properties props6 = new Properties();
/* 2088 */       if (totalDuration < 0L)
/*      */       {
/*      */ 
/*      */ 
/* 2092 */         props6.setProperty("period", "This Week");
/* 2093 */         props6.setProperty("thisweekdata", "Not Applicable");
/* 2094 */         if (period.equals("6"))
/*      */         {
/*      */ 
/*      */ 
/* 2098 */           String status1 = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(temptime[0]).toString(), new java.util.Date(temptime[1]).toString() });
/* 2099 */           request.setAttribute("STATUS", status1);
/* 2100 */           return mapping.findForward("availabilitydata.success");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2105 */         props6 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2106 */         props6.setProperty("period", "This Week");
/* 2107 */         props6.setProperty("thisweekdata", "Applicable");
/* 2108 */         if (period.equals("6")) {
/* 2109 */           request.setAttribute("timeperiod", FormatUtil.getString("This Week's"));
/* 2110 */           request.setAttribute("summary", props6);
/* 2111 */           summary = props6;
/* 2112 */           tempST = startTime;
/* 2113 */           tempET = endTime;
/*      */         }
/*      */       }
/*      */       
/* 2117 */       long weekST = temptime[0];
/* 2118 */       temptime = ReportUtilities.getTimeStamp("7");
/* 2119 */       if (mintimeindb > temptime[0]) {
/* 2120 */         startTime = mintimeindb;
/* 2121 */         endTime = temptime[1];
/* 2122 */         totalDuration = endTime - startTime;
/*      */       }
/* 2124 */       else if (mintimeindb != 0L) {
/* 2125 */         startTime = temptime[0];
/* 2126 */         endTime = temptime[1];
/* 2127 */         totalDuration = endTime - startTime;
/*      */       }
/* 2129 */       Properties props7 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2130 */       props7.setProperty("period", "This Month");
/* 2131 */       if (period.equals("7")) {
/* 2132 */         request.setAttribute("timeperiod", FormatUtil.getString("This Month's"));
/* 2133 */         request.setAttribute("summary", props7);
/* 2134 */         summary = props7;
/* 2135 */         tempST = startTime;
/* 2136 */         tempET = endTime;
/*      */       }
/* 2138 */       long monthST = temptime[0];
/* 2139 */       temptime = ReportUtilities.getTimeStamp("8");
/* 2140 */       if (mintimeindb > temptime[0]) {
/* 2141 */         startTime = mintimeindb;
/* 2142 */         endTime = temptime[1];
/* 2143 */         totalDuration = endTime - startTime;
/*      */       }
/* 2145 */       else if (mintimeindb != 0L) {
/* 2146 */         startTime = temptime[0];
/* 2147 */         endTime = temptime[1];
/* 2148 */         totalDuration = endTime - startTime;
/*      */       }
/* 2150 */       Properties props8 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2151 */       props8.setProperty("period", "This Year");
/* 2152 */       if (period.equals("8")) {
/* 2153 */         request.setAttribute("timeperiod", FormatUtil.getString("This Year's"));
/* 2154 */         request.setAttribute("summary", props8);
/* 2155 */         summary = props8;
/* 2156 */         tempST = startTime;
/* 2157 */         tempET = endTime;
/*      */       }
/* 2159 */       temptime = ReportUtilities.getTimeStamp("9");
/* 2160 */       if (mintimeindb > temptime[0]) {
/* 2161 */         startTime = mintimeindb;
/* 2162 */         endTime = temptime[1];
/* 2163 */         totalDuration = endTime - startTime;
/*      */       }
/* 2165 */       else if (mintimeindb != 0L) {
/* 2166 */         startTime = temptime[0];
/* 2167 */         endTime = temptime[1];
/* 2168 */         totalDuration = endTime - startTime;
/*      */       }
/* 2170 */       Properties props9 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2171 */       props9.setProperty("period", "This Quarter");
/* 2172 */       if (period.equals("9")) {
/* 2173 */         request.setAttribute("timeperiod", FormatUtil.getString("This Quarter's"));
/* 2174 */         request.setAttribute("summary", props9);
/* 2175 */         summary = props9;
/* 2176 */         tempST = startTime;
/* 2177 */         tempET = endTime;
/*      */       }
/*      */       
/* 2180 */       temptime = ReportUtilities.getTimeStamp("11");
/* 2181 */       Properties props11 = new Properties();
/* 2182 */       if (mintimeindb > monthST)
/*      */       {
/* 2184 */         props11.setProperty("period", "Last Month");
/* 2185 */         props11.setProperty("LastMonthdata", "Not Applicable");
/*      */         
/* 2187 */         if (period.equals("11"))
/*      */         {
/*      */ 
/* 2190 */           String status1 = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(temptime[0]).toString(), new java.util.Date(temptime[1]).toString() });
/* 2191 */           request.setAttribute("STATUS", status1);
/* 2192 */           return mapping.findForward("availabilitydata.success");
/*      */         }
/*      */       }
/*      */       else {
/* 2196 */         if ((mintimeindb > temptime[0]) && (mintimeindb < monthST))
/*      */         {
/*      */ 
/*      */ 
/* 2200 */           startTime = mintimeindb;
/* 2201 */           endTime = temptime[1];
/* 2202 */           totalDuration = endTime - startTime;
/*      */         }
/* 2204 */         else if (mintimeindb != 0L) {
/* 2205 */           startTime = temptime[0];
/* 2206 */           endTime = temptime[1];
/* 2207 */           totalDuration = endTime - startTime;
/*      */         }
/*      */         
/* 2210 */         props11 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2211 */         props11.setProperty("period", "Last Month");
/* 2212 */         props11.setProperty("LastMonthdata", "Applicable");
/*      */         
/* 2214 */         if (period.equals("11")) {
/* 2215 */           request.setAttribute("timeperiod", FormatUtil.getString("Last Month's"));
/* 2216 */           request.setAttribute("summary", props11);
/* 2217 */           summary = props11;
/* 2218 */           tempST = startTime;
/* 2219 */           tempET = endTime;
/*      */         }
/*      */       }
/*      */       
/* 2223 */       temptime = ReportUtilities.getTimeStamp("12");
/* 2224 */       Properties props12 = new Properties();
/* 2225 */       if (mintimeindb > weekST)
/*      */       {
/* 2227 */         props12.setProperty("period", "Last Week");
/* 2228 */         props12.setProperty("LastWeekdata", "Not Applicable");
/*      */         
/* 2230 */         if (period.equals("12"))
/*      */         {
/*      */ 
/* 2233 */           String status1 = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(temptime[0]).toString(), new java.util.Date(temptime[1]).toString() });
/* 2234 */           request.setAttribute("STATUS", status1);
/* 2235 */           return mapping.findForward("availabilitydata.success");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2240 */         if ((mintimeindb > temptime[0]) && (mintimeindb < weekST))
/*      */         {
/* 2242 */           startTime = mintimeindb;
/* 2243 */           endTime = temptime[1];
/* 2244 */           totalDuration = endTime - startTime;
/*      */         }
/* 2246 */         else if (mintimeindb != 0L) {
/* 2247 */           startTime = temptime[0];
/* 2248 */           endTime = temptime[1];
/* 2249 */           totalDuration = endTime - startTime;
/*      */         }
/*      */         
/*      */ 
/* 2253 */         props12 = calculateAvailabilityDetails(resourceid, startTime, endTime);
/* 2254 */         props12.setProperty("period", "Last Week");
/* 2255 */         props12.setProperty("LastWeekdata", "Applicable");
/* 2256 */         if (period.equals("12")) {
/* 2257 */           request.setAttribute("timeperiod", "Last Week's");
/* 2258 */           request.setAttribute("summary", props12);
/* 2259 */           summary = props12;
/* 2260 */           tempST = startTime;
/* 2261 */           tempET = endTime;
/*      */         }
/*      */       }
/* 2264 */       downtimehistory.add(props6);
/* 2265 */       downtimehistory.add(props1);
/* 2266 */       downtimehistory.add(props12);
/* 2267 */       downtimehistory.add(props7);
/* 2268 */       downtimehistory.add(props2);
/* 2269 */       downtimehistory.add(props11);
/* 2270 */       downtimehistory.add(props9);
/* 2271 */       downtimehistory.add(props8);
/* 2272 */       downtimehistory.add(props5);
/*      */       
/*      */ 
/* 2275 */       request.setAttribute("downtimehistory", downtimehistory);
/* 2276 */       reportform.setPeriod(period);
/* 2277 */       startTime = tempST;
/* 2278 */       endTime = tempET;
/* 2279 */       String query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/* 2280 */       if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */       {
/* 2282 */         query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.REASONID,SHORT_DESCRIPTION,TYPE from AM_MO_DowntimeData left outer join AM_DOWNTIMEREASON on AM_MO_DowntimeData.REASONID = AM_DOWNTIMEREASON.REASONID where RESID=" + resourceid + " and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       }
/*      */       
/*      */ 
/* 2286 */       if ((summary != null) && ((summary.getProperty("totaldowntime").indexOf("0 Min(s) 0 Sec(s)") == -1) || (summary.getProperty("showManaged").equals("true")) || (summary.getProperty("showScheduled").equals("true")))) {
/* 2287 */         set = AMConnectionPool.executeQueryStmt(query);
/* 2288 */         while (set.next()) {
/* 2289 */           Properties rows = new Properties();
/*      */           
/* 2291 */           if (("true".equalsIgnoreCase(summary.getProperty("AppCluster"))) && (set.getInt("TYPE") == 1)) {
/* 2292 */             System.out.println("Inside Application cluster");
/* 2293 */             rows.put("downtime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("DownTime"))));
/* 2294 */             if (set.getLong("UpTime") == endTime) {
/* 2295 */               rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/* 2296 */               rows.put("dontdelete", "true");
/*      */             }
/*      */             else {
/* 2299 */               rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/*      */             }
/* 2301 */             rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/*      */             
/*      */ 
/* 2304 */             rows.put("ReasonID", Integer.valueOf(set.getInt("REASONID")));
/* 2305 */             rows.put("typeID", Integer.valueOf(set.getInt("TYPE")));
/* 2306 */             rows.put("interval", ReportUtilities.format(set.getLong("TotalDownTime")));
/* 2307 */             if (set.getInt("TYPE") == 1)
/*      */             {
/* 2309 */               totdowntime += set.getLong("TotalDownTime");
/*      */             }
/*      */             
/* 2312 */             if (set.getString("SHORT_DESCRIPTION") != null) {
/* 2313 */               rows.put("Downtime_Reason", set.getString("SHORT_DESCRIPTION"));
/*      */             }
/*      */             
/* 2316 */             if (totdowntime != 0L) {
/* 2317 */               rows.put("TotDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2322 */           rows.put("downtime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("DownTime"))));
/* 2323 */           if (set.getLong("UpTime") == endTime) {
/* 2324 */             rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/* 2325 */             rows.put("dontdelete", "true");
/*      */           }
/*      */           else {
/* 2328 */             rows.put("uptime", new java.util.Date(ReportUtilities.roundOffToNearestSeconds(set.getLong("UpTime"))));
/*      */           }
/* 2330 */           rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/*      */           
/*      */ 
/* 2333 */           rows.put("ReasonID", Integer.valueOf(set.getInt("REASONID")));
/* 2334 */           rows.put("typeID", Integer.valueOf(set.getInt("TYPE")));
/* 2335 */           rows.put("interval", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(set.getLong("TotalDownTime"))));
/*      */           
/* 2337 */           if (set.getInt("TYPE") == 1)
/*      */           {
/* 2339 */             totdowntime += set.getLong("TotalDownTime");
/*      */           }
/* 2341 */           else if (set.getInt("TYPE") == 2)
/*      */           {
/* 2343 */             unmanagedtime += set.getLong("TotalDownTime");
/*      */           }
/*      */           else
/*      */           {
/* 2347 */             scheduledtime += set.getLong("TotalDownTime");
/*      */           }
/*      */           
/* 2350 */           if (set.getString("SHORT_DESCRIPTION") != null) {
/* 2351 */             rows.put("Downtime_Reason", set.getString("SHORT_DESCRIPTION"));
/*      */           }
/*      */           else {
/* 2354 */             if (set.getInt("TYPE") == 2) {
/* 2355 */               rows.put("Downtime_Reason", "Monitor is Unmanaged");
/*      */             }
/* 2357 */             if (set.getInt("TYPE") == 3) {
/* 2358 */               rows.put("Downtime_Reason", "Scheduled Maintenance");
/*      */             }
/*      */           }
/*      */           
/* 2362 */           if (totdowntime != 0L) {
/* 2363 */             rows.put("TotDownTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(totdowntime)));
/*      */           }
/* 2365 */           if (unmanagedtime != 0L) {
/* 2366 */             rows.put("UnManagedTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(unmanagedtime)));
/*      */           }
/* 2368 */           if (scheduledtime != 0L) {
/* 2369 */             rows.put("ScheduledTime", ReportUtilities.format(ReportUtilities.roundOffToNearestSeconds(scheduledtime)));
/*      */           }
/*      */           
/* 2372 */           if (rows.size() > 0) {
/* 2373 */             downtimesummary.add(rows);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2378 */       request.setAttribute("downtimesummary", downtimesummary);
/* 2379 */       request.setAttribute("size", String.valueOf(downtimesummary.size()));
/*      */       
/* 2381 */       ReportUtilities rep = new ReportUtilities();
/* 2382 */       String valueforpdf = rep.getValueForPeriodForPDF(period);
/* 2383 */       GetWLSGraph graph = new GetWLSGraph();
/* 2384 */       Hashtable colors; if ("pdf".equals(reportform.getReporttype()))
/*      */       {
/* 2386 */         graph.setParam(request.getParameter("resourceid"), "AVAILABILITY", request.getAttribute("period").toString(), true, summary);
/*      */         
/* 2388 */         colors = new Hashtable();
/* 2389 */         colors.put("1", "#00FF00");
/* 2390 */         colors.put("0", "#FF0000");
/* 2391 */         colors.put("2", "#0066CC");
/* 2392 */         colors.put("3", "#FF00FF");
/* 2393 */         ChartInfo cinfo = new ChartInfo();
/* 2394 */         cinfo.setDataSet(graph);
/* 2395 */         cinfo.setHeight("200");
/* 2396 */         cinfo.setWidth("300");
/* 2397 */         cinfo.setColors(colors);
/* 2398 */         cinfo.setDecimal(true);
/* 2399 */         cinfo.setUnits("%");
/* 2400 */         String image = cinfo.getPieChartAsJPG();
/*      */         
/*      */ 
/* 2403 */         request.setAttribute("availabilityImage", image);
/*      */         
/* 2405 */         request.setAttribute("report-type-template", "report.mttravail");
/* 2406 */         request.setAttribute("period", period);
/* 2407 */         request.setAttribute("periodvalue", valueforpdf);
/* 2408 */         request.setAttribute("sp-report-type", "pdf");
/*      */         
/* 2410 */         return mapping.findForward("availabilitydata.success.pdf");
/*      */       }
/*      */       
/* 2413 */       request.setAttribute("STATUS", "SUCCESS");
/* 2414 */       return mapping.findForward("availabilitydata.success");
/*      */     }
/*      */     catch (Exception exp) {
/*      */       String displayName;
/* 2418 */       log.fatal("Exception in getting availability details ", exp);
/* 2419 */       exp.printStackTrace();
/* 2420 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 2421 */       saveMessages(request, messages);
/* 2422 */       return mapping.getInputForward();
/*      */     }
/*      */     finally
/*      */     {
/* 2426 */       closeResultSet(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward deleteDowntimeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2432 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2433 */     int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/* 2434 */     long downtime = Long.parseLong(request.getParameter("downtime"));
/* 2435 */     int reasonid = Integer.parseInt(request.getParameter("reasonid"));
/* 2436 */     String period = request.getParameter("period");
/* 2437 */     String query = "delete from AM_MO_DowntimeData where RESID=" + resourceid + " and DOWNTIME=" + downtime;
/* 2438 */     if (reasonid != -1) {
/* 2439 */       String reasonQuery = "delete from AM_DOWNTIMEREASON where REASONID = " + reasonid;
/*      */       try {
/* 2441 */         AMConnectionPool.executeUpdateStmt(reasonQuery);
/*      */       }
/*      */       catch (Exception ex) {
/* 2444 */         ex.printStackTrace();
/*      */       }
/* 2446 */       EnterpriseUtil.addUpdateQueryToFile(reasonQuery);
/*      */     }
/*      */     try {
/* 2449 */       AMConnectionPool.executeUpdateStmt(query);
/*      */     }
/*      */     catch (Exception e) {
/* 2452 */       e.printStackTrace();
/*      */     }
/* 2454 */     EnterpriseUtil.addUpdateQueryToFile(query);
/* 2455 */     String temp = null;
/* 2456 */     String redirect = request.getParameter("redirectto");
/* 2457 */     if (redirect != null) {
/* 2458 */       temp = "/showReports.do?actionMethod=generateMttrAvailablityReport&resourceid=" + resourceid + "&period=" + period + "&Report=true&resourceType=Monitors";
/*      */     }
/* 2460 */     else if (request.getParameter("haid") != null)
/*      */     {
/* 2462 */       temp = "/showapplication.do?&method=showApplication&haid=" + request.getParameter("haid");
/*      */     }
/*      */     else {
/* 2465 */       temp = "/showHistoryData.do?method=getAvailabilityData&resourceid=" + resourceid + "&period=" + period;
/*      */     }
/*      */     
/* 2468 */     return new ActionForward(temp);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward insertDowntimeNote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2475 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2476 */     ResultSet rsDowntime = null;
/* 2477 */     String note = null;
/* 2478 */     String downtimeEqualQuery = null;
/* 2479 */     String insertQuery = null;
/* 2480 */     String updateQuery = null;
/* 2481 */     String newupdateQuery = null;
/* 2482 */     int noteID = 0;
/*      */     try
/*      */     {
/* 2485 */       response.setContentType("text/html;charset=UTF-8");
/* 2486 */       int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/* 2487 */       int reasonid = Integer.parseInt(request.getParameter("reasonid"));
/* 2488 */       long downtime = Long.parseLong(request.getParameter("downtime"));
/* 2489 */       note = URLDecoder.decode(request.getParameter("textValue"));
/* 2490 */       PrintWriter out = response.getWriter();
/*      */       
/* 2492 */       if (note != null)
/*      */       {
/* 2494 */         if (reasonid != -1)
/*      */         {
/* 2496 */           updateQuery = "update AM_DOWNTIMEREASON set SHORT_DESCRIPTION = '" + note + "' where REASONID = " + reasonid;
/*      */           try {
/* 2498 */             AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */           }
/*      */           catch (Exception e) {
/* 2501 */             e.printStackTrace();
/*      */           }
/* 2503 */           noteID = reasonid;
/*      */         }
/*      */         else
/*      */         {
/* 2507 */           PreparedStatement ps = null;
/* 2508 */           int reasonID = DBQueryUtil.getIncrementedID("REASONID", "AM_DOWNTIMEREASON");
/* 2509 */           ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_DOWNTIMEREASON (REASONID,SHORT_DESCRIPTION,LONG_DESCRIPTION) values (?,?,?)");
/*      */           try
/*      */           {
/* 2512 */             ps.setInt(1, reasonID);
/* 2513 */             ps.setString(2, note);
/* 2514 */             ps.setString(3, note);
/* 2515 */             ps.executeUpdate();
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/* 2524 */               if (ps != null)
/*      */               {
/* 2526 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 2531 */               ex.printStackTrace();
/*      */             }
/*      */             
/* 2534 */             downtimeEqualQuery = "select DOWNTIME from AM_MO_DowntimeData where RESID = " + resourceid + " and DOWNTIME = " + downtime + " order by DOWNTIME desc";
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 2518 */             ex.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 2524 */               if (ps != null)
/*      */               {
/* 2526 */                 ps.close();
/*      */               }
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 2531 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */           try
/*      */           {
/* 2536 */             rsDowntime = AMConnectionPool.executeQueryStmt(downtimeEqualQuery);
/*      */           }
/*      */           catch (Exception ex1) {
/* 2539 */             ex1.printStackTrace();
/*      */           }
/*      */           
/* 2542 */           if (rsDowntime.next())
/*      */           {
/* 2544 */             newupdateQuery = "update AM_MO_DowntimeData set REASONID = " + reasonID + " where RESID = " + resourceid + " and DOWNTIME=" + downtime;
/*      */           }
/*      */           else
/*      */           {
/* 2548 */             newupdateQuery = DBQueryUtil.getTopNValues("update AM_MO_DowntimeData set REASONID = " + reasonID + " where RESID = " + resourceid + " and DOWNTIME < " + downtime + " order by DOWNTIME desc", "1");
/*      */           }
/*      */           try {
/* 2551 */             AMConnectionPool.executeUpdateStmt(newupdateQuery);
/* 2552 */             EnterpriseUtil.addUpdateQueryToFile(newupdateQuery);
/*      */           }
/*      */           catch (Exception ex2) {
/* 2555 */             ex2.printStackTrace();
/*      */           }
/* 2557 */           noteID = reasonID;
/*      */         }
/*      */       }
/* 2560 */       out.println(noteID);
/* 2561 */       out.flush();
/*      */     }
/*      */     catch (Exception ex3)
/*      */     {
/* 2565 */       ex3.printStackTrace();
/*      */     }
/*      */     finally {
/* 2568 */       closeResultSet(rsDowntime);
/*      */     }
/* 2570 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward cancelDowntimeNote(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2575 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2576 */     ResultSet set = null;
/* 2577 */     String note = null;
/* 2578 */     String selectQuery = null;
/* 2579 */     int typeID = -1;
/*      */     try {
/* 2581 */       response.setContentType("text/html;charset=UTF-8");
/* 2582 */       int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/* 2583 */       int noteID = Integer.parseInt(request.getParameter("reasonid"));
/* 2584 */       long downtime = Long.parseLong(request.getParameter("downtime"));
/* 2585 */       note = URLDecoder.decode(request.getParameter("textValue"));
/* 2586 */       PrintWriter out1 = response.getWriter();
/*      */       
/* 2588 */       if ((note != null) && (noteID != -1))
/*      */       {
/* 2590 */         selectQuery = "select SHORT_DESCRIPTION from AM_DOWNTIMEREASON where REASONID = " + noteID;
/*      */         try
/*      */         {
/* 2593 */           set = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */         }
/*      */         catch (Exception ex3) {
/* 2596 */           ex3.printStackTrace();
/*      */         }
/* 2598 */         if (set.next())
/*      */         {
/* 2600 */           note = set.getString(1);
/*      */         }
/*      */       }
/*      */       
/* 2604 */       out1.println(noteID + "~" + note);
/* 2605 */       out1.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2609 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2612 */       closeResultSet(set);
/*      */     }
/* 2614 */     return null;
/*      */   }
/*      */   
/*      */   private final Properties calculateAvailabilityDetails(String resourceid, long startTime, long endTime) {
/* 2618 */     return ReportUtilities.calculateAvailabilityDetails(resourceid, startTime, endTime);
/*      */   }
/*      */   
/*      */   public ActionForward getsegmentByHourData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2624 */     ReportForm rf = (ReportForm)form;
/* 2625 */     ActionMessages messages = new ActionMessages();
/* 2626 */     ActionErrors errors = new ActionErrors();
/*      */     
/* 2628 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 2629 */     request.setAttribute("resourceid", request.getParameter("resourceid"));
/*      */     
/* 2631 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/* 2632 */     request.setAttribute("attributeid", request.getParameter("attributeid"));
/* 2633 */     String childID = request.getParameter("childid");
/* 2634 */     String period = request.getParameter("period");
/* 2635 */     request.setAttribute("period", period);
/* 2636 */     int tab = Integer.parseInt(request.getParameter("tabsel"));
/* 2637 */     request.setAttribute("tabsel", Integer.valueOf(tab));
/* 2638 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(Integer.toString(attID));
/* 2639 */     String archivedTableName = (String)attribDetails.get(0);
/* 2640 */     String expression1 = (String)attribDetails.get(1);
/* 2641 */     String startdate = request.getParameter("startDate");
/* 2642 */     if (startdate == null) {
/* 2643 */       startdate = rf.getStartDate();
/*      */     }
/*      */     
/* 2646 */     String enddate = request.getParameter("endDate");
/* 2647 */     if (enddate == null) {
/* 2648 */       enddate = rf.getEndDate();
/*      */     }
/* 2650 */     Calendar ucal = Calendar.getInstance();
/* 2651 */     ucal.setTimeInMillis(0L);
/* 2652 */     String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */     
/* 2654 */     String status = null;
/* 2655 */     String attName = null;
/* 2656 */     boolean status_flag = false;
/* 2657 */     long customstartTime = 0L;
/* 2658 */     long customendTime = 0L;
/* 2659 */     long startTime = 0L;
/* 2660 */     long endTime = System.currentTimeMillis();
/* 2661 */     if (period.equals("4"))
/*      */     {
/* 2663 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 2664 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/* 2665 */       if (customstartTime > customendTime) {
/* 2666 */         status = "Start Time " + new java.util.Date(customstartTime) + " is greater than the End Time " + new java.util.Date(customendTime);
/* 2667 */         startTime = customstartTime;
/* 2668 */         endTime = customendTime;
/*      */       }
/*      */       else {
/* 2671 */         startTime = customstartTime;
/* 2672 */         endTime = customendTime;
/*      */         
/* 2674 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/* 2675 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*      */         
/* 2677 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2682 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/* 2683 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/* 2684 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/* 2686 */       System.out.println(period + "^^^^^^^^^^^^^^^^ PERIOD PRINT ^^^^^^^^^^^^^^^^ START ---> " + startTime + " End date -----> " + endTime);
/*      */       
/* 2688 */       if (period.equals("0"))
/*      */       {
/* 2690 */         startTime += 60000L;
/*      */       }
/* 2692 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */     }
/*      */     
/* 2695 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2696 */     String resQuery = "select DISPLAYNAME, TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/* 2697 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2700 */       rs = AMConnectionPool.executeQueryStmt(resQuery);
/* 2701 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*      */       
/* 2703 */       if (rs.next())
/*      */       {
/* 2705 */         String resName = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 2706 */         if (attID == 711)
/*      */         {
/* 2708 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 2709 */           String name = resName;
/* 2710 */           String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 2711 */           String[] temp = dname1.split(":", 2);
/* 2712 */           String s = alldisplayname.get(temp[0]).toString();
/* 2713 */           if ((s != null) && (temp.length > 1))
/*      */           {
/* 2715 */             dname1 = s + ":" + temp[1];
/*      */           }
/* 2717 */           request.setAttribute("resourcename", dname1);
/*      */ 
/*      */         }
/* 2720 */         else if (allSecondLevelAttribute.contains(attID + ""))
/*      */         {
/*      */ 
/* 2723 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/* 2724 */           String dname2 = displayname + "_" + resName;
/* 2725 */           request.setAttribute("resourcename", dname2);
/*      */         }
/*      */         else
/*      */         {
/* 2729 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/* 2732 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/* 2733 */       ResultSet titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */       
/* 2735 */       if (titleSet.next())
/*      */       {
/* 2737 */         String title = titleSet.getString("DISPLAYNAME");
/* 2738 */         attName = titleSet.getString("ATTRIBUTE");
/* 2739 */         request.setAttribute("monitortype", title);
/* 2740 */         String unit = titleSet.getString("UNITS");
/* 2741 */         if (unit == null)
/*      */         {
/* 2743 */           unit = "";
/*      */         }
/* 2745 */         request.setAttribute("unit", unit);
/*      */       }
/*      */       
/* 2748 */       long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/* 2749 */       String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime;
/*      */       
/* 2751 */       if (timeStamp[2] > 0L) {
/* 2752 */         dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " OR DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ")";
/*      */       }
/* 2754 */       Hashtable hourhash = new Hashtable();
/* 2755 */       float lowestAvg = 0.0F;
/* 2756 */       int lowestAvgHr = 0;
/* 2757 */       float highestAvg = 0.0F;
/* 2758 */       int highestAvgHr = 0;
/* 2759 */       boolean setFlag = true;
/* 2760 */       for (int i = 0; i < 24; i++)
/*      */       {
/* 2762 */         Hashtable datahash = new Hashtable();
/*      */         
/*      */ 
/*      */ 
/* 2766 */         String query = DBQueryUtil.getDBQuery("am.historydataaction.getsegmentbyhourdata.query", new String[] { archivedTableName, String.valueOf(resID), String.valueOf(attID), dailyRptCondition, unixStartTime, String.valueOf(i), expression1, System.getProperty("user.timezone", "UTC") });
/*      */         
/*      */ 
/* 2769 */         System.out.println("######################### Seg/Hr DATA QUERY ######################## " + query);
/*      */         
/*      */ 
/*      */ 
/* 2773 */         request.setAttribute("STIME", Long.valueOf(startTime));
/* 2774 */         request.setAttribute("ETIME", Long.valueOf(endTime));
/*      */         
/* 2776 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2777 */         double minAvgValue = -1.0D;
/* 2778 */         double maxAvgValue = -1.0D;
/* 2779 */         if (rs.next())
/*      */         {
/* 2781 */           if ((rs.getString("MIN") != null) && (rs.getFloat("MIN") >= 0.0F))
/*      */           {
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
/* 2795 */             datahash.put("MIN", rs.getString("MIN"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 2800 */             datahash.put("MIN", "-");
/*      */           }
/* 2802 */           if ((rs.getString("MAX") != null) && (rs.getFloat("MAX") >= 0.0F))
/*      */           {
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
/* 2816 */             datahash.put("MAX", rs.getString("MAX"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 2821 */             datahash.put("MAX", "-");
/*      */           }
/* 2823 */           if ((rs.getString("AVG") != null) && (rs.getFloat("AVG") >= 0.0F))
/*      */           {
/* 2825 */             float avg_float = rs.getFloat("AVG");
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
/* 2837 */             datahash.put("AVG", String.valueOf(Math.round(rs.getFloat("AVG") * 100.0F) / 100.0F));
/*      */             
/*      */ 
/* 2840 */             status_flag = true;
/* 2841 */             if (setFlag)
/*      */             {
/* 2843 */               lowestAvg = avg_float;
/* 2844 */               highestAvg = avg_float;
/* 2845 */               lowestAvgHr = i;
/* 2846 */               highestAvgHr = i;
/* 2847 */               setFlag = false;
/*      */             }
/* 2849 */             if (lowestAvg > avg_float)
/*      */             {
/* 2851 */               lowestAvg = avg_float;
/* 2852 */               lowestAvgHr = i;
/*      */             }
/* 2854 */             if (highestAvg < avg_float)
/*      */             {
/* 2856 */               highestAvg = avg_float;
/* 2857 */               highestAvgHr = i;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2862 */             datahash.put("AVG", "-");
/*      */           }
/* 2864 */           hourhash.put(Integer.toString(i), datahash);
/*      */         }
/*      */       }
/* 2867 */       request.setAttribute("LowestAvg", SegmentReportUtil.getDayorTime(lowestAvgHr, 1));
/* 2868 */       request.setAttribute("HighestAvg", SegmentReportUtil.getDayorTime(highestAvgHr, 1));
/* 2869 */       request.setAttribute("DATA", hourhash);
/* 2870 */       request.setAttribute("starttime", new java.util.Date(startTime));
/* 2871 */       request.setAttribute("endtime", new java.util.Date(endTime));
/* 2872 */       System.out.println("######################### Seg/Hr DATA  ######################## " + hourhash);
/*      */       
/* 2874 */       if ((hourhash.size() > 0) && (status_flag))
/*      */       {
/* 2876 */         status = "SUCCESS";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2881 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */       
/* 2884 */       request.setAttribute("STATUS", status);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2889 */       ex.printStackTrace();
/* 2890 */       log.fatal("History data Segmented By Day Reports :  Exception ", ex);
/* 2891 */       request.setAttribute("heading", "0");
/* 2892 */       request.setAttribute("strTime", "0");
/* 2893 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", ex.toString()));
/* 2894 */       saveMessages(request, messages);
/* 2895 */       return mapping.findForward("report.message");
/*      */     }
/*      */     
/* 2898 */     return mapping.findForward("historydata.segmentbyhr");
/*      */   }
/*      */   
/*      */   public ActionForward getsegmentByWeekData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2904 */     ReportForm rf = (ReportForm)form;
/* 2905 */     ActionMessages messages = new ActionMessages();
/* 2906 */     ActionErrors errors = new ActionErrors();
/* 2907 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 2908 */     request.setAttribute("resourceid", request.getParameter("resourceid"));
/*      */     
/* 2910 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/* 2911 */     request.setAttribute("attributeid", request.getParameter("attributeid"));
/* 2912 */     int tab = Integer.parseInt(request.getParameter("tabsel"));
/* 2913 */     request.setAttribute("tabsel", Integer.valueOf(tab));
/* 2914 */     String childID = request.getParameter("childid");
/* 2915 */     String period = request.getParameter("period");
/* 2916 */     request.setAttribute("period", period);
/* 2917 */     String businessRule = request.getParameter("businessPeriod");
/* 2918 */     if (businessRule == null)
/*      */     {
/* 2920 */       businessRule = "oni";
/*      */     }
/* 2922 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(Integer.toString(attID));
/* 2923 */     String archivedTableName = (String)attribDetails.get(0);
/* 2924 */     String expression1 = (String)attribDetails.get(1);
/* 2925 */     String startdate = request.getParameter("startdate");
/* 2926 */     if (startdate == null) {
/* 2927 */       startdate = rf.getStartDate();
/*      */     }
/*      */     
/* 2930 */     String enddate = request.getParameter("enddate");
/* 2931 */     if (enddate == null) {
/* 2932 */       enddate = rf.getEndDate();
/*      */     }
/* 2934 */     Calendar ucal = Calendar.getInstance();
/* 2935 */     ucal.setTimeInMillis(0L);
/* 2936 */     String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/* 2937 */     String status = null;
/* 2938 */     String attName = null;
/* 2939 */     boolean status_flag = false;
/* 2940 */     long customstartTime = 0L;
/* 2941 */     long customendTime = 0L;
/* 2942 */     long startTime = 0L;
/* 2943 */     long endTime = System.currentTimeMillis();
/* 2944 */     if (period.equals("4"))
/*      */     {
/* 2946 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 2947 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/* 2948 */       if (customstartTime > customendTime) {
/* 2949 */         status = "Start Time " + new java.util.Date(customstartTime) + " is greater than the End Time " + new java.util.Date(customendTime);
/* 2950 */         startTime = customstartTime;
/* 2951 */         endTime = customendTime;
/*      */       }
/*      */       else {
/* 2954 */         startTime = customstartTime;
/* 2955 */         endTime = customendTime;
/* 2956 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/* 2957 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*      */         
/* 2959 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2964 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/* 2965 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/* 2966 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/*      */ 
/*      */ 
/* 2970 */       if (period.equals("3"))
/*      */       {
/* 2972 */         endTime -= 60000L;
/*      */       }
/* 2974 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */     }
/* 2976 */     System.out.println(period + "^^^^^^^^^^^^^^^^ PERIOD PRINT ^^^^^^^^^^^^^^^^ START ---> " + startTime + " End date -----> " + endTime);
/* 2977 */     request.setAttribute("starttime", new java.util.Date(startTime));
/* 2978 */     request.setAttribute("endtime", new java.util.Date(endTime));
/* 2979 */     request.setAttribute("STATUS", status);
/* 2980 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2981 */     String resQuery = "select DISPLAYNAME, TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/* 2982 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2985 */       rs = AMConnectionPool.executeQueryStmt(resQuery);
/* 2986 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*      */       
/* 2988 */       if (rs.next())
/*      */       {
/* 2990 */         String resName = EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME"));
/* 2991 */         if (attID == 711)
/*      */         {
/* 2993 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 2994 */           String name = resName;
/* 2995 */           String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 2996 */           String[] temp = dname1.split(":", 2);
/* 2997 */           String s = alldisplayname.get(temp[0]).toString();
/* 2998 */           if ((s != null) && (temp.length > 1))
/*      */           {
/* 3000 */             dname1 = s + ":" + temp[1];
/*      */           }
/* 3002 */           request.setAttribute("resourcename", dname1);
/*      */ 
/*      */         }
/* 3005 */         else if (allSecondLevelAttribute.contains(attID + ""))
/*      */         {
/*      */ 
/* 3008 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/* 3009 */           String dname2 = displayname + "_" + resName;
/* 3010 */           request.setAttribute("resourcename", dname2);
/*      */         }
/*      */         else
/*      */         {
/* 3014 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/* 3017 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/* 3018 */       ResultSet titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */       
/* 3020 */       if (titleSet.next())
/*      */       {
/* 3022 */         String title = titleSet.getString("DISPLAYNAME");
/* 3023 */         attName = titleSet.getString("ATTRIBUTE");
/* 3024 */         request.setAttribute("monitortype", title);
/* 3025 */         String unit = titleSet.getString("UNITS");
/* 3026 */         if (unit == null)
/*      */         {
/* 3028 */           unit = "";
/*      */         }
/* 3030 */         request.setAttribute("unit", unit);
/*      */       }
/*      */       
/* 3033 */       long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/* 3034 */       String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime;
/*      */       
/* 3036 */       if (timeStamp[2] > 0L) {
/* 3037 */         dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " OR DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ")";
/*      */       }
/* 3039 */       Hashtable weekhash = new Hashtable();
/*      */       
/* 3041 */       StringBuilder bhr_condition = new StringBuilder();
/* 3042 */       StringBuilder bhr_condition1 = new StringBuilder();
/* 3043 */       StringBuilder bhr_condition2 = new StringBuilder();
/* 3044 */       Hashtable bHourDetail = new Hashtable();
/* 3045 */       boolean forBHr = false;
/* 3046 */       if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */       {
/* 3048 */         bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/* 3049 */         forBHr = true;
/* 3050 */         request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/* 3051 */         request.setAttribute("businessPeriod", businessRule);
/*      */         
/* 3053 */         if (timeStamp[2] > 0L)
/*      */         {
/* 3055 */           ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(timeStamp[0], timeStamp[1], resID, attID, archivedTableName, bHourDetail);
/* 3056 */           for (int i = 0; i < blist.size(); i++)
/*      */           {
/* 3058 */             Hashtable time_stamps = (Hashtable)blist.get(i);
/* 3059 */             if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */             {
/* 3061 */               bhr_condition1.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3065 */               bhr_condition1.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3069 */           ArrayList blist2 = SegmentReportUtil.getBizHourTimeStamps(timeStamp[2], timeStamp[3], resID, attID, archivedTableName, bHourDetail);
/* 3070 */           for (int i = 0; i < blist2.size(); i++)
/*      */           {
/* 3072 */             Hashtable time_stamps = (Hashtable)blist2.get(i);
/* 3073 */             if ((bhr_condition2 != null) && (bhr_condition2.length() > 1))
/*      */             {
/* 3075 */               bhr_condition2.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3079 */               bhr_condition2.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3083 */           if ((bhr_condition1 == null) || ((bhr_condition1 != null) && (bhr_condition1.length() <= 1)))
/*      */           {
/* 3085 */             bhr_condition1.append("(ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/* 3087 */           else if ((bhr_condition2 == null) || ((bhr_condition2 != null) && (bhr_condition2.length() <= 1)))
/*      */           {
/* 3089 */             bhr_condition2.append("(ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/* 3091 */           bhr_condition.append("((DURATION=1 and (" + bhr_condition1 + ")) OR (DURATION=2 and (" + bhr_condition2 + ")))");
/*      */         }
/*      */         else
/*      */         {
/* 3095 */           ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(startTime, endTime, resID, attID, archivedTableName, bHourDetail);
/* 3096 */           for (int i = 0; i < blist.size(); i++)
/*      */           {
/* 3098 */             Hashtable time_stamps = (Hashtable)blist.get(i);
/* 3099 */             if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */             {
/* 3101 */               bhr_condition1.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3105 */               bhr_condition1.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3109 */           if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */           {
/* 3111 */             bhr_condition.append("DURATION=1 and (" + bhr_condition1 + ")");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3116 */             bhr_condition.append("DURATION=1 and (ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 3121 */       float lowestAvg = 0.0F;
/* 3122 */       int lowestAvgDay = 0;
/* 3123 */       float highestAvg = 0.0F;
/* 3124 */       int highestAvgDay = 0;
/* 3125 */       boolean setFlag = true;
/*      */       
/* 3127 */       int dateFirst = 0;
/* 3128 */       if ("mssql".equals(DBQueryUtil.getDBType()))
/*      */       {
/* 3130 */         ResultSet datefirst_rs = null;
/*      */         try {
/* 3132 */           datefirst_rs = DBQueryUtil.executeQueryStmt("SELECT @@DATEFIRST  as DATEFIRST");
/* 3133 */           if (datefirst_rs.next())
/*      */           {
/* 3135 */             dateFirst = datefirst_rs.getInt("DATEFIRST");
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 3140 */           exc.printStackTrace();
/* 3141 */           log.fatal("History data Segmented By Week Reports :  Exception ", exc);
/*      */         }
/*      */         
/* 3144 */         closeResultSet(datefirst_rs);
/*      */       }
/* 3146 */       for (int i = 0; i < 7; i++)
/*      */       {
/* 3148 */         Hashtable datahash = new Hashtable();
/*      */         
/*      */ 
/*      */ 
/* 3152 */         int week = i;
/* 3153 */         if ("mssql".equals(DBQueryUtil.getDBType()))
/*      */         {
/* 3155 */           week = i + (7 - dateFirst) + 1;
/* 3156 */           if (week > 7)
/*      */           {
/* 3158 */             week -= 7;
/*      */           }
/*      */         }
/* 3161 */         String query = DBQueryUtil.getDBQuery("am.historydataaction.getsegmentbyweekdata.query", new String[] { archivedTableName, String.valueOf(resID), String.valueOf(attID), dailyRptCondition, unixStartTime, String.valueOf(week), expression1 });
/* 3162 */         if (forBHr)
/*      */         {
/*      */ 
/* 3165 */           query = DBQueryUtil.getDBQuery("am.historydataaction.getsegmentbyweekdata.bhour.query", new String[] { archivedTableName, String.valueOf(resID), String.valueOf(attID), bhr_condition.toString(), unixStartTime, String.valueOf(week), expression1 });
/*      */         }
/* 3167 */         System.out.println("######################### Seg/Week DATA QUERY ######################## " + query);
/*      */         
/* 3169 */         request.setAttribute("STIME", Long.valueOf(startTime));
/* 3170 */         request.setAttribute("ETIME", Long.valueOf(endTime));
/*      */         
/* 3172 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3173 */         double minAvgValue = -1.0D;
/* 3174 */         double maxAvgValue = -1.0D;
/*      */         
/* 3176 */         if (rs.next())
/*      */         {
/* 3178 */           if ((rs.getString("MIN") != null) && (rs.getFloat("MIN") >= 0.0F))
/*      */           {
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
/* 3190 */             datahash.put("MIN", rs.getString("MIN"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3195 */             datahash.put("MIN", "-");
/*      */           }
/* 3197 */           if ((rs.getString("MAX") != null) && (rs.getFloat("MAX") >= 0.0F))
/*      */           {
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
/* 3209 */             datahash.put("MAX", rs.getString("MAX"));
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3214 */             datahash.put("MAX", "-");
/*      */           }
/* 3216 */           if ((rs.getString("AVG") != null) && (rs.getFloat("AVG") >= 0.0F))
/*      */           {
/* 3218 */             float avg_float = rs.getFloat("AVG");
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
/* 3230 */             datahash.put("AVG", String.valueOf(Math.round(rs.getFloat("AVG") * 100.0F) / 100.0F));
/*      */             
/*      */ 
/* 3233 */             status_flag = true;
/* 3234 */             if (setFlag)
/*      */             {
/* 3236 */               lowestAvg = avg_float;
/* 3237 */               highestAvg = avg_float;
/* 3238 */               lowestAvgDay = i;
/* 3239 */               highestAvgDay = i;
/* 3240 */               setFlag = false;
/*      */             }
/* 3242 */             if (lowestAvg > avg_float)
/*      */             {
/* 3244 */               lowestAvg = avg_float;
/* 3245 */               lowestAvgDay = i;
/*      */             }
/* 3247 */             if (highestAvg < avg_float)
/*      */             {
/* 3249 */               highestAvg = avg_float;
/* 3250 */               highestAvgDay = i;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3255 */             datahash.put("AVG", "-");
/*      */           }
/*      */           
/* 3258 */           weekhash.put(Integer.toString(i), datahash);
/*      */         }
/*      */       }
/*      */       
/* 3262 */       request.setAttribute("LowestAvg", SegmentReportUtil.getDayorTime(lowestAvgDay, 2));
/* 3263 */       request.setAttribute("HighestAvg", SegmentReportUtil.getDayorTime(highestAvgDay, 2));
/* 3264 */       request.setAttribute("DATA", weekhash);
/* 3265 */       System.out.println("######################### Seg/Week DATA  ######################## " + weekhash);
/* 3266 */       if ((weekhash.size() > 0) && (status_flag))
/*      */       {
/* 3268 */         status = "SUCCESS";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 3273 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */       
/* 3276 */       request.setAttribute("STATUS", status);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3281 */       ex.printStackTrace();
/* 3282 */       log.fatal("History data Segmented By Week Reports :  Exception ", ex);
/* 3283 */       request.setAttribute("heading", "0");
/* 3284 */       request.setAttribute("strTime", "0");
/* 3285 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", ex.toString()));
/* 3286 */       saveMessages(request, messages);
/* 3287 */       return mapping.findForward("report.message");
/*      */     }
/* 3289 */     closeResultSet(rs);
/*      */     
/* 3291 */     return mapping.findForward("historydata.segmentbywk");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getStandardDeviationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3301 */     ReportForm rf = (ReportForm)form;
/* 3302 */     ActionMessages messages = new ActionMessages();
/* 3303 */     ActionErrors errors = new ActionErrors();
/* 3304 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 3305 */     request.setAttribute("resourceid", request.getParameter("resourceid"));
/*      */     
/* 3307 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/* 3308 */     request.setAttribute("attributeid", request.getParameter("attributeid"));
/* 3309 */     int tab = Integer.parseInt(request.getParameter("tabsel"));
/* 3310 */     request.setAttribute("tabsel", Integer.valueOf(tab));
/* 3311 */     String childID = request.getParameter("childid");
/* 3312 */     request.setAttribute("childid", childID);
/* 3313 */     String period = request.getParameter("period");
/* 3314 */     request.setAttribute("period", period);
/* 3315 */     String businessRule = request.getParameter("businessPeriod");
/* 3316 */     if (businessRule == null)
/*      */     {
/* 3318 */       businessRule = "oni";
/*      */     }
/* 3320 */     ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(Integer.toString(attID));
/* 3321 */     String archivedTableName = (String)attribDetails.get(0);
/* 3322 */     String expression1 = (String)attribDetails.get(1);
/* 3323 */     String startdate = request.getParameter("startdate");
/* 3324 */     if (startdate == null) {
/* 3325 */       startdate = rf.getStartDate();
/*      */     }
/*      */     
/* 3328 */     String enddate = request.getParameter("enddate");
/* 3329 */     if (enddate == null) {
/* 3330 */       enddate = rf.getEndDate();
/*      */     }
/*      */     
/* 3333 */     String status = null;
/* 3334 */     String attName = null;
/* 3335 */     String unit = null;
/* 3336 */     boolean status_flag = false;
/* 3337 */     long customstartTime = 0L;
/* 3338 */     long customendTime = 0L;
/* 3339 */     long startTime = 0L;
/* 3340 */     long endTime = System.currentTimeMillis();
/* 3341 */     if (period.equals("4"))
/*      */     {
/* 3343 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 3344 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/* 3345 */       if (customstartTime > customendTime) {
/* 3346 */         status = "Start Time " + new java.util.Date(customstartTime) + " is greater than the End Time " + new java.util.Date(customendTime);
/* 3347 */         startTime = customstartTime;
/* 3348 */         endTime = customendTime;
/*      */       }
/*      */       else {
/* 3351 */         startTime = customstartTime;
/* 3352 */         endTime = customendTime;
/* 3353 */         request.setAttribute("customstarttime", Long.toString(customstartTime));
/* 3354 */         request.setAttribute("customendtime", Long.toString(customendTime));
/*      */         
/* 3356 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 3362 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/* 3363 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/* 3364 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*      */       
/* 3366 */       System.out.println(period + "^^^^^^^^^^^^^^^^ PERIOD PRINT ^^^^^^^^^^^^^^^^ START ---> " + startTime + " End date -----> " + endTime);
/*      */       
/* 3368 */       if (period.equals("3"))
/*      */       {
/* 3370 */         endTime -= 60000L;
/*      */       }
/* 3372 */       status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */     }
/* 3374 */     request.setAttribute("starttime", new java.util.Date(startTime));
/* 3375 */     request.setAttribute("endtime", new java.util.Date(endTime));
/*      */     
/* 3377 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3378 */     String resQuery = "select DISPLAYNAME, TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/* 3379 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3382 */       rs = AMConnectionPool.executeQueryStmt(resQuery);
/* 3383 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*      */       
/* 3385 */       if (rs.next())
/*      */       {
/* 3387 */         String resName = rs.getString("DISPLAYNAME");
/* 3388 */         resName = EnterpriseUtil.decodeString(resName);
/* 3389 */         if (attID == 711)
/*      */         {
/* 3391 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 3392 */           String name = resName;
/* 3393 */           String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 3394 */           String[] temp = dname1.split(":", 2);
/* 3395 */           String s = alldisplayname.get(temp[0]).toString();
/* 3396 */           if ((s != null) && (temp.length > 1))
/*      */           {
/* 3398 */             dname1 = s + ":" + temp[1];
/*      */           }
/* 3400 */           request.setAttribute("resourcename", dname1);
/*      */ 
/*      */         }
/* 3403 */         else if (allSecondLevelAttribute.contains(attID + ""))
/*      */         {
/*      */ 
/* 3406 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/* 3407 */           String dname2 = displayname + "_" + resName;
/* 3408 */           request.setAttribute("resourcename", dname2);
/*      */         }
/*      */         else
/*      */         {
/* 3412 */           request.setAttribute("resourcename", resName);
/*      */         }
/*      */       }
/* 3415 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/* 3416 */       ResultSet titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*      */       
/* 3418 */       if (titleSet.next())
/*      */       {
/* 3420 */         String title = titleSet.getString("DISPLAYNAME");
/* 3421 */         attName = titleSet.getString("ATTRIBUTE");
/* 3422 */         request.setAttribute("monitortype", title);
/* 3423 */         unit = titleSet.getString("UNITS");
/* 3424 */         if (unit == null)
/*      */         {
/* 3426 */           unit = "";
/*      */         }
/* 3428 */         request.setAttribute("unit", unit);
/*      */       }
/*      */       
/*      */ 
/* 3432 */       Hashtable weekhash = new Hashtable();
/* 3433 */       long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/* 3434 */       String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime;
/*      */       
/* 3436 */       if (timeStamp[2] > 0L) {
/* 3437 */         dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " OR DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ")";
/*      */       }
/*      */       
/* 3440 */       StringBuilder bhr_condition = new StringBuilder();
/* 3441 */       StringBuilder bhr_condition1 = new StringBuilder();
/* 3442 */       StringBuilder bhr_condition2 = new StringBuilder();
/* 3443 */       Hashtable bHourDetail = new Hashtable();
/* 3444 */       boolean forBHr = false;
/* 3445 */       if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */       {
/* 3447 */         bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/* 3448 */         forBHr = true;
/* 3449 */         request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/* 3450 */         request.setAttribute("businessPeriod", businessRule);
/*      */         
/* 3452 */         if (timeStamp[2] > 0L)
/*      */         {
/* 3454 */           ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(timeStamp[0], timeStamp[1], resID, attID, archivedTableName, bHourDetail);
/* 3455 */           for (int i = 0; i < blist.size(); i++)
/*      */           {
/* 3457 */             Hashtable time_stamps = (Hashtable)blist.get(i);
/* 3458 */             if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */             {
/* 3460 */               bhr_condition1.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3464 */               bhr_condition1.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3468 */           ArrayList blist2 = SegmentReportUtil.getBizHourTimeStamps(timeStamp[2], timeStamp[3], resID, attID, archivedTableName, bHourDetail);
/* 3469 */           for (int i = 0; i < blist2.size(); i++)
/*      */           {
/* 3471 */             Hashtable time_stamps = (Hashtable)blist2.get(i);
/* 3472 */             if ((bhr_condition2 != null) && (bhr_condition2.length() > 1))
/*      */             {
/* 3474 */               bhr_condition2.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3478 */               bhr_condition2.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3482 */           if ((bhr_condition1 == null) || ((bhr_condition1 != null) && (bhr_condition1.length() <= 1)))
/*      */           {
/* 3484 */             bhr_condition1.append("(ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/* 3486 */           else if ((bhr_condition2 == null) || ((bhr_condition2 != null) && (bhr_condition2.length() <= 1)))
/*      */           {
/* 3488 */             bhr_condition2.append("(ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/* 3490 */           bhr_condition.append("((DURATION=1 and (" + bhr_condition1 + ")) OR (DURATION=2 and (" + bhr_condition1 + ")))");
/*      */         }
/*      */         else
/*      */         {
/* 3494 */           ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(startTime, endTime, resID, attID, archivedTableName, bHourDetail);
/* 3495 */           for (int i = 0; i < blist.size(); i++)
/*      */           {
/* 3497 */             Hashtable time_stamps = (Hashtable)blist.get(i);
/* 3498 */             if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */             {
/* 3500 */               bhr_condition1.append(" or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */             else
/*      */             {
/* 3504 */               bhr_condition1.append("(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")");
/*      */             }
/*      */           }
/*      */           
/* 3508 */           if ((bhr_condition1 != null) && (bhr_condition1.length() > 1))
/*      */           {
/* 3510 */             bhr_condition.append("DURATION=1 and (" + bhr_condition1 + ")");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 3515 */             bhr_condition.append("DURATION=1 and (ARCHIVEDTIME > 0 and ARCHIVEDTIME < 0)");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3523 */       Hashtable datahash = new Hashtable();
/*      */       
/*      */ 
/* 3526 */       String query = DBQueryUtil.getDBQuery("am.historyDataAction.getStandardDeviationData.query1", new String[] { expression1, archivedTableName, "" + resID, attID + dailyRptCondition });
/* 3527 */       if (forBHr)
/*      */       {
/*      */ 
/* 3530 */         query = DBQueryUtil.getDBQuery("am.historyDataAction.getStandardDeviationData.query2", new String[] { expression1, archivedTableName, "" + resID, "" + attID, "" + bhr_condition });
/*      */       }
/* 3532 */       System.out.println("######################### StdDevn Min/Max/Avg QUERY ######################## " + query);
/*      */       
/* 3534 */       request.setAttribute("STIME", Long.valueOf(startTime));
/* 3535 */       request.setAttribute("ETIME", Long.valueOf(endTime));
/*      */       
/* 3537 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3538 */       double minAvgValue = -1.0D;
/* 3539 */       double maxAvgValue = -1.0D;
/* 3540 */       String minval = "0.0";
/* 3541 */       String maxval = "0.0";
/* 3542 */       float avgval = 0.0F;
/* 3543 */       int total = 0;
/* 3544 */       if (rs.next())
/*      */       {
/* 3546 */         if (rs.getString("MIN") != null)
/*      */         {
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
/* 3559 */           minval = String.valueOf(Math.round(rs.getFloat("MIN") * 100.0F) / 100.0F);
/*      */         }
/*      */         
/* 3562 */         if (rs.getString("MAX") != null)
/*      */         {
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
/* 3575 */           maxval = String.valueOf(Math.round(rs.getFloat("MAX") * 100.0F) / 100.0F);
/*      */         }
/*      */         
/* 3578 */         if (rs.getString("AVG") != null)
/*      */         {
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
/* 3590 */           avgval = rs.getFloat("AVG");
/*      */         }
/*      */         
/* 3593 */         total = rs.getInt("TOTAL");
/*      */       }
/* 3595 */       rs.close();
/* 3596 */       System.out.println("MIN ========>" + minval + " MAX =======>" + maxval + " AVG =======>" + avgval);
/*      */       
/* 3598 */       request.setAttribute("MINVAL", minval);
/* 3599 */       request.setAttribute("MAXVAL", maxval);
/* 3600 */       request.setAttribute("AVGVAL", String.valueOf(Math.round(avgval * 100.0F) / 100.0F));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3605 */       long currentTime = System.currentTimeMillis();
/* 3606 */       String[] pr = { "0", "3", "6", "12", "7", "11" };
/* 3607 */       float[] avgdata = new float[6];
/* 3608 */       ResultSet avgset = null;
/*      */       
/* 3610 */       for (int i = 0; i < 6; i++)
/*      */       {
/*      */ 
/* 3613 */         Properties tProp = getTimePeriod(0L, currentTime, pr[i]);
/* 3614 */         long stTime = ((Long)tProp.get("STARTTIME")).longValue();
/* 3615 */         long edTime = ((Long)tProp.get("ENDTIME")).longValue();
/* 3616 */         String peravgquery = "select (sum(TOTAL)/sum(TOTALCOUNT))" + expression1 + " as AVG from " + archivedTableName + " where RESID=" + resID + " and ATTRIBUTEID=" + attID + " and DURATION=1 and ARCHIVEDTIME <= " + edTime + " and ARCHIVEDTIME >= " + stTime;
/*      */         
/*      */ 
/* 3619 */         avgset = AMConnectionPool.executeQueryStmt(peravgquery);
/* 3620 */         if (avgset.next())
/*      */         {
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
/* 3633 */           avgdata[i] = (Math.round(avgset.getFloat("AVG") * 100.0F) / 100.0F);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 3638 */           avgdata[i] = 0.0F;
/*      */         }
/*      */       }
/*      */       
/* 3642 */       closeResultSet(avgset);
/* 3643 */       request.setAttribute("AVGDATAS", avgdata);
/*      */       
/*      */ 
/* 3646 */       ResultSet sddataSet = null;
/* 3647 */       ArrayList avglist = new ArrayList();
/* 3648 */       ArrayList countlist = new ArrayList();
/* 3649 */       int totcount = 0;
/* 3650 */       int med_pos = 0;
/* 3651 */       float median = 0.0F;
/*      */       
/* 3653 */       String sddataQuery = DBQueryUtil.getDBQuery("am.historyDataAction.getStandardDeviationData.query3", new String[] { expression1, archivedTableName, "" + resID, attID + dailyRptCondition });
/* 3654 */       if (forBHr)
/*      */       {
/*      */ 
/* 3657 */         sddataQuery = DBQueryUtil.getDBQuery("am.historyDataAction.getStandardDeviationData.query4", new String[] { expression1, archivedTableName, "" + resID, "" + attID, "" + bhr_condition });
/*      */       }
/* 3659 */       System.out.println("################### Std Devn QUERY  ===========>" + sddataQuery);
/* 3660 */       sddataSet = AMConnectionPool.executeQueryStmt(sddataQuery);
/* 3661 */       while (sddataSet.next())
/*      */       {
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
/* 3674 */         avglist.add(Float.valueOf(Math.round(sddataSet.getFloat("AVG") * 100.0F) / 100.0F));
/*      */         
/* 3676 */         countlist.add(Integer.valueOf(sddataSet.getInt("COUNT")));
/* 3677 */         totcount++;
/*      */       }
/*      */       
/* 3680 */       if (totcount > 1) {
/*      */         try
/*      */         {
/* 3683 */           if (totcount % 2 != 0)
/*      */           {
/* 3685 */             median = ((Float)avglist.get(totcount / 2)).floatValue();
/*      */           }
/*      */           else
/*      */           {
/* 3689 */             median = Float.valueOf(((Float)avglist.get(totcount / 2 - 1)).floatValue() + ((Float)avglist.get(totcount / 2)).floatValue()).floatValue() / 2.0F;
/*      */           }
/* 3691 */           status = "SUCCESS";
/* 3692 */           median = Math.round(median * 100.0F) / 100.0F;
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 3696 */           ee.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 3701 */         status = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { getFormatedDate(new java.util.Date(startTime)), getFormatedDate(new java.util.Date(endTime)) });
/*      */       }
/* 3703 */       String nfpercentile = getnfPercentile(avglist);
/* 3704 */       System.out.println("################### MEDIAN  ===========>" + median + " ######### 95 Percentile  ===============>" + nfpercentile);
/* 3705 */       request.setAttribute("MEDIAN", Float.valueOf(median));
/* 3706 */       request.setAttribute("NFPERCENTILE", nfpercentile);
/* 3707 */       request.setAttribute("STATUS", status);
/*      */       
/* 3709 */       StringBuilder lowtenlist = new StringBuilder();
/* 3710 */       StringBuilder hightenlist = new StringBuilder();
/* 3711 */       int lwcount = 0;
/* 3712 */       float lwpreVal = 0.0F;
/* 3713 */       for (int n = 0; n < avglist.size(); n++)
/*      */       {
/* 3715 */         if (lowtenlist.length() > 1)
/*      */         {
/* 3717 */           if (lwpreVal != ((Float)avglist.get(n)).floatValue())
/*      */           {
/* 3719 */             lwpreVal = ((Float)avglist.get(n)).floatValue();
/* 3720 */             lowtenlist.append(", ");
/* 3721 */             lowtenlist.append(FormatUtil.formatNumber(Float.valueOf(lwpreVal)));
/* 3722 */             lwcount++;
/*      */           }
/*      */           else
/*      */           {
/* 3726 */             lwpreVal = ((Float)avglist.get(n)).floatValue();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 3731 */           lwcount++;
/* 3732 */           lwpreVal = ((Float)avglist.get(n)).floatValue();
/* 3733 */           lowtenlist.append(FormatUtil.formatNumber(Float.valueOf(lwpreVal)));
/*      */         }
/* 3735 */         if (lwcount == 10) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/* 3740 */       int hgcount = 0;
/* 3741 */       float hgpreVal = 0.0F;
/* 3742 */       for (int n = avglist.size() - 1; n >= 0; n--)
/*      */       {
/* 3744 */         if (hightenlist.length() > 1)
/*      */         {
/* 3746 */           if (hgpreVal != ((Float)avglist.get(n)).floatValue())
/*      */           {
/* 3748 */             hgpreVal = ((Float)avglist.get(n)).floatValue();
/* 3749 */             hightenlist.append(", ");
/* 3750 */             hightenlist.append(FormatUtil.formatNumber(Float.valueOf(hgpreVal)));
/* 3751 */             hgcount++;
/*      */           }
/*      */           else
/*      */           {
/* 3755 */             hgpreVal = ((Float)avglist.get(n)).floatValue();
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 3761 */           hgcount++;
/* 3762 */           hgpreVal = ((Float)avglist.get(n)).floatValue();
/* 3763 */           hightenlist.append(FormatUtil.formatNumber(Float.valueOf(hgpreVal)));
/*      */         }
/*      */         
/* 3766 */         if (hgcount == 10) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/* 3771 */       System.out.println("################### HIGHTEN  ===========> " + hightenlist);
/* 3772 */       System.out.println("################### LOWTEN  ===========>" + lowtenlist);
/*      */       
/* 3774 */       request.setAttribute("LOWTEN", lowtenlist.toString());
/* 3775 */       request.setAttribute("HIGHTEN", hightenlist.toString());
/* 3776 */       Hashtable rangehash = getRangeList(avglist, avgval);
/* 3777 */       ArrayList rangelist = (ArrayList)rangehash.get("range");
/* 3778 */       float stddevn = ((Float)rangehash.get("StdDevn")).floatValue();
/* 3779 */       request.setAttribute("StdDevn", Float.valueOf(stddevn));
/* 3780 */       System.out.println("################### RANGELIST  ===========>" + rangelist);
/* 3781 */       System.out.println("################### TOTAL  ===========>" + total);
/* 3782 */       int rangecount = rangelist.size();
/* 3783 */       int i = 0;
/* 3784 */       int countperrange = 0;
/* 3785 */       int lastrangecount = 0;
/* 3786 */       ArrayList listperrange = new ArrayList();
/* 3787 */       boolean maxnum = false;
/* 3788 */       for (int k = 0; k < rangecount; k++)
/*      */       {
/* 3790 */         countperrange = 0;
/* 3791 */         while ((((Float)avglist.get(i)).floatValue() <= ((Float)rangelist.get(k)).floatValue()) && (!maxnum))
/*      */         {
/* 3793 */           countperrange += ((Integer)countlist.get(i)).intValue();
/* 3794 */           if (i + 1 < totcount)
/*      */           {
/* 3796 */             i++;
/*      */           }
/*      */           else
/*      */           {
/* 3800 */             maxnum = true;
/*      */           }
/*      */         }
/*      */         
/* 3804 */         System.out.println("################### COUNT/RANGE1:" + i + "  ===========>" + countperrange);
/* 3805 */         float count_val = countperrange / total;
/* 3806 */         listperrange.add(Float.valueOf(Math.round(count_val * 10000.0F) / 100.0F));
/*      */       }
/*      */       
/*      */ 
/* 3810 */       if (i + 1 < totcount) {
/* 3811 */         countperrange = 0;
/* 3812 */         while (((Float)avglist.get(i)).floatValue() > ((Float)rangelist.get(rangecount - 1)).floatValue())
/*      */         {
/* 3814 */           countperrange += ((Integer)countlist.get(i)).intValue();
/* 3815 */           if (i + 1 >= totcount)
/*      */             break;
/* 3817 */           i++;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3824 */         System.out.println("################### COUNT/RANGE2:" + i + "  ===========>" + countperrange);
/* 3825 */         float count_val = countperrange / total;
/* 3826 */         if (count_val > 0.0F)
/*      */         {
/* 3828 */           listperrange.add(Float.valueOf(Math.round(count_val * 10000.0F) / 100.0F));
/*      */         }
/*      */       }
/*      */       
/* 3832 */       System.out.println("################### LISTDATA PER RANGE ===========>" + listperrange);
/* 3833 */       request.setAttribute("countperrange", listperrange);
/* 3834 */       request.setAttribute("range", rangelist);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 3840 */       ex.printStackTrace();
/* 3841 */       log.fatal("History data Standard Deviation Reports :  Exception ", ex);
/* 3842 */       request.setAttribute("heading", "0");
/* 3843 */       request.setAttribute("strTime", "0");
/* 3844 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", ex.toString()));
/* 3845 */       saveMessages(request, messages);
/* 3846 */       return mapping.findForward("report.message");
/*      */     }
/*      */     
/*      */ 
/* 3850 */     return mapping.findForward("historydata.stdDevn");
/*      */   }
/*      */   
/*      */ 
/*      */   public String getnfPercentile(ArrayList avgList)
/*      */   {
/* 3856 */     String percentileAvg = "";
/*      */     
/* 3858 */     int length = avgList.size();
/* 3859 */     if (length > 1) {
/* 3860 */       Float[] array = (Float[])avgList.toArray(new Float[length]);
/* 3861 */       Arrays.sort(array);
/*      */       
/* 3863 */       int _95percentileLength = length * 95 / 100;
/* 3864 */       System.out.println(" ######### 95 Percentile length ===============>" + _95percentileLength);
/*      */       
/* 3866 */       float _95percentileAvg = array[(_95percentileLength - 1)].floatValue();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3874 */       percentileAvg = String.valueOf(_95percentileAvg);
/*      */     }
/* 3876 */     return percentileAvg;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Hashtable getRangeList(ArrayList avgList, float mean)
/*      */   {
/* 3883 */     Hashtable rethash = new Hashtable();
/* 3884 */     ArrayList retlist = new ArrayList();
/* 3885 */     ArrayList rangelist = new ArrayList();
/* 3886 */     int rangedivsn = 0;
/* 3887 */     int length = avgList.size();
/* 3888 */     double sqsum = 0.0D;
/* 3889 */     double stddevn = 0.0D;
/*      */     
/* 3891 */     for (int i = 0; i < length; i++)
/*      */     {
/* 3893 */       float diff = ((Float)avgList.get(i)).floatValue() - mean;
/* 3894 */       sqsum += Math.pow(diff, 2.0D);
/*      */     }
/*      */     
/* 3897 */     stddevn = Math.sqrt(sqsum / length);
/* 3898 */     float stddevn_float = (float)Math.round(stddevn * 100.0D) / 100.0F;
/* 3899 */     System.out.println("#################### STD DEVN ########### ------------->" + stddevn_float);
/* 3900 */     if (length > 0)
/*      */     {
/* 3902 */       Float[] array = (Float[])avgList.toArray(new Float[length]);
/* 3903 */       Arrays.sort(array);
/* 3904 */       float minval = array[0].floatValue();
/* 3905 */       float maxval = array[(length - 1)].floatValue();
/* 3906 */       System.out.println("MIN2 ========>" + minval + " MAX2 =======>" + maxval + " AVG2 =======>" + mean);
/* 3907 */       float span = maxval - minval;
/* 3908 */       boolean mincheck = true;
/*      */       
/*      */ 
/* 3911 */       for (int p = 3; p >= -3; p--)
/*      */       {
/* 3913 */         float val = mean + p * stddevn_float;
/* 3914 */         if (val <= 0.0F)
/*      */         {
/* 3916 */           retlist.add(Float.valueOf(0.0F));
/* 3917 */           break;
/*      */         }
/*      */         
/*      */ 
/* 3921 */         retlist.add(Float.valueOf(Math.round(val * 100.0F) / 100.0F));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3928 */     if (retlist != null)
/*      */     {
/* 3930 */       int range_count = retlist.size() - 1;
/* 3931 */       for (int k = range_count; k >= 0; k--)
/*      */       {
/* 3933 */         rangelist.add((Float)retlist.get(k));
/*      */       }
/*      */     }
/* 3936 */     rethash.put("range", rangelist);
/* 3937 */     rethash.put("StdDevn", Float.valueOf(stddevn_float));
/* 3938 */     return rethash;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getheatData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3945 */     ReportForm rf = (ReportForm)form;
/* 3946 */     ActionMessages messages = new ActionMessages();
/* 3947 */     ActionErrors errors = new ActionErrors();
/* 3948 */     int resID = Integer.parseInt(request.getParameter("resourceid"));
/* 3949 */     request.setAttribute("resourceid", request.getParameter("resourceid"));
/* 3950 */     int attID = Integer.parseInt(request.getParameter("attributeid"));
/* 3951 */     request.setAttribute("attributeid", request.getParameter("attributeid"));
/* 3952 */     int tab = Integer.parseInt(request.getParameter("tabsel"));
/* 3953 */     request.setAttribute("tabsel", Integer.valueOf(tab));
/* 3954 */     String childID = request.getParameter("childid");
/*      */     
/* 3956 */     String period = "";
/* 3957 */     String subGroup = "";
/* 3958 */     Vector moNameList = new Vector();
/* 3959 */     Vector resIDList = new Vector();
/* 3960 */     Vector attIDList = new Vector();
/* 3961 */     ArrayList moheatlist = new ArrayList();
/* 3962 */     if (request.getParameter("period") != null)
/*      */     {
/* 3964 */       period = request.getParameter("period");
/*      */     }
/*      */     else
/*      */     {
/* 3968 */       period = "1";
/*      */     }
/* 3970 */     request.setAttribute("period", period);
/* 3971 */     String archivedTableName = DBUtil.getArchiveingTableName(Integer.toString(attID));
/*      */     
/*      */ 
/*      */ 
/* 3975 */     String status = null;
/* 3976 */     String attName = null;
/* 3977 */     boolean status_flag = false;
/* 3978 */     String resName = new String();
/* 3979 */     String secAtttype = "";
/* 3980 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3981 */     List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/*      */     
/* 3983 */     List allThirdLevelAttribute = ReportUtil.getAllThirdLevelAttribute();
/* 3984 */     String resQuery = "select mo.DISPLAYNAME, mo.TYPE,mrt.SUBGROUP from AM_ManagedObject as mo,AM_ManagedResourceType as mrt where mo.RESOURCEID=" + resID + " and mrt.RESOURCETYPE=mo.TYPE";
/* 3985 */     if ((allSecondLevelAttribute.contains(attID + "")) || (allThirdLevelAttribute.contains(attID + "")))
/*      */     {
/* 3987 */       resQuery = "select DISPLAYNAME, TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/*      */     }
/* 3989 */     String moQuery = "";
/* 3990 */     String parentID = "";
/* 3991 */     ResultSet rs = null;
/* 3992 */     ResultSet parentSet = null;
/*      */     
/*      */     try
/*      */     {
/* 3996 */       rs = AMConnectionPool.executeQueryStmt(resQuery);
/*      */       
/* 3998 */       if (rs.next())
/*      */       {
/* 4000 */         resName = rs.getString("DISPLAYNAME");
/* 4001 */         resName = EnterpriseUtil.decodeString(resName);
/*      */         
/*      */ 
/* 4004 */         resIDList.add(String.valueOf(resID));
/* 4005 */         attIDList.add(String.valueOf(attID));
/*      */         
/* 4007 */         if (attID == 711)
/*      */         {
/* 4009 */           secAtttype = rs.getString("TYPE");
/* 4010 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 4011 */           String name = resName;
/* 4012 */           String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 4013 */           String[] temp = dname1.split(":", 2);
/* 4014 */           String s = alldisplayname.get(temp[0]).toString();
/* 4015 */           if ((s != null) && (temp.length > 1))
/*      */           {
/* 4017 */             dname1 = s + ":" + temp[1];
/*      */           }
/* 4019 */           request.setAttribute("resourcename", dname1);
/* 4020 */           moNameList.add(resName.substring(resName.indexOf(":") + 1));
/*      */ 
/*      */         }
/* 4023 */         else if ((allSecondLevelAttribute.contains(attID + "")) || (allThirdLevelAttribute.contains(attID + "")))
/*      */         {
/* 4025 */           secAtttype = rs.getString("TYPE");
/* 4026 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/* 4027 */           String dname2 = displayname + "_" + resName;
/* 4028 */           request.setAttribute("resourcename", dname2);
/* 4029 */           moNameList.add(resName.substring(resName.indexOf(":") + 1));
/*      */         }
/*      */         else
/*      */         {
/* 4033 */           subGroup = rs.getString("SUBGROUP");
/* 4034 */           request.setAttribute("resourcename", resName);
/* 4035 */           moNameList.add(resName);
/*      */         }
/*      */       }
/* 4038 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/* 4039 */       ResultSet titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/* 4040 */       if (titleSet.next())
/*      */       {
/* 4042 */         String title = titleSet.getString("DISPLAYNAME");
/* 4043 */         attName = titleSet.getString("ATTRIBUTE");
/* 4044 */         request.setAttribute("monitortype", title);
/* 4045 */         String unit = titleSet.getString("UNITS");
/* 4046 */         if (unit == null)
/*      */         {
/* 4048 */           unit = "";
/*      */         }
/* 4050 */         request.setAttribute("unit", unit);
/*      */       }
/* 4052 */       if (titleSet != null)
/*      */       {
/* 4054 */         titleSet.close();
/*      */       }
/* 4056 */       String resType = ReportUtilities.getResourceType(String.valueOf(resID));
/* 4057 */       List attributeList = ReportUtil.getAttributeList();
/*      */       
/* 4059 */       if ((!allSecondLevelAttribute.contains(attID + "")) && (!allThirdLevelAttribute.contains(attID + "")))
/*      */       {
/*      */ 
/* 4062 */         if ((request.isUserInRole("OPERATOR")) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */         {
/* 4064 */           String condition = "";
/* 4065 */           if (EnterpriseUtil.isIt360MSPEdition())
/*      */           {
/* 4067 */             condition = ReportUtilities.getQueryCondition("mo.RESOURCEID", request.getRemoteUser(), request);
/*      */           }
/*      */           else
/*      */           {
/* 4071 */             condition = ReportUtilities.getQueryCondition("mo.RESOURCEID", request.getRemoteUser());
/*      */           }
/* 4073 */           moQuery = "select mo.DISPLAYNAME, mo.RESOURCEID,mo.TYPE,att.ATTRIBUTEID from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_ATTRIBUTES as att  where mrt.SUBGROUP='" + subGroup + "' and  mo.TYPE=mrt.RESOURCETYPE and att.ATTRIBUTE='" + attName + "' and att.RESOURCETYPE=mo.TYPE and " + condition + " and mo.RESOURCEID!=" + resID + " order by mo.DISPLAYNAME";
/* 4074 */           if ("HAI".equals(subGroup))
/*      */           {
/* 4076 */             moQuery = "select mo.DISPLAYNAME, mo.RESOURCEID,mo.TYPE,att.ATTRIBUTEID from AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_ATTRIBUTES as att  where mo.TYPE='" + subGroup + "' and att.ATTRIBUTE='" + attName + "' and att.RESOURCETYPE=mo.TYPE and " + condition + " and mo.RESOURCEID!=" + resID + " and ha.HAID=mo.RESOURCEID and ha.GROUPTYPE in (1010,1012) order by mo.DISPLAYNAME";
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 4082 */           moQuery = "select mo.DISPLAYNAME, mo.RESOURCEID,mo.TYPE,att.ATTRIBUTEID from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_ATTRIBUTES as att  where mrt.SUBGROUP='" + subGroup + "' and  mo.TYPE=mrt.RESOURCETYPE and att.ATTRIBUTE='" + attName + "' and att.RESOURCETYPE=mo.TYPE and mo.RESOURCEID!=" + resID + " order by mo.DISPLAYNAME";
/* 4083 */           if ("HAI".equals(subGroup))
/*      */           {
/* 4085 */             moQuery = "select mo.DISPLAYNAME, mo.RESOURCEID,mo.TYPE,att.ATTRIBUTEID from AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_ATTRIBUTES as att  where mo.TYPE='" + subGroup + "' and att.ATTRIBUTE='" + attName + "' and att.RESOURCETYPE=mo.TYPE and mo.RESOURCEID!=" + resID + " and ha.HAID=mo.RESOURCEID and ha.GROUPTYPE in (1010,1012) order by mo.DISPLAYNAME";
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 4092 */         String parentidquery = "SELECT AM_ManagedObject.RESOURCEID AS RESID, AM_ManagedObject.DISPLAYNAME FROM AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.CHILDID='" + resID + "' AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID ";
/* 4093 */         parentSet = AMConnectionPool.executeQueryStmt(parentidquery);
/* 4094 */         if (parentSet.next())
/*      */         {
/* 4096 */           parentID = parentSet.getString("RESID");
/*      */         }
/* 4098 */         if (parentSet != null)
/*      */         {
/* 4100 */           parentSet.close();
/*      */         }
/*      */         
/* 4103 */         moQuery = "select mo.DISPLAYNAME,mo.RESOURCEID," + attID + " as ATTRIBUTEID from AM_ManagedObject as mo, AM_PARENTCHILDMAPPER as pcm where mo.RESOURCEID=pcm.CHILDID and pcm.PARENTID='" + parentID + "' and mo.TYPE='" + secAtttype + "' and mo.RESOURCEID!=" + resID;
/*      */       }
/* 4105 */       ResultSet moSet = AMConnectionPool.executeQueryStmt(moQuery);
/* 4106 */       while (moSet.next())
/*      */       {
/* 4108 */         if ((allSecondLevelAttribute.contains(attID + "")) || (allThirdLevelAttribute.contains(attID + "")))
/*      */         {
/* 4110 */           String temp = moSet.getString("DISPLAYNAME");
/* 4111 */           temp = EnterpriseUtil.decodeString(temp);
/* 4112 */           moNameList.add(temp.substring(temp.indexOf(":") + 1));
/*      */         }
/*      */         else
/*      */         {
/* 4116 */           moNameList.add(EnterpriseUtil.decodeString(moSet.getString("DISPLAYNAME")));
/*      */         }
/* 4118 */         resIDList.add(moSet.getString("RESOURCEID"));
/* 4119 */         attIDList.add(moSet.getString("ATTRIBUTEID"));
/*      */       }
/*      */       
/*      */ 
/* 4123 */       Hashtable threshold = new Hashtable();
/* 4124 */       int isUserThresh = Integer.parseInt(request.getParameter("userThresh"));
/* 4125 */       if (isUserThresh != 1)
/*      */       {
/* 4127 */         threshold = SegmentReportUtil.getAttribThresh(String.valueOf(resID), String.valueOf(attID), archivedTableName);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 4132 */         boolean isThreshEnabled = Boolean.valueOf(request.getParameter("isThresh")).booleanValue();
/* 4133 */         String thCndn1 = request.getParameter("threshC1");
/* 4134 */         String thValue1 = request.getParameter("threshV1");
/* 4135 */         String thCndn2 = request.getParameter("threshC2");
/* 4136 */         String thValue2 = request.getParameter("threshV2");
/* 4137 */         String thCndn3 = request.getParameter("threshC3");
/* 4138 */         String thValue3 = request.getParameter("threshV3");
/* 4139 */         threshold = SegmentReportUtil.getUserThresh(isThreshEnabled, thCndn1, thValue1, thCndn2, thValue2, thCndn3, thValue3);
/*      */       }
/* 4141 */       request.setAttribute("threshold", threshold);
/* 4142 */       ArrayList eumParentResid = SegmentReportUtil.getEumParentResIds();
/* 4143 */       for (int i = 0; i < resIDList.size(); i++)
/*      */       {
/* 4145 */         String rid = (String)resIDList.get(i);
/* 4146 */         if (!eumParentResid.contains(rid))
/*      */         {
/*      */ 
/*      */ 
/* 4150 */           Hashtable moattrib = new Hashtable();
/* 4151 */           ArrayList avgdata = SegmentReportUtil.getAttribAvgArchVal(period, Integer.parseInt((String)resIDList.get(i)), Integer.parseInt((String)attIDList.get(i)), archivedTableName);
/*      */           
/* 4153 */           ArrayList heatlist = SegmentReportUtil.applyThreshforHeat(avgdata, threshold);
/*      */           
/* 4155 */           moattrib.put("MO", (String)moNameList.get(i));
/* 4156 */           moattrib.put("Heat", heatlist);
/* 4157 */           moheatlist.add(moattrib);
/*      */         }
/*      */       }
/* 4160 */       Hashtable thresholdlegend = SegmentReportUtil.getThresholdLegendData(threshold);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4165 */       request.setAttribute("thresholdlegend", thresholdlegend);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4172 */       System.out.println("************ #HEAT CHART DATA# **************" + moheatlist);
/* 4173 */       request.setAttribute("heatdata", moheatlist);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4177 */       ex.printStackTrace();
/* 4178 */       log.fatal("History data Heat Chart Reports :  Exception ", ex);
/* 4179 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", ex.toString()));
/* 4180 */       saveMessages(request, messages);
/* 4181 */       return mapping.findForward("report.message");
/*      */     }
/* 4183 */     return mapping.findForward("historydata.heat");
/*      */   }
/*      */   
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType)
/*      */   {
/* 4188 */     ArrayList al = getAttributeCollection(resID, attID, resType, false);
/* 4189 */     return al;
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
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType, boolean getAllAttributes)
/*      */   {
/* 4202 */     ArrayList retlist = new ArrayList();
/*      */     try {
/* 4204 */       ReportUtilities repUtil = new ReportUtilities();
/* 4205 */       retlist = repUtil.getAttributeCollection(resID, attID, resType, getAllAttributes);
/*      */     }
/*      */     catch (Exception e) {
/* 4208 */       e.printStackTrace();
/*      */     }
/* 4210 */     return retlist;
/*      */   }
/*      */   
/*      */   private String getFormatedDate(java.util.Date date)
/*      */   {
/* 4215 */     String retDate = "";
/* 4216 */     String locale = System.getProperty("locale");
/* 4217 */     Locale currentLocale = new Locale("en", "US");
/*      */     try {
/* 4219 */       if ((locale != null) && (locale.indexOf("_") != -1))
/*      */       {
/* 4221 */         String[] lan_Loc = locale.trim().split("_");
/* 4222 */         currentLocale = new Locale(lan_Loc[0], lan_Loc[1]);
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4227 */       ee.printStackTrace();
/*      */     }
/* 4229 */     DateFormat dateFormatter = DateFormat.getDateTimeInstance(3, 3, currentLocale);
/* 4230 */     retDate = dateFormatter.format(date);
/* 4231 */     return retDate;
/*      */   }
/*      */   
/*      */   public ActionForward ischildAttribute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4236 */     String resid = request.getParameter("resourceid");
/* 4237 */     String resType = ReportUtilities.getResourceType(resid);
/* 4238 */     String attId = request.getParameter("attributeid");
/* 4239 */     ArrayList childAttributes = new ArrayList();
/* 4240 */     String isChildMonitorAttribute = "false";
/* 4241 */     PrintWriter pw = response.getWriter();
/*      */     
/* 4243 */     if ("VMWare ESX/ESXi".equals(resType)) {
/* 4244 */       childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("VirtualMachine");
/*      */     }
/*      */     
/* 4247 */     if ("Amazon".equals(resType)) {
/* 4248 */       childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("EC2Instance");
/* 4249 */       childAttributes.addAll((ArrayList)ReportUtil.getAttributeIDsforResType("RDSInstance"));
/*      */     }
/* 4251 */     if ("Hyper-V-Server".equals(resType)) {
/* 4252 */       childAttributes = (ArrayList)ReportUtil.getAttributeIDsforResType("HyperVVirtualMachine");
/*      */     }
/*      */     
/* 4255 */     if ((!childAttributes.isEmpty()) && (childAttributes.contains(attId))) {
/* 4256 */       isChildMonitorAttribute = "true";
/*      */     }
/*      */     
/* 4259 */     pw.print(isChildMonitorAttribute);
/*      */     
/* 4261 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward getChildGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 4265 */     String redirectUrl = "";
/*      */     try
/*      */     {
/* 4268 */       String resType = request.getParameter("resType");
/* 4269 */       String period = request.getParameter("period");
/* 4270 */       String resourceid = request.getParameter("resourceid");
/* 4271 */       String attributeid = request.getParameter("attributeid");
/*      */       
/*      */ 
/* 4274 */       redirectUrl = "/showReports.do?actionMethod=generateTrendReport&period=1&Report=true&resourceType=" + resType + "&workingdays=false&attribute=" + attributeid + "&resourceid=" + resourceid + "&ischildReport=true";
/*      */     }
/*      */     catch (Exception e) {
/* 4277 */       e.printStackTrace();
/*      */     }
/*      */     
/* 4280 */     return new ActionForward(redirectUrl, true);
/*      */   }
/*      */   
/*      */   public String getValueQuery(ArrayList dbtables, String colTime, String valueCol, String resourceId, int resID, String condition, String startTime, String endTime)
/*      */   {
/* 4285 */     Iterator it = dbtables.iterator();
/* 4286 */     StringBuffer queryList = new StringBuffer();
/* 4287 */     condition = condition + " and " + colTime + "<=" + endTime + " and " + colTime + ">=" + startTime;
/* 4288 */     while (it.hasNext()) {
/* 4289 */       String tableName = (String)it.next();
/* 4290 */       if (queryList.length() > 0) {
/* 4291 */         queryList.append(" union ");
/*      */       }
/* 4293 */       queryList.append("select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "=" + resID + condition);
/*      */     }
/*      */     
/* 4296 */     String valueQuery = queryList.toString() + " order by " + colTime + " desc";
/* 4297 */     return valueQuery;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\HistoryDataAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */