/*     */ package com.adventnet.appmanager.server.db2.bean;
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
/*     */ public class DB2Graphs
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  20 */   private int resid = -1;
/*  21 */   private int dbresid = -1;
/*  22 */   private String type = "default";
/*  23 */   private String resourcename = null;
/*     */   
/*     */ 
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  29 */     long curtime = System.currentTimeMillis();
/*  30 */     long queryval = curtime - 3600000L;
/*  31 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  32 */     ResultSet rs = null;
/*  33 */     if (this.type.equals("CONNECTION"))
/*     */     {
/*  35 */       String query = "select * from AM_ManagedObjectData where AM_ManagedObjectData.RESID='" + this.resid + "' and AM_ManagedObjectData.RESPONSETIME <> '-1'";
/*     */       try
/*     */       {
/*  38 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  42 */         e.printStackTrace();
/*     */       }
/*  44 */       TimeSeriesCollection col = new TimeSeriesCollection();
/*  45 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Connection Time"), Minute.class);
/*     */       try
/*     */       {
/*  48 */         while (rs.next())
/*     */         {
/*  50 */           long time = rs.getLong(1);
/*  51 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  54 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("RESPONSETIME"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/*  58 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*  61 */         col.addSeries(ts1);
/*  62 */         if (rs != null)
/*     */         {
/*  64 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  69 */         col.addSeries(ts1);
/*  70 */         e.printStackTrace();
/*     */       }
/*  72 */       int[] x = { 0 };
/*  73 */       return new SubSeriesDataset(col, x);
/*     */     }
/*  75 */     if (this.type.equals("CONNECTIONSTATICS"))
/*     */     {
/*  77 */       String query = "select TOTALCONN,COLLECTIONTIME from AM_DB2CONNECTION_AGENTS where AM_DB2CONNECTION_AGENTS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/*  80 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  84 */         e.printStackTrace();
/*     */       }
/*  86 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/*  87 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.graph.numberofconnections"), Minute.class);
/*     */       try
/*     */       {
/*  90 */         while (rs.next())
/*     */         {
/*  92 */           long time = rs.getLong(1);
/*  93 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/*  96 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("TOTALCONN"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 100 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 103 */         col1.addSeries(ts1);
/* 104 */         if (rs != null)
/*     */         {
/* 106 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 111 */         col1.addSeries(ts1);
/* 112 */         e.printStackTrace();
/*     */       }
/* 114 */       int[] x = { 0 };
/* 115 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 117 */     if (this.type.equals("ACTIVEAGENTSTATICS"))
/*     */     {
/* 119 */       String query = "select AGENTSACTIVE,COLLECTIONTIME from AM_DB2CONNECTION_AGENTS where AM_DB2CONNECTION_AGENTS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/* 122 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 126 */         e.printStackTrace();
/*     */       }
/* 128 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 129 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.graph.numberofactiveagents"), Minute.class);
/*     */       try
/*     */       {
/* 132 */         while (rs.next())
/*     */         {
/* 134 */           long time = rs.getLong(1);
/* 135 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 138 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("AGENTSACTIVE"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 142 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 145 */         col1.addSeries(ts1);
/* 146 */         if (rs != null)
/*     */         {
/* 148 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 153 */         col1.addSeries(ts1);
/* 154 */         e.printStackTrace();
/*     */       }
/* 156 */       int[] x = { 0 };
/* 157 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 159 */     if (this.type.equals("WAITINGAGENTSTATICS"))
/*     */     {
/* 161 */       String query = "select AGENTSWAITING,COLLECTIONTIME from AM_DB2CONNECTION_AGENTS where AM_DB2CONNECTION_AGENTS.RESOURCEID='" + this.resid + "'";
/*     */       try
/*     */       {
/* 164 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 168 */         e.printStackTrace();
/*     */       }
/* 170 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 171 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.graph.numberofagentswaiting"), Minute.class);
/*     */       try
/*     */       {
/* 174 */         while (rs.next())
/*     */         {
/* 176 */           long time = rs.getLong(1);
/* 177 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 180 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("AGENTSWAITING"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 184 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 187 */         col1.addSeries(ts1);
/* 188 */         if (rs != null)
/*     */         {
/* 190 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 195 */         col1.addSeries(ts1);
/* 196 */         e.printStackTrace();
/*     */       }
/* 198 */       int[] x = { 0 };
/* 199 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 201 */     if (this.type.equals("RATEOFWORK"))
/*     */     {
/* 203 */       String query = "select RATEOFWORK,COLLECTIONTIME from AM_DB2DATABASESTATUS where AM_DB2DATABASESTATUS.RESOURCEID='" + this.dbresid + "'";
/*     */       try
/*     */       {
/* 206 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 210 */         e.printStackTrace();
/*     */       }
/* 212 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 213 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.unitsofwork"), Minute.class);
/*     */       try
/*     */       {
/* 216 */         while (rs.next())
/*     */         {
/* 218 */           long time = rs.getLong(1);
/* 219 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 222 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("RATEOFWORK"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 226 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 229 */         col1.addSeries(ts1);
/* 230 */         if (rs != null)
/*     */         {
/* 232 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 237 */         col1.addSeries(ts1);
/* 238 */         e.printStackTrace();
/*     */       }
/* 240 */       int[] x = { 0 };
/* 241 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 243 */     if (this.type.equals("PKGCACHEHITRATIO"))
/*     */     {
/* 245 */       String query = "select PKGCACHEHITRATIO,COLLECTIONTIME from AM_DB2DATABASESTATUS where AM_DB2DATABASESTATUS.RESOURCEID='" + this.dbresid + "'";
/*     */       try
/*     */       {
/* 248 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 252 */         e.printStackTrace();
/*     */       }
/* 254 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 255 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.packagecache"), Minute.class);
/*     */       try
/*     */       {
/* 258 */         while (rs.next())
/*     */         {
/* 260 */           long time = rs.getLong(1);
/* 261 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 264 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("PKGCACHEHITRATIO"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 268 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 271 */         col1.addSeries(ts1);
/* 272 */         if (rs != null)
/*     */         {
/* 274 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 279 */         col1.addSeries(ts1);
/* 280 */         e.printStackTrace();
/*     */       }
/* 282 */       int[] x = { 0 };
/* 283 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 285 */     if (this.type.equals("DBSIZE"))
/*     */     {
/* 287 */       String query = "select DBSIZE,COLLECTIONTIME from AM_DB2DATABASESTATUS where AM_DB2DATABASESTATUS.RESOURCEID='" + this.dbresid + "'";
/*     */       try
/*     */       {
/* 290 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 294 */         e.printStackTrace();
/*     */       }
/* 296 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 297 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.databasesize"), Minute.class);
/*     */       try
/*     */       {
/* 300 */         while (rs.next())
/*     */         {
/* 302 */           long time = rs.getLong(1);
/* 303 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 306 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("DBSIZE"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 310 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 313 */         col1.addSeries(ts1);
/* 314 */         if (rs != null)
/*     */         {
/* 316 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 321 */         col1.addSeries(ts1);
/* 322 */         e.printStackTrace();
/*     */       }
/* 324 */       int[] x = { 0 };
/* 325 */       return new SubSeriesDataset(col1, x);
/*     */     }
/*     */     
/* 328 */     if (this.type.equals("BUFFERPOOLSTATS"))
/*     */     {
/* 330 */       String query = "select BUFFERPOOLHITRATIO,COLLECTIONTIME from AM_DB2BUFFERPOOLSTATS where AM_DB2BUFFERPOOLSTATS.RESOURCEID='" + this.dbresid + "'";
/*     */       try
/*     */       {
/* 333 */         rs = AMConnectionPool.executeQueryStmt(query);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 337 */         e.printStackTrace();
/*     */       }
/* 339 */       TimeSeriesCollection col1 = new TimeSeriesCollection();
/* 340 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.db2.bufferpool"), Minute.class);
/*     */       try
/*     */       {
/* 343 */         while (rs.next())
/*     */         {
/* 345 */           long time = rs.getLong(1);
/* 346 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           try
/*     */           {
/* 349 */             ts1.addOrUpdate(new Minute(d), rs.getDouble("BUFFERPOOLHITRATIO"));
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 353 */             ex.printStackTrace();
/*     */           }
/*     */         }
/* 356 */         col1.addSeries(ts1);
/* 357 */         if (rs != null)
/*     */         {
/* 359 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 364 */         col1.addSeries(ts1);
/* 365 */         e.printStackTrace();
/*     */       }
/* 367 */       int[] x = { 0 };
/* 368 */       return new SubSeriesDataset(col1, x);
/*     */     }
/* 370 */     if (this.type.equals("ROW_STATS"))
/*     */     {
/*     */ 
/* 373 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 374 */       String query = "select ROWSINSERTEDRATE,ROWSDELETEDRATE,ROWSSELECTEDRATE,ROWSUPDATEDRATE,COLLECTIONTIME from AM_DB2_ROWSTATS where RESOURCEID=" + this.dbresid;
/* 375 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("Rows Inserted/Sec"), Minute.class);
/* 376 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("Rows Deleted/Sec"), Minute.class);
/* 377 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("Rows Selected/Sec"), Minute.class);
/* 378 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("Rows Updated/Sec"), Minute.class);
/*     */       try
/*     */       {
/* 381 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 382 */         while (rs.next())
/*     */         {
/* 384 */           Date d = new Date(rs.getLong(5));
/* 385 */           if (rs.getLong(1) >= 0L)
/* 386 */             ts1.addOrUpdate(new Minute(d), new Long(rs.getLong(1)));
/* 387 */           if (rs.getLong(2) >= 0L)
/* 388 */             ts2.addOrUpdate(new Minute(d), new Long(rs.getLong(2)));
/* 389 */           if (rs.getLong(3) >= 0L)
/* 390 */             ts3.addOrUpdate(new Minute(d), new Long(rs.getLong(3)));
/* 391 */           if (rs.getLong(4) >= 0L) {
/* 392 */             ts3.addOrUpdate(new Minute(d), new Long(rs.getLong(4)));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 403 */           if (rs != null)
/*     */           {
/* 405 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 410 */           e.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/* 414 */         col = new TimeSeriesCollection();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 397 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 403 */           if (rs != null)
/*     */           {
/* 405 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 410 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */       TimeSeriesCollection col;
/* 415 */       col.addSeries(ts1);
/* 416 */       col.addSeries(ts2);
/* 417 */       col.addSeries(ts3);
/* 418 */       col.addSeries(ts4);
/* 419 */       int[] x = { 0, 1, 2, 3 };
/* 420 */       return new SubSeriesDataset(col, x);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 425 */     return null;
/*     */   }
/*     */   
/*     */   public int getresid()
/*     */   {
/* 430 */     return this.resid;
/*     */   }
/*     */   
/*     */   public void setresid(int resource)
/*     */   {
/* 435 */     this.resid = resource;
/*     */   }
/*     */   
/*     */   public int getdbresid()
/*     */   {
/* 440 */     return this.dbresid;
/*     */   }
/*     */   
/*     */   public void setdbresid(int resource)
/*     */   {
/* 445 */     this.dbresid = resource;
/*     */   }
/*     */   
/*     */   public String gettype()
/*     */   {
/* 450 */     return this.type;
/*     */   }
/*     */   
/*     */   public void settype(String cat)
/*     */   {
/* 455 */     this.type = cat;
/*     */   }
/*     */   
/*     */   public void setresourcename(String name)
/*     */   {
/* 460 */     this.resourcename = name;
/*     */   }
/*     */   
/*     */   public String getresourcename()
/*     */   {
/* 465 */     return this.resourcename;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\db2\bean\DB2Graphs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */