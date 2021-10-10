/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionDependency
/*     */ {
/*     */   private static Vector<ExtensionInstallationProvider> providers;
/*     */   static final boolean DEBUG = false;
/*     */   
/*     */   public static synchronized void addExtensionInstallationProvider(ExtensionInstallationProvider paramExtensionInstallationProvider) {
/*  85 */     if (providers == null) {
/*  86 */       providers = new Vector<>();
/*     */     }
/*  88 */     providers.add(paramExtensionInstallationProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void removeExtensionInstallationProvider(ExtensionInstallationProvider paramExtensionInstallationProvider) {
/*  99 */     providers.remove(paramExtensionInstallationProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkExtensionsDependencies(JarFile paramJarFile) {
/* 110 */     if (providers == null)
/*     */     {
/*     */       
/* 113 */       return true;
/*     */     }
/*     */     
/*     */     try {
/* 117 */       ExtensionDependency extensionDependency = new ExtensionDependency();
/* 118 */       return extensionDependency.checkExtensions(paramJarFile);
/* 119 */     } catch (ExtensionInstallationException extensionInstallationException) {
/* 120 */       debug(extensionInstallationException.getMessage());
/*     */       
/* 122 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtensions(JarFile paramJarFile) throws ExtensionInstallationException {
/*     */     Manifest manifest;
/*     */     try {
/* 134 */       manifest = paramJarFile.getManifest();
/* 135 */     } catch (IOException iOException) {
/* 136 */       return false;
/*     */     } 
/*     */     
/* 139 */     if (manifest == null)
/*     */     {
/*     */       
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     boolean bool = true;
/* 146 */     Attributes attributes = manifest.getMainAttributes();
/* 147 */     if (attributes != null) {
/*     */       
/* 149 */       String str = attributes.getValue(Attributes.Name.EXTENSION_LIST);
/* 150 */       if (str != null) {
/* 151 */         StringTokenizer stringTokenizer = new StringTokenizer(str);
/*     */         
/* 153 */         while (stringTokenizer.hasMoreTokens()) {
/* 154 */           String str1 = stringTokenizer.nextToken();
/* 155 */           debug("The file " + paramJarFile.getName() + " appears to depend on " + str1);
/*     */ 
/*     */ 
/*     */           
/* 159 */           String str2 = str1 + "-" + Attributes.Name.EXTENSION_NAME.toString();
/* 160 */           if (attributes.getValue(str2) == null) {
/* 161 */             debug("The jar file " + paramJarFile.getName() + " appers to depend on " + str1 + " but does not define the " + str2 + " attribute in its manifest ");
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 167 */           if (!checkExtension(str1, attributes)) {
/* 168 */             debug("Failed installing " + str1);
/* 169 */             bool = false;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 174 */         debug("No dependencies for " + paramJarFile.getName());
/*     */       } 
/*     */     } 
/* 177 */     return bool;
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
/*     */   protected synchronized boolean checkExtension(String paramString, Attributes paramAttributes) throws ExtensionInstallationException {
/* 194 */     debug("Checking extension " + paramString);
/* 195 */     if (checkExtensionAgainstInstalled(paramString, paramAttributes)) {
/* 196 */       return true;
/*     */     }
/* 198 */     debug("Extension not currently installed ");
/* 199 */     ExtensionInfo extensionInfo = new ExtensionInfo(paramString, paramAttributes);
/* 200 */     return installExtension(extensionInfo, null);
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
/*     */   boolean checkExtensionAgainstInstalled(String paramString, Attributes paramAttributes) throws ExtensionInstallationException {
/* 217 */     File arrayOfFile[], file = checkExtensionExists(paramString);
/*     */     
/* 219 */     if (file != null) {
/*     */       
/*     */       try {
/* 222 */         if (checkExtensionAgainst(paramString, paramAttributes, file))
/* 223 */           return true; 
/* 224 */       } catch (FileNotFoundException fileNotFoundException) {
/* 225 */         debugException(fileNotFoundException);
/* 226 */       } catch (IOException iOException) {
/* 227 */         debugException(iOException);
/*     */       } 
/* 229 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 240 */       arrayOfFile = getInstalledExtensions();
/* 241 */     } catch (IOException iOException) {
/* 242 */       debugException(iOException);
/* 243 */       return false;
/*     */     } 
/*     */     
/* 246 */     for (byte b = 0; b < arrayOfFile.length; b++) {
/*     */       try {
/* 248 */         if (checkExtensionAgainst(paramString, paramAttributes, arrayOfFile[b]))
/* 249 */           return true; 
/* 250 */       } catch (FileNotFoundException fileNotFoundException) {
/* 251 */         debugException(fileNotFoundException);
/* 252 */       } catch (IOException iOException) {
/* 253 */         debugException(iOException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return false;
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
/*     */   protected boolean checkExtensionAgainst(String paramString, Attributes paramAttributes, final File file) throws IOException, FileNotFoundException, ExtensionInstallationException {
/*     */     Manifest manifest;
/* 281 */     debug("Checking extension " + paramString + " against " + file
/* 282 */         .getName());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 287 */       manifest = AccessController.<Manifest>doPrivileged(new PrivilegedExceptionAction<Manifest>()
/*     */           {
/*     */             public Manifest run() throws IOException, FileNotFoundException
/*     */             {
/* 291 */               if (!file.exists())
/* 292 */                 throw new FileNotFoundException(file.getName()); 
/* 293 */               JarFile jarFile = new JarFile(file);
/* 294 */               return jarFile.getManifest();
/*     */             }
/*     */           });
/* 297 */     } catch (PrivilegedActionException privilegedActionException) {
/* 298 */       if (privilegedActionException.getException() instanceof FileNotFoundException)
/* 299 */         throw (FileNotFoundException)privilegedActionException.getException(); 
/* 300 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/*     */ 
/*     */     
/* 304 */     ExtensionInfo extensionInfo1 = new ExtensionInfo(paramString, paramAttributes);
/* 305 */     debug("Requested Extension : " + extensionInfo1);
/*     */     
/* 307 */     int i = 4;
/* 308 */     ExtensionInfo extensionInfo2 = null;
/*     */     
/* 310 */     if (manifest != null) {
/* 311 */       Attributes attributes = manifest.getMainAttributes();
/* 312 */       if (attributes != null) {
/* 313 */         extensionInfo2 = new ExtensionInfo(null, attributes);
/* 314 */         debug("Extension Installed " + extensionInfo2);
/* 315 */         i = extensionInfo2.isCompatibleWith(extensionInfo1);
/* 316 */         switch (i) {
/*     */           case 0:
/* 318 */             debug("Extensions are compatible");
/* 319 */             return true;
/*     */           
/*     */           case 4:
/* 322 */             debug("Extensions are incompatible");
/* 323 */             return false;
/*     */         } 
/*     */ 
/*     */         
/* 327 */         debug("Extensions require an upgrade or vendor switch");
/* 328 */         return installExtension(extensionInfo1, extensionInfo2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 333 */     return false;
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
/*     */   protected boolean installExtension(ExtensionInfo paramExtensionInfo1, ExtensionInfo paramExtensionInfo2) throws ExtensionInstallationException {
/*     */     Vector vector;
/* 352 */     synchronized (providers) {
/*     */ 
/*     */       
/* 355 */       Vector vector1 = (Vector)providers.clone();
/* 356 */       vector = vector1;
/*     */     } 
/* 358 */     Enumeration<ExtensionInstallationProvider> enumeration = vector.elements();
/* 359 */     while (enumeration.hasMoreElements()) {
/* 360 */       ExtensionInstallationProvider extensionInstallationProvider = enumeration.nextElement();
/*     */       
/* 362 */       if (extensionInstallationProvider != null)
/*     */       {
/* 364 */         if (extensionInstallationProvider.installExtension(paramExtensionInfo1, paramExtensionInfo2)) {
/* 365 */           debug(paramExtensionInfo1.name + " installation successful");
/*     */           
/* 367 */           Launcher.ExtClassLoader extClassLoader = (Launcher.ExtClassLoader)Launcher.getLauncher().getClassLoader().getParent();
/* 368 */           addNewExtensionsToClassLoader(extClassLoader);
/* 369 */           return true;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 375 */     debug(paramExtensionInfo1.name + " installation failed");
/* 376 */     return false;
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
/*     */   private File checkExtensionExists(String paramString) {
/* 392 */     final String extName = paramString;
/* 393 */     final String[] fileExt = { ".jar", ".zip" };
/*     */     
/* 395 */     return AccessController.<File>doPrivileged(new PrivilegedAction<File>()
/*     */         {
/*     */           public File run()
/*     */           {
/*     */             try {
/* 400 */               File[] arrayOfFile = ExtensionDependency.getExtDirs();
/*     */ 
/*     */ 
/*     */               
/* 404 */               for (byte b = 0; b < arrayOfFile.length; b++) {
/* 405 */                 for (byte b1 = 0; b1 < fileExt.length; b1++) {
/* 406 */                   File file; if (extName.toLowerCase().endsWith(fileExt[b1])) {
/* 407 */                     file = new File(arrayOfFile[b], extName);
/*     */                   } else {
/* 409 */                     file = new File(arrayOfFile[b], extName + fileExt[b1]);
/*     */                   } 
/* 411 */                   ExtensionDependency.debug("checkExtensionExists:fileName " + file.getName());
/* 412 */                   if (file.exists()) {
/* 413 */                     return file;
/*     */                   }
/*     */                 } 
/*     */               } 
/* 417 */               return null;
/*     */             }
/* 419 */             catch (Exception exception) {
/* 420 */               ExtensionDependency.this.debugException(exception);
/* 421 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File[] getExtDirs() {
/*     */     File[] arrayOfFile;
/* 433 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.ext.dirs"));
/*     */ 
/*     */ 
/*     */     
/* 437 */     if (str != null) {
/* 438 */       StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
/*     */       
/* 440 */       int i = stringTokenizer.countTokens();
/* 441 */       debug("getExtDirs count " + i);
/* 442 */       arrayOfFile = new File[i];
/* 443 */       for (byte b = 0; b < i; b++) {
/* 444 */         arrayOfFile[b] = new File(stringTokenizer.nextToken());
/* 445 */         debug("getExtDirs dirs[" + b + "] " + arrayOfFile[b]);
/*     */       } 
/*     */     } else {
/* 448 */       arrayOfFile = new File[0];
/* 449 */       debug("getExtDirs dirs " + arrayOfFile);
/*     */     } 
/* 451 */     debug("getExtDirs dirs.length " + arrayOfFile.length);
/* 452 */     return arrayOfFile;
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
/*     */   private static File[] getExtFiles(File[] paramArrayOfFile) throws IOException {
/* 464 */     Vector<File> vector = new Vector();
/* 465 */     for (byte b = 0; b < paramArrayOfFile.length; b++) {
/* 466 */       String[] arrayOfString = paramArrayOfFile[b].list(new JarFilter());
/* 467 */       if (arrayOfString != null) {
/* 468 */         debug("getExtFiles files.length " + arrayOfString.length);
/* 469 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 470 */           File file = new File(paramArrayOfFile[b], arrayOfString[b1]);
/* 471 */           vector.add(file);
/* 472 */           debug("getExtFiles f[" + b1 + "] " + file);
/*     */         } 
/*     */       } 
/*     */     } 
/* 476 */     File[] arrayOfFile = new File[vector.size()];
/* 477 */     vector.copyInto((Object[])arrayOfFile);
/* 478 */     debug("getExtFiles ua.length " + arrayOfFile.length);
/* 479 */     return arrayOfFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File[] getInstalledExtensions() throws IOException {
/* 488 */     return AccessController.<File[]>doPrivileged((PrivilegedAction)new PrivilegedAction<File[]>()
/*     */         {
/*     */           public File[] run() {
/*     */             try {
/* 492 */               return ExtensionDependency.getExtFiles(ExtensionDependency.getExtDirs());
/* 493 */             } catch (IOException iOException) {
/* 494 */               ExtensionDependency.debug("Cannot get list of installed extensions");
/* 495 */               ExtensionDependency.this.debugException(iOException);
/* 496 */               return new File[0];
/*     */             } 
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
/*     */   private Boolean addNewExtensionsToClassLoader(Launcher.ExtClassLoader paramExtClassLoader) {
/*     */     try {
/* 513 */       File[] arrayOfFile = getInstalledExtensions();
/* 514 */       for (byte b = 0; b < arrayOfFile.length; b++) {
/* 515 */         final File instFile = arrayOfFile[b];
/* 516 */         URL uRL = AccessController.<URL>doPrivileged(new PrivilegedAction<URL>()
/*     */             {
/*     */               public URL run() {
/*     */                 try {
/* 520 */                   return ParseUtil.fileToEncodedURL(instFile);
/* 521 */                 } catch (MalformedURLException malformedURLException) {
/* 522 */                   ExtensionDependency.this.debugException(malformedURLException);
/* 523 */                   return null;
/*     */                 } 
/*     */               }
/*     */             });
/* 527 */         if (uRL != null) {
/* 528 */           URL[] arrayOfURL = paramExtClassLoader.getURLs();
/* 529 */           boolean bool = false;
/* 530 */           for (byte b1 = 0; b1 < arrayOfURL.length; b1++) {
/* 531 */             debug("URL[" + b1 + "] is " + arrayOfURL[b1] + " looking for " + uRL);
/*     */             
/* 533 */             if (arrayOfURL[b1].toString().compareToIgnoreCase(uRL
/* 534 */                 .toString()) == 0) {
/* 535 */               bool = true;
/* 536 */               debug("Found !");
/*     */             } 
/*     */           } 
/* 539 */           if (!bool) {
/* 540 */             debug("Not Found ! adding to the classloader " + uRL);
/*     */             
/* 542 */             paramExtClassLoader.addExtURL(uRL);
/*     */           } 
/*     */         } 
/*     */       } 
/* 546 */     } catch (MalformedURLException malformedURLException) {
/* 547 */       malformedURLException.printStackTrace();
/* 548 */     } catch (IOException iOException) {
/* 549 */       iOException.printStackTrace();
/*     */     } 
/*     */     
/* 552 */     return Boolean.TRUE;
/*     */   }
/*     */   
/*     */   private static void debug(String paramString) {}
/*     */   
/*     */   private void debugException(Throwable paramThrowable) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ExtensionDependency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */