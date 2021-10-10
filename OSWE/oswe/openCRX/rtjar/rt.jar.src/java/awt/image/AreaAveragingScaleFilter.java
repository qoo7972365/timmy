/*     */ package java.awt.image;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AreaAveragingScaleFilter
/*     */   extends ReplicateScaleFilter
/*     */ {
/*  61 */   private static final ColorModel rgbmodel = ColorModel.getRGBdefault();
/*     */   
/*     */   private static final int neededHints = 6;
/*     */   
/*     */   private boolean passthrough;
/*     */   
/*     */   private float[] reds;
/*     */   
/*     */   private float[] greens;
/*     */   
/*     */   private float[] blues;
/*     */   private float[] alphas;
/*     */   private int savedy;
/*     */   private int savedyrem;
/*     */   
/*     */   public AreaAveragingScaleFilter(int paramInt1, int paramInt2) {
/*  77 */     super(paramInt1, paramInt2);
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
/*     */   public void setHints(int paramInt) {
/*  93 */     this.passthrough = ((paramInt & 0x6) != 6);
/*  94 */     super.setHints(paramInt);
/*     */   }
/*     */   
/*     */   private void makeAccumBuffers() {
/*  98 */     this.reds = new float[this.destWidth];
/*  99 */     this.greens = new float[this.destWidth];
/* 100 */     this.blues = new float[this.destWidth];
/* 101 */     this.alphas = new float[this.destWidth];
/*     */   }
/*     */   
/*     */   private int[] calcRow() {
/* 105 */     float f = this.srcWidth * this.srcHeight;
/* 106 */     if (this.outpixbuf == null || !(this.outpixbuf instanceof int[])) {
/* 107 */       this.outpixbuf = new int[this.destWidth];
/*     */     }
/* 109 */     int[] arrayOfInt = (int[])this.outpixbuf;
/* 110 */     for (byte b = 0; b < this.destWidth; b++) {
/* 111 */       float f1 = f;
/* 112 */       int i = Math.round(this.alphas[b] / f1);
/* 113 */       if (i <= 0) {
/* 114 */         i = 0;
/* 115 */       } else if (i >= 255) {
/* 116 */         i = 255;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 121 */         f1 = this.alphas[b] / 255.0F;
/*     */       } 
/* 123 */       int j = Math.round(this.reds[b] / f1);
/* 124 */       int k = Math.round(this.greens[b] / f1);
/* 125 */       int m = Math.round(this.blues[b] / f1);
/* 126 */       if (j < 0) { j = 0; } else if (j > 255) { j = 255; }
/* 127 */        if (k < 0) { k = 0; } else if (k > 255) { k = 255; }
/* 128 */        if (m < 0) { m = 0; } else if (m > 255) { m = 255; }
/* 129 */        arrayOfInt[b] = i << 24 | j << 16 | k << 8 | m;
/*     */     } 
/* 131 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   private void accumPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, Object paramObject, int paramInt5, int paramInt6) {
/*     */     int k, m;
/* 137 */     if (this.reds == null) {
/* 138 */       makeAccumBuffers();
/*     */     }
/* 140 */     int i = paramInt2;
/* 141 */     int j = this.destHeight;
/*     */     
/* 143 */     if (i == 0) {
/* 144 */       k = 0;
/* 145 */       m = 0;
/*     */     } else {
/* 147 */       k = this.savedy;
/* 148 */       m = this.savedyrem;
/*     */     } 
/* 150 */     while (i < paramInt2 + paramInt4) {
/*     */       int n;
/* 152 */       if (m == 0) {
/* 153 */         for (byte b = 0; b < this.destWidth; b++) {
/* 154 */           this.blues[b] = 0.0F; this.greens[b] = 0.0F; this.reds[b] = 0.0F; this.alphas[b] = 0.0F;
/*     */         } 
/* 156 */         m = this.srcHeight;
/*     */       } 
/* 158 */       if (j < m) {
/* 159 */         n = j;
/*     */       } else {
/* 161 */         n = m;
/*     */       } 
/* 163 */       byte b1 = 0;
/* 164 */       byte b2 = 0;
/* 165 */       int i1 = 0;
/* 166 */       int i2 = this.srcWidth;
/* 167 */       float f1 = 0.0F, f2 = 0.0F, f3 = 0.0F, f4 = 0.0F;
/* 168 */       while (b1 < paramInt3) {
/* 169 */         int i3; if (!i1) {
/* 170 */           i1 = this.destWidth;
/*     */           
/* 172 */           if (paramObject instanceof byte[]) {
/* 173 */             i3 = ((byte[])paramObject)[paramInt5 + b1] & 0xFF;
/*     */           } else {
/* 175 */             i3 = ((int[])paramObject)[paramInt5 + b1];
/*     */           } 
/*     */           
/* 178 */           i3 = paramColorModel.getRGB(i3);
/* 179 */           f1 = (i3 >>> 24);
/* 180 */           f2 = (i3 >> 16 & 0xFF);
/* 181 */           f3 = (i3 >> 8 & 0xFF);
/* 182 */           f4 = (i3 & 0xFF);
/*     */           
/* 184 */           if (f1 != 255.0F) {
/* 185 */             float f5 = f1 / 255.0F;
/* 186 */             f2 *= f5;
/* 187 */             f3 *= f5;
/* 188 */             f4 *= f5;
/*     */           } 
/*     */         } 
/*     */         
/* 192 */         if (i1 < i2) {
/* 193 */           i3 = i1;
/*     */         } else {
/* 195 */           i3 = i2;
/*     */         } 
/* 197 */         float f = i3 * n;
/* 198 */         this.alphas[b2] = this.alphas[b2] + f * f1;
/* 199 */         this.reds[b2] = this.reds[b2] + f * f2;
/* 200 */         this.greens[b2] = this.greens[b2] + f * f3;
/* 201 */         this.blues[b2] = this.blues[b2] + f * f4;
/* 202 */         if ((i1 -= i3) == 0) {
/* 203 */           b1++;
/*     */         }
/* 205 */         if ((i2 -= i3) == 0) {
/* 206 */           b2++;
/* 207 */           i2 = this.srcWidth;
/*     */         } 
/*     */       } 
/* 210 */       if ((m -= n) == 0) {
/* 211 */         int[] arrayOfInt = calcRow();
/*     */         do {
/* 213 */           this.consumer.setPixels(0, k, this.destWidth, 1, rgbmodel, arrayOfInt, 0, this.destWidth);
/*     */           
/* 215 */           k++;
/* 216 */         } while ((j -= n) >= n && n == this.srcHeight);
/*     */       } else {
/* 218 */         j -= n;
/*     */       } 
/* 220 */       if (j == 0) {
/* 221 */         j = this.destHeight;
/* 222 */         i++;
/* 223 */         paramInt5 += paramInt6;
/*     */       } 
/*     */     } 
/* 226 */     this.savedyrem = m;
/* 227 */     this.savedy = k;
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 249 */     if (this.passthrough) {
/* 250 */       super.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfbyte, paramInt5, paramInt6);
/*     */     } else {
/* 252 */       accumPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfbyte, paramInt5, paramInt6);
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 275 */     if (this.passthrough) {
/* 276 */       super.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfint, paramInt5, paramInt6);
/*     */     } else {
/* 278 */       accumPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfint, paramInt5, paramInt6);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/AreaAveragingScaleFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */