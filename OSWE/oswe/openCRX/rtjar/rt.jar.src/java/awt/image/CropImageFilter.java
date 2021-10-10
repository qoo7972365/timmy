/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropImageFilter
/*     */   extends ImageFilter
/*     */ {
/*     */   int cropX;
/*     */   int cropY;
/*     */   int cropW;
/*     */   int cropH;
/*     */   
/*     */   public CropImageFilter(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  62 */     this.cropX = paramInt1;
/*  63 */     this.cropY = paramInt2;
/*  64 */     this.cropW = paramInt3;
/*  65 */     this.cropH = paramInt4;
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
/*     */   public void setProperties(Hashtable<?, ?> paramHashtable) {
/*  82 */     Hashtable<String, Rectangle> hashtable = (Hashtable)paramHashtable.clone();
/*  83 */     hashtable.put("croprect", new Rectangle(this.cropX, this.cropY, this.cropW, this.cropH));
/*  84 */     super.setProperties(hashtable);
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
/*     */   public void setDimensions(int paramInt1, int paramInt2) {
/* 100 */     this.consumer.setDimensions(this.cropW, this.cropH);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 118 */     int i = paramInt1;
/* 119 */     if (i < this.cropX) {
/* 120 */       i = this.cropX;
/*     */     }
/* 122 */     int j = addWithoutOverflow(paramInt1, paramInt3);
/* 123 */     if (j > this.cropX + this.cropW) {
/* 124 */       j = this.cropX + this.cropW;
/*     */     }
/* 126 */     int k = paramInt2;
/* 127 */     if (k < this.cropY) {
/* 128 */       k = this.cropY;
/*     */     }
/*     */     
/* 131 */     int m = addWithoutOverflow(paramInt2, paramInt4);
/* 132 */     if (m > this.cropY + this.cropH) {
/* 133 */       m = this.cropY + this.cropH;
/*     */     }
/* 135 */     if (i >= j || k >= m) {
/*     */       return;
/*     */     }
/* 138 */     this.consumer.setPixels(i - this.cropX, k - this.cropY, j - i, m - k, paramColorModel, paramArrayOfbyte, paramInt5 + (k - paramInt2) * paramInt6 + i - paramInt1, paramInt6);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 158 */     int i = paramInt1;
/* 159 */     if (i < this.cropX) {
/* 160 */       i = this.cropX;
/*     */     }
/* 162 */     int j = addWithoutOverflow(paramInt1, paramInt3);
/* 163 */     if (j > this.cropX + this.cropW) {
/* 164 */       j = this.cropX + this.cropW;
/*     */     }
/* 166 */     int k = paramInt2;
/* 167 */     if (k < this.cropY) {
/* 168 */       k = this.cropY;
/*     */     }
/*     */     
/* 171 */     int m = addWithoutOverflow(paramInt2, paramInt4);
/* 172 */     if (m > this.cropY + this.cropH) {
/* 173 */       m = this.cropY + this.cropH;
/*     */     }
/* 175 */     if (i >= j || k >= m) {
/*     */       return;
/*     */     }
/* 178 */     this.consumer.setPixels(i - this.cropX, k - this.cropY, j - i, m - k, paramColorModel, paramArrayOfint, paramInt5 + (k - paramInt2) * paramInt6 + i - paramInt1, paramInt6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int addWithoutOverflow(int paramInt1, int paramInt2) {
/* 185 */     int i = paramInt1 + paramInt2;
/* 186 */     if (paramInt1 > 0 && paramInt2 > 0 && i < 0) {
/* 187 */       i = Integer.MAX_VALUE;
/* 188 */     } else if (paramInt1 < 0 && paramInt2 < 0 && i > 0) {
/* 189 */       i = Integer.MIN_VALUE;
/*     */     } 
/* 191 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/CropImageFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */