/*      */ package com.adventnet.appmanager.reporting.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import com.adventnet.appmanager.util.VMReportUtilities;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ public class CustomReportActions
/*      */   extends DispatchAction
/*      */ {
/*      */   private Boolean customisedReports;
/*      */   
/*      */   public CustomReportActions()
/*      */   {
/*   72 */     this.customisedReports = Boolean.valueOf(false);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward generateIndividualReportCapacityPlanning(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   79 */     String mapforward = "report.undersized.individual";
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*   84 */       String reportID = request.getParameter("report");
/*   85 */       String resID = request.getParameter("resourceid");
/*   86 */       String period = request.getParameter("period");
/*   87 */       Boolean allServers = Boolean.valueOf(false);
/*   88 */       ReportForm rf = (ReportForm)form;
/*   89 */       String resourceType = rf.getResourceType();
/*   90 */       String attributeIDs = request.getParameter("attribute");
/*   91 */       ArrayList attributeList = ReportDataUtilities.returnTokenizedList(attributeIDs, ",");
/*   92 */       ArrayList allattributeList = AMCacheHandler.allServerListCapacityPlanning;
/*   93 */       if ((resourceType.equals("servers")) || (resourceType.equalsIgnoreCase("windows")) || (resourceType.equalsIgnoreCase("linux")) || (allattributeList.contains(attributeList.get(0))))
/*      */       {
/*      */ 
/*   96 */         allServers = Boolean.valueOf(true);
/*      */       }
/*      */       
/*   99 */       String reportType = request.getParameter("reporttype");
/*      */       
/*  101 */       String category = request.getParameter("reportmethod");
/*      */       
/*      */ 
/*      */ 
/*  105 */       Collections.sort(attributeList);
/*      */       
/*      */ 
/*  108 */       String resource = ReportUtilities.getResourceType(resID);
/*  109 */       String resourceTypeName = FormatUtil.getString("am.reporttab.capacityplanning.server");
/*  110 */       if (!allServers.booleanValue())
/*      */       {
/*  112 */         resourceTypeName = VMReportUtilities.getDisplayName(resource);
/*      */       }
/*      */       
/*  115 */       Properties virtualServerAttributesProps = AMCacheHandler.virtualServerAttributesProps;
/*  116 */       AMLog.debug("virtualServerAttributesProps==" + virtualServerAttributesProps);
/*      */       try
/*      */       {
/*  119 */         String image = "";
/*  120 */         if (resourceTypeName.contains("Hyper-V Virtual Machine"))
/*      */         {
/*  122 */           if (virtualServerAttributesProps.getProperty("VirtualMachine") != null)
/*      */           {
/*  124 */             image = virtualServerAttributesProps.getProperty("VirtualMachine");
/*  125 */             AMLog.debug("inside right" + image);
/*      */           }
/*      */           else
/*      */           {
/*  129 */             image = ReportDataUtilities.getImagePath("VirtualMachine");
/*      */           }
/*      */         }
/*  132 */         else if (resourceTypeName.contains("Virtual Machine"))
/*      */         {
/*  134 */           if (virtualServerAttributesProps.getProperty("VirtualMachine") != null)
/*      */           {
/*  136 */             image = virtualServerAttributesProps.getProperty("VirtualMachine");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  141 */             image = ReportDataUtilities.getImagePath("VirtualMachine");
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  147 */         else if (virtualServerAttributesProps.getProperty(resource) != null)
/*      */         {
/*  149 */           image = virtualServerAttributesProps.getProperty(resource);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  154 */           image = ReportDataUtilities.getImagePath(resource);
/*      */         }
/*      */         
/*      */ 
/*  158 */         AMLog.debug("imagePath1==" + image);
/*  159 */         int index = image.indexOf("@");
/*  160 */         if (index != -1)
/*      */         {
/*  162 */           image = image.substring(0, index);
/*      */         }
/*      */         
/*  165 */         if ((image != null) && (!image.equalsIgnoreCase("")))
/*      */         {
/*  167 */           request.setAttribute("imagepath", image);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  174 */         e.printStackTrace();
/*      */       }
/*      */       
/*  177 */       String heading = FormatUtil.getString("am.vmreports.capacityplanning.indivdual.heading");
/*  178 */       String timeType = FormatUtil.getString("am.webclient.common.undersized.oversizedtime.text");
/*  179 */       if ((category != null) && (category.indexOf("Oversized") != -1))
/*      */       {
/*  181 */         timeType = FormatUtil.getString("am.reporttab.oversizedvm.timeused.text");
/*      */       }
/*  183 */       else if ((category != null) && (category.indexOf("Idle") != -1))
/*      */       {
/*  185 */         timeType = FormatUtil.getString("am.reporttab.idle.usedtime");
/*      */       }
/*      */       
/*  188 */       request.setAttribute("heading", heading);
/*  189 */       request.setAttribute("timeType", timeType);
/*  190 */       Properties memMapping = new Properties();
/*      */       
/*  192 */       if (allServers.booleanValue())
/*      */       {
/*  194 */         memMapping = VMReportUtilities.getCpuMemMapping();
/*      */       }
/*      */       
/*  197 */       if (!allServers.booleanValue())
/*      */       {
/*  199 */         request.setAttribute("AttributeIDList", attributeList);
/*      */       }
/*      */       else
/*      */       {
/*  203 */         ArrayList newList = VMReportUtilities.returnRelativeList(attributeList, memMapping);
/*  204 */         request.setAttribute("AttributeIDList", newList);
/*      */       }
/*      */       
/*  207 */       String businessPeriod = request.getParameter("businessPeriod");
/*  208 */       Hashtable bHourDetail = new Hashtable();
/*      */       
/*      */ 
/*  211 */       Properties attributeProps = ReportDataUtilities.returnAttributeProps(attributeIDs);
/*  212 */       Properties attributeNames = new Properties();
/*  213 */       if (request.getAttribute("headingPeriod") == null)
/*      */       {
/*  215 */         request.setAttribute("headingPeriod", rf.getReportPeriod());
/*      */       }
/*      */       
/*  218 */       String exampleAtt = "";
/*  219 */       for (int l = 0; l < attributeList.size(); l++)
/*      */       {
/*  221 */         String attribute = (String)attributeList.get(l);
/*      */         
/*  223 */         String attributeDisplayName = ReportDataUtilities.getAttributeDisplayName(attribute);
/*  224 */         String units = ReportDataUtilities.returnUnits(attribute);
/*  225 */         if (allServers.booleanValue())
/*      */         {
/*  227 */           exampleAtt = VMReportUtilities.returnExampleAttID(memMapping, attribute);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  232 */           exampleAtt = attribute;
/*      */         }
/*  234 */         attributeNames.setProperty(exampleAtt, FormatUtil.getString(attributeDisplayName));
/*      */         
/*      */ 
/*  237 */         if (units != null)
/*      */         {
/*  239 */           attributeNames.setProperty(exampleAtt + "_units", units);
/*      */         }
/*      */         else
/*      */         {
/*  243 */           attributeNames.setProperty(exampleAtt + "_units", "");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  248 */       request.setAttribute("attributeNames", attributeNames);
/*  249 */       String dataTable1 = attributeProps.getProperty((String)attributeList.get(0));
/*  250 */       String dailyRptCondition = "";
/*  251 */       Boolean forbHr = Boolean.valueOf(false);
/*  252 */       long[] timeStamps; long[] timeStamps; if ((rf.getStartDate().equals("")) || (rf.getEndDate().equals("")))
/*      */       {
/*  254 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*      */       }
/*      */       else
/*      */       {
/*  258 */         rf.setPeriod("4");
/*      */         try {
/*  260 */           timeStamps = ReportUtilities.parseTimeAndDate(rf.getStartDate(), rf.getEndDate());
/*      */         }
/*      */         catch (IllegalArgumentException iae)
/*      */         {
/*  264 */           String errMsg = iae.getMessage();
/*      */           
/*      */ 
/*      */ 
/*  268 */           request.setAttribute("heading", "0");
/*  269 */           request.setAttribute("strTime", "0");
/*  270 */           return mapping.findForward("report.message");
/*      */         }
/*      */       }
/*  273 */       String businessRule = rf.getBusinessPeriod();
/*      */       
/*  275 */       if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */       {
/*  277 */         bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/*      */         
/*  279 */         forbHr = Boolean.valueOf(true);
/*  280 */         request.setAttribute("businessPeriod", businessRule);
/*  281 */         rf.setBusinessPeriod(businessRule);
/*  282 */         request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/*      */       }
/*      */       else
/*      */       {
/*  286 */         request.setAttribute("businessPeriod", "oni");
/*  287 */         rf.setBusinessPeriod(businessRule);
/*      */       }
/*  289 */       long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], dataTable1);
/*      */       
/*  291 */       request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/*  292 */       request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*  293 */       request.setAttribute("strTime", new Date(timeStamps[0]));
/*  294 */       request.setAttribute("endTime", new Date(timeStamps[1]));
/*      */       
/*  296 */       dailyRptCondition = " arch1.DURATION=1 and arch1.ARCHIVEDTIME >={0} and arch1.ARCHIVEDTIME <={1}";
/*  297 */       dailyRptCondition = DBQueryUtil.getDBQuery(dailyRptCondition, new String[] { "" + dailyRptTimestamp[0], "" + dailyRptTimestamp[1] });
/*      */       
/*      */ 
/*  300 */       if (dailyRptTimestamp[2] > 0L)
/*      */       {
/*      */ 
/*      */ 
/*  304 */         dailyRptCondition = " arch1.DURATION=2 and arch1.ARCHIVEDTIME >={0} and arch1.ARCHIVEDTIME <={1}  ";
/*      */         
/*  306 */         dailyRptCondition = DBQueryUtil.getDBQuery(dailyRptCondition, new String[] { "" + dailyRptTimestamp[2], "" + dailyRptTimestamp[1] });
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  311 */       if (forbHr.booleanValue())
/*      */       {
/*  313 */         dailyRptCondition = ReportDataUtilities.returnDailyCondtionForBHours(dailyRptTimestamp, bHourDetail);
/*  314 */         AMLog.debug("daily RPT Condition" + dailyRptCondition);
/*      */       }
/*      */       
/*  317 */       Properties reportProps = VMReportUtilities.getDatafromDB(reportID);
/*  318 */       request.setAttribute("reportProps", reportProps);
/*  319 */       String reportMethod = rf.getReportmethod();
/*      */       
/*      */ 
/*  322 */       VMReportUtilities.setConfigurationText(request, reportProps, resourceTypeName, reportMethod);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  328 */       String firstCondn = "select m1.DISPLAYNAME as name,arch1.ARCHIVEDTIME,m1.RESOURCENAME,";
/*  329 */       String selectionList = "";
/*  330 */       String fromSet = "";
/*  331 */       String fourthCondition = "";
/*      */       
/*  333 */       String thirdCondition = "";
/*      */       
/*  335 */       for (int i = 0; i < attributeList.size(); i++)
/*      */       {
/*  337 */         int j = i + 1;
/*  338 */         if (i == 0)
/*      */         {
/*  340 */           if (attributeProps.getProperty("" + attributeList.get(i)) != null)
/*      */           {
/*  342 */             dataTable1 = attributeProps.getProperty("" + attributeList.get(i));
/*      */           }
/*  344 */           selectionList = "(arch" + j + ".total)/(arch" + j + ".totalcount) as avg" + j + "";
/*  345 */           fromSet = " from  AM_ManagedObject m1 inner join " + dataTable1 + "  arch1 on m1.RESOURCEID=arch1.RESID";
/*      */           
/*  347 */           thirdCondition = "arch" + j + ".ATTRIBUTEID=" + attributeList.get(i) + " and arch" + j + ".RESID=" + resID;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  353 */           if (attributeProps.getProperty("" + attributeList.get(i)) != null)
/*      */           {
/*  355 */             dataTable1 = attributeProps.getProperty("" + attributeList.get(i));
/*      */           }
/*      */           
/*  358 */           selectionList = selectionList + ",(arch" + j + ".total)/(arch" + j + ".totalcount) as avg" + j + "";
/*  359 */           fromSet = fromSet + " inner join " + dataTable1 + " arch" + j + " on arch" + i + ".ARCHIVEDTIME =arch" + j + ".ARCHIVEDTIME ";
/*  360 */           thirdCondition = thirdCondition + " and arch" + j + ".ATTRIBUTEID=" + attributeList.get(j - 1);
/*  361 */           fourthCondition = fourthCondition + " and arch" + j + ".RESID=arch" + i + ".RESID and arch" + j + ".DURATION =arch" + i + ".DURATION";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  368 */       String query = firstCondn + "" + selectionList + "" + fromSet + " and " + dailyRptCondition + " " + fourthCondition + "  and " + thirdCondition + " order by arch1.ARCHIVEDTIME";
/*  369 */       ResultSet set = null;
/*  370 */       AMLog.debug("query before==" + query);
/*      */       
/*      */       try
/*      */       {
/*  374 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*  375 */         set = AMConnectionPool.executeQueryStmt(query);
/*  376 */         LinkedHashMap outerMap = new LinkedHashMap();
/*  377 */         String resName = "Monitor";
/*  378 */         while (set.next())
/*      */         {
/*  380 */           if (resName.equalsIgnoreCase("monitor"))
/*      */           {
/*  382 */             resName = set.getString(1);
/*      */             
/*  384 */             if ((resName != null) && (!resName.equalsIgnoreCase("null")))
/*      */             {
/*  386 */               request.setAttribute("resourcetypename", FormatUtil.getString(resName));
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  391 */               resName = set.getString(1);
/*  392 */               request.setAttribute("resourcetypename", FormatUtil.getString(set.getString(3)));
/*      */             }
/*      */           }
/*      */           
/*  396 */           HashMap innerProps = new HashMap();
/*  397 */           String archivedTime = set.getString(2);
/*  398 */           for (int l = 0; l < attributeList.size(); l++)
/*      */           {
/*  400 */             String attID = (String)attributeList.get(l);
/*  401 */             String value = set.getString(4 + l);
/*  402 */             innerProps.put(attID, value);
/*  403 */             innerProps.put("date", new Date(Long.parseLong(archivedTime)));
/*      */           }
/*  405 */           outerMap.put(archivedTime, innerProps);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  411 */         Iterator iter = outerMap.keySet().iterator();
/*  412 */         Long totalCount = Long.valueOf(outerMap.keySet().size());
/*  413 */         Long count = Long.valueOf(0L);
/*      */         
/*      */         String hourlyColor;
/*  416 */         for (int i = 0; iter.hasNext(); i++)
/*      */         {
/*  418 */           String key = (String)iter.next();
/*  419 */           HashMap innerProps = (HashMap)outerMap.get(key);
/*  420 */           ArrayList colors = new ArrayList();
/*  421 */           String combination = reportProps.getProperty("combination");
/*  422 */           for (int l = 0; l < attributeList.size(); l++)
/*      */           {
/*  424 */             String attID = (String)attributeList.get(l);
/*  425 */             Double value = Double.valueOf(Double.parseDouble((String)innerProps.get(attID)));
/*  426 */             NumberFormat nd = NumberFormat.getInstance();
/*  427 */             nd.setMinimumFractionDigits(1);
/*  428 */             nd.setMaximumFractionDigits(2);
/*  429 */             String hourlyAvg = nd.format(value);
/*  430 */             if (allServers.booleanValue())
/*      */             {
/*  432 */               attID = VMReportUtilities.returnExampleAttID(memMapping, attID);
/*      */             }
/*      */             
/*  435 */             AMLog.debug("attid--" + attID + "reportProps==" + reportProps);
/*  436 */             ArrayList list = VMReportUtilities.returnThresoldCondition(reportProps, attID);
/*  437 */             String condition = (String)list.get(1);
/*  438 */             Double timeThreshold = Double.valueOf(Double.parseDouble((String)list.get(0)));
/*  439 */             String color = VMReportUtilities.returnColor(value, condition, timeThreshold.doubleValue());
/*  440 */             colors.add(color);
/*  441 */             String hourlyAvgCSV = hourlyAvg.replace(",", "");
/*  442 */             innerProps.put(attID + "_value", "" + hourlyAvg);
/*  443 */             innerProps.put(attID + "_csv", "" + hourlyAvgCSV);
/*  444 */             innerProps.put(attID + "_color", color);
/*  445 */             AMLog.debug("list111===" + list);
/*      */           }
/*  447 */           hourlyColor = VMReportUtilities.returnHourlyColor(colors, combination);
/*  448 */           if (hourlyColor.equalsIgnoreCase("red"))
/*      */           {
/*  450 */             count = Long.valueOf(count.longValue() + 1L);
/*      */           }
/*  452 */           innerProps.put("unicolor", hourlyColor);
/*      */         }
/*      */         
/*  455 */         Long timeUtilization = Long.valueOf(0L);
/*  456 */         if (count.longValue() != 0L)
/*      */         {
/*  458 */           timeUtilization = Long.valueOf(count.longValue() * 100L / totalCount.longValue());
/*      */         }
/*      */         
/*      */ 
/*  462 */         String timeCondition = reportProps.getProperty("timeCondition");
/*  463 */         int threshold = Integer.parseInt(reportProps.getProperty("timethreshold"));
/*  464 */         ArrayList calculatedTime = new ArrayList();
/*  465 */         String color = VMReportUtilities.returnColor(timeUtilization, timeCondition, threshold);
/*  466 */         if (color.equalsIgnoreCase("red"))
/*      */         {
/*  468 */           calculatedTime.add("red");
/*      */         }
/*      */         else
/*      */         {
/*  472 */           calculatedTime.add("green");
/*      */         }
/*  474 */         calculatedTime.add(resName);
/*  475 */         calculatedTime.add("" + count);
/*  476 */         calculatedTime.add("" + totalCount);
/*  477 */         calculatedTime.add("" + timeUtilization);
/*  478 */         VMReportUtilities.setIndividualMonitorText(request, reportMethod, calculatedTime);
/*  479 */         request.setAttribute("outermap", outerMap);
/*      */         
/*  481 */         request.setAttribute("calculatedTime", calculatedTime);
/*  482 */         reportType = rf.getReporttype();
/*      */         
/*      */ 
/*  485 */         if ((reportType != null) && (reportType.equalsIgnoreCase("pdf")))
/*      */         {
/*      */ 
/*  488 */           request.setAttribute("report-type-template", "report.undersizedindividual");
/*  489 */           request.setAttribute("reportType", "pdf");
/*      */           
/*  491 */           return mapping.findForward("report.undersized.pdf");
/*      */         }
/*      */         
/*  494 */         if ((reportType != null) && (reportType.equalsIgnoreCase("csv")))
/*      */         {
/*      */ 
/*      */ 
/*  498 */           request.setAttribute("reportType", "pdf");
/*      */           
/*  500 */           return mapping.findForward("report.undersized.individualcsv");
/*      */         }
/*      */         
/*  503 */         if ((reportType != null) && (reportType.equals("excel"))) {
/*  504 */           request.setAttribute("report-type-template", "report.undersizedindividual");
/*  505 */           request.setAttribute("reportType", "excel");
/*      */           
/*      */ 
/*  508 */           return mapping.findForward("report.undersized.pdf");
/*      */         }
/*  510 */         if (outerMap.size() == 0)
/*      */         {
/*  512 */           ActionMessages messages = new ActionMessages();
/*  513 */           AMLog.debug("saved message for individual custom VM report--" + FormatUtil.getString("customreport.nodata.time"));
/*  514 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("customreport.nodata.time")));
/*  515 */           saveMessages(request, messages);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  521 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  525 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  532 */       exc.printStackTrace();
/*      */     }
/*  534 */     return mapping.findForward(mapforward);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward generateUnderSizedMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  542 */     response.setContentType("text/html;charset=UTF-8");
/*      */     
/*  544 */     int length = 2;
/*      */     
/*  546 */     String mapforward = "report.undersized";
/*  547 */     ActionMessages messages = new ActionMessages();
/*  548 */     Properties reportProps = null;
/*  549 */     Properties attributeProps = new Properties();
/*  550 */     LinkedHashMap sorted_map1 = new LinkedHashMap();
/*  551 */     ReportForm rf = (ReportForm)form;
/*  552 */     String customfield = rf.getCustomfield();
/*  553 */     String customValue = rf.getCustomFieldValue();
/*  554 */     String customFieldDescription = "-";
/*  555 */     if ((customfield.equals("true")) && (customValue.indexOf("$") != -1)) {
/*  556 */       HashMap<String, String> customDetail = MyFields.reportValues(customValue);
/*      */       
/*  558 */       customFieldDescription = FormatUtil.getString("am.capacityplanning.customfield.description", new String[] { (String)customDetail.get("label"), (String)customDetail.get("value") });
/*      */     }
/*      */     
/*  561 */     request.setAttribute("customFieldDescription", customFieldDescription);
/*  562 */     AMLog.debug(customfield + "==customField");
/*  563 */     AMLog.debug(customValue + "==customValue");
/*  564 */     String resourceTypeName = FormatUtil.getString("am.capacityplanning.servers");
/*  565 */     String resource = FormatUtil.getString("am.capacityplanning.servers");
/*      */     
/*  567 */     String reportType = request.getParameter("reporttype");
/*  568 */     this.customisedReports = Boolean.valueOf(false);
/*  569 */     Hashtable bHourDetail = new Hashtable();
/*  570 */     boolean forbHr = false;
/*  571 */     ResultSet set = null;
/*  572 */     ArrayList bh = new ArrayList();
/*  573 */     Boolean haidIsPresent = Boolean.valueOf(false);
/*  574 */     String residsString = "";
/*  575 */     String operatorCondition = "";
/*  576 */     String resourceType = request.getParameter("resourceType");
/*  577 */     Boolean showAllMonitors = Boolean.valueOf(true);
/*  578 */     String optionalFilter = request.getParameter("capacityPlanningOptions");
/*  579 */     String mg = rf.getMondaycapacity();
/*  580 */     if (mg != null)
/*      */     {
/*  582 */       mg = mg.replaceAll("(^')|('$)|(^&#39;)|(&#39;$)", "");
/*      */     }
/*      */     
/*      */ 
/*  586 */     if (!mg.equalsIgnoreCase(FormatUtil.getString("am.webclient.reports.select.mg.text")))
/*      */     {
/*  588 */       request.setAttribute("mgName", mg);
/*      */     }
/*      */     
/*  591 */     if ((optionalFilter != null) && (optionalFilter.equalsIgnoreCase("1")))
/*      */     {
/*  593 */       showAllMonitors = Boolean.valueOf(false);
/*      */     }
/*  595 */     request.setAttribute("showAllMonitors", showAllMonitors);
/*  596 */     AMLog.debug("showAllMonitors" + showAllMonitors);
/*      */     
/*  598 */     if ((Constants.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition())) {
/*  599 */       if (Constants.isUserResourceEnabled()) {
/*  600 */         String loginUserid = Constants.getLoginUserid(request);
/*  601 */         operatorCondition = " and m1.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID = " + loginUserid + ")";
/*      */       } else {
/*  603 */         Vector usersResourceids = new Vector();
/*  604 */         String owner = request.getRemoteUser();
/*  605 */         usersResourceids = ReportUtilities.getResourceIdentity(owner, request);
/*  606 */         operatorCondition = " and " + ReportUtilities.getCondition("m1.RESOURCEID", usersResourceids);
/*      */       }
/*      */     }
/*  609 */     bh = VMReportUtilities.returnBusinessHours();
/*      */     
/*  611 */     ((ReportForm)form).setBusinessrules(bh);
/*      */     
/*      */ 
/*  614 */     String haid = rf.getHaid();
/*      */     
/*      */ 
/*  617 */     String businessRule = rf.getBusinessPeriod();
/*  618 */     if ((businessRule != null) && (!businessRule.equals("oni")))
/*      */     {
/*  620 */       bHourDetail = SegmentReportUtil.getBusinessRule(businessRule);
/*      */       
/*  622 */       forbHr = true;
/*  623 */       request.setAttribute("businessPeriod", businessRule);
/*  624 */       rf.setBusinessPeriod(businessRule);
/*  625 */       request.setAttribute("bRuleName", ReportUtilities.getBusinessRuleName(businessRule));
/*      */     }
/*      */     else
/*      */     {
/*  629 */       request.setAttribute("businessPeriod", "oni");
/*  630 */       rf.setBusinessPeriod(businessRule);
/*      */     }
/*  632 */     String headingString = "am.webclient.common.undersized.text";
/*  633 */     String categoryTitle = FormatUtil.getString("am.reporttab.undersized");
/*  634 */     if (rf.getReportPeriod() != null)
/*      */     {
/*      */ 
/*  637 */       headingString = FormatUtil.getString("am.webclient.common.undersized.text") + " " + rf.getReportPeriod();
/*      */     }
/*  639 */     if (request.getAttribute("tempheading") != null)
/*      */     {
/*  641 */       String tempheading = (String)request.getAttribute("tempheading");
/*  642 */       headingString = tempheading + " " + headingString;
/*      */     }
/*  644 */     if (request.getAttribute("headingPeriod") == null)
/*      */     {
/*  646 */       request.setAttribute("headingPeriod", rf.getReportPeriod());
/*      */     }
/*  648 */     Properties countProps = new Properties();
/*  649 */     String bName = ReportUtilities.getBusinessRuleName(businessRule);
/*      */     
/*  651 */     request.setAttribute("BNAME", bName);
/*      */     
/*      */     try
/*      */     {
/*  655 */       Properties attributeNames = new Properties();
/*      */       
/*  657 */       ResultSet set1 = null;
/*  658 */       AMConnectionPool cp = new AMConnectionPool();
/*  659 */       String attribute = request.getParameter("attribute");
/*  660 */       String allAttributes1 = VMReportUtilities.returnCPUStringSequence();
/*      */       
/*  662 */       Boolean serverAttributes = Boolean.valueOf(false);
/*  663 */       ArrayList attributeListServers = new ArrayList();
/*      */       
/*  665 */       if ((resourceType.equals("servers")) || (resourceType.equalsIgnoreCase("windows")) || (resourceType.equalsIgnoreCase("linux")) || (attribute.equalsIgnoreCase("1,2")) || (attribute.equalsIgnoreCase("2,1")) || (attribute.equals("1")) || (attribute.equalsIgnoreCase("2")))
/*      */       {
/*      */ 
/*  668 */         serverAttributes = Boolean.valueOf(true);
/*  669 */         attributeListServers.add("1");
/*  670 */         attributeListServers.add("2");
/*      */       }
/*      */       
/*  673 */       request.setAttribute("attributeListServers", attributeListServers);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  679 */       String reportMethod = "generateUnderSizedMonitors";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  684 */       String timeTitle = "am.reporttab.oversizedvm.timeused.text";
/*      */       
/*      */ 
/*  687 */       ArrayList outerArrayList = new ArrayList();
/*      */       
/*  689 */       if (request.getParameter("reportmethod") != null)
/*      */       {
/*  691 */         reportMethod = request.getParameter("reportmethod");
/*      */         
/*  693 */         rf.setReportmethod(reportMethod);
/*  694 */         request.setAttribute("heading", headingString);
/*      */       }
/*  696 */       if ((reportType != null) && (reportType.equalsIgnoreCase("pdf")))
/*      */       {
/*  698 */         reportMethod = rf.getReportmethod();
/*  699 */         mg = request.getParameter("mondaycapacicty");
/*  700 */         attribute = request.getParameter("attribute");
/*      */       }
/*  702 */       StringTokenizer attributeids = new StringTokenizer(attribute, ",");
/*  703 */       String reload = "false";
/*      */       
/*  705 */       String reportName = "";
/*      */       
/*  707 */       if (request.getParameter("reload") != null)
/*      */       {
/*  709 */         reload = request.getParameter("reload");
/*      */         
/*      */ 
/*  712 */         if (reload.equals("true"))
/*      */         {
/*  714 */           reportName = rf.getReportname();
/*      */           
/*      */ 
/*      */ 
/*  718 */           mapforward = "report.undersizedjsp";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  724 */       if (reportMethod.equalsIgnoreCase("generateOverSizedMonitors"))
/*      */       {
/*  726 */         categoryTitle = FormatUtil.getString("am.reporttab.oversized");
/*  727 */         timeTitle = "am.reporttab.oversizedvm.timeused.text";
/*  728 */         mapforward = "report.oversized";
/*      */         
/*  730 */         if (reload.equals("true"))
/*      */         {
/*  732 */           mapforward = "report.oversizedjsp";
/*      */         }
/*  734 */         headingString = "am.webclient.common.oversized.text";
/*  735 */         if (rf.getPeriod() != null)
/*      */         {
/*  737 */           headingString = FormatUtil.getString(headingString) + " " + rf.getReportPeriod();
/*      */         }
/*  739 */         if (request.getAttribute("tempheading") != null)
/*      */         {
/*  741 */           String tempheading = (String)request.getAttribute("tempheading");
/*  742 */           headingString = tempheading + " " + headingString;
/*      */         }
/*      */       }
/*      */       
/*  746 */       if (reportMethod.equalsIgnoreCase("generateIdleMonitors"))
/*      */       {
/*  748 */         timeTitle = "am.reporttab.idle.usedtime";
/*  749 */         categoryTitle = FormatUtil.getString("am.reporttab.idle");
/*  750 */         mapforward = "report.idle";
/*      */         
/*      */ 
/*  753 */         if (reload.equals("true"))
/*      */         {
/*  755 */           mapforward = "report.idlejsp";
/*      */         }
/*  757 */         headingString = "am.webclient.common.idle.text";
/*  758 */         if (rf.getPeriod() != null)
/*      */         {
/*  760 */           headingString = FormatUtil.getString(headingString) + " " + rf.getReportPeriod();
/*      */         }
/*  762 */         if (request.getAttribute("tempheading") != null)
/*      */         {
/*  764 */           String tempheading = (String)request.getAttribute("tempheading");
/*  765 */           headingString = tempheading + " " + headingString;
/*      */         }
/*      */       }
/*  768 */       if ("pdf".equalsIgnoreCase(reportType))
/*      */       {
/*  770 */         mapforward = "report.undersized.pdf";
/*      */       }
/*      */       
/*  773 */       request.setAttribute("reload", reload);
/*  774 */       ArrayList attributeIDS = new ArrayList();
/*  775 */       for (int i = 0; attributeids.hasMoreTokens(); i++)
/*      */       {
/*  777 */         String attributeID = attributeids.nextToken();
/*  778 */         attributeIDS.add(attributeID);
/*      */       }
/*      */       
/*  781 */       ArrayList allAttributesList = VMReportUtilities.getServerAllCPUMemoryList(attributeIDS);
/*      */       
/*      */ 
/*  784 */       Collections.sort(attributeIDS);
/*  785 */       request.setAttribute("AttributeIDList", attributeIDS);
/*  786 */       request.setAttribute("categoryTitle", categoryTitle);
/*  787 */       request.setAttribute("allservers", serverAttributes);
/*      */       
/*  789 */       if ("pdf".equalsIgnoreCase(reportType))
/*      */       {
/*  791 */         reportName = request.getParameter("report");
/*      */       }
/*  793 */       else if (request.getParameter("reportName") != null)
/*      */       {
/*      */ 
/*  796 */         reportName = request.getParameter("reportName");
/*  797 */         AMLog.debug("reportName1---" + reportName);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  806 */       int reportid = -1;
/*      */       
/*  808 */       if (!reportName.trim().equals(""))
/*      */       {
/*  810 */         reportid = VMReportUtilities.isReportIDpresent(reportName);
/*      */       }
/*      */       else
/*      */       {
/*  814 */         reportName = rf.getReportname();
/*  815 */         reportid = VMReportUtilities.isReportIDpresent(reportName);
/*      */       }
/*  817 */       rf.setReportname(reportName);
/*  818 */       if (reportid != -1)
/*      */       {
/*      */ 
/*      */ 
/*  822 */         this.customisedReports = Boolean.valueOf(true);
/*      */       }
/*      */       
/*  825 */       ArrayList unConfiguredAttributes = new ArrayList();
/*  826 */       if (!this.customisedReports.booleanValue())
/*      */       {
/*  828 */         if (!serverAttributes.booleanValue())
/*      */         {
/*  830 */           unConfiguredAttributes = VMReportUtilities.getUnconfiguredAttributeList(attributeIDS, this.customisedReports);
/*      */         }
/*  832 */         attributeIDS = VMReportUtilities.returnRealAttributeList(attributeIDS, unConfiguredAttributes);
/*  833 */         attributeIDS = insertReportinDB(reportName, attributeIDS, request, reportMethod, serverAttributes);
/*  834 */         reportid = VMReportUtilities.isReportIDpresent(reportName);
/*  835 */         this.customisedReports = Boolean.valueOf(true);
/*  836 */         reportProps = VMReportUtilities.getDatafromDB("" + reportid, attributeIDS);
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*  844 */         if (reload.equals("true"))
/*      */         {
/*  846 */           AMLog.debug("update on reload");
/*  847 */           updateReportsinDB(reportid, attributeIDS, request);
/*      */         }
/*  849 */         AMLog.debug("reportid==" + reportid + "attributeIDS==" + attributeIDS);
/*  850 */         reportProps = VMReportUtilities.getDatafromDB("" + reportid, attributeIDS);
/*  851 */         for (int i = 0; i < attributeIDS.size(); i++)
/*      */         {
/*  853 */           String unconfiguredKey = "" + attributeIDS.get(i) + "_unconfigured";
/*  854 */           if ((reportProps.getProperty(unconfiguredKey) != null) && (reportProps.getProperty(unconfiguredKey).equalsIgnoreCase("true")))
/*      */           {
/*  856 */             unConfiguredAttributes.add("" + attributeIDS.get(i));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  862 */       VMReportUtilities.setCombination(request, reportProps);
/*  863 */       attributeIDS = VMReportUtilities.returnRealAttributeList(attributeIDS, unConfiguredAttributes);
/*      */       
/*  865 */       if (!serverAttributes.booleanValue())
/*      */       {
/*      */ 
/*  868 */         for (int l = 0; l < attributeIDS.size(); l++)
/*      */         {
/*  870 */           String attributeDisplayName = ReportDataUtilities.getAttributeDisplayName("" + attributeIDS.get(l));
/*  871 */           attributeNames.setProperty("" + attributeIDS.get(l), FormatUtil.getString(attributeDisplayName));
/*  872 */           String units = ReportDataUtilities.returnUnits("" + attributeIDS.get(l));
/*      */           
/*  874 */           if (units != null)
/*      */           {
/*  876 */             attributeNames.setProperty(attributeIDS.get(l) + "_units", units);
/*      */           }
/*      */           else
/*      */           {
/*  880 */             attributeNames.setProperty(attributeIDS.get(l) + "_units", "");
/*      */           }
/*  882 */           String disableText = FormatUtil.getString("am.capacityplanning.link.disable.attribute", new String[] { FormatUtil.getString(attributeDisplayName) });
/*      */           
/*  884 */           attributeNames.setProperty(attributeIDS.get(l) + "_disableText", disableText);
/*  885 */           String disableAlert = FormatUtil.getString("am.capacityplanning.jsalert.confirm.disableattribute", new String[] { attributeDisplayName });
/*  886 */           attributeNames.setProperty(attributeIDS.get(l) + "_disableAlert", disableAlert);
/*      */         }
/*      */         
/*  889 */         for (int l = 0; l < unConfiguredAttributes.size(); l++)
/*      */         {
/*  891 */           String attributeDisplayName = ReportDataUtilities.getAttributeDisplayName("" + unConfiguredAttributes.get(l));
/*  892 */           attributeNames.setProperty("" + unConfiguredAttributes.get(l), FormatUtil.getString(attributeDisplayName));
/*  893 */           String units = ReportDataUtilities.returnUnits("" + unConfiguredAttributes.get(l));
/*      */           
/*  895 */           if (units != null)
/*      */           {
/*  897 */             attributeNames.setProperty(unConfiguredAttributes.get(l) + "_units", units);
/*      */           }
/*      */           else
/*      */           {
/*  901 */             attributeNames.setProperty(unConfiguredAttributes.get(l) + "_units", "");
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  906 */         attributeNames.setProperty("1", FormatUtil.getString("CPU Utilization"));
/*  907 */         attributeNames.setProperty("2", FormatUtil.getString("Memory Utilization"));
/*  908 */         attributeNames.setProperty("1_units", "%");
/*  909 */         attributeNames.setProperty("2_units", "%");
/*  910 */         String disableText = FormatUtil.getString("am.capacityplanning.link.disable.attribute", new String[] { FormatUtil.getString("am.capacityplanning.server.cpu") });
/*  911 */         attributeNames.setProperty("1_disableText", disableText);
/*  912 */         String disableAlert = FormatUtil.getString("am.capacityplanning.jsalert.confirm.disableattribute", new String[] { FormatUtil.getString("am.capacityplanning.server.cpu") });
/*  913 */         attributeNames.setProperty("1_disableAlert", disableAlert);
/*  914 */         disableText = FormatUtil.getString("am.capacityplanning.link.disable.attribute", new String[] { FormatUtil.getString("am.capacityplanning.server.memory") });
/*  915 */         attributeNames.setProperty("2_disableText", disableText);
/*  916 */         disableAlert = FormatUtil.getString("am.capacityplanning.jsalert.confirm.disableattribute", new String[] { FormatUtil.getString("am.capacityplanning.server.memory") });
/*  917 */         attributeNames.setProperty("2_disableAlert", disableAlert);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  922 */       request.setAttribute("attributeNames", attributeNames);
/*  923 */       if (attributeIDS != null)
/*      */       {
/*  925 */         Collections.sort(attributeIDS);
/*      */       }
/*      */       
/*  928 */       request.setAttribute("AttributeIDList", attributeIDS);
/*      */       
/*  930 */       String attributes = "";
/*  931 */       for (int l = 0; l < attributeIDS.size(); l++)
/*      */       {
/*  933 */         if (attributes.equals(""))
/*      */         {
/*  935 */           attributes = attributes + attributeIDS.get(l);
/*      */         }
/*      */         else
/*      */         {
/*  939 */           attributes = attributes + "," + attributeIDS.get(l);
/*      */         }
/*      */       }
/*  942 */       request.setAttribute("unConfiguredAttributes", unConfiguredAttributes);
/*  943 */       String period = rf.getPeriod();
/*  944 */       AMLog.debug("period before " + period);
/*  945 */       if ("true".equals(reload))
/*      */       {
/*  947 */         period = request.getParameter("period");
/*  948 */         AMLog.debug("period set " + period);
/*  949 */         rf.setPeriod(period);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  954 */       request.setAttribute("period", period);
/*      */       try
/*      */       {
/*  957 */         String unconfiguredIds = VMReportUtilities.convertToStringSequence(unConfiguredAttributes);
/*  958 */         String configuredIds = VMReportUtilities.convertToStringSequence(attributeIDS);
/*  959 */         request.setAttribute("unconfiguredIds", unconfiguredIds);
/*  960 */         request.setAttribute("configuredIds", configuredIds);
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  965 */         e.printStackTrace();
/*      */       }
/*  967 */       request.setAttribute("unConfiguredAttributes", unConfiguredAttributes);
/*      */       
/*  969 */       if ((attributeIDS.isEmpty()) && (!unConfiguredAttributes.isEmpty()))
/*      */       {
/*  971 */         if (!serverAttributes.booleanValue())
/*      */         {
/*  973 */           resource = ReportUtilities.getResourceTypeForAttribute("" + unConfiguredAttributes.get(0));
/*  974 */           resourceTypeName = VMReportUtilities.getDisplayName(resource);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  979 */           resourceTypeName = FormatUtil.getString("am.reporttab.capacityplanning.server");
/*      */         }
/*      */         
/*      */ 
/*  983 */         VMReportUtilities.setConfigurationText(request, reportProps, resourceTypeName, reportMethod);
/*  984 */         request.setAttribute("heading", FormatUtil.getString(headingString, new String[] { FormatUtil.getString(resourceTypeName) }));
/*  985 */         request.setAttribute("resourcetypename", FormatUtil.getString(resourceTypeName));
/*      */         
/*  987 */         if (!serverAttributes.booleanValue())
/*      */         {
/*  989 */           rf.setResourceType(FormatUtil.getString(resourceTypeName));
/*      */         }
/*      */         else
/*      */         {
/*  993 */           rf.setResourceType(resourceType);
/*      */         }
/*      */         
/*  996 */         request.setAttribute("reportProps", reportProps);
/*  997 */         request.setAttribute("hidedata", "true");
/*  998 */         AMLog.debug(reportName + " is not unconfigured ");
/*      */         
/* 1000 */         return mapping.findForward(mapforward);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1005 */       request.setAttribute("hidedata", "false");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1012 */       HashMap resultMap = new HashMap();
/* 1013 */       ArrayList totalList = new ArrayList();
/*      */       
/*      */ 
/* 1016 */       ArrayList underUsedVMs = new ArrayList();
/*      */       
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1022 */         String query = "Select  ATTRIBUTEID ,ARCHIVEDDATA_TABLENAME  from  AM_ATTRIBUTES_EXT WHERE ATTRIBUTEID in (" + attributes + ")";
/* 1023 */         if (serverAttributes.booleanValue())
/*      */         {
/* 1025 */           query = "Select  ATTRIBUTEID ,ARCHIVEDDATA_TABLENAME  from  AM_ATTRIBUTES_EXT WHERE ATTRIBUTEID in (" + allAttributes1 + ")";
/*      */         }
/*      */         
/* 1028 */         AMLog.debug("datatable query===" + query);
/* 1029 */         set1 = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1031 */         while (set1.next())
/*      */         {
/*      */ 
/* 1034 */           String attId = set1.getString(1);
/* 1035 */           String datatable = set1.getString(2);
/* 1036 */           attributeProps.setProperty(attId, datatable);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1043 */         e.printStackTrace();
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 1048 */         AMConnectionPool.closeStatement(set1);
/*      */       }
/* 1050 */       AMLog.debug(attributeProps);
/* 1051 */       long[] timeStamps; long[] timeStamps; if ((rf.getStartDate().equals("")) || (rf.getEndDate().equals("")))
/*      */       {
/* 1053 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*      */       }
/*      */       else
/*      */       {
/* 1057 */         rf.setPeriod("4");
/*      */         try {
/* 1059 */           timeStamps = ReportUtilities.parseTimeAndDate(rf.getStartDate(), rf.getEndDate());
/*      */ 
/*      */         }
/*      */         catch (IllegalArgumentException iae)
/*      */         {
/* 1064 */           String errMsg = iae.getMessage();
/*      */           
/*      */ 
/* 1067 */           request.setAttribute("heading", "0");
/* 1068 */           request.setAttribute("strTime", "0");
/* 1069 */           return mapping.findForward("report.message");
/*      */         }
/*      */       }
/*      */       
/* 1073 */       AMLog.debug("timeStamps--" + timeStamps[0] + "1+" + timeStamps[1]);
/* 1074 */       if (timeStamps.length > 2)
/*      */       {
/* 1076 */         AMLog.debug("timeStamps--" + timeStamps[2] + "1+" + timeStamps[3]);
/*      */       }
/* 1078 */       String datatable1 = "";
/* 1079 */       if (!serverAttributes.booleanValue())
/*      */       {
/* 1081 */         datatable1 = attributeProps.getProperty("" + attributeIDS.get(0));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1086 */         datatable1 = "AM_CPU_MinMaxAvgData";
/*      */       }
/*      */       
/* 1089 */       long[] dailyRptTimestamp = ReportUtilities.getDailyStartEndTime(timeStamps[0], timeStamps[1], datatable1);
/*      */       
/* 1091 */       request.setAttribute("dailyStime", dailyRptTimestamp[2] + "");
/* 1092 */       request.setAttribute("dailyEtime", dailyRptTimestamp[3] + "");
/*      */       
/* 1094 */       String dailyRptCondition = " arch1.DURATION=1 and arch1.ARCHIVEDTIME >={0} and arch1.ARCHIVEDTIME <={1}";
/* 1095 */       dailyRptCondition = DBQueryUtil.getDBQuery(dailyRptCondition, new String[] { "" + dailyRptTimestamp[0], "" + dailyRptTimestamp[1] });
/*      */       
/*      */ 
/* 1098 */       if (dailyRptTimestamp[2] > 0L)
/*      */       {
/*      */ 
/*      */ 
/* 1102 */         dailyRptCondition = " arch1.DURATION=2 and arch1.ARCHIVEDTIME >={0} and arch1.ARCHIVEDTIME <={1}  ";
/*      */         
/* 1104 */         dailyRptCondition = DBQueryUtil.getDBQuery(dailyRptCondition, new String[] { "" + dailyRptTimestamp[2], "" + dailyRptTimestamp[3] });
/* 1105 */         AMLog.debug("dailyRptCondition1111===" + dailyRptCondition);
/*      */       }
/*      */       
/* 1108 */       if (forbHr)
/*      */       {
/*      */ 
/*      */ 
/* 1112 */         dailyRptCondition = ReportDataUtilities.returnDailyCondtionForBHours(dailyRptTimestamp, bHourDetail);
/*      */       }
/* 1114 */       if (serverAttributes.booleanValue())
/*      */       {
/* 1116 */         countProps = VMReportUtilities.returnCountPropsForAllServerAttributes(allAttributes1, attributeProps, dailyRptCondition);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1122 */         countProps = VMReportUtilities.returnCountProps(attributeIDS, attributeProps, dailyRptCondition);
/*      */       }
/* 1124 */       AMLog.debug(countProps);
/* 1125 */       Enumeration resids = countProps.keys();
/* 1126 */       String residseq = "";
/* 1127 */       String primaryResid = "-1";
/* 1128 */       while (resids.hasMoreElements())
/*      */       {
/* 1130 */         if (residseq.equals(""))
/*      */         {
/*      */ 
/* 1133 */           residseq = (String)resids.nextElement();
/* 1134 */           primaryResid = residseq;
/*      */         }
/*      */         else
/*      */         {
/* 1138 */           residseq = residseq + "," + (String)resids.nextElement();
/*      */         }
/*      */       }
/*      */       
/* 1142 */       request.setAttribute("RESOURCEIDS", residseq);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1149 */         if (primaryResid.equalsIgnoreCase("-1"))
/*      */         {
/*      */ 
/* 1152 */           resource = ReportUtilities.getResourceTypeForAttribute(attributeIDS.get(0) + "");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 1158 */           resource = ReportUtilities.getResourceType(primaryResid);
/* 1159 */           if (resource.trim().equalsIgnoreCase(""))
/*      */           {
/* 1161 */             resource = ReportUtilities.getResourceTypeForAttribute(attributeIDS.get(0) + "");
/*      */           }
/*      */         }
/*      */         
/* 1165 */         String image = "";
/* 1166 */         String hostImage = "";
/*      */         
/* 1168 */         if (!serverAttributes.booleanValue())
/*      */         {
/* 1170 */           resourceTypeName = VMReportUtilities.getDisplayName(resource);
/* 1171 */           Properties imageProps = AMCacheHandler.virtualServerAttributesProps;
/* 1172 */           if (resourceTypeName.contains("Hyper-V Virtual Machine"))
/*      */           {
/* 1174 */             image = VMReportUtilities.returnImageForVirtualServers(imageProps, "VirtualMachine");
/* 1175 */             hostImage = VMReportUtilities.returnImageForVirtualServers(imageProps, "Hyper-V-Server");
/*      */ 
/*      */ 
/*      */           }
/* 1179 */           else if (resourceTypeName.contains("Virtual Machine"))
/*      */           {
/* 1181 */             image = VMReportUtilities.returnImageForVirtualServers(imageProps, "VirtualMachine");
/* 1182 */             hostImage = VMReportUtilities.returnImageForVirtualServers(imageProps, "VMWare ESX/ESXi");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1187 */             image = VMReportUtilities.returnImageForVirtualServers(imageProps, resourceTypeName);
/*      */           }
/*      */           
/* 1190 */           int index = image.indexOf("@");
/* 1191 */           if (index != -1)
/*      */           {
/* 1193 */             image = image.substring(0, index);
/*      */           }
/* 1195 */           if ((image != null) && (!image.equalsIgnoreCase("")))
/*      */           {
/* 1197 */             request.setAttribute("imagepath", image);
/*      */           }
/*      */           
/* 1200 */           if ((hostImage != null) && (!hostImage.equalsIgnoreCase("")))
/*      */           {
/* 1202 */             request.setAttribute("hostImage", hostImage);
/*      */           }
/*      */         }
/* 1205 */         String monName = FormatUtil.getString("am.reporttab.oversizedvm.heading.text");
/* 1206 */         String hostName = FormatUtil.getString("am.reporttab.undersizedvm.hostname.text");
/* 1207 */         if ((!resourceTypeName.equalsIgnoreCase("")) && (resourceTypeName.equalsIgnoreCase("Hyper-V Virtual Machine")))
/*      */         {
/* 1209 */           monName = FormatUtil.getString("Virtual Machine");
/*      */         }
/* 1211 */         if (resourceTypeName.equalsIgnoreCase("Virtual Machine"))
/*      */         {
/* 1213 */           hostName = FormatUtil.getString("am.reporttab.vmware.server");
/*      */         }
/*      */         
/* 1216 */         if (resourceTypeName.equalsIgnoreCase("Hyper-V Virtual Machine"))
/*      */         {
/* 1218 */           hostName = FormatUtil.getString("am.reporttab.hyperv.server");
/*      */         }
/* 1220 */         request.setAttribute("hostname", hostName);
/* 1221 */         request.setAttribute("monname", monName);
/*      */         
/* 1223 */         VMReportUtilities.setConfigurationText(request, reportProps, resourceTypeName, reportMethod);
/*      */         
/* 1225 */         VMReportUtilities.setConfigurationText(request, reportProps, resourceTypeName, reportMethod);
/*      */         
/* 1227 */         ArrayList resIDs = new ArrayList();
/* 1228 */         ArrayList allHaids = new ArrayList();
/* 1229 */         request.setAttribute("capacityPlanningFilter", "true");
/* 1230 */         String customFieldRestypes = resource;
/* 1231 */         if ("Servers".equalsIgnoreCase(resourceType)) {
/* 1232 */           Properties allServerTypes = (Properties)rf.getSystems().get(0);
/* 1233 */           customFieldRestypes = (String)allServerTypes.get("value");
/* 1234 */           if (customFieldRestypes.indexOf("AS400/iSeries") != -1) {
/* 1235 */             customFieldRestypes = customFieldRestypes.replace("'AS400/iSeries',", "");
/*      */           }
/*      */         }
/* 1238 */         request.setAttribute("capacityPlanningResourceTypes", customFieldRestypes);
/* 1239 */         AMLog.debug("Capacity Planning ResourceTypes ######## " + customFieldRestypes);
/* 1240 */         if (customfield.equals("true"))
/*      */         {
/* 1242 */           resIDs = (ArrayList)MyFields.customFieldResourceIDs(customValue, customFieldRestypes);
/*      */           
/* 1244 */           AMLog.debug("MyFields haids==" + allHaids);
/* 1245 */           haidIsPresent = Boolean.valueOf(true);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1254 */           AMLog.debug("resIDS in custom MG==" + resIDs);
/*      */         }
/*      */         
/* 1257 */         if ((haid != null) && (haid.equalsIgnoreCase("all")))
/*      */         {
/* 1259 */           haidIsPresent = Boolean.valueOf(true);
/* 1260 */           AMReportActions actions1 = new AMReportActions();
/* 1261 */           actions1.getHolisticApps(mapping, form, request, response);
/*      */           
/* 1263 */           ArrayList vmApplications = rf.getVmapplications();
/* 1264 */           AMLog.debug("vmApplications==---" + vmApplications);
/* 1265 */           ArrayList mgIDS = new ArrayList();
/* 1266 */           for (int k = 0; k < vmApplications.size(); k++)
/*      */           {
/* 1268 */             Properties mgMap = (Properties)vmApplications.get(k);
/* 1269 */             String mgID = mgMap.getProperty("value");
/* 1270 */             if ((mgID != null) && (!mgID.equalsIgnoreCase("all")))
/*      */             {
/* 1272 */               mgIDS.add(mgMap.getProperty("value"));
/*      */             }
/*      */           }
/*      */           
/* 1276 */           if (serverAttributes.booleanValue())
/*      */           {
/* 1278 */             resIDs = ReportDataUtilities.getRelatedMonitors(mgIDS, "servers");
/*      */           }
/*      */           else
/*      */           {
/* 1282 */             resIDs = ReportDataUtilities.getRelatedMonitors(mgIDS, resource);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1287 */         if ((haid != null) && (!haid.equalsIgnoreCase("all")) && (!haid.equalsIgnoreCase("nomgs")) && (!haid.equalsIgnoreCase("nogroup")) && (!haid.equalsIgnoreCase("Select Monitor Group")))
/*      */         {
/*      */ 
/* 1290 */           haidIsPresent = Boolean.valueOf(true);
/*      */           
/* 1292 */           ArrayList a1 = new ArrayList();
/*      */           
/* 1294 */           a1.add(haid);
/* 1295 */           if (serverAttributes.booleanValue())
/*      */           {
/* 1297 */             resIDs = ReportDataUtilities.getRelatedMonitors(a1, "servers");
/*      */           }
/*      */           else
/*      */           {
/* 1301 */             resIDs = ReportDataUtilities.getRelatedMonitors(a1, resource);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1310 */         AMLog.debug(" resIDs evaluated--" + resIDs);
/* 1311 */         if ((resIDs != null) && (resIDs.size() > 0))
/*      */         {
/* 1313 */           residsString = ReportDataUtilities.getCommaSeparatedIdsWithQuotes(resIDs);
/*      */ 
/*      */ 
/*      */         }
/* 1317 */         else if (haidIsPresent.booleanValue())
/*      */         {
/* 1319 */           request.setAttribute("heading", FormatUtil.getString(headingString, new String[] { FormatUtil.getString(resourceTypeName) }));
/* 1320 */           request.setAttribute("resourcetypename", FormatUtil.getString(resourceTypeName));
/* 1321 */           if (!serverAttributes.booleanValue())
/*      */           {
/* 1323 */             rf.setResourceType(resourceTypeName);
/*      */           }
/*      */           else
/*      */           {
/* 1327 */             rf.setResourceType(resourceType);
/*      */           }
/*      */           
/* 1330 */           AMLog.debug("No Monitors associated with  MG " + mg);
/* 1331 */           if (customfield.equals("true")) {
/* 1332 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.capacityplanning.nomonitors.customfield.message", new String[] { FormatUtil.getString(resourceTypeName), customFieldDescription })));
/*      */           }
/*      */           else
/*      */           {
/* 1336 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.capacityplanning.nomonitors.mg.message", new String[] { FormatUtil.getString(resourceTypeName), mg })));
/*      */           }
/*      */           
/* 1339 */           saveMessages(request, messages);
/* 1340 */           request.setAttribute("hidedata", "true");
/* 1341 */           request.setAttribute("reportProps", reportProps);
/* 1342 */           if ((reportType != null) && (reportType.equalsIgnoreCase("pdf")))
/*      */           {
/*      */ 
/* 1345 */             request.setAttribute("report-type-template", "report.undersized");
/* 1346 */             request.setAttribute("reportType", "pdf");
/* 1347 */             request.setAttribute("resultmap", sorted_map1);
/*      */           }
/*      */           
/*      */ 
/* 1351 */           return mapping.findForward(mapforward);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1357 */         e.printStackTrace();
/*      */       }
/* 1359 */       if (serverAttributes.booleanValue())
/*      */       {
/* 1361 */         resourceTypeName = FormatUtil.getString("am.reporttab.capacityplanning.server");
/*      */       }
/*      */       
/* 1364 */       if (headingString != null)
/*      */       {
/* 1366 */         request.setAttribute("heading", FormatUtil.getString(headingString, new String[] { FormatUtil.getString(resourceTypeName) }));
/* 1367 */         request.setAttribute("graphheading", FormatUtil.getString(headingString, new String[] { FormatUtil.getString(resourceTypeName) }));
/* 1368 */         request.setAttribute("resourcetypename", FormatUtil.getString(resourceTypeName));
/* 1369 */         if (!serverAttributes.booleanValue())
/*      */         {
/* 1371 */           rf.setResourceType(FormatUtil.getString(resourceTypeName));
/*      */         }
/*      */         else {
/* 1374 */           rf.setResourceType(resourceType);
/*      */         }
/*      */       }
/* 1377 */       if (((resource.contains("HyperVVirtualMachine")) || (resource.contains("VirtualMachine"))) && (!residseq.trim().equalsIgnoreCase("")))
/*      */       {
/*      */ 
/* 1380 */         String disidsquery = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DisplayName,p1.CHILDID AS RESID FROM AM_PARENTCHILDMAPPER p1,AM_ManagedObject where p1.CHILDID IN (" + residseq + ") AND AM_ManagedObject.RESOURCEID=p1.PARENTID AND   AM_ManagedObject.TYPE not in ('HAI')";
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1385 */           set = AMConnectionPool.executeQueryStmt(disidsquery);
/* 1386 */           while (set.next())
/*      */           {
/* 1388 */             String hostName = set.getString(2);
/* 1389 */             String childId = set.getString(3);
/* 1390 */             countProps.setProperty(childId + "_hostname", hostName);
/*      */           }
/* 1392 */           request.setAttribute("parentHostPresent", Boolean.valueOf(true));
/* 1393 */           length += 1;
/* 1394 */           AMLog.debug("countProps==" + countProps);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1398 */           exc.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 1402 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1409 */         request.setAttribute("parentHostPresent", Boolean.valueOf(false));
/*      */       }
/*      */       
/* 1412 */       if (reportProps != null)
/*      */       {
/* 1414 */         request.setAttribute("reportProps", reportProps);
/*      */       }
/* 1416 */       String condition = ">";
/*      */       
/*      */ 
/* 1419 */       HashMap paramsMap = new HashMap();
/* 1420 */       paramsMap.put("reportMethod", reportMethod);
/* 1421 */       paramsMap.put("resource", resource);
/* 1422 */       paramsMap.put("operatorCondition", operatorCondition);
/* 1423 */       paramsMap.put("reportid", Integer.valueOf(reportid));
/* 1424 */       paramsMap.put("residsString", residsString);
/* 1425 */       paramsMap.put("dailyRptCondition", dailyRptCondition);
/* 1426 */       paramsMap.put("haidIsPresent", haidIsPresent);
/* 1427 */       paramsMap.put("customisedReports", this.customisedReports);
/* 1428 */       paramsMap.put("allserverreport", serverAttributes);
/* 1429 */       paramsMap.put("resourceType", resourceType);
/* 1430 */       paramsMap.put("showAll", showAllMonitors);
/*      */       
/* 1432 */       ArrayList resultList = new ArrayList();
/* 1433 */       if (!serverAttributes.booleanValue())
/*      */       {
/* 1435 */         resultList = VMReportUtilities.queryMap(paramsMap, attributeProps, countProps, reportProps, attributeIDS, request);
/*      */       }
/*      */       else
/*      */       {
/* 1439 */         resultList = VMReportUtilities.queryMapForServers(paramsMap, attributeProps, countProps, reportProps, attributeIDS, request);
/*      */       }
/* 1441 */       resultMap = (HashMap)resultList.get(0);
/* 1442 */       totalList = (ArrayList)resultList.get(1);
/*      */       
/*      */ 
/* 1445 */       int mapLength = attributeIDS.size() + length;
/* 1446 */       HashMap individualMap = new HashMap();
/* 1447 */       HashMap newResultMap = new HashMap();
/* 1448 */       HashMap paramsMap2 = new HashMap();
/* 1449 */       paramsMap2.put("attributeProps", attributeProps);
/* 1450 */       paramsMap2.put("returnProps", reportProps);
/* 1451 */       paramsMap2.put("condition", condition);
/* 1452 */       paramsMap2.put("dailyRptCondition", dailyRptCondition);
/* 1453 */       paramsMap2.put("resIDs", totalList);
/* 1454 */       paramsMap2.put("allserverreport", serverAttributes);
/*      */       
/* 1456 */       individualMap = VMReportUtilities.getIndividualUtilityProps(attributeIDS, paramsMap2, this.customisedReports, request);
/* 1457 */       newResultMap = VMReportUtilities.getCombineProps(resultMap, individualMap, attributeIDS, reportProps, categoryTitle, attributeNames);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1462 */       ComparatorImpl2 bvc = new ComparatorImpl2(resultMap);
/* 1463 */       TreeMap sorted_map = new TreeMap(bvc);
/* 1464 */       sorted_map.putAll(resultMap);
/* 1465 */       int num = rf.getNumberOfRows();
/* 1466 */       request.setAttribute("numberOfRows", Integer.valueOf(num));
/* 1467 */       sorted_map1 = VMReportUtilities.removeLimitEntries(sorted_map, num);
/* 1468 */       AMLog.audit("capacitydebug : " + sorted_map1.size() + " " + num + "==sortedmap1===" + sorted_map1);
/*      */       
/* 1470 */       request.setAttribute("mapLength", mapLength + "");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1480 */       request.setAttribute("reportid", Integer.valueOf(reportid));
/*      */       
/* 1482 */       request.setAttribute("strTime", new Date(timeStamps[0]));
/* 1483 */       request.setAttribute("endTime", new Date(timeStamps[1]));
/* 1484 */       request.setAttribute("STIME", timeStamps[0] + "");
/* 1485 */       request.setAttribute("ETIME", timeStamps[1] + "");
/* 1486 */       if (resultMap.size() == 0)
/*      */       {
/* 1488 */         AMLog.debug("saved message for custom VM report--" + FormatUtil.getString("customreport.nodata.time"));
/* 1489 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("customreport.nodata.time")));
/* 1490 */         saveMessages(request, messages);
/*      */       }
/*      */       
/* 1493 */       reportType = rf.getReporttype();
/*      */       
/*      */ 
/* 1496 */       if ((reportType != null) && (reportType.equalsIgnoreCase("pdf")))
/*      */       {
/*      */ 
/* 1499 */         request.setAttribute("report-type-template", "report.undersized");
/* 1500 */         request.setAttribute("reportType", "pdf");
/* 1501 */         request.setAttribute("resultmap", sorted_map1);
/*      */         
/* 1503 */         return mapping.findForward("report.undersized.pdf");
/*      */       }
/*      */       
/* 1506 */       if (rf.getReporttype().equals("excel")) {
/* 1507 */         request.setAttribute("report-type-template", "report.undersized");
/* 1508 */         request.setAttribute("reportType", "excel");
/* 1509 */         request.setAttribute("resultmap", sorted_map1);
/*      */         
/* 1511 */         return mapping.findForward("report.undersized.pdf");
/*      */       }
/* 1513 */       if ((reportType != null) && (reportType.equalsIgnoreCase("csv")))
/*      */       {
/*      */ 
/* 1516 */         request.setAttribute("timeTitle", FormatUtil.getString(timeTitle));
/* 1517 */         request.setAttribute("resultmap", sorted_map1);
/*      */         
/* 1519 */         return mapping.findForward("report.undersized.csv");
/*      */       }
/*      */       
/* 1522 */       request.setAttribute("resultmap", sorted_map1);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1526 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1530 */     return mapping.findForward(mapforward);
/*      */   }
/*      */   
/*      */   public ArrayList updateReportsinDB(int reportid, ArrayList attdIds, HttpServletRequest request)
/*      */     throws Exception
/*      */   {
/* 1536 */     PreparedStatement pstmt = null;
/* 1537 */     Statement ps1 = null;
/*      */     try {
/* 1539 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       try
/*      */       {
/* 1542 */         ps1 = AMConnectionPool.getConnection().createStatement();
/*      */       }
/*      */       catch (Exception e) {
/* 1545 */         e.printStackTrace();
/*      */       }
/*      */       
/* 1548 */       ResultSet set2 = null;
/*      */       
/* 1550 */       String combinationID = "";
/*      */       
/*      */       try
/*      */       {
/* 1554 */         String query = "select  RULEID,RULENAME  from AM_CAPACITY_PLANNING_RULES WHERE REPORTID=" + reportid;
/* 1555 */         set2 = AMConnectionPool.executeQueryStmt(query);
/* 1556 */         while (set2.next())
/*      */         {
/* 1558 */           combinationID = set2.getString(1);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1564 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1568 */         AMConnectionPool.closeStatement(set2);
/*      */       }
/* 1570 */       if (request.getParameter("timeused") != null)
/*      */       {
/* 1572 */         String timeUtilization = request.getParameter("timeused");
/*      */         
/* 1574 */         String query = "update AM_CAPACITY_PLANNING_REPORTS set TIMETHRESHOLD=" + timeUtilization + " where REPORTID=" + reportid;
/*      */         
/* 1576 */         AMLog.debug(" time query" + query);
/* 1577 */         ps1.addBatch(query);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1583 */       if (request.getParameter("timecond") != null)
/*      */       {
/* 1585 */         String timeCondition = request.getParameter("timecond");
/* 1586 */         String query1 = "update AM_CAPACITY_PLANNING_REPORTS set TIMECONDITION='" + timeCondition + "' where REPORTID=" + reportid;
/*      */         
/*      */ 
/* 1589 */         ps1.addBatch(query1);
/*      */       }
/*      */       
/*      */ 
/* 1593 */       if (request.getParameter("combination") != null)
/*      */       {
/* 1595 */         String combination = request.getParameter("combination");
/* 1596 */         if ((combination != null) && (combination.indexOf("object") == -1))
/*      */         {
/* 1598 */           String query2 = "update AM_CAPACITY_PLANNING_RULES set RULENAME='" + combination + "' where  REPORTID=" + reportid;
/* 1599 */           AMLog.debug("combination--" + query2);
/* 1600 */           ps1.addBatch(query2);
/*      */         }
/*      */       }
/* 1603 */       for (int i = 1; i <= attdIds.size(); i++)
/*      */       {
/* 1605 */         String attdId = "" + attdIds.get(i - 1);
/* 1606 */         String paramName = "thresName" + attdId;
/* 1607 */         String paramName1 = "option" + attdId;
/*      */         
/* 1609 */         if (request.getParameter(paramName) != null)
/*      */         {
/* 1611 */           String threshName = request.getParameter(paramName);
/* 1612 */           if (threshName.equals("empty"))
/*      */           {
/* 1614 */             String deleteQuery = "delete from AM_CAPACITY_PLANNING_THRESHOLDS where ATTRIBUTEID=" + attdId + " and REPORTID=" + reportid;
/* 1615 */             AMLog.debug("delete queries in updatereports==" + deleteQuery);
/* 1616 */             ps1.addBatch(deleteQuery);
/*      */           }
/*      */           else
/*      */           {
/* 1620 */             String sql = "update AM_CAPACITY_PLANNING_THRESHOLDS set \tTHRESHOLD='" + threshName + "'  where REPORTID=" + reportid + " and ATTRIBUTEID=" + attdId;
/*      */             
/* 1622 */             ps1.addBatch(sql);
/*      */           }
/*      */         }
/*      */         
/* 1626 */         if (request.getParameter(paramName1) != null)
/*      */         {
/* 1628 */           String thresholdCondn = request.getParameter(paramName1);
/* 1629 */           String sql1 = "update AM_CAPACITY_PLANNING_THRESHOLDS set CONDITIONTYPE='" + thresholdCondn + "' where REPORTID=" + reportid + " and ATTRIBUTEID=" + attdId;
/*      */           
/* 1631 */           ps1.addBatch(sql1);
/*      */         }
/*      */       }
/*      */       
/* 1635 */       if (request.getParameter("unconfiguredids") != null)
/*      */       {
/* 1637 */         String Thresholdquery = "insert into AM_CAPACITY_PLANNING_THRESHOLDS (THRESHOLDID,ATTRIBUTEID,CONDITIONTYPE,THRESHOLD,RULEID,REPORTID) VALUES (";
/*      */         
/* 1639 */         String unconfiguredAttributes = request.getParameter("unconfiguredids");
/* 1640 */         StringTokenizer st = new StringTokenizer(unconfiguredAttributes, ",");
/* 1641 */         ArrayList unconfiguredAttlist = new ArrayList();
/* 1642 */         while (st.hasMoreTokens())
/*      */         {
/* 1644 */           String unconfiguredID = (String)st.nextElement();
/* 1645 */           unconfiguredAttlist.add(unconfiguredID);
/*      */         }
/*      */         
/* 1648 */         int k = 1;
/* 1649 */         for (int i = 0; i < unconfiguredAttlist.size(); i++)
/*      */         {
/* 1651 */           String attdId = "" + unconfiguredAttlist.get(i);
/* 1652 */           String paramName = "unconfiguredthresName" + attdId;
/* 1653 */           String paramName1 = "option" + attdId;
/*      */           
/* 1655 */           if ((request.getParameter(paramName) != null) && (request.getParameter(paramName1) != null) && (!request.getParameter(paramName).equals("empty")))
/*      */           {
/* 1657 */             String thresholdValue = request.getParameter(paramName);
/* 1658 */             String thresholdCondn = request.getParameter(paramName1);
/* 1659 */             long ThresholdID = ReportUtil.getMaxID("THRESHOLDID", "AM_CAPACITY_PLANNING_THRESHOLDS ") + k;
/* 1660 */             k++;
/* 1661 */             StringBuffer thresQuery = new StringBuffer(Thresholdquery).append(ThresholdID).append(",").append(attdId).append(",'").append(thresholdCondn).append("',").append(thresholdValue).append(",").append(combinationID).append(",").append(reportid).append(")");
/*      */             
/*      */ 
/* 1664 */             ps1.addBatch(thresQuery.toString());
/*      */           }
/*      */           
/*      */ 
/* 1668 */           attdIds.add(attdId);
/*      */         }
/*      */       }
/* 1671 */       ps1.executeBatch();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1676 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1680 */       if (ps1 != null)
/*      */       {
/* 1682 */         ps1.close();
/*      */       }
/*      */     }
/*      */     
/* 1686 */     return attdIds;
/*      */   }
/*      */   
/*      */   public ArrayList insertReportinDB(String reportName, ArrayList attdIds, HttpServletRequest request, String reportMethod, Boolean serverAttributes) throws Exception {
/* 1690 */     AMLog.debug("reportName to be inserted " + reportName);
/*      */     
/* 1692 */     PreparedStatement pstmt = null;
/*      */     
/* 1694 */     Statement ps1 = null;
/*      */     try {
/* 1696 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1697 */       long reportid = ReportUtil.getMaxID("REPORTID", "AM_CAPACITY_PLANNING_REPORTS") + 1L;
/*      */       
/* 1699 */       String timeUtilization = "50";
/* 1700 */       if (request.getParameter("timeused") != null)
/*      */       {
/* 1702 */         timeUtilization = request.getParameter("timeused");
/*      */       }
/*      */       
/* 1705 */       String timeCondition = "GT";
/* 1706 */       if (request.getParameter("timecond") != null)
/*      */       {
/* 1708 */         timeCondition = request.getParameter("timecond");
/*      */       }
/*      */       
/* 1711 */       String insertquery = "insert into AM_CAPACITY_PLANNING_REPORTS values(" + reportid + ",'" + reportName + "','" + timeUtilization + "','" + timeCondition + "')";
/*      */       
/* 1713 */       AMConnectionPool.executeUpdateStmt(insertquery);
/* 1714 */       String combination = "OR";
/* 1715 */       String combinationParam = request.getParameter("combination");
/* 1716 */       if ((combinationParam != null) && (combinationParam.indexOf("object") != -1))
/*      */       {
/* 1718 */         combination = combinationParam;
/*      */       }
/*      */       
/* 1721 */       long combintionID = ReportUtil.getMaxID("RULEID", "AM_CAPACITY_PLANNING_RULES") + 1L;
/* 1722 */       String combinationquery = "insert into  AM_CAPACITY_PLANNING_RULES(RULEID,RULENAME,REPORTID) values (" + combintionID + ",'" + combination + "'," + reportid + ") ";
/* 1723 */       AMConnectionPool.executeUpdateStmt(combinationquery);
/*      */       
/*      */ 
/* 1726 */       ps1 = AMConnectionPool.getConnection().createStatement();
/*      */       
/* 1728 */       String unconfiguredAttributes = "";
/* 1729 */       String Thresholdquery = "insert into AM_CAPACITY_PLANNING_THRESHOLDS (THRESHOLDID,ATTRIBUTEID,CONDITIONTYPE,THRESHOLD,RULEID,REPORTID) VALUES (";
/* 1730 */       AMLog.debug("thresholdquery===" + Thresholdquery);
/* 1731 */       int k = 1;
/*      */       
/*      */ 
/* 1734 */       for (int i = 1; i <= attdIds.size(); i++)
/*      */       {
/* 1736 */         String attdId = "" + attdIds.get(i - 1);
/*      */         
/* 1738 */         String thresholdValue = "90";
/*      */         
/* 1740 */         String thresholdCondn = "GT";
/* 1741 */         if (reportMethod.equalsIgnoreCase("generateOverSizedMonitors"))
/*      */         {
/* 1743 */           thresholdValue = "50";
/* 1744 */           thresholdCondn = "LT";
/*      */         }
/* 1746 */         else if (reportMethod.equalsIgnoreCase("generateIdleMonitors"))
/*      */         {
/* 1748 */           thresholdValue = "30";
/* 1749 */           thresholdCondn = "LT";
/*      */         }
/* 1751 */         String paramName = "thresName" + attdId;
/* 1752 */         if (request.getParameter(paramName) != null)
/*      */         {
/* 1754 */           thresholdValue = request.getParameter(paramName);
/*      */         }
/* 1756 */         String paramName1 = "option" + attdId;
/* 1757 */         if (request.getParameter(paramName1) != null)
/*      */         {
/* 1759 */           thresholdCondn = request.getParameter(paramName1);
/*      */         }
/* 1761 */         long ThresholdID = ReportUtil.getMaxID("THRESHOLDID", "AM_CAPACITY_PLANNING_THRESHOLDS ") + k;
/* 1762 */         k++;
/* 1763 */         StringBuffer thresQuery = new StringBuffer(Thresholdquery).append(ThresholdID).append(",").append(attdId).append(",'").append(thresholdCondn).append("',").append(thresholdValue).append(",").append(combintionID).append(",").append(reportid).append(")");
/* 1764 */         ps1.addBatch(thresQuery.toString());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1770 */       if (request.getParameter("unconfiguredids") != null)
/*      */       {
/* 1772 */         unconfiguredAttributes = request.getParameter("unconfiguredids");
/* 1773 */         StringTokenizer st = new StringTokenizer(unconfiguredAttributes, ",");
/* 1774 */         ArrayList unconfiguredAttlist = new ArrayList();
/*      */         
/* 1776 */         while (st.hasMoreTokens())
/*      */         {
/* 1778 */           String unconfiguredID = (String)st.nextElement();
/* 1779 */           unconfiguredAttlist.add(unconfiguredID);
/*      */         }
/*      */         
/* 1782 */         for (int i = 0; i < unconfiguredAttlist.size(); i++)
/*      */         {
/* 1784 */           String attdId = "" + unconfiguredAttlist.get(i);
/* 1785 */           String paramName = "unconfiguredthresName" + attdId;
/* 1786 */           String paramName1 = "option" + attdId;
/* 1787 */           if ((request.getParameter(paramName) != null) && (request.getParameter(paramName1) != null))
/*      */           {
/* 1789 */             String thresholdValue = request.getParameter(paramName);
/* 1790 */             String thresholdCondn = request.getParameter(paramName1);
/* 1791 */             long ThresholdID = ReportUtil.getMaxID("THRESHOLDID", "AM_CAPACITY_PLANNING_THRESHOLDS") + k;
/* 1792 */             StringBuffer thresQuery = new StringBuffer(Thresholdquery).append(ThresholdID).append(",").append(attdId).append(",'").append(thresholdCondn).append("',").append(thresholdValue).append(",").append(combintionID).append(",").append(reportid).append(")");
/*      */             
/* 1794 */             k++;
/* 1795 */             ps1.addBatch(thresQuery.toString());
/*      */           }
/*      */           
/* 1798 */           attdIds.add(attdId);
/*      */         }
/*      */       }
/* 1801 */       ps1.executeBatch();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 1807 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1811 */       if (ps1 != null)
/*      */       {
/* 1813 */         ps1.close();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1818 */     return attdIds;
/*      */   }
/*      */   
/*      */   public ActionForward saveNoteId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1823 */     String resourceId = request.getParameter("resourceid");
/* 1824 */     String reportId = request.getParameter("reportid");
/*      */     
/* 1826 */     String fieldValue = request.getParameter("fieldvalue");
/* 1827 */     String savedValue = ReportDataUtilities.encodeString(fieldValue);
/*      */     
/* 1829 */     AMLog.debug("savedValue==" + savedValue + "==reportID" + reportId + "resourceid==" + resourceId);
/* 1830 */     ResultSet rs = null;
/* 1831 */     boolean updateDB = true;
/* 1832 */     boolean insertDB = true;
/*      */     
/*      */     try
/*      */     {
/* 1836 */       String query = "select RELATIONSHIPID,REASONID from AM_CAPACITY_PLANNING_NOTES where REPORTID=" + reportId + " and RESOURCEID=" + resourceId;
/* 1837 */       AMLog.debug("comment query==" + query);
/* 1838 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1839 */       String reasonidUpdate = "-1";
/*      */       
/* 1841 */       while (rs.next())
/*      */       {
/* 1843 */         insertDB = false;
/* 1844 */         updateDB = true;
/* 1845 */         reasonidUpdate = rs.getString(2);
/*      */         
/* 1847 */         AMConnectionPool.executeUpdateStmt("update AM_DOWNTIMEREASON SET SHORT_DESCRIPTION='" + savedValue + "' WHERE  REASONID=" + reasonidUpdate);
/* 1848 */         AMConnectionPool.executeUpdateStmt("update AM_DOWNTIMEREASON SET LONG_DESCRIPTION='" + savedValue + "' WHERE  REASONID=" + reasonidUpdate);
/* 1849 */         AMConnectionPool.executeUpdateStmt("update AM_CAPACITY_PLANNING_NOTES SET LASTUPDATEDTIME =" + System.currentTimeMillis() + " WHERE  REASONID=" + reasonidUpdate);
/*      */       }
/*      */       
/* 1852 */       if (insertDB)
/*      */       {
/*      */ 
/* 1855 */         AMLog.debug("inside insert Reports");
/* 1856 */         int reasonID = DBQueryUtil.getIncrementedID("REASONID", "AM_DOWNTIMEREASON");
/* 1857 */         int relID = DBQueryUtil.getIncrementedID("RELATIONSHIPID", "AM_CAPACITY_PLANNING_NOTES");
/* 1858 */         AMConnectionPool.executeUpdateStmt("insert into AM_DOWNTIMEREASON (REASONID,SHORT_DESCRIPTION,LONG_DESCRIPTION) values (" + reasonID + ",'" + savedValue + "','" + savedValue + "')");
/* 1859 */         AMConnectionPool.executeUpdateStmt("insert into AM_CAPACITY_PLANNING_NOTES (RELATIONSHIPID,REPORTID,RESOURCEID,REASONID,LASTUPDATEDTIME ) VALUES(" + relID + "," + reportId + "," + resourceId + "," + reasonID + "," + System.currentTimeMillis() + ")");
/*      */       }
/*      */       
/* 1862 */       response.setContentType("text/html; charset=utf-8");
/* 1863 */       PrintWriter out = response.getWriter();
/*      */       
/*      */       try
/*      */       {
/* 1867 */         if (savedValue.length() > 40)
/*      */         {
/* 1869 */           fieldValue = fieldValue.substring(0, 40) + "......";
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/* 1878 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1882 */         out.println(fieldValue);
/* 1883 */         out.flush();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1888 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1892 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1894 */     return null;
/*      */   }
/*      */   
/*      */   class ComparatorImpl2 implements Comparator
/*      */   {
/*      */     Map base;
/*      */     
/*      */     public ComparatorImpl2(HashMap map1) {
/* 1902 */       this.base = map1;
/*      */     }
/*      */     
/*      */     public int compare(Object o1, Object o2) {
/* 1906 */       if ((o1 == null) || (o2 == null)) {
/* 1907 */         return 0;
/*      */       }
/* 1909 */       String key1 = (String)o1;
/* 1910 */       String key2 = (String)o2;
/*      */       
/* 1912 */       Properties prop1 = (Properties)this.base.get(key1);
/*      */       
/* 1914 */       Properties prop2 = (Properties)this.base.get(key2);
/*      */       
/* 1916 */       Long val11 = Long.valueOf(Long.parseLong(prop1.getProperty("TimeUsed")));
/* 1917 */       Long val22 = Long.valueOf(Long.parseLong(prop2.getProperty("TimeUsed")));
/* 1918 */       long val1 = val11.longValue();
/* 1919 */       long val2 = val22.longValue();
/* 1920 */       int k = 0;
/* 1921 */       if (val1 > val2)
/* 1922 */         return -1;
/* 1923 */       if (val1 == val2) {
/* 1924 */         return 1;
/*      */       }
/* 1926 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\actions\CustomReportActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */