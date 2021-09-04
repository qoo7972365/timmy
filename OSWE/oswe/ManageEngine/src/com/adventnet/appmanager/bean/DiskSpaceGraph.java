/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
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
/*     */ public class DiskSpaceGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  29 */   private String resourcename = "default";
/*  30 */   private String category = "default";
/*  31 */   private String entity = "default";
/*  32 */   private String resourceId = "default";
/*  33 */   private String toremove = "DiskUtilization-";
/*  34 */   private String toremoveI18N = FormatUtil.getString("DiskUtilization") + "-";
/*     */   private AMConnectionPool pool;
/*     */   private ResultSet rs;
/*     */   private ResultSet free;
/*     */   private ResultSet used;
/*  39 */   final String[] seriesNames = { FormatUtil.getString("am.webclient.oracle.graph.usedspace"), FormatUtil.getString("am.webclient.oracle.graph.freespace") };
/*     */   DefaultIntervalCategoryDataset ds;
/*     */   
/*     */   public DiskSpaceGraph() {
/*  43 */     this.pool = AMConnectionPool.getInstance();
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException {
/*  47 */     long maxtime = 0L;
/*  48 */     long freespace = 0L;
/*  49 */     long usedspace = 0L;
/*  50 */     long totalspace = 0L;
/*  51 */     String query = "select max(COLLECTIONTIME) from HostDiskUtilDataCollected,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and HostDiskUtilDataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID";
/*     */     try {
/*  53 */       this.rs = AMConnectionPool.executeQueryStmt(query);
/*     */     }
/*     */     catch (Exception e) {
/*  56 */       e.printStackTrace();
/*  57 */       return null;
/*     */     }
/*     */     try {
/*  60 */       if (this.rs.next())
/*     */       {
/*  62 */         maxtime = this.rs.getLong(1);
/*     */       }
/*  64 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  68 */       return null;
/*     */     }
/*     */     
/*  71 */     String tsquery = "select RESOURCENAME, DISKUTIL from HostDiskUtilDataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskUtilDataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME=" + maxtime;
/*     */     try {
/*  73 */       this.rs = AMConnectionPool.executeQueryStmt(tsquery);
/*  74 */       String iname = null;
/*  75 */       Vector v = new Vector();
/*  76 */       while (this.rs.next()) {
/*  77 */         iname = this.rs.getString(1);
/*  78 */         v.add(iname.substring(iname.indexOf(this.toremove) + this.toremove.length()));
/*     */       }
/*  80 */       this.rs.close();
/*  81 */       String[] categories = new String[v.size()];
/*  82 */       Long[][] startValues = new Long[this.seriesNames.length][categories.length];
/*  83 */       Long[][] endValues = new Long[this.seriesNames.length][categories.length];
/*     */       
/*  85 */       this.rs = AMConnectionPool.executeQueryStmt(tsquery);
/*  86 */       int i = 0;
/*  87 */       while (this.rs.next())
/*     */       {
/*  89 */         categories[i] = ((String)v.get(i));
/*  90 */         startValues[0][i] = new Long(0L);
/*  91 */         endValues[0][i] = new Long(this.rs.getLong(2));
/*  92 */         startValues[1][i] = new Long(0L);
/*  93 */         endValues[1][i] = new Long(100L - this.rs.getLong(2));
/*  94 */         i += 1;
/*     */       }
/*  96 */       this.rs.close();
/*  97 */       this.ds = new DefaultIntervalCategoryDataset(this.seriesNames, categories, startValues, endValues);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 101 */       e.printStackTrace();
/* 102 */       return null;
/*     */     }
/* 104 */     return this.ds;
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 112 */     return "tablespacegraph";
/*     */   }
/*     */   
/*     */   public String getresourceName()
/*     */   {
/* 117 */     return this.resourcename;
/*     */   }
/*     */   
/*     */   public void setresourceName(String resource) {
/* 121 */     this.resourcename = resource;
/*     */   }
/*     */   
/*     */   public String getResourceId() {
/* 125 */     return this.resourceId;
/*     */   }
/*     */   
/*     */   public void setResourceId(String resource) {
/* 129 */     this.resourceId = resource;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 133 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String cat) {
/* 137 */     this.category = cat;
/*     */   }
/*     */   
/*     */   public String getEntity() {
/* 141 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void setEntity(String ent) {
/* 145 */     this.entity = ent;
/*     */   }
/*     */   
/*     */   public Hashtable getDiskData(String resourcename, long time, String resourceid)
/*     */   {
/* 150 */     Hashtable h = new Hashtable();
/*     */     try
/*     */     {
/* 153 */       String totaldiskquery = "select  HostDiskUtilDataCollected.RESOURCEID, DISKUTILMB, DISKUTIL , FREEDISKUTILMB, FREEDISKUTIL,DISKTOTALSIZE from HostDiskUtilDataCollected where HostDiskUtilDataCollected.RESOURCEID=" + this.resourceId + " and COLLECTIONTIME=" + time;
/* 154 */       this.rs = AMConnectionPool.executeQueryStmt(totaldiskquery);
/* 155 */       while (this.rs.next())
/*     */       {
/* 157 */         String entity = "Total Disk";
/* 158 */         String curvalue = this.rs.getString(3);
/* 159 */         String curvalueMB = this.rs.getString(2);
/* 160 */         String freecurvalue = this.rs.getString(5);
/* 161 */         String freecurvalueMB = this.rs.getString(4);
/* 162 */         String totalDiskSize = this.rs.getString(6);
/* 163 */         if (curvalue == null) {
/* 164 */           curvalue = "-";
/*     */         }
/* 166 */         if (curvalueMB == null) {
/* 167 */           curvalueMB = "-";
/*     */         }
/* 169 */         if (freecurvalue == null) {
/* 170 */           freecurvalue = "-";
/*     */         }
/* 172 */         if (freecurvalueMB == null) {
/* 173 */           freecurvalueMB = "-";
/*     */         }
/* 175 */         if (totalDiskSize == null) {
/* 176 */           totalDiskSize = "-";
/*     */         }
/* 178 */         Properties p = new Properties();
/* 179 */         p.setProperty("name", entity);
/* 180 */         p.setProperty("CURVALUE", curvalue);
/* 181 */         p.setProperty("CURVALUEMB", curvalueMB);
/* 182 */         p.setProperty("FREECURVALUE", freecurvalue);
/* 183 */         p.setProperty("FREECURVALUEMB", freecurvalueMB);
/* 184 */         p.setProperty("DISKTOTALSIZE", totalDiskSize);
/* 185 */         p.setProperty("ID", this.rs.getString(1));
/* 186 */         h.put("Total Disk Space", p);
/*     */       }
/* 188 */       this.rs.close();
/* 189 */       String totalmaxvaluequery = "select max(DISKUTIL) from HostDiskUtilDataCollected where HostDiskUtilDataCollected.RESOURCEID=" + this.resourceId + " and COLLECTIONTIME <= " + time + " and COLLECTIONTIME >= " + (time - 3600000L);
/* 190 */       this.rs = AMConnectionPool.executeQueryStmt(totalmaxvaluequery);
/* 191 */       while (this.rs.next()) {
/* 192 */         String entity = this.rs.getString(1);
/* 193 */         Properties p = (Properties)h.get("Total Disk Space");
/* 194 */         if (p != null)
/*     */         {
/* 196 */           long util = this.rs.getLong(1);
/* 197 */           if (util < 100L)
/*     */           {
/* 199 */             p.setProperty("MAXVALUE", String.valueOf(util));
/*     */           }
/*     */           else
/*     */           {
/* 203 */             p.setProperty("MAXVALUE", "100");
/*     */           }
/* 205 */           h.put("Total Disk Space", p);
/*     */         }
/*     */       }
/* 208 */       this.rs.close();
/*     */       
/* 210 */       String attidsquery = "select ATTRIBUTEID from AM_ATTRIBUTES,AM_ManagedObject where AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=" + this.resourceId + " and ATTRIBUTE in ('Total DiskUtilizationMB','Total DiskUtilization','Total FreeDiskSpaceMB','Total FreeDiskSpace','Total DiskSize')";
/* 211 */       this.rs = AMConnectionPool.executeQueryStmt(attidsquery);
/* 212 */       String attributes = "";
/* 213 */       Properties pr = (Properties)h.get("Total Disk Space");
/* 214 */       if (pr != null)
/*     */       {
/* 216 */         while (this.rs.next())
/*     */         {
/* 218 */           if (!attributes.equals(""))
/*     */           {
/* 220 */             attributes = attributes + ":";
/*     */           }
/* 222 */           attributes = attributes + this.rs.getString(1);
/*     */         }
/* 224 */         pr.setProperty("attr", attributes);
/*     */         
/* 226 */         h.put("Total Disk Space", pr);
/*     */       }
/*     */       
/* 229 */       this.rs.close();
/*     */       
/* 231 */       String diskquery = "select DISPLAYNAME, HostDiskUtilDataCollected.RESOURCEID, DISKUTILMB, DISKUTIL , FREEDISKUTILMB, FREEDISKUTIL,DISKTOTALSIZE from HostDiskUtilDataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskUtilDataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME=" + time;
/* 232 */       this.rs = AMConnectionPool.executeQueryStmt(diskquery);
/* 233 */       while (this.rs.next())
/*     */       {
/* 235 */         String entity = this.rs.getString(1);
/* 236 */         entity = entity.substring(entity.indexOf(this.toremove) + this.toremove.length());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 241 */         String curvalue = this.rs.getString(4);
/* 242 */         String curvalueMB = this.rs.getString(3);
/* 243 */         String freecurvalue = this.rs.getString(6);
/* 244 */         String freecurvalueMB = this.rs.getString(5);
/* 245 */         String totalDiskSize = this.rs.getString(7);
/* 246 */         if (curvalue == null) {
/* 247 */           curvalue = "-";
/*     */         }
/* 249 */         if (curvalueMB == null) {
/* 250 */           curvalueMB = "-";
/*     */         }
/* 252 */         if (freecurvalue == null) {
/* 253 */           freecurvalue = "-";
/*     */         }
/* 255 */         if (freecurvalueMB == null) {
/* 256 */           freecurvalueMB = "-";
/*     */         }
/* 258 */         if (totalDiskSize == null) {
/* 259 */           totalDiskSize = "-";
/*     */         }
/* 261 */         Properties p = new Properties();
/* 262 */         p.setProperty("name", entity);
/* 263 */         p.setProperty("CURVALUE", curvalue);
/* 264 */         p.setProperty("CURVALUEMB", curvalueMB);
/* 265 */         p.setProperty("FREECURVALUE", freecurvalue);
/* 266 */         p.setProperty("FREECURVALUEMB", freecurvalueMB);
/* 267 */         p.setProperty("DISKTOTALSIZE", totalDiskSize);
/* 268 */         p.setProperty("ID", this.rs.getString(2));
/* 269 */         h.put(entity, p);
/*     */       }
/* 271 */       this.rs.close();
/*     */       
/* 273 */       String maxvaluequery = "select DISPLAYNAME, max(DISKUTIL) from HostDiskUtilDataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskUtilDataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME <= " + time + " and COLLECTIONTIME >= " + (time - 3600000L) + " group by DISPLAYNAME";
/* 274 */       this.rs = AMConnectionPool.executeQueryStmt(maxvaluequery);
/* 275 */       while (this.rs.next()) {
/* 276 */         String entity = this.rs.getString(1);
/* 277 */         Properties p = (Properties)h.get(entity.substring(entity.indexOf(this.toremove) + this.toremove.length()));
/* 278 */         if (p != null)
/*     */         {
/* 280 */           long util = this.rs.getLong(2);
/* 281 */           if (util < 100L)
/*     */           {
/* 283 */             p.setProperty("MAXVALUE", String.valueOf(this.rs.getLong(2)));
/*     */           }
/*     */           else
/*     */           {
/* 287 */             p.setProperty("MAXVALUE", "100");
/*     */           }
/* 289 */           entity = entity.substring(entity.indexOf(this.toremove) + this.toremove.length());
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 294 */           h.put(entity, p);
/*     */         }
/*     */       }
/* 297 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 300 */       e.printStackTrace();
/* 301 */       AMLog.fatal("HostResource : Error in Fetching the data for Disk ", e);
/*     */     }
/* 303 */     return h;
/*     */   }
/*     */   
/*     */   public Hashtable getDiskDataForDisplay(String resourcename, long time, String resourceid, String resourceType)
/*     */   {
/* 308 */     Hashtable h = new Hashtable();
/*     */     try
/*     */     {
/* 311 */       String totaldiskquery = "select  HostDiskUtilDataCollected.RESOURCEID, DISKUTILMB, DISKUTIL , FREEDISKUTILMB, FREEDISKUTIL,DISKTOTALSIZE from HostDiskUtilDataCollected where HostDiskUtilDataCollected.RESOURCEID=" + this.resourceId + " and COLLECTIONTIME=" + time;
/* 312 */       this.rs = AMConnectionPool.executeQueryStmt(totaldiskquery);
/* 313 */       while (this.rs.next())
/*     */       {
/* 315 */         String entity = "Total Disk";
/* 316 */         String curvalue = this.rs.getString(3);
/* 317 */         String curvalueMB = this.rs.getString(2);
/* 318 */         String freecurvalue = this.rs.getString(5);
/* 319 */         String freecurvalueMB = this.rs.getString(4);
/* 320 */         String totalDiskSize = this.rs.getString(6);
/* 321 */         if (curvalue == null) {
/* 322 */           curvalue = "-";
/*     */         }
/* 324 */         if (curvalueMB == null) {
/* 325 */           curvalueMB = "-";
/*     */         }
/* 327 */         if (freecurvalue == null) {
/* 328 */           freecurvalue = "-";
/*     */         }
/* 330 */         if (freecurvalueMB == null) {
/* 331 */           freecurvalueMB = "-";
/*     */         }
/* 333 */         if (totalDiskSize == null) {
/* 334 */           totalDiskSize = "-";
/*     */         }
/* 336 */         Properties p = new Properties();
/* 337 */         p.setProperty("name", entity);
/* 338 */         p.setProperty("CURVALUE", curvalue);
/* 339 */         p.setProperty("CURVALUEMB", curvalueMB);
/* 340 */         p.setProperty("FREECURVALUE", freecurvalue);
/* 341 */         p.setProperty("FREECURVALUEMB", freecurvalueMB);
/* 342 */         p.setProperty("DISKTOTALSIZE", totalDiskSize);
/* 343 */         p.setProperty("ID", this.rs.getString(1));
/* 344 */         h.put("Total Disk Space", p);
/*     */       }
/* 346 */       this.rs.close();
/*     */       
/*     */ 
/* 349 */       Properties pr = (Properties)h.get("Total Disk Space");
/* 350 */       if ((pr != null) && (pr.size() > 0)) {
/* 351 */         String attributes = "";
/* 352 */         String[] diskAttrbs = { "Total DiskUtilizationMB", "Total DiskUtilization", "Total FreeDiskSpaceMB", "Total FreeDiskSpace", "Total DiskSize" };
/* 353 */         String[] diskAttrbsId = AMAttributesCache.getAttributesId(diskAttrbs, resourceType);
/* 354 */         if ((diskAttrbsId != null) && (diskAttrbsId.length > 0)) {
/* 355 */           for (int i = 0; i < diskAttrbsId.length; i++) {
/* 356 */             if (i == diskAttrbsId.length - 1) {
/* 357 */               attributes = attributes + diskAttrbsId[i];
/*     */             }
/*     */             else
/* 360 */               attributes = attributes + diskAttrbsId[i] + ":";
/*     */           }
/* 362 */           pr.setProperty("attr", attributes);
/* 363 */           h.put("Total Disk Space", pr);
/*     */         } else {
/* 365 */           String attidsquery = "select ATTRIBUTEID from AM_ATTRIBUTES,AM_ManagedObject where AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID=" + this.resourceId + " and ATTRIBUTE in ('Total DiskUtilizationMB','Total DiskUtilization','Total FreeDiskSpaceMB','Total FreeDiskSpace','Total DiskSize')";
/* 366 */           this.rs = AMConnectionPool.executeQueryStmt(attidsquery);
/* 367 */           while (this.rs.next())
/*     */           {
/* 369 */             if (!attributes.equals(""))
/*     */             {
/* 371 */               attributes = attributes + ":";
/*     */             }
/* 373 */             attributes = attributes + this.rs.getString(1);
/*     */           }
/* 375 */           pr.setProperty("attr", attributes);
/* 376 */           h.put("Total Disk Space", pr);
/* 377 */           this.rs.close();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 382 */       String diskquery = "select DISPLAYNAME, HostDiskUtilDataCollected.RESOURCEID, DISKUTILMB, DISKUTIL , FREEDISKUTILMB, FREEDISKUTIL,DISKTOTALSIZE from HostDiskUtilDataCollected,AM_PARENTCHILDMAPPER,AM_ManagedObject where HostDiskUtilDataCollected.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + this.resourceId + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and COLLECTIONTIME=" + time;
/* 383 */       this.rs = AMConnectionPool.executeQueryStmt(diskquery);
/* 384 */       while (this.rs.next())
/*     */       {
/* 386 */         String entity = this.rs.getString(1);
/* 387 */         entity = entity.substring(entity.indexOf(this.toremoveI18N) + this.toremoveI18N.length());
/* 388 */         String curvalue = this.rs.getString(4);
/* 389 */         String curvalueMB = this.rs.getString(3);
/* 390 */         String freecurvalue = this.rs.getString(6);
/* 391 */         String freecurvalueMB = this.rs.getString(5);
/* 392 */         String totalDiskSize = this.rs.getString(7);
/* 393 */         if (curvalue == null) {
/* 394 */           curvalue = "-";
/*     */         }
/* 396 */         if (curvalueMB == null) {
/* 397 */           curvalueMB = "-";
/*     */         }
/* 399 */         if (freecurvalue == null) {
/* 400 */           freecurvalue = "-";
/*     */         }
/* 402 */         if (freecurvalueMB == null) {
/* 403 */           freecurvalueMB = "-";
/*     */         }
/* 405 */         if (totalDiskSize == null) {
/* 406 */           totalDiskSize = "-";
/*     */         }
/* 408 */         Properties p = new Properties();
/* 409 */         p.setProperty("name", entity);
/* 410 */         p.setProperty("CURVALUE", curvalue);
/* 411 */         p.setProperty("CURVALUEMB", curvalueMB);
/* 412 */         p.setProperty("FREECURVALUE", freecurvalue);
/* 413 */         p.setProperty("FREECURVALUEMB", freecurvalueMB);
/* 414 */         p.setProperty("DISKTOTALSIZE", totalDiskSize);
/* 415 */         p.setProperty("ID", this.rs.getString(2));
/* 416 */         h.put(entity, p);
/*     */       }
/* 418 */       this.rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 421 */       e.printStackTrace();
/* 422 */       AMLog.fatal("HostResource : Error in Fetching the data for Disk ", e);
/*     */     }
/* 424 */     return h;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\DiskSpaceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */