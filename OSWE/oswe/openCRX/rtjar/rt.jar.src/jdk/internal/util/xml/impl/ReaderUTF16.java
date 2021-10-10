/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderUTF16
/*     */   extends Reader
/*     */ {
/*     */   private InputStream is;
/*     */   private char bo;
/*     */   
/*     */   public ReaderUTF16(InputStream paramInputStream, char paramChar) {
/*  49 */     switch (paramChar) {
/*     */       case 'l':
/*     */       case 'b':
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/*  57 */         throw new IllegalArgumentException("");
/*     */     } 
/*  59 */     this.bo = paramChar;
/*  60 */     this.is = paramInputStream;
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
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/*  72 */     byte b = 0;
/*     */     
/*  74 */     if (this.bo == 'b') {
/*  75 */       while (b < paramInt2) {
/*  76 */         int i; if ((i = this.is.read()) < 0) {
/*  77 */           return (b != 0) ? b : -1;
/*     */         }
/*  79 */         paramArrayOfchar[paramInt1++] = (char)(i << 8 | this.is.read() & 0xFF);
/*  80 */         b++;
/*     */       } 
/*     */     } else {
/*  83 */       while (b < paramInt2) {
/*  84 */         int i; if ((i = this.is.read()) < 0) {
/*  85 */           return (b != 0) ? b : -1;
/*     */         }
/*  87 */         paramArrayOfchar[paramInt1++] = (char)(this.is.read() << 8 | i & 0xFF);
/*  88 */         b++;
/*     */       } 
/*     */     } 
/*  91 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*     */     int i;
/* 103 */     if ((i = this.is.read()) < 0) {
/* 104 */       return -1;
/*     */     }
/* 106 */     if (this.bo == 'b') {
/* 107 */       i = (char)(i << 8 | this.is.read() & 0xFF);
/*     */     } else {
/* 109 */       i = (char)(this.is.read() << 8 | i & 0xFF);
/*     */     } 
/* 111 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 120 */     this.is.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/ReaderUTF16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */