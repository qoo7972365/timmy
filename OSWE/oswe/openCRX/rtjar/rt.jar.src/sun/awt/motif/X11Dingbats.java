/*     */ package sun.awt.motif;
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
/*     */ public class X11Dingbats
/*     */   extends Charset
/*     */ {
/*     */   public X11Dingbats() {
/*  34 */     super("X11Dingbats", null);
/*     */   }
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  38 */     return new Encoder(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  45 */     throw new Error("Decoder is not supported by X11Dingbats Charset");
/*     */   }
/*     */   
/*     */   public boolean contains(Charset paramCharset) {
/*  49 */     return paramCharset instanceof X11Dingbats;
/*     */   }
/*     */   
/*     */   private static class Encoder extends CharsetEncoder {
/*     */     public Encoder(Charset param1Charset) {
/*  54 */       super(param1Charset, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */     public boolean canEncode(char param1Char) {
/*  58 */       if (param1Char >= '✁' && param1Char <= '❞') {
/*  59 */         return true;
/*     */       }
/*  61 */       if (param1Char >= '❡' && param1Char <= '➾') {
/*  62 */         return (table[param1Char - 10081] != 0);
/*     */       }
/*  64 */       return false;
/*     */     }
/*     */     
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*  68 */       char[] arrayOfChar = param1CharBuffer.array();
/*  69 */       int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/*  70 */       int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*  71 */       assert i <= j;
/*  72 */       i = (i <= j) ? i : j;
/*  73 */       byte[] arrayOfByte = param1ByteBuffer.array();
/*  74 */       int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/*  75 */       int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*  76 */       assert k <= m;
/*  77 */       k = (k <= m) ? k : m;
/*     */       
/*     */       try {
/*  80 */         while (i < j) {
/*  81 */           char c = arrayOfChar[i];
/*  82 */           if (m - k < 1) {
/*  83 */             return CoderResult.OVERFLOW;
/*     */           }
/*  85 */           if (!canEncode(c))
/*  86 */             return CoderResult.unmappableForLength(1); 
/*  87 */           i++;
/*  88 */           if (c >= '❡') {
/*  89 */             arrayOfByte[k++] = table[c - 10081]; continue;
/*     */           } 
/*  91 */           arrayOfByte[k++] = (byte)(c + 32 - 9984);
/*     */         } 
/*     */         
/*  94 */         return CoderResult.UNDERFLOW;
/*     */       } finally {
/*  96 */         param1CharBuffer.position(i - param1CharBuffer.arrayOffset());
/*  97 */         param1ByteBuffer.position(k - param1ByteBuffer.arrayOffset());
/*     */       } 
/*     */     }
/*     */     
/* 101 */     private static byte[] table = new byte[] { -95, -94, -93, -92, -91, -90, -89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -74, -73, -72, -71, -70, -69, -68, -67, -66, -65, -64, -63, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, -45, -44, 0, 0, 0, -40, -39, -38, -37, -36, -35, -34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) {
/* 129 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/X11Dingbats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */