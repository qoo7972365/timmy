/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.spi.ResourceBundleControlProvider;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.util.locale.BaseLocale;
/*      */ import sun.util.locale.LocaleObjectCache;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ResourceBundle
/*      */ {
/*      */   private static final int INITIAL_CACHE_SIZE = 32;
/*      */   
/*  293 */   private static final ResourceBundle NONEXISTENT_BUNDLE = new ResourceBundle() {
/*  294 */       public Enumeration<String> getKeys() { return null; }
/*  295 */       protected Object handleGetObject(String param1String) { return null; } public String toString() {
/*  296 */         return "NONEXISTENT_BUNDLE";
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  312 */   private static final ConcurrentMap<CacheKey, BundleReference> cacheList = new ConcurrentHashMap<>(32);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  318 */   private static final ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseBundleName() {
/*  335 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  343 */   protected ResourceBundle parent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  348 */   private Locale locale = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private String name;
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean expired;
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile CacheKey cacheKey;
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile Set<String> keySet;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final List<ResourceBundleControlProvider> providers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  374 */     ArrayList<ResourceBundleControlProvider> arrayList = null;
/*      */     
/*  376 */     ServiceLoader<ResourceBundleControlProvider> serviceLoader = ServiceLoader.loadInstalled(ResourceBundleControlProvider.class);
/*  377 */     for (ResourceBundleControlProvider resourceBundleControlProvider : serviceLoader) {
/*  378 */       if (arrayList == null) {
/*  379 */         arrayList = new ArrayList();
/*      */       }
/*  381 */       arrayList.add(resourceBundleControlProvider);
/*      */     } 
/*  383 */     providers = arrayList;
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
/*      */   public final String getString(String paramString) {
/*  407 */     return (String)getObject(paramString);
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
/*      */   public final String[] getStringArray(String paramString) {
/*  424 */     return (String[])getObject(paramString);
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
/*      */   public final Object getObject(String paramString) {
/*  441 */     Object object = handleGetObject(paramString);
/*  442 */     if (object == null) {
/*  443 */       if (this.parent != null) {
/*  444 */         object = this.parent.getObject(paramString);
/*      */       }
/*  446 */       if (object == null) {
/*  447 */         throw new MissingResourceException("Can't find resource for bundle " + 
/*  448 */             getClass().getName() + ", key " + paramString, 
/*      */             
/*  450 */             getClass().getName(), paramString);
/*      */       }
/*      */     } 
/*      */     
/*  454 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  465 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ClassLoader getLoader(Class<?> paramClass) {
/*  473 */     ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/*  474 */     if (classLoader == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  482 */       classLoader = RBClassLoader.INSTANCE;
/*      */     }
/*  484 */     return classLoader;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class RBClassLoader
/*      */     extends ClassLoader
/*      */   {
/*  491 */     private static final RBClassLoader INSTANCE = AccessController.<RBClassLoader>doPrivileged(new PrivilegedAction<RBClassLoader>()
/*      */         {
/*      */           public ResourceBundle.RBClassLoader run() {
/*  494 */             return new ResourceBundle.RBClassLoader();
/*      */           }
/*      */         });
/*      */     private static final ClassLoader loader;
/*      */     
/*      */     static {
/*  500 */       ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/*      */       ClassLoader classLoader2;
/*  502 */       while ((classLoader2 = classLoader1.getParent()) != null) {
/*  503 */         classLoader1 = classLoader2;
/*      */       }
/*  505 */       loader = classLoader1;
/*      */     }
/*      */     
/*      */     private RBClassLoader() {}
/*      */     
/*      */     public Class<?> loadClass(String param1String) throws ClassNotFoundException {
/*  511 */       if (loader != null) {
/*  512 */         return loader.loadClass(param1String);
/*      */       }
/*  514 */       return Class.forName(param1String);
/*      */     }
/*      */     public URL getResource(String param1String) {
/*  517 */       if (loader != null) {
/*  518 */         return loader.getResource(param1String);
/*      */       }
/*  520 */       return ClassLoader.getSystemResource(param1String);
/*      */     }
/*      */     public InputStream getResourceAsStream(String param1String) {
/*  523 */       if (loader != null) {
/*  524 */         return loader.getResourceAsStream(param1String);
/*      */       }
/*  526 */       return ClassLoader.getSystemResourceAsStream(param1String);
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
/*      */   protected void setParent(ResourceBundle paramResourceBundle) {
/*  538 */     assert paramResourceBundle != NONEXISTENT_BUNDLE;
/*  539 */     this.parent = paramResourceBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CacheKey
/*      */     implements Cloneable
/*      */   {
/*      */     private String name;
/*      */ 
/*      */ 
/*      */     
/*      */     private Locale locale;
/*      */ 
/*      */ 
/*      */     
/*      */     private ResourceBundle.LoaderReference loaderRef;
/*      */ 
/*      */ 
/*      */     
/*      */     private String format;
/*      */ 
/*      */     
/*      */     private volatile long loadTime;
/*      */ 
/*      */     
/*      */     private volatile long expirationTime;
/*      */ 
/*      */     
/*      */     private Throwable cause;
/*      */ 
/*      */     
/*      */     private int hashCodeCache;
/*      */ 
/*      */ 
/*      */     
/*      */     CacheKey(String param1String, Locale param1Locale, ClassLoader param1ClassLoader) {
/*  577 */       this.name = param1String;
/*  578 */       this.locale = param1Locale;
/*  579 */       if (param1ClassLoader == null) {
/*  580 */         this.loaderRef = null;
/*      */       } else {
/*  582 */         this.loaderRef = new ResourceBundle.LoaderReference(param1ClassLoader, ResourceBundle.referenceQueue, this);
/*      */       } 
/*  584 */       calculateHashCode();
/*      */     }
/*      */     
/*      */     String getName() {
/*  588 */       return this.name;
/*      */     }
/*      */     
/*      */     CacheKey setName(String param1String) {
/*  592 */       if (!this.name.equals(param1String)) {
/*  593 */         this.name = param1String;
/*  594 */         calculateHashCode();
/*      */       } 
/*  596 */       return this;
/*      */     }
/*      */     
/*      */     Locale getLocale() {
/*  600 */       return this.locale;
/*      */     }
/*      */     
/*      */     CacheKey setLocale(Locale param1Locale) {
/*  604 */       if (!this.locale.equals(param1Locale)) {
/*  605 */         this.locale = param1Locale;
/*  606 */         calculateHashCode();
/*      */       } 
/*  608 */       return this;
/*      */     }
/*      */     
/*      */     ClassLoader getLoader() {
/*  612 */       return (this.loaderRef != null) ? this.loaderRef.get() : null;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  616 */       if (this == param1Object) {
/*  617 */         return true;
/*      */       }
/*      */       try {
/*  620 */         CacheKey cacheKey = (CacheKey)param1Object;
/*      */         
/*  622 */         if (this.hashCodeCache != cacheKey.hashCodeCache) {
/*  623 */           return false;
/*      */         }
/*      */         
/*  626 */         if (!this.name.equals(cacheKey.name)) {
/*  627 */           return false;
/*      */         }
/*      */         
/*  630 */         if (!this.locale.equals(cacheKey.locale)) {
/*  631 */           return false;
/*      */         }
/*      */         
/*  634 */         if (this.loaderRef == null) {
/*  635 */           return (cacheKey.loaderRef == null);
/*      */         }
/*  637 */         ClassLoader classLoader = this.loaderRef.get();
/*  638 */         return (cacheKey.loaderRef != null && classLoader != null && classLoader == cacheKey.loaderRef
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  643 */           .get());
/*  644 */       } catch (NullPointerException|ClassCastException nullPointerException) {
/*      */         
/*  646 */         return false;
/*      */       } 
/*      */     }
/*      */     public int hashCode() {
/*  650 */       return this.hashCodeCache;
/*      */     }
/*      */     
/*      */     private void calculateHashCode() {
/*  654 */       this.hashCodeCache = this.name.hashCode() << 3;
/*  655 */       this.hashCodeCache ^= this.locale.hashCode();
/*  656 */       ClassLoader classLoader = getLoader();
/*  657 */       if (classLoader != null) {
/*  658 */         this.hashCodeCache ^= classLoader.hashCode();
/*      */       }
/*      */     }
/*      */     
/*      */     public Object clone() {
/*      */       try {
/*  664 */         CacheKey cacheKey = (CacheKey)super.clone();
/*  665 */         if (this.loaderRef != null) {
/*  666 */           cacheKey
/*  667 */             .loaderRef = new ResourceBundle.LoaderReference(this.loaderRef.get(), ResourceBundle.referenceQueue, cacheKey);
/*      */         }
/*      */         
/*  670 */         cacheKey.cause = null;
/*  671 */         return cacheKey;
/*  672 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */         
/*  674 */         throw new InternalError(cloneNotSupportedException);
/*      */       } 
/*      */     }
/*      */     
/*      */     String getFormat() {
/*  679 */       return this.format;
/*      */     }
/*      */     
/*      */     void setFormat(String param1String) {
/*  683 */       this.format = param1String;
/*      */     }
/*      */     
/*      */     private void setCause(Throwable param1Throwable) {
/*  687 */       if (this.cause == null) {
/*  688 */         this.cause = param1Throwable;
/*      */ 
/*      */       
/*      */       }
/*  692 */       else if (this.cause instanceof ClassNotFoundException) {
/*  693 */         this.cause = param1Throwable;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private Throwable getCause() {
/*  699 */       return this.cause;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  703 */       String str = this.locale.toString();
/*  704 */       if (str.length() == 0) {
/*  705 */         if (this.locale.getVariant().length() != 0) {
/*  706 */           str = "__" + this.locale.getVariant();
/*      */         } else {
/*  708 */           str = "\"\"";
/*      */         } 
/*      */       }
/*  711 */       return "CacheKey[" + this.name + ", lc=" + str + ", ldr=" + getLoader() + "(format=" + this.format + ")]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface CacheKeyReference
/*      */   {
/*      */     ResourceBundle.CacheKey getCacheKey();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class LoaderReference
/*      */     extends WeakReference<ClassLoader>
/*      */     implements CacheKeyReference
/*      */   {
/*      */     private ResourceBundle.CacheKey cacheKey;
/*      */ 
/*      */ 
/*      */     
/*      */     LoaderReference(ClassLoader param1ClassLoader, ReferenceQueue<Object> param1ReferenceQueue, ResourceBundle.CacheKey param1CacheKey) {
/*  734 */       super(param1ClassLoader, param1ReferenceQueue);
/*  735 */       this.cacheKey = param1CacheKey;
/*      */     }
/*      */     
/*      */     public ResourceBundle.CacheKey getCacheKey() {
/*  739 */       return this.cacheKey;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class BundleReference
/*      */     extends SoftReference<ResourceBundle>
/*      */     implements CacheKeyReference
/*      */   {
/*      */     private ResourceBundle.CacheKey cacheKey;
/*      */ 
/*      */     
/*      */     BundleReference(ResourceBundle param1ResourceBundle, ReferenceQueue<Object> param1ReferenceQueue, ResourceBundle.CacheKey param1CacheKey) {
/*  752 */       super(param1ResourceBundle, param1ReferenceQueue);
/*  753 */       this.cacheKey = param1CacheKey;
/*      */     }
/*      */     
/*      */     public ResourceBundle.CacheKey getCacheKey() {
/*  757 */       return this.cacheKey;
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
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString) {
/*  782 */     return getBundleImpl(paramString, Locale.getDefault(), 
/*  783 */         getLoader(Reflection.getCallerClass()), 
/*  784 */         getDefaultControl(paramString));
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
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Control paramControl) {
/*  824 */     return getBundleImpl(paramString, Locale.getDefault(), 
/*  825 */         getLoader(Reflection.getCallerClass()), paramControl);
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
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Locale paramLocale) {
/*  854 */     return getBundleImpl(paramString, paramLocale, 
/*  855 */         getLoader(Reflection.getCallerClass()), 
/*  856 */         getDefaultControl(paramString));
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
/*      */   @CallerSensitive
/*      */   public static final ResourceBundle getBundle(String paramString, Locale paramLocale, Control paramControl) {
/*  899 */     return getBundleImpl(paramString, paramLocale, 
/*  900 */         getLoader(Reflection.getCallerClass()), paramControl);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ResourceBundle getBundle(String paramString, Locale paramLocale, ClassLoader paramClassLoader) {
/* 1088 */     if (paramClassLoader == null) {
/* 1089 */       throw new NullPointerException();
/*      */     }
/* 1091 */     return getBundleImpl(paramString, paramLocale, paramClassLoader, getDefaultControl(paramString));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ResourceBundle getBundle(String paramString, Locale paramLocale, ClassLoader paramClassLoader, Control paramControl) {
/* 1305 */     if (paramClassLoader == null || paramControl == null) {
/* 1306 */       throw new NullPointerException();
/*      */     }
/* 1308 */     return getBundleImpl(paramString, paramLocale, paramClassLoader, paramControl);
/*      */   }
/*      */   
/*      */   private static Control getDefaultControl(String paramString) {
/* 1312 */     if (providers != null) {
/* 1313 */       for (ResourceBundleControlProvider resourceBundleControlProvider : providers) {
/* 1314 */         Control control = resourceBundleControlProvider.getControl(paramString);
/* 1315 */         if (control != null) {
/* 1316 */           return control;
/*      */         }
/*      */       } 
/*      */     }
/* 1320 */     return Control.INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceBundle getBundleImpl(String paramString, Locale paramLocale, ClassLoader paramClassLoader, Control paramControl) {
/* 1325 */     if (paramLocale == null || paramControl == null) {
/* 1326 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1333 */     CacheKey cacheKey = new CacheKey(paramString, paramLocale, paramClassLoader);
/* 1334 */     ResourceBundle resourceBundle1 = null;
/*      */ 
/*      */     
/* 1337 */     BundleReference bundleReference = cacheList.get(cacheKey);
/* 1338 */     if (bundleReference != null) {
/* 1339 */       resourceBundle1 = bundleReference.get();
/* 1340 */       bundleReference = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1347 */     if (isValidBundle(resourceBundle1) && hasValidParentChain(resourceBundle1)) {
/* 1348 */       return resourceBundle1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1354 */     boolean bool = (paramControl == Control.INSTANCE || paramControl instanceof SingleFormatControl) ? true : false;
/*      */     
/* 1356 */     List<String> list = paramControl.getFormats(paramString);
/* 1357 */     if (!bool && !checkList(list)) {
/* 1358 */       throw new IllegalArgumentException("Invalid Control: getFormats");
/*      */     }
/*      */     
/* 1361 */     ResourceBundle resourceBundle2 = null;
/* 1362 */     Locale locale = paramLocale;
/* 1363 */     for (; locale != null; 
/* 1364 */       locale = paramControl.getFallbackLocale(paramString, locale)) {
/* 1365 */       List<Locale> list1 = paramControl.getCandidateLocales(paramString, locale);
/* 1366 */       if (!bool && !checkList(list1)) {
/* 1367 */         throw new IllegalArgumentException("Invalid Control: getCandidateLocales");
/*      */       }
/*      */       
/* 1370 */       resourceBundle1 = findBundle(cacheKey, list1, list, 0, paramControl, resourceBundle2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       if (isValidBundle(resourceBundle1)) {
/* 1378 */         boolean bool1 = Locale.ROOT.equals(resourceBundle1.locale);
/* 1379 */         if (!bool1 || resourceBundle1.locale.equals(paramLocale) || (list1
/* 1380 */           .size() == 1 && resourceBundle1.locale
/* 1381 */           .equals(list1.get(0)))) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1388 */         if (bool1 && resourceBundle2 == null) {
/* 1389 */           resourceBundle2 = resourceBundle1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1394 */     if (resourceBundle1 == null) {
/* 1395 */       if (resourceBundle2 == null) {
/* 1396 */         throwMissingResourceException(paramString, paramLocale, cacheKey.getCause());
/*      */       }
/* 1398 */       resourceBundle1 = resourceBundle2;
/*      */     } 
/*      */     
/* 1401 */     keepAlive(paramClassLoader);
/* 1402 */     return resourceBundle1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void keepAlive(ClassLoader paramClassLoader) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean checkList(List<?> paramList) {
/* 1417 */     boolean bool = (paramList != null && !paramList.isEmpty()) ? true : false;
/* 1418 */     if (bool) {
/* 1419 */       int i = paramList.size();
/* 1420 */       for (byte b = 0; bool && b < i; b++) {
/* 1421 */         bool = (paramList.get(b) != null) ? true : false;
/*      */       }
/*      */     } 
/* 1424 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ResourceBundle findBundle(CacheKey paramCacheKey, List<Locale> paramList, List<String> paramList1, int paramInt, Control paramControl, ResourceBundle paramResourceBundle) {
/* 1433 */     Locale locale = paramList.get(paramInt);
/* 1434 */     ResourceBundle resourceBundle1 = null;
/* 1435 */     if (paramInt != paramList.size() - 1) {
/* 1436 */       resourceBundle1 = findBundle(paramCacheKey, paramList, paramList1, paramInt + 1, paramControl, paramResourceBundle);
/*      */     }
/* 1438 */     else if (paramResourceBundle != null && Locale.ROOT.equals(locale)) {
/* 1439 */       return paramResourceBundle;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     Reference<?> reference;
/*      */ 
/*      */     
/* 1447 */     while ((reference = referenceQueue.poll()) != null) {
/* 1448 */       cacheList.remove(((CacheKeyReference)reference).getCacheKey());
/*      */     }
/*      */ 
/*      */     
/* 1452 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */     
/* 1456 */     paramCacheKey.setLocale(locale);
/* 1457 */     ResourceBundle resourceBundle2 = findBundleInCache(paramCacheKey, paramControl);
/* 1458 */     if (isValidBundle(resourceBundle2)) {
/* 1459 */       bool = resourceBundle2.expired;
/* 1460 */       if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1466 */         if (resourceBundle2.parent == resourceBundle1) {
/* 1467 */           return resourceBundle2;
/*      */         }
/*      */ 
/*      */         
/* 1471 */         BundleReference bundleReference = cacheList.get(paramCacheKey);
/* 1472 */         if (bundleReference != null && bundleReference.get() == resourceBundle2) {
/* 1473 */           cacheList.remove(paramCacheKey, bundleReference);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1478 */     if (resourceBundle2 != NONEXISTENT_BUNDLE) {
/* 1479 */       CacheKey cacheKey = (CacheKey)paramCacheKey.clone();
/*      */       
/*      */       try {
/* 1482 */         resourceBundle2 = loadBundle(paramCacheKey, paramList1, paramControl, bool);
/* 1483 */         if (resourceBundle2 != null) {
/* 1484 */           if (resourceBundle2.parent == null) {
/* 1485 */             resourceBundle2.setParent(resourceBundle1);
/*      */           }
/* 1487 */           resourceBundle2.locale = locale;
/* 1488 */           resourceBundle2 = putBundleInCache(paramCacheKey, resourceBundle2, paramControl);
/* 1489 */           return resourceBundle2;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1494 */         putBundleInCache(paramCacheKey, NONEXISTENT_BUNDLE, paramControl);
/*      */       } finally {
/* 1496 */         if (cacheKey.getCause() instanceof InterruptedException) {
/* 1497 */           Thread.currentThread().interrupt();
/*      */         }
/*      */       } 
/*      */     } 
/* 1501 */     return resourceBundle1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ResourceBundle loadBundle(CacheKey paramCacheKey, List<String> paramList, Control paramControl, boolean paramBoolean) {
/* 1511 */     Locale locale = paramCacheKey.getLocale();
/*      */     
/* 1513 */     ResourceBundle resourceBundle = null;
/* 1514 */     int i = paramList.size();
/* 1515 */     for (byte b = 0; b < i; b++) {
/* 1516 */       String str = paramList.get(b);
/*      */       try {
/* 1518 */         resourceBundle = paramControl.newBundle(paramCacheKey.getName(), locale, str, paramCacheKey
/* 1519 */             .getLoader(), paramBoolean);
/* 1520 */       } catch (LinkageError linkageError) {
/*      */ 
/*      */ 
/*      */         
/* 1524 */         paramCacheKey.setCause(linkageError);
/* 1525 */       } catch (Exception exception) {
/* 1526 */         paramCacheKey.setCause(exception);
/*      */       } 
/* 1528 */       if (resourceBundle != null) {
/*      */ 
/*      */         
/* 1531 */         paramCacheKey.setFormat(str);
/* 1532 */         resourceBundle.name = paramCacheKey.getName();
/* 1533 */         resourceBundle.locale = locale;
/*      */ 
/*      */         
/* 1536 */         resourceBundle.expired = false;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1541 */     return resourceBundle;
/*      */   }
/*      */   
/*      */   private static boolean isValidBundle(ResourceBundle paramResourceBundle) {
/* 1545 */     return (paramResourceBundle != null && paramResourceBundle != NONEXISTENT_BUNDLE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasValidParentChain(ResourceBundle paramResourceBundle) {
/* 1553 */     long l = System.currentTimeMillis();
/* 1554 */     while (paramResourceBundle != null) {
/* 1555 */       if (paramResourceBundle.expired) {
/* 1556 */         return false;
/*      */       }
/* 1558 */       CacheKey cacheKey = paramResourceBundle.cacheKey;
/* 1559 */       if (cacheKey != null) {
/* 1560 */         long l1 = cacheKey.expirationTime;
/* 1561 */         if (l1 >= 0L && l1 <= l) {
/* 1562 */           return false;
/*      */         }
/*      */       } 
/* 1565 */       paramResourceBundle = paramResourceBundle.parent;
/*      */     } 
/* 1567 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void throwMissingResourceException(String paramString, Locale paramLocale, Throwable paramThrowable) {
/* 1578 */     if (paramThrowable instanceof MissingResourceException) {
/* 1579 */       paramThrowable = null;
/*      */     }
/* 1581 */     throw new MissingResourceException("Can't find bundle for base name " + paramString + ", locale " + paramLocale, paramString + "_" + paramLocale, "", paramThrowable);
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
/*      */   private static ResourceBundle findBundleInCache(CacheKey paramCacheKey, Control paramControl) {
/* 1600 */     BundleReference bundleReference = cacheList.get(paramCacheKey);
/* 1601 */     if (bundleReference == null) {
/* 1602 */       return null;
/*      */     }
/* 1604 */     ResourceBundle resourceBundle1 = bundleReference.get();
/* 1605 */     if (resourceBundle1 == null) {
/* 1606 */       return null;
/*      */     }
/* 1608 */     ResourceBundle resourceBundle2 = resourceBundle1.parent;
/* 1609 */     assert resourceBundle2 != NONEXISTENT_BUNDLE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1643 */     if (resourceBundle2 != null && resourceBundle2.expired) {
/* 1644 */       assert resourceBundle1 != NONEXISTENT_BUNDLE;
/* 1645 */       resourceBundle1.expired = true;
/* 1646 */       resourceBundle1.cacheKey = null;
/* 1647 */       cacheList.remove(paramCacheKey, bundleReference);
/* 1648 */       resourceBundle1 = null;
/*      */     } else {
/* 1650 */       CacheKey cacheKey = bundleReference.getCacheKey();
/* 1651 */       long l = cacheKey.expirationTime;
/* 1652 */       if (!resourceBundle1.expired && l >= 0L && l <= 
/* 1653 */         System.currentTimeMillis())
/*      */       {
/* 1655 */         if (resourceBundle1 != NONEXISTENT_BUNDLE) {
/*      */ 
/*      */           
/* 1658 */           synchronized (resourceBundle1) {
/* 1659 */             l = cacheKey.expirationTime;
/* 1660 */             if (!resourceBundle1.expired && l >= 0L && l <= 
/* 1661 */               System.currentTimeMillis()) {
/*      */               try {
/* 1663 */                 resourceBundle1.expired = paramControl.needsReload(cacheKey.getName(), cacheKey
/* 1664 */                     .getLocale(), cacheKey
/* 1665 */                     .getFormat(), cacheKey
/* 1666 */                     .getLoader(), resourceBundle1, cacheKey
/*      */                     
/* 1668 */                     .loadTime);
/* 1669 */               } catch (Exception exception) {
/* 1670 */                 paramCacheKey.setCause(exception);
/*      */               } 
/* 1672 */               if (resourceBundle1.expired) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1677 */                 resourceBundle1.cacheKey = null;
/* 1678 */                 cacheList.remove(paramCacheKey, bundleReference);
/*      */               }
/*      */               else {
/*      */                 
/* 1682 */                 setExpirationTime(cacheKey, paramControl);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 1688 */           cacheList.remove(paramCacheKey, bundleReference);
/* 1689 */           resourceBundle1 = null;
/*      */         } 
/*      */       }
/*      */     } 
/* 1693 */     return resourceBundle1;
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
/*      */   private static ResourceBundle putBundleInCache(CacheKey paramCacheKey, ResourceBundle paramResourceBundle, Control paramControl) {
/* 1708 */     setExpirationTime(paramCacheKey, paramControl);
/* 1709 */     if (paramCacheKey.expirationTime != -1L) {
/* 1710 */       CacheKey cacheKey = (CacheKey)paramCacheKey.clone();
/* 1711 */       BundleReference bundleReference1 = new BundleReference(paramResourceBundle, referenceQueue, cacheKey);
/* 1712 */       paramResourceBundle.cacheKey = cacheKey;
/*      */ 
/*      */       
/* 1715 */       BundleReference bundleReference2 = cacheList.putIfAbsent(cacheKey, bundleReference1);
/*      */ 
/*      */ 
/*      */       
/* 1719 */       if (bundleReference2 != null) {
/* 1720 */         ResourceBundle resourceBundle = bundleReference2.get();
/* 1721 */         if (resourceBundle != null && !resourceBundle.expired) {
/*      */           
/* 1723 */           paramResourceBundle.cacheKey = null;
/* 1724 */           paramResourceBundle = resourceBundle;
/*      */ 
/*      */           
/* 1727 */           bundleReference1.clear();
/*      */         }
/*      */         else {
/*      */           
/* 1731 */           cacheList.put(cacheKey, bundleReference1);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1735 */     return paramResourceBundle;
/*      */   }
/*      */   
/*      */   private static void setExpirationTime(CacheKey paramCacheKey, Control paramControl) {
/* 1739 */     long l = paramControl.getTimeToLive(paramCacheKey.getName(), paramCacheKey
/* 1740 */         .getLocale());
/* 1741 */     if (l >= 0L) {
/*      */ 
/*      */       
/* 1744 */       long l1 = System.currentTimeMillis();
/* 1745 */       paramCacheKey.loadTime = l1;
/* 1746 */       paramCacheKey.expirationTime = l1 + l;
/* 1747 */     } else if (l >= -2L) {
/* 1748 */       paramCacheKey.expirationTime = l;
/*      */     } else {
/* 1750 */       throw new IllegalArgumentException("Invalid Control: TTL=" + l);
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
/*      */   @CallerSensitive
/*      */   public static final void clearCache() {
/* 1763 */     clearCache(getLoader(Reflection.getCallerClass()));
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
/*      */   public static final void clearCache(ClassLoader paramClassLoader) {
/* 1776 */     if (paramClassLoader == null) {
/* 1777 */       throw new NullPointerException();
/*      */     }
/* 1779 */     Set<CacheKey> set = cacheList.keySet();
/* 1780 */     for (CacheKey cacheKey : set) {
/* 1781 */       if (cacheKey.getLoader() == paramClassLoader) {
/* 1782 */         set.remove(cacheKey);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(String paramString) {
/* 1820 */     if (paramString == null) {
/* 1821 */       throw new NullPointerException();
/*      */     }
/* 1823 */     for (ResourceBundle resourceBundle = this; resourceBundle != null; resourceBundle = resourceBundle.parent) {
/* 1824 */       if (resourceBundle.handleKeySet().contains(paramString)) {
/* 1825 */         return true;
/*      */       }
/*      */     } 
/* 1828 */     return false;
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
/*      */   public Set<String> keySet() {
/* 1840 */     HashSet<String> hashSet = new HashSet();
/* 1841 */     for (ResourceBundle resourceBundle = this; resourceBundle != null; resourceBundle = resourceBundle.parent) {
/* 1842 */       hashSet.addAll(resourceBundle.handleKeySet());
/*      */     }
/* 1844 */     return hashSet;
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
/*      */   protected Set<String> handleKeySet() {
/* 1865 */     if (this.keySet == null) {
/* 1866 */       synchronized (this) {
/* 1867 */         if (this.keySet == null) {
/* 1868 */           HashSet<String> hashSet = new HashSet();
/* 1869 */           Enumeration<String> enumeration = getKeys();
/* 1870 */           while (enumeration.hasMoreElements()) {
/* 1871 */             String str = enumeration.nextElement();
/* 1872 */             if (handleGetObject(str) != null) {
/* 1873 */               hashSet.add(str);
/*      */             }
/*      */           } 
/* 1876 */           this.keySet = hashSet;
/*      */         } 
/*      */       } 
/*      */     }
/* 1880 */     return this.keySet;
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
/*      */   protected abstract Object handleGetObject(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Enumeration<String> getKeys();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Control
/*      */   {
/* 2042 */     public static final List<String> FORMAT_DEFAULT = Collections.unmodifiableList(Arrays.asList(new String[] { "java.class", "java.properties" }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2053 */     public static final List<String> FORMAT_CLASS = Collections.unmodifiableList(Arrays.asList(new String[] { "java.class" }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2063 */     public static final List<String> FORMAT_PROPERTIES = Collections.unmodifiableList(Arrays.asList(new String[] { "java.properties" }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final long TTL_DONT_CACHE = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final long TTL_NO_EXPIRATION_CONTROL = -2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2081 */     private static final Control INSTANCE = new Control();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final Control getControl(List<String> param1List) {
/* 2114 */       if (param1List.equals(FORMAT_PROPERTIES)) {
/* 2115 */         return ResourceBundle.SingleFormatControl.PROPERTIES_ONLY;
/*      */       }
/* 2117 */       if (param1List.equals(FORMAT_CLASS)) {
/* 2118 */         return ResourceBundle.SingleFormatControl.CLASS_ONLY;
/*      */       }
/* 2120 */       if (param1List.equals(FORMAT_DEFAULT)) {
/* 2121 */         return INSTANCE;
/*      */       }
/* 2123 */       throw new IllegalArgumentException();
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
/*      */     public static final Control getNoFallbackControl(List<String> param1List) {
/* 2149 */       if (param1List.equals(FORMAT_DEFAULT)) {
/* 2150 */         return ResourceBundle.NoFallbackControl.NO_FALLBACK;
/*      */       }
/* 2152 */       if (param1List.equals(FORMAT_PROPERTIES)) {
/* 2153 */         return ResourceBundle.NoFallbackControl.PROPERTIES_ONLY_NO_FALLBACK;
/*      */       }
/* 2155 */       if (param1List.equals(FORMAT_CLASS)) {
/* 2156 */         return ResourceBundle.NoFallbackControl.CLASS_ONLY_NO_FALLBACK;
/*      */       }
/* 2158 */       throw new IllegalArgumentException();
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
/*      */     public List<String> getFormats(String param1String) {
/* 2196 */       if (param1String == null) {
/* 2197 */         throw new NullPointerException();
/*      */       }
/* 2199 */       return FORMAT_DEFAULT;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<Locale> getCandidateLocales(String param1String, Locale param1Locale) {
/* 2381 */       if (param1String == null) {
/* 2382 */         throw new NullPointerException();
/*      */       }
/* 2384 */       return new ArrayList<>(CANDIDATES_CACHE.get(param1Locale.getBaseLocale()));
/*      */     }
/*      */     
/* 2387 */     private static final CandidateListCache CANDIDATES_CACHE = new CandidateListCache();
/*      */     private static class CandidateListCache extends LocaleObjectCache<BaseLocale, List<Locale>> { private CandidateListCache() {}
/*      */       
/*      */       protected List<Locale> createObject(BaseLocale param2BaseLocale) {
/* 2391 */         String str1 = param2BaseLocale.getLanguage();
/* 2392 */         String str2 = param2BaseLocale.getScript();
/* 2393 */         String str3 = param2BaseLocale.getRegion();
/* 2394 */         String str4 = param2BaseLocale.getVariant();
/*      */ 
/*      */         
/* 2397 */         boolean bool1 = false;
/* 2398 */         boolean bool2 = false;
/* 2399 */         if (str1.equals("no")) {
/* 2400 */           if (str3.equals("NO") && str4.equals("NY")) {
/* 2401 */             str4 = "";
/* 2402 */             bool2 = true;
/*      */           } else {
/* 2404 */             bool1 = true;
/*      */           } 
/*      */         }
/* 2407 */         if (str1.equals("nb") || bool1) {
/* 2408 */           List<Locale> list = getDefaultList("nb", str2, str3, str4);
/*      */           
/* 2410 */           LinkedList<Locale> linkedList = new LinkedList();
/* 2411 */           for (Locale locale : list) {
/* 2412 */             linkedList.add(locale);
/* 2413 */             if (locale.getLanguage().length() == 0) {
/*      */               break;
/*      */             }
/* 2416 */             linkedList.add(Locale.getInstance("no", locale.getScript(), locale.getCountry(), locale
/* 2417 */                   .getVariant(), null));
/*      */           } 
/* 2419 */           return linkedList;
/* 2420 */         }  if (str1.equals("nn") || bool2) {
/*      */           
/* 2422 */           List<Locale> list = getDefaultList("nn", str2, str3, str4);
/* 2423 */           int i = list.size() - 1;
/* 2424 */           list.add(i++, Locale.getInstance("no", "NO", "NY"));
/* 2425 */           list.add(i++, Locale.getInstance("no", "NO", ""));
/* 2426 */           list.add(i++, Locale.getInstance("no", "", ""));
/* 2427 */           return list;
/*      */         } 
/*      */         
/* 2430 */         if (str1.equals("zh")) {
/* 2431 */           if (str2.length() == 0 && str3.length() > 0) {
/*      */ 
/*      */             
/* 2434 */             switch (str3) {
/*      */               case "TW":
/*      */               case "HK":
/*      */               case "MO":
/* 2438 */                 str2 = "Hant";
/*      */                 break;
/*      */               case "CN":
/*      */               case "SG":
/* 2442 */                 str2 = "Hans";
/*      */                 break;
/*      */             } 
/* 2445 */           } else if (str2.length() > 0 && str3.length() == 0) {
/*      */ 
/*      */             
/* 2448 */             switch (str2) {
/*      */               case "Hans":
/* 2450 */                 str3 = "CN";
/*      */                 break;
/*      */               case "Hant":
/* 2453 */                 str3 = "TW";
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/*      */         }
/* 2459 */         return getDefaultList(str1, str2, str3, str4);
/*      */       }
/*      */       
/*      */       private static List<Locale> getDefaultList(String param2String1, String param2String2, String param2String3, String param2String4) {
/* 2463 */         LinkedList<String> linkedList = null;
/*      */         
/* 2465 */         if (param2String4.length() > 0) {
/* 2466 */           linkedList = new LinkedList();
/* 2467 */           int i = param2String4.length();
/* 2468 */           while (i != -1) {
/* 2469 */             linkedList.add(param2String4.substring(0, i));
/* 2470 */             i = param2String4.lastIndexOf('_', --i);
/*      */           } 
/*      */         } 
/*      */         
/* 2474 */         LinkedList<Locale> linkedList1 = new LinkedList();
/*      */         
/* 2476 */         if (linkedList != null) {
/* 2477 */           for (String str : linkedList) {
/* 2478 */             linkedList1.add(Locale.getInstance(param2String1, param2String2, param2String3, str, null));
/*      */           }
/*      */         }
/* 2481 */         if (param2String3.length() > 0) {
/* 2482 */           linkedList1.add(Locale.getInstance(param2String1, param2String2, param2String3, "", null));
/*      */         }
/* 2484 */         if (param2String2.length() > 0) {
/* 2485 */           linkedList1.add(Locale.getInstance(param2String1, param2String2, "", "", null));
/*      */ 
/*      */ 
/*      */           
/* 2489 */           if (linkedList != null) {
/* 2490 */             for (String str : linkedList) {
/* 2491 */               linkedList1.add(Locale.getInstance(param2String1, "", param2String3, str, null));
/*      */             }
/*      */           }
/* 2494 */           if (param2String3.length() > 0) {
/* 2495 */             linkedList1.add(Locale.getInstance(param2String1, "", param2String3, "", null));
/*      */           }
/*      */         } 
/* 2498 */         if (param2String1.length() > 0) {
/* 2499 */           linkedList1.add(Locale.getInstance(param2String1, "", "", "", null));
/*      */         }
/*      */         
/* 2502 */         linkedList1.add(Locale.ROOT);
/*      */         
/* 2504 */         return linkedList1;
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Locale getFallbackLocale(String param1String, Locale param1Locale) {
/* 2545 */       if (param1String == null) {
/* 2546 */         throw new NullPointerException();
/*      */       }
/* 2548 */       Locale locale = Locale.getDefault();
/* 2549 */       return param1Locale.equals(locale) ? null : locale;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ResourceBundle newBundle(String param1String1, Locale param1Locale, String param1String2, ClassLoader param1ClassLoader, boolean param1Boolean) throws IllegalAccessException, InstantiationException, IOException {
/* 2651 */       String str = toBundleName(param1String1, param1Locale);
/* 2652 */       ResourceBundle resourceBundle = null;
/* 2653 */       if (param1String2.equals("java.class")) {
/*      */ 
/*      */         
/*      */         try {
/* 2657 */           Class<?> clazz = param1ClassLoader.loadClass(str);
/*      */ 
/*      */ 
/*      */           
/* 2661 */           if (ResourceBundle.class.isAssignableFrom(clazz)) {
/* 2662 */             resourceBundle = (ResourceBundle)clazz.newInstance();
/*      */           } else {
/* 2664 */             throw new ClassCastException(clazz.getName() + " cannot be cast to ResourceBundle");
/*      */           }
/*      */         
/* 2667 */         } catch (ClassNotFoundException classNotFoundException) {}
/*      */       }
/* 2669 */       else if (param1String2.equals("java.properties")) {
/* 2670 */         final String resourceName = toResourceName0(str, "properties");
/* 2671 */         if (str1 == null) {
/* 2672 */           return resourceBundle;
/*      */         }
/* 2674 */         final ClassLoader classLoader = param1ClassLoader;
/* 2675 */         final boolean reloadFlag = param1Boolean;
/* 2676 */         InputStream inputStream = null;
/*      */         try {
/* 2678 */           inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>()
/*      */               {
/*      */                 public InputStream run() throws IOException {
/* 2681 */                   InputStream inputStream = null;
/* 2682 */                   if (reloadFlag) {
/* 2683 */                     URL uRL = classLoader.getResource(resourceName);
/* 2684 */                     if (uRL != null) {
/* 2685 */                       URLConnection uRLConnection = uRL.openConnection();
/* 2686 */                       if (uRLConnection != null) {
/*      */ 
/*      */                         
/* 2689 */                         uRLConnection.setUseCaches(false);
/* 2690 */                         inputStream = uRLConnection.getInputStream();
/*      */                       } 
/*      */                     } 
/*      */                   } else {
/* 2694 */                     inputStream = classLoader.getResourceAsStream(resourceName);
/*      */                   } 
/* 2696 */                   return inputStream;
/*      */                 }
/*      */               });
/* 2699 */         } catch (PrivilegedActionException privilegedActionException) {
/* 2700 */           throw (IOException)privilegedActionException.getException();
/*      */         } 
/* 2702 */         if (inputStream != null) {
/*      */           try {
/* 2704 */             resourceBundle = new PropertyResourceBundle(inputStream);
/*      */           } finally {
/* 2706 */             inputStream.close();
/*      */           } 
/*      */         }
/*      */       } else {
/* 2710 */         throw new IllegalArgumentException("unknown format: " + param1String2);
/*      */       } 
/* 2712 */       return resourceBundle;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getTimeToLive(String param1String, Locale param1Locale) {
/* 2762 */       if (param1String == null || param1Locale == null) {
/* 2763 */         throw new NullPointerException();
/*      */       }
/* 2765 */       return -2L;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean needsReload(String param1String1, Locale param1Locale, String param1String2, ClassLoader param1ClassLoader, ResourceBundle param1ResourceBundle, long param1Long) {
/* 2819 */       if (param1ResourceBundle == null) {
/* 2820 */         throw new NullPointerException();
/*      */       }
/* 2822 */       if (param1String2.equals("java.class") || param1String2.equals("java.properties")) {
/* 2823 */         param1String2 = param1String2.substring(5);
/*      */       }
/* 2825 */       boolean bool = false;
/*      */       try {
/* 2827 */         String str = toResourceName0(toBundleName(param1String1, param1Locale), param1String2);
/* 2828 */         if (str == null) {
/* 2829 */           return bool;
/*      */         }
/* 2831 */         URL uRL = param1ClassLoader.getResource(str);
/* 2832 */         if (uRL != null) {
/* 2833 */           long l = 0L;
/* 2834 */           URLConnection uRLConnection = uRL.openConnection();
/* 2835 */           if (uRLConnection != null) {
/*      */             
/* 2837 */             uRLConnection.setUseCaches(false);
/* 2838 */             if (uRLConnection instanceof JarURLConnection) {
/* 2839 */               JarEntry jarEntry = ((JarURLConnection)uRLConnection).getJarEntry();
/* 2840 */               if (jarEntry != null) {
/* 2841 */                 l = jarEntry.getTime();
/* 2842 */                 if (l == -1L) {
/* 2843 */                   l = 0L;
/*      */                 }
/*      */               } 
/*      */             } else {
/* 2847 */               l = uRLConnection.getLastModified();
/*      */             } 
/*      */           } 
/* 2850 */           bool = (l >= param1Long) ? true : false;
/*      */         } 
/* 2852 */       } catch (NullPointerException nullPointerException) {
/* 2853 */         throw nullPointerException;
/* 2854 */       } catch (Exception exception) {}
/*      */ 
/*      */       
/* 2857 */       return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toBundleName(String param1String, Locale param1Locale) {
/* 2903 */       if (param1Locale == Locale.ROOT) {
/* 2904 */         return param1String;
/*      */       }
/*      */       
/* 2907 */       String str1 = param1Locale.getLanguage();
/* 2908 */       String str2 = param1Locale.getScript();
/* 2909 */       String str3 = param1Locale.getCountry();
/* 2910 */       String str4 = param1Locale.getVariant();
/*      */       
/* 2912 */       if (str1 == "" && str3 == "" && str4 == "") {
/* 2913 */         return param1String;
/*      */       }
/*      */       
/* 2916 */       StringBuilder stringBuilder = new StringBuilder(param1String);
/* 2917 */       stringBuilder.append('_');
/* 2918 */       if (str2 != "") {
/* 2919 */         if (str4 != "") {
/* 2920 */           stringBuilder.append(str1).append('_').append(str2).append('_').append(str3).append('_').append(str4);
/* 2921 */         } else if (str3 != "") {
/* 2922 */           stringBuilder.append(str1).append('_').append(str2).append('_').append(str3);
/*      */         } else {
/* 2924 */           stringBuilder.append(str1).append('_').append(str2);
/*      */         }
/*      */       
/* 2927 */       } else if (str4 != "") {
/* 2928 */         stringBuilder.append(str1).append('_').append(str3).append('_').append(str4);
/* 2929 */       } else if (str3 != "") {
/* 2930 */         stringBuilder.append(str1).append('_').append(str3);
/*      */       } else {
/* 2932 */         stringBuilder.append(str1);
/*      */       } 
/*      */       
/* 2935 */       return stringBuilder.toString();
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
/*      */     public final String toResourceName(String param1String1, String param1String2) {
/* 2960 */       StringBuilder stringBuilder = new StringBuilder(param1String1.length() + 1 + param1String2.length());
/* 2961 */       stringBuilder.append(param1String1.replace('.', '/')).append('.').append(param1String2);
/* 2962 */       return stringBuilder.toString();
/*      */     }
/*      */ 
/*      */     
/*      */     private String toResourceName0(String param1String1, String param1String2) {
/* 2967 */       if (param1String1.contains("://")) {
/* 2968 */         return null;
/*      */       }
/* 2970 */       return toResourceName(param1String1, param1String2);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SingleFormatControl
/*      */     extends Control {
/* 2976 */     private static final ResourceBundle.Control PROPERTIES_ONLY = new SingleFormatControl(FORMAT_PROPERTIES);
/*      */ 
/*      */     
/* 2979 */     private static final ResourceBundle.Control CLASS_ONLY = new SingleFormatControl(FORMAT_CLASS);
/*      */     
/*      */     private final List<String> formats;
/*      */ 
/*      */     
/*      */     protected SingleFormatControl(List<String> param1List) {
/* 2985 */       this.formats = param1List;
/*      */     }
/*      */     
/*      */     public List<String> getFormats(String param1String) {
/* 2989 */       if (param1String == null) {
/* 2990 */         throw new NullPointerException();
/*      */       }
/* 2992 */       return this.formats;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class NoFallbackControl extends SingleFormatControl {
/* 2997 */     private static final ResourceBundle.Control NO_FALLBACK = new NoFallbackControl(FORMAT_DEFAULT);
/*      */ 
/*      */     
/* 3000 */     private static final ResourceBundle.Control PROPERTIES_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_PROPERTIES);
/*      */ 
/*      */     
/* 3003 */     private static final ResourceBundle.Control CLASS_ONLY_NO_FALLBACK = new NoFallbackControl(FORMAT_CLASS);
/*      */ 
/*      */     
/*      */     protected NoFallbackControl(List<String> param1List) {
/* 3007 */       super(param1List);
/*      */     }
/*      */     
/*      */     public Locale getFallbackLocale(String param1String, Locale param1Locale) {
/* 3011 */       if (param1String == null || param1Locale == null) {
/* 3012 */         throw new NullPointerException();
/*      */       }
/* 3014 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */