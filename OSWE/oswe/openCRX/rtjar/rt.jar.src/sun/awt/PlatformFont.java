/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.peer.FontPeer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import sun.font.SunFontManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PlatformFont
/*     */   implements FontPeer
/*     */ {
/*     */   protected FontDescriptor[] componentFonts;
/*     */   protected char defaultChar;
/*     */   protected FontConfiguration fontConfig;
/*     */   protected FontDescriptor defaultFont;
/*     */   protected String familyName;
/*     */   private Object[] fontCache;
/*     */   
/*     */   static {
/*  39 */     NativeLibLoader.loadLibraries();
/*  40 */     initIDs();
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
/*  55 */   protected static int FONTCACHESIZE = 256;
/*  56 */   protected static int FONTCACHEMASK = FONTCACHESIZE - 1;
/*     */   protected static String osVersion;
/*     */   
/*     */   public PlatformFont(String paramString, int paramInt) {
/*  60 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/*  61 */     if (sunFontManager instanceof sun.java2d.FontSupport) {
/*  62 */       this.fontConfig = sunFontManager.getFontConfiguration();
/*     */     }
/*  64 */     if (this.fontConfig == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  69 */     this.familyName = paramString.toLowerCase(Locale.ENGLISH);
/*  70 */     if (!FontConfiguration.isLogicalFontFamilyName(this.familyName)) {
/*  71 */       this.familyName = this.fontConfig.getFallbackFamilyName(this.familyName, "sansserif");
/*     */     }
/*     */     
/*  74 */     this.componentFonts = this.fontConfig.getFontDescriptors(this.familyName, paramInt);
/*     */ 
/*     */ 
/*     */     
/*  78 */     char c = getMissingGlyphCharacter();
/*     */     
/*  80 */     this.defaultChar = '?';
/*  81 */     if (this.componentFonts.length > 0) {
/*  82 */       this.defaultFont = this.componentFonts[0];
/*     */     }
/*  84 */     for (byte b = 0; b < this.componentFonts.length; b++) {
/*  85 */       if (!this.componentFonts[b].isExcluded(c))
/*     */       {
/*     */ 
/*     */         
/*  89 */         if ((this.componentFonts[b]).encoder.canEncode(c)) {
/*  90 */           this.defaultFont = this.componentFonts[b];
/*  91 */           this.defaultChar = c;
/*     */           break;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public CharsetString[] makeMultiCharsetString(String paramString) {
/* 107 */     return makeMultiCharsetString(paramString.toCharArray(), 0, paramString.length(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharsetString[] makeMultiCharsetString(String paramString, boolean paramBoolean) {
/* 114 */     return makeMultiCharsetString(paramString.toCharArray(), 0, paramString.length(), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharsetString[] makeMultiCharsetString(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 124 */     return makeMultiCharsetString(paramArrayOfchar, paramInt1, paramInt2, true);
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
/*     */   public CharsetString[] makeMultiCharsetString(char[] paramArrayOfchar, int paramInt1, int paramInt2, boolean paramBoolean) {
/*     */     CharsetString[] arrayOfCharsetString;
/* 143 */     if (paramInt2 < 1) {
/* 144 */       return new CharsetString[0];
/*     */     }
/* 146 */     Vector<CharsetString> vector = null;
/* 147 */     char[] arrayOfChar = new char[paramInt2];
/* 148 */     char c = this.defaultChar;
/* 149 */     boolean bool = false;
/*     */     
/* 151 */     FontDescriptor fontDescriptor = this.defaultFont;
/*     */     
/*     */     byte b1;
/* 154 */     for (b1 = 0; b1 < this.componentFonts.length; b1++) {
/* 155 */       if (!this.componentFonts[b1].isExcluded(paramArrayOfchar[paramInt1]))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 163 */         if ((this.componentFonts[b1]).encoder.canEncode(paramArrayOfchar[paramInt1])) {
/* 164 */           fontDescriptor = this.componentFonts[b1];
/* 165 */           c = paramArrayOfchar[paramInt1];
/* 166 */           bool = true;
/*     */           break;
/*     */         }  } 
/*     */     } 
/* 170 */     if (!paramBoolean && !bool) {
/* 171 */       return null;
/*     */     }
/* 173 */     arrayOfChar[0] = c;
/*     */ 
/*     */     
/* 176 */     b1 = 0;
/* 177 */     for (byte b2 = 1; b2 < paramInt2; b2++) {
/* 178 */       char c1 = paramArrayOfchar[paramInt1 + b2];
/* 179 */       FontDescriptor fontDescriptor1 = this.defaultFont;
/* 180 */       c = this.defaultChar;
/* 181 */       bool = false;
/* 182 */       for (byte b = 0; b < this.componentFonts.length; b++) {
/* 183 */         if (!this.componentFonts[b].isExcluded(c1))
/*     */         {
/*     */ 
/*     */           
/* 187 */           if ((this.componentFonts[b]).encoder.canEncode(c1)) {
/* 188 */             fontDescriptor1 = this.componentFonts[b];
/* 189 */             c = c1;
/* 190 */             bool = true;
/*     */             break;
/*     */           }  } 
/*     */       } 
/* 194 */       if (!paramBoolean && !bool) {
/* 195 */         return null;
/*     */       }
/* 197 */       arrayOfChar[b2] = c;
/*     */       
/* 199 */       if (fontDescriptor != fontDescriptor1) {
/* 200 */         if (vector == null) {
/* 201 */           vector = new Vector(3);
/*     */         }
/* 203 */         vector.addElement(new CharsetString(arrayOfChar, b1, b2 - b1, fontDescriptor));
/*     */         
/* 205 */         fontDescriptor = fontDescriptor1;
/* 206 */         fontDescriptor1 = this.defaultFont;
/* 207 */         b1 = b2;
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     CharsetString charsetString = new CharsetString(arrayOfChar, b1, paramInt2 - b1, fontDescriptor);
/*     */     
/* 213 */     if (vector == null) {
/* 214 */       arrayOfCharsetString = new CharsetString[1];
/* 215 */       arrayOfCharsetString[0] = charsetString;
/*     */     } else {
/* 217 */       vector.addElement(charsetString);
/* 218 */       arrayOfCharsetString = new CharsetString[vector.size()];
/* 219 */       for (byte b = 0; b < vector.size(); b++) {
/* 220 */         arrayOfCharsetString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/* 223 */     return arrayOfCharsetString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mightHaveMultiFontMetrics() {
/* 231 */     return (this.fontConfig != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] makeConvertedMultiFontString(String paramString) {
/* 239 */     return makeConvertedMultiFontChars(paramString.toCharArray(), 0, paramString.length());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] makeConvertedMultiFontChars(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 245 */     Object[] arrayOfObject = new Object[2];
/*     */     
/* 247 */     byte[] arrayOfByte = null;
/* 248 */     int i = paramInt1;
/* 249 */     byte b1 = 0;
/* 250 */     byte b2 = 0;
/*     */     
/* 252 */     FontDescriptor fontDescriptor1 = null;
/* 253 */     FontDescriptor fontDescriptor2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     int j = paramInt1 + paramInt2;
/* 259 */     if (paramInt1 < 0 || j > paramArrayOfchar.length) {
/* 260 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/*     */     
/* 263 */     if (i >= j) {
/* 264 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 268 */     while (i < j) {
/*     */       
/* 270 */       char c = paramArrayOfchar[i];
/*     */ 
/*     */       
/* 273 */       this; int k = c & FONTCACHEMASK;
/*     */       
/* 275 */       PlatformFontCache platformFontCache = (PlatformFontCache)getFontCache()[k];
/*     */ 
/*     */       
/* 278 */       if (platformFontCache == null || platformFontCache.uniChar != c) {
/*     */ 
/*     */         
/* 281 */         fontDescriptor1 = this.defaultFont;
/* 282 */         c = this.defaultChar;
/* 283 */         char c1 = paramArrayOfchar[i];
/* 284 */         int n = this.componentFonts.length;
/*     */         
/* 286 */         for (byte b = 0; b < n; b++) {
/* 287 */           FontDescriptor fontDescriptor = this.componentFonts[b];
/*     */           
/* 289 */           fontDescriptor.encoder.reset();
/*     */ 
/*     */           
/* 292 */           if (!fontDescriptor.isExcluded(c1))
/*     */           {
/*     */             
/* 295 */             if (fontDescriptor.encoder.canEncode(c1)) {
/* 296 */               fontDescriptor1 = fontDescriptor;
/* 297 */               c = c1;
/*     */               break;
/*     */             }  } 
/*     */         } 
/*     */         try {
/* 302 */           char[] arrayOfChar = new char[1];
/* 303 */           arrayOfChar[0] = c;
/*     */           
/* 305 */           platformFontCache = new PlatformFontCache();
/* 306 */           if (fontDescriptor1.useUnicode()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 312 */             if (FontDescriptor.isLE) {
/* 313 */               platformFontCache.bb.put((byte)(arrayOfChar[0] & 0xFF));
/* 314 */               platformFontCache.bb.put((byte)(arrayOfChar[0] >> 8));
/*     */             } else {
/* 316 */               platformFontCache.bb.put((byte)(arrayOfChar[0] >> 8));
/* 317 */               platformFontCache.bb.put((byte)(arrayOfChar[0] & 0xFF));
/*     */             } 
/*     */           } else {
/*     */             
/* 321 */             fontDescriptor1.encoder.encode(CharBuffer.wrap(arrayOfChar), platformFontCache.bb, true);
/*     */           } 
/*     */ 
/*     */           
/* 325 */           platformFontCache.fontDescriptor = fontDescriptor1;
/* 326 */           platformFontCache.uniChar = paramArrayOfchar[i];
/* 327 */           getFontCache()[k] = platformFontCache;
/* 328 */         } catch (Exception exception) {
/*     */           
/* 330 */           System.err.println(exception);
/* 331 */           exception.printStackTrace();
/* 332 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 337 */       if (fontDescriptor2 != platformFontCache.fontDescriptor) {
/* 338 */         if (fontDescriptor2 != null) {
/* 339 */           arrayOfObject[b2++] = fontDescriptor2;
/* 340 */           arrayOfObject[b2++] = arrayOfByte;
/*     */           
/* 342 */           if (arrayOfByte != null) {
/* 343 */             b1 -= true;
/* 344 */             arrayOfByte[0] = (byte)(b1 >> 24);
/* 345 */             arrayOfByte[1] = (byte)(b1 >> 16);
/* 346 */             arrayOfByte[2] = (byte)(b1 >> 8);
/* 347 */             arrayOfByte[3] = (byte)b1;
/*     */           } 
/*     */           
/* 350 */           if (b2 >= arrayOfObject.length) {
/* 351 */             Object[] arrayOfObject1 = new Object[arrayOfObject.length * 2];
/*     */             
/* 353 */             System.arraycopy(arrayOfObject, 0, arrayOfObject1, 0, arrayOfObject.length);
/*     */             
/* 355 */             arrayOfObject = arrayOfObject1;
/*     */           } 
/*     */         } 
/*     */         
/* 359 */         if (platformFontCache.fontDescriptor.useUnicode()) {
/*     */           
/* 361 */           arrayOfByte = new byte[(j - i + 1) * (int)platformFontCache.fontDescriptor.unicodeEncoder.maxBytesPerChar() + 4];
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 366 */           arrayOfByte = new byte[(j - i + 1) * (int)platformFontCache.fontDescriptor.encoder.maxBytesPerChar() + 4];
/*     */         } 
/*     */ 
/*     */         
/* 370 */         b1 = 4;
/*     */         
/* 372 */         fontDescriptor2 = platformFontCache.fontDescriptor;
/*     */       } 
/*     */       
/* 375 */       byte[] arrayOfByte1 = platformFontCache.bb.array();
/* 376 */       int m = platformFontCache.bb.position();
/* 377 */       if (m == 1) {
/* 378 */         arrayOfByte[b1++] = arrayOfByte1[0];
/*     */       }
/* 380 */       else if (m == 2) {
/* 381 */         arrayOfByte[b1++] = arrayOfByte1[0];
/* 382 */         arrayOfByte[b1++] = arrayOfByte1[1];
/* 383 */       } else if (m == 3) {
/* 384 */         arrayOfByte[b1++] = arrayOfByte1[0];
/* 385 */         arrayOfByte[b1++] = arrayOfByte1[1];
/* 386 */         arrayOfByte[b1++] = arrayOfByte1[2];
/* 387 */       } else if (m == 4) {
/* 388 */         arrayOfByte[b1++] = arrayOfByte1[0];
/* 389 */         arrayOfByte[b1++] = arrayOfByte1[1];
/* 390 */         arrayOfByte[b1++] = arrayOfByte1[2];
/* 391 */         arrayOfByte[b1++] = arrayOfByte1[3];
/*     */       } 
/* 393 */       i++;
/*     */     } 
/*     */     
/* 396 */     arrayOfObject[b2++] = fontDescriptor2;
/* 397 */     arrayOfObject[b2] = arrayOfByte;
/*     */ 
/*     */     
/* 400 */     if (arrayOfByte != null) {
/* 401 */       b1 -= 4;
/* 402 */       arrayOfByte[0] = (byte)(b1 >> 24);
/* 403 */       arrayOfByte[1] = (byte)(b1 >> 16);
/* 404 */       arrayOfByte[2] = (byte)(b1 >> 8);
/* 405 */       arrayOfByte[3] = (byte)b1;
/*     */     } 
/* 407 */     return arrayOfObject;
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
/*     */   protected final Object[] getFontCache() {
/* 422 */     if (this.fontCache == null) {
/* 423 */       this; this.fontCache = new Object[FONTCACHESIZE];
/*     */     } 
/*     */     
/* 426 */     return this.fontCache;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract char getMissingGlyphCharacter();
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   class PlatformFontCache
/*     */   {
/*     */     char uniChar;
/*     */     FontDescriptor fontDescriptor;
/* 438 */     ByteBuffer bb = ByteBuffer.allocate(4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/PlatformFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */