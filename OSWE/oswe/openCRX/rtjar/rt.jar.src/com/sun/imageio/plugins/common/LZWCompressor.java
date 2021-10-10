/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LZWCompressor
/*     */ {
/*     */   int codeSize;
/*     */   int clearCode;
/*     */   int endOfInfo;
/*     */   int numBits;
/*     */   int limit;
/*     */   short prefix;
/*     */   BitFile bf;
/*     */   LZWStringTable lzss;
/*     */   boolean tiffFudge;
/*     */   
/*     */   public LZWCompressor(ImageOutputStream paramImageOutputStream, int paramInt, boolean paramBoolean) throws IOException {
/*  73 */     this.bf = new BitFile(paramImageOutputStream, !paramBoolean);
/*  74 */     this.codeSize = paramInt;
/*  75 */     this.tiffFudge = paramBoolean;
/*  76 */     this.clearCode = 1 << paramInt;
/*  77 */     this.endOfInfo = this.clearCode + 1;
/*  78 */     this.numBits = paramInt + 1;
/*     */     
/*  80 */     this.limit = (1 << this.numBits) - 1;
/*  81 */     if (this.tiffFudge) {
/*  82 */       this.limit--;
/*     */     }
/*     */     
/*  85 */     this.prefix = -1;
/*  86 */     this.lzss = new LZWStringTable();
/*  87 */     this.lzss.clearTable(paramInt);
/*  88 */     this.bf.writeBits(this.clearCode, this.numBits);
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
/*     */   public void compress(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 102 */     int j = paramInt1 + paramInt2;
/* 103 */     for (int i = paramInt1; i < j; i++) {
/* 104 */       byte b = paramArrayOfbyte[i]; short s;
/* 105 */       if ((s = this.lzss.findCharString(this.prefix, b)) != -1) {
/* 106 */         this.prefix = s;
/*     */       } else {
/* 108 */         this.bf.writeBits(this.prefix, this.numBits);
/* 109 */         if (this.lzss.addCharString(this.prefix, b) > this.limit) {
/* 110 */           if (this.numBits == 12) {
/* 111 */             this.bf.writeBits(this.clearCode, this.numBits);
/* 112 */             this.lzss.clearTable(this.codeSize);
/* 113 */             this.numBits = this.codeSize + 1;
/*     */           } else {
/* 115 */             this.numBits++;
/*     */           } 
/*     */           
/* 118 */           this.limit = (1 << this.numBits) - 1;
/* 119 */           if (this.tiffFudge) {
/* 120 */             this.limit--;
/*     */           }
/*     */         } 
/* 123 */         this.prefix = (short)((short)b & 0xFF);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 135 */     if (this.prefix != -1) {
/* 136 */       this.bf.writeBits(this.prefix, this.numBits);
/*     */     }
/*     */     
/* 139 */     this.bf.writeBits(this.endOfInfo, this.numBits);
/* 140 */     this.bf.flush();
/*     */   }
/*     */   
/*     */   public void dump(PrintStream paramPrintStream) {
/* 144 */     this.lzss.dump(paramPrintStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/LZWCompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */