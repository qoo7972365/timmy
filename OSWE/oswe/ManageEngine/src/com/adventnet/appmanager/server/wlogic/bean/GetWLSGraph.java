/*      */ package com.adventnet.appmanager.server.wlogic.bean;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.datacorrection.AMDataCorrectionUtil;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.DatasetProduceException;
/*      */ import com.adventnet.awolf.data.DatasetProducer;
/*      */ import com.adventnet.awolf.data.UrlProducer;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.jfree.data.category.DefaultCategoryDataset;
/*      */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*      */ import org.jfree.data.general.DefaultPieDataset;
/*      */ import org.jfree.data.general.SubSeriesDataset;
/*      */ import org.jfree.data.time.Minute;
/*      */ import org.jfree.data.time.TimeSeries;
/*      */ import org.jfree.data.time.TimeSeriesCollection;
/*      */ import org.jfree.data.time.TimeTableXYDataset;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GetWLSGraph
/*      */   implements DatasetProducer, UrlProducer, Serializable
/*      */ {
/*   47 */   private String pieChartType = null;
/*   48 */   private String mgName = null;
/*   49 */   private String resID = null;
/*   50 */   private String type = null;
/*   51 */   private String period = "0";
/*   52 */   private String startdate = null;
/*   53 */   private String enddate = null;
/*   54 */   private long starttime = 0L;
/*   55 */   private long endtime = 0L;
/*   56 */   private long collectionTime = 0L;
/*   57 */   private String colTime = null;
/*   58 */   static Log log = LogFactory.getLog("WebClient");
/*   59 */   private Hashtable urls = null;
/*   60 */   private String downIdsList = null;
/*   61 */   private boolean isData = false;
/*   62 */   private String attributeId = null;
/*   63 */   private String primaryColId = null;
/*   64 */   private boolean isConfMonitor = false;
/*   65 */   private String orderBy = null;
/*   66 */   private String graphType = null;
/*   67 */   private String tableType = null;
/*      */   
/*   69 */   private boolean issummaryData = false;
/*   70 */   private Properties summaryData = null;
/*      */   
/*      */   public void setParam(String resid, String type, String period, boolean issummaryData, Properties summaryData)
/*      */   {
/*   74 */     this.resID = resid;
/*   75 */     this.type = type;
/*   76 */     this.starttime = this.starttime;
/*   77 */     this.endtime = this.endtime;
/*   78 */     this.period = period;
/*   79 */     this.summaryData = summaryData;
/*   80 */     this.issummaryData = issummaryData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParam(String resid, String type)
/*      */   {
/*   89 */     this.resID = resid;
/*   90 */     this.type = type;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setParam(String resid, String type, String period)
/*      */   {
/*   96 */     this.resID = resid;
/*   97 */     this.type = type;
/*   98 */     this.period = period;
/*      */   }
/*      */   
/*      */   public void setParam(String resid, String type, long starttime, long endtime)
/*      */   {
/*  103 */     this.resID = resid;
/*  104 */     this.type = type;
/*  105 */     this.starttime = starttime;
/*  106 */     this.endtime = endtime;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setParam(String resid, String type, long starttime, long endtime, String period)
/*      */   {
/*  112 */     this.resID = resid;
/*  113 */     this.type = type;
/*  114 */     this.starttime = starttime;
/*  115 */     this.endtime = endtime;
/*  116 */     this.period = period;
/*      */   }
/*      */   
/*      */   public void setParam(String resid, String type, String period, String startdate, String enddate) {
/*  120 */     this.resID = resid;
/*  121 */     this.type = type;
/*  122 */     this.period = period;
/*  123 */     this.startdate = startdate;
/*  124 */     this.enddate = enddate;
/*      */   }
/*      */   
/*      */   public void setParam(String tempType, String chartType, String groupName, long startTime, long endTime) {
/*  128 */     this.type = tempType;
/*  129 */     this.pieChartType = chartType;
/*  130 */     this.mgName = groupName;
/*  131 */     this.starttime = startTime;
/*  132 */     this.endtime = endTime;
/*      */   }
/*      */   
/*  135 */   public void setParam(String attributeId, String primaryColId, String type, String collectionTime, String orderBy, String graphType, String tableType) { this.attributeId = attributeId;
/*  136 */     this.type = type;
/*  137 */     this.primaryColId = primaryColId;
/*  138 */     this.colTime = collectionTime;
/*  139 */     this.orderBy = orderBy;
/*  140 */     this.graphType = graphType;
/*  141 */     this.tableType = tableType;
/*      */   }
/*      */   
/*      */   public void setParam(String attributeId, String primaryColId, String type, String collectionTime, String orderBy, String graphType, String tableType, String resID) {
/*  145 */     this.attributeId = attributeId;
/*  146 */     this.type = type;
/*  147 */     this.primaryColId = primaryColId;
/*  148 */     this.colTime = collectionTime;
/*  149 */     this.orderBy = orderBy;
/*  150 */     this.graphType = graphType;
/*  151 */     this.tableType = tableType;
/*  152 */     this.resID = resID;
/*      */   }
/*      */   
/*      */   public long getLastDataCollectedTime()
/*      */   {
/*  157 */     return this.collectionTime;
/*      */   }
/*      */   
/*      */   public String getDownIdsList()
/*      */   {
/*  162 */     return this.downIdsList;
/*      */   }
/*      */   
/*      */   public boolean checkData()
/*      */   {
/*  167 */     return this.isData;
/*      */   }
/*      */   
/*      */   public Object produceDataset(Map params) throws DatasetProduceException
/*      */   {
/*  172 */     Object toReturn = null;
/*  173 */     if (this.type.equals("JVM"))
/*      */     {
/*  175 */       this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JVMData where ID=" + this.resID);
/*      */       
/*  177 */       long oneHourBefore = this.collectionTime - 3600000L;
/*  178 */       ResultSet rs = null;
/*  179 */       if (this.collectionTime != -1L)
/*      */       {
/*  181 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*  182 */         String query = "select HEAPSIZECURRENT,COLLECTIONTIME from AM_JVMData where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and ID=" + this.resID;
/*      */         
/*  184 */         TimeSeries ts = new TimeSeries(FormatUtil.getString("Heap Usage"), Minute.class);
/*      */         try
/*      */         {
/*  187 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  188 */           while (rs.next())
/*      */           {
/*  190 */             Date d = new Date(rs.getLong(2));
/*  191 */             Number num = new Long(rs.getLong(1) / 1024L);
/*  192 */             ts.addOrUpdate(new Minute(d), num);
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
/*  203 */             if (rs != null) {
/*  204 */               rs.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  208 */             e.printStackTrace();
/*      */           }
/*      */           
/*      */ 
/*  212 */           col = new TimeSeriesCollection(ts);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  197 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*      */           try
/*      */           {
/*  203 */             if (rs != null) {
/*  204 */               rs.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  208 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */         TimeSeriesCollection col;
/*  213 */         toReturn = new SubSeriesDataset(col, 0);
/*      */       }
/*      */       else
/*      */       {
/*  217 */         System.err.println("No Data collected ");
/*      */       }
/*      */     } else {
/*  220 */       if (this.type.equals("ConfMonitor")) {
/*  221 */         DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  222 */         DefaultPieDataset pieresult = new DefaultPieDataset();
/*  223 */         String tablename = null;
/*  224 */         String primaryColTable = null;
/*  225 */         Properties attvalues = new Properties();
/*  226 */         Properties primaryValues = new Properties();
/*  227 */         String rowIds = null;
/*  228 */         DefaultIntervalCategoryDataset ds = null;
/*  229 */         if (this.collectionTime != -1L) {
/*  230 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  231 */           String tablenameqry = "";
/*  232 */           if (this.tableType.equals("MO")) {
/*  233 */             tablenameqry = "select ext.ATTRIBUTEID,DATATABLE,VALUE_COL,RESOURCETYPE from AM_ATTRIBUTES_EXT ext, AM_ATTRIBUTES att where ext.ATTRIBUTEID=att.ATTRIBUTEID and ext.ATTRIBUTEID=" + this.attributeId;
/*  234 */             primaryColTable = "AM_ManagedObject";
/*      */           }
/*      */           else {
/*  237 */             tablenameqry = "select ext.ATTRIBUTEID,DATATABLE,VALUE_COL,RESOURCETYPE from AM_ATTRIBUTES_EXT ext, AM_ATTRIBUTES att where ext.ATTRIBUTEID=att.ATTRIBUTEID and ext.ATTRIBUTEID=" + this.attributeId + " or ext.ATTRIBUTEID=" + this.primaryColId;
/*      */           }
/*  239 */           ResultSet rs = null;
/*      */           try {
/*  241 */             rs = AMConnectionPool.executeQueryStmt(tablenameqry);
/*  242 */             String attributeName = "";
/*  243 */             String resType = "";
/*  244 */             while (rs.next()) {
/*  245 */               if (rs.getString("ATTRIBUTEID").equals(this.attributeId)) {
/*  246 */                 tablename = rs.getString("DATATABLE");
/*  247 */                 attributeName = rs.getString("VALUE_COL");
/*  248 */                 resType = rs.getString("RESOURCETYPE");
/*      */               }
/*      */               else {
/*  251 */                 primaryColTable = rs.getString("DATATABLE");
/*      */               }
/*      */             }
/*  254 */             if ((tablename != null) && (primaryColTable != null))
/*      */             {
/*  256 */               if ((tablename.equals("AM_ManagedObjectData")) && (attributeName.equals("RESPONSETIME"))) {
/*  257 */                 int baseId = NewMonitorUtil.getBaseId(resType);
/*  258 */                 if (baseId != -1) {
/*  259 */                   tablename = "AM_Script_Numeric_Data_" + Integer.toString(baseId);
/*      */                 }
/*      */               }
/*      */               
/*  263 */               String resNameQuery = "";
/*  264 */               String resColumn = "RESOURCEID";
/*  265 */               if (tablename.startsWith("AM_SCRIPT_TABULAR_NUMERIC_DATA")) {
/*  266 */                 resColumn = "RESID";
/*  267 */                 resNameQuery = "and am.RESOURCENAME like ('%" + this.resID + "')";
/*      */               }
/*  269 */               String query = "select Distinct";
/*  270 */               String qrylast = "order by VALUE";
/*      */               
/*  272 */               if ((this.orderBy != null) || (this.orderBy.equalsIgnoreCase("DESC"))) {
/*  273 */                 qrylast = qrylast + " DESC";
/*      */               }
/*  275 */               if (DBQueryUtil.getDBType().equals("mysql"))
/*      */               {
/*  277 */                 query = query + "(data." + resColumn + ") as RESID,VALUE from " + tablename + " data,AM_ManagedObject am,AM_PARENTCHILDMAPPER map where ATTRIBUTEID =" + this.attributeId + " and  map.PARENTID=" + this.resID + " and map.CHILDID=am.RESOURCEID and COLLECTIONTIME in (" + this.colTime + ") " + resNameQuery + " and am.RESOURCEID=data." + resColumn + " " + qrylast + " limit 10";
/*      */               }
/*      */               else
/*      */               {
/*  281 */                 query = query + " TOP 10 (data." + resColumn + ") as RESID,VALUE from " + tablename + " data,AM_ManagedObject am,AM_PARENTCHILDMAPPER map where ATTRIBUTEID=" + this.attributeId + " and map.PARENTID=" + this.resID + " and map.CHILDID=am.RESOURCEID and  COLLECTIONTIME in (" + this.colTime + ") " + resNameQuery + " and am.RESOURCEID=data." + resColumn + " " + qrylast;
/*      */               }
/*      */               
/*  284 */               ResultSet set = null;
/*      */               try {
/*  286 */                 set = AMConnectionPool.executeQueryStmt(query);
/*  287 */                 while (set.next()) {
/*  288 */                   attvalues.setProperty(set.getString("RESID"), set.getString("VALUE"));
/*  289 */                   if (rowIds == null) {
/*  290 */                     rowIds = set.getString("RESID");
/*      */                   }
/*      */                   else {
/*  293 */                     rowIds = rowIds + "," + set.getString("RESID");
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  301 */                 AMLog.debug(this.graphType + " Graph Query for att" + this.attributeId + " :" + query);
/*      */                 try {
/*  303 */                   if (set != null)
/*  304 */                     set.close();
/*      */                 } catch (Exception e) {
/*  306 */                   e.printStackTrace();
/*      */                 }
/*      */                 
/*  309 */                 if (!this.tableType.equals("MO")) {
/*      */                   break label1348;
/*      */                 }
/*      */               }
/*      */               catch (Exception exp)
/*      */               {
/*  298 */                 exp.printStackTrace();
/*      */               }
/*      */               finally {
/*  301 */                 AMLog.debug(this.graphType + " Graph Query for att" + this.attributeId + " :" + query);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  310 */               query = "select resourceid as RESID,Displayname as VALUE from " + primaryColTable + " where resourceid in (" + rowIds + ")";
/*      */               break label1473;
/*      */               label1348:
/*  313 */               if (primaryColTable.startsWith("AM_SCRIPT_TABULAR_NUMERIC_DATA")) {
/*  314 */                 query = "select RESID,VALUE from " + primaryColTable + " where RESID in (" + rowIds + ") and ATTRIBUTEID=" + this.primaryColId + " and COLLECTIONTIME=" + this.colTime;
/*      */               }
/*      */               else {
/*  317 */                 query = "select ROWID as RESID,VALUE from " + primaryColTable + " where ROWID in (" + rowIds + ") and ATTRIBUTEID=" + this.primaryColId + " and COLLECTIONTIME=" + this.colTime;
/*      */               }
/*      */               label1473:
/*  320 */               ResultSet primaryrs = null;
/*      */               try {
/*  322 */                 primaryrs = AMConnectionPool.executeQueryStmt(query);
/*  323 */                 while (primaryrs.next()) {
/*  324 */                   primaryValues.setProperty(primaryrs.getString("RESID"), primaryrs.getString("VALUE"));
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  331 */                 AMLog.debug(this.graphType + " graph Primary Columns Query for att" + this.primaryColId + " :" + query);
/*      */                 try {
/*  333 */                   if (primaryrs != null)
/*  334 */                     primaryrs.close();
/*      */                 } catch (Exception e) {
/*  336 */                   e.printStackTrace();
/*      */                 }
/*  338 */                 keys = attvalues.keySet();
/*      */               }
/*      */               catch (Exception exp)
/*      */               {
/*  328 */                 exp.printStackTrace();
/*      */               }
/*      */               finally {
/*  331 */                 AMLog.debug(this.graphType + " graph Primary Columns Query for att" + this.primaryColId + " :" + query);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */               Set keys;
/*      */               
/*      */ 
/*  339 */               Iterator it = keys.iterator();
/*  340 */               while (it.hasNext()) {
/*  341 */                 String infoKey = (String)it.next();
/*  342 */                 if (this.graphType.equals("BAR")) {
/*  343 */                   result.addValue(Double.valueOf(Double.parseDouble(attvalues.getProperty(infoKey))), "", primaryValues.getProperty(infoKey));
/*      */                 }
/*  345 */                 else if (this.graphType.equals("PIE")) {
/*  346 */                   pieresult.setValue(FormatUtil.getTrimmedText(primaryValues.getProperty(infoKey), 20), Double.parseDouble(attvalues.getProperty(infoKey)));
/*      */                 }
/*      */               }
/*  349 */               toReturn = this.graphType.equals("BAR") ? result : pieresult;
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/*  353 */             e.printStackTrace();
/*      */           }
/*      */           catch (Exception e) {
/*  356 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/*  360 */               if (rs != null)
/*  361 */                 rs.close();
/*      */             } catch (Exception e) {
/*  363 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  369 */       if (this.type.equals("HEALTH"))
/*      */       {
/*  371 */         long mintimeindb = ReportUtilities.getMinTimeInDB(this.resID);
/*  372 */         long[] timeStamps = ReportUtilities.getTimeStamp(this.period);
/*  373 */         long startTime = timeStamps[0];
/*  374 */         long endTime = timeStamps[1];
/*      */         
/*  376 */         if (mintimeindb > startTime)
/*      */         {
/*  378 */           startTime = mintimeindb;
/*      */         }
/*  380 */         long totalDuration = endTime - startTime;
/*      */         
/*  382 */         Properties overAllHealth = ReportUtilities.getHealthStatsForMO(this.resID, String.valueOf(startTime), String.valueOf(endTime), "", "");
/*  383 */         float critical = Float.parseFloat(overAllHealth.getProperty("Critical"));
/*  384 */         float warning = Float.parseFloat(overAllHealth.getProperty("Warning"));
/*  385 */         float clear = Float.parseFloat(overAllHealth.getProperty("Clear"));
/*  386 */         float unmanaged = Float.parseFloat(overAllHealth.getProperty("Unmanaged"));
/*  387 */         long criticaltime = ((float)totalDuration * critical) / 100L;
/*  388 */         long warningtime = ((float)totalDuration * warning) / 100L;
/*  389 */         long unmanagedtime = ((float)totalDuration * unmanaged) / 100L;
/*  390 */         long cleartime = totalDuration - (criticaltime + warningtime + unmanagedtime);
/*  391 */         DefaultPieDataset ds = new DefaultPieDataset();
/*  392 */         if (critical > 0.0F)
/*      */         {
/*  394 */           ds.setValue(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + " " + ReportUtilities.format(criticaltime), critical);
/*      */         }
/*  396 */         if (warning > 0.0F)
/*      */         {
/*  398 */           ds.setValue(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + " " + ReportUtilities.format(warningtime), warning);
/*      */         }
/*  400 */         if (clear > 0.0F)
/*      */         {
/*  402 */           ds.setValue(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + " " + ReportUtilities.format(cleartime), clear);
/*      */         }
/*  404 */         if (unmanaged > 0.0F)
/*      */         {
/*  406 */           ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + ReportUtilities.format(unmanagedtime), unmanaged);
/*      */         }
/*      */         
/*  409 */         toReturn = ds;
/*      */         
/*  411 */         this.urls = new Hashtable(2);
/*  412 */         this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " down", "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + this.resID + "&period=0')");
/*      */ 
/*      */       }
/*  415 */       else if (this.type.equals("AVAILABILITY"))
/*      */       {
/*  417 */         if (this.issummaryData) {
/*  418 */           String down = "0";
/*  419 */           String up = "0";
/*  420 */           String unmanage = "0";
/*  421 */           String schedule = "0";
/*      */           
/*  423 */           float downPercent = 0.0F;
/*  424 */           float upPercent = 0.0F;
/*  425 */           float unmanagePercent = 0.0F;
/*  426 */           float schedulePercent = 0.0F;
/*      */           
/*  428 */           if (this.summaryData.containsKey("totaldowntime")) {
/*  429 */             down = this.summaryData.getProperty("totaldowntime");
/*  430 */             downPercent = Float.parseFloat(this.summaryData.getProperty("DowntimePercentage"));
/*      */           }
/*  432 */           if (this.summaryData.containsKey("up")) {
/*  433 */             up = this.summaryData.getProperty("up");
/*  434 */             upPercent = Float.parseFloat(this.summaryData.getProperty("uptimepercentage"));
/*      */           }
/*  436 */           if (this.summaryData.containsKey("UnManagedTime")) {
/*  437 */             unmanage = this.summaryData.getProperty("UnManagedTime");
/*  438 */             unmanagePercent = Float.parseFloat(this.summaryData.getProperty("UnManagedPercentage"));
/*      */           }
/*  440 */           if (this.summaryData.containsKey("ScheduledTime")) {
/*  441 */             schedule = this.summaryData.getProperty("ScheduledTime");
/*  442 */             schedulePercent = Float.parseFloat(this.summaryData.getProperty("ScheduledPercentage"));
/*      */           }
/*      */           
/*  445 */           DefaultPieDataset ds = new DefaultPieDataset();
/*  446 */           if (!down.startsWith("0")) {
/*  447 */             ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, Math.round(downPercent * 1000.0F) / 1000.0F);
/*      */           }
/*  449 */           if (!up.startsWith("0")) {
/*  450 */             ds.setValue(FormatUtil.getString("am.webclient.historydata.uptime.text") + " " + up, Math.round(upPercent * 1000.0F) / 1000.0F);
/*      */           }
/*  452 */           if (!unmanage.startsWith("0")) {
/*  453 */             ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + unmanage, Math.round(unmanagePercent * 1000.0F) / 1000.0F);
/*      */           }
/*  455 */           if (!schedule.startsWith("0")) {
/*  456 */             ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") + " " + schedule, Math.round(schedulePercent * 1000.0F) / 1000.0F);
/*      */           }
/*  458 */           toReturn = ds;
/*      */         }
/*      */         else {
/*  461 */           boolean firstTime = true;
/*  462 */           for (int i = 0; i < 2; i++)
/*      */           {
/*  464 */             DefaultPieDataset ds = new DefaultPieDataset();
/*  465 */             AMConnectionPool cp = AMConnectionPool.getInstance();
/*  466 */             ResultSet set = null;
/*      */             
/*      */             try
/*      */             {
/*  470 */               long customstartTime = 0L;
/*  471 */               long customendTime = 0L;
/*  472 */               long[] time = ReportUtilities.getTimeStamp(this.period);
/*  473 */               long mintimeindb = ReportUtilities.getMinTimeInDB(this.resID);
/*  474 */               long startTime = 0L;
/*  475 */               long endTime = 0L;
/*  476 */               long totalDuration = 0L;
/*      */               
/*      */               long currenttime;
/*  479 */               if (this.period.equals("4"))
/*      */               {
/*  481 */                 customstartTime = ReportUtilities.parseAndReturnTimeStamp(this.startdate);
/*  482 */                 customendTime = ReportUtilities.parseAndReturnTimeStamp(this.enddate);
/*  483 */                 if (mintimeindb > customstartTime)
/*      */                 {
/*  485 */                   startTime = mintimeindb;
/*      */                 }
/*  487 */                 else if (mintimeindb != 0L)
/*      */                 {
/*  489 */                   startTime = customstartTime;
/*      */                 }
/*  491 */                 currenttime = System.currentTimeMillis();
/*  492 */                 if (customendTime > currenttime)
/*      */                 {
/*  494 */                   endTime = currenttime;
/*      */                 }
/*      */                 else
/*      */                 {
/*  498 */                   endTime = customendTime;
/*      */                 }
/*  500 */                 totalDuration = endTime - startTime;
/*  501 */                 AMLog.debug(" ######### GetWLSGraph : Availabilty : Period @ " + this.period + " START TIME = " + startTime + " END TIME = " + endTime);
/*      */               }
/*  503 */               else if (this.period.equals("-1"))
/*      */               {
/*  505 */                 startTime = this.starttime;
/*  506 */                 endTime = this.endtime;
/*  507 */                 if (endTime > startTime)
/*      */                 {
/*  509 */                   totalDuration = endTime - startTime;
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*  514 */                   return null;
/*      */                 }
/*      */                 
/*      */               }
/*  518 */               else if (mintimeindb > time[0])
/*      */               {
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
/*  537 */                 startTime = mintimeindb;
/*  538 */                 endTime = time[1];
/*  539 */                 if (endTime > startTime)
/*      */                 {
/*  541 */                   totalDuration = endTime - startTime;
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*  546 */                   return null;
/*      */                 }
/*      */               }
/*  549 */               else if (mintimeindb != 0L)
/*      */               {
/*      */ 
/*  552 */                 startTime = time[0];
/*  553 */                 endTime = time[1];
/*  554 */                 totalDuration = endTime - startTime;
/*      */               }
/*      */               
/*      */ 
/*  558 */               String down = "";
/*  559 */               String up = "";
/*  560 */               String unmanage = "";
/*  561 */               String schedule = "";
/*  562 */               DBUtil db = new DBUtil();
/*  563 */               float downPercent = 0.0F;
/*  564 */               float upPercent = 0.0F;
/*  565 */               float unmanagePercent = 0.0F;
/*  566 */               float schedulePercent = 0.0F;
/*  567 */               ReportUtilities rep = new ReportUtilities();
/*  568 */               String DBVal = db.getGlobalConfigValueForMGAvailability();
/*  569 */               if (rep.isHAI(this.resID)) {
/*  570 */                 Properties p1 = ReportUtilities.getMonitorGroupAvailability(this.resID, this.period, startTime, endTime);
/*  571 */                 down = p1.getProperty("downtime");
/*  572 */                 up = p1.getProperty("uptime");
/*  573 */                 unmanage = p1.getProperty("unmanagedtime");
/*  574 */                 schedule = p1.getProperty("scheduledtime");
/*  575 */                 downPercent = Float.parseFloat(p1.getProperty("unavailable"));
/*  576 */                 upPercent = Float.parseFloat(p1.getProperty("available"));
/*  577 */                 unmanagePercent = Float.parseFloat(p1.getProperty("ServicesUnMgPercent"));
/*  578 */                 schedulePercent = Float.parseFloat(p1.getProperty("ServicesSchPercent"));
/*      */               }
/*      */               else
/*      */               {
/*      */                 try {
/*  583 */                   AMDataCorrectionUtil.checkFalseDowntime(Integer.parseInt(this.resID), this.type);
/*      */                 }
/*      */                 catch (Exception exc) {
/*  586 */                   exc.printStackTrace();
/*      */                 }
/*  588 */                 String query = "select RESID, TYPE, sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where RESID in (" + this.resID + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE order by TotalDownTime desc";
/*      */                 
/*  590 */                 set = AMConnectionPool.executeQueryStmt(query);
/*  591 */                 log.info("query " + query);
/*  592 */                 int typeID = -1;
/*  593 */                 long unmanagedtime = 0L;
/*  594 */                 long scheduledtime = 0L;
/*  595 */                 long totdowntime = 0L;
/*  596 */                 while (set.next())
/*      */                 {
/*  598 */                   typeID = set.getInt("TYPE");
/*  599 */                   if (typeID == 1)
/*      */                   {
/*  601 */                     totdowntime = set.getLong("TotalDownTime");
/*      */                   }
/*  603 */                   else if (typeID == 2)
/*      */                   {
/*  605 */                     unmanagedtime = set.getLong("TotalDownTime");
/*      */                   }
/*      */                   else
/*      */                   {
/*  609 */                     scheduledtime = set.getLong("TotalDownTime");
/*      */                   }
/*      */                 }
/*      */                 
/*  613 */                 long uptime = totalDuration - (totdowntime + unmanagedtime + scheduledtime);
/*  614 */                 down = ReportUtilities.format(totdowntime);
/*  615 */                 up = ReportUtilities.format(uptime);
/*  616 */                 unmanage = ReportUtilities.format(unmanagedtime);
/*  617 */                 schedule = ReportUtilities.format(scheduledtime);
/*  618 */                 upPercent = (float)uptime / (float)totalDuration * 100.0F;
/*  619 */                 downPercent = (float)totdowntime / (float)totalDuration * 100.0F;
/*  620 */                 unmanagePercent = (float)unmanagedtime / (float)totalDuration * 100.0F;
/*  621 */                 schedulePercent = (float)scheduledtime / (float)totalDuration * 100.0F;
/*  622 */                 if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */                 {
/*  624 */                   uptime = totalDuration - totdowntime;
/*  625 */                   down = ReportUtilities.format(totdowntime);
/*  626 */                   up = ReportUtilities.format(uptime);
/*  627 */                   unmanage = ReportUtilities.format(0L);
/*  628 */                   schedule = ReportUtilities.format(0L);
/*  629 */                   upPercent = (float)uptime / (float)totalDuration * 100.0F;
/*  630 */                   downPercent = (float)totdowntime / (float)totalDuration * 100.0F;
/*  631 */                   unmanagePercent = 0.0F;
/*  632 */                   schedulePercent = 0.0F;
/*      */                 }
/*      */                 
/*      */ 
/*  636 */                 String dateQuery = "select count(*) from AM_MO_DowntimeData where (uptime > " + System.currentTimeMillis() + " or downtime > " + System.currentTimeMillis() + ") and RESID in (" + this.resID + ")";
/*  637 */                 boolean dateInconsistent = false;
/*  638 */                 ResultSet dateSet = null;
/*      */                 try {
/*  640 */                   dateSet = AMConnectionPool.executeQueryStmt(dateQuery);
/*  641 */                   if ((dateSet.next()) && (dateSet.getInt(1) > 0)) {
/*  642 */                     dateInconsistent = true;
/*      */                   }
/*      */                 }
/*      */                 catch (Exception exc) {
/*  646 */                   exc.printStackTrace();
/*      */                 }
/*      */                 finally {}
/*      */                 
/*      */ 
/*      */ 
/*  652 */                 if ((upPercent < 0.0F) || (upPercent > 100.0F) || (downPercent < 0.0F) || (downPercent > 100.0F) || (uptime < 0L) || (totdowntime < 0L) || (dateInconsistent) || (unmanagePercent < 0.0F) || (unmanagePercent > 100.0F) || (schedulePercent < 0.0F) || (schedulePercent > 100.0F)) {
/*  653 */                   AMLog.debug("**********************************************************************************");
/*  654 */                   AMLog.debug("GetWLSGraph :  Resource ID: " + this.resID + " uptime: " + uptime + " downtime: " + totdowntime);
/*  655 */                   AMLog.debug("**********************************************************************************");
/*  656 */                   if (firstTime) {
/*  657 */                     firstTime = false;
/*  658 */                     AMDataCorrectionUtil.correctResourceForAvailability(Integer.parseInt(this.resID));
/*  659 */                     AMDataCorrectionUtil.correctOverlappingEntriesForAvailability(Integer.parseInt(this.resID));
/*  660 */                     AMDataCorrectionUtil.correctInconsistentDataForAvailability(Integer.parseInt(this.resID));
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
/*  741 */                     closeResultSet(set); continue;
/*      */                   }
/*  663 */                   throw new IllegalStateException("The data is inconsistent to plot the chart.");
/*      */                 }
/*      */                 
/*      */                 try
/*      */                 {
/*  668 */                   AMDataCorrectionUtil.checkFalseDowntime(Integer.parseInt(this.resID), this.type);
/*      */                 }
/*      */                 catch (Exception exc) {
/*  671 */                   exc.printStackTrace();
/*      */                 }
/*      */                 
/*  674 */                 if (totalDuration == 0L) {
/*  675 */                   throw new IllegalStateException(FormatUtil.getString("am.webclient.urlmonitor.nodata.message"));
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*  680 */               if (!down.startsWith("0")) {
/*  681 */                 ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, Math.round(downPercent * 1000.0F) / 1000.0F);
/*      */               }
/*  683 */               if (!up.startsWith("0")) {
/*  684 */                 ds.setValue(FormatUtil.getString("am.webclient.historydata.uptime.text") + " " + up, Math.round(upPercent * 1000.0F) / 1000.0F);
/*      */               }
/*      */               
/*  687 */               if (!unmanage.startsWith("0")) {
/*  688 */                 ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + unmanage, Math.round(unmanagePercent * 1000.0F) / 1000.0F);
/*      */               }
/*  690 */               if (!schedule.startsWith("0")) {
/*  691 */                 ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") + " " + schedule, Math.round(schedulePercent * 1000.0F) / 1000.0F);
/*      */               }
/*      */               
/*      */ 
/*  695 */               if (this.period.equals("0"))
/*      */               {
/*  697 */                 this.urls = new Hashtable(2);
/*  698 */                 this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + this.resID + "&period=0')");
/*  699 */                 this.urls.put(FormatUtil.getString("am.webclient.historydata.uptime.text") + " " + up, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + this.resID + "&period=0')");
/*  700 */                 this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + unmanage, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + this.resID + "&period=0')");
/*  701 */                 this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") + " " + schedule, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + this.resID + "&period=0')");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               }
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
/*      */             }
/*      */             catch (IllegalStateException ise)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  732 */               ise.printStackTrace();
/*  733 */               throw ise;
/*      */             }
/*      */             catch (Exception exp) {
/*  736 */               log.fatal("Exception while getting availability graph", exp);
/*  737 */               exp.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*  741 */               closeResultSet(set);
/*      */             }
/*  743 */             toReturn = ds;
/*  744 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  748 */       else if (this.type.equals("CONSOLEMGAVAILABILITY"))
/*      */       {
/*  750 */         DefaultPieDataset ds = new DefaultPieDataset();
/*  751 */         ResultSet set = null;
/*      */         try
/*      */         {
/*  754 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  755 */           long startTime = this.starttime;
/*  756 */           long endTime = this.endtime;
/*      */           
/*  758 */           String crTimeQuery = "select CREATIONTIME from AM_ManagedObject where RESOURCEID = " + this.resID;
/*  759 */           ResultSet rs = AMConnectionPool.executeQueryStmt(crTimeQuery);
/*  760 */           if (rs.next())
/*      */           {
/*  762 */             long creationTime = rs.getLong("CREATIONTIME");
/*  763 */             if (startTime < creationTime)
/*      */             {
/*  765 */               startTime = creationTime;
/*      */             }
/*      */           }
/*      */           
/*  769 */           String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where RESID in (" + this.resID + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID order by TotalDownTime desc";
/*  770 */           set = AMConnectionPool.executeQueryStmt(query);
/*  771 */           long totalDownTime = 0L;
/*  772 */           long totalDuration = 0L;
/*  773 */           totalDuration = endTime - startTime;
/*  774 */           if (set.next())
/*      */           {
/*  776 */             totalDownTime = set.getLong("TotalDownTime");
/*      */           }
/*  778 */           long uptime = totalDuration - totalDownTime;
/*  779 */           String down = ReportUtilities.format(totalDownTime);
/*  780 */           String up = ReportUtilities.format(uptime);
/*  781 */           float upPercent = (float)uptime / (float)totalDuration * 100.0F;
/*  782 */           float downPercent = (float)totalDownTime / (float)totalDuration * 100.0F;
/*  783 */           if (totalDuration < 0L)
/*      */           {
/*  785 */             throw new IllegalStateException(FormatUtil.getString("am.webclient.urlmonitor.nodata.message"));
/*      */           }
/*  787 */           ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, downPercent * 100.0F / 100.0F);
/*  788 */           ds.setValue(FormatUtil.getString("am.webclient.historydata.uptime.text") + " " + up, upPercent * 100.0F / 100.0F);
/*      */         }
/*      */         catch (IllegalStateException ise)
/*      */         {
/*  792 */           ise.printStackTrace();
/*  793 */           throw ise;
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/*  797 */           log.fatal("Exception while getting availability graph", exp);
/*  798 */           exp.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  802 */           closeResultSet(set);
/*      */         }
/*  804 */         toReturn = ds;
/*      */ 
/*      */       }
/*  807 */       else if (this.type.equals("CONSOLEWINDOW"))
/*      */       {
/*  809 */         DefaultPieDataset ds = new DefaultPieDataset();
/*      */         try
/*      */         {
/*  812 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  813 */           Vector resIds = new Vector();
/*  814 */           double downPercent = 0.0D;
/*  815 */           double upPercent = 0.0D;
/*  816 */           long timeToReduce = 0L;
/*  817 */           int totalNoOfMons = 0;
/*  818 */           long totalTime = 0L;
/*  819 */           long totalDownTime = 0L;
/*  820 */           long currenttime = System.currentTimeMillis();
/*      */           
/*  822 */           if (this.endtime > currenttime)
/*      */           {
/*  824 */             this.endtime = currenttime;
/*      */           }
/*      */           
/*  827 */           String resIdQuery = null;
/*  828 */           if (this.pieChartType.equals("allServersAvailability"))
/*      */           {
/*  830 */             resIdQuery = "select RESOURCEID,CREATIONTIME from AM_ManagedObject where TYPE in " + Constants.serverTypes;
/*      */           }
/*  832 */           else if (this.pieChartType.equals("allServersInMGAvailability"))
/*      */           {
/*  834 */             resIdQuery = "select RESOURCEID,CREATIONTIME from AM_ManagedObject, AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID='" + this.mgName + "' and AM_ManagedObject.TYPE in " + Constants.serverTypes;
/*      */           }
/*  836 */           else if (this.pieChartType.equals("allAppsAvailability"))
/*      */           {
/*  838 */             resIdQuery = "select RESOURCEID,CREATIONTIME from AM_ManagedObject where AM_ManagedObject.TYPE in " + Constants.resourceTypes + " and AM_ManagedObject.TYPE not in " + Constants.serverTypes;
/*      */           }
/*  840 */           else if (this.pieChartType.equals("allAppsInMGAvailability"))
/*      */           {
/*  842 */             resIdQuery = "select RESOURCEID,CREATIONTIME from AM_ManagedObject, AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID='" + this.mgName + "' and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " and AM_ManagedObject.TYPE not in " + Constants.serverTypes;
/*      */           }
/*  844 */           ResultSet res = AMConnectionPool.executeQueryStmt(resIdQuery);
/*  845 */           while (res.next())
/*      */           {
/*  847 */             this.isData = true;
/*  848 */             totalNoOfMons++;
/*  849 */             resIds.add(res.getString("RESOURCEID"));
/*  850 */             if (res.getLong("CREATIONTIME") > this.starttime)
/*      */             {
/*  852 */               timeToReduce += this.starttime - res.getLong("CREATIONTIME");
/*      */             }
/*      */           }
/*  855 */           res.close();
/*      */           
/*      */ 
/*  858 */           totalTime = (this.endtime - this.starttime) * totalNoOfMons;
/*  859 */           totalTime -= timeToReduce;
/*      */           
/*  861 */           Map downTimeDetailsMap = new HashMap();
/*  862 */           downTimeDetailsMap = ReportUtilities.getAllDowntimeDetails(resIds, this.starttime, this.endtime, null);
/*      */           
/*  864 */           int tempInt = 0;
/*  865 */           for (Iterator it = downTimeDetailsMap.keySet().iterator(); it.hasNext();)
/*      */           {
/*  867 */             String id = (String)it.next();
/*  868 */             if (tempInt == 0)
/*      */             {
/*  870 */               this.downIdsList = id;
/*  871 */               tempInt++;
/*      */             }
/*      */             else
/*      */             {
/*  875 */               this.downIdsList = (this.downIdsList + ',' + id);
/*      */             }
/*      */           }
/*      */           
/*  879 */           for (Iterator it = downTimeDetailsMap.values().iterator(); it.hasNext();)
/*      */           {
/*  881 */             ArrayList valuesList = (ArrayList)it.next();
/*  882 */             for (Object obj : valuesList)
/*      */             {
/*  884 */               Properties p = (Properties)obj;
/*  885 */               long downTime = Long.parseLong((String)p.get("TotalDownTime"));
/*  886 */               totalDownTime += downTime;
/*      */             }
/*      */           }
/*      */           
/*  890 */           downPercent = totalDownTime / totalTime * 100.0D;
/*  891 */           upPercent = 100.0D - downPercent;
/*      */           
/*  893 */           ds.setValue(FormatUtil.getString("am.webclient.downmonitors") + " " + downTimeDetailsMap.size(), (float)Math.round(downPercent * 100.0D) / 100.0F);
/*  894 */           ds.setValue(FormatUtil.getString("am.webclient.upmonitors") + " " + (totalNoOfMons - downTimeDetailsMap.size()), (float)Math.round(upPercent * 100.0D) / 100.0F);
/*      */           
/*  896 */           toReturn = ds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  909 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*  913 */       else if ((this.type.equals("WEBAPP")) || (this.type.equals("JBWEBAPP")) || (this.type.equals("JBEJBCOUNT")) || (this.type.equals("JBEJBREADYCOUNT")))
/*      */       {
/*  915 */         ArrayList poolNames = new ArrayList();
/*  916 */         Properties data = new Properties();
/*      */         
/*  918 */         if (this.type.equals("JBWEBAPP"))
/*      */         {
/*  920 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JB_WebAppData,AM_WAR,AM_EAR where AM_JB_WebAppData.resourceid=AM_WAR.warid and AM_WAR.parentid=AM_EAR.earid and AM_EAR.parentid=" + this.resID);
/*      */         }
/*  922 */         else if ((this.type.equals("JBEJBCOUNT")) || (this.type.equals("JBEJBREADYCOUNT")))
/*      */         {
/*  924 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JB_EJBData,AM_EJB,AM_EAR where ejbid=AM_JB_EJBData.RESOURCEID and  AM_EJB.PARENTID=EARID and AM_EAR.parentid=" + this.resID);
/*      */         }
/*      */         else
/*      */         {
/*  928 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_WebAppData,AM_WAR,AM_EAR where WARID= WEBAPPID and AM_WAR.PARENTID=EARID and AM_EAR.PARENTID=" + this.resID);
/*      */         }
/*  930 */         long oneHourBefore = this.collectionTime - 3600000L;
/*  931 */         DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  932 */         if (this.collectionTime != -1L)
/*      */         {
/*  934 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*  935 */           String query = null;
/*  936 */           if (this.type.equals("JBWEBAPP"))
/*      */           {
/*  938 */             query = "select contextpath,sum(requests) as total from AM_JB_WebAppData,AM_WAR,AM_EAR where AM_JB_WebAppData.resourceid=AM_WAR.warid and AM_WAR.parentid=AM_EAR.earid and AM_EAR.parentid=" + this.resID + " and  AM_JB_WebAppData.COLLECTIONTIME >=" + oneHourBefore + " and AM_JB_WebAppData.COLLECTIONTIME <=" + this.collectionTime + " group by contextpath order by total desc";
/*      */           }
/*  940 */           else if ((this.type.equals("JBEJBCOUNT")) || (this.type.equals("JBEJBREADYCOUNT")))
/*      */           {
/*  942 */             String columnname = "CreateCount";
/*  943 */             if (this.type.equals("JBEJBREADYCOUNT"))
/*      */             {
/*  945 */               columnname = "readycount";
/*      */             }
/*  947 */             query = "select AM_ManagedObject.DISPLAYNAME," + columnname + " as InstanceCount from AM_JB_EJBData,AM_ManagedObject,AM_EJB,AM_EAR where AM_JB_EJBData.COLLECTIONTIME =" + this.collectionTime + " and AM_JB_EJBData.RESOURCEID=AM_EJB.EJBID and AM_EAR.EARID=AM_EJB.PARENTID and  AM_EAR.PARENTID=" + this.resID + " and AM_ManagedObject.resourceid=AM_JB_EJBData.RESOURCEID and " + columnname + "<>-1 order by InstanceCount desc";
/*      */           }
/*      */           else
/*      */           {
/*  951 */             query = "select AM_ManagedObject.DISPLAYNAME,max(AM_WLS_WebAppData.OPENSESSIONCURRENTCOUNT) as total from AM_WLS_WebAppData,AM_ManagedObject,AM_WAR,AM_EAR where AM_WLS_WebAppData.COLLECTIONTIME >=" + oneHourBefore + " and AM_WLS_WebAppData.COLLECTIONTIME <=" + this.collectionTime + " and AM_WLS_WebAppData.WEBAPPID=AM_WAR.WARID and AM_WAR.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID + " and AM_WAR.WARID=AM_ManagedObject.RESOURCEID group by AM_WLS_WebAppData.WEBAPPID,AM_ManagedObject.DISPLAYNAME order by total desc";
/*      */           }
/*      */           
/*  954 */           ResultSet set = null;
/*  955 */           log.info("Query is query---------" + query);
/*      */           try
/*      */           {
/*  958 */             set = AMConnectionPool.executeQueryStmt(query);
/*      */             
/*  960 */             int i = 0;
/*  961 */             while (set.next())
/*      */             {
/*  963 */               if (i >= 5)
/*      */                 break;
/*  965 */               result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/*  966 */               i++;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*  977 */               if (set != null) {
/*  978 */                 set.close();
/*      */               }
/*      */             } catch (Exception e) {
/*  981 */               e.printStackTrace();
/*      */             }
/*      */             
/*  984 */             toReturn = result;
/*      */           }
/*      */           catch (Exception exp)
/*      */           {
/*  970 */             log.fatal("Error in produce dataset method");
/*  971 */             exp.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/*  977 */               if (set != null) {
/*  978 */                 set.close();
/*      */               }
/*      */             } catch (Exception e) {
/*  981 */               e.printStackTrace();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  988 */           System.err.println("No Data collected ");
/*      */         }
/*      */       } else { DefaultCategoryDataset result;
/*  991 */         if (this.type.equals("SERVLET_INVCOUNT"))
/*      */         {
/*  993 */           result = new DefaultCategoryDataset();
/*  994 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + this.resID);
/*  995 */           if (this.collectionTime != -1L)
/*      */           {
/*  997 */             AMConnectionPool cp = AMConnectionPool.getInstance();
/*  998 */             String query = "select SERVLETNAME,INVOCATIONTOTALCOUNT from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + this.resID + " and COLLECTIONTIME=" + this.collectionTime + " order by INVOCATIONTOTALCOUNT desc";
/*  999 */             ResultSet set = null;
/*      */             try
/*      */             {
/* 1002 */               set = AMConnectionPool.executeQueryStmt(query);
/*      */               
/* 1004 */               int i = 0;
/* 1005 */               while (set.next())
/*      */               {
/*      */ 
/* 1008 */                 if (i >= 5)
/*      */                   break;
/* 1010 */                 result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/* 1011 */                 i++;
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 1021 */                 if (set != null) {
/* 1022 */                   set.close();
/*      */                 }
/*      */               } catch (Exception e) {
/* 1025 */                 e.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/* 1029 */               toReturn = result;
/*      */             }
/*      */             catch (Exception exp)
/*      */             {
/* 1015 */               exp.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 1021 */                 if (set != null) {
/* 1022 */                   set.close();
/*      */                 }
/*      */               } catch (Exception e) {
/* 1025 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */         } else {
/*      */           DefaultCategoryDataset result;
/* 1031 */           if (this.type.equals("SERVLET_EXECHIGH"))
/*      */           {
/* 1033 */             result = new DefaultCategoryDataset();
/* 1034 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + this.resID);
/* 1035 */             if (this.collectionTime != -1L)
/*      */             {
/* 1037 */               AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1038 */               String query = "select SERVLETNAME,EXECUTIONTIMEHIGH from AM_WLS_ServletData,AM_Servlet where AM_WLS_ServletData.ID=AM_Servlet.ID and AM_Servlet.PARENTID=" + this.resID + " and COLLECTIONTIME=" + this.collectionTime + " order by EXECUTIONTIMEHIGH desc";
/* 1039 */               ResultSet set = null;
/*      */               try
/*      */               {
/* 1042 */                 set = AMConnectionPool.executeQueryStmt(query);
/*      */                 
/* 1044 */                 int i = 0;
/* 1045 */                 while (set.next())
/*      */                 {
/* 1047 */                   if (i >= 5)
/*      */                     break;
/* 1049 */                   result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/* 1050 */                   i++;
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 try
/*      */                 {
/* 1060 */                   if (set != null) {
/* 1061 */                     set.close();
/*      */                   }
/*      */                 } catch (Exception e) {
/* 1064 */                   e.printStackTrace();
/*      */                 }
/*      */                 
/*      */ 
/* 1068 */                 toReturn = result;
/*      */               }
/*      */               catch (Exception exp)
/*      */               {
/* 1054 */                 exp.printStackTrace();
/*      */               }
/*      */               finally
/*      */               {
/*      */                 try
/*      */                 {
/* 1060 */                   if (set != null) {
/* 1061 */                     set.close();
/*      */                   }
/*      */                 } catch (Exception e) {
/* 1064 */                   e.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           } else {
/*      */             DefaultCategoryDataset result;
/* 1070 */             if (this.type.equals("EJB_POOL"))
/*      */             {
/* 1072 */               result = new DefaultCategoryDataset();
/* 1073 */               this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_EJBTxData,AM_EJB,AM_EAR where AM_EJB.EJBID=AM_EJBTxData.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID);
/* 1074 */               long oneHourBefore = this.collectionTime - 3600000L;
/* 1075 */               if (this.collectionTime != -1L)
/*      */               {
/* 1077 */                 AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1078 */                 String query = "select max(AM_ManagedObject.DISPLAYNAME) as DISPLAYNAME,max(BEANSINUSECOUNT) as beansinuse from AM_EJBPoolData,AM_EJB,AM_EAR,AM_ManagedObject where AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID + " and AM_ManagedObject.RESOURCEID=AM_EJBPoolData.EJBID and AM_EJB.EJBID=AM_EJBPoolData.EJBID and AM_EJBPoolData.COLLECTIONTIME >=" + oneHourBefore + " and AM_EJBPoolData.COLLECTIONTIME <= " + this.collectionTime + " group by AM_EJBPoolData.EJBID,AM_ManagedObject.DISPLAYNAME order by beansinuse desc";
/* 1079 */                 ResultSet set = null;
/*      */                 try
/*      */                 {
/* 1082 */                   set = AMConnectionPool.executeQueryStmt(query);
/*      */                   
/* 1084 */                   int i = 0;
/* 1085 */                   while (set.next())
/*      */                   {
/* 1087 */                     if (i >= 5)
/*      */                       break;
/* 1089 */                     result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/* 1090 */                     i++;
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   try
/*      */                   {
/* 1100 */                     if (set != null) {
/* 1101 */                       set.close();
/*      */                     }
/*      */                   } catch (Exception e) {
/* 1104 */                     e.printStackTrace();
/*      */                   }
/*      */                   
/*      */ 
/* 1108 */                   toReturn = result;
/*      */                 }
/*      */                 catch (Exception exp)
/*      */                 {
/* 1094 */                   exp.printStackTrace();
/*      */                 }
/*      */                 finally
/*      */                 {
/*      */                   try
/*      */                   {
/* 1100 */                     if (set != null) {
/* 1101 */                       set.close();
/*      */                     }
/*      */                   } catch (Exception e) {
/* 1104 */                     e.printStackTrace();
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } else {
/*      */               DefaultCategoryDataset result;
/* 1110 */               if (this.type.equals("EJB_CACHE"))
/*      */               {
/* 1112 */                 result = new DefaultCategoryDataset();
/* 1113 */                 this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_EJBTxData,AM_EJB,AM_EAR where AM_EJB.EJBID=AM_EJBTxData.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID);
/* 1114 */                 long oneHourBefore = this.collectionTime - 3600000L;
/* 1115 */                 if (this.collectionTime != -1L)
/*      */                 {
/* 1117 */                   AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1118 */                   String query = "select max(AM_ManagedObject.DISPLAYNAME) as DISPLAYNAME,max(CACHEDBEANSCURRENTCOUNT) as beansinuse from AM_EJBCacheData,AM_EJB,AM_EAR,AM_ManagedObject where AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID + " and AM_ManagedObject.RESOURCEID=AM_EJBCacheData.EJBID and AM_EJB.EJBID=AM_EJBCacheData.EJBID and AM_EJBCacheData.COLLECTIONTIME >=" + oneHourBefore + " and AM_EJBCacheData.COLLECTIONTIME <=" + this.collectionTime + " group by AM_EJBCacheData.EJBID,AM_ManagedObject.DISPLAYNAME order by beansinuse desc";
/* 1119 */                   ResultSet set = null;
/*      */                   try
/*      */                   {
/* 1122 */                     set = AMConnectionPool.executeQueryStmt(query);
/*      */                     
/* 1124 */                     int i = 0;
/* 1125 */                     while (set.next())
/*      */                     {
/* 1127 */                       if (i >= 5)
/*      */                         break;
/* 1129 */                       result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/* 1130 */                       i++;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     try
/*      */                     {
/* 1140 */                       if (set != null) {
/* 1141 */                         set.close();
/*      */                       }
/*      */                     } catch (Exception e) {
/* 1144 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/*      */ 
/* 1148 */                     toReturn = result;
/*      */                   }
/*      */                   catch (Exception exp)
/*      */                   {
/* 1134 */                     exp.printStackTrace();
/*      */                   }
/*      */                   finally
/*      */                   {
/*      */                     try
/*      */                     {
/* 1140 */                       if (set != null) {
/* 1141 */                         set.close();
/*      */                       }
/*      */                     } catch (Exception e) {
/* 1144 */                       e.printStackTrace();
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               } else {
/*      */                 DefaultCategoryDataset result;
/* 1150 */                 if (this.type.equals("EJB_TX"))
/*      */                 {
/* 1152 */                   result = new DefaultCategoryDataset();
/* 1153 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_EJBTxData,AM_EJB,AM_EAR where AM_EJB.EJBID=AM_EJBTxData.EJBID and AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID);
/* 1154 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1155 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1157 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1158 */                     String query = "select max(AM_ManagedObject.DISPLAYNAME) as DISPLAYNAME,max(TRANSACTIONSTIMEDOUTTOTALCOUNT) as bean from AM_EJBTxData,AM_EJB,AM_EAR,AM_ManagedObject where AM_EJB.PARENTID=AM_EAR.EARID and AM_EAR.PARENTID=" + this.resID + " and AM_ManagedObject.RESOURCEID=AM_EJBTxData.EJBID and AM_EJB.EJBID=AM_EJBTxData.EJBID and AM_EJBTxData.COLLECTIONTIME >=" + oneHourBefore + " and AM_EJBTxData.COLLECTIONTIME <=" + this.collectionTime + " group by AM_EJBTxData.EJBID,AM_ManagedObject.DISPLAYNAME order by bean desc";
/* 1159 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1162 */                       set = AMConnectionPool.executeQueryStmt(query);
/*      */                       
/* 1164 */                       int i = 0;
/* 1165 */                       while (set.next())
/*      */                       {
/* 1167 */                         if (i >= 5)
/*      */                           break;
/* 1169 */                         result.addValue(new Integer(set.getInt(2)), "", set.getString(1));
/* 1170 */                         i++;
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1180 */                         if (set != null) {
/* 1181 */                           set.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1184 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1188 */                       toReturn = result;
/*      */                     }
/*      */                     catch (Exception exp)
/*      */                     {
/* 1174 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1180 */                         if (set != null) {
/* 1181 */                           set.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1184 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */                     }
/*      */                   }
/*      */                 }
/* 1190 */                 else if (this.type.equals("THREAD_PENDING"))
/*      */                 {
/* 1192 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_ThreadData,AM_Thread  where AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.PARENTID=" + this.resID);
/*      */                   
/* 1194 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1195 */                   ResultSet rs = null;
/* 1196 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1198 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1199 */                     String query = "select RESOURCENAME,PENDINGREQUESTCURRENTCOUNT,COLLECTIONTIME from AM_WLS_ThreadData,AM_ManagedObject,AM_Thread where  COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.PARENTID =" + this.resID + " and AM_Thread.ID=AM_ManagedObject.RESOURCEID";
/*      */                     
/* 1201 */                     ArrayList poolNames = new ArrayList();
/* 1202 */                     Properties data = new Properties();
/*      */                     try
/*      */                     {
/* 1205 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1206 */                       while (rs.next())
/*      */                       {
/* 1208 */                         String poolName = rs.getString(1);
/* 1209 */                         if (!poolNames.contains(poolName))
/*      */                         {
/* 1211 */                           poolNames.add(poolName);
/* 1212 */                           Properties props = new Properties();
/* 1213 */                           data.put(poolName, props);
/*      */                         }
/* 1215 */                         Properties temp = (Properties)data.get(poolName);
/* 1216 */                         Date d = new Date(rs.getLong(3));
/* 1217 */                         temp.put(d, new Long(rs.getLong(2)));
/* 1218 */                         data.put(poolName, temp);
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1229 */                         if (rs != null) {
/* 1230 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1233 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1237 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1223 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1229 */                         if (rs != null) {
/* 1230 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1233 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1238 */                     int size = poolNames.size();
/* 1239 */                     for (int i = 0; i < size; i++)
/*      */                     {
/* 1241 */                       TimeSeries ts = new TimeSeries(FormatUtil.getString((String)poolNames.get(i)), Minute.class);
/* 1242 */                       Properties temp = (Properties)data.get(poolNames.get(i));
/* 1243 */                       Enumeration keys = temp.keys();
/* 1244 */                       while (keys.hasMoreElements())
/*      */                       {
/* 1246 */                         Date d = (Date)keys.nextElement();
/* 1247 */                         ts.addOrUpdate(new Minute(d), ((Long)temp.get(d)).longValue());
/*      */                       }
/* 1249 */                       col.addSeries(ts);
/*      */                     }
/* 1251 */                     int[] arr = new int[size];
/* 1252 */                     for (int i = 0; i < size; i++)
/*      */                     {
/* 1254 */                       arr[i] = i;
/*      */                     }
/* 1256 */                     toReturn = new SubSeriesDataset(col, arr);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1260 */                     System.err.println("No Data collected ");
/*      */                   }
/*      */                   
/*      */                 }
/* 1264 */                 else if (this.type.equals("THREAD_USAGE"))
/*      */                 {
/* 1266 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1267 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_ThreadData,AM_Thread  where AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.PARENTID=" + this.resID);
/* 1268 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1269 */                   DefaultIntervalCategoryDataset ds = null;
/* 1270 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1272 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1273 */                     String query = "select AM_ManagedObject.RESOURCENAME,(avg( CASE WHEN EXECUTETHREADS != 0 THEN (EXECUTETHREADS-EXECUTETHREADCURRENTIDLECOUNT)/EXECUTETHREADS ELSE EXECUTETHREADS END )) * 100 as util from AM_WLS_ThreadData,AM_ManagedObject,AM_Thread where  COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and AM_WLS_ThreadData.ID=AM_Thread.ID and AM_Thread.PARENTID=" + this.resID + " and AM_Thread.ID=AM_ManagedObject.RESOURCEID  group by RESOURCENAME order by util desc";
/* 1274 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1277 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1278 */                       while (set.next())
/*      */                       {
/* 1280 */                         result.addValue(new Double(set.getDouble(2)), "", set.getString(1));
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 1284 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1290 */                         if (set != null) {
/* 1291 */                           set.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1294 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 1300 */                   System.err.println("No Data collected ");
/*      */                   
/* 1302 */                   toReturn = result;
/*      */                 }
/* 1304 */                 else if (this.type.equals("JDBC_USAGE"))
/*      */                 {
/* 1306 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1307 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_JDBCData,AM_JDBC  where AM_WLS_JDBCData.ID=AM_JDBC.ID and AM_JDBC.PARENTID=" + this.resID);
/* 1308 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1309 */                   DefaultIntervalCategoryDataset ds = null;
/* 1310 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1312 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1313 */                     String query = "select RESOURCENAME,(avg(ACTIVECONNECTIONSCURRENTCOUNT/CONNECTIONSTOTALCOUNT))*100  as util from AM_WLS_JDBCData,AM_ManagedObject,AM_JDBC where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and AM_JDBC.ID=AM_WLS_JDBCData.ID and AM_JDBC.ID=AM_ManagedObject.RESOURCEID and AM_JDBC.PARENTID=" + this.resID + " group by AM_WLS_JDBCData.ID,RESOURCENAME order by util desc";
/* 1314 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1317 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1318 */                       while (set.next())
/*      */                       {
/* 1320 */                         result.addValue(new Double(set.getDouble(2)), "", set.getString(1));
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 1324 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1330 */                         if (set != null) {
/* 1331 */                           set.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1334 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 1340 */                   System.err.println("No Data collected ");
/*      */                   
/* 1342 */                   toReturn = result;
/*      */                 }
/* 1344 */                 else if (this.type.equals("JBOSS_JDBC_ConnectionsInUse"))
/*      */                 {
/* 1346 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JB_JDBCData,AM_JDBC  where AM_JB_JDBCData.ID=AM_JDBC.ID and AM_JDBC.PARENTID=" + this.resID);
/* 1347 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1348 */                   DataCollectionDBUtil mo = new DataCollectionDBUtil();
/* 1349 */                   ResultSet rs = null;
/* 1350 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1351 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1353 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1354 */                     String query = "select AM_ManagedObject.resourceid,AM_ManagedObject.displayname,AM_JB_JDBCData.* from AM_ManagedObject,AM_JDBC,AM_JB_JDBCData where AM_ManagedObject.resourceid=AM_JDBC.ID and AM_JB_JDBCData.id=AM_JDBC.ID  and collectiontime=" + this.collectionTime + " and PARENTID=" + this.resID;
/* 1355 */                     ArrayList rows = mo.getRows(query);
/* 1356 */                     for (int i = 0; i < rows.size(); i++)
/*      */                     {
/* 1358 */                       ArrayList row = (ArrayList)rows.get(i);
/* 1359 */                       result.addValue(new Integer((String)row.get(8)), "", (String)row.get(1));
/*      */                     }
/*      */                   }
/* 1362 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 1365 */                 else if (this.type.equals("JBOSS_JDBC_MaxConnectionsInUseCount"))
/*      */                 {
/* 1367 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_JB_JDBCData,AM_JDBC  where AM_JB_JDBCData.ID=AM_JDBC.ID and AM_JDBC.PARENTID=" + this.resID);
/* 1368 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1369 */                   DataCollectionDBUtil mo = new DataCollectionDBUtil();
/* 1370 */                   ResultSet rs = null;
/* 1371 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1372 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1374 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1375 */                     String query = "select AM_ManagedObject.resourceid,AM_ManagedObject.displayname,AM_JB_JDBCData.MAXCONNECTIONSINUSECOUNT from AM_ManagedObject,AM_JDBC,AM_JB_JDBCData where AM_ManagedObject.resourceid=AM_JDBC.ID and AM_JB_JDBCData.id=AM_JDBC.ID and AM_JB_JDBCData.MaxConnectionsInUseCount<>-1 and collectiontime=" + this.collectionTime + " and PARENTID=" + this.resID;
/* 1376 */                     ArrayList rows = mo.getRows(query);
/* 1377 */                     for (int i = 0; i < rows.size(); i++)
/*      */                     {
/* 1379 */                       ArrayList row = (ArrayList)rows.get(i);
/* 1380 */                       result.addValue(new Integer((String)row.get(2)), "", (String)row.get(1));
/*      */                     }
/*      */                   }
/* 1383 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 1386 */                 else if (this.type.equals("JDBC_PERF"))
/*      */                 {
/* 1388 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_WLS_JDBCData,AM_JDBC  where AM_WLS_JDBCData.ID=AM_JDBC.ID and AM_JDBC.PARENTID=" + this.resID);
/*      */                   
/* 1390 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1391 */                   ResultSet rs = null;
/* 1392 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1394 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1395 */                     String query = "select RESOURCENAME,WAITINGFORCONNECTIONCURRENTCOUNT,COLLECTIONTIME from AM_WLS_JDBCData,AM_ManagedObject,AM_JDBC where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and AM_JDBC.ID=AM_WLS_JDBCData.ID and AM_JDBC.ID=AM_ManagedObject.RESOURCEID and AM_JDBC.PARENTID=" + this.resID;
/*      */                     
/* 1397 */                     ArrayList poolNames = new ArrayList();
/* 1398 */                     Properties data = new Properties();
/*      */                     try
/*      */                     {
/* 1401 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1402 */                       while (rs.next())
/*      */                       {
/* 1404 */                         String poolName = rs.getString(1);
/* 1405 */                         if (!poolNames.contains(poolName))
/*      */                         {
/* 1407 */                           poolNames.add(poolName);
/* 1408 */                           Properties props = new Properties();
/* 1409 */                           data.put(poolName, props);
/*      */                         }
/* 1411 */                         Properties temp = (Properties)data.get(poolName);
/* 1412 */                         Date d = new Date(rs.getLong(3));
/* 1413 */                         temp.put(d, new Long(rs.getLong(2)));
/* 1414 */                         data.put(poolName, temp);
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1425 */                         if (rs != null) {
/* 1426 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1429 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1433 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1419 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1425 */                         if (rs != null) {
/* 1426 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1429 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1434 */                     int size = poolNames.size();
/* 1435 */                     for (int i = 0; i < size; i++)
/*      */                     {
/* 1437 */                       TimeSeries ts = new TimeSeries(FormatUtil.getString((String)poolNames.get(i)), Minute.class);
/* 1438 */                       Properties temp = (Properties)data.get(poolNames.get(i));
/* 1439 */                       Enumeration keys = temp.keys();
/* 1440 */                       while (keys.hasMoreElements())
/*      */                       {
/* 1442 */                         Date d = (Date)keys.nextElement();
/* 1443 */                         ts.addOrUpdate(new Minute(d), ((Long)temp.get(d)).longValue());
/*      */                       }
/* 1445 */                       col.addSeries(ts);
/*      */                     }
/* 1447 */                     int[] arr = new int[size];
/* 1448 */                     for (int i = 0; i < size; i++)
/*      */                     {
/* 1450 */                       arr[i] = i;
/*      */                     }
/* 1452 */                     toReturn = new SubSeriesDataset(col, arr);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1456 */                     System.err.println("No Data collected ");
/*      */                   }
/*      */                   
/*      */                 }
/* 1460 */                 else if ((this.type.equals("RESPONSETIME")) || (this.type.equals("QRESPONSETIME")) || (this.type.equals("TOTALRESPONSETIME")))
/*      */                 {
/* 1462 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + this.resID);
/*      */                   
/* 1464 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1465 */                   ResultSet rs = null;
/* 1466 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1468 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1469 */                     String query = "select RESPONSETIME,COLLECTIONTIME from AM_ManagedObjectData where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESID=" + this.resID + " and RESPONSETIME<>-1";
/* 1470 */                     if (this.type.equals("QRESPONSETIME"))
/*      */                     {
/* 1472 */                       query = "select RESPONSETIME,COLLECTIONTIME from AM_QEngineScript_Responsetime where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESOURCEID=" + this.resID + " and RESPONSETIME<>-1";
/*      */                     }
/* 1474 */                     if (this.type.equals("TOTALRESPONSETIME"))
/*      */                     {
/* 1476 */                       long sixHourBefore = this.collectionTime - 21600000L;
/* 1477 */                       query = "select RESPONSETIME,COLLECTIONTIME from AM_ManagedObjectData where COLLECTIONTIME >=" + sixHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESID=" + this.resID + " and RESPONSETIME<>-1";
/*      */                     }
/* 1479 */                     TimeSeries ts = new TimeSeries(FormatUtil.getString("Response Time"), Minute.class);
/*      */                     try
/*      */                     {
/* 1482 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1483 */                       while (rs.next())
/*      */                       {
/* 1485 */                         Date d = new Date(rs.getLong(2));
/* 1486 */                         ts.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1497 */                         if (rs != null) {
/* 1498 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1501 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1505 */                       col = new TimeSeriesCollection(ts);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1491 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1497 */                         if (rs != null) {
/* 1498 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1501 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1506 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1510 */                     System.err.println("No Data collected FOR Showing ResponseTime");
/*      */ 
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/* 1516 */                 else if (this.type.equals("URLCONTENT"))
/*      */                 {
/* 1518 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_URLData where URLID=" + this.resID);
/*      */                   
/* 1520 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1521 */                   ResultSet rs = null;
/* 1522 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1524 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1525 */                     String query = "select CONTENTLENGTH,COLLECTIONTIME from AM_URLData where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and URLID=" + this.resID;
/*      */                     
/* 1527 */                     TimeSeries ts = new TimeSeries(FormatUtil.getString("Response Time"), Minute.class);
/*      */                     try
/*      */                     {
/* 1530 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1531 */                       while (rs.next())
/*      */                       {
/* 1533 */                         Date d = new Date(rs.getLong(2));
/* 1534 */                         ts.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1545 */                         if (rs != null) {
/* 1546 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1549 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1553 */                       col = new TimeSeriesCollection(ts);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1539 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1545 */                         if (rs != null) {
/* 1546 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1549 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1554 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1558 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                   
/*      */                 }
/* 1562 */                 else if ((this.type.equals("URLRESPONSESPLITUP")) || (this.type.equals("URLTOTALRESPONSESPLITUP")))
/*      */                 {
/* 1564 */                   TimeTableXYDataset timetablexydataset = new TimeTableXYDataset();
/* 1565 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_URLData_EXT where URLID=" + this.resID);
/*      */                   
/* 1567 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1568 */                   ResultSet rs = null;ResultSet rs1 = null;
/* 1569 */                   int i = 0;
/*      */                   
/* 1571 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1573 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1574 */                     String query = "select DNSTIME,CONNECTIONTIME,FIRSTBYTETIME,LASTBYTETIME,COLLECTIONTIME from AM_URLData_EXT where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and URLID=" + this.resID + " order by COLLECTIONTIME";
/* 1575 */                     if (this.type.equals("URLTOTALRESPONSESPLITUP"))
/*      */                     {
/* 1577 */                       long sixHourBefore = this.collectionTime - 21600000L;
/* 1578 */                       query = "select DNSTIME,CONNECTIONTIME,FIRSTBYTETIME,LASTBYTETIME,COLLECTIONTIME from AM_URLData_EXT where COLLECTIONTIME >=" + sixHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and URLID=" + this.resID + " order by COLLECTIONTIME";
/*      */                     }
/*      */                     try
/*      */                     {
/* 1582 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1583 */                       while (rs.next())
/*      */                       {
/* 1585 */                         if (rs.getDouble(1) >= 0.0D)
/*      */                         {
/* 1587 */                           timetablexydataset.add(new Minute(new Date(rs.getLong(5))), rs.getDouble(1), FormatUtil.getString("am.webclient.urlmonitor.dns.text") + " " + FormatUtil.getString("Time"));
/*      */                         }
/* 1589 */                         if (rs.getDouble(2) >= 0.0D)
/*      */                         {
/* 1591 */                           timetablexydataset.add(new Minute(new Date(rs.getLong(5))), rs.getDouble(2), FormatUtil.getString("am.webclient.urlmonitor.conn.text") + " " + FormatUtil.getString("Time"));
/*      */                         }
/* 1593 */                         if (rs.getDouble(3) >= 0.0D)
/*      */                         {
/* 1595 */                           timetablexydataset.add(new Minute(new Date(rs.getLong(5))), rs.getDouble(3), FormatUtil.getString("am.webclient.urlmonitor.fbt.text") + " " + FormatUtil.getString("Time"));
/*      */                         }
/* 1597 */                         if (rs.getDouble(4) >= 0.0D)
/*      */                         {
/* 1599 */                           timetablexydataset.add(new Minute(new Date(rs.getLong(5))), rs.getDouble(4) - rs.getDouble(3), FormatUtil.getString("am.webclient.urlmonitor.lbt.text") + " " + FormatUtil.getString("Time"));
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1605 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/* 1609 */                       if (rs != null)
/*      */                       {
/* 1611 */                         AMConnectionPool.closeResultSet(rs);
/*      */                       }
/* 1613 */                       if (rs1 != null)
/*      */                       {
/* 1615 */                         AMConnectionPool.closeResultSet(rs1);
/*      */                       }
/*      */                     }
/* 1618 */                     toReturn = timetablexydataset;
/*      */                   }
/*      */                   else
/*      */                   {
/* 1622 */                     log.error("No Data collected FOR Showing Response Time Split Up");
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/* 1627 */                 else if (this.type.equals("PHPPAGEFAULT"))
/*      */                 {
/* 1629 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + this.resID);
/* 1630 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1631 */                   ResultSet rs = null;
/* 1632 */                   System.out.println("Inside the phppagefault graph page..");
/* 1633 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1635 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1636 */                     String query = "select PAGEFAULTS,COLLECTIONTIME from AM_PHPDETAILS where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESOURCEID=" + this.resID + " and PAGEFAULTS<>-1";
/* 1637 */                     TimeSeries ts = new TimeSeries(FormatUtil.getString("Page Faults"), Minute.class);
/*      */                     try
/*      */                     {
/* 1640 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1641 */                       while (rs.next())
/*      */                       {
/* 1643 */                         Date d = new Date(rs.getLong(2));
/* 1644 */                         ts.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1656 */                         if (rs != null) {
/* 1657 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1660 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1664 */                       col = new TimeSeriesCollection(ts);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1649 */                       System.out.println("AM I inside the pagefault exception");
/* 1650 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1656 */                         if (rs != null) {
/* 1657 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1660 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1665 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1669 */                     System.err.println("No Data collected FOR Showing Page Faults");
/*      */                   }
/*      */                   
/*      */                 }
/* 1673 */                 else if ((this.type.equals("REQPERMIN")) || (this.type.equals("BUSYSERVERS")) || (this.type.equals("BYTESPERSEC")))
/*      */                 {
/*      */ 
/* 1676 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_ApacheDetails where RESID=" + this.resID);
/* 1677 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1678 */                   ResultSet rs = null;
/* 1679 */                   String query = null;
/* 1680 */                   TimeSeries ts = null;
/* 1681 */                   if (this.collectionTime != -1L)
/*      */                   {
/*      */ 
/*      */ 
/* 1685 */                     String query1 = DBQueryUtil.getTopNValues("select enablestatus from AM_ApacheDetails where resid=" + this.resID + " order by collectiontime desc", "1");
/* 1686 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1687 */                     ResultSet rs1 = null;
/* 1688 */                     String enablestatus = null;
/*      */                     try
/*      */                     {
/* 1691 */                       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 1692 */                       if (rs1.next())
/*      */                       {
/* 1694 */                         enablestatus = rs1.getString(1);
/*      */                       }
/* 1696 */                       rs1.close();
/*      */ 
/*      */                     }
/*      */                     catch (SQLException sqlexc)
/*      */                     {
/* 1701 */                       sqlexc.printStackTrace();
/*      */                     }
/* 1703 */                     if (this.type.equals("REQPERMIN"))
/*      */                     {
/* 1705 */                       if (enablestatus != null)
/*      */                       {
/* 1707 */                         if (enablestatus.equals("0"))
/*      */                         {
/* 1709 */                           return null;
/*      */                         }
/*      */                       }
/* 1712 */                       query = "select REQPERMIN,COLLECTIONTIME from AM_ApacheDetails where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESID=" + this.resID + " and REQPERMIN<>-1 and REQPERMIN is not NULL";
/* 1713 */                       ts = new TimeSeries(FormatUtil.getString("Request/second"), Minute.class);
/*      */                     }
/* 1715 */                     else if (this.type.equals("BUSYSERVERS"))
/*      */                     {
/* 1717 */                       query = "select BUSYSERVERS,COLLECTIONTIME from AM_ApacheDetails where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESID=" + this.resID + " and BUSYSERVERS<>-1 and BUSYSERVERS is not NULL";
/* 1718 */                       ts = new TimeSeries(FormatUtil.getString("Busy Workers"), Minute.class);
/*      */                     }
/* 1720 */                     else if (this.type.equals("BYTESPERSEC"))
/*      */                     {
/* 1722 */                       if (enablestatus != null)
/*      */                       {
/* 1724 */                         if (enablestatus.equals("0"))
/*      */                         {
/* 1726 */                           return null;
/*      */                         }
/*      */                       }
/* 1729 */                       query = "select BYTESPERSEC,COLLECTIONTIME from AM_ApacheDetails where COLLECTIONTIME >=" + oneHourBefore + " and COLLECTIONTIME <=" + this.collectionTime + " and RESID=" + this.resID + " and BYTESPERSEC<>-1 and BYTESPERSEC is not NULL";
/* 1730 */                       ts = new TimeSeries(FormatUtil.getString("Bytes/second"), Minute.class);
/*      */                     }
/*      */                     
/*      */                     try
/*      */                     {
/* 1735 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1736 */                       while (rs.next())
/*      */                       {
/* 1738 */                         Date d = new Date(rs.getLong(2));
/* 1739 */                         ts.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1750 */                         if (rs != null) {
/* 1751 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1754 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1758 */                       col = new TimeSeriesCollection(ts);
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1744 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1750 */                         if (rs != null) {
/* 1751 */                           rs.close();
/*      */                         }
/*      */                       } catch (Exception e) {
/* 1754 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1759 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1763 */                     System.err.println("No Data collected FOR Showing ApacheData");
/*      */                   }
/*      */                 }
/* 1766 */                 else if (this.type.equals("IIS_BYTESSENTPERSECOND"))
/*      */                 {
/* 1768 */                   ResultSet rs = null;
/* 1769 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1771 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1772 */                     String query = "select BYTESSENTPERSEC,BYTESRECEIVEDPERSEC,BYTESTOTALPERSEC,COLLECTIONTIME from AM_IIS_TRAFFIC_DATA where BYTESSENTPERSEC>=0 and BYTESTOTALPERSEC>=0 and BYTESTOTALPERSEC>=0 and RESOURCEID=" + this.resID;
/* 1773 */                     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.iis.bytessentpersec.text"), Minute.class);
/* 1774 */                     TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.iis.bytesreceivedpersec.text"), Minute.class);
/* 1775 */                     TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.iis.bytestransferredpersec.text"), Minute.class);
/*      */                     try
/*      */                     {
/* 1778 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 1779 */                       while (rs.next())
/*      */                       {
/* 1781 */                         Date d = new Date(rs.getLong(4));
/* 1782 */                         ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/* 1783 */                         ts2.addOrUpdate(new Minute(d), new Long(rs.getLong(2)));
/* 1784 */                         ts3.addOrUpdate(new Minute(d), new Long(rs.getLong(3)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 1796 */                         if (rs != null) {
/* 1797 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 1801 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 1805 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1790 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1796 */                         if (rs != null) {
/* 1797 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 1801 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 1806 */                     col.addSeries(ts1);
/* 1807 */                     col.addSeries(ts2);
/* 1808 */                     col.addSeries(ts3);
/* 1809 */                     int[] x = { 0, 1, 2 };
/* 1810 */                     toReturn = new SubSeriesDataset(col, x);
/*      */                   }
/*      */                   else
/*      */                   {
/* 1814 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                   
/*      */                 }
/* 1818 */                 else if (this.type.equals("JOBSTATUS"))
/*      */                 {
/* 1820 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1821 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 1822 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1823 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 1824 */                   System.out.println("Resource ID :" + this.resID);
/* 1825 */                   DefaultIntervalCategoryDataset ds = null;
/* 1826 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1828 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1829 */                     String query = "select JOB_CLEAR,JOB_CRITICAL,JOB_WARNING from  AM_AS400_ALERTSTATUS where RESOURCEID=" + this.resID;
/* 1830 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1833 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1834 */                       if (set.next())
/*      */                       {
/* 1836 */                         System.out.println("AS400_POOL Graph :" + set.getDouble(2) + ":" + set.getString(1));
/* 1837 */                         result.addValue(new Double(set.getDouble(1)), "", "Clear");
/* 1838 */                         result.addValue(new Double(set.getDouble(2)), "", "Critical");
/* 1839 */                         result.addValue(new Double(set.getDouble(3)), "", "Warning");
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp)
/*      */                     {
/* 1844 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1850 */                         if (set != null)
/*      */                         {
/* 1852 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 1856 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 1862 */                   System.err.println("No Data collected ");
/*      */                   
/* 1864 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 1867 */                 else if (this.type.equals("SPOOLSTATUS"))
/*      */                 {
/* 1869 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1870 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 1871 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1872 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 1873 */                   System.out.println("Resource ID :" + this.resID);
/* 1874 */                   DefaultIntervalCategoryDataset ds = null;
/* 1875 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1877 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1878 */                     String query = "select SPOOL_CLEAR,SPOOL_CRITICAL,SPOOL_WARNING from  AM_AS400_ALERTSTATUS where RESOURCEID=" + this.resID;
/* 1879 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1882 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1883 */                       if (set.next())
/*      */                       {
/* 1885 */                         System.out.println("AS400_POOL Graph :" + set.getDouble(2) + ":" + set.getString(1));
/* 1886 */                         result.addValue(new Double(set.getDouble(1)), "", "Clear");
/* 1887 */                         result.addValue(new Double(set.getDouble(2)), "", "Critical");
/* 1888 */                         result.addValue(new Double(set.getDouble(3)), "", "Warning");
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp)
/*      */                     {
/* 1893 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1899 */                         if (set != null)
/*      */                         {
/* 1901 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 1905 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 1911 */                   System.err.println("No Data collected ");
/*      */                   
/* 1913 */                   toReturn = result;
/*      */                 }
/* 1915 */                 else if (this.type.equals("SUBSYSTEMSTATUS"))
/*      */                 {
/* 1917 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1918 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 1919 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1920 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 1921 */                   System.out.println("Resource ID :" + this.resID);
/* 1922 */                   DefaultIntervalCategoryDataset ds = null;
/* 1923 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1925 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1926 */                     String query = "select SUBSYSTEM_CLEAR,SUBSYSTEM_CRITICAL from  AM_AS400_ALERTSTATUS where RESOURCEID=" + this.resID;
/* 1927 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1930 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1931 */                       if (set.next())
/*      */                       {
/* 1933 */                         System.out.println("AS400_POOL Graph :" + set.getDouble(2) + ":" + set.getString(1));
/* 1934 */                         result.addValue(new Double(set.getDouble(1)), "", "Clear");
/* 1935 */                         result.addValue(new Double(set.getDouble(2)), "", "Critical");
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp)
/*      */                     {
/* 1940 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1946 */                         if (set != null)
/*      */                         {
/* 1948 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 1952 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 1958 */                   System.err.println("No Data collected ");
/*      */                   
/* 1960 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 1963 */                 else if (this.type.equals("AS400_POOL"))
/*      */                 {
/* 1965 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 1966 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 1967 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 1968 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 1969 */                   System.out.println("Resource ID :" + this.resID);
/* 1970 */                   DefaultIntervalCategoryDataset ds = null;
/* 1971 */                   String toremove = "Pool-";
/*      */                   
/* 1973 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 1975 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1976 */                     String query = "select mo.DISPLAYNAME,bi.SIZE from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 1977 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 1980 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 1981 */                       while (set.next())
/*      */                       {
/*      */ 
/* 1984 */                         String pooln = "";
/* 1985 */                         pooln = set.getString("DISPLAYNAME");
/* 1986 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/*      */                         
/* 1988 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 1992 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 1998 */                         if (set != null)
/*      */                         {
/* 2000 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2004 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2010 */                   System.err.println("No Data collected ");
/*      */                   
/* 2012 */                   toReturn = result;
/*      */                 }
/* 2014 */                 else if (this.type.equals("AS400_RESIZE"))
/*      */                 {
/* 2016 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 2017 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 2018 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 2019 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 2020 */                   System.out.println("Resource ID :" + this.resID);
/* 2021 */                   DefaultIntervalCategoryDataset ds = null;
/* 2022 */                   String toremove = "Pool-";
/* 2023 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2025 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2026 */                     String query = "select mo.DISPLAYNAME,bi.RESERVED_SIZE from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 2027 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 2030 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 2031 */                       while (set.next())
/*      */                       {
/*      */ 
/* 2034 */                         String pooln = "";
/* 2035 */                         pooln = set.getString("DISPLAYNAME");
/* 2036 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/* 2037 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 2041 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2047 */                         if (set != null)
/*      */                         {
/* 2049 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2053 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2059 */                   System.err.println("No Data collected ");
/*      */                   
/* 2061 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 2064 */                 else if (this.type.equals("AS400_DBPAGES"))
/*      */                 {
/* 2066 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 2067 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 2068 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 2069 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 2070 */                   System.out.println("Resource ID :" + this.resID);
/* 2071 */                   String toremove = "Pool-";
/* 2072 */                   DefaultIntervalCategoryDataset ds = null;
/* 2073 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2075 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2076 */                     String query = "select mo.DISPLAYNAME,bi.DB_PAGES from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 2077 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 2080 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 2081 */                       while (set.next())
/*      */                       {
/* 2083 */                         String pooln = "";
/* 2084 */                         pooln = set.getString("DISPLAYNAME");
/* 2085 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/* 2086 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 2090 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2096 */                         if (set != null)
/*      */                         {
/* 2098 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2102 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2108 */                   System.err.println("No Data collected ");
/*      */                   
/* 2110 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 2113 */                 else if (this.type.equals("AS400_NDBPAGES"))
/*      */                 {
/* 2115 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 2116 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 2117 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 2118 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 2119 */                   System.out.println("Resource ID :" + this.resID);
/* 2120 */                   String toremove = "Pool-";
/* 2121 */                   DefaultIntervalCategoryDataset ds = null;
/* 2122 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2124 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2125 */                     String query = "select mo.DISPLAYNAME,bi.NON_DB_PAGES from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 2126 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 2129 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 2130 */                       while (set.next())
/*      */                       {
/* 2132 */                         String pooln = "";
/* 2133 */                         pooln = set.getString("DISPLAYNAME");
/* 2134 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/* 2135 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 2139 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2145 */                         if (set != null)
/*      */                         {
/* 2147 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2151 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2157 */                   System.err.println("No Data collected ");
/*      */                   
/* 2159 */                   toReturn = result;
/*      */                 }
/* 2161 */                 else if (this.type.equals("AS400_DBFAULTS"))
/*      */                 {
/* 2163 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 2164 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 2165 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 2166 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 2167 */                   System.out.println("Resource ID :" + this.resID);
/* 2168 */                   String toremove = "Pool-";
/* 2169 */                   DefaultIntervalCategoryDataset ds = null;
/* 2170 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2172 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2173 */                     String query = "select mo.DISPLAYNAME,bi.DB_FAULTS from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 2174 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 2177 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 2178 */                       while (set.next())
/*      */                       {
/* 2180 */                         String pooln = "";
/* 2181 */                         pooln = set.getString("DISPLAYNAME");
/* 2182 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/* 2183 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 2187 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2193 */                         if (set != null)
/*      */                         {
/* 2195 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2199 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2205 */                   System.err.println("No Data collected ");
/*      */                   
/* 2207 */                   toReturn = result;
/*      */ 
/*      */                 }
/* 2210 */                 else if (this.type.equals("AS400_NDBFAULTS"))
/*      */                 {
/* 2212 */                   DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 2213 */                   this.collectionTime = getMaxCollectionTimeFrom("select max(collectiontime) from AM_AS400_STATUS where RESOURCEID=" + this.resID);
/* 2214 */                   long oneHourBefore = this.collectionTime - 3600000L;
/* 2215 */                   System.out.println("Collection Time :" + this.collectionTime);
/* 2216 */                   System.out.println("Resource ID :" + this.resID);
/* 2217 */                   String toremove = "Pool-";
/* 2218 */                   DefaultIntervalCategoryDataset ds = null;
/* 2219 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2221 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2222 */                     String query = "select mo.DISPLAYNAME,bi.NON_DB_FAULTS from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + this.resID + " and bi.collectiontime=" + this.collectionTime;
/* 2223 */                     ResultSet set = null;
/*      */                     try
/*      */                     {
/* 2226 */                       set = AMConnectionPool.executeQueryStmt(query);
/* 2227 */                       while (set.next())
/*      */                       {
/* 2229 */                         String pooln = "";
/* 2230 */                         pooln = set.getString("DISPLAYNAME");
/* 2231 */                         pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/* 2232 */                         result.addValue(new Double(set.getDouble(2)), "", pooln);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception exp) {
/* 2236 */                       exp.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2242 */                         if (set != null)
/*      */                         {
/* 2244 */                           set.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2248 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/* 2254 */                   System.err.println("No Data collected ");
/*      */                   
/* 2256 */                   toReturn = result;
/*      */ 
/*      */ 
/*      */ 
/*      */                 }
/* 2261 */                 else if (this.type.equals("IIS_FILESPERSECOND"))
/*      */                 {
/* 2263 */                   ResultSet rs = null;
/* 2264 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2266 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2267 */                     String query = "select FILESSENTPERSEC,FILESRECEIVEDPERSEC,FILESPERSEC,COLLECTIONTIME from AM_IIS_TRAFFIC_DATA where FILESSENTPERSEC>=0 and FILESRECEIVEDPERSEC>=0 and FILESPERSEC>=0 and  RESOURCEID=" + this.resID;
/* 2268 */                     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.iis.filessentpersec.text"), Minute.class);
/* 2269 */                     TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.iis.filesreceivedpersec"), Minute.class);
/* 2270 */                     TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.iis.filestransferredpersec.text"), Minute.class);
/*      */                     try
/*      */                     {
/* 2273 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 2274 */                       while (rs.next())
/*      */                       {
/* 2276 */                         Date d = new Date(rs.getLong(4));
/* 2277 */                         ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/* 2278 */                         ts2.addOrUpdate(new Minute(d), new Long(rs.getLong(2)));
/* 2279 */                         ts3.addOrUpdate(new Minute(d), new Long(rs.getLong(3)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 2291 */                         if (rs != null) {
/* 2292 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2296 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 2300 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2285 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2291 */                         if (rs != null) {
/* 2292 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2296 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 2301 */                     col.addSeries(ts1);
/* 2302 */                     col.addSeries(ts2);
/* 2303 */                     col.addSeries(ts3);
/* 2304 */                     int[] x = { 0, 1, 2 };
/* 2305 */                     toReturn = new SubSeriesDataset(col, x);
/*      */                   }
/*      */                   else
/*      */                   {
/* 2309 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                 }
/* 2312 */                 else if (this.type.equals("IIS_AnonymousUsers"))
/*      */                 {
/* 2314 */                   ResultSet rs = null;
/* 2315 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2317 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2318 */                     String query = "select ANONYMOUSUSERSPERSEC,COLLECTIONTIME from AM_IIS_USERS_DATA where ANONYMOUSUSERSPERSEC>=0 and RESOURCEID=" + this.resID;
/* 2319 */                     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.iis.anonymoususerspersec.text"), Minute.class);
/*      */                     try
/*      */                     {
/* 2322 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 2323 */                       while (rs.next())
/*      */                       {
/* 2325 */                         Date d = new Date(rs.getLong(2));
/* 2326 */                         ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 2337 */                         if (rs != null) {
/* 2338 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2342 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 2346 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2331 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2337 */                         if (rs != null) {
/* 2338 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2342 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 2347 */                     col.addSeries(ts1);
/* 2348 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 2352 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                 }
/* 2355 */                 else if (this.type.equals("IIS_NonAnonymousUsers"))
/*      */                 {
/* 2357 */                   ResultSet rs = null;
/* 2358 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2360 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2361 */                     String query = "select NONANONYMOUSUSERSPERSEC,COLLECTIONTIME from AM_IIS_USERS_DATA where NONANONYMOUSUSERSPERSEC>=0 and RESOURCEID=" + this.resID;
/* 2362 */                     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.iis.nonanonymoususerspersec.text"), Minute.class);
/*      */                     try
/*      */                     {
/* 2365 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 2366 */                       while (rs.next())
/*      */                       {
/* 2368 */                         Date d = new Date(rs.getLong(2));
/* 2369 */                         ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 2380 */                         if (rs != null) {
/* 2381 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2385 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 2389 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2374 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2380 */                         if (rs != null) {
/* 2381 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2385 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 2390 */                     col.addSeries(ts1);
/* 2391 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 2395 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                 }
/* 2398 */                 else if (this.type.equals("IIS_Connections"))
/*      */                 {
/* 2400 */                   ResultSet rs = null;
/* 2401 */                   if (this.collectionTime != -1L)
/*      */                   {
/* 2403 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2404 */                     String query = "select CURRENTCONNECTIONS,COLLECTIONTIME from AM_IIS_USERS_DATA where RESOURCEID=" + this.resID;
/* 2405 */                     TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.dotnet.connections"), Minute.class);
/*      */                     try
/*      */                     {
/* 2408 */                       rs = AMConnectionPool.executeQueryStmt(query);
/* 2409 */                       while (rs.next())
/*      */                       {
/* 2411 */                         Date d = new Date(rs.getLong(2));
/* 2412 */                         ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       try
/*      */                       {
/* 2423 */                         if (rs != null) {
/* 2424 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2428 */                         e.printStackTrace();
/*      */                       }
/*      */                       
/*      */ 
/* 2432 */                       col = new TimeSeriesCollection();
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2417 */                       e.printStackTrace();
/*      */                     }
/*      */                     finally
/*      */                     {
/*      */                       try
/*      */                       {
/* 2423 */                         if (rs != null) {
/* 2424 */                           rs.close();
/*      */                         }
/*      */                       }
/*      */                       catch (Exception e) {
/* 2428 */                         e.printStackTrace();
/*      */                       }
/*      */                     }
/*      */                     
/*      */                     TimeSeriesCollection col;
/* 2433 */                     col.addSeries(ts1);
/* 2434 */                     toReturn = new SubSeriesDataset(col, 0);
/*      */                   }
/*      */                   else
/*      */                   {
/* 2438 */                     System.err.println("No Data collected FOR Showing ContentLength");
/*      */                   }
/*      */                 } } } } } } }
/* 2441 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   private final long getMaxCollectionTimeFrom(String query)
/*      */   {
/* 2447 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2448 */     ResultSet set = null;
/* 2449 */     collectionTime = -1L;
/*      */     try
/*      */     {
/* 2452 */       set = AMConnectionPool.executeQueryStmt(query);
/* 2453 */       if (set.next())
/*      */       {
/* 2455 */         collectionTime = set.getLong(1);
/* 2456 */         if (collectionTime != 0L) {} }
/* 2457 */       return System.currentTimeMillis();
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 2461 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2467 */         if (set != null) {
/* 2468 */           set.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 2471 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final void closeResultSet(ResultSet set)
/*      */   {
/*      */     try
/*      */     {
/* 2482 */       if (set != null)
/*      */       {
/* 2484 */         set.close();
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2488 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasExpired(Map params, Date since)
/*      */   {
/* 2494 */     return true;
/*      */   }
/*      */   
/*      */   public String getProducerId()
/*      */   {
/* 2499 */     return "DataProducer";
/*      */   }
/*      */   
/*      */   public Object produceUrl(Map params)
/*      */     throws DatasetProduceException
/*      */   {
/* 2505 */     return this.urls;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\wlogic\bean\GetWLSGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */