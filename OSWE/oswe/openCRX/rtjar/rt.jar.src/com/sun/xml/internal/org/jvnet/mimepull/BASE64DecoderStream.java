/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BASE64DecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*  43 */   private byte[] buffer = new byte[3];
/*  44 */   private int bufsize = 0;
/*  45 */   private int index = 0;
/*     */ 
/*     */ 
/*     */   
/*  49 */   private byte[] input_buffer = new byte[8190];
/*  50 */   private int input_pos = 0;
/*  51 */   private int input_len = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ignoreErrors = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64DecoderStream(InputStream in) {
/*  64 */     super(in);
/*     */     
/*  66 */     this.ignoreErrors = PropUtil.getBooleanSystemProperty("mail.mime.base64.ignoreerrors", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64DecoderStream(InputStream in, boolean ignoreErrors) {
/*  77 */     super(in);
/*  78 */     this.ignoreErrors = ignoreErrors;
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
/*     */   public int read() throws IOException {
/*  96 */     if (this.index >= this.bufsize) {
/*  97 */       this.bufsize = decode(this.buffer, 0, this.buffer.length);
/*  98 */       if (this.bufsize <= 0) {
/*  99 */         return -1;
/*     */       }
/* 101 */       this.index = 0;
/*     */     } 
/* 103 */     return this.buffer[this.index++] & 0xFF;
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
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/* 123 */     int off0 = off;
/* 124 */     while (this.index < this.bufsize && len > 0) {
/* 125 */       buf[off++] = this.buffer[this.index++];
/* 126 */       len--;
/*     */     } 
/* 128 */     if (this.index >= this.bufsize) {
/* 129 */       this.bufsize = this.index = 0;
/*     */     }
/*     */     
/* 132 */     int bsize = len / 3 * 3;
/* 133 */     if (bsize > 0) {
/* 134 */       int size = decode(buf, off, bsize);
/* 135 */       off += size;
/* 136 */       len -= size;
/*     */       
/* 138 */       if (size != bsize) {
/* 139 */         if (off == off0) {
/* 140 */           return -1;
/*     */         }
/* 142 */         return off - off0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     for (; len > 0; len--) {
/* 149 */       int c = read();
/* 150 */       if (c == -1) {
/*     */         break;
/*     */       }
/* 153 */       buf[off++] = (byte)c;
/*     */     } 
/*     */     
/* 156 */     if (off == off0) {
/* 157 */       return -1;
/*     */     }
/* 159 */     return off - off0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 168 */     long skipped = 0L;
/* 169 */     while (n-- > 0L && read() >= 0) {
/* 170 */       skipped++;
/*     */     }
/* 172 */     return skipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 181 */     return false;
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
/*     */   public int available() throws IOException {
/* 194 */     return this.in.available() * 3 / 4 + this.bufsize - this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   private static final byte[] pem_convert_array = new byte[256];
/*     */   static {
/*     */     int i;
/* 215 */     for (i = 0; i < 255; i++) {
/* 216 */       pem_convert_array[i] = -1;
/*     */     }
/* 218 */     for (i = 0; i < pem_array.length; i++) {
/* 219 */       pem_convert_array[pem_array[i]] = (byte)i;
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
/*     */   private int decode(byte[] outbuf, int pos, int len) throws IOException {
/* 238 */     int pos0 = pos;
/* 239 */     while (len >= 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       int got = 0;
/* 246 */       int val = 0;
/* 247 */       while (got < 4) {
/* 248 */         int i = getByte();
/* 249 */         if (i == -1 || i == -2) {
/*     */           boolean atEOF;
/* 251 */           if (i == -1) {
/* 252 */             if (got == 0) {
/* 253 */               return pos - pos0;
/*     */             }
/* 255 */             if (!this.ignoreErrors) {
/* 256 */               throw new DecodingException("BASE64Decoder: Error in encoded stream: needed 4 valid base64 characters but only got " + got + " before EOF" + 
/*     */ 
/*     */ 
/*     */                   
/* 260 */                   recentChars());
/*     */             }
/* 262 */             atEOF = true;
/*     */           }
/*     */           else {
/*     */             
/* 266 */             if (got < 2 && !this.ignoreErrors) {
/* 267 */               throw new DecodingException("BASE64Decoder: Error in encoded stream: needed at least 2 valid base64 characters, but only got " + got + " before padding character (=)" + 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 272 */                   recentChars());
/*     */             }
/*     */ 
/*     */             
/* 276 */             if (got == 0) {
/* 277 */               return pos - pos0;
/*     */             }
/* 279 */             atEOF = false;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 286 */           int size = got - 1;
/* 287 */           if (size == 0) {
/* 288 */             size = 1;
/*     */           }
/*     */ 
/*     */           
/* 292 */           got++;
/* 293 */           val <<= 6;
/*     */           
/* 295 */           while (got < 4) {
/* 296 */             if (!atEOF) {
/*     */ 
/*     */               
/* 299 */               i = getByte();
/* 300 */               if (i == -1) {
/* 301 */                 if (!this.ignoreErrors) {
/* 302 */                   throw new DecodingException("BASE64Decoder: Error in encoded stream: hit EOF while looking for padding characters (=)" + 
/*     */ 
/*     */ 
/*     */                       
/* 306 */                       recentChars());
/*     */                 }
/* 308 */               } else if (i != -2 && 
/* 309 */                 !this.ignoreErrors) {
/* 310 */                 throw new DecodingException("BASE64Decoder: Error in encoded stream: found valid base64 character after a padding character (=)" + 
/*     */ 
/*     */ 
/*     */                     
/* 314 */                     recentChars());
/*     */               } 
/*     */             } 
/*     */             
/* 318 */             val <<= 6;
/* 319 */             got++;
/*     */           } 
/*     */ 
/*     */           
/* 323 */           val >>= 8;
/* 324 */           if (size == 2) {
/* 325 */             outbuf[pos + 1] = (byte)(val & 0xFF);
/*     */           }
/* 327 */           val >>= 8;
/* 328 */           outbuf[pos] = (byte)(val & 0xFF);
/*     */           
/* 330 */           pos += size;
/* 331 */           return pos - pos0;
/*     */         } 
/*     */         
/* 334 */         val <<= 6;
/* 335 */         got++;
/* 336 */         val |= i;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 341 */       outbuf[pos + 2] = (byte)(val & 0xFF);
/* 342 */       val >>= 8;
/* 343 */       outbuf[pos + 1] = (byte)(val & 0xFF);
/* 344 */       val >>= 8;
/* 345 */       outbuf[pos] = (byte)(val & 0xFF);
/* 346 */       len -= 3;
/* 347 */       pos += 3;
/*     */     } 
/* 349 */     return pos - pos0;
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
/*     */   private int getByte() throws IOException {
/*     */     while (true) {
/* 363 */       if (this.input_pos >= this.input_len) {
/*     */         try {
/* 365 */           this.input_len = this.in.read(this.input_buffer);
/* 366 */         } catch (EOFException ex) {
/* 367 */           return -1;
/*     */         } 
/* 369 */         if (this.input_len <= 0) {
/* 370 */           return -1;
/*     */         }
/* 372 */         this.input_pos = 0;
/*     */       } 
/*     */       
/* 375 */       int c = this.input_buffer[this.input_pos++] & 0xFF;
/*     */       
/* 377 */       if (c == 61) {
/* 378 */         return -2;
/*     */       }
/*     */       
/* 381 */       c = pem_convert_array[c];
/*     */       
/* 383 */       if (c != -1) {
/* 384 */         return c;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String recentChars() {
/* 393 */     StringBuilder errstr = new StringBuilder();
/* 394 */     int nc = (this.input_pos > 10) ? 10 : this.input_pos;
/* 395 */     if (nc > 0) {
/* 396 */       errstr.append(", the ").append(nc).append(" most recent characters were: \"");
/* 397 */       for (int k = this.input_pos - nc; k < this.input_pos; k++) {
/* 398 */         char c = (char)(this.input_buffer[k] & 0xFF);
/* 399 */         switch (c) { case '\r':
/* 400 */             errstr.append("\\r"); break;
/* 401 */           case '\n': errstr.append("\\n"); break;
/* 402 */           case '\t': errstr.append("\\t"); break;
/*     */           default:
/* 404 */             if (c >= ' ' && c < '') {
/* 405 */               errstr.append(c); break;
/*     */             } 
/* 407 */             errstr.append("\\").append(c);
/*     */             break; }
/*     */       
/*     */       } 
/* 411 */       errstr.append("\"");
/*     */     } 
/* 413 */     return errstr.toString();
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
/*     */   public static byte[] decode(byte[] inbuf) {
/* 426 */     int size = inbuf.length / 4 * 3;
/* 427 */     if (size == 0) {
/* 428 */       return inbuf;
/*     */     }
/*     */     
/* 431 */     if (inbuf[inbuf.length - 1] == 61) {
/* 432 */       size--;
/* 433 */       if (inbuf[inbuf.length - 2] == 61) {
/* 434 */         size--;
/*     */       }
/*     */     } 
/* 437 */     byte[] outbuf = new byte[size];
/*     */     
/* 439 */     int inpos = 0, outpos = 0;
/* 440 */     size = inbuf.length;
/* 441 */     while (size > 0) {
/*     */       
/* 443 */       int osize = 3;
/* 444 */       int val = pem_convert_array[inbuf[inpos++] & 0xFF];
/* 445 */       val <<= 6;
/* 446 */       val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/* 447 */       val <<= 6;
/* 448 */       if (inbuf[inpos] != 61) {
/* 449 */         val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       } else {
/* 451 */         osize--;
/*     */       } 
/* 453 */       val <<= 6;
/* 454 */       if (inbuf[inpos] != 61) {
/* 455 */         val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       } else {
/* 457 */         osize--;
/*     */       } 
/* 459 */       if (osize > 2) {
/* 460 */         outbuf[outpos + 2] = (byte)(val & 0xFF);
/*     */       }
/* 462 */       val >>= 8;
/* 463 */       if (osize > 1) {
/* 464 */         outbuf[outpos + 1] = (byte)(val & 0xFF);
/*     */       }
/* 466 */       val >>= 8;
/* 467 */       outbuf[outpos] = (byte)(val & 0xFF);
/* 468 */       outpos += osize;
/* 469 */       size -= 4;
/*     */     } 
/* 471 */     return outbuf;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/BASE64DecoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */