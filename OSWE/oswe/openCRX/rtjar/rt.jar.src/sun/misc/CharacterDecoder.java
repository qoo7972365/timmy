/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PushbackInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CharacterDecoder
/*     */ {
/*     */   protected abstract int bytesPerAtom();
/*     */   
/*     */   protected abstract int bytesPerLine();
/*     */   
/*     */   protected void decodeBufferPrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {}
/*     */   
/*     */   protected void decodeBufferSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {}
/*     */   
/*     */   protected int decodeLinePrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {
/* 109 */     return bytesPerLine();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLineSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 126 */     throw new CEStreamExhausted();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int readFully(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 135 */     for (byte b = 0; b < paramInt2; b++) {
/* 136 */       int i = paramInputStream.read();
/* 137 */       if (i == -1)
/* 138 */         return (b == 0) ? -1 : b; 
/* 139 */       paramArrayOfbyte[b + paramInt1] = (byte)i;
/*     */     } 
/* 141 */     return paramInt2;
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
/*     */   public void decodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/* 153 */     int i = 0;
/*     */     
/* 155 */     PushbackInputStream pushbackInputStream = new PushbackInputStream(paramInputStream);
/* 156 */     decodeBufferPrefix(pushbackInputStream, paramOutputStream);
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true)
/* 161 */       { int k = decodeLinePrefix(pushbackInputStream, paramOutputStream); int j;
/* 162 */         for (j = 0; j + bytesPerAtom() < k; j += bytesPerAtom()) {
/* 163 */           decodeAtom(pushbackInputStream, paramOutputStream, bytesPerAtom());
/* 164 */           i += bytesPerAtom();
/*     */         } 
/* 166 */         if (j + bytesPerAtom() == k) {
/* 167 */           decodeAtom(pushbackInputStream, paramOutputStream, bytesPerAtom());
/* 168 */           i += bytesPerAtom();
/*     */         } else {
/* 170 */           decodeAtom(pushbackInputStream, paramOutputStream, k - j);
/* 171 */           i += k - j;
/*     */         } 
/* 173 */         decodeLineSuffix(pushbackInputStream, paramOutputStream); } 
/* 174 */     } catch (CEStreamExhausted cEStreamExhausted) {
/*     */ 
/*     */ 
/*     */       
/* 178 */       decodeBufferSuffix(pushbackInputStream, paramOutputStream);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decodeBuffer(String paramString) throws IOException {
/* 187 */     byte[] arrayOfByte = new byte[paramString.length()];
/*     */ 
/*     */ 
/*     */     
/* 191 */     paramString.getBytes(0, paramString.length(), arrayOfByte, 0);
/* 192 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 193 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 194 */     decodeBuffer(byteArrayInputStream, byteArrayOutputStream);
/* 195 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decodeBuffer(InputStream paramInputStream) throws IOException {
/* 202 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 203 */     decodeBuffer(paramInputStream, byteArrayOutputStream);
/* 204 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer decodeBufferToByteBuffer(String paramString) throws IOException {
/* 212 */     return ByteBuffer.wrap(decodeBuffer(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer decodeBufferToByteBuffer(InputStream paramInputStream) throws IOException {
/* 220 */     return ByteBuffer.wrap(decodeBuffer(paramInputStream));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/CharacterDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */