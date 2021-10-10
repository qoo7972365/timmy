/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MagicFileTypeDetector
/*    */   extends AbstractFileTypeDetector
/*    */ {
/*    */   private static final String UNKNOW_MIME_TYPE = "application/octet-stream";
/* 45 */   private final boolean libmagicAvailable = initialize0();
/*    */ 
/*    */ 
/*    */   
/*    */   protected String implProbeContentType(Path paramPath) throws IOException {
/* 50 */     if (!this.libmagicAvailable || !(paramPath instanceof UnixPath)) {
/* 51 */       return null;
/*    */     }
/* 53 */     UnixPath unixPath = (UnixPath)paramPath;
/* 54 */     unixPath.checkRead();
/*    */     
/* 56 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(unixPath.getByteArrayForSysCalls());
/*    */     try {
/* 58 */       byte[] arrayOfByte = probe0(nativeBuffer.address());
/* 59 */       String str = (arrayOfByte == null) ? null : new String(arrayOfByte);
/* 60 */       return "application/octet-stream".equals(str) ? null : str;
/*    */     } finally {
/* 62 */       nativeBuffer.release();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static native boolean initialize0();
/*    */   
/*    */   private static native byte[] probe0(long paramLong);
/*    */   
/*    */   static {
/* 71 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 74 */             System.loadLibrary("nio");
/* 75 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/MagicFileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */