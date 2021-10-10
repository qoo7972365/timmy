/*    */ package java.net;
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
/*    */ public class ConnectException
/*    */   extends SocketException
/*    */ {
/*    */   private static final long serialVersionUID = 3831404271622369215L;
/*    */   
/*    */   public ConnectException(String paramString) {
/* 47 */     super(paramString);
/*    */   }
/*    */   
/*    */   public ConnectException() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/ConnectException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */