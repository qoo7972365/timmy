/*     */ package sun.security.provider;
/*     */ 
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
/*     */ 
/*     */ public final class MD2
/*     */   extends DigestBase
/*     */ {
/*     */   private int[] X;
/*     */   private int[] C;
/*     */   private byte[] cBytes;
/*     */   
/*     */   public MD2() {
/*  55 */     super("MD2", 16, 16);
/*  56 */     this.X = new int[48];
/*  57 */     this.C = new int[16];
/*  58 */     this.cBytes = new byte[16];
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  62 */     MD2 mD2 = (MD2)super.clone();
/*  63 */     mD2.X = (int[])mD2.X.clone();
/*  64 */     mD2.C = (int[])mD2.C.clone();
/*  65 */     mD2.cBytes = new byte[16];
/*  66 */     return mD2;
/*     */   }
/*     */ 
/*     */   
/*     */   void implReset() {
/*  71 */     Arrays.fill(this.X, 0);
/*  72 */     Arrays.fill(this.C, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   void implDigest(byte[] paramArrayOfbyte, int paramInt) {
/*  77 */     int i = 16 - ((int)this.bytesProcessed & 0xF);
/*  78 */     engineUpdate(PADDING[i], 0, i); byte b;
/*  79 */     for (b = 0; b < 16; b++) {
/*  80 */       this.cBytes[b] = (byte)this.C[b];
/*     */     }
/*  82 */     implCompress(this.cBytes, 0);
/*  83 */     for (b = 0; b < 16; b++) {
/*  84 */       paramArrayOfbyte[paramInt + b] = (byte)this.X[b];
/*     */     }
/*     */   }
/*     */   
/*     */   void implCompress(byte[] paramArrayOfbyte, int paramInt) {
/*     */     int i;
/*  90 */     for (i = 0; i < 16; i++) {
/*  91 */       int j = paramArrayOfbyte[paramInt + i] & 0xFF;
/*  92 */       this.X[16 + i] = j;
/*  93 */       this.X[32 + i] = j ^ this.X[i];
/*     */     } 
/*     */ 
/*     */     
/*  97 */     i = this.C[15]; byte b;
/*  98 */     for (b = 0; b < 16; b++) {
/*  99 */       i = this.C[b] = this.C[b] ^ S[this.X[16 + b] ^ i];
/*     */     }
/*     */     
/* 102 */     i = 0;
/* 103 */     for (b = 0; b < 18; b++) {
/* 104 */       for (byte b1 = 0; b1 < 48; b1++) {
/* 105 */         i = this.X[b1] = this.X[b1] ^ S[i];
/*     */       }
/* 107 */       i = i + b & 0xFF;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 112 */   private static final int[] S = new int[] { 41, 46, 67, 201, 162, 216, 124, 1, 61, 54, 84, 161, 236, 240, 6, 19, 98, 167, 5, 243, 192, 199, 115, 140, 152, 147, 43, 217, 188, 76, 130, 202, 30, 155, 87, 60, 253, 212, 224, 22, 103, 66, 111, 24, 138, 23, 229, 18, 190, 78, 196, 214, 218, 158, 222, 73, 160, 251, 245, 142, 187, 47, 238, 122, 169, 104, 121, 145, 21, 178, 7, 63, 148, 194, 16, 137, 11, 34, 95, 33, 128, 127, 93, 154, 90, 144, 50, 39, 53, 62, 204, 231, 191, 247, 151, 3, 255, 25, 48, 179, 72, 165, 181, 209, 215, 94, 146, 42, 172, 86, 170, 198, 79, 184, 56, 210, 150, 164, 125, 182, 118, 252, 107, 226, 156, 116, 4, 241, 69, 157, 112, 89, 100, 113, 135, 32, 134, 91, 207, 101, 230, 45, 168, 2, 27, 96, 37, 173, 174, 176, 185, 246, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, 163, 35, 221, 81, 175, 58, 195, 92, 249, 206, 186, 197, 234, 38, 44, 83, 13, 110, 133, 40, 132, 9, 211, 223, 205, 244, 65, 129, 77, 82, 106, 220, 55, 200, 108, 193, 171, 250, 36, 225, 123, 8, 12, 189, 177, 74, 120, 136, 149, 139, 227, 99, 232, 109, 233, 203, 213, 254, 59, 0, 29, 57, 242, 239, 183, 14, 102, 88, 208, 228, 166, 119, 114, 248, 235, 117, 75, 10, 49, 68, 80, 180, 143, 237, 31, 26, 219, 153, 141, 51, 159, 17, 131, 20 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private static final byte[][] PADDING = new byte[17][]; static {
/* 140 */     for (byte b = 1; b < 17; b++) {
/* 141 */       byte[] arrayOfByte = new byte[b];
/* 142 */       Arrays.fill(arrayOfByte, (byte)b);
/* 143 */       PADDING[b] = arrayOfByte;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/MD2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */