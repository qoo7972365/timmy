/*     */ package javax.sql.rowset.spi;
/*     */ 
/*     */ import com.sun.rowset.providers.RIOptimisticProvider;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.sql.SQLPermission;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.PropertyPermission;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.NotContextException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncFactory
/*     */ {
/*     */   public static final String ROWSET_SYNC_PROVIDER = "rowset.provider.classname";
/*     */   public static final String ROWSET_SYNC_VENDOR = "rowset.provider.vendor";
/*     */   public static final String ROWSET_SYNC_PROVIDER_VERSION = "rowset.provider.version";
/*     */   
/*     */   private SyncFactory() {}
/*     */   
/* 236 */   private static String ROWSET_PROPERTIES = "rowset.properties";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   private static final SQLPermission SET_SYNCFACTORY_PERMISSION = new SQLPermission("setSyncFactory");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Context ic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile Logger rsLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Hashtable<String, SyncProvider> implementations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void registerProvider(String paramString) throws SyncFactoryException {
/* 291 */     ProviderImpl providerImpl = new ProviderImpl();
/* 292 */     providerImpl.setClassname(paramString);
/* 293 */     initMapIfNecessary();
/* 294 */     implementations.put(paramString, providerImpl);
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
/*     */   public static SyncFactory getSyncFactory() {
/* 309 */     return SyncFactoryHolder.factory;
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
/*     */   public static synchronized void unregisterProvider(String paramString) throws SyncFactoryException {
/* 322 */     initMapIfNecessary();
/* 323 */     if (implementations.containsKey(paramString))
/* 324 */       implementations.remove(paramString); 
/*     */   }
/*     */   
/* 327 */   private static String colon = ":";
/* 328 */   private static String strFileSep = "/";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void initMapIfNecessary() throws SyncFactoryException {
/* 335 */     Properties properties = new Properties();
/*     */     
/* 337 */     if (implementations == null) {
/* 338 */       String str; implementations = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*     */         try {
/* 358 */           str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */                 public String run() {
/* 360 */                   return System.getProperty("rowset.properties");
/*     */                 }
/*     */               },  (AccessControlContext)null, new Permission[] { new PropertyPermission("rowset.properties", "read") });
/* 363 */         } catch (Exception exception) {
/* 364 */           System.out.println("errorget rowset.properties: " + exception);
/* 365 */           str = null;
/*     */         } 
/*     */         
/* 368 */         if (str != null) {
/*     */ 
/*     */           
/* 371 */           ROWSET_PROPERTIES = str;
/* 372 */           try (FileInputStream null = new FileInputStream(ROWSET_PROPERTIES)) {
/* 373 */             properties.load(fileInputStream);
/*     */           } 
/* 375 */           parseProperties(properties);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 381 */         ROWSET_PROPERTIES = "javax" + strFileSep + "sql" + strFileSep + "rowset" + strFileSep + "rowset.properties";
/*     */ 
/*     */ 
/*     */         
/* 385 */         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */         
/*     */         try {
/* 388 */           AccessController.doPrivileged(() -> {
/* 389 */                 try (InputStream null = (paramClassLoader == null) ? ClassLoader.getSystemResourceAsStream(ROWSET_PROPERTIES) : paramClassLoader.getResourceAsStream(ROWSET_PROPERTIES)) {
/*     */                   if (inputStream == null) {
/*     */                     throw new SyncFactoryException("Resource " + ROWSET_PROPERTIES + " not found");
/*     */                   }
/*     */                   
/*     */                   paramProperties.load(inputStream);
/*     */                 } 
/*     */                 
/*     */                 return null;
/*     */               });
/* 399 */         } catch (PrivilegedActionException privilegedActionException) {
/* 400 */           Exception exception = privilegedActionException.getException();
/* 401 */           if (exception instanceof SyncFactoryException) {
/* 402 */             throw (SyncFactoryException)exception;
/*     */           }
/* 404 */           SyncFactoryException syncFactoryException = new SyncFactoryException();
/* 405 */           syncFactoryException.initCause(privilegedActionException.getException());
/* 406 */           throw syncFactoryException;
/*     */         } 
/*     */ 
/*     */         
/* 410 */         parseProperties(properties);
/*     */ 
/*     */       
/*     */       }
/* 414 */       catch (FileNotFoundException null) {
/* 415 */         throw new SyncFactoryException("Cannot locate properties file: " + str);
/* 416 */       } catch (IOException null) {
/* 417 */         throw new SyncFactoryException("IOException: " + str);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       properties.clear();
/*     */       
/*     */       try {
/* 427 */         str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */               public String run() {
/* 429 */                 return System.getProperty("rowset.provider.classname");
/*     */               }
/*     */             },  (AccessControlContext)null, new Permission[] { new PropertyPermission("rowset.provider.classname", "read") });
/* 432 */       } catch (Exception exception) {
/* 433 */         str = null;
/*     */       } 
/*     */       
/* 436 */       if (str != null) {
/* 437 */         byte b = 0;
/* 438 */         if (str.indexOf(colon) > 0) {
/* 439 */           StringTokenizer stringTokenizer = new StringTokenizer(str, colon);
/* 440 */           while (stringTokenizer.hasMoreElements()) {
/* 441 */             properties.put("rowset.provider.classname." + b, stringTokenizer.nextToken());
/* 442 */             b++;
/*     */           } 
/*     */         } else {
/* 445 */           properties.put("rowset.provider.classname", str);
/*     */         } 
/* 447 */         parseProperties(properties);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean debug = false;
/*     */ 
/*     */ 
/*     */   
/* 460 */   private static int providerImplIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void parseProperties(Properties paramProperties) {
/* 468 */     ProviderImpl providerImpl = null;
/* 469 */     String str = null;
/* 470 */     String[] arrayOfString = null;
/*     */     
/* 472 */     for (Enumeration<?> enumeration = paramProperties.propertyNames(); enumeration.hasMoreElements(); ) {
/*     */       
/* 474 */       String str1 = (String)enumeration.nextElement();
/*     */       
/* 476 */       int i = str1.length();
/*     */       
/* 478 */       if (str1.startsWith("rowset.provider.classname")) {
/*     */         
/* 480 */         providerImpl = new ProviderImpl();
/* 481 */         providerImpl.setIndex(providerImplIndex++);
/*     */         
/* 483 */         if (i == "rowset.provider.classname".length()) {
/*     */           
/* 485 */           arrayOfString = getPropertyNames(false);
/*     */         } else {
/*     */           
/* 488 */           arrayOfString = getPropertyNames(true, str1.substring(i - 1));
/*     */         } 
/*     */         
/* 491 */         str = paramProperties.getProperty(arrayOfString[0]);
/* 492 */         providerImpl.setClassname(str);
/* 493 */         providerImpl.setVendor(paramProperties.getProperty(arrayOfString[1]));
/* 494 */         providerImpl.setVersion(paramProperties.getProperty(arrayOfString[2]));
/* 495 */         implementations.put(str, providerImpl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getPropertyNames(boolean paramBoolean) {
/* 504 */     return getPropertyNames(paramBoolean, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getPropertyNames(boolean paramBoolean, String paramString) {
/* 513 */     String str = ".";
/* 514 */     String[] arrayOfString = { "rowset.provider.classname", "rowset.provider.vendor", "rowset.provider.version" };
/*     */ 
/*     */ 
/*     */     
/* 518 */     if (paramBoolean) {
/* 519 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 520 */         arrayOfString[b] = arrayOfString[b] + str + paramString;
/*     */       }
/*     */ 
/*     */       
/* 524 */       return arrayOfString;
/*     */     } 
/* 526 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void showImpl(ProviderImpl paramProviderImpl) {
/* 534 */     System.out.println("Provider implementation:");
/* 535 */     System.out.println("Classname: " + paramProviderImpl.getClassname());
/* 536 */     System.out.println("Vendor: " + paramProviderImpl.getVendor());
/* 537 */     System.out.println("Version: " + paramProviderImpl.getVersion());
/* 538 */     System.out.println("Impl index: " + paramProviderImpl.getIndex());
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
/*     */   public static SyncProvider getInstance(String paramString) throws SyncFactoryException {
/* 553 */     if (paramString == null) {
/* 554 */       throw new SyncFactoryException("The providerID cannot be null");
/*     */     }
/*     */     
/* 557 */     initMapIfNecessary();
/* 558 */     initJNDIContext();
/*     */     
/* 560 */     ProviderImpl providerImpl = (ProviderImpl)implementations.get(paramString);
/*     */     
/* 562 */     if (providerImpl == null)
/*     */     {
/* 564 */       return new RIOptimisticProvider();
/*     */     }
/*     */     
/*     */     try {
/* 568 */       ReflectUtil.checkPackageAccess(paramString);
/* 569 */     } catch (AccessControlException accessControlException) {
/* 570 */       SyncFactoryException syncFactoryException = new SyncFactoryException();
/* 571 */       syncFactoryException.initCause(accessControlException);
/* 572 */       throw syncFactoryException;
/*     */     } 
/*     */ 
/*     */     
/* 576 */     Class<?> clazz = null;
/*     */     try {
/* 578 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 586 */       clazz = Class.forName(paramString, true, classLoader);
/*     */       
/* 588 */       if (clazz != null) {
/* 589 */         return (SyncProvider)clazz.newInstance();
/*     */       }
/* 591 */       return new RIOptimisticProvider();
/*     */     
/*     */     }
/* 594 */     catch (IllegalAccessException illegalAccessException) {
/* 595 */       throw new SyncFactoryException("IllegalAccessException: " + illegalAccessException.getMessage());
/* 596 */     } catch (InstantiationException instantiationException) {
/* 597 */       throw new SyncFactoryException("InstantiationException: " + instantiationException.getMessage());
/* 598 */     } catch (ClassNotFoundException classNotFoundException) {
/* 599 */       throw new SyncFactoryException("ClassNotFoundException: " + classNotFoundException.getMessage());
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
/*     */   public static Enumeration<SyncProvider> getRegisteredProviders() throws SyncFactoryException {
/* 619 */     initMapIfNecessary();
/*     */ 
/*     */     
/* 622 */     return implementations.elements();
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
/*     */   public static void setLogger(Logger paramLogger) {
/* 648 */     SecurityManager securityManager = System.getSecurityManager();
/* 649 */     if (securityManager != null) {
/* 650 */       securityManager.checkPermission(SET_SYNCFACTORY_PERMISSION);
/*     */     }
/*     */     
/* 653 */     if (paramLogger == null) {
/* 654 */       throw new NullPointerException("You must provide a Logger");
/*     */     }
/* 656 */     rsLogger = paramLogger;
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
/*     */   public static void setLogger(Logger paramLogger, Level paramLevel) {
/* 685 */     SecurityManager securityManager = System.getSecurityManager();
/* 686 */     if (securityManager != null) {
/* 687 */       securityManager.checkPermission(SET_SYNCFACTORY_PERMISSION);
/*     */     }
/*     */     
/* 690 */     if (paramLogger == null) {
/* 691 */       throw new NullPointerException("You must provide a Logger");
/*     */     }
/* 693 */     paramLogger.setLevel(paramLevel);
/* 694 */     rsLogger = paramLogger;
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
/*     */   public static Logger getLogger() throws SyncFactoryException {
/* 706 */     Logger logger = rsLogger;
/*     */     
/* 708 */     if (logger == null) {
/* 709 */       throw new SyncFactoryException("(SyncFactory) : No logger has been set");
/*     */     }
/*     */     
/* 712 */     return logger;
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
/*     */   public static synchronized void setJNDIContext(Context paramContext) throws SyncFactoryException {
/* 735 */     SecurityManager securityManager = System.getSecurityManager();
/* 736 */     if (securityManager != null) {
/* 737 */       securityManager.checkPermission(SET_SYNCFACTORY_PERMISSION);
/*     */     }
/* 739 */     if (paramContext == null) {
/* 740 */       throw new SyncFactoryException("Invalid JNDI context supplied");
/*     */     }
/* 742 */     ic = paramContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void initJNDIContext() throws SyncFactoryException {
/* 752 */     if (ic != null && !lazyJNDICtxRefresh) {
/*     */       try {
/* 754 */         parseProperties(parseJNDIContext());
/* 755 */         lazyJNDICtxRefresh = true;
/* 756 */       } catch (NamingException namingException) {
/* 757 */         namingException.printStackTrace();
/* 758 */         throw new SyncFactoryException("SPI: NamingException: " + namingException.getExplanation());
/* 759 */       } catch (Exception exception) {
/* 760 */         exception.printStackTrace();
/* 761 */         throw new SyncFactoryException("SPI: Exception: " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean lazyJNDICtxRefresh = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties parseJNDIContext() throws NamingException {
/* 776 */     NamingEnumeration<Binding> namingEnumeration = ic.listBindings("");
/* 777 */     Properties properties = new Properties();
/*     */ 
/*     */     
/* 780 */     enumerateBindings(namingEnumeration, properties);
/*     */     
/* 782 */     return properties;
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
/*     */   private static void enumerateBindings(NamingEnumeration<?> paramNamingEnumeration, Properties paramProperties) throws NamingException {
/* 794 */     boolean bool = false;
/*     */     
/*     */     try {
/* 797 */       Binding binding = null;
/* 798 */       Object object = null;
/* 799 */       String str = null;
/* 800 */       while (paramNamingEnumeration.hasMore()) {
/* 801 */         binding = (Binding)paramNamingEnumeration.next();
/* 802 */         str = binding.getName();
/* 803 */         object = binding.getObject();
/*     */         
/* 805 */         if (!(ic.lookup(str) instanceof Context))
/*     */         {
/* 807 */           if (ic.lookup(str) instanceof SyncProvider) {
/* 808 */             bool = true;
/*     */           }
/*     */         }
/*     */         
/* 812 */         if (bool) {
/* 813 */           SyncProvider syncProvider = (SyncProvider)object;
/* 814 */           paramProperties.put("rowset.provider.classname", syncProvider
/* 815 */               .getProviderID());
/* 816 */           bool = false;
/*     */         }
/*     */       
/*     */       } 
/* 820 */     } catch (NotContextException notContextException) {
/* 821 */       paramNamingEnumeration.next();
/*     */       
/* 823 */       enumerateBindings(paramNamingEnumeration, paramProperties);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SyncFactoryHolder
/*     */   {
/* 831 */     static final SyncFactory factory = new SyncFactory();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/rowset/spi/SyncFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */