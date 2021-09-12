/*     */ package com.adventnet.appmanager.client.sap;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
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
/*     */ public class SAPGraph
/*     */   implements DatasetProducer
/*     */ {
/*  26 */   String resourceid = null;
/*  27 */   String parameter = null;
/*     */   
/*  29 */   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
/*     */   
/*     */   public void setParameter(String resid, String parameter) {
/*  32 */     this.resourceid = resid;
/*  33 */     this.parameter = parameter;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) {
/*  37 */     long curTime = System.currentTimeMillis();
/*  38 */     long sixhoursbefore = curTime - 21600000L;
/*     */     
/*  40 */     if (this.parameter.equals("backgroundutilization")) {
/*  41 */       ResultSet rs = null;
/*  42 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("UTILIZATION"), Minute.class);
/*     */       
/*  44 */       String query = "select butilization,collectiontime from AM_SAP_BackgroundInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/*  46 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  47 */         while (rs.next())
/*     */         {
/*  49 */           Date d = new Date(rs.getLong("collectiontime"));
/*  50 */           int value = rs.getInt("butilization");
/*  51 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  54 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  57 */         closeResultSet(rs);
/*     */       }
/*  59 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  60 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/*  62 */     if (this.parameter.equals("spoolutilization")) {
/*  63 */       ResultSet rs = null;
/*  64 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("UTILIZATION"), Minute.class);
/*     */       
/*  66 */       String query = "select sutilization,collectiontime from AM_SAP_SpoolInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/*  68 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  69 */         while (rs.next())
/*     */         {
/*  71 */           Date d = new Date(rs.getLong("collectiontime"));
/*  72 */           int value = rs.getInt("sutilization");
/*  73 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  76 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  79 */         closeResultSet(rs);
/*     */       }
/*  81 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  82 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/*  84 */     if (this.parameter.equals("spoolservicequeue")) {
/*  85 */       ResultSet rs = null;
/*  86 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("SERVICEQUEUE"), Minute.class);
/*     */       
/*  88 */       String query = "select servicequeue,collectiontime from AM_SAP_SpoolInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/*  90 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  91 */         while (rs.next())
/*     */         {
/*  93 */           Date d = new Date(rs.getLong("collectiontime"));
/*  94 */           int value = rs.getInt("servicequeue");
/*  95 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  98 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 101 */         closeResultSet(rs);
/*     */       }
/* 103 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 104 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 106 */     if (this.parameter.equals("spoolqueuelength")) {
/* 107 */       ResultSet rs = null;
/* 108 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUELENGTH"), Minute.class);
/*     */       
/* 110 */       String query = "select queuelength,collectiontime from AM_SAP_SpoolInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 112 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 113 */         while (rs.next())
/*     */         {
/* 115 */           Date d = new Date(rs.getLong("collectiontime"));
/* 116 */           int value = rs.getInt("queuelength");
/* 117 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 120 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 123 */         closeResultSet(rs);
/*     */       }
/* 125 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 126 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 128 */     if (this.parameter.equals("spoolservicequeuepages")) {
/* 129 */       ResultSet rs = null;
/* 130 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUETIME"), Minute.class);
/*     */       
/* 132 */       String query = "select servicequeuepages,collectiontime from AM_SAP_SpoolInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 134 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 135 */         while (rs.next())
/*     */         {
/* 137 */           Date d = new Date(rs.getLong("collectiontime"));
/* 138 */           int value = rs.getInt("servicequeuepages");
/* 139 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 142 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 145 */         closeResultSet(rs);
/*     */       }
/* 147 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 148 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 150 */     if (this.parameter.equals("dialogresponsetime")) {
/* 151 */       ResultSet rs = null;
/* 152 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUETIME"), Minute.class);
/*     */       
/* 154 */       String query = "select frontendresponsetime,collectiontime from AM_SAP_DialogInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 156 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 157 */         while (rs.next())
/*     */         {
/* 159 */           Date d = new Date(rs.getLong("collectiontime"));
/* 160 */           int value = rs.getInt("frontendresponsetime");
/* 161 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 164 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 167 */         closeResultSet(rs);
/*     */       }
/* 169 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 170 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 172 */     if (this.parameter.equals("dbrequesttime")) {
/* 173 */       ResultSet rs = null;
/* 174 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUETIME"), Minute.class);
/*     */       
/* 176 */       String query = "select dbrequesttime,collectiontime from AM_SAP_DialogInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 178 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 179 */         while (rs.next())
/*     */         {
/* 181 */           Date d = new Date(rs.getLong("collectiontime"));
/* 182 */           int value = rs.getInt("dbrequesttime");
/* 183 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 186 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 189 */         closeResultSet(rs);
/*     */       }
/* 191 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 192 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 194 */     if (this.parameter.equals("loadplusgentime")) {
/* 195 */       ResultSet rs = null;
/* 196 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUETIME"), Minute.class);
/*     */       
/* 198 */       String query = "select loadplusgentime,collectiontime from AM_SAP_DialogInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 200 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 201 */         while (rs.next())
/*     */         {
/* 203 */           Date d = new Date(rs.getLong("collectiontime"));
/* 204 */           int value = rs.getInt("loadplusgentime");
/* 205 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 208 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 211 */         closeResultSet(rs);
/*     */       }
/* 213 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 214 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 216 */     if (this.parameter.equals("queuetime")) {
/* 217 */       ResultSet rs = null;
/* 218 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("QUEUETIME"), Minute.class);
/*     */       
/* 220 */       String query = "select queuetime,collectiontime from AM_SAP_DialogInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 222 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 223 */         while (rs.next())
/*     */         {
/* 225 */           Date d = new Date(rs.getLong("collectiontime"));
/* 226 */           int value = rs.getInt("queuetime");
/* 227 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 230 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 233 */         closeResultSet(rs);
/*     */       }
/* 235 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 236 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 238 */     if (this.parameter.equals("enqueuerequests")) {
/* 239 */       ResultSet rs = null;
/* 240 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("ENQUEUEREQUESTS"), Minute.class);
/*     */       
/* 242 */       String query = "select enqueuerequests,collectiontime from AM_SAP_EnqueueInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 244 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 245 */         while (rs.next())
/*     */         {
/* 247 */           Date d = new Date(rs.getLong("collectiontime"));
/* 248 */           int value = rs.getInt("enqueuerequests");
/* 249 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 252 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 255 */         closeResultSet(rs);
/*     */       }
/* 257 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 258 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 260 */     if (this.parameter.equals("pageinfo")) {
/* 261 */       ResultSet rs = null;
/* 262 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("PAGEIN"), Minute.class);
/* 263 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("PAGEOUT"), Minute.class);
/*     */       
/* 265 */       String query = "select pagein,pageout,collectiontime from AM_SAP_OSInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 267 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 268 */         while (rs.next())
/*     */         {
/* 270 */           Date d = new Date(rs.getLong("collectiontime"));
/* 271 */           int value = rs.getInt("pagein");
/* 272 */           int value1 = rs.getInt("pageout");
/* 273 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/* 274 */           ts1.addOrUpdate(new Minute(d), new Integer(value1));
/*     */         }
/*     */       } catch (SQLException e) {
/* 277 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 280 */         closeResultSet(rs);
/*     */       }
/* 282 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 283 */       tsc.addSeries(ts);
/* 284 */       tsc.addSeries(ts1);
/* 285 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 287 */     if (this.parameter.equals("esact")) {
/* 288 */       ResultSet rs = null;
/* 289 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("ESACT"), Minute.class);
/*     */       
/* 291 */       String query = "select esact,collectiontime from AM_SAP_OSInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 293 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 294 */         while (rs.next())
/*     */         {
/* 296 */           Date d = new Date(rs.getLong("collectiontime"));
/* 297 */           int value = rs.getInt("esact");
/* 298 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 301 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 304 */         closeResultSet(rs);
/*     */       }
/* 306 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 307 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 309 */     if (this.parameter.equals("programbufferhitratio")) {
/* 310 */       ResultSet rs = null;
/* 311 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Program"), Minute.class);
/*     */       
/* 313 */       String query = "select binfo.hitratio,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename='Program' and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 315 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 316 */         while (rs.next())
/*     */         {
/* 318 */           Date d = new Date(rs.getLong("collectiontime"));
/* 319 */           int value = rs.getInt("hitratio");
/* 320 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 323 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 326 */         closeResultSet(rs);
/*     */       }
/* 328 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 329 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 331 */     if (this.parameter.equals("programbufferspaceused")) {
/* 332 */       ResultSet rs = null;
/* 333 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Program Buffer Storage Utilization"), Minute.class);
/*     */       
/* 335 */       String query = "select binfo.spaceused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename='Program' and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 337 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 338 */         while (rs.next())
/*     */         {
/* 340 */           Date d = new Date(rs.getLong("collectiontime"));
/* 341 */           int value = rs.getInt("spaceused");
/* 342 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 345 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 348 */         closeResultSet(rs);
/*     */       }
/* 350 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 351 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 353 */     if (this.parameter.equals("programbufferdirectoryused")) {
/* 354 */       ResultSet rs = null;
/* 355 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Program Buffer Directory Utilization"), Minute.class);
/*     */       
/* 357 */       String query = "select binfo.directoryused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename='Program' and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 359 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 360 */         while (rs.next())
/*     */         {
/* 362 */           Date d = new Date(rs.getLong("collectiontime"));
/* 363 */           int value = rs.getInt("directoryused");
/* 364 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 367 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 370 */         closeResultSet(rs);
/*     */       }
/* 372 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 373 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 375 */     if (this.parameter.equals("repositorybuffershitratio")) {
/* 376 */       ResultSet rs = null;
/* 377 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("FieldDescription"), Minute.class);
/* 378 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("TableDefinition"), Minute.class);
/* 379 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("ShortNameTAB"), Minute.class);
/* 380 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("InitialRecords"), Minute.class);
/*     */       
/* 382 */       String query = "select mo1.resourcename,binfo.hitratio,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('FieldDescription','TableDefinition','ShortNameTAB','InitialRecords') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 384 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 385 */         while (rs.next())
/*     */         {
/* 387 */           Date d = new Date(rs.getLong("collectiontime"));
/* 388 */           int value = rs.getInt("hitratio");
/* 389 */           String resname = rs.getString("resourcename");
/* 390 */           if (resname.equals("FieldDescription")) {
/* 391 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 393 */           else if (resname.equals("TableDefinition")) {
/* 394 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 396 */           else if (resname.equals("ShortNameTAB")) {
/* 397 */             ts2.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 399 */           else if (resname.equals("InitialRecords")) {
/* 400 */             ts3.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 404 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 407 */         closeResultSet(rs);
/*     */       }
/* 409 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 410 */       tsc.addSeries(ts);
/* 411 */       tsc.addSeries(ts1);
/* 412 */       tsc.addSeries(ts2);
/* 413 */       tsc.addSeries(ts3);
/*     */       
/* 415 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3 });
/*     */     }
/* 417 */     if (this.parameter.equals("repositorybuffersspaceused")) {
/* 418 */       ResultSet rs = null;
/* 419 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("FieldDescription"), Minute.class);
/* 420 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("TableDefinition"), Minute.class);
/* 421 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("ShortNameTAB"), Minute.class);
/* 422 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("InitialRecords"), Minute.class);
/*     */       
/* 424 */       String query = "select mo1.resourcename,binfo.spaceused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('FieldDescription','TableDefinition','ShortNameTAB','InitialRecords') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 426 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 427 */         while (rs.next())
/*     */         {
/* 429 */           Date d = new Date(rs.getLong("collectiontime"));
/* 430 */           int value = rs.getInt("spaceused");
/* 431 */           String resname = rs.getString("resourcename");
/* 432 */           if (resname.equals("FieldDescription")) {
/* 433 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 435 */           else if (resname.equals("TableDefinition")) {
/* 436 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 438 */           else if (resname.equals("ShortNameTAB")) {
/* 439 */             ts2.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 441 */           else if (resname.equals("InitialRecords")) {
/* 442 */             ts3.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 446 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 449 */         closeResultSet(rs);
/*     */       }
/* 451 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 452 */       tsc.addSeries(ts);
/* 453 */       tsc.addSeries(ts1);
/* 454 */       tsc.addSeries(ts2);
/* 455 */       tsc.addSeries(ts3);
/*     */       
/* 457 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3 });
/*     */     }
/* 459 */     if (this.parameter.equals("repositorybuffersdirectoryused")) {
/* 460 */       ResultSet rs = null;
/* 461 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("FieldDescription"), Minute.class);
/* 462 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("TableDefinition"), Minute.class);
/* 463 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("ShortNameTAB"), Minute.class);
/* 464 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("InitialRecords"), Minute.class);
/*     */       
/* 466 */       String query = "select mo1.resourcename,binfo.directoryused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('FieldDescription','TableDefinition','ShortNameTAB','InitialRecords') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 468 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 469 */         while (rs.next())
/*     */         {
/* 471 */           Date d = new Date(rs.getLong("collectiontime"));
/* 472 */           int value = rs.getInt("directoryused");
/* 473 */           String resname = rs.getString("resourcename");
/* 474 */           if (resname.equals("FieldDescription")) {
/* 475 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 477 */           else if (resname.equals("TableDefinition")) {
/* 478 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 480 */           else if (resname.equals("ShortNameTAB")) {
/* 481 */             ts2.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 483 */           else if (resname.equals("InitialRecords")) {
/* 484 */             ts3.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 488 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 491 */         closeResultSet(rs);
/*     */       }
/* 493 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 494 */       tsc.addSeries(ts);
/* 495 */       tsc.addSeries(ts1);
/* 496 */       tsc.addSeries(ts2);
/* 497 */       tsc.addSeries(ts3);
/*     */       
/* 499 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3 });
/*     */     }
/* 501 */     if (this.parameter.equals("guibuffershitratio")) {
/* 502 */       ResultSet rs = null;
/* 503 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Screen"), Minute.class);
/* 504 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("CUA"), Minute.class);
/*     */       
/* 506 */       String query = "select mo1.resourcename,binfo.hitratio,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('Screen','CUA') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 508 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 509 */         while (rs.next())
/*     */         {
/* 511 */           Date d = new Date(rs.getLong("collectiontime"));
/* 512 */           int value = rs.getInt("hitratio");
/* 513 */           String resname = rs.getString("resourcename");
/* 514 */           if (resname.equals("Screen")) {
/* 515 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 517 */           else if (resname.equals("CUA")) {
/* 518 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 522 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 525 */         closeResultSet(rs);
/*     */       }
/* 527 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 528 */       tsc.addSeries(ts);
/* 529 */       tsc.addSeries(ts1);
/*     */       
/* 531 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 533 */     if (this.parameter.equals("guibuffersspaceused")) {
/* 534 */       ResultSet rs = null;
/* 535 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Screen"), Minute.class);
/* 536 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("CUA"), Minute.class);
/*     */       
/* 538 */       String query = "select mo1.resourcename,binfo.spaceused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('Screen','CUA') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 540 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 541 */         while (rs.next())
/*     */         {
/* 543 */           Date d = new Date(rs.getLong("collectiontime"));
/* 544 */           int value = rs.getInt("spaceused");
/* 545 */           String resname = rs.getString("resourcename");
/* 546 */           if (resname.equals("Screen")) {
/* 547 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 549 */           else if (resname.equals("CUA")) {
/* 550 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 554 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 557 */         closeResultSet(rs);
/*     */       }
/* 559 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 560 */       tsc.addSeries(ts);
/* 561 */       tsc.addSeries(ts1);
/*     */       
/* 563 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 565 */     if (this.parameter.equals("guibuffersdirectoryused")) {
/* 566 */       ResultSet rs = null;
/* 567 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("Screen"), Minute.class);
/* 568 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("CUA"), Minute.class);
/*     */       
/* 570 */       String query = "select mo1.resourcename,binfo.directoryused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('Screen','CUA') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 572 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 573 */         while (rs.next())
/*     */         {
/* 575 */           Date d = new Date(rs.getLong("collectiontime"));
/* 576 */           int value = rs.getInt("directoryused");
/* 577 */           String resname = rs.getString("resourcename");
/* 578 */           if (resname.equals("Screen")) {
/* 579 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 581 */           else if (resname.equals("CUA")) {
/* 582 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 586 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 589 */         closeResultSet(rs);
/*     */       }
/* 591 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 592 */       tsc.addSeries(ts);
/* 593 */       tsc.addSeries(ts1);
/*     */       
/* 595 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 597 */     if (this.parameter.equals("tablebuffershitratio")) {
/* 598 */       ResultSet rs = null;
/* 599 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("SingleRecord"), Minute.class);
/* 600 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("GenericKey"), Minute.class);
/*     */       
/* 602 */       String query = "select mo1.resourcename,binfo.hitratio,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('SingleRecord','GenericKey') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 604 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 605 */         while (rs.next())
/*     */         {
/* 607 */           Date d = new Date(rs.getLong("collectiontime"));
/* 608 */           int value = rs.getInt("hitratio");
/* 609 */           String resname = rs.getString("resourcename");
/* 610 */           if (resname.equals("SingleRecord")) {
/* 611 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 613 */           else if (resname.equals("GenericKey")) {
/* 614 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 618 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 621 */         closeResultSet(rs);
/*     */       }
/* 623 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 624 */       tsc.addSeries(ts);
/* 625 */       tsc.addSeries(ts1);
/*     */       
/* 627 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 629 */     if (this.parameter.equals("tablebuffersspaceused")) {
/* 630 */       ResultSet rs = null;
/* 631 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("SingleRecord"), Minute.class);
/* 632 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("GenericKey"), Minute.class);
/*     */       
/* 634 */       String query = "select mo1.resourcename,binfo.spaceused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('SingleRecord','GenericKey') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 636 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 637 */         while (rs.next())
/*     */         {
/* 639 */           Date d = new Date(rs.getLong("collectiontime"));
/* 640 */           int value = rs.getInt("spaceused");
/* 641 */           String resname = rs.getString("resourcename");
/* 642 */           if (resname.equals("SingleRecord")) {
/* 643 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 645 */           else if (resname.equals("GenericKey")) {
/* 646 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 650 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 653 */         closeResultSet(rs);
/*     */       }
/* 655 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 656 */       tsc.addSeries(ts);
/* 657 */       tsc.addSeries(ts1);
/*     */       
/* 659 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 661 */     if (this.parameter.equals("tablebuffersdirectoryused")) {
/* 662 */       ResultSet rs = null;
/* 663 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("SingleRecord"), Minute.class);
/* 664 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("GenericKey"), Minute.class);
/*     */       
/* 666 */       String query = "select mo1.resourcename,binfo.directoryused,binfo.collectiontime from AM_SAP_BufferInfo binfo, AM_ManagedObject mo, AM_ManagedObject mo1, AM_PARENTCHILDMAPPER pcm where mo.resourceid=" + this.resourceid + " and mo.resourceid=pcm.parentid and mo1.resourceid=pcm.childid and pcm.childid=binfo.resourceid and mo1.resourcename IN ('SingleRecord','GenericKey') and  binfo.collectiontime>=" + sixhoursbefore + " and binfo.collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 668 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 669 */         while (rs.next())
/*     */         {
/* 671 */           Date d = new Date(rs.getLong("collectiontime"));
/* 672 */           int value = rs.getInt("directoryused");
/* 673 */           String resname = rs.getString("resourcename");
/* 674 */           if (resname.equals("SingleRecord")) {
/* 675 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/* 677 */           else if (resname.equals("GenericKey")) {
/* 678 */             ts1.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 682 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 685 */         closeResultSet(rs);
/*     */       }
/* 687 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 688 */       tsc.addSeries(ts);
/* 689 */       tsc.addSeries(ts1);
/*     */       
/* 691 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 693 */     if (this.parameter.equals("syslogfreq")) {
/* 694 */       ResultSet rs = null;
/* 695 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("SYSLOGFREQ"), Minute.class);
/*     */       
/* 697 */       String query = "select syslogfreq,collectiontime from AM_SAP_OSInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 699 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 700 */         while (rs.next())
/*     */         {
/* 702 */           Date d = new Date(rs.getLong("collectiontime"));
/* 703 */           int value = rs.getInt("syslogfreq");
/* 704 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 707 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 710 */         closeResultSet(rs);
/*     */       }
/* 712 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 713 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 715 */     if (this.parameter.equals("cpu")) {
/* 716 */       ResultSet rs = null;
/* 717 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("CPU"), Minute.class);
/*     */       
/* 719 */       String query = "select cpuutilization,collectiontime from AM_SAP_OSInfo where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime + " order by collectiontime asc";
/*     */       try {
/* 721 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 722 */         while (rs.next())
/*     */         {
/* 724 */           Date d = new Date(rs.getLong("collectiontime"));
/* 725 */           int value = rs.getInt("cpuutilization");
/* 726 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/* 729 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 732 */         closeResultSet(rs);
/*     */       }
/* 734 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 735 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 737 */     return null;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/* 742 */     AMConnectionPool.closeStatement(rs);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\sap\SAPGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */