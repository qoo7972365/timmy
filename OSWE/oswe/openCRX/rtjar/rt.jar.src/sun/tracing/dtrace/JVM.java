/*    */ package sun.tracing.dtrace;
/*    */ 
/*    */ import java.lang.reflect.Method;
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
/*    */ class JVM
/*    */ {
/*    */   static {
/* 38 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 41 */             System.loadLibrary("jsdt");
/* 42 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   static long activate(String paramString, DTraceProvider[] paramArrayOfDTraceProvider) {
/* 48 */     return activate0(paramString, paramArrayOfDTraceProvider);
/*    */   }
/*    */   
/*    */   static void dispose(long paramLong) {
/* 52 */     dispose0(paramLong);
/*    */   }
/*    */   
/*    */   static boolean isEnabled(Method paramMethod) {
/* 56 */     return isEnabled0(paramMethod);
/*    */   }
/*    */   
/*    */   static boolean isSupported() {
/* 60 */     return isSupported0();
/*    */   }
/*    */ 
/*    */   
/*    */   static Class<?> defineClass(ClassLoader paramClassLoader, String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 65 */     return defineClass0(paramClassLoader, paramString, paramArrayOfbyte, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   private static native long activate0(String paramString, DTraceProvider[] paramArrayOfDTraceProvider);
/*    */   
/*    */   private static native void dispose0(long paramLong);
/*    */   
/*    */   private static native boolean isEnabled0(Method paramMethod);
/*    */   
/*    */   private static native boolean isSupported0();
/*    */   
/*    */   private static native Class<?> defineClass0(ClassLoader paramClassLoader, String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/dtrace/JVM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */