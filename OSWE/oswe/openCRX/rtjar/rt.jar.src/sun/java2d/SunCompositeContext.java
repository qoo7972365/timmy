/*     */ package sun.java2d;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.CompositeContext;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.awt.image.BufImgSurfaceData;
/*     */ import sun.java2d.loops.Blit;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.XORComposite;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SunCompositeContext
/*     */   implements CompositeContext
/*     */ {
/*     */   ColorModel srcCM;
/*     */   ColorModel dstCM;
/*     */   Composite composite;
/*     */   CompositeType comptype;
/*     */   
/*     */   public SunCompositeContext(AlphaComposite paramAlphaComposite, ColorModel paramColorModel1, ColorModel paramColorModel2) {
/*  49 */     if (paramColorModel1 == null) {
/*  50 */       throw new NullPointerException("Source color model cannot be null");
/*     */     }
/*  52 */     if (paramColorModel2 == null) {
/*  53 */       throw new NullPointerException("Destination color model cannot be null");
/*     */     }
/*  55 */     this.srcCM = paramColorModel1;
/*  56 */     this.dstCM = paramColorModel2;
/*  57 */     this.composite = paramAlphaComposite;
/*  58 */     this.comptype = CompositeType.forAlphaComposite(paramAlphaComposite);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SunCompositeContext(XORComposite paramXORComposite, ColorModel paramColorModel1, ColorModel paramColorModel2) {
/*  64 */     if (paramColorModel1 == null) {
/*  65 */       throw new NullPointerException("Source color model cannot be null");
/*     */     }
/*  67 */     if (paramColorModel2 == null) {
/*  68 */       throw new NullPointerException("Destination color model cannot be null");
/*     */     }
/*  70 */     this.srcCM = paramColorModel1;
/*  71 */     this.dstCM = paramColorModel2;
/*  72 */     this.composite = paramXORComposite;
/*  73 */     this.comptype = CompositeType.Xor;
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
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compose(Raster paramRaster1, Raster paramRaster2, WritableRaster paramWritableRaster) {
/*     */     WritableRaster writableRaster;
/*  96 */     if (paramRaster2 != paramWritableRaster) {
/*  97 */       paramWritableRaster.setDataElements(0, 0, paramRaster2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (paramRaster1 instanceof WritableRaster) {
/* 106 */       writableRaster = (WritableRaster)paramRaster1;
/*     */     } else {
/* 108 */       writableRaster = paramRaster1.createCompatibleWritableRaster();
/* 109 */       writableRaster.setDataElements(0, 0, paramRaster1);
/*     */     } 
/*     */     
/* 112 */     int i = Math.min(writableRaster.getWidth(), paramRaster2.getWidth());
/* 113 */     int j = Math.min(writableRaster.getHeight(), paramRaster2.getHeight());
/*     */ 
/*     */     
/* 116 */     BufferedImage bufferedImage1 = new BufferedImage(this.srcCM, writableRaster, this.srcCM.isAlphaPremultiplied(), null);
/*     */ 
/*     */     
/* 119 */     BufferedImage bufferedImage2 = new BufferedImage(this.dstCM, paramWritableRaster, this.dstCM.isAlphaPremultiplied(), null);
/*     */ 
/*     */     
/* 122 */     SurfaceData surfaceData1 = BufImgSurfaceData.createData(bufferedImage1);
/* 123 */     SurfaceData surfaceData2 = BufImgSurfaceData.createData(bufferedImage2);
/* 124 */     Blit blit = Blit.getFromCache(surfaceData1.getSurfaceType(), this.comptype, surfaceData2
/*     */         
/* 126 */         .getSurfaceType());
/* 127 */     blit.Blit(surfaceData1, surfaceData2, this.composite, null, 0, 0, 0, 0, i, j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/SunCompositeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */