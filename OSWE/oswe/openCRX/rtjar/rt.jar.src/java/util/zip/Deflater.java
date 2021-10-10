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
/*     */ public class Deflater
/*     */ {
/*     */   private final ZStreamRef zsRef;
/*  77 */   private byte[] buf = new byte[0];
/*     */ 
/*     */ 
/*     */   
/*     */   private int off;
/*     */ 
/*     */ 
/*     */   
/*     */   private int len;
/*     */ 
/*     */ 
/*     */   
/*     */   private int level;
/*     */ 
/*     */ 
/*     */   
/*     */   private int strategy;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setParams;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finish;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finished;
/*     */ 
/*     */ 
/*     */   
/*     */   private long bytesRead;
/*     */ 
/*     */ 
/*     */   
/*     */   private long bytesWritten;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFLATED = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NO_COMPRESSION = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BEST_SPEED = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BEST_COMPRESSION = 9;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_COMPRESSION = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FILTERED = 1;
/*     */ 
/*     */   
/*     */   public static final int HUFFMAN_ONLY = 2;
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_STRATEGY = 0;
/*     */ 
/*     */   
/*     */   public static final int NO_FLUSH = 0;
/*     */ 
/*     */   
/*     */   public static final int SYNC_FLUSH = 2;
/*     */ 
/*     */   
/*     */   public static final int FULL_FLUSH = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 157 */     initIDs();
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
/*     */   public Deflater(int paramInt, boolean paramBoolean) {
/* 169 */     this.level = paramInt;
/* 170 */     this.strategy = 0;
/* 171 */     this.zsRef = new ZStreamRef(init(paramInt, 0, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Deflater(int paramInt) {
/* 180 */     this(paramInt, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Deflater() {
/* 188 */     this(-1, false);
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
/*     */   public void setInput(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 200 */     if (paramArrayOfbyte == null) {
/* 201 */       throw new NullPointerException();
/*     */     }
/* 203 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 204 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 206 */     synchronized (this.zsRef) {
/* 207 */       this.buf = paramArrayOfbyte;
/* 208 */       this.off = paramInt1;
/* 209 */       this.len = paramInt2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInput(byte[] paramArrayOfbyte) {
/* 220 */     setInput(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public void setDictionary(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 236 */     if (paramArrayOfbyte == null) {
/* 237 */       throw new NullPointerException();
/*     */     }
/* 239 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 240 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 242 */     synchronized (this.zsRef) {
/* 243 */       ensureOpen();
/* 244 */       setDictionary(this.zsRef.address(), paramArrayOfbyte, paramInt1, paramInt2);
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
/*     */   public void setDictionary(byte[] paramArrayOfbyte) {
/* 259 */     setDictionary(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public void setStrategy(int paramInt) {
/* 275 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */         break;
/*     */       default:
/* 281 */         throw new IllegalArgumentException();
/*     */     } 
/* 283 */     synchronized (this.zsRef) {
/* 284 */       if (this.strategy != paramInt) {
/* 285 */         this.strategy = paramInt;
/* 286 */         this.setParams = true;
/*     */       } 
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
/*     */   public void setLevel(int paramInt) {
/* 303 */     if ((paramInt < 0 || paramInt > 9) && paramInt != -1) {
/* 304 */       throw new IllegalArgumentException("invalid compression level");
/*     */     }
/* 306 */     synchronized (this.zsRef) {
/* 307 */       if (this.level != paramInt) {
/* 308 */         this.level = paramInt;
/* 309 */         this.setParams = true;
/*     */       } 
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
/* 321 */     synchronized (this.zsRef) {
/* 322 */       return (this.len <= 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() {
/* 331 */     synchronized (this.zsRef) {
/* 332 */       this.finish = true;
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
/* 343 */     synchronized (this.zsRef) {
/* 344 */       return this.finished;
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
/*     */   public int deflate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 366 */     return deflate(paramArrayOfbyte, paramInt1, paramInt2, 0);
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
/*     */   public int deflate(byte[] paramArrayOfbyte) {
/* 385 */     return deflate(paramArrayOfbyte, 0, paramArrayOfbyte.length, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deflate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
/* 433 */     if (paramArrayOfbyte == null) {
/* 434 */       throw new NullPointerException();
/*     */     }
/* 436 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 437 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 439 */     synchronized (this.zsRef) {
/* 440 */       ensureOpen();
/* 441 */       if (paramInt3 == 0 || paramInt3 == 2 || paramInt3 == 3) {
/*     */         
/* 443 */         int i = this.len;
/* 444 */         int j = deflateBytes(this.zsRef.address(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3);
/* 445 */         this.bytesWritten += j;
/* 446 */         this.bytesRead += (i - this.len);
/* 447 */         return j;
/*     */       } 
/* 449 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAdler() {
/* 458 */     synchronized (this.zsRef) {
/* 459 */       ensureOpen();
/* 460 */       return getAdler(this.zsRef.address());
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
/* 474 */     return (int)getBytesRead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBytesRead() {
/* 484 */     synchronized (this.zsRef) {
/* 485 */       ensureOpen();
/* 486 */       return this.bytesRead;
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
/* 500 */     return (int)getBytesWritten();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBytesWritten() {
/* 510 */     synchronized (this.zsRef) {
/* 511 */       ensureOpen();
/* 512 */       return this.bytesWritten;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 521 */     synchronized (this.zsRef) {
/* 522 */       ensureOpen();
/* 523 */       reset(this.zsRef.address());
/* 524 */       this.finish = false;
/* 525 */       this.finished = false;
/* 526 */       this.off = this.len = 0;
/* 527 */       this.bytesRead = this.bytesWritten = 0L;
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
/* 539 */     synchronized (this.zsRef) {
/* 540 */       long l = this.zsRef.address();
/* 541 */       this.zsRef.clear();
/* 542 */       if (l != 0L) {
/* 543 */         end(l);
/* 544 */         this.buf = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 553 */     end();
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/* 557 */     assert Thread.holdsLock(this.zsRef);
/* 558 */     if (this.zsRef.address() == 0L)
/* 559 */       throw new NullPointerException("Deflater has been closed"); 
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private static native long init(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   private static native void setDictionary(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */   
/*     */   private native int deflateBytes(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   private static native int getAdler(long paramLong);
/*     */   
/*     */   private static native void reset(long paramLong);
/*     */   
/*     */   private static native void end(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/Deflater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */