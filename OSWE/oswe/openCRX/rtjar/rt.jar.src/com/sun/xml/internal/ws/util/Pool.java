/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Pool<T>
/*     */ {
/*     */   private volatile WeakReference<ConcurrentLinkedQueue<T>> queue;
/*     */   
/*     */   public final T take() {
/*  65 */     T t = getQueue().poll();
/*  66 */     if (t == null)
/*  67 */       return create(); 
/*  68 */     return t;
/*     */   }
/*     */   
/*     */   private ConcurrentLinkedQueue<T> getQueue() {
/*  72 */     WeakReference<ConcurrentLinkedQueue<T>> q = this.queue;
/*  73 */     if (q != null) {
/*  74 */       ConcurrentLinkedQueue<T> concurrentLinkedQueue = q.get();
/*  75 */       if (concurrentLinkedQueue != null) {
/*  76 */         return concurrentLinkedQueue;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     ConcurrentLinkedQueue<T> d = new ConcurrentLinkedQueue<>();
/*  81 */     this.queue = new WeakReference<>(d);
/*     */     
/*  83 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void recycle(T t) {
/*  90 */     getQueue().offer(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Marshaller
/*     */     extends Pool<javax.xml.bind.Marshaller>
/*     */   {
/*     */     private final JAXBContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Marshaller(JAXBContext context) {
/* 114 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     protected javax.xml.bind.Marshaller create() {
/*     */       try {
/* 120 */         return this.context.createMarshaller();
/* 121 */       } catch (JAXBException e) {
/*     */         
/* 123 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Unmarshaller
/*     */     extends Pool<javax.xml.bind.Unmarshaller>
/*     */   {
/*     */     private final JAXBContext context;
/*     */     
/*     */     public Unmarshaller(JAXBContext context) {
/* 135 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     protected javax.xml.bind.Unmarshaller create() {
/*     */       try {
/* 141 */         return this.context.createUnmarshaller();
/* 142 */       } catch (JAXBException e) {
/*     */         
/* 144 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class TubePool
/*     */     extends Pool<Tube>
/*     */   {
/*     */     private final Tube master;
/*     */     
/*     */     public TubePool(Tube master) {
/* 156 */       this.master = master;
/* 157 */       recycle(master);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Tube create() {
/* 162 */       return TubeCloner.clone(this.master);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public final Tube takeMaster() {
/* 173 */       return this.master;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/Pool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */