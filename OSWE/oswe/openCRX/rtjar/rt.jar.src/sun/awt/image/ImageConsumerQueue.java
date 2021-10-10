/*    */ package sun.awt.image;
/*    */ 
/*    */ import java.awt.image.ImageConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ImageConsumerQueue
/*    */ {
/*    */   ImageConsumerQueue next;
/*    */   ImageConsumer consumer;
/*    */   boolean interested;
/*    */   Object securityContext;
/*    */   boolean secure;
/*    */   
/*    */   static ImageConsumerQueue removeConsumer(ImageConsumerQueue paramImageConsumerQueue, ImageConsumer paramImageConsumer, boolean paramBoolean) {
/* 43 */     ImageConsumerQueue imageConsumerQueue1 = null;
/* 44 */     for (ImageConsumerQueue imageConsumerQueue2 = paramImageConsumerQueue; imageConsumerQueue2 != null; imageConsumerQueue2 = imageConsumerQueue2.next) {
/* 45 */       if (imageConsumerQueue2.consumer == paramImageConsumer) {
/* 46 */         if (imageConsumerQueue1 == null) {
/* 47 */           paramImageConsumerQueue = imageConsumerQueue2.next;
/*    */         } else {
/* 49 */           imageConsumerQueue1.next = imageConsumerQueue2.next;
/*    */         } 
/* 51 */         imageConsumerQueue2.interested = paramBoolean;
/*    */         break;
/*    */       } 
/* 54 */       imageConsumerQueue1 = imageConsumerQueue2;
/*    */     } 
/* 56 */     return paramImageConsumerQueue;
/*    */   }
/*    */   
/*    */   static boolean isConsumer(ImageConsumerQueue paramImageConsumerQueue, ImageConsumer paramImageConsumer) {
/* 60 */     for (ImageConsumerQueue imageConsumerQueue = paramImageConsumerQueue; imageConsumerQueue != null; imageConsumerQueue = imageConsumerQueue.next) {
/* 61 */       if (imageConsumerQueue.consumer == paramImageConsumer) {
/* 62 */         return true;
/*    */       }
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   ImageConsumerQueue(InputStreamImageSource paramInputStreamImageSource, ImageConsumer paramImageConsumer) {
/* 69 */     this.consumer = paramImageConsumer;
/* 70 */     this.interested = true;
/*    */     
/* 72 */     if (paramImageConsumer instanceof ImageRepresentation) {
/* 73 */       ImageRepresentation imageRepresentation = (ImageRepresentation)paramImageConsumer;
/* 74 */       if (imageRepresentation.image.source != paramInputStreamImageSource) {
/* 75 */         throw new SecurityException("ImageRep added to wrong image source");
/*    */       }
/* 77 */       this.secure = true;
/*    */     } else {
/* 79 */       SecurityManager securityManager = System.getSecurityManager();
/* 80 */       if (securityManager != null) {
/* 81 */         this.securityContext = securityManager.getSecurityContext();
/*    */       } else {
/* 83 */         this.securityContext = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 89 */     return "[" + this.consumer + ", " + (this.interested ? "" : "not ") + "interested" + ((this.securityContext != null) ? (", " + this.securityContext) : "") + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageConsumerQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */