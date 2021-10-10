/*     */ package com.sun.jndi.toolkit.url;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.ModificationItem;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.spi.DirectoryManager;
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
/*     */ public abstract class GenericURLDirContext
/*     */   extends GenericURLContext
/*     */   implements DirContext
/*     */ {
/*     */   protected GenericURLDirContext(Hashtable<?, ?> paramHashtable) {
/*  54 */     super(paramHashtable);
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
/*     */   protected DirContext getContinuationDirContext(Name paramName) throws NamingException {
/*  67 */     Object object = lookup(paramName.get(0));
/*  68 */     CannotProceedException cannotProceedException = new CannotProceedException();
/*  69 */     cannotProceedException.setResolvedObj(object);
/*  70 */     cannotProceedException.setEnvironment(this.myEnv);
/*  71 */     return DirectoryManager.getContinuationDirContext(cannotProceedException);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/*  76 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/*  77 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/*  79 */       return dirContext.getAttributes(resolveResult.getRemainingName());
/*     */     } finally {
/*  81 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/*  86 */     if (paramName.size() == 1) {
/*  87 */       return getAttributes(paramName.get(0));
/*     */     }
/*  89 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/*  91 */       return dirContext.getAttributes(paramName.getSuffix(1));
/*     */     } finally {
/*  93 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 100 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 101 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 103 */       return dirContext.getAttributes(resolveResult.getRemainingName(), paramArrayOfString);
/*     */     } finally {
/* 105 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/* 111 */     if (paramName.size() == 1) {
/* 112 */       return getAttributes(paramName.get(0), paramArrayOfString);
/*     */     }
/* 114 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 116 */       return dirContext.getAttributes(paramName.getSuffix(1), paramArrayOfString);
/*     */     } finally {
/* 118 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 125 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 126 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 128 */       dirContext.modifyAttributes(resolveResult.getRemainingName(), paramInt, paramAttributes);
/*     */     } finally {
/* 130 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 136 */     if (paramName.size() == 1) {
/* 137 */       modifyAttributes(paramName.get(0), paramInt, paramAttributes);
/*     */     } else {
/* 139 */       DirContext dirContext = getContinuationDirContext(paramName);
/*     */       try {
/* 141 */         dirContext.modifyAttributes(paramName.getSuffix(1), paramInt, paramAttributes);
/*     */       } finally {
/* 143 */         dirContext.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 150 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 151 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 153 */       dirContext.modifyAttributes(resolveResult.getRemainingName(), paramArrayOfModificationItem);
/*     */     } finally {
/* 155 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 161 */     if (paramName.size() == 1) {
/* 162 */       modifyAttributes(paramName.get(0), paramArrayOfModificationItem);
/*     */     } else {
/* 164 */       DirContext dirContext = getContinuationDirContext(paramName);
/*     */       try {
/* 166 */         dirContext.modifyAttributes(paramName.getSuffix(1), paramArrayOfModificationItem);
/*     */       } finally {
/* 168 */         dirContext.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 175 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 176 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 178 */       dirContext.bind(resolveResult.getRemainingName(), paramObject, paramAttributes);
/*     */     } finally {
/* 180 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 186 */     if (paramName.size() == 1) {
/* 187 */       bind(paramName.get(0), paramObject, paramAttributes);
/*     */     } else {
/* 189 */       DirContext dirContext = getContinuationDirContext(paramName);
/*     */       try {
/* 191 */         dirContext.bind(paramName.getSuffix(1), paramObject, paramAttributes);
/*     */       } finally {
/* 193 */         dirContext.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 200 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 201 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 203 */       dirContext.rebind(resolveResult.getRemainingName(), paramObject, paramAttributes);
/*     */     } finally {
/* 205 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 211 */     if (paramName.size() == 1) {
/* 212 */       rebind(paramName.get(0), paramObject, paramAttributes);
/*     */     } else {
/* 214 */       DirContext dirContext = getContinuationDirContext(paramName);
/*     */       try {
/* 216 */         dirContext.rebind(paramName.getSuffix(1), paramObject, paramAttributes);
/*     */       } finally {
/* 218 */         dirContext.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 225 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 226 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 228 */       return dirContext.createSubcontext(resolveResult.getRemainingName(), paramAttributes);
/*     */     } finally {
/* 230 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/* 236 */     if (paramName.size() == 1) {
/* 237 */       return createSubcontext(paramName.get(0), paramAttributes);
/*     */     }
/* 239 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 241 */       return dirContext.createSubcontext(paramName.getSuffix(1), paramAttributes);
/*     */     } finally {
/* 243 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 249 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 250 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/* 251 */     return dirContext.getSchema(resolveResult.getRemainingName());
/*     */   }
/*     */   
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/* 255 */     if (paramName.size() == 1) {
/* 256 */       return getSchema(paramName.get(0));
/*     */     }
/* 258 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 260 */       return dirContext.getSchema(paramName.getSuffix(1));
/*     */     } finally {
/* 262 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 269 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 270 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 272 */       return dirContext.getSchemaClassDefinition(resolveResult.getRemainingName());
/*     */     } finally {
/* 274 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/* 280 */     if (paramName.size() == 1) {
/* 281 */       return getSchemaClassDefinition(paramName.get(0));
/*     */     }
/* 283 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 285 */       return dirContext.getSchemaClassDefinition(paramName.getSuffix(1));
/*     */     } finally {
/* 287 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 295 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 296 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 298 */       return dirContext.search(resolveResult.getRemainingName(), paramAttributes);
/*     */     } finally {
/* 300 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 307 */     if (paramName.size() == 1) {
/* 308 */       return search(paramName.get(0), paramAttributes);
/*     */     }
/* 310 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 312 */       return dirContext.search(paramName.getSuffix(1), paramAttributes);
/*     */     } finally {
/* 314 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 323 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 324 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 326 */       return dirContext.search(resolveResult.getRemainingName(), paramAttributes, paramArrayOfString);
/*     */     } finally {
/*     */       
/* 329 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 337 */     if (paramName.size() == 1) {
/* 338 */       return search(paramName.get(0), paramAttributes, paramArrayOfString);
/*     */     }
/*     */     
/* 341 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 343 */       return dirContext.search(paramName.getSuffix(1), paramAttributes, paramArrayOfString);
/*     */     } finally {
/*     */       
/* 346 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 355 */     ResolveResult resolveResult = getRootURLContext(paramString1, this.myEnv);
/* 356 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 358 */       return dirContext.search(resolveResult.getRemainingName(), paramString2, paramSearchControls);
/*     */     } finally {
/* 360 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 368 */     if (paramName.size() == 1) {
/* 369 */       return search(paramName.get(0), paramString, paramSearchControls);
/*     */     }
/* 371 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 373 */       return dirContext.search(paramName.getSuffix(1), paramString, paramSearchControls);
/*     */     } finally {
/* 375 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 385 */     ResolveResult resolveResult = getRootURLContext(paramString1, this.myEnv);
/* 386 */     DirContext dirContext = (DirContext)resolveResult.getResolvedObj();
/*     */     try {
/* 388 */       return dirContext
/* 389 */         .search(resolveResult.getRemainingName(), paramString2, paramArrayOfObject, paramSearchControls);
/*     */     } finally {
/* 391 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 400 */     if (paramName.size() == 1) {
/* 401 */       return search(paramName.get(0), paramString, paramArrayOfObject, paramSearchControls);
/*     */     }
/* 403 */     DirContext dirContext = getContinuationDirContext(paramName);
/*     */     try {
/* 405 */       return dirContext.search(paramName.getSuffix(1), paramString, paramArrayOfObject, paramSearchControls);
/*     */     } finally {
/* 407 */       dirContext.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/url/GenericURLDirContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */