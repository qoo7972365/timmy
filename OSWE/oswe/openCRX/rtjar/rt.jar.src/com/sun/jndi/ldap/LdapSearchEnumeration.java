/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.ctx.Continuation;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Vector;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.LdapName;
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
/*     */ final class LdapSearchEnumeration
/*     */   extends AbstractLdapNamingEnumeration<SearchResult>
/*     */ {
/*     */   private Name startName;
/*  45 */   private LdapCtx.SearchArgs searchArgs = null;
/*     */   
/*  47 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LdapSearchEnumeration(LdapCtx paramLdapCtx, LdapResult paramLdapResult, String paramString, LdapCtx.SearchArgs paramSearchArgs, Continuation paramContinuation) throws NamingException {
/*  53 */     super(paramLdapCtx, paramLdapResult, paramSearchArgs.name, paramContinuation);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.startName = new LdapName(paramString);
/*  59 */     this.searchArgs = paramSearchArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SearchResult createItem(String paramString, final Attributes attrs, Vector<Control> paramVector) throws NamingException {
/*     */     String str1, str2;
/*     */     SearchResult searchResult;
/*  67 */     Object object = null;
/*     */ 
/*     */ 
/*     */     
/*  71 */     boolean bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  77 */       LdapName ldapName = new LdapName(paramString);
/*     */ 
/*     */ 
/*     */       
/*  81 */       if (this.startName != null && ldapName.startsWith(this.startName)) {
/*  82 */         str1 = ldapName.getSuffix(this.startName.size()).toString();
/*  83 */         str2 = ldapName.getSuffix(this.homeCtx.currentParsedDN.size()).toString();
/*     */       } else {
/*  85 */         bool = false;
/*     */         
/*  87 */         str2 = str1 = LdapURL.toUrlString(this.homeCtx.hostname, this.homeCtx.port_number, paramString, this.homeCtx.hasLdapsScheme);
/*     */       }
/*     */     
/*  90 */     } catch (NamingException namingException) {
/*     */       
/*  92 */       bool = false;
/*     */       
/*  94 */       str2 = str1 = LdapURL.toUrlString(this.homeCtx.hostname, this.homeCtx.port_number, paramString, this.homeCtx.hasLdapsScheme);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  99 */     CompositeName compositeName1 = new CompositeName();
/* 100 */     if (!str1.equals("")) {
/* 101 */       compositeName1.add(str1);
/*     */     }
/*     */ 
/*     */     
/* 105 */     CompositeName compositeName2 = new CompositeName();
/* 106 */     if (!str2.equals("")) {
/* 107 */       compositeName2.add(str2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.homeCtx.setParents(attrs, compositeName2);
/*     */ 
/*     */     
/* 116 */     if (this.searchArgs.cons.getReturningObjFlag()) {
/*     */       
/* 118 */       if (attrs.get(Obj.JAVA_ATTRIBUTES[2]) != null) {
/*     */         
/*     */         try {
/*     */           
/* 122 */           object = AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */               {
/*     */                 public Object run() throws NamingException {
/* 125 */                   return Obj.decodeObject(attrs);
/*     */                 }
/*     */               },  this.acc);
/* 128 */         } catch (PrivilegedActionException privilegedActionException) {
/* 129 */           throw (NamingException)privilegedActionException.getException();
/*     */         } 
/*     */       }
/* 132 */       if (object == null) {
/* 133 */         object = new LdapCtx(this.homeCtx, paramString);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 139 */         object = DirectoryManager.getObjectInstance(object, compositeName2, bool ? this.homeCtx : null, this.homeCtx.envprops, attrs);
/*     */       
/*     */       }
/* 142 */       catch (NamingException namingException) {
/* 143 */         throw namingException;
/* 144 */       } catch (Exception exception) {
/* 145 */         NamingException namingException = new NamingException("problem generating object using object factory");
/*     */ 
/*     */         
/* 148 */         namingException.setRootCause(exception);
/* 149 */         throw namingException;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       String[] arrayOfString;
/*     */ 
/*     */       
/* 157 */       if ((arrayOfString = this.searchArgs.reqAttrs) != null) {
/*     */         
/* 159 */         BasicAttributes basicAttributes = new BasicAttributes(true); byte b;
/* 160 */         for (b = 0; b < arrayOfString.length; b++) {
/* 161 */           basicAttributes.put(arrayOfString[b], null);
/*     */         }
/* 163 */         for (b = 0; b < Obj.JAVA_ATTRIBUTES.length; b++) {
/*     */           
/* 165 */           if (basicAttributes.get(Obj.JAVA_ATTRIBUTES[b]) == null) {
/* 166 */             attrs.remove(Obj.JAVA_ATTRIBUTES[b]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (paramVector != null) {
/*     */ 
/*     */       
/* 183 */       searchResult = new SearchResultWithControls(bool ? compositeName1.toString() : str1, object, attrs, bool, this.homeCtx.convertControls(paramVector));
/*     */     } else {
/*     */       
/* 186 */       searchResult = new SearchResult(bool ? compositeName1.toString() : str1, object, attrs, bool);
/*     */     } 
/*     */     
/* 189 */     searchResult.setNameInNamespace(paramString);
/* 190 */     return searchResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendUnprocessedReferrals(LdapReferralException paramLdapReferralException) {
/* 197 */     this.startName = null;
/* 198 */     super.appendUnprocessedReferrals(paramLdapReferralException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractLdapNamingEnumeration<? extends NameClassPair> getReferredResults(LdapReferralContext paramLdapReferralContext) throws NamingException {
/* 205 */     return (AbstractLdapNamingEnumeration)paramLdapReferralContext.search(this.searchArgs.name, this.searchArgs.filter, this.searchArgs.cons);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(AbstractLdapNamingEnumeration<? extends NameClassPair> paramAbstractLdapNamingEnumeration) {
/* 211 */     super.update(paramAbstractLdapNamingEnumeration);
/*     */ 
/*     */     
/* 214 */     LdapSearchEnumeration ldapSearchEnumeration = (LdapSearchEnumeration)paramAbstractLdapNamingEnumeration;
/* 215 */     this.startName = ldapSearchEnumeration.startName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setStartName(Name paramName) {
/* 221 */     this.startName = paramName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapSearchEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */