/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarException;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.security.jca.Providers;
/*     */ import sun.security.pkcs.PKCS7;
/*     */ import sun.security.pkcs.SignerInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureFileVerifier
/*     */ {
/*  60 */   private static final Debug debug = Debug.getInstance("jar");
/*     */   
/*  62 */   private static final DisabledAlgorithmConstraints JAR_DISABLED_CHECK = new DisabledAlgorithmConstraints("jdk.jar.disabledAlgorithms");
/*     */ 
/*     */   
/*     */   private ArrayList<CodeSigner[]> signerCache;
/*     */ 
/*     */   
/*  68 */   private static final String ATTR_DIGEST = "-DIGEST-Manifest-Main-Attributes"
/*     */     
/*  70 */     .toUpperCase(Locale.ENGLISH);
/*     */ 
/*     */ 
/*     */   
/*     */   private PKCS7 block;
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] sfBytes;
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */   
/*     */   private ManifestDigester md;
/*     */ 
/*     */   
/*     */   private HashMap<String, MessageDigest> createdDigests;
/*     */ 
/*     */   
/*     */   private boolean workaround = false;
/*     */ 
/*     */   
/*  93 */   private CertificateFactory certificateFactory = null;
/*     */ 
/*     */   
/*  96 */   private Map<String, Boolean> permittedAlgs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private Timestamp timestamp = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureFileVerifier(ArrayList<CodeSigner[]> paramArrayList, ManifestDigester paramManifestDigester, String paramString, byte[] paramArrayOfbyte) throws IOException, CertificateException {
/* 118 */     Object object = null;
/*     */     try {
/* 120 */       object = Providers.startJarVerification();
/* 121 */       this.block = new PKCS7(paramArrayOfbyte);
/* 122 */       this.sfBytes = this.block.getContentInfo().getData();
/* 123 */       this.certificateFactory = CertificateFactory.getInstance("X509");
/*     */     } finally {
/* 125 */       Providers.stopJarVerification(object);
/*     */     } 
/* 127 */     this
/* 128 */       .name = paramString.substring(0, paramString.lastIndexOf('.')).toUpperCase(Locale.ENGLISH);
/* 129 */     this.md = paramManifestDigester;
/* 130 */     this.signerCache = paramArrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needSignatureFileBytes() {
/* 139 */     return (this.sfBytes == null);
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
/*     */   public boolean needSignatureFile(String paramString) {
/* 151 */     return this.name.equalsIgnoreCase(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureFile(byte[] paramArrayOfbyte) {
/* 160 */     this.sfBytes = paramArrayOfbyte;
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
/*     */   public static boolean isBlockOrSF(String paramString) {
/* 174 */     return (paramString.endsWith(".SF") || paramString
/* 175 */       .endsWith(".DSA") || paramString
/* 176 */       .endsWith(".RSA") || paramString
/* 177 */       .endsWith(".EC"));
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
/*     */   public static boolean isSigningRelated(String paramString) {
/* 191 */     paramString = paramString.toUpperCase(Locale.ENGLISH);
/* 192 */     if (!paramString.startsWith("META-INF/")) {
/* 193 */       return false;
/*     */     }
/* 195 */     paramString = paramString.substring(9);
/* 196 */     if (paramString.indexOf('/') != -1) {
/* 197 */       return false;
/*     */     }
/* 199 */     if (isBlockOrSF(paramString) || paramString.equals("MANIFEST.MF"))
/* 200 */       return true; 
/* 201 */     if (paramString.startsWith("SIG-")) {
/*     */ 
/*     */ 
/*     */       
/* 205 */       int i = paramString.lastIndexOf('.');
/* 206 */       if (i != -1) {
/* 207 */         String str = paramString.substring(i + 1);
/*     */         
/* 209 */         if (str.length() > 3 || str.length() < 1) {
/* 210 */           return false;
/*     */         }
/*     */         
/* 213 */         for (byte b = 0; b < str.length(); b++) {
/* 214 */           char c = str.charAt(b);
/*     */           
/* 216 */           if ((c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
/* 217 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/* 221 */       return true;
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MessageDigest getDigest(String paramString) throws SignatureException {
/* 230 */     if (this.createdDigests == null) {
/* 231 */       this.createdDigests = new HashMap<>();
/*     */     }
/* 233 */     MessageDigest messageDigest = this.createdDigests.get(paramString);
/*     */     
/* 235 */     if (messageDigest == null) {
/*     */       try {
/* 237 */         messageDigest = MessageDigest.getInstance(paramString);
/* 238 */         this.createdDigests.put(paramString, messageDigest);
/* 239 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
/*     */     }
/*     */ 
/*     */     
/* 243 */     return messageDigest;
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
/*     */   public void process(Hashtable<String, CodeSigner[]> paramHashtable, List<Object> paramList) throws IOException, SignatureException, NoSuchAlgorithmException, JarException, CertificateException {
/* 260 */     Object object = null;
/*     */     try {
/* 262 */       object = Providers.startJarVerification();
/* 263 */       processImpl(paramHashtable, paramList);
/*     */     } finally {
/* 265 */       Providers.stopJarVerification(object);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processImpl(Hashtable<String, CodeSigner[]> paramHashtable, List<Object> paramList) throws IOException, SignatureException, NoSuchAlgorithmException, JarException, CertificateException {
/* 275 */     Manifest manifest = new Manifest();
/* 276 */     manifest.read(new ByteArrayInputStream(this.sfBytes));
/*     */ 
/*     */     
/* 279 */     String str = manifest.getMainAttributes().getValue(Attributes.Name.SIGNATURE_VERSION);
/*     */     
/* 281 */     if (str == null || !str.equalsIgnoreCase("1.0")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 287 */     SignerInfo[] arrayOfSignerInfo = this.block.verify(this.sfBytes);
/*     */     
/* 289 */     if (arrayOfSignerInfo == null) {
/* 290 */       throw new SecurityException("cannot verify signature block file " + this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 295 */     CodeSigner[] arrayOfCodeSigner = getSigners(arrayOfSignerInfo, this.block);
/*     */ 
/*     */     
/* 298 */     if (arrayOfCodeSigner == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     for (CodeSigner codeSigner : arrayOfCodeSigner) {
/* 306 */       if (debug != null) {
/* 307 */         debug.println("Gathering timestamp for:  " + codeSigner.toString());
/*     */       }
/* 309 */       if (codeSigner.getTimestamp() == null) {
/* 310 */         this.timestamp = null; break;
/*     */       } 
/* 312 */       if (this.timestamp == null) {
/* 313 */         this.timestamp = codeSigner.getTimestamp();
/*     */       }
/* 315 */       else if (this.timestamp.getTimestamp().before(codeSigner
/* 316 */           .getTimestamp().getTimestamp())) {
/* 317 */         this.timestamp = codeSigner.getTimestamp();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 323 */     Iterator<Map.Entry> iterator = manifest.getEntries().entrySet().iterator();
/*     */ 
/*     */     
/* 326 */     boolean bool = verifyManifestHash(manifest, this.md, paramList);
/*     */ 
/*     */     
/* 329 */     if (!bool && !verifyManifestMainAttrs(manifest, this.md)) {
/* 330 */       throw new SecurityException("Invalid signature file digest for Manifest main attributes");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 335 */     while (iterator.hasNext()) {
/*     */       
/* 337 */       Map.Entry entry = iterator.next();
/* 338 */       String str1 = (String)entry.getKey();
/*     */       
/* 340 */       if (bool || 
/* 341 */         verifySection((Attributes)entry.getValue(), str1, this.md)) {
/*     */         
/* 343 */         if (str1.startsWith("./")) {
/* 344 */           str1 = str1.substring(2);
/*     */         }
/* 346 */         if (str1.startsWith("/")) {
/* 347 */           str1 = str1.substring(1);
/*     */         }
/* 349 */         updateSigners(arrayOfCodeSigner, paramHashtable, str1);
/*     */         
/* 351 */         if (debug != null)
/* 352 */           debug.println("processSignature signed name = " + str1); 
/*     */         continue;
/*     */       } 
/* 355 */       if (debug != null) {
/* 356 */         debug.println("processSignature unsigned name = " + str1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 361 */     updateSigners(arrayOfCodeSigner, paramHashtable, "META-INF/MANIFEST.MF");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean permittedCheck(String paramString1, String paramString2) {
/* 371 */     Boolean bool = this.permittedAlgs.get(paramString2);
/* 372 */     if (bool == null) {
/*     */       try {
/* 374 */         JAR_DISABLED_CHECK.permits(paramString2, new ConstraintsParameters(this.timestamp));
/*     */       }
/* 376 */       catch (GeneralSecurityException generalSecurityException) {
/* 377 */         this.permittedAlgs.put(paramString2, Boolean.FALSE);
/* 378 */         this.permittedAlgs.put(paramString1.toUpperCase(), Boolean.FALSE);
/* 379 */         if (debug != null) {
/* 380 */           if (generalSecurityException.getMessage() != null) {
/* 381 */             debug.println(paramString1 + ":  " + generalSecurityException.getMessage());
/*     */           } else {
/* 383 */             debug.println("Debug info only. " + paramString1 + ":  " + paramString2 + " was disabled, no exception msg given.");
/*     */ 
/*     */             
/* 386 */             generalSecurityException.printStackTrace();
/*     */           } 
/*     */         }
/* 389 */         return false;
/*     */       } 
/*     */       
/* 392 */       this.permittedAlgs.put(paramString2, Boolean.TRUE);
/* 393 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 397 */     return bool.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getWeakAlgorithms(String paramString) {
/* 406 */     String str = "";
/*     */     try {
/* 408 */       for (String str1 : this.permittedAlgs.keySet()) {
/* 409 */         if (str1.endsWith(paramString)) {
/* 410 */           str = str + str1.substring(0, str1.length() - paramString.length()) + " ";
/*     */         }
/*     */       } 
/* 413 */     } catch (RuntimeException runtimeException) {
/*     */       
/* 415 */       str = "Unknown Algorithm(s).  Error processing " + paramString + ".  " + runtimeException.getMessage();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 420 */     if (str.length() == 0) {
/* 421 */       return "Unknown Algorithm(s)";
/*     */     }
/*     */     
/* 424 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyManifestHash(Manifest paramManifest, ManifestDigester paramManifestDigester, List<Object> paramList) throws IOException, SignatureException {
/* 435 */     Attributes attributes = paramManifest.getMainAttributes();
/* 436 */     boolean bool1 = false;
/*     */     
/* 438 */     boolean bool2 = true;
/*     */     
/* 440 */     boolean bool3 = false;
/*     */ 
/*     */     
/* 443 */     for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
/*     */       
/* 445 */       String str = entry.getKey().toString();
/*     */       
/* 447 */       if (str.toUpperCase(Locale.ENGLISH).endsWith("-DIGEST-MANIFEST")) {
/*     */         
/* 449 */         String str1 = str.substring(0, str.length() - 16);
/* 450 */         bool3 = true;
/*     */ 
/*     */         
/* 453 */         if (!permittedCheck(str, str1)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 459 */         bool2 = false;
/*     */         
/* 461 */         paramList.add(str);
/* 462 */         paramList.add(entry.getValue());
/* 463 */         MessageDigest messageDigest = getDigest(str1);
/* 464 */         if (messageDigest != null) {
/* 465 */           byte[] arrayOfByte1 = paramManifestDigester.manifestDigest(messageDigest);
/*     */           
/* 467 */           byte[] arrayOfByte2 = Base64.getMimeDecoder().decode((String)entry.getValue());
/*     */           
/* 469 */           if (debug != null) {
/* 470 */             debug.println("Signature File: Manifest digest " + str1);
/*     */             
/* 472 */             debug.println("  sigfile  " + toHex(arrayOfByte2));
/* 473 */             debug.println("  computed " + toHex(arrayOfByte1));
/* 474 */             debug.println();
/*     */           } 
/*     */           
/* 477 */           if (MessageDigest.isEqual(arrayOfByte1, arrayOfByte2)) {
/* 478 */             bool1 = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 486 */     if (debug != null) {
/* 487 */       debug.println("PermittedAlgs mapping: ");
/* 488 */       for (String str : this.permittedAlgs.keySet()) {
/* 489 */         debug.println(str + " : " + ((Boolean)this.permittedAlgs
/* 490 */             .get(str)).toString());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 495 */     if (bool3 && bool2) {
/* 496 */       throw new SignatureException("Manifest hash check failed (DIGEST-MANIFEST). Disabled algorithm(s) used: " + 
/*     */           
/* 498 */           getWeakAlgorithms("-DIGEST-MANIFEST"));
/*     */     }
/* 500 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyManifestMainAttrs(Manifest paramManifest, ManifestDigester paramManifestDigester) throws IOException, SignatureException {
/* 506 */     Attributes attributes = paramManifest.getMainAttributes();
/* 507 */     boolean bool1 = true;
/*     */     
/* 509 */     boolean bool2 = true;
/*     */     
/* 511 */     boolean bool3 = false;
/*     */ 
/*     */ 
/*     */     
/* 515 */     for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
/* 516 */       String str = entry.getKey().toString();
/*     */       
/* 518 */       if (str.toUpperCase(Locale.ENGLISH).endsWith(ATTR_DIGEST)) {
/*     */         
/* 520 */         String str1 = str.substring(0, str.length() - ATTR_DIGEST.length());
/* 521 */         bool3 = true;
/*     */ 
/*     */         
/* 524 */         if (!permittedCheck(str, str1)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 530 */         bool2 = false;
/*     */         
/* 532 */         MessageDigest messageDigest = getDigest(str1);
/* 533 */         if (messageDigest != null) {
/*     */           
/* 535 */           ManifestDigester.Entry entry1 = paramManifestDigester.get("Manifest-Main-Attributes", false);
/* 536 */           byte[] arrayOfByte1 = entry1.digest(messageDigest);
/*     */           
/* 538 */           byte[] arrayOfByte2 = Base64.getMimeDecoder().decode((String)entry.getValue());
/*     */           
/* 540 */           if (debug != null) {
/* 541 */             debug.println("Signature File: Manifest Main Attributes digest " + messageDigest
/*     */                 
/* 543 */                 .getAlgorithm());
/* 544 */             debug.println("  sigfile  " + toHex(arrayOfByte2));
/* 545 */             debug.println("  computed " + toHex(arrayOfByte1));
/* 546 */             debug.println();
/*     */           } 
/*     */           
/* 549 */           if (MessageDigest.isEqual(arrayOfByte1, arrayOfByte2)) {
/*     */             continue;
/*     */           }
/*     */           
/* 553 */           bool1 = false;
/* 554 */           if (debug != null) {
/* 555 */             debug.println("Verification of Manifest main attributes failed");
/*     */             
/* 557 */             debug.println();
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 565 */     if (debug != null) {
/* 566 */       debug.println("PermittedAlgs mapping: ");
/* 567 */       for (String str : this.permittedAlgs.keySet()) {
/* 568 */         debug.println(str + " : " + ((Boolean)this.permittedAlgs
/* 569 */             .get(str)).toString());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 574 */     if (bool3 && bool2) {
/* 575 */       throw new SignatureException("Manifest Main Attribute check failed (" + ATTR_DIGEST + ").  Disabled algorithm(s) used: " + 
/*     */ 
/*     */           
/* 578 */           getWeakAlgorithms(ATTR_DIGEST));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 584 */     return bool1;
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
/*     */   private boolean verifySection(Attributes paramAttributes, String paramString, ManifestDigester paramManifestDigester) throws IOException, SignatureException {
/* 601 */     boolean bool1 = false;
/* 602 */     ManifestDigester.Entry entry = paramManifestDigester.get(paramString, this.block.isOldStyle());
/*     */     
/* 604 */     boolean bool2 = true;
/*     */     
/* 606 */     boolean bool3 = false;
/*     */     
/* 608 */     if (entry == null) {
/* 609 */       throw new SecurityException("no manifest section for signature file entry " + paramString);
/*     */     }
/*     */ 
/*     */     
/* 613 */     if (paramAttributes != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 618 */       for (Map.Entry<Object, Object> entry1 : paramAttributes.entrySet()) {
/* 619 */         String str = entry1.getKey().toString();
/*     */         
/* 621 */         if (str.toUpperCase(Locale.ENGLISH).endsWith("-DIGEST")) {
/*     */           
/* 623 */           String str1 = str.substring(0, str.length() - 7);
/* 624 */           bool3 = true;
/*     */ 
/*     */           
/* 627 */           if (!permittedCheck(str, str1)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 633 */           bool2 = false;
/*     */           
/* 635 */           MessageDigest messageDigest = getDigest(str1);
/*     */           
/* 637 */           if (messageDigest != null) {
/* 638 */             byte[] arrayOfByte2; boolean bool = false;
/*     */ 
/*     */             
/* 641 */             byte[] arrayOfByte1 = Base64.getMimeDecoder().decode((String)entry1.getValue());
/*     */             
/* 643 */             if (this.workaround) {
/* 644 */               arrayOfByte2 = entry.digestWorkaround(messageDigest);
/*     */             } else {
/* 646 */               arrayOfByte2 = entry.digest(messageDigest);
/*     */             } 
/*     */             
/* 649 */             if (debug != null) {
/* 650 */               debug.println("Signature Block File: " + paramString + " digest=" + messageDigest
/* 651 */                   .getAlgorithm());
/* 652 */               debug.println("  expected " + toHex(arrayOfByte1));
/* 653 */               debug.println("  computed " + toHex(arrayOfByte2));
/* 654 */               debug.println();
/*     */             } 
/*     */             
/* 657 */             if (MessageDigest.isEqual(arrayOfByte2, arrayOfByte1)) {
/* 658 */               bool1 = true;
/* 659 */               bool = true;
/*     */             
/*     */             }
/* 662 */             else if (!this.workaround) {
/* 663 */               arrayOfByte2 = entry.digestWorkaround(messageDigest);
/* 664 */               if (MessageDigest.isEqual(arrayOfByte2, arrayOfByte1)) {
/* 665 */                 if (debug != null) {
/* 666 */                   debug.println("  re-computed " + toHex(arrayOfByte2));
/* 667 */                   debug.println();
/*     */                 } 
/* 669 */                 this.workaround = true;
/* 670 */                 bool1 = true;
/* 671 */                 bool = true;
/*     */               } 
/*     */             } 
/*     */             
/* 675 */             if (!bool) {
/* 676 */               throw new SecurityException("invalid " + messageDigest
/* 677 */                   .getAlgorithm() + " signature file digest for " + paramString);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 685 */     if (debug != null) {
/* 686 */       debug.println("PermittedAlgs mapping: ");
/* 687 */       for (String str : this.permittedAlgs.keySet()) {
/* 688 */         debug.println(str + " : " + ((Boolean)this.permittedAlgs
/* 689 */             .get(str)).toString());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 694 */     if (bool3 && bool2) {
/* 695 */       throw new SignatureException("Manifest Main Attribute check failed (DIGEST).  Disabled algorithm(s) used: " + 
/*     */           
/* 697 */           getWeakAlgorithms("DIGEST"));
/*     */     }
/*     */     
/* 700 */     return bool1;
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
/*     */   private CodeSigner[] getSigners(SignerInfo[] paramArrayOfSignerInfo, PKCS7 paramPKCS7) throws IOException, NoSuchAlgorithmException, SignatureException, CertificateException {
/* 712 */     ArrayList<CodeSigner> arrayList = null;
/*     */     
/* 714 */     for (byte b = 0; b < paramArrayOfSignerInfo.length; b++) {
/*     */       
/* 716 */       SignerInfo signerInfo = paramArrayOfSignerInfo[b];
/* 717 */       ArrayList<X509Certificate> arrayList1 = signerInfo.getCertificateChain(paramPKCS7);
/* 718 */       CertPath certPath = this.certificateFactory.generateCertPath((List)arrayList1);
/* 719 */       if (arrayList == null) {
/* 720 */         arrayList = new ArrayList();
/*     */       }
/*     */ 
/*     */       
/* 724 */       arrayList.add(new CodeSigner(certPath, signerInfo.getTimestamp()));
/*     */       
/* 726 */       if (debug != null) {
/* 727 */         debug.println("Signature Block Certificate: " + arrayList1
/* 728 */             .get(0));
/*     */       }
/*     */     } 
/*     */     
/* 732 */     if (arrayList != null) {
/* 733 */       return arrayList.<CodeSigner>toArray(new CodeSigner[arrayList.size()]);
/*     */     }
/* 735 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 740 */   private static final char[] hexc = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String toHex(byte[] paramArrayOfbyte) {
/* 750 */     StringBuilder stringBuilder = new StringBuilder(paramArrayOfbyte.length * 2);
/*     */     
/* 752 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 753 */       stringBuilder.append(hexc[paramArrayOfbyte[b] >> 4 & 0xF]);
/* 754 */       stringBuilder.append(hexc[paramArrayOfbyte[b] & 0xF]);
/*     */     } 
/* 756 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean contains(CodeSigner[] paramArrayOfCodeSigner, CodeSigner paramCodeSigner) {
/* 762 */     for (byte b = 0; b < paramArrayOfCodeSigner.length; b++) {
/* 763 */       if (paramArrayOfCodeSigner[b].equals(paramCodeSigner))
/* 764 */         return true; 
/*     */     } 
/* 766 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSubSet(CodeSigner[] paramArrayOfCodeSigner1, CodeSigner[] paramArrayOfCodeSigner2) {
/* 773 */     if (paramArrayOfCodeSigner2 == paramArrayOfCodeSigner1) {
/* 774 */       return true;
/*     */     }
/*     */     
/* 777 */     for (byte b = 0; b < paramArrayOfCodeSigner1.length; b++) {
/* 778 */       if (!contains(paramArrayOfCodeSigner2, paramArrayOfCodeSigner1[b]))
/* 779 */         return false; 
/*     */     } 
/* 781 */     return true;
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
/*     */   static boolean matches(CodeSigner[] paramArrayOfCodeSigner1, CodeSigner[] paramArrayOfCodeSigner2, CodeSigner[] paramArrayOfCodeSigner3) {
/* 793 */     if (paramArrayOfCodeSigner2 == null && paramArrayOfCodeSigner1 == paramArrayOfCodeSigner3) {
/* 794 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 799 */     if (paramArrayOfCodeSigner2 != null && !isSubSet(paramArrayOfCodeSigner2, paramArrayOfCodeSigner1)) {
/* 800 */       return false;
/*     */     }
/*     */     
/* 803 */     if (!isSubSet(paramArrayOfCodeSigner3, paramArrayOfCodeSigner1)) {
/* 804 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 810 */     for (byte b = 0; b < paramArrayOfCodeSigner1.length; b++) {
/*     */ 
/*     */       
/* 813 */       boolean bool = ((paramArrayOfCodeSigner2 != null && contains(paramArrayOfCodeSigner2, paramArrayOfCodeSigner1[b])) || contains(paramArrayOfCodeSigner3, paramArrayOfCodeSigner1[b])) ? true : false;
/* 814 */       if (!bool)
/* 815 */         return false; 
/*     */     } 
/* 817 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateSigners(CodeSigner[] paramArrayOfCodeSigner, Hashtable<String, CodeSigner[]> paramHashtable, String paramString) {
/* 823 */     CodeSigner[] arrayOfCodeSigner2, arrayOfCodeSigner1 = paramHashtable.get(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 830 */     for (int i = this.signerCache.size() - 1; i != -1; i--) {
/* 831 */       arrayOfCodeSigner2 = this.signerCache.get(i);
/* 832 */       if (matches(arrayOfCodeSigner2, arrayOfCodeSigner1, paramArrayOfCodeSigner)) {
/* 833 */         paramHashtable.put(paramString, arrayOfCodeSigner2);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 838 */     if (arrayOfCodeSigner1 == null) {
/* 839 */       arrayOfCodeSigner2 = paramArrayOfCodeSigner;
/*     */     } else {
/* 841 */       arrayOfCodeSigner2 = new CodeSigner[arrayOfCodeSigner1.length + paramArrayOfCodeSigner.length];
/*     */       
/* 843 */       System.arraycopy(arrayOfCodeSigner1, 0, arrayOfCodeSigner2, 0, arrayOfCodeSigner1.length);
/*     */       
/* 845 */       System.arraycopy(paramArrayOfCodeSigner, 0, arrayOfCodeSigner2, arrayOfCodeSigner1.length, paramArrayOfCodeSigner.length);
/*     */     } 
/*     */     
/* 848 */     this.signerCache.add(arrayOfCodeSigner2);
/* 849 */     paramHashtable.put(paramString, arrayOfCodeSigner2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/SignatureFileVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */