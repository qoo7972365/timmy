/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
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
/*     */ abstract class UnicodeDecoder
/*     */   extends CharsetDecoder
/*     */ {
/*     */   protected static final char BYTE_ORDER_MARK = '﻿';
/*     */   protected static final char REVERSED_MARK = '￾';
/*     */   protected static final int NONE = 0;
/*     */   protected static final int BIG = 1;
/*     */   protected static final int LITTLE = 2;
/*     */   private final int expectedByteOrder;
/*     */   private int currentByteOrder;
/*  48 */   private int defaultByteOrder = 1;
/*     */   
/*     */   public UnicodeDecoder(Charset paramCharset, int paramInt) {
/*  51 */     super(paramCharset, 0.5F, 1.0F);
/*  52 */     this.expectedByteOrder = this.currentByteOrder = paramInt;
/*     */   }
/*     */   
/*     */   public UnicodeDecoder(Charset paramCharset, int paramInt1, int paramInt2) {
/*  56 */     this(paramCharset, paramInt1);
/*  57 */     this.defaultByteOrder = paramInt2;
/*     */   }
/*     */   
/*     */   private char decode(int paramInt1, int paramInt2) {
/*  61 */     if (this.currentByteOrder == 1) {
/*  62 */       return (char)(paramInt1 << 8 | paramInt2);
/*     */     }
/*  64 */     return (char)(paramInt2 << 8 | paramInt1);
/*     */   }
/*     */   
/*     */   protected CoderResult decodeLoop(ByteBuffer paramByteBuffer, CharBuffer paramCharBuffer) {
/*  68 */     int i = paramByteBuffer.position();
/*     */     
/*     */     try {
/*  71 */       while (paramByteBuffer.remaining() > 1) {
/*  72 */         int j = paramByteBuffer.get() & 0xFF;
/*  73 */         int k = paramByteBuffer.get() & 0xFF;
/*     */ 
/*     */         
/*  76 */         if (this.currentByteOrder == 0) {
/*  77 */           char c1 = (char)(j << 8 | k);
/*  78 */           if (c1 == '﻿') {
/*  79 */             this.currentByteOrder = 1;
/*  80 */             i += 2; continue;
/*     */           } 
/*  82 */           if (c1 == '￾') {
/*  83 */             this.currentByteOrder = 2;
/*  84 */             i += 2;
/*     */             continue;
/*     */           } 
/*  87 */           this.currentByteOrder = this.defaultByteOrder;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  92 */         char c = decode(j, k);
/*     */         
/*  94 */         if (c == '￾')
/*     */         {
/*  96 */           return CoderResult.malformedForLength(2);
/*     */         }
/*     */ 
/*     */         
/* 100 */         if (Character.isSurrogate(c)) {
/* 101 */           if (Character.isHighSurrogate(c)) {
/* 102 */             if (paramByteBuffer.remaining() < 2)
/* 103 */               return CoderResult.UNDERFLOW; 
/* 104 */             char c1 = decode(paramByteBuffer.get() & 0xFF, paramByteBuffer.get() & 0xFF);
/* 105 */             if (!Character.isLowSurrogate(c1))
/* 106 */               return CoderResult.malformedForLength(4); 
/* 107 */             if (paramCharBuffer.remaining() < 2)
/* 108 */               return CoderResult.OVERFLOW; 
/* 109 */             i += 4;
/* 110 */             paramCharBuffer.put(c);
/* 111 */             paramCharBuffer.put(c1);
/*     */             
/*     */             continue;
/*     */           } 
/* 115 */           return CoderResult.malformedForLength(2);
/*     */         } 
/*     */         
/* 118 */         if (!paramCharBuffer.hasRemaining())
/* 119 */           return CoderResult.OVERFLOW; 
/* 120 */         i += 2;
/* 121 */         paramCharBuffer.put(c);
/*     */       } 
/*     */       
/* 124 */       return CoderResult.UNDERFLOW;
/*     */     } finally {
/*     */       
/* 127 */       paramByteBuffer.position(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void implReset() {
/* 132 */     this.currentByteOrder = this.expectedByteOrder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UnicodeDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */