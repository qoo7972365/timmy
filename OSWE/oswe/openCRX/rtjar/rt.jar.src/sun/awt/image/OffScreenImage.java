/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.Hashtable;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OffScreenImage
/*     */   extends BufferedImage
/*     */ {
/*     */   protected Component c;
/*     */   private OffScreenImageSource osis;
/*     */   private Font defaultFont;
/*     */   
/*     */   public OffScreenImage(Component paramComponent, ColorModel paramColorModel, WritableRaster paramWritableRaster, boolean paramBoolean) {
/*  62 */     super(paramColorModel, paramWritableRaster, paramBoolean, (Hashtable<?, ?>)null);
/*  63 */     this.c = paramComponent;
/*  64 */     initSurface(paramWritableRaster.getWidth(), paramWritableRaster.getHeight());
/*     */   }
/*     */   
/*     */   public Graphics getGraphics() {
/*  68 */     return createGraphics();
/*     */   }
/*     */   
/*     */   public Graphics2D createGraphics() {
/*  72 */     if (this.c == null) {
/*     */       
/*  74 */       GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  75 */       return graphicsEnvironment.createGraphics(this);
/*     */     } 
/*     */     
/*  78 */     Color color1 = this.c.getBackground();
/*  79 */     if (color1 == null) {
/*  80 */       color1 = SystemColor.window;
/*     */     }
/*     */     
/*  83 */     Color color2 = this.c.getForeground();
/*  84 */     if (color2 == null) {
/*  85 */       color2 = SystemColor.windowText;
/*     */     }
/*     */     
/*  88 */     Font font = this.c.getFont();
/*  89 */     if (font == null) {
/*  90 */       if (this.defaultFont == null) {
/*  91 */         this.defaultFont = new Font("Dialog", 0, 12);
/*     */       }
/*  93 */       font = this.defaultFont;
/*     */     } 
/*     */     
/*  96 */     return new SunGraphics2D(SurfaceData.getPrimarySurfaceData(this), color2, color1, font);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initSurface(int paramInt1, int paramInt2) {
/* 101 */     Graphics2D graphics2D = createGraphics();
/*     */     try {
/* 103 */       graphics2D.clearRect(0, 0, paramInt1, paramInt2);
/*     */     } finally {
/* 105 */       graphics2D.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ImageProducer getSource() {
/* 110 */     if (this.osis == null) {
/* 111 */       this.osis = new OffScreenImageSource(this);
/*     */     }
/* 113 */     return this.osis;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/OffScreenImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */