/*     */ package com.adventnet.appmanager.bean;
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
/*     */ 
/*     */ public class PerformanceBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  24 */   private String resourcename = "default";
/*  25 */   private String category = "default";
/*  26 */   private String entity = "default";
/*     */   private AMConnectionPool pool;
/*  28 */   private String attributeid = null;
/*     */   private ResultSet rs;
/*     */   
/*  31 */   public PerformanceBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  36 */     long curtime = System.currentTimeMillis();
/*  37 */     long caltime = curtime - 3600000L;
/*  38 */     TimeSeries ts = null;
/*  39 */     if ((this.category != null) && (this.category.equals("ExchangeGraph")))
/*     */     {
/*  41 */       ts = new TimeSeries(FormatUtil.getString(getEntity()), Minute.class);
/*  42 */       String query = "select VALUE , COLLECTIONTIME from AM_EXCHANGESTATS where RESOURCEID=" + getresourceid() + " and ATTRIBUTEID=" + getAttributeid() + " and COLLECTIONTIME >=" + caltime;
/*     */       try {
/*  44 */         this.rs = AMConnectionPool.executeQueryStmt(query);
/*  45 */         while (this.rs.next())
/*     */         {
/*  47 */           Date d = new Date(this.rs.getLong(2));
/*     */           try
/*     */           {
/*  50 */             if (this.rs.getLong(1) > -1L) {
/*  51 */               ts.add(new Minute(d), this.rs.getLong(1));
/*     */             }
/*     */             
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  60 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/*  66 */           this.rs.close();
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */     ts = new TimeSeries(FormatUtil.getString(getEntity()), Minute.class);
/*  78 */     String dataquery = "select AM_ManagedObjectData.RESPONSETIME , AM_ManagedObjectData.COLLECTIONTIME  from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + getresourceid() + " and AM_ManagedObjectData.COLLECTIONTIME >=" + caltime + "";
/*     */     try
/*     */     {
/*  81 */       this.rs = AMConnectionPool.executeQueryStmt(dataquery);
/*  82 */       while (this.rs.next())
/*     */       {
/*  84 */         Date d = new Date(this.rs.getLong(2));
/*     */         try
/*     */         {
/*  87 */           if (this.rs.getLong(1) != -1L) {
/*  88 */             ts.add(new Minute(d), this.rs.getLong(1));
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
/*     */       try
/*     */       {
/* 103 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */       col = new TimeSeriesCollection(ts);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  97 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 103 */         this.rs.close();
/*     */       }
/*     */       catch (Exception exc) {}
/*     */     }
/*     */     
/*     */ 
/*     */     TimeSeriesCollection col;
/*     */     
/*     */ 
/* 112 */     return new SubSeriesDataset(col, 0);
/*     */   }
/*     */   
/*     */   private int resourceid;
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 122 */     return "dummygraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 127 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 131 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 135 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 139 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 143 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 147 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public String getAttributeid() {
/* 151 */     return this.attributeid;
/*     */   }
/*     */   
/*     */   public void setAttributeid(String ent) {
/* 155 */     this.attributeid = ent;
/*     */   }
/*     */   
/*     */   public void setresourceid(int id)
/*     */   {
/* 160 */     this.resourceid = id;
/*     */   }
/*     */   
/* 163 */   public int getresourceid() { return this.resourceid; }
/*     */   
/*     */   public long getResponseTime(int id) {
/* 166 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + id + "";
/* 167 */     long connectiontime = -1L;
/*     */     try
/*     */     {
/* 170 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 171 */       long maxtime = -1L;
/* 172 */       if (rs1.next()) {
/* 173 */         maxtime = rs1.getLong(1);
/*     */       }
/* 175 */       rs1.close();
/* 176 */       String dataquery = "select AM_ManagedObjectData.RESPONSETIME from AM_ManagedObjectData where AM_ManagedObjectData.RESID=" + id + " and COLLECTIONTIME='" + maxtime + "'";
/* 177 */       rs1 = AMConnectionPool.executeQueryStmt(dataquery);
/* 178 */       if (rs1.next()) {
/* 179 */         connectiontime = rs1.getLong(1);
/*     */       }
/* 181 */       rs1.close();
/*     */     }
/*     */     catch (Exception e) {
/* 184 */       e.printStackTrace();
/*     */     }
/* 186 */     return connectiontime;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\PerformanceBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */