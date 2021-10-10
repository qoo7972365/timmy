/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NotContextException;
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PartialCompositeDirContext
/*     */   extends AtomicContext
/*     */   implements DirContext
/*     */ {
/*     */   protected abstract Attributes p_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext p_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> p_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> p_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<SearchResult> p_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext p_getSchema(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract DirContext p_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   public Attributes getAttributes(String paramString) throws NamingException {
/* 119 */     return getAttributes(paramString, (String[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(Name paramName) throws NamingException {
/* 124 */     return getAttributes(paramName, (String[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attributes getAttributes(String paramString, String[] paramArrayOfString) throws NamingException {
/* 129 */     return getAttributes(new CompositeName(paramString), paramArrayOfString);
/*     */   }
/*     */   
/*     */   public Attributes getAttributes(Name paramName, String[] paramArrayOfString) throws NamingException {
/*     */     Attributes attributes;
/* 134 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 135 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 136 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 138 */     Name name = paramName;
/*     */     
/*     */     try {
/* 141 */       attributes = partialCompositeDirContext.p_getAttributes(name, paramArrayOfString, continuation);
/* 142 */       while (continuation.isContinue()) {
/* 143 */         name = continuation.getRemainingName();
/* 144 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 145 */         attributes = partialCompositeDirContext.p_getAttributes(name, paramArrayOfString, continuation);
/*     */       } 
/* 147 */     } catch (CannotProceedException cannotProceedException) {
/* 148 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 149 */       attributes = dirContext.getAttributes(cannotProceedException.getRemainingName(), paramArrayOfString);
/*     */     } 
/* 151 */     return attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, int paramInt, Attributes paramAttributes) throws NamingException {
/* 156 */     modifyAttributes(new CompositeName(paramString), paramInt, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes) throws NamingException {
/* 161 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 162 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 163 */     Continuation continuation = new Continuation(paramName, hashtable);
/* 164 */     Name name = paramName;
/*     */     
/*     */     try {
/* 167 */       partialCompositeDirContext.p_modifyAttributes(name, paramInt, paramAttributes, continuation);
/* 168 */       while (continuation.isContinue()) {
/* 169 */         name = continuation.getRemainingName();
/* 170 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 171 */         partialCompositeDirContext.p_modifyAttributes(name, paramInt, paramAttributes, continuation);
/*     */       } 
/* 173 */     } catch (CannotProceedException cannotProceedException) {
/* 174 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 175 */       dirContext.modifyAttributes(cannotProceedException.getRemainingName(), paramInt, paramAttributes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(String paramString, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 181 */     modifyAttributes(new CompositeName(paramString), paramArrayOfModificationItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem) throws NamingException {
/* 186 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 187 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 188 */     Continuation continuation = new Continuation(paramName, hashtable);
/* 189 */     Name name = paramName;
/*     */     
/*     */     try {
/* 192 */       partialCompositeDirContext.p_modifyAttributes(name, paramArrayOfModificationItem, continuation);
/* 193 */       while (continuation.isContinue()) {
/* 194 */         name = continuation.getRemainingName();
/* 195 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 196 */         partialCompositeDirContext.p_modifyAttributes(name, paramArrayOfModificationItem, continuation);
/*     */       } 
/* 198 */     } catch (CannotProceedException cannotProceedException) {
/* 199 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 200 */       dirContext.modifyAttributes(cannotProceedException.getRemainingName(), paramArrayOfModificationItem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 206 */     bind(new CompositeName(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 211 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 212 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 213 */     Continuation continuation = new Continuation(paramName, hashtable);
/* 214 */     Name name = paramName;
/*     */     
/*     */     try {
/* 217 */       partialCompositeDirContext.p_bind(name, paramObject, paramAttributes, continuation);
/* 218 */       while (continuation.isContinue()) {
/* 219 */         name = continuation.getRemainingName();
/* 220 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 221 */         partialCompositeDirContext.p_bind(name, paramObject, paramAttributes, continuation);
/*     */       } 
/* 223 */     } catch (CannotProceedException cannotProceedException) {
/* 224 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 225 */       dirContext.bind(cannotProceedException.getRemainingName(), paramObject, paramAttributes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(String paramString, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 231 */     rebind(new CompositeName(paramString), paramObject, paramAttributes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject, Attributes paramAttributes) throws NamingException {
/* 236 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 237 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 238 */     Continuation continuation = new Continuation(paramName, hashtable);
/* 239 */     Name name = paramName;
/*     */     
/*     */     try {
/* 242 */       partialCompositeDirContext.p_rebind(name, paramObject, paramAttributes, continuation);
/* 243 */       while (continuation.isContinue()) {
/* 244 */         name = continuation.getRemainingName();
/* 245 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 246 */         partialCompositeDirContext.p_rebind(name, paramObject, paramAttributes, continuation);
/*     */       } 
/* 248 */     } catch (CannotProceedException cannotProceedException) {
/* 249 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 250 */       dirContext.rebind(cannotProceedException.getRemainingName(), paramObject, paramAttributes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext createSubcontext(String paramString, Attributes paramAttributes) throws NamingException {
/* 256 */     return createSubcontext(new CompositeName(paramString), paramAttributes);
/*     */   }
/*     */   
/*     */   public DirContext createSubcontext(Name paramName, Attributes paramAttributes) throws NamingException {
/*     */     DirContext dirContext;
/* 261 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 262 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 263 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 265 */     Name name = paramName;
/*     */     
/*     */     try {
/* 268 */       dirContext = partialCompositeDirContext.p_createSubcontext(name, paramAttributes, continuation);
/* 269 */       while (continuation.isContinue()) {
/* 270 */         name = continuation.getRemainingName();
/* 271 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 272 */         dirContext = partialCompositeDirContext.p_createSubcontext(name, paramAttributes, continuation);
/*     */       } 
/* 274 */     } catch (CannotProceedException cannotProceedException) {
/* 275 */       DirContext dirContext1 = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 276 */       dirContext = dirContext1.createSubcontext(cannotProceedException.getRemainingName(), paramAttributes);
/*     */     } 
/* 278 */     return dirContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes) throws NamingException {
/* 285 */     return search(paramString, paramAttributes, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes) throws NamingException {
/* 292 */     return search(paramName, paramAttributes, (String[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 301 */     return search(new CompositeName(paramString), paramAttributes, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/*     */     NamingEnumeration<SearchResult> namingEnumeration;
/* 312 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 313 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 314 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 316 */     Name name = paramName;
/*     */     
/*     */     try {
/* 319 */       namingEnumeration = partialCompositeDirContext.p_search(name, paramAttributes, paramArrayOfString, continuation);
/*     */       
/* 321 */       while (continuation.isContinue()) {
/* 322 */         name = continuation.getRemainingName();
/* 323 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 324 */         namingEnumeration = partialCompositeDirContext.p_search(name, paramAttributes, paramArrayOfString, continuation);
/*     */       }
/*     */     
/* 327 */     } catch (CannotProceedException cannotProceedException) {
/* 328 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 329 */       namingEnumeration = dirContext.search(cannotProceedException.getRemainingName(), paramAttributes, paramArrayOfString);
/*     */     } 
/*     */     
/* 332 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, SearchControls paramSearchControls) throws NamingException {
/* 341 */     return search(new CompositeName(paramString1), paramString2, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, SearchControls paramSearchControls) throws NamingException {
/*     */     NamingEnumeration<SearchResult> namingEnumeration;
/* 351 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 352 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 353 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 355 */     Name name = paramName;
/*     */     
/*     */     try {
/* 358 */       namingEnumeration = partialCompositeDirContext.p_search(name, paramString, paramSearchControls, continuation);
/* 359 */       while (continuation.isContinue()) {
/* 360 */         name = continuation.getRemainingName();
/* 361 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 362 */         namingEnumeration = partialCompositeDirContext.p_search(name, paramString, paramSearchControls, continuation);
/*     */       } 
/* 364 */     } catch (CannotProceedException cannotProceedException) {
/* 365 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 366 */       namingEnumeration = dirContext.search(cannotProceedException.getRemainingName(), paramString, paramSearchControls);
/*     */     } 
/* 368 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 378 */     return search(new CompositeName(paramString1), paramString2, paramArrayOfObject, paramSearchControls);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<SearchResult> search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/*     */     NamingEnumeration<SearchResult> namingEnumeration;
/* 389 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 390 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 391 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 393 */     Name name = paramName;
/*     */     
/*     */     try {
/* 396 */       namingEnumeration = partialCompositeDirContext.p_search(name, paramString, paramArrayOfObject, paramSearchControls, continuation);
/* 397 */       while (continuation.isContinue()) {
/* 398 */         name = continuation.getRemainingName();
/* 399 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 400 */         namingEnumeration = partialCompositeDirContext.p_search(name, paramString, paramArrayOfObject, paramSearchControls, continuation);
/*     */       } 
/* 402 */     } catch (CannotProceedException cannotProceedException) {
/* 403 */       DirContext dirContext = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 404 */       namingEnumeration = dirContext.search(cannotProceedException.getRemainingName(), paramString, paramArrayOfObject, paramSearchControls);
/*     */     } 
/*     */     
/* 407 */     return namingEnumeration;
/*     */   }
/*     */   
/*     */   public DirContext getSchema(String paramString) throws NamingException {
/* 411 */     return getSchema(new CompositeName(paramString));
/*     */   }
/*     */   public DirContext getSchema(Name paramName) throws NamingException {
/*     */     DirContext dirContext;
/* 415 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 416 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 417 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 419 */     Name name = paramName;
/*     */     
/*     */     try {
/* 422 */       dirContext = partialCompositeDirContext.p_getSchema(name, continuation);
/* 423 */       while (continuation.isContinue()) {
/* 424 */         name = continuation.getRemainingName();
/* 425 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 426 */         dirContext = partialCompositeDirContext.p_getSchema(name, continuation);
/*     */       } 
/* 428 */     } catch (CannotProceedException cannotProceedException) {
/* 429 */       DirContext dirContext1 = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 430 */       dirContext = dirContext1.getSchema(cannotProceedException.getRemainingName());
/*     */     } 
/* 432 */     return dirContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public DirContext getSchemaClassDefinition(String paramString) throws NamingException {
/* 437 */     return getSchemaClassDefinition(new CompositeName(paramString));
/*     */   }
/*     */   
/*     */   public DirContext getSchemaClassDefinition(Name paramName) throws NamingException {
/*     */     DirContext dirContext;
/* 442 */     PartialCompositeDirContext partialCompositeDirContext = this;
/* 443 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 444 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 446 */     Name name = paramName;
/*     */     
/*     */     try {
/* 449 */       dirContext = partialCompositeDirContext.p_getSchemaClassDefinition(name, continuation);
/* 450 */       while (continuation.isContinue()) {
/* 451 */         name = continuation.getRemainingName();
/* 452 */         partialCompositeDirContext = getPCDirContext(continuation);
/* 453 */         dirContext = partialCompositeDirContext.p_getSchemaClassDefinition(name, continuation);
/*     */       } 
/* 455 */     } catch (CannotProceedException cannotProceedException) {
/* 456 */       DirContext dirContext1 = DirectoryManager.getContinuationDirContext(cannotProceedException);
/* 457 */       dirContext = dirContext1.getSchemaClassDefinition(cannotProceedException.getRemainingName());
/*     */     } 
/* 459 */     return dirContext;
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
/*     */   protected static PartialCompositeDirContext getPCDirContext(Continuation paramContinuation) throws NamingException {
/* 472 */     PartialCompositeContext partialCompositeContext = PartialCompositeContext.getPCContext(paramContinuation);
/*     */     
/* 474 */     if (!(partialCompositeContext instanceof PartialCompositeDirContext)) {
/* 475 */       throw paramContinuation.fillInException(new NotContextException("Resolved object is not a DirContext."));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 480 */     return (PartialCompositeDirContext)partialCompositeContext;
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
/*     */   protected StringHeadTail c_parseComponent(String paramString, Continuation paramContinuation) throws NamingException {
/* 495 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 497 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object a_lookup(String paramString, Continuation paramContinuation) throws NamingException {
/* 502 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 504 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object a_lookupLink(String paramString, Continuation paramContinuation) throws NamingException {
/* 509 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 511 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> a_list(Continuation paramContinuation) throws NamingException {
/* 516 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 518 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> a_listBindings(Continuation paramContinuation) throws NamingException {
/* 523 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 525 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_bind(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 530 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 532 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_rebind(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 537 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 539 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_unbind(String paramString, Continuation paramContinuation) throws NamingException {
/* 544 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 546 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_destroySubcontext(String paramString, Continuation paramContinuation) throws NamingException {
/* 551 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 553 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context a_createSubcontext(String paramString, Continuation paramContinuation) throws NamingException {
/* 558 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 560 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_rename(String paramString, Name paramName, Continuation paramContinuation) throws NamingException {
/* 565 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 567 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameParser a_getNameParser(Continuation paramContinuation) throws NamingException {
/* 572 */     OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
/*     */     
/* 574 */     throw paramContinuation.fillInException(operationNotSupportedException);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/PartialCompositeDirContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */