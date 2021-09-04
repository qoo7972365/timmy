/*      */ package com.adventnet.appmanager.reporting;
/*      */ 
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.datacorrection.AMDataCorrectionUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import com.adventnet.appmanager.util.StartUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ReportUtilities
/*      */ {
/*      */   private static final long SEVENDAYS = 604800000L;
/*      */   private static final long THIRTYDAYS = 2592000000L;
/*      */   private static final long THREEMONTHS = 7776000000L;
/*      */   private static final long SIXMONTHS = 15552000000L;
/*      */   private static final long ONEYEAR = 31536000000L;
/*      */   private static final long LASTHALF = 15552000000L;
/*   52 */   private static int hourlyCleanupDays = -1;
/*   53 */   private static int dailyCleanupDays = -2;
/*      */   
/*   55 */   private static boolean isForecastReport = false;
/*      */   
/*      */   public static final int LAST_24_HOURS = 1;
/*      */   public static final int LAST_7_DAYS = 7;
/*      */   public static final int LAST_30_DAYS = 2;
/*      */   public static final int LAST_6_HOURS = 4;
/*      */   public static final int YESTERDAY = 3;
/*   62 */   private static long START_TIME = 0L;
/*   63 */   private static long END_TIME = 0L;
/*   64 */   private static String allResourcetypes = "";
/*   65 */   private static DecimalFormat fourDForm = new DecimalFormat("#.####");
/*      */   
/*      */   static {
/*   68 */     for (int i = 0; i < Constants.categoryLink.length; i++)
/*      */     {
/*   70 */       allResourcetypes = allResourcetypes + "'" + Constants.categoryLink[i] + "'" + ",";
/*      */     }
/*   72 */     allResourcetypes += "'QA'";
/*      */   }
/*      */   
/*      */   public static float roundOff(float f1, int roundoff) {
/*   76 */     try { float f2 = (float)Math.pow(10.0D, roundoff);
/*   77 */       f1 = Math.round(f1 * f2) / f2;
/*      */     } catch (Exception ex) {
/*   79 */       ex.printStackTrace();
/*      */     }
/*   81 */     return f1;
/*      */   }
/*      */   
/*      */   public static long getTotalDurationFromBH(long startTime, long endTime, String id)
/*      */   {
/*   86 */     AMLog.debug("inside getTotalDurationFromBH ...............");
/*   87 */     AMLog.debug("startTime : " + startTime + "\tendTime : " + endTime);
/*   88 */     long totalDuration = 0L;
/*      */     
/*   90 */     HashMap map = getBusinessRule(id);
/*   91 */     Properties prop = new Properties();
/*   92 */     prop.putAll(map);
/*   93 */     AMLog.debug("BUSINESS HOUR PROPS : " + prop);
/*   94 */     Calendar startCal = Calendar.getInstance();
/*   95 */     startCal.setTimeInMillis(startTime);
/*   96 */     Calendar endCal = Calendar.getInstance();
/*   97 */     endCal.setTimeInMillis(endTime);
/*      */     
/*   99 */     long totalNoOfDays = (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / 86400000L;
/*  100 */     long endTimeInMin = endCal.get(11) * 60 + endCal.get(12);
/*  101 */     boolean firstDay = true;
/*  102 */     boolean temp = true;
/*  103 */     int i = 1;
/*      */     do
/*      */     {
/*  106 */       int day = startCal.get(7);
/*  107 */       int startTimeInHr = startCal.get(11);
/*  108 */       int startTimeInMin = startCal.get(12);
/*  109 */       int currentTimeInMin = startTimeInHr * 60 + startTimeInMin;
/*  110 */       String period = prop.getProperty(String.valueOf(day));
/*  111 */       AMLog.debug("DAY : " + day + "\tHR :" + startTimeInHr + "\tMIN : " + startTimeInMin + "\tPERIOD :" + period);
/*  112 */       while (period == null)
/*      */       {
/*  114 */         if (firstDay)
/*      */         {
/*  116 */           startCal.add(10, 23 - startTimeInHr);
/*  117 */           startCal.add(12, 60 - startTimeInMin);
/*  118 */           firstDay = false;
/*      */         }
/*      */         else
/*      */         {
/*  122 */           startCal.add(10, 24);
/*  123 */           startCal.add(12, 0);
/*  124 */           temp = false;
/*      */         }
/*  126 */         int tempDay = startCal.get(7);
/*  127 */         int tempHr = startCal.get(11);
/*  128 */         day = tempDay;
/*  129 */         period = prop.getProperty(String.valueOf(tempDay));
/*  130 */         AMLog.debug("DAY :::::: " + tempDay + "\tHR ::::: " + tempHr + "\tPERIOD::::" + period);
/*  131 */         i++;
/*      */       }
/*  133 */       if (temp)
/*      */       {
/*  135 */         if (firstDay)
/*      */         {
/*  137 */           startCal.add(10, 23 - startTimeInHr);
/*  138 */           startCal.add(12, 60 - startTimeInMin);
/*  139 */           firstDay = false;
/*      */         }
/*      */         else
/*      */         {
/*  143 */           startCal.add(10, 24);
/*  144 */           startCal.add(12, 0);
/*      */         }
/*      */         
/*      */ 
/*  148 */         String[] timeArr = period.split("-");
/*  149 */         String[] fromArr = timeArr[0].split(":");
/*  150 */         String[] toArr = timeArr[1].split(":");
/*  151 */         long bhStartTimeInMin = Integer.parseInt(fromArr[0]) * 60 + Integer.parseInt(fromArr[1]);
/*  152 */         long bhEndTimeInMin = Integer.parseInt(toArr[0]) * 60 + Integer.parseInt(toArr[1]);
/*  153 */         AMLog.debug("currentTimeInMin=" + currentTimeInMin + "\tbhStartTimeInMin=" + bhStartTimeInMin + "\tbhEndTimeInMin=" + bhEndTimeInMin);
/*  154 */         if (currentTimeInMin > bhStartTimeInMin)
/*      */         {
/*  156 */           bhStartTimeInMin = currentTimeInMin;
/*      */         }
/*  158 */         if (currentTimeInMin > bhEndTimeInMin)
/*      */         {
/*  160 */           bhEndTimeInMin = currentTimeInMin;
/*      */         }
/*  162 */         if ((totalNoOfDays == i - 1) && (endTimeInMin > bhStartTimeInMin) && (endTimeInMin < bhEndTimeInMin))
/*      */         {
/*  164 */           bhEndTimeInMin = endTimeInMin;
/*      */         }
/*  166 */         AMLog.debug("(" + bhEndTimeInMin + "-" + bhStartTimeInMin + ")=" + (bhEndTimeInMin - bhStartTimeInMin) + "\ttotalDuration=" + totalDuration);
/*  167 */         totalDuration += bhEndTimeInMin - bhStartTimeInMin;
/*      */         
/*  169 */         i++;
/*      */       }
/*      */       else
/*      */       {
/*  173 */         temp = true;
/*      */       }
/*  175 */       startTime = startCal.getTimeInMillis();
/*      */     }
/*  177 */     while ((startTime <= endTime) && (i <= totalNoOfDays + 1L));
/*      */     
/*      */ 
/*  180 */     return totalDuration * 60L * 1000L;
/*      */   }
/*      */   
/*      */   public static Properties getHealthStatsForServiceMO(String resourceid, String minTime, String maxTime, String period, String duration)
/*      */   {
/*  185 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  186 */     Properties dataProps = new Properties();
/*  187 */     ResultSet set = null;
/*      */     try
/*      */     {
/*  190 */       long totalTime = 0L;
/*  191 */       long criticalTime = 0L;
/*  192 */       long clearTime = 0L;
/*  193 */       long unmanagedTime = 0L;
/*  194 */       long warningTime = 0L;
/*  195 */       ArrayList resources = new ArrayList();
/*  196 */       HashMap mintimeMap = new HashMap();
/*  197 */       String residquery = "select CHILDID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  198 */       AMLog.debug("REPORTS => HA-AVAILABILITY DATA QUERY getServiceAvailabilityStatsForMO parent => " + residquery);
/*  199 */       set = AMConnectionPool.executeQueryStmt(residquery);
/*      */       
/*  201 */       while (set.next())
/*      */       {
/*  203 */         String resid = String.valueOf(set.getInt("CHILDID"));
/*  204 */         String resType = set.getString("TYPE");
/*  205 */         resources.add(resid);
/*  206 */         long childmintimeindb = getMinTimeInDB(resid);
/*  207 */         mintimeMap.put(resid, childmintimeindb + "");
/*      */       }
/*  209 */       set.close();
/*  210 */       for (int i = 0; i < resources.size(); i++)
/*      */       {
/*  212 */         String resid = (String)resources.get(i);
/*  213 */         long mintimeindb = Long.parseLong((String)mintimeMap.get(resid));
/*  214 */         if (mintimeindb < Long.parseLong(minTime))
/*      */         {
/*  216 */           mintimeindb = Long.parseLong(minTime);
/*      */         }
/*  218 */         Properties healthProps = getHealthStatsForMO(resid, String.valueOf(mintimeindb), maxTime, period, duration);
/*  219 */         criticalTime += Long.parseLong(healthProps.getProperty("criticalTime"));
/*  220 */         warningTime += Long.parseLong(healthProps.getProperty("warningTime"));
/*  221 */         clearTime += Long.parseLong(healthProps.getProperty("clearTime"));
/*  222 */         unmanagedTime += Long.parseLong(healthProps.getProperty("unmanagedTime"));
/*  223 */         totalTime += Long.parseLong(healthProps.getProperty("totalTime"));
/*      */       }
/*      */       
/*  226 */       float criticalPercent = (float)criticalTime * 100.0F / (float)totalTime;
/*  227 */       float warningPercent = (float)warningTime * 100.0F / (float)totalTime;
/*  228 */       float clearPercent = (float)clearTime * 100.0F / (float)totalTime;
/*  229 */       float unamnagedPercent = (float)unmanagedTime * 100.0F / (float)totalTime;
/*  230 */       dataProps.setProperty("Name", "");
/*  231 */       dataProps.setProperty("Clear", String.valueOf(roundOff(clearPercent, 2)));
/*  232 */       dataProps.setProperty("Critical", String.valueOf(roundOff(criticalPercent, 2)));
/*  233 */       dataProps.setProperty("Warning", String.valueOf(roundOff(warningPercent, 2)));
/*  234 */       dataProps.setProperty("Unmanaged", String.valueOf(roundOff(unamnagedPercent, 2)));
/*  235 */       dataProps.setProperty("totalTime", String.valueOf(totalTime));
/*  236 */       dataProps.setProperty("criticalTime", String.valueOf(criticalTime));
/*  237 */       dataProps.setProperty("warningTime", String.valueOf(warningTime));
/*  238 */       dataProps.setProperty("clearTime", String.valueOf(clearTime));
/*  239 */       dataProps.setProperty("unmanagedTime", String.valueOf(unmanagedTime));
/*  240 */       dataProps.setProperty("Mintime", minTime);
/*  241 */       dataProps.setProperty("Maxtime", maxTime);
/*      */     }
/*      */     catch (Exception ex) {
/*  244 */       ex = 
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  250 */         ex;ex.printStackTrace();
/*      */     }
/*      */     finally {}
/*      */     
/*      */ 
/*  251 */     return dataProps;
/*      */   }
/*      */   
/*      */   public static Properties getHealthStatsForMO(String haid, String minTime, String maxTime, String period, String duration)
/*      */   {
/*  256 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  257 */     ResultSet rs = null;
/*  258 */     Properties dataProps = new Properties();
/*      */     try
/*      */     {
/*  261 */       DBUtil db = new DBUtil();
/*  262 */       String DBVal = db.getGlobalConfigValueForMGAvailability();
/*      */       
/*  264 */       if ("true".equalsIgnoreCase(DBVal))
/*      */       {
/*  266 */         String typeQuery = "select  TYPE from AM_ManagedObject where RESOURCEID=" + haid;
/*  267 */         rs = AMConnectionPool.executeQueryStmt(typeQuery);
/*  268 */         if (rs.next())
/*      */         {
/*  270 */           String moType = rs.getString("TYPE");
/*  271 */           if (moType.equals("HAI"))
/*      */           {
/*  273 */             return getHealthStatsForServiceMO(haid, minTime, maxTime, period, duration);
/*      */           }
/*      */         }
/*      */         
/*  277 */         rs.close();
/*      */       }
/*  279 */       String businessRule = "oni";
/*  280 */       Vector v2 = new Vector();
/*  281 */       v2.add(haid);
/*      */       
/*  283 */       Map MonitorGroupAvailabilityData = getAllDowntimeDetails(v2, Long.parseLong(minTime), Long.parseLong(maxTime), businessRule);
/*  284 */       Map MonitorGroupHealthData = getAllHealthDowntimeDetails(v2, Long.parseLong(minTime), Long.parseLong(maxTime), businessRule);
/*      */       
/*      */ 
/*  287 */       ArrayList healthDownTimes = (ArrayList)MonitorGroupHealthData.get(haid);
/*  288 */       long warningTime = 0L;
/*  289 */       long criticaltime = 0L;
/*  290 */       long unmanagedTime = 0L;
/*  291 */       ArrayList availDownTimes = (ArrayList)MonitorGroupAvailabilityData.get(haid);
/*      */       int j;
/*  293 */       for (int i = 0; (healthDownTimes != null) && (i < healthDownTimes.size()); i++)
/*      */       {
/*  295 */         Properties eachHealthDowntime = (Properties)healthDownTimes.get(i);
/*  296 */         long healthdowntime = Long.parseLong((String)eachHealthDowntime.get("downtime"));
/*  297 */         long healthuptime = Long.parseLong((String)eachHealthDowntime.get("uptime"));
/*  298 */         long totalhealthdowntime = Long.parseLong((String)eachHealthDowntime.get("TotalDownTime"));
/*  299 */         for (j = 0; (availDownTimes != null) && (j < availDownTimes.size()); j++)
/*      */         {
/*  301 */           Properties eachAvailDowntime = (Properties)availDownTimes.get(j);
/*  302 */           long availdowntime = Long.parseLong((String)eachAvailDowntime.get("downtime"));
/*  303 */           long availuptime = Long.parseLong((String)eachAvailDowntime.get("uptime"));
/*  304 */           long totalavaildowntime = Long.parseLong((String)eachAvailDowntime.get("TotalDownTime"));
/*  305 */           if ((availdowntime >= healthdowntime) && (availdowntime <= healthuptime))
/*      */           {
/*  307 */             if (availuptime <= healthuptime)
/*      */             {
/*  309 */               eachAvailDowntime.put("TotalDownTime", "0");
/*  310 */               eachAvailDowntime.put("downtime", "0");
/*  311 */               eachAvailDowntime.put("uptime", "0");
/*      */             }
/*  313 */             else if (availuptime > healthuptime)
/*      */             {
/*  315 */               eachAvailDowntime.put("TotalDownTime", String.valueOf(availuptime - healthuptime));
/*  316 */               eachAvailDowntime.put("downtime", healthuptime + "");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  321 */       for (int i = 0; (healthDownTimes != null) && (i < healthDownTimes.size()); i++)
/*      */       {
/*  323 */         Properties eachDowntime = (Properties)healthDownTimes.get(i);
/*  324 */         String severity = eachDowntime.getProperty("severity");
/*  325 */         String totalDownTime = eachDowntime.getProperty("TotalDownTime");
/*  326 */         if ("4".equals(severity))
/*      */         {
/*  328 */           warningTime += Long.parseLong(totalDownTime);
/*      */         }
/*  330 */         else if ("1".equals(severity))
/*      */         {
/*  332 */           criticaltime += Long.parseLong(totalDownTime);
/*      */         }
/*      */       }
/*  335 */       for (int i = 0; (availDownTimes != null) && (i < availDownTimes.size()); i++)
/*      */       {
/*  337 */         Properties eachDowntime = (Properties)availDownTimes.get(i);
/*  338 */         String downtimeType = eachDowntime.getProperty("downtimeType");
/*  339 */         String totalDownTime = eachDowntime.getProperty("TotalDownTime");
/*  340 */         if ((downtimeType != null) && ("2".equals(downtimeType)))
/*      */         {
/*  342 */           unmanagedTime += Long.parseLong(totalDownTime);
/*      */         }
/*      */         else
/*      */         {
/*  346 */           criticaltime += Long.parseLong(totalDownTime);
/*      */         }
/*      */       }
/*  349 */       long totalTime = Long.parseLong(maxTime) - Long.parseLong(minTime);
/*  350 */       long clearTime = totalTime - criticaltime - warningTime - unmanagedTime;
/*  351 */       float criticalPercent = (float)criticaltime * 100.0F / (float)totalTime;
/*  352 */       float warningPercent = (float)warningTime * 100.0F / (float)totalTime;
/*  353 */       float clearPercent = (float)clearTime * 100.0F / (float)totalTime;
/*  354 */       float unamnagedPercent = (float)unmanagedTime * 100.0F / (float)totalTime;
/*  355 */       dataProps.setProperty("Name", "");
/*  356 */       dataProps.setProperty("Clear", String.valueOf(roundOff(clearPercent, 2)));
/*  357 */       dataProps.setProperty("Critical", String.valueOf(roundOff(criticalPercent, 2)));
/*  358 */       dataProps.setProperty("Warning", String.valueOf(roundOff(warningPercent, 2)));
/*  359 */       dataProps.setProperty("Unmanaged", String.valueOf(roundOff(unamnagedPercent, 2)));
/*  360 */       dataProps.setProperty("totalTime", String.valueOf(totalTime));
/*  361 */       dataProps.setProperty("criticalTime", String.valueOf(criticaltime));
/*  362 */       dataProps.setProperty("warningTime", String.valueOf(warningTime));
/*  363 */       dataProps.setProperty("clearTime", String.valueOf(clearTime));
/*  364 */       dataProps.setProperty("unmanagedTime", String.valueOf(unmanagedTime));
/*  365 */       dataProps.setProperty("Mintime", minTime);
/*  366 */       dataProps.setProperty("Maxtime", maxTime);
/*      */       
/*  368 */       return dataProps;
/*      */     }
/*      */     catch (Exception exp) {
/*  371 */       exp.printStackTrace();
/*  372 */       AMLog.fatal("Reports : Exception in getHealthStatsForMO ", exp);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  377 */         if (rs != null)
/*      */         {
/*  379 */           rs.close();
/*      */         }
/*      */       } catch (Exception ex) {}
/*      */     }
/*  383 */     return dataProps;
/*      */   }
/*      */   
/*      */   public static ArrayList getValueForHASnapshotReport(ArrayList ALLDATA, Hashtable appkeys, Hashtable healthkeys) {
/*  387 */     ArrayList allvalues = new ArrayList();
/*      */     try {
/*  389 */       String resGroup = Constants.resourceGroups;
/*  390 */       resGroup = resGroup.substring(0, resGroup.length() - 1) + ",'HAI')";
/*      */       
/*  392 */       Vector resrcIds = new Vector();
/*  393 */       for (int k = 0; k < ALLDATA.size(); k++)
/*      */       {
/*  395 */         ArrayList resrcList = (ArrayList)ALLDATA.get(k);
/*      */         
/*  397 */         if (resrcList.size() > 0) {
/*  398 */           for (int s = 0; s < resrcList.size(); s++) {
/*  399 */             ArrayList resrcDetail = (ArrayList)resrcList.get(s);
/*      */             
/*  401 */             String RID = resrcDetail.get(0).toString();
/*  402 */             resrcIds.add(RID);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  407 */       String query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP in " + resGroup + " and (AM_ManagedObject.TYPE in " + Constants.resourceTypes + " or AM_ManagedObject.TYPE='HAI') group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,AM_ManagedResourceType.SHORTNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH";
/*      */       
/*      */ 
/*      */ 
/*  411 */       if (resrcIds.size() > 0)
/*      */       {
/*  413 */         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resrcIds) + " group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,AM_ManagedResourceType.SHORTNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH";
/*      */       }
/*      */       
/*      */ 
/*  417 */       AMLog.debug("ReportUtilities getValueForHASnapshotReport QUERY " + query);
/*      */       
/*  419 */       ArrayList alertarray = DBUtil.getRows(query);
/*  420 */       Properties alertproperties = getAlerts(alertarray, appkeys, healthkeys);
/*      */       
/*  422 */       for (int i = 0; i < ALLDATA.size(); i++) {
/*  423 */         ArrayList M1 = (ArrayList)ALLDATA.get(i);
/*  424 */         if (M1.size() > 0) {
/*  425 */           for (int s = 0; s < M1.size(); s++) {
/*  426 */             ArrayList insertAllDetails = new ArrayList();
/*  427 */             ArrayList insideM1 = (ArrayList)M1.get(s);
/*      */             
/*  429 */             String RID = insideM1.get(0).toString();
/*  430 */             String DISNAME = insideM1.get(2).toString();
/*  431 */             String TYPE = insideM1.get(3).toString();
/*      */             
/*  433 */             if ("SUBGROUP".equals(TYPE)) {
/*  434 */               TYPE = "HAI";
/*      */             }
/*      */             
/*  437 */             insertAllDetails.add(RID);
/*  438 */             insertAllDetails.add(DISNAME);
/*  439 */             insertAllDetails.add(TYPE);
/*      */             
/*      */ 
/*  442 */             String severity = alertproperties.getProperty(RID + "#" + appkeys.get(TYPE));
/*      */             
/*  444 */             insertAllDetails.add(getSeverityImageForAvailability(severity));
/*  445 */             String healthseverity = alertproperties.getProperty(RID + "#" + healthkeys.get(TYPE));
/*  446 */             insertAllDetails.add(getSeverityImageForHealth(healthseverity));
/*  447 */             String text = alertproperties.getProperty(RID + "#" + healthkeys.get(TYPE) + "#MESSAGE");
/*  448 */             if ((text == null) || ("5".equals(healthseverity))) {
/*  449 */               text = "-";
/*      */             }
/*  451 */             insertAllDetails.add(text);
/*  452 */             allvalues.add(insertAllDetails);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  458 */       ex.printStackTrace();
/*      */     }
/*  460 */     return allvalues;
/*      */   }
/*      */   
/*      */   public static ArrayList getHostnameForHASnapshotReport(ArrayList ALLDATA, Hashtable appkeys, Hashtable healthkeys) {
/*  464 */     ArrayList toreturn = new ArrayList();
/*      */     try
/*      */     {
/*  467 */       Vector resrcIds = new Vector();
/*  468 */       for (int k = 0; k < ALLDATA.size(); k++)
/*      */       {
/*  470 */         ArrayList resrcList = (ArrayList)ALLDATA.get(k);
/*      */         
/*  472 */         if (resrcList.size() > 0) {
/*  473 */           for (int s = 0; s < resrcList.size(); s++) {
/*  474 */             ArrayList resrcDetail = (ArrayList)resrcList.get(s);
/*      */             
/*  476 */             String RID = resrcDetail.get(0).toString();
/*  477 */             resrcIds.add(RID);
/*      */           }
/*      */         }
/*      */       }
/*  481 */       String query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP in ('APP','CAM','DBS','SER','SYS','URL','MS','TM','ERP','HAI','NWD','SAN','MOM','VIR','CLD','EMO') and (AM_ManagedObject.TYPE in " + Constants.resourceTypes + " or AM_ManagedObject.TYPE='HAI') group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,AM_ManagedResourceType.SHORTNAME,AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH";
/*      */       
/*      */ 
/*      */ 
/*  485 */       if (resrcIds.size() > 0)
/*      */       {
/*  487 */         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resrcIds) + " group by resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,AM_ManagedResourceType.SHORTNAME,AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH";
/*      */       }
/*  489 */       AMLog.debug("ReportUtilities getHostnameForHASnapshotReport Query " + query);
/*      */       
/*  491 */       ArrayList alertarray = DBUtil.getRows(query);
/*  492 */       Properties alertproperties = getAlerts(alertarray, appkeys, healthkeys);
/*  493 */       LinkedHashMap mapids = getMonitorsInMonitorGroup(ALLDATA);
/*  494 */       Collection c = mapids.keySet();
/*  495 */       Iterator itr = c.iterator();
/*  496 */       while (itr.hasNext()) {
/*  497 */         ArrayList insertAllDetails = new ArrayList();
/*  498 */         String key = itr.next().toString();
/*  499 */         String name = getLabelName(key);
/*  500 */         String type = "HAI";
/*  501 */         insertAllDetails.add(key);
/*  502 */         insertAllDetails.add(name);
/*  503 */         insertAllDetails.add(type);
/*      */         
/*      */ 
/*  506 */         String severity = alertproperties.getProperty(key + "#" + appkeys.get(type));
/*      */         
/*  508 */         insertAllDetails.add(getSeverityImageForAvailability(severity));
/*  509 */         String healthseverity = alertproperties.getProperty(key + "#" + healthkeys.get(type));
/*      */         
/*  511 */         insertAllDetails.add(getSeverityImageForHealth(healthseverity));
/*  512 */         String text = alertproperties.getProperty(key + "#" + healthkeys.get(type) + "#MESSAGE");
/*  513 */         if ((text == null) || ("5".equals(healthseverity))) {
/*  514 */           text = "-";
/*      */         }
/*  516 */         insertAllDetails.add(text);
/*  517 */         if ((severity != null) && ((!"5".equals(severity)) || (!"5".equals(healthseverity))))
/*      */         {
/*  519 */           if ((mapids.size() > 0) && (mapids.containsKey(key))) {
/*  520 */             Vector v = (Vector)mapids.get(key);
/*  521 */             if ((v != null) && (v.size() > 0)) {
/*  522 */               ArrayList a1 = new ArrayList();
/*  523 */               for (int i = 0; i < v.size(); i++) {
/*  524 */                 String id = v.get(i).toString();
/*  525 */                 String[] monTypenName = getResourceTypeAndLabelName(id);
/*  526 */                 String montype = monTypenName[0];
/*  527 */                 String moname = monTypenName[1];
/*  528 */                 String monitorseverity = alertproperties.getProperty(id + "#" + appkeys.get(montype));
/*  529 */                 String healthmonitorseverity = alertproperties.getProperty(id + "#" + healthkeys.get(montype));
/*      */                 
/*      */ 
/*  532 */                 if (("1".equals(monitorseverity)) || ("4".equals(monitorseverity)))
/*      */                 {
/*  534 */                   String montext = alertproperties.getProperty(id + "#" + healthkeys.get(montype) + "#MESSAGE");
/*  535 */                   if (montext == null) {
/*  536 */                     StartUtil.printStr("ReportUtilities.getHostnameForHASnapshotReport()1:No alert in database for this entity ==> " + id + "#" + healthkeys.get(montype) + "#MESSAGE");
/*  537 */                     montext = "";
/*      */                   }
/*  539 */                   Properties pr = new Properties();
/*  540 */                   pr.setProperty("moname", moname);
/*  541 */                   pr.setProperty("momessage", montext);
/*  542 */                   a1.add(pr);
/*      */                 }
/*  544 */                 else if (("1".equals(healthmonitorseverity)) || ("4".equals(healthmonitorseverity)))
/*      */                 {
/*  546 */                   String monhealthtext = alertproperties.getProperty(id + "#" + healthkeys.get(montype) + "#MESSAGE");
/*  547 */                   if (monhealthtext == null) {
/*  548 */                     StartUtil.printStr("ReportUtilities.getHostnameForHASnapshotReport()2:No alert in database for this entity ==> " + id + "#" + healthkeys.get(montype) + "#MESSAGE");
/*  549 */                     monhealthtext = "";
/*      */                   }
/*  551 */                   Properties pr = new Properties();
/*  552 */                   pr.setProperty("moname", moname);
/*  553 */                   pr.setProperty("momessage", monhealthtext);
/*  554 */                   a1.add(pr);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  560 */               insertAllDetails.add(a1);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  567 */           ArrayList a2 = new ArrayList();
/*  568 */           Properties ps = new Properties();
/*  569 */           ps.setProperty("moname", "-");
/*  570 */           ps.setProperty("momessage", "-");
/*  571 */           a2.add(ps);
/*  572 */           insertAllDetails.add(a2);
/*      */         }
/*      */         
/*  575 */         toreturn.add(insertAllDetails);
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  584 */       ex.printStackTrace();
/*      */     }
/*  586 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Properties getAlerts(ArrayList monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  592 */     Properties alert = null;
/*      */     try
/*      */     {
/*  595 */       ArrayList resIDs = new ArrayList();
/*      */       
/*  597 */       for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++) {
/*  598 */         ArrayList row = (ArrayList)monitorList.get(j);
/*  599 */         String resourceid = (String)row.get(6);
/*      */         
/*  601 */         String resourceType = (String)row.get(7);
/*  602 */         Object healthkey = healthkeys.get(resourceType);
/*  603 */         resIDs.add(resourceid + "_" + healthkey);
/*      */         
/*      */ 
/*      */ 
/*  607 */         Object availabilitykey = availabilitykeys.get(resourceType);
/*  608 */         resIDs.add(resourceid + "_" + availabilitykey);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  614 */       alert = FaultUtil.getStatus(resIDs, false);
/*      */     }
/*      */     catch (Exception ex) {
/*  617 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  620 */     return alert;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Map getAllDowntimeDetails(Vector resids, long startTime, long endTime, String Bid)
/*      */   {
/*  808 */     ResultSet set = null;
/*  809 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  810 */     ArrayList toreturn = new ArrayList();
/*  811 */     Map residsProperties = new HashMap();
/*      */     try
/*      */     {
/*  814 */       String query = "select resid,displayname,AM_ManagedObject.type,case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.TYPE as downtimetype from AM_MO_DowntimeData,AM_ManagedObject where resid=resourceid and " + getCondition("RESID", resids) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*  815 */       if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/*  816 */         query = "select resid,displayname,AM_ManagedObject.type,case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.TYPE as downtimetype from AM_MO_DowntimeData,AM_ManagedObject where resid=resourceid and AM_MO_DowntimeData.TYPE=1 and " + getCondition("RESID", resids) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       }
/*      */       
/*      */ 
/*  820 */       if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/*  821 */         query = "select resid,displayname,AM_ManagedObject.type,case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime,AM_MO_DowntimeData.TYPE as downtimetype from AM_MO_DowntimeData,AM_ManagedObject where resid=resourceid and AM_MO_DowntimeData.TYPE=1 and " + getCondition("RESID", resids) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       }
/*      */       
/*      */ 
/*  825 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  827 */       ArrayList summary = null;
/*  828 */       while (set.next())
/*      */       {
/*  830 */         String resid = set.getString("resid");
/*  831 */         Properties rows = new Properties();
/*  832 */         rows.put("moname", EnterpriseUtil.decodeString(set.getString("displayname")));
/*  833 */         rows.put("motype", set.getString("type"));
/*  834 */         rows.put("downtimeType", set.getString("downtimetype"));
/*  835 */         if ((!"oni".equals(Bid)) && (Bid != null))
/*      */         {
/*      */ 
/*  838 */           Properties p = getWorkingTotalHours(set.getLong("DownTime"), set.getLong("UpTime"), Bid);
/*      */           
/*  840 */           String toRemove = p.get("toremove").toString();
/*  841 */           if ("true".equals(toRemove)) {
/*      */             continue;
/*      */           }
/*  844 */           rows.put("downtime", p.get("downtime").toString());
/*  845 */           rows.put("uptime", p.get("uptime").toString());
/*  846 */           rows.put("downtimeinmillis", p.get("downtime").toString());
/*  847 */           rows.put("TotalDownTime", p.get("totaldowntime").toString());
/*      */         }
/*      */         else {
/*  850 */           rows.put("downtime", Long.toString(set.getLong("DownTime")));
/*  851 */           if (set.getLong("UpTime") == endTime)
/*      */           {
/*  853 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/*  854 */             rows.put("dontdelete", "true");
/*      */           }
/*      */           else
/*      */           {
/*  858 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/*      */           }
/*  860 */           rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/*  861 */           rows.put("TotalDownTime", Long.toString(set.getLong("TotalDownTime")));
/*      */         }
/*      */         
/*  864 */         if (!residsProperties.containsKey(resid))
/*      */         {
/*  866 */           summary = new ArrayList();
/*  867 */           summary.add(rows);
/*  868 */           residsProperties.put(resid, summary);
/*      */         }
/*      */         else {
/*  871 */           ArrayList a1 = (ArrayList)residsProperties.get(resid);
/*  872 */           a1.add(rows);
/*  873 */           residsProperties.put(resid, a1);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  879 */       ex.printStackTrace();
/*      */     }
/*  881 */     return residsProperties;
/*      */   }
/*      */   
/*      */   public static HashMap getBusinessRule(String id) {
/*  885 */     return SegmentReportUtil.getBusinessRuleMap(id);
/*      */   }
/*      */   
/*      */   public static boolean isWorkingHours() {
/*  889 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  890 */     ResultSet set = null;
/*  891 */     boolean isB = false;
/*      */     try {
/*  893 */       String query = "select * from AM_BUSINESSHOURSDETAILS";
/*  894 */       set = AMConnectionPool.executeQueryStmt(query);
/*  895 */       if (set.next()) {
/*  896 */         isB = true;
/*      */       }
/*      */     } catch (Exception ex) {
/*  899 */       ex.printStackTrace();
/*      */     }
/*  901 */     return isB;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Properties getWorkingTotalHours(long startMonDownTime, long endMonDownTime, String id)
/*      */   {
/*  907 */     Calendar cal = new GregorianCalendar();
/*  908 */     Calendar cal1 = new GregorianCalendar();
/*  909 */     java.util.Date stTime = new java.util.Date(startMonDownTime);
/*  910 */     cal.setTime(stTime);
/*  911 */     java.util.Date endTime = new java.util.Date(endMonDownTime);
/*  912 */     cal1.setTime(endTime);
/*  913 */     Properties toreturn = new Properties();
/*  914 */     String toRemove = "true";
/*  915 */     long dispStart = 0L;
/*  916 */     long dispEnd = 0L;
/*  917 */     long totalTime = 0L;
/*      */     try {
/*  919 */       int startYear = cal.get(1);
/*  920 */       int startMonth = cal.get(2);
/*  921 */       int startDay = cal.get(5);
/*  922 */       int startDayOfWeek = cal.get(7);
/*  923 */       int startHour = cal.get(11);
/*  924 */       int startMinutes = cal.get(12);
/*      */       
/*  926 */       int endYear = cal1.get(1);
/*  927 */       int endMonth = cal1.get(2);
/*  928 */       int endDay = cal1.get(5);
/*  929 */       int endHour = cal1.get(11);
/*  930 */       int endMinutes = cal1.get(12);
/*      */       
/*  932 */       long from = new GregorianCalendar(startYear, startMonth, startDay).getTime().getTime();
/*  933 */       long to = new GregorianCalendar(endYear, endMonth, endDay).getTime().getTime();
/*  934 */       double difference = to - from;
/*  935 */       long daydiff = Math.round(difference / 8.64E7D);
/*      */       
/*  937 */       HashMap hourvalues = getBusinessRule(id);
/*  938 */       int DBStartHour = 0;
/*  939 */       int DBEndHour = 0;
/*  940 */       int DBStartMinute = 0;
/*  941 */       int DBEndMinute = 0;
/*  942 */       long startMonitorTimeinMS = getSetTimeInMillis(startYear, startMonth, startDay, startHour, startMinutes, 0, 0);
/*  943 */       long endMonitorTImeinMS = getSetTimeInMillis(endYear, endMonth, endDay, endHour, endMinutes, 0, 0);
/*      */       
/*  945 */       Calendar tmpEndDt = new GregorianCalendar();
/*  946 */       java.util.Date tmpenddate = new java.util.Date();
/*  947 */       tmpEndDt.setTime(tmpenddate);
/*  948 */       int tempDay = startDayOfWeek;
/*  949 */       for (int i = 0; i <= daydiff; i++) {
/*  950 */         tempDay = startDayOfWeek + i;
/*  951 */         if (tempDay > 7) {
/*  952 */           tempDay %= 7;
/*  953 */           if (tempDay == 0)
/*  954 */             tempDay = 7;
/*      */         }
/*  956 */         String tempStartHour = hourvalues.get(String.valueOf(tempDay)).toString();
/*  957 */         String[] temphour = tempStartHour.split("-");
/*  958 */         String[] t1 = temphour[0].split(":");
/*  959 */         String[] t2 = temphour[1].split(":");
/*  960 */         String sthr = t1[0];
/*  961 */         String stmn = t1[1];
/*  962 */         String edhr = t2[0];
/*  963 */         String edmn = t2[1];
/*  964 */         DBStartHour = Integer.parseInt(sthr);
/*  965 */         DBStartMinute = Integer.parseInt(stmn);
/*  966 */         DBEndHour = Integer.parseInt(edhr);
/*  967 */         DBEndMinute = Integer.parseInt(edmn);
/*      */         
/*  969 */         int tempDayCalc = startDay + i;
/*  970 */         long startBusinessfrom = new GregorianCalendar(startYear, startMonth, tempDayCalc).getTime().getTime();
/*  971 */         java.util.Date stBusinessTime = new java.util.Date(startBusinessfrom);
/*  972 */         cal.setTime(stBusinessTime);
/*  973 */         int startBuYear = cal.get(1);
/*  974 */         int startBuMonth = cal.get(2);
/*  975 */         int startBuDay = cal.get(5);
/*  976 */         if (DBStartHour != 25) {
/*  977 */           long startBusinessTimeinMS = getSetTimeInMillis(startBuYear, startBuMonth, startBuDay, DBStartHour, DBStartMinute, 0, 0);
/*  978 */           long endBusinessTimeinMS = getSetTimeInMillis(startBuYear, startBuMonth, startBuDay, DBEndHour, DBEndMinute, 0, 0);
/*      */           
/*      */ 
/*  981 */           if ((endBusinessTimeinMS >= startMonitorTimeinMS) && (endMonitorTImeinMS >= startBusinessTimeinMS))
/*      */           {
/*      */ 
/*      */ 
/*  985 */             if (startBusinessTimeinMS >= startMonitorTimeinMS) {
/*  986 */               startMonitorTimeinMS = startBusinessTimeinMS;
/*      */             }
/*  988 */             if (dispStart == 0L) {
/*  989 */               dispStart = startMonitorTimeinMS;
/*      */             }
/*      */             
/*  992 */             if (endBusinessTimeinMS <= endMonitorTImeinMS) {
/*  993 */               dispEnd = endBusinessTimeinMS;
/*      */             } else {
/*  995 */               dispEnd = endMonitorTImeinMS;
/*      */             }
/*  997 */             totalTime += dispEnd - startMonitorTimeinMS;
/*  998 */             toRemove = "false";
/*      */           }
/*      */         } }
/* 1001 */       toreturn.put("downtime", Long.toString(dispStart));
/* 1002 */       toreturn.put("uptime", Long.toString(dispEnd));
/* 1003 */       toreturn.put("totaldowntime", Long.toString(totalTime));
/* 1004 */       toreturn.put("toremove", toRemove);
/*      */     } catch (Exception ex) {
/* 1006 */       ex.printStackTrace();
/*      */     }
/* 1008 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Properties getPerDayDowntimeUsingBusinessHour(long starttime, long endtime, int BstartHour, int BendHour, int BStartMinute, int BEndMinute)
/*      */   {
/* 1016 */     Calendar cal = new GregorianCalendar();
/* 1017 */     Calendar cal1 = new GregorianCalendar();
/* 1018 */     java.util.Date stTime = new java.util.Date(starttime);
/* 1019 */     cal.setTime(stTime);
/* 1020 */     java.util.Date endTime = new java.util.Date(endtime);
/* 1021 */     cal1.setTime(endTime);
/* 1022 */     Properties toreturn = new Properties();
/* 1023 */     long totalTime = 0L;
/*      */     try {
/* 1025 */       int startYear = cal.get(1);
/* 1026 */       int startMonth = cal.get(2);
/* 1027 */       int startDay = cal.get(5);
/* 1028 */       int startDayOfWeek = cal.get(7);
/*      */       
/* 1030 */       int startHour = cal.get(11);
/* 1031 */       int startMinutes = cal.get(12);
/* 1032 */       int startSecond = cal.get(13);
/* 1033 */       int startmillis = cal.get(14);
/*      */       
/* 1035 */       int endYear = cal1.get(1);
/* 1036 */       int endMonth = cal1.get(2);
/* 1037 */       int endDay = cal1.get(5);
/* 1038 */       int endDayOfWeek = cal1.get(7);
/* 1039 */       int morningOrNite = cal1.get(9);
/*      */       
/* 1041 */       int endHour = cal1.get(11);
/* 1042 */       int endMinutes = cal1.get(12);
/* 1043 */       int endSecond = cal1.get(13);
/* 1044 */       int endmillis = cal1.get(14);
/*      */       
/* 1046 */       long from = new GregorianCalendar(startYear, startMonth, startDay).getTime().getTime();
/* 1047 */       long to = new GregorianCalendar(endYear, endMonth, endDay).getTime().getTime();
/* 1048 */       double difference = to - from;
/* 1049 */       long daydiff = Math.round(difference / 8.64E7D);
/* 1050 */       int DBStartHour = 0;
/* 1051 */       int DBStartHourEnds = 0;
/* 1052 */       int DBEndHourStarts = 0;
/* 1053 */       int DBEndHour = 0;
/*      */       
/* 1055 */       int DBStartMinute = 0;
/* 1056 */       int DBStartMinuteEnds = 0;
/* 1057 */       int DBEndMinuteStarts = 0;
/* 1058 */       int DBEndMinute = 0;
/*      */       
/* 1060 */       long totalHrs = 0L;
/* 1061 */       long tempHrs = 0L;
/* 1062 */       long newStartTime = 0L;
/* 1063 */       long newEndTime = 0L;
/* 1064 */       long temptime = 0L;
/* 1065 */       long tempstarttime = 0L;
/* 1066 */       long tempendtime = 0L;
/* 1067 */       DBStartHour = BstartHour;
/* 1068 */       DBStartHourEnds = BendHour;
/* 1069 */       DBEndHourStarts = BstartHour;
/* 1070 */       DBEndHour = BendHour;
/* 1071 */       DBStartMinute = BStartMinute;
/* 1072 */       DBStartMinuteEnds = BEndMinute;
/* 1073 */       DBEndMinuteStarts = BStartMinute;
/* 1074 */       DBEndMinute = BEndMinute;
/*      */       
/*      */ 
/*      */ 
/* 1078 */       String toRemove = "false";
/*      */       
/* 1080 */       if (((endHour == 0) && (morningOrNite != 0)) || (daydiff == 1L))
/*      */       {
/*      */ 
/* 1083 */         endYear = startYear;
/* 1084 */         endMonth = startMonth;
/* 1085 */         endDay = startDay;
/* 1086 */         endDayOfWeek = startDayOfWeek;
/* 1087 */         endHour = 23;
/* 1088 */         endMinutes = 59;
/* 1089 */         endSecond = 59;
/* 1090 */         endmillis = endmillis;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1101 */       if (((startHour < DBStartHour) && (endHour < DBStartHour)) || ((endHour > DBEndHour) && (startHour > DBEndHour)) || ((startHour >= DBEndHour) && (startMinutes > 0)))
/*      */       {
/*      */ 
/* 1104 */         toRemove = "true";
/*      */       }
/*      */       
/* 1107 */       if (((startHour == DBStartHour) && (startMinutes < DBStartMinute)) || ((endHour == DBEndHour) && (endMinutes > DBEndMinute))) {
/* 1108 */         toRemove = "true";
/*      */       }
/* 1110 */       if ((startHour < DBStartHour) && (endHour >= DBStartHour)) {
/* 1111 */         startHour = DBStartHour;
/* 1112 */         startMinutes = DBStartMinute;
/*      */       }
/* 1114 */       if ((endHour >= DBEndHour) && (DBEndHour != 0))
/*      */       {
/* 1116 */         endHour = DBEndHour;
/* 1117 */         endMinutes = DBEndMinute;
/*      */       }
/*      */       
/* 1120 */       newStartTime = getSetTimeInMillis(startYear, startMonth, startDay, startHour, startMinutes, startSecond, startmillis);
/* 1121 */       newEndTime = getSetTimeInMillis(endYear, endMonth, endDay, endHour, endMinutes, endSecond, endmillis);
/* 1122 */       tempstarttime = newStartTime;
/* 1123 */       tempendtime = newEndTime;
/* 1124 */       totalTime = newEndTime - newStartTime;
/*      */       
/* 1126 */       toreturn.put("downtime", Long.valueOf(tempstarttime));
/* 1127 */       toreturn.put("uptime", Long.valueOf(tempendtime));
/* 1128 */       toreturn.put("totaldowntime", Long.valueOf(totalTime));
/* 1129 */       toreturn.put("toremove", toRemove);
/*      */     } catch (Exception ex) {
/* 1131 */       ex.printStackTrace();
/*      */     }
/* 1133 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static long getTotalTimeForGivenHours(long starttime, long endtime, int DBStartHour, int DBStartHourEnds, int DBEndHourStarts, int DBEndHour, int DBStartMinute, int DBStartMinuteEnds, int DBEndMinuteStarts, int DBEndMinute)
/*      */   {
/* 1138 */     long totalTime = 0L;
/*      */     try {
/* 1140 */       long e1 = 0L;
/* 1141 */       long e2 = 0L;
/*      */       
/* 1143 */       Properties t1 = getPerDayDowntimeUsingBusinessHour(starttime, endtime, DBStartHour, DBStartHourEnds, DBStartMinute, DBStartMinuteEnds);
/*      */       
/* 1145 */       Properties t2 = getPerDayDowntimeUsingBusinessHour(starttime, endtime, DBEndHourStarts, DBEndHour, DBEndMinuteStarts, DBEndMinute);
/*      */       
/*      */ 
/* 1148 */       String isRemove = (String)t1.get("toremove");
/* 1149 */       if (!"true".equals(isRemove))
/* 1150 */         e1 = ((Long)t1.get("totaldowntime")).longValue();
/* 1151 */       String isRemove1 = (String)t2.get("toremove");
/* 1152 */       if (!"true".equals(isRemove1)) {
/* 1153 */         e2 = ((Long)t2.get("totaldowntime")).longValue();
/*      */       }
/* 1155 */       totalTime = e1 + e2;
/*      */     }
/*      */     catch (Exception ex) {
/* 1158 */       ex.printStackTrace();
/*      */     }
/* 1160 */     return totalTime;
/*      */   }
/*      */   
/*      */   public static Properties getBusinessHoursIfStartHourIsGreater(long starttime, long endtime, String BstartHour, String BendHour, HashMap hourvalues) {
/* 1164 */     Calendar cal = new GregorianCalendar();
/* 1165 */     Calendar cal1 = new GregorianCalendar();
/* 1166 */     java.util.Date stTime = new java.util.Date(starttime);
/* 1167 */     cal.setTime(stTime);
/* 1168 */     java.util.Date endTime = new java.util.Date(endtime);
/* 1169 */     cal1.setTime(endTime);
/* 1170 */     Properties toreturn = new Properties();
/* 1171 */     long totalTime = 0L;
/*      */     try {
/* 1173 */       int startYear = cal.get(1);
/* 1174 */       int startMonth = cal.get(2);
/* 1175 */       int startDay = cal.get(5);
/* 1176 */       int startDayOfWeek = cal.get(7);
/*      */       
/* 1178 */       int startHour = cal.get(11);
/* 1179 */       int startMinutes = cal.get(12);
/* 1180 */       int startSecond = cal.get(13);
/* 1181 */       int startmillis = cal.get(14);
/*      */       
/* 1183 */       int endYear = cal1.get(1);
/* 1184 */       int endMonth = cal1.get(2);
/* 1185 */       int endDay = cal1.get(5);
/* 1186 */       int endDayOfWeek = cal1.get(7);
/* 1187 */       int morningOrNite = cal1.get(9);
/* 1188 */       int endHour = cal1.get(11);
/* 1189 */       int endMinutes = cal1.get(12);
/* 1190 */       int endSecond = cal1.get(13);
/* 1191 */       int endmillis = cal1.get(14);
/*      */       
/* 1193 */       long from = new GregorianCalendar(startYear, startMonth, startDay).getTime().getTime();
/* 1194 */       long to = new GregorianCalendar(endYear, endMonth, endDay).getTime().getTime();
/* 1195 */       double difference = to - from;
/* 1196 */       long daydiff = Math.round(difference / 8.64E7D);
/* 1197 */       int DBStartHour = 0;
/* 1198 */       int DBStartHourEnds = 0;
/* 1199 */       int DBEndHourStarts = 0;
/* 1200 */       int DBEndHour = 0;
/* 1201 */       int DBStartMinute = 0;
/* 1202 */       int DBStartMinuteEnds = 0;
/* 1203 */       int DBEndMinuteStarts = 0;
/* 1204 */       int DBEndMinute = 0;
/* 1205 */       String toRemove = "false";
/* 1206 */       int tempBstartHour = Integer.parseInt(BstartHour);
/* 1207 */       int tempBendHour = Integer.parseInt(BendHour);
/* 1208 */       if (tempBstartHour < tempBendHour) {
/* 1209 */         if (daydiff == 0L)
/*      */         {
/* 1211 */           String tempStartHour = hourvalues.get(String.valueOf(startDayOfWeek)).toString();
/* 1212 */           String[] temphour = tempStartHour.split("-");
/* 1213 */           String[] t1 = temphour[0].split(":");
/* 1214 */           String[] t2 = temphour[1].split(":");
/* 1215 */           DBStartHour = Integer.parseInt(t1[0]);
/* 1216 */           DBEndHour = Integer.parseInt(t2[0]);
/* 1217 */           DBStartMinute = Integer.parseInt(t1[1]);
/* 1218 */           DBEndMinute = Integer.parseInt(t2[1]);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1223 */           String tempStartHour = hourvalues.get(String.valueOf(startDayOfWeek)).toString();
/* 1224 */           String[] temphour = tempStartHour.split("-");
/* 1225 */           String[] t1 = temphour[0].split(":");
/* 1226 */           String[] t2 = temphour[1].split(":");
/* 1227 */           String sthr = t1[0];
/* 1228 */           String stmn = t1[1];
/* 1229 */           String edhr = t2[0];
/* 1230 */           String edmn = t2[1];
/* 1231 */           DBStartHour = Integer.parseInt(sthr);
/* 1232 */           DBStartHourEnds = Integer.parseInt(edhr);
/* 1233 */           DBStartMinute = Integer.parseInt(stmn);
/* 1234 */           DBStartMinuteEnds = Integer.parseInt(edmn);
/*      */           
/* 1236 */           String tempStartHour1 = hourvalues.get(String.valueOf(endDayOfWeek)).toString();
/* 1237 */           String[] temphour1 = tempStartHour.split("-");
/* 1238 */           String[] t3 = temphour1[0].split(":");
/* 1239 */           String[] t4 = temphour1[1].split(":");
/* 1240 */           String sthr1 = t3[0];
/* 1241 */           String stmn1 = t3[1];
/* 1242 */           String edhr1 = t4[0];
/* 1243 */           String edmn1 = t4[1];
/* 1244 */           DBEndHourStarts = Integer.parseInt(sthr1);
/* 1245 */           DBEndHour = Integer.parseInt(edhr1);
/* 1246 */           DBEndMinuteStarts = Integer.parseInt(stmn1);
/* 1247 */           DBEndMinute = Integer.parseInt(edmn1);
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 1253 */         DBStartHour = 0;
/* 1254 */         DBStartHourEnds = tempBendHour;
/* 1255 */         DBEndHourStarts = tempBstartHour;
/* 1256 */         DBEndHour = 0;
/*      */       }
/* 1258 */       if ((daydiff == 1L) && 
/* 1259 */         (endHour == 0) && (morningOrNite != 0)) {
/* 1260 */         daydiff = 0L;
/*      */         
/* 1262 */         endYear = startYear;
/* 1263 */         endMonth = startMonth;
/* 1264 */         endDay = startDay;
/* 1265 */         endDayOfWeek = startDayOfWeek;
/* 1266 */         endHour = 23;
/* 1267 */         endMinutes = 59;
/* 1268 */         endSecond = 59;
/* 1269 */         endmillis = endmillis;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1282 */       if (daydiff == 0L) {
/* 1283 */         if (tempBstartHour < tempBendHour) {
/* 1284 */           long e1 = 0L;
/* 1285 */           long e2 = 0L;
/*      */           
/* 1287 */           Properties t1 = getPerDayDowntimeUsingBusinessHour(starttime, endtime, DBStartHour, DBEndHour, DBStartMinute, DBEndMinute);
/*      */           
/* 1289 */           String isRemove = (String)t1.get("toremove");
/* 1290 */           if (!"true".equals(isRemove)) {
/* 1291 */             totalTime = ((Long)t1.get("totaldowntime")).longValue();
/*      */           }
/*      */         }
/*      */         else {
/* 1295 */           totalTime = getTotalTimeForGivenHours(starttime, endtime, DBStartHour, DBStartHourEnds, DBEndHourStarts, DBEndHour, DBStartMinute, DBStartMinuteEnds, DBEndMinuteStarts, DBEndMinute);
/*      */         }
/* 1297 */       } else if (daydiff == 1L)
/*      */       {
/* 1299 */         long firstdayStarttime = starttime;
/* 1300 */         long firstdayEndtime = getSetTimeInMillis(startYear, startMonth, startDay + 1, 0, 0, 0, 0);
/* 1301 */         long endDayStartTime = getSetTimeInMillis(endYear, endMonth, endDay, 0, 0, 0, 0);
/* 1302 */         long endDayEndTime = endtime;
/* 1303 */         if (tempBstartHour < tempBendHour) {
/* 1304 */           long e1 = 0L;
/* 1305 */           long e2 = 0L;
/*      */           
/* 1307 */           Properties t1 = getPerDayDowntimeUsingBusinessHour(firstdayStarttime, firstdayEndtime, DBStartHour, DBEndHour, DBStartMinute, DBEndMinute);
/*      */           
/* 1309 */           String isRemove = (String)t1.get("toremove");
/* 1310 */           if (!"true".equals(isRemove)) {
/* 1311 */             e1 = ((Long)t1.get("totaldowntime")).longValue();
/*      */           }
/*      */           
/* 1314 */           Properties t2 = getPerDayDowntimeUsingBusinessHour(endDayStartTime, endDayEndTime, DBStartHour, DBEndHour, DBStartMinute, DBEndMinute);
/*      */           
/* 1316 */           String isRemove2 = (String)t2.get("toremove");
/* 1317 */           if (!"true".equals(isRemove2)) {
/* 1318 */             e2 = ((Long)t2.get("totaldowntime")).longValue();
/*      */           }
/* 1320 */           totalTime = e1 + e2;
/*      */         }
/*      */         else {
/* 1323 */           totalTime = getTotalTimeForGivenHours(firstdayStarttime, firstdayEndtime, DBStartHour, DBStartHourEnds, DBEndHourStarts, DBEndHour, DBStartMinute, DBStartMinuteEnds, DBEndMinuteStarts, DBEndMinute);
/*      */           
/* 1325 */           totalTime += getTotalTimeForGivenHours(endDayStartTime, endDayEndTime, DBStartHour, DBStartHourEnds, DBEndHourStarts, DBEndHour, DBStartMinute, DBStartMinuteEnds, DBEndMinuteStarts, DBEndMinute);
/*      */         }
/*      */       }
/*      */       else {
/* 1329 */         long firstdayStarttime = starttime;
/* 1330 */         long firstdayEndtime = getSetTimeInMillis(startYear, startMonth, startDay + 1, 0, 0, 0, 0);
/* 1331 */         long endDayStartTime = getSetTimeInMillis(endYear, endMonth, endDay, 0, 0, 0, 0);
/* 1332 */         long endDayEndTime = endtime;
/* 1333 */         if (tempBstartHour < tempBendHour) {
/* 1334 */           long e1 = 0L;
/* 1335 */           long e2 = 0L;
/*      */           
/* 1337 */           Properties t1 = getPerDayDowntimeUsingBusinessHour(firstdayStarttime, firstdayEndtime, DBStartHour, DBEndHour, DBStartMinute, DBEndMinute);
/*      */           
/* 1339 */           String isRemove = (String)t1.get("toremove");
/* 1340 */           if (!"true".equals(isRemove)) {
/* 1341 */             e1 = ((Long)t1.get("totaldowntime")).longValue();
/*      */           }
/*      */           
/* 1344 */           Properties t2 = getPerDayDowntimeUsingBusinessHour(endDayStartTime, endDayEndTime, DBStartHour, DBEndHour, DBStartMinute, DBEndMinute);
/*      */           
/* 1346 */           String isRemove2 = (String)t2.get("toremove");
/* 1347 */           if (!"true".equals(isRemove2)) {
/* 1348 */             e2 = ((Long)t2.get("totaldowntime")).longValue();
/*      */           }
/* 1350 */           totalTime = e1 + e2 + getTotalHoursInBetween(daydiff, startDayOfWeek, hourvalues);
/*      */         }
/*      */         else {
/* 1353 */           totalTime = getTotalTimeForGivenHours(firstdayStarttime, firstdayEndtime, DBStartHour, DBStartHourEnds, DBEndHourStarts, DBEndHour, DBStartMinute, DBStartMinuteEnds, DBEndMinuteStarts, DBEndMinute);
/* 1354 */           totalTime = totalTime + getTotalTimeForGivenHours(endDayStartTime, endDayEndTime, DBStartHour, DBStartHourEnds, DBEndHourStarts, DBEndHour, DBStartMinute, DBStartMinuteEnds, DBEndMinuteStarts, DBEndMinute) + getTotalHoursInBetween(daydiff, startDayOfWeek, hourvalues);
/*      */         }
/*      */       }
/*      */       
/* 1358 */       if (totalTime == 0L) {
/* 1359 */         toRemove = "true";
/*      */       }
/*      */       
/* 1362 */       toreturn.put("downtime", Long.valueOf(starttime));
/* 1363 */       toreturn.put("uptime", Long.valueOf(endtime));
/* 1364 */       toreturn.put("totaldowntime", Long.valueOf(totalTime));
/* 1365 */       toreturn.put("toremove", toRemove);
/*      */     } catch (Exception ex) {
/* 1367 */       ex.printStackTrace();
/*      */     }
/* 1369 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static long getTotalHoursInBetween(long daydiff, int startday, HashMap business) {
/* 1373 */     long toreturn = 0L;
/*      */     try
/*      */     {
/* 1376 */       Collection c = business.keySet();
/* 1377 */       Iterator itr = c.iterator();
/* 1378 */       int totalhours = 0;
/* 1379 */       long remainingdays = 0L;
/*      */       
/* 1381 */       long temphours = 0L;
/* 1382 */       if (daydiff > 7L) {
/* 1383 */         long remain = daydiff / 7L;
/* 1384 */         remainingdays = daydiff % 7L;
/* 1385 */         while (itr.hasNext()) {
/* 1386 */           String key = itr.next().toString();
/* 1387 */           String value = business.get(key).toString();
/* 1388 */           String[] temp = value.split("-");
/* 1389 */           String[] h1 = temp[0].split(":");
/* 1390 */           String[] h2 = temp[1].split(":");
/*      */           
/* 1392 */           int t1 = Integer.parseInt(h1[0]);
/* 1393 */           int t2 = Integer.parseInt(h2[0]);
/* 1394 */           if (t2 == 0) {
/* 1395 */             t2 = 24;
/*      */           }
/* 1397 */           int diff = 0;
/* 1398 */           if (t1 > t2) {
/* 1399 */             diff = 24 - t1 + t2;
/*      */           }
/*      */           else {
/* 1402 */             diff = t2 - t1;
/*      */           }
/*      */           
/* 1405 */           totalhours += diff;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1410 */         long rHours = remain * totalhours;
/* 1411 */         temphours = rHours * 60L * 60L * 1000L;
/*      */       }
/*      */       else {
/* 1414 */         remainingdays = daydiff;
/*      */       }
/*      */       
/* 1417 */       int remainingHours = 0;
/*      */       
/* 1419 */       if (daydiff > 1L)
/*      */       {
/* 1421 */         int nextday = startday + 1;
/*      */         
/* 1423 */         for (int i = 0; i < remainingdays - 1L; nextday++)
/*      */         {
/* 1425 */           if (nextday > 7) {
/* 1426 */             nextday = 1;
/*      */           }
/* 1428 */           String val = business.get(String.valueOf(nextday)).toString();
/* 1429 */           String[] temp = val.split("-");
/* 1430 */           String[] h1 = temp[0].split(":");
/* 1431 */           String[] h2 = temp[1].split(":");
/* 1432 */           int one = Integer.parseInt(h1[0]);
/* 1433 */           int two = Integer.parseInt(h2[0]);
/* 1434 */           if (0 == two)
/*      */           {
/* 1436 */             two = 24;
/*      */           }
/* 1438 */           int diff = 0;
/* 1439 */           if (one > two)
/*      */           {
/* 1441 */             diff = 24 - one + two;
/*      */           }
/*      */           else {
/* 1444 */             diff = two - one;
/*      */           }
/*      */           
/* 1447 */           remainingHours += diff;i++;
/*      */         }
/*      */       }
/*      */       
/* 1451 */       long temphours1 = remainingHours * 60 * 60 * 1000L;
/* 1452 */       toreturn = temphours1 + temphours;
/*      */     }
/*      */     catch (Exception ex) {
/* 1455 */       ex.printStackTrace();
/*      */     }
/* 1457 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static long getSetTimeInMillis(int year, int month, int day, int hour, int min, int sec, int millis) {
/* 1461 */     Calendar cal = new GregorianCalendar();
/* 1462 */     long toreturn = 0L;
/*      */     try {
/* 1464 */       cal.set(1, year);
/* 1465 */       cal.set(2, month);
/* 1466 */       cal.set(5, day);
/* 1467 */       cal.set(11, hour);
/* 1468 */       cal.set(12, min);
/* 1469 */       cal.set(13, sec);
/* 1470 */       cal.set(14, millis);
/*      */       
/* 1472 */       toreturn = cal.getTimeInMillis();
/*      */     }
/*      */     catch (Exception ex) {
/* 1475 */       ex.printStackTrace();
/*      */     }
/* 1477 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Map getAllHealthDowntimeDetails(Vector resids, long startTime, long endTime, String Bid)
/*      */   {
/* 1484 */     ResultSet set = null;
/* 1485 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1486 */     ArrayList toreturn = new ArrayList();
/* 1487 */     Map residsProperties = new HashMap();
/*      */     try {
/* 1489 */       String query = "select resid,displayname,type,attributeid,severity,message,case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime from AM_HEALTH_ALERTDATA,AM_ManagedObject where  resid=resourceid and " + getCondition("RESID", resids) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/* 1490 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 1492 */       ArrayList summary = null;
/* 1493 */       while (set.next())
/*      */       {
/* 1495 */         String resid = set.getString("resid");
/* 1496 */         Properties rows = new Properties();
/* 1497 */         rows.put("attributeid", set.getString("attributeid"));
/* 1498 */         rows.put("severity", set.getString("severity"));
/* 1499 */         rows.put("message", set.getString("message"));
/* 1500 */         rows.put("moname", EnterpriseUtil.decodeString(set.getString("displayname")));
/* 1501 */         rows.put("motype", set.getString("type"));
/* 1502 */         if ((!"oni".equals(Bid)) && (Bid != null))
/*      */         {
/*      */ 
/* 1505 */           Properties p = getWorkingTotalHours(set.getLong("DownTime"), set.getLong("UpTime"), Bid);
/*      */           
/* 1507 */           String toRemove = p.get("toremove").toString();
/* 1508 */           if ("true".equals(toRemove)) {
/*      */             continue;
/*      */           }
/* 1511 */           rows.put("downtime", p.get("downtime").toString());
/* 1512 */           rows.put("uptime", p.get("uptime").toString());
/* 1513 */           rows.put("downtimeinmillis", p.get("downtime").toString());
/* 1514 */           rows.put("TotalDownTime", p.get("totaldowntime").toString());
/*      */         }
/*      */         else {
/* 1517 */           rows.put("downtime", Long.toString(set.getLong("DownTime")));
/* 1518 */           if (set.getLong("UpTime") == endTime) {
/* 1519 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/* 1520 */             rows.put("dontdelete", "true");
/*      */           }
/*      */           else {
/* 1523 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/*      */           }
/* 1525 */           rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/* 1526 */           rows.put("TotalDownTime", Long.toString(set.getLong("TotalDownTime")));
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1532 */           long from = Long.parseLong((String)rows.get("downtime"));
/* 1533 */           long to = Long.parseLong((String)rows.get("uptime"));
/* 1534 */           if (to - from < 1000L) {
/* 1535 */             AMLog.debug("Skipping inside getAllHealthDowntimeDetails:: resid::" + resid + "::attributeid::" + format(from) + "-" + format(to));
/* 1536 */             continue;
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 1540 */           e.printStackTrace();
/*      */         }
/*      */         
/* 1543 */         if (!residsProperties.containsKey(resid))
/*      */         {
/* 1545 */           summary = new ArrayList();
/* 1546 */           summary.add(rows);
/* 1547 */           residsProperties.put(resid, summary);
/*      */         }
/*      */         else {
/* 1550 */           ArrayList a1 = (ArrayList)residsProperties.get(resid);
/* 1551 */           a1.add(rows);
/* 1552 */           residsProperties.put(resid, a1);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1557 */       ex.printStackTrace();
/*      */     }
/* 1559 */     return residsProperties;
/*      */   }
/*      */   
/*      */   public static ArrayList getDowntimeDetails(String resourceid, long startTime, long endTime, String Bid) {
/* 1563 */     ResultSet set = null;
/* 1564 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1565 */     ArrayList summary = new ArrayList();
/*      */     try
/*      */     {
/* 1568 */       String query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime, count(*) as DownCount,TYPE  from AM_MO_DowntimeData where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) group by RESID,DOWNTIME,UPTIME,TYPE order by TotalDownTime desc";
/* 1569 */       if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 1570 */         AMLog.debug("Inside if loop of the Query : ");
/* 1571 */         query = "select case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime, count(*) as DownCount,TYPE  from AM_MO_DowntimeData where RESID=" + resourceid + "  and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) group by RESID,DOWNTIME,UPTIME,TYPE order by TotalDownTime desc";
/*      */       }
/*      */       
/* 1574 */       AMLog.debug("getDowntime Details Query : " + query);
/*      */       
/*      */ 
/* 1577 */       int count = 0;
/* 1578 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1579 */       while (set.next())
/*      */       {
/* 1581 */         Properties rows = new Properties();
/* 1582 */         if ((!"oni".equals(Bid)) && (Bid != null))
/*      */         {
/* 1584 */           Properties p = getWorkingTotalHours(set.getLong("DownTime"), set.getLong("UpTime"), Bid);
/*      */           
/* 1586 */           String toRemove = p.get("toremove").toString();
/* 1587 */           if ("true".equals(toRemove)) {
/*      */             continue;
/*      */           }
/*      */           
/*      */ 
/* 1592 */           count++;
/* 1593 */           AMLog.debug("The Count value in Bid ===> : " + count);
/* 1594 */           rows.put("downtime", p.get("downtime").toString());
/* 1595 */           rows.put("uptime", p.get("uptime").toString());
/* 1596 */           rows.put("downtimeinmillis", p.get("totaldowntime").toString());
/* 1597 */           rows.put("countDown", Integer.valueOf(count));
/*      */         }
/*      */         else {
/* 1600 */           rows.put("downtime", Long.toString(set.getLong("DownTime")));
/* 1601 */           if (set.getLong("UpTime") == endTime) {
/* 1602 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/* 1603 */             rows.put("dontdelete", "true");
/*      */           }
/*      */           else {
/* 1606 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/*      */           }
/* 1608 */           rows.put("downtimeinmillis", Long.toString(set.getLong("TotalDownTime")));
/*      */           
/* 1610 */           rows.put("countDown", Integer.valueOf(set.getInt("DownCount")));
/*      */         }
/*      */         
/* 1613 */         summary.add(rows);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1617 */       ex.printStackTrace();
/*      */     }
/* 1619 */     AMLog.debug("======> GetDowntime Details  : " + summary);
/* 1620 */     return summary;
/*      */   }
/*      */   
/*      */   public static ArrayList getDowntimeAndHealthDetails(String resourceid, long startTime, long endTime, String Bid)
/*      */   {
/* 1625 */     ArrayList toreturn = new ArrayList();
/*      */     try {
/* 1627 */       ArrayList rows = getDowntimeDetails(resourceid, startTime, endTime, Bid);
/* 1628 */       for (int i = 0; i < rows.size(); i++) {
/* 1629 */         Properties p1 = (Properties)rows.get(i);
/* 1630 */         p1.put("type", "Availability");
/* 1631 */         toreturn.add(p1);
/*      */       }
/* 1633 */       ArrayList Healthrows = getHealthAlertDetails(resourceid, startTime, endTime, Bid);
/* 1634 */       for (int i = 0; i < Healthrows.size(); i++) {
/* 1635 */         Properties p1 = (Properties)Healthrows.get(i);
/* 1636 */         p1.put("type", "Health");
/* 1637 */         toreturn.add(p1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1641 */       ex.printStackTrace();
/*      */     }
/* 1643 */     return toreturn;
/*      */   }
/*      */   
/* 1646 */   public static ArrayList getHealthAlertDetails(String resourceid, long startTime, long endTime, String Bid) { ResultSet set = null;
/* 1647 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1648 */     ArrayList summary = new ArrayList();
/*      */     try
/*      */     {
/* 1651 */       String query = "select attributeid,severity,message,case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as DownTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end as UpTime, case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME <" + startTime + " and (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end as TotalDownTime from AM_HEALTH_ALERTDATA where RESID=" + resourceid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + endTime + 1 + ") else UPTIME end)) order by DOWNTIME desc";
/*      */       
/*      */ 
/*      */ 
/* 1655 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1656 */       while (set.next())
/*      */       {
/* 1658 */         Properties rows = new Properties();
/* 1659 */         rows.put("attributeid", set.getString("attributeid"));
/* 1660 */         rows.put("severity", set.getString("severity"));
/* 1661 */         rows.put("message", set.getString("message"));
/*      */         
/* 1663 */         if ((!"oni".equals(Bid)) && (Bid != null)) {
/* 1664 */           Properties p = getWorkingTotalHours(set.getLong("DownTime"), set.getLong("UpTime"), Bid);
/* 1665 */           String toRemove = p.get("toremove").toString();
/* 1666 */           if ("true".equals(toRemove)) {
/*      */             continue;
/*      */           }
/* 1669 */           rows.put("downtime", p.get("downtime").toString());
/* 1670 */           rows.put("uptime", p.get("uptime").toString());
/* 1671 */           rows.put("downtimeinmillis", p.get("downtime").toString());
/* 1672 */           rows.put("TotalDownTime", p.get("totaldowntime").toString());
/*      */         }
/*      */         else {
/* 1675 */           rows.put("downtime", Long.toString(set.getLong("DownTime")));
/* 1676 */           if (set.getLong("UpTime") == endTime)
/*      */           {
/* 1678 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/* 1679 */             rows.put("dontdelete", "true");
/*      */           }
/*      */           else
/*      */           {
/* 1683 */             rows.put("uptime", Long.toString(set.getLong("UpTime")));
/*      */           }
/* 1685 */           rows.put("downtimeinmillis", Long.toString(set.getLong("DownTime")));
/*      */           
/* 1687 */           rows.put("TotalDownTime", Long.toString(set.getLong("TotalDownTime")));
/*      */         }
/*      */         
/* 1690 */         summary.add(rows);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1694 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 1697 */     return summary;
/*      */   }
/*      */   
/*      */   public static Properties getAvailabilityStatsForMO(String resourceid, String stTime, String edTime, String period, String duration) {
/* 1701 */     try { return getAvailabilityStatsForMO(resourceid, stTime, edTime, period, duration, "oni", null);
/*      */     } catch (Exception ex) {
/* 1703 */       ex.printStackTrace();
/*      */     }
/* 1705 */     return null;
/*      */   }
/*      */   
/*      */   public static HashMap getTodaysAvailabilityForMonitors(ArrayList<String> resIds, String owner, String ownerRole) {
/* 1709 */     HashMap availStatsMap = new HashMap();
/* 1710 */     resIds = filterResIds(resIds, "MO", owner, ownerRole);
/*      */     try
/*      */     {
/* 1713 */       if (resIds.size() > 0)
/*      */       {
/* 1715 */         AMLog.info("REPORT-UTILITIES : getTodaysAvailabilityForMonitors : selected monitors" + resIds);
/* 1716 */         for (String resourceID : resIds)
/*      */         {
/* 1718 */           Properties availProps = getTodaysAvailabilityForMonitors(resourceID);
/* 1719 */           if (availProps != null) {
/* 1720 */             availStatsMap.put(resourceID, availProps);
/*      */           }
/*      */         }
/* 1723 */         AMLog.info("REPORT-UTILITIES : getTodaysAvailabilityForMonitors stats" + availStatsMap);
/*      */       }
/*      */     } catch (Exception e) {
/* 1726 */       AMLog.debug("REPORT-UTILITIES : getTodaysAvailabilityForMonitors : " + e.getMessage());
/* 1727 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1730 */     return availStatsMap;
/*      */   }
/*      */   
/*      */   public static Properties getTodaysAvailabilityForMonitors(String resId) {
/* 1734 */     Properties availProps = null;
/*      */     try {
/* 1736 */       availProps = getAvailabilityStatsForMO(resId, "0", "0", "0", null);
/* 1737 */       formatAvailProps(availProps);
/*      */     } catch (Exception e) {
/* 1739 */       e.printStackTrace();
/*      */     }
/* 1741 */     AMLog.debug("REPORT-UTILITIES : getTodaysAvailabilityForMonitors : resId :" + availProps);
/* 1742 */     return availProps;
/*      */   }
/*      */   
/*      */   private static void formatAvailProps(Properties availProps) {
/* 1746 */     String[] propsNeedToFormat = { "available", "OverallScheduledDowntime", "OverallUnmanagedTime", "unavailable", "ServicesUnMgPercent", "ServicesSchPercent" };
/* 1747 */     for (String availProp : propsNeedToFormat) {
/* 1748 */       if (availProps.containsKey(availProp))
/*      */       {
/*      */         try
/*      */         {
/* 1752 */           DecimalFormat df = new DecimalFormat("0.###");
/* 1753 */           availProps.setProperty(availProp, df.format(availProps.getProperty(availProp)));
/*      */         }
/*      */         catch (Exception e) {
/* 1756 */           AMLog.debug("ReportUtilities : formatAvailProps :" + e.getMessage());
/*      */         } }
/*      */     }
/*      */   }
/*      */   
/*      */   public static ArrayList<String> filterResIds(ArrayList<String> resIds, String moType, String owner, String ownerRole) {
/* 1762 */     ArrayList<String> qualifiedResIds = new ArrayList();
/* 1763 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1766 */       String checkQuery = null;
/* 1767 */       String operatorCondition = owner != null ? getCondition(owner, ownerRole) : "";
/* 1768 */       if ("MO".equalsIgnoreCase(moType)) {
/* 1769 */         checkQuery = "SELECT AM_ManagedObject.RESOURCEID from AM_ManagedObject, AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedObject.TYPE in " + Constants.resourceTypes + operatorCondition;
/*      */       }
/* 1771 */       else if ("HAI".equalsIgnoreCase(moType)) {
/* 1772 */         checkQuery = "SELECT RESOURCEID, DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION where RESOURCEID=HAID " + operatorCondition;
/*      */       } else {
/* 1774 */         return resIds;
/*      */       }
/*      */       
/* 1777 */       rs = AMConnectionPool.executeQueryStmt(checkQuery);
/* 1778 */       while (rs.next()) {
/* 1779 */         qualifiedResIds.add(rs.getString("RESOURCEID"));
/*      */       }
/*      */       
/* 1782 */       for (String resourceId : resIds) {
/* 1783 */         if (!qualifiedResIds.contains(resourceId)) {
/* 1784 */           resIds.remove(resourceId);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1790 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1793 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1795 */     return null;
/*      */   }
/*      */   
/*      */   public static Properties getAvailabilityStatsForMO(String resourceid, String stTime, String edTime, String period, String duration, String bid, String value)
/*      */   {
/* 1800 */     AMLog.debug("Get Availability Stats for MO  :  ");
/* 1801 */     DecimalFormat threeDForm = new DecimalFormat("#.###");
/* 1802 */     threeDForm.setMinimumFractionDigits(3);
/* 1803 */     long startTime = Long.parseLong(stTime);
/* 1804 */     long endTime = Long.parseLong(edTime);
/* 1805 */     long totalDuration = 0L;
/* 1806 */     int count = 1;
/* 1807 */     ResultSet set = null;
/* 1808 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1809 */     Properties summary = new Properties();
/*      */     try
/*      */     {
/* 1812 */       long mintimeindb = getMinTimeInDB(resourceid);
/* 1813 */       long[] temptime = getTimeStamp(period);
/*      */       
/* 1815 */       if ("-99".equals(period)) {
/* 1816 */         startTime = Long.parseLong(stTime);
/* 1817 */         endTime = Long.parseLong(edTime);
/* 1818 */         if ((mintimeindb > startTime) && (mintimeindb < endTime)) {
/* 1819 */           startTime = mintimeindb;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1824 */         AMLog.debug("REPORT-UTILITIES : Period : " + period + " StartTime : " + new java.util.Date(startTime) + " EndTime : " + new java.util.Date(endTime));
/* 1825 */         if ((period.equals("0")) || (period.equals("1")) || (period.equals("2")) || (period.equals("5")) || (period.equals("6")) || (period.equals("7")) || (period.equals("8")) || (period.equals("9")))
/*      */         {
/* 1827 */           if (mintimeindb > temptime[0]) {
/* 1828 */             startTime = mintimeindb;
/* 1829 */             endTime = temptime[1];
/*      */           }
/* 1831 */           else if (mintimeindb != 0L) {
/* 1832 */             startTime = temptime[0];
/* 1833 */             endTime = temptime[1];
/*      */           } } else { long[] todayTime;
/*      */           Properties localProperties1;
/* 1836 */           if (period.equals("3"))
/*      */           {
/* 1838 */             temptime = getTimeStamp("3");
/* 1839 */             todayTime = getTimeStamp("0");
/* 1840 */             if (mintimeindb > todayTime[0]) {
/* 1841 */               return summary;
/*      */             }
/*      */             
/* 1844 */             if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1850 */               startTime = mintimeindb;
/* 1851 */               endTime = temptime[1];
/*      */             }
/* 1853 */             else if (mintimeindb != 0L) {
/* 1854 */               startTime = temptime[0];
/* 1855 */               endTime = temptime[1];
/*      */             }
/*      */           }
/*      */           else {
/* 1859 */             if (period.equals("-4")) {
/* 1860 */               return summary;
/*      */             }
/* 1862 */             if (period.equals("4"))
/*      */             {
/*      */ 
/* 1865 */               if (mintimeindb > endTime)
/*      */               {
/* 1867 */                 return summary;
/*      */               }
/* 1869 */               if (mintimeindb > startTime)
/*      */               {
/* 1871 */                 startTime = mintimeindb;
/*      */               }
/*      */               
/*      */             }
/* 1875 */             else if (period.equals("11")) {
/* 1876 */               temptime = getTimeStamp("11");
/* 1877 */               long[] todayTime = getTimeStamp("7");
/* 1878 */               if (mintimeindb > todayTime[0]) {
/* 1879 */                 return summary;
/*      */               }
/*      */               
/* 1882 */               if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */               {
/* 1884 */                 startTime = mintimeindb;
/* 1885 */                 endTime = temptime[1];
/*      */               }
/* 1887 */               else if (mintimeindb != 0L) {
/* 1888 */                 startTime = temptime[0];
/* 1889 */                 endTime = temptime[1];
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/* 1895 */             else if (period.equals("12")) {
/* 1896 */               temptime = getTimeStamp("12");
/* 1897 */               long[] todayTime = getTimeStamp("6");
/* 1898 */               if (mintimeindb > todayTime[0]) {
/* 1899 */                 return summary;
/*      */               }
/*      */               
/* 1902 */               if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */               {
/* 1904 */                 startTime = mintimeindb;
/* 1905 */                 endTime = temptime[1];
/*      */               }
/* 1907 */               else if (mintimeindb != 0L) {
/* 1908 */                 startTime = temptime[0];
/* 1909 */                 endTime = temptime[1];
/*      */               }
/*      */             }
/*      */           }
/*      */         } }
/* 1914 */       ResultSet displayNameSet = null;
/* 1915 */       String displayName = null;
/*      */       try
/*      */       {
/* 1918 */         if (DBUtil.minTimeinDBMGs.containsKey(resourceid)) {
/*      */           try {
/* 1920 */             displayName = (String)((HashMap)DBUtil.minTimeinDBMGs.get(resourceid)).get("dspName");
/*      */           } catch (Exception he) {
/* 1922 */             he.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/* 1926 */         if (displayName == null) {
/* 1927 */           String displayNameQuery = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 1928 */           displayNameSet = AMConnectionPool.executeQueryStmt(displayNameQuery);
/* 1929 */           if (displayNameSet.next()) {
/* 1930 */             displayName = displayNameSet.getString("DISPLAYNAME");
/* 1931 */             displayName = EnterpriseUtil.decodeString(displayName);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1937 */         AMLog.fatal("Exception in getting RESOURCENAME details ", ex);
/*      */       }
/*      */       finally {}
/*      */       
/*      */ 
/*      */ 
/* 1943 */       AMLog.debug("REPORT-UTILITIES B4 Query : Period : " + period + " StartTime : " + new java.util.Date(startTime) + " EndTime : " + new java.util.Date(endTime));
/*      */       
/* 1945 */       boolean firstTime = true;
/*      */       try
/*      */       {
/* 1948 */         AMLog.info("ReportUtilities before querying MODOWNTIMEDATA from MGDetailsPage calling AMDataCorrectionUtil.checkFalseDownTime for resID:" + resourceid);
/* 1949 */         AMDataCorrectionUtil.checkFalseDowntime(Integer.parseInt(resourceid), "AVAILABILITY");
/*      */       }
/*      */       catch (Exception exc) {
/* 1952 */         exc.printStackTrace();
/*      */       }
/* 1954 */       for (int i = 0; i < 2; i++)
/*      */       {
/* 1956 */         String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID in (" + resourceid + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by TotalDownTime desc";
/* 1957 */         if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 1958 */           query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID in (" + resourceid + ") and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by TotalDownTime desc";
/*      */         }
/*      */         
/*      */ 
/* 1962 */         AMLog.debug("REPORT-UTILITIES : getAvailabilityStatsForMO QUERY = " + query);
/* 1963 */         totalDuration = endTime - startTime;
/* 1964 */         long totalDowntime = 0L;
/* 1965 */         long totalUnmanagedtime = 0L;
/* 1966 */         long totalScheduledtime = 0L;
/* 1967 */         int typeID = 0;
/* 1968 */         int downCount = 0;
/* 1969 */         set = AMConnectionPool.executeQueryStmt(query);
/* 1970 */         while (set.next())
/*      */         {
/*      */ 
/* 1973 */           downCount = set.getInt("DownCount");
/* 1974 */           count = set.getInt("DownCount");
/* 1975 */           typeID = set.getInt("TYPE");
/* 1976 */           if (typeID == 1)
/*      */           {
/* 1978 */             totalDowntime = set.getLong("TotalDownTime");
/*      */           }
/* 1980 */           else if (typeID == 2)
/*      */           {
/* 1982 */             totalUnmanagedtime = set.getLong("TotalDownTime");
/*      */           }
/*      */           else
/*      */           {
/* 1986 */             totalScheduledtime = set.getLong("TotalDownTime");
/*      */           }
/*      */         }
/*      */         
/* 1990 */         long uptime = totalDuration - (totalDowntime + totalUnmanagedtime + totalScheduledtime);
/* 1991 */         float upPercent = (float)uptime / (float)totalDuration * 100.0F;
/* 1992 */         float downPercent = (float)totalDowntime / (float)totalDuration * 100.0F;
/* 1993 */         float unmgPercent = (float)totalUnmanagedtime / (float)totalDuration * 100.0F;
/* 1994 */         float schPercent = (float)totalScheduledtime / (float)totalDuration * 100.0F;
/* 1995 */         long mttr = totalDowntime / count;
/* 1996 */         long mtbf = uptime / count;
/* 1997 */         String down = format(totalDowntime);
/* 1998 */         if ((upPercent < 0.0F) || (upPercent > 100.0F) || (downPercent < 0.0F) || (downPercent > 100.0F) || (uptime < 0L) || (totalDowntime < 0L) || (unmgPercent < 0.0F) || (unmgPercent > 100.0F) || (schPercent < 0.0F) || (schPercent > 100.0F) || (totalUnmanagedtime < 0L) || (totalScheduledtime < 0L))
/*      */         {
/* 2000 */           AMLog.debug("**********************************************************************************");
/* 2001 */           AMLog.debug("ReportUtilities :  Resource ID: " + resourceid + " uptime: " + uptime + " downtime: " + totalDowntime + "Unmanaged Percent " + unmgPercent + " totalUnmanagedTime :" + totalUnmanagedtime + "Scheduled Percent " + schPercent + " totalScheduledTime :" + totalScheduledtime);
/* 2002 */           AMLog.debug("**********************************************************************************");
/* 2003 */           if (firstTime)
/*      */           {
/* 2005 */             firstTime = false;
/* 2006 */             AMDataCorrectionUtil.correctResourceForAvailability(Integer.parseInt(resourceid));
/* 2007 */             AMDataCorrectionUtil.correctOverlappingEntriesForAvailability(Integer.parseInt(resourceid));
/* 2008 */             AMDataCorrectionUtil.correctInconsistentDataForAvailability(Integer.parseInt(resourceid));
/*      */           }
/*      */           else
/*      */           {
/* 2012 */             throw new IllegalStateException("report.inconsistentdata");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2017 */           summary.setProperty("mttr", format(mttr));
/* 2018 */           summary.setProperty("totaldowntime", down);
/* 2019 */           summary.setProperty("mtbf", format(mtbf));
/* 2020 */           summary.setProperty("Name", displayName);
/* 2021 */           if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */           {
/* 2023 */             summary.setProperty("available", threeDForm.format(upPercent));
/*      */           }
/* 2025 */           else if (upPercent >= 99.995D)
/*      */           {
/* 2027 */             summary.setProperty("available", fourDForm.format(upPercent));
/*      */           }
/*      */           else {
/* 2030 */             summary.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/*      */           }
/* 2032 */           if (downPercent < 0.005D)
/*      */           {
/* 2034 */             summary.setProperty("unavailable", fourDForm.format(downPercent));
/*      */           }
/*      */           else
/*      */           {
/* 2038 */             summary.setProperty("unavailable", String.valueOf(Math.round(downPercent * 100.0F) / 100.0F));
/*      */           }
/* 2040 */           summary.setProperty("TotalUnmanagedTime", String.valueOf(totalUnmanagedtime));
/* 2041 */           summary.setProperty("TotalScheduledTime", String.valueOf(totalScheduledtime));
/* 2042 */           summary.setProperty("OverallUnmanagedTime", String.valueOf(Math.round(unmgPercent * 100.0F) / 100.0F));
/* 2043 */           summary.setProperty("OverallScheduledDowntime", String.valueOf(Math.round(schPercent * 100.0F) / 100.0F));
/* 2044 */           summary.setProperty("ServicesUnMgPercent", String.valueOf(Math.round(unmgPercent * 100.0F) / 100.0F));
/* 2045 */           summary.setProperty("ServicesSchPercent", String.valueOf(Math.round(schPercent * 100.0F) / 100.0F));
/* 2046 */           summary.setProperty("Mintime", String.valueOf(startTime));
/* 2047 */           summary.setProperty("Maxtime", String.valueOf(endTime));
/*      */           
/*      */ 
/* 2050 */           if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */           {
/* 2052 */             summary.setProperty("servicesup", threeDForm.format(upPercent));
/*      */           }
/* 2054 */           else if (upPercent >= 99.995D)
/*      */           {
/* 2056 */             summary.setProperty("servicesup", fourDForm.format(upPercent));
/*      */           }
/*      */           else {
/* 2059 */             summary.setProperty("servicesup", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/*      */           }
/* 2061 */           summary.setProperty("overalluptime", String.valueOf(uptime));
/* 2062 */           summary.setProperty("OverallDowntime", String.valueOf(totalDowntime));
/* 2063 */           summary.setProperty("overallmtbf", String.valueOf(mtbf));
/* 2064 */           summary.setProperty("overallmttr", String.valueOf(mttr));
/* 2065 */           if (downPercent < 0.005D)
/*      */           {
/* 2067 */             summary.setProperty("ServicesDownPercent", fourDForm.format(downPercent));
/*      */           }
/*      */           else
/*      */           {
/* 2071 */             summary.setProperty("ServicesDownPercent", String.valueOf(Math.round(downPercent * 100.0F) / 100.0F));
/*      */           }
/*      */           
/* 2074 */           summary.setProperty("countDown", String.valueOf(downCount));
/* 2075 */           summary.setProperty("TotDown", String.valueOf(totalDowntime));
/*      */           
/*      */ 
/* 2078 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IllegalStateException ise) {
/* 2083 */       throw ise;
/*      */ 
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 2088 */       AMLog.fatal("Exception in getAvailabilityStatsForMO", exp);
/*      */     }
/*      */     finally {
/* 2091 */       closeResultSet(set);
/*      */     }
/* 2093 */     AMLog.debug("getAvailabilityStatsforMO() ===> : " + summary);
/* 2094 */     return summary;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Map getAvailabilityDetailsForTwelveTimePeriods(Vector haids, Vector resids, ArrayList allTimes, String bid, String value)
/*      */   {
/* 2100 */     Map putData = new HashMap();
/*      */     try
/*      */     {
/* 2103 */       Hashtable mgmintimemap = getMinTimeInDB(haids);
/*      */       
/* 2105 */       for (int i = 12; i >= 0; i--)
/*      */       {
/* 2107 */         int key = i + 1;
/*      */         
/* 2109 */         String keyName = key + "";
/*      */         
/* 2111 */         Properties p1 = (Properties)allTimes.get(i);
/*      */         
/* 2113 */         long starttime = Long.parseLong(p1.get("starttime").toString());
/* 2114 */         long endtime = Long.parseLong(p1.get("endtime").toString());
/*      */         
/* 2116 */         for (int j = 0; j < haids.size(); j++) {
/* 2117 */           Map haidData = new HashMap();
/* 2118 */           String id = haids.get(j).toString();
/* 2119 */           long mintime = ((Long)mgmintimemap.get(id)).longValue();
/*      */           
/*      */ 
/* 2122 */           Properties uptimeprops = getMonitorGroupAvailability(id, "-99", starttime, endtime, bid, value);
/*      */           
/*      */ 
/*      */ 
/* 2126 */           String uptime = uptimeprops.getProperty("available");
/* 2127 */           String downcount = uptimeprops.getProperty("countDown");
/* 2128 */           String totaldowntime = uptimeprops.getProperty("totaldowntime");
/* 2129 */           String TotDown = uptimeprops.getProperty("TotDown");
/*      */           
/*      */ 
/* 2132 */           if (putData.containsKey(id)) {
/* 2133 */             HashMap t = (HashMap)putData.get(id);
/*      */             
/* 2135 */             if (value.equalsIgnoreCase("trendDowntimeReport")) {
/* 2136 */               HashMap n = new HashMap();
/* 2137 */               n.put("uptime", uptime);
/* 2138 */               n.put("downcount", downcount);
/* 2139 */               n.put("totaldowntime", totaldowntime);
/* 2140 */               n.put("TotDown", TotDown);
/* 2141 */               t.put(keyName, n);
/*      */             }
/*      */             else
/*      */             {
/* 2145 */               t.put(keyName, uptime);
/*      */             }
/* 2147 */             putData.put(id, t);
/*      */           }
/*      */           else {
/* 2150 */             HashMap g = new HashMap();
/* 2151 */             if (value.equalsIgnoreCase("trendDowntimeReport")) {
/* 2152 */               HashMap n = new HashMap();
/* 2153 */               n.put("uptime", uptime);
/* 2154 */               n.put("downcount", downcount);
/* 2155 */               n.put("totaldowntime", totaldowntime);
/* 2156 */               n.put("TotDown", TotDown);
/* 2157 */               g.put(keyName, n);
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 2162 */               g.put(keyName, uptime);
/*      */             }
/* 2164 */             putData.put(id, g);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2203 */       ex.printStackTrace();
/*      */     }
/* 2205 */     AMLog.debug("THE Map in getAvailabilityDetailsForTwelveTimePeriods() Method ===> : " + putData);
/* 2206 */     return putData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList getBusinessAvailabilityDataForMonitors(Vector resids, long startTime, long endTime, String Bid)
/*      */   {
/* 2213 */     ArrayList summary = new ArrayList();
/* 2214 */     Vector resources = new Vector();
/* 2215 */     resources = (Vector)resids.clone();
/*      */     
/*      */     try
/*      */     {
/* 2219 */       Properties p = getWorkingTotalHours(startTime, endTime, Bid);
/*      */       
/* 2221 */       long totalduration = Long.parseLong(p.getProperty("totaldowntime"));
/*      */       
/* 2223 */       Map Data = getAllDowntimeDetails(resids, startTime, endTime, Bid);
/*      */       
/* 2225 */       if (Data.size() > 0) {
/* 2226 */         Collection c = Data.keySet();
/* 2227 */         Iterator itr = c.iterator();
/* 2228 */         while (itr.hasNext()) {
/* 2229 */           String key = itr.next().toString();
/* 2230 */           ArrayList a1 = (ArrayList)Data.get(key);
/* 2231 */           long totaldowntime = 0L;
/* 2232 */           int count = 0;
/* 2233 */           Properties rows = new Properties();
/* 2234 */           for (int j = 0; j < a1.size(); j++) {
/* 2235 */             Properties p1 = (Properties)a1.get(j);
/* 2236 */             String toRemove = p.get("toremove").toString();
/* 2237 */             if (!"true".equals(toRemove))
/*      */             {
/*      */ 
/*      */ 
/* 2241 */               totaldowntime += Long.parseLong(p1.getProperty("TotalDownTime"));
/* 2242 */               count++;
/*      */             }
/*      */           }
/* 2245 */           String moname = getLabelName(key);
/* 2246 */           rows.setProperty("Name", moname);
/* 2247 */           rows.setProperty("resourceid", key);
/* 2248 */           long uptime = totalduration - totaldowntime;
/* 2249 */           if (resources.contains(key)) {
/* 2250 */             resources.remove(key);
/*      */           }
/* 2252 */           rows.setProperty("totaldowntime", format(totaldowntime));
/* 2253 */           rows.put("DowntimeInLong", new Long(totaldowntime));
/* 2254 */           long mttr = totaldowntime / count;
/* 2255 */           long mtbf = uptime / count;
/* 2256 */           rows.setProperty("mttr", format(mttr));
/* 2257 */           rows.setProperty("mtbf", format(mtbf));
/* 2258 */           float upPercent = (float)uptime / (float)totalduration * 100.0F;
/* 2259 */           float downPercent = (float)totaldowntime / (float)totalduration * 100.0F;
/* 2260 */           rows.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/* 2261 */           summary.add(rows);
/*      */         }
/* 2263 */         int size = resources.size();
/*      */         
/* 2265 */         for (int i = 0; i < size; i++)
/*      */         {
/* 2267 */           String resourceid = (String)resources.get(i);
/* 2268 */           String moname = getLabelName(resourceid);
/* 2269 */           Properties rows1 = new Properties();
/* 2270 */           rows1.setProperty("Name", moname);
/* 2271 */           rows1.setProperty("resourceid", resourceid);
/* 2272 */           rows1.setProperty("totaldowntime", format(0L));
/* 2273 */           rows1.put("DowntimeInLong", new Long(0L));
/* 2274 */           rows1.setProperty("mttr", format(0L));
/*      */           
/* 2276 */           rows1.setProperty("mtbf", format(0L));
/* 2277 */           rows1.setProperty("available", "100");
/* 2278 */           summary.add(rows1);
/*      */         }
/*      */       }
/*      */       else {
/* 2282 */         for (int i = 0; i < resids.size(); i++) {
/* 2283 */           Properties rows1 = new Properties();
/* 2284 */           String moname = getLabelName(resids.get(i).toString());
/* 2285 */           rows1.setProperty("Name", moname);
/* 2286 */           rows1.setProperty("resourceid", resids.get(i).toString());
/* 2287 */           rows1.setProperty("totaldowntime", format(0L));
/* 2288 */           rows1.put("DowntimeInLong", new Long(0L));
/* 2289 */           rows1.setProperty("mttr", format(0L));
/*      */           
/* 2291 */           rows1.setProperty("mtbf", format(0L));
/* 2292 */           rows1.setProperty("available", "100");
/* 2293 */           summary.add(rows1);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 2297 */       ex.printStackTrace();
/*      */     }
/* 2299 */     return summary;
/*      */   }
/*      */   
/*      */   public static ArrayList getAvailabilityDataForMonitors(Vector resids, long startTime, long endTime, String Bid) {
/* 2303 */     long totalduration = 0L;
/* 2304 */     ArrayList data = new ArrayList();
/* 2305 */     Vector resources = new Vector();
/* 2306 */     resources = (Vector)resids.clone();
/*      */     
/* 2308 */     Properties durationProp = new Properties();
/* 2309 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2312 */       if ((!"oni".equals(Bid)) && (Bid != null))
/*      */       {
/* 2314 */         return getBusinessAvailabilityDataForMonitors(resids, startTime, endTime, Bid);
/*      */       }
/*      */       
/*      */ 
/* 2318 */       String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount from AM_MO_DowntimeData where " + getCondition("RESID", resids) + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID order by TotalDownTime desc";
/*      */       
/* 2320 */       totalduration = endTime - startTime;
/* 2321 */       Properties p = null;
/*      */       
/*      */ 
/* 2324 */       ResultSet set = AMConnectionPool.executeQueryStmt(query);
/* 2325 */       while (set.next()) {
/* 2326 */         long totaldowntime = set.getLong("TotalDownTime");
/*      */         
/* 2328 */         Properties rows = new Properties();
/* 2329 */         int resourceid = set.getInt("RESID");
/* 2330 */         durationProp.setProperty(resourceid + "", String.valueOf(totalduration));
/*      */         
/* 2332 */         int count = set.getInt("DownCount");
/* 2333 */         long uptime = totalduration - totaldowntime;
/* 2334 */         if (resources.contains(String.valueOf(resourceid)))
/* 2335 */           resources.remove(String.valueOf(resourceid));
/* 2336 */         if (uptime < 0L) {
/* 2337 */           AMLog.fatal("Reports :  Fatal Error****************************************************");
/* 2338 */           AMLog.fatal("Reports :  Error in the stored  availability information for " + resourceid);
/* 2339 */           AMLog.fatal("Reports :  totalduration " + totalduration);
/* 2340 */           AMLog.fatal("Reports :  totaldowntime " + totaldowntime);
/* 2341 */           AMLog.fatal("Reports :  execute :select * from AM_MO_DowntimeData where RESID " + resourceid);
/* 2342 */           AMLog.fatal("Reports :  Error : It has multiple rows with UPTIME=0");
/* 2343 */           AMLog.fatal("Reports :  ***************************************************************");
/*      */         }
/*      */         else {
/* 2346 */           String moname = getLabelName(String.valueOf(resourceid));
/* 2347 */           rows.setProperty("Name", moname);
/* 2348 */           rows.setProperty("resourceid", String.valueOf(resourceid));
/* 2349 */           rows.setProperty("totaldowntime", format(totaldowntime));
/* 2350 */           rows.put("DowntimeInLong", new Long(totaldowntime));
/* 2351 */           long mttr = totaldowntime / count;
/* 2352 */           long mtbf = uptime / count;
/* 2353 */           rows.setProperty("mttr", format(mttr));
/* 2354 */           rows.setProperty("mtbf", format(mtbf));
/* 2355 */           float upPercent = (float)uptime / (float)totalduration * 100.0F;
/* 2356 */           float downPercent = (float)totaldowntime / (float)totalduration * 100.0F;
/* 2357 */           if (upPercent > 100.0F) {
/* 2358 */             rows.setProperty("available", "100");
/*      */           } else {
/* 2360 */             rows.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/*      */           }
/* 2362 */           data.add(rows);
/*      */         }
/*      */       }
/* 2365 */       int currentDataSize = data.size();
/* 2366 */       int size = resources.size();
/*      */       
/* 2368 */       for (int i = 0; i < size; i++)
/*      */       {
/* 2370 */         String resourceid = (String)resources.get(i);
/* 2371 */         String moname = getLabelName(resourceid);
/* 2372 */         Properties rows1 = new Properties();
/* 2373 */         rows1.setProperty("Name", moname);
/* 2374 */         rows1.setProperty("resourceid", resourceid);
/* 2375 */         rows1.setProperty("totaldowntime", format(0L));
/* 2376 */         rows1.put("DowntimeInLong", new Long(0L));
/* 2377 */         rows1.setProperty("mttr", format(0L));
/*      */         
/* 2379 */         rows1.setProperty("mtbf", format(0L));
/* 2380 */         rows1.setProperty("available", "100");
/* 2381 */         data.add(rows1);
/*      */       }
/* 2383 */       AMConnectionPool.closeStatement(set);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2388 */       ex.printStackTrace();
/*      */     }
/* 2390 */     return data;
/*      */   }
/*      */   
/* 2393 */   public static ArrayList getDataForAvailabilityTrendReport(ArrayList resourceids, ArrayList allTimes, String bid, String value) { ArrayList AllData = new ArrayList();
/*      */     try
/*      */     {
/* 2396 */       Vector haids = new Vector();
/* 2397 */       Vector monitorids = new Vector();
/* 2398 */       for (int r = 0; r < resourceids.size(); r++)
/*      */       {
/* 2400 */         ArrayList M1 = (ArrayList)resourceids.get(r);
/* 2401 */         if (M1.size() > 0) {
/* 2402 */           for (int s = 0; s < M1.size(); s++) {
/* 2403 */             ArrayList mg = new ArrayList();
/* 2404 */             ArrayList insideM1 = (ArrayList)M1.get(s);
/*      */             
/* 2406 */             String RID = insideM1.get(0).toString();
/*      */             
/* 2408 */             String DISNAME = insideM1.get(2).toString();
/* 2409 */             String TYPE = insideM1.get(3).toString();
/* 2410 */             if ("HAI".equals(TYPE)) {
/* 2411 */               haids.add(RID);
/*      */             }
/* 2413 */             else if (!monitorids.contains(RID)) {
/* 2414 */               monitorids.add(RID);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2422 */       Map Data = getAvailabilityDetailsForTwelveTimePeriods(haids, monitorids, allTimes, bid, value);
/*      */       
/* 2424 */       for (int i = 0; i < resourceids.size(); i++) {
/* 2425 */         ArrayList M1 = (ArrayList)resourceids.get(i);
/* 2426 */         if (M1.size() > 0) {
/* 2427 */           for (int s = 0; s < M1.size(); s++) {
/* 2428 */             ArrayList insideM1 = (ArrayList)M1.get(s);
/*      */             
/* 2430 */             String ID = insideM1.get(0).toString();
/*      */             
/* 2432 */             HashMap h1 = (HashMap)Data.get(ID);
/*      */             
/* 2434 */             insideM1.add(h1);
/* 2435 */             AllData.add(insideM1);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2443 */       ex.printStackTrace();
/*      */     }
/* 2445 */     return AllData;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList checkArchivedtime(ArrayList a1, long Archivedtime)
/*      */   {
/* 2451 */     if ((a1 != null) && (a1.size() > 0)) {
/* 2452 */       for (int i = 0; i < a1.size(); i++) {
/* 2453 */         ArrayList a2 = (ArrayList)a1.get(i);
/* 2454 */         long l1 = ((Long)a2.get(0)).longValue();
/* 2455 */         if (l1 == Archivedtime) {
/* 2456 */           return a2;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2461 */     return null;
/*      */   }
/*      */   
/*      */   public static String getConvertedToString(Vector RESV) {
/* 2465 */     StringBuffer condition = new StringBuffer("");
/*      */     try
/*      */     {
/* 2468 */       if ((RESV != null) && (RESV.size() > 0))
/*      */       {
/* 2470 */         for (int i = 0; i < RESV.size(); i++)
/*      */         {
/* 2472 */           if (i + 1 == RESV.size())
/*      */           {
/*      */ 
/* 2475 */             condition.append(RESV.get(i) + "");
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2482 */             condition.append(RESV.get(i) + ",");
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2491 */       ex.printStackTrace();
/*      */     }
/* 2493 */     String s = condition.toString();
/* 2494 */     return s;
/*      */   }
/*      */   
/*      */   public static String getCondition(String owner, String ownerRole) {
/* 2498 */     String qryCondition = "";
/*      */     try
/*      */     {
/* 2501 */       StringBuilder resIds = new StringBuilder();
/* 2502 */       resIds.append("-1");
/* 2503 */       Vector resIds_vector = new Vector();
/* 2504 */       if ((ownerRole != null) && (ownerRole.equalsIgnoreCase("OPERATOR")))
/*      */       {
/*      */ 
/* 2507 */         resIds_vector = getResourceIdentity(owner, Boolean.valueOf(true));
/* 2508 */         if ((resIds_vector != null) && (resIds_vector.size() != 0))
/*      */         {
/* 2510 */           for (int i = 0; i < resIds_vector.size(); i++)
/*      */           {
/* 2512 */             resIds.append(",").append((String)resIds_vector.get(i));
/*      */           }
/*      */         }
/* 2515 */         qryCondition = " and AM_ManagedObject.RESOURCEID in (" + resIds + ") ";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2520 */       e.printStackTrace();
/*      */     }
/* 2522 */     return qryCondition;
/*      */   }
/*      */   
/*      */   public static Vector getResourceIdentity(String owner)
/*      */   {
/* 2527 */     return getResourceIdentity(owner, Boolean.valueOf(false), null);
/*      */   }
/*      */   
/*      */ 
/*      */   public static Vector getResourceIdentity(String owner, HttpServletRequest request)
/*      */   {
/* 2533 */     return getResourceIdentity(owner, Boolean.valueOf(false), request);
/*      */   }
/*      */   
/*      */ 
/*      */   public static Vector getResourceIdentity(String owner, Boolean flag)
/*      */   {
/* 2539 */     return getResourceIdentity(owner, flag, null);
/*      */   }
/*      */   
/*      */   public static Vector getResourceIdentity(String owner, Boolean flag, HttpServletRequest request)
/*      */   {
/* 2544 */     Vector result = new Vector();
/* 2545 */     if (flag.booleanValue())
/*      */     {
/* 2547 */       System.out.println("inside the flag");
/* 2548 */       result = SegmentReportUtil.getResourceIdentityForOwner(owner, request);
/*      */     }
/*      */     else
/*      */     {
/* 2552 */       result = SegmentReportUtil.getResourceIdentity(owner, request);
/*      */     }
/* 2554 */     return result;
/*      */   }
/*      */   
/*      */   public String getValueForPeriodForPDF(String period)
/*      */   {
/* 2559 */     int p = Integer.parseInt(period);
/* 2560 */     if (p == 0)
/*      */     {
/* 2562 */       return "Today's";
/*      */     }
/* 2564 */     if ((p == 1) || (p == -7))
/*      */     {
/* 2566 */       return "Last 7 Days";
/*      */     }
/* 2568 */     if ((p == 2) || (p == -30))
/*      */     {
/* 2570 */       return "Last 30 days";
/*      */     }
/* 2572 */     if ((p == 3) || (p == -1))
/*      */     {
/* 2574 */       return "Yesterday's";
/*      */     }
/* 2576 */     if (p == 4)
/*      */     {
/* 2578 */       return "Custom Time Period";
/*      */     }
/* 2580 */     if ((p == 5) || (p == -5))
/*      */     {
/* 2582 */       return "Last One Year's";
/*      */     }
/* 2584 */     if (p == 6)
/*      */     {
/* 2586 */       return "This Week's";
/*      */     }
/*      */     
/* 2589 */     if (p == 7)
/*      */     {
/* 2591 */       return "This Month's";
/*      */     }
/* 2593 */     if (p == 8)
/*      */     {
/* 2595 */       return "This Year's";
/*      */     }
/* 2597 */     if (p == 9)
/*      */     {
/* 2599 */       return "This Quarter's";
/*      */     }
/* 2601 */     if (p == 10)
/*      */     {
/* 2603 */       return "This Half";
/*      */     }
/* 2605 */     if (p == 11)
/*      */     {
/* 2607 */       return "Last Month's";
/*      */     }
/* 2609 */     if (p == 12)
/*      */     {
/* 2611 */       return "Last Week's";
/*      */     }
/* 2613 */     if (p == 14)
/*      */     {
/* 2615 */       return "Last 2 days polled values";
/*      */     }
/* 2617 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getCondition(String column, Vector v)
/*      */   {
/* 2626 */     return SegmentReportUtil.getCondition(column, v);
/*      */   }
/*      */   
/*      */   public static String getNotCondition(String column, Vector v)
/*      */   {
/* 2631 */     StringBuffer condition = new StringBuffer(column + " not in (");
/* 2632 */     if (v == null)
/*      */     {
/* 2634 */       return column + " in (-1) ";
/*      */     }
/* 2636 */     if ((v != null) && (v.size() == 0))
/*      */     {
/* 2638 */       return column + " in (-1) ";
/*      */     }
/*      */     
/*      */ 
/* 2642 */     for (int i = 0; i < v.size(); i++)
/*      */     {
/* 2644 */       if (i + 1 == v.size())
/*      */       {
/*      */ 
/* 2647 */         condition.append(v.get(i) + ")");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2652 */         condition.append(v.get(i) + ",");
/*      */       }
/*      */     }
/*      */     
/* 2656 */     return condition.toString();
/*      */   }
/*      */   
/* 2659 */   public static String getSeverityImageForAvailability(String severity) { if ("5".equals(severity)) {
/* 2660 */       return "Up#<img border=\"0\" src=\"/images/icon_availability_up.gif\"  >";
/*      */     }
/* 2662 */     if ("1".equals(severity)) {
/* 2663 */       return "Down#<img border=\"0\" src=\"/images/icon_availability_down.gif\" >";
/*      */     }
/*      */     
/*      */ 
/* 2667 */     return "Unknown#<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" >";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getSeverityImageForHealth(String severity)
/*      */   {
/* 2674 */     if (severity == null) {
/* 2675 */       return "Unknown#<img border=\"0\" src=\"/images/icon_health_unknown.gif\" >";
/*      */     }
/* 2677 */     if (severity.equals("5")) {
/* 2678 */       return "Clear#<img border=\"0\" src=\"/images/icon_health_clear.gif\"  >";
/*      */     }
/* 2680 */     if (severity.equals("4")) {
/* 2681 */       return "Warning#<img border=\"0\" src=\"/images/icon_health_warning.gif\" >";
/*      */     }
/* 2683 */     if (severity.equals("1")) {
/* 2684 */       return "Critical#<img border=\"0\" src=\"/images/icon_health_critical.gif\" >";
/*      */     }
/*      */     
/*      */ 
/* 2688 */     return "Unknown#<img border=\"0\" src=\"/images/icon_health_unknown.gif\" >";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getConditionWithQuotes(String column, Vector v)
/*      */   {
/* 2695 */     StringBuffer condition = new StringBuffer(column + " in ('");
/* 2696 */     if (v == null)
/*      */     {
/* 2698 */       return column + " in (-1) ";
/*      */     }
/* 2700 */     if ((v != null) && (v.size() == 0))
/*      */     {
/* 2702 */       return column + " in (-1) ";
/*      */     }
/*      */     
/*      */ 
/* 2706 */     for (int i = 0; i < v.size(); i++)
/*      */     {
/* 2708 */       if (i + 1 == v.size())
/*      */       {
/*      */ 
/* 2711 */         condition.append(v.get(i) + "')");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2716 */         condition.append(v.get(i) + "','");
/*      */       }
/*      */     }
/*      */     
/* 2720 */     return condition.toString();
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
/*      */   public static Map getTodaysAvailabilityForAllMonitorGroups(ArrayList monitorGroups)
/*      */   {
/* 2736 */     Map retMap = new HashMap();
/*      */     try
/*      */     {
/* 2739 */       String resourceID = "";
/* 2740 */       String period = "0";
/*      */       
/* 2742 */       for (int i = 0; i < monitorGroups.size(); i++)
/*      */       {
/* 2744 */         resourceID = (String)monitorGroups.get(i);
/*      */         
/* 2746 */         if (isHavingMonitors(resourceID))
/*      */         {
/* 2748 */           retMap.put(resourceID, getMonitorGroupAvailability(resourceID, period));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2754 */       AMLog.debug("REPORT-UTILITIES : getTodaysAvailabilityForAllMonitorGroups with selectedMonitorGroups" + e);
/* 2755 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2758 */     return retMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Map getTodaysAvailabilityForAllMonitorGroups()
/*      */   {
/* 2766 */     Map retMap = new HashMap();
/*      */     try
/*      */     {
/* 2769 */       String resourceID = "";
/* 2770 */       String period = "0";
/* 2771 */       String qry = "select RESOURCEID from AM_ManagedObject where TYPE = 'HAI'";
/*      */       
/* 2773 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       
/* 2775 */       ArrayList resID = DBUtil.getRows(qry);
/*      */       
/*      */ 
/*      */ 
/* 2779 */       for (int i = 0; i < resID.size(); i++) {
/* 2780 */         ArrayList resRow = (ArrayList)resID.get(i);
/* 2781 */         resourceID = (String)resRow.get(0);
/* 2782 */         if (isHavingMonitors(resourceID))
/*      */         {
/* 2784 */           retMap.put(resourceID, getMonitorGroupAvailability(resourceID, period));
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*      */ 
/* 2799 */       AMLog.debug("REPORT-UTILITIES : getTodaysAvailabilityForAllMonitorGroups " + e);
/* 2800 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2803 */     return retMap;
/*      */   }
/*      */   
/*      */   public boolean isHAI(String HAid)
/*      */   {
/* 2808 */     boolean reply = false;
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 2814 */       String qry = "select * from AM_ManagedObject where TYPE = 'HAI' and Resourceid='" + HAid + "'";
/*      */       
/*      */ 
/* 2817 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2818 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*      */       
/* 2820 */       if (rs.next())
/*      */       {
/* 2822 */         reply = true;
/*      */       }
/* 2824 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception e) {
/* 2827 */       AMLog.debug("REPORT-UTILITIES : ISHAI " + e);
/* 2828 */       e.printStackTrace();
/*      */     }
/* 2830 */     return reply;
/*      */   }
/*      */   
/*      */   public static boolean isHavingMonitors(String HAid) {
/* 2834 */     boolean reply = false;
/* 2835 */     ResultSet rs = null;
/* 2836 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 2880 */       String s1 = DBQueryUtil.getTopNValues("select childid from AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid='" + HAid + "' and AM_PARENTCHILDMAPPER.childid=AM_ManagedObject.Resourceid", "1");
/*      */       
/*      */ 
/* 2883 */       rs = AMConnectionPool.executeQueryStmt(s1);
/* 2884 */       boolean bool1; if (rs.next())
/*      */       {
/* 2886 */         return true;
/*      */       }
/*      */       
/*      */ 
/* 2890 */       return false;
/*      */     }
/*      */     catch (Exception e) {
/* 2893 */       AMLog.debug("REPORT-UTILITIES : ISHAI " + e);
/* 2894 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 2898 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ed) {
/* 2900 */         ed.printStackTrace();
/*      */       }
/*      */     }
/* 2903 */     return reply;
/*      */   }
/*      */   
/*      */   public static Properties getServiceAvailabilityStatsForMO(String resourceid, String StTime, String edTime, String period, String duration, String Bid)
/*      */   {
/* 2908 */     Properties pl = null;
/*      */     try {
/* 2910 */       pl = getServiceAvailabilityStatsForMO(resourceid, StTime, edTime, period, duration, "oni", null);
/*      */     } catch (Exception ef) {
/* 2912 */       ef.printStackTrace();
/*      */     }
/* 2914 */     return pl;
/*      */   }
/*      */   
/*      */   public static Properties getServiceAvailabilityStatsForMO(String resourceid, String stTime, String edTime, String period, String duration, String bid, String value)
/*      */   {
/* 2919 */     ResultSet set = null;
/* 2920 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2921 */     Properties residdisplaynamemapping = new Properties();
/* 2922 */     Properties summary = new Properties();
/* 2923 */     Properties residVsMinTime = new Properties();
/* 2924 */     Properties residVsType = new Properties();
/* 2925 */     DecimalFormat threeDForm = new DecimalFormat("#.###");
/* 2926 */     threeDForm.setMinimumFractionDigits(3);
/*      */     try
/*      */     {
/* 2929 */       long startTime = Long.parseLong(stTime);
/* 2930 */       long endTime = Long.parseLong(edTime);
/* 2931 */       long mintimeindb = getMinTimeInDB(resourceid);
/* 2932 */       long[] temptime = getTimeStamp(period);
/* 2933 */       AMLog.debug("REPORT-UTILITIES getServiceAvailabilityStatsForMO: Period : " + period + " StartTime : " + new java.util.Date(startTime) + " EndTime : " + new java.util.Date(endTime));
/*      */       
/* 2935 */       if ((period.equals("0")) || (period.equals("1")) || (period.equals("2")) || (period.equals("5")) || (period.equals("6")) || (period.equals("7")) || (period.equals("8")) || (period.equals("9")) || (period.equals("29")))
/*      */       {
/* 2937 */         if (mintimeindb > temptime[0]) {
/* 2938 */           startTime = mintimeindb;
/* 2939 */           endTime = temptime[1];
/*      */         }
/* 2941 */         else if (mintimeindb != 0L) {
/* 2942 */           startTime = temptime[0];
/* 2943 */           endTime = temptime[1];
/*      */         }
/* 2945 */       } else if (period.equals("-99")) {
/* 2946 */         startTime = Long.parseLong(stTime);
/* 2947 */         endTime = Long.parseLong(edTime);
/* 2948 */         if ((mintimeindb > startTime) && (mintimeindb < endTime))
/* 2949 */           startTime = mintimeindb;
/*      */       } else {
/*      */         long[] todayTime;
/*      */         Properties localProperties1;
/* 2953 */         if (period.equals("3"))
/*      */         {
/* 2955 */           temptime = getTimeStamp("3");
/* 2956 */           todayTime = getTimeStamp("0");
/*      */           
/* 2958 */           if (mintimeindb > todayTime[0])
/*      */           {
/* 2960 */             return summary;
/*      */           }
/*      */           
/*      */ 
/* 2964 */           if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2971 */             startTime = mintimeindb;
/* 2972 */             endTime = temptime[1];
/*      */           }
/* 2974 */           else if (mintimeindb != 0L)
/*      */           {
/* 2976 */             startTime = temptime[0];
/* 2977 */             endTime = temptime[1];
/*      */           }
/*      */         }
/*      */         else {
/* 2981 */           if (period.equals("-4"))
/*      */           {
/*      */ 
/* 2984 */             return summary;
/*      */           }
/*      */           
/* 2987 */           if (period.equals("4"))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 2992 */             if (mintimeindb > endTime)
/*      */             {
/* 2994 */               return summary;
/*      */             }
/* 2996 */             if (mintimeindb > startTime)
/*      */             {
/* 2998 */               startTime = mintimeindb;
/*      */             }
/*      */             
/*      */           }
/* 3002 */           else if (period.equals("11")) {
/* 3003 */             temptime = getTimeStamp("11");
/* 3004 */             long[] todayTime = getTimeStamp("7");
/* 3005 */             if (mintimeindb > todayTime[0]) {
/* 3006 */               return summary;
/*      */             }
/*      */             
/* 3009 */             if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */             {
/* 3011 */               startTime = mintimeindb;
/* 3012 */               endTime = temptime[1];
/*      */             }
/* 3014 */             else if (mintimeindb != 0L) {
/* 3015 */               startTime = temptime[0];
/* 3016 */               endTime = temptime[1];
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 3022 */           else if (period.equals("12")) {
/* 3023 */             temptime = getTimeStamp("12");
/* 3024 */             long[] todayTime = getTimeStamp("6");
/* 3025 */             if (mintimeindb > todayTime[0]) {
/* 3026 */               return summary;
/*      */             }
/*      */             
/* 3029 */             if ((mintimeindb > temptime[0]) && (mintimeindb < todayTime[0]))
/*      */             {
/* 3031 */               startTime = mintimeindb;
/* 3032 */               endTime = temptime[1];
/*      */             }
/* 3034 */             else if (mintimeindb != 0L) {
/* 3035 */               startTime = temptime[0];
/* 3036 */               endTime = temptime[1];
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 3041 */       ArrayList resources = new ArrayList();
/*      */       
/* 3043 */       String residquery = "select CHILDID,DISPLAYNAME,TYPE from AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/* 3044 */       AMLog.debug("REPORTS => HA-AVAILABILITY DATA QUERY getServiceAvailabilityStatsForMO parent => " + residquery);
/* 3045 */       set = AMConnectionPool.executeQueryStmt(residquery);
/*      */       
/* 3047 */       while (set.next())
/*      */       {
/* 3049 */         String resid = String.valueOf(set.getInt("CHILDID"));
/* 3050 */         residVsType.setProperty(resid, set.getString("TYPE"));
/* 3051 */         resources.add(resid);
/* 3052 */         residdisplaynamemapping.setProperty(resid, EnterpriseUtil.decodeString(set.getString("DISPLAYNAME")));
/* 3053 */         long childmintimeindb = getMinTimeInDB(resid);
/* 3054 */         residVsMinTime.setProperty(resid, String.valueOf(childmintimeindb));
/*      */       }
/*      */       
/*      */ 
/* 3058 */       AMConnectionPool.closeStatement(set);
/* 3059 */       long sttime = startTime;
/* 3060 */       Enumeration enum2 = residVsMinTime.keys();
/* 3061 */       long totalduration = 0L;
/* 3062 */       long OverallDuration = 0L;
/* 3063 */       long OverallDowntime = 0L;
/* 3064 */       long OverallCount = 0L;
/* 3065 */       long OverallUnmanagedTime = 0L;
/* 3066 */       long OverallScheduledDowntime = 0L;
/* 3067 */       Properties durationProp = new Properties();
/* 3068 */       int child = 0;
/* 3069 */       while (enum2.hasMoreElements()) {
/* 3070 */         child++;
/* 3071 */         startTime = sttime;
/* 3072 */         String resourceID = (String)enum2.nextElement();
/* 3073 */         String minTime = residVsMinTime.getProperty(resourceID);
/* 3074 */         String type = residVsType.getProperty(resourceID);
/* 3075 */         long minTimeInDB = Long.parseLong(minTime);
/* 3076 */         if (minTimeInDB > startTime) {
/* 3077 */           startTime = minTimeInDB;
/*      */         }
/* 3079 */         if (startTime > endTime) {
/* 3080 */           resources.remove(resourceID);
/*      */ 
/*      */ 
/*      */         }
/* 3084 */         else if ((type != null) && (!type.equals("HAI"))) {
/* 3085 */           String query = null;
/*      */           
/* 3087 */           if ((!"oni".equals(bid)) && (bid != null))
/*      */           {
/* 3089 */             Properties p = getWorkingTotalHours(startTime, endTime, bid);
/* 3090 */             AMLog.debug("The Property in getServiceAvailabilityStatsForMO() ===> : " + p);
/* 3091 */             totalduration = Long.parseLong(p.getProperty("totaldowntime"));
/* 3092 */             ArrayList a1 = getDowntimeDetails(resourceID, startTime, endTime, bid);
/* 3093 */             int count = 0;
/* 3094 */             long totaldowntime = 0L;
/* 3095 */             AMLog.debug("SIZE of arraylist is ===> : " + a1.size());
/* 3096 */             if (a1.size() > 0)
/*      */             {
/*      */ 
/* 3099 */               for (int i = 0; i < a1.size(); i++) {
/* 3100 */                 Properties df = (Properties)a1.get(i);
/* 3101 */                 AMLog.debug("df ==== getServiceAvailabilityStatsForMO ======> : " + df);
/* 3102 */                 totaldowntime += Long.parseLong(df.getProperty("downtimeinmillis"));
/* 3103 */                 count++;
/*      */               }
/* 3105 */               AMLog.debug("The Total Downtime ===> : 1111" + totaldowntime + " and downcount : " + OverallCount);
/*      */             }
/*      */             else {
/* 3108 */               count = 0;
/* 3109 */               totaldowntime = 0L;
/*      */             }
/* 3111 */             OverallDuration += totalduration;
/* 3112 */             OverallDowntime += totaldowntime;
/*      */             
/* 3114 */             totaldowntime = OverallDowntime;
/* 3115 */             if (totaldowntime == 0L) {
/* 3116 */               OverallCount = 0L;
/*      */             } else {
/* 3118 */               OverallCount += count;
/*      */             }
/* 3120 */             AMLog.debug("The Total Downtime ===> : " + totaldowntime + " and the downcount : " + OverallCount + " out of the loop");
/*      */           } else {
/* 3122 */             query = "select RESID,DISPLAYNAME,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,AM_MO_DowntimeData.TYPE from AM_MO_DowntimeData,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_MO_DowntimeData.RESID and RESID=" + resourceID + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,AM_MO_DowntimeData.TYPE,DISPLAYNAME";
/* 3123 */             if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 3124 */               query = "select RESID,DISPLAYNAME,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,AM_MO_DowntimeData.TYPE from AM_MO_DowntimeData,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_MO_DowntimeData.RESID and RESID=" + resourceID + " and AM_MO_DowntimeData.TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,AM_MO_DowntimeData.TYPE,DISPLAYNAME";
/*      */             }
/* 3126 */             totalduration = endTime - startTime;
/*      */             
/*      */ 
/* 3129 */             AMLog.debug("REPORTS => HA-AVAILABILITY DATA QUERY getServiceAvailabilityStatsForMO=> " + query);
/* 3130 */             set = AMConnectionPool.executeQueryStmt(query);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 3135 */             Properties p = null;
/*      */             
/* 3137 */             OverallDuration += totalduration;
/* 3138 */             durationProp.setProperty(resourceID, String.valueOf(totalduration));
/*      */             
/* 3140 */             while (set.next())
/*      */             {
/*      */ 
/* 3143 */               int resourceid1 = set.getInt("RESID");
/* 3144 */               if (resources.contains(String.valueOf(resourceid1)))
/* 3145 */                 resources.remove(String.valueOf(resourceid1));
/* 3146 */               long totaldowntime = 0L;
/*      */               
/* 3148 */               long unmanagedtime = 0L;
/* 3149 */               long scheduledtime = 0L;
/* 3150 */               if (set.getInt("TYPE") == 1)
/*      */               {
/* 3152 */                 totaldowntime += set.getLong("TotalDownTime");
/*      */               }
/* 3154 */               else if (set.getInt("TYPE") == 2)
/*      */               {
/* 3156 */                 unmanagedtime += set.getLong("TotalDownTime");
/*      */               }
/*      */               else
/*      */               {
/* 3160 */                 scheduledtime += set.getLong("TotalDownTime");
/*      */               }
/*      */               
/* 3163 */               OverallDowntime += totaldowntime;
/* 3164 */               OverallUnmanagedTime += unmanagedtime;
/* 3165 */               OverallScheduledDowntime += scheduledtime;
/* 3166 */               OverallCount += set.getLong("DownCount");
/*      */             }
/*      */             
/*      */ 
/* 3170 */             AMConnectionPool.closeStatement(set);
/*      */           }
/*      */         }
/*      */         else {
/* 3174 */           Properties subgroupstats = getServiceAvailabilityStatsForMO(resourceID, String.valueOf(startTime), String.valueOf(endTime), period, duration, bid, value);
/* 3175 */           if (subgroupstats.getProperty("totaltime") != null) {
/* 3176 */             OverallDuration += Long.parseLong(subgroupstats.getProperty("totaltime"));
/*      */           }
/* 3178 */           if (subgroupstats.getProperty("OverallDowntime") != null) {
/* 3179 */             OverallDowntime += Long.parseLong(subgroupstats.getProperty("OverallDowntime"));
/*      */           }
/* 3181 */           if (subgroupstats.getProperty("OverallUnmanagedTime") != null) {
/* 3182 */             OverallUnmanagedTime += Long.parseLong(subgroupstats.getProperty("OverallUnmanagedTime"));
/*      */           }
/* 3184 */           if (subgroupstats.getProperty("OverallScheduledDowntime") != null) {
/* 3185 */             OverallScheduledDowntime += Long.parseLong(subgroupstats.getProperty("OverallScheduledDowntime"));
/*      */           }
/* 3187 */           if (subgroupstats.getProperty("OverallCount") != null) {
/* 3188 */             OverallCount += Long.parseLong(subgroupstats.getProperty("OverallCount"));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3195 */       if (OverallCount == 0L) {
/* 3196 */         OverallCount = 1L;
/*      */       }
/*      */       
/* 3199 */       if (child == 0) {
/* 3200 */         child = 1;
/*      */       }
/*      */       
/* 3203 */       float ServicesDownPercent = (float)OverallDowntime / (float)OverallDuration * 100.0F;
/* 3204 */       float ServicesUnmanagedPercent = (float)OverallUnmanagedTime / (float)OverallDuration * 100.0F;
/* 3205 */       float ServicesScheduledPercent = (float)OverallScheduledDowntime / (float)OverallDuration * 100.0F;
/* 3206 */       long overalluptime = (OverallDuration - (OverallDowntime + OverallUnmanagedTime + OverallScheduledDowntime)) / child;
/* 3207 */       long overallmttr = OverallDowntime / child / OverallCount;
/* 3208 */       long overallmtbf = overalluptime / child / OverallCount;
/* 3209 */       float ServicesUpPercent = 100.0F - (ServicesDownPercent + ServicesUnmanagedPercent + ServicesScheduledPercent);
/*      */       
/*      */ 
/* 3212 */       summary.setProperty("totaltime", String.valueOf(OverallDuration / child));
/* 3213 */       summary.setProperty("OverallCount", String.valueOf(OverallCount));
/* 3214 */       if (OverallDuration == 0L) {
/* 3215 */         summary.setProperty("available", "NA");
/*      */       }
/* 3217 */       else if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */       {
/* 3219 */         summary.setProperty("available", threeDForm.format(ServicesUpPercent));
/*      */       }
/* 3221 */       else if (ServicesUpPercent >= 99.995D)
/*      */       {
/* 3223 */         summary.setProperty("available", fourDForm.format(ServicesUpPercent));
/*      */       }
/*      */       else {
/* 3226 */         summary.setProperty("available", String.valueOf(Math.round(ServicesUpPercent * 100.0F) / 100.0F));
/*      */       }
/*      */       
/* 3229 */       if (ServicesDownPercent < 0.005D)
/*      */       {
/* 3231 */         summary.setProperty("unavailable", fourDForm.format(ServicesDownPercent));
/*      */       }
/*      */       else
/*      */       {
/* 3235 */         summary.setProperty("unavailable", String.valueOf(Math.round(ServicesDownPercent * 100.0F) / 100.0F));
/*      */       }
/* 3237 */       summary.setProperty("ServicesUnMgPercent", String.valueOf(Math.round(ServicesUnmanagedPercent * 100.0F) / 100.0F));
/* 3238 */       summary.setProperty("ServicesSchPercent", String.valueOf(Math.round(ServicesScheduledPercent * 100.0F) / 100.0F));
/* 3239 */       summary.setProperty("uptime", format(overalluptime));
/* 3240 */       summary.setProperty("downtime", format(Long.parseLong(String.valueOf(OverallDowntime)) / child));
/* 3241 */       summary.setProperty("MTTR", format(overallmttr));
/* 3242 */       summary.setProperty("MTBF", format(overallmtbf));
/* 3243 */       summary.setProperty("mttr", format(overallmttr));
/* 3244 */       summary.setProperty("mtbf", format(overallmtbf));
/* 3245 */       summary.setProperty("totaldowntime", format(Long.parseLong(String.valueOf(OverallDowntime / child))));
/* 3246 */       summary.setProperty("unmanagedtime", format(Long.parseLong(String.valueOf(OverallUnmanagedTime / child))));
/* 3247 */       summary.setProperty("scheduledtime", format(Long.parseLong(String.valueOf(OverallScheduledDowntime / child))));
/* 3248 */       summary.setProperty("Mintime", String.valueOf(startTime));
/* 3249 */       summary.setProperty("Maxtime", String.valueOf(endTime));
/* 3250 */       summary.setProperty("servicesup", String.valueOf(ServicesUpPercent));
/* 3251 */       summary.setProperty("overalluptime", String.valueOf(overalluptime));
/* 3252 */       summary.setProperty("OverallDowntime", String.valueOf(OverallDowntime / child));
/* 3253 */       summary.setProperty("OverallUnmanagedTime", String.valueOf(OverallUnmanagedTime / child));
/* 3254 */       summary.setProperty("OverallScheduledDowntime", String.valueOf(OverallScheduledDowntime / child));
/* 3255 */       summary.setProperty("overallmtbf", String.valueOf(overallmtbf));
/* 3256 */       summary.setProperty("overallmttr", String.valueOf(overallmttr));
/* 3257 */       summary.setProperty("ServicesDownPercent", String.valueOf(ServicesDownPercent));
/* 3258 */       if (OverallDowntime == 0L) {
/* 3259 */         summary.setProperty("countDown", String.valueOf("0"));
/*      */       }
/*      */       else {
/* 3262 */         summary.setProperty("countDown", String.valueOf(OverallCount));
/*      */       }
/* 3264 */       summary.setProperty("TotDown", String.valueOf(OverallDowntime / child));
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 3268 */       exp.printStackTrace();
/* 3269 */       AMLog.fatal("Exception in getAvailabilityStatsForMO", exp);
/*      */     }
/*      */     finally {
/* 3272 */       closeResultSet(set);
/*      */     }
/* 3274 */     AMLog.debug("============> getServiceAvailabilityStatsForMO(" + resourceid + ") ==========> : " + summary);
/* 3275 */     return summary;
/*      */   }
/*      */   
/*      */   public static Properties getMonitorGroupAvailability(String haid, String period)
/*      */   {
/* 3280 */     Properties pl = null;
/*      */     try {
/* 3282 */       pl = getMonitorGroupAvailability(haid, period, 0L, 0L, "oni");
/*      */     } catch (Exception ef) {
/* 3284 */       ef.printStackTrace();
/*      */     }
/* 3286 */     return pl;
/*      */   }
/*      */   
/* 3289 */   public static Properties getMonitorGroupAvailability(String haid, String period, long stTime, long eTime) { Properties pl = null;
/*      */     try {
/* 3291 */       pl = getMonitorGroupAvailability(haid, period, stTime, eTime, "oni");
/*      */     } catch (Exception ef) {
/* 3293 */       ef.printStackTrace();
/*      */     }
/* 3295 */     return pl;
/*      */   }
/*      */   
/*      */   public static Properties getMonitorGroupAvailability(String haid, String period, long stTime, long eTime, String Bid) {
/* 3299 */     Properties pl = null;
/*      */     try {
/* 3301 */       pl = getMonitorGroupAvailability(haid, period, stTime, eTime, Bid, null);
/*      */     } catch (Exception ef) {
/* 3303 */       ef.printStackTrace();
/*      */     }
/* 3305 */     return pl;
/*      */   }
/*      */   
/*      */   public static Properties getMonitorGroupAvailability(String haid, String period, long stTime, long eTime, String bid, String value) {
/* 3309 */     return getMonitorGroupAvailability(haid, period, stTime, eTime, bid, value, false);
/*      */   }
/*      */   
/*      */   public static Properties getMonitorGroupAvailability(String haid, String period, long stTime, long eTime, String bid, String value, boolean isServiceAvailability)
/*      */   {
/* 3314 */     ResultSet set = null;
/*      */     
/* 3316 */     Properties props = new Properties();
/* 3317 */     DecimalFormat threeDForm = new DecimalFormat("#.###");
/* 3318 */     threeDForm.setMinimumFractionDigits(3);
/* 3319 */     long startTime = 0L;
/* 3320 */     long endTime = 0L;
/* 3321 */     float ServicesUpPercent = 0.0F;
/* 3322 */     long overallmtbf = 0L;
/* 3323 */     long overallmttr = 0L;
/* 3324 */     long overalluptime = 0L;
/* 3325 */     long OverallDowntime = 0L;
/* 3326 */     long TotalUnmanagedTime = 1L;
/* 3327 */     long TotalScheduleTime = 1L;
/* 3328 */     float UnmanagedPercent = 0.0F;
/* 3329 */     float MaintenancePercent = 0.0F;
/* 3330 */     float ServicesDownPercent = 0.0F;
/* 3331 */     String available = "100";
/* 3332 */     int downCount = 0;
/* 3333 */     long totdowntime = 0L;
/* 3334 */     boolean nodata = false;
/*      */     try
/*      */     {
/* 3337 */       long mintimeindb1 = getMinTimeInDB(haid);
/* 3338 */       DBUtil db = new DBUtil();
/* 3339 */       String DBVal = db.getGlobalConfigValueForMGAvailability();
/* 3340 */       if ("-99".equals(period)) {
/* 3341 */         startTime = stTime;
/* 3342 */         endTime = eTime;
/* 3343 */         if ((mintimeindb1 > startTime) && (mintimeindb1 < endTime)) {
/* 3344 */           startTime = mintimeindb1;
/*      */         }
/*      */       }
/*      */       else {
/* 3348 */         long[] temptime = getTimeStamp(period);
/* 3349 */         if ((stTime != 0L) && (eTime != 0L))
/*      */         {
/* 3351 */           startTime = stTime;
/* 3352 */           endTime = eTime;
/*      */         } else {
/* 3354 */           startTime = temptime[0];
/* 3355 */           endTime = temptime[1];
/*      */         }
/*      */       }
/*      */       
/* 3359 */       if ((startTime < mintimeindb1) && (endTime < mintimeindb1)) {
/* 3360 */         available = FormatUtil.getString("am.webclient.viewaction.na");
/* 3361 */         nodata = true;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3366 */       if (haid != null) {
/* 3367 */         Properties p3 = new Properties();
/* 3368 */         if (("true".equalsIgnoreCase(DBVal)) || (isServiceAvailability)) {
/* 3369 */           return getServiceAvailabilityStatsForMO(haid, String.valueOf(startTime), String.valueOf(endTime), period, "", bid, value);
/*      */         }
/* 3371 */         if (!nodata) {
/* 3372 */           if ((!"oni".equals(bid)) && (bid != null))
/*      */           {
/* 3374 */             Properties p = getWorkingTotalHours(startTime, endTime, bid);
/*      */             
/* 3376 */             long totalduration = Long.parseLong(p.getProperty("totaldowntime"));
/*      */             
/* 3378 */             ArrayList a1 = getDowntimeDetails(haid, startTime, endTime, bid);
/*      */             
/* 3380 */             if (a1.size() > 0)
/*      */             {
/* 3382 */               long totaldowntime = 0L;
/* 3383 */               for (int i = 0; i < a1.size(); i++) {
/* 3384 */                 Properties df = (Properties)a1.get(i);
/* 3385 */                 totaldowntime += Long.parseLong(df.getProperty("downtimeinmillis"));
/* 3386 */                 downCount++;
/*      */               }
/* 3388 */               long uptime = totalduration - totaldowntime;
/* 3389 */               float upPercent = (float)uptime / (float)totalduration * 100.0F;
/* 3390 */               float downPercent = (float)totaldowntime / (float)totalduration * 100.0F;
/* 3391 */               ServicesUpPercent = upPercent;
/* 3392 */               ServicesDownPercent = downPercent;
/* 3393 */               overallmtbf = Long.parseLong("0");
/* 3394 */               overallmttr = Long.parseLong("0");
/* 3395 */               overalluptime = uptime;
/* 3396 */               OverallDowntime = totaldowntime;
/* 3397 */               if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */               {
/*      */ 
/* 3400 */                 available = threeDForm.format(ServicesUpPercent);
/*      */               }
/* 3402 */               else if (ServicesUpPercent > 99.995D)
/*      */               {
/* 3404 */                 available = fourDForm.format(ServicesUpPercent);
/*      */               }
/*      */               else {
/* 3407 */                 available = String.valueOf(Math.round(ServicesUpPercent * 100.0F) / 100.0F);
/*      */               }
/* 3409 */               totdowntime = totaldowntime;
/* 3410 */               downCount = downCount;
/*      */             }
/*      */             else {
/* 3413 */               ServicesUpPercent = Float.parseFloat("100");
/* 3414 */               ServicesDownPercent = Float.parseFloat("0");
/* 3415 */               overallmtbf = Long.parseLong("0");
/* 3416 */               overallmttr = Long.parseLong("0");
/* 3417 */               overalluptime = Long.parseLong("0");
/* 3418 */               OverallDowntime = Long.parseLong("0");
/* 3419 */               if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */               {
/* 3421 */                 available = threeDForm.format(ServicesUpPercent);
/*      */               } else {
/* 3423 */                 available = String.valueOf(Math.round(ServicesUpPercent * 100.0F) / 100.0F);
/*      */               }
/* 3425 */               totdowntime = Long.parseLong("0");
/* 3426 */               downCount = 0;
/*      */             }
/*      */           }
/*      */           else {
/* 3430 */             p3 = getAvailabilityStatsForMO(haid, String.valueOf(startTime), String.valueOf(endTime), period, "", bid, value);
/*      */             
/* 3432 */             ServicesUpPercent = Float.parseFloat(p3.getProperty("servicesup"));
/* 3433 */             ServicesDownPercent = Float.parseFloat(p3.getProperty("ServicesDownPercent"));
/* 3434 */             overallmtbf = Long.parseLong(p3.getProperty("overallmtbf"));
/* 3435 */             overallmttr = Long.parseLong(p3.getProperty("overallmttr"));
/* 3436 */             overalluptime = Long.parseLong(p3.getProperty("overalluptime"));
/* 3437 */             OverallDowntime = Long.parseLong(p3.getProperty("OverallDowntime"));
/* 3438 */             UnmanagedPercent = Float.parseFloat(p3.getProperty("OverallUnmanagedTime"));
/* 3439 */             MaintenancePercent = Float.parseFloat(p3.getProperty("OverallScheduledDowntime"));
/* 3440 */             TotalUnmanagedTime = Long.parseLong(p3.getProperty("TotalUnmanagedTime"));
/* 3441 */             TotalScheduleTime = Long.parseLong(p3.getProperty("TotalScheduledTime"));
/*      */             
/* 3443 */             if ("trendDowntimeReport".equalsIgnoreCase(value))
/*      */             {
/* 3445 */               available = threeDForm.format(ServicesUpPercent);
/*      */             }
/* 3447 */             else if (ServicesUpPercent > 99.995D)
/*      */             {
/* 3449 */               available = fourDForm.format(ServicesUpPercent);
/*      */             }
/*      */             else {
/* 3452 */               available = String.valueOf(Math.round(ServicesUpPercent * 100.0F) / 100.0F);
/*      */             }
/* 3454 */             totdowntime = Long.parseLong(p3.getProperty("TotDown"));
/* 3455 */             downCount = Integer.parseInt(p3.getProperty("countDown"));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3462 */       props.setProperty("available", available);
/* 3463 */       if (ServicesDownPercent < 0.005D)
/*      */       {
/* 3465 */         props.setProperty("unavailable", fourDForm.format(ServicesDownPercent));
/*      */       }
/*      */       else
/*      */       {
/* 3469 */         props.setProperty("unavailable", String.valueOf(Math.round(ServicesDownPercent * 100.0F) / 100.0F));
/*      */       }
/* 3471 */       props.setProperty("uptime", format(overalluptime));
/* 3472 */       props.setProperty("downtime", format(Long.parseLong(String.valueOf(OverallDowntime))));
/* 3473 */       props.setProperty("MTTR", format(overallmttr));
/* 3474 */       props.setProperty("MTBF", format(overallmtbf));
/* 3475 */       props.setProperty("mttr", format(overallmttr));
/* 3476 */       props.setProperty("mtbf", format(overallmtbf));
/* 3477 */       props.setProperty("totaldowntime", format(Long.parseLong(String.valueOf(OverallDowntime))));
/* 3478 */       props.setProperty("ServicesUnMgPercent", String.valueOf(UnmanagedPercent));
/* 3479 */       props.setProperty("ServicesSchPercent", String.valueOf(MaintenancePercent));
/* 3480 */       props.setProperty("unmanagedtime", format(TotalUnmanagedTime));
/* 3481 */       props.setProperty("scheduledtime", format(TotalScheduleTime));
/* 3482 */       props.setProperty("countDown", String.valueOf(downCount));
/* 3483 */       props.setProperty("TotDown", String.valueOf(totdowntime));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 3491 */         if (set != null)
/* 3492 */           AMConnectionPool.closeStatement(set);
/*      */       } catch (Exception ed) {
/* 3494 */         ed.printStackTrace();
/*      */       }
/*      */       
/* 3497 */       AMLog.debug("============> getMonitorGroupAvailability(" + haid + ") ==========> : getMGAvail " + props);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3487 */       ex.printStackTrace();
/* 3488 */       AMLog.fatal("Exception in getMonitorGroupAvailability", ex);
/*      */     } finally {
/*      */       try {
/* 3491 */         if (set != null)
/* 3492 */           AMConnectionPool.closeStatement(set);
/*      */       } catch (Exception ed) {
/* 3494 */         ed.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 3498 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */   public static final ArrayList getTabularData(String query, boolean needTableHeading)
/*      */     throws Exception
/*      */   {
/* 3505 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3506 */     ResultSet set = null;
/* 3507 */     ArrayList data = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3512 */       if ((query == null) || (query.equals("")) || (query.equalsIgnoreCase("NULL")))
/* 3513 */         throw new Exception("Specify the query");
/* 3514 */       set = AMConnectionPool.executeQueryStmt(query);
/* 3515 */       ResultSetMetaData metadata = set.getMetaData();
/* 3516 */       int columncount = metadata.getColumnCount();
/* 3517 */       ArrayList columnNames = new ArrayList();
/* 3518 */       int[] columnTypes = new int[columncount];
/*      */       
/* 3520 */       for (int i = 1; i <= columncount; i++)
/*      */       {
/* 3522 */         columnNames.add(metadata.getColumnName(i));
/* 3523 */         columnTypes[(i - 1)] = metadata.getColumnType(i);
/*      */       }
/* 3525 */       if (needTableHeading)
/* 3526 */         data.add(columnNames);
/* 3527 */       while (set.next())
/*      */       {
/* 3529 */         ArrayList row = new ArrayList();
/* 3530 */         for (int i = 1; i <= columncount; i++)
/*      */         {
/* 3532 */           int columntype = columnTypes[(i - 1)];
/* 3533 */           if (columntype == 4)
/*      */           {
/* 3535 */             int value = set.getInt(i);
/* 3536 */             row.add(new Integer(value));
/*      */           }
/* 3538 */           else if (columntype == -5)
/*      */           {
/* 3540 */             long value = set.getLong(i);
/* 3541 */             row.add(new Long(value));
/*      */           }
/* 3543 */           else if (columntype == 8)
/*      */           {
/* 3545 */             double value = set.getDouble(i);
/* 3546 */             row.add(String.valueOf((float)Math.round(value * 1000.0D) / 1000.0F));
/* 3547 */           } else if (columntype == 6)
/*      */           {
/* 3549 */             float value = set.getFloat(i);
/* 3550 */             row.add(String.valueOf(Math.round(value * 1000.0F) / 1000.0F));
/*      */           }
/* 3552 */           else if (columntype == 12)
/*      */           {
/* 3554 */             String name = set.getString(i);
/* 3555 */             row.add(name);
/*      */           }
/*      */           else
/*      */           {
/* 3559 */             String name = set.getString(i);
/* 3560 */             row.add(name);
/*      */           }
/*      */         }
/* 3563 */         data.add(row);
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 3568 */       AMLog.fatal("Reports : Exception in getTabularData ", exp);
/* 3569 */       throw exp;
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/* 3573 */       t.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3577 */       closeResultSet(set);
/*      */     }
/* 3579 */     return data;
/*      */   }
/*      */   
/*      */ 
/* 3583 */   public static HashMap getResourceIdInOrder(String query, String val, String tablename) { return getResourceIdInOrder(query, val, tablename, 10); }
/*      */   
/*      */   public static HashMap getResourceIdInOrder(String query, String val, String tablename, int limit) {
/* 3586 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3587 */     ResultSet set = null;
/* 3588 */     Vector data = new Vector();
/* 3589 */     Vector attdata = new Vector();
/* 3590 */     HashMap props = new HashMap();
/*      */     try
/*      */     {
/* 3593 */       if ((tablename == null) || (tablename.equals("")) || (tablename.equalsIgnoreCase("NULL"))) {
/* 3594 */         tablename = "AM_MinMaxAvgData";
/*      */       }
/* 3596 */       if ((query == null) || (query.equals("")) || (query.equalsIgnoreCase("NULL")))
/* 3597 */         throw new Exception("Specify the query");
/* 3598 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*      */ 
/* 3601 */       int i = 0;
/* 3602 */       if ("rawvalue".equals(val)) {
/* 3603 */         while (set.next()) {
/* 3604 */           if (i < limit)
/*      */           {
/*      */ 
/* 3607 */             data.add(set.getString("RESOURCEID"));
/* 3608 */             attdata.add(set.getString("ATTRIBUTEID"));
/*      */           }
/* 3610 */           i++;
/*      */         }
/*      */       }
/*      */       
/* 3614 */       while (set.next()) {
/* 3615 */         if (i < limit)
/*      */         {
/*      */ 
/* 3618 */           data.add(set.getString("RESOURCEID"));
/* 3619 */           attdata.add(set.getString("ATTRIBUTEID"));
/*      */         }
/* 3621 */         i++;
/*      */       }
/*      */       
/* 3624 */       props.put("RESOUREID", data);
/* 3625 */       props.put("ATTRIBUTEID", attdata);
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 3629 */       AMLog.fatal("Reports : Exception in getTabularData ", exp);
/* 3630 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3634 */       closeResultSet(set);
/*      */     }
/* 3636 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getDisplayName(int resid)
/*      */   {
/* 3642 */     ResultSet set = null;
/* 3643 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3644 */     Hashtable display = new Hashtable();
/*      */     try
/*      */     {
/* 3647 */       String query = "SELECT AM_ManagedObject.DISPLAYNAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.CHILDID=" + resid;
/*      */       
/* 3649 */       set = AMConnectionPool.executeQueryStmt(query);
/* 3650 */       String displayname = "";
/* 3651 */       if (set.next())
/*      */       {
/* 3653 */         displayname = set.getString("DISPLAYNAME");
/* 3654 */         displayname = EnterpriseUtil.decodeString(displayname);
/*      */       }
/*      */       
/* 3657 */       display.put(String.valueOf(resid), displayname);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3661 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3665 */       closeResultSet(set);
/*      */     }
/* 3667 */     return display;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Vector getChildIDs(int resid)
/*      */   {
/* 3673 */     ResultSet set = null;
/* 3674 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3675 */     Vector display = new Vector();
/*      */     try
/*      */     {
/* 3678 */       String query = "SELECT AM_ManagedObject.RESOURCEID as RESID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=" + resid;
/*      */       
/* 3680 */       set = AMConnectionPool.executeQueryStmt(query);
/* 3681 */       while (set.next())
/*      */       {
/* 3683 */         display.add(set.getString("RESID"));
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3689 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3693 */       closeResultSet(set);
/*      */     }
/*      */     
/* 3696 */     return display;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void getChildIDs(Vector resultVector, int resourceid)
/*      */   {
/* 3703 */     ResultSet set = null;
/*      */     try
/*      */     {
/* 3706 */       String query = "SELECT AM_ManagedObject.RESOURCEID as RESID,TYPE FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/* 3707 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 3709 */       while (set.next())
/*      */       {
/* 3711 */         String resType = set.getString("TYPE");
/* 3712 */         String resId = set.getString("RESID");
/* 3713 */         if (!resultVector.contains(resId))
/*      */         {
/* 3715 */           resultVector.add(resId);
/*      */         }
/* 3717 */         if (Constants.resourceTypesEUM.contains(resType))
/*      */         {
/* 3719 */           getChildIDs(resultVector, Integer.parseInt(resId));
/*      */         }
/*      */       }
/* 3722 */       if (!resultVector.contains(Integer.valueOf(resourceid)))
/*      */       {
/* 3724 */         resultVector.add(String.valueOf(resourceid));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3729 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3733 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*      */   }
/*      */   
/*      */   public static HashMap getAllMonitorsInGroup(ArrayList haids) {
/* 3738 */     return getAllMonitorsInGroup(haids, false);
/*      */   }
/*      */   
/*      */   public static HashMap getAllMonitorsInGroup(ArrayList haids, boolean excludesSubGroup)
/*      */   {
/* 3743 */     HashMap allData = new HashMap();
/* 3744 */     if ((haids == null) || (haids.size() == 0)) {
/* 3745 */       return allData;
/*      */     }
/* 3747 */     if ((DBQueryUtil.isMssql()) || (DBQueryUtil.isPgsql())) {
/* 3748 */       allData = getAllMonitorsInGroup_1(haids, excludesSubGroup);
/*      */     } else {
/* 3750 */       allData = getAllMonitorsInGroup_2(haids, excludesSubGroup);
/*      */     }
/* 3752 */     return allData;
/*      */   }
/*      */   
/*      */   private static HashMap getAllMonitorsInGroup_1(ArrayList haids, boolean excludesSubGroup)
/*      */   {
/* 3757 */     HashMap allData = new HashMap();
/* 3758 */     ResultSet rs = null;
/*      */     try {
/* 3760 */       for (int k = 0; k < haids.size(); k++) {
/* 3761 */         String resourceid = (String)haids.get(k);
/* 3762 */         StringBuilder builder = new StringBuilder();
/*      */         
/* 3764 */         ArrayList data = new ArrayList();
/* 3765 */         ArrayList subgroupsId = new ArrayList();
/* 3766 */         ArrayList parnetMgMonitors = new ArrayList();
/* 3767 */         HashMap resultset = new HashMap();
/* 3768 */         if (DBQueryUtil.isMssql()) {
/* 3769 */           builder.append("WITH CHILDRESOURCES_CTE(PARENTRESOURCEID,PARENTTYPE,PARENTRESOURCENAME,CHILDRESOURCEID,CHILDTYPE,CHILDRESOURCENAME,DISPLAYNAME,TREENAME) AS (");
/*      */         } else {
/* 3771 */           builder.append("WITH RECURSIVE CHILDRESOURCES_CTE(PARENTRESOURCEID,PARENTTYPE,PARENTRESOURCENAME,CHILDRESOURCEID,CHILDTYPE,CHILDRESOURCENAME,DISPLAYNAME,TREENAME) AS (");
/*      */         }
/* 3773 */         builder.append("SELECT DISTINCT AM_ManagedObject.RESOURCEID AS PARENTRESOURCEID, AM_ManagedObject.TYPE AS PARENTTYPE,AM_ManagedObject.RESOURCENAME AS PARENTRESOURCENAME,AM_ManagedObject.RESOURCEID AS CHILDRESOURCEID, AM_ManagedObject.TYPE AS CHILDTYPE,AM_ManagedObject.RESOURCENAME AS CHILDRESOURCENAME,DISPLAYNAME,RESOURCENAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER WHERE AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID AND AM_ManagedObject.TYPE='HAI' AND AM_PARENTCHILDMAPPER.PARENTID=").append(resourceid);
/* 3774 */         builder.append(" UNION ALL ");
/*      */         
/*      */ 
/* 3777 */         if (DBQueryUtil.isMssql()) {
/* 3778 */           builder.append("SELECT parentmo.RESOURCEID AS RESOURCEID,parentmo.TYPE AS TYPE,parentmo.RESOURCENAME AS RESOURCENAME,childmo.RESOURCEID AS RESOURCEID1,childmo.TYPE AS TYPE1,childmo.RESOURCENAME aS RESOURCENAME1,childmo.DISPLAYNAME,CAST(((CHILDRESOURCES_CTE.TREENAME + ('->' + childmo.RESOURCENAME))) AS VARCHAR(2000)) AS TREENAME1 FROM AM_PARENTCHILDMAPPER INNER JOIN AM_ManagedObject parentmo on AM_PARENTCHILDMAPPER.PARENTID = parentmo.RESOURCEID INNER JOIN AM_ManagedObject childmo on AM_PARENTCHILDMAPPER.CHILDID= childmo.RESOURCEID INNER JOIN CHILDRESOURCES_CTE ON CHILDRESOURCES_CTE.CHILDRESOURCEID = AM_PARENTCHILDMAPPER.PARENTID where CHILDRESOURCES_CTE.CHILDTYPE='HAI')");
/*      */         } else {
/* 3780 */           builder.append("SELECT parentmo.RESOURCEID AS RESOURCEID,parentmo.TYPE AS TYPE,parentmo.RESOURCENAME AS RESOURCENAME,childmo.RESOURCEID AS RESOURCEID1,childmo.TYPE AS TYPE1,childmo.RESOURCENAME aS RESOURCENAME1,childmo.DISPLAYNAME,CAST((CONCAT(CHILDRESOURCES_CTE.TREENAME,CONCAT('->',childmo.RESOURCENAME))) AS VARCHAR(2000)) AS TREENAME1 FROM AM_PARENTCHILDMAPPER INNER JOIN AM_ManagedObject parentmo on AM_PARENTCHILDMAPPER.PARENTID = parentmo.RESOURCEID INNER JOIN AM_ManagedObject childmo on AM_PARENTCHILDMAPPER.CHILDID= childmo.RESOURCEID INNER JOIN CHILDRESOURCES_CTE ON CHILDRESOURCES_CTE.CHILDRESOURCEID = AM_PARENTCHILDMAPPER.PARENTID where CHILDRESOURCES_CTE.CHILDTYPE='HAI')");
/*      */         }
/* 3782 */         builder.append(" SELECT * FROM CHILDRESOURCES_CTE ORDER BY TREENAME ASC");
/* 3783 */         StartUtil.printStr("ReportUtilities.getAllMonitorsInGroup_1:Query ===> " + builder);
/* 3784 */         rs = AMConnectionPool.executeQueryStmt(builder.toString());
/* 3785 */         while (rs.next()) {
/* 3786 */           String parentResId = rs.getString(1);
/* 3787 */           String parentResType = rs.getString(2);
/* 3788 */           String parentResName = rs.getString(3);
/* 3789 */           String childResId = rs.getString(4);
/* 3790 */           String childResType = rs.getString(5);
/* 3791 */           String childResName = rs.getString(6);
/* 3792 */           String childDispName = rs.getString(7);
/* 3793 */           String treeName = rs.getString(8);
/*      */           
/*      */ 
/* 3796 */           ArrayList al = (ArrayList)resultset.get(parentResId);
/* 3797 */           if (al == null) {
/* 3798 */             al = new ArrayList();
/* 3799 */             resultset.put(parentResId, al);
/*      */           }
/* 3801 */           ArrayList eachrow = new ArrayList();
/* 3802 */           eachrow.add(parentResId);
/* 3803 */           eachrow.add(parentResType);
/* 3804 */           eachrow.add(parentResName);
/* 3805 */           eachrow.add(childResId);
/* 3806 */           eachrow.add(childResType);
/* 3807 */           eachrow.add(childResName);
/* 3808 */           eachrow.add(childDispName);
/* 3809 */           eachrow.add(treeName);
/* 3810 */           al.add(eachrow);
/*      */           
/* 3812 */           if ((childResType.equals("HAI")) && (Integer.parseInt(parentResId) != Integer.parseInt(childResId))) {
/* 3813 */             ArrayList al1 = (ArrayList)resultset.get(childResId);
/* 3814 */             if (al1 == null) {
/* 3815 */               al1 = new ArrayList();
/* 3816 */               resultset.put(childResId, al1);
/*      */             }
/* 3818 */             al1.add(eachrow);
/*      */           }
/*      */           
/* 3821 */           if ((childResType.equals("HAI")) && (Integer.parseInt(resourceid) != Integer.parseInt(childResId)))
/*      */           {
/* 3823 */             subgroupsId.add(childResId);
/*      */           }
/*      */           
/* 3826 */           if (Integer.parseInt(resourceid) == Integer.parseInt(parentResId))
/* 3827 */             if ((!childResType.equals("HAI")) || (Integer.parseInt(resourceid) == Integer.parseInt(childResId)))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/* 3832 */               ArrayList al1 = new ArrayList();
/* 3833 */               al1.add(childResId);
/* 3834 */               al1.add(childResName);
/* 3835 */               al1.add(childDispName);
/* 3836 */               al1.add(childResType);
/* 3837 */               parnetMgMonitors.add(al1);
/*      */             }
/*      */         }
/* 3840 */         data.add(parnetMgMonitors);
/* 3841 */         if (!excludesSubGroup) {
/* 3842 */           LinkedHashMap map = getAllMonitorsInSubGroup_1(subgroupsId, resultset);
/* 3843 */           Iterator itr = map.keySet().iterator();
/* 3844 */           while (itr.hasNext()) {
/* 3845 */             String key = (String)itr.next();
/* 3846 */             if (!resourceid.equals(key)) {
/* 3847 */               data.add((ArrayList)map.get(key));
/*      */             }
/*      */           }
/*      */         }
/* 3851 */         allData.put(resourceid, data);
/*      */       }
/*      */     } catch (Exception ex) {
/* 3854 */       ex.printStackTrace();
/*      */     } finally {
/* 3856 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 3858 */     return allData;
/*      */   }
/*      */   
/*      */   private static HashMap getAllMonitorsInGroup_2(ArrayList haids, boolean excludesSubGroup) {
/* 3862 */     HashMap allData = new HashMap();
/*      */     try {
/* 3864 */       if (haids.size() > 0) {
/* 3865 */         for (int i = 0; i < haids.size(); i++) {
/* 3866 */           ArrayList data = new ArrayList();
/* 3867 */           String haid = haids.get(i).toString();
/* 3868 */           String displayname = getLabelName(haid);
/* 3869 */           ArrayList a2 = new ArrayList();
/* 3870 */           a2.add(haid);
/* 3871 */           a2.add(displayname);
/* 3872 */           a2.add(displayname);
/* 3873 */           a2.add("HAI");
/*      */           
/* 3875 */           ArrayList a1 = getAllMonitorsPresentInGroup(haid, a2);
/* 3876 */           data.add(a1);
/* 3877 */           Vector v1 = new Vector();
/* 3878 */           Vector v2 = new Vector();
/* 3879 */           getMGtree(v1, v2, haid, displayname + "-> ");
/*      */           
/* 3881 */           int size1 = v1.size();
/* 3882 */           int size2 = v2.size();
/* 3883 */           if (size1 != size2) {
/* 3884 */             System.out.println("******BIG PROBLEM IN MONITOR GROUP REPORTS********************");
/*      */           }
/* 3886 */           if (!excludesSubGroup)
/*      */           {
/* 3888 */             for (int j = 0; j < size1; j++) {
/* 3889 */               String RID = v1.get(j).toString();
/* 3890 */               String RNAME = v2.get(j).toString();
/* 3891 */               ArrayList a4 = new ArrayList();
/* 3892 */               a4.add(RID);
/* 3893 */               a4.add(RNAME);
/* 3894 */               a4.add(RNAME);
/* 3895 */               a4.add("SUBGROUP");
/*      */               
/* 3897 */               ArrayList a3 = getAllMonitorsPresentInGroup(RID, a4);
/* 3898 */               data.add(a3);
/*      */             }
/*      */           }
/* 3901 */           allData.put(haid, data);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 3905 */       ex.printStackTrace();
/*      */     }
/* 3907 */     return allData;
/*      */   }
/*      */   
/*      */   private static LinkedHashMap getAllMonitorsInSubGroup_1(ArrayList subgroupsId, HashMap resultset)
/*      */   {
/* 3912 */     LinkedHashMap map = new LinkedHashMap();
/*      */     try {
/* 3914 */       for (int i = 0; i < subgroupsId.size(); i++) {
/* 3915 */         String resid = (String)subgroupsId.get(i);
/* 3916 */         ArrayList al = (ArrayList)resultset.get(resid);
/* 3917 */         for (int j = 0; j < al.size(); j++) {
/* 3918 */           ArrayList eachrow = (ArrayList)al.get(j);
/* 3919 */           String parentGrpId = (String)eachrow.get(0);
/* 3920 */           String parentGrpName = (String)eachrow.get(2);
/* 3921 */           String childResId = (String)eachrow.get(3);
/* 3922 */           String childResType = (String)eachrow.get(4);
/* 3923 */           String childResName = (String)eachrow.get(5);
/* 3924 */           String childResDisp = (String)eachrow.get(6);
/* 3925 */           String treeName = (String)eachrow.get(7);
/* 3926 */           if (childResType.equals("HAI")) {
/* 3927 */             if (!map.containsKey(parentGrpId)) {
/* 3928 */               ArrayList al1 = new ArrayList();
/* 3929 */               al1.add(parentGrpId);
/* 3930 */               al1.add(treeName.substring(0, treeName.lastIndexOf("->")));
/* 3931 */               al1.add(treeName.substring(0, treeName.lastIndexOf("->")));
/* 3932 */               al1.add("SUBGROUP");
/* 3933 */               ArrayList templist1 = (ArrayList)map.get(parentGrpId);
/* 3934 */               if (templist1 == null) {
/* 3935 */                 templist1 = new ArrayList();
/* 3936 */                 map.put(parentGrpId, templist1);
/*      */               }
/* 3938 */               templist1.add(al1);
/*      */             }
/*      */             
/* 3941 */             if (!map.containsKey(childResId)) {
/* 3942 */               ArrayList al2 = new ArrayList();
/* 3943 */               al2.add(childResId);
/* 3944 */               al2.add(treeName);
/* 3945 */               al2.add(treeName);
/* 3946 */               al2.add("SUBGROUP");
/* 3947 */               ArrayList templist2 = (ArrayList)map.get(childResId);
/* 3948 */               if (templist2 == null) {
/* 3949 */                 templist2 = new ArrayList();
/* 3950 */                 map.put(childResId, templist2);
/*      */               }
/* 3952 */               templist2.add(al2);
/*      */             }
/*      */           } else {
/* 3955 */             ArrayList al1 = new ArrayList();
/* 3956 */             al1.add(childResId);
/* 3957 */             al1.add(childResName);
/* 3958 */             al1.add(childResDisp);
/* 3959 */             al1.add(childResType);
/* 3960 */             ArrayList templist1 = (ArrayList)map.get(parentGrpId);
/* 3961 */             if (templist1 == null) {
/* 3962 */               templist1 = new ArrayList();
/* 3963 */               map.put(parentGrpId, templist1);
/*      */             }
/* 3965 */             templist1.add(al1);
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 3970 */       ex.printStackTrace();
/*      */     }
/* 3972 */     return map;
/*      */   }
/*      */   
/*      */   public static LinkedHashMap getMonitorsInMonitorGroup(ArrayList allids)
/*      */   {
/* 3977 */     LinkedHashMap toReturn = new LinkedHashMap();
/*      */     try
/*      */     {
/* 3980 */       if (allids.size() > 0) {
/* 3981 */         for (int i = 0; i < allids.size(); i++) {
/* 3982 */           ArrayList inside = (ArrayList)allids.get(i);
/* 3983 */           Vector v = new Vector();
/* 3984 */           String haid = null;
/* 3985 */           for (int k = 0; k < inside.size(); k++)
/*      */           {
/* 3987 */             ArrayList m = (ArrayList)inside.get(k);
/* 3988 */             String id = m.get(0).toString();
/* 3989 */             String type = m.get(3).toString();
/*      */             
/* 3991 */             if ("HAI".equals(type)) {
/* 3992 */               haid = id;
/*      */             }
/*      */             
/*      */ 
/* 3996 */             if ((!"HAI".equals(type)) && (!"SUBGROUP".equals(type)) && (!v.contains(id))) {
/* 3997 */               v.add(id);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 4004 */           if (!toReturn.containsKey(haid)) {
/* 4005 */             toReturn.put(haid, v);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 4011 */       ex.printStackTrace();
/*      */     }
/* 4013 */     return toReturn;
/*      */   }
/*      */   
/* 4016 */   public static ArrayList getValueForSnapshotReport(LinkedHashMap dataids, long startTime, long endTime, String Bid) { ArrayList toreturn = new ArrayList();
/*      */     try {
/* 4018 */       Collection c = dataids.keySet();
/* 4019 */       Iterator itr = c.iterator();
/* 4020 */       Vector leftids = new Vector();
/* 4021 */       String uptime = "100";
/* 4022 */       int availcount = 0;
/* 4023 */       int healthcount = 0;
/* 4024 */       long totalavaildowntime = 0L;
/* 4025 */       long totalhealthdowntime = 0L;
/* 4026 */       while (itr.hasNext()) {
/* 4027 */         ArrayList AllMgData = new ArrayList();
/* 4028 */         String key = itr.next().toString();
/*      */         
/* 4030 */         Vector v2 = new Vector();
/* 4031 */         v2.add(key);
/* 4032 */         ArrayList bothdata = new ArrayList();
/* 4033 */         boolean toAdd = false;
/* 4034 */         String mgName = "";
/* 4035 */         availcount = 0;
/* 4036 */         healthcount = 0;
/* 4037 */         totalavaildowntime = 0L;
/* 4038 */         totalhealthdowntime = 0L;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 4043 */         mgName = getLabelName(key);
/* 4044 */         Map MonitorGroupAvailabilityData = getAllDowntimeDetails(v2, startTime, endTime, Bid);
/* 4045 */         Map MonitorGroupHealthData = getAllHealthDowntimeDetails(v2, startTime, endTime, Bid);
/* 4046 */         Properties uptimeprops = getMonitorGroupAvailability(key, "-99", startTime, endTime, Bid);
/* 4047 */         uptime = uptimeprops.getProperty("available");
/*      */         
/* 4049 */         Vector monitorVector = null;
/* 4050 */         if (dataids.containsKey(key)) {
/* 4051 */           monitorVector = (Vector)dataids.get(key);
/*      */         } else {
/* 4053 */           monitorVector = new Vector();
/*      */         }
/* 4055 */         if ((MonitorGroupAvailabilityData.size() > 0) && (MonitorGroupAvailabilityData.containsKey(key)))
/*      */         {
/* 4057 */           ArrayList availabilityArray = (ArrayList)MonitorGroupAvailabilityData.get(key);
/* 4058 */           toAdd = true;
/* 4059 */           availcount = availabilityArray.size();
/* 4060 */           StartUtil.printStr("availcount ==> " + availcount);
/* 4061 */           for (int i = 0; i < availabilityArray.size(); i++) {
/* 4062 */             Properties rows = (Properties)availabilityArray.get(i);
/* 4063 */             String from = (String)rows.get("downtime");
/* 4064 */             String to = (String)rows.get("uptime");
/* 4065 */             String totaldowntime = (String)rows.get("TotalDownTime");
/* 4066 */             totalavaildowntime += Long.parseLong(totaldowntime);
/*      */             
/* 4068 */             Map MonitorAvailabilityData = getAllDowntimeDetails(monitorVector, Long.parseLong(from), Long.parseLong(to), Bid);
/*      */             
/* 4070 */             Collection Mc = MonitorAvailabilityData.keySet();
/* 4071 */             Iterator Mitr = Mc.iterator();
/* 4072 */             int Ms = 0;
/* 4073 */             while (Mitr.hasNext()) {
/* 4074 */               String Mkey = Mitr.next().toString();
/* 4075 */               ArrayList a1 = (ArrayList)MonitorAvailabilityData.get(Mkey);
/*      */               
/* 4077 */               for (int k = 0; k < a1.size(); k++) {
/* 4078 */                 Properties p1 = (Properties)a1.get(k);
/* 4079 */                 p1.put("type", "Availability");
/* 4080 */                 p1.put("message", "Resource Down");
/* 4081 */                 p1.remove("TotalDownTime");
/* 4082 */                 if (Ms == 0)
/*      */                 {
/* 4084 */                   p1.put("TotalDownTime", totaldowntime);
/*      */                 }
/*      */                 else {
/* 4087 */                   p1.put("TotalDownTime", "");
/*      */                 }
/* 4089 */                 Ms++;
/* 4090 */                 bothdata.add(p1);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 4098 */         if ((MonitorGroupHealthData.size() > 0) && (MonitorGroupHealthData.containsKey(key))) {
/* 4099 */           toAdd = true;
/* 4100 */           ArrayList healthArray = (ArrayList)MonitorGroupHealthData.get(key);
/* 4101 */           healthcount = healthArray.size();
/* 4102 */           StartUtil.printStr("healthcount ==> " + healthcount);
/* 4103 */           for (int j = 0; j < healthArray.size(); j++) {
/* 4104 */             Properties rows1 = (Properties)healthArray.get(j);
/* 4105 */             String from1 = (String)rows1.get("downtime");
/*      */             
/* 4107 */             String to1 = (String)rows1.get("uptime");
/*      */             
/* 4109 */             String totaldowntime1 = (String)rows1.get("TotalDownTime");
/* 4110 */             totalhealthdowntime += Long.parseLong(totaldowntime1);
/* 4111 */             Map MonitorHealthData = getAllHealthDowntimeDetails(monitorVector, Long.parseLong(from1), Long.parseLong(to1), Bid);
/* 4112 */             Collection Hc = MonitorHealthData.keySet();
/* 4113 */             Iterator Hitr = Hc.iterator();
/* 4114 */             int ps = 0;
/* 4115 */             while (Hitr.hasNext()) {
/* 4116 */               String Hkey = Hitr.next().toString();
/* 4117 */               ArrayList a2 = (ArrayList)MonitorHealthData.get(Hkey);
/*      */               
/* 4119 */               for (int l = 0; l < a2.size(); l++)
/*      */               {
/* 4121 */                 Properties p2 = (Properties)a2.get(l);
/*      */                 
/* 4123 */                 p2.put("type", "Health");
/*      */                 
/* 4125 */                 p2.remove("TotalDownTime");
/* 4126 */                 if (ps == 0)
/*      */                 {
/* 4128 */                   p2.put("TotalDownTime", totaldowntime1);
/*      */                 }
/*      */                 else {
/* 4131 */                   p2.put("TotalDownTime", "");
/*      */                 }
/* 4133 */                 ps++;
/* 4134 */                 bothdata.add(p2);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4144 */         if (toAdd) {
/* 4145 */           AllMgData.add(key);
/* 4146 */           AllMgData.add(mgName);
/* 4147 */           AllMgData.add("HAI");
/* 4148 */           AllMgData.add(bothdata);
/*      */           
/*      */ 
/* 4151 */           toreturn.add(AllMgData);
/*      */           
/*      */ 
/*      */ 
/* 4155 */           ArrayList sd = new ArrayList();
/* 4156 */           sd.add("");
/* 4157 */           sd.add(FormatUtil.getString("am.webclient.reports.string.totalfor", new String[] { getLabelName(key) }));
/*      */           
/* 4159 */           sd.add("TA");
/* 4160 */           ArrayList ef = new ArrayList();
/* 4161 */           Properties ts = new Properties();
/* 4162 */           ts.setProperty("uptime", "100");
/* 4163 */           ts.setProperty("TotalDownTime", "0");
/* 4164 */           ts.setProperty("AvailabilityTotalDownTime", totalavaildowntime + "");
/* 4165 */           ts.setProperty("Available", uptime);
/* 4166 */           ts.setProperty("type", "TA");
/* 4167 */           ts.setProperty("availcount", availcount + "");
/* 4168 */           ts.setProperty("healthcount", healthcount + "");
/* 4169 */           ts.setProperty("HealthTotalDownTime", totalhealthdowntime + "");
/* 4170 */           ef.add(ts);
/* 4171 */           sd.add(ef);
/* 4172 */           toreturn.add(sd);
/*      */         }
/*      */         else {
/* 4175 */           leftids.add(key);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4181 */       for (int h = 0; h < leftids.size(); h++) {
/* 4182 */         String ids = leftids.get(h).toString();
/* 4183 */         Properties uptimeprops = getMonitorGroupAvailability(ids, "-99", startTime, endTime, Bid);
/* 4184 */         uptime = uptimeprops.getProperty("available");
/* 4185 */         ArrayList sd = new ArrayList();
/* 4186 */         sd.add("");
/* 4187 */         sd.add(FormatUtil.getString("am.webclient.reports.string.totalfor", new String[] { getLabelName(ids) }));
/*      */         
/* 4189 */         sd.add("TA");
/* 4190 */         ArrayList ef = new ArrayList();
/* 4191 */         Properties ts = new Properties();
/* 4192 */         ts.setProperty("uptime", "100");
/* 4193 */         ts.setProperty("TotalDownTime", "0");
/* 4194 */         ts.setProperty("AvailabilityTotalDownTime", "0");
/* 4195 */         ts.setProperty("Available", uptime);
/* 4196 */         ts.setProperty("type", "TA");
/* 4197 */         ts.setProperty("availcount", "0");
/* 4198 */         ts.setProperty("healthcount", "0");
/* 4199 */         ts.setProperty("HealthTotalDownTime", "0");
/* 4200 */         ef.add(ts);
/* 4201 */         sd.add(ef);
/* 4202 */         toreturn.add(sd);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 4206 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 4209 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static ArrayList getStructuredDataForMonitorGroup(ArrayList allHaids) {
/* 4213 */     return getStructuredDataForMonitorGroup(allHaids, false);
/*      */   }
/*      */   
/*      */   public static ArrayList getStructuredDataForMonitorGroup(ArrayList allHaids, boolean excludesSubGroup) {
/* 4217 */     ArrayList ALLDATA = new ArrayList();
/*      */     try {
/* 4219 */       HashMap e = getAllMonitorsInGroup(allHaids, excludesSubGroup);
/*      */       
/* 4221 */       for (int m = 0; m < allHaids.size(); m++) {
/* 4222 */         ArrayList data = new ArrayList();
/* 4223 */         String haid = (String)allHaids.get(m);
/* 4224 */         if (e.containsKey(haid)) {
/* 4225 */           ArrayList a1 = (ArrayList)e.get(haid);
/* 4226 */           ArrayList mgs = (ArrayList)a1.get(0);
/*      */           
/* 4228 */           for (int p = 0; p < mgs.size(); p++) {
/* 4229 */             data.add((ArrayList)mgs.get(p));
/*      */           }
/*      */           
/* 4232 */           for (int i = 1; i < a1.size(); i++) {
/* 4233 */             ArrayList a2 = (ArrayList)a1.get(i);
/*      */             
/* 4235 */             for (int j = 0; j < a2.size(); j++) {
/* 4236 */               data.add((ArrayList)a2.get(j));
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 4242 */           ALLDATA.add(data);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 4247 */       ex.printStackTrace();
/*      */     }
/* 4249 */     return ALLDATA;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getAllMonitorsPresentInGroup(String haid, ArrayList a)
/*      */   {
/* 4255 */     ResultSet set = null;
/* 4256 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4257 */     ArrayList all = new ArrayList();
/*      */     try {
/* 4259 */       String query = "select amo.RESOURCEID, amo.RESOURCENAME, amo.displayname, amo.type from AM_PARENTCHILDMAPPER apcm inner join AM_ManagedObject amo on amo.RESOURCEID=apcm.CHILDID inner join AM_ManagedResourceType amrt on amrt.RESOURCETYPE=amo.type where apcm.PARENTID='" + haid + "' and amrt.RESOURCEGROUP IN (" + allResourcetypes + ") and amrt.SHORTNAME <>'Interface' order by amo.RESOURCENAME";
/* 4260 */       set = AMConnectionPool.executeQueryStmt(query);
/* 4261 */       all.add(a);
/* 4262 */       while (set.next()) {
/* 4263 */         ArrayList all1 = new ArrayList();
/* 4264 */         String resid = set.getString("RESOURCEID");
/* 4265 */         String resname = set.getString("RESOURCENAME");
/* 4266 */         String disname = set.getString("displayname");
/* 4267 */         disname = EnterpriseUtil.decodeString(disname);
/* 4268 */         String type = set.getString("type");
/*      */         
/* 4270 */         all1.add(resid);
/* 4271 */         all1.add(resname);
/* 4272 */         all1.add(disname);
/* 4273 */         all1.add(type);
/* 4274 */         all.add(all1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4279 */       ex.printStackTrace();
/*      */     }
/* 4281 */     return all;
/*      */   }
/*      */   
/*      */   public static String getResourceName(int resid) {
/* 4285 */     ResultSet set = null;
/* 4286 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4287 */     String displayname = "";
/* 4288 */     Hashtable display = new Hashtable();
/*      */     try
/*      */     {
/* 4291 */       String query = "SELECT AM_ManagedObject.RESOURCENAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where  AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.CHILDID=" + resid;
/*      */       
/* 4293 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 4295 */       if (set.next())
/*      */       {
/* 4297 */         displayname = set.getString("RESOURCENAME");
/*      */       }
/*      */       
/* 4300 */       display.put(String.valueOf(resid), displayname);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4304 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4308 */       closeResultSet(set);
/*      */     }
/* 4310 */     return displayname;
/*      */   }
/*      */   
/*      */   public static String getTypeName(String Type)
/*      */   {
/* 4315 */     ResultSet set = null;
/* 4316 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4317 */     String displayname = "";
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 4322 */       if ((Type != null) && ((Type.equals("HP-UX / Tru64")) || (Type.equals("Sun Solaris")) || (Type.equals("Windows")) || (Type.equals("Trap")) || (Type.equals("JMXNotification"))))
/*      */       {
/* 4324 */         displayname = Type;
/*      */       }
/*      */       else
/*      */       {
/* 4328 */         if (Type.equals("File System Monitor"))
/*      */         {
/* 4330 */           Type = "file";
/*      */         }
/*      */         
/* 4333 */         String query = "SELECT DISPLAYNAME FROM AM_ManagedResourceType where RESOURCETYPE='" + Type + "'";
/*      */         
/* 4335 */         set = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 4337 */         if (set.next())
/*      */         {
/* 4339 */           displayname = set.getString("DISPLAYNAME");
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4348 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4352 */       closeResultSet(set);
/*      */     }
/* 4354 */     return displayname;
/*      */   }
/*      */   
/*      */   public static String getResourceType(String id)
/*      */   {
/* 4359 */     ResultSet set = null;
/* 4360 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4361 */     String restype = "";
/*      */     
/*      */     try
/*      */     {
/* 4365 */       String query = "SELECT AM_ManagedObject.TYPE FROM AM_ManagedObject where AM_ManagedObject.RESOURCEID='" + id + "'";
/*      */       
/* 4367 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 4369 */       if (set.next())
/*      */       {
/* 4371 */         restype = set.getString("TYPE");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4378 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4382 */       closeResultSet(set);
/*      */     }
/* 4384 */     return restype;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getLabelName(String resid)
/*      */   {
/* 4390 */     ResultSet set = null;
/* 4391 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4392 */     String displayname = "";
/* 4393 */     if ((resid != null) && (!"null".equals(resid)) && (!"on".equals(resid)))
/*      */     {
/*      */       try
/*      */       {
/* 4397 */         String query = "SELECT AM_ManagedObject.DISPLAYNAME FROM AM_ManagedObject where AM_ManagedObject.RESOURCEID IN (" + resid + ")";
/*      */         
/* 4399 */         set = AMConnectionPool.executeQueryStmt(query);
/* 4400 */         if (set.next())
/*      */         {
/* 4402 */           displayname = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4409 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 4413 */         closeResultSet(set);
/*      */       }
/*      */     }
/* 4416 */     displayname = EnterpriseUtil.decodeString(displayname);
/* 4417 */     return displayname;
/*      */   }
/*      */   
/*      */   public static String[] getResourceTypeAndLabelName(String resid)
/*      */   {
/* 4422 */     ResultSet set = null;
/* 4423 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4424 */     String[] retString = new String[2];
/* 4425 */     String displayname = "";
/* 4426 */     String restype = "";
/*      */     try
/*      */     {
/* 4429 */       String query = "SELECT AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE FROM AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + resid;
/* 4430 */       if (!resid.equals("on"))
/*      */       {
/* 4432 */         set = AMConnectionPool.executeQueryStmt(query);
/* 4433 */         if (set.next())
/*      */         {
/* 4435 */           displayname = EnterpriseUtil.decodeString(set.getString("DISPLAYNAME"));
/* 4436 */           restype = set.getString("TYPE");
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4443 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4447 */       closeResultSet(set);
/*      */     }
/* 4449 */     retString[0] = restype;
/* 4450 */     retString[1] = displayname;
/* 4451 */     return retString;
/*      */   }
/*      */   
/*      */   public static String getBusinessRuleName(String id)
/*      */   {
/* 4456 */     ResultSet set = null;
/* 4457 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4458 */     String displayname = "oni";
/*      */     try
/*      */     {
/* 4461 */       if ((id != null) && (!id.equals("oni")))
/*      */       {
/* 4463 */         String query = "SELECT NAME from AM_BUSINESSHOURSDETAILS where id='" + id + "'";
/* 4464 */         set = AMConnectionPool.executeQueryStmt(query);
/* 4465 */         if (set.next())
/*      */         {
/* 4467 */           displayname = set.getString("NAME");
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4474 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4478 */       closeResultSet(set);
/*      */     }
/* 4480 */     return displayname;
/*      */   }
/*      */   
/*      */   private static final void closeResultSet(ResultSet set)
/*      */   {
/* 4485 */     if (set != null)
/*      */     {
/*      */       try
/*      */       {
/* 4489 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       catch (Exception ex) {
/* 4492 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static long[] parseTimeAndDate(String str1, String str2) throws IllegalArgumentException
/*      */   {
/* 4499 */     long[] toReturn = new long[2];
/* 4500 */     toReturn[0] = parseAndReturnTimeStamp(str1);
/* 4501 */     toReturn[1] = parseAndReturnTimeStamp(str2);
/* 4502 */     long currentTime = System.currentTimeMillis();
/* 4503 */     if (toReturn[0] > toReturn[1]) {
/* 4504 */       throw new IllegalArgumentException(FormatUtil.getString("am.webclient.report.greatertimeTO.message.text", new String[] { new java.util.Date(toReturn[0]).toString(), new java.util.Date(toReturn[1]).toString() }));
/*      */     }
/* 4506 */     if (toReturn[0] > currentTime) {
/* 4507 */       throw new IllegalArgumentException(FormatUtil.getString("am.webclient.report.greatertimeCurrent.message.text", new String[] { new java.util.Date(toReturn[0]).toString(), new java.util.Date(currentTime).toString() }));
/*      */     }
/*      */     
/* 4510 */     if (toReturn[1] > currentTime) {
/* 4511 */       toReturn[1] = currentTime;
/*      */     }
/* 4513 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static long parseAndReturnTimeStamp(String str)
/*      */   {
/* 4518 */     long time = 0L;
/*      */     try
/*      */     {
/* 4521 */       int i = str.indexOf(" ");
/* 4522 */       String s1 = str.substring(0, i);
/* 4523 */       String s2 = str.substring(i + 1);
/* 4524 */       String hr = s2.substring(0, s2.indexOf(":"));
/* 4525 */       String min = s2.substring(s2.indexOf(":") + 1);
/* 4526 */       long hrs = Integer.parseInt(hr) * 60 * 60 * 1000;
/* 4527 */       long mins = Integer.parseInt(min) * 60 * 1000;
/* 4528 */       java.sql.Date d = java.sql.Date.valueOf(s1);
/* 4529 */       time = d.getTime();
/* 4530 */       time = time + hrs + mins;
/*      */     }
/*      */     catch (Exception exp) {
/* 4533 */       AMLog.fatal("Reports : Cannot parse String in parseAndReturnTimeStamp STR=" + str, exp);
/*      */     }
/* 4535 */     return time;
/*      */   }
/*      */   
/*      */   public static long[] getDailyStartEndTime(long hourlyRptStartTime, long hourlyRptEndTime, String tableName)
/*      */   {
/* 4540 */     return SegmentReportUtil.getDailyStartEndTime(hourlyRptStartTime, hourlyRptEndTime, tableName);
/*      */   }
/*      */   
/*      */   public static String format(long time) {
/* 4544 */     String format = "";
/* 4545 */     time /= 1000L;
/* 4546 */     long totalMins = time / 60L;
/* 4547 */     long avaSecs = time % 60L;
/* 4548 */     long totalHours = totalMins / 60L;
/* 4549 */     long avaMins = totalMins % 60L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4559 */     format = totalHours + " " + FormatUtil.getString("am.webclient.report.hrs") + " " + avaMins + " " + FormatUtil.getString("am.webclient.report.mins") + " " + avaSecs + " " + FormatUtil.getString("am.webclient.report.secs");
/*      */     
/* 4561 */     if (totalHours == 0L)
/*      */     {
/* 4563 */       if (avaMins == 0L) {
/* 4564 */         format = avaSecs + " " + FormatUtil.getString("am.webclient.report.secs");
/*      */       } else {
/* 4566 */         format = avaMins + " " + FormatUtil.getString("am.webclient.report.mins") + " " + avaSecs + " " + FormatUtil.getString("am.webclient.report.secs");
/*      */       }
/*      */     }
/* 4569 */     return format;
/*      */   }
/*      */   
/*      */   public static String formatWithoutSeconds(long time) {
/* 4573 */     String format = "";
/* 4574 */     time /= 1000L;
/* 4575 */     long totalMins = time / 60L;
/* 4576 */     long avaSecs = time % 60L;
/* 4577 */     long totalHours = totalMins / 60L;
/* 4578 */     long avaMins = totalMins % 60L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4588 */     format = totalHours + " " + FormatUtil.getString("am.webclient.report.hrs") + " " + avaMins + " " + FormatUtil.getString("am.webclient.report.mins");
/*      */     
/* 4590 */     if (totalHours == 0L)
/*      */     {
/* 4592 */       if (avaMins == 0L) {
/* 4593 */         format = avaSecs + " " + FormatUtil.getString("am.webclient.report.secs");
/*      */       } else {
/* 4595 */         format = avaMins + " " + FormatUtil.getString("am.webclient.report.mins") + " " + avaSecs + " " + FormatUtil.getString("am.webclient.report.secs");
/*      */       }
/*      */     }
/* 4598 */     return format;
/*      */   }
/*      */   
/*      */ 
/*      */   public static final long[] getTimeStamp(String period)
/*      */   {
/* 4604 */     Calendar cal = new GregorianCalendar();
/* 4605 */     Calendar cal1 = new GregorianCalendar();
/*      */     
/* 4607 */     int dayofweek = cal1.get(7);
/*      */     
/* 4609 */     long[] toReturn = new long[2];
/* 4610 */     long curTime = System.currentTimeMillis();
/* 4611 */     long startTime = 0L;
/* 4612 */     long endTime = curTime;
/* 4613 */     if (period.equals("0"))
/*      */     {
/* 4615 */       java.util.Date currentTimeAsDate = new java.util.Date(curTime);
/* 4616 */       Calendar cldr = Calendar.getInstance();
/* 4617 */       cldr.setTime(currentTimeAsDate);
/* 4618 */       cldr.set(11, 0);
/* 4619 */       cldr.set(13, 0);
/* 4620 */       cldr.set(14, 0);
/* 4621 */       cldr.set(12, 0);
/* 4622 */       startTime = cldr.getTime().getTime();
/*      */     }
/* 4624 */     else if ((period.equals("1")) || (period.equals("-7")))
/*      */     {
/* 4626 */       startTime = curTime - 604800000L;
/*      */     }
/* 4628 */     else if ((period.equals("2")) || (period.equals("-30")))
/*      */     {
/* 4630 */       startTime = curTime - 2592000000L;
/*      */ 
/*      */     }
/* 4633 */     else if (period.equals("5"))
/*      */     {
/* 4635 */       startTime = curTime - 31536000000L;
/*      */     }
/* 4637 */     else if ("16".equals(period))
/*      */     {
/* 4639 */       startTime = curTime - 7776000000L;
/*      */     }
/* 4641 */     else if ("17".equals(period))
/*      */     {
/* 4643 */       startTime = curTime - 15552000000L;
/*      */ 
/*      */ 
/*      */     }
/* 4647 */     else if (period.equals("6"))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 4652 */       cal.set(7, 2);
/* 4653 */       cal.set(11, 0);
/* 4654 */       cal.set(12, 0);
/* 4655 */       cal.set(13, 0);
/* 4656 */       cal.set(14, 0);
/*      */       
/* 4658 */       long lday = cal.getTimeInMillis();
/* 4659 */       startTime = lday;
/*      */       
/*      */ 
/* 4662 */       if (dayofweek == 7)
/*      */       {
/*      */ 
/* 4665 */         cal.set(7, 1);
/* 4666 */         cal.set(11, 0);
/* 4667 */         cal.set(12, 0);
/* 4668 */         cal.set(13, 0);
/* 4669 */         cal.set(14, 0);
/*      */         
/* 4671 */         long lweek1 = cal.getTimeInMillis();
/* 4672 */         startTime = lweek1;
/* 4673 */         startTime += 86400000L;
/* 4674 */         endTime = startTime + 432000000L;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4679 */       if (dayofweek == 1)
/*      */       {
/* 4681 */         cal.set(7, 1);
/* 4682 */         cal.set(11, 0);
/* 4683 */         cal.set(12, 0);
/* 4684 */         cal.set(13, 0);
/* 4685 */         cal.set(14, 0);
/*      */         
/* 4687 */         long lweek1 = cal.getTimeInMillis();
/* 4688 */         startTime = lweek1 - 604800000L;
/* 4689 */         startTime += 86400000L;
/* 4690 */         endTime = startTime + 432000000L;
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 4699 */     else if (period.equals("7"))
/*      */     {
/* 4701 */       cal.set(5, 1);
/* 4702 */       cal.set(11, 0);
/* 4703 */       cal.set(12, 0);
/* 4704 */       cal.set(13, 0);
/* 4705 */       cal.set(14, 0);
/*      */       
/* 4707 */       long lmonth = cal.getTimeInMillis();
/* 4708 */       startTime = lmonth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 4714 */     else if (period.equals("8"))
/*      */     {
/* 4716 */       cal.set(6, 1);
/* 4717 */       cal.set(11, 0);
/* 4718 */       cal.set(12, 0);
/* 4719 */       cal.set(13, 0);
/* 4720 */       cal.set(14, 0);
/*      */       
/* 4722 */       long lyear = cal.getTimeInMillis();
/* 4723 */       startTime = lyear;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 4729 */     else if (period.equals("9"))
/*      */     {
/* 4731 */       int i = cal.get(2);
/* 4732 */       if (i < 3)
/*      */       {
/* 4734 */         cal.set(2, 0);
/*      */       }
/* 4736 */       else if (i < 6)
/*      */       {
/* 4738 */         cal.set(2, 3);
/*      */       }
/* 4740 */       else if (i < 9)
/*      */       {
/* 4742 */         cal.set(2, 6);
/*      */       }
/*      */       else
/*      */       {
/* 4746 */         cal.set(2, 9);
/*      */       }
/* 4748 */       cal.set(5, 1);
/* 4749 */       cal.set(11, 0);
/* 4750 */       cal.set(12, 0);
/* 4751 */       cal.set(13, 0);
/* 4752 */       cal.set(14, 0);
/*      */       
/* 4754 */       long lqatar = cal.getTimeInMillis();
/* 4755 */       startTime = lqatar;
/*      */ 
/*      */     }
/* 4758 */     else if (period.equals("11"))
/*      */     {
/* 4760 */       int i = cal.get(2);
/*      */       
/* 4762 */       cal.set(2, i - 1);
/*      */       
/* 4764 */       cal.set(5, 1);
/*      */       
/* 4766 */       cal.set(11, 0);
/* 4767 */       cal.set(12, 0);
/* 4768 */       cal.set(13, 0);
/* 4769 */       cal.set(14, 0);
/*      */       
/* 4771 */       long lastmonth = cal.getTimeInMillis();
/* 4772 */       startTime = lastmonth;
/* 4773 */       int maxDaysInMonth = cal.getActualMaximum(5);
/* 4774 */       endTime = startTime + maxDaysInMonth * 24 * 60 * 60 * 1000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4792 */     else if (period.equals("12"))
/*      */     {
/*      */ 
/* 4795 */       cal.set(7, 1);
/* 4796 */       cal.set(11, 0);
/* 4797 */       cal.set(12, 0);
/* 4798 */       cal.set(13, 0);
/* 4799 */       cal.set(14, 0);
/*      */       
/* 4801 */       long lweek = cal.getTimeInMillis();
/* 4802 */       startTime = lweek - 604800000L;
/* 4803 */       endTime = startTime + 604800000L;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 4808 */     else if (period.equals("15"))
/*      */     {
/* 4810 */       cal.set(7, Constants.weekStartDay);
/* 4811 */       cal.set(11, 0);
/* 4812 */       cal.set(12, 0);
/* 4813 */       cal.set(13, 0);
/* 4814 */       cal.set(14, 0);
/*      */       
/* 4816 */       long lweek1 = cal.getTimeInMillis();
/* 4817 */       startTime = lweek1;
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 4822 */     else if (period.equals("10"))
/*      */     {
/* 4824 */       startTime = curTime - 15552000000L;
/*      */ 
/*      */ 
/*      */     }
/* 4828 */     else if (period.equals("3"))
/*      */     {
/* 4830 */       java.util.Date currentTimeAsDate = new java.util.Date(curTime);
/* 4831 */       Calendar cldr = Calendar.getInstance();
/* 4832 */       cldr.setTime(currentTimeAsDate);
/* 4833 */       cldr.add(7, -1);
/* 4834 */       cldr.set(11, 0);
/* 4835 */       cldr.set(13, 0);
/* 4836 */       cldr.set(14, 0);
/* 4837 */       cldr.set(12, 0);
/* 4838 */       startTime = cldr.getTime().getTime();
/* 4839 */       cldr.setTime(currentTimeAsDate);
/* 4840 */       cldr.set(11, 0);
/* 4841 */       cldr.set(13, 0);
/* 4842 */       cldr.set(14, 0);
/* 4843 */       cldr.set(12, 0);
/* 4844 */       endTime = cldr.getTime().getTime();
/*      */ 
/*      */     }
/* 4847 */     else if (period.equals("29"))
/*      */     {
/*      */ 
/* 4850 */       java.util.Date currentTimeAsDate = new java.util.Date(curTime);
/* 4851 */       Calendar cldr = Calendar.getInstance();
/* 4852 */       cldr.setTime(currentTimeAsDate);
/* 4853 */       endTime = cldr.getTime().getTime();
/* 4854 */       startTime = endTime - 86400000L;
/*      */     }
/* 4856 */     AMLog.debug("Reports : Period : " + period + " : The calculated startTime = " + startTime + " " + new java.util.Date(startTime) + " and endTime =" + endTime + "" + new java.util.Date(endTime));
/* 4857 */     toReturn[0] = startTime;
/* 4858 */     toReturn[1] = endTime;
/* 4859 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final Hashtable getMinTimeInDB(Vector resids)
/*      */   {
/* 4867 */     ResultSet set = null;
/* 4868 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4869 */     Hashtable residMintimeMap = new Hashtable();
/* 4870 */     long mintime = 0L;
/* 4871 */     long toReturn = 0L;
/*      */     try
/*      */     {
/* 4874 */       String conditionStr = getCondition("RESOURCEID", resids);
/* 4875 */       String query1 = "select RESOURCEID,CREATIONTIME as mintime from AM_ManagedObject where " + conditionStr;
/* 4876 */       set = AMConnectionPool.executeQueryStmt(query1);
/* 4877 */       while (set.next())
/*      */       {
/* 4879 */         int resourceid = set.getInt("RESOURCEID");
/* 4880 */         mintime = set.getLong("mintime");
/*      */         
/* 4882 */         residMintimeMap.put(resourceid + "", Long.valueOf(mintime));
/*      */       }
/* 4884 */       closeResultSet(set);
/*      */     }
/*      */     catch (Exception exp) {
/* 4887 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4891 */       closeResultSet(set);
/*      */     }
/* 4893 */     return residMintimeMap;
/*      */   }
/*      */   
/*      */   public static final long getMinTimeInDB(String resourceid)
/*      */   {
/* 4898 */     long mintime = 0L;
/* 4899 */     long toReturn = 0L;
/* 4900 */     if (DBUtil.minTimeinDBMGs.containsKey(resourceid)) {
/*      */       try {
/* 4902 */         mintime = ((Long)((HashMap)DBUtil.minTimeinDBMGs.get(resourceid)).get("startTime")).longValue();
/*      */       } catch (Exception he) {
/* 4904 */         he.printStackTrace();
/*      */       }
/* 4906 */       return mintime;
/*      */     }
/*      */     
/* 4909 */     ResultSet set = null;
/* 4910 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */     try
/*      */     {
/* 4914 */       String query1 = "select DISPLAYNAME,CREATIONTIME as mintime from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 4915 */       set = AMConnectionPool.executeQueryStmt(query1);
/* 4916 */       HashMap haidinfo; if (set.next())
/*      */       {
/* 4918 */         mintime = set.getLong("mintime");
/* 4919 */         haidinfo = new HashMap();
/* 4920 */         haidinfo.put("startTime", Long.valueOf(mintime));
/* 4921 */         haidinfo.put("dspName", EnterpriseUtil.decodeString(set.getString("DISPLAYNAME")));
/* 4922 */         DBUtil.minTimeinDBMGs.put(resourceid, haidinfo);
/*      */       }
/*      */       
/* 4925 */       closeResultSet(set);
/*      */       
/* 4927 */       return mintime;
/*      */     }
/*      */     catch (Exception exp) {
/* 4930 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4934 */       closeResultSet(set);
/*      */     }
/* 4936 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static Hashtable getMinTimeForEUMAgent(Vector resids)
/*      */   {
/* 4941 */     ResultSet set = null;
/* 4942 */     Hashtable residMintimeMap = new Hashtable();
/* 4943 */     long mintime = 0L;
/* 4944 */     long toReturn = 0L;
/*      */     try
/*      */     {
/* 4947 */       String conditionStr = getCondition("AGENTID", resids);
/* 4948 */       String query1 = "select AGENTID,CREATIONTIME as mintime from AM_RBMAGENTDATA where " + conditionStr;
/* 4949 */       set = AMConnectionPool.executeQueryStmt(query1);
/* 4950 */       while (set.next())
/*      */       {
/* 4952 */         int resourceid = set.getInt("AGENTID");
/* 4953 */         mintime = set.getLong("mintime");
/* 4954 */         residMintimeMap.put(resourceid + "", Long.valueOf(mintime));
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 4959 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4963 */       AMConnectionPool.closeStatement(set);
/*      */     }
/* 4965 */     return residMintimeMap;
/*      */   }
/*      */   
/*      */   public static ArrayList getTimeToDisplayInTrendReport(ArrayList all, String interval)
/*      */   {
/* 4970 */     ArrayList toReturn = new ArrayList();
/*      */     try
/*      */     {
/* 4973 */       for (int i = 0; i < all.size(); i++) {
/* 4974 */         Properties p1 = (Properties)all.get(i);
/*      */         
/* 4976 */         long starttime = Long.parseLong(p1.get("starttime").toString());
/* 4977 */         long endtime = Long.parseLong(p1.get("endtime").toString());
/*      */         
/* 4979 */         java.util.Date d = new java.util.Date(starttime);
/* 4980 */         Calendar cal = Calendar.getInstance();
/* 4981 */         cal.setTimeInMillis(starttime);
/*      */         
/*      */ 
/* 4984 */         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
/* 4985 */         java.util.Date resultdate = new java.util.Date(starttime);
/*      */         
/*      */ 
/* 4988 */         if ("month".equals(interval))
/*      */         {
/* 4990 */           String mon = FormatUtil.getString(getMonthToDisplay(cal.get(2))) + " " + cal.get(1);
/* 4991 */           toReturn.add(mon);
/*      */         }
/*      */         else {
/* 4994 */           toReturn.add(sdf.format(resultdate));
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 4998 */       ex.printStackTrace();
/*      */     }
/* 5000 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static String getMonthToDisplay(int mon) {
/* 5004 */     if (mon == 0)
/* 5005 */       return "Jan";
/* 5006 */     if (mon == 1)
/* 5007 */       return "Feb";
/* 5008 */     if (mon == 2)
/* 5009 */       return "Mar";
/* 5010 */     if (mon == 3)
/* 5011 */       return "Apr";
/* 5012 */     if (mon == 4)
/* 5013 */       return "May";
/* 5014 */     if (mon == 5)
/* 5015 */       return "Jun";
/* 5016 */     if (mon == 6)
/* 5017 */       return "Jul";
/* 5018 */     if (mon == 7)
/* 5019 */       return "Aug";
/* 5020 */     if (mon == 8)
/* 5021 */       return "Sep";
/* 5022 */     if (mon == 9)
/* 5023 */       return "Oct";
/* 5024 */     if (mon == 10)
/* 5025 */       return "Nov";
/* 5026 */     if (mon == 11) {
/* 5027 */       return "Dec";
/*      */     }
/* 5029 */     return "";
/*      */   }
/*      */   
/*      */   public static ArrayList getLastTwelveTimeStamps(String interval) {
/* 5033 */     long timeinMillis = 0L;
/* 5034 */     long starttime = 0L;
/* 5035 */     long endtime = 0L;
/* 5036 */     ArrayList toReturn = new ArrayList();
/* 5037 */     Calendar cal = Calendar.getInstance();
/* 5038 */     if ("day".equals(interval)) {
/* 5039 */       long[] t1 = getTimeStamp("0");
/* 5040 */       starttime = t1[0];
/* 5041 */       endtime = t1[1];
/* 5042 */       timeinMillis = 86400000L;
/* 5043 */     } else if ("week".equals(interval)) {
/* 5044 */       long[] t1 = getTimeStamp("15");
/* 5045 */       starttime = t1[0];
/* 5046 */       endtime = t1[1];
/* 5047 */       timeinMillis = 604800000L;
/* 5048 */     } else if ("month".equals(interval))
/*      */     {
/*      */ 
/* 5051 */       long[] t1 = getTimeStamp("7");
/* 5052 */       starttime = t1[0];
/* 5053 */       endtime = t1[1];
/*      */     }
/*      */     
/*      */ 
/* 5057 */     Properties row1 = new Properties();
/* 5058 */     row1.put("starttime", Long.valueOf(starttime));
/* 5059 */     row1.put("endtime", Long.valueOf(endtime));
/* 5060 */     toReturn.add(row1);
/* 5061 */     int currentMonth = cal.get(2);
/* 5062 */     int currentYear = cal.get(1);
/*      */     
/*      */ 
/* 5065 */     int j = 1;
/* 5066 */     for (int i = 1; i < 13; i++)
/*      */     {
/* 5068 */       if ("month".equals(interval))
/*      */       {
/* 5070 */         if (currentMonth - j == -1)
/*      */         {
/* 5072 */           currentMonth = 11;
/* 5073 */           j = 0;
/* 5074 */           currentYear -= 1;
/*      */         }
/*      */         
/*      */ 
/* 5078 */         cal.set(1, currentYear);
/* 5079 */         cal.set(2, currentMonth - j);
/*      */         
/* 5081 */         cal.set(5, 1);
/*      */         
/* 5083 */         cal.set(11, 0);
/* 5084 */         cal.set(12, 0);
/* 5085 */         cal.set(13, 0);
/* 5086 */         cal.set(14, 0);
/*      */         
/* 5088 */         long lastmonth = cal.getTimeInMillis();
/* 5089 */         starttime = lastmonth;
/* 5090 */         int maxDaysInMonth = cal.getActualMaximum(5);
/* 5091 */         timeinMillis = maxDaysInMonth * 24 * 60 * 60 * 1000L;
/* 5092 */         endtime = starttime + timeinMillis;
/*      */       }
/*      */       else {
/* 5095 */         endtime = starttime;
/* 5096 */         starttime -= timeinMillis;
/*      */       }
/*      */       
/*      */ 
/* 5100 */       j++;
/* 5101 */       Properties row = new Properties();
/* 5102 */       row.put("starttime", Long.valueOf(starttime));
/* 5103 */       row.put("endtime", Long.valueOf(endtime));
/* 5104 */       toReturn.add(row);
/*      */     }
/*      */     
/* 5107 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static long roundOffToNearestSeconds(long timInMilliSecs)
/*      */   {
/* 5112 */     long toRet = timInMilliSecs / 1000L * 1000L;
/* 5113 */     if (timInMilliSecs % 1000L >= 500L) {
/* 5114 */       toRet += 1000L;
/*      */     }
/* 5116 */     return toRet;
/*      */   }
/*      */   
/*      */   public static void setHourlyCleanupDays(int hourlyCleanup)
/*      */   {
/* 5121 */     hourlyCleanupDays = hourlyCleanup;
/* 5122 */     SegmentReportUtil.setHourlyCleanupDays(hourlyCleanup);
/*      */   }
/*      */   
/*      */   public static void setDailyCleanupDays(int dailyCleanup) {
/* 5126 */     dailyCleanupDays = dailyCleanup;
/* 5127 */     SegmentReportUtil.setDailyCleanupDays(dailyCleanup);
/*      */   }
/*      */   
/*      */   public static void setForecastReport(boolean forecast) {
/* 5131 */     isForecastReport = forecast;
/*      */   }
/*      */   
/*      */   public static void getMGtree(Vector toreturn, Vector treelist, String resourceid, String tree)
/*      */   {
/* 5136 */     if ((DBQueryUtil.isMssql()) || (DBQueryUtil.isPgsql())) {
/* 5137 */       ResultSet rs = null;
/*      */       try {
/* 5139 */         StringBuilder builder = new StringBuilder();
/* 5140 */         if (DBQueryUtil.isMssql()) {
/* 5141 */           builder.append("WITH CHILDRESOURCES_CTE(RESOURCEID,TYPE,RESOURCENAME) AS ");
/*      */         } else {
/* 5143 */           builder.append("WITH RECURSIVE CHILDRESOURCES_CTE(RESOURCEID,TYPE,RESOURCENAME) AS ");
/*      */         }
/* 5145 */         builder.append("(");
/* 5146 */         builder.append("SELECT DISTINCT AM_ManagedObject.RESOURCEID, AM_ManagedObject.TYPE,AM_ManagedObject.RESOURCENAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER WHERE AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID AND AM_ManagedObject.TYPE='HAI' AND AM_PARENTCHILDMAPPER.PARENTID=").append(resourceid);
/* 5147 */         builder.append(" UNION ALL ");
/* 5148 */         builder.append("SELECT AM_PARENTCHILDMAPPER.CHILDID,childmo.TYPE,CAST((CONCAT(CHILDRESOURCES_CTE.RESOURCENAME,CONCAT('-> ',childmo.RESOURCENAME))) AS VARCHAR(2000)) AS RESOURCENAME FROM AM_PARENTCHILDMAPPER INNER JOIN AM_ManagedObject parentmo on AM_PARENTCHILDMAPPER.PARENTID = parentmo.RESOURCEID INNER JOIN AM_ManagedObject childmo on AM_PARENTCHILDMAPPER.CHILDID= childmo.RESOURCEID INNER JOIN CHILDRESOURCES_CTE ON CHILDRESOURCES_CTE.RESOURCEID = AM_PARENTCHILDMAPPER.PARENTID where CHILDRESOURCES_CTE.TYPE='HAI'");
/* 5149 */         builder.append(")");
/* 5150 */         builder.append(" SELECT DISTINCT RESOURCEID,TYPE,RESOURCENAME FROM CHILDRESOURCES_CTE WHERE TYPE='HAI' AND RESOURCEID!=").append(resourceid);
/* 5151 */         StartUtil.printStr("ReportUtilities.getMGtree:CTE query to get child resources ===> " + builder);
/* 5152 */         rs = AMConnectionPool.executeQueryStmt(builder.toString());
/* 5153 */         while (rs.next()) {
/* 5154 */           toreturn.add(rs.getString("RESOURCEID"));
/* 5155 */           treelist.add(rs.getString("RESOURCENAME"));
/*      */         }
/*      */       } catch (Exception ex) {
/* 5158 */         ex.printStackTrace();
/*      */       } finally {
/* 5160 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     } else {
/*      */       try {
/* 5164 */         String query = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.DISPLAYNAME  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1";
/* 5165 */         ArrayList templist = DBUtil.getRows(query);
/* 5166 */         for (int i = 0; i < templist.size(); i++) {
/* 5167 */           ArrayList singlerow = (ArrayList)templist.get(i);
/* 5168 */           String chResId = (String)singlerow.get(0);
/* 5169 */           String displayname = (String)singlerow.get(1);
/* 5170 */           toreturn.add(chResId);
/* 5171 */           if ("".equals(tree)) {
/* 5172 */             treelist.add(tree + displayname + "->");
/*      */           } else {
/* 5174 */             treelist.add(tree + displayname);
/*      */           }
/* 5176 */           getMGtree(toreturn, treelist, chResId, tree + displayname + "->");
/*      */         }
/*      */       } catch (Exception ex) {
/* 5179 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void getMGtreeforMonitors(Vector toreturn, Vector treelist, String resourceid, String tree)
/*      */   {
/*      */     try
/*      */     {
/* 5188 */       String query = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.DISPLAYNAME  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1";
/* 5189 */       ArrayList templist = DBUtil.getRows(query);
/*      */       
/* 5191 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 5193 */         ArrayList singlerow = (ArrayList)templist.get(i);
/* 5194 */         String chResId = (String)singlerow.get(0);
/* 5195 */         String displayname = (String)singlerow.get(1);
/* 5196 */         toreturn.add(chResId);
/* 5197 */         if ("".equals(tree))
/*      */         {
/* 5199 */           treelist.add(tree + displayname + "->");
/*      */         }
/*      */         else
/*      */         {
/* 5203 */           treelist.add(tree + displayname + "->");
/*      */         }
/* 5205 */         getMGtreeforMonitors(toreturn, treelist, chResId, tree + displayname + "->");
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 5210 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/* 5215 */   public static Hashtable getAvailabilityHistoryForMGs(String type, int period, String owner, String role) { return getAvailabilityHistoryForMGs(type, period, owner, role, null); }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryForMGs(String type, int period, String owner, String role, HttpServletRequest request) {
/* 5218 */     String mqry = "";
/* 5219 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 5221 */       mqry = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_HOLISTICAPPLICATION ha  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' and mo.resourceid=ha.haid and ha.type=0";
/* 5222 */       if ((request != null) && (request.getParameter("selecttionType") != null) && ("CUSTOM FIELDS".equals(request.getParameter("selecttionType"))))
/*      */       {
/* 5224 */         String datatable = "";
/* 5225 */         String aliasname = "";
/* 5226 */         String value = "";
/* 5227 */         String qryCon = "";
/* 5228 */         String widgetid = request.getParameter("widgetid");
/* 5229 */         ResultSet res = null;
/*      */         try {
/* 5231 */           res = AMConnectionPool.executeQueryStmt("select VALUE,ALIASNAME,FIELDTYPE,DATATABLE from AM_MYFIELDS_METADATA as me, AM_MYPAGE_WIDGET_CUSTOMFIELD as cs where me.fieldid=cs.fieldid and cs.widgetid=" + widgetid);
/* 5232 */           if (res.next())
/*      */           {
/* 5234 */             datatable = res.getString("DATATABLE");
/* 5235 */             aliasname = res.getString("ALIASNAME");
/* 5236 */             value = res.getString("VALUE");
/*      */           }
/* 5238 */           HashMap<String, String> conditon = MyFields.customCondition(aliasname, value, null, false);
/* 5239 */           datatable = (String)conditon.get("groupTable");
/* 5240 */           qryCon = (String)conditon.get("groupQuery");
/*      */         } catch (Exception ex) {
/* 5242 */           ex.printStackTrace();
/*      */         } finally {
/* 5244 */           if (res != null) {
/* 5245 */             AMConnectionPool.closeStatement(res);
/*      */           }
/*      */         }
/* 5248 */         String isSubGroup = request.getParameter("subgroup");
/* 5249 */         String enableSubGroup = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/* 5250 */         mqry = "select AM_HOLISTICAPPLICATION.HAID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by DISPLAYNAME";
/* 5251 */         if ("1".equals(isSubGroup)) {
/* 5252 */           enableSubGroup = "";
/* 5253 */           mqry = "select AM_HOLISTICAPPLICATION.HAID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by DISPLAYNAME";
/* 5254 */           Vector<String> getsubgroups = MyFields.listMgSubGroups(mqry, datatable, "HAID", qryCon);
/* 5255 */           mqry = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where " + EnterpriseUtil.getCondition("RESOURCEID", getsubgroups);
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 5261 */       String condition = getQueryCondition("mo.RESOURCEID", owner);
/* 5262 */       String bsgType = "0";
/* 5263 */       if (request != null)
/*      */       {
/* 5265 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 5267 */           bsgType = "1";
/* 5268 */           Vector resVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 5269 */           if (request.isUserInRole("OPERATOR"))
/*      */           {
/* 5271 */             condition = EnterpriseUtil.getCondition("hao.HAID", resVector);
/* 5272 */             Vector associatedBsg = DBUtil.assignedBsg(owner, condition);
/* 5273 */             if (!associatedBsg.isEmpty())
/*      */             {
/* 5275 */               condition = EnterpriseUtil.getCondition("mo.RESOURCEID", associatedBsg);
/*      */             }
/*      */             else
/*      */             {
/* 5279 */               condition = null;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 5284 */             condition = EnterpriseUtil.getCondition("mo.RESOURCEID", resVector);
/*      */           }
/*      */         }
/*      */       }
/* 5288 */       mqry = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_HOLISTICAPPLICATION ha  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' and mo.resourceid=ha.haid and ha.type=" + bsgType + " and " + condition;
/*      */     }
/*      */     
/* 5291 */     return getAvailabilityHistory(mqry, period);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryByType(String type, int period, String owner, String role, Vector resIds_vector) {
/* 5295 */     return getAvailabilityHistoryByType(type, period, owner, role, resIds_vector, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryByType(String type, int period, String owner, String role)
/*      */   {
/* 5300 */     return getAvailabilityHistoryByType(type, period, owner, role, null, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryByType(String type, int period, String owner, String role, Vector resIds_vector, int startIndex, int noOfRows, HttpServletRequest request) {
/* 5304 */     String query = "";
/* 5305 */     String eumCondition = " ";
/* 5306 */     if (("All".equalsIgnoreCase(type)) || (Constants.resourceTypesEUM.contains(type)))
/*      */     {
/* 5308 */       eumCondition = " and mo.resourceid NOT IN (" + Constants.getEUMChildString() + ") ";
/*      */     }
/* 5310 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 5312 */       if ((type.equalsIgnoreCase("applications")) || (type.equalsIgnoreCase("$ComplexType_AllApps")))
/*      */       {
/* 5314 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP NOT IN " + Constants.serverTypes + eumCondition + " AND mo.TYPE NOT LIKE '%OpManager%' order by mo.displayname";
/* 5315 */         if (Constants.isIt360)
/*      */         {
/* 5317 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP NOT IN " + Constants.nonApplicationResGpTypes + eumCondition + " AND mo.TYPE NOT LIKE '%OpManager%' order by mo.displayname";
/*      */         }
/*      */       }
/* 5320 */       else if ((type.equalsIgnoreCase("servers")) || (type.equalsIgnoreCase("$ComplexType_AllSers")))
/*      */       {
/* 5322 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + Constants.serverResGpTypes + eumCondition + " AND mo.TYPE NOT LIKE '%OpManager%' order by mo.displayname";
/*      */       }
/* 5324 */       else if (type.equals("All"))
/*      */       {
/*      */ 
/* 5327 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mo.TYPE NOT IN ('Network','HAI') and mrt.SUBGROUP not in ('OpManager-Interface')" + eumCondition + " order by mo.displayname";
/*      */       }
/* 5329 */       else if (type.equals("$ComplexType_NetworkDevices"))
/*      */       {
/* 5331 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP not in ('OpManager-Interface') and mrt.SUBGROUP like 'OpManager%' order by mo.displayname";
/*      */       }
/*      */       else
/*      */       {
/* 5335 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "'" + eumCondition + " order by mo.displayname";
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 5340 */       String condition = "";
/* 5341 */       String loginUserid = null;
/* 5342 */       boolean isUserResourceEnabled = false;
/* 5343 */       if (!Constants.isSsoEnabled()) {
/* 5344 */         condition = getQueryCondition("mo.RESOURCEID", owner);
/*      */       } else {
/* 5346 */         isUserResourceEnabled = true;
/* 5347 */         loginUserid = Constants.getLoginUserid(request);
/*      */       }
/* 5349 */       condition = condition.trim().length() > 0 ? " and " + condition : condition;
/*      */       
/*      */ 
/* 5352 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (resIds_vector != null))
/*      */       {
/* 5354 */         condition = getCondition("mo.RESOURCEID", resIds_vector);
/* 5355 */         condition = condition + eumCondition;
/*      */       }
/*      */       
/*      */ 
/* 5359 */       if ((type.equalsIgnoreCase("applications")) || (type.equalsIgnoreCase("$ComplexType_AllApps")))
/*      */       {
/* 5361 */         if (isUserResourceEnabled) {
/* 5362 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP NOT IN " + Constants.serverTypes + " AND mo.TYPE NOT LIKE '%OpManager%' order by mo.displayname";
/*      */         } else {
/* 5364 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP NOT IN " + Constants.serverTypes + " AND mo.TYPE NOT LIKE '%OpManager%' " + condition + " order by mo.displayname";
/*      */         }
/*      */         
/* 5367 */         if (Constants.isIt360)
/*      */         {
/* 5369 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP NOT IN " + Constants.nonApplicationResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' " + condition + " order by mo.displayname";
/*      */         }
/*      */       }
/* 5372 */       else if ((type.equalsIgnoreCase("servers")) || (type.equalsIgnoreCase("$ComplexType_AllSers")))
/*      */       {
/* 5374 */         if (isUserResourceEnabled) {
/* 5375 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' order by mo.displayname";
/*      */         } else {
/* 5377 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP IN " + Constants.serverResGpTypes + " AND mo.TYPE NOT LIKE '%OpManager%' " + condition + " order by mo.displayname";
/*      */         }
/*      */       }
/* 5380 */       else if (type.equals("All"))
/*      */       {
/* 5382 */         if (isUserResourceEnabled) {
/* 5383 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mo.TYPE NOT IN ('Network','HAI') " + eumCondition + " order by mo.displayname";
/*      */         } else {
/* 5385 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mo.TYPE NOT IN ('Network','HAI') " + condition + eumCondition + " order by mo.displayname";
/*      */         }
/*      */       }
/* 5388 */       else if (type.equals("$ComplexType_NetworkDevices"))
/*      */       {
/* 5390 */         if (isUserResourceEnabled) {
/* 5391 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP not in ('OpManager-Interface') and mrt.SUBGROUP like 'OpManager%' and " + condition + " order by mo.displayname";
/*      */         } else {
/* 5393 */           query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP not in ('OpManager-Interface') and mrt.SUBGROUP like 'OpManager%' " + condition + " order by mo.displayname";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 5398 */       else if (isUserResourceEnabled) {
/* 5399 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' " + eumCondition + " order by mo.displayname";
/*      */       } else {
/* 5401 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' " + condition + eumCondition + " order by mo.displayname";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5406 */     if ((startIndex != -1) && (noOfRows != -1))
/*      */     {
/* 5408 */       String totalQuery = DBUtil.getCountQuery(query);
/* 5409 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 5410 */       query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "mo.displayname");
/*      */     }
/*      */     
/*      */ 
/* 5414 */     return getAvailabilityHistory(query, period);
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getAvailabilityHistoryByCategoryType(String type, int period, String owner, String role, Vector resIds_vector)
/*      */   {
/* 5420 */     return getAvailabilityHistoryByCategoryType(type, period, owner, role, resIds_vector, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryByCategoryType(String type, int period, String owner, String role) {
/* 5424 */     return getAvailabilityHistoryByCategoryType(type, period, owner, role, null, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryByCategoryType(String type, int period, String owner, String role, Vector resIds_vector, int startIndex, int noOfRows, HttpServletRequest request) {
/* 5428 */     String query = "";
/*      */     
/* 5430 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 5432 */       query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and mo.TYPE not like 'OpManager-Interface%'";
/*      */     }
/*      */     else
/*      */     {
/* 5436 */       String condition = "";
/*      */       
/* 5438 */       if (!Constants.isUserResourceEnabled()) {
/* 5439 */         condition = getQueryCondition("mo.RESOURCEID", owner);
/*      */       }
/*      */       
/*      */ 
/* 5443 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (resIds_vector != null))
/*      */       {
/* 5445 */         condition = getCondition("mo.RESOURCEID", resIds_vector);
/*      */       }
/*      */       
/* 5448 */       if (Constants.isUserResourceEnabled()) {
/* 5449 */         String loginUserid = Constants.getLoginUserid(request);
/* 5450 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and mo.TYPE not like 'OpManager-Interface%' ";
/*      */       } else {
/* 5452 */         query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and " + condition + " and mo.TYPE not like 'OpManager-Interface%'";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 5458 */     if ((startIndex != -1) && (noOfRows != -1))
/*      */     {
/* 5460 */       String totalQuery = DBUtil.getCountQuery(query);
/* 5461 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 5462 */       query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "mo.displayname");
/*      */     }
/*      */     
/* 5465 */     return getAvailabilityHistory(query, period);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryById(String query, int period, boolean getEUMChildAvailability) {
/* 5469 */     return getAvailabilityHistory(query, period, getEUMChildAvailability);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryById(int haid, int period)
/*      */   {
/* 5474 */     String query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_PARENTCHILDMAPPER pcm where pcm.parentid=" + haid + " and pcm.childid=mo.resourceid";
/*      */     
/* 5476 */     return getAvailabilityHistory(query, period);
/*      */   }
/*      */   
/*      */   public static Hashtable getAvailabilityHistoryById(int haid, int period, boolean isowner, String username, HttpServletRequest request) {
/* 5480 */     String query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_PARENTCHILDMAPPER pcm,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable  where pcm.parentid=" + haid + " and pcm.childid=mo.resourceid and AM_UserPasswordTable.USERNAME like '" + username + "' and USERID=OWNERID AND CHILDID=HAID and type like 'HAI'";
/* 5481 */     if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 5482 */       String loginUserid = Constants.getLoginUserid(request);
/* 5483 */       query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_PARENTCHILDMAPPER pcm,AM_USERRESOURCESTABLE  where pcm.parentid=" + haid + " and pcm.childid=mo.resourceid and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and pcm.CHILDID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and type like 'HAI'";
/*      */     }
/* 5485 */     return getAvailabilityHistory(query, period);
/*      */   }
/*      */   
/*      */   private static Hashtable getGenericAvailability(String query, int period) {
/* 5489 */     return getGenericAvailability(query, period, false);
/*      */   }
/*      */   
/*      */   private static Hashtable getGenericAvailability(String query, int period, boolean addType)
/*      */   {
/* 5494 */     toret = new Hashtable();
/*      */     
/* 5496 */     ResultSet rs = null;
/*      */     try {
/* 5498 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 5499 */       while (rs.next()) {
/* 5500 */         int resid = rs.getInt("resourceid");
/* 5501 */         String dispname = rs.getString("displayname");
/* 5502 */         dispname = EnterpriseUtil.decodeString(dispname);
/*      */         
/* 5504 */         if (addType)
/*      */         {
/* 5506 */           String type = rs.getString("type");
/* 5507 */           toret.put(type + "$" + resid + "#" + dispname, getAvailabilityHistory(resid, period));
/*      */         }
/*      */         else
/*      */         {
/* 5511 */           toret.put(resid + "#" + dispname, getAvailabilityHistory(resid, period));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5526 */       return toret;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5515 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 5518 */       if (rs != null) {
/*      */         try {
/* 5520 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 5522 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static ArrayList getAvailabilityHistory(int resid, int period)
/*      */   {
/* 5530 */     long starttime = getStartTime(period);
/* 5531 */     long endtime = getEndTime(period);
/*      */     
/* 5533 */     toret = new ArrayList();
/* 5534 */     long dcstarttime = getMinTimeInDB(String.valueOf(resid));
/* 5535 */     long prevuptime = starttime;
/* 5536 */     long timeperiod = endtime - starttime;
/* 5537 */     if (dcstarttime > starttime) {
/* 5538 */       prevuptime = dcstarttime;
/* 5539 */       Hashtable props = new Hashtable();
/* 5540 */       float persc = Math.round((float)(dcstarttime - starttime) / (float)timeperiod * 10000.0F) / 100.0F;
/* 5541 */       props.put("STATUS", "NO_DC");
/* 5542 */       props.put("PERCENTAGE", Float.valueOf(persc));
/* 5543 */       props.put("STARTTIME", new java.util.Date(starttime));
/* 5544 */       props.put("ENDTIME", new java.util.Date(dcstarttime));
/*      */       
/* 5546 */       toret.add(props);
/*      */     }
/* 5548 */     String query = "select dd.* from AM_MO_DowntimeData dd where dd.RESID=" + resid + " and (dd.DOWNTIME>" + starttime + " or dd.UPTIME>" + starttime + " or dd.UPTIME=0) and dd.DOWNTIME<" + endtime + " order by DOWNTIME";
/* 5549 */     if ("true".equals(Constants.addMaintenanceToAvailablity)) {
/* 5550 */       query = "select dd.* from AM_MO_DowntimeData dd where dd.RESID=" + resid + " and TYPE=1 and (dd.DOWNTIME>" + starttime + " or dd.UPTIME>" + starttime + " or dd.UPTIME=0) and dd.DOWNTIME<" + endtime + " order by DOWNTIME";
/*      */     }
/* 5552 */     boolean nodowntime = true;
/* 5553 */     ResultSet rs = null;
/*      */     try {
/* 5555 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 5556 */       while (rs.next()) {
/* 5557 */         nodowntime = false;
/* 5558 */         long uptime = rs.getLong("UPTIME");
/* 5559 */         long downtime = rs.getLong("DOWNTIME");
/* 5560 */         int typeid = rs.getInt("TYPE");
/* 5561 */         if (downtime < starttime) {
/* 5562 */           downtime = starttime;
/*      */         }
/* 5564 */         if (uptime == 0L) {
/* 5565 */           uptime = endtime;
/*      */         }
/* 5567 */         long available_period = downtime - prevuptime;
/*      */         
/* 5569 */         if (available_period > 0L) {
/* 5570 */           Hashtable props = new Hashtable();
/* 5571 */           props.put("STATUS", "AVAILABALE");
/* 5572 */           props.put("PERCENTAGE", Float.valueOf(Math.round((float)available_period / (float)timeperiod * 10000.0F) / 100.0F));
/* 5573 */           props.put("STARTTIME", new java.util.Date(prevuptime));
/* 5574 */           props.put("ENDTIME", new java.util.Date(downtime));
/* 5575 */           props.put("TYPE", Integer.valueOf(typeid));
/* 5576 */           toret.add(props);
/*      */         }
/* 5578 */         Hashtable props = new Hashtable();
/* 5579 */         props.put("STATUS", "UNAVAILABLE");
/* 5580 */         props.put("PERCENTAGE", Float.valueOf(Math.round((float)(uptime - downtime) / (float)timeperiod * 10000.0F) / 100.0F));
/* 5581 */         props.put("STARTTIME", new java.util.Date(downtime));
/* 5582 */         props.put("ENDTIME", new java.util.Date(uptime));
/* 5583 */         props.put("TYPE", Integer.valueOf(typeid));
/* 5584 */         toret.add(props);
/*      */         
/* 5586 */         prevuptime = uptime;
/*      */       }
/* 5588 */       if (nodowntime)
/*      */       {
/* 5590 */         if (dcstarttime < starttime)
/*      */         {
/* 5592 */           Hashtable props = new Hashtable();
/* 5593 */           props.put("STATUS", "AVAILABALE");
/* 5594 */           props.put("PERCENTAGE", Float.valueOf(100.0F));
/* 5595 */           props.put("STARTTIME", new java.util.Date(starttime));
/* 5596 */           props.put("ENDTIME", new java.util.Date(System.currentTimeMillis()));
/* 5597 */           toret.add(props);
/*      */         }
/*      */         else
/*      */         {
/* 5601 */           float persc = Math.round((float)(dcstarttime - starttime) / (float)timeperiod * 10000.0F) / 100.0F;
/* 5602 */           Hashtable props = new Hashtable();
/* 5603 */           props.put("STATUS", "AVAILABALE");
/* 5604 */           props.put("PERCENTAGE", Float.valueOf(Math.round((100.0F - persc) * 100.0F) / 100.0F));
/* 5605 */           props.put("STARTTIME", new java.util.Date(dcstarttime));
/* 5606 */           props.put("ENDTIME", new java.util.Date(System.currentTimeMillis()));
/* 5607 */           toret.add(props);
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 5613 */         float persc = Math.round((float)(endtime - prevuptime) / (float)timeperiod * 10000.0F) / 100.0F;
/* 5614 */         if (persc > 0.0F)
/*      */         {
/* 5616 */           Hashtable props = new Hashtable();
/* 5617 */           props.put("STATUS", "AVAILABALE");
/* 5618 */           props.put("PERCENTAGE", Float.valueOf(persc));
/* 5619 */           props.put("STARTTIME", new java.util.Date(prevuptime));
/* 5620 */           props.put("ENDTIME", new java.util.Date(endtime));
/* 5621 */           toret.add(props);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5636 */       return toret;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5625 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 5628 */       if (rs != null) {
/*      */         try {
/* 5630 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 5632 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static Hashtable getAvailabilityHistory(String query, int period)
/*      */   {
/* 5641 */     return getAvailabilityHistory(query, period, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Hashtable getAvailabilityHistory(String query, int period, boolean getAvailabilityForEUMChild)
/*      */   {
/* 5648 */     Hashtable totalavailability = new Hashtable();
/* 5649 */     Hashtable prevuptimeidMap = new Hashtable();
/* 5650 */     ArrayList nodowntimelist = new ArrayList();
/* 5651 */     long starttime = getStartTime(period);
/* 5652 */     long endtime = getEndTime(period);
/* 5653 */     DBUtil mo = new DBUtil();
/* 5654 */     ResultSet residset = null;
/* 5655 */     ArrayList eumChildList = new ArrayList();
/* 5656 */     if (!Constants.sqlManager) {
/* 5657 */       eumChildList = Constants.getEUMChildList();
/*      */     }
/* 5659 */     StringBuilder residlist = new StringBuilder();
/*      */     try {
/* 5661 */       residlist.append("-1");
/* 5662 */       residset = AMConnectionPool.executeQueryStmt(query);
/* 5663 */       String rid = "";
/* 5664 */       if (residset.next()) {
/* 5665 */         rid = residset.getString(1);
/* 5666 */         if ((!eumChildList.contains(rid)) || (getAvailabilityForEUMChild))
/*      */         {
/* 5668 */           residlist.append(",").append(rid);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5674 */       while (residset.next())
/*      */       {
/* 5676 */         rid = residset.getString(1);
/* 5677 */         if ((!eumChildList.contains(rid)) || (getAvailabilityForEUMChild))
/*      */         {
/* 5679 */           residlist.append(",").append(rid);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 5686 */         if (residset != null) {
/* 5687 */           AMConnectionPool.closeStatement(residset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/* 5692 */       mintimeQuery = "select resourceid,min(CREATIONTIME)as CREATIONTIME,type,displayname from AM_ManagedObject where resourceid in (" + residlist + ") group by resourceid,type,displayname";
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5683 */       exc.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 5686 */         if (residset != null) {
/* 5687 */           AMConnectionPool.closeStatement(residset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     String mintimeQuery;
/* 5693 */     ResultSet mintimeset = null;
/* 5694 */     Hashtable idmintimeMap = new Hashtable();
/*      */     try {
/* 5696 */       mintimeset = AMConnectionPool.executeQueryStmt(mintimeQuery);
/* 5697 */       while (mintimeset.next()) {
/* 5698 */         ArrayList mondetail = new ArrayList();
/* 5699 */         idmintimeMap.put(mintimeset.getString(1), mondetail);
/* 5700 */         mondetail.add(Long.valueOf(mintimeset.getLong(2)));
/* 5701 */         mondetail.add(mintimeset.getString(3));
/* 5702 */         mondetail.add(mintimeset.getString(4));
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 5708 */         if (mintimeset != null) {
/* 5709 */           AMConnectionPool.closeStatement(mintimeset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/* 5715 */       prevuptime = starttime;
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5705 */       exc.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 5708 */         if (mintimeset != null) {
/* 5709 */           AMConnectionPool.closeStatement(mintimeset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     
/*      */     long prevuptime;
/* 5716 */     long timeperiod = endtime - starttime;
/* 5717 */     ResultSet downtimeset = null;
/* 5718 */     Hashtable downtimetable = new Hashtable();
/* 5719 */     String downtimequery = "select dd.* from AM_MO_DowntimeData dd where dd.RESID in (" + residlist + ") and (dd.DOWNTIME>" + starttime + " or dd.UPTIME>" + starttime + " or dd.UPTIME=0) and dd.DOWNTIME<" + endtime + " order by RESID,DOWNTIME";
/* 5720 */     if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */     {
/* 5722 */       downtimequery = "select dd.* from AM_MO_DowntimeData dd where dd.RESID in (" + residlist + ") and TYPE=1 and (dd.DOWNTIME>" + starttime + " or dd.UPTIME>" + starttime + " or dd.UPTIME=0) and dd.DOWNTIME<" + endtime + " order by RESID,DOWNTIME";
/*      */     }
/*      */     
/*      */ 
/* 5726 */     System.out.println("downtimequery:" + downtimequery);
/*      */     try {
/* 5728 */       downtimeset = AMConnectionPool.executeQueryStmt(downtimequery);
/*      */       
/* 5730 */       while (downtimeset.next()) {
/* 5731 */         Long[] downtimearr = new Long[4];
/* 5732 */         downtimearr[0] = Long.valueOf(downtimeset.getLong(2));
/* 5733 */         downtimearr[1] = Long.valueOf(downtimeset.getLong(3));
/* 5734 */         downtimearr[2] = Long.valueOf(downtimeset.getLong(4));
/* 5735 */         downtimearr[3] = Long.valueOf(downtimeset.getLong(5));
/*      */         
/* 5737 */         String downtimeresource = downtimeset.getString(1);
/* 5738 */         ArrayList downtimelist = (ArrayList)downtimetable.get(downtimeresource);
/* 5739 */         if (downtimelist != null) {
/* 5740 */           downtimelist.add(downtimearr);
/*      */         } else {
/* 5742 */           downtimelist = new ArrayList();
/* 5743 */           downtimelist.add(downtimearr);
/* 5744 */           downtimetable.put(downtimeresource, downtimelist);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 5751 */         if (downtimeset != null) {
/* 5752 */           AMConnectionPool.closeStatement(downtimeset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/* 5758 */       mintimekeys = idmintimeMap.keys();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5748 */       exc.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 5751 */         if (downtimeset != null) {
/* 5752 */           AMConnectionPool.closeStatement(downtimeset);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     
/*      */     Enumeration mintimekeys;
/* 5759 */     while (mintimekeys.hasMoreElements()) {
/* 5760 */       ArrayList monitordata = new ArrayList();
/* 5761 */       String mintimeresid = (String)mintimekeys.nextElement();
/* 5762 */       ArrayList mondetail = (ArrayList)idmintimeMap.get(mintimeresid);
/* 5763 */       long dcstarttime = ((Long)mondetail.get(0)).longValue();
/* 5764 */       String type = (String)mondetail.get(1);
/* 5765 */       String dispname = (String)mondetail.get(2);
/* 5766 */       if (!totalavailability.containsKey(type + "$" + mintimeresid + "#" + dispname)) {
/* 5767 */         totalavailability.put(type + "$" + mintimeresid + "#" + dispname, monitordata);
/*      */       }
/* 5769 */       prevuptime = starttime;
/* 5770 */       if (dcstarttime > starttime) {
/* 5771 */         prevuptime = dcstarttime;
/* 5772 */         Hashtable props = new Hashtable();
/* 5773 */         float persc = Math.round((float)(dcstarttime - starttime) / (float)timeperiod * 10000.0F) / 100.0F;
/* 5774 */         props.put("STATUS", "NO_DC");
/* 5775 */         props.put("PERCENTAGE", Float.valueOf(persc));
/* 5776 */         props.put("STARTTIME", new java.util.Date(starttime));
/* 5777 */         props.put("ENDTIME", new java.util.Date(dcstarttime));
/*      */         
/* 5779 */         monitordata.add(props);
/*      */       }
/* 5781 */       prevuptimeidMap.put(mintimeresid, Long.valueOf(prevuptime));
/*      */       
/* 5783 */       if (!downtimetable.containsKey(mintimeresid)) {
/* 5784 */         nodowntimelist.add(mintimeresid);
/*      */       } else {
/* 5786 */         ArrayList downtimelist = (ArrayList)downtimetable.get(mintimeresid);
/* 5787 */         for (int i = 0; i < downtimelist.size(); i++) {
/* 5788 */           long typeid = 0L;
/* 5789 */           long reasonid = -1L;
/* 5790 */           Long[] downtimerow = (Long[])downtimelist.get(i);
/* 5791 */           long downtime = downtimerow[0].longValue();
/* 5792 */           long uptime = downtimerow[1].longValue();
/* 5793 */           typeid = downtimerow[2].longValue();
/* 5794 */           reasonid = downtimerow[3].longValue();
/* 5795 */           if (downtime < starttime) {
/* 5796 */             downtime = starttime;
/*      */           }
/* 5798 */           if (uptime == 0L) {
/* 5799 */             uptime = endtime;
/*      */           }
/* 5801 */           long available_period = downtime - prevuptime;
/*      */           
/* 5803 */           if (available_period > 0L) {
/* 5804 */             Hashtable props = new Hashtable();
/* 5805 */             props.put("STATUS", "AVAILABALE");
/*      */             
/* 5807 */             props.put("PERCENTAGE", Float.valueOf(Math.round((float)available_period / (float)timeperiod * 10000.0F) / 100.0F));
/* 5808 */             props.put("STARTTIME", new java.util.Date(prevuptime));
/* 5809 */             props.put("ENDTIME", new java.util.Date(downtime));
/* 5810 */             monitordata.add(props);
/*      */           }
/* 5812 */           Hashtable props = new Hashtable();
/* 5813 */           props.put("STATUS", "UNAVAILABLE");
/*      */           
/* 5815 */           props.put("PERCENTAGE", Float.valueOf(Math.round((float)(uptime - downtime) / (float)timeperiod * 10000.0F) / 100.0F));
/* 5816 */           props.put("STARTTIME", new java.util.Date(downtime));
/* 5817 */           props.put("ENDTIME", new java.util.Date(uptime));
/* 5818 */           props.put("TYPE", Long.valueOf(typeid));
/* 5819 */           props.put("REASONID", Long.valueOf(reasonid));
/* 5820 */           monitordata.add(props);
/* 5821 */           prevuptime = uptime;
/* 5822 */           prevuptimeidMap.put(mintimeresid, Long.valueOf(prevuptime));
/*      */         }
/*      */         
/* 5825 */         long monprevuptime = ((Long)prevuptimeidMap.get(mintimeresid)).longValue();
/*      */         
/* 5827 */         float persc = Math.round((float)(endtime - monprevuptime) / (float)timeperiod * 10000.0F) / 100.0F;
/* 5828 */         if (persc > 0.0F)
/*      */         {
/* 5830 */           Hashtable props = new Hashtable();
/* 5831 */           props.put("STATUS", "AVAILABALE");
/* 5832 */           props.put("PERCENTAGE", Float.valueOf(persc));
/* 5833 */           props.put("STARTTIME", new java.util.Date(monprevuptime));
/* 5834 */           props.put("ENDTIME", new java.util.Date(endtime));
/* 5835 */           monitordata.add(props);
/*      */         }
/* 5837 */         downtimetable.remove(mintimeresid);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5842 */     for (int i = 0; i < nodowntimelist.size(); i++) {
/* 5843 */       ArrayList mondetail = (ArrayList)idmintimeMap.get(nodowntimelist.get(i));
/* 5844 */       String nodowntimemonitor = (String)mondetail.get(1) + "$" + nodowntimelist.get(i) + "#" + mondetail.get(2);
/* 5845 */       long dcstarttime = ((Long)mondetail.get(0)).longValue();
/* 5846 */       ArrayList monitoravailability = (ArrayList)totalavailability.get(nodowntimemonitor);
/* 5847 */       if (dcstarttime < starttime)
/*      */       {
/* 5849 */         Hashtable props = new Hashtable();
/* 5850 */         props.put("STATUS", "AVAILABALE");
/* 5851 */         props.put("PERCENTAGE", Float.valueOf(100.0F));
/* 5852 */         props.put("STARTTIME", new java.util.Date(starttime));
/* 5853 */         props.put("ENDTIME", new java.util.Date(System.currentTimeMillis()));
/*      */         
/* 5855 */         if (monitoravailability != null) {
/* 5856 */           monitoravailability.add(props);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 5861 */         float persc = Math.round((float)(dcstarttime - starttime) / (float)timeperiod * 10000.0F) / 100.0F;
/*      */         
/* 5863 */         Hashtable props = new Hashtable();
/* 5864 */         props.put("STATUS", "AVAILABALE");
/* 5865 */         props.put("PERCENTAGE", Float.valueOf(Math.round((100.0F - persc) * 100.0F) / 100.0F));
/* 5866 */         props.put("STARTTIME", new java.util.Date(dcstarttime));
/* 5867 */         props.put("ENDTIME", new java.util.Date(System.currentTimeMillis()));
/*      */         
/* 5869 */         if (monitoravailability != null) {
/* 5870 */           monitoravailability.add(props);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 5875 */     return totalavailability;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Hashtable getallMOAvailabilityHistory(int period)
/*      */   {
/* 5885 */     String query = "select mo.resourceid,mo.displayname from AM_ManagedObject as mo where mo.TYPE in " + Constants.resourceTypes;
/*      */     
/* 5887 */     if (Constants.sqlManager)
/*      */     {
/* 5889 */       query = "select mo.resourceid,mo.displayname from AM_ManagedObject as mo where mo.TYPE in " + Constants.sqlManagerresourceTypes;
/*      */     }
/*      */     
/*      */ 
/* 5893 */     return getAvailabilityHistory(query, period);
/*      */   }
/*      */   
/*      */   public static long getStartTime(int period) {
/* 5897 */     if (period == 1) {
/* 5898 */       long t = System.currentTimeMillis() - 86400000L;
/* 5899 */       Calendar cal = new GregorianCalendar();
/* 5900 */       cal.setTime(new java.util.Date(t));
/* 5901 */       cal.set(12, 0);
/* 5902 */       cal.set(13, 0);
/* 5903 */       cal.set(14, 0);
/* 5904 */       return cal.getTime().getTime();
/*      */     }
/* 5906 */     if (period == 4) {
/* 5907 */       long t = System.currentTimeMillis() - 21600000L;
/* 5908 */       Calendar cal = new GregorianCalendar();
/* 5909 */       cal.setTime(new java.util.Date(t));
/* 5910 */       cal.set(12, 0);
/* 5911 */       cal.set(13, 0);
/* 5912 */       cal.set(14, 0);
/* 5913 */       return cal.getTime().getTime();
/*      */     }
/* 5915 */     if (period == 7) {
/* 5916 */       long t = System.currentTimeMillis() - 604800000L;
/* 5917 */       Calendar cal = new GregorianCalendar();
/* 5918 */       cal.setTime(new java.util.Date(t));
/* 5919 */       cal.set(11, 0);
/* 5920 */       cal.set(12, 0);
/* 5921 */       cal.set(13, 0);
/* 5922 */       cal.set(14, 0);
/* 5923 */       return cal.getTime().getTime();
/*      */     }
/* 5925 */     if (period == 2) {
/* 5926 */       long t = System.currentTimeMillis() - 2592000000L;
/* 5927 */       Calendar cal = new GregorianCalendar();
/* 5928 */       cal.setTime(new java.util.Date(t));
/* 5929 */       cal.set(11, 0);
/* 5930 */       cal.set(12, 0);
/* 5931 */       cal.set(13, 0);
/* 5932 */       cal.set(14, 0);
/* 5933 */       return cal.getTime().getTime();
/*      */     }
/* 5935 */     if (period == 3) {
/* 5936 */       long t = System.currentTimeMillis();
/* 5937 */       Calendar cal = new GregorianCalendar();
/* 5938 */       cal.setTime(new java.util.Date(t));
/* 5939 */       cal.add(7, -1);
/* 5940 */       cal.set(11, 0);
/* 5941 */       cal.set(13, 0);
/* 5942 */       cal.set(14, 0);
/* 5943 */       cal.set(12, 0);
/* 5944 */       return cal.getTime().getTime();
/*      */     }
/*      */     
/* 5947 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public static long getEndTime(int period)
/*      */   {
/* 5952 */     if ((period == 1) || (period == 4)) {
/* 5953 */       return System.currentTimeMillis();
/*      */     }
/* 5955 */     if ((period == 2) || (period == 7)) {
/* 5956 */       return System.currentTimeMillis();
/*      */     }
/* 5958 */     if (period == 3) {
/* 5959 */       long curTime = System.currentTimeMillis();
/* 5960 */       java.util.Date currentTimeAsDate = new java.util.Date(curTime);
/* 5961 */       Calendar cldr = new GregorianCalendar();
/* 5962 */       cldr.setTime(currentTimeAsDate);
/* 5963 */       cldr.set(11, 0);
/* 5964 */       cldr.set(13, 0);
/* 5965 */       cldr.set(14, 0);
/* 5966 */       cldr.set(12, 0);
/* 5967 */       return cldr.getTime().getTime();
/*      */     }
/*      */     
/* 5970 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */ 
/*      */   public static long getRoundedEndTime(int span)
/*      */   {
/* 5976 */     long endtime = 0L;
/* 5977 */     if ((span == 1) || (span == 4))
/*      */     {
/* 5979 */       Calendar cldr = Calendar.getInstance();
/* 5980 */       cldr.set(13, 0);
/* 5981 */       cldr.set(14, 0);
/* 5982 */       cldr.set(12, 0);
/* 5983 */       endtime = cldr.getTime().getTime();
/*      */     }
/* 5985 */     else if (span == 2)
/*      */     {
/* 5987 */       Calendar cldr = Calendar.getInstance();
/* 5988 */       cldr.set(13, 0);
/* 5989 */       cldr.set(14, 0);
/* 5990 */       cldr.set(12, 0);
/* 5991 */       cldr.set(11, 0);
/* 5992 */       endtime = cldr.getTime().getTime();
/*      */     }
/*      */     
/* 5995 */     return endtime;
/*      */   }
/*      */   
/*      */   public static String getQueryCondition(String coloumn, String owner) {
/* 5999 */     return getQueryCondition(coloumn, owner, null);
/*      */   }
/*      */   
/*      */   public static String getQueryCondition(String coloumn, String owner, HttpServletRequest request) {
/* 6003 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 6005 */       return SegmentReportUtil.getQueryCondition(coloumn, owner, request);
/*      */     }
/*      */     
/*      */ 
/* 6009 */     return SegmentReportUtil.getQueryCondition(coloumn, owner);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryForMGs(String type, int span, String owner, String role)
/*      */   {
/* 6014 */     return getHealthHistoryForMGs(type, span, owner, role, null);
/*      */   }
/*      */   
/*      */   private static HashMap<String, String> customFieldMGs(String widgetid)
/*      */   {
/* 6019 */     HashMap<String, String> customField = new HashMap();
/*      */     
/*      */ 
/* 6022 */     String aliasname = "";
/* 6023 */     String value = "";
/* 6024 */     String qryCon = "";
/* 6025 */     ResultSet res = null;
/*      */     try {
/* 6027 */       res = AMConnectionPool.executeQueryStmt("select VALUE,ALIASNAME,FIELDTYPE from AM_MYFIELDS_METADATA as me, AM_MYPAGE_WIDGET_CUSTOMFIELD as cs where me.fieldid=cs.fieldid and cs.widgetid=" + widgetid);
/*      */       
/* 6029 */       if (res.next())
/*      */       {
/* 6031 */         aliasname = res.getString("ALIASNAME");
/* 6032 */         value = res.getString("VALUE");
/*      */       }
/* 6034 */       customField = MyFields.customCondition(aliasname, value, null, false);
/*      */     }
/*      */     catch (Exception ex) {
/* 6037 */       ex.printStackTrace();
/*      */     } finally {
/* 6039 */       if (res != null) {
/* 6040 */         AMConnectionPool.closeStatement(res);
/*      */       }
/*      */     }
/* 6043 */     return customField;
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryForMGs(String type, int span, String owner, String role, HttpServletRequest request) {
/* 6047 */     String mqry = "";
/* 6048 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 6050 */       mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_HOLISTICAPPLICATION ha  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' and mo.resourceid=ha.haid and ha.type=0 order by mo.RESOURCEID";
/*      */       
/* 6052 */       String selecttionType = request.getParameter("selecttionType");
/* 6053 */       if ((selecttionType != null) && (selecttionType.equals("CUSTOM FIELDS")))
/*      */       {
/* 6055 */         String widgetid = request.getParameter("widgetid");
/* 6056 */         HashMap<String, String> customValues = customFieldMGs(widgetid);
/*      */         
/* 6058 */         String qryCon = (String)customValues.get("groupQuery");
/* 6059 */         String datatable = (String)customValues.get("groupTable");
/*      */         
/* 6061 */         String isSubGroup = request.getParameter("subgroup");
/* 6062 */         String enableSubGroup = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/* 6063 */         mqry = "select AM_HOLISTICAPPLICATION.HAID,DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by AM_HOLISTICAPPLICATION.HAID";
/* 6064 */         if ("1".equals(isSubGroup)) {
/* 6065 */           enableSubGroup = "";
/* 6066 */           mqry = "select AM_HOLISTICAPPLICATION.HAID,DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " order by AM_HOLISTICAPPLICATION.HAID";
/* 6067 */           Vector<String> getsubgroups = MyFields.listMgSubGroups(mqry, datatable, "HAID", qryCon);
/* 6068 */           mqry = "select RESOURCEID, DISPLAYNAME,TYPE from AM_ManagedObject where " + EnterpriseUtil.getCondition("RESOURCEID", getsubgroups);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 6075 */       String condition = "";
/* 6076 */       String bsgType = "0";
/* 6077 */       if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/* 6079 */         bsgType = "1";
/* 6080 */         condition = getQueryCondition("mo.RESOURCEID", owner, request);
/*      */       }
/*      */       else
/*      */       {
/* 6084 */         if (!Constants.isUserResourceEnabled()) {
/* 6085 */           condition = getQueryCondition("mo.RESOURCEID", owner);
/*      */         }
/*      */         
/* 6088 */         String selecttionType = request.getParameter("selecttionType");
/* 6089 */         if ((selecttionType != null) && (selecttionType.equals("CUSTOM FIELDS")))
/*      */         {
/* 6091 */           String widgetid = request.getParameter("widgetid");
/* 6092 */           HashMap<String, String> customValues = customFieldMGs(widgetid);
/*      */           
/* 6094 */           String qryCon = (String)customValues.get("groupQuery");
/* 6095 */           String datatable = (String)customValues.get("groupTable");
/*      */           
/* 6097 */           String isSubGroup = request.getParameter("subgroup");
/* 6098 */           String enableSubGroup = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/* 6099 */           if ("1".equals(isSubGroup)) {
/* 6100 */             enableSubGroup = "";
/*      */           }
/*      */           
/* 6103 */           mqry = "select AM_HOLISTICAPPLICATION.HAID,DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION," + datatable + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + enableSubGroup + " " + qryCon + " and " + condition + " order by AM_HOLISTICAPPLICATION.HAID";
/*      */         }
/*      */       }
/* 6106 */       if (Constants.isUserResourceEnabled()) {
/* 6107 */         String loginUserid = Constants.getLoginUserid(request);
/* 6108 */         mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_HOLISTICAPPLICATION ha  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' and mo.resourceid=ha.haid and ha.type=" + bsgType + "  order by mo.RESOURCEID";
/*      */       } else {
/* 6110 */         mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_HOLISTICAPPLICATION ha  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "' and mo.resourceid=ha.haid and ha.type=" + bsgType + " and " + condition + " order by mo.RESOURCEID";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 6116 */     return getGenericHealthHistory(mqry, span, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getHealthHistoryByType(String type, int span, String owner, String role, Vector resIds_vector)
/*      */   {
/* 6122 */     return getHealthHistoryByType(type, span, owner, role, resIds_vector, -1, -1, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getHealthHistoryByType(String type, int span, String owner, String role)
/*      */   {
/* 6128 */     return getHealthHistoryByType(type, span, owner, role, null, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryByType(String type, int span, String owner, String role, Vector resIds_vector, int startIndex, int noOfRows, HttpServletRequest request)
/*      */   {
/* 6133 */     String mqry = "";
/* 6134 */     String eumCondition = "";
/*      */     
/* 6136 */     if ((request != null) && (("All".equalsIgnoreCase(request.getParameter("type"))) || (Constants.resourceTypesEUM.contains(type))))
/*      */     {
/*      */ 
/*      */ 
/* 6140 */       eumCondition = " and ad.resourceid NOT IN (" + Constants.getEUMChildString() + ") ";
/*      */     }
/* 6142 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 6144 */       if ((type.equalsIgnoreCase("applications")) || (type.equalsIgnoreCase("$ComplexType_AllApps")))
/*      */       {
/* 6146 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP NOT IN " + Constants.serverTypes + eumCondition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/* 6147 */         if (Constants.isIt360)
/*      */         {
/* 6149 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.RESOURCEGROUP NOT IN " + Constants.nonApplicationResGpTypes + eumCondition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         }
/*      */       }
/* 6152 */       else if ((type.equalsIgnoreCase("servers")) || (type.equalsIgnoreCase("$ComplexType_AllSers")))
/*      */       {
/* 6154 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.RESOURCEGROUP IN " + Constants.serverResGpTypes + eumCondition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */       }
/* 6156 */       else if (type.equals("All"))
/*      */       {
/* 6158 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and ad.TYPE NOT IN ('Network','HAI') and bd.SUBGROUP not in ('OpManager-Interface') " + eumCondition + " order by ad.DISPLAYNAME";
/*      */       }
/* 6160 */       else if (type.equals("$ComplexType_NetworkDevices"))
/*      */       {
/*      */ 
/* 6163 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP not in ('OpManager-Interface') and bd.SUBGROUP like 'OpManager%' order by ad.DISPLAYNAME";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 6168 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP='" + type + "'" + eumCondition + " order by ad.DISPLAYNAME";
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 6173 */       String condition = "";
/* 6174 */       boolean isUserResourceEnabled = false;
/* 6175 */       String loginUserid = null;
/* 6176 */       if (Constants.isUserResourceEnabled()) {
/* 6177 */         isUserResourceEnabled = true;
/* 6178 */         loginUserid = Constants.getLoginUserid(request);
/*      */       } else {
/* 6180 */         condition = getQueryCondition("ad.RESOURCEID", owner);
/*      */       }
/*      */       
/* 6183 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (resIds_vector != null))
/*      */       {
/* 6185 */         condition = getCondition("ad.RESOURCEID", resIds_vector);
/* 6186 */         condition = condition + eumCondition;
/*      */       }
/*      */       
/* 6189 */       if ((type.equalsIgnoreCase("applications")) || (type.equalsIgnoreCase("$ComplexType_AllApps")))
/*      */       {
/* 6191 */         if (isUserResourceEnabled) {
/* 6192 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=ad.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP NOT IN " + Constants.serverTypes + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         } else {
/* 6194 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP NOT IN " + Constants.serverTypes + " and " + condition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         }
/*      */         
/* 6197 */         if (Constants.isIt360)
/*      */         {
/* 6199 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.RESOURCEGROUP NOT IN " + Constants.nonApplicationResGpTypes + " and " + condition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         }
/*      */       }
/* 6202 */       else if ((type.equalsIgnoreCase("servers")) || (type.equalsIgnoreCase("$ComplexType_AllSers")))
/*      */       {
/* 6204 */         if (isUserResourceEnabled) {
/* 6205 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=ad.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ad.TYPE=bd.RESOURCETYPE and bd.RESOURCEGROUP IN " + Constants.serverResGpTypes + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         } else {
/* 6207 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.RESOURCEGROUP IN " + Constants.serverResGpTypes + " and " + condition + " AND bd.SUBGROUP NOT LIKE '%OpManager%' order by ad.DISPLAYNAME";
/*      */         }
/*      */       }
/* 6210 */       else if (type.equals("All"))
/*      */       {
/* 6212 */         if (isUserResourceEnabled) {
/* 6213 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=ad.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ad.TYPE=bd.RESOURCETYPE and ad.TYPE NOT IN ('Network','HAI') " + eumCondition + " order by ad.DISPLAYNAME";
/*      */         } else {
/* 6215 */           condition = condition.trim().length() > 0 ? " and " + condition : condition;
/* 6216 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and ad.TYPE NOT IN ('Network','HAI') " + condition + eumCondition + " order by ad.DISPLAYNAME";
/*      */         }
/* 6218 */       } else if (type.equals("$ComplexType_NetworkDevices"))
/*      */       {
/* 6220 */         if (isUserResourceEnabled) {
/* 6221 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=ad.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP not in ('OpManager-Interface') and bd.SUBGROUP like 'OpManager%' order by ad.DISPLAYNAME";
/*      */         } else {
/* 6223 */           mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP not in ('OpManager-Interface') and bd.SUBGROUP like 'OpManager%' and " + condition + " order by ad.DISPLAYNAME";
/*      */         }
/*      */       }
/* 6226 */       else if (isUserResourceEnabled) {
/* 6227 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=ad.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP='" + type + "' " + eumCondition + " order by ad.DISPLAYNAME";
/*      */       } else {
/* 6229 */         condition = condition.trim().length() > 0 ? " and " + condition : condition;
/* 6230 */         mqry = "select ad.RESOURCEID,ad.DISPLAYNAME,ad.type from AM_ManagedObject as ad,AM_ManagedResourceType as bd  where ad.TYPE=bd.RESOURCETYPE and bd.SUBGROUP='" + type + "'" + condition + eumCondition + " order by ad.DISPLAYNAME";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 6237 */     if ((startIndex != -1) && (noOfRows != -1))
/*      */     {
/* 6239 */       String totalQuery = DBUtil.getCountQuery(mqry);
/* 6240 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 6241 */       mqry = DBQueryUtil.addLimit(mqry, startIndex, noOfRows, "ad.DISPLAYNAME");
/*      */     }
/* 6243 */     return getGenericHealthHistory(mqry, span, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Hashtable getHealthHistoryByCategoryType(String type, int span, String owner, String role, Vector resIds_vector)
/*      */   {
/* 6250 */     return getHealthHistoryByCategoryType(type, span, owner, role, resIds_vector, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryByCategoryType(String type, int span, String owner, String role)
/*      */   {
/* 6255 */     return getHealthHistoryByCategoryType(type, span, owner, role, null, -1, -1, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryByCategoryType(String type, int span, String owner, String role, Vector resIds_vector, int startIndex, int noOfRows, HttpServletRequest request) {
/* 6259 */     String mqry = "";
/* 6260 */     String EUMCondn = "";
/* 6261 */     if ((request != null) && ("All".equalsIgnoreCase(request.getParameter("type")))) {
/* 6262 */       EUMCondn = " and mo.resourceid NOT IN (" + Constants.getEUMChildString() + ") ";
/*      */     }
/* 6264 */     if ((!role.equals("operator")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 6266 */       mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and mo.TYPE not like 'OpManager-Interface%' " + EUMCondn + "order by mo.RESOURCEID";
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 6271 */       String condition = "";
/* 6272 */       if (!Constants.isUserResourceEnabled()) {
/* 6273 */         condition = getQueryCondition("mo.RESOURCEID", owner);
/*      */       }
/*      */       
/* 6276 */       if ((EnterpriseUtil.isIt360MSPEdition()) && (resIds_vector != null))
/*      */       {
/* 6278 */         condition = getCondition("mo.RESOURCEID", resIds_vector);
/*      */       }
/*      */       
/* 6281 */       if (Constants.isUserResourceEnabled()) {
/* 6282 */         String loginUserid = Constants.getLoginUserid(request);
/* 6283 */         mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and mo.TYPE not like 'OpManager-Interface%'  " + EUMCondn + " order by mo.RESOURCEID";
/*      */       } else {
/* 6285 */         mqry = "select mo.RESOURCEID,mo.DISPLAYNAME,mo.type from AM_ManagedObject as mo,AM_ManagedResourceType as mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.RESOURCEGROUP='" + type + "' and mo.TYPE not like 'OpManager-Interface%' and " + condition + EUMCondn + " order by mo.RESOURCEID";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 6292 */     if ((startIndex != -1) && (noOfRows != -1))
/*      */     {
/* 6294 */       String totalQuery = DBUtil.getCountQuery(mqry);
/* 6295 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 6296 */       mqry = DBQueryUtil.addLimit(mqry, startIndex, noOfRows, "mo.DISPLAYNAME");
/*      */     }
/* 6298 */     return getGenericHealthHistory(mqry, span, true);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryById(int rid, int span)
/*      */   {
/* 6303 */     String query = "select pc.CHILDID,ob.DISPLAYNAME,ob.type from AM_PARENTCHILDMAPPER as pc, AM_ManagedObject as ob where  pc.PARENTID='" + rid + "' and ob.RESOURCEID=pc.CHILDID order by ob.RESOURCEID";
/* 6304 */     return getGenericHealthHistory(query, span, true);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryById(int rid, int span, boolean isowner, String username, HttpServletRequest request) {
/* 6308 */     String query = "select pc.CHILDID,ob.DISPLAYNAME,ob.type from AM_PARENTCHILDMAPPER as pc, AM_ManagedObject as ob,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where  pc.PARENTID='" + rid + "' and ob.RESOURCEID=pc.CHILDID and AM_UserPasswordTable.USERNAME like '" + username + "' and USERID=OWNERID AND CHILDID=HAID  and type like 'HAI' order by ob.RESOURCEID";
/* 6309 */     if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 6310 */       String loginUserid = Constants.getLoginUserid(request);
/* 6311 */       query = "select pc.CHILDID,ob.DISPLAYNAME,ob.type from AM_PARENTCHILDMAPPER as pc, AM_ManagedObject as ob,AM_USERRESOURCESTABLE where  pc.PARENTID='" + rid + "' and ob.RESOURCEID=pc.CHILDID  and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " AND CHILDID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.RESOURCEID=ob.RESOURCEID  and type like 'HAI' order by ob.RESOURCEID";
/*      */     }
/* 6313 */     return getGenericHealthHistory(query, span, true);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryById(String query, int span, boolean addType, boolean getHealthHistoryForEUMChild)
/*      */   {
/* 6318 */     return getGenericHealthHistory(query, span, addType, getHealthHistoryForEUMChild);
/*      */   }
/*      */   
/*      */   private static Hashtable getGenericHealthHistory(String mqry, int span) {
/* 6322 */     return getGenericHealthHistory(mqry, span, false);
/*      */   }
/*      */   
/*      */   private static Hashtable getGenericHealthHistory(String mqry, int span, boolean addType) {
/* 6326 */     return getGenericHealthHistory(mqry, span, addType, false);
/*      */   }
/*      */   
/*      */   private static Hashtable getGenericHealthHistory(String mqry, int span, boolean addType, boolean getEUMChildHealthHistory) {
/* 6330 */     Hashtable rethash = new Hashtable();
/* 6331 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6332 */     ResultSet set = null;
/* 6333 */     Vector resid = new Vector();
/* 6334 */     Vector disnam = new Vector();
/* 6335 */     Vector type = new Vector();
/*      */     try
/*      */     {
/* 6338 */       ArrayList eumChildList = Constants.getEUMChildList();
/* 6339 */       set = AMConnectionPool.executeQueryStmt(mqry);
/* 6340 */       String rid = "";
/* 6341 */       while (set.next())
/*      */       {
/* 6343 */         rid = set.getString(1);
/* 6344 */         if ((!eumChildList.contains(rid)) || (getEUMChildHealthHistory))
/*      */         {
/* 6346 */           resid.add(rid);
/* 6347 */           disnam.add(set.getString(2));
/* 6348 */           if (addType)
/*      */           {
/* 6350 */             type.add(set.getString(3));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6367 */       if (addType)
/*      */       {
/* 6369 */         if (getEUMChildHealthHistory) {
/* 6370 */           rethash = getHealthHistory(resid, disnam, span, type, getEUMChildHealthHistory);
/*      */         }
/*      */         else {
/* 6373 */           rethash = getHealthHistory(resid, disnam, span, type);
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 6378 */         rethash = getHealthHistory(resid, disnam, span);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6384 */       e.printStackTrace();
/*      */     }
/* 6386 */     return rethash;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getResourceTypeForAttribute(String attributeid)
/*      */   {
/* 6393 */     String toReturn = "";
/* 6394 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6395 */     ResultSet set = null;
/*      */     try {
/* 6397 */       String query = "select RESOURCETYPE from AM_ATTRIBUTES where attributeid='" + attributeid + "'";
/* 6398 */       set = AMConnectionPool.executeQueryStmt(query);
/* 6399 */       while (set.next())
/*      */       {
/* 6401 */         toReturn = set.getString("RESOURCETYPE");
/*      */       }
/*      */     } catch (Exception ex) {
/* 6404 */       ex.printStackTrace();
/*      */     }
/* 6406 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static Map getAttributesForApplicationServer() {
/* 6410 */     ArrayList toReturn = new ArrayList();
/* 6411 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6412 */     Map restypeAttribute = new HashMap();
/* 6413 */     ResultSet set = null;
/*      */     try {
/* 6415 */       String query1 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='APP' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%' and DISPLAYNAME NOT IN ('WebLogic Clusters') ORDER BY DISPLAYNAME";
/* 6416 */       set = AMConnectionPool.executeQueryStmt(query1);
/*      */       
/* 6418 */       while (set.next())
/*      */       {
/* 6420 */         String restype = set.getString("RESOURCETYPE");
/* 6421 */         String name = set.getString("DISPLAYNAME");
/* 6422 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/* 6423 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6428 */       ex.printStackTrace();
/*      */     }
/* 6430 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6433 */   public static Map getAttributesForDatabaseServer() { ArrayList toReturn = new ArrayList();
/* 6434 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6435 */     Map restypeAttribute = new HashMap();
/* 6436 */     ResultSet set = null;
/*      */     try {
/* 6438 */       String query2 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='DBS' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%'  ORDER BY DISPLAYNAME";
/* 6439 */       set = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 6441 */       while (set.next())
/*      */       {
/* 6443 */         String restype = set.getString("RESOURCETYPE");
/* 6444 */         String name = set.getString("DISPLAYNAME");
/* 6445 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6447 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6452 */       ex.printStackTrace();
/*      */     }
/* 6454 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6457 */   public static Map getAttributesForMiddlewareServer() { ArrayList toReturn = new ArrayList();
/* 6458 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6459 */     Map restypeAttribute = new HashMap();
/* 6460 */     ResultSet set = null;
/*      */     try {
/* 6462 */       String query2 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='MOM' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%'  ORDER BY DISPLAYNAME";
/* 6463 */       set = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 6465 */       while (set.next())
/*      */       {
/* 6467 */         String restype = set.getString("RESOURCETYPE");
/* 6468 */         String name = set.getString("DISPLAYNAME");
/* 6469 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6471 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6476 */       ex.printStackTrace();
/*      */     }
/* 6478 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6481 */   public static Map getAttributesForWebServicesServer() { ArrayList toReturn = new ArrayList();
/* 6482 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6483 */     Map restypeAttribute = new HashMap();
/* 6484 */     String remfromWebService = "Url";
/* 6485 */     ResultSet set = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 6493 */       ArrayList a1 = getAttributeListForResourcetype("Web Service");
/*      */       
/* 6495 */       restypeAttribute.put("Web Services", a1);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6499 */       ex.printStackTrace();
/*      */     }
/* 6501 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */   public static Map getAttributesForWebServer() {
/* 6505 */     ArrayList toReturn = new ArrayList();
/* 6506 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6507 */     Map restypeAttribute = new HashMap();
/* 6508 */     String remfromWebService = "Url";
/* 6509 */     ResultSet set = null;
/*      */     try {
/* 6511 */       String query3 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='URL' and RESOURCETYPE in " + Constants.resourceTypes + "  and RESOURCETYPE NOT LIKE 'NMT%' and RESOURCETYPE not in ('UrlMonitor','UrlSeq','Web Service','RBM','RBMURL') ORDER BY DISPLAYNAME";
/* 6512 */       set = AMConnectionPool.executeQueryStmt(query3);
/*      */       
/* 6514 */       while (set.next())
/*      */       {
/* 6516 */         String restype = set.getString("RESOURCETYPE");
/* 6517 */         String name = set.getString("DISPLAYNAME");
/* 6518 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6520 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6525 */       ex.printStackTrace();
/*      */     }
/* 6527 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */   public static Map getAttributesForCategory(String categoryLink)
/*      */   {
/* 6532 */     ArrayList toReturn = new ArrayList();
/* 6533 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6534 */     Map restypeAttribute = new HashMap();
/* 6535 */     ResultSet set = null;
/*      */     try {
/* 6537 */       String query3 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='" + categoryLink + "' and RESOURCETYPE in " + Constants.resourceTypes + "  and RESOURCETYPE NOT LIKE 'NMT%' ORDER BY DISPLAYNAME";
/* 6538 */       set = AMConnectionPool.executeQueryStmt(query3);
/*      */       
/* 6540 */       while (set.next())
/*      */       {
/* 6542 */         String restype = set.getString("RESOURCETYPE");
/* 6543 */         String name = set.getString("DISPLAYNAME");
/* 6544 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/* 6545 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     } catch (Exception ex) {
/* 6548 */       AMConnectionPool.closeStatement(set);
/* 6549 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 6552 */       AMConnectionPool.closeStatement(set);
/*      */     }
/* 6554 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */   public static Map getAttributesForURLs() {
/* 6558 */     ArrayList toReturn = new ArrayList();
/* 6559 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6560 */     Map restypeAttribute = new HashMap();
/* 6561 */     String remfromWebService = "Url";
/* 6562 */     ResultSet set = null;
/*      */     try {
/* 6564 */       String query3 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='URL' and RESOURCETYPE in " + Constants.resourceTypes + "  and RESOURCETYPE NOT LIKE 'NMT%' and RESOURCETYPE in ('UrlMonitor','UrlSeq','RBM','RBMURL') ORDER BY DISPLAYNAME";
/* 6565 */       set = AMConnectionPool.executeQueryStmt(query3);
/*      */       
/* 6567 */       while (set.next())
/*      */       {
/* 6569 */         String restype = set.getString("RESOURCETYPE");
/* 6570 */         String name = set.getString("DISPLAYNAME");
/* 6571 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6573 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6578 */       ex.printStackTrace();
/*      */     }
/* 6580 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */   public static Map getAttributesForSystemServer() {
/* 6584 */     ArrayList toReturn = new ArrayList();
/* 6585 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6586 */     Map restypeAttribute = new HashMap();
/* 6587 */     ResultSet set = null;
/*      */     try {
/* 6589 */       String query4 = "SELECT  RESOURCETYPE,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='SYS' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%' and  RESOURCETYPE NOT IN('Node','WindowsNT','WindowsNT_Server','Windows95','SUN PC','Windows 2000','Windows 2003','Windows 2008','Windows 7','Windows 8','Windows 10','Windows 2012','Windows XP','Windows Vista','snmp-node') GROUP BY SUBGROUP,RESOURCETYPE";
/* 6590 */       set = AMConnectionPool.executeQueryStmt(query4);
/*      */       
/* 6592 */       while (set.next())
/*      */       {
/* 6594 */         String restype = set.getString("RESOURCETYPE");
/* 6595 */         String name = set.getString("SUBGROUP");
/* 6596 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6598 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */       
/*      */ 
/* 6602 */       ArrayList a2 = getAttributeListForResourcetype("Windows");
/* 6603 */       restypeAttribute.put("Windows", a2);
/*      */     } catch (Exception ex) {
/* 6605 */       ex.printStackTrace();
/*      */     }
/* 6607 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6610 */   public static Map getAttributesForMailServer() { ArrayList toReturn = new ArrayList();
/* 6611 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6612 */     Map restypeAttribute = new HashMap();
/* 6613 */     ResultSet set = null;
/*      */     try {
/* 6615 */       String query5 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='MS' and RESOURCETYPE in " + Constants.resourceTypes + "  and RESOURCETYPE NOT LIKE 'NMT%' ORDER BY DISPLAYNAME";
/* 6616 */       set = AMConnectionPool.executeQueryStmt(query5);
/*      */       
/* 6618 */       while (set.next())
/*      */       {
/* 6620 */         String restype = set.getString("RESOURCETYPE");
/* 6621 */         String name = set.getString("DISPLAYNAME");
/* 6622 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6624 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6630 */       ex.printStackTrace();
/*      */     }
/* 6632 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6635 */   public static Map getAttributesForServicesServer() { ArrayList toReturn = new ArrayList();
/* 6636 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6637 */     Map restypeAttribute = new HashMap();
/* 6638 */     ResultSet set = null;
/*      */     try {
/* 6640 */       String query6 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='SER' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%' ORDER BY DISPLAYNAME";
/* 6641 */       set = AMConnectionPool.executeQueryStmt(query6);
/*      */       
/* 6643 */       while (set.next())
/*      */       {
/* 6645 */         String restype = set.getString("RESOURCETYPE");
/* 6646 */         String name = set.getString("DISPLAYNAME");
/* 6647 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6649 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6655 */       ex.printStackTrace();
/*      */     }
/* 6657 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6660 */   public static Map getAttributesForTransactionServer() { ArrayList toReturn = new ArrayList();
/* 6661 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6662 */     Map restypeAttribute = new HashMap();
/* 6663 */     ResultSet set = null;
/*      */     try {
/* 6665 */       String query7 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='TM' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT in ('WTA') and RESOURCETYPE NOT LIKE 'NMT%' ORDER BY DISPLAYNAME";
/* 6666 */       set = AMConnectionPool.executeQueryStmt(query7);
/*      */       
/* 6668 */       while (set.next())
/*      */       {
/* 6670 */         String restype = set.getString("RESOURCETYPE");
/* 6671 */         String name = set.getString("DISPLAYNAME");
/* 6672 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/* 6674 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6680 */       ex.printStackTrace();
/*      */     }
/* 6682 */     return restypeAttribute;
/*      */   }
/*      */   
/* 6685 */   public static Map getAttributesForERPServer() { ArrayList toReturn = new ArrayList();
/* 6686 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6687 */     Map restypeAttribute = new HashMap();
/* 6688 */     ResultSet set = null;
/*      */     try
/*      */     {
/* 6691 */       String query8 = "SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType where RESOURCEGROUP='ERP' and RESOURCETYPE in " + Constants.resourceTypes + " and RESOURCETYPE NOT LIKE 'NMT%' ORDER BY DISPLAYNAME";
/* 6692 */       set = AMConnectionPool.executeQueryStmt(query8);
/*      */       
/* 6694 */       while (set.next())
/*      */       {
/* 6696 */         String restype = set.getString("RESOURCETYPE");
/* 6697 */         String name = set.getString("DISPLAYNAME");
/* 6698 */         ArrayList a1 = getAttributeListForResourcetype(restype);
/*      */         
/*      */ 
/* 6701 */         restypeAttribute.put(name, a1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6707 */       ex.printStackTrace();
/*      */     }
/* 6709 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getReportEnabledAttListForResourcetype(String restype)
/*      */   {
/* 6715 */     ArrayList toReturn = new ArrayList();
/* 6716 */     String resid = null;
/*      */     try
/*      */     {
/* 6719 */       String restypeStr = restype;
/* 6720 */       String resGroup = ReportUtil.getSubgroupForResType(restype);
/* 6721 */       if (!restype.equalsIgnoreCase(resGroup))
/*      */       {
/* 6723 */         restypeStr = resGroup;
/*      */       }
/* 6725 */       if ((restype.contains("QueryMonitor#")) || (restype.contains("Script Monitor#")) || (restype.contains("SNMP#")) || (restype.contains("SAP-CCMS#"))) {
/* 6726 */         String[] temp = restype.split("#");
/* 6727 */         resid = temp[1];
/* 6728 */         restype = temp[0];
/*      */       }
/* 6730 */       List attList = null;
/* 6731 */       if ("windows".equalsIgnoreCase(restype))
/*      */       {
/* 6733 */         attList = ReportUtil.getAttributesForWindows();
/*      */       }
/*      */       else
/*      */       {
/* 6737 */         if ("SYS".equals(restype))
/*      */         {
/* 6739 */           restype = Constants.serverTypes;
/*      */         }
/* 6741 */         if ((resid != null) && (resid != "") && ((restype.equals("QueryMonitor")) || (restype.equals("Script Monitor")) || (restype.equals("SNMP")) || (restype.equals("SAP-CCMS")))) {
/* 6742 */           attList = ReportUtil.getAttribsForResType(restype + "#" + resid);
/*      */         } else {
/* 6744 */           attList = ReportUtil.getAttribsForResType(restype);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 6749 */       ArrayList reportEnabledList = ReportUtil.getRepoEnabledAttribs();
/* 6750 */       for (int j = 0; j < attList.size(); j++)
/*      */       {
/* 6752 */         String res = attList.get(j).toString();
/* 6753 */         String[] temp = res.split("#");
/* 6754 */         String attributeid = temp[0];
/* 6755 */         String displayname = temp[1];
/* 6756 */         if ("windows".equalsIgnoreCase(restype))
/*      */         {
/* 6758 */           String[] tempAttributes = attributeid.split(",");
/* 6759 */           StringBuffer condition = new StringBuffer();
/* 6760 */           boolean isAdd = false;
/* 6761 */           if (tempAttributes.length > 0)
/*      */           {
/* 6763 */             for (int k = 0; k < tempAttributes.length; k++)
/*      */             {
/* 6765 */               String Aid = tempAttributes[k];
/*      */               
/* 6767 */               if (reportEnabledList.contains(Aid))
/*      */               {
/* 6769 */                 isAdd = true;
/*      */                 
/* 6771 */                 if (k == tempAttributes.length - 1) {
/* 6772 */                   condition.append(Aid);
/*      */                 } else {
/* 6774 */                   condition.append(Aid + ",");
/*      */                 }
/*      */               }
/*      */             }
/* 6778 */             if (isAdd)
/*      */             {
/* 6780 */               String attri = condition.toString();
/* 6781 */               Properties props1 = new Properties();
/* 6782 */               props1.setProperty("label", displayname);
/* 6783 */               props1.setProperty("value", restypeStr + "#" + attri);
/* 6784 */               toReturn.add(props1);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 6789 */           else if (reportEnabledList.contains(attributeid))
/*      */           {
/* 6791 */             Properties props = new Properties();
/* 6792 */             props.setProperty("label", displayname);
/* 6793 */             props.setProperty("value", restypeStr + "#" + attributeid);
/* 6794 */             toReturn.add(props);
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 6800 */         else if (reportEnabledList.contains(attributeid))
/*      */         {
/* 6802 */           Properties props = new Properties();
/* 6803 */           props.setProperty("label", displayname);
/* 6804 */           props.setProperty("value", restypeStr + "#" + attributeid);
/* 6805 */           toReturn.add(props);
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6813 */       ex.printStackTrace();
/*      */     }
/* 6815 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static ArrayList getAttributeListForResourcetype(String restype) {
/* 6819 */     ArrayList toReturn = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 6824 */       List attList = null;
/* 6825 */       if ("windows".equalsIgnoreCase(restype))
/*      */       {
/* 6827 */         restype = "Windows";
/* 6828 */         attList = ReportUtil.getAttributesForWindows();
/*      */       }
/*      */       else
/*      */       {
/* 6832 */         if ("SYS".equals(restype)) {
/* 6833 */           restype = Constants.serverTypes;
/*      */         }
/*      */         
/*      */ 
/* 6837 */         attList = ReportUtil.getAttribsForResType(restype);
/*      */       }
/* 6839 */       if ("SUN".equals(restype)) {
/* 6840 */         restype = "Sun Solaris";
/*      */       }
/* 6842 */       if ("HP-UX".equals(restype)) {
/* 6843 */         restype = "HP-UX / Tru64";
/*      */       }
/* 6845 */       if ("FreeBSD".equals(restype))
/*      */       {
/* 6847 */         restype = "FreeBSD / OpenBSD";
/*      */       }
/*      */       
/* 6850 */       if ("HyperVVirtualMachine".equals(restype)) {
/* 6851 */         restype = "VirtualMachine";
/*      */       }
/* 6853 */       ArrayList reportEnabledList = ReportUtil.getRepoEnabledAttribs();
/*      */       
/*      */ 
/* 6856 */       List attributeList = new ArrayList();
/*      */       
/* 6858 */       if (!isForecastReport) {
/* 6859 */         attributeList = ReportUtil.getAttributeList();
/*      */       }
/*      */       
/* 6862 */       for (int j = 0; j < attList.size(); j++) {
/* 6863 */         String res = attList.get(j).toString();
/* 6864 */         String[] temp = res.split("#");
/* 6865 */         String attributeid = temp[0];
/* 6866 */         String displayname = temp[1];
/* 6867 */         if ("windows".equalsIgnoreCase(restype)) {
/* 6868 */           String[] tempAttributes = attributeid.split(",");
/* 6869 */           StringBuffer condition = new StringBuffer();
/* 6870 */           boolean isAdd = false;
/* 6871 */           if (tempAttributes.length > 0) {
/* 6872 */             for (int k = 0; k < tempAttributes.length; k++) {
/* 6873 */               String Aid = tempAttributes[k];
/*      */               
/* 6875 */               if ((reportEnabledList.contains(Aid)) && (!attributeList.contains(Aid))) {
/* 6876 */                 isAdd = true;
/*      */                 
/* 6878 */                 if (k == tempAttributes.length - 1) {
/* 6879 */                   condition.append(Aid);
/*      */                 } else {
/* 6881 */                   condition.append(Aid + ",");
/*      */                 }
/*      */               }
/*      */             }
/* 6885 */             if (isAdd) {
/* 6886 */               String attri = condition.toString();
/* 6887 */               Properties props1 = new Properties();
/* 6888 */               props1.setProperty("label", displayname);
/* 6889 */               props1.setProperty("value", restype + "#" + attri);
/* 6890 */               toReturn.add(props1);
/*      */             }
/*      */           }
/* 6893 */           else if ((reportEnabledList.contains(attributeid)) && (!attributeList.contains(attributeid))) {
/* 6894 */             Properties props = new Properties();
/* 6895 */             props.setProperty("label", displayname);
/* 6896 */             props.setProperty("value", restype + "#" + attributeid);
/* 6897 */             toReturn.add(props);
/*      */           }
/*      */           
/*      */         }
/* 6901 */         else if ((reportEnabledList.contains(attributeid)) && ((!attributeList.contains(attributeid)) || (restype.equalsIgnoreCase("Exchange-Server")))) {
/* 6902 */           Properties props = new Properties();
/* 6903 */           props.setProperty("label", displayname);
/* 6904 */           props.setProperty("value", restype + "#" + attributeid);
/* 6905 */           toReturn.add(props);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 6912 */       ex.printStackTrace();
/*      */     }
/* 6914 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static String getDayValue(String day)
/*      */   {
/* 6919 */     return SegmentReportUtil.getDayValue(day);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistory(Vector rid, Vector disnam, int span)
/*      */   {
/* 6924 */     return getHealthHistory(rid, disnam, span, null);
/*      */   }
/*      */   
/*      */ 
/* 6928 */   public static Hashtable getHealthHistory(Vector rid, Vector disnam, int span, Vector monType) { return getHealthHistory(rid, disnam, span, monType, false); }
/*      */   
/*      */   public static Hashtable getHealthHistory(Vector rid, Vector disnam, int span, Vector monType, boolean getEUMChildHealthHistory) {
/* 6931 */     long endtime = getRoundedEndTime(span);
/* 6932 */     rethash = new Hashtable();
/*      */     
/* 6934 */     String cond = getConditionWithQuotes("RESID", rid);
/*      */     
/* 6936 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6937 */     ResultSet set = null;
/* 6938 */     ResultSet eumSet = null;
/*      */     
/* 6940 */     String eumMonitors = Constants.resourceTypesEUM;
/*      */     try
/*      */     {
/* 6943 */       if (span == 2) {
/* 6944 */         long starttime = endtime - 2592000000L;
/*      */         
/* 6946 */         String qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and DURATION=" + 2 + " and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' order by RESID ";
/*      */         
/*      */ 
/*      */ 
/* 6950 */         set = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 6955 */           rethash = getHealthHistoryData(set, rid, monType, disnam, starttime, endtime, "2");
/*      */         }
/*      */         catch (Exception e) {
/* 6958 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 6963 */       else if ((span == 1) || (span == 4)) {
/* 6964 */         long starttime = endtime - 86400000L;
/* 6965 */         if (span == 4) {
/* 6966 */           starttime = endtime - 21600000L;
/*      */         }
/* 6968 */         String qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and DURATION=" + 1 + " and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' order by RESID ";
/*      */         
/* 6970 */         set = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 6975 */           if (!getEUMChildHealthHistory) {
/* 6976 */             rethash = getHealthHistoryData(set, rid, monType, disnam, starttime, endtime, "1");
/*      */           }
/*      */           else {
/* 6979 */             rethash = getHealthHistoryData(set, rid, monType, disnam, starttime, endtime, "1", getEUMChildHealthHistory);
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 6983 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       String orid1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       String orid1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       int i;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       String orid1;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7027 */       return rethash;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6987 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 6990 */       if (rethash.size() != rid.size())
/*      */       {
/* 6992 */         for (i = 0; i < rid.size(); i++)
/*      */         {
/* 6994 */           orid1 = (String)rid.elementAt(i);
/* 6995 */           if (!rethash.containsKey(orid1 + "#" + disnam.elementAt(i)))
/*      */           {
/* 6997 */             if (monType != null)
/*      */             {
/* 6999 */               rethash.put(monType.elementAt(i) + "$" + rid.elementAt(i) + "#" + disnam.elementAt(i), new Hashtable());
/*      */             }
/*      */             else
/*      */             {
/* 7003 */               rethash.put(rid.elementAt(i) + "#" + disnam.elementAt(i), new Hashtable());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       try {
/* 7009 */         if (set != null) {
/* 7010 */           set.close();
/*      */         }
/* 7012 */         if (eumSet != null) {
/* 7013 */           eumSet.close();
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 7019 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean getIsEUMType(String monType)
/*      */   {
/* 7030 */     String eumTypes = Constants.resourceTypesEUM;
/* 7031 */     boolean toreturn = false;
/* 7032 */     if (eumTypes.indexOf(monType) != -1) {
/* 7033 */       toreturn = true;
/*      */     }
/* 7035 */     return toreturn;
/*      */   }
/*      */   
/* 7038 */   public static boolean getIsEUMId(String resId) { AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7039 */     ResultSet rs = null;
/* 7040 */     String eumTypes = Constants.resourceTypesEUM;
/* 7041 */     toreturn = false;
/*      */     try {
/* 7043 */       String qry = "select RESOURCEID from AM_ManagedObject where RESOURCEID=" + resId + " and TYPE in " + eumTypes;
/* 7044 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 7045 */       if (rs.next()) {}
/* 7046 */       return true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7050 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 7054 */         if (rs != null) {
/* 7055 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 7059 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Hashtable getHealthHistoryData(ResultSet normalset, Vector rid, Vector monType, Vector disnam, long starttime, long endtime, String duration)
/*      */   {
/* 7068 */     return getHealthHistoryData(normalset, rid, monType, disnam, starttime, endtime, duration, false);
/*      */   }
/*      */   
/*      */   public static Hashtable getHealthHistoryData(ResultSet normalset, Vector rid, Vector monType, Vector disnam, long starttime, long endtime, String duration, boolean getEUMChildHealthHistory)
/*      */   {
/* 7073 */     rethash = new Hashtable();
/* 7074 */     ResultSet set = null;
/* 7075 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7076 */     String eumMonitors = Constants.resourceTypesEUM;
/*      */     try
/*      */     {
/* 7079 */       for (int i = 0; i < rid.size(); i++)
/*      */       {
/* 7081 */         String monTypeStr = (String)monType.elementAt(i);
/* 7082 */         String resId = (String)rid.elementAt(i);
/*      */         
/* 7084 */         set = null;
/* 7085 */         boolean isEUMType = false;
/* 7086 */         boolean isEUMResId = false;
/* 7087 */         Hashtable timehash = new Hashtable();
/* 7088 */         if (!getEUMChildHealthHistory)
/*      */         {
/* 7090 */           if (monType != null)
/*      */           {
/* 7092 */             isEUMType = getIsEUMType(monTypeStr);
/*      */           }
/*      */           else
/*      */           {
/* 7096 */             isEUMResId = getIsEUMId(resId);
/*      */           }
/*      */         }
/* 7099 */         if ((isEUMType) || (isEUMResId))
/*      */         {
/* 7101 */           String qry = "select archivedtime,sum(CLEARCOUNT),sum(CRITICALCOUNT),sum(WARNINGCOUNT),PARENTID from AM_ManagedObjectHistoryData,AM_PARENTCHILDMAPPER,AM_ManagedObject where PARENTID=" + resId + " and RESID in (select CHILDID from AM_PARENTCHILDMAPPER where PARENTID=" + resId + " ) and DURATION=" + duration + " and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' and AM_ManagedObject.RESOURCEID=PARENTID  and Type in " + eumMonitors + "  group by PARENTID,archivedtime";
/*      */           
/* 7103 */           set = AMConnectionPool.executeQueryStmt(qry);
/*      */         }
/*      */         else
/*      */         {
/* 7107 */           set = normalset;
/*      */           
/*      */ 
/* 7110 */           set.beforeFirst();
/*      */         }
/* 7112 */         if ((i != 0) && (!set.isAfterLast()))
/*      */         {
/*      */           try
/*      */           {
/* 7116 */             Hashtable temphash = new Hashtable();
/* 7117 */             temphash.put("clear", set.getString(2));
/* 7118 */             temphash.put("critic", set.getString(3));
/* 7119 */             temphash.put("warn", set.getString(4));
/* 7120 */             timehash.put(Long.valueOf(set.getLong(1)), temphash);
/*      */           }
/*      */           catch (Exception ex) {}
/*      */         }
/*      */         
/*      */ 
/* 7126 */         while (set.next())
/*      */         {
/* 7128 */           if (set.getString(5).equals(resId))
/*      */           {
/* 7130 */             Hashtable datahash = new Hashtable();
/* 7131 */             datahash.put("clear", set.getString(2));
/* 7132 */             datahash.put("critic", set.getString(3));
/* 7133 */             datahash.put("warn", set.getString(4));
/* 7134 */             timehash.put(Long.valueOf(set.getLong(1)), datahash);
/*      */           }
/*      */         }
/*      */         
/* 7138 */         String dispName = resId + "#" + disnam.elementAt(i);
/* 7139 */         if (monType != null)
/*      */         {
/* 7141 */           dispName = monTypeStr + "$" + dispName;
/*      */         }
/* 7143 */         rethash.put(dispName, timehash);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7164 */       return rethash;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7148 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 7154 */         if (set != null)
/*      */         {
/* 7156 */           set.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 7161 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Hashtable getHealthHistoryinspan(Vector rid, long starttime, long endtime, int daily_no, int totno)
/*      */   {
/* 7170 */     Hashtable rethash = new Hashtable();
/*      */     
/* 7172 */     String cond = getConditionWithQuotes("RESID", rid);
/* 7173 */     String cond1 = getConditionWithQuotes("RESOURCEID", rid);
/* 7174 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7175 */     ResultSet set = null;
/* 7176 */     String qry = "";
/* 7177 */     Vector rid1 = new Vector();
/* 7178 */     Vector disnam = new Vector();
/* 7179 */     Vector monType = new Vector();
/*      */     try {
/* 7181 */       String ordmolistqry = "select RESOURCEID, DISPLAYNAME,TYPE from AM_ManagedObject  where " + cond1 + " order by RESOURCEID ";
/* 7182 */       System.out.println("*************COMP QUERY*******************" + ordmolistqry);
/* 7183 */       set = AMConnectionPool.executeQueryStmt(ordmolistqry);
/* 7184 */       while (set.next())
/*      */       {
/* 7186 */         rid1.add(set.getString("RESOURCEID"));
/* 7187 */         disnam.add(EnterpriseUtil.decodeString(set.getString("DISPLAYNAME")));
/* 7188 */         monType.add(set.getString("TYPE"));
/*      */       }
/*      */       
/* 7191 */       AMConnectionPool.closeStatement(set);
/* 7192 */       if (daily_no == 0)
/*      */       {
/* 7194 */         qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and DURATION=1 and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' order by RESID ";
/*      */ 
/*      */       }
/* 7197 */       else if (daily_no == totno)
/*      */       {
/* 7199 */         qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and DURATION=2 and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' order by RESID ";
/*      */ 
/*      */       }
/* 7202 */       else if (daily_no > 30)
/*      */       {
/* 7204 */         qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and DURATION=3 and archivedtime > '" + starttime + "' and archivedtime <= '" + endtime + "' order by RESID ";
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 7209 */         long dec_endtime = endtime - 82800000L;
/* 7210 */         qry = "select archivedtime,CLEARCOUNT,CRITICALCOUNT,WARNINGCOUNT,RESID from AM_ManagedObjectHistoryData where " + cond + " and ((DURATION=2 and archivedtime > '" + starttime + "' and archivedtime <= '" + dec_endtime + "') OR (DURATION=1 and archivedtime > '" + dec_endtime + "' and archivedtime <= '" + endtime + "'))   order by RESID ";
/*      */       }
/*      */       
/* 7213 */       System.out.println("************* QUERY IN TROUBLESHOOT DASHBOARD *******************" + qry);
/* 7214 */       set = AMConnectionPool.executeQueryStmt(qry);
/* 7215 */       for (int i = 0; i < rid1.size(); i++) {
/* 7216 */         Hashtable timehash = new Hashtable();
/* 7217 */         if (i != 0)
/*      */         {
/* 7219 */           if (!set.isAfterLast()) {
/*      */             try
/*      */             {
/* 7222 */               Hashtable temphash = new Hashtable();
/* 7223 */               temphash.put("clear", set.getString(2));
/* 7224 */               temphash.put("critic", set.getString(3));
/* 7225 */               temphash.put("warn", set.getString(4));
/* 7226 */               timehash.put(Long.valueOf(set.getLong(1)), temphash);
/*      */             }
/*      */             catch (Exception ex) {}
/*      */           }
/*      */         }
/*      */         
/* 7232 */         String orid = (String)rid1.elementAt(i);
/* 7233 */         while (set.next()) {
/* 7234 */           Hashtable datahash = new Hashtable();
/* 7235 */           datahash.put("clear", set.getString(2));
/* 7236 */           datahash.put("critic", set.getString(3));
/* 7237 */           datahash.put("warn", set.getString(4));
/* 7238 */           if (!set.getString(5).equals(orid))
/*      */             break;
/* 7240 */           timehash.put(Long.valueOf(set.getLong(1)), datahash);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 7249 */         rethash.put(monType.elementAt(i) + "$" + rid1.elementAt(i) + "#" + disnam.elementAt(i), timehash);
/*      */       }
/*      */     } catch (Exception e) {
/*      */       int i;
/*      */       String orid1;
/* 7254 */       e.printStackTrace();
/*      */     } finally { int i;
/*      */       String orid1;
/* 7257 */       if (rethash.size() != rid1.size())
/*      */       {
/* 7259 */         for (int i = 0; i < rid1.size(); i++)
/*      */         {
/* 7261 */           String orid1 = (String)rid1.elementAt(i);
/* 7262 */           if (!rethash.containsKey(monType.elementAt(i) + "$" + orid1 + "#" + disnam.elementAt(i)))
/*      */           {
/* 7264 */             rethash.put(monType.elementAt(i) + "$" + rid1.elementAt(i) + "#" + disnam.elementAt(i), new Hashtable());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 7271 */       closeResultSet(set);
/*      */     }
/*      */     
/*      */ 
/* 7275 */     return rethash;
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
/*      */   public static Hashtable getArchivaltimes(long starttime, long endtime)
/*      */   {
/* 7288 */     Hashtable rethash = new Hashtable();
/* 7289 */     long arc_starttime = starttime;
/* 7290 */     long arc_endtime = endtime;
/* 7291 */     ArrayList retlist = new ArrayList();
/* 7292 */     long diff = endtime - starttime;
/* 7293 */     diff /= 1000L;
/* 7294 */     long totalMins = diff / 60L;
/* 7295 */     long totalHours = totalMins / 60L;
/*      */     
/* 7297 */     Calendar cal_start = new GregorianCalendar();
/* 7298 */     cal_start.setTime(new java.util.Date(starttime));
/* 7299 */     if ((cal_start.get(12) != 0) || (cal_start.get(13) != 0) || (cal_start.get(14) != 0))
/*      */     {
/* 7301 */       cal_start.add(11, 1);
/* 7302 */       cal_start.set(12, 0);
/* 7303 */       cal_start.set(13, 0);
/* 7304 */       cal_start.set(14, 0);
/* 7305 */       arc_starttime = cal_start.getTime().getTime();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7310 */     Calendar cal_end = new GregorianCalendar();
/* 7311 */     cal_end.setTime(new java.util.Date(endtime));
/* 7312 */     if ((cal_end.get(12) != 0) || (cal_end.get(13) != 0) || (cal_end.get(14) != 0))
/*      */     {
/*      */ 
/* 7315 */       cal_end.set(12, 0);
/* 7316 */       cal_end.set(13, 0);
/* 7317 */       cal_end.set(14, 0);
/* 7318 */       arc_endtime = cal_end.getTime().getTime();
/*      */     }
/*      */     
/*      */ 
/* 7322 */     int daily = 0;
/* 7323 */     if (totalHours <= 30L)
/*      */     {
/* 7325 */       for (long i = arc_starttime; i <= arc_endtime; i += 3600000L)
/*      */       {
/* 7327 */         retlist.add(Long.valueOf(i));
/*      */       }
/*      */       
/*      */     }
/* 7331 */     else if (totalHours <= 168L)
/*      */     {
/*      */ 
/* 7334 */       long st = arc_starttime;
/* 7335 */       Calendar cal_split = new GregorianCalendar();
/* 7336 */       cal_split.setTime(new java.util.Date(st));
/* 7337 */       if (cal_split.get(11) != 23)
/*      */       {
/* 7339 */         cal_split.set(11, 23);
/* 7340 */         st = cal_split.getTime().getTime();
/*      */       }
/*      */       
/* 7343 */       for (long i = st; i <= arc_endtime; i += 86400000L)
/*      */       {
/* 7345 */         retlist.add(Long.valueOf(i));
/* 7346 */         daily++;
/*      */       }
/*      */       
/*      */ 
/*      */     }
/* 7351 */     else if (totalHours > 720L)
/*      */     {
/* 7353 */       long st = arc_starttime;
/* 7354 */       Calendar cal = Calendar.getInstance();
/* 7355 */       while (arc_starttime < arc_endtime)
/*      */       {
/* 7357 */         cal.setTime(new java.util.Date(arc_starttime));
/* 7358 */         cal.set(11, 23);
/* 7359 */         cal.set(13, 0);
/* 7360 */         cal.set(14, 0);
/* 7361 */         cal.set(12, 0);
/* 7362 */         cal.set(5, cal.getActualMinimum(5));
/* 7363 */         retlist.add(Long.valueOf(cal.getTimeInMillis()));
/* 7364 */         cal.add(2, 1);
/* 7365 */         arc_starttime = cal.getTimeInMillis();
/*      */       }
/* 7367 */       daily = (int)totalHours / 24;
/*      */     }
/*      */     else
/*      */     {
/* 7371 */       long st = arc_starttime;
/* 7372 */       Calendar cal_split = new GregorianCalendar();
/* 7373 */       cal_split.setTime(new java.util.Date(st));
/* 7374 */       if (cal_split.get(11) != 23)
/*      */       {
/* 7376 */         cal_split.set(11, 23);
/* 7377 */         st = cal_split.getTime().getTime();
/*      */       }
/* 7379 */       for (long i = st; i <= arc_endtime; i += 86400000L)
/*      */       {
/* 7381 */         retlist.add(Long.valueOf(i));
/* 7382 */         daily++;
/*      */       }
/*      */     }
/* 7385 */     rethash.put("daily", Integer.valueOf(daily));
/* 7386 */     rethash.put("time", retlist);
/*      */     
/* 7388 */     return rethash;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Hashtable getHealthEvents(String resid, String endstr, String span, String motype)
/*      */   {
/* 7396 */     Hashtable rethash = new Hashtable();
/* 7397 */     ArrayList atlist = new ArrayList();
/* 7398 */     ArrayList atdatalist = new ArrayList();
/* 7399 */     ArrayList raw_attrib = new ArrayList();
/* 7400 */     Hashtable rawdatatable = new Hashtable();
/* 7401 */     ArrayList colltime = new ArrayList();
/* 7402 */     int perccount = 0;
/* 7403 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7404 */     ResultSet set = null;
/* 7405 */     if ((motype.toLowerCase().contains("windows")) && (!motype.equals("WindowsAzure")))
/*      */     {
/* 7407 */       motype = "Windows";
/*      */     }
/* 7409 */     else if (motype.toLowerCase().contains("hp-"))
/*      */     {
/* 7411 */       motype = "HP-";
/*      */     }
/* 7413 */     String qry = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES_EXT.ARCHIVEDDATA_TABLENAME,AM_ATTRIBUTES_EXT.DATATABLE,AM_ATTRIBUTES_EXT.RESID_COL,AM_ATTRIBUTES_EXT.VALUE_COL,AM_ATTRIBUTES_EXT.ATTID_COL from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_ATTRIBUTES.resourcetype like '%" + motype + "%' and AM_ATTRIBUTES.DISPLAYNAME not in ('Health') and AM_ATTRIBUTES_EXT.ISARCHIVEING=1 order by AM_ATTRIBUTES.DISPLAYNAME ";
/*      */     try
/*      */     {
/* 7416 */       set = AMConnectionPool.executeQueryStmt(qry);
/* 7417 */       while (set.next())
/*      */       {
/* 7419 */         Hashtable athash = new Hashtable();
/* 7420 */         athash.put("ATTRIBUTEID", set.getString("ATTRIBUTEID"));
/* 7421 */         athash.put("DISPLAYNAME", set.getString("DISPLAYNAME"));
/*      */         
/* 7423 */         String unit = set.getString("UNITS");
/* 7424 */         if (unit != null) {
/* 7425 */           athash.put("UNITS", unit);
/*      */         } else {
/* 7427 */           athash.put("UNITS", "-");
/*      */         }
/* 7429 */         athash.put("TABLENAME", set.getString("ARCHIVEDDATA_TABLENAME"));
/* 7430 */         athash.put("RAWDATATABLE", set.getString("DATATABLE"));
/* 7431 */         athash.put("RESID_COL", set.getString("RESID_COL"));
/* 7432 */         athash.put("VALUE_COL", set.getString("VALUE_COL"));
/* 7433 */         athash.put("ATTID_COL", set.getString("ATTID_COL"));
/* 7434 */         atlist.add(athash);
/*      */       }
/*      */       
/*      */ 
/* 7438 */       long end = Long.valueOf(endstr).longValue();
/* 7439 */       for (int k = 0; k < atlist.size(); k++)
/*      */       {
/* 7441 */         Hashtable attribhash = (Hashtable)atlist.get(k);
/* 7442 */         String table = (String)attribhash.get("TABLENAME");
/* 7443 */         String attid = (String)attribhash.get("ATTRIBUTEID");
/*      */         
/* 7445 */         String dataqry = "select MINVALUE,MAXVALUE,(TOTAL/TOTALCOUNT) as AVERAGE from " + table + " where RESID='" + resid + "' and ARCHIVEDTIME='" + end + "' and ATTRIBUTEID='" + attid + "'";
/*      */         try
/*      */         {
/* 7448 */           ResultSet datset = AMConnectionPool.executeQueryStmt(dataqry);
/*      */           
/* 7450 */           if (datset.next())
/*      */           {
/* 7452 */             Hashtable dathash = new Hashtable();
/* 7453 */             dathash.put("MINVALUE", datset.getString("MINVALUE"));
/* 7454 */             dathash.put("MAXVALUE", datset.getString("MAXVALUE"));
/* 7455 */             dathash.put("AVERAGE", datset.getString("AVERAGE"));
/* 7456 */             dathash.put("DISPLAYNAME", (String)attribhash.get("DISPLAYNAME"));
/* 7457 */             dathash.put("UNITS", (String)attribhash.get("UNITS"));
/* 7458 */             if (((String)attribhash.get("UNITS")).trim().equals("%"))
/* 7459 */               perccount++;
/* 7460 */             atdatalist.add(dathash);
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 7466 */           ex.printStackTrace();
/*      */         }
/*      */       }
/* 7469 */       long start_time = end - 3600000L;
/* 7470 */       long currentTime = System.currentTimeMillis();
/* 7471 */       long rawdataTime = currentTime - 10800000L;
/*      */       
/* 7473 */       System.out.println(currentTime + "------>CURRENT TIME*************CURRENT TIME********* RAW TIME------>" + rawdataTime);
/* 7474 */       if (start_time > rawdataTime)
/*      */       {
/* 7476 */         for (int k = 0; k < atlist.size(); k++)
/*      */         {
/* 7478 */           Hashtable attribhash = (Hashtable)atlist.get(k);
/* 7479 */           ArrayList atrawdatalist = new ArrayList();
/* 7480 */           String table_name = (String)attribhash.get("RAWDATATABLE");
/* 7481 */           String att_col = (String)attribhash.get("VALUE_COL");
/* 7482 */           String resid_col = (String)attribhash.get("RESID_COL");
/* 7483 */           String disp = (String)attribhash.get("DISPLAYNAME");
/* 7484 */           String attID_col = (String)attribhash.get("ATTID_COL");
/* 7485 */           String attID = (String)attribhash.get("ATTRIBUTEID");
/* 7486 */           String specialChar = "";
/* 7487 */           ArrayList confAttList = ConfMonitorConfiguration.getInstance().getAttListInDataTables();
/* 7488 */           if (confAttList.contains(attID)) {
/* 7489 */             specialChar = DBQueryUtil.getSpecialCharToAppend();
/*      */           }
/* 7491 */           att_col = specialChar + att_col + specialChar;
/*      */           
/* 7493 */           String rawdataqry = "select " + att_col + ",COLLECTIONTIME from " + table_name + " where " + resid_col + "='" + resid + "' and COLLECTIONTIME>" + start_time + " and COLLECTIONTIME<" + end + " order by COLLECTIONTIME";
/* 7494 */           if (!"-1".equals(attID_col))
/*      */           {
/* 7496 */             rawdataqry = "select " + att_col + ",COLLECTIONTIME from " + table_name + " where " + resid_col + "='" + resid + "' and " + attID_col + "=" + attID + " and COLLECTIONTIME>" + start_time + " and COLLECTIONTIME<" + end + " order by COLLECTIONTIME";
/*      */           }
/*      */           
/* 7499 */           Hashtable dathash = new Hashtable();
/*      */           try {
/* 7501 */             ResultSet datset = AMConnectionPool.executeQueryStmt(rawdataqry);
/*      */             
/*      */ 
/* 7504 */             while (datset.next())
/*      */             {
/* 7506 */               if (datset.getString(1).equals("-1")) {
/* 7507 */                 dathash.put(datset.getString(2), "-");
/*      */               } else
/* 7509 */                 dathash.put(datset.getString(2), datset.getString(1));
/* 7510 */               if (((disp.equals("Response Time")) || (disp.equals("Connection Time")) || (disp.equals("Round Trip Time")) || (disp.equals("FrontEnd ResponseTime"))) && (!colltime.contains(datset.getString("COLLECTIONTIME"))))
/*      */               {
/* 7512 */                 colltime.add(datset.getString("COLLECTIONTIME"));
/*      */               }
/*      */               
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 7520 */             ex.printStackTrace();
/*      */           }
/* 7522 */           rawdatatable.put(disp, dathash);
/*      */         }
/*      */       }
/*      */       
/* 7526 */       rethash.put("Perc_count", Integer.valueOf(perccount));
/* 7527 */       rethash.put("ColnTime", colltime);
/* 7528 */       rethash.put("Rawdata", rawdatatable);
/* 7529 */       rethash.put("Attrib_raw", raw_attrib);
/* 7530 */       rethash.put("Endtime", Long.valueOf(end));
/* 7531 */       rethash.put("Starttime", Long.valueOf(end - 3600000L));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7535 */       e.printStackTrace();
/*      */     }
/* 7537 */     ArrayList retlist = getHealthEvents(resid, endstr, span);
/* 7538 */     rethash.put("Events", retlist);
/* 7539 */     rethash.put("Attributes", atdatalist);
/*      */     
/* 7541 */     return rethash;
/*      */   }
/*      */   
/*      */   public static ArrayList getHealthEvents(String resid, String endstr, String span) {
/* 7545 */     long start = 0L;
/* 7546 */     long end = Long.valueOf(endstr).longValue();
/* 7547 */     int num = 0;
/* 7548 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7549 */     ResultSet set = null;
/*      */     
/* 7551 */     ArrayList events = new ArrayList();
/*      */     
/*      */ 
/* 7554 */     if (span.equals("24"))
/*      */     {
/* 7556 */       start = end - 3600000L;
/*      */     }
/*      */     else
/*      */     {
/* 7560 */       start = end - 86400000L;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 7565 */       Calendar cal = Calendar.getInstance();
/* 7566 */       String qry = "select ab.SEVERITY,ab.TTIME as time,ab.TEXT from Event as ab,AM_ATTRIBUTES as cd where ab.SOURCE='" + resid + "' and ab.CATEGORY=cd.ATTRIBUTEID and ab.TTIME >'" + start + "' and ab.TTIME <'" + end + "' and cd.DISPLAYNAME like '%Health%' order by time desc";
/* 7567 */       if (DBQueryUtil.isPgsql())
/*      */       {
/* 7569 */         qry = "select ab.SEVERITY,ab.TTIME as time,ab.TEXT from Event as ab,AM_ATTRIBUTES as cd where ab.SOURCE='" + resid + "' and ab.CATEGORY=CAST(cd.ATTRIBUTEID as VARCHAR) and ab.TTIME >'" + start + "' and ab.TTIME <'" + end + "' and cd.DISPLAYNAME like '%Health%' order by time desc";
/*      */       }
/* 7571 */       set = AMConnectionPool.executeQueryStmt(qry);
/*      */       
/* 7573 */       while (set.next())
/*      */       {
/* 7575 */         Hashtable rethash = new Hashtable();
/* 7576 */         rethash.put("severity", set.getString(1));
/* 7577 */         cal.setTimeInMillis(set.getLong(2));
/* 7578 */         rethash.put("time", cal.getTime().toString().substring(0, 20));
/* 7579 */         rethash.put("text", set.getString(3));
/* 7580 */         events.add(rethash);
/* 7581 */         num = 1;
/*      */       }
/* 7583 */       AMConnectionPool.closeStatement(set);
/* 7584 */       START_TIME = start;
/* 7585 */       END_TIME = end;
/* 7586 */       if (num == 0) {
/* 7587 */         long start1 = 0L;
/*      */         
/* 7589 */         if (span.equals("24"))
/*      */         {
/* 7591 */           start1 = end - 86400000L;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 7596 */           start1 = end - 2592000000L;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 7603 */           String spclq = "select ab.SEVERITY,ab.TTIME as time,ab.TEXT from Event as ab,AM_ATTRIBUTES as cd where ab.SOURCE='" + resid + "' and ab.CATEGORY=cd.ATTRIBUTEID and ab.TTIME >'" + start1 + "' and ab.TTIME <'" + end + "' and cd.DISPLAYNAME like '%Health%' order by time desc";
/* 7604 */           if (DBQueryUtil.isPgsql())
/*      */           {
/* 7606 */             spclq = "select ab.SEVERITY,ab.TTIME as time,ab.TEXT from Event as ab,AM_ATTRIBUTES as cd where ab.SOURCE='" + resid + "' and ab.CATEGORY=CAST(cd.ATTRIBUTEID as VARCHAR) and ab.TTIME >'" + start1 + "' and ab.TTIME <'" + end + "' and cd.DISPLAYNAME like '%Health%' order by time desc";
/*      */           }
/* 7608 */           set = AMConnectionPool.executeQueryStmt(spclq);
/* 7609 */           while (set.next())
/*      */           {
/* 7611 */             Hashtable rethash = new Hashtable();
/* 7612 */             rethash.put("severity", set.getString(1));
/* 7613 */             cal.setTimeInMillis(set.getLong(2));
/* 7614 */             rethash.put("time", cal.getTime().toString().substring(0, 20));
/* 7615 */             rethash.put("text", set.getString(3));
/* 7616 */             events.add(rethash);
/*      */           }
/*      */           
/* 7619 */           AMConnectionPool.closeStatement(set);
/* 7620 */           START_TIME = start1;
/*      */ 
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 7625 */           ee.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7631 */       e.printStackTrace();
/*      */     }
/* 7633 */     return events;
/*      */   }
/*      */   
/*      */   public static String[] getHealthEventsTime() {
/* 7637 */     String[] ret = new String[2];
/* 7638 */     java.util.Date start = new java.util.Date();
/* 7639 */     java.util.Date end = new java.util.Date();
/* 7640 */     start.setTime(START_TIME);
/* 7641 */     end.setTime(END_TIME);
/* 7642 */     ret[0] = start.toString().substring(4, 16);
/* 7643 */     ret[1] = end.toString().substring(4, 16);
/* 7644 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getGraphDataForConfigAttList(String resType)
/*      */   {
/* 7650 */     ArrayList configList = getReportEnabledAttListForResourcetype(resType);
/* 7651 */     System.out.println("the config list=======>" + configList);
/*      */     
/* 7653 */     ArrayList graphData = new ArrayList();
/* 7654 */     for (int i = 0; i < configList.size(); i++)
/*      */     {
/* 7656 */       ArrayList al = new ArrayList();
/* 7657 */       Properties attList = (Properties)configList.get(i);
/* 7658 */       al.add(FormatUtil.getString(attList.getProperty("label")));
/* 7659 */       String attInf = attList.getProperty("value");
/* 7660 */       String attId = attInf.substring(attInf.indexOf("#") + 1);
/* 7661 */       al.add(attId);
/* 7662 */       graphData.add(al);
/*      */     }
/* 7664 */     return graphData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Hashtable getMOTypeCount(boolean isUserOpr, String remoteUser)
/*      */   {
/* 7672 */     Hashtable rethash = new Hashtable();
/* 7673 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7674 */     Hashtable moTypeCount = new Hashtable();
/* 7675 */     Hashtable moDisNamCount = new Hashtable();
/*      */     
/*      */     try
/*      */     {
/* 7679 */       String qry = "(select mrt.SUBGROUP,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE group by mrt.SUBGROUP) union ( SELECT mo.TYPE,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE  AND mo.TYPE IN ('HyperVVirtualMachine','XenServerVM') group by mo.TYPE )";
/* 7680 */       if ((isUserOpr) && (remoteUser != null))
/*      */       {
/* 7682 */         String monitorsForOpr = getQueryCondition("mo.RESOURCEID", remoteUser);
/*      */         
/*      */ 
/* 7685 */         qry = "(select mrt.SUBGROUP,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE and " + monitorsForOpr + "  group by mrt.SUBGROUP) UNION ( SELECT mo.TYPE,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE and " + monitorsForOpr + "  AND mo.TYPE IN ('HyperVVirtualMachine','XenServerVM') group by mo.TYPE )";
/*      */       }
/*      */       
/*      */ 
/* 7689 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 7690 */       while (rs.next())
/*      */       {
/* 7692 */         moTypeCount.put(rs.getString(1), Integer.valueOf(rs.getInt(2)));
/*      */       }
/*      */       
/* 7695 */       qry = "select mrt.DISPLAYNAME,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE group by mrt.DISPLAYNAME";
/* 7696 */       if ((isUserOpr) && (remoteUser != null))
/*      */       {
/* 7698 */         String monitorsForOpr = getQueryCondition("mo.RESOURCEID", remoteUser);
/*      */         
/* 7700 */         qry = "select mrt.DISPLAYNAME,count(mo.TYPE) from AM_ManagedObject as mo, AM_ManagedResourceType as mrt where mo.TYPE=mrt.RESOURCETYPE and " + monitorsForOpr + "  group by mrt.DISPLAYNAME";
/*      */       }
/*      */       
/* 7703 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 7704 */       while (rs.next())
/*      */       {
/* 7706 */         moDisNamCount.put(rs.getString(1), Integer.valueOf(rs.getInt(2)));
/*      */       }
/*      */       
/* 7709 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7713 */       e.printStackTrace();
/*      */     }
/*      */     
/* 7716 */     rethash.put("moTypeCount", moTypeCount);
/* 7717 */     rethash.put("moDisNamCount", moDisNamCount);
/*      */     
/* 7719 */     return rethash;
/*      */   }
/*      */   
/*      */   public static Hashtable getMGHistoryForOperator(String userName, int period, boolean mgdrilldown, int dashtype)
/*      */   {
/* 7724 */     return getMGHistoryForOperator(userName, period, mgdrilldown, dashtype, null);
/*      */   }
/*      */   
/*      */   public static Hashtable getMGHistoryForOperator(String userName, int period, boolean mgdrilldown, int dashtype, HttpServletRequest request)
/*      */   {
/* 7729 */     toret = new Hashtable();
/* 7730 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7731 */     ResultSet rs = null;
/* 7732 */     String query = null;
/*      */     try
/*      */     {
/* 7735 */       if (mgdrilldown)
/*      */       {
/*      */ 
/* 7738 */         String qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_HOLISTICAPPLICATION WHERE   AM_UserPasswordTable.USERNAME='" + userName + "' AND AM_UserPasswordTable.USERID= AM_HOLISTICAPPLICATION_OWNERS.OWNERID AND AM_HOLISTICAPPLICATION.HAID= AM_HOLISTICAPPLICATION_OWNERS.HAID";
/* 7739 */         if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 7740 */           String loginUserid = Constants.getLoginUserid(request);
/* 7741 */           qry = "SELECT AM_HOLISTICAPPLICATION.HAID,TYPE from  AM_USERRESOURCESTABLE,AM_HOLISTICAPPLICATION WHERE AM_HOLISTICAPPLICATION.HAID= AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */         }
/* 7743 */         rs = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/* 7745 */         String mainhaids = "(";
/* 7746 */         String childhaids = "(";
/* 7747 */         Hashtable parentlist = new Hashtable();
/*      */         
/* 7749 */         while (rs.next())
/*      */         {
/* 7751 */           if (rs.getInt(2) == 0)
/*      */           {
/* 7753 */             mainhaids = mainhaids + "'" + rs.getString(1) + "',";
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 7758 */             childhaids = childhaids + "'" + rs.getString(1) + "',";
/*      */           }
/*      */         }
/*      */         
/* 7762 */         childhaids = childhaids.substring(0, childhaids.length() - 1) + ")";
/*      */         
/*      */ 
/*      */ 
/* 7766 */         if (childhaids.length() > 2)
/*      */         {
/* 7768 */           parentlist = DBUtil.getParentMGsforChildMGs(childhaids);
/* 7769 */           List rootlist = new ArrayList();
/* 7770 */           Set ks = parentlist.keySet();
/* 7771 */           Iterator it = ks.iterator();
/* 7772 */           while (it.hasNext()) {
/* 7773 */             String key = (String)it.next();
/* 7774 */             mainhaids = mainhaids + "'" + ((ArrayList)parentlist.get(key)).get(0) + "',";
/*      */           }
/*      */         }
/* 7777 */         if (mainhaids.length() > 2)
/*      */         {
/* 7779 */           mainhaids = mainhaids.substring(0, mainhaids.length() - 1) + ")";
/* 7780 */           query = "select RESOURCEID as resourceid ,DISPLAYNAME as displayname from AM_ManagedObject join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID left outer join AM_HOLISTICAPPLICATION_OWNERS  on  AM_HOLISTICAPPLICATION_OWNERS.HAID= AM_HOLISTICAPPLICATION.HAID left outer join AM_UserPasswordTable on AM_UserPasswordTable.userid=AM_HOLISTICAPPLICATION_OWNERS.ownerid where  AM_HOLISTICAPPLICATION.HAID in " + mainhaids + " order by RESOURCEID";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 7788 */         String qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_UserPasswordTable,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID AND  AM_UserPasswordTable.USERNAME='" + userName + "' AND USERID=OWNERID ORDER BY HAID";
/* 7789 */         if (Constants.isUserResourceEnabled()) {
/* 7790 */           String loginUserid = Constants.getLoginUserid(request);
/* 7791 */           qry = "select AM_HOLISTICAPPLICATION.HAID,TYPE from AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_HOLISTICAPPLICATION.HAID  AND AM_USERRESOURCESTABLE.USERID=" + loginUserid + " ORDER BY HAID";
/*      */         }
/* 7793 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 7794 */         String sg = "(";
/* 7795 */         List list1 = new ArrayList();
/* 7796 */         while (rs1.next()) {
/* 7797 */           list1.add(rs1.getString(1));
/* 7798 */           if (rs1.getInt(2) == 1) {
/* 7799 */             sg = sg + "'" + rs1.getString(1) + "',";
/*      */           }
/*      */         }
/* 7802 */         if (sg.length() > 2)
/*      */         {
/* 7804 */           sg = sg.substring(0, sg.length() - 1) + ")";
/* 7805 */           Hashtable sglist = DBUtil.getParentMGsforChildMGs(sg);
/* 7806 */           Set s1 = sglist.keySet();
/* 7807 */           Iterator it = s1.iterator();
/* 7808 */           String key; while (it.hasNext()) {
/* 7809 */             key = (String)it.next();
/* 7810 */             List l1 = (ArrayList)sglist.get(key);
/* 7811 */             for (Object temp : l1)
/*      */             {
/* 7813 */               if (list1.contains(temp))
/*      */               {
/*      */ 
/* 7816 */                 list1.remove(key);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 7821 */         String haids = "(";
/* 7822 */         if ((list1 != null) && (list1.size() > 0)) {
/* 7823 */           for (int i = 0; i < list1.size(); i++) {
/* 7824 */             haids = haids + "'" + list1.get(i) + "',";
/*      */           }
/* 7826 */           if (haids.length() > 2) {
/* 7827 */             haids = haids.substring(0, haids.length() - 1) + ")";
/*      */             
/* 7829 */             query = "select RESOURCEID as resourceid ,DISPLAYNAME as displayname from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  AND HAID in " + haids + " order by RESOURCEID";
/*      */           }
/*      */         }
/* 7832 */         if (rs1 != null)
/*      */         {
/* 7834 */           AMConnectionPool.closeStatement(rs1);
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception err)
/*      */     {
/* 7842 */       err.printStackTrace();
/*      */     }
/*      */     try {
/*      */       Vector resid;
/*      */       Vector disnam;
/*      */       Vector type;
/* 7848 */       if (dashtype == 1)
/*      */       {
/* 7850 */         if (query != null) {
/* 7851 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 7852 */           while (rs.next()) {
/* 7853 */             int resid = rs.getInt("resourceid");
/* 7854 */             String dispname = rs.getString("displayname");
/* 7855 */             dispname = EnterpriseUtil.decodeString(dispname);
/*      */             
/* 7857 */             String type = "HAI";
/* 7858 */             toret.put(type + "$" + resid + "#" + dispname, getAvailabilityHistory(resid, period));
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 7866 */         resid = new Vector();
/* 7867 */         disnam = new Vector();
/* 7868 */         type = new Vector();
/*      */         try
/*      */         {
/* 7871 */           if (query != null) {
/* 7872 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 7873 */             while (rs.next())
/*      */             {
/* 7875 */               resid.add(rs.getString(1));
/* 7876 */               disnam.add(rs.getString(2));
/*      */               
/* 7878 */               type.add("HAI");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception eee)
/*      */         {
/* 7886 */           eee.printStackTrace();
/*      */         } }
/* 7888 */       return getHealthHistory(resid, disnam, period, type);
/*      */     }
/*      */     catch (Exception e) {
/* 7891 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 7894 */       if (rs != null) {
/*      */         try {
/* 7896 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 7898 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getGraphDataForConfigAttListInOrder(String resType)
/*      */   {
/* 7907 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7908 */     ResultSet rs = null;
/* 7909 */     ArrayList configList = getGraphDataForConfigAttList(resType);
/*      */     
/* 7911 */     String resAttIDQuery = "select ATTRIBUTEID from AM_ATTRIBUTES where ATTRIBUTE='ResponseTime' and RESOURCETYPE in " + Constants.resourceTypes;
/*      */     
/* 7913 */     Vector resptimeAttIDs = new Vector();
/*      */     try {
/* 7915 */       rs = AMConnectionPool.executeQueryStmt(resAttIDQuery);
/* 7916 */       while (rs.next())
/*      */       {
/* 7918 */         resptimeAttIDs.add(rs.getString("ATTRIBUTEID"));
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 7923 */       ee.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 7927 */       closeResultSet(rs);
/*      */     }
/* 7929 */     ArrayList graphData = new ArrayList();
/* 7930 */     for (int i = 0; i < configList.size(); i++)
/*      */     {
/* 7932 */       ArrayList a1 = (ArrayList)configList.get(i);
/* 7933 */       String attributeid = (String)a1.get(1);
/* 7934 */       if ((resptimeAttIDs != null) && (resptimeAttIDs.contains(attributeid)))
/*      */       {
/* 7936 */         graphData.add(0, a1);
/*      */       }
/*      */       else
/*      */       {
/* 7940 */         graphData.add(a1);
/*      */       }
/*      */     }
/* 7943 */     return graphData;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList getGlanceAttListInOrder(String type, ArrayList attList)
/*      */   {
/* 7949 */     ArrayList retList = new ArrayList();
/* 7950 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 7951 */     ResultSet rs = null;
/* 7952 */     String resAttIDQuery = "select ATTRIBUTEID,ATTRIBUTE from AM_ATTRIBUTES where (ATTRIBUTE='ResponseTime' or ATTRIBUTE='Physical Memory Utilization' or ATTRIBUTE='CPU Utilization') and RESOURCETYPE in " + Constants.resourceTypes + " order by ATTRIBUTE desc";
/* 7953 */     String resGroupQuery = "select RESOURCEGROUP from AM_ManagedResourceType where SUBGROUP in ('" + type + "')";
/* 7954 */     boolean isServer = false;
/* 7955 */     Vector resptimeAttIDs = new Vector();
/* 7956 */     Vector phyMemAttIDs = new Vector();
/* 7957 */     Vector cpuAttIDs = new Vector();
/*      */     try {
/* 7959 */       rs = AMConnectionPool.executeQueryStmt(resAttIDQuery);
/* 7960 */       while (rs.next())
/*      */       {
/* 7962 */         if ("ResponseTime".equals(rs.getString("ATTRIBUTE")))
/*      */         {
/* 7964 */           resptimeAttIDs.add(rs.getString("ATTRIBUTEID"));
/*      */         }
/* 7966 */         else if ("Physical Memory Utilization".equals(rs.getString("ATTRIBUTE")))
/*      */         {
/* 7968 */           phyMemAttIDs.add(rs.getString("ATTRIBUTEID"));
/*      */         }
/* 7970 */         else if ("CPU Utilization".equals(rs.getString("ATTRIBUTE")))
/*      */         {
/* 7972 */           cpuAttIDs.add(rs.getString("ATTRIBUTEID"));
/*      */         }
/*      */       }
/* 7975 */       closeResultSet(rs);
/* 7976 */       if (type != null)
/*      */       {
/* 7978 */         rs = AMConnectionPool.executeQueryStmt(resGroupQuery);
/* 7979 */         while (rs.next())
/*      */         {
/* 7981 */           if ("SYS".equals(rs.getString("RESOURCEGROUP")))
/*      */           {
/* 7983 */             isServer = true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 7990 */       ee.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 7994 */       closeResultSet(rs);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 7999 */       Hashtable respHash = new Hashtable();
/* 8000 */       Hashtable cpuHash = new Hashtable();
/* 8001 */       Hashtable phyMemhash = new Hashtable();
/* 8002 */       for (int i = 0; i < attList.size(); i++)
/*      */       {
/* 8004 */         Hashtable attribProp = (Hashtable)attList.get(i);
/* 8005 */         Vector attids = (Vector)attribProp.get("ATTRIBUTEID");
/* 8006 */         if ((resptimeAttIDs != null) && (resptimeAttIDs.containsAll(attids)))
/*      */         {
/*      */ 
/* 8009 */           respHash = attribProp;
/*      */         }
/* 8011 */         else if ((cpuAttIDs != null) && (cpuAttIDs.containsAll(attids)))
/*      */         {
/* 8013 */           cpuHash = attribProp;
/*      */         }
/* 8015 */         else if ((phyMemAttIDs != null) && (phyMemAttIDs.containsAll(attids)))
/*      */         {
/* 8017 */           phyMemhash = attribProp;
/*      */         }
/*      */         else
/*      */         {
/* 8021 */           retList.add(attribProp);
/*      */         }
/*      */       }
/*      */       
/* 8025 */       if ((respHash != null) && (respHash.size() > 0))
/*      */       {
/* 8027 */         retList.add(0, respHash);
/*      */       }
/* 8029 */       if ((cpuHash != null) && (cpuHash.size() > 0))
/*      */       {
/* 8031 */         retList.add(1, cpuHash);
/*      */       }
/* 8033 */       if ((phyMemhash != null) && (phyMemhash.size() > 0))
/*      */       {
/* 8035 */         retList.add(2, phyMemhash);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 8040 */       ex.printStackTrace();
/*      */     }
/* 8042 */     return retList;
/*      */   }
/*      */   
/*      */   public static final Properties calculateAvailabilityDetails(String resourceid, long startTime, long endTime) {
/* 8046 */     Properties summary = new Properties();
/*      */     try {
/* 8048 */       DBUtil db = new DBUtil();
/* 8049 */       ReportUtilities rep = new ReportUtilities();
/* 8050 */       String DBVal = null;
/*      */       
/* 8052 */       DBVal = db.getGlobalConfigValueForMGAvailability();
/*      */       
/* 8054 */       long totalDowntime = 0L;
/* 8055 */       long totalDuration = 0L;
/* 8056 */       long totalUnmanagedtime = 0L;
/* 8057 */       long totalScheduledtime = 0L;
/* 8058 */       int count = 1;
/* 8059 */       ResultSet set = null;
/* 8060 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       
/* 8062 */       if (("true".equalsIgnoreCase(DBVal)) && (rep.isHAI(resourceid))) {
/*      */         try {
/* 8064 */           Properties p1 = getMonitorGroupAvailability(resourceid, "4", startTime, endTime);
/* 8065 */           String down = p1.getProperty("downtime");
/* 8066 */           String up = p1.getProperty("uptime");
/* 8067 */           summary.setProperty("up", up);
/* 8068 */           String downPercent = p1.getProperty("unavailable");
/* 8069 */           String upPercent = p1.getProperty("available");
/*      */           
/* 8071 */           summary.setProperty("totaldowntime", down);
/* 8072 */           summary.setProperty("uptimepercentage", p1.getProperty("available"));
/* 8073 */           summary.setProperty("mttr", p1.getProperty("MTTR"));
/* 8074 */           summary.setProperty("mtbf", p1.getProperty("MTBF"));
/* 8075 */           summary.setProperty("DowntimePercentage", p1.getProperty("unavailable"));
/* 8076 */           summary.setProperty("UnManagedPercentage", p1.getProperty("ServicesUnMgPercent"));
/* 8077 */           summary.setProperty("ScheduledPercentage", p1.getProperty("ServicesSchPercent"));
/* 8078 */           summary.setProperty("UnManagedTime", p1.getProperty("unmanagedtime"));
/* 8079 */           summary.setProperty("ScheduledTime", p1.getProperty("scheduledtime"));
/* 8080 */           summary.setProperty("MGService", "true");
/*      */         } catch (Exception ext) {
/* 8082 */           ext.printStackTrace();
/*      */         }
/*      */         
/* 8085 */       } else if (("false".equalsIgnoreCase(DBVal)) && (rep.isHAI(resourceid))) {
/*      */         try {
/* 8087 */           Properties p1 = getMonitorGroupAvailability(resourceid, "4", startTime, endTime);
/*      */           
/* 8089 */           System.out.println("THE ACTION CLASS ====> :" + p1);
/* 8090 */           String down = p1.getProperty("downtime");
/*      */           
/* 8092 */           String up = p1.getProperty("uptime");
/* 8093 */           String downPercent = p1.getProperty("unavailable");
/* 8094 */           String upPercent = p1.getProperty("available");
/* 8095 */           summary.setProperty("up", up);
/* 8096 */           summary.setProperty("totaldowntime", down);
/* 8097 */           summary.setProperty("uptimepercentage", p1.getProperty("available"));
/*      */           
/* 8099 */           summary.setProperty("mttr", p1.getProperty("MTTR"));
/*      */           
/* 8101 */           summary.setProperty("mtbf", p1.getProperty("MTBF"));
/* 8102 */           summary.setProperty("DowntimePercentage", p1.getProperty("unavailable"));
/* 8103 */           summary.setProperty("UnManagedPercentage", p1.getProperty("ServicesUnMgPercent"));
/* 8104 */           summary.setProperty("ScheduledPercentage", p1.getProperty("ServicesSchPercent"));
/* 8105 */           summary.setProperty("UnManagedTime", p1.getProperty("unmanagedtime"));
/* 8106 */           summary.setProperty("ScheduledTime", p1.getProperty("scheduledtime"));
/* 8107 */           summary.setProperty("AppCluster", "true");
/*      */         } catch (Exception ext) {
/* 8109 */           ext.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/*      */ 
/* 8118 */           String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID in (" + resourceid + ") and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by TotalDownTime desc";
/* 8119 */           if ("true".equals(Constants.addMaintenanceToAvailablity))
/*      */           {
/* 8121 */             query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount,TYPE from AM_MO_DowntimeData where RESID in (" + resourceid + ") and TYPE=1 and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID,TYPE order by TotalDownTime desc";
/*      */           }
/* 8123 */           totalDuration = endTime - startTime;
/* 8124 */           set = AMConnectionPool.executeQueryStmt(query);
/* 8125 */           while (set.next()) {
/* 8126 */             int typeID = -1;
/* 8127 */             count = set.getInt("DownCount");
/* 8128 */             typeID = set.getInt("TYPE");
/* 8129 */             if (typeID == 1)
/*      */             {
/* 8131 */               totalDowntime = set.getLong("TotalDownTime");
/*      */             }
/* 8133 */             else if (typeID == 2)
/*      */             {
/* 8135 */               totalUnmanagedtime = set.getLong("TotalDownTime");
/*      */             }
/*      */             else
/*      */             {
/* 8139 */               totalScheduledtime = set.getLong("TotalDownTime");
/*      */             }
/*      */           }
/* 8142 */           long uptime = totalDuration - (totalDowntime + totalUnmanagedtime + totalScheduledtime);
/* 8143 */           String down = format(totalDowntime);
/* 8144 */           String up = format(uptime);
/* 8145 */           String unmanaged = format(totalUnmanagedtime);
/* 8146 */           String scheduled = format(totalScheduledtime);
/* 8147 */           float upPercent = (float)uptime / (float)totalDuration * 100.0F;
/* 8148 */           float downPercent = (float)totalDowntime / (float)totalDuration * 100.0F;
/* 8149 */           float unmanagedPercent = (float)totalUnmanagedtime / (float)totalDuration * 100.0F;
/* 8150 */           float scheduledPercent = (float)totalScheduledtime / (float)totalDuration * 100.0F;
/*      */           
/* 8152 */           summary.setProperty("showManaged", "false");
/* 8153 */           if (totalUnmanagedtime > 0L) {
/* 8154 */             summary.setProperty("showManaged", "true");
/*      */           }
/* 8156 */           summary.setProperty("showScheduled", "false");
/* 8157 */           if (totalScheduledtime > 0L) {
/* 8158 */             summary.setProperty("showScheduled", "true");
/*      */           }
/* 8160 */           summary.setProperty("up", up);
/*      */           
/*      */ 
/* 8163 */           summary.setProperty("totaldowntime", down);
/* 8164 */           summary.setProperty("uptimepercentage", String.valueOf(Math.round(upPercent * 1000.0F) / 1000.0F));
/* 8165 */           long mttr = totalDowntime / count;
/* 8166 */           summary.setProperty("mttr", format(mttr));
/* 8167 */           long mtbf = uptime / count;
/* 8168 */           summary.setProperty("mtbf", format(mtbf));
/* 8169 */           summary.setProperty("UnManagedTime", unmanaged);
/* 8170 */           summary.setProperty("ScheduledTime", scheduled);
/* 8171 */           summary.setProperty("DowntimePercentage", String.valueOf(Math.round(downPercent * 1000.0F) / 1000.0F));
/* 8172 */           summary.setProperty("UnManagedPercentage", String.valueOf(Math.round(unmanagedPercent * 1000.0F) / 1000.0F));
/* 8173 */           summary.setProperty("ScheduledPercentage", String.valueOf(Math.round(scheduledPercent * 1000.0F) / 1000.0F));
/* 8174 */           summary.setProperty("Monitor", "true");
/*      */         } catch (Exception exp) {
/* 8176 */           exp.printStackTrace();
/* 8177 */           AMLog.fatal("Exception in getAvailabilityData", exp);
/*      */         }
/*      */         finally {
/* 8180 */           closeResultSet(set);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 8186 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 8189 */     return summary;
/*      */   }
/*      */   
/*      */   public static Properties getAllSiteProps(String customerId)
/*      */   {
/* 8194 */     AMLog.debug("getAllSiteProps called for " + customerId);
/* 8195 */     ResultSet rs = null;
/* 8196 */     siteProps = new Properties();
/*      */     
/*      */     try
/*      */     {
/* 8200 */       String sitePropsQuery = "SELECT SITE.SITEID,SITE.SITENAME,SITE.CUSTOMERID,SITE.CONTACTPERSON,SITE.CONTACTEMAIL FROM CUSTOMERINFO AS CUST,SITEINFO AS SITE WHERE CUST.CUSTOMERID=SITE.CUSTOMERID AND CUST.CUSTOMERID=" + customerId;
/* 8201 */       rs = AMConnectionPool.executeQueryStmt(sitePropsQuery);
/* 8202 */       while (rs.next())
/*      */       {
/* 8204 */         String siteId = rs.getString("SITEID");
/* 8205 */         String siteName = rs.getString("SITENAME");
/* 8206 */         if ((siteId != null) && (siteName != null)) {
/* 8207 */           siteProps.setProperty(siteId, siteName);
/*      */         }
/*      */       }
/* 8210 */       AMLog.debug("siteProps" + siteProps);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 8224 */       return siteProps;
/*      */     }
/*      */     catch (Exception exx)
/*      */     {
/* 8212 */       EnterpriseUtil.sendError("/jsp/formpages/Customer_Segmentation_Error.jsp");
/* 8213 */       exx.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 8217 */         if (rs != null) {
/* 8218 */           rs.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 8221 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType)
/*      */   {
/* 8229 */     ArrayList al = getAttributeCollection(resID, attID, resType, false);
/* 8230 */     return al;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean checkandUpdateSecondLevelAttributes(String attrID)
/*      */   {
/* 8459 */     String[] attrDets = DBUtil.getAttributeExtDetails(attrID);
/* 8460 */     if (attrDets[4].equals("2"))
/*      */     {
/* 8462 */       ReportUtil.updateSecondLevelAttributes(attrID);
/* 8463 */       return true;
/*      */     }
/* 8465 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Map getAttributesForResourceGroups(String resourceGroup)
/*      */   {
/* 8472 */     Map restypeAttribute = new HashMap();
/*      */     try
/*      */     {
/* 8475 */       if (DBUtil.resourceTypeList.containsKey(resourceGroup))
/*      */       {
/* 8477 */         Map hash = (Map)DBUtil.resourceTypeList.get(resourceGroup);
/*      */         
/* 8479 */         Iterator itr = hash.keySet().iterator();
/*      */         
/* 8481 */         while (itr.hasNext())
/*      */         {
/* 8483 */           String name = itr.next().toString();
/* 8484 */           String restype = (String)hash.get(name);
/*      */           
/* 8486 */           if ((resourceGroup.equals("URL")) && ((restype.equals("UrlMonitor")) || (restype.equals("UrlSeq")) || (restype.equals("RBM")) || (restype.equals("RBMURL"))))
/*      */           {
/* 8488 */             ArrayList a1 = getAttributeListForResourcetype(restype);
/* 8489 */             restypeAttribute.put(name, a1);
/*      */           }
/* 8491 */           else if ((resourceGroup.equals("TM")) && (!restype.equals("WTA")))
/*      */           {
/* 8493 */             ArrayList a1 = getAttributeListForResourcetype(restype);
/* 8494 */             restypeAttribute.put(name, a1);
/*      */           }
/* 8496 */           else if ((!resourceGroup.equals("URL")) || (!resourceGroup.equals("TM")))
/*      */           {
/* 8498 */             ArrayList a1 = getAttributeListForResourcetype(restype);
/* 8499 */             restypeAttribute.put(name, a1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 8506 */       ex.printStackTrace();
/*      */     }
/* 8508 */     return restypeAttribute;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   public ArrayList getAttributeCollection(String resID, String attID, String resType, boolean getAllAttributes)
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aconst_null
/*      */     //   1: astore 5
/*      */     //   3: invokestatic 3040	com/adventnet/appmanager/db/AMConnectionPool:getInstance	()Lcom/adventnet/appmanager/db/AMConnectionPool;
/*      */     //   6: astore 6
/*      */     //   8: new 2541	java/util/ArrayList
/*      */     //   11: dup
/*      */     //   12: invokespecial 3252	java/util/ArrayList:<init>	()V
/*      */     //   15: astore 7
/*      */     //   17: invokestatic 3176	com/adventnet/appmanager/util/ReportUtil:getAttributeList	()Ljava/util/List;
/*      */     //   20: astore 8
/*      */     //   22: invokestatic 3175	com/adventnet/appmanager/util/ReportUtil:getAllSecondLevelAttribute	()Ljava/util/List;
/*      */     //   25: astore 9
/*      */     //   27: aconst_null
/*      */     //   28: astore 10
/*      */     //   30: aconst_null
/*      */     //   31: astore 11
/*      */     //   33: aload 9
/*      */     //   35: aload_2
/*      */     //   36: invokeinterface 3340 2 0
/*      */     //   41: ifne +11 -> 52
/*      */     //   44: aload_0
/*      */     //   45: aload_2
/*      */     //   46: invokespecial 3066	com/adventnet/appmanager/reporting/ReportUtilities:checkandUpdateSecondLevelAttributes	(Ljava/lang/String;)Z
/*      */     //   49: ifeq +146 -> 195
/*      */     //   52: aconst_null
/*      */     //   53: astore 12
/*      */     //   55: new 2533	java/lang/StringBuilder
/*      */     //   58: dup
/*      */     //   59: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   62: ldc_w 2261
/*      */     //   65: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   68: aload_1
/*      */     //   69: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   72: ldc_w 2027
/*      */     //   75: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   78: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   81: astore 13
/*      */     //   83: aload 6
/*      */     //   85: pop
/*      */     //   86: aload 13
/*      */     //   88: invokestatic 3042	com/adventnet/appmanager/db/AMConnectionPool:executeQueryStmt	(Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   91: astore 12
/*      */     //   93: aload 12
/*      */     //   95: invokeinterface 3319 1 0
/*      */     //   100: ifeq +26 -> 126
/*      */     //   103: aload_1
/*      */     //   104: astore 11
/*      */     //   106: aload 12
/*      */     //   108: ldc 69
/*      */     //   110: invokeinterface 3328 2 0
/*      */     //   115: astore_1
/*      */     //   116: aload 12
/*      */     //   118: ldc 77
/*      */     //   120: invokeinterface 3328 2 0
/*      */     //   125: astore_3
/*      */     //   126: aload 12
/*      */     //   128: invokeinterface 3317 1 0
/*      */     //   133: goto +62 -> 195
/*      */     //   136: astore 14
/*      */     //   138: aload 14
/*      */     //   140: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   143: goto +52 -> 195
/*      */     //   146: astore 14
/*      */     //   148: aload 14
/*      */     //   150: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   153: aload 12
/*      */     //   155: invokeinterface 3317 1 0
/*      */     //   160: goto +35 -> 195
/*      */     //   163: astore 14
/*      */     //   165: aload 14
/*      */     //   167: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   170: goto +25 -> 195
/*      */     //   173: astore 15
/*      */     //   175: aload 12
/*      */     //   177: invokeinterface 3317 1 0
/*      */     //   182: goto +10 -> 192
/*      */     //   185: astore 16
/*      */     //   187: aload 16
/*      */     //   189: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   192: aload 15
/*      */     //   194: athrow
/*      */     //   195: ldc_w 2501
/*      */     //   198: aload_3
/*      */     //   199: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   202: ifeq +11 -> 213
/*      */     //   205: invokestatic 3177	com/adventnet/appmanager/util/ReportUtil:getAttributesForWindows	()Ljava/util/List;
/*      */     //   208: astore 10
/*      */     //   210: goto +152 -> 362
/*      */     //   213: aload_1
/*      */     //   214: ifnull +49 -> 263
/*      */     //   217: aload_1
/*      */     //   218: ldc 2
/*      */     //   220: if_acmpeq +43 -> 263
/*      */     //   223: aload_3
/*      */     //   224: ldc_w 2205
/*      */     //   227: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   230: ifne +43 -> 273
/*      */     //   233: aload_3
/*      */     //   234: ldc_w 2297
/*      */     //   237: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   240: ifne +33 -> 273
/*      */     //   243: aload_3
/*      */     //   244: ldc_w 2288
/*      */     //   247: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   250: ifne +23 -> 273
/*      */     //   253: aload_3
/*      */     //   254: ldc_w 2250
/*      */     //   257: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   260: ifne +13 -> 273
/*      */     //   263: aload_3
/*      */     //   264: ldc_w 2229
/*      */     //   267: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   270: ifeq +34 -> 304
/*      */     //   273: new 2533	java/lang/StringBuilder
/*      */     //   276: dup
/*      */     //   277: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   280: aload_3
/*      */     //   281: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   284: ldc 34
/*      */     //   286: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   289: aload_1
/*      */     //   290: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   293: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   296: invokestatic 3179	com/adventnet/appmanager/util/ReportUtil:getAttribsForResType	(Ljava/lang/String;)Ljava/util/List;
/*      */     //   299: astore 10
/*      */     //   301: goto +61 -> 362
/*      */     //   304: aload_1
/*      */     //   305: ifnull +51 -> 356
/*      */     //   308: aload_1
/*      */     //   309: ldc 2
/*      */     //   311: if_acmpeq +45 -> 356
/*      */     //   314: aload_3
/*      */     //   315: ldc_w 2348
/*      */     //   318: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   321: ifeq +35 -> 356
/*      */     //   324: new 2533	java/lang/StringBuilder
/*      */     //   327: dup
/*      */     //   328: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   331: aload_3
/*      */     //   332: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   335: ldc 34
/*      */     //   337: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   340: aload 11
/*      */     //   342: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   345: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   348: invokestatic 3179	com/adventnet/appmanager/util/ReportUtil:getAttribsForResType	(Ljava/lang/String;)Ljava/util/List;
/*      */     //   351: astore 10
/*      */     //   353: goto +9 -> 362
/*      */     //   356: aload_3
/*      */     //   357: invokestatic 3179	com/adventnet/appmanager/util/ReportUtil:getAttribsForResType	(Ljava/lang/String;)Ljava/util/List;
/*      */     //   360: astore 10
/*      */     //   362: aconst_null
/*      */     //   363: astore 12
/*      */     //   365: iload 4
/*      */     //   367: ifne +8 -> 375
/*      */     //   370: invokestatic 3174	com/adventnet/appmanager/util/ReportUtil:getReportEnabledAttributes	()Ljava/util/ArrayList;
/*      */     //   373: astore 12
/*      */     //   375: new 2541	java/util/ArrayList
/*      */     //   378: dup
/*      */     //   379: invokespecial 3252	java/util/ArrayList:<init>	()V
/*      */     //   382: astore 13
/*      */     //   384: new 2541	java/util/ArrayList
/*      */     //   387: dup
/*      */     //   388: invokespecial 3252	java/util/ArrayList:<init>	()V
/*      */     //   391: astore 14
/*      */     //   393: new 2541	java/util/ArrayList
/*      */     //   396: dup
/*      */     //   397: invokespecial 3252	java/util/ArrayList:<init>	()V
/*      */     //   400: astore 15
/*      */     //   402: ldc_w 2297
/*      */     //   405: aload_3
/*      */     //   406: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   409: ifeq +136 -> 545
/*      */     //   412: ldc 2
/*      */     //   414: astore 16
/*      */     //   416: new 2533	java/lang/StringBuilder
/*      */     //   419: dup
/*      */     //   420: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   423: ldc_w 2418
/*      */     //   426: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   429: aload_1
/*      */     //   430: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   433: ldc_w 2072
/*      */     //   436: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   439: aload_1
/*      */     //   440: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   443: ldc_w 2071
/*      */     //   446: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   449: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   452: astore 16
/*      */     //   454: aload 6
/*      */     //   456: pop
/*      */     //   457: aload 16
/*      */     //   459: invokestatic 3042	com/adventnet/appmanager/db/AMConnectionPool:executeQueryStmt	(Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   462: astore 5
/*      */     //   464: aload 5
/*      */     //   466: invokeinterface 3319 1 0
/*      */     //   471: ifeq +26 -> 497
/*      */     //   474: aload 5
/*      */     //   476: ldc_w 2124
/*      */     //   479: invokeinterface 3328 2 0
/*      */     //   484: astore 17
/*      */     //   486: aload 15
/*      */     //   488: aload 17
/*      */     //   490: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   493: pop
/*      */     //   494: goto -30 -> 464
/*      */     //   497: aload 5
/*      */     //   499: ifnull +46 -> 545
/*      */     //   502: aload 5
/*      */     //   504: invokestatic 3041	com/adventnet/appmanager/db/AMConnectionPool:closeStatement	(Ljava/sql/ResultSet;)V
/*      */     //   507: goto +38 -> 545
/*      */     //   510: astore 17
/*      */     //   512: aload 17
/*      */     //   514: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   517: aload 5
/*      */     //   519: ifnull +26 -> 545
/*      */     //   522: aload 5
/*      */     //   524: invokestatic 3041	com/adventnet/appmanager/db/AMConnectionPool:closeStatement	(Ljava/sql/ResultSet;)V
/*      */     //   527: goto +18 -> 545
/*      */     //   530: astore 18
/*      */     //   532: aload 5
/*      */     //   534: ifnull +8 -> 542
/*      */     //   537: aload 5
/*      */     //   539: invokestatic 3041	com/adventnet/appmanager/db/AMConnectionPool:closeStatement	(Ljava/sql/ResultSet;)V
/*      */     //   542: aload 18
/*      */     //   544: athrow
/*      */     //   545: iconst_0
/*      */     //   546: istore 16
/*      */     //   548: iload 16
/*      */     //   550: aload 10
/*      */     //   552: invokeinterface 3337 1 0
/*      */     //   557: if_icmpge +1043 -> 1600
/*      */     //   560: aload 10
/*      */     //   562: iload 16
/*      */     //   564: invokeinterface 3338 2 0
/*      */     //   569: invokevirtual 3214	java/lang/Object:toString	()Ljava/lang/String;
/*      */     //   572: astore 17
/*      */     //   574: aload 17
/*      */     //   576: ldc 34
/*      */     //   578: invokevirtual 3229	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
/*      */     //   581: astore 18
/*      */     //   583: iconst_0
/*      */     //   584: istore 19
/*      */     //   586: iload 4
/*      */     //   588: ifne +51 -> 639
/*      */     //   591: aload 8
/*      */     //   593: aload 18
/*      */     //   595: iconst_0
/*      */     //   596: aaload
/*      */     //   597: invokeinterface 3340 2 0
/*      */     //   602: ifeq +9 -> 611
/*      */     //   605: iconst_1
/*      */     //   606: istore 19
/*      */     //   608: goto +34 -> 642
/*      */     //   611: aload 12
/*      */     //   613: aload 18
/*      */     //   615: iconst_0
/*      */     //   616: aaload
/*      */     //   617: invokevirtual 3256	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
/*      */     //   620: ifne +13 -> 633
/*      */     //   623: aload 18
/*      */     //   625: iconst_0
/*      */     //   626: aaload
/*      */     //   627: invokestatic 3172	com/adventnet/appmanager/util/ReportUtil:getReportEnabledAttributesForWindows	(Ljava/lang/String;)Z
/*      */     //   630: ifeq +12 -> 642
/*      */     //   633: iconst_1
/*      */     //   634: istore 19
/*      */     //   636: goto +6 -> 642
/*      */     //   639: iconst_1
/*      */     //   640: istore 19
/*      */     //   642: ldc_w 2297
/*      */     //   645: aload_3
/*      */     //   646: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   649: ifeq +37 -> 686
/*      */     //   652: aload 15
/*      */     //   654: aload 18
/*      */     //   656: iconst_0
/*      */     //   657: aaload
/*      */     //   658: invokevirtual 3256	java/util/ArrayList:contains	(Ljava/lang/Object;)Z
/*      */     //   661: ifne +16 -> 677
/*      */     //   664: aload 18
/*      */     //   666: iconst_0
/*      */     //   667: aaload
/*      */     //   668: ldc_w 2103
/*      */     //   671: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   674: ifeq +9 -> 683
/*      */     //   677: iconst_1
/*      */     //   678: istore 19
/*      */     //   680: goto +6 -> 686
/*      */     //   683: iconst_0
/*      */     //   684: istore 19
/*      */     //   686: iload 19
/*      */     //   688: ifeq +906 -> 1594
/*      */     //   691: new 2533	java/lang/StringBuilder
/*      */     //   694: dup
/*      */     //   695: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   698: ldc_w 2260
/*      */     //   701: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   704: aload_1
/*      */     //   705: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   708: ldc_w 2026
/*      */     //   711: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   714: aload 18
/*      */     //   716: iconst_0
/*      */     //   717: aaload
/*      */     //   718: invokestatic 3079	com/adventnet/appmanager/reporting/ReportUtilities:getResourceTypeForAttribute	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   721: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   724: ldc_w 2024
/*      */     //   727: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   730: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   733: astore 20
/*      */     //   735: ldc_w 2145
/*      */     //   738: aload 18
/*      */     //   740: iconst_0
/*      */     //   741: aaload
/*      */     //   742: invokestatic 3079	com/adventnet/appmanager/reporting/ReportUtilities:getResourceTypeForAttribute	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   745: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   748: ifeq +106 -> 854
/*      */     //   751: new 2533	java/lang/StringBuilder
/*      */     //   754: dup
/*      */     //   755: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   758: ldc_w 2258
/*      */     //   761: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   764: aload_1
/*      */     //   765: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   768: ldc_w 1932
/*      */     //   771: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   774: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   777: astore 21
/*      */     //   779: aload 21
/*      */     //   781: invokestatic 3161	com/adventnet/appmanager/util/DBUtil:getRowsForSingleColumn	(Ljava/lang/String;)Ljava/util/ArrayList;
/*      */     //   784: astore 22
/*      */     //   786: new 2555	java/util/Vector
/*      */     //   789: dup
/*      */     //   790: invokespecial 3306	java/util/Vector:<init>	()V
/*      */     //   793: astore 23
/*      */     //   795: aload 23
/*      */     //   797: aload 22
/*      */     //   799: invokevirtual 3314	java/util/Vector:addAll	(Ljava/util/Collection;)Z
/*      */     //   802: pop
/*      */     //   803: new 2533	java/lang/StringBuilder
/*      */     //   806: dup
/*      */     //   807: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   810: ldc_w 2259
/*      */     //   813: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   816: ldc_w 2119
/*      */     //   819: aload 23
/*      */     //   821: invokestatic 3103	com/adventnet/appmanager/reporting/ReportUtilities:getCondition	(Ljava/lang/String;Ljava/util/Vector;)Ljava/lang/String;
/*      */     //   824: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   827: ldc_w 2120
/*      */     //   830: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   833: aload 18
/*      */     //   835: iconst_0
/*      */     //   836: aaload
/*      */     //   837: invokestatic 3079	com/adventnet/appmanager/reporting/ReportUtilities:getResourceTypeForAttribute	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   840: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   843: ldc_w 2024
/*      */     //   846: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   849: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   852: astore 20
/*      */     //   854: aload 18
/*      */     //   856: iconst_0
/*      */     //   857: aaload
/*      */     //   858: ldc_w 2109
/*      */     //   861: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   864: ifeq +204 -> 1068
/*      */     //   867: aload 6
/*      */     //   869: pop
/*      */     //   870: aload 20
/*      */     //   872: invokestatic 3042	com/adventnet/appmanager/db/AMConnectionPool:executeQueryStmt	(Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   875: astore 5
/*      */     //   877: aload 5
/*      */     //   879: invokeinterface 3319 1 0
/*      */     //   884: ifeq +112 -> 996
/*      */     //   887: new 2533	java/lang/StringBuilder
/*      */     //   890: dup
/*      */     //   891: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   894: aload 5
/*      */     //   896: ldc 69
/*      */     //   898: invokeinterface 3328 2 0
/*      */     //   903: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   906: ldc 34
/*      */     //   908: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   911: aload 18
/*      */     //   913: iconst_0
/*      */     //   914: aaload
/*      */     //   915: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   918: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   921: astore 21
/*      */     //   923: aload 5
/*      */     //   925: ldc_w 2146
/*      */     //   928: invokeinterface 3328 2 0
/*      */     //   933: astore 22
/*      */     //   935: aload 22
/*      */     //   937: invokestatic 3166	com/adventnet/appmanager/util/EnterpriseUtil:decodeString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   940: astore 22
/*      */     //   942: new 2553	java/util/Properties
/*      */     //   945: dup
/*      */     //   946: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   949: astore 23
/*      */     //   951: aload 23
/*      */     //   953: ldc_w 2387
/*      */     //   956: aload 22
/*      */     //   958: aload 22
/*      */     //   960: ldc 49
/*      */     //   962: invokevirtual 3225	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   965: iconst_1
/*      */     //   966: iadd
/*      */     //   967: invokevirtual 3221	java/lang/String:substring	(I)Ljava/lang/String;
/*      */     //   970: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   973: pop
/*      */     //   974: aload 23
/*      */     //   976: ldc_w 2497
/*      */     //   979: aload 21
/*      */     //   981: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   984: pop
/*      */     //   985: aload 7
/*      */     //   987: aload 23
/*      */     //   989: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   992: pop
/*      */     //   993: goto -116 -> 877
/*      */     //   996: aload 5
/*      */     //   998: invokeinterface 3317 1 0
/*      */     //   1003: goto +591 -> 1594
/*      */     //   1006: astore 21
/*      */     //   1008: aload 21
/*      */     //   1010: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1013: goto +581 -> 1594
/*      */     //   1016: astore 21
/*      */     //   1018: aload 21
/*      */     //   1020: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1023: aload 5
/*      */     //   1025: invokeinterface 3317 1 0
/*      */     //   1030: goto +35 -> 1065
/*      */     //   1033: astore 21
/*      */     //   1035: aload 21
/*      */     //   1037: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1040: goto +25 -> 1065
/*      */     //   1043: astore 24
/*      */     //   1045: aload 5
/*      */     //   1047: invokeinterface 3317 1 0
/*      */     //   1052: goto +10 -> 1062
/*      */     //   1055: astore 25
/*      */     //   1057: aload 25
/*      */     //   1059: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1062: aload 24
/*      */     //   1064: athrow
/*      */     //   1065: goto +464 -> 1529
/*      */     //   1068: ldc_w 2350
/*      */     //   1071: aload 18
/*      */     //   1073: iconst_0
/*      */     //   1074: aaload
/*      */     //   1075: invokestatic 3079	com/adventnet/appmanager/reporting/ReportUtilities:getResourceTypeForAttribute	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1078: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1081: ifeq +117 -> 1198
/*      */     //   1084: new 2533	java/lang/StringBuilder
/*      */     //   1087: dup
/*      */     //   1088: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1091: aload 11
/*      */     //   1093: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1096: ldc 34
/*      */     //   1098: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1101: aload 18
/*      */     //   1103: iconst_0
/*      */     //   1104: aaload
/*      */     //   1105: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1108: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1111: astore 21
/*      */     //   1113: aload_1
/*      */     //   1114: invokestatic 3158	com/adventnet/appmanager/util/DBUtil:getDisplaynameforResourceID	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1117: astore 22
/*      */     //   1119: new 2553	java/util/Properties
/*      */     //   1122: dup
/*      */     //   1123: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   1126: astore 23
/*      */     //   1128: aload 23
/*      */     //   1130: ldc_w 2387
/*      */     //   1133: new 2533	java/lang/StringBuilder
/*      */     //   1136: dup
/*      */     //   1137: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1140: aload 18
/*      */     //   1142: iconst_1
/*      */     //   1143: aaload
/*      */     //   1144: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1147: ldc 83
/*      */     //   1149: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1152: aload 22
/*      */     //   1154: aload 22
/*      */     //   1156: ldc 49
/*      */     //   1158: invokevirtual 3225	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   1161: iconst_1
/*      */     //   1162: iadd
/*      */     //   1163: invokevirtual 3221	java/lang/String:substring	(I)Ljava/lang/String;
/*      */     //   1166: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1169: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1172: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1175: pop
/*      */     //   1176: aload 23
/*      */     //   1178: ldc_w 2497
/*      */     //   1181: aload 21
/*      */     //   1183: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1186: pop
/*      */     //   1187: aload 7
/*      */     //   1189: aload 23
/*      */     //   1191: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   1194: pop
/*      */     //   1195: goto +399 -> 1594
/*      */     //   1198: ldc_w 2229
/*      */     //   1201: aload 18
/*      */     //   1203: iconst_0
/*      */     //   1204: aaload
/*      */     //   1205: invokestatic 3079	com/adventnet/appmanager/reporting/ReportUtilities:getResourceTypeForAttribute	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1208: invokevirtual 3217	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   1211: ifeq +81 -> 1292
/*      */     //   1214: new 2533	java/lang/StringBuilder
/*      */     //   1217: dup
/*      */     //   1218: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1221: aload_1
/*      */     //   1222: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1225: ldc 34
/*      */     //   1227: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1230: aload 18
/*      */     //   1232: iconst_0
/*      */     //   1233: aaload
/*      */     //   1234: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1237: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1240: astore 21
/*      */     //   1242: aload_1
/*      */     //   1243: invokestatic 3158	com/adventnet/appmanager/util/DBUtil:getDisplaynameforResourceID	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1246: astore 22
/*      */     //   1248: new 2553	java/util/Properties
/*      */     //   1251: dup
/*      */     //   1252: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   1255: astore 23
/*      */     //   1257: aload 23
/*      */     //   1259: ldc_w 2387
/*      */     //   1262: aload 18
/*      */     //   1264: iconst_1
/*      */     //   1265: aaload
/*      */     //   1266: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1269: pop
/*      */     //   1270: aload 23
/*      */     //   1272: ldc_w 2497
/*      */     //   1275: aload 21
/*      */     //   1277: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1280: pop
/*      */     //   1281: aload 7
/*      */     //   1283: aload 23
/*      */     //   1285: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   1288: pop
/*      */     //   1289: goto +305 -> 1594
/*      */     //   1292: aload 9
/*      */     //   1294: aload 18
/*      */     //   1296: iconst_0
/*      */     //   1297: aaload
/*      */     //   1298: invokeinterface 3340 2 0
/*      */     //   1303: ifeq +226 -> 1529
/*      */     //   1306: aload 6
/*      */     //   1308: pop
/*      */     //   1309: aload 20
/*      */     //   1311: invokestatic 3042	com/adventnet/appmanager/db/AMConnectionPool:executeQueryStmt	(Ljava/lang/String;)Ljava/sql/ResultSet;
/*      */     //   1314: astore 5
/*      */     //   1316: aload 5
/*      */     //   1318: invokeinterface 3319 1 0
/*      */     //   1323: ifeq +137 -> 1460
/*      */     //   1326: new 2533	java/lang/StringBuilder
/*      */     //   1329: dup
/*      */     //   1330: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1333: aload 5
/*      */     //   1335: ldc 69
/*      */     //   1337: invokeinterface 3328 2 0
/*      */     //   1342: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1345: ldc 34
/*      */     //   1347: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1350: aload 18
/*      */     //   1352: iconst_0
/*      */     //   1353: aaload
/*      */     //   1354: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1357: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1360: astore 21
/*      */     //   1362: aload 5
/*      */     //   1364: ldc_w 2146
/*      */     //   1367: invokeinterface 3328 2 0
/*      */     //   1372: astore 22
/*      */     //   1374: aload 22
/*      */     //   1376: invokestatic 3166	com/adventnet/appmanager/util/EnterpriseUtil:decodeString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1379: astore 22
/*      */     //   1381: new 2553	java/util/Properties
/*      */     //   1384: dup
/*      */     //   1385: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   1388: astore 23
/*      */     //   1390: aload 23
/*      */     //   1392: ldc_w 2387
/*      */     //   1395: new 2533	java/lang/StringBuilder
/*      */     //   1398: dup
/*      */     //   1399: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1402: aload 18
/*      */     //   1404: iconst_1
/*      */     //   1405: aaload
/*      */     //   1406: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1409: ldc 83
/*      */     //   1411: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1414: aload 22
/*      */     //   1416: aload 22
/*      */     //   1418: ldc 49
/*      */     //   1420: invokevirtual 3225	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   1423: iconst_1
/*      */     //   1424: iadd
/*      */     //   1425: invokevirtual 3221	java/lang/String:substring	(I)Ljava/lang/String;
/*      */     //   1428: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1431: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1434: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1437: pop
/*      */     //   1438: aload 23
/*      */     //   1440: ldc_w 2497
/*      */     //   1443: aload 21
/*      */     //   1445: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1448: pop
/*      */     //   1449: aload 7
/*      */     //   1451: aload 23
/*      */     //   1453: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   1456: pop
/*      */     //   1457: goto -141 -> 1316
/*      */     //   1460: aload 5
/*      */     //   1462: invokeinterface 3317 1 0
/*      */     //   1467: goto +127 -> 1594
/*      */     //   1470: astore 21
/*      */     //   1472: aload 21
/*      */     //   1474: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1477: goto +117 -> 1594
/*      */     //   1480: astore 21
/*      */     //   1482: aload 21
/*      */     //   1484: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1487: aload 5
/*      */     //   1489: invokeinterface 3317 1 0
/*      */     //   1494: goto +35 -> 1529
/*      */     //   1497: astore 21
/*      */     //   1499: aload 21
/*      */     //   1501: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1504: goto +25 -> 1529
/*      */     //   1507: astore 26
/*      */     //   1509: aload 5
/*      */     //   1511: invokeinterface 3317 1 0
/*      */     //   1516: goto +10 -> 1526
/*      */     //   1519: astore 27
/*      */     //   1521: aload 27
/*      */     //   1523: invokevirtual 3194	java/lang/Exception:printStackTrace	()V
/*      */     //   1526: aload 26
/*      */     //   1528: athrow
/*      */     //   1529: new 2553	java/util/Properties
/*      */     //   1532: dup
/*      */     //   1533: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   1536: astore 21
/*      */     //   1538: aload 21
/*      */     //   1540: ldc_w 2387
/*      */     //   1543: aload 18
/*      */     //   1545: iconst_1
/*      */     //   1546: aaload
/*      */     //   1547: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1550: pop
/*      */     //   1551: aload 21
/*      */     //   1553: ldc_w 2497
/*      */     //   1556: new 2533	java/lang/StringBuilder
/*      */     //   1559: dup
/*      */     //   1560: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1563: aload_1
/*      */     //   1564: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1567: ldc 34
/*      */     //   1569: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1572: aload 18
/*      */     //   1574: iconst_0
/*      */     //   1575: aaload
/*      */     //   1576: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1579: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1582: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1585: pop
/*      */     //   1586: aload 7
/*      */     //   1588: aload 21
/*      */     //   1590: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   1593: pop
/*      */     //   1594: iinc 16 1
/*      */     //   1597: goto -1049 -> 548
/*      */     //   1600: ldc_w 2320
/*      */     //   1603: aload_3
/*      */     //   1604: invokevirtual 3227	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*      */     //   1607: ifeq +61 -> 1668
/*      */     //   1610: new 2553	java/util/Properties
/*      */     //   1613: dup
/*      */     //   1614: invokespecial 3296	java/util/Properties:<init>	()V
/*      */     //   1617: astore 16
/*      */     //   1619: aload 16
/*      */     //   1621: ldc_w 2387
/*      */     //   1624: ldc_w 2322
/*      */     //   1627: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1630: pop
/*      */     //   1631: aload 16
/*      */     //   1633: ldc_w 2497
/*      */     //   1636: new 2533	java/lang/StringBuilder
/*      */     //   1639: dup
/*      */     //   1640: invokespecial 3234	java/lang/StringBuilder:<init>	()V
/*      */     //   1643: aload_1
/*      */     //   1644: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1647: ldc_w 2017
/*      */     //   1650: invokevirtual 3240	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1653: invokevirtual 3235	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1656: invokevirtual 3304	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   1659: pop
/*      */     //   1660: aload 7
/*      */     //   1662: aload 16
/*      */     //   1664: invokevirtual 3255	java/util/ArrayList:add	(Ljava/lang/Object;)Z
/*      */     //   1667: pop
/*      */     //   1668: aload 7
/*      */     //   1670: areturn
/*      */     // Line number table:
/*      */     //   Java source line #8243	-> byte code offset #0
/*      */     //   Java source line #8244	-> byte code offset #3
/*      */     //   Java source line #8245	-> byte code offset #8
/*      */     //   Java source line #8246	-> byte code offset #17
/*      */     //   Java source line #8247	-> byte code offset #22
/*      */     //   Java source line #8248	-> byte code offset #27
/*      */     //   Java source line #8249	-> byte code offset #30
/*      */     //   Java source line #8251	-> byte code offset #33
/*      */     //   Java source line #8253	-> byte code offset #52
/*      */     //   Java source line #8254	-> byte code offset #55
/*      */     //   Java source line #8257	-> byte code offset #83
/*      */     //   Java source line #8258	-> byte code offset #93
/*      */     //   Java source line #8260	-> byte code offset #103
/*      */     //   Java source line #8261	-> byte code offset #106
/*      */     //   Java source line #8262	-> byte code offset #116
/*      */     //   Java source line #8272	-> byte code offset #126
/*      */     //   Java source line #8276	-> byte code offset #133
/*      */     //   Java source line #8274	-> byte code offset #136
/*      */     //   Java source line #8275	-> byte code offset #138
/*      */     //   Java source line #8277	-> byte code offset #143
/*      */     //   Java source line #8266	-> byte code offset #146
/*      */     //   Java source line #8268	-> byte code offset #148
/*      */     //   Java source line #8272	-> byte code offset #153
/*      */     //   Java source line #8276	-> byte code offset #160
/*      */     //   Java source line #8274	-> byte code offset #163
/*      */     //   Java source line #8275	-> byte code offset #165
/*      */     //   Java source line #8277	-> byte code offset #170
/*      */     //   Java source line #8271	-> byte code offset #173
/*      */     //   Java source line #8272	-> byte code offset #175
/*      */     //   Java source line #8276	-> byte code offset #182
/*      */     //   Java source line #8274	-> byte code offset #185
/*      */     //   Java source line #8275	-> byte code offset #187
/*      */     //   Java source line #8276	-> byte code offset #192
/*      */     //   Java source line #8280	-> byte code offset #195
/*      */     //   Java source line #8281	-> byte code offset #205
/*      */     //   Java source line #8282	-> byte code offset #213
/*      */     //   Java source line #8283	-> byte code offset #273
/*      */     //   Java source line #8285	-> byte code offset #304
/*      */     //   Java source line #8287	-> byte code offset #324
/*      */     //   Java source line #8290	-> byte code offset #356
/*      */     //   Java source line #8293	-> byte code offset #362
/*      */     //   Java source line #8294	-> byte code offset #365
/*      */     //   Java source line #8295	-> byte code offset #370
/*      */     //   Java source line #8297	-> byte code offset #375
/*      */     //   Java source line #8298	-> byte code offset #384
/*      */     //   Java source line #8299	-> byte code offset #393
/*      */     //   Java source line #8300	-> byte code offset #402
/*      */     //   Java source line #8301	-> byte code offset #412
/*      */     //   Java source line #8302	-> byte code offset #416
/*      */     //   Java source line #8304	-> byte code offset #454
/*      */     //   Java source line #8305	-> byte code offset #464
/*      */     //   Java source line #8306	-> byte code offset #474
/*      */     //   Java source line #8309	-> byte code offset #486
/*      */     //   Java source line #8310	-> byte code offset #494
/*      */     //   Java source line #8314	-> byte code offset #497
/*      */     //   Java source line #8315	-> byte code offset #502
/*      */     //   Java source line #8311	-> byte code offset #510
/*      */     //   Java source line #8312	-> byte code offset #512
/*      */     //   Java source line #8314	-> byte code offset #517
/*      */     //   Java source line #8315	-> byte code offset #522
/*      */     //   Java source line #8314	-> byte code offset #530
/*      */     //   Java source line #8315	-> byte code offset #537
/*      */     //   Java source line #8319	-> byte code offset #545
/*      */     //   Java source line #8320	-> byte code offset #560
/*      */     //   Java source line #8321	-> byte code offset #574
/*      */     //   Java source line #8322	-> byte code offset #583
/*      */     //   Java source line #8323	-> byte code offset #586
/*      */     //   Java source line #8324	-> byte code offset #591
/*      */     //   Java source line #8325	-> byte code offset #605
/*      */     //   Java source line #8327	-> byte code offset #611
/*      */     //   Java source line #8328	-> byte code offset #633
/*      */     //   Java source line #8332	-> byte code offset #639
/*      */     //   Java source line #8334	-> byte code offset #642
/*      */     //   Java source line #8335	-> byte code offset #652
/*      */     //   Java source line #8336	-> byte code offset #677
/*      */     //   Java source line #8338	-> byte code offset #683
/*      */     //   Java source line #8341	-> byte code offset #686
/*      */     //   Java source line #8343	-> byte code offset #691
/*      */     //   Java source line #8344	-> byte code offset #735
/*      */     //   Java source line #8345	-> byte code offset #751
/*      */     //   Java source line #8346	-> byte code offset #779
/*      */     //   Java source line #8347	-> byte code offset #786
/*      */     //   Java source line #8348	-> byte code offset #795
/*      */     //   Java source line #8349	-> byte code offset #803
/*      */     //   Java source line #8351	-> byte code offset #854
/*      */     //   Java source line #8355	-> byte code offset #867
/*      */     //   Java source line #8356	-> byte code offset #877
/*      */     //   Java source line #8358	-> byte code offset #887
/*      */     //   Java source line #8359	-> byte code offset #923
/*      */     //   Java source line #8360	-> byte code offset #935
/*      */     //   Java source line #8361	-> byte code offset #942
/*      */     //   Java source line #8362	-> byte code offset #951
/*      */     //   Java source line #8363	-> byte code offset #974
/*      */     //   Java source line #8364	-> byte code offset #985
/*      */     //   Java source line #8366	-> byte code offset #993
/*      */     //   Java source line #8376	-> byte code offset #996
/*      */     //   Java source line #8380	-> byte code offset #1003
/*      */     //   Java source line #8378	-> byte code offset #1006
/*      */     //   Java source line #8379	-> byte code offset #1008
/*      */     //   Java source line #8380	-> byte code offset #1013
/*      */     //   Java source line #8370	-> byte code offset #1016
/*      */     //   Java source line #8372	-> byte code offset #1018
/*      */     //   Java source line #8376	-> byte code offset #1023
/*      */     //   Java source line #8380	-> byte code offset #1030
/*      */     //   Java source line #8378	-> byte code offset #1033
/*      */     //   Java source line #8379	-> byte code offset #1035
/*      */     //   Java source line #8381	-> byte code offset #1040
/*      */     //   Java source line #8375	-> byte code offset #1043
/*      */     //   Java source line #8376	-> byte code offset #1045
/*      */     //   Java source line #8380	-> byte code offset #1052
/*      */     //   Java source line #8378	-> byte code offset #1055
/*      */     //   Java source line #8379	-> byte code offset #1057
/*      */     //   Java source line #8380	-> byte code offset #1062
/*      */     //   Java source line #8384	-> byte code offset #1068
/*      */     //   Java source line #8386	-> byte code offset #1084
/*      */     //   Java source line #8387	-> byte code offset #1113
/*      */     //   Java source line #8388	-> byte code offset #1119
/*      */     //   Java source line #8389	-> byte code offset #1128
/*      */     //   Java source line #8390	-> byte code offset #1176
/*      */     //   Java source line #8391	-> byte code offset #1187
/*      */     //   Java source line #8392	-> byte code offset #1195
/*      */     //   Java source line #8394	-> byte code offset #1198
/*      */     //   Java source line #8396	-> byte code offset #1214
/*      */     //   Java source line #8397	-> byte code offset #1242
/*      */     //   Java source line #8398	-> byte code offset #1248
/*      */     //   Java source line #8399	-> byte code offset #1257
/*      */     //   Java source line #8400	-> byte code offset #1270
/*      */     //   Java source line #8401	-> byte code offset #1281
/*      */     //   Java source line #8402	-> byte code offset #1289
/*      */     //   Java source line #8404	-> byte code offset #1292
/*      */     //   Java source line #8409	-> byte code offset #1306
/*      */     //   Java source line #8410	-> byte code offset #1316
/*      */     //   Java source line #8412	-> byte code offset #1326
/*      */     //   Java source line #8413	-> byte code offset #1362
/*      */     //   Java source line #8414	-> byte code offset #1374
/*      */     //   Java source line #8415	-> byte code offset #1381
/*      */     //   Java source line #8417	-> byte code offset #1390
/*      */     //   Java source line #8418	-> byte code offset #1438
/*      */     //   Java source line #8419	-> byte code offset #1449
/*      */     //   Java source line #8421	-> byte code offset #1457
/*      */     //   Java source line #8431	-> byte code offset #1460
/*      */     //   Java source line #8435	-> byte code offset #1467
/*      */     //   Java source line #8433	-> byte code offset #1470
/*      */     //   Java source line #8434	-> byte code offset #1472
/*      */     //   Java source line #8435	-> byte code offset #1477
/*      */     //   Java source line #8425	-> byte code offset #1480
/*      */     //   Java source line #8427	-> byte code offset #1482
/*      */     //   Java source line #8431	-> byte code offset #1487
/*      */     //   Java source line #8435	-> byte code offset #1494
/*      */     //   Java source line #8433	-> byte code offset #1497
/*      */     //   Java source line #8434	-> byte code offset #1499
/*      */     //   Java source line #8436	-> byte code offset #1504
/*      */     //   Java source line #8430	-> byte code offset #1507
/*      */     //   Java source line #8431	-> byte code offset #1509
/*      */     //   Java source line #8435	-> byte code offset #1516
/*      */     //   Java source line #8433	-> byte code offset #1519
/*      */     //   Java source line #8434	-> byte code offset #1521
/*      */     //   Java source line #8435	-> byte code offset #1526
/*      */     //   Java source line #8440	-> byte code offset #1529
/*      */     //   Java source line #8441	-> byte code offset #1538
/*      */     //   Java source line #8442	-> byte code offset #1551
/*      */     //   Java source line #8443	-> byte code offset #1586
/*      */     //   Java source line #8319	-> byte code offset #1594
/*      */     //   Java source line #8447	-> byte code offset #1600
/*      */     //   Java source line #8448	-> byte code offset #1610
/*      */     //   Java source line #8449	-> byte code offset #1619
/*      */     //   Java source line #8450	-> byte code offset #1631
/*      */     //   Java source line #8451	-> byte code offset #1660
/*      */     //   Java source line #8453	-> byte code offset #1668
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	1671	0	this	ReportUtilities
/*      */     //   0	1671	1	resID	String
/*      */     //   0	1671	2	attID	String
/*      */     //   0	1671	3	resType	String
/*      */     //   0	1671	4	getAllAttributes	boolean
/*      */     //   1	1509	5	rs	ResultSet
/*      */     //   6	1301	6	cp	AMConnectionPool
/*      */     //   15	1654	7	retlist	ArrayList
/*      */     //   20	572	8	attributeList	List
/*      */     //   25	1268	9	secondLevelAttributes	List
/*      */     //   28	533	10	attList	List
/*      */     //   31	1061	11	childID	String
/*      */     //   53	123	12	parentSet	ResultSet
/*      */     //   363	249	12	reportEnabledList	ArrayList
/*      */     //   81	6	13	parentidquery	String
/*      */     //   382	3	13	attributeid	ArrayList
/*      */     //   136	3	14	e	Exception
/*      */     //   146	3	14	exc	Exception
/*      */     //   163	3	14	e	Exception
/*      */     //   391	3	14	displayname	ArrayList
/*      */     //   173	20	15	localObject1	Object
/*      */     //   400	253	15	scriptAttribsList	ArrayList
/*      */     //   185	3	16	e	Exception
/*      */     //   414	44	16	scriptAttribsQuery	String
/*      */     //   546	1049	16	j	int
/*      */     //   1617	46	16	data	Properties
/*      */     //   484	5	17	tempAid	String
/*      */     //   510	3	17	ex	Exception
/*      */     //   572	3	17	res	String
/*      */     //   530	13	18	localObject2	Object
/*      */     //   581	992	18	temp	String[]
/*      */     //   584	103	19	addAttrib	boolean
/*      */     //   733	577	20	disidsquery	String
/*      */     //   777	3	21	secondLevelquery	String
/*      */     //   921	59	21	tempAid	String
/*      */     //   1006	3	21	e	Exception
/*      */     //   1016	3	21	exc	Exception
/*      */     //   1033	3	21	e	Exception
/*      */     //   1111	71	21	tempAid	String
/*      */     //   1240	36	21	tempAid	String
/*      */     //   1360	84	21	tempAid	String
/*      */     //   1470	3	21	e	Exception
/*      */     //   1480	3	21	exc	Exception
/*      */     //   1497	3	21	e	Exception
/*      */     //   1536	53	21	data	Properties
/*      */     //   784	14	22	seconflevelMos	ArrayList
/*      */     //   933	26	22	tempResNam	String
/*      */     //   1117	38	22	tempResNam	String
/*      */     //   1246	3	22	tempResNam	String
/*      */     //   1372	45	22	tempResNam	String
/*      */     //   793	27	23	v	Vector
/*      */     //   949	39	23	data	Properties
/*      */     //   1126	64	23	data	Properties
/*      */     //   1255	29	23	data	Properties
/*      */     //   1388	64	23	data	Properties
/*      */     //   1043	20	24	localObject3	Object
/*      */     //   1055	3	25	e	Exception
/*      */     //   1507	20	26	localObject4	Object
/*      */     //   1519	3	27	e	Exception
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   126	133	136	java/lang/Exception
/*      */     //   83	126	146	java/lang/Exception
/*      */     //   153	160	163	java/lang/Exception
/*      */     //   83	126	173	finally
/*      */     //   146	153	173	finally
/*      */     //   173	175	173	finally
/*      */     //   175	182	185	java/lang/Exception
/*      */     //   454	497	510	java/lang/Exception
/*      */     //   454	497	530	finally
/*      */     //   510	517	530	finally
/*      */     //   530	532	530	finally
/*      */     //   996	1003	1006	java/lang/Exception
/*      */     //   867	996	1016	java/lang/Exception
/*      */     //   1023	1030	1033	java/lang/Exception
/*      */     //   867	996	1043	finally
/*      */     //   1016	1023	1043	finally
/*      */     //   1043	1045	1043	finally
/*      */     //   1045	1052	1055	java/lang/Exception
/*      */     //   1460	1467	1470	java/lang/Exception
/*      */     //   1306	1460	1480	java/lang/Exception
/*      */     //   1487	1494	1497	java/lang/Exception
/*      */     //   1306	1460	1507	finally
/*      */     //   1480	1487	1507	finally
/*      */     //   1507	1509	1507	finally
/*      */     //   1509	1516	1519	java/lang/Exception
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\ReportUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */