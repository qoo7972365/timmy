/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
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
/*     */ final class LineInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  46 */   private char[] lineBuffer = null;
/*  47 */   private static int MAX_INCR = 1048576;
/*     */   
/*     */   public LineInputStream(InputStream in) {
/*  50 */     super(in);
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
/*     */   
/*     */   public String readLine() throws IOException {
/*  66 */     char[] buf = this.lineBuffer;
/*     */     
/*  68 */     if (buf == null) {
/*  69 */       buf = this.lineBuffer = new char[128];
/*     */     }
/*     */ 
/*     */     
/*  73 */     int room = buf.length;
/*  74 */     int offset = 0;
/*     */     int c1;
/*  76 */     while ((c1 = this.in.read()) != -1 && 
/*  77 */       c1 != 10) {
/*     */       
/*  79 */       if (c1 == 13) {
/*     */         
/*  81 */         boolean twoCRs = false;
/*  82 */         if (this.in.markSupported()) {
/*  83 */           this.in.mark(2);
/*     */         }
/*  85 */         int c2 = this.in.read();
/*  86 */         if (c2 == 13) {
/*  87 */           twoCRs = true;
/*  88 */           c2 = this.in.read();
/*     */         } 
/*  90 */         if (c2 != 10) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 102 */           if (this.in.markSupported()) {
/* 103 */             this.in.reset(); break;
/*     */           } 
/* 105 */           if (!(this.in instanceof PushbackInputStream)) {
/* 106 */             this.in = new PushbackInputStream(this.in, 2);
/*     */           }
/* 108 */           if (c2 != -1) {
/* 109 */             ((PushbackInputStream)this.in).unread(c2);
/*     */           }
/* 111 */           if (twoCRs) {
/* 112 */             ((PushbackInputStream)this.in).unread(13);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 121 */       if (--room < 0) {
/* 122 */         if (buf.length < MAX_INCR) {
/* 123 */           buf = new char[buf.length * 2];
/*     */         } else {
/* 125 */           buf = new char[buf.length + MAX_INCR];
/*     */         } 
/* 127 */         room = buf.length - offset - 1;
/* 128 */         System.arraycopy(this.lineBuffer, 0, buf, 0, offset);
/* 129 */         this.lineBuffer = buf;
/*     */       } 
/* 131 */       buf[offset++] = (char)c1;
/*     */     } 
/*     */     
/* 134 */     if (c1 == -1 && offset == 0) {
/* 135 */       return null;
/*     */     }
/*     */     
/* 138 */     return String.copyValueOf(buf, 0, offset);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/LineInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */