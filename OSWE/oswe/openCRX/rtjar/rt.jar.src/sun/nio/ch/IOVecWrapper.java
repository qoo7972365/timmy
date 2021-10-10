/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import sun.misc.Cleaner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IOVecWrapper
/*     */ {
/*     */   private static final int BASE_OFFSET = 0;
/*     */   private static final int LEN_OFFSET;
/*     */   private static final int SIZE_IOVEC;
/*     */   private final AllocatedNativeObject vecArray;
/*     */   private final int size;
/*     */   private final ByteBuffer[] buf;
/*     */   private final int[] position;
/*     */   private final int[] remaining;
/*     */   private final ByteBuffer[] shadow;
/*     */   final long address;
/*     */   static int addressSize;
/*     */   
/*     */   private static class Deallocator
/*     */     implements Runnable
/*     */   {
/*     */     private final AllocatedNativeObject obj;
/*     */     
/*     */     Deallocator(AllocatedNativeObject param1AllocatedNativeObject) {
/*  74 */       this.obj = param1AllocatedNativeObject;
/*     */     }
/*     */     public void run() {
/*  77 */       this.obj.free();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  82 */   private static final ThreadLocal<IOVecWrapper> cached = new ThreadLocal<>();
/*     */ 
/*     */   
/*     */   private IOVecWrapper(int paramInt) {
/*  86 */     this.size = paramInt;
/*  87 */     this.buf = new ByteBuffer[paramInt];
/*  88 */     this.position = new int[paramInt];
/*  89 */     this.remaining = new int[paramInt];
/*  90 */     this.shadow = new ByteBuffer[paramInt];
/*  91 */     this.vecArray = new AllocatedNativeObject(paramInt * SIZE_IOVEC, false);
/*  92 */     this.address = this.vecArray.address();
/*     */   }
/*     */   
/*     */   static IOVecWrapper get(int paramInt) {
/*  96 */     IOVecWrapper iOVecWrapper = cached.get();
/*  97 */     if (iOVecWrapper != null && iOVecWrapper.size < paramInt) {
/*     */       
/*  99 */       iOVecWrapper.vecArray.free();
/* 100 */       iOVecWrapper = null;
/*     */     } 
/* 102 */     if (iOVecWrapper == null) {
/* 103 */       iOVecWrapper = new IOVecWrapper(paramInt);
/* 104 */       Cleaner.create(iOVecWrapper, new Deallocator(iOVecWrapper.vecArray));
/* 105 */       cached.set(iOVecWrapper);
/*     */     } 
/* 107 */     return iOVecWrapper;
/*     */   }
/*     */   
/*     */   void setBuffer(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3) {
/* 111 */     this.buf[paramInt1] = paramByteBuffer;
/* 112 */     this.position[paramInt1] = paramInt2;
/* 113 */     this.remaining[paramInt1] = paramInt3;
/*     */   }
/*     */   
/*     */   void setShadow(int paramInt, ByteBuffer paramByteBuffer) {
/* 117 */     this.shadow[paramInt] = paramByteBuffer;
/*     */   }
/*     */   
/*     */   ByteBuffer getBuffer(int paramInt) {
/* 121 */     return this.buf[paramInt];
/*     */   }
/*     */   
/*     */   int getPosition(int paramInt) {
/* 125 */     return this.position[paramInt];
/*     */   }
/*     */   
/*     */   int getRemaining(int paramInt) {
/* 129 */     return this.remaining[paramInt];
/*     */   }
/*     */   
/*     */   ByteBuffer getShadow(int paramInt) {
/* 133 */     return this.shadow[paramInt];
/*     */   }
/*     */   
/*     */   void clearRefs(int paramInt) {
/* 137 */     this.buf[paramInt] = null;
/* 138 */     this.shadow[paramInt] = null;
/*     */   }
/*     */   
/*     */   void putBase(int paramInt, long paramLong) {
/* 142 */     int i = SIZE_IOVEC * paramInt + 0;
/* 143 */     if (addressSize == 4) {
/* 144 */       this.vecArray.putInt(i, (int)paramLong);
/*     */     } else {
/* 146 */       this.vecArray.putLong(i, paramLong);
/*     */     } 
/*     */   }
/*     */   void putLen(int paramInt, long paramLong) {
/* 150 */     int i = SIZE_IOVEC * paramInt + LEN_OFFSET;
/* 151 */     if (addressSize == 4) {
/* 152 */       this.vecArray.putInt(i, (int)paramLong);
/*     */     } else {
/* 154 */       this.vecArray.putLong(i, paramLong);
/*     */     } 
/*     */   }
/*     */   static {
/* 158 */     addressSize = Util.unsafe().addressSize();
/* 159 */     LEN_OFFSET = addressSize;
/* 160 */     SIZE_IOVEC = (short)(addressSize * 2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/IOVecWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */