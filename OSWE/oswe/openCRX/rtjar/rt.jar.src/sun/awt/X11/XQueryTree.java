/*     */ package sun.awt.X11;
/*     */ 
/*     */ import sun.java2d.Disposer;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XQueryTree
/*     */ {
/*  33 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*     */   private boolean __executed = false;
/*     */   long _w;
/*  36 */   long root_ptr = unsafe.allocateMemory(Native.getLongSize());
/*  37 */   long parent_ptr = unsafe.allocateMemory(Native.getLongSize());
/*  38 */   long children_ptr = unsafe.allocateMemory(Native.getLongSize());
/*  39 */   long nchildren_ptr = unsafe.allocateMemory(Native.getIntSize());
/*     */   
/*     */   UnsafeXDisposerRecord disposer;
/*     */   
/*     */   public XQueryTree(long paramLong) {
/*  44 */     set_w(paramLong);
/*  45 */     Disposer.addRecord(this, this.disposer = new UnsafeXDisposerRecord("XQueryTree", new long[] { this.root_ptr, this.parent_ptr, this.nchildren_ptr }, new long[] { this.children_ptr }));
/*     */ 
/*     */     
/*  48 */     set_children(0L);
/*     */   }
/*     */   public int execute() {
/*  51 */     return execute(null);
/*     */   }
/*     */   public int execute(XErrorHandler paramXErrorHandler) {
/*  54 */     XToolkit.awtLock();
/*     */     try {
/*  56 */       if (isDisposed()) {
/*  57 */         throw new IllegalStateException("Disposed");
/*     */       }
/*  59 */       if (this.__executed) {
/*  60 */         throw new IllegalStateException("Already executed");
/*     */       }
/*  62 */       this.__executed = true;
/*  63 */       if (paramXErrorHandler != null) {
/*  64 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(paramXErrorHandler);
/*     */       }
/*  66 */       Native.putLong(this.children_ptr, 0L);
/*     */       
/*  68 */       int i = XlibWrapper.XQueryTree(XToolkit.getDisplay(), 
/*  69 */           get_w(), this.root_ptr, this.parent_ptr, this.children_ptr, this.nchildren_ptr);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       if (paramXErrorHandler != null) {
/*  75 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       }
/*  77 */       return i;
/*     */     } finally {
/*  79 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public boolean isExecuted() {
/*  83 */     return this.__executed;
/*     */   }
/*     */   
/*     */   public boolean isDisposed() {
/*  87 */     return this.disposer.disposed;
/*     */   }
/*     */   public void dispose() {
/*  90 */     XToolkit.awtLock();
/*     */     try {
/*  92 */       if (isDisposed()) {
/*     */         return;
/*     */       }
/*  95 */       this.disposer.dispose();
/*     */     } finally {
/*  97 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public long get_w() {
/* 101 */     if (isDisposed()) {
/* 102 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 104 */     if (!this.__executed) {
/* 105 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 107 */     return this._w;
/*     */   }
/*     */   public void set_w(long paramLong) {
/* 110 */     this._w = paramLong;
/*     */   }
/*     */   public long get_root() {
/* 113 */     if (isDisposed()) {
/* 114 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 116 */     if (!this.__executed) {
/* 117 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 119 */     return Native.getLong(this.root_ptr);
/*     */   }
/*     */   public void set_root(long paramLong) {
/* 122 */     Native.putLong(this.root_ptr, paramLong);
/*     */   }
/*     */   public long get_parent() {
/* 125 */     if (isDisposed()) {
/* 126 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 128 */     if (!this.__executed) {
/* 129 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 131 */     return Native.getLong(this.parent_ptr);
/*     */   }
/*     */   public void set_parent(long paramLong) {
/* 134 */     Native.putLong(this.parent_ptr, paramLong);
/*     */   }
/*     */   public long get_children() {
/* 137 */     if (isDisposed()) {
/* 138 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 140 */     if (!this.__executed) {
/* 141 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 143 */     return Native.getLong(this.children_ptr);
/*     */   }
/*     */   public void set_children(long paramLong) {
/* 146 */     Native.putLong(this.children_ptr, paramLong);
/*     */   }
/*     */   public int get_nchildren() {
/* 149 */     if (isDisposed()) {
/* 150 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 152 */     if (!this.__executed) {
/* 153 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 155 */     return Native.getInt(this.nchildren_ptr);
/*     */   }
/*     */   public void set_nchildren(int paramInt) {
/* 158 */     Native.putInt(this.nchildren_ptr, paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XQueryTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */