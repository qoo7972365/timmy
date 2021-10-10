/*     */ package java.util.zip;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Inflater
/*     */ {
/*     */   private final ZStreamRef zsRef;
/*  77 */   private byte[] buf = defaultBuf;
/*     */   private int off;
/*     */   private int len;
/*     */   private boolean finished;
/*     */   private boolean needDict;
/*     */   private long bytesRead;
/*     */   private long bytesWritten;
/*  84 */   private static final byte[] defaultBuf = new byte[0];
/*     */ 
/*     */   
/*     */   static {
/*  88 */     initIDs();
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
/*     */   public Inflater(boolean paramBoolean) {
/* 103 */     this.zsRef = new ZStreamRef(init(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inflater() {
/* 110 */     this(false);
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
/*     */   public void setInput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 123 */     if (paramArrayOfbyte == null) {
/* 124 */       throw new NullPointerException();
/*     */     }
/* 126 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 127 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 129 */     synchronized (this.zsRef) {
/* 130 */       this.buf = paramArrayOfbyte;
/* 131 */       this.off = paramInt1;
/* 132 */       this.len = paramInt2;
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
/*     */   public void setInput(byte[] paramArrayOfbyte) {
/* 144 */     setInput(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public void setDictionary(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 159 */     if (paramArrayOfbyte == null) {
/* 160 */       throw new NullPointerException();
/*     */     }
/* 162 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 163 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 165 */     synchronized (this.zsRef) {
/* 166 */       ensureOpen();
/* 167 */       setDictionary(this.zsRef.address(), paramArrayOfbyte, paramInt1, paramInt2);
/* 168 */       this.needDict = false;
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
/*     */   public void setDictionary(byte[] paramArrayOfbyte) {
/* 182 */     setDictionary(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRemaining() {
/* 192 */     synchronized (this.zsRef) {
/* 193 */       return this.len;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsInput() {
/* 204 */     synchronized (this.zsRef) {
/* 205 */       return (this.len <= 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsDictionary() {
/* 215 */     synchronized (this.zsRef) {
/* 216 */       return this.needDict;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean finished() {
/* 227 */     synchronized (this.zsRef) {
/* 228 */       return this.finished;
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
/*     */   public int inflate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DataFormatException {
/* 250 */     if (paramArrayOfbyte == null) {
/* 251 */       throw new NullPointerException();
/*     */     }
/* 253 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 254 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 256 */     synchronized (this.zsRef) {
/* 257 */       ensureOpen();
/* 258 */       int i = this.len;
/* 259 */       int j = inflateBytes(this.zsRef.address(), paramArrayOfbyte, paramInt1, paramInt2);
/* 260 */       this.bytesWritten += j;
/* 261 */       this.bytesRead += (i - this.len);
/* 262 */       return j;
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
/*     */   public int inflate(byte[] paramArrayOfbyte) throws DataFormatException {
/* 280 */     return inflate(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAdler() {
/* 288 */     synchronized (this.zsRef) {
/* 289 */       ensureOpen();
/* 290 */       return getAdler(this.zsRef.address());
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
/*     */   public int getTotalIn() {
/* 304 */     return (int)getBytesRead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBytesRead() {
/* 314 */     synchronized (this.zsRef) {
/* 315 */       ensureOpen();
/* 316 */       return this.bytesRead;
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
/*     */   public int getTotalOut() {
/* 330 */     return (int)getBytesWritten();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBytesWritten() {
/* 340 */     synchronized (this.zsRef) {
/* 341 */       ensureOpen();
/* 342 */       return this.bytesWritten;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 350 */     synchronized (this.zsRef) {
/* 351 */       ensureOpen();
/* 352 */       reset(this.zsRef.address());
/* 353 */       this.buf = defaultBuf;
/* 354 */       this.finished = false;
/* 355 */       this.needDict = false;
/* 356 */       this.off = this.len = 0;
/* 357 */       this.bytesRead = this.bytesWritten = 0L;
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
/*     */   public void end() {
/* 369 */     synchronized (this.zsRef) {
/* 370 */       long l = this.zsRef.address();
/* 371 */       this.zsRef.clear();
/* 372 */       if (l != 0L) {
/* 373 */         end(l);
/* 374 */         this.buf = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 383 */     end();
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/* 387 */     assert Thread.holdsLock(this.zsRef);
/* 388 */     if (this.zsRef.address() == 0L)
/* 389 */       throw new NullPointerException("Inflater has been closed"); 
/*     */   }
/*     */   
/*     */   boolean ended() {
/* 393 */     synchronized (this.zsRef) {
/* 394 */       return (this.zsRef.address() == 0L);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long init(boolean paramBoolean);
/*     */   
/*     */   private static native void setDictionary(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   private native int inflateBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DataFormatException;
/*     */   
/*     */   private static native int getAdler(long paramLong);
/*     */   
/*     */   private static native void reset(long paramLong);
/*     */   
/*     */   private static native void end(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/Inflater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */