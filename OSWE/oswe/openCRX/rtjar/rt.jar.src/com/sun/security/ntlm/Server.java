/*     */ package com.sun.security.ntlm;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public abstract class Server
/*     */   extends NTLM
/*     */ {
/*     */   private final String domain;
/*     */   private final boolean allVersion;
/*     */   
/*     */   public Server(String paramString1, String paramString2) throws NTLMException {
/*  69 */     super(paramString1);
/*  70 */     if (paramString2 == null) {
/*  71 */       throw new NTLMException(6, "domain cannot be null");
/*     */     }
/*     */     
/*  74 */     this.allVersion = (paramString1 == null);
/*  75 */     this.domain = paramString2;
/*  76 */     debug("NTLM Server: (t,version) = (%s,%s)\n", new Object[] { paramString2, paramString1 });
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
/*     */   public byte[] type2(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws NTLMException {
/*  89 */     if (paramArrayOfbyte2 == null) {
/*  90 */       throw new NTLMException(6, "nonce cannot be null");
/*     */     }
/*     */     
/*  93 */     debug("NTLM Server: Type 1 received\n", new Object[0]);
/*  94 */     if (paramArrayOfbyte1 != null) debug(paramArrayOfbyte1); 
/*  95 */     NTLM.Writer writer = new NTLM.Writer(2, 32);
/*     */ 
/*     */     
/*  98 */     int i = 590341;
/*  99 */     writer.writeSecurityBuffer(12, this.domain, true);
/* 100 */     writer.writeInt(20, i);
/* 101 */     writer.writeBytes(24, paramArrayOfbyte2);
/* 102 */     debug("NTLM Server: Type 2 created\n", new Object[0]);
/* 103 */     debug(writer.getBytes());
/* 104 */     return writer.getBytes();
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
/*     */   public String[] verify(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws NTLMException {
/* 118 */     if (paramArrayOfbyte1 == null || paramArrayOfbyte2 == null) {
/* 119 */       throw new NTLMException(6, "type1 or nonce cannot be null");
/*     */     }
/*     */     
/* 122 */     debug("NTLM Server: Type 3 received\n", new Object[0]);
/* 123 */     if (paramArrayOfbyte1 != null) debug(paramArrayOfbyte1); 
/* 124 */     NTLM.Reader reader = new NTLM.Reader(paramArrayOfbyte1);
/* 125 */     String str1 = reader.readSecurityBuffer(36, true);
/* 126 */     String str2 = reader.readSecurityBuffer(44, true);
/* 127 */     String str3 = reader.readSecurityBuffer(28, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     boolean bool = false;
/* 135 */     char[] arrayOfChar = getPassword(str3, str1);
/* 136 */     if (arrayOfChar == null) {
/* 137 */       throw new NTLMException(3, "Unknown user");
/*     */     }
/*     */     
/* 140 */     byte[] arrayOfByte1 = reader.readSecurityBuffer(12);
/* 141 */     byte[] arrayOfByte2 = reader.readSecurityBuffer(20);
/*     */     
/* 143 */     if (!bool && (this.allVersion || this.v == Version.NTLM)) {
/* 144 */       if (arrayOfByte1.length > 0) {
/* 145 */         byte[] arrayOfByte3 = getP1(arrayOfChar);
/* 146 */         byte[] arrayOfByte4 = calcLMHash(arrayOfByte3);
/* 147 */         byte[] arrayOfByte5 = calcResponse(arrayOfByte4, paramArrayOfbyte2);
/* 148 */         if (Arrays.equals(arrayOfByte5, arrayOfByte1)) {
/* 149 */           bool = true;
/*     */         }
/*     */       } 
/* 152 */       if (arrayOfByte2.length > 0) {
/* 153 */         byte[] arrayOfByte3 = getP2(arrayOfChar);
/* 154 */         byte[] arrayOfByte4 = calcNTHash(arrayOfByte3);
/* 155 */         byte[] arrayOfByte5 = calcResponse(arrayOfByte4, paramArrayOfbyte2);
/* 156 */         if (Arrays.equals(arrayOfByte5, arrayOfByte2)) {
/* 157 */           bool = true;
/*     */         }
/*     */       } 
/* 160 */       debug("NTLM Server: verify using NTLM: " + bool + "\n", new Object[0]);
/*     */     } 
/* 162 */     if (!bool && (this.allVersion || this.v == Version.NTLM2)) {
/* 163 */       byte[] arrayOfByte3 = getP2(arrayOfChar);
/* 164 */       byte[] arrayOfByte4 = calcNTHash(arrayOfByte3);
/* 165 */       byte[] arrayOfByte5 = Arrays.copyOf(arrayOfByte1, 8);
/* 166 */       byte[] arrayOfByte6 = ntlm2NTLM(arrayOfByte4, arrayOfByte5, paramArrayOfbyte2);
/* 167 */       if (Arrays.equals(arrayOfByte2, arrayOfByte6)) {
/* 168 */         bool = true;
/*     */       }
/* 170 */       debug("NTLM Server: verify using NTLM2: " + bool + "\n", new Object[0]);
/*     */     } 
/* 172 */     if (!bool && (this.allVersion || this.v == Version.NTLMv2)) {
/* 173 */       byte[] arrayOfByte3 = getP2(arrayOfChar);
/* 174 */       byte[] arrayOfByte4 = calcNTHash(arrayOfByte3);
/* 175 */       if (arrayOfByte1.length > 0) {
/* 176 */         byte[] arrayOfByte5 = Arrays.copyOfRange(arrayOfByte1, 16, arrayOfByte1.length);
/*     */         
/* 178 */         byte[] arrayOfByte6 = calcV2(arrayOfByte4, str1
/* 179 */             .toUpperCase(Locale.US) + str3, arrayOfByte5, paramArrayOfbyte2);
/*     */         
/* 181 */         if (Arrays.equals(arrayOfByte6, arrayOfByte1)) {
/* 182 */           bool = true;
/*     */         }
/*     */       } 
/* 185 */       if (arrayOfByte2.length > 0) {
/*     */ 
/*     */         
/* 188 */         byte[] arrayOfByte5 = Arrays.copyOfRange(arrayOfByte2, 16, arrayOfByte2.length);
/*     */         
/* 190 */         byte[] arrayOfByte6 = calcV2(arrayOfByte4, str1
/* 191 */             .toUpperCase(Locale.US) + str3, arrayOfByte5, paramArrayOfbyte2);
/*     */         
/* 193 */         if (Arrays.equals(arrayOfByte6, arrayOfByte2)) {
/* 194 */           bool = true;
/*     */         }
/*     */       } 
/* 197 */       debug("NTLM Server: verify using NTLMv2: " + bool + "\n", new Object[0]);
/*     */     } 
/* 199 */     if (!bool) {
/* 200 */       throw new NTLMException(4, "None of LM and NTLM verified");
/*     */     }
/*     */     
/* 203 */     return new String[] { str1, str2, str3 };
/*     */   }
/*     */   
/*     */   public abstract char[] getPassword(String paramString1, String paramString2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/ntlm/Server.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */