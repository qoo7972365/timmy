/*     */ package sun.font;
/*     */ 
/*     */ import java.text.Bidi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BidiUtils
/*     */ {
/*     */   static final char NUMLEVELS = '>';
/*     */   
/*     */   public static void getLevels(Bidi paramBidi, byte[] paramArrayOfbyte, int paramInt) {
/*  55 */     int i = paramInt + paramBidi.getLength();
/*     */     
/*  57 */     if (paramInt < 0 || i > paramArrayOfbyte.length) {
/*  58 */       throw new IndexOutOfBoundsException("levels.length = " + paramArrayOfbyte.length + " start: " + paramInt + " limit: " + i);
/*     */     }
/*     */ 
/*     */     
/*  62 */     int j = paramBidi.getRunCount();
/*  63 */     int k = paramInt;
/*  64 */     for (byte b = 0; b < j; b++) {
/*  65 */       int m = paramInt + paramBidi.getRunLimit(b);
/*  66 */       byte b1 = (byte)paramBidi.getRunLevel(b);
/*     */       
/*  68 */       while (k < m) {
/*  69 */         paramArrayOfbyte[k++] = b1;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getLevels(Bidi paramBidi) {
/*  79 */     byte[] arrayOfByte = new byte[paramBidi.getLength()];
/*  80 */     getLevels(paramBidi, arrayOfByte, 0);
/*  81 */     return arrayOfByte;
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
/*     */   public static int[] createVisualToLogicalMap(byte[] paramArrayOfbyte) {
/*  95 */     int i = paramArrayOfbyte.length;
/*  96 */     int[] arrayOfInt = new int[i];
/*     */     
/*  98 */     byte b1 = 63;
/*  99 */     byte b2 = 0;
/*     */     
/*     */     byte b;
/*     */     
/* 103 */     for (b = 0; b < i; b++) {
/* 104 */       arrayOfInt[b] = b;
/*     */       
/* 106 */       byte b3 = paramArrayOfbyte[b];
/* 107 */       if (b3 > b2) {
/* 108 */         b2 = b3;
/*     */       }
/*     */       
/* 111 */       if ((b3 & 0x1) != 0 && b3 < b1) {
/* 112 */         b1 = b3;
/*     */       }
/*     */     } 
/*     */     
/* 116 */     while (b2 >= b1) {
/* 117 */       b = 0;
/*     */       while (true) {
/* 119 */         if (b < i && paramArrayOfbyte[b] < b2) {
/* 120 */           b++; continue;
/*     */         } 
/* 122 */         byte b3 = b++;
/*     */         
/* 124 */         if (b3 == paramArrayOfbyte.length) {
/*     */           break;
/*     */         }
/*     */         
/* 128 */         while (b < i && paramArrayOfbyte[b] >= b2) {
/* 129 */           b++;
/*     */         }
/* 131 */         int j = b - 1;
/*     */         
/* 133 */         while (b3 < j) {
/* 134 */           int k = arrayOfInt[b3];
/* 135 */           arrayOfInt[b3] = arrayOfInt[j];
/* 136 */           arrayOfInt[j] = k;
/* 137 */           b3++;
/* 138 */           j--;
/*     */         } 
/*     */       } 
/*     */       
/* 142 */       b2 = (byte)(b2 - 1);
/*     */     } 
/*     */     
/* 145 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] createInverseMap(int[] paramArrayOfint) {
/* 156 */     if (paramArrayOfint == null) {
/* 157 */       return null;
/*     */     }
/*     */     
/* 160 */     int[] arrayOfInt = new int[paramArrayOfint.length];
/* 161 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 162 */       arrayOfInt[paramArrayOfint[b]] = b;
/*     */     }
/*     */     
/* 165 */     return arrayOfInt;
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
/*     */   public static int[] createContiguousOrder(int[] paramArrayOfint) {
/* 179 */     if (paramArrayOfint != null) {
/* 180 */       return computeContiguousOrder(paramArrayOfint, 0, paramArrayOfint.length);
/*     */     }
/*     */     
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] computeContiguousOrder(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 192 */     int[] arrayOfInt = new int[paramInt2 - paramInt1]; byte b;
/* 193 */     for (b = 0; b < arrayOfInt.length; b++) {
/* 194 */       arrayOfInt[b] = b + paramInt1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     for (b = 0; b < arrayOfInt.length - 1; b++) {
/* 202 */       int i = b;
/* 203 */       int j = paramArrayOfint[arrayOfInt[i]]; int k;
/* 204 */       for (k = b; k < arrayOfInt.length; k++) {
/* 205 */         if (paramArrayOfint[arrayOfInt[k]] < j) {
/* 206 */           i = k;
/* 207 */           j = paramArrayOfint[arrayOfInt[i]];
/*     */         } 
/*     */       } 
/* 210 */       k = arrayOfInt[b];
/* 211 */       arrayOfInt[b] = arrayOfInt[i];
/* 212 */       arrayOfInt[i] = k;
/*     */     } 
/*     */ 
/*     */     
/* 216 */     if (paramInt1 != 0) {
/* 217 */       for (b = 0; b < arrayOfInt.length; b++) {
/* 218 */         arrayOfInt[b] = arrayOfInt[b] - paramInt1;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 224 */     for (b = 0; b < arrayOfInt.length && 
/* 225 */       arrayOfInt[b] == b; b++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (b == arrayOfInt.length) {
/* 231 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 235 */     return createInverseMap(arrayOfInt);
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
/*     */   public static int[] createNormalizedMap(int[] paramArrayOfint, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 253 */     if (paramArrayOfint != null) {
/* 254 */       if (paramInt1 != 0 || paramInt2 != paramArrayOfint.length) {
/*     */         boolean bool1;
/*     */         
/*     */         boolean bool2;
/*     */         boolean bool3;
/* 259 */         if (paramArrayOfbyte == null) {
/* 260 */           bool3 = false;
/* 261 */           bool1 = true;
/* 262 */           bool2 = true;
/*     */         
/*     */         }
/* 265 */         else if (paramArrayOfbyte[paramInt1] == paramArrayOfbyte[paramInt2 - 1]) {
/* 266 */           bool3 = paramArrayOfbyte[paramInt1];
/* 267 */           bool2 = ((bool3 & true) == 0) ? true : false;
/*     */           
/*     */           int i;
/*     */           
/* 271 */           for (i = paramInt1; i < paramInt2 && 
/* 272 */             paramArrayOfbyte[i] >= bool3; i++) {
/*     */ 
/*     */             
/* 275 */             if (bool2) {
/* 276 */               bool2 = (paramArrayOfbyte[i] == bool3) ? true : false;
/*     */             }
/*     */           } 
/*     */           
/* 280 */           bool1 = (i == paramInt2) ? true : false;
/*     */         } else {
/*     */           
/* 283 */           bool1 = false;
/*     */ 
/*     */           
/* 286 */           bool3 = false;
/* 287 */           bool2 = false;
/*     */         } 
/*     */ 
/*     */         
/* 291 */         if (bool1) {
/* 292 */           int i; if (bool2) {
/* 293 */             return null;
/*     */           }
/*     */           
/* 296 */           int[] arrayOfInt = new int[paramInt2 - paramInt1];
/*     */ 
/*     */           
/* 299 */           if ((bool3 & true) != 0) {
/* 300 */             i = paramArrayOfint[paramInt2 - 1];
/*     */           } else {
/* 302 */             i = paramArrayOfint[paramInt1];
/*     */           } 
/*     */           
/* 305 */           if (i == 0) {
/* 306 */             System.arraycopy(paramArrayOfint, paramInt1, arrayOfInt, 0, paramInt2 - paramInt1);
/*     */           } else {
/*     */             
/* 309 */             for (byte b = 0; b < arrayOfInt.length; b++) {
/* 310 */               arrayOfInt[b] = paramArrayOfint[b + paramInt1] - i;
/*     */             }
/*     */           } 
/*     */           
/* 314 */           return arrayOfInt;
/*     */         } 
/*     */         
/* 317 */         return computeContiguousOrder(paramArrayOfint, paramInt1, paramInt2);
/*     */       } 
/*     */ 
/*     */       
/* 321 */       return paramArrayOfint;
/*     */     } 
/*     */ 
/*     */     
/* 325 */     return null;
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
/*     */   public static void reorderVisually(byte[] paramArrayOfbyte, Object[] paramArrayOfObject) {
/* 339 */     int i = paramArrayOfbyte.length;
/*     */     
/* 341 */     byte b1 = 63;
/* 342 */     byte b2 = 0;
/*     */     
/*     */     byte b;
/*     */     
/* 346 */     for (b = 0; b < i; b++) {
/* 347 */       byte b3 = paramArrayOfbyte[b];
/* 348 */       if (b3 > b2) {
/* 349 */         b2 = b3;
/*     */       }
/*     */       
/* 352 */       if ((b3 & 0x1) != 0 && b3 < b1) {
/* 353 */         b1 = b3;
/*     */       }
/*     */     } 
/*     */     
/* 357 */     while (b2 >= b1) {
/* 358 */       b = 0;
/*     */       while (true) {
/* 360 */         if (b < i && paramArrayOfbyte[b] < b2) {
/* 361 */           b++; continue;
/*     */         } 
/* 363 */         byte b3 = b++;
/*     */         
/* 365 */         if (b3 == paramArrayOfbyte.length) {
/*     */           break;
/*     */         }
/*     */         
/* 369 */         while (b < i && paramArrayOfbyte[b] >= b2) {
/* 370 */           b++;
/*     */         }
/* 372 */         int j = b - 1;
/*     */         
/* 374 */         while (b3 < j) {
/* 375 */           Object object = paramArrayOfObject[b3];
/* 376 */           paramArrayOfObject[b3] = paramArrayOfObject[j];
/* 377 */           paramArrayOfObject[j] = object;
/* 378 */           b3++;
/* 379 */           j--;
/*     */         } 
/*     */       } 
/*     */       
/* 383 */       b2 = (byte)(b2 - 1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/BidiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */