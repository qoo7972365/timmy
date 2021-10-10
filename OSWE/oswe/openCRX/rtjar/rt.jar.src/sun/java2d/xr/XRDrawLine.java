/*     */ package sun.java2d.xr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRDrawLine
/*     */ {
/*     */   static final int BIG_MAX = 536870911;
/*     */   static final int BIG_MIN = -536870912;
/*     */   static final int OUTCODE_TOP = 1;
/*     */   static final int OUTCODE_BOTTOM = 2;
/*     */   static final int OUTCODE_LEFT = 4;
/*     */   static final int OUTCODE_RIGHT = 8;
/*     */   int x1;
/*     */   int y1;
/*     */   int x2;
/*     */   int y2;
/*     */   int ucX1;
/*     */   int ucY1;
/*     */   int ucX2;
/*     */   int ucY2;
/*  49 */   DirtyRegion region = new DirtyRegion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rasterizeLine(GrowableRectArray paramGrowableRectArray, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     int k, m;
/*  61 */     initCoordinates(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean2);
/*     */     
/*  63 */     int n = this.x2 - this.x1;
/*  64 */     int i1 = this.y2 - this.y1;
/*  65 */     int i2 = Math.abs(n);
/*  66 */     int i3 = Math.abs(i1);
/*  67 */     boolean bool1 = (i2 >= i3) ? true : false;
/*  68 */     float f = i2 / i3;
/*     */     
/*  70 */     if (paramBoolean1 && 
/*  71 */       !clipCoordinates(paramInt5, paramInt6, paramInt7, paramInt8, bool1, n, i1, i2, i3)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.region.setDirtyLineRegion(this.x1, this.y1, this.x2, this.y2);
/*  78 */     int i4 = this.region.x2 - this.region.x;
/*  79 */     int i5 = this.region.y2 - this.region.y;
/*     */     
/*  81 */     if (i4 == 0 || i5 == 0) {
/*     */ 
/*     */       
/*  84 */       paramGrowableRectArray.pushRectValues(this.region.x, this.region.y, this.region.x2 - this.region.x + 1, this.region.y2 - this.region.y + 1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  90 */     if (bool1) {
/*  91 */       m = i3 * 2;
/*  92 */       k = i2 * 2;
/*  93 */       i2 = -i2;
/*  94 */       j = this.x2 - this.x1;
/*     */     } else {
/*  96 */       m = i2 * 2;
/*  97 */       k = i3 * 2;
/*  98 */       i3 = -i3;
/*  99 */       j = this.y2 - this.y1;
/*     */     } 
/*     */     int j;
/* 102 */     if ((j = Math.abs(j) + 1) == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     int i = -(k / 2);
/*     */     
/* 108 */     if (this.y1 != this.ucY1) {
/* 109 */       int i6 = this.y1 - this.ucY1;
/* 110 */       if (i6 < 0) {
/* 111 */         i6 = -i6;
/*     */       }
/* 113 */       i += i6 * i2 * 2;
/*     */     } 
/*     */     
/* 116 */     if (this.x1 != this.ucX1) {
/* 117 */       int i6 = this.x1 - this.ucX1;
/* 118 */       if (i6 < 0) {
/* 119 */         i6 = -i6;
/*     */       }
/* 121 */       i += i6 * i3 * 2;
/*     */     } 
/* 123 */     i += m;
/* 124 */     k -= m;
/*     */     
/* 126 */     boolean bool2 = (n > 0) ? true : true;
/* 127 */     boolean bool3 = (i1 > 0) ? true : true;
/* 128 */     boolean bool4 = bool1 ? bool2 : false;
/* 129 */     boolean bool5 = !bool1 ? bool3 : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (f <= 0.9D || f >= 1.1D) {
/* 137 */       lineToRects(paramGrowableRectArray, j, i, m, k, bool2, bool3, bool4, bool5);
/*     */     } else {
/*     */       
/* 140 */       lineToPoints(paramGrowableRectArray, j, i, m, k, bool2, bool3, bool4, bool5);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void lineToPoints(GrowableRectArray paramGrowableRectArray, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 148 */     int i = this.x1, j = this.y1;
/*     */     
/*     */     do {
/* 151 */       paramGrowableRectArray.pushRectValues(i, j, 1, 1);
/*     */ 
/*     */       
/* 154 */       if (paramInt2 < 0) {
/* 155 */         paramInt2 += paramInt3;
/* 156 */         i += paramInt7;
/* 157 */         j += paramInt8;
/*     */       } else {
/* 159 */         paramInt2 -= paramInt4;
/* 160 */         i += paramInt5;
/* 161 */         j += paramInt6;
/*     */       } 
/* 163 */     } while (--paramInt1 > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void lineToRects(GrowableRectArray paramGrowableRectArray, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 169 */     int i = this.x1, j = this.y1;
/* 170 */     int k = Integer.MIN_VALUE, m = 0;
/* 171 */     byte b1 = 0, b2 = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 176 */       if (j == m) {
/* 177 */         if (i == k + b1) {
/* 178 */           b1++;
/* 179 */         } else if (i == k - 1) {
/* 180 */           k--;
/* 181 */           b1++;
/*     */         } 
/* 183 */       } else if (i == k) {
/* 184 */         if (j == m + b2) {
/* 185 */           b2++;
/* 186 */         } else if (j == m - 1) {
/* 187 */           m--;
/* 188 */           b2++;
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 194 */         if (k != Integer.MIN_VALUE) {
/* 195 */           paramGrowableRectArray.pushRectValues(k, m, b1, b2);
/*     */         }
/* 197 */         k = i;
/* 198 */         m = j;
/* 199 */         b1 = b2 = 1;
/*     */       } 
/*     */ 
/*     */       
/* 203 */       if (paramInt2 < 0) {
/* 204 */         paramInt2 += paramInt3;
/* 205 */         i += paramInt7;
/* 206 */         j += paramInt8;
/*     */       } else {
/* 208 */         paramInt2 -= paramInt4;
/* 209 */         i += paramInt5;
/* 210 */         j += paramInt6;
/*     */       } 
/* 212 */     } while (--paramInt1 > 0);
/*     */ 
/*     */ 
/*     */     
/* 216 */     paramGrowableRectArray.pushRectValues(k, m, b1, b2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean clipCoordinates(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 223 */     int i = outcode(this.x1, this.y1, paramInt1, paramInt2, paramInt3, paramInt4);
/* 224 */     int j = outcode(this.x2, this.y2, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 226 */     while ((i | j) != 0) {
/* 227 */       int k = 0, m = 0;
/*     */       
/* 229 */       if ((i & j) != 0) {
/* 230 */         return false;
/*     */       }
/*     */       
/* 233 */       if (i != 0) {
/* 234 */         if ((i & 0x3) != 0) {
/* 235 */           if ((i & 0x1) != 0) {
/* 236 */             this.y1 = paramInt2;
/*     */           } else {
/* 238 */             this.y1 = paramInt4;
/*     */           } 
/* 240 */           m = this.y1 - this.ucY1;
/* 241 */           if (m < 0) {
/* 242 */             m = -m;
/*     */           }
/* 244 */           k = 2 * m * paramInt7 + paramInt8;
/* 245 */           if (paramBoolean) {
/* 246 */             k += paramInt8 - paramInt7 - 1;
/*     */           }
/* 248 */           k /= 2 * paramInt8;
/* 249 */           if (paramInt5 < 0) {
/* 250 */             k = -k;
/*     */           }
/* 252 */           this.x1 = this.ucX1 + k;
/* 253 */         } else if ((i & 0xC) != 0) {
/* 254 */           if ((i & 0x4) != 0) {
/* 255 */             this.x1 = paramInt1;
/*     */           } else {
/* 257 */             this.x1 = paramInt3;
/*     */           } 
/* 259 */           k = this.x1 - this.ucX1;
/* 260 */           if (k < 0) {
/* 261 */             k = -k;
/*     */           }
/* 263 */           m = 2 * k * paramInt8 + paramInt7;
/* 264 */           if (!paramBoolean) {
/* 265 */             m += paramInt7 - paramInt8 - 1;
/*     */           }
/* 267 */           m /= 2 * paramInt7;
/* 268 */           if (paramInt6 < 0) {
/* 269 */             m = -m;
/*     */           }
/* 271 */           this.y1 = this.ucY1 + m;
/*     */         } 
/* 273 */         i = outcode(this.x1, this.y1, paramInt1, paramInt2, paramInt3, paramInt4); continue;
/*     */       } 
/* 275 */       if ((j & 0x3) != 0) {
/* 276 */         if ((j & 0x1) != 0) {
/* 277 */           this.y2 = paramInt2;
/*     */         } else {
/* 279 */           this.y2 = paramInt4;
/*     */         } 
/* 281 */         m = this.y2 - this.ucY2;
/* 282 */         if (m < 0) {
/* 283 */           m = -m;
/*     */         }
/* 285 */         k = 2 * m * paramInt7 + paramInt8;
/* 286 */         if (paramBoolean) {
/* 287 */           k += paramInt8 - paramInt7;
/*     */         } else {
/* 289 */           k--;
/*     */         } 
/* 291 */         k /= 2 * paramInt8;
/* 292 */         if (paramInt5 > 0) {
/* 293 */           k = -k;
/*     */         }
/* 295 */         this.x2 = this.ucX2 + k;
/* 296 */       } else if ((j & 0xC) != 0) {
/* 297 */         if ((j & 0x4) != 0) {
/* 298 */           this.x2 = paramInt1;
/*     */         } else {
/* 300 */           this.x2 = paramInt3;
/*     */         } 
/* 302 */         k = this.x2 - this.ucX2;
/* 303 */         if (k < 0) {
/* 304 */           k = -k;
/*     */         }
/* 306 */         m = 2 * k * paramInt8 + paramInt7;
/* 307 */         if (paramBoolean) {
/* 308 */           m--;
/*     */         } else {
/* 310 */           m += paramInt7 - paramInt8;
/*     */         } 
/* 312 */         m /= 2 * paramInt7;
/* 313 */         if (paramInt6 > 0) {
/* 314 */           m = -m;
/*     */         }
/* 316 */         this.y2 = this.ucY2 + m;
/*     */       } 
/* 318 */       j = outcode(this.x2, this.y2, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */ 
/*     */     
/* 322 */     return true;
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
/*     */   private void initCoordinates(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 349 */     if (paramBoolean && (
/* 350 */       OverflowsBig(paramInt1) || OverflowsBig(paramInt2) || OverflowsBig(paramInt3) || OverflowsBig(paramInt4))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       double d1 = paramInt1;
/* 364 */       double d2 = paramInt2;
/* 365 */       double d3 = paramInt3;
/* 366 */       double d4 = paramInt4;
/* 367 */       double d5 = d3 - d1;
/* 368 */       double d6 = d4 - d2;
/*     */       
/* 370 */       if (paramInt1 < -536870912) {
/* 371 */         d2 = paramInt2 + (-536870912 - paramInt1) * d6 / d5;
/* 372 */         d1 = -5.36870912E8D;
/* 373 */       } else if (paramInt1 > 536870911) {
/* 374 */         d2 = paramInt2 - (paramInt1 - 536870911) * d6 / d5;
/* 375 */         d1 = 5.36870911E8D;
/*     */       } 
/*     */       
/* 378 */       if (d2 < -5.36870912E8D) {
/* 379 */         d1 = paramInt1 + (-536870912 - paramInt2) * d5 / d6;
/* 380 */         d2 = -5.36870912E8D;
/* 381 */       } else if (d2 > 5.36870911E8D) {
/* 382 */         d1 = paramInt1 - (paramInt2 - 536870911) * d5 / d6;
/* 383 */         d2 = 5.36870911E8D;
/*     */       } 
/* 385 */       if (paramInt3 < -536870912) {
/* 386 */         d4 = paramInt4 + (-536870912 - paramInt3) * d6 / d5;
/* 387 */         d3 = -5.36870912E8D;
/* 388 */       } else if (paramInt3 > 536870911) {
/* 389 */         d4 = paramInt4 - (paramInt3 - 536870911) * d6 / d5;
/* 390 */         d3 = 5.36870911E8D;
/*     */       } 
/*     */       
/* 393 */       if (d4 < -5.36870912E8D) {
/* 394 */         d3 = paramInt3 + (-536870912 - paramInt4) * d5 / d6;
/* 395 */         d4 = -5.36870912E8D;
/* 396 */       } else if (d4 > 5.36870911E8D) {
/* 397 */         d3 = paramInt3 - (paramInt4 - 536870911) * d5 / d6;
/* 398 */         d4 = 5.36870911E8D;
/*     */       } 
/*     */       
/* 401 */       paramInt1 = (int)d1;
/* 402 */       paramInt2 = (int)d2;
/* 403 */       paramInt3 = (int)d3;
/* 404 */       paramInt4 = (int)d4;
/*     */     } 
/*     */     
/* 407 */     this.x1 = this.ucX1 = paramInt1;
/* 408 */     this.y1 = this.ucY1 = paramInt2;
/* 409 */     this.x2 = this.ucX2 = paramInt3;
/* 410 */     this.y2 = this.ucY2 = paramInt4;
/*     */   }
/*     */   
/*     */   private boolean OverflowsBig(int paramInt) {
/* 414 */     return (paramInt != paramInt << 2 >> 2);
/*     */   }
/*     */   
/*     */   private int out(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 418 */     return (paramInt1 < paramInt2) ? paramInt4 : ((paramInt1 > paramInt3) ? paramInt5 : 0);
/*     */   }
/*     */   
/*     */   private int outcode(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 422 */     return out(paramInt2, paramInt4, paramInt6, 1, 2) | 
/* 423 */       out(paramInt1, paramInt3, paramInt5, 4, 8);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRDrawLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */