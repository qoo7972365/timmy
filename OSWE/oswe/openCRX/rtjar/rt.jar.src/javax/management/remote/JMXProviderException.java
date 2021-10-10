/*    */ package javax.management.remote;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JMXProviderException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -3166703627550447198L;
/*    */   
/*    */   public JMXProviderException() {}
/*    */   
/*    */   public JMXProviderException(String paramString) {
/* 58 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JMXProviderException(String paramString, Throwable paramThrowable) {
/* 69 */     super(paramString);
/* 70 */     this.cause = paramThrowable;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 74 */     return this.cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   private Throwable cause = null;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXProviderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */