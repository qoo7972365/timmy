/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BerDecoder
/*     */   extends Ber
/*     */ {
/*     */   private int origOffset;
/*     */   
/*     */   public BerDecoder(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  45 */     this.buf = paramArrayOfbyte;
/*  46 */     this.bufsize = paramInt2;
/*  47 */     this.origOffset = paramInt1;
/*     */     
/*  49 */     reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  57 */     this.offset = this.origOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParsePosition() {
/*  66 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseLength() throws Ber.DecodeException {
/*  74 */     int i = parseByte();
/*     */     
/*  76 */     if ((i & 0x80) == 128) {
/*     */       
/*  78 */       i &= 0x7F;
/*     */       
/*  80 */       if (i == 0) {
/*  81 */         throw new Ber.DecodeException("Indefinite length not supported");
/*     */       }
/*     */ 
/*     */       
/*  85 */       if (i > 4) {
/*  86 */         throw new Ber.DecodeException("encoding too long");
/*     */       }
/*     */       
/*  89 */       if (this.bufsize - this.offset < i) {
/*  90 */         throw new Ber.DecodeException("Insufficient data");
/*     */       }
/*     */       
/*  93 */       int j = 0;
/*     */       
/*  95 */       for (byte b = 0; b < i; b++) {
/*  96 */         j = (j << 8) + (this.buf[this.offset++] & 0xFF);
/*     */       }
/*  98 */       if (j < 0) {
/*  99 */         throw new Ber.DecodeException("Invalid length bytes");
/*     */       }
/* 101 */       return j;
/*     */     } 
/* 103 */     return i;
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
/*     */   public int parseSeq(int[] paramArrayOfint) throws Ber.DecodeException {
/* 115 */     int i = parseByte();
/* 116 */     int j = parseLength();
/* 117 */     if (paramArrayOfint != null) {
/* 118 */       paramArrayOfint[0] = j;
/*     */     }
/* 120 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void seek(int paramInt) throws Ber.DecodeException {
/* 129 */     if (this.offset + paramInt > this.bufsize || this.offset + paramInt < 0) {
/* 130 */       throw new Ber.DecodeException("array index out of bounds");
/*     */     }
/* 132 */     this.offset += paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseByte() throws Ber.DecodeException {
/* 140 */     if (this.bufsize - this.offset < 1) {
/* 141 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/* 143 */     return this.buf[this.offset++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int peekByte() throws Ber.DecodeException {
/* 152 */     if (this.bufsize - this.offset < 1) {
/* 153 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/* 155 */     return this.buf[this.offset] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean parseBoolean() throws Ber.DecodeException {
/* 163 */     return !(parseIntWithTag(1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseEnumeration() throws Ber.DecodeException {
/* 171 */     return parseIntWithTag(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int parseInt() throws Ber.DecodeException {
/* 179 */     return parseIntWithTag(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseIntWithTag(int paramInt) throws Ber.DecodeException {
/* 189 */     if (parseByte() != paramInt) {
/*     */       String str;
/*     */       
/* 192 */       if (this.offset > 0) {
/* 193 */         str = Integer.toString(this.buf[this.offset - 1] & 0xFF);
/*     */       } else {
/* 195 */         str = "Empty tag";
/*     */       } 
/* 197 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + str + " (expected tag " + 
/* 198 */           Integer.toString(paramInt) + ")");
/*     */     } 
/*     */     
/* 201 */     int i = parseLength();
/*     */     
/* 203 */     if (i > 4)
/* 204 */       throw new Ber.DecodeException("INTEGER too long"); 
/* 205 */     if (i > this.bufsize - this.offset) {
/* 206 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */     
/* 209 */     byte b = this.buf[this.offset++];
/* 210 */     int j = 0;
/*     */     
/* 212 */     j = b & Byte.MAX_VALUE;
/* 213 */     for (byte b1 = 1; b1 < i; b1++) {
/* 214 */       j <<= 8;
/* 215 */       j |= this.buf[this.offset++] & 0xFF;
/*     */     } 
/*     */     
/* 218 */     if ((b & 0x80) == 128) {
/* 219 */       j = -j;
/*     */     }
/*     */     
/* 222 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String parseString(boolean paramBoolean) throws Ber.DecodeException {
/* 229 */     return parseStringWithTag(4, paramBoolean, (int[])null);
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
/*     */   public String parseStringWithTag(int paramInt, boolean paramBoolean, int[] paramArrayOfint) throws Ber.DecodeException {
/*     */     String str;
/* 248 */     int j = this.offset;
/*     */     int i;
/* 250 */     if ((i = parseByte()) != paramInt) {
/* 251 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + 
/* 252 */           Integer.toString((byte)i) + " (expected tag " + paramInt + ")");
/*     */     }
/*     */     
/* 255 */     int k = parseLength();
/*     */     
/* 257 */     if (k > this.bufsize - this.offset) {
/* 258 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */ 
/*     */     
/* 262 */     if (k == 0) {
/* 263 */       str = "";
/*     */     } else {
/* 265 */       byte[] arrayOfByte = new byte[k];
/*     */       
/* 267 */       System.arraycopy(this.buf, this.offset, arrayOfByte, 0, k);
/* 268 */       if (paramBoolean) {
/*     */         try {
/* 270 */           str = new String(arrayOfByte, "UTF8");
/* 271 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 272 */           throw new Ber.DecodeException("UTF8 not available on platform");
/*     */         } 
/*     */       } else {
/*     */         try {
/* 276 */           str = new String(arrayOfByte, "8859_1");
/* 277 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 278 */           throw new Ber.DecodeException("8859_1 not available on platform");
/*     */         } 
/*     */       } 
/* 281 */       this.offset += k;
/*     */     } 
/*     */     
/* 284 */     if (paramArrayOfint != null) {
/* 285 */       paramArrayOfint[0] = this.offset - j;
/*     */     }
/*     */     
/* 288 */     return str;
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
/*     */   public byte[] parseOctetString(int paramInt, int[] paramArrayOfint) throws Ber.DecodeException {
/* 307 */     int i = this.offset;
/*     */     int j;
/* 309 */     if ((j = parseByte()) != paramInt)
/*     */     {
/* 311 */       throw new Ber.DecodeException("Encountered ASN.1 tag " + 
/* 312 */           Integer.toString(j) + " (expected tag " + 
/* 313 */           Integer.toString(paramInt) + ")");
/*     */     }
/*     */     
/* 316 */     int k = parseLength();
/*     */     
/* 318 */     if (k > this.bufsize - this.offset) {
/* 319 */       throw new Ber.DecodeException("Insufficient data");
/*     */     }
/*     */     
/* 322 */     byte[] arrayOfByte = new byte[k];
/* 323 */     if (k > 0) {
/* 324 */       System.arraycopy(this.buf, this.offset, arrayOfByte, 0, k);
/* 325 */       this.offset += k;
/*     */     } 
/*     */     
/* 328 */     if (paramArrayOfint != null) {
/* 329 */       paramArrayOfint[0] = this.offset - i;
/*     */     }
/*     */     
/* 332 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int bytesLeft() {
/* 339 */     return this.bufsize - this.offset;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/BerDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */