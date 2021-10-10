/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import com.sun.jndi.toolkit.ctx.ComponentDirContext;
/*     */ import com.sun.jndi.toolkit.ctx.Continuation;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.InvalidAttributeIdentifierException;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
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
/*     */ public class DnsContext
/*     */   extends ComponentDirContext
/*     */ {
/*     */   DnsName domain;
/*     */   Hashtable<Object, Object> environment;
/*     */   private boolean envShared;
/*     */   private boolean parentIsDns;
/*     */   private String[] servers;
/*     */   private Resolver resolver;
/*     */   private boolean authoritative;
/*     */   private boolean recursion;
/*     */   private int timeout;
/*     */   private int retries;
/*  63 */   static final NameParser nameParser = new DnsNameParser();
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_INIT_TIMEOUT = 1000;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_RETRIES = 4;
/*     */ 
/*     */   
/*     */   private static final String INIT_TIMEOUT = "com.sun.jndi.dns.timeout.initial";
/*     */ 
/*     */   
/*     */   private static final String RETRIES = "com.sun.jndi.dns.timeout.retries";
/*     */ 
/*     */   
/*     */   private CT lookupCT;
/*     */ 
/*     */   
/*     */   private static final String LOOKUP_ATTR = "com.sun.jndi.dns.lookup.attr";
/*     */ 
/*     */   
/*     */   private static final String RECURSION = "com.sun.jndi.dns.recursion";
/*     */   
/*     */   private static final int ANY = 255;
/*     */   
/*  88 */   private static final ZoneNode zoneTree = new ZoneNode(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean debug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DnsContext(String paramString, String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 102 */     this.domain = new DnsName(paramString.endsWith(".") ? paramString : (paramString + "."));
/*     */ 
/*     */     
/* 105 */     this.servers = (paramArrayOfString == null) ? null : (String[])paramArrayOfString.clone();
/* 106 */     this.environment = (Hashtable<Object, Object>)paramHashtable.clone();
/* 107 */     this.envShared = false;
/* 108 */     this.parentIsDns = false;
/* 109 */     this.resolver = null;
/*     */     
/* 111 */     initFromEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DnsContext(DnsContext paramDnsContext, DnsName paramDnsName) {
/* 119 */     this(paramDnsContext);
/* 120 */     this.domain = paramDnsName;
/* 121 */     this.parentIsDns = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DnsContext(DnsContext paramDnsContext) {
/* 132 */     this.environment = paramDnsContext.environment;
/* 133 */     paramDnsContext.envShared = true;
/* 134 */     this.parentIsDns = paramDnsContext.parentIsDns;
/* 135 */     this.domain = paramDnsContext.domain;
/* 136 */     this.servers = paramDnsContext.servers;
/* 137 */     this.resolver = paramDnsContext.resolver;
/* 138 */     this.authoritative = paramDnsContext.authoritative;
/* 139 */     this.recursion = paramDnsContext.recursion;
/* 140 */     this.timeout = paramDnsContext.timeout;
/* 141 */     this.retries = paramDnsContext.retries;
/* 142 */     this.lookupCT = paramDnsContext.lookupCT;
/*     */   }
/*     */   
/*     */   public void close() {
/* 146 */     if (this.resolver != null) {
/* 147 */       this.resolver.close();
/* 148 */       this.resolver = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable<?, ?> p_getEnvironment() {
/* 159 */     return this.environment;
/*     */   }
/*     */   
/*     */   public Hashtable<?, ?> getEnvironment() throws NamingException {
/* 163 */     return (Hashtable<?, ?>)this.environment.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 170 */     if (paramString.equals("com.sun.jndi.dns.lookup.attr")) {
/* 171 */       this.lookupCT = getLookupCT((String)paramObject);
/* 172 */     } else if (paramString.equals("java.naming.authoritative")) {
/* 173 */       this.authoritative = "true".equalsIgnoreCase((String)paramObject);
/* 174 */     } else if (paramString.equals("com.sun.jndi.dns.recursion")) {
/* 175 */       this.recursion = "true".equalsIgnoreCase((String)paramObject);
/* 176 */     } else if (paramString.equals("com.sun.jndi.dns.timeout.initial")) {
/* 177 */       int i = Integer.parseInt((String)paramObject);
/* 178 */       if (this.timeout != i) {
/* 179 */         this.timeout = i;
/* 180 */         this.resolver = null;
/*     */       } 
/* 182 */     } else if (paramString.equals("com.sun.jndi.dns.timeout.retries")) {
/* 183 */       int i = Integer.parseInt((String)paramObject);
/* 184 */       if (this.retries != i) {
/* 185 */         this.retries = i;
/* 186 */         this.resolver = null;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if (!this.envShared)
/* 191 */       return this.environment.put(paramString, paramObject); 
/* 192 */     if (this.environment.get(paramString) != paramObject) {
/*     */       
/* 194 */       this.environment = (Hashtable<Object, Object>)this.environment.clone();
/* 195 */       this.envShared = false;
/* 196 */       return this.environment.put(paramString, paramObject);
/*     */     } 
/* 198 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 206 */     if (paramString.equals("com.sun.jndi.dns.lookup.attr")) {
/* 207 */       this.lookupCT = getLookupCT(null);
/* 208 */     } else if (paramString.equals("java.naming.authoritative")) {
/* 209 */       this.authoritative = false;
/* 210 */     } else if (paramString.equals("com.sun.jndi.dns.recursion")) {
/* 211 */       this.recursion = true;
/* 212 */     } else if (paramString.equals("com.sun.jndi.dns.timeout.initial")) {
/* 213 */       if (this.timeout != 1000) {
/* 214 */         this.timeout = 1000;
/* 215 */         this.resolver = null;
/*     */       } 
/* 217 */     } else if (paramString.equals("com.sun.jndi.dns.timeout.retries") && 
/* 218 */       this.retries != 4) {
/* 219 */       this.retries = 4;
/* 220 */       this.resolver = null;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (!this.envShared)
/* 225 */       return this.environment.remove(paramString); 
/* 226 */     if (this.environment.get(paramString) != null) {
/*     */       
/* 228 */       this.environment = (Hashtable<Object, Object>)this.environment.clone();
/* 229 */       this.envShared = false;
/* 230 */       return this.environment.remove(paramString);
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setProviderUrl(String paramString) {
/* 242 */     this.environment.put("java.naming.provider.url", paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initFromEnvironment() throws InvalidAttributeIdentifierException {
/* 251 */     this.lookupCT = getLookupCT((String)this.environment.get("com.sun.jndi.dns.lookup.attr"));
/* 252 */     this.authoritative = "true".equalsIgnoreCase((String)this.environment
/* 253 */         .get("java.naming.authoritative"));
/* 254 */     String str = (String)this.environment.get("com.sun.jndi.dns.recursion");
/* 255 */     this
/* 256 */       .recursion = (str == null || "true".equalsIgnoreCase(str));
/* 257 */     str = (String)this.environment.get("com.sun.jndi.dns.timeout.initial");
/* 258 */     this
/*     */       
/* 260 */       .timeout = (str == null) ? 1000 : Integer.parseInt(str);
/* 261 */     str = (String)this.environment.get("com.sun.jndi.dns.timeout.retries");
/* 262 */     this
/*     */       
/* 264 */       .retries = (str == null) ? 4 : Integer.parseInt(str);
/*     */   }
/*     */ 
/*     */   
/*     */   private CT getLookupCT(String paramString) throws InvalidAttributeIdentifierException {
/* 269 */     return (paramString == null) ? new CT(1, 16) : 
/*     */       
/* 271 */       fromAttrId(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object c_lookup(Name paramName, Continuation paramContinuation) throws NamingException {
/* 280 */     paramContinuation.setSuccess();
/* 281 */     if (paramName.isEmpty()) {
/* 282 */       DnsContext dnsContext = new DnsContext(this);
/* 283 */       dnsContext.resolver = new Resolver(this.servers, this.timeout, this.retries);
/*     */       
/* 285 */       return dnsContext;
/*     */     } 
/*     */     try {
/* 288 */       DnsName dnsName = fullyQualify(paramName);
/*     */       
/* 290 */       ResourceRecords resourceRecords = getResolver().query(dnsName, this.lookupCT.rrclass, this.lookupCT.rrtype, this.recursion, this.authoritative);
/*     */       
/* 292 */       Attributes attributes = rrsToAttrs(resourceRecords, null);
/* 293 */       DnsContext dnsContext = new DnsContext(this, dnsName);
/* 294 */       return DirectoryManager.getObjectInstance(dnsContext, paramName, this, this.environment, attributes);
/*     */     }
/* 296 */     catch (NamingException namingException) {
/* 297 */       paramContinuation.setError(this, paramName);
/* 298 */       throw paramContinuation.fillInException(namingException);
/* 299 */     } catch (Exception exception) {
/* 300 */       paramContinuation.setError(this, paramName);
/* 301 */       NamingException namingException = new NamingException("Problem generating object using object factory");
/*     */       
/* 303 */       namingException.setRootCause(exception);
/* 304 */       throw paramContinuation.fillInException(namingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object c_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException {
/* 310 */     return c_lookup(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> c_list(Name paramName, Continuation paramContinuation) throws NamingException {
/* 315 */     paramContinuation.setSuccess();
/*     */     try {
/* 317 */       DnsName dnsName = fullyQualify(paramName);
/* 318 */       NameNode nameNode = getNameNode(dnsName);
/* 319 */       DnsContext dnsContext = new DnsContext(this, dnsName);
/* 320 */       return new NameClassPairEnumeration(dnsContext, nameNode.getChildren());
/*     */     }
/* 322 */     catch (NamingException namingException) {
/* 323 */       paramContinuation.setError(this, paramName);
/* 324 */       throw paramContinuation.fillInException(namingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> c_listBindings(Name paramName, Continuation paramContinuation) throws NamingException {
/* 330 */     paramContinuation.setSuccess();
/*     */     try {
/* 332 */       DnsName dnsName = fullyQualify(paramName);
/* 333 */       NameNode nameNode = getNameNode(dnsName);
/* 334 */       DnsContext dnsContext = new DnsContext(this, dnsName);
/* 335 */       return new BindingEnumeration(dnsContext, nameNode.getChildren());
/*     */     }
/* 337 */     catch (NamingException namingException) {
/* 338 */       paramContinuation.setError(this, paramName);
/* 339 */       throw paramContinuation.fillInException(namingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void c_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 345 */     paramContinuation.setError(this, paramName);
/* 346 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 352 */     paramContinuation.setError(this, paramName);
/* 353 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_unbind(Name paramName, Continuation paramContinuation) throws NamingException {
/* 359 */     paramContinuation.setError(this, paramName);
/* 360 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/* 366 */     paramContinuation.setError(this, paramName1);
/* 367 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Context c_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 373 */     paramContinuation.setError(this, paramName);
/* 374 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 380 */     paramContinuation.setError(this, paramName);
/* 381 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NameParser c_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException {
/* 387 */     paramContinuation.setSuccess();
/* 388 */     return nameParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 399 */     paramContinuation.setError(this, paramName);
/* 400 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 409 */     paramContinuation.setError(this, paramName);
/* 410 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext c_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 418 */     paramContinuation.setError(this, paramName);
/* 419 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes c_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 428 */     paramContinuation.setSuccess();
/*     */     try {
/* 430 */       DnsName dnsName = fullyQualify(paramName);
/* 431 */       CT[] arrayOfCT = attrIdsToClassesAndTypes(paramArrayOfString);
/* 432 */       CT cT = getClassAndTypeToQuery(arrayOfCT);
/*     */       
/* 434 */       ResourceRecords resourceRecords = getResolver().query(dnsName, cT.rrclass, cT.rrtype, this.recursion, this.authoritative);
/*     */       
/* 436 */       return rrsToAttrs(resourceRecords, arrayOfCT);
/*     */     }
/* 438 */     catch (NamingException namingException) {
/* 439 */       paramContinuation.setError(this, paramName);
/* 440 */       throw paramContinuation.fillInException(namingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 449 */     paramContinuation.setError(this, paramName);
/* 450 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 458 */     paramContinuation.setError(this, paramName);
/* 459 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> c_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 468 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 476 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 485 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext c_getSchema(Name paramName, Continuation paramContinuation) throws NamingException {
/* 490 */     paramContinuation.setError(this, paramName);
/* 491 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext c_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException {
/* 497 */     paramContinuation.setError(this, paramName);
/* 498 */     throw paramContinuation.fillInException(new OperationNotSupportedException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNameInNamespace() {
/* 506 */     return this.domain.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 514 */     if (!(paramName2 instanceof DnsName) && !(paramName2 instanceof CompositeName)) {
/* 515 */       paramName2 = (new DnsName()).addAll(paramName2);
/*     */     }
/* 517 */     if (!(paramName1 instanceof DnsName) && !(paramName1 instanceof CompositeName)) {
/* 518 */       paramName1 = (new DnsName()).addAll(paramName1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     if (paramName2 instanceof DnsName && paramName1 instanceof DnsName) {
/* 525 */       DnsName dnsName = (DnsName)paramName2.clone();
/* 526 */       dnsName.addAll(paramName1);
/* 527 */       return (new CompositeName()).add(dnsName.toString());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 533 */     Name name2 = (paramName2 instanceof CompositeName) ? paramName2 : (new CompositeName()).add(paramName2.toString());
/*     */ 
/*     */     
/* 536 */     Name name3 = (paramName1 instanceof CompositeName) ? paramName1 : (new CompositeName()).add(paramName1.toString());
/* 537 */     int i = name2.size() - 1;
/*     */ 
/*     */     
/* 540 */     if (name3.isEmpty() || name3.get(0).equals("") || name2
/* 541 */       .isEmpty() || name2.get(i).equals("")) {
/* 542 */       return super.composeName(name3, name2);
/*     */     }
/*     */ 
/*     */     
/* 546 */     Name name1 = (paramName2 == name2) ? (CompositeName)name2.clone() : name2;
/*     */     
/* 548 */     name1.addAll(name3);
/*     */     
/* 550 */     if (this.parentIsDns) {
/*     */ 
/*     */       
/* 553 */       DnsName dnsName = (paramName2 instanceof DnsName) ? (DnsName)paramName2.clone() : new DnsName(name2.get(i));
/* 554 */       dnsName.addAll((paramName1 instanceof DnsName) ? paramName1 : new DnsName(name3
/*     */             
/* 556 */             .get(0)));
/* 557 */       name1.remove(i + 1);
/* 558 */       name1.remove(i);
/* 559 */       name1.add(i, dnsName.toString());
/*     */     } 
/* 561 */     return name1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Resolver getResolver() throws NamingException {
/* 572 */     if (this.resolver == null) {
/* 573 */       this.resolver = new Resolver(this.servers, this.timeout, this.retries);
/*     */     }
/* 575 */     return this.resolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DnsName fullyQualify(Name paramName) throws NamingException {
/* 584 */     if (paramName.isEmpty()) {
/* 585 */       return this.domain;
/*     */     }
/*     */ 
/*     */     
/* 589 */     DnsName dnsName = (paramName instanceof CompositeName) ? new DnsName(paramName.get(0)) : (DnsName)(new DnsName()).addAll(paramName);
/*     */     
/* 591 */     if (dnsName.hasRootLabel()) {
/*     */       
/* 593 */       if (this.domain.size() == 1) {
/* 594 */         return dnsName;
/*     */       }
/* 596 */       throw new InvalidNameException("DNS name " + dnsName + " not relative to " + this.domain);
/*     */     } 
/*     */ 
/*     */     
/* 600 */     return (DnsName)dnsName.addAll(0, this.domain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Attributes rrsToAttrs(ResourceRecords paramResourceRecords, CT[] paramArrayOfCT) {
/* 611 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/*     */     
/* 613 */     for (byte b = 0; b < paramResourceRecords.answer.size(); b++) {
/* 614 */       ResourceRecord resourceRecord = paramResourceRecords.answer.elementAt(b);
/* 615 */       int i = resourceRecord.getType();
/* 616 */       int j = resourceRecord.getRrclass();
/*     */       
/* 618 */       if (classAndTypeMatch(j, i, paramArrayOfCT)) {
/*     */ 
/*     */ 
/*     */         
/* 622 */         String str = toAttrId(j, i);
/* 623 */         Attribute attribute = basicAttributes.get(str);
/* 624 */         if (attribute == null) {
/* 625 */           attribute = new BasicAttribute(str);
/* 626 */           basicAttributes.put(attribute);
/*     */         } 
/* 628 */         attribute.add(resourceRecord.getRdata());
/*     */       } 
/* 630 */     }  return basicAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean classAndTypeMatch(int paramInt1, int paramInt2, CT[] paramArrayOfCT) {
/* 641 */     if (paramArrayOfCT == null) {
/* 642 */       return true;
/*     */     }
/* 644 */     for (byte b = 0; b < paramArrayOfCT.length; b++) {
/* 645 */       CT cT = paramArrayOfCT[b];
/* 646 */       boolean bool1 = (cT.rrclass == 255 || cT.rrclass == paramInt1) ? true : false;
/*     */       
/* 648 */       boolean bool2 = (cT.rrtype == 255 || cT.rrtype == paramInt2) ? true : false;
/*     */       
/* 650 */       if (bool1 && bool2) {
/* 651 */         return true;
/*     */       }
/*     */     } 
/* 654 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toAttrId(int paramInt1, int paramInt2) {
/* 672 */     String str = ResourceRecord.getTypeName(paramInt2);
/* 673 */     if (paramInt1 != 1) {
/* 674 */       str = ResourceRecord.getRrclassName(paramInt1) + " " + str;
/*     */     }
/* 676 */     return str;
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
/*     */   private static CT fromAttrId(String paramString) throws InvalidAttributeIdentifierException {
/*     */     int i;
/* 690 */     if (paramString.equals("")) {
/* 691 */       throw new InvalidAttributeIdentifierException("Attribute ID cannot be empty");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 696 */     int k = paramString.indexOf(' ');
/*     */ 
/*     */     
/* 699 */     if (k < 0) {
/* 700 */       i = 1;
/*     */     } else {
/* 702 */       String str1 = paramString.substring(0, k);
/* 703 */       i = ResourceRecord.getRrclass(str1);
/* 704 */       if (i < 0) {
/* 705 */         throw new InvalidAttributeIdentifierException("Unknown resource record class '" + str1 + '\'');
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 711 */     String str = paramString.substring(k + 1);
/* 712 */     int j = ResourceRecord.getType(str);
/* 713 */     if (j < 0) {
/* 714 */       throw new InvalidAttributeIdentifierException("Unknown resource record type '" + str + '\'');
/*     */     }
/*     */ 
/*     */     
/* 718 */     return new CT(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CT[] attrIdsToClassesAndTypes(String[] paramArrayOfString) throws InvalidAttributeIdentifierException {
/* 729 */     if (paramArrayOfString == null) {
/* 730 */       return null;
/*     */     }
/* 732 */     CT[] arrayOfCT = new CT[paramArrayOfString.length];
/*     */     
/* 734 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 735 */       arrayOfCT[b] = fromAttrId(paramArrayOfString[b]);
/*     */     }
/* 737 */     return arrayOfCT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CT getClassAndTypeToQuery(CT[] paramArrayOfCT) {
/*     */     int i;
/*     */     int j;
/* 749 */     if (paramArrayOfCT == null) {
/*     */       
/* 751 */       i = 255;
/* 752 */       j = 255;
/* 753 */     } else if (paramArrayOfCT.length == 0) {
/*     */       
/* 755 */       i = 1;
/* 756 */       j = 255;
/*     */     } else {
/* 758 */       i = (paramArrayOfCT[0]).rrclass;
/* 759 */       j = (paramArrayOfCT[0]).rrtype;
/* 760 */       for (byte b = 1; b < paramArrayOfCT.length; b++) {
/* 761 */         if (i != (paramArrayOfCT[b]).rrclass) {
/* 762 */           i = 255;
/*     */         }
/* 764 */         if (j != (paramArrayOfCT[b]).rrtype) {
/* 765 */           j = 255;
/*     */         }
/*     */       } 
/*     */     } 
/* 769 */     return new CT(i, j);
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
/*     */   private NameNode getNameNode(DnsName paramDnsName) throws NamingException {
/*     */     ZoneNode zoneNode;
/*     */     NameNode nameNode1;
/* 800 */     dprint("getNameNode(" + paramDnsName + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 805 */     synchronized (zoneTree) {
/* 806 */       zoneNode = zoneTree.getDeepestPopulated(paramDnsName);
/*     */     } 
/* 808 */     dprint("Deepest related zone in zone tree: " + ((zoneNode != null) ? zoneNode
/* 809 */         .getLabel() : "[none]"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 814 */     if (zoneNode != null) {
/* 815 */       synchronized (zoneNode) {
/* 816 */         nameNode1 = zoneNode.getContents();
/*     */       } 
/*     */ 
/*     */       
/* 820 */       if (nameNode1 != null) {
/* 821 */         NameNode nameNode = nameNode1.get(paramDnsName, zoneNode.depth() + 1);
/*     */         
/* 823 */         if (nameNode != null && !nameNode.isZoneCut()) {
/* 824 */           dprint("Found node " + paramDnsName + " in zone tree");
/*     */           
/* 826 */           DnsName dnsName1 = (DnsName)paramDnsName.getPrefix(zoneNode.depth() + 1);
/* 827 */           boolean bool = isZoneCurrent(zoneNode, dnsName1);
/* 828 */           boolean bool1 = false;
/*     */           
/* 830 */           synchronized (zoneNode) {
/* 831 */             if (nameNode1 != zoneNode.getContents()) {
/*     */ 
/*     */               
/* 834 */               bool1 = true;
/* 835 */             } else if (!bool) {
/* 836 */               zoneNode.depopulate();
/*     */             } else {
/* 838 */               return nameNode;
/*     */             } 
/*     */           } 
/* 841 */           dprint("Zone not current; discarding node");
/* 842 */           if (bool1) {
/* 843 */             return getNameNode(paramDnsName);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 850 */     dprint("Adding node " + paramDnsName + " to zone tree");
/*     */ 
/*     */     
/* 853 */     DnsName dnsName = getResolver().findZoneName(paramDnsName, 1, this.recursion);
/*     */     
/* 855 */     dprint("Node's zone is " + dnsName);
/* 856 */     synchronized (zoneTree) {
/* 857 */       zoneNode = (ZoneNode)zoneTree.add(dnsName, 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 864 */     synchronized (zoneNode) {
/*     */ 
/*     */       
/* 867 */       nameNode1 = zoneNode.isPopulated() ? zoneNode.getContents() : populateZone(zoneNode, dnsName);
/*     */     } 
/*     */     
/* 870 */     NameNode nameNode2 = nameNode1.get(paramDnsName, dnsName.size());
/* 871 */     if (nameNode2 == null) {
/* 872 */       throw new ConfigurationException("DNS error: node not found in its own zone");
/*     */     }
/*     */     
/* 875 */     dprint("Found node in newly-populated zone");
/* 876 */     return nameNode2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NameNode populateZone(ZoneNode paramZoneNode, DnsName paramDnsName) throws NamingException {
/* 885 */     dprint("Populating zone " + paramDnsName);
/*     */ 
/*     */     
/* 888 */     ResourceRecords resourceRecords = getResolver().queryZone(paramDnsName, 1, this.recursion);
/*     */     
/* 890 */     dprint("zone xfer complete: " + resourceRecords.answer.size() + " records");
/* 891 */     return paramZoneNode.populate(paramDnsName, resourceRecords);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isZoneCurrent(ZoneNode paramZoneNode, DnsName paramDnsName) throws NamingException {
/* 910 */     if (!paramZoneNode.isPopulated()) {
/* 911 */       return false;
/*     */     }
/*     */     
/* 914 */     ResourceRecord resourceRecord = getResolver().findSoa(paramDnsName, 1, this.recursion);
/*     */     
/* 916 */     synchronized (paramZoneNode) {
/* 917 */       if (resourceRecord == null) {
/* 918 */         paramZoneNode.depopulate();
/*     */       }
/* 920 */       return (paramZoneNode.isPopulated() && paramZoneNode
/* 921 */         .compareSerialNumberTo(resourceRecord) >= 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final void dprint(String paramString) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */