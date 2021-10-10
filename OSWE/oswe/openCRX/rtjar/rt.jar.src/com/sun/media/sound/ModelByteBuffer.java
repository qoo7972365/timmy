/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModelByteBuffer
/*     */ {
/*  43 */   private ModelByteBuffer root = this;
/*     */   private File file;
/*     */   private long fileoffset;
/*     */   private byte[] buffer;
/*     */   private long offset;
/*     */   private final long len;
/*     */   
/*     */   private class RandomFileInputStream
/*     */     extends InputStream {
/*     */     private final RandomAccessFile raf;
/*     */     private long left;
/*  54 */     private long mark = 0L;
/*  55 */     private long markleft = 0L;
/*     */     
/*     */     RandomFileInputStream() throws IOException {
/*  58 */       this.raf = new RandomAccessFile(ModelByteBuffer.this.root.file, "r");
/*  59 */       this.raf.seek(ModelByteBuffer.this.root.fileoffset + ModelByteBuffer.this.arrayOffset());
/*  60 */       this.left = ModelByteBuffer.this.capacity();
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/*  64 */       if (this.left > 2147483647L)
/*  65 */         return Integer.MAX_VALUE; 
/*  66 */       return (int)this.left;
/*     */     }
/*     */     
/*     */     public synchronized void mark(int param1Int) {
/*     */       try {
/*  71 */         this.mark = this.raf.getFilePointer();
/*  72 */         this.markleft = this.left;
/*  73 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean markSupported() {
/*  79 */       return true;
/*     */     }
/*     */     
/*     */     public synchronized void reset() throws IOException {
/*  83 */       this.raf.seek(this.mark);
/*  84 */       this.left = this.markleft;
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/*  88 */       if (param1Long < 0L)
/*  89 */         return 0L; 
/*  90 */       if (param1Long > this.left)
/*  91 */         param1Long = this.left; 
/*  92 */       long l = this.raf.getFilePointer();
/*  93 */       this.raf.seek(l + param1Long);
/*  94 */       this.left -= param1Long;
/*  95 */       return param1Long;
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  99 */       if (param1Int2 > this.left)
/* 100 */         param1Int2 = (int)this.left; 
/* 101 */       if (this.left == 0L)
/* 102 */         return -1; 
/* 103 */       param1Int2 = this.raf.read(param1ArrayOfbyte, param1Int1, param1Int2);
/* 104 */       if (param1Int2 == -1)
/* 105 */         return -1; 
/* 106 */       this.left -= param1Int2;
/* 107 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 111 */       int i = param1ArrayOfbyte.length;
/* 112 */       if (i > this.left)
/* 113 */         i = (int)this.left; 
/* 114 */       if (this.left == 0L)
/* 115 */         return -1; 
/* 116 */       i = this.raf.read(param1ArrayOfbyte, 0, i);
/* 117 */       if (i == -1)
/* 118 */         return -1; 
/* 119 */       this.left -= i;
/* 120 */       return i;
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 124 */       if (this.left == 0L)
/* 125 */         return -1; 
/* 126 */       int i = this.raf.read();
/* 127 */       if (i == -1)
/* 128 */         return -1; 
/* 129 */       this.left--;
/* 130 */       return i;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 134 */       this.raf.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private ModelByteBuffer(ModelByteBuffer paramModelByteBuffer, long paramLong1, long paramLong2, boolean paramBoolean) {
/* 140 */     this.root = paramModelByteBuffer.root;
/* 141 */     this.offset = 0L;
/* 142 */     long l = paramModelByteBuffer.len;
/* 143 */     if (paramLong1 < 0L)
/* 144 */       paramLong1 = 0L; 
/* 145 */     if (paramLong1 > l)
/* 146 */       paramLong1 = l; 
/* 147 */     if (paramLong2 < 0L)
/* 148 */       paramLong2 = 0L; 
/* 149 */     if (paramLong2 > l)
/* 150 */       paramLong2 = l; 
/* 151 */     if (paramLong1 > paramLong2)
/* 152 */       paramLong1 = paramLong2; 
/* 153 */     this.offset = paramLong1;
/* 154 */     this.len = paramLong2 - paramLong1;
/* 155 */     if (paramBoolean) {
/* 156 */       this.buffer = this.root.buffer;
/* 157 */       if (this.root.file != null) {
/* 158 */         this.file = this.root.file;
/* 159 */         this.root.fileoffset += arrayOffset();
/* 160 */         this.offset = 0L;
/*     */       } else {
/* 162 */         this.offset = arrayOffset();
/* 163 */       }  this.root = this;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelByteBuffer(byte[] paramArrayOfbyte) {
/* 168 */     this.buffer = paramArrayOfbyte;
/* 169 */     this.offset = 0L;
/* 170 */     this.len = paramArrayOfbyte.length;
/*     */   }
/*     */   
/*     */   public ModelByteBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 174 */     this.buffer = paramArrayOfbyte;
/* 175 */     this.offset = paramInt1;
/* 176 */     this.len = paramInt2;
/*     */   }
/*     */   
/*     */   public ModelByteBuffer(File paramFile) {
/* 180 */     this.file = paramFile;
/* 181 */     this.fileoffset = 0L;
/* 182 */     this.len = paramFile.length();
/*     */   }
/*     */   
/*     */   public ModelByteBuffer(File paramFile, long paramLong1, long paramLong2) {
/* 186 */     this.file = paramFile;
/* 187 */     this.fileoffset = paramLong1;
/* 188 */     this.len = paramLong2;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream paramOutputStream) throws IOException {
/* 192 */     if (this.root.file != null && this.root.buffer == null) {
/* 193 */       InputStream inputStream = getInputStream();
/* 194 */       byte[] arrayOfByte = new byte[1024];
/*     */       int i;
/* 196 */       while ((i = inputStream.read(arrayOfByte)) != -1)
/* 197 */         paramOutputStream.write(arrayOfByte, 0, i); 
/*     */     } else {
/* 199 */       paramOutputStream.write(array(), (int)arrayOffset(), (int)capacity());
/*     */     } 
/*     */   }
/*     */   public InputStream getInputStream() {
/* 203 */     if (this.root.file != null && this.root.buffer == null) {
/*     */       try {
/* 205 */         return new RandomFileInputStream();
/* 206 */       } catch (IOException iOException) {
/*     */         
/* 208 */         return null;
/*     */       } 
/*     */     }
/* 211 */     return new ByteArrayInputStream(array(), 
/* 212 */         (int)arrayOffset(), (int)capacity());
/*     */   }
/*     */   
/*     */   public ModelByteBuffer subbuffer(long paramLong) {
/* 216 */     return subbuffer(paramLong, capacity());
/*     */   }
/*     */   
/*     */   public ModelByteBuffer subbuffer(long paramLong1, long paramLong2) {
/* 220 */     return subbuffer(paramLong1, paramLong2, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelByteBuffer subbuffer(long paramLong1, long paramLong2, boolean paramBoolean) {
/* 225 */     return new ModelByteBuffer(this, paramLong1, paramLong2, paramBoolean);
/*     */   }
/*     */   
/*     */   public byte[] array() {
/* 229 */     return this.root.buffer;
/*     */   }
/*     */   
/*     */   public long arrayOffset() {
/* 233 */     if (this.root != this)
/* 234 */       return this.root.arrayOffset() + this.offset; 
/* 235 */     return this.offset;
/*     */   }
/*     */   
/*     */   public long capacity() {
/* 239 */     return this.len;
/*     */   }
/*     */   
/*     */   public ModelByteBuffer getRoot() {
/* 243 */     return this.root;
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 247 */     return this.file;
/*     */   }
/*     */   
/*     */   public long getFilePointer() {
/* 251 */     return this.fileoffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadAll(Collection<ModelByteBuffer> paramCollection) throws IOException {
/* 256 */     File file = null;
/* 257 */     RandomAccessFile randomAccessFile = null;
/*     */     try {
/* 259 */       for (ModelByteBuffer modelByteBuffer : paramCollection) {
/* 260 */         modelByteBuffer = modelByteBuffer.root;
/* 261 */         if (modelByteBuffer.file == null)
/*     */           continue; 
/* 263 */         if (modelByteBuffer.buffer != null)
/*     */           continue; 
/* 265 */         if (file == null || !file.equals(modelByteBuffer.file)) {
/* 266 */           if (randomAccessFile != null) {
/* 267 */             randomAccessFile.close();
/* 268 */             randomAccessFile = null;
/*     */           } 
/* 270 */           file = modelByteBuffer.file;
/* 271 */           randomAccessFile = new RandomAccessFile(modelByteBuffer.file, "r");
/*     */         } 
/* 273 */         randomAccessFile.seek(modelByteBuffer.fileoffset);
/* 274 */         byte[] arrayOfByte = new byte[(int)modelByteBuffer.capacity()];
/*     */         
/* 276 */         int i = 0;
/* 277 */         int j = arrayOfByte.length;
/* 278 */         while (i != j) {
/* 279 */           if (j - i > 65536) {
/* 280 */             randomAccessFile.readFully(arrayOfByte, i, 65536);
/* 281 */             i += 65536; continue;
/*     */           } 
/* 283 */           randomAccessFile.readFully(arrayOfByte, i, j - i);
/* 284 */           i = j;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 289 */         modelByteBuffer.buffer = arrayOfByte;
/* 290 */         modelByteBuffer.offset = 0L;
/*     */       } 
/*     */     } finally {
/* 293 */       if (randomAccessFile != null)
/* 294 */         randomAccessFile.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void load() throws IOException {
/* 299 */     if (this.root != this) {
/* 300 */       this.root.load();
/*     */       return;
/*     */     } 
/* 303 */     if (this.buffer != null)
/*     */       return; 
/* 305 */     if (this.file == null) {
/* 306 */       throw new IllegalStateException("No file associated with this ByteBuffer!");
/*     */     }
/*     */ 
/*     */     
/* 310 */     DataInputStream dataInputStream = new DataInputStream(getInputStream());
/* 311 */     this.buffer = new byte[(int)capacity()];
/* 312 */     this.offset = 0L;
/* 313 */     dataInputStream.readFully(this.buffer);
/* 314 */     dataInputStream.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unload() {
/* 319 */     if (this.root != this) {
/* 320 */       this.root.unload();
/*     */       return;
/*     */     } 
/* 323 */     if (this.file == null) {
/* 324 */       throw new IllegalStateException("No file associated with this ByteBuffer!");
/*     */     }
/*     */     
/* 327 */     this.root.buffer = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */