/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class WindowPropertyGetter
/*     */ {
/*  32 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*  33 */   private final long actual_type = unsafe.allocateMemory(8L);
/*  34 */   private final long actual_format = unsafe.allocateMemory(4L);
/*  35 */   private final long nitems_ptr = unsafe.allocateMemory(8L);
/*  36 */   private final long bytes_after = unsafe.allocateMemory(8L);
/*  37 */   private final long data = unsafe.allocateMemory(8L);
/*     */   private final long window;
/*     */   private final XAtom property;
/*     */   private final long offset;
/*     */   private final long length;
/*     */   private final boolean auto_delete;
/*     */   private final long type;
/*     */   private boolean executed = false;
/*     */   UnsafeXDisposerRecord disposer;
/*     */   
/*     */   public WindowPropertyGetter(long paramLong1, XAtom paramXAtom, long paramLong2, long paramLong3, boolean paramBoolean, long paramLong4) {
/*  48 */     if (paramXAtom.getAtom() == 0L) {
/*  49 */       throw new IllegalArgumentException("Property ATOM should be initialized first:" + paramXAtom);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (paramLong1 == 0L) {
/*  56 */       throw new IllegalArgumentException("Window must not be zero");
/*     */     }
/*  58 */     this.window = paramLong1;
/*  59 */     this.property = paramXAtom;
/*  60 */     this.offset = paramLong2;
/*  61 */     this.length = paramLong3;
/*  62 */     this.auto_delete = paramBoolean;
/*  63 */     this.type = paramLong4;
/*     */     
/*  65 */     Native.putLong(this.data, 0L);
/*  66 */     Disposer.addRecord(this, this.disposer = new UnsafeXDisposerRecord("WindowPropertyGetter", new long[] { this.actual_type, this.actual_format, this.nitems_ptr, this.bytes_after }, new long[] { this.data }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowPropertyGetter(long paramLong1, XAtom paramXAtom1, long paramLong2, long paramLong3, boolean paramBoolean, XAtom paramXAtom2) {
/*  73 */     this(paramLong1, paramXAtom1, paramLong2, paramLong3, paramBoolean, paramXAtom2.getAtom());
/*     */   }
/*     */   public int execute() {
/*  76 */     return execute(null);
/*     */   }
/*     */   
/*     */   public int execute(XErrorHandler paramXErrorHandler) {
/*  80 */     XToolkit.awtLock();
/*     */     try {
/*  82 */       if (isDisposed()) {
/*  83 */         throw new IllegalStateException("Disposed");
/*     */       }
/*  85 */       if (this.executed) {
/*  86 */         throw new IllegalStateException("Already executed");
/*     */       }
/*  88 */       this.executed = true;
/*     */       
/*  90 */       if (isCachingSupported() && isCached()) {
/*  91 */         readFromCache();
/*  92 */         return 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  97 */       if (paramXErrorHandler instanceof XErrorHandler.IgnoreBadWindowHandler) {
/*  98 */         paramXErrorHandler = null;
/*     */       }
/*     */       
/* 101 */       if (paramXErrorHandler != null) {
/* 102 */         XErrorHandlerUtil.WITH_XERROR_HANDLER(paramXErrorHandler);
/*     */       }
/* 104 */       Native.putLong(this.data, 0L);
/* 105 */       int i = XlibWrapper.XGetWindowProperty(XToolkit.getDisplay(), this.window, this.property.getAtom(), this.offset, this.length, (this.auto_delete ? 1L : 0L), this.type, this.actual_type, this.actual_format, this.nitems_ptr, this.bytes_after, this.data);
/*     */ 
/*     */ 
/*     */       
/* 109 */       if (isCachingSupported() && i == 0 && getData() != 0L && isCacheableProperty(this.property))
/*     */       {
/* 111 */         cacheProperty();
/*     */       }
/*     */       
/* 114 */       if (paramXErrorHandler != null) {
/* 115 */         XErrorHandlerUtil.RESTORE_XERROR_HANDLER();
/*     */       }
/* 117 */       return i;
/*     */     } finally {
/* 119 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isExecuted() {
/* 124 */     return this.executed;
/*     */   }
/*     */   
/*     */   public boolean isDisposed() {
/* 128 */     return this.disposer.disposed;
/*     */   }
/*     */   
/*     */   public int getActualFormat() {
/* 132 */     if (isDisposed()) {
/* 133 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 135 */     if (!this.executed) {
/* 136 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 138 */     return unsafe.getInt(this.actual_format);
/*     */   }
/*     */   public long getActualType() {
/* 141 */     if (isDisposed()) {
/* 142 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 144 */     if (!this.executed) {
/* 145 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 147 */     return XAtom.getAtom(this.actual_type);
/*     */   }
/*     */   public int getNumberOfItems() {
/* 150 */     if (isDisposed()) {
/* 151 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 153 */     if (!this.executed) {
/* 154 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 156 */     return (int)Native.getLong(this.nitems_ptr);
/*     */   }
/*     */   public long getData() {
/* 159 */     if (isDisposed()) {
/* 160 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 162 */     return Native.getLong(this.data);
/*     */   }
/*     */   public long getBytesAfter() {
/* 165 */     if (isDisposed()) {
/* 166 */       throw new IllegalStateException("Disposed");
/*     */     }
/* 168 */     if (!this.executed) {
/* 169 */       throw new IllegalStateException("Not executed");
/*     */     }
/* 171 */     return Native.getLong(this.bytes_after);
/*     */   }
/*     */   public void dispose() {
/* 174 */     XToolkit.awtLock();
/*     */     try {
/* 176 */       if (isDisposed()) {
/*     */         return;
/*     */       }
/* 179 */       this.disposer.dispose();
/*     */     } finally {
/* 181 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isCachingSupported() {
/* 186 */     return XPropertyCache.isCachingSupported();
/*     */   }
/*     */   
/* 189 */   static Set<XAtom> cacheableProperties = new HashSet<>(Arrays.asList(new XAtom[] {
/* 190 */           XAtom.get("_NET_WM_STATE"), XAtom.get("WM_STATE"), XAtom.get("_MOTIF_WM_HINTS") }));
/*     */   
/*     */   static boolean isCacheableProperty(XAtom paramXAtom) {
/* 193 */     return cacheableProperties.contains(paramXAtom);
/*     */   }
/*     */   
/*     */   boolean isCached() {
/* 197 */     return XPropertyCache.isCached(this.window, this.property);
/*     */   }
/*     */   
/*     */   int getDataLength() {
/* 201 */     return getActualFormat() / 8 * getNumberOfItems();
/*     */   }
/*     */   
/*     */   void readFromCache() {
/* 205 */     this.property.putAtom(this.actual_type);
/* 206 */     XPropertyCache.PropertyCacheEntry propertyCacheEntry = XPropertyCache.getCacheEntry(this.window, this.property);
/* 207 */     Native.putInt(this.actual_format, propertyCacheEntry.getFormat());
/* 208 */     Native.putLong(this.nitems_ptr, propertyCacheEntry.getNumberOfItems());
/* 209 */     Native.putLong(this.bytes_after, propertyCacheEntry.getBytesAfter());
/* 210 */     Native.putLong(this.data, unsafe.allocateMemory(getDataLength()));
/* 211 */     XlibWrapper.memcpy(getData(), propertyCacheEntry.getData(), getDataLength());
/*     */   }
/*     */   
/*     */   void cacheProperty() {
/* 215 */     XPropertyCache.storeCache(new XPropertyCache.PropertyCacheEntry(
/* 216 */           getActualFormat(), 
/* 217 */           getNumberOfItems(), 
/* 218 */           getBytesAfter(), 
/* 219 */           getData(), 
/* 220 */           getDataLength()), this.window, this.property);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/WindowPropertyGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */