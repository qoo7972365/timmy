/*     */ package com.sun.jndi.ldap.sasl;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ class SaslOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private static final boolean debug = false;
/*  38 */   private byte[] lenBuf = new byte[4];
/*  39 */   private int rawSendSize = 65536;
/*     */   private SaslClient sc;
/*     */   
/*     */   SaslOutputStream(SaslClient paramSaslClient, OutputStream paramOutputStream) throws SaslException {
/*  43 */     super(paramOutputStream);
/*  44 */     this.sc = paramSaslClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     String str = (String)paramSaslClient.getNegotiatedProperty("javax.security.sasl.rawsendsize");
/*  51 */     if (str != null) {
/*     */       try {
/*  53 */         this.rawSendSize = Integer.parseInt(str);
/*  54 */       } catch (NumberFormatException numberFormatException) {
/*  55 */         throw new SaslException("javax.security.sasl.rawsendsize property must be numeric string: " + str);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/*  65 */     byte[] arrayOfByte = new byte[1];
/*  66 */     arrayOfByte[0] = (byte)paramInt;
/*  67 */     write(arrayOfByte, 0, 1);
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     int i;
/*  83 */     for (i = 0; i < paramInt2; i += this.rawSendSize) {
/*     */ 
/*     */       
/*  86 */       int j = (paramInt2 - i < this.rawSendSize) ? (paramInt2 - i) : this.rawSendSize;
/*     */ 
/*     */       
/*  89 */       byte[] arrayOfByte = this.sc.wrap(paramArrayOfbyte, paramInt1 + i, j);
/*     */ 
/*     */       
/*  92 */       intToNetworkByteOrder(arrayOfByte.length, this.lenBuf, 0, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       this.out.write(this.lenBuf, 0, 4);
/*     */ 
/*     */       
/* 100 */       this.out.write(arrayOfByte, 0, arrayOfByte.length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 105 */     SaslException saslException = null;
/*     */     try {
/* 107 */       this.sc.dispose();
/* 108 */     } catch (SaslException saslException1) {
/*     */       
/* 110 */       saslException = saslException1;
/*     */     } 
/* 112 */     super.close();
/*     */     
/* 114 */     if (saslException != null) {
/* 115 */       throw saslException;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void intToNetworkByteOrder(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 126 */     if (paramInt3 > 4) {
/* 127 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */     
/* 130 */     for (int i = paramInt3 - 1; i >= 0; i--) {
/* 131 */       paramArrayOfbyte[paramInt2 + i] = (byte)(paramInt1 & 0xFF);
/* 132 */       paramInt1 >>>= 8;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/sasl/SaslOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */