/*     */ package com.sun.security.ntlm;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Client
/*     */   extends NTLM
/*     */ {
/*     */   private final String hostname;
/*     */   private final String username;
/*     */   private String domain;
/*     */   private byte[] pw1;
/*     */   private byte[] pw2;
/*     */   
/*     */   public Client(String paramString1, String paramString2, String paramString3, String paramString4, char[] paramArrayOfchar) throws NTLMException {
/*  78 */     super(paramString1);
/*  79 */     if (paramString3 == null || paramArrayOfchar == null) {
/*  80 */       throw new NTLMException(6, "username/password cannot be null");
/*     */     }
/*     */     
/*  83 */     this.hostname = paramString2;
/*  84 */     this.username = paramString3;
/*  85 */     this.domain = (paramString4 == null) ? "" : paramString4;
/*  86 */     this.pw1 = getP1(paramArrayOfchar);
/*  87 */     this.pw2 = getP2(paramArrayOfchar);
/*  88 */     debug("NTLM Client: (h,u,t,version(v)) = (%s,%s,%s,%s(%s))\n", new Object[] { paramString2, paramString3, paramString4, paramString1, this.v
/*  89 */           .toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] type1() {
/*  97 */     NTLM.Writer writer = new NTLM.Writer(1, 32);
/*     */ 
/*     */     
/* 100 */     int i = 33287;
/* 101 */     if (this.v != Version.NTLM) {
/* 102 */       i |= 0x80000;
/*     */     }
/* 104 */     writer.writeInt(12, i);
/* 105 */     debug("NTLM Client: Type 1 created\n", new Object[0]);
/* 106 */     debug(writer.getBytes());
/* 107 */     return writer.getBytes();
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
/*     */   public byte[] type3(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws NTLMException {
/* 120 */     if (paramArrayOfbyte1 == null || (this.v != Version.NTLM && paramArrayOfbyte2 == null)) {
/* 121 */       throw new NTLMException(6, "type2 and nonce cannot be null");
/*     */     }
/*     */     
/* 124 */     debug("NTLM Client: Type 2 received\n", new Object[0]);
/* 125 */     debug(paramArrayOfbyte1);
/* 126 */     NTLM.Reader reader = new NTLM.Reader(paramArrayOfbyte1);
/* 127 */     byte[] arrayOfByte1 = reader.readBytes(24, 8);
/* 128 */     int i = reader.readInt(20);
/* 129 */     boolean bool = ((i & 0x1) == 1) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     int j = 0x88200 | i & 0x3;
/* 136 */     NTLM.Writer writer = new NTLM.Writer(3, 64);
/* 137 */     byte[] arrayOfByte2 = null, arrayOfByte3 = null;
/*     */     
/* 139 */     writer.writeSecurityBuffer(28, this.domain, bool);
/* 140 */     writer.writeSecurityBuffer(36, this.username, bool);
/* 141 */     writer.writeSecurityBuffer(44, this.hostname, bool);
/*     */     
/* 143 */     if (this.v == Version.NTLM) {
/* 144 */       byte[] arrayOfByte4 = calcLMHash(this.pw1);
/* 145 */       byte[] arrayOfByte5 = calcNTHash(this.pw2);
/* 146 */       if (this.writeLM) arrayOfByte2 = calcResponse(arrayOfByte4, arrayOfByte1); 
/* 147 */       if (this.writeNTLM) arrayOfByte3 = calcResponse(arrayOfByte5, arrayOfByte1); 
/* 148 */     } else if (this.v == Version.NTLM2) {
/* 149 */       byte[] arrayOfByte = calcNTHash(this.pw2);
/* 150 */       arrayOfByte2 = ntlm2LM(paramArrayOfbyte2);
/* 151 */       arrayOfByte3 = ntlm2NTLM(arrayOfByte, paramArrayOfbyte2, arrayOfByte1);
/*     */     } else {
/* 153 */       byte[] arrayOfByte = calcNTHash(this.pw2);
/* 154 */       if (this.writeLM) arrayOfByte2 = calcV2(arrayOfByte, this.username
/* 155 */             .toUpperCase(Locale.US) + this.domain, paramArrayOfbyte2, arrayOfByte1); 
/* 156 */       if (this.writeNTLM) {
/*     */ 
/*     */ 
/*     */         
/* 160 */         byte[] arrayOfByte4 = ((i & 0x800000) != 0) ? reader.readSecurityBuffer(40) : new byte[0];
/* 161 */         byte[] arrayOfByte5 = new byte[32 + arrayOfByte4.length];
/* 162 */         System.arraycopy(new byte[] { 1, 1, 0, 0, 0, 0, 0, 0 }, 0, arrayOfByte5, 0, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 167 */         byte[] arrayOfByte6 = BigInteger.valueOf((new Date()).getTime()).add(new BigInteger("11644473600000")).multiply(BigInteger.valueOf(10000L)).toByteArray();
/* 168 */         for (byte b = 0; b < arrayOfByte6.length; b++) {
/* 169 */           arrayOfByte5[8 + arrayOfByte6.length - b - 1] = arrayOfByte6[b];
/*     */         }
/* 171 */         System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte5, 16, 8);
/* 172 */         System.arraycopy(new byte[] { 0, 0, 0, 0 }, 0, arrayOfByte5, 24, 4);
/* 173 */         System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 28, arrayOfByte4.length);
/* 174 */         System.arraycopy(new byte[] { 0, 0, 0, 0 }, 0, arrayOfByte5, 28 + arrayOfByte4.length, 4);
/*     */         
/* 176 */         arrayOfByte3 = calcV2(arrayOfByte, this.username.toUpperCase(Locale.US) + this.domain, arrayOfByte5, arrayOfByte1);
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     writer.writeSecurityBuffer(12, arrayOfByte2);
/* 181 */     writer.writeSecurityBuffer(20, arrayOfByte3);
/* 182 */     writer.writeSecurityBuffer(52, new byte[0]);
/*     */     
/* 184 */     writer.writeInt(60, j);
/* 185 */     debug("NTLM Client: Type 3 created\n", new Object[0]);
/* 186 */     debug(writer.getBytes());
/* 187 */     return writer.getBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 196 */     return this.domain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 203 */     Arrays.fill(this.pw1, (byte)0);
/* 204 */     Arrays.fill(this.pw2, (byte)0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/ntlm/Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */