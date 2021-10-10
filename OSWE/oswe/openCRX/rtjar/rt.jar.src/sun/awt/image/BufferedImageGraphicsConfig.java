/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedImageGraphicsConfig
/*     */   extends GraphicsConfiguration
/*     */ {
/*     */   private static final int numconfigs = 12;
/*  48 */   private static BufferedImageGraphicsConfig[] configs = new BufferedImageGraphicsConfig[12];
/*     */   GraphicsDevice gd;
/*     */   ColorModel model;
/*     */   
/*     */   public static BufferedImageGraphicsConfig getConfig(BufferedImage paramBufferedImage) {
/*  53 */     int i = paramBufferedImage.getType();
/*  54 */     if (i > 0 && i < 12) {
/*  55 */       BufferedImageGraphicsConfig bufferedImageGraphicsConfig1 = configs[i];
/*  56 */       if (bufferedImageGraphicsConfig1 != null) {
/*  57 */         return bufferedImageGraphicsConfig1;
/*     */       }
/*     */     } 
/*  60 */     BufferedImageGraphicsConfig bufferedImageGraphicsConfig = new BufferedImageGraphicsConfig(paramBufferedImage, null);
/*  61 */     if (i > 0 && i < 12) {
/*  62 */       configs[i] = bufferedImageGraphicsConfig;
/*     */     }
/*  64 */     return bufferedImageGraphicsConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   Raster raster;
/*     */   int width;
/*     */   int height;
/*     */   
/*     */   public BufferedImageGraphicsConfig(BufferedImage paramBufferedImage, Component paramComponent) {
/*  73 */     if (paramComponent == null) {
/*  74 */       this.gd = new BufferedImageDevice(this);
/*     */     } else {
/*  76 */       Graphics2D graphics2D = (Graphics2D)paramComponent.getGraphics();
/*  77 */       this.gd = graphics2D.getDeviceConfiguration().getDevice();
/*     */     } 
/*  79 */     this.model = paramBufferedImage.getColorModel();
/*  80 */     this.raster = paramBufferedImage.getRaster().createCompatibleWritableRaster(1, 1);
/*  81 */     this.width = paramBufferedImage.getWidth();
/*  82 */     this.height = paramBufferedImage.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsDevice getDevice() {
/*  89 */     return this.gd;
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
/*     */   public BufferedImage createCompatibleImage(int paramInt1, int paramInt2) {
/* 102 */     WritableRaster writableRaster = this.raster.createCompatibleWritableRaster(paramInt1, paramInt2);
/* 103 */     return new BufferedImage(this.model, writableRaster, this.model.isAlphaPremultiplied(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() {
/* 110 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel(int paramInt) {
/* 119 */     if (this.model.getTransparency() == paramInt) {
/* 120 */       return this.model;
/*     */     }
/* 122 */     switch (paramInt) {
/*     */       case 1:
/* 124 */         return new DirectColorModel(24, 16711680, 65280, 255);
/*     */       case 2:
/* 126 */         return new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*     */       case 3:
/* 128 */         return ColorModel.getRGBdefault();
/*     */     } 
/* 130 */     return null;
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
/*     */   public AffineTransform getDefaultTransform() {
/* 144 */     return new AffineTransform();
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
/*     */   public AffineTransform getNormalizingTransform() {
/* 167 */     return new AffineTransform();
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 171 */     return new Rectangle(0, 0, this.width, this.height);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/BufferedImageGraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */