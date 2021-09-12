/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
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
/*     */ public class MemGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private String resourcename = "default";
/*  27 */   private String category = "default";
/*  28 */   private String entity = "default";
/*  29 */   private String resourceId = "default";
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet rs1;
/*     */   
/*  34 */   public MemGraph() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  38 */     long curtime = System.currentTimeMillis();
/*  39 */     long queryval = curtime - 21600000L;
/*  40 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*  41 */     int size = 0;
/*  42 */     int swap = 0;
/*  43 */     int physical = 0;
/*  44 */     int cpu = 0;
/*     */     try {
/*  46 */       String swapEntity = FormatUtil.getString("Swap Memory Utilization");
/*  47 */       String physicalEntity = FormatUtil.getString("Physical Memory Utilization");
/*  48 */       String cpuEntity = FormatUtil.getString("am.webclient.hometab.highcpuservers.columnheader.cpuutil");
/*  49 */       TimeSeries ts = null;
/*  50 */       TimeSeries ts1 = null;
/*  51 */       TimeSeries ts2 = null;
/*  52 */       String instquery = "select SWAPMEMUTIL ,PHYMEMUTIL, COLLECTIONTIME ,CPUUTIL from HostCpuMemDataCollected where RESOURCEID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + "";
/*  53 */       AMLog.debug("HostResource : Memory CPU Graph Entity Query:" + instquery);
/*  54 */       this.rs1 = AMConnectionPool.executeQueryStmt(instquery);
/*  55 */       while (this.rs1.next()) {
/*  56 */         long time = this.rs1.getLong(3);
/*  57 */         Date d = new Date(time);
/*  58 */         if (this.rs1.getString(1) != null) {
/*  59 */           if (swap == 0) {
/*  60 */             ts = new TimeSeries(swapEntity, Minute.class);
/*  61 */             swap++;
/*  62 */             size++;
/*     */           }
/*  64 */           long swapValue = this.rs1.getLong(1);
/*     */           try {
/*  66 */             ts.addOrUpdate(new Minute(d), swapValue);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*  71 */         if (this.rs1.getString(2) != null) {
/*  72 */           if (physical == 0) {
/*  73 */             ts1 = new TimeSeries(physicalEntity, Minute.class);
/*  74 */             physical++;
/*  75 */             size++;
/*     */           }
/*  77 */           long phyVal = this.rs1.getLong(2);
/*     */           try {
/*  79 */             ts1.addOrUpdate(new Minute(d), phyVal);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*  84 */         if (this.rs1.getString(4) != null) {
/*  85 */           if (cpu == 0) {
/*  86 */             ts2 = new TimeSeries(cpuEntity, Minute.class);
/*  87 */             cpu++;
/*  88 */             size++;
/*     */           }
/*  90 */           long cpuVal = this.rs1.getLong(4);
/*     */           try {
/*  92 */             ts2.addOrUpdate(new Minute(d), cpuVal);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */       }
/*     */       
/*  98 */       if (ts != null) {
/*  99 */         col.addSeries(ts);
/*     */       }
/* 101 */       if (ts1 != null) {
/* 102 */         col.addSeries(ts1);
/*     */       }
/* 104 */       if (ts2 != null) {
/* 105 */         col.addSeries(ts2);
/*     */       }
/*     */       try {
/* 108 */         this.rs1.close();
/*     */       }
/*     */       catch (Exception exc) {
/* 111 */         exc.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 115 */       e.printStackTrace();
/*     */     }
/* 117 */     int[] x = new int[size];
/* 118 */     for (int j = 0; j < size; j++) {
/* 119 */       x[j] = j;
/*     */     }
/* 121 */     return new SubSeriesDataset(col, x);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 131 */     return "memgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 136 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 140 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getResourceId() {
/* 144 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resource) {
/* 148 */     this.resourceId = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 152 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 156 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 160 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 164 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public Hashtable getMemoryData(String resourcename, long time, String resourceid) {
/* 168 */     String memquery = "select PHYMEMUTILMB, PHYMEMUTIL, SWAPMEMUTILMB, SWAPMEMUTIL,FREEPHYMEMMB from HostCpuMemDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + time;
/*     */     
/* 170 */     Hashtable h = new Hashtable();
/*     */     try {
/* 172 */       this.rs = AMConnectionPool.executeQueryStmt(memquery);
/* 173 */       while (this.rs.next()) {
/* 174 */         if (this.rs.getString(1) != null) {
/* 175 */           Properties p = new Properties();
/* 176 */           p.setProperty("PhysicalMemUtilization", this.entity);
/* 177 */           p.setProperty("CURVALUE", this.rs.getString(2));
/* 178 */           p.setProperty("CURVALUEMB", this.rs.getString(1));
/* 179 */           if (this.rs.getString(5) != null)
/*     */           {
/* 181 */             p.setProperty("FREEPHYMEMMB", this.rs.getString(5));
/*     */           }
/* 183 */           h.put("PhysicalMemUtilization", p);
/*     */         }
/* 185 */         if (this.rs.getString(3) != null) {
/* 186 */           Properties p = new Properties();
/* 187 */           p.setProperty("SwapMemUtilization", this.entity);
/* 188 */           p.setProperty("CURVALUE", this.rs.getString(4));
/* 189 */           p.setProperty("CURVALUEMB", this.rs.getString(3));
/* 190 */           h.put("SwapMemUtilization", p);
/*     */         }
/*     */       }
/* 193 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 196 */       e.printStackTrace();
/* 197 */       AMLog.fatal("HostResource : Error in Fetching the data for Disk ", e);
/*     */     }
/* 199 */     return h;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\MemGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */