/*    */ package sun.security.krb5;
/*    */ 
/*    */ import javax.security.auth.kerberos.KeyTab;
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KerberosSecrets
/*    */ {
/* 32 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*    */   
/*    */   private static JavaxSecurityAuthKerberosAccess javaxSecurityAuthKerberosAccess;
/*    */   
/*    */   public static void setJavaxSecurityAuthKerberosAccess(JavaxSecurityAuthKerberosAccess paramJavaxSecurityAuthKerberosAccess) {
/* 37 */     javaxSecurityAuthKerberosAccess = paramJavaxSecurityAuthKerberosAccess;
/*    */   }
/*    */ 
/*    */   
/*    */   public static JavaxSecurityAuthKerberosAccess getJavaxSecurityAuthKerberosAccess() {
/* 42 */     if (javaxSecurityAuthKerberosAccess == null)
/* 43 */       unsafe.ensureClassInitialized(KeyTab.class); 
/* 44 */     return javaxSecurityAuthKerberosAccess;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KerberosSecrets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */