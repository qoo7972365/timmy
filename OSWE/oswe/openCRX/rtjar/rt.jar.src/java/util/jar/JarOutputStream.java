/*     */ package java.util.jar;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JarOutputStream
/*     */   extends ZipOutputStream
/*     */ {
/*     */   private static final int JAR_MAGIC = 51966;
/*     */   private boolean firstEntry;
/*     */   
/*     */   public JarOutputStream(OutputStream paramOutputStream, Manifest paramManifest) throws IOException {
/*  58 */     super(paramOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this.firstEntry = true; if (paramManifest == null) throw new NullPointerException("man");  ZipEntry zipEntry = new ZipEntry("META-INF/MANIFEST.MF"); putNextEntry(zipEntry); paramManifest.write(new BufferedOutputStream(this)); closeEntry(); } public JarOutputStream(OutputStream paramOutputStream) throws IOException { super(paramOutputStream); this.firstEntry = true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasMagic(byte[] paramArrayOfbyte) {
/*     */     try {
/* 120 */       int i = 0;
/* 121 */       while (i < paramArrayOfbyte.length) {
/* 122 */         if (get16(paramArrayOfbyte, i) == 51966) {
/* 123 */           return true;
/*     */         }
/* 125 */         i += get16(paramArrayOfbyte, i + 2) + 4;
/*     */       } 
/* 127 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */ 
/*     */     
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int get16(byte[] paramArrayOfbyte, int paramInt) {
/* 138 */     return Byte.toUnsignedInt(paramArrayOfbyte[paramInt]) | Byte.toUnsignedInt(paramArrayOfbyte[paramInt + 1]) << 8;
/*     */   } public void putNextEntry(ZipEntry paramZipEntry) throws IOException { if (this.firstEntry) { byte[] arrayOfByte = paramZipEntry.getExtra(); if (arrayOfByte == null || !hasMagic(arrayOfByte)) {
/*     */         if (arrayOfByte == null) {
/*     */           arrayOfByte = new byte[4];
/*     */         } else {
/*     */           byte[] arrayOfByte1 = new byte[arrayOfByte.length + 4]; System.arraycopy(arrayOfByte, 0, arrayOfByte1, 4, arrayOfByte.length); arrayOfByte = arrayOfByte1;
/*     */         }  set16(arrayOfByte, 0, 51966); set16(arrayOfByte, 2, 0); paramZipEntry.setExtra(arrayOfByte);
/*     */       }  this.firstEntry = false; }
/* 146 */      super.putNextEntry(paramZipEntry); } private static void set16(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { paramArrayOfbyte[paramInt1 + 0] = (byte)paramInt2;
/* 147 */     paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt2 >> 8); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/jar/JarOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */