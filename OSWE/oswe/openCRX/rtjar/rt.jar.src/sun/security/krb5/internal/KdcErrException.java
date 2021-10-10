/*    */ package sun.security.krb5.internal;
/*    */ 
/*    */ import sun.security.krb5.KrbException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KdcErrException
/*    */   extends KrbException
/*    */ {
/*    */   private static final long serialVersionUID = -8788186031117310306L;
/*    */   
/*    */   public KdcErrException(int paramInt) {
/* 39 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public KdcErrException(int paramInt, String paramString) {
/* 43 */     super(paramInt, paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KdcErrException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */