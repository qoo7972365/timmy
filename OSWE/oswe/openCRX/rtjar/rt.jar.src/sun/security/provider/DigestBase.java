/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.security.DigestException;
/*     */ import java.security.MessageDigestSpi;
/*     */ import java.security.ProviderException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DigestBase
/*     */   extends MessageDigestSpi
/*     */   implements Cloneable
/*     */ {
/*     */   private byte[] oneByte;
/*     */   private final String algorithm;
/*     */   private final int digestLength;
/*     */   private final int blockSize;
/*     */   byte[] buffer;
/*     */   private int bufOfs;
/*     */   long bytesProcessed;
/*     */   
/*     */   DigestBase(String paramString, int paramInt1, int paramInt2) {
/*  81 */     this.algorithm = paramString;
/*  82 */     this.digestLength = paramInt1;
/*  83 */     this.blockSize = paramInt2;
/*  84 */     this.buffer = new byte[paramInt2];
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int engineGetDigestLength() {
/*  89 */     return this.digestLength;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineUpdate(byte paramByte) {
/*  94 */     if (this.oneByte == null) {
/*  95 */       this.oneByte = new byte[1];
/*     */     }
/*  97 */     this.oneByte[0] = paramByte;
/*  98 */     engineUpdate(this.oneByte, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 103 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/* 106 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 107 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 109 */     if (this.bytesProcessed < 0L) {
/* 110 */       engineReset();
/*     */     }
/* 112 */     this.bytesProcessed += paramInt2;
/*     */     
/* 114 */     if (this.bufOfs != 0) {
/* 115 */       int i = Math.min(paramInt2, this.blockSize - this.bufOfs);
/* 116 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, this.bufOfs, i);
/* 117 */       this.bufOfs += i;
/* 118 */       paramInt1 += i;
/* 119 */       paramInt2 -= i;
/* 120 */       if (this.bufOfs >= this.blockSize) {
/*     */         
/* 122 */         implCompress(this.buffer, 0);
/* 123 */         this.bufOfs = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     if (paramInt2 >= this.blockSize) {
/* 128 */       int i = paramInt1 + paramInt2;
/* 129 */       paramInt1 = implCompressMultiBlock(paramArrayOfbyte, paramInt1, i - this.blockSize);
/* 130 */       paramInt2 = i - paramInt1;
/*     */     } 
/*     */     
/* 133 */     if (paramInt2 > 0) {
/* 134 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.buffer, 0, paramInt2);
/* 135 */       this.bufOfs = paramInt2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int implCompressMultiBlock(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 141 */     implCompressMultiBlockCheck(paramArrayOfbyte, paramInt1, paramInt2);
/* 142 */     return implCompressMultiBlock0(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   private int implCompressMultiBlock0(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 146 */     for (; paramInt1 <= paramInt2; paramInt1 += this.blockSize) {
/* 147 */       implCompress(paramArrayOfbyte, paramInt1);
/*     */     }
/* 149 */     return paramInt1;
/*     */   }
/*     */   
/*     */   private void implCompressMultiBlockCheck(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 153 */     if (paramInt2 < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 158 */     Objects.requireNonNull(paramArrayOfbyte);
/*     */     
/* 160 */     if (paramInt1 < 0 || paramInt1 >= paramArrayOfbyte.length) {
/* 161 */       throw new ArrayIndexOutOfBoundsException(paramInt1);
/*     */     }
/*     */     
/* 164 */     int i = paramInt2 / this.blockSize * this.blockSize + this.blockSize - 1;
/* 165 */     if (i >= paramArrayOfbyte.length) {
/* 166 */       throw new ArrayIndexOutOfBoundsException(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void engineReset() {
/* 172 */     if (this.bytesProcessed == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     implReset();
/* 177 */     this.bufOfs = 0;
/* 178 */     this.bytesProcessed = 0L;
/* 179 */     Arrays.fill(this.buffer, (byte)0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final byte[] engineDigest() {
/* 184 */     byte[] arrayOfByte = new byte[this.digestLength];
/*     */     try {
/* 186 */       engineDigest(arrayOfByte, 0, arrayOfByte.length);
/* 187 */     } catch (DigestException digestException) {
/* 188 */       throw (ProviderException)(new ProviderException("Internal error"))
/* 189 */         .initCause(digestException);
/*     */     } 
/* 191 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int engineDigest(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DigestException {
/* 197 */     if (paramInt2 < this.digestLength) {
/* 198 */       throw new DigestException("Length must be at least " + this.digestLength + " for " + this.algorithm + "digests");
/*     */     }
/*     */     
/* 201 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2) {
/* 202 */       throw new DigestException("Buffer too short to store digest");
/*     */     }
/* 204 */     if (this.bytesProcessed < 0L) {
/* 205 */       engineReset();
/*     */     }
/* 207 */     implDigest(paramArrayOfbyte, paramInt1);
/* 208 */     this.bytesProcessed = -1L;
/* 209 */     return this.digestLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implCompress(byte[] paramArrayOfbyte, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implDigest(byte[] paramArrayOfbyte, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implReset();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 231 */     DigestBase digestBase = (DigestBase)super.clone();
/* 232 */     digestBase.buffer = (byte[])digestBase.buffer.clone();
/* 233 */     return digestBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   static final byte[] padding = new byte[136]; static {
/* 244 */     padding[0] = Byte.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DigestBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */