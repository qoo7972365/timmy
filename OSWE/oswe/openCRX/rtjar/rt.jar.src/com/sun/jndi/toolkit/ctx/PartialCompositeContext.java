/*     */ package com.sun.jndi.toolkit.ctx;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.spi.NamingManager;
/*     */ import javax.naming.spi.ResolveResult;
/*     */ import javax.naming.spi.Resolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PartialCompositeContext
/*     */   implements Context, Resolver
/*     */ {
/*     */   protected static final int _PARTIAL = 1;
/*     */   protected static final int _COMPONENT = 2;
/*     */   protected static final int _ATOMIC = 3;
/*  60 */   protected int _contextType = 1;
/*     */   
/*  62 */   static final CompositeName _EMPTY_NAME = new CompositeName();
/*     */   static CompositeName _NNS_NAME;
/*     */   
/*     */   static {
/*     */     try {
/*  67 */       _NNS_NAME = new CompositeName("/");
/*  68 */     } catch (InvalidNameException invalidNameException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Hashtable<?, ?> p_getEnvironment() throws NamingException {
/* 119 */     return getEnvironment();
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
/*     */   public ResolveResult resolveToClass(String paramString, Class<? extends Context> paramClass) throws NamingException {
/* 132 */     return resolveToClass(new CompositeName(paramString), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResolveResult resolveToClass(Name paramName, Class<? extends Context> paramClass) throws NamingException {
/*     */     ResolveResult resolveResult;
/* 139 */     PartialCompositeContext partialCompositeContext = this;
/* 140 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 141 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 143 */     Name name = paramName;
/*     */     
/*     */     try {
/* 146 */       resolveResult = partialCompositeContext.p_resolveToClass(name, paramClass, continuation);
/* 147 */       while (continuation.isContinue()) {
/* 148 */         name = continuation.getRemainingName();
/* 149 */         partialCompositeContext = getPCContext(continuation);
/* 150 */         resolveResult = partialCompositeContext.p_resolveToClass(name, paramClass, continuation);
/*     */       } 
/* 152 */     } catch (CannotProceedException cannotProceedException) {
/* 153 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 154 */       if (!(context instanceof Resolver)) {
/* 155 */         throw cannotProceedException;
/*     */       }
/* 157 */       resolveResult = ((Resolver)context).resolveToClass(cannotProceedException.getRemainingName(), paramClass);
/*     */     } 
/*     */     
/* 160 */     return resolveResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/* 166 */     return lookup(new CompositeName(paramString));
/*     */   }
/*     */   public Object lookup(Name paramName) throws NamingException {
/*     */     Object object;
/* 170 */     PartialCompositeContext partialCompositeContext = this;
/* 171 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 172 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 174 */     Name name = paramName;
/*     */     
/*     */     try {
/* 177 */       object = partialCompositeContext.p_lookup(name, continuation);
/* 178 */       while (continuation.isContinue()) {
/* 179 */         name = continuation.getRemainingName();
/* 180 */         partialCompositeContext = getPCContext(continuation);
/* 181 */         object = partialCompositeContext.p_lookup(name, continuation);
/*     */       } 
/* 183 */     } catch (CannotProceedException cannotProceedException) {
/* 184 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 185 */       object = context.lookup(cannotProceedException.getRemainingName());
/*     */     } 
/* 187 */     return object;
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 191 */     bind(new CompositeName(paramString), paramObject);
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 195 */     PartialCompositeContext partialCompositeContext = this;
/* 196 */     Name name = paramName;
/* 197 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 198 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 201 */       partialCompositeContext.p_bind(name, paramObject, continuation);
/* 202 */       while (continuation.isContinue()) {
/* 203 */         name = continuation.getRemainingName();
/* 204 */         partialCompositeContext = getPCContext(continuation);
/* 205 */         partialCompositeContext.p_bind(name, paramObject, continuation);
/*     */       } 
/* 207 */     } catch (CannotProceedException cannotProceedException) {
/* 208 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 209 */       context.bind(cannotProceedException.getRemainingName(), paramObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 214 */     rebind(new CompositeName(paramString), paramObject);
/*     */   }
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 217 */     PartialCompositeContext partialCompositeContext = this;
/* 218 */     Name name = paramName;
/* 219 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 220 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 223 */       partialCompositeContext.p_rebind(name, paramObject, continuation);
/* 224 */       while (continuation.isContinue()) {
/* 225 */         name = continuation.getRemainingName();
/* 226 */         partialCompositeContext = getPCContext(continuation);
/* 227 */         partialCompositeContext.p_rebind(name, paramObject, continuation);
/*     */       } 
/* 229 */     } catch (CannotProceedException cannotProceedException) {
/* 230 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 231 */       context.rebind(cannotProceedException.getRemainingName(), paramObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 236 */     unbind(new CompositeName(paramString));
/*     */   }
/*     */   public void unbind(Name paramName) throws NamingException {
/* 239 */     PartialCompositeContext partialCompositeContext = this;
/* 240 */     Name name = paramName;
/* 241 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 242 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 245 */       partialCompositeContext.p_unbind(name, continuation);
/* 246 */       while (continuation.isContinue()) {
/* 247 */         name = continuation.getRemainingName();
/* 248 */         partialCompositeContext = getPCContext(continuation);
/* 249 */         partialCompositeContext.p_unbind(name, continuation);
/*     */       } 
/* 251 */     } catch (CannotProceedException cannotProceedException) {
/* 252 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 253 */       context.unbind(cannotProceedException.getRemainingName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 258 */     rename(new CompositeName(paramString1), new CompositeName(paramString2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 263 */     PartialCompositeContext partialCompositeContext = this;
/* 264 */     Name name = paramName1;
/* 265 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 266 */     Continuation continuation = new Continuation(paramName1, hashtable);
/*     */     
/*     */     try {
/* 269 */       partialCompositeContext.p_rename(name, paramName2, continuation);
/* 270 */       while (continuation.isContinue()) {
/* 271 */         name = continuation.getRemainingName();
/* 272 */         partialCompositeContext = getPCContext(continuation);
/* 273 */         partialCompositeContext.p_rename(name, paramName2, continuation);
/*     */       } 
/* 275 */     } catch (CannotProceedException cannotProceedException) {
/* 276 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 277 */       if (cannotProceedException.getRemainingNewName() != null)
/*     */       {
/* 279 */         paramName2 = cannotProceedException.getRemainingNewName();
/*     */       }
/* 281 */       context.rename(cannotProceedException.getRemainingName(), paramName2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 288 */     return list(new CompositeName(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/*     */     NamingEnumeration<NameClassPair> namingEnumeration;
/* 294 */     PartialCompositeContext partialCompositeContext = this;
/* 295 */     Name name = paramName;
/*     */     
/* 297 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 298 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 301 */       namingEnumeration = partialCompositeContext.p_list(name, continuation);
/* 302 */       while (continuation.isContinue()) {
/* 303 */         name = continuation.getRemainingName();
/* 304 */         partialCompositeContext = getPCContext(continuation);
/* 305 */         namingEnumeration = partialCompositeContext.p_list(name, continuation);
/*     */       } 
/* 307 */     } catch (CannotProceedException cannotProceedException) {
/* 308 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 309 */       namingEnumeration = context.list(cannotProceedException.getRemainingName());
/*     */     } 
/* 311 */     return namingEnumeration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 317 */     return listBindings(new CompositeName(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/*     */     NamingEnumeration<Binding> namingEnumeration;
/* 323 */     PartialCompositeContext partialCompositeContext = this;
/* 324 */     Name name = paramName;
/*     */     
/* 326 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 327 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 330 */       namingEnumeration = partialCompositeContext.p_listBindings(name, continuation);
/* 331 */       while (continuation.isContinue()) {
/* 332 */         name = continuation.getRemainingName();
/* 333 */         partialCompositeContext = getPCContext(continuation);
/* 334 */         namingEnumeration = partialCompositeContext.p_listBindings(name, continuation);
/*     */       } 
/* 336 */     } catch (CannotProceedException cannotProceedException) {
/* 337 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 338 */       namingEnumeration = context.listBindings(cannotProceedException.getRemainingName());
/*     */     } 
/* 340 */     return namingEnumeration;
/*     */   }
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 344 */     destroySubcontext(new CompositeName(paramString));
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 348 */     PartialCompositeContext partialCompositeContext = this;
/* 349 */     Name name = paramName;
/* 350 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 351 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 354 */       partialCompositeContext.p_destroySubcontext(name, continuation);
/* 355 */       while (continuation.isContinue()) {
/* 356 */         name = continuation.getRemainingName();
/* 357 */         partialCompositeContext = getPCContext(continuation);
/* 358 */         partialCompositeContext.p_destroySubcontext(name, continuation);
/*     */       } 
/* 360 */     } catch (CannotProceedException cannotProceedException) {
/* 361 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 362 */       context.destroySubcontext(cannotProceedException.getRemainingName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 367 */     return createSubcontext(new CompositeName(paramString));
/*     */   }
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/*     */     Context context;
/* 371 */     PartialCompositeContext partialCompositeContext = this;
/* 372 */     Name name = paramName;
/*     */     
/* 374 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 375 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 378 */       context = partialCompositeContext.p_createSubcontext(name, continuation);
/* 379 */       while (continuation.isContinue()) {
/* 380 */         name = continuation.getRemainingName();
/* 381 */         partialCompositeContext = getPCContext(continuation);
/* 382 */         context = partialCompositeContext.p_createSubcontext(name, continuation);
/*     */       } 
/* 384 */     } catch (CannotProceedException cannotProceedException) {
/* 385 */       Context context1 = NamingManager.getContinuationContext(cannotProceedException);
/* 386 */       context = context1.createSubcontext(cannotProceedException.getRemainingName());
/*     */     } 
/* 388 */     return context;
/*     */   }
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 392 */     return lookupLink(new CompositeName(paramString));
/*     */   }
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/*     */     Object object;
/* 396 */     PartialCompositeContext partialCompositeContext = this;
/* 397 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 398 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/* 400 */     Name name = paramName;
/*     */     
/*     */     try {
/* 403 */       object = partialCompositeContext.p_lookupLink(name, continuation);
/* 404 */       while (continuation.isContinue()) {
/* 405 */         name = continuation.getRemainingName();
/* 406 */         partialCompositeContext = getPCContext(continuation);
/* 407 */         object = partialCompositeContext.p_lookupLink(name, continuation);
/*     */       } 
/* 409 */     } catch (CannotProceedException cannotProceedException) {
/* 410 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 411 */       object = context.lookupLink(cannotProceedException.getRemainingName());
/*     */     } 
/* 413 */     return object;
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 417 */     return getNameParser(new CompositeName(paramString));
/*     */   }
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/*     */     NameParser nameParser;
/* 421 */     PartialCompositeContext partialCompositeContext = this;
/* 422 */     Name name = paramName;
/*     */     
/* 424 */     Hashtable<?, ?> hashtable = p_getEnvironment();
/* 425 */     Continuation continuation = new Continuation(paramName, hashtable);
/*     */     
/*     */     try {
/* 428 */       nameParser = partialCompositeContext.p_getNameParser(name, continuation);
/* 429 */       while (continuation.isContinue()) {
/* 430 */         name = continuation.getRemainingName();
/* 431 */         partialCompositeContext = getPCContext(continuation);
/* 432 */         nameParser = partialCompositeContext.p_getNameParser(name, continuation);
/*     */       } 
/* 434 */     } catch (CannotProceedException cannotProceedException) {
/* 435 */       Context context = NamingManager.getContinuationContext(cannotProceedException);
/* 436 */       nameParser = context.getNameParser(cannotProceedException.getRemainingName());
/*     */     } 
/* 438 */     return nameParser;
/*     */   }
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 443 */     Name name = composeName(new CompositeName(paramString1), new CompositeName(paramString2));
/*     */     
/* 445 */     return name.toString();
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
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 465 */     Name name = (Name)paramName2.clone();
/* 466 */     if (paramName1 == null) {
/* 467 */       return name;
/*     */     }
/* 469 */     name.addAll(paramName1);
/*     */ 
/*     */     
/* 472 */     String str = (String)p_getEnvironment().get("java.naming.provider.compose.elideEmpty");
/* 473 */     if (str == null || !str.equalsIgnoreCase("true")) {
/* 474 */       return name;
/*     */     }
/*     */     
/* 477 */     int i = paramName2.size();
/*     */     
/* 479 */     if (!allEmpty(paramName2) && !allEmpty(paramName1)) {
/* 480 */       if (name.get(i - 1).equals("")) {
/* 481 */         name.remove(i - 1);
/* 482 */       } else if (name.get(i).equals("")) {
/* 483 */         name.remove(i);
/*     */       } 
/*     */     }
/* 486 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean allEmpty(Name paramName) {
/* 496 */     Enumeration<String> enumeration = paramName.getAll();
/* 497 */     while (enumeration.hasMoreElements()) {
/* 498 */       if (!((String)enumeration.nextElement()).isEmpty()) {
/* 499 */         return false;
/*     */       }
/*     */     } 
/* 502 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static PartialCompositeContext getPCContext(Continuation paramContinuation) throws NamingException {
/* 512 */     Object object = paramContinuation.getResolvedObj();
/* 513 */     Object object1 = null;
/*     */     
/* 515 */     if (object instanceof PartialCompositeContext)
/*     */     {
/*     */       
/* 518 */       return (PartialCompositeContext)object;
/*     */     }
/* 520 */     throw paramContinuation.fillInException(new CannotProceedException());
/*     */   }
/*     */   
/*     */   protected abstract ResolveResult p_resolveToClass(Name paramName, Class<?> paramClass, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract Object p_lookup(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract Object p_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<NameClassPair> p_list(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NamingEnumeration<Binding> p_listBindings(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_unbind(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract Context p_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract void p_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException;
/*     */   
/*     */   protected abstract NameParser p_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/ctx/PartialCompositeContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */