/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.VolatileImage;
/*     */ import sun.java2d.DestSurfaceProvider;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.Surface;
/*     */ import sun.java2d.SurfaceManagerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SunVolatileImage
/*     */   extends VolatileImage
/*     */   implements DestSurfaceProvider
/*     */ {
/*     */   protected VolatileSurfaceManager volSurfaceManager;
/*     */   protected Component comp;
/*     */   private GraphicsConfiguration graphicsConfig;
/*     */   private Font defaultFont;
/*     */   private int width;
/*     */   private int height;
/*     */   private int forcedAccelSurfaceType;
/*     */   
/*     */   protected SunVolatileImage(Component paramComponent, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object paramObject, int paramInt3, ImageCapabilities paramImageCapabilities, int paramInt4) {
/*  71 */     this.comp = paramComponent;
/*  72 */     this.graphicsConfig = paramGraphicsConfiguration;
/*  73 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/*  74 */       throw new IllegalArgumentException("Width (" + paramInt1 + ") and height (" + paramInt2 + ") cannot be <= 0");
/*     */     }
/*     */     
/*  77 */     this.width = paramInt1;
/*  78 */     this.height = paramInt2;
/*  79 */     this.forcedAccelSurfaceType = paramInt4;
/*  80 */     if (paramInt3 != 1 && paramInt3 != 2 && paramInt3 != 3)
/*     */     {
/*     */ 
/*     */       
/*  84 */       throw new IllegalArgumentException("Unknown transparency type:" + paramInt3);
/*     */     }
/*     */     
/*  87 */     this.transparency = paramInt3;
/*  88 */     this.volSurfaceManager = createSurfaceManager(paramObject, paramImageCapabilities);
/*  89 */     SurfaceManager.setManager(this, this.volSurfaceManager);
/*     */ 
/*     */     
/*  92 */     this.volSurfaceManager.initialize();
/*     */     
/*  94 */     this.volSurfaceManager.initContents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SunVolatileImage(Component paramComponent, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object paramObject, ImageCapabilities paramImageCapabilities) {
/* 102 */     this(paramComponent, paramGraphicsConfiguration, paramInt1, paramInt2, paramObject, 1, paramImageCapabilities, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SunVolatileImage(Component paramComponent, int paramInt1, int paramInt2) {
/* 107 */     this(paramComponent, paramInt1, paramInt2, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SunVolatileImage(Component paramComponent, int paramInt1, int paramInt2, Object paramObject) {
/* 113 */     this(paramComponent, paramComponent.getGraphicsConfiguration(), paramInt1, paramInt2, paramObject, (ImageCapabilities)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SunVolatileImage(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, int paramInt3, ImageCapabilities paramImageCapabilities) {
/* 121 */     this((Component)null, paramGraphicsConfiguration, paramInt1, paramInt2, (Object)null, paramInt3, paramImageCapabilities, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 126 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 130 */     return this.height;
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getGraphicsConfig() {
/* 134 */     return this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGraphicsConfig() {
/* 141 */     if (this.comp != null) {
/* 142 */       GraphicsConfiguration graphicsConfiguration = this.comp.getGraphicsConfiguration();
/* 143 */       if (graphicsConfiguration != null)
/*     */       {
/*     */ 
/*     */         
/* 147 */         this.graphicsConfig = graphicsConfiguration;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Component getComponent() {
/* 153 */     return this.comp;
/*     */   }
/*     */   
/*     */   public int getForcedAccelSurfaceType() {
/* 157 */     return this.forcedAccelSurfaceType;
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
/*     */   protected VolatileSurfaceManager createSurfaceManager(Object paramObject, ImageCapabilities paramImageCapabilities) {
/* 175 */     if (this.graphicsConfig instanceof BufferedImageGraphicsConfig || this.graphicsConfig instanceof sun.print.PrinterGraphicsConfig || (paramImageCapabilities != null && 
/*     */       
/* 177 */       !paramImageCapabilities.isAccelerated()))
/*     */     {
/* 179 */       return new BufImgVolatileSurfaceManager(this, paramObject);
/*     */     }
/* 181 */     SurfaceManagerFactory surfaceManagerFactory = SurfaceManagerFactory.getInstance();
/* 182 */     return surfaceManagerFactory.createVolatileManager(this, paramObject);
/*     */   }
/*     */   
/*     */   private Color getForeground() {
/* 186 */     if (this.comp != null) {
/* 187 */       return this.comp.getForeground();
/*     */     }
/* 189 */     return Color.black;
/*     */   }
/*     */ 
/*     */   
/*     */   private Color getBackground() {
/* 194 */     if (this.comp != null) {
/* 195 */       return this.comp.getBackground();
/*     */     }
/* 197 */     return Color.white;
/*     */   }
/*     */ 
/*     */   
/*     */   private Font getFont() {
/* 202 */     if (this.comp != null) {
/* 203 */       return this.comp.getFont();
/*     */     }
/* 205 */     if (this.defaultFont == null) {
/* 206 */       this.defaultFont = new Font("Dialog", 0, 12);
/*     */     }
/* 208 */     return this.defaultFont;
/*     */   }
/*     */ 
/*     */   
/*     */   public Graphics2D createGraphics() {
/* 213 */     return new SunGraphics2D(this.volSurfaceManager.getPrimarySurfaceData(), 
/* 214 */         getForeground(), 
/* 215 */         getBackground(), 
/* 216 */         getFont());
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String paramString, ImageObserver paramImageObserver) {
/* 221 */     if (paramString == null) {
/* 222 */       throw new NullPointerException("null property name is not allowed");
/*     */     }
/* 224 */     return Image.UndefinedProperty;
/*     */   }
/*     */   
/*     */   public int getWidth(ImageObserver paramImageObserver) {
/* 228 */     return getWidth();
/*     */   }
/*     */   
/*     */   public int getHeight(ImageObserver paramImageObserver) {
/* 232 */     return getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getBackupImage() {
/* 240 */     return this.graphicsConfig.createCompatibleImage(getWidth(), getHeight(), 
/* 241 */         getTransparency());
/*     */   }
/*     */   
/*     */   public BufferedImage getSnapshot() {
/* 245 */     BufferedImage bufferedImage = getBackupImage();
/* 246 */     Graphics2D graphics2D = bufferedImage.createGraphics();
/* 247 */     graphics2D.setComposite(AlphaComposite.Src);
/* 248 */     graphics2D.drawImage(this, 0, 0, (ImageObserver)null);
/* 249 */     graphics2D.dispose();
/* 250 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public int validate(GraphicsConfiguration paramGraphicsConfiguration) {
/* 254 */     return this.volSurfaceManager.validate(paramGraphicsConfiguration);
/*     */   }
/*     */   
/*     */   public boolean contentsLost() {
/* 258 */     return this.volSurfaceManager.contentsLost();
/*     */   }
/*     */   
/*     */   public ImageCapabilities getCapabilities() {
/* 262 */     return this.volSurfaceManager.getCapabilities(this.graphicsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Surface getDestSurface() {
/* 272 */     return this.volSurfaceManager.getPrimarySurfaceData();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/SunVolatileImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */