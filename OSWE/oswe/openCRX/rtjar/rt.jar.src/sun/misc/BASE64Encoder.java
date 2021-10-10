/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BASE64Encoder
/*     */   extends CharacterEncoder
/*     */ {
/*     */   protected int bytesPerAtom() {
/*  51 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int bytesPerLine() {
/*  60 */     return 57;
/*     */   }
/*     */ 
/*     */   
/*  64 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeAtom(OutputStream paramOutputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  86 */     if (paramInt2 == 1) {
/*  87 */       byte b = paramArrayOfbyte[paramInt1];
/*  88 */       byte b1 = 0;
/*  89 */       boolean bool = false;
/*  90 */       paramOutputStream.write(pem_array[b >>> 2 & 0x3F]);
/*  91 */       paramOutputStream.write(pem_array[(b << 4 & 0x30) + (b1 >>> 4 & 0xF)]);
/*  92 */       paramOutputStream.write(61);
/*  93 */       paramOutputStream.write(61);
/*  94 */     } else if (paramInt2 == 2) {
/*  95 */       byte b1 = paramArrayOfbyte[paramInt1];
/*  96 */       byte b2 = paramArrayOfbyte[paramInt1 + 1];
/*  97 */       byte b = 0;
/*  98 */       paramOutputStream.write(pem_array[b1 >>> 2 & 0x3F]);
/*  99 */       paramOutputStream.write(pem_array[(b1 << 4 & 0x30) + (b2 >>> 4 & 0xF)]);
/* 100 */       paramOutputStream.write(pem_array[(b2 << 2 & 0x3C) + (b >>> 6 & 0x3)]);
/* 101 */       paramOutputStream.write(61);
/*     */     } else {
/* 103 */       byte b1 = paramArrayOfbyte[paramInt1];
/* 104 */       byte b2 = paramArrayOfbyte[paramInt1 + 1];
/* 105 */       byte b3 = paramArrayOfbyte[paramInt1 + 2];
/* 106 */       paramOutputStream.write(pem_array[b1 >>> 2 & 0x3F]);
/* 107 */       paramOutputStream.write(pem_array[(b1 << 4 & 0x30) + (b2 >>> 4 & 0xF)]);
/* 108 */       paramOutputStream.write(pem_array[(b2 << 2 & 0x3C) + (b3 >>> 6 & 0x3)]);
/* 109 */       paramOutputStream.write(pem_array[b3 & 0x3F]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/BASE64Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */