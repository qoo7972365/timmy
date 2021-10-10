/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Hashtable;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PNGImageDecoder
/*     */   extends ImageDecoder
/*     */ {
/*     */   private static final int GRAY = 0;
/*     */   private static final int PALETTE = 1;
/*     */   private static final int COLOR = 2;
/*     */   private static final int ALPHA = 4;
/*     */   private static final int bKGDChunk = 1649100612;
/*     */   private static final int cHRMChunk = 1665684045;
/*     */   private static final int gAMAChunk = 1732332865;
/*     */   private static final int hISTChunk = 1749635924;
/*     */   private static final int IDATChunk = 1229209940;
/*     */   private static final int IENDChunk = 1229278788;
/*     */   private static final int IHDRChunk = 1229472850;
/*     */   private static final int PLTEChunk = 1347179589;
/*     */   private static final int pHYsChunk = 1883789683;
/*     */   private static final int sBITChunk = 1933723988;
/*     */   private static final int tEXtChunk = 1950701684;
/*     */   private static final int tIMEChunk = 1950960965;
/*     */   private static final int tRNSChunk = 1951551059;
/*     */   private static final int zTXtChunk = 2052348020;
/*     */   private int width;
/*     */   private int height;
/*     */   private int bitDepth;
/*     */   private int colorType;
/*     */   private int compressionMethod;
/*     */   private int filterMethod;
/*     */   private int interlaceMethod;
/*  70 */   private int gamma = 100000;
/*     */   private Hashtable properties;
/*     */   private ColorModel cm;
/*     */   private byte[] red_map;
/*     */   private byte[] green_map;
/*     */   private byte[] blue_map;
/*     */   private byte[] alpha_map;
/*  77 */   private int transparentPixel = -1;
/*  78 */   private byte[] transparentPixel_16 = null;
/*  79 */   private static ColorModel[] greyModels = new ColorModel[4];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void property(String paramString, Object paramObject) {
/*  85 */     if (paramObject == null)
/*  86 */       return;  if (this.properties == null) this.properties = new Hashtable<>(); 
/*  87 */     this.properties.put(paramString, paramObject);
/*     */   }
/*     */   private void property(String paramString, float paramFloat) {
/*  90 */     property(paramString, new Float(paramFloat));
/*     */   }
/*     */   
/*  93 */   private final void pngassert(boolean paramBoolean) throws IOException { if (!paramBoolean) {
/*  94 */       PNGException pNGException = new PNGException("Broken file");
/*  95 */       pNGException.printStackTrace();
/*  96 */       throw pNGException;
/*     */     }  } protected boolean handleChunk(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws IOException { Color color;
/*     */     int i;
/*     */     int j;
/*     */     int k;
/* 101 */     switch (paramInt1) {
/*     */       case 1649100612:
/* 103 */         color = null;
/* 104 */         switch (this.colorType) {
/*     */           case 2:
/*     */           case 6:
/* 107 */             pngassert((paramInt3 == 6));
/* 108 */             color = new Color(paramArrayOfbyte[paramInt2] & 0xFF, paramArrayOfbyte[paramInt2 + 2] & 0xFF, paramArrayOfbyte[paramInt2 + 4] & 0xFF);
/*     */             break;
/*     */           case 3:
/*     */           case 7:
/* 112 */             pngassert((paramInt3 == 1));
/* 113 */             i = paramArrayOfbyte[paramInt2] & 0xFF;
/* 114 */             pngassert((this.red_map != null && i < this.red_map.length));
/* 115 */             color = new Color(this.red_map[i] & 0xFF, this.green_map[i] & 0xFF, this.blue_map[i] & 0xFF);
/*     */             break;
/*     */           case 0:
/*     */           case 4:
/* 119 */             pngassert((paramInt3 == 2));
/* 120 */             j = paramArrayOfbyte[paramInt2] & 0xFF;
/* 121 */             color = new Color(j, j, j);
/*     */             break;
/*     */         } 
/* 124 */         if (color != null) property("background", color); 
/*     */         break;
/*     */       case 1665684045:
/* 127 */         property("chromaticities", new Chromaticities(
/*     */               
/* 129 */               getInt(paramInt2), 
/* 130 */               getInt(paramInt2 + 4), 
/* 131 */               getInt(paramInt2 + 8), 
/* 132 */               getInt(paramInt2 + 12), 
/* 133 */               getInt(paramInt2 + 16), 
/* 134 */               getInt(paramInt2 + 20), 
/* 135 */               getInt(paramInt2 + 24), 
/* 136 */               getInt(paramInt2 + 28)));
/*     */         break;
/*     */       case 1732332865:
/* 139 */         if (paramInt3 != 4) throw new PNGException("bogus gAMA"); 
/* 140 */         this.gamma = getInt(paramInt2);
/* 141 */         if (this.gamma != 100000) property("gamma", this.gamma / 100000.0F); 
/*     */         break;
/*     */       case 1229209940:
/* 144 */         return false;
/*     */       
/*     */       case 1229472850:
/* 147 */         if (paramInt3 != 13 || (this
/* 148 */           .width = getInt(paramInt2)) == 0 || (this
/* 149 */           .height = getInt(paramInt2 + 4)) == 0)
/* 150 */           throw new PNGException("bogus IHDR"); 
/* 151 */         this.bitDepth = getByte(paramInt2 + 8);
/* 152 */         this.colorType = getByte(paramInt2 + 9);
/* 153 */         this.compressionMethod = getByte(paramInt2 + 10);
/* 154 */         this.filterMethod = getByte(paramInt2 + 11);
/* 155 */         this.interlaceMethod = getByte(paramInt2 + 12);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1347179589:
/* 161 */         i = paramInt3 / 3;
/* 162 */         this.red_map = new byte[i];
/* 163 */         this.green_map = new byte[i];
/* 164 */         this.blue_map = new byte[i];
/* 165 */         for (j = 0, k = paramInt2; j < i; j++, k += 3) {
/* 166 */           this.red_map[j] = paramArrayOfbyte[k];
/* 167 */           this.green_map[j] = paramArrayOfbyte[k + 1];
/* 168 */           this.blue_map[j] = paramArrayOfbyte[k + 2];
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1950701684:
/* 175 */         i = 0;
/* 176 */         for (; i < paramInt3 && paramArrayOfbyte[paramInt2 + i] != 0; i++);
/* 177 */         if (i < paramInt3) {
/* 178 */           String str1 = new String(paramArrayOfbyte, paramInt2, i);
/* 179 */           String str2 = new String(paramArrayOfbyte, paramInt2 + i + 1, paramInt3 - i - 1);
/* 180 */           property(str1, str2);
/*     */         } 
/*     */         break;
/*     */       case 1950960965:
/* 184 */         property("modtime", (new GregorianCalendar(
/* 185 */               getShort(paramInt2 + 0), 
/* 186 */               getByte(paramInt2 + 2) - 1, 
/* 187 */               getByte(paramInt2 + 3), 
/* 188 */               getByte(paramInt2 + 4), 
/* 189 */               getByte(paramInt2 + 5), 
/* 190 */               getByte(paramInt2 + 6))).getTime());
/*     */         break;
/*     */       case 1951551059:
/* 193 */         switch (this.colorType) {
/*     */           case 3:
/*     */           case 7:
/* 196 */             j = paramInt3;
/* 197 */             if (this.red_map != null) j = this.red_map.length; 
/* 198 */             this.alpha_map = new byte[j];
/* 199 */             System.arraycopy(paramArrayOfbyte, paramInt2, this.alpha_map, 0, (paramInt3 < j) ? paramInt3 : j);
/* 200 */             for (; --j >= paramInt3; this.alpha_map[j] = -1);
/*     */             break;
/*     */           case 2:
/*     */           case 6:
/* 204 */             pngassert((paramInt3 == 6));
/* 205 */             if (this.bitDepth == 16) {
/* 206 */               this.transparentPixel_16 = new byte[6];
/* 207 */               for (k = 0; k < 6; k++)
/* 208 */                 this.transparentPixel_16[k] = (byte)getByte(paramInt2 + k); 
/*     */               break;
/*     */             } 
/* 211 */             this
/*     */ 
/*     */               
/* 214 */               .transparentPixel = (getShort(paramInt2 + 0) & 0xFF) << 16 | (getShort(paramInt2 + 2) & 0xFF) << 8 | getShort(paramInt2 + 4) & 0xFF;
/*     */             break;
/*     */           
/*     */           case 0:
/*     */           case 4:
/* 219 */             pngassert((paramInt3 == 2));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 224 */             k = getShort(paramInt2);
/* 225 */             k = 0xFF & ((this.bitDepth == 16) ? (k >> 8) : k);
/* 226 */             this.transparentPixel = k << 16 | k << 8 | k;
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */     } 
/* 232 */     return true; }
/*     */   
/*     */   public class PNGException extends IOException { PNGException(String param1String) {
/* 235 */       super(param1String);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void produceImage() throws IOException, ImageFormatException {
/*     */     
/*     */     try { byte b2, b6, b7;
/* 246 */       for (byte b1 = 0; b1 < signature.length; b1++) {
/* 247 */         if ((signature[b1] & 0xFF) != this.underlyingInputStream.read())
/* 248 */           throw new PNGException("Chunk signature mismatch"); 
/*     */       } 
/* 250 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(new InflaterInputStream(this.inputStream, new Inflater()));
/*     */       
/* 252 */       getData();
/*     */       
/* 254 */       byte[] arrayOfByte = null;
/* 255 */       int[] arrayOfInt = null;
/* 256 */       int i = this.width;
/*     */       
/* 258 */       byte b3 = 0;
/* 259 */       switch (this.bitDepth) { case 1:
/* 260 */           b3 = 0; break;
/* 261 */         case 2: b3 = 1; break;
/* 262 */         case 4: b3 = 2; break;
/* 263 */         case 8: b3 = 3; break;
/* 264 */         case 16: b3 = 4; break;
/* 265 */         default: throw new PNGException("invalid depth"); }
/*     */       
/* 267 */       if (this.interlaceMethod != 0) { i *= this.height; b2 = this.width; }
/* 268 */       else { b2 = 0; }
/* 269 */        int j = this.colorType | this.bitDepth << 3;
/* 270 */       int k = (1 << ((this.bitDepth >= 8) ? 8 : this.bitDepth)) - 1;
/*     */       
/* 272 */       switch (this.colorType) {
/*     */         case 3:
/*     */         case 7:
/* 275 */           if (this.red_map == null) throw new PNGException("palette expected"); 
/* 276 */           if (this.alpha_map == null) {
/* 277 */             this.cm = new IndexColorModel(this.bitDepth, this.red_map.length, this.red_map, this.green_map, this.blue_map);
/*     */           } else {
/*     */             
/* 280 */             this.cm = new IndexColorModel(this.bitDepth, this.red_map.length, this.red_map, this.green_map, this.blue_map, this.alpha_map);
/*     */           } 
/* 282 */           arrayOfByte = new byte[i];
/*     */           break;
/*     */         case 0:
/* 285 */           b4 = (b3 >= 4) ? 3 : b3;
/* 286 */           if ((this.cm = greyModels[b4]) == null) {
/* 287 */             int i1 = 1 << 1 << b4;
/*     */             
/* 289 */             byte[] arrayOfByte1 = new byte[i1];
/* 290 */             for (byte b = 0; b < i1; ) { arrayOfByte1[b] = (byte)(255 * b / (i1 - 1)); b++; }
/*     */             
/* 292 */             if (this.transparentPixel == -1) {
/* 293 */               this.cm = new IndexColorModel(this.bitDepth, arrayOfByte1.length, arrayOfByte1, arrayOfByte1, arrayOfByte1);
/*     */             } else {
/* 295 */               this.cm = new IndexColorModel(this.bitDepth, arrayOfByte1.length, arrayOfByte1, arrayOfByte1, arrayOfByte1, this.transparentPixel & 0xFF);
/*     */             } 
/*     */             
/* 298 */             greyModels[b4] = this.cm;
/*     */           } 
/*     */           
/* 301 */           arrayOfByte = new byte[i];
/*     */           break;
/*     */         case 2:
/*     */         case 4:
/*     */         case 6:
/* 306 */           this.cm = ColorModel.getRGBdefault();
/* 307 */           arrayOfInt = new int[i];
/*     */           break;
/*     */         default:
/* 310 */           throw new PNGException("invalid color type");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       setDimensions(this.width, this.height);
/* 321 */       setColorModel(this.cm);
/* 322 */       byte b4 = (this.interlaceMethod != 0) ? 6 : 30;
/*     */ 
/*     */ 
/*     */       
/* 326 */       setHints(b4);
/* 327 */       headerComplete();
/*     */ 
/*     */       
/* 330 */       byte b5 = ((this.colorType & 0x1) != 0) ? 1 : ((((this.colorType & 0x2) != 0) ? 3 : 1) + (((this.colorType & 0x4) != 0) ? 1 : 0));
/*     */       
/* 332 */       int m = b5 * this.bitDepth;
/* 333 */       int n = m + 7 >> 3;
/*     */       
/* 335 */       if (this.interlaceMethod == 0) { b6 = -1; b7 = 0; }
/* 336 */       else { b6 = 0; b7 = 7; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       while (++b6 <= b7) {
/* 343 */         byte b8 = startingRow[b6];
/* 344 */         byte b9 = rowIncrement[b6];
/* 345 */         byte b10 = colIncrement[b6];
/* 346 */         byte b11 = blockWidth[b6];
/* 347 */         byte b12 = blockHeight[b6];
/* 348 */         byte b13 = startingCol[b6];
/* 349 */         int i1 = (this.width - b13 + b10 - 1) / b10;
/* 350 */         int i2 = i1 * m + 7 >> 3;
/* 351 */         if (i2 == 0)
/* 352 */           continue;  boolean bool1 = (this.interlaceMethod == 0) ? (b9 * this.width) : false;
/* 353 */         int i3 = b2 * b8;
/* 354 */         boolean bool2 = true;
/*     */         
/* 356 */         byte[] arrayOfByte1 = new byte[i2];
/* 357 */         byte[] arrayOfByte2 = new byte[i2];
/*     */ 
/*     */ 
/*     */         
/* 361 */         while (b8 < this.height) {
/* 362 */           int i5 = bufferedInputStream.read(); int i6;
/* 363 */           for (i6 = 0; i6 < i2; ) {
/* 364 */             int i8 = bufferedInputStream.read(arrayOfByte1, i6, i2 - i6);
/* 365 */             if (i8 <= 0) throw new PNGException("missing data"); 
/* 366 */             i6 += i8;
/*     */           } 
/* 368 */           filterRow(arrayOfByte1, bool2 ? null : arrayOfByte2, i5, i2, n);
/*     */ 
/*     */           
/* 371 */           i6 = b13;
/* 372 */           byte b = 0;
/* 373 */           int i7 = 0;
/* 374 */           while (i6 < this.width) {
/* 375 */             if (arrayOfInt != null)
/* 376 */             { int i8; int i9; switch (j) {
/*     */                 case 70:
/* 378 */                   arrayOfInt[i6 + i3] = (arrayOfByte1[b] & 0xFF) << 16 | (arrayOfByte1[b + 1] & 0xFF) << 8 | arrayOfByte1[b + 2] & 0xFF | (arrayOfByte1[b + 3] & 0xFF) << 24;
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 383 */                   b += 4;
/*     */                   break;
/*     */                 case 134:
/* 386 */                   arrayOfInt[i6 + i3] = (arrayOfByte1[b] & 0xFF) << 16 | (arrayOfByte1[b + 2] & 0xFF) << 8 | arrayOfByte1[b + 4] & 0xFF | (arrayOfByte1[b + 6] & 0xFF) << 24;
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 391 */                   b += 8;
/*     */                   break;
/*     */                 case 66:
/* 394 */                   i7 = (arrayOfByte1[b] & 0xFF) << 16 | (arrayOfByte1[b + 1] & 0xFF) << 8 | arrayOfByte1[b + 2] & 0xFF;
/*     */ 
/*     */ 
/*     */                   
/* 398 */                   if (i7 != this.transparentPixel) {
/* 399 */                     i7 |= 0xFF000000;
/*     */                   }
/* 401 */                   arrayOfInt[i6 + i3] = i7;
/* 402 */                   b += 3;
/*     */                   break;
/*     */                 case 130:
/* 405 */                   i7 = (arrayOfByte1[b] & 0xFF) << 16 | (arrayOfByte1[b + 2] & 0xFF) << 8 | arrayOfByte1[b + 4] & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 410 */                   i8 = (this.transparentPixel_16 != null) ? 1 : 0;
/* 411 */                   for (i9 = 0; i8 && i9 < 6; i9++) {
/* 412 */                     i8 &= ((arrayOfByte1[b + i9] & 0xFF) == (this.transparentPixel_16[i9] & 0xFF)) ? 1 : 0;
/*     */                   }
/*     */                   
/* 415 */                   if (i8 == 0) {
/* 416 */                     i7 |= 0xFF000000;
/*     */                   }
/* 418 */                   arrayOfInt[i6 + i3] = i7;
/* 419 */                   b += 6;
/*     */                   break;
/*     */                 case 68:
/* 422 */                   i9 = arrayOfByte1[b] & 0xFF;
/* 423 */                   arrayOfInt[i6 + i3] = i9 << 16 | i9 << 8 | i9 | (arrayOfByte1[b + 1] & 0xFF) << 24;
/*     */ 
/*     */                   
/* 426 */                   b += 2;
/*     */                   break;
/*     */                 case 132:
/* 429 */                   i9 = arrayOfByte1[b] & 0xFF;
/* 430 */                   arrayOfInt[i6 + i3] = i9 << 16 | i9 << 8 | i9 | (arrayOfByte1[b + 2] & 0xFF) << 24;
/*     */ 
/*     */                   
/* 433 */                   b += 4; break;
/*     */                 default:
/* 435 */                   throw new PNGException("illegal type/depth");
/*     */               }  }
/* 437 */             else { switch (this.bitDepth) {
/*     */                 case 1:
/* 439 */                   arrayOfByte[i6 + i3] = (byte)(arrayOfByte1[b >> 3] >> 7 - (b & 0x7) & 0x1);
/*     */                   
/* 441 */                   b++;
/*     */                   break;
/*     */                 case 2:
/* 444 */                   arrayOfByte[i6 + i3] = (byte)(arrayOfByte1[b >> 2] >> (3 - (b & 0x3)) * 2 & 0x3);
/*     */                   
/* 446 */                   b++;
/*     */                   break;
/*     */                 case 4:
/* 449 */                   arrayOfByte[i6 + i3] = (byte)(arrayOfByte1[b >> 1] >> (1 - (b & 0x1)) * 4 & 0xF);
/*     */                   
/* 451 */                   b++; break;
/*     */                 case 8:
/* 453 */                   arrayOfByte[i6 + i3] = arrayOfByte1[b++]; break;
/*     */                 case 16:
/* 455 */                   arrayOfByte[i6 + i3] = arrayOfByte1[b]; b += 2; break;
/*     */                 default:
/* 457 */                   throw new PNGException("illegal type/depth");
/*     */               } 
/*     */               
/*     */                }
/*     */             
/* 462 */             i6 += b10;
/*     */           } 
/* 464 */           if (this.interlaceMethod == 0) {
/* 465 */             if (arrayOfInt != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 470 */               sendPixels(0, b8, this.width, 1, arrayOfInt, 0, this.width);
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */ 
/*     */               
/* 478 */               sendPixels(0, b8, this.width, 1, arrayOfByte, 0, this.width);
/*     */             } 
/*     */           }
/* 481 */           int i4 = b8 + b9;
/* 482 */           i3 += b9 * b2;
/* 483 */           byte[] arrayOfByte3 = arrayOfByte1;
/* 484 */           arrayOfByte1 = arrayOfByte2;
/* 485 */           arrayOfByte2 = arrayOfByte3;
/* 486 */           bool2 = false;
/*     */         } 
/* 488 */         if (this.interlaceMethod != 0) {
/* 489 */           if (arrayOfInt != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 494 */             sendPixels(0, 0, this.width, this.height, arrayOfInt, 0, this.width);
/*     */ 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */ 
/*     */           
/* 502 */           sendPixels(0, 0, this.width, this.height, arrayOfByte, 0, this.width);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       imageComplete(3, true);
/*     */ 
/*     */       
/*     */        }
/*     */     
/* 524 */     catch (IOException iOException)
/* 525 */     { if (!this.aborted) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 530 */         property("error", iOException);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 535 */         imageComplete(3, true);
/* 536 */         throw iOException;
/*     */       }  }
/*     */     finally { 
/* 539 */       try { close(); } catch (Throwable throwable) {} }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sendPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 549 */     int i = setPixels(paramInt1, paramInt2, paramInt3, paramInt4, this.cm, paramArrayOfint, paramInt5, paramInt6);
/*     */     
/* 551 */     if (i <= 0) {
/* 552 */       this.aborted = true;
/*     */     }
/* 554 */     return !this.aborted;
/*     */   }
/*     */   
/*     */   private boolean sendPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 558 */     int i = setPixels(paramInt1, paramInt2, paramInt3, paramInt4, this.cm, paramArrayOfbyte, paramInt5, paramInt6);
/*     */     
/* 560 */     if (i <= 0) {
/* 561 */       this.aborted = true;
/*     */     }
/* 563 */     return !this.aborted;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void filterRow(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, int paramInt3) throws IOException {
/* 569 */     int i = 0;
/* 570 */     switch (paramInt1) {
/*     */       case 0:
/*     */         return;
/*     */       case 1:
/* 574 */         for (i = paramInt3; i < paramInt2; i++) {
/* 575 */           paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + paramArrayOfbyte1[i - paramInt3]);
/*     */         }
/*     */       case 2:
/* 578 */         if (paramArrayOfbyte2 != null)
/* 579 */           for (; i < paramInt2; i++) {
/* 580 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + paramArrayOfbyte2[i]);
/*     */           } 
/*     */       case 3:
/* 583 */         if (paramArrayOfbyte2 != null) {
/* 584 */           for (; i < paramInt3; i++)
/* 585 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + ((0xFF & paramArrayOfbyte2[i]) >> 1)); 
/* 586 */           for (; i < paramInt2; i++)
/* 587 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + ((paramArrayOfbyte2[i] & 0xFF) + (paramArrayOfbyte1[i - paramInt3] & 0xFF) >> 1)); 
/*     */         } else {
/* 589 */           for (i = paramInt3; i < paramInt2; i++)
/* 590 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + ((paramArrayOfbyte1[i - paramInt3] & 0xFF) >> 1)); 
/*     */         } 
/*     */       case 4:
/* 593 */         if (paramArrayOfbyte2 != null) {
/* 594 */           for (; i < paramInt3; i++)
/* 595 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + paramArrayOfbyte2[i]); 
/* 596 */           for (; i < paramInt2; i++) {
/*     */             
/* 598 */             int j = paramArrayOfbyte1[i - paramInt3] & 0xFF;
/* 599 */             int k = paramArrayOfbyte2[i] & 0xFF;
/* 600 */             int m = paramArrayOfbyte2[i - paramInt3] & 0xFF;
/* 601 */             int n = j + k - m;
/* 602 */             int i1 = (n > j) ? (n - j) : (j - n);
/* 603 */             int i2 = (n > k) ? (n - k) : (k - n);
/* 604 */             int i3 = (n > m) ? (n - m) : (m - n);
/* 605 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + ((i1 <= i2 && i1 <= i3) ? j : ((i2 <= i3) ? k : m)));
/*     */           } 
/*     */         } else {
/* 608 */           for (i = paramInt3; i < paramInt2; i++)
/* 609 */             paramArrayOfbyte1[i] = (byte)(paramArrayOfbyte1[i] + paramArrayOfbyte1[i - paramInt3]); 
/*     */         } 
/*     */     } 
/* 612 */     throw new PNGException("Illegal filter");
/*     */   }
/*     */   
/* 615 */   private static final byte[] startingRow = new byte[] { 0, 0, 0, 4, 0, 2, 0, 1 };
/* 616 */   private static final byte[] startingCol = new byte[] { 0, 0, 4, 0, 2, 0, 1, 0 };
/* 617 */   private static final byte[] rowIncrement = new byte[] { 1, 8, 8, 8, 4, 4, 2, 2 };
/* 618 */   private static final byte[] colIncrement = new byte[] { 1, 8, 8, 4, 4, 2, 2, 1 };
/* 619 */   private static final byte[] blockHeight = new byte[] { 1, 8, 8, 4, 4, 2, 2, 1 };
/* 620 */   private static final byte[] blockWidth = new byte[] { 1, 8, 4, 4, 2, 2, 1, 1 };
/*     */   int pos;
/*     */   int limit;
/*     */   int chunkStart;
/*     */   int chunkKey;
/*     */   int chunkLength;
/*     */   int chunkCRC;
/*     */   boolean seenEOF;
/* 628 */   private static final byte[] signature = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
/*     */   
/*     */   PNGFilterInputStream inputStream;
/*     */   
/*     */   InputStream underlyingInputStream;
/*     */   
/*     */   byte[] inbuf;
/*     */ 
/*     */   
/*     */   public PNGImageDecoder(InputStreamImageSource paramInputStreamImageSource, InputStream paramInputStream) throws IOException
/*     */   {
/* 639 */     super(paramInputStreamImageSource, paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 663 */     this.inbuf = new byte[4096];
/*     */     this.inputStream = new PNGFilterInputStream(this, paramInputStream);
/* 665 */     this.underlyingInputStream = this.inputStream.underlyingInputStream; } private void fill() throws IOException { if (!this.seenEOF) {
/* 666 */       if (this.pos > 0 && this.pos < this.limit) {
/* 667 */         System.arraycopy(this.inbuf, this.pos, this.inbuf, 0, this.limit - this.pos);
/* 668 */         this.limit -= this.pos;
/* 669 */         this.pos = 0;
/* 670 */       } else if (this.pos >= this.limit) {
/* 671 */         this.pos = 0; this.limit = 0;
/*     */       } 
/* 673 */       int i = this.inbuf.length;
/* 674 */       while (this.limit < i) {
/* 675 */         int j = this.underlyingInputStream.read(this.inbuf, this.limit, i - this.limit);
/* 676 */         if (j <= 0) { this.seenEOF = true; break; }
/* 677 */          this.limit += j;
/*     */       } 
/*     */     }  }
/*     */   
/*     */   private boolean need(int paramInt) throws IOException {
/* 682 */     if (this.limit - this.pos >= paramInt) return true; 
/* 683 */     fill();
/* 684 */     if (this.limit - this.pos >= paramInt) return true; 
/* 685 */     if (this.seenEOF) return false; 
/* 686 */     byte[] arrayOfByte = new byte[paramInt + 100];
/* 687 */     System.arraycopy(this.inbuf, this.pos, arrayOfByte, 0, this.limit - this.pos);
/* 688 */     this.limit -= this.pos;
/* 689 */     this.pos = 0;
/* 690 */     this.inbuf = arrayOfByte;
/* 691 */     fill();
/* 692 */     return (this.limit - this.pos >= paramInt);
/*     */   }
/*     */   private final int getInt(int paramInt) {
/* 695 */     return (this.inbuf[paramInt] & 0xFF) << 24 | (this.inbuf[paramInt + 1] & 0xFF) << 16 | (this.inbuf[paramInt + 2] & 0xFF) << 8 | this.inbuf[paramInt + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final int getShort(int paramInt) {
/* 701 */     return (short)((this.inbuf[paramInt] & 0xFF) << 8 | this.inbuf[paramInt + 1] & 0xFF);
/*     */   }
/*     */   
/*     */   private final int getByte(int paramInt) {
/* 705 */     return this.inbuf[paramInt] & 0xFF;
/*     */   }
/*     */   private final boolean getChunk() throws IOException {
/* 708 */     this.chunkLength = 0;
/* 709 */     if (!need(8)) return false; 
/* 710 */     this.chunkLength = getInt(this.pos);
/* 711 */     this.chunkKey = getInt(this.pos + 4);
/* 712 */     if (this.chunkLength < 0) throw new PNGException("bogus length: " + this.chunkLength); 
/* 713 */     if (!need(this.chunkLength + 12)) return false; 
/* 714 */     this.chunkCRC = getInt(this.pos + 8 + this.chunkLength);
/* 715 */     this.chunkStart = this.pos + 8;
/* 716 */     int i = crc(this.inbuf, this.pos + 4, this.chunkLength + 4);
/* 717 */     if (this.chunkCRC != i && checkCRC) throw new PNGException("crc corruption"); 
/* 718 */     this.pos += this.chunkLength + 12;
/* 719 */     return true;
/*     */   }
/*     */   private void readAll() throws IOException {
/* 722 */     for (; getChunk(); handleChunk(this.chunkKey, this.inbuf, this.chunkStart, this.chunkLength));
/*     */   }
/*     */   boolean getData() throws IOException {
/* 725 */     while (this.chunkLength == 0 && getChunk()) {
/* 726 */       if (handleChunk(this.chunkKey, this.inbuf, this.chunkStart, this.chunkLength))
/* 727 */         this.chunkLength = 0; 
/* 728 */     }  return (this.chunkLength > 0);
/*     */   }
/*     */   
/*     */   private static boolean checkCRC = true;
/*     */   
/* 733 */   public static boolean getCheckCRC() { return checkCRC; } public static void setCheckCRC(boolean paramBoolean) {
/* 734 */     checkCRC = paramBoolean;
/*     */   }
/*     */   protected void wrc(int paramInt) {
/* 737 */     paramInt &= 0xFF;
/* 738 */     if (paramInt <= 32 || paramInt > 122) paramInt = 63; 
/* 739 */     System.out.write(paramInt);
/*     */   }
/*     */   protected void wrk(int paramInt) {
/* 742 */     wrc(paramInt >> 24);
/* 743 */     wrc(paramInt >> 16);
/* 744 */     wrc(paramInt >> 8);
/* 745 */     wrc(paramInt);
/*     */   }
/*     */   public void print() {
/* 748 */     wrk(this.chunkKey);
/* 749 */     System.out.print(" " + this.chunkLength + "\n");
/*     */   }
/*     */ 
/*     */   
/* 753 */   private static final int[] crc_table = new int[256];
/*     */ 
/*     */   
/*     */   static {
/* 757 */     for (byte b = 0; b < 'Ā'; b++) {
/* 758 */       int i = b;
/* 759 */       for (byte b1 = 0; b1 < 8; b1++) {
/* 760 */         if ((i & 0x1) != 0)
/* 761 */         { i = 0xEDB88320 ^ i >>> 1; }
/*     */         else
/* 763 */         { i >>>= 1; } 
/* 764 */       }  crc_table[b] = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int update_crc(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 774 */     int i = paramInt1;
/* 775 */     while (--paramInt3 >= 0)
/* 776 */       i = crc_table[(i ^ paramArrayOfbyte[paramInt2++]) & 0xFF] ^ i >>> 8; 
/* 777 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int crc(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 782 */     return update_crc(-1, paramArrayOfbyte, paramInt1, paramInt2) ^ 0xFFFFFFFF;
/*     */   }
/*     */   public static class Chromaticities { public float whiteX; public float whiteY; public float redX; public float redY;
/*     */     
/*     */     Chromaticities(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
/* 787 */       this.whiteX = param1Int1 / 100000.0F;
/* 788 */       this.whiteY = param1Int2 / 100000.0F;
/* 789 */       this.redX = param1Int3 / 100000.0F;
/* 790 */       this.redY = param1Int4 / 100000.0F;
/* 791 */       this.greenX = param1Int5 / 100000.0F;
/* 792 */       this.greenY = param1Int6 / 100000.0F;
/* 793 */       this.blueX = param1Int7 / 100000.0F;
/* 794 */       this.blueY = param1Int8 / 100000.0F;
/*     */     } public float greenX; public float greenY; public float blueX; public float blueY;
/*     */     public String toString() {
/* 797 */       return "Chromaticities(white=" + this.whiteX + "," + this.whiteY + ";red=" + this.redX + "," + this.redY + ";green=" + this.greenX + "," + this.greenY + ";blue=" + this.blueX + "," + this.blueY + ")";
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/PNGImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */