/*     */ package com.adventnet.appmanager.server.mssql.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
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
/*     */ public class MsSqlGraphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  26 */   private int resid = -1;
/*  27 */   private String type = "default";
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  32 */     long curtime = System.currentTimeMillis();
/*  33 */     long queryval = curtime - 3600000L;
/*  34 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  35 */     ResultSet rs = null;
/*  36 */     if (this.type.equals("CACHE")) {
/*  37 */       String query = "select COLLECTIONTIME, CACHEHITRATIO , CACHEUSEDPERMIN from AM_MSSQLCACHEDETAILS where  RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/*  39 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*  43 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Cache Hit Ratio"), Minute.class);
/*     */       
/*  45 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/*  47 */         while (rs.next())
/*     */         {
/*  49 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  52 */             ts.add(new Minute(d), rs.getDouble("CACHEHITRATIO"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*  59 */         rs.close();
/*  60 */         col.addSeries(ts);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*  66 */       int[] x = { 0 };
/*  67 */       return new SubSeriesDataset(col, x);
/*     */     }
/*  69 */     if (this.type.equals("BUFFER")) {
/*  70 */       String query = "select COLLECTIONTIME , BUFFERHITRATIO , PAGELOOKUPSPERMIN , PAGEREADSPERMIN , PAGEWRITESPERMIN  from AM_MSSQLBUFFERDETAILS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/*  72 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*  76 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*  77 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Buffer Hit Ratio"), Minute.class);
/*     */       
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/*  83 */         while (rs.next())
/*     */         {
/*  85 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  88 */             ts.add(new Minute(d), rs.getDouble("BUFFERHITRATIO"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */         col.addSeries(ts);
/*     */         
/*     */ 
/*     */ 
/* 103 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 107 */         col.addSeries(ts);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 112 */       int[] x = { 0 };
/* 113 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/* 116 */     if (this.type.equals("MEMORY"))
/*     */     {
/* 118 */       String query = "select COLLECTIONTIME , TOTALMEMORY , SQLCACHEMEMORY , OPTIMIZERMEMORY , LOCKMEMORY , CONNECTIONMEMORY from AM_MSSQLMEMORYDETAILS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 120 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 123 */         e.printStackTrace();
/*     */       }
/* 125 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 126 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Total Server Memory"), Minute.class);
/* 127 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("SQL Cache Memory"), Minute.class);
/* 128 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Optimizer Memory"), Minute.class);
/* 129 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("Lock Memory"), Minute.class);
/* 130 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("Connection Memory"), Minute.class);
/*     */       try
/*     */       {
/* 133 */         while (rs.next())
/*     */         {
/* 135 */           long time = rs.getLong(1);
/* 136 */           Date d = new Date(time);
/*     */           try
/*     */           {
/* 139 */             ts.add(new Minute(d), rs.getDouble("TOTALMEMORY"));
/* 140 */             ts1.add(new Minute(d), rs.getDouble("SQLCACHEMEMORY"));
/* 141 */             ts2.add(new Minute(d), rs.getDouble("OPTIMIZERMEMORY"));
/* 142 */             ts3.add(new Minute(d), rs.getDouble("LOCKMEMORY"));
/* 143 */             ts4.add(new Minute(d), rs.getDouble("CONNECTIONMEMORY"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 151 */         col.addSeries(ts);
/* 152 */         col.addSeries(ts1);
/* 153 */         col.addSeries(ts2);
/* 154 */         col.addSeries(ts3);
/* 155 */         col.addSeries(ts4);
/* 156 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 160 */         col.addSeries(ts);
/* 161 */         col.addSeries(ts1);
/* 162 */         col.addSeries(ts2);
/* 163 */         col.addSeries(ts3);
/* 164 */         col.addSeries(ts4);
/* 165 */         e.printStackTrace();
/*     */       }
/* 167 */       int[] x = { 0, 1, 2, 3, 4 };
/* 168 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/* 171 */     if (this.type.equals("CONNECTION")) {
/* 172 */       String query = "select AM_ManagedObjectData.COLLECTIONTIME , CONNECTIONS , RESPONSETIME  from AM_MSSQLGENERALDETAILS left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=AM_MSSQLGENERALDETAILS.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=AM_MSSQLGENERALDETAILS.COLLECTIONTIME where AM_MSSQLGENERALDETAILS.RESOURCEID=" + this.resid + " and AM_MSSQLGENERALDETAILS.COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 174 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 177 */         e.printStackTrace();
/*     */       }
/* 179 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       
/* 181 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Connection Time"), Minute.class);
/*     */       try
/*     */       {
/* 184 */         while (rs.next())
/*     */         {
/*     */ 
/* 187 */           long time = rs.getLong(1);
/* 188 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           
/*     */           try
/*     */           {
/* 192 */             ts1.add(new Minute(d), rs.getDouble("RESPONSETIME"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */         col.addSeries(ts1);
/* 202 */         rs.close();
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 207 */         col.addSeries(ts1);
/* 208 */         e.printStackTrace();
/*     */       }
/* 210 */       int[] x = { 0 };
/* 211 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 213 */     if (this.type.equals("DBSIZE"))
/*     */     {
/* 215 */       Vector v = new Vector();
/* 216 */       String dbquery = "select AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME like '%" + getresourcename() + "%' and AM_ManagedObject.TYPE = 'DataBase'";
/*     */       try
/*     */       {
/* 219 */         ResultSet db = AMConnectionPool.executeQueryStmt(dbquery);
/* 220 */         while (db.next())
/*     */         {
/*     */ 
/* 223 */           v.add(db.getString(1));
/*     */         }
/* 225 */         db.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 229 */         e.printStackTrace();
/*     */       }
/* 231 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 232 */       if (v.size() == 0)
/*     */       {
/* 234 */         int[] x = new int[0];
/* 235 */         return new SubSeriesDataset(col, x);
/*     */       }
/* 237 */       for (int i = 0; i < v.size(); i++)
/*     */       {
/* 239 */         String query = "select AM_MYSQLDBTUNING.DBSIZE , AM_MYSQLDBTUNING.COLLECTIONTIME from AM_ManagedObject, AM_MYSQLDBTUNING where AM_ManagedObject.RESOURCEID = AM_MYSQLDBTUNING.RESOURCEID and AM_ManagedObject.RESOURCENAME='" + (String)v.get(i) + "-" + getresourcename() + "' and AM_ManagedObject.TYPE = 'DataBase' and COLLECTIONTIME >" + queryval + "";
/*     */         try {
/* 241 */           rs = AMConnectionPool.executeQueryStmt(query);
/*     */         }
/*     */         catch (Exception e) {
/* 244 */           e.printStackTrace();
/*     */         }
/*     */         try
/*     */         {
/* 248 */           TimeSeries ts = new TimeSeries((String)v.get(i), Minute.class);
/* 249 */           while (rs.next())
/*     */           {
/*     */ 
/* 252 */             long size = rs.getLong(1);
/* 253 */             long time = rs.getLong(2);
/* 254 */             Date d = new Date(time);
/*     */             try
/*     */             {
/* 257 */               ts.add(new Minute(d), size);
/*     */             }
/*     */             catch (Exception e) {}
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 265 */           col.addSeries(ts);
/* 266 */           rs.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 270 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 273 */       int[] x = new int[v.size()];
/* 274 */       for (int j = 0; j < v.size(); j++) {
/* 275 */         x[j] = j;
/*     */       }
/* 277 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 279 */     if (this.type.equals("LOCKS"))
/*     */     {
/* 281 */       String query = "select COLLECTIONTIME ,LOCKREQUESTSPERMIN , LOCKWAITSPERMIN , LOCKTIMEOUTSPERMIN , DEADLOCKSPERMIN from AM_MSSQLLOCKDETAILS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 283 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 286 */         e.printStackTrace();
/*     */       }
/* 288 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       
/* 290 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Lock Waits/Min"), Minute.class);
/* 291 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Lock Timeouts/Min"), Minute.class);
/* 292 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("Deadlocks/Min"), Minute.class);
/*     */       try
/*     */       {
/* 295 */         while (rs.next())
/*     */         {
/*     */ 
/* 298 */           Date d = new Date(rs.getLong(1));
/*     */           
/*     */           try
/*     */           {
/* 302 */             ts1.add(new Minute(d), rs.getDouble("LOCKWAITSPERMIN"));
/* 303 */             ts2.add(new Minute(d), rs.getDouble("LOCKTIMEOUTSPERMIN"));
/* 304 */             ts3.add(new Minute(d), rs.getDouble("DEADLOCKSPERMIN"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 313 */         col.addSeries(ts1);
/* 314 */         col.addSeries(ts2);
/* 315 */         col.addSeries(ts3);
/* 316 */         rs.close();
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 321 */         col.addSeries(ts1);
/* 322 */         col.addSeries(ts2);
/* 323 */         col.addSeries(ts3);
/* 324 */         e.printStackTrace();
/*     */       }
/* 326 */       int[] x = { 0, 1, 2 };
/* 327 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 329 */     if (this.type.equals("SQL"))
/*     */     {
/* 331 */       String query = "select COLLECTIONTIME , BATCHREQUESTSPERMIN , SQLCOMPILATIONSPERMIN , SQLRECOMPILATIONSPERMIN from AM_MSSQLSQLDETAILS where RESOURCEID=" + this.resid + " and COLLECTIONTIME >" + queryval + "";
/*     */       try {
/* 333 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {
/* 336 */         e.printStackTrace();
/*     */       }
/* 338 */       TimeSeriesCollection col = new TimeSeriesCollection();
/* 339 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Batch Requests/Min"), Minute.class);
/* 340 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("SQL Compilations/Min"), Minute.class);
/* 341 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("SQL Recompilations/Min"), Minute.class);
/*     */       try
/*     */       {
/* 344 */         while (rs.next())
/*     */         {
/*     */ 
/* 347 */           Date d = new Date(rs.getLong(1));
/*     */           try
/*     */           {
/* 350 */             ts.add(new Minute(d), rs.getDouble("BATCHREQUESTSPERMIN"));
/* 351 */             ts1.add(new Minute(d), rs.getDouble("SQLCOMPILATIONSPERMIN"));
/* 352 */             ts2.add(new Minute(d), rs.getDouble("SQLRECOMPILATIONSPERMIN"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 360 */         col.addSeries(ts);
/* 361 */         col.addSeries(ts1);
/* 362 */         col.addSeries(ts2);
/* 363 */         rs.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 367 */         col.addSeries(ts);
/* 368 */         col.addSeries(ts1);
/* 369 */         col.addSeries(ts2);
/* 370 */         e.printStackTrace();
/*     */       }
/* 372 */       int[] x = { 0, 1, 2 };
/* 373 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 375 */     if (this.type.equals("LATCH")) {
/* 376 */       String query = "select COLLECTIONTIME, LATCHWAITSPERMIN , AVGLATCHWAITTIME from AM_MSSQLLATCHES where  RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 378 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 383 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Average Latch Wait Time"), Minute.class);
/* 384 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 386 */         while (rs.next())
/*     */         {
/* 388 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           
/*     */           try
/*     */           {
/* 392 */             ts1.add(new Minute(d), rs.getDouble("AVGLATCHWAITTIME"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 398 */         rs.close();
/*     */         
/* 400 */         col.addSeries(ts1);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 405 */       int[] x = { 0 };
/* 406 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 408 */     if (this.type.equals("ACCESS")) {
/* 409 */       String query = "select COLLECTIONTIME, FULLSCANSPERMIN , RANGESCANSPERMIN , PROBESCANSPERMIN from AM_MSSQLACCESSMETHODDETAILS where  RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 411 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 415 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Full Scans/Min"), Minute.class);
/* 416 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Range Scans/Min"), Minute.class);
/* 417 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Probe Scans/Min"), Minute.class);
/* 418 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 420 */         while (rs.next())
/*     */         {
/* 422 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 425 */             ts.add(new Minute(d), rs.getDouble("FULLSCANSPERMIN"));
/* 426 */             ts1.add(new Minute(d), rs.getDouble("RANGESCANSPERMIN"));
/* 427 */             ts2.add(new Minute(d), rs.getDouble("PROBESCANSPERMIN"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 433 */         rs.close();
/* 434 */         col.addSeries(ts);
/* 435 */         col.addSeries(ts1);
/* 436 */         col.addSeries(ts2);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 441 */       int[] x = { 0, 1, 2 };
/* 442 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 444 */     if (this.type.equals("TRANS")) {
/* 445 */       String query = "select COLLECTIONTIME, TRANSACTIONSPERMIN , ACTIVETRANSACTIONS , REPLICATIONTRANSACTIONPERMIN from  AM_MSSQL_DATABASEDETAILS where RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 447 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 451 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Transactions/Min"), Minute.class);
/*     */       
/* 453 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Replication Transactions/Min"), Minute.class);
/* 454 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 456 */         while (rs.next())
/*     */         {
/* 458 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 461 */             ts.add(new Minute(d), rs.getDouble("TRANSACTIONSPERMIN"));
/*     */             
/* 463 */             ts2.add(new Minute(d), rs.getDouble("REPLICATIONTRANSACTIONPERMIN"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 469 */         rs.close();
/* 470 */         col.addSeries(ts);
/*     */         
/* 472 */         col.addSeries(ts2);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 477 */       int[] x = { 0, 1 };
/* 478 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 480 */     if (this.type.equals("LOGFLUSH")) {
/* 481 */       String query = "select COLLECTIONTIME, LOGFULSHESPERMIN , LOGFLUSHWAITSPERMIN , LOGFLUSHWAITTIME  from  AM_MSSQL_DATABASEDETAILS where RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 483 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 487 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Log Flush/Min"), Minute.class);
/* 488 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Log Flush Wait/Min"), Minute.class);
/*     */       
/* 490 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 492 */         while (rs.next())
/*     */         {
/* 494 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 497 */             ts.add(new Minute(d), rs.getDouble("LOGFULSHESPERMIN"));
/* 498 */             ts1.add(new Minute(d), rs.getDouble("LOGFLUSHWAITSPERMIN"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 505 */         rs.close();
/* 506 */         col.addSeries(ts);
/* 507 */         col.addSeries(ts1);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/* 513 */       int[] x = { 0, 1 };
/* 514 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 516 */     if (this.type.equals("DATAFILE")) {
/* 517 */       String query = "select COLLECTIONTIME, DATAFILESSIZE  from  AM_MSSQL_DATABASEDETAILS where RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 519 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 523 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("DataFiles size in MB"), Minute.class);
/* 524 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 526 */         while (rs.next())
/*     */         {
/* 528 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 531 */             ts.add(new Minute(d), rs.getDouble("DATAFILESSIZE") / 1024.0D);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 537 */         rs.close();
/* 538 */         col.addSeries(ts);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 543 */       int[] x = { 0 };
/* 544 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 546 */     if (this.type.equals("LOGCACHE")) {
/* 547 */       String query = "select COLLECTIONTIME, LOGCACHEHITRATIO from  AM_MSSQL_DATABASEDETAILS where RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 549 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 553 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Log Cache Hit Ratio in %"), Minute.class);
/* 554 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 556 */         while (rs.next())
/*     */         {
/* 558 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 561 */             ts.add(new Minute(d), rs.getDouble("LOGCACHEHITRATIO"));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 567 */         rs.close();
/* 568 */         col.addSeries(ts);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 573 */       int[] x = { 0 };
/* 574 */       return new SubSeriesDataset(col, x);
/*     */     }
/* 576 */     if (this.type.equals("LOGSIZE")) {
/* 577 */       String query = "select COLLECTIONTIME, LOGFILESSIZE , LOGFILEUSEDSIZE from  AM_MSSQL_DATABASEDETAILS where RESOURCEID=" + getresid() + " and COLLECTIONTIME >=" + queryval + "";
/*     */       try {
/* 579 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 583 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Log File Size in MB"), Minute.class);
/* 584 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Log File Used Size in MB"), Minute.class);
/* 585 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*     */       try {
/* 587 */         while (rs.next())
/*     */         {
/* 589 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 592 */             ts.add(new Minute(d), rs.getDouble("LOGFILESSIZE") / 1024.0D);
/* 593 */             ts1.add(new Minute(d), rs.getDouble("LOGFILEUSEDSIZE") / 1024.0D);
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */         
/*     */ 
/* 599 */         rs.close();
/* 600 */         col.addSeries(ts);
/* 601 */         col.addSeries(ts1);
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/* 606 */       int[] x = { 0, 1 };
/* 607 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 612 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 616 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 620 */     return "cpugraph";
/*     */   }
/*     */   
/*     */   public int getresid()
/*     */   {
/* 625 */     return this.resid;
/*     */   }
/*     */   
/*     */   public void setresid(int resource) {
/* 629 */     this.resid = resource;
/*     */   }
/*     */   
/*     */   public String gettype() {
/* 633 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/* 637 */   public void settype(String cat) { this.type = cat; }
/*     */   
/* 639 */   private String resourcename = null;
/*     */   
/*     */   public void setresourcename(String name) {
/* 642 */     this.resourcename = name;
/*     */   }
/*     */   
/*     */   public String getresourcename() {
/* 646 */     return this.resourcename;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\mssql\bean\MsSqlGraphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */