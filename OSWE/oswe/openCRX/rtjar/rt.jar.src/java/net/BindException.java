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
/*    */ public class BindException
/*    */   extends SocketException
/*    */ {
/*    */   private static final long serialVersionUID = -5945005768251722951L;
/*    */   
/*    */   public BindException(String paramString) {
/* 47 */     super(paramString);
/*    */   }
/*    */   
/*    */   public BindException() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/BindException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */