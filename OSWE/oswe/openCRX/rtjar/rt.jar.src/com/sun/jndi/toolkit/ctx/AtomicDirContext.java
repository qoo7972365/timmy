/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
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
/*     */ public abstract class AtomicDirContext
/*     */   extends ComponentDirContext
/*     */ {
/*     */   protected abstract Attributes a_getAttributes(String paramString, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void a_modifyAttributes(String paramString, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void a_modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void a_bind(String paramString, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void a_rebind(String paramString, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext a_createSubcontext(String paramString, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> a_search(Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> a_search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> a_search(String paramString1, String paramString2, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext a_getSchema(Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext a_getSchemaClassDefinition(Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected Attributes a_getAttributes_nns(String paramString, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 130 */     a_processJunction_nns(paramString, paramContinuation);
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a_modifyAttributes_nns(String paramString, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 138 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a_modifyAttributes_nns(String paramString, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 145 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a_bind_nns(String paramString, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 152 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a_rebind_nns(String paramString, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 159 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContext a_createSubcontext_nns(String paramString, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 166 */     a_processJunction_nns(paramString, paramContinuation);
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> a_search_nns(Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 175 */     a_processJunction_nns(paramContinuation);
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> a_search_nns(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 185 */     a_processJunction_nns(paramString1, paramContinuation);
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> a_search_nns(String paramString1, String paramString2, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 194 */     a_processJunction_nns(paramString1, paramContinuation);
/* 195 */     return null;
/*     */   }
/*     */   
/*     */   protected DirContext a_getSchema_nns(Continuation paramContinuation) throws NamingException {
/* 199 */     a_processJunction_nns(paramContinuation);
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext a_getSchemaDefinition_nns(Continuation paramContinuation) throws NamingException {
/* 205 */     a_processJunction_nns(paramContinuation);
/* 206 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attributes c_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 215 */     if (resolve_to_penultimate_context(paramName, paramContinuation))
/* 216 */       return a_getAttributes(paramName.toString(), paramArrayOfString, paramContinuation); 
/* 217 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 223 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 224 */       a_modifyAttributes(paramName.toString(), paramInt, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 230 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 231 */       a_modifyAttributes(paramName.toString(), paramArrayOfModificationItem, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 237 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 238 */       a_bind(paramName.toString(), paramObject, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 244 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 245 */       a_rebind(paramName.toString(), paramObject, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContext c_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 252 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 253 */       return a_createSubcontext(paramName.toString(), paramAttributes, paramContinuation);
/*     */     }
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 263 */     if (resolve_to_context(paramName, paramContinuation))
/* 264 */       return a_search(paramAttributes, paramArrayOfString, paramContinuation); 
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 273 */     if (resolve_to_penultimate_context(paramName, paramContinuation))
/* 274 */       return a_search(paramName.toString(), paramString, paramSearchControls, paramContinuation); 
/* 275 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 284 */     if (resolve_to_penultimate_context(paramName, paramContinuation))
/* 285 */       return a_search(paramName.toString(), paramString, paramArrayOfObject, paramSearchControls, paramContinuation); 
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchema(Name paramName, Continuation paramContinuation) throws NamingException {
/* 291 */     if (resolve_to_context(paramName, paramContinuation))
/* 292 */       return a_getSchema(paramContinuation); 
/* 293 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException {
/* 298 */     if (resolve_to_context(paramName, paramContinuation))
/* 299 */       return a_getSchemaClassDefinition(paramContinuation); 
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attributes c_getAttributes_nns(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 308 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation))
/* 309 */       return a_getAttributes_nns(paramName.toString(), paramArrayOfString, paramContinuation); 
/* 310 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes_nns(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 316 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 317 */       a_modifyAttributes_nns(paramName.toString(), paramInt, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes_nns(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 323 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 324 */       a_modifyAttributes_nns(paramName.toString(), paramArrayOfModificationItem, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_bind_nns(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 330 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 331 */       a_bind_nns(paramName.toString(), paramObject, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rebind_nns(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 337 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 338 */       a_rebind_nns(paramName.toString(), paramObject, paramAttributes, paramContinuation);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContext c_createSubcontext_nns(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 345 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation))
/* 346 */       return a_createSubcontext_nns(paramName.toString(), paramAttributes, paramContinuation); 
/* 347 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 356 */     resolve_to_nns_and_continue(paramName, paramContinuation);
/* 357 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 365 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation))
/* 366 */       return a_search_nns(paramName.toString(), paramString, paramSearchControls, paramContinuation); 
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 376 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 377 */       return a_search_nns(paramName.toString(), paramString, paramArrayOfObject, paramSearchControls, paramContinuation);
/*     */     }
/* 379 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchema_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 384 */     resolve_to_nns_and_continue(paramName, paramContinuation);
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchemaClassDefinition_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 390 */     resolve_to_nns_and_continue(paramName, paramContinuation);
/* 391 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/AtomicDirContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */