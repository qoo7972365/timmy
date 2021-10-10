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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UTF_8
/*     */   extends Unicode
/*     */ {
/*     */   public UTF_8() {
/*  60 */     super("UTF-8", StandardCharsets.aliases_UTF_8);
/*     */   }
/*     */   
/*     */   public String historicalName() {
/*  64 */     return "UTF8";
/*     */   }
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  68 */     return new Decoder(this);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  72 */     return new Encoder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void updatePositions(Buffer paramBuffer1, int paramInt1, Buffer paramBuffer2, int paramInt2) {
/*  77 */     paramBuffer1.position(paramInt1 - paramBuffer1.arrayOffset());
/*  78 */     paramBuffer2.position(paramInt2 - paramBuffer2.arrayOffset());
/*     */   }
/*     */   
/*     */   private static class Decoder
/*     */     extends CharsetDecoder implements ArrayDecoder {
/*     */     private Decoder(Charset param1Charset) {
/*  84 */       super(param1Charset, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */     private static boolean isNotContinuation(int param1Int) {
/*  88 */       return ((param1Int & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed3(int param1Int1, int param1Int2, int param1Int3) {
/*  94 */       return ((param1Int1 == -32 && (param1Int2 & 0xE0) == 128) || (param1Int2 & 0xC0) != 128 || (param1Int3 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed3_2(int param1Int1, int param1Int2) {
/* 100 */       return ((param1Int1 == -32 && (param1Int2 & 0xE0) == 128) || (param1Int2 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4(int param1Int1, int param1Int2, int param1Int3) {
/* 110 */       return ((param1Int1 & 0xC0) != 128 || (param1Int2 & 0xC0) != 128 || (param1Int3 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4_2(int param1Int1, int param1Int2) {
/* 117 */       return ((param1Int1 == 240 && (param1Int2 < 144 || param1Int2 > 191)) || (param1Int1 == 244 && (param1Int2 & 0xF0) != 128) || (param1Int2 & 0xC0) != 128);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isMalformed4_3(int param1Int) {
/* 127 */       return ((param1Int & 0xC0) != 128);
/*     */     }
/*     */     
/*     */     private static CoderResult lookupN(ByteBuffer param1ByteBuffer, int param1Int)
/*     */     {
/* 132 */       for (byte b = 1; b < param1Int; b++) {
/* 133 */         if (isNotContinuation(param1ByteBuffer.get()))
/* 134 */           return CoderResult.malformedForLength(b); 
/*     */       } 
/* 136 */       return CoderResult.malformedForLength(param1Int); } private static CoderResult malformedN(ByteBuffer param1ByteBuffer, int param1Int) { byte b1;
/*     */       int i;
/*     */       byte b2;
/*     */       int j;
/* 140 */       switch (param1Int) {
/*     */         case 1:
/*     */         case 2:
/* 143 */           return CoderResult.malformedForLength(1);
/*     */         case 3:
/* 145 */           b1 = param1ByteBuffer.get();
/* 146 */           b2 = param1ByteBuffer.get();
/* 147 */           return CoderResult.malformedForLength(((b1 == -32 && (b2 & 0xE0) == 128) || 
/*     */               
/* 149 */               isNotContinuation(b2)) ? 1 : 2);
/*     */         case 4:
/* 151 */           i = param1ByteBuffer.get() & 0xFF;
/* 152 */           j = param1ByteBuffer.get() & 0xFF;
/* 153 */           if (i > 244 || (i == 240 && (j < 144 || j > 191)) || (i == 244 && (j & 0xF0) != 128) || 
/*     */ 
/*     */             
/* 156 */             isNotContinuation(j))
/* 157 */             return CoderResult.malformedForLength(1); 
/* 158 */           if (isNotContinuation(param1ByteBuffer.get()))
/* 159 */             return CoderResult.malformedForLength(2); 
/* 160 */           return CoderResult.malformedForLength(3);
/*     */       } 
/*     */       assert false;
/* 163 */       return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformed(ByteBuffer param1ByteBuffer, int param1Int1, CharBuffer param1CharBuffer, int param1Int2, int param1Int3) {
/* 171 */       param1ByteBuffer.position(param1Int1 - param1ByteBuffer.arrayOffset());
/* 172 */       CoderResult coderResult = malformedN(param1ByteBuffer, param1Int3);
/* 173 */       UTF_8.updatePositions(param1ByteBuffer, param1Int1, param1CharBuffer, param1Int2);
/* 174 */       return coderResult;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformed(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2) {
/* 181 */       param1ByteBuffer.position(param1Int1);
/* 182 */       CoderResult coderResult = malformedN(param1ByteBuffer, param1Int2);
/* 183 */       param1ByteBuffer.position(param1Int1);
/* 184 */       return coderResult;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformedForLength(ByteBuffer param1ByteBuffer, int param1Int1, CharBuffer param1CharBuffer, int param1Int2, int param1Int3) {
/* 193 */       UTF_8.updatePositions(param1ByteBuffer, param1Int1, param1CharBuffer, param1Int2);
/* 194 */       return CoderResult.malformedForLength(param1Int3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult malformedForLength(ByteBuffer param1ByteBuffer, int param1Int1, int param1Int2) {
/* 201 */       param1ByteBuffer.position(param1Int1);
/* 202 */       return CoderResult.malformedForLength(param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static CoderResult xflow(Buffer param1Buffer1, int param1Int1, int param1Int2, Buffer param1Buffer2, int param1Int3, int param1Int4) {
/* 208 */       UTF_8.updatePositions(param1Buffer1, param1Int1, param1Buffer2, param1Int3);
/* 209 */       return (param1Int4 == 0 || param1Int2 - param1Int1 < param1Int4) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
/*     */     }
/*     */ 
/*     */     
/*     */     private static CoderResult xflow(Buffer param1Buffer, int param1Int1, int param1Int2) {
/* 214 */       param1Buffer.position(param1Int1);
/* 215 */       return (param1Int2 == 0 || param1Buffer.remaining() < param1Int2) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeArrayLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 223 */       byte[] arrayOfByte = param1ByteBuffer.array();
/* 224 */       int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/* 225 */       int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*     */       
/* 227 */       char[] arrayOfChar = param1CharBuffer.array();
/* 228 */       int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/* 229 */       int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/* 230 */       int n = k + Math.min(j - i, m - k);
/*     */ 
/*     */       
/* 233 */       while (k < n && arrayOfByte[i] >= 0)
/* 234 */         arrayOfChar[k++] = (char)arrayOfByte[i++]; 
/* 235 */       while (i < j) {
/* 236 */         byte b = arrayOfByte[i];
/* 237 */         if (b >= 0) {
/*     */           
/* 239 */           if (k >= m)
/* 240 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 1); 
/* 241 */           arrayOfChar[k++] = (char)b;
/* 242 */           i++; continue;
/* 243 */         }  if (b >> 5 == -2 && (b & 0x1E) != 0) {
/*     */ 
/*     */           
/* 246 */           if (j - i < 2 || k >= m)
/* 247 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 2); 
/* 248 */           byte b1 = arrayOfByte[i + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 255 */           if (isNotContinuation(b1))
/* 256 */             return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 1); 
/* 257 */           arrayOfChar[k++] = (char)(b << 6 ^ b1 ^ 0xF80);
/*     */ 
/*     */ 
/*     */           
/* 261 */           i += 2; continue;
/* 262 */         }  if (b >> 4 == -2) {
/*     */           
/* 264 */           int i1 = j - i;
/* 265 */           if (i1 < 3 || k >= m) {
/* 266 */             if (i1 > 1 && isMalformed3_2(b, arrayOfByte[i + 1]))
/* 267 */               return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 1); 
/* 268 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 3);
/*     */           } 
/* 270 */           byte b1 = arrayOfByte[i + 1];
/* 271 */           byte b2 = arrayOfByte[i + 2];
/* 272 */           if (isMalformed3(b, b1, b2))
/* 273 */             return malformed(param1ByteBuffer, i, param1CharBuffer, k, 3); 
/* 274 */           char c = (char)(b << 12 ^ b1 << 6 ^ b2 ^ 0xFFFE1F80);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 281 */           if (Character.isSurrogate(c))
/* 282 */             return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 3); 
/* 283 */           arrayOfChar[k++] = c;
/* 284 */           i += 3; continue;
/* 285 */         }  if (b >> 3 == -2) {
/*     */           
/* 287 */           int i1, i2 = j - i;
/* 288 */           if (i2 < 4 || m - k < 2) {
/* 289 */             i1 = b & 0xFF;
/* 290 */             if (i1 > 244 || (i2 > 1 && 
/* 291 */               isMalformed4_2(i1, arrayOfByte[i + 1] & 0xFF)))
/* 292 */               return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 1); 
/* 293 */             if (i2 > 2 && isMalformed4_3(arrayOfByte[i + 2]))
/* 294 */               return malformedForLength(param1ByteBuffer, i, param1CharBuffer, k, 2); 
/* 295 */             return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 4);
/*     */           } 
/* 297 */           byte b1 = arrayOfByte[i + 1];
/* 298 */           byte b2 = arrayOfByte[i + 2];
/* 299 */           byte b3 = arrayOfByte[i + 3];
/* 300 */           int i3 = i1 << 18 ^ b1 << 12 ^ b2 << 6 ^ b3 ^ 0x381F80;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 308 */           if (isMalformed4(b1, b2, b3) || 
/*     */             
/* 310 */             !Character.isSupplementaryCodePoint(i3)) {
/* 311 */             return malformed(param1ByteBuffer, i, param1CharBuffer, k, 4);
/*     */           }
/* 313 */           arrayOfChar[k++] = Character.highSurrogate(i3);
/* 314 */           arrayOfChar[k++] = Character.lowSurrogate(i3);
/* 315 */           i += 4; continue;
/*     */         } 
/* 317 */         return malformed(param1ByteBuffer, i, param1CharBuffer, k, 1);
/*     */       } 
/* 319 */       return xflow(param1ByteBuffer, i, j, param1CharBuffer, k, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeBufferLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 325 */       int i = param1ByteBuffer.position();
/* 326 */       int j = param1ByteBuffer.limit();
/* 327 */       while (i < j) {
/* 328 */         byte b = param1ByteBuffer.get();
/* 329 */         if (b >= 0) {
/*     */           
/* 331 */           if (param1CharBuffer.remaining() < 1)
/* 332 */             return xflow(param1ByteBuffer, i, 1); 
/* 333 */           param1CharBuffer.put((char)b);
/* 334 */           i++; continue;
/* 335 */         }  if (b >> 5 == -2 && (b & 0x1E) != 0) {
/*     */           
/* 337 */           if (j - i < 2 || param1CharBuffer.remaining() < 1)
/* 338 */             return xflow(param1ByteBuffer, i, 2); 
/* 339 */           byte b1 = param1ByteBuffer.get();
/* 340 */           if (isNotContinuation(b1))
/* 341 */             return malformedForLength(param1ByteBuffer, i, 1); 
/* 342 */           param1CharBuffer.put((char)(b << 6 ^ b1 ^ 0xF80));
/*     */ 
/*     */ 
/*     */           
/* 346 */           i += 2; continue;
/* 347 */         }  if (b >> 4 == -2) {
/*     */           
/* 349 */           int k = j - i;
/* 350 */           if (k < 3 || param1CharBuffer.remaining() < 1) {
/* 351 */             if (k > 1 && isMalformed3_2(b, param1ByteBuffer.get()))
/* 352 */               return malformedForLength(param1ByteBuffer, i, 1); 
/* 353 */             return xflow(param1ByteBuffer, i, 3);
/*     */           } 
/* 355 */           byte b1 = param1ByteBuffer.get();
/* 356 */           byte b2 = param1ByteBuffer.get();
/* 357 */           if (isMalformed3(b, b1, b2))
/* 358 */             return malformed(param1ByteBuffer, i, 3); 
/* 359 */           char c = (char)(b << 12 ^ b1 << 6 ^ b2 ^ 0xFFFE1F80);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 366 */           if (Character.isSurrogate(c))
/* 367 */             return malformedForLength(param1ByteBuffer, i, 3); 
/* 368 */           param1CharBuffer.put(c);
/* 369 */           i += 3; continue;
/* 370 */         }  if (b >> 3 == -2) {
/*     */           
/* 372 */           int k, m = j - i;
/* 373 */           if (m < 4 || param1CharBuffer.remaining() < 2) {
/* 374 */             k = b & 0xFF;
/* 375 */             if (k > 244 || (m > 1 && 
/* 376 */               isMalformed4_2(k, param1ByteBuffer.get() & 0xFF)))
/* 377 */               return malformedForLength(param1ByteBuffer, i, 1); 
/* 378 */             if (m > 2 && isMalformed4_3(param1ByteBuffer.get()))
/* 379 */               return malformedForLength(param1ByteBuffer, i, 2); 
/* 380 */             return xflow(param1ByteBuffer, i, 4);
/*     */           } 
/* 382 */           byte b1 = param1ByteBuffer.get();
/* 383 */           byte b2 = param1ByteBuffer.get();
/* 384 */           byte b3 = param1ByteBuffer.get();
/* 385 */           int n = k << 18 ^ b1 << 12 ^ b2 << 6 ^ b3 ^ 0x381F80;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 393 */           if (isMalformed4(b1, b2, b3) || 
/*     */             
/* 395 */             !Character.isSupplementaryCodePoint(n)) {
/* 396 */             return malformed(param1ByteBuffer, i, 4);
/*     */           }
/* 398 */           param1CharBuffer.put(Character.highSurrogate(n));
/* 399 */           param1CharBuffer.put(Character.lowSurrogate(n));
/* 400 */           i += 4; continue;
/*     */         } 
/* 402 */         return malformed(param1ByteBuffer, i, 1);
/*     */       } 
/*     */       
/* 405 */       return xflow(param1ByteBuffer, i, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 411 */       if (param1ByteBuffer.hasArray() && param1CharBuffer.hasArray()) {
/* 412 */         return decodeArrayLoop(param1ByteBuffer, param1CharBuffer);
/*     */       }
/* 414 */       return decodeBufferLoop(param1ByteBuffer, param1CharBuffer);
/*     */     }
/*     */ 
/*     */     
/*     */     private static ByteBuffer getByteBuffer(ByteBuffer param1ByteBuffer, byte[] param1ArrayOfbyte, int param1Int) {
/* 419 */       if (param1ByteBuffer == null)
/* 420 */         param1ByteBuffer = ByteBuffer.wrap(param1ArrayOfbyte); 
/* 421 */       param1ByteBuffer.position(param1Int);
/* 422 */       return param1ByteBuffer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 428 */       int i = param1Int1 + param1Int2;
/* 429 */       byte b = 0;
/* 430 */       int j = Math.min(param1Int2, param1ArrayOfchar.length);
/* 431 */       ByteBuffer byteBuffer = null;
/*     */ 
/*     */       
/* 434 */       while (b < j && param1ArrayOfbyte[param1Int1] >= 0) {
/* 435 */         param1ArrayOfchar[b++] = (char)param1ArrayOfbyte[param1Int1++];
/*     */       }
/* 437 */       while (param1Int1 < i) {
/* 438 */         byte b1 = param1ArrayOfbyte[param1Int1++];
/* 439 */         if (b1 >= 0) {
/*     */           
/* 441 */           param1ArrayOfchar[b++] = (char)b1; continue;
/* 442 */         }  if (b1 >> 5 == -2 && (b1 & 0x1E) != 0) {
/*     */           
/* 444 */           if (param1Int1 < i) {
/* 445 */             byte b2 = param1ArrayOfbyte[param1Int1++];
/* 446 */             if (isNotContinuation(b2)) {
/* 447 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 448 */                 return -1; 
/* 449 */               param1ArrayOfchar[b++] = replacement().charAt(0);
/* 450 */               param1Int1--; continue;
/*     */             } 
/* 452 */             param1ArrayOfchar[b++] = (char)(b1 << 6 ^ b2 ^ 0xF80);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 458 */           if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 459 */             return -1; 
/* 460 */           param1ArrayOfchar[b++] = replacement().charAt(0);
/* 461 */           return b;
/* 462 */         }  if (b1 >> 4 == -2) {
/*     */           
/* 464 */           if (param1Int1 + 1 < i) {
/* 465 */             byte b2 = param1ArrayOfbyte[param1Int1++];
/* 466 */             byte b3 = param1ArrayOfbyte[param1Int1++];
/* 467 */             if (isMalformed3(b1, b2, b3)) {
/* 468 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 469 */                 return -1; 
/* 470 */               param1ArrayOfchar[b++] = replacement().charAt(0);
/* 471 */               param1Int1 -= 3;
/* 472 */               byteBuffer = getByteBuffer(byteBuffer, param1ArrayOfbyte, param1Int1);
/* 473 */               param1Int1 += malformedN(byteBuffer, 3).length(); continue;
/*     */             } 
/* 475 */             char c = (char)(b1 << 12 ^ b2 << 6 ^ b3 ^ 0xFFFE1F80);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 481 */             if (Character.isSurrogate(c)) {
/* 482 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 483 */                 return -1; 
/* 484 */               param1ArrayOfchar[b++] = replacement().charAt(0); continue;
/*     */             } 
/* 486 */             param1ArrayOfchar[b++] = c;
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 491 */           if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 492 */             return -1; 
/* 493 */           if (param1Int1 < i && isMalformed3_2(b1, param1ArrayOfbyte[param1Int1])) {
/* 494 */             param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */             
/*     */             continue;
/*     */           } 
/* 498 */           param1ArrayOfchar[b++] = replacement().charAt(0);
/* 499 */           return b;
/* 500 */         }  if (b1 >> 3 == -2) {
/*     */           
/* 502 */           if (param1Int1 + 2 < i) {
/* 503 */             byte b2 = param1ArrayOfbyte[param1Int1++];
/* 504 */             byte b3 = param1ArrayOfbyte[param1Int1++];
/* 505 */             byte b4 = param1ArrayOfbyte[param1Int1++];
/* 506 */             int m = b1 << 18 ^ b2 << 12 ^ b3 << 6 ^ b4 ^ 0x381F80;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 514 */             if (isMalformed4(b2, b3, b4) || 
/*     */               
/* 516 */               !Character.isSupplementaryCodePoint(m)) {
/* 517 */               if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 518 */                 return -1; 
/* 519 */               param1ArrayOfchar[b++] = replacement().charAt(0);
/* 520 */               param1Int1 -= 4;
/* 521 */               byteBuffer = getByteBuffer(byteBuffer, param1ArrayOfbyte, param1Int1);
/* 522 */               param1Int1 += malformedN(byteBuffer, 4).length(); continue;
/*     */             } 
/* 524 */             param1ArrayOfchar[b++] = Character.highSurrogate(m);
/* 525 */             param1ArrayOfchar[b++] = Character.lowSurrogate(m);
/*     */             
/*     */             continue;
/*     */           } 
/* 529 */           if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 530 */             return -1; 
/* 531 */           int k = b1 & 0xFF;
/* 532 */           if (k > 244 || (param1Int1 < i && 
/* 533 */             isMalformed4_2(k, param1ArrayOfbyte[param1Int1] & 0xFF))) {
/* 534 */             param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */             continue;
/*     */           } 
/* 537 */           param1Int1++;
/* 538 */           if (param1Int1 < i && isMalformed4_3(param1ArrayOfbyte[param1Int1])) {
/* 539 */             param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */             continue;
/*     */           } 
/* 542 */           param1ArrayOfchar[b++] = replacement().charAt(0);
/* 543 */           return b;
/*     */         } 
/* 545 */         if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 546 */           return -1; 
/* 547 */         param1ArrayOfchar[b++] = replacement().charAt(0);
/*     */       } 
/*     */       
/* 550 */       return b;
/*     */     }
/*     */   } private static final class Encoder extends CharsetEncoder implements ArrayEncoder { private Surrogate.Parser sgp; private byte repl; public boolean canEncode(char param1Char) {
/*     */       return !Character.isSurrogate(param1Char);
/*     */     }
/*     */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) {
/*     */       return ((param1ArrayOfbyte.length == 1 && param1ArrayOfbyte[0] >= 0) || super.isLegalReplacement(param1ArrayOfbyte));
/*     */     }
/* 558 */     private Encoder(Charset param1Charset) { super(param1Charset, 1.1F, 3.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 696 */       this.repl = 63; }
/*     */     private static CoderResult overflow(CharBuffer param1CharBuffer, int param1Int1, ByteBuffer param1ByteBuffer, int param1Int2) { UTF_8.updatePositions(param1CharBuffer, param1Int1, param1ByteBuffer, param1Int2);
/* 698 */       return CoderResult.OVERFLOW; } protected void implReplaceWith(byte[] param1ArrayOfbyte) { this.repl = param1ArrayOfbyte[0]; }
/*     */      private static CoderResult overflow(CharBuffer param1CharBuffer, int param1Int) {
/*     */       param1CharBuffer.position(param1Int);
/*     */       return CoderResult.OVERFLOW;
/*     */     }
/*     */     public int encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) {
/* 704 */       int i = param1Int1 + param1Int2;
/* 705 */       byte b = 0;
/* 706 */       int j = b + Math.min(param1Int2, param1ArrayOfbyte.length);
/*     */ 
/*     */       
/* 709 */       while (b < j && param1ArrayOfchar[param1Int1] < '') {
/* 710 */         param1ArrayOfbyte[b++] = (byte)param1ArrayOfchar[param1Int1++];
/*     */       }
/* 712 */       while (param1Int1 < i) {
/* 713 */         char c = param1ArrayOfchar[param1Int1++];
/* 714 */         if (c < '') {
/*     */           
/* 716 */           param1ArrayOfbyte[b++] = (byte)c; continue;
/* 717 */         }  if (c < 'ࠀ') {
/*     */           
/* 719 */           param1ArrayOfbyte[b++] = (byte)(0xC0 | c >> 6);
/* 720 */           param1ArrayOfbyte[b++] = (byte)(0x80 | c & 0x3F); continue;
/* 721 */         }  if (Character.isSurrogate(c)) {
/* 722 */           if (this.sgp == null)
/* 723 */             this.sgp = new Surrogate.Parser(); 
/* 724 */           int k = this.sgp.parse(c, param1ArrayOfchar, param1Int1 - 1, i);
/* 725 */           if (k < 0) {
/* 726 */             if (malformedInputAction() != CodingErrorAction.REPLACE)
/* 727 */               return -1; 
/* 728 */             param1ArrayOfbyte[b++] = this.repl; continue;
/*     */           } 
/* 730 */           param1ArrayOfbyte[b++] = (byte)(0xF0 | k >> 18);
/* 731 */           param1ArrayOfbyte[b++] = (byte)(0x80 | k >> 12 & 0x3F);
/* 732 */           param1ArrayOfbyte[b++] = (byte)(0x80 | k >> 6 & 0x3F);
/* 733 */           param1ArrayOfbyte[b++] = (byte)(0x80 | k & 0x3F);
/* 734 */           param1Int1++;
/*     */           
/*     */           continue;
/*     */         } 
/* 738 */         param1ArrayOfbyte[b++] = (byte)(0xE0 | c >> 12);
/* 739 */         param1ArrayOfbyte[b++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 740 */         param1ArrayOfbyte[b++] = (byte)(0x80 | c & 0x3F);
/*     */       } 
/*     */       
/* 743 */       return b;
/*     */     }
/*     */     
/*     */     private CoderResult encodeArrayLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*     */       char[] arrayOfChar = param1CharBuffer.array();
/*     */       int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/*     */       int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*     */       byte[] arrayOfByte = param1ByteBuffer.array();
/*     */       int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/*     */       int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*     */       int n = k + Math.min(j - i, m - k);
/*     */       while (k < n && arrayOfChar[i] < '')
/*     */         arrayOfByte[k++] = (byte)arrayOfChar[i++]; 
/*     */       while (i < j) {
/*     */         char c = arrayOfChar[i];
/*     */         if (c < '') {
/*     */           if (k >= m)
/*     */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/*     */           arrayOfByte[k++] = (byte)c;
/*     */         } else if (c < 'ࠀ') {
/*     */           if (m - k < 2)
/*     */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/*     */           arrayOfByte[k++] = (byte)(0xC0 | c >> 6);
/*     */           arrayOfByte[k++] = (byte)(0x80 | c & 0x3F);
/*     */         } else if (Character.isSurrogate(c)) {
/*     */           if (this.sgp == null)
/*     */             this.sgp = new Surrogate.Parser(); 
/*     */           int i1 = this.sgp.parse(c, arrayOfChar, i, j);
/*     */           if (i1 < 0) {
/*     */             UTF_8.updatePositions(param1CharBuffer, i, param1ByteBuffer, k);
/*     */             return this.sgp.error();
/*     */           } 
/*     */           if (m - k < 4)
/*     */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/*     */           arrayOfByte[k++] = (byte)(0xF0 | i1 >> 18);
/*     */           arrayOfByte[k++] = (byte)(0x80 | i1 >> 12 & 0x3F);
/*     */           arrayOfByte[k++] = (byte)(0x80 | i1 >> 6 & 0x3F);
/*     */           arrayOfByte[k++] = (byte)(0x80 | i1 & 0x3F);
/*     */           i++;
/*     */         } else {
/*     */           if (m - k < 3)
/*     */             return overflow(param1CharBuffer, i, param1ByteBuffer, k); 
/*     */           arrayOfByte[k++] = (byte)(0xE0 | c >> 12);
/*     */           arrayOfByte[k++] = (byte)(0x80 | c >> 6 & 0x3F);
/*     */           arrayOfByte[k++] = (byte)(0x80 | c & 0x3F);
/*     */         } 
/*     */         i++;
/*     */       } 
/*     */       UTF_8.updatePositions(param1CharBuffer, i, param1ByteBuffer, k);
/*     */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */     
/*     */     private CoderResult encodeBufferLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*     */       int i = param1CharBuffer.position();
/*     */       while (param1CharBuffer.hasRemaining()) {
/*     */         char c = param1CharBuffer.get();
/*     */         if (c < '') {
/*     */           if (!param1ByteBuffer.hasRemaining())
/*     */             return overflow(param1CharBuffer, i); 
/*     */           param1ByteBuffer.put((byte)c);
/*     */         } else if (c < 'ࠀ') {
/*     */           if (param1ByteBuffer.remaining() < 2)
/*     */             return overflow(param1CharBuffer, i); 
/*     */           param1ByteBuffer.put((byte)(0xC0 | c >> 6));
/*     */           param1ByteBuffer.put((byte)(0x80 | c & 0x3F));
/*     */         } else if (Character.isSurrogate(c)) {
/*     */           if (this.sgp == null)
/*     */             this.sgp = new Surrogate.Parser(); 
/*     */           int j = this.sgp.parse(c, param1CharBuffer);
/*     */           if (j < 0) {
/*     */             param1CharBuffer.position(i);
/*     */             return this.sgp.error();
/*     */           } 
/*     */           if (param1ByteBuffer.remaining() < 4)
/*     */             return overflow(param1CharBuffer, i); 
/*     */           param1ByteBuffer.put((byte)(0xF0 | j >> 18));
/*     */           param1ByteBuffer.put((byte)(0x80 | j >> 12 & 0x3F));
/*     */           param1ByteBuffer.put((byte)(0x80 | j >> 6 & 0x3F));
/*     */           param1ByteBuffer.put((byte)(0x80 | j & 0x3F));
/*     */           i++;
/*     */         } else {
/*     */           if (param1ByteBuffer.remaining() < 3)
/*     */             return overflow(param1CharBuffer, i); 
/*     */           param1ByteBuffer.put((byte)(0xE0 | c >> 12));
/*     */           param1ByteBuffer.put((byte)(0x80 | c >> 6 & 0x3F));
/*     */           param1ByteBuffer.put((byte)(0x80 | c & 0x3F));
/*     */         } 
/*     */         i++;
/*     */       } 
/*     */       param1CharBuffer.position(i);
/*     */       return CoderResult.UNDERFLOW;
/*     */     }
/*     */     
/*     */     protected final CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*     */       if (param1CharBuffer.hasArray() && param1ByteBuffer.hasArray())
/*     */         return encodeArrayLoop(param1CharBuffer, param1ByteBuffer); 
/*     */       return encodeBufferLoop(param1CharBuffer, param1ByteBuffer);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UTF_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */