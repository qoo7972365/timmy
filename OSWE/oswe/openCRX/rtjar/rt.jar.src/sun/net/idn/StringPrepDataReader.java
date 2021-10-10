/*     */ package sun.net.idn;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import sun.text.normalizer.ICUBinary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StringPrepDataReader
/*     */   implements ICUBinary.Authenticate
/*     */ {
/*     */   private DataInputStream dataInputStream;
/*     */   private byte[] unicodeVersion;
/*     */   
/*     */   public StringPrepDataReader(InputStream paramInputStream) throws IOException {
/*  67 */     this.unicodeVersion = ICUBinary.readHeader(paramInputStream, DATA_FORMAT_ID, this);
/*     */ 
/*     */     
/*  70 */     this.dataInputStream = new DataInputStream(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(byte[] paramArrayOfbyte, char[] paramArrayOfchar) throws IOException {
/*  79 */     this.dataInputStream.read(paramArrayOfbyte);
/*     */ 
/*     */     
/*  82 */     for (byte b = 0; b < paramArrayOfchar.length; b++) {
/*  83 */       paramArrayOfchar[b] = this.dataInputStream.readChar();
/*     */     }
/*     */   }
/*     */   
/*     */   public byte[] getDataFormatVersion() {
/*  88 */     return DATA_FORMAT_VERSION;
/*     */   }
/*     */   
/*     */   public boolean isDataVersionAcceptable(byte[] paramArrayOfbyte) {
/*  92 */     return (paramArrayOfbyte[0] == DATA_FORMAT_VERSION[0] && paramArrayOfbyte[2] == DATA_FORMAT_VERSION[2] && paramArrayOfbyte[3] == DATA_FORMAT_VERSION[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] readIndexes(int paramInt) throws IOException {
/*  97 */     int[] arrayOfInt = new int[paramInt];
/*     */     
/*  99 */     for (byte b = 0; b < paramInt; b++) {
/* 100 */       arrayOfInt[b] = this.dataInputStream.readInt();
/*     */     }
/* 102 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public byte[] getUnicodeVersion() {
/* 106 */     return this.unicodeVersion;
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
/* 122 */   private static final byte[] DATA_FORMAT_ID = new byte[] { 83, 80, 82, 80 };
/*     */   
/* 124 */   private static final byte[] DATA_FORMAT_VERSION = new byte[] { 3, 2, 5, 2 };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/idn/StringPrepDataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */