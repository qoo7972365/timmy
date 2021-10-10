/*     */ package java.awt.image;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilteredImageSource
/*     */   implements ImageProducer
/*     */ {
/*     */   ImageProducer src;
/*     */   ImageFilter filter;
/*     */   private Hashtable proxies;
/*     */   
/*     */   public FilteredImageSource(ImageProducer paramImageProducer, ImageFilter paramImageFilter) {
/*  67 */     this.src = paramImageProducer;
/*  68 */     this.filter = paramImageFilter;
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
/*     */   public synchronized void addConsumer(ImageConsumer paramImageConsumer) {
/*  96 */     if (this.proxies == null) {
/*  97 */       this.proxies = new Hashtable<>();
/*     */     }
/*  99 */     if (!this.proxies.containsKey(paramImageConsumer)) {
/* 100 */       ImageFilter imageFilter = this.filter.getFilterInstance(paramImageConsumer);
/* 101 */       this.proxies.put(paramImageConsumer, imageFilter);
/* 102 */       this.src.addConsumer(imageFilter);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isConsumer(ImageConsumer paramImageConsumer) {
/* 122 */     return (this.proxies != null && this.proxies.containsKey(paramImageConsumer));
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
/*     */   public synchronized void removeConsumer(ImageConsumer paramImageConsumer) {
/* 139 */     if (this.proxies != null) {
/* 140 */       ImageFilter imageFilter = (ImageFilter)this.proxies.get(paramImageConsumer);
/* 141 */       if (imageFilter != null) {
/* 142 */         this.src.removeConsumer(imageFilter);
/* 143 */         this.proxies.remove(paramImageConsumer);
/* 144 */         if (this.proxies.isEmpty()) {
/* 145 */           this.proxies = null;
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void startProduction(ImageConsumer paramImageConsumer) {
/* 175 */     if (this.proxies == null) {
/* 176 */       this.proxies = new Hashtable<>();
/*     */     }
/* 178 */     ImageFilter imageFilter = (ImageFilter)this.proxies.get(paramImageConsumer);
/* 179 */     if (imageFilter == null) {
/* 180 */       imageFilter = this.filter.getFilterInstance(paramImageConsumer);
/* 181 */       this.proxies.put(paramImageConsumer, imageFilter);
/*     */     } 
/* 183 */     this.src.startProduction(imageFilter);
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
/*     */   public synchronized void requestTopDownLeftRightResend(ImageConsumer paramImageConsumer) {
/* 202 */     if (this.proxies != null) {
/* 203 */       ImageFilter imageFilter = (ImageFilter)this.proxies.get(paramImageConsumer);
/* 204 */       if (imageFilter != null)
/* 205 */         imageFilter.resendTopDownLeftRight(this.src); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/FilteredImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */