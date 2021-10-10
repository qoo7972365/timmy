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
/*     */ public class US_ASCII
/*     */   extends Charset
/*     */   implements HistoricallyNamedCharset
/*     */ {
/*     */   public US_ASCII() {
/*  42 */     super("US-ASCII", StandardCharsets.aliases_US_ASCII);
/*     */   }
/*     */   
/*     */   public String historicalName() {
/*  46 */     return "ASCII";
/*     */   }
/*     */   
/*     */   public boolean contains(Charset paramCharset) {
/*  50 */     return paramCharset instanceof US_ASCII;
/*     */   }
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  54 */     return new Decoder(this);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  58 */     return new Encoder(this);
/*     */   }
/*     */   
/*     */   private static class Decoder extends CharsetDecoder implements ArrayDecoder
/*     */   {
/*     */     private char repl;
/*     */     
/*  65 */     private Decoder(Charset param1Charset) { super(param1Charset, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       this.repl = '�'; } private CoderResult decodeArrayLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { byte[] arrayOfByte = param1ByteBuffer.array(); int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position(); int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit(); assert i <= j; i = (i <= j) ? i : j; char[] arrayOfChar = param1CharBuffer.array(); int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position(); int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit(); assert k <= m; k = (k <= m) ? k : m; try { while (i < j) { byte b = arrayOfByte[i]; if (b >= 0) { if (k >= m) return CoderResult.OVERFLOW;  arrayOfChar[k++] = (char)b; i++; continue; }  return CoderResult.malformedForLength(1); }  return CoderResult.UNDERFLOW; } finally { param1ByteBuffer.position(i - param1ByteBuffer.arrayOffset()); param1CharBuffer.position(k - param1CharBuffer.arrayOffset()); }  } private CoderResult decodeBufferLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { int i = param1ByteBuffer.position(); try { while (param1ByteBuffer.hasRemaining()) { byte b = param1ByteBuffer.get(); if (b >= 0) { if (!param1CharBuffer.hasRemaining()) return CoderResult.OVERFLOW;  param1CharBuffer.put((char)b); i++; continue; }  return CoderResult.malformedForLength(1); }  return CoderResult.UNDERFLOW; } finally { param1ByteBuffer.position(i); }  }
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { if (param1ByteBuffer.hasArray() && param1CharBuffer.hasArray()) return decodeArrayLoop(param1ByteBuffer, param1CharBuffer);  return decodeBufferLoop(param1ByteBuffer, param1CharBuffer); }
/* 134 */     protected void implReplaceWith(String param1String) { this.repl = param1String.charAt(0); }
/*     */ 
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 138 */       byte b = 0;
/* 139 */       param1Int2 = Math.min(param1Int2, param1ArrayOfchar.length);
/* 140 */       while (b < param1Int2) {
/* 141 */         byte b1 = param1ArrayOfbyte[param1Int1++];
/* 142 */         if (b1 >= 0) {
/* 143 */           param1ArrayOfchar[b++] = (char)b1; continue;
/*     */         } 
/* 145 */         param1ArrayOfchar[b++] = this.repl;
/*     */       } 
/* 147 */       return b;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Encoder extends CharsetEncoder implements ArrayEncoder {
/*     */     private final Surrogate.Parser sgp;
/*     */     private byte repl;
/*     */     
/* 155 */     private Encoder(Charset param1Charset) { super(param1Charset, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 167 */       this.sgp = new Surrogate.Parser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       this.repl = 63; }
/*     */     public boolean canEncode(char param1Char) { return (param1Char < ''); }
/* 238 */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) { return ((param1ArrayOfbyte.length == 1 && param1ArrayOfbyte[0] >= 0) || super.isLegalReplacement(param1ArrayOfbyte)); } protected void implReplaceWith(byte[] param1ArrayOfbyte) { this.repl = param1ArrayOfbyte[0]; }
/*     */     private CoderResult encodeArrayLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { char[] arrayOfChar = param1CharBuffer.array(); int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position(); int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit(); assert i <= j; i = (i <= j) ? i : j; byte[] arrayOfByte = param1ByteBuffer.array(); int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position(); int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit(); assert k <= m; k = (k <= m) ? k : m; try { while (i < j) { char c = arrayOfChar[i]; if (c < '') { if (k >= m) return CoderResult.OVERFLOW;  arrayOfByte[k] = (byte)c; i++; k++; continue; }  if (this.sgp.parse(c, arrayOfChar, i, j) < 0) return this.sgp.error();  return this.sgp.unmappableResult(); }  return CoderResult.UNDERFLOW; } finally { param1CharBuffer.position(i - param1CharBuffer.arrayOffset()); param1ByteBuffer.position(k - param1ByteBuffer.arrayOffset()); }  }
/*     */     private CoderResult encodeBufferLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { int i = param1CharBuffer.position(); try { while (param1CharBuffer.hasRemaining()) { char c = param1CharBuffer.get(); if (c < '') { if (!param1ByteBuffer.hasRemaining()) return CoderResult.OVERFLOW;  param1ByteBuffer.put((byte)c); i++; continue; }  if (this.sgp.parse(c, param1CharBuffer) < 0) return this.sgp.error();  return this.sgp.unmappableResult(); }  return CoderResult.UNDERFLOW; } finally { param1CharBuffer.position(i); }  }
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { if (param1CharBuffer.hasArray() && param1ByteBuffer.hasArray())
/* 242 */         return encodeArrayLoop(param1CharBuffer, param1ByteBuffer);  return encodeBufferLoop(param1CharBuffer, param1ByteBuffer); } public int encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) { byte b = 0;
/* 243 */       int i = param1Int1 + Math.min(param1Int2, param1ArrayOfbyte.length);
/* 244 */       while (param1Int1 < i) {
/* 245 */         char c = param1ArrayOfchar[param1Int1++];
/* 246 */         if (c < '') {
/* 247 */           param1ArrayOfbyte[b++] = (byte)c;
/*     */           continue;
/*     */         } 
/* 250 */         if (Character.isHighSurrogate(c) && param1Int1 < i && 
/* 251 */           Character.isLowSurrogate(param1ArrayOfchar[param1Int1])) {
/* 252 */           if (param1Int2 > param1ArrayOfbyte.length) {
/* 253 */             i++;
/* 254 */             param1Int2--;
/*     */           } 
/* 256 */           param1Int1++;
/*     */         } 
/* 258 */         param1ArrayOfbyte[b++] = this.repl;
/*     */       } 
/* 260 */       return b; }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/US_ASCII.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */