/*    */ package sun.net;
/*    */ 
/*    */ import java.net.Proxy;
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
/*    */ public final class ApplicationProxy
/*    */   extends Proxy
/*    */ {
/*    */   private ApplicationProxy(Proxy paramProxy) {
/* 37 */     super(paramProxy.type(), paramProxy.address());
/*    */   }
/*    */   
/*    */   public static ApplicationProxy create(Proxy paramProxy) {
/* 41 */     return new ApplicationProxy(paramProxy);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ApplicationProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */