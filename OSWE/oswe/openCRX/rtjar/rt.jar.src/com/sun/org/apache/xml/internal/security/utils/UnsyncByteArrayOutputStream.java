/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnsyncByteArrayOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private static final int INITIAL_SIZE = 8192;
/*     */   private byte[] buf;
/*  37 */   private int size = 8192;
/*  38 */   private int pos = 0;
/*     */   
/*     */   public UnsyncByteArrayOutputStream() {
/*  41 */     this.buf = new byte[8192];
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte) {
/*  45 */     if (Integer.MAX_VALUE - this.pos < paramArrayOfbyte.length) {
/*  46 */       throw new OutOfMemoryError();
/*     */     }
/*  48 */     int i = this.pos + paramArrayOfbyte.length;
/*  49 */     if (i > this.size) {
/*  50 */       expandSize(i);
/*     */     }
/*  52 */     System.arraycopy(paramArrayOfbyte, 0, this.buf, this.pos, paramArrayOfbyte.length);
/*  53 */     this.pos = i;
/*     */   }
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  57 */     if (Integer.MAX_VALUE - this.pos < paramInt2) {
/*  58 */       throw new OutOfMemoryError();
/*     */     }
/*  60 */     int i = this.pos + paramInt2;
/*  61 */     if (i > this.size) {
/*  62 */       expandSize(i);
/*     */     }
/*  64 */     System.arraycopy(paramArrayOfbyte, paramInt1, this.buf, this.pos, paramInt2);
/*  65 */     this.pos = i;
/*     */   }
/*     */   
/*     */   public void write(int paramInt) {
/*  69 */     if (Integer.MAX_VALUE - this.pos == 0) {
/*  70 */       throw new OutOfMemoryError();
/*     */     }
/*  72 */     int i = this.pos + 1;
/*  73 */     if (i > this.size) {
/*  74 */       expandSize(i);
/*     */     }
/*  76 */     this.buf[this.pos++] = (byte)paramInt;
/*     */   }
/*     */   
/*     */   public byte[] toByteArray() {
/*  80 */     byte[] arrayOfByte = new byte[this.pos];
/*  81 */     System.arraycopy(this.buf, 0, arrayOfByte, 0, this.pos);
/*  82 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  86 */     this.pos = 0;
/*     */   }
/*     */   
/*     */   private void expandSize(int paramInt) {
/*  90 */     int i = this.size;
/*  91 */     while (paramInt > i) {
/*  92 */       i <<= 1;
/*     */       
/*  94 */       if (i < 0) {
/*  95 */         i = Integer.MAX_VALUE;
/*     */       }
/*     */     } 
/*  98 */     byte[] arrayOfByte = new byte[i];
/*  99 */     System.arraycopy(this.buf, 0, arrayOfByte, 0, this.pos);
/* 100 */     this.buf = arrayOfByte;
/* 101 */     this.size = i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/UnsyncByteArrayOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */