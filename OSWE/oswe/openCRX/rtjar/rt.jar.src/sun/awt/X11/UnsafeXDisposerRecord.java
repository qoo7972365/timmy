/*    */ package sun.awt.X11;
/*    */ 
/*    */ import sun.java2d.DisposerRecord;
/*    */ import sun.misc.Unsafe;
/*    */ import sun.util.logging.PlatformLogger;
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
/*    */ class UnsafeXDisposerRecord
/*    */   implements DisposerRecord
/*    */ {
/* 31 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.UnsafeXDisposerRecord"); final long[] unsafe_ptrs; final long[] x_ptrs; final String name;
/* 32 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*    */   
/*    */   volatile boolean disposed;
/*    */   final Throwable place;
/*    */   
/*    */   public UnsafeXDisposerRecord(String paramString, long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 38 */     this.unsafe_ptrs = paramArrayOflong1;
/* 39 */     this.x_ptrs = paramArrayOflong2;
/* 40 */     this.name = paramString;
/* 41 */     if (XlibWrapper.isBuildInternal) {
/* 42 */       this.place = new Throwable();
/*    */     } else {
/* 44 */       this.place = null;
/*    */     } 
/*    */   }
/*    */   public UnsafeXDisposerRecord(String paramString, long... paramVarArgs) {
/* 48 */     this.unsafe_ptrs = paramVarArgs;
/* 49 */     this.x_ptrs = null;
/* 50 */     this.name = paramString;
/* 51 */     if (XlibWrapper.isBuildInternal) {
/* 52 */       this.place = new Throwable();
/*    */     } else {
/* 54 */       this.place = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 59 */     XToolkit.awtLock();
/*    */     try {
/* 61 */       if (!this.disposed) {
/* 62 */         if (XlibWrapper.isBuildInternal && "Java2D Disposer".equals(Thread.currentThread().getName()) && log.isLoggable(PlatformLogger.Level.WARNING)) {
/* 63 */           if (this.place != null) {
/* 64 */             log.warning(this.name + " object was not disposed before finalization!", this.place);
/*    */           } else {
/* 66 */             log.warning(this.name + " object was not disposed before finalization!");
/*    */           } 
/*    */         }
/*    */         
/* 70 */         if (this.unsafe_ptrs != null) {
/* 71 */           for (long l : this.unsafe_ptrs) {
/* 72 */             if (l != 0L) {
/* 73 */               unsafe.freeMemory(l);
/*    */             }
/*    */           } 
/*    */         }
/* 77 */         if (this.x_ptrs != null) {
/* 78 */           for (long l : this.x_ptrs) {
/* 79 */             if (l != 0L) {
/* 80 */               if (Native.getLong(l) != 0L) {
/* 81 */                 XlibWrapper.XFree(Native.getLong(l));
/*    */               }
/* 83 */               unsafe.freeMemory(l);
/*    */             } 
/*    */           } 
/*    */         }
/* 87 */         this.disposed = true;
/*    */       } 
/*    */     } finally {
/* 90 */       XToolkit.awtUnlock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/UnsafeXDisposerRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */