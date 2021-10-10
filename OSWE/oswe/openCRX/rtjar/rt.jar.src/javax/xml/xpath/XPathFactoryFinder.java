/*     */ package javax.xml.xpath;
/*     */ 
/*     */ import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Properties;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XPathFactoryFinder
/*     */ {
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xpath.internal";
/*  48 */   private static final SecuritySupport ss = new SecuritySupport();
/*     */   
/*     */   private static boolean debug = false;
/*     */   
/*     */   static {
/*     */     try {
/*  54 */       debug = (ss.getSystemProperty("jaxp.debug") != null);
/*  55 */     } catch (Exception unused) {
/*  56 */       debug = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final Properties cacheProps = new Properties();
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile boolean firstTime = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */   
/*     */   private static void debugPrintln(String msg) {
/*  76 */     if (debug) {
/*  77 */       System.err.println("JAXP: " + msg);
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
/*     */   public XPathFactoryFinder(ClassLoader loader) {
/*  97 */     this.classLoader = loader;
/*  98 */     if (debug) {
/*  99 */       debugDisplayClassLoader();
/*     */     }
/*     */   }
/*     */   
/*     */   private void debugDisplayClassLoader() {
/*     */     try {
/* 105 */       if (this.classLoader == ss.getContextClassLoader()) {
/* 106 */         debugPrintln("using thread context class loader (" + this.classLoader + ") for search");
/*     */         return;
/*     */       } 
/* 109 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (this.classLoader == ClassLoader.getSystemClassLoader()) {
/* 114 */       debugPrintln("using system class loader (" + this.classLoader + ") for search");
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     debugPrintln("using class loader (" + this.classLoader + ") for search");
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
/*     */   public XPathFactory newFactory(String uri) throws XPathFactoryConfigurationException {
/* 134 */     if (uri == null) {
/* 135 */       throw new NullPointerException();
/*     */     }
/* 137 */     XPathFactory f = _newFactory(uri);
/* 138 */     if (f != null) {
/* 139 */       debugPrintln("factory '" + f.getClass().getName() + "' was found for " + uri);
/*     */     } else {
/* 141 */       debugPrintln("unable to find a factory for " + uri);
/*     */     } 
/* 143 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XPathFactory _newFactory(String uri) throws XPathFactoryConfigurationException {
/* 154 */     XPathFactory xpathFactory = null;
/*     */     
/* 156 */     String propertyName = SERVICE_CLASS.getName() + ":" + uri;
/*     */ 
/*     */     
/*     */     try {
/* 160 */       debugPrintln("Looking up system property '" + propertyName + "'");
/* 161 */       String r = ss.getSystemProperty(propertyName);
/* 162 */       if (r != null)
/* 163 */       { debugPrintln("The value is '" + r + "'");
/* 164 */         xpathFactory = createInstance(r);
/* 165 */         if (xpathFactory != null) {
/* 166 */           return xpathFactory;
/*     */         } }
/*     */       else
/* 169 */       { debugPrintln("The property is undefined."); } 
/* 170 */     } catch (Throwable t) {
/* 171 */       if (debug) {
/* 172 */         debugPrintln("failed to look up system property '" + propertyName + "'");
/* 173 */         t.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     String javah = ss.getSystemProperty("java.home");
/* 178 */     String configFile = javah + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 183 */       if (firstTime) {
/* 184 */         synchronized (cacheProps) {
/* 185 */           if (firstTime) {
/* 186 */             File f = new File(configFile);
/* 187 */             firstTime = false;
/* 188 */             if (ss.doesFileExist(f)) {
/* 189 */               debugPrintln("Read properties file " + f);
/* 190 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 195 */       String factoryClassName = cacheProps.getProperty(propertyName);
/* 196 */       debugPrintln("found " + factoryClassName + " in $java.home/jaxp.properties");
/*     */       
/* 198 */       if (factoryClassName != null) {
/* 199 */         xpathFactory = createInstance(factoryClassName);
/* 200 */         if (xpathFactory != null) {
/* 201 */           return xpathFactory;
/*     */         }
/*     */       } 
/* 204 */     } catch (Exception ex) {
/* 205 */       if (debug) {
/* 206 */         ex.printStackTrace();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 211 */     assert xpathFactory == null;
/* 212 */     xpathFactory = findServiceProvider(uri);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (xpathFactory != null) {
/* 219 */       return xpathFactory;
/*     */     }
/*     */ 
/*     */     
/* 223 */     if (uri.equals("http://java.sun.com/jaxp/xpath/dom")) {
/* 224 */       debugPrintln("attempting to use the platform default W3C DOM XPath lib");
/* 225 */       return new XPathFactoryImpl();
/*     */     } 
/*     */     
/* 228 */     debugPrintln("all things were tried, but none was found. bailing out.");
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> createClass(String className) {
/*     */     Class<?> clazz;
/* 240 */     boolean internal = false;
/* 241 */     if (System.getSecurityManager() != null && 
/* 242 */       className != null && className.startsWith("com.sun.org.apache.xpath.internal")) {
/* 243 */       internal = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 249 */       if (this.classLoader != null && !internal) {
/* 250 */         clazz = Class.forName(className, false, this.classLoader);
/*     */       } else {
/* 252 */         clazz = Class.forName(className);
/*     */       } 
/* 254 */     } catch (Throwable t) {
/* 255 */       if (debug) {
/* 256 */         t.printStackTrace();
/*     */       }
/* 258 */       return null;
/*     */     } 
/*     */     
/* 261 */     return clazz;
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
/*     */   XPathFactory createInstance(String className) throws XPathFactoryConfigurationException {
/* 276 */     XPathFactory xPathFactory = null;
/*     */     
/* 278 */     debugPrintln("createInstance(" + className + ")");
/*     */ 
/*     */     
/* 281 */     Class<?> clazz = createClass(className);
/* 282 */     if (clazz == null) {
/* 283 */       debugPrintln("failed to getClass(" + className + ")");
/* 284 */       return null;
/*     */     } 
/* 286 */     debugPrintln("loaded " + className + " from " + which(clazz));
/*     */ 
/*     */     
/*     */     try {
/* 290 */       xPathFactory = (XPathFactory)clazz.newInstance();
/* 291 */     } catch (ClassCastException classCastException) {
/* 292 */       debugPrintln("could not instantiate " + clazz.getName());
/* 293 */       if (debug) {
/* 294 */         classCastException.printStackTrace();
/*     */       }
/* 296 */       return null;
/* 297 */     } catch (IllegalAccessException illegalAccessException) {
/* 298 */       debugPrintln("could not instantiate " + clazz.getName());
/* 299 */       if (debug) {
/* 300 */         illegalAccessException.printStackTrace();
/*     */       }
/* 302 */       return null;
/* 303 */     } catch (InstantiationException instantiationException) {
/* 304 */       debugPrintln("could not instantiate " + clazz.getName());
/* 305 */       if (debug) {
/* 306 */         instantiationException.printStackTrace();
/*     */       }
/* 308 */       return null;
/*     */     } 
/*     */     
/* 311 */     return xPathFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isObjectModelSupportedBy(final XPathFactory factory, final String objectModel, AccessControlContext acc) {
/* 318 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Boolean run() {
/* 320 */             return Boolean.valueOf(factory.isObjectModelSupported(objectModel));
/*     */           }
/*     */         },  acc)).booleanValue();
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
/*     */   private XPathFactory findServiceProvider(final String objectModel) throws XPathFactoryConfigurationException {
/* 337 */     assert objectModel != null;
/*     */     
/* 339 */     final AccessControlContext acc = AccessController.getContext();
/*     */     try {
/* 341 */       return AccessController.<XPathFactory>doPrivileged(new PrivilegedAction<XPathFactory>()
/*     */           {
/*     */             public XPathFactory run() {
/* 344 */               ServiceLoader<XPathFactory> loader = ServiceLoader.load(XPathFactoryFinder.SERVICE_CLASS);
/* 345 */               for (XPathFactory factory : loader) {
/*     */ 
/*     */                 
/* 348 */                 if (XPathFactoryFinder.this.isObjectModelSupportedBy(factory, objectModel, acc)) {
/* 349 */                   return factory;
/*     */                 }
/*     */               } 
/* 352 */               return null;
/*     */             }
/*     */           });
/* 355 */     } catch (ServiceConfigurationError error) {
/* 356 */       throw new XPathFactoryConfigurationException(error);
/*     */     } 
/*     */   }
/*     */   
/* 360 */   private static final Class<XPathFactory> SERVICE_CLASS = XPathFactory.class;
/*     */   
/*     */   private static String which(Class clazz) {
/* 363 */     return which(clazz.getName(), clazz.getClassLoader());
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
/*     */   private static String which(String classname, ClassLoader loader) {
/* 376 */     String classnameAsResource = classname.replace('.', '/') + ".class";
/*     */     
/* 378 */     if (loader == null) loader = ClassLoader.getSystemClassLoader();
/*     */ 
/*     */     
/* 381 */     URL it = ss.getResourceAsURL(loader, classnameAsResource);
/* 382 */     if (it != null) {
/* 383 */       return it.toString();
/*     */     }
/* 385 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/xpath/XPathFactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */