/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.naming.directory.Attributes;
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
/*    */ 
/*    */ final class LdapEntry
/*    */ {
/*    */   String DN;
/*    */   Attributes attributes;
/* 41 */   Vector<Control> respCtls = null;
/*    */   
/*    */   LdapEntry(String paramString, Attributes paramAttributes) {
/* 44 */     this.DN = paramString;
/* 45 */     this.attributes = paramAttributes;
/*    */   }
/*    */   
/*    */   LdapEntry(String paramString, Attributes paramAttributes, Vector<Control> paramVector) {
/* 49 */     this.DN = paramString;
/* 50 */     this.attributes = paramAttributes;
/* 51 */     this.respCtls = paramVector;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */