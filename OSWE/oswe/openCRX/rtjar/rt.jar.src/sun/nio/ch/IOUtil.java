/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOUtil
/*     */ {
/*     */   static int write(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, long paramLong, NativeDispatcher paramNativeDispatcher) throws IOException {
/*  50 */     if (paramByteBuffer instanceof DirectBuffer) {
/*  51 */       return writeFromNativeBuffer(paramFileDescriptor, paramByteBuffer, paramLong, paramNativeDispatcher);
/*     */     }
/*     */     
/*  54 */     int i = paramByteBuffer.position();
/*  55 */     int j = paramByteBuffer.limit();
/*  56 */     assert i <= j;
/*  57 */     boolean bool = (i <= j) ? (j - i) : false;
/*  58 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(bool);
/*     */     try {
/*  60 */       byteBuffer.put(paramByteBuffer);
/*  61 */       byteBuffer.flip();
/*     */       
/*  63 */       paramByteBuffer.position(i);
/*     */       
/*  65 */       int k = writeFromNativeBuffer(paramFileDescriptor, byteBuffer, paramLong, paramNativeDispatcher);
/*  66 */       if (k > 0)
/*     */       {
/*  68 */         paramByteBuffer.position(i + k);
/*     */       }
/*  70 */       return k;
/*     */     } finally {
/*  72 */       Util.offerFirstTemporaryDirectBuffer(byteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int writeFromNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, long paramLong, NativeDispatcher paramNativeDispatcher) throws IOException {
/*  80 */     int i = paramByteBuffer.position();
/*  81 */     int j = paramByteBuffer.limit();
/*  82 */     assert i <= j;
/*  83 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/*  85 */     int k = 0;
/*  86 */     if (!bool)
/*  87 */       return 0; 
/*  88 */     if (paramLong != -1L) {
/*  89 */       k = paramNativeDispatcher.pwrite(paramFileDescriptor, ((DirectBuffer)paramByteBuffer)
/*  90 */           .address() + i, bool, paramLong);
/*     */     } else {
/*     */       
/*  93 */       k = paramNativeDispatcher.write(paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + i, bool);
/*     */     } 
/*  95 */     if (k > 0)
/*  96 */       paramByteBuffer.position(i + k); 
/*  97 */     return k;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long write(FileDescriptor paramFileDescriptor, ByteBuffer[] paramArrayOfByteBuffer, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 103 */     return write(paramFileDescriptor, paramArrayOfByteBuffer, 0, paramArrayOfByteBuffer.length, paramNativeDispatcher);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long write(FileDescriptor paramFileDescriptor, ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 110 */     IOVecWrapper iOVecWrapper = IOVecWrapper.get(paramInt2);
/*     */     
/* 112 */     boolean bool = false;
/* 113 */     byte b = 0;
/*     */ 
/*     */     
/*     */     try {
/* 117 */       int i = paramInt1 + paramInt2;
/* 118 */       int j = paramInt1;
/* 119 */       while (j < i && b < IOV_MAX) {
/* 120 */         ByteBuffer byteBuffer = paramArrayOfByteBuffer[j];
/* 121 */         int k = byteBuffer.position();
/* 122 */         int m = byteBuffer.limit();
/* 123 */         assert k <= m;
/* 124 */         boolean bool1 = (k <= m) ? (m - k) : false;
/* 125 */         if (bool1) {
/* 126 */           iOVecWrapper.setBuffer(b, byteBuffer, k, bool1);
/*     */ 
/*     */           
/* 129 */           if (!(byteBuffer instanceof DirectBuffer)) {
/* 130 */             ByteBuffer byteBuffer1 = Util.getTemporaryDirectBuffer(bool1);
/* 131 */             byteBuffer1.put(byteBuffer);
/* 132 */             byteBuffer1.flip();
/* 133 */             iOVecWrapper.setShadow(b, byteBuffer1);
/* 134 */             byteBuffer.position(k);
/* 135 */             byteBuffer = byteBuffer1;
/* 136 */             k = byteBuffer1.position();
/*     */           } 
/*     */           
/* 139 */           iOVecWrapper.putBase(b, ((DirectBuffer)byteBuffer).address() + k);
/* 140 */           iOVecWrapper.putLen(b, bool1);
/* 141 */           b++;
/*     */         } 
/* 143 */         j++;
/*     */       } 
/* 145 */       if (b == 0) {
/* 146 */         return 0L;
/*     */       }
/* 148 */       long l1 = paramNativeDispatcher.writev(paramFileDescriptor, iOVecWrapper.address, b);
/*     */ 
/*     */       
/* 151 */       long l2 = l1;
/* 152 */       for (byte b1 = 0; b1 < b; b1++) {
/* 153 */         if (l2 > 0L) {
/* 154 */           ByteBuffer byteBuffer1 = iOVecWrapper.getBuffer(b1);
/* 155 */           int k = iOVecWrapper.getPosition(b1);
/* 156 */           int m = iOVecWrapper.getRemaining(b1);
/* 157 */           int n = (l2 > m) ? m : (int)l2;
/* 158 */           byteBuffer1.position(k + n);
/* 159 */           l2 -= n;
/*     */         } 
/*     */         
/* 162 */         ByteBuffer byteBuffer = iOVecWrapper.getShadow(b1);
/* 163 */         if (byteBuffer != null)
/* 164 */           Util.offerLastTemporaryDirectBuffer(byteBuffer); 
/* 165 */         iOVecWrapper.clearRefs(b1);
/*     */       } 
/*     */       
/* 168 */       bool = true;
/* 169 */       return l1;
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 174 */       if (!bool) {
/* 175 */         for (byte b1 = 0; b1 < b; b1++) {
/* 176 */           ByteBuffer byteBuffer = iOVecWrapper.getShadow(b1);
/* 177 */           if (byteBuffer != null)
/* 178 */             Util.offerLastTemporaryDirectBuffer(byteBuffer); 
/* 179 */           iOVecWrapper.clearRefs(b1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int read(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, long paramLong, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 189 */     if (paramByteBuffer.isReadOnly())
/* 190 */       throw new IllegalArgumentException("Read-only buffer"); 
/* 191 */     if (paramByteBuffer instanceof DirectBuffer) {
/* 192 */       return readIntoNativeBuffer(paramFileDescriptor, paramByteBuffer, paramLong, paramNativeDispatcher);
/*     */     }
/*     */     
/* 195 */     ByteBuffer byteBuffer = Util.getTemporaryDirectBuffer(paramByteBuffer.remaining());
/*     */     try {
/* 197 */       int i = readIntoNativeBuffer(paramFileDescriptor, byteBuffer, paramLong, paramNativeDispatcher);
/* 198 */       byteBuffer.flip();
/* 199 */       if (i > 0)
/* 200 */         paramByteBuffer.put(byteBuffer); 
/* 201 */       return i;
/*     */     } finally {
/* 203 */       Util.offerFirstTemporaryDirectBuffer(byteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readIntoNativeBuffer(FileDescriptor paramFileDescriptor, ByteBuffer paramByteBuffer, long paramLong, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 211 */     int i = paramByteBuffer.position();
/* 212 */     int j = paramByteBuffer.limit();
/* 213 */     assert i <= j;
/* 214 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/* 216 */     if (!bool)
/* 217 */       return 0; 
/* 218 */     int k = 0;
/* 219 */     if (paramLong != -1L) {
/* 220 */       k = paramNativeDispatcher.pread(paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + i, bool, paramLong);
/*     */     } else {
/*     */       
/* 223 */       k = paramNativeDispatcher.read(paramFileDescriptor, ((DirectBuffer)paramByteBuffer).address() + i, bool);
/*     */     } 
/* 225 */     if (k > 0)
/* 226 */       paramByteBuffer.position(i + k); 
/* 227 */     return k;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static long read(FileDescriptor paramFileDescriptor, ByteBuffer[] paramArrayOfByteBuffer, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 233 */     return read(paramFileDescriptor, paramArrayOfByteBuffer, 0, paramArrayOfByteBuffer.length, paramNativeDispatcher);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long read(FileDescriptor paramFileDescriptor, ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, NativeDispatcher paramNativeDispatcher) throws IOException {
/* 240 */     IOVecWrapper iOVecWrapper = IOVecWrapper.get(paramInt2);
/*     */     
/* 242 */     boolean bool = false;
/* 243 */     byte b = 0;
/*     */ 
/*     */     
/*     */     try {
/* 247 */       int i = paramInt1 + paramInt2;
/* 248 */       int j = paramInt1;
/* 249 */       while (j < i && b < IOV_MAX) {
/* 250 */         ByteBuffer byteBuffer = paramArrayOfByteBuffer[j];
/* 251 */         if (byteBuffer.isReadOnly())
/* 252 */           throw new IllegalArgumentException("Read-only buffer"); 
/* 253 */         int k = byteBuffer.position();
/* 254 */         int m = byteBuffer.limit();
/* 255 */         assert k <= m;
/* 256 */         boolean bool1 = (k <= m) ? (m - k) : false;
/*     */         
/* 258 */         if (bool1) {
/* 259 */           iOVecWrapper.setBuffer(b, byteBuffer, k, bool1);
/*     */ 
/*     */           
/* 262 */           if (!(byteBuffer instanceof DirectBuffer)) {
/* 263 */             ByteBuffer byteBuffer1 = Util.getTemporaryDirectBuffer(bool1);
/* 264 */             iOVecWrapper.setShadow(b, byteBuffer1);
/* 265 */             byteBuffer = byteBuffer1;
/* 266 */             k = byteBuffer1.position();
/*     */           } 
/*     */           
/* 269 */           iOVecWrapper.putBase(b, ((DirectBuffer)byteBuffer).address() + k);
/* 270 */           iOVecWrapper.putLen(b, bool1);
/* 271 */           b++;
/*     */         } 
/* 273 */         j++;
/*     */       } 
/* 275 */       if (b == 0) {
/* 276 */         return 0L;
/*     */       }
/* 278 */       long l1 = paramNativeDispatcher.readv(paramFileDescriptor, iOVecWrapper.address, b);
/*     */ 
/*     */       
/* 281 */       long l2 = l1;
/* 282 */       for (byte b1 = 0; b1 < b; b1++) {
/* 283 */         ByteBuffer byteBuffer = iOVecWrapper.getShadow(b1);
/* 284 */         if (l2 > 0L) {
/* 285 */           ByteBuffer byteBuffer1 = iOVecWrapper.getBuffer(b1);
/* 286 */           int k = iOVecWrapper.getRemaining(b1);
/* 287 */           int m = (l2 > k) ? k : (int)l2;
/* 288 */           if (byteBuffer == null) {
/* 289 */             int n = iOVecWrapper.getPosition(b1);
/* 290 */             byteBuffer1.position(n + m);
/*     */           } else {
/* 292 */             byteBuffer.limit(byteBuffer.position() + m);
/* 293 */             byteBuffer1.put(byteBuffer);
/*     */           } 
/* 295 */           l2 -= m;
/*     */         } 
/* 297 */         if (byteBuffer != null)
/* 298 */           Util.offerLastTemporaryDirectBuffer(byteBuffer); 
/* 299 */         iOVecWrapper.clearRefs(b1);
/*     */       } 
/*     */       
/* 302 */       bool = true;
/* 303 */       return l1;
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 308 */       if (!bool) {
/* 309 */         for (byte b1 = 0; b1 < b; b1++) {
/* 310 */           ByteBuffer byteBuffer = iOVecWrapper.getShadow(b1);
/* 311 */           if (byteBuffer != null)
/* 312 */             Util.offerLastTemporaryDirectBuffer(byteBuffer); 
/* 313 */           iOVecWrapper.clearRefs(b1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static FileDescriptor newFD(int paramInt) {
/* 320 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 321 */     setfdVal(fileDescriptor, paramInt);
/* 322 */     return fileDescriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   static native boolean randomBytes(byte[] paramArrayOfbyte);
/*     */ 
/*     */   
/*     */   static native long makePipe(boolean paramBoolean);
/*     */ 
/*     */   
/*     */   static native boolean drain(int paramInt) throws IOException;
/*     */ 
/*     */   
/*     */   public static native void configureBlocking(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
/*     */ 
/*     */   
/*     */   public static native int fdVal(FileDescriptor paramFileDescriptor);
/*     */ 
/*     */   
/*     */   static native void setfdVal(FileDescriptor paramFileDescriptor, int paramInt);
/*     */ 
/*     */   
/*     */   static native int fdLimit();
/*     */ 
/*     */   
/*     */   static native int iovMax();
/*     */ 
/*     */   
/*     */   static native void initIDs();
/*     */ 
/*     */   
/*     */   public static void load() {}
/*     */   
/*     */   static {
/* 356 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 359 */             System.loadLibrary("net");
/* 360 */             System.loadLibrary("nio");
/* 361 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 365 */     initIDs();
/*     */   }
/* 367 */   static final int IOV_MAX = iovMax();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/IOUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */