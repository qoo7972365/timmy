/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import javax.naming.NameClassPair;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.ldap.Control;
/*    */ import javax.naming.ldap.HasControls;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NameClassPairWithControls
/*    */   extends NameClassPair
/*    */   implements HasControls
/*    */ {
/*    */   private Control[] controls;
/*    */   private static final long serialVersionUID = 2010738921219112944L;
/*    */   
/*    */   public NameClassPairWithControls(String paramString1, String paramString2, Control[] paramArrayOfControl) {
/* 36 */     super(paramString1, paramString2);
/* 37 */     this.controls = paramArrayOfControl;
/*    */   }
/*    */   
/*    */   public Control[] getControls() throws NamingException {
/* 41 */     return this.controls;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/NameClassPairWithControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */