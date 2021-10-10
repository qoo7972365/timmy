/*     */ package com.sun.jndi.url.ldap;
/*     */ 
/*     */ import com.sun.jndi.ldap.LdapURL;
/*     */ import com.sun.jndi.toolkit.url.GenericURLDirContext;
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
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.spi.ResolveResult;
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
/*     */ public final class ldapURLContext
/*     */   extends GenericURLDirContext
/*     */ {
/*     */   ldapURLContext(Hashtable<?, ?> paramHashtable) {
/*  46 */     super(paramHashtable);
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
/*     */   protected ResolveResult getRootURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  61 */     return ldapURLContextFactory.getUsingURLIgnoreRootDN(paramString, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Name getURLSuffix(String paramString1, String paramString2) throws NamingException {
/*  71 */     LdapURL ldapURL = new LdapURL(paramString2);
/*  72 */     String str = (ldapURL.getDN() != null) ? ldapURL.getDN() : "";
/*     */ 
/*     */     
/*  75 */     CompositeName compositeName = new CompositeName();
/*  76 */     if (!"".equals(str))
/*     */     {
/*  78 */       compositeName.add(str);
/*     */     }
/*  80 */     return compositeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/*  91 */     if (LdapURL.hasQueryComponents(paramString)) {
/*  92 */       throw new InvalidNameException(paramString);
/*     */     }
/*  94 */     return super.lookup(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/*  99 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 100 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 102 */     return super.lookup(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 107 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 108 */       throw new InvalidNameException(paramString);
/*     */     }
/* 110 */     super.bind(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 115 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 116 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 118 */     super.bind(paramName, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 123 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 124 */       throw new InvalidNameException(paramString);
/*     */     }
/* 126 */     super.rebind(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 131 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 132 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 134 */     super.rebind(paramName, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 139 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 140 */       throw new InvalidNameException(paramString);
/*     */     }
/* 142 */     super.unbind(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 147 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 148 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 150 */     super.unbind(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 155 */     if (LdapURL.hasQueryComponents(paramString1))
/* 156 */       throw new InvalidNameException(paramString1); 
/* 157 */     if (LdapURL.hasQueryComponents(paramString2)) {
/* 158 */       throw new InvalidNameException(paramString2);
/*     */     }
/* 160 */     super.rename(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 165 */     if (LdapURL.hasQueryComponents(paramName1.get(0)))
/* 166 */       throw new InvalidNameException(paramName1.toString()); 
/* 167 */     if (LdapURL.hasQueryComponents(paramName2.get(0))) {
/* 168 */       throw new InvalidNameException(paramName2.toString());
/*     */     }
/* 170 */     super.rename(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 176 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 177 */       throw new InvalidNameException(paramString);
/*     */     }
/* 179 */     return super.list(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 185 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 186 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 188 */     return super.list(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 194 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 195 */       throw new InvalidNameException(paramString);
/*     */     }
/* 197 */     return super.listBindings(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 203 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 204 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 206 */     return super.listBindings(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 211 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 212 */       throw new InvalidNameException(paramString);
/*     */     }
/* 214 */     super.destroySubcontext(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 219 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 220 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 222 */     super.destroySubcontext(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 227 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 228 */       throw new InvalidNameException(paramString);
/*     */     }
/* 230 */     return super.createSubcontext(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 235 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 236 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 238 */     return super.createSubcontext(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 243 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 244 */       throw new InvalidNameException(paramString);
/*     */     }
/* 246 */     return super.lookupLink(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 251 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 252 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 254 */     return super.lookupLink(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 259 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 260 */       throw new InvalidNameException(paramString);
/*     */     }
/* 262 */     return super.getNameParser(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 267 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 268 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 270 */     return super.getNameParser(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 276 */     if (LdapURL.hasQueryComponents(paramString1))
/* 277 */       throw new InvalidNameException(paramString1); 
/* 278 */     if (LdapURL.hasQueryComponents(paramString2)) {
/* 279 */       throw new InvalidNameException(paramString2);
/*     */     }
/* 281 */     return super.composeName(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 286 */     if (LdapURL.hasQueryComponents(paramName1.get(0)))
/* 287 */       throw new InvalidNameException(paramName1.toString()); 
/* 288 */     if (LdapURL.hasQueryComponents(paramName2.get(0))) {
/* 289 */       throw new InvalidNameException(paramName2.toString());
/*     */     }
/* 291 */     return super.composeName(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/* 296 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 297 */       throw new InvalidNameException(paramString);
/*     */     }
/* 299 */     return super.getAttributes(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/* 304 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 305 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 307 */     return super.getAttributes(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 313 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 314 */       throw new InvalidNameException(paramString);
/*     */     }
/* 316 */     return super.getAttributes(paramString, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/* 322 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 323 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 325 */     return super.getAttributes(paramName, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 331 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 332 */       throw new InvalidNameException(paramString);
/*     */     }
/* 334 */     super.modifyAttributes(paramString, paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 340 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 341 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 343 */     super.modifyAttributes(paramName, paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 349 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 350 */       throw new InvalidNameException(paramString);
/*     */     }
/* 352 */     super.modifyAttributes(paramString, paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 358 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 359 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 361 */     super.modifyAttributes(paramName, paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 367 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 368 */       throw new InvalidNameException(paramString);
/*     */     }
/* 370 */     super.bind(paramString, paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 376 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 377 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 379 */     super.bind(paramName, paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 385 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 386 */       throw new InvalidNameException(paramString);
/*     */     }
/* 388 */     super.rebind(paramString, paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 394 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 395 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 397 */     super.rebind(paramName, paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 403 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 404 */       throw new InvalidNameException(paramString);
/*     */     }
/* 406 */     return super.createSubcontext(paramString, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 412 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 413 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 415 */     return super.createSubcontext(paramName, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 420 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 421 */       throw new InvalidNameException(paramString);
/*     */     }
/* 423 */     return super.getSchema(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/* 428 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 429 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 431 */     return super.getSchema(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 437 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 438 */       throw new InvalidNameException(paramString);
/*     */     }
/* 440 */     return super.getSchemaClassDefinition(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/* 446 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 447 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 449 */     return super.getSchemaClassDefinition(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 458 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 459 */       return searchUsingURL(paramString);
/*     */     }
/* 461 */     return super.search(paramString, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 469 */     if (paramName.size() == 1)
/* 470 */       return search(paramName.get(0), paramAttributes); 
/* 471 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 472 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 474 */     return super.search(paramName, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 484 */     if (LdapURL.hasQueryComponents(paramString)) {
/* 485 */       return searchUsingURL(paramString);
/*     */     }
/* 487 */     return super.search(paramString, paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 497 */     if (paramName.size() == 1)
/* 498 */       return search(paramName.get(0), paramAttributes, paramArrayOfString); 
/* 499 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 500 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 502 */     return super.search(paramName, paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 512 */     if (LdapURL.hasQueryComponents(paramString1)) {
/* 513 */       return searchUsingURL(paramString1);
/*     */     }
/* 515 */     return super.search(paramString1, paramString2, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 525 */     if (paramName.size() == 1)
/* 526 */       return search(paramName.get(0), paramString, paramSearchControls); 
/* 527 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 528 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 530 */     return super.search(paramName, paramString, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 541 */     if (LdapURL.hasQueryComponents(paramString1)) {
/* 542 */       return searchUsingURL(paramString1);
/*     */     }
/* 544 */     return super.search(paramString1, paramString2, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 555 */     if (paramName.size() == 1)
/* 556 */       return search(paramName.get(0), paramString, paramArrayOfObject, paramSearchControls); 
/* 557 */     if (LdapURL.hasQueryComponents(paramName.get(0))) {
/* 558 */       throw new InvalidNameException(paramName.toString());
/*     */     }
/* 560 */     return super.search(paramName, paramString, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NamingEnumeration<SearchResult> searchUsingURL(String paramString) throws NamingException {
/* 569 */     LdapURL ldapURL = new LdapURL(paramString);
/*     */     
/* 571 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 572 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 574 */       return dirContext.search(resolveResult.getRemainingName(), 
/* 575 */           setFilterUsingURL(ldapURL), 
/* 576 */           setSearchControlsUsingURL(ldapURL));
/*     */     } finally {
/* 578 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String setFilterUsingURL(LdapURL paramLdapURL) {
/* 589 */     String str = paramLdapURL.getFilter();
/*     */     
/* 591 */     if (str == null) {
/* 592 */       str = "(objectClass=*)";
/*     */     }
/* 594 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SearchControls setSearchControlsUsingURL(LdapURL paramLdapURL) {
/* 604 */     SearchControls searchControls = new SearchControls();
/* 605 */     String str1 = paramLdapURL.getScope();
/* 606 */     String str2 = paramLdapURL.getAttributes();
/*     */     
/* 608 */     if (str1 == null) {
/* 609 */       searchControls.setSearchScope(0);
/*     */     }
/* 611 */     else if (str1.equals("sub")) {
/* 612 */       searchControls.setSearchScope(2);
/* 613 */     } else if (str1.equals("one")) {
/* 614 */       searchControls.setSearchScope(1);
/* 615 */     } else if (str1.equals("base")) {
/* 616 */       searchControls.setSearchScope(0);
/*     */     } 
/*     */ 
/*     */     
/* 620 */     if (str2 == null) {
/* 621 */       searchControls.setReturningAttributes(null);
/*     */     } else {
/* 623 */       StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
/* 624 */       int i = stringTokenizer.countTokens();
/* 625 */       String[] arrayOfString = new String[i];
/* 626 */       for (byte b = 0; b < i; b++) {
/* 627 */         arrayOfString[b] = stringTokenizer.nextToken();
/*     */       }
/* 629 */       searchControls.setReturningAttributes(arrayOfString);
/*     */     } 
/* 631 */     return searchControls;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/ldap/ldapURLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */