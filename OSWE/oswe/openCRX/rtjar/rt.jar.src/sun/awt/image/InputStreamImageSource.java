/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ImageConsumer;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InputStreamImageSource
/*     */   implements ImageProducer, ImageFetchable
/*     */ {
/*     */   ImageConsumerQueue consumers;
/*     */   ImageDecoder decoder;
/*     */   ImageDecoder decoders;
/*     */   boolean awaitingFetch = false;
/*     */   
/*     */   abstract boolean checkSecurity(Object paramObject, boolean paramBoolean);
/*     */   
/*     */   int countConsumers(ImageConsumerQueue paramImageConsumerQueue) {
/*  47 */     byte b = 0;
/*  48 */     while (paramImageConsumerQueue != null) {
/*  49 */       b++;
/*  50 */       paramImageConsumerQueue = paramImageConsumerQueue.next;
/*     */     } 
/*  52 */     return b;
/*     */   }
/*     */   
/*     */   synchronized int countConsumers() {
/*  56 */     ImageDecoder imageDecoder = this.decoders;
/*  57 */     int i = countConsumers(this.consumers);
/*  58 */     while (imageDecoder != null) {
/*  59 */       i += countConsumers(imageDecoder.queue);
/*  60 */       imageDecoder = imageDecoder.next;
/*     */     } 
/*  62 */     return i;
/*     */   }
/*     */   
/*     */   public void addConsumer(ImageConsumer paramImageConsumer) {
/*  66 */     addConsumer(paramImageConsumer, false);
/*     */   }
/*     */   
/*     */   synchronized void printQueue(ImageConsumerQueue paramImageConsumerQueue, String paramString) {
/*  70 */     while (paramImageConsumerQueue != null) {
/*  71 */       System.out.println(paramString + paramImageConsumerQueue);
/*  72 */       paramImageConsumerQueue = paramImageConsumerQueue.next;
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void printQueues(String paramString) {
/*  77 */     System.out.println(paramString + "[ -----------");
/*  78 */     printQueue(this.consumers, "  ");
/*  79 */     for (ImageDecoder imageDecoder = this.decoders; imageDecoder != null; imageDecoder = imageDecoder.next) {
/*  80 */       System.out.println("    " + imageDecoder);
/*  81 */       printQueue(imageDecoder.queue, "      ");
/*     */     } 
/*  83 */     System.out.println("----------- ]" + paramString);
/*     */   }
/*     */   
/*     */   synchronized void addConsumer(ImageConsumer paramImageConsumer, boolean paramBoolean) {
/*  87 */     checkSecurity(null, false);
/*  88 */     for (ImageDecoder imageDecoder = this.decoders; imageDecoder != null; imageDecoder = imageDecoder.next) {
/*  89 */       if (imageDecoder.isConsumer(paramImageConsumer)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     ImageConsumerQueue imageConsumerQueue = this.consumers;
/*  95 */     while (imageConsumerQueue != null && imageConsumerQueue.consumer != paramImageConsumer) {
/*  96 */       imageConsumerQueue = imageConsumerQueue.next;
/*     */     }
/*  98 */     if (imageConsumerQueue == null) {
/*  99 */       imageConsumerQueue = new ImageConsumerQueue(this, paramImageConsumer);
/* 100 */       imageConsumerQueue.next = this.consumers;
/* 101 */       this.consumers = imageConsumerQueue;
/*     */     } else {
/* 103 */       if (!imageConsumerQueue.secure) {
/* 104 */         Object object = null;
/* 105 */         SecurityManager securityManager = System.getSecurityManager();
/* 106 */         if (securityManager != null) {
/* 107 */           object = securityManager.getSecurityContext();
/*     */         }
/* 109 */         if (imageConsumerQueue.securityContext == null) {
/* 110 */           imageConsumerQueue.securityContext = object;
/* 111 */         } else if (!imageConsumerQueue.securityContext.equals(object)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 117 */           errorConsumer(imageConsumerQueue, false);
/* 118 */           throw new SecurityException("Applets are trading image data!");
/*     */         } 
/*     */       } 
/* 121 */       imageConsumerQueue.interested = true;
/*     */     } 
/* 123 */     if (paramBoolean && this.decoder == null) {
/* 124 */       startProduction();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized boolean isConsumer(ImageConsumer paramImageConsumer) {
/* 129 */     for (ImageDecoder imageDecoder = this.decoders; imageDecoder != null; imageDecoder = imageDecoder.next) {
/* 130 */       if (imageDecoder.isConsumer(paramImageConsumer)) {
/* 131 */         return true;
/*     */       }
/*     */     } 
/* 134 */     return ImageConsumerQueue.isConsumer(this.consumers, paramImageConsumer);
/*     */   }
/*     */   
/*     */   private void errorAllConsumers(ImageConsumerQueue paramImageConsumerQueue, boolean paramBoolean) {
/* 138 */     while (paramImageConsumerQueue != null) {
/* 139 */       if (paramImageConsumerQueue.interested) {
/* 140 */         errorConsumer(paramImageConsumerQueue, paramBoolean);
/*     */       }
/* 142 */       paramImageConsumerQueue = paramImageConsumerQueue.next;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void errorConsumer(ImageConsumerQueue paramImageConsumerQueue, boolean paramBoolean) {
/* 147 */     paramImageConsumerQueue.consumer.imageComplete(1);
/* 148 */     if (paramBoolean && paramImageConsumerQueue.consumer instanceof ImageRepresentation) {
/* 149 */       ((ImageRepresentation)paramImageConsumerQueue.consumer).image.flush();
/*     */     }
/* 151 */     removeConsumer(paramImageConsumerQueue.consumer);
/*     */   }
/*     */   
/*     */   public synchronized void removeConsumer(ImageConsumer paramImageConsumer) {
/* 155 */     for (ImageDecoder imageDecoder = this.decoders; imageDecoder != null; imageDecoder = imageDecoder.next) {
/* 156 */       imageDecoder.removeConsumer(paramImageConsumer);
/*     */     }
/* 158 */     this.consumers = ImageConsumerQueue.removeConsumer(this.consumers, paramImageConsumer, false);
/*     */   }
/*     */   
/*     */   public void startProduction(ImageConsumer paramImageConsumer) {
/* 162 */     addConsumer(paramImageConsumer, true);
/*     */   }
/*     */   
/*     */   private synchronized void startProduction() {
/* 166 */     if (!this.awaitingFetch) {
/* 167 */       if (ImageFetcher.add(this)) {
/* 168 */         this.awaitingFetch = true;
/*     */       } else {
/* 170 */         ImageConsumerQueue imageConsumerQueue = this.consumers;
/* 171 */         this.consumers = null;
/* 172 */         errorAllConsumers(imageConsumerQueue, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void stopProduction() {
/* 178 */     if (this.awaitingFetch) {
/* 179 */       ImageFetcher.remove(this);
/* 180 */       this.awaitingFetch = false;
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
/*     */   public void requestTopDownLeftRightResend(ImageConsumer paramImageConsumer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ImageDecoder getDecoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ImageDecoder decoderForType(InputStream paramInputStream, String paramString) {
/* 210 */     return null;
/*     */   }
/*     */   
/*     */   protected ImageDecoder getDecoder(InputStream paramInputStream) {
/* 214 */     if (!paramInputStream.markSupported()) {
/* 215 */       paramInputStream = new BufferedInputStream(paramInputStream);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 220 */       paramInputStream.mark(8);
/* 221 */       int i = paramInputStream.read();
/* 222 */       int j = paramInputStream.read();
/* 223 */       int k = paramInputStream.read();
/* 224 */       int m = paramInputStream.read();
/* 225 */       int n = paramInputStream.read();
/* 226 */       int i1 = paramInputStream.read();
/*     */       
/* 228 */       int i2 = paramInputStream.read();
/* 229 */       int i3 = paramInputStream.read();
/*     */       
/* 231 */       paramInputStream.reset();
/* 232 */       paramInputStream.mark(-1);
/*     */       
/* 234 */       if (i == 71 && j == 73 && k == 70 && m == 56)
/* 235 */         return new GifImageDecoder(this, paramInputStream); 
/* 236 */       if (i == 255 && j == 216 && k == 255)
/* 237 */         return new JPEGImageDecoder(this, paramInputStream); 
/* 238 */       if (i == 35 && j == 100 && k == 101 && m == 102) {
/* 239 */         return new XbmImageDecoder(this, paramInputStream);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 244 */       if (i == 137 && j == 80 && k == 78 && m == 71 && n == 13 && i1 == 10 && i2 == 26 && i3 == 10)
/*     */       {
/*     */         
/* 247 */         return new PNGImageDecoder(this, paramInputStream);
/*     */       }
/*     */     }
/* 250 */     catch (IOException iOException) {}
/*     */ 
/*     */     
/* 253 */     return null;
/*     */   }
/*     */   
/*     */   public void doFetch() {
/* 257 */     synchronized (this) {
/* 258 */       if (this.consumers == null) {
/* 259 */         this.awaitingFetch = false;
/*     */         return;
/*     */       } 
/*     */     } 
/* 263 */     ImageDecoder imageDecoder = getDecoder();
/* 264 */     if (imageDecoder == null) {
/* 265 */       badDecoder();
/*     */     } else {
/* 267 */       setDecoder(imageDecoder);
/*     */       try {
/* 269 */         imageDecoder.produceImage();
/* 270 */       } catch (IOException iOException) {
/* 271 */         iOException.printStackTrace();
/*     */       }
/* 273 */       catch (ImageFormatException imageFormatException) {
/* 274 */         imageFormatException.printStackTrace();
/*     */       } finally {
/*     */         
/* 277 */         removeDecoder(imageDecoder);
/* 278 */         if (Thread.currentThread().isInterrupted() || !Thread.currentThread().isAlive()) {
/* 279 */           errorAllConsumers(imageDecoder.queue, true);
/*     */         } else {
/* 281 */           errorAllConsumers(imageDecoder.queue, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void badDecoder() {
/*     */     ImageConsumerQueue imageConsumerQueue;
/* 289 */     synchronized (this) {
/* 290 */       imageConsumerQueue = this.consumers;
/* 291 */       this.consumers = null;
/* 292 */       this.awaitingFetch = false;
/*     */     } 
/* 294 */     errorAllConsumers(imageConsumerQueue, false);
/*     */   }
/*     */   
/*     */   private void setDecoder(ImageDecoder paramImageDecoder) {
/*     */     ImageConsumerQueue imageConsumerQueue;
/* 299 */     synchronized (this) {
/* 300 */       paramImageDecoder.next = this.decoders;
/* 301 */       this.decoders = paramImageDecoder;
/* 302 */       this.decoder = paramImageDecoder;
/* 303 */       imageConsumerQueue = this.consumers;
/* 304 */       paramImageDecoder.queue = imageConsumerQueue;
/* 305 */       this.consumers = null;
/* 306 */       this.awaitingFetch = false;
/*     */     } 
/* 308 */     while (imageConsumerQueue != null) {
/* 309 */       if (imageConsumerQueue.interested)
/*     */       {
/*     */         
/* 312 */         if (!checkSecurity(imageConsumerQueue.securityContext, true)) {
/* 313 */           errorConsumer(imageConsumerQueue, false);
/*     */         }
/*     */       }
/* 316 */       imageConsumerQueue = imageConsumerQueue.next;
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void removeDecoder(ImageDecoder paramImageDecoder) {
/* 321 */     doneDecoding(paramImageDecoder);
/* 322 */     ImageDecoder imageDecoder1 = null;
/* 323 */     for (ImageDecoder imageDecoder2 = this.decoders; imageDecoder2 != null; imageDecoder2 = imageDecoder2.next) {
/* 324 */       if (imageDecoder2 == paramImageDecoder) {
/* 325 */         if (imageDecoder1 == null) {
/* 326 */           this.decoders = imageDecoder2.next; break;
/*     */         } 
/* 328 */         imageDecoder1.next = imageDecoder2.next;
/*     */         
/*     */         break;
/*     */       } 
/* 332 */       imageDecoder1 = imageDecoder2;
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void doneDecoding(ImageDecoder paramImageDecoder) {
/* 337 */     if (this.decoder == paramImageDecoder) {
/* 338 */       this.decoder = null;
/* 339 */       if (this.consumers != null) {
/* 340 */         startProduction();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void latchConsumers(ImageDecoder paramImageDecoder) {
/* 346 */     doneDecoding(paramImageDecoder);
/*     */   }
/*     */   
/*     */   synchronized void flush() {
/* 350 */     this.decoder = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/InputStreamImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */