/*      */ package com.adventnet.appmanager.reporting.servlet;
/*      */ 
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.server.reporting.GenerateExcel;
/*      */ import com.adventnet.appmanager.server.reporting.GeneratePDF;
/*      */ import com.adventnet.appmanager.server.reporting.JReportsSpecUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ 
/*      */ public class AMPDFReportsServlet extends javax.servlet.http.HttpServlet
/*      */ {
/*   26 */   private javax.servlet.ServletContext servletContext = null;
/*   27 */   private ServletConfig config = null;
/*   28 */   private static Hashtable attributeHeadings = new Hashtable();
/*      */   
/*      */   static {
/*   31 */     attributeHeadings.put("availability", "Availability");
/*   32 */     attributeHeadings.put("health", "Health");
/*   33 */     attributeHeadings.put("responseTime", "Response Time");
/*   34 */     attributeHeadings.put("connectionTime", "Connection Time");
/*   35 */     attributeHeadings.put("cpuid", "CPU Utilisation");
/*   36 */     attributeHeadings.put("jvm", "JVM Usage");
/*   37 */     attributeHeadings.put("mem", "Memory Usage");
/*   38 */     attributeHeadings.put("disk", "Disk Utilisation");
/*   39 */     attributeHeadings.put("jdbc", "Connection Pool Usage");
/*   40 */     attributeHeadings.put("thread", "Thread Details");
/*   41 */     attributeHeadings.put("session", "Session Details");
/*   42 */     attributeHeadings.put("buffer", "Buffer Hit Ratio");
/*   43 */     attributeHeadings.put("cache", "Cache Hit Ratio");
/*      */   }
/*      */   
/*      */   public void init(ServletConfig sConfig)
/*      */     throws ServletException
/*      */   {
/*   49 */     super.init(sConfig);
/*   50 */     this.servletContext = sConfig.getServletContext();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*      */     throws ServletException, IOException
/*      */   {
/*   59 */     response.setContentType("application/pdf");
/*      */     
/*      */ 
/*   62 */     String headerval = request.getHeader("reportagent");
/*      */     
/*   64 */     if (request.isUserInRole("REPORTER"))
/*      */     {
/*   66 */       if ((headerval == null) || ((headerval != null) && (!headerval.equals(com.adventnet.appmanager.util.Constants.RY))))
/*      */       {
/*      */ 
/*   69 */         return;
/*      */       }
/*      */     }
/*      */     
/*   73 */     String reportType = (String)request.getAttribute("report-type-template");
/*   74 */     if (reportType == null)
/*      */     {
/*   76 */       reportType = (String)request.getSession().getAttribute("report-type-template");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*   83 */       if ("report.perf".equals(reportType)) {
/*   84 */         attributeReport(request, response);
/*   85 */       } else if ("report.ha.availability".equals(reportType)) {
/*   86 */         haAvailabilityReport(request, response);
/*   87 */       } else if ("report.ha.health".equals(reportType)) {
/*   88 */         haHealthReport(request, response);
/*   89 */       } else if ("report.availability".equals(reportType)) {
/*   90 */         availabilityReport(request, response);
/*   91 */       } else if ("report.health".equals(reportType)) {
/*   92 */         healthReport(request, response);
/*      */       }
/*   94 */       else if ("report.eventreport".equals(reportType)) {
/*   95 */         eventReport(request, response);
/*      */       }
/*   97 */       else if ("report.CustomAttribute".equals(reportType)) {
/*   98 */         camReport(request, response);
/*      */       }
/*  100 */       else if ("report.mttravail".equals(reportType)) {
/*  101 */         sevenThrityAvailReport(request, response);
/*      */       }
/*  103 */       else if ("report.summary".equals(reportType)) {
/*  104 */         summaryReport(request, response);
/*      */       }
/*  106 */       else if ("report.attribute".equals(reportType)) {
/*  107 */         sevenThirtyAttributeReport(request, response);
/*      */       }
/*  109 */       else if ("report.showpolldata.attribute".equals(reportType)) {
/*  110 */         showPollDataReport(request, response);
/*      */       }
/*  112 */       else if ("report.individualURLandComparision".equals(reportType)) {
/*  113 */         individualURLandComparisionReport(request, response);
/*      */       }
/*  115 */       else if ("report.eventlist".equals(reportType)) {
/*  116 */         eventListReport(request, response);
/*      */       }
/*  118 */       else if ("report.snapshot".equals(reportType)) {
/*  119 */         HASnapshotReport(request, response);
/*      */       }
/*  121 */       else if ("report.lic.snapshot".equals(reportType)) {
/*  122 */         LicUsageReport(request, response);
/*      */       }
/*  124 */       else if ("report.outagereport".equals(reportType)) {
/*  125 */         OutageReport(request, response);
/*      */       }
/*  127 */       else if ("report.availabilitysnapshot".equals(reportType)) {
/*  128 */         availabilitySnapshotReport(request, response);
/*      */       }
/*  130 */       else if ("report.availabilitytrendreport".equals(reportType)) {
/*  131 */         AvailabilityTrendReport(request, response);
/*      */       }
/*  133 */       else if ("report.availabilitytrenddowntimereport".equals(reportType)) {
/*  134 */         AvailabilityTrendDowntimeReport(request, response);
/*      */       }
/*  136 */       else if ("report.glancereport".equals(reportType)) {
/*  137 */         glanceReport(request, response);
/*      */       }
/*  139 */       else if ("report.configurationhistory".equals(reportType)) {
/*  140 */         configurationhistoryReport(request, response);
/*      */       }
/*  142 */       else if ("report.configurationglobalview".equals(reportType)) {
/*  143 */         configurationglobalviewReport(request, response);
/*      */       }
/*  145 */       else if ("report.eumsummary".equals(reportType))
/*      */       {
/*  147 */         eumSummaryReport(request, response);
/*      */       }
/*  149 */       else if ("report.undersized".equals(reportType))
/*      */       {
/*  151 */         undersizedReport(request, response);
/*      */       }
/*  153 */       else if ("report.undersizedindividual".equals(reportType))
/*      */       {
/*  155 */         undersizedReportIndividual(request, response);
/*      */       }
/*  157 */       else if ("report.mssqlperformancereport".equals(reportType)) {
/*  158 */         getMSSQLPerformanceReport(request, response);
/*      */       }
/*  160 */       else if ("report.mssqlgeneraldetails".equals(reportType)) {
/*  161 */         getMSSQLGeneralDetailsReport(request, response);
/*      */       }
/*  163 */       else if ("report.SLA.Application".equals(reportType)) {
/*  164 */         getSLAApplicationsAvailabilityReport(request, response);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  168 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*      */     throws ServletException, IOException
/*      */   {
/*  175 */     doPost(request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void attributeReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  185 */     String reportName = "AttributeReport_" + new java.sql.Date(System.currentTimeMillis());
/*  186 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  187 */     ReportUtilities rep = new ReportUtilities();
/*  188 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*  189 */     String unit = frm.getUnit();
/*      */     
/*  191 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*  192 */     String attribute = frm.getAttribute();
/*  193 */     String period = frm.getPeriod();
/*      */     
/*  195 */     String heading = "";
/*  196 */     String graphheading = "";
/*  197 */     heading = (String)request.getAttribute("heading");
/*  198 */     graphheading = (String)request.getAttribute("graphheading");
/*      */     
/*  200 */     if (graphheading == null) {
/*  201 */       graphheading = FormatUtil.getString("am.webclient.730attribute.graphheading");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  206 */     Map map = new HashMap();
/*  207 */     if ((period != null) && (period.equals("14"))) {
/*  208 */       periodvalue = "polled data";
/*  209 */       map.put("pdfdata", request.getAttribute("rawdata"));
/*  210 */       map.put("data", request.getAttribute("list"));
/*  211 */       map.put("Image", request.getAttribute("attributeImage"));
/*  212 */       map.put("resids", request.getAttribute("resids"));
/*  213 */       map.put("periodvalue", "periodvalue");
/*  214 */       map.put("period", request.getAttribute("period"));
/*  215 */       map.put("monitortype", " ");
/*  216 */       map.put("resourcename", " ");
/*  217 */       map.put("starttime", request.getAttribute("strTime"));
/*  218 */       map.put("endtime", request.getAttribute("endTime"));
/*  219 */       map.put("attID", "702");
/*  220 */       map.put("height", request.getAttribute("height"));
/*  221 */       map.put("heading", heading);
/*  222 */       map.put("graphheading", graphheading);
/*  223 */       map.put("Unit", unit);
/*  224 */       JReportsSpecUtil.individualURLandComparisionReport(map, response.getOutputStream());
/*      */     }
/*      */     else {
/*  227 */       map.put("data", request.getAttribute("data"));
/*  228 */       map.put("ReportName", reportName);
/*  229 */       map.put("Unit", unit);
/*  230 */       map.put("Image", request.getAttribute("attributeImage"));
/*  231 */       map.put("strTime", request.getAttribute("strTime"));
/*  232 */       map.put("endTime", request.getAttribute("endTime"));
/*  233 */       map.put("graphheading", graphheading);
/*  234 */       map.put("heading", heading);
/*  235 */       map.put("height", request.getAttribute("height"));
/*  236 */       JReportsSpecUtil.attributeReport(map, response.getOutputStream());
/*      */     }
/*      */     
/*      */ 
/*  240 */     System.out.println("Servlet Map : " + map);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void configurationhistoryReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  248 */     HttpSession session = request.getSession();
/*  249 */     String rType = (String)request.getAttribute("reportType");
/*  250 */     String heading = "Configuration Details";
/*  251 */     String mtype = (String)request.getAttribute("monitortype");
/*  252 */     String perval = (String)request.getAttribute("periodvalue");
/*      */     
/*  254 */     String reportName = "Configuration_History_Report_" + new java.sql.Date(System.currentTimeMillis());
/*  255 */     if ("excel".equals(rType)) {
/*  256 */       response.setContentType("application/vnd.ms-excel");
/*  257 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/*  259 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*  261 */     Map map = new HashMap();
/*  262 */     map.put("data", request.getAttribute("data"));
/*  263 */     map.put("periodvalue", perval);
/*  264 */     map.put("period", request.getAttribute("period"));
/*  265 */     map.put("monitortype", mtype);
/*  266 */     map.put("resourcename", request.getAttribute("resourcename"));
/*  267 */     map.put("starttime", request.getAttribute("starttime"));
/*  268 */     map.put("endtime", request.getAttribute("endtime"));
/*  269 */     map.put("bRuleName", request.getAttribute("bRuleName"));
/*      */     
/*  271 */     if ("excel".equals(rType)) {
/*  272 */       GenerateExcel gpf = new GenerateExcel();
/*  273 */       gpf.generateExcelReport(1, map, response);
/*      */     } else {
/*  275 */       GeneratePDF gpf = new GeneratePDF();
/*  276 */       gpf.generatePDFReport(8, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void configurationglobalviewReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  284 */     HttpSession session = request.getSession();
/*  285 */     String rType = (String)request.getAttribute("reportType");
/*  286 */     String reportName = "Configuration_Global_View_Report_" + new java.sql.Date(System.currentTimeMillis());
/*  287 */     if ("excel".equals(rType)) {
/*  288 */       response.setContentType("application/vnd.ms-excel");
/*  289 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/*  291 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*      */     
/*  294 */     Map map = new HashMap();
/*  295 */     map.put("outputData", request.getAttribute("outputData"));
/*  296 */     if ("excel".equals(rType)) {
/*  297 */       GenerateExcel gpf = new GenerateExcel();
/*  298 */       gpf.generateExcelReport(1, map, response);
/*      */     } else {
/*  300 */       GeneratePDF gpf = new GeneratePDF();
/*  301 */       gpf.generatePDFReport(9, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   private void haAvailabilityReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
/*  306 */     String reportName = "HAAvailabilityReport_" + new java.sql.Date(System.currentTimeMillis());
/*  307 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     
/*  309 */     Properties pros = (Properties)request.getAttribute("overAllAvailability");
/*  310 */     ReportUtilities rep = new ReportUtilities();
/*  311 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*      */     
/*  313 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*  314 */     String attribute = frm.getAttribute();
/*      */     
/*  316 */     String heading = "";
/*  317 */     heading = (String)request.getAttribute("heading");
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
/*  333 */     Map map = new HashMap();
/*      */     
/*  335 */     map.put("ReportName", reportName);
/*  336 */     map.put("strTime", request.getAttribute("strTime"));
/*  337 */     map.put("endTime", request.getAttribute("endTime"));
/*  338 */     map.put("data", request.getAttribute("data"));
/*  339 */     map.put("heading", heading);
/*  340 */     map.put("overAllAvailability", request.getAttribute("overAllAvailability"));
/*      */     
/*  342 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  344 */     JReportsSpecUtil.haAvailabilityReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */   private void haHealthReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  350 */     String reportName = "HAHealthReport_" + new java.sql.Date(System.currentTimeMillis());
/*  351 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  352 */     Properties pros = (Properties)request.getAttribute("overAllHealth");
/*  353 */     ReportUtilities rep = new ReportUtilities();
/*  354 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*      */     
/*  356 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*  357 */     String attribute = frm.getAttribute();
/*      */     
/*  359 */     String heading = "";
/*  360 */     heading = (String)request.getAttribute("heading");
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
/*  376 */     Map map = new HashMap();
/*      */     
/*  378 */     map.put("ReportName", reportName);
/*      */     
/*  380 */     map.put("strTime", request.getAttribute("strTime"));
/*  381 */     map.put("endTime", request.getAttribute("endTime"));
/*  382 */     map.put("data", request.getAttribute("data"));
/*  383 */     map.put("heading", heading);
/*  384 */     map.put("overAllHealth", request.getAttribute("overAllHealth"));
/*      */     
/*      */ 
/*  387 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  389 */     JReportsSpecUtil.haHealthReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void availabilityReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  397 */     String reportName = "AvailabilityReport_" + new java.sql.Date(System.currentTimeMillis());
/*  398 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  399 */     ReportUtilities rep = new ReportUtilities();
/*  400 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*      */     
/*  402 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*  403 */     String attribute = frm.getAttribute();
/*      */     
/*  405 */     String heading = "";
/*  406 */     heading = (String)request.getAttribute("heading");
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
/*  423 */     Map map = new HashMap();
/*      */     
/*  425 */     map.put("ReportName", reportName);
/*      */     
/*  427 */     map.put("strTime", request.getAttribute("strTime"));
/*  428 */     map.put("endTime", request.getAttribute("endTime"));
/*  429 */     map.put("data", request.getAttribute("data"));
/*  430 */     map.put("heading", heading);
/*      */     
/*  432 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  434 */     JReportsSpecUtil.availabilityReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */   private void healthReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  441 */     String reportName = "HealthReport_" + new java.sql.Date(System.currentTimeMillis());
/*  442 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  443 */     ReportUtilities rep = new ReportUtilities();
/*  444 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*      */     
/*      */ 
/*  447 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*      */     
/*      */ 
/*  450 */     String heading = "";
/*  451 */     heading = (String)request.getAttribute("heading");
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
/*  467 */     Map map = new HashMap();
/*      */     
/*  469 */     map.put("ReportName", reportName);
/*      */     
/*  471 */     map.put("strTime", request.getAttribute("strTime"));
/*  472 */     map.put("endTime", request.getAttribute("endTime"));
/*  473 */     map.put("data", request.getAttribute("data"));
/*  474 */     map.put("heading", heading);
/*      */     
/*  476 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  478 */     JReportsSpecUtil.healthReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */   private void eventReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  484 */     String reportName = "EventReport_" + new java.sql.Date(System.currentTimeMillis());
/*  485 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     
/*  487 */     Map map = new HashMap();
/*  488 */     ReportUtilities rep = new ReportUtilities();
/*  489 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*  490 */     String unit = frm.getUnit();
/*      */     
/*  492 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*      */     
/*  494 */     String heading = "";
/*  495 */     heading = (String)request.getAttribute("heading");
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
/*  511 */     map.put("ReportName", reportName);
/*      */     
/*  513 */     map.put("strTime", request.getAttribute("strTime"));
/*  514 */     map.put("endTime", request.getAttribute("endTime"));
/*  515 */     map.put("data", request.getAttribute("data"));
/*  516 */     map.put("heading", heading);
/*      */     
/*  518 */     map.put("alertSplitImage", request.getAttribute("alertSplitImage"));
/*  519 */     map.put("alertOccurImage", request.getAttribute("alertOccurImage"));
/*  520 */     map.put("CriticalEvents", request.getAttribute("CriticalEvents"));
/*  521 */     map.put("WarningEvents", request.getAttribute("WarningEvents"));
/*  522 */     map.put("ClearEvents", request.getAttribute("ClearEvents"));
/*      */     
/*      */ 
/*  525 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  527 */     JReportsSpecUtil.eventReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */   private void camReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  534 */     String reportName = "CustomAttribReport_" + new java.sql.Date(System.currentTimeMillis());
/*  535 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  536 */     ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/*  537 */     String unit = frm.getUnit();
/*  538 */     ReportUtilities rep = new ReportUtilities();
/*  539 */     String periodvalue = rep.getValueForPeriodForPDF(frm.getPeriod());
/*      */     
/*  541 */     String heading = "";
/*  542 */     heading = (String)request.getAttribute("heading");
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
/*  558 */     Map map = new HashMap();
/*      */     
/*  560 */     map.put("ReportName", reportName);
/*      */     
/*  562 */     map.put("strTime", request.getAttribute("strTime"));
/*  563 */     map.put("endTime", request.getAttribute("endTime"));
/*  564 */     map.put("data", request.getAttribute("data"));
/*  565 */     map.put("heading", heading);
/*      */     
/*  567 */     map.put("camattribImage", request.getAttribute("camattribImage"));
/*      */     
/*      */ 
/*  570 */     Map firstmap = new java.util.LinkedHashMap();
/*  571 */     List modata = (List)request.getAttribute("modata");
/*  572 */     List row = (List)modata.get(0);
/*      */     
/*      */ 
/*  575 */     map.put("Attribute Name", row.get(2).toString());
/*      */     
/*  577 */     firstmap.put("Attribute Name", row.get(2).toString());
/*  578 */     if (request.getAttribute("mBeanName") != null) {
/*  579 */       firstmap.put("MBean Name", request.getAttribute("mBeanName"));
/*      */     }
/*  581 */     firstmap.put("Agent", row.get(0).toString());
/*  582 */     firstmap.put("Port", row.get(3).toString());
/*  583 */     firstmap.put("ResourceType", row.get(1).toString());
/*  584 */     firstmap.put("Minimum Value", row.get(4).toString());
/*  585 */     firstmap.put("Maximum Value", row.get(5).toString());
/*  586 */     firstmap.put("Average Value", row.get(6).toString());
/*      */     
/*  588 */     map.put("TYPE-5-FirstTable", firstmap);
/*      */     
/*      */ 
/*  591 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  593 */     JReportsSpecUtil.camReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void sevenThrityAvailReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  601 */     String reportName = "MTTRReport_" + new java.sql.Date(System.currentTimeMillis());
/*  602 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  603 */     String perval = (String)request.getAttribute("periodvalue");
/*      */     
/*      */ 
/*  606 */     perval = FormatUtil.getString(perval);
/*  607 */     Map map = new HashMap();
/*      */     
/*  609 */     map.put("ReportName", reportName);
/*      */     
/*  611 */     System.out.println("Servlet Map : " + map);
/*      */     
/*      */ 
/*      */ 
/*  615 */     map.put("availabilityImage", request.getAttribute("availabilityImage"));
/*  616 */     map.put("summary", request.getAttribute("summary"));
/*  617 */     map.put("period", request.getParameter("period"));
/*      */     
/*  619 */     map.put("timeperiod", perval);
/*  620 */     map.put("resourcename", request.getAttribute("resourcename"));
/*  621 */     map.put("downtimesummary", request.getAttribute("downtimesummary"));
/*  622 */     map.put("downtimehistory", request.getAttribute("downtimehistory"));
/*      */     
/*  624 */     if (request.getAttribute("downtimereport") != null)
/*      */     {
/*  626 */       map.put("downtimeheading", FormatUtil.getString("am.webclient.730downtimetitleforPDF"));
/*      */     }
/*      */     else
/*      */     {
/*  630 */       map.put("downtimeheading", FormatUtil.getString("am.reporttab.availablityreport.text"));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  636 */     JReportsSpecUtil.sevenThrityAvailReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */   private void sevenThirtyAttributeReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  643 */     HttpSession session = request.getSession();
/*      */     
/*  645 */     String reportName = "SevenThirtyAttributeReport_" + new java.sql.Date(System.currentTimeMillis());
/*  646 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  647 */     String unit = (String)request.getAttribute("unit");
/*      */     
/*  649 */     Map map = new HashMap();
/*      */     
/*  651 */     map.put("ReportName", reportName);
/*      */     
/*  653 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  655 */     if (unit.equals(""))
/*      */     {
/*  657 */       map.put("unit", "");
/*      */     }
/*      */     else
/*      */     {
/*  661 */       map.put("unit", " in " + unit);
/*      */     }
/*  663 */     String mtype = (String)request.getAttribute("monitortype");
/*  664 */     String perval = (String)request.getAttribute("periodvalue");
/*      */     
/*  666 */     mtype = FormatUtil.getString(mtype);
/*  667 */     perval = FormatUtil.getString(perval);
/*      */     
/*  669 */     map.put("Image", request.getAttribute("attributeImage"));
/*  670 */     map.put("data", request.getAttribute("data"));
/*  671 */     map.put("rawdata", request.getAttribute("rawdata"));
/*  672 */     map.put("periodvalue", perval);
/*  673 */     map.put("period", request.getAttribute("period"));
/*      */     
/*  675 */     map.put("monitortype", mtype);
/*  676 */     String resName = FormatUtil.findAndReplaceAll((String)request.getAttribute("resourcename"), "\\", "\\\\");
/*  677 */     map.put("resourcename", resName);
/*      */     
/*  679 */     map.put("starttime", request.getAttribute("starttime"));
/*  680 */     map.put("endtime", request.getAttribute("endtime"));
/*  681 */     map.put("minavgval", request.getAttribute("minAvgValue"));
/*  682 */     map.put("maxavgval", request.getAttribute("maxAvgValue"));
/*  683 */     map.put("avgvalue", request.getAttribute("avgvalue"));
/*  684 */     map.put("bRuleName", request.getAttribute("bRuleName"));
/*      */     
/*  686 */     JReportsSpecUtil.sevenThirtyAttributeReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */   private void showPollDataReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  692 */     HttpSession session = request.getSession();
/*  693 */     String reportName = "AttributePolledDataReport_" + new java.sql.Date(System.currentTimeMillis());
/*  694 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  695 */     String unit = (String)request.getAttribute("unit");
/*      */     
/*  697 */     Map map = new HashMap();
/*  698 */     map.put("ReportName", reportName);
/*  699 */     System.out.println("Servlet Map : " + map);
/*  700 */     if (unit.equals(""))
/*      */     {
/*  702 */       map.put("unit", "");
/*      */     }
/*      */     else
/*      */     {
/*  706 */       map.put("unit", FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/*      */     }
/*      */     
/*  709 */     String mtype = (String)request.getAttribute("monitortype");
/*  710 */     String perval = (String)request.getAttribute("periodvalue");
/*      */     
/*  712 */     mtype = FormatUtil.getString(mtype);
/*  713 */     perval = FormatUtil.getString(perval);
/*  714 */     map.put("Image", request.getAttribute("attributeImage"));
/*  715 */     map.put("rawdata", request.getAttribute("rawdata"));
/*  716 */     map.put("periodvalue", perval);
/*  717 */     map.put("period", request.getAttribute("period"));
/*      */     
/*  719 */     map.put("monitortype", mtype);
/*  720 */     map.put("resourcename", request.getAttribute("resourcename"));
/*  721 */     String startTime = (String)request.getAttribute("start");
/*  722 */     String endTime = (String)request.getAttribute("end");
/*  723 */     map.put("starttime", Long.valueOf(startTime));
/*  724 */     map.put("endtime", Long.valueOf(endTime));
/*      */     
/*  726 */     map.put("bRuleName", request.getAttribute("bRuleName"));
/*      */     
/*  728 */     JReportsSpecUtil.showPollDataReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void individualURLandComparisionReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  737 */     HttpSession session = request.getSession();
/*      */     
/*  739 */     String reportName = (String)request.getAttribute("reportname") + "_" + new java.sql.Date(System.currentTimeMillis());
/*  740 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  741 */     String unit = (String)request.getAttribute("unit");
/*      */     
/*  743 */     Map map = new HashMap();
/*      */     
/*  745 */     map.put("ReportName", reportName);
/*      */     
/*  747 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  749 */     if (unit.equals(""))
/*      */     {
/*  751 */       map.put("unit", "");
/*      */     }
/*      */     else
/*      */     {
/*  755 */       map.put("unit", " in " + unit);
/*      */     }
/*      */     
/*  758 */     map.put("Image", request.getAttribute("attributeImage"));
/*  759 */     map.put("data", request.getAttribute("list"));
/*      */     
/*  761 */     map.put("pdfdata", request.getAttribute("pdfdata"));
/*  762 */     map.put("resids", request.getAttribute("resids"));
/*  763 */     map.put("periodvalue", request.getAttribute("periodvalue"));
/*  764 */     map.put("period", request.getAttribute("period"));
/*  765 */     map.put("monitortype", request.getAttribute("monitortype"));
/*  766 */     map.put("resourcename", request.getAttribute("resourcename"));
/*  767 */     map.put("starttime", request.getAttribute("starttime"));
/*  768 */     map.put("endtime", request.getAttribute("endtime"));
/*  769 */     map.put("attID", request.getAttribute("attID"));
/*  770 */     map.put("height", request.getAttribute("height"));
/*  771 */     map.put("bRuleName", request.getAttribute("bRuleName"));
/*      */     
/*  773 */     JReportsSpecUtil.individualURLandComparisionReport(map, response.getOutputStream());
/*      */   }
/*      */   
/*      */ 
/*      */   private void eventListReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  780 */     HttpSession session = request.getSession();
/*  781 */     String reportType = (String)request.getAttribute("sp-report-type");
/*      */     
/*  783 */     String reportName = "AlarmHistory_" + new java.sql.Date(System.currentTimeMillis());
/*  784 */     if ("pdf".equalsIgnoreCase(reportType)) {
/*  785 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     } else {
/*  787 */       response.setContentType("application/vnd.ms-excel");
/*  788 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     }
/*      */     
/*  791 */     String unit = (String)request.getAttribute("unit");
/*      */     
/*  793 */     Map map = new HashMap();
/*      */     
/*  795 */     map.put("ReportName", reportName);
/*      */     
/*  797 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  799 */     map.put("size", request.getAttribute("columnsize"));
/*  800 */     map.put("data", request.getAttribute("data"));
/*  801 */     if ("pdf".equalsIgnoreCase(reportType)) {
/*  802 */       GeneratePDF gpf = new GeneratePDF();
/*  803 */       gpf.generatePDFReport(2, map, response);
/*      */     } else {
/*  805 */       GenerateExcel gpf = new GenerateExcel();
/*  806 */       gpf.generateExcelReport(2, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void LicUsageReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  815 */     String heading = (String)request.getAttribute("heading");
/*  816 */     String reportName = (String)request.getAttribute("reportname");
/*  817 */     response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*  818 */     String licDuration = (String)request.getAttribute("licDuration");
/*      */     
/*  820 */     AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: Report Period " + licDuration);
/*      */     
/*  822 */     if ((licDuration != null) && ("lastMonth".equals(licDuration)))
/*      */     {
/*  824 */       Map map = new HashMap();
/*  825 */       map.put("ReportName", reportName);
/*  826 */       map.put("heading", heading);
/*  827 */       map.put("data", request.getAttribute("licUsage"));
/*      */       
/*  829 */       String month = (String)request.getAttribute("month");
/*  830 */       String year = (String)request.getAttribute("year");
/*  831 */       String userName = (String)request.getAttribute("userName");
/*  832 */       String companyName = (String)request.getAttribute("companyName");
/*  833 */       String emailID = (String)request.getAttribute("emailID");
/*      */       
/*  835 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: LastMonth ::: username " + userName + " companyName " + companyName + " month " + month + " year " + year + " emailID " + emailID);
/*      */       
/*  837 */       Properties licDisProps = (Properties)com.adventnet.appmanager.util.ReportUtil.getLicenseCategoryDisplayNameProps();
/*  838 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: licDisProps : " + licDisProps);
/*      */       
/*  840 */       map.put("companyName", companyName);
/*  841 */       map.put("userName", userName);
/*  842 */       map.put("month", month);
/*  843 */       map.put("year", year);
/*  844 */       map.put("emailID", emailID);
/*  845 */       map.put("licDuration", licDuration);
/*  846 */       map.put("licDisProps", licDisProps);
/*      */       
/*  848 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: Map To Be Passed to GeneratePDF : " + map);
/*  849 */       GeneratePDF gpf = new GeneratePDF();
/*  850 */       gpf.generatePDFReport(14, map, response);
/*      */     }
/*      */     else
/*      */     {
/*  854 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: Report Period " + licDuration);
/*  855 */       Map map = new HashMap();
/*  856 */       map.put("ReportName", reportName);
/*  857 */       map.put("heading", heading);
/*  858 */       map.put("data", request.getAttribute("licUsage"));
/*      */       
/*  860 */       String month = (String)request.getAttribute("month");
/*  861 */       String year = (String)request.getAttribute("year");
/*  862 */       String userName = (String)request.getAttribute("userName");
/*  863 */       String companyName = (String)request.getAttribute("companyName");
/*  864 */       String emailID = (String)request.getAttribute("emailID");
/*  865 */       String fromMonth = (String)request.getAttribute("fromMonth");
/*  866 */       String fromYear = (String)request.getAttribute("fromYear");
/*  867 */       String toMonth = (String)request.getAttribute("toMonth");
/*  868 */       String toYear = (String)request.getAttribute("toYear");
/*  869 */       Properties licDisProps = (Properties)com.adventnet.appmanager.util.ReportUtil.getLicenseCategoryDisplayNameProps();
/*  870 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: licDisProps : " + licDisProps);
/*      */       
/*  872 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: username " + userName + " companyName " + companyName + " month " + month + " year " + year + " emailID " + emailID);
/*  873 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: fromMonth " + fromMonth + " fromYear " + fromYear + " toMonth " + toMonth + " toYear " + toYear);
/*      */       
/*  875 */       map.put("companyName", companyName);
/*  876 */       map.put("userName", userName);
/*  877 */       map.put("fromMonth", fromMonth);
/*  878 */       map.put("fromYear", fromYear);
/*  879 */       map.put("emailID", emailID);
/*  880 */       map.put("toMonth", toMonth);
/*  881 */       map.put("toYear", toYear);
/*  882 */       map.put("licDuration", licDuration);
/*  883 */       map.put("licDisProps", licDisProps);
/*      */       
/*  885 */       AMLog.logLicUsage("[IT360 License Usage Calculation] AMPDFReportServlet ::: Map To Be Passed to GeneratePDF for " + licDuration + " is " + map);
/*  886 */       GeneratePDF gpf = new GeneratePDF();
/*  887 */       gpf.generatePDFReport(15, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   private void HASnapshotReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception
/*      */   {
/*  893 */     HttpSession session = request.getSession();
/*      */     
/*  895 */     String rType = (String)request.getAttribute("reportType");
/*  896 */     String heading = (String)request.getAttribute("heading");
/*  897 */     String reportName = "Availability_Health_Snapshot_Report_" + new java.sql.Date(System.currentTimeMillis());
/*  898 */     if ("excel".equals(rType)) {
/*  899 */       response.setContentType("application/vnd.ms-excel");
/*  900 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/*  902 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*      */     
/*  905 */     String withhost = (String)request.getAttribute("withhostname");
/*  906 */     if (withhost == null) {
/*  907 */       withhost = "-";
/*      */     }
/*  909 */     Map map = new HashMap();
/*      */     
/*  911 */     map.put("ReportName", reportName);
/*  912 */     map.put("heading", heading);
/*      */     
/*  914 */     map.put("withhost", withhost);
/*      */     
/*      */ 
/*      */ 
/*  918 */     map.put("data", request.getAttribute("data"));
/*  919 */     System.out.println("Servlet Map : " + map);
/*  920 */     if ("excel".equals(rType)) {
/*  921 */       GenerateExcel gpf = new GenerateExcel();
/*  922 */       gpf.generateExcelReport(1, map, response);
/*      */     } else {
/*  924 */       GeneratePDF gpf = new GeneratePDF();
/*  925 */       gpf.generatePDFReport(1, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void OutageReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*  936 */     HttpSession session = request.getSession();
/*  937 */     String timeperiod = (String)request.getAttribute("timeperiod");
/*  938 */     String custom = (String)request.getAttribute("CUSTOM");
/*  939 */     Properties Tprops = (Properties)request.getAttribute("timeprops");
/*  940 */     String rType = (String)request.getAttribute("reportType");
/*  941 */     String heading = "";
/*  942 */     String Bname = (String)request.getAttribute("BNAME");
/*  943 */     heading = (String)request.getAttribute("heading");
/*  944 */     String reportName = timeperiod + "OutageReport_" + new java.sql.Date(System.currentTimeMillis());
/*  945 */     if ("excel".equals(rType)) {
/*  946 */       response.setContentType("application/vnd.ms-excel");
/*  947 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/*  949 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*      */     
/*      */ 
/*  953 */     Map map = new HashMap();
/*      */     
/*  955 */     map.put("ReportName", reportName);
/*  956 */     map.put("heading", heading);
/*  957 */     map.put("Bname", Bname);
/*  958 */     map.put("custom", custom);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  963 */     map.put("data", request.getAttribute("mgvalues"));
/*  964 */     map.put("timeperiod", timeperiod);
/*  965 */     map.put("timeprops", Tprops);
/*  966 */     System.out.println("Servlet Map : " + map);
/*      */     
/*  968 */     if ("excel".equals(rType)) {
/*  969 */       GenerateExcel gpf = new GenerateExcel();
/*  970 */       gpf.generateExcelReport(3, map, response);
/*      */     } else {
/*  972 */       GeneratePDF gpf = new GeneratePDF();
/*  973 */       gpf.generatePDFReport(3, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   private void AvailabilityTrendDowntimeReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception
/*      */   {
/*      */     try
/*      */     {
/*  981 */       AMLog.debug("Inside the servlet class ===> : ");
/*  982 */       HttpSession session = request.getSession();
/*      */       
/*  984 */       String rType = (String)request.getAttribute("reportType");
/*      */       
/*  986 */       String timeperiod1 = (String)request.getAttribute("timeperiod");
/*  987 */       AMLog.debug("The timeperiod is ====> : " + timeperiod1);
/*  988 */       String timeperiod = "Daily";
/*  989 */       String Bname = (String)request.getAttribute("BNAME");
/*  990 */       ArrayList time = (ArrayList)request.getAttribute("displaytime");
/*  991 */       String heading = "";
/*      */       
/*  993 */       heading = (String)request.getAttribute("heading");
/*  994 */       if ("day".equals(timeperiod1)) {
/*  995 */         timeperiod = "Daily";
/*  996 */       } else if ("week".equals(timeperiod1)) {
/*  997 */         timeperiod = "Weekly";
/*  998 */       } else if ("month".equals(timeperiod1)) {
/*  999 */         timeperiod = "Monthly";
/*      */       }
/*      */       
/* 1002 */       String reportName = timeperiod + "AvailabilityTrendDowntimeReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1003 */       if ("excel".equals(rType)) {
/* 1004 */         response.setContentType("application/vnd.ms-excel");
/* 1005 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */       }
/*      */       else {
/* 1008 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */       }
/*      */       
/*      */ 
/* 1012 */       Map map = new HashMap();
/*      */       
/* 1014 */       map.put("ReportName", reportName);
/* 1015 */       map.put("heading", heading);
/* 1016 */       map.put("Bname", Bname);
/* 1017 */       map.put("displaytime", time);
/* 1018 */       map.put("targetAvail", request.getAttribute("targetAvail"));
/*      */       
/* 1020 */       map.put("data", request.getAttribute("allvalues"));
/* 1021 */       map.put("time", timeperiod1);
/* 1022 */       map.put("images", request.getAttribute("images"));
/* 1023 */       AMLog.debug("Servlet Map : " + map);
/*      */       
/* 1025 */       if ("excel".equals(rType)) {
/* 1026 */         AMLog.debug("Its inside the Excel servlet print loop :" + map);
/* 1027 */         GenerateExcel gpf = new GenerateExcel();
/* 1028 */         gpf.generateExcelReport(6, map, response);
/* 1029 */         AMLog.debug("AFTER calling the generateExcel method () : ");
/*      */       } else {
/* 1031 */         GeneratePDF gpf = new GeneratePDF();
/* 1032 */         gpf.generatePDFReport(10, map, response);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1036 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void availabilitySnapshotReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/* 1044 */     HttpSession session = request.getSession();
/* 1045 */     String timeperiod = (String)request.getAttribute("timeperiod");
/* 1046 */     Properties Tprops = (Properties)request.getAttribute("timeprops");
/* 1047 */     String rType = (String)request.getAttribute("reportType");
/* 1048 */     String Bname = (String)request.getAttribute("BNAME");
/*      */     
/* 1050 */     String heading = "";
/*      */     
/* 1052 */     heading = (String)request.getAttribute("heading");
/* 1053 */     String reportName = "AvailabilitySnapshotReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1054 */     if ("excel".equals(rType)) {
/* 1055 */       response.setContentType("application/vnd.ms-excel");
/* 1056 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/* 1058 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*      */     
/*      */ 
/* 1062 */     Map map = new HashMap();
/*      */     
/* 1064 */     map.put("ReportName", reportName);
/* 1065 */     map.put("heading", heading);
/* 1066 */     map.put("Bname", Bname);
/* 1067 */     System.out.println("Servlet Map : " + map);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1072 */     map.put("data", request.getAttribute("AllValues"));
/* 1073 */     map.put("timeprops", request.getAttribute("timeprops"));
/* 1074 */     if ("excel".equals(rType)) {
/* 1075 */       GenerateExcel gpf = new GenerateExcel();
/* 1076 */       gpf.generateExcelReport(5, map, response);
/*      */     } else {
/* 1078 */       GeneratePDF gpf = new GeneratePDF();
/* 1079 */       gpf.generatePDFReport(5, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void AvailabilityTrendReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/* 1088 */     HttpSession session = request.getSession();
/*      */     
/* 1090 */     String rType = (String)request.getAttribute("reportType");
/*      */     
/* 1092 */     String timeperiod1 = (String)request.getAttribute("timeperiod");
/* 1093 */     String timeperiod = "Daily";
/* 1094 */     String Bname = (String)request.getAttribute("BNAME");
/* 1095 */     ArrayList time = (ArrayList)request.getAttribute("displaytime");
/* 1096 */     String heading = "";
/*      */     
/* 1098 */     heading = (String)request.getAttribute("heading");
/* 1099 */     if ("day".equals(timeperiod1)) {
/* 1100 */       timeperiod = "Daily";
/* 1101 */     } else if ("week".equals(timeperiod1)) {
/* 1102 */       timeperiod = "Weekly";
/* 1103 */     } else if ("month".equals(timeperiod1)) {
/* 1104 */       timeperiod = "Monthly";
/*      */     }
/*      */     
/* 1107 */     String reportName = timeperiod + "AvailabilityTrendReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1108 */     if ("excel".equals(rType)) {
/* 1109 */       response.setContentType("application/vnd.ms-excel");
/* 1110 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/* 1112 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/*      */     
/*      */ 
/* 1116 */     Map map = new HashMap();
/*      */     
/* 1118 */     map.put("ReportName", reportName);
/* 1119 */     map.put("heading", heading);
/* 1120 */     map.put("Bname", Bname);
/* 1121 */     map.put("displaytime", time);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1126 */     map.put("data", request.getAttribute("allvalues"));
/* 1127 */     map.put("time", timeperiod1);
/* 1128 */     System.out.println("Servlet Map : " + map);
/*      */     
/* 1130 */     if ("excel".equals(rType)) {
/* 1131 */       GenerateExcel gpf = new GenerateExcel();
/* 1132 */       gpf.generateExcelReport(4, map, response);
/*      */     } else {
/* 1134 */       GeneratePDF gpf = new GeneratePDF();
/* 1135 */       gpf.generatePDFReport(4, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void summaryReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*      */     try
/*      */     {
/* 1147 */       HttpSession session = request.getSession();
/* 1148 */       ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/* 1149 */       ReportUtilities rep = new ReportUtilities();
/* 1150 */       String reportName = "SummaryReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1151 */       String heading = "";
/* 1152 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/* 1153 */       String periodvalue = rep.getValueForPeriodForPDF((String)request.getAttribute("period"));
/* 1154 */       heading = (String)request.getAttribute("heading");
/* 1155 */       String rType = (String)request.getAttribute("reportType");
/*      */       
/* 1157 */       Map map = new HashMap();
/* 1158 */       map.put("ReportName", reportName);
/* 1159 */       map.put("heading", heading);
/* 1160 */       map.put("images", request.getAttribute("images"));
/* 1161 */       map.put("attrnames", request.getAttribute("attrinames"));
/* 1162 */       if (request.getAttribute("GraphSize") != null) {
/* 1163 */         map.put("GraphSize", request.getAttribute("GraphSize"));
/*      */       }
/*      */       
/*      */ 
/* 1167 */       if ("excel".equals(rType)) {
/* 1168 */         GenerateExcel gpf = new GenerateExcel();
/* 1169 */         gpf.generateExcelReport(6, map, response);
/*      */       } else {
/* 1171 */         GeneratePDF gpf = new GeneratePDF();
/*      */         
/* 1173 */         gpf.generatePDFReport(6, map, response);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1178 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void glanceReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/*      */     try
/*      */     {
/* 1187 */       HttpSession session = request.getSession();
/* 1188 */       ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/* 1189 */       ReportUtilities rep = new ReportUtilities();
/* 1190 */       String reportName = "GlanceReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1191 */       String heading = "";
/* 1192 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/* 1193 */       String periodvalue = rep.getValueForPeriodForPDF((String)request.getAttribute("period"));
/* 1194 */       heading = (String)request.getAttribute("heading");
/* 1195 */       String rType = (String)request.getAttribute("reportType");
/*      */       
/* 1197 */       Map map = new HashMap();
/* 1198 */       map.put("ReportName", reportName);
/* 1199 */       map.put("heading", heading);
/* 1200 */       map.put("images", request.getAttribute("graphs"));
/* 1201 */       map.put("attrnames", request.getAttribute("attributename"));
/* 1202 */       map.put("secondLvlAttr", request.getAttribute("SECONDLEVELATTRIBS"));
/* 1203 */       if ("generateIndividualGlanceReport".equals((String)request.getAttribute("actionMethod")))
/*      */       {
/* 1205 */         map.put("isIndivGlance", Boolean.valueOf(true));
/*      */       }
/*      */       else
/*      */       {
/* 1209 */         map.put("isIndivGlance", Boolean.valueOf(false));
/*      */       }
/* 1211 */       if ((request.getAttribute("eumReport") != null) && (request.getAttribute("eumReport").equals("true")))
/*      */       {
/* 1213 */         map.put("eumDownTimeHeader", FormatUtil.getString("am.webclient.eum.outagereport"));
/* 1214 */         map.put("eumDownTimeTable", request.getAttribute("downTimeParentList"));
/* 1215 */         if (request.getAttribute("secondLvlAttr") != null)
/*      */         {
/* 1217 */           map.put("secondLvlAttr", request.getAttribute("secondLvlAttr"));
/* 1218 */           map.put("isEUMwithSLA", Boolean.valueOf(true));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1223 */         map.put("isEUMwithSLA", Boolean.valueOf(false));
/*      */       }
/*      */       
/*      */ 
/* 1227 */       GeneratePDF gpf = new GeneratePDF();
/* 1228 */       gpf.generatePDFReport(7, map, response);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1233 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void eumSummaryReport(HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1242 */     HttpSession session = request.getSession();
/* 1243 */     String rType = (String)request.getAttribute("reportType");
/* 1244 */     String heading1 = (String)request.getAttribute("heading1");
/* 1245 */     String heading2 = (String)request.getAttribute("heading2");
/* 1246 */     String reportName = "EUMSummary_Report_" + new java.sql.Date(System.currentTimeMillis());
/* 1247 */     Map map = new HashMap();
/* 1248 */     map.put("ReportName", reportName);
/* 1249 */     map.put("heading1", heading1);
/* 1250 */     map.put("heading2", heading2);
/* 1251 */     map.put("agentIdVSname", request.getAttribute("agentIdVSname"));
/* 1252 */     map.put("agentVSresTypeVSAvg", request.getAttribute("agentVSresTypeVSAvg"));
/* 1253 */     map.put("resTypeList", request.getAttribute("resTypeList"));
/* 1254 */     map.put("downTimeList", request.getAttribute("downTimeList"));
/*      */     
/* 1256 */     if ("pdf".equals(rType))
/*      */     {
/* 1258 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/* 1259 */       GeneratePDF gpf = new GeneratePDF();
/* 1260 */       gpf.generatePDFReport(11, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   private void undersizedReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
/*      */     try {
/* 1266 */       HttpSession session = request.getSession();
/* 1267 */       ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/* 1268 */       ReportUtilities rep = new ReportUtilities();
/* 1269 */       String reportName = "CustomSizedReports_" + new java.sql.Date(System.currentTimeMillis());
/* 1270 */       String heading = "";
/* 1271 */       String rType = (String)request.getAttribute("reportType");
/*      */       
/* 1273 */       if ("excel".equals(rType)) {
/* 1274 */         response.setContentType("application/vnd.ms-excel");
/* 1275 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */       }
/*      */       else
/*      */       {
/* 1279 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */       }
/* 1281 */       String periodvalue = rep.getValueForPeriodForPDF((String)request.getAttribute("period"));
/* 1282 */       String headingPeriod = (String)request.getAttribute("headingPeriod");
/* 1283 */       heading = (String)request.getAttribute("heading");
/*      */       
/*      */ 
/*      */ 
/* 1287 */       Map map = new HashMap();
/* 1288 */       map.put("ReportName", reportName);
/* 1289 */       map.put("heading", heading);
/*      */       
/*      */ 
/* 1292 */       String restypeName = (String)request.getAttribute("resourcetypename");
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
/* 1314 */       map.put("timeTitle", request.getAttribute("configUtilizationTimeText"));
/* 1315 */       map.put("categoryTitle", request.getAttribute("categoryTitle"));
/*      */       
/* 1317 */       map.put("attrids", request.getAttribute("AttributeIDList"));
/* 1318 */       map.put("attrnames", request.getAttribute("attributeNames"));
/* 1319 */       map.put("resultmap", request.getAttribute("resultmap"));
/* 1320 */       map.put("reportprops", request.getAttribute("reportProps"));
/* 1321 */       map.put("parentHostPresent", request.getAttribute("parentHostPresent"));
/* 1322 */       map.put("resourcetypename", request.getAttribute("resourcetypename"));
/* 1323 */       map.put("allservers", request.getAttribute("allservers"));
/* 1324 */       map.put("imagepath", request.getAttribute("imagepath"));
/* 1325 */       map.put("systime", new java.util.Date());
/* 1326 */       map.put("period", headingPeriod);
/* 1327 */       map.put("bRuleName", request.getAttribute("bRuleName"));
/* 1328 */       map.put("mgName", request.getAttribute("mgName"));
/* 1329 */       map.put("configurationMap", request.getAttribute("configurationMap"));
/*      */       
/*      */ 
/*      */ 
/* 1333 */       if ("excel".equals(rType)) {
/* 1334 */         GenerateExcel gpf = new GenerateExcel();
/* 1335 */         gpf.generateExcelReport(7, map, response);
/*      */       }
/*      */       else
/*      */       {
/* 1339 */         GeneratePDF gpf = new GeneratePDF();
/* 1340 */         gpf.generatePDFReport(12, map, response);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1346 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void undersizedReportIndividual(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception
/*      */   {
/*      */     try
/*      */     {
/* 1354 */       ReportForm frm = (ReportForm)request.getAttribute("ReportForm");
/* 1355 */       ReportUtilities rep = new ReportUtilities();
/* 1356 */       String reportName = "CustomSizedReports_" + new java.sql.Date(System.currentTimeMillis());
/* 1357 */       String heading = "";
/* 1358 */       String rType = (String)request.getAttribute("reportType");
/* 1359 */       if ("excel".equals(rType)) {
/* 1360 */         response.setContentType("application/vnd.ms-excel");
/* 1361 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */       }
/*      */       else
/*      */       {
/* 1365 */         response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */       }
/*      */       
/* 1368 */       String headingPeriod = (String)request.getAttribute("headingPeriod");
/* 1369 */       heading = (String)request.getAttribute("heading");
/* 1370 */       String category = (String)request.getAttribute("categoryTitle");
/* 1371 */       AMLog.debug("categoryTitle111==" + category);
/*      */       
/* 1373 */       Map map = new HashMap();
/* 1374 */       map.put("ReportName", reportName);
/* 1375 */       map.put("heading", heading);
/* 1376 */       String timeTitle = "Time Used";
/*      */       
/*      */ 
/* 1379 */       String restypeName = (String)request.getAttribute("resourcetypename");
/*      */       
/*      */ 
/* 1382 */       map.put("timeTitle", request.getAttribute("configUtilizationTimeText"));
/* 1383 */       map.put("calculatedTime", request.getAttribute("calculatedTime"));
/* 1384 */       map.put("categoryTitle", request.getAttribute("categoryTitle"));
/*      */       
/* 1386 */       map.put("attrids", request.getAttribute("AttributeIDList"));
/* 1387 */       map.put("attrnames", request.getAttribute("attributeNames"));
/* 1388 */       map.put("resultmap", request.getAttribute("outermap"));
/* 1389 */       map.put("reportprops", request.getAttribute("reportProps"));
/* 1390 */       map.put("configUtilizationTimeText", request.getAttribute("configUtilizationTimeText"));
/*      */       
/* 1392 */       map.put("resourcetypename", request.getAttribute("resourcetypename"));
/* 1393 */       map.put("systime", new java.util.Date());
/* 1394 */       map.put("period", headingPeriod);
/*      */       
/*      */ 
/* 1397 */       if ("excel".equals(rType)) {
/* 1398 */         GenerateExcel gpf = new GenerateExcel();
/* 1399 */         gpf.generateExcelReport(8, map, response);
/*      */       }
/*      */       else
/*      */       {
/* 1403 */         GeneratePDF gpf = new GeneratePDF();
/* 1404 */         gpf.generatePDFReport(13, map, response);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1409 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void getMSSQLPerformanceReport(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, Exception
/*      */   {
/* 1416 */     String reportName = "PerformanceReportForMSSQL_" + new java.sql.Date(System.currentTimeMillis());
/* 1417 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1418 */       response.setContentType("application/vnd.ms-excel");
/* 1419 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/* 1421 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/* 1423 */     String heading = "";
/* 1424 */     heading = (String)request.getAttribute("heading");
/*      */     
/* 1426 */     Map map = new HashMap();
/* 1427 */     map.put("ReportName", reportName);
/* 1428 */     map.put("data", request.getAttribute("data"));
/* 1429 */     map.put("columnheadings", request.getAttribute("columnheadings"));
/* 1430 */     map.put("columns", request.getAttribute("columns"));
/* 1431 */     map.put("cellwidth", request.getAttribute("cellwidth"));
/* 1432 */     map.put("heading", heading);
/* 1433 */     map.put("image", request.getAttribute("image"));
/* 1434 */     map.put("image1", request.getAttribute("image1"));
/* 1435 */     map.put("topqrycnt", request.getAttribute("topqrycnt"));
/* 1436 */     if (request.getAttribute("image_heading") != null) {
/* 1437 */       map.put("image_heading", request.getAttribute("image_heading"));
/*      */     }
/* 1439 */     if (request.getAttribute("image1_heading") != null) {
/* 1440 */       map.put("image1_heading", request.getAttribute("image1_heading"));
/*      */     }
/* 1442 */     AMLog.debug("Servlet Map : " + map);
/*      */     
/* 1444 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1445 */       GenerateExcel gef = new GenerateExcel();
/* 1446 */       gef.generateExcelReport(18, map, response);
/*      */     } else {
/* 1448 */       GeneratePDF gpf = new GeneratePDF();
/* 1449 */       gpf.generatePDFReport(18, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   private void getMSSQLGeneralDetailsReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
/* 1454 */     AMLog.debug("AMPDFReportServlet => calling getMSSQLGeneralDetailsReport()");
/* 1455 */     String reportName = "MSSQLGeneralDetailsReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1456 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1457 */       response.setContentType("application/vnd.ms-excel");
/* 1458 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/* 1460 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/* 1462 */     String heading = "";
/* 1463 */     heading = (String)request.getAttribute("heading");
/*      */     
/* 1465 */     Map map = new HashMap();
/* 1466 */     map.put("ReportName", reportName);
/* 1467 */     map.put("data", request.getAttribute("data"));
/* 1468 */     map.put("selectedColumns", request.getAttribute("selectedColumns"));
/* 1469 */     map.put("columnHeadings", request.getAttribute("columnHeadings"));
/* 1470 */     map.put("heading", heading);
/* 1471 */     map.put("data", request.getAttribute("data"));
/* 1472 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1473 */       GenerateExcel gef = new GenerateExcel();
/* 1474 */       gef.generateExcelReport(15, map, response);
/*      */     } else {
/* 1476 */       GeneratePDF gpf = new GeneratePDF();
/* 1477 */       gpf.generatePDFReport(15, map, response);
/*      */     }
/*      */   }
/*      */   
/* 1481 */   private void getSLAApplicationsAvailabilityReport(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception { AMLog.debug("AMPDFReportServlet => calling getSLAApplicationsAvailabilityReport()");
/* 1482 */     String reportName = "SLAApplicationsAvailailityReport_" + new java.sql.Date(System.currentTimeMillis());
/* 1483 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1484 */       response.setContentType("application/vnd.ms-excel");
/* 1485 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".xls");
/*      */     } else {
/* 1487 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*      */     }
/* 1489 */     Map map = new HashMap();
/* 1490 */     map.put("ReportName", reportName);
/* 1491 */     map.putAll((HashMap)request.getAttribute("data"));
/* 1492 */     if ("excel".equals(request.getAttribute("reportType"))) {
/* 1493 */       GenerateExcel gef = new GenerateExcel();
/* 1494 */       gef.generateExcelReport(17, map, response);
/*      */     } else {
/* 1496 */       GeneratePDF gpf = new GeneratePDF();
/*      */       
/* 1498 */       gpf.generatePDFReport(17, map, response);
/*      */     }
/*      */   }
/*      */   
/*      */   public void destroy() {}
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\servlet\AMPDFReportsServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */