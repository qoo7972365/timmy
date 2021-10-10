/*    */ package javax.security.auth.kerberos;
/*    */ 
/*    */ import sun.security.krb5.JavaxSecurityAuthKerberosAccess;
/*    */ import sun.security.krb5.internal.ktab.KeyTab;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class JavaxSecurityAuthKerberosAccessImpl
/*    */   implements JavaxSecurityAuthKerberosAccess
/*    */ {
/*    */   public KeyTab keyTabTakeSnapshot(KeyTab paramKeyTab) {
/* 34 */     return paramKeyTab.takeSnapshot();
/*    */   }
/*    */   
/*    */   public KerberosPrincipal kerberosTicketGetClientAlias(KerberosTicket paramKerberosTicket) {
/* 38 */     return paramKerberosTicket.clientAlias;
/*    */   }
/*    */   
/*    */   public void kerberosTicketSetClientAlias(KerberosTicket paramKerberosTicket, KerberosPrincipal paramKerberosPrincipal) {
/* 42 */     paramKerberosTicket.clientAlias = paramKerberosPrincipal;
/*    */   }
/*    */   
/*    */   public KerberosPrincipal kerberosTicketGetServerAlias(KerberosTicket paramKerberosTicket) {
/* 46 */     return paramKerberosTicket.serverAlias;
/*    */   }
/*    */   
/*    */   public void kerberosTicketSetServerAlias(KerberosTicket paramKerberosTicket, KerberosPrincipal paramKerberosPrincipal) {
/* 50 */     paramKerberosTicket.serverAlias = paramKerberosPrincipal;
/*    */   }
/*    */   public KerberosTicket kerberosTicketGetProxy(KerberosTicket paramKerberosTicket) {
/* 53 */     return paramKerberosTicket.proxy;
/*    */   }
/*    */   public void kerberosTicketSetProxy(KerberosTicket paramKerberosTicket1, KerberosTicket paramKerberosTicket2) {
/* 56 */     paramKerberosTicket1.proxy = paramKerberosTicket2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/kerberos/JavaxSecurityAuthKerberosAccessImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */