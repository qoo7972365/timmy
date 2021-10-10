/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class XPropertyCache
/*     */ {
/*     */   static class PropertyCacheEntry
/*     */   {
/*     */     private final int format;
/*     */     private final int numberOfItems;
/*     */     private final long bytesAfter;
/*     */     private final long data;
/*     */     private final int dataLength;
/*     */     
/*     */     public PropertyCacheEntry(int param1Int1, int param1Int2, long param1Long1, long param1Long2, int param1Int3) {
/*  49 */       this.format = param1Int1;
/*  50 */       this.numberOfItems = param1Int2;
/*  51 */       this.bytesAfter = param1Long1;
/*  52 */       this.data = XlibWrapper.unsafe.allocateMemory(param1Int3);
/*  53 */       this.dataLength = param1Int3;
/*  54 */       XlibWrapper.memcpy(this.data, param1Long2, param1Int3);
/*     */     }
/*     */     
/*     */     public int getFormat() {
/*  58 */       return this.format;
/*     */     }
/*     */     
/*     */     public int getNumberOfItems() {
/*  62 */       return this.numberOfItems;
/*     */     }
/*     */     
/*     */     public long getBytesAfter() {
/*  66 */       return this.bytesAfter;
/*     */     }
/*     */     
/*     */     public long getData() {
/*  70 */       return this.data;
/*     */     }
/*     */     
/*     */     public int getDataLength() {
/*  74 */       return this.dataLength;
/*     */     }
/*     */   }
/*     */   
/*  78 */   private static Map<Long, Map<XAtom, PropertyCacheEntry>> windowToMap = new HashMap<>();
/*     */   
/*     */   public static boolean isCached(long paramLong, XAtom paramXAtom) {
/*  81 */     Map map = windowToMap.get(Long.valueOf(paramLong));
/*  82 */     if (map != null) {
/*  83 */       return map.containsKey(paramXAtom);
/*     */     }
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PropertyCacheEntry getCacheEntry(long paramLong, XAtom paramXAtom) {
/*  90 */     Map map = windowToMap.get(Long.valueOf(paramLong));
/*  91 */     if (map != null) {
/*  92 */       return (PropertyCacheEntry)map.get(paramXAtom);
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void storeCache(PropertyCacheEntry paramPropertyCacheEntry, long paramLong, XAtom paramXAtom) {
/*  99 */     Map<Object, Object> map = (Map)windowToMap.get(Long.valueOf(paramLong));
/* 100 */     if (map == null) {
/* 101 */       map = new HashMap<>();
/* 102 */       windowToMap.put(Long.valueOf(paramLong), map);
/*     */     } 
/* 104 */     map.put(paramXAtom, paramPropertyCacheEntry);
/*     */   }
/*     */   
/*     */   public static void clearCache(long paramLong) {
/* 108 */     windowToMap.remove(Long.valueOf(paramLong));
/*     */   }
/*     */   
/*     */   public static void clearCache(long paramLong, XAtom paramXAtom) {
/* 112 */     Map map = windowToMap.get(Long.valueOf(paramLong));
/* 113 */     if (map != null) {
/* 114 */       map.remove(paramXAtom);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isCachingSupported() {
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XPropertyCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */