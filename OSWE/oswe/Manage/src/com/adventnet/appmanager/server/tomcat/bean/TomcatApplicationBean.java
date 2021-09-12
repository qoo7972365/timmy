/*     */ package com.adventnet.appmanager.server.tomcat.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
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
/*     */ public class TomcatApplicationBean
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private String resourcename = "default";
/*  27 */   private String context = "default";
/*  28 */   private String session = "default";
/*     */   private AMConnectionPool pool;
/*     */   private long total;
/*     */   private long used;
/*     */   private long free;
/*  33 */   private Hashtable hash = new Hashtable();
/*  34 */   private Hashtable appdata = new Hashtable();
/*  35 */   private String attribute = null;
/*     */   Properties prop;
/*     */   Properties info;
/*     */   private int resourceid;
/*     */   SubSeriesDataset ssd;
/*     */   private ResultSet rs;
/*     */   private ResultSet rs1;
/*     */   
/*  43 */   public void setresourceID(int id) { this.resourceid = id; }
/*     */   
/*     */   public int getresourceID()
/*     */   {
/*  47 */     return this.resourceid;
/*     */   }
/*     */   
/*     */ 
/*     */   public TomcatApplicationBean()
/*     */   {
/*  53 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*  56 */   public void setAttribute(String att) { this.attribute = att; }
/*     */   
/*     */ 
/*     */   public String getAttribute()
/*     */   {
/*  61 */     return this.attribute;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  67 */     if (this.attribute.equals("Responsetime"))
/*     */     {
/*  69 */       long curtime = System.currentTimeMillis();
/*  70 */       long queryval = curtime - 3600000L;
/*  71 */       String query = "select AM_TOMCATWEBAPPSTATS.AVGRESPONSETIME,AM_TOMCATWEBAPPSTATS.AVGBYTESPERSECOND,AM_TOMCATWEBAPPSTATS.COLLECTIONTIME from AM_RESOURCECONFIG , AM_TOMCATWEBAPPSTATS , AM_TC_WAR where AM_TC_WAR.PARENTID=" + getresourceID() + " and AM_TC_WAR.WARNAME='" + getcontext() + "' and  AM_TOMCATWEBAPPSTATS.RESOURCEID=AM_TC_WAR.WARID and  AM_TOMCATWEBAPPSTATS.COLLECTIONTIME > " + queryval;
/*     */       try {
/*  73 */         this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/*  76 */         e.printStackTrace();
/*     */       }
/*  78 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try
/*     */       {
/*  81 */         TimeSeries ts = new TimeSeries(FormatUtil.getString("Average Response Time"), Minute.class);
/*     */         
/*  83 */         while (this.rs.next())
/*     */         {
/*  85 */           long memtotal = this.rs.getLong(1);
/*  86 */           long memused = this.rs.getLong(2);
/*  87 */           long time = this.rs.getLong(3);
/*  88 */           Date d = new Date(time);
/*     */           try {
/*  90 */             ts.add(new Minute(d), memtotal);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  98 */         col.addSeries(ts);
/*     */         
/* 100 */         AMConnectionPool.closeStatement(this.rs);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/* 106 */       int[] x = { 0 };
/* 107 */       update(getresourceID());
/* 108 */       this.ssd = new SubSeriesDataset(col, x);
/*     */ 
/*     */     }
/* 111 */     else if (this.attribute.equals("session"))
/*     */     {
/* 113 */       long curtime = System.currentTimeMillis();
/* 114 */       long queryval = curtime - 3600000L;
/* 115 */       String sesquery = "select SEESION,COLLECTIONTIME from AM_TOMCATSESSIONDETAILS where COLLECTIONTIME > " + queryval + " and RESOURCEID=" + getresourceID() + ";";
/*     */       try
/*     */       {
/* 118 */         this.rs = AMConnectionPool.executeQueryStmt(sesquery);
/*     */       }
/*     */       catch (Exception e) {
/* 121 */         e.printStackTrace();
/*     */       }
/* 123 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try
/*     */       {
/* 126 */         TimeSeries ts = new TimeSeries(FormatUtil.getString("No. of Open Sessions"), Minute.class);
/* 127 */         while (this.rs.next()) {
/* 128 */           int sesval = Integer.parseInt(this.rs.getString(1));
/* 129 */           long time = this.rs.getLong(2);
/* 130 */           Date d = new Date(time);
/*     */           try {
/* 132 */             ts.add(new Minute(d), sesval);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 139 */         col.addSeries(ts);
/*     */         
/* 141 */         AMConnectionPool.closeStatement(this.rs);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/* 147 */       int[] x = { 0 };
/* 148 */       this.ssd = new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/* 151 */     return this.ssd;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSession(String ses)
/*     */   {
/* 157 */     this.session = ses;
/*     */   }
/*     */   
/*     */   public String getSession()
/*     */   {
/* 162 */     return this.session;
/*     */   }
/*     */   
/*     */   public int getSessionInfo(int resourceID) {
/* 166 */     int sesval = 0;
/* 167 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_TOMCATSESSIONDETAILS where RESOURCEID=" + resourceID;
/*     */     
/* 169 */     Hashtable h = new Hashtable();
/*     */     try {
/* 171 */       ResultSet rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 172 */       if (rs.next()) {
/* 173 */         long time = rs.getLong(1);
/* 174 */         String dataquery = "select SEESION from AM_TOMCATSESSIONDETAILS where COLLECTIONTIME =" + time + " and RESOURCEID=" + resourceID;
/*     */         try {
/* 176 */           this.rs1 = AMConnectionPool.executeQueryStmt(dataquery);
/*     */         }
/*     */         catch (Exception eee) {}
/*     */         
/*     */ 
/* 181 */         while (this.rs1.next()) {
/* 182 */           sesval = this.rs1.getInt("SEESION");
/*     */         }
/*     */       }
/* 185 */       AMConnectionPool.closeStatement(this.rs1);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 190 */       System.out.println("Exception in Session Info");
/*     */     }
/* 192 */     return sesval;
/*     */   }
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
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 213 */     return "tomcatgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 218 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 222 */     this.resourcename = resource;
/*     */   }
/*     */   
/* 225 */   public String getcontext() { return this.context; }
/*     */   
/*     */   public void setcontext(String resource)
/*     */   {
/* 229 */     this.context = resource;
/*     */   }
/*     */   
/*     */   public Hashtable getContexts(int resourceID) {
/* 233 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_TOMCATWEBAPPSTATS where RESOURCEID=" + resourceID + "";
/* 234 */     Hashtable h = new Hashtable();
/*     */     try {
/* 236 */       ResultSet rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 237 */       if (rs.next()) {
/* 238 */         long time = rs.getLong(1);
/* 239 */         String detailsquery = "select RESID, AM_TC_WAR.WARNAME ,AVAILABILITY , HEALTH , -1   from AM_ManagedObjectData , AM_TC_WAR where AM_ManagedObjectData.COLLECTIONTIME=" + time + " and AM_ManagedObjectData.RESID=AM_TC_WAR.WARID and AM_TC_WAR.PARENTID=" + resourceID + " group by RESID , AM_TC_WAR.WARNAME , AVAILABILITY , HEALTH";
/* 240 */         rs = AMConnectionPool.executeQueryStmt(detailsquery);
/* 241 */         while (rs.next()) {
/* 242 */           Properties p = new Properties();
/* 243 */           p.setProperty("ID", String.valueOf(rs.getInt(1)));
/* 244 */           p.setProperty("Availability", String.valueOf(rs.getInt(3)));
/* 245 */           p.setProperty("Health", String.valueOf(rs.getInt(4)));
/* 246 */           p.setProperty("Alerts", String.valueOf(rs.getInt(5)));
/* 247 */           h.put(rs.getString(2), p);
/*     */         }
/* 249 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 253 */       e.printStackTrace();
/*     */     }
/*     */     
/* 256 */     return h;
/*     */   }
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
/*     */   public Hashtable getContextDetails()
/*     */   {
/* 274 */     return this.hash;
/*     */   }
/*     */   
/* 277 */   public Properties getContextInfo() { return this.info; }
/*     */   
/*     */   public void update(int resid) {
/* 280 */     this.hash.clear();
/* 281 */     String query = "select AM_TC_WAR.WARNAME ,AM_TC_WAR.WARID from AM_RESOURCECONFIG , AM_TC_WAR where AM_RESOURCECONFIG.RESOURCEID=" + getresourceID() + " and AM_TC_WAR.PARENTID=AM_RESOURCECONFIG.RESOURCEID";
/*     */     try {
/* 283 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/* 284 */       while (this.rs.next()) {
/* 285 */         this.prop = new Properties();
/* 286 */         this.prop.setProperty("ID", String.valueOf(this.rs.getInt(2)));
/* 287 */         this.hash.put(this.rs.getString(1), this.prop);
/*     */       }
/*     */       
/* 290 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 293 */       e.printStackTrace();
/*     */     }
/* 295 */     if (!this.context.equals("default"))
/*     */     {
/*     */       try
/*     */       {
/* 299 */         String maxtimequery = "select max(AM_TOMCATWEBAPPSTATS.COLLECTIONTIME) from AM_TOMCATWEBAPPSTATS , AM_TC_WAR  where AM_TC_WAR.PARENTID=" + resid + " and  AM_TOMCATWEBAPPSTATS.RESOURCEID=AM_TC_WAR.WARID";
/* 300 */         this.rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 301 */         long maxtime = 0L;
/* 302 */         if (this.rs.next()) {
/* 303 */           maxtime = this.rs.getLong(1);
/*     */         }
/* 305 */         this.rs.close();
/* 306 */         String dataquery = "select AM_TOMCATWEBAPPSTATS.AVGRESPONSETIME , AM_TOMCATWEBAPPSTATS.AVGBYTESPERSECOND , AM_TOMCATWEBAPPSTATS.REQUESTPERSECOND , AM_TOMCATWEBAPPSTATS.TOTALREQUESTS from AM_TOMCATWEBAPPSTATS , AM_RESOURCECONFIG , AM_TC_WAR where AM_TC_WAR.PARENTID=" + getresourceID() + " and AM_TC_WAR.WARNAME='" + getcontext() + "' and AM_TOMCATWEBAPPSTATS.RESOURCEID=AM_TC_WAR.WARID and AM_TOMCATWEBAPPSTATS.COLLECTIONTIME=" + maxtime;
/* 307 */         this.rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 308 */         if (this.rs.next()) {
/* 309 */           this.info = new Properties();
/* 310 */           this.info.setProperty("AVGRESPONSETIME", String.valueOf(this.rs.getLong(1)));
/* 311 */           this.info.setProperty("REQUESTPERSECOND", String.valueOf(this.rs.getLong(3)));
/* 312 */           this.info.setProperty("TOTALREQUESTS", String.valueOf(this.rs.getLong(4)));
/* 313 */           this.info.setProperty("AVGBYTESPERSECOND", String.valueOf(this.rs.getLong(2)));
/*     */         }
/*     */         else {
/* 316 */           this.info = new Properties();
/* 317 */           long val = 0L;
/* 318 */           this.info.setProperty("AVGRESPONSETIME", String.valueOf(val));
/* 319 */           this.info.setProperty("REQUESTPERSECOND", String.valueOf(val));
/* 320 */           this.info.setProperty("TOTALREQUESTS", String.valueOf(val));
/* 321 */           this.info.setProperty("AVGBYTESPERSECOND", String.valueOf(val));
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 326 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 332 */           AMConnectionPool.closeStatement(this.rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Hashtable getAppData(int resid, String con)
/*     */   {
/* 342 */     this.appdata.clear();
/*     */     try {
/* 344 */       String maxtimequery = " select  max(AM_TOMCATSERVLETSTATS.COLLECTIONTIME) from  AM_TOMCATSERVLETSTATS , AM_TC_URL, AM_TC_WAR where AM_TC_WAR.PARENTID=" + resid + " and AM_TC_WAR.WARNAME='" + con + "' and AM_TC_URL.WARID=AM_TC_WAR.WARID and AM_TOMCATSERVLETSTATS.RESOURCEID=AM_TC_URL.URLID";
/* 345 */       this.rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 346 */       long maxtime = 0L;
/* 347 */       if (this.rs.next()) {
/* 348 */         maxtime = this.rs.getLong(1);
/*     */       }
/* 350 */       this.rs.close();
/* 351 */       String appdataquery = "select AM_TC_URL.URL , AM_TC_URL.URLID , AM_TOMCATSERVLETSTATS.AVGRESPONSETIME , AM_TOMCATSERVLETSTATS.REQUESTPERSECOND ,-1 from AM_TC_URL , AM_TC_WAR , AM_TOMCATSERVLETSTATS  where AM_TC_WAR.WARNAME='" + con + "' and AM_TC_WAR.PARENTID=" + resid + " and AM_TC_URL.WARID=AM_TC_WAR.WARID and AM_TOMCATSERVLETSTATS.RESOURCEID=AM_TC_URL.URLID and AM_TOMCATSERVLETSTATS.COLLECTIONTIME=" + maxtime;
/*     */       
/* 353 */       this.rs = AMConnectionPool.executeQueryStmt(appdataquery);
/* 354 */       while (this.rs.next()) {
/* 355 */         String uri = this.rs.getString(1);
/* 356 */         Properties p = new Properties();
/* 357 */         p.setProperty("AVGRESPONSETIME", String.valueOf(this.rs.getLong(3)));
/* 358 */         p.setProperty("REQUESTPERSECOND", String.valueOf(this.rs.getLong(4)));
/* 359 */         p.setProperty("Health", String.valueOf(this.rs.getLong(5)));
/* 360 */         p.setProperty("ID", String.valueOf(this.rs.getInt(2)));
/* 361 */         this.appdata.put(uri, p);
/*     */       }
/* 363 */       AMConnectionPool.closeStatement(this.rs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 377 */       e.printStackTrace();
/*     */     }
/* 379 */     return this.appdata;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\tomcat\bean\TomcatApplicationBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */