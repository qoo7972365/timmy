/*     */ package java.awt.image;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Kernel
/*     */   implements Cloneable
/*     */ {
/*     */   private int width;
/*     */   private int height;
/*     */   private int xOrigin;
/*     */   private int yOrigin;
/*     */   private float[] data;
/*     */   
/*     */   static {
/*  48 */     ColorModel.loadLibraries();
/*  49 */     initIDs();
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
/*     */   public Kernel(int paramInt1, int paramInt2, float[] paramArrayOffloat) {
/*  67 */     this.width = paramInt1;
/*  68 */     this.height = paramInt2;
/*  69 */     this.xOrigin = paramInt1 - 1 >> 1;
/*  70 */     this.yOrigin = paramInt2 - 1 >> 1;
/*  71 */     int i = paramInt1 * paramInt2;
/*  72 */     if (paramArrayOffloat.length < i) {
/*  73 */       throw new IllegalArgumentException("Data array too small (is " + paramArrayOffloat.length + " and should be " + i);
/*     */     }
/*     */ 
/*     */     
/*  77 */     this.data = new float[i];
/*  78 */     System.arraycopy(paramArrayOffloat, 0, this.data, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getXOrigin() {
/*  87 */     return this.xOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getYOrigin() {
/*  95 */     return this.yOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getWidth() {
/* 103 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getHeight() {
/* 111 */     return this.height;
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
/*     */   public final float[] getKernelData(float[] paramArrayOffloat) {
/* 127 */     if (paramArrayOffloat == null) {
/* 128 */       paramArrayOffloat = new float[this.data.length];
/*     */     }
/* 130 */     else if (paramArrayOffloat.length < this.data.length) {
/* 131 */       throw new IllegalArgumentException("Data array too small (should be " + this.data.length + " but is " + paramArrayOffloat.length + " )");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     System.arraycopy(this.data, 0, paramArrayOffloat, 0, this.data.length);
/*     */     
/* 138 */     return paramArrayOffloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 147 */       return super.clone();
/* 148 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 150 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/Kernel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */