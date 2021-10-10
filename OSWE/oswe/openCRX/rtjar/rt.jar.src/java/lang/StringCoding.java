/*     */ package java.lang;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.util.Arrays;
/*     */ import sun.misc.MessageUtils;
/*     */ import sun.nio.cs.ArrayDecoder;
/*     */ import sun.nio.cs.ArrayEncoder;
/*     */ import sun.nio.cs.HistoricallyNamedCharset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class StringCoding
/*     */ {
/*  55 */   private static final ThreadLocal<SoftReference<StringDecoder>> decoder = new ThreadLocal<>();
/*     */   
/*  57 */   private static final ThreadLocal<SoftReference<StringEncoder>> encoder = new ThreadLocal<>();
/*     */   
/*     */   private static boolean warnUnsupportedCharset = true;
/*     */ 
/*     */   
/*     */   private static <T> T deref(ThreadLocal<SoftReference<T>> paramThreadLocal) {
/*  63 */     SoftReference<T> softReference = paramThreadLocal.get();
/*  64 */     if (softReference == null)
/*  65 */       return null; 
/*  66 */     return softReference.get();
/*     */   }
/*     */   
/*     */   private static <T> void set(ThreadLocal<SoftReference<T>> paramThreadLocal, T paramT) {
/*  70 */     paramThreadLocal.set(new SoftReference<>(paramT));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] safeTrim(byte[] paramArrayOfbyte, int paramInt, Charset paramCharset, boolean paramBoolean) {
/*  76 */     if (paramInt == paramArrayOfbyte.length && (paramBoolean || System.getSecurityManager() == null)) {
/*  77 */       return paramArrayOfbyte;
/*     */     }
/*  79 */     return Arrays.copyOf(paramArrayOfbyte, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static char[] safeTrim(char[] paramArrayOfchar, int paramInt, Charset paramCharset, boolean paramBoolean) {
/*  86 */     if (paramInt == paramArrayOfchar.length && (paramBoolean || System.getSecurityManager() == null)) {
/*  87 */       return paramArrayOfchar;
/*     */     }
/*  89 */     return Arrays.copyOf(paramArrayOfchar, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int scale(int paramInt, float paramFloat) {
/*  95 */     return (int)(paramInt * paramFloat);
/*     */   }
/*     */   
/*     */   private static Charset lookupCharset(String paramString) {
/*  99 */     if (Charset.isSupported(paramString)) {
/*     */       try {
/* 101 */         return Charset.forName(paramString);
/* 102 */       } catch (UnsupportedCharsetException unsupportedCharsetException) {
/* 103 */         throw new Error(unsupportedCharsetException);
/*     */       } 
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private static void warnUnsupportedCharset(String paramString) {
/* 110 */     if (warnUnsupportedCharset) {
/*     */ 
/*     */ 
/*     */       
/* 114 */       MessageUtils.err("WARNING: Default charset " + paramString + " not supported, using ISO-8859-1 instead");
/*     */       
/* 116 */       warnUnsupportedCharset = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class StringDecoder
/*     */   {
/*     */     private final String requestedCharsetName;
/*     */     private final Charset cs;
/*     */     private final CharsetDecoder cd;
/*     */     private final boolean isTrusted;
/*     */     
/*     */     private StringDecoder(Charset param1Charset, String param1String) {
/* 129 */       this.requestedCharsetName = param1String;
/* 130 */       this.cs = param1Charset;
/* 131 */       this
/*     */         
/* 133 */         .cd = param1Charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 134 */       this.isTrusted = (param1Charset.getClass().getClassLoader0() == null);
/*     */     }
/*     */     
/*     */     String charsetName() {
/* 138 */       if (this.cs instanceof HistoricallyNamedCharset)
/* 139 */         return ((HistoricallyNamedCharset)this.cs).historicalName(); 
/* 140 */       return this.cs.name();
/*     */     }
/*     */     
/*     */     final String requestedCharsetName() {
/* 144 */       return this.requestedCharsetName;
/*     */     }
/*     */     
/*     */     char[] decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 148 */       int i = StringCoding.scale(param1Int2, this.cd.maxCharsPerByte());
/* 149 */       char[] arrayOfChar = new char[i];
/* 150 */       if (param1Int2 == 0)
/* 151 */         return arrayOfChar; 
/* 152 */       if (this.cd instanceof ArrayDecoder) {
/* 153 */         int j = ((ArrayDecoder)this.cd).decode(param1ArrayOfbyte, param1Int1, param1Int2, arrayOfChar);
/* 154 */         return StringCoding.safeTrim(arrayOfChar, j, this.cs, this.isTrusted);
/*     */       } 
/* 156 */       this.cd.reset();
/* 157 */       ByteBuffer byteBuffer = ByteBuffer.wrap(param1ArrayOfbyte, param1Int1, param1Int2);
/* 158 */       CharBuffer charBuffer = CharBuffer.wrap(arrayOfChar);
/*     */       try {
/* 160 */         CoderResult coderResult = this.cd.decode(byteBuffer, charBuffer, true);
/* 161 */         if (!coderResult.isUnderflow())
/* 162 */           coderResult.throwException(); 
/* 163 */         coderResult = this.cd.flush(charBuffer);
/* 164 */         if (!coderResult.isUnderflow())
/* 165 */           coderResult.throwException(); 
/* 166 */       } catch (CharacterCodingException characterCodingException) {
/*     */ 
/*     */         
/* 169 */         throw new Error(characterCodingException);
/*     */       } 
/* 171 */       return StringCoding.safeTrim(arrayOfChar, charBuffer.position(), this.cs, this.isTrusted);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static char[] decode(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws UnsupportedEncodingException {
/* 179 */     StringDecoder stringDecoder = deref(decoder);
/* 180 */     String str = (paramString == null) ? "ISO-8859-1" : paramString;
/* 181 */     if (stringDecoder == null || (!str.equals(stringDecoder.requestedCharsetName()) && 
/* 182 */       !str.equals(stringDecoder.charsetName()))) {
/* 183 */       stringDecoder = null;
/*     */       try {
/* 185 */         Charset charset = lookupCharset(str);
/* 186 */         if (charset != null)
/* 187 */           stringDecoder = new StringDecoder(charset, str); 
/* 188 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {}
/* 189 */       if (stringDecoder == null)
/* 190 */         throw new UnsupportedEncodingException(str); 
/* 191 */       set(decoder, stringDecoder);
/*     */     } 
/* 193 */     return stringDecoder.decode(paramArrayOfbyte, paramInt1, paramInt2);
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
/*     */   static char[] decode(Charset paramCharset, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 213 */     CharsetDecoder charsetDecoder = paramCharset.newDecoder();
/* 214 */     int i = scale(paramInt2, charsetDecoder.maxCharsPerByte());
/* 215 */     char[] arrayOfChar = new char[i];
/* 216 */     if (paramInt2 == 0)
/* 217 */       return arrayOfChar; 
/* 218 */     boolean bool = false;
/* 219 */     if (System.getSecurityManager() != null) {
/* 220 */       if (!(bool = (paramCharset.getClass().getClassLoader0() == null) ? true : false)) {
/* 221 */         paramArrayOfbyte = Arrays.copyOfRange(paramArrayOfbyte, paramInt1, paramInt1 + paramInt2);
/* 222 */         paramInt1 = 0;
/*     */       } 
/*     */     }
/* 225 */     charsetDecoder.onMalformedInput(CodingErrorAction.REPLACE)
/* 226 */       .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 227 */       .reset();
/* 228 */     if (charsetDecoder instanceof ArrayDecoder) {
/* 229 */       int j = ((ArrayDecoder)charsetDecoder).decode(paramArrayOfbyte, paramInt1, paramInt2, arrayOfChar);
/* 230 */       return safeTrim(arrayOfChar, j, paramCharset, bool);
/*     */     } 
/* 232 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte, paramInt1, paramInt2);
/* 233 */     CharBuffer charBuffer = CharBuffer.wrap(arrayOfChar);
/*     */     try {
/* 235 */       CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
/* 236 */       if (!coderResult.isUnderflow())
/* 237 */         coderResult.throwException(); 
/* 238 */       coderResult = charsetDecoder.flush(charBuffer);
/* 239 */       if (!coderResult.isUnderflow())
/* 240 */         coderResult.throwException(); 
/* 241 */     } catch (CharacterCodingException characterCodingException) {
/*     */ 
/*     */       
/* 244 */       throw new Error(characterCodingException);
/*     */     } 
/* 246 */     return safeTrim(arrayOfChar, charBuffer.position(), paramCharset, bool);
/*     */   }
/*     */ 
/*     */   
/*     */   static char[] decode(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 251 */     String str = Charset.defaultCharset().name();
/*     */     
/*     */     try {
/* 254 */       return decode(str, paramArrayOfbyte, paramInt1, paramInt2);
/* 255 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 256 */       warnUnsupportedCharset(str);
/*     */       
/*     */       try {
/* 259 */         return decode("ISO-8859-1", paramArrayOfbyte, paramInt1, paramInt2);
/* 260 */       } catch (UnsupportedEncodingException unsupportedEncodingException1) {
/*     */ 
/*     */         
/* 263 */         MessageUtils.err("ISO-8859-1 charset not available: " + unsupportedEncodingException1
/* 264 */             .toString());
/*     */ 
/*     */         
/* 267 */         System.exit(1);
/* 268 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class StringEncoder {
/*     */     private Charset cs;
/*     */     private CharsetEncoder ce;
/*     */     private final String requestedCharsetName;
/*     */     private final boolean isTrusted;
/*     */     
/*     */     private StringEncoder(Charset param1Charset, String param1String) {
/* 280 */       this.requestedCharsetName = param1String;
/* 281 */       this.cs = param1Charset;
/* 282 */       this
/*     */         
/* 284 */         .ce = param1Charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 285 */       this.isTrusted = (param1Charset.getClass().getClassLoader0() == null);
/*     */     }
/*     */     
/*     */     String charsetName() {
/* 289 */       if (this.cs instanceof HistoricallyNamedCharset)
/* 290 */         return ((HistoricallyNamedCharset)this.cs).historicalName(); 
/* 291 */       return this.cs.name();
/*     */     }
/*     */     
/*     */     final String requestedCharsetName() {
/* 295 */       return this.requestedCharsetName;
/*     */     }
/*     */     
/*     */     byte[] encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 299 */       int i = StringCoding.scale(param1Int2, this.ce.maxBytesPerChar());
/* 300 */       byte[] arrayOfByte = new byte[i];
/* 301 */       if (param1Int2 == 0)
/* 302 */         return arrayOfByte; 
/* 303 */       if (this.ce instanceof ArrayEncoder) {
/* 304 */         int j = ((ArrayEncoder)this.ce).encode(param1ArrayOfchar, param1Int1, param1Int2, arrayOfByte);
/* 305 */         return StringCoding.safeTrim(arrayOfByte, j, this.cs, this.isTrusted);
/*     */       } 
/* 307 */       this.ce.reset();
/* 308 */       ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 309 */       CharBuffer charBuffer = CharBuffer.wrap(param1ArrayOfchar, param1Int1, param1Int2);
/*     */       try {
/* 311 */         CoderResult coderResult = this.ce.encode(charBuffer, byteBuffer, true);
/* 312 */         if (!coderResult.isUnderflow())
/* 313 */           coderResult.throwException(); 
/* 314 */         coderResult = this.ce.flush(byteBuffer);
/* 315 */         if (!coderResult.isUnderflow())
/* 316 */           coderResult.throwException(); 
/* 317 */       } catch (CharacterCodingException characterCodingException) {
/*     */ 
/*     */         
/* 320 */         throw new Error(characterCodingException);
/*     */       } 
/* 322 */       return StringCoding.safeTrim(arrayOfByte, byteBuffer.position(), this.cs, this.isTrusted);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] encode(String paramString, char[] paramArrayOfchar, int paramInt1, int paramInt2) throws UnsupportedEncodingException {
/* 330 */     StringEncoder stringEncoder = deref(encoder);
/* 331 */     String str = (paramString == null) ? "ISO-8859-1" : paramString;
/* 332 */     if (stringEncoder == null || (!str.equals(stringEncoder.requestedCharsetName()) && 
/* 333 */       !str.equals(stringEncoder.charsetName()))) {
/* 334 */       stringEncoder = null;
/*     */       try {
/* 336 */         Charset charset = lookupCharset(str);
/* 337 */         if (charset != null)
/* 338 */           stringEncoder = new StringEncoder(charset, str); 
/* 339 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {}
/* 340 */       if (stringEncoder == null)
/* 341 */         throw new UnsupportedEncodingException(str); 
/* 342 */       set(encoder, stringEncoder);
/*     */     } 
/* 344 */     return stringEncoder.encode(paramArrayOfchar, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static byte[] encode(Charset paramCharset, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 348 */     CharsetEncoder charsetEncoder = paramCharset.newEncoder();
/* 349 */     int i = scale(paramInt2, charsetEncoder.maxBytesPerChar());
/* 350 */     byte[] arrayOfByte = new byte[i];
/* 351 */     if (paramInt2 == 0)
/* 352 */       return arrayOfByte; 
/* 353 */     boolean bool = false;
/* 354 */     if (System.getSecurityManager() != null) {
/* 355 */       if (!(bool = (paramCharset.getClass().getClassLoader0() == null) ? true : false)) {
/* 356 */         paramArrayOfchar = Arrays.copyOfRange(paramArrayOfchar, paramInt1, paramInt1 + paramInt2);
/* 357 */         paramInt1 = 0;
/*     */       } 
/*     */     }
/* 360 */     charsetEncoder.onMalformedInput(CodingErrorAction.REPLACE)
/* 361 */       .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 362 */       .reset();
/* 363 */     if (charsetEncoder instanceof ArrayEncoder) {
/* 364 */       int j = ((ArrayEncoder)charsetEncoder).encode(paramArrayOfchar, paramInt1, paramInt2, arrayOfByte);
/* 365 */       return safeTrim(arrayOfByte, j, paramCharset, bool);
/*     */     } 
/* 367 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 368 */     CharBuffer charBuffer = CharBuffer.wrap(paramArrayOfchar, paramInt1, paramInt2);
/*     */     try {
/* 370 */       CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, true);
/* 371 */       if (!coderResult.isUnderflow())
/* 372 */         coderResult.throwException(); 
/* 373 */       coderResult = charsetEncoder.flush(byteBuffer);
/* 374 */       if (!coderResult.isUnderflow())
/* 375 */         coderResult.throwException(); 
/* 376 */     } catch (CharacterCodingException characterCodingException) {
/* 377 */       throw new Error(characterCodingException);
/*     */     } 
/* 379 */     return safeTrim(arrayOfByte, byteBuffer.position(), paramCharset, bool);
/*     */   }
/*     */ 
/*     */   
/*     */   static byte[] encode(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 384 */     String str = Charset.defaultCharset().name();
/*     */     
/*     */     try {
/* 387 */       return encode(str, paramArrayOfchar, paramInt1, paramInt2);
/* 388 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 389 */       warnUnsupportedCharset(str);
/*     */       
/*     */       try {
/* 392 */         return encode("ISO-8859-1", paramArrayOfchar, paramInt1, paramInt2);
/* 393 */       } catch (UnsupportedEncodingException unsupportedEncodingException1) {
/*     */ 
/*     */         
/* 396 */         MessageUtils.err("ISO-8859-1 charset not available: " + unsupportedEncodingException1
/* 397 */             .toString());
/*     */ 
/*     */         
/* 400 */         System.exit(1);
/* 401 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/StringCoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */