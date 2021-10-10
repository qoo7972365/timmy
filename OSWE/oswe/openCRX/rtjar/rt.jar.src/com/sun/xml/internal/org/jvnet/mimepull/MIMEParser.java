/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MIMEParser
/*     */   implements Iterable<MIMEEvent>
/*     */ {
/*  55 */   private static final Logger LOGGER = Logger.getLogger(MIMEParser.class.getName());
/*     */   
/*     */   private static final String HEADER_ENCODING = "ISO8859-1";
/*     */   
/*     */   private static final int NO_LWSP = 1000;
/*     */   
/*     */   private enum STATE
/*     */   {
/*  63 */     START_MESSAGE, SKIP_PREAMBLE, START_PART, HEADERS, BODY, END_PART, END_MESSAGE; }
/*  64 */   private STATE state = STATE.START_MESSAGE;
/*     */   
/*     */   private final InputStream in;
/*     */   private final byte[] bndbytes;
/*     */   private final int bl;
/*     */   private final MIMEConfig config;
/*  70 */   private final int[] bcs = new int[128];
/*     */ 
/*     */   
/*     */   private final int[] gss;
/*     */ 
/*     */   
/*     */   private boolean parsed;
/*     */ 
/*     */   
/*     */   private boolean done = false;
/*     */ 
/*     */   
/*     */   private boolean eof;
/*     */   
/*     */   private final int capacity;
/*     */   
/*     */   private byte[] buf;
/*     */   
/*     */   private int len;
/*     */   
/*     */   private boolean bol;
/*     */ 
/*     */   
/*     */   MIMEParser(InputStream in, String boundary, MIMEConfig config) {
/*  94 */     this.in = in;
/*  95 */     this.bndbytes = getBytes("--" + boundary);
/*  96 */     this.bl = this.bndbytes.length;
/*  97 */     this.config = config;
/*  98 */     this.gss = new int[this.bl];
/*  99 */     compileBoundaryPattern();
/*     */ 
/*     */     
/* 102 */     this.capacity = config.chunkSize + 2 + this.bl + 4 + 1000;
/* 103 */     createBuf(this.capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<MIMEEvent> iterator() {
/* 114 */     return new MIMEEventIterator();
/*     */   }
/*     */   
/*     */   class MIMEEventIterator
/*     */     implements Iterator<MIMEEvent>
/*     */   {
/*     */     public boolean hasNext() {
/* 121 */       return !MIMEParser.this.parsed;
/*     */     }
/*     */     public MIMEEvent next() {
/*     */       InternetHeaders ih;
/*     */       ByteBuffer buf;
/* 126 */       switch (MIMEParser.this.state) {
/*     */         case START_MESSAGE:
/* 128 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.START_MESSAGE); 
/* 129 */           MIMEParser.this.state = MIMEParser.STATE.SKIP_PREAMBLE;
/* 130 */           return MIMEEvent.START_MESSAGE;
/*     */         
/*     */         case SKIP_PREAMBLE:
/* 133 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.SKIP_PREAMBLE); 
/* 134 */           MIMEParser.this.skipPreamble();
/*     */         
/*     */         case START_PART:
/* 137 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.START_PART); 
/* 138 */           MIMEParser.this.state = MIMEParser.STATE.HEADERS;
/* 139 */           return MIMEEvent.START_PART;
/*     */         
/*     */         case HEADERS:
/* 142 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.HEADERS); 
/* 143 */           ih = MIMEParser.this.readHeaders();
/* 144 */           MIMEParser.this.state = MIMEParser.STATE.BODY;
/* 145 */           MIMEParser.this.bol = true;
/* 146 */           return new MIMEEvent.Headers(ih);
/*     */         
/*     */         case BODY:
/* 149 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.BODY); 
/* 150 */           buf = MIMEParser.this.readBody();
/* 151 */           MIMEParser.this.bol = false;
/* 152 */           return new MIMEEvent.Content(buf);
/*     */         
/*     */         case END_PART:
/* 155 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.END_PART); 
/* 156 */           if (MIMEParser.this.done) {
/* 157 */             MIMEParser.this.state = MIMEParser.STATE.END_MESSAGE;
/*     */           } else {
/* 159 */             MIMEParser.this.state = MIMEParser.STATE.START_PART;
/*     */           } 
/* 161 */           return MIMEEvent.END_PART;
/*     */         
/*     */         case END_MESSAGE:
/* 164 */           if (MIMEParser.LOGGER.isLoggable(Level.FINER)) MIMEParser.LOGGER.log(Level.FINER, "MIMEParser state={0}", MIMEParser.STATE.END_MESSAGE); 
/* 165 */           MIMEParser.this.parsed = true;
/* 166 */           return MIMEEvent.END_MESSAGE;
/*     */       } 
/*     */       
/* 169 */       throw new MIMEParsingException("Unknown Parser state = " + MIMEParser.this.state);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/* 175 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InternetHeaders readHeaders() {
/* 185 */     if (!this.eof) {
/* 186 */       fillBuf();
/*     */     }
/* 188 */     return new InternetHeaders(new LineInputStream());
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
/*     */   private ByteBuffer readBody() {
/* 200 */     if (!this.eof) {
/* 201 */       fillBuf();
/*     */     }
/* 203 */     int start = match(this.buf, 0, this.len);
/* 204 */     if (start == -1) {
/*     */       
/* 206 */       assert this.eof || this.len >= this.config.chunkSize;
/* 207 */       int chunkSize = this.eof ? this.len : this.config.chunkSize;
/* 208 */       if (this.eof) {
/* 209 */         this.done = true;
/* 210 */         throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */       } 
/* 212 */       return adjustBuf(chunkSize, this.len - chunkSize);
/*     */     } 
/*     */ 
/*     */     
/* 216 */     int chunkLen = start;
/* 217 */     if (!this.bol || start != 0)
/*     */     {
/* 219 */       if (start > 0 && (this.buf[start - 1] == 10 || this.buf[start - 1] == 13)) {
/* 220 */         chunkLen--;
/* 221 */         if (this.buf[start - 1] == 10 && start > 1 && this.buf[start - 2] == 13) {
/* 222 */           chunkLen--;
/*     */         }
/*     */       } else {
/* 225 */         return adjustBuf(start + 1, this.len - start - 1);
/*     */       } 
/*     */     }
/* 228 */     if (start + this.bl + 1 < this.len && this.buf[start + this.bl] == 45 && this.buf[start + this.bl + 1] == 45) {
/* 229 */       this.state = STATE.END_PART;
/* 230 */       this.done = true;
/* 231 */       return adjustBuf(chunkLen, 0);
/*     */     } 
/*     */ 
/*     */     
/* 235 */     int lwsp = 0;
/* 236 */     for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 237 */       lwsp++;
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (start + this.bl + lwsp < this.len && this.buf[start + this.bl + lwsp] == 10) {
/* 242 */       this.state = STATE.END_PART;
/* 243 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 1);
/* 244 */     }  if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp] == 13 && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 245 */       this.state = STATE.END_PART;
/* 246 */       return adjustBuf(chunkLen, this.len - start - this.bl - lwsp - 2);
/* 247 */     }  if (start + this.bl + lwsp + 1 < this.len)
/* 248 */       return adjustBuf(chunkLen + 1, this.len - chunkLen - 1); 
/* 249 */     if (this.eof) {
/* 250 */       this.done = true;
/* 251 */       throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return adjustBuf(chunkLen, this.len - chunkLen);
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
/*     */   private ByteBuffer adjustBuf(int chunkSize, int remaining) {
/* 268 */     assert this.buf != null;
/* 269 */     assert chunkSize >= 0;
/* 270 */     assert remaining >= 0;
/*     */     
/* 272 */     byte[] temp = this.buf;
/*     */     
/* 274 */     createBuf(remaining);
/* 275 */     System.arraycopy(temp, this.len - remaining, this.buf, 0, remaining);
/* 276 */     this.len = remaining;
/*     */     
/* 278 */     return ByteBuffer.wrap(temp, 0, chunkSize);
/*     */   }
/*     */   
/*     */   private void createBuf(int min) {
/* 282 */     this.buf = new byte[(min < this.capacity) ? this.capacity : min];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void skipPreamble() {
/*     */     while (true) {
/* 291 */       if (!this.eof) {
/* 292 */         fillBuf();
/*     */       }
/* 294 */       int start = match(this.buf, 0, this.len);
/* 295 */       if (start == -1) {
/*     */         
/* 297 */         if (this.eof) {
/* 298 */           throw new MIMEParsingException("Missing start boundary");
/*     */         }
/* 300 */         adjustBuf(this.len - this.bl + 1, this.bl - 1);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 305 */       if (start > this.config.chunkSize) {
/* 306 */         adjustBuf(start, this.len - start);
/*     */         
/*     */         continue;
/*     */       } 
/* 310 */       int lwsp = 0;
/* 311 */       for (int i = start + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); i++) {
/* 312 */         lwsp++;
/*     */       }
/*     */       
/* 315 */       if (start + this.bl + lwsp < this.len && (this.buf[start + this.bl + lwsp] == 10 || this.buf[start + this.bl + lwsp] == 13)) {
/* 316 */         if (this.buf[start + this.bl + lwsp] == 10) {
/* 317 */           adjustBuf(start + this.bl + lwsp + 1, this.len - start - this.bl - lwsp - 1); break;
/*     */         } 
/* 319 */         if (start + this.bl + lwsp + 1 < this.len && this.buf[start + this.bl + lwsp + 1] == 10) {
/* 320 */           adjustBuf(start + this.bl + lwsp + 2, this.len - start - this.bl - lwsp - 2);
/*     */           break;
/*     */         } 
/*     */       } 
/* 324 */       adjustBuf(start + 1, this.len - start - 1);
/*     */     } 
/* 326 */     if (LOGGER.isLoggable(Level.FINE)) LOGGER.log(Level.FINE, "Skipped the preamble. buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private static byte[] getBytes(String s) {
/* 330 */     char[] chars = s.toCharArray();
/* 331 */     int size = chars.length;
/* 332 */     byte[] bytes = new byte[size];
/*     */     
/* 334 */     for (int i = 0; i < size;) {
/* 335 */       bytes[i] = (byte)chars[i++];
/*     */     }
/* 337 */     return bytes;
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
/*     */   private void compileBoundaryPattern() {
/*     */     int i;
/* 355 */     for (i = 0; i < this.bndbytes.length; i++) {
/* 356 */       this.bcs[this.bndbytes[i] & Byte.MAX_VALUE] = i + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 361 */     for (i = this.bndbytes.length; i > 0; i--) {
/*     */       
/* 363 */       int j = this.bndbytes.length - 1; while (true) { if (j >= i) {
/*     */           
/* 365 */           if (this.bndbytes[j] == this.bndbytes[j - i]) {
/*     */             
/* 367 */             this.gss[j - 1] = i;
/*     */ 
/*     */             
/*     */             j--;
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 377 */         while (j > 0)
/* 378 */           this.gss[--j] = i; 
/*     */         break; }
/*     */     
/*     */     } 
/* 382 */     this.gss[this.bndbytes.length - 1] = 1;
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
/*     */   private int match(byte[] mybuf, int off, int len) {
/* 396 */     int last = len - this.bndbytes.length;
/*     */ 
/*     */     
/* 399 */     label15: while (off <= last) {
/*     */       
/* 401 */       for (int j = this.bndbytes.length - 1; j >= 0; j--) {
/* 402 */         byte ch = mybuf[off + j];
/* 403 */         if (ch != this.bndbytes[j]) {
/*     */ 
/*     */           
/* 406 */           off += Math.max(j + 1 - this.bcs[ch & Byte.MAX_VALUE], this.gss[j]);
/*     */           
/*     */           continue label15;
/*     */         } 
/*     */       } 
/* 411 */       return off;
/*     */     } 
/* 413 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillBuf() {
/* 420 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "Before fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/* 421 */     assert !this.eof;
/* 422 */     while (this.len < this.buf.length) {
/*     */       int read;
/*     */       try {
/* 425 */         read = this.in.read(this.buf, this.len, this.buf.length - this.len);
/* 426 */       } catch (IOException ioe) {
/* 427 */         throw new MIMEParsingException(ioe);
/*     */       } 
/* 429 */       if (read == -1) {
/* 430 */         this.eof = true;
/*     */         try {
/* 432 */           if (LOGGER.isLoggable(Level.FINE)) LOGGER.fine("Closing the input stream."); 
/* 433 */           this.in.close();
/* 434 */         } catch (IOException ioe) {
/* 435 */           throw new MIMEParsingException(ioe);
/*     */         } 
/*     */         break;
/*     */       } 
/* 439 */       this.len += read;
/*     */     } 
/*     */     
/* 442 */     if (LOGGER.isLoggable(Level.FINER)) LOGGER.log(Level.FINER, "After fillBuf() buffer len={0}", Integer.valueOf(this.len)); 
/*     */   }
/*     */   
/*     */   private void doubleBuf() {
/* 446 */     byte[] temp = new byte[2 * this.len];
/* 447 */     System.arraycopy(this.buf, 0, temp, 0, this.len);
/* 448 */     this.buf = temp;
/* 449 */     if (!this.eof) {
/* 450 */       fillBuf();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LineInputStream
/*     */   {
/*     */     private int offset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String readLine() throws IOException {
/* 470 */       int hdrLen = 0;
/* 471 */       int lwsp = 0;
/* 472 */       while (this.offset + hdrLen < MIMEParser.this.len) {
/* 473 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 10) {
/* 474 */           lwsp = 1;
/*     */           break;
/*     */         } 
/* 477 */         if (this.offset + hdrLen + 1 == MIMEParser.this.len) {
/* 478 */           MIMEParser.this.doubleBuf();
/*     */         }
/* 480 */         if (this.offset + hdrLen + 1 >= MIMEParser.this.len) {
/* 481 */           assert MIMEParser.this.eof;
/* 482 */           return null;
/*     */         } 
/* 484 */         if (MIMEParser.this.buf[this.offset + hdrLen] == 13 && MIMEParser.this.buf[this.offset + hdrLen + 1] == 10) {
/* 485 */           lwsp = 2;
/*     */           break;
/*     */         } 
/* 488 */         hdrLen++;
/*     */       } 
/* 490 */       if (hdrLen == 0) {
/* 491 */         MIMEParser.this.adjustBuf(this.offset + lwsp, MIMEParser.this.len - this.offset - lwsp);
/* 492 */         return null;
/*     */       } 
/*     */       
/* 495 */       String hdr = new String(MIMEParser.this.buf, this.offset, hdrLen, "ISO8859-1");
/* 496 */       this.offset += hdrLen + lwsp;
/* 497 */       return hdr;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MIMEParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */