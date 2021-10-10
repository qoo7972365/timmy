/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.awt.image.IntegerComponentRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GradientPaintContext
/*     */   implements PaintContext
/*     */ {
/*  38 */   static ColorModel xrgbmodel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */   
/*  40 */   static ColorModel xbgrmodel = new DirectColorModel(24, 255, 65280, 16711680); static ColorModel cachedModel;
/*     */   static WeakReference<Raster> cached;
/*     */   double x1;
/*     */   double y1;
/*     */   double dx;
/*     */   
/*     */   static synchronized Raster getCachedRaster(ColorModel paramColorModel, int paramInt1, int paramInt2) {
/*  47 */     if (paramColorModel == cachedModel && 
/*  48 */       cached != null) {
/*  49 */       Raster raster = cached.get();
/*  50 */       if (raster != null && raster
/*  51 */         .getWidth() >= paramInt1 && raster
/*  52 */         .getHeight() >= paramInt2) {
/*     */         
/*  54 */         cached = null;
/*  55 */         return raster;
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     return paramColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */   }
/*     */   double dy; boolean cyclic; int[] interp; Raster saved; ColorModel model;
/*     */   static synchronized void putCachedRaster(ColorModel paramColorModel, Raster paramRaster) {
/*  63 */     if (cached != null) {
/*  64 */       Raster raster = cached.get();
/*  65 */       if (raster != null) {
/*  66 */         int i = raster.getWidth();
/*  67 */         int j = raster.getHeight();
/*  68 */         int k = paramRaster.getWidth();
/*  69 */         int m = paramRaster.getHeight();
/*  70 */         if (i >= k && j >= m) {
/*     */           return;
/*     */         }
/*  73 */         if (i * j >= k * m) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*  78 */     cachedModel = paramColorModel;
/*  79 */     cached = new WeakReference<>(paramRaster);
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
/*     */   public GradientPaintContext(ColorModel paramColorModel, Point2D paramPoint2D1, Point2D paramPoint2D2, AffineTransform paramAffineTransform, Color paramColor1, Color paramColor2, boolean paramBoolean) {
/*  96 */     Point2D.Double double_1 = new Point2D.Double(1.0D, 0.0D);
/*  97 */     Point2D.Double double_2 = new Point2D.Double(0.0D, 1.0D);
/*     */     try {
/*  99 */       AffineTransform affineTransform = paramAffineTransform.createInverse();
/* 100 */       affineTransform.deltaTransform(double_1, double_1);
/* 101 */       affineTransform.deltaTransform(double_2, double_2);
/* 102 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 103 */       double_1.setLocation(0.0D, 0.0D);
/* 104 */       double_2.setLocation(0.0D, 0.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     double d1 = paramPoint2D2.getX() - paramPoint2D1.getX();
/* 111 */     double d2 = paramPoint2D2.getY() - paramPoint2D1.getY();
/* 112 */     double d3 = d1 * d1 + d2 * d2;
/*     */     
/* 114 */     if (d3 <= Double.MIN_VALUE) {
/* 115 */       this.dx = 0.0D;
/* 116 */       this.dy = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       this.dx = (double_1.getX() * d1 + double_1.getY() * d2) / d3;
/* 134 */       this.dy = (double_2.getX() * d1 + double_2.getY() * d2) / d3;
/*     */       
/* 136 */       if (paramBoolean) {
/* 137 */         this.dx %= 1.0D;
/* 138 */         this.dy %= 1.0D;
/*     */       
/*     */       }
/* 141 */       else if (this.dx < 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         Point2D point2D1 = paramPoint2D1; paramPoint2D1 = paramPoint2D2; paramPoint2D2 = point2D1;
/* 148 */         Color color = paramColor1; paramColor1 = paramColor2; paramColor2 = color;
/* 149 */         this.dx = -this.dx;
/* 150 */         this.dy = -this.dy;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 155 */     Point2D point2D = paramAffineTransform.transform(paramPoint2D1, null);
/* 156 */     this.x1 = point2D.getX();
/* 157 */     this.y1 = point2D.getY();
/*     */     
/* 159 */     this.cyclic = paramBoolean;
/* 160 */     int i = paramColor1.getRGB();
/* 161 */     int j = paramColor2.getRGB();
/* 162 */     int k = i >> 24 & 0xFF;
/* 163 */     int m = i >> 16 & 0xFF;
/* 164 */     int n = i >> 8 & 0xFF;
/* 165 */     int i1 = i & 0xFF;
/* 166 */     int i2 = (j >> 24 & 0xFF) - k;
/* 167 */     int i3 = (j >> 16 & 0xFF) - m;
/* 168 */     int i4 = (j >> 8 & 0xFF) - n;
/* 169 */     int i5 = (j & 0xFF) - i1;
/* 170 */     if (k == 255 && i2 == 0) {
/* 171 */       this.model = xrgbmodel;
/* 172 */       if (paramColorModel instanceof DirectColorModel) {
/* 173 */         DirectColorModel directColorModel = (DirectColorModel)paramColorModel;
/* 174 */         int i6 = directColorModel.getAlphaMask();
/* 175 */         if ((i6 == 0 || i6 == 255) && directColorModel
/* 176 */           .getRedMask() == 255 && directColorModel
/* 177 */           .getGreenMask() == 65280 && directColorModel
/* 178 */           .getBlueMask() == 16711680) {
/*     */           
/* 180 */           this.model = xbgrmodel;
/* 181 */           i6 = m; m = i1; i1 = i6;
/* 182 */           i6 = i3; i3 = i5; i5 = i6;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 186 */       this.model = ColorModel.getRGBdefault();
/*     */     } 
/* 188 */     this.interp = new int[paramBoolean ? 513 : 257];
/* 189 */     for (byte b = 0; b <= 'Ā'; b++) {
/* 190 */       float f = b / 256.0F;
/* 191 */       int i6 = (int)(k + i2 * f) << 24 | (int)(m + i3 * f) << 16 | (int)(n + i4 * f) << 8 | (int)(i1 + i5 * f);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       this.interp[b] = i6;
/* 197 */       if (paramBoolean) {
/* 198 */         this.interp[512 - b] = i6;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 207 */     if (this.saved != null) {
/* 208 */       putCachedRaster(this.model, this.saved);
/* 209 */       this.saved = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() {
/* 217 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 227 */     double d = (paramInt1 - this.x1) * this.dx + (paramInt2 - this.y1) * this.dy;
/*     */     
/* 229 */     Raster raster = this.saved;
/* 230 */     if (raster == null || raster.getWidth() < paramInt3 || raster.getHeight() < paramInt4) {
/* 231 */       raster = getCachedRaster(this.model, paramInt3, paramInt4);
/* 232 */       this.saved = raster;
/*     */     } 
/* 234 */     IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)raster;
/* 235 */     int i = integerComponentRaster.getDataOffset(0);
/* 236 */     int j = integerComponentRaster.getScanlineStride() - paramInt3;
/* 237 */     int[] arrayOfInt = integerComponentRaster.getDataStorage();
/*     */     
/* 239 */     if (this.cyclic) {
/* 240 */       cycleFillRaster(arrayOfInt, i, j, paramInt3, paramInt4, d, this.dx, this.dy);
/*     */     } else {
/* 242 */       clipFillRaster(arrayOfInt, i, j, paramInt3, paramInt4, d, this.dx, this.dy);
/*     */     } 
/*     */     
/* 245 */     integerComponentRaster.markDirty();
/*     */     
/* 247 */     return raster;
/*     */   }
/*     */ 
/*     */   
/*     */   void cycleFillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 252 */     paramDouble1 %= 2.0D;
/* 253 */     int i = (int)(paramDouble1 * 1.073741824E9D) << 1;
/* 254 */     int j = (int)(-paramDouble2 * -2.147483648E9D);
/* 255 */     int k = (int)(-paramDouble3 * -2.147483648E9D);
/* 256 */     while (--paramInt4 >= 0) {
/* 257 */       int m = i;
/* 258 */       for (int n = paramInt3; n > 0; n--) {
/* 259 */         paramArrayOfint[paramInt1++] = this.interp[m >>> 23];
/* 260 */         m += j;
/*     */       } 
/*     */       
/* 263 */       paramInt1 += paramInt2;
/* 264 */       i += k;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void clipFillRaster(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 270 */     while (--paramInt4 >= 0) {
/* 271 */       double d = paramDouble1;
/* 272 */       int i = paramInt3;
/* 273 */       if (d <= 0.0D) {
/* 274 */         int j = this.interp[0];
/*     */         do {
/* 276 */           paramArrayOfint[paramInt1++] = j;
/* 277 */           d += paramDouble2;
/* 278 */         } while (--i > 0 && d <= 0.0D);
/*     */       } 
/* 280 */       while (d < 1.0D && --i >= 0) {
/* 281 */         paramArrayOfint[paramInt1++] = this.interp[(int)(d * 256.0D)];
/* 282 */         d += paramDouble2;
/*     */       } 
/* 284 */       if (i > 0) {
/* 285 */         int j = this.interp[256];
/*     */         do {
/* 287 */           paramArrayOfint[paramInt1++] = j;
/* 288 */         } while (--i > 0);
/*     */       } 
/*     */       
/* 291 */       paramInt1 += paramInt2;
/* 292 */       paramDouble1 += paramDouble3;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GradientPaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */