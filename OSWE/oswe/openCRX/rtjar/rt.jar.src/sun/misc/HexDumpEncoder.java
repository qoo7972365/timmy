/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HexDumpEncoder
/*     */   extends CharacterEncoder
/*     */ {
/*     */   private int offset;
/*     */   private int thisLineLength;
/*     */   private int currentByte;
/*  51 */   private byte[] thisLine = new byte[16];
/*     */ 
/*     */ 
/*     */   
/*     */   static void hexDigit(PrintStream paramPrintStream, byte paramByte) {
/*  56 */     char c = (char)(paramByte >> 4 & 0xF);
/*  57 */     if (c > '\t') {
/*  58 */       c = (char)(c - 10 + 65);
/*     */     } else {
/*  60 */       c = (char)(c + 48);
/*  61 */     }  paramPrintStream.write(c);
/*  62 */     c = (char)(paramByte & 0xF);
/*  63 */     if (c > '\t') {
/*  64 */       c = (char)(c - 10 + 65);
/*     */     } else {
/*  66 */       c = (char)(c + 48);
/*  67 */     }  paramPrintStream.write(c);
/*     */   }
/*     */   
/*     */   protected int bytesPerAtom() {
/*  71 */     return 1;
/*     */   }
/*     */   
/*     */   protected int bytesPerLine() {
/*  75 */     return 16;
/*     */   }
/*     */   
/*     */   protected void encodeBufferPrefix(OutputStream paramOutputStream) throws IOException {
/*  79 */     this.offset = 0;
/*  80 */     super.encodeBufferPrefix(paramOutputStream);
/*     */   }
/*     */   
/*     */   protected void encodeLinePrefix(OutputStream paramOutputStream, int paramInt) throws IOException {
/*  84 */     hexDigit(this.pStream, (byte)(this.offset >>> 8 & 0xFF));
/*  85 */     hexDigit(this.pStream, (byte)(this.offset & 0xFF));
/*  86 */     this.pStream.print(": ");
/*  87 */     this.currentByte = 0;
/*  88 */     this.thisLineLength = paramInt;
/*     */   }
/*     */   
/*     */   protected void encodeAtom(OutputStream paramOutputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  92 */     this.thisLine[this.currentByte] = paramArrayOfbyte[paramInt1];
/*  93 */     hexDigit(this.pStream, paramArrayOfbyte[paramInt1]);
/*  94 */     this.pStream.print(" ");
/*  95 */     this.currentByte++;
/*  96 */     if (this.currentByte == 8)
/*  97 */       this.pStream.print("  "); 
/*     */   }
/*     */   
/*     */   protected void encodeLineSuffix(OutputStream paramOutputStream) throws IOException {
/* 101 */     if (this.thisLineLength < 16)
/* 102 */       for (int i = this.thisLineLength; i < 16; i++) {
/* 103 */         this.pStream.print("   ");
/* 104 */         if (i == 7) {
/* 105 */           this.pStream.print("  ");
/*     */         }
/*     */       }  
/* 108 */     this.pStream.print(" ");
/* 109 */     for (byte b = 0; b < this.thisLineLength; b++) {
/* 110 */       if (this.thisLine[b] < 32 || this.thisLine[b] > 122) {
/* 111 */         this.pStream.print(".");
/*     */       } else {
/* 113 */         this.pStream.write(this.thisLine[b]);
/*     */       } 
/*     */     } 
/* 116 */     this.pStream.println();
/* 117 */     this.offset += this.thisLineLength;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/HexDumpEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */