/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.RenderQueue;
/*     */ import sun.java2d.pipe.hw.AccelGraphicsConfig;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StrikeCache
/*     */ {
/*  66 */   static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*  68 */   static ReferenceQueue refQueue = Disposer.getQueue();
/*     */   
/*  70 */   static ArrayList<GlyphDisposedListener> disposeListeners = new ArrayList<>(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   static int MINSTRIKES = 8;
/*  96 */   static int recentStrikeIndex = 0;
/*     */ 
/*     */   
/*     */   static FontStrike[] recentStrikes;
/*     */ 
/*     */   
/*     */   static boolean cacheRefTypeWeak;
/*     */ 
/*     */   
/*     */   static int nativeAddressSize;
/*     */ 
/*     */   
/*     */   static int glyphInfoSize;
/*     */ 
/*     */   
/*     */   static int xAdvanceOffset;
/*     */ 
/*     */   
/*     */   static int yAdvanceOffset;
/*     */   
/*     */   static int boundsOffset;
/*     */   
/*     */   static int widthOffset;
/*     */   
/*     */   static int heightOffset;
/*     */   
/*     */   static int rowBytesOffset;
/*     */   
/*     */   static int topLeftXOffset;
/*     */   
/*     */   static int topLeftYOffset;
/*     */   
/*     */   static int pixelDataOffset;
/*     */   
/*     */   static int cacheCellOffset;
/*     */   
/*     */   static int managedOffset;
/*     */   
/*     */   static long invisibleGlyphPtr;
/*     */ 
/*     */   
/*     */   static {
/* 138 */     long[] arrayOfLong = new long[13];
/* 139 */     getGlyphCacheDescription(arrayOfLong);
/*     */ 
/*     */     
/* 142 */     nativeAddressSize = (int)arrayOfLong[0];
/* 143 */     glyphInfoSize = (int)arrayOfLong[1];
/* 144 */     xAdvanceOffset = (int)arrayOfLong[2];
/* 145 */     yAdvanceOffset = (int)arrayOfLong[3];
/* 146 */     widthOffset = (int)arrayOfLong[4];
/* 147 */     heightOffset = (int)arrayOfLong[5];
/* 148 */     rowBytesOffset = (int)arrayOfLong[6];
/* 149 */     topLeftXOffset = (int)arrayOfLong[7];
/* 150 */     topLeftYOffset = (int)arrayOfLong[8];
/* 151 */     pixelDataOffset = (int)arrayOfLong[9];
/* 152 */     invisibleGlyphPtr = arrayOfLong[10];
/* 153 */     cacheCellOffset = (int)arrayOfLong[11];
/* 154 */     managedOffset = (int)arrayOfLong[12];
/*     */     
/* 156 */     if (nativeAddressSize < 4) {
/* 157 */       throw new InternalError("Unexpected address size for font data: " + nativeAddressSize);
/*     */     }
/*     */ 
/*     */     
/* 161 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Object run()
/*     */           {
/* 172 */             String str1 = System.getProperty("sun.java2d.font.reftype", "soft");
/* 173 */             StrikeCache.cacheRefTypeWeak = str1.equals("weak");
/*     */ 
/*     */             
/* 176 */             String str2 = System.getProperty("sun.java2d.font.minstrikes");
/* 177 */             if (str2 != null) {
/*     */               try {
/* 179 */                 StrikeCache.MINSTRIKES = Integer.parseInt(str2);
/* 180 */                 if (StrikeCache.MINSTRIKES <= 0) {
/* 181 */                   StrikeCache.MINSTRIKES = 1;
/*     */                 }
/* 183 */               } catch (NumberFormatException numberFormatException) {}
/*     */             }
/*     */ 
/*     */             
/* 187 */             StrikeCache.recentStrikes = new FontStrike[StrikeCache.MINSTRIKES];
/*     */             
/* 189 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static void refStrike(FontStrike paramFontStrike) {
/* 196 */     int i = recentStrikeIndex;
/* 197 */     recentStrikes[i] = paramFontStrike;
/* 198 */     i++;
/* 199 */     if (i == MINSTRIKES) {
/* 200 */       i = 0;
/*     */     }
/* 202 */     recentStrikeIndex = i;
/*     */   }
/*     */   
/*     */   private static final void doDispose(FontStrikeDisposer paramFontStrikeDisposer) {
/* 206 */     if (paramFontStrikeDisposer.intGlyphImages != null) {
/* 207 */       freeCachedIntMemory(paramFontStrikeDisposer.intGlyphImages, paramFontStrikeDisposer.pScalerContext);
/*     */     }
/* 209 */     else if (paramFontStrikeDisposer.longGlyphImages != null) {
/* 210 */       freeCachedLongMemory(paramFontStrikeDisposer.longGlyphImages, paramFontStrikeDisposer.pScalerContext);
/*     */     }
/* 212 */     else if (paramFontStrikeDisposer.segIntGlyphImages != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       for (byte b = 0; b < paramFontStrikeDisposer.segIntGlyphImages.length; b++) {
/* 218 */         if (paramFontStrikeDisposer.segIntGlyphImages[b] != null) {
/* 219 */           freeCachedIntMemory(paramFontStrikeDisposer.segIntGlyphImages[b], paramFontStrikeDisposer.pScalerContext);
/*     */ 
/*     */           
/* 222 */           paramFontStrikeDisposer.pScalerContext = 0L;
/* 223 */           paramFontStrikeDisposer.segIntGlyphImages[b] = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 229 */       if (paramFontStrikeDisposer.pScalerContext != 0L) {
/* 230 */         freeCachedIntMemory(new int[0], paramFontStrikeDisposer.pScalerContext);
/*     */       }
/* 232 */     } else if (paramFontStrikeDisposer.segLongGlyphImages != null) {
/* 233 */       for (byte b = 0; b < paramFontStrikeDisposer.segLongGlyphImages.length; b++) {
/* 234 */         if (paramFontStrikeDisposer.segLongGlyphImages[b] != null) {
/* 235 */           freeCachedLongMemory(paramFontStrikeDisposer.segLongGlyphImages[b], paramFontStrikeDisposer.pScalerContext);
/*     */           
/* 237 */           paramFontStrikeDisposer.pScalerContext = 0L;
/* 238 */           paramFontStrikeDisposer.segLongGlyphImages[b] = null;
/*     */         } 
/*     */       } 
/* 241 */       if (paramFontStrikeDisposer.pScalerContext != 0L) {
/* 242 */         freeCachedLongMemory(new long[0], paramFontStrikeDisposer.pScalerContext);
/*     */       }
/* 244 */     } else if (paramFontStrikeDisposer.pScalerContext != 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (longAddresses()) {
/* 250 */         freeCachedLongMemory(new long[0], paramFontStrikeDisposer.pScalerContext);
/*     */       } else {
/* 252 */         freeCachedIntMemory(new int[0], paramFontStrikeDisposer.pScalerContext);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean longAddresses() {
/* 258 */     return (nativeAddressSize == 8);
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
/*     */   static void disposeStrike(final FontStrikeDisposer disposer) {
/* 275 */     if (Disposer.pollingQueue) {
/* 276 */       doDispose(disposer);
/*     */       
/*     */       return;
/*     */     } 
/* 280 */     RenderQueue renderQueue = null;
/*     */     
/* 282 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 283 */     if (!GraphicsEnvironment.isHeadless()) {
/*     */       
/* 285 */       GraphicsConfiguration graphicsConfiguration = graphicsEnvironment.getDefaultScreenDevice().getDefaultConfiguration();
/* 286 */       if (graphicsConfiguration instanceof AccelGraphicsConfig) {
/* 287 */         AccelGraphicsConfig accelGraphicsConfig = (AccelGraphicsConfig)graphicsConfiguration;
/* 288 */         BufferedContext bufferedContext = accelGraphicsConfig.getContext();
/* 289 */         if (bufferedContext != null) {
/* 290 */           renderQueue = bufferedContext.getRenderQueue();
/*     */         }
/*     */       } 
/*     */     } 
/* 294 */     if (renderQueue != null) {
/* 295 */       renderQueue.lock();
/*     */       try {
/* 297 */         renderQueue.flushAndInvokeNow(new Runnable() {
/*     */               public void run() {
/* 299 */                 StrikeCache.doDispose(disposer);
/* 300 */                 Disposer.pollRemove();
/*     */               }
/*     */             });
/*     */       } finally {
/* 304 */         renderQueue.unlock();
/*     */       } 
/*     */     } else {
/* 307 */       doDispose(disposer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void freeCachedIntMemory(int[] paramArrayOfint, long paramLong) {
/* 317 */     synchronized (disposeListeners) {
/* 318 */       if (disposeListeners.size() > 0) {
/* 319 */         ArrayList<Long> arrayList = null;
/*     */         
/* 321 */         for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 322 */           if (paramArrayOfint[b] != 0 && unsafe.getByte((paramArrayOfint[b] + managedOffset)) == 0) {
/*     */             
/* 324 */             if (arrayList == null) {
/* 325 */               arrayList = new ArrayList();
/*     */             }
/* 327 */             arrayList.add(Long.valueOf(paramArrayOfint[b]));
/*     */           } 
/*     */         } 
/*     */         
/* 331 */         if (arrayList != null)
/*     */         {
/*     */           
/* 334 */           notifyDisposeListeners(arrayList);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     freeIntMemory(paramArrayOfint, paramLong);
/*     */   }
/*     */   
/*     */   private static void freeCachedLongMemory(long[] paramArrayOflong, long paramLong) {
/* 343 */     synchronized (disposeListeners) {
/* 344 */       if (disposeListeners.size() > 0) {
/* 345 */         ArrayList<Long> arrayList = null;
/*     */         
/* 347 */         for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 348 */           if (paramArrayOflong[b] != 0L && unsafe
/* 349 */             .getByte(paramArrayOflong[b] + managedOffset) == 0) {
/*     */             
/* 351 */             if (arrayList == null) {
/* 352 */               arrayList = new ArrayList();
/*     */             }
/* 354 */             arrayList.add(Long.valueOf(paramArrayOflong[b]));
/*     */           } 
/*     */         } 
/*     */         
/* 358 */         if (arrayList != null)
/*     */         {
/*     */           
/* 361 */           notifyDisposeListeners(arrayList);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     freeLongMemory(paramArrayOflong, paramLong);
/*     */   }
/*     */   
/*     */   public static void addGlyphDisposedListener(GlyphDisposedListener paramGlyphDisposedListener) {
/* 370 */     synchronized (disposeListeners) {
/* 371 */       disposeListeners.add(paramGlyphDisposedListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void notifyDisposeListeners(ArrayList<Long> paramArrayList) {
/* 376 */     for (GlyphDisposedListener glyphDisposedListener : disposeListeners) {
/* 377 */       glyphDisposedListener.glyphDisposed(paramArrayList);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Reference getStrikeRef(FontStrike paramFontStrike) {
/* 382 */     return getStrikeRef(paramFontStrike, cacheRefTypeWeak);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Reference getStrikeRef(FontStrike paramFontStrike, boolean paramBoolean) {
/* 393 */     if (paramFontStrike.disposer == null) {
/* 394 */       if (paramBoolean) {
/* 395 */         return new WeakReference<>(paramFontStrike);
/*     */       }
/* 397 */       return new SoftReference<>(paramFontStrike);
/*     */     } 
/*     */ 
/*     */     
/* 401 */     if (paramBoolean) {
/* 402 */       return new WeakDisposerRef(paramFontStrike);
/*     */     }
/* 404 */     return new SoftDisposerRef(paramFontStrike);
/*     */   }
/*     */   static native void getGlyphCacheDescription(long[] paramArrayOflong);
/*     */   static native void freeIntPointer(int paramInt);
/*     */   static native void freeLongPointer(long paramLong);
/*     */   
/*     */   private static native void freeIntMemory(int[] paramArrayOfint, long paramLong);
/*     */   
/*     */   private static native void freeLongMemory(long[] paramArrayOflong, long paramLong);
/*     */   
/*     */   static interface DisposableStrike {
/*     */     FontStrikeDisposer getDisposer(); }
/*     */   
/*     */   static class SoftDisposerRef extends SoftReference implements DisposableStrike { public FontStrikeDisposer getDisposer() {
/* 418 */       return this.disposer;
/*     */     }
/*     */     private FontStrikeDisposer disposer;
/*     */     SoftDisposerRef(FontStrike param1FontStrike) {
/* 422 */       super((T)param1FontStrike, StrikeCache.refQueue);
/* 423 */       this.disposer = param1FontStrike.disposer;
/* 424 */       Disposer.addReference(this, this.disposer);
/*     */     } }
/*     */ 
/*     */   
/*     */   static class WeakDisposerRef
/*     */     extends WeakReference
/*     */     implements DisposableStrike {
/*     */     private FontStrikeDisposer disposer;
/*     */     
/*     */     public FontStrikeDisposer getDisposer() {
/* 434 */       return this.disposer;
/*     */     }
/*     */     
/*     */     WeakDisposerRef(FontStrike param1FontStrike) {
/* 438 */       super((T)param1FontStrike, StrikeCache.refQueue);
/* 439 */       this.disposer = param1FontStrike.disposer;
/* 440 */       Disposer.addReference(this, this.disposer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/StrikeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */