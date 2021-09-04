/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
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
/*     */ public class SysloadGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  27 */   private String resourcename = "default";
/*  28 */   private String category = "default";
/*  29 */   private String entity = "default";
/*  30 */   private String resourceId = "default";
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet rs1;
/*     */   private ResultSet rso;
/*     */   private ResultSet rso1;
/*  36 */   private Object writeSubSeries = null;
/*  37 */   private Object readSubSeries = null;
/*     */   private ResultSet rsi;
/*     */   private ResultSet rsi1;
/*     */   private ResultSet rsm;
/*     */   SubSeriesDataset ssd;
/*     */   
/*     */   public SysloadGraph()
/*     */   {
/*  45 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  50 */     long curtime = System.currentTimeMillis();
/*  51 */     long queryval = curtime - 3600000L;
/*  52 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*  53 */     boolean entityexists = false;
/*  54 */     long maxtime = 0L;
/*  55 */     int i = 0;
/*  56 */     int j = 0;
/*     */     
/*  58 */     if (this.category.equals("System Load"))
/*     */     {
/*  60 */       TimeSeries ts = null;
/*  61 */       TimeSeries ts1 = null;
/*  62 */       TimeSeries ts2 = null;
/*  63 */       int temp1 = 0;
/*  64 */       int temp2 = 0;
/*  65 */       int temp3 = 0;
/*  66 */       int tempValue = 0;
/*  67 */       String sysLoadQuery = "select JOBSPERMIN,JOBSPER5MIN,JOBSPER15MIN,COLLECTIONTIME from HostSysLoadDataCollected where RESOURCEID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + " order by COLLECTIONTIME";
/*     */       try
/*     */       {
/*  70 */         this.rs = AMConnectionPool.executeQueryStmt(sysLoadQuery);
/*  71 */         while (this.rs.next())
/*     */         {
/*  73 */           entityexists = true;
/*  74 */           long time = this.rs.getLong(4);
/*  75 */           Date d = new Date(time);
/*  76 */           if (temp1 == 0)
/*     */           {
/*  78 */             if (this.rs.getString(1) != null)
/*     */             {
/*  80 */               this.entity = FormatUtil.getString("Jobs in Minute");
/*  81 */               ts = new TimeSeries(this.entity, Minute.class);
/*  82 */               temp1++;
/*     */             }
/*     */           }
/*  85 */           if (temp2 == 0)
/*     */           {
/*  87 */             if (this.rs.getString(2) != null)
/*     */             {
/*  89 */               this.entity = FormatUtil.getString("Jobs in 5 Minutes");
/*  90 */               ts1 = new TimeSeries(this.entity, Minute.class);
/*  91 */               temp2++;
/*     */             }
/*     */           }
/*  94 */           if (temp3 == 0)
/*     */           {
/*  96 */             if (this.rs.getString(3) != null)
/*     */             {
/*  98 */               this.entity = FormatUtil.getString("Jobs in 15 Minutes");
/*  99 */               ts2 = new TimeSeries(this.entity, Minute.class);
/* 100 */               temp3++;
/*     */             }
/*     */           }
/* 103 */           if (ts != null)
/*     */           {
/*     */             try
/*     */             {
/* 107 */               ts.add(new Minute(d), this.rs.getLong(1));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/* 113 */           if (ts1 != null)
/*     */           {
/*     */             try
/*     */             {
/* 117 */               ts1.add(new Minute(d), this.rs.getLong(2));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/* 123 */           if (ts2 != null)
/*     */           {
/*     */             try
/*     */             {
/* 127 */               ts2.add(new Minute(d), this.rs.getLong(3));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 134 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 138 */         e.printStackTrace();
/*     */       }
/* 140 */       int tempCount = 0;
/* 141 */       if (ts != null)
/*     */       {
/* 143 */         col.addSeries(ts);
/* 144 */         tempCount++;
/*     */       }
/* 146 */       if (ts1 != null)
/*     */       {
/* 148 */         col.addSeries(ts1);
/* 149 */         tempCount++;
/*     */       }
/* 151 */       if (ts2 != null)
/*     */       {
/* 153 */         col.addSeries(ts2);
/* 154 */         tempCount++;
/*     */       }
/*     */       
/* 157 */       int[] x = new int[tempCount];
/* 158 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 160 */         x[c] = c;
/*     */       }
/* 162 */       if (!entityexists)
/*     */       {
/* 164 */         int[] y = new int[0];
/* 165 */         this.ssd = new SubSeriesDataset(col, y);
/*     */       }
/*     */       else {
/* 168 */         this.ssd = new SubSeriesDataset(col, x);
/*     */       }
/*     */     }
/* 171 */     else if ((this.category.equals("Disk IO Stats")) && (this.entity.equals("WritesPerSecond")))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 176 */         this.ssd = ((SubSeriesDataset)getWritePerSec());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 180 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */     }
/* 184 */     else if ((this.category.equals("Disk IO Stats")) && (this.entity.equals("ReadsPerSecond")))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 189 */         this.ssd = ((SubSeriesDataset)getReadPerSec());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 193 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 196 */     return this.ssd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setProduceDatasetForDiskIo()
/*     */   {
/*     */     try
/*     */     {
/* 205 */       long currentTime = System.currentTimeMillis();
/* 206 */       long queryValue = currentTime - 3600000L;
/* 207 */       boolean existsEntity = false;
/* 208 */       TimeSeries ts = null;
/* 209 */       TimeSeries ts1 = null;
/* 210 */       TimeSeriesCollection colW = new TimeSeriesCollection();
/* 211 */       TimeSeriesCollection colR = new TimeSeriesCollection();
/* 212 */       String previousEntity = "";
/* 213 */       String newEntity = "";
/* 214 */       int tempW = 0;
/* 215 */       int tempR = 0;
/* 216 */       ResultSet rw = null;
/* 217 */       String instquery = "select RESOURCENAME, READSPERSEC, WRITESPERSEC, COLLECTIONTIME from HostDiskIODataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskIODataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME>=" + queryValue + " order by RESOURCENAME,COLLECTIONTIME";
/* 218 */       rw = AMConnectionPool.executeQueryStmt(instquery);
/* 219 */       while (rw.next())
/*     */       {
/* 221 */         existsEntity = true;
/* 222 */         long time = rw.getLong(4);
/* 223 */         long readsPerSecValue = rw.getLong(2);
/* 224 */         long writesPerSecValue = rw.getLong(3);
/* 225 */         if ((readsPerSecValue != -1L) && (writesPerSecValue != -1L)) {
/* 226 */           String entity = rw.getString(1);
/* 227 */           Date d = new Date(time);
/* 228 */           newEntity = entity;
/* 229 */           String entity1 = entity.substring(entity.indexOf("Disk IO Stats") + 14, entity.length());
/* 230 */           if (previousEntity.equals(""))
/*     */           {
/* 232 */             ts = new TimeSeries(entity1, Minute.class);
/* 233 */             ts1 = new TimeSeries(entity1, Minute.class);
/* 234 */             ts.addOrUpdate(new Minute(d), readsPerSecValue);
/* 235 */             ts1.addOrUpdate(new Minute(d), writesPerSecValue);
/* 236 */             previousEntity = newEntity;
/*     */           }
/* 238 */           else if (previousEntity.equals(newEntity))
/*     */           {
/* 240 */             ts.addOrUpdate(new Minute(d), readsPerSecValue);
/* 241 */             ts1.addOrUpdate(new Minute(d), writesPerSecValue);
/* 242 */             previousEntity = newEntity;
/*     */           }
/* 244 */           else if (!previousEntity.equals(newEntity))
/*     */           {
/* 246 */             colW.addSeries(ts1);
/* 247 */             tempW++;
/* 248 */             colR.addSeries(ts);
/* 249 */             tempR++;
/* 250 */             ts = null;
/* 251 */             ts1 = null;
/* 252 */             ts = new TimeSeries(entity1, Minute.class);
/* 253 */             ts1 = new TimeSeries(entity1, Minute.class);
/* 254 */             ts.addOrUpdate(new Minute(d), readsPerSecValue);
/* 255 */             ts1.addOrUpdate(new Minute(d), writesPerSecValue);
/* 256 */             previousEntity = newEntity;
/*     */           }
/*     */         }
/*     */       }
/* 260 */       rw.close();
/* 261 */       if (ts1 != null)
/*     */       {
/* 263 */         colW.addSeries(ts1);
/* 264 */         tempW++;
/*     */       }
/* 266 */       if (ts != null)
/*     */       {
/* 268 */         colR.addSeries(ts);
/* 269 */         tempR++;
/*     */       }
/*     */       
/*     */ 
/* 273 */       int[] w = new int[tempW];
/* 274 */       int[] r = new int[tempR];
/* 275 */       for (int j = 0; j < tempW; j++)
/*     */       {
/* 277 */         w[j] = j;
/*     */       }
/* 279 */       for (int k = 0; k < tempR; k++)
/*     */       {
/* 281 */         r[k] = k;
/*     */       }
/* 283 */       SubSeriesDataset ssdW = null;
/* 284 */       SubSeriesDataset ssdR = null;
/* 285 */       if (!existsEntity)
/*     */       {
/* 287 */         int[] y = new int[0];
/* 288 */         ssdW = new SubSeriesDataset(colW, y);
/* 289 */         ssdR = new SubSeriesDataset(colR, y);
/* 290 */         setWritePerSec(ssdW);
/* 291 */         setReadPerSec(ssdR);
/*     */       }
/*     */       else {
/* 294 */         ssdW = new SubSeriesDataset(colW, w);
/* 295 */         ssdR = new SubSeriesDataset(colR, r);
/* 296 */         setWritePerSec(ssdW);
/* 297 */         setReadPerSec(ssdR);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 302 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 307 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 311 */     return "sysloadgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 316 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 320 */     this.resourcename = resource;
/* 321 */     System.out.println("resourceName:" + this.resourcename);
/*     */   }
/*     */   
/*     */   public void setResourceId(String resourecid)
/*     */   {
/* 326 */     this.resourceId = resourecid;
/*     */   }
/*     */   
/*     */   public String getResourceId()
/*     */   {
/* 331 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 335 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 339 */     this.category = cat;
/* 340 */     System.out.println("category:" + this.category);
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 344 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 348 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public void setWritePerSec(Object ssdWr)
/*     */   {
/* 353 */     this.writeSubSeries = ssdWr;
/*     */   }
/*     */   
/*     */   public void setReadPerSec(Object ssdRd)
/*     */   {
/* 358 */     this.readSubSeries = ssdRd;
/*     */   }
/*     */   
/*     */   public Object getWritePerSec()
/*     */   {
/* 363 */     return this.writeSubSeries;
/*     */   }
/*     */   
/*     */   public Object getReadPerSec()
/*     */   {
/* 368 */     return this.readSubSeries;
/*     */   }
/*     */   
/*     */   public Hashtable getSysloadData(String resourcename, long time, String resourceid)
/*     */   {
/* 373 */     String sysloadquery = "select JOBSPERMIN,JOBSPER5MIN,JOBSPER15MIN from HostSysLoadDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + time;
/* 374 */     System.out.println("The resource id is:" + resourceid);
/* 375 */     Hashtable h = new Hashtable();
/*     */     try {
/* 377 */       this.rs = AMConnectionPool.executeQueryStmt(sysloadquery);
/* 378 */       while (this.rs.next()) {
/* 379 */         Properties p = null;
/* 380 */         if (this.rs.getString(1) != null)
/*     */         {
/* 382 */           p = new Properties();
/* 383 */           p.setProperty("name", "jobsPerMin");
/* 384 */           p.setProperty("CURVALUE", this.rs.getString(1));
/* 385 */           h.put("jobsPerMin", p);
/*     */         }
/* 387 */         if (this.rs.getString(2) != null)
/*     */         {
/* 389 */           p = new Properties();
/* 390 */           p.setProperty("name", "jobsPer5Min");
/* 391 */           p.setProperty("CURVALUE", this.rs.getString(2));
/* 392 */           h.put("jobsPer5Min", p);
/*     */         }
/* 394 */         if (this.rs.getString(3) != null)
/*     */         {
/* 396 */           p = new Properties();
/* 397 */           p.setProperty("name", "jobsPer15Min");
/* 398 */           p.setProperty("CURVALUE", this.rs.getString(3));
/* 399 */           h.put("jobsPer15Min", p);
/*     */         }
/*     */       }
/* 402 */       this.rs.close();
/* 403 */       String maxvaluequery = "select max(JOBSPERMIN),max(JOBSPER5MIN),max(JOBSPER15MIN) from HostSysLoadDataCollected where RESOURCEID=" + resourceid + " and COLLECTIONTIME <=" + time + " and COLLECTIONTIME >= " + (time - 3600000L);
/* 404 */       this.rs = AMConnectionPool.executeQueryStmt(maxvaluequery);
/* 405 */       while (this.rs.next()) {
/* 406 */         Properties p = null;
/* 407 */         if (h.get("jobsPerMin") != null)
/*     */         {
/* 409 */           p = (Properties)h.get("jobsPerMin");
/* 410 */           p.setProperty("MAXVALUE", String.valueOf(this.rs.getLong(1)));
/* 411 */           h.put("jobsPerMin", p);
/*     */         }
/* 413 */         if (h.get("jobsPer5Min") != null)
/*     */         {
/* 415 */           p = (Properties)h.get("jobsPer5Min");
/* 416 */           p.setProperty("MAXVALUE", String.valueOf(this.rs.getLong(2)));
/* 417 */           h.put("jobsPer5Min", p);
/*     */         }
/* 419 */         if (h.get("jobsPer15Min") != null)
/*     */         {
/* 421 */           p = (Properties)h.get("jobsPer15Min");
/* 422 */           p.setProperty("MAXVALUE", String.valueOf(this.rs.getLong(3)));
/* 423 */           h.put("jobsPer15Min", p);
/*     */         }
/*     */       }
/* 426 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 430 */       AMLog.fatal("HostResource : Error in Fetching the data for SysLoad ", e);
/*     */     }
/*     */     
/* 433 */     return h;
/*     */   }
/*     */   
/*     */ 
/*     */   public Hashtable getDiskIoData(String resourcename, long time, String category, String resourceId)
/*     */   {
/* 439 */     String diskquery = "select RESOURCENAME, HostDiskIODataCollected.RESOURCEID, READSPERSEC, WRITESPERSEC,TRANSFERSPERSECOND,PERCENTDISKTIME,AVGQUEUELENGTH from HostDiskIODataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskIODataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME=" + time;
/* 440 */     Hashtable toReturn = new Hashtable();
/*     */     try {
/* 442 */       this.rs = AMConnectionPool.executeQueryStmt(diskquery);
/* 443 */       while (this.rs.next())
/*     */       {
/* 445 */         String ammoResName = this.rs.getString(1);
/* 446 */         Properties tempProp = new Properties();
/* 447 */         if ((this.rs.getString(3) != null) && (!this.rs.getString(3).equals("-1")))
/*     */         {
/* 449 */           tempProp.setProperty("READ", this.rs.getString(3));
/*     */         }
/* 451 */         if ((this.rs.getString(4) != null) && (!this.rs.getString(4).equals("-1")))
/*     */         {
/* 453 */           tempProp.setProperty("WRITE", this.rs.getString(4));
/*     */         }
/* 455 */         if ((this.rs.getString(5) != null) && (!this.rs.getString(5).equals("-1")))
/*     */         {
/* 457 */           tempProp.setProperty("TRANSFER", this.rs.getString(5));
/*     */         }
/* 459 */         if ((this.rs.getString(6) != null) && (!this.rs.getString(6).equals("-1")))
/*     */         {
/* 461 */           tempProp.setProperty("DISKTIME", this.rs.getString(6));
/*     */         }
/* 463 */         if ((this.rs.getString(7) != null) && (!this.rs.getString(7).equals("-1")))
/*     */         {
/* 465 */           tempProp.setProperty("QUELENGTH", this.rs.getString(7));
/*     */         }
/* 467 */         if ((!this.rs.getString(3).equals("-1")) || (!this.rs.getString(4).equals("-1")) || (!this.rs.getString(5).equals("-1")) || (!this.rs.getString(6).equals("-1")) || (!this.rs.getString(7).equals("-1"))) {
/* 468 */           tempProp.setProperty("ID", this.rs.getString(2));
/* 469 */           toReturn.put(ammoResName, tempProp);
/*     */         }
/*     */       }
/* 472 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 475 */       e.printStackTrace();
/* 476 */       AMLog.fatal("HostResource : Error in Fetching the data for Disk ", e);
/*     */     }
/* 478 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */   public Hashtable getPageSpaceData(String resourcename, long time, String resourceid)
/*     */   {
/* 484 */     String pgspacequery = "select RESOURCENAME,AM_HOSTPGSPACEDATACOLLECTED.RESOURCEID,SIZEINMB,USEDINPER,USEDINMB,FREEINPER,FREEINMB from AM_ManagedObject,AM_HOSTPGSPACEDATACOLLECTED,AM_PARENTCHILDMAPPER where AM_HOSTPGSPACEDATACOLLECTED.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_HOSTPGSPACEDATACOLLECTED.COLLECTIONTIME=" + time;
/* 485 */     Hashtable pageSpace = new Hashtable();
/*     */     try {
/* 487 */       this.rs = AMConnectionPool.executeQueryStmt(pgspacequery);
/* 488 */       while (this.rs.next())
/*     */       {
/* 490 */         String pgSpaceName = this.rs.getString(1);
/* 491 */         Properties tempProp = new Properties();
/* 492 */         if ((this.rs.getString(3) != null) && (!this.rs.getString(3).equals("-1")))
/*     */         {
/* 494 */           tempProp.setProperty("SIZE", this.rs.getString(3));
/*     */         }
/* 496 */         if ((this.rs.getString(4) != null) && (!this.rs.getString(4).equals("-1")))
/*     */         {
/* 498 */           tempProp.setProperty("USED", this.rs.getString(4));
/*     */         }
/* 500 */         if ((this.rs.getString(5) != null) && (!this.rs.getString(5).equals("-1")))
/*     */         {
/* 502 */           tempProp.setProperty("USEDMB", this.rs.getString(5));
/*     */         }
/* 504 */         if ((this.rs.getString(6) != null) && (!this.rs.getString(6).equals("-1")))
/*     */         {
/* 506 */           tempProp.setProperty("FREE", this.rs.getString(6));
/*     */         }
/* 508 */         if ((this.rs.getString(7) != null) && (!this.rs.getString(7).equals("-1")))
/*     */         {
/* 510 */           tempProp.setProperty("FREEMB", this.rs.getString(7));
/*     */         }
/* 512 */         if ((!this.rs.getString(3).equals("-1")) || (!this.rs.getString(4).equals("-1")) || (!this.rs.getString(5).equals("-1")) || (!this.rs.getString(6).equals("-1")) || (!this.rs.getString(7).equals("-1"))) {
/* 513 */           tempProp.setProperty("ID", this.rs.getString(2));
/* 514 */           pageSpace.put(pgSpaceName, tempProp);
/*     */         }
/*     */       }
/* 517 */       this.rs.close();
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 522 */       AMLog.fatal("HostResource : Error in Fetching the data for PageSpace ", e);
/*     */     }
/*     */     
/* 525 */     return pageSpace;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\SysloadGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */