/*     */ package sun.security.jgss;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GSSToken
/*     */ {
/*     */   public static final void writeLittleEndian(int paramInt, byte[] paramArrayOfbyte) {
/*  49 */     writeLittleEndian(paramInt, paramArrayOfbyte, 0);
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
/*     */   public static final void writeLittleEndian(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/*  62 */     paramArrayOfbyte[paramInt2++] = (byte)paramInt1;
/*  63 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 8);
/*  64 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 16);
/*  65 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 24);
/*     */   }
/*     */   
/*     */   public static final void writeBigEndian(int paramInt, byte[] paramArrayOfbyte) {
/*  69 */     writeBigEndian(paramInt, paramArrayOfbyte, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void writeBigEndian(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/*  74 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 24);
/*  75 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 16);
/*  76 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 8);
/*  77 */     paramArrayOfbyte[paramInt2++] = (byte)paramInt1;
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
/*     */   public static final int readLittleEndian(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  91 */     int i = 0;
/*  92 */     byte b = 0;
/*  93 */     while (paramInt2 > 0) {
/*  94 */       i += (paramArrayOfbyte[paramInt1] & 0xFF) << b;
/*  95 */       b += 8;
/*  96 */       paramInt1++;
/*  97 */       paramInt2--;
/*     */     } 
/*  99 */     return i;
/*     */   }
/*     */   
/*     */   public static final int readBigEndian(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 103 */     int i = 0;
/* 104 */     int j = (paramInt2 - 1) * 8;
/* 105 */     while (paramInt2 > 0) {
/* 106 */       i += (paramArrayOfbyte[paramInt1] & 0xFF) << j;
/* 107 */       j -= 8;
/* 108 */       paramInt1++;
/* 109 */       paramInt2--;
/*     */     } 
/* 111 */     return i;
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
/*     */   public static final void writeInt(int paramInt, OutputStream paramOutputStream) throws IOException {
/* 123 */     paramOutputStream.write(paramInt >>> 8);
/* 124 */     paramOutputStream.write(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int writeInt(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 135 */     paramArrayOfbyte[paramInt2++] = (byte)(paramInt1 >>> 8);
/* 136 */     paramArrayOfbyte[paramInt2++] = (byte)paramInt1;
/* 137 */     return paramInt2;
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
/*     */   public static final int readInt(InputStream paramInputStream) throws IOException {
/* 149 */     return (0xFF & paramInputStream.read()) << 8 | 0xFF & paramInputStream
/* 150 */       .read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int readInt(byte[] paramArrayOfbyte, int paramInt) {
/* 161 */     return (0xFF & paramArrayOfbyte[paramInt]) << 8 | 0xFF & paramArrayOfbyte[paramInt + 1];
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
/*     */   public static final void readFully(InputStream paramInputStream, byte[] paramArrayOfbyte) throws IOException {
/* 176 */     readFully(paramInputStream, paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void readFully(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 195 */     while (paramInt2 > 0) {
/* 196 */       int i = paramInputStream.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 197 */       if (i == -1) {
/* 198 */         throw new EOFException("Cannot read all " + paramInt2 + " bytes needed to form this token!");
/*     */       }
/*     */       
/* 201 */       paramInt1 += i;
/* 202 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static final void debug(String paramString) {
/* 207 */     System.err.print(paramString);
/*     */   }
/*     */   
/*     */   public static final String getHexBytes(byte[] paramArrayOfbyte) {
/* 211 */     return getHexBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public static final String getHexBytes(byte[] paramArrayOfbyte, int paramInt) {
/* 215 */     return getHexBytes(paramArrayOfbyte, 0, paramInt);
/*     */   }
/*     */   
/*     */   public static final String getHexBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 219 */     StringBuffer stringBuffer = new StringBuffer();
/* 220 */     for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
/* 221 */       int j = paramArrayOfbyte[i] >> 4 & 0xF;
/* 222 */       int k = paramArrayOfbyte[i] & 0xF;
/*     */       
/* 224 */       stringBuffer.append(Integer.toHexString(j));
/* 225 */       stringBuffer.append(Integer.toHexString(k));
/* 226 */       stringBuffer.append(' ');
/*     */     } 
/* 228 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */