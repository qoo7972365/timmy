/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AtomicContext
/*     */   extends ComponentContext
/*     */ {
/*  45 */   private static int debug = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object a_lookup(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object a_lookupLink(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NamingEnumeration<NameClassPair> a_list(Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NamingEnumeration<Binding> a_listBindings(Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void a_bind(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void a_rebind(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void a_unbind(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void a_destroySubcontext(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Context a_createSubcontext(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void a_rename(String paramString, Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NameParser a_getNameParser(Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract StringHeadTail c_parseComponent(String paramString, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object a_resolveIntermediate_nns(String paramString, Continuation paramContinuation) throws NamingException {
/*     */     try {
/* 118 */       final Object obj = a_lookup(paramString, paramContinuation);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       if (object != null && getClass().isInstance(object)) {
/*     */ 
/*     */ 
/*     */         
/* 128 */         paramContinuation.setContinueNNS(object, paramString, this);
/* 129 */         return null;
/*     */       } 
/* 131 */       if (object != null && !(object instanceof Context)) {
/*     */ 
/*     */         
/* 134 */         RefAddr refAddr = new RefAddr("nns") {
/*     */             public Object getContent() {
/* 136 */               return obj;
/*     */             }
/*     */             
/*     */             private static final long serialVersionUID = -3399518522645918499L;
/*     */           };
/* 141 */         Reference reference = new Reference("java.lang.Object", refAddr);
/*     */ 
/*     */         
/* 144 */         CompositeName compositeName = new CompositeName();
/* 145 */         compositeName.add(paramString);
/* 146 */         compositeName.add("");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         paramContinuation.setContinue(reference, compositeName, this);
/* 154 */         return null;
/*     */       } 
/*     */       
/* 157 */       return object;
/*     */     
/*     */     }
/* 160 */     catch (NamingException namingException) {
/* 161 */       namingException.appendRemainingComponent("");
/* 162 */       throw namingException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object a_lookup_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 185 */     a_processJunction_nns(paramString, paramContinuation);
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object a_lookupLink_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 191 */     a_processJunction_nns(paramString, paramContinuation);
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> a_list_nns(Continuation paramContinuation) throws NamingException {
/* 197 */     a_processJunction_nns(paramContinuation);
/* 198 */     return null;
/*     */   }
/*     */   
/*     */   protected NamingEnumeration<Binding> a_listBindings_nns(Continuation paramContinuation) throws NamingException {
/* 202 */     a_processJunction_nns(paramContinuation);
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_bind_nns(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 208 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_rebind_nns(String paramString, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 213 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_unbind_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 218 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context a_createSubcontext_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 223 */     a_processJunction_nns(paramString, paramContinuation);
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_destroySubcontext_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 229 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a_rename_nns(String paramString, Name paramName, Continuation paramContinuation) throws NamingException {
/* 234 */     a_processJunction_nns(paramString, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameParser a_getNameParser_nns(Continuation paramContinuation) throws NamingException {
/* 239 */     a_processJunction_nns(paramContinuation);
/* 240 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEmpty(String paramString) {
/* 246 */     return (paramString == null || paramString.equals(""));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object c_lookup(Name paramName, Continuation paramContinuation) throws NamingException {
/* 256 */     Object object = null;
/* 257 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 258 */       object = a_lookup(paramName.toString(), paramContinuation);
/* 259 */       if (object != null && object instanceof javax.naming.LinkRef) {
/* 260 */         paramContinuation.setContinue(object, paramName, this);
/* 261 */         object = null;
/*     */       } 
/*     */     } 
/* 264 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object c_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException {
/* 269 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 270 */       return a_lookupLink(paramName.toString(), paramContinuation);
/*     */     }
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> c_list(Name paramName, Continuation paramContinuation) throws NamingException {
/* 277 */     if (resolve_to_context(paramName, paramContinuation)) {
/* 278 */       return a_list(paramContinuation);
/*     */     }
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> c_listBindings(Name paramName, Continuation paramContinuation) throws NamingException {
/* 285 */     if (resolve_to_context(paramName, paramContinuation)) {
/* 286 */       return a_listBindings(paramContinuation);
/*     */     }
/* 288 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 293 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 294 */       a_bind(paramName.toString(), paramObject, paramContinuation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 299 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 300 */       a_rebind(paramName.toString(), paramObject, paramContinuation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c_unbind(Name paramName, Continuation paramContinuation) throws NamingException {
/* 305 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 306 */       a_unbind(paramName.toString(), paramContinuation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 311 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 312 */       a_destroySubcontext(paramName.toString(), paramContinuation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Context c_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 317 */     if (resolve_to_penultimate_context(paramName, paramContinuation)) {
/* 318 */       return a_createSubcontext(paramName.toString(), paramContinuation);
/*     */     }
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/* 325 */     if (resolve_to_penultimate_context(paramName1, paramContinuation)) {
/* 326 */       a_rename(paramName1.toString(), paramName2, paramContinuation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected NameParser c_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException {
/* 331 */     if (resolve_to_context(paramName, paramContinuation))
/* 332 */       return a_getNameParser(paramContinuation); 
/* 333 */     return null;
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
/*     */   protected Object c_resolveIntermediate_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 347 */     if (this._contextType == 3) {
/* 348 */       Object object = null;
/* 349 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 350 */         object = a_resolveIntermediate_nns(paramName.toString(), paramContinuation);
/* 351 */         if (object != null && object instanceof javax.naming.LinkRef) {
/* 352 */           paramContinuation.setContinue(object, paramName, this);
/* 353 */           object = null;
/*     */         } 
/*     */       } 
/* 356 */       return object;
/*     */     } 
/*     */     
/* 359 */     return super.c_resolveIntermediate_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object c_lookup_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 367 */     if (this._contextType == 3) {
/* 368 */       Object object = null;
/* 369 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 370 */         object = a_lookup_nns(paramName.toString(), paramContinuation);
/* 371 */         if (object != null && object instanceof javax.naming.LinkRef) {
/* 372 */           paramContinuation.setContinue(object, paramName, this);
/* 373 */           object = null;
/*     */         } 
/*     */       } 
/* 376 */       return object;
/*     */     } 
/* 378 */     return super.c_lookup_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object c_lookupLink_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 384 */     if (this._contextType == 3) {
/*     */       
/* 386 */       resolve_to_nns_and_continue(paramName, paramContinuation);
/* 387 */       return null;
/*     */     } 
/*     */     
/* 390 */     return super.c_lookupLink_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> c_list_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 396 */     if (this._contextType == 3) {
/* 397 */       resolve_to_nns_and_continue(paramName, paramContinuation);
/* 398 */       return null;
/*     */     } 
/*     */     
/* 401 */     return super.c_list_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> c_listBindings_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 407 */     if (this._contextType == 3) {
/* 408 */       resolve_to_nns_and_continue(paramName, paramContinuation);
/* 409 */       return null;
/*     */     } 
/*     */     
/* 412 */     return super.c_listBindings_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_bind_nns(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 418 */     if (this._contextType == 3) {
/* 419 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 420 */         a_bind_nns(paramName.toString(), paramObject, paramContinuation);
/*     */       }
/*     */     } else {
/* 423 */       super.c_bind_nns(paramName, paramObject, paramContinuation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rebind_nns(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 429 */     if (this._contextType == 3) {
/* 430 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 431 */         a_rebind_nns(paramName.toString(), paramObject, paramContinuation);
/*     */       }
/*     */     } else {
/* 434 */       super.c_rebind_nns(paramName, paramObject, paramContinuation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_unbind_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 440 */     if (this._contextType == 3) {
/* 441 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 442 */         a_unbind_nns(paramName.toString(), paramContinuation);
/*     */       }
/*     */     } else {
/* 445 */       super.c_unbind_nns(paramName, paramContinuation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context c_createSubcontext_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 451 */     if (this._contextType == 3) {
/* 452 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 453 */         return a_createSubcontext_nns(paramName.toString(), paramContinuation);
/*     */       }
/* 455 */       return null;
/*     */     } 
/*     */     
/* 458 */     return super.c_createSubcontext_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_destroySubcontext_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 464 */     if (this._contextType == 3) {
/* 465 */       if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 466 */         a_destroySubcontext_nns(paramName.toString(), paramContinuation);
/*     */       }
/*     */     } else {
/* 469 */       super.c_destroySubcontext_nns(paramName, paramContinuation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rename_nns(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/* 475 */     if (this._contextType == 3) {
/* 476 */       if (resolve_to_penultimate_context_nns(paramName1, paramContinuation)) {
/* 477 */         a_rename_nns(paramName1.toString(), paramName2, paramContinuation);
/*     */       }
/*     */     } else {
/* 480 */       super.c_rename_nns(paramName1, paramName2, paramContinuation);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameParser c_getNameParser_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 486 */     if (this._contextType == 3) {
/* 487 */       resolve_to_nns_and_continue(paramName, paramContinuation);
/* 488 */       return null;
/*     */     } 
/*     */     
/* 491 */     return super.c_getNameParser_nns(paramName, paramContinuation);
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
/*     */   protected void a_processJunction_nns(String paramString, Continuation paramContinuation) throws NamingException {
/* 513 */     if (paramString.equals("")) {
/* 514 */       NameNotFoundException nameNotFoundException = new NameNotFoundException();
/* 515 */       paramContinuation.setErrorNNS(this, paramString);
/* 516 */       throw paramContinuation.fillInException(nameNotFoundException);
/*     */     } 
/*     */     
/*     */     try {
/* 520 */       Object object = a_lookup(paramString, paramContinuation);
/* 521 */       if (paramContinuation.isContinue()) {
/* 522 */         paramContinuation.appendRemainingComponent("");
/*     */       } else {
/* 524 */         paramContinuation.setContinueNNS(object, paramString, this);
/*     */       } 
/* 526 */     } catch (NamingException namingException) {
/* 527 */       namingException.appendRemainingComponent("");
/* 528 */       throw namingException;
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
/*     */   
/*     */   protected void a_processJunction_nns(Continuation paramContinuation) throws NamingException {
/* 543 */     RefAddr refAddr = new RefAddr("nns") { private static final long serialVersionUID = 3449785852664978312L;
/*     */         public Object getContent() {
/* 545 */           return AtomicContext.this;
/*     */         } }
/*     */       ;
/*     */     
/* 549 */     Reference reference = new Reference("java.lang.Object", refAddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 555 */     paramContinuation.setContinue(reference, _NNS_NAME, this);
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
/*     */   protected boolean resolve_to_context(Name paramName, Continuation paramContinuation) throws NamingException {
/* 567 */     String str1 = paramName.toString();
/*     */ 
/*     */     
/* 570 */     StringHeadTail stringHeadTail = c_parseComponent(str1, paramContinuation);
/* 571 */     String str2 = stringHeadTail.getTail();
/* 572 */     String str3 = stringHeadTail.getHead();
/*     */     
/* 574 */     if (debug > 0) {
/* 575 */       System.out.println("RESOLVE TO CONTEXT(" + str1 + ") = {" + str3 + ", " + str2 + "}");
/*     */     }
/*     */     
/* 578 */     if (str3 == null) {
/*     */       
/* 580 */       InvalidNameException invalidNameException = new InvalidNameException();
/* 581 */       throw paramContinuation.fillInException(invalidNameException);
/*     */     } 
/* 583 */     if (!isEmpty(str3)) {
/*     */ 
/*     */       
/*     */       try {
/* 587 */         Object object = a_lookup(str3, paramContinuation);
/*     */         
/* 589 */         if (object != null)
/* 590 */         { paramContinuation.setContinue(object, str3, this, (str2 == null) ? "" : str2); }
/* 591 */         else if (paramContinuation.isContinue())
/* 592 */         { paramContinuation.appendRemainingComponent(str2); } 
/* 593 */       } catch (NamingException namingException) {
/* 594 */         namingException.appendRemainingComponent(str2);
/* 595 */         throw namingException;
/*     */       } 
/*     */     } else {
/* 598 */       paramContinuation.setSuccess();
/* 599 */       return true;
/*     */     } 
/* 601 */     return false;
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
/*     */   protected boolean resolve_to_penultimate_context(Name paramName, Continuation paramContinuation) throws NamingException {
/* 613 */     String str1 = paramName.toString();
/*     */     
/* 615 */     if (debug > 0) {
/* 616 */       System.out.println("RESOLVE TO PENULTIMATE" + str1);
/*     */     }
/* 618 */     StringHeadTail stringHeadTail = c_parseComponent(str1, paramContinuation);
/* 619 */     String str2 = stringHeadTail.getTail();
/* 620 */     String str3 = stringHeadTail.getHead();
/* 621 */     if (str3 == null) {
/*     */       
/* 623 */       InvalidNameException invalidNameException = new InvalidNameException();
/* 624 */       throw paramContinuation.fillInException(invalidNameException);
/*     */     } 
/*     */     
/* 627 */     if (!isEmpty(str2)) {
/*     */       
/*     */       try {
/* 630 */         Object object = a_lookup(str3, paramContinuation);
/* 631 */         if (object != null)
/* 632 */         { paramContinuation.setContinue(object, str3, this, str2); }
/* 633 */         else if (paramContinuation.isContinue())
/* 634 */         { paramContinuation.appendRemainingComponent(str2); } 
/* 635 */       } catch (NamingException namingException) {
/* 636 */         namingException.appendRemainingComponent(str2);
/* 637 */         throw namingException;
/*     */       } 
/*     */     } else {
/*     */       
/* 641 */       paramContinuation.setSuccess();
/* 642 */       return true;
/*     */     } 
/* 644 */     return false;
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
/*     */   protected boolean resolve_to_penultimate_context_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/*     */     try {
/* 657 */       if (debug > 0)
/* 658 */         System.out.println("RESOLVE TO PENULTIMATE NNS" + paramName.toString()); 
/* 659 */       boolean bool = resolve_to_penultimate_context(paramName, paramContinuation);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 664 */       if (paramContinuation.isContinue()) {
/* 665 */         paramContinuation.appendRemainingComponent("");
/*     */       }
/* 667 */       return bool;
/* 668 */     } catch (NamingException namingException) {
/*     */ 
/*     */ 
/*     */       
/* 672 */       namingException.appendRemainingComponent("");
/* 673 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resolve_to_nns_and_continue(Name paramName, Continuation paramContinuation) throws NamingException {
/* 683 */     if (debug > 0) {
/* 684 */       System.out.println("RESOLVE TO NNS AND CONTINUE" + paramName.toString());
/*     */     }
/* 686 */     if (resolve_to_penultimate_context_nns(paramName, paramContinuation)) {
/* 687 */       Object object = a_lookup_nns(paramName.toString(), paramContinuation);
/* 688 */       if (object != null)
/* 689 */         paramContinuation.setContinue(object, paramName, this); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/AtomicContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */