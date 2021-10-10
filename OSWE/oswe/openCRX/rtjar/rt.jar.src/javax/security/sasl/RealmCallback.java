/*    */ package javax.security.sasl;
/*    */ 
/*    */ import javax.security.auth.callback.TextInputCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RealmCallback
/*    */   extends TextInputCallback
/*    */ {
/*    */   private static final long serialVersionUID = -4342673378785456908L;
/*    */   
/*    */   public RealmCallback(String paramString) {
/* 49 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RealmCallback(String paramString1, String paramString2) {
/* 63 */     super(paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/sasl/RealmCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */