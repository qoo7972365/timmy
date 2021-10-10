/*      */ package sun.rmi.server;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.SocketPermission;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.net.URLConnection;
/*      */ import java.rmi.server.LogStream;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.Permissions;
/*      */ import java.security.Policy;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.PropertyPermission;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.rmi.runtime.Log;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LoaderHandler
/*      */ {
/*   74 */   static final int logLevel = LogStream.parseLevel(
/*   75 */       AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.loader.logLevel")));
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   static final Log loaderLog = Log.getLog("sun.rmi.loader", "loader", logLevel);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   private static String codebaseProperty = null;
/*      */   static {
/*   88 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.rmi.server.codebase"));
/*      */     
/*   90 */     if (str != null && str.trim().length() > 0) {
/*   91 */       codebaseProperty = str;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*   96 */   private static URL[] codebaseURLs = null;
/*      */ 
/*      */ 
/*      */   
/*  100 */   private static final Map<ClassLoader, Void> codebaseLoaders = Collections.synchronizedMap(new IdentityHashMap<>(5));
/*      */   static {
/*  102 */     ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*  103 */     for (; classLoader != null; 
/*  104 */       classLoader = classLoader.getParent())
/*      */     {
/*  106 */       codebaseLoaders.put(classLoader, null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final HashMap<LoaderKey, LoaderEntry> loaderTable = new HashMap<>(5);
/*      */ 
/*      */ 
/*      */   
/*  120 */   private static final ReferenceQueue<Loader> refQueue = new ReferenceQueue<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized URL[] getDefaultCodebaseURLs() throws MalformedURLException {
/*  139 */     if (codebaseURLs == null) {
/*  140 */       if (codebaseProperty != null) {
/*  141 */         codebaseURLs = pathToURLs(codebaseProperty);
/*      */       } else {
/*  143 */         codebaseURLs = new URL[0];
/*      */       } 
/*      */     }
/*  146 */     return codebaseURLs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader) throws MalformedURLException, ClassNotFoundException {
/*      */     URL[] arrayOfURL;
/*  158 */     if (loaderLog.isLoggable(Log.BRIEF)) {
/*  159 */       loaderLog.log(Log.BRIEF, "name = \"" + paramString2 + "\", codebase = \"" + ((paramString1 != null) ? paramString1 : "") + "\"" + ((paramClassLoader != null) ? (", defaultLoader = " + paramClassLoader) : ""));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  167 */     if (paramString1 != null) {
/*  168 */       arrayOfURL = pathToURLs(paramString1);
/*      */     } else {
/*  170 */       arrayOfURL = getDefaultCodebaseURLs();
/*      */     } 
/*      */     
/*  173 */     if (paramClassLoader != null) {
/*      */       try {
/*  175 */         Class<?> clazz = loadClassForName(paramString2, false, paramClassLoader);
/*  176 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  177 */           loaderLog.log(Log.VERBOSE, "class \"" + paramString2 + "\" found via defaultLoader, defined by " + clazz
/*      */               
/*  179 */               .getClassLoader());
/*      */         }
/*  181 */         return clazz;
/*  182 */       } catch (ClassNotFoundException classNotFoundException) {}
/*      */     }
/*      */ 
/*      */     
/*  186 */     return loadClass(arrayOfURL, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getClassAnnotation(Class<?> paramClass) {
/*  195 */     String str1 = paramClass.getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  204 */     int i = str1.length();
/*  205 */     if (i > 0 && str1.charAt(0) == '[') {
/*      */       
/*  207 */       byte b = 1;
/*  208 */       while (i > b && str1.charAt(b) == '[') {
/*  209 */         b++;
/*      */       }
/*  211 */       if (i > b && str1.charAt(b) != 'L') {
/*  212 */         return null;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  222 */     ClassLoader classLoader = paramClass.getClassLoader();
/*  223 */     if (classLoader == null || codebaseLoaders.containsKey(classLoader)) {
/*  224 */       return codebaseProperty;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  231 */     String str2 = null;
/*  232 */     if (classLoader instanceof Loader) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  238 */       str2 = ((Loader)classLoader).getClassAnnotation();
/*      */     }
/*  240 */     else if (classLoader instanceof URLClassLoader) {
/*      */       try {
/*  242 */         URL[] arrayOfURL = ((URLClassLoader)classLoader).getURLs();
/*  243 */         if (arrayOfURL != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  249 */           SecurityManager securityManager = System.getSecurityManager();
/*  250 */           if (securityManager != null) {
/*  251 */             Permissions permissions = new Permissions();
/*  252 */             for (byte b = 0; b < arrayOfURL.length; b++) {
/*      */               
/*  254 */               Permission permission = arrayOfURL[b].openConnection().getPermission();
/*  255 */               if (permission != null && 
/*  256 */                 !permissions.implies(permission)) {
/*  257 */                 securityManager.checkPermission(permission);
/*  258 */                 permissions.add(permission);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  264 */           str2 = urlsToPath(arrayOfURL);
/*      */         } 
/*  266 */       } catch (SecurityException|IOException securityException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  279 */     if (str2 != null) {
/*  280 */       return str2;
/*      */     }
/*  282 */     return codebaseProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ClassLoader getClassLoader(String paramString) throws MalformedURLException {
/*      */     URL[] arrayOfURL;
/*  294 */     ClassLoader classLoader = getRMIContextClassLoader();
/*      */ 
/*      */     
/*  297 */     if (paramString != null) {
/*  298 */       arrayOfURL = pathToURLs(paramString);
/*      */     } else {
/*  300 */       arrayOfURL = getDefaultCodebaseURLs();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  307 */     SecurityManager securityManager = System.getSecurityManager();
/*  308 */     if (securityManager != null) {
/*  309 */       securityManager.checkPermission(new RuntimePermission("getClassLoader"));
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  315 */       return classLoader;
/*      */     } 
/*      */     
/*  318 */     Loader loader = lookupLoader(arrayOfURL, classLoader);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     if (loader != null) {
/*  324 */       loader.checkPermissions();
/*      */     }
/*      */     
/*  327 */     return loader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object getSecurityContext(ClassLoader paramClassLoader) {
/*  340 */     if (paramClassLoader instanceof Loader) {
/*  341 */       URL[] arrayOfURL = ((Loader)paramClassLoader).getURLs();
/*  342 */       if (arrayOfURL.length > 0) {
/*  343 */         return arrayOfURL[0];
/*      */       }
/*      */     } 
/*  346 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerCodebaseLoader(ClassLoader paramClassLoader) {
/*  354 */     codebaseLoaders.put(paramClassLoader, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> loadClass(URL[] paramArrayOfURL, String paramString) throws ClassNotFoundException {
/*  364 */     ClassLoader classLoader = getRMIContextClassLoader();
/*  365 */     if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  366 */       loaderLog.log(Log.VERBOSE, "(thread context class loader: " + classLoader + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  375 */     SecurityManager securityManager = System.getSecurityManager();
/*  376 */     if (securityManager == null) {
/*      */       try {
/*  378 */         Class<?> clazz = Class.forName(paramString, false, classLoader);
/*  379 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  380 */           loaderLog.log(Log.VERBOSE, "class \"" + paramString + "\" found via thread context class loader (no security manager: codebase disabled), defined by " + clazz
/*      */ 
/*      */ 
/*      */               
/*  384 */               .getClassLoader());
/*      */         }
/*  386 */         return clazz;
/*  387 */       } catch (ClassNotFoundException classNotFoundException) {
/*  388 */         if (loaderLog.isLoggable(Log.BRIEF)) {
/*  389 */           loaderLog.log(Log.BRIEF, "class \"" + paramString + "\" not found via thread context class loader (no security manager: codebase disabled)", classNotFoundException);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  394 */         throw new ClassNotFoundException(classNotFoundException.getMessage() + " (no security manager: RMI class loader disabled)", classNotFoundException
/*      */             
/*  396 */             .getException());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     Loader loader = lookupLoader(paramArrayOfURL, classLoader);
/*      */     
/*      */     try {
/*  407 */       if (loader != null)
/*      */       {
/*      */ 
/*      */         
/*  411 */         loader.checkPermissions();
/*      */       }
/*  413 */     } catch (SecurityException securityException) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  427 */         Class<?> clazz = loadClassForName(paramString, false, classLoader);
/*  428 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  429 */           loaderLog.log(Log.VERBOSE, "class \"" + paramString + "\" found via thread context class loader (access to codebase denied), defined by " + clazz
/*      */ 
/*      */ 
/*      */               
/*  433 */               .getClassLoader());
/*      */         }
/*  435 */         return clazz;
/*  436 */       } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  441 */         if (loaderLog.isLoggable(Log.BRIEF)) {
/*  442 */           loaderLog.log(Log.BRIEF, "class \"" + paramString + "\" not found via thread context class loader (access to codebase denied)", securityException);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  447 */         throw new ClassNotFoundException("access to class loader denied", securityException);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  453 */       Class<?> clazz = loadClassForName(paramString, false, loader);
/*  454 */       if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  455 */         loaderLog.log(Log.VERBOSE, "class \"" + paramString + "\" found via codebase, defined by " + clazz
/*      */             
/*  457 */             .getClassLoader());
/*      */       }
/*  459 */       return clazz;
/*  460 */     } catch (ClassNotFoundException classNotFoundException) {
/*  461 */       if (loaderLog.isLoggable(Log.BRIEF)) {
/*  462 */         loaderLog.log(Log.BRIEF, "class \"" + paramString + "\" not found via codebase", classNotFoundException);
/*      */       }
/*      */       
/*  465 */       throw classNotFoundException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> loadProxyClass(String paramString, String[] paramArrayOfString, ClassLoader paramClassLoader) throws MalformedURLException, ClassNotFoundException {
/*      */     URL[] arrayOfURL;
/*  479 */     if (loaderLog.isLoggable(Log.BRIEF)) {
/*  480 */       loaderLog.log(Log.BRIEF, "interfaces = " + 
/*  481 */           Arrays.<String>asList(paramArrayOfString) + ", codebase = \"" + ((paramString != null) ? paramString : "") + "\"" + ((paramClassLoader != null) ? (", defaultLoader = " + paramClassLoader) : ""));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  520 */     ClassLoader classLoader = getRMIContextClassLoader();
/*  521 */     if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  522 */       loaderLog.log(Log.VERBOSE, "(thread context class loader: " + classLoader + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  527 */     if (paramString != null) {
/*  528 */       arrayOfURL = pathToURLs(paramString);
/*      */     } else {
/*  530 */       arrayOfURL = getDefaultCodebaseURLs();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     SecurityManager securityManager = System.getSecurityManager();
/*  538 */     if (securityManager == null) {
/*      */       try {
/*  540 */         Class<?> clazz = loadProxyClass(paramArrayOfString, paramClassLoader, classLoader, false);
/*      */         
/*  542 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  543 */           loaderLog.log(Log.VERBOSE, "(no security manager: codebase disabled) proxy class defined by " + clazz
/*      */               
/*  545 */               .getClassLoader());
/*      */         }
/*  547 */         return clazz;
/*  548 */       } catch (ClassNotFoundException classNotFoundException) {
/*  549 */         if (loaderLog.isLoggable(Log.BRIEF)) {
/*  550 */           loaderLog.log(Log.BRIEF, "(no security manager: codebase disabled) proxy class resolution failed", classNotFoundException);
/*      */         }
/*      */ 
/*      */         
/*  554 */         throw new ClassNotFoundException(classNotFoundException.getMessage() + " (no security manager: RMI class loader disabled)", classNotFoundException
/*      */             
/*  556 */             .getException());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     Loader loader = lookupLoader(arrayOfURL, classLoader);
/*      */     
/*      */     try {
/*  567 */       if (loader != null)
/*      */       {
/*      */ 
/*      */         
/*  571 */         loader.checkPermissions();
/*      */       }
/*  573 */     } catch (SecurityException securityException) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  587 */         Class<?> clazz = loadProxyClass(paramArrayOfString, paramClassLoader, classLoader, false);
/*      */         
/*  589 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  590 */           loaderLog.log(Log.VERBOSE, "(access to codebase denied) proxy class defined by " + clazz
/*      */               
/*  592 */               .getClassLoader());
/*      */         }
/*  594 */         return clazz;
/*  595 */       } catch (ClassNotFoundException classNotFoundException) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  600 */         if (loaderLog.isLoggable(Log.BRIEF)) {
/*  601 */           loaderLog.log(Log.BRIEF, "(access to codebase denied) proxy class resolution failed", securityException);
/*      */         }
/*      */ 
/*      */         
/*  605 */         throw new ClassNotFoundException("access to class loader denied", securityException);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  611 */       Class<?> clazz = loadProxyClass(paramArrayOfString, paramClassLoader, loader, true);
/*  612 */       if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  613 */         loaderLog.log(Log.VERBOSE, "proxy class defined by " + clazz
/*  614 */             .getClassLoader());
/*      */       }
/*  616 */       return clazz;
/*  617 */     } catch (ClassNotFoundException classNotFoundException) {
/*  618 */       if (loaderLog.isLoggable(Log.BRIEF)) {
/*  619 */         loaderLog.log(Log.BRIEF, "proxy class resolution failed", classNotFoundException);
/*      */       }
/*      */       
/*  622 */       throw classNotFoundException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> loadProxyClass(String[] paramArrayOfString, ClassLoader paramClassLoader1, ClassLoader paramClassLoader2, boolean paramBoolean) throws ClassNotFoundException {
/*  638 */     ClassLoader classLoader = null;
/*  639 */     Class[] arrayOfClass = new Class[paramArrayOfString.length];
/*  640 */     boolean[] arrayOfBoolean = { false };
/*      */ 
/*      */     
/*  643 */     if (paramClassLoader1 != null) {
/*      */       
/*      */       try {
/*  646 */         classLoader = loadProxyInterfaces(paramArrayOfString, paramClassLoader1, arrayOfClass, arrayOfBoolean);
/*      */         
/*  648 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  649 */           ClassLoader[] arrayOfClassLoader = new ClassLoader[arrayOfClass.length];
/*      */           
/*  651 */           for (byte b = 0; b < arrayOfClassLoader.length; b++) {
/*  652 */             arrayOfClassLoader[b] = arrayOfClass[b].getClassLoader();
/*      */           }
/*  654 */           loaderLog.log(Log.VERBOSE, "proxy interfaces found via defaultLoader, defined by " + 
/*      */               
/*  656 */               Arrays.<ClassLoader>asList(arrayOfClassLoader));
/*      */         } 
/*  658 */       } catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */       
/*  661 */       if (!arrayOfBoolean[0]) {
/*  662 */         if (paramBoolean) {
/*      */           try {
/*  664 */             return Proxy.getProxyClass(paramClassLoader2, arrayOfClass);
/*  665 */           } catch (IllegalArgumentException illegalArgumentException) {}
/*      */         }
/*      */         
/*  668 */         classLoader = paramClassLoader1;
/*      */       } 
/*  670 */       return loadProxyClass(classLoader, arrayOfClass);
/*      */     } 
/*      */     
/*  673 */     arrayOfBoolean[0] = false;
/*  674 */     classLoader = loadProxyInterfaces(paramArrayOfString, paramClassLoader2, arrayOfClass, arrayOfBoolean);
/*      */     
/*  676 */     if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  677 */       ClassLoader[] arrayOfClassLoader = new ClassLoader[arrayOfClass.length];
/*  678 */       for (byte b = 0; b < arrayOfClassLoader.length; b++) {
/*  679 */         arrayOfClassLoader[b] = arrayOfClass[b].getClassLoader();
/*      */       }
/*  681 */       loaderLog.log(Log.VERBOSE, "proxy interfaces found via codebase, defined by " + 
/*      */           
/*  683 */           Arrays.<ClassLoader>asList(arrayOfClassLoader));
/*      */     } 
/*  685 */     if (!arrayOfBoolean[0]) {
/*  686 */       classLoader = paramClassLoader2;
/*      */     }
/*  688 */     return loadProxyClass(classLoader, arrayOfClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> loadProxyClass(ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass) throws ClassNotFoundException {
/*      */     try {
/*  699 */       return Proxy.getProxyClass(paramClassLoader, paramArrayOfClass);
/*  700 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  701 */       throw new ClassNotFoundException("error creating dynamic proxy class", illegalArgumentException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClassLoader loadProxyInterfaces(String[] paramArrayOfString, ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass, boolean[] paramArrayOfboolean) throws ClassNotFoundException {
/*  727 */     ClassLoader classLoader = null;
/*      */     
/*  729 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*      */       
/*  731 */       Class<?> clazz = paramArrayOfClass[b] = loadClassForName(paramArrayOfString[b], false, paramClassLoader);
/*      */       
/*  733 */       if (!Modifier.isPublic(clazz.getModifiers())) {
/*  734 */         ClassLoader classLoader1 = clazz.getClassLoader();
/*  735 */         if (loaderLog.isLoggable(Log.VERBOSE)) {
/*  736 */           loaderLog.log(Log.VERBOSE, "non-public interface \"" + paramArrayOfString[b] + "\" defined by " + classLoader1);
/*      */         }
/*      */ 
/*      */         
/*  740 */         if (!paramArrayOfboolean[0]) {
/*  741 */           classLoader = classLoader1;
/*  742 */           paramArrayOfboolean[0] = true;
/*  743 */         } else if (classLoader1 != classLoader) {
/*  744 */           throw new IllegalAccessError("non-public interfaces defined in different class loaders");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  750 */     return classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URL[] pathToURLs(String paramString) throws MalformedURLException {
/*  761 */     synchronized (pathToURLsCache) {
/*  762 */       Object[] arrayOfObject = pathToURLsCache.get(paramString);
/*  763 */       if (arrayOfObject != null) {
/*  764 */         return (URL[])arrayOfObject[0];
/*      */       }
/*      */     } 
/*  767 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString);
/*  768 */     URL[] arrayOfURL = new URL[stringTokenizer.countTokens()];
/*  769 */     for (byte b = 0; stringTokenizer.hasMoreTokens(); b++) {
/*  770 */       arrayOfURL[b] = new URL(stringTokenizer.nextToken());
/*      */     }
/*  772 */     synchronized (pathToURLsCache) {
/*  773 */       pathToURLsCache.put(paramString, new Object[] { arrayOfURL, new SoftReference<>(paramString) });
/*      */     } 
/*      */     
/*  776 */     return arrayOfURL;
/*      */   }
/*      */ 
/*      */   
/*  780 */   private static final Map<String, Object[]> pathToURLsCache = (Map)new WeakHashMap<>(5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String urlsToPath(URL[] paramArrayOfURL) {
/*  791 */     if (paramArrayOfURL.length == 0)
/*  792 */       return null; 
/*  793 */     if (paramArrayOfURL.length == 1) {
/*  794 */       return paramArrayOfURL[0].toExternalForm();
/*      */     }
/*  796 */     StringBuffer stringBuffer = new StringBuffer(paramArrayOfURL[0].toExternalForm());
/*  797 */     for (byte b = 1; b < paramArrayOfURL.length; b++) {
/*  798 */       stringBuffer.append(' ');
/*  799 */       stringBuffer.append(paramArrayOfURL[b].toExternalForm());
/*      */     } 
/*  801 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClassLoader getRMIContextClassLoader() {
/*  814 */     return Thread.currentThread().getContextClassLoader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Loader lookupLoader(final URL[] urls, final ClassLoader parent) {
/*      */     Loader loader;
/*  843 */     synchronized (LoaderHandler.class) {
/*      */       LoaderEntry loaderEntry;
/*      */ 
/*      */ 
/*      */       
/*  848 */       while ((loaderEntry = (LoaderEntry)refQueue.poll()) != null) {
/*  849 */         if (!loaderEntry.removed) {
/*  850 */           loaderTable.remove(loaderEntry.key);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  858 */       LoaderKey loaderKey = new LoaderKey(urls, parent);
/*  859 */       loaderEntry = loaderTable.get(loaderKey);
/*      */       
/*  861 */       if (loaderEntry == null || (loader = loaderEntry.get()) == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  869 */         if (loaderEntry != null) {
/*  870 */           loaderTable.remove(loaderKey);
/*  871 */           loaderEntry.removed = true;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  881 */         AccessControlContext accessControlContext = getLoaderAccessControlContext(urls);
/*  882 */         loader = AccessController.<Loader>doPrivileged(new PrivilegedAction<Loader>()
/*      */             {
/*      */               public LoaderHandler.Loader run() {
/*  885 */                 return new LoaderHandler.Loader(urls, parent);
/*      */               }
/*      */             }accessControlContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  893 */         loaderEntry = new LoaderEntry(loaderKey, loader);
/*  894 */         loaderTable.put(loaderKey, loaderEntry);
/*      */       } 
/*      */     } 
/*      */     
/*  898 */     return loader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LoaderKey
/*      */   {
/*      */     private URL[] urls;
/*      */ 
/*      */     
/*      */     private ClassLoader parent;
/*      */     
/*      */     private int hashValue;
/*      */ 
/*      */     
/*      */     public LoaderKey(URL[] param1ArrayOfURL, ClassLoader param1ClassLoader) {
/*  914 */       this.urls = param1ArrayOfURL;
/*  915 */       this.parent = param1ClassLoader;
/*      */       
/*  917 */       if (param1ClassLoader != null) {
/*  918 */         this.hashValue = param1ClassLoader.hashCode();
/*      */       }
/*  920 */       for (byte b = 0; b < param1ArrayOfURL.length; b++) {
/*  921 */         this.hashValue ^= param1ArrayOfURL[b].hashCode();
/*      */       }
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  926 */       return this.hashValue;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  930 */       if (param1Object instanceof LoaderKey) {
/*  931 */         LoaderKey loaderKey = (LoaderKey)param1Object;
/*  932 */         if (this.parent != loaderKey.parent) {
/*  933 */           return false;
/*      */         }
/*  935 */         if (this.urls == loaderKey.urls) {
/*  936 */           return true;
/*      */         }
/*  938 */         if (this.urls.length != loaderKey.urls.length) {
/*  939 */           return false;
/*      */         }
/*  941 */         for (byte b = 0; b < this.urls.length; b++) {
/*  942 */           if (!this.urls[b].equals(loaderKey.urls[b])) {
/*  943 */             return false;
/*      */           }
/*      */         } 
/*  946 */         return true;
/*      */       } 
/*  948 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LoaderEntry
/*      */     extends WeakReference<Loader>
/*      */   {
/*      */     public LoaderHandler.LoaderKey key;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LoaderEntry(LoaderHandler.LoaderKey param1LoaderKey, LoaderHandler.Loader param1Loader) {
/*  972 */       super(param1Loader, LoaderHandler.refQueue);
/*  973 */       this.key = param1LoaderKey;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AccessControlContext getLoaderAccessControlContext(URL[] paramArrayOfURL) {
/*  990 */     PermissionCollection permissionCollection = AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*      */         {
/*      */           public PermissionCollection run() {
/*  993 */             CodeSource codeSource = new CodeSource(null, (Certificate[])null);
/*      */             
/*  995 */             Policy policy = Policy.getPolicy();
/*  996 */             if (policy != null) {
/*  997 */               return policy.getPermissions(codeSource);
/*      */             }
/*  999 */             return new Permissions();
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */     
/* 1005 */     permissionCollection.add(new RuntimePermission("createClassLoader"));
/*      */ 
/*      */     
/* 1008 */     permissionCollection.add(new PropertyPermission("java.*", "read"));
/*      */ 
/*      */     
/* 1011 */     addPermissionsForURLs(paramArrayOfURL, permissionCollection, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource((paramArrayOfURL.length > 0) ? paramArrayOfURL[0] : null, (Certificate[])null), permissionCollection);
/*      */ 
/*      */ 
/*      */     
/* 1021 */     return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addPermissionsForURLs(URL[] paramArrayOfURL, PermissionCollection paramPermissionCollection, boolean paramBoolean) {
/* 1038 */     for (byte b = 0; b < paramArrayOfURL.length; b++) {
/* 1039 */       URL uRL = paramArrayOfURL[b];
/*      */       try {
/* 1041 */         URLConnection uRLConnection = uRL.openConnection();
/* 1042 */         Permission permission = uRLConnection.getPermission();
/* 1043 */         if (permission != null) {
/* 1044 */           if (permission instanceof FilePermission) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1056 */             String str = permission.getName();
/* 1057 */             int i = str.lastIndexOf(File.separatorChar);
/* 1058 */             if (i != -1) {
/* 1059 */               str = str.substring(0, i + 1);
/* 1060 */               if (str.endsWith(File.separator)) {
/* 1061 */                 str = str + "-";
/*      */               }
/* 1063 */               FilePermission filePermission = new FilePermission(str, "read");
/* 1064 */               if (!paramPermissionCollection.implies(filePermission)) {
/* 1065 */                 paramPermissionCollection.add(filePermission);
/*      */               }
/* 1067 */               paramPermissionCollection.add(new FilePermission(str, "read"));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 1073 */             else if (!paramPermissionCollection.implies(permission)) {
/* 1074 */               paramPermissionCollection.add(permission);
/*      */             } 
/*      */           } else {
/*      */             
/* 1078 */             if (!paramPermissionCollection.implies(permission)) {
/* 1079 */               paramPermissionCollection.add(permission);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1090 */             if (paramBoolean) {
/*      */               
/* 1092 */               URL uRL1 = uRL;
/* 1093 */               URLConnection uRLConnection1 = uRLConnection;
/* 1094 */               while (uRLConnection1 instanceof JarURLConnection) {
/*      */ 
/*      */                 
/* 1097 */                 uRL1 = ((JarURLConnection)uRLConnection1).getJarFileURL();
/* 1098 */                 uRLConnection1 = uRL1.openConnection();
/*      */               } 
/* 1100 */               String str = uRL1.getHost();
/* 1101 */               if (str != null && permission
/* 1102 */                 .implies(new SocketPermission(str, "resolve"))) {
/*      */ 
/*      */                 
/* 1105 */                 SocketPermission socketPermission = new SocketPermission(str, "connect,accept");
/*      */ 
/*      */                 
/* 1108 */                 if (!paramPermissionCollection.implies(socketPermission)) {
/* 1109 */                   paramPermissionCollection.add(socketPermission);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/* 1115 */       } catch (IOException iOException) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Loader
/*      */     extends URLClassLoader
/*      */   {
/*      */     private ClassLoader parent;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String annotation;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Permissions permissions;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Loader(URL[] param1ArrayOfURL, ClassLoader param1ClassLoader) {
/* 1142 */       super(param1ArrayOfURL, param1ClassLoader);
/* 1143 */       this.parent = param1ClassLoader;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1148 */       this.permissions = new Permissions();
/* 1149 */       LoaderHandler.addPermissionsForURLs(param1ArrayOfURL, this.permissions, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1156 */       this.annotation = LoaderHandler.urlsToPath(param1ArrayOfURL);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getClassAnnotation() {
/* 1164 */       return this.annotation;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkPermissions() {
/* 1172 */       SecurityManager securityManager = System.getSecurityManager();
/* 1173 */       if (securityManager != null) {
/* 1174 */         Enumeration<Permission> enumeration = this.permissions.elements();
/* 1175 */         while (enumeration.hasMoreElements()) {
/* 1176 */           securityManager.checkPermission(enumeration.nextElement());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected PermissionCollection getPermissions(CodeSource param1CodeSource) {
/* 1186 */       return super.getPermissions(param1CodeSource);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1198 */       return super.toString() + "[\"" + this.annotation + "\"]";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Class<?> loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException {
/* 1204 */       if (this.parent == null) {
/* 1205 */         ReflectUtil.checkPackageAccess(param1String);
/*      */       }
/* 1207 */       return super.loadClass(param1String, param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> loadClassForName(String paramString, boolean paramBoolean, ClassLoader paramClassLoader) throws ClassNotFoundException {
/* 1218 */     if (paramClassLoader == null) {
/* 1219 */       ReflectUtil.checkPackageAccess(paramString);
/*      */     }
/* 1221 */     return Class.forName(paramString, paramBoolean, paramClassLoader);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/LoaderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */