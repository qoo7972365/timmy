/*     */ package sun.font;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XMap
/*     */ {
/*  40 */   private static HashMap xMappers = new HashMap<>();
/*     */   
/*     */   char[] convertedGlyphs;
/*     */   
/*     */   static final int SINGLE_BYTE = 1;
/*     */   
/*     */   static final int DOUBLE_BYTE = 2;
/*     */   
/*     */   private static final char SURR_MIN = '?';
/*     */   private static final char SURR_MAX = '?';
/*     */   
/*     */   static synchronized XMap getXMapper(String paramString) {
/*  52 */     XMap xMap = (XMap)xMappers.get(paramString);
/*  53 */     if (xMap == null) {
/*  54 */       xMap = getXMapperInternal(paramString);
/*  55 */       xMappers.put(paramString, xMap);
/*     */     } 
/*  57 */     return xMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMap getXMapperInternal(String paramString) {
/*  65 */     String str = null;
/*  66 */     byte b = 1;
/*  67 */     char c = '￿';
/*  68 */     char c1 = Character.MIN_VALUE;
/*  69 */     boolean bool1 = false;
/*  70 */     boolean bool2 = false;
/*  71 */     if (paramString.equals("dingbats")) {
/*  72 */       str = "sun.awt.motif.X11Dingbats";
/*  73 */       c1 = '✁';
/*  74 */       c = '➾';
/*  75 */     } else if (paramString.equals("symbol")) {
/*  76 */       str = "sun.awt.Symbol";
/*  77 */       c1 = 'Α';
/*  78 */       c = '⋯';
/*  79 */     } else if (paramString.equals("iso8859-1")) {
/*  80 */       c = 'ÿ';
/*  81 */     } else if (paramString.equals("iso8859-2")) {
/*  82 */       str = "ISO8859_2";
/*  83 */     } else if (paramString.equals("jisx0208.1983-0")) {
/*  84 */       str = "sun.awt.motif.X11JIS0208";
/*  85 */       b = 2;
/*  86 */     } else if (paramString.equals("jisx0201.1976-0")) {
/*  87 */       str = "sun.awt.motif.X11JIS0201";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       bool1 = true;
/*  95 */       bool2 = true;
/*  96 */     } else if (paramString.equals("jisx0212.1990-0")) {
/*  97 */       str = "sun.awt.motif.X11JIS0212";
/*  98 */       b = 2;
/*  99 */     } else if (paramString.equals("iso8859-4")) {
/* 100 */       str = "ISO8859_4";
/* 101 */     } else if (paramString.equals("iso8859-5")) {
/* 102 */       str = "ISO8859_5";
/* 103 */     } else if (paramString.equals("koi8-r")) {
/* 104 */       str = "KOI8_R";
/* 105 */     } else if (paramString.equals("ansi-1251")) {
/* 106 */       str = "windows-1251";
/* 107 */     } else if (paramString.equals("iso8859-6")) {
/* 108 */       str = "ISO8859_6";
/* 109 */     } else if (paramString.equals("iso8859-7")) {
/* 110 */       str = "ISO8859_7";
/* 111 */     } else if (paramString.equals("iso8859-8")) {
/* 112 */       str = "ISO8859_8";
/* 113 */     } else if (paramString.equals("iso8859-9")) {
/* 114 */       str = "ISO8859_9";
/* 115 */     } else if (paramString.equals("iso8859-13")) {
/* 116 */       str = "ISO8859_13";
/* 117 */     } else if (paramString.equals("iso8859-15")) {
/* 118 */       str = "ISO8859_15";
/* 119 */     } else if (paramString.equals("ksc5601.1987-0")) {
/* 120 */       str = "sun.awt.motif.X11KSC5601";
/* 121 */       b = 2;
/* 122 */     } else if (paramString.equals("ksc5601.1992-3")) {
/* 123 */       str = "sun.awt.motif.X11Johab";
/* 124 */       b = 2;
/* 125 */     } else if (paramString.equals("ksc5601.1987-1")) {
/* 126 */       str = "EUC_KR";
/* 127 */       b = 2;
/* 128 */     } else if (paramString.equals("cns11643-1")) {
/* 129 */       str = "sun.awt.motif.X11CNS11643P1";
/* 130 */       b = 2;
/* 131 */     } else if (paramString.equals("cns11643-2")) {
/* 132 */       str = "sun.awt.motif.X11CNS11643P2";
/* 133 */       b = 2;
/* 134 */     } else if (paramString.equals("cns11643-3")) {
/* 135 */       str = "sun.awt.motif.X11CNS11643P3";
/* 136 */       b = 2;
/* 137 */     } else if (paramString.equals("gb2312.1980-0")) {
/* 138 */       str = "sun.awt.motif.X11GB2312";
/* 139 */       b = 2;
/* 140 */     } else if (paramString.indexOf("big5") >= 0) {
/* 141 */       str = "Big5";
/* 142 */       b = 2;
/* 143 */       bool1 = true;
/* 144 */     } else if (paramString.equals("tis620.2533-0")) {
/* 145 */       str = "TIS620";
/* 146 */     } else if (paramString.equals("gbk-0")) {
/* 147 */       str = "sun.awt.motif.X11GBK";
/* 148 */       b = 2;
/* 149 */     } else if (paramString.indexOf("sun.unicode-0") >= 0) {
/* 150 */       str = "sun.awt.motif.X11SunUnicode_0";
/* 151 */       b = 2;
/* 152 */     } else if (paramString.indexOf("gb18030.2000-1") >= 0) {
/* 153 */       str = "sun.awt.motif.X11GB18030_1";
/* 154 */       b = 2;
/* 155 */     } else if (paramString.indexOf("gb18030.2000-0") >= 0) {
/* 156 */       str = "sun.awt.motif.X11GB18030_0";
/* 157 */       b = 2;
/* 158 */     } else if (paramString.indexOf("hkscs") >= 0) {
/* 159 */       str = "sun.awt.HKSCS";
/* 160 */       b = 2;
/*     */     } 
/* 162 */     return new XMap(str, c1, c, b, bool1, bool2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMap(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
/* 171 */     CharsetEncoder charsetEncoder = null;
/* 172 */     if (paramString != null)
/*     */       
/* 174 */       try { if (paramString.startsWith("sun.awt")) {
/* 175 */           charsetEncoder = ((Charset)Class.forName(paramString).newInstance()).newEncoder();
/*     */         } else {
/* 177 */           charsetEncoder = Charset.forName(paramString).newEncoder();
/*     */         }  }
/* 179 */       catch (Exception exception) { exception.printStackTrace(); }
/*     */        
/* 181 */     if (charsetEncoder == null) {
/* 182 */       this.convertedGlyphs = new char[256];
/* 183 */       for (byte b1 = 0; b1 < 'Ā'; b1++) {
/* 184 */         this.convertedGlyphs[b1] = (char)b1;
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 192 */     int i = paramInt2 - paramInt1 + 1;
/* 193 */     byte[] arrayOfByte1 = new byte[i * paramInt3];
/* 194 */     char[] arrayOfChar = new char[i]; int j;
/* 195 */     for (j = 0; j < i; j++) {
/* 196 */       arrayOfChar[j] = (char)(paramInt1 + j);
/*     */     }
/* 198 */     j = 0;
/*     */     
/* 200 */     if (paramInt3 > 1 && paramInt1 < 256) {
/* 201 */       j = 256 - paramInt1;
/*     */     }
/* 203 */     byte[] arrayOfByte2 = new byte[paramInt3];
/*     */     try {
/* 205 */       int k = 0;
/* 206 */       int m = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       if (j < 55296 && j + i > 57343) {
/* 213 */         k = 55296 - j;
/* 214 */         m = k * paramInt3;
/* 215 */         charsetEncoder.onMalformedInput(CodingErrorAction.REPLACE)
/* 216 */           .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 217 */           .replaceWith(arrayOfByte2)
/* 218 */           .encode(CharBuffer.wrap(arrayOfChar, j, k), 
/* 219 */             ByteBuffer.wrap(arrayOfByte1, j * paramInt3, m), true);
/*     */         
/* 221 */         j = 57344;
/*     */       } 
/* 223 */       k = i - j;
/* 224 */       m = k * paramInt3;
/* 225 */       charsetEncoder.onMalformedInput(CodingErrorAction.REPLACE)
/* 226 */         .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 227 */         .replaceWith(arrayOfByte2)
/* 228 */         .encode(CharBuffer.wrap(arrayOfChar, j, k), 
/* 229 */           ByteBuffer.wrap(arrayOfByte1, j * paramInt3, m), true);
/*     */     } catch (Exception exception) {
/* 231 */       exception.printStackTrace();
/*     */     } 
/* 233 */     this.convertedGlyphs = new char[65536];
/* 234 */     for (byte b = 0; b < i; b++) {
/* 235 */       if (paramInt3 == 1) {
/* 236 */         this.convertedGlyphs[b + paramInt1] = (char)(arrayOfByte1[b] & 0xFF);
/*     */       } else {
/* 238 */         this.convertedGlyphs[b + paramInt1] = (char)(((arrayOfByte1[b * 2] & 0xFF) << 8) + (arrayOfByte1[b * 2 + 1] & 0xFF));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     i = paramBoolean2 ? 128 : 256;
/* 245 */     if (paramBoolean1 && this.convertedGlyphs.length >= 256)
/* 246 */       for (byte b1 = 0; b1 < i; b1++) {
/* 247 */         if (this.convertedGlyphs[b1] == '\000')
/* 248 */           this.convertedGlyphs[b1] = (char)b1; 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/XMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */