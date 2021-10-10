/*     */ package com.sun.naming.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResourceManager
/*     */ {
/*     */   private static final String PROVIDER_RESOURCE_FILE_NAME = "jndiprovider.properties";
/*     */   private static final String APP_RESOURCE_FILE_NAME = "jndi.properties";
/*     */   private static final String JRELIB_PROPERTY_FILE_NAME = "jndi.properties";
/*     */   private static final String DISABLE_APP_RESOURCE_FILES = "com.sun.naming.disable.app.resource.files";
/*  80 */   private static final String[] listProperties = new String[] { "java.naming.factory.object", "java.naming.factory.url.pkgs", "java.naming.factory.state", "java.naming.factory.control" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final VersionHelper helper = VersionHelper.getVersionHelper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final WeakHashMap<Object, Hashtable<? super String, Object>> propertiesCache = new WeakHashMap<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static final WeakHashMap<ClassLoader, Map<String, List<NamedWeakReference<Object>>>> factoryCache = new WeakHashMap<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private static final WeakHashMap<ClassLoader, Map<String, WeakReference<Object>>> urlFactoryCache = new WeakHashMap<>(11);
/* 126 */   private static final WeakReference<Object> NO_FACTORY = new WeakReference(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AppletParameter
/*     */   {
/* 134 */     private static final Class<?> clazz = getClass("java.applet.Applet");
/*     */     
/* 136 */     private static final Method getMethod = getMethod(clazz, "getParameter", new Class[] { String.class });
/*     */     private static Class<?> getClass(String param1String) {
/*     */       try {
/* 139 */         return Class.forName(param1String, true, null);
/* 140 */       } catch (ClassNotFoundException classNotFoundException) {
/* 141 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/* 148 */       if (param1Class != null) {
/*     */         try {
/* 150 */           return param1Class.getMethod(param1String, param1VarArgs);
/* 151 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 152 */           throw new AssertionError(noSuchMethodException);
/*     */         } 
/*     */       }
/* 155 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Object get(Object param1Object, String param1String) {
/* 164 */       if (clazz == null || !clazz.isInstance(param1Object))
/* 165 */         throw new ClassCastException(param1Object.getClass().getName()); 
/*     */       try {
/* 167 */         return getMethod.invoke(param1Object, new Object[] { param1String });
/* 168 */       } catch (InvocationTargetException|IllegalAccessException invocationTargetException) {
/*     */         
/* 170 */         throw new AssertionError(invocationTargetException);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static Hashtable<?, ?> getInitialEnvironment(Hashtable<?, ?> paramHashtable) throws NamingException {
/* 203 */     String[] arrayOfString1 = VersionHelper.PROPS;
/* 204 */     if (paramHashtable == null) {
/* 205 */       paramHashtable = new Hashtable<>(11);
/*     */     }
/* 207 */     Object object = paramHashtable.get("java.naming.applet");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     String[] arrayOfString2 = helper.getJndiProperties();
/* 217 */     for (byte b = 0; b < arrayOfString1.length; b++) {
/* 218 */       Object object1 = paramHashtable.get(arrayOfString1[b]);
/* 219 */       if (object1 == null) {
/* 220 */         if (object != null) {
/* 221 */           object1 = AppletParameter.get(object, arrayOfString1[b]);
/*     */         }
/* 223 */         if (object1 == null)
/*     */         {
/*     */ 
/*     */           
/* 227 */           object1 = (arrayOfString2 != null) ? arrayOfString2[b] : helper.getJndiProperty(b);
/*     */         }
/* 229 */         if (object1 != null) {
/* 230 */           paramHashtable.put(arrayOfString1[b], object1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 237 */     String str = (String)paramHashtable.get("com.sun.naming.disable.app.resource.files");
/* 238 */     if (str != null && str.equalsIgnoreCase("true")) {
/* 239 */       return paramHashtable;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 244 */     mergeTables((Hashtable)paramHashtable, getApplicationResources());
/* 245 */     return paramHashtable;
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
/*     */   public static String getProperty(String paramString, Hashtable<?, ?> paramHashtable, Context paramContext, boolean paramBoolean) throws NamingException {
/* 272 */     String str1 = (paramHashtable != null) ? (String)paramHashtable.get(paramString) : null;
/* 273 */     if (paramContext == null || (str1 != null && !paramBoolean))
/*     */     {
/* 275 */       return str1;
/*     */     }
/* 277 */     String str2 = (String)getProviderResource(paramContext).get(paramString);
/* 278 */     if (str1 == null)
/* 279 */       return str2; 
/* 280 */     if (str2 == null || !paramBoolean) {
/* 281 */       return str1;
/*     */     }
/* 283 */     return str1 + ":" + str2;
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
/*     */   public static FactoryEnumeration getFactories(String paramString, Hashtable<?, ?> paramHashtable, Context paramContext) throws NamingException {
/* 332 */     String str = getProperty(paramString, paramHashtable, paramContext, true);
/* 333 */     if (str == null) {
/* 334 */       return null;
/*     */     }
/*     */     
/* 337 */     ClassLoader classLoader = helper.getContextClassLoader();
/*     */     
/* 339 */     Map<Object, Object> map = null;
/* 340 */     synchronized (factoryCache) {
/* 341 */       map = (Map)factoryCache.get(classLoader);
/* 342 */       if (map == null) {
/* 343 */         map = new HashMap<>(11);
/* 344 */         factoryCache.put(classLoader, map);
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     synchronized (map) {
/*     */       
/* 350 */       List<NamedWeakReference<Object>> list = (List)map.get(str);
/* 351 */       if (list != null)
/*     */       {
/* 353 */         return (list.size() == 0) ? null : new FactoryEnumeration(list, classLoader);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 358 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ":");
/* 359 */       list = new ArrayList<>(5);
/* 360 */       while (stringTokenizer.hasMoreTokens()) {
/*     */         
/*     */         try {
/* 363 */           String str1 = stringTokenizer.nextToken();
/* 364 */           Class<?> clazz = helper.loadClass(str1, classLoader);
/* 365 */           list.add(new NamedWeakReference(clazz, str1));
/* 366 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 371 */       map.put(str, list);
/* 372 */       return new FactoryEnumeration(list, classLoader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFactory(String paramString1, Hashtable<?, ?> paramHashtable, Context paramContext, String paramString2, String paramString3) throws NamingException {
/* 418 */     String str1 = getProperty(paramString1, paramHashtable, paramContext, true);
/* 419 */     if (str1 != null) {
/* 420 */       str1 = str1 + ":" + paramString3;
/*     */     } else {
/* 422 */       str1 = paramString3;
/*     */     } 
/*     */ 
/*     */     
/* 426 */     ClassLoader classLoader = helper.getContextClassLoader();
/* 427 */     String str2 = paramString2 + " " + str1;
/*     */     
/* 429 */     Map<Object, Object> map = null;
/* 430 */     synchronized (urlFactoryCache) {
/* 431 */       map = (Map)urlFactoryCache.get(classLoader);
/* 432 */       if (map == null) {
/* 433 */         map = new HashMap<>(11);
/* 434 */         urlFactoryCache.put(classLoader, map);
/*     */       } 
/*     */     } 
/*     */     
/* 438 */     synchronized (map) {
/* 439 */       Object object = null;
/*     */       
/* 441 */       WeakReference<Object> weakReference = (WeakReference)map.get(str2);
/* 442 */       if (weakReference == NO_FACTORY)
/* 443 */         return null; 
/* 444 */       if (weakReference != null) {
/* 445 */         object = weakReference.get();
/* 446 */         if (object != null) {
/* 447 */           return object;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 452 */       StringTokenizer stringTokenizer = new StringTokenizer(str1, ":");
/*     */       
/* 454 */       while (object == null && stringTokenizer.hasMoreTokens()) {
/* 455 */         String str = stringTokenizer.nextToken() + paramString2;
/*     */         
/*     */         try {
/* 458 */           object = helper.loadClass(str, classLoader).newInstance();
/* 459 */         } catch (InstantiationException instantiationException) {
/* 460 */           NamingException namingException = new NamingException("Cannot instantiate " + str);
/*     */           
/* 462 */           namingException.setRootCause(instantiationException);
/* 463 */           throw namingException;
/* 464 */         } catch (IllegalAccessException illegalAccessException) {
/* 465 */           NamingException namingException = new NamingException("Cannot access " + str);
/*     */           
/* 467 */           namingException.setRootCause(illegalAccessException);
/* 468 */           throw namingException;
/* 469 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 476 */       map.put(str2, (object != null) ? new WeakReference(object) : NO_FACTORY);
/*     */ 
/*     */       
/* 479 */       return object;
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
/*     */   private static Hashtable<? super String, Object> getProviderResource(Object paramObject) throws NamingException {
/* 498 */     if (paramObject == null) {
/* 499 */       return new Hashtable<>(1);
/*     */     }
/* 501 */     synchronized (propertiesCache) {
/* 502 */       Class<?> clazz = paramObject.getClass();
/*     */ 
/*     */       
/* 505 */       Hashtable<? super String, Object> hashtable = propertiesCache.get(clazz);
/* 506 */       if (hashtable != null) {
/* 507 */         return hashtable;
/*     */       }
/* 509 */       hashtable = new Properties();
/*     */ 
/*     */       
/* 512 */       InputStream inputStream = helper.getResourceAsStream(clazz, "jndiprovider.properties");
/*     */       
/* 514 */       if (inputStream != null) {
/*     */         try {
/* 516 */           ((Properties)hashtable).load(inputStream);
/* 517 */         } catch (IOException iOException) {
/* 518 */           ConfigurationException configurationException = new ConfigurationException("Error reading provider resource file for " + clazz);
/*     */           
/* 520 */           configurationException.setRootCause(iOException);
/* 521 */           throw configurationException;
/*     */         } 
/*     */       }
/* 524 */       propertiesCache.put(clazz, hashtable);
/* 525 */       return hashtable;
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
/*     */   private static Hashtable<? super String, Object> getApplicationResources() throws NamingException {
/* 549 */     ClassLoader classLoader = helper.getContextClassLoader();
/*     */     
/* 551 */     synchronized (propertiesCache) {
/* 552 */       Hashtable<? super String, Object> hashtable = propertiesCache.get(classLoader);
/* 553 */       if (hashtable != null) {
/* 554 */         return hashtable;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 559 */         NamingEnumeration<InputStream> namingEnumeration = helper.getResources(classLoader, "jndi.properties");
/*     */         try {
/* 561 */           while (namingEnumeration.hasMore()) {
/* 562 */             Properties properties = new Properties();
/* 563 */             InputStream inputStream1 = namingEnumeration.next();
/*     */             try {
/* 565 */               properties.load(inputStream1);
/*     */             } finally {
/* 567 */               inputStream1.close();
/*     */             } 
/*     */             
/* 570 */             if (hashtable == null) {
/* 571 */               hashtable = properties; continue;
/*     */             } 
/* 573 */             mergeTables(hashtable, properties);
/*     */           } 
/*     */         } finally {
/*     */           
/* 577 */           while (namingEnumeration.hasMore()) {
/* 578 */             ((InputStream)namingEnumeration.next()).close();
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 584 */         InputStream inputStream = helper.getJavaHomeLibStream("jndi.properties");
/* 585 */         if (inputStream != null) {
/*     */           try {
/* 587 */             Properties properties = new Properties();
/* 588 */             properties.load(inputStream);
/*     */             
/* 590 */             if (hashtable == null) {
/* 591 */               hashtable = properties;
/*     */             } else {
/* 593 */               mergeTables(hashtable, properties);
/*     */             } 
/*     */           } finally {
/* 596 */             inputStream.close();
/*     */           }
/*     */         
/*     */         }
/* 600 */       } catch (IOException iOException) {
/* 601 */         ConfigurationException configurationException = new ConfigurationException("Error reading application resource file");
/*     */         
/* 603 */         configurationException.setRootCause(iOException);
/* 604 */         throw configurationException;
/*     */       } 
/* 606 */       if (hashtable == null) {
/* 607 */         hashtable = new Hashtable<>(11);
/*     */       }
/* 609 */       propertiesCache.put(classLoader, hashtable);
/* 610 */       return hashtable;
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
/*     */   private static void mergeTables(Hashtable<? super String, Object> paramHashtable1, Hashtable<? super String, Object> paramHashtable2) {
/* 623 */     for (String str1 : paramHashtable2.keySet()) {
/* 624 */       String str2 = str1;
/* 625 */       Object object = paramHashtable1.get(str2);
/* 626 */       if (object == null) {
/* 627 */         paramHashtable1.put(str2, paramHashtable2.get(str2)); continue;
/* 628 */       }  if (isListProperty(str2)) {
/* 629 */         String str = (String)paramHashtable2.get(str2);
/* 630 */         paramHashtable1.put(str2, (String)object + ":" + str);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isListProperty(String paramString) {
/* 640 */     paramString = paramString.intern();
/* 641 */     for (byte b = 0; b < listProperties.length; b++) {
/* 642 */       if (paramString == listProperties[b]) {
/* 643 */         return true;
/*     */       }
/*     */     } 
/* 646 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/naming/internal/ResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */