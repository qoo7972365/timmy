/*     */ package sun.security.pkcs;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyRep;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKCS8Key
/*     */   implements PrivateKey
/*     */ {
/*     */   private static final long serialVersionUID = -3836890099307167124L;
/*     */   protected AlgorithmId algid;
/*     */   protected byte[] key;
/*     */   protected byte[] encodedKey;
/*  68 */   public static final BigInteger version = BigInteger.ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKCS8Key() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PKCS8Key(AlgorithmId paramAlgorithmId, byte[] paramArrayOfbyte) throws InvalidKeyException {
/*  84 */     this.algid = paramAlgorithmId;
/*  85 */     this.key = paramArrayOfbyte;
/*  86 */     encode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PKCS8Key parse(DerValue paramDerValue) throws IOException {
/*  95 */     PrivateKey privateKey = parseKey(paramDerValue);
/*  96 */     if (privateKey instanceof PKCS8Key) {
/*  97 */       return (PKCS8Key)privateKey;
/*     */     }
/*  99 */     throw new IOException("Provider did not return PKCS8Key");
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
/*     */   public static PrivateKey parseKey(DerValue paramDerValue) throws IOException {
/*     */     PrivateKey privateKey;
/* 121 */     if (paramDerValue.tag != 48) {
/* 122 */       throw new IOException("corrupt private key");
/*     */     }
/* 124 */     BigInteger bigInteger = paramDerValue.data.getBigInteger();
/* 125 */     if (!version.equals(bigInteger)) {
/* 126 */       throw new IOException("version mismatch: (supported: " + 
/* 127 */           Debug.toHexString(version) + ", parsed: " + 
/*     */           
/* 129 */           Debug.toHexString(bigInteger));
/*     */     }
/*     */     
/* 132 */     AlgorithmId algorithmId = AlgorithmId.parse(paramDerValue.data.getDerValue());
/*     */     
/*     */     try {
/* 135 */       privateKey = buildPKCS8Key(algorithmId, paramDerValue.data.getOctetString());
/*     */     }
/* 137 */     catch (InvalidKeyException invalidKeyException) {
/* 138 */       throw new IOException("corrupt private key");
/*     */     } 
/*     */     
/* 141 */     if (paramDerValue.data.available() != 0)
/* 142 */       throw new IOException("excess private key"); 
/* 143 */     return privateKey;
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
/*     */   protected void parseKeyBits() throws IOException, InvalidKeyException {
/* 161 */     encode();
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
/*     */   static PrivateKey buildPKCS8Key(AlgorithmId paramAlgorithmId, byte[] paramArrayOfbyte) throws IOException, InvalidKeyException {
/* 177 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 178 */     encode(derOutputStream, paramAlgorithmId, paramArrayOfbyte);
/*     */     
/* 180 */     PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(derOutputStream.toByteArray());
/*     */ 
/*     */     
/*     */     try {
/* 184 */       KeyFactory keyFactory = KeyFactory.getInstance(paramAlgorithmId.getName());
/*     */ 
/*     */       
/* 187 */       return keyFactory.generatePrivate(pKCS8EncodedKeySpec);
/* 188 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */     
/* 190 */     } catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     String str = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     try { Provider provider = Security.getProvider("SUN");
/* 204 */       if (provider == null)
/* 205 */         throw new InstantiationException(); 
/* 206 */       str = provider.getProperty("PrivateKey.PKCS#8." + paramAlgorithmId
/* 207 */           .getName());
/* 208 */       if (str == null) {
/* 209 */         throw new InstantiationException();
/*     */       }
/*     */       
/* 212 */       Class<?> clazz = null;
/*     */       try {
/* 214 */         clazz = Class.forName(str);
/* 215 */       } catch (ClassNotFoundException classNotFoundException) {
/* 216 */         ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 217 */         if (classLoader != null) {
/* 218 */           clazz = classLoader.loadClass(str);
/*     */         }
/*     */       } 
/*     */       
/* 222 */       Object object = null;
/*     */ 
/*     */       
/* 225 */       if (clazz != null)
/* 226 */         object = clazz.newInstance(); 
/* 227 */       if (object instanceof PKCS8Key) {
/* 228 */         PKCS8Key pKCS8Key1 = (PKCS8Key)object;
/* 229 */         pKCS8Key1.algid = paramAlgorithmId;
/* 230 */         pKCS8Key1.key = paramArrayOfbyte;
/* 231 */         pKCS8Key1.parseKeyBits();
/* 232 */         return pKCS8Key1;
/*     */       }  }
/* 234 */     catch (ClassNotFoundException classNotFoundException) {  }
/* 235 */     catch (InstantiationException instantiationException) {  }
/* 236 */     catch (IllegalAccessException illegalAccessException)
/*     */     
/* 238 */     { throw new IOException(str + " [internal error]"); }
/*     */ 
/*     */     
/* 241 */     PKCS8Key pKCS8Key = new PKCS8Key();
/* 242 */     pKCS8Key.algid = paramAlgorithmId;
/* 243 */     pKCS8Key.key = paramArrayOfbyte;
/* 244 */     return pKCS8Key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/* 251 */     return this.algid.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId getAlgorithmId() {
/* 257 */     return this.algid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 264 */     encode(paramDerOutputStream, this.algid, this.key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized byte[] getEncoded() {
/* 271 */     byte[] arrayOfByte = null;
/*     */     try {
/* 273 */       arrayOfByte = encode();
/* 274 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */     
/* 276 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 283 */     return "PKCS#8";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() throws InvalidKeyException {
/* 292 */     if (this.encodedKey == null) {
/*     */       
/*     */       try {
/*     */         
/* 296 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 297 */         encode(derOutputStream);
/* 298 */         this.encodedKey = derOutputStream.toByteArray();
/*     */       }
/* 300 */       catch (IOException iOException) {
/* 301 */         throw new InvalidKeyException("IOException : " + iOException
/* 302 */             .getMessage());
/*     */       } 
/*     */     }
/* 305 */     return (byte[])this.encodedKey.clone();
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
/*     */   public void decode(InputStream paramInputStream) throws InvalidKeyException {
/*     */     try {
/* 329 */       DerValue derValue = new DerValue(paramInputStream);
/* 330 */       if (derValue.tag != 48) {
/* 331 */         throw new InvalidKeyException("invalid key format");
/*     */       }
/*     */       
/* 334 */       BigInteger bigInteger = derValue.data.getBigInteger();
/* 335 */       if (!bigInteger.equals(version)) {
/* 336 */         throw new IOException("version mismatch: (supported: " + 
/* 337 */             Debug.toHexString(version) + ", parsed: " + 
/*     */             
/* 339 */             Debug.toHexString(bigInteger));
/*     */       }
/* 341 */       this.algid = AlgorithmId.parse(derValue.data.getDerValue());
/* 342 */       this.key = derValue.data.getOctetString();
/* 343 */       parseKeyBits();
/*     */       
/* 345 */       if (derValue.data.available() != 0);
/*     */ 
/*     */     
/*     */     }
/* 349 */     catch (IOException iOException) {
/* 350 */       throw new InvalidKeyException("IOException : " + iOException
/* 351 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void decode(byte[] paramArrayOfbyte) throws InvalidKeyException {
/* 356 */     decode(new ByteArrayInputStream(paramArrayOfbyte));
/*     */   }
/*     */   
/*     */   protected Object writeReplace() throws ObjectStreamException {
/* 360 */     return new KeyRep(KeyRep.Type.PRIVATE, 
/* 361 */         getAlgorithm(), 
/* 362 */         getFormat(), 
/* 363 */         getEncoded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/*     */     try {
/* 374 */       decode(paramObjectInputStream);
/*     */     }
/* 376 */     catch (InvalidKeyException invalidKeyException) {
/* 377 */       invalidKeyException.printStackTrace();
/* 378 */       throw new IOException("deserialized key is invalid: " + invalidKeyException
/* 379 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void encode(DerOutputStream paramDerOutputStream, AlgorithmId paramAlgorithmId, byte[] paramArrayOfbyte) throws IOException {
/* 388 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 389 */     derOutputStream.putInteger(version);
/* 390 */     paramAlgorithmId.encode(derOutputStream);
/* 391 */     derOutputStream.putOctetString(paramArrayOfbyte);
/* 392 */     paramDerOutputStream.write((byte)48, derOutputStream);
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
/* 406 */     if (this == paramObject) {
/* 407 */       return true;
/*     */     }
/*     */     
/* 410 */     if (paramObject instanceof Key) {
/*     */       byte[] arrayOfByte1;
/*     */ 
/*     */       
/* 414 */       if (this.encodedKey != null) {
/* 415 */         arrayOfByte1 = this.encodedKey;
/*     */       } else {
/* 417 */         arrayOfByte1 = getEncoded();
/*     */       } 
/*     */ 
/*     */       
/* 421 */       byte[] arrayOfByte2 = ((Key)paramObject).getEncoded();
/*     */ 
/*     */       
/* 424 */       return MessageDigest.isEqual(arrayOfByte1, arrayOfByte2);
/*     */     } 
/* 426 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 434 */     int i = 0;
/* 435 */     byte[] arrayOfByte = getEncoded();
/*     */     
/* 437 */     for (byte b = 1; b < arrayOfByte.length; b++) {
/* 438 */       i += arrayOfByte[b] * b;
/*     */     }
/* 440 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/pkcs/PKCS8Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */