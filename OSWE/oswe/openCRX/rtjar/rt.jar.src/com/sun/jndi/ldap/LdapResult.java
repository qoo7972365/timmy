/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.naming.directory.BasicAttributes;
/*    */ import javax.naming.ldap.Control;
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
/*    */ public final class LdapResult
/*    */ {
/*    */   int msgId;
/*    */   public int status;
/*    */   String matchedDN;
/*    */   String errorMessage;
/* 42 */   Vector<Vector<String>> referrals = null;
/* 43 */   LdapReferralException refEx = null;
/* 44 */   Vector<LdapEntry> entries = null;
/* 45 */   Vector<Control> resControls = null;
/* 46 */   public byte[] serverCreds = null;
/* 47 */   String extensionId = null;
/* 48 */   byte[] extensionValue = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean compareToSearchResult(String paramString) {
/*    */     BasicAttributes basicAttributes;
/*    */     LdapEntry ldapEntry;
/* 57 */     boolean bool = false;
/*    */     
/* 59 */     switch (this.status)
/*    */     { case 6:
/* 61 */         this.status = 0;
/* 62 */         this.entries = new Vector<>(1, 1);
/* 63 */         basicAttributes = new BasicAttributes(true);
/* 64 */         ldapEntry = new LdapEntry(paramString, basicAttributes);
/* 65 */         this.entries.addElement(ldapEntry);
/* 66 */         bool = true;
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
/* 80 */         return bool;case 5: this.status = 0; this.entries = new Vector<>(0); bool = true; return bool; }  bool = false; return bool;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */