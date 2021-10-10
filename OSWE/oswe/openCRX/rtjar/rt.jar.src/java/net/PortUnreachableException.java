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
/*    */ public class PortUnreachableException
/*    */   extends SocketException
/*    */ {
/*    */   private static final long serialVersionUID = 8462541992376507323L;
/*    */   
/*    */   public PortUnreachableException(String paramString) {
/* 44 */     super(paramString);
/*    */   }
/*    */   
/*    */   public PortUnreachableException() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/PortUnreachableException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */