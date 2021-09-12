/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.SubSeriesDataset;
/*     */ import org.jfree.data.time.Minute;
/*     */ import org.jfree.data.time.TimeSeries;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AS400Graphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  23 */   private int resid = -1;
/*  24 */   private int dbresid = -1;
/*  25 */   private String type = "default";
/*  26 */   private String resourcename = null;
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  31 */     long curtime = System.currentTimeMillis();
/*  32 */     long queryval = curtime - 21600000L;
/*  33 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  34 */     ResultSet rs = null;
/*     */     
/*  36 */     if (this.type.equals("JOBTYPES")) {
/*  37 */       DefaultPieDataset ds = new DefaultPieDataset();
/*  38 */       System.out.println("JOBTYPES ResourceID :" + this.resid);
/*  39 */       String query = "select * from AM_AS400_JOBTYPES where RESOURCEID='" + this.resid + "'";
/*     */       try {
/*  41 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  42 */         double size = 0.0D;
/*  43 */         for (int i = 0; rs.next(); i++) {
/*  44 */           if (i <= 8)
/*     */           {
/*  46 */             ds.setValue(FormatUtil.getTrimmedText(rs.getString("JOBTYPE"), 20), formatnumber(rs.getDouble("TCOUNT")));
/*     */           } else {
/*  48 */             size += rs.getDouble("TCOUNT");
/*  49 */             if (rs.isLast()) {
/*  50 */               ds.setValue("Other Databases", formatnumber(size));
/*     */             }
/*     */           }
/*     */         }
/*  54 */         rs.close();
/*     */       }
/*     */       catch (Exception e) {
/*  57 */         e.printStackTrace();
/*     */       }
/*  59 */       return ds;
/*     */     }
/*     */     
/*  62 */     if (this.type.equals("ACTIVEJOBS")) {
/*  63 */       String query = "select COLLECTIONTIME , NO_OF_ACTIVE_JOBS   from AM_AS400_STATUS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/*  65 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*  69 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       
/*  71 */       TimeSeries ts = new TimeSeries("Active Jobs", Minute.class);
/*     */       
/*     */       try
/*     */       {
/*  75 */         while (rs.next())
/*     */         {
/*  77 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  80 */             ts.add(new Minute(d), rs.getDouble("NO_OF_ACTIVE_JOBS"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  88 */         col.addSeries(ts);
/*  89 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  93 */         col.addSeries(ts);
/*     */       }
/*  95 */       int[] x = { 0 };
/*  96 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/*  99 */     if (this.type.equals("BATCHJOBSONQ")) {
/* 100 */       String query = "select COLLECTIONTIME , BATCHJOBHELDONJOBQUEUE   from AM_AS400_STATUS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 102 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 106 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       
/* 108 */       TimeSeries ts = new TimeSeries("Batch jobs held on jobqueue", Minute.class);
/*     */       
/*     */       try
/*     */       {
/* 112 */         while (rs.next())
/*     */         {
/* 114 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 117 */             ts.add(new Minute(d), rs.getDouble("BATCHJOBHELDONJOBQUEUE"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 125 */         col.addSeries(ts);
/* 126 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 130 */         col.addSeries(ts);
/*     */       }
/* 132 */       int[] x = { 0 };
/* 133 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/*     */ 
/* 137 */     if (this.type.equals("USERSSIGNEDON")) {
/* 138 */       String query = "select COLLECTIONTIME , USERS_SIGNED_ON   from AM_AS400_STATUS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 140 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 144 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       
/* 146 */       TimeSeries ts = new TimeSeries("Users Signed on", Minute.class);
/*     */       
/*     */       try
/*     */       {
/* 150 */         while (rs.next())
/*     */         {
/* 152 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 155 */             ts.add(new Minute(d), rs.getDouble("USERS_SIGNED_ON"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 163 */         col.addSeries(ts);
/* 164 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 168 */         col.addSeries(ts);
/*     */       }
/* 170 */       int[] x = { 0 };
/* 171 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   public int getresid() {
/* 186 */     return this.resid;
/*     */   }
/*     */   
/*     */   public void setresid(int resource) {
/* 190 */     this.resid = resource;
/*     */   }
/*     */   
/*     */   public int getdbresid() {
/* 194 */     return this.dbresid;
/*     */   }
/*     */   
/*     */   public void setdbresid(int resource) {
/* 198 */     this.dbresid = resource;
/*     */   }
/*     */   
/*     */   public String gettype() {
/* 202 */     return this.type;
/*     */   }
/*     */   
/*     */   public void settype(String cat) {
/* 206 */     this.type = cat;
/*     */   }
/*     */   
/*     */   public void setresourcename(String name) {
/* 210 */     this.resourcename = name;
/*     */   }
/*     */   
/*     */ 
/* 214 */   public String getresourcename() { return this.resourcename; }
/*     */   
/*     */   public double formatnumber(double d) {
/* 217 */     double value = 0.0D;
/*     */     try {
/* 219 */       String temp = new Double(d * 100.0D).longValue() + "";
/* 220 */       if (temp.length() < 2) {
/* 221 */         temp = "0" + temp;
/*     */       }
/* 223 */       temp = temp.substring(0, temp.length() - 2) + "." + temp.substring(temp.length() - 2, temp.length());
/* 224 */       value = Double.parseDouble(temp);
/*     */     } catch (Exception e) {
/* 226 */       e.printStackTrace();
/*     */     }
/* 228 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\AS400Graphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */