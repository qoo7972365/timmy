/*     */ package com.sun.security.ntlm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.provider.MD4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NTLM
/*     */ {
/*     */   private final SecretKeyFactory fac;
/*     */   private final Cipher cipher;
/*     */   private final MessageDigest md4;
/*     */   private final Mac hmac;
/*     */   private final MessageDigest md5;
/*  59 */   private static final boolean DEBUG = (System.getProperty("ntlm.debug") != null);
/*     */   
/*     */   final Version v;
/*     */   
/*     */   final boolean writeLM;
/*     */   final boolean writeNTLM;
/*     */   
/*     */   protected NTLM(String paramString) throws NTLMException {
/*  67 */     if (paramString == null) paramString = "LMv2/NTLMv2"; 
/*  68 */     switch (paramString) { case "LM":
/*  69 */         this.v = Version.NTLM; this.writeLM = true; this.writeNTLM = false; break;
/*  70 */       case "NTLM": this.v = Version.NTLM; this.writeLM = false; this.writeNTLM = true; break;
/*  71 */       case "LM/NTLM": this.v = Version.NTLM; this.writeLM = this.writeNTLM = true; break;
/*  72 */       case "NTLM2": this.v = Version.NTLM2; this.writeLM = this.writeNTLM = true; break;
/*  73 */       case "LMv2": this.v = Version.NTLMv2; this.writeLM = true; this.writeNTLM = false; break;
/*  74 */       case "NTLMv2": this.v = Version.NTLMv2; this.writeLM = false; this.writeNTLM = true; break;
/*  75 */       case "LMv2/NTLMv2": this.v = Version.NTLMv2; this.writeLM = this.writeNTLM = true; break;
/*  76 */       default: throw new NTLMException(5, "Unknown version " + paramString); }
/*     */ 
/*     */     
/*     */     try {
/*  80 */       this.fac = SecretKeyFactory.getInstance("DES");
/*  81 */       this.cipher = Cipher.getInstance("DES/ECB/NoPadding");
/*  82 */       this.md4 = MD4.getInstance();
/*  83 */       this.hmac = Mac.getInstance("HmacMD5");
/*  84 */       this.md5 = MessageDigest.getInstance("MD5");
/*  85 */     } catch (NoSuchPaddingException noSuchPaddingException) {
/*  86 */       throw new AssertionError();
/*  87 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  88 */       throw new AssertionError();
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
/*     */ 
/*     */   
/*     */   public void debug(String paramString, Object... paramVarArgs) {
/* 103 */     if (DEBUG) {
/* 104 */       System.out.printf(paramString, paramVarArgs);
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
/*     */   public void debug(byte[] paramArrayOfbyte) {
/* 117 */     if (DEBUG) {
/*     */       try {
/* 119 */         (new HexDumpEncoder()).encodeBuffer(paramArrayOfbyte, System.out);
/* 120 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Reader
/*     */   {
/*     */     private final byte[] internal;
/*     */ 
/*     */ 
/*     */     
/*     */     Reader(byte[] param1ArrayOfbyte) {
/* 134 */       this.internal = param1ArrayOfbyte;
/*     */     }
/*     */     
/*     */     int readInt(int param1Int) throws NTLMException {
/*     */       try {
/* 139 */         return (this.internal[param1Int] & 0xFF) + ((this.internal[param1Int + 1] & 0xFF) << 8) + ((this.internal[param1Int + 2] & 0xFF) << 16) + ((this.internal[param1Int + 3] & 0xFF) << 24);
/*     */ 
/*     */       
/*     */       }
/* 143 */       catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 144 */         throw new NTLMException(1, "Input message incorrect size");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     int readShort(int param1Int) throws NTLMException {
/*     */       try {
/* 151 */         return (this.internal[param1Int] & 0xFF) + (this.internal[param1Int + 1] & 0xFF00);
/*     */       }
/* 153 */       catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 154 */         throw new NTLMException(1, "Input message incorrect size");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     byte[] readBytes(int param1Int1, int param1Int2) throws NTLMException {
/*     */       try {
/* 161 */         return Arrays.copyOfRange(this.internal, param1Int1, param1Int1 + param1Int2);
/* 162 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 163 */         throw new NTLMException(1, "Input message incorrect size");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     byte[] readSecurityBuffer(int param1Int) throws NTLMException {
/* 169 */       int i = readInt(param1Int + 4);
/* 170 */       if (i == 0) return null; 
/*     */       try {
/* 172 */         return Arrays.copyOfRange(this.internal, i, i + 
/* 173 */             readShort(param1Int));
/* 174 */       } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 175 */         throw new NTLMException(1, "Input message incorrect size");
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     String readSecurityBuffer(int param1Int, boolean param1Boolean) throws NTLMException {
/* 182 */       byte[] arrayOfByte = readSecurityBuffer(param1Int);
/*     */       try {
/* 184 */         return (arrayOfByte == null) ? null : new String(arrayOfByte, param1Boolean ? "UnicodeLittleUnmarked" : "ISO8859_1");
/*     */       }
/* 186 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 187 */         throw new NTLMException(1, "Invalid input encoding");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Writer
/*     */   {
/*     */     private byte[] internal;
/*     */ 
/*     */ 
/*     */     
/*     */     private int current;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Writer(int param1Int1, int param1Int2) {
/* 207 */       assert param1Int2 < 256;
/* 208 */       this.internal = new byte[256];
/* 209 */       this.current = param1Int2;
/* 210 */       System.arraycopy(new byte[] { 78, 84, 76, 77, 83, 83, 80, 0, (byte)param1Int1 }, 0, this.internal, 0, 9);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void writeShort(int param1Int1, int param1Int2) {
/* 216 */       this.internal[param1Int1] = (byte)param1Int2;
/* 217 */       this.internal[param1Int1 + 1] = (byte)(param1Int2 >> 8);
/*     */     }
/*     */     
/*     */     void writeInt(int param1Int1, int param1Int2) {
/* 221 */       this.internal[param1Int1] = (byte)param1Int2;
/* 222 */       this.internal[param1Int1 + 1] = (byte)(param1Int2 >> 8);
/* 223 */       this.internal[param1Int1 + 2] = (byte)(param1Int2 >> 16);
/* 224 */       this.internal[param1Int1 + 3] = (byte)(param1Int2 >> 24);
/*     */     }
/*     */     
/*     */     void writeBytes(int param1Int, byte[] param1ArrayOfbyte) {
/* 228 */       System.arraycopy(param1ArrayOfbyte, 0, this.internal, param1Int, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     void writeSecurityBuffer(int param1Int, byte[] param1ArrayOfbyte) {
/* 232 */       if (param1ArrayOfbyte == null) {
/* 233 */         writeShort(param1Int + 4, this.current);
/*     */       } else {
/* 235 */         int i = param1ArrayOfbyte.length;
/* 236 */         if (this.current + i > this.internal.length) {
/* 237 */           this.internal = Arrays.copyOf(this.internal, this.current + i + 256);
/*     */         }
/* 239 */         writeShort(param1Int, i);
/* 240 */         writeShort(param1Int + 2, i);
/* 241 */         writeShort(param1Int + 4, this.current);
/* 242 */         System.arraycopy(param1ArrayOfbyte, 0, this.internal, this.current, i);
/* 243 */         this.current += i;
/*     */       } 
/*     */     }
/*     */     
/*     */     void writeSecurityBuffer(int param1Int, String param1String, boolean param1Boolean) {
/*     */       try {
/* 249 */         writeSecurityBuffer(param1Int, (param1String == null) ? null : param1String.getBytes(param1Boolean ? "UnicodeLittleUnmarked" : "ISO8859_1"));
/*     */       }
/* 251 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */         assert false;
/*     */       } 
/*     */     }
/*     */     
/*     */     byte[] getBytes() {
/* 257 */       return Arrays.copyOf(this.internal, this.current);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] makeDesKey(byte[] paramArrayOfbyte, int paramInt) {
/* 267 */     int[] arrayOfInt = new int[paramArrayOfbyte.length];
/* 268 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 269 */       arrayOfInt[b] = (paramArrayOfbyte[b] < 0) ? (paramArrayOfbyte[b] + 256) : paramArrayOfbyte[b];
/*     */     }
/* 271 */     byte[] arrayOfByte = new byte[8];
/* 272 */     arrayOfByte[0] = (byte)arrayOfInt[paramInt + 0];
/* 273 */     arrayOfByte[1] = (byte)(arrayOfInt[paramInt + 0] << 7 & 0xFF | arrayOfInt[paramInt + 1] >> 1);
/* 274 */     arrayOfByte[2] = (byte)(arrayOfInt[paramInt + 1] << 6 & 0xFF | arrayOfInt[paramInt + 2] >> 2);
/* 275 */     arrayOfByte[3] = (byte)(arrayOfInt[paramInt + 2] << 5 & 0xFF | arrayOfInt[paramInt + 3] >> 3);
/* 276 */     arrayOfByte[4] = (byte)(arrayOfInt[paramInt + 3] << 4 & 0xFF | arrayOfInt[paramInt + 4] >> 4);
/* 277 */     arrayOfByte[5] = (byte)(arrayOfInt[paramInt + 4] << 3 & 0xFF | arrayOfInt[paramInt + 5] >> 5);
/* 278 */     arrayOfByte[6] = (byte)(arrayOfInt[paramInt + 5] << 2 & 0xFF | arrayOfInt[paramInt + 6] >> 6);
/* 279 */     arrayOfByte[7] = (byte)(arrayOfInt[paramInt + 6] << 1 & 0xFF);
/* 280 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   byte[] calcLMHash(byte[] paramArrayOfbyte) {
/* 284 */     byte[] arrayOfByte1 = { 75, 71, 83, 33, 64, 35, 36, 37 };
/* 285 */     byte[] arrayOfByte2 = new byte[14];
/* 286 */     int i = paramArrayOfbyte.length;
/* 287 */     if (i > 14)
/* 288 */       i = 14; 
/* 289 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte2, 0, i);
/*     */     
/*     */     try {
/* 292 */       DESKeySpec dESKeySpec1 = new DESKeySpec(makeDesKey(arrayOfByte2, 0));
/* 293 */       DESKeySpec dESKeySpec2 = new DESKeySpec(makeDesKey(arrayOfByte2, 7));
/*     */       
/* 295 */       SecretKey secretKey1 = this.fac.generateSecret(dESKeySpec1);
/* 296 */       SecretKey secretKey2 = this.fac.generateSecret(dESKeySpec2);
/* 297 */       this.cipher.init(1, secretKey1);
/* 298 */       byte[] arrayOfByte3 = this.cipher.doFinal(arrayOfByte1, 0, 8);
/* 299 */       this.cipher.init(1, secretKey2);
/* 300 */       byte[] arrayOfByte4 = this.cipher.doFinal(arrayOfByte1, 0, 8);
/* 301 */       byte[] arrayOfByte5 = new byte[21];
/* 302 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte5, 0, 8);
/* 303 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 8, 8);
/* 304 */       return arrayOfByte5;
/* 305 */     } catch (InvalidKeyException invalidKeyException) {
/*     */       
/*     */       assert false;
/* 308 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/*     */       
/*     */       assert false;
/* 311 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/*     */       
/*     */       assert false;
/* 314 */     } catch (BadPaddingException badPaddingException) {
/*     */       assert false;
/*     */     } 
/*     */     
/* 318 */     return null;
/*     */   }
/*     */   
/*     */   byte[] calcNTHash(byte[] paramArrayOfbyte) {
/* 322 */     byte[] arrayOfByte1 = this.md4.digest(paramArrayOfbyte);
/* 323 */     byte[] arrayOfByte2 = new byte[21];
/* 324 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 16);
/* 325 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] calcResponse(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*     */     try {
/* 334 */       assert paramArrayOfbyte1.length == 21;
/* 335 */       DESKeySpec dESKeySpec1 = new DESKeySpec(makeDesKey(paramArrayOfbyte1, 0));
/* 336 */       DESKeySpec dESKeySpec2 = new DESKeySpec(makeDesKey(paramArrayOfbyte1, 7));
/* 337 */       DESKeySpec dESKeySpec3 = new DESKeySpec(makeDesKey(paramArrayOfbyte1, 14));
/* 338 */       SecretKey secretKey1 = this.fac.generateSecret(dESKeySpec1);
/* 339 */       SecretKey secretKey2 = this.fac.generateSecret(dESKeySpec2);
/* 340 */       SecretKey secretKey3 = this.fac.generateSecret(dESKeySpec3);
/* 341 */       this.cipher.init(1, secretKey1);
/* 342 */       byte[] arrayOfByte1 = this.cipher.doFinal(paramArrayOfbyte2, 0, 8);
/* 343 */       this.cipher.init(1, secretKey2);
/* 344 */       byte[] arrayOfByte2 = this.cipher.doFinal(paramArrayOfbyte2, 0, 8);
/* 345 */       this.cipher.init(1, secretKey3);
/* 346 */       byte[] arrayOfByte3 = this.cipher.doFinal(paramArrayOfbyte2, 0, 8);
/* 347 */       byte[] arrayOfByte4 = new byte[24];
/* 348 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte4, 0, 8);
/* 349 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 8, 8);
/* 350 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 16, 8);
/* 351 */       return arrayOfByte4;
/* 352 */     } catch (IllegalBlockSizeException illegalBlockSizeException) {
/*     */       assert false;
/* 354 */     } catch (BadPaddingException badPaddingException) {
/*     */       assert false;
/* 356 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/*     */       assert false;
/* 358 */     } catch (InvalidKeyException invalidKeyException) {
/*     */       assert false;
/*     */     } 
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] hmacMD5(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*     */     try {
/* 369 */       SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(paramArrayOfbyte1, 16), "HmacMD5");
/* 370 */       this.hmac.init(secretKeySpec);
/* 371 */       return this.hmac.doFinal(paramArrayOfbyte2);
/* 372 */     } catch (InvalidKeyException invalidKeyException) {
/*     */       assert false;
/* 374 */     } catch (RuntimeException runtimeException) {
/*     */       assert false;
/*     */     } 
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   byte[] calcV2(byte[] paramArrayOfbyte1, String paramString, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/*     */     try {
/* 382 */       byte[] arrayOfByte1 = hmacMD5(paramArrayOfbyte1, paramString
/* 383 */           .getBytes("UnicodeLittleUnmarked"));
/* 384 */       byte[] arrayOfByte2 = new byte[paramArrayOfbyte2.length + 8];
/* 385 */       System.arraycopy(paramArrayOfbyte3, 0, arrayOfByte2, 0, 8);
/* 386 */       System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte2, 8, paramArrayOfbyte2.length);
/* 387 */       byte[] arrayOfByte3 = new byte[16 + paramArrayOfbyte2.length];
/* 388 */       System.arraycopy(hmacMD5(arrayOfByte1, arrayOfByte2), 0, arrayOfByte3, 0, 16);
/* 389 */       System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte3, 16, paramArrayOfbyte2.length);
/* 390 */       return arrayOfByte3;
/* 391 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       assert false;
/*     */       
/* 394 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static byte[] ntlm2LM(byte[] paramArrayOfbyte) {
/* 400 */     return Arrays.copyOf(paramArrayOfbyte, 24);
/*     */   }
/*     */   
/*     */   byte[] ntlm2NTLM(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 404 */     byte[] arrayOfByte1 = Arrays.copyOf(paramArrayOfbyte3, 16);
/* 405 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 8, 8);
/* 406 */     byte[] arrayOfByte2 = Arrays.copyOf(this.md5.digest(arrayOfByte1), 8);
/* 407 */     return calcResponse(paramArrayOfbyte1, arrayOfByte2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] getP1(char[] paramArrayOfchar) {
/*     */     try {
/* 414 */       return (new String(paramArrayOfchar)).toUpperCase(Locale.ENGLISH)
/* 415 */         .getBytes("ISO8859_1");
/* 416 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 417 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static byte[] getP2(char[] paramArrayOfchar) {
/*     */     try {
/* 423 */       return (new String(paramArrayOfchar)).getBytes("UnicodeLittleUnmarked");
/* 424 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 425 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/ntlm/NTLM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */