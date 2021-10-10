/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PixelConverter
/*     */ {
/*  49 */   public static final PixelConverter instance = new PixelConverter();
/*     */ 
/*     */   
/*  52 */   protected int alphaMask = 0;
/*     */   public int rgbToPixel(int paramInt, ColorModel paramColorModel) {
/*     */     byte[] arrayOfByte;
/*     */     int i;
/*     */     short[] arrayOfShort;
/*  57 */     Object object = paramColorModel.getDataElements(paramInt, null);
/*  58 */     switch (paramColorModel.getTransferType()) {
/*     */       case 0:
/*  60 */         arrayOfByte = (byte[])object;
/*  61 */         i = 0;
/*     */         
/*  63 */         switch (arrayOfByte.length) {
/*     */           default:
/*  65 */             i = arrayOfByte[3] << 24;
/*     */           
/*     */           case 3:
/*  68 */             i |= (arrayOfByte[2] & 0xFF) << 16;
/*     */           
/*     */           case 2:
/*  71 */             i |= (arrayOfByte[1] & 0xFF) << 8; break;
/*     */           case 1:
/*     */             break;
/*  74 */         }  i |= arrayOfByte[0] & 0xFF;
/*     */ 
/*     */         
/*  77 */         return i;
/*     */       case 1:
/*     */       case 2:
/*  80 */         arrayOfShort = (short[])object;
/*     */         
/*  82 */         return ((arrayOfShort.length > 1) ? (arrayOfShort[1] << 16) : 0) | arrayOfShort[0] & 0xFFFF;
/*     */       
/*     */       case 3:
/*  85 */         return ((int[])object)[0];
/*     */     } 
/*  87 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int pixelToRgb(int paramInt, ColorModel paramColorModel) {
/*  93 */     return paramInt;
/*     */   }
/*     */   
/*     */   public final int getAlphaMask() {
/*  97 */     return this.alphaMask;
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
/*     */   public static class Rgbx
/*     */     extends PixelConverter
/*     */   {
/* 125 */     public static final PixelConverter instance = new Rgbx();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 130 */       return param1Int << 8;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 134 */       return 0xFF000000 | param1Int >> 8;
/*     */     } }
/*     */   
/*     */   public static class Xrgb extends PixelConverter {
/* 138 */     public static final PixelConverter instance = new Xrgb();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 143 */       return param1Int;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 147 */       return 0xFF000000 | param1Int;
/*     */     }
/*     */   }
/*     */   
/* 151 */   public static class Argb extends PixelConverter { public static final PixelConverter instance = new Argb();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 158 */       return param1Int;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 162 */       return param1Int;
/*     */     } }
/*     */   
/*     */   public static class Ushort565Rgb extends PixelConverter {
/* 166 */     public static final PixelConverter instance = new Ushort565Rgb();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 171 */       return param1Int >> 8 & 0xF800 | param1Int >> 5 & 0x7E0 | param1Int >> 3 & 0x1F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 178 */       int i = param1Int >> 11 & 0x1F;
/* 179 */       i = i << 3 | i >> 2;
/* 180 */       int j = param1Int >> 5 & 0x3F;
/* 181 */       j = j << 2 | j >> 4;
/* 182 */       int k = param1Int & 0x1F;
/* 183 */       k = k << 3 | k >> 2;
/* 184 */       return 0xFF000000 | i << 16 | j << 8 | k;
/*     */     }
/*     */   }
/*     */   
/* 188 */   public static class Ushort555Rgbx extends PixelConverter { public static final PixelConverter instance = new Ushort555Rgbx();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 193 */       return param1Int >> 8 & 0xF800 | param1Int >> 5 & 0x7C0 | param1Int >> 2 & 0x3E;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 200 */       int i = param1Int >> 11 & 0x1F;
/* 201 */       i = i << 3 | i >> 2;
/* 202 */       int j = param1Int >> 6 & 0x1F;
/* 203 */       j = j << 3 | j >> 2;
/* 204 */       int k = param1Int >> 1 & 0x1F;
/* 205 */       k = k << 3 | k >> 2;
/* 206 */       return 0xFF000000 | i << 16 | j << 8 | k;
/*     */     } }
/*     */   
/*     */   public static class Ushort555Rgb extends PixelConverter {
/* 210 */     public static final PixelConverter instance = new Ushort555Rgb();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 215 */       return param1Int >> 9 & 0x7C00 | param1Int >> 6 & 0x3E0 | param1Int >> 3 & 0x1F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 222 */       int i = param1Int >> 10 & 0x1F;
/* 223 */       i = i << 3 | i >> 2;
/* 224 */       int j = param1Int >> 5 & 0x1F;
/* 225 */       j = j << 3 | j >> 2;
/* 226 */       int k = param1Int & 0x1F;
/* 227 */       k = k << 3 | k >> 2;
/* 228 */       return 0xFF000000 | i << 16 | j << 8 | k;
/*     */     }
/*     */   }
/*     */   
/* 232 */   public static class Ushort4444Argb extends PixelConverter { public static final PixelConverter instance = new Ushort4444Argb();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 241 */       int i = param1Int >> 16 & 0xF000;
/* 242 */       int j = param1Int >> 12 & 0xF00;
/* 243 */       int k = param1Int >> 8 & 0xF0;
/* 244 */       int m = param1Int >> 4 & 0xF;
/*     */       
/* 246 */       return i | j | k | m;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 253 */       int i = param1Int & 0xF000;
/* 254 */       i = (param1Int << 16 | param1Int << 12) & 0xFF000000;
/* 255 */       int j = param1Int & 0xF00;
/* 256 */       j = (param1Int << 12 | param1Int << 8) & 0xFF0000;
/* 257 */       int k = param1Int & 0xF0;
/* 258 */       k = (param1Int << 8 | param1Int << 4) & 0xFF00;
/* 259 */       int m = param1Int & 0xF;
/* 260 */       m = (param1Int << 4 | param1Int << 0) & 0xFF;
/*     */       
/* 262 */       return i | j | k | m;
/*     */     } }
/*     */   
/*     */   public static class Xbgr extends PixelConverter {
/* 266 */     public static final PixelConverter instance = new Xbgr();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 271 */       return (param1Int & 0xFF) << 16 | param1Int & 0xFF00 | param1Int >> 16 & 0xFF;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 277 */       return 0xFF000000 | (param1Int & 0xFF) << 16 | param1Int & 0xFF00 | param1Int >> 16 & 0xFF;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Bgrx
/*     */     extends PixelConverter
/*     */   {
/* 284 */     public static final PixelConverter instance = new Bgrx();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 289 */       return param1Int << 24 | (param1Int & 0xFF00) << 8 | param1Int >> 8 & 0xFF00;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 295 */       return 0xFF000000 | (param1Int & 0xFF00) << 8 | param1Int >> 8 & 0xFF00 | param1Int >>> 24;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rgba
/*     */     extends PixelConverter
/*     */   {
/* 302 */     public static final PixelConverter instance = new Rgba();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 309 */       return param1Int << 8 | param1Int >>> 24;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 313 */       return param1Int << 24 | param1Int >>> 8;
/*     */     } }
/*     */   
/*     */   public static class RgbaPre extends PixelConverter {
/* 317 */     public static final PixelConverter instance = new RgbaPre();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 324 */       if (param1Int >> 24 == -1) {
/* 325 */         return param1Int << 8 | param1Int >>> 24;
/*     */       }
/* 327 */       int i = param1Int >>> 24;
/* 328 */       int j = param1Int >> 16 & 0xFF;
/* 329 */       int k = param1Int >> 8 & 0xFF;
/* 330 */       int m = param1Int & 0xFF;
/* 331 */       int n = i + (i >> 7);
/* 332 */       j = j * n >> 8;
/* 333 */       k = k * n >> 8;
/* 334 */       m = m * n >> 8;
/* 335 */       return j << 24 | k << 16 | m << 8 | i;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 339 */       int i = param1Int & 0xFF;
/* 340 */       if (i == 255 || i == 0) {
/* 341 */         return param1Int >>> 8 | param1Int << 24;
/*     */       }
/* 343 */       int j = param1Int >>> 24;
/* 344 */       int k = param1Int >> 16 & 0xFF;
/* 345 */       int m = param1Int >> 8 & 0xFF;
/* 346 */       j = ((j << 8) - j) / i;
/* 347 */       k = ((k << 8) - k) / i;
/* 348 */       m = ((m << 8) - m) / i;
/* 349 */       return j << 24 | k << 16 | m << 8 | i;
/*     */     }
/*     */   }
/*     */   
/* 353 */   public static class ArgbPre extends PixelConverter { public static final PixelConverter instance = new ArgbPre();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 360 */       if (param1Int >> 24 == -1) {
/* 361 */         return param1Int;
/*     */       }
/* 363 */       int i = param1Int >>> 24;
/* 364 */       int j = param1Int >> 16 & 0xFF;
/* 365 */       int k = param1Int >> 8 & 0xFF;
/* 366 */       int m = param1Int & 0xFF;
/* 367 */       int n = i + (i >> 7);
/* 368 */       j = j * n >> 8;
/* 369 */       k = k * n >> 8;
/* 370 */       m = m * n >> 8;
/* 371 */       return i << 24 | j << 16 | k << 8 | m;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 375 */       int i = param1Int >>> 24;
/* 376 */       if (i == 255 || i == 0) {
/* 377 */         return param1Int;
/*     */       }
/* 379 */       int j = param1Int >> 16 & 0xFF;
/* 380 */       int k = param1Int >> 8 & 0xFF;
/* 381 */       int m = param1Int & 0xFF;
/* 382 */       j = ((j << 8) - j) / i;
/* 383 */       k = ((k << 8) - k) / i;
/* 384 */       m = ((m << 8) - m) / i;
/* 385 */       return i << 24 | j << 16 | k << 8 | m;
/*     */     } }
/*     */   
/*     */   public static class ArgbBm extends PixelConverter {
/* 389 */     public static final PixelConverter instance = new ArgbBm();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 394 */       return param1Int | param1Int >> 31 << 24;
/*     */     }
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 398 */       return param1Int << 7 >> 7;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ByteGray extends PixelConverter { static final double RED_MULT = 0.299D;
/*     */     static final double GRN_MULT = 0.587D;
/*     */     static final double BLU_MULT = 0.114D;
/* 405 */     public static final PixelConverter instance = new ByteGray();
/*     */     
/*     */     private ByteGray() {}
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 410 */       int i = param1Int >> 16 & 0xFF;
/* 411 */       int j = param1Int >> 8 & 0xFF;
/* 412 */       int k = param1Int & 0xFF;
/* 413 */       return (int)(i * 0.299D + j * 0.587D + k * 0.114D + 0.5D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 420 */       return ((0xFF00 | param1Int) << 8 | param1Int) << 8 | param1Int;
/*     */     } }
/*     */   
/*     */   public static class UshortGray extends ByteGray {
/*     */     static final double SHORT_MULT = 257.0D;
/*     */     static final double USHORT_RED_MULT = 76.843D;
/*     */     static final double USHORT_GRN_MULT = 150.85899999999998D;
/*     */     static final double USHORT_BLU_MULT = 29.298000000000002D;
/* 428 */     public static final PixelConverter instance = new UshortGray();
/*     */ 
/*     */ 
/*     */     
/*     */     public int rgbToPixel(int param1Int, ColorModel param1ColorModel) {
/* 433 */       int i = param1Int >> 16 & 0xFF;
/* 434 */       int j = param1Int >> 8 & 0xFF;
/* 435 */       int k = param1Int & 0xFF;
/* 436 */       return (int)(i * 76.843D + j * 150.85899999999998D + k * 29.298000000000002D + 0.5D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int pixelToRgb(int param1Int, ColorModel param1ColorModel) {
/* 443 */       param1Int >>= 8;
/* 444 */       return ((0xFF00 | param1Int) << 8 | param1Int) << 8 | param1Int;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/PixelConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */