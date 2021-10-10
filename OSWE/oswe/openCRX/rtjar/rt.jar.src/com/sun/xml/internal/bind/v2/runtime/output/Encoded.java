/*     */ package com.sun.xml.internal.bind.v2.runtime.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Encoded
/*     */ {
/*     */   public byte[] buf;
/*     */   public int len;
/*     */   
/*     */   public Encoded() {}
/*     */   
/*     */   public Encoded(String text) {
/*  45 */     set(text);
/*     */   }
/*     */   
/*     */   public void ensureSize(int size) {
/*  49 */     if (this.buf == null || this.buf.length < size)
/*  50 */       this.buf = new byte[size]; 
/*     */   }
/*     */   
/*     */   public final void set(String text) {
/*  54 */     int length = text.length();
/*     */     
/*  56 */     ensureSize(length * 3 + 1);
/*     */     
/*  58 */     int ptr = 0;
/*     */     
/*  60 */     for (int i = 0; i < length; i++) {
/*  61 */       char chr = text.charAt(i);
/*  62 */       if (chr > '')
/*  63 */       { if (chr > '߿')
/*  64 */         { if ('?' <= chr && chr <= '?')
/*     */           
/*  66 */           { int uc = ((chr & 0x3FF) << 10 | text.charAt(++i) & 0x3FF) + 65536;
/*     */             
/*  68 */             this.buf[ptr++] = (byte)(0xF0 | uc >> 18);
/*  69 */             this.buf[ptr++] = (byte)(0x80 | uc >> 12 & 0x3F);
/*  70 */             this.buf[ptr++] = (byte)(0x80 | uc >> 6 & 0x3F);
/*  71 */             this.buf[ptr++] = (byte)(128 + (uc & 0x3F)); }
/*     */           else
/*     */           
/*  74 */           { this.buf[ptr++] = (byte)(224 + (chr >> 12));
/*  75 */             this.buf[ptr++] = (byte)(128 + (chr >> 6 & 0x3F));
/*     */ 
/*     */ 
/*     */             
/*  79 */             this.buf[ptr++] = (byte)(128 + (chr & 0x3F)); }  } else { this.buf[ptr++] = (byte)(192 + (chr >> 6)); this.buf[ptr++] = (byte)(128 + (chr & 0x3F)); }
/*     */          }
/*  81 */       else { this.buf[ptr++] = (byte)chr; }
/*     */     
/*     */     } 
/*     */     
/*  85 */     this.len = ptr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setEscape(String text, boolean isAttribute) {
/*  96 */     int length = text.length();
/*  97 */     ensureSize(length * 6 + 1);
/*     */     
/*  99 */     int ptr = 0;
/*     */     
/* 101 */     for (int i = 0; i < length; i++) {
/* 102 */       char chr = text.charAt(i);
/*     */       
/* 104 */       int ptr1 = ptr;
/* 105 */       if (chr > '') {
/* 106 */         if (chr > '߿')
/* 107 */         { if ('?' <= chr && chr <= '?')
/*     */           
/* 109 */           { int uc = ((chr & 0x3FF) << 10 | text.charAt(++i) & 0x3FF) + 65536;
/*     */             
/* 111 */             this.buf[ptr++] = (byte)(0xF0 | uc >> 18);
/* 112 */             this.buf[ptr++] = (byte)(0x80 | uc >> 12 & 0x3F);
/* 113 */             this.buf[ptr++] = (byte)(0x80 | uc >> 6 & 0x3F);
/* 114 */             this.buf[ptr++] = (byte)(128 + (uc & 0x3F)); }
/*     */           else
/*     */           
/* 117 */           { this.buf[ptr1++] = (byte)(224 + (chr >> 12));
/* 118 */             this.buf[ptr1++] = (byte)(128 + (chr >> 6 & 0x3F));
/*     */ 
/*     */ 
/*     */             
/* 122 */             this.buf[ptr1++] = (byte)(128 + (chr & 0x3F)); }  } else { this.buf[ptr1++] = (byte)(192 + (chr >> 6)); this.buf[ptr1++] = (byte)(128 + (chr & 0x3F)); }
/*     */       
/*     */       } else {
/*     */         byte[] ent;
/* 126 */         if ((ent = attributeEntities[chr]) != null)
/*     */         
/*     */         { 
/*     */ 
/*     */           
/* 131 */           if (isAttribute || entities[chr] != null) {
/* 132 */             ptr1 = writeEntity(ent, ptr1);
/*     */           } else {
/* 134 */             this.buf[ptr1++] = (byte)chr;
/*     */           }  }
/* 136 */         else { this.buf[ptr1++] = (byte)chr; }
/*     */         
/* 138 */         ptr = ptr1;
/*     */       } 
/* 140 */     }  this.len = ptr;
/*     */   }
/*     */   
/*     */   private int writeEntity(byte[] entity, int ptr) {
/* 144 */     System.arraycopy(entity, 0, this.buf, ptr, entity.length);
/* 145 */     return ptr + entity.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write(UTF8XmlOutput out) throws IOException {
/* 152 */     out.write(this.buf, 0, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(char b) {
/* 160 */     this.buf[this.len++] = (byte)b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compact() {
/* 168 */     byte[] b = new byte[this.len];
/* 169 */     System.arraycopy(this.buf, 0, b, 0, this.len);
/* 170 */     this.buf = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   private static final byte[][] entities = new byte[128][];
/* 180 */   private static final byte[][] attributeEntities = new byte[128][];
/*     */   
/*     */   static {
/* 183 */     add('&', "&amp;", false);
/* 184 */     add('<', "&lt;", false);
/* 185 */     add('>', "&gt;", false);
/* 186 */     add('"', "&quot;", true);
/* 187 */     add('\t', "&#x9;", true);
/* 188 */     add('\r', "&#xD;", false);
/* 189 */     add('\n', "&#xA;", true);
/*     */   }
/*     */   
/*     */   private static void add(char c, String s, boolean attOnly) {
/* 193 */     byte[] image = UTF8XmlOutput.toBytes(s);
/* 194 */     attributeEntities[c] = image;
/* 195 */     if (!attOnly)
/* 196 */       entities[c] = image; 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/output/Encoded.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */