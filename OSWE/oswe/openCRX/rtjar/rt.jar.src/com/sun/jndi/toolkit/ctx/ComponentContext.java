/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
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
/*     */ public abstract class ComponentContext
/*     */   extends PartialCompositeContext
/*     */ {
/*  44 */   private static int debug = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final byte USE_CONTINUATION = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final byte TERMINAL_COMPONENT = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final byte TERMINAL_NNS_COMPONENT = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object c_lookup(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object c_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NamingEnumeration<NameClassPair> c_list(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NamingEnumeration<Binding> c_listBindings(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void c_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void c_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void c_unbind(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void c_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Context c_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void c_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract NameParser c_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ 
/*     */ 
/*     */   
/*     */   protected HeadTail p_parseComponent(Name paramName, Continuation paramContinuation) throws NamingException {
/*     */     boolean bool;
/*     */     Name name1;
/*     */     Name name2;
/* 108 */     if (paramName.isEmpty() || paramName.get(0).equals("")) {
/* 109 */       bool = false;
/*     */     } else {
/* 111 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     if (paramName instanceof CompositeName) {
/* 116 */       name1 = paramName.getPrefix(bool);
/* 117 */       name2 = paramName.getSuffix(bool);
/*     */     } else {
/*     */       
/* 120 */       name1 = (new CompositeName()).add(paramName.toString());
/* 121 */       name2 = null;
/*     */     } 
/*     */     
/* 124 */     if (debug > 2) {
/* 125 */       System.err.println("ORIG: " + paramName);
/* 126 */       System.err.println("PREFIX: " + paramName);
/* 127 */       System.err.println("SUFFIX: " + null);
/*     */     } 
/* 129 */     return new HeadTail(name1, name2);
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
/*     */     try {
/* 168 */       final Object obj = c_lookup(paramName, paramContinuation);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       if (object != null && getClass().isInstance(object)) {
/*     */ 
/*     */ 
/*     */         
/* 177 */         paramContinuation.setContinueNNS(object, paramName, this);
/* 178 */         return null;
/*     */       } 
/* 180 */       if (object != null && !(object instanceof Context)) {
/*     */ 
/*     */         
/* 183 */         RefAddr refAddr = new RefAddr("nns") {
/*     */             public Object getContent() {
/* 185 */               return obj;
/*     */             }
/*     */             
/*     */             private static final long serialVersionUID = -8831204798861786362L;
/*     */           };
/* 190 */         Reference reference = new Reference("java.lang.Object", refAddr);
/*     */ 
/*     */         
/* 193 */         CompositeName compositeName = (CompositeName)paramName.clone();
/* 194 */         compositeName.add("");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 201 */         paramContinuation.setContinue(reference, compositeName, this);
/* 202 */         return null;
/*     */       } 
/*     */       
/* 205 */       return object;
/*     */     
/*     */     }
/* 208 */     catch (NamingException namingException) {
/* 209 */       namingException.appendRemainingComponent("");
/* 210 */       throw namingException;
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
/*     */   protected Object c_lookup_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 228 */     c_processJunction_nns(paramName, paramContinuation);
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object c_lookupLink_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 234 */     c_processJunction_nns(paramName, paramContinuation);
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> c_list_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 240 */     c_processJunction_nns(paramName, paramContinuation);
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> c_listBindings_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 246 */     c_processJunction_nns(paramName, paramContinuation);
/* 247 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_bind_nns(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 252 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_rebind_nns(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 257 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_unbind_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 262 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Context c_createSubcontext_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 267 */     c_processJunction_nns(paramName, paramContinuation);
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c_destroySubcontext_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 273 */     c_processJunction_nns(paramName, paramContinuation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_rename_nns(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/* 279 */     c_processJunction_nns(paramName1, paramContinuation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NameParser c_getNameParser_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 284 */     c_processJunction_nns(paramName, paramContinuation);
/* 285 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c_processJunction_nns(Name paramName, Continuation paramContinuation) throws NamingException {
/* 321 */     if (paramName.isEmpty()) {
/*     */       
/* 323 */       RefAddr refAddr = new RefAddr("nns") {
/*     */           public Object getContent() {
/* 325 */             return ComponentContext.this;
/*     */           }
/*     */           
/*     */           private static final long serialVersionUID = -1389472957988053402L;
/*     */         };
/* 330 */       Reference reference = new Reference("java.lang.Object", refAddr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       paramContinuation.setContinue(reference, _NNS_NAME, this);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 342 */       Object object = c_lookup(paramName, paramContinuation);
/* 343 */       if (paramContinuation.isContinue()) {
/* 344 */         paramContinuation.appendRemainingComponent("");
/*     */       } else {
/* 346 */         paramContinuation.setContinueNNS(object, paramName, this);
/*     */       } 
/* 348 */     } catch (NamingException namingException) {
/* 349 */       namingException.appendRemainingComponent("");
/* 350 */       throw namingException;
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
/*     */   protected HeadTail p_resolveIntermediate(Name paramName, Continuation paramContinuation) throws NamingException {
/* 373 */     byte b = 1;
/* 374 */     paramContinuation.setSuccess();
/* 375 */     HeadTail headTail = p_parseComponent(paramName, paramContinuation);
/* 376 */     Name name1 = headTail.getTail();
/* 377 */     Name name2 = headTail.getHead();
/*     */     
/* 379 */     if (name1 == null || name1.isEmpty()) {
/*     */       
/* 381 */       b = 2;
/* 382 */     } else if (!name1.get(0).equals("")) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 397 */         Object object = c_resolveIntermediate_nns(name2, paramContinuation);
/*     */         
/* 399 */         if (object != null) {
/* 400 */           paramContinuation.setContinue(object, name2, this, name1);
/* 401 */         } else if (paramContinuation.isContinue()) {
/* 402 */           checkAndAdjustRemainingName(paramContinuation.getRemainingName());
/* 403 */           paramContinuation.appendRemainingName(name1);
/*     */         } 
/* 405 */       } catch (NamingException namingException) {
/* 406 */         checkAndAdjustRemainingName(namingException.getRemainingName());
/* 407 */         namingException.appendRemainingName(name1);
/* 408 */         throw namingException;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 415 */     else if (name1.size() == 1) {
/* 416 */       b = 3;
/*     */     }
/* 418 */     else if (name2.isEmpty() || isAllEmpty(name1)) {
/*     */       
/* 420 */       Name name = name1.getSuffix(1);
/*     */       try {
/* 422 */         Object object = c_lookup_nns(name2, paramContinuation);
/*     */         
/* 424 */         if (object != null) {
/* 425 */           paramContinuation.setContinue(object, name2, this, name);
/* 426 */         } else if (paramContinuation.isContinue()) {
/* 427 */           paramContinuation.appendRemainingName(name);
/*     */         }
/*     */       
/*     */       }
/* 431 */       catch (NamingException namingException) {
/* 432 */         namingException.appendRemainingName(name);
/* 433 */         throw namingException;
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 439 */         Object object = c_resolveIntermediate_nns(name2, paramContinuation);
/*     */         
/* 441 */         if (object != null) {
/* 442 */           paramContinuation.setContinue(object, name2, this, name1);
/* 443 */         } else if (paramContinuation.isContinue()) {
/* 444 */           checkAndAdjustRemainingName(paramContinuation.getRemainingName());
/* 445 */           paramContinuation.appendRemainingName(name1);
/*     */         } 
/* 447 */       } catch (NamingException namingException) {
/* 448 */         checkAndAdjustRemainingName(namingException.getRemainingName());
/* 449 */         namingException.appendRemainingName(name1);
/* 450 */         throw namingException;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 455 */     headTail.setStatus(b);
/* 456 */     return headTail;
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
/*     */   void checkAndAdjustRemainingName(Name paramName) throws InvalidNameException {
/*     */     int i;
/* 470 */     if (paramName != null && (i = paramName.size()) > 1 && paramName
/* 471 */       .get(i - 1).equals("")) {
/* 472 */       paramName.remove(i - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAllEmpty(Name paramName) {
/* 478 */     int i = paramName.size();
/* 479 */     for (byte b = 0; b < i; b++) {
/* 480 */       if (!paramName.get(b).equals("")) {
/* 481 */         return false;
/*     */       }
/*     */     } 
/* 484 */     return true;
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
/*     */   protected ResolveResult p_resolveToClass(Name paramName, Class<?> paramClass, Continuation paramContinuation) throws NamingException {
/*     */     Object object;
/* 500 */     if (paramClass.isInstance(this)) {
/* 501 */       paramContinuation.setSuccess();
/* 502 */       return new ResolveResult(this, paramName);
/*     */     } 
/*     */     
/* 505 */     ResolveResult resolveResult = null;
/* 506 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 507 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 509 */         object = p_lookup(paramName, paramContinuation);
/* 510 */         if (!paramContinuation.isContinue() && paramClass.isInstance(object)) {
/* 511 */           resolveResult = new ResolveResult(object, _EMPTY_NAME);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/* 516 */         paramContinuation.setSuccess();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     return resolveResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object p_lookup(Name paramName, Continuation paramContinuation) throws NamingException {
/* 530 */     Object object = null;
/* 531 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 532 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 534 */         object = c_lookup_nns(headTail.getHead(), paramContinuation);
/* 535 */         if (object instanceof javax.naming.LinkRef) {
/* 536 */           paramContinuation.setContinue(object, headTail.getHead(), this);
/* 537 */           object = null;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 2:
/* 542 */         object = c_lookup(headTail.getHead(), paramContinuation);
/* 543 */         if (object instanceof javax.naming.LinkRef) {
/* 544 */           paramContinuation.setContinue(object, headTail.getHead(), this);
/* 545 */           object = null;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<NameClassPair> p_list(Name paramName, Continuation paramContinuation) throws NamingException {
/* 559 */     NamingEnumeration<NameClassPair> namingEnumeration = null;
/* 560 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 561 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 563 */         if (debug > 0)
/* 564 */           System.out.println("c_list_nns(" + headTail.getHead() + ")"); 
/* 565 */         namingEnumeration = c_list_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 569 */         if (debug > 0)
/* 570 */           System.out.println("c_list(" + headTail.getHead() + ")"); 
/* 571 */         namingEnumeration = c_list(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NamingEnumeration<Binding> p_listBindings(Name paramName, Continuation paramContinuation) throws NamingException {
/* 584 */     NamingEnumeration<Binding> namingEnumeration = null;
/* 585 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 586 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 588 */         namingEnumeration = c_listBindings_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 592 */         namingEnumeration = c_listBindings(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void p_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 605 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 606 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 608 */         c_bind_nns(headTail.getHead(), paramObject, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 612 */         c_bind(headTail.getHead(), paramObject, paramContinuation);
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
/*     */   protected void p_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/* 624 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 625 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 627 */         c_rebind_nns(headTail.getHead(), paramObject, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 631 */         c_rebind(headTail.getHead(), paramObject, paramContinuation);
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
/*     */   protected void p_unbind(Name paramName, Continuation paramContinuation) throws NamingException {
/* 643 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 644 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 646 */         c_unbind_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 650 */         c_unbind(headTail.getHead(), paramContinuation);
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
/*     */   protected void p_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 662 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 663 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 665 */         c_destroySubcontext_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 669 */         c_destroySubcontext(headTail.getHead(), paramContinuation);
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
/*     */   protected Context p_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/* 681 */     Context context = null;
/* 682 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 683 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 685 */         context = c_createSubcontext_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 689 */         context = c_createSubcontext(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 697 */     return context;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void p_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/* 702 */     HeadTail headTail = p_resolveIntermediate(paramName1, paramContinuation);
/* 703 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 705 */         c_rename_nns(headTail.getHead(), paramName2, paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 709 */         c_rename(headTail.getHead(), paramName2, paramContinuation);
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
/*     */   protected NameParser p_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException {
/* 721 */     NameParser nameParser = null;
/* 722 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 723 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 725 */         nameParser = c_getNameParser_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 729 */         nameParser = c_getNameParser(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 737 */     return nameParser;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object p_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException {
/* 742 */     Object object = null;
/* 743 */     HeadTail headTail = p_resolveIntermediate(paramName, paramContinuation);
/* 744 */     switch (headTail.getStatus()) {
/*     */       case 3:
/* 746 */         object = c_lookupLink_nns(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */       
/*     */       case 2:
/* 750 */         object = c_lookupLink(headTail.getHead(), paramContinuation);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 758 */     return object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/ComponentContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */