/*     */ package com.adventnet.appmanager.reporting;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.math.RoundingMode;
/*     */ import java.sql.ResultSet;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sourceforge.openforecast.DataPoint;
/*     */ import net.sourceforge.openforecast.DataSet;
/*     */ import net.sourceforge.openforecast.Observation;
/*     */ import net.sourceforge.openforecast.models.MultipleLinearRegressionModel;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
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
/*     */ public class ForecastReportUtil
/*     */ {
/*     */   public static HashMap forecastFullPercentageTimes(TreeMap<Long, BigDecimal> trendDataPoints, ArrayList requiredPercentageList)
/*     */     throws Exception
/*     */   {
/*  58 */     HashMap percentageMap = new HashMap();
/*     */     try {
/*  60 */       DataSet observedDataSet = new DataSet();
/*  61 */       Iterator dataValues = trendDataPoints.keySet().iterator();
/*  62 */       while (dataValues.hasNext()) {
/*  63 */         Long ttime = (Long)dataValues.next();
/*  64 */         BigDecimal value = (BigDecimal)trendDataPoints.get(ttime);
/*     */         
/*     */ 
/*  67 */         DataPoint observedDataPoint = new Observation(ttime.doubleValue());
/*  68 */         observedDataPoint.setIndependentValue("x", value.doubleValue());
/*  69 */         observedDataSet.add(observedDataPoint);
/*     */       }
/*     */       
/*  72 */       MultipleLinearRegressionModel forecaster = new MultipleLinearRegressionModel(new String[] { "x" });
/*  73 */       forecaster.init(observedDataSet);
/*     */       
/*  75 */       DataSet forecastDataSet = new DataSet();
/*  76 */       for (int i = 0; i < requiredPercentageList.size(); i++)
/*     */       {
/*  78 */         String percentInIndex = (String)requiredPercentageList.get(i);
/*     */         
/*  80 */         BigInteger value = new BigInteger(percentInIndex);
/*     */         
/*  82 */         DataPoint forecastDatapoint = new Observation(0.0D);
/*  83 */         forecastDatapoint.setIndependentValue("x", value.doubleValue());
/*  84 */         forecastDataSet.add(forecastDatapoint);
/*     */       }
/*     */       
/*  87 */       forecaster.forecast(forecastDataSet);
/*     */       
/*  89 */       Iterator<DataPoint> fcValues = forecastDataSet.iterator();
/*  90 */       while (fcValues.hasNext())
/*     */       {
/*  92 */         DataPoint dp = (DataPoint)fcValues.next();
/*     */         
/*  94 */         String percentage = Double.toString(dp.getIndependentValue("x"));
/*     */         
/*  96 */         long fcValue = 0L;
/*     */         try
/*     */         {
/*  99 */           fcValue = Double.valueOf(dp.getDependentValue()).longValue();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 103 */           e.printStackTrace();
/*     */         }
/*     */         
/* 106 */         if (percentage.indexOf(".") != -1)
/*     */         {
/* 108 */           percentage = percentage.substring(0, percentage.indexOf("."));
/*     */         }
/*     */         
/* 111 */         if (fcValue == 0L)
/*     */         {
/* 113 */           percentageMap.put(percentage, Long.valueOf(0L));
/*     */         }
/*     */         else
/*     */         {
/* 117 */           percentageMap.put(percentage, Long.valueOf(fcValue));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 122 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 125 */     return percentageMap;
/*     */   }
/*     */   
/*     */   public static double getGrowthRate(double present, double past, double trendDays) throws Exception
/*     */   {
/* 130 */     double growthRatePerDay = 0.0D;
/*     */     try
/*     */     {
/* 133 */       growthRatePerDay = Math.pow(present / past, 1.0D / trendDays) - 1.0D;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 137 */       e.printStackTrace();
/*     */     }
/* 139 */     return growthRatePerDay;
/*     */   }
/*     */   
/*     */   public static float getPercentGrowthRate(BigInteger present, BigInteger past) throws Exception
/*     */   {
/* 144 */     float percentGrowthRate = 0.0F;
/*     */     try
/*     */     {
/* 147 */       percentGrowthRate = (float)((present.longValue() - past.longValue()) / past.longValue() * 100L);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 151 */       e.printStackTrace();
/*     */     }
/* 153 */     return percentGrowthRate;
/*     */   }
/*     */   
/*     */   public static BigDecimal projectedUtilizationAfterDays(BigDecimal currentUtilization, BigDecimal growth, int numberOfDaysToProject, TreeMap<Long, BigDecimal> dataPoints, boolean isPercentageReport) throws Exception
/*     */   {
/* 158 */     BigDecimal predictionValue = BigDecimal.TEN;
/*     */     try
/*     */     {
/* 161 */       long tTime = ((Long)dataPoints.lastEntry().getKey()).longValue();
/* 162 */       long timeperiod = 86400000L;
/*     */       
/* 164 */       for (int i = 1; i <= numberOfDaysToProject; i++)
/*     */       {
/* 166 */         tTime += timeperiod;
/* 167 */         BigDecimal growthinteger = new BigDecimal(Math.pow(growth.doubleValue() + 1.0D, i));
/* 168 */         predictionValue = currentUtilization.multiply(growthinteger);
/* 169 */         predictionValue = predictionValue.setScale(2, RoundingMode.CEILING);
/*     */         
/* 171 */         if ((isPercentageReport) && (predictionValue.doubleValue() > 100.0D)) {
/* 172 */           predictionValue = new BigDecimal(100);
/* 173 */           dataPoints.put(Long.valueOf(tTime), predictionValue);
/* 174 */           break;
/*     */         }
/* 176 */         if (predictionValue.doubleValue() <= 0.0D) {
/*     */           break;
/*     */         }
/* 179 */         dataPoints.put(Long.valueOf(tTime), predictionValue);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 184 */       e.printStackTrace();
/*     */     }
/* 186 */     return predictionValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static JSONObject jsonFormatter(LinkedHashMap<Integer, HashMap<String, String>> attributeDetails, ArrayList<Long> timeStamp)
/*     */   {
/* 193 */     JSONObject formatedValue = new JSONObject();
/*     */     try {
/* 195 */       JSONObject fullPercentageData = new JSONObject();
/* 196 */       JSONObject forcastChartData = new JSONObject();
/* 197 */       long fromDate = ((Long)timeStamp.get(0)).longValue();
/* 198 */       long toDate = ((Long)timeStamp.get(1)).longValue();
/* 199 */       long futureDate = ((Long)timeStamp.get(2)).longValue();
/* 200 */       SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
/*     */       
/* 202 */       JSONArray monames = new JSONArray();
/* 203 */       JSONArray attributeValue = new JSONArray();
/* 204 */       JSONArray remaingValue = new JSONArray();
/* 205 */       JSONArray eightyPercentage = new JSONArray();
/* 206 */       JSONArray ninetyPercentage = new JSONArray();
/* 207 */       JSONArray hundredPercentage = new JSONArray();
/*     */       
/* 209 */       JSONArray chartCategories = new JSONArray();
/* 210 */       JSONArray seriesCategories = new JSONArray();
/*     */       
/* 212 */       chartCategories.put(df.format(new Date(fromDate)));
/* 213 */       chartCategories.put(df.format(new Date(toDate)));
/* 214 */       chartCategories.put(df.format(new Date(futureDate)));
/*     */       
/* 216 */       int graphCount = 0;
/* 217 */       Iterator<Integer> it = attributeDetails.keySet().iterator();
/* 218 */       while ((it.hasNext()) && 
/* 219 */         (graphCount <= 9))
/*     */       {
/*     */ 
/* 222 */         graphCount++;
/* 223 */         int resid = ((Integer)it.next()).intValue();
/* 224 */         HashMap<String, String> details = (HashMap)attributeDetails.get(Integer.valueOf(resid));
/* 225 */         String dispName = (String)details.get("displayname");
/* 226 */         double value = Double.valueOf((String)details.get("value")).doubleValue();
/* 227 */         double freeValue = 100.0D - value;
/* 228 */         String eighty = (String)details.get("eightyPercentage");
/* 229 */         String ninety = (String)details.get("ninetyPercentage");
/* 230 */         String hundred = (String)details.get("hundredPercentage");
/*     */         
/* 232 */         String pastValue = (String)details.get("pastValue");
/* 233 */         String presentValue = (String)details.get("presentValue");
/* 234 */         String futureValue = (String)details.get("futureValue");
/*     */         
/* 236 */         monames.put(dispName);
/* 237 */         attributeValue.put(value);
/* 238 */         remaingValue.put(freeValue);
/* 239 */         JSONObject eightyObj = new JSONObject();
/* 240 */         JSONObject ninetyObj = new JSONObject();
/* 241 */         JSONObject hundredObj = new JSONObject();
/*     */         
/*     */ 
/* 244 */         JSONObject predictChartData = new JSONObject();
/* 245 */         JSONArray predictChartValues = new JSONArray();
/*     */         
/* 247 */         JSONObject obj = new JSONObject();
/* 248 */         obj.put("y", Float.valueOf(pastValue));
/* 249 */         predictChartValues.put(obj);
/*     */         
/* 251 */         obj = new JSONObject();
/* 252 */         obj.put("y", Float.valueOf(presentValue));
/* 253 */         predictChartValues.put(obj);
/*     */         
/* 255 */         obj = new JSONObject();
/*     */         try {
/* 257 */           obj.put("y", Float.valueOf(futureValue));
/*     */         } catch (Exception ex) {
/* 259 */           AMLog.debug("Exception occured when calculating future " + futureValue + " for " + dispName + " resid =" + resid);
/* 260 */           obj.put("y", Float.valueOf(presentValue));
/* 261 */           ex.printStackTrace();
/*     */         }
/* 263 */         predictChartValues.put(obj);
/*     */         
/* 265 */         predictChartData.put("name", dispName);
/* 266 */         predictChartData.put("showInLegend", false);
/* 267 */         predictChartData.put("data", predictChartValues);
/*     */         
/* 269 */         eightyObj.put("name", eighty);
/* 270 */         eightyObj.put("y", 80);
/*     */         
/* 272 */         ninetyObj.put("name", ninety);
/* 273 */         ninetyObj.put("y", 90);
/*     */         
/* 275 */         hundredObj.put("name", hundred);
/* 276 */         hundredObj.put("y", 100);
/*     */         
/* 278 */         eightyPercentage.put(eightyObj);
/* 279 */         ninetyPercentage.put(ninetyObj);
/* 280 */         hundredPercentage.put(hundredObj);
/* 281 */         seriesCategories.put(predictChartData);
/*     */       }
/*     */       
/* 284 */       fullPercentageData.put("monames", monames);
/* 285 */       fullPercentageData.put("attribueValue", attributeValue);
/* 286 */       fullPercentageData.put("remainingValue", remaingValue);
/* 287 */       fullPercentageData.put("eightyPercentage", eightyPercentage);
/* 288 */       fullPercentageData.put("ninetyPercentage", ninetyPercentage);
/* 289 */       fullPercentageData.put("hundredPercentage", hundredPercentage);
/*     */       
/* 291 */       forcastChartData.put("categories", chartCategories);
/* 292 */       forcastChartData.put("seriesValue", seriesCategories);
/*     */       
/* 294 */       formatedValue.put("fullpercentage", fullPercentageData);
/* 295 */       formatedValue.put("predictchartdata", forcastChartData);
/*     */     } catch (Exception ex) {
/* 297 */       ex.printStackTrace();
/*     */     }
/* 299 */     return formatedValue;
/*     */   }
/*     */   
/*     */   public static void setRequestAttributes(HashMap attributes, HttpServletRequest request) {
/*     */     try {
/* 304 */       Iterator it = attributes.keySet().iterator();
/* 305 */       while (it.hasNext()) {
/* 306 */         String key = (String)it.next();
/* 307 */         Object value = attributes.get(key);
/* 308 */         request.setAttribute(key, value);
/*     */       }
/*     */     } catch (Exception ex) {
/* 311 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static HashMap predictForecastValues(HashMap<String, String> predictionValues)
/*     */   {
/* 318 */     HashMap graphValues = new HashMap();
/*     */     try {
/* 320 */       String attributeid = (String)predictionValues.get("attributeid");
/* 321 */       String timestamp = (String)predictionValues.get("timestamp");
/* 322 */       String rowCount = (String)predictionValues.get("rows");
/* 323 */       String resourceid = (String)predictionValues.get("resourceid");
/* 324 */       String individualReport = (String)predictionValues.get("individualReport");
/* 325 */       String growthTrend = (String)predictionValues.get("growthTrend");
/* 326 */       String startDate = (String)predictionValues.get("startDate");
/* 327 */       String endDate = (String)predictionValues.get("endDate");
/* 328 */       String isPrivilegedUser = (String)predictionValues.get("isPrivilegedUser");
/* 329 */       String haid = (String)predictionValues.get("haid");
/*     */       
/*     */ 
/* 332 */       boolean unitsUpdated = false;
/* 333 */       boolean isPercentageReport = true;
/* 334 */       String attributeUnit = FormatUtil.getString("am.myfield.editfield.values.text");
/* 335 */       int limit = 10;
/*     */       
/* 337 */       String heading = null;
/*     */       
/* 339 */       Calendar forecastCalendar = new GregorianCalendar();
/* 340 */       Date currentDate = new Date();
/* 341 */       BigDecimal graphMaxValue = new BigDecimal(100);
/* 342 */       int projectedDate = 30;
/* 343 */       int dataDuration = 2;
/*     */       
/* 345 */       if ((growthTrend == null) || ("".equals(growthTrend.trim()))) {
/* 346 */         growthTrend = "1";
/*     */       }
/*     */       
/* 349 */       if (rowCount == null) {
/* 350 */         rowCount = "10";
/*     */       }
/*     */       
/* 353 */       if (timestamp == null) {
/* 354 */         timestamp = "1";
/*     */       }
/*     */       try
/*     */       {
/* 358 */         limit = Integer.valueOf(rowCount).intValue();
/*     */       }
/*     */       catch (NumberFormatException num) {}
/*     */       
/*     */ 
/* 363 */       if (limit == -1)
/*     */       {
/* 365 */         heading = FormatUtil.getString("am.monitortab.all.text");
/*     */       }
/*     */       else
/*     */       {
/* 369 */         heading = FormatUtil.getString("am.reporttab.heading.toprows.text", new String[] { String.valueOf(limit) });
/*     */       }
/*     */       
/* 372 */       Map AttName = ReportUtil.getDisplayNameForAttributes();
/*     */       
/* 374 */       String displayName = FormatUtil.getString("am.webclient.forecast.attribute.heading.text", new String[] { (String)AttName.get(attributeid) });
/* 375 */       if (displayName.contains("%")) {
/* 376 */         unitsUpdated = true;
/* 377 */         isPercentageReport = true;
/* 378 */         attributeUnit = "%";
/*     */       }
/*     */       
/* 381 */       if (!"true".equals(individualReport)) {
/* 382 */         heading = heading + " - " + displayName;
/*     */       } else {
/* 384 */         heading = displayName;
/*     */       }
/* 386 */       if (attributeid.endsWith(",")) {
/* 387 */         attributeid = attributeid.substring(0, attributeid.length() - 1);
/*     */       }
/*     */       
/* 390 */       ArrayList attribDetails = DBUtil.getArchTableNameWithExpression(attributeid + "");
/* 391 */       String archivedTableName = (String)attribDetails.get(0);
/* 392 */       String expression = (String)attribDetails.get(1);
/*     */       
/*     */ 
/* 395 */       long[] timeStamps = null;
/*     */       
/*     */ 
/* 398 */       if ((startDate == null) || ("".equals(startDate)) || (endDate == null) || ("".equals(endDate))) {
/* 399 */         timeStamps = ReportUtilities.getTimeStamp(timestamp);
/*     */       } else {
/*     */         try {
/* 402 */           timeStamps = ReportUtilities.parseTimeAndDate(startDate, endDate);
/*     */         } catch (Exception ex) {
/* 404 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/* 408 */       long fromDate = timeStamps[0];
/* 409 */       long toDate = timeStamps[1];
/*     */       
/* 411 */       if ("1".equals(growthTrend)) {
/* 412 */         projectedDate = 30;
/* 413 */         forecastCalendar.add(2, 1);
/* 414 */       } else if ("2".equals(growthTrend)) {
/* 415 */         projectedDate = 60;
/* 416 */         forecastCalendar.add(2, 2);
/* 417 */       } else if ("3".equals(growthTrend)) {
/* 418 */         projectedDate = 90;
/* 419 */         forecastCalendar.add(2, 3);
/* 420 */       } else if ("4".equals(growthTrend)) {
/* 421 */         projectedDate = 120;
/* 422 */         forecastCalendar.add(2, 4);
/* 423 */       } else if ("5".equals(growthTrend)) {
/* 424 */         projectedDate = 150;
/* 425 */         forecastCalendar.add(2, 5);
/* 426 */       } else if ("6".equals(growthTrend)) {
/* 427 */         projectedDate = 180;
/* 428 */         forecastCalendar.add(2, 6);
/*     */       }
/*     */       
/* 431 */       long futureDate = forecastCalendar.getTimeInMillis();
/*     */       
/* 433 */       ArrayList<Long> dateTimeStamp = new ArrayList();
/* 434 */       dateTimeStamp.add(Long.valueOf(fromDate));
/* 435 */       dateTimeStamp.add(Long.valueOf(toDate));
/* 436 */       dateTimeStamp.add(Long.valueOf(futureDate));
/*     */       
/* 438 */       if (("1".equals(timestamp)) || ("2".equals(timestamp))) {
/* 439 */         dataDuration = 1;
/*     */       }
/*     */       
/* 442 */       LinkedHashMap<Integer, HashMap<String, String>> attributeValueDetails = new LinkedHashMap();
/* 443 */       String query = "select AM_ManagedObject.DISPLAYNAME as Name,AM_ManagedObject.RESOURCEID,ROUND(cast(sum(TOTAL)*1.0 / sum(TOTALCOUNT) as decimal),3)" + expression + " as AVERAGE,AM_ATTRIBUTES.UNITS from AM_ManagedObject," + archivedTableName + ",AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")  and AM_ATTRIBUTES.ATTRIBUTEID=" + archivedTableName + ".ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and " + archivedTableName + ".DURATION=" + dataDuration + " and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + "  group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE order by Average desc";
/* 444 */       Vector resourceids = new Vector();
/* 445 */       if ("true".equals(individualReport)) {
/* 446 */         query = "select AM_ManagedObject.DISPLAYNAME as Name,AM_ManagedObject.RESOURCEID,ROUND(cast(sum(TOTAL)*1.0 / sum(TOTALCOUNT) as decimal),3)" + expression + " as AVERAGE,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE from AM_ManagedObject," + archivedTableName + ",AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")  and AM_ATTRIBUTES.ATTRIBUTEID=" + archivedTableName + ".ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and " + archivedTableName + ".DURATION=" + dataDuration + " and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " and AM_ManagedObject.RESOURCEID=" + resourceid + "  group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE order by Average desc";
/* 447 */       } else if (haid != null) {
/* 448 */         Vector resvector = new Vector();
/* 449 */         resvector.add(haid);
/* 450 */         ParentChildRelationalUtil.getAllChildMapper(resvector, haid, false, true);
/* 451 */         query = "select AM_ManagedObject.DISPLAYNAME as Name,AM_ManagedObject.RESOURCEID,ROUND(cast(sum(TOTAL)*1.0 / sum(TOTALCOUNT) as decimal),3)" + expression + " as AVERAGE,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE from AM_ManagedObject," + archivedTableName + ",AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")  and AM_ATTRIBUTES.ATTRIBUTEID=" + archivedTableName + ".ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and " + archivedTableName + ".DURATION=" + dataDuration + " and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " and " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resvector) + "   group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE order by Average desc";
/*     */       }
/* 453 */       else if ("true".equals(isPrivilegedUser)) {
/* 454 */         if (Constants.isUserResourceEnabled()) {
/* 455 */           String loginUserid = (String)predictionValues.get("loginUserid");
/* 456 */           resourceids = Constants.getUserResourceID(loginUserid);
/*     */         } else {
/* 458 */           String owner = (String)predictionValues.get("owner");
/* 459 */           resourceids = ReportUtilities.getResourceIdentity(owner);
/* 460 */           query = "select DISTINCT AM_ManagedObject.RESOURCEID AS RESID FROM AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ATTRIBUTES where " + ReportUtilities.getCondition("PARENTID", resourceids) + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")";
/* 461 */           ResultSet rs = null;
/*     */           try {
/* 463 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 464 */             while (rs.next()) {
/* 465 */               resourceids.add(rs.getString("RESID"));
/*     */             }
/*     */           } catch (Exception ex) {
/* 468 */             ex.printStackTrace();
/*     */           } finally {
/* 470 */             if (rs != null) {
/* 471 */               AMConnectionPool.closeStatement(rs);
/*     */             }
/*     */           }
/*     */         }
/* 475 */         query = "select AM_ManagedObject.DISPLAYNAME as Name,AM_ManagedObject.RESOURCEID,ROUND(cast(sum(TOTAL)*1.0 / sum(TOTALCOUNT) as decimal),2)" + expression + " as AVERAGE,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE from AM_ManagedObject," + archivedTableName + ",AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")  and AM_ATTRIBUTES.ATTRIBUTEID=" + archivedTableName + ".ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and " + archivedTableName + ".DURATION=" + dataDuration + " and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " and " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "  group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE order by Average desc";
/*     */       } else {
/* 477 */         query = "select AM_ManagedObject.DISPLAYNAME as Name,AM_ManagedObject.RESOURCEID,ROUND(cast(sum(TOTAL)*1.0 / sum(TOTALCOUNT) as decimal),2)" + expression + " as AVERAGE,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE from AM_ManagedObject," + archivedTableName + ",AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and AM_ATTRIBUTES.ATTRIBUTEID in (" + attributeid + ")  and AM_ATTRIBUTES.ATTRIBUTEID=" + archivedTableName + ".ATTRIBUTEID and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and " + archivedTableName + ".DURATION=" + dataDuration + " and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + "  group by AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES.RESOURCETYPE order by Average desc";
/*     */       }
/*     */       
/*     */ 
/* 481 */       if ((!"true".equals(individualReport)) && (limit != -1)) {
/* 482 */         query = DBQueryUtil.getTopNValues(query, limit);
/*     */       }
/* 484 */       ResultSet rs = null;
/* 485 */       Vector resids = new Vector();
/* 486 */       HashMap diskDisplayNames = new HashMap();
/*     */       
/* 488 */       if ("711".equals(attributeid)) {
/* 489 */         diskDisplayNames = DBUtil.getDisplayNameForDisk();
/*     */       }
/*     */       try {
/* 492 */         List allSecondLevelAttribute = ReportUtil.getAllSecondLevelAttribute();
/* 493 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 494 */         while (rs.next()) {
/* 495 */           HashMap<String, String> details = new HashMap();
/*     */           
/* 497 */           if (!unitsUpdated) {
/* 498 */             attributeUnit = rs.getString("UNITS");
/* 499 */             unitsUpdated = true;
/* 500 */             if ("%".equals(attributeUnit)) {
/* 501 */               isPercentageReport = true;
/*     */             } else {
/* 503 */               isPercentageReport = false;
/*     */             }
/*     */           }
/* 506 */           int resid = rs.getInt("RESOURCEID");
/* 507 */           if (allSecondLevelAttribute.contains(attributeid))
/*     */           {
/* 509 */             String dname = rs.getString("NAME");
/* 510 */             if ("711".equals(attributeid)) {
/* 511 */               dname = FormatUtil.findReplace(dname, "DiskUtilization", FormatUtil.getString("DiskUtilization"));
/* 512 */               String[] temp1 = dname.split(":", 2);
/* 513 */               if ((temp1[0] != null) && (diskDisplayNames.get(temp1[0]) != null)) {
/* 514 */                 String s1 = diskDisplayNames.get(temp1[0]).toString();
/* 515 */                 if ((s1 != null) && (temp1.length > 1)) {
/* 516 */                   dname = s1 + ":" + temp1[1];
/*     */                 }
/* 518 */                 details.put("displayname", dname);
/*     */               } else {
/* 520 */                 details.put("displayname", dname);
/*     */               }
/*     */             }
/*     */             else {
/* 524 */               String displayname = ReportUtil.getDisplayNameForAttribute(resid);
/* 525 */               details.put("displayname", displayname + "_" + rs.getString("NAME"));
/*     */             }
/*     */           }
/*     */           else {
/* 529 */             details.put("displayname", rs.getString("NAME"));
/*     */           }
/* 531 */           details.put("resourceid", resid + "");
/* 532 */           BigDecimal avgValue = rs.getBigDecimal("AVERAGE");
/*     */           
/* 534 */           int decimalComparison = avgValue.compareTo(graphMaxValue);
/* 535 */           if (decimalComparison == 1) {
/* 536 */             graphMaxValue = avgValue;
/*     */           }
/* 538 */           details.put("value", avgValue.setScale(2, 2) + "");
/* 539 */           resids.add(Integer.valueOf(resid));
/* 540 */           attributeValueDetails.put(Integer.valueOf(resid), details);
/*     */         }
/*     */       } catch (Exception ex) {
/* 543 */         ex.printStackTrace();
/*     */       } finally {
/* 545 */         if (rs != null) {
/* 546 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/* 549 */       graphValues.put("attributeUnit", attributeUnit);
/* 550 */       int attributesCount = attributeValueDetails.size();
/* 551 */       if (attributesCount == 0) {
/* 552 */         HashMap graphValue = new HashMap();
/* 553 */         graphValue.put("attributeid", attributeid);
/* 554 */         graphValue.put("heading", FormatUtil.getString("am.webclient.forecast.report.text"));
/* 555 */         graphValue.put("period", timestamp);
/* 556 */         graphValue.put("nodata", "true");
/* 557 */         graphValue.put("emptymessage", FormatUtil.getString("am.webclient.forecast.nodata.text"));
/* 558 */         return graphValue;
/*     */       }
/* 560 */       ResultSet set = null;
/*     */       
/*     */ 
/* 563 */       HashMap<Integer, TreeMap<Long, BigDecimal>> dataPoints = new HashMap();
/*     */       try {
/* 565 */         String datapointQuery = "select ARCHIVEDTIME,ROUND(cast((TOTAL)*1.0 / (TOTALCOUNT) as decimal),2)" + expression + " as AVG,RESID  from " + archivedTableName + ",AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + archivedTableName + ".RESID and " + ReportUtilities.getCondition("AM_ManagedObject.RESOURCEID", resids) + " and ATTRIBUTEID in (" + attributeid + ")  and DURATION=" + dataDuration + " and ARCHIVEDTIME <= " + toDate + " and ARCHIVEDTIME >= " + fromDate + " ";
/* 566 */         set = AMConnectionPool.executeQueryStmt(datapointQuery);
/* 567 */         while (set.next()) {
/* 568 */           int resid = set.getInt("RESID");
/* 569 */           long ttime = set.getLong("ARCHIVEDTIME");
/* 570 */           BigDecimal value = set.getBigDecimal("AVG");
/* 571 */           BigDecimal formatedValue = value.setScale(2, 2);
/* 572 */           if (dataPoints.containsKey(Integer.valueOf(resid))) {
/* 573 */             TreeMap<Long, BigDecimal> points = (TreeMap)dataPoints.get(Integer.valueOf(resid));
/* 574 */             points.put(Long.valueOf(ttime), formatedValue);
/*     */           } else {
/* 576 */             TreeMap<Long, BigDecimal> points = new TreeMap();
/* 577 */             points.put(Long.valueOf(ttime), formatedValue);
/* 578 */             dataPoints.put(Integer.valueOf(resid), points);
/*     */           }
/*     */         }
/*     */       } catch (Exception ex) {
/* 582 */         ex.printStackTrace();
/*     */       } finally {
/* 584 */         if (set != null) {
/* 585 */           AMConnectionPool.closeStatement(set);
/*     */         }
/*     */       }
/* 588 */       SimpleDateFormat predictionDate = new SimpleDateFormat("dd MMM yyyy");
/* 589 */       SimpleDateFormat predictedDataPoints = new SimpleDateFormat("dd MMM yy kk:mm");
/* 590 */       ArrayList requiredPercentageList = new ArrayList();
/* 591 */       requiredPercentageList.add("80");
/* 592 */       requiredPercentageList.add("90");
/* 593 */       requiredPercentageList.add("100");
/* 594 */       Object setPoint = dataPoints.keySet().iterator();
/* 595 */       JSONArray dataPointsChart = new JSONArray();
/* 596 */       JSONArray predictionPointsChart = new JSONArray();
/*     */       
/* 598 */       while (((Iterator)setPoint).hasNext()) {
/* 599 */         int id = ((Integer)((Iterator)setPoint).next()).intValue();
/* 600 */         Object points = (TreeMap)dataPoints.get(Integer.valueOf(id));
/*     */         try
/*     */         {
/* 603 */           HashMap fullPercentageMap = forecastFullPercentageTimes((TreeMap)points, requiredPercentageList);
/* 604 */           BigDecimal presentEntry = (BigDecimal)((TreeMap)points).lastEntry().getValue();
/* 605 */           long lastTime = ((Long)((TreeMap)points).lastEntry().getKey()).longValue();
/*     */           
/*     */ 
/* 608 */           long duration = System.currentTimeMillis() - ((Long)((TreeMap)points).firstEntry().getKey()).longValue();
/* 609 */           long totalDays = TimeUnit.MILLISECONDS.toDays(duration);
/*     */           
/* 611 */           duration = System.currentTimeMillis() - lastTime;
/* 612 */           long missedDays = TimeUnit.MILLISECONDS.toDays(duration);
/*     */           
/* 614 */           long timeperiod = 86400000L;
/*     */           
/* 616 */           for (int j = (int)missedDays; j > 0; j--) {
/* 617 */             lastTime += timeperiod;
/* 618 */             ((TreeMap)points).put(Long.valueOf(lastTime), presentEntry);
/*     */           }
/*     */           
/* 621 */           BigDecimal pastEntry = (BigDecimal)((TreeMap)points).firstEntry().getValue();
/* 622 */           double trendDays = ((TreeMap)points).size();
/* 623 */           if (dataDuration == 1) {
/* 624 */             trendDays /= 24.0D;
/*     */           }
/*     */           
/* 627 */           if (trendDays < 3.0D) {
/* 628 */             AMLog.debug("Data points for the resid " + id + " is for " + trendDays + "");
/* 629 */             attributesCount--;
/* 630 */             attributeValueDetails.remove(Integer.valueOf(id));
/*     */           }
/*     */           else
/*     */           {
/* 634 */             if (dataDuration == 2) {
/* 635 */               trendDays = totalDays;
/*     */             }
/*     */             
/* 638 */             double growthRate = getGrowthRate(presentEntry.doubleValue(), pastEntry.doubleValue(), trendDays);
/* 639 */             BigDecimal predictedValue = BigDecimal.TEN;
/* 640 */             TreeMap<Long, BigDecimal> predictedPoints = new TreeMap();
/* 641 */             predictedPoints.put(Long.valueOf(lastTime), presentEntry);
/*     */             
/* 643 */             BigDecimal growthCalculator = BigDecimal.ZERO;
/* 644 */             if ((!Double.isInfinite(growthRate)) && (!Double.isNaN(growthRate)) && (growthRate != 0.0D)) {
/* 645 */               growthCalculator = new BigDecimal(growthRate);
/*     */             }
/*     */             
/* 648 */             if (!"true".equals(individualReport)) {
/* 649 */               BigDecimal growthinteger = new BigDecimal(Math.pow(growthCalculator.doubleValue() + 1.0D, projectedDate));
/* 650 */               predictedValue = presentEntry.multiply(growthinteger);
/*     */             }
/*     */             else {
/* 653 */               predictedValue = projectedUtilizationAfterDays(presentEntry, growthCalculator, projectedDate, predictedPoints, isPercentageReport);
/*     */             }
/* 655 */             predictedValue = predictedValue.setScale(2, RoundingMode.CEILING);
/* 656 */             if ((isPercentageReport) && (predictedValue.doubleValue() > 100.0D)) {
/* 657 */               predictedValue = new BigDecimal(100);
/*     */             }
/*     */             
/* 660 */             if (predictedValue.doubleValue() > graphMaxValue.doubleValue()) {
/* 661 */               graphMaxValue = predictedValue;
/*     */             }
/* 663 */             if (predictedValue.doubleValue() <= 0.0D) {
/* 664 */               predictedValue = BigDecimal.ZERO;
/*     */             }
/* 666 */             HashMap<String, String> details = (HashMap)attributeValueDetails.get(Integer.valueOf(id));
/* 667 */             Double value = Double.valueOf((String)details.get("value"));
/*     */             
/*     */ 
/* 670 */             for (int i = 0; i < requiredPercentageList.size(); i++) {
/* 671 */               String percentage = (String)requiredPercentageList.get(i);
/* 672 */               int percent = Integer.valueOf(percentage).intValue();
/* 673 */               String key = "";
/* 674 */               if (percent == 80) {
/* 675 */                 key = "eightyPercentage";
/* 676 */               } else if (percent == 90) {
/* 677 */                 key = "ninetyPercentage";
/*     */               } else {
/* 679 */                 key = "hundredPercentage";
/*     */               }
/* 681 */               Long futurePrediction = (Long)fullPercentageMap.get(percentage);
/* 682 */               if (value.doubleValue() >= percent) {
/* 683 */                 details.put(key, "Full");
/* 684 */               } else if (futurePrediction.longValue() == 0L) {
/* 685 */                 details.put(key, "N.A.");
/*     */               } else {
/* 687 */                 Date predictedDate = new Date(futurePrediction.longValue());
/* 688 */                 if (currentDate.after(predictedDate)) {
/* 689 */                   details.put(key, "N.A.");
/*     */                 } else {
/* 691 */                   details.put(key, predictionDate.format(predictedDate).toString());
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 696 */             details.put("pastValue", pastEntry + "");
/* 697 */             details.put("presentValue", presentEntry + "");
/* 698 */             details.put("futureValue", predictedValue + "");
/* 699 */             if ("true".equals(individualReport)) {
/* 700 */               graphValues.put("monitorName", details.get("displayname"));
/*     */               
/* 702 */               predictedPoints.remove(predictedPoints.firstEntry().getKey());
/* 703 */               dataPointsChart = formDataPoints((TreeMap)points);
/* 704 */               predictionPointsChart = formDataPoints(predictedPoints);
/* 705 */               int dataPointSize = ((TreeMap)points).size();
/* 706 */               ArrayList dataList = new ArrayList();
/* 707 */               if (dataPointSize > 20) {
/* 708 */                 int i = 0;
/* 709 */                 for (Map.Entry entry : ((TreeMap)points).descendingMap().entrySet()) {
/* 710 */                   if (i++ >= 20) break;
/* 711 */                   HashMap map = new HashMap();
/* 712 */                   long ttimes = ((Long)entry.getKey()).longValue();
/* 713 */                   BigDecimal values = (BigDecimal)entry.getValue();
/*     */                   
/* 715 */                   map.put("time", predictedDataPoints.format(new Date(ttimes)));
/* 716 */                   map.put("actualvalue", values);
/* 717 */                   map.put("predictedvalue", "NA");
/* 718 */                   dataList.add(map);
/*     */                 }
/*     */                 
/*     */ 
/*     */ 
/* 723 */                 Collections.reverse(dataList);
/*     */               } else {
/* 725 */                 iteratePoints((TreeMap)points, dataList, false);
/*     */               }
/* 727 */               iteratePoints(predictedPoints, dataList, true);
/*     */               
/* 729 */               graphValues.put("dataTrends", dataList);
/*     */             }
/*     */           }
/* 732 */         } catch (Exception ex) { ex.printStackTrace();
/*     */         }
/*     */       }
/* 735 */       if (attributesCount < 1) {
/* 736 */         HashMap graphValue = new HashMap();
/* 737 */         graphValue.put("attributeid", attributeid);
/* 738 */         graphValue.put("heading", FormatUtil.getString("am.webclient.forecast.report.text"));
/* 739 */         graphValue.put("period", timestamp);
/* 740 */         graphValue.put("emptymessage", FormatUtil.getString("am.webclient.forecast.minimumdata.text"));
/* 741 */         graphValue.put("nodata", "true");
/* 742 */         return graphValue;
/*     */       }
/* 744 */       JSONObject formatedValue = jsonFormatter(attributeValueDetails, dateTimeStamp);
/* 745 */       JSONObject fullPercentageData = (JSONObject)formatedValue.get("fullpercentage");
/* 746 */       JSONObject forecastData = (JSONObject)formatedValue.get("predictchartdata");
/*     */       
/*     */ 
/* 749 */       forecastData.put("dataPoints", dataPointsChart);
/* 750 */       forecastData.put("predictionPoints", predictionPointsChart);
/*     */       
/*     */ 
/* 753 */       graphValues.put("attributeDetails", attributeValueDetails);
/* 754 */       graphValues.put("fullPercentageData", fullPercentageData);
/* 755 */       graphValues.put("chartData", forecastData);
/* 756 */       graphValues.put("attributeid", attributeid);
/* 757 */       graphValues.put("fromDate", predictionDate.format(new Date(fromDate)));
/* 758 */       graphValues.put("toDate", predictionDate.format(new Date(toDate)));
/* 759 */       graphValues.put("futureDate", predictionDate.format(new Date(futureDate)));
/* 760 */       graphValues.put("individualReport", individualReport);
/* 761 */       graphValues.put("maxValue", graphMaxValue);
/* 762 */       graphValues.put("heading", heading);
/* 763 */       graphValues.put("growthTrendValue", growthTrend);
/* 764 */       graphValues.put("percentageReport", Boolean.valueOf(isPercentageReport));
/* 765 */       graphValues.put("period", timestamp);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 769 */       ex.printStackTrace();
/*     */     }
/* 771 */     return graphValues;
/*     */   }
/*     */   
/*     */   public static void iteratePoints(TreeMap<Long, BigDecimal> points, ArrayList dataList, boolean isPredictData) {
/*     */     try {
/* 776 */       SimpleDateFormat predictedDataPoints = new SimpleDateFormat("dd MMM yy kk:mm");
/* 777 */       Iterator<Long> it = points.keySet().iterator();
/* 778 */       while (it.hasNext()) {
/* 779 */         long predictedTime = ((Long)it.next()).longValue();
/* 780 */         BigDecimal predictedValues = (BigDecimal)points.get(Long.valueOf(predictedTime));
/* 781 */         HashMap map = new HashMap();
/* 782 */         map.put("time", predictedDataPoints.format(new Date(predictedTime)));
/* 783 */         if (isPredictData) {
/* 784 */           map.put("predictedvalue", predictedValues);
/* 785 */           map.put("actualvalue", "NA");
/*     */         } else {
/* 787 */           map.put("actualvalue", predictedValues);
/* 788 */           map.put("predictedvalue", "NA");
/*     */         }
/*     */         
/* 791 */         dataList.add(map);
/*     */       }
/*     */     } catch (Exception ex) {
/* 794 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static JSONArray formDataPoints(TreeMap<Long, BigDecimal> points) {
/* 799 */     JSONArray datapoints = new JSONArray();
/*     */     try {
/* 801 */       Iterator<Long> it = points.keySet().iterator();
/* 802 */       while (it.hasNext()) {
/* 803 */         long key = ((Long)it.next()).longValue();
/* 804 */         BigDecimal value = (BigDecimal)points.get(Long.valueOf(key));
/* 805 */         JSONObject obj = new JSONObject();
/* 806 */         obj.put("x", key);
/* 807 */         obj.put("y", value);
/* 808 */         datapoints.put(obj);
/*     */       }
/*     */     } catch (Exception ex) {
/* 811 */       ex.printStackTrace();
/*     */     }
/* 813 */     return datapoints;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\ForecastReportUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */