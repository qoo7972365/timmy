/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CharacterEncoder
/*     */ {
/*     */   protected PrintStream pStream;
/*     */   
/*     */   protected abstract int bytesPerAtom();
/*     */   
/*     */   protected abstract int bytesPerLine();
/*     */   
/*     */   protected void encodeBufferPrefix(OutputStream paramOutputStream) throws IOException {
/*  92 */     this.pStream = new PrintStream(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeBufferSuffix(OutputStream paramOutputStream) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLinePrefix(OutputStream paramOutputStream, int paramInt) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLineSuffix(OutputStream paramOutputStream) throws IOException {
/* 113 */     this.pStream.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void encodeAtom(OutputStream paramOutputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int readFully(InputStream paramInputStream, byte[] paramArrayOfbyte) throws IOException {
/* 126 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 127 */       int i = paramInputStream.read();
/* 128 */       if (i == -1)
/* 129 */         return b; 
/* 130 */       paramArrayOfbyte[b] = (byte)i;
/*     */     } 
/* 132 */     return paramArrayOfbyte.length;
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
/*     */   public void encode(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/* 145 */     byte[] arrayOfByte = new byte[bytesPerLine()];
/*     */     
/* 147 */     encodeBufferPrefix(paramOutputStream);
/*     */     
/*     */     while (true) {
/* 150 */       int j = readFully(paramInputStream, arrayOfByte);
/* 151 */       if (j == 0) {
/*     */         break;
/*     */       }
/* 154 */       encodeLinePrefix(paramOutputStream, j);
/* 155 */       for (int i = 0; i < j; i += bytesPerAtom()) {
/*     */         
/* 157 */         if (i + bytesPerAtom() <= j) {
/* 158 */           encodeAtom(paramOutputStream, arrayOfByte, i, bytesPerAtom());
/*     */         } else {
/* 160 */           encodeAtom(paramOutputStream, arrayOfByte, i, j - i);
/*     */         } 
/*     */       } 
/* 163 */       if (j < bytesPerLine()) {
/*     */         break;
/*     */       }
/* 166 */       encodeLineSuffix(paramOutputStream);
/*     */     } 
/*     */     
/* 169 */     encodeBufferSuffix(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(byte[] paramArrayOfbyte, OutputStream paramOutputStream) throws IOException {
/* 178 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/* 179 */     encode(byteArrayInputStream, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encode(byte[] paramArrayOfbyte) {
/* 187 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 188 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/* 189 */     String str = null;
/*     */     try {
/* 191 */       encode(byteArrayInputStream, byteArrayOutputStream);
/*     */       
/* 193 */       str = byteArrayOutputStream.toString("8859_1");
/* 194 */     } catch (Exception exception) {
/*     */       
/* 196 */       throw new Error("CharacterEncoder.encode internal error");
/*     */     } 
/* 198 */     return str;
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
/*     */   private byte[] getBytes(ByteBuffer paramByteBuffer) {
/* 215 */     byte[] arrayOfByte = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     if (paramByteBuffer.hasArray()) {
/* 222 */       byte[] arrayOfByte1 = paramByteBuffer.array();
/* 223 */       if (arrayOfByte1.length == paramByteBuffer.capacity() && arrayOfByte1.length == paramByteBuffer
/* 224 */         .remaining()) {
/* 225 */         arrayOfByte = arrayOfByte1;
/* 226 */         paramByteBuffer.position(paramByteBuffer.limit());
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     if (arrayOfByte == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       arrayOfByte = new byte[paramByteBuffer.remaining()];
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       paramByteBuffer.get(arrayOfByte);
/*     */     } 
/*     */     
/* 244 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(ByteBuffer paramByteBuffer, OutputStream paramOutputStream) throws IOException {
/* 255 */     byte[] arrayOfByte = getBytes(paramByteBuffer);
/* 256 */     encode(arrayOfByte, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encode(ByteBuffer paramByteBuffer) {
/* 266 */     byte[] arrayOfByte = getBytes(paramByteBuffer);
/* 267 */     return encode(arrayOfByte);
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
/*     */   public void encodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/*     */     int i;
/* 280 */     byte[] arrayOfByte = new byte[bytesPerLine()];
/*     */     
/* 282 */     encodeBufferPrefix(paramOutputStream);
/*     */     
/*     */     do {
/* 285 */       i = readFully(paramInputStream, arrayOfByte);
/* 286 */       if (i == 0) {
/*     */         break;
/*     */       }
/* 289 */       encodeLinePrefix(paramOutputStream, i);
/* 290 */       for (int j = 0; j < i; j += bytesPerAtom()) {
/* 291 */         if (j + bytesPerAtom() <= i) {
/* 292 */           encodeAtom(paramOutputStream, arrayOfByte, j, bytesPerAtom());
/*     */         } else {
/* 294 */           encodeAtom(paramOutputStream, arrayOfByte, j, i - j);
/*     */         } 
/*     */       } 
/* 297 */       encodeLineSuffix(paramOutputStream);
/* 298 */     } while (i >= bytesPerLine());
/*     */ 
/*     */ 
/*     */     
/* 302 */     encodeBufferSuffix(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeBuffer(byte[] paramArrayOfbyte, OutputStream paramOutputStream) throws IOException {
/* 311 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/* 312 */     encodeBuffer(byteArrayInputStream, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encodeBuffer(byte[] paramArrayOfbyte) {
/* 320 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 321 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*     */     try {
/* 323 */       encodeBuffer(byteArrayInputStream, byteArrayOutputStream);
/* 324 */     } catch (Exception exception) {
/*     */       
/* 326 */       throw new Error("CharacterEncoder.encodeBuffer internal error");
/*     */     } 
/* 328 */     return byteArrayOutputStream.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeBuffer(ByteBuffer paramByteBuffer, OutputStream paramOutputStream) throws IOException {
/* 339 */     byte[] arrayOfByte = getBytes(paramByteBuffer);
/* 340 */     encodeBuffer(arrayOfByte, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String encodeBuffer(ByteBuffer paramByteBuffer) {
/* 350 */     byte[] arrayOfByte = getBytes(paramByteBuffer);
/* 351 */     return encodeBuffer(arrayOfByte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/CharacterEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */