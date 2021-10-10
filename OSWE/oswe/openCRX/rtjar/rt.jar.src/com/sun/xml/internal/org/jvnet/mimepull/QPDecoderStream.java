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
/*     */ final class QPDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*  41 */   private byte[] ba = new byte[2];
/*  42 */   private int spaces = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QPDecoderStream(InputStream in) {
/*  50 */     super(new PushbackInputStream(in, 2));
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
/*     */   
/*     */   public int read() throws IOException {
/*  67 */     if (this.spaces > 0) {
/*     */       
/*  69 */       this.spaces--;
/*  70 */       return 32;
/*     */     } 
/*     */     
/*  73 */     int c = this.in.read();
/*     */     
/*  75 */     if (c == 32) {
/*     */       
/*  77 */       while ((c = this.in.read()) == 32) {
/*  78 */         this.spaces++;
/*     */       }
/*     */       
/*  81 */       if (c == 13 || c == 10 || c == -1) {
/*  82 */         this.spaces = 0;
/*     */       } else {
/*     */         
/*  85 */         ((PushbackInputStream)this.in).unread(c);
/*  86 */         c = 32;
/*     */       } 
/*  88 */       return c;
/*     */     } 
/*  90 */     if (c == 61) {
/*     */       
/*  92 */       int a = this.in.read();
/*     */       
/*  94 */       if (a == 10)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         return read(); } 
/* 101 */       if (a == 13) {
/*     */         
/* 103 */         int b = this.in.read();
/* 104 */         if (b != 10) {
/* 105 */           ((PushbackInputStream)this.in).unread(b);
/*     */         }
/* 107 */         return read();
/* 108 */       }  if (a == -1)
/*     */       {
/* 110 */         return -1;
/*     */       }
/* 112 */       this.ba[0] = (byte)a;
/* 113 */       this.ba[1] = (byte)this.in.read();
/*     */       try {
/* 115 */         return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
/* 116 */       } catch (NumberFormatException nex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 124 */         ((PushbackInputStream)this.in).unread(this.ba);
/* 125 */         return c;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return c;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 149 */     for (i = 0; i < len; i++) {
/* 150 */       int c; if ((c = read()) == -1) {
/* 151 */         if (i == 0) {
/* 152 */           i = -1;
/*     */         }
/*     */         break;
/*     */       } 
/* 156 */       buf[off + i] = (byte)c;
/*     */     } 
/* 158 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 166 */     long skipped = 0L;
/* 167 */     while (n-- > 0L && read() >= 0) {
/* 168 */       skipped++;
/*     */     }
/* 170 */     return skipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 179 */     return false;
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
/*     */   public int available() throws IOException {
/* 193 */     return this.in.available();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/QPDecoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */