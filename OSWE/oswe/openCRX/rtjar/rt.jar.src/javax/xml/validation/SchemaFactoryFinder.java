/*     */ package javax.xml.validation;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SchemaFactoryFinder
/*     */ {
/*     */   private static boolean debug = false;
/*  54 */   private static final SecuritySupport ss = new SecuritySupport();
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xerces.internal";
/*     */   
/*  59 */   private static final Properties cacheProps = new Properties();
/*     */ 
/*     */   
/*     */   private static volatile boolean firstTime = true;
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  69 */       debug = (ss.getSystemProperty("jaxp.debug") != null);
/*  70 */     } catch (Exception unused) {
/*  71 */       debug = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void debugPrintln(String msg) {
/*  81 */     if (debug) {
/*  82 */       System.err.println("JAXP: " + msg);
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
/*     */   public SchemaFactoryFinder(ClassLoader loader) {
/* 103 */     this.classLoader = loader;
/* 104 */     if (debug) {
/* 105 */       debugDisplayClassLoader();
/*     */     }
/*     */   }
/*     */   
/*     */   private void debugDisplayClassLoader() {
/*     */     try {
/* 111 */       if (this.classLoader == ss.getContextClassLoader()) {
/* 112 */         debugPrintln("using thread context class loader (" + this.classLoader + ") for search");
/*     */         return;
/*     */       } 
/* 115 */     } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (this.classLoader == ClassLoader.getSystemClassLoader()) {
/* 120 */       debugPrintln("using system class loader (" + this.classLoader + ") for search");
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     debugPrintln("using class loader (" + this.classLoader + ") for search");
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
/*     */   public SchemaFactory newFactory(String schemaLanguage) {
/* 143 */     if (schemaLanguage == null) {
/* 144 */       throw new NullPointerException();
/*     */     }
/* 146 */     SchemaFactory f = _newFactory(schemaLanguage);
/* 147 */     if (f != null) {
/* 148 */       debugPrintln("factory '" + f.getClass().getName() + "' was found for " + schemaLanguage);
/*     */     } else {
/* 150 */       debugPrintln("unable to find a factory for " + schemaLanguage);
/*     */     } 
/* 152 */     return f;
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
/*     */   private SchemaFactory _newFactory(String schemaLanguage) {
/* 165 */     String propertyName = SERVICE_CLASS.getName() + ":" + schemaLanguage;
/*     */ 
/*     */     
/*     */     try {
/* 169 */       debugPrintln("Looking up system property '" + propertyName + "'");
/* 170 */       String r = ss.getSystemProperty(propertyName);
/* 171 */       if (r != null)
/* 172 */       { debugPrintln("The value is '" + r + "'");
/* 173 */         SchemaFactory sf = createInstance(r);
/* 174 */         if (sf != null) return sf;  }
/*     */       else
/* 176 */       { debugPrintln("The property is undefined."); } 
/* 177 */     } catch (Throwable t) {
/* 178 */       if (debug) {
/* 179 */         debugPrintln("failed to look up system property '" + propertyName + "'");
/* 180 */         t.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     String javah = ss.getSystemProperty("java.home");
/* 185 */     String configFile = javah + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 191 */       if (firstTime) {
/* 192 */         synchronized (cacheProps) {
/* 193 */           if (firstTime) {
/* 194 */             File f = new File(configFile);
/* 195 */             firstTime = false;
/* 196 */             if (ss.doesFileExist(f)) {
/* 197 */               debugPrintln("Read properties file " + f);
/* 198 */               cacheProps.load(ss.getFileInputStream(f));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 203 */       String factoryClassName = cacheProps.getProperty(propertyName);
/* 204 */       debugPrintln("found " + factoryClassName + " in $java.home/jaxp.properties");
/*     */       
/* 206 */       if (factoryClassName != null) {
/* 207 */         SchemaFactory sf = createInstance(factoryClassName);
/* 208 */         if (sf != null) {
/* 209 */           return sf;
/*     */         }
/*     */       } 
/* 212 */     } catch (Exception ex) {
/* 213 */       if (debug) {
/* 214 */         ex.printStackTrace();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 219 */     SchemaFactory factoryImpl = findServiceProvider(schemaLanguage);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (factoryImpl != null) {
/* 226 */       return factoryImpl;
/*     */     }
/*     */ 
/*     */     
/* 230 */     if (schemaLanguage.equals("http://www.w3.org/2001/XMLSchema")) {
/* 231 */       debugPrintln("attempting to use the platform default XML Schema validator");
/* 232 */       return new XMLSchemaFactory();
/*     */     } 
/*     */     
/* 235 */     debugPrintln("all things were tried, but none was found. bailing out.");
/* 236 */     return null;
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
/* 247 */     boolean internal = false;
/* 248 */     if (System.getSecurityManager() != null && 
/* 249 */       className != null && className.startsWith("com.sun.org.apache.xerces.internal")) {
/* 250 */       internal = true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 255 */       if (this.classLoader != null && !internal) {
/* 256 */         clazz = Class.forName(className, false, this.classLoader);
/*     */       } else {
/* 258 */         clazz = Class.forName(className);
/*     */       } 
/* 260 */     } catch (Throwable t) {
/* 261 */       if (debug) {
/* 262 */         t.printStackTrace();
/*     */       }
/* 264 */       return null;
/*     */     } 
/*     */     
/* 267 */     return clazz;
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
/*     */   SchemaFactory createInstance(String className) {
/* 280 */     SchemaFactory schemaFactory = null;
/*     */     
/* 282 */     debugPrintln("createInstance(" + className + ")");
/*     */ 
/*     */     
/* 285 */     Class<?> clazz = createClass(className);
/* 286 */     if (clazz == null) {
/* 287 */       debugPrintln("failed to getClass(" + className + ")");
/* 288 */       return null;
/*     */     } 
/* 290 */     debugPrintln("loaded " + className + " from " + which(clazz));
/*     */ 
/*     */     
/*     */     try {
/* 294 */       if (!SchemaFactory.class.isAssignableFrom(clazz)) {
/* 295 */         throw new ClassCastException(clazz.getName() + " cannot be cast to " + SchemaFactory.class);
/*     */       }
/*     */       
/* 298 */       schemaFactory = (SchemaFactory)clazz.newInstance();
/* 299 */     } catch (ClassCastException classCastException) {
/* 300 */       debugPrintln("could not instantiate " + clazz.getName());
/* 301 */       if (debug) {
/* 302 */         classCastException.printStackTrace();
/*     */       }
/* 304 */       return null;
/* 305 */     } catch (IllegalAccessException illegalAccessException) {
/* 306 */       debugPrintln("could not instantiate " + clazz.getName());
/* 307 */       if (debug) {
/* 308 */         illegalAccessException.printStackTrace();
/*     */       }
/* 310 */       return null;
/* 311 */     } catch (InstantiationException instantiationException) {
/* 312 */       debugPrintln("could not instantiate " + clazz.getName());
/* 313 */       if (debug) {
/* 314 */         instantiationException.printStackTrace();
/*     */       }
/* 316 */       return null;
/*     */     } 
/*     */     
/* 319 */     return schemaFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSchemaLanguageSupportedBy(final SchemaFactory factory, final String schemaLanguage, AccessControlContext acc) {
/* 326 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Boolean run() {
/* 328 */             return Boolean.valueOf(factory.isSchemaLanguageSupported(schemaLanguage));
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
/*     */   private SchemaFactory findServiceProvider(final String schemaLanguage) {
/* 343 */     assert schemaLanguage != null;
/*     */     
/* 345 */     final AccessControlContext acc = AccessController.getContext();
/*     */     try {
/* 347 */       return AccessController.<SchemaFactory>doPrivileged(new PrivilegedAction<SchemaFactory>()
/*     */           {
/*     */             public SchemaFactory run() {
/* 350 */               ServiceLoader<SchemaFactory> loader = ServiceLoader.load(SchemaFactoryFinder.SERVICE_CLASS);
/* 351 */               for (SchemaFactory factory : loader) {
/*     */ 
/*     */                 
/* 354 */                 if (SchemaFactoryFinder.this.isSchemaLanguageSupportedBy(factory, schemaLanguage, acc)) {
/* 355 */                   return factory;
/*     */                 }
/*     */               } 
/* 358 */               return null;
/*     */             }
/*     */           });
/* 361 */     } catch (ServiceConfigurationError error) {
/* 362 */       throw new SchemaFactoryConfigurationError("Provider for " + SERVICE_CLASS + " cannot be created", error);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 367 */   private static final Class<SchemaFactory> SERVICE_CLASS = SchemaFactory.class;
/*     */ 
/*     */   
/*     */   private static String which(Class<?> clazz) {
/* 371 */     return which(clazz.getName(), clazz.getClassLoader());
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
/* 384 */     String classnameAsResource = classname.replace('.', '/') + ".class";
/*     */     
/* 386 */     if (loader == null) loader = ClassLoader.getSystemClassLoader();
/*     */ 
/*     */     
/* 389 */     URL it = ss.getResourceAsURL(loader, classnameAsResource);
/* 390 */     if (it != null) {
/* 391 */       return it.toString();
/*     */     }
/* 393 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/validation/SchemaFactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */