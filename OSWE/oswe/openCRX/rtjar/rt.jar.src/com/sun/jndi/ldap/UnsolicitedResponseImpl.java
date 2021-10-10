/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.UnsolicitedNotification;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UnsolicitedResponseImpl
/*     */   implements UnsolicitedNotification
/*     */ {
/*     */   private String oid;
/*     */   private String[] referrals;
/*     */   private byte[] extensionValue;
/*     */   private NamingException exception;
/*     */   private Control[] controls;
/*     */   private static final long serialVersionUID = 5913778898401784775L;
/*     */   
/*     */   UnsolicitedResponseImpl(String paramString1, byte[] paramArrayOfbyte, Vector<Vector<String>> paramVector, int paramInt, String paramString2, String paramString3, Control[] paramArrayOfControl) {
/*  46 */     this.oid = paramString1;
/*  47 */     this.extensionValue = paramArrayOfbyte;
/*     */     
/*  49 */     if (paramVector != null && paramVector.size() > 0) {
/*  50 */       int i = paramVector.size();
/*  51 */       this.referrals = new String[i];
/*  52 */       for (byte b = 0; b < i; b++)
/*     */       {
/*  54 */         this.referrals[b] = ((Vector<String>)paramVector.elementAt(b)).elementAt(0);
/*     */       }
/*     */     } 
/*  57 */     this.exception = LdapCtx.mapErrorCode(paramInt, paramString2);
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.controls = paramArrayOfControl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getID() {
/*  71 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncodedValue() {
/*  86 */     return this.extensionValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getReferrals() {
/*  96 */     return this.referrals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingException getException() {
/* 107 */     return this.exception;
/*     */   }
/*     */   
/*     */   public Control[] getControls() throws NamingException {
/* 111 */     return this.controls;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/UnsolicitedResponseImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */