/*     */ package javax.management.remote;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMXConnectorFactory
/*     */ {
/*     */   public static final String DEFAULT_CLASS_LOADER = "jmx.remote.default.class.loader";
/*     */   public static final String PROTOCOL_PROVIDER_PACKAGES = "jmx.remote.protocol.provider.pkgs";
/*     */   public static final String PROTOCOL_PROVIDER_CLASS_LOADER = "jmx.remote.protocol.provider.class.loader";
/*     */   private static final String PROTOCOL_PROVIDER_DEFAULT_PACKAGE = "com.sun.jmx.remote.protocol";
/* 199 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "JMXConnectorFactory");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JMXConnector connect(JMXServiceURL paramJMXServiceURL) throws IOException {
/* 229 */     return connect(paramJMXServiceURL, null);
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
/*     */   public static JMXConnector connect(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) throws IOException {
/* 267 */     if (paramJMXServiceURL == null)
/* 268 */       throw new NullPointerException("Null JMXServiceURL"); 
/* 269 */     JMXConnector jMXConnector = newJMXConnector(paramJMXServiceURL, paramMap);
/* 270 */     jMXConnector.connect(paramMap);
/* 271 */     return jMXConnector;
/*     */   }
/*     */   
/*     */   private static <K, V> Map<K, V> newHashMap() {
/* 275 */     return new HashMap<>();
/*     */   }
/*     */   
/*     */   private static <K> Map<K, Object> newHashMap(Map<K, ?> paramMap) {
/* 279 */     return new HashMap<>(paramMap);
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
/*     */   public static JMXConnector newJMXConnector(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) throws IOException {
/*     */     Map<?, ?> map1;
/* 316 */     if (paramMap == null) {
/* 317 */       map1 = newHashMap();
/*     */     } else {
/* 319 */       EnvHelp.checkAttributes(paramMap);
/* 320 */       map1 = newHashMap(paramMap);
/*     */     } 
/*     */     
/* 323 */     ClassLoader classLoader = resolveClassLoader((Map)map1);
/* 324 */     Class<JMXConnectorProvider> clazz = JMXConnectorProvider.class;
/*     */     
/* 326 */     String str = paramJMXServiceURL.getProtocol();
/*     */     
/* 328 */     JMXServiceURL jMXServiceURL = paramJMXServiceURL;
/*     */     
/* 330 */     JMXConnectorProvider jMXConnectorProvider = getProvider(jMXServiceURL, (Map)map1, "ClientProvider", clazz, classLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     IOException iOException = null;
/* 336 */     if (jMXConnectorProvider == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 341 */       if (classLoader != null) {
/*     */         
/*     */         try {
/* 344 */           JMXConnector jMXConnector = getConnectorAsService(classLoader, jMXServiceURL, (Map)map1);
/* 345 */           if (jMXConnector != null)
/* 346 */             return jMXConnector; 
/* 347 */         } catch (JMXProviderException jMXProviderException) {
/* 348 */           throw jMXProviderException;
/* 349 */         } catch (IOException iOException1) {
/* 350 */           iOException = iOException1;
/*     */         } 
/*     */       }
/* 353 */       jMXConnectorProvider = getProvider(str, "com.sun.jmx.remote.protocol", JMXConnectorFactory.class
/* 354 */           .getClassLoader(), "ClientProvider", clazz);
/*     */     } 
/*     */ 
/*     */     
/* 358 */     if (jMXConnectorProvider == null) {
/* 359 */       MalformedURLException malformedURLException = new MalformedURLException("Unsupported protocol: " + str);
/*     */       
/* 361 */       if (iOException == null) {
/* 362 */         throw malformedURLException;
/*     */       }
/* 364 */       throw (MalformedURLException)EnvHelp.initCause(malformedURLException, iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 369 */     Map<?, ?> map2 = Collections.unmodifiableMap(map1);
/*     */     
/* 371 */     return jMXConnectorProvider.newJMXConnector(paramJMXServiceURL, (Map)map2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String resolvePkgs(Map<String, ?> paramMap) throws JMXProviderException {
/* 377 */     Object object = null;
/*     */     
/* 379 */     if (paramMap != null) {
/* 380 */       object = paramMap.get("jmx.remote.protocol.provider.pkgs");
/*     */     }
/* 382 */     if (object == null)
/*     */     {
/* 384 */       object = AccessController.doPrivileged(new PrivilegedAction<String>() {
/*     */             public String run() {
/* 386 */               return System.getProperty("jmx.remote.protocol.provider.pkgs");
/*     */             }
/*     */           });
/*     */     }
/* 390 */     if (object == null) {
/* 391 */       return null;
/*     */     }
/* 393 */     if (!(object instanceof String)) {
/*     */ 
/*     */       
/* 396 */       String str1 = "Value of jmx.remote.protocol.provider.pkgs parameter is not a String: " + object.getClass().getName();
/* 397 */       throw new JMXProviderException(str1);
/*     */     } 
/*     */     
/* 400 */     String str = (String)object;
/* 401 */     if (str.trim().equals("")) {
/* 402 */       return null;
/*     */     }
/*     */     
/* 405 */     if (str.startsWith("|") || str.endsWith("|") || str
/* 406 */       .indexOf("||") >= 0) {
/* 407 */       String str1 = "Value of jmx.remote.protocol.provider.pkgs contains an empty element: " + str;
/*     */       
/* 409 */       throw new JMXProviderException(str1);
/*     */     } 
/*     */     
/* 412 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T getProvider(JMXServiceURL paramJMXServiceURL, Map<String, Object> paramMap, String paramString, Class<T> paramClass, ClassLoader paramClassLoader) throws IOException {
/* 422 */     String str1 = paramJMXServiceURL.getProtocol();
/*     */     
/* 424 */     String str2 = resolvePkgs(paramMap);
/*     */     
/* 426 */     T t = null;
/*     */     
/* 428 */     if (str2 != null) {
/*     */       
/* 430 */       t = getProvider(str1, str2, paramClassLoader, paramString, (Class)paramClass);
/*     */ 
/*     */       
/* 433 */       if (t != null) {
/* 434 */         boolean bool = (paramClassLoader != t.getClass().getClassLoader()) ? true : false;
/* 435 */         paramMap.put("jmx.remote.protocol.provider.class.loader", bool ? wrap(paramClassLoader) : paramClassLoader);
/*     */       } 
/*     */     } 
/*     */     
/* 439 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> Iterator<T> getProviderIterator(Class<T> paramClass, ClassLoader paramClassLoader) {
/* 445 */     ServiceLoader<T> serviceLoader = ServiceLoader.load(paramClass, paramClassLoader);
/* 446 */     return serviceLoader.iterator();
/*     */   }
/*     */   
/*     */   private static ClassLoader wrap(final ClassLoader parent) {
/* 450 */     return (parent != null) ? AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public ClassLoader run() {
/* 453 */             return new ClassLoader(parent)
/*     */               {
/*     */                 protected Class<?> loadClass(String param2String, boolean param2Boolean) throws ClassNotFoundException {
/* 456 */                   ReflectUtil.checkPackageAccess(param2String);
/* 457 */                   return super.loadClass(param2String, param2Boolean);
/*     */                 }
/*     */               };
/*     */           }
/*     */         }) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JMXConnector getConnectorAsService(ClassLoader paramClassLoader, JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) throws IOException {
/* 470 */     Iterator<JMXConnectorProvider> iterator = getProviderIterator(JMXConnectorProvider.class, paramClassLoader);
/*     */     
/* 472 */     IOException iOException = null;
/* 473 */     while (iterator.hasNext()) {
/* 474 */       JMXConnectorProvider jMXConnectorProvider = iterator.next();
/*     */       try {
/* 476 */         return jMXConnectorProvider.newJMXConnector(paramJMXServiceURL, paramMap);
/*     */       }
/* 478 */       catch (JMXProviderException jMXProviderException) {
/* 479 */         throw jMXProviderException;
/* 480 */       } catch (Exception exception) {
/* 481 */         if (logger.traceOn()) {
/* 482 */           logger.trace("getConnectorAsService", "URL[" + paramJMXServiceURL + "] Service provider exception: " + exception);
/*     */         }
/*     */         
/* 485 */         if (!(exception instanceof MalformedURLException) && 
/* 486 */           iOException == null) {
/* 487 */           if (exception instanceof IOException) {
/* 488 */             iOException = (IOException)exception; continue;
/*     */           } 
/* 490 */           iOException = EnvHelp.<IOException>initCause(new IOException(exception
/* 491 */                 .getMessage()), exception);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 498 */     if (iOException == null) {
/* 499 */       return null;
/*     */     }
/* 501 */     throw iOException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T getProvider(String paramString1, String paramString2, ClassLoader paramClassLoader, String paramString3, Class<T> paramClass) throws IOException {
/* 511 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, "|");
/*     */     
/* 513 */     while (stringTokenizer.hasMoreTokens()) {
/* 514 */       Class<?> clazz; String str1 = stringTokenizer.nextToken();
/* 515 */       String str2 = str1 + "." + protocol2package(paramString1) + "." + paramString3;
/*     */ 
/*     */       
/*     */       try {
/* 519 */         clazz = Class.forName(str2, true, paramClassLoader);
/* 520 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 525 */       if (!paramClass.isAssignableFrom(clazz)) {
/*     */ 
/*     */ 
/*     */         
/* 529 */         String str = "Provider class does not implement " + paramClass.getName() + ": " + clazz.getName();
/* 530 */         throw new JMXProviderException(str);
/*     */       } 
/*     */ 
/*     */       
/* 534 */       Class<T> clazz1 = (Class)Util.<Class<?>>cast(clazz);
/*     */       try {
/* 536 */         return clazz1.newInstance();
/* 537 */       } catch (Exception exception) {
/* 538 */         String str = "Exception when instantiating provider [" + str2 + "]";
/*     */ 
/*     */         
/* 541 */         throw new JMXProviderException(str, exception);
/*     */       } 
/*     */     } 
/*     */     
/* 545 */     return null;
/*     */   }
/*     */   
/*     */   static ClassLoader resolveClassLoader(Map<String, ?> paramMap) {
/* 549 */     ClassLoader classLoader = null;
/*     */     
/* 551 */     if (paramMap != null) {
/*     */       
/*     */       try {
/* 554 */         classLoader = (ClassLoader)paramMap.get("jmx.remote.protocol.provider.class.loader");
/* 555 */       } catch (ClassCastException classCastException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 560 */         throw new IllegalArgumentException("The ClassLoader supplied in the environment map using the jmx.remote.protocol.provider.class.loader attribute is not an instance of java.lang.ClassLoader");
/*     */       } 
/*     */     }
/*     */     
/* 564 */     if (classLoader == null) {
/* 565 */       classLoader = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/* 568 */     return classLoader;
/*     */   }
/*     */   
/*     */   private static String protocol2package(String paramString) {
/* 572 */     return paramString.replace('+', '.').replace('-', '_');
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXConnectorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */