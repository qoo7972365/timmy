/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
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
/*     */ public class HostResourceBean
/*     */ {
/*  20 */   private String resourcename = "default";
/*  21 */   private String resourceId = "default";
/*  22 */   private long maxcollectiontime = 0L;
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   
/*     */   public HostResourceBean()
/*     */   {
/*  28 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public String getresourceName() {
/*  32 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/*  36 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getResourceId() {
/*  40 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resourceid) {
/*  44 */     this.resourceId = resourceid;
/*     */   }
/*     */   
/*     */   public long getmaxcollectiontime()
/*     */   {
/*  49 */     String query = "select max(COLLECTIONTIME) from HostCpuMemDataCollected where RESOURCEID=" + getResourceId();
/*     */     try {
/*  51 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  52 */       if (this.rs.next()) {
/*  53 */         this.maxcollectiontime = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  58 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/*  61 */       AMLog.fatal("HostResource : Problem in getting the max(COLLECTIONTIME) from HostDataCollected table");
/*  62 */       e.printStackTrace();
/*     */     }
/*     */     
/*  65 */     return this.maxcollectiontime;
/*     */   }
/*     */   
/*     */   public Properties getHostProperty(String name)
/*     */   {
/*  70 */     Properties p = new Properties();
/*     */     try {
/*  72 */       boolean configuredState = false;
/*  73 */       String query = "select AM_ManagedObject.TYPE,HostDetails.USERNAME,HostDetails.PASSWORD,HostDetails.CATEGORY,CollectData.POLLINTERVAL from AM_ManagedObject,HostDetails,CollectData where HostDetails.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.RESOURCENAME=HostDetails.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + getResourceId();
/*     */       
/*     */ 
/*     */       try
/*     */       {
/*  78 */         this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */         
/*  80 */         if (this.rs.next())
/*     */         {
/*  82 */           p.setProperty("USERNAME", this.rs.getString(2));
/*  83 */           p.setProperty("PASSWORD", this.rs.getString(3));
/*  84 */           p.setProperty("OS", this.rs.getString(1));
/*  85 */           p.setProperty("POLL", this.rs.getString(5));
/*  86 */           p.setProperty("CONFIGURED", "true");
/*     */         }
/*     */         else {
/*  89 */           p.setProperty("CONFIGURED", "false");
/*     */         }
/*  91 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/*     */ 
/*  99 */     return p;
/*     */   }
/*     */   
/*     */   public Vector getCategoryList()
/*     */   {
/* 104 */     String query = "select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + getResourceId();
/*     */     try {
/* 106 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 107 */       if (this.rs.next()) {
/* 108 */         this.maxcollectiontime = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 113 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 116 */       AMLog.fatal("HostResource : Problem in getting the max(COLLECTIONTIME) from HostDataCollected table", e);
/* 117 */       e.printStackTrace();
/*     */     }
/*     */     
/* 120 */     Vector v = new Vector();
/* 121 */     if (this.maxcollectiontime != 0L) {
/* 122 */       String categoryquery = "select distinct(CATEGORY) from HostDataCollected where COLLECTIONTIME=" + this.maxcollectiontime + " and RESOURCENAME='" + getresourceName() + "'";
/* 123 */       String temp = null;
/*     */       try {
/* 125 */         this.rs = AMConnectionPool.executeQueryStmt(categoryquery);
/* 126 */         while (this.rs.next()) {
/* 127 */           temp = this.rs.getString(1);
/* 128 */           v.add(temp);
/*     */         }
/* 130 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e) {
/* 133 */         AMLog.fatal("HostResource : Problem in Getting the Categories for the Host" + getresourceName(), e);
/* 134 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 137 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\HostResourceBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */