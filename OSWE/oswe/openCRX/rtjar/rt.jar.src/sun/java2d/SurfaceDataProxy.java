/*     */ package sun.java2d;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.DisplayChangedListener;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.java2d.loops.Blit;
/*     */ import sun.java2d.loops.BlitBg;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SurfaceDataProxy
/*     */   implements DisplayChangedListener, SurfaceManager.FlushableCacheData
/*     */ {
/*     */   private static boolean cachingAllowed = true;
/*     */   
/*     */   static {
/*  74 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.managedimages"));
/*     */     
/*  76 */     if (str1 != null && str1.equals("false")) {
/*  77 */       cachingAllowed = false;
/*  78 */       System.out.println("Disabling managed images");
/*     */     } 
/*     */   }
/*  81 */   private static int defaultThreshold = 1; static {
/*  82 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.accthreshold"));
/*     */     
/*  84 */     if (str2 != null) {
/*     */       try {
/*  86 */         int i = Integer.parseInt(str2);
/*  87 */         if (i >= 0) {
/*  88 */           defaultThreshold = i;
/*  89 */           System.out.println("New Default Acceleration Threshold: " + defaultThreshold);
/*     */         }
/*     */       
/*  92 */       } catch (NumberFormatException numberFormatException) {
/*  93 */         System.err.println("Error setting new threshold:" + numberFormatException);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isCachingAllowed() {
/*  99 */     return cachingAllowed;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateTracker getRetryTracker(SurfaceData paramSurfaceData) {
/* 137 */     return new CountdownTracker(this.threshold);
/*     */   }
/*     */   
/*     */   public static class CountdownTracker implements StateTracker {
/*     */     private int countdown;
/*     */     
/*     */     public CountdownTracker(int param1Int) {
/* 144 */       this.countdown = param1Int;
/*     */     }
/*     */     
/*     */     public synchronized boolean isCurrent() {
/* 148 */       return (--this.countdown >= 0);
/*     */     }
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
/* 162 */   public static SurfaceDataProxy UNCACHED = new SurfaceDataProxy(0)
/*     */     {
/*     */       public boolean isAccelerated() {
/* 165 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isSupportedOperation(SurfaceData param1SurfaceData, int param1Int, CompositeType param1CompositeType, Color param1Color) {
/* 174 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public SurfaceData validateSurfaceData(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, int param1Int1, int param1Int2) {
/* 182 */         throw new InternalError("UNCACHED should never validate SDs");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public SurfaceData replaceData(SurfaceData param1SurfaceData, int param1Int, CompositeType param1CompositeType, Color param1Color) {
/* 192 */         return param1SurfaceData;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StateTracker srcTracker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int numtries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SurfaceData cachedSD;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StateTracker cacheTracker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean valid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy() {
/* 243 */     this(defaultThreshold);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy(int paramInt) {
/* 253 */     this.threshold = paramInt;
/*     */     
/* 255 */     this.srcTracker = StateTracker.NEVER_CURRENT;
/*     */     
/* 257 */     this.cacheTracker = StateTracker.NEVER_CURRENT;
/*     */     
/* 259 */     this.valid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 268 */     return this.valid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 277 */     this.valid = false;
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
/*     */   public boolean flush(boolean paramBoolean) {
/* 290 */     if (paramBoolean) {
/* 291 */       invalidate();
/*     */     }
/* 293 */     flush();
/* 294 */     return !isValid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 302 */     SurfaceData surfaceData = this.cachedSD;
/* 303 */     this.cachedSD = null;
/* 304 */     this.cacheTracker = StateTracker.NEVER_CURRENT;
/* 305 */     if (surfaceData != null) {
/* 306 */       surfaceData.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAccelerated() {
/* 316 */     return (isValid() && this.srcTracker
/* 317 */       .isCurrent() && this.cacheTracker
/* 318 */       .isCurrent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void activateDisplayListener() {
/* 328 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/* 334 */       ((SunGraphicsEnvironment)graphicsEnvironment).addDisplayChangedListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayChanged() {
/* 343 */     flush();
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
/*     */   public void paletteChanged() {
/* 362 */     this.srcTracker = StateTracker.NEVER_CURRENT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData replaceData(SurfaceData paramSurfaceData, int paramInt, CompositeType paramCompositeType, Color paramColor) {
/* 398 */     if (isSupportedOperation(paramSurfaceData, paramInt, paramCompositeType, paramColor)) {
/*     */       
/* 400 */       if (!this.srcTracker.isCurrent()) {
/* 401 */         synchronized (this) {
/* 402 */           this.numtries = this.threshold;
/* 403 */           this.srcTracker = paramSurfaceData.getStateTracker();
/* 404 */           this.cacheTracker = StateTracker.NEVER_CURRENT;
/*     */         } 
/*     */         
/* 407 */         if (!this.srcTracker.isCurrent()) {
/*     */           
/* 409 */           if (paramSurfaceData.getState() == StateTrackable.State.UNTRACKABLE) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 414 */             invalidate();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 419 */             flush();
/*     */           } 
/* 421 */           return paramSurfaceData;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 426 */       SurfaceData surfaceData = this.cachedSD;
/* 427 */       if (!this.cacheTracker.isCurrent()) {
/*     */         
/* 429 */         synchronized (this) {
/* 430 */           if (this.numtries > 0) {
/* 431 */             this.numtries--;
/* 432 */             return paramSurfaceData;
/*     */           } 
/*     */         } 
/*     */         
/* 436 */         Rectangle rectangle = paramSurfaceData.getBounds();
/* 437 */         int i = rectangle.width;
/* 438 */         int j = rectangle.height;
/*     */ 
/*     */ 
/*     */         
/* 442 */         StateTracker stateTracker = this.srcTracker;
/*     */         
/* 444 */         surfaceData = validateSurfaceData(paramSurfaceData, surfaceData, i, j);
/* 445 */         if (surfaceData == null) {
/* 446 */           synchronized (this) {
/* 447 */             if (stateTracker == this.srcTracker) {
/* 448 */               this.cacheTracker = getRetryTracker(paramSurfaceData);
/* 449 */               this.cachedSD = null;
/*     */             } 
/*     */           } 
/* 452 */           return paramSurfaceData;
/*     */         } 
/*     */         
/* 455 */         updateSurfaceData(paramSurfaceData, surfaceData, i, j);
/* 456 */         if (!surfaceData.isValid()) {
/* 457 */           return paramSurfaceData;
/*     */         }
/*     */         
/* 460 */         synchronized (this) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 466 */           if (stateTracker == this.srcTracker && stateTracker.isCurrent()) {
/* 467 */             this.cacheTracker = surfaceData.getStateTracker();
/* 468 */             this.cachedSD = surfaceData;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 473 */       if (surfaceData != null) {
/* 474 */         return surfaceData;
/*     */       }
/*     */     } 
/*     */     
/* 478 */     return paramSurfaceData;
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
/*     */   public void updateSurfaceData(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2) {
/* 493 */     SurfaceType surfaceType1 = paramSurfaceData1.getSurfaceType();
/* 494 */     SurfaceType surfaceType2 = paramSurfaceData2.getSurfaceType();
/* 495 */     Blit blit = Blit.getFromCache(surfaceType1, CompositeType.SrcNoEa, surfaceType2);
/*     */ 
/*     */     
/* 498 */     blit.Blit(paramSurfaceData1, paramSurfaceData2, AlphaComposite.Src, null, 0, 0, 0, 0, paramInt1, paramInt2);
/*     */ 
/*     */     
/* 501 */     paramSurfaceData2.markDirty();
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
/*     */   public void updateSurfaceDataBg(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2, Color paramColor) {
/* 518 */     SurfaceType surfaceType1 = paramSurfaceData1.getSurfaceType();
/* 519 */     SurfaceType surfaceType2 = paramSurfaceData2.getSurfaceType();
/* 520 */     BlitBg blitBg = BlitBg.getFromCache(surfaceType1, CompositeType.SrcNoEa, surfaceType2);
/*     */ 
/*     */     
/* 523 */     blitBg.BlitBg(paramSurfaceData1, paramSurfaceData2, AlphaComposite.Src, null, paramColor
/* 524 */         .getRGB(), 0, 0, 0, 0, paramInt1, paramInt2);
/*     */     
/* 526 */     paramSurfaceData2.markDirty();
/*     */   }
/*     */   
/*     */   public abstract boolean isSupportedOperation(SurfaceData paramSurfaceData, int paramInt, CompositeType paramCompositeType, Color paramColor);
/*     */   
/*     */   public abstract SurfaceData validateSurfaceData(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/SurfaceDataProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */