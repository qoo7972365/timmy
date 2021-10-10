/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.CodeSource;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.util.ManifestEntryVerifier;
/*     */ import sun.security.util.SignatureFileVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarFile
/*     */   extends ZipFile
/*     */ {
/*     */   private SoftReference<Manifest> manRef;
/*     */   private JarEntry manEntry;
/*     */   private JarVerifier jv;
/*     */   private boolean jvInitialized;
/*     */   private boolean verify;
/*     */   private boolean hasClassPathAttribute;
/*     */   private volatile boolean hasCheckedSpecialAttributes;
/*     */   public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
/*     */   
/*     */   static {
/*  85 */     SharedSecrets.setJavaUtilJarAccess(new JavaUtilJarAccessImpl());
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
/*     */   public JarFile(String paramString) throws IOException {
/* 103 */     this(new File(paramString), true, 1);
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
/*     */   public JarFile(String paramString, boolean paramBoolean) throws IOException {
/* 117 */     this(new File(paramString), paramBoolean, 1);
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
/*     */   public JarFile(File paramFile) throws IOException {
/* 130 */     this(paramFile, true, 1);
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
/*     */   public JarFile(File paramFile, boolean paramBoolean) throws IOException {
/* 145 */     this(paramFile, paramBoolean, 1);
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
/*     */   public JarFile(File paramFile, boolean paramBoolean, int paramInt) throws IOException {
/* 166 */     super(paramFile, paramInt);
/* 167 */     this.verify = paramBoolean;
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
/*     */   public Manifest getManifest() throws IOException {
/* 180 */     return getManifestFromReference();
/*     */   }
/*     */   
/*     */   private Manifest getManifestFromReference() throws IOException {
/* 184 */     Manifest manifest = (this.manRef != null) ? this.manRef.get() : null;
/*     */     
/* 186 */     if (manifest == null) {
/*     */       
/* 188 */       JarEntry jarEntry = getManEntry();
/*     */ 
/*     */       
/* 191 */       if (jarEntry != null) {
/* 192 */         if (this.verify) {
/* 193 */           byte[] arrayOfByte = getBytes(jarEntry);
/* 194 */           if (!this.jvInitialized) {
/* 195 */             this.jv = new JarVerifier(arrayOfByte);
/*     */           }
/* 197 */           manifest = new Manifest(this.jv, new ByteArrayInputStream(arrayOfByte));
/*     */         } else {
/* 199 */           manifest = new Manifest(super.getInputStream(jarEntry));
/*     */         } 
/* 201 */         this.manRef = new SoftReference<>(manifest);
/*     */       } 
/*     */     } 
/* 204 */     return manifest;
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
/*     */   public JarEntry getJarEntry(String paramString) {
/* 223 */     return (JarEntry)getEntry(paramString);
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
/*     */   public ZipEntry getEntry(String paramString) {
/* 240 */     ZipEntry zipEntry = super.getEntry(paramString);
/* 241 */     if (zipEntry != null) {
/* 242 */       return new JarFileEntry(zipEntry);
/*     */     }
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   private class JarEntryIterator
/*     */     implements Enumeration<JarEntry>, Iterator<JarEntry>
/*     */   {
/* 250 */     final Enumeration<? extends ZipEntry> e = JarFile.this.entries();
/*     */     
/*     */     public boolean hasNext() {
/* 253 */       return this.e.hasMoreElements();
/*     */     }
/*     */     
/*     */     public JarEntry next() {
/* 257 */       ZipEntry zipEntry = this.e.nextElement();
/* 258 */       return new JarFile.JarFileEntry(zipEntry);
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 262 */       return hasNext();
/*     */     }
/*     */     
/*     */     public JarEntry nextElement() {
/* 266 */       return next();
/*     */     }
/*     */ 
/*     */     
/*     */     private JarEntryIterator() {}
/*     */   }
/*     */   
/*     */   public Enumeration<JarEntry> entries() {
/* 274 */     return new JarEntryIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<JarEntry> stream() {
/* 279 */     return StreamSupport.stream(Spliterators.spliterator(new JarEntryIterator(), 
/* 280 */           size(), 1297), false);
/*     */   }
/*     */   
/*     */   private class JarFileEntry
/*     */     extends JarEntry
/*     */   {
/*     */     JarFileEntry(ZipEntry param1ZipEntry) {
/* 287 */       super(param1ZipEntry);
/*     */     }
/*     */     public Attributes getAttributes() throws IOException {
/* 290 */       Manifest manifest = JarFile.this.getManifest();
/* 291 */       if (manifest != null) {
/* 292 */         return manifest.getAttributes(getName());
/*     */       }
/* 294 */       return null;
/*     */     }
/*     */     
/*     */     public Certificate[] getCertificates() {
/*     */       try {
/* 299 */         JarFile.this.maybeInstantiateVerifier();
/* 300 */       } catch (IOException iOException) {
/* 301 */         throw new RuntimeException(iOException);
/*     */       } 
/* 303 */       if (this.certs == null && JarFile.this.jv != null) {
/* 304 */         this.certs = JarFile.this.jv.getCerts(JarFile.this, this);
/*     */       }
/* 306 */       return (this.certs == null) ? null : (Certificate[])this.certs.clone();
/*     */     }
/*     */     public CodeSigner[] getCodeSigners() {
/*     */       try {
/* 310 */         JarFile.this.maybeInstantiateVerifier();
/* 311 */       } catch (IOException iOException) {
/* 312 */         throw new RuntimeException(iOException);
/*     */       } 
/* 314 */       if (this.signers == null && JarFile.this.jv != null) {
/* 315 */         this.signers = JarFile.this.jv.getCodeSigners(JarFile.this, this);
/*     */       }
/* 317 */       return (this.signers == null) ? null : (CodeSigner[])this.signers.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeInstantiateVerifier() throws IOException {
/* 328 */     if (this.jv != null) {
/*     */       return;
/*     */     }
/*     */     
/* 332 */     if (this.verify) {
/* 333 */       String[] arrayOfString = getMetaInfEntryNames();
/* 334 */       if (arrayOfString != null) {
/* 335 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 336 */           String str = arrayOfString[b].toUpperCase(Locale.ENGLISH);
/* 337 */           if (str.endsWith(".DSA") || str
/* 338 */             .endsWith(".RSA") || str
/* 339 */             .endsWith(".EC") || str
/* 340 */             .endsWith(".SF")) {
/*     */ 
/*     */ 
/*     */             
/* 344 */             getManifest();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 351 */       this.verify = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeVerifier() {
/* 361 */     ManifestEntryVerifier manifestEntryVerifier = null;
/*     */ 
/*     */     
/*     */     try {
/* 365 */       String[] arrayOfString = getMetaInfEntryNames();
/* 366 */       if (arrayOfString != null) {
/* 367 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 368 */           String str = arrayOfString[b].toUpperCase(Locale.ENGLISH);
/* 369 */           if ("META-INF/MANIFEST.MF".equals(str) || 
/* 370 */             SignatureFileVerifier.isBlockOrSF(str)) {
/* 371 */             JarEntry jarEntry = getJarEntry(arrayOfString[b]);
/* 372 */             if (jarEntry == null) {
/* 373 */               throw new JarException("corrupted jar file");
/*     */             }
/* 375 */             if (manifestEntryVerifier == null)
/*     */             {
/* 377 */               manifestEntryVerifier = new ManifestEntryVerifier(getManifestFromReference());
/*     */             }
/* 379 */             byte[] arrayOfByte = getBytes(jarEntry);
/* 380 */             if (arrayOfByte != null && arrayOfByte.length > 0) {
/* 381 */               this.jv.beginEntry(jarEntry, manifestEntryVerifier);
/* 382 */               this.jv.update(arrayOfByte.length, arrayOfByte, 0, arrayOfByte.length, manifestEntryVerifier);
/* 383 */               this.jv.update(-1, null, 0, 0, manifestEntryVerifier);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 388 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 391 */       this.jv = null;
/* 392 */       this.verify = false;
/* 393 */       if (JarVerifier.debug != null) {
/* 394 */         JarVerifier.debug.println("jarfile parsing error!");
/* 395 */         iOException.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     if (this.jv != null) {
/*     */       
/* 404 */       this.jv.doneWithMeta();
/* 405 */       if (JarVerifier.debug != null) {
/* 406 */         JarVerifier.debug.println("done with meta!");
/*     */       }
/*     */       
/* 409 */       if (this.jv.nothingToVerify()) {
/* 410 */         if (JarVerifier.debug != null) {
/* 411 */           JarVerifier.debug.println("nothing to verify!");
/*     */         }
/* 413 */         this.jv = null;
/* 414 */         this.verify = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getBytes(ZipEntry paramZipEntry) throws IOException {
/* 424 */     try (InputStream null = super.getInputStream(paramZipEntry)) {
/* 425 */       int i = (int)paramZipEntry.getSize();
/* 426 */       byte[] arrayOfByte = IOUtils.readAllBytes(inputStream);
/* 427 */       if (i != -1 && arrayOfByte.length != i) {
/* 428 */         throw new EOFException("Expected:" + i + ", read:" + arrayOfByte.length);
/*     */       }
/* 430 */       return arrayOfByte;
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
/*     */   public synchronized InputStream getInputStream(ZipEntry paramZipEntry) throws IOException {
/* 450 */     maybeInstantiateVerifier();
/* 451 */     if (this.jv == null) {
/* 452 */       return super.getInputStream(paramZipEntry);
/*     */     }
/* 454 */     if (!this.jvInitialized) {
/* 455 */       initializeVerifier();
/* 456 */       this.jvInitialized = true;
/*     */ 
/*     */ 
/*     */       
/* 460 */       if (this.jv == null) {
/* 461 */         return super.getInputStream(paramZipEntry);
/*     */       }
/*     */     } 
/*     */     
/* 465 */     return new JarVerifier.VerifierStream(
/* 466 */         getManifestFromReference(), (paramZipEntry instanceof JarFileEntry) ? (JarEntry)paramZipEntry : 
/*     */         
/* 468 */         getJarEntry(paramZipEntry.getName()), super
/* 469 */         .getInputStream(paramZipEntry), this.jv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 474 */   private static final char[] CLASSPATH_CHARS = new char[] { 'c', 'l', 'a', 's', 's', '-', 'p', 'a', 't', 'h' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 481 */   private static final int[] CLASSPATH_LASTOCC = new int[128];
/* 482 */   private static final int[] CLASSPATH_OPTOSFT = new int[10]; private static String javaHome; static {
/* 483 */     CLASSPATH_LASTOCC[99] = 1;
/* 484 */     CLASSPATH_LASTOCC[108] = 2;
/* 485 */     CLASSPATH_LASTOCC[115] = 5;
/* 486 */     CLASSPATH_LASTOCC[45] = 6;
/* 487 */     CLASSPATH_LASTOCC[112] = 7;
/* 488 */     CLASSPATH_LASTOCC[97] = 8;
/* 489 */     CLASSPATH_LASTOCC[116] = 9;
/* 490 */     CLASSPATH_LASTOCC[104] = 10;
/* 491 */     for (byte b = 0; b < 9; b++)
/* 492 */       CLASSPATH_OPTOSFT[b] = 10; 
/* 493 */     CLASSPATH_OPTOSFT[9] = 1;
/*     */   }
/*     */   private static volatile String[] jarNames;
/*     */   private JarEntry getManEntry() {
/* 497 */     if (this.manEntry == null) {
/*     */       
/* 499 */       this.manEntry = getJarEntry("META-INF/MANIFEST.MF");
/* 500 */       if (this.manEntry == null) {
/*     */ 
/*     */         
/* 503 */         String[] arrayOfString = getMetaInfEntryNames();
/* 504 */         if (arrayOfString != null) {
/* 505 */           for (byte b = 0; b < arrayOfString.length; b++) {
/* 506 */             if ("META-INF/MANIFEST.MF".equals(arrayOfString[b]
/* 507 */                 .toUpperCase(Locale.ENGLISH))) {
/* 508 */               this.manEntry = getJarEntry(arrayOfString[b]);
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 515 */     return this.manEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasClassPathAttribute() throws IOException {
/* 523 */     checkForSpecialAttributes();
/* 524 */     return this.hasClassPathAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean match(char[] paramArrayOfchar, byte[] paramArrayOfbyte, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 533 */     int i = paramArrayOfchar.length;
/* 534 */     int j = paramArrayOfbyte.length - i;
/* 535 */     int k = 0;
/*     */     
/* 537 */     label19: while (k <= j) {
/* 538 */       for (int m = i - 1; m >= 0; m--) {
/* 539 */         char c = (char)paramArrayOfbyte[k + m];
/* 540 */         c = ((c - 65 | 90 - c) >= 0) ? (char)(c + 32) : c;
/* 541 */         if (c != paramArrayOfchar[m]) {
/* 542 */           k += Math.max(m + 1 - paramArrayOfint1[c & 0x7F], paramArrayOfint2[m]);
/*     */           continue label19;
/*     */         } 
/*     */       } 
/* 546 */       return true;
/*     */     } 
/* 548 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForSpecialAttributes() throws IOException {
/* 556 */     if (this.hasCheckedSpecialAttributes)
/* 557 */       return;  if (!isKnownNotToHaveSpecialAttributes()) {
/* 558 */       JarEntry jarEntry = getManEntry();
/* 559 */       if (jarEntry != null) {
/* 560 */         byte[] arrayOfByte = getBytes(jarEntry);
/* 561 */         if (match(CLASSPATH_CHARS, arrayOfByte, CLASSPATH_LASTOCC, CLASSPATH_OPTOSFT))
/* 562 */           this.hasClassPathAttribute = true; 
/*     */       } 
/*     */     } 
/* 565 */     this.hasCheckedSpecialAttributes = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isKnownNotToHaveSpecialAttributes() {
/* 575 */     if (javaHome == null) {
/* 576 */       javaHome = AccessController.<String>doPrivileged(new GetPropertyAction("java.home"));
/*     */     }
/*     */     
/* 579 */     if (jarNames == null) {
/* 580 */       String[] arrayOfString = new String[11];
/* 581 */       String str = File.separator;
/* 582 */       byte b = 0;
/* 583 */       arrayOfString[b++] = str + "rt.jar";
/* 584 */       arrayOfString[b++] = str + "jsse.jar";
/* 585 */       arrayOfString[b++] = str + "jce.jar";
/* 586 */       arrayOfString[b++] = str + "charsets.jar";
/* 587 */       arrayOfString[b++] = str + "dnsns.jar";
/* 588 */       arrayOfString[b++] = str + "zipfs.jar";
/* 589 */       arrayOfString[b++] = str + "localedata.jar";
/* 590 */       arrayOfString[b++] = str = "cldrdata.jar";
/* 591 */       arrayOfString[b++] = str + "sunjce_provider.jar";
/* 592 */       arrayOfString[b++] = str + "sunpkcs11.jar";
/* 593 */       arrayOfString[b++] = str + "sunec.jar";
/* 594 */       jarNames = arrayOfString;
/*     */     } 
/*     */     
/* 597 */     String str1 = getName();
/* 598 */     String str2 = javaHome;
/* 599 */     if (str1.startsWith(str2)) {
/* 600 */       String[] arrayOfString = jarNames;
/* 601 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 602 */         if (str1.endsWith(arrayOfString[b])) {
/* 603 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 607 */     return false;
/*     */   }
/*     */   
/*     */   synchronized void ensureInitialization() {
/*     */     try {
/* 612 */       maybeInstantiateVerifier();
/* 613 */     } catch (IOException iOException) {
/* 614 */       throw new RuntimeException(iOException);
/*     */     } 
/* 616 */     if (this.jv != null && !this.jvInitialized) {
/* 617 */       initializeVerifier();
/* 618 */       this.jvInitialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   JarEntry newEntry(ZipEntry paramZipEntry) {
/* 623 */     return new JarFileEntry(paramZipEntry);
/*     */   }
/*     */   
/*     */   Enumeration<String> entryNames(CodeSource[] paramArrayOfCodeSource) {
/* 627 */     ensureInitialization();
/* 628 */     if (this.jv != null) {
/* 629 */       return this.jv.entryNames(this, paramArrayOfCodeSource);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 636 */     boolean bool = false;
/* 637 */     for (byte b = 0; b < paramArrayOfCodeSource.length; b++) {
/* 638 */       if (paramArrayOfCodeSource[b].getCodeSigners() == null) {
/* 639 */         bool = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 643 */     if (bool) {
/* 644 */       return unsignedEntryNames();
/*     */     }
/* 646 */     return new Enumeration<String>()
/*     */       {
/*     */         public boolean hasMoreElements() {
/* 649 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 653 */           throw new NoSuchElementException();
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
/*     */   Enumeration<JarEntry> entries2() {
/* 665 */     ensureInitialization();
/* 666 */     if (this.jv != null) {
/* 667 */       return this.jv.entries2(this, super.entries());
/*     */     }
/*     */ 
/*     */     
/* 671 */     final Enumeration<? extends ZipEntry> enum_ = super.entries();
/* 672 */     return new Enumeration<JarEntry>()
/*     */       {
/*     */         ZipEntry entry;
/*     */         
/*     */         public boolean hasMoreElements() {
/* 677 */           if (this.entry != null) {
/* 678 */             return true;
/*     */           }
/* 680 */           while (enum_.hasMoreElements()) {
/* 681 */             ZipEntry zipEntry = enum_.nextElement();
/* 682 */             if (JarVerifier.isSigningRelated(zipEntry.getName())) {
/*     */               continue;
/*     */             }
/* 685 */             this.entry = zipEntry;
/* 686 */             return true;
/*     */           } 
/* 688 */           return false;
/*     */         }
/*     */         
/*     */         public JarFile.JarFileEntry nextElement() {
/* 692 */           if (hasMoreElements()) {
/* 693 */             ZipEntry zipEntry = this.entry;
/* 694 */             this.entry = null;
/* 695 */             return new JarFile.JarFileEntry(zipEntry);
/*     */           } 
/* 697 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   CodeSource[] getCodeSources(URL paramURL) {
/* 703 */     ensureInitialization();
/* 704 */     if (this.jv != null) {
/* 705 */       return this.jv.getCodeSources(this, paramURL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 712 */     Enumeration<String> enumeration = unsignedEntryNames();
/* 713 */     if (enumeration.hasMoreElements()) {
/* 714 */       return new CodeSource[] { JarVerifier.getUnsignedCS(paramURL) };
/*     */     }
/* 716 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Enumeration<String> unsignedEntryNames() {
/* 721 */     final Enumeration<JarEntry> entries = entries();
/* 722 */     return new Enumeration<String>()
/*     */       {
/*     */         String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasMoreElements() {
/* 731 */           if (this.name != null) {
/* 732 */             return true;
/*     */           }
/* 734 */           while (entries.hasMoreElements()) {
/*     */             
/* 736 */             ZipEntry zipEntry = entries.nextElement();
/* 737 */             String str = zipEntry.getName();
/* 738 */             if (zipEntry.isDirectory() || JarVerifier.isSigningRelated(str)) {
/*     */               continue;
/*     */             }
/* 741 */             this.name = str;
/* 742 */             return true;
/*     */           } 
/* 744 */           return false;
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 748 */           if (hasMoreElements()) {
/* 749 */             String str = this.name;
/* 750 */             this.name = null;
/* 751 */             return str;
/*     */           } 
/* 753 */           throw new NoSuchElementException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   CodeSource getCodeSource(URL paramURL, String paramString) {
/* 759 */     ensureInitialization();
/* 760 */     if (this.jv != null) {
/* 761 */       if (this.jv.eagerValidation) {
/* 762 */         CodeSource codeSource = null;
/* 763 */         JarEntry jarEntry = getJarEntry(paramString);
/* 764 */         if (jarEntry != null) {
/* 765 */           codeSource = this.jv.getCodeSource(paramURL, this, jarEntry);
/*     */         } else {
/* 767 */           codeSource = this.jv.getCodeSource(paramURL, paramString);
/*     */         } 
/* 769 */         return codeSource;
/*     */       } 
/* 771 */       return this.jv.getCodeSource(paramURL, paramString);
/*     */     } 
/*     */ 
/*     */     
/* 775 */     return JarVerifier.getUnsignedCS(paramURL);
/*     */   }
/*     */   
/*     */   void setEagerValidation(boolean paramBoolean) {
/*     */     try {
/* 780 */       maybeInstantiateVerifier();
/* 781 */     } catch (IOException iOException) {
/* 782 */       throw new RuntimeException(iOException);
/*     */     } 
/* 784 */     if (this.jv != null) {
/* 785 */       this.jv.setEagerValidation(paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   List<Object> getManifestDigests() {
/* 790 */     ensureInitialization();
/* 791 */     if (this.jv != null) {
/* 792 */       return this.jv.getManifestDigests();
/*     */     }
/* 794 */     return new ArrayList();
/*     */   }
/*     */   
/*     */   private native String[] getMetaInfEntryNames();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/JarFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */