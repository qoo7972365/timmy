/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ImageCache
/*     */ {
/*  47 */   private final LinkedHashMap<Integer, PixelCountSoftReference> map = new LinkedHashMap<>(16, 0.75F, true);
/*     */ 
/*     */   
/*     */   private final int maxPixelCount;
/*     */   
/*     */   private final int maxSingleImagePixelSize;
/*     */   
/*  54 */   private int currentPixelCount = 0;
/*     */   
/*  56 */   private ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*  58 */   private ReferenceQueue<Image> referenceQueue = new ReferenceQueue<>();
/*     */   
/*  60 */   private static final ImageCache instance = new ImageCache();
/*     */ 
/*     */ 
/*     */   
/*     */   static ImageCache getInstance() {
/*  65 */     return instance;
/*     */   }
/*     */   
/*     */   public ImageCache() {
/*  69 */     this.maxPixelCount = 2097152;
/*  70 */     this.maxSingleImagePixelSize = 90000;
/*     */   }
/*     */   
/*     */   public ImageCache(int paramInt1, int paramInt2) {
/*  74 */     this.maxPixelCount = paramInt1;
/*  75 */     this.maxSingleImagePixelSize = paramInt2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() {
/*  80 */     this.lock.readLock().lock();
/*     */     try {
/*  82 */       this.map.clear();
/*     */     } finally {
/*  84 */       this.lock.readLock().unlock();
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
/*     */   public boolean isImageCachable(int paramInt1, int paramInt2) {
/*  96 */     return (paramInt1 * paramInt2 < this.maxSingleImagePixelSize);
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
/*     */   public Image getImage(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object... paramVarArgs) {
/* 109 */     this.lock.readLock().lock();
/*     */     try {
/* 111 */       PixelCountSoftReference pixelCountSoftReference = this.map.get(Integer.valueOf(hash(paramGraphicsConfiguration, paramInt1, paramInt2, paramVarArgs)));
/*     */       
/* 113 */       if (pixelCountSoftReference != null && pixelCountSoftReference.equals(paramGraphicsConfiguration, paramInt1, paramInt2, paramVarArgs)) {
/* 114 */         return pixelCountSoftReference.get();
/*     */       }
/* 116 */       return null;
/*     */     } finally {
/*     */       
/* 119 */       this.lock.readLock().unlock();
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
/*     */   public boolean setImage(Image paramImage, GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object... paramVarArgs) {
/* 134 */     if (!isImageCachable(paramInt1, paramInt2)) return false; 
/* 135 */     int i = hash(paramGraphicsConfiguration, paramInt1, paramInt2, paramVarArgs);
/* 136 */     this.lock.writeLock().lock();
/*     */     try {
/* 138 */       PixelCountSoftReference pixelCountSoftReference = this.map.get(Integer.valueOf(i));
/*     */       
/* 140 */       if (pixelCountSoftReference != null && pixelCountSoftReference.get() == paramImage) {
/* 141 */         return true;
/*     */       }
/*     */       
/* 144 */       if (pixelCountSoftReference != null) {
/* 145 */         this.currentPixelCount -= pixelCountSoftReference.pixelCount;
/* 146 */         this.map.remove(Integer.valueOf(i));
/*     */       } 
/*     */       
/* 149 */       int j = paramImage.getWidth(null) * paramImage.getHeight(null);
/* 150 */       this.currentPixelCount += j;
/*     */       
/* 152 */       if (this.currentPixelCount > this.maxPixelCount) {
/* 153 */         while ((pixelCountSoftReference = (PixelCountSoftReference)this.referenceQueue.poll()) != null) {
/*     */           
/* 155 */           this.map.remove(Integer.valueOf(pixelCountSoftReference.hash));
/* 156 */           this.currentPixelCount -= pixelCountSoftReference.pixelCount;
/*     */         } 
/*     */       }
/*     */       
/* 160 */       if (this.currentPixelCount > this.maxPixelCount) {
/* 161 */         Iterator<Map.Entry> iterator = this.map.entrySet().iterator();
/* 162 */         while (this.currentPixelCount > this.maxPixelCount && iterator.hasNext()) {
/* 163 */           Map.Entry entry = iterator.next();
/* 164 */           iterator.remove();
/* 165 */           Image image = ((PixelCountSoftReference)entry.getValue()).get();
/* 166 */           if (image != null) image.flush(); 
/* 167 */           this.currentPixelCount -= ((PixelCountSoftReference)entry.getValue()).pixelCount;
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       this.map.put(Integer.valueOf(i), new PixelCountSoftReference(paramImage, this.referenceQueue, j, i, paramGraphicsConfiguration, paramInt1, paramInt2, paramVarArgs));
/* 172 */       return true;
/*     */     } finally {
/* 174 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int hash(GraphicsConfiguration paramGraphicsConfiguration, int paramInt1, int paramInt2, Object... paramVarArgs) {
/* 181 */     int i = (paramGraphicsConfiguration != null) ? paramGraphicsConfiguration.hashCode() : 0;
/* 182 */     i = 31 * i + paramInt1;
/* 183 */     i = 31 * i + paramInt2;
/* 184 */     i = 31 * i + Arrays.deepHashCode(paramVarArgs);
/* 185 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PixelCountSoftReference
/*     */     extends SoftReference<Image>
/*     */   {
/*     */     private final int pixelCount;
/*     */     
/*     */     private final int hash;
/*     */     private final GraphicsConfiguration config;
/*     */     private final int w;
/*     */     private final int h;
/*     */     private final Object[] args;
/*     */     
/*     */     public PixelCountSoftReference(Image param1Image, ReferenceQueue<? super Image> param1ReferenceQueue, int param1Int1, int param1Int2, GraphicsConfiguration param1GraphicsConfiguration, int param1Int3, int param1Int4, Object[] param1ArrayOfObject) {
/* 201 */       super(param1Image, param1ReferenceQueue);
/* 202 */       this.pixelCount = param1Int1;
/* 203 */       this.hash = param1Int2;
/* 204 */       this.config = param1GraphicsConfiguration;
/* 205 */       this.w = param1Int3;
/* 206 */       this.h = param1Int4;
/* 207 */       this.args = param1ArrayOfObject;
/*     */     }
/*     */     
/*     */     public boolean equals(GraphicsConfiguration param1GraphicsConfiguration, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 211 */       return (param1GraphicsConfiguration == this.config && param1Int1 == this.w && param1Int2 == this.h && 
/*     */ 
/*     */         
/* 214 */         Arrays.equals(param1ArrayOfObject, this.args));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ImageCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */