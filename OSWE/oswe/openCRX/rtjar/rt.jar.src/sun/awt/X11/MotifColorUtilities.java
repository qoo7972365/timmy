/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.security.AccessController;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MotifColorUtilities
/*     */ {
/*     */   static final float XmRED_LUMINOSITY = 0.3F;
/*     */   static final float XmGREEN_LUMINOSITY = 0.59F;
/*     */   static final float XmBLUE_LUMINOSITY = 0.11F;
/*     */   static final int XmINTENSITY_FACTOR = 75;
/*     */   static final int XmLIGHT_FACTOR = 0;
/*     */   static final int XmLUMINOSITY_FACTOR = 25;
/*     */   static final int XmMAX_SHORT = 65535;
/*     */   static final int XmCOLOR_PERCENTILE = 655;
/*     */   static final int XmDEFAULT_DARK_THRESHOLD = 20;
/*     */   static final int XmDEFAULT_LIGHT_THRESHOLD = 93;
/*     */   static final int XmDEFAULT_FOREGROUND_THRESHOLD = 70;
/*     */   static final int BLACK = -16777216;
/*     */   static final int WHITE = -1;
/*     */   static final int MOTIF_WINDOW_COLOR = -2105377;
/*     */   static final int DEFAULT_COLOR = -3881788;
/*     */   static final int XmCOLOR_LITE_THRESHOLD = 60915;
/*     */   static final int XmCOLOR_DARK_THRESHOLD = 13100;
/*     */   static final int XmFOREGROUND_THRESHOLD = 45850;
/*     */   static final int XmCOLOR_LITE_SEL_FACTOR = 15;
/*     */   static final int XmCOLOR_LITE_BS_FACTOR = 40;
/*     */   static final int XmCOLOR_LITE_TS_FACTOR = 20;
/*     */   static final int XmCOLOR_DARK_SEL_FACTOR = 15;
/*     */   static final int XmCOLOR_DARK_BS_FACTOR = 30;
/*     */   static final int XmCOLOR_DARK_TS_FACTOR = 50;
/*     */   static final int XmCOLOR_HI_SEL_FACTOR = 15;
/*     */   static final int XmCOLOR_HI_BS_FACTOR = 40;
/*     */   static final int XmCOLOR_HI_TS_FACTOR = 60;
/*     */   static final int XmCOLOR_LO_SEL_FACTOR = 15;
/*     */   static final int XmCOLOR_LO_BS_FACTOR = 60;
/*     */   static final int XmCOLOR_LO_TS_FACTOR = 50;
/*     */   
/*     */   static int brightness(int paramInt1, int paramInt2, int paramInt3) {
/* 112 */     paramInt1 <<= 8;
/* 113 */     paramInt2 <<= 8;
/* 114 */     paramInt3 <<= 8;
/*     */ 
/*     */     
/* 117 */     float f2 = ((paramInt1 + paramInt2 + paramInt3) / 3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     float f4 = (int)(0.3F * paramInt1 + 0.59F * paramInt2 + 0.11F * paramInt3);
/*     */ 
/*     */ 
/*     */     
/* 129 */     float f5 = ((paramInt1 > paramInt2) ? ((paramInt1 > paramInt3) ? paramInt1 : paramInt3) : ((paramInt2 > paramInt3) ? paramInt2 : paramInt3));
/*     */ 
/*     */ 
/*     */     
/* 133 */     float f6 = ((paramInt1 < paramInt2) ? ((paramInt1 < paramInt3) ? paramInt1 : paramInt3) : ((paramInt2 < paramInt3) ? paramInt2 : paramInt3));
/*     */ 
/*     */ 
/*     */     
/* 137 */     float f3 = (f6 + f5) / 2.0F;
/*     */     
/* 139 */     float f1 = (f2 * 75.0F + f3 * 0.0F + f4 * 25.0F) / 100.0F;
/*     */ 
/*     */     
/* 142 */     return Math.round(f1);
/*     */   }
/*     */ 
/*     */   
/*     */   static int calculateForegroundFromBackground(int paramInt1, int paramInt2, int paramInt3) {
/* 147 */     int i = -1;
/* 148 */     int j = brightness(paramInt1, paramInt2, paramInt3);
/*     */     
/* 150 */     if (j > 45850) {
/* 151 */       i = -16777216;
/*     */     } else {
/* 153 */       i = -1;
/*     */     } 
/* 155 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateTopShadowFromBackground(int paramInt1, int paramInt2, int paramInt3) {
/*     */     float f1, f2, f3;
/* 162 */     int i = paramInt1 << 8;
/* 163 */     int j = paramInt2 << 8;
/* 164 */     int k = paramInt3 << 8;
/*     */     
/* 166 */     int m = brightness(paramInt1, paramInt2, paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (m < 13100) {
/*     */ 
/*     */       
/* 175 */       float f = i;
/* 176 */       f += 50.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 178 */       f1 = f;
/*     */       
/* 180 */       f = j;
/* 181 */       f += 50.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 183 */       f2 = f;
/*     */       
/* 185 */       f = k;
/* 186 */       f += 50.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 188 */       f3 = f;
/*     */     }
/* 190 */     else if (m > 60915) {
/*     */ 
/*     */       
/* 193 */       float f = i;
/* 194 */       f -= f * 20.0F / 100.0F;
/* 195 */       f1 = f;
/*     */       
/* 197 */       f = j;
/* 198 */       f -= f * 20.0F / 100.0F;
/* 199 */       f2 = f;
/*     */       
/* 201 */       f = k;
/* 202 */       f -= f * 20.0F / 100.0F;
/* 203 */       f3 = f;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 208 */       float f5 = (50 + m * 10 / 65535);
/*     */ 
/*     */ 
/*     */       
/* 212 */       float f4 = i;
/* 213 */       f4 += f5 * (65535.0F - f4) / 100.0F;
/* 214 */       f1 = f4;
/*     */       
/* 216 */       f4 = j;
/* 217 */       f4 += f5 * (65535.0F - f4) / 100.0F;
/* 218 */       f2 = f4;
/*     */       
/* 220 */       f4 = k;
/* 221 */       f4 += f5 * (65535.0F - f4) / 100.0F;
/* 222 */       f3 = f4;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     int n = (int)f1 >> 8;
/* 229 */     int i1 = (int)f2 >> 8;
/* 230 */     int i2 = (int)f3 >> 8;
/*     */     
/* 232 */     return 0xFF000000 | n << 16 | i1 << 8 | i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateBottomShadowFromBackground(int paramInt1, int paramInt2, int paramInt3) {
/*     */     float f1, f2, f3;
/* 242 */     int i = paramInt1 << 8;
/* 243 */     int j = paramInt2 << 8;
/* 244 */     int k = paramInt3 << 8;
/*     */     
/* 246 */     int m = brightness(paramInt1, paramInt2, paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (m < 13100) {
/*     */       
/* 254 */       float f = i;
/* 255 */       f += 30.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 257 */       f1 = f;
/*     */       
/* 259 */       f = j;
/* 260 */       f += 30.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 262 */       f2 = f;
/*     */       
/* 264 */       f = k;
/* 265 */       f += 30.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 267 */       f3 = f;
/*     */     
/*     */     }
/* 270 */     else if (m > 60915) {
/*     */       
/* 272 */       float f = i;
/* 273 */       f -= f * 40.0F / 100.0F;
/* 274 */       f1 = f;
/*     */       
/* 276 */       f = j;
/* 277 */       f -= f * 40.0F / 100.0F;
/* 278 */       f2 = f;
/*     */       
/* 280 */       f = k;
/* 281 */       f -= f * 40.0F / 100.0F;
/* 282 */       f3 = f;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 287 */       float f5 = (60 + m * -20 / 65535);
/*     */ 
/*     */ 
/*     */       
/* 291 */       float f4 = i;
/* 292 */       f4 -= f4 * f5 / 100.0F;
/* 293 */       f1 = f4;
/*     */       
/* 295 */       f4 = j;
/* 296 */       f4 -= f4 * f5 / 100.0F;
/* 297 */       f2 = f4;
/*     */       
/* 299 */       f4 = k;
/* 300 */       f4 -= f4 * f5 / 100.0F;
/* 301 */       f3 = f4;
/*     */     } 
/*     */ 
/*     */     
/* 305 */     int n = (int)f1 >> 8;
/* 306 */     int i1 = (int)f2 >> 8;
/* 307 */     int i2 = (int)f3 >> 8;
/*     */     
/* 309 */     return 0xFF000000 | n << 16 | i1 << 8 | i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateSelectFromBackground(int paramInt1, int paramInt2, int paramInt3) {
/*     */     float f1, f2, f3;
/* 318 */     int i = paramInt1 << 8;
/* 319 */     int j = paramInt2 << 8;
/* 320 */     int k = paramInt3 << 8;
/*     */     
/* 322 */     int m = brightness(paramInt1, paramInt2, paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     if (m < 13100) {
/*     */       
/* 330 */       float f = i;
/* 331 */       f += 15.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 333 */       f1 = f;
/*     */       
/* 335 */       f = j;
/* 336 */       f += 15.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 338 */       f2 = f;
/*     */       
/* 340 */       f = k;
/* 341 */       f += 15.0F * (65535.0F - f) / 100.0F;
/*     */       
/* 343 */       f3 = f;
/*     */     
/*     */     }
/* 346 */     else if (m > 60915) {
/*     */       
/* 348 */       float f = i;
/* 349 */       f -= f * 15.0F / 100.0F;
/* 350 */       f1 = f;
/*     */       
/* 352 */       f = j;
/* 353 */       f -= f * 15.0F / 100.0F;
/* 354 */       f2 = f;
/*     */       
/* 356 */       f = k;
/* 357 */       f -= f * 15.0F / 100.0F;
/* 358 */       f3 = f;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 363 */       float f5 = (15 + m * 0 / 65535);
/*     */ 
/*     */ 
/*     */       
/* 367 */       float f4 = i;
/* 368 */       f4 -= f4 * f5 / 100.0F;
/* 369 */       f1 = f4;
/*     */       
/* 371 */       f4 = j;
/* 372 */       f4 -= f4 * f5 / 100.0F;
/* 373 */       f2 = f4;
/*     */       
/* 375 */       f4 = k;
/* 376 */       f4 -= f4 * f5 / 100.0F;
/* 377 */       f3 = f4;
/*     */     } 
/*     */ 
/*     */     
/* 381 */     int n = (int)f1 >> 8;
/* 382 */     int i1 = (int)f2 >> 8;
/* 383 */     int i2 = (int)f3 >> 8;
/*     */     
/* 385 */     return 0xFF000000 | n << 16 | i1 << 8 | i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadSystemColorsForCDE(int[] paramArrayOfint) throws Exception {
/* 392 */     XAtom xAtom = XAtom.get("RESOURCE_MANAGER");
/*     */     
/* 394 */     String str1 = xAtom.getProperty(XToolkit.getDefaultRootWindow());
/*     */     
/* 396 */     int i = str1.indexOf("ColorPalette:");
/* 397 */     int j = str1.length();
/* 398 */     for (; i < j && str1.charAt(i) != ':'; i++);
/* 399 */     i++;
/* 400 */     if (str1.charAt(i) == '\t') i++;
/*     */     
/* 402 */     String str2 = str1.substring(i, str1.indexOf("\n", i));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     String str3 = System.getProperty("user.home") + "/.dt/palettes/" + str2;
/*     */     
/* 410 */     File file = new File(str3);
/* 411 */     if (!file.exists()) {
/*     */ 
/*     */       
/* 414 */       str3 = "/usr/dt/palettes/" + str2;
/* 415 */       file = new File(str3);
/* 416 */       if (!file.exists())
/*     */       {
/* 418 */         throw new FileNotFoundException("Could not open : " + str3);
/*     */       }
/*     */     } 
/* 421 */     BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/*     */     
/* 423 */     int[] arrayOfInt = new int[8];
/*     */     
/*     */     int i1;
/*     */     
/* 427 */     for (i1 = 0; i1 < 8; i1++) {
/* 428 */       String str4 = bufferedReader.readLine();
/* 429 */       String str5 = str4.substring(1, str4.length());
/* 430 */       int i6 = Integer.valueOf(str5.substring(0, 4), 16).intValue() >> 8;
/* 431 */       int i7 = Integer.valueOf(str5.substring(4, 8), 16).intValue() >> 8;
/* 432 */       int i8 = Integer.valueOf(str5.substring(8, 12), 16).intValue() >> 8;
/* 433 */       arrayOfInt[i1] = 0xFF000000 | i6 << 16 | i7 << 8 | i8;
/*     */     } 
/*     */ 
/*     */     
/* 437 */     paramArrayOfint[1] = arrayOfInt[0];
/* 438 */     paramArrayOfint[3] = arrayOfInt[0];
/*     */     
/* 440 */     paramArrayOfint[4] = arrayOfInt[1];
/* 441 */     paramArrayOfint[6] = arrayOfInt[1];
/*     */     
/* 443 */     paramArrayOfint[7] = arrayOfInt[1];
/*     */     
/* 445 */     paramArrayOfint[8] = arrayOfInt[1];
/* 446 */     paramArrayOfint[10] = arrayOfInt[1];
/*     */     
/* 448 */     paramArrayOfint[12] = arrayOfInt[3];
/*     */     
/* 450 */     paramArrayOfint[23] = arrayOfInt[1];
/* 451 */     paramArrayOfint[17] = arrayOfInt[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 458 */     int k = (arrayOfInt[0] & 0xFF0000) >> 16;
/* 459 */     int m = (arrayOfInt[0] & 0xFF00) >> 8;
/* 460 */     int n = arrayOfInt[0] & 0xFF;
/*     */     
/* 462 */     i1 = calculateForegroundFromBackground(k, m, n);
/*     */     
/* 464 */     k = (arrayOfInt[1] & 0xFF0000) >> 16;
/* 465 */     m = (arrayOfInt[1] & 0xFF00) >> 8;
/* 466 */     n = arrayOfInt[1] & 0xFF;
/*     */     
/* 468 */     int i2 = calculateForegroundFromBackground(k, m, n);
/*     */     
/* 470 */     int i4 = calculateTopShadowFromBackground(k, m, n);
/* 471 */     int i5 = calculateBottomShadowFromBackground(k, m, n);
/*     */ 
/*     */     
/* 474 */     k = (arrayOfInt[3] & 0xFF0000) >> 16;
/* 475 */     m = (arrayOfInt[3] & 0xFF00) >> 8;
/* 476 */     n = arrayOfInt[3] & 0xFF;
/*     */     
/* 478 */     int i3 = calculateForegroundFromBackground(k, m, n);
/*     */ 
/*     */     
/* 481 */     paramArrayOfint[2] = i1;
/* 482 */     paramArrayOfint[5] = i2;
/* 483 */     paramArrayOfint[9] = i2;
/* 484 */     paramArrayOfint[11] = i2;
/* 485 */     paramArrayOfint[13] = i3;
/* 486 */     paramArrayOfint[14] = -16777216;
/* 487 */     paramArrayOfint[15] = -3881788;
/* 488 */     paramArrayOfint[18] = i2;
/* 489 */     Color color = new Color(i4);
/* 490 */     paramArrayOfint[19] = i4;
/* 491 */     paramArrayOfint[20] = color.brighter().getRGB();
/*     */     
/* 493 */     color = new Color(i5);
/* 494 */     paramArrayOfint[21] = i5;
/* 495 */     paramArrayOfint[22] = color.darker().getRGB();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadMotifDefaultColors(int[] paramArrayOfint) {
/* 501 */     paramArrayOfint[7] = -2105377;
/* 502 */     paramArrayOfint[12] = -1;
/* 503 */     paramArrayOfint[9] = -16777216;
/* 504 */     paramArrayOfint[11] = -16777216;
/* 505 */     paramArrayOfint[2] = -16777216;
/* 506 */     paramArrayOfint[5] = -16777216;
/* 507 */     paramArrayOfint[13] = -16777216;
/* 508 */     paramArrayOfint[14] = -16777216;
/* 509 */     paramArrayOfint[15] = -3881788;
/* 510 */     paramArrayOfint[18] = -16777216;
/* 511 */     paramArrayOfint[8] = -3881788;
/* 512 */     paramArrayOfint[10] = -3881788;
/* 513 */     paramArrayOfint[23] = -3881788;
/* 514 */     paramArrayOfint[17] = -2105377;
/*     */     
/* 516 */     char c1 = 'Ä';
/* 517 */     char c2 = 'Ä';
/* 518 */     char c3 = 'Ä';
/*     */ 
/*     */     
/* 521 */     int i = calculateTopShadowFromBackground(c1, c2, c3);
/* 522 */     int j = calculateBottomShadowFromBackground(c1, c2, c3);
/*     */     
/* 524 */     Color color = new Color(i);
/* 525 */     paramArrayOfint[19] = i;
/* 526 */     paramArrayOfint[20] = color.brighter().getRGB();
/*     */     
/* 528 */     color = new Color(j);
/* 529 */     paramArrayOfint[21] = j;
/* 530 */     paramArrayOfint[22] = color.darker().getRGB();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadSystemColors(int[] paramArrayOfint) {
/* 536 */     if ("Linux".equals(AccessController.doPrivileged(new GetPropertyAction("os.name")))) {
/* 537 */       loadMotifDefaultColors(paramArrayOfint);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 542 */         loadSystemColorsForCDE(paramArrayOfint);
/*     */       }
/* 544 */       catch (Exception exception) {
/*     */         
/* 546 */         loadMotifDefaultColors(paramArrayOfint);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/MotifColorUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */