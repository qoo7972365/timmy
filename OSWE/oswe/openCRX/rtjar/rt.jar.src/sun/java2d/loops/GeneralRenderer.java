/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.font.GlyphList;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GeneralRenderer
/*     */ {
/*     */   static final int OUTCODE_TOP = 1;
/*     */   static final int OUTCODE_BOTTOM = 2;
/*     */   static final int OUTCODE_LEFT = 4;
/*     */   static final int OUTCODE_RIGHT = 8;
/*     */   
/*     */   public static void register() {
/*  53 */     Class<GeneralRenderer> clazz = GeneralRenderer.class;
/*  54 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new GraphicsPrimitiveProxy(clazz, "SetFillRectANY", FillRect.methodSignature, FillRect.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetFillPathANY", FillPath.methodSignature, FillPath.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetFillSpansANY", FillSpans.methodSignature, FillSpans.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetDrawLineANY", DrawLine.methodSignature, DrawLine.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetDrawPolygonsANY", DrawPolygons.methodSignature, DrawPolygons.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetDrawPathANY", DrawPath.methodSignature, DrawPath.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "SetDrawRectANY", DrawRect.methodSignature, DrawRect.primTypeID, SurfaceType.AnyColor, CompositeType.SrcNoEa, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorFillRectANY", FillRect.methodSignature, FillRect.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorFillPathANY", FillPath.methodSignature, FillPath.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorFillSpansANY", FillSpans.methodSignature, FillSpans.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawLineANY", DrawLine.methodSignature, DrawLine.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawPolygonsANY", DrawPolygons.methodSignature, DrawPolygons.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawPathANY", DrawPath.methodSignature, DrawPath.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawRectANY", DrawRect.methodSignature, DrawRect.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawGlyphListANY", DrawGlyphList.methodSignature, DrawGlyphList.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any), new GraphicsPrimitiveProxy(clazz, "XorDrawGlyphListAAANY", DrawGlyphListAA.methodSignature, DrawGlyphListAA.primTypeID, SurfaceType.AnyColor, CompositeType.Xor, SurfaceType.Any) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void doDrawPoly(SurfaceData paramSurfaceData, PixelWriter paramPixelWriter, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, Region paramRegion, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 161 */     int[] arrayOfInt = null;
/*     */     
/* 163 */     if (paramInt2 <= 0) {
/*     */       return;
/*     */     }
/* 166 */     int k = paramArrayOfint1[paramInt1] + paramInt3, i = k;
/* 167 */     int m = paramArrayOfint2[paramInt1] + paramInt4, j = m;
/* 168 */     while (--paramInt2 > 0) {
/* 169 */       paramInt1++;
/* 170 */       int n = paramArrayOfint1[paramInt1] + paramInt3;
/* 171 */       int i1 = paramArrayOfint2[paramInt1] + paramInt4;
/* 172 */       arrayOfInt = doDrawLine(paramSurfaceData, paramPixelWriter, arrayOfInt, paramRegion, k, m, n, i1);
/*     */       
/* 174 */       k = n;
/* 175 */       m = i1;
/*     */     } 
/* 177 */     if (paramBoolean && (k != i || m != j)) {
/* 178 */       arrayOfInt = doDrawLine(paramSurfaceData, paramPixelWriter, arrayOfInt, paramRegion, k, m, i, j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void doSetRect(SurfaceData paramSurfaceData, PixelWriter paramPixelWriter, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 186 */     WritableRaster writableRaster = (WritableRaster)paramSurfaceData.getRaster(paramInt1, paramInt2, paramInt3 - paramInt1, paramInt4 - paramInt2);
/* 187 */     paramPixelWriter.setRaster(writableRaster);
/*     */     
/* 189 */     while (paramInt2 < paramInt4) {
/* 190 */       for (int i = paramInt1; i < paramInt3; i++) {
/* 191 */         paramPixelWriter.writePixel(i, paramInt2);
/*     */       }
/* 193 */       paramInt2++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] doDrawLine(SurfaceData paramSurfaceData, PixelWriter paramPixelWriter, int[] paramArrayOfint, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 201 */     if (paramArrayOfint == null) {
/* 202 */       paramArrayOfint = new int[8];
/*     */     }
/* 204 */     paramArrayOfint[0] = paramInt1;
/* 205 */     paramArrayOfint[1] = paramInt2;
/* 206 */     paramArrayOfint[2] = paramInt3;
/* 207 */     paramArrayOfint[3] = paramInt4;
/* 208 */     if (!adjustLine(paramArrayOfint, paramRegion
/* 209 */         .getLoX(), paramRegion.getLoY(), paramRegion
/* 210 */         .getHiX(), paramRegion.getHiY()))
/*     */     {
/* 212 */       return paramArrayOfint;
/*     */     }
/* 214 */     int i = paramArrayOfint[0];
/* 215 */     int j = paramArrayOfint[1];
/* 216 */     int k = paramArrayOfint[2];
/* 217 */     int m = paramArrayOfint[3];
/*     */ 
/*     */     
/* 220 */     WritableRaster writableRaster = (WritableRaster)paramSurfaceData.getRaster(Math.min(i, k), Math.min(j, m), 
/* 221 */         Math.abs(i - k) + 1, Math.abs(j - m) + 1);
/* 222 */     paramPixelWriter.setRaster(writableRaster);
/*     */ 
/*     */     
/* 225 */     if (i == k) {
/* 226 */       if (j > m) {
/*     */         do {
/* 228 */           paramPixelWriter.writePixel(i, j);
/* 229 */           --j;
/* 230 */         } while (j >= m);
/*     */       } else {
/*     */         do {
/* 233 */           paramPixelWriter.writePixel(i, j);
/* 234 */           ++j;
/* 235 */         } while (j <= m);
/*     */       } 
/* 237 */     } else if (j == m) {
/* 238 */       if (i > k) {
/*     */         do {
/* 240 */           paramPixelWriter.writePixel(i, j);
/* 241 */           --i;
/* 242 */         } while (i >= k);
/*     */       } else {
/*     */         do {
/* 245 */           paramPixelWriter.writePixel(i, j);
/* 246 */           ++i;
/* 247 */         } while (i <= k);
/*     */       } 
/*     */     } else {
/* 250 */       int i4; byte b1, b2; int i5, i6; boolean bool; int n = paramArrayOfint[4];
/* 251 */       int i1 = paramArrayOfint[5];
/* 252 */       int i2 = paramArrayOfint[6];
/* 253 */       int i3 = paramArrayOfint[7];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       if (i2 >= i3) {
/*     */         
/* 264 */         bool = true;
/* 265 */         i6 = i3 * 2;
/* 266 */         i5 = i2 * 2;
/* 267 */         b1 = (n < 0) ? -1 : 1;
/* 268 */         b2 = (i1 < 0) ? -1 : 1;
/* 269 */         i2 = -i2;
/* 270 */         i4 = k - i;
/*     */       } else {
/*     */         
/* 273 */         bool = false;
/* 274 */         i6 = i2 * 2;
/* 275 */         i5 = i3 * 2;
/* 276 */         b1 = (i1 < 0) ? -1 : 1;
/* 277 */         b2 = (n < 0) ? -1 : 1;
/* 278 */         i3 = -i3;
/* 279 */         i4 = m - j;
/*     */       } 
/* 281 */       int i7 = -(i5 / 2);
/* 282 */       if (j != paramInt2) {
/* 283 */         int i8 = j - paramInt2;
/* 284 */         if (i8 < 0) {
/* 285 */           i8 = -i8;
/*     */         }
/* 287 */         i7 += i8 * i2 * 2;
/*     */       } 
/* 289 */       if (i != paramInt1) {
/* 290 */         int i8 = i - paramInt1;
/* 291 */         if (i8 < 0) {
/* 292 */           i8 = -i8;
/*     */         }
/* 294 */         i7 += i8 * i3 * 2;
/*     */       } 
/* 296 */       if (i4 < 0) {
/* 297 */         i4 = -i4;
/*     */       }
/* 299 */       if (bool) {
/*     */         do {
/* 301 */           paramPixelWriter.writePixel(i, j);
/* 302 */           i += b1;
/* 303 */           i7 += i6;
/* 304 */           if (i7 < 0)
/* 305 */             continue;  j += b2;
/* 306 */           i7 -= i5;
/*     */         }
/* 308 */         while (--i4 >= 0);
/*     */       } else {
/*     */         do {
/* 311 */           paramPixelWriter.writePixel(i, j);
/* 312 */           j += b1;
/* 313 */           i7 += i6;
/* 314 */           if (i7 < 0)
/* 315 */             continue;  i += b2;
/* 316 */           i7 -= i5;
/*     */         }
/* 318 */         while (--i4 >= 0);
/*     */       } 
/*     */     } 
/* 321 */     return paramArrayOfint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void doDrawRect(PixelWriter paramPixelWriter, SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 328 */     if (paramInt3 < 0 || paramInt4 < 0) {
/*     */       return;
/*     */     }
/* 331 */     int i = Region.dimAdd(Region.dimAdd(paramInt1, paramInt3), 1);
/* 332 */     int j = Region.dimAdd(Region.dimAdd(paramInt2, paramInt4), 1);
/* 333 */     Region region = paramSunGraphics2D.getCompClip().getBoundsIntersectionXYXY(paramInt1, paramInt2, i, j);
/* 334 */     if (region.isEmpty()) {
/*     */       return;
/*     */     }
/* 337 */     int k = region.getLoX();
/* 338 */     int m = region.getLoY();
/* 339 */     int n = region.getHiX();
/* 340 */     int i1 = region.getHiY();
/*     */     
/* 342 */     if (paramInt3 < 2 || paramInt4 < 2) {
/* 343 */       doSetRect(paramSurfaceData, paramPixelWriter, k, m, n, i1);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 348 */     if (m == paramInt2) {
/* 349 */       doSetRect(paramSurfaceData, paramPixelWriter, k, m, n, m + 1);
/*     */     }
/* 351 */     if (k == paramInt1) {
/* 352 */       doSetRect(paramSurfaceData, paramPixelWriter, k, m + 1, k + 1, i1 - 1);
/*     */     }
/* 354 */     if (n == i) {
/* 355 */       doSetRect(paramSurfaceData, paramPixelWriter, n - 1, m + 1, n, i1 - 1);
/*     */     }
/* 357 */     if (i1 == j) {
/* 358 */       doSetRect(paramSurfaceData, paramPixelWriter, k, i1 - 1, n, i1);
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
/*     */   static void doDrawGlyphList(SurfaceData paramSurfaceData, PixelWriter paramPixelWriter, GlyphList paramGlyphList, Region paramRegion) {
/* 372 */     int[] arrayOfInt = paramGlyphList.getBounds();
/* 373 */     paramRegion.clipBoxToBounds(arrayOfInt);
/* 374 */     int i = arrayOfInt[0];
/* 375 */     int j = arrayOfInt[1];
/* 376 */     int k = arrayOfInt[2];
/* 377 */     int m = arrayOfInt[3];
/*     */ 
/*     */     
/* 380 */     WritableRaster writableRaster = (WritableRaster)paramSurfaceData.getRaster(i, j, k - i, m - j);
/* 381 */     paramPixelWriter.setRaster(writableRaster);
/*     */     
/* 383 */     int n = paramGlyphList.getNumGlyphs();
/* 384 */     for (byte b = 0; b < n; b++) {
/* 385 */       paramGlyphList.setGlyphIndex(b);
/* 386 */       int[] arrayOfInt1 = paramGlyphList.getMetrics();
/* 387 */       int i1 = arrayOfInt1[0];
/* 388 */       int i2 = arrayOfInt1[1];
/* 389 */       int i3 = arrayOfInt1[2];
/* 390 */       int i4 = i1 + i3;
/* 391 */       int i5 = i2 + arrayOfInt1[3];
/* 392 */       int i6 = 0;
/* 393 */       if (i1 < i) {
/* 394 */         i6 = i - i1;
/* 395 */         i1 = i;
/*     */       } 
/* 397 */       if (i2 < j) {
/* 398 */         i6 += (j - i2) * i3;
/* 399 */         i2 = j;
/*     */       } 
/* 401 */       if (i4 > k) i4 = k; 
/* 402 */       if (i5 > m) i5 = m; 
/* 403 */       if (i4 > i1 && i5 > i2) {
/* 404 */         byte[] arrayOfByte = paramGlyphList.getGrayBits();
/* 405 */         i3 -= i4 - i1;
/* 406 */         for (int i7 = i2; i7 < i5; i7++) {
/* 407 */           for (int i8 = i1; i8 < i4; i8++) {
/* 408 */             if (arrayOfByte[i6++] < 0) {
/* 409 */               paramPixelWriter.writePixel(i8, i7);
/*     */             }
/*     */           } 
/* 412 */           i6 += i3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int outcode(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     int i;
/* 425 */     if (paramInt2 < paramInt4) {
/* 426 */       i = 1;
/* 427 */     } else if (paramInt2 > paramInt6) {
/* 428 */       i = 2;
/*     */     } else {
/* 430 */       i = 0;
/*     */     } 
/* 432 */     if (paramInt1 < paramInt3) {
/* 433 */       i |= 0x4;
/* 434 */     } else if (paramInt1 > paramInt5) {
/* 435 */       i |= 0x8;
/*     */     } 
/* 437 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean adjustLine(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 443 */     int i = paramInt3 - 1;
/* 444 */     int j = paramInt4 - 1;
/* 445 */     int k = paramArrayOfint[0];
/* 446 */     int m = paramArrayOfint[1];
/* 447 */     int n = paramArrayOfint[2];
/* 448 */     int i1 = paramArrayOfint[3];
/*     */     
/* 450 */     if (i < paramInt1 || j < paramInt2) {
/* 451 */       return false;
/*     */     }
/*     */     
/* 454 */     if (k == n) {
/* 455 */       if (k < paramInt1 || k > i) {
/* 456 */         return false;
/*     */       }
/* 458 */       if (m > i1) {
/* 459 */         int i2 = m;
/* 460 */         m = i1;
/* 461 */         i1 = i2;
/*     */       } 
/* 463 */       if (m < paramInt2) {
/* 464 */         m = paramInt2;
/*     */       }
/* 466 */       if (i1 > j) {
/* 467 */         i1 = j;
/*     */       }
/* 469 */       if (m > i1) {
/* 470 */         return false;
/*     */       }
/* 472 */       paramArrayOfint[1] = m;
/* 473 */       paramArrayOfint[3] = i1;
/* 474 */     } else if (m == i1) {
/* 475 */       if (m < paramInt2 || m > j) {
/* 476 */         return false;
/*     */       }
/* 478 */       if (k > n) {
/* 479 */         int i2 = k;
/* 480 */         k = n;
/* 481 */         n = i2;
/*     */       } 
/* 483 */       if (k < paramInt1) {
/* 484 */         k = paramInt1;
/*     */       }
/* 486 */       if (n > i) {
/* 487 */         n = i;
/*     */       }
/* 489 */       if (k > n) {
/* 490 */         return false;
/*     */       }
/* 492 */       paramArrayOfint[0] = k;
/* 493 */       paramArrayOfint[2] = n;
/*     */     }
/*     */     else {
/*     */       
/* 497 */       int i4 = n - k;
/* 498 */       int i5 = i1 - m;
/* 499 */       int i6 = (i4 < 0) ? -i4 : i4;
/* 500 */       int i7 = (i5 < 0) ? -i5 : i5;
/* 501 */       boolean bool = (i6 >= i7) ? true : false;
/*     */       
/* 503 */       int i2 = outcode(k, m, paramInt1, paramInt2, i, j);
/* 504 */       int i3 = outcode(n, i1, paramInt1, paramInt2, i, j);
/* 505 */       while ((i2 | i3) != 0) {
/*     */         
/* 507 */         if ((i2 & i3) != 0) {
/* 508 */           return false;
/*     */         }
/* 510 */         if (i2 != 0) {
/* 511 */           if (0 != (i2 & 0x3)) {
/* 512 */             if (0 != (i2 & 0x1)) {
/* 513 */               m = paramInt2;
/*     */             } else {
/* 515 */               m = j;
/*     */             } 
/* 517 */             int i9 = m - paramArrayOfint[1];
/* 518 */             if (i9 < 0) {
/* 519 */               i9 = -i9;
/*     */             }
/* 521 */             int i8 = 2 * i9 * i6 + i7;
/* 522 */             if (bool) {
/* 523 */               i8 += i7 - i6 - 1;
/*     */             }
/* 525 */             i8 /= 2 * i7;
/* 526 */             if (i4 < 0) {
/* 527 */               i8 = -i8;
/*     */             }
/* 529 */             k = paramArrayOfint[0] + i8;
/* 530 */           } else if (0 != (i2 & 0xC)) {
/*     */             
/* 532 */             if (0 != (i2 & 0x4)) {
/* 533 */               k = paramInt1;
/*     */             } else {
/* 535 */               k = i;
/*     */             } 
/* 537 */             int i8 = k - paramArrayOfint[0];
/* 538 */             if (i8 < 0) {
/* 539 */               i8 = -i8;
/*     */             }
/* 541 */             int i9 = 2 * i8 * i7 + i6;
/* 542 */             if (!bool) {
/* 543 */               i9 += i6 - i7 - 1;
/*     */             }
/* 545 */             i9 /= 2 * i6;
/* 546 */             if (i5 < 0) {
/* 547 */               i9 = -i9;
/*     */             }
/* 549 */             m = paramArrayOfint[1] + i9;
/*     */           } 
/* 551 */           i2 = outcode(k, m, paramInt1, paramInt2, i, j); continue;
/*     */         } 
/* 553 */         if (0 != (i3 & 0x3)) {
/* 554 */           if (0 != (i3 & 0x1)) {
/* 555 */             i1 = paramInt2;
/*     */           } else {
/* 557 */             i1 = j;
/*     */           } 
/* 559 */           int i9 = i1 - paramArrayOfint[3];
/* 560 */           if (i9 < 0) {
/* 561 */             i9 = -i9;
/*     */           }
/* 563 */           int i8 = 2 * i9 * i6 + i7;
/* 564 */           if (bool) {
/* 565 */             i8 += i7 - i6;
/*     */           } else {
/* 567 */             i8--;
/*     */           } 
/* 569 */           i8 /= 2 * i7;
/* 570 */           if (i4 > 0) {
/* 571 */             i8 = -i8;
/*     */           }
/* 573 */           n = paramArrayOfint[2] + i8;
/* 574 */         } else if (0 != (i3 & 0xC)) {
/*     */           
/* 576 */           if (0 != (i3 & 0x4)) {
/* 577 */             n = paramInt1;
/*     */           } else {
/* 579 */             n = i;
/*     */           } 
/* 581 */           int i8 = n - paramArrayOfint[2];
/* 582 */           if (i8 < 0) {
/* 583 */             i8 = -i8;
/*     */           }
/* 585 */           int i9 = 2 * i8 * i7 + i6;
/* 586 */           if (bool) {
/* 587 */             i9--;
/*     */           } else {
/* 589 */             i9 += i6 - i7;
/*     */           } 
/* 591 */           i9 /= 2 * i6;
/* 592 */           if (i5 > 0) {
/* 593 */             i9 = -i9;
/*     */           }
/* 595 */           i1 = paramArrayOfint[3] + i9;
/*     */         } 
/* 597 */         i3 = outcode(n, i1, paramInt1, paramInt2, i, j);
/*     */       } 
/*     */       
/* 600 */       paramArrayOfint[0] = k;
/* 601 */       paramArrayOfint[1] = m;
/* 602 */       paramArrayOfint[2] = n;
/* 603 */       paramArrayOfint[3] = i1;
/* 604 */       paramArrayOfint[4] = i4;
/* 605 */       paramArrayOfint[5] = i5;
/* 606 */       paramArrayOfint[6] = i6;
/* 607 */       paramArrayOfint[7] = i7;
/*     */     } 
/* 609 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static PixelWriter createSolidPixelWriter(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData) {
/* 615 */     ColorModel colorModel = paramSurfaceData.getColorModel();
/* 616 */     Object object = colorModel.getDataElements(paramSunGraphics2D.eargb, null);
/*     */     
/* 618 */     return new SolidPixelWriter(object);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static PixelWriter createXorPixelWriter(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData) {
/* 624 */     ColorModel colorModel = paramSurfaceData.getColorModel();
/*     */     
/* 626 */     Object object1 = colorModel.getDataElements(paramSunGraphics2D.eargb, null);
/*     */     
/* 628 */     XORComposite xORComposite = (XORComposite)paramSunGraphics2D.getComposite();
/* 629 */     int i = xORComposite.getXorColor().getRGB();
/* 630 */     Object object2 = colorModel.getDataElements(i, null);
/*     */     
/* 632 */     switch (colorModel.getTransferType()) {
/*     */       case 0:
/* 634 */         return new XorPixelWriter.ByteData(object1, object2);
/*     */       case 1:
/*     */       case 2:
/* 637 */         return new XorPixelWriter.ShortData(object1, object2);
/*     */       case 3:
/* 639 */         return new XorPixelWriter.IntData(object1, object2);
/*     */       case 4:
/* 641 */         return new XorPixelWriter.FloatData(object1, object2);
/*     */       case 5:
/* 643 */         return new XorPixelWriter.DoubleData(object1, object2);
/*     */     } 
/* 645 */     throw new InternalError("Unsupported XOR pixel type");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/GeneralRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */