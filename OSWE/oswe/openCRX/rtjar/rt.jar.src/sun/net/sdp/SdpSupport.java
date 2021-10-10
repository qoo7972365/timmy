/*    */ package sun.net.sdp;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import sun.misc.JavaIOFileDescriptorAccess;
/*    */ import sun.misc.SharedSecrets;
/*    */ import sun.security.action.GetPropertyAction;
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
/*    */ public final class SdpSupport
/*    */ {
/* 43 */   private static final String os = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/* 44 */   private static final boolean isSupported = (os.equals("SunOS") || os.equals("Linux"));
/*    */   
/* 46 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FileDescriptor createSocket() throws IOException {
/* 54 */     if (!isSupported)
/* 55 */       throw new UnsupportedOperationException("SDP not supported on this platform"); 
/* 56 */     int i = create0();
/* 57 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 58 */     fdAccess.set(fileDescriptor, i);
/* 59 */     return fileDescriptor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void convertSocket(FileDescriptor paramFileDescriptor) throws IOException {
/* 67 */     if (!isSupported)
/* 68 */       throw new UnsupportedOperationException("SDP not supported on this platform"); 
/* 69 */     int i = fdAccess.get(paramFileDescriptor);
/* 70 */     convert0(i);
/*    */   }
/*    */   
/*    */   private static native int create0() throws IOException;
/*    */   
/*    */   private static native void convert0(int paramInt) throws IOException;
/*    */   
/*    */   static {
/* 78 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 81 */             System.loadLibrary("net");
/* 82 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/sdp/SdpSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */