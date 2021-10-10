/*      */ package java.security;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.net.URL;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.security.jca.GetInstance;
/*      */ import sun.security.jca.ProviderList;
/*      */ import sun.security.jca.Providers;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.PropertyExpander;
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
/*      */ public final class Security
/*      */ {
/*   53 */   private static final Debug sdebug = Debug.getInstance("properties");
/*      */   
/*      */   private static Properties props;
/*      */ 
/*      */   
/*      */   private static class ProviderProperty
/*      */   {
/*      */     String className;
/*      */     
/*      */     Provider provider;
/*      */ 
/*      */     
/*      */     private ProviderProperty() {}
/*      */   }
/*      */   
/*      */   static {
/*   69 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*   71 */             Security.initialize();
/*   72 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static void initialize() {
/*   78 */     props = new Properties();
/*   79 */     boolean bool1 = false;
/*   80 */     boolean bool2 = false;
/*      */ 
/*      */ 
/*      */     
/*   84 */     File file = securityPropFile("java.security");
/*   85 */     if (file.exists()) {
/*   86 */       BufferedInputStream bufferedInputStream = null;
/*      */       try {
/*   88 */         FileInputStream fileInputStream = new FileInputStream(file);
/*   89 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*   90 */         props.load(bufferedInputStream);
/*   91 */         bool1 = true;
/*      */         
/*   93 */         if (sdebug != null) {
/*   94 */           sdebug.println("reading security properties file: " + file);
/*      */         }
/*      */       }
/*   97 */       catch (IOException iOException) {
/*   98 */         if (sdebug != null) {
/*   99 */           sdebug.println("unable to load security properties from " + file);
/*      */           
/*  101 */           iOException.printStackTrace();
/*      */         } 
/*      */       } finally {
/*  104 */         if (bufferedInputStream != null) {
/*      */           try {
/*  106 */             bufferedInputStream.close();
/*  107 */           } catch (IOException iOException) {
/*  108 */             if (sdebug != null) {
/*  109 */               sdebug.println("unable to close input stream");
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  116 */     if ("true".equalsIgnoreCase(props
/*  117 */         .getProperty("security.overridePropertiesFile"))) {
/*      */ 
/*      */       
/*  120 */       String str = System.getProperty("java.security.properties");
/*  121 */       if (str != null && str.startsWith("=")) {
/*  122 */         bool2 = true;
/*  123 */         str = str.substring(1);
/*      */       } 
/*      */       
/*  126 */       if (bool2) {
/*  127 */         props = new Properties();
/*  128 */         if (sdebug != null) {
/*  129 */           sdebug
/*  130 */             .println("overriding other security properties files!");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  136 */       if (str != null) {
/*  137 */         BufferedInputStream bufferedInputStream = null;
/*      */         
/*      */         try {
/*      */           URL uRL;
/*  141 */           str = PropertyExpander.expand(str);
/*  142 */           file = new File(str);
/*  143 */           if (file.exists()) {
/*      */             
/*  145 */             uRL = new URL("file:" + file.getCanonicalPath());
/*      */           } else {
/*  147 */             uRL = new URL(str);
/*      */           } 
/*  149 */           bufferedInputStream = new BufferedInputStream(uRL.openStream());
/*  150 */           props.load(bufferedInputStream);
/*  151 */           bool1 = true;
/*      */           
/*  153 */           if (sdebug != null) {
/*  154 */             sdebug.println("reading security properties file: " + uRL);
/*      */             
/*  156 */             if (bool2) {
/*  157 */               sdebug
/*  158 */                 .println("overriding other security properties files!");
/*      */             }
/*      */           } 
/*  161 */         } catch (Exception exception) {
/*  162 */           if (sdebug != null) {
/*  163 */             sdebug
/*  164 */               .println("unable to load security properties from " + str);
/*      */             
/*  166 */             exception.printStackTrace();
/*      */           } 
/*      */         } finally {
/*  169 */           if (bufferedInputStream != null) {
/*      */             try {
/*  171 */               bufferedInputStream.close();
/*  172 */             } catch (IOException iOException) {
/*  173 */               if (sdebug != null) {
/*  174 */                 sdebug.println("unable to close input stream");
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  182 */     if (!bool1) {
/*  183 */       initializeStatic();
/*  184 */       if (sdebug != null) {
/*  185 */         sdebug.println("unable to load security properties -- using defaults");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void initializeStatic() {
/*  197 */     props.put("security.provider.1", "sun.security.provider.Sun");
/*  198 */     props.put("security.provider.2", "sun.security.rsa.SunRsaSign");
/*  199 */     props.put("security.provider.3", "com.sun.net.ssl.internal.ssl.Provider");
/*  200 */     props.put("security.provider.4", "com.sun.crypto.provider.SunJCE");
/*  201 */     props.put("security.provider.5", "sun.security.jgss.SunProvider");
/*  202 */     props.put("security.provider.6", "com.sun.security.sasl.Provider");
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
/*      */   private static File securityPropFile(String paramString) {
/*  214 */     String str = File.separator;
/*  215 */     return new File(System.getProperty("java.home") + str + "lib" + str + "security" + str + paramString);
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
/*      */   private static ProviderProperty getProviderProperty(String paramString) {
/*  227 */     ProviderProperty providerProperty = null;
/*      */     
/*  229 */     List<Provider> list = Providers.getProviderList().providers();
/*  230 */     for (byte b = 0; b < list.size(); b++) {
/*      */       
/*  232 */       String str1 = null;
/*  233 */       Provider provider = list.get(b);
/*  234 */       String str2 = provider.getProperty(paramString);
/*      */       
/*  236 */       if (str2 == null) {
/*      */ 
/*      */         
/*  239 */         Enumeration<Object> enumeration = provider.keys();
/*  240 */         while (enumeration.hasMoreElements() && str2 == null) {
/*  241 */           str1 = (String)enumeration.nextElement();
/*  242 */           if (paramString.equalsIgnoreCase(str1)) {
/*  243 */             str2 = provider.getProperty(str1);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  249 */       if (str2 != null) {
/*  250 */         ProviderProperty providerProperty1 = new ProviderProperty();
/*  251 */         providerProperty1.className = str2;
/*  252 */         providerProperty1.provider = provider;
/*  253 */         return providerProperty1;
/*      */       } 
/*      */     } 
/*      */     
/*  257 */     return providerProperty;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getProviderProperty(String paramString, Provider paramProvider) {
/*  264 */     String str = paramProvider.getProperty(paramString);
/*  265 */     if (str == null) {
/*      */ 
/*      */       
/*  268 */       Enumeration<Object> enumeration = paramProvider.keys();
/*  269 */       while (enumeration.hasMoreElements() && str == null) {
/*  270 */         String str1 = (String)enumeration.nextElement();
/*  271 */         if (paramString.equalsIgnoreCase(str1)) {
/*  272 */           str = paramProvider.getProperty(str1);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  277 */     return str;
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
/*      */   
/*      */   @Deprecated
/*      */   public static String getAlgorithmProperty(String paramString1, String paramString2) {
/*  307 */     ProviderProperty providerProperty = getProviderProperty("Alg." + paramString2 + "." + paramString1);
/*      */     
/*  309 */     if (providerProperty != null) {
/*  310 */       return providerProperty.className;
/*      */     }
/*  312 */     return null;
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
/*      */   public static synchronized int insertProviderAt(Provider paramProvider, int paramInt) {
/*  358 */     String str = paramProvider.getName();
/*  359 */     checkInsertProvider(str);
/*  360 */     ProviderList providerList1 = Providers.getFullProviderList();
/*  361 */     ProviderList providerList2 = ProviderList.insertAt(providerList1, paramProvider, paramInt - 1);
/*  362 */     if (providerList1 == providerList2) {
/*  363 */       return -1;
/*      */     }
/*  365 */     Providers.setProviderList(providerList2);
/*  366 */     return providerList2.getIndex(str) + 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int addProvider(Provider paramProvider) {
/*  403 */     return insertProviderAt(paramProvider, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized void removeProvider(String paramString) {
/*  439 */     check("removeProvider." + paramString);
/*  440 */     ProviderList providerList1 = Providers.getFullProviderList();
/*  441 */     ProviderList providerList2 = ProviderList.remove(providerList1, paramString);
/*  442 */     Providers.setProviderList(providerList2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Provider[] getProviders() {
/*  452 */     return Providers.getFullProviderList().toArray();
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
/*      */   public static Provider getProvider(String paramString) {
/*  468 */     return Providers.getProviderList().getProvider(paramString);
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
/*      */   public static Provider[] getProviders(String paramString) {
/*  532 */     String str1 = null;
/*  533 */     String str2 = null;
/*  534 */     int i = paramString.indexOf(':');
/*      */     
/*  536 */     if (i == -1) {
/*  537 */       str1 = paramString;
/*  538 */       str2 = "";
/*      */     } else {
/*  540 */       str1 = paramString.substring(0, i);
/*  541 */       str2 = paramString.substring(i + 1);
/*      */     } 
/*      */     
/*  544 */     Hashtable<Object, Object> hashtable = new Hashtable<>(1);
/*  545 */     hashtable.put(str1, str2);
/*      */     
/*  547 */     return getProviders((Map)hashtable);
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
/*      */   public static Provider[] getProviders(Map<String, String> paramMap) {
/*  605 */     Provider[] arrayOfProvider1 = getProviders();
/*  606 */     Set<String> set = paramMap.keySet();
/*  607 */     LinkedHashSet<Provider> linkedHashSet = new LinkedHashSet(5);
/*      */ 
/*      */ 
/*      */     
/*  611 */     if (set == null || arrayOfProvider1 == null) {
/*  612 */       return arrayOfProvider1;
/*      */     }
/*      */     
/*  615 */     boolean bool = true;
/*      */ 
/*      */ 
/*      */     
/*  619 */     for (String str1 : set) {
/*      */       
/*  621 */       String str2 = paramMap.get(str1);
/*      */       
/*  623 */       LinkedHashSet<Provider> linkedHashSet1 = getAllQualifyingCandidates(str1, str2, arrayOfProvider1);
/*      */       
/*  625 */       if (bool) {
/*  626 */         linkedHashSet = linkedHashSet1;
/*  627 */         bool = false;
/*      */       } 
/*      */       
/*  630 */       if (linkedHashSet1 != null && !linkedHashSet1.isEmpty()) {
/*      */ 
/*      */ 
/*      */         
/*  634 */         Iterator<Provider> iterator = linkedHashSet.iterator();
/*  635 */         while (iterator.hasNext()) {
/*  636 */           Provider provider = iterator.next();
/*  637 */           if (!linkedHashSet1.contains(provider))
/*  638 */             iterator.remove(); 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  642 */       linkedHashSet = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  647 */     if (linkedHashSet == null || linkedHashSet.isEmpty()) {
/*  648 */       return null;
/*      */     }
/*  650 */     Object[] arrayOfObject = linkedHashSet.toArray();
/*  651 */     Provider[] arrayOfProvider2 = new Provider[arrayOfObject.length];
/*      */     
/*  653 */     for (byte b = 0; b < arrayOfProvider2.length; b++) {
/*  654 */       arrayOfProvider2[b] = (Provider)arrayOfObject[b];
/*      */     }
/*      */     
/*  657 */     return arrayOfProvider2;
/*      */   }
/*      */ 
/*      */   
/*  661 */   private static final Map<String, Class<?>> spiMap = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> getSpiClass(String paramString) {
/*  670 */     Class<?> clazz = spiMap.get(paramString);
/*  671 */     if (clazz != null) {
/*  672 */       return clazz;
/*      */     }
/*      */     try {
/*  675 */       clazz = Class.forName("java.security." + paramString + "Spi");
/*  676 */       spiMap.put(paramString, clazz);
/*  677 */       return clazz;
/*  678 */     } catch (ClassNotFoundException classNotFoundException) {
/*  679 */       throw new AssertionError("Spi class not found", classNotFoundException);
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
/*      */   static Object[] getImpl(String paramString1, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/*  693 */     if (paramString3 == null) {
/*  694 */       return 
/*  695 */         GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1).toArray();
/*      */     }
/*  697 */     return 
/*  698 */       GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramString3).toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object[] getImpl(String paramString1, String paramString2, String paramString3, Object paramObject) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
/*  705 */     if (paramString3 == null) {
/*  706 */       return 
/*  707 */         GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject).toArray();
/*      */     }
/*  709 */     return 
/*  710 */       GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject, paramString3).toArray();
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
/*      */   static Object[] getImpl(String paramString1, String paramString2, Provider paramProvider) throws NoSuchAlgorithmException {
/*  723 */     return 
/*  724 */       GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramProvider).toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static Object[] getImpl(String paramString1, String paramString2, Provider paramProvider, Object paramObject) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/*  730 */     return 
/*  731 */       GetInstance.getInstance(paramString2, getSpiClass(paramString2), paramString1, paramObject, paramProvider).toArray();
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
/*      */   public static String getProperty(String paramString) {
/*  758 */     SecurityManager securityManager = System.getSecurityManager();
/*  759 */     if (securityManager != null) {
/*  760 */       securityManager.checkPermission(new SecurityPermission("getProperty." + paramString));
/*      */     }
/*      */     
/*  763 */     String str = props.getProperty(paramString);
/*  764 */     if (str != null)
/*  765 */       str = str.trim(); 
/*  766 */     return str;
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
/*      */   public static void setProperty(String paramString1, String paramString2) {
/*  792 */     check("setProperty." + paramString1);
/*  793 */     props.put(paramString1, paramString2);
/*  794 */     invalidateSMCache(paramString1);
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
/*      */   private static void invalidateSMCache(String paramString) {
/*  809 */     final boolean pa = paramString.equals("package.access");
/*  810 */     boolean bool2 = paramString.equals("package.definition");
/*      */     
/*  812 */     if (bool1 || bool2) {
/*  813 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*      */               try {
/*  817 */                 Class<?> clazz = Class.forName("java.lang.SecurityManager", false, null);
/*      */                 
/*  819 */                 Field field = null;
/*  820 */                 boolean bool = false;
/*      */                 
/*  822 */                 if (pa) {
/*  823 */                   field = clazz.getDeclaredField("packageAccessValid");
/*  824 */                   bool = field.isAccessible();
/*  825 */                   field.setAccessible(true);
/*      */                 } else {
/*  827 */                   field = clazz.getDeclaredField("packageDefinitionValid");
/*  828 */                   bool = field.isAccessible();
/*  829 */                   field.setAccessible(true);
/*      */                 } 
/*  831 */                 field.setBoolean(field, false);
/*  832 */                 field.setAccessible(bool);
/*      */               }
/*  834 */               catch (Exception exception) {}
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
/*  845 */               return null;
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private static void check(String paramString) {
/*  852 */     SecurityManager securityManager = System.getSecurityManager();
/*  853 */     if (securityManager != null) {
/*  854 */       securityManager.checkSecurityAccess(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void checkInsertProvider(String paramString) {
/*  859 */     SecurityManager securityManager = System.getSecurityManager();
/*  860 */     if (securityManager != null) {
/*      */       try {
/*  862 */         securityManager.checkSecurityAccess("insertProvider");
/*  863 */       } catch (SecurityException securityException) {
/*      */         try {
/*  865 */           securityManager.checkSecurityAccess("insertProvider." + paramString);
/*  866 */         } catch (SecurityException securityException1) {
/*      */           
/*  868 */           securityException.addSuppressed(securityException1);
/*  869 */           throw securityException;
/*      */         } 
/*      */       } 
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
/*      */   private static LinkedHashSet<Provider> getAllQualifyingCandidates(String paramString1, String paramString2, Provider[] paramArrayOfProvider) {
/*  883 */     String[] arrayOfString = getFilterComponents(paramString1, paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  889 */     String str1 = arrayOfString[0];
/*  890 */     String str2 = arrayOfString[1];
/*  891 */     String str3 = arrayOfString[2];
/*      */     
/*  893 */     return getProvidersNotUsingCache(str1, str2, str3, paramString2, paramArrayOfProvider);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LinkedHashSet<Provider> getProvidersNotUsingCache(String paramString1, String paramString2, String paramString3, String paramString4, Provider[] paramArrayOfProvider) {
/*  903 */     LinkedHashSet<Provider> linkedHashSet = new LinkedHashSet(5);
/*  904 */     for (byte b = 0; b < paramArrayOfProvider.length; b++) {
/*  905 */       if (isCriterionSatisfied(paramArrayOfProvider[b], paramString1, paramString2, paramString3, paramString4))
/*      */       {
/*      */         
/*  908 */         linkedHashSet.add(paramArrayOfProvider[b]);
/*      */       }
/*      */     } 
/*  911 */     return linkedHashSet;
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
/*      */   private static boolean isCriterionSatisfied(Provider paramProvider, String paramString1, String paramString2, String paramString3, String paramString4) {
/*  923 */     String str1 = paramString1 + '.' + paramString2;
/*      */     
/*  925 */     if (paramString3 != null) {
/*  926 */       str1 = str1 + ' ' + paramString3;
/*      */     }
/*      */ 
/*      */     
/*  930 */     String str2 = getProviderProperty(str1, paramProvider);
/*      */     
/*  932 */     if (str2 == null) {
/*      */ 
/*      */       
/*  935 */       String str = getProviderProperty("Alg.Alias." + paramString1 + "." + paramString2, paramProvider);
/*      */ 
/*      */ 
/*      */       
/*  939 */       if (str != null) {
/*  940 */         str1 = paramString1 + "." + str;
/*      */         
/*  942 */         if (paramString3 != null) {
/*  943 */           str1 = str1 + ' ' + paramString3;
/*      */         }
/*      */         
/*  946 */         str2 = getProviderProperty(str1, paramProvider);
/*      */       } 
/*      */       
/*  949 */       if (str2 == null)
/*      */       {
/*      */         
/*  952 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  960 */     if (paramString3 == null) {
/*  961 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  966 */     if (isStandardAttr(paramString3)) {
/*  967 */       return isConstraintSatisfied(paramString3, paramString4, str2);
/*      */     }
/*  969 */     return paramString4.equalsIgnoreCase(str2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isStandardAttr(String paramString) {
/*  980 */     if (paramString.equalsIgnoreCase("KeySize")) {
/*  981 */       return true;
/*      */     }
/*  983 */     if (paramString.equalsIgnoreCase("ImplementedIn")) {
/*  984 */       return true;
/*      */     }
/*  986 */     return false;
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
/*      */   private static boolean isConstraintSatisfied(String paramString1, String paramString2, String paramString3) {
/*  998 */     if (paramString1.equalsIgnoreCase("KeySize")) {
/*  999 */       int i = Integer.parseInt(paramString2);
/* 1000 */       int j = Integer.parseInt(paramString3);
/* 1001 */       if (i <= j) {
/* 1002 */         return true;
/*      */       }
/* 1004 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (paramString1.equalsIgnoreCase("ImplementedIn")) {
/* 1011 */       return paramString2.equalsIgnoreCase(paramString3);
/*      */     }
/*      */     
/* 1014 */     return false;
/*      */   }
/*      */   
/*      */   static String[] getFilterComponents(String paramString1, String paramString2) {
/* 1018 */     int i = paramString1.indexOf('.');
/*      */     
/* 1020 */     if (i < 0)
/*      */     {
/*      */       
/* 1023 */       throw new InvalidParameterException("Invalid filter");
/*      */     }
/*      */     
/* 1026 */     String str1 = paramString1.substring(0, i);
/* 1027 */     String str2 = null;
/* 1028 */     String str3 = null;
/*      */     
/* 1030 */     if (paramString2.length() == 0) {
/*      */ 
/*      */       
/* 1033 */       str2 = paramString1.substring(i + 1).trim();
/* 1034 */       if (str2.length() == 0)
/*      */       {
/* 1036 */         throw new InvalidParameterException("Invalid filter");
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1042 */       int j = paramString1.indexOf(' ');
/*      */       
/* 1044 */       if (j == -1)
/*      */       {
/* 1046 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/* 1048 */       str3 = paramString1.substring(j + 1).trim();
/* 1049 */       if (str3.length() == 0)
/*      */       {
/* 1051 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1056 */       if (j < i || i == j - 1)
/*      */       {
/* 1058 */         throw new InvalidParameterException("Invalid filter");
/*      */       }
/* 1060 */       str2 = paramString1.substring(i + 1, j);
/*      */     } 
/*      */ 
/*      */     
/* 1064 */     String[] arrayOfString = new String[3];
/* 1065 */     arrayOfString[0] = str1;
/* 1066 */     arrayOfString[1] = str2;
/* 1067 */     arrayOfString[2] = str3;
/*      */     
/* 1069 */     return arrayOfString;
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
/*      */   public static Set<String> getAlgorithms(String paramString) {
/* 1095 */     if (paramString == null || paramString.length() == 0 || paramString
/* 1096 */       .endsWith(".")) {
/* 1097 */       return Collections.emptySet();
/*      */     }
/*      */     
/* 1100 */     HashSet<String> hashSet = new HashSet();
/* 1101 */     Provider[] arrayOfProvider = getProviders();
/*      */     
/* 1103 */     for (byte b = 0; b < arrayOfProvider.length; b++) {
/*      */       
/* 1105 */       Enumeration<Object> enumeration = arrayOfProvider[b].keys();
/* 1106 */       while (enumeration.hasMoreElements()) {
/*      */         
/* 1108 */         String str = ((String)enumeration.nextElement()).toUpperCase(Locale.ENGLISH);
/* 1109 */         if (str.startsWith(paramString
/* 1110 */             .toUpperCase(Locale.ENGLISH)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1117 */           if (str.indexOf(" ") < 0) {
/* 1118 */             hashSet.add(str.substring(paramString
/* 1119 */                   .length() + 1));
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/* 1124 */     return Collections.unmodifiableSet(hashSet);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/Security.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */