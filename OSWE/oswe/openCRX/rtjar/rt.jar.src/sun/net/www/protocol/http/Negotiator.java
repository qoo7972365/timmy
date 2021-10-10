/*    */ package sun.net.www.protocol.http;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Constructor;
/*    */ import sun.util.logging.PlatformLogger;
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
/*    */ public abstract class Negotiator
/*    */ {
/*    */   static Negotiator getNegotiator(HttpCallerInfo paramHttpCallerInfo) {
/*    */     Constructor<?> constructor;
/*    */     try {
/* 51 */       Class<?> clazz = Class.forName("sun.net.www.protocol.http.spnego.NegotiatorImpl", true, null);
/* 52 */       constructor = clazz.getConstructor(new Class[] { HttpCallerInfo.class });
/* 53 */     } catch (ClassNotFoundException classNotFoundException) {
/* 54 */       finest(classNotFoundException);
/* 55 */       return null;
/* 56 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*    */ 
/*    */       
/* 59 */       throw new AssertionError(reflectiveOperationException);
/*    */     } 
/*    */     
/*    */     try {
/* 63 */       return (Negotiator)constructor.newInstance(new Object[] { paramHttpCallerInfo });
/* 64 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 65 */       finest(reflectiveOperationException);
/* 66 */       Throwable throwable = reflectiveOperationException.getCause();
/* 67 */       if (throwable != null && throwable instanceof Exception)
/* 68 */         finest((Exception)throwable); 
/* 69 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract byte[] firstToken() throws IOException;
/*    */   
/*    */   public abstract byte[] nextToken(byte[] paramArrayOfbyte) throws IOException;
/*    */   
/*    */   private static void finest(Exception paramException) {
/* 78 */     PlatformLogger platformLogger = HttpURLConnection.getHttpLogger();
/* 79 */     if (platformLogger.isLoggable(PlatformLogger.Level.FINEST))
/* 80 */       platformLogger.finest("NegotiateAuthentication: " + paramException); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/Negotiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */