/*     */ package java.awt.image;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReplicateScaleFilter
/*     */   extends ImageFilter
/*     */ {
/*     */   protected int srcWidth;
/*     */   protected int srcHeight;
/*     */   protected int destWidth;
/*     */   protected int destHeight;
/*     */   protected int[] srcrows;
/*     */   protected int[] srccols;
/*     */   protected Object outpixbuf;
/*     */   
/*     */   public ReplicateScaleFilter(int paramInt1, int paramInt2) {
/* 101 */     if (paramInt1 == 0 || paramInt2 == 0) {
/* 102 */       throw new IllegalArgumentException("Width (" + paramInt1 + ") and height (" + paramInt2 + ") must be non-zero");
/*     */     }
/*     */ 
/*     */     
/* 106 */     this.destWidth = paramInt1;
/* 107 */     this.destHeight = paramInt2;
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
/*     */   public void setProperties(Hashtable<?, ?> paramHashtable) {
/* 124 */     Hashtable<String, String> hashtable = (Hashtable)paramHashtable.clone();
/* 125 */     String str1 = "rescale";
/* 126 */     String str2 = this.destWidth + "x" + this.destHeight;
/* 127 */     Object object = hashtable.get(str1);
/* 128 */     if (object != null && object instanceof String) {
/* 129 */       str2 = (String)object + ", " + str2;
/*     */     }
/* 131 */     hashtable.put(str1, str2);
/* 132 */     super.setProperties(hashtable);
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
/*     */   public void setDimensions(int paramInt1, int paramInt2) {
/* 148 */     this.srcWidth = paramInt1;
/* 149 */     this.srcHeight = paramInt2;
/* 150 */     if (this.destWidth < 0) {
/* 151 */       if (this.destHeight < 0) {
/* 152 */         this.destWidth = this.srcWidth;
/* 153 */         this.destHeight = this.srcHeight;
/*     */       } else {
/* 155 */         this.destWidth = this.srcWidth * this.destHeight / this.srcHeight;
/*     */       } 
/* 157 */     } else if (this.destHeight < 0) {
/* 158 */       this.destHeight = this.srcHeight * this.destWidth / this.srcWidth;
/*     */     } 
/* 160 */     this.consumer.setDimensions(this.destWidth, this.destHeight);
/*     */   }
/*     */   
/*     */   private void calculateMaps() {
/* 164 */     this.srcrows = new int[this.destHeight + 1]; byte b;
/* 165 */     for (b = 0; b <= this.destHeight; b++) {
/* 166 */       this.srcrows[b] = (2 * b * this.srcHeight + this.srcHeight) / 2 * this.destHeight;
/*     */     }
/* 168 */     this.srccols = new int[this.destWidth + 1];
/* 169 */     for (b = 0; b <= this.destWidth; b++) {
/* 170 */       this.srccols[b] = (2 * b * this.srcWidth + this.srcWidth) / 2 * this.destWidth;
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/*     */     byte[] arrayOfByte;
/* 189 */     if (this.srcrows == null || this.srccols == null) {
/* 190 */       calculateMaps();
/*     */     }
/*     */     
/* 193 */     int j = (2 * paramInt1 * this.destWidth + this.srcWidth - 1) / 2 * this.srcWidth;
/* 194 */     int k = (2 * paramInt2 * this.destHeight + this.srcHeight - 1) / 2 * this.srcHeight;
/*     */     
/* 196 */     if (this.outpixbuf != null && this.outpixbuf instanceof byte[]) {
/* 197 */       arrayOfByte = (byte[])this.outpixbuf;
/*     */     } else {
/* 199 */       arrayOfByte = new byte[this.destWidth];
/* 200 */       this.outpixbuf = arrayOfByte;
/*     */     }  int i;
/* 202 */     for (int m = k; (i = this.srcrows[m]) < paramInt2 + paramInt4; m++) {
/* 203 */       int i1 = paramInt5 + paramInt6 * (i - paramInt2);
/*     */       int n, i2;
/* 205 */       for (i2 = j; (n = this.srccols[i2]) < paramInt1 + paramInt3; i2++) {
/* 206 */         arrayOfByte[i2] = paramArrayOfbyte[i1 + n - paramInt1];
/*     */       }
/* 208 */       if (i2 > j) {
/* 209 */         this.consumer.setPixels(j, m, i2 - j, 1, paramColorModel, arrayOfByte, j, this.destWidth);
/*     */       }
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/*     */     int[] arrayOfInt;
/* 230 */     if (this.srcrows == null || this.srccols == null) {
/* 231 */       calculateMaps();
/*     */     }
/*     */     
/* 234 */     int j = (2 * paramInt1 * this.destWidth + this.srcWidth - 1) / 2 * this.srcWidth;
/* 235 */     int k = (2 * paramInt2 * this.destHeight + this.srcHeight - 1) / 2 * this.srcHeight;
/*     */     
/* 237 */     if (this.outpixbuf != null && this.outpixbuf instanceof int[]) {
/* 238 */       arrayOfInt = (int[])this.outpixbuf;
/*     */     } else {
/* 240 */       arrayOfInt = new int[this.destWidth];
/* 241 */       this.outpixbuf = arrayOfInt;
/*     */     }  int i;
/* 243 */     for (int m = k; (i = this.srcrows[m]) < paramInt2 + paramInt4; m++) {
/* 244 */       int i1 = paramInt5 + paramInt6 * (i - paramInt2);
/*     */       int n, i2;
/* 246 */       for (i2 = j; (n = this.srccols[i2]) < paramInt1 + paramInt3; i2++) {
/* 247 */         arrayOfInt[i2] = paramArrayOfint[i1 + n - paramInt1];
/*     */       }
/* 249 */       if (i2 > j)
/* 250 */         this.consumer.setPixels(j, m, i2 - j, 1, paramColorModel, arrayOfInt, j, this.destWidth); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ReplicateScaleFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */