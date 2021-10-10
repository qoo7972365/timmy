/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.Buffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingleByte
/*     */ {
/*     */   private static final CoderResult withResult(CoderResult paramCoderResult, Buffer paramBuffer1, int paramInt1, Buffer paramBuffer2, int paramInt2) {
/*  44 */     paramBuffer1.position(paramInt1 - paramBuffer1.arrayOffset());
/*  45 */     paramBuffer2.position(paramInt2 - paramBuffer2.arrayOffset());
/*  46 */     return paramCoderResult;
/*     */   }
/*     */   
/*     */   public static final class Decoder extends CharsetDecoder implements ArrayDecoder {
/*     */     private final char[] b2c;
/*     */     private char repl;
/*     */     
/*     */     public Decoder(Charset param1Charset, char[] param1ArrayOfchar) {
/*  54 */       super(param1Charset, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       this.repl = '�'; this.b2c = param1ArrayOfchar;
/*     */     } private CoderResult decodeArrayLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { byte[] arrayOfByte = param1ByteBuffer.array(); int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position(); int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit(); char[] arrayOfChar = param1CharBuffer.array(); int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position(); int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit(); CoderResult coderResult = CoderResult.UNDERFLOW; if (m - k < j - i) { j = i + m - k; coderResult = CoderResult.OVERFLOW; }  while (i < j) { char c = decode(arrayOfByte[i]); if (c == '�') return SingleByte.withResult(CoderResult.unmappableForLength(1), param1ByteBuffer, i, param1CharBuffer, k);  arrayOfChar[k++] = c; i++; }  return SingleByte.withResult(coderResult, param1ByteBuffer, i, param1CharBuffer, k); } private CoderResult decodeBufferLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { int i = param1ByteBuffer.position(); try { while (param1ByteBuffer.hasRemaining()) { char c = decode(param1ByteBuffer.get()); if (c == '�') return CoderResult.unmappableForLength(1);  if (!param1CharBuffer.hasRemaining()) return CoderResult.OVERFLOW;  param1CharBuffer.put(c); i++; }  return CoderResult.UNDERFLOW; } finally { param1ByteBuffer.position(i); }  } protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) { if (param1ByteBuffer.hasArray() && param1CharBuffer.hasArray()) return decodeArrayLoop(param1ByteBuffer, param1CharBuffer);  return decodeBufferLoop(param1ByteBuffer, param1CharBuffer); } public final char decode(int param1Int) { return this.b2c[param1Int + 128]; }
/* 116 */     protected void implReplaceWith(String param1String) { this.repl = param1String.charAt(0); }
/*     */ 
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, char[] param1ArrayOfchar) {
/* 120 */       if (param1Int2 > param1ArrayOfchar.length)
/* 121 */         param1Int2 = param1ArrayOfchar.length; 
/* 122 */       byte b = 0;
/* 123 */       while (b < param1Int2) {
/* 124 */         param1ArrayOfchar[b] = decode(param1ArrayOfbyte[param1Int1++]);
/* 125 */         if (param1ArrayOfchar[b] == '�') {
/* 126 */           param1ArrayOfchar[b] = this.repl;
/*     */         }
/* 128 */         b++;
/*     */       } 
/* 130 */       return b;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Encoder extends CharsetEncoder implements ArrayEncoder {
/*     */     private Surrogate.Parser sgp;
/*     */     private final char[] c2b;
/*     */     private final char[] c2bIndex;
/*     */     private byte repl;
/*     */     
/*     */     public Encoder(Charset param1Charset, char[] param1ArrayOfchar1, char[] param1ArrayOfchar2) {
/* 141 */       super(param1Charset, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       this.repl = 63; this.c2b = param1ArrayOfchar1; this.c2bIndex = param1ArrayOfchar2;
/*     */     }
/* 233 */     public boolean canEncode(char param1Char) { return (encode(param1Char) != 65533); } public boolean isLegalReplacement(byte[] param1ArrayOfbyte) { return ((param1ArrayOfbyte.length == 1 && param1ArrayOfbyte[0] == 63) || super.isLegalReplacement(param1ArrayOfbyte)); } private CoderResult encodeArrayLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { char[] arrayOfChar = param1CharBuffer.array(); int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position(); int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit(); byte[] arrayOfByte = param1ByteBuffer.array(); int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position(); int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit(); CoderResult coderResult = CoderResult.UNDERFLOW; if (m - k < j - i) { j = i + m - k; coderResult = CoderResult.OVERFLOW; }  while (i < j) { char c = arrayOfChar[i]; int n = encode(c); if (n == 65533) { if (Character.isSurrogate(c)) { if (this.sgp == null) this.sgp = new Surrogate.Parser();  if (this.sgp.parse(c, arrayOfChar, i, j) < 0) return SingleByte.withResult(this.sgp.error(), param1CharBuffer, i, param1ByteBuffer, k);  return SingleByte.withResult(this.sgp.unmappableResult(), param1CharBuffer, i, param1ByteBuffer, k); }  return SingleByte.withResult(CoderResult.unmappableForLength(1), param1CharBuffer, i, param1ByteBuffer, k); }  arrayOfByte[k++] = (byte)n; i++; }  return SingleByte.withResult(coderResult, param1CharBuffer, i, param1ByteBuffer, k); } protected void implReplaceWith(byte[] param1ArrayOfbyte) { this.repl = param1ArrayOfbyte[0]; }
/*     */     private CoderResult encodeBufferLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { int i = param1CharBuffer.position(); try { while (param1CharBuffer.hasRemaining()) { char c = param1CharBuffer.get(); int j = encode(c); if (j == 65533) { if (Character.isSurrogate(c)) { if (this.sgp == null) this.sgp = new Surrogate.Parser();  if (this.sgp.parse(c, param1CharBuffer) < 0) return this.sgp.error();  return this.sgp.unmappableResult(); }  return CoderResult.unmappableForLength(1); }  if (!param1ByteBuffer.hasRemaining()) return CoderResult.OVERFLOW;  param1ByteBuffer.put((byte)j); i++; }  return CoderResult.UNDERFLOW; } finally { param1CharBuffer.position(i); }  }
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) { if (param1CharBuffer.hasArray() && param1ByteBuffer.hasArray()) return encodeArrayLoop(param1CharBuffer, param1ByteBuffer);  return encodeBufferLoop(param1CharBuffer, param1ByteBuffer); }
/*     */     public final int encode(char param1Char) { char c = this.c2bIndex[param1Char >> 8]; if (c == '�')
/* 237 */         return 65533;  return this.c2b[c + (param1Char & 0xFF)]; } public int encode(char[] param1ArrayOfchar, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) { byte b = 0;
/* 238 */       int i = param1Int1 + Math.min(param1Int2, param1ArrayOfbyte.length);
/* 239 */       while (param1Int1 < i) {
/* 240 */         char c = param1ArrayOfchar[param1Int1++];
/* 241 */         int j = encode(c);
/* 242 */         if (j != 65533) {
/* 243 */           param1ArrayOfbyte[b++] = (byte)j;
/*     */           continue;
/*     */         } 
/* 246 */         if (Character.isHighSurrogate(c) && param1Int1 < i && 
/* 247 */           Character.isLowSurrogate(param1ArrayOfchar[param1Int1])) {
/* 248 */           if (param1Int2 > param1ArrayOfbyte.length) {
/* 249 */             i++;
/* 250 */             param1Int2--;
/*     */           } 
/* 252 */           param1Int1++;
/*     */         } 
/* 254 */         param1ArrayOfbyte[b++] = this.repl;
/*     */       } 
/* 256 */       return b; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initC2B(char[] paramArrayOfchar1, char[] paramArrayOfchar2, char[] paramArrayOfchar3, char[] paramArrayOfchar4) {
/*     */     byte b1;
/* 263 */     for (b1 = 0; b1 < paramArrayOfchar4.length; b1++)
/* 264 */       paramArrayOfchar4[b1] = '�'; 
/* 265 */     for (b1 = 0; b1 < paramArrayOfchar3.length; b1++)
/* 266 */       paramArrayOfchar3[b1] = '�'; 
/* 267 */     b1 = 0; byte b2;
/* 268 */     for (b2 = 0; b2 < paramArrayOfchar1.length; b2++) {
/* 269 */       char c = paramArrayOfchar1[b2];
/* 270 */       if (c != '�') {
/*     */         
/* 272 */         int i = c >> 8;
/* 273 */         if (paramArrayOfchar4[i] == '�') {
/* 274 */           paramArrayOfchar4[i] = (char)b1;
/* 275 */           b1 += 256;
/*     */         } 
/* 277 */         i = paramArrayOfchar4[i] + (c & 0xFF);
/* 278 */         paramArrayOfchar3[i] = (char)((b2 >= '') ? (b2 - 128) : (b2 + 128));
/*     */       } 
/* 280 */     }  if (paramArrayOfchar2 != null) {
/*     */       
/* 282 */       b2 = 0;
/* 283 */       while (b2 < paramArrayOfchar2.length) {
/* 284 */         char c1 = paramArrayOfchar2[b2++];
/* 285 */         char c2 = paramArrayOfchar2[b2++];
/* 286 */         int i = c2 >> 8;
/* 287 */         if (paramArrayOfchar4[i] == '�') {
/* 288 */           paramArrayOfchar4[i] = (char)b1;
/* 289 */           b1 += 256;
/*     */         } 
/* 291 */         i = paramArrayOfchar4[i] + (c2 & 0xFF);
/* 292 */         paramArrayOfchar3[i] = c1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/SingleByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */