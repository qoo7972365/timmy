/*     */ package java.util.zip;
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
/*     */ public class GZIPOutputStream
/*     */   extends DeflaterOutputStream
/*     */ {
/*  42 */   protected CRC32 crc = new CRC32();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GZIP_MAGIC = 35615;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TRAILER_SIZE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GZIPOutputStream(OutputStream paramOutputStream, int paramInt) throws IOException {
/*  67 */     this(paramOutputStream, paramInt, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GZIPOutputStream(OutputStream paramOutputStream, int paramInt, boolean paramBoolean) throws IOException {
/*  90 */     super(paramOutputStream, new Deflater(-1, true), paramInt, paramBoolean);
/*     */ 
/*     */     
/*  93 */     this.usesDefaultDeflater = true;
/*  94 */     writeHeader();
/*  95 */     this.crc.reset();
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
/*     */   public GZIPOutputStream(OutputStream paramOutputStream) throws IOException {
/* 109 */     this(paramOutputStream, 512, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public GZIPOutputStream(OutputStream paramOutputStream, boolean paramBoolean) throws IOException {
/* 131 */     this(paramOutputStream, 512, paramBoolean);
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
/*     */   public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 145 */     super.write(paramArrayOfbyte, paramInt1, paramInt2);
/* 146 */     this.crc.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() throws IOException {
/* 156 */     if (!this.def.finished()) {
/* 157 */       this.def.finish();
/* 158 */       while (!this.def.finished()) {
/* 159 */         int i = this.def.deflate(this.buf, 0, this.buf.length);
/* 160 */         if (this.def.finished() && i <= this.buf.length - 8) {
/*     */           
/* 162 */           writeTrailer(this.buf, i);
/* 163 */           i += 8;
/* 164 */           this.out.write(this.buf, 0, i);
/*     */           return;
/*     */         } 
/* 167 */         if (i > 0) {
/* 168 */           this.out.write(this.buf, 0, i);
/*     */         }
/*     */       } 
/*     */       
/* 172 */       byte[] arrayOfByte = new byte[8];
/* 173 */       writeTrailer(arrayOfByte, 0);
/* 174 */       this.out.write(arrayOfByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeHeader() throws IOException {
/* 182 */     this.out.write(new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 });
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
/*     */   private void writeTrailer(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 201 */     writeInt((int)this.crc.getValue(), paramArrayOfbyte, paramInt);
/* 202 */     writeInt(this.def.getTotalIn(), paramArrayOfbyte, paramInt + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeInt(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws IOException {
/* 210 */     writeShort(paramInt1 & 0xFFFF, paramArrayOfbyte, paramInt2);
/* 211 */     writeShort(paramInt1 >> 16 & 0xFFFF, paramArrayOfbyte, paramInt2 + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeShort(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws IOException {
/* 219 */     paramArrayOfbyte[paramInt2] = (byte)(paramInt1 & 0xFF);
/* 220 */     paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 8 & 0xFF);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/GZIPOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */