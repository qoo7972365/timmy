/*     */ package com.sun.xml.internal.messaging.saaj.packaging.mime.util;
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
/*     */ public class BASE64DecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private byte[] buffer;
/*  48 */   private int bufsize = 0;
/*  49 */   public int read() throws IOException { if (this.index >= this.bufsize) { decode(); if (this.bufsize == 0) return -1;  this.index = 0; }  return this.buffer[this.index++] & 0xFF; } private int index = 0;
/*     */   public int read(byte[] buf, int off, int len) throws IOException { int i; for (i = 0; i < len; i++) {
/*     */       int c; if ((c = read()) == -1) {
/*     */         if (i == 0)
/*     */           i = -1;  break;
/*     */       }  buf[off + i] = (byte)c;
/*     */     } 
/*  56 */     return i; } public BASE64DecoderStream(InputStream in) { super(in);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     this.decode_buffer = new byte[4]; this.buffer = new byte[3]; } public boolean markSupported() { return false; }
/*     */   public int available() throws IOException { return this.in.available() * 3 / 4 + this.bufsize - this.index; }
/* 158 */   private void decode() throws IOException { this.bufsize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     int got = 0;
/* 165 */     while (got < 4) {
/* 166 */       int i = this.in.read();
/* 167 */       if (i == -1) {
/* 168 */         if (got == 0)
/*     */           return; 
/* 170 */         throw new IOException("Error in encoded stream, got " + got);
/*     */       } 
/* 172 */       if ((i >= 0 && i < 256 && i == 61) || pem_convert_array[i] != -1) {
/* 173 */         this.decode_buffer[got++] = (byte)i;
/*     */       }
/*     */     } 
/*     */     
/* 177 */     byte a = pem_convert_array[this.decode_buffer[0] & 0xFF];
/* 178 */     byte b = pem_convert_array[this.decode_buffer[1] & 0xFF];
/*     */     
/* 180 */     this.buffer[this.bufsize++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */     
/* 182 */     if (this.decode_buffer[2] == 61)
/*     */       return; 
/* 184 */     a = b;
/* 185 */     b = pem_convert_array[this.decode_buffer[2] & 0xFF];
/*     */     
/* 187 */     this.buffer[this.bufsize++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */     
/* 189 */     if (this.decode_buffer[3] == 61)
/*     */       return; 
/* 191 */     a = b;
/* 192 */     b = pem_convert_array[this.decode_buffer[3] & 0xFF];
/*     */     
/* 194 */     this.buffer[this.bufsize++] = (byte)(a << 6 & 0xC0 | b & 0x3F); } private static final char[] pem_array = new char[] { 
/*     */       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
/*     */       'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
/*     */       'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
/*     */       'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
/*     */       'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
/*     */       'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
/*     */       '8', '9', '+', '/' }; private static final byte[] pem_convert_array = new byte[256]; private byte[] decode_buffer; static { int i;
/*     */     for (i = 0; i < 255; i++)
/*     */       pem_convert_array[i] = -1; 
/*     */     for (i = 0; i < pem_array.length; i++)
/*     */       pem_convert_array[pem_array[i]] = (byte)i;  }
/*     */   public static byte[] decode(byte[] inbuf) {
/* 207 */     int size = inbuf.length / 4 * 3;
/* 208 */     if (size == 0) {
/* 209 */       return inbuf;
/*     */     }
/* 211 */     if (inbuf[inbuf.length - 1] == 61) {
/* 212 */       size--;
/* 213 */       if (inbuf[inbuf.length - 2] == 61)
/* 214 */         size--; 
/*     */     } 
/* 216 */     byte[] outbuf = new byte[size];
/*     */     
/* 218 */     int inpos = 0, outpos = 0;
/* 219 */     size = inbuf.length;
/* 220 */     while (size > 0) {
/*     */       
/* 222 */       byte a = pem_convert_array[inbuf[inpos++] & 0xFF];
/* 223 */       byte b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 225 */       outbuf[outpos++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */       
/* 227 */       if (inbuf[inpos] == 61)
/* 228 */         return outbuf; 
/* 229 */       a = b;
/* 230 */       b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 232 */       outbuf[outpos++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */       
/* 234 */       if (inbuf[inpos] == 61)
/* 235 */         return outbuf; 
/* 236 */       a = b;
/* 237 */       b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 239 */       outbuf[outpos++] = (byte)(a << 6 & 0xC0 | b & 0x3F);
/* 240 */       size -= 4;
/*     */     } 
/* 242 */     return outbuf;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/packaging/mime/util/BASE64DecoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */