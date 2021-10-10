/*     */ package com.sun.security.sasl.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.sasl.SaslException;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSaslImpl
/*     */ {
/*     */   protected boolean completed = false;
/*     */   protected boolean privacy = false;
/*     */   protected boolean integrity = false;
/*     */   protected byte[] qop;
/*     */   protected byte allQop;
/*     */   protected byte[] strength;
/*  58 */   protected int sendMaxBufSize = 0;
/*  59 */   protected int recvMaxBufSize = 65536;
/*     */   protected int rawSendSize;
/*     */   protected String myClassName;
/*     */   private static final String SASL_LOGGER_NAME = "javax.security.sasl";
/*     */   protected static final String MAX_SEND_BUF = "javax.security.sasl.sendmaxbuffer";
/*     */   
/*     */   protected AbstractSaslImpl(Map<String, ?> paramMap, String paramString) throws SaslException {
/*  66 */     this.myClassName = paramString;
/*     */ 
/*     */     
/*  69 */     if (paramMap != null) {
/*     */       String str;
/*     */ 
/*     */       
/*  73 */       this.qop = parseQop(str = (String)paramMap.get("javax.security.sasl.qop"));
/*  74 */       logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL01:Preferred qop property: {0}", str);
/*     */       
/*  76 */       this.allQop = combineMasks(this.qop);
/*     */       
/*  78 */       if (logger.isLoggable(Level.FINE)) {
/*  79 */         logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL02:Preferred qop mask: {0}", new Byte(this.allQop));
/*     */ 
/*     */         
/*  82 */         if (this.qop.length > 0) {
/*  83 */           StringBuffer stringBuffer = new StringBuffer();
/*  84 */           for (byte b = 0; b < this.qop.length; b++) {
/*  85 */             stringBuffer.append(Byte.toString(this.qop[b]));
/*  86 */             stringBuffer.append(' ');
/*     */           } 
/*  88 */           logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL03:Preferred qops : {0}", stringBuffer
/*  89 */               .toString());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  94 */       this.strength = parseStrength(str = (String)paramMap.get("javax.security.sasl.strength"));
/*  95 */       logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL04:Preferred strength property: {0}", str);
/*     */       
/*  97 */       if (logger.isLoggable(Level.FINE) && this.strength.length > 0) {
/*  98 */         StringBuffer stringBuffer = new StringBuffer();
/*  99 */         for (byte b = 0; b < this.strength.length; b++) {
/* 100 */           stringBuffer.append(Byte.toString(this.strength[b]));
/* 101 */           stringBuffer.append(' ');
/*     */         } 
/* 103 */         logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL05:Cipher strengths: {0}", stringBuffer
/* 104 */             .toString());
/*     */       } 
/*     */ 
/*     */       
/* 108 */       str = (String)paramMap.get("javax.security.sasl.maxbuffer");
/* 109 */       if (str != null) {
/*     */         try {
/* 111 */           logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL06:Max receive buffer size: {0}", str);
/*     */           
/* 113 */           this.recvMaxBufSize = Integer.parseInt(str);
/* 114 */         } catch (NumberFormatException numberFormatException) {
/* 115 */           throw new SaslException("Property must be string representation of integer: javax.security.sasl.maxbuffer");
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       str = (String)paramMap.get("javax.security.sasl.sendmaxbuffer");
/* 123 */       if (str != null) {
/*     */         try {
/* 125 */           logger.logp(Level.FINE, this.myClassName, "constructor", "SASLIMPL07:Max send buffer size: {0}", str);
/*     */           
/* 127 */           this.sendMaxBufSize = Integer.parseInt(str);
/* 128 */         } catch (NumberFormatException numberFormatException) {
/* 129 */           throw new SaslException("Property must be string representation of integer: javax.security.sasl.sendmaxbuffer");
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 135 */       this.qop = DEFAULT_QOP;
/* 136 */       this.allQop = 1;
/* 137 */       this.strength = STRENGTH_MASKS;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 147 */     return this.completed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 156 */     if (!this.completed) {
/* 157 */       throw new IllegalStateException("SASL authentication not completed");
/*     */     }
/* 159 */     switch (paramString) {
/*     */       case "javax.security.sasl.qop":
/* 161 */         if (this.privacy)
/* 162 */           return "auth-conf"; 
/* 163 */         if (this.integrity) {
/* 164 */           return "auth-int";
/*     */         }
/* 166 */         return "auth";
/*     */       
/*     */       case "javax.security.sasl.maxbuffer":
/* 169 */         return Integer.toString(this.recvMaxBufSize);
/*     */       case "javax.security.sasl.rawsendsize":
/* 171 */         return Integer.toString(this.rawSendSize);
/*     */       case "javax.security.sasl.sendmaxbuffer":
/* 173 */         return Integer.toString(this.sendMaxBufSize);
/*     */     } 
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final byte combineMasks(byte[] paramArrayOfbyte) {
/* 180 */     byte b = 0;
/* 181 */     for (byte b1 = 0; b1 < paramArrayOfbyte.length; b1++) {
/* 182 */       b = (byte)(b | paramArrayOfbyte[b1]);
/*     */     }
/* 184 */     return b;
/*     */   }
/*     */   
/*     */   protected static final byte findPreferredMask(byte paramByte, byte[] paramArrayOfbyte) {
/* 188 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 189 */       if ((paramArrayOfbyte[b] & paramByte) != 0) {
/* 190 */         return paramArrayOfbyte[b];
/*     */       }
/*     */     } 
/* 193 */     return 0;
/*     */   }
/*     */   
/*     */   private static final byte[] parseQop(String paramString) throws SaslException {
/* 197 */     return parseQop(paramString, null, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final byte[] parseQop(String paramString, String[] paramArrayOfString, boolean paramBoolean) throws SaslException {
/* 202 */     if (paramString == null) {
/* 203 */       return DEFAULT_QOP;
/*     */     }
/*     */     
/* 206 */     return parseProp("javax.security.sasl.qop", paramString, QOP_TOKENS, QOP_MASKS, paramArrayOfString, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final byte[] parseStrength(String paramString) throws SaslException {
/* 211 */     if (paramString == null) {
/* 212 */       return DEFAULT_STRENGTH;
/*     */     }
/*     */     
/* 215 */     return parseProp("javax.security.sasl.strength", paramString, STRENGTH_TOKENS, STRENGTH_MASKS, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte[] parseProp(String paramString1, String paramString2, String[] paramArrayOfString1, byte[] paramArrayOfbyte, String[] paramArrayOfString2, boolean paramBoolean) throws SaslException {
/* 223 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString2, ", \t\n");
/*     */     
/* 225 */     byte[] arrayOfByte = new byte[paramArrayOfString1.length];
/* 226 */     byte b1 = 0;
/*     */ 
/*     */     
/* 229 */     while (stringTokenizer.hasMoreTokens() && b1 < arrayOfByte.length) {
/* 230 */       String str = stringTokenizer.nextToken();
/* 231 */       boolean bool = false;
/* 232 */       for (byte b = 0; !bool && b < paramArrayOfString1.length; b++) {
/* 233 */         if (str.equalsIgnoreCase(paramArrayOfString1[b])) {
/* 234 */           bool = true;
/* 235 */           arrayOfByte[b1++] = paramArrayOfbyte[b];
/* 236 */           if (paramArrayOfString2 != null) {
/* 237 */             paramArrayOfString2[b] = str;
/*     */           }
/*     */         } 
/*     */       } 
/* 241 */       if (!bool && !paramBoolean) {
/* 242 */         throw new SaslException("Invalid token in " + paramString1 + ": " + paramString2);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 247 */     for (byte b2 = b1; b2 < arrayOfByte.length; b2++) {
/* 248 */       arrayOfByte[b2] = 0;
/*     */     }
/* 250 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final void traceOutput(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfbyte) {
/* 259 */     traceOutput(paramString1, paramString2, paramString3, paramArrayOfbyte, 0, (paramArrayOfbyte == null) ? 0 : paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   protected static final void traceOutput(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*     */     try {
/*     */       Level level;
/*     */       String str;
/* 266 */       int i = paramInt2;
/*     */ 
/*     */       
/* 269 */       if (!logger.isLoggable(Level.FINEST)) {
/* 270 */         paramInt2 = Math.min(16, paramInt2);
/* 271 */         level = Level.FINER;
/*     */       } else {
/* 273 */         level = Level.FINEST;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 278 */       if (paramArrayOfbyte != null) {
/* 279 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(paramInt2);
/* 280 */         (new HexDumpEncoder()).encodeBuffer(new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2), byteArrayOutputStream);
/*     */         
/* 282 */         str = byteArrayOutputStream.toString();
/*     */       } else {
/* 284 */         str = "NULL";
/*     */       } 
/*     */ 
/*     */       
/* 288 */       logger.logp(level, paramString1, paramString2, "{0} ( {1} ): {2}", new Object[] { paramString3, new Integer(i), str });
/*     */     }
/* 290 */     catch (Exception exception) {
/* 291 */       logger.logp(Level.WARNING, paramString1, paramString2, "SASLIMPL09:Error generating trace output: {0}", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int networkByteOrderToInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 302 */     if (paramInt2 > 4) {
/* 303 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */     
/* 306 */     int i = 0;
/*     */     
/* 308 */     for (byte b = 0; b < paramInt2; b++) {
/* 309 */       i <<= 8;
/* 310 */       i |= paramArrayOfbyte[paramInt1 + b] & 0xFF;
/*     */     } 
/* 312 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final void intToNetworkByteOrder(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 321 */     if (paramInt3 > 4) {
/* 322 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */     
/* 325 */     for (int i = paramInt3 - 1; i >= 0; i--) {
/* 326 */       paramArrayOfbyte[paramInt2 + i] = (byte)(paramInt1 & 0xFF);
/* 327 */       paramInt1 >>>= 8;
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
/* 338 */   protected static final Logger logger = Logger.getLogger("javax.security.sasl");
/*     */   
/*     */   protected static final byte NO_PROTECTION = 1;
/*     */   
/*     */   protected static final byte INTEGRITY_ONLY_PROTECTION = 2;
/*     */   
/*     */   protected static final byte PRIVACY_PROTECTION = 4;
/*     */   
/*     */   protected static final byte LOW_STRENGTH = 1;
/*     */   protected static final byte MEDIUM_STRENGTH = 2;
/*     */   protected static final byte HIGH_STRENGTH = 4;
/* 349 */   private static final byte[] DEFAULT_QOP = new byte[] { 1 };
/* 350 */   private static final String[] QOP_TOKENS = new String[] { "auth-conf", "auth-int", "auth" };
/*     */ 
/*     */   
/* 353 */   private static final byte[] QOP_MASKS = new byte[] { 4, 2, 1 };
/*     */ 
/*     */ 
/*     */   
/* 357 */   private static final byte[] DEFAULT_STRENGTH = new byte[] { 4, 2, 1 };
/*     */   
/* 359 */   private static final String[] STRENGTH_TOKENS = new String[] { "low", "medium", "high" };
/*     */ 
/*     */   
/* 362 */   private static final byte[] STRENGTH_MASKS = new byte[] { 1, 2, 4 };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/util/AbstractSaslImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */