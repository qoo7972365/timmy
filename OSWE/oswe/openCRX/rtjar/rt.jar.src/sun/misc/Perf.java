/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Perf
/*     */ {
/*     */   public static class GetPerfAction
/*     */     implements PrivilegedAction<Perf>
/*     */   {
/*     */     public Perf run() {
/*  97 */       return Perf.getPerf();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Perf getPerf() {
/* 135 */     SecurityManager securityManager = System.getSecurityManager();
/* 136 */     if (securityManager != null) {
/* 137 */       RuntimePermission runtimePermission = new RuntimePermission("sun.misc.Perf.getPerf");
/* 138 */       securityManager.checkPermission(runtimePermission);
/*     */     } 
/*     */     
/* 141 */     return instance;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer attach(int paramInt, String paramString) throws IllegalArgumentException, IOException {
/* 199 */     if (paramString.compareTo("r") == 0) {
/* 200 */       return attachImpl(null, paramInt, 0);
/*     */     }
/* 202 */     if (paramString.compareTo("rw") == 0) {
/* 203 */       return attachImpl(null, paramInt, 1);
/*     */     }
/*     */     
/* 206 */     throw new IllegalArgumentException("unknown mode");
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
/*     */   public ByteBuffer attach(String paramString1, int paramInt, String paramString2) throws IllegalArgumentException, IOException {
/* 235 */     if (paramString2.compareTo("r") == 0) {
/* 236 */       return attachImpl(paramString1, paramInt, 0);
/*     */     }
/* 238 */     if (paramString2.compareTo("rw") == 0) {
/* 239 */       return attachImpl(paramString1, paramInt, 1);
/*     */     }
/*     */     
/* 242 */     throw new IllegalArgumentException("unknown mode");
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
/*     */   private ByteBuffer attachImpl(String paramString, int paramInt1, int paramInt2) throws IllegalArgumentException, IOException {
/* 270 */     final ByteBuffer b = attach(paramString, paramInt1, paramInt2);
/*     */     
/* 272 */     if (paramInt1 == 0)
/*     */     {
/*     */       
/* 275 */       return byteBuffer1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     ByteBuffer byteBuffer2 = byteBuffer1.duplicate();
/* 286 */     Cleaner.create(byteBuffer2, new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 289 */               Perf.instance.detach(b);
/*     */             }
/* 291 */             catch (Throwable throwable) {
/*     */ 
/*     */               
/* 294 */               assert false : throwable.toString();
/*     */             } 
/*     */           }
/*     */         });
/* 298 */     return byteBuffer2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer createString(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3) {
/* 408 */     byte[] arrayOfByte1 = getBytes(paramString2);
/* 409 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/* 410 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/* 411 */     arrayOfByte2[arrayOfByte1.length] = 0;
/* 412 */     return createByteArray(paramString1, paramInt1, paramInt2, arrayOfByte2, Math.max(arrayOfByte2.length, paramInt3));
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
/*     */   public ByteBuffer createString(String paramString1, int paramInt1, int paramInt2, String paramString2) {
/* 447 */     byte[] arrayOfByte1 = getBytes(paramString2);
/* 448 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/* 449 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/* 450 */     arrayOfByte2[arrayOfByte1.length] = 0;
/* 451 */     return createByteArray(paramString1, paramInt1, paramInt2, arrayOfByte2, arrayOfByte2.length);
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
/*     */   private static byte[] getBytes(String paramString) {
/* 492 */     byte[] arrayOfByte = null;
/*     */     
/*     */     try {
/* 495 */       arrayOfByte = paramString.getBytes("UTF-8");
/*     */     }
/* 497 */     catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */ 
/*     */     
/* 501 */     return arrayOfByte;
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
/*     */   static {
/* 536 */     registerNatives();
/* 537 */   } private static Perf instance = new Perf();
/*     */   private static final int PERF_MODE_RO = 0;
/*     */   private static final int PERF_MODE_RW = 1;
/*     */   
/*     */   private native ByteBuffer attach(String paramString, int paramInt1, int paramInt2) throws IllegalArgumentException, IOException;
/*     */   
/*     */   private native void detach(ByteBuffer paramByteBuffer);
/*     */   
/*     */   public native ByteBuffer createLong(String paramString, int paramInt1, int paramInt2, long paramLong);
/*     */   
/*     */   public native ByteBuffer createByteArray(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3);
/*     */   
/*     */   public native long highResCounter();
/*     */   
/*     */   public native long highResFrequency();
/*     */   
/*     */   private static native void registerNatives();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Perf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */