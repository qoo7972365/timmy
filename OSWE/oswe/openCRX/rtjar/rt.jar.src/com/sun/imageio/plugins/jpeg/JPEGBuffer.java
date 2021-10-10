/*     */ package com.sun.imageio.plugins.jpeg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JPEGBuffer
/*     */ {
/*     */   private boolean debug = false;
/*  45 */   final int BUFFER_SIZE = 4096;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int bufAvail;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int bufPtr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ImageInputStream iis;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JPEGBuffer(ImageInputStream paramImageInputStream) {
/*  71 */     this.buf = new byte[4096];
/*  72 */     this.bufAvail = 0;
/*  73 */     this.bufPtr = 0;
/*  74 */     this.iis = paramImageInputStream;
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
/*     */   void loadBuf(int paramInt) throws IOException {
/*  87 */     if (this.debug) {
/*  88 */       System.out.print("loadbuf called with ");
/*  89 */       System.out.print("count " + paramInt + ", ");
/*  90 */       System.out.println("bufAvail " + this.bufAvail + ", ");
/*     */     } 
/*  92 */     if (paramInt != 0) {
/*  93 */       if (this.bufAvail >= paramInt) {
/*     */         return;
/*     */       }
/*     */     }
/*  97 */     else if (this.bufAvail == 4096) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (this.bufAvail > 0 && this.bufAvail < 4096) {
/* 103 */       System.arraycopy(this.buf, this.bufPtr, this.buf, 0, this.bufAvail);
/*     */     }
/*     */     
/* 106 */     int i = this.iis.read(this.buf, this.bufAvail, this.buf.length - this.bufAvail);
/* 107 */     if (this.debug) {
/* 108 */       System.out.println("iis.read returned " + i);
/*     */     }
/* 110 */     if (i != -1) {
/* 111 */       this.bufAvail += i;
/*     */     }
/* 113 */     this.bufPtr = 0;
/* 114 */     int j = Math.min(4096, paramInt);
/* 115 */     if (this.bufAvail < j) {
/* 116 */       throw new IIOException("Image Format Error");
/*     */     }
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
/*     */   void readData(byte[] paramArrayOfbyte) throws IOException {
/* 129 */     int i = paramArrayOfbyte.length;
/*     */     
/* 131 */     if (this.bufAvail >= i) {
/* 132 */       System.arraycopy(this.buf, this.bufPtr, paramArrayOfbyte, 0, i);
/* 133 */       this.bufAvail -= i;
/* 134 */       this.bufPtr += i;
/*     */       return;
/*     */     } 
/* 137 */     int j = 0;
/* 138 */     if (this.bufAvail > 0) {
/* 139 */       System.arraycopy(this.buf, this.bufPtr, paramArrayOfbyte, 0, this.bufAvail);
/* 140 */       j = this.bufAvail;
/* 141 */       i -= this.bufAvail;
/* 142 */       this.bufAvail = 0;
/* 143 */       this.bufPtr = 0;
/*     */     } 
/*     */     
/* 146 */     if (this.iis.read(paramArrayOfbyte, j, i) != i) {
/* 147 */       throw new IIOException("Image format Error");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void skipData(int paramInt) throws IOException {
/* 159 */     if (this.bufAvail >= paramInt) {
/* 160 */       this.bufAvail -= paramInt;
/* 161 */       this.bufPtr += paramInt;
/*     */       return;
/*     */     } 
/* 164 */     if (this.bufAvail > 0) {
/* 165 */       paramInt -= this.bufAvail;
/* 166 */       this.bufAvail = 0;
/* 167 */       this.bufPtr = 0;
/*     */     } 
/*     */     
/* 170 */     if (this.iis.skipBytes(paramInt) != paramInt) {
/* 171 */       throw new IIOException("Image format Error");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void pushBack() throws IOException {
/* 180 */     this.iis.seek(this.iis.getStreamPosition() - this.bufAvail);
/* 181 */     this.bufAvail = 0;
/* 182 */     this.bufPtr = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getStreamPosition() throws IOException {
/* 190 */     return this.iis.getStreamPosition() - this.bufAvail;
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
/*     */   boolean scanForFF(JPEGImageReader paramJPEGImageReader) throws IOException {
/* 202 */     boolean bool1 = false;
/* 203 */     boolean bool2 = false;
/* 204 */     while (!bool2) {
/* 205 */       while (this.bufAvail > 0) {
/* 206 */         if ((this.buf[this.bufPtr++] & 0xFF) == 255) {
/* 207 */           this.bufAvail--;
/* 208 */           bool2 = true;
/*     */           break;
/*     */         } 
/* 211 */         this.bufAvail--;
/*     */       } 
/*     */       
/* 214 */       loadBuf(0);
/*     */       
/* 216 */       if (bool2 == true) {
/* 217 */         while (this.bufAvail > 0 && (this.buf[this.bufPtr] & 0xFF) == 255) {
/* 218 */           this.bufPtr++;
/* 219 */           this.bufAvail--;
/*     */         } 
/*     */       }
/* 222 */       if (this.bufAvail == 0) {
/*     */ 
/*     */         
/* 225 */         bool1 = true;
/* 226 */         this.buf[0] = -39;
/* 227 */         this.bufAvail = 1;
/* 228 */         this.bufPtr = 0;
/* 229 */         bool2 = true;
/*     */       } 
/*     */     } 
/* 232 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void print(int paramInt) {
/* 241 */     System.out.print("buffer has ");
/* 242 */     System.out.print(this.bufAvail);
/* 243 */     System.out.println(" bytes available");
/* 244 */     if (this.bufAvail < paramInt) {
/* 245 */       paramInt = this.bufAvail;
/*     */     }
/* 247 */     for (int i = this.bufPtr; paramInt > 0; paramInt--) {
/* 248 */       int j = this.buf[i++] & 0xFF;
/* 249 */       System.out.print(" " + Integer.toHexString(j));
/*     */     } 
/* 251 */     System.out.println();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/jpeg/JPEGBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */