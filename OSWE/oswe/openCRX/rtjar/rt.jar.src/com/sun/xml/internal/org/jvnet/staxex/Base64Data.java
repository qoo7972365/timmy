/*     */ package com.sun.xml.internal.org.jvnet.staxex;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64Data
/*     */   implements CharSequence, Cloneable
/*     */ {
/*     */   private DataHandler dataHandler;
/*     */   private byte[] data;
/*     */   private int dataLen;
/*     */   private boolean dataCloneByRef;
/*     */   private String mimeType;
/*     */   
/*     */   public Base64Data() {}
/*     */   
/*  89 */   private static final Logger logger = Logger.getLogger(Base64Data.class.getName());
/*     */ 
/*     */   
/*     */   private static final int CHUNK_SIZE;
/*     */ 
/*     */   
/*     */   public Base64Data(Base64Data that) {
/*  96 */     that.get();
/*  97 */     if (that.dataCloneByRef) {
/*  98 */       this.data = that.data;
/*     */     } else {
/* 100 */       this.data = new byte[that.dataLen];
/* 101 */       System.arraycopy(that.data, 0, this.data, 0, that.dataLen);
/*     */     } 
/*     */     
/* 104 */     this.dataCloneByRef = true;
/* 105 */     this.dataLen = that.dataLen;
/* 106 */     this.dataHandler = null;
/* 107 */     this.mimeType = that.mimeType;
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
/*     */   public void set(byte[] data, int len, String mimeType, boolean cloneByRef) {
/* 121 */     this.data = data;
/* 122 */     this.dataLen = len;
/* 123 */     this.dataCloneByRef = cloneByRef;
/* 124 */     this.dataHandler = null;
/* 125 */     this.mimeType = mimeType;
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
/*     */   public void set(byte[] data, int len, String mimeType) {
/* 137 */     set(data, len, mimeType, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(byte[] data, String mimeType) {
/* 148 */     set(data, data.length, mimeType, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(DataHandler data) {
/* 157 */     assert data != null;
/* 158 */     this.dataHandler = data;
/* 159 */     this.data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataHandler getDataHandler() {
/* 170 */     if (this.dataHandler == null) {
/* 171 */       this.dataHandler = new Base64StreamingDataHandler(new Base64DataSource());
/* 172 */     } else if (!(this.dataHandler instanceof StreamingDataHandler)) {
/* 173 */       this.dataHandler = new FilterDataHandler(this.dataHandler);
/*     */     } 
/* 175 */     return this.dataHandler;
/*     */   }
/*     */   
/*     */   private final class Base64DataSource implements DataSource {
/*     */     public String getContentType() {
/* 180 */       return Base64Data.this.getMimeType();
/*     */     }
/*     */     private Base64DataSource() {}
/*     */     public InputStream getInputStream() {
/* 184 */       return new ByteArrayInputStream(Base64Data.this.data, 0, Base64Data.this.dataLen);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 188 */       return null;
/*     */     }
/*     */     
/*     */     public OutputStream getOutputStream() {
/* 192 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Base64StreamingDataHandler
/*     */     extends StreamingDataHandler
/*     */   {
/*     */     Base64StreamingDataHandler(DataSource source) {
/* 200 */       super(source);
/*     */     }
/*     */     
/*     */     public InputStream readOnce() throws IOException {
/* 204 */       return getDataSource().getInputStream();
/*     */     }
/*     */     
/*     */     public void moveTo(File dst) throws IOException {
/* 208 */       FileOutputStream fout = new FileOutputStream(dst);
/*     */       try {
/* 210 */         fout.write(Base64Data.this.data, 0, Base64Data.this.dataLen);
/*     */       } finally {
/* 212 */         fout.close();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */   
/*     */   private static final class FilterDataHandler
/*     */     extends StreamingDataHandler
/*     */   {
/*     */     FilterDataHandler(DataHandler dh) {
/* 224 */       super(dh.getDataSource());
/*     */     }
/*     */     
/*     */     public InputStream readOnce() throws IOException {
/* 228 */       return getDataSource().getInputStream();
/*     */     }
/*     */     
/*     */     public void moveTo(File dst) throws IOException {
/* 232 */       byte[] buf = new byte[8192];
/* 233 */       InputStream in = null;
/* 234 */       OutputStream out = null;
/*     */       try {
/* 236 */         in = getDataSource().getInputStream();
/* 237 */         out = new FileOutputStream(dst);
/*     */         while (true) {
/* 239 */           int amountRead = in.read(buf);
/* 240 */           if (amountRead == -1) {
/*     */             break;
/*     */           }
/* 243 */           out.write(buf, 0, amountRead);
/*     */         } 
/*     */       } finally {
/* 246 */         if (in != null) {
/*     */           try {
/* 248 */             in.close();
/* 249 */           } catch (IOException iOException) {}
/*     */         }
/*     */ 
/*     */         
/* 253 */         if (out != null) {
/*     */           try {
/* 255 */             out.close();
/* 256 */           } catch (IOException iOException) {}
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExact() {
/* 274 */     get();
/* 275 */     if (this.dataLen != this.data.length) {
/* 276 */       byte[] buf = new byte[this.dataLen];
/* 277 */       System.arraycopy(this.data, 0, buf, 0, this.dataLen);
/* 278 */       this.data = buf;
/*     */     } 
/* 280 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 290 */     if (this.dataHandler != null) {
/* 291 */       return this.dataHandler.getInputStream();
/*     */     }
/* 293 */     return new ByteArrayInputStream(this.data, 0, this.dataLen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasData() {
/* 304 */     return (this.data != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get() {
/* 313 */     if (this.data == null) {
/*     */       try {
/* 315 */         ByteArrayOutputStreamEx baos = new ByteArrayOutputStreamEx(1024);
/* 316 */         InputStream is = this.dataHandler.getDataSource().getInputStream();
/* 317 */         baos.readFrom(is);
/* 318 */         is.close();
/* 319 */         this.data = baos.getBuffer();
/* 320 */         this.dataLen = baos.size();
/* 321 */         this.dataCloneByRef = true;
/* 322 */       } catch (IOException e) {
/*     */         
/* 324 */         this.dataLen = 0;
/*     */       } 
/*     */     }
/* 327 */     return this.data;
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
/*     */   public int getDataLen() {
/* 341 */     get();
/* 342 */     return this.dataLen;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 346 */     if (this.mimeType == null) {
/* 347 */       return "application/octet-stream";
/*     */     }
/* 349 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 359 */     get();
/* 360 */     return (this.dataLen + 2) / 3 * 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAt(int index) {
/*     */     byte b1;
/* 372 */     int offset = index % 4;
/* 373 */     int base = index / 4 * 3;
/*     */ 
/*     */ 
/*     */     
/* 377 */     switch (offset) {
/*     */       case 0:
/* 379 */         return Base64Encoder.encode(this.data[base] >> 2);
/*     */       case 1:
/* 381 */         if (base + 1 < this.dataLen) {
/* 382 */           b1 = this.data[base + 1];
/*     */         } else {
/* 384 */           b1 = 0;
/*     */         } 
/* 386 */         return Base64Encoder.encode((this.data[base] & 0x3) << 4 | b1 >> 4 & 0xF);
/*     */ 
/*     */       
/*     */       case 2:
/* 390 */         if (base + 1 < this.dataLen) {
/* 391 */           byte b2; b1 = this.data[base + 1];
/* 392 */           if (base + 2 < this.dataLen) {
/* 393 */             b2 = this.data[base + 2];
/*     */           } else {
/* 395 */             b2 = 0;
/*     */           } 
/*     */           
/* 398 */           return Base64Encoder.encode((b1 & 0xF) << 2 | b2 >> 6 & 0x3);
/*     */         } 
/*     */ 
/*     */         
/* 402 */         return '=';
/*     */       
/*     */       case 3:
/* 405 */         if (base + 2 < this.dataLen) {
/* 406 */           return Base64Encoder.encode(this.data[base + 2] & 0x3F);
/*     */         }
/* 408 */         return '=';
/*     */     } 
/*     */ 
/*     */     
/* 412 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence subSequence(int start, int end) {
/* 421 */     StringBuilder buf = new StringBuilder();
/* 422 */     get();
/* 423 */     for (int i = start; i < end; i++) {
/* 424 */       buf.append(charAt(i));
/*     */     }
/* 426 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 434 */     get();
/* 435 */     return Base64Encoder.print(this.data, 0, this.dataLen);
/*     */   }
/*     */   
/*     */   public void writeTo(char[] buf, int start) {
/* 439 */     get();
/* 440 */     Base64Encoder.print(this.data, 0, this.dataLen, buf, start);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 445 */     int bufSize = 1024;
/*     */     try {
/* 447 */       String bufSizeStr = getProperty("com.sun.xml.internal.org.jvnet.staxex.Base64DataStreamWriteBufferSize");
/* 448 */       if (bufSizeStr != null) {
/* 449 */         bufSize = Integer.parseInt(bufSizeStr);
/*     */       }
/* 451 */     } catch (Exception e) {
/* 452 */       logger.log(Level.INFO, "Error reading com.sun.xml.internal.org.jvnet.staxex.Base64DataStreamWriteBufferSize property", e);
/*     */     } 
/* 454 */     CHUNK_SIZE = bufSize;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter output) throws IOException, XMLStreamException {
/* 458 */     if (this.data == null) {
/*     */       try {
/* 460 */         InputStream is = this.dataHandler.getDataSource().getInputStream();
/* 461 */         ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 462 */         Base64EncoderStream encWriter = new Base64EncoderStream(output, outStream);
/*     */         
/* 464 */         byte[] buffer = new byte[CHUNK_SIZE]; int b;
/* 465 */         while ((b = is.read(buffer)) != -1) {
/* 466 */           encWriter.write(buffer, 0, b);
/*     */         }
/* 468 */         outStream.close();
/* 469 */         encWriter.close();
/* 470 */       } catch (IOException e) {
/* 471 */         this.dataLen = 0;
/* 472 */         throw e;
/*     */       } 
/*     */     } else {
/*     */       
/* 476 */       String s = Base64Encoder.print(this.data, 0, this.dataLen);
/* 477 */       output.writeCharacters(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Base64Data clone() {
/*     */     try {
/* 484 */       Base64Data clone = (Base64Data)super.clone();
/* 485 */       clone.get();
/* 486 */       if (clone.dataCloneByRef) {
/* 487 */         this.data = clone.data;
/*     */       } else {
/* 489 */         this.data = new byte[clone.dataLen];
/* 490 */         System.arraycopy(clone.data, 0, this.data, 0, clone.dataLen);
/*     */       } 
/*     */       
/* 493 */       this.dataCloneByRef = true;
/* 494 */       this.dataLen = clone.dataLen;
/* 495 */       this.dataHandler = null;
/* 496 */       this.mimeType = clone.mimeType;
/* 497 */       return clone;
/* 498 */     } catch (CloneNotSupportedException ex) {
/* 499 */       Logger.getLogger(Base64Data.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 500 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static String getProperty(final String propName) {
/* 505 */     if (System.getSecurityManager() == null) {
/* 506 */       return System.getProperty(propName);
/*     */     }
/* 508 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public Object run() {
/* 511 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/staxex/Base64Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */