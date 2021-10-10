/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.LockInfo;
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
/*     */ 
/*     */ public class LockInfoCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final LockInfo lock;
/*     */   private static final CompositeType lockInfoCompositeType;
/*     */   private static final String CLASS_NAME = "className";
/*     */   private static final String IDENTITY_HASH_CODE = "identityHashCode";
/*     */   
/*     */   private LockInfoCompositeData(LockInfo paramLockInfo) {
/*  43 */     this.lock = paramLockInfo;
/*     */   }
/*     */   
/*     */   public LockInfo getLockInfo() {
/*  47 */     return this.lock;
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(LockInfo paramLockInfo) {
/*  51 */     if (paramLockInfo == null) {
/*  52 */       return null;
/*     */     }
/*     */     
/*  55 */     LockInfoCompositeData lockInfoCompositeData = new LockInfoCompositeData(paramLockInfo);
/*  56 */     return lockInfoCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  64 */     Object[] arrayOfObject = { new String(this.lock.getClassName()), new Integer(this.lock.getIdentityHashCode()) };
/*     */ 
/*     */     
/*     */     try {
/*  68 */       return new CompositeDataSupport(lockInfoCompositeType, lockInfoItemNames, arrayOfObject);
/*     */     
/*     */     }
/*  71 */     catch (OpenDataException openDataException) {
/*     */       
/*  73 */       throw Util.newException(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  81 */       lockInfoCompositeType = (CompositeType)MappedMXBeanType.toOpenType(LockInfo.class);
/*  82 */     } catch (OpenDataException openDataException) {
/*     */       
/*  84 */       throw Util.newException(openDataException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static CompositeType getLockInfoCompositeType() {
/*  89 */     return lockInfoCompositeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final String[] lockInfoItemNames = new String[] { "className", "identityHashCode" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -6374759159749014052L;
/*     */ 
/*     */ 
/*     */   
/*     */   public static LockInfo toLockInfo(CompositeData paramCompositeData) {
/* 103 */     if (paramCompositeData == null) {
/* 104 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 107 */     if (!isTypeMatched(lockInfoCompositeType, paramCompositeData.getCompositeType())) {
/* 108 */       throw new IllegalArgumentException("Unexpected composite type for LockInfo");
/*     */     }
/*     */ 
/*     */     
/* 112 */     String str = getString(paramCompositeData, "className");
/* 113 */     int i = getInt(paramCompositeData, "identityHashCode");
/* 114 */     return new LockInfo(str, i);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/LockInfoCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */