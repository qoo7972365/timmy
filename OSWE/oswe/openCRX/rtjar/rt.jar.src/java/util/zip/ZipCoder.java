/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ import sun.nio.cs.ArrayDecoder;
/*     */ import sun.nio.cs.ArrayEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ZipCoder
/*     */ {
/*     */   private Charset cs;
/*     */   private CharsetDecoder dec;
/*     */   private CharsetEncoder enc;
/*     */   private boolean isUTF8;
/*     */   private ZipCoder utf8;
/*     */   
/*     */   String toString(byte[] paramArrayOfbyte, int paramInt) {
/*  47 */     CharsetDecoder charsetDecoder = decoder().reset();
/*  48 */     int i = (int)(paramInt * charsetDecoder.maxCharsPerByte());
/*  49 */     char[] arrayOfChar = new char[i];
/*  50 */     if (i == 0) {
/*  51 */       return new String(arrayOfChar);
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (this.isUTF8 && charsetDecoder instanceof ArrayDecoder) {
/*  56 */       int j = ((ArrayDecoder)charsetDecoder).decode(paramArrayOfbyte, 0, paramInt, arrayOfChar);
/*  57 */       if (j == -1)
/*  58 */         throw new IllegalArgumentException("MALFORMED"); 
/*  59 */       return new String(arrayOfChar, 0, j);
/*     */     } 
/*  61 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte, 0, paramInt);
/*  62 */     CharBuffer charBuffer = CharBuffer.wrap(arrayOfChar);
/*  63 */     CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
/*  64 */     if (!coderResult.isUnderflow())
/*  65 */       throw new IllegalArgumentException(coderResult.toString()); 
/*  66 */     coderResult = charsetDecoder.flush(charBuffer);
/*  67 */     if (!coderResult.isUnderflow())
/*  68 */       throw new IllegalArgumentException(coderResult.toString()); 
/*  69 */     return new String(arrayOfChar, 0, charBuffer.position());
/*     */   }
/*     */   
/*     */   String toString(byte[] paramArrayOfbyte) {
/*  73 */     return toString(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   byte[] getBytes(String paramString) {
/*  77 */     CharsetEncoder charsetEncoder = encoder().reset();
/*  78 */     char[] arrayOfChar = paramString.toCharArray();
/*  79 */     int i = (int)(arrayOfChar.length * charsetEncoder.maxBytesPerChar());
/*  80 */     byte[] arrayOfByte = new byte[i];
/*  81 */     if (i == 0) {
/*  82 */       return arrayOfByte;
/*     */     }
/*     */     
/*  85 */     if (this.isUTF8 && charsetEncoder instanceof ArrayEncoder) {
/*  86 */       int j = ((ArrayEncoder)charsetEncoder).encode(arrayOfChar, 0, arrayOfChar.length, arrayOfByte);
/*  87 */       if (j == -1)
/*  88 */         throw new IllegalArgumentException("MALFORMED"); 
/*  89 */       return Arrays.copyOf(arrayOfByte, j);
/*     */     } 
/*  91 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/*  92 */     CharBuffer charBuffer = CharBuffer.wrap(arrayOfChar);
/*  93 */     CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, true);
/*  94 */     if (!coderResult.isUnderflow())
/*  95 */       throw new IllegalArgumentException(coderResult.toString()); 
/*  96 */     coderResult = charsetEncoder.flush(byteBuffer);
/*  97 */     if (!coderResult.isUnderflow())
/*  98 */       throw new IllegalArgumentException(coderResult.toString()); 
/*  99 */     if (byteBuffer.position() == arrayOfByte.length) {
/* 100 */       return arrayOfByte;
/*     */     }
/* 102 */     return Arrays.copyOf(arrayOfByte, byteBuffer.position());
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] getBytesUTF8(String paramString) {
/* 107 */     if (this.isUTF8)
/* 108 */       return getBytes(paramString); 
/* 109 */     if (this.utf8 == null)
/* 110 */       this.utf8 = new ZipCoder(StandardCharsets.UTF_8); 
/* 111 */     return this.utf8.getBytes(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   String toStringUTF8(byte[] paramArrayOfbyte, int paramInt) {
/* 116 */     if (this.isUTF8)
/* 117 */       return toString(paramArrayOfbyte, paramInt); 
/* 118 */     if (this.utf8 == null)
/* 119 */       this.utf8 = new ZipCoder(StandardCharsets.UTF_8); 
/* 120 */     return this.utf8.toString(paramArrayOfbyte, paramInt);
/*     */   }
/*     */   
/*     */   boolean isUTF8() {
/* 124 */     return this.isUTF8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ZipCoder(Charset paramCharset) {
/* 134 */     this.cs = paramCharset;
/* 135 */     this.isUTF8 = paramCharset.name().equals(StandardCharsets.UTF_8.name());
/*     */   }
/*     */   
/*     */   static ZipCoder get(Charset paramCharset) {
/* 139 */     return new ZipCoder(paramCharset);
/*     */   }
/*     */   
/*     */   private CharsetDecoder decoder() {
/* 143 */     if (this.dec == null) {
/* 144 */       this
/*     */         
/* 146 */         .dec = this.cs.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/*     */     }
/* 148 */     return this.dec;
/*     */   }
/*     */   
/*     */   private CharsetEncoder encoder() {
/* 152 */     if (this.enc == null) {
/* 153 */       this
/*     */         
/* 155 */         .enc = this.cs.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/*     */     }
/* 157 */     return this.enc;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/ZipCoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */