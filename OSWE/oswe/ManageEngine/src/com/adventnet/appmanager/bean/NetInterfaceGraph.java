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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetInterfaceGraph
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
/*     */   public NetInterfaceGraph()
/*     */   {
/*  45 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException
/*     */   {
/*  50 */     long curtime = System.currentTimeMillis();
/*  51 */     long queryval = curtime - 21600000L;
/*  52 */     TimeSeriesCollection col = new TimeSeriesCollection();
/*  53 */     boolean entityexists = false;
/*  54 */     long maxtime = 0L;
/*  55 */     int i = 0;
/*  56 */     int j = 0;
/*     */     
/*     */ 
/*  59 */     if (this.category.equals("Traffic")) {
/*  60 */       TimeSeries ts = null;
/*  61 */       TimeSeries ts1 = null;
/*  62 */       int temp1 = 0;
/*  63 */       int temp2 = 0;
/*  64 */       int tempValue = 0;
/*     */       
/*     */ 
/*  67 */       String sysLoadQuery = "select COLLECTIONTIME,BYTES_RX,BYTES_TX from AM_NETWORKINTERFACE where RESID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + " order by COLLECTIONTIME";
/*     */       
/*     */       try
/*     */       {
/*  71 */         this.rs = AMConnectionPool.executeQueryStmt(sysLoadQuery);
/*  72 */         while (this.rs.next())
/*     */         {
/*  74 */           entityexists = true;
/*  75 */           long time = this.rs.getLong(1);
/*  76 */           Date d = new Date(time);
/*  77 */           if (temp1 == 0)
/*     */           {
/*     */ 
/*  80 */             if (this.rs.getString(2) != null)
/*     */             {
/*  82 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.rxtraffic");
/*  83 */               ts = new TimeSeries(this.entity, Minute.class);
/*  84 */               temp1++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*  89 */           if (temp2 == 0)
/*     */           {
/*  91 */             if (this.rs.getString(2) != null)
/*     */             {
/*  93 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.txtraffic");
/*  94 */               ts1 = new TimeSeries(this.entity, Minute.class);
/*  95 */               temp2++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 101 */           if (ts != null)
/*     */           {
/*     */             try
/*     */             {
/* 105 */               ts.add(new Minute(d), this.rs.getDouble("BYTES_RX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 112 */           if (ts1 != null)
/*     */           {
/*     */             try
/*     */             {
/* 116 */               ts1.add(new Minute(d), this.rs.getDouble("BYTES_TX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 124 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 128 */         e.printStackTrace();
/*     */       }
/* 130 */       int tempCount = 0;
/* 131 */       if (ts != null)
/*     */       {
/* 133 */         col.addSeries(ts);
/* 134 */         tempCount++;
/*     */       }
/* 136 */       if (ts1 != null)
/*     */       {
/* 138 */         col.addSeries(ts1);
/* 139 */         tempCount++;
/*     */       }
/*     */       
/*     */ 
/* 143 */       int[] x = new int[tempCount];
/* 144 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 146 */         x[c] = c;
/*     */       }
/* 148 */       if (!entityexists)
/*     */       {
/* 150 */         int[] y = new int[0];
/* 151 */         this.ssd = new SubSeriesDataset(col, y);
/*     */       }
/*     */       else {
/* 154 */         this.ssd = new SubSeriesDataset(col, x);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 159 */     else if (this.category.equals("Utilization")) {
/* 160 */       TimeSeries ts = null;
/* 161 */       TimeSeries ts1 = null;
/* 162 */       int temp1 = 0;
/* 163 */       int temp2 = 0;
/* 164 */       int tempValue = 0;
/*     */       
/*     */ 
/* 167 */       String sysLoadQuery = "select COLLECTIONTIME,RX_UTILIZATION,TX_UTILIZATION from AM_NETWORKINTERFACE where RESID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + " order by COLLECTIONTIME";
/*     */       
/*     */       try
/*     */       {
/* 171 */         this.rs = AMConnectionPool.executeQueryStmt(sysLoadQuery);
/* 172 */         while (this.rs.next())
/*     */         {
/* 174 */           entityexists = true;
/* 175 */           long time = this.rs.getLong(1);
/* 176 */           Date d = new Date(time);
/* 177 */           if (temp1 == 0)
/*     */           {
/*     */ 
/* 180 */             if (this.rs.getString(2) != null)
/*     */             {
/* 182 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.interfaceiputilization");
/* 183 */               ts = new TimeSeries(this.entity, Minute.class);
/* 184 */               temp1++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 189 */           if (temp2 == 0)
/*     */           {
/* 191 */             if (this.rs.getString(2) != null)
/*     */             {
/* 193 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.interfaceoputilization");
/* 194 */               ts1 = new TimeSeries(this.entity, Minute.class);
/* 195 */               temp2++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 201 */           if (ts != null)
/*     */           {
/*     */             try
/*     */             {
/* 205 */               ts.add(new Minute(d), this.rs.getDouble("RX_UTILIZATION"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 212 */           if (ts1 != null)
/*     */           {
/*     */             try
/*     */             {
/* 216 */               ts1.add(new Minute(d), this.rs.getDouble("TX_UTILIZATION"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 224 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 228 */         e.printStackTrace();
/*     */       }
/* 230 */       int tempCount = 0;
/* 231 */       if (ts != null)
/*     */       {
/* 233 */         col.addSeries(ts);
/* 234 */         tempCount++;
/*     */       }
/* 236 */       if (ts1 != null)
/*     */       {
/* 238 */         col.addSeries(ts1);
/* 239 */         tempCount++;
/*     */       }
/*     */       
/*     */ 
/* 243 */       int[] x = new int[tempCount];
/* 244 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 246 */         x[c] = c;
/*     */       }
/* 248 */       if (!entityexists)
/*     */       {
/* 250 */         int[] y = new int[0];
/* 251 */         this.ssd = new SubSeriesDataset(col, y);
/*     */       }
/*     */       else {
/* 254 */         this.ssd = new SubSeriesDataset(col, x);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 259 */     else if (this.category.equals("Packets")) {
/* 260 */       TimeSeries ts = null;
/* 261 */       TimeSeries ts1 = null;
/* 262 */       int temp1 = 0;
/* 263 */       int temp2 = 0;
/* 264 */       int tempValue = 0;
/*     */       
/*     */ 
/* 267 */       String sysLoadQuery = "select COLLECTIONTIME,PKTS_RX,PKTS_TX from AM_NETWORKINTERFACE where RESID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + " order by COLLECTIONTIME";
/*     */       
/*     */       try
/*     */       {
/* 271 */         this.rs = AMConnectionPool.executeQueryStmt(sysLoadQuery);
/* 272 */         while (this.rs.next())
/*     */         {
/* 274 */           entityexists = true;
/* 275 */           long time = this.rs.getLong(1);
/* 276 */           Date d = new Date(time);
/* 277 */           if (temp1 == 0)
/*     */           {
/*     */ 
/* 280 */             if (this.rs.getString(2) != null)
/*     */             {
/* 282 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.packetsrx");
/* 283 */               ts = new TimeSeries(this.entity, Minute.class);
/* 284 */               temp1++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 289 */           if (temp2 == 0)
/*     */           {
/* 291 */             if (this.rs.getString(2) != null)
/*     */             {
/* 293 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.packetstx");
/* 294 */               ts1 = new TimeSeries(this.entity, Minute.class);
/* 295 */               temp2++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 301 */           if (ts != null)
/*     */           {
/*     */             try
/*     */             {
/* 305 */               ts.add(new Minute(d), this.rs.getLong("PKTS_RX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 312 */           if (ts1 != null)
/*     */           {
/*     */             try
/*     */             {
/* 316 */               ts1.add(new Minute(d), this.rs.getLong("PKTS_TX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 324 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 328 */         e.printStackTrace();
/*     */       }
/* 330 */       int tempCount = 0;
/* 331 */       if (ts != null)
/*     */       {
/* 333 */         col.addSeries(ts);
/* 334 */         tempCount++;
/*     */       }
/* 336 */       if (ts1 != null)
/*     */       {
/* 338 */         col.addSeries(ts1);
/* 339 */         tempCount++;
/*     */       }
/*     */       
/*     */ 
/* 343 */       int[] x = new int[tempCount];
/* 344 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 346 */         x[c] = c;
/*     */       }
/* 348 */       if (!entityexists)
/*     */       {
/* 350 */         int[] y = new int[0];
/* 351 */         this.ssd = new SubSeriesDataset(col, y);
/*     */       }
/*     */       else {
/* 354 */         this.ssd = new SubSeriesDataset(col, x);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 360 */     else if (this.category.equals("Errors")) {
/* 361 */       TimeSeries ts = null;
/* 362 */       TimeSeries ts1 = null;
/* 363 */       int temp1 = 0;
/* 364 */       int temp2 = 0;
/* 365 */       int tempValue = 0;
/*     */       
/*     */ 
/* 368 */       String sysLoadQuery = "select COLLECTIONTIME,DISCARDED_RX,DISCARDED_TX,ERROR_RX,ERROR_TX from AM_NETWORKINTERFACE where RESID=" + this.resourceId + " and COLLECTIONTIME>=" + queryval + " order by COLLECTIONTIME";
/*     */       
/*     */       try
/*     */       {
/* 372 */         this.rs = AMConnectionPool.executeQueryStmt(sysLoadQuery);
/* 373 */         while (this.rs.next())
/*     */         {
/* 375 */           entityexists = true;
/* 376 */           long time = this.rs.getLong(1);
/* 377 */           Date d = new Date(time);
/* 378 */           if (temp1 == 0)
/*     */           {
/*     */ 
/* 381 */             if (this.rs.getString(2) != null)
/*     */             {
/* 383 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.errorpkts");
/* 384 */               ts = new TimeSeries(this.entity, Minute.class);
/* 385 */               temp1++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 390 */           if (temp2 == 0)
/*     */           {
/* 392 */             if (this.rs.getString(2) != null)
/*     */             {
/* 394 */               this.entity = FormatUtil.getString("am.webclient.netinterface.heading.discardedpkts");
/* 395 */               ts1 = new TimeSeries(this.entity, Minute.class);
/* 396 */               temp2++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 402 */           if (ts != null)
/*     */           {
/*     */             try
/*     */             {
/* 406 */               ts.add(new Minute(d), this.rs.getLong("ERROR_RX") + this.rs.getLong("ERROR_TX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 413 */           if (ts1 != null)
/*     */           {
/*     */             try
/*     */             {
/* 417 */               ts1.add(new Minute(d), this.rs.getLong("DISCARDED_RX") + this.rs.getLong("DISCARDED_TX"));
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 425 */         this.rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 429 */         e.printStackTrace();
/*     */       }
/* 431 */       int tempCount = 0;
/* 432 */       if (ts != null)
/*     */       {
/* 434 */         col.addSeries(ts);
/* 435 */         tempCount++;
/*     */       }
/* 437 */       if (ts1 != null)
/*     */       {
/* 439 */         col.addSeries(ts1);
/* 440 */         tempCount++;
/*     */       }
/*     */       
/*     */ 
/* 444 */       int[] x = new int[tempCount];
/* 445 */       for (int c = 0; c < tempCount; c++)
/*     */       {
/* 447 */         x[c] = c;
/*     */       }
/* 449 */       if (!entityexists)
/*     */       {
/* 451 */         int[] y = new int[0];
/* 452 */         this.ssd = new SubSeriesDataset(col, y);
/*     */       }
/*     */       else {
/* 455 */         this.ssd = new SubSeriesDataset(col, x);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 461 */     return this.ssd;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasExpired(Map params, Date since)
/*     */   {
/* 467 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 471 */     return "sysloadgraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 476 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 480 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resourecid)
/*     */   {
/* 485 */     this.resourceId = resourecid;
/*     */   }
/*     */   
/*     */   public String getResourceId()
/*     */   {
/* 490 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 494 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 498 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 502 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 506 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public void setWritePerSec(Object ssdWr)
/*     */   {
/* 511 */     this.writeSubSeries = ssdWr;
/*     */   }
/*     */   
/*     */   public void setReadPerSec(Object ssdRd)
/*     */   {
/* 516 */     this.readSubSeries = ssdRd;
/*     */   }
/*     */   
/*     */   public Object getWritePerSec()
/*     */   {
/* 521 */     return this.writeSubSeries;
/*     */   }
/*     */   
/*     */   public Object getReadPerSec()
/*     */   {
/* 526 */     return this.readSubSeries;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\NetInterfaceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */