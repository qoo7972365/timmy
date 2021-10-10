/*     */ package sun.net;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TelnetInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   boolean stickyCRLF = false;
/*     */   boolean seenCR = false;
/*     */   public boolean binaryMode = false;
/*     */   
/*     */   public TelnetInputStream(InputStream paramInputStream, boolean paramBoolean) {
/*  82 */     super(paramInputStream);
/*  83 */     this.binaryMode = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setStickyCRLF(boolean paramBoolean) {
/*  87 */     this.stickyCRLF = paramBoolean;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  91 */     if (this.binaryMode) {
/*  92 */       return super.read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (this.seenCR) {
/* 102 */       this.seenCR = false;
/* 103 */       return 10;
/*     */     } 
/*     */     int i;
/* 106 */     if ((i = super.read()) == 13) {
/* 107 */       switch (i = super.read()) {
/*     */         
/*     */         default:
/* 110 */           throw new TelnetProtocolException("misplaced CR in input");
/*     */         
/*     */         case 0:
/* 113 */           return 13;
/*     */         case 10:
/*     */           break;
/* 116 */       }  if (this.stickyCRLF) {
/* 117 */         this.seenCR = true;
/* 118 */         return 13;
/*     */       } 
/* 120 */       return 10;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte) throws IOException {
/* 129 */     return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 137 */     if (this.binaryMode) {
/* 138 */       return super.read(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 141 */     int i = paramInt1;
/*     */     
/* 143 */     while (--paramInt2 >= 0) {
/* 144 */       int j = read();
/* 145 */       if (j == -1)
/*     */         break; 
/* 147 */       paramArrayOfbyte[paramInt1++] = (byte)j;
/*     */     } 
/* 149 */     return (paramInt1 > i) ? (paramInt1 - i) : -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/TelnetInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */