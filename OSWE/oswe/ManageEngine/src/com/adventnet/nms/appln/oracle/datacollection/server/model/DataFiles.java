/*     */ package com.adventnet.nms.appln.oracle.datacollection.server.model;
/*     */ 
/*     */ import com.adventnet.nms.applnfw.datacollection.server.model.ApplnData;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class DataFiles extends ApplnData
/*     */ {
/*     */   private int reads;
/*     */   private String status;
/*     */   private double createBytes;
/*     */   private int avgrdTime;
/*     */   private int avgwrTime;
/*     */   private String tableSpaceName;
/*     */   private int writes;
/*     */   private String file_name;
/*     */   
/*     */   public int getReads()
/*     */   {
/*  19 */     return this.reads;
/*     */   }
/*     */   
/*     */   public void setReads(int arg0)
/*     */   {
/*  24 */     this.reads = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getStatus()
/*     */   {
/*  31 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(String arg0)
/*     */   {
/*  36 */     this.status = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getCreateBytes()
/*     */   {
/*  43 */     return this.createBytes;
/*     */   }
/*     */   
/*     */   public void setCreateBytes(double arg0)
/*     */   {
/*  48 */     this.createBytes = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAvgrdTime()
/*     */   {
/*  55 */     return this.avgrdTime;
/*     */   }
/*     */   
/*     */   public void setAvgrdTime(int arg0)
/*     */   {
/*  60 */     this.avgrdTime = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAvgwrTime()
/*     */   {
/*  67 */     return this.avgwrTime;
/*     */   }
/*     */   
/*     */   public void setAvgwrTime(int arg0)
/*     */   {
/*  72 */     this.avgwrTime = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTableSpaceName()
/*     */   {
/*  79 */     return this.tableSpaceName;
/*     */   }
/*     */   
/*     */   public void setTableSpaceName(String arg0)
/*     */   {
/*  84 */     this.tableSpaceName = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getWrites()
/*     */   {
/*  91 */     return this.writes;
/*     */   }
/*     */   
/*     */   public void setWrites(int arg0)
/*     */   {
/*  96 */     this.writes = arg0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getFile_name()
/*     */   {
/* 103 */     return this.file_name;
/*     */   }
/*     */   
/*     */   public void setFile_name(String arg0)
/*     */   {
/* 108 */     this.file_name = arg0;
/*     */   }
/*     */   
/*     */   public String getKey()
/*     */   {
/* 113 */     return getResourceName() + "\t" + getComponentName() + "\t" + getFile_name() + "\t" + getCollectionTime();
/*     */   }
/*     */   
/*     */   public String getKeyName()
/*     */   {
/* 118 */     return "resourceName\tcomponentName\tfile_name\tcollectionTime";
/*     */   }
/*     */   
/*     */   public Properties getProperties()
/*     */   {
/* 123 */     Properties p = super.getProperties();
/* 124 */     p.put("reads", String.valueOf(this.reads));
/* 125 */     if (this.status != null) p.put("status", this.status);
/* 126 */     p.put("createBytes", String.valueOf(this.createBytes));
/* 127 */     p.put("avgrdTime", String.valueOf(this.avgrdTime));
/* 128 */     p.put("avgwrTime", String.valueOf(this.avgwrTime));
/* 129 */     if (this.tableSpaceName != null) p.put("tableSpaceName", this.tableSpaceName);
/* 130 */     p.put("writes", String.valueOf(this.writes));
/* 131 */     if (this.file_name != null) p.put("file_name", this.file_name);
/* 132 */     return p;
/*     */   }
/*     */   
/*     */   public void setProperties(Properties p)
/*     */   {
/* 137 */     String s = null;
/* 138 */     if ((s = (String)p.remove("reads")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 142 */         this.reads = Integer.parseInt(s);
/*     */       }
/*     */       catch (NumberFormatException ne) {}
/*     */     }
/*     */     
/*     */ 
/* 148 */     if ((s = (String)p.remove("status")) != null) this.status = s;
/* 149 */     if ((s = (String)p.remove("createBytes")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 153 */         this.createBytes = Double.parseDouble(s);
/*     */ 
/*     */       }
/*     */       catch (NumberFormatException ne)
/*     */       {
/* 158 */         ne.printStackTrace();
/*     */       }
/*     */     }
/* 161 */     if ((s = (String)p.remove("avgrdTime")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 165 */         this.avgrdTime = Integer.parseInt(s);
/*     */       }
/*     */       catch (NumberFormatException ne) {}
/*     */     }
/*     */     
/*     */ 
/* 171 */     if ((s = (String)p.remove("avgwrTime")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 175 */         this.avgwrTime = Integer.parseInt(s);
/*     */       }
/*     */       catch (NumberFormatException ne) {}
/*     */     }
/*     */     
/*     */ 
/* 181 */     if ((s = (String)p.remove("tableSpaceName")) != null) this.tableSpaceName = s;
/* 182 */     if ((s = (String)p.remove("writes")) != null)
/*     */     {
/*     */       try
/*     */       {
/* 186 */         this.writes = Integer.parseInt(s);
/*     */       }
/*     */       catch (NumberFormatException ne) {}
/*     */     }
/*     */     
/*     */ 
/* 192 */     if ((s = (String)p.remove("file_name")) != null) this.file_name = s;
/* 193 */     super.setProperties(p);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\appln\oracle\datacollection\server\model\DataFiles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */