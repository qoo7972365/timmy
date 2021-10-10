/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.ActiveEvent;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvocationEvent
/*     */   extends AWTEvent
/*     */   implements ActiveEvent
/*     */ {
/*     */   public static final int INVOCATION_FIRST = 1200;
/*     */   public static final int INVOCATION_DEFAULT = 1200;
/*     */   public static final int INVOCATION_LAST = 1200;
/*     */   protected Runnable runnable;
/*     */   protected volatile Object notifier;
/*     */   private final Runnable listener;
/*     */   
/*     */   static {
/*  62 */     AWTAccessor.setInvocationEventAccessor(new AWTAccessor.InvocationEventAccessor()
/*     */         {
/*     */           public void dispose(InvocationEvent param1InvocationEvent) {
/*  65 */             param1InvocationEvent.finishedDispatching(false);
/*     */           }
/*     */         });
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
/*     */   private volatile boolean dispatched = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean catchExceptions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private Exception exception = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private Throwable throwable = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long when;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 436056344909459450L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvocationEvent(Object paramObject, Runnable paramRunnable) {
/* 171 */     this(paramObject, 1200, paramRunnable, null, null, false);
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
/*     */   
/*     */   public InvocationEvent(Object paramObject1, Runnable paramRunnable, Object paramObject2, boolean paramBoolean) {
/* 210 */     this(paramObject1, 1200, paramRunnable, paramObject2, null, paramBoolean);
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
/*     */   public InvocationEvent(Object paramObject, Runnable paramRunnable1, Runnable paramRunnable2, boolean paramBoolean) {
/* 242 */     this(paramObject, 1200, paramRunnable1, null, paramRunnable2, paramBoolean);
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
/*     */   protected InvocationEvent(Object paramObject1, int paramInt, Runnable paramRunnable, Object paramObject2, boolean paramBoolean) {
/* 279 */     this(paramObject1, paramInt, paramRunnable, paramObject2, null, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private InvocationEvent(Object paramObject1, int paramInt, Runnable paramRunnable1, Object paramObject2, Runnable paramRunnable2, boolean paramBoolean) {
/* 284 */     super(paramObject1, paramInt);
/* 285 */     this.runnable = paramRunnable1;
/* 286 */     this.notifier = paramObject2;
/* 287 */     this.listener = paramRunnable2;
/* 288 */     this.catchExceptions = paramBoolean;
/* 289 */     this.when = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispatch() {
/*     */     try {
/* 299 */       if (this.catchExceptions) {
/*     */         try {
/* 301 */           this.runnable.run();
/*     */         }
/* 303 */         catch (Throwable throwable) {
/* 304 */           if (throwable instanceof Exception) {
/* 305 */             this.exception = (Exception)throwable;
/*     */           }
/* 307 */           this.throwable = throwable;
/*     */         } 
/*     */       } else {
/*     */         
/* 311 */         this.runnable.run();
/*     */       } 
/*     */     } finally {
/* 314 */       finishedDispatching(true);
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
/*     */   public Exception getException() {
/* 327 */     return this.catchExceptions ? this.exception : null;
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
/*     */   public Throwable getThrowable() {
/* 340 */     return this.catchExceptions ? this.throwable : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWhen() {
/* 350 */     return this.when;
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
/*     */   public boolean isDispatched() {
/* 384 */     return this.dispatched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishedDispatching(boolean paramBoolean) {
/* 393 */     this.dispatched = paramBoolean;
/*     */     
/* 395 */     if (this.notifier != null) {
/* 396 */       synchronized (this.notifier) {
/* 397 */         this.notifier.notifyAll();
/*     */       } 
/*     */     }
/*     */     
/* 401 */     if (this.listener != null) {
/* 402 */       this.listener.run();
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
/*     */   public String paramString() {
/* 414 */     switch (this.id)
/*     */     { case 1200:
/* 416 */         str = "INVOCATION_DEFAULT";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 421 */         return str + ",runnable=" + this.runnable + ",notifier=" + this.notifier + ",catchExceptions=" + this.catchExceptions + ",when=" + this.when; }  String str = "unknown type"; return str + ",runnable=" + this.runnable + ",notifier=" + this.notifier + ",catchExceptions=" + this.catchExceptions + ",when=" + this.when;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/InvocationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */