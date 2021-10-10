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
/*     */ class UnixNativeDispatcher
/*     */ {
/*     */   private static final int SUPPORTS_OPENAT = 2;
/*     */   private static final int SUPPORTS_FUTIMES = 4;
/*     */   private static final int SUPPORTS_BIRTHTIME = 65536;
/*     */   
/*     */   static NativeBuffer copyToNativeBuffer(UnixPath paramUnixPath) {
/*  40 */     byte[] arrayOfByte = paramUnixPath.getByteArrayForSysCalls();
/*  41 */     int i = arrayOfByte.length + 1;
/*  42 */     NativeBuffer nativeBuffer = NativeBuffers.getNativeBufferFromCache(i);
/*  43 */     if (nativeBuffer == null) {
/*  44 */       nativeBuffer = NativeBuffers.allocNativeBuffer(i);
/*     */     
/*     */     }
/*  47 */     else if (nativeBuffer.owner() == paramUnixPath) {
/*  48 */       return nativeBuffer;
/*     */     } 
/*  50 */     NativeBuffers.copyCStringToNativeBuffer(arrayOfByte, nativeBuffer);
/*  51 */     nativeBuffer.setOwner(paramUnixPath);
/*  52 */     return nativeBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native byte[] getcwd();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native int dup(int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int open(UnixPath paramUnixPath, int paramInt1, int paramInt2) throws UnixException {
/*  69 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/*  71 */       return open0(nativeBuffer.address(), paramInt1, paramInt2);
/*     */     } finally {
/*  73 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int open0(long paramLong, int paramInt1, int paramInt2) throws UnixException;
/*     */ 
/*     */   
/*     */   static int openat(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws UnixException {
/*  83 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/*  85 */       return openat0(paramInt1, nativeBuffer.address(), paramInt2, paramInt3);
/*     */     } finally {
/*  87 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int openat0(int paramInt1, long paramLong, int paramInt2, int paramInt3) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static native void close(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   static long fopen(UnixPath paramUnixPath, String paramString) throws UnixException {
/* 102 */     NativeBuffer nativeBuffer1 = copyToNativeBuffer(paramUnixPath);
/* 103 */     NativeBuffer nativeBuffer2 = NativeBuffers.asNativeBuffer(Util.toBytes(paramString));
/*     */     try {
/* 105 */       return fopen0(nativeBuffer1.address(), nativeBuffer2.address());
/*     */     } finally {
/* 107 */       nativeBuffer2.release();
/* 108 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native long fopen0(long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static native void fclose(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void link(UnixPath paramUnixPath1, UnixPath paramUnixPath2) throws UnixException {
/* 123 */     NativeBuffer nativeBuffer1 = copyToNativeBuffer(paramUnixPath1);
/* 124 */     NativeBuffer nativeBuffer2 = copyToNativeBuffer(paramUnixPath2);
/*     */     try {
/* 126 */       link0(nativeBuffer1.address(), nativeBuffer2.address());
/*     */     } finally {
/* 128 */       nativeBuffer2.release();
/* 129 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void link0(long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void unlink(UnixPath paramUnixPath) throws UnixException {
/* 139 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 141 */       unlink0(nativeBuffer.address());
/*     */     } finally {
/* 143 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native void unlink0(long paramLong) throws UnixException;
/*     */ 
/*     */   
/*     */   static void unlinkat(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws UnixException {
/* 152 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/* 154 */       unlinkat0(paramInt1, nativeBuffer.address(), paramInt2);
/*     */     } finally {
/* 156 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void unlinkat0(int paramInt1, long paramLong, int paramInt2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void mknod(UnixPath paramUnixPath, int paramInt, long paramLong) throws UnixException {
/* 166 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 168 */       mknod0(nativeBuffer.address(), paramInt, paramLong);
/*     */     } finally {
/* 170 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void mknod0(long paramLong1, int paramInt, long paramLong2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void rename(UnixPath paramUnixPath1, UnixPath paramUnixPath2) throws UnixException {
/* 180 */     NativeBuffer nativeBuffer1 = copyToNativeBuffer(paramUnixPath1);
/* 181 */     NativeBuffer nativeBuffer2 = copyToNativeBuffer(paramUnixPath2);
/*     */     try {
/* 183 */       rename0(nativeBuffer1.address(), nativeBuffer2.address());
/*     */     } finally {
/* 185 */       nativeBuffer2.release();
/* 186 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void rename0(long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void renameat(int paramInt1, byte[] paramArrayOfbyte1, int paramInt2, byte[] paramArrayOfbyte2) throws UnixException {
/* 196 */     NativeBuffer nativeBuffer1 = NativeBuffers.asNativeBuffer(paramArrayOfbyte1);
/* 197 */     NativeBuffer nativeBuffer2 = NativeBuffers.asNativeBuffer(paramArrayOfbyte2);
/*     */     try {
/* 199 */       renameat0(paramInt1, nativeBuffer1.address(), paramInt2, nativeBuffer2.address());
/*     */     } finally {
/* 201 */       nativeBuffer2.release();
/* 202 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void renameat0(int paramInt1, long paramLong1, int paramInt2, long paramLong2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void mkdir(UnixPath paramUnixPath, int paramInt) throws UnixException {
/* 212 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 214 */       mkdir0(nativeBuffer.address(), paramInt);
/*     */     } finally {
/* 216 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native void mkdir0(long paramLong, int paramInt) throws UnixException;
/*     */ 
/*     */   
/*     */   static void rmdir(UnixPath paramUnixPath) throws UnixException {
/* 225 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 227 */       rmdir0(nativeBuffer.address());
/*     */     } finally {
/* 229 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void rmdir0(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] readlink(UnixPath paramUnixPath) throws UnixException {
/* 240 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 242 */       return readlink0(nativeBuffer.address());
/*     */     } finally {
/* 244 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native byte[] readlink0(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] realpath(UnixPath paramUnixPath) throws UnixException {
/* 255 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 257 */       return realpath0(nativeBuffer.address());
/*     */     } finally {
/* 259 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static native byte[] realpath0(long paramLong) throws UnixException;
/*     */ 
/*     */   
/*     */   static void symlink(byte[] paramArrayOfbyte, UnixPath paramUnixPath) throws UnixException {
/* 268 */     NativeBuffer nativeBuffer1 = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/* 269 */     NativeBuffer nativeBuffer2 = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 271 */       symlink0(nativeBuffer1.address(), nativeBuffer2.address());
/*     */     } finally {
/* 273 */       nativeBuffer2.release();
/* 274 */       nativeBuffer1.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void symlink0(long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void stat(UnixPath paramUnixPath, UnixFileAttributes paramUnixFileAttributes) throws UnixException {
/* 284 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 286 */       stat0(nativeBuffer.address(), paramUnixFileAttributes);
/*     */     } finally {
/* 288 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void stat0(long paramLong, UnixFileAttributes paramUnixFileAttributes) throws UnixException;
/*     */ 
/*     */   
/*     */   static void lstat(UnixPath paramUnixPath, UnixFileAttributes paramUnixFileAttributes) throws UnixException {
/* 298 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 300 */       lstat0(nativeBuffer.address(), paramUnixFileAttributes);
/*     */     } finally {
/* 302 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void lstat0(long paramLong, UnixFileAttributes paramUnixFileAttributes) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native void fstat(int paramInt, UnixFileAttributes paramUnixFileAttributes) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void fstatat(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, UnixFileAttributes paramUnixFileAttributes) throws UnixException {
/* 319 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(paramArrayOfbyte);
/*     */     try {
/* 321 */       fstatat0(paramInt1, nativeBuffer.address(), paramInt2, paramUnixFileAttributes);
/*     */     } finally {
/* 323 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void fstatat0(int paramInt1, long paramLong, int paramInt2, UnixFileAttributes paramUnixFileAttributes) throws UnixException;
/*     */ 
/*     */   
/*     */   static void chown(UnixPath paramUnixPath, int paramInt1, int paramInt2) throws UnixException {
/* 333 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 335 */       chown0(nativeBuffer.address(), paramInt1, paramInt2);
/*     */     } finally {
/* 337 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void chown0(long paramLong, int paramInt1, int paramInt2) throws UnixException;
/*     */ 
/*     */   
/*     */   static void lchown(UnixPath paramUnixPath, int paramInt1, int paramInt2) throws UnixException {
/* 347 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 349 */       lchown0(nativeBuffer.address(), paramInt1, paramInt2);
/*     */     } finally {
/* 351 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void lchown0(long paramLong, int paramInt1, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static native void fchown(int paramInt1, int paramInt2, int paramInt3) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void chmod(UnixPath paramUnixPath, int paramInt) throws UnixException {
/* 366 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 368 */       chmod0(nativeBuffer.address(), paramInt);
/*     */     } finally {
/* 370 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void chmod0(long paramLong, int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native void fchmod(int paramInt1, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void utimes(UnixPath paramUnixPath, long paramLong1, long paramLong2) throws UnixException {
/* 387 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 389 */       utimes0(nativeBuffer.address(), paramLong1, paramLong2);
/*     */     } finally {
/* 391 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void utimes0(long paramLong1, long paramLong2, long paramLong3) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static native void futimes(int paramInt, long paramLong1, long paramLong2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static long opendir(UnixPath paramUnixPath) throws UnixException {
/* 406 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 408 */       return opendir0(nativeBuffer.address());
/*     */     } finally {
/* 410 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native long opendir0(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native long fdopendir(int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native void closedir(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native byte[] readdir(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native int read(int paramInt1, long paramLong, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native int write(int paramInt1, long paramLong, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void access(UnixPath paramUnixPath, int paramInt) throws UnixException {
/* 447 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 449 */       access0(nativeBuffer.address(), paramInt);
/*     */     } finally {
/* 451 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void access0(long paramLong, int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native byte[] getpwuid(int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native byte[] getgrgid(int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getpwnam(String paramString) throws UnixException {
/* 476 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(Util.toBytes(paramString));
/*     */     try {
/* 478 */       return getpwnam0(nativeBuffer.address());
/*     */     } finally {
/* 480 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int getpwnam0(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static int getgrnam(String paramString) throws UnixException {
/* 491 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(Util.toBytes(paramString));
/*     */     try {
/* 493 */       return getgrnam0(nativeBuffer.address());
/*     */     } finally {
/* 495 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int getgrnam0(long paramLong) throws UnixException;
/*     */ 
/*     */ 
/*     */   
/*     */   static void statvfs(UnixPath paramUnixPath, UnixFileStoreAttributes paramUnixFileStoreAttributes) throws UnixException {
/* 506 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 508 */       statvfs0(nativeBuffer.address(), paramUnixFileStoreAttributes);
/*     */     } finally {
/* 510 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void statvfs0(long paramLong, UnixFileStoreAttributes paramUnixFileStoreAttributes) throws UnixException;
/*     */ 
/*     */   
/*     */   static long pathconf(UnixPath paramUnixPath, int paramInt) throws UnixException {
/* 520 */     NativeBuffer nativeBuffer = copyToNativeBuffer(paramUnixPath);
/*     */     try {
/* 522 */       return pathconf0(nativeBuffer.address(), paramInt);
/*     */     } finally {
/* 524 */       nativeBuffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native long pathconf0(long paramLong, int paramInt) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native long fpathconf(int paramInt1, int paramInt2) throws UnixException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static native byte[] strerror(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean openatSupported() {
/* 552 */     return ((capabilities & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean futimesSupported() {
/* 559 */     return ((capabilities & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean birthtimeSupported() {
/* 566 */     return ((capabilities & 0x10000) != 0);
/*     */   }
/*     */   private static native int init();
/*     */   
/*     */   static {
/* 571 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 573 */             System.loadLibrary("nio");
/* 574 */             return null; }
/*     */         });
/* 576 */   } private static final int capabilities = init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixNativeDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */