/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import com.sun.jndi.toolkit.ctx.Continuation;
/*    */ import java.util.Vector;
/*    */ import javax.naming.CompositeName;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NameClassPair;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.Attributes;
/*    */ import javax.naming.directory.DirContext;
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
/*    */ final class LdapNamingEnumeration
/*    */   extends AbstractLdapNamingEnumeration<NameClassPair>
/*    */ {
/* 39 */   private static final String defaultClassName = DirContext.class.getName();
/*    */ 
/*    */   
/*    */   LdapNamingEnumeration(LdapCtx paramLdapCtx, LdapResult paramLdapResult, Name paramName, Continuation paramContinuation) throws NamingException {
/* 43 */     super(paramLdapCtx, paramLdapResult, paramName, paramContinuation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected NameClassPair createItem(String paramString, Attributes paramAttributes, Vector<Control> paramVector) throws NamingException {
/*    */     NameClassPair nameClassPair;
/* 51 */     String str = null;
/*    */     
/*    */     Attribute attribute;
/* 54 */     if ((attribute = paramAttributes.get(Obj.JAVA_ATTRIBUTES[2])) != null) {
/* 55 */       str = (String)attribute.get();
/*    */     } else {
/* 57 */       str = defaultClassName;
/*    */     } 
/* 59 */     CompositeName compositeName = new CompositeName();
/* 60 */     compositeName.add(getAtom(paramString));
/*    */ 
/*    */     
/* 63 */     if (paramVector != null) {
/*    */ 
/*    */       
/* 66 */       nameClassPair = new NameClassPairWithControls(compositeName.toString(), str, this.homeCtx.convertControls(paramVector));
/*    */     } else {
/* 68 */       nameClassPair = new NameClassPair(compositeName.toString(), str);
/*    */     } 
/* 70 */     nameClassPair.setNameInNamespace(paramString);
/* 71 */     return nameClassPair;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractLdapNamingEnumeration<? extends NameClassPair> getReferredResults(LdapReferralContext paramLdapReferralContext) throws NamingException {
/* 78 */     return (AbstractLdapNamingEnumeration<? extends NameClassPair>)paramLdapReferralContext.list(this.listArg);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapNamingEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */