/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageConsumer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ImageDecoder
/*     */ {
/*     */   InputStreamImageSource source;
/*     */   InputStream input;
/*     */   Thread feeder;
/*     */   protected boolean aborted;
/*     */   protected boolean finished;
/*     */   ImageConsumerQueue queue;
/*     */   ImageDecoder next;
/*     */   
/*     */   public ImageDecoder(InputStreamImageSource paramInputStreamImageSource, InputStream paramInputStream) {
/*  44 */     this.source = paramInputStreamImageSource;
/*  45 */     this.input = paramInputStream;
/*  46 */     this.feeder = Thread.currentThread();
/*     */   }
/*     */   
/*     */   public boolean isConsumer(ImageConsumer paramImageConsumer) {
/*  50 */     return ImageConsumerQueue.isConsumer(this.queue, paramImageConsumer);
/*     */   }
/*     */   
/*     */   public void removeConsumer(ImageConsumer paramImageConsumer) {
/*  54 */     this.queue = ImageConsumerQueue.removeConsumer(this.queue, paramImageConsumer, false);
/*  55 */     if (!this.finished && this.queue == null) {
/*  56 */       abort();
/*     */     }
/*     */   }
/*     */   
/*     */   protected ImageConsumerQueue nextConsumer(ImageConsumerQueue paramImageConsumerQueue) {
/*  61 */     synchronized (this.source) {
/*  62 */       if (this.aborted) {
/*  63 */         return null;
/*     */       }
/*  65 */       paramImageConsumerQueue = (paramImageConsumerQueue == null) ? this.queue : paramImageConsumerQueue.next;
/*  66 */       while (paramImageConsumerQueue != null) {
/*  67 */         if (paramImageConsumerQueue.interested) {
/*  68 */           return paramImageConsumerQueue;
/*     */         }
/*  70 */         paramImageConsumerQueue = paramImageConsumerQueue.next;
/*     */       } 
/*     */     } 
/*  73 */     return null;
/*     */   }
/*     */   
/*     */   protected int setDimensions(int paramInt1, int paramInt2) {
/*  77 */     ImageConsumerQueue imageConsumerQueue = null;
/*  78 */     byte b = 0;
/*  79 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/*  80 */       imageConsumerQueue.consumer.setDimensions(paramInt1, paramInt2);
/*  81 */       b++;
/*     */     } 
/*  83 */     return b;
/*     */   }
/*     */   
/*     */   protected int setProperties(Hashtable<?, ?> paramHashtable) {
/*  87 */     ImageConsumerQueue imageConsumerQueue = null;
/*  88 */     byte b = 0;
/*  89 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/*  90 */       imageConsumerQueue.consumer.setProperties(paramHashtable);
/*  91 */       b++;
/*     */     } 
/*  93 */     return b;
/*     */   }
/*     */   
/*     */   protected int setColorModel(ColorModel paramColorModel) {
/*  97 */     ImageConsumerQueue imageConsumerQueue = null;
/*  98 */     byte b = 0;
/*  99 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/* 100 */       imageConsumerQueue.consumer.setColorModel(paramColorModel);
/* 101 */       b++;
/*     */     } 
/* 103 */     return b;
/*     */   }
/*     */   
/*     */   protected int setHints(int paramInt) {
/* 107 */     ImageConsumerQueue imageConsumerQueue = null;
/* 108 */     byte b = 0;
/* 109 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/* 110 */       imageConsumerQueue.consumer.setHints(paramInt);
/* 111 */       b++;
/*     */     } 
/* 113 */     return b;
/*     */   }
/*     */   
/*     */   protected void headerComplete() {
/* 117 */     this.feeder.setPriority(3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 122 */     this.source.latchConsumers(this);
/* 123 */     ImageConsumerQueue imageConsumerQueue = null;
/* 124 */     byte b = 0;
/* 125 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/* 126 */       imageConsumerQueue.consumer.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfbyte, paramInt5, paramInt6);
/* 127 */       b++;
/*     */     } 
/* 129 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 134 */     this.source.latchConsumers(this);
/* 135 */     ImageConsumerQueue imageConsumerQueue = null;
/* 136 */     byte b = 0;
/* 137 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/* 138 */       imageConsumerQueue.consumer.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfint, paramInt5, paramInt6);
/* 139 */       b++;
/*     */     } 
/* 141 */     return b;
/*     */   }
/*     */   
/*     */   protected int imageComplete(int paramInt, boolean paramBoolean) {
/* 145 */     this.source.latchConsumers(this);
/* 146 */     if (paramBoolean) {
/* 147 */       this.finished = true;
/* 148 */       this.source.doneDecoding(this);
/*     */     } 
/* 150 */     ImageConsumerQueue imageConsumerQueue = null;
/* 151 */     byte b = 0;
/* 152 */     while ((imageConsumerQueue = nextConsumer(imageConsumerQueue)) != null) {
/* 153 */       imageConsumerQueue.consumer.imageComplete(paramInt);
/* 154 */       b++;
/*     */     } 
/* 156 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void produceImage() throws IOException, ImageFormatException;
/*     */   
/*     */   public void abort() {
/* 163 */     this.aborted = true;
/* 164 */     this.source.doneDecoding(this);
/* 165 */     close();
/* 166 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 169 */             ImageDecoder.this.feeder.interrupt();
/* 170 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public synchronized void close() {
/* 176 */     if (this.input != null)
/*     */       try {
/* 178 */         this.input.close();
/* 179 */       } catch (IOException iOException) {} 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */