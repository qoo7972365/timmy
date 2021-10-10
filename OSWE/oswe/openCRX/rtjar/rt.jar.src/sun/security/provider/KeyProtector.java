/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import java.util.Arrays;
/*     */ import sun.security.pkcs.EncryptedPrivateKeyInfo;
/*     */ import sun.security.pkcs.PKCS8Key;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class KeyProtector
/*     */ {
/*     */   private static final int SALT_LEN = 20;
/*     */   private static final String DIGEST_ALG = "SHA";
/*     */   private static final int DIGEST_LEN = 20;
/*     */   private static final String KEY_PROTECTOR_OID = "1.3.6.1.4.1.42.2.17.1.1";
/*     */   private byte[] passwdBytes;
/*     */   private MessageDigest md;
/*     */   
/*     */   public KeyProtector(byte[] paramArrayOfbyte) throws NoSuchAlgorithmException {
/* 126 */     if (paramArrayOfbyte == null) {
/* 127 */       throw new IllegalArgumentException("password can't be null");
/*     */     }
/* 129 */     this.md = MessageDigest.getInstance("SHA");
/* 130 */     this.passwdBytes = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/* 138 */     if (this.passwdBytes != null) {
/* 139 */       Arrays.fill(this.passwdBytes, (byte)0);
/* 140 */       this.passwdBytes = null;
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
/*     */   public byte[] protect(Key paramKey) throws KeyStoreException {
/* 154 */     int j = 0;
/*     */     
/* 156 */     if (paramKey == null) {
/* 157 */       throw new IllegalArgumentException("plaintext key can't be null");
/*     */     }
/*     */     
/* 160 */     if (!"PKCS#8".equalsIgnoreCase(paramKey.getFormat())) {
/* 161 */       throw new KeyStoreException("Cannot get key bytes, not PKCS#8 encoded");
/*     */     }
/*     */ 
/*     */     
/* 165 */     byte[] arrayOfByte2 = paramKey.getEncoded();
/* 166 */     if (arrayOfByte2 == null) {
/* 167 */       throw new KeyStoreException("Cannot get key bytes, encoding not supported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     int i = arrayOfByte2.length / 20;
/* 173 */     if (arrayOfByte2.length % 20 != 0) {
/* 174 */       i++;
/*     */     }
/*     */     
/* 177 */     byte[] arrayOfByte3 = new byte[20];
/* 178 */     SecureRandom secureRandom = new SecureRandom();
/* 179 */     secureRandom.nextBytes(arrayOfByte3);
/*     */ 
/*     */     
/* 182 */     byte[] arrayOfByte4 = new byte[arrayOfByte2.length];
/*     */ 
/*     */     
/* 185 */     byte b1 = 0, b2 = 0; byte[] arrayOfByte1 = arrayOfByte3;
/* 186 */     for (; b1 < i; 
/* 187 */       b1++, b2 += 20) {
/* 188 */       this.md.update(this.passwdBytes);
/* 189 */       this.md.update(arrayOfByte1);
/* 190 */       arrayOfByte1 = this.md.digest();
/* 191 */       this.md.reset();
/*     */       
/* 193 */       if (b1 < i - 1) {
/* 194 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte4, b2, arrayOfByte1.length);
/*     */       } else {
/*     */         
/* 197 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte4, b2, arrayOfByte4.length - b2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 203 */     byte[] arrayOfByte5 = new byte[arrayOfByte2.length];
/* 204 */     for (b1 = 0; b1 < arrayOfByte5.length; b1++) {
/* 205 */       arrayOfByte5[b1] = (byte)(arrayOfByte2[b1] ^ arrayOfByte4[b1]);
/*     */     }
/*     */ 
/*     */     
/* 209 */     byte[] arrayOfByte6 = new byte[arrayOfByte3.length + arrayOfByte5.length + 20];
/* 210 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte6, j, arrayOfByte3.length);
/* 211 */     j += arrayOfByte3.length;
/* 212 */     System.arraycopy(arrayOfByte5, 0, arrayOfByte6, j, arrayOfByte5.length);
/* 213 */     j += arrayOfByte5.length;
/*     */ 
/*     */     
/* 216 */     this.md.update(this.passwdBytes);
/* 217 */     Arrays.fill(this.passwdBytes, (byte)0);
/* 218 */     this.passwdBytes = null;
/* 219 */     this.md.update(arrayOfByte2);
/* 220 */     arrayOfByte1 = this.md.digest();
/* 221 */     this.md.reset();
/* 222 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte6, j, arrayOfByte1.length);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 228 */       AlgorithmId algorithmId = new AlgorithmId(new ObjectIdentifier("1.3.6.1.4.1.42.2.17.1.1"));
/* 229 */       return (new EncryptedPrivateKeyInfo(algorithmId, arrayOfByte6)).getEncoded();
/* 230 */     } catch (IOException iOException) {
/* 231 */       throw new KeyStoreException(iOException.getMessage());
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
/*     */   public Key recover(EncryptedPrivateKeyInfo paramEncryptedPrivateKeyInfo) throws UnrecoverableKeyException {
/* 249 */     AlgorithmId algorithmId = paramEncryptedPrivateKeyInfo.getAlgorithm();
/* 250 */     if (!algorithmId.getOID().toString().equals("1.3.6.1.4.1.42.2.17.1.1")) {
/* 251 */       throw new UnrecoverableKeyException("Unsupported key protection algorithm");
/*     */     }
/*     */ 
/*     */     
/* 255 */     byte[] arrayOfByte2 = paramEncryptedPrivateKeyInfo.getEncryptedData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     byte[] arrayOfByte3 = new byte[20];
/* 262 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, 20);
/*     */ 
/*     */     
/* 265 */     int j = arrayOfByte2.length - 20 - 20;
/* 266 */     int i = j / 20;
/* 267 */     if (j % 20 != 0) i++;
/*     */ 
/*     */     
/* 270 */     byte[] arrayOfByte4 = new byte[j];
/* 271 */     System.arraycopy(arrayOfByte2, 20, arrayOfByte4, 0, j);
/*     */ 
/*     */     
/* 274 */     byte[] arrayOfByte5 = new byte[arrayOfByte4.length];
/*     */ 
/*     */     
/* 277 */     byte b1 = 0, b2 = 0; byte[] arrayOfByte1 = arrayOfByte3;
/* 278 */     for (; b1 < i; 
/* 279 */       b1++, b2 += 20) {
/* 280 */       this.md.update(this.passwdBytes);
/* 281 */       this.md.update(arrayOfByte1);
/* 282 */       arrayOfByte1 = this.md.digest();
/* 283 */       this.md.reset();
/*     */       
/* 285 */       if (b1 < i - 1) {
/* 286 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte5, b2, arrayOfByte1.length);
/*     */       } else {
/*     */         
/* 289 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte5, b2, arrayOfByte5.length - b2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 295 */     byte[] arrayOfByte6 = new byte[arrayOfByte4.length];
/* 296 */     for (b1 = 0; b1 < arrayOfByte6.length; b1++) {
/* 297 */       arrayOfByte6[b1] = (byte)(arrayOfByte4[b1] ^ arrayOfByte5[b1]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     this.md.update(this.passwdBytes);
/* 308 */     Arrays.fill(this.passwdBytes, (byte)0);
/* 309 */     this.passwdBytes = null;
/* 310 */     this.md.update(arrayOfByte6);
/* 311 */     arrayOfByte1 = this.md.digest();
/* 312 */     this.md.reset();
/* 313 */     for (b1 = 0; b1 < arrayOfByte1.length; b1++) {
/* 314 */       if (arrayOfByte1[b1] != arrayOfByte2[20 + j + b1]) {
/* 315 */         throw new UnrecoverableKeyException("Cannot recover key");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 323 */       return PKCS8Key.parseKey(new DerValue(arrayOfByte6));
/* 324 */     } catch (IOException iOException) {
/* 325 */       throw new UnrecoverableKeyException(iOException.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/KeyProtector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */