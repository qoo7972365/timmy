/*     */ package com.adventnet.appmanager.reporting.dataproducer;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.datacorrection.AMDataCorrectionUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.DefaultValueDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Hour;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  41 */   private Map params = null;
/*     */   
/*     */ 
/*     */ 
/*  45 */   public void setParams(Map params) { this.params = params; }
/*     */   
/*  47 */   private Hashtable urls = null;
/*     */   
/*     */   public Object produceDataset(Map param) throws DatasetProduceException {
/*  50 */     Object toReturn = null;
/*  51 */     String type = (String)this.params.get("type");
/*  52 */     String period = (String)this.params.get("period");
/*  53 */     if (type.equals("EVENT_SUMMARY"))
/*     */     {
/*  55 */       DefaultPieDataset ds = new DefaultPieDataset();
/*  56 */       long[] timeStamps = new long[2];
/*  57 */       if ((period != null) && (period.equals("4")))
/*     */       {
/*  59 */         timeStamps[0] = ((Long)this.params.get("starttime")).longValue();
/*  60 */         timeStamps[1] = ((Long)this.params.get("endtime")).longValue();
/*     */       }
/*     */       else
/*     */       {
/*  64 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*     */       }
/*     */       
/*  67 */       String haid = (String)this.params.get("id");
/*     */       
/*  69 */       String query = "select SEVERITY,sum(OCCURANCES) as num  from AM_EventHistoryData,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " group by SEVERITY";
/*  70 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  71 */       ResultSet set = null;
/*     */       try
/*     */       {
/*  74 */         set = AMConnectionPool.executeQueryStmt(query);
/*  75 */         AMLog.debug("Reports : Graphs .. Query executed for EVENT_SUMMARY is " + query);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  80 */         ds.setValue(FormatUtil.getString("Critical"), 0.0D);
/*  81 */         ds.setValue(FormatUtil.getString("Warning"), 0.0D);
/*  82 */         ds.setValue(FormatUtil.getString("Clear"), 0.0D);
/*     */         
/*  84 */         int critical = 0;
/*  85 */         int clear = 0;
/*  86 */         int warning = 0;
/*  87 */         while (set.next())
/*     */         {
/*  89 */           int severity = set.getInt(1);
/*  90 */           int value = set.getInt(2);
/*  91 */           if (severity == 1) {
/*  92 */             critical = value;
/*  93 */           } else if (severity == 4) {
/*  94 */             warning = value;
/*  95 */           } else if (severity == 5)
/*  96 */             clear = value;
/*     */         }
/*  98 */         int total = critical + clear + warning;
/*  99 */         float criticalPercentage = critical / total * 100.0F;
/* 100 */         float clearPercentage = clear / total * 100.0F;
/* 101 */         float warningPercentage = warning / total * 100.0F;
/*     */         
/*     */ 
/*     */ 
/* 105 */         ds.setValue(FormatUtil.getString("Critical"), Math.round(criticalPercentage * 100.0F) / 100.0F);
/* 106 */         ds.setValue(FormatUtil.getString("Warning"), Math.round(warningPercentage * 100.0F) / 100.0F);
/* 107 */         ds.setValue(FormatUtil.getString("Clear"), Math.round(clearPercentage * 100.0F) / 100.0F);
/*     */       }
/*     */       catch (Exception exp) {
/* 110 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 114 */         closeResultSet(set);
/*     */       }
/* 116 */       toReturn = ds;
/*     */     }
/* 118 */     else if (type.equals("MONITOR_EVENTS"))
/*     */     {
/* 120 */       DefaultPieDataset ds = new DefaultPieDataset();
/* 121 */       long[] timeStamps = new long[2];
/*     */       
/* 123 */       if ((period != null) && (period.equals("4")))
/*     */       {
/* 125 */         timeStamps[0] = ((Long)this.params.get("starttime")).longValue();
/* 126 */         timeStamps[1] = ((Long)this.params.get("endtime")).longValue();
/*     */       }
/*     */       else
/*     */       {
/* 130 */         timeStamps = ReportUtilities.getTimeStamp(period);
/*     */       }
/* 132 */       String haid = (String)this.params.get("id");
/* 133 */       String query = "select AM_ManagedObject.DISPLAYNAME,sum(OCCURANCES) as num  from AM_EventHistoryData,AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_ManagedObject.RESOURCEID=RESID and  AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " and SEVERITY = 1 group by RESID,AM_ManagedObject.DISPLAYNAME order by num desc";
/* 134 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 135 */       ResultSet set = null;
/*     */       try
/*     */       {
/* 138 */         set = AMConnectionPool.executeQueryStmt(query);
/* 139 */         AMLog.debug("Reports : Query in Monitor_Events" + query);
/*     */         
/* 141 */         while (set.next())
/*     */         {
/* 143 */           String moname = set.getString("DISPLAYNAME");
/* 144 */           int value = set.getInt("num");
/* 145 */           ds.setValue(moname, new Integer(value));
/*     */         }
/*     */       }
/*     */       catch (Exception exp) {
/* 149 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 153 */         closeResultSet(set);
/*     */       }
/* 155 */       toReturn = ds;
/*     */     } else {
/* 157 */       if (type.equals("ATTRIBUTE_GRAPH"))
/*     */       {
/* 159 */         long[] timeStamps = ReportUtilities.getTimeStamp(period);
/* 160 */         String resourceid = (String)this.params.get("id");
/* 161 */         String attributeid = (String)this.params.get("attid");
/* 162 */         String startTime = (String)this.params.get("startTime");
/* 163 */         String endTime = (String)this.params.get("endTime");
/* 164 */         String attributeName = (String)this.params.get("attributeName");
/* 165 */         if ((startTime.equals("")) || (endTime.equals("")))
/*     */         {
/* 167 */           timeStamps = ReportUtilities.getTimeStamp(period);
/*     */         }
/*     */         else
/*     */         {
/* 171 */           timeStamps = ReportUtilities.parseTimeAndDate(startTime, endTime);
/*     */         }
/* 173 */         ResultSet rs = null;
/* 174 */         ResultSet set1 = null;
/* 175 */         float avg = -1.0F;
/*     */         
/* 177 */         TimeSeries ts3 = null;
/* 178 */         TimeSeries ts4 = null;
/* 179 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */         
/*     */ 
/*     */ 
/* 183 */         ResultSet set = null;
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
/*     */         try
/*     */         {
/* 197 */           String query = "select (sum(TOTAL)/sum(TOTALCOUNT)) as AVG from AM_MinMaxAvgData where DURATION=1 and RESID=" + resourceid + " and ATTRIBUTEID=" + attributeid + "  and ARCHIVEDTIME <= " + timeStamps[1] + " and ARCHIVEDTIME >= " + timeStamps[0];
/* 198 */           AMLog.debug("Reports : ATTRIBUTE_GRAPH avg2 query" + query);
/* 199 */           set1 = AMConnectionPool.executeQueryStmt(query);
/* 200 */           if (set1.next())
/*     */           {
/* 202 */             avg = set1.getFloat("AVG");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */           query = "select (TOTAL/TOTALCOUNT) as AVG, ARCHIVEDTIME from AM_MinMaxAvgData where DURATION=1 and RESID=" + resourceid + " and ATTRIBUTEID=" + attributeid + "  and ARCHIVEDTIME <= " + timeStamps[1] + " and ARCHIVEDTIME >= " + timeStamps[0] + " order by ARCHIVEDTIME asc";
/* 210 */           AMLog.debug("Reports : ATTRIBUTE_GRAPH avg3 query" + query);
/*     */           
/* 212 */           ts3 = new TimeSeries(FormatUtil.getString("am.webclient.730attribute.legendaverage.text"), Hour.class);
/* 213 */           ts4 = new TimeSeries(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"), Hour.class);
/*     */           
/* 215 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 216 */           while (rs.next())
/*     */           {
/* 218 */             float averageValue = rs.getFloat("AVG");
/* 219 */             Date d = new Date(rs.getLong("ARCHIVEDTIME"));
/*     */             
/*     */ 
/* 222 */             Number num3 = new Float(avg);
/* 223 */             ts3.addOrUpdate(new Hour(d), num3);
/*     */             
/* 225 */             Number num4 = new Float(averageValue);
/* 226 */             ts4.addOrUpdate(new Hour(d), num4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 245 */           AMLog.fatal("Reports : Error Occured ,While getting Reporting data", e);
/*     */         }
/*     */         finally
/*     */         {
/* 249 */           closeResultSet(set);
/* 250 */           closeResultSet(set1);
/* 251 */           closeResultSet(rs);
/*     */         }
/* 253 */         TimeSeriesCollection col = new TimeSeriesCollection();
/* 254 */         col.addSeries(ts3);
/* 255 */         col.addSeries(ts4);
/* 256 */         int[] x = { 0, 1 };
/* 257 */         toReturn = new SubSeriesDataset(col, x);
/* 258 */         return toReturn;
/*     */       }
/*     */       
/*     */ 
/* 262 */       if (type.equals("AVAILABILITY"))
/*     */       {
/* 264 */         boolean firstTime = true;
/* 265 */         for (int i = 0; i < 2; i++)
/*     */         {
/* 267 */           DefaultPieDataset ds = new DefaultPieDataset();
/* 268 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 269 */           ResultSet set = null;
/* 270 */           String resID = (String)this.params.get("id");
/*     */           
/*     */           try
/*     */           {
/* 274 */             long[] time = ReportUtilities.getTimeStamp(period);
/* 275 */             long customstartTime = 0L;
/* 276 */             long customendTime = 0L;
/* 277 */             long mintimeindb = ReportUtilities.getMinTimeInDB(resID);
/* 278 */             long startTime = 0L;
/* 279 */             long endTime = 0L;
/* 280 */             long totalDuration = 0L;
/* 281 */             String startdate = (String)this.params.get("startTime");
/* 282 */             String enddate = (String)this.params.get("endTime");
/*     */             long currenttime;
/* 284 */             if (period.equals("4"))
/*     */             {
/* 286 */               customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/* 287 */               customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/* 288 */               if (mintimeindb > customstartTime)
/*     */               {
/* 290 */                 startTime = mintimeindb;
/*     */               }
/* 292 */               else if (mintimeindb != 0L)
/*     */               {
/* 294 */                 startTime = customstartTime;
/*     */               }
/* 296 */               currenttime = System.currentTimeMillis();
/* 297 */               if (customendTime > currenttime)
/*     */               {
/* 299 */                 endTime = currenttime;
/*     */               }
/*     */               else
/*     */               {
/* 303 */                 endTime = customendTime;
/*     */               }
/* 305 */               totalDuration = endTime - startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/* 311 */             else if (mintimeindb > time[0])
/*     */             {
/* 313 */               startTime = mintimeindb;
/* 314 */               endTime = time[1];
/* 315 */               if (endTime > startTime)
/*     */               {
/* 317 */                 totalDuration = endTime - startTime;
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 322 */                 return null;
/*     */               }
/*     */             }
/* 325 */             else if (mintimeindb != 0L)
/*     */             {
/* 327 */               startTime = time[0];
/* 328 */               endTime = time[1];
/* 329 */               totalDuration = endTime - startTime;
/*     */             }
/*     */             
/*     */ 
/* 333 */             String down = "";
/* 334 */             String up = "";
/* 335 */             String unmanage = "";
/* 336 */             String schedule = "";
/* 337 */             float downPercent = 0.0F;
/* 338 */             float upPercent = 0.0F;
/* 339 */             float unmanagePercent = 0.0F;
/* 340 */             float schedulePercent = 0.0F;
/*     */             
/* 342 */             String query = "select RESID, TYPE, sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where RESID in (" + resID + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE order by TotalDownTime desc";
/* 343 */             if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 344 */               query = "select RESID, TYPE, sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where RESID in (" + resID + ") and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE order by TotalDownTime desc";
/*     */             }
/*     */             
/* 347 */             set = AMConnectionPool.executeQueryStmt(query);
/* 348 */             AMLog.debug("query in report graph bean availability" + query);
/*     */             
/* 350 */             int typeID = -1;
/* 351 */             long unmanagedtime = 0L;
/* 352 */             long scheduledtime = 0L;
/* 353 */             long totdowntime = 0L;
/* 354 */             while (set.next())
/*     */             {
/* 356 */               typeID = set.getInt("TYPE");
/* 357 */               if (typeID == 1)
/*     */               {
/* 359 */                 totdowntime = set.getLong("TotalDownTime");
/*     */               }
/* 361 */               else if (typeID == 2)
/*     */               {
/* 363 */                 unmanagedtime = set.getLong("TotalDownTime");
/*     */               }
/*     */               else
/*     */               {
/* 367 */                 scheduledtime = set.getLong("TotalDownTime");
/*     */               }
/*     */             }
/*     */             
/* 371 */             long uptime = totalDuration - (totdowntime + unmanagedtime + scheduledtime);
/* 372 */             down = ReportUtilities.format(totdowntime);
/* 373 */             up = ReportUtilities.format(uptime);
/* 374 */             unmanage = ReportUtilities.format(unmanagedtime);
/* 375 */             schedule = ReportUtilities.format(scheduledtime);
/* 376 */             upPercent = (float)uptime / (float)totalDuration * 100.0F;
/* 377 */             downPercent = (float)totdowntime / (float)totalDuration * 100.0F;
/* 378 */             unmanagePercent = (float)unmanagedtime / (float)totalDuration * 100.0F;
/* 379 */             schedulePercent = (float)scheduledtime / (float)totalDuration * 100.0F;
/* 380 */             if ((upPercent < 0.0F) || (upPercent > 100.0F) || (downPercent < 0.0F) || (downPercent > 100.0F) || (uptime < 0L) || (totdowntime < 0L) || (unmanagePercent < 0.0F) || (unmanagePercent > 100.0F) || (schedulePercent < 0.0F) || (schedulePercent > 100.0F)) {
/* 381 */               AMLog.debug("**********************************************************************************");
/* 382 */               AMLog.debug("ReportGraphs :  Resource ID: " + resID + " uptime: " + uptime + " downtime: " + totdowntime);
/* 383 */               AMLog.debug("**********************************************************************************");
/* 384 */               if (firstTime) {
/* 385 */                 firstTime = false;
/* 386 */                 AMDataCorrectionUtil.correctResourceForAvailability(Integer.parseInt(resID));
/* 387 */                 AMDataCorrectionUtil.correctOverlappingEntriesForAvailability(Integer.parseInt(resID));
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
/* 429 */                 closeResultSet(set); continue;
/*     */               }
/* 390 */               throw new IllegalStateException("The data is inconsistent to plot the chart.");
/*     */             }
/*     */             
/* 393 */             if (totalDuration == 0L) {
/* 394 */               throw new IllegalStateException("There is no data available at this moment.");
/*     */             }
/*     */             
/* 397 */             if (!down.startsWith("0")) {
/* 398 */               ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, Math.round(downPercent * 100.0F) / 100.0F);
/*     */             }
/* 400 */             if (!up.startsWith("0")) {
/* 401 */               ds.setValue(FormatUtil.getString("am.webclient.historydata.uptime.text") + " " + up, Math.round(upPercent * 100.0F) / 100.0F);
/*     */             }
/* 403 */             if (!unmanage.startsWith("0")) {
/* 404 */               ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + unmanage, Math.round(unmanagePercent * 100.0F) / 100.0F);
/*     */             }
/* 406 */             if (!schedule.startsWith("0")) {
/* 407 */               ds.setValue(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") + " " + schedule, Math.round(schedulePercent * 100.0F) / 100.0F);
/*     */             }
/*     */             
/*     */ 
/* 411 */             if (period.equals("0"))
/*     */             {
/* 413 */               this.urls = new Hashtable(4);
/* 414 */               this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.downtime.text") + " " + down, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + resID + "&period=0')");
/* 415 */               this.urls.put(FormatUtil.getString("am.webclient.reports.uptime") + " " + up, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + resID + "&period=0')");
/* 416 */               this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text") + " " + unmanage, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + resID + "&period=0')");
/* 417 */               this.urls.put(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") + " " + schedule, "javascript:fnOpenNewWindow('/showHistoryData.do?method=getAvailabilityData&resourceid=" + resID + "&period=0')");
/*     */             }
/*     */           }
/*     */           catch (IllegalStateException ise) {
/* 421 */             throw ise;
/*     */           }
/*     */           catch (Exception exp) {
/* 424 */             AMLog.fatal("Exception while getting availability graph in Report Graph", exp);
/* 425 */             exp.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/* 429 */             closeResultSet(set);
/*     */           }
/* 431 */           toReturn = ds;
/* 432 */           break;
/*     */         }
/*     */         
/*     */       }
/* 436 */       else if (type.equals("TRENDDOWNTIMEREPORT"))
/*     */       {
/* 438 */         period = "0";
/* 439 */         String interval = (String)this.params.get("interval");
/* 440 */         long[] timeStamps = new long[2];
/* 441 */         if ((period != null) && (period.equals("4")))
/*     */         {
/* 443 */           timeStamps[0] = ((Long)this.params.get("starttime")).longValue();
/* 444 */           timeStamps[1] = ((Long)this.params.get("endtime")).longValue();
/*     */         }
/*     */         else
/*     */         {
/* 448 */           timeStamps = ReportUtilities.getTimeStamp(period);
/*     */         }
/* 450 */         period = (String)this.params.get("period");
/*     */         
/* 452 */         DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 453 */         ArrayList dataList = (ArrayList)this.params.get("data");
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 458 */           Calendar cal = Calendar.getInstance();
/* 459 */           SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
/* 460 */           SimpleDateFormat sdf4 = new SimpleDateFormat("MMM-yyyy");
/* 461 */           Date d = new Date();
/* 462 */           for (int i = 0; i < dataList.size(); i++) {
/* 463 */             ArrayList a1 = (ArrayList)dataList.get(i);
/* 464 */             String mgValue = a1.get(3).toString();
/* 465 */             if ("HAI".equals(mgValue))
/*     */             {
/*     */ 
/* 468 */               HashMap data = (HashMap)a1.get(4);
/* 469 */               for (int j = 12; j > 0; j--) {
/* 470 */                 cal.setTime(new Date(System.currentTimeMillis()));
/* 471 */                 if ("day".equalsIgnoreCase(interval)) {
/* 472 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 86400000);
/*     */                 }
/* 474 */                 else if ("week".equalsIgnoreCase(interval)) {
/* 475 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 604800000L);
/*     */                 }
/*     */                 else {
/* 478 */                   AMLog.debug("Month Loop 1111 ===> : " + interval);
/* 479 */                   cal.add(2, -j + 1);
/*     */                 }
/*     */                 
/* 482 */                 String k = j + "";
/* 483 */                 HashMap temp = (HashMap)data.get(k);
/* 484 */                 long totaldowntime = 0L;
/* 485 */                 if ((temp.get("totaldowntime") != null) && (!"0 Secs".equals(temp.get("totaldowntime").toString()))) {
/* 486 */                   totaldowntime = Long.parseLong(temp.get("TotDown").toString());
/* 487 */                   AMLog.debug("===> : totaldowntime ===> : " + totaldowntime);
/*     */                 }
/* 489 */                 d.setHours(0);
/* 490 */                 d.setMinutes(0);
/* 491 */                 d.setSeconds(0);
/* 492 */                 if (!"month".equalsIgnoreCase(interval)) {
/* 493 */                   barset.setValue(totaldowntime / 60000L, "", sdf.format(d));
/*     */                 }
/*     */                 else {
/* 496 */                   barset.setValue(totaldowntime / 60000L, "", sdf4.format(cal.getTime()));
/*     */                 }
/* 498 */                 cal.add(2, 1);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (NullPointerException ex) {
/* 504 */           ex.printStackTrace();
/*     */         }
/*     */         
/* 507 */         toReturn = barset;
/*     */ 
/*     */       }
/* 510 */       else if (type.equals("TRENDDOWNCOUNTREPORT"))
/*     */       {
/* 512 */         period = "0";
/* 513 */         String interval = (String)this.params.get("interval");
/* 514 */         long[] timeStamps = new long[2];
/* 515 */         if ((period != null) && (period.equals("4")))
/*     */         {
/* 517 */           timeStamps[0] = ((Long)this.params.get("starttime")).longValue();
/* 518 */           timeStamps[1] = ((Long)this.params.get("endtime")).longValue();
/*     */         }
/*     */         else
/*     */         {
/* 522 */           timeStamps = ReportUtilities.getTimeStamp(period);
/*     */         }
/* 524 */         period = (String)this.params.get("period");
/* 525 */         DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 526 */         ArrayList dataList = (ArrayList)this.params.get("data");
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 531 */           Calendar cal1 = Calendar.getInstance();
/* 532 */           SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yy");
/* 533 */           SimpleDateFormat sdf3 = new SimpleDateFormat("MMM-yyyy");
/* 534 */           Date d = new Date();
/* 535 */           for (int i = 0; i < dataList.size(); i++) {
/* 536 */             ArrayList a1 = (ArrayList)dataList.get(i);
/* 537 */             String mgValue = a1.get(3).toString();
/* 538 */             if ("HAI".equals(mgValue))
/*     */             {
/*     */ 
/* 541 */               HashMap data = (HashMap)a1.get(4);
/* 542 */               for (int j = 12; j > 0; j--) {
/* 543 */                 cal1.setTime(new Date(System.currentTimeMillis()));
/* 544 */                 if ("day".equalsIgnoreCase(interval)) {
/* 545 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 86400000);
/* 546 */                 } else if ("week".equalsIgnoreCase(interval)) {
/* 547 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 604800000L);
/*     */                 }
/*     */                 else {
/* 550 */                   AMLog.debug("Month Loop 1111 ===> : " + interval);
/* 551 */                   cal1.add(2, -j + 1);
/*     */                 }
/*     */                 
/* 554 */                 String k = j + "";
/* 555 */                 HashMap temp = (HashMap)data.get(k);
/* 556 */                 int downcount = Integer.parseInt(temp.get("downcount").toString());
/* 557 */                 AMLog.debug("downcount values is ====> : " + downcount);
/* 558 */                 d.setHours(0);
/* 559 */                 d.setMinutes(0);
/* 560 */                 d.setSeconds(0);
/* 561 */                 if (!"month".equalsIgnoreCase(interval)) {
/* 562 */                   barset.setValue(downcount, "", sdf1.format(d));
/*     */                 }
/*     */                 else {
/* 565 */                   barset.setValue(downcount, "", sdf3.format(cal1.getTime()));
/*     */                 }
/* 567 */                 cal1.add(2, 1);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (NullPointerException ex) {}
/*     */         
/*     */ 
/*     */ 
/* 576 */         toReturn = barset;
/*     */       }
/*     */       else
/*     */       {
/* 580 */         if (type.equals("TRENDAVAILABILITYLINE"))
/*     */         {
/* 582 */           String haid = (String)this.params.get("id");
/* 583 */           String interval = (String)this.params.get("interval");
/* 584 */           AMLog.debug("Haid ===========> : " + interval);
/* 585 */           period = "0";
/* 586 */           long[] timeStamps = new long[2];
/* 587 */           if ((period != null) && (period.equals("4")))
/*     */           {
/* 589 */             timeStamps[0] = ((Long)this.params.get("starttime")).longValue();
/* 590 */             timeStamps[1] = ((Long)this.params.get("endtime")).longValue();
/*     */           }
/*     */           else
/*     */           {
/* 594 */             timeStamps = ReportUtilities.getTimeStamp(period);
/*     */           }
/* 596 */           period = (String)this.params.get("period");
/*     */           
/* 598 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 599 */           TimeSeriesCollection col = new TimeSeriesCollection();
/* 600 */           TimeSeries timechart = new TimeSeries(FormatUtil.getString("am.reporting.admin.summarymail.coloumn.availibility"), Minute.class);
/* 601 */           TimeSeries timechart1 = new TimeSeries(FormatUtil.getString("am.webclient.reports.TargetAvailability.text"), Minute.class);
/* 602 */           ArrayList dataList = (ArrayList)this.params.get("data");
/*     */           
/* 604 */           String query = "select PERCENTAVAIL from AM_SLO_APPLICATIONAVAILABLITY,AM_SLA_RESID_MAPPER,AM_SLO where  AM_SLO.ID=AM_SLO_APPLICATIONAVAILABLITY.SLO_ID and AM_SLA_RESID_MAPPER.SLA_ID =AM_SLO.SLA_ID and AM_SLA_RESID_MAPPER.RESID=" + haid;
/* 605 */           AMLog.debug("Target Availability Query : " + query);
/* 606 */           ResultSet set = null;
/* 607 */           long targetAvail = 0L;
/*     */           try
/*     */           {
/* 610 */             set = AMConnectionPool.executeQueryStmt(query);
/* 611 */             while (set.next())
/*     */             {
/* 613 */               targetAvail = set.getLong("PERCENTAVAIL");
/*     */             }
/*     */           } catch (Exception ex) {
/* 616 */             ex.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/* 620 */             closeResultSet(set);
/*     */           }
/*     */           try
/*     */           {
/* 624 */             Calendar cal2 = Calendar.getInstance();
/* 625 */             Date d = new Date();
/* 626 */             for (int i = 0; i < dataList.size(); i++)
/*     */             {
/* 628 */               ArrayList a1 = (ArrayList)dataList.get(i);
/* 629 */               String mgValue = a1.get(3).toString();
/* 630 */               if ("HAI".equals(mgValue))
/*     */               {
/*     */ 
/* 633 */                 HashMap data = (HashMap)a1.get(4);
/* 634 */                 for (int j = 12; j > 0; j--) {
/* 635 */                   cal2.setTime(new Date(System.currentTimeMillis()));
/* 636 */                   if ("day".equalsIgnoreCase(interval)) {
/* 637 */                     d = new Date(System.currentTimeMillis() - (j - 1) * 86400000);
/*     */                   }
/* 639 */                   else if ("week".equalsIgnoreCase(interval)) {
/* 640 */                     d = new Date(System.currentTimeMillis() - (j - 1) * 604800000L);
/*     */                   } else {
/* 642 */                     cal2.add(2, -j + 1);
/*     */                   }
/*     */                   
/* 645 */                   String k = j + "";
/* 646 */                   HashMap temp = (HashMap)data.get(k);
/* 647 */                   float uptime = 0.0F;
/* 648 */                   boolean addData = false;
/* 649 */                   if (temp.get("uptime").toString().matches("\\d+(\\.\\d+)?")) {
/* 650 */                     uptime = Float.parseFloat(temp.get("uptime").toString());
/* 651 */                     addData = true;
/* 652 */                     AMLog.info("UPTIME in ReportGraphs ===> : " + uptime);
/*     */                   }
/* 654 */                   d.setHours(0);
/* 655 */                   d.setMinutes(0);
/* 656 */                   d.setSeconds(0);
/* 657 */                   if (!"month".equalsIgnoreCase(interval)) {
/* 658 */                     if (addData) {
/* 659 */                       timechart.add(new Minute(d), uptime);
/*     */                     }
/* 661 */                     timechart1.add(new Minute(d), targetAvail);
/*     */                   } else {
/* 663 */                     cal2.set(5, 1);
/* 664 */                     cal2.set(11, 0);
/* 665 */                     cal2.set(12, 0);
/* 666 */                     cal2.set(13, 0);
/* 667 */                     if (addData) {
/* 668 */                       timechart.add(new Minute(cal2.getTime()), uptime);
/*     */                     }
/* 670 */                     timechart1.add(new Minute(cal2.getTime()), targetAvail);
/*     */                   }
/* 672 */                   cal2.add(2, 1);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (NullPointerException ex) {
/* 678 */             ex.printStackTrace();
/*     */           }
/* 680 */           col.addSeries(timechart);
/* 681 */           col.addSeries(timechart1);
/* 682 */           int[] y = new int[2];
/* 683 */           if (targetAvail == 0L) {
/* 684 */             int[] x = { 0 };
/* 685 */             y = (int[])x;
/*     */           } else {
/* 687 */             int[] x = { 0, 1 };
/* 688 */             y = (int[])x;
/*     */           }
/* 690 */           return new SubSeriesDataset(col, y);
/*     */         }
/*     */         
/* 693 */         if (type.equals("ATAGLANCE"))
/*     */         {
/* 695 */           DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 696 */           ArrayList dataList = (ArrayList)this.params.get("data");
/*     */           
/* 698 */           for (int i = 0; i < dataList.size(); i++)
/*     */           {
/* 700 */             Hashtable moHash = (Hashtable)dataList.get(i);
/* 701 */             barset.setValue((Float)moHash.get("VALUE"), "", (String)moHash.get("DISPLAYNAME") + "_#$@_resid=" + (String)moHash.get("RESOURCEID"));
/*     */           }
/* 703 */           toReturn = barset;
/*     */         }
/* 705 */         else if (type.equals("ATAGLANCEAVAILABILITY"))
/*     */         {
/* 707 */           String[] seriesNames = { FormatUtil.getString("am.webclient.reports.uptime"), FormatUtil.getString("am.reporttab.availablityreport.downtime.text"), FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text"), FormatUtil.getString("am.reporttab.availablityreport.scheduled.text") };
/* 708 */           ArrayList dataList = (ArrayList)this.params.get("data");
/* 709 */           String[] categories = new String[dataList.size()];
/* 710 */           Float[][] startValues = new Float[seriesNames.length][categories.length];
/* 711 */           Float[][] endValues = new Float[seriesNames.length][categories.length];
/*     */           
/* 713 */           for (int i = 0; i < dataList.size(); i++)
/*     */           {
/* 715 */             Hashtable moHash = (Hashtable)dataList.get(i);
/* 716 */             categories[i] = ((String)moHash.get("DISPLAYNAME") + "_#$@_resid=" + (String)moHash.get("RESOURCEID"));
/* 717 */             startValues[0][i] = Float.valueOf(0.0F);
/* 718 */             endValues[0][i] = ((Float)moHash.get("VALUE"));
/* 719 */             startValues[1][i] = Float.valueOf(0.0F);
/* 720 */             endValues[1][i] = ((Float)moHash.get("VALUE_DOWN"));
/* 721 */             startValues[2][i] = Float.valueOf(0.0F);
/* 722 */             endValues[2][i] = ((Float)moHash.get("VALUE_UNMAN"));
/* 723 */             startValues[3][i] = Float.valueOf(0.0F);
/* 724 */             endValues[3][i] = ((Float)moHash.get("VALUE_SCHED"));
/*     */           }
/* 726 */           toReturn = new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
/*     */         }
/* 728 */         else if (type.equals("EUMRESPONSETIME"))
/*     */         {
/* 730 */           Hashtable moHash = (Hashtable)this.params.get("data");
/* 731 */           String valToset = String.valueOf(moHash.get("VALUE"));
/* 732 */           int roundedValToSet = new BigDecimal(valToset).setScale(1, 0).intValue();
/* 733 */           toReturn = new DefaultValueDataset(roundedValToSet);
/*     */         }
/* 735 */         else if (type.equals("ATAGLANCETIMESERIES"))
/*     */         {
/*     */           try
/*     */           {
/* 739 */             ArrayList dataList = (ArrayList)this.params.get("data");
/* 740 */             TimeSeriesCollection col = new TimeSeriesCollection();
/* 741 */             for (int i = 0; i < dataList.size(); i++)
/*     */             {
/* 743 */               Hashtable mo = (Hashtable)dataList.get(i);
/* 744 */               TimeSeries ts = new TimeSeries(mo.get("DISPLAYNAME").toString(), Hour.class);
/* 745 */               String query = "select MINVALUE,MAXVALUE,ARCHIVEDTIME from " + mo.get("ARCHIVEDDATA_TABLENAME") + " where RESID=" + mo.get("RESOURCEID") + " and ATTRIBUTEID=" + mo.get("ATTRIBUTEID") + " and ARCHIVEDTIME>=" + mo.get("STARTTIME") + " and ARCHIVEDTIME<=" + mo.get("ENDTIME") + " and DURATION=1";
/* 746 */               ResultSet rs = null;
/*     */               try
/*     */               {
/* 749 */                 rs = AMConnectionPool.executeQueryStmt(query);
/* 750 */                 while ((rs != null) && (rs.next()))
/*     */                 {
/* 752 */                   float avg = (rs.getFloat("MINVALUE") + rs.getFloat("MAXVALUE")) / 2.0F;
/* 753 */                   Date d = new Date(rs.getLong("ARCHIVEDTIME"));
/* 754 */                   ts.addOrUpdate(new Hour(d), avg);
/*     */                 }
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 759 */                 e.printStackTrace();
/*     */               }
/*     */               finally
/*     */               {
/* 763 */                 if (rs != null)
/*     */                 {
/* 765 */                   AMConnectionPool.closeResultSet(rs);
/*     */                 }
/*     */               }
/* 768 */               col.addSeries(ts);
/*     */             }
/* 770 */             int[] x = new int[col.getSeriesCount()];
/* 771 */             for (int j = 0; j < col.getSeriesCount(); j++)
/*     */             {
/* 773 */               x[j] = j;
/*     */             }
/* 775 */             return new SubSeriesDataset(col, x);
/*     */ 
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 780 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 789 */     return toReturn;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 794 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 799 */     return "ReportDataProducer";
/*     */   }
/*     */   
/*     */   private final DefaultPieDataset getEventDistributionDataset(String period, String haid, String severity)
/*     */   {
/* 804 */     long[] timeStamps = ReportUtilities.getTimeStamp(period);
/* 805 */     DefaultPieDataset ds = new DefaultPieDataset();
/* 806 */     String query = "select DISPLAYNAME,count(*) as num  from Event,AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=SOURCE and SEVERITY=" + severity + " and AM_PARENTCHILDMAPPER.CHILDID=Event.SOURCE and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and TTIME >=" + timeStamps[0] + " and TTIME <=" + timeStamps[1] + " group by SOURCE order by num desc";
/* 807 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 808 */     ResultSet set = null;
/*     */     try
/*     */     {
/* 811 */       set = AMConnectionPool.executeQueryStmt(query);
/* 812 */       while (set.next())
/*     */       {
/* 814 */         int value = set.getInt(2);
/* 815 */         ds.setValue(set.getString(1) + "(" + value + ")", new Integer(value));
/*     */       }
/*     */     }
/*     */     catch (Exception exp) {
/* 819 */       exp.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 823 */       closeResultSet(set);
/*     */     }
/* 825 */     return ds;
/*     */   }
/*     */   
/*     */   private final void closeResultSet(ResultSet set)
/*     */   {
/*     */     try
/*     */     {
/* 832 */       if (set != null) {
/* 833 */         set.close();
/*     */       }
/*     */     } catch (Exception e) {
/* 836 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Object produceUrl(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 844 */     return this.urls;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\dataproducer\ReportGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */