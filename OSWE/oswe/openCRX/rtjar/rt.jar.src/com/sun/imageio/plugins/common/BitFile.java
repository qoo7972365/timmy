/*     */ package com.sun.imageio.plugins.common;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class BitFile
/*     */ {
/*     */   ImageOutputStream output;
/*     */   byte[] buffer;
/*     */   int index;
/*     */   int bitsLeft;
/*     */   boolean blocks = false;
/*     */   
/*     */   public BitFile(ImageOutputStream paramImageOutputStream, boolean paramBoolean) {
/*  50 */     this.output = paramImageOutputStream;
/*  51 */     this.blocks = paramBoolean;
/*  52 */     this.buffer = new byte[256];
/*  53 */     this.index = 0;
/*  54 */     this.bitsLeft = 8;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/*  58 */     int i = this.index + ((this.bitsLeft == 8) ? 0 : 1);
/*  59 */     if (i > 0) {
/*  60 */       if (this.blocks) {
/*  61 */         this.output.write(i);
/*     */       }
/*  63 */       this.output.write(this.buffer, 0, i);
/*  64 */       this.buffer[0] = 0;
/*  65 */       this.index = 0;
/*  66 */       this.bitsLeft = 8;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeBits(int paramInt1, int paramInt2) throws IOException {
/*  71 */     int i = 0;
/*  72 */     char c = 'ÿ';
/*     */     
/*     */     do {
/*  75 */       if ((this.index == 254 && this.bitsLeft == 0) || this.index > 254) {
/*  76 */         if (this.blocks) {
/*  77 */           this.output.write(c);
/*     */         }
/*     */         
/*  80 */         this.output.write(this.buffer, 0, c);
/*     */         
/*  82 */         this.buffer[0] = 0;
/*  83 */         this.index = 0;
/*  84 */         this.bitsLeft = 8;
/*     */       } 
/*     */       
/*  87 */       if (paramInt2 <= this.bitsLeft) {
/*  88 */         if (this.blocks) {
/*  89 */           this.buffer[this.index] = (byte)(this.buffer[this.index] | (paramInt1 & (1 << paramInt2) - 1) << 8 - this.bitsLeft);
/*  90 */           i += paramInt2;
/*  91 */           this.bitsLeft -= paramInt2;
/*  92 */           paramInt2 = 0;
/*     */         } else {
/*  94 */           this.buffer[this.index] = (byte)(this.buffer[this.index] | (paramInt1 & (1 << paramInt2) - 1) << this.bitsLeft - paramInt2);
/*  95 */           i += paramInt2;
/*  96 */           this.bitsLeft -= paramInt2;
/*  97 */           paramInt2 = 0;
/*     */         }
/*     */       
/* 100 */       } else if (this.blocks) {
/*     */ 
/*     */         
/* 103 */         this.buffer[this.index] = (byte)(this.buffer[this.index] | (paramInt1 & (1 << this.bitsLeft) - 1) << 8 - this.bitsLeft);
/* 104 */         i += this.bitsLeft;
/* 105 */         paramInt1 >>= this.bitsLeft;
/* 106 */         paramInt2 -= this.bitsLeft;
/* 107 */         this.buffer[++this.index] = 0;
/* 108 */         this.bitsLeft = 8;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 113 */         int j = paramInt1 >>> paramInt2 - this.bitsLeft & (1 << this.bitsLeft) - 1;
/* 114 */         this.buffer[this.index] = (byte)(this.buffer[this.index] | j);
/* 115 */         paramInt2 -= this.bitsLeft;
/* 116 */         i += this.bitsLeft;
/* 117 */         this.buffer[++this.index] = 0;
/* 118 */         this.bitsLeft = 8;
/*     */       }
/*     */     
/* 121 */     } while (paramInt2 != 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/common/BitFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */