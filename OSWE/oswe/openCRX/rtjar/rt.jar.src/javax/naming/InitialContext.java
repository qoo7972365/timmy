/*     */ package javax.naming;
/*     */ 
/*     */ import com.sun.naming.internal.ResourceManager;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.spi.NamingManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InitialContext
/*     */   implements Context
/*     */ {
/* 141 */   protected Hashtable<Object, Object> myProps = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   protected Context defaultInitCtx = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean gotDefault = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InitialContext(boolean paramBoolean) throws NamingException {
/* 177 */     if (!paramBoolean) {
/* 178 */       init(null);
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
/*     */   public InitialContext() throws NamingException {
/* 192 */     init(null);
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
/*     */   public InitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/* 213 */     if (paramHashtable != null) {
/* 214 */       paramHashtable = (Hashtable<?, ?>)paramHashtable.clone();
/*     */     }
/* 216 */     init(paramHashtable);
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
/*     */   protected void init(Hashtable<?, ?> paramHashtable) throws NamingException {
/* 239 */     this
/* 240 */       .myProps = (Hashtable)ResourceManager.getInitialEnvironment(paramHashtable);
/*     */     
/* 242 */     if (this.myProps.get("java.naming.factory.initial") != null)
/*     */     {
/* 244 */       getDefaultInitCtx();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T doLookup(Name paramName) throws NamingException {
/* 274 */     return (T)(new InitialContext()).lookup(paramName);
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
/*     */   public static <T> T doLookup(String paramString) throws NamingException {
/* 290 */     return (T)(new InitialContext()).lookup(paramString);
/*     */   }
/*     */   
/*     */   private static String getURLScheme(String paramString) {
/* 294 */     int i = paramString.indexOf(':');
/* 295 */     int j = paramString.indexOf('/');
/*     */     
/* 297 */     if (i > 0 && (j == -1 || i < j))
/* 298 */       return paramString.substring(0, i); 
/* 299 */     return null;
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
/*     */   protected Context getDefaultInitCtx() throws NamingException {
/* 312 */     if (!this.gotDefault) {
/* 313 */       this.defaultInitCtx = NamingManager.getInitialContext(this.myProps);
/* 314 */       this.gotDefault = true;
/*     */     } 
/* 316 */     if (this.defaultInitCtx == null) {
/* 317 */       throw new NoInitialContextException();
/*     */     }
/* 319 */     return this.defaultInitCtx;
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
/*     */   protected Context getURLOrDefaultInitCtx(String paramString) throws NamingException {
/* 340 */     if (NamingManager.hasInitialContextFactoryBuilder()) {
/* 341 */       return getDefaultInitCtx();
/*     */     }
/* 343 */     String str = getURLScheme(paramString);
/* 344 */     if (str != null) {
/* 345 */       Context context = NamingManager.getURLContext(str, this.myProps);
/* 346 */       if (context != null) {
/* 347 */         return context;
/*     */       }
/*     */     } 
/* 350 */     return getDefaultInitCtx();
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
/*     */   protected Context getURLOrDefaultInitCtx(Name paramName) throws NamingException {
/* 397 */     if (NamingManager.hasInitialContextFactoryBuilder()) {
/* 398 */       return getDefaultInitCtx();
/*     */     }
/* 400 */     if (paramName.size() > 0) {
/* 401 */       String str1 = paramName.get(0);
/* 402 */       String str2 = getURLScheme(str1);
/* 403 */       if (str2 != null) {
/* 404 */         Context context = NamingManager.getURLContext(str2, this.myProps);
/* 405 */         if (context != null) {
/* 406 */           return context;
/*     */         }
/*     */       } 
/*     */     } 
/* 410 */     return getDefaultInitCtx();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/* 417 */     return getURLOrDefaultInitCtx(paramString).lookup(paramString);
/*     */   }
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/* 421 */     return getURLOrDefaultInitCtx(paramName).lookup(paramName);
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/* 425 */     getURLOrDefaultInitCtx(paramString).bind(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/* 429 */     getURLOrDefaultInitCtx(paramName).bind(paramName, paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/* 433 */     getURLOrDefaultInitCtx(paramString).rebind(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/* 437 */     getURLOrDefaultInitCtx(paramName).rebind(paramName, paramObject);
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws NamingException {
/* 441 */     getURLOrDefaultInitCtx(paramString).unbind(paramString);
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/* 445 */     getURLOrDefaultInitCtx(paramName).unbind(paramName);
/*     */   }
/*     */   
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 449 */     getURLOrDefaultInitCtx(paramString1).rename(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 455 */     getURLOrDefaultInitCtx(paramName1).rename(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 461 */     return getURLOrDefaultInitCtx(paramString).list(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 467 */     return getURLOrDefaultInitCtx(paramName).list(paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 472 */     return getURLOrDefaultInitCtx(paramString).listBindings(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 477 */     return getURLOrDefaultInitCtx(paramName).listBindings(paramName);
/*     */   }
/*     */   
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 481 */     getURLOrDefaultInitCtx(paramString).destroySubcontext(paramString);
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 485 */     getURLOrDefaultInitCtx(paramName).destroySubcontext(paramName);
/*     */   }
/*     */   
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 489 */     return getURLOrDefaultInitCtx(paramString).createSubcontext(paramString);
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 493 */     return getURLOrDefaultInitCtx(paramName).createSubcontext(paramName);
/*     */   }
/*     */   
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 497 */     return getURLOrDefaultInitCtx(paramString).lookupLink(paramString);
/*     */   }
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 501 */     return getURLOrDefaultInitCtx(paramName).lookupLink(paramName);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 505 */     return getURLOrDefaultInitCtx(paramString).getNameParser(paramString);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 509 */     return getURLOrDefaultInitCtx(paramName).getNameParser(paramName);
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
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 521 */     return paramString1;
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
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 534 */     return (Name)paramName1.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 539 */     this.myProps.put(paramString, paramObject);
/* 540 */     return getDefaultInitCtx().addToEnvironment(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 545 */     this.myProps.remove(paramString);
/* 546 */     return getDefaultInitCtx().removeFromEnvironment(paramString);
/*     */   }
/*     */   
/*     */   public Hashtable<?, ?> getEnvironment() throws NamingException {
/* 550 */     return getDefaultInitCtx().getEnvironment();
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/* 554 */     this.myProps = null;
/* 555 */     if (this.defaultInitCtx != null) {
/* 556 */       this.defaultInitCtx.close();
/* 557 */       this.defaultInitCtx = null;
/*     */     } 
/* 559 */     this.gotDefault = false;
/*     */   }
/*     */   
/*     */   public String getNameInNamespace() throws NamingException {
/* 563 */     return getDefaultInitCtx().getNameInNamespace();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/InitialContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */