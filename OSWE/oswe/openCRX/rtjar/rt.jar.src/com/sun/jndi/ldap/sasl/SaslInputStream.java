/*     */ package com.sun.jndi.ldap.sasl;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaslInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private byte[] saslBuffer;
/*  51 */   private byte[] lenBuf = new byte[4];
/*     */   
/*  53 */   private byte[] buf = new byte[0];
/*     */   
/*  55 */   private int bufPos = 0;
/*     */   private InputStream in;
/*     */   private SaslClient sc;
/*  58 */   private int recvMaxBufSize = 65536;
/*     */ 
/*     */   
/*     */   SaslInputStream(SaslClient paramSaslClient, InputStream paramInputStream) throws SaslException {
/*  62 */     this.in = paramInputStream;
/*  63 */     this.sc = paramSaslClient;
/*     */     
/*  65 */     String str = (String)paramSaslClient.getNegotiatedProperty("javax.security.sasl.maxbuffer");
/*  66 */     if (str != null) {
/*     */       try {
/*  68 */         this.recvMaxBufSize = Integer.parseInt(str);
/*  69 */       } catch (NumberFormatException numberFormatException) {
/*  70 */         throw new SaslException("javax.security.sasl.maxbuffer property must be numeric string: " + str);
/*     */       } 
/*     */     }
/*     */     
/*  74 */     this.saslBuffer = new byte[this.recvMaxBufSize];
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  78 */     byte[] arrayOfByte = new byte[1];
/*  79 */     int i = read(arrayOfByte, 0, 1);
/*  80 */     if (i > 0) {
/*  81 */       return arrayOfByte[0];
/*     */     }
/*  83 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  89 */     if (this.bufPos >= this.buf.length) {
/*  90 */       int j = fill();
/*  91 */       while (j == 0) {
/*  92 */         j = fill();
/*     */       }
/*  94 */       if (j == -1) {
/*  95 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     int i = this.buf.length - this.bufPos;
/* 100 */     if (paramInt2 > i) {
/*     */ 
/*     */ 
/*     */       
/* 104 */       System.arraycopy(this.buf, this.bufPos, paramArrayOfbyte, paramInt1, i);
/* 105 */       this.bufPos = this.buf.length;
/* 106 */       return i;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     System.arraycopy(this.buf, this.bufPos, paramArrayOfbyte, paramInt1, paramInt2);
/* 111 */     this.bufPos += paramInt2;
/* 112 */     return paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fill() throws IOException {
/* 123 */     int i = readFully(this.lenBuf, 4);
/* 124 */     if (i != 4) {
/* 125 */       return -1;
/*     */     }
/* 127 */     int j = networkByteOrderToInt(this.lenBuf, 0, 4);
/*     */     
/* 129 */     if (j > this.recvMaxBufSize) {
/* 130 */       throw new IOException(j + "exceeds the negotiated receive buffer size limit:" + this.recvMaxBufSize);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     i = readFully(this.saslBuffer, j);
/* 141 */     if (i != j) {
/* 142 */       throw new EOFException("Expecting to read " + j + " bytes but got " + i + " bytes before EOF");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.buf = this.sc.unwrap(this.saslBuffer, 0, j);
/*     */     
/* 149 */     this.bufPos = 0;
/*     */     
/* 151 */     return this.buf.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readFully(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 159 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     while (paramInt > 0) {
/* 166 */       int j = this.in.read(paramArrayOfbyte, i, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (j == -1) {
/* 173 */         return !i ? -1 : i;
/*     */       }
/* 175 */       i += j;
/* 176 */       paramInt -= j;
/*     */     } 
/* 178 */     return i;
/*     */   }
/*     */   
/*     */   public int available() throws IOException {
/* 182 */     return this.buf.length - this.bufPos;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 186 */     SaslException saslException = null;
/*     */     try {
/* 188 */       this.sc.dispose();
/* 189 */     } catch (SaslException saslException1) {
/*     */       
/* 191 */       saslException = saslException1;
/*     */     } 
/*     */     
/* 194 */     this.in.close();
/*     */     
/* 196 */     if (saslException != null) {
/* 197 */       throw saslException;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int networkByteOrderToInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 206 */     if (paramInt2 > 4) {
/* 207 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */     
/* 210 */     int i = 0;
/*     */     
/* 212 */     for (byte b = 0; b < paramInt2; b++) {
/* 213 */       i <<= 8;
/* 214 */       i |= paramArrayOfbyte[paramInt1 + b] & 0xFF;
/*     */     } 
/* 216 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/sasl/SaslInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */