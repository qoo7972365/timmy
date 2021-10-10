/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.CodeSigner;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.nio.ByteBuffered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Resource
/*     */ {
/*     */   private InputStream cis;
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract URL getURL();
/*     */   
/*     */   public abstract URL getCodeSourceURL();
/*     */   
/*     */   public abstract InputStream getInputStream() throws IOException;
/*     */   
/*     */   public abstract int getContentLength() throws IOException;
/*     */   
/*     */   private synchronized InputStream cachedInputStream() throws IOException {
/*  76 */     if (this.cis == null) {
/*  77 */       this.cis = getInputStream();
/*     */     }
/*  79 */     return this.cis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() throws IOException {
/*     */     byte[] arrayOfByte;
/*     */     int i;
/*  89 */     InputStream inputStream = cachedInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     boolean bool = Thread.interrupted();
/*     */     
/*     */     while (true) {
/*     */       try {
/* 100 */         i = getContentLength();
/*     */         break;
/* 102 */       } catch (InterruptedIOException interruptedIOException) {
/* 103 */         Thread.interrupted();
/* 104 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 109 */       arrayOfByte = new byte[0];
/* 110 */       if (i == -1) i = Integer.MAX_VALUE; 
/* 111 */       int j = 0;
/* 112 */       while (j < i) {
/*     */         int k;
/* 114 */         if (j >= arrayOfByte.length) {
/* 115 */           k = Math.min(i - j, arrayOfByte.length + 1024);
/* 116 */           if (arrayOfByte.length < j + k) {
/* 117 */             arrayOfByte = Arrays.copyOf(arrayOfByte, j + k);
/*     */           }
/*     */         } else {
/* 120 */           k = arrayOfByte.length - j;
/*     */         } 
/* 122 */         int m = 0;
/*     */         try {
/* 124 */           m = inputStream.read(arrayOfByte, j, k);
/* 125 */         } catch (InterruptedIOException interruptedIOException) {
/* 126 */           Thread.interrupted();
/* 127 */           bool = true;
/*     */         } 
/* 129 */         if (m < 0) {
/* 130 */           if (i != Integer.MAX_VALUE) {
/* 131 */             throw new EOFException("Detect premature EOF");
/*     */           }
/* 133 */           if (arrayOfByte.length != j) {
/* 134 */             arrayOfByte = Arrays.copyOf(arrayOfByte, j);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/* 139 */         j += m;
/*     */       } 
/*     */     } finally {
/*     */       try {
/* 143 */         inputStream.close();
/* 144 */       } catch (InterruptedIOException interruptedIOException) {
/* 145 */         bool = true;
/* 146 */       } catch (IOException iOException) {}
/*     */       
/* 148 */       if (bool) {
/* 149 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/* 152 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getByteBuffer() throws IOException {
/* 160 */     InputStream inputStream = cachedInputStream();
/* 161 */     if (inputStream instanceof ByteBuffered) {
/* 162 */       return ((ByteBuffered)inputStream).getByteBuffer();
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest getManifest() throws IOException {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getCertificates() {
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSigner[] getCodeSigners() {
/* 185 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Resource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */