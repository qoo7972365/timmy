/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.DomainLoadStoreParameter;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.KeyStoreSpi;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import sun.security.util.PolicyUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DomainKeyStore
/*     */   extends KeyStoreSpi
/*     */ {
/*     */   private static final String ENTRY_NAME_SEPARATOR = "entrynameseparator";
/*     */   private static final String KEYSTORE_PROVIDER_NAME = "keystoreprovidername";
/*     */   private static final String KEYSTORE_TYPE = "keystoretype";
/*     */   private static final String KEYSTORE_URI = "keystoreuri";
/*     */   private static final String KEYSTORE_PASSWORD_ENV = "keystorepasswordenv";
/*     */   private static final String REGEX_META = ".$|()[{^?*+\\";
/*     */   private static final String DEFAULT_STREAM_PREFIX = "iostream";
/*     */   
/*     */   public static final class DKS
/*     */     extends DomainKeyStore
/*     */   {
/*     */     String convertAlias(String param1String) {
/*  70 */       return param1String.toLowerCase(Locale.ENGLISH);
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
/*  86 */   private int streamCounter = 1;
/*  87 */   private String entryNameSeparator = " ";
/*  88 */   private String entryNameSeparatorRegEx = " ";
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final String DEFAULT_KEYSTORE_TYPE = KeyStore.getDefaultType();
/*     */ 
/*     */   
/*  95 */   private final Map<String, KeyStore> keystores = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key engineGetKey(String paramString, char[] paramArrayOfchar) throws NoSuchAlgorithmException, UnrecoverableKeyException {
/* 123 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/* 124 */     Key key = null;
/*     */     
/*     */     try {
/* 127 */       String str = simpleEntry.getKey();
/* 128 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 129 */         key = keyStore.getKey(str, paramArrayOfchar);
/* 130 */         if (key != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 134 */     } catch (KeyStoreException keyStoreException) {
/* 135 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 138 */     return key;
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
/*     */   public Certificate[] engineGetCertificateChain(String paramString) {
/* 155 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/* 156 */     Certificate[] arrayOfCertificate = null;
/*     */     
/*     */     try {
/* 159 */       String str = simpleEntry.getKey();
/* 160 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 161 */         arrayOfCertificate = keyStore.getCertificateChain(str);
/* 162 */         if (arrayOfCertificate != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 166 */     } catch (KeyStoreException keyStoreException) {
/* 167 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 170 */     return arrayOfCertificate;
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
/*     */   public Certificate engineGetCertificate(String paramString) {
/* 191 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/* 192 */     Certificate certificate = null;
/*     */     
/*     */     try {
/* 195 */       String str = simpleEntry.getKey();
/* 196 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 197 */         certificate = keyStore.getCertificate(str);
/* 198 */         if (certificate != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 202 */     } catch (KeyStoreException keyStoreException) {
/* 203 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 206 */     return certificate;
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
/*     */   public Date engineGetCreationDate(String paramString) {
/* 220 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/* 221 */     Date date = null;
/*     */     
/*     */     try {
/* 224 */       String str = simpleEntry.getKey();
/* 225 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 226 */         date = keyStore.getCreationDate(str);
/* 227 */         if (date != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 231 */     } catch (KeyStoreException keyStoreException) {
/* 232 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 235 */     return date;
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
/*     */   public void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfchar, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 266 */     AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, KeyStore>> simpleEntry = getKeystoreForWriting(paramString);
/*     */     
/* 268 */     if (simpleEntry == null) {
/* 269 */       throw new KeyStoreException("Error setting key entry for '" + paramString + "'");
/*     */     }
/*     */     
/* 272 */     String str = simpleEntry.getKey();
/* 273 */     Map.Entry entry = simpleEntry.getValue();
/* 274 */     ((KeyStore)entry.getValue()).setKeyEntry(str, paramKey, paramArrayOfchar, paramArrayOfCertificate);
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
/*     */   public void engineSetKeyEntry(String paramString, byte[] paramArrayOfbyte, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 306 */     AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, KeyStore>> simpleEntry = getKeystoreForWriting(paramString);
/*     */     
/* 308 */     if (simpleEntry == null) {
/* 309 */       throw new KeyStoreException("Error setting protected key entry for '" + paramString + "'");
/*     */     }
/*     */     
/* 312 */     String str = simpleEntry.getKey();
/* 313 */     Map.Entry entry = simpleEntry.getValue();
/* 314 */     ((KeyStore)entry.getValue()).setKeyEntry(str, paramArrayOfbyte, paramArrayOfCertificate);
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
/*     */   public void engineSetCertificateEntry(String paramString, Certificate paramCertificate) throws KeyStoreException {
/* 336 */     AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, KeyStore>> simpleEntry = getKeystoreForWriting(paramString);
/*     */     
/* 338 */     if (simpleEntry == null) {
/* 339 */       throw new KeyStoreException("Error setting certificate entry for '" + paramString + "'");
/*     */     }
/*     */     
/* 342 */     String str = simpleEntry.getKey();
/* 343 */     Map.Entry entry = simpleEntry.getValue();
/* 344 */     ((KeyStore)entry.getValue()).setCertificateEntry(str, paramCertificate);
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
/*     */   public void engineDeleteEntry(String paramString) throws KeyStoreException {
/* 358 */     AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, KeyStore>> simpleEntry = getKeystoreForWriting(paramString);
/*     */     
/* 360 */     if (simpleEntry == null) {
/* 361 */       throw new KeyStoreException("Error deleting entry for '" + paramString + "'");
/*     */     }
/*     */     
/* 364 */     String str = simpleEntry.getKey();
/* 365 */     Map.Entry entry = simpleEntry.getValue();
/* 366 */     ((KeyStore)entry.getValue()).deleteEntry(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> engineAliases() {
/* 376 */     final Iterator iterator = this.keystores.entrySet().iterator();
/*     */     
/* 378 */     return new Enumeration<String>() {
/* 379 */         private int index = 0;
/* 380 */         private Map.Entry<String, KeyStore> keystoresEntry = null;
/* 381 */         private String prefix = null;
/* 382 */         private Enumeration<String> aliases = null;
/*     */         
/*     */         public boolean hasMoreElements() {
/*     */           try {
/* 386 */             if (this.aliases == null) {
/* 387 */               if (iterator.hasNext()) {
/* 388 */                 this.keystoresEntry = iterator.next();
/* 389 */                 this
/* 390 */                   .prefix = (String)this.keystoresEntry.getKey() + DomainKeyStore.this.entryNameSeparator;
/* 391 */                 this.aliases = ((KeyStore)this.keystoresEntry.getValue()).aliases();
/*     */               } else {
/* 393 */                 return false;
/*     */               } 
/*     */             }
/* 396 */             if (this.aliases.hasMoreElements()) {
/* 397 */               return true;
/*     */             }
/* 399 */             if (iterator.hasNext()) {
/* 400 */               this.keystoresEntry = iterator.next();
/* 401 */               this
/* 402 */                 .prefix = (String)this.keystoresEntry.getKey() + DomainKeyStore.this.entryNameSeparator;
/* 403 */               this.aliases = ((KeyStore)this.keystoresEntry.getValue()).aliases();
/*     */             } else {
/* 405 */               return false;
/*     */             }
/*     */           
/* 408 */           } catch (KeyStoreException keyStoreException) {
/* 409 */             return false;
/*     */           } 
/*     */           
/* 412 */           return this.aliases.hasMoreElements();
/*     */         }
/*     */         
/*     */         public String nextElement() {
/* 416 */           if (hasMoreElements()) {
/* 417 */             return this.prefix + (String)this.aliases.nextElement();
/*     */           }
/* 419 */           throw new NoSuchElementException();
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
/*     */   public boolean engineContainsAlias(String paramString) {
/* 434 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/*     */     
/*     */     try {
/* 437 */       String str = simpleEntry.getKey();
/* 438 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 439 */         if (keyStore.containsAlias(str)) {
/* 440 */           return true;
/*     */         }
/*     */       } 
/* 443 */     } catch (KeyStoreException keyStoreException) {
/* 444 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int engineSize() {
/* 457 */     int i = 0;
/*     */     try {
/* 459 */       for (KeyStore keyStore : this.keystores.values()) {
/* 460 */         i += keyStore.size();
/*     */       }
/* 462 */     } catch (KeyStoreException keyStoreException) {
/* 463 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 466 */     return i;
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
/*     */   public boolean engineIsKeyEntry(String paramString) {
/* 479 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/*     */     
/*     */     try {
/* 482 */       String str = simpleEntry.getKey();
/* 483 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 484 */         if (keyStore.isKeyEntry(str)) {
/* 485 */           return true;
/*     */         }
/*     */       } 
/* 488 */     } catch (KeyStoreException keyStoreException) {
/* 489 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 492 */     return false;
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
/*     */   public boolean engineIsCertificateEntry(String paramString) {
/* 505 */     AbstractMap.SimpleEntry<String, Collection<KeyStore>> simpleEntry = getKeystoresForReading(paramString);
/*     */     
/*     */     try {
/* 508 */       String str = simpleEntry.getKey();
/* 509 */       for (KeyStore keyStore : simpleEntry.getValue()) {
/* 510 */         if (keyStore.isCertificateEntry(str)) {
/* 511 */           return true;
/*     */         }
/*     */       } 
/* 514 */     } catch (KeyStoreException keyStoreException) {
/* 515 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 518 */     return false;
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
/*     */   private AbstractMap.SimpleEntry<String, Collection<KeyStore>> getKeystoresForReading(String paramString) {
/* 530 */     String[] arrayOfString = paramString.split(this.entryNameSeparatorRegEx, 2);
/* 531 */     if (arrayOfString.length == 2) {
/* 532 */       KeyStore keyStore = this.keystores.get(arrayOfString[0]);
/* 533 */       if (keyStore != null) {
/* 534 */         return new AbstractMap.SimpleEntry<>(arrayOfString[1], 
/* 535 */             Collections.singleton(keyStore));
/*     */       }
/* 537 */     } else if (arrayOfString.length == 1) {
/*     */       
/* 539 */       return new AbstractMap.SimpleEntry<>(paramString, this.keystores.values());
/*     */     } 
/* 541 */     return new AbstractMap.SimpleEntry<>("", 
/* 542 */         Collections.emptyList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, KeyStore>> getKeystoreForWriting(String paramString) {
/* 553 */     String[] arrayOfString = paramString.split(this.entryNameSeparator, 2);
/* 554 */     if (arrayOfString.length == 2) {
/* 555 */       KeyStore keyStore = this.keystores.get(arrayOfString[0]);
/* 556 */       if (keyStore != null) {
/* 557 */         return new AbstractMap.SimpleEntry<>(arrayOfString[1], new AbstractMap.SimpleEntry<>(arrayOfString[0], keyStore));
/*     */       }
/*     */     } 
/*     */     
/* 561 */     return null;
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
/*     */   public String engineGetCertificateAlias(Certificate paramCertificate) {
/*     */     try {
/*     */       KeyStore keyStore;
/* 584 */       String str = null;
/* 585 */       Iterator<KeyStore> iterator = this.keystores.values().iterator(); do { keyStore = iterator.next(); } while (iterator.hasNext() && (
/* 586 */         str = keyStore.getCertificateAlias(paramCertificate)) == null);
/*     */ 
/*     */ 
/*     */       
/* 590 */       return str;
/*     */     }
/* 592 */     catch (KeyStoreException keyStoreException) {
/* 593 */       throw new IllegalStateException(keyStoreException);
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
/*     */   public void engineStore(OutputStream paramOutputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/*     */     try {
/* 616 */       if (this.keystores.size() == 1) {
/* 617 */         ((KeyStore)this.keystores.values().iterator().next()).store(paramOutputStream, paramArrayOfchar);
/*     */         return;
/*     */       } 
/* 620 */     } catch (KeyStoreException keyStoreException) {
/* 621 */       throw new IllegalStateException(keyStoreException);
/*     */     } 
/*     */     
/* 624 */     throw new UnsupportedOperationException("This keystore must be stored using a DomainLoadStoreParameter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineStore(KeyStore.LoadStoreParameter paramLoadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 632 */     if (paramLoadStoreParameter instanceof DomainLoadStoreParameter) {
/* 633 */       DomainLoadStoreParameter domainLoadStoreParameter = (DomainLoadStoreParameter)paramLoadStoreParameter;
/*     */       
/* 635 */       List<KeyStoreBuilderComponents> list = getBuilders(domainLoadStoreParameter
/* 636 */           .getConfiguration(), domainLoadStoreParameter
/* 637 */           .getProtectionParams());
/*     */       
/* 639 */       for (KeyStoreBuilderComponents keyStoreBuilderComponents : list) {
/*     */ 
/*     */         
/*     */         try {
/* 643 */           KeyStore.ProtectionParameter protectionParameter = keyStoreBuilderComponents.protection;
/* 644 */           if (!(protectionParameter instanceof KeyStore.PasswordProtection)) {
/* 645 */             throw new KeyStoreException(new IllegalArgumentException("ProtectionParameter must be a KeyStore.PasswordProtection"));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 651 */           char[] arrayOfChar = ((KeyStore.PasswordProtection)keyStoreBuilderComponents.protection).getPassword();
/*     */ 
/*     */           
/* 654 */           KeyStore keyStore = this.keystores.get(keyStoreBuilderComponents.name);
/*     */           
/* 656 */           try (FileOutputStream null = new FileOutputStream(keyStoreBuilderComponents.file)) {
/*     */ 
/*     */             
/* 659 */             keyStore.store(fileOutputStream, arrayOfChar);
/*     */           } 
/* 661 */         } catch (KeyStoreException keyStoreException) {
/* 662 */           throw new IOException(keyStoreException);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 666 */       throw new UnsupportedOperationException("This keystore must be stored using a DomainLoadStoreParameter");
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
/*     */   public void engineLoad(InputStream paramInputStream, char[] paramArrayOfchar) throws IOException, NoSuchAlgorithmException, CertificateException {
/*     */     try {
/* 694 */       KeyStore keyStore = null;
/*     */       
/*     */       try {
/* 697 */         keyStore = KeyStore.getInstance("JKS");
/* 698 */         keyStore.load(paramInputStream, paramArrayOfchar);
/*     */       }
/* 700 */       catch (Exception exception) {
/*     */         
/* 702 */         if (!"JKS".equalsIgnoreCase(DEFAULT_KEYSTORE_TYPE)) {
/* 703 */           keyStore = KeyStore.getInstance(DEFAULT_KEYSTORE_TYPE);
/* 704 */           keyStore.load(paramInputStream, paramArrayOfchar);
/*     */         } else {
/* 706 */           throw exception;
/*     */         } 
/*     */       } 
/* 709 */       String str = "iostream" + this.streamCounter++;
/* 710 */       this.keystores.put(str, keyStore);
/*     */     }
/* 712 */     catch (Exception exception) {
/* 713 */       throw new UnsupportedOperationException("This keystore must be loaded using a DomainLoadStoreParameter");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineLoad(KeyStore.LoadStoreParameter paramLoadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
/* 723 */     if (paramLoadStoreParameter instanceof DomainLoadStoreParameter) {
/* 724 */       DomainLoadStoreParameter domainLoadStoreParameter = (DomainLoadStoreParameter)paramLoadStoreParameter;
/*     */       
/* 726 */       List<KeyStoreBuilderComponents> list = getBuilders(domainLoadStoreParameter
/* 727 */           .getConfiguration(), domainLoadStoreParameter
/* 728 */           .getProtectionParams());
/*     */       
/* 730 */       for (KeyStoreBuilderComponents keyStoreBuilderComponents : list) {
/*     */ 
/*     */         
/*     */         try {
/* 734 */           if (keyStoreBuilderComponents.file != null) {
/* 735 */             this.keystores.put(keyStoreBuilderComponents.name, 
/* 736 */                 KeyStore.Builder.newInstance(keyStoreBuilderComponents.type, keyStoreBuilderComponents.provider, keyStoreBuilderComponents.file, keyStoreBuilderComponents.protection)
/*     */ 
/*     */                 
/* 739 */                 .getKeyStore()); continue;
/*     */           } 
/* 741 */           this.keystores.put(keyStoreBuilderComponents.name, 
/* 742 */               KeyStore.Builder.newInstance(keyStoreBuilderComponents.type, keyStoreBuilderComponents.provider, keyStoreBuilderComponents.protection)
/*     */               
/* 744 */               .getKeyStore());
/*     */         }
/* 746 */         catch (KeyStoreException keyStoreException) {
/* 747 */           throw new IOException(keyStoreException);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 751 */       throw new UnsupportedOperationException("This keystore must be loaded using a DomainLoadStoreParameter");
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
/*     */   private List<KeyStoreBuilderComponents> getBuilders(URI paramURI, Map<String, KeyStore.ProtectionParameter> paramMap) throws IOException {
/* 765 */     PolicyParser policyParser = new PolicyParser(true);
/* 766 */     Collection<PolicyParser.DomainEntry> collection = null;
/* 767 */     ArrayList<KeyStoreBuilderComponents> arrayList = new ArrayList();
/* 768 */     String str = paramURI.getFragment();
/*     */     
/* 770 */     try (InputStreamReader null = new InputStreamReader(
/*     */           
/* 772 */           PolicyUtil.getInputStream(paramURI.toURL()), "UTF-8")) {
/* 773 */       policyParser.read(inputStreamReader);
/* 774 */       collection = policyParser.getDomainEntries();
/*     */     }
/* 776 */     catch (MalformedURLException malformedURLException) {
/* 777 */       throw new IOException(malformedURLException);
/*     */     }
/* 779 */     catch (ParsingException parsingException) {
/* 780 */       throw new IOException(parsingException);
/*     */     } 
/*     */     
/* 783 */     for (PolicyParser.DomainEntry domainEntry : collection) {
/* 784 */       Map<String, String> map = domainEntry.getProperties();
/*     */       
/* 786 */       if (str != null && 
/* 787 */         !str.equalsIgnoreCase(domainEntry.getName())) {
/*     */         continue;
/*     */       }
/*     */       
/* 791 */       if (map.containsKey("entrynameseparator")) {
/* 792 */         this
/* 793 */           .entryNameSeparator = map.get("entrynameseparator");
/*     */         
/* 795 */         char c = Character.MIN_VALUE;
/* 796 */         StringBuilder stringBuilder = new StringBuilder();
/* 797 */         for (byte b = 0; b < this.entryNameSeparator.length(); b++) {
/* 798 */           c = this.entryNameSeparator.charAt(b);
/* 799 */           if (".$|()[{^?*+\\".indexOf(c) != -1) {
/* 800 */             stringBuilder.append('\\');
/*     */           }
/* 802 */           stringBuilder.append(c);
/*     */         } 
/* 804 */         this.entryNameSeparatorRegEx = stringBuilder.toString();
/*     */       } 
/*     */ 
/*     */       
/* 808 */       Collection<PolicyParser.KeyStoreEntry> collection1 = domainEntry.getEntries();
/* 809 */       for (PolicyParser.KeyStoreEntry keyStoreEntry : collection1) {
/* 810 */         String str1 = keyStoreEntry.getName();
/* 811 */         HashMap<String, String> hashMap = new HashMap<>(map);
/*     */         
/* 813 */         hashMap.putAll(keyStoreEntry.getProperties());
/*     */         
/* 815 */         String str2 = DEFAULT_KEYSTORE_TYPE;
/* 816 */         if (hashMap.containsKey("keystoretype")) {
/* 817 */           str2 = hashMap.get("keystoretype");
/*     */         }
/*     */         
/* 820 */         Provider provider = null;
/* 821 */         if (hashMap.containsKey("keystoreprovidername")) {
/*     */           
/* 823 */           String str3 = hashMap.get("keystoreprovidername");
/*     */           
/* 825 */           provider = Security.getProvider(str3);
/* 826 */           if (provider == null) {
/* 827 */             throw new IOException("Error locating JCE provider: " + str3);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 832 */         File file = null;
/* 833 */         if (hashMap.containsKey("keystoreuri")) {
/* 834 */           String str3 = hashMap.get("keystoreuri");
/*     */           
/*     */           try {
/* 837 */             if (str3.startsWith("file://")) {
/* 838 */               file = new File(new URI(str3));
/*     */             } else {
/* 840 */               file = new File(str3);
/*     */             }
/*     */           
/* 843 */           } catch (URISyntaxException|IllegalArgumentException uRISyntaxException) {
/* 844 */             throw new IOException("Error processing keystore property: keystoreURI=\"" + str3 + "\"", uRISyntaxException);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 850 */         KeyStore.ProtectionParameter protectionParameter = null;
/* 851 */         if (paramMap.containsKey(str1)) {
/* 852 */           protectionParameter = paramMap.get(str1);
/*     */         }
/* 854 */         else if (hashMap.containsKey("keystorepasswordenv")) {
/* 855 */           String str3 = hashMap.get("keystorepasswordenv");
/* 856 */           String str4 = System.getenv(str3);
/* 857 */           if (str4 != null) {
/*     */             
/* 859 */             protectionParameter = new KeyStore.PasswordProtection(str4.toCharArray());
/*     */           } else {
/* 861 */             throw new IOException("Error processing keystore property: keystorePasswordEnv=\"" + str3 + "\"");
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 866 */           protectionParameter = new KeyStore.PasswordProtection(null);
/*     */         } 
/*     */         
/* 869 */         arrayList.add(new KeyStoreBuilderComponents(str1, str2, provider, file, protectionParameter));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 875 */     if (arrayList.isEmpty()) {
/* 876 */       throw new IOException("Error locating domain configuration data for: " + paramURI);
/*     */     }
/*     */ 
/*     */     
/* 880 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract String convertAlias(String paramString);
/*     */   
/*     */   class KeyStoreBuilderComponents
/*     */   {
/*     */     String name;
/*     */     String type;
/*     */     Provider provider;
/*     */     File file;
/*     */     KeyStore.ProtectionParameter protection;
/*     */     
/*     */     KeyStoreBuilderComponents(String param1String1, String param1String2, Provider param1Provider, File param1File, KeyStore.ProtectionParameter param1ProtectionParameter) {
/* 895 */       this.name = param1String1;
/* 896 */       this.type = param1String2;
/* 897 */       this.provider = param1Provider;
/* 898 */       this.file = param1File;
/* 899 */       this.protection = param1ProtectionParameter;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DomainKeyStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */