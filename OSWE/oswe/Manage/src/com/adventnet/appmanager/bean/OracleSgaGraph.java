/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OracleSgaGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  28 */   private String resourcename = "default";
/*  29 */   private String category = "default";
/*  30 */   private String entity = "default";
/*  31 */   private String graphType = "";
/*  32 */   private LinkedHashMap graphValues = new LinkedHashMap();
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   
/*  36 */   public OracleSgaGraph() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  40 */     if ((this.graphType.equals("BAR")) || (this.graphType.equals("PIE"))) {
/*  41 */       DefaultCategoryDataset barData = new DefaultCategoryDataset();
/*  42 */       DefaultPieDataset pieData = new DefaultPieDataset();
/*  43 */       if (this.graphValues != null) {
/*  44 */         Iterator it = this.graphValues.keySet().iterator();
/*     */         try {
/*  46 */           while (it.hasNext()) {
/*  47 */             String displayName = (String)it.next();
/*  48 */             String value = (String)this.graphValues.get(displayName);
/*  49 */             if (this.graphType.equals("BAR")) {
/*  50 */               barData.addValue(Double.valueOf(Double.parseDouble(value)), "", FormatUtil.getString(displayName));
/*     */             }
/*     */             else {
/*  53 */               pieData.setValue(FormatUtil.getString(displayName), Double.parseDouble(value));
/*     */             }
/*     */           }
/*  56 */           if (this.graphType.equals("BAR")) {
/*  57 */             return barData;
/*     */           }
/*     */           
/*  60 */           return pieData;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  64 */           e.printStackTrace();
/*     */         } }
/*  66 */       return null;
/*     */     }
/*     */     
/*  69 */     long curtime = System.currentTimeMillis();
/*  70 */     long queryval = curtime - 3600000L;
/*  71 */     if (getEntity().equals("SGA"))
/*     */     {
/*  73 */       String query = "select COLLECTIONTIME,BUFFERHITRATIO,DICTIONARYHITRATIO,LIBRARYHITRATIO from SgaStatus where RESOURCENAME = '" + getresourceName() + "' and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/*  75 */         this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/*  78 */         return null;
/*     */       }
/*  80 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.oracle.bufferhitratio"), Minute.class);
/*  81 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.oracle.datadictionaryhitratio"), Minute.class);
/*  82 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.oracle.libraryhitratio"), Minute.class);
/*     */       try
/*     */       {
/*  85 */         while (this.rs.next())
/*     */         {
/*  87 */           long ct = this.rs.getLong(1);
/*  88 */           long bhr = this.rs.getLong(2);
/*  89 */           long dhr = this.rs.getLong(3);
/*  90 */           long lhr = this.rs.getLong(4);
/*  91 */           Date d = new Date(ct);
/*     */           try {
/*  93 */             ts.add(new Minute(d), bhr);
/*  94 */             ts1.add(new Minute(d), dhr);
/*  95 */             ts2.add(new Minute(d), lhr);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 102 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 106 */         e.printStackTrace();
/* 107 */         return null;
/*     */       }
/* 109 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 110 */       col.addSeries(ts);
/* 111 */       col.addSeries(ts1);
/* 112 */       col.addSeries(ts2);
/* 113 */       int[] x = { 0, 1, 2 };
/* 114 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 116 */     if (getEntity().equals("USERS"))
/*     */     {
/* 118 */       String query = "select COLLECTIONTIME,AVGUSERS from InstanceStatus where RESOURCENAME = '" + getresourceName() + "' and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 120 */         this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 123 */         return null;
/*     */       }
/* 125 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.oracle.numberofusers"), Minute.class);
/*     */       try
/*     */       {
/* 128 */         while (this.rs.next())
/*     */         {
/* 130 */           long ct = this.rs.getLong(1);
/* 131 */           long nos = this.rs.getLong(2);
/* 132 */           Date d = new Date(ct);
/*     */           try {
/* 134 */             ts.add(new Minute(d), nos);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 141 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 145 */         e.printStackTrace();
/* 146 */         return null;
/*     */       }
/* 148 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 149 */       col.addSeries(ts);
/* 150 */       int[] x = { 0 };
/* 151 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 153 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 162 */     return "oraclesgagraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 167 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 171 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 175 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 179 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 183 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 187 */     this.entity = ent;
/*     */   }
/*     */   
/* 190 */   public void setGraphType(String gtype) { this.graphType = gtype; }
/*     */   
/*     */   public void setGraphValues(LinkedHashMap gValues) {
/* 193 */     this.graphValues = gValues;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\OracleSgaGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */