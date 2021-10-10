/*    */ package sun.net;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.net.SocketOption;
/*    */ import java.security.AccessController;
/*    */ import jdk.net.NetworkPermission;
/*    */ import jdk.net.SocketFlow;
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
/*    */ public class ExtendedOptionsImpl
/*    */ {
/*    */   static {
/* 47 */     AccessController.doPrivileged(() -> {
/*    */           System.loadLibrary("net");
/*    */           return null;
/*    */         });
/* 51 */     init();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void checkSetOptionPermission(SocketOption<?> paramSocketOption) {
/* 57 */     SecurityManager securityManager = System.getSecurityManager();
/* 58 */     if (securityManager == null) {
/*    */       return;
/*    */     }
/* 61 */     String str = "setOption." + paramSocketOption.name();
/* 62 */     securityManager.checkPermission(new NetworkPermission(str));
/*    */   }
/*    */   
/*    */   public static void checkGetOptionPermission(SocketOption<?> paramSocketOption) {
/* 66 */     SecurityManager securityManager = System.getSecurityManager();
/* 67 */     if (securityManager == null) {
/*    */       return;
/*    */     }
/* 70 */     String str = "getOption." + paramSocketOption.name();
/* 71 */     securityManager.checkPermission(new NetworkPermission(str));
/*    */   }
/*    */   
/*    */   public static void checkValueType(Object paramObject, Class<?> paramClass) {
/* 75 */     if (!paramClass.isAssignableFrom(paramObject.getClass())) {
/*    */       
/* 77 */       String str = "Found: " + paramObject.getClass().toString() + " Expected: " + paramClass.toString();
/* 78 */       throw new IllegalArgumentException(str);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static native void init();
/*    */   
/*    */   public static native void setFlowOption(FileDescriptor paramFileDescriptor, SocketFlow paramSocketFlow);
/*    */   
/*    */   public static native void getFlowOption(FileDescriptor paramFileDescriptor, SocketFlow paramSocketFlow);
/*    */   
/*    */   public static native boolean flowSupported();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ExtendedOptionsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */