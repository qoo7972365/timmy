/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CESU_8
/*     */   extends Unicode
/*     */ {
/*     */   public CESU_8() {
/*  55 */     super("CESU-8", StandardCharsets.aliases_CESU_8);
/*     */   }
/*     */   
/*     */   public String historicalName() {
/*  59 */     return "CESU8";
/*     */   }
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  63 */     return new Decoder(this);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  67 */     return new Encoder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void updatePositions(Buffer paramBuffer1, int paramInt1, Buffer paramBuffer2, int paramInt2) {
/*  72 */     paramBuffer1.position(paramInt1 - paramBuffer1.arrayOffset());
/*  73 */     paramBuffer2.position(paramInt2 - paramBuffer2.arrayOffset());
/*     */   }
/*     */   
/*     */   private static class Decoder
/*     */     extends CharsetDecoder implements ArrayDecoder {
/*     */     private Decoder(Charset param1Charset) {
/*  79 */       super(param1Charset, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */     private static boolean isNotContinuation(int param1Int) {
/*  83 */       return ((param1Int & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed3(int param1Int1, int param1Int2, int param1Int3) {
/*  89 */       return ((param1Int1 == -32 && (param1Int2 & 0xE0) == 128) || (param1Int2 & 0xC0) != 128 || (param1Int3 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed3_2(int param1Int1, int param1Int2) {
/*  95 */       return ((param1Int1 == -32 && (param1Int2 & 0xE0) == 128) || (param1Int2 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4(int param1Int1, int param1Int2, int param1Int3) {
/* 106 */       return ((param1Int1 & 0xC0) != 128 || (param1Int2 & 0xC0) != 128 || (param1Int3 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4_2(int param1Int1, int param1Int2) {
/* 112 */       return ((param1Int1 == 240 && param1Int2 == 144) || (param1Int2 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4_3(int param1Int) {
/* 117 */       return ((param1Int & 0xC0) != 128); } private static CoderResult malformedN(ByteBuffer param1ByteBuffer, int param1Int) { byte b1;
/*     */       int i;
/*     */       byte b2;
/*     */       int j;
/* 121 */       switch (param1Int) {
/*     */         case 1:
/*     */         case 2:
/* 124 */           return CoderResult.malformedForLength(1);
/*     */         case 3:
/* 126 */           b1 = param1ByteBuffer.get();
/* 127 */           b2 = param1ByteBuffer.get();
/* 128 */           return CoderResult.malformedForLength(((b1 == -32 && (b2 & 0xE0) == 128) || 
/*     */               
/* 130 */               isNotContinuation(b2)) ? 1 : 2);
/*     */         case 4:
/* 132 */           i = param1ByteBuffer.get() & 0xFF;
/* 133 */           j = param1ByteBuffer.get() & 0xFF;
/* 134 */           if (i > 244 || (i == 240 && (j < 144 || j > 191)) || (i == 244 && (j & 0xF0) != 128) || 
/*     */ 
/*     */             
/* 137 */             isNotContinuation(j))
/* 138 */             return CoderResult.malformedForLength(1); 
/* 139 */           if (isNotContinuation(param1ByteBuffer.get()))
/* 140 */             return CoderResult.malformedForLength(2); 
/* 141 */           return CoderResult.malformedForLength(3);
/*     */       } 
/*     */       assert false;
/* 144 */       return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformed(ByteBuffer param1ByteBuffer, int param1Int1, CharBuffer param1CharBuffer, int param1Int2, int param1Int3) {
/* 152 */       param1ByteBuffer.position(param1Int1 - param1ByteBuffer.arrayOffset());
/* 153 */       CoderResult coderResult = malformedN(param1ByteBuffer, param1Int3);
/* 154 */       CESU_8.updatePositions(param1ByteBuffer, param1Int1, param1CharBuffer, param1Int2);
/* 155 */       return coderResult;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformed(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2) {
/* 162 */       param1ByteBuffer.position(param1Int1);
/* 163 */       CoderResult coderResult = malformedN(param1ByteBuffer, param1Int2);
/* 164 */       param1ByteBuffer.position(param1Int1);
/* 165 */       return coderResult;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformedForLength(ByteBuffer param1ByteBuffer, int param1Int1, CharBuffer param1CharBuffer, int param1Int2, int param1Int3) {
/* 174 */       CESU_8.updatePositions(param1ByteBuffer, param1Int1, param1CharBuffer, param1Int2);
/* 175 */       return CoderResult.malformedForLength(param1Int3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformedForLength(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2) {
/* 182 */       param1ByteBuffer.position(param1Int1);
/* 183 */       return CoderResult.malformedForLength(param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult xflow(Buffer param1Buffer1, int param1Int1, int param1Int2, Buffer param1Buffer2, int param1Int3, int param1Int4) {
/* 189 */       CESU_8.updatePositions(param1Buffer1, param1Int1, param1Buffer2, param1Int3);
/* 190 */       return (param1Int4 == 0 || param1Int2 - param1Int1 < param1Int4) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
/*     */     }
/*     */ 
/*     */     
/*     */     private static CoderResult xflow(Buffer param1Buffer, int param1Int1, int param1Int2) {
/* 195 */       param1Buffer.position(param1Int1);
/* 196 */       return (param1Int2 == 0 || param1Buffer.remaining() < param1Int2) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeArrayLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 204 */       byte[] arrayOfByte = param1ByteBuffer.array();
/* 205 */       int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/* 206 */       int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*     */       
/* 208 */       char[] arrayOfChar = param1CharBuffer.array();
/* 209 */       int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/* 210 */       int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/* 211 */       int n = k + Math.min(j - i, m - k);
/*     */ 
/*     */       
/* 214 */       while (k < n && arrayOfByte[i] >= 0)
/* 215 */         arrayOfChar[k++] = (char)arrayOfByte[i++]; 
/* 216 */       while (i < j) {
/* 217 */         byte b = arrayOfByte[i];
/* 218 */         if (b >= 0) {
/*     */           
/* 220 */           if (k >= m)
/* 221 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 1); 
/* 222 */           arrayOfChar[k++] = (char)b;
/* 223 */           i++; continue;
/* 224 */         }  if (b >> 5 == -2 && (b & 0x1E) != 0) {
/*     */           
/* 226 */           if (j - i < 2 || k >= m)
/* 227 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 2); 
/* 228 */           byte b1 = arrayOfByte[i + 1];
/* 229 */           if (isNotContinuation(b1))
/* 230 */             return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 1); 
/* 231 */           arrayOfChar[k++] = (char)(b << 6 ^ b1 ^ 0xF80);
/*     */ 
/*     */ 
/*     */           
/* 235 */           i += 2; continue;
/* 236 */         }  if (b >> 4 == -2) {
/*     */           
/* 238 */           int i1 = j - i;
/* 239 */           if (i1 < 3 || k >= m) {
/* 240 */             if (i1 > 1 && isMalformed3_2(b, arrayOfByte[i + 1]))
/* 241 */               return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 1); 
/* 242 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 3);
/*     */           } 
/* 244 */           byte b1 = arrayOfByte[i + 1];
/* 245 */           byte b2 = arrayOfByte[i + 2];
/* 246 */           if (isMalformed3(b, b1, b2))
/* 247 */             return malformed(param1ByteBuffer, i, param1CharBuffer, k, 3); 
/* 248 */           arrayOfChar[k++] = (char)(b << 12 ^ b1 << 6 ^ b2 ^ 0xFFFE1F80);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 255 */           i += 3; continue;
/*     */         } 
/* 257 */         return malformed(param1ByteBuffer, i, param1CharBuffer, k, 1);
/*     */       } 
/*     */       
/* 260 */       return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeBufferLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 266 */       int i = param1ByteBuffer.position();
/* 267 */       int j = param1ByteBuffer.limit();
/* 268 */       while (i < j) {
/* 269 */         byte b = param1ByteBuffer.get();
/* 270 */         if (b >= 0) {
/*     */           
/* 272 */           if (param1CharBuffer.remaining() < 1)
/* 273 */             return xflow(param1ByteBuffer, i, 1); 
/* 274 */           param1CharBuffer.put((char)b);
/* 275 */           i++; continue;
/* 276 */         }  if (b >> 5 == -2 && (b & 0x1E) != 0) {
/*     */           
/* 278 */           if (j - i < 2 || param1CharBuffer.remaining() < 1)
/* 279 */             return xflow(param1ByteBuffer, i, 2); 
/* 280 */           byte b1 = param1ByteBuffer.get();
/* 281 */           if (isNotContinuation(b1))
/* 282 */             return malformedForLength(param1ByteBuffer, i, 1); 
/* 283 */           param1CharBuffer.put((char)(b << 6 ^ b1 ^ 0xF80));
/*     */ 
/*     */ 
/*     */           
/* 287 */           i += 2; continue;
/* 288 */         }  if (b >> 4 == -2) {
/*     */           
/* 290 */           int k = j - i;
/* 291 */           if (k < 3 || param1CharBuffer.remaining() < 1) {
/* 292 */             if (k > 1 && isMalformed3_2(b, param1ByteBuffer.get()))
/* 293 */               return malformedForLength(param1ByteBuffer, i, 1); 
/* 294 */             return xflow(param1ByteBuffer, i, 3);
/*     */           } 
/* 296 */           byte b1 = param1ByteBuffer.get();
/* 297 */           byte b2 = param1ByteBuffer.get();
/* 298 */           if (isMalformed3(b, b1, b2))
/* 299 */             return malformed(param1ByteBuffer, i, 3); 
/* 300 */           param1CharBuffer.put((char)(b << 12 ^ b1 << 6 ^ b2 ^ 0xFFFE1F80));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 307 */           i += 3; continue;
/*     */         } 
/* 309 */         return malformed(param1ByteBuffer, i, 1);
/*     */       } 
/*     */       
/* 312 */       return xflow(param1ByteBuffer, i, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 318 */       if (param1ByteBuffer.hasArray() && param1CharBuffer.hasArray()) {
/* 319 */         return decodeArrayLoop(param1ByteBuffer, param1CharBuffer);
/*     */       }
/* 321 */       return decodeBufferLoop(param1ByteBuffer, param1CharBuffer);
/*     */     }
/*     */ 
/*     */     
/*     */     private static ByteBuffer getByteBuffer(ByteBuffer param1ByteBuffer, byte[] param1ArrayOfbyte, int param1Int) {
/* 326 */       if (param1ByteBuffer == null)
/* 327 */         param1ByteBuffer = ByteBuffer.wrap(param1ArrayOfbyte); 
/* 328 */       param1ByteBuffer.position(param1Int);
/* 329 */       return param1ByteBuffer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 335 */       int i = param1Int1 + param1Int2;
/* 336 */       byte b = 0;
/* 337 */       int j = Math.min(param1Int2, param1ArrayOfchar.length);
/* 338 */       ByteBuffer byteBuffer = null;
/*     */ 
/*     */       
/* 341 */       while (b < j && param1ArrayOfbyte[param1Int1] >= 0) {
/* 342 */         param1ArrayOfchar[b++] = (char)param1ArrayOfbyte[param1Int1++];
/*     */       }
/* 344 */       while (param1Int1 < i) {
/* 345 */         byte b1 = param1ArrayOfbyte[param1Int1++];
/* 346 */         if (b1 >= 0) {
/*     */           
/* 348 */           param1ArrayOfchar[b++] = (char)b1; continue;
/* 349 */         }  if (b1 >> 5 == -2 && (b1 & 0x1E) != 0) {
/*     */           
/* 351 */           if (param1Int1 < i) {
/* 352 */             byte b2 = param1ArrayOfbyte[param1Int1++];
/* 353 */             if (isNotContinuation(b2)) {
/* 354 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 355 */                 return -1; 
/* 356 */               param1ArrayOfchar[b++] = replacement().charAt(0);
/* 357 */               param1Int1--; continue;
/*     */             } 
/* 359 */             param1ArrayOfchar[b++] = (char)(b1 << 6 ^ b2 ^ 0xF80);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 365 */           if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 366 */             return -1; 
/* 367 */           param1ArrayOfchar[b++] = replacement().charAt(0);
/* 368 */           return b;
/* 369 */         }  if (b1 >> 4 == -2) {
/*     */           
/* 371 */           if (param1Int1 + 1 < i) {
/* 372 */             byte b2 = param1ArrayOfbyte[param1Int1++];
/* 373 */             byte b3 = param1ArrayOfbyte[param1Int1++];
/* 374 */             if (isMalformed3(b1, b2, b3)) {
/* 375 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 376 */                 return -1; 
/* 377 */               param1ArrayOfchar[b++] = replacement().charAt(0);
/* 378 */               param1Int1 -= 3;
/* 379 */               byteBuffer = getByteBuffer(byteBuffer, param1ArrayOfbyte, param1Int1);
/* 380 */               param1Int1 += malformedN(byteBuffer, 3).length(); continue;
/*     */             } 
/* 382 */             param1ArrayOfchar[b++] = (char)(b1 << 12 ^ b2 << 6 ^ b3 ^ 0xFFFE1F80);
/*     */ 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 391 */           if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 392 */             return -1; 
/* 393 */           if (param1Int1 < i && isMalformed3_2(b1, param1ArrayOfbyte[param1Int1])) {
/* 394 */             param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */             
/*     */             continue;
/*     */           } 
/* 398 */           param1ArrayOfchar[b++] = replacement().charAt(0);
/* 399 */           return b;
/*     */         } 
/* 401 */         if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 402 */           return -1; 
/* 403 */         param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */       } 
/*     */       
/* 406 */       return b;
/*     */     } }
/*     */   
/*     */   private static class Encoder extends CharsetEncoder implements ArrayEncoder {
/*     */     private Surrogate.Parser sgp;
/*     */     private char[] c2;
/*     */     
/*     */     private Encoder(Charset param1Charset) {
/* 414 */       super(param1Charset, 1.1F, 3.0F);
/*     */     }
/*     */     
/*     */     public boolean canEncode(char param1Char) {
/* 418 */       return !Character.isSurrogate(param1Char);
/*     */     }
/*     */     
/*     */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) {
/* 422 */       return ((param1ArrayOfbyte.length == 1 && param1ArrayOfbyte[0] >= 0) || super
/* 423 */         .isLegalReplacement(param1ArrayOfbyte));
/*     */     }
/*     */ 
/*     */     
/*     */     private static CoderResult overflow(CharBuffer param1CharBuffer, int param1Int1, ByteBuffer param1ByteBuffer, int param1Int2) {
/* 428 */       CESU_8.updatePositions(param1CharBuffer, param1Int1, param1ByteBuffer, param1Int2);
/* 429 */       return CoderResult.OVERFLOW;
/*     */     }
/*     */     
/*     */     private static CoderResult overflow(CharBuffer param1CharBuffer, int param1Int) {
/* 433 */       param1CharBuffer.position(param1Int);
/* 434 */       return CoderResult.OVERFLOW;
/*     */     }
/*     */     
/*     */     private static void to3Bytes(byte[] param1ArrayOfbyte, int param1Int, char param1Char) {
/* 438 */       param1ArrayOfbyte[param1Int] = (byte)(0xE0 | param1Char >> 12);
/* 439 */       param1ArrayOfbyte[param1Int + 1] = (byte)(0x80 | param1Char >> 6 & 0x3F);
/* 440 */       param1ArrayOfbyte[param1Int + 2] = (byte)(0x80 | param1Char & 0x3F);
/*     */     }
/*     */     
/*     */     private static void to3Bytes(ByteBuffer param1ByteBuffer, char param1Char) {
/* 444 */       param1ByteBuffer.put((byte)(0xE0 | param1Char >> 12));
/* 445 */       param1ByteBuffer.put((byte)(0x80 | param1Char >> 6 & 0x3F));
/* 446 */       param1ByteBuffer.put((byte)(0x80 | param1Char & 0x3F));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult encodeArrayLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/* 454 */       char[] arrayOfChar = param1CharBuffer.array();
/* 455 */       int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/* 456 */       int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*     */       
/* 458 */       byte[] arrayOfByte = param1ByteBuffer.array();
/* 459 */       int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/* 460 */       int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/* 461 */       int n = k + Math.min(j - i, m - k);
/*     */ 
/*     */       
/* 464 */       while (k < n && arrayOfChar[i] < '')
/* 465 */         arrayOfByte[k++] = (byte)arrayOfChar[i++]; 
/* 466 */       while (i < j) {
/* 467 */         char c = arrayOfChar[i];
/* 468 */         if (c < '') {
/*     */           
/* 470 */           if (k >= m)
/* 471 */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/* 472 */           arrayOfByte[k++] = (byte)c;
/* 473 */         } else if (c < 'ࠀ') {
/*     */           
/* 475 */           if (m - k < 2)
/* 476 */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/* 477 */           arrayOfByte[k++] = (byte)(0xC0 | c >> 6);
/* 478 */           arrayOfByte[k++] = (byte)(0x80 | c & 0x3F);
/* 479 */         } else if (Character.isSurrogate(c)) {
/*     */           
/* 481 */           if (this.sgp == null)
/* 482 */             this.sgp = new Surrogate.Parser(); 
/* 483 */           int i1 = this.sgp.parse(c, arrayOfChar, i, j);
/* 484 */           if (i1 < 0) {
/* 485 */             CESU_8.updatePositions(param1CharBuffer, i, param1ByteBuffer, k);
/* 486 */             return this.sgp.error();
/*     */           } 
/* 488 */           if (m - k < 6)
/* 489 */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/* 490 */           to3Bytes(arrayOfByte, k, Character.highSurrogate(i1));
/* 491 */           k += 3;
/* 492 */           to3Bytes(arrayOfByte, k, Character.lowSurrogate(i1));
/* 493 */           k += 3;
/* 494 */           i++;
/*     */         } else {
/*     */           
/* 497 */           if (m - k < 3)
/* 498 */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/* 499 */           to3Bytes(arrayOfByte, k, c);
/* 500 */           k += 3;
/*     */         } 
/* 502 */         i++;
/*     */       } 
/* 504 */       CESU_8.updatePositions(param1CharBuffer, i, param1ByteBuffer, k);
/* 505 */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult encodeBufferLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/* 511 */       int i = param1CharBuffer.position();
/* 512 */       while (param1CharBuffer.hasRemaining()) {
/* 513 */         char c = param1CharBuffer.get();
/* 514 */         if (c < '') {
/*     */           
/* 516 */           if (!param1ByteBuffer.hasRemaining())
/* 517 */             return overflow(param1CharBuffer, i); 
/* 518 */           param1ByteBuffer.put((byte)c);
/* 519 */         } else if (c < 'ࠀ') {
/*     */           
/* 521 */           if (param1ByteBuffer.remaining() < 2)
/* 522 */             return overflow(param1CharBuffer, i); 
/* 523 */           param1ByteBuffer.put((byte)(0xC0 | c >> 6));
/* 524 */           param1ByteBuffer.put((byte)(0x80 | c & 0x3F));
/* 525 */         } else if (Character.isSurrogate(c)) {
/*     */           
/* 527 */           if (this.sgp == null)
/* 528 */             this.sgp = new Surrogate.Parser(); 
/* 529 */           int j = this.sgp.parse(c, param1CharBuffer);
/* 530 */           if (j < 0) {
/* 531 */             param1CharBuffer.position(i);
/* 532 */             return this.sgp.error();
/*     */           } 
/* 534 */           if (param1ByteBuffer.remaining() < 6)
/* 535 */             return overflow(param1CharBuffer, i); 
/* 536 */           to3Bytes(param1ByteBuffer, Character.highSurrogate(j));
/* 537 */           to3Bytes(param1ByteBuffer, Character.lowSurrogate(j));
/* 538 */           i++;
/*     */         } else {
/*     */           
/* 541 */           if (param1ByteBuffer.remaining() < 3)
/* 542 */             return overflow(param1CharBuffer, i); 
/* 543 */           to3Bytes(param1ByteBuffer, c);
/*     */         } 
/* 545 */         i++;
/*     */       } 
/* 547 */       param1CharBuffer.position(i);
/* 548 */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected final CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/* 554 */       if (param1CharBuffer.hasArray() && param1ByteBuffer.hasArray()) {
/* 555 */         return encodeArrayLoop(param1CharBuffer, param1ByteBuffer);
/*     */       }
/* 557 */       return encodeBufferLoop(param1CharBuffer, param1ByteBuffer);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) {
/* 563 */       int i = param1Int1 + param1Int2;
/* 564 */       byte b = 0;
/* 565 */       int j = b + Math.min(param1Int2, param1ArrayOfbyte.length);
/*     */ 
/*     */       
/* 568 */       while (b < j && param1ArrayOfchar[param1Int1] < '') {
/* 569 */         param1ArrayOfbyte[b++] = (byte)param1ArrayOfchar[param1Int1++];
/*     */       }
/* 571 */       while (param1Int1 < i) {
/* 572 */         char c = param1ArrayOfchar[param1Int1++];
/* 573 */         if (c < '') {
/*     */           
/* 575 */           param1ArrayOfbyte[b++] = (byte)c; continue;
/* 576 */         }  if (c < 'ࠀ') {
/*     */           
/* 578 */           param1ArrayOfbyte[b++] = (byte)(0xC0 | c >> 6);
/* 579 */           param1ArrayOfbyte[b++] = (byte)(0x80 | c & 0x3F); continue;
/* 580 */         }  if (Character.isSurrogate(c)) {
/* 581 */           if (this.sgp == null)
/* 582 */             this.sgp = new Surrogate.Parser(); 
/* 583 */           int k = this.sgp.parse(c, param1ArrayOfchar, param1Int1 - 1, i);
/* 584 */           if (k < 0) {
/* 585 */             if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 586 */               return -1; 
/* 587 */             param1ArrayOfbyte[b++] = replacement()[0]; continue;
/*     */           } 
/* 589 */           to3Bytes(param1ArrayOfbyte, b, Character.highSurrogate(k));
/* 590 */           b += 3;
/* 591 */           to3Bytes(param1ArrayOfbyte, b, Character.lowSurrogate(k));
/* 592 */           b += 3;
/* 593 */           param1Int1++;
/*     */           
/*     */           continue;
/*     */         } 
/* 597 */         to3Bytes(param1ArrayOfbyte, b, c);
/* 598 */         b += 3;
/*     */       } 
/*     */       
/* 601 */       return b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/CESU_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */