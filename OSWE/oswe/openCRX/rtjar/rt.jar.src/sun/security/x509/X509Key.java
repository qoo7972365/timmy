/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Security;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.util.BitArray;
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
/*     */ public class X509Key
/*     */   implements PublicKey
/*     */ {
/*     */   private static final long serialVersionUID = -5359250853002055002L;
/*     */   protected AlgorithmId algid;
/*     */   @Deprecated
/*  74 */   protected byte[] key = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  82 */   private int unusedBits = 0;
/*     */ 
/*     */ 
/*     */   
/*  86 */   private BitArray bitStringKey = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] encodedKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Key() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Key(AlgorithmId paramAlgorithmId, BitArray paramBitArray) throws InvalidKeyException {
/* 105 */     this.algid = paramAlgorithmId;
/* 106 */     setKey(paramBitArray);
/* 107 */     encode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setKey(BitArray paramBitArray) {
/* 114 */     this.bitStringKey = (BitArray)paramBitArray.clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.key = paramBitArray.toByteArray();
/* 121 */     int i = paramBitArray.length() % 8;
/* 122 */     this.unusedBits = (i == 0) ? 0 : (8 - i);
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
/*     */   protected BitArray getKey() {
/* 139 */     this.bitStringKey = new BitArray(this.key.length * 8 - this.unusedBits, this.key);
/*     */ 
/*     */ 
/*     */     
/* 143 */     return (BitArray)this.bitStringKey.clone();
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
/*     */   public static PublicKey parse(DerValue paramDerValue) throws IOException {
/*     */     PublicKey publicKey;
/* 165 */     if (paramDerValue.tag != 48) {
/* 166 */       throw new IOException("corrupt subject key");
/*     */     }
/* 168 */     AlgorithmId algorithmId = AlgorithmId.parse(paramDerValue.data.getDerValue());
/*     */     try {
/* 170 */       publicKey = buildX509Key(algorithmId, paramDerValue.data
/* 171 */           .getUnalignedBitString());
/*     */     }
/* 173 */     catch (InvalidKeyException invalidKeyException) {
/* 174 */       throw new IOException("subject key, " + invalidKeyException.getMessage(), invalidKeyException);
/*     */     } 
/*     */     
/* 177 */     if (paramDerValue.data.available() != 0)
/* 178 */       throw new IOException("excess subject key"); 
/* 179 */     return publicKey;
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
/* 197 */     encode();
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
/*     */   static PublicKey buildX509Key(AlgorithmId paramAlgorithmId, BitArray paramBitArray) throws IOException, InvalidKeyException {
/* 213 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 214 */     encode(derOutputStream, paramAlgorithmId, paramBitArray);
/*     */     
/* 216 */     X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(derOutputStream.toByteArray());
/*     */ 
/*     */     
/*     */     try {
/* 220 */       KeyFactory keyFactory = KeyFactory.getInstance(paramAlgorithmId.getName());
/*     */ 
/*     */       
/* 223 */       return keyFactory.generatePublic(x509EncodedKeySpec);
/* 224 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */     
/* 226 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 227 */       throw new InvalidKeyException(invalidKeySpecException.getMessage(), invalidKeySpecException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     String str = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     try { Provider provider = Security.getProvider("SUN");
/* 240 */       if (provider == null)
/* 241 */         throw new InstantiationException(); 
/* 242 */       str = provider.getProperty("PublicKey.X.509." + paramAlgorithmId
/* 243 */           .getName());
/* 244 */       if (str == null) {
/* 245 */         throw new InstantiationException();
/*     */       }
/*     */       
/* 248 */       Class<?> clazz = null;
/*     */       try {
/* 250 */         clazz = Class.forName(str);
/* 251 */       } catch (ClassNotFoundException classNotFoundException) {
/* 252 */         ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 253 */         if (classLoader != null) {
/* 254 */           clazz = classLoader.loadClass(str);
/*     */         }
/*     */       } 
/*     */       
/* 258 */       Object object = null;
/*     */ 
/*     */       
/* 261 */       if (clazz != null)
/* 262 */         object = clazz.newInstance(); 
/* 263 */       if (object instanceof X509Key) {
/* 264 */         X509Key x509Key = (X509Key)object;
/* 265 */         x509Key.algid = paramAlgorithmId;
/* 266 */         x509Key.setKey(paramBitArray);
/* 267 */         x509Key.parseKeyBits();
/* 268 */         return x509Key;
/*     */       }  }
/* 270 */     catch (ClassNotFoundException classNotFoundException) {  }
/* 271 */     catch (InstantiationException instantiationException) {  }
/* 272 */     catch (IllegalAccessException illegalAccessException)
/*     */     
/* 274 */     { throw new IOException(str + " [internal error]"); }
/*     */ 
/*     */     
/* 277 */     return new X509Key(paramAlgorithmId, paramBitArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/* 285 */     return this.algid.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmId getAlgorithmId() {
/* 291 */     return this.algid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 300 */     encode(paramDerOutputStream, this.algid, getKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() {
/*     */     try {
/* 308 */       return (byte[])getEncodedInternal().clone();
/* 309 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 312 */       return null;
/*     */     } 
/*     */   }
/*     */   public byte[] getEncodedInternal() throws InvalidKeyException {
/* 316 */     byte[] arrayOfByte = this.encodedKey;
/* 317 */     if (arrayOfByte == null) {
/*     */       try {
/* 319 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 320 */         encode(derOutputStream);
/* 321 */         arrayOfByte = derOutputStream.toByteArray();
/* 322 */       } catch (IOException iOException) {
/* 323 */         throw new InvalidKeyException("IOException : " + iOException
/* 324 */             .getMessage());
/*     */       } 
/* 326 */       this.encodedKey = arrayOfByte;
/*     */     } 
/* 328 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 335 */     return "X.509";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() throws InvalidKeyException {
/* 344 */     return (byte[])getEncodedInternal().clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 352 */     HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*     */     
/* 354 */     return "algorithm = " + this.algid.toString() + ", unparsed keybits = \n" + hexDumpEncoder
/* 355 */       .encodeBuffer(this.key);
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
/*     */   public void decode(InputStream paramInputStream) throws InvalidKeyException {
/*     */     try {
/* 385 */       DerValue derValue = new DerValue(paramInputStream);
/* 386 */       if (derValue.tag != 48) {
/* 387 */         throw new InvalidKeyException("invalid key format");
/*     */       }
/* 389 */       this.algid = AlgorithmId.parse(derValue.data.getDerValue());
/* 390 */       setKey(derValue.data.getUnalignedBitString());
/* 391 */       parseKeyBits();
/* 392 */       if (derValue.data.available() != 0) {
/* 393 */         throw new InvalidKeyException("excess key data");
/*     */       }
/* 395 */     } catch (IOException iOException) {
/* 396 */       throw new InvalidKeyException("IOException: " + iOException
/* 397 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void decode(byte[] paramArrayOfbyte) throws InvalidKeyException {
/* 402 */     decode(new ByteArrayInputStream(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 410 */     paramObjectOutputStream.write(getEncoded());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/*     */     try {
/* 419 */       decode(paramObjectInputStream);
/* 420 */     } catch (InvalidKeyException invalidKeyException) {
/* 421 */       invalidKeyException.printStackTrace();
/* 422 */       throw new IOException("deserialized key is invalid: " + invalidKeyException
/* 423 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 428 */     if (this == paramObject) {
/* 429 */       return true;
/*     */     }
/* 431 */     if (!(paramObject instanceof Key)) {
/* 432 */       return false;
/*     */     }
/*     */     try {
/* 435 */       byte[] arrayOfByte2, arrayOfByte1 = getEncodedInternal();
/*     */       
/* 437 */       if (paramObject instanceof X509Key) {
/* 438 */         arrayOfByte2 = ((X509Key)paramObject).getEncodedInternal();
/*     */       } else {
/* 440 */         arrayOfByte2 = ((Key)paramObject).getEncoded();
/*     */       } 
/* 442 */       return Arrays.equals(arrayOfByte1, arrayOfByte2);
/* 443 */     } catch (InvalidKeyException invalidKeyException) {
/* 444 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*     */     try {
/* 454 */       byte[] arrayOfByte = getEncodedInternal();
/* 455 */       int i = arrayOfByte.length;
/* 456 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 457 */         i += (arrayOfByte[b] & 0xFF) * 37;
/*     */       }
/* 459 */       return i;
/* 460 */     } catch (InvalidKeyException invalidKeyException) {
/*     */       
/* 462 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void encode(DerOutputStream paramDerOutputStream, AlgorithmId paramAlgorithmId, BitArray paramBitArray) throws IOException {
/* 471 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 472 */     paramAlgorithmId.encode(derOutputStream);
/* 473 */     derOutputStream.putUnalignedBitString(paramBitArray);
/* 474 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/X509Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */