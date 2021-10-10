/*     */ package java.lang;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarInputStream;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Package
/*     */   implements AnnotatedElement
/*     */ {
/*     */   public String getName() {
/* 120 */     return this.pkgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSpecificationTitle() {
/* 129 */     return this.specTitle;
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
/*     */   public String getSpecificationVersion() {
/* 142 */     return this.specVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSpecificationVendor() {
/* 152 */     return this.specVendor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplementationTitle() {
/* 160 */     return this.implTitle;
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
/*     */   public String getImplementationVersion() {
/* 173 */     return this.implVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplementationVendor() {
/* 182 */     return this.implVendor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSealed() {
/* 191 */     return (this.sealBase != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSealed(URL paramURL) {
/* 202 */     return paramURL.equals(this.sealBase);
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
/*     */   public boolean isCompatibleWith(String paramString) throws NumberFormatException {
/* 230 */     if (this.specVersion == null || this.specVersion.length() < 1) {
/* 231 */       throw new NumberFormatException("Empty version string");
/*     */     }
/*     */     
/* 234 */     String[] arrayOfString1 = this.specVersion.split("\\.", -1);
/* 235 */     int[] arrayOfInt1 = new int[arrayOfString1.length];
/* 236 */     for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/* 237 */       arrayOfInt1[b1] = Integer.parseInt(arrayOfString1[b1]);
/* 238 */       if (arrayOfInt1[b1] < 0) {
/* 239 */         throw NumberFormatException.forInputString("" + arrayOfInt1[b1]);
/*     */       }
/*     */     } 
/* 242 */     String[] arrayOfString2 = paramString.split("\\.", -1);
/* 243 */     int[] arrayOfInt2 = new int[arrayOfString2.length]; int i;
/* 244 */     for (i = 0; i < arrayOfString2.length; i++) {
/* 245 */       arrayOfInt2[i] = Integer.parseInt(arrayOfString2[i]);
/* 246 */       if (arrayOfInt2[i] < 0) {
/* 247 */         throw NumberFormatException.forInputString("" + arrayOfInt2[i]);
/*     */       }
/*     */     } 
/* 250 */     i = Math.max(arrayOfInt2.length, arrayOfInt1.length);
/* 251 */     for (byte b2 = 0; b2 < i; b2++) {
/* 252 */       byte b3 = (b2 < arrayOfInt2.length) ? arrayOfInt2[b2] : 0;
/* 253 */       byte b4 = (b2 < arrayOfInt1.length) ? arrayOfInt1[b2] : 0;
/* 254 */       if (b4 < b3)
/* 255 */         return false; 
/* 256 */       if (b4 > b3)
/* 257 */         return true; 
/*     */     } 
/* 259 */     return true;
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
/*     */   @CallerSensitive
/*     */   public static Package getPackage(String paramString) {
/* 280 */     ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
/* 281 */     if (classLoader != null) {
/* 282 */       return classLoader.getPackage(paramString);
/*     */     }
/* 284 */     return getSystemPackage(paramString);
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
/*     */   @CallerSensitive
/*     */   public static Package[] getPackages() {
/* 302 */     ClassLoader classLoader = ClassLoader.getClassLoader(Reflection.getCallerClass());
/* 303 */     if (classLoader != null) {
/* 304 */       return classLoader.getPackages();
/*     */     }
/* 306 */     return getSystemPackages();
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
/*     */   static Package getPackage(Class<?> paramClass) {
/* 328 */     String str = paramClass.getName();
/* 329 */     int i = str.lastIndexOf('.');
/* 330 */     if (i != -1) {
/* 331 */       str = str.substring(0, i);
/* 332 */       ClassLoader classLoader = paramClass.getClassLoader();
/* 333 */       if (classLoader != null) {
/* 334 */         return classLoader.getPackage(str);
/*     */       }
/* 336 */       return getSystemPackage(str);
/*     */     } 
/*     */     
/* 339 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 348 */     return this.pkgName.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 359 */     String str1 = this.specTitle;
/* 360 */     String str2 = this.specVersion;
/* 361 */     if (str1 != null && str1.length() > 0) {
/* 362 */       str1 = ", " + str1;
/*     */     } else {
/* 364 */       str1 = "";
/* 365 */     }  if (str2 != null && str2.length() > 0) {
/* 366 */       str2 = ", version " + str2;
/*     */     } else {
/* 368 */       str2 = "";
/* 369 */     }  return "package " + this.pkgName + str1 + str2;
/*     */   }
/*     */   
/*     */   private Class<?> getPackageInfo() {
/* 373 */     if (this.packageInfo == null) {
/*     */       try {
/* 375 */         this.packageInfo = Class.forName(this.pkgName + ".package-info", false, this.loader);
/* 376 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */         
/* 379 */         this.packageInfo = PackageInfoProxy.class;
/*     */       } 
/*     */     }
/* 382 */     return this.packageInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(Class<A> paramClass) {
/* 390 */     return getPackageInfo().getAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass) {
/* 400 */     return super.isAnnotationPresent(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A[] getAnnotationsByType(Class<A> paramClass) {
/* 409 */     return getPackageInfo().getAnnotationsByType(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 416 */     return getPackageInfo().getAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A getDeclaredAnnotation(Class<A> paramClass) {
/* 425 */     return getPackageInfo().getDeclaredAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> paramClass) {
/* 434 */     return getPackageInfo().getDeclaredAnnotationsByType(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 441 */     return getPackageInfo().getDeclaredAnnotations();
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
/*     */   Package(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, URL paramURL, ClassLoader paramClassLoader) {
/* 460 */     this.pkgName = paramString1;
/* 461 */     this.implTitle = paramString5;
/* 462 */     this.implVersion = paramString6;
/* 463 */     this.implVendor = paramString7;
/* 464 */     this.specTitle = paramString2;
/* 465 */     this.specVersion = paramString3;
/* 466 */     this.specVendor = paramString4;
/* 467 */     this.sealBase = paramURL;
/* 468 */     this.loader = paramClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Package(String paramString, Manifest paramManifest, URL paramURL, ClassLoader paramClassLoader) {
/* 479 */     String str1 = paramString.replace('.', '/').concat("/");
/* 480 */     String str2 = null;
/* 481 */     String str3 = null;
/* 482 */     String str4 = null;
/* 483 */     String str5 = null;
/* 484 */     String str6 = null;
/* 485 */     String str7 = null;
/* 486 */     String str8 = null;
/* 487 */     URL uRL = null;
/* 488 */     Attributes attributes = paramManifest.getAttributes(str1);
/* 489 */     if (attributes != null) {
/* 490 */       str3 = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/* 491 */       str4 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/* 492 */       str5 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/* 493 */       str6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/* 494 */       str7 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/* 495 */       str8 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/* 496 */       str2 = attributes.getValue(Attributes.Name.SEALED);
/*     */     } 
/* 498 */     attributes = paramManifest.getMainAttributes();
/* 499 */     if (attributes != null) {
/* 500 */       if (str3 == null) {
/* 501 */         str3 = attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
/*     */       }
/* 503 */       if (str4 == null) {
/* 504 */         str4 = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
/*     */       }
/* 506 */       if (str5 == null) {
/* 507 */         str5 = attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
/*     */       }
/* 509 */       if (str6 == null) {
/* 510 */         str6 = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
/*     */       }
/* 512 */       if (str7 == null) {
/* 513 */         str7 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
/*     */       }
/* 515 */       if (str8 == null) {
/* 516 */         str8 = attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
/*     */       }
/* 518 */       if (str2 == null) {
/* 519 */         str2 = attributes.getValue(Attributes.Name.SEALED);
/*     */       }
/*     */     } 
/* 522 */     if ("true".equalsIgnoreCase(str2)) {
/* 523 */       uRL = paramURL;
/*     */     }
/* 525 */     this.pkgName = paramString;
/* 526 */     this.specTitle = str3;
/* 527 */     this.specVersion = str4;
/* 528 */     this.specVendor = str5;
/* 529 */     this.implTitle = str6;
/* 530 */     this.implVersion = str7;
/* 531 */     this.implVendor = str8;
/* 532 */     this.sealBase = uRL;
/* 533 */     this.loader = paramClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Package getSystemPackage(String paramString) {
/* 540 */     synchronized (pkgs) {
/* 541 */       Package package_ = pkgs.get(paramString);
/* 542 */       if (package_ == null) {
/* 543 */         paramString = paramString.replace('.', '/').concat("/");
/* 544 */         String str = getSystemPackage0(paramString);
/* 545 */         if (str != null) {
/* 546 */           package_ = defineSystemPackage(paramString, str);
/*     */         }
/*     */       } 
/* 549 */       return package_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Package[] getSystemPackages() {
/* 558 */     String[] arrayOfString = getSystemPackages0();
/* 559 */     synchronized (pkgs) {
/* 560 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 561 */         defineSystemPackage(arrayOfString[b], getSystemPackage0(arrayOfString[b]));
/*     */       }
/* 563 */       return (Package[])pkgs.values().toArray((Object[])new Package[pkgs.size()]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Package defineSystemPackage(final String iname, final String fn) {
/* 570 */     return AccessController.<Package>doPrivileged(new PrivilegedAction<Package>() { public Package run() {
/*     */             Package package_;
/* 572 */             String str = iname;
/*     */             
/* 574 */             URL uRL = (URL)Package.urls.get(fn);
/* 575 */             if (uRL == null) {
/*     */               
/* 577 */               File file = new File(fn);
/*     */               try {
/* 579 */                 uRL = ParseUtil.fileToEncodedURL(file);
/* 580 */               } catch (MalformedURLException malformedURLException) {}
/*     */               
/* 582 */               if (uRL != null) {
/* 583 */                 Package.urls.put(fn, uRL);
/*     */                 
/* 585 */                 if (file.isFile()) {
/* 586 */                   Package.mans.put(fn, Package.loadManifest(fn));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 591 */             str = str.substring(0, str.length() - 1).replace('/', '.');
/*     */             
/* 593 */             Manifest manifest = (Manifest)Package.mans.get(fn);
/* 594 */             if (manifest != null) {
/* 595 */               package_ = new Package(str, manifest, uRL, null);
/*     */             } else {
/* 597 */               package_ = new Package(str, null, null, null, null, null, null, null, null);
/*     */             } 
/*     */             
/* 600 */             Package.pkgs.put(str, package_);
/* 601 */             return package_;
/*     */           } }
/*     */       );
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Manifest loadManifest(String paramString) {
/* 610 */     try(FileInputStream null = new FileInputStream(paramString); 
/* 611 */         JarInputStream null = new JarInputStream(fileInputStream, false)) {
/*     */       
/* 613 */       return jarInputStream.getManifest();
/* 614 */     } catch (IOException iOException) {
/* 615 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 620 */   private static Map<String, Package> pkgs = new HashMap<>(31);
/*     */ 
/*     */   
/* 623 */   private static Map<String, URL> urls = new HashMap<>(10);
/*     */ 
/*     */   
/* 626 */   private static Map<String, Manifest> mans = new HashMap<>(10);
/*     */   private final String pkgName;
/*     */   private final String specTitle;
/*     */   private final String specVersion;
/*     */   private final String specVendor;
/*     */   private final String implTitle;
/*     */   private final String implVersion;
/*     */   private final String implVendor;
/*     */   private final URL sealBase;
/*     */   private final transient ClassLoader loader;
/*     */   private transient Class<?> packageInfo;
/*     */   
/*     */   private static native String getSystemPackage0(String paramString);
/*     */   
/*     */   private static native String[] getSystemPackages0();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Package.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */