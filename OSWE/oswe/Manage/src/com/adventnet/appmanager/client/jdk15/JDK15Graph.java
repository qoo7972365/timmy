/*     */ package com.adventnet.appmanager.client.jdk15;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ public class JDK15Graph
/*     */   implements DatasetProducer
/*     */ {
/*  24 */   String resourceid = null;
/*  25 */   String parameter = null;
/*     */   
/*     */   public void setParameter(String resid, String parameter) {
/*  28 */     this.resourceid = resid;
/*  29 */     this.parameter = parameter;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map map) {
/*  33 */     long curTime = System.currentTimeMillis();
/*  34 */     long sixhoursbefore = curTime - 21600000L;
/*     */     
/*     */ 
/*     */ 
/*  38 */     if (this.parameter.equals("responsetime")) {
/*  39 */       ResultSet rs = null;
/*  40 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.responsetime.text"), Minute.class);
/*     */       
/*  42 */       String query = "select responsetime,collectiontime from AM_JDK15_VMINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/*  44 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  45 */         while (rs.next())
/*     */         {
/*  47 */           Date d = new Date(rs.getLong("collectiontime"));
/*  48 */           int value = rs.getInt("responsetime");
/*  49 */           ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  52 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  55 */         closeResultSet(rs);
/*     */       }
/*  57 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  58 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/*  60 */     if (this.parameter.equals("processmemory")) {
/*  61 */       ResultSet rs = null;
/*  62 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.memory.text"), Minute.class);
/*     */       
/*  64 */       String query = "select processmemory,collectiontime from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/*  66 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  67 */         while (rs.next())
/*     */         {
/*  69 */           Date d = new Date(rs.getLong("collectiontime"));
/*  70 */           long value = rs.getLong("processmemory") / 1048576L;
/*  71 */           ts.addOrUpdate(new Minute(d), new Long(value));
/*     */         }
/*     */       } catch (SQLException e) {
/*  74 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*  77 */         closeResultSet(rs);
/*     */       }
/*  79 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/*  80 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/*  82 */     if (this.parameter.equals("processcpu")) {
/*  83 */       ResultSet rs = null;
/*  84 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.cpu.text"), Minute.class);
/*     */       
/*  86 */       String query = "select cputimeper,collectiontime from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/*  88 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  89 */         while (rs.next())
/*     */         {
/*  91 */           Date d = new Date(rs.getLong("collectiontime"));
/*  92 */           int value = rs.getInt("cputimeper");
/*  93 */           if (value != -1)
/*     */           {
/*  95 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/*  99 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 102 */         closeResultSet(rs);
/*     */       }
/* 104 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 105 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 107 */     if (this.parameter.equals("cpuload"))
/*     */     {
/* 109 */       ResultSet rs = null;
/* 110 */       TimeSeries ts = new TimeSeries(FormatUtil.getString("am.webclient.jre.cpuload.text"), Minute.class);
/*     */       
/* 112 */       String query = "select VMCPULOAD,collectiontime from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/* 114 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 115 */         while (rs.next())
/*     */         {
/* 117 */           Date d = new Date(rs.getLong("collectiontime"));
/* 118 */           int value = rs.getInt("VMCPULOAD");
/* 119 */           if (value != -1)
/*     */           {
/* 121 */             ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 125 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 128 */         closeResultSet(rs);
/*     */       }
/* 130 */       TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 131 */       return new SubSeriesDataset(tsc, 0);
/*     */     }
/* 133 */     if (this.parameter.equals("ibmheap")) {
/* 134 */       ResultSet rs = null;
/* 135 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalheap.text"), Minute.class);
/* 136 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.javaheap.text1"), Minute.class);
/*     */       
/* 138 */       String query = "select usedheap,JAVAHEAP,AM_JDK15_HEAPMEMORYINFO.collectiontime from AM_JDK15_HEAPMEMORYINFO_EXT,AM_JDK15_HEAPMEMORYINFO where AM_JDK15_HEAPMEMORYINFO.resourceid=" + this.resourceid + " and AM_JDK15_HEAPMEMORYINFO.RESOURCEID=AM_JDK15_HEAPMEMORYINFO_EXT.RESOURCEID  and AM_JDK15_HEAPMEMORYINFO.collectiontime>=" + sixhoursbefore + " and AM_JDK15_HEAPMEMORYINFO.collectiontime<=" + curTime;
/*     */       try {
/* 140 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 141 */         while (rs.next())
/*     */         {
/* 143 */           Date d = new Date(rs.getLong("collectiontime"));
/* 144 */           long heapbytes = rs.getLong("usedheap");
/* 145 */           if (heapbytes != -1L) {
/* 146 */             long heap = heapbytes / 1048576L;
/* 147 */             ts1.addOrUpdate(new Minute(d), heap);
/*     */           }
/* 149 */           long javaheap = rs.getLong("JAVAHEAP");
/* 150 */           if (javaheap != -1L) {
/* 151 */             ts2.addOrUpdate(new Minute(d), javaheap / 1048576L);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (SQLException e) {
/* 156 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 159 */         closeResultSet(rs);
/*     */       }
/* 161 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 162 */       tsc.addSeries(ts1);
/* 163 */       tsc.addSeries(ts2);
/*     */       
/* 165 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 167 */     if (this.parameter.equals("beaheap")) {
/* 168 */       ResultSet rs = null;
/* 169 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalheap.text"), Minute.class);
/* 170 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.javaheap.text"), Minute.class);
/* 171 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.nursery.text"), Minute.class);
/* 172 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.oldspace.text"), Minute.class);
/*     */       
/* 174 */       String query = "select usedheap,JJAVAHEAP,NURSERY,OLDSPACE,AM_JDK15_HEAPMEMORYINFO.collectiontime from AM_JDK15_HEAPMEMORYINFO_EXT,AM_JDK15_HEAPMEMORYINFO where AM_JDK15_HEAPMEMORYINFO.resourceid=" + this.resourceid + " and AM_JDK15_HEAPMEMORYINFO.RESOURCEID=AM_JDK15_HEAPMEMORYINFO_EXT.RESOURCEID  and AM_JDK15_HEAPMEMORYINFO.collectiontime>=" + sixhoursbefore + " and AM_JDK15_HEAPMEMORYINFO.collectiontime<=" + curTime;
/*     */       try {
/* 176 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 177 */         while (rs.next())
/*     */         {
/* 179 */           Date d = new Date(rs.getLong("collectiontime"));
/* 180 */           long heapbytes = rs.getLong("usedheap");
/* 181 */           if (heapbytes != -1L) {
/* 182 */             long heap = heapbytes / 1048576L;
/* 183 */             ts1.addOrUpdate(new Minute(d), heap);
/*     */           }
/* 185 */           long javaheap = rs.getLong("JJAVAHEAP");
/* 186 */           if (javaheap != -1L) {
/* 187 */             ts2.addOrUpdate(new Minute(d), javaheap / 1048576L);
/*     */           }
/* 189 */           long nursery = rs.getLong("NURSERY");
/* 190 */           if (nursery != -1L) {
/* 191 */             ts3.addOrUpdate(new Minute(d), nursery / 1048576L);
/*     */           }
/* 193 */           long oldspace = rs.getLong("OLDSPACE");
/* 194 */           if (oldspace != -1L) {
/* 195 */             ts4.addOrUpdate(new Minute(d), oldspace / 1048576L);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (SQLException e) {
/* 200 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 203 */         closeResultSet(rs);
/*     */       }
/* 205 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 206 */       tsc.addSeries(ts1);
/* 207 */       tsc.addSeries(ts2);
/* 208 */       tsc.addSeries(ts3);
/* 209 */       tsc.addSeries(ts4);
/*     */       
/* 211 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3 });
/*     */     }
/* 213 */     if (this.parameter.equals("ibmnonheap")) {
/* 214 */       ResultSet rs = null;
/* 215 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalnonheap.text"), Minute.class);
/* 216 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.jitcodecache.text"), Minute.class);
/* 217 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.jitdatacache.text"), Minute.class);
/* 218 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.classstorage.text"), Minute.class);
/* 219 */       TimeSeries ts5 = new TimeSeries(FormatUtil.getString("am.webclient.javaruntime.nonheapstorage.text"), Minute.class);
/*     */       
/* 221 */       String query = "select usednonheap,JITCCACHE,JITDCACHE,CLASSSTOR,NONHPSTOR,AM_JDK15_NONHEAPMEMORYINFO.collectiontime from AM_JDK15_NONHEAPMEMORYINFO_EXT,AM_JDK15_NONHEAPMEMORYINFO where AM_JDK15_NONHEAPMEMORYINFO_EXT.RESOURCEID=AM_JDK15_NONHEAPMEMORYINFO.RESOURCEID and AM_JDK15_NONHEAPMEMORYINFO.resourceid=" + this.resourceid + " and AM_JDK15_NONHEAPMEMORYINFO.collectiontime>=" + sixhoursbefore + " and AM_JDK15_NONHEAPMEMORYINFO.collectiontime<=" + curTime;
/*     */       try {
/* 223 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 224 */         while (rs.next())
/*     */         {
/* 226 */           Date d = new Date(rs.getLong("collectiontime"));
/* 227 */           long nonheapbytes = rs.getLong("usednonheap");
/* 228 */           if (nonheapbytes != -1L) {
/* 229 */             long nonheap = nonheapbytes / 1048576L;
/* 230 */             ts1.addOrUpdate(new Minute(d), nonheap);
/*     */           }
/* 232 */           long permgenbytes = rs.getLong("JITCCACHE");
/* 233 */           if (permgenbytes != -1L) {
/* 234 */             long permgen = permgenbytes / 1048576L;
/* 235 */             ts2.addOrUpdate(new Minute(d), permgen);
/*     */           }
/* 237 */           long permgenrobytes = rs.getLong("JITDCACHE");
/* 238 */           if (permgenrobytes != -1L) {
/* 239 */             long permgenro = permgenrobytes / 1048576L;
/* 240 */             ts3.addOrUpdate(new Minute(d), permgenro);
/*     */           }
/* 242 */           long permgenrwbytes = rs.getLong("CLASSSTOR");
/* 243 */           if (permgenrwbytes != -1L) {
/* 244 */             long permgenrw = permgenrwbytes / 1048576L;
/* 245 */             ts4.addOrUpdate(new Minute(d), permgenrw);
/*     */           }
/* 247 */           long codecachebytes = rs.getLong("NONHPSTOR");
/* 248 */           if (codecachebytes != -1L) {
/* 249 */             long codecache = codecachebytes / 1048576L;
/* 250 */             ts5.addOrUpdate(new Minute(d), codecache);
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 254 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 257 */         closeResultSet(rs);
/*     */       }
/* 259 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 260 */       tsc.addSeries(ts1);
/* 261 */       tsc.addSeries(ts2);
/* 262 */       tsc.addSeries(ts3);
/* 263 */       tsc.addSeries(ts4);
/* 264 */       tsc.addSeries(ts5);
/*     */       
/* 266 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3, 4 }); }
/* 267 */     if (this.parameter.equals("beanonheap")) {
/* 268 */       ResultSet rs = null;
/* 269 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalnonheap.text"), Minute.class);
/* 270 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.classmemory.text"), Minute.class);
/* 271 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.classblock.text"), Minute.class);
/*     */       
/* 273 */       String query = "select usednonheap,CLASSMEM,CLASSBLOCK,AM_JDK15_NONHEAPMEMORYINFO.collectiontime from AM_JDK15_NONHEAPMEMORYINFO_EXT,AM_JDK15_NONHEAPMEMORYINFO where AM_JDK15_NONHEAPMEMORYINFO.resourceid=" + this.resourceid + " and AM_JDK15_NONHEAPMEMORYINFO.RESOURCEID=AM_JDK15_NONHEAPMEMORYINFO_EXT.RESOURCEID and AM_JDK15_NONHEAPMEMORYINFO.collectiontime>=" + sixhoursbefore + " and AM_JDK15_NONHEAPMEMORYINFO.collectiontime<=" + curTime;
/*     */       try {
/* 275 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 276 */         while (rs.next())
/*     */         {
/* 278 */           Date d = new Date(rs.getLong("collectiontime"));
/* 279 */           long nonheapbytes = rs.getLong("usednonheap");
/* 280 */           if (nonheapbytes != -1L) {
/* 281 */             long nonheap = nonheapbytes / 1048576L;
/* 282 */             ts1.addOrUpdate(new Minute(d), nonheap);
/*     */           }
/* 284 */           long classmembytes = rs.getLong("CLASSMEM");
/* 285 */           if (classmembytes != -1L) {
/* 286 */             long classmem = classmembytes / 1048576L;
/* 287 */             ts2.addOrUpdate(new Minute(d), classmem);
/*     */           }
/* 289 */           long classblockbytes = rs.getLong("CLASSBLOCK");
/* 290 */           if (classblockbytes != -1L) {
/* 291 */             long classblock = classblockbytes / 1048576L;
/* 292 */             ts3.addOrUpdate(new Minute(d), classblock);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (SQLException e) {
/* 297 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 300 */         closeResultSet(rs);
/*     */       }
/* 302 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 303 */       tsc.addSeries(ts1);
/* 304 */       tsc.addSeries(ts2);
/* 305 */       tsc.addSeries(ts3);
/*     */       
/* 307 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2 });
/*     */     }
/* 309 */     if (this.parameter.equals("ibmhostmemory")) {
/* 310 */       ResultSet rs = null;
/* 311 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.freephysicalmem.text"), Minute.class);
/*     */       
/* 313 */       String query = "select FREEMEMORY,COLLECTIONTIME from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/* 315 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 316 */         while (rs.next())
/*     */         {
/* 318 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 319 */           long freemembytes = rs.getLong("FREEMEMORY");
/* 320 */           if (freemembytes != -1L) {
/* 321 */             long freemem = freemembytes / 1048576L;
/* 322 */             ts1.addOrUpdate(new Minute(d), freemem);
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 326 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 329 */         closeResultSet(rs);
/*     */       }
/* 331 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 332 */       tsc.addSeries(ts1);
/*     */       
/* 334 */       return new SubSeriesDataset(tsc, new int[] { 0 });
/*     */     }
/* 336 */     if (this.parameter.equals("heap")) {
/* 337 */       ResultSet rs = null;
/* 338 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalheap.text"), Minute.class);
/* 339 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.eden.text"), Minute.class);
/* 340 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.survivor.text"), Minute.class);
/* 341 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.tenuredgen.text"), Minute.class);
/*     */       
/* 343 */       String query = "select usedheap,eden,survivor,tengen,collectiontime from AM_JDK15_HEAPMEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/* 345 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 346 */         while (rs.next())
/*     */         {
/* 348 */           Date d = new Date(rs.getLong("collectiontime"));
/* 349 */           long heapbytes = rs.getLong("usedheap");
/* 350 */           if (heapbytes != -1L) {
/* 351 */             long heap = heapbytes / 1048576L;
/* 352 */             ts1.addOrUpdate(new Minute(d), heap);
/*     */           }
/* 354 */           long edenbytes = rs.getLong("eden");
/* 355 */           if (edenbytes != -1L) {
/* 356 */             long eden = edenbytes / 1048576L;
/* 357 */             ts2.addOrUpdate(new Minute(d), eden);
/*     */           }
/* 359 */           long survivorbytes = rs.getLong("survivor");
/* 360 */           if (survivorbytes != -1L) {
/* 361 */             long survivor = survivorbytes / 1048576L;
/* 362 */             ts3.addOrUpdate(new Minute(d), survivor);
/*     */           }
/* 364 */           long tengenbytes = rs.getLong("tengen");
/* 365 */           if (tengenbytes != -1L) {
/* 366 */             long tengen = tengenbytes / 1048576L;
/* 367 */             ts4.addOrUpdate(new Minute(d), tengen);
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 371 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 374 */         closeResultSet(rs);
/*     */       }
/* 376 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 377 */       tsc.addSeries(ts1);
/* 378 */       tsc.addSeries(ts2);
/* 379 */       tsc.addSeries(ts3);
/* 380 */       tsc.addSeries(ts4);
/*     */       
/* 382 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3 }); }
/*     */     long permgenbytes;
/* 384 */     if (this.parameter.equals("nonheap")) {
/* 385 */       ResultSet rs = null;
/* 386 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.totalnonheap.text"), Minute.class);
/* 387 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.permgen.text"), Minute.class);
/* 388 */       TimeSeries ts3 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.permgenro.text"), Minute.class);
/* 389 */       TimeSeries ts4 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.permgenrw.text"), Minute.class);
/* 390 */       TimeSeries ts5 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.codecache.text"), Minute.class);
/*     */       
/* 392 */       String query = "select usednonheap,permgen,permgenro,permgenrw,codecache,collectiontime from AM_JDK15_NONHEAPMEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/* 394 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 395 */         while (rs.next())
/*     */         {
/* 397 */           Date d = new Date(rs.getLong("collectiontime"));
/* 398 */           long nonheapbytes = rs.getLong("usednonheap");
/* 399 */           if (nonheapbytes != -1L) {
/* 400 */             long nonheap = nonheapbytes / 1048576L;
/* 401 */             ts1.addOrUpdate(new Minute(d), nonheap);
/*     */           }
/* 403 */           permgenbytes = rs.getLong("permgen");
/* 404 */           if (permgenbytes != -1L) {
/* 405 */             long permgen = permgenbytes / 1048576L;
/* 406 */             ts2.addOrUpdate(new Minute(d), permgen);
/*     */           }
/* 408 */           long permgenrobytes = rs.getLong("permgenro");
/* 409 */           if (permgenrobytes != -1L) {
/* 410 */             long permgenro = permgenrobytes / 1048576L;
/* 411 */             ts3.addOrUpdate(new Minute(d), permgenro);
/*     */           }
/* 413 */           long permgenrwbytes = rs.getLong("permgenrw");
/* 414 */           if (permgenrwbytes != -1L) {
/* 415 */             long permgenrw = permgenrwbytes / 1048576L;
/* 416 */             ts4.addOrUpdate(new Minute(d), permgenrw);
/*     */           }
/* 418 */           long codecachebytes = rs.getLong("codecache");
/* 419 */           if (codecachebytes != -1L) {
/* 420 */             long codecache = codecachebytes / 1048576L;
/* 421 */             ts5.addOrUpdate(new Minute(d), codecache);
/*     */           }
/*     */         }
/*     */       } catch (SQLException e) {
/* 425 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 428 */         closeResultSet(rs);
/*     */       }
/* 430 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 431 */       tsc.addSeries(ts1);
/* 432 */       tsc.addSeries(ts2);
/* 433 */       tsc.addSeries(ts3);
/* 434 */       tsc.addSeries(ts4);
/* 435 */       tsc.addSeries(ts5);
/*     */       
/* 437 */       return new SubSeriesDataset(tsc, new int[] { 0, 1, 2, 3, 4 });
/*     */     }
/* 439 */     if (this.parameter.equals("thread")) {
/* 440 */       ResultSet rs = null;
/* 441 */       TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.livethread.text"), Minute.class);
/* 442 */       TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.daemonthread.text"), Minute.class);
/*     */       
/* 444 */       String query = "select live,daemon,collectiontime from AM_JDK15_THREADINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */       try {
/* 446 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 447 */         while (rs.next())
/*     */         {
/* 449 */           Date d = new Date(rs.getLong("collectiontime"));
/* 450 */           long live = rs.getLong("live");
/* 451 */           long daemon = rs.getLong("daemon");
/*     */           
/* 453 */           ts1.addOrUpdate(new Minute(d), live);
/* 454 */           ts2.addOrUpdate(new Minute(d), daemon);
/*     */         }
/*     */       } catch (SQLException e) {
/* 457 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 460 */         closeResultSet(rs);
/*     */       }
/* 462 */       TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 463 */       tsc.addSeries(ts1);
/* 464 */       tsc.addSeries(ts2);
/*     */       
/* 466 */       return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */     }
/* 468 */     if (this.parameter.equals("overallmemory")) {
/* 469 */       ResultSet rs = null;
/*     */       try {
/* 471 */         String query = "select freememory,processmemory,others from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " order by collectiontime desc ";
/* 472 */         String modifiedquery = DBQueryUtil.getTopNValues(query, "1");
/* 473 */         rs = AMConnectionPool.executeQueryStmt(modifiedquery);
/* 474 */         long freememory = 0L;
/* 475 */         long processmemory = 0L;
/* 476 */         long others = 0L;
/*     */         
/*     */ 
/* 479 */         if (rs.next()) {
/* 480 */           freememory = rs.getLong("freememory");
/* 481 */           processmemory = rs.getLong("processmemory");
/* 482 */           others = rs.getLong("others");
/*     */         }
/* 484 */         closeResultSet(rs);
/*     */         
/* 486 */         DefaultPieDataset ds = new DefaultPieDataset();
/* 487 */         ds.setValue(FormatUtil.getString("am.webclient.jdk15.processmemory.text"), processmemory / 1048576L);
/* 488 */         ds.setValue(FormatUtil.getString("am.webclient.jdk15.others.text"), others / 1048576L);
/* 489 */         ds.setValue(FormatUtil.getString("am.webclient.jdk15.freememory.text"), freememory / 1048576L);
/*     */         
/* 491 */         return ds;
/*     */       } catch (Exception e) {
/* 493 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 496 */         closeResultSet(rs);
/*     */       }
/*     */     }
/* 499 */     else if (!this.parameter.equals("processmemory"))
/*     */     {
/*     */ 
/* 502 */       if (this.parameter.equals("garbagecollection"))
/*     */       {
/* 504 */         ResultSet rs = null;
/* 505 */         TimeSeries ts = new TimeSeries(FormatUtil.getString("Number of Collections"), Minute.class);
/*     */         
/* 507 */         String query = "select COLCOUNTMIN,COLLECTIONTIME from AM_JDK15_HEAPMEMORYINFO_BGC where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */         try
/*     */         {
/* 510 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 511 */           while (rs.next())
/*     */           {
/* 513 */             Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 514 */             int value = rs.getInt("COLCOUNTMIN");
/* 515 */             if (value != -1)
/*     */             {
/* 517 */               ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */             }
/*     */           }
/*     */         } catch (SQLException e) {
/* 521 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 524 */           closeResultSet(rs);
/*     */         }
/* 526 */         TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 527 */         return new SubSeriesDataset(tsc, 0);
/*     */       }
/* 529 */       if (this.parameter.equals("totalgarbagecollection")) {
/* 530 */         ResultSet rs = null;
/* 531 */         TimeSeries ts = new TimeSeries(FormatUtil.getString("Number of Collections"), Minute.class);
/*     */         
/* 533 */         String query = "select TOTCOLCOUNTDIF,COLLECTIONTIME  from AM_JDK15_GCINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */         try
/*     */         {
/* 536 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 537 */           while (rs.next())
/*     */           {
/* 539 */             Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 540 */             int value = rs.getInt("TOTCOLCOUNTDIF");
/* 541 */             if (value != -1)
/*     */             {
/* 543 */               ts.addOrUpdate(new Minute(d), new Integer(value));
/*     */             }
/*     */           }
/*     */         } catch (SQLException e) {
/* 547 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 550 */           closeResultSet(rs);
/*     */         }
/* 552 */         TimeSeriesCollection tsc = new TimeSeriesCollection(ts);
/* 553 */         return new SubSeriesDataset(tsc, 0);
/*     */       }
/* 555 */       if (this.parameter.equals("hostmemory")) {
/* 556 */         ResultSet rs = null;
/* 557 */         TimeSeries ts1 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.freephysicalmem.text"), Minute.class);
/* 558 */         TimeSeries ts2 = new TimeSeries(FormatUtil.getString("am.webclient.jdk15.freeswapspace.text"), Minute.class);
/*     */         
/* 560 */         String query = "select FREEMEMORY,FREESWAPSPACE,COLLECTIONTIME from AM_JDK15_MEMORYINFO where resourceid=" + this.resourceid + " and collectiontime>=" + sixhoursbefore + " and collectiontime<=" + curTime;
/*     */         try {
/* 562 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 563 */           while (rs.next())
/*     */           {
/* 565 */             Date d = new Date(rs.getLong("COLLECTIONTIME"));
/* 566 */             long freemembytes = rs.getLong("FREEMEMORY");
/* 567 */             if (freemembytes != -1L) {
/* 568 */               long freemem = freemembytes / 1048576L;
/* 569 */               ts1.addOrUpdate(new Minute(d), freemem);
/*     */             }
/* 571 */             long freeswapspace = rs.getLong("FREESWAPSPACE");
/* 572 */             if (freeswapspace != -1L) {
/* 573 */               ts2.addOrUpdate(new Minute(d), freeswapspace);
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (SQLException e) {
/* 578 */           e.printStackTrace();
/*     */         }
/*     */         finally {
/* 581 */           closeResultSet(rs);
/*     */         }
/* 583 */         TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 584 */         tsc.addSeries(ts1);
/* 585 */         tsc.addSeries(ts2);
/*     */         
/* 587 */         return new SubSeriesDataset(tsc, new int[] { 0, 1 });
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 593 */     return null;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs) {
/* 597 */     if (rs != null) {
/*     */       try
/*     */       {
/* 600 */         AMConnectionPool.closeStatement(rs);
/*     */       } catch (Exception e) {
/* 602 */         System.out.println("Exception closing result set " + e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\jdk15\JDK15Graph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */