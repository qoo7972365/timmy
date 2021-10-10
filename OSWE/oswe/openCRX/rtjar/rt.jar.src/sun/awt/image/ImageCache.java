/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ImageCache
/*     */ {
/*  46 */   private final LinkedHashMap<PixelsKey, ImageSoftReference> map = new LinkedHashMap<>(16, 0.75F, true);
/*     */ 
/*     */   
/*     */   private final int maxPixelCount;
/*     */ 
/*     */   
/*  52 */   private int currentPixelCount = 0;
/*     */ 
/*     */   
/*  55 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*  57 */   private final ReferenceQueue<Image> referenceQueue = new ReferenceQueue<>();
/*     */   
/*     */   public static ImageCache getInstance() {
/*  60 */     return AppContext.<ImageCache>getSoftReferenceValue(ImageCache.class, () -> new ImageCache());
/*     */   }
/*     */ 
/*     */   
/*     */   ImageCache(int paramInt) {
/*  65 */     this.maxPixelCount = paramInt;
/*     */   }
/*     */   
/*     */   ImageCache() {
/*  69 */     this(2097152);
/*     */   }
/*     */   
/*     */   public void flush() {
/*  73 */     this.lock.writeLock().lock();
/*     */     try {
/*  75 */       this.map.clear();
/*     */     } finally {
/*  77 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Image getImage(PixelsKey paramPixelsKey) {
/*     */     ImageSoftReference imageSoftReference;
/*  83 */     this.lock.readLock().lock();
/*     */     try {
/*  85 */       imageSoftReference = this.map.get(paramPixelsKey);
/*     */     } finally {
/*  87 */       this.lock.readLock().unlock();
/*     */     } 
/*  89 */     return (imageSoftReference == null) ? null : imageSoftReference.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImage(PixelsKey paramPixelsKey, Image paramImage) {
/* 100 */     this.lock.writeLock().lock();
/*     */     try {
/* 102 */       ImageSoftReference imageSoftReference = this.map.get(paramPixelsKey);
/*     */ 
/*     */       
/* 105 */       if (imageSoftReference != null) {
/* 106 */         if (imageSoftReference.get() != null) {
/*     */           return;
/*     */         }
/*     */         
/* 110 */         this.currentPixelCount -= paramPixelsKey.getPixelCount();
/* 111 */         this.map.remove(paramPixelsKey);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 116 */       int i = paramPixelsKey.getPixelCount();
/* 117 */       this.currentPixelCount += i;
/*     */       
/* 119 */       if (this.currentPixelCount > this.maxPixelCount) {
/* 120 */         while ((imageSoftReference = (ImageSoftReference)this.referenceQueue.poll()) != null) {
/*     */           
/* 122 */           this.map.remove(imageSoftReference.key);
/* 123 */           this.currentPixelCount -= imageSoftReference.key.getPixelCount();
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 128 */       if (this.currentPixelCount > this.maxPixelCount) {
/*     */         
/* 130 */         Iterator<Map.Entry> iterator = this.map.entrySet().iterator();
/* 131 */         while (this.currentPixelCount > this.maxPixelCount && iterator.hasNext()) {
/*     */           
/* 133 */           Map.Entry entry = iterator.next();
/* 134 */           iterator.remove();
/* 135 */           Image image = ((ImageSoftReference)entry.getValue()).get();
/* 136 */           if (image != null) image.flush(); 
/* 137 */           this.currentPixelCount -= ((ImageSoftReference)entry.getValue()).key.getPixelCount();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 142 */       this.map.put(paramPixelsKey, new ImageSoftReference(paramPixelsKey, paramImage, this.referenceQueue));
/*     */     } finally {
/* 144 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ImageSoftReference
/*     */     extends SoftReference<Image>
/*     */   {
/*     */     final ImageCache.PixelsKey key;
/*     */ 
/*     */ 
/*     */     
/*     */     ImageSoftReference(ImageCache.PixelsKey param1PixelsKey, Image param1Image, ReferenceQueue<? super Image> param1ReferenceQueue) {
/* 159 */       super(param1Image, param1ReferenceQueue);
/* 160 */       this.key = param1PixelsKey;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface PixelsKey {
/*     */     int getPixelCount();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */