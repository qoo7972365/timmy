/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.ConsoleHandler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContextFinder
/*     */ {
/*  60 */   private static final Logger logger = Logger.getLogger("javax.xml.bind"); static {
/*     */     try {
/*  62 */       if (AccessController.doPrivileged(new GetPropertyAction("jaxb.debug")) != null)
/*     */       {
/*     */         
/*  65 */         logger.setUseParentHandlers(false);
/*  66 */         logger.setLevel(Level.ALL);
/*  67 */         ConsoleHandler handler = new ConsoleHandler();
/*  68 */         handler.setLevel(Level.ALL);
/*  69 */         logger.addHandler(handler);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*  75 */     catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PLATFORM_DEFAULT_FACTORY_CLASS = "com.sun.xml.internal.bind.v2.ContextFactory";
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleInvocationTargetException(InvocationTargetException x) throws JAXBException {
/*  86 */     Throwable t = x.getTargetException();
/*  87 */     if (t != null) {
/*  88 */       if (t instanceof JAXBException)
/*     */       {
/*  90 */         throw (JAXBException)t; } 
/*  91 */       if (t instanceof RuntimeException)
/*     */       {
/*  93 */         throw (RuntimeException)t; } 
/*  94 */       if (t instanceof Error) {
/*  95 */         throw (Error)t;
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
/*     */   private static JAXBException handleClassCastException(Class originalType, Class targetType) {
/* 112 */     URL targetTypeURL = which(targetType);
/*     */     
/* 114 */     return new JAXBException(Messages.format("JAXBContext.IllegalCast", 
/*     */ 
/*     */           
/* 117 */           getClassClassLoader(originalType).getResource("javax/xml/bind/JAXBContext.class"), targetTypeURL));
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
/*     */   static JAXBContext newInstance(String contextPath, String className, ClassLoader classLoader, Map properties) throws JAXBException {
/*     */     try {
/* 130 */       Class spFactory = safeLoadClass(className, classLoader);
/* 131 */       return newInstance(contextPath, spFactory, classLoader, properties);
/* 132 */     } catch (ClassNotFoundException x) {
/* 133 */       throw new JAXBException(
/* 134 */           Messages.format("ContextFinder.ProviderNotFound", className), x);
/*     */     }
/* 136 */     catch (RuntimeException x) {
/*     */ 
/*     */       
/* 139 */       throw x;
/* 140 */     } catch (Exception x) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       throw new JAXBException(
/* 146 */           Messages.format("ContextFinder.CouldNotInstantiate", className, x), x);
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
/*     */   static JAXBContext newInstance(String contextPath, Class spFactory, ClassLoader classLoader, Map properties) throws JAXBException {
/*     */     try {
/* 164 */       Object context = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 169 */         Method m = spFactory.getMethod("createContext", new Class[] { String.class, ClassLoader.class, Map.class });
/*     */         
/* 171 */         context = m.invoke(null, new Object[] { contextPath, classLoader, properties });
/* 172 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */ 
/*     */       
/* 176 */       if (context == null) {
/*     */ 
/*     */         
/* 179 */         Method m = spFactory.getMethod("createContext", new Class[] { String.class, ClassLoader.class });
/*     */         
/* 181 */         context = m.invoke(null, new Object[] { contextPath, classLoader });
/*     */       } 
/*     */       
/* 184 */       if (!(context instanceof JAXBContext))
/*     */       {
/* 186 */         throw handleClassCastException(context.getClass(), JAXBContext.class);
/*     */       }
/* 188 */       return (JAXBContext)context;
/* 189 */     } catch (InvocationTargetException x) {
/* 190 */       handleInvocationTargetException(x);
/*     */ 
/*     */       
/* 193 */       Throwable e = x;
/* 194 */       if (x.getTargetException() != null) {
/* 195 */         e = x.getTargetException();
/*     */       }
/* 197 */       throw new JAXBException(Messages.format("ContextFinder.CouldNotInstantiate", spFactory, e), e);
/* 198 */     } catch (RuntimeException x) {
/*     */ 
/*     */       
/* 201 */       throw x;
/* 202 */     } catch (Exception x) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       throw new JAXBException(
/* 208 */           Messages.format("ContextFinder.CouldNotInstantiate", spFactory, x), x);
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
/*     */   static JAXBContext newInstance(Class[] classes, Map properties, String className) throws JAXBException {
/*     */     Class spi;
/* 221 */     ClassLoader cl = getContextClassLoader();
/*     */     
/*     */     try {
/* 224 */       spi = safeLoadClass(className, cl);
/* 225 */     } catch (ClassNotFoundException e) {
/* 226 */       throw new JAXBException(e);
/*     */     } 
/*     */     
/* 229 */     if (logger.isLoggable(Level.FINE))
/*     */     {
/* 231 */       logger.log(Level.FINE, "loaded {0} from {1}", new Object[] { className, which(spi) });
/*     */     }
/*     */     
/* 234 */     return newInstance(classes, properties, spi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static JAXBContext newInstance(Class[] classes, Map properties, Class spFactory) throws JAXBException {
/*     */     Method m;
/*     */     try {
/* 242 */       m = spFactory.getMethod("createContext", new Class[] { Class[].class, Map.class });
/* 243 */     } catch (NoSuchMethodException e) {
/* 244 */       throw new JAXBException(e);
/*     */     } 
/*     */     try {
/* 247 */       Object context = m.invoke(null, new Object[] { classes, properties });
/* 248 */       if (!(context instanceof JAXBContext))
/*     */       {
/* 250 */         throw handleClassCastException(context.getClass(), JAXBContext.class);
/*     */       }
/* 252 */       return (JAXBContext)context;
/* 253 */     } catch (IllegalAccessException e) {
/* 254 */       throw new JAXBException(e);
/* 255 */     } catch (InvocationTargetException e) {
/* 256 */       handleInvocationTargetException(e);
/*     */       
/* 258 */       Throwable x = e;
/* 259 */       if (e.getTargetException() != null) {
/* 260 */         x = e.getTargetException();
/*     */       }
/* 262 */       throw new JAXBException(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JAXBContext find(String factoryId, String contextPath, ClassLoader classLoader, Map properties) throws JAXBException {
/* 270 */     String jaxbContextFQCN = JAXBContext.class.getName();
/*     */ 
/*     */ 
/*     */     
/* 274 */     StringTokenizer packages = new StringTokenizer(contextPath, ":");
/*     */ 
/*     */     
/* 277 */     if (!packages.hasMoreTokens())
/*     */     {
/* 279 */       throw new JAXBException(Messages.format("ContextFinder.NoPackageInContextPath"));
/*     */     }
/*     */     
/* 282 */     logger.fine("Searching jaxb.properties");
/*     */     
/* 284 */     while (packages.hasMoreTokens()) {
/* 285 */       String packageName = packages.nextToken(":").replace('.', '/');
/*     */       
/* 287 */       StringBuilder propFileName = (new StringBuilder()).append(packageName).append("/jaxb.properties");
/*     */       
/* 289 */       Properties props = loadJAXBProperties(classLoader, propFileName.toString());
/* 290 */       if (props != null) {
/* 291 */         if (props.containsKey(factoryId)) {
/* 292 */           String str = props.getProperty(factoryId);
/* 293 */           return newInstance(contextPath, str, classLoader, properties);
/*     */         } 
/* 295 */         throw new JAXBException(Messages.format("ContextFinder.MissingProperty", packageName, factoryId));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 300 */     logger.fine("Searching the system property");
/*     */ 
/*     */     
/* 303 */     String factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction("javax.xml.bind.context.factory"));
/* 304 */     if (factoryClassName != null) {
/* 305 */       return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */     }
/* 307 */     factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction(jaxbContextFQCN));
/* 308 */     if (factoryClassName != null) {
/* 309 */       return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 314 */     Class jaxbContext = lookupJaxbContextUsingOsgiServiceLoader();
/* 315 */     if (jaxbContext != null) {
/* 316 */       logger.fine("OSGi environment detected");
/* 317 */       return newInstance(contextPath, jaxbContext, classLoader, properties);
/*     */     } 
/*     */     
/* 320 */     logger.fine("Searching META-INF/services");
/*     */     
/* 322 */     BufferedReader r = null;
/*     */     try {
/* 324 */       StringBuilder resource = (new StringBuilder()).append("META-INF/services/").append(jaxbContextFQCN);
/*     */       
/* 326 */       InputStream resourceStream = classLoader.getResourceAsStream(resource.toString());
/*     */       
/* 328 */       if (resourceStream != null) {
/* 329 */         r = new BufferedReader(new InputStreamReader(resourceStream, "UTF-8"));
/* 330 */         factoryClassName = r.readLine();
/* 331 */         if (factoryClassName != null) {
/* 332 */           factoryClassName = factoryClassName.trim();
/*     */         }
/* 334 */         r.close();
/* 335 */         return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */       } 
/* 337 */       logger.log(Level.FINE, "Unable to load:{0}", resource.toString());
/*     */     }
/* 339 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 341 */       throw new JAXBException(e);
/* 342 */     } catch (IOException e) {
/* 343 */       throw new JAXBException(e);
/*     */     } finally {
/*     */       try {
/* 346 */         if (r != null) {
/* 347 */           r.close();
/*     */         }
/* 349 */       } catch (IOException ex) {
/* 350 */         Logger.getLogger(ContextFinder.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 355 */     logger.fine("Trying to create the platform default provider");
/* 356 */     return newInstance(contextPath, "com.sun.xml.internal.bind.v2.ContextFactory", classLoader, properties);
/*     */   }
/*     */ 
/*     */   
/*     */   static JAXBContext find(Class[] classes, Map properties) throws JAXBException {
/* 361 */     String jaxbContextFQCN = JAXBContext.class.getName();
/*     */ 
/*     */ 
/*     */     
/* 365 */     for (Class c : classes) {
/*     */       
/* 367 */       ClassLoader classLoader = getClassClassLoader(c);
/* 368 */       Package pkg = c.getPackage();
/* 369 */       if (pkg != null) {
/*     */         
/* 371 */         String packageName = pkg.getName().replace('.', '/');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 380 */         String resourceName = packageName + "/jaxb.properties";
/* 381 */         logger.log(Level.FINE, "Trying to locate {0}", resourceName);
/* 382 */         Properties props = loadJAXBProperties(classLoader, resourceName);
/* 383 */         if (props == null) {
/* 384 */           logger.fine("  not found");
/*     */         } else {
/* 386 */           logger.fine("  found");
/* 387 */           if (props.containsKey("javax.xml.bind.context.factory")) {
/*     */             
/* 389 */             String str = props.getProperty("javax.xml.bind.context.factory").trim();
/* 390 */             return newInstance(classes, properties, str);
/*     */           } 
/* 392 */           throw new JAXBException(Messages.format("ContextFinder.MissingProperty", packageName, "javax.xml.bind.context.factory"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 398 */     logger.log(Level.FINE, "Checking system property {0}", "javax.xml.bind.context.factory");
/* 399 */     String factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction("javax.xml.bind.context.factory"));
/* 400 */     if (factoryClassName != null) {
/* 401 */       logger.log(Level.FINE, "  found {0}", factoryClassName);
/* 402 */       return newInstance(classes, properties, factoryClassName);
/*     */     } 
/* 404 */     logger.fine("  not found");
/* 405 */     logger.log(Level.FINE, "Checking system property {0}", jaxbContextFQCN);
/* 406 */     factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction(jaxbContextFQCN));
/* 407 */     if (factoryClassName != null) {
/* 408 */       logger.log(Level.FINE, "  found {0}", factoryClassName);
/* 409 */       return newInstance(classes, properties, factoryClassName);
/*     */     } 
/* 411 */     logger.fine("  not found");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     Class jaxbContext = lookupJaxbContextUsingOsgiServiceLoader();
/* 417 */     if (jaxbContext != null) {
/* 418 */       logger.fine("OSGi environment detected");
/* 419 */       return newInstance(classes, properties, jaxbContext);
/*     */     } 
/*     */ 
/*     */     
/* 423 */     logger.fine("Checking META-INF/services");
/* 424 */     BufferedReader r = null; try {
/*     */       URL resourceURL;
/* 426 */       String resource = "META-INF/services/" + jaxbContextFQCN;
/* 427 */       ClassLoader classLoader = getContextClassLoader();
/*     */       
/* 429 */       if (classLoader == null) {
/* 430 */         resourceURL = ClassLoader.getSystemResource(resource);
/*     */       } else {
/* 432 */         resourceURL = classLoader.getResource(resource);
/*     */       } 
/* 434 */       if (resourceURL != null) {
/* 435 */         logger.log(Level.FINE, "Reading {0}", resourceURL);
/* 436 */         r = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "UTF-8"));
/* 437 */         factoryClassName = r.readLine();
/* 438 */         if (factoryClassName != null) {
/* 439 */           factoryClassName = factoryClassName.trim();
/*     */         }
/* 441 */         return newInstance(classes, properties, factoryClassName);
/*     */       } 
/* 443 */       logger.log(Level.FINE, "Unable to find: {0}", resource);
/*     */     }
/* 445 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 447 */       throw new JAXBException(e);
/* 448 */     } catch (IOException e) {
/* 449 */       throw new JAXBException(e);
/*     */     } finally {
/* 451 */       if (r != null) {
/*     */         try {
/* 453 */           r.close();
/* 454 */         } catch (IOException ex) {
/* 455 */           logger.log(Level.FINE, "Unable to close stream", ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 461 */     logger.fine("Trying to create the platform default provider");
/* 462 */     return newInstance(classes, properties, "com.sun.xml.internal.bind.v2.ContextFactory");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class lookupJaxbContextUsingOsgiServiceLoader() {
/*     */     try {
/* 468 */       Class<?> target = Class.forName("com.sun.org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 469 */       Method m = target.getMethod("lookupProviderClasses", new Class[] { Class.class });
/* 470 */       Iterator<Class<?>> iter = ((Iterable)m.invoke(null, new Object[] { JAXBContext.class })).iterator();
/* 471 */       return iter.hasNext() ? iter.next() : null;
/* 472 */     } catch (Exception e) {
/* 473 */       logger.log(Level.FINE, "Unable to find from OSGi: javax.xml.bind.JAXBContext");
/* 474 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties loadJAXBProperties(ClassLoader classLoader, String propFileName) throws JAXBException {
/* 482 */     Properties props = null;
/*     */     
/*     */     try {
/*     */       URL url;
/* 486 */       if (classLoader == null) {
/* 487 */         url = ClassLoader.getSystemResource(propFileName);
/*     */       } else {
/* 489 */         url = classLoader.getResource(propFileName);
/*     */       } 
/* 491 */       if (url != null) {
/* 492 */         logger.log(Level.FINE, "loading props from {0}", url);
/* 493 */         props = new Properties();
/* 494 */         InputStream is = url.openStream();
/* 495 */         props.load(is);
/* 496 */         is.close();
/*     */       } 
/* 498 */     } catch (IOException ioe) {
/* 499 */       logger.log(Level.FINE, "Unable to load " + propFileName, ioe);
/* 500 */       throw new JAXBException(ioe.toString(), ioe);
/*     */     } 
/*     */     
/* 503 */     return props;
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
/*     */   static URL which(Class clazz, ClassLoader loader) {
/* 521 */     String classnameAsResource = clazz.getName().replace('.', '/') + ".class";
/*     */     
/* 523 */     if (loader == null) {
/* 524 */       loader = getSystemClassLoader();
/*     */     }
/*     */     
/* 527 */     return loader.getResource(classnameAsResource);
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
/*     */   static URL which(Class clazz) {
/* 543 */     return which(clazz, getClassClassLoader(clazz));
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
/*     */   private static Class safeLoadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
/* 565 */     logger.log(Level.FINE, "Trying to load {0}", className);
/*     */     
/*     */     try {
/* 568 */       SecurityManager s = System.getSecurityManager();
/* 569 */       if (s != null) {
/* 570 */         int i = className.lastIndexOf('.');
/* 571 */         if (i != -1) {
/* 572 */           s.checkPackageAccess(className.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 576 */       if (classLoader == null) {
/* 577 */         return Class.forName(className);
/*     */       }
/* 579 */       return classLoader.loadClass(className);
/*     */     }
/* 581 */     catch (SecurityException se) {
/*     */       
/* 583 */       if ("com.sun.xml.internal.bind.v2.ContextFactory".equals(className)) {
/* 584 */         return Class.forName(className);
/*     */       }
/* 586 */       throw se;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ClassLoader getContextClassLoader() {
/* 591 */     if (System.getSecurityManager() == null) {
/* 592 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 594 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 597 */             return Thread.currentThread().getContextClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ClassLoader getClassClassLoader(final Class c) {
/* 604 */     if (System.getSecurityManager() == null) {
/* 605 */       return c.getClassLoader();
/*     */     }
/* 607 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 610 */             return c.getClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ClassLoader getSystemClassLoader() {
/* 617 */     if (System.getSecurityManager() == null) {
/* 618 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/* 620 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 623 */             return ClassLoader.getSystemClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/ContextFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */