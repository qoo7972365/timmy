/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.security.util.Debug;
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
/*     */ public final class PKCS12Attribute
/*     */   implements KeyStore.Entry.Attribute
/*     */ {
/*  44 */   private static final Pattern COLON_SEPARATED_HEX_PAIRS = Pattern.compile("^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2})+$");
/*     */   public PKCS12Attribute(String paramString1, String paramString2) {
/*     */     ObjectIdentifier objectIdentifier;
/*     */     String[] arrayOfString;
/*  48 */     this.hashValue = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     if (paramString1 == null || paramString2 == null) {
/*  74 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  79 */       objectIdentifier = new ObjectIdentifier(paramString1);
/*  80 */     } catch (IOException iOException) {
/*  81 */       throw new IllegalArgumentException("Incorrect format: name", iOException);
/*     */     } 
/*  83 */     this.name = paramString1;
/*     */ 
/*     */     
/*  86 */     int i = paramString2.length();
/*     */     
/*  88 */     if (paramString2.charAt(0) == '[' && paramString2.charAt(i - 1) == ']') {
/*  89 */       arrayOfString = paramString2.substring(1, i - 1).split(", ");
/*     */     } else {
/*  91 */       arrayOfString = new String[] { paramString2 };
/*     */     } 
/*  93 */     this.value = paramString2;
/*     */     
/*     */     try {
/*  96 */       this.encoded = encode(objectIdentifier, arrayOfString);
/*  97 */     } catch (IOException iOException) {
/*  98 */       throw new IllegalArgumentException("Incorrect format: value", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private String value;
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] encoded;
/*     */ 
/*     */ 
/*     */   
/*     */   private int hashValue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS12Attribute(byte[] paramArrayOfbyte) {
/*     */     this.hashValue = -1;
/* 125 */     if (paramArrayOfbyte == null) {
/* 126 */       throw new NullPointerException();
/*     */     }
/* 128 */     this.encoded = (byte[])paramArrayOfbyte.clone();
/*     */     
/*     */     try {
/* 131 */       parse(paramArrayOfbyte);
/* 132 */     } catch (IOException iOException) {
/* 133 */       throw new IllegalArgumentException("Incorrect format: encoded", iOException);
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
/*     */   public String getName() {
/* 145 */     return this.name;
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
/*     */   public String getValue() {
/* 172 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/* 181 */     return (byte[])this.encoded.clone();
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
/*     */   public boolean equals(Object paramObject) {
/* 195 */     if (this == paramObject) {
/* 196 */       return true;
/*     */     }
/* 198 */     if (!(paramObject instanceof PKCS12Attribute)) {
/* 199 */       return false;
/*     */     }
/* 201 */     return Arrays.equals(this.encoded, ((PKCS12Attribute)paramObject).getEncoded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 212 */     if (this.hashValue == -1) {
/* 213 */       Arrays.hashCode(this.encoded);
/*     */     }
/* 215 */     return this.hashValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return this.name + "=" + this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] encode(ObjectIdentifier paramObjectIdentifier, String[] paramArrayOfString) throws IOException {
/* 230 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 231 */     derOutputStream1.putOID(paramObjectIdentifier);
/* 232 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 233 */     for (String str : paramArrayOfString) {
/* 234 */       if (COLON_SEPARATED_HEX_PAIRS.matcher(str).matches()) {
/*     */         
/* 236 */         byte[] arrayOfByte = (new BigInteger(str.replace(":", ""), 16)).toByteArray();
/* 237 */         if (arrayOfByte[0] == 0) {
/* 238 */           arrayOfByte = Arrays.copyOfRange(arrayOfByte, 1, arrayOfByte.length);
/*     */         }
/* 240 */         derOutputStream2.putOctetString(arrayOfByte);
/*     */       } else {
/* 242 */         derOutputStream2.putUTF8String(str);
/*     */       } 
/*     */     } 
/* 245 */     derOutputStream1.write((byte)49, derOutputStream2);
/* 246 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 247 */     derOutputStream3.write((byte)48, derOutputStream1);
/*     */     
/* 249 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */   
/*     */   private void parse(byte[] paramArrayOfbyte) throws IOException {
/* 253 */     DerInputStream derInputStream1 = new DerInputStream(paramArrayOfbyte);
/* 254 */     DerValue[] arrayOfDerValue1 = derInputStream1.getSequence(2);
/* 255 */     ObjectIdentifier objectIdentifier = arrayOfDerValue1[0].getOID();
/*     */     
/* 257 */     DerInputStream derInputStream2 = new DerInputStream(arrayOfDerValue1[1].toByteArray());
/* 258 */     DerValue[] arrayOfDerValue2 = derInputStream2.getSet(1);
/* 259 */     String[] arrayOfString = new String[arrayOfDerValue2.length];
/*     */     
/* 261 */     for (byte b = 0; b < arrayOfDerValue2.length; b++) {
/* 262 */       if ((arrayOfDerValue2[b]).tag == 4)
/* 263 */       { arrayOfString[b] = Debug.toString(arrayOfDerValue2[b].getOctetString()); }
/* 264 */       else { String str; if ((str = arrayOfDerValue2[b].getAsString()) != null) {
/*     */           
/* 266 */           arrayOfString[b] = str;
/* 267 */         } else if ((arrayOfDerValue2[b]).tag == 6) {
/* 268 */           arrayOfString[b] = arrayOfDerValue2[b].getOID().toString();
/* 269 */         } else if ((arrayOfDerValue2[b]).tag == 24) {
/* 270 */           arrayOfString[b] = arrayOfDerValue2[b].getGeneralizedTime().toString();
/* 271 */         } else if ((arrayOfDerValue2[b]).tag == 23) {
/* 272 */           arrayOfString[b] = arrayOfDerValue2[b].getUTCTime().toString();
/* 273 */         } else if ((arrayOfDerValue2[b]).tag == 2) {
/* 274 */           arrayOfString[b] = arrayOfDerValue2[b].getBigInteger().toString();
/* 275 */         } else if ((arrayOfDerValue2[b]).tag == 1) {
/* 276 */           arrayOfString[b] = String.valueOf(arrayOfDerValue2[b].getBoolean());
/*     */         } else {
/* 278 */           arrayOfString[b] = Debug.toString(arrayOfDerValue2[b].getDataBytes());
/*     */         }  }
/*     */     
/*     */     } 
/* 282 */     this.name = objectIdentifier.toString();
/* 283 */     this.value = (arrayOfString.length == 1) ? arrayOfString[0] : Arrays.toString((Object[])arrayOfString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/PKCS12Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */