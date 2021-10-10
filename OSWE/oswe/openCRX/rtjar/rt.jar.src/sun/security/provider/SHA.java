/*     */ package sun.security.provider;
/*     */ 
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
/*     */ public final class SHA
/*     */   extends DigestBase
/*     */ {
/*     */   private int[] W;
/*     */   private int[] state;
/*     */   private static final int round1_kt = 1518500249;
/*     */   private static final int round2_kt = 1859775393;
/*     */   private static final int round3_kt = -1894007588;
/*     */   private static final int round4_kt = -899497514;
/*     */   
/*     */   public SHA() {
/*  63 */     super("SHA-1", 20, 64);
/*  64 */     this.state = new int[5];
/*  65 */     this.W = new int[80];
/*  66 */     resetHashes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  73 */     SHA sHA = (SHA)super.clone();
/*  74 */     sHA.state = (int[])sHA.state.clone();
/*  75 */     sHA.W = new int[80];
/*  76 */     return sHA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implReset() {
/*  84 */     resetHashes();
/*     */     
/*  86 */     Arrays.fill(this.W, 0);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/*  90 */     this.state[0] = 1732584193;
/*  91 */     this.state[1] = -271733879;
/*  92 */     this.state[2] = -1732584194;
/*  93 */     this.state[3] = 271733878;
/*  94 */     this.state[4] = -1009589776;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/* 101 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 103 */     int i = (int)this.bytesProcessed & 0x3F;
/* 104 */     int j = (i < 56) ? (56 - i) : (120 - i);
/* 105 */     engineUpdate(padding, 0, j);
/*     */     
/* 107 */     ByteArrayAccess.i2bBig4((int)(l >>> 32L), this.buffer, 56);
/* 108 */     ByteArrayAccess.i2bBig4((int)l, this.buffer, 60);
/* 109 */     implCompress(this.buffer, 0);
/*     */     
/* 111 */     ByteArrayAccess.i2bBig(this.state, 0, paramArrayOfbyte, paramInt, 20);
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
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 128 */     implCompressCheck(paramArrayOfbyte, paramInt);
/* 129 */     implCompress0(paramArrayOfbyte, paramInt);
/*     */   }
/*     */   
/*     */   private void implCompressCheck(byte[] paramArrayOfbyte, int paramInt) {
/* 133 */     Objects.requireNonNull(paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     ByteArrayAccess.b2iBig64(paramArrayOfbyte, paramInt, this.W);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void implCompress0(byte[] paramArrayOfbyte, int paramInt) {
/*     */     int i;
/* 150 */     for (i = 16; i <= 79; i++) {
/* 151 */       int i1 = this.W[i - 3] ^ this.W[i - 8] ^ this.W[i - 14] ^ this.W[i - 16];
/* 152 */       this.W[i] = i1 << 1 | i1 >>> 31;
/*     */     } 
/*     */     
/* 155 */     i = this.state[0];
/* 156 */     int j = this.state[1];
/* 157 */     int k = this.state[2];
/* 158 */     int m = this.state[3];
/* 159 */     int n = this.state[4];
/*     */     
/*     */     byte b;
/* 162 */     for (b = 0; b < 20; b++) {
/* 163 */       int i1 = (i << 5 | i >>> 27) + (j & k | (j ^ 0xFFFFFFFF) & m) + n + this.W[b] + 1518500249;
/*     */       
/* 165 */       n = m;
/* 166 */       m = k;
/* 167 */       k = j << 30 | j >>> 2;
/* 168 */       j = i;
/* 169 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 173 */     for (b = 20; b < 40; b++) {
/* 174 */       int i1 = (i << 5 | i >>> 27) + (j ^ k ^ m) + n + this.W[b] + 1859775393;
/*     */       
/* 176 */       n = m;
/* 177 */       m = k;
/* 178 */       k = j << 30 | j >>> 2;
/* 179 */       j = i;
/* 180 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     for (b = 40; b < 60; b++) {
/* 185 */       int i1 = (i << 5 | i >>> 27) + (j & k | j & m | k & m) + n + this.W[b] + -1894007588;
/*     */       
/* 187 */       n = m;
/* 188 */       m = k;
/* 189 */       k = j << 30 | j >>> 2;
/* 190 */       j = i;
/* 191 */       i = i1;
/*     */     } 
/*     */ 
/*     */     
/* 195 */     for (b = 60; b < 80; b++) {
/* 196 */       int i1 = (i << 5 | i >>> 27) + (j ^ k ^ m) + n + this.W[b] + -899497514;
/*     */       
/* 198 */       n = m;
/* 199 */       m = k;
/* 200 */       k = j << 30 | j >>> 2;
/* 201 */       j = i;
/* 202 */       i = i1;
/*     */     } 
/* 204 */     this.state[0] = this.state[0] + i;
/* 205 */     this.state[1] = this.state[1] + j;
/* 206 */     this.state[2] = this.state[2] + k;
/* 207 */     this.state[3] = this.state[3] + m;
/* 208 */     this.state[4] = this.state[4] + n;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/SHA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */