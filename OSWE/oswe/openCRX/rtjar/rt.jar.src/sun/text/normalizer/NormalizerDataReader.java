/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NormalizerDataReader
/*     */   implements ICUBinary.Authenticate
/*     */ {
/*     */   private DataInputStream dataInputStream;
/*     */   private byte[] unicodeVersion;
/*     */   
/*     */   protected NormalizerDataReader(InputStream paramInputStream) throws IOException {
/* 303 */     this.unicodeVersion = ICUBinary.readHeader(paramInputStream, DATA_FORMAT_ID, this);
/* 304 */     this.dataInputStream = new DataInputStream(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] readIndexes(int paramInt) throws IOException {
/* 310 */     int[] arrayOfInt = new int[paramInt];
/*     */     
/* 312 */     for (byte b = 0; b < paramInt; b++) {
/* 313 */       arrayOfInt[b] = this.dataInputStream.readInt();
/*     */     }
/* 315 */     return arrayOfInt;
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
/*     */   protected void read(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, char[] paramArrayOfchar1, char[] paramArrayOfchar2) throws IOException {
/* 333 */     this.dataInputStream.readFully(paramArrayOfbyte1);
/*     */ 
/*     */     
/*     */     byte b;
/*     */     
/* 338 */     for (b = 0; b < paramArrayOfchar1.length; b++) {
/* 339 */       paramArrayOfchar1[b] = this.dataInputStream.readChar();
/*     */     }
/*     */ 
/*     */     
/* 343 */     for (b = 0; b < paramArrayOfchar2.length; b++) {
/* 344 */       paramArrayOfchar2[b] = this.dataInputStream.readChar();
/*     */     }
/*     */ 
/*     */     
/* 348 */     this.dataInputStream.readFully(paramArrayOfbyte2);
/*     */ 
/*     */ 
/*     */     
/* 352 */     this.dataInputStream.readFully(paramArrayOfbyte3);
/*     */   }
/*     */   
/*     */   public byte[] getDataFormatVersion() {
/* 356 */     return DATA_FORMAT_VERSION;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDataVersionAcceptable(byte[] paramArrayOfbyte) {
/* 361 */     return (paramArrayOfbyte[0] == DATA_FORMAT_VERSION[0] && paramArrayOfbyte[2] == DATA_FORMAT_VERSION[2] && paramArrayOfbyte[3] == DATA_FORMAT_VERSION[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getUnicodeVersion() {
/* 367 */     return this.unicodeVersion;
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
/* 384 */   private static final byte[] DATA_FORMAT_ID = new byte[] { 78, 111, 114, 109 };
/*     */   
/* 386 */   private static final byte[] DATA_FORMAT_VERSION = new byte[] { 2, 2, 5, 2 };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/NormalizerDataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */