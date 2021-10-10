/*     */ package com.sun.management;
/*     */ 
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataView;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import jdk.Exported;
/*     */ import sun.management.GcInfoBuilder;
/*     */ import sun.management.GcInfoCompositeData;
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
/*     */ @Exported
/*     */ public class GcInfo
/*     */   implements CompositeData, CompositeDataView
/*     */ {
/*     */   private final long index;
/*     */   private final long startTime;
/*     */   private final long endTime;
/*     */   private final Map<String, MemoryUsage> usageBeforeGc;
/*     */   private final Map<String, MemoryUsage> usageAfterGc;
/*     */   private final Object[] extAttributes;
/*     */   private final CompositeData cdata;
/*     */   private final GcInfoBuilder builder;
/*     */   
/*     */   private GcInfo(GcInfoBuilder paramGcInfoBuilder, long paramLong1, long paramLong2, long paramLong3, MemoryUsage[] paramArrayOfMemoryUsage1, MemoryUsage[] paramArrayOfMemoryUsage2, Object[] paramArrayOfObject) {
/*  83 */     this.builder = paramGcInfoBuilder;
/*  84 */     this.index = paramLong1;
/*  85 */     this.startTime = paramLong2;
/*  86 */     this.endTime = paramLong3;
/*  87 */     String[] arrayOfString = paramGcInfoBuilder.getPoolNames();
/*  88 */     this.usageBeforeGc = new HashMap<>(arrayOfString.length);
/*  89 */     this.usageAfterGc = new HashMap<>(arrayOfString.length);
/*  90 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  91 */       this.usageBeforeGc.put(arrayOfString[b], paramArrayOfMemoryUsage1[b]);
/*  92 */       this.usageAfterGc.put(arrayOfString[b], paramArrayOfMemoryUsage2[b]);
/*     */     } 
/*  94 */     this.extAttributes = paramArrayOfObject;
/*  95 */     this.cdata = (CompositeData)new GcInfoCompositeData(this, paramGcInfoBuilder, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   private GcInfo(CompositeData paramCompositeData) {
/*  99 */     GcInfoCompositeData.validateCompositeData(paramCompositeData);
/*     */     
/* 101 */     this.index = GcInfoCompositeData.getId(paramCompositeData);
/* 102 */     this.startTime = GcInfoCompositeData.getStartTime(paramCompositeData);
/* 103 */     this.endTime = GcInfoCompositeData.getEndTime(paramCompositeData);
/* 104 */     this.usageBeforeGc = GcInfoCompositeData.getMemoryUsageBeforeGc(paramCompositeData);
/* 105 */     this.usageAfterGc = GcInfoCompositeData.getMemoryUsageAfterGc(paramCompositeData);
/* 106 */     this.extAttributes = null;
/* 107 */     this.builder = null;
/* 108 */     this.cdata = paramCompositeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 119 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getStartTime() {
/* 129 */     return this.startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getEndTime() {
/* 139 */     return this.endTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDuration() {
/* 148 */     return this.endTime - this.startTime;
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
/*     */   public Map<String, MemoryUsage> getMemoryUsageBeforeGc() {
/* 163 */     return Collections.unmodifiableMap(this.usageBeforeGc);
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
/*     */   public Map<String, MemoryUsage> getMemoryUsageAfterGc() {
/* 178 */     return Collections.unmodifiableMap(this.usageAfterGc);
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
/*     */ 
/*     */   
/*     */   public static GcInfo from(CompositeData paramCompositeData) {
/* 225 */     if (paramCompositeData == null) {
/* 226 */       return null;
/*     */     }
/*     */     
/* 229 */     if (paramCompositeData instanceof GcInfoCompositeData) {
/* 230 */       return ((GcInfoCompositeData)paramCompositeData).getGcInfo();
/*     */     }
/* 232 */     return new GcInfo(paramCompositeData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(String paramString) {
/* 239 */     return this.cdata.containsKey(paramString);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 243 */     return this.cdata.containsValue(paramObject);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 247 */     return this.cdata.equals(paramObject);
/*     */   }
/*     */   
/*     */   public Object get(String paramString) {
/* 251 */     return this.cdata.get(paramString);
/*     */   }
/*     */   
/*     */   public Object[] getAll(String[] paramArrayOfString) {
/* 255 */     return this.cdata.getAll(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public CompositeType getCompositeType() {
/* 259 */     return this.cdata.getCompositeType();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 263 */     return this.cdata.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 267 */     return this.cdata.toString();
/*     */   }
/*     */   
/*     */   public Collection values() {
/* 271 */     return this.cdata.values();
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
/*     */   public CompositeData toCompositeData(CompositeType paramCompositeType) {
/* 287 */     return this.cdata;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/management/GcInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */