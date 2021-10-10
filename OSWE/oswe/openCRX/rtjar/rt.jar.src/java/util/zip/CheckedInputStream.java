/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.FilterInputStream;
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
/*     */ public class CheckedInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private Checksum cksum;
/*     */   
/*     */   public CheckedInputStream(InputStream paramInputStream, Checksum paramChecksum) {
/*  49 */     super(paramInputStream);
/*  50 */     this.cksum = paramChecksum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  59 */     int i = this.in.read();
/*  60 */     if (i != -1) {
/*  61 */       this.cksum.update(i);
/*     */     }
/*  63 */     return i;
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
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  82 */     paramInt2 = this.in.read(paramArrayOfbyte, paramInt1, paramInt2);
/*  83 */     if (paramInt2 != -1) {
/*  84 */       this.cksum.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*  86 */     return paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/*  96 */     byte[] arrayOfByte = new byte[512];
/*  97 */     long l = 0L;
/*  98 */     while (l < paramLong) {
/*  99 */       long l1 = paramLong - l;
/* 100 */       l1 = read(arrayOfByte, 0, (l1 < arrayOfByte.length) ? (int)l1 : arrayOfByte.length);
/* 101 */       if (l1 == -1L) {
/* 102 */         return l;
/*     */       }
/* 104 */       l += l1;
/*     */     } 
/* 106 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Checksum getChecksum() {
/* 114 */     return this.cksum;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/CheckedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */