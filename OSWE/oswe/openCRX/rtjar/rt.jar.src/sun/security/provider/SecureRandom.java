/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.SecureRandomSpi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecureRandom
/*     */   extends SecureRandomSpi
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3581829991155417889L;
/*     */   private static final int DIGEST_SIZE = 20;
/*     */   private transient MessageDigest digest;
/*     */   private byte[] state;
/*     */   private byte[] remainder;
/*     */   private int remCount;
/*     */   
/*     */   public SecureRandom() {
/*  79 */     init(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SecureRandom(byte[] paramArrayOfbyte) {
/*  89 */     init(paramArrayOfbyte);
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
/*     */   private void init(byte[] paramArrayOfbyte) {
/*     */     try {
/* 102 */       this.digest = MessageDigest.getInstance("SHA", "SUN");
/* 103 */     } catch (NoSuchProviderException|NoSuchAlgorithmException noSuchProviderException) {
/*     */       
/*     */       try {
/* 106 */         this.digest = MessageDigest.getInstance("SHA");
/* 107 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 108 */         throw new InternalError("internal error: SHA-1 not available.", noSuchAlgorithmException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 113 */     if (paramArrayOfbyte != null) {
/* 114 */       engineSetSeed(paramArrayOfbyte);
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
/*     */   public byte[] engineGenerateSeed(int paramInt) {
/* 138 */     byte[] arrayOfByte = new byte[paramInt];
/* 139 */     SeedGenerator.generateSeed(arrayOfByte);
/* 140 */     return arrayOfByte;
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
/*     */   public synchronized void engineSetSeed(byte[] paramArrayOfbyte) {
/* 152 */     if (this.state != null) {
/* 153 */       this.digest.update(this.state);
/* 154 */       for (byte b = 0; b < this.state.length; b++) {
/* 155 */         this.state[b] = 0;
/*     */       }
/*     */     } 
/* 158 */     this.state = this.digest.digest(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   private static void updateState(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 162 */     int i = 1;
/*     */ 
/*     */     
/* 165 */     int j = 0;
/*     */ 
/*     */     
/* 168 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/*     */       
/* 170 */       int k = paramArrayOfbyte1[b] + paramArrayOfbyte2[b] + i;
/*     */       
/* 172 */       byte b1 = (byte)k;
/*     */       
/* 174 */       j |= (paramArrayOfbyte1[b] != b1) ? 1 : 0;
/* 175 */       paramArrayOfbyte1[b] = b1;
/*     */       
/* 177 */       i = k >> 8;
/*     */     } 
/*     */ 
/*     */     
/* 181 */     if (j == 0) {
/* 182 */       paramArrayOfbyte1[0] = (byte)(paramArrayOfbyte1[0] + 1);
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
/*     */   private static class SeederHolder
/*     */   {
/* 201 */     private static final SecureRandom seeder = new SecureRandom(SeedGenerator.getSystemEntropy()); static {
/* 202 */       byte[] arrayOfByte = new byte[20];
/* 203 */       SeedGenerator.generateSeed(arrayOfByte);
/* 204 */       seeder.engineSetSeed(arrayOfByte);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void engineNextBytes(byte[] paramArrayOfbyte) {
/* 215 */     int i = 0;
/*     */     
/* 217 */     byte[] arrayOfByte = this.remainder;
/*     */     
/* 219 */     if (this.state == null) {
/* 220 */       byte[] arrayOfByte1 = new byte[20];
/* 221 */       SeederHolder.seeder.engineNextBytes(arrayOfByte1);
/* 222 */       this.state = this.digest.digest(arrayOfByte1);
/*     */     } 
/*     */ 
/*     */     
/* 226 */     int j = this.remCount;
/* 227 */     if (j > 0) {
/*     */       
/* 229 */       int k = (paramArrayOfbyte.length - i < 20 - j) ? (paramArrayOfbyte.length - i) : (20 - j);
/*     */ 
/*     */       
/* 232 */       for (byte b = 0; b < k; b++) {
/* 233 */         paramArrayOfbyte[b] = arrayOfByte[j];
/* 234 */         arrayOfByte[j++] = 0;
/*     */       } 
/* 236 */       this.remCount += k;
/* 237 */       i += k;
/*     */     } 
/*     */ 
/*     */     
/* 241 */     while (i < paramArrayOfbyte.length) {
/*     */       
/* 243 */       this.digest.update(this.state);
/* 244 */       arrayOfByte = this.digest.digest();
/* 245 */       updateState(this.state, arrayOfByte);
/*     */ 
/*     */       
/* 248 */       byte b1 = (paramArrayOfbyte.length - i > 20) ? 20 : (paramArrayOfbyte.length - i);
/*     */ 
/*     */       
/* 251 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 252 */         paramArrayOfbyte[i++] = arrayOfByte[b2];
/* 253 */         arrayOfByte[b2] = 0;
/*     */       } 
/* 255 */       this.remCount += b1;
/*     */     } 
/*     */ 
/*     */     
/* 259 */     this.remainder = arrayOfByte;
/* 260 */     this.remCount %= 20;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 276 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 283 */       this.digest = MessageDigest.getInstance("SHA", "SUN");
/* 284 */     } catch (NoSuchProviderException|NoSuchAlgorithmException noSuchProviderException) {
/*     */       
/*     */       try {
/* 287 */         this.digest = MessageDigest.getInstance("SHA");
/* 288 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 289 */         throw new InternalError("internal error: SHA-1 not available.", noSuchAlgorithmException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/SecureRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */