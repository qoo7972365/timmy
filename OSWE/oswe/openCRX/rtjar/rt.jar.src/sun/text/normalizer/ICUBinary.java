/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ICUBinary
/*     */ {
/*     */   private static final byte MAGIC1 = -38;
/*     */   private static final byte MAGIC2 = 39;
/*     */   private static final byte BIG_ENDIAN_ = 1;
/*     */   private static final byte CHAR_SET_ = 0;
/*     */   private static final byte CHAR_SIZE_ = 2;
/*     */   private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
/*     */   private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
/*     */   
/*     */   public static final byte[] readHeader(InputStream paramInputStream, byte[] paramArrayOfbyte, Authenticate paramAuthenticate) throws IOException {
/* 118 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/* 119 */     char c = dataInputStream.readChar();
/* 120 */     byte b = 2;
/*     */     
/* 122 */     byte b1 = dataInputStream.readByte();
/* 123 */     b++;
/* 124 */     byte b2 = dataInputStream.readByte();
/* 125 */     b++;
/* 126 */     if (b1 != -38 || b2 != 39) {
/* 127 */       throw new IOException("ICU data file error: Not an ICU data file");
/*     */     }
/*     */     
/* 130 */     dataInputStream.readChar();
/* 131 */     b += 2;
/* 132 */     dataInputStream.readChar();
/* 133 */     b += 2;
/* 134 */     byte b3 = dataInputStream.readByte();
/* 135 */     b++;
/* 136 */     byte b4 = dataInputStream.readByte();
/* 137 */     b++;
/* 138 */     byte b5 = dataInputStream.readByte();
/* 139 */     b++;
/* 140 */     dataInputStream.readByte();
/* 141 */     b++;
/*     */     
/* 143 */     byte[] arrayOfByte1 = new byte[4];
/* 144 */     dataInputStream.readFully(arrayOfByte1);
/* 145 */     b += 4;
/* 146 */     byte[] arrayOfByte2 = new byte[4];
/* 147 */     dataInputStream.readFully(arrayOfByte2);
/* 148 */     b += 4;
/* 149 */     byte[] arrayOfByte3 = new byte[4];
/* 150 */     dataInputStream.readFully(arrayOfByte3);
/* 151 */     b += 4;
/* 152 */     if (c < b) {
/* 153 */       throw new IOException("Internal Error: Header size error");
/*     */     }
/* 155 */     dataInputStream.skipBytes(c - b);
/*     */     
/* 157 */     if (b3 != 1 || b4 != 0 || b5 != 2 || 
/*     */       
/* 159 */       !Arrays.equals(paramArrayOfbyte, arrayOfByte1) || (paramAuthenticate != null && 
/*     */       
/* 161 */       !paramAuthenticate.isDataVersionAcceptable(arrayOfByte2))) {
/* 162 */       throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
/*     */     }
/* 164 */     return arrayOfByte3;
/*     */   }
/*     */   
/*     */   public static interface Authenticate {
/*     */     boolean isDataVersionAcceptable(byte[] param1ArrayOfbyte);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/ICUBinary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */