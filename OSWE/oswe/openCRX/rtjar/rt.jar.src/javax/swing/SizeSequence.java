/*     */ package javax.swing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SizeSequence
/*     */ {
/* 125 */   private static int[] emptyArray = new int[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private int[] a = emptyArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SizeSequence() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SizeSequence(int paramInt) {
/* 150 */     this(paramInt, 0);
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
/*     */   public SizeSequence(int paramInt1, int paramInt2) {
/* 162 */     this();
/* 163 */     insertEntries(0, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SizeSequence(int[] paramArrayOfint) {
/* 174 */     this();
/* 175 */     setSizes(paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setSizes(int paramInt1, int paramInt2) {
/* 183 */     if (this.a.length != paramInt1) {
/* 184 */       this.a = new int[paramInt1];
/*     */     }
/* 186 */     setSizes(0, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   private int setSizes(int paramInt1, int paramInt2, int paramInt3) {
/* 190 */     if (paramInt2 <= paramInt1) {
/* 191 */       return 0;
/*     */     }
/* 193 */     int i = (paramInt1 + paramInt2) / 2;
/* 194 */     this.a[i] = paramInt3 + setSizes(paramInt1, i, paramInt3);
/* 195 */     return this.a[i] + setSizes(i + 1, paramInt2, paramInt3);
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
/*     */   public void setSizes(int[] paramArrayOfint) {
/* 210 */     if (this.a.length != paramArrayOfint.length) {
/* 211 */       this.a = new int[paramArrayOfint.length];
/*     */     }
/* 213 */     setSizes(0, this.a.length, paramArrayOfint);
/*     */   }
/*     */   
/*     */   private int setSizes(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 217 */     if (paramInt2 <= paramInt1) {
/* 218 */       return 0;
/*     */     }
/* 220 */     int i = (paramInt1 + paramInt2) / 2;
/* 221 */     this.a[i] = paramArrayOfint[i] + setSizes(paramInt1, i, paramArrayOfint);
/* 222 */     return this.a[i] + setSizes(i + 1, paramInt2, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSizes() {
/* 231 */     int i = this.a.length;
/* 232 */     int[] arrayOfInt = new int[i];
/* 233 */     getSizes(0, i, arrayOfInt);
/* 234 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private int getSizes(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 238 */     if (paramInt2 <= paramInt1) {
/* 239 */       return 0;
/*     */     }
/* 241 */     int i = (paramInt1 + paramInt2) / 2;
/* 242 */     paramArrayOfint[i] = this.a[i] - getSizes(paramInt1, i, paramArrayOfint);
/* 243 */     return this.a[i] + getSizes(i + 1, paramInt2, paramArrayOfint);
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
/*     */   public int getPosition(int paramInt) {
/* 262 */     return getPosition(0, this.a.length, paramInt);
/*     */   }
/*     */   
/*     */   private int getPosition(int paramInt1, int paramInt2, int paramInt3) {
/* 266 */     if (paramInt2 <= paramInt1) {
/* 267 */       return 0;
/*     */     }
/* 269 */     int i = (paramInt1 + paramInt2) / 2;
/* 270 */     if (paramInt3 <= i) {
/* 271 */       return getPosition(paramInt1, i, paramInt3);
/*     */     }
/*     */     
/* 274 */     return this.a[i] + getPosition(i + 1, paramInt2, paramInt3);
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
/*     */   public int getIndex(int paramInt) {
/* 288 */     return getIndex(0, this.a.length, paramInt);
/*     */   }
/*     */   
/*     */   private int getIndex(int paramInt1, int paramInt2, int paramInt3) {
/* 292 */     if (paramInt2 <= paramInt1) {
/* 293 */       return paramInt1;
/*     */     }
/* 295 */     int i = (paramInt1 + paramInt2) / 2;
/* 296 */     int j = this.a[i];
/* 297 */     if (paramInt3 < j) {
/* 298 */       return getIndex(paramInt1, i, paramInt3);
/*     */     }
/*     */     
/* 301 */     return getIndex(i + 1, paramInt2, paramInt3 - j);
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
/*     */   public int getSize(int paramInt) {
/* 315 */     return getPosition(paramInt + 1) - getPosition(paramInt);
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
/*     */   public void setSize(int paramInt1, int paramInt2) {
/* 329 */     changeSize(0, this.a.length, paramInt1, paramInt2 - getSize(paramInt1));
/*     */   }
/*     */   
/*     */   private void changeSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 333 */     if (paramInt2 <= paramInt1) {
/*     */       return;
/*     */     }
/* 336 */     int i = (paramInt1 + paramInt2) / 2;
/* 337 */     if (paramInt3 <= i) {
/* 338 */       this.a[i] = this.a[i] + paramInt4;
/* 339 */       changeSize(paramInt1, i, paramInt3, paramInt4);
/*     */     } else {
/*     */       
/* 342 */       changeSize(i + 1, paramInt2, paramInt3, paramInt4);
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
/*     */   public void insertEntries(int paramInt1, int paramInt2, int paramInt3) {
/* 364 */     int[] arrayOfInt = getSizes();
/* 365 */     int i = paramInt1 + paramInt2;
/* 366 */     int j = this.a.length + paramInt2;
/* 367 */     this.a = new int[j]; int k;
/* 368 */     for (k = 0; k < paramInt1; k++) {
/* 369 */       this.a[k] = arrayOfInt[k];
/*     */     }
/* 371 */     for (k = paramInt1; k < i; k++) {
/* 372 */       this.a[k] = paramInt3;
/*     */     }
/* 374 */     for (k = i; k < j; k++) {
/* 375 */       this.a[k] = arrayOfInt[k - paramInt2];
/*     */     }
/* 377 */     setSizes(this.a);
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
/*     */   public void removeEntries(int paramInt1, int paramInt2) {
/* 394 */     int[] arrayOfInt = getSizes();
/* 395 */     int i = paramInt1 + paramInt2;
/* 396 */     int j = this.a.length - paramInt2;
/* 397 */     this.a = new int[j]; int k;
/* 398 */     for (k = 0; k < paramInt1; k++) {
/* 399 */       this.a[k] = arrayOfInt[k];
/*     */     }
/* 401 */     for (k = paramInt1; k < j; k++) {
/* 402 */       this.a[k] = arrayOfInt[k + paramInt2];
/*     */     }
/* 404 */     setSizes(this.a);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SizeSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */