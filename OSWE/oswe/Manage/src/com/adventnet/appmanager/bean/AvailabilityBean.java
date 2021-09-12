/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ 
/*     */ 
/*     */ public class AvailabilityBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  18 */   private String resourcename = "default";
/*  19 */   private String clrMessage = null;
/*  20 */   private String errorMessage = null;
/*  21 */   private long downtime = 0L;
/*  22 */   private long StartTime = 0L;
/*  23 */   private AMConnectionPool pool = null;
/*     */   private ResultSet rs;
/*     */   private ResultSet start;
/*     */   private ResultSet end;
/*  27 */   private long TotalMonitoredTime = 0L;
/*  28 */   private long lastFailureStartTime = 0L;
/*  29 */   private long lastFailureEndTime = 0L;
/*     */   private DefaultPieDataset ds;
/*     */   private Hashtable downtable;
/*     */   
/*     */   public AvailabilityBean() {
/*  34 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Hashtable getDownDetails() {
/*  38 */     return this.downtable;
/*     */   }
/*     */   
/*     */   public void setresourceName(String name) {
/*  42 */     this.resourcename = name;
/*     */   }
/*     */   
/*  45 */   public String getresourceName() { return this.resourcename; }
/*     */   
/*     */   public String getclrMessage()
/*     */   {
/*  49 */     return this.clrMessage;
/*     */   }
/*     */   
/*  52 */   public void setclrMessage(String clrmess) { this.clrMessage = clrmess; }
/*     */   
/*     */ 
/*     */   public String geterrorMessage()
/*     */   {
/*  57 */     return this.errorMessage;
/*     */   }
/*     */   
/*  60 */   public void seterrorMessage(String errmess) { this.errorMessage = errmess; }
/*     */   
/*     */   public long getdowntime()
/*     */     throws Exception
/*     */   {
/*  65 */     this.downtime = 0L;
/*  66 */     if ((this.errorMessage != null) && (this.clrMessage != null))
/*     */     {
/*  68 */       String errquery = "select count(*) from Event where ENTITY='" + this.resourcename + "' and TEXT like '%" + this.errorMessage + "%'";
/*  69 */       String clrquery = "select count(*) from Event where ENTITY='" + this.resourcename + "' and TEXT like '%" + this.clrMessage + "%'";
/*  70 */       int errcount = 0;
/*  71 */       int clrcount = 0;
/*     */       try {
/*  73 */         this.rs = AMConnectionPool.executeQueryStmt(errquery);
/*  74 */         if (this.rs.next())
/*     */         {
/*  76 */           errcount = this.rs.getInt(1);
/*     */         }
/*  78 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  82 */         this.rs.close();
/*  83 */         e.printStackTrace();
/*     */       }
/*     */       try {
/*  86 */         this.rs = AMConnectionPool.executeQueryStmt(clrquery);
/*  87 */         if (this.rs.next())
/*     */         {
/*  89 */           clrcount = this.rs.getInt(1);
/*     */         }
/*  91 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e) {
/*  94 */         this.rs.close();
/*  95 */         e.printStackTrace();
/*     */       }
/*     */       
/*  98 */       String queryfordownstarttime = "select TTIME from Event where ENTITY='" + this.resourcename + "' and TEXT like '%" + this.errorMessage + "%' order by TTIME asc";
/*  99 */       String queryfordownendtime = "select TTIME from Event where ENTITY='" + this.resourcename + "' and TEXT like '%" + this.clrMessage + "%' order by TTIME asc";
/*     */       
/* 101 */       this.start = AMConnectionPool.executeQueryStmt(queryfordownstarttime);
/* 102 */       this.end = AMConnectionPool.executeQueryStmt(queryfordownendtime);
/* 103 */       long starttime = 0L;
/* 104 */       long endtime = 0L;
/* 105 */       long down = 0L;
/* 106 */       this.downtable = new Hashtable();
/* 107 */       if ((errcount == clrcount) && (errcount != 0) && (clrcount != 0))
/*     */       {
/* 109 */         int i = 0;
/*     */         
/* 111 */         while (this.start.next())
/*     */         {
/* 113 */           Properties p = new Properties();
/* 114 */           this.end.next();
/* 115 */           starttime = this.start.getLong(1);
/* 116 */           endtime = this.end.getLong(1);
/* 117 */           p.setProperty("Start", String.valueOf(starttime));
/* 118 */           p.setProperty("End", String.valueOf(endtime));
/* 119 */           down = endtime - starttime;
/* 120 */           this.downtime += down;
/* 121 */           this.downtable.put(new Integer(i), p);
/* 122 */           i++;
/*     */         }
/* 124 */         this.lastFailureStartTime = starttime;
/* 125 */         this.lastFailureEndTime = endtime;
/*     */       }
/* 127 */       else if ((errcount > clrcount) && (errcount - clrcount == 1))
/*     */       {
/* 129 */         int i = 0;
/*     */         
/* 131 */         while (this.end.next())
/*     */         {
/*     */ 
/* 134 */           this.start.next();
/* 135 */           starttime = this.start.getLong(1);
/* 136 */           endtime = this.end.getLong(1);
/* 137 */           down = endtime - starttime;
/* 138 */           this.downtime += down;
/* 139 */           Properties p = new Properties();
/* 140 */           p.setProperty("Start", String.valueOf(starttime));
/* 141 */           p.setProperty("End", String.valueOf(endtime));
/* 142 */           this.downtable.put(new Integer(i), p);
/* 143 */           i++;
/*     */         }
/* 145 */         if (this.start.next())
/*     */         {
/* 147 */           starttime = this.start.getLong(1);
/* 148 */           endtime = System.currentTimeMillis();
/* 149 */           down = endtime - starttime;
/* 150 */           this.downtime += down;
/* 151 */           Properties p = new Properties();
/* 152 */           p.setProperty("Start", String.valueOf(starttime));
/* 153 */           p.setProperty("End", String.valueOf(endtime));
/* 154 */           this.downtable.put(new Integer(i), p);
/* 155 */           i++;
/*     */         }
/* 157 */         this.lastFailureStartTime = starttime;
/* 158 */         this.lastFailureEndTime = 0L;
/*     */       }
/* 160 */       else if ((errcount == 0) && (clrcount == 0))
/*     */       {
/*     */ 
/* 163 */         this.downtime = 0L;
/* 164 */         this.lastFailureEndTime = 0L;
/* 165 */         this.lastFailureStartTime = 0L;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 171 */       return this.downtime;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 176 */     return this.downtime;
/*     */   }
/*     */   
/*     */   public void setdowntime(long dt) {
/* 180 */     this.downtime = dt;
/*     */   }
/*     */   
/*     */   public long getStartTime() throws Exception {
/* 184 */     String infoquery = "select TTIME from Event where ENTITY='" + this.resourcename + "' and TEXT like '%Tomcat-server Object Added to Database%'";
/* 185 */     this.rs = AMConnectionPool.executeQueryStmt(infoquery);
/* 186 */     if (this.rs.next())
/*     */     {
/* 188 */       this.StartTime = this.rs.getLong(1);
/*     */     }
/*     */     
/* 191 */     this.rs.close();
/* 192 */     return this.StartTime;
/*     */   }
/*     */   
/* 195 */   public void setStartTime(long st) { this.StartTime = st; }
/*     */   
/*     */   public long getTotalMonitoredTime()
/*     */   {
/* 199 */     this.TotalMonitoredTime = 0L;
/* 200 */     long curtime = System.currentTimeMillis();
/*     */     try {
/* 202 */       this.TotalMonitoredTime = (curtime - getStartTime());
/*     */     }
/*     */     catch (Exception e) {
/* 205 */       e.printStackTrace();
/*     */     }
/* 207 */     return this.TotalMonitoredTime;
/*     */   }
/*     */   
/* 210 */   public void setTotalMonitoredTime(long total) { this.TotalMonitoredTime = total; }
/*     */   
/*     */   public long getlastFailureStartTime()
/*     */   {
/* 214 */     return this.lastFailureStartTime;
/*     */   }
/*     */   
/* 217 */   public void setlastFailureStartTime(long laststart) { this.lastFailureStartTime = laststart; }
/*     */   
/*     */   public long getlastFailureEndTime()
/*     */   {
/* 221 */     return this.lastFailureEndTime;
/*     */   }
/*     */   
/* 224 */   public void setlastFailureEndTime(long lastend) { this.lastFailureEndTime = lastend; }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/* 228 */     this.ds = new DefaultPieDataset();
/*     */     try {
/* 230 */       this.ds.setValue("Down Time", getdowntime());
/* 231 */       this.ds.setValue("Up Time", getTotalMonitoredTime() - getdowntime());
/*     */     }
/*     */     catch (Exception e) {
/* 234 */       e.printStackTrace();
/*     */     }
/* 236 */     return this.ds;
/*     */   }
/*     */   
/* 239 */   public boolean hasExpired(Map params, Date since) { return true; }
/*     */   
/*     */   public String getProducerId()
/*     */   {
/* 243 */     return "availabilitygraph";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\AvailabilityBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */