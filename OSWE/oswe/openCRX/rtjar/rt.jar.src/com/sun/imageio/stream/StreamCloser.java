/*     */ package com.sun.imageio.stream;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamCloser
/*     */ {
/*     */   private static WeakHashMap<CloseAction, Object> toCloseQueue;
/*     */   private static Thread streamCloser;
/*     */   
/*     */   public static void addToQueue(CloseAction paramCloseAction) {
/*  50 */     synchronized (StreamCloser.class) {
/*  51 */       if (toCloseQueue == null) {
/*  52 */         toCloseQueue = new WeakHashMap<>();
/*     */       }
/*     */ 
/*     */       
/*  56 */       toCloseQueue.put(paramCloseAction, null);
/*     */       
/*  58 */       if (streamCloser == null) {
/*  59 */         final Runnable streamCloserRunnable = new Runnable() {
/*     */             public void run() {
/*  61 */               if (StreamCloser.toCloseQueue != null) {
/*  62 */                 synchronized (StreamCloser.class) {
/*     */                   
/*  64 */                   Set set = StreamCloser.toCloseQueue.keySet();
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*  69 */                   StreamCloser.CloseAction[] arrayOfCloseAction = new StreamCloser.CloseAction[set.size()];
/*  70 */                   arrayOfCloseAction = (StreamCloser.CloseAction[])set.toArray((Object[])arrayOfCloseAction);
/*  71 */                   for (StreamCloser.CloseAction closeAction : arrayOfCloseAction) {
/*  72 */                     if (closeAction != null) {
/*     */                       try {
/*  74 */                         closeAction.performAction();
/*  75 */                       } catch (IOException iOException) {}
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             }
/*     */           };
/*     */ 
/*     */         
/*  84 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public Object run()
/*     */               {
/*  92 */                 ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/*  93 */                 ThreadGroup threadGroup2 = threadGroup1;
/*  94 */                 while (threadGroup2 != null) {
/*  95 */                   threadGroup1 = threadGroup2; threadGroup2 = threadGroup1.getParent();
/*  96 */                 }  StreamCloser.streamCloser = new Thread(threadGroup1, streamCloserRunnable);
/*     */ 
/*     */ 
/*     */                 
/* 100 */                 StreamCloser.streamCloser.setContextClassLoader(null);
/* 101 */                 Runtime.getRuntime().addShutdownHook(StreamCloser.streamCloser);
/* 102 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void removeFromQueue(CloseAction paramCloseAction) {
/* 110 */     synchronized (StreamCloser.class) {
/* 111 */       if (toCloseQueue != null) {
/* 112 */         toCloseQueue.remove(paramCloseAction);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static CloseAction createCloseAction(ImageInputStream paramImageInputStream) {
/* 118 */     return new CloseAction(paramImageInputStream);
/*     */   }
/*     */   
/*     */   public static final class CloseAction {
/*     */     private ImageInputStream iis;
/*     */     
/*     */     private CloseAction(ImageInputStream param1ImageInputStream) {
/* 125 */       this.iis = param1ImageInputStream;
/*     */     }
/*     */     
/*     */     public void performAction() throws IOException {
/* 129 */       if (this.iis != null)
/* 130 */         this.iis.close(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/stream/StreamCloser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */