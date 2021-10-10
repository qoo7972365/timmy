/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
/*     */ import sun.security.krb5.internal.util.KerberosString;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrincipalName
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int KRB_NT_UNKNOWN = 0;
/*     */   public static final int KRB_NT_PRINCIPAL = 1;
/*     */   public static final int KRB_NT_SRV_INST = 2;
/*     */   public static final int KRB_NT_SRV_HST = 3;
/*     */   public static final int KRB_NT_SRV_XHST = 4;
/*     */   public static final int KRB_NT_UID = 5;
/*     */   public static final int KRB_NT_ENTERPRISE = 10;
/*     */   public static final String TGS_DEFAULT_SRV_NAME = "krbtgt";
/*     */   public static final int TGS_DEFAULT_NT = 2;
/*     */   public static final char NAME_COMPONENT_SEPARATOR = '/';
/*     */   public static final char NAME_REALM_SEPARATOR = '@';
/*     */   public static final char REALM_COMPONENT_SEPARATOR = '.';
/*     */   public static final String NAME_COMPONENT_SEPARATOR_STR = "/";
/*     */   public static final String NAME_REALM_SEPARATOR_STR = "@";
/*     */   public static final String REALM_COMPONENT_SEPARATOR_STR = ".";
/*     */   private final int nameType;
/*     */   private final String[] nameStrings;
/*     */   private final Realm nameRealm;
/*     */   private final boolean realmDeduced;
/* 139 */   private transient String salt = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long NAME_STRINGS_OFFSET;
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalName(int paramInt, String[] paramArrayOfString, Realm paramRealm) {
/* 151 */     if (paramRealm == null) {
/* 152 */       throw new IllegalArgumentException("Null realm not allowed");
/*     */     }
/* 154 */     validateNameStrings(paramArrayOfString);
/* 155 */     this.nameType = paramInt;
/* 156 */     this.nameStrings = (String[])paramArrayOfString.clone();
/* 157 */     this.nameRealm = paramRealm;
/* 158 */     this.realmDeduced = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrincipalName(String[] paramArrayOfString, String paramString) throws RealmException {
/* 163 */     this(0, paramArrayOfString, new Realm(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateNameStrings(String[] paramArrayOfString) {
/* 168 */     if (paramArrayOfString == null) {
/* 169 */       throw new IllegalArgumentException("Null nameStrings not allowed");
/*     */     }
/* 171 */     if (paramArrayOfString.length == 0) {
/* 172 */       throw new IllegalArgumentException("Empty nameStrings not allowed");
/*     */     }
/* 174 */     for (String str : paramArrayOfString) {
/* 175 */       if (str == null) {
/* 176 */         throw new IllegalArgumentException("Null nameString not allowed");
/*     */       }
/* 178 */       if (str.isEmpty()) {
/* 179 */         throw new IllegalArgumentException("Empty nameString not allowed");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 186 */       PrincipalName principalName = (PrincipalName)super.clone();
/* 187 */       UNSAFE.putObject(this, NAME_STRINGS_OFFSET, this.nameStrings.clone());
/* 188 */       return principalName;
/* 189 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 190 */       throw new AssertionError("Should never happen");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 198 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 199 */       NAME_STRINGS_OFFSET = unsafe.objectFieldOffset(PrincipalName.class
/* 200 */           .getDeclaredField("nameStrings"));
/* 201 */       UNSAFE = unsafe;
/* 202 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 203 */       throw new Error(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 209 */     if (this == paramObject) {
/* 210 */       return true;
/*     */     }
/* 212 */     if (paramObject instanceof PrincipalName) {
/* 213 */       PrincipalName principalName = (PrincipalName)paramObject;
/* 214 */       return (this.nameRealm.equals(principalName.nameRealm) && 
/* 215 */         Arrays.equals((Object[])this.nameStrings, (Object[])principalName.nameStrings));
/*     */     } 
/* 217 */     return false;
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
/*     */   public PrincipalName(DerValue paramDerValue, Realm paramRealm) throws Asn1Exception, IOException {
/* 248 */     if (paramRealm == null) {
/* 249 */       throw new IllegalArgumentException("Null realm not allowed");
/*     */     }
/* 251 */     this.realmDeduced = false;
/* 252 */     this.nameRealm = paramRealm;
/*     */     
/* 254 */     if (paramDerValue == null) {
/* 255 */       throw new IllegalArgumentException("Null encoding not allowed");
/*     */     }
/* 257 */     if (paramDerValue.getTag() != 48) {
/* 258 */       throw new Asn1Exception(906);
/*     */     }
/* 260 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 261 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 262 */       BigInteger bigInteger = derValue.getData().getBigInteger();
/* 263 */       this.nameType = bigInteger.intValue();
/*     */     } else {
/* 265 */       throw new Asn1Exception(906);
/*     */     } 
/* 267 */     derValue = paramDerValue.getData().getDerValue();
/* 268 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 269 */       DerValue derValue1 = derValue.getData().getDerValue();
/* 270 */       if (derValue1.getTag() != 48) {
/* 271 */         throw new Asn1Exception(906);
/*     */       }
/* 273 */       Vector<String> vector = new Vector();
/*     */       
/* 275 */       while (derValue1.getData().available() > 0) {
/* 276 */         DerValue derValue2 = derValue1.getData().getDerValue();
/* 277 */         String str = (new KerberosString(derValue2)).toString();
/* 278 */         vector.addElement(str);
/*     */       } 
/* 280 */       this.nameStrings = new String[vector.size()];
/* 281 */       vector.copyInto((Object[])this.nameStrings);
/* 282 */       validateNameStrings(this.nameStrings);
/*     */     } else {
/* 284 */       throw new Asn1Exception(906);
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
/*     */   public static PrincipalName parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean, Realm paramRealm) throws Asn1Exception, IOException, RealmException {
/* 309 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/*     */     {
/* 311 */       return null; } 
/* 312 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 313 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 314 */       throw new Asn1Exception(906);
/*     */     }
/* 316 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 317 */     if (paramRealm == null) {
/* 318 */       paramRealm = Realm.getDefault();
/*     */     }
/* 320 */     return new PrincipalName(derValue2, paramRealm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] parseName(String paramString) {
/* 329 */     Vector<String> vector = new Vector();
/* 330 */     String str = paramString;
/* 331 */     byte b = 0;
/* 332 */     int i = 0;
/*     */ 
/*     */     
/* 335 */     while (b < str.length()) {
/* 336 */       if (str.charAt(b) == '/') {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 341 */         if (b > 0 && str.charAt(b - 1) == '\\') {
/*     */           
/* 343 */           str = str.substring(0, b - 1) + str.substring(b, str.length());
/*     */           
/*     */           continue;
/*     */         } 
/* 347 */         if (i <= b) {
/* 348 */           String str1 = str.substring(i, b);
/* 349 */           vector.addElement(str1);
/*     */         } 
/* 351 */         i = b + 1;
/*     */       
/*     */       }
/* 354 */       else if (str.charAt(b) == '@') {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         if (b > 0 && str.charAt(b - 1) == '\\') {
/*     */           
/* 361 */           str = str.substring(0, b - 1) + str.substring(b, str.length());
/*     */           continue;
/*     */         } 
/* 364 */         if (i < b) {
/* 365 */           String str1 = str.substring(i, b);
/* 366 */           vector.addElement(str1);
/*     */         } 
/* 368 */         i = b + 1;
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 373 */       b++;
/*     */     } 
/*     */     
/* 376 */     if (b == str.length()) {
/* 377 */       String str1 = str.substring(i, b);
/* 378 */       vector.addElement(str1);
/*     */     } 
/*     */     
/* 381 */     String[] arrayOfString = new String[vector.size()];
/* 382 */     vector.copyInto((Object[])arrayOfString);
/* 383 */     return arrayOfString;
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
/*     */   public PrincipalName(String paramString1, int paramInt, String paramString2) throws RealmException {
/* 398 */     if (paramString1 == null) {
/* 399 */       throw new IllegalArgumentException("Null name not allowed");
/*     */     }
/* 401 */     String[] arrayOfString = parseName(paramString1);
/* 402 */     validateNameStrings(arrayOfString);
/* 403 */     if (paramString2 == null) {
/* 404 */       paramString2 = Realm.parseRealmAtSeparator(paramString1);
/*     */     }
/*     */ 
/*     */     
/* 408 */     this.realmDeduced = (paramString2 == null);
/*     */     
/* 410 */     switch (paramInt) {
/*     */       case 3:
/* 412 */         if (arrayOfString.length >= 2) {
/* 413 */           String str = arrayOfString[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 420 */             String str1 = InetAddress.getByName(str).getCanonicalHostName();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 425 */             if (str1.toLowerCase(Locale.ENGLISH).startsWith(str
/* 426 */                 .toLowerCase(Locale.ENGLISH) + ".")) {
/* 427 */               str = str1;
/*     */             }
/* 429 */           } catch (UnknownHostException|SecurityException unknownHostException) {}
/*     */ 
/*     */           
/* 432 */           if (str.endsWith(".")) {
/* 433 */             str = str.substring(0, str.length() - 1);
/*     */           }
/* 435 */           arrayOfString[1] = str.toLowerCase(Locale.ENGLISH);
/*     */         } 
/* 437 */         this.nameStrings = arrayOfString;
/* 438 */         this.nameType = paramInt;
/*     */         
/* 440 */         if (paramString2 != null) {
/* 441 */           this.nameRealm = new Realm(paramString2);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 449 */           String str = mapHostToRealm(arrayOfString[1]);
/* 450 */           if (str != null) {
/* 451 */             this.nameRealm = new Realm(str);
/*     */           } else {
/* 453 */             this.nameRealm = Realm.getDefault();
/*     */           } 
/*     */         } 
/*     */         return;
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 4:
/*     */       case 5:
/*     */       case 10:
/* 463 */         this.nameStrings = arrayOfString;
/* 464 */         this.nameType = paramInt;
/* 465 */         if (paramString2 != null) {
/* 466 */           this.nameRealm = new Realm(paramString2);
/*     */         } else {
/* 468 */           this.nameRealm = Realm.getDefault();
/*     */         } 
/*     */         return;
/*     */     } 
/* 472 */     throw new IllegalArgumentException("Illegal name type");
/*     */   }
/*     */ 
/*     */   
/*     */   public PrincipalName(String paramString, int paramInt) throws RealmException {
/* 477 */     this(paramString, paramInt, (String)null);
/*     */   }
/*     */   
/*     */   public PrincipalName(String paramString) throws RealmException {
/* 481 */     this(paramString, 0);
/*     */   }
/*     */   
/*     */   public PrincipalName(String paramString1, String paramString2) throws RealmException {
/* 485 */     this(paramString1, 0, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PrincipalName tgsService(String paramString1, String paramString2) throws KrbException {
/* 490 */     return new PrincipalName(2, new String[] { "krbtgt", paramString1 }, new Realm(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealmAsString() {
/* 496 */     return getRealmString();
/*     */   }
/*     */   
/*     */   public String getPrincipalNameAsString() {
/* 500 */     StringBuffer stringBuffer = new StringBuffer(this.nameStrings[0]);
/* 501 */     for (byte b = 1; b < this.nameStrings.length; b++)
/* 502 */       stringBuffer.append(this.nameStrings[b]); 
/* 503 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 507 */     return toString().hashCode();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 511 */     return toString();
/*     */   }
/*     */   
/*     */   public int getNameType() {
/* 515 */     return this.nameType;
/*     */   }
/*     */   
/*     */   public String[] getNameStrings() {
/* 519 */     return (String[])this.nameStrings.clone();
/*     */   }
/*     */   
/*     */   public byte[][] toByteArray() {
/* 523 */     byte[][] arrayOfByte = new byte[this.nameStrings.length][];
/* 524 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 525 */       arrayOfByte[b] = new byte[this.nameStrings[b].length()];
/* 526 */       arrayOfByte[b] = this.nameStrings[b].getBytes();
/*     */     } 
/* 528 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public String getRealmString() {
/* 532 */     return this.nameRealm.toString();
/*     */   }
/*     */   
/*     */   public Realm getRealm() {
/* 536 */     return this.nameRealm;
/*     */   }
/*     */   
/*     */   public String getSalt() {
/* 540 */     if (this.salt == null) {
/* 541 */       StringBuffer stringBuffer = new StringBuffer();
/* 542 */       stringBuffer.append(this.nameRealm.toString());
/* 543 */       for (byte b = 0; b < this.nameStrings.length; b++) {
/* 544 */         stringBuffer.append(this.nameStrings[b]);
/*     */       }
/* 546 */       return stringBuffer.toString();
/*     */     } 
/* 548 */     return this.salt;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 552 */     StringBuffer stringBuffer = new StringBuffer();
/* 553 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 554 */       if (b > 0)
/* 555 */         stringBuffer.append("/"); 
/* 556 */       String str = this.nameStrings[b];
/* 557 */       str = str.replace("@", "\\@");
/* 558 */       stringBuffer.append(str);
/*     */     } 
/* 560 */     stringBuffer.append("@");
/* 561 */     stringBuffer.append(this.nameRealm.toString());
/* 562 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String getNameString() {
/* 566 */     StringBuffer stringBuffer = new StringBuffer();
/* 567 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 568 */       if (b > 0)
/* 569 */         stringBuffer.append("/"); 
/* 570 */       stringBuffer.append(this.nameStrings[b]);
/*     */     } 
/* 572 */     return stringBuffer.toString();
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 584 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 585 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 586 */     BigInteger bigInteger = BigInteger.valueOf(this.nameType);
/* 587 */     derOutputStream2.putInteger(bigInteger);
/* 588 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 589 */     derOutputStream2 = new DerOutputStream();
/* 590 */     DerValue[] arrayOfDerValue = new DerValue[this.nameStrings.length];
/* 591 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 592 */       arrayOfDerValue[b] = (new KerberosString(this.nameStrings[b])).toDerValue();
/*     */     }
/* 594 */     derOutputStream2.putSequence(arrayOfDerValue);
/* 595 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 596 */     derOutputStream2 = new DerOutputStream();
/* 597 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 598 */     return derOutputStream2.toByteArray();
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
/*     */   public boolean match(PrincipalName paramPrincipalName) {
/* 610 */     boolean bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 615 */     if (this.nameRealm != null && paramPrincipalName.nameRealm != null && 
/* 616 */       !this.nameRealm.toString().equalsIgnoreCase(paramPrincipalName.nameRealm.toString())) {
/* 617 */       bool = false;
/*     */     }
/*     */     
/* 620 */     if (this.nameStrings.length != paramPrincipalName.nameStrings.length) {
/* 621 */       bool = false;
/*     */     } else {
/* 623 */       for (byte b = 0; b < this.nameStrings.length; b++) {
/* 624 */         if (!this.nameStrings[b].equalsIgnoreCase(paramPrincipalName.nameStrings[b])) {
/* 625 */           bool = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 629 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePrincipal(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 640 */     paramCCacheOutputStream.write32(this.nameType);
/* 641 */     paramCCacheOutputStream.write32(this.nameStrings.length);
/* 642 */     byte[] arrayOfByte1 = null;
/* 643 */     arrayOfByte1 = this.nameRealm.toString().getBytes();
/* 644 */     paramCCacheOutputStream.write32(arrayOfByte1.length);
/* 645 */     paramCCacheOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
/* 646 */     byte[] arrayOfByte2 = null;
/* 647 */     for (byte b = 0; b < this.nameStrings.length; b++) {
/* 648 */       arrayOfByte2 = this.nameStrings[b].getBytes();
/* 649 */       paramCCacheOutputStream.write32(arrayOfByte2.length);
/* 650 */       paramCCacheOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
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
/*     */   public String getInstanceComponent() {
/* 664 */     if (this.nameStrings != null && this.nameStrings.length >= 2)
/*     */     {
/* 666 */       return new String(this.nameStrings[1]);
/*     */     }
/*     */     
/* 669 */     return null;
/*     */   }
/*     */   
/*     */   static String mapHostToRealm(String paramString) {
/* 673 */     String str = null;
/*     */     try {
/* 675 */       String str1 = null;
/* 676 */       Config config = Config.getInstance();
/* 677 */       if ((str = config.get(new String[] { "domain_realm", paramString })) != null) {
/* 678 */         return str;
/*     */       }
/* 680 */       for (byte b = 1; b < paramString.length(); b++) {
/* 681 */         if (paramString.charAt(b) == '.' && b != paramString.length() - 1) {
/* 682 */           str1 = paramString.substring(b);
/* 683 */           str = config.get(new String[] { "domain_realm", str1 });
/* 684 */           if (str != null) {
/*     */             break;
/*     */           }
/*     */           
/* 688 */           str1 = paramString.substring(b + 1);
/* 689 */           str = config.get(new String[] { "domain_realm", str1 });
/* 690 */           if (str != null) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 697 */     } catch (KrbException krbException) {}
/*     */     
/* 699 */     return str;
/*     */   }
/*     */   
/*     */   public boolean isRealmDeduced() {
/* 703 */     return this.realmDeduced;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/PrincipalName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */