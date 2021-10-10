/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class OGLUtilities
/*     */ {
/*     */   public static final int UNDEFINED = 0;
/*     */   public static final int WINDOW = 1;
/*     */   public static final int PBUFFER = 2;
/*     */   public static final int TEXTURE = 3;
/*     */   public static final int FLIP_BACKBUFFER = 4;
/*     */   public static final int FBOBJECT = 5;
/*     */   
/*     */   public static boolean isQueueFlusherThread() {
/*  66 */     return OGLRenderQueue.isQueueFlusherThread();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean invokeWithOGLContextCurrent(Graphics paramGraphics, Runnable paramRunnable) {
/*  96 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/*  97 */     oGLRenderQueue.lock();
/*     */     try {
/*  99 */       if (paramGraphics != null) {
/* 100 */         if (!(paramGraphics instanceof SunGraphics2D)) {
/* 101 */           return false;
/*     */         }
/* 103 */         SurfaceData surfaceData = ((SunGraphics2D)paramGraphics).surfaceData;
/* 104 */         if (!(surfaceData instanceof OGLSurfaceData)) {
/* 105 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 109 */         OGLContext.validateContext((OGLSurfaceData)surfaceData);
/*     */       } 
/*     */ 
/*     */       
/* 113 */       oGLRenderQueue.flushAndInvokeNow(paramRunnable);
/*     */ 
/*     */ 
/*     */       
/* 117 */       OGLContext.invalidateCurrentContext();
/*     */     } finally {
/* 119 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */     
/* 122 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean invokeWithOGLSharedContextCurrent(GraphicsConfiguration paramGraphicsConfiguration, Runnable paramRunnable) {
/* 152 */     if (!(paramGraphicsConfiguration instanceof OGLGraphicsConfig)) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 157 */     oGLRenderQueue.lock();
/*     */     
/*     */     try {
/* 160 */       OGLContext.setScratchSurface((OGLGraphicsConfig)paramGraphicsConfiguration);
/*     */ 
/*     */       
/* 163 */       oGLRenderQueue.flushAndInvokeNow(paramRunnable);
/*     */ 
/*     */ 
/*     */       
/* 167 */       OGLContext.invalidateCurrentContext();
/*     */     } finally {
/* 169 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */     
/* 172 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Rectangle getOGLViewport(Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 199 */     if (!(paramGraphics instanceof SunGraphics2D)) {
/* 200 */       return null;
/*     */     }
/*     */     
/* 203 */     SunGraphics2D sunGraphics2D = (SunGraphics2D)paramGraphics;
/* 204 */     SurfaceData surfaceData = sunGraphics2D.surfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     int i = sunGraphics2D.transX;
/* 210 */     int j = sunGraphics2D.transY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     Rectangle rectangle = surfaceData.getBounds();
/* 216 */     int k = i;
/* 217 */     int m = rectangle.height - j + paramInt2;
/*     */     
/* 219 */     return new Rectangle(k, m, paramInt1, paramInt2);
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
/*     */   public static Rectangle getOGLScissorBox(Graphics paramGraphics) {
/* 240 */     if (!(paramGraphics instanceof SunGraphics2D)) {
/* 241 */       return null;
/*     */     }
/*     */     
/* 244 */     SunGraphics2D sunGraphics2D = (SunGraphics2D)paramGraphics;
/* 245 */     SurfaceData surfaceData = sunGraphics2D.surfaceData;
/* 246 */     Region region = sunGraphics2D.getCompClip();
/* 247 */     if (!region.isRectangular())
/*     */     {
/*     */ 
/*     */       
/* 251 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 256 */     int i = region.getLoX();
/* 257 */     int j = region.getLoY();
/*     */ 
/*     */     
/* 260 */     int k = region.getWidth();
/* 261 */     int m = region.getHeight();
/*     */ 
/*     */ 
/*     */     
/* 265 */     Rectangle rectangle = surfaceData.getBounds();
/* 266 */     int n = i;
/* 267 */     int i1 = rectangle.height - j + m;
/*     */     
/* 269 */     return new Rectangle(n, i1, k, m);
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
/*     */   public static Object getOGLSurfaceIdentifier(Graphics paramGraphics) {
/* 285 */     if (!(paramGraphics instanceof SunGraphics2D)) {
/* 286 */       return null;
/*     */     }
/* 288 */     return ((SunGraphics2D)paramGraphics).surfaceData;
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
/*     */   public static int getOGLSurfaceType(Graphics paramGraphics) {
/* 304 */     if (!(paramGraphics instanceof SunGraphics2D)) {
/* 305 */       return 0;
/*     */     }
/* 307 */     SurfaceData surfaceData = ((SunGraphics2D)paramGraphics).surfaceData;
/* 308 */     if (!(surfaceData instanceof OGLSurfaceData)) {
/* 309 */       return 0;
/*     */     }
/* 311 */     return ((OGLSurfaceData)surfaceData).getType();
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
/*     */   public static int getOGLTextureType(Graphics paramGraphics) {
/* 329 */     if (!(paramGraphics instanceof SunGraphics2D)) {
/* 330 */       return 0;
/*     */     }
/* 332 */     SurfaceData surfaceData = ((SunGraphics2D)paramGraphics).surfaceData;
/* 333 */     if (!(surfaceData instanceof OGLSurfaceData)) {
/* 334 */       return 0;
/*     */     }
/* 336 */     return ((OGLSurfaceData)surfaceData).getTextureTarget();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */