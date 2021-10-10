/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.krb5.internal.util.KerberosString;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KRBError
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3643809337475284503L;
/*     */   private int pvno;
/*     */   private int msgType;
/*     */   private KerberosTime cTime;
/*     */   private Integer cuSec;
/*     */   private KerberosTime sTime;
/*     */   private Integer suSec;
/*     */   private int errorCode;
/*     */   private Realm crealm;
/*     */   private PrincipalName cname;
/*     */   private PrincipalName sname;
/*     */   private String eText;
/*     */   private byte[] eData;
/*     */   private Checksum eCksum;
/*     */   private PAData[] pa;
/* 102 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     try {
/* 107 */       init(new DerValue((byte[])paramObjectInputStream.readObject()));
/* 108 */       parseEData(this.eData);
/* 109 */     } catch (Exception exception) {
/* 110 */       throw new IOException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*     */     try {
/* 117 */       paramObjectOutputStream.writeObject(asn1Encode());
/* 118 */     } catch (Exception exception) {
/* 119 */       throw new IOException(exception);
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
/*     */   public KRBError(APOptions paramAPOptions, KerberosTime paramKerberosTime1, Integer paramInteger1, KerberosTime paramKerberosTime2, Integer paramInteger2, int paramInt, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString, byte[] paramArrayOfbyte) throws IOException, Asn1Exception {
/* 135 */     this.pvno = 5;
/* 136 */     this.msgType = 30;
/* 137 */     this.cTime = paramKerberosTime1;
/* 138 */     this.cuSec = paramInteger1;
/* 139 */     this.sTime = paramKerberosTime2;
/* 140 */     this.suSec = paramInteger2;
/* 141 */     this.errorCode = paramInt;
/* 142 */     this.crealm = (paramPrincipalName1 != null) ? paramPrincipalName1.getRealm() : null;
/* 143 */     this.cname = paramPrincipalName1;
/* 144 */     this.sname = paramPrincipalName2;
/* 145 */     this.eText = paramString;
/* 146 */     this.eData = paramArrayOfbyte;
/*     */     
/* 148 */     parseEData(this.eData);
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
/*     */   public KRBError(APOptions paramAPOptions, KerberosTime paramKerberosTime1, Integer paramInteger1, KerberosTime paramKerberosTime2, Integer paramInteger2, int paramInt, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, String paramString, byte[] paramArrayOfbyte, Checksum paramChecksum) throws IOException, Asn1Exception {
/* 164 */     this.pvno = 5;
/* 165 */     this.msgType = 30;
/* 166 */     this.cTime = paramKerberosTime1;
/* 167 */     this.cuSec = paramInteger1;
/* 168 */     this.sTime = paramKerberosTime2;
/* 169 */     this.suSec = paramInteger2;
/* 170 */     this.errorCode = paramInt;
/* 171 */     this.crealm = (paramPrincipalName1 != null) ? paramPrincipalName1.getRealm() : null;
/* 172 */     this.cname = paramPrincipalName1;
/* 173 */     this.sname = paramPrincipalName2;
/* 174 */     this.eText = paramString;
/* 175 */     this.eData = paramArrayOfbyte;
/* 176 */     this.eCksum = paramChecksum;
/*     */     
/* 178 */     parseEData(this.eData);
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBError(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 183 */     init(new DerValue(paramArrayOfbyte));
/* 184 */     parseEData(this.eData);
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBError(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 189 */     init(paramDerValue);
/* 190 */     showDebug();
/* 191 */     parseEData(this.eData);
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
/*     */   private void parseEData(byte[] paramArrayOfbyte) throws IOException {
/* 218 */     if (paramArrayOfbyte == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 223 */     if (this.errorCode == 25 || this.errorCode == 24) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 229 */         parsePAData(paramArrayOfbyte);
/* 230 */       } catch (Exception exception) {
/* 231 */         if (DEBUG) {
/* 232 */           System.out.println("Unable to parse eData field of KRB-ERROR:\n" + (new HexDumpEncoder())
/* 233 */               .encodeBuffer(paramArrayOfbyte));
/*     */         }
/* 235 */         IOException iOException = new IOException("Unable to parse eData field of KRB-ERROR");
/*     */         
/* 237 */         iOException.initCause(exception);
/* 238 */         throw iOException;
/*     */       }
/*     */     
/* 241 */     } else if (DEBUG) {
/* 242 */       System.out.println("Unknown eData field of KRB-ERROR:\n" + (new HexDumpEncoder())
/* 243 */           .encodeBuffer(paramArrayOfbyte));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parsePAData(byte[] paramArrayOfbyte) throws IOException, Asn1Exception {
/* 254 */     DerValue derValue = new DerValue(paramArrayOfbyte);
/* 255 */     ArrayList<PAData> arrayList = new ArrayList();
/* 256 */     while (derValue.data.available() > 0) {
/*     */       
/* 258 */       DerValue derValue1 = derValue.data.getDerValue();
/* 259 */       PAData pAData = new PAData(derValue1);
/* 260 */       arrayList.add(pAData);
/* 261 */       if (DEBUG) {
/* 262 */         System.out.println(pAData);
/*     */       }
/*     */     } 
/* 265 */     this.pa = arrayList.<PAData>toArray(new PAData[arrayList.size()]);
/*     */   }
/*     */   
/*     */   public final Realm getClientRealm() {
/* 269 */     return this.crealm;
/*     */   }
/*     */   
/*     */   public final KerberosTime getServerTime() {
/* 273 */     return this.sTime;
/*     */   }
/*     */   
/*     */   public final KerberosTime getClientTime() {
/* 277 */     return this.cTime;
/*     */   }
/*     */   
/*     */   public final Integer getServerMicroSeconds() {
/* 281 */     return this.suSec;
/*     */   }
/*     */   
/*     */   public final Integer getClientMicroSeconds() {
/* 285 */     return this.cuSec;
/*     */   }
/*     */   
/*     */   public final int getErrorCode() {
/* 289 */     return this.errorCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public final PAData[] getPA() {
/* 294 */     return this.pa;
/*     */   }
/*     */   
/*     */   public final String getErrorString() {
/* 298 */     return this.eText;
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 313 */     if ((paramDerValue.getTag() & 0x1F) != 30 || paramDerValue
/* 314 */       .isApplication() != true || paramDerValue
/* 315 */       .isConstructed() != true) {
/* 316 */       throw new Asn1Exception(906);
/*     */     }
/* 318 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 319 */     if (derValue1.getTag() != 48) {
/* 320 */       throw new Asn1Exception(906);
/*     */     }
/* 322 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 323 */     if ((derValue2.getTag() & 0x1F) == 0) {
/*     */       
/* 325 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 326 */       if (this.pvno != 5)
/* 327 */         throw new KrbApErrException(39); 
/*     */     } else {
/* 329 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 332 */     derValue2 = derValue1.getData().getDerValue();
/* 333 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 334 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 335 */       if (this.msgType != 30) {
/* 336 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 339 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 342 */     this.cTime = KerberosTime.parse(derValue1.getData(), (byte)2, true);
/* 343 */     if ((derValue1.getData().peekByte() & 0x1F) == 3) {
/* 344 */       derValue2 = derValue1.getData().getDerValue();
/* 345 */       this.cuSec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 347 */       this.cuSec = null;
/* 348 */     }  this.sTime = KerberosTime.parse(derValue1.getData(), (byte)4, false);
/* 349 */     derValue2 = derValue1.getData().getDerValue();
/* 350 */     if ((derValue2.getTag() & 0x1F) == 5) {
/* 351 */       this.suSec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 353 */       throw new Asn1Exception(906);
/* 354 */     }  derValue2 = derValue1.getData().getDerValue();
/* 355 */     if ((derValue2.getTag() & 0x1F) == 6) {
/* 356 */       this.errorCode = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 358 */       throw new Asn1Exception(906);
/* 359 */     }  this.crealm = Realm.parse(derValue1.getData(), (byte)7, true);
/* 360 */     this.cname = PrincipalName.parse(derValue1.getData(), (byte)8, true, this.crealm);
/* 361 */     Realm realm = Realm.parse(derValue1.getData(), (byte)9, false);
/* 362 */     this.sname = PrincipalName.parse(derValue1.getData(), (byte)10, false, realm);
/* 363 */     this.eText = null;
/* 364 */     this.eData = null;
/* 365 */     this.eCksum = null;
/* 366 */     if (derValue1.getData().available() > 0 && (
/* 367 */       derValue1.getData().peekByte() & 0x1F) == 11) {
/* 368 */       derValue2 = derValue1.getData().getDerValue();
/* 369 */       this
/* 370 */         .eText = (new KerberosString(derValue2.getData().getDerValue())).toString();
/*     */     } 
/*     */     
/* 373 */     if (derValue1.getData().available() > 0 && (
/* 374 */       derValue1.getData().peekByte() & 0x1F) == 12) {
/* 375 */       derValue2 = derValue1.getData().getDerValue();
/* 376 */       this.eData = derValue2.getData().getOctetString();
/*     */     } 
/*     */     
/* 379 */     if (derValue1.getData().available() > 0) {
/* 380 */       this.eCksum = Checksum.parse(derValue1.getData(), (byte)13, true);
/*     */     }
/* 382 */     if (derValue1.getData().available() > 0) {
/* 383 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void showDebug() {
/* 390 */     if (DEBUG) {
/* 391 */       System.out.println(">>>KRBError:");
/* 392 */       if (this.cTime != null)
/* 393 */         System.out.println("\t cTime is " + this.cTime.toDate().toString() + " " + this.cTime.toDate().getTime()); 
/* 394 */       if (this.cuSec != null) {
/* 395 */         System.out.println("\t cuSec is " + this.cuSec.intValue());
/*     */       }
/*     */       
/* 398 */       System.out.println("\t sTime is " + this.sTime.toDate()
/* 399 */           .toString() + " " + this.sTime.toDate().getTime());
/* 400 */       System.out.println("\t suSec is " + this.suSec);
/* 401 */       System.out.println("\t error code is " + this.errorCode);
/* 402 */       System.out.println("\t error Message is " + Krb5.getErrorMessage(this.errorCode));
/* 403 */       if (this.crealm != null) {
/* 404 */         System.out.println("\t crealm is " + this.crealm.toString());
/*     */       }
/* 406 */       if (this.cname != null) {
/* 407 */         System.out.println("\t cname is " + this.cname.toString());
/*     */       }
/* 409 */       if (this.sname != null) {
/* 410 */         System.out.println("\t sname is " + this.sname.toString());
/*     */       }
/* 412 */       if (this.eData != null) {
/* 413 */         System.out.println("\t eData provided.");
/*     */       }
/* 415 */       if (this.eCksum != null) {
/* 416 */         System.out.println("\t checksum provided.");
/*     */       }
/* 418 */       System.out.println("\t msgType is " + this.msgType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 429 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 430 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 432 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 433 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/* 434 */     derOutputStream1 = new DerOutputStream();
/* 435 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 436 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     
/* 438 */     if (this.cTime != null) {
/* 439 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)2), this.cTime.asn1Encode());
/*     */     }
/* 441 */     if (this.cuSec != null) {
/* 442 */       derOutputStream1 = new DerOutputStream();
/* 443 */       derOutputStream1.putInteger(BigInteger.valueOf(this.cuSec.intValue()));
/* 444 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), derOutputStream1);
/*     */     } 
/*     */     
/* 447 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)4), this.sTime.asn1Encode());
/* 448 */     derOutputStream1 = new DerOutputStream();
/* 449 */     derOutputStream1.putInteger(BigInteger.valueOf(this.suSec.intValue()));
/* 450 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)5), derOutputStream1);
/* 451 */     derOutputStream1 = new DerOutputStream();
/* 452 */     derOutputStream1.putInteger(BigInteger.valueOf(this.errorCode));
/* 453 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)6), derOutputStream1);
/*     */     
/* 455 */     if (this.crealm != null) {
/* 456 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)7), this.crealm.asn1Encode());
/*     */     }
/* 458 */     if (this.cname != null) {
/* 459 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)8), this.cname.asn1Encode());
/*     */     }
/*     */     
/* 462 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)9), this.sname.getRealm().asn1Encode());
/* 463 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)10), this.sname.asn1Encode());
/*     */     
/* 465 */     if (this.eText != null) {
/* 466 */       derOutputStream1 = new DerOutputStream();
/* 467 */       derOutputStream1.putDerValue((new KerberosString(this.eText)).toDerValue());
/* 468 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)11), derOutputStream1);
/*     */     } 
/* 470 */     if (this.eData != null) {
/* 471 */       derOutputStream1 = new DerOutputStream();
/* 472 */       derOutputStream1.putOctetString(this.eData);
/* 473 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)12), derOutputStream1);
/*     */     } 
/* 475 */     if (this.eCksum != null) {
/* 476 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)13), this.eCksum.asn1Encode());
/*     */     }
/*     */     
/* 479 */     derOutputStream1 = new DerOutputStream();
/* 480 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 481 */     derOutputStream2 = new DerOutputStream();
/* 482 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)30), derOutputStream1);
/* 483 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 487 */     if (this == paramObject) {
/* 488 */       return true;
/*     */     }
/*     */     
/* 491 */     if (!(paramObject instanceof KRBError)) {
/* 492 */       return false;
/*     */     }
/*     */     
/* 495 */     KRBError kRBError = (KRBError)paramObject;
/* 496 */     return (this.pvno == kRBError.pvno && this.msgType == kRBError.msgType && 
/*     */       
/* 498 */       isEqual(this.cTime, kRBError.cTime) && 
/* 499 */       isEqual(this.cuSec, kRBError.cuSec) && 
/* 500 */       isEqual(this.sTime, kRBError.sTime) && 
/* 501 */       isEqual(this.suSec, kRBError.suSec) && this.errorCode == kRBError.errorCode && 
/*     */       
/* 503 */       isEqual(this.crealm, kRBError.crealm) && 
/* 504 */       isEqual(this.cname, kRBError.cname) && 
/* 505 */       isEqual(this.sname, kRBError.sname) && 
/* 506 */       isEqual(this.eText, kRBError.eText) && 
/* 507 */       Arrays.equals(this.eData, kRBError.eData) && 
/* 508 */       isEqual(this.eCksum, kRBError.eCksum));
/*     */   }
/*     */   
/*     */   private static boolean isEqual(Object paramObject1, Object paramObject2) {
/* 512 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 516 */     int i = 17;
/* 517 */     i = 37 * i + this.pvno;
/* 518 */     i = 37 * i + this.msgType;
/* 519 */     if (this.cTime != null) i = 37 * i + this.cTime.hashCode(); 
/* 520 */     if (this.cuSec != null) i = 37 * i + this.cuSec.hashCode(); 
/* 521 */     if (this.sTime != null) i = 37 * i + this.sTime.hashCode(); 
/* 522 */     if (this.suSec != null) i = 37 * i + this.suSec.hashCode(); 
/* 523 */     i = 37 * i + this.errorCode;
/* 524 */     if (this.crealm != null) i = 37 * i + this.crealm.hashCode(); 
/* 525 */     if (this.cname != null) i = 37 * i + this.cname.hashCode(); 
/* 526 */     if (this.sname != null) i = 37 * i + this.sname.hashCode(); 
/* 527 */     if (this.eText != null) i = 37 * i + this.eText.hashCode(); 
/* 528 */     i = 37 * i + Arrays.hashCode(this.eData);
/* 529 */     if (this.eCksum != null) i = 37 * i + this.eCksum.hashCode(); 
/* 530 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KRBError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */