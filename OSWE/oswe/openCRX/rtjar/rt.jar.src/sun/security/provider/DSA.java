/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.DigestException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.SignatureException;
/*     */ import java.security.SignatureSpi;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.DSAPrivateKey;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.util.Arrays;
/*     */ import sun.security.jca.JCAUtil;
/*     */ import sun.security.util.Debug;
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
/*     */ abstract class DSA
/*     */   extends SignatureSpi
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private static final int BLINDING_BITS = 7;
/*  75 */   private static final BigInteger BLINDING_CONSTANT = BigInteger.valueOf(128L);
/*     */ 
/*     */   
/*     */   private DSAParams params;
/*     */ 
/*     */   
/*     */   private BigInteger presetP;
/*     */ 
/*     */   
/*     */   private BigInteger presetQ;
/*     */ 
/*     */   
/*     */   private BigInteger presetG;
/*     */ 
/*     */   
/*     */   private BigInteger presetY;
/*     */ 
/*     */   
/*     */   private BigInteger presetX;
/*     */   
/*     */   private SecureRandom signingRandom;
/*     */   
/*     */   private final MessageDigest md;
/*     */ 
/*     */   
/*     */   DSA(MessageDigest paramMessageDigest) {
/* 101 */     this.md = paramMessageDigest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkKey(DSAParams paramDSAParams, int paramInt, String paramString) throws InvalidKeyException {
/* 109 */     int i = paramDSAParams.getQ().bitLength();
/* 110 */     if (i > paramInt) {
/* 111 */       throw new InvalidKeyException("The security strength of " + paramString + " digest algorithm is not sufficient for this key size");
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
/*     */   protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
/* 126 */     if (!(paramPrivateKey instanceof DSAPrivateKey)) {
/* 127 */       throw new InvalidKeyException("not a DSA private key: " + paramPrivateKey);
/*     */     }
/*     */ 
/*     */     
/* 131 */     DSAPrivateKey dSAPrivateKey = (DSAPrivateKey)paramPrivateKey;
/*     */ 
/*     */ 
/*     */     
/* 135 */     DSAParams dSAParams = dSAPrivateKey.getParams();
/* 136 */     if (dSAParams == null) {
/* 137 */       throw new InvalidKeyException("DSA private key lacks parameters");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     if (this.md.getAlgorithm() != "NullDigest20") {
/* 143 */       checkKey(dSAParams, this.md.getDigestLength() * 8, this.md.getAlgorithm());
/*     */     }
/*     */     
/* 146 */     this.params = dSAParams;
/* 147 */     this.presetX = dSAPrivateKey.getX();
/* 148 */     this.presetY = null;
/* 149 */     this.presetP = dSAParams.getP();
/* 150 */     this.presetQ = dSAParams.getQ();
/* 151 */     this.presetG = dSAParams.getG();
/* 152 */     this.md.reset();
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
/*     */   protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
/* 164 */     if (!(paramPublicKey instanceof DSAPublicKey)) {
/* 165 */       throw new InvalidKeyException("not a DSA public key: " + paramPublicKey);
/*     */     }
/*     */     
/* 168 */     DSAPublicKey dSAPublicKey = (DSAPublicKey)paramPublicKey;
/*     */ 
/*     */ 
/*     */     
/* 172 */     DSAParams dSAParams = dSAPublicKey.getParams();
/* 173 */     if (dSAParams == null) {
/* 174 */       throw new InvalidKeyException("DSA public key lacks parameters");
/*     */     }
/* 176 */     this.params = dSAParams;
/* 177 */     this.presetY = dSAPublicKey.getY();
/* 178 */     this.presetX = null;
/* 179 */     this.presetP = dSAParams.getP();
/* 180 */     this.presetQ = dSAParams.getQ();
/* 181 */     this.presetG = dSAParams.getG();
/* 182 */     this.md.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte paramByte) {
/* 189 */     this.md.update(paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 196 */     this.md.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   protected void engineUpdate(ByteBuffer paramByteBuffer) {
/* 200 */     this.md.update(paramByteBuffer);
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
/*     */   protected byte[] engineSign() throws SignatureException {
/* 219 */     BigInteger bigInteger1 = generateK(this.presetQ);
/* 220 */     BigInteger bigInteger2 = generateR(this.presetP, this.presetQ, this.presetG, bigInteger1);
/* 221 */     BigInteger bigInteger3 = generateS(this.presetX, this.presetQ, bigInteger2, bigInteger1);
/*     */     
/*     */     try {
/* 224 */       DerOutputStream derOutputStream = new DerOutputStream(100);
/* 225 */       derOutputStream.putInteger(bigInteger2);
/* 226 */       derOutputStream.putInteger(bigInteger3);
/*     */       
/* 228 */       DerValue derValue = new DerValue((byte)48, derOutputStream.toByteArray());
/*     */       
/* 230 */       return derValue.toByteArray();
/*     */     }
/* 232 */     catch (IOException iOException) {
/* 233 */       throw new SignatureException("error encoding signature");
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
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
/* 251 */     return engineVerify(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/* 273 */     BigInteger bigInteger1 = null;
/* 274 */     BigInteger bigInteger2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 278 */       DerInputStream derInputStream = new DerInputStream(paramArrayOfbyte, paramInt1, paramInt2, false);
/*     */       
/* 280 */       DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/*     */ 
/*     */ 
/*     */       
/* 284 */       if (arrayOfDerValue.length != 2 || derInputStream.available() != 0) {
/* 285 */         throw new IOException("Invalid encoding for signature");
/*     */       }
/* 287 */       bigInteger1 = arrayOfDerValue[0].getBigInteger();
/* 288 */       bigInteger2 = arrayOfDerValue[1].getBigInteger();
/* 289 */     } catch (IOException iOException) {
/* 290 */       throw new SignatureException("Invalid encoding for signature", iOException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     if (bigInteger1.signum() < 0) {
/* 297 */       bigInteger1 = new BigInteger(1, bigInteger1.toByteArray());
/*     */     }
/* 299 */     if (bigInteger2.signum() < 0) {
/* 300 */       bigInteger2 = new BigInteger(1, bigInteger2.toByteArray());
/*     */     }
/*     */     
/* 303 */     if (bigInteger1.compareTo(this.presetQ) == -1 && bigInteger2.compareTo(this.presetQ) == -1) {
/* 304 */       BigInteger bigInteger3 = generateW(this.presetP, this.presetQ, this.presetG, bigInteger2);
/* 305 */       BigInteger bigInteger4 = generateV(this.presetY, this.presetP, this.presetQ, this.presetG, bigInteger3, bigInteger1);
/* 306 */       return bigInteger4.equals(bigInteger1);
/*     */     } 
/* 308 */     throw new SignatureException("invalid signature: out of range values");
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void engineSetParameter(String paramString, Object paramObject) {
/* 314 */     throw new InvalidParameterException("No parameter accepted");
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected Object engineGetParameter(String paramString) {
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BigInteger generateR(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) {
/* 327 */     SecureRandom secureRandom = getSigningRandom();
/*     */     
/* 329 */     BigInteger bigInteger1 = new BigInteger(7, secureRandom);
/*     */     
/* 331 */     bigInteger1 = bigInteger1.add(BLINDING_CONSTANT);
/*     */     
/* 333 */     paramBigInteger4 = paramBigInteger4.add(paramBigInteger2.multiply(bigInteger1));
/*     */     
/* 335 */     BigInteger bigInteger2 = paramBigInteger3.modPow(paramBigInteger4, paramBigInteger1);
/* 336 */     return bigInteger2.mod(paramBigInteger2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BigInteger generateS(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) throws SignatureException {
/*     */     byte[] arrayOfByte;
/*     */     try {
/* 344 */       arrayOfByte = this.md.digest();
/* 345 */     } catch (RuntimeException runtimeException) {
/*     */       
/* 347 */       throw new SignatureException(runtimeException.getMessage());
/*     */     } 
/*     */     
/* 350 */     int i = paramBigInteger2.bitLength() / 8;
/* 351 */     if (i < arrayOfByte.length) {
/* 352 */       arrayOfByte = Arrays.copyOfRange(arrayOfByte, 0, i);
/*     */     }
/* 354 */     BigInteger bigInteger1 = new BigInteger(1, arrayOfByte);
/* 355 */     BigInteger bigInteger2 = paramBigInteger4.modInverse(paramBigInteger2);
/*     */     
/* 357 */     return paramBigInteger1.multiply(paramBigInteger3).add(bigInteger1).multiply(bigInteger2).mod(paramBigInteger2);
/*     */   }
/*     */ 
/*     */   
/*     */   private BigInteger generateW(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) {
/* 362 */     return paramBigInteger4.modInverse(paramBigInteger2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BigInteger generateV(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, BigInteger paramBigInteger5, BigInteger paramBigInteger6) throws SignatureException {
/*     */     byte[] arrayOfByte;
/*     */     try {
/* 371 */       arrayOfByte = this.md.digest();
/* 372 */     } catch (RuntimeException runtimeException) {
/*     */       
/* 374 */       throw new SignatureException(runtimeException.getMessage());
/*     */     } 
/*     */     
/* 377 */     int i = paramBigInteger3.bitLength() / 8;
/* 378 */     if (i < arrayOfByte.length) {
/* 379 */       arrayOfByte = Arrays.copyOfRange(arrayOfByte, 0, i);
/*     */     }
/* 381 */     BigInteger bigInteger1 = new BigInteger(1, arrayOfByte);
/*     */     
/* 383 */     BigInteger bigInteger2 = bigInteger1.multiply(paramBigInteger5).mod(paramBigInteger3);
/* 384 */     BigInteger bigInteger3 = paramBigInteger6.multiply(paramBigInteger5).mod(paramBigInteger3);
/*     */     
/* 386 */     BigInteger bigInteger4 = paramBigInteger4.modPow(bigInteger2, paramBigInteger2);
/* 387 */     BigInteger bigInteger5 = paramBigInteger1.modPow(bigInteger3, paramBigInteger2);
/* 388 */     BigInteger bigInteger6 = bigInteger4.multiply(bigInteger5);
/* 389 */     BigInteger bigInteger7 = bigInteger6.mod(paramBigInteger2);
/* 390 */     return bigInteger7.mod(paramBigInteger3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BigInteger generateK(BigInteger paramBigInteger) {
/* 395 */     SecureRandom secureRandom = getSigningRandom();
/* 396 */     byte[] arrayOfByte = new byte[(paramBigInteger.bitLength() + 7) / 8 + 8];
/*     */     
/* 398 */     secureRandom.nextBytes(arrayOfByte);
/* 399 */     return (new BigInteger(1, arrayOfByte)).mod(paramBigInteger
/* 400 */         .subtract(BigInteger.ONE)).add(BigInteger.ONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecureRandom getSigningRandom() {
/* 406 */     if (this.signingRandom == null) {
/* 407 */       if (this.appRandom != null) {
/* 408 */         this.signingRandom = this.appRandom;
/*     */       } else {
/* 410 */         this.signingRandom = JCAUtil.getSecureRandom();
/*     */       } 
/*     */     }
/* 413 */     return this.signingRandom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 420 */     String str = "DSA Signature";
/* 421 */     if (this.presetP != null && this.presetQ != null && this.presetG != null) {
/* 422 */       str = str + "\n\tp: " + Debug.toHexString(this.presetP);
/* 423 */       str = str + "\n\tq: " + Debug.toHexString(this.presetQ);
/* 424 */       str = str + "\n\tg: " + Debug.toHexString(this.presetG);
/*     */     } else {
/* 426 */       str = str + "\n\t P, Q or G not initialized.";
/*     */     } 
/* 428 */     if (this.presetY != null) {
/* 429 */       str = str + "\n\ty: " + Debug.toHexString(this.presetY);
/*     */     }
/* 431 */     if (this.presetY == null && this.presetX == null) {
/* 432 */       str = str + "\n\tUNINIIALIZED";
/*     */     }
/* 434 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class SHA224withDSA
/*     */     extends DSA
/*     */   {
/*     */     public SHA224withDSA() throws NoSuchAlgorithmException {
/* 442 */       super(MessageDigest.getInstance("SHA-224"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class SHA256withDSA
/*     */     extends DSA
/*     */   {
/*     */     public SHA256withDSA() throws NoSuchAlgorithmException {
/* 451 */       super(MessageDigest.getInstance("SHA-256"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class SHA1withDSA
/*     */     extends DSA
/*     */   {
/*     */     public SHA1withDSA() throws NoSuchAlgorithmException {
/* 460 */       super(MessageDigest.getInstance("SHA-1"));
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
/*     */   public static final class RawDSA
/*     */     extends DSA
/*     */   {
/*     */     public static final class NullDigest20
/*     */       extends MessageDigest
/*     */     {
/* 477 */       private final byte[] digestBuffer = new byte[20];
/*     */ 
/*     */ 
/*     */       
/* 481 */       private int ofs = 0;
/*     */       
/*     */       protected NullDigest20() {
/* 484 */         super("NullDigest20");
/*     */       }
/*     */       protected void engineUpdate(byte param2Byte) {
/* 487 */         if (this.ofs == this.digestBuffer.length) {
/* 488 */           this.ofs = Integer.MAX_VALUE;
/*     */         } else {
/* 490 */           this.digestBuffer[this.ofs++] = param2Byte;
/*     */         } 
/*     */       }
/*     */       protected void engineUpdate(byte[] param2ArrayOfbyte, int param2Int1, int param2Int2) {
/* 494 */         if (param2Int2 > this.digestBuffer.length - this.ofs) {
/* 495 */           this.ofs = Integer.MAX_VALUE;
/*     */         } else {
/* 497 */           System.arraycopy(param2ArrayOfbyte, param2Int1, this.digestBuffer, this.ofs, param2Int2);
/* 498 */           this.ofs += param2Int2;
/*     */         } 
/*     */       }
/*     */       protected final void engineUpdate(ByteBuffer param2ByteBuffer) {
/* 502 */         int i = param2ByteBuffer.remaining();
/* 503 */         if (i > this.digestBuffer.length - this.ofs) {
/* 504 */           this.ofs = Integer.MAX_VALUE;
/*     */         } else {
/* 506 */           param2ByteBuffer.get(this.digestBuffer, this.ofs, i);
/* 507 */           this.ofs += i;
/*     */         } 
/*     */       }
/*     */       protected byte[] engineDigest() throws RuntimeException {
/* 511 */         if (this.ofs != this.digestBuffer.length) {
/* 512 */           throw new RuntimeException("Data for RawDSA must be exactly 20 bytes long");
/*     */         }
/*     */         
/* 515 */         reset();
/* 516 */         return this.digestBuffer;
/*     */       }
/*     */       
/*     */       protected int engineDigest(byte[] param2ArrayOfbyte, int param2Int1, int param2Int2) throws DigestException {
/* 520 */         if (this.ofs != this.digestBuffer.length) {
/* 521 */           throw new DigestException("Data for RawDSA must be exactly 20 bytes long");
/*     */         }
/*     */         
/* 524 */         if (param2Int2 < this.digestBuffer.length) {
/* 525 */           throw new DigestException("Output buffer too small; must be at least 20 bytes");
/*     */         }
/*     */         
/* 528 */         System.arraycopy(this.digestBuffer, 0, param2ArrayOfbyte, param2Int1, this.digestBuffer.length);
/* 529 */         reset();
/* 530 */         return this.digestBuffer.length;
/*     */       }
/*     */       
/*     */       protected void engineReset() {
/* 534 */         this.ofs = 0;
/*     */       }
/*     */       protected final int engineGetDigestLength() {
/* 537 */         return this.digestBuffer.length;
/*     */       }
/*     */     }
/*     */     
/*     */     public RawDSA() throws NoSuchAlgorithmException {
/* 542 */       super(new NullDigest20());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */