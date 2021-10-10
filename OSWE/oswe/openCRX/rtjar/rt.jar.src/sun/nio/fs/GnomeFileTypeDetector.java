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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GnomeFileTypeDetector
/*    */   extends AbstractFileTypeDetector
/*    */ {
/*    */   private static final String GNOME_VFS_MIME_TYPE_UNKNOWN = "application/octet-stream";
/* 51 */   private final boolean gioAvailable = initializeGio(); public GnomeFileTypeDetector() {
/* 52 */     if (this.gioAvailable) {
/* 53 */       this.gnomeVfsAvailable = false;
/*    */     } else {
/* 55 */       this.gnomeVfsAvailable = initializeGnomeVfs();
/*    */     } 
/*    */   }
/*    */   private final boolean gnomeVfsAvailable;
/*    */   
/*    */   public String implProbeContentType(Path paramPath) throws IOException {
/* 61 */     if (!this.gioAvailable && !this.gnomeVfsAvailable)
/* 62 */       return null; 
/* 63 */     if (!(paramPath instanceof UnixPath)) {
/* 64 */       return null;
/*    */     }
/* 66 */     UnixPath unixPath = (UnixPath)paramPath;
/* 67 */     NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(unixPath.getByteArrayForSysCalls());
/*    */     try {
/* 69 */       if (this.gioAvailable) {
/*    */         
/* 71 */         unixPath.checkRead();
/* 72 */         byte[] arrayOfByte1 = probeUsingGio(nativeBuffer.address());
/* 73 */         return (arrayOfByte1 == null) ? null : Util.toString(arrayOfByte1);
/*    */       } 
/* 75 */       byte[] arrayOfByte = probeUsingGnomeVfs(nativeBuffer.address());
/* 76 */       if (arrayOfByte == null)
/* 77 */         return null; 
/* 78 */       String str = Util.toString(arrayOfByte);
/* 79 */       return str.equals("application/octet-stream") ? null : str;
/*    */     } finally {
/*    */       
/* 82 */       nativeBuffer.release();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static native boolean initializeGio();
/*    */   
/*    */   private static synchronized native byte[] probeUsingGio(long paramLong);
/*    */   
/*    */   private static native boolean initializeGnomeVfs();
/*    */   
/*    */   private static native byte[] probeUsingGnomeVfs(long paramLong);
/*    */   
/*    */   static {
/* 96 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*    */           public Void run() {
/* 98 */             System.loadLibrary("nio");
/* 99 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/GnomeFileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */