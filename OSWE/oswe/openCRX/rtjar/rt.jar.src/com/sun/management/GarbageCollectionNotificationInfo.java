/*     */ package com.sun.management;
/*     */ 
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataView;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import jdk.Exported;
/*     */ import sun.management.GarbageCollectionNotifInfoCompositeData;
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
/*     */ public class GarbageCollectionNotificationInfo
/*     */   implements CompositeDataView
/*     */ {
/*     */   private final String gcName;
/*     */   private final String gcAction;
/*     */   private final String gcCause;
/*     */   private final GcInfo gcInfo;
/*     */   private final CompositeData cdata;
/*     */   public static final String GARBAGE_COLLECTION_NOTIFICATION = "com.sun.management.gc.notification";
/*     */   
/*     */   public GarbageCollectionNotificationInfo(String paramString1, String paramString2, String paramString3, GcInfo paramGcInfo) {
/* 119 */     if (paramString1 == null) {
/* 120 */       throw new NullPointerException("Null gcName");
/*     */     }
/* 122 */     if (paramString2 == null) {
/* 123 */       throw new NullPointerException("Null gcAction");
/*     */     }
/* 125 */     if (paramString3 == null) {
/* 126 */       throw new NullPointerException("Null gcCause");
/*     */     }
/* 128 */     this.gcName = paramString1;
/* 129 */     this.gcAction = paramString2;
/* 130 */     this.gcCause = paramString3;
/* 131 */     this.gcInfo = paramGcInfo;
/* 132 */     this.cdata = (CompositeData)new GarbageCollectionNotifInfoCompositeData(this);
/*     */   }
/*     */   
/*     */   GarbageCollectionNotificationInfo(CompositeData paramCompositeData) {
/* 136 */     GarbageCollectionNotifInfoCompositeData.validateCompositeData(paramCompositeData);
/*     */     
/* 138 */     this.gcName = GarbageCollectionNotifInfoCompositeData.getGcName(paramCompositeData);
/* 139 */     this.gcAction = GarbageCollectionNotifInfoCompositeData.getGcAction(paramCompositeData);
/* 140 */     this.gcCause = GarbageCollectionNotifInfoCompositeData.getGcCause(paramCompositeData);
/* 141 */     this.gcInfo = GarbageCollectionNotifInfoCompositeData.getGcInfo(paramCompositeData);
/* 142 */     this.cdata = paramCompositeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGcName() {
/* 151 */     return this.gcName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGcAction() {
/* 160 */     return this.gcAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGcCause() {
/* 169 */     return this.gcCause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GcInfo getGcInfo() {
/* 179 */     return this.gcInfo;
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
/*     */   public static GarbageCollectionNotificationInfo from(CompositeData paramCompositeData) {
/* 223 */     if (paramCompositeData == null) {
/* 224 */       return null;
/*     */     }
/*     */     
/* 227 */     if (paramCompositeData instanceof GarbageCollectionNotifInfoCompositeData) {
/* 228 */       return ((GarbageCollectionNotifInfoCompositeData)paramCompositeData).getGarbageCollectionNotifInfo();
/*     */     }
/* 230 */     return new GarbageCollectionNotificationInfo(paramCompositeData);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeData toCompositeData(CompositeType paramCompositeType) {
/* 235 */     return this.cdata;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/management/GarbageCollectionNotificationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */