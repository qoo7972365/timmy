/*     */ package sun.misc;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cleaner
/*     */   extends PhantomReference<Object>
/*     */ {
/*  67 */   private static final ReferenceQueue<Object> dummyQueue = new ReferenceQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static Cleaner first = null;
/*     */   
/*  74 */   private Cleaner next = null; private Cleaner prev = null;
/*     */   
/*     */   private final Runnable thunk;
/*     */   
/*     */   private static synchronized Cleaner add(Cleaner paramCleaner) {
/*  79 */     if (first != null) {
/*  80 */       paramCleaner.next = first;
/*  81 */       first.prev = paramCleaner;
/*     */     } 
/*  83 */     first = paramCleaner;
/*  84 */     return paramCleaner;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized boolean remove(Cleaner paramCleaner) {
/*  90 */     if (paramCleaner.next == paramCleaner) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     if (first == paramCleaner)
/*  95 */       if (paramCleaner.next != null) {
/*  96 */         first = paramCleaner.next;
/*     */       } else {
/*  98 */         first = paramCleaner.prev;
/*     */       }  
/* 100 */     if (paramCleaner.next != null)
/* 101 */       paramCleaner.next.prev = paramCleaner.prev; 
/* 102 */     if (paramCleaner.prev != null) {
/* 103 */       paramCleaner.prev.next = paramCleaner.next;
/*     */     }
/*     */     
/* 106 */     paramCleaner.next = paramCleaner;
/* 107 */     paramCleaner.prev = paramCleaner;
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Cleaner(Object paramObject, Runnable paramRunnable) {
/* 115 */     super(paramObject, dummyQueue);
/* 116 */     this.thunk = paramRunnable;
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
/*     */   public static Cleaner create(Object paramObject, Runnable paramRunnable) {
/* 131 */     if (paramRunnable == null)
/* 132 */       return null; 
/* 133 */     return add(new Cleaner(paramObject, paramRunnable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean() {
/* 140 */     if (!remove(this))
/*     */       return; 
/*     */     try {
/* 143 */       this.thunk.run();
/* 144 */     } catch (Throwable throwable) {
/* 145 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 147 */               if (System.err != null)
/* 148 */                 (new Error("Cleaner terminated abnormally", x))
/* 149 */                   .printStackTrace(); 
/* 150 */               System.exit(1);
/* 151 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Cleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */