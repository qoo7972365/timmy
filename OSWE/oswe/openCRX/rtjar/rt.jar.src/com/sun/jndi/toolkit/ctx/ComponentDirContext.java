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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ComponentDirContext
/*     */   extends PartialCompositeDirContext
/*     */ {
/*     */   protected abstract Attributes c_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void c_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void c_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void c_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void c_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext c_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> c_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext c_getSchema(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext c_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected Attributes c_getAttributes_nns(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 133 */     c_processJunction_nns(paramName, paramContinuation);
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes_nns(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 142 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_modifyAttributes_nns(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 149 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_bind_nns(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 157 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_rebind_nns(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 165 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirContext c_createSubcontext_nns(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 172 */     c_processJunction_nns(paramName, paramContinuation);
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 182 */     c_processJunction_nns(paramName, paramContinuation);
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 192 */     c_processJunction_nns(paramName, paramContinuation);
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> c_search_nns(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 203 */     c_processJunction_nns(paramName, paramContinuation);
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchema_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 209 */     c_processJunction_nns(paramName, paramContinuation);
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext c_getSchemaClassDefinition_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 215 */     c_processJunction_nns(paramName, paramContinuation);
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attributes p_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 227 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 228 */     Attributes attributes = null;
/* 229 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 231 */         attributes = c_getAttributes_nns(headTail.getHead(), paramArrayOfString, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 235 */         attributes = c_getAttributes(headTail.getHead(), paramArrayOfString, paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     return attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void p_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 250 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 251 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 253 */         c_modifyAttributes_nns(headTail.getHead(), paramInt, paramAttributes, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 257 */         c_modifyAttributes(headTail.getHead(), paramInt, paramAttributes, paramContinuation);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void p_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 270 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 271 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 273 */         c_modifyAttributes_nns(headTail.getHead(), paramArrayOfModificationItem, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 277 */         c_modifyAttributes(headTail.getHead(), paramArrayOfModificationItem, paramContinuation);
/*     */         break;
/*     */     } 
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
/*     */   protected void p_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 292 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 293 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 295 */         c_bind_nns(headTail.getHead(), paramObject, paramAttributes, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 299 */         c_bind(headTail.getHead(), paramObject, paramAttributes, paramContinuation);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void p_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 312 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 313 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 315 */         c_rebind_nns(headTail.getHead(), paramObject, paramAttributes, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 319 */         c_rebind(headTail.getHead(), paramObject, paramAttributes, paramContinuation);
/*     */         break;
/*     */     } 
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
/*     */   protected DirContext p_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 333 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 334 */     DirContext dirContext = null;
/* 335 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 337 */         dirContext = c_createSubcontext_nns(headTail.getHead(), paramAttributes, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 341 */         dirContext = c_createSubcontext(headTail.getHead(), paramAttributes, paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     return dirContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> p_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 358 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 359 */     NamingEnumeration<SearchResult> namingEnumeration = null;
/* 360 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 362 */         namingEnumeration = c_search_nns(headTail.getHead(), paramAttributes, paramArrayOfString, paramContinuation);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 367 */         namingEnumeration = c_search(headTail.getHead(), paramAttributes, paramArrayOfString, paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> p_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 384 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 385 */     NamingEnumeration<SearchResult> namingEnumeration = null;
/* 386 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 388 */         namingEnumeration = c_search_nns(headTail.getHead(), paramString, paramSearchControls, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 392 */         namingEnumeration = c_search(headTail.getHead(), paramString, paramSearchControls, paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<SearchResult> p_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 409 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 410 */     NamingEnumeration<SearchResult> namingEnumeration = null;
/* 411 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 413 */         namingEnumeration = c_search_nns(headTail.getHead(), paramString, paramArrayOfObject, paramSearchControls, paramContinuation);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 418 */         namingEnumeration = c_search(headTail.getHead(), paramString, paramArrayOfObject, paramSearchControls, paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext p_getSchema(Name paramName, Continuation paramContinuation) throws NamingException {
/* 431 */     DirContext dirContext = null;
/* 432 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 433 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 435 */         dirContext = c_getSchema_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 439 */         dirContext = c_getSchema(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     return dirContext;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DirContext p_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException {
/* 452 */     DirContext dirContext = null;
/* 453 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 454 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 456 */         dirContext = c_getSchemaClassDefinition_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 460 */         dirContext = c_getSchemaClassDefinition(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     return dirContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/ComponentDirContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */