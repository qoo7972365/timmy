/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.ProviderException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.SignatureException;
/*     */ import java.security.SignatureSpi;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
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
/*     */ 
/*     */ public abstract class RSASignature
/*     */   extends SignatureSpi
/*     */ {
/*     */   private static final int baseLength = 8;
/*     */   private final ObjectIdentifier digestOID;
/*     */   private final int encodedLength;
/*     */   private final MessageDigest md;
/*     */   private boolean digestReset;
/*     */   private RSAPrivateKey privateKey;
/*     */   private RSAPublicKey publicKey;
/*     */   private RSAPadding padding;
/*     */   
/*     */   RSASignature(String paramString, ObjectIdentifier paramObjectIdentifier, int paramInt) {
/*  77 */     this.digestOID = paramObjectIdentifier;
/*     */     try {
/*  79 */       this.md = MessageDigest.getInstance(paramString);
/*  80 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  81 */       throw new ProviderException(noSuchAlgorithmException);
/*     */     } 
/*  83 */     this.digestReset = true;
/*  84 */     this.encodedLength = 8 + paramInt + this.md.getDigestLength();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitVerify(PublicKey paramPublicKey) throws InvalidKeyException {
/*  90 */     RSAPublicKey rSAPublicKey = (RSAPublicKey)RSAKeyFactory.toRSAKey(paramPublicKey);
/*  91 */     this.privateKey = null;
/*  92 */     this.publicKey = rSAPublicKey;
/*  93 */     initCommon(rSAPublicKey, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(PrivateKey paramPrivateKey) throws InvalidKeyException {
/*  99 */     engineInitSign(paramPrivateKey, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
/* 106 */     RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)RSAKeyFactory.toRSAKey(paramPrivateKey);
/* 107 */     this.privateKey = rSAPrivateKey;
/* 108 */     this.publicKey = null;
/* 109 */     initCommon(rSAPrivateKey, paramSecureRandom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initCommon(RSAKey paramRSAKey, SecureRandom paramSecureRandom) throws InvalidKeyException {
/* 117 */     resetDigest();
/* 118 */     int i = RSACore.getByteLength(paramRSAKey);
/*     */     try {
/* 120 */       this
/* 121 */         .padding = RSAPadding.getInstance(1, i, paramSecureRandom);
/* 122 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 123 */       throw new InvalidKeyException(invalidAlgorithmParameterException.getMessage());
/*     */     } 
/* 125 */     int j = this.padding.getMaxDataSize();
/* 126 */     if (this.encodedLength > j) {
/* 127 */       throw new InvalidKeyException("Key is too short for this signature algorithm");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetDigest() {
/* 136 */     if (!this.digestReset) {
/* 137 */       this.md.reset();
/* 138 */       this.digestReset = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getDigestValue() {
/* 146 */     this.digestReset = true;
/* 147 */     return this.md.digest();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte paramByte) throws SignatureException {
/* 152 */     this.md.update(paramByte);
/* 153 */     this.digestReset = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SignatureException {
/* 159 */     this.md.update(paramArrayOfbyte, paramInt1, paramInt2);
/* 160 */     this.digestReset = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void engineUpdate(ByteBuffer paramByteBuffer) {
/* 165 */     this.md.update(paramByteBuffer);
/* 166 */     this.digestReset = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] engineSign() throws SignatureException {
/* 171 */     byte[] arrayOfByte = getDigestValue();
/*     */     try {
/* 173 */       byte[] arrayOfByte1 = encodeSignature(this.digestOID, arrayOfByte);
/* 174 */       byte[] arrayOfByte2 = this.padding.pad(arrayOfByte1);
/* 175 */       return RSACore.rsa(arrayOfByte2, this.privateKey, true);
/*     */     }
/* 177 */     catch (GeneralSecurityException generalSecurityException) {
/* 178 */       throw new SignatureException("Could not sign data", generalSecurityException);
/* 179 */     } catch (IOException iOException) {
/* 180 */       throw new SignatureException("Could not encode data", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean engineVerify(byte[] paramArrayOfbyte) throws SignatureException {
/* 186 */     if (paramArrayOfbyte.length != RSACore.getByteLength(this.publicKey)) {
/* 187 */       throw new SignatureException("Signature length not correct: got " + paramArrayOfbyte.length + " but was expecting " + 
/*     */           
/* 189 */           RSACore.getByteLength(this.publicKey));
/*     */     }
/* 191 */     byte[] arrayOfByte = getDigestValue();
/*     */     try {
/* 193 */       byte[] arrayOfByte1 = RSACore.rsa(paramArrayOfbyte, this.publicKey);
/* 194 */       byte[] arrayOfByte2 = this.padding.unpad(arrayOfByte1);
/* 195 */       byte[] arrayOfByte3 = decodeSignature(this.digestOID, arrayOfByte2);
/* 196 */       return MessageDigest.isEqual(arrayOfByte, arrayOfByte3);
/* 197 */     } catch (BadPaddingException badPaddingException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       return false;
/* 203 */     } catch (IOException iOException) {
/* 204 */       throw new SignatureException("Signature encoding error", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encodeSignature(ObjectIdentifier paramObjectIdentifier, byte[] paramArrayOfbyte) throws IOException {
/* 214 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 215 */     (new AlgorithmId(paramObjectIdentifier)).encode(derOutputStream);
/* 216 */     derOutputStream.putOctetString(paramArrayOfbyte);
/*     */     
/* 218 */     DerValue derValue = new DerValue((byte)48, derOutputStream.toByteArray());
/* 219 */     return derValue.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeSignature(ObjectIdentifier paramObjectIdentifier, byte[] paramArrayOfbyte) throws IOException {
/* 229 */     DerInputStream derInputStream = new DerInputStream(paramArrayOfbyte, 0, paramArrayOfbyte.length, false);
/* 230 */     DerValue[] arrayOfDerValue = derInputStream.getSequence(2);
/* 231 */     if (arrayOfDerValue.length != 2 || derInputStream.available() != 0) {
/* 232 */       throw new IOException("SEQUENCE length error");
/*     */     }
/* 234 */     AlgorithmId algorithmId = AlgorithmId.parse(arrayOfDerValue[0]);
/* 235 */     if (!algorithmId.getOID().equals(paramObjectIdentifier)) {
/* 236 */       throw new IOException("ObjectIdentifier mismatch: " + algorithmId
/* 237 */           .getOID());
/*     */     }
/* 239 */     if (algorithmId.getEncodedParams() != null) {
/* 240 */       throw new IOException("Unexpected AlgorithmId parameters");
/*     */     }
/* 242 */     return arrayOfDerValue[1].getOctetString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void engineSetParameter(String paramString, Object paramObject) throws InvalidParameterException {
/* 250 */     throw new UnsupportedOperationException("setParameter() not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Object engineGetParameter(String paramString) throws InvalidParameterException {
/* 257 */     throw new UnsupportedOperationException("getParameter() not supported");
/*     */   }
/*     */   
/*     */   public static final class MD2withRSA
/*     */     extends RSASignature {
/*     */     public MD2withRSA() {
/* 263 */       super("MD2", AlgorithmId.MD2_oid, 10);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class MD5withRSA
/*     */     extends RSASignature {
/*     */     public MD5withRSA() {
/* 270 */       super("MD5", AlgorithmId.MD5_oid, 10);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SHA1withRSA
/*     */     extends RSASignature {
/*     */     public SHA1withRSA() {
/* 277 */       super("SHA-1", AlgorithmId.SHA_oid, 7);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SHA224withRSA
/*     */     extends RSASignature {
/*     */     public SHA224withRSA() {
/* 284 */       super("SHA-224", AlgorithmId.SHA224_oid, 11);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SHA256withRSA
/*     */     extends RSASignature {
/*     */     public SHA256withRSA() {
/* 291 */       super("SHA-256", AlgorithmId.SHA256_oid, 11);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SHA384withRSA
/*     */     extends RSASignature {
/*     */     public SHA384withRSA() {
/* 298 */       super("SHA-384", AlgorithmId.SHA384_oid, 11);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SHA512withRSA
/*     */     extends RSASignature {
/*     */     public SHA512withRSA() {
/* 305 */       super("SHA-512", AlgorithmId.SHA512_oid, 11);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSASignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */