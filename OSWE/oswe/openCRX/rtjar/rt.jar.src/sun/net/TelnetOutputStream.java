/*     */ package sun.net;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TelnetOutputStream
/*     */   extends BufferedOutputStream
/*     */ {
/*     */   boolean stickyCRLF = false;
/*     */   boolean seenCR = false;
/*     */   public boolean binaryMode = false;
/*     */   
/*     */   public TelnetOutputStream(OutputStream paramOutputStream, boolean paramBoolean) {
/*  79 */     super(paramOutputStream);
/*  80 */     this.binaryMode = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStickyCRLF(boolean paramBoolean) {
/*  90 */     this.stickyCRLF = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/*  97 */     if (this.binaryMode) {
/*  98 */       super.write(paramInt);
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     if (this.seenCR) {
/* 103 */       if (paramInt != 10)
/* 104 */         super.write(0); 
/* 105 */       super.write(paramInt);
/* 106 */       if (paramInt != 13)
/* 107 */         this.seenCR = false; 
/*     */     } else {
/* 109 */       if (paramInt == 10) {
/* 110 */         super.write(13);
/* 111 */         super.write(10);
/*     */         return;
/*     */       } 
/* 114 */       if (paramInt == 13) {
/* 115 */         if (this.stickyCRLF) {
/* 116 */           this.seenCR = true;
/*     */         } else {
/* 118 */           super.write(13);
/* 119 */           paramInt = 0;
/*     */         } 
/*     */       }
/* 122 */       super.write(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 131 */     if (this.binaryMode) {
/* 132 */       super.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     while (--paramInt2 >= 0)
/* 137 */       write(paramArrayOfbyte[paramInt1++]); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/TelnetOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */