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
/*    */ 
/*    */ public class JMXServerErrorException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 3996732239558744666L;
/*    */   private final Error cause;
/*    */   
/*    */   public JMXServerErrorException(String paramString, Error paramError) {
/* 58 */     super(paramString);
/* 59 */     this.cause = paramError;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 63 */     return this.cause;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXServerErrorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */