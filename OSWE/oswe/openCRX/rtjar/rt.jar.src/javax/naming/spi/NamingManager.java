/*     */ package javax.naming.spi;
/*     */ 
/*     */ import com.sun.naming.internal.FactoryEnumeration;
/*     */ import com.sun.naming.internal.ResourceManager;
/*     */ import com.sun.naming.internal.VersionHelper;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NoInitialContextException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamingManager
/*     */ {
/*  74 */   static final VersionHelper helper = VersionHelper.getVersionHelper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static ObjectFactoryBuilder object_factory_builder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String defaultPkgPrefix = "com.sun.jndi.url";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setObjectFactoryBuilder(ObjectFactoryBuilder paramObjectFactoryBuilder) throws NamingException {
/* 110 */     if (object_factory_builder != null) {
/* 111 */       throw new IllegalStateException("ObjectFactoryBuilder already set");
/*     */     }
/* 113 */     SecurityManager securityManager = System.getSecurityManager();
/* 114 */     if (securityManager != null) {
/* 115 */       securityManager.checkSetFactory();
/*     */     }
/* 117 */     object_factory_builder = paramObjectFactoryBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized ObjectFactoryBuilder getObjectFactoryBuilder() {
/* 124 */     return object_factory_builder;
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
/*     */   static ObjectFactory getObjectFactoryFromReference(Reference paramReference, String paramString) throws IllegalAccessException, InstantiationException, MalformedURLException {
/* 142 */     Class<?> clazz = null;
/*     */ 
/*     */     
/*     */     try {
/* 146 */       clazz = helper.loadClass(paramString);
/* 147 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */ 
/*     */     
/*     */     String str;
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (clazz == null && (
/* 156 */       str = paramReference.getFactoryClassLocation()) != null) {
/*     */       try {
/* 158 */         clazz = helper.loadClass(paramString, str);
/* 159 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     }
/*     */ 
/*     */     
/* 163 */     return (clazz != null) ? (ObjectFactory)clazz.newInstance() : null;
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
/*     */   private static Object createObjectFromFactories(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws Exception {
/* 177 */     FactoryEnumeration factoryEnumeration = ResourceManager.getFactories("java.naming.factory.object", paramHashtable, paramContext);
/*     */ 
/*     */     
/* 180 */     if (factoryEnumeration == null) {
/* 181 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 185 */     Object object = null;
/* 186 */     while (object == null && factoryEnumeration.hasMore()) {
/* 187 */       ObjectFactory objectFactory = (ObjectFactory)factoryEnumeration.next();
/* 188 */       object = objectFactory.getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/*     */     } 
/* 190 */     return object;
/*     */   }
/*     */   
/*     */   private static String getURLScheme(String paramString) {
/* 194 */     int i = paramString.indexOf(':');
/* 195 */     int j = paramString.indexOf('/');
/*     */     
/* 197 */     if (i > 0 && (j == -1 || i < j))
/* 198 */       return paramString.substring(0, i); 
/* 199 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws Exception {
/* 296 */     ObjectFactoryBuilder objectFactoryBuilder = getObjectFactoryBuilder();
/* 297 */     if (objectFactoryBuilder != null) {
/*     */       
/* 299 */       ObjectFactory objectFactory = objectFactoryBuilder.createObjectFactory(paramObject, paramHashtable);
/* 300 */       return objectFactory.getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     Reference reference = null;
/* 306 */     if (paramObject instanceof Reference) {
/* 307 */       reference = (Reference)paramObject;
/* 308 */     } else if (paramObject instanceof Referenceable) {
/* 309 */       reference = ((Referenceable)paramObject).getReference();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (reference != null) {
/* 315 */       String str = reference.getFactoryClassName();
/* 316 */       if (str != null) {
/*     */ 
/*     */         
/* 319 */         ObjectFactory objectFactory = getObjectFactoryFromReference(reference, str);
/* 320 */         if (objectFactory != null) {
/* 321 */           return objectFactory.getObjectInstance(reference, paramName, paramContext, paramHashtable);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 327 */         return paramObject;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       Object object1 = processURLAddrs(reference, paramName, paramContext, paramHashtable);
/* 334 */       if (object1 != null) {
/* 335 */         return object1;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     Object object = createObjectFromFactories(paramObject, paramName, paramContext, paramHashtable);
/* 343 */     return (object != null) ? object : paramObject;
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
/*     */   static Object processURLAddrs(Reference paramReference, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 355 */     for (byte b = 0; b < paramReference.size(); b++) {
/* 356 */       RefAddr refAddr = paramReference.get(b);
/* 357 */       if (refAddr instanceof javax.naming.StringRefAddr && refAddr
/* 358 */         .getType().equalsIgnoreCase("URL")) {
/*     */         
/* 360 */         String str = (String)refAddr.getContent();
/* 361 */         Object object = processURL(str, paramName, paramContext, paramHashtable);
/* 362 */         if (object != null) {
/* 363 */           return object;
/*     */         }
/*     */       } 
/*     */     } 
/* 367 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object processURL(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 377 */     if (paramObject instanceof String) {
/* 378 */       String str1 = (String)paramObject;
/* 379 */       String str2 = getURLScheme(str1);
/* 380 */       if (str2 != null) {
/* 381 */         Object object = getURLObject(str2, paramObject, paramName, paramContext, paramHashtable);
/*     */         
/* 383 */         if (object != null) {
/* 384 */           return object;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 392 */     if (paramObject instanceof String[]) {
/* 393 */       String[] arrayOfString = (String[])paramObject;
/* 394 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 395 */         String str = getURLScheme(arrayOfString[b]);
/* 396 */         if (str != null) {
/* 397 */           Object object = getURLObject(str, paramObject, paramName, paramContext, paramHashtable);
/*     */           
/* 399 */           if (object != null)
/* 400 */             return object; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 404 */     return null;
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
/*     */   static Context getContext(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     Object object;
/* 433 */     if (paramObject instanceof Context)
/*     */     {
/* 435 */       return (Context)paramObject;
/*     */     }
/*     */     
/*     */     try {
/* 439 */       object = getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/* 440 */     } catch (NamingException namingException) {
/* 441 */       throw namingException;
/* 442 */     } catch (Exception exception) {
/* 443 */       NamingException namingException = new NamingException();
/* 444 */       namingException.setRootCause(exception);
/* 445 */       throw namingException;
/*     */     } 
/*     */     
/* 448 */     return (object instanceof Context) ? (Context)object : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Resolver getResolver(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     Object object;
/* 458 */     if (paramObject instanceof Resolver)
/*     */     {
/* 460 */       return (Resolver)paramObject;
/*     */     }
/*     */     
/*     */     try {
/* 464 */       object = getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/* 465 */     } catch (NamingException namingException) {
/* 466 */       throw namingException;
/* 467 */     } catch (Exception exception) {
/* 468 */       NamingException namingException = new NamingException();
/* 469 */       namingException.setRootCause(exception);
/* 470 */       throw namingException;
/*     */     } 
/*     */     
/* 473 */     return (object instanceof Resolver) ? (Resolver)object : null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Context getURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 550 */     Object object = getURLObject(paramString, null, null, null, paramHashtable);
/* 551 */     if (object instanceof Context) {
/* 552 */       return (Context)object;
/*     */     }
/* 554 */     return null;
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
/*     */   private static Object getURLObject(String paramString, Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 592 */     ObjectFactory objectFactory = (ObjectFactory)ResourceManager.getFactory("java.naming.factory.url.pkgs", paramHashtable, paramContext, "." + paramString + "." + paramString + "URLContextFactory", "com.sun.jndi.url");
/*     */ 
/*     */ 
/*     */     
/* 596 */     if (objectFactory == null) {
/* 597 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 601 */       return objectFactory.getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/* 602 */     } catch (NamingException namingException) {
/* 603 */       throw namingException;
/* 604 */     } catch (Exception exception) {
/* 605 */       NamingException namingException = new NamingException();
/* 606 */       namingException.setRootCause(exception);
/* 607 */       throw namingException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 614 */   private static InitialContextFactoryBuilder initctx_factory_builder = null;
/*     */ 
/*     */   
/*     */   public static final String CPE = "java.naming.spi.CannotProceedException";
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized InitialContextFactoryBuilder getInitialContextFactoryBuilder() {
/* 622 */     return initctx_factory_builder;
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
/*     */   public static Context getInitialContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/*     */     InitialContextFactory initialContextFactory;
/* 654 */     InitialContextFactoryBuilder initialContextFactoryBuilder = getInitialContextFactoryBuilder();
/* 655 */     if (initialContextFactoryBuilder == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 660 */       String str = (paramHashtable != null) ? (String)paramHashtable.get("java.naming.factory.initial") : null;
/* 661 */       if (str == null) {
/* 662 */         NoInitialContextException noInitialContextException = new NoInitialContextException("Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 667 */         throw noInitialContextException;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 672 */         initialContextFactory = (InitialContextFactory)helper.loadClass(str).newInstance();
/* 673 */       } catch (Exception exception) {
/* 674 */         NoInitialContextException noInitialContextException = new NoInitialContextException("Cannot instantiate class: " + str);
/*     */ 
/*     */         
/* 677 */         noInitialContextException.setRootCause(exception);
/* 678 */         throw noInitialContextException;
/*     */       } 
/*     */     } else {
/* 681 */       initialContextFactory = initialContextFactoryBuilder.createInitialContextFactory(paramHashtable);
/*     */     } 
/*     */     
/* 684 */     return initialContextFactory.getInitialContext(paramHashtable);
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
/*     */   public static synchronized void setInitialContextFactoryBuilder(InitialContextFactoryBuilder paramInitialContextFactoryBuilder) throws NamingException {
/* 708 */     if (initctx_factory_builder != null) {
/* 709 */       throw new IllegalStateException("InitialContextFactoryBuilder already set");
/*     */     }
/*     */     
/* 712 */     SecurityManager securityManager = System.getSecurityManager();
/* 713 */     if (securityManager != null) {
/* 714 */       securityManager.checkSetFactory();
/*     */     }
/* 716 */     initctx_factory_builder = paramInitialContextFactoryBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasInitialContextFactoryBuilder() {
/* 727 */     return (getInitialContextFactoryBuilder() != null);
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
/*     */   public static Context getContinuationContext(CannotProceedException paramCannotProceedException) throws NamingException {
/* 778 */     Hashtable<?, ?> hashtable = paramCannotProceedException.getEnvironment();
/* 779 */     if (hashtable == null) {
/* 780 */       hashtable = new Hashtable<>(7);
/*     */     } else {
/*     */       
/* 783 */       hashtable = (Hashtable<?, ?>)hashtable.clone();
/*     */     } 
/* 785 */     hashtable.put("java.naming.spi.CannotProceedException", paramCannotProceedException);
/*     */     
/* 787 */     ContinuationContext continuationContext = new ContinuationContext(paramCannotProceedException, hashtable);
/* 788 */     return continuationContext.getTargetContext();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getStateToBind(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 858 */     FactoryEnumeration factoryEnumeration = ResourceManager.getFactories("java.naming.factory.state", paramHashtable, paramContext);
/*     */ 
/*     */     
/* 861 */     if (factoryEnumeration == null) {
/* 862 */       return paramObject;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 867 */     Object object = null;
/* 868 */     while (object == null && factoryEnumeration.hasMore()) {
/* 869 */       StateFactory stateFactory = (StateFactory)factoryEnumeration.next();
/* 870 */       object = stateFactory.getStateToBind(paramObject, paramName, paramContext, paramHashtable);
/*     */     } 
/*     */     
/* 873 */     return (object != null) ? object : paramObject;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/NamingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */