/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConvolveOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   Kernel kernel;
/*     */   int edgeHint;
/*     */   RenderingHints hints;
/*     */   public static final int EDGE_ZERO_FILL = 0;
/*     */   public static final int EDGE_NO_OP = 1;
/*     */   
/*     */   public ConvolveOp(Kernel paramKernel, int paramInt, RenderingHints paramRenderingHints) {
/* 102 */     this.kernel = paramKernel;
/* 103 */     this.edgeHint = paramInt;
/* 104 */     this.hints = paramRenderingHints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvolveOp(Kernel paramKernel) {
/* 115 */     this.kernel = paramKernel;
/* 116 */     this.edgeHint = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEdgeCondition() {
/* 126 */     return this.edgeHint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Kernel getKernel() {
/* 134 */     return (Kernel)this.kernel.clone();
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
/*     */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/* 157 */     if (paramBufferedImage1 == null) {
/* 158 */       throw new NullPointerException("src image is null");
/*     */     }
/* 160 */     if (paramBufferedImage1 == paramBufferedImage2) {
/* 161 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */ 
/*     */     
/* 165 */     boolean bool = false;
/* 166 */     ColorModel colorModel = paramBufferedImage1.getColorModel();
/*     */     
/* 168 */     BufferedImage bufferedImage = paramBufferedImage2;
/*     */ 
/*     */     
/* 171 */     if (colorModel instanceof IndexColorModel) {
/* 172 */       IndexColorModel indexColorModel = (IndexColorModel)colorModel;
/* 173 */       paramBufferedImage1 = indexColorModel.convertToIntDiscrete(paramBufferedImage1.getRaster(), false);
/* 174 */       colorModel = paramBufferedImage1.getColorModel();
/*     */     } 
/*     */     
/* 177 */     if (paramBufferedImage2 == null) {
/* 178 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 179 */       ColorModel colorModel1 = colorModel;
/* 180 */       bufferedImage = paramBufferedImage2;
/*     */     } else {
/*     */       
/* 183 */       ColorModel colorModel1 = paramBufferedImage2.getColorModel();
/* 184 */       if (colorModel.getColorSpace().getType() != colorModel1
/* 185 */         .getColorSpace().getType()) {
/*     */         
/* 187 */         bool = true;
/* 188 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 189 */         colorModel1 = paramBufferedImage2.getColorModel();
/*     */       }
/* 191 */       else if (colorModel1 instanceof IndexColorModel) {
/* 192 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 193 */         colorModel1 = paramBufferedImage2.getColorModel();
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/* 198 */       throw new ImagingOpException("Unable to convolve src image");
/*     */     }
/*     */     
/* 201 */     if (bool) {
/* 202 */       ColorConvertOp colorConvertOp = new ColorConvertOp(this.hints);
/* 203 */       colorConvertOp.filter(paramBufferedImage2, bufferedImage);
/*     */     }
/* 205 */     else if (bufferedImage != paramBufferedImage2) {
/* 206 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/*     */       try {
/* 208 */         graphics2D.drawImage(paramBufferedImage2, 0, 0, (ImageObserver)null);
/*     */       } finally {
/* 210 */         graphics2D.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     return bufferedImage;
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
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 236 */     if (paramWritableRaster == null) {
/* 237 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     } else {
/* 239 */       if (paramRaster == paramWritableRaster) {
/* 240 */         throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */       }
/*     */       
/* 243 */       if (paramRaster.getNumBands() != paramWritableRaster.getNumBands()) {
/* 244 */         throw new ImagingOpException("Different number of bands in src  and dst Rasters");
/*     */       }
/*     */     } 
/*     */     
/* 248 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) == null) {
/* 249 */       throw new ImagingOpException("Unable to convolve src image");
/*     */     }
/*     */     
/* 252 */     return paramWritableRaster;
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel) {
/* 267 */     int i = paramBufferedImage.getWidth();
/* 268 */     int j = paramBufferedImage.getHeight();
/*     */     
/* 270 */     WritableRaster writableRaster = null;
/*     */     
/* 272 */     if (paramColorModel == null) {
/* 273 */       paramColorModel = paramBufferedImage.getColorModel();
/*     */       
/* 275 */       if (paramColorModel instanceof IndexColorModel) {
/* 276 */         paramColorModel = ColorModel.getRGBdefault();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 281 */         writableRaster = paramBufferedImage.getData().createCompatibleWritableRaster(i, j);
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     if (writableRaster == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       writableRaster = paramColorModel.createCompatibleWritableRaster(i, j);
/*     */     }
/*     */     
/* 295 */     return new BufferedImage(paramColorModel, writableRaster, paramColorModel
/* 296 */         .isAlphaPremultiplied(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/* 306 */     return paramRaster.createCompatibleWritableRaster();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage) {
/* 315 */     return getBounds2D(paramBufferedImage.getRaster());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/* 324 */     return paramRaster.getBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 334 */     if (paramPoint2D2 == null) {
/* 335 */       paramPoint2D2 = new Point2D.Float();
/*     */     }
/* 337 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*     */     
/* 339 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderingHints getRenderingHints() {
/* 346 */     return this.hints;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ConvolveOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */