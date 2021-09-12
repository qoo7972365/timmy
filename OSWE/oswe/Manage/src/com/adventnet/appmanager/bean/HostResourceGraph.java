/*     */ package com.adventnet.appmanager.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class HostResourceGraph
/*     */   implements DatasetProducer, Serializable
/*     */ {
/*  31 */   String entity = "";
/*  32 */   String resourceId = "";
/*  33 */   String attributeId = "9641";
/*     */   
/*     */ 
/*  36 */   HashMap<String, HashMap<String, HashMap<Long, Integer>>> cpuUsagePolledData = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  46 */   public String[] cpuAttributes = { "RUNQUEUE", "BLOCKEDPROCS", "USERCPUTIME", "SYSTEMCPUTIME", "WAITCPUTIME", "IDLECPUTIME", "CPUUTIL", "INTRSPERSEC" };
/*     */   
/*     */   public void setResourceId(String resId)
/*     */   {
/*  50 */     this.resourceId = resId;
/*     */   }
/*     */   
/*     */   public void setAttributeId(String id) {
/*  54 */     this.attributeId = id;
/*     */   }
/*     */   
/*     */   public void setEntity(String entity) {
/*  58 */     this.entity = entity;
/*     */   }
/*     */   
/*     */   public void setCpuUsagePolledData(HashMap hm) {
/*  62 */     this.cpuUsagePolledData = hm;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/*  68 */     if ((this.entity.equalsIgnoreCase("cpu")) || (this.entity.equalsIgnoreCase("cpucore"))) {
/*  69 */       boolean isMultipleResId = this.resourceId.indexOf(",") > 0;
/*  70 */       if (isMultipleResId)
/*     */       {
/*     */ 
/*  73 */         if ((this.cpuUsagePolledData == null) || (this.cpuUsagePolledData.size() == 0)) {
/*  74 */           return null;
/*     */         }
/*  76 */         int arrsize = 0;
/*  77 */         TimeSeriesCollection tsc = new TimeSeriesCollection();
/*     */         try {
/*  79 */           StringTokenizer tokens = new StringTokenizer(this.resourceId, ",");
/*  80 */           while (tokens.hasMoreTokens()) {
/*  81 */             String coreResId = tokens.nextToken();
/*  82 */             HashMap<String, HashMap<Long, Integer>> attrbsValsMap = (HashMap)this.cpuUsagePolledData.get(coreResId);
/*  83 */             if (attrbsValsMap != null)
/*     */             {
/*     */ 
/*  86 */               String attribute = getName();
/*  87 */               HashMap<Long, Integer> data = (HashMap)attrbsValsMap.get(attribute);
/*  88 */               if ((data != null) && (data.size() != 0))
/*     */               {
/*     */ 
/*  91 */                 TimeSeries ts = null;
/*  92 */                 Iterator<Long> itrKey = data.keySet().iterator();
/*  93 */                 while (itrKey.hasNext()) {
/*  94 */                   Long dcTime = (Long)itrKey.next();
/*  95 */                   Number val = (Number)data.get(dcTime);
/*  96 */                   if ((val != null) && (val.longValue() != -1L)) {
/*  97 */                     if (ts == null) {
/*  98 */                       ts = new TimeSeries(FormatUtil.getString(getDisplayName(coreResId)), Minute.class);
/*     */                     }
/* 100 */                     ts.addOrUpdate(new Minute(new Date(dcTime.longValue())), (Number)data.get(dcTime));
/*     */                   }
/*     */                 }
/* 103 */                 if (ts != null) {
/* 104 */                   tsc.addSeries(ts);
/* 105 */                   arrsize++;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 110 */         } catch (Exception ex) { ex.printStackTrace();
/*     */         }
/* 112 */         int[] arr = new int[arrsize];
/* 113 */         for (int n = 0; n < arrsize; n++) {
/* 114 */           arr[n] = n;
/*     */         }
/* 116 */         return new SubSeriesDataset(tsc, arr);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 121 */         HashMap<String, HashMap<Long, Integer>> attrbsValMap = (HashMap)this.cpuUsagePolledData.get(this.resourceId);
/* 122 */         if ((attrbsValMap == null) || (attrbsValMap.size() == 0)) {
/* 123 */           return null;
/*     */         }
/* 125 */         int arrsize = 0;
/* 126 */         TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 127 */         for (int i = 0; i < this.cpuAttributes.length; i++)
/* 128 */           if ((!this.entity.equalsIgnoreCase("cpucore")) || ((i != 0) && (i != 1)))
/*     */           {
/*     */ 
/*     */ 
/* 132 */             HashMap<Long, Integer> data = (HashMap)attrbsValMap.get(this.cpuAttributes[i]);
/* 133 */             if ((data != null) && (data.size() > 0)) {
/* 134 */               Iterator<Long> itrKey = data.keySet().iterator();
/* 135 */               String title = "";
/* 136 */               if (i != 0)
/*     */               {
/*     */ 
/* 139 */                 if (i != 1)
/*     */                 {
/*     */ 
/* 142 */                   if (i == 2) {
/* 143 */                     title = "am.webclient.server.cpu.usage.user.percent";
/* 144 */                   } else if (i == 3) {
/* 145 */                     title = "am.webclient.server.cpu.usage.system.percent";
/* 146 */                   } else if (i == 4) {
/* 147 */                     title = "am.webclient.server.cpu.usage.wait.percent";
/* 148 */                   } else if (i == 5) {
/* 149 */                     title = "am.webclient.server.cpu.usage.idle.percent";
/* 150 */                   } else { if (i == 6) {
/*     */                       continue;
/*     */                     }
/* 153 */                     if (i == 7) {
/*     */                       continue;
/*     */                     }
/*     */                   }
/* 157 */                   TimeSeries ts = null;
/* 158 */                   while (itrKey.hasNext()) {
/* 159 */                     Long dcTime = (Long)itrKey.next();
/* 160 */                     Number val = (Number)data.get(dcTime);
/* 161 */                     if ((val != null) && (val.longValue() != -1L)) {
/* 162 */                       if (ts == null) {
/* 163 */                         ts = new TimeSeries(FormatUtil.getString(title), Minute.class);
/*     */                       }
/* 165 */                       ts.addOrUpdate(new Minute(new Date(dcTime.longValue())), (Number)data.get(dcTime));
/*     */                     }
/*     */                   }
/* 168 */                   if (ts != null) {
/* 169 */                     tsc.addSeries(ts);
/* 170 */                     arrsize++;
/*     */                   }
/*     */                 } }
/*     */             } }
/* 174 */         int[] arr = new int[arrsize];
/* 175 */         for (int n = 0; n < arrsize; n++) {
/* 176 */           arr[n] = n;
/*     */         }
/* 178 */         return new SubSeriesDataset(tsc, arr);
/*     */       } catch (Exception ex) {
/* 180 */         ex.printStackTrace();
/*     */         
/* 182 */         return null;
/*     */       }
/*     */     }
/* 185 */     if (this.entity.equalsIgnoreCase("Memory Util")) {
/* 186 */       long queryval = System.currentTimeMillis() - 21600000L;
/* 187 */       ResultSet rs = null;
/*     */       try {
/* 189 */         TimeSeries ts1 = null;
/* 190 */         TimeSeries ts2 = null;
/* 191 */         StringBuffer queryBuff = new StringBuffer();
/* 192 */         queryBuff.append("select SWAPMEMUTIL ,PHYMEMUTIL, COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID=");
/* 193 */         queryBuff.append(this.resourceId);
/* 194 */         queryBuff.append(" and COLLECTIONTIME>=");
/* 195 */         queryBuff.append(queryval);
/* 196 */         rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 197 */         String strPhysValue; while (rs.next()) {
/* 198 */           String strSwapValue = rs.getString("SWAPMEMUTIL");
/* 199 */           strPhysValue = rs.getString("PHYMEMUTIL");
/* 200 */           Date d = new Date(rs.getLong("COLLECTIONTIME"));
/*     */           
/* 202 */           if (!isNull(strPhysValue)) {
/* 203 */             if (ts1 == null) {
/* 204 */               ts1 = new TimeSeries(FormatUtil.getString("Physical Memory Utilization"), Minute.class);
/*     */             }
/* 206 */             ts1.addOrUpdate(new Minute(d), Long.parseLong(strPhysValue));
/*     */           }
/* 208 */           if (!isNull(strSwapValue)) {
/* 209 */             if (ts2 == null) {
/* 210 */               ts2 = new TimeSeries(FormatUtil.getString("Swap Memory Utilization"), Minute.class);
/*     */             }
/* 212 */             ts2.addOrUpdate(new Minute(d), Long.parseLong(strSwapValue));
/*     */           }
/*     */         }
/* 215 */         TimeSeriesCollection tsc = new TimeSeriesCollection();
/* 216 */         if ((ts1 != null) && (ts2 != null)) {
/* 217 */           tsc.addSeries(ts1);
/* 218 */           tsc.addSeries(ts2);
/* 219 */           return new SubSeriesDataset(tsc, new int[] { 0, 1 }); }
/* 220 */         if (ts1 != null) {
/* 221 */           tsc.addSeries(ts1);
/* 222 */           return new SubSeriesDataset(tsc, new int[] { 0 }); }
/* 223 */         if (ts2 != null) {
/* 224 */           tsc.addSeries(ts2);
/* 225 */           return new SubSeriesDataset(tsc, new int[] { 0 });
/*     */         }
/*     */       } catch (Exception ex) {
/* 228 */         ex.printStackTrace();
/*     */       } finally {
/* 230 */         if (rs != null) {
/* 231 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */     }
/* 235 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 243 */     String attrbName = "CPUUTIL";
/* 244 */     if (this.attributeId.equals("9642")) {
/* 245 */       attrbName = "USERCPUTIME";
/* 246 */     } else if (this.attributeId.equals("9643")) {
/* 247 */       attrbName = "SYSTEMCPUTIME";
/* 248 */     } else if (this.attributeId.equals("9644")) {
/* 249 */       attrbName = "WAITCPUTIME";
/* 250 */     } else if (this.attributeId.equals("9645")) {
/* 251 */       attrbName = "IDLECPUTIME";
/* 252 */     } else if (this.attributeId.equals("9646")) {
/* 253 */       attrbName = "INTRSPERSEC";
/*     */     }
/* 255 */     return attrbName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashMap<String, HashMap<String, HashMap<Long, Integer>>> getCPUPolledData()
/*     */   {
/* 266 */     ResultSet rs = null;
/* 267 */     HashMap<String, HashMap<String, HashMap<Long, Integer>>> allCpuCorePolledData = null;
/*     */     try {
/* 269 */       long currentTime = System.currentTimeMillis();
/* 270 */       long queryCondn = currentTime - 21600000L;
/* 271 */       allCpuCorePolledData = new HashMap();
/* 272 */       StringBuffer queryBuff = new StringBuffer();
/* 273 */       queryBuff.append("select RESOURCEID");
/* 274 */       for (int i = 0; i < this.cpuAttributes.length; i++) {
/* 275 */         queryBuff.append(",");
/* 276 */         queryBuff.append(this.cpuAttributes[i]);
/*     */       }
/* 278 */       queryBuff.append(",COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID in (");
/* 279 */       if (this.resourceId.endsWith(",")) {
/* 280 */         queryBuff.append(this.resourceId.substring(0, this.resourceId.length() - 1));
/*     */       } else {
/* 282 */         queryBuff.append(this.resourceId);
/*     */       }
/* 284 */       queryBuff.append(")");
/* 285 */       queryBuff.append(" and COLLECTIONTIME>=").append(queryCondn);
/* 286 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 287 */       while (rs.next()) {
/* 288 */         String resId = rs.getString("RESOURCEID");
/* 289 */         long dcTime = rs.getLong("COLLECTIONTIME");
/*     */         
/* 291 */         HashMap<String, HashMap<Long, Integer>> attrbsMap = (HashMap)allCpuCorePolledData.get(resId);
/* 292 */         if (attrbsMap == null) {
/* 293 */           attrbsMap = new HashMap();
/* 294 */           allCpuCorePolledData.put(resId, attrbsMap);
/*     */         }
/*     */         
/* 297 */         for (int i = 0; i < this.cpuAttributes.length; i++)
/* 298 */           if ((!this.entity.equalsIgnoreCase("cpucore")) || ((i != 0) && (i != 1)))
/*     */           {
/*     */ 
/* 301 */             HashMap<Long, Integer> valsMap = (HashMap)attrbsMap.get(this.cpuAttributes[i]);
/* 302 */             if (valsMap == null) {
/* 303 */               valsMap = new HashMap();
/* 304 */               attrbsMap.put(this.cpuAttributes[i], valsMap);
/*     */             }
/* 306 */             int value = rs.getInt(this.cpuAttributes[i]);
/* 307 */             valsMap.put(new Long(dcTime), new Integer(value));
/*     */           }
/*     */       }
/*     */     } catch (Exception ex) {
/* 311 */       ex.printStackTrace();
/*     */     } finally {
/* 313 */       if (rs != null) {
/* 314 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 317 */     return allCpuCorePolledData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashMap<String, HashMap<String, HashMap<Long, Integer>>> getCPUPolledDataForAttrb()
/*     */   {
/* 327 */     ResultSet rs = null;
/* 328 */     HashMap<String, HashMap<String, HashMap<Long, Integer>>> allCpuCorePolledData = null;
/*     */     try {
/* 330 */       long currentTime = System.currentTimeMillis();
/* 331 */       long queryCondn = currentTime - 21600000L;
/* 332 */       allCpuCorePolledData = new HashMap();
/* 333 */       String attrbName = getName();
/* 334 */       StringBuffer queryBuff = new StringBuffer();
/* 335 */       queryBuff.append("select RESOURCEID,");
/* 336 */       queryBuff.append(attrbName);
/* 337 */       queryBuff.append(",COLLECTIONTIME from HostCpuMemDataCollected where RESOURCEID in (");
/* 338 */       if (this.resourceId.endsWith(",")) {
/* 339 */         queryBuff.append(this.resourceId.substring(0, this.resourceId.length() - 1));
/*     */       } else {
/* 341 */         queryBuff.append(this.resourceId);
/*     */       }
/* 343 */       queryBuff.append(")");
/* 344 */       queryBuff.append(" and COLLECTIONTIME>=").append(queryCondn);
/* 345 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 346 */       while (rs.next()) {
/* 347 */         String resId = rs.getString("RESOURCEID");
/* 348 */         long dcTime = rs.getLong("COLLECTIONTIME");
/* 349 */         HashMap<String, HashMap<Long, Integer>> attrbsMap = (HashMap)allCpuCorePolledData.get(resId);
/* 350 */         if (attrbsMap == null) {
/* 351 */           attrbsMap = new HashMap();
/* 352 */           allCpuCorePolledData.put(resId, attrbsMap);
/*     */         }
/* 354 */         HashMap<Long, Integer> valsMap = (HashMap)attrbsMap.get(attrbName);
/* 355 */         if (valsMap == null) {
/* 356 */           valsMap = new HashMap();
/* 357 */           attrbsMap.put(attrbName, valsMap);
/*     */         }
/* 359 */         int value = rs.getInt(attrbName);
/* 360 */         valsMap.put(new Long(dcTime), new Integer(value));
/*     */       }
/*     */     } catch (Exception ex) {
/* 363 */       ex.printStackTrace();
/*     */     } finally {
/* 365 */       if (rs != null) {
/* 366 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/* 369 */     return allCpuCorePolledData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDisplayName(String resourceId)
/*     */   {
/* 378 */     String displayName = "CPU";
/* 379 */     ResultSet rs = null;
/*     */     try {
/* 381 */       String query = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceId;
/* 382 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 383 */       if (rs.next()) {
/* 384 */         displayName = rs.getString("DISPLAYNAME");
/*     */       }
/*     */       
/* 387 */       displayName = displayName.substring(displayName.lastIndexOf("-") + 1);
/*     */     } catch (Exception ex) {
/* 389 */       ex.printStackTrace();
/*     */     } finally {
/* 391 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 393 */     return displayName;
/*     */   }
/*     */   
/*     */   public boolean isNull(String s) {
/* 397 */     if (s == null) {
/* 398 */       return true;
/*     */     }
/* 400 */     s = s.toLowerCase();
/* 401 */     if (s.equals("null")) {
/* 402 */       return true;
/*     */     }
/* 404 */     return false;
/*     */   }
/*     */   
/* 407 */   public void reset() { this.resourceId = "";
/* 408 */     this.entity = "";
/* 409 */     this.attributeId = "9641";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\bean\HostResourceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */