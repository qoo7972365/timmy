/*     */ package com.adventnet.appmanager.server.tomcat.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.category.DefaultIntervalCategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TomcatThreadBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private String resourcename = "default";
/*     */   private AMConnectionPool pool;
/*     */   private long busy;
/*     */   private long current;
/*     */   private long max;
/*     */   private ResultSet rs;
/*     */   private int resourceid;
/*  33 */   ArrayList threads = new ArrayList();
/*     */   
/*     */ 
/*     */   public void setresourceID(int id)
/*     */   {
/*  38 */     this.resourceid = id;
/*     */   }
/*     */   
/*     */   public int getresourceID() {
/*  42 */     return this.resourceid;
/*     */   }
/*     */   
/*  45 */   public TomcatThreadBean() { this.pool = AMConnectionPool.getInstance(); }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  49 */     long curtime = System.currentTimeMillis();
/*  50 */     long maxtime = 0L;
/*  51 */     long queryval = curtime - 3600000L;
/*  52 */     String query = "select max(COLLECTIONTIME) from AM_TOMCATTHREADSTATS , AM_Thread where AM_Thread.PARENTID=" + getresourceID() + " and AM_TOMCATTHREADSTATS.RESOURCEID=AM_Thread.ID";
/*     */     
/*     */     try
/*     */     {
/*  56 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  57 */       if (this.rs.next())
/*     */       {
/*  59 */         maxtime = this.rs.getLong(1);
/*     */       }
/*  61 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  65 */       e.printStackTrace();
/*     */     }
/*  67 */     query = "select AM_ManagedObject.RESOURCENAME , AM_TOMCATTHREADSTATS.RESOURCEID , AM_TOMCATTHREADSTATS.BUSYTHREADS , AM_TOMCATTHREADSTATS.CURRENTTHREADS , AM_TOMCATTHREADSTATS.MAXSPARETHREADS from AM_TOMCATTHREADSTATS , AM_Thread , AM_ManagedObject where AM_Thread.PARENTID=" + getresourceID() + " and AM_TOMCATTHREADSTATS.RESOURCEID=AM_Thread.ID  and AM_TOMCATTHREADSTATS.COLLECTIONTIME=" + maxtime + " and AM_ManagedObject.RESOURCEID=AM_Thread.ID";
/*     */     
/*     */     try
/*     */     {
/*  71 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*  72 */       while (this.rs.next())
/*     */       {
/*  74 */         ArrayList list = new ArrayList();
/*  75 */         list.add(this.rs.getString("RESOURCENAME"));
/*  76 */         list.add(this.rs.getString("BUSYTHREADS"));
/*  77 */         list.add(this.rs.getString("CURRENTTHREADS"));
/*  78 */         list.add(this.rs.getString("MAXSPARETHREADS"));
/*  79 */         list.add(this.rs.getString("RESOURCEID"));
/*  80 */         this.threads.add(list);
/*     */       }
/*  82 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  86 */       return null;
/*     */     }
/*     */     
/*  89 */     String[] seriesNames = { FormatUtil.getString("Busy Threads"), FormatUtil.getString("Current Threads") };
/*  90 */     String[] categories = new String[this.threads.size()];
/*  91 */     Long[][] startValues = new Long[seriesNames.length][categories.length];
/*  92 */     Long[][] endValues = new Long[seriesNames.length][categories.length];
/*  93 */     System.out.println("Threads Size is " + this.threads.size());
/*  94 */     for (int i = 0; i < categories.length; i++)
/*     */     {
/*  96 */       ArrayList thread = (ArrayList)this.threads.get(i);
/*  97 */       categories[i] = ((String)thread.get(0));
/*  98 */       this.busy = 0L;
/*  99 */       long curr = 0L;
/* 100 */       this.max = 0L;
/*     */       try
/*     */       {
/* 103 */         this.busy = Long.parseLong((String)thread.get(1));
/* 104 */         curr = Long.parseLong((String)thread.get(2));
/* 105 */         this.max = Long.parseLong((String)thread.get(3));
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 109 */         e.printStackTrace();
/*     */       }
/* 111 */       System.out.println("Values are " + this.busy + curr + this.max);
/* 112 */       startValues[0][i] = new Long(0L);
/* 113 */       endValues[0][i] = new Long(this.busy);
/* 114 */       startValues[1][i] = new Long(0L);
/* 115 */       endValues[1][i] = new Long(curr);
/*     */     }
/*     */     
/*     */ 
/* 119 */     System.out.println(categories.length + " " + seriesNames.length);
/* 120 */     DefaultIntervalCategoryDataset ds = new DefaultIntervalCategoryDataset(seriesNames, categories, startValues, endValues);
/* 121 */     return ds;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList getData()
/*     */   {
/* 128 */     return this.threads;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 136 */     return "tomcatgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 141 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 145 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public long getbusy() {
/* 149 */     return this.busy;
/*     */   }
/*     */   
/*     */   public void setbusy(long cat) {
/* 153 */     this.busy = cat;
/*     */   }
/*     */   
/*     */   public long getcurrent() {
/* 157 */     return this.current;
/*     */   }
/*     */   
/*     */   public void setcurrent(long ent) {
/* 161 */     this.current = ent;
/*     */   }
/*     */   
/* 164 */   public long getmax() { return this.max; }
/*     */   
/*     */ 
/*     */ 
/* 168 */   public void setmax(long ent) { this.max = ent; }
/*     */   
/*     */   public void update(int resid) {
/* 171 */     String query = "select max(COLLECTIONTIME) from AM_TOMCATTHREADSTATS where AM_TOMCATTHREADSTATS.RESOURCEID=" + resid + "";
/*     */     try {
/* 173 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 174 */       long time = 0L;
/* 175 */       if (this.rs.next()) {
/* 176 */         time = this.rs.getLong(1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 181 */       this.rs.close();
/* 182 */       String query1 = "select BUSYTHREADS , CURRENTTHREADS , MAXSPARETHREADS from AM_TOMCATTHREADSTATS where COLLECTIONTIME=" + time + " and RESOURCEID=" + resid + "";
/* 183 */       this.rs = AMConnectionPool.executeQueryStmt(query1);
/* 184 */       if (this.rs.next()) {
/* 185 */         setbusy(this.rs.getLong(1));
/* 186 */         setcurrent(this.rs.getLong(2));
/* 187 */         setmax(this.rs.getLong(3));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 191 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\tomcat\bean\TomcatThreadBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */