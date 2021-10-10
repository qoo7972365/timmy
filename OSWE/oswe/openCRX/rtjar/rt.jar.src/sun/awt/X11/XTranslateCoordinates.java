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
/*     */ public class XTranslateCoordinates
/*     */ {
/*  33 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*     */   private boolean __executed = false;
/*     */   long _scr_w;
/*     */   long _dest_w;
/*     */   int _src_x;
/*     */   int _src_y;
/*  39 */   long dest_x_ptr = unsafe.allocateMemory(Native.getIntSize());
/*  40 */   long dest_y_ptr = unsafe.allocateMemory(Native.getIntSize());
/*  41 */   long child_ptr = unsafe.allocateMemory(Native.getLongSize());
/*     */ 
/*     */   
/*     */   UnsafeXDisposerRecord disposer;
/*     */ 
/*     */   
/*     */   public XTranslateCoordinates(long paramLong1, long paramLong2, int paramInt1, int paramInt2) {
/*  48 */     set_scr_w(paramLong1);
/*  49 */     set_dest_w(paramLong2);
/*  50 */     set_src_x(paramInt1);
/*  51 */     set_src_y(paramInt2);
/*     */     
/*  53 */     Disposer.addRecord(this, this.disposer = new UnsafeXDisposerRecord("XTranslateCoordinates", new long[] { this.dest_x_ptr, this.dest_y_ptr, this.child_ptr }));
/*     */   }
/*     */ 
/*     */   
/*     */   public int execute() {
/*  58 */     return execute(null);
/*     */   }
/*     */   public int execute(XErrorHandler paramXErrorHandler) {
/*  61 */     XToolkit.awtLock();
/*     */     try {
/*  63 */       if (isDisposed()) {
/*  64 */         throw new IllegalStateException("Disposed");
/*     */       }
/*  66 */       if (this.__executed) {
/*  67 */         throw new IllegalStateException("Already executed");
/*     */       }
/*  69 */       this.__executed = true;
/*  70 */       if (paramXErrorHandler != null) {
/*  71 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(paramXErrorHandler);
/*     */       }
/*     */       
/*  74 */       int i = XlibWrapper.XTranslateCoordinates(XToolkit.getDisplay(), 
/*  75 */           get_scr_w(), 
/*  76 */           get_dest_w(), 
/*  77 */           get_src_x(), 
/*  78 */           get_src_y(), this.dest_x_ptr, this.dest_y_ptr, this.child_ptr);
/*     */ 
/*     */ 
/*     */       
/*  82 */       if (paramXErrorHandler != null) {
/*  83 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       }
/*  85 */       return i;
/*     */     } finally {
/*  87 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public boolean isExecuted() {
/*  91 */     return this.__executed;
/*     */   }
/*     */   
/*     */   public boolean isDisposed() {
/*  95 */     return this.disposer.disposed;
/*     */   }
/*     */   public void dispose() {
/*  98 */     XToolkit.awtLock();
/*     */     try {
/* 100 */       if (isDisposed()) {
/*     */         return;
/*     */       }
/* 103 */       this.disposer.dispose();
/*     */     } finally {
/* 105 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public long get_scr_w() {
/* 109 */     if (isDisposed()) {
/* 110 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 112 */     if (!this.__executed) {
/* 113 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 115 */     return this._scr_w;
/*     */   }
/*     */   public void set_scr_w(long paramLong) {
/* 118 */     this._scr_w = paramLong;
/*     */   }
/*     */   public long get_dest_w() {
/* 121 */     if (isDisposed()) {
/* 122 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 124 */     if (!this.__executed) {
/* 125 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 127 */     return this._dest_w;
/*     */   }
/*     */   public void set_dest_w(long paramLong) {
/* 130 */     this._dest_w = paramLong;
/*     */   }
/*     */   public int get_src_x() {
/* 133 */     if (isDisposed()) {
/* 134 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 136 */     if (!this.__executed) {
/* 137 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 139 */     return this._src_x;
/*     */   }
/*     */   public void set_src_x(int paramInt) {
/* 142 */     this._src_x = paramInt;
/*     */   }
/*     */   public int get_src_y() {
/* 145 */     if (isDisposed()) {
/* 146 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 148 */     if (!this.__executed) {
/* 149 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 151 */     return this._src_y;
/*     */   }
/*     */   public void set_src_y(int paramInt) {
/* 154 */     this._src_y = paramInt;
/*     */   }
/*     */   public int get_dest_x() {
/* 157 */     if (isDisposed()) {
/* 158 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 160 */     if (!this.__executed) {
/* 161 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 163 */     return Native.getInt(this.dest_x_ptr);
/*     */   }
/*     */   public void set_dest_x(int paramInt) {
/* 166 */     Native.putInt(this.dest_x_ptr, paramInt);
/*     */   }
/*     */   public int get_dest_y() {
/* 169 */     if (isDisposed()) {
/* 170 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 172 */     if (!this.__executed) {
/* 173 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 175 */     return Native.getInt(this.dest_y_ptr);
/*     */   }
/*     */   public void set_dest_y(int paramInt) {
/* 178 */     Native.putInt(this.dest_y_ptr, paramInt);
/*     */   }
/*     */   public long get_child() {
/* 181 */     if (isDisposed()) {
/* 182 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 184 */     if (!this.__executed) {
/* 185 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 187 */     return Native.getLong(this.child_ptr);
/*     */   }
/*     */   public void set_child(long paramLong) {
/* 190 */     Native.putLong(this.child_ptr, paramLong);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XTranslateCoordinates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */