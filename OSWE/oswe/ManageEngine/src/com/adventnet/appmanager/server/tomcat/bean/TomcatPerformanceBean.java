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
/*     */ public class TomcatPerformanceBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private String resourcename = "default";
/*     */   private AMConnectionPool pool;
/*     */   private long total;
/*     */   private long used;
/*     */   private long free;
/*     */   Properties prop;
/*     */   private ResultSet rs;
/*     */   private int resourceid;
/*     */   
/*     */   public void setresourceID(int id)
/*     */   {
/*  37 */     this.resourceid = id;
/*     */   }
/*     */   
/*     */   public int getresourceID() {
/*  41 */     return this.resourceid;
/*     */   }
/*     */   
/*  44 */   public TomcatPerformanceBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  48 */     long curtime = System.currentTimeMillis();
/*  49 */     long queryval = curtime - 3600000L;
/*  50 */     String query = "select AM_TOMCATPERFORMANCESTATS.AVGRESPONSETIME,AM_TOMCATPERFORMANCESTATS.AVGBYTESPERSECOND,AM_TOMCATPERFORMANCESTATS.COLLECTIONTIME from AM_TOMCATPERFORMANCESTATS where AM_TOMCATPERFORMANCESTATS.RESOURCEID=" + getresourceID() + " and AM_TOMCATPERFORMANCESTATS.COLLECTIONTIME > " + queryval + "";
/*     */     try {
/*  52 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*  56 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*     */     try
/*     */     {
/*  59 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Average Response Time"), Minute.class);
/*     */       
/*  61 */       while (this.rs.next())
/*     */       {
/*  63 */         long memtotal = this.rs.getLong(1);
/*  64 */         long memused = this.rs.getLong(2);
/*  65 */         long time = this.rs.getLong(3);
/*     */         try
/*     */         {
/*  68 */           Date d = new Date(time);
/*  69 */           ts.addOrUpdate(new Minute(d), memtotal);
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*  74 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  79 */       col.addSeries(ts);
/*     */       
/*  81 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  85 */       return null;
/*     */     }
/*  87 */     int[] x = { 0 };
/*  88 */     return new SubSeriesDataset(col, x);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/*  98 */     return "tomcatgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 103 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 107 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public long gettotal() {
/* 111 */     return this.total;
/*     */   }
/*     */   
/*     */   public void settotal(long cat) {
/* 115 */     this.total = cat;
/*     */   }
/*     */   
/*     */   public long getused() {
/* 119 */     return this.used;
/*     */   }
/*     */   
/*     */   public void setused(long ent) {
/* 123 */     this.used = ent;
/*     */   }
/*     */   
/* 126 */   public long getfree() { return this.free; }
/*     */   
/*     */   public void setfree(long ent)
/*     */   {
/* 130 */     this.free = ent;
/*     */   }
/*     */   
/* 133 */   public void setProperties(Properties p) { this.prop = p; }
/*     */   
/*     */   public Properties getProperties(int resid) {
/* 136 */     p = new Properties();
/* 137 */     String query = "select max(COLLECTIONTIME) from AM_TOMCATPERFORMANCESTATS where AM_TOMCATPERFORMANCESTATS.RESOURCEID=" + resid + "";
/*     */     try {
/* 139 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 140 */       long time = 0L;
/* 141 */       if (this.rs.next()) {
/* 142 */         time = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 147 */       this.rs.close();
/* 148 */       String query1 = "select AVGRESPONSETIME,REQUESTPERSECOND,TOTALREQUESTS,AVGBYTESPERSECOND from AM_TOMCATPERFORMANCESTATS where COLLECTIONTIME=" + time + " and RESOURCEID=" + resid + "";
/* 149 */       this.rs = AMConnectionPool.executeQueryStmt(query1);
/* 150 */       if (this.rs.next()) {
/* 151 */         p.setProperty("AVGRESPONSETIME", String.valueOf(this.rs.getLong(1)));
/* 152 */         p.setProperty("REQUESTPERSECOND", String.valueOf(this.rs.getLong(2)));
/* 153 */         p.setProperty("TOTALREQUESTS", String.valueOf(this.rs.getLong(3)));
/* 154 */         p.setProperty("AVGBYTESPERSECOND", String.valueOf(this.rs.getLong(4)));
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
/* 172 */       return p;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 158 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 164 */         this.rs.close();
/*     */       }
/*     */       catch (Exception nullexc)
/*     */       {
/* 168 */         nullexc.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\tomcat\bean\TomcatPerformanceBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */