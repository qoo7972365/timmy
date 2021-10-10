/*     */ package java.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureClassLoader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.misc.JavaNetAccess;
/*     */ import sun.misc.PerfCounter;
/*     */ import sun.misc.Resource;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.URLClassPath;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLClassLoader
/*     */   extends SecureClassLoader
/*     */   implements Closeable
/*     */ {
/*     */   private final URLClassPath ucp;
/*     */   private final AccessControlContext acc;
/*     */   private WeakHashMap<Closeable, Void> closeables;
/*     */   
/*     */   public URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader) {
/* 101 */     super(paramClassLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.closeables = new WeakHashMap<>(); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkCreateClassLoader();  this.acc = AccessController.getContext(); this.ucp = new URLClassPath(paramArrayOfURL, this.acc); } URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader, AccessControlContext paramAccessControlContext) { super(paramClassLoader); this.closeables = new WeakHashMap<>(); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkCreateClassLoader();  this.acc = paramAccessControlContext; this.ucp = new URLClassPath(paramArrayOfURL, paramAccessControlContext); } public URLClassLoader(URL[] paramArrayOfURL) { this.closeables = new WeakHashMap<>(); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkCreateClassLoader();  this.acc = AccessController.getContext(); this.ucp = new URLClassPath(paramArrayOfURL, this.acc); } URLClassLoader(URL[] paramArrayOfURL, AccessControlContext paramAccessControlContext) { this.closeables = new WeakHashMap<>(); SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkCreateClassLoader();  this.acc = paramAccessControlContext; this.ucp = new URLClassPath(paramArrayOfURL, paramAccessControlContext); } public URLClassLoader(URL[] paramArrayOfURL, ClassLoader paramClassLoader, URLStreamHandlerFactory paramURLStreamHandlerFactory) { super(paramClassLoader); this.closeables = new WeakHashMap<>();
/*     */     SecurityManager securityManager = System.getSecurityManager();
/*     */     if (securityManager != null) {
/*     */       securityManager.checkCreateClassLoader();
/*     */     }
/*     */     this.acc = AccessController.getContext();
/*     */     this.ucp = new URLClassPath(paramArrayOfURL, paramURLStreamHandlerFactory, this.acc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String paramString) {
/* 233 */     URL uRL = getResource(paramString);
/*     */     try {
/* 235 */       if (uRL == null) {
/* 236 */         return null;
/*     */       }
/* 238 */       URLConnection uRLConnection = uRL.openConnection();
/* 239 */       InputStream inputStream = uRLConnection.getInputStream();
/* 240 */       if (uRLConnection instanceof JarURLConnection) {
/* 241 */         JarURLConnection jarURLConnection = (JarURLConnection)uRLConnection;
/* 242 */         JarFile jarFile = jarURLConnection.getJarFile();
/* 243 */         synchronized (this.closeables) {
/* 244 */           if (!this.closeables.containsKey(jarFile)) {
/* 245 */             this.closeables.put(jarFile, null);
/*     */           }
/*     */         } 
/* 248 */       } else if (uRLConnection instanceof sun.net.www.protocol.file.FileURLConnection) {
/* 249 */         synchronized (this.closeables) {
/* 250 */           this.closeables.put(inputStream, null);
/*     */         } 
/*     */       } 
/* 253 */       return inputStream;
/* 254 */     } catch (IOException iOException) {
/* 255 */       return null;
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
/*     */   public void close() throws IOException {
/* 288 */     SecurityManager securityManager = System.getSecurityManager();
/* 289 */     if (securityManager != null) {
/* 290 */       securityManager.checkPermission(new RuntimePermission("closeClassLoader"));
/*     */     }
/* 292 */     List<IOException> list = this.ucp.closeLoaders();
/*     */ 
/*     */ 
/*     */     
/* 296 */     synchronized (this.closeables) {
/* 297 */       Set<Closeable> set = this.closeables.keySet();
/* 298 */       for (Closeable closeable : set) {
/*     */         try {
/* 300 */           closeable.close();
/* 301 */         } catch (IOException iOException1) {
/* 302 */           list.add(iOException1);
/*     */         } 
/*     */       } 
/* 305 */       this.closeables.clear();
/*     */     } 
/*     */     
/* 308 */     if (list.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 312 */     IOException iOException = list.remove(0);
/*     */ 
/*     */ 
/*     */     
/* 316 */     for (IOException iOException1 : list) {
/* 317 */       iOException.addSuppressed(iOException1);
/*     */     }
/* 319 */     throw iOException;
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
/*     */   protected void addURL(URL paramURL) {
/* 333 */     this.ucp.addURL(paramURL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL[] getURLs() {
/* 343 */     return this.ucp.getURLs();
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
/*     */   protected Class<?> findClass(final String name) throws ClassNotFoundException {
/*     */     Class<?> clazz;
/*     */     try {
/* 362 */       clazz = AccessController.<Class<?>>doPrivileged(new PrivilegedExceptionAction<Class<?>>()
/*     */           {
/*     */             public Class<?> run() throws ClassNotFoundException {
/* 365 */               String str = name.replace('.', '/').concat(".class");
/* 366 */               Resource resource = URLClassLoader.this.ucp.getResource(str, false);
/* 367 */               if (resource != null) {
/*     */                 try {
/* 369 */                   return URLClassLoader.this.defineClass(name, resource);
/* 370 */                 } catch (IOException iOException) {
/* 371 */                   throw new ClassNotFoundException(name, iOException);
/*     */                 } 
/*     */               }
/* 374 */               return null;
/*     */             }
/*     */           }this.acc);
/*     */     }
/* 378 */     catch (PrivilegedActionException privilegedActionException) {
/* 379 */       throw (ClassNotFoundException)privilegedActionException.getException();
/*     */     } 
/* 381 */     if (clazz == null) {
/* 382 */       throw new ClassNotFoundException(name);
/*     */     }
/* 384 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Package getAndVerifyPackage(String paramString, Manifest paramManifest, URL paramURL) {
/* 394 */     Package package_ = getPackage(paramString);
/* 395 */     if (package_ != null)
/*     */     {
/* 397 */       if (package_.isSealed()) {
/*     */         
/* 399 */         if (!package_.isSealed(paramURL)) {
/* 400 */           throw new SecurityException("sealing violation: package " + paramString + " is sealed");
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 406 */       else if (paramManifest != null && isSealed(paramString, paramManifest)) {
/* 407 */         throw new SecurityException("sealing violation: can't seal package " + paramString + ": already loaded");
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 413 */     return package_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void definePackageInternal(String paramString, Manifest paramManifest, URL paramURL) {
/* 420 */     if (getAndVerifyPackage(paramString, paramManifest, paramURL) == null) {
/*     */       try {
/* 422 */         if (paramManifest != null) {
/* 423 */           definePackage(paramString, paramManifest, paramURL);
/*     */         } else {
/* 425 */           definePackage(paramString, null, null, null, null, null, null, null);
/*     */         } 
/* 427 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */ 
/*     */         
/* 430 */         if (getAndVerifyPackage(paramString, paramManifest, paramURL) == null)
/*     */         {
/* 432 */           throw new AssertionError("Cannot find package " + paramString);
/*     */         }
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
/*     */   private Class<?> defineClass(String paramString, Resource paramResource) throws IOException {
/* 445 */     long l = System.nanoTime();
/* 446 */     int i = paramString.lastIndexOf('.');
/* 447 */     URL uRL = paramResource.getCodeSourceURL();
/* 448 */     if (i != -1) {
/* 449 */       String str = paramString.substring(0, i);
/*     */       
/* 451 */       Manifest manifest = paramResource.getManifest();
/* 452 */       definePackageInternal(str, manifest, uRL);
/*     */     } 
/*     */     
/* 455 */     ByteBuffer byteBuffer = paramResource.getByteBuffer();
/* 456 */     if (byteBuffer != null) {
/*     */       
/* 458 */       CodeSigner[] arrayOfCodeSigner1 = paramResource.getCodeSigners();
/* 459 */       CodeSource codeSource1 = new CodeSource(uRL, arrayOfCodeSigner1);
/* 460 */       PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(l);
/* 461 */       return defineClass(paramString, byteBuffer, codeSource1);
/*     */     } 
/* 463 */     byte[] arrayOfByte = paramResource.getBytes();
/*     */     
/* 465 */     CodeSigner[] arrayOfCodeSigner = paramResource.getCodeSigners();
/* 466 */     CodeSource codeSource = new CodeSource(uRL, arrayOfCodeSigner);
/* 467 */     PerfCounter.getReadClassBytesTime().addElapsedTimeFrom(l);
/* 468 */     return defineClass(paramString, arrayOfByte, 0, arrayOfByte.length, codeSource);
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
/*     */   protected Package definePackage(String paramString, Manifest paramManifest, URL paramURL) throws IllegalArgumentException {
/* 490 */     String str1 = null, str2 = null, str3 = null;
/* 491 */     String str4 = null, str5 = null, str6 = null;
/* 492 */     String str7 = null;
/* 493 */     URL uRL = null;
/*     */ 
/*     */     
/* 496 */     Attributes attributes = SharedSecrets.javaUtilJarAccess().getTrustedAttributes(paramManifest, paramString.replace('.', '/').concat("/"));
/* 497 */     if (attributes != null) {
/* 498 */       str1 = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/* 499 */       str2 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/* 500 */       str3 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/* 501 */       str4 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/* 502 */       str5 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/* 503 */       str6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/* 504 */       str7 = attributes.getValue(Attributes.Name.SEALED);
/*     */     } 
/* 506 */     attributes = paramManifest.getMainAttributes();
/* 507 */     if (attributes != null) {
/* 508 */       if (str1 == null) {
/* 509 */         str1 = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/*     */       }
/* 511 */       if (str2 == null) {
/* 512 */         str2 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/*     */       }
/* 514 */       if (str3 == null) {
/* 515 */         str3 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/*     */       }
/* 517 */       if (str4 == null) {
/* 518 */         str4 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/*     */       }
/* 520 */       if (str5 == null) {
/* 521 */         str5 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/*     */       }
/* 523 */       if (str6 == null) {
/* 524 */         str6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/*     */       }
/* 526 */       if (str7 == null) {
/* 527 */         str7 = attributes.getValue(Attributes.Name.SEALED);
/*     */       }
/*     */     } 
/* 530 */     if ("true".equalsIgnoreCase(str7)) {
/* 531 */       uRL = paramURL;
/*     */     }
/* 533 */     return definePackage(paramString, str1, str2, str3, str4, str5, str6, uRL);
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
/*     */   private boolean isSealed(String paramString, Manifest paramManifest) {
/* 545 */     Attributes attributes = SharedSecrets.javaUtilJarAccess().getTrustedAttributes(paramManifest, paramString.replace('.', '/').concat("/"));
/* 546 */     String str = null;
/* 547 */     if (attributes != null) {
/* 548 */       str = attributes.getValue(Attributes.Name.SEALED);
/*     */     }
/* 550 */     if (str == null && (
/* 551 */       attributes = paramManifest.getMainAttributes()) != null) {
/* 552 */       str = attributes.getValue(Attributes.Name.SEALED);
/*     */     }
/*     */     
/* 555 */     return "true".equalsIgnoreCase(str);
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
/*     */   public URL findResource(final String name) {
/* 569 */     URL uRL = AccessController.<URL>doPrivileged(new PrivilegedAction<URL>()
/*     */         {
/*     */           public URL run() {
/* 572 */             return URLClassLoader.this.ucp.findResource(name, true);
/*     */           }
/*     */         }this.acc);
/*     */     
/* 576 */     return (uRL != null) ? this.ucp.checkURL(uRL) : null;
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
/*     */   public Enumeration<URL> findResources(String paramString) throws IOException {
/* 591 */     final Enumeration e = this.ucp.findResources(paramString, true);
/*     */     
/* 593 */     return new Enumeration<URL>() {
/* 594 */         private URL url = null;
/*     */         
/*     */         private boolean next() {
/* 597 */           if (this.url != null) {
/* 598 */             return true;
/*     */           }
/*     */           do {
/* 601 */             URL uRL = AccessController.<URL>doPrivileged(new PrivilegedAction<URL>()
/*     */                 {
/*     */                   public URL run() {
/* 604 */                     if (!e.hasMoreElements())
/* 605 */                       return null; 
/* 606 */                     return e.nextElement();
/*     */                   }
/* 608 */                 },  URLClassLoader.this.acc);
/* 609 */             if (uRL == null)
/*     */               break; 
/* 611 */             this.url = URLClassLoader.this.ucp.checkURL(uRL);
/* 612 */           } while (this.url == null);
/* 613 */           return (this.url != null);
/*     */         }
/*     */         
/*     */         public URL nextElement() {
/* 617 */           if (!next()) {
/* 618 */             throw new NoSuchElementException();
/*     */           }
/* 620 */           URL uRL = this.url;
/* 621 */           this.url = null;
/* 622 */           return uRL;
/*     */         }
/*     */         
/*     */         public boolean hasMoreElements() {
/* 626 */           return next();
/*     */         }
/*     */       };
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
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource) {
/*     */     Permission permission;
/*     */     JarURLConnection jarURLConnection;
/* 657 */     PermissionCollection permissionCollection = super.getPermissions(paramCodeSource);
/*     */     
/* 659 */     URL uRL = paramCodeSource.getLocation();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 665 */       jarURLConnection = (JarURLConnection)uRL.openConnection();
/* 666 */       permission = jarURLConnection.getPermission();
/* 667 */     } catch (IOException iOException) {
/* 668 */       permission = null;
/* 669 */       jarURLConnection = null;
/*     */     } 
/*     */     
/* 672 */     if (permission instanceof FilePermission) {
/*     */ 
/*     */ 
/*     */       
/* 676 */       String str = permission.getName();
/* 677 */       if (str.endsWith(File.separator)) {
/* 678 */         str = str + "-";
/* 679 */         permission = new FilePermission(str, "read");
/*     */       } 
/* 681 */     } else if (permission == null && uRL.getProtocol().equals("file")) {
/* 682 */       String str = uRL.getFile().replace('/', File.separatorChar);
/* 683 */       str = ParseUtil.decode(str);
/* 684 */       if (str.endsWith(File.separator))
/* 685 */         str = str + "-"; 
/* 686 */       permission = new FilePermission(str, "read");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 693 */       URL uRL1 = uRL;
/* 694 */       if (jarURLConnection instanceof JarURLConnection) {
/* 695 */         uRL1 = ((JarURLConnection)jarURLConnection).getJarFileURL();
/*     */       }
/* 697 */       String str = uRL1.getHost();
/* 698 */       if (str != null && str.length() > 0) {
/* 699 */         permission = new SocketPermission(str, "connect,accept");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 706 */     if (permission != null) {
/* 707 */       final SecurityManager sm = System.getSecurityManager();
/* 708 */       if (securityManager != null) {
/* 709 */         final Permission fp = permission;
/* 710 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() throws SecurityException {
/* 712 */                 sm.checkPermission(fp);
/* 713 */                 return null;
/*     */               }
/*     */             },  this.acc);
/*     */       } 
/* 717 */       permissionCollection.add(permission);
/*     */     } 
/* 719 */     return permissionCollection;
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
/*     */   public static URLClassLoader newInstance(final URL[] urls, final ClassLoader parent) {
/* 738 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/* 740 */     return AccessController.<URLClassLoader>doPrivileged(new PrivilegedAction<URLClassLoader>()
/*     */         {
/*     */           public URLClassLoader run() {
/* 743 */             return new FactoryURLClassLoader(urls, parent, acc);
/*     */           }
/*     */         });
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
/*     */   public static URLClassLoader newInstance(final URL[] urls) {
/* 763 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/* 765 */     return AccessController.<URLClassLoader>doPrivileged(new PrivilegedAction<URLClassLoader>()
/*     */         {
/*     */           public URLClassLoader run() {
/* 768 */             return new FactoryURLClassLoader(urls, acc);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 775 */     SharedSecrets.setJavaNetAccess(new JavaNetAccess()
/*     */         {
/*     */           public URLClassPath getURLClassPath(URLClassLoader param1URLClassLoader) {
/* 778 */             return param1URLClassLoader.ucp;
/*     */           }
/*     */           
/*     */           public String getOriginalHostName(InetAddress param1InetAddress) {
/* 782 */             return param1InetAddress.holder.getOriginalHostName();
/*     */           }
/*     */         });
/*     */     
/* 786 */     ClassLoader.registerAsParallelCapable();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */