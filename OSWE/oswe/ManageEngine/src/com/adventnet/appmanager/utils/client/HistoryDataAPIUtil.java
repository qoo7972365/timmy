/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*     */ import com.adventnet.awolf.chart.ChartInfo;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HistoryDataAPIUtil
/*     */ {
/*  30 */   private final long sevenDaysInMillis = 604800000L;
/*  31 */   private final long oneDaysInMillis = 86400000L;
/*  32 */   private final long thirtyDaysInMillis = 2592000000L;
/*  33 */   private final long oneYearInMillis = 31536000000L;
/*  34 */   private final long lastquatarInMillis = 7776000000L;
/*  35 */   private final long lasthalfInMillis = 15552000000L;
/*     */   
/*     */   public Hashtable getHistoryData(HttpServletRequest request, int resID, int attID, String period, Hashtable reportform)
/*     */   {
/*  39 */     Hashtable requestDetails = new Hashtable();
/*     */     
/*  41 */     ResultSet resNameSet = null;
/*  42 */     ResultSet titleSet = null;
/*  43 */     String businessRule = "oni";
/*  44 */     requestDetails.put("Period", period + "");
/*  45 */     requestDetails.put("ResourceId", resID + "");
/*  46 */     requestDetails.put("AttributeID", attID + "");
/*     */     
/*  48 */     String startdate = (String)reportform.get("startDate");
/*  49 */     String enddate = (String)reportform.get("endDate");
/*     */     
/*  51 */     String status = null;
/*  52 */     long customstartTime = 0L;
/*  53 */     long customendTime = 0L;
/*  54 */     boolean forbHr = false;
/*  55 */     long startTime = 0L;
/*  56 */     long endTime = System.currentTimeMillis();
/*  57 */     Hashtable bHourDetail = new Hashtable();
/*  58 */     if ((businessRule == null) || (businessRule.equals("oni")))
/*     */     {
/*  60 */       requestDetails.put("BusinessPeriod", "NA");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  65 */     String archivedTableName = DBUtil.getArchiveingTableName(String.valueOf(attID));
/*  66 */     if (period.equals("4"))
/*     */     {
/*  68 */       requestDetails.put("EndDate", enddate);
/*  69 */       requestDetails.put("StartDate", startdate);
/*  70 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  71 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*     */       
/*  73 */       if (customstartTime > customendTime)
/*     */       {
/*  75 */         status = "Start Time " + new Date(customstartTime) + " is greater than the End Time " + new Date(customendTime);
/*  76 */         startTime = customstartTime;
/*  77 */         endTime = customendTime;
/*     */       }
/*     */       else
/*     */       {
/*  81 */         startTime = customstartTime;
/*  82 */         endTime = customendTime;
/*  83 */         requestDetails.put("CustomeStartTime", Long.toString(customstartTime));
/*  84 */         requestDetails.put("CustomeEndTime", Long.toString(customendTime));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  89 */       Properties timeProp = getTimePeriod(startTime, endTime, period);
/*  90 */       startTime = ((Long)timeProp.get("STARTTIME")).longValue();
/*  91 */       endTime = ((Long)timeProp.get("ENDTIME")).longValue();
/*     */     }
/*     */     
/*  94 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  95 */     ArrayList list = new ArrayList();
/*  96 */     ArrayList avgList = new ArrayList();
/*  97 */     String attName = null;
/*  98 */     boolean isGenericWmi = false;
/*  99 */     long min = -1L;
/* 100 */     long max = -1L;
/* 101 */     float avg = -1.0F;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 106 */       String resNameQuery = "select DISPLAYNAME,TYPE from AM_ManagedObject where RESOURCEID=" + resID;
/*     */       
/*     */ 
/*     */ 
/* 110 */       resNameSet = AMConnectionPool.executeQueryStmt(resNameQuery);
/* 111 */       AMLog.info("#############resNameQuery####" + resNameQuery);
/* 112 */       List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/* 113 */       if (resNameSet.next())
/*     */       {
/*     */ 
/* 116 */         String resName = resNameSet.getString("DISPLAYNAME");
/* 117 */         AMLog.debug("RESTAPI: Historydata api RESourcename: " + resName + "#attID:" + attID + "#type:" + resNameSet.getString("TYPE"));
/*     */         
/*     */ 
/* 120 */         if (attID == 711)
/*     */         {
/* 122 */           HashMap alldisplayname = DBUtil.getDisplayNameForDisk();
/* 123 */           String name = resName;
/* 124 */           String dname1 = FormatUtil.findReplace(name, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 125 */           String[] temp = dname1.split(":");
/* 126 */           String s = alldisplayname.get(temp[0]).toString();
/* 127 */           if ((s != null) && (temp.length > 1))
/*     */           {
/* 129 */             dname1 = s + ":" + temp[1];
/*     */           }
/* 131 */           requestDetails.put("ResourceName", dname1);
/*     */         }
/* 133 */         else if (allSecondLevelAttribute.contains(attID + ""))
/*     */         {
/* 135 */           String displayname = ReportUtil.getDisplayNameForAttribute(resID);
/* 136 */           String dname2 = displayname + "_" + resName;
/* 137 */           if (dname2.indexOf("\\") != -1)
/*     */           {
/* 139 */             dname2 = FormatUtil.findReplace(dname2, "\\", "/");
/*     */           }
/* 141 */           requestDetails.put("ResourceName", dname2);
/*     */         }
/*     */         else
/*     */         {
/* 145 */           if (resName.indexOf("\\") != -1)
/*     */           {
/* 147 */             resName = FormatUtil.findReplace(resName, "\\", "/");
/*     */           }
/* 149 */           requestDetails.put("ResourceName", resName);
/*     */         }
/* 151 */         if (resNameSet.getString("TYPE").toLowerCase().startsWith("win32_"))
/*     */         {
/* 153 */           isGenericWmi = true;
/*     */         }
/*     */       }
/* 156 */       String titleQuery = "select DISPLAYNAME,ATTRIBUTE,UNITS,RESOURCETYPE from AM_ATTRIBUTES where ATTRIBUTEID=" + attID;
/* 157 */       if (isGenericWmi)
/*     */       {
/* 159 */         titleQuery = "SELECT AM_CAM_DC_ATTRIBUTES.DISPLAYNAME, AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTE, '' as UNITS,AM_ManagedObject.type as RESOURCETYPE FROM AM_CAM_DC_ATTRIBUTES left outer join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER cam on cam.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=cam.RESOURCEID WHERE AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + attID + " AND AM_ManagedObject.RESOURCEID=" + resID;
/*     */       }
/* 161 */       titleSet = AMConnectionPool.executeQueryStmt(titleQuery);
/*     */       
/* 163 */       if (titleSet.next())
/*     */       {
/* 165 */         String title = titleSet.getString("DISPLAYNAME");
/* 166 */         attName = titleSet.getString("ATTRIBUTE");
/* 167 */         requestDetails.put("AttributeName", title);
/* 168 */         String unit = titleSet.getString("UNITS");
/* 169 */         if (unit == null)
/*     */         {
/* 171 */           unit = "";
/*     */         }
/* 173 */         requestDetails.put("Unit", unit);
/* 174 */         String serverType = titleSet.getString("RESOURCETYPE");
/* 175 */         requestDetails.put("ResourceType", serverType);
/*     */       }
/*     */       
/* 178 */       ResultSet set = null;
/* 179 */       ResultSet set1 = null;
/* 180 */       if ((period != null) && (period.equals("20")))
/*     */       {
/* 182 */         ArrayList raw = new ArrayList();
/* 183 */         String ResName = "";
/* 184 */         String tableName = "";
/* 185 */         String resourceId = "";
/* 186 */         String valueCol = "";
/* 187 */         String attidCol = "";
/* 188 */         String colTime = "";
/* 189 */         String stTime = "";
/* 190 */         String enTime = "";
/* 191 */         String Condition = "";
/* 192 */         String expression = "";
/* 193 */         if (!isGenericWmi)
/*     */         {
/* 195 */           String paramQuery = "select Datatable,resid_col,value_col,coltime_val,attid_col,expression from AM_ATTRIBUTES_EXT where attributeid=" + attID;
/* 196 */           AMLog.debug("paramQuery=======>" + paramQuery);
/* 197 */           set = AMConnectionPool.executeQueryStmt(paramQuery);
/* 198 */           while (set.next())
/*     */           {
/* 200 */             tableName = set.getString("Datatable");
/* 201 */             resourceId = set.getString("resid_col");
/* 202 */             valueCol = tableName.equalsIgnoreCase("AM_CONFIGURATION_INFO") ? set.getString("value_col") : DBQueryUtil.escapeColumn(set.getString("value_col"), attID + "");
/* 203 */             colTime = set.getString("coltime_val");
/* 204 */             attidCol = set.getString("attid_col");
/* 205 */             expression = set.getString("expression");
/* 206 */             valueCol = valueCol + expression;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 211 */           attidCol = "ATTRIBUTEID";
/* 212 */           tableName = "AM_CAM_COLUMNAR_DATA";
/* 213 */           colTime = "COLLECTIONTIME";
/* 214 */           valueCol = "VALUE";
/* 215 */           resourceId = "ROWID";
/*     */         }
/* 217 */         if (!attidCol.equals("-1"))
/*     */         {
/* 219 */           Condition = " and attributeid=" + attID;
/*     */         }
/* 221 */         AMLog.debug("businessRUle" + businessRule);
/* 222 */         if (businessRule != null)
/*     */         {
/*     */ 
/* 225 */           String valueQuery = "select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "=" + resID + Condition + " order by " + colTime + " desc";
/*     */           
/* 227 */           if (resourceId.equals("RESOURCENAME"))
/*     */           {
/* 229 */             String NameQuery = "select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resID;
/* 230 */             set = AMConnectionPool.executeQueryStmt(NameQuery);
/* 231 */             if (set.next())
/*     */             {
/* 233 */               ResName = set.getString("RESOURCENAME");
/* 234 */               valueQuery = "select " + colTime + "," + valueCol + " from " + tableName + " where " + resourceId + "='" + ResName + "'" + Condition + " order by " + colTime + " desc";
/*     */             }
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 240 */             AMLog.debug("the value query====>" + valueQuery);
/* 241 */             set = AMConnectionPool.executeQueryStmt(valueQuery);
/* 242 */             while (set.next())
/*     */             {
/*     */ 
/* 245 */               Hashtable rowtab = new Hashtable();
/* 246 */               Properties prop = new Properties();
/* 247 */               long collectionTime = set.getLong(1);
/* 248 */               String time = FormatUtil.formatDT(collectionTime + "");
/* 249 */               String rValueD = "-";
/*     */               try
/*     */               {
/* 252 */                 rValueD = set.getLong(2) + "";
/* 253 */                 if (set.getLong(2) < 0L)
/*     */                 {
/* 255 */                   rValueD = "-";
/*     */                 }
/*     */                 
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 261 */                 rValueD = set.getString(2);
/*     */               }
/*     */               
/* 264 */               rowtab.put("CollectionTime", collectionTime + "");
/* 265 */               rowtab.put("Value", rValueD);
/* 266 */               rowtab.put("DateTime", time);
/* 267 */               raw.add(rowtab);
/*     */             }
/*     */             
/*     */ 
/* 271 */             requestDetails.put("RawData", raw);
/* 272 */             if ((raw == null) || ((raw != null) && (raw.size() == 0)))
/*     */             {
/* 274 */               enTime = String.valueOf(System.currentTimeMillis());
/* 275 */               long beginTime = Long.parseLong(enTime) - 18000000L;
/* 276 */               String error = FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new Date(beginTime).toString(), new Date(Long.parseLong(enTime)).toString() });
/* 277 */               AMLog.debug("the status error==>" + error);
/* 278 */               requestDetails.put("Status", error);
/* 279 */               requestDetails.put("StartTime", Long.valueOf(beginTime));
/* 280 */               requestDetails.put("EndTime", enTime);
/*     */               
/* 282 */               requestDetails.put("StartDateTime", FormatUtil.formatDT(beginTime + ""));
/* 283 */               requestDetails.put("EndDateTime", FormatUtil.formatDT(enTime + ""));
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 288 */               enTime = String.valueOf(System.currentTimeMillis());
/* 289 */               requestDetails.put("Status", "SUCCESS");
/*     */               
/* 291 */               stTime = ((Hashtable)raw.get(raw.size() - 1)).get("CollectionTime").toString();
/* 292 */               enTime = ((Hashtable)raw.get(0)).get("CollectionTime").toString();
/*     */               
/* 294 */               requestDetails.put("StartTime", stTime);
/* 295 */               requestDetails.put("EndTime", enTime);
/*     */               
/* 297 */               requestDetails.put("StartDateTime", FormatUtil.formatDT(stTime + ""));
/* 298 */               requestDetails.put("EndDateTime", FormatUtil.formatDT(enTime + ""));
/*     */             }
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 303 */             ex.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 310 */         long[] timeStamp = ReportUtilities.getDailyStartEndTime(startTime, endTime, archivedTableName);
/* 311 */         requestDetails.put("StartTime", timeStamp[0] + "");
/* 312 */         requestDetails.put("EndTime", timeStamp[1] + "");
/*     */         
/* 314 */         requestDetails.put("StartDateTime", FormatUtil.formatDT(timeStamp[0] + ""));
/* 315 */         requestDetails.put("EndDateTime", FormatUtil.formatDT(timeStamp[1] + ""));
/*     */         
/* 317 */         requestDetails.put("dailyStime", timeStamp[2] + "");
/* 318 */         requestDetails.put("dailyEtime", timeStamp[3] + "");
/*     */         
/* 320 */         String query = "select ARCHIVEDTIME,MINVALUE,MAXVALUE,(TOTAL/TOTALCOUNT) as AVG  from " + archivedTableName + " where RESID=" + resID + " and ATTRIBUTEID=" + attID;
/* 321 */         String dailyRptCondition = " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime + " order by ARCHIVEDTIME desc";
/*     */         
/* 323 */         if (timeStamp[2] > 0L)
/*     */         {
/* 325 */           dailyRptCondition = " and (DURATION=1 and ARCHIVEDTIME <= " + timeStamp[1] + " and ARCHIVEDTIME >= " + timeStamp[0] + " OR DURATION=2 and ARCHIVEDTIME <=" + timeStamp[3] + " and ARCHIVEDTIME >= " + timeStamp[2] + ") order by ARCHIVEDTIME desc";
/*     */         }
/* 327 */         query = query + dailyRptCondition;
/*     */         
/*     */         try
/*     */         {
/* 331 */           set = AMConnectionPool.executeQueryStmt(query);
/* 332 */           double minAvgValue = -1.0D;
/* 333 */           double maxAvgValue = -1.0D;
/*     */           
/* 335 */           Calendar cal = Calendar.getInstance();
/*     */           
/* 337 */           while (set.next())
/*     */           {
/* 339 */             boolean isbHrdata = false;
/*     */             try
/*     */             {
/* 342 */               if (bHourDetail.size() != 0)
/*     */               {
/* 344 */                 cal.setTimeInMillis(set.getLong("ARCHIVEDTIME"));
/*     */                 
/* 346 */                 int weekday = cal.get(7) - 1;
/* 347 */                 int hour = cal.get(11);
/* 348 */                 int BHr_Start = ((Integer)bHourDetail.get(weekday + "_StHour")).intValue();
/* 349 */                 int BHr_End = ((Integer)bHourDetail.get(weekday + "_EndHour")).intValue();
/* 350 */                 if ((BHr_Start != 25) && (BHr_End != 25) && (BHr_Start < hour) && (BHr_End >= hour))
/*     */                 {
/* 352 */                   isbHrdata = true;
/*     */                 }
/*     */               }
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 358 */               AMLog.debug("############# Error in Bhour ############" + ex.toString());
/*     */             }
/*     */             
/* 361 */             if ((!forbHr) || ((forbHr) && (isbHrdata)))
/*     */             {
/* 363 */               long archivedTime = set.getLong("ARCHIVEDTIME");
/* 364 */               long minValue = set.getLong("MINVALUE");
/* 365 */               long maxValue = set.getLong("MAXVALUE");
/* 366 */               double avgValue = set.getDouble("AVG");
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 371 */               if ((attID == 224) || (attID == 11)) {
/* 372 */                 minValue /= 1024L;
/* 373 */                 maxValue /= 1024L;
/* 374 */                 avgValue /= 1024.0D;
/*     */               }
/* 376 */               else if ((attID >= 3608) && (attID <= 3619)) {
/* 377 */                 minValue /= 1048576L;
/* 378 */                 maxValue /= 1048576L;
/* 379 */                 avgValue /= 1048576.0D;
/*     */               }
/* 381 */               if (avgValue > maxAvgValue) {
/* 382 */                 maxAvgValue = avgValue;
/*     */               }
/* 384 */               if ((minAvgValue == -1.0D) || (avgValue < minAvgValue)) {
/* 385 */                 minAvgValue = avgValue;
/*     */               }
/* 387 */               Properties prop = new Properties();
/* 388 */               prop.put("ArchivedTime", archivedTime + "");
/* 389 */               prop.put("MinValue", minValue + "");
/* 390 */               prop.put("MaxValue", maxValue + "");
/* 391 */               prop.put("AvgValue", avgValue + "");
/* 392 */               prop.put("DateTime", FormatUtil.formatDT(archivedTime + ""));
/* 393 */               list.add(prop);
/*     */               
/* 395 */               avgList.add(new Float(avgValue));
/*     */             }
/*     */           }
/* 398 */           requestDetails.put("ArchiveData", list);
/* 399 */           requestDetails.put("MinAvgValue", minAvgValue + "");
/* 400 */           requestDetails.put("MaxAvgValue", maxAvgValue + "");
/*     */         }
/*     */         catch (Exception ee)
/*     */         {
/* 404 */           AMLog.debug("HistoryDataAction : Error Occured ====>" + ee.getMessage());
/*     */         }
/*     */         finally {
/* 407 */           closeResultSet(set);
/*     */         }
/*     */         
/* 410 */         if (list.size() == 0)
/*     */         {
/* 412 */           status = "Error";
/* 413 */           requestDetails.put("Status", status);
/*     */         }
/*     */         else
/*     */         {
/* 417 */           status = "SUCCESS";
/* 418 */           Properties prp = (Properties)list.get(0);
/* 419 */           String etime = (String)prp.get("ArchivedTime");
/* 420 */           requestDetails.put("Endtime", etime);
/*     */           
/* 422 */           prp = (Properties)list.get(list.size() - 1);
/* 423 */           String stime = (String)prp.get("ArchivedTime");
/* 424 */           requestDetails.put("StartTime", stime);
/* 425 */           requestDetails.put("Status", status);
/*     */         }
/*     */         
/*     */ 
/* 429 */         if ((min == -1L) && (status.equals("SUCCESS")))
/*     */         {
/* 431 */           String bhr_condition = "";
/* 432 */           if (forbHr)
/*     */           {
/* 434 */             ArrayList blist = SegmentReportUtil.getBizHourTimeStamps(startTime, endTime, resID, attID, archivedTableName, bHourDetail);
/* 435 */             for (int i = 0; i < blist.size(); i++)
/*     */             {
/* 437 */               Hashtable time_stamps = (Hashtable)blist.get(i);
/* 438 */               if ((bhr_condition != null) && (bhr_condition.length() > 1))
/*     */               {
/* 440 */                 bhr_condition = bhr_condition + " or (ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*     */               }
/*     */               else
/*     */               {
/* 444 */                 bhr_condition = "(ARCHIVEDTIME > " + (Long)time_stamps.get("start") + " and ARCHIVEDTIME <= " + (Long)time_stamps.get("end") + ")";
/*     */               }
/*     */             }
/* 447 */             query = "select min(MINVALUE) as MIN,max(MAXVALUE) as MAX,(sum(TOTAL)/sum(TOTALCOUNT)) as AVG from " + archivedTableName + " where RESID=" + resID + " and ATTRIBUTEID=" + attID + " and DURATION=1 and (" + bhr_condition + ")";
/*     */             
/* 449 */             AMLog.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ MIN-MAX-AVG CONDITION $$$$$ " + query);
/*     */           }
/*     */           else
/*     */           {
/* 453 */             query = "select min(MINVALUE) as MIN,max(MAXVALUE) as MAX,(sum(TOTAL)/sum(TOTALCOUNT)) as AVG from " + archivedTableName + " where RESID=" + resID + " and ATTRIBUTEID=" + attID + " and DURATION=1 and ARCHIVEDTIME <= " + endTime + " and ARCHIVEDTIME >= " + startTime;
/*     */           }
/*     */           try
/*     */           {
/* 457 */             set1 = AMConnectionPool.executeQueryStmt(query);
/* 458 */             if (set1.next())
/*     */             {
/* 460 */               min = set1.getLong("MIN");
/* 461 */               max = set1.getLong("MAX");
/* 462 */               avg = set1.getFloat("AVG");
/*     */               
/* 464 */               if ((attID == 224) || (attID == 11)) {
/* 465 */                 min /= 1024L;
/* 466 */                 max /= 1024L;
/* 467 */                 avg /= 1024.0F;
/*     */               }
/* 469 */               else if ((attID >= 3608) && (attID <= 3619)) {
/* 470 */                 min /= 1048576L;
/* 471 */                 max /= 1048576L;
/* 472 */                 avg /= 1048576.0F;
/*     */               }
/* 474 */               requestDetails.put("MinValue", String.valueOf(min));
/* 475 */               requestDetails.put("MaxValue", String.valueOf(max));
/* 476 */               requestDetails.put("AvgValue", String.valueOf(avg));
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 481 */             e.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/* 485 */             AMConnectionPool.closeStatement(set1);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 491 */       String brQuery = "select ID,NAME from AM_BUSINESSHOURSDETAILS where STATUS='enable'";
/* 492 */       ResultSet rs_br = null;
/*     */       try
/*     */       {
/* 495 */         rs_br = AMConnectionPool.executeQueryStmt(brQuery);
/* 496 */         ArrayList brules = new ArrayList();
/* 497 */         while (rs_br.next())
/*     */         {
/* 499 */           Properties dataProps = new Properties();
/* 500 */           dataProps.setProperty("label", rs_br.getString("NAME"));
/* 501 */           dataProps.setProperty("value", rs_br.getString("ID"));
/* 502 */           brules.add(dataProps);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 507 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 510 */         closeResultSet(rs_br);
/*     */       }
/*     */       
/* 513 */       ArrayList thresholdList = getThresholdDetailsForHistoryData(resID, attID, true);
/* 514 */       requestDetails.put("ThresholdDetails", thresholdList);
/* 515 */       requestDetails.put("Period", period);
/*     */       
/* 517 */       if (period.equals("20"))
/*     */       {
/* 519 */         requestDetails.put("ReportType", FormatUtil.getString("am.restapi.polleddata.txt"));
/*     */       }
/* 521 */       else if (period.equals("-7"))
/*     */       {
/* 523 */         requestDetails.put("ReportType", FormatUtil.getString("am.restapi.last7days.txt"));
/*     */       }
/* 525 */       else if (period.equals("-30"))
/*     */       {
/* 527 */         requestDetails.put("ReportType", FormatUtil.getString("am.restapi.last30days.txt"));
/*     */       }
/*     */       else
/*     */       {
/* 531 */         requestDetails.put("ReportType", "NA");
/*     */       }
/*     */       
/* 534 */       String unit = (String)requestDetails.get("Unit");
/* 535 */       String yaxis = FormatUtil.getString((String)requestDetails.get("AttributeName")) + ("".equals(unit) ? " " : new StringBuilder().append(" (").append(unit).append(")").toString());
/* 536 */       HistoryDataGraphUtil graph = new HistoryDataGraphUtil();
/*     */       
/* 538 */       if (period.equals("20"))
/*     */       {
/* 540 */         ChartInfo cinfo = new ChartInfo();
/*     */         
/* 542 */         if ((businessRule != null) && (!businessRule.equals("oni")))
/*     */         {
/* 544 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period, businessRule);
/*     */ 
/*     */ 
/*     */         }
/* 548 */         else if ((period != null) && (period.equals("20")))
/*     */         {
/* 550 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period, businessRule);
/*     */         }
/*     */         else
/*     */         {
/* 554 */           graph.setParam(String.valueOf(resID), String.valueOf(attID), period);
/*     */         }
/*     */         
/*     */ 
/* 558 */         String image = null;
/* 559 */         cinfo.setDataSet(graph);
/* 560 */         cinfo.setHeight("200");
/* 561 */         cinfo.setWidth("320");
/* 562 */         cinfo.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/* 563 */         cinfo.setYaxisLabel(yaxis);
/* 564 */         cinfo.setShape(true);
/* 565 */         cinfo.setCustomDateAxis(true);
/* 566 */         cinfo.setCustomAngle(270.0D);
/*     */         
/* 568 */         image = cinfo.getTimeChartAsJPG();
/* 569 */         if (image.contains("webclient"))
/*     */         {
/* 571 */           image = "/" + image.substring(image.indexOf("webclient"));
/*     */         }
/* 573 */         image = image.replaceAll("\\\\", "/");
/* 574 */         requestDetails.put("AttributeImage", image);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/*     */ 
/* 583 */       exp.printStackTrace();
/*     */     }
/*     */     finally {
/* 586 */       closeResultSet(resNameSet);
/* 587 */       closeResultSet(titleSet);
/*     */     }
/* 589 */     AMLog.debug("printing details:" + requestDetails);
/* 590 */     return requestDetails;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet set) {
/* 594 */     if (set != null)
/*     */     {
/* 596 */       AMConnectionPool.closeStatement(set);
/*     */     }
/*     */   }
/*     */   
/* 600 */   public Properties getTimePeriod(long startTime, long endTime, String period) { return getTimePeriod(startTime, endTime, period, null); }
/*     */   
/*     */   public ArrayList getThresholdDetailsForHistoryData(int resID, int attID, boolean includeunit) {
/* 603 */     Hashtable thresholdDetails = new Hashtable();
/* 604 */     ArrayList thresholdList = new ArrayList();
/* 605 */     thresholdList.add(thresholdDetails);
/*     */     try {
/* 607 */       ArrayList thresholdValues = FaultUtil.getThresholdDetailsForHistoryData(resID, attID, includeunit);
/* 608 */       if (!thresholdValues.isEmpty()) {
/* 609 */         thresholdDetails.put("Critical", (String)thresholdValues.get(0));
/* 610 */         thresholdDetails.put("Warning", (String)thresholdValues.get(1));
/* 611 */         thresholdDetails.put("Clear", (String)thresholdValues.get(2));
/* 612 */         thresholdDetails.put("Unit", (String)thresholdValues.get(3));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 616 */       e.printStackTrace();
/*     */     }
/* 618 */     return thresholdList;
/*     */   }
/*     */   
/* 621 */   public Properties getTimePeriod(long startTime, long endTime, String period, Properties customPeriod) { Calendar cal = new GregorianCalendar();
/* 622 */     Calendar cal1 = new GregorianCalendar();
/*     */     
/* 624 */     int dayofweek = cal1.get(7);
/* 625 */     Properties prop = new Properties();
/* 626 */     if (period.equals("0"))
/*     */     {
/* 628 */       Date currentTimeAsDate = new Date(endTime);
/* 629 */       Calendar cldr = Calendar.getInstance();
/* 630 */       cldr.setTime(currentTimeAsDate);
/* 631 */       cldr.set(11, 0);
/* 632 */       cldr.set(13, 0);
/* 633 */       cldr.set(14, 0);
/* 634 */       cldr.set(12, 0);
/* 635 */       startTime = cldr.getTime().getTime();
/*     */     }
/* 637 */     else if ((period.equals("-7")) || (period.equals("1")))
/*     */     {
/* 639 */       startTime = endTime - 604800000L;
/*     */     }
/* 641 */     else if ((period.equals("-30")) || (period.equals("2")))
/*     */     {
/* 643 */       startTime = endTime - 2592000000L;
/*     */     }
/* 645 */     else if ((period.equals("-5")) || (period.equals("5")))
/*     */     {
/* 647 */       startTime = endTime - 31536000000L;
/*     */ 
/*     */     }
/* 650 */     else if (period.equals("6")) {
/* 651 */       cal.set(7, 2);
/* 652 */       cal.set(11, 0);
/* 653 */       cal.set(12, 0);
/* 654 */       cal.set(13, 0);
/* 655 */       cal.set(14, 0);
/*     */       
/* 657 */       long lday = cal.getTimeInMillis();
/* 658 */       startTime = lday;
/*     */       
/*     */ 
/* 661 */       if (dayofweek == 7)
/*     */       {
/* 663 */         cal.set(7, 1);
/* 664 */         cal.set(11, 0);
/* 665 */         cal.set(12, 0);
/* 666 */         cal.set(13, 0);
/* 667 */         cal.set(14, 0);
/*     */         
/* 669 */         long lweek1 = cal.getTimeInMillis();
/* 670 */         startTime = lweek1;
/* 671 */         startTime += 86400000L;
/* 672 */         endTime = startTime + 432000000L;
/*     */       }
/* 674 */       if (dayofweek == 1) {
/* 675 */         cal.set(7, 1);
/* 676 */         cal.set(11, 0);
/* 677 */         cal.set(12, 0);
/* 678 */         cal.set(13, 0);
/* 679 */         cal.set(14, 0);
/*     */         
/* 681 */         long lweek1 = cal.getTimeInMillis();
/* 682 */         startTime = lweek1 - 604800000L;
/* 683 */         startTime += 86400000L;
/* 684 */         endTime = startTime + 432000000L;
/*     */       }
/*     */       
/*     */     }
/* 688 */     else if (period.equals("7")) {
/* 689 */       cal.set(5, 1);
/* 690 */       cal.set(11, 0);
/* 691 */       cal.set(12, 0);
/* 692 */       cal.set(13, 0);
/* 693 */       cal.set(14, 0);
/*     */       
/* 695 */       long lmonth = cal.getTimeInMillis();
/* 696 */       startTime = lmonth;
/*     */ 
/*     */     }
/* 699 */     else if (period.equals("11")) {
/* 700 */       int i = cal.get(2);
/*     */       
/* 702 */       cal.set(2, i - 1);
/*     */       
/* 704 */       cal.set(5, 1);
/*     */       
/* 706 */       cal.set(11, 0);
/* 707 */       cal.set(12, 0);
/* 708 */       cal.set(13, 0);
/* 709 */       cal.set(14, 0);
/*     */       
/* 711 */       long lastmonth = cal.getTimeInMillis();
/* 712 */       startTime = lastmonth;
/* 713 */       int maxDaysInMonth = cal.getActualMaximum(5);
/* 714 */       endTime = startTime + maxDaysInMonth * 24 * 60 * 60 * 1000L;
/*     */ 
/*     */     }
/* 717 */     else if (period.equals("12"))
/*     */     {
/* 719 */       cal.set(7, 1);
/* 720 */       cal.set(11, 0);
/* 721 */       cal.set(12, 0);
/* 722 */       cal.set(13, 0);
/* 723 */       cal.set(14, 0);
/*     */       
/* 725 */       long lweek = cal.getTimeInMillis();
/* 726 */       startTime = lweek - 604800000L;
/* 727 */       endTime = startTime + 604800000L;
/*     */ 
/*     */ 
/*     */     }
/* 731 */     else if (period.equals("8")) {
/* 732 */       cal.set(6, 1);
/* 733 */       cal.set(11, 0);
/* 734 */       cal.set(12, 0);
/* 735 */       cal.set(13, 0);
/* 736 */       cal.set(14, 0);
/*     */       
/* 738 */       long lyear = cal.getTimeInMillis();
/* 739 */       startTime = lyear;
/*     */ 
/*     */     }
/* 742 */     else if (period.equals("9")) {
/* 743 */       int i = cal.get(2);
/* 744 */       if (i < 3) {
/* 745 */         cal.set(2, 0);
/*     */       }
/* 747 */       else if (i < 6) {
/* 748 */         cal.set(2, 3);
/*     */       }
/* 750 */       else if (i < 9) {
/* 751 */         cal.set(2, 6);
/*     */       }
/*     */       else {
/* 754 */         cal.set(2, 9);
/*     */       }
/* 756 */       cal.set(5, 1);
/* 757 */       cal.set(11, 0);
/* 758 */       cal.set(12, 0);
/* 759 */       cal.set(13, 0);
/* 760 */       cal.set(14, 0);
/*     */       
/* 762 */       long lqatar = cal.getTimeInMillis();
/* 763 */       startTime = lqatar;
/*     */ 
/*     */     }
/* 766 */     else if (period.equals("3"))
/*     */     {
/* 768 */       Date currentTimeAsDate = new Date(endTime);
/* 769 */       Calendar cldr = Calendar.getInstance();
/* 770 */       cldr.setTime(currentTimeAsDate);
/* 771 */       cldr.add(7, -1);
/* 772 */       cldr.set(11, 0);
/* 773 */       cldr.set(13, 0);
/* 774 */       cldr.set(14, 0);
/* 775 */       cldr.set(12, 0);
/* 776 */       startTime = cldr.getTime().getTime();
/* 777 */       cldr.setTime(currentTimeAsDate);
/* 778 */       cldr.set(11, 0);
/* 779 */       cldr.set(13, 0);
/* 780 */       cldr.set(14, 0);
/* 781 */       cldr.set(12, 0);
/* 782 */       endTime = cldr.getTime().getTime();
/*     */     }
/* 784 */     else if ((period.equals("15")) && (customPeriod != null))
/*     */     {
/* 786 */       Calendar calendar = Calendar.getInstance();
/* 787 */       calendar.set(Integer.parseInt(customPeriod.getProperty("YEAR")), Integer.parseInt(customPeriod.getProperty("MONTH")), Integer.parseInt(customPeriod.getProperty("DATE")));
/* 788 */       calendar.set(11, 0);
/* 789 */       calendar.set(13, 0);
/* 790 */       calendar.set(14, 0);
/* 791 */       calendar.set(12, 0);
/* 792 */       startTime = calendar.getTime().getTime();
/* 793 */       endTime = startTime + 86399000L;
/*     */ 
/*     */     }
/* 796 */     else if ((period.equals("16")) && (customPeriod != null))
/*     */     {
/* 798 */       int selectedWeek = Integer.parseInt(customPeriod.getProperty("WEEK"));
/* 799 */       int selectedYear = Integer.parseInt(customPeriod.getProperty("YEAR"));
/* 800 */       int selectedmonth = Integer.parseInt(customPeriod.getProperty("MONTH"));
/* 801 */       Calendar calendar = Calendar.getInstance();
/* 802 */       calendar.set(selectedYear, selectedmonth, 1);
/* 803 */       calendar.set(11, 0);
/* 804 */       calendar.set(13, 0);
/* 805 */       calendar.set(14, 0);
/* 806 */       calendar.set(12, 0);
/* 807 */       int noOfdaysinMonth = calendar.getActualMaximum(5);
/* 808 */       int startingDayOfMonth = calendar.get(7);
/* 809 */       startTime = calendar.getTimeInMillis();
/* 810 */       long temendTime = startTime + 604800000L;
/*     */       
/* 812 */       if (selectedWeek == 1) {
/* 813 */         int addDays = 8 - startingDayOfMonth;
/* 814 */         endTime = startTime + addDays * 86400000L;
/*     */       }
/* 816 */       else if ((selectedWeek == 2) || (selectedWeek == 3)) {
/* 817 */         int daysInLastWeek = selectedWeek == 2 ? 8 : 15;
/* 818 */         int noOfdaySpent = daysInLastWeek - startingDayOfMonth + 1;
/* 819 */         calendar.set(selectedYear, selectedmonth, noOfdaySpent);
/* 820 */         startTime = calendar.getTimeInMillis();
/* 821 */         endTime = startTime + 604800000L;
/*     */       }
/*     */       else {
/* 824 */         int daysInLastWeek = 22;
/* 825 */         if (selectedWeek == 5) {
/* 826 */           daysInLastWeek = 29;
/*     */         }
/* 828 */         else if (selectedWeek == 6) {
/* 829 */           daysInLastWeek = 36;
/*     */         }
/* 831 */         int noOfdaySpent = daysInLastWeek - startingDayOfMonth + 1;
/* 832 */         calendar.set(selectedYear, selectedmonth, noOfdaySpent);
/* 833 */         startTime = calendar.getTimeInMillis();
/* 834 */         if (noOfdaySpent + 7 > noOfdaysinMonth) {
/* 835 */           int diff = noOfdaysinMonth - noOfdaySpent + 1;
/* 836 */           endTime = startTime + diff * 86400000L;
/*     */         }
/*     */         else {
/* 839 */           endTime = startTime + 604800000L;
/*     */         }
/*     */       }
/* 842 */       endTime -= 1000L;
/*     */     }
/* 844 */     prop.put("STARTTIME", new Long(startTime));
/* 845 */     prop.put("ENDTIME", new Long(endTime));
/* 846 */     return prop;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\HistoryDataAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */