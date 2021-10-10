/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Provider;
/*     */ import java.security.ProviderException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MD4
/*     */   extends DigestBase
/*     */ {
/*     */   private int[] state;
/*     */   private int[] x;
/*     */   private static final int S11 = 3;
/*     */   private static final int S12 = 7;
/*     */   private static final int S13 = 11;
/*     */   private static final int S14 = 19;
/*     */   private static final int S21 = 3;
/*     */   private static final int S22 = 5;
/*     */   private static final int S23 = 9;
/*     */   private static final int S24 = 13;
/*     */   private static final int S31 = 3;
/*     */   private static final int S32 = 9;
/*     */   private static final int S33 = 11;
/*     */   private static final int S34 = 15;
/*  69 */   private static final Provider md4Provider = new Provider("MD4Provider", 1.8D, "MD4 MessageDigest") { private static final long serialVersionUID = -8850464997518327965L; }
/*     */   ;
/*     */   static {
/*  72 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*  74 */             MD4.md4Provider.put("MessageDigest.MD4", "sun.security.provider.MD4");
/*  75 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static MessageDigest getInstance() {
/*     */     try {
/*  82 */       return MessageDigest.getInstance("MD4", md4Provider);
/*  83 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */       
/*  85 */       throw new ProviderException(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MD4() {
/*  91 */     super("MD4", 16, 64);
/*  92 */     this.state = new int[4];
/*  93 */     this.x = new int[16];
/*  94 */     resetHashes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  99 */     MD4 mD4 = (MD4)super.clone();
/* 100 */     mD4.state = (int[])mD4.state.clone();
/* 101 */     mD4.x = new int[16];
/* 102 */     return mD4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implReset() {
/* 110 */     resetHashes();
/*     */     
/* 112 */     Arrays.fill(this.x, 0);
/*     */   }
/*     */   
/*     */   private void resetHashes() {
/* 116 */     this.state[0] = 1732584193;
/* 117 */     this.state[1] = -271733879;
/* 118 */     this.state[2] = -1732584194;
/* 119 */     this.state[3] = 271733878;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/* 128 */     long l = this.bytesProcessed << 3L;
/*     */     
/* 130 */     int i = (int)this.bytesProcessed & 0x3F;
/* 131 */     int j = (i < 56) ? (56 - i) : (120 - i);
/* 132 */     engineUpdate(padding, 0, j);
/*     */     
/* 134 */     ByteArrayAccess.i2bLittle4((int)l, this.buffer, 56);
/* 135 */     ByteArrayAccess.i2bLittle4((int)(l >>> 32L), this.buffer, 60);
/* 136 */     implCompress(this.buffer, 0);
/*     */     
/* 138 */     ByteArrayAccess.i2bLittle(this.state, 0, paramArrayOfbyte, paramInt, 16);
/*     */   }
/*     */   
/*     */   private static int FF(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 142 */     paramInt1 += (paramInt2 & paramInt3 | (paramInt2 ^ 0xFFFFFFFF) & paramInt4) + paramInt5;
/* 143 */     return paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6;
/*     */   }
/*     */   
/*     */   private static int GG(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 147 */     paramInt1 += (paramInt2 & paramInt3 | paramInt2 & paramInt4 | paramInt3 & paramInt4) + paramInt5 + 1518500249;
/* 148 */     return paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6;
/*     */   }
/*     */   
/*     */   private static int HH(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 152 */     paramInt1 += (paramInt2 ^ paramInt3 ^ paramInt4) + paramInt5 + 1859775393;
/* 153 */     return paramInt1 << paramInt6 | paramInt1 >>> 32 - paramInt6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/* 162 */     ByteArrayAccess.b2iLittle64(paramArrayOfbyte, paramInt, this.x);
/*     */     
/* 164 */     int i = this.state[0];
/* 165 */     int j = this.state[1];
/* 166 */     int k = this.state[2];
/* 167 */     int m = this.state[3];
/*     */ 
/*     */     
/* 170 */     i = FF(i, j, k, m, this.x[0], 3);
/* 171 */     m = FF(m, i, j, k, this.x[1], 7);
/* 172 */     k = FF(k, m, i, j, this.x[2], 11);
/* 173 */     j = FF(j, k, m, i, this.x[3], 19);
/* 174 */     i = FF(i, j, k, m, this.x[4], 3);
/* 175 */     m = FF(m, i, j, k, this.x[5], 7);
/* 176 */     k = FF(k, m, i, j, this.x[6], 11);
/* 177 */     j = FF(j, k, m, i, this.x[7], 19);
/* 178 */     i = FF(i, j, k, m, this.x[8], 3);
/* 179 */     m = FF(m, i, j, k, this.x[9], 7);
/* 180 */     k = FF(k, m, i, j, this.x[10], 11);
/* 181 */     j = FF(j, k, m, i, this.x[11], 19);
/* 182 */     i = FF(i, j, k, m, this.x[12], 3);
/* 183 */     m = FF(m, i, j, k, this.x[13], 7);
/* 184 */     k = FF(k, m, i, j, this.x[14], 11);
/* 185 */     j = FF(j, k, m, i, this.x[15], 19);
/*     */ 
/*     */     
/* 188 */     i = GG(i, j, k, m, this.x[0], 3);
/* 189 */     m = GG(m, i, j, k, this.x[4], 5);
/* 190 */     k = GG(k, m, i, j, this.x[8], 9);
/* 191 */     j = GG(j, k, m, i, this.x[12], 13);
/* 192 */     i = GG(i, j, k, m, this.x[1], 3);
/* 193 */     m = GG(m, i, j, k, this.x[5], 5);
/* 194 */     k = GG(k, m, i, j, this.x[9], 9);
/* 195 */     j = GG(j, k, m, i, this.x[13], 13);
/* 196 */     i = GG(i, j, k, m, this.x[2], 3);
/* 197 */     m = GG(m, i, j, k, this.x[6], 5);
/* 198 */     k = GG(k, m, i, j, this.x[10], 9);
/* 199 */     j = GG(j, k, m, i, this.x[14], 13);
/* 200 */     i = GG(i, j, k, m, this.x[3], 3);
/* 201 */     m = GG(m, i, j, k, this.x[7], 5);
/* 202 */     k = GG(k, m, i, j, this.x[11], 9);
/* 203 */     j = GG(j, k, m, i, this.x[15], 13);
/*     */ 
/*     */     
/* 206 */     i = HH(i, j, k, m, this.x[0], 3);
/* 207 */     m = HH(m, i, j, k, this.x[8], 9);
/* 208 */     k = HH(k, m, i, j, this.x[4], 11);
/* 209 */     j = HH(j, k, m, i, this.x[12], 15);
/* 210 */     i = HH(i, j, k, m, this.x[2], 3);
/* 211 */     m = HH(m, i, j, k, this.x[10], 9);
/* 212 */     k = HH(k, m, i, j, this.x[6], 11);
/* 213 */     j = HH(j, k, m, i, this.x[14], 15);
/* 214 */     i = HH(i, j, k, m, this.x[1], 3);
/* 215 */     m = HH(m, i, j, k, this.x[9], 9);
/* 216 */     k = HH(k, m, i, j, this.x[5], 11);
/* 217 */     j = HH(j, k, m, i, this.x[13], 15);
/* 218 */     i = HH(i, j, k, m, this.x[3], 3);
/* 219 */     m = HH(m, i, j, k, this.x[11], 9);
/* 220 */     k = HH(k, m, i, j, this.x[7], 11);
/* 221 */     j = HH(j, k, m, i, this.x[15], 15);
/*     */     
/* 223 */     this.state[0] = this.state[0] + i;
/* 224 */     this.state[1] = this.state[1] + j;
/* 225 */     this.state[2] = this.state[2] + k;
/* 226 */     this.state[3] = this.state[3] + m;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/MD4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */