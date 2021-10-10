/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.MemoryNotificationInfo;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
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
/*     */ public class MemoryNotifInfoCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final MemoryNotificationInfo memoryNotifInfo;
/*     */   private static final CompositeType memoryNotifInfoCompositeType;
/*     */   private static final String POOL_NAME = "poolName";
/*     */   private static final String USAGE = "usage";
/*     */   private static final String COUNT = "count";
/*     */   
/*     */   private MemoryNotifInfoCompositeData(MemoryNotificationInfo paramMemoryNotificationInfo) {
/*  44 */     this.memoryNotifInfo = paramMemoryNotificationInfo;
/*     */   }
/*     */   
/*     */   public MemoryNotificationInfo getMemoryNotifInfo() {
/*  48 */     return this.memoryNotifInfo;
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(MemoryNotificationInfo paramMemoryNotificationInfo) {
/*  52 */     MemoryNotifInfoCompositeData memoryNotifInfoCompositeData = new MemoryNotifInfoCompositeData(paramMemoryNotificationInfo);
/*     */     
/*  54 */     return memoryNotifInfoCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  63 */     Object[] arrayOfObject = { this.memoryNotifInfo.getPoolName(), MemoryUsageCompositeData.toCompositeData(this.memoryNotifInfo.getUsage()), new Long(this.memoryNotifInfo.getCount()) };
/*     */ 
/*     */     
/*     */     try {
/*  67 */       return new CompositeDataSupport(memoryNotifInfoCompositeType, memoryNotifInfoItemNames, arrayOfObject);
/*     */     
/*     */     }
/*  70 */     catch (OpenDataException openDataException) {
/*     */       
/*  72 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  80 */       memoryNotifInfoCompositeType = (CompositeType)MappedMXBeanType.toOpenType(MemoryNotificationInfo.class);
/*  81 */     } catch (OpenDataException openDataException) {
/*     */       
/*  83 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final String[] memoryNotifInfoItemNames = new String[] { "poolName", "usage", "count" };
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1805123446483771291L;
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPoolName(CompositeData paramCompositeData) {
/*  98 */     String str = getString(paramCompositeData, "poolName");
/*  99 */     if (str == null) {
/* 100 */       throw new IllegalArgumentException("Invalid composite data: Attribute poolName has null value");
/*     */     }
/*     */     
/* 103 */     return str;
/*     */   }
/*     */   
/*     */   public static MemoryUsage getUsage(CompositeData paramCompositeData) {
/* 107 */     CompositeData compositeData = (CompositeData)paramCompositeData.get("usage");
/* 108 */     return MemoryUsage.from(compositeData);
/*     */   }
/*     */   
/*     */   public static long getCount(CompositeData paramCompositeData) {
/* 112 */     return getLong(paramCompositeData, "count");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCompositeData(CompositeData paramCompositeData) {
/* 120 */     if (paramCompositeData == null) {
/* 121 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 124 */     if (!isTypeMatched(memoryNotifInfoCompositeType, paramCompositeData.getCompositeType()))
/* 125 */       throw new IllegalArgumentException("Unexpected composite type for MemoryNotificationInfo"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MemoryNotifInfoCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */