/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlgorithmId
/*     */   implements Serializable, DerEncoder
/*     */ {
/*     */   private static final long serialVersionUID = 7205873507486557157L;
/*     */   private ObjectIdentifier algid;
/*     */   private AlgorithmParameters algParams;
/*     */   private boolean constructedFromDer = true;
/*     */   protected DerValue params;
/*     */   
/*     */   public AlgorithmId(ObjectIdentifier paramObjectIdentifier) {
/*  94 */     this.algid = paramObjectIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId(ObjectIdentifier paramObjectIdentifier, AlgorithmParameters paramAlgorithmParameters) {
/* 104 */     this.algid = paramObjectIdentifier;
/* 105 */     this.algParams = paramAlgorithmParameters;
/* 106 */     this.constructedFromDer = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private AlgorithmId(ObjectIdentifier paramObjectIdentifier, DerValue paramDerValue) throws IOException {
/* 111 */     this.algid = paramObjectIdentifier;
/* 112 */     this.params = paramDerValue;
/* 113 */     if (this.params != null) {
/* 114 */       decodeParams();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void decodeParams() throws IOException {
/* 119 */     String str = this.algid.toString();
/*     */     try {
/* 121 */       this.algParams = AlgorithmParameters.getInstance(str);
/* 122 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       this.algParams = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 132 */     this.algParams.init(this.params.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 139 */     derEncode(paramDerOutputStream);
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
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 152 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 153 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 155 */     derOutputStream1.putOID(this.algid);
/*     */     
/* 157 */     if (!this.constructedFromDer) {
/* 158 */       if (this.algParams != null) {
/* 159 */         this.params = new DerValue(this.algParams.getEncoded());
/*     */       } else {
/* 161 */         this.params = null;
/*     */       } 
/*     */     }
/* 164 */     if (this.params == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       derOutputStream1.putNull();
/*     */     } else {
/* 193 */       derOutputStream1.putDerValue(this.params);
/*     */     } 
/* 195 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 196 */     paramOutputStream.write(derOutputStream2.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] encode() throws IOException {
/* 204 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 205 */     derEncode(derOutputStream);
/* 206 */     return derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObjectIdentifier getOID() {
/* 217 */     return this.algid;
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
/*     */   public String getName() {
/* 229 */     String str = nameTable.get(this.algid);
/* 230 */     if (str != null) {
/* 231 */       return str;
/*     */     }
/* 233 */     if (this.params != null && this.algid.equals(specifiedWithECDSA_oid)) {
/*     */       
/*     */       try {
/* 236 */         AlgorithmId algorithmId = parse(new DerValue(getEncodedParams()));
/* 237 */         String str1 = algorithmId.getName();
/* 238 */         str = makeSigAlg(str1, "EC");
/* 239 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 243 */     return (str == null) ? this.algid.toString() : str;
/*     */   }
/*     */   
/*     */   public AlgorithmParameters getParameters() {
/* 247 */     return this.algParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncodedParams() throws IOException {
/* 257 */     return (this.params == null) ? null : this.params.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(AlgorithmId paramAlgorithmId) {
/* 266 */     boolean bool = (this.params == null) ? ((paramAlgorithmId.params == null) ? true : false) : this.params.equals(paramAlgorithmId.params);
/* 267 */     return (this.algid.equals(paramAlgorithmId.algid) && bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 278 */     if (this == paramObject) {
/* 279 */       return true;
/*     */     }
/* 281 */     if (paramObject instanceof AlgorithmId)
/* 282 */       return equals((AlgorithmId)paramObject); 
/* 283 */     if (paramObject instanceof ObjectIdentifier) {
/* 284 */       return equals((ObjectIdentifier)paramObject);
/*     */     }
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(ObjectIdentifier paramObjectIdentifier) {
/* 295 */     return this.algid.equals(paramObjectIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 304 */     StringBuilder stringBuilder = new StringBuilder();
/* 305 */     stringBuilder.append(this.algid.toString());
/* 306 */     stringBuilder.append(paramsToString());
/* 307 */     return stringBuilder.toString().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String paramsToString() {
/* 315 */     if (this.params == null)
/* 316 */       return ""; 
/* 317 */     if (this.algParams != null) {
/* 318 */       return this.algParams.toString();
/*     */     }
/* 320 */     return ", params unparsed";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 328 */     return getName() + paramsToString();
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
/*     */   public static AlgorithmId parse(DerValue paramDerValue) throws IOException {
/*     */     DerValue derValue;
/* 345 */     if (paramDerValue.tag != 48) {
/* 346 */       throw new IOException("algid parse error, not a sequence");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     DerInputStream derInputStream = paramDerValue.toDerInputStream();
/*     */     
/* 356 */     ObjectIdentifier objectIdentifier = derInputStream.getOID();
/* 357 */     if (derInputStream.available() == 0) {
/* 358 */       derValue = null;
/*     */     } else {
/* 360 */       derValue = derInputStream.getDerValue();
/* 361 */       if (derValue.tag == 5) {
/* 362 */         if (derValue.length() != 0) {
/* 363 */           throw new IOException("invalid NULL");
/*     */         }
/* 365 */         derValue = null;
/*     */       } 
/* 367 */       if (derInputStream.available() != 0) {
/* 368 */         throw new IOException("Invalid AlgorithmIdentifier: extra data");
/*     */       }
/*     */     } 
/*     */     
/* 372 */     return new AlgorithmId(objectIdentifier, derValue);
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
/*     */   @Deprecated
/*     */   public static AlgorithmId getAlgorithmId(String paramString) throws NoSuchAlgorithmException {
/* 386 */     return get(paramString);
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
/*     */   public static AlgorithmId get(String paramString) throws NoSuchAlgorithmException {
/*     */     ObjectIdentifier objectIdentifier;
/*     */     try {
/* 400 */       objectIdentifier = algOID(paramString);
/* 401 */     } catch (IOException iOException) {
/* 402 */       throw new NoSuchAlgorithmException("Invalid ObjectIdentifier " + paramString);
/*     */     } 
/*     */ 
/*     */     
/* 406 */     if (objectIdentifier == null) {
/* 407 */       throw new NoSuchAlgorithmException("unrecognized algorithm name: " + paramString);
/*     */     }
/*     */     
/* 410 */     return new AlgorithmId(objectIdentifier);
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
/*     */   public static AlgorithmId get(AlgorithmParameters paramAlgorithmParameters) throws NoSuchAlgorithmException {
/*     */     ObjectIdentifier objectIdentifier;
/* 423 */     String str = paramAlgorithmParameters.getAlgorithm();
/*     */     try {
/* 425 */       objectIdentifier = algOID(str);
/* 426 */     } catch (IOException iOException) {
/* 427 */       throw new NoSuchAlgorithmException("Invalid ObjectIdentifier " + str);
/*     */     } 
/*     */     
/* 430 */     if (objectIdentifier == null) {
/* 431 */       throw new NoSuchAlgorithmException("unrecognized algorithm name: " + str);
/*     */     }
/*     */     
/* 434 */     return new AlgorithmId(objectIdentifier, paramAlgorithmParameters);
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
/*     */   private static ObjectIdentifier algOID(String paramString) throws IOException {
/* 451 */     if (paramString.indexOf('.') != -1) {
/* 452 */       if (paramString.startsWith("OID.")) {
/* 453 */         return new ObjectIdentifier(paramString.substring("OID.".length()));
/*     */       }
/* 455 */       return new ObjectIdentifier(paramString);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 460 */     if (paramString.equalsIgnoreCase("MD5")) {
/* 461 */       return MD5_oid;
/*     */     }
/* 463 */     if (paramString.equalsIgnoreCase("MD2")) {
/* 464 */       return MD2_oid;
/*     */     }
/* 466 */     if (paramString.equalsIgnoreCase("SHA") || paramString.equalsIgnoreCase("SHA1") || paramString
/* 467 */       .equalsIgnoreCase("SHA-1")) {
/* 468 */       return SHA_oid;
/*     */     }
/* 470 */     if (paramString.equalsIgnoreCase("SHA-256") || paramString
/* 471 */       .equalsIgnoreCase("SHA256")) {
/* 472 */       return SHA256_oid;
/*     */     }
/* 474 */     if (paramString.equalsIgnoreCase("SHA-384") || paramString
/* 475 */       .equalsIgnoreCase("SHA384")) {
/* 476 */       return SHA384_oid;
/*     */     }
/* 478 */     if (paramString.equalsIgnoreCase("SHA-512") || paramString
/* 479 */       .equalsIgnoreCase("SHA512")) {
/* 480 */       return SHA512_oid;
/*     */     }
/* 482 */     if (paramString.equalsIgnoreCase("SHA-224") || paramString
/* 483 */       .equalsIgnoreCase("SHA224")) {
/* 484 */       return SHA224_oid;
/*     */     }
/*     */ 
/*     */     
/* 488 */     if (paramString.equalsIgnoreCase("RSA")) {
/* 489 */       return RSAEncryption_oid;
/*     */     }
/* 491 */     if (paramString.equalsIgnoreCase("Diffie-Hellman") || paramString
/* 492 */       .equalsIgnoreCase("DH")) {
/* 493 */       return DH_oid;
/*     */     }
/* 495 */     if (paramString.equalsIgnoreCase("DSA")) {
/* 496 */       return DSA_oid;
/*     */     }
/* 498 */     if (paramString.equalsIgnoreCase("EC")) {
/* 499 */       return EC_oid;
/*     */     }
/* 501 */     if (paramString.equalsIgnoreCase("ECDH")) {
/* 502 */       return ECDH_oid;
/*     */     }
/*     */ 
/*     */     
/* 506 */     if (paramString.equalsIgnoreCase("AES")) {
/* 507 */       return AES_oid;
/*     */     }
/*     */ 
/*     */     
/* 511 */     if (paramString.equalsIgnoreCase("MD5withRSA") || paramString
/* 512 */       .equalsIgnoreCase("MD5/RSA")) {
/* 513 */       return md5WithRSAEncryption_oid;
/*     */     }
/* 515 */     if (paramString.equalsIgnoreCase("MD2withRSA") || paramString
/* 516 */       .equalsIgnoreCase("MD2/RSA")) {
/* 517 */       return md2WithRSAEncryption_oid;
/*     */     }
/* 519 */     if (paramString.equalsIgnoreCase("SHAwithDSA") || paramString
/* 520 */       .equalsIgnoreCase("SHA1withDSA") || paramString
/* 521 */       .equalsIgnoreCase("SHA/DSA") || paramString
/* 522 */       .equalsIgnoreCase("SHA1/DSA") || paramString
/* 523 */       .equalsIgnoreCase("DSAWithSHA1") || paramString
/* 524 */       .equalsIgnoreCase("DSS") || paramString
/* 525 */       .equalsIgnoreCase("SHA-1/DSA")) {
/* 526 */       return sha1WithDSA_oid;
/*     */     }
/* 528 */     if (paramString.equalsIgnoreCase("SHA224WithDSA")) {
/* 529 */       return sha224WithDSA_oid;
/*     */     }
/* 531 */     if (paramString.equalsIgnoreCase("SHA256WithDSA")) {
/* 532 */       return sha256WithDSA_oid;
/*     */     }
/* 534 */     if (paramString.equalsIgnoreCase("SHA1WithRSA") || paramString
/* 535 */       .equalsIgnoreCase("SHA1/RSA")) {
/* 536 */       return sha1WithRSAEncryption_oid;
/*     */     }
/* 538 */     if (paramString.equalsIgnoreCase("SHA1withECDSA") || paramString
/* 539 */       .equalsIgnoreCase("ECDSA")) {
/* 540 */       return sha1WithECDSA_oid;
/*     */     }
/* 542 */     if (paramString.equalsIgnoreCase("SHA224withECDSA")) {
/* 543 */       return sha224WithECDSA_oid;
/*     */     }
/* 545 */     if (paramString.equalsIgnoreCase("SHA256withECDSA")) {
/* 546 */       return sha256WithECDSA_oid;
/*     */     }
/* 548 */     if (paramString.equalsIgnoreCase("SHA384withECDSA")) {
/* 549 */       return sha384WithECDSA_oid;
/*     */     }
/* 551 */     if (paramString.equalsIgnoreCase("SHA512withECDSA")) {
/* 552 */       return sha512WithECDSA_oid;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 558 */     if (!initOidTable) {
/* 559 */       Provider[] arrayOfProvider = Security.getProviders();
/* 560 */       for (byte b = 0; b < arrayOfProvider.length; b++) {
/* 561 */         Enumeration<Object> enumeration = arrayOfProvider[b].keys();
/* 562 */         while (enumeration.hasMoreElements()) {
/* 563 */           String str1 = (String)enumeration.nextElement();
/* 564 */           String str2 = str1.toUpperCase(Locale.ENGLISH);
/*     */           int i;
/* 566 */           if (str2.startsWith("ALG.ALIAS") && (
/* 567 */             i = str2.indexOf("OID.", 0)) != -1) {
/* 568 */             i += "OID.".length();
/* 569 */             if (i == str1.length()) {
/*     */               break;
/*     */             }
/*     */             
/* 573 */             if (oidTable == null) {
/* 574 */               oidTable = new HashMap<>();
/*     */             }
/* 576 */             String str3 = str1.substring(i);
/* 577 */             String str4 = arrayOfProvider[b].getProperty(str1);
/* 578 */             if (str4 != null) {
/* 579 */               str4 = str4.toUpperCase(Locale.ENGLISH);
/*     */             }
/* 581 */             if (str4 != null && oidTable
/* 582 */               .get(str4) == null) {
/* 583 */               oidTable.put(str4, new ObjectIdentifier(str3));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 590 */       if (oidTable == null) {
/* 591 */         oidTable = Collections.emptyMap();
/*     */       }
/* 593 */       initOidTable = true;
/*     */     } 
/*     */     
/* 596 */     return oidTable.get(paramString.toUpperCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   private static ObjectIdentifier oid(int... paramVarArgs) {
/* 600 */     return ObjectIdentifier.newInternal(paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean initOidTable = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, ObjectIdentifier> oidTable;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final Map<ObjectIdentifier, String> nameTable;
/*     */ 
/*     */ 
/*     */   
/* 618 */   public static final ObjectIdentifier MD2_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 2, 2 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 625 */   public static final ObjectIdentifier MD5_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 2, 5 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 634 */   public static final ObjectIdentifier SHA_oid = ObjectIdentifier.newInternal(new int[] { 1, 3, 14, 3, 2, 26 });
/*     */ 
/*     */   
/* 637 */   public static final ObjectIdentifier SHA224_oid = ObjectIdentifier.newInternal(new int[] { 2, 16, 840, 1, 101, 3, 4, 2, 4 });
/*     */ 
/*     */   
/* 640 */   public static final ObjectIdentifier SHA256_oid = ObjectIdentifier.newInternal(new int[] { 2, 16, 840, 1, 101, 3, 4, 2, 1 });
/*     */ 
/*     */   
/* 643 */   public static final ObjectIdentifier SHA384_oid = ObjectIdentifier.newInternal(new int[] { 2, 16, 840, 1, 101, 3, 4, 2, 2 });
/*     */ 
/*     */   
/* 646 */   public static final ObjectIdentifier SHA512_oid = ObjectIdentifier.newInternal(new int[] { 2, 16, 840, 1, 101, 3, 4, 2, 3 });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 651 */   private static final int[] DH_data = new int[] { 1, 2, 840, 113549, 1, 3, 1 };
/* 652 */   private static final int[] DH_PKIX_data = new int[] { 1, 2, 840, 10046, 2, 1 };
/* 653 */   private static final int[] DSA_OIW_data = new int[] { 1, 3, 14, 3, 2, 12 };
/* 654 */   private static final int[] DSA_PKIX_data = new int[] { 1, 2, 840, 10040, 4, 1 };
/* 655 */   private static final int[] RSA_data = new int[] { 2, 5, 8, 1, 1 };
/* 656 */   private static final int[] RSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 1 };
/*     */   
/*     */   public static final ObjectIdentifier DH_oid;
/*     */   
/*     */   public static final ObjectIdentifier DH_PKIX_oid;
/*     */   public static final ObjectIdentifier DSA_oid;
/*     */   public static final ObjectIdentifier DSA_OIW_oid;
/* 663 */   public static final ObjectIdentifier EC_oid = oid(new int[] { 1, 2, 840, 10045, 2, 1 });
/* 664 */   public static final ObjectIdentifier ECDH_oid = oid(new int[] { 1, 3, 132, 1, 12 });
/*     */ 
/*     */   
/*     */   public static final ObjectIdentifier RSA_oid;
/*     */ 
/*     */   
/*     */   public static final ObjectIdentifier RSAEncryption_oid;
/*     */   
/* 672 */   public static final ObjectIdentifier AES_oid = oid(new int[] { 2, 16, 840, 1, 101, 3, 4, 1 });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 677 */   private static final int[] md2WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 2 };
/*     */   
/* 679 */   private static final int[] md5WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 4 };
/*     */   
/* 681 */   private static final int[] sha1WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 5 };
/*     */   
/* 683 */   private static final int[] sha1WithRSAEncryption_OIW_data = new int[] { 1, 3, 14, 3, 2, 29 };
/*     */   
/* 685 */   private static final int[] sha224WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 14 };
/*     */   
/* 687 */   private static final int[] sha256WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 11 };
/*     */   
/* 689 */   private static final int[] sha384WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 12 };
/*     */   
/* 691 */   private static final int[] sha512WithRSAEncryption_data = new int[] { 1, 2, 840, 113549, 1, 1, 13 };
/*     */   
/* 693 */   private static final int[] shaWithDSA_OIW_data = new int[] { 1, 3, 14, 3, 2, 13 };
/*     */   
/* 695 */   private static final int[] sha1WithDSA_OIW_data = new int[] { 1, 3, 14, 3, 2, 27 };
/*     */   
/* 697 */   private static final int[] dsaWithSHA1_PKIX_data = new int[] { 1, 2, 840, 10040, 4, 3 };
/*     */   
/*     */   public static final ObjectIdentifier md2WithRSAEncryption_oid;
/*     */   
/*     */   public static final ObjectIdentifier md5WithRSAEncryption_oid;
/*     */   
/*     */   public static final ObjectIdentifier sha1WithRSAEncryption_oid;
/*     */   public static final ObjectIdentifier sha1WithRSAEncryption_OIW_oid;
/*     */   public static final ObjectIdentifier sha224WithRSAEncryption_oid;
/*     */   public static final ObjectIdentifier sha256WithRSAEncryption_oid;
/*     */   public static final ObjectIdentifier sha384WithRSAEncryption_oid;
/*     */   public static final ObjectIdentifier sha512WithRSAEncryption_oid;
/*     */   public static final ObjectIdentifier shaWithDSA_OIW_oid;
/*     */   public static final ObjectIdentifier sha1WithDSA_OIW_oid;
/*     */   public static final ObjectIdentifier sha1WithDSA_oid;
/* 712 */   public static final ObjectIdentifier sha224WithDSA_oid = oid(new int[] { 2, 16, 840, 1, 101, 3, 4, 3, 1 });
/*     */   
/* 714 */   public static final ObjectIdentifier sha256WithDSA_oid = oid(new int[] { 2, 16, 840, 1, 101, 3, 4, 3, 2 });
/*     */ 
/*     */   
/* 717 */   public static final ObjectIdentifier sha1WithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 1 });
/*     */   
/* 719 */   public static final ObjectIdentifier sha224WithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 3, 1 });
/*     */   
/* 721 */   public static final ObjectIdentifier sha256WithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 3, 2 });
/*     */   
/* 723 */   public static final ObjectIdentifier sha384WithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 3, 3 });
/*     */   
/* 725 */   public static final ObjectIdentifier sha512WithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 3, 4 });
/*     */   
/* 727 */   public static final ObjectIdentifier specifiedWithECDSA_oid = oid(new int[] { 1, 2, 840, 10045, 4, 3 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 734 */   public static final ObjectIdentifier pbeWithMD5AndDES_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 5, 3 });
/*     */   
/* 736 */   public static final ObjectIdentifier pbeWithMD5AndRC2_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 5, 6 });
/*     */   
/* 738 */   public static final ObjectIdentifier pbeWithSHA1AndDES_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 5, 10 });
/*     */   
/* 740 */   public static final ObjectIdentifier pbeWithSHA1AndRC2_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 5, 11 });
/*     */   
/* 742 */   public static ObjectIdentifier pbeWithSHA1AndDESede_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 12, 1, 3 });
/*     */   
/* 744 */   public static ObjectIdentifier pbeWithSHA1AndRC2_40_oid = ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 12, 1, 6 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 760 */     DH_oid = ObjectIdentifier.newInternal(DH_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 767 */     DH_PKIX_oid = ObjectIdentifier.newInternal(DH_PKIX_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 777 */     DSA_OIW_oid = ObjectIdentifier.newInternal(DSA_OIW_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 786 */     DSA_oid = ObjectIdentifier.newInternal(DSA_PKIX_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 794 */     RSA_oid = ObjectIdentifier.newInternal(RSA_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 801 */     RSAEncryption_oid = ObjectIdentifier.newInternal(RSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 810 */     md2WithRSAEncryption_oid = ObjectIdentifier.newInternal(md2WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 818 */     md5WithRSAEncryption_oid = ObjectIdentifier.newInternal(md5WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 826 */     sha1WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha1WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 834 */     sha1WithRSAEncryption_OIW_oid = ObjectIdentifier.newInternal(sha1WithRSAEncryption_OIW_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 842 */     sha224WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha224WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 850 */     sha256WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha256WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 858 */     sha384WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha384WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 866 */     sha512WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha512WithRSAEncryption_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 874 */     shaWithDSA_OIW_oid = ObjectIdentifier.newInternal(shaWithDSA_OIW_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 881 */     sha1WithDSA_OIW_oid = ObjectIdentifier.newInternal(sha1WithDSA_OIW_data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 888 */     sha1WithDSA_oid = ObjectIdentifier.newInternal(dsaWithSHA1_PKIX_data);
/*     */     
/* 890 */     nameTable = new HashMap<>();
/* 891 */     nameTable.put(MD5_oid, "MD5");
/* 892 */     nameTable.put(MD2_oid, "MD2");
/* 893 */     nameTable.put(SHA_oid, "SHA-1");
/* 894 */     nameTable.put(SHA224_oid, "SHA-224");
/* 895 */     nameTable.put(SHA256_oid, "SHA-256");
/* 896 */     nameTable.put(SHA384_oid, "SHA-384");
/* 897 */     nameTable.put(SHA512_oid, "SHA-512");
/* 898 */     nameTable.put(RSAEncryption_oid, "RSA");
/* 899 */     nameTable.put(RSA_oid, "RSA");
/* 900 */     nameTable.put(DH_oid, "Diffie-Hellman");
/* 901 */     nameTable.put(DH_PKIX_oid, "Diffie-Hellman");
/* 902 */     nameTable.put(DSA_oid, "DSA");
/* 903 */     nameTable.put(DSA_OIW_oid, "DSA");
/* 904 */     nameTable.put(EC_oid, "EC");
/* 905 */     nameTable.put(ECDH_oid, "ECDH");
/*     */     
/* 907 */     nameTable.put(AES_oid, "AES");
/*     */     
/* 909 */     nameTable.put(sha1WithECDSA_oid, "SHA1withECDSA");
/* 910 */     nameTable.put(sha224WithECDSA_oid, "SHA224withECDSA");
/* 911 */     nameTable.put(sha256WithECDSA_oid, "SHA256withECDSA");
/* 912 */     nameTable.put(sha384WithECDSA_oid, "SHA384withECDSA");
/* 913 */     nameTable.put(sha512WithECDSA_oid, "SHA512withECDSA");
/* 914 */     nameTable.put(md5WithRSAEncryption_oid, "MD5withRSA");
/* 915 */     nameTable.put(md2WithRSAEncryption_oid, "MD2withRSA");
/* 916 */     nameTable.put(sha1WithDSA_oid, "SHA1withDSA");
/* 917 */     nameTable.put(sha1WithDSA_OIW_oid, "SHA1withDSA");
/* 918 */     nameTable.put(shaWithDSA_OIW_oid, "SHA1withDSA");
/* 919 */     nameTable.put(sha224WithDSA_oid, "SHA224withDSA");
/* 920 */     nameTable.put(sha256WithDSA_oid, "SHA256withDSA");
/* 921 */     nameTable.put(sha1WithRSAEncryption_oid, "SHA1withRSA");
/* 922 */     nameTable.put(sha1WithRSAEncryption_OIW_oid, "SHA1withRSA");
/* 923 */     nameTable.put(sha224WithRSAEncryption_oid, "SHA224withRSA");
/* 924 */     nameTable.put(sha256WithRSAEncryption_oid, "SHA256withRSA");
/* 925 */     nameTable.put(sha384WithRSAEncryption_oid, "SHA384withRSA");
/* 926 */     nameTable.put(sha512WithRSAEncryption_oid, "SHA512withRSA");
/* 927 */     nameTable.put(pbeWithMD5AndDES_oid, "PBEWithMD5AndDES");
/* 928 */     nameTable.put(pbeWithMD5AndRC2_oid, "PBEWithMD5AndRC2");
/* 929 */     nameTable.put(pbeWithSHA1AndDES_oid, "PBEWithSHA1AndDES");
/* 930 */     nameTable.put(pbeWithSHA1AndRC2_oid, "PBEWithSHA1AndRC2");
/* 931 */     nameTable.put(pbeWithSHA1AndDESede_oid, "PBEWithSHA1AndDESede");
/* 932 */     nameTable.put(pbeWithSHA1AndRC2_40_oid, "PBEWithSHA1AndRC2_40");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String makeSigAlg(String paramString1, String paramString2) {
/* 940 */     paramString1 = paramString1.replace("-", "");
/* 941 */     if (paramString2.equalsIgnoreCase("EC")) paramString2 = "ECDSA";
/*     */     
/* 943 */     return paramString1 + "with" + paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEncAlgFromSigAlg(String paramString) {
/* 951 */     paramString = paramString.toUpperCase(Locale.ENGLISH);
/* 952 */     int i = paramString.indexOf("WITH");
/* 953 */     String str = null;
/* 954 */     if (i > 0) {
/* 955 */       int j = paramString.indexOf("AND", i + 4);
/* 956 */       if (j > 0) {
/* 957 */         str = paramString.substring(i + 4, j);
/*     */       } else {
/* 959 */         str = paramString.substring(i + 4);
/*     */       } 
/* 961 */       if (str.equalsIgnoreCase("ECDSA")) {
/* 962 */         str = "EC";
/*     */       }
/*     */     } 
/* 965 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDigAlgFromSigAlg(String paramString) {
/* 973 */     paramString = paramString.toUpperCase(Locale.ENGLISH);
/* 974 */     int i = paramString.indexOf("WITH");
/* 975 */     if (i > 0) {
/* 976 */       return paramString.substring(0, i);
/*     */     }
/* 978 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public AlgorithmId() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/AlgorithmId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */