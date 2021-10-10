/*     */ package sun.awt.motif;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import sun.nio.cs.ext.EUC_TW;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X11CNS11643
/*     */   extends Charset
/*     */ {
/*     */   private final int plane;
/*     */   
/*     */   public X11CNS11643(int paramInt, String paramString) {
/*  36 */     super(paramString, null);
/*  37 */     switch (paramInt) {
/*     */       case 1:
/*  39 */         this.plane = 0;
/*     */         return;
/*     */       case 2:
/*     */       case 3:
/*  43 */         this.plane = paramInt;
/*     */         return;
/*     */     } 
/*  46 */     throw new IllegalArgumentException("Only planes 1, 2, and 3 supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharsetEncoder newEncoder() {
/*  52 */     return new Encoder(this, this.plane);
/*     */   }
/*     */   
/*     */   public CharsetDecoder newDecoder() {
/*  56 */     return new Decoder(this, this.plane);
/*     */   }
/*     */   
/*     */   public boolean contains(Charset paramCharset) {
/*  60 */     return paramCharset instanceof X11CNS11643;
/*     */   }
/*     */   private class Encoder extends EUC_TW.Encoder {
/*     */     private int plane;
/*     */     private byte[] bb;
/*     */     
/*  66 */     public Encoder(Charset param1Charset, int param1Int) { super(param1Charset);
/*     */ 
/*     */ 
/*     */       
/*  70 */       this.bb = new byte[4];
/*     */       this.plane = param1Int; } public boolean canEncode(char param1Char) {
/*  72 */       if (param1Char <= '') {
/*  73 */         return false;
/*     */       }
/*  75 */       int i = toEUC(param1Char, this.bb);
/*  76 */       if (i == -1)
/*  77 */         return false; 
/*  78 */       int j = 0;
/*  79 */       if (i == 4)
/*  80 */         j = (this.bb[1] & 0xFF) - 160; 
/*  81 */       return (j == this.plane);
/*     */     }
/*     */     
/*     */     public boolean isLegalReplacement(byte[] param1ArrayOfbyte) {
/*  85 */       return true;
/*     */     }
/*     */     
/*     */     protected CoderResult encodeLoop(CharBuffer param1CharBuffer, ByteBuffer param1ByteBuffer) {
/*  89 */       char[] arrayOfChar = param1CharBuffer.array();
/*  90 */       int i = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/*  91 */       int j = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*  92 */       byte[] arrayOfByte = param1ByteBuffer.array();
/*  93 */       int k = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/*  94 */       int m = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*     */       
/*     */       try {
/*  97 */         while (i < j) {
/*  98 */           char c = arrayOfChar[i];
/*  99 */           if (c > '' && c < '￾') {
/* 100 */             int n = toEUC(c, this.bb);
/* 101 */             if (n != -1) {
/* 102 */               int i1 = 0;
/* 103 */               if (n == 4)
/* 104 */                 i1 = (this.bb[1] & 0xFF) - 160; 
/* 105 */               if (i1 == this.plane) {
/* 106 */                 if (m - k < 2)
/* 107 */                   return CoderResult.OVERFLOW; 
/* 108 */                 if (n == 2) {
/* 109 */                   arrayOfByte[k++] = (byte)(this.bb[0] & Byte.MAX_VALUE);
/* 110 */                   arrayOfByte[k++] = (byte)(this.bb[1] & Byte.MAX_VALUE);
/*     */                 } else {
/* 112 */                   arrayOfByte[k++] = (byte)(this.bb[2] & Byte.MAX_VALUE);
/* 113 */                   arrayOfByte[k++] = (byte)(this.bb[3] & Byte.MAX_VALUE);
/*     */                 } 
/* 115 */                 i++;
/*     */                 continue;
/*     */               } 
/*     */             } 
/*     */           } 
/* 120 */           return CoderResult.unmappableForLength(1);
/*     */         } 
/* 122 */         return CoderResult.UNDERFLOW;
/*     */       } finally {
/* 124 */         param1CharBuffer.position(i - param1CharBuffer.arrayOffset());
/* 125 */         param1ByteBuffer.position(k - param1ByteBuffer.arrayOffset());
/*     */       } 
/*     */     } }
/*     */   
/*     */   private class Decoder extends EUC_TW.Decoder {
/*     */     int plane;
/*     */     private String table;
/*     */     
/*     */     protected Decoder(Charset param1Charset, int param1Int) {
/* 134 */       super(param1Charset);
/* 135 */       if (param1Int == 0) {
/* 136 */         this.plane = param1Int;
/* 137 */       } else if (param1Int == 2 || param1Int == 3) {
/* 138 */         this.plane = param1Int - 1;
/*     */       } else {
/* 140 */         throw new IllegalArgumentException("Only planes 1, 2, and 3 supported");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected CoderResult decodeLoop(ByteBuffer param1ByteBuffer, CharBuffer param1CharBuffer) {
/* 146 */       byte[] arrayOfByte = param1ByteBuffer.array();
/* 147 */       int i = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/* 148 */       int j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/*     */       
/* 150 */       char[] arrayOfChar = param1CharBuffer.array();
/* 151 */       int k = param1CharBuffer.arrayOffset() + param1CharBuffer.position();
/* 152 */       int m = param1CharBuffer.arrayOffset() + param1CharBuffer.limit();
/*     */       
/*     */       try {
/* 155 */         while (i < j) {
/* 156 */           if (j - i < 2) {
/* 157 */             return CoderResult.UNDERFLOW;
/*     */           }
/* 159 */           int n = arrayOfByte[i] & 0xFF | 0x80;
/* 160 */           int i1 = arrayOfByte[i + 1] & 0xFF | 0x80;
/* 161 */           char[] arrayOfChar1 = toUnicode(n, i1, this.plane);
/*     */ 
/*     */           
/* 164 */           if (arrayOfChar1 == null || arrayOfChar1.length == 2)
/* 165 */             return CoderResult.unmappableForLength(2); 
/* 166 */           if (m - k < 1)
/* 167 */             return CoderResult.OVERFLOW; 
/* 168 */           arrayOfChar[k++] = arrayOfChar1[0];
/* 169 */           i += 2;
/*     */         } 
/* 171 */         return CoderResult.UNDERFLOW;
/*     */       } finally {
/* 173 */         param1ByteBuffer.position(i - param1ByteBuffer.arrayOffset());
/* 174 */         param1CharBuffer.position(k - param1CharBuffer.arrayOffset());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/X11CNS11643.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */