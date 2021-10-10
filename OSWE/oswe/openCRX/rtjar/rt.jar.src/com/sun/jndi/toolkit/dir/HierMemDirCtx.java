/*     */ package com.sun.jndi.toolkit.dir;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameAlreadyBoundException;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.AttributeModificationException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SchemaViolationException;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.spi.DirStateFactory;
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
/*     */ public class HierMemDirCtx
/*     */   implements DirContext
/*     */ {
/*     */   private static final boolean debug = false;
/*  43 */   private static final NameParser defaultParser = new HierarchicalNameParser();
/*     */   
/*     */   protected Hashtable<String, Object> myEnv;
/*     */   protected Hashtable<Name, Object> bindings;
/*     */   protected Attributes attrs;
/*     */   protected boolean ignoreCase = false;
/*  49 */   protected NamingException readOnlyEx = null;
/*  50 */   protected NameParser myParser = defaultParser;
/*     */   
/*     */   private boolean alwaysUseFactory;
/*     */   
/*     */   public void close() throws NamingException {
/*  55 */     this.myEnv = null;
/*  56 */     this.bindings = null;
/*  57 */     this.attrs = null;
/*     */   }
/*     */   
/*     */   public String getNameInNamespace() throws NamingException {
/*  61 */     throw new OperationNotSupportedException("Cannot determine full name");
/*     */   }
/*     */ 
/*     */   
/*     */   public HierMemDirCtx() {
/*  66 */     this(null, false, false);
/*     */   }
/*     */   
/*     */   public HierMemDirCtx(boolean paramBoolean) {
/*  70 */     this(null, paramBoolean, false);
/*     */   }
/*     */   
/*     */   public HierMemDirCtx(Hashtable<String, Object> paramHashtable, boolean paramBoolean) {
/*  74 */     this(paramHashtable, paramBoolean, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HierMemDirCtx(Hashtable<String, Object> paramHashtable, boolean paramBoolean1, boolean paramBoolean2) {
/*  79 */     this.myEnv = paramHashtable;
/*  80 */     this.ignoreCase = paramBoolean1;
/*  81 */     init();
/*  82 */     this.alwaysUseFactory = paramBoolean2;
/*     */   }
/*     */   
/*     */   private void init() {
/*  86 */     this.attrs = new BasicAttributes(this.ignoreCase);
/*  87 */     this.bindings = new Hashtable<>(11, 0.75F);
/*     */   }
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/*  91 */     return lookup(this.myParser.parse(paramString));
/*     */   }
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/*  95 */     return doLookup(paramName, this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object doLookup(Name paramName, boolean paramBoolean) throws NamingException {
/*     */     Object object;
/* 101 */     HierMemDirCtx hierMemDirCtx2, hierMemDirCtx1 = null;
/* 102 */     paramName = canonizeName(paramName);
/*     */     
/* 104 */     switch (paramName.size()) {
/*     */       
/*     */       case 0:
/* 107 */         hierMemDirCtx1 = this;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 112 */         hierMemDirCtx1 = (HierMemDirCtx)this.bindings.get(paramName);
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 117 */         hierMemDirCtx2 = (HierMemDirCtx)this.bindings.get(paramName.getPrefix(1));
/* 118 */         if (hierMemDirCtx2 == null) {
/* 119 */           hierMemDirCtx1 = null; break;
/*     */         } 
/* 121 */         object = hierMemDirCtx2.doLookup(paramName.getSuffix(1), false);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (object == null) {
/* 127 */       throw new NameNotFoundException(paramName.toString());
/*     */     }
/*     */     
/* 130 */     if (paramBoolean) {
/*     */       try {
/* 132 */         return DirectoryManager.getObjectInstance(object, paramName, this, this.myEnv, (object instanceof HierMemDirCtx) ? ((HierMemDirCtx)object).attrs : null);
/*     */ 
/*     */       
/*     */       }
/* 136 */       catch (NamingException namingException) {
/* 137 */         throw namingException;
/* 138 */       } catch (Exception exception) {
/* 139 */         NamingException namingException = new NamingException("Problem calling getObjectInstance");
/*     */         
/* 141 */         namingException.setRootCause(exception);
/* 142 */         throw namingException;
/*     */       } 
/*     */     }
/* 145 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 150 */     bind(this.myParser.parse(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 154 */     doBind(paramName, paramObject, null, this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 159 */     bind(this.myParser.parse(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 164 */     doBind(paramName, paramObject, paramAttributes, this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(Name paramName, Object paramObject, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/* 169 */     if (paramName.isEmpty()) {
/* 170 */       throw new InvalidNameException("Cannot bind empty name");
/*     */     }
/*     */     
/* 173 */     if (paramBoolean) {
/* 174 */       DirStateFactory.Result result = DirectoryManager.getStateToBind(paramObject, paramName, this, this.myEnv, paramAttributes);
/*     */       
/* 176 */       paramObject = result.getObject();
/* 177 */       paramAttributes = result.getAttributes();
/*     */     } 
/*     */     
/* 180 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName), false);
/* 181 */     hierMemDirCtx.doBindAux(getLeafName(paramName), paramObject);
/*     */     
/* 183 */     if (paramAttributes != null && paramAttributes.size() > 0) {
/* 184 */       modifyAttributes(paramName, 1, paramAttributes);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doBindAux(Name paramName, Object paramObject) throws NamingException {
/* 189 */     if (this.readOnlyEx != null) {
/* 190 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/*     */     
/* 193 */     if (this.bindings.get(paramName) != null) {
/* 194 */       throw new NameAlreadyBoundException(paramName.toString());
/*     */     }
/* 196 */     if (paramObject instanceof HierMemDirCtx) {
/* 197 */       this.bindings.put(paramName, paramObject);
/*     */     } else {
/* 199 */       throw new SchemaViolationException("This context only supports binding objects of it's own kind");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 205 */     rebind(this.myParser.parse(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 209 */     doRebind(paramName, paramObject, null, this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 214 */     rebind(this.myParser.parse(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 219 */     doRebind(paramName, paramObject, paramAttributes, this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRebind(Name paramName, Object paramObject, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/* 224 */     if (paramName.isEmpty()) {
/* 225 */       throw new InvalidNameException("Cannot rebind empty name");
/*     */     }
/*     */     
/* 228 */     if (paramBoolean) {
/* 229 */       DirStateFactory.Result result = DirectoryManager.getStateToBind(paramObject, paramName, this, this.myEnv, paramAttributes);
/*     */       
/* 231 */       paramObject = result.getObject();
/* 232 */       paramAttributes = result.getAttributes();
/*     */     } 
/*     */     
/* 235 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName), false);
/* 236 */     hierMemDirCtx.doRebindAux(getLeafName(paramName), paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (paramAttributes != null && paramAttributes.size() > 0) {
/* 247 */       modifyAttributes(paramName, 1, paramAttributes);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doRebindAux(Name paramName, Object paramObject) throws NamingException {
/* 252 */     if (this.readOnlyEx != null) {
/* 253 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/* 255 */     if (paramObject instanceof HierMemDirCtx) {
/* 256 */       this.bindings.put(paramName, paramObject);
/*     */     } else {
/*     */       
/* 259 */       throw new SchemaViolationException("This context only supports binding objects of it's own kind");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 265 */     unbind(this.myParser.parse(paramString));
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 269 */     if (paramName.isEmpty()) {
/* 270 */       throw new InvalidNameException("Cannot unbind empty name");
/*     */     }
/*     */     
/* 273 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName), false);
/* 274 */     hierMemDirCtx.doUnbind(getLeafName(paramName));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doUnbind(Name paramName) throws NamingException {
/* 279 */     if (this.readOnlyEx != null) {
/* 280 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/*     */     
/* 283 */     this.bindings.remove(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 288 */     rename(this.myParser.parse(paramString1), this.myParser.parse(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 294 */     if (paramName2.isEmpty() || paramName1.isEmpty()) {
/* 295 */       throw new InvalidNameException("Cannot rename empty name");
/*     */     }
/*     */     
/* 298 */     if (!getInternalName(paramName2).equals(getInternalName(paramName1))) {
/* 299 */       throw new InvalidNameException("Cannot rename across contexts");
/*     */     }
/*     */ 
/*     */     
/* 303 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName2), false);
/* 304 */     hierMemDirCtx.doRename(getLeafName(paramName1), getLeafName(paramName2));
/*     */   }
/*     */   
/*     */   protected void doRename(Name paramName1, Name paramName2) throws NamingException {
/* 308 */     if (this.readOnlyEx != null) {
/* 309 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/*     */     
/* 312 */     paramName1 = canonizeName(paramName1);
/* 313 */     paramName2 = canonizeName(paramName2);
/*     */ 
/*     */     
/* 316 */     if (this.bindings.get(paramName2) != null) {
/* 317 */       throw new NameAlreadyBoundException(paramName2.toString());
/*     */     }
/*     */ 
/*     */     
/* 321 */     Object object = this.bindings.remove(paramName1);
/* 322 */     if (object == null) {
/* 323 */       throw new NameNotFoundException(paramName1.toString());
/*     */     }
/*     */     
/* 326 */     this.bindings.put(paramName2, object);
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 330 */     return list(this.myParser.parse(paramString));
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 334 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/* 335 */     return hierMemDirCtx.doList();
/*     */   }
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> doList() throws NamingException {
/* 339 */     return new FlatNames(this.bindings.keys());
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 344 */     return listBindings(this.myParser.parse(paramString));
/*     */   }
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 348 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/* 349 */     return hierMemDirCtx.doListBindings(this.alwaysUseFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> doListBindings(boolean paramBoolean) throws NamingException {
/* 354 */     return new FlatBindings(this.bindings, this.myEnv, paramBoolean);
/*     */   }
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 358 */     destroySubcontext(this.myParser.parse(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 363 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName), false);
/* 364 */     hierMemDirCtx.doDestroySubcontext(getLeafName(paramName));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDestroySubcontext(Name paramName) throws NamingException {
/* 369 */     if (this.readOnlyEx != null) {
/* 370 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/* 372 */     paramName = canonizeName(paramName);
/* 373 */     this.bindings.remove(paramName);
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 377 */     return createSubcontext(this.myParser.parse(paramString));
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 381 */     return createSubcontext(paramName, (Attributes)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 386 */     return createSubcontext(this.myParser.parse(paramString), paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 392 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(getInternalName(paramName), false);
/* 393 */     return hierMemDirCtx.doCreateSubcontext(getLeafName(paramName), paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext doCreateSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 398 */     if (this.readOnlyEx != null) {
/* 399 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/*     */     
/* 402 */     paramName = canonizeName(paramName);
/*     */     
/* 404 */     if (this.bindings.get(paramName) != null) {
/* 405 */       throw new NameAlreadyBoundException(paramName.toString());
/*     */     }
/* 407 */     HierMemDirCtx hierMemDirCtx = createNewCtx();
/* 408 */     this.bindings.put(paramName, hierMemDirCtx);
/* 409 */     if (paramAttributes != null) {
/* 410 */       hierMemDirCtx.modifyAttributes("", 1, paramAttributes);
/*     */     }
/* 412 */     return hierMemDirCtx;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 418 */     return lookupLink(this.myParser.parse(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 423 */     return lookup(paramName);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 427 */     return this.myParser;
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 431 */     return this.myParser;
/*     */   }
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 436 */     Name name = composeName(new CompositeName(paramString1), new CompositeName(paramString2));
/*     */     
/* 438 */     return name.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 443 */     paramName1 = canonizeName(paramName1);
/* 444 */     paramName2 = canonizeName(paramName2);
/* 445 */     Name name = (Name)paramName2.clone();
/* 446 */     name.addAll(paramName1);
/* 447 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 453 */     this
/*     */       
/* 455 */       .myEnv = (this.myEnv == null) ? new Hashtable<>(11, 0.75F) : (Hashtable<String, Object>)this.myEnv.clone();
/*     */     
/* 457 */     return this.myEnv.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 463 */     if (this.myEnv == null) {
/* 464 */       return null;
/*     */     }
/* 466 */     this.myEnv = (Hashtable<String, Object>)this.myEnv.clone();
/* 467 */     return this.myEnv.remove(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Hashtable<String, Object> getEnvironment() throws NamingException {
/* 472 */     if (this.myEnv == null) {
/* 473 */       return new Hashtable<>(5, 0.75F);
/*     */     }
/* 475 */     return (Hashtable<String, Object>)this.myEnv.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/* 481 */     return getAttributes(this.myParser.parse(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/* 486 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/* 487 */     return hierMemDirCtx.doGetAttributes();
/*     */   }
/*     */   
/*     */   protected Attributes doGetAttributes() throws NamingException {
/* 491 */     return (Attributes)this.attrs.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 496 */     return getAttributes(this.myParser.parse(paramString), paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/* 501 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/* 502 */     return hierMemDirCtx.doGetAttributes(paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attributes doGetAttributes(String[] paramArrayOfString) throws NamingException {
/* 508 */     if (paramArrayOfString == null) {
/* 509 */       return doGetAttributes();
/*     */     }
/* 511 */     BasicAttributes basicAttributes = new BasicAttributes(this.ignoreCase);
/* 512 */     Attribute attribute = null;
/* 513 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 514 */       attribute = this.attrs.get(paramArrayOfString[b]);
/* 515 */       if (attribute != null) {
/* 516 */         basicAttributes.put(attribute);
/*     */       }
/*     */     } 
/* 519 */     return basicAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 524 */     modifyAttributes(this.myParser.parse(paramString), paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 530 */     if (paramAttributes == null || paramAttributes.size() == 0) {
/* 531 */       throw new IllegalArgumentException("Cannot modify without an attribute");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 536 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 537 */     ModificationItem[] arrayOfModificationItem = new ModificationItem[paramAttributes.size()];
/* 538 */     for (byte b = 0; b < arrayOfModificationItem.length && namingEnumeration.hasMoreElements(); b++) {
/* 539 */       arrayOfModificationItem[b] = new ModificationItem(paramInt, namingEnumeration.next());
/*     */     }
/*     */     
/* 542 */     modifyAttributes(paramName, arrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 547 */     modifyAttributes(this.myParser.parse(paramString), paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 552 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/* 553 */     hierMemDirCtx.doModifyAttributes(paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doModifyAttributes(ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 559 */     if (this.readOnlyEx != null) {
/* 560 */       throw (NamingException)this.readOnlyEx.fillInStackTrace();
/*     */     }
/*     */     
/* 563 */     applyMods(paramArrayOfModificationItem, this.attrs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Attributes applyMods(ModificationItem[] paramArrayOfModificationItem, Attributes paramAttributes) throws NamingException {
/* 573 */     for (byte b = 0; b < paramArrayOfModificationItem.length; b++) {
/* 574 */       Attribute attribute1; NamingEnumeration<?> namingEnumeration; ModificationItem modificationItem = paramArrayOfModificationItem[b];
/* 575 */       Attribute attribute2 = modificationItem.getAttribute();
/*     */       
/* 577 */       switch (modificationItem.getModificationOp()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 583 */           attribute1 = paramAttributes.get(attribute2.getID());
/* 584 */           if (attribute1 == null) {
/* 585 */             paramAttributes.put((Attribute)attribute2.clone());
/*     */             break;
/*     */           } 
/* 588 */           namingEnumeration = attribute2.getAll();
/* 589 */           while (namingEnumeration.hasMore()) {
/* 590 */             attribute1.add(namingEnumeration.next());
/*     */           }
/*     */           break;
/*     */         
/*     */         case 2:
/* 595 */           if (attribute2.size() == 0) {
/* 596 */             paramAttributes.remove(attribute2.getID()); break;
/*     */           } 
/* 598 */           paramAttributes.put((Attribute)attribute2.clone());
/*     */           break;
/*     */         
/*     */         case 3:
/* 602 */           attribute1 = paramAttributes.get(attribute2.getID());
/* 603 */           if (attribute1 != null) {
/* 604 */             if (attribute2.size() == 0) {
/* 605 */               paramAttributes.remove(attribute2.getID());
/*     */               break;
/*     */             } 
/* 608 */             namingEnumeration = attribute2.getAll();
/* 609 */             while (namingEnumeration.hasMore()) {
/* 610 */               attribute1.remove(namingEnumeration.next());
/*     */             }
/* 612 */             if (attribute1.size() == 0) {
/* 613 */               paramAttributes.remove(attribute2.getID());
/*     */             }
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 619 */           throw new AttributeModificationException("Unknown mod_op");
/*     */       } 
/*     */     
/*     */     } 
/* 623 */     return paramAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 629 */     return search(paramString, paramAttributes, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 635 */     return search(paramName, paramAttributes, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 642 */     return search(this.myParser.parse(paramString), paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 651 */     HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)doLookup(paramName, false);
/*     */     
/* 653 */     SearchControls searchControls = new SearchControls();
/* 654 */     searchControls.setReturningAttributes(paramArrayOfString);
/*     */     
/* 656 */     return new LazySearchEnumerationImpl(hierMemDirCtx
/* 657 */         .doListBindings(false), new ContainmentFilter(paramAttributes), searchControls, this, this.myEnv, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 667 */     DirContext dirContext = (DirContext)doLookup(paramName, false);
/*     */     
/* 669 */     SearchFilter searchFilter = new SearchFilter(paramString);
/* 670 */     return new LazySearchEnumerationImpl(new HierContextEnumerator(dirContext, (paramSearchControls != null) ? paramSearchControls
/*     */           
/* 672 */           .getSearchScope() : 1), searchFilter, paramSearchControls, this, this.myEnv, this.alwaysUseFactory);
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
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 684 */     String str = SearchFilter.format(paramString, paramArrayOfObject);
/* 685 */     return search(paramName, str, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 692 */     return search(this.myParser.parse(paramString1), paramString2, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 700 */     return search(this.myParser.parse(paramString1), paramString2, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HierMemDirCtx createNewCtx() throws NamingException {
/* 707 */     return new HierMemDirCtx(this.myEnv, this.ignoreCase);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Name canonizeName(Name paramName) throws NamingException {
/* 713 */     Name name = paramName;
/*     */     
/* 715 */     if (!(paramName instanceof HierarchicalName)) {
/*     */       
/* 717 */       name = new HierarchicalName();
/* 718 */       int i = paramName.size();
/* 719 */       for (byte b = 0; b < i; b++) {
/* 720 */         name.add(b, paramName.get(b));
/*     */       }
/*     */     } 
/*     */     
/* 724 */     return name;
/*     */   }
/*     */   
/*     */   protected Name getInternalName(Name paramName) throws NamingException {
/* 728 */     return paramName.getPrefix(paramName.size() - 1);
/*     */   }
/*     */   
/*     */   protected Name getLeafName(Name paramName) throws NamingException {
/* 732 */     return paramName.getSuffix(paramName.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 737 */     throw new OperationNotSupportedException();
/*     */   }
/*     */   
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/* 741 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 746 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/* 751 */     throw new OperationNotSupportedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReadOnly(NamingException paramNamingException) {
/* 756 */     this.readOnlyEx = paramNamingException;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIgnoreCase(boolean paramBoolean) {
/* 761 */     this.ignoreCase = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setNameParser(NameParser paramNameParser) {
/* 765 */     this.myParser = paramNameParser;
/*     */   }
/*     */ 
/*     */   
/*     */   private abstract class BaseFlatNames<T>
/*     */     implements NamingEnumeration<T>
/*     */   {
/*     */     Enumeration<Name> names;
/*     */     
/*     */     BaseFlatNames(Enumeration<Name> param1Enumeration) {
/* 775 */       this.names = param1Enumeration;
/*     */     }
/*     */     
/*     */     public final boolean hasMoreElements() {
/*     */       try {
/* 780 */         return hasMore();
/* 781 */       } catch (NamingException namingException) {
/* 782 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public final boolean hasMore() throws NamingException {
/* 787 */       return this.names.hasMoreElements();
/*     */     }
/*     */     
/*     */     public final T nextElement() {
/*     */       try {
/* 792 */         return next();
/* 793 */       } catch (NamingException namingException) {
/* 794 */         throw new NoSuchElementException(namingException.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     public abstract T next() throws NamingException;
/*     */     
/*     */     public final void close() {
/* 801 */       this.names = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class FlatNames
/*     */     extends BaseFlatNames<NameClassPair> {
/*     */     FlatNames(Enumeration<Name> param1Enumeration) {
/* 808 */       super(param1Enumeration);
/*     */     }
/*     */ 
/*     */     
/*     */     public NameClassPair next() throws NamingException {
/* 813 */       Name name = this.names.nextElement();
/* 814 */       String str = HierMemDirCtx.this.bindings.get(name).getClass().getName();
/* 815 */       return new NameClassPair(name.toString(), str);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class FlatBindings
/*     */     extends BaseFlatNames<Binding>
/*     */   {
/*     */     private Hashtable<Name, Object> bds;
/*     */     private Hashtable<String, Object> env;
/*     */     private boolean useFactory;
/*     */     
/*     */     FlatBindings(Hashtable<Name, Object> param1Hashtable, Hashtable<String, Object> param1Hashtable1, boolean param1Boolean) {
/* 828 */       super(param1Hashtable.keys());
/* 829 */       this.env = param1Hashtable1;
/* 830 */       this.bds = param1Hashtable;
/* 831 */       this.useFactory = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public Binding next() throws NamingException {
/* 836 */       Name name = this.names.nextElement();
/*     */       
/* 838 */       HierMemDirCtx hierMemDirCtx = (HierMemDirCtx)this.bds.get(name);
/*     */       
/* 840 */       Object object = hierMemDirCtx;
/* 841 */       if (this.useFactory) {
/* 842 */         Attributes attributes = hierMemDirCtx.getAttributes("");
/*     */         try {
/* 844 */           object = DirectoryManager.getObjectInstance(hierMemDirCtx, name, HierMemDirCtx.this, this.env, attributes);
/*     */         }
/* 846 */         catch (NamingException namingException) {
/* 847 */           throw namingException;
/* 848 */         } catch (Exception exception) {
/* 849 */           NamingException namingException = new NamingException("Problem calling getObjectInstance");
/*     */           
/* 851 */           namingException.setRootCause(exception);
/* 852 */           throw namingException;
/*     */         } 
/*     */       } 
/*     */       
/* 856 */       return new Binding(name.toString(), object);
/*     */     }
/*     */   }
/*     */   
/*     */   public class HierContextEnumerator
/*     */     extends ContextEnumerator {
/*     */     public HierContextEnumerator(Context param1Context, int param1Int) throws NamingException {
/* 863 */       super(param1Context, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     protected HierContextEnumerator(Context param1Context, int param1Int, String param1String, boolean param1Boolean) throws NamingException {
/* 868 */       super(param1Context, param1Int, param1String, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     protected NamingEnumeration<Binding> getImmediateChildren(Context param1Context) throws NamingException {
/* 873 */       return ((HierMemDirCtx)param1Context).doListBindings(false);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ContextEnumerator newEnumerator(Context param1Context, int param1Int, String param1String, boolean param1Boolean) throws NamingException {
/* 878 */       return new HierContextEnumerator(param1Context, param1Int, param1String, param1Boolean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/HierMemDirCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */