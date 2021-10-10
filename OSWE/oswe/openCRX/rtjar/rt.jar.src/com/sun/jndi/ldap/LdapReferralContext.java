/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.dir.SearchFilter;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NotContextException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.StringRefAddr;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.ExtendedRequest;
/*     */ import javax.naming.ldap.ExtendedResponse;
/*     */ import javax.naming.ldap.LdapContext;
/*     */ import javax.naming.spi.NamingManager;
/*     */ 
/*     */ final class LdapReferralContext
/*     */   implements DirContext, LdapContext {
/*     */   private DirContext refCtx;
/*     */   private Name urlName;
/*     */   private String urlAttrs;
/*     */   private String urlScope;
/*     */   private String urlFilter;
/*     */   private LdapReferralException refEx;
/*     */   private boolean skipThisReferral;
/*     */   private int hopCount;
/*     */   private NamingException previousEx;
/*     */   
/*     */   LdapReferralContext(LdapReferralException paramLdapReferralException, Hashtable<?, ?> paramHashtable, Control[] paramArrayOfControl1, Control[] paramArrayOfControl2, String paramString, boolean paramBoolean, int paramInt) throws NamingException {
/*     */     String str;
/*     */     Object object;
/*  44 */     this.refCtx = null;
/*  45 */     this.urlName = null;
/*  46 */     this.urlAttrs = null;
/*  47 */     this.urlScope = null;
/*  48 */     this.urlFilter = null;
/*     */     
/*  50 */     this.refEx = null;
/*  51 */     this.skipThisReferral = false;
/*  52 */     this.hopCount = 1;
/*  53 */     this.previousEx = null;
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
/*  64 */     this.refEx = paramLdapReferralException;
/*     */     
/*  66 */     if (this.skipThisReferral = paramBoolean) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     if (paramHashtable != null) {
/*  74 */       paramHashtable = (Hashtable<?, ?>)paramHashtable.clone();
/*     */ 
/*     */       
/*  77 */       if (paramArrayOfControl1 == null) {
/*  78 */         paramHashtable.remove("java.naming.ldap.control.connect");
/*     */       }
/*  80 */     } else if (paramArrayOfControl1 != null) {
/*  81 */       paramHashtable = new Hashtable<>(5);
/*     */     } 
/*  83 */     if (paramArrayOfControl1 != null) {
/*  84 */       Control[] arrayOfControl = new Control[paramArrayOfControl1.length];
/*  85 */       System.arraycopy(paramArrayOfControl1, 0, arrayOfControl, 0, paramArrayOfControl1.length);
/*     */       
/*  87 */       paramHashtable
/*  88 */         .put("java.naming.ldap.control.connect", arrayOfControl);
/*     */     } 
/*     */     
/*     */     while (true) {
/*     */       try {
/*  93 */         str = this.refEx.getNextReferral();
/*  94 */         if (str == null) {
/*  95 */           if (this.previousEx != null) {
/*  96 */             throw (NamingException)this.previousEx.fillInStackTrace();
/*     */           }
/*  98 */           throw new NamingException("Illegal encoding: referral is empty");
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 103 */       catch (LdapReferralException ldapReferralException) {
/*     */         
/* 105 */         if (paramInt == 2) {
/* 106 */           throw ldapReferralException;
/*     */         }
/* 108 */         this.refEx = ldapReferralException;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 114 */       Reference reference = new Reference("javax.naming.directory.DirContext", new StringRefAddr("URL", str));
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 119 */         object = NamingManager.getObjectInstance(reference, null, null, paramHashtable);
/*     */         break;
/* 121 */       } catch (NamingException namingException) {
/*     */         
/* 123 */         if (paramInt == 2) {
/* 124 */           throw namingException;
/*     */         }
/*     */ 
/*     */         
/* 128 */         this.previousEx = namingException;
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 133 */       catch (Exception exception) {
/* 134 */         NamingException namingException = new NamingException("problem generating object using object factory");
/*     */ 
/*     */         
/* 137 */         namingException.setRootCause(exception);
/* 138 */         throw namingException;
/*     */       } 
/* 140 */     }  if (object instanceof DirContext) {
/* 141 */       this.refCtx = (DirContext)object;
/* 142 */       if (this.refCtx instanceof LdapContext && paramArrayOfControl2 != null) {
/* 143 */         ((LdapContext)this.refCtx).setRequestControls(paramArrayOfControl2);
/*     */       }
/* 145 */       initDefaults(str, paramString);
/*     */     }
/*     */     else {
/*     */       
/* 149 */       NotContextException notContextException = new NotContextException("Cannot create context for: " + str);
/*     */       
/* 151 */       notContextException.setRemainingName((new CompositeName()).add(paramString));
/* 152 */       throw notContextException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initDefaults(String paramString1, String paramString2) throws NamingException {
/*     */     String str;
/*     */     try {
/* 162 */       LdapURL ldapURL = new LdapURL(paramString1);
/* 163 */       str = ldapURL.getDN();
/* 164 */       this.urlAttrs = ldapURL.getAttributes();
/* 165 */       this.urlScope = ldapURL.getScope();
/* 166 */       this.urlFilter = ldapURL.getFilter();
/*     */     }
/* 168 */     catch (NamingException namingException) {
/*     */       
/* 170 */       str = paramString1;
/* 171 */       this.urlAttrs = this.urlScope = this.urlFilter = null;
/*     */     } 
/*     */ 
/*     */     
/* 175 */     if (str == null) {
/* 176 */       str = paramString2;
/*     */     } else {
/*     */       
/* 179 */       str = "";
/*     */     } 
/*     */     
/* 182 */     if (str == null) {
/* 183 */       this.urlName = null;
/*     */     } else {
/* 185 */       this
/* 186 */         .urlName = str.equals("") ? new CompositeName() : (new CompositeName()).add(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws NamingException {
/* 192 */     if (this.refCtx != null) {
/* 193 */       this.refCtx.close();
/* 194 */       this.refCtx = null;
/*     */     } 
/* 196 */     this.refEx = null;
/*     */   }
/*     */   
/*     */   void setHopCount(int paramInt) {
/* 200 */     this.hopCount = paramInt;
/* 201 */     if (this.refCtx != null && this.refCtx instanceof LdapCtx) {
/* 202 */       ((LdapCtx)this.refCtx).setHopCount(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/* 207 */     return lookup(toName(paramString));
/*     */   }
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/* 211 */     if (this.skipThisReferral) {
/* 212 */       throw (NamingException)this.refEx
/* 213 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 216 */     return this.refCtx.lookup(overrideName(paramName));
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 220 */     bind(toName(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 224 */     if (this.skipThisReferral) {
/* 225 */       throw (NamingException)this.refEx
/* 226 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 229 */     this.refCtx.bind(overrideName(paramName), paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 233 */     rebind(toName(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 237 */     if (this.skipThisReferral) {
/* 238 */       throw (NamingException)this.refEx
/* 239 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 242 */     this.refCtx.rebind(overrideName(paramName), paramObject);
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 246 */     unbind(toName(paramString));
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 250 */     if (this.skipThisReferral) {
/* 251 */       throw (NamingException)this.refEx
/* 252 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 255 */     this.refCtx.unbind(overrideName(paramName));
/*     */   }
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 259 */     rename(toName(paramString1), toName(paramString2));
/*     */   }
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 263 */     if (this.skipThisReferral) {
/* 264 */       throw (NamingException)this.refEx
/* 265 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 268 */     this.refCtx.rename(overrideName(paramName1), toName(this.refEx.getNewRdn()));
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 272 */     return list(toName(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 277 */     if (this.skipThisReferral) {
/* 278 */       throw (NamingException)this.refEx
/* 279 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     try {
/* 282 */       NamingEnumeration<SearchResult> namingEnumeration = null;
/*     */       
/* 284 */       if (this.urlScope != null && this.urlScope.equals("base")) {
/* 285 */         SearchControls searchControls = new SearchControls();
/* 286 */         searchControls.setReturningObjFlag(true);
/* 287 */         searchControls.setSearchScope(0);
/*     */ 
/*     */         
/* 290 */         namingEnumeration = this.refCtx.search(overrideName(paramName), "(objectclass=*)", searchControls);
/*     */       } else {
/*     */         
/* 293 */         namingEnumeration = (NamingEnumeration)this.refCtx.list(overrideName(paramName));
/*     */       } 
/*     */       
/* 296 */       this.refEx.setNameResolved(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       ((ReferralEnumeration)namingEnumeration).appendUnprocessedReferrals(this.refEx);
/*     */       
/* 303 */       return (NamingEnumeration)namingEnumeration;
/*     */     }
/* 305 */     catch (LdapReferralException ldapReferralException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 311 */       ldapReferralException.appendUnprocessedReferrals(this.refEx);
/* 312 */       throw (NamingException)ldapReferralException.fillInStackTrace();
/*     */     }
/* 314 */     catch (NamingException namingException) {
/*     */ 
/*     */       
/* 317 */       if (this.refEx != null && !this.refEx.hasMoreReferrals()) {
/* 318 */         this.refEx.setNamingException(namingException);
/*     */       }
/* 320 */       if (this.refEx != null && (this.refEx
/* 321 */         .hasMoreReferrals() || this.refEx
/* 322 */         .hasMoreReferralExceptions())) {
/* 323 */         throw (NamingException)this.refEx
/* 324 */           .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */       }
/* 326 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 333 */     return listBindings(toName(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 339 */     if (this.skipThisReferral) {
/* 340 */       throw (NamingException)this.refEx
/* 341 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/*     */     try {
/* 345 */       NamingEnumeration<SearchResult> namingEnumeration = null;
/*     */       
/* 347 */       if (this.urlScope != null && this.urlScope.equals("base")) {
/* 348 */         SearchControls searchControls = new SearchControls();
/* 349 */         searchControls.setReturningObjFlag(true);
/* 350 */         searchControls.setSearchScope(0);
/*     */         
/* 352 */         namingEnumeration = this.refCtx.search(overrideName(paramName), "(objectclass=*)", searchControls);
/*     */       }
/*     */       else {
/*     */         
/* 356 */         namingEnumeration = (NamingEnumeration)this.refCtx.listBindings(overrideName(paramName));
/*     */       } 
/*     */       
/* 359 */       this.refEx.setNameResolved(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 364 */       ((ReferralEnumeration)namingEnumeration).appendUnprocessedReferrals(this.refEx);
/*     */       
/* 366 */       return (NamingEnumeration)namingEnumeration;
/*     */     }
/* 368 */     catch (LdapReferralException ldapReferralException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       ldapReferralException.appendUnprocessedReferrals(this.refEx);
/* 375 */       throw (NamingException)ldapReferralException.fillInStackTrace();
/*     */     }
/* 377 */     catch (NamingException namingException) {
/*     */ 
/*     */       
/* 380 */       if (this.refEx != null && !this.refEx.hasMoreReferrals()) {
/* 381 */         this.refEx.setNamingException(namingException);
/*     */       }
/* 383 */       if (this.refEx != null && (this.refEx
/* 384 */         .hasMoreReferrals() || this.refEx
/* 385 */         .hasMoreReferralExceptions())) {
/* 386 */         throw (NamingException)this.refEx
/* 387 */           .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */       }
/* 389 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 395 */     destroySubcontext(toName(paramString));
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 399 */     if (this.skipThisReferral) {
/* 400 */       throw (NamingException)this.refEx
/* 401 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 404 */     this.refCtx.destroySubcontext(overrideName(paramName));
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 408 */     return createSubcontext(toName(paramString));
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 412 */     if (this.skipThisReferral) {
/* 413 */       throw (NamingException)this.refEx
/* 414 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 417 */     return this.refCtx.createSubcontext(overrideName(paramName));
/*     */   }
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 421 */     return lookupLink(toName(paramString));
/*     */   }
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 425 */     if (this.skipThisReferral) {
/* 426 */       throw (NamingException)this.refEx
/* 427 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 430 */     return this.refCtx.lookupLink(overrideName(paramName));
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 434 */     return getNameParser(toName(paramString));
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 438 */     if (this.skipThisReferral) {
/* 439 */       throw (NamingException)this.refEx
/* 440 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 443 */     return this.refCtx.getNameParser(overrideName(paramName));
/*     */   }
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 448 */     return composeName(toName(paramString1), toName(paramString2)).toString();
/*     */   }
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 452 */     if (this.skipThisReferral) {
/* 453 */       throw (NamingException)this.refEx
/* 454 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/* 456 */     return this.refCtx.composeName(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 461 */     if (this.skipThisReferral) {
/* 462 */       throw (NamingException)this.refEx
/* 463 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 466 */     return this.refCtx.addToEnvironment(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 471 */     if (this.skipThisReferral) {
/* 472 */       throw (NamingException)this.refEx
/* 473 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 476 */     return this.refCtx.removeFromEnvironment(paramString);
/*     */   }
/*     */   
/*     */   public Hashtable<?, ?> getEnvironment() throws NamingException {
/* 480 */     if (this.skipThisReferral) {
/* 481 */       throw (NamingException)this.refEx
/* 482 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 485 */     return this.refCtx.getEnvironment();
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/* 489 */     return getAttributes(toName(paramString));
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/* 493 */     if (this.skipThisReferral) {
/* 494 */       throw (NamingException)this.refEx
/* 495 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 498 */     return this.refCtx.getAttributes(overrideName(paramName));
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 503 */     return getAttributes(toName(paramString), paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/* 508 */     if (this.skipThisReferral) {
/* 509 */       throw (NamingException)this.refEx
/* 510 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 513 */     return this.refCtx.getAttributes(overrideName(paramName), paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 518 */     modifyAttributes(toName(paramString), paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 523 */     if (this.skipThisReferral) {
/* 524 */       throw (NamingException)this.refEx
/* 525 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 528 */     this.refCtx.modifyAttributes(overrideName(paramName), paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 533 */     modifyAttributes(toName(paramString), paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 538 */     if (this.skipThisReferral) {
/* 539 */       throw (NamingException)this.refEx
/* 540 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 543 */     this.refCtx.modifyAttributes(overrideName(paramName), paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 548 */     bind(toName(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 553 */     if (this.skipThisReferral) {
/* 554 */       throw (NamingException)this.refEx
/* 555 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 558 */     this.refCtx.bind(overrideName(paramName), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 563 */     rebind(toName(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 568 */     if (this.skipThisReferral) {
/* 569 */       throw (NamingException)this.refEx
/* 570 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 573 */     this.refCtx.rebind(overrideName(paramName), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 578 */     return createSubcontext(toName(paramString), paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 583 */     if (this.skipThisReferral) {
/* 584 */       throw (NamingException)this.refEx
/* 585 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 588 */     return this.refCtx.createSubcontext(overrideName(paramName), paramAttributes);
/*     */   }
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 592 */     return getSchema(toName(paramString));
/*     */   }
/*     */   
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/* 596 */     if (this.skipThisReferral) {
/* 597 */       throw (NamingException)this.refEx
/* 598 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 601 */     return this.refCtx.getSchema(overrideName(paramName));
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 606 */     return getSchemaClassDefinition(toName(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/* 611 */     if (this.skipThisReferral) {
/* 612 */       throw (NamingException)this.refEx
/* 613 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 616 */     return this.refCtx.getSchemaClassDefinition(overrideName(paramName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 622 */     return search(toName(paramString), SearchFilter.format(paramAttributes), new SearchControls());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 629 */     return search(paramName, SearchFilter.format(paramAttributes), new SearchControls());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 637 */     SearchControls searchControls = new SearchControls();
/* 638 */     searchControls.setReturningAttributes(paramArrayOfString);
/*     */     
/* 640 */     return search(toName(paramString), SearchFilter.format(paramAttributes), searchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 648 */     SearchControls searchControls = new SearchControls();
/* 649 */     searchControls.setReturningAttributes(paramArrayOfString);
/*     */     
/* 651 */     return search(paramName, SearchFilter.format(paramAttributes), searchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 658 */     return search(toName(paramString1), paramString2, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 665 */     if (this.skipThisReferral) {
/* 666 */       throw (NamingException)this.refEx
/* 667 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 672 */       NamingEnumeration<SearchResult> namingEnumeration = this.refCtx.search(overrideName(paramName), 
/* 673 */           overrideFilter(paramString), 
/* 674 */           overrideAttributesAndScope(paramSearchControls));
/*     */       
/* 676 */       this.refEx.setNameResolved(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 681 */       ((ReferralEnumeration)namingEnumeration).appendUnprocessedReferrals(this.refEx);
/*     */       
/* 683 */       return namingEnumeration;
/*     */     }
/* 685 */     catch (LdapReferralException ldapReferralException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 693 */       ldapReferralException.appendUnprocessedReferrals(this.refEx);
/* 694 */       throw (NamingException)ldapReferralException.fillInStackTrace();
/*     */     }
/* 696 */     catch (NamingException namingException) {
/*     */ 
/*     */       
/* 699 */       if (this.refEx != null && !this.refEx.hasMoreReferrals()) {
/* 700 */         this.refEx.setNamingException(namingException);
/*     */       }
/* 702 */       if (this.refEx != null && (this.refEx
/* 703 */         .hasMoreReferrals() || this.refEx
/* 704 */         .hasMoreReferralExceptions())) {
/* 705 */         throw (NamingException)this.refEx
/* 706 */           .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */       }
/* 708 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 718 */     return search(toName(paramString1), paramString2, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 726 */     if (this.skipThisReferral) {
/* 727 */       throw (NamingException)this.refEx
/* 728 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/*     */     try {
/*     */       NamingEnumeration<SearchResult> namingEnumeration;
/*     */       
/* 734 */       if (this.urlFilter != null) {
/* 735 */         namingEnumeration = this.refCtx.search(overrideName(paramName), this.urlFilter, 
/* 736 */             overrideAttributesAndScope(paramSearchControls));
/*     */       } else {
/* 738 */         namingEnumeration = this.refCtx.search(overrideName(paramName), paramString, paramArrayOfObject, 
/* 739 */             overrideAttributesAndScope(paramSearchControls));
/*     */       } 
/*     */       
/* 742 */       this.refEx.setNameResolved(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 747 */       ((ReferralEnumeration)namingEnumeration).appendUnprocessedReferrals(this.refEx);
/*     */       
/* 749 */       return namingEnumeration;
/*     */     }
/* 751 */     catch (LdapReferralException ldapReferralException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 757 */       ldapReferralException.appendUnprocessedReferrals(this.refEx);
/* 758 */       throw (NamingException)ldapReferralException.fillInStackTrace();
/*     */     }
/* 760 */     catch (NamingException namingException) {
/*     */ 
/*     */       
/* 763 */       if (this.refEx != null && !this.refEx.hasMoreReferrals()) {
/* 764 */         this.refEx.setNamingException(namingException);
/*     */       }
/* 766 */       if (this.refEx != null && (this.refEx
/* 767 */         .hasMoreReferrals() || this.refEx
/* 768 */         .hasMoreReferralExceptions())) {
/* 769 */         throw (NamingException)this.refEx
/* 770 */           .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */       }
/* 772 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameInNamespace() throws NamingException {
/* 778 */     if (this.skipThisReferral) {
/* 779 */       throw (NamingException)this.refEx
/* 780 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/* 782 */     return (this.urlName != null && !this.urlName.isEmpty()) ? this.urlName.get(0) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedResponse extendedOperation(ExtendedRequest paramExtendedRequest) throws NamingException {
/* 790 */     if (this.skipThisReferral) {
/* 791 */       throw (NamingException)this.refEx
/* 792 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 795 */     if (!(this.refCtx instanceof LdapContext)) {
/* 796 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */ 
/*     */     
/* 800 */     return ((LdapContext)this.refCtx).extendedOperation(paramExtendedRequest);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LdapContext newInstance(Control[] paramArrayOfControl) throws NamingException {
/* 806 */     if (this.skipThisReferral) {
/* 807 */       throw (NamingException)this.refEx
/* 808 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 811 */     if (!(this.refCtx instanceof LdapContext)) {
/* 812 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */ 
/*     */     
/* 816 */     return ((LdapContext)this.refCtx).newInstance(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public void reconnect(Control[] paramArrayOfControl) throws NamingException {
/* 820 */     if (this.skipThisReferral) {
/* 821 */       throw (NamingException)this.refEx
/* 822 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 825 */     if (!(this.refCtx instanceof LdapContext)) {
/* 826 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */ 
/*     */     
/* 830 */     ((LdapContext)this.refCtx).reconnect(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public Control[] getConnectControls() throws NamingException {
/* 834 */     if (this.skipThisReferral) {
/* 835 */       throw (NamingException)this.refEx
/* 836 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 839 */     if (!(this.refCtx instanceof LdapContext)) {
/* 840 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */ 
/*     */     
/* 844 */     return ((LdapContext)this.refCtx).getConnectControls();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestControls(Control[] paramArrayOfControl) throws NamingException {
/* 850 */     if (this.skipThisReferral) {
/* 851 */       throw (NamingException)this.refEx
/* 852 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 855 */     if (!(this.refCtx instanceof LdapContext)) {
/* 856 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */ 
/*     */     
/* 860 */     ((LdapContext)this.refCtx).setRequestControls(paramArrayOfControl);
/*     */   }
/*     */   
/*     */   public Control[] getRequestControls() throws NamingException {
/* 864 */     if (this.skipThisReferral) {
/* 865 */       throw (NamingException)this.refEx
/* 866 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 869 */     if (!(this.refCtx instanceof LdapContext)) {
/* 870 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */     
/* 873 */     return ((LdapContext)this.refCtx).getRequestControls();
/*     */   }
/*     */   
/*     */   public Control[] getResponseControls() throws NamingException {
/* 877 */     if (this.skipThisReferral) {
/* 878 */       throw (NamingException)this.refEx
/* 879 */         .appendUnprocessedReferrals(null).fillInStackTrace();
/*     */     }
/*     */     
/* 882 */     if (!(this.refCtx instanceof LdapContext)) {
/* 883 */       throw new NotContextException("Referral context not an instance of LdapContext");
/*     */     }
/*     */     
/* 886 */     return ((LdapContext)this.refCtx).getResponseControls();
/*     */   }
/*     */ 
/*     */   
/*     */   private Name toName(String paramString) throws InvalidNameException {
/* 891 */     return paramString.equals("") ? new CompositeName() : (new CompositeName())
/* 892 */       .add(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Name overrideName(Name paramName) throws InvalidNameException {
/* 900 */     return (this.urlName == null) ? paramName : this.urlName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SearchControls overrideAttributesAndScope(SearchControls paramSearchControls) {
/* 910 */     if (this.urlScope != null || this.urlAttrs != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 916 */       SearchControls searchControls = new SearchControls(paramSearchControls.getSearchScope(), paramSearchControls.getCountLimit(), paramSearchControls.getTimeLimit(), paramSearchControls.getReturningAttributes(), paramSearchControls.getReturningObjFlag(), paramSearchControls.getDerefLinkFlag());
/*     */       
/* 918 */       if (this.urlScope != null) {
/* 919 */         if (this.urlScope.equals("base")) {
/* 920 */           searchControls.setSearchScope(0);
/* 921 */         } else if (this.urlScope.equals("one")) {
/* 922 */           searchControls.setSearchScope(1);
/* 923 */         } else if (this.urlScope.equals("sub")) {
/* 924 */           searchControls.setSearchScope(2);
/*     */         } 
/*     */       }
/*     */       
/* 928 */       if (this.urlAttrs != null) {
/* 929 */         StringTokenizer stringTokenizer = new StringTokenizer(this.urlAttrs, ",");
/* 930 */         int i = stringTokenizer.countTokens();
/* 931 */         String[] arrayOfString = new String[i];
/* 932 */         for (byte b = 0; b < i; b++) {
/* 933 */           arrayOfString[b] = stringTokenizer.nextToken();
/*     */         }
/* 935 */         searchControls.setReturningAttributes(arrayOfString);
/*     */       } 
/*     */       
/* 938 */       return searchControls;
/*     */     } 
/*     */     
/* 941 */     return paramSearchControls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String overrideFilter(String paramString) {
/* 950 */     return (this.urlFilter == null) ? paramString : this.urlFilter;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapReferralContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */