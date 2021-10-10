/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NativeFont
/*     */   extends PhysicalFont
/*     */ {
/*     */   String encoding;
/*  61 */   private int numGlyphs = -1;
/*     */ 
/*     */   
/*     */   boolean isBitmapDelegate;
/*     */ 
/*     */   
/*     */   PhysicalFont delegateFont;
/*     */ 
/*     */   
/*     */   public NativeFont(String paramString, boolean paramBoolean) throws FontFormatException {
/*  71 */     super(paramString, (Object)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     this.isBitmapDelegate = paramBoolean;
/*     */     
/*  85 */     if (GraphicsEnvironment.isHeadless()) {
/*  86 */       throw new FontFormatException("Native font in headless toolkit");
/*     */     }
/*  88 */     this.fontRank = 5;
/*  89 */     initNames();
/*  90 */     if (getNumGlyphs() == 0) {
/*  91 */       throw new FontFormatException("Couldn't locate font" + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initNames() throws FontFormatException {
/* 101 */     int[] arrayOfInt = new int[14];
/* 102 */     byte b = 1;
/* 103 */     int i = 1;
/*     */     
/* 105 */     String str = this.platName.toLowerCase(Locale.ENGLISH);
/* 106 */     if (str.startsWith("-")) {
/* 107 */       while (i != -1 && b < 14) {
/* 108 */         i = str.indexOf('-', i);
/* 109 */         if (i != -1) {
/* 110 */           arrayOfInt[b++] = i;
/* 111 */           i++;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 116 */     if (b == 14 && i != -1) {
/*     */ 
/*     */       
/* 119 */       String str1 = str.substring(arrayOfInt[1] + 1, arrayOfInt[2]);
/* 120 */       StringBuilder stringBuilder = new StringBuilder(str1);
/* 121 */       char c = Character.toUpperCase(stringBuilder.charAt(0));
/* 122 */       stringBuilder.replace(0, 1, String.valueOf(c));
/* 123 */       for (byte b1 = 1; b1 < stringBuilder.length() - 1; b1++) {
/* 124 */         if (stringBuilder.charAt(b1) == ' ') {
/* 125 */           c = Character.toUpperCase(stringBuilder.charAt(b1 + 1));
/* 126 */           stringBuilder.replace(b1 + 1, b1 + 2, String.valueOf(c));
/*     */         } 
/*     */       } 
/* 129 */       this.familyName = stringBuilder.toString();
/*     */       
/* 131 */       String str2 = str.substring(arrayOfInt[2] + 1, arrayOfInt[3]);
/* 132 */       String str3 = str.substring(arrayOfInt[3] + 1, arrayOfInt[4]);
/*     */       
/* 134 */       String str4 = null;
/*     */       
/* 136 */       if (str2.indexOf("bold") >= 0 || str2
/* 137 */         .indexOf("demi") >= 0) {
/* 138 */         this.style |= 0x1;
/* 139 */         str4 = "Bold";
/*     */       } 
/*     */       
/* 142 */       if (str3.equals("i") || str3
/* 143 */         .indexOf("italic") >= 0) {
/* 144 */         this.style |= 0x2;
/*     */         
/* 146 */         if (str4 == null) {
/* 147 */           str4 = "Italic";
/*     */         } else {
/* 149 */           str4 = str4 + " Italic";
/*     */         }
/*     */       
/* 152 */       } else if (str3.equals("o") || str3
/* 153 */         .indexOf("oblique") >= 0) {
/* 154 */         this.style |= 0x2;
/* 155 */         if (str4 == null) {
/* 156 */           str4 = "Oblique";
/*     */         } else {
/* 158 */           str4 = str4 + " Oblique";
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       if (str4 == null) {
/* 163 */         this.fullName = this.familyName;
/*     */       } else {
/* 165 */         this.fullName = this.familyName + " " + str4;
/*     */       } 
/*     */       
/* 168 */       this.encoding = str.substring(arrayOfInt[12] + 1);
/* 169 */       if (this.encoding.startsWith("-")) {
/* 170 */         this.encoding = str.substring(arrayOfInt[13] + 1);
/*     */       }
/* 172 */       if (this.encoding.indexOf("fontspecific") >= 0) {
/* 173 */         if (str1.indexOf("dingbats") >= 0) {
/* 174 */           this.encoding = "dingbats";
/* 175 */         } else if (str1.indexOf("symbol") >= 0) {
/* 176 */           this.encoding = "symbol";
/*     */         } else {
/* 178 */           this.encoding = "iso8859-1";
/*     */         } 
/*     */       }
/*     */     } else {
/* 182 */       throw new FontFormatException("Bad native name " + this.platName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean hasExternalBitmaps(String paramString) {
/* 209 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/* 210 */     int i = stringBuilder.indexOf("-0-");
/* 211 */     while (i >= 0) {
/* 212 */       stringBuilder.replace(i + 1, i + 2, "*");
/* 213 */       i = stringBuilder.indexOf("-0-", i);
/*     */     } 
/* 215 */     String str = stringBuilder.toString();
/* 216 */     byte[] arrayOfByte = null;
/*     */     try {
/* 218 */       arrayOfByte = str.getBytes("UTF-8");
/* 219 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 220 */       arrayOfByte = str.getBytes();
/*     */     } 
/* 222 */     return haveBitmapFonts(arrayOfByte);
/*     */   }
/*     */   
/*     */   public static boolean fontExists(String paramString) {
/* 226 */     byte[] arrayOfByte = null;
/*     */     try {
/* 228 */       arrayOfByte = paramString.getBytes("UTF-8");
/* 229 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 230 */       arrayOfByte = paramString.getBytes();
/*     */     } 
/* 232 */     return fontExists(arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   private static native boolean haveBitmapFonts(byte[] paramArrayOfbyte);
/*     */   
/*     */   public CharToGlyphMapper getMapper() {
/* 239 */     if (this.mapper == null) {
/* 240 */       if (this.isBitmapDelegate) {
/*     */         
/* 242 */         this.mapper = new NativeGlyphMapper(this);
/*     */       } else {
/*     */         
/* 245 */         SunFontManager sunFontManager = SunFontManager.getInstance();
/* 246 */         this.delegateFont = sunFontManager.getDefaultPhysicalFont();
/* 247 */         this.mapper = this.delegateFont.getMapper();
/*     */       } 
/*     */     }
/* 250 */     return this.mapper;
/*     */   }
/*     */   private static native boolean fontExists(byte[] paramArrayOfbyte);
/*     */   FontStrike createStrike(FontStrikeDesc paramFontStrikeDesc) {
/* 254 */     if (this.isBitmapDelegate) {
/* 255 */       return new NativeStrike(this, paramFontStrikeDesc);
/*     */     }
/* 257 */     if (this.delegateFont == null) {
/* 258 */       SunFontManager sunFontManager = SunFontManager.getInstance();
/* 259 */       this.delegateFont = sunFontManager.getDefaultPhysicalFont();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 264 */     if (this.delegateFont instanceof NativeFont) {
/* 265 */       return new NativeStrike((NativeFont)this.delegateFont, paramFontStrikeDesc);
/*     */     }
/* 267 */     FontStrike fontStrike = this.delegateFont.createStrike(paramFontStrikeDesc);
/* 268 */     return new DelegateStrike(this, paramFontStrikeDesc, fontStrike);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle2D getMaxCharBounds(FontRenderContext paramFontRenderContext) {
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   native StrikeMetrics getFontMetrics(long paramLong);
/*     */   
/*     */   native float getGlyphAdvance(long paramLong, int paramInt);
/*     */   
/*     */   Rectangle2D.Float getGlyphOutlineBounds(long paramLong, int paramInt) {
/* 282 */     return new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralPath getGlyphOutline(long paramLong, int paramInt, float paramFloat1, float paramFloat2) {
/* 289 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   native long getGlyphImage(long paramLong, int paramInt);
/*     */   
/*     */   native long getGlyphImageNoDefault(long paramLong, int paramInt);
/*     */   
/*     */   void getGlyphMetrics(long paramLong, int paramInt, Point2D.Float paramFloat) {
/* 298 */     throw new RuntimeException("this should be called on the strike");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralPath getGlyphVectorOutline(long paramLong, int[] paramArrayOfint, int paramInt, float paramFloat1, float paramFloat2) {
/* 304 */     return null;
/*     */   }
/*     */   
/*     */   private native int countGlyphs(byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   public int getNumGlyphs() {
/* 310 */     if (this.numGlyphs == -1) {
/* 311 */       byte[] arrayOfByte = getPlatformNameBytes(8);
/* 312 */       this.numGlyphs = countGlyphs(arrayOfByte, 8);
/*     */     } 
/* 314 */     return this.numGlyphs;
/*     */   }
/*     */   
/*     */   PhysicalFont getDelegateFont() {
/* 318 */     if (this.delegateFont == null) {
/* 319 */       SunFontManager sunFontManager = SunFontManager.getInstance();
/* 320 */       this.delegateFont = sunFontManager.getDefaultPhysicalFont();
/*     */     } 
/* 322 */     return this.delegateFont;
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
/*     */   byte[] getPlatformNameBytes(int paramInt) {
/* 335 */     int[] arrayOfInt = new int[14];
/* 336 */     byte b = 1;
/* 337 */     int i = 1;
/*     */     
/* 339 */     while (i != -1 && b < 14) {
/* 340 */       i = this.platName.indexOf('-', i);
/* 341 */       if (i != -1) {
/* 342 */         arrayOfInt[b++] = i;
/* 343 */         i++;
/*     */       } 
/*     */     } 
/* 346 */     String str1 = Integer.toString(Math.abs(paramInt) * 10);
/* 347 */     StringBuilder stringBuilder = new StringBuilder(this.platName);
/*     */     
/* 349 */     stringBuilder.replace(arrayOfInt[11] + 1, arrayOfInt[12], "*");
/*     */     
/* 351 */     stringBuilder.replace(arrayOfInt[9] + 1, arrayOfInt[10], "72");
/*     */     
/* 353 */     stringBuilder.replace(arrayOfInt[8] + 1, arrayOfInt[9], "72");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 362 */     stringBuilder.replace(arrayOfInt[7] + 1, arrayOfInt[8], str1);
/*     */     
/* 364 */     stringBuilder.replace(arrayOfInt[6] + 1, arrayOfInt[7], "*");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (arrayOfInt[0] == 0 && arrayOfInt[1] == 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 378 */       stringBuilder.replace(arrayOfInt[0] + 1, arrayOfInt[1], "*");
/*     */     }
/*     */     
/* 381 */     String str2 = stringBuilder.toString();
/* 382 */     byte[] arrayOfByte = null;
/*     */     try {
/* 384 */       arrayOfByte = str2.getBytes("UTF-8");
/* 385 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 386 */       arrayOfByte = str2.getBytes();
/*     */     } 
/* 388 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 392 */     return " ** Native Font: Family=" + this.familyName + " Name=" + this.fullName + " style=" + this.style + " nativeName=" + this.platName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/NativeFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */