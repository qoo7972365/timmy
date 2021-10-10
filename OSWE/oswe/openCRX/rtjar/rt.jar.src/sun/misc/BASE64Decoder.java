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
/*     */ public class BASE64Decoder
/*     */   extends CharacterDecoder
/*     */ {
/*     */   protected int bytesPerAtom() {
/*  65 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bytesPerLine() {
/*  70 */     return 72;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final byte[] pem_convert_array = new byte[256];
/*     */   static {
/*     */     byte b;
/*  92 */     for (b = 0; b < 'ÿ'; b++) {
/*  93 */       pem_convert_array[b] = -1;
/*     */     }
/*  95 */     for (b = 0; b < pem_array.length; b++) {
/*  96 */       pem_convert_array[pem_array[b]] = (byte)b;
/*     */     }
/*     */   }
/*     */   
/* 100 */   byte[] decode_buffer = new byte[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt) throws IOException {
/* 110 */     byte b1 = -1, b2 = -1, b3 = -1, b4 = -1;
/*     */     
/* 112 */     if (paramInt < 2) {
/* 113 */       throw new CEFormatException("BASE64Decoder: Not enough bytes for an atom.");
/*     */     }
/*     */     while (true) {
/* 116 */       int i = paramPushbackInputStream.read();
/* 117 */       if (i == -1) {
/* 118 */         throw new CEStreamExhausted();
/*     */       }
/* 120 */       if (i != 10 && i != 13) {
/* 121 */         this.decode_buffer[0] = (byte)i;
/*     */         
/* 123 */         i = readFully(paramPushbackInputStream, this.decode_buffer, 1, paramInt - 1);
/* 124 */         if (i == -1) {
/* 125 */           throw new CEStreamExhausted();
/*     */         }
/*     */         
/* 128 */         if (paramInt > 3 && this.decode_buffer[3] == 61) {
/* 129 */           paramInt = 3;
/*     */         }
/* 131 */         if (paramInt > 2 && this.decode_buffer[2] == 61)
/* 132 */           paramInt = 2;  break;
/*     */       } 
/* 134 */     }  switch (paramInt) {
/*     */       case 4:
/* 136 */         b4 = pem_convert_array[this.decode_buffer[3] & 0xFF];
/*     */       
/*     */       case 3:
/* 139 */         b3 = pem_convert_array[this.decode_buffer[2] & 0xFF];
/*     */       
/*     */       case 2:
/* 142 */         b2 = pem_convert_array[this.decode_buffer[1] & 0xFF];
/* 143 */         b1 = pem_convert_array[this.decode_buffer[0] & 0xFF];
/*     */         break;
/*     */     } 
/*     */     
/* 147 */     switch (paramInt) {
/*     */       case 2:
/* 149 */         paramOutputStream.write((byte)(b1 << 2 & 0xFC | b2 >>> 4 & 0x3));
/*     */         break;
/*     */       case 3:
/* 152 */         paramOutputStream.write((byte)(b1 << 2 & 0xFC | b2 >>> 4 & 0x3));
/* 153 */         paramOutputStream.write((byte)(b2 << 4 & 0xF0 | b3 >>> 2 & 0xF));
/*     */         break;
/*     */       case 4:
/* 156 */         paramOutputStream.write((byte)(b1 << 2 & 0xFC | b2 >>> 4 & 0x3));
/* 157 */         paramOutputStream.write((byte)(b2 << 4 & 0xF0 | b3 >>> 2 & 0xF));
/* 158 */         paramOutputStream.write((byte)(b3 << 6 & 0xC0 | b4 & 0x3F));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/BASE64Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */