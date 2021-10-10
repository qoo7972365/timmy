/*     */ package sun.nio.fs;
/*     */ 
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
/*     */ class LinuxNativeDispatcher
/*     */   extends UnixNativeDispatcher
/*     */ {
/*     */   static long setmntent(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws UnixException {
/*  42 */     NativeBuffer nativeBuffer1 = NativeBuffers.asNativeBuffer(paramArrayOfbyte1);
/*  43 */     NativeBuffer nativeBuffer2 = NativeBuffers.asNativeBuffer(paramArrayOfbyte2);
/*     */     try {
/*  45 */       return setmntent0(nativeBuffer1.address(), nativeBuffer2.address());
/*     */     } finally {
/*  47 */       nativeBuffer2.release();
/*  48 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native long setmntent0(long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native int getmntent(long paramLong, UnixMountEntry paramUnixMountEntry) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native void endmntent(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int fgetxattr(int paramInt1, byte[] paramArrayOfbyte, long paramLong, int paramInt2) throws UnixException {
/*  71 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/*  73 */       return fgetxattr0(paramInt1, nativeBuffer.address(), paramLong, paramInt2);
/*     */     } finally {
/*  75 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int fgetxattr0(int paramInt1, long paramLong1, long paramLong2, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void fsetxattr(int paramInt1, byte[] paramArrayOfbyte, long paramLong, int paramInt2) throws UnixException {
/*  88 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/*  90 */       fsetxattr0(paramInt1, nativeBuffer.address(), paramLong, paramInt2);
/*     */     } finally {
/*  92 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void fsetxattr0(int paramInt1, long paramLong1, long paramLong2, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void fremovexattr(int paramInt, byte[] paramArrayOfbyte) throws UnixException {
/* 103 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/* 105 */       fremovexattr0(paramInt, nativeBuffer.address());
/*     */     } finally {
/* 107 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void fremovexattr0(int paramInt, long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static native int flistxattr(int paramInt1, long paramLong, int paramInt2) throws UnixException;
/*     */ 
/*     */   
/*     */   private static native void init();
/*     */ 
/*     */   
/*     */   static {
/* 124 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 126 */             System.loadLibrary("nio");
/* 127 */             return null; }
/*     */         });
/* 129 */     init();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxNativeDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */