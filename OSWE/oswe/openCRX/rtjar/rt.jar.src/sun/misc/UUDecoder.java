/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUDecoder
/*     */   extends CharacterDecoder
/*     */ {
/*     */   public String bufferName;
/*     */   public int mode;
/*     */   
/*     */   protected int bytesPerAtom() {
/* 103 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int bytesPerLine() {
/* 111 */     return 45;
/*     */   }
/*     */ 
/*     */   
/* 115 */   private byte[] decoderBuffer = new byte[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 126 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 128 */     for (byte b = 0; b < 4; b++) {
/* 129 */       int m = paramPushbackInputStream.read();
/* 130 */       if (m == -1) {
/* 131 */         throw new CEStreamExhausted();
/*     */       }
/* 133 */       stringBuffer.append((char)m);
/* 134 */       this.decoderBuffer[b] = (byte)(m - 32 & 0x3F);
/*     */     } 
/* 136 */     int i = this.decoderBuffer[0] << 2 & 0xFC | this.decoderBuffer[1] >>> 4 & 0x3;
/* 137 */     int j = this.decoderBuffer[1] << 4 & 0xF0 | this.decoderBuffer[2] >>> 2 & 0xF;
/* 138 */     int k = this.decoderBuffer[2] << 6 & 0xC0 | this.decoderBuffer[3] & 0x3F;
/* 139 */     paramOutputStream.write((byte)(i & 0xFF));
/* 140 */     if (paramInt > 1) {
/* 141 */       paramOutputStream.write((byte)(j & 0xFF));
/*     */     }
/* 143 */     if (paramInt > 2) {
/* 144 */       paramOutputStream.write((byte)(k & 0xFF));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeBufferPrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {
/*     */     int i;
/* 155 */     StringBuffer stringBuffer = new StringBuffer(32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     boolean bool = true;
/*     */     while (true) {
/* 165 */       i = paramPushbackInputStream.read();
/* 166 */       if (i == -1) {
/* 167 */         throw new CEFormatException("UUDecoder: No begin line.");
/*     */       }
/* 169 */       if (i == 98 && bool) {
/* 170 */         i = paramPushbackInputStream.read();
/* 171 */         if (i == 101) {
/*     */           break;
/*     */         }
/*     */       } 
/* 175 */       bool = (i == 10 || i == 13) ? true : false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     while (i != 10 && i != 13) {
/* 182 */       i = paramPushbackInputStream.read();
/* 183 */       if (i == -1) {
/* 184 */         throw new CEFormatException("UUDecoder: No begin line.");
/*     */       }
/* 186 */       if (i != 10 && i != 13) {
/* 187 */         stringBuffer.append((char)i);
/*     */       }
/*     */     } 
/* 190 */     String str = stringBuffer.toString();
/* 191 */     if (str.indexOf(' ') != 3) {
/* 192 */       throw new CEFormatException("UUDecoder: Malformed begin line.");
/*     */     }
/* 194 */     this.mode = Integer.parseInt(str.substring(4, 7));
/* 195 */     this.bufferName = str.substring(str.indexOf(' ', 6) + 1);
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (i == 13) {
/* 200 */       i = paramPushbackInputStream.read();
/* 201 */       if (i != 10 && i != -1) {
/* 202 */         paramPushbackInputStream.unread(i);
/*     */       }
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
/*     */   protected int decodeLinePrefix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {
/* 215 */     int i = paramPushbackInputStream.read();
/* 216 */     if (i == 32) {
/* 217 */       i = paramPushbackInputStream.read();
/* 218 */       i = paramPushbackInputStream.read();
/* 219 */       if (i != 10 && i != -1)
/* 220 */         paramPushbackInputStream.unread(i); 
/* 221 */       throw new CEStreamExhausted();
/* 222 */     }  if (i == -1) {
/* 223 */       throw new CEFormatException("UUDecoder: Short Buffer.");
/*     */     }
/*     */     
/* 226 */     i = i - 32 & 0x3F;
/* 227 */     if (i > bytesPerLine()) {
/* 228 */       throw new CEFormatException("UUDecoder: Bad Line Length.");
/*     */     }
/* 230 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLineSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {
/*     */     while (true) {
/* 242 */       int i = paramPushbackInputStream.read();
/* 243 */       if (i == -1) {
/* 244 */         throw new CEStreamExhausted();
/*     */       }
/* 246 */       if (i == 10) {
/*     */         break;
/*     */       }
/* 249 */       if (i == 13) {
/* 250 */         i = paramPushbackInputStream.read();
/* 251 */         if (i != 10 && i != -1) {
/* 252 */           paramPushbackInputStream.unread(i);
/*     */         }
/*     */         break;
/*     */       } 
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
/*     */   protected void decodeBufferSuffix(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream) throws IOException {
/* 267 */     int i = paramPushbackInputStream.read(this.decoderBuffer);
/* 268 */     if (this.decoderBuffer[0] != 101 || this.decoderBuffer[1] != 110 || this.decoderBuffer[2] != 100)
/*     */     {
/* 270 */       throw new CEFormatException("UUDecoder: Missing 'end' line.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/UUDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */