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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UCEncoder
/*     */   extends CharacterEncoder
/*     */ {
/*     */   protected int bytesPerAtom() {
/*  80 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int bytesPerLine() {
/*  85 */     return 48;
/*     */   }
/*     */ 
/*     */   
/*  89 */   private static final byte[] map_array = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 40, 41 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private byte[] tmp = new byte[2];
/* 103 */   private CRC16 crc = new CRC16();
/*     */ 
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
/*     */     byte b1;
/* 116 */     byte b = paramArrayOfbyte[paramInt1];
/* 117 */     if (paramInt2 == 2) {
/* 118 */       b1 = paramArrayOfbyte[paramInt1 + 1];
/*     */     } else {
/* 120 */       b1 = 0;
/*     */     } 
/* 122 */     this.crc.update(b);
/* 123 */     if (paramInt2 == 2) {
/* 124 */       this.crc.update(b1);
/*     */     }
/* 126 */     paramOutputStream.write(map_array[(b >>> 2 & 0x38) + (b1 >>> 5 & 0x7)]);
/* 127 */     int j = 0, k = 0; int i;
/* 128 */     for (i = 1; i < 256; i *= 2) {
/* 129 */       if ((b & i) != 0) {
/* 130 */         j++;
/*     */       }
/* 132 */       if ((b1 & i) != 0) {
/* 133 */         k++;
/*     */       }
/*     */     } 
/* 136 */     j = (j & 0x1) * 32;
/* 137 */     k = (k & 0x1) * 32;
/* 138 */     paramOutputStream.write(map_array[(b & 0x1F) + j]);
/* 139 */     paramOutputStream.write(map_array[(b1 & 0x1F) + k]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLinePrefix(OutputStream paramOutputStream, int paramInt) throws IOException {
/* 149 */     paramOutputStream.write(42);
/* 150 */     this.crc.value = 0;
/* 151 */     this.tmp[0] = (byte)paramInt;
/* 152 */     this.tmp[1] = (byte)this.sequence;
/* 153 */     this.sequence = this.sequence + 1 & 0xFF;
/* 154 */     encodeAtom(paramOutputStream, this.tmp, 0, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeLineSuffix(OutputStream paramOutputStream) throws IOException {
/* 164 */     this.tmp[0] = (byte)(this.crc.value >>> 8 & 0xFF);
/* 165 */     this.tmp[1] = (byte)(this.crc.value & 0xFF);
/* 166 */     encodeAtom(paramOutputStream, this.tmp, 0, 2);
/* 167 */     this.pStream.println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeBufferPrefix(OutputStream paramOutputStream) throws IOException {
/* 175 */     this.sequence = 0;
/* 176 */     super.encodeBufferPrefix(paramOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/UCEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */