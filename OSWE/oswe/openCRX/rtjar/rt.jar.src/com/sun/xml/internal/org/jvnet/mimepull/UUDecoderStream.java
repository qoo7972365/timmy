/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
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
/*     */ final class UUDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private String name;
/*     */   private int mode;
/*  45 */   private byte[] buffer = new byte[45];
/*  46 */   private int bufsize = 0;
/*  47 */   private int index = 0;
/*     */ 
/*     */   
/*     */   private boolean gotPrefix = false;
/*     */ 
/*     */   
/*     */   private boolean gotEnd = false;
/*     */ 
/*     */   
/*     */   private LineInputStream lin;
/*     */   
/*     */   private boolean ignoreErrors;
/*     */   
/*     */   private boolean ignoreMissingBeginEnd;
/*     */   
/*     */   private String readAhead;
/*     */ 
/*     */   
/*     */   public UUDecoderStream(InputStream in) {
/*  66 */     super(in);
/*  67 */     this.lin = new LineInputStream(in);
/*     */     
/*  69 */     this.ignoreErrors = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoreerrors", false);
/*     */ 
/*     */     
/*  72 */     this.ignoreMissingBeginEnd = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoremissingbeginend", false);
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
/*     */   public UUDecoderStream(InputStream in, boolean ignoreErrors, boolean ignoreMissingBeginEnd) {
/*  84 */     super(in);
/*  85 */     this.lin = new LineInputStream(in);
/*  86 */     this.ignoreErrors = ignoreErrors;
/*  87 */     this.ignoreMissingBeginEnd = ignoreMissingBeginEnd;
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
/*     */   public int read() throws IOException {
/* 105 */     if (this.index >= this.bufsize) {
/* 106 */       readPrefix();
/* 107 */       if (!decode()) {
/* 108 */         return -1;
/*     */       }
/* 110 */       this.index = 0;
/*     */     } 
/* 112 */     return this.buffer[this.index++] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 118 */     for (i = 0; i < len; i++) {
/* 119 */       int c; if ((c = read()) == -1) {
/* 120 */         if (i == 0) {
/* 121 */           i = -1;
/*     */         }
/*     */         break;
/*     */       } 
/* 125 */       buf[off + i] = (byte)c;
/*     */     } 
/* 127 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 139 */     return this.in.available() * 3 / 4 + this.bufsize - this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() throws IOException {
/* 150 */     readPrefix();
/* 151 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() throws IOException {
/* 162 */     readPrefix();
/* 163 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readPrefix() throws IOException {
/* 172 */     if (this.gotPrefix) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     this.mode = 438;
/* 177 */     this.name = "encoder.buf";
/*     */ 
/*     */     
/*     */     while (true) {
/* 181 */       String line = this.lin.readLine();
/* 182 */       if (line == null) {
/* 183 */         if (!this.ignoreMissingBeginEnd) {
/* 184 */           throw new DecodingException("UUDecoder: Missing begin");
/*     */         }
/*     */         
/* 187 */         this.gotPrefix = true;
/* 188 */         this.gotEnd = true;
/*     */         break;
/*     */       } 
/* 191 */       if (line.regionMatches(false, 0, "begin", 0, 5)) {
/*     */         try {
/* 193 */           this.mode = Integer.parseInt(line.substring(6, 9));
/* 194 */         } catch (NumberFormatException ex) {
/* 195 */           if (!this.ignoreErrors) {
/* 196 */             throw new DecodingException("UUDecoder: Error in mode: " + ex
/* 197 */                 .toString());
/*     */           }
/*     */         } 
/* 200 */         if (line.length() > 10) {
/* 201 */           this.name = line.substring(10);
/*     */         }
/* 203 */         else if (!this.ignoreErrors) {
/* 204 */           throw new DecodingException("UUDecoder: Missing name: " + line);
/*     */         } 
/*     */ 
/*     */         
/* 208 */         this.gotPrefix = true; break;
/*     */       } 
/* 210 */       if (this.ignoreMissingBeginEnd && line.length() != 0) {
/* 211 */         int count = line.charAt(0);
/* 212 */         count = count - 32 & 0x3F;
/* 213 */         int need = (count * 8 + 5) / 6;
/* 214 */         if (need == 0 || line.length() >= need + 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 221 */           this.readAhead = line;
/* 222 */           this.gotPrefix = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean decode() throws IOException {
/*     */     String line;
/* 231 */     if (this.gotEnd) {
/* 232 */       return false;
/*     */     }
/* 234 */     this.bufsize = 0;
/* 235 */     int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 242 */       if (this.readAhead != null) {
/* 243 */         line = this.readAhead;
/* 244 */         this.readAhead = null;
/*     */       } else {
/* 246 */         line = this.lin.readLine();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       if (line == null) {
/* 255 */         if (!this.ignoreMissingBeginEnd) {
/* 256 */           throw new DecodingException("UUDecoder: Missing end at EOF");
/*     */         }
/*     */         
/* 259 */         this.gotEnd = true;
/* 260 */         return false;
/*     */       } 
/* 262 */       if (line.equals("end")) {
/* 263 */         this.gotEnd = true;
/* 264 */         return false;
/*     */       } 
/* 266 */       if (line.length() == 0) {
/*     */         continue;
/*     */       }
/* 269 */       count = line.charAt(0);
/* 270 */       if (count < 32) {
/* 271 */         if (!this.ignoreErrors) {
/* 272 */           throw new DecodingException("UUDecoder: Buffer format error");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       count = count - 32 & 0x3F;
/*     */       
/* 286 */       if (count == 0) {
/* 287 */         line = this.lin.readLine();
/* 288 */         if ((line == null || !line.equals("end")) && 
/* 289 */           !this.ignoreMissingBeginEnd) {
/* 290 */           throw new DecodingException("UUDecoder: Missing End after count 0 line");
/*     */         }
/*     */ 
/*     */         
/* 294 */         this.gotEnd = true;
/* 295 */         return false;
/*     */       } 
/*     */       
/* 298 */       int need = (count * 8 + 5) / 6;
/*     */       
/* 300 */       if (line.length() < need + 1) {
/* 301 */         if (!this.ignoreErrors) {
/* 302 */           throw new DecodingException("UUDecoder: Short buffer error");
/*     */         }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/*     */     
/* 312 */     int i = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     while (this.bufsize < count) {
/*     */       
/* 322 */       byte a = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 323 */       byte b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 324 */       this.buffer[this.bufsize++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */       
/* 326 */       if (this.bufsize < count) {
/* 327 */         a = b;
/* 328 */         b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 329 */         this.buffer[this.bufsize++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */       } 
/*     */ 
/*     */       
/* 333 */       if (this.bufsize < count) {
/* 334 */         a = b;
/* 335 */         b = (byte)(line.charAt(i++) - 32 & 0x3F);
/* 336 */         this.buffer[this.bufsize++] = (byte)(a << 6 & 0xC0 | b & 0x3F);
/*     */       } 
/*     */     } 
/* 339 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/UUDecoderStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */