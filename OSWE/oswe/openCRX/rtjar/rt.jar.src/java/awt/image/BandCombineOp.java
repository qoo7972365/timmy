/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Arrays;
/*     */ import sun.awt.image.ImagingLib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BandCombineOp
/*     */   implements RasterOp
/*     */ {
/*     */   float[][] matrix;
/*  62 */   int nrows = 0;
/*  63 */   int ncols = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RenderingHints hints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BandCombineOp(float[][] paramArrayOffloat, RenderingHints paramRenderingHints) {
/*  85 */     this.nrows = paramArrayOffloat.length;
/*  86 */     this.ncols = (paramArrayOffloat[0]).length;
/*  87 */     this.matrix = new float[this.nrows][];
/*  88 */     for (byte b = 0; b < this.nrows; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       if (this.ncols > (paramArrayOffloat[b]).length) {
/*  95 */         throw new IndexOutOfBoundsException("row " + b + " too short");
/*     */       }
/*  97 */       this.matrix[b] = Arrays.copyOf(paramArrayOffloat[b], this.ncols);
/*     */     } 
/*  99 */     this.hints = paramRenderingHints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float[][] getMatrix() {
/* 108 */     float[][] arrayOfFloat = new float[this.nrows][];
/* 109 */     for (byte b = 0; b < this.nrows; b++) {
/* 110 */       arrayOfFloat[b] = Arrays.copyOf(this.matrix[b], this.ncols);
/*     */     }
/* 112 */     return arrayOfFloat;
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
/*     */   public WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 135 */     int i = paramRaster.getNumBands();
/* 136 */     if (this.ncols != i && this.ncols != i + 1) {
/* 137 */       throw new IllegalArgumentException("Number of columns in the matrix (" + this.ncols + ") must be equal to the number of bands ([+1]) in src (" + i + ").");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (paramWritableRaster == null) {
/* 144 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     }
/* 146 */     else if (this.nrows != paramWritableRaster.getNumBands()) {
/* 147 */       throw new IllegalArgumentException("Number of rows in the matrix (" + this.nrows + ") must be equal to the number of bands ([+1]) in dst (" + i + ").");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) != null) {
/* 155 */       return paramWritableRaster;
/*     */     }
/*     */     
/* 158 */     int[] arrayOfInt1 = null;
/* 159 */     int[] arrayOfInt2 = new int[paramWritableRaster.getNumBands()];
/*     */     
/* 161 */     int j = paramRaster.getMinX();
/* 162 */     int k = paramRaster.getMinY();
/* 163 */     int m = paramWritableRaster.getMinX();
/* 164 */     int n = paramWritableRaster.getMinY();
/*     */ 
/*     */     
/* 167 */     if (this.ncols == i) {
/* 168 */       for (byte b = 0; b < paramRaster.getHeight(); b++, k++, n++) {
/* 169 */         int i2 = m;
/* 170 */         int i1 = j;
/* 171 */         for (byte b1 = 0; b1 < paramRaster.getWidth(); b1++, i1++, i2++) {
/* 172 */           arrayOfInt1 = paramRaster.getPixel(i1, k, arrayOfInt1);
/* 173 */           for (byte b2 = 0; b2 < this.nrows; b2++) {
/* 174 */             float f = 0.0F;
/* 175 */             for (byte b3 = 0; b3 < this.ncols; b3++) {
/* 176 */               f += this.matrix[b2][b3] * arrayOfInt1[b3];
/*     */             }
/* 178 */             arrayOfInt2[b2] = (int)f;
/*     */           } 
/* 180 */           paramWritableRaster.setPixel(i2, n, arrayOfInt2);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 186 */       for (byte b = 0; b < paramRaster.getHeight(); b++, k++, n++) {
/* 187 */         int i2 = m;
/* 188 */         int i1 = j;
/* 189 */         for (byte b1 = 0; b1 < paramRaster.getWidth(); b1++, i1++, i2++) {
/* 190 */           arrayOfInt1 = paramRaster.getPixel(i1, k, arrayOfInt1);
/* 191 */           for (byte b2 = 0; b2 < this.nrows; b2++) {
/* 192 */             float f = 0.0F;
/* 193 */             for (byte b3 = 0; b3 < i; b3++) {
/* 194 */               f += this.matrix[b2][b3] * arrayOfInt1[b3];
/*     */             }
/* 196 */             arrayOfInt2[b2] = (int)(f + this.matrix[b2][i]);
/*     */           } 
/* 198 */           paramWritableRaster.setPixel(i2, n, arrayOfInt2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return paramWritableRaster;
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
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/* 223 */     return paramRaster.getBounds();
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
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/* 239 */     int i = paramRaster.getNumBands();
/* 240 */     if (this.ncols != i && this.ncols != i + 1) {
/* 241 */       throw new IllegalArgumentException("Number of columns in the matrix (" + this.ncols + ") must be equal to the number of bands ([+1]) in src (" + i + ").");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (paramRaster.getNumBands() == this.nrows) {
/* 248 */       return paramRaster.createCompatibleWritableRaster();
/*     */     }
/*     */     
/* 251 */     throw new IllegalArgumentException("Don't know how to create a  compatible Raster with " + this.nrows + " bands.");
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
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 272 */     if (paramPoint2D2 == null) {
/* 273 */       paramPoint2D2 = new Point2D.Float();
/*     */     }
/* 275 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*     */     
/* 277 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderingHints getRenderingHints() {
/* 287 */     return this.hints;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/BandCombineOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */