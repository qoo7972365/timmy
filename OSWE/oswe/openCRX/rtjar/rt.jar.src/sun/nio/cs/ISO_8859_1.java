/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ISO_8859_1
/*     */   extends Charset
/*     */   implements HistoricallyNamedCharset
/*     */ {
/*     */   public ISO_8859_1() {
/*  42 */     super("ISO-8859-1", StandardCharsets.aliases_ISO_8859_1);
/*     */   }
/*     */   
/*     */   public String historicalName() {
/*  46 */     return "ISO8859_1";
/*     */   }
/*     */   
/*     */   public boolean contains(Charset paramCharset) {
/*  50 */     return (paramCharset instanceof US_ASCII || paramCharset instanceof ISO_8859_1);
/*     */   }
/*     */ 
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  55 */     return new Decoder(this);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  59 */     return new Encoder(this);
/*     */   }
/*     */   
/*     */   private static class Decoder
/*     */     extends CharsetDecoder implements ArrayDecoder {
/*     */     private Decoder(Charset param1Charset) {
/*  65 */       super(param1Charset, 1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeArrayLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/*  71 */       byte[] arrayOfByte = param1ByteBuffer.array();
/*  72 */       int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/*  73 */       int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*  74 */       assert i <= j;
/*  75 */       i = (i <= j) ? i : j;
/*  76 */       char[] arrayOfChar = param1CharBuffer.array();
/*  77 */       int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/*  78 */       int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*  79 */       assert k <= m;
/*  80 */       k = (k <= m) ? k : m;
/*     */       
/*     */       try {
/*  83 */         while (i < j) {
/*  84 */           byte b = arrayOfByte[i];
/*  85 */           if (k >= m)
/*  86 */             return CoderResult.OVERFLOW; 
/*  87 */           arrayOfChar[k++] = (char)(b & 0xFF);
/*  88 */           i++;
/*     */         } 
/*  90 */         return CoderResult.UNDERFLOW;
/*     */       } finally {
/*  92 */         param1ByteBuffer.position(i - param1ByteBuffer.arrayOffset());
/*  93 */         param1CharBuffer.position(k - param1CharBuffer.arrayOffset());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private CoderResult decodeBufferLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 100 */       int i = param1ByteBuffer.position();
/*     */       try {
/* 102 */         while (param1ByteBuffer.hasRemaining()) {
/* 103 */           byte b = param1ByteBuffer.get();
/* 104 */           if (!param1CharBuffer.hasRemaining())
/* 105 */             return CoderResult.OVERFLOW; 
/* 106 */           param1CharBuffer.put((char)(b & 0xFF));
/* 107 */           i++;
/*     */         } 
/* 109 */         return CoderResult.UNDERFLOW;
/*     */       } finally {
/* 111 */         param1ByteBuffer.position(i);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 118 */       if (param1ByteBuffer.hasArray() && param1CharBuffer.hasArray()) {
/* 119 */         return decodeArrayLoop(param1ByteBuffer, param1CharBuffer);
/*     */       }
/* 121 */       return decodeBufferLoop(param1ByteBuffer, param1CharBuffer);
/*     */     }
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 125 */       if (param1Int2 > param1ArrayOfchar.length)
/* 126 */         param1Int2 = param1ArrayOfchar.length; 
/* 127 */       byte b = 0;
/* 128 */       while (b < param1Int2)
/* 129 */         param1ArrayOfchar[b++] = (char)(param1ArrayOfbyte[param1Int1++] & 0xFF); 
/* 130 */       return b;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Encoder extends CharsetEncoder implements ArrayEncoder { private final Surrogate.Parser sgp;
/*     */     private byte repl;
/*     */     
/* 137 */     private Encoder(Charset param1Charset) { super(param1Charset, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       this.sgp = new Surrogate.Parser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       this.repl = 63; }
/*     */     public boolean canEncode(char param1Char) { return (param1Char <= 'ÿ'); }
/* 236 */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) { return true; } private static int encodeISOArray(char[] param1ArrayOfchar, int param1Int1, byte[] param1ArrayOfbyte, int param1Int2, int param1Int3) { byte b = 0; for (; b < param1Int3; b++) { char c = param1ArrayOfchar[param1Int1++]; if (c > 'ÿ') break;  param1ArrayOfbyte[param1Int2++] = (byte)c; }  return b; } protected void implReplaceWith(byte[] param1ArrayOfbyte) { this.repl = param1ArrayOfbyte[0]; }
/*     */     private CoderResult encodeArrayLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { char[] arrayOfChar = param1CharBuffer.array(); int i = param1CharBuffer.arrayOffset(); int j = i + param1CharBuffer.position(); int k = i + param1CharBuffer.limit(); assert j <= k; j = (j <= k) ? j : k; byte[] arrayOfByte = param1ByteBuffer.array(); int m = param1ByteBuffer.arrayOffset(); int n = m + param1ByteBuffer.position(); int i1 = m + param1ByteBuffer.limit(); assert n <= i1; n = (n <= i1) ? n : i1; int i2 = i1 - n; int i3 = k - j; int i4 = (i2 < i3) ? i2 : i3; try { byte b = (i4 <= 0) ? 0 : encodeISOArray(arrayOfChar, j, arrayOfByte, n, i4); j += b; n += b; if (b != i4) { if (this.sgp.parse(arrayOfChar[j], arrayOfChar, j, k) < 0) return this.sgp.error();  return this.sgp.unmappableResult(); }  if (i4 < i3) return CoderResult.OVERFLOW;  return CoderResult.UNDERFLOW; } finally { param1CharBuffer.position(j - i); param1ByteBuffer.position(n - m); }  }
/*     */     private CoderResult encodeBufferLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { int i = param1CharBuffer.position(); try { while (param1CharBuffer.hasRemaining()) { char c = param1CharBuffer.get(); if (c <= 'ÿ') { if (!param1ByteBuffer.hasRemaining()) return CoderResult.OVERFLOW;  param1ByteBuffer.put((byte)c); i++; continue; }  if (this.sgp.parse(c, param1CharBuffer) < 0) return this.sgp.error();  return this.sgp.unmappableResult(); }  return CoderResult.UNDERFLOW; } finally { param1CharBuffer.position(i); }  }
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { if (param1CharBuffer.hasArray() && param1ByteBuffer.hasArray())
/* 240 */         return encodeArrayLoop(param1CharBuffer, param1ByteBuffer);  return encodeBufferLoop(param1CharBuffer, param1ByteBuffer); } public int encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) { int i = 0;
/* 241 */       int j = Math.min(param1Int2, param1ArrayOfbyte.length);
/* 242 */       int k = param1Int1 + j;
/* 243 */       while (param1Int1 < k) {
/*     */         
/* 245 */         byte b = (j <= 0) ? 0 : encodeISOArray(param1ArrayOfchar, param1Int1, param1ArrayOfbyte, i, j);
/* 246 */         param1Int1 += b;
/* 247 */         i += b;
/* 248 */         if (b != j) {
/* 249 */           char c = param1ArrayOfchar[param1Int1++];
/* 250 */           if (Character.isHighSurrogate(c) && param1Int1 < k && 
/* 251 */             Character.isLowSurrogate(param1ArrayOfchar[param1Int1])) {
/* 252 */             if (param1Int2 > param1ArrayOfbyte.length) {
/* 253 */               k++;
/* 254 */               param1Int2--;
/*     */             } 
/* 256 */             param1Int1++;
/*     */           } 
/* 258 */           param1ArrayOfbyte[i++] = this.repl;
/* 259 */           j = Math.min(k - param1Int1, param1ArrayOfbyte.length - i);
/*     */         } 
/*     */       } 
/* 262 */       return i; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/ISO_8859_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */