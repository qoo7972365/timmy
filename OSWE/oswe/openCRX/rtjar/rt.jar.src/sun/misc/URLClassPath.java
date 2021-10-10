/*      */ package sun.misc;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.JarURLConnection;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.net.URLStreamHandler;
/*      */ import java.net.URLStreamHandlerFactory;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSigner;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.jar.Attributes;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.jar.JarFile;
/*      */ import java.util.jar.Manifest;
/*      */ import java.util.zip.ZipEntry;
/*      */ import sun.net.util.URLUtil;
/*      */ import sun.net.www.ParseUtil;
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
/*      */ public class URLClassPath
/*      */ {
/*      */   static final String USER_AGENT_JAVA_VERSION = "UA-Java-Version";
/*   77 */   static final String JAVA_VERSION = AccessController.<String>doPrivileged(new GetPropertyAction("java.version"));
/*      */   
/*   79 */   private static final boolean DEBUG = (AccessController.doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.debug")) != null);
/*      */   private static final boolean DISABLE_JAR_CHECKING;
/*   81 */   private static final boolean DEBUG_LOOKUP_CACHE = (AccessController.doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.debugLookupCache")) != null); private static final boolean DISABLE_ACC_CHECKING;
/*      */   static {
/*   83 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.misc.URLClassPath.disableJarChecking"));
/*      */     
/*   85 */     DISABLE_JAR_CHECKING = (str != null) ? ((str.equals("true") || str.equals(""))) : false;
/*      */     
/*   87 */     str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.net.URLClassPath.disableRestrictedPermissions"));
/*      */     
/*   89 */     DISABLE_ACC_CHECKING = (str != null) ? ((str.equals("true") || str.equals(""))) : false;
/*      */ 
/*      */     
/*   92 */     str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.net.URLClassPath.disableClassPathURLCheck", "true"));
/*      */ 
/*      */     
/*   95 */     DISABLE_CP_URL_CHECK = (str != null) ? ((str.equals("true") || str.isEmpty())) : false;
/*   96 */     DEBUG_CP_URL_CHECK = "debug".equals(str);
/*      */   }
/*      */   private static final boolean DISABLE_CP_URL_CHECK;
/*      */   private static final boolean DEBUG_CP_URL_CHECK;
/*  100 */   private ArrayList<URL> path = new ArrayList<>();
/*      */ 
/*      */   
/*  103 */   Stack<URL> urls = new Stack<>();
/*      */ 
/*      */   
/*  106 */   ArrayList<Loader> loaders = new ArrayList<>();
/*      */ 
/*      */   
/*  109 */   HashMap<String, Loader> lmap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private URLStreamHandler jarHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean closed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AccessControlContext acc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URLClassPath(URL[] paramArrayOfURL, URLStreamHandlerFactory paramURLStreamHandlerFactory, AccessControlContext paramAccessControlContext) {
/*  137 */     for (byte b = 0; b < paramArrayOfURL.length; b++) {
/*  138 */       this.path.add(paramArrayOfURL[b]);
/*      */     }
/*  140 */     push(paramArrayOfURL);
/*  141 */     if (paramURLStreamHandlerFactory != null) {
/*  142 */       this.jarHandler = paramURLStreamHandlerFactory.createURLStreamHandler("jar");
/*      */     }
/*  144 */     if (DISABLE_ACC_CHECKING) {
/*  145 */       this.acc = null;
/*      */     } else {
/*  147 */       this.acc = paramAccessControlContext;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URLClassPath(URL[] paramArrayOfURL) {
/*  155 */     this(paramArrayOfURL, null, null);
/*      */   }
/*      */   
/*      */   public URLClassPath(URL[] paramArrayOfURL, AccessControlContext paramAccessControlContext) {
/*  159 */     this(paramArrayOfURL, null, paramAccessControlContext);
/*      */   }
/*      */   
/*      */   public synchronized List<IOException> closeLoaders() {
/*  163 */     if (this.closed) {
/*  164 */       return Collections.emptyList();
/*      */     }
/*  166 */     LinkedList<IOException> linkedList = new LinkedList();
/*  167 */     for (Loader loader : this.loaders) {
/*      */       try {
/*  169 */         loader.close();
/*  170 */       } catch (IOException iOException) {
/*  171 */         linkedList.add(iOException);
/*      */       } 
/*      */     } 
/*  174 */     this.closed = true;
/*  175 */     return linkedList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addURL(URL paramURL) {
/*  186 */     if (this.closed)
/*      */       return; 
/*  188 */     synchronized (this.urls) {
/*  189 */       if (paramURL == null || this.path.contains(paramURL)) {
/*      */         return;
/*      */       }
/*  192 */       this.urls.add(0, paramURL);
/*  193 */       this.path.add(paramURL);
/*      */       
/*  195 */       if (this.lookupCacheURLs != null)
/*      */       {
/*      */         
/*  198 */         disableAllLookupCaches();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL[] getURLs() {
/*  207 */     synchronized (this.urls) {
/*  208 */       return this.path.<URL>toArray(new URL[this.path.size()]);
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
/*      */   public URL findResource(String paramString, boolean paramBoolean) {
/*  223 */     int[] arrayOfInt = getLookupCache(paramString); Loader loader;
/*  224 */     for (byte b = 0; (loader = getNextLoader(arrayOfInt, b)) != null; b++) {
/*  225 */       URL uRL = loader.findResource(paramString, paramBoolean);
/*  226 */       if (uRL != null) {
/*  227 */         return uRL;
/*      */       }
/*      */     } 
/*  230 */     return null;
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
/*      */   public Resource getResource(String paramString, boolean paramBoolean) {
/*  242 */     if (DEBUG) {
/*  243 */       System.err.println("URLClassPath.getResource(\"" + paramString + "\")");
/*      */     }
/*      */ 
/*      */     
/*  247 */     int[] arrayOfInt = getLookupCache(paramString); Loader loader;
/*  248 */     for (byte b = 0; (loader = getNextLoader(arrayOfInt, b)) != null; b++) {
/*  249 */       Resource resource = loader.getResource(paramString, paramBoolean);
/*  250 */       if (resource != null) {
/*  251 */         return resource;
/*      */       }
/*      */     } 
/*  254 */     return null;
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
/*      */   public Enumeration<URL> findResources(final String name, final boolean check) {
/*  266 */     return new Enumeration<URL>() {
/*  267 */         private int index = 0;
/*  268 */         private int[] cache = URLClassPath.this.getLookupCache(name);
/*  269 */         private URL url = null;
/*      */         
/*      */         private boolean next() {
/*  272 */           if (this.url != null) {
/*  273 */             return true;
/*      */           }
/*      */           URLClassPath.Loader loader;
/*  276 */           while ((loader = URLClassPath.this.getNextLoader(this.cache, this.index++)) != null) {
/*  277 */             this.url = loader.findResource(name, check);
/*  278 */             if (this.url != null) {
/*  279 */               return true;
/*      */             }
/*      */           } 
/*  282 */           return false;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasMoreElements() {
/*  287 */           return next();
/*      */         }
/*      */         
/*      */         public URL nextElement() {
/*  291 */           if (!next()) {
/*  292 */             throw new NoSuchElementException();
/*      */           }
/*  294 */           URL uRL = this.url;
/*  295 */           this.url = null;
/*  296 */           return uRL;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Resource getResource(String paramString) {
/*  302 */     return getResource(paramString, true);
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
/*      */   public Enumeration<Resource> getResources(final String name, final boolean check) {
/*  314 */     return new Enumeration<Resource>() {
/*  315 */         private int index = 0;
/*  316 */         private int[] cache = URLClassPath.this.getLookupCache(name);
/*  317 */         private Resource res = null;
/*      */         
/*      */         private boolean next() {
/*  320 */           if (this.res != null) {
/*  321 */             return true;
/*      */           }
/*      */           URLClassPath.Loader loader;
/*  324 */           while ((loader = URLClassPath.this.getNextLoader(this.cache, this.index++)) != null) {
/*  325 */             this.res = loader.getResource(name, check);
/*  326 */             if (this.res != null) {
/*  327 */               return true;
/*      */             }
/*      */           } 
/*  330 */           return false;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasMoreElements() {
/*  335 */           return next();
/*      */         }
/*      */         
/*      */         public Resource nextElement() {
/*  339 */           if (!next()) {
/*  340 */             throw new NoSuchElementException();
/*      */           }
/*  342 */           Resource resource = this.res;
/*  343 */           this.res = null;
/*  344 */           return resource;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Enumeration<Resource> getResources(String paramString) {
/*  350 */     return getResources(paramString, true);
/*      */   }
/*      */   
/*  353 */   private static volatile boolean lookupCacheEnabled = "true"
/*  354 */     .equals(VM.getSavedProperty("sun.cds.enableSharedLookupCache"));
/*      */   private URL[] lookupCacheURLs;
/*      */   private ClassLoader lookupCacheLoader;
/*      */   
/*      */   synchronized void initLookupCache(ClassLoader paramClassLoader) {
/*  359 */     if ((this.lookupCacheURLs = getLookupCacheURLs(paramClassLoader)) != null) {
/*  360 */       this.lookupCacheLoader = paramClassLoader;
/*      */     } else {
/*      */       
/*  363 */       disableAllLookupCaches();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void disableAllLookupCaches() {
/*  368 */     lookupCacheEnabled = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized boolean knownToNotExist(String paramString) {
/*  378 */     if (this.lookupCacheURLs != null && lookupCacheEnabled) {
/*  379 */       return knownToNotExist0(this.lookupCacheLoader, paramString);
/*      */     }
/*      */ 
/*      */     
/*  383 */     return false;
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
/*      */   private synchronized int[] getLookupCache(String paramString) {
/*  406 */     if (this.lookupCacheURLs == null || !lookupCacheEnabled) {
/*  407 */       return null;
/*      */     }
/*      */     
/*  410 */     int[] arrayOfInt = getLookupCacheForClassLoader(this.lookupCacheLoader, paramString);
/*  411 */     if (arrayOfInt != null && arrayOfInt.length > 0) {
/*  412 */       int i = arrayOfInt[arrayOfInt.length - 1];
/*  413 */       if (!ensureLoaderOpened(i)) {
/*  414 */         if (DEBUG_LOOKUP_CACHE) {
/*  415 */           System.out.println("Expanded loaders FAILED " + this.loaders
/*  416 */               .size() + " for maxindex=" + i);
/*      */         }
/*  418 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*  422 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   private boolean ensureLoaderOpened(int paramInt) {
/*  426 */     if (this.loaders.size() <= paramInt) {
/*      */       
/*  428 */       if (getLoader(paramInt) == null) {
/*  429 */         return false;
/*      */       }
/*  431 */       if (!lookupCacheEnabled)
/*      */       {
/*  433 */         return false;
/*      */       }
/*  435 */       if (DEBUG_LOOKUP_CACHE) {
/*  436 */         System.out.println("Expanded loaders " + this.loaders.size() + " to index=" + paramInt);
/*      */       }
/*      */     } 
/*      */     
/*  440 */     return true;
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
/*      */   private synchronized void validateLookupCache(int paramInt, String paramString) {
/*  452 */     if (this.lookupCacheURLs != null && lookupCacheEnabled) {
/*  453 */       if (paramInt < this.lookupCacheURLs.length && paramString
/*  454 */         .equals(
/*  455 */           URLUtil.urlNoFragString(this.lookupCacheURLs[paramInt]))) {
/*      */         return;
/*      */       }
/*  458 */       if (DEBUG || DEBUG_LOOKUP_CACHE) {
/*  459 */         System.out.println("WARNING: resource lookup cache invalidated for lookupCacheLoader at " + paramInt);
/*      */       }
/*      */       
/*  462 */       disableAllLookupCaches();
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
/*      */   private synchronized Loader getNextLoader(int[] paramArrayOfint, int paramInt) {
/*  479 */     if (this.closed) {
/*  480 */       return null;
/*      */     }
/*  482 */     if (paramArrayOfint != null) {
/*  483 */       if (paramInt < paramArrayOfint.length) {
/*  484 */         Loader loader = this.loaders.get(paramArrayOfint[paramInt]);
/*  485 */         if (DEBUG_LOOKUP_CACHE) {
/*  486 */           System.out.println("HASCACHE: Loading from : " + paramArrayOfint[paramInt] + " = " + loader
/*  487 */               .getBaseURL());
/*      */         }
/*  489 */         return loader;
/*      */       } 
/*  491 */       return null;
/*      */     } 
/*      */     
/*  494 */     return getLoader(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized Loader getLoader(int paramInt) {
/*  504 */     if (this.closed) {
/*  505 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  509 */     while (this.loaders.size() < paramInt + 1) {
/*      */       URL uRL;
/*      */       Loader loader;
/*  512 */       synchronized (this.urls) {
/*  513 */         if (this.urls.empty()) {
/*  514 */           return null;
/*      */         }
/*  516 */         uRL = this.urls.pop();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  522 */       String str = URLUtil.urlNoFragString(uRL);
/*  523 */       if (this.lmap.containsKey(str)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  529 */         loader = getLoader(uRL);
/*      */ 
/*      */         
/*  532 */         URL[] arrayOfURL = loader.getClassPath();
/*  533 */         if (arrayOfURL != null) {
/*  534 */           push(arrayOfURL);
/*      */         }
/*  536 */       } catch (IOException iOException) {
/*      */         
/*      */         continue;
/*  539 */       } catch (SecurityException securityException) {
/*      */ 
/*      */ 
/*      */         
/*  543 */         if (DEBUG) {
/*  544 */           System.err.println("Failed to access " + uRL + ", " + securityException);
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  549 */       validateLookupCache(this.loaders.size(), str);
/*  550 */       this.loaders.add(loader);
/*  551 */       this.lmap.put(str, loader);
/*      */     } 
/*  553 */     if (DEBUG_LOOKUP_CACHE) {
/*  554 */       System.out.println("NOCACHE: Loading from : " + paramInt);
/*      */     }
/*  556 */     return this.loaders.get(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Loader getLoader(final URL url) throws IOException {
/*      */     try {
/*  564 */       return AccessController.<Loader>doPrivileged(new PrivilegedExceptionAction<Loader>()
/*      */           {
/*      */             public URLClassPath.Loader run() throws IOException {
/*  567 */               String str = url.getFile();
/*  568 */               if (str != null && str.endsWith("/")) {
/*  569 */                 if ("file".equals(url.getProtocol())) {
/*  570 */                   return new URLClassPath.FileLoader(url);
/*      */                 }
/*  572 */                 return new URLClassPath.Loader(url);
/*      */               } 
/*      */               
/*  575 */               return new URLClassPath.JarLoader(url, URLClassPath.this.jarHandler, URLClassPath.this.lmap, URLClassPath.this.acc);
/*      */             }
/*      */           }this.acc);
/*      */     }
/*  579 */     catch (PrivilegedActionException privilegedActionException) {
/*  580 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void push(URL[] paramArrayOfURL) {
/*  588 */     synchronized (this.urls) {
/*  589 */       for (int i = paramArrayOfURL.length - 1; i >= 0; i--) {
/*  590 */         this.urls.push(paramArrayOfURL[i]);
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
/*      */   public static URL[] pathToURLs(String paramString) {
/*  602 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, File.pathSeparator);
/*  603 */     URL[] arrayOfURL = new URL[stringTokenizer.countTokens()];
/*  604 */     byte b = 0;
/*  605 */     while (stringTokenizer.hasMoreTokens()) {
/*  606 */       File file = new File(stringTokenizer.nextToken());
/*      */       try {
/*  608 */         file = new File(file.getCanonicalPath());
/*  609 */       } catch (IOException iOException) {}
/*      */ 
/*      */       
/*      */       try {
/*  613 */         arrayOfURL[b++] = ParseUtil.fileToEncodedURL(file);
/*  614 */       } catch (IOException iOException) {}
/*      */     } 
/*      */     
/*  617 */     if (arrayOfURL.length != b) {
/*  618 */       URL[] arrayOfURL1 = new URL[b];
/*  619 */       System.arraycopy(arrayOfURL, 0, arrayOfURL1, 0, b);
/*  620 */       arrayOfURL = arrayOfURL1;
/*      */     } 
/*  622 */     return arrayOfURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL checkURL(URL paramURL) {
/*      */     try {
/*  632 */       check(paramURL);
/*  633 */     } catch (Exception exception) {
/*  634 */       return null;
/*      */     } 
/*      */     
/*  637 */     return paramURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void check(URL paramURL) throws IOException {
/*  646 */     SecurityManager securityManager = System.getSecurityManager();
/*  647 */     if (securityManager != null) {
/*  648 */       URLConnection uRLConnection = paramURL.openConnection();
/*  649 */       Permission permission = uRLConnection.getPermission();
/*  650 */       if (permission != null)
/*      */         try {
/*  652 */           securityManager.checkPermission(permission);
/*  653 */         } catch (SecurityException securityException) {
/*      */ 
/*      */           
/*  656 */           if (permission instanceof java.io.FilePermission && permission
/*  657 */             .getActions().indexOf("read") != -1) {
/*  658 */             securityManager.checkRead(permission.getName());
/*  659 */           } else if (permission instanceof java.net.SocketPermission && permission
/*      */             
/*  661 */             .getActions().indexOf("connect") != -1) {
/*  662 */             URL uRL = paramURL;
/*  663 */             if (uRLConnection instanceof JarURLConnection) {
/*  664 */               uRL = ((JarURLConnection)uRLConnection).getJarFileURL();
/*      */             }
/*  666 */             securityManager.checkConnect(uRL.getHost(), uRL
/*  667 */                 .getPort());
/*      */           } else {
/*  669 */             throw securityException;
/*      */           } 
/*      */         }  
/*      */     } 
/*      */   }
/*      */   
/*      */   private static native URL[] getLookupCacheURLs(ClassLoader paramClassLoader);
/*      */   
/*      */   private static native int[] getLookupCacheForClassLoader(ClassLoader paramClassLoader, String paramString);
/*      */   
/*      */   private static native boolean knownToNotExist0(ClassLoader paramClassLoader, String paramString);
/*      */   
/*      */   private static class Loader
/*      */     implements Closeable
/*      */   {
/*      */     private final URL base;
/*      */     private JarFile jarfile;
/*      */     
/*      */     Loader(URL param1URL) {
/*  688 */       this.base = param1URL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     URL getBaseURL() {
/*  695 */       return this.base;
/*      */     }
/*      */     
/*      */     URL findResource(String param1String, boolean param1Boolean) {
/*      */       URL uRL;
/*      */       try {
/*  701 */         uRL = new URL(this.base, ParseUtil.encodePath(param1String, false));
/*  702 */       } catch (MalformedURLException malformedURLException) {
/*  703 */         throw new IllegalArgumentException("name");
/*      */       } 
/*      */       
/*      */       try {
/*  707 */         if (param1Boolean) {
/*  708 */           URLClassPath.check(uRL);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  715 */         URLConnection uRLConnection = uRL.openConnection();
/*  716 */         if (uRLConnection instanceof HttpURLConnection) {
/*  717 */           HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/*  718 */           httpURLConnection.setRequestMethod("HEAD");
/*  719 */           if (httpURLConnection.getResponseCode() >= 400) {
/*  720 */             return null;
/*      */           }
/*      */         } else {
/*      */           
/*  724 */           uRLConnection.setUseCaches(false);
/*  725 */           InputStream inputStream = uRLConnection.getInputStream();
/*  726 */           inputStream.close();
/*      */         } 
/*  728 */         return uRL;
/*  729 */       } catch (Exception exception) {
/*  730 */         return null;
/*      */       } 
/*      */     }
/*      */     Resource getResource(final String name, boolean param1Boolean) {
/*      */       final URL url;
/*      */       final URLConnection uc;
/*      */       try {
/*  737 */         uRL = new URL(this.base, ParseUtil.encodePath(name, false));
/*  738 */       } catch (MalformedURLException malformedURLException) {
/*  739 */         throw new IllegalArgumentException("name");
/*      */       } 
/*      */       
/*      */       try {
/*  743 */         if (param1Boolean) {
/*  744 */           URLClassPath.check(uRL);
/*      */         }
/*  746 */         uRLConnection = uRL.openConnection();
/*  747 */         InputStream inputStream = uRLConnection.getInputStream();
/*  748 */         if (uRLConnection instanceof JarURLConnection) {
/*      */ 
/*      */ 
/*      */           
/*  752 */           JarURLConnection jarURLConnection = (JarURLConnection)uRLConnection;
/*  753 */           this.jarfile = URLClassPath.JarLoader.checkJar(jarURLConnection.getJarFile());
/*      */         } 
/*  755 */       } catch (Exception exception) {
/*  756 */         return null;
/*      */       } 
/*  758 */       return new Resource() {
/*  759 */           public String getName() { return name; }
/*  760 */           public URL getURL() { return url; } public URL getCodeSourceURL() {
/*  761 */             return URLClassPath.Loader.this.base;
/*      */           } public InputStream getInputStream() throws IOException {
/*  763 */             return uc.getInputStream();
/*      */           }
/*      */           public int getContentLength() throws IOException {
/*  766 */             return uc.getContentLength();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Resource getResource(String param1String) {
/*  777 */       return getResource(param1String, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/*  785 */       if (this.jarfile != null) {
/*  786 */         this.jarfile.close();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     URL[] getClassPath() throws IOException {
/*  794 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class JarLoader
/*      */     extends Loader
/*      */   {
/*      */     private JarFile jar;
/*      */     
/*      */     private final URL csu;
/*      */     private JarIndex index;
/*      */     private MetaIndex metaIndex;
/*      */     private URLStreamHandler handler;
/*      */     private final HashMap<String, URLClassPath.Loader> lmap;
/*      */     private final AccessControlContext acc;
/*      */     private boolean closed = false;
/*  811 */     private static final JavaUtilZipFileAccess zipAccess = SharedSecrets.getJavaUtilZipFileAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JarLoader(URL param1URL, URLStreamHandler param1URLStreamHandler, HashMap<String, URLClassPath.Loader> param1HashMap, AccessControlContext param1AccessControlContext) throws IOException {
/*  822 */       super(new URL("jar", "", -1, param1URL + "!/", param1URLStreamHandler));
/*  823 */       this.csu = param1URL;
/*  824 */       this.handler = param1URLStreamHandler;
/*  825 */       this.lmap = param1HashMap;
/*  826 */       this.acc = param1AccessControlContext;
/*      */       
/*  828 */       if (!isOptimizable(param1URL)) {
/*  829 */         ensureOpen();
/*      */       } else {
/*  831 */         String str = param1URL.getFile();
/*  832 */         if (str != null) {
/*  833 */           str = ParseUtil.decode(str);
/*  834 */           File file = new File(str);
/*  835 */           this.metaIndex = MetaIndex.forJar(file);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  842 */           if (this.metaIndex != null && !file.exists()) {
/*  843 */             this.metaIndex = null;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  850 */         if (this.metaIndex == null) {
/*  851 */           ensureOpen();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() throws IOException {
/*  859 */       if (!this.closed) {
/*  860 */         this.closed = true;
/*      */         
/*  862 */         ensureOpen();
/*  863 */         this.jar.close();
/*      */       } 
/*      */     }
/*      */     
/*      */     JarFile getJarFile() {
/*  868 */       return this.jar;
/*      */     }
/*      */     
/*      */     private boolean isOptimizable(URL param1URL) {
/*  872 */       return "file".equals(param1URL.getProtocol());
/*      */     }
/*      */     
/*      */     private void ensureOpen() throws IOException {
/*  876 */       if (this.jar == null) {
/*      */         try {
/*  878 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */               {
/*      */                 public Void run() throws IOException {
/*  881 */                   if (URLClassPath.DEBUG) {
/*  882 */                     System.err.println("Opening " + URLClassPath.JarLoader.this.csu);
/*  883 */                     Thread.dumpStack();
/*      */                   } 
/*      */                   
/*  886 */                   URLClassPath.JarLoader.this.jar = URLClassPath.JarLoader.this.getJarFile(URLClassPath.JarLoader.this.csu);
/*  887 */                   URLClassPath.JarLoader.this.index = JarIndex.getJarIndex(URLClassPath.JarLoader.this.jar, URLClassPath.JarLoader.this.metaIndex);
/*  888 */                   if (URLClassPath.JarLoader.this.index != null) {
/*  889 */                     String[] arrayOfString = URLClassPath.JarLoader.this.index.getJarFiles();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  895 */                     for (byte b = 0; b < arrayOfString.length; b++) {
/*      */                       try {
/*  897 */                         URL uRL = new URL(URLClassPath.JarLoader.this.csu, arrayOfString[b]);
/*      */                         
/*  899 */                         String str = URLUtil.urlNoFragString(uRL);
/*  900 */                         if (!URLClassPath.JarLoader.this.lmap.containsKey(str)) {
/*  901 */                           URLClassPath.JarLoader.this.lmap.put(str, null);
/*      */                         }
/*  903 */                       } catch (MalformedURLException malformedURLException) {}
/*      */                     } 
/*      */                   } 
/*      */ 
/*      */                   
/*  908 */                   return null;
/*      */                 }
/*      */               }this.acc);
/*  911 */         } catch (PrivilegedActionException privilegedActionException) {
/*  912 */           throw (IOException)privilegedActionException.getException();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     static JarFile checkJar(JarFile param1JarFile) throws IOException {
/*  919 */       if (System.getSecurityManager() != null && !URLClassPath.DISABLE_JAR_CHECKING && 
/*  920 */         !zipAccess.startsWithLocHeader(param1JarFile)) {
/*  921 */         IOException iOException = new IOException("Invalid Jar file");
/*      */         try {
/*  923 */           param1JarFile.close();
/*  924 */         } catch (IOException iOException1) {
/*  925 */           iOException.addSuppressed(iOException1);
/*      */         } 
/*  927 */         throw iOException;
/*      */       } 
/*      */       
/*  930 */       return param1JarFile;
/*      */     }
/*      */ 
/*      */     
/*      */     private JarFile getJarFile(URL param1URL) throws IOException {
/*  935 */       if (isOptimizable(param1URL)) {
/*  936 */         FileURLMapper fileURLMapper = new FileURLMapper(param1URL);
/*  937 */         if (!fileURLMapper.exists()) {
/*  938 */           throw new FileNotFoundException(fileURLMapper.getPath());
/*      */         }
/*  940 */         return checkJar(new JarFile(fileURLMapper.getPath()));
/*      */       } 
/*  942 */       URLConnection uRLConnection = getBaseURL().openConnection();
/*  943 */       uRLConnection.setRequestProperty("UA-Java-Version", URLClassPath.JAVA_VERSION);
/*  944 */       JarFile jarFile = ((JarURLConnection)uRLConnection).getJarFile();
/*  945 */       return checkJar(jarFile);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JarIndex getIndex() {
/*      */       try {
/*  953 */         ensureOpen();
/*  954 */       } catch (IOException iOException) {
/*  955 */         throw new InternalError(iOException);
/*      */       } 
/*  957 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Resource checkResource(final String name, boolean param1Boolean, final JarEntry entry) {
/*      */       final URL url;
/*      */       try {
/*  969 */         uRL = new URL(getBaseURL(), ParseUtil.encodePath(name, false));
/*  970 */         if (param1Boolean) {
/*  971 */           URLClassPath.check(uRL);
/*      */         }
/*  973 */       } catch (MalformedURLException malformedURLException) {
/*  974 */         return null;
/*      */       }
/*  976 */       catch (IOException iOException) {
/*  977 */         return null;
/*  978 */       } catch (AccessControlException accessControlException) {
/*  979 */         return null;
/*      */       } 
/*      */       
/*  982 */       return new Resource() {
/*  983 */           public String getName() { return name; }
/*  984 */           public URL getURL() { return url; } public URL getCodeSourceURL() {
/*  985 */             return URLClassPath.JarLoader.this.csu;
/*      */           } public InputStream getInputStream() throws IOException {
/*  987 */             return URLClassPath.JarLoader.this.jar.getInputStream(entry);
/*      */           } public int getContentLength() {
/*  989 */             return (int)entry.getSize();
/*      */           } public Manifest getManifest() throws IOException {
/*  991 */             SharedSecrets.javaUtilJarAccess().ensureInitialization(URLClassPath.JarLoader.this.jar);
/*  992 */             return URLClassPath.JarLoader.this.jar.getManifest();
/*      */           }
/*      */           public Certificate[] getCertificates() {
/*  995 */             return entry.getCertificates();
/*      */           } public CodeSigner[] getCodeSigners() {
/*  997 */             return entry.getCodeSigners();
/*      */           }
/*      */         };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean validIndex(String param1String) {
/* 1007 */       String str = param1String;
/*      */       int i;
/* 1009 */       if ((i = param1String.lastIndexOf("/")) != -1) {
/* 1010 */         str = param1String.substring(0, i);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1015 */       Enumeration<JarEntry> enumeration = this.jar.entries();
/* 1016 */       while (enumeration.hasMoreElements()) {
/* 1017 */         ZipEntry zipEntry = enumeration.nextElement();
/* 1018 */         String str1 = zipEntry.getName();
/* 1019 */         if ((i = str1.lastIndexOf("/")) != -1)
/* 1020 */           str1 = str1.substring(0, i); 
/* 1021 */         if (str1.equals(str)) {
/* 1022 */           return true;
/*      */         }
/*      */       } 
/* 1025 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     URL findResource(String param1String, boolean param1Boolean) {
/* 1032 */       Resource resource = getResource(param1String, param1Boolean);
/* 1033 */       if (resource != null) {
/* 1034 */         return resource.getURL();
/*      */       }
/* 1036 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Resource getResource(String param1String, boolean param1Boolean) {
/* 1043 */       if (this.metaIndex != null && 
/* 1044 */         !this.metaIndex.mayContain(param1String)) {
/* 1045 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1050 */         ensureOpen();
/* 1051 */       } catch (IOException iOException) {
/* 1052 */         throw new InternalError(iOException);
/*      */       } 
/* 1054 */       JarEntry jarEntry = this.jar.getJarEntry(param1String);
/* 1055 */       if (jarEntry != null) {
/* 1056 */         return checkResource(param1String, param1Boolean, jarEntry);
/*      */       }
/* 1058 */       if (this.index == null) {
/* 1059 */         return null;
/*      */       }
/* 1061 */       HashSet<String> hashSet = new HashSet();
/* 1062 */       return getResource(param1String, param1Boolean, hashSet);
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
/*      */     Resource getResource(String param1String, boolean param1Boolean, Set<String> param1Set) {
/* 1077 */       byte b = 0;
/* 1078 */       LinkedList<String> linkedList = null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1083 */       if ((linkedList = this.index.get(param1String)) == null) {
/* 1084 */         return null;
/*      */       }
/*      */       while (true) {
/* 1087 */         int i = linkedList.size();
/* 1088 */         String[] arrayOfString = linkedList.<String>toArray(new String[i]);
/*      */         
/* 1090 */         while (b < i) {
/* 1091 */           JarLoader jarLoader; final URL url; String str = arrayOfString[b++];
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1096 */             uRL = new URL(this.csu, str);
/* 1097 */             String str1 = URLUtil.urlNoFragString(uRL);
/* 1098 */             if ((jarLoader = (JarLoader)this.lmap.get(str1)) == null) {
/*      */ 
/*      */ 
/*      */               
/* 1102 */               jarLoader = AccessController.<JarLoader>doPrivileged(new PrivilegedExceptionAction<JarLoader>()
/*      */                   {
/*      */                     public URLClassPath.JarLoader run() throws IOException {
/* 1105 */                       return new URLClassPath.JarLoader(url, URLClassPath.JarLoader.this.handler, URLClassPath.JarLoader.this
/* 1106 */                           .lmap, URLClassPath.JarLoader.this.acc);
/*      */                     }
/*      */                   }this.acc);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1114 */               JarIndex jarIndex = jarLoader.getIndex();
/* 1115 */               if (jarIndex != null) {
/* 1116 */                 int j = str.lastIndexOf("/");
/* 1117 */                 jarIndex.merge(this.index, (j == -1) ? null : str
/* 1118 */                     .substring(0, j + 1));
/*      */               } 
/*      */ 
/*      */               
/* 1122 */               this.lmap.put(str1, jarLoader);
/*      */             } 
/* 1124 */           } catch (PrivilegedActionException privilegedActionException) {
/*      */             continue;
/* 1126 */           } catch (MalformedURLException malformedURLException) {
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1134 */           boolean bool = !param1Set.add(URLUtil.urlNoFragString(uRL)) ? true : false;
/* 1135 */           if (!bool) {
/*      */             try {
/* 1137 */               jarLoader.ensureOpen();
/* 1138 */             } catch (IOException iOException) {
/* 1139 */               throw new InternalError(iOException);
/*      */             } 
/* 1141 */             JarEntry jarEntry = jarLoader.jar.getJarEntry(param1String);
/* 1142 */             if (jarEntry != null) {
/* 1143 */               return jarLoader.checkResource(param1String, param1Boolean, jarEntry);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1150 */             if (!jarLoader.validIndex(param1String))
/*      */             {
/* 1152 */               throw new InvalidJarIndexException("Invalid index");
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1161 */           if (bool || jarLoader == this || jarLoader
/* 1162 */             .getIndex() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*      */           Resource resource;
/*      */           
/* 1168 */           if ((resource = jarLoader.getResource(param1String, param1Boolean, param1Set)) != null)
/*      */           {
/* 1170 */             return resource;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1175 */         linkedList = this.index.get(param1String);
/*      */ 
/*      */         
/* 1178 */         if (b >= linkedList.size()) {
/* 1179 */           return null;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     URL[] getClassPath() throws IOException {
/* 1187 */       if (this.index != null) {
/* 1188 */         return null;
/*      */       }
/*      */       
/* 1191 */       if (this.metaIndex != null) {
/* 1192 */         return null;
/*      */       }
/*      */       
/* 1195 */       ensureOpen();
/* 1196 */       parseExtensionsDependencies();
/*      */       
/* 1198 */       if (SharedSecrets.javaUtilJarAccess().jarFileHasClassPathAttribute(this.jar)) {
/* 1199 */         Manifest manifest = this.jar.getManifest();
/* 1200 */         if (manifest != null) {
/* 1201 */           Attributes attributes = manifest.getMainAttributes();
/* 1202 */           if (attributes != null) {
/* 1203 */             String str = attributes.getValue(Attributes.Name.CLASS_PATH);
/* 1204 */             if (str != null) {
/* 1205 */               return parseClassPath(this.csu, str);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1210 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void parseExtensionsDependencies() throws IOException {
/* 1217 */       ExtensionDependency.checkExtensionsDependencies(this.jar);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private URL[] parseClassPath(URL param1URL, String param1String) throws MalformedURLException {
/* 1227 */       StringTokenizer stringTokenizer = new StringTokenizer(param1String);
/* 1228 */       URL[] arrayOfURL = new URL[stringTokenizer.countTokens()];
/* 1229 */       byte b = 0;
/* 1230 */       while (stringTokenizer.hasMoreTokens()) {
/* 1231 */         String str = stringTokenizer.nextToken();
/* 1232 */         URL uRL = URLClassPath.DISABLE_CP_URL_CHECK ? new URL(param1URL, str) : tryResolve(param1URL, str);
/* 1233 */         if (uRL != null) {
/* 1234 */           arrayOfURL[b] = uRL;
/* 1235 */           b++; continue;
/*      */         } 
/* 1237 */         if (URLClassPath.DEBUG_CP_URL_CHECK) {
/* 1238 */           System.err.println("Class-Path entry: \"" + str + "\" ignored in JAR file " + param1URL);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1243 */       if (b == 0) {
/* 1244 */         arrayOfURL = null;
/* 1245 */       } else if (b != arrayOfURL.length) {
/*      */         
/* 1247 */         arrayOfURL = Arrays.<URL>copyOf(arrayOfURL, b);
/*      */       } 
/* 1249 */       return arrayOfURL;
/*      */     }
/*      */     
/*      */     static URL tryResolve(URL param1URL, String param1String) throws MalformedURLException {
/* 1253 */       if ("file".equalsIgnoreCase(param1URL.getProtocol())) {
/* 1254 */         return tryResolveFile(param1URL, param1String);
/*      */       }
/* 1256 */       return tryResolveNonFile(param1URL, param1String);
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
/*      */     static URL tryResolveFile(URL param1URL, String param1String) throws MalformedURLException {
/*      */       boolean bool;
/* 1274 */       int i = param1String.indexOf(':');
/*      */       
/* 1276 */       if (i >= 0) {
/* 1277 */         String str = param1String.substring(0, i);
/* 1278 */         bool = "file".equalsIgnoreCase(str);
/*      */       } else {
/* 1280 */         bool = true;
/*      */       } 
/* 1282 */       return bool ? new URL(param1URL, param1String) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static URL tryResolveNonFile(URL param1URL, String param1String) throws MalformedURLException {
/* 1293 */       String str = param1String.replace(File.separatorChar, '/');
/* 1294 */       if (isRelative(str)) {
/* 1295 */         URL uRL = new URL(param1URL, str);
/* 1296 */         String str1 = param1URL.getPath();
/* 1297 */         String str2 = uRL.getPath();
/* 1298 */         int i = str1.lastIndexOf('/');
/* 1299 */         if (i == -1) {
/* 1300 */           i = str1.length() - 1;
/*      */         }
/* 1302 */         if (str2.regionMatches(0, str1, 0, i + 1) && str2
/* 1303 */           .indexOf("..", i) == -1) {
/* 1304 */           return uRL;
/*      */         }
/*      */       } 
/* 1307 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isRelative(String param1String) {
/*      */       try {
/* 1315 */         return !URI.create(param1String).isAbsolute();
/* 1316 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 1317 */         return false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FileLoader
/*      */     extends Loader
/*      */   {
/*      */     private File dir;
/*      */ 
/*      */     
/*      */     FileLoader(URL param1URL) throws IOException {
/* 1331 */       super(param1URL);
/* 1332 */       if (!"file".equals(param1URL.getProtocol())) {
/* 1333 */         throw new IllegalArgumentException("url");
/*      */       }
/* 1335 */       String str = param1URL.getFile().replace('/', File.separatorChar);
/* 1336 */       str = ParseUtil.decode(str);
/* 1337 */       this.dir = (new File(str)).getCanonicalFile();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     URL findResource(String param1String, boolean param1Boolean) {
/* 1344 */       Resource resource = getResource(param1String, param1Boolean);
/* 1345 */       if (resource != null) {
/* 1346 */         return resource.getURL();
/*      */       }
/* 1348 */       return null;
/*      */     }
/*      */     
/*      */     Resource getResource(final String name, boolean param1Boolean) {
/*      */       try {
/*      */         final File file;
/* 1354 */         URL uRL2 = new URL(getBaseURL(), ".");
/* 1355 */         final URL url = new URL(getBaseURL(), ParseUtil.encodePath(name, false));
/*      */         
/* 1357 */         if (!uRL1.getFile().startsWith(uRL2.getFile()))
/*      */         {
/* 1359 */           return null;
/*      */         }
/*      */         
/* 1362 */         if (param1Boolean) {
/* 1363 */           URLClassPath.check(uRL1);
/*      */         }
/*      */         
/* 1366 */         if (name.indexOf("..") != -1) {
/*      */           
/* 1368 */           file = (new File(this.dir, name.replace('/', File.separatorChar))).getCanonicalFile();
/* 1369 */           if (!file.getPath().startsWith(this.dir.getPath()))
/*      */           {
/* 1371 */             return null;
/*      */           }
/*      */         } else {
/* 1374 */           file = new File(this.dir, name.replace('/', File.separatorChar));
/*      */         } 
/*      */         
/* 1377 */         if (file.exists())
/* 1378 */           return new Resource() {
/* 1379 */               public String getName() { return name; }
/* 1380 */               public URL getURL() { return url; } public URL getCodeSourceURL() {
/* 1381 */                 return URLClassPath.FileLoader.this.getBaseURL();
/*      */               } public InputStream getInputStream() throws IOException {
/* 1383 */                 return new FileInputStream(file);
/*      */               } public int getContentLength() throws IOException {
/* 1385 */                 return (int)file.length();
/*      */               }
/*      */             }; 
/* 1388 */       } catch (Exception exception) {
/* 1389 */         return null;
/*      */       } 
/* 1391 */       return null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/URLClassPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */