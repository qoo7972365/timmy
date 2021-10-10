/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.ctx.Continuation;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Vector;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.spi.DirectoryManager;
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
/*     */ final class LdapBindingEnumeration
/*     */   extends AbstractLdapNamingEnumeration<Binding>
/*     */ {
/*  43 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */   
/*     */   LdapBindingEnumeration(LdapCtx paramLdapCtx, LdapResult paramLdapResult, Name paramName, Continuation paramContinuation) throws NamingException {
/*  48 */     super(paramLdapCtx, paramLdapResult, paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Binding createItem(String paramString, final Attributes attrs, Vector<Control> paramVector) throws NamingException {
/*     */     Binding binding;
/*  56 */     Object object = null;
/*  57 */     String str = getAtom(paramString);
/*     */     
/*  59 */     if (attrs.get(Obj.JAVA_ATTRIBUTES[2]) != null) {
/*     */       
/*     */       try {
/*  62 */         object = AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */             {
/*     */               public Object run() throws NamingException {
/*  65 */                 return Obj.decodeObject(attrs);
/*     */               }
/*     */             },  this.acc);
/*  68 */       } catch (PrivilegedActionException privilegedActionException) {
/*  69 */         throw (NamingException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/*  72 */     if (object == null)
/*     */     {
/*  74 */       object = new LdapCtx(this.homeCtx, paramString);
/*     */     }
/*     */     
/*  77 */     CompositeName compositeName = new CompositeName();
/*  78 */     compositeName.add(str);
/*     */     
/*     */     try {
/*  81 */       object = DirectoryManager.getObjectInstance(object, compositeName, this.homeCtx, this.homeCtx.envprops, attrs);
/*     */     
/*     */     }
/*  84 */     catch (NamingException namingException) {
/*  85 */       throw namingException;
/*     */     }
/*  87 */     catch (Exception exception) {
/*  88 */       NamingException namingException = new NamingException("problem generating object using object factory");
/*     */ 
/*     */       
/*  91 */       namingException.setRootCause(exception);
/*  92 */       throw namingException;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (paramVector != null) {
/*     */       
/*  98 */       binding = new BindingWithControls(compositeName.toString(), object, this.homeCtx.convertControls(paramVector));
/*     */     } else {
/* 100 */       binding = new Binding(compositeName.toString(), object);
/*     */     } 
/* 102 */     binding.setNameInNamespace(paramString);
/* 103 */     return binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLdapNamingEnumeration<? extends NameClassPair> getReferredResults(LdapReferralContext paramLdapReferralContext) throws NamingException {
/* 110 */     return (AbstractLdapNamingEnumeration)paramLdapReferralContext.listBindings(this.listArg);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapBindingEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */