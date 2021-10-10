/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerEncoder;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ import sun.security.x509.CertificateExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKCS9Attribute
/*     */   implements DerEncoder
/*     */ {
/* 183 */   private static final Debug debug = Debug.getInstance("jar");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   static final ObjectIdentifier[] PKCS9_OIDS = new ObjectIdentifier[18];
/*     */   
/*     */   private static final Class<?> BYTE_ARRAY_CLASS;
/*     */   
/*     */   static {
/* 193 */     for (byte b = 1; b < PKCS9_OIDS.length - 2; b++) {
/* 194 */       PKCS9_OIDS[b] = 
/* 195 */         ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 9, b });
/*     */     } 
/*     */ 
/*     */     
/* 199 */     PKCS9_OIDS[PKCS9_OIDS.length - 2] = 
/* 200 */       ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 9, 16, 2, 12 });
/* 201 */     PKCS9_OIDS[PKCS9_OIDS.length - 1] = 
/* 202 */       ObjectIdentifier.newInternal(new int[] { 1, 2, 840, 113549, 1, 9, 16, 2, 14 });
/*     */     
/*     */     try {
/* 205 */       BYTE_ARRAY_CLASS = Class.forName("[B");
/* 206 */     } catch (ClassNotFoundException classNotFoundException) {
/* 207 */       throw new ExceptionInInitializerError(classNotFoundException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 212 */   public static final ObjectIdentifier EMAIL_ADDRESS_OID = PKCS9_OIDS[1];
/* 213 */   public static final ObjectIdentifier UNSTRUCTURED_NAME_OID = PKCS9_OIDS[2];
/* 214 */   public static final ObjectIdentifier CONTENT_TYPE_OID = PKCS9_OIDS[3];
/* 215 */   public static final ObjectIdentifier MESSAGE_DIGEST_OID = PKCS9_OIDS[4];
/* 216 */   public static final ObjectIdentifier SIGNING_TIME_OID = PKCS9_OIDS[5];
/* 217 */   public static final ObjectIdentifier COUNTERSIGNATURE_OID = PKCS9_OIDS[6];
/* 218 */   public static final ObjectIdentifier CHALLENGE_PASSWORD_OID = PKCS9_OIDS[7];
/* 219 */   public static final ObjectIdentifier UNSTRUCTURED_ADDRESS_OID = PKCS9_OIDS[8];
/* 220 */   public static final ObjectIdentifier EXTENDED_CERTIFICATE_ATTRIBUTES_OID = PKCS9_OIDS[9];
/*     */   
/* 222 */   public static final ObjectIdentifier ISSUER_SERIALNUMBER_OID = PKCS9_OIDS[10];
/*     */ 
/*     */   
/* 225 */   public static final ObjectIdentifier EXTENSION_REQUEST_OID = PKCS9_OIDS[14];
/* 226 */   public static final ObjectIdentifier SMIME_CAPABILITY_OID = PKCS9_OIDS[15];
/* 227 */   public static final ObjectIdentifier SIGNING_CERTIFICATE_OID = PKCS9_OIDS[16];
/* 228 */   public static final ObjectIdentifier SIGNATURE_TIMESTAMP_TOKEN_OID = PKCS9_OIDS[17];
/*     */   
/*     */   public static final String EMAIL_ADDRESS_STR = "EmailAddress";
/*     */   
/*     */   public static final String UNSTRUCTURED_NAME_STR = "UnstructuredName";
/*     */   
/*     */   public static final String CONTENT_TYPE_STR = "ContentType";
/*     */   
/*     */   public static final String MESSAGE_DIGEST_STR = "MessageDigest";
/*     */   
/*     */   public static final String SIGNING_TIME_STR = "SigningTime";
/*     */   
/*     */   public static final String COUNTERSIGNATURE_STR = "Countersignature";
/*     */   
/*     */   public static final String CHALLENGE_PASSWORD_STR = "ChallengePassword";
/*     */   
/*     */   public static final String UNSTRUCTURED_ADDRESS_STR = "UnstructuredAddress";
/*     */   
/*     */   public static final String EXTENDED_CERTIFICATE_ATTRIBUTES_STR = "ExtendedCertificateAttributes";
/*     */   
/*     */   public static final String ISSUER_SERIALNUMBER_STR = "IssuerAndSerialNumber";
/*     */   
/*     */   private static final String RSA_PROPRIETARY_STR = "RSAProprietary";
/*     */   private static final String SMIME_SIGNING_DESC_STR = "SMIMESigningDesc";
/*     */   public static final String EXTENSION_REQUEST_STR = "ExtensionRequest";
/*     */   public static final String SMIME_CAPABILITY_STR = "SMIMECapability";
/*     */   public static final String SIGNING_CERTIFICATE_STR = "SigningCertificate";
/*     */   public static final String SIGNATURE_TIMESTAMP_TOKEN_STR = "SignatureTimestampToken";
/* 256 */   private static final Hashtable<String, ObjectIdentifier> NAME_OID_TABLE = new Hashtable<>(18);
/*     */ 
/*     */   
/*     */   static {
/* 260 */     NAME_OID_TABLE.put("emailaddress", PKCS9_OIDS[1]);
/* 261 */     NAME_OID_TABLE.put("unstructuredname", PKCS9_OIDS[2]);
/* 262 */     NAME_OID_TABLE.put("contenttype", PKCS9_OIDS[3]);
/* 263 */     NAME_OID_TABLE.put("messagedigest", PKCS9_OIDS[4]);
/* 264 */     NAME_OID_TABLE.put("signingtime", PKCS9_OIDS[5]);
/* 265 */     NAME_OID_TABLE.put("countersignature", PKCS9_OIDS[6]);
/* 266 */     NAME_OID_TABLE.put("challengepassword", PKCS9_OIDS[7]);
/* 267 */     NAME_OID_TABLE.put("unstructuredaddress", PKCS9_OIDS[8]);
/* 268 */     NAME_OID_TABLE.put("extendedcertificateattributes", PKCS9_OIDS[9]);
/* 269 */     NAME_OID_TABLE.put("issuerandserialnumber", PKCS9_OIDS[10]);
/* 270 */     NAME_OID_TABLE.put("rsaproprietary", PKCS9_OIDS[11]);
/* 271 */     NAME_OID_TABLE.put("rsaproprietary", PKCS9_OIDS[12]);
/* 272 */     NAME_OID_TABLE.put("signingdescription", PKCS9_OIDS[13]);
/* 273 */     NAME_OID_TABLE.put("extensionrequest", PKCS9_OIDS[14]);
/* 274 */     NAME_OID_TABLE.put("smimecapability", PKCS9_OIDS[15]);
/* 275 */     NAME_OID_TABLE.put("signingcertificate", PKCS9_OIDS[16]);
/* 276 */     NAME_OID_TABLE.put("signaturetimestamptoken", PKCS9_OIDS[17]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   private static final Hashtable<ObjectIdentifier, String> OID_NAME_TABLE = new Hashtable<>(16);
/*     */   
/*     */   static {
/* 286 */     OID_NAME_TABLE.put(PKCS9_OIDS[1], "EmailAddress");
/* 287 */     OID_NAME_TABLE.put(PKCS9_OIDS[2], "UnstructuredName");
/* 288 */     OID_NAME_TABLE.put(PKCS9_OIDS[3], "ContentType");
/* 289 */     OID_NAME_TABLE.put(PKCS9_OIDS[4], "MessageDigest");
/* 290 */     OID_NAME_TABLE.put(PKCS9_OIDS[5], "SigningTime");
/* 291 */     OID_NAME_TABLE.put(PKCS9_OIDS[6], "Countersignature");
/* 292 */     OID_NAME_TABLE.put(PKCS9_OIDS[7], "ChallengePassword");
/* 293 */     OID_NAME_TABLE.put(PKCS9_OIDS[8], "UnstructuredAddress");
/* 294 */     OID_NAME_TABLE.put(PKCS9_OIDS[9], "ExtendedCertificateAttributes");
/* 295 */     OID_NAME_TABLE.put(PKCS9_OIDS[10], "IssuerAndSerialNumber");
/* 296 */     OID_NAME_TABLE.put(PKCS9_OIDS[11], "RSAProprietary");
/* 297 */     OID_NAME_TABLE.put(PKCS9_OIDS[12], "RSAProprietary");
/* 298 */     OID_NAME_TABLE.put(PKCS9_OIDS[13], "SMIMESigningDesc");
/* 299 */     OID_NAME_TABLE.put(PKCS9_OIDS[14], "ExtensionRequest");
/* 300 */     OID_NAME_TABLE.put(PKCS9_OIDS[15], "SMIMECapability");
/* 301 */     OID_NAME_TABLE.put(PKCS9_OIDS[16], "SigningCertificate");
/* 302 */     OID_NAME_TABLE.put(PKCS9_OIDS[17], "SignatureTimestampToken");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   private static final Byte[][] PKCS9_VALUE_TAGS = new Byte[][] { null, { new Byte((byte)22) }, { new Byte((byte)22), new Byte((byte)19) }, { new Byte((byte)6) }, { new Byte((byte)4) }, { new Byte((byte)23) }, { new Byte((byte)48) }, { new Byte((byte)19), new Byte((byte)20) }, { new Byte((byte)19), new Byte((byte)20) }, { new Byte((byte)49) }, { new Byte((byte)48) }, null, null, null, { new Byte((byte)48) }, { new Byte((byte)48) }, { new Byte((byte)48) }, { new Byte((byte)48) } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 334 */   private static final Class<?>[] VALUE_CLASSES = new Class[18];
/*     */   
/*     */   static {
/*     */     try {
/* 338 */       Class<?> clazz = Class.forName("[Ljava.lang.String;");
/*     */       
/* 340 */       VALUE_CLASSES[0] = null;
/* 341 */       VALUE_CLASSES[1] = clazz;
/* 342 */       VALUE_CLASSES[2] = clazz;
/* 343 */       VALUE_CLASSES[3] = 
/* 344 */         Class.forName("sun.security.util.ObjectIdentifier");
/* 345 */       VALUE_CLASSES[4] = BYTE_ARRAY_CLASS;
/* 346 */       VALUE_CLASSES[5] = Class.forName("java.util.Date");
/* 347 */       VALUE_CLASSES[6] = 
/* 348 */         Class.forName("[Lsun.security.pkcs.SignerInfo;");
/* 349 */       VALUE_CLASSES[7] = 
/* 350 */         Class.forName("java.lang.String");
/* 351 */       VALUE_CLASSES[8] = clazz;
/* 352 */       VALUE_CLASSES[9] = null;
/* 353 */       VALUE_CLASSES[10] = null;
/* 354 */       VALUE_CLASSES[11] = null;
/* 355 */       VALUE_CLASSES[12] = null;
/* 356 */       VALUE_CLASSES[13] = null;
/* 357 */       VALUE_CLASSES[14] = 
/* 358 */         Class.forName("sun.security.x509.CertificateExtensions");
/* 359 */       VALUE_CLASSES[15] = null;
/* 360 */       VALUE_CLASSES[16] = null;
/* 361 */       VALUE_CLASSES[17] = BYTE_ARRAY_CLASS;
/* 362 */     } catch (ClassNotFoundException classNotFoundException) {
/* 363 */       throw new ExceptionInInitializerError(classNotFoundException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 371 */   private static final boolean[] SINGLE_VALUED = new boolean[] { 
/*     */       false, false, false, true, true, true, false, true, false, false, 
/*     */       true, false, false, false, true, true, true, true };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObjectIdentifier oid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attribute(ObjectIdentifier paramObjectIdentifier, Object paramObject) throws IllegalArgumentException {
/* 426 */     init(paramObjectIdentifier, paramObject);
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
/*     */   public PKCS9Attribute(String paramString, Object paramObject) throws IllegalArgumentException {
/* 447 */     ObjectIdentifier objectIdentifier = getOID(paramString);
/*     */     
/* 449 */     if (objectIdentifier == null) {
/* 450 */       throw new IllegalArgumentException("Unrecognized attribute name " + paramString + " constructing PKCS9Attribute.");
/*     */     }
/*     */ 
/*     */     
/* 454 */     init(objectIdentifier, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(ObjectIdentifier paramObjectIdentifier, Object paramObject) throws IllegalArgumentException {
/* 460 */     this.oid = paramObjectIdentifier;
/* 461 */     this.index = indexOf(paramObjectIdentifier, (Object[])PKCS9_OIDS, 1);
/* 462 */     Class<?> clazz = (this.index == -1) ? BYTE_ARRAY_CLASS : VALUE_CLASSES[this.index];
/* 463 */     if (!clazz.isInstance(paramObject)) {
/* 464 */       throw new IllegalArgumentException("Wrong value class  for attribute " + paramObjectIdentifier + " constructing PKCS9Attribute; was " + paramObject
/*     */ 
/*     */ 
/*     */           
/* 468 */           .getClass().toString() + ", should be " + clazz
/* 469 */           .toString());
/*     */     }
/* 471 */     this.value = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS9Attribute(DerValue paramDerValue) throws IOException {
/*     */     String[] arrayOfString;
/*     */     SignerInfo[] arrayOfSignerInfo;
/*     */     byte b2;
/* 484 */     DerInputStream derInputStream = new DerInputStream(paramDerValue.toByteArray());
/* 485 */     DerValue[] arrayOfDerValue1 = derInputStream.getSequence(2);
/*     */     
/* 487 */     if (derInputStream.available() != 0) {
/* 488 */       throw new IOException("Excess data parsing PKCS9Attribute");
/*     */     }
/* 490 */     if (arrayOfDerValue1.length != 2) {
/* 491 */       throw new IOException("PKCS9Attribute doesn't have two components");
/*     */     }
/*     */     
/* 494 */     this.oid = arrayOfDerValue1[0].getOID();
/* 495 */     byte[] arrayOfByte = arrayOfDerValue1[1].toByteArray();
/* 496 */     DerValue[] arrayOfDerValue2 = (new DerInputStream(arrayOfByte)).getSet(1);
/*     */     
/* 498 */     this.index = indexOf(this.oid, (Object[])PKCS9_OIDS, 1);
/* 499 */     if (this.index == -1) {
/* 500 */       if (debug != null) {
/* 501 */         debug.println("Unsupported signer attribute: " + this.oid);
/*     */       }
/* 503 */       this.value = arrayOfByte;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 508 */     if (SINGLE_VALUED[this.index] && arrayOfDerValue2.length > 1) {
/* 509 */       throwSingleValuedException();
/*     */     }
/*     */ 
/*     */     
/* 513 */     for (byte b1 = 0; b1 < arrayOfDerValue2.length; b1++) {
/* 514 */       Byte byte_ = new Byte((arrayOfDerValue2[b1]).tag);
/*     */       
/* 516 */       if (indexOf(byte_, (Object[])PKCS9_VALUE_TAGS[this.index], 0) == -1) {
/* 517 */         throwTagException(byte_);
/*     */       }
/*     */     } 
/* 520 */     switch (this.index) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 8:
/* 525 */         arrayOfString = new String[arrayOfDerValue2.length];
/*     */         
/* 527 */         for (b2 = 0; b2 < arrayOfDerValue2.length; b2++)
/* 528 */           arrayOfString[b2] = arrayOfDerValue2[b2].getAsString(); 
/* 529 */         this.value = arrayOfString;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 534 */         this.value = arrayOfDerValue2[0].getOID();
/*     */         break;
/*     */       
/*     */       case 4:
/* 538 */         this.value = arrayOfDerValue2[0].getOctetString();
/*     */         break;
/*     */       
/*     */       case 5:
/* 542 */         this.value = (new DerInputStream(arrayOfDerValue2[0].toByteArray())).getUTCTime();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 547 */         arrayOfSignerInfo = new SignerInfo[arrayOfDerValue2.length];
/* 548 */         for (b2 = 0; b2 < arrayOfDerValue2.length; b2++)
/* 549 */           arrayOfSignerInfo[b2] = new SignerInfo(arrayOfDerValue2[b2]
/* 550 */               .toDerInputStream()); 
/* 551 */         this.value = arrayOfSignerInfo;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 556 */         this.value = arrayOfDerValue2[0].getAsString();
/*     */         break;
/*     */       
/*     */       case 9:
/* 560 */         throw new IOException("PKCS9 extended-certificate attribute not supported.");
/*     */ 
/*     */       
/*     */       case 10:
/* 564 */         throw new IOException("PKCS9 IssuerAndSerialNumberattribute not supported.");
/*     */ 
/*     */       
/*     */       case 11:
/*     */       case 12:
/* 569 */         throw new IOException("PKCS9 RSA DSI attributes11 and 12, not supported.");
/*     */ 
/*     */       
/*     */       case 13:
/* 573 */         throw new IOException("PKCS9 attribute #13 not supported.");
/*     */ 
/*     */       
/*     */       case 14:
/* 577 */         this
/* 578 */           .value = new CertificateExtensions(new DerInputStream(arrayOfDerValue2[0].toByteArray()));
/*     */         break;
/*     */       
/*     */       case 15:
/* 582 */         throw new IOException("PKCS9 SMIMECapability attribute not supported.");
/*     */ 
/*     */       
/*     */       case 16:
/* 586 */         this.value = new SigningCertificateInfo(arrayOfDerValue2[0].toByteArray());
/*     */         break;
/*     */       
/*     */       case 17:
/* 590 */         this.value = arrayOfDerValue2[0].toByteArray();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/*     */     String[] arrayOfString2;
/*     */     DerOutputStream derOutputStream3;
/*     */     String[] arrayOfString1;
/*     */     DerOutputStream[] arrayOfDerOutputStream;
/*     */     CertificateExtensions certificateExtensions;
/*     */     byte b;
/* 605 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 606 */     derOutputStream1.putOID(this.oid);
/* 607 */     switch (this.index) {
/*     */       case -1:
/* 609 */         derOutputStream1.write((byte[])this.value);
/*     */         break;
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 614 */         arrayOfString2 = (String[])this.value;
/* 615 */         arrayOfDerOutputStream = new DerOutputStream[arrayOfString2.length];
/*     */ 
/*     */         
/* 618 */         for (b = 0; b < arrayOfString2.length; b++) {
/* 619 */           arrayOfDerOutputStream[b] = new DerOutputStream();
/* 620 */           arrayOfDerOutputStream[b].putIA5String(arrayOfString2[b]);
/*     */         } 
/* 622 */         derOutputStream1.putOrderedSetOf((byte)49, (DerEncoder[])arrayOfDerOutputStream);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 628 */         derOutputStream3 = new DerOutputStream();
/* 629 */         derOutputStream3.putOID((ObjectIdentifier)this.value);
/* 630 */         derOutputStream1.write((byte)49, derOutputStream3.toByteArray());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 636 */         derOutputStream3 = new DerOutputStream();
/* 637 */         derOutputStream3.putOctetString((byte[])this.value);
/* 638 */         derOutputStream1.write((byte)49, derOutputStream3.toByteArray());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 644 */         derOutputStream3 = new DerOutputStream();
/* 645 */         derOutputStream3.putUTCTime((Date)this.value);
/* 646 */         derOutputStream1.write((byte)49, derOutputStream3.toByteArray());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 6:
/* 651 */         derOutputStream1.putOrderedSetOf((byte)49, (DerEncoder[])this.value);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 656 */         derOutputStream3 = new DerOutputStream();
/* 657 */         derOutputStream3.putPrintableString((String)this.value);
/* 658 */         derOutputStream1.write((byte)49, derOutputStream3.toByteArray());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 664 */         arrayOfString1 = (String[])this.value;
/* 665 */         arrayOfDerOutputStream = new DerOutputStream[arrayOfString1.length];
/*     */ 
/*     */         
/* 668 */         for (b = 0; b < arrayOfString1.length; b++) {
/* 669 */           arrayOfDerOutputStream[b] = new DerOutputStream();
/* 670 */           arrayOfDerOutputStream[b].putPrintableString(arrayOfString1[b]);
/*     */         } 
/* 672 */         derOutputStream1.putOrderedSetOf((byte)49, (DerEncoder[])arrayOfDerOutputStream);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 9:
/* 677 */         throw new IOException("PKCS9 extended-certificate attribute not supported.");
/*     */ 
/*     */       
/*     */       case 10:
/* 681 */         throw new IOException("PKCS9 IssuerAndSerialNumberattribute not supported.");
/*     */ 
/*     */       
/*     */       case 11:
/*     */       case 12:
/* 686 */         throw new IOException("PKCS9 RSA DSI attributes11 and 12, not supported.");
/*     */ 
/*     */       
/*     */       case 13:
/* 690 */         throw new IOException("PKCS9 attribute #13 not supported.");
/*     */ 
/*     */ 
/*     */       
/*     */       case 14:
/* 695 */         derOutputStream2 = new DerOutputStream();
/* 696 */         certificateExtensions = (CertificateExtensions)this.value;
/*     */         try {
/* 698 */           certificateExtensions.encode(derOutputStream2, true);
/* 699 */         } catch (CertificateException certificateException) {
/* 700 */           throw new IOException(certificateException.toString());
/*     */         } 
/* 702 */         derOutputStream1.write((byte)49, derOutputStream2.toByteArray());
/*     */         break;
/*     */       
/*     */       case 15:
/* 706 */         throw new IOException("PKCS9 attribute #15 not supported.");
/*     */ 
/*     */       
/*     */       case 16:
/* 710 */         throw new IOException("PKCS9 SigningCertificate attribute not supported.");
/*     */ 
/*     */ 
/*     */       
/*     */       case 17:
/* 715 */         derOutputStream1.write((byte)49, (byte[])this.value);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 721 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 722 */     derOutputStream2.write((byte)48, derOutputStream1.toByteArray());
/*     */     
/* 724 */     paramOutputStream.write(derOutputStream2.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKnown() {
/* 733 */     return (this.index != -1);
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
/*     */   public Object getValue() {
/* 747 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingleValued() {
/* 754 */     return (this.index == -1 || SINGLE_VALUED[this.index]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getOID() {
/* 761 */     return this.oid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 768 */     return (this.index == -1) ? this.oid
/* 769 */       .toString() : OID_NAME_TABLE
/* 770 */       .get(PKCS9_OIDS[this.index]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectIdentifier getOID(String paramString) {
/* 778 */     return NAME_OID_TABLE.get(paramString.toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(ObjectIdentifier paramObjectIdentifier) {
/* 786 */     return OID_NAME_TABLE.get(paramObjectIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 793 */     StringBuffer stringBuffer = new StringBuffer(100);
/*     */     
/* 795 */     stringBuffer.append("[");
/*     */     
/* 797 */     if (this.index == -1) {
/* 798 */       stringBuffer.append(this.oid.toString());
/*     */     } else {
/* 800 */       stringBuffer.append(OID_NAME_TABLE.get(PKCS9_OIDS[this.index]));
/*     */     } 
/* 802 */     stringBuffer.append(": ");
/*     */     
/* 804 */     if (this.index == -1 || SINGLE_VALUED[this.index]) {
/* 805 */       if (this.value instanceof byte[]) {
/* 806 */         HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 807 */         stringBuffer.append(hexDumpEncoder.encodeBuffer((byte[])this.value));
/*     */       } else {
/* 809 */         stringBuffer.append(this.value.toString());
/*     */       } 
/* 811 */       stringBuffer.append("]");
/* 812 */       return stringBuffer.toString();
/*     */     } 
/* 814 */     boolean bool = true;
/* 815 */     Object[] arrayOfObject = (Object[])this.value;
/*     */     
/* 817 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 818 */       if (bool) {
/* 819 */         bool = false;
/*     */       } else {
/* 821 */         stringBuffer.append(", ");
/*     */       } 
/* 823 */       stringBuffer.append(arrayOfObject[b].toString());
/*     */     } 
/* 825 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int indexOf(Object paramObject, Object[] paramArrayOfObject, int paramInt) {
/* 836 */     for (int i = paramInt; i < paramArrayOfObject.length; i++) {
/* 837 */       if (paramObject.equals(paramArrayOfObject[i])) return i; 
/*     */     } 
/* 839 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwSingleValuedException() throws IOException {
/* 847 */     throw new IOException("Single-value attribute " + this.oid + " (" + 
/* 848 */         getName() + ") has multiple values.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwTagException(Byte paramByte) throws IOException {
/* 859 */     Byte[] arrayOfByte = PKCS9_VALUE_TAGS[this.index];
/* 860 */     StringBuffer stringBuffer = new StringBuffer(100);
/* 861 */     stringBuffer.append("Value of attribute ");
/* 862 */     stringBuffer.append(this.oid.toString());
/* 863 */     stringBuffer.append(" (");
/* 864 */     stringBuffer.append(getName());
/* 865 */     stringBuffer.append(") has wrong tag: ");
/* 866 */     stringBuffer.append(paramByte.toString());
/* 867 */     stringBuffer.append(".  Expected tags: ");
/*     */     
/* 869 */     stringBuffer.append(arrayOfByte[0].toString());
/*     */     
/* 871 */     for (byte b = 1; b < arrayOfByte.length; b++) {
/* 872 */       stringBuffer.append(", ");
/* 873 */       stringBuffer.append(arrayOfByte[b].toString());
/*     */     } 
/* 875 */     stringBuffer.append(".");
/* 876 */     throw new IOException(stringBuffer.toString());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/PKCS9Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */