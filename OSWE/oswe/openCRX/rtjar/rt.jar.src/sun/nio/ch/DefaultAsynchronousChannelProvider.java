/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.nio.channels.spi.AsynchronousChannelProvider;
/*    */ import java.security.AccessController;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultAsynchronousChannelProvider
/*    */ {
/*    */   private static AsynchronousChannelProvider createProvider(String paramString) {
/*    */     Class<?> clazz;
/*    */     try {
/* 47 */       clazz = Class.forName(paramString);
/* 48 */     } catch (ClassNotFoundException classNotFoundException) {
/* 49 */       throw new AssertionError(classNotFoundException);
/*    */     } 
/*    */     try {
/* 52 */       return (AsynchronousChannelProvider)clazz.newInstance();
/* 53 */     } catch (IllegalAccessException|InstantiationException illegalAccessException) {
/* 54 */       throw new AssertionError(illegalAccessException);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AsynchronousChannelProvider create() {
/* 64 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/* 65 */     if (str.equals("SunOS"))
/* 66 */       return createProvider("sun.nio.ch.SolarisAsynchronousChannelProvider"); 
/* 67 */     if (str.equals("Linux"))
/* 68 */       return createProvider("sun.nio.ch.LinuxAsynchronousChannelProvider"); 
/* 69 */     if (str.contains("OS X"))
/* 70 */       return createProvider("sun.nio.ch.BsdAsynchronousChannelProvider"); 
/* 71 */     if (str.equals("AIX"))
/* 72 */       return createProvider("sun.nio.ch.AixAsynchronousChannelProvider"); 
/* 73 */     throw new InternalError("platform not recognized");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/DefaultAsynchronousChannelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */