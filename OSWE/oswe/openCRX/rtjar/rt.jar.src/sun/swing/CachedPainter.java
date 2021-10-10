/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CachedPainter
/*     */ {
/*  56 */   private static final Map<Object, ImageCache> cacheMap = new HashMap<>();
/*     */   
/*     */   private static ImageCache getCache(Object paramObject) {
/*  59 */     synchronized (CachedPainter.class) {
/*  60 */       ImageCache imageCache = cacheMap.get(paramObject);
/*  61 */       if (imageCache == null) {
/*  62 */         imageCache = new ImageCache(1);
/*  63 */         cacheMap.put(paramObject, imageCache);
/*     */       } 
/*  65 */       return imageCache;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachedPainter(int paramInt) {
/*  76 */     getCache(getClass()).setMaxCount(paramInt);
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
/*     */   public void paint(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object... paramVarArgs) {
/*  94 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */       return;
/*     */     }
/*  97 */     synchronized (CachedPainter.class) {
/*  98 */       paint0(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramVarArgs);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paint0(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object... paramVarArgs) {
/* 104 */     Class<?> clazz = getClass();
/* 105 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration(paramComponent);
/* 106 */     ImageCache imageCache = getCache(clazz);
/* 107 */     Image image = imageCache.getImage(clazz, graphicsConfiguration, paramInt3, paramInt4, paramVarArgs);
/* 108 */     byte b = 0;
/*     */     do {
/* 110 */       boolean bool = false;
/* 111 */       if (image instanceof VolatileImage)
/*     */       {
/* 113 */         switch (((VolatileImage)image).validate(graphicsConfiguration)) {
/*     */           case 2:
/* 115 */             ((VolatileImage)image).flush();
/* 116 */             image = null;
/*     */             break;
/*     */           case 1:
/* 119 */             bool = true;
/*     */             break;
/*     */         } 
/*     */       }
/* 123 */       if (image == null) {
/*     */         
/* 125 */         image = createImage(paramComponent, paramInt3, paramInt4, graphicsConfiguration, paramVarArgs);
/* 126 */         imageCache.setImage(clazz, graphicsConfiguration, paramInt3, paramInt4, paramVarArgs, image);
/* 127 */         bool = true;
/*     */       } 
/* 129 */       if (bool) {
/*     */         
/* 131 */         Graphics graphics = image.getGraphics();
/* 132 */         paintToImage(paramComponent, image, graphics, paramInt3, paramInt4, paramVarArgs);
/* 133 */         graphics.dispose();
/*     */       } 
/*     */ 
/*     */       
/* 137 */       paintImage(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, image, paramVarArgs);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 142 */     while (image instanceof VolatileImage && ((VolatileImage)image)
/* 143 */       .contentsLost() && ++b < 3);
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
/*     */   protected abstract void paintToImage(Component paramComponent, Image paramImage, Graphics paramGraphics, int paramInt1, int paramInt2, Object[] paramArrayOfObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintImage(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Image paramImage, Object[] paramArrayOfObject) {
/* 175 */     paramGraphics.drawImage(paramImage, paramInt1, paramInt2, null);
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
/*     */   protected Image createImage(Component paramComponent, int paramInt1, int paramInt2, GraphicsConfiguration paramGraphicsConfiguration, Object[] paramArrayOfObject) {
/* 192 */     if (paramGraphicsConfiguration == null) {
/* 193 */       return new BufferedImage(paramInt1, paramInt2, 1);
/*     */     }
/* 195 */     return paramGraphicsConfiguration.createCompatibleVolatileImage(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void flush() {
/* 202 */     synchronized (CachedPainter.class) {
/* 203 */       getCache(getClass()).flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private GraphicsConfiguration getGraphicsConfiguration(Component paramComponent) {
/* 208 */     if (paramComponent == null) {
/* 209 */       return null;
/*     */     }
/* 211 */     return paramComponent.getGraphicsConfiguration();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/CachedPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */