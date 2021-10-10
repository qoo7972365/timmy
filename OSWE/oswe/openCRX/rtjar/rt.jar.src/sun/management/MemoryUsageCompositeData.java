/*     */ package sun.management;
/*     */ 
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
/*     */ public class MemoryUsageCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final MemoryUsage usage;
/*     */   private static final CompositeType memoryUsageCompositeType;
/*     */   private static final String INIT = "init";
/*     */   private static final String USED = "used";
/*     */   private static final String COMMITTED = "committed";
/*     */   private static final String MAX = "max";
/*     */   
/*     */   private MemoryUsageCompositeData(MemoryUsage paramMemoryUsage) {
/*  43 */     this.usage = paramMemoryUsage;
/*     */   }
/*     */   
/*     */   public MemoryUsage getMemoryUsage() {
/*  47 */     return this.usage;
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(MemoryUsage paramMemoryUsage) {
/*  51 */     MemoryUsageCompositeData memoryUsageCompositeData = new MemoryUsageCompositeData(paramMemoryUsage);
/*  52 */     return memoryUsageCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  62 */     Object[] arrayOfObject = { new Long(this.usage.getInit()), new Long(this.usage.getUsed()), new Long(this.usage.getCommitted()), new Long(this.usage.getMax()) };
/*     */ 
/*     */     
/*     */     try {
/*  66 */       return new CompositeDataSupport(memoryUsageCompositeType, memoryUsageItemNames, arrayOfObject);
/*     */     
/*     */     }
/*  69 */     catch (OpenDataException openDataException) {
/*     */       
/*  71 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  79 */       memoryUsageCompositeType = (CompositeType)MappedMXBeanType.toOpenType(MemoryUsage.class);
/*  80 */     } catch (OpenDataException openDataException) {
/*     */       
/*  82 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static CompositeType getMemoryUsageCompositeType() {
/*  87 */     return memoryUsageCompositeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final String[] memoryUsageItemNames = new String[] { "init", "used", "committed", "max" };
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -8504291541083874143L;
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getInit(CompositeData paramCompositeData) {
/* 103 */     return getLong(paramCompositeData, "init");
/*     */   }
/*     */   public static long getUsed(CompositeData paramCompositeData) {
/* 106 */     return getLong(paramCompositeData, "used");
/*     */   }
/*     */   public static long getCommitted(CompositeData paramCompositeData) {
/* 109 */     return getLong(paramCompositeData, "committed");
/*     */   }
/*     */   public static long getMax(CompositeData paramCompositeData) {
/* 112 */     return getLong(paramCompositeData, "max");
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
/* 124 */     if (!isTypeMatched(memoryUsageCompositeType, paramCompositeData.getCompositeType()))
/* 125 */       throw new IllegalArgumentException("Unexpected composite type for MemoryUsage"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MemoryUsageCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */