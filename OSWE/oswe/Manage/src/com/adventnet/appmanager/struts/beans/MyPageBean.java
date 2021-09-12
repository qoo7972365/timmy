/*     */ package com.adventnet.appmanager.struts.beans;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.util.DashboardUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.DefaultValueDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
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
/*     */ 
/*     */ 
/*     */ public class MyPageBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  48 */   private ArrayList resourceIds = null;
/*  49 */   private String attributeid = null;
/*  50 */   private HashMap residDisplayNames = null;
/*  51 */   private ArrayList metaInfoInOrder = null;
/*  52 */   private ArrayList<String> headerAttributes = null;
/*  53 */   private String graphType = "linegraph";
/*  54 */   private long value = 0L;
/*  55 */   private ArrayList outputData = null;
/*  56 */   private String period = "20";
/*  57 */   private int baseAttributeValueColumnIndex = 1;
/*  58 */   private ArrayList attribslistCheckedforComplextype = null;
/*  59 */   private ArrayList allAttributeIDs = null;
/*  60 */   private String widgetType = null;
/*  61 */   private boolean additionalMetricGraph = false;
/*  62 */   private HashMap<String, ArrayList<String>> selectedResidattribsMap = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  68 */   private ManagedApplication mo = new ManagedApplication();
/*  69 */   private String resourceType = "";
/*  70 */   private String monitorSelectionType = "";
/*     */   
/*     */ 
/*     */   public void setParam(ArrayList resourceIds, ArrayList selectedAttribIds, String period)
/*     */   {
/*  75 */     this.resourceIds = resourceIds;
/*  76 */     this.attributeid = this.attributeid;
/*  77 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setAllAttirbuteIDs(ArrayList attributes) {
/*  81 */     this.allAttributeIDs = attributes;
/*     */   }
/*     */   
/*     */   public void setWidgetType(String widgetType) {
/*  85 */     this.widgetType = widgetType;
/*     */   }
/*     */   
/*     */   public void setAdditionalMetricGraph(boolean graph)
/*     */   {
/*  90 */     this.additionalMetricGraph = graph;
/*     */   }
/*     */   
/*     */   public boolean getAdditionalMetricGraph() {
/*  94 */     return this.additionalMetricGraph;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setResourceIds(ArrayList resourceIds)
/*     */   {
/* 100 */     this.resourceIds = resourceIds;
/*     */   }
/*     */   
/*     */   public void setAttributeid(String attributeid)
/*     */   {
/* 105 */     this.attributeid = attributeid;
/*     */   }
/*     */   
/*     */   public void setResidDisplayNames(HashMap residDisplayNames)
/*     */   {
/* 110 */     this.residDisplayNames = residDisplayNames;
/*     */   }
/*     */   
/*     */   public void setGraphType(String graphType)
/*     */   {
/* 115 */     this.graphType = graphType;
/*     */   }
/*     */   
/*     */   public void setValue(long value)
/*     */   {
/* 120 */     this.value = value;
/*     */   }
/*     */   
/*     */   public long getValue()
/*     */   {
/* 125 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setOutputData(ArrayList outputData)
/*     */   {
/* 130 */     this.outputData = outputData;
/*     */   }
/*     */   
/*     */   public void setPeriod(String period) {
/* 134 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setMetaInfoInOrder(ArrayList<HashMap> metaInfoInOrder)
/*     */   {
/* 139 */     this.metaInfoInOrder = metaInfoInOrder;
/*     */   }
/*     */   
/*     */   public void setAttribslist_checkedforComplextype(ArrayList attribslistCheckedforComplextype)
/*     */   {
/* 144 */     this.attribslistCheckedforComplextype = attribslistCheckedforComplextype;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setHeaderAttributes(ArrayList<String> headerAttributes)
/*     */   {
/* 150 */     this.headerAttributes = headerAttributes;
/* 151 */     if (headerAttributes != null)
/*     */     {
/* 153 */       this.baseAttributeValueColumnIndex = headerAttributes.indexOf(this.attributeid);
/* 154 */       if (this.baseAttributeValueColumnIndex < 0)
/*     */       {
/* 156 */         this.baseAttributeValueColumnIndex = 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setResourceType(String resourceType)
/*     */   {
/* 164 */     this.resourceType = resourceType;
/*     */   }
/*     */   
/*     */   public void setMonitorSelectionType(String monitorSelectionType) {
/* 168 */     this.monitorSelectionType = monitorSelectionType;
/*     */   }
/*     */   
/*     */   public int getBaseAttributeValueColumnIndex() {
/* 172 */     return this.baseAttributeValueColumnIndex;
/*     */   }
/*     */   
/*     */   public HashMap<String, ArrayList<String>> getSelectedResidattribsMap() {
/* 176 */     return this.selectedResidattribsMap;
/*     */   }
/*     */   
/* 179 */   Hashtable bhrDetails = null;
/*     */   
/*     */   public void setBusinessPeriod(String businessPeriod) {
/* 182 */     this.bhrDetails = SegmentReportUtil.getBusinessRule(businessPeriod);
/*     */   }
/*     */   
/*     */   public void setSelectedResidattribsMap(HashMap<String, ArrayList<String>> selectedResidattribsMap)
/*     */   {
/* 187 */     this.selectedResidattribsMap = selectedResidattribsMap;
/*     */   }
/*     */   
/*     */   public Object produceAreaset(Map params, String metricGraphQuery) throws DatasetProduceException {
/* 191 */     ResultSet rs = null;
/* 192 */     AMConnectionPool cp = new AMConnectionPool();
/* 193 */     DefaultCategoryDataset areaset = new DefaultCategoryDataset();
/*     */     try {
/* 195 */       metricGraphQuery = metricGraphQuery + " limit 10";
/* 196 */       rs = AMConnectionPool.executeQueryStmt(metricGraphQuery);
/* 197 */       while (rs.next())
/*     */       {
/* 199 */         Date d = new Date(rs.getLong(3));
/* 200 */         areaset.setValue(rs.getLong(2), "test", new Minute(d));
/* 201 */         System.out.println(rs.getLong(2) + " : " + new Minute(d));
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 206 */       ex.printStackTrace();
/*     */     }
/* 208 */     return areaset;
/*     */   }
/*     */   
/*     */   public Object produceDialDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 214 */     int toreturn = 0;
/*     */     try
/*     */     {
/* 217 */       ArrayList firstRow = (ArrayList)this.outputData.get(0);
/* 218 */       String value = (String)firstRow.get(this.baseAttributeValueColumnIndex);
/* 219 */       toreturn = Math.round(Float.parseFloat(value));
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 223 */       ex.printStackTrace();
/* 224 */       toreturn = 0;
/*     */     }
/* 226 */     return new DefaultValueDataset(toreturn);
/*     */   }
/*     */   
/*     */   public Object produceBarDataset(Map params) throws DatasetProduceException
/*     */   {
/* 231 */     System.out.println("outputData in chart--->" + this.outputData);
/* 232 */     DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 233 */     for (int i = 0; i < this.outputData.size(); i++)
/*     */     {
/* 235 */       ArrayList singleRow = (ArrayList)this.outputData.get(i);
/*     */       
/* 237 */       if (!this.additionalMetricGraph) {
/* 238 */         String resourceName = i + 1 + ".";
/* 239 */         if ((String)((HashMap)this.metaInfoInOrder.get(i)).get("secondLevelDisplayname") != null) {
/* 240 */           resourceName = resourceName + (String)((HashMap)this.metaInfoInOrder.get(i)).get("secondLevelDisplayname");
/*     */         }
/*     */         else {
/* 243 */           resourceName = resourceName + (String)((HashMap)this.metaInfoInOrder.get(i)).get("displayname");
/*     */         }
/* 245 */         String value = (String)singleRow.get(this.baseAttributeValueColumnIndex);
/* 246 */         if (value != null) {
/* 247 */           barset.setValue(new Float(value), "", resourceName);
/*     */         }
/*     */       }
/*     */       else {
/* 251 */         for (int j = 1; j < this.headerAttributes.size(); j++) {
/* 252 */           ArrayList<String> attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(this.headerAttributes.get(j));
/* 253 */           String displayname = null;
/* 254 */           if ((attrExtTableDetails != null) && (attrExtTableDetails.get(5) != null))
/*     */           {
/* 256 */             displayname = (String)attrExtTableDetails.get(5);
/*     */           }
/* 258 */           if ((!displayname.equalsIgnoreCase("health")) && (!displayname.equalsIgnoreCase("availability")))
/*     */           {
/*     */ 
/* 261 */             displayname = FormatUtil.getString(displayname);
/* 262 */             String val = (String)singleRow.get(j);
/* 263 */             if (val != null) {
/*     */               try {
/* 265 */                 barset.setValue(new Float(val), "", displayname);
/*     */               } catch (NumberFormatException num) {
/* 267 */                 num.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 277 */     return barset;
/*     */   }
/*     */   
/*     */   public Object produceBarPecentageDataset(Map params) throws DatasetProduceException
/*     */   {
/* 282 */     String[] seriesNames = { FormatUtil.getString("am.webclient.oracle.graph.usedspace"), FormatUtil.getString("am.webclient.oracle.graph.freespace") };
/* 283 */     String[] categories = new String[this.outputData.size()];
/* 284 */     Long[][] startValues = new Long[seriesNames.length][categories.length];
/* 285 */     Long[][] endValues = new Long[seriesNames.length][categories.length];
/* 286 */     DefaultCategoryDataset barset = new DefaultCategoryDataset();
/* 287 */     for (int i = 0; i < this.outputData.size(); i++)
/*     */     {
/* 289 */       ArrayList singleRow = (ArrayList)this.outputData.get(i);
/* 290 */       String resourceName = (String)((HashMap)this.metaInfoInOrder.get(i)).get("displayname");
/* 291 */       String value = (String)singleRow.get(this.baseAttributeValueColumnIndex);
/* 292 */       long value_inlong = Long.parseLong(value);
/* 293 */       categories[i] = (i + 1 + "." + resourceName);
/* 294 */       startValues[0][i] = new Long(0L);
/* 295 */       endValues[0][i] = new Long(value_inlong);
/* 296 */       startValues[1][i] = new Long(0L);
/* 297 */       endValues[1][i] = new Long(100L - value_inlong);
/*     */     }
/* 299 */     DefaultIntervalCategoryDataset ds = new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
/* 300 */     return ds;
/*     */   }
/*     */   
/*     */   public Object producePieChartDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 306 */     DefaultPieDataset ds = new DefaultPieDataset();
/* 307 */     ArrayList firstRow = (ArrayList)this.outputData.get(0);
/* 308 */     String value = (String)firstRow.get(this.baseAttributeValueColumnIndex);
/* 309 */     long value_inlong = Long.parseLong(value);
/* 310 */     ds.setValue(FormatUtil.getString("Used"), value_inlong);
/* 311 */     ds.setValue(FormatUtil.getString("Available"), 100L - value_inlong);
/* 312 */     return ds;
/*     */   }
/*     */   
/*     */   public ArrayList getAttributeIdsForComplexType(String complextype, String attributeid) {
/* 316 */     ArrayList defaultList = new ArrayList();
/*     */     try
/*     */     {
/* 319 */       if ((complextype.equals("$ComplexType_Windows")) || (complextype.equals("$ComplexType_Servers")) || (complextype.equals("$ComplexType_All")))
/*     */       {
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
/* 341 */         if ((this.attribslistCheckedforComplextype != null) && (this.attribslistCheckedforComplextype.size() > 0))
/*     */         {
/* 343 */           return this.attribslistCheckedforComplextype;
/*     */         }
/*     */         
/*     */ 
/* 347 */         defaultList.add(attributeid);
/* 348 */         return defaultList;
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 354 */       ex.printStackTrace();
/*     */     }
/* 356 */     defaultList.add(attributeid);
/* 357 */     return defaultList;
/*     */   }
/*     */   
/*     */   public Object produceLineChart(Map params) throws DatasetProduceException {
/* 361 */     boolean flagForAllTypes = this.resourceType.equalsIgnoreCase("$ComplexType_All");
/* 362 */     if ((this.monitorSelectionType != null) && (this.monitorSelectionType.equals("2")))
/*     */     {
/* 364 */       flagForAllTypes = true;
/*     */     }
/* 366 */     if ((this.widgetType.equals("2")) && (flagForAllTypes))
/*     */     {
/* 368 */       return produceLineChartforAllTypes(params);
/*     */     }
/* 370 */     toReturn = null;
/* 371 */     ResultSet rs = null;
/* 372 */     AMConnectionPool cp = new AMConnectionPool();
/* 373 */     TimeSeriesCollection col = new TimeSeriesCollection();
/* 374 */     int timeSeriesSize = 0;
/*     */     try {
/* 376 */       long currenttime = System.currentTimeMillis();
/* 377 */       if ((this.widgetType.equals("2")) && (this.additionalMetricGraph)) {
/* 378 */         this.allAttributeIDs.add(this.attributeid);
/*     */       } else {
/* 380 */         this.allAttributeIDs.clear();
/* 381 */         this.allAttributeIDs.add(this.attributeid);
/*     */       }
/* 383 */       for (int i = 0; i < this.allAttributeIDs.size(); i++) {
/* 384 */         ArrayList<String> attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(this.allAttributeIDs.get(i));
/* 385 */         String attrId = this.allAttributeIDs.get(i).toString();
/* 386 */         String datatable = (String)attrExtTableDetails.get(0);
/* 387 */         String resid_col = (String)attrExtTableDetails.get(1);
/* 388 */         String attid_col = (String)attrExtTableDetails.get(2);
/* 389 */         String value_col = (String)attrExtTableDetails.get(3);
/* 390 */         String coltime_col = (String)attrExtTableDetails.get(4);
/* 391 */         String displayname = (String)attrExtTableDetails.get(5);
/* 392 */         if ((!displayname.equalsIgnoreCase("health")) && (!displayname.equalsIgnoreCase("availability")))
/*     */         {
/*     */ 
/* 395 */           displayname = FormatUtil.getString(displayname);
/* 396 */           String archivedTableName = (String)attrExtTableDetails.get(7);
/* 397 */           String columnsToQuery = DBQueryUtil.escapeColumn(value_col, attrId);
/* 398 */           String expression1 = (String)attrExtTableDetails.get(12);
/* 399 */           if ((expression1 != null) && (!expression1.equals("")))
/*     */           {
/* 401 */             columnsToQuery = value_col + expression1;
/*     */           }
/* 403 */           ArrayList attributesToQuery = getAttributeIdsForComplexType(this.resourceType, String.valueOf(this.allAttributeIDs.get(i)));
/* 404 */           boolean isTypeGenericWMI = false;
/* 405 */           if ((this.resourceType.equals("Generic WMI")) || (this.resourceType.toLowerCase().indexOf("win32") != -1))
/*     */           {
/* 407 */             isTypeGenericWMI = true;
/*     */           }
/* 409 */           value_col = "COLUMNVALUE";
/* 410 */           String metricGraphQuery = "select " + resid_col + "," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, this.resourceIds);
/*     */           
/* 412 */           AMLog.info("MyPageBean.produceLineChart called for period : " + this.period + " and resourceIds " + this.resourceIds + " and widgetType : " + this.widgetType);
/*     */           
/* 414 */           if (this.period.equals("20"))
/*     */           {
/* 416 */             ArrayList confAttList = ConfMonitorConfiguration.getInstance().getAttListInDataTables();
/* 417 */             if (confAttList.contains(attrId)) {
/* 418 */               String colTimeCondition = getCollectionTimeCondition(coltime_col);
/* 419 */               metricGraphQuery = metricGraphQuery + colTimeCondition;
/*     */             }
/* 421 */             else if (!attid_col.equals("-1"))
/*     */             {
/* 423 */               metricGraphQuery = "select " + resid_col + "," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, this.resourceIds) + " and   " + attid_col + "=" + String.valueOf(this.allAttributeIDs.get(i));
/*     */             }
/* 425 */             else if (datatable.equalsIgnoreCase("TableSpaceStatus"))
/*     */             {
/* 427 */               metricGraphQuery = "select RESOURCEID," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "TableSpaceStatus.RESOURCENAME", "':'", "TableSpaceStatus.TABLESPACENAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 428 */               resid_col = "RESOURCEID";
/*     */             }
/* 430 */             else if (datatable.equalsIgnoreCase("DataFiles"))
/*     */             {
/* 432 */               metricGraphQuery = "select RESOURCEID," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "DataFiles.RESOURCENAME", "':'", "DataFiles.FILE_NAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 433 */               resid_col = "RESOURCEID";
/*     */             }
/* 435 */             else if (datatable.equalsIgnoreCase("RollbackData"))
/*     */             {
/* 437 */               metricGraphQuery = "select RESOURCEID," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on    " + DBQueryUtil.concat(new String[] { "RollbackData.RESOURCENAME", "':'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 438 */               resid_col = "RESOURCEID";
/*     */             }
/* 440 */             else if (resid_col.equalsIgnoreCase("RESOURCENAME"))
/*     */             {
/* 442 */               metricGraphQuery = "select RESOURCEID," + columnsToQuery + " as " + value_col + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject on  AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + " where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 443 */               resid_col = "RESOURCEID";
/*     */             }
/* 445 */             else if ((this.resourceType.equals("JMX1.2-MX4J-RMI")) || (this.resourceType.equals("SNMP")) || (isTypeGenericWMI))
/*     */             {
/* 447 */               metricGraphQuery = getSimilarAttributeForCAM_Actions(this.resourceType, String.valueOf(this.allAttributeIDs.get(i)), null, "getGraphQuery");
/* 448 */               value_col = "VALUE";
/* 449 */               resid_col = "RESOURCEID";
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 454 */             long[] timeStamps = null;
/* 455 */             timeStamps = ReportUtilities.getTimeStamp(this.period);
/* 456 */             String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/* 457 */             if (this.resourceType.equals("SNMP"))
/*     */             {
/* 459 */               ResultSet attrRs = null;
/*     */               try
/*     */               {
/* 462 */                 attrRs = AMConnectionPool.executeQueryStmt("select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID where AM_CAM_DC_ATTRIBUTES.DISPLAYNAME in (select DISPLAYNAME from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + this.attributeid + ")");
/* 463 */                 while (attrRs.next())
/*     */                 {
/* 465 */                   String attId = attrRs.getString(1);
/* 466 */                   if (!attributesToQuery.contains(attId))
/*     */                   {
/* 468 */                     attributesToQuery.add(attId);
/*     */                   }
/*     */                 }
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 474 */                 e.printStackTrace();
/*     */               }
/*     */               finally
/*     */               {
/* 478 */                 AMConnectionPool.closeStatement(attrRs);
/*     */               }
/* 480 */               AMLog.debug("MyPageBean.produceLineChart called for SNMP archieved data attributesToQuery : " + attributesToQuery);
/*     */             }
/* 482 */             metricGraphQuery = "select RESID,TOTAL/TOTALCOUNT as VALUE,ARCHIVEDTIME from " + archivedTableName + " where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attributesToQuery) + " and " + ManagedApplication.getCondition("RESID", this.resourceIds);
/* 483 */             metricGraphQuery = metricGraphQuery + dailyRptCondition;
/* 484 */             resid_col = "RESID";
/* 485 */             coltime_col = "ARCHIVEDTIME";
/* 486 */             value_col = "VALUE";
/* 487 */             metricGraphQuery = getReportQueryForComplexAttribute(metricGraphQuery, attributesToQuery, this.resourceType);
/*     */           }
/* 489 */           HashMap<String, TimeSeries> timeseriesmap = new HashMap();
/* 490 */           System.out.println("metricGraphQuery--->" + metricGraphQuery);
/*     */           try {
/* 492 */             rs = AMConnectionPool.executeQueryStmt(metricGraphQuery);
/*     */           } catch (SQLException sqlexe) {
/* 494 */             sqlexe.printStackTrace();
/* 495 */             continue;
/*     */           }
/* 497 */           while (rs.next())
/*     */           {
/* 499 */             String resid = rs.getString(resid_col);
/* 500 */             long value = 1L;
/*     */             try {
/* 502 */               value = rs.getLong(value_col);
/*     */             } catch (SQLException sql) {
/* 504 */               sql.printStackTrace(); }
/* 505 */             continue;
/*     */             
/* 507 */             if (value >= 0L)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 512 */               long collectionTime = rs.getLong(coltime_col);
/* 513 */               Date d = new Date(collectionTime);
/* 514 */               boolean ispolledData = "20".equals(this.period);
/* 515 */               if (DashboardUtil.isBHour(collectionTime, this.bhrDetails, ispolledData))
/*     */               {
/* 517 */                 if (timeseriesmap.containsKey(resid))
/*     */                 {
/*     */ 
/* 520 */                   TimeSeries existingSeries = (TimeSeries)timeseriesmap.get(resid);
/* 521 */                   existingSeries.addOrUpdate(new Minute(d), new Long(value));
/*     */                 }
/*     */                 else
/*     */                 {
/* 525 */                   String res_dispName = "";
/* 526 */                   if ((this.residDisplayNames != null) && (this.residDisplayNames.get(resid) != null))
/*     */                   {
/* 528 */                     res_dispName = (String)this.residDisplayNames.get(resid);
/*     */                   }
/* 530 */                   TimeSeries newSeries = null;
/* 531 */                   if (this.widgetType.equals("2")) {
/* 532 */                     newSeries = new TimeSeries(displayname, Minute.class);
/*     */                   } else {
/* 534 */                     newSeries = new TimeSeries(res_dispName, Minute.class);
/*     */                   }
/*     */                   
/* 537 */                   newSeries.addOrUpdate(new Minute(d), new Long(rs.getLong(value_col)));
/* 538 */                   timeseriesmap.put(resid, newSeries);
/*     */                 }
/*     */               } else {
/* 541 */                 AMLog.debug("Data Not included for attribute " + displayname + " for resource " + resid + " on Non-Business Hour : " + d);
/* 542 */                 if (timeseriesmap.containsKey(resid))
/*     */                 {
/*     */ 
/* 545 */                   TimeSeries existingSeries = (TimeSeries)timeseriesmap.get(resid);
/* 546 */                   existingSeries.addOrUpdate(new Minute(d), null);
/*     */                 }
/*     */                 else
/*     */                 {
/* 550 */                   String res_dispName = "";
/* 551 */                   if ((this.residDisplayNames != null) && (this.residDisplayNames.get(resid) != null))
/*     */                   {
/* 553 */                     res_dispName = (String)this.residDisplayNames.get(resid);
/*     */                   }
/* 555 */                   TimeSeries newSeries = null;
/* 556 */                   if (this.widgetType.equals("2")) {
/* 557 */                     newSeries = new TimeSeries(displayname, Minute.class);
/*     */                   } else {
/* 559 */                     newSeries = new TimeSeries(res_dispName, Minute.class);
/*     */                   }
/*     */                   
/* 562 */                   newSeries.addOrUpdate(new Minute(d), null);
/* 563 */                   timeseriesmap.put(resid, newSeries);
/*     */                 }
/*     */               }
/*     */             } }
/* 567 */           System.out.println("timeseries---->" + timeseriesmap);
/*     */           
/* 569 */           for (Iterator it = timeseriesmap.entrySet().iterator(); it.hasNext();)
/*     */           {
/* 571 */             Map.Entry entry = (Map.Entry)it.next();
/* 572 */             String strKey = (String)entry.getKey();
/* 573 */             col.addSeries((TimeSeries)timeseriesmap.get(strKey));
/*     */           }
/* 575 */           timeSeriesSize = timeseriesmap.size();
/*     */         } }
/* 577 */       int tempCount = 0;
/* 578 */       if (this.widgetType.equals("2")) {
/* 579 */         tempCount = this.allAttributeIDs.size();
/*     */       } else {
/* 581 */         tempCount = timeSeriesSize;
/*     */       }
/*     */       
/* 584 */       int[] x = new int[tempCount];
/* 585 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 587 */         x[c] = c;
/*     */       }
/* 589 */       return new SubSeriesDataset(col, x);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 594 */       System.out.println("exception occured for attributeid in mo1" + ManagedApplication.attributesExt);
/* 595 */       System.out.println("exception occured for attributeid in mo2" + this.attributeid);
/* 596 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 602 */         if (rs != null)
/*     */         {
/* 604 */           rs.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 609 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object produceLineChartforAllTypes(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 620 */     Object toReturn = null;
/* 621 */     ResultSet rs = null;
/* 622 */     AMConnectionPool cp = new AMConnectionPool();
/* 623 */     TimeSeriesCollection col = new TimeSeriesCollection();
/* 624 */     int timeSeriesSize = 0;
/*     */     try {
/* 626 */       boolean ispolledData = this.period.equals("20");
/* 627 */       long currenttime = System.currentTimeMillis();
/* 628 */       for (Iterator it1 = this.selectedResidattribsMap.entrySet().iterator(); it1.hasNext();)
/*     */       {
/* 630 */         Map.Entry entry1 = (Map.Entry)it1.next();
/* 631 */         String strKey1 = (String)entry1.getKey();
/*     */         
/* 633 */         this.allAttributeIDs.clear();
/* 634 */         this.allAttributeIDs = ((ArrayList)this.selectedResidattribsMap.get(strKey1));
/* 635 */         this.resourceIds = new ArrayList();
/* 636 */         this.resourceIds.add(strKey1);
/* 637 */         for (int i = 0; i < this.allAttributeIDs.size(); i++) {
/* 638 */           ArrayList<String> attrExtTableDetails = (ArrayList)ManagedApplication.attributesExt.get(this.allAttributeIDs.get(i));
/* 639 */           String attrId = this.allAttributeIDs.get(i).toString();
/* 640 */           String datatable = (String)attrExtTableDetails.get(0);
/* 641 */           String resid_col = (String)attrExtTableDetails.get(1);
/* 642 */           String attid_col = (String)attrExtTableDetails.get(2);
/* 643 */           String value_col = (String)attrExtTableDetails.get(3);
/* 644 */           String coltime_col = (String)attrExtTableDetails.get(4);
/* 645 */           String displayname = (String)attrExtTableDetails.get(5);
/* 646 */           if ((!displayname.equalsIgnoreCase("health")) && (!displayname.equalsIgnoreCase("availability")))
/*     */           {
/*     */ 
/* 649 */             displayname = FormatUtil.getString(displayname);
/* 650 */             String archivedTableName = (String)attrExtTableDetails.get(7);
/* 651 */             String columnsToQuery = DBQueryUtil.escapeColumn(value_col, attrId);
/* 652 */             String expression1 = (String)attrExtTableDetails.get(12);
/* 653 */             if ((expression1 != null) && (!expression1.equals("")))
/*     */             {
/* 655 */               columnsToQuery = value_col + expression1;
/*     */             }
/* 657 */             ArrayList attributesToQuery = getAttributeIdsForComplexType(this.resourceType, String.valueOf(this.allAttributeIDs.get(i)));
/* 658 */             boolean isTypeGenericWMI = false;
/* 659 */             if ((this.resourceType.equals("Generic WMI")) || (this.resourceType.toLowerCase().indexOf("win32") != -1))
/*     */             {
/* 661 */               isTypeGenericWMI = true;
/*     */             }
/* 663 */             String metricGraphQuery = "select " + resid_col + "," + columnsToQuery + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, this.resourceIds);
/*     */             
/* 665 */             if (this.period.equals("20"))
/*     */             {
/* 667 */               if (!attid_col.equals("-1"))
/*     */               {
/* 669 */                 metricGraphQuery = "select " + resid_col + "," + columnsToQuery + "," + coltime_col + " from " + datatable + " where " + ManagedApplication.getCondition(resid_col, this.resourceIds) + " and   " + attid_col + "=" + String.valueOf(this.allAttributeIDs.get(i));
/*     */               }
/* 671 */               else if (datatable.equalsIgnoreCase("TableSpaceStatus"))
/*     */               {
/* 673 */                 metricGraphQuery = "select RESOURCEID," + columnsToQuery + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "TableSpaceStatus.RESOURCENAME", "':'", "TableSpaceStatus.TABLESPACENAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 674 */                 resid_col = "RESOURCEID";
/*     */               }
/* 676 */               else if (datatable.equalsIgnoreCase("DataFiles"))
/*     */               {
/* 678 */                 metricGraphQuery = "select RESOURCEID," + columnsToQuery + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on " + DBQueryUtil.concat(new String[] { "DataFiles.RESOURCENAME", "':'", "DataFiles.FILE_NAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 679 */                 resid_col = "RESOURCEID";
/*     */               }
/* 681 */               else if (datatable.equalsIgnoreCase("RollbackData"))
/*     */               {
/* 683 */                 metricGraphQuery = "select RESOURCEID," + columnsToQuery + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject  on    " + DBQueryUtil.concat(new String[] { "RollbackData.RESOURCENAME", "':'", "RollbackData.TABLESPACENAME", "'_'", "RollbackData.SEGMENTNAME" }) + "=AM_ManagedObject.RESOURCENAME  where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 684 */                 resid_col = "RESOURCEID";
/*     */               }
/* 686 */               else if (resid_col.equalsIgnoreCase("RESOURCENAME"))
/*     */               {
/* 688 */                 metricGraphQuery = "select RESOURCEID," + columnsToQuery + "," + coltime_col + " from " + datatable + " inner join AM_ManagedObject on  AM_ManagedObject.RESOURCENAME=" + datatable + "." + resid_col + " where " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/* 689 */                 resid_col = "RESOURCEID";
/*     */               }
/* 691 */               else if ((this.resourceType.equals("JMX1.2-MX4J-RMI")) || (this.resourceType.equals("SNMP")) || (isTypeGenericWMI))
/*     */               {
/* 693 */                 metricGraphQuery = getSimilarAttributeForCAM_Actions(this.resourceType, String.valueOf(this.allAttributeIDs.get(i)), null, "getGraphQuery");
/* 694 */                 value_col = "VALUE";
/* 695 */                 resid_col = "RESOURCEID";
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 700 */               long[] timeStamps = null;
/* 701 */               timeStamps = ReportUtilities.getTimeStamp(this.period);
/* 702 */               String dailyRptCondition = " and " + archivedTableName + ".DURATION=1 and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1];
/* 703 */               metricGraphQuery = "select RESID,TOTAL/TOTALCOUNT as VALUE,ARCHIVEDTIME from " + archivedTableName + " where " + ManagedApplication.getCondition(new StringBuilder().append(archivedTableName).append(".ATTRIBUTEID").toString(), attributesToQuery) + " and " + ManagedApplication.getCondition("RESID", this.resourceIds);
/* 704 */               metricGraphQuery = metricGraphQuery + dailyRptCondition;
/* 705 */               resid_col = "RESID";
/* 706 */               coltime_col = "ARCHIVEDTIME";
/* 707 */               value_col = "VALUE";
/* 708 */               metricGraphQuery = getReportQueryForComplexAttribute(metricGraphQuery, attributesToQuery, this.resourceType);
/*     */             }
/* 710 */             HashMap<String, TimeSeries> timeseriesmap = new HashMap();
/* 711 */             AMLog.debug("metricGraphQuery--->" + metricGraphQuery);
/*     */             try {
/* 713 */               rs = AMConnectionPool.executeQueryStmt(metricGraphQuery);
/*     */             } catch (SQLException sqlexe) {
/* 715 */               sqlexe.printStackTrace();
/* 716 */               continue;
/*     */             }
/* 718 */             while (rs.next())
/*     */             {
/* 720 */               String resid = rs.getString(resid_col);
/* 721 */               long value = 1L;
/*     */               try {
/* 723 */                 value = rs.getLong(value_col);
/*     */               } catch (SQLException sql) {
/* 725 */                 sql.printStackTrace(); }
/* 726 */               continue;
/*     */               
/* 728 */               if (value >= 0L)
/*     */               {
/*     */ 
/*     */ 
/* 732 */                 long collectiontTime = rs.getLong(coltime_col);
/* 733 */                 Date d = new Date(collectiontTime);
/*     */                 
/* 735 */                 if (DashboardUtil.isBHour(collectiontTime, this.bhrDetails, ispolledData))
/*     */                 {
/* 737 */                   if (timeseriesmap.containsKey(resid))
/*     */                   {
/*     */ 
/* 740 */                     TimeSeries existingSeries = (TimeSeries)timeseriesmap.get(resid);
/* 741 */                     existingSeries.addOrUpdate(new Minute(d), new Long(value));
/*     */                   }
/*     */                   else
/*     */                   {
/* 745 */                     String res_dispName = "";
/* 746 */                     if ((this.residDisplayNames != null) && (this.residDisplayNames.get(resid) != null))
/*     */                     {
/* 748 */                       res_dispName = (String)this.residDisplayNames.get(resid);
/*     */                     }
/* 750 */                     TimeSeries newSeries = null;
/* 751 */                     String resTestforAll = res_dispName + "_" + displayname;
/* 752 */                     newSeries = new TimeSeries(resTestforAll, Minute.class);
/* 753 */                     newSeries.addOrUpdate(new Minute(d), new Long(rs.getLong(value_col)));
/* 754 */                     timeseriesmap.put(resid, newSeries);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 760 */             for (Iterator it = timeseriesmap.entrySet().iterator(); it.hasNext();)
/*     */             {
/* 762 */               Map.Entry entry = (Map.Entry)it.next();
/* 763 */               String strKey = (String)entry.getKey();
/* 764 */               col.addSeries((TimeSeries)timeseriesmap.get(strKey));
/*     */             }
/* 766 */             timeSeriesSize = timeseriesmap.size();
/*     */           }
/*     */         }
/*     */       }
/* 770 */       int tempCount = this.allAttributeIDs.size();
/*     */       
/* 772 */       int[] x = new int[tempCount];
/* 773 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 775 */         x[c] = c;
/*     */       }
/* 777 */       toReturn = new SubSeriesDataset(col, x);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 782 */       AMLog.debug("exception occured for attributeid in mo1:::" + ManagedApplication.attributesExt);
/* 783 */       AMLog.debug("exception occured for attributeid in mo2:::" + this.attributeid);
/* 784 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 788 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/* 791 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 800 */     if (this.graphType.equals("dial"))
/*     */     {
/* 802 */       return produceDialDataset(params);
/*     */     }
/* 804 */     if (this.graphType.equals("bar"))
/*     */     {
/* 806 */       return produceBarDataset(params);
/*     */     }
/* 808 */     if (this.graphType.equals("percentageBar"))
/*     */     {
/* 810 */       return produceBarPecentageDataset(params);
/*     */     }
/* 812 */     if (this.graphType.equals("pieChart"))
/*     */     {
/* 814 */       return producePieChartDataset(params);
/*     */     }
/* 816 */     if (this.graphType.equals("line"))
/*     */     {
/* 818 */       return produceLineChart(params);
/*     */     }
/* 820 */     return null;
/*     */   }
/*     */   
/*     */   private String getReportQueryForComplexAttribute(String metricGraphQuery, ArrayList attribslistCheckedforComplextype, String resourcetype)
/*     */   {
/* 825 */     String modifiedReportQuery = "";
/*     */     
/*     */ 
/* 828 */     ArrayList listofReportDataTables = new ArrayList();
/*     */     try
/*     */     {
/* 831 */       if ((resourcetype.equals("$ComplexType_Servers")) || (resourcetype.equals("$ComplexType_All")) || (resourcetype.equals("$ComplexType_Windows")))
/*     */       {
/* 833 */         for (int i = 0; i < attribslistCheckedforComplextype.size(); i++)
/*     */         {
/* 835 */           String attributeid = (String)attribslistCheckedforComplextype.get(i);
/* 836 */           ArrayList attrExtTableDetails = this.mo.getCachedAttributeDetails(attributeid);
/* 837 */           if (attrExtTableDetails != null)
/*     */           {
/* 839 */             String reportDataTable = (String)attrExtTableDetails.get(7);
/* 840 */             if (!listofReportDataTables.contains(reportDataTable))
/*     */             {
/*     */ 
/*     */ 
/* 844 */               listofReportDataTables.add(reportDataTable);
/* 845 */               if (modifiedReportQuery.equals(""))
/*     */               {
/* 847 */                 modifiedReportQuery = "(" + metricGraphQuery.replaceAll("AM_RESPONSETIME_MinMaxAvgData", reportDataTable) + ")";
/*     */               }
/*     */               else
/*     */               {
/* 851 */                 modifiedReportQuery = modifiedReportQuery + " union (" + metricGraphQuery.replaceAll("AM_RESPONSETIME_MinMaxAvgData", reportDataTable) + ")";
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 858 */         return metricGraphQuery;
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */ 
/* 868 */       ex.printStackTrace();
/*     */     }
/* 870 */     return modifiedReportQuery;
/*     */   }
/*     */   
/*     */   public String getCollectionTimeCondition(String coltime_col) {
/* 874 */     String colTimeCondition = "";
/* 875 */     long endTime = System.currentTimeMillis();
/* 876 */     long oneHourInMillis = 3600000L;
/* 877 */     int cleanUpInterval = 5;
/*     */     try {
/* 879 */       if (AMAutomaticPortChanger.rawDataRetentionTime != -1) {
/* 880 */         cleanUpInterval = AMAutomaticPortChanger.rawDataRetentionTime;
/*     */       }
/* 882 */       long startTime = endTime - cleanUpInterval * oneHourInMillis;
/* 883 */       colTimeCondition = " and " + coltime_col + " < " + endTime + " and " + coltime_col + " > " + startTime;
/*     */     }
/*     */     catch (Exception e) {
/* 886 */       e.printStackTrace();
/*     */     }
/* 888 */     return colTimeCondition;
/*     */   }
/*     */   
/*     */   private String getSimilarAttributeForCAM_Actions(String resourcetype, String camattribute, HashMap extraprops, String action) {
/* 892 */     ArrayList camAttributeList = new ArrayList();
/* 893 */     AMConnectionPool cp = new AMConnectionPool();
/* 894 */     ResultSet rs = null;
/* 895 */     ResultSet rs1 = null;
/* 896 */     toreturn = null;
/* 897 */     String dataTable = "AM_CAM_NUMERIC_DATA";
/*     */     
/*     */     try
/*     */     {
/* 901 */       boolean isTypeGenericWMI = false;
/* 902 */       if ((this.resourceType.equals("Generic WMI")) || (this.resourceType.toLowerCase().indexOf("win32") != -1))
/*     */       {
/* 904 */         isTypeGenericWMI = true;
/*     */       }
/* 906 */       if ((resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("SNMP")))
/*     */       {
/* 908 */         String camattribDetailsQuery = "select ATTRIBUTENAME ,TYPE ,GROUPNAME,AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID left outer join AM_CAM_TABLE_COLUMN_MAPPER on AM_CAM_TABLE_COLUMN_MAPPER.COLUMNATTRIBUTEID =AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + camattribute;
/* 909 */         AMLog.info("MyPageBean.getSimilarAttributeForCAM_Actions called camattribDetailsQuery : " + camattribDetailsQuery + " and resourcetype : " + resourcetype);
/* 910 */         rs = AMConnectionPool.executeQueryStmt(camattribDetailsQuery);
/* 911 */         String camAttribName = null;
/* 912 */         String groupName = null;
/* 913 */         int attribType = -1;
/* 914 */         if (rs.next())
/*     */         {
/* 916 */           camAttribName = rs.getString("ATTRIBUTENAME");
/* 917 */           groupName = rs.getString("GROUPNAME");
/* 918 */           attribType = rs.getInt("TYPE");
/* 919 */           if (rs.getString("COLUMNATTRIBUTEID") != null)
/*     */           {
/* 921 */             dataTable = "AM_CAM_COLUMNAR_DATA";
/*     */           }
/* 923 */           else if (attribType == 0)
/*     */           {
/* 925 */             dataTable = "AM_CAM_NUMERIC_DATA";
/*     */           }
/* 927 */           else if (attribType == 1)
/*     */           {
/* 929 */             dataTable = "AM_CAM_STRING_DATA";
/*     */           }
/*     */           
/* 932 */           String allatribsquery = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID and AM_CAM_DC_GROUPS.GROUPNAME='" + groupName + "' where AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME='" + camAttribName + "'";
/* 933 */           if (resourcetype.equals("SNMP"))
/*     */           {
/*     */ 
/* 936 */             allatribsquery = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID  from AM_CAM_DC_ATTRIBUTES inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID where AM_CAM_DC_ATTRIBUTES.DISPLAYNAME in (select DISPLAYNAME from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + camattribute + ")";
/*     */           }
/* 938 */           rs1 = AMConnectionPool.executeQueryStmt(allatribsquery);
/* 939 */           while (rs1.next())
/*     */           {
/* 941 */             camAttributeList.add(rs1.getString("ATTRIBUTEID"));
/*     */           }
/* 943 */           if (action.equals("getGraphQuery"))
/*     */           {
/* 945 */             toreturn = "select RESOURCEID,VALUE,COLLECTIONTIME from " + dataTable + " inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=" + dataTable + ".ATTRIBUTEID inner join AM_CAM_DC_GROUPS on AM_CAM_DC_GROUPS.GROUPID=AM_CAM_DC_ATTRIBUTES.GROUPID  where " + ManagedApplication.getCondition(new StringBuilder().append(dataTable).append(".ATTRIBUTEID").toString(), camAttributeList) + " and " + ManagedApplication.getCondition("RESOURCEID", this.resourceIds);
/*     */           }
/* 947 */           AMLog.info("MyPageBean.getSimilarAttributeForCAM_Actions getGraphQuery is  : " + toreturn + " and resourcetype : " + resourcetype);
/*     */         }
/*     */         
/*     */       }
/* 951 */       else if (isTypeGenericWMI)
/*     */       {
/* 953 */         if (!action.equals("getGraphQuery")) {}
/*     */       }
/* 955 */       return "select ROWID as RESOURCEID,cast(VALUE as decimal(15,2)) as VALUE,COLLECTIONTIME from AM_CAM_COLUMNAR_DATA  where AM_CAM_COLUMNAR_DATA.ATTRIBUTEID=" + camattribute + " and " + ManagedApplication.getCondition("ROWID", this.resourceIds);
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */ 
/* 961 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 967 */         if (rs != null)
/*     */         {
/* 969 */           rs.close();
/*     */         }
/* 971 */         if (rs1 != null)
/*     */         {
/* 973 */           rs1.close();
/*     */         }
/*     */       } catch (Exception ex) {
/* 976 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\MyPageBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */