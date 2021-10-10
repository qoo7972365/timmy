/*     */ package java.lang.management;
/*     */ 
/*     */ import java.lang.management.LockInfo;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.management.LockInfoCompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LockInfo
/*     */ {
/*     */   private String className;
/*     */   private int identityHashCode;
/*     */   
/*     */   public LockInfo(String paramString, int paramInt) {
/*  69 */     if (paramString == null) {
/*  70 */       throw new NullPointerException("Parameter className cannot be null");
/*     */     }
/*  72 */     this.className = paramString;
/*  73 */     this.identityHashCode = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LockInfo(Object paramObject) {
/*  80 */     this.className = paramObject.getClass().getName();
/*  81 */     this.identityHashCode = System.identityHashCode(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*  90 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdentityHashCode() {
/* 100 */     return this.identityHashCode;
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
/*     */   public static LockInfo from(CompositeData paramCompositeData) {
/* 136 */     if (paramCompositeData == null) {
/* 137 */       return null;
/*     */     }
/*     */     
/* 140 */     if (paramCompositeData instanceof LockInfoCompositeData) {
/* 141 */       return ((LockInfoCompositeData)paramCompositeData).getLockInfo();
/*     */     }
/* 143 */     return LockInfoCompositeData.toLockInfo(paramCompositeData);
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
/*     */   public String toString() {
/* 162 */     return this.className + '@' + Integer.toHexString(this.identityHashCode);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/LockInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */