/*     */ package com.sun.jndi.toolkit.url;
/*     */ 
/*     */ import java.net.MalformedURLException;
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
/*     */ import javax.naming.OperationNotSupportedException;
/*     */ import javax.naming.spi.NamingManager;
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
/*     */ public abstract class GenericURLContext
/*     */   implements Context
/*     */ {
/*  51 */   protected Hashtable<String, Object> myEnv = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericURLContext(Hashtable<?, ?> paramHashtable) {
/*  56 */     this
/*  57 */       .myEnv = (paramHashtable == null) ? null : (Hashtable<String, Object>)paramHashtable.clone();
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/*  61 */     this.myEnv = null;
/*     */   }
/*     */   
/*     */   public String getNameInNamespace() throws NamingException {
/*  65 */     return "";
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
/*     */   protected abstract ResolveResult getRootURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Name getURLSuffix(String paramString1, String paramString2) throws NamingException {
/* 118 */     String str = paramString2.substring(paramString1.length());
/* 119 */     if (str.length() == 0) {
/* 120 */       return new CompositeName();
/*     */     }
/*     */     
/* 123 */     if (str.charAt(0) == '/') {
/* 124 */       str = str.substring(1);
/*     */     }
/*     */     
/*     */     try {
/* 128 */       return (new CompositeName()).add(UrlUtil.decode(str));
/* 129 */     } catch (MalformedURLException malformedURLException) {
/* 130 */       throw new InvalidNameException(malformedURLException.getMessage());
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
/*     */   protected String getURLPrefix(String paramString) throws NamingException {
/* 152 */     int i = paramString.indexOf(":");
/*     */     
/* 154 */     if (i < 0) {
/* 155 */       throw new OperationNotSupportedException("Invalid URL: " + paramString);
/*     */     }
/* 157 */     i++;
/*     */     
/* 159 */     if (paramString.startsWith("//", i)) {
/* 160 */       i += 2;
/*     */ 
/*     */       
/* 163 */       int j = paramString.indexOf("/", i);
/* 164 */       if (j >= 0) {
/* 165 */         i = j;
/*     */       } else {
/* 167 */         i = paramString.length();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return paramString.substring(0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean urlEquals(String paramString1, String paramString2) {
/* 182 */     return paramString1.equals(paramString2);
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
/*     */   protected Context getContinuationContext(Name paramName) throws NamingException {
/* 194 */     Object object = lookup(paramName.get(0));
/* 195 */     CannotProceedException cannotProceedException = new CannotProceedException();
/* 196 */     cannotProceedException.setResolvedObj(object);
/* 197 */     cannotProceedException.setEnvironment(this.myEnv);
/* 198 */     return NamingManager.getContinuationContext(cannotProceedException);
/*     */   }
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/* 202 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 203 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 205 */       return context.lookup(resolveResult.getRemainingName());
/*     */     } finally {
/* 207 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/* 212 */     if (paramName.size() == 1) {
/* 213 */       return lookup(paramName.get(0));
/*     */     }
/* 215 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 217 */       return context.lookup(paramName.getSuffix(1));
/*     */     } finally {
/* 219 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 225 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 226 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 228 */       context.bind(resolveResult.getRemainingName(), paramObject);
/*     */     } finally {
/* 230 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 235 */     if (paramName.size() == 1) {
/* 236 */       bind(paramName.get(0), paramObject);
/*     */     } else {
/* 238 */       Context context = getContinuationContext(paramName);
/*     */       try {
/* 240 */         context.bind(paramName.getSuffix(1), paramObject);
/*     */       } finally {
/* 242 */         context.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 248 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 249 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 251 */       context.rebind(resolveResult.getRemainingName(), paramObject);
/*     */     } finally {
/* 253 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 258 */     if (paramName.size() == 1) {
/* 259 */       rebind(paramName.get(0), paramObject);
/*     */     } else {
/* 261 */       Context context = getContinuationContext(paramName);
/*     */       try {
/* 263 */         context.rebind(paramName.getSuffix(1), paramObject);
/*     */       } finally {
/* 265 */         context.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 271 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 272 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 274 */       context.unbind(resolveResult.getRemainingName());
/*     */     } finally {
/* 276 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 281 */     if (paramName.size() == 1) {
/* 282 */       unbind(paramName.get(0));
/*     */     } else {
/* 284 */       Context context = getContinuationContext(paramName);
/*     */       try {
/* 286 */         context.unbind(paramName.getSuffix(1));
/*     */       } finally {
/* 288 */         context.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 294 */     String str1 = getURLPrefix(paramString1);
/* 295 */     String str2 = getURLPrefix(paramString2);
/* 296 */     if (!urlEquals(str1, str2)) {
/* 297 */       throw new OperationNotSupportedException("Renaming using different URL prefixes not supported : " + paramString1 + " " + paramString2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 302 */     ResolveResult resolveResult = getRootURLContext(paramString1, this.myEnv);
/* 303 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 305 */       context.rename(resolveResult.getRemainingName(), getURLSuffix(str2, paramString2));
/*     */     } finally {
/* 307 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 312 */     if (paramName1.size() == 1) {
/* 313 */       if (paramName2.size() != 1) {
/* 314 */         throw new OperationNotSupportedException("Renaming to a Name with more components not supported: " + paramName2);
/*     */       }
/*     */       
/* 317 */       rename(paramName1.get(0), paramName2.get(0));
/*     */     }
/*     */     else {
/*     */       
/* 321 */       if (!urlEquals(paramName1.get(0), paramName2.get(0))) {
/* 322 */         throw new OperationNotSupportedException("Renaming using different URLs as first components not supported: " + paramName1 + " " + paramName2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 327 */       Context context = getContinuationContext(paramName1);
/*     */       try {
/* 329 */         context.rename(paramName1.getSuffix(1), paramName2.getSuffix(1));
/*     */       } finally {
/* 331 */         context.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 337 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 338 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 340 */       return context.list(resolveResult.getRemainingName());
/*     */     } finally {
/* 342 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 347 */     if (paramName.size() == 1) {
/* 348 */       return list(paramName.get(0));
/*     */     }
/* 350 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 352 */       return context.list(paramName.getSuffix(1));
/*     */     } finally {
/* 354 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 361 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 362 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 364 */       return context.listBindings(resolveResult.getRemainingName());
/*     */     } finally {
/* 366 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 371 */     if (paramName.size() == 1) {
/* 372 */       return listBindings(paramName.get(0));
/*     */     }
/* 374 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 376 */       return context.listBindings(paramName.getSuffix(1));
/*     */     } finally {
/* 378 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 384 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 385 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 387 */       context.destroySubcontext(resolveResult.getRemainingName());
/*     */     } finally {
/* 389 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 394 */     if (paramName.size() == 1) {
/* 395 */       destroySubcontext(paramName.get(0));
/*     */     } else {
/* 397 */       Context context = getContinuationContext(paramName);
/*     */       try {
/* 399 */         context.destroySubcontext(paramName.getSuffix(1));
/*     */       } finally {
/* 401 */         context.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 407 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 408 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 410 */       return context.createSubcontext(resolveResult.getRemainingName());
/*     */     } finally {
/* 412 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 417 */     if (paramName.size() == 1) {
/* 418 */       return createSubcontext(paramName.get(0));
/*     */     }
/* 420 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 422 */       return context.createSubcontext(paramName.getSuffix(1));
/*     */     } finally {
/* 424 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 430 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 431 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 433 */       return context.lookupLink(resolveResult.getRemainingName());
/*     */     } finally {
/* 435 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 440 */     if (paramName.size() == 1) {
/* 441 */       return lookupLink(paramName.get(0));
/*     */     }
/* 443 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 445 */       return context.lookupLink(paramName.getSuffix(1));
/*     */     } finally {
/* 447 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 453 */     ResolveResult resolveResult = getRootURLContext(paramString, this.myEnv);
/* 454 */     Context context = (Context)resolveResult.getResolvedObj();
/*     */     try {
/* 456 */       return context.getNameParser(resolveResult.getRemainingName());
/*     */     } finally {
/* 458 */       context.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 463 */     if (paramName.size() == 1) {
/* 464 */       return getNameParser(paramName.get(0));
/*     */     }
/* 466 */     Context context = getContinuationContext(paramName);
/*     */     try {
/* 468 */       return context.getNameParser(paramName.getSuffix(1));
/*     */     } finally {
/* 470 */       context.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 477 */     if (paramString2.equals(""))
/* 478 */       return paramString1; 
/* 479 */     if (paramString1.equals("")) {
/* 480 */       return paramString2;
/*     */     }
/* 482 */     return paramString2 + "/" + paramString1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 487 */     Name name = (Name)paramName2.clone();
/* 488 */     name.addAll(paramName1);
/* 489 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 494 */     if (this.myEnv == null) {
/* 495 */       return null;
/*     */     }
/* 497 */     return this.myEnv.remove(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 502 */     if (this.myEnv == null) {
/* 503 */       this.myEnv = new Hashtable<>(11, 0.75F);
/*     */     }
/* 505 */     return this.myEnv.put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Hashtable<String, Object> getEnvironment() throws NamingException {
/* 510 */     if (this.myEnv == null) {
/* 511 */       return new Hashtable<>(5, 0.75F);
/*     */     }
/* 513 */     return (Hashtable<String, Object>)this.myEnv.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/url/GenericURLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */