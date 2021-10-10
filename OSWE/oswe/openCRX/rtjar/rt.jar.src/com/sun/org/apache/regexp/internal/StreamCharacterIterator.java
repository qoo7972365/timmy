/*     */ package com.sun.org.apache.regexp.internal;
/*     */ 
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
/*     */ public final class StreamCharacterIterator
/*     */   implements CharacterIterator
/*     */ {
/*     */   private final InputStream is;
/*     */   private final StringBuffer buff;
/*     */   private boolean closed;
/*     */   
/*     */   public StreamCharacterIterator(InputStream is) {
/*  45 */     this.is = is;
/*  46 */     this.buff = new StringBuffer(512);
/*  47 */     this.closed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String substring(int beginIndex, int endIndex) {
/*     */     try {
/*  55 */       ensure(endIndex);
/*  56 */       return this.buff.toString().substring(beginIndex, endIndex);
/*     */     }
/*  58 */     catch (IOException e) {
/*     */       
/*  60 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String substring(int beginIndex) {
/*     */     try {
/*  69 */       readAll();
/*  70 */       return this.buff.toString().substring(beginIndex);
/*     */     }
/*  72 */     catch (IOException e) {
/*     */       
/*  74 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAt(int pos) {
/*     */     try {
/*  84 */       ensure(pos);
/*  85 */       return this.buff.charAt(pos);
/*     */     }
/*  87 */     catch (IOException e) {
/*     */       
/*  89 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnd(int pos) {
/*  96 */     if (this.buff.length() > pos)
/*     */     {
/*  98 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       ensure(pos);
/* 105 */       return (this.buff.length() <= pos);
/*     */     }
/* 107 */     catch (IOException e) {
/*     */       
/* 109 */       throw new StringIndexOutOfBoundsException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int read(int n) throws IOException {
/* 117 */     if (this.closed)
/*     */     {
/* 119 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 123 */     int i = n;
/* 124 */     while (--i >= 0) {
/*     */       
/* 126 */       int c = this.is.read();
/* 127 */       if (c < 0) {
/*     */         
/* 129 */         this.closed = true;
/*     */         break;
/*     */       } 
/* 132 */       this.buff.append((char)c);
/*     */     } 
/* 134 */     return n - i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readAll() throws IOException {
/* 140 */     while (!this.closed)
/*     */     {
/* 142 */       read(1000);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensure(int idx) throws IOException {
/* 149 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 154 */     if (idx < this.buff.length()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 159 */     read(idx + 1 - this.buff.length());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/regexp/internal/StreamCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */