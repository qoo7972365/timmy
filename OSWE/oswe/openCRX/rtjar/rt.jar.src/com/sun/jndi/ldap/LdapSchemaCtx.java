/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import com.sun.jndi.toolkit.dir.HierMemDirCtx;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SchemaViolationException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LdapSchemaCtx
/*     */   extends HierMemDirCtx
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private static final int LEAF = 0;
/*     */   private static final int SCHEMA_ROOT = 1;
/*     */   static final int OBJECTCLASS_ROOT = 2;
/*     */   static final int ATTRIBUTE_ROOT = 3;
/*     */   static final int SYNTAX_ROOT = 4;
/*     */   static final int MATCHRULE_ROOT = 5;
/*     */   static final int OBJECTCLASS = 6;
/*     */   static final int ATTRIBUTE = 7;
/*     */   static final int SYNTAX = 8;
/*     */   static final int MATCHRULE = 9;
/*  59 */   private SchemaInfo info = null;
/*     */ 
/*     */   
/*     */   private boolean setupMode = true;
/*     */   
/*     */   private int objectType;
/*     */ 
/*     */   
/*     */   static DirContext createSchemaTree(Hashtable<String, Object> paramHashtable, String paramString, LdapCtx paramLdapCtx, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/*     */     try {
/*  69 */       LdapSchemaParser ldapSchemaParser = new LdapSchemaParser(paramBoolean);
/*     */       
/*  71 */       SchemaInfo schemaInfo = new SchemaInfo(paramString, paramLdapCtx, ldapSchemaParser);
/*     */ 
/*     */       
/*  74 */       LdapSchemaCtx ldapSchemaCtx = new LdapSchemaCtx(1, paramHashtable, schemaInfo);
/*  75 */       LdapSchemaParser.LDAP2JNDISchema(paramAttributes, ldapSchemaCtx);
/*  76 */       return ldapSchemaCtx;
/*  77 */     } catch (NamingException namingException) {
/*  78 */       paramLdapCtx.close();
/*  79 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LdapSchemaCtx(int paramInt, Hashtable<String, Object> paramHashtable, SchemaInfo paramSchemaInfo) {
/*  86 */     super(paramHashtable, true);
/*     */     
/*  88 */     this.objectType = paramInt;
/*  89 */     this.info = paramSchemaInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws NamingException {
/*  94 */     this.info.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 101 */     if (!this.setupMode) {
/* 102 */       if (paramObject != null) {
/* 103 */         throw new IllegalArgumentException("obj must be null");
/*     */       }
/*     */ 
/*     */       
/* 107 */       addServerSchema(paramAttributes);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     LdapSchemaCtx ldapSchemaCtx = (LdapSchemaCtx)super.doCreateSubcontext(paramName, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doBind(Name paramName, Object paramObject, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/* 117 */     if (!this.setupMode) {
/* 118 */       throw new SchemaViolationException("Cannot bind arbitrary object; use createSubcontext()");
/*     */     }
/*     */     
/* 121 */     super.doBind(paramName, paramObject, paramAttributes, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/*     */     try {
/* 129 */       doLookup(paramName, false);
/* 130 */       throw new SchemaViolationException("Cannot replace existing schema object");
/*     */     }
/* 132 */     catch (NameNotFoundException nameNotFoundException) {
/* 133 */       bind(paramName, paramObject, paramAttributes);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void doRebind(Name paramName, Object paramObject, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/* 139 */     if (!this.setupMode) {
/* 140 */       throw new SchemaViolationException("Cannot bind arbitrary object; use createSubcontext()");
/*     */     }
/*     */     
/* 143 */     super.doRebind(paramName, paramObject, paramAttributes, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doUnbind(Name paramName) throws NamingException {
/* 148 */     if (!this.setupMode) {
/*     */       
/*     */       try {
/*     */         
/* 152 */         LdapSchemaCtx ldapSchemaCtx = (LdapSchemaCtx)doLookup(paramName, false);
/*     */         
/* 154 */         deleteServerSchema(ldapSchemaCtx.attrs);
/* 155 */       } catch (NameNotFoundException nameNotFoundException) {
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 160 */     super.doUnbind(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doRename(Name paramName1, Name paramName2) throws NamingException {
/* 165 */     if (!this.setupMode) {
/* 166 */       throw new SchemaViolationException("Cannot rename a schema object");
/*     */     }
/* 168 */     super.doRename(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doDestroySubcontext(Name paramName) throws NamingException {
/* 173 */     if (!this.setupMode) {
/*     */       
/*     */       try {
/*     */         
/* 177 */         LdapSchemaCtx ldapSchemaCtx = (LdapSchemaCtx)doLookup(paramName, false);
/*     */         
/* 179 */         deleteServerSchema(ldapSchemaCtx.attrs);
/* 180 */       } catch (NameNotFoundException nameNotFoundException) {
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 186 */     super.doDestroySubcontext(paramName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final LdapSchemaCtx setup(int paramInt, String paramString, Attributes paramAttributes) throws NamingException {
/*     */     try {
/* 193 */       this.setupMode = true;
/*     */       
/* 195 */       LdapSchemaCtx ldapSchemaCtx = (LdapSchemaCtx)super.doCreateSubcontext(new CompositeName(paramString), paramAttributes);
/*     */ 
/*     */       
/* 198 */       ldapSchemaCtx.objectType = paramInt;
/* 199 */       ldapSchemaCtx.setupMode = false;
/* 200 */       return ldapSchemaCtx;
/*     */     } finally {
/* 202 */       this.setupMode = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final DirContext doCreateSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 209 */     if (paramAttributes == null || paramAttributes.size() == 0) {
/* 210 */       throw new SchemaViolationException("Must supply attributes describing schema");
/*     */     }
/*     */ 
/*     */     
/* 214 */     if (!this.setupMode)
/*     */     {
/* 216 */       addServerSchema(paramAttributes);
/*     */     }
/*     */ 
/*     */     
/* 220 */     return super
/* 221 */       .doCreateSubcontext(paramName, paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Attributes deepClone(Attributes paramAttributes) throws NamingException {
/* 227 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/* 228 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 229 */     while (namingEnumeration.hasMore()) {
/* 230 */       basicAttributes.put((Attribute)((Attribute)namingEnumeration.next()).clone());
/*     */     }
/* 232 */     return basicAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doModifyAttributes(ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 237 */     if (this.setupMode) {
/* 238 */       super.doModifyAttributes(paramArrayOfModificationItem);
/*     */     } else {
/* 240 */       Attributes attributes = deepClone(this.attrs);
/*     */ 
/*     */       
/* 243 */       applyMods(paramArrayOfModificationItem, attributes);
/*     */ 
/*     */       
/* 246 */       modifyServerSchema(this.attrs, attributes);
/*     */ 
/*     */       
/* 249 */       this.attrs = attributes;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final HierMemDirCtx createNewCtx() {
/* 257 */     return new LdapSchemaCtx(0, this.myEnv, this.info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void addServerSchema(Attributes paramAttributes) throws NamingException {
/*     */     Attribute attribute;
/* 266 */     switch (this.objectType) {
/*     */       case 2:
/* 268 */         attribute = this.info.parser.stringifyObjDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 3:
/* 272 */         attribute = this.info.parser.stringifyAttrDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 4:
/* 276 */         attribute = this.info.parser.stringifySyntaxDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 5:
/* 280 */         attribute = this.info.parser.stringifyMatchRuleDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 1:
/* 284 */         throw new SchemaViolationException("Cannot create new entry under schema root");
/*     */ 
/*     */       
/*     */       default:
/* 288 */         throw new SchemaViolationException("Cannot create child of schema object");
/*     */     } 
/*     */ 
/*     */     
/* 292 */     BasicAttributes basicAttributes = new BasicAttributes(true);
/* 293 */     basicAttributes.put(attribute);
/*     */ 
/*     */     
/* 296 */     this.info.modifyAttributes(this.myEnv, 1, basicAttributes);
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
/*     */   private final void deleteServerSchema(Attributes paramAttributes) throws NamingException {
/*     */     Attribute attribute;
/* 312 */     switch (this.objectType) {
/*     */       case 2:
/* 314 */         attribute = this.info.parser.stringifyObjDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 3:
/* 318 */         attribute = this.info.parser.stringifyAttrDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 4:
/* 322 */         attribute = this.info.parser.stringifySyntaxDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 5:
/* 326 */         attribute = this.info.parser.stringifyMatchRuleDesc(paramAttributes);
/*     */         break;
/*     */       
/*     */       case 1:
/* 330 */         throw new SchemaViolationException("Cannot delete schema root");
/*     */ 
/*     */       
/*     */       default:
/* 334 */         throw new SchemaViolationException("Cannot delete child of schema object");
/*     */     } 
/*     */ 
/*     */     
/* 338 */     ModificationItem[] arrayOfModificationItem = new ModificationItem[1];
/* 339 */     arrayOfModificationItem[0] = new ModificationItem(3, attribute);
/*     */     
/* 341 */     this.info.modifyAttributes(this.myEnv, arrayOfModificationItem);
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
/*     */   private final void modifyServerSchema(Attributes paramAttributes1, Attributes paramAttributes2) throws NamingException {
/*     */     Attribute attribute1, attribute2;
/* 356 */     switch (this.objectType) {
/*     */       case 6:
/* 358 */         attribute2 = this.info.parser.stringifyObjDesc(paramAttributes1);
/* 359 */         attribute1 = this.info.parser.stringifyObjDesc(paramAttributes2);
/*     */         break;
/*     */       
/*     */       case 7:
/* 363 */         attribute2 = this.info.parser.stringifyAttrDesc(paramAttributes1);
/* 364 */         attribute1 = this.info.parser.stringifyAttrDesc(paramAttributes2);
/*     */         break;
/*     */       
/*     */       case 8:
/* 368 */         attribute2 = this.info.parser.stringifySyntaxDesc(paramAttributes1);
/* 369 */         attribute1 = this.info.parser.stringifySyntaxDesc(paramAttributes2);
/*     */         break;
/*     */       
/*     */       case 9:
/* 373 */         attribute2 = this.info.parser.stringifyMatchRuleDesc(paramAttributes1);
/* 374 */         attribute1 = this.info.parser.stringifyMatchRuleDesc(paramAttributes2);
/*     */         break;
/*     */       
/*     */       default:
/* 378 */         throw new SchemaViolationException("Cannot modify schema root");
/*     */     } 
/*     */ 
/*     */     
/* 382 */     ModificationItem[] arrayOfModificationItem = new ModificationItem[2];
/* 383 */     arrayOfModificationItem[0] = new ModificationItem(3, attribute2);
/* 384 */     arrayOfModificationItem[1] = new ModificationItem(1, attribute1);
/*     */     
/* 386 */     this.info.modifyAttributes(this.myEnv, arrayOfModificationItem);
/*     */   }
/*     */   
/*     */   private static final class SchemaInfo
/*     */   {
/*     */     private LdapCtx schemaEntry;
/*     */     private String schemaEntryName;
/*     */     LdapSchemaParser parser;
/*     */     private String host;
/*     */     private int port;
/*     */     private boolean hasLdapsScheme;
/*     */     
/*     */     SchemaInfo(String param1String, LdapCtx param1LdapCtx, LdapSchemaParser param1LdapSchemaParser) {
/* 399 */       this.schemaEntryName = param1String;
/* 400 */       this.schemaEntry = param1LdapCtx;
/* 401 */       this.parser = param1LdapSchemaParser;
/* 402 */       this.port = param1LdapCtx.port_number;
/* 403 */       this.host = param1LdapCtx.hostname;
/* 404 */       this.hasLdapsScheme = param1LdapCtx.hasLdapsScheme;
/*     */     }
/*     */     
/*     */     synchronized void close() throws NamingException {
/* 408 */       if (this.schemaEntry != null) {
/* 409 */         this.schemaEntry.close();
/* 410 */         this.schemaEntry = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private LdapCtx reopenEntry(Hashtable<?, ?> param1Hashtable) throws NamingException {
/* 416 */       return new LdapCtx(this.schemaEntryName, this.host, this.port, param1Hashtable, this.hasLdapsScheme);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void modifyAttributes(Hashtable<?, ?> param1Hashtable, ModificationItem[] param1ArrayOfModificationItem) throws NamingException {
/* 423 */       if (this.schemaEntry == null) {
/* 424 */         this.schemaEntry = reopenEntry(param1Hashtable);
/*     */       }
/* 426 */       this.schemaEntry.modifyAttributes("", param1ArrayOfModificationItem);
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void modifyAttributes(Hashtable<?, ?> param1Hashtable, int param1Int, Attributes param1Attributes) throws NamingException {
/* 431 */       if (this.schemaEntry == null) {
/* 432 */         this.schemaEntry = reopenEntry(param1Hashtable);
/*     */       }
/* 434 */       this.schemaEntry.modifyAttributes("", param1Int, param1Attributes);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapSchemaCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */