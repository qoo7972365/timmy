/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*     */ import com.adventnet.appmanager.struts.actions.HistoryDataAction;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Hour;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HistoryDataGraphUtil
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  37 */   private String resID = null;
/*  38 */   private String attID = null;
/*  39 */   private String period = null;
/*  40 */   private String starttime = null;
/*  41 */   private String endtime = null;
/*  42 */   private ArrayList resids = null;
/*  43 */   private ArrayList attids = null;
/*  44 */   private int segmenttype = 1;
/*  45 */   private boolean comparision = false;
/*  46 */   private Hashtable segdatahash = null;
/*  47 */   private String bHrid = null;
/*  48 */   private final long thirtyDaysInMillis = 2592000000L;
/*  49 */   private final long sevenDaysInMillis = 604800000L;
/*  50 */   private final long yesterdayInMillis = 86400000L;
/*  51 */   private ArrayList rangelist = null;
/*  52 */   private ArrayList countperrange = null;
/*  53 */   private String attribname = null;
/*  54 */   private Boolean isConfMonitor = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParam(String resid, String attid, String period)
/*     */   {
/*  60 */     this.resID = resid;
/*  61 */     this.attID = attid;
/*  62 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String attid, String period, String bHrid) {
/*  66 */     this.resID = resid;
/*  67 */     this.attID = attid;
/*  68 */     this.period = period;
/*  69 */     this.bHrid = bHrid;
/*     */   }
/*     */   
/*     */   public void setParam(ArrayList rangelist, ArrayList countperrange, int segmenttype, String attribname)
/*     */   {
/*  74 */     this.segmenttype = segmenttype;
/*  75 */     this.rangelist = rangelist;
/*  76 */     this.countperrange = countperrange;
/*  77 */     this.attribname = attribname;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String attid, String period, String starttime, String endtime) {
/*  81 */     this.resID = resid;
/*  82 */     this.attID = attid;
/*  83 */     this.period = period;
/*  84 */     this.starttime = starttime;
/*  85 */     this.endtime = endtime;
/*     */   }
/*     */   
/*  88 */   public void setParam(String resid, String attid, String period, String starttime, String endtime, String bHrid) { this.resID = resid;
/*  89 */     this.attID = attid;
/*  90 */     this.period = period;
/*  91 */     this.starttime = starttime;
/*  92 */     this.endtime = endtime;
/*  93 */     this.bHrid = bHrid;
/*     */   }
/*     */   
/*     */   public void setParamToCompare(ArrayList aresids, ArrayList aattids, String period) {
/*  97 */     this.resids = aresids;
/*  98 */     this.attids = aattids;
/*  99 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setIsConfMonitor(Boolean isConfMonitor)
/*     */   {
/* 104 */     this.isConfMonitor = isConfMonitor;
/*     */   }
/*     */   
/*     */   public void setParamToCompare(ArrayList aresids, ArrayList aattids, String period, String starttime, String endtime) {
/* 108 */     this.resids = aresids;
/* 109 */     this.attids = aattids;
/* 110 */     this.period = period;
/* 111 */     this.starttime = starttime;
/* 112 */     this.endtime = endtime;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String attid, String period, String starttime, String endtime, String bHrid, int segmenttype, Hashtable segdatahash)
/*     */   {
/* 117 */     this.period = period;
/* 118 */     this.segmenttype = segmenttype;
/* 119 */     this.segdatahash = segdatahash;
/*     */   }
/*     */   
/*     */   public ArrayList getResids() {
/* 123 */     return this.resids;
/*     */   }
/*     */   
/* 126 */   public void setResids(ArrayList aresids) { this.resids = aresids; }
/*     */   
/*     */   public ArrayList getAttids()
/*     */   {
/* 130 */     return this.attids;
/*     */   }
/*     */   
/* 133 */   public void setAttids(ArrayList aattids) { this.attids = aattids; }
/*     */   
/*     */   public boolean isComparision() {
/* 136 */     return this.comparision;
/*     */   }
/*     */   
/*     */   public void setComparision(boolean comparision) {
/* 140 */     this.comparision = comparision;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/* 145 */     Object toReturn = null;
/* 146 */     if (this.segmenttype == 1)
/*     */     {
/* 148 */       long endTime = System.currentTimeMillis();
/* 149 */       long startTime = -1L;
/* 150 */       Properties p1 = new Properties();
/* 151 */       HistoryDataAction h = new HistoryDataAction();
/*     */       
/* 153 */       boolean forbHr = false;
/* 154 */       Hashtable bHourDetail = new Hashtable();
/* 155 */       if ((this.bHrid != null) && (!this.bHrid.equals("oni")))
/*     */       {
/* 157 */         bHourDetail = SegmentReportUtil.getBusinessRule(this.bHrid);
/* 158 */         forbHr = true;
/*     */       }
/* 160 */       if (this.period.equals("4")) {
/* 161 */         startTime = Long.parseLong(this.starttime);
/* 162 */         endTime = Long.parseLong(this.endtime);
/*     */       }
/* 164 */       if (this.period.equals("20"))
/*     */       {
/* 166 */         startTime = endTime - 18000000L;
/*     */       }
/*     */       else {
/* 169 */         p1 = h.getTimePeriod(startTime, endTime, this.period);
/* 170 */         startTime = ((Long)p1.get("STARTTIME")).longValue();
/* 171 */         endTime = ((Long)p1.get("ENDTIME")).longValue();
/*     */       }
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
/* 224 */       ResultSet rs = null;
/*     */       
/* 226 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */       
/*     */ 
/* 229 */       ResultSet set = null;
/* 230 */       ResultSet set1 = null;
/* 231 */       float avg = -1.0F;
/* 232 */       int _95percentileAvg = -1;
/* 233 */       ArrayList avgList = new ArrayList();
/*     */       
/* 235 */       TimeSeries ts3 = null;
/* 236 */       TimeSeries ts4 = null;
/* 237 */       TimeSeries ts5 = null;
/*     */       
/*     */       try
/*     */       {
/* 241 */         if ((this.period != null) && (this.period.equals("14")))
/*     */         {
/* 243 */           ArrayList raw = new ArrayList();
/* 244 */           Properties rawproperties = DBUtil.getRawValue();
/* 245 */           String RawVal = rawproperties.getProperty("rawvalue");
/* 246 */           long TI = Long.parseLong(RawVal) * 24L * 60L * 60L * 1000L;
/* 247 */           long ET = System.currentTimeMillis();
/* 248 */           long ST = ET - TI;
/* 249 */           String q1 = null;
/* 250 */           String nam = null;
/* 251 */           int attID1 = -1;
/* 252 */           if (this.attID != null)
/*     */           {
/* 254 */             attID1 = Integer.parseInt(this.attID);
/*     */           }
/* 256 */           if ((attID1 == 1357) || (attID1 == 1377) || (attID1 == 1457) || (attID1 == 1473) || (attID1 == 708) || (attID1 == 1394) || (attID1 == 1107) || (attID1 == 1207) || (attID1 == 1307) || (attID1 == 1657) || (attID1 == 807))
/*     */           {
/* 258 */             q1 = "select CPUUTIL AS VALUE,COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID=" + this.resID + " and COLLECTIONTIME<=" + ET + " and COLLECTIONTIME>=" + ST + " and CPUUTIL>=0 order by COLLECTIONTIME desc";
/* 259 */             nam = FormatUtil.getString("am.webclient.hometab.highcpuservers.columnheader.cpuutil");
/*     */           }
/* 261 */           if ((attID1 == 1352) || (attID1 == 1372) || (attID1 == 1452) || (attID1 == 1472) || (attID1 == 702) || (attID1 == 1392) || (attID1 == 1102) || (attID1 == 1202) || (attID1 == 803) || (attID1 == 1302) || (attID1 == 1652))
/*     */           {
/* 263 */             q1 = "select PHYMEMUTIL AS VALUE,COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID=" + this.resID + " and COLLECTIONTIME<=" + ET + " and COLLECTIONTIME>=" + ST + " and PHYMEMUTIL>=0 order by COLLECTIONTIME desc";
/* 264 */             nam = FormatUtil.getString("Physical Memory Utilization");
/*     */           }
/*     */           
/* 267 */           ts3 = new TimeSeries(nam, Minute.class);
/* 268 */           rs = AMConnectionPool.executeQueryStmt(q1);
/* 269 */           while (rs.next()) {
/* 270 */             int val = rs.getInt("VALUE");
/* 271 */             Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 272 */             Number num3 = new Integer(val);
/* 273 */             ts3.addOrUpdate(new Minute(d), num3);
/*     */           }
/* 275 */           TimeSeriesCollection col = new TimeSeriesCollection();
/* 276 */           col.addSeries(ts3);
/*     */           
/* 278 */           int[] x = { 0 };
/* 279 */           toReturn = new SubSeriesDataset(col, x);
/*     */         }
/*     */         
/* 282 */         if ((this.period != null) && (this.period.equals("20")))
/*     */         {
/* 284 */           ArrayList raw = new ArrayList();
/* 285 */           String ResName = "";
/* 286 */           String tableName = "";
/* 287 */           String resourceId = "";
/* 288 */           String valueCol = "";
/* 289 */           String tempColName = "";
/* 290 */           String attidCol = "";
/* 291 */           String colTime = "";
/* 292 */           String attName = "";
/* 293 */           String Condition = "";
/* 294 */           String expression = "";
/* 295 */           String Query = "select DISPLAYNAME from AM_ATTRIBUTES where attributeid=" + this.attID;
/* 296 */           set = AMConnectionPool.executeQueryStmt(Query);
/* 297 */           if (set.next())
/*     */           {
/* 299 */             attName = set.getString("DISPLAYNAME");
/*     */           }
/* 301 */           ts3 = new TimeSeries(FormatUtil.getString(attName), Minute.class);
/*     */           
/* 303 */           String typequery = "select type from AM_ManagedObject where resourceid=" + this.resID;
/* 304 */           boolean isGenericWmi = false;
/* 305 */           set = AMConnectionPool.executeQueryStmt(typequery);
/* 306 */           if ((set.next()) && ((set.getString("type").toLowerCase().startsWith("win32_")) || ((set.getString("type").equalsIgnoreCase("Web_Service_Operation")) && (Integer.parseInt(this.attID) > EnterpriseUtil.RANGE))))
/*     */           {
/* 308 */             isGenericWmi = true;
/*     */           }
/* 310 */           String type = set.getString("type");
/*     */           
/* 312 */           if (!isGenericWmi)
/*     */           {
/* 314 */             String paramQuery = "select Datatable,resid_col,value_col,coltime_val,attid_col,EXPRESSION from AM_ATTRIBUTES_EXT where attributeid=" + this.attID;
/* 315 */             set = AMConnectionPool.executeQueryStmt(paramQuery);
/* 316 */             ArrayList confAttributes = ConfMonitorConfiguration.getInstance().getAttListInDataTables();
/* 317 */             while (set.next())
/*     */             {
/* 319 */               tableName = set.getString("Datatable");
/* 320 */               resourceId = set.getString("resid_col");
/* 321 */               valueCol = set.getString("value_col");
/* 322 */               tempColName = set.getString("value_col");
/* 323 */               if ((this.isConfMonitor.booleanValue()) && (tableName.equals("AM_ManagedObjectData")) && (valueCol.equalsIgnoreCase("RESPONSETIME"))) {
/* 324 */                 ArrayList dbtableNameList = ConfMonitorUtil.getInstance().getCurrentTable(type, "");
/* 325 */                 tableName = (String)dbtableNameList.get(0);
/* 326 */                 resourceId = "RESOURCEID";
/* 327 */                 attidCol = "-1";
/* 328 */                 valueCol = "responsetime";
/*     */               }
/* 330 */               if ((confAttributes.contains(this.attID)) && (!tableName.equalsIgnoreCase("AM_CONFIGURATION_INFO"))) {
/* 331 */                 String charToAppend = ConfMonitorUtil.getSpecialCharToAppend();
/* 332 */                 valueCol = charToAppend + valueCol + charToAppend;
/*     */               }
/* 334 */               colTime = set.getString("coltime_val");
/* 335 */               attidCol = set.getString("attid_col");
/* 336 */               expression = set.getString("EXPRESSION");
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 341 */             attidCol = "ATTRIBUTEID";
/* 342 */             tableName = "AM_CAM_COLUMNAR_DATA";
/* 343 */             colTime = "COLLECTIONTIME";
/* 344 */             valueCol = "VALUE";
/* 345 */             tempColName = "VALUE";
/* 346 */             resourceId = "ROWID";
/*     */           }
/*     */           
/* 349 */           if (!attidCol.equals("-1"))
/*     */           {
/* 351 */             Condition = " and attributeid=" + this.attID;
/*     */           }
/* 353 */           System.out.println("Bhr" + this.bHrid);
/*     */           
/* 355 */           if ((this.bHrid != null) && (!this.bHrid.equals("oni")))
/*     */           {
/* 357 */             System.out.println("inside conditional loop");
/* 358 */             Properties props = ReportUtilities.getWorkingTotalHours(startTime, endTime, this.bHrid);
/* 359 */             String stTime = props.getProperty("downtime");
/* 360 */             String enTime = props.getProperty("uptime");
/* 361 */             String action = props.getProperty("toremove");
/* 362 */             if (!action.equals("toremove"))
/*     */             {
/* 364 */               String valueQuery = "select " + colTime + "," + valueCol + expression + " as " + valueCol + " from " + tableName + " where " + resourceId + "=" + this.resID + Condition + " and " + colTime + ">=" + stTime + " and " + colTime + "<=" + enTime + " order by " + colTime + " desc";
/* 365 */               if (resourceId.equals("RESOURCENAME"))
/*     */               {
/* 367 */                 String NameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + this.resID;
/* 368 */                 set = AMConnectionPool.executeQueryStmt(NameQuery);
/* 369 */                 if (set.next())
/*     */                 {
/* 371 */                   ResName = set.getString("RESOURCENAME");
/* 372 */                   valueQuery = "select " + colTime + "," + valueCol + expression + " as " + valueCol + " from " + tableName + " where " + resourceId + "='" + ResName + "' and " + colTime + ">=" + stTime + " and " + colTime + "<=" + enTime + Condition + " order by " + colTime + " desc";
/*     */                 }
/*     */               }
/* 375 */               set = AMConnectionPool.executeQueryStmt(valueQuery);
/* 376 */               while (set.next())
/*     */               {
/* 378 */                 Properties prop = new Properties();
/* 379 */                 Date d = new Date(set.getLong(colTime));
/* 380 */                 int rValue = set.getInt(tempColName);
/* 381 */                 Number num3 = new Integer(rValue);
/* 382 */                 ts3.addOrUpdate(new Minute(d), num3);
/*     */               }
/*     */             }
/*     */           }
/* 386 */           if ((this.bHrid != null) && (this.bHrid.equals("oni")))
/*     */           {
/* 388 */             String valueQuery = "select " + colTime + "," + valueCol + expression + " as " + valueCol + " from " + tableName + " where " + resourceId + "=" + this.resID + Condition + " order by " + colTime + " desc";
/* 389 */             if ((this.isConfMonitor.booleanValue()) && (tableName.equals("AM_CAM_DC_ATTRIBUTES"))) {
/* 390 */               valueQuery = "select COLLECTIONTIME,VALUE from AM_CAM_COLUMNAR_DATA where ROWID=" + this.resID + " and ATTRIBUTEID=" + this.attID + " order by COLLECTIONTIME desc";
/* 391 */               tempColName = "VALUE";
/*     */             }
/* 393 */             if (resourceId.equals("RESOURCENAME"))
/*     */             {
/* 395 */               String NameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + this.resID;
/* 396 */               set = AMConnectionPool.executeQueryStmt(NameQuery);
/* 397 */               if (set.next())
/*     */               {
/* 399 */                 ResName = set.getString("RESOURCENAME");
/* 400 */                 valueQuery = "select " + colTime + "," + valueCol + expression + " as " + valueCol + " from " + tableName + " where " + resourceId + "='" + ResName + "'" + Condition + " order by " + colTime + " desc";
/*     */               }
/*     */             }
/* 403 */             set = AMConnectionPool.executeQueryStmt(valueQuery);
/* 404 */             while (set.next())
/*     */             {
/* 406 */               Properties prop = new Properties();
/* 407 */               Date d = new Date(set.getLong(colTime));
/*     */               
/*     */ 
/*     */ 
/* 411 */               double rValueD = 0.0D;
/*     */               try {
/* 413 */                 rValueD = set.getDouble(tempColName);
/*     */               } catch (Exception e) {
/* 415 */                 AMLog.debug("Exception occured in processing query : " + valueQuery + ". Error : " + e.getMessage() + ". So going to read it as String and parse to Double.");
/* 416 */                 rValueD = Double.parseDouble(set.getString(tempColName));
/*     */               }
/*     */               
/* 419 */               ts3.addOrUpdate(new Minute(d), rValueD);
/*     */             }
/* 421 */             AMConnectionPool.closeResultSet(set);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 426 */           TimeSeriesCollection col = new TimeSeriesCollection();
/* 427 */           col.addSeries(ts3);
/*     */           
/* 429 */           int[] x = { 0 };
/* 430 */           toReturn = new SubSeriesDataset(col, x);
/*     */         }
/*     */         else
/*     */         {
/* 434 */           ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(String.valueOf(this.attID));
/* 435 */           String archivedTableName = (String)attribDetails.get(0);
/* 436 */           String expression1 = (String)attribDetails.get(1);
/* 437 */           long[] dailyRptStartEndTime = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/*     */           
/* 439 */           String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime;
/* 440 */           if (dailyRptStartEndTime[2] > 0L) {
/* 441 */             dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + dailyRptStartEndTime[1] + " and ARCHIVEDTIME >= " + dailyRptStartEndTime[0] + " OR DURATION=2 and ARCHIVEDTIME <= " + dailyRptStartEndTime[3] + " and ARCHIVEDTIME >= " + dailyRptStartEndTime[2] + ") ";
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 446 */           String query = "select (TOTAL/TOTALCOUNT)" + expression1 + " as AVG from " + archivedTableName + " where RESID=" + this.resID + " and ATTRIBUTEID=" + this.attID + dailyRptCondition + " order by ARCHIVEDTIME asc";
/* 447 */           set = AMConnectionPool.executeQueryStmt(query);
/* 448 */           while (set.next()) {
/* 449 */             float avgValue = set.getFloat("AVG");
/* 450 */             avgList.add(new Float(avgValue));
/*     */           }
/*     */           
/* 453 */           Number num5 = Float.valueOf(-1.0F);
/* 454 */           if (avgList.size() > 1) {
/* 455 */             num5 = Float.valueOf(h.getnfPercentile(avgList));
/* 456 */             ts5 = new TimeSeries(FormatUtil.getString("am.webclient.historydatareport.stdDevn.nfpercentile.text"), Hour.class);
/*     */           }
/*     */           
/* 459 */           String bhr_condition = "";
/* 460 */           int resid = Integer.parseInt(this.resID);
/* 461 */           int attid = Integer.parseInt(this.attID);
/* 462 */           if (forbHr)
/*     */           {
/* 464 */             ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(startTime, endTime, resid, attid, archivedTableName, bHourDetail);
/* 465 */             for (int i = 0; i < blist.size(); i++)
/*     */             {
/* 467 */               Hashtable time_stamps = (Hashtable)blist.get(i);
/* 468 */               if ((bhr_condition != null) && (bhr_condition.length() > 1))
/*     */               {
/* 470 */                 bhr_condition = bhr_condition + " or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*     */               }
/*     */               else
/*     */               {
/* 474 */                 bhr_condition = "(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*     */               }
/*     */             }
/* 477 */             query = "select min(MINVALUE)" + expression1 + " as MIN,max(MAXVALUE)" + expression1 + " as MAX,(sum(TOTAL)/sum(TOTALCOUNT))" + expression1 + " as AVG from " + archivedTableName + " where RESID=" + this.resID + " and ATTRIBUTEID=" + this.attID + " and DURATION=1 and (" + bhr_condition + ")";
/* 478 */             AMLog.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MIN-MAX-AVG CONDITION $$$$$ " + query);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 483 */             query = "select min(MINVALUE)" + expression1 + " as MIN,max(MAXVALUE)" + expression1 + " as MAX,(sum(TOTAL)/sum(TOTALCOUNT))" + expression1 + " as AVG from " + archivedTableName + " where RESID=" + this.resID + " and ATTRIBUTEID=" + this.attID + dailyRptCondition;
/* 484 */             AMLog.debug("########## Data Graph util AVG Query ############# " + query);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 492 */           set1 = AMConnectionPool.executeQueryStmt(query);
/* 493 */           if (set1.next()) {
/* 494 */             avg = set1.getFloat("AVG");
/*     */           }
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
/* 506 */           Number num3 = new Float(avg);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 512 */           query = "select (TOTAL/TOTALCOUNT)" + expression1 + " as AVG, ARCHIVEDTIME from " + archivedTableName + " where RESID=" + this.resID + " and ATTRIBUTEID=" + this.attID + dailyRptCondition + " order by ARCHIVEDTIME asc";
/*     */           
/*     */ 
/* 515 */           ts3 = new TimeSeries(FormatUtil.getString("am.webclient.730attribute.legendaverage.text"), Hour.class);
/* 516 */           ts4 = new TimeSeries(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"), Hour.class);
/*     */           
/* 518 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 519 */           while (rs.next()) {
/* 520 */             boolean isbHrdata = false;
/* 521 */             if (bHourDetail.size() != 0)
/*     */             {
/*     */ 
/*     */ 
/* 525 */               Calendar cal = Calendar.getInstance();
/* 526 */               cal.setTimeInMillis(rs.getLong("ARCHIVEDTIME"));
/* 527 */               int weekday = cal.get(7) - 1;
/* 528 */               int hour = cal.get(11);
/* 529 */               int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/* 530 */               int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/* 531 */               if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*     */               {
/* 533 */                 isbHrdata = true;
/*     */               }
/*     */             }
/*     */             
/* 537 */             if ((!forbHr) || ((forbHr) && (isbHrdata)))
/*     */             {
/* 539 */               float averageValue = rs.getFloat("AVG");
/* 540 */               Date d = new Date(rs.getLong("ARCHIVEDTIME"));
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 550 */               ts3.addOrUpdate(new Hour(d), num3);
/*     */               
/* 552 */               Number num4 = new Float(averageValue);
/* 553 */               ts4.addOrUpdate(new Hour(d), num4);
/*     */               
/* 555 */               if (ts5 != null) {
/* 556 */                 ts5.addOrUpdate(new Hour(d), num5);
/*     */               }
/*     */             }
/*     */           }
/* 560 */           TimeSeriesCollection col = new TimeSeriesCollection();
/* 561 */           col.addSeries(ts3);
/* 562 */           col.addSeries(ts4);
/*     */           
/* 564 */           if (ts5 != null) {
/* 565 */             col.addSeries(ts5);
/* 566 */             int[] x = { 0, 1, 2 };
/* 567 */             toReturn = new SubSeriesDataset(col, x);
/*     */           } else {
/* 569 */             int[] x = { 0, 1 };
/* 570 */             toReturn = new SubSeriesDataset(col, x);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 575 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 578 */         closeResultSet(set);
/* 579 */         closeResultSet(set1);
/* 580 */         closeResultSet(rs);
/*     */       }
/*     */       
/* 583 */       return toReturn;
/*     */     }
/* 585 */     if (this.segmenttype == 2)
/*     */     {
/*     */ 
/* 588 */       DefaultCategoryDataset barset = new DefaultCategoryDataset();
/*     */       
/* 590 */       for (int i = 1; i < 24; i++)
/*     */       {
/* 592 */         String hrString = Integer.toString(i);
/* 593 */         Hashtable hrdata = (Hashtable)this.segdatahash.get(hrString);
/* 594 */         if (i < 12)
/*     */         {
/* 596 */           hrString = hrString + " " + FormatUtil.getString("AM");
/*     */         }
/* 598 */         else if (i == 12)
/*     */         {
/* 600 */           hrString = hrString + " " + FormatUtil.getString("PM");
/*     */         }
/*     */         else
/*     */         {
/* 604 */           hrString = Integer.toString(i - 12) + " " + FormatUtil.getString("PM");
/*     */         }
/* 606 */         if (!((String)hrdata.get("AVG")).equals("-"))
/*     */         {
/* 608 */           barset.setValue(new Float((String)hrdata.get("AVG")), "", FormatUtil.getString(hrString));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 615 */       Hashtable hrdata = (Hashtable)this.segdatahash.get("0");
/*     */       
/* 617 */       if (!((String)hrdata.get("AVG")).equals("-"))
/*     */       {
/* 619 */         barset.setValue(new Float((String)hrdata.get("AVG")), "", "12 " + FormatUtil.getString("AM"));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 624 */       return barset;
/*     */     }
/*     */     
/* 627 */     if (this.segmenttype == 3)
/*     */     {
/*     */ 
/* 630 */       DefaultCategoryDataset barset = new DefaultCategoryDataset();
/*     */       
/* 632 */       for (int i = 0; i < 7; i++)
/*     */       {
/* 634 */         String hrString = Integer.toString(i);
/* 635 */         Hashtable hrdata = (Hashtable)this.segdatahash.get(hrString);
/* 636 */         String weekday = "";
/* 637 */         if (i == 0)
/*     */         {
/* 639 */           weekday = "Sunday";
/*     */         }
/* 641 */         else if (i == 1)
/*     */         {
/* 643 */           weekday = "Monday";
/*     */         }
/* 645 */         else if (i == 2)
/*     */         {
/* 647 */           weekday = "Tuesday";
/*     */         }
/* 649 */         else if (i == 3)
/*     */         {
/* 651 */           weekday = "Wednesday";
/*     */         }
/* 653 */         else if (i == 4)
/*     */         {
/* 655 */           weekday = "Thursday";
/*     */         }
/* 657 */         else if (i == 5)
/*     */         {
/* 659 */           weekday = "Friday";
/*     */         }
/*     */         else
/*     */         {
/* 663 */           weekday = "Saturday";
/*     */         }
/*     */         
/* 666 */         if (!((String)hrdata.get("AVG")).equals("-"))
/*     */         {
/* 668 */           barset.setValue(new Float((String)hrdata.get("AVG")), "", FormatUtil.getString(weekday));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 677 */       return barset;
/*     */     }
/*     */     
/* 680 */     if (this.segmenttype == 4)
/*     */     {
/*     */ 
/*     */ 
/* 684 */       DefaultCategoryDataset areaset = new DefaultCategoryDataset();
/* 685 */       for (int i = 0; i < this.rangelist.size(); i++)
/*     */       {
/* 687 */         if (i == 0)
/*     */         {
/* 689 */           if (((Float)this.rangelist.get(i)).floatValue() != 0.0F)
/*     */           {
/* 691 */             areaset.setValue((Float)this.countperrange.get(i), this.attribname, "<" + (Float)this.rangelist.get(i));
/*     */           }
/*     */           
/*     */         }
/*     */         else {
/* 696 */           areaset.setValue((Float)this.countperrange.get(i), this.attribname, (Float)this.rangelist.get(i - 1) + " - " + (Float)this.rangelist.get(i));
/*     */         }
/*     */       }
/*     */       
/* 700 */       if (this.countperrange.size() > this.rangelist.size())
/*     */       {
/* 702 */         areaset.setValue((Float)this.countperrange.get(this.countperrange.size() - 1), this.attribname, "> " + (Float)this.rangelist.get(this.rangelist.size() - 1));
/*     */       }
/*     */       
/*     */ 
/* 706 */       return areaset;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 711 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 716 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 720 */     return "DataProducer";
/*     */   }
/*     */   
/*     */   private final void closeResultSet(ResultSet set) {
/* 724 */     if (set != null) {
/*     */       try {
/* 726 */         set.close();
/*     */       } catch (Exception ex) {
/* 728 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\HistoryDataGraphUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */