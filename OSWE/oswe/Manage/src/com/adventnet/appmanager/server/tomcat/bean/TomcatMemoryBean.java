/*     */ package com.adventnet.appmanager.server.tomcat.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TomcatMemoryBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  23 */   private String resourcename = "default";
/*     */   private AMConnectionPool pool;
/*     */   private long total;
/*     */   private long used;
/*     */   private long free;
/*     */   private ResultSet rs;
/*     */   private int resourceid;
/*     */   
/*     */   public void setresourceID(int id)
/*     */   {
/*  33 */     this.resourceid = id;
/*     */   }
/*     */   
/*     */   public int getresourceID() {
/*  37 */     return this.resourceid;
/*     */   }
/*     */   
/*  40 */   public TomcatMemoryBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  44 */     long curtime = System.currentTimeMillis();
/*  45 */     long queryval = curtime - 3600000L;
/*  46 */     String query = "select AM_JVMData.HEAPSIZECURRENT,AM_JVMData.HEAPFREECURRENT,AM_JVMData.COLLECTIONTIME from AM_JVMData where AM_JVMData.ID=" + getresourceID() + " and AM_JVMData.COLLECTIONTIME > " + queryval + "";
/*     */     try {
/*  48 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*  52 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*     */     try
/*     */     {
/*  55 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Total Memory"), Minute.class);
/*  56 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Used Memory"), Minute.class);
/*  57 */       while (this.rs.next())
/*     */       {
/*  59 */         long memtotal = this.rs.getLong(1) / 1024L + this.rs.getLong(2) / 1024L;
/*  60 */         long memused = this.rs.getLong(1) / 1024L;
/*  61 */         long time = this.rs.getLong(3);
/*  62 */         Date d = new Date(time);
/*     */         try
/*     */         {
/*  65 */           ts.addOrUpdate(new Minute(d), memtotal);
/*  66 */           ts1.addOrUpdate(new Minute(d), memused);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  70 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  75 */       col.addSeries(ts);
/*  76 */       col.addSeries(ts1);
/*  77 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  81 */       return null;
/*     */     }
/*  83 */     int[] x = { 0, 1 };
/*  84 */     update(getresourceID());
/*  85 */     return new SubSeriesDataset(col, x);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/*  95 */     return "tomcatgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 100 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 104 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public long gettotal() {
/* 108 */     return this.total;
/*     */   }
/*     */   
/*     */   public void settotal(long cat) {
/* 112 */     this.total = cat;
/*     */   }
/*     */   
/*     */   public long getused() {
/* 116 */     return this.used;
/*     */   }
/*     */   
/*     */   public void setused(long ent) {
/* 120 */     this.used = ent;
/*     */   }
/*     */   
/* 123 */   public long getfree() { return this.free; }
/*     */   
/*     */ 
/*     */ 
/* 127 */   public void setfree(long ent) { this.free = ent; }
/*     */   
/*     */   public void update(int resid) {
/* 130 */     String query = "select max(COLLECTIONTIME) from AM_JVMData where  AM_JVMData.ID=" + resid + "";
/*     */     try {
/* 132 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 133 */       long time = 0L;
/* 134 */       if (this.rs.next()) {
/* 135 */         time = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 140 */       this.rs.close();
/* 141 */       String query1 = "select HEAPSIZECURRENT , HEAPFREECURRENT from AM_JVMData where COLLECTIONTIME=" + time + " and ID=" + resid + "";
/* 142 */       this.rs = AMConnectionPool.executeQueryStmt(query1);
/* 143 */       if (this.rs.next()) {
/* 144 */         settotal(this.rs.getLong(1) + this.rs.getLong(2));
/* 145 */         setused(this.rs.getLong(1));
/* 146 */         setfree(this.rs.getLong(2));
/*     */       }
/*     */       return;
/*     */     } catch (Exception e) {
/* 150 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 156 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\tomcat\bean\TomcatMemoryBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */