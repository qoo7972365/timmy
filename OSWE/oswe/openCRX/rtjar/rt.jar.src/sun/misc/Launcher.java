/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.net.URLStreamHandlerFactory;
/*     */ import java.nio.file.Paths;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashSet;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.nio.fs.DefaultFileSystemProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Launcher
/*     */ {
/*  53 */   private static URLStreamHandlerFactory factory = new Factory();
/*  54 */   private static Launcher launcher = new Launcher();
/*     */   
/*  56 */   private static String bootClassPath = System.getProperty("sun.boot.class.path");
/*     */   
/*     */   public static Launcher getLauncher() {
/*  59 */     return launcher;
/*     */   }
/*     */   
/*     */   private ClassLoader loader;
/*     */   private static URLStreamHandler fileHandler;
/*     */   
/*     */   public Launcher() {
/*     */     ExtClassLoader extClassLoader;
/*     */     try {
/*  68 */       extClassLoader = ExtClassLoader.getExtClassLoader();
/*  69 */     } catch (IOException iOException) {
/*  70 */       throw new InternalError("Could not create extension class loader", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  76 */       this.loader = AppClassLoader.getAppClassLoader(extClassLoader);
/*  77 */     } catch (IOException iOException) {
/*  78 */       throw new InternalError("Could not create application class loader", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  83 */     Thread.currentThread().setContextClassLoader(this.loader);
/*     */ 
/*     */     
/*  86 */     String str = System.getProperty("java.security.manager");
/*  87 */     if (str != null) {
/*     */       
/*  89 */       DefaultFileSystemProvider.create();
/*     */       
/*  91 */       SecurityManager securityManager = null;
/*  92 */       if ("".equals(str) || "default".equals(str)) {
/*  93 */         securityManager = new SecurityManager();
/*     */       } else {
/*     */         
/*  96 */         try { securityManager = (SecurityManager)this.loader.loadClass(str).newInstance(); }
/*  97 */         catch (IllegalAccessException illegalAccessException) {  }
/*  98 */         catch (InstantiationException instantiationException) {  }
/*  99 */         catch (ClassNotFoundException classNotFoundException) {  }
/* 100 */         catch (ClassCastException classCastException) {}
/*     */       } 
/*     */       
/* 103 */       if (securityManager != null) {
/* 104 */         System.setSecurityManager(securityManager);
/*     */       } else {
/* 106 */         throw new InternalError("Could not create SecurityManager: " + str);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 116 */     return this.loader;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ExtClassLoader
/*     */     extends URLClassLoader
/*     */   {
/*     */     static {
/* 125 */       ClassLoader.registerAsParallelCapable();
/*     */     }
/* 127 */     private static volatile ExtClassLoader instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static ExtClassLoader getExtClassLoader() throws IOException {
/* 135 */       if (instance == null) {
/* 136 */         synchronized (ExtClassLoader.class) {
/* 137 */           if (instance == null) {
/* 138 */             instance = createExtClassLoader();
/*     */           }
/*     */         } 
/*     */       }
/* 142 */       return instance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static ExtClassLoader createExtClassLoader() throws IOException {
/*     */       try {
/* 151 */         return AccessController.<ExtClassLoader>doPrivileged(new PrivilegedExceptionAction<ExtClassLoader>()
/*     */             {
/*     */               public Launcher.ExtClassLoader run() throws IOException {
/* 154 */                 File[] arrayOfFile = Launcher.ExtClassLoader.getExtDirs();
/* 155 */                 int i = arrayOfFile.length;
/* 156 */                 for (byte b = 0; b < i; b++) {
/* 157 */                   MetaIndex.registerDirectory(arrayOfFile[b]);
/*     */                 }
/* 159 */                 return new Launcher.ExtClassLoader(arrayOfFile);
/*     */               }
/*     */             });
/* 162 */       } catch (PrivilegedActionException privilegedActionException) {
/* 163 */         throw (IOException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */     
/*     */     void addExtURL(URL param1URL) {
/* 168 */       addURL(param1URL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ExtClassLoader(File[] param1ArrayOfFile) throws IOException {
/* 175 */       super(getExtURLs(param1ArrayOfFile), (ClassLoader)null, Launcher.factory);
/* 176 */       SharedSecrets.getJavaNetAccess()
/* 177 */         .getURLClassPath(this).initLookupCache(this);
/*     */     }
/*     */     private static File[] getExtDirs() {
/*     */       File[] arrayOfFile;
/* 181 */       String str = System.getProperty("java.ext.dirs");
/*     */       
/* 183 */       if (str != null) {
/* 184 */         StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
/*     */         
/* 186 */         int i = stringTokenizer.countTokens();
/* 187 */         arrayOfFile = new File[i];
/* 188 */         for (byte b = 0; b < i; b++) {
/* 189 */           arrayOfFile[b] = new File(stringTokenizer.nextToken());
/*     */         }
/*     */       } else {
/* 192 */         arrayOfFile = new File[0];
/*     */       } 
/* 194 */       return arrayOfFile;
/*     */     }
/*     */     
/*     */     private static URL[] getExtURLs(File[] param1ArrayOfFile) throws IOException {
/* 198 */       Vector<URL> vector = new Vector();
/* 199 */       for (byte b = 0; b < param1ArrayOfFile.length; b++) {
/* 200 */         String[] arrayOfString = param1ArrayOfFile[b].list();
/* 201 */         if (arrayOfString != null) {
/* 202 */           for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 203 */             if (!arrayOfString[b1].equals("meta-index")) {
/* 204 */               File file = new File(param1ArrayOfFile[b], arrayOfString[b1]);
/* 205 */               vector.add(Launcher.getFileURL(file));
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 210 */       URL[] arrayOfURL = new URL[vector.size()];
/* 211 */       vector.copyInto((Object[])arrayOfURL);
/* 212 */       return arrayOfURL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String findLibrary(String param1String) {
/* 223 */       param1String = System.mapLibraryName(param1String);
/* 224 */       URL[] arrayOfURL = getURLs();
/* 225 */       File file = null;
/* 226 */       for (byte b = 0; b < arrayOfURL.length; b++) {
/*     */         URI uRI;
/*     */ 
/*     */         
/*     */         try {
/* 231 */           uRI = arrayOfURL[b].toURI();
/* 232 */         } catch (URISyntaxException uRISyntaxException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 238 */         File file1 = Paths.get(uRI).toFile().getParentFile();
/* 239 */         if (file1 != null && !file1.equals(file)) {
/*     */ 
/*     */           
/* 242 */           String str = VM.getSavedProperty("os.arch");
/* 243 */           if (str != null) {
/* 244 */             File file3 = new File(new File(file1, str), param1String);
/* 245 */             if (file3.exists()) {
/* 246 */               return file3.getAbsolutePath();
/*     */             }
/*     */           } 
/*     */           
/* 250 */           File file2 = new File(file1, param1String);
/* 251 */           if (file2.exists()) {
/* 252 */             return file2.getAbsolutePath();
/*     */           }
/*     */         } 
/* 255 */         file = file1;
/*     */       } 
/* 257 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static AccessControlContext getContext(File[] param1ArrayOfFile) throws IOException {
/* 263 */       PathPermissions pathPermissions = new PathPermissions(param1ArrayOfFile);
/*     */ 
/*     */ 
/*     */       
/* 267 */       ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(pathPermissions.getCodeBase(), (Certificate[])null), pathPermissions);
/*     */ 
/*     */ 
/*     */       
/* 271 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AppClassLoader
/*     */     extends URLClassLoader
/*     */   {
/*     */     final URLClassPath ucp;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 285 */       ClassLoader.registerAsParallelCapable();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static ClassLoader getAppClassLoader(final ClassLoader extcl) throws IOException {
/* 291 */       final String s = System.getProperty("java.class.path");
/* 292 */       final File[] path = (str == null) ? new File[0] : Launcher.getClassPath(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       return AccessController.<ClassLoader>doPrivileged((PrivilegedAction)new PrivilegedAction<AppClassLoader>()
/*     */           {
/*     */             public Launcher.AppClassLoader run()
/*     */             {
/* 305 */               URL[] arrayOfURL = (s == null) ? new URL[0] : Launcher.pathToURLs(path);
/* 306 */               return new Launcher.AppClassLoader(arrayOfURL, extcl);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AppClassLoader(URL[] param1ArrayOfURL, ClassLoader param1ClassLoader) {
/* 317 */       super(param1ArrayOfURL, param1ClassLoader, Launcher.factory);
/* 318 */       this.ucp = SharedSecrets.getJavaNetAccess().getURLClassPath(this);
/* 319 */       this.ucp.initLookupCache(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<?> loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException {
/* 328 */       int i = param1String.lastIndexOf('.');
/* 329 */       if (i != -1) {
/* 330 */         SecurityManager securityManager = System.getSecurityManager();
/* 331 */         if (securityManager != null) {
/* 332 */           securityManager.checkPackageAccess(param1String.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 336 */       if (this.ucp.knownToNotExist(param1String)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 342 */         Class<?> clazz = findLoadedClass(param1String);
/* 343 */         if (clazz != null) {
/* 344 */           if (param1Boolean) {
/* 345 */             resolveClass(clazz);
/*     */           }
/* 347 */           return clazz;
/*     */         } 
/* 349 */         throw new ClassNotFoundException(param1String);
/*     */       } 
/*     */       
/* 352 */       return super.loadClass(param1String, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected PermissionCollection getPermissions(CodeSource param1CodeSource) {
/* 360 */       PermissionCollection permissionCollection = super.getPermissions(param1CodeSource);
/* 361 */       permissionCollection.add(new RuntimePermission("exitVM"));
/* 362 */       return permissionCollection;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void appendToClassPathForInstrumentation(String param1String) {
/* 372 */       assert Thread.holdsLock(this);
/*     */ 
/*     */       
/* 375 */       addURL(Launcher.getFileURL(new File(param1String)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static AccessControlContext getContext(File[] param1ArrayOfFile) throws MalformedURLException {
/* 388 */       PathPermissions pathPermissions = new PathPermissions(param1ArrayOfFile);
/*     */ 
/*     */ 
/*     */       
/* 392 */       ProtectionDomain protectionDomain = new ProtectionDomain(new CodeSource(pathPermissions.getCodeBase(), (Certificate[])null), pathPermissions);
/*     */ 
/*     */ 
/*     */       
/* 396 */       return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BootClassPathHolder
/*     */   {
/*     */     static final URLClassPath bcp;
/*     */     
/*     */     static {
/*     */       URL[] arrayOfURL;
/* 407 */       if (Launcher.bootClassPath != null) {
/* 408 */         arrayOfURL = AccessController.<URL[]>doPrivileged((PrivilegedAction)new PrivilegedAction<URL[]>()
/*     */             {
/*     */               public URL[] run() {
/* 411 */                 File[] arrayOfFile = Launcher.getClassPath(Launcher.bootClassPath);
/* 412 */                 int i = arrayOfFile.length;
/* 413 */                 HashSet<File> hashSet = new HashSet();
/* 414 */                 for (byte b = 0; b < i; b++) {
/* 415 */                   File file = arrayOfFile[b];
/*     */ 
/*     */                   
/* 418 */                   if (!file.isDirectory()) {
/* 419 */                     file = file.getParentFile();
/*     */                   }
/* 421 */                   if (file != null && hashSet.add(file)) {
/* 422 */                     MetaIndex.registerDirectory(file);
/*     */                   }
/*     */                 } 
/* 425 */                 return Launcher.pathToURLs(arrayOfFile);
/*     */               }
/*     */             });
/*     */       } else {
/*     */         
/* 430 */         arrayOfURL = new URL[0];
/*     */       } 
/* 432 */       bcp = new URLClassPath(arrayOfURL, Launcher.factory, null);
/* 433 */       bcp.initLookupCache(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static URLClassPath getBootstrapClassPath() {
/* 438 */     return BootClassPathHolder.bcp;
/*     */   }
/*     */   
/*     */   private static URL[] pathToURLs(File[] paramArrayOfFile) {
/* 442 */     URL[] arrayOfURL = new URL[paramArrayOfFile.length];
/* 443 */     for (byte b = 0; b < paramArrayOfFile.length; b++) {
/* 444 */       arrayOfURL[b] = getFileURL(paramArrayOfFile[b]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 450 */     return arrayOfURL;
/*     */   }
/*     */   
/*     */   private static File[] getClassPath(String paramString) {
/*     */     File[] arrayOfFile;
/* 455 */     if (paramString != null) {
/* 456 */       byte b1 = 0, b2 = 1;
/* 457 */       int i = 0, j = 0;
/*     */       
/* 459 */       while ((i = paramString.indexOf(File.pathSeparator, j)) != -1) {
/* 460 */         b2++;
/* 461 */         j = i + 1;
/*     */       } 
/* 463 */       arrayOfFile = new File[b2];
/* 464 */       j = i = 0;
/*     */       
/* 466 */       while ((i = paramString.indexOf(File.pathSeparator, j)) != -1) {
/* 467 */         if (i - j > 0) {
/* 468 */           arrayOfFile[b1++] = new File(paramString.substring(j, i));
/*     */         } else {
/*     */           
/* 471 */           arrayOfFile[b1++] = new File(".");
/*     */         } 
/* 473 */         j = i + 1;
/*     */       } 
/*     */       
/* 476 */       if (j < paramString.length()) {
/* 477 */         arrayOfFile[b1++] = new File(paramString.substring(j));
/*     */       } else {
/* 479 */         arrayOfFile[b1++] = new File(".");
/*     */       } 
/*     */       
/* 482 */       if (b1 != b2) {
/* 483 */         File[] arrayOfFile1 = new File[b1];
/* 484 */         System.arraycopy(arrayOfFile, 0, arrayOfFile1, 0, b1);
/* 485 */         arrayOfFile = arrayOfFile1;
/*     */       } 
/*     */     } else {
/* 488 */       arrayOfFile = new File[0];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     return arrayOfFile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static URL getFileURL(File paramFile) {
/*     */     try {
/* 501 */       paramFile = paramFile.getCanonicalFile();
/* 502 */     } catch (IOException iOException) {}
/*     */     
/*     */     try {
/* 505 */       return ParseUtil.fileToEncodedURL(paramFile);
/* 506 */     } catch (MalformedURLException malformedURLException) {
/*     */       
/* 508 */       throw new InternalError(malformedURLException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Factory
/*     */     implements URLStreamHandlerFactory {
/*     */     private Factory() {}
/*     */     
/* 516 */     private static String PREFIX = "sun.net.www.protocol";
/*     */     
/*     */     public URLStreamHandler createURLStreamHandler(String param1String) {
/* 519 */       String str = PREFIX + "." + param1String + ".Handler";
/*     */       try {
/* 521 */         Class<?> clazz = Class.forName(str);
/* 522 */         return (URLStreamHandler)clazz.newInstance();
/* 523 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 524 */         throw new InternalError("could not load " + param1String + "system protocol handler", reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Launcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */