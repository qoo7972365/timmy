/*    */ package sun.net;
/*    */ 
/*    */ import java.net.SocketException;
/*    */ import java.security.AccessController;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceManager
/*    */ {
/*    */   private static final int DEFAULT_MAX_SOCKETS = 25;
/*    */   private static final int maxSockets;
/*    */   
/*    */   static {
/* 56 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.net.maxDatagramSockets"));
/*    */ 
/*    */     
/* 59 */     int i = 25;
/*    */     try {
/* 61 */       if (str != null) {
/* 62 */         i = Integer.parseInt(str);
/*    */       }
/* 64 */     } catch (NumberFormatException numberFormatException) {}
/* 65 */     maxSockets = i;
/* 66 */   } private static final AtomicInteger numSockets = new AtomicInteger(0);
/*    */ 
/*    */   
/*    */   public static void beforeUdpCreate() throws SocketException {
/* 70 */     if (System.getSecurityManager() != null && 
/* 71 */       numSockets.incrementAndGet() > maxSockets) {
/* 72 */       numSockets.decrementAndGet();
/* 73 */       throw new SocketException("maximum number of DatagramSockets reached");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void afterUdpClose() {
/* 79 */     if (System.getSecurityManager() != null)
/* 80 */       numSockets.decrementAndGet(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */