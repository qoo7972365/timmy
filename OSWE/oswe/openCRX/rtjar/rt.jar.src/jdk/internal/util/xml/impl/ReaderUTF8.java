/*     */ package jdk.internal.util.xml.impl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderUTF8
/*     */   extends Reader
/*     */ {
/*     */   private InputStream is;
/*     */   
/*     */   public ReaderUTF8(InputStream paramInputStream) {
/*  55 */     this.is = paramInputStream;
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
/*     */   public int read(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/*  68 */     byte b = 0;
/*     */     
/*  70 */     while (b < paramInt2) {
/*  71 */       int i; if ((i = this.is.read()) < 0) {
/*  72 */         return (b != 0) ? b : -1;
/*     */       }
/*  74 */       switch (i & 0xF0) {
/*     */         case 192:
/*     */         case 208:
/*  77 */           paramArrayOfchar[paramInt1++] = (char)((i & 0x1F) << 6 | this.is.read() & 0x3F);
/*     */           break;
/*     */         
/*     */         case 224:
/*  81 */           paramArrayOfchar[paramInt1++] = 
/*  82 */             (char)((i & 0xF) << 12 | (this.is.read() & 0x3F) << 6 | this.is.read() & 0x3F);
/*     */           break;
/*     */         
/*     */         case 240:
/*  86 */           throw new UnsupportedEncodingException("UTF-32 (or UCS-4) encoding not supported.");
/*     */         
/*     */         default:
/*  89 */           paramArrayOfchar[paramInt1++] = (char)i;
/*     */           break;
/*     */       } 
/*  92 */       b++;
/*     */     } 
/*  94 */     return b;
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
/*     */   public int read() throws IOException {
/*     */     int i;
/* 107 */     if ((i = this.is.read()) < 0) {
/* 108 */       return -1;
/*     */     }
/* 110 */     switch (i & 0xF0) {
/*     */       case 192:
/*     */       case 208:
/* 113 */         i = (i & 0x1F) << 6 | this.is.read() & 0x3F;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 224:
/* 118 */         i = (i & 0xF) << 12 | (this.is.read() & 0x3F) << 6 | this.is.read() & 0x3F;
/*     */         break;
/*     */       
/*     */       case 240:
/* 122 */         throw new UnsupportedEncodingException();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 136 */     this.is.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/impl/ReaderUTF8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */