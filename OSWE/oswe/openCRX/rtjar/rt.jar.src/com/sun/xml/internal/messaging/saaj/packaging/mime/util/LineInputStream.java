/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LineInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  51 */   private char[] lineBuffer = null;
/*     */   
/*     */   public LineInputStream(InputStream in) {
/*  54 */     super(in);
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
/*     */   
/*     */   public String readLine() throws IOException {
/*  69 */     InputStream in = this.in;
/*  70 */     char[] buf = this.lineBuffer;
/*     */     
/*  72 */     if (buf == null) {
/*  73 */       buf = this.lineBuffer = new char[128];
/*     */     }
/*     */     
/*  76 */     int room = buf.length;
/*  77 */     int offset = 0;
/*     */     int c1;
/*  79 */     while ((c1 = in.read()) != -1 && 
/*  80 */       c1 != 10) {
/*     */       
/*  82 */       if (c1 == 13) {
/*     */         
/*  84 */         int c2 = in.read();
/*  85 */         if (c2 == 13)
/*  86 */           c2 = in.read(); 
/*  87 */         if (c2 != 10) {
/*     */           
/*  89 */           if (!(in instanceof PushbackInputStream))
/*  90 */             in = this.in = new PushbackInputStream(in); 
/*  91 */           ((PushbackInputStream)in).unread(c2);
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*  98 */       if (--room < 0) {
/*  99 */         buf = new char[offset + 128];
/* 100 */         room = buf.length - offset - 1;
/* 101 */         System.arraycopy(this.lineBuffer, 0, buf, 0, offset);
/* 102 */         this.lineBuffer = buf;
/*     */       } 
/* 104 */       buf[offset++] = (char)c1;
/*     */     } 
/*     */     
/* 107 */     if (c1 == -1 && offset == 0) {
/* 108 */       return null;
/*     */     }
/* 110 */     return String.copyValueOf(buf, 0, offset);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/util/LineInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */