/*     */ package com.adventnet.appmanager.server.mysql.bean;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySqlGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  28 */   private int resid = -1;
/*  29 */   private String type = "default";
/*  30 */   private String attributes = "";
/*  31 */   private String baseid = "-1";
/*     */   
/*     */ 
/*     */ 
/*  35 */   private long threadcachesize = 0L;
/*     */   
/*  37 */   public void setthreadcachesize(long value) { this.threadcachesize = value; }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException {
/*  40 */     long curtime = System.currentTimeMillis();
/*  41 */     long queryval = curtime - 3600000L;
/*  42 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  43 */     ResultSet rs = null;
/*  44 */     if (this.type.equals("CONNECTIONTIME")) {
/*  45 */       String query = "select COLLECTIONTIME, CONNECTIONTIME from AM_MYSQLPERFORMANCE where  RESOURCEID=" + getresid();
/*     */       try {
/*  47 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*  51 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Connection Time"), Minute.class);
/*  52 */       TimeSeriesCollection col = new TimeSeriesCollection(ts);
/*     */       try {
/*  54 */         while (rs.next())
/*     */         {
/*  56 */           long ct = rs.getLong(1);
/*  57 */           long val = rs.getLong(2);
/*  58 */           Date d = new Date(ct);
/*     */           try
/*     */           {
/*  61 */             ts.addOrUpdate(new Minute(d), val);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*  67 */         if (rs != null)
/*     */         {
/*  69 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*  75 */       return new SubSeriesDataset(col, 0);
/*     */     }
/*  77 */     if (this.type.equals("REQUESTRATE")) {
/*  78 */       String query = "select COLLECTIONTIME ,REQUESTRATE from AM_MYSQLPERFORMANCE where RESOURCEID=" + this.resid;
/*     */       try {
/*  80 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*  84 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*  85 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Request Rate"), Minute.class);
/*     */       try
/*     */       {
/*  88 */         while (rs.next())
/*     */         {
/*  90 */           long rate = rs.getLong(2);
/*     */           
/*  92 */           long time = rs.getLong(1);
/*  93 */           Date d = new Date(time);
/*     */           try
/*     */           {
/*  96 */             ts.addOrUpdate(new Minute(d), rate);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 104 */         col.addSeries(ts);
/* 105 */         if (rs != null)
/*     */         {
/* 107 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 112 */         col.addSeries(ts);
/*     */       }
/* 114 */       int[] x = { 0 };
/* 115 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/* 118 */     if (this.type.equals("MAXCONNECTION"))
/*     */     {
/* 120 */       String query = "select COLLECTIONTIME ,OPENCONNECTIONS,ABORTEDCONNECTIONS,ABORTEDCLIENTS from AM_MYSQLSERVERTUNING where RESOURCEID=" + this.resid;
/*     */       try {
/* 122 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 125 */         e.printStackTrace();
/*     */       }
/* 127 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 128 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Open Connections"), Minute.class);
/* 129 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Aborted Connections"), Minute.class);
/* 130 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Aborted Clients"), Minute.class);
/*     */       try
/*     */       {
/* 133 */         while (rs.next())
/*     */         {
/* 135 */           long rate = rs.getLong(2);
/*     */           
/* 137 */           long recrate = rs.getLong(3);
/* 138 */           long sendrate = rs.getLong(4);
/* 139 */           long time = rs.getLong(1);
/* 140 */           Date d = new Date(time);
/*     */           try
/*     */           {
/* 143 */             ts.addOrUpdate(new Minute(d), rate);
/* 144 */             ts1.addOrUpdate(new Minute(d), recrate);
/* 145 */             ts2.addOrUpdate(new Minute(d), sendrate);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 153 */         col.addSeries(ts);
/* 154 */         col.addSeries(ts1);
/* 155 */         col.addSeries(ts2);
/* 156 */         if (rs != null)
/*     */         {
/* 158 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 163 */         col.addSeries(ts);
/* 164 */         col.addSeries(ts1);
/* 165 */         col.addSeries(ts2);
/* 166 */         e.printStackTrace();
/*     */       }
/* 168 */       int[] x = { 0, 1, 2 };
/* 169 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/* 172 */     if (this.type.equals("THREADS"))
/*     */     {
/* 174 */       String query = "select COLLECTIONTIME ,THREADSUSED,THREADSINCACHE  from AM_MYSQLSERVERTUNING where RESOURCEID=" + this.resid;
/*     */       try {
/* 176 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 179 */         e.printStackTrace();
/*     */       }
/* 181 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 182 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Threads Used"), Minute.class);
/* 183 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Threads in Cache"), Minute.class);
/* 184 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Thread Cache Size"), Minute.class);
/*     */       try
/*     */       {
/* 187 */         while (rs.next())
/*     */         {
/*     */ 
/* 190 */           long rate = rs.getLong(2);
/* 191 */           long recrate = rs.getLong(3);
/* 192 */           long time = rs.getLong(1);
/* 193 */           Date d = new Date(time);
/*     */           try
/*     */           {
/* 196 */             ts.addOrUpdate(new Minute(d), rate);
/* 197 */             ts1.addOrUpdate(new Minute(d), recrate);
/* 198 */             ts2.addOrUpdate(new Minute(d), this.threadcachesize);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 206 */         col.addSeries(ts);
/* 207 */         col.addSeries(ts1);
/* 208 */         col.addSeries(ts2);
/* 209 */         if (rs != null)
/*     */         {
/* 211 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 216 */         col.addSeries(ts);
/* 217 */         col.addSeries(ts1);
/* 218 */         col.addSeries(ts2);
/* 219 */         e.printStackTrace();
/*     */       }
/* 221 */       int[] x = { 0, 1, 2 };
/* 222 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 224 */     if (this.type.equals("DBSIZE")) {
/* 225 */       DefaultPieDataset ds = new DefaultPieDataset();
/* 226 */       String query = "select AM_ManagedObject.DISPLAYNAME, AM_MYSQLDBTUNING.DBSIZE from AM_ManagedObject,AM_MYSQLDBTUNING where AM_MYSQLDBTUNING.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.RESOURCENAME like '%" + getresourcename() + "' and AM_ManagedObject.TYPE = 'DataBase'  and collectiontime =" + getcollectiontime() + " order by AM_MYSQLDBTUNING.DBSIZE desc";
/*     */       try {
/* 228 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 229 */         double size = 0.0D;
/* 230 */         for (int i = 0; rs.next(); i++)
/*     */         {
/* 232 */           if (i <= 5) {
/* 233 */             ds.setValue(FormatUtil.getTrimmedText(rs.getString("DISPLAYNAME"), 20), formatnumber(rs.getDouble("DBSIZE") / 1048576.0D));
/*     */           } else {
/* 235 */             size += rs.getDouble("DBSIZE");
/* 236 */             if (rs.isLast())
/*     */             {
/* 238 */               ds.setValue("Other Databases", formatnumber(size / 1048576.0D));
/*     */             }
/*     */           }
/*     */         }
/* 242 */         if (rs != null)
/*     */         {
/* 244 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 249 */         e.printStackTrace();
/*     */       }
/* 251 */       return ds;
/*     */     }
/* 253 */     if (this.type.equals("CUSTOMMONS")) {
/* 254 */       DefaultPieDataset ds = new DefaultPieDataset();
/*     */       
/* 256 */       String query = "select AM_ATTRIBUTES.DISPLAYNAME,value from AM_Script_Numeric_Data_" + getBaseid() + ",AM_ATTRIBUTES WHERE RESOURCEID=" + this.resid + " and AM_ATTRIBUTES.attributeid in (" + this.attributes + ") and AM_ATTRIBUTES.ATTRIBUTEID=AM_Script_Numeric_Data_" + getBaseid() + ".ATTRIBUTEID and collectiontime =" + getcollectiontime();
/* 257 */       System.out.println("The piechartquery =====>" + query);
/*     */       try {
/* 259 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 260 */         double size = 0.0D;
/* 261 */         for (int i = 0; rs.next(); i++)
/*     */         {
/* 263 */           if (i <= 5) {
/* 264 */             ds.setValue(FormatUtil.getTrimmedText(FormatUtil.getString(rs.getString("DISPLAYNAME")), 20), rs.getDouble("value"));
/*     */           } else {
/* 266 */             size += rs.getDouble("value");
/* 267 */             if (rs.isLast())
/*     */             {
/* 269 */               ds.setValue("Other Stats", size);
/*     */             }
/*     */           }
/*     */         }
/* 273 */         rs.last();
/* 274 */         if (rs.getRow() == 0) {
/* 275 */           ds = null;
/*     */         }
/* 277 */         if (rs != null)
/*     */         {
/* 279 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 284 */         e.printStackTrace();
/*     */       }
/* 286 */       return ds;
/*     */     }
/*     */     
/* 289 */     if (this.type.equals("LOCKS"))
/*     */     {
/* 291 */       String query = "select COLLECTIONTIME , IMMEDIATELOCKS , LOCKSWAIT from AM_MYSQLTABLELOCKS where RESOURCEID=" + this.resid;
/*     */       try {
/* 293 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 296 */         e.printStackTrace();
/*     */       }
/* 298 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 299 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Immediate Locks"), Minute.class);
/* 300 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Locks Wait"), Minute.class);
/*     */       try
/*     */       {
/* 303 */         while (rs.next())
/*     */         {
/*     */ 
/* 306 */           long rate = rs.getLong(2);
/* 307 */           long recrate = rs.getLong(3);
/* 308 */           long time = rs.getLong(1);
/* 309 */           Date d = new Date(time);
/*     */           try
/*     */           {
/* 312 */             ts.addOrUpdate(new Minute(d), rate);
/* 313 */             ts1.addOrUpdate(new Minute(d), recrate);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 321 */         col.addSeries(ts);
/* 322 */         col.addSeries(ts1);
/* 323 */         if (rs != null)
/*     */         {
/* 325 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 330 */         col.addSeries(ts);
/* 331 */         col.addSeries(ts1);
/* 332 */         e.printStackTrace();
/*     */       }
/* 334 */       int[] x = { 0, 1 };
/* 335 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 337 */     if (this.type.equals("QCACHE"))
/*     */     {
/* 339 */       String query = "select COLLECTIONTIME , QUERYCACHEHITRATE from AM_MYSQLMEMORY where RESOURCEID=" + getresid();
/*     */       try {
/* 341 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 344 */         e.printStackTrace();
/* 345 */         return null;
/*     */       }
/* 347 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 348 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Query Cache Hitrate"), Minute.class);
/*     */       try
/*     */       {
/* 351 */         while (rs.next())
/*     */         {
/*     */ 
/* 354 */           long hitrate = rs.getLong(2);
/* 355 */           long time = rs.getLong(1);
/* 356 */           Date d = new Date(time);
/* 357 */           if (hitrate == -1L) {
/* 358 */             return null;
/*     */           }
/*     */           try
/*     */           {
/* 362 */             ts.addOrUpdate(new Minute(d), hitrate);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 370 */         col.addSeries(ts);
/* 371 */         if (rs != null)
/*     */         {
/* 373 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 378 */         col.addSeries(ts);
/* 379 */         e.printStackTrace();
/*     */       }
/* 381 */       int[] x = { 0 };
/* 382 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 384 */     if (this.type.equals("KEY"))
/*     */     {
/* 386 */       String query = "select COLLECTIONTIME , KEYHITRATE from AM_MYSQLMEMORY where RESOURCEID=" + getresid();
/*     */       try {
/* 388 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 391 */         e.printStackTrace();
/* 392 */         return null;
/*     */       }
/* 394 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 395 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Key Hitrate"), Minute.class);
/*     */       try
/*     */       {
/* 398 */         while (rs.next())
/*     */         {
/*     */ 
/* 401 */           long hitrate = rs.getLong(2);
/* 402 */           long time = rs.getLong(1);
/* 403 */           Date d = new Date(time);
/* 404 */           if (hitrate == -1L) {
/* 405 */             return null;
/*     */           }
/*     */           try
/*     */           {
/* 409 */             ts.addOrUpdate(new Minute(d), hitrate);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 417 */         col.addSeries(ts);
/* 418 */         if (rs != null)
/*     */         {
/* 420 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 425 */         col.addSeries(ts);
/* 426 */         e.printStackTrace();
/*     */       }
/* 428 */       int[] x = { 0 };
/* 429 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 431 */     if (this.type.equals("QUERY_STATS")) {
/* 432 */       String statsQuery = "select DELETEDRATE, INSERTEDRATE, SELECTEDRATE, UPDATEDRATE, COLLECTIONTIME from AM_MYSQLQUERYSTATS where RESOURCEID = " + getresid();
/* 433 */       TimeSeries timeSeries1 = new TimeSeries(FormatUtil.getString("am.webclient.mysql.querystats.delected"), Minute.class);
/* 434 */       TimeSeries timeSeries2 = new TimeSeries(FormatUtil.getString("am.webclient.mysql.querystats.inserted"), Minute.class);
/* 435 */       TimeSeries timeSeries3 = new TimeSeries(FormatUtil.getString("am.webclient.mysql.querystats.selected"), Minute.class);
/* 436 */       TimeSeries timeSeries4 = new TimeSeries(FormatUtil.getString("am.webclient.mysql.querystats.updated"), Minute.class);
/* 437 */       ResultSet results = null;
/*     */       try {
/* 439 */         results = AMConnectionPool.executeQueryStmt(statsQuery);
/* 440 */         while (results.next()) {
/* 441 */           Date date = new Date(results.getLong(5));
/* 442 */           if (results.getDouble(1) >= 0.0D)
/* 443 */             timeSeries1.addOrUpdate(new Minute(date), new Double(results.getDouble(1)));
/* 444 */           if (results.getDouble(2) >= 0.0D)
/* 445 */             timeSeries2.addOrUpdate(new Minute(date), new Double(results.getDouble(2)));
/* 446 */           if (results.getDouble(3) >= 0.0D)
/* 447 */             timeSeries3.addOrUpdate(new Minute(date), new Double(results.getDouble(3)));
/* 448 */           if (results.getDouble(4) >= 0.0D) {
/* 449 */             timeSeries4.addOrUpdate(new Minute(date), new Double(results.getDouble(4)));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 456 */           if (results != null)
/* 457 */             AMConnectionPool.closeStatement(results);
/*     */         } catch (Exception exception) {
/* 459 */           exception.printStackTrace();
/*     */         }
/*     */         
/* 462 */         timeSeriesCollection = new TimeSeriesCollection();
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 452 */         exception.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         try {
/* 456 */           if (results != null)
/* 457 */             AMConnectionPool.closeStatement(results);
/*     */         } catch (Exception exception) {
/* 459 */           exception.printStackTrace();
/*     */         }
/*     */       }
/*     */       TimeSeriesCollection timeSeriesCollection;
/* 463 */       timeSeriesCollection.addSeries(timeSeries1);
/* 464 */       timeSeriesCollection.addSeries(timeSeries2);
/* 465 */       timeSeriesCollection.addSeries(timeSeries3);
/* 466 */       timeSeriesCollection.addSeries(timeSeries4);
/* 467 */       int[] x = { 0, 1, 2, 3 };
/* 468 */       return new SubSeriesDataset(timeSeriesCollection, x);
/*     */     }
/* 470 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 474 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 478 */     return "cpugraph";
/*     */   }
/*     */   
/*     */   public int getresid()
/*     */   {
/* 483 */     return this.resid;
/*     */   }
/*     */   
/*     */   public void setresid(int resource) {
/* 487 */     this.resid = resource;
/*     */   }
/*     */   
/*     */   public String gettype() {
/* 491 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/* 495 */   public void settype(String cat) { this.type = cat; }
/*     */   
/* 497 */   private String resourcename = null;
/*     */   private String maxcollectiontime;
/*     */   
/* 500 */   public void setresourcename(String name) { this.resourcename = name; }
/*     */   
/*     */   public String getresourcename()
/*     */   {
/* 504 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setcollectiontime(String collectiontime)
/*     */   {
/* 509 */     this.maxcollectiontime = collectiontime;
/*     */   }
/*     */   
/*     */   public String getcollectiontime() {
/* 513 */     return this.maxcollectiontime;
/*     */   }
/*     */   
/*     */   public double formatnumber(double d) {
/* 517 */     double value = 0.0D;
/*     */     try
/*     */     {
/* 520 */       String temp = new Double(d * 100.0D).longValue() + "";
/* 521 */       if (temp.length() < 2)
/*     */       {
/* 523 */         temp = "0" + temp;
/*     */       }
/* 525 */       temp = temp.substring(0, temp.length() - 2) + "." + temp.substring(temp.length() - 2, temp.length());
/* 526 */       value = Double.parseDouble(temp);
/*     */     }
/*     */     catch (Exception e) {
/* 529 */       e.printStackTrace();
/*     */     }
/* 531 */     return value;
/*     */   }
/*     */   
/* 534 */   public String getAttributes() { return this.attributes; }
/*     */   
/*     */   public void setAttributes(String attributes)
/*     */   {
/* 538 */     this.attributes = attributes;
/*     */   }
/*     */   
/*     */   public String getBaseid() {
/* 542 */     return this.baseid;
/*     */   }
/*     */   
/*     */   public void setBaseid(String baseid) {
/* 546 */     this.baseid = baseid;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\mysql\bean\MySqlGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */