/*    */ package com.sun.jndi.url.ldap;
/*    */ 
/*    */ import com.sun.jndi.ldap.LdapCtx;
/*    */ import com.sun.jndi.ldap.LdapCtxFactory;
/*    */ import com.sun.jndi.ldap.LdapURL;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.CompositeName;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.spi.ObjectFactory;
/*    */ import javax.naming.spi.ResolveResult;
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
/*    */ public class ldapURLContextFactory
/*    */   implements ObjectFactory
/*    */ {
/*    */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws Exception {
/* 49 */     if (paramObject == null) {
/* 50 */       return new ldapURLContext(paramHashtable);
/*    */     }
/* 52 */     return LdapCtxFactory.getLdapCtxInstance(paramObject, paramHashtable);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static ResolveResult getUsingURLIgnoreRootDN(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 58 */     LdapURL ldapURL = new LdapURL(paramString);
/*    */     
/* 60 */     LdapCtx ldapCtx = new LdapCtx("", ldapURL.getHost(), ldapURL.getPort(), paramHashtable, ldapURL.useSsl());
/* 61 */     String str = (ldapURL.getDN() != null) ? ldapURL.getDN() : "";
/*    */ 
/*    */     
/* 64 */     CompositeName compositeName = new CompositeName();
/* 65 */     if (!"".equals(str))
/*    */     {
/* 67 */       compositeName.add(str);
/*    */     }
/*    */     
/* 70 */     return new ResolveResult(ldapCtx, compositeName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/ldap/ldapURLContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */