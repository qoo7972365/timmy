/*     */ package com.adventnet.appmanager.server.portmonitoring.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortPerformanceBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  25 */   private String resourcename = "default";
/*  26 */   private String category = "default";
/*  27 */   private String entity = "default";
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   
/*  31 */   public PortPerformanceBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  36 */     long curtime = System.currentTimeMillis();
/*  37 */     long caltime = curtime - 86400000L;
/*  38 */     TimeSeries ts = new TimeSeries("Response Time", Minute.class);
/*  39 */     String query = "select RESPONSETIME , COLLECTIONTIME from AM_ManagedObjectData where RESID=" + getresourceID() + " and COLLECTIONTIME >" + caltime + "";
/*     */     try {
/*  41 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  42 */       while (this.rs.next())
/*     */       {
/*  44 */         Date d = new Date(this.rs.getLong(2));
/*     */         try
/*     */         {
/*  47 */           if (this.rs.getLong(1) != -1L)
/*     */           {
/*  49 */             ts.add(new Minute(d), this.rs.getLong(1));
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
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
/*     */       try
/*     */       {
/*  66 */         this.rs.close();
/*     */       }
/*     */       catch (Exception nullexc)
/*     */       {
/*  70 */         nullexc.printStackTrace();
/*     */       }
/*     */       
/*  73 */       col = new TimeSeriesCollection(ts);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  59 */       e.printStackTrace();
/*  60 */       return null;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  66 */         this.rs.close();
/*     */       }
/*     */       catch (Exception nullexc)
/*     */       {
/*  70 */         nullexc.printStackTrace();
/*     */       }
/*     */     }
/*     */     TimeSeriesCollection col;
/*  74 */     return new SubSeriesDataset(col, 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/*  84 */     return "dummygraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/*  89 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/*  93 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/*  97 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 101 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 105 */     return this.entity;
/*     */   }
/*     */   
/*     */ 
/* 109 */   public void setEntity(String ent) { this.entity = ent; }
/*     */   
/* 111 */   int id = -1;
/*     */   
/* 113 */   public int getresourceID() { return this.id; }
/*     */   
/*     */   public void setresourceID(int resid)
/*     */   {
/* 117 */     this.id = resid;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\portmonitoring\bean\PortPerformanceBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */