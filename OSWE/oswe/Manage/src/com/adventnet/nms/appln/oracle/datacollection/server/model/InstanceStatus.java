/*     */ package com.adventnet.nms.appln.oracle.datacollection.server.model;
/*     */ 
/*     */ import com.adventnet.nms.applnfw.datacollection.server.model.ApplnData;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ public class InstanceStatus
/*     */   extends ApplnData
/*     */ {
/*     */   private long reads;
/*     */   private int avgUsers;
/*     */   private int blockSize;
/*     */   private int avgExecs;
/*     */   private long writes;
/*     */   private long dbsize;
/*     */   
/*     */   public long getReads()
/*     */   {
/*  19 */     return this.reads;
/*     */   }
/*     */   
/*     */   public void setReads(long arg0)
/*     */   {
/*  24 */     this.reads = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAvgUsers()
/*     */   {
/*  31 */     return this.avgUsers;
/*     */   }
/*     */   
/*     */   public void setAvgUsers(int arg0)
/*     */   {
/*  36 */     this.avgUsers = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getBlockSize()
/*     */   {
/*  43 */     return this.blockSize;
/*     */   }
/*     */   
/*     */   public void setBlockSize(int arg0)
/*     */   {
/*  48 */     this.blockSize = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAvgExecs()
/*     */   {
/*  55 */     return this.avgExecs;
/*     */   }
/*     */   
/*     */   public void setAvgExecs(int arg0)
/*     */   {
/*  60 */     this.avgExecs = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getWrites()
/*     */   {
/*  67 */     return this.writes;
/*     */   }
/*     */   
/*     */   public void setWrites(long arg0)
/*     */   {
/*  72 */     this.writes = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getDbsize()
/*     */   {
/*  79 */     return this.dbsize;
/*     */   }
/*     */   
/*     */   public void setDbsize(long arg0)
/*     */   {
/*  84 */     this.dbsize = arg0;
/*     */   }
/*     */   
/*     */   public String getKey()
/*     */   {
/*  89 */     return getResourceName() + "\t" + getComponentName() + "\t" + getCollectionTime();
/*     */   }
/*     */   
/*     */   public String getKeyName()
/*     */   {
/*  94 */     return "resourceName\tcomponentName\tcollectionTime";
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getProperties()
/*     */   {
/* 100 */     Properties p = super.getProperties();
/*     */     
/* 102 */     p.put("reads", String.valueOf(this.reads));
/*     */     
/* 104 */     p.put("avgUsers", String.valueOf(this.avgUsers));
/*     */     
/* 106 */     p.put("blockSize", String.valueOf(this.blockSize));
/*     */     
/* 108 */     p.put("avgExecs", String.valueOf(this.avgExecs));
/*     */     
/* 110 */     p.put("writes", String.valueOf(this.writes));
/*     */     
/* 112 */     p.put("dbsize", String.valueOf(this.dbsize));
/* 113 */     return p;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProperties(Properties p)
/*     */   {
/* 121 */     String s = null;
/* 122 */     if ((s = (String)p.remove("reads")) != null)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 127 */         this.reads = Long.parseLong(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 133 */     if ((s = (String)p.remove("avgUsers")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 137 */         this.avgUsers = Integer.parseInt(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 143 */     if ((s = (String)p.remove("blockSize")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 147 */         this.blockSize = Integer.parseInt(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 153 */     if ((s = (String)p.remove("avgExecs")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 157 */         this.avgExecs = Integer.parseInt(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 163 */     if ((s = (String)p.remove("writes")) != null)
/*     */     {
/*     */       try
/*     */       {
/*     */ 
/* 168 */         this.writes = Long.parseLong(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 174 */     if ((s = (String)p.remove("dbsize")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 178 */         this.dbsize = Long.parseLong(s);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     
/*     */ 
/* 184 */     super.setProperties(p);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\appln\oracle\datacollection\server\model\InstanceStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */