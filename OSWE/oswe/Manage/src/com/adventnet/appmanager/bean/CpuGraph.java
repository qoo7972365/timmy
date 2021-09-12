/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
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
/*     */ public class CpuGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private String resourcename = "default";
/*  27 */   private String category = "default";
/*  28 */   private String entity = "default";
/*  29 */   private String resourceId = "default";
/*     */   private AMConnectionPool pool;
/*  31 */   private long maxcollectiontime = 0L;
/*     */   private ResultSet rs;
/*     */   
/*  34 */   public CpuGraph() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  38 */     long curtime = System.currentTimeMillis();
/*  39 */     long queryval = curtime - 3600000L;
/*  40 */     String query = "select COLLECTIONTIME, CPUUTIL from HostCpuMemDataCollected where RESOURCEID=" + getResourceId() + " and COLLECTIONTIME >=" + queryval + "";
/*     */     try {
/*  42 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*  46 */     TimeSeries ts = new TimeSeries("CPU Utilization", Minute.class);
/*     */     try {
/*  48 */       while (this.rs.next())
/*     */       {
/*  50 */         long ct = this.rs.getLong(1);
/*  51 */         long val = this.rs.getLong(2);
/*  52 */         Date d = new Date(ct);
/*     */         try
/*     */         {
/*  55 */           ts.addOrUpdate(new Minute(d), val);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  59 */           e.printStackTrace();
/*     */         }
/*     */       }
/*  62 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/*  67 */     TimeSeriesCollection col = new TimeSeriesCollection(ts);
/*  68 */     return new SubSeriesDataset(col, 0);
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/*  77 */     return "cpugraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/*  82 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/*  86 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/*  90 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/*  94 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resourceid)
/*     */   {
/*  99 */     this.resourceId = resourceid;
/*     */   }
/*     */   
/*     */   public String getResourceId()
/*     */   {
/* 104 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 108 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 112 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public Properties getCpuData(String name, long time, String resourceid) {
/* 116 */     Properties p = new Properties();
/* 117 */     String cpuquery = "select CPUUTIL from HostCpuMemDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + time;
/*     */     try {
/* 119 */       this.rs = AMConnectionPool.executeQueryStmt(cpuquery);
/* 120 */       if ((this.rs.next()) && 
/* 121 */         (this.rs.getString(1) != null))
/*     */       {
/* 123 */         p.setProperty("CURVALUE", String.valueOf(this.rs.getLong(1)));
/*     */       }
/*     */       
/* 126 */       this.rs.close();
/* 127 */       String peakcpuquery = "select max(CPUUTIL) from HostCpuMemDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME <=" + time + " and COLLECTIONTIME > " + (time - 3600000L);
/* 128 */       this.rs = AMConnectionPool.executeQueryStmt(peakcpuquery);
/* 129 */       if ((this.rs.next()) && 
/* 130 */         (this.rs.getString(1) != null))
/*     */       {
/* 132 */         p.setProperty("MAXVALUE", String.valueOf(this.rs.getLong(1)));
/*     */       }
/*     */       
/* 135 */       this.rs.close();
/* 136 */       p.setProperty("SEVERITY", "5");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 140 */       AMLog.fatal("HostResource : Exception in getting the data for CPU in HostResources", e);
/*     */     }
/*     */     
/* 143 */     return p;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Properties getLatestCpuUsage(String name, long time, String resourceid)
/*     */   {
/* 154 */     Properties p = new Properties();
/* 155 */     String cpuquery = "select CPUUTIL from HostCpuMemDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + time;
/*     */     try {
/* 157 */       this.rs = AMConnectionPool.executeQueryStmt(cpuquery);
/* 158 */       if (this.rs.next()) {
/* 159 */         String cpuutil = this.rs.getString(1);
/* 160 */         if (cpuutil != null)
/*     */         {
/* 162 */           p.setProperty("CURVALUE", cpuutil);
/*     */         }
/*     */       }
/* 165 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 168 */       AMLog.fatal("HostResource : Exception in getting the data for CPU in HostResources", e);
/*     */     }
/*     */     
/* 171 */     return p;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\CpuGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */