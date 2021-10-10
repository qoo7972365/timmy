/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UUID
/*     */   implements Serializable, Comparable<UUID>
/*     */ {
/*     */   private static final long serialVersionUID = -4856846361193249489L;
/*     */   private final long mostSigBits;
/*     */   private final long leastSigBits;
/*     */   
/*     */   private static class Holder
/*     */   {
/*  96 */     static final SecureRandom numberGenerator = new SecureRandom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID(byte[] paramArrayOfbyte) {
/* 105 */     long l1 = 0L;
/* 106 */     long l2 = 0L;
/* 107 */     assert paramArrayOfbyte.length == 16 : "data must be 16 bytes in length"; byte b;
/* 108 */     for (b = 0; b < 8; b++)
/* 109 */       l1 = l1 << 8L | (paramArrayOfbyte[b] & 0xFF); 
/* 110 */     for (b = 8; b < 16; b++)
/* 111 */       l2 = l2 << 8L | (paramArrayOfbyte[b] & 0xFF); 
/* 112 */     this.mostSigBits = l1;
/* 113 */     this.leastSigBits = l2;
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
/*     */   public UUID(long paramLong1, long paramLong2) {
/* 129 */     this.mostSigBits = paramLong1;
/* 130 */     this.leastSigBits = paramLong2;
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
/*     */   public static UUID randomUUID() {
/* 142 */     SecureRandom secureRandom = Holder.numberGenerator;
/*     */     
/* 144 */     byte[] arrayOfByte = new byte[16];
/* 145 */     secureRandom.nextBytes(arrayOfByte);
/* 146 */     arrayOfByte[6] = (byte)(arrayOfByte[6] & 0xF);
/* 147 */     arrayOfByte[6] = (byte)(arrayOfByte[6] | 0x40);
/* 148 */     arrayOfByte[8] = (byte)(arrayOfByte[8] & 0x3F);
/* 149 */     arrayOfByte[8] = (byte)(arrayOfByte[8] | 0x80);
/* 150 */     return new UUID(arrayOfByte);
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
/*     */   public static UUID nameUUIDFromBytes(byte[] paramArrayOfbyte) {
/*     */     MessageDigest messageDigest;
/*     */     try {
/* 165 */       messageDigest = MessageDigest.getInstance("MD5");
/* 166 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 167 */       throw new InternalError("MD5 not supported", noSuchAlgorithmException);
/*     */     } 
/* 169 */     byte[] arrayOfByte = messageDigest.digest(paramArrayOfbyte);
/* 170 */     arrayOfByte[6] = (byte)(arrayOfByte[6] & 0xF);
/* 171 */     arrayOfByte[6] = (byte)(arrayOfByte[6] | 0x30);
/* 172 */     arrayOfByte[8] = (byte)(arrayOfByte[8] & 0x3F);
/* 173 */     arrayOfByte[8] = (byte)(arrayOfByte[8] | 0x80);
/* 174 */     return new UUID(arrayOfByte);
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
/*     */   public static UUID fromString(String paramString) {
/* 192 */     String[] arrayOfString = paramString.split("-");
/* 193 */     if (arrayOfString.length != 5)
/* 194 */       throw new IllegalArgumentException("Invalid UUID string: " + paramString); 
/* 195 */     for (byte b = 0; b < 5; b++) {
/* 196 */       arrayOfString[b] = "0x" + arrayOfString[b];
/*     */     }
/* 198 */     long l1 = Long.decode(arrayOfString[0]).longValue();
/* 199 */     l1 <<= 16L;
/* 200 */     l1 |= Long.decode(arrayOfString[1]).longValue();
/* 201 */     l1 <<= 16L;
/* 202 */     l1 |= Long.decode(arrayOfString[2]).longValue();
/*     */     
/* 204 */     long l2 = Long.decode(arrayOfString[3]).longValue();
/* 205 */     l2 <<= 48L;
/* 206 */     l2 |= Long.decode(arrayOfString[4]).longValue();
/*     */     
/* 208 */     return new UUID(l1, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLeastSignificantBits() {
/* 219 */     return this.leastSigBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMostSignificantBits() {
/* 228 */     return this.mostSigBits;
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
/*     */   public int version() {
/* 247 */     return (int)(this.mostSigBits >> 12L & 0xFL);
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
/*     */   public int variant() {
/* 271 */     return (int)(this.leastSigBits >>> (int)(64L - (this.leastSigBits >>> 62L)) & this.leastSigBits >> 63L);
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
/*     */   public long timestamp() {
/* 292 */     if (version() != 1) {
/* 293 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 296 */     return (this.mostSigBits & 0xFFFL) << 48L | (this.mostSigBits >> 16L & 0xFFFFL) << 32L | this.mostSigBits >>> 32L;
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
/*     */   public int clockSequence() {
/* 318 */     if (version() != 1) {
/* 319 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 322 */     return (int)((this.leastSigBits & 0x3FFF000000000000L) >>> 48L);
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
/*     */   public long node() {
/* 342 */     if (version() != 1) {
/* 343 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/*     */     
/* 346 */     return this.leastSigBits & 0xFFFFFFFFFFFFL;
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
/*     */   public String toString() {
/* 376 */     return digits(this.mostSigBits >> 32L, 8) + "-" + 
/* 377 */       digits(this.mostSigBits >> 16L, 4) + "-" + 
/* 378 */       digits(this.mostSigBits, 4) + "-" + 
/* 379 */       digits(this.leastSigBits >> 48L, 4) + "-" + 
/* 380 */       digits(this.leastSigBits, 12);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String digits(long paramLong, int paramInt) {
/* 385 */     long l = 1L << paramInt * 4;
/* 386 */     return Long.toHexString(l | paramLong & l - 1L).substring(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 395 */     long l = this.mostSigBits ^ this.leastSigBits;
/* 396 */     return (int)(l >> 32L) ^ (int)l;
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
/*     */   public boolean equals(Object paramObject) {
/* 412 */     if (null == paramObject || paramObject.getClass() != UUID.class)
/* 413 */       return false; 
/* 414 */     UUID uUID = (UUID)paramObject;
/* 415 */     return (this.mostSigBits == uUID.mostSigBits && this.leastSigBits == uUID.leastSigBits);
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
/*     */   public int compareTo(UUID paramUUID) {
/* 438 */     return (this.mostSigBits < paramUUID.mostSigBits) ? -1 : ((this.mostSigBits > paramUUID.mostSigBits) ? 1 : ((this.leastSigBits < paramUUID.leastSigBits) ? -1 : ((this.leastSigBits > paramUUID.leastSigBits) ? 1 : 0)));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/UUID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */