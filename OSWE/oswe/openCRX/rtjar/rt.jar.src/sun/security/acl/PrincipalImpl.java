/*    */ package sun.security.acl;
/*    */ 
/*    */ import java.security.Principal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrincipalImpl
/*    */   implements Principal
/*    */ {
/*    */   private String user;
/*    */   
/*    */   public PrincipalImpl(String paramString) {
/* 44 */     this.user = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 55 */     if (paramObject instanceof PrincipalImpl) {
/* 56 */       PrincipalImpl principalImpl = (PrincipalImpl)paramObject;
/* 57 */       return this.user.equals(principalImpl.toString());
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return this.user;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return this.user.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 80 */     return this.user;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/acl/PrincipalImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */