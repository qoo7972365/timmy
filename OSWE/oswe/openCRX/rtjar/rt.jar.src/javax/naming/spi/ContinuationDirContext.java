/*     */ package javax.naming.spi;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContinuationDirContext
/*     */   extends ContinuationContext
/*     */   implements DirContext
/*     */ {
/*     */   ContinuationDirContext(CannotProceedException paramCannotProceedException, Hashtable<?, ?> paramHashtable) {
/*  55 */     super(paramCannotProceedException, paramHashtable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContextNamePair getTargetContext(Name paramName) throws NamingException {
/*  61 */     if (this.cpe.getResolvedObj() == null) {
/*  62 */       throw (NamingException)this.cpe.fillInStackTrace();
/*     */     }
/*  64 */     Context context = NamingManager.getContext(this.cpe.getResolvedObj(), this.cpe
/*  65 */         .getAltName(), this.cpe
/*  66 */         .getAltNameCtx(), this.env);
/*     */     
/*  68 */     if (context == null) {
/*  69 */       throw (NamingException)this.cpe.fillInStackTrace();
/*     */     }
/*  71 */     if (context instanceof DirContext) {
/*  72 */       return new DirContextNamePair((DirContext)context, paramName);
/*     */     }
/*  74 */     if (context instanceof Resolver) {
/*  75 */       Resolver resolver = (Resolver)context;
/*  76 */       ResolveResult resolveResult = resolver.resolveToClass(paramName, (Class)DirContext.class);
/*     */ 
/*     */       
/*  79 */       DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*  80 */       return new DirContextNamePair(dirContext, resolveResult.getRemainingName());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  85 */     Object object = context.lookup(paramName);
/*  86 */     if (object instanceof DirContext) {
/*  87 */       return new DirContextNamePair((DirContext)object, new CompositeName());
/*     */     }
/*     */ 
/*     */     
/*  91 */     throw (NamingException)this.cpe.fillInStackTrace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContextStringPair getTargetContext(String paramString) throws NamingException {
/*  97 */     if (this.cpe.getResolvedObj() == null) {
/*  98 */       throw (NamingException)this.cpe.fillInStackTrace();
/*     */     }
/* 100 */     Context context = NamingManager.getContext(this.cpe.getResolvedObj(), this.cpe
/* 101 */         .getAltName(), this.cpe
/* 102 */         .getAltNameCtx(), this.env);
/*     */ 
/*     */     
/* 105 */     if (context instanceof DirContext) {
/* 106 */       return new DirContextStringPair((DirContext)context, paramString);
/*     */     }
/* 108 */     if (context instanceof Resolver) {
/* 109 */       Resolver resolver = (Resolver)context;
/* 110 */       ResolveResult resolveResult = resolver.resolveToClass(paramString, (Class)DirContext.class);
/*     */ 
/*     */       
/* 113 */       DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/* 114 */       Name name = resolveResult.getRemainingName();
/* 115 */       String str = (name != null) ? name.toString() : "";
/* 116 */       return new DirContextStringPair(dirContext, str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     Object object = context.lookup(paramString);
/* 122 */     if (object instanceof DirContext) {
/* 123 */       return new DirContextStringPair((DirContext)object, "");
/*     */     }
/*     */     
/* 126 */     throw (NamingException)this.cpe.fillInStackTrace();
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/* 130 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 131 */     return dirContextStringPair.getDirContext().getAttributes(dirContextStringPair.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 136 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 137 */     return dirContextStringPair.getDirContext().getAttributes(dirContextStringPair.getString(), paramArrayOfString);
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/* 141 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 142 */     return dirContextNamePair.getDirContext().getAttributes(dirContextNamePair.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/* 147 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 148 */     return dirContextNamePair.getDirContext().getAttributes(dirContextNamePair.getName(), paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 153 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 154 */     dirContextNamePair.getDirContext().modifyAttributes(dirContextNamePair.getName(), paramInt, paramAttributes);
/*     */   }
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 158 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 159 */     dirContextStringPair.getDirContext().modifyAttributes(dirContextStringPair.getString(), paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 164 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 165 */     dirContextNamePair.getDirContext().modifyAttributes(dirContextNamePair.getName(), paramArrayOfModificationItem);
/*     */   }
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 169 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 170 */     dirContextStringPair.getDirContext().modifyAttributes(dirContextStringPair.getString(), paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 175 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 176 */     dirContextNamePair.getDirContext().bind(dirContextNamePair.getName(), paramObject, paramAttributes);
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 180 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 181 */     dirContextStringPair.getDirContext().bind(dirContextStringPair.getString(), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 186 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 187 */     dirContextNamePair.getDirContext().rebind(dirContextNamePair.getName(), paramObject, paramAttributes);
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 191 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 192 */     dirContextStringPair.getDirContext().rebind(dirContextStringPair.getString(), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 197 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 198 */     return dirContextNamePair.getDirContext().createSubcontext(dirContextNamePair.getName(), paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 203 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 204 */     return dirContextStringPair
/* 205 */       .getDirContext().createSubcontext(dirContextStringPair.getString(), paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 212 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 213 */     return dirContextNamePair.getDirContext().search(dirContextNamePair.getName(), paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 221 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 222 */     return dirContextStringPair.getDirContext().search(dirContextStringPair.getString(), paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 230 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 231 */     return dirContextNamePair.getDirContext().search(dirContextNamePair.getName(), paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 236 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 237 */     return dirContextStringPair.getDirContext().search(dirContextStringPair.getString(), paramAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 245 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 246 */     return dirContextNamePair.getDirContext().search(dirContextNamePair.getName(), paramString, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 253 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString1);
/* 254 */     return dirContextStringPair.getDirContext().search(dirContextStringPair.getString(), paramString2, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 262 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 263 */     return dirContextNamePair.getDirContext().search(dirContextNamePair.getName(), paramString, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 272 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString1);
/* 273 */     return dirContextStringPair.getDirContext().search(dirContextStringPair.getString(), paramString2, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 278 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 279 */     return dirContextStringPair.getDirContext().getSchema(dirContextStringPair.getString());
/*     */   }
/*     */   
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/* 283 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 284 */     return dirContextNamePair.getDirContext().getSchema(dirContextNamePair.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 289 */     DirContextStringPair dirContextStringPair = getTargetContext(paramString);
/* 290 */     return dirContextStringPair.getDirContext().getSchemaClassDefinition(dirContextStringPair.getString());
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/* 295 */     DirContextNamePair dirContextNamePair = getTargetContext(paramName);
/* 296 */     return dirContextNamePair.getDirContext().getSchemaClassDefinition(dirContextNamePair.getName());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/ContinuationDirContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */