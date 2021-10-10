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
/*    */ public class KrbApErrException
/*    */   extends KrbException
/*    */ {
/*    */   private static final long serialVersionUID = 7545264413323118315L;
/*    */   
/*    */   public KrbApErrException(int paramInt) {
/* 39 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public KrbApErrException(int paramInt, String paramString) {
/* 43 */     super(paramInt, paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KrbApErrException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */