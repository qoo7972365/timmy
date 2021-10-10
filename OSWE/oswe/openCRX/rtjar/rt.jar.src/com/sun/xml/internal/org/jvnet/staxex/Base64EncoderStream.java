/*     */ package com.sun.xml.internal.org.jvnet.staxex;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64EncoderStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private byte[] buffer;
/*  50 */   private int bufsize = 0;
/*     */   
/*     */   private XMLStreamWriter outWriter;
/*     */   
/*     */   public Base64EncoderStream(OutputStream out) {
/*  55 */     super(out);
/*  56 */     this.buffer = new byte[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Base64EncoderStream(XMLStreamWriter outWriter, OutputStream out) {
/*  63 */     super(out);
/*  64 */     this.buffer = new byte[3];
/*  65 */     this.outWriter = outWriter;
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
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  80 */     for (int i = 0; i < len; i++) {
/*  81 */       write(b[off + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  91 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 101 */     this.buffer[this.bufsize++] = (byte)c;
/* 102 */     if (this.bufsize == 3) {
/* 103 */       encode();
/* 104 */       this.bufsize = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 115 */     if (this.bufsize > 0) {
/* 116 */       encode();
/* 117 */       this.bufsize = 0;
/*     */     } 
/* 119 */     this.out.flush();
/*     */     try {
/* 121 */       this.outWriter.flush();
/* 122 */     } catch (XMLStreamException ex) {
/* 123 */       Logger.getLogger(Base64EncoderStream.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 124 */       throw new IOException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 134 */     flush();
/* 135 */     this.out.close();
/*     */   }
/*     */ 
/*     */   
/* 139 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encode() throws IOException {
/* 152 */     char[] buf = new char[4];
/* 153 */     if (this.bufsize == 1) {
/* 154 */       byte a = this.buffer[0];
/* 155 */       byte b = 0;
/* 156 */       byte c = 0;
/* 157 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 158 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 159 */       buf[2] = '=';
/* 160 */       buf[3] = '=';
/* 161 */     } else if (this.bufsize == 2) {
/* 162 */       byte a = this.buffer[0];
/* 163 */       byte b = this.buffer[1];
/* 164 */       byte c = 0;
/* 165 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 166 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 167 */       buf[2] = pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/* 168 */       buf[3] = '=';
/*     */     } else {
/* 170 */       byte a = this.buffer[0];
/* 171 */       byte b = this.buffer[1];
/* 172 */       byte c = this.buffer[2];
/* 173 */       buf[0] = pem_array[a >>> 2 & 0x3F];
/* 174 */       buf[1] = pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/* 175 */       buf[2] = pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/* 176 */       buf[3] = pem_array[c & 0x3F];
/*     */     } 
/*     */     try {
/* 179 */       this.outWriter.writeCharacters(buf, 0, 4);
/* 180 */     } catch (XMLStreamException ex) {
/* 181 */       Logger.getLogger(Base64EncoderStream.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 182 */       throw new IOException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/staxex/Base64EncoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */