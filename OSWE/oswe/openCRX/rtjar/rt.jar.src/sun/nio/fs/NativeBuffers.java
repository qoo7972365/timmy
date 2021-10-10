/*     */ package sun.nio.fs;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeBuffers
/*     */ {
/*  37 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   private static final int TEMP_BUF_POOL_SIZE = 3;
/*  40 */   private static ThreadLocal<NativeBuffer[]> threadLocal = (ThreadLocal)new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NativeBuffer allocNativeBuffer(int paramInt) {
/*  48 */     if (paramInt < 2048) paramInt = 2048; 
/*  49 */     return new NativeBuffer(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NativeBuffer getNativeBufferFromCache(int paramInt) {
/*  58 */     NativeBuffer[] arrayOfNativeBuffer = threadLocal.get();
/*  59 */     if (arrayOfNativeBuffer != null) {
/*  60 */       for (byte b = 0; b < 3; b++) {
/*  61 */         NativeBuffer nativeBuffer = arrayOfNativeBuffer[b];
/*  62 */         if (nativeBuffer != null && nativeBuffer.size() >= paramInt) {
/*  63 */           arrayOfNativeBuffer[b] = null;
/*  64 */           return nativeBuffer;
/*     */         } 
/*     */       } 
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NativeBuffer getNativeBuffer(int paramInt) {
/*  77 */     NativeBuffer nativeBuffer = getNativeBufferFromCache(paramInt);
/*  78 */     if (nativeBuffer != null) {
/*  79 */       nativeBuffer.setOwner(null);
/*  80 */       return nativeBuffer;
/*     */     } 
/*  82 */     return allocNativeBuffer(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void releaseNativeBuffer(NativeBuffer paramNativeBuffer) {
/*  92 */     NativeBuffer[] arrayOfNativeBuffer = threadLocal.get();
/*  93 */     if (arrayOfNativeBuffer == null) {
/*  94 */       arrayOfNativeBuffer = new NativeBuffer[3];
/*  95 */       arrayOfNativeBuffer[0] = paramNativeBuffer;
/*  96 */       threadLocal.set(arrayOfNativeBuffer);
/*     */       return;
/*     */     } 
/*     */     byte b;
/* 100 */     for (b = 0; b < 3; b++) {
/* 101 */       if (arrayOfNativeBuffer[b] == null) {
/* 102 */         arrayOfNativeBuffer[b] = paramNativeBuffer;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 107 */     for (b = 0; b < 3; b++) {
/* 108 */       NativeBuffer nativeBuffer = arrayOfNativeBuffer[b];
/* 109 */       if (nativeBuffer.size() < paramNativeBuffer.size()) {
/* 110 */         nativeBuffer.cleaner().clean();
/* 111 */         arrayOfNativeBuffer[b] = paramNativeBuffer;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     paramNativeBuffer.cleaner().clean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void copyCStringToNativeBuffer(byte[] paramArrayOfbyte, NativeBuffer paramNativeBuffer) {
/* 124 */     long l1 = Unsafe.ARRAY_BYTE_BASE_OFFSET;
/* 125 */     long l2 = paramArrayOfbyte.length;
/* 126 */     assert paramNativeBuffer.size() >= l2 + 1L;
/* 127 */     unsafe.copyMemory(paramArrayOfbyte, l1, null, paramNativeBuffer.address(), l2);
/* 128 */     unsafe.putByte(paramNativeBuffer.address() + l2, (byte)0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static NativeBuffer asNativeBuffer(byte[] paramArrayOfbyte) {
/* 136 */     NativeBuffer nativeBuffer = getNativeBuffer(paramArrayOfbyte.length + 1);
/* 137 */     copyCStringToNativeBuffer(paramArrayOfbyte, nativeBuffer);
/* 138 */     return nativeBuffer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/NativeBuffers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */