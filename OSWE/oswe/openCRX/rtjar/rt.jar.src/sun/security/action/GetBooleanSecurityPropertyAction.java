/*    */ package sun.security.action;
/*    */ 
/*    */ import java.security.PrivilegedAction;
/*    */ import java.security.Security;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetBooleanSecurityPropertyAction
/*    */   implements PrivilegedAction<Boolean>
/*    */ {
/*    */   private String theProp;
/*    */   
/*    */   public GetBooleanSecurityPropertyAction(String paramString) {
/* 57 */     this.theProp = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean run() {
/* 67 */     boolean bool = false;
/*    */     try {
/* 69 */       String str = Security.getProperty(this.theProp);
/* 70 */       bool = (str != null && str.equalsIgnoreCase("true")) ? true : false;
/* 71 */     } catch (NullPointerException nullPointerException) {}
/* 72 */     return Boolean.valueOf(bool);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/action/GetBooleanSecurityPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */