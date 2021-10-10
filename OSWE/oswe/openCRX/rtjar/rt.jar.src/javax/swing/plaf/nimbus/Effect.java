/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.lang.ref.SoftReference;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Effect
/*     */ {
/*     */   abstract EffectType getEffectType();
/*     */   
/*     */   abstract float getOpacity();
/*     */   
/*     */   abstract BufferedImage applyEffect(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, int paramInt1, int paramInt2);
/*     */   
/*     */   enum EffectType
/*     */   {
/*  39 */     UNDER, BLENDED, OVER;
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
/*     */   protected static ArrayCache getArrayCache() {
/*  79 */     ArrayCache arrayCache = (ArrayCache)AppContext.getAppContext().get(ArrayCache.class);
/*  80 */     if (arrayCache == null) {
/*  81 */       arrayCache = new ArrayCache();
/*  82 */       AppContext.getAppContext().put(ArrayCache.class, arrayCache);
/*     */     } 
/*  84 */     return arrayCache;
/*     */   }
/*     */   
/*     */   protected static class ArrayCache {
/*  88 */     private SoftReference<int[]> tmpIntArray = null;
/*  89 */     private SoftReference<byte[]> tmpByteArray1 = null;
/*  90 */     private SoftReference<byte[]> tmpByteArray2 = null;
/*  91 */     private SoftReference<byte[]> tmpByteArray3 = null;
/*     */     
/*     */     protected int[] getTmpIntArray(int param1Int) {
/*     */       int[] arrayOfInt;
/*  95 */       if (this.tmpIntArray == null || (arrayOfInt = this.tmpIntArray.get()) == null || arrayOfInt.length < param1Int) {
/*     */         
/*  97 */         arrayOfInt = new int[param1Int];
/*  98 */         this.tmpIntArray = (SoftReference)new SoftReference<>(arrayOfInt);
/*     */       } 
/* 100 */       return arrayOfInt;
/*     */     }
/*     */     
/*     */     protected byte[] getTmpByteArray1(int param1Int) {
/*     */       byte[] arrayOfByte;
/* 105 */       if (this.tmpByteArray1 == null || (arrayOfByte = this.tmpByteArray1.get()) == null || arrayOfByte.length < param1Int) {
/*     */         
/* 107 */         arrayOfByte = new byte[param1Int];
/* 108 */         this.tmpByteArray1 = (SoftReference)new SoftReference<>(arrayOfByte);
/*     */       } 
/* 110 */       return arrayOfByte;
/*     */     }
/*     */     
/*     */     protected byte[] getTmpByteArray2(int param1Int) {
/*     */       byte[] arrayOfByte;
/* 115 */       if (this.tmpByteArray2 == null || (arrayOfByte = this.tmpByteArray2.get()) == null || arrayOfByte.length < param1Int) {
/*     */         
/* 117 */         arrayOfByte = new byte[param1Int];
/* 118 */         this.tmpByteArray2 = (SoftReference)new SoftReference<>(arrayOfByte);
/*     */       } 
/* 120 */       return arrayOfByte;
/*     */     }
/*     */     
/*     */     protected byte[] getTmpByteArray3(int param1Int) {
/*     */       byte[] arrayOfByte;
/* 125 */       if (this.tmpByteArray3 == null || (arrayOfByte = this.tmpByteArray3.get()) == null || arrayOfByte.length < param1Int) {
/*     */         
/* 127 */         arrayOfByte = new byte[param1Int];
/* 128 */         this.tmpByteArray3 = (SoftReference)new SoftReference<>(arrayOfByte);
/*     */       } 
/* 130 */       return arrayOfByte;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/Effect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */