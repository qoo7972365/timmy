/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.EOFException;
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
/*     */ public final class RIFFReader
/*     */   extends InputStream
/*     */ {
/*     */   private final RIFFReader root;
/*     */   private long filepointer;
/*     */   private final String fourcc;
/*     */   private String riff_type;
/*     */   private long ckSize;
/*     */   private InputStream stream;
/*     */   private long avail;
/*     */   private RIFFReader lastiterator;
/*     */   
/*     */   public RIFFReader(InputStream paramInputStream) throws IOException {
/*     */     int i;
/*  39 */     this.filepointer = 0L;
/*     */     
/*  41 */     this.riff_type = null;
/*  42 */     this.ckSize = 2147483647L;
/*     */     
/*  44 */     this.avail = 2147483647L;
/*  45 */     this.lastiterator = null;
/*     */ 
/*     */ 
/*     */     
/*  49 */     if (paramInputStream instanceof RIFFReader) {
/*  50 */       this.root = ((RIFFReader)paramInputStream).root;
/*     */     } else {
/*  52 */       this.root = this;
/*     */     } 
/*     */     
/*  55 */     this.stream = paramInputStream;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  60 */       i = read();
/*  61 */       if (i == -1) {
/*  62 */         this.fourcc = "";
/*     */ 
/*     */         
/*  65 */         this.riff_type = null;
/*  66 */         this.avail = 0L;
/*     */         return;
/*     */       } 
/*  69 */     } while (i == 0);
/*     */ 
/*     */ 
/*     */     
/*  73 */     byte[] arrayOfByte = new byte[4];
/*  74 */     arrayOfByte[0] = (byte)i;
/*  75 */     readFully(arrayOfByte, 1, 3);
/*  76 */     this.fourcc = new String(arrayOfByte, "ascii");
/*  77 */     this.ckSize = readUnsignedInt();
/*  78 */     this.avail = this.ckSize;
/*     */     
/*  80 */     if (getFormat().equals("RIFF") || getFormat().equals("LIST")) {
/*  81 */       if (this.avail > 2147483647L) {
/*  82 */         throw new RIFFInvalidDataException("Chunk size too big");
/*     */       }
/*  84 */       byte[] arrayOfByte1 = new byte[4];
/*  85 */       readFully(arrayOfByte1);
/*  86 */       this.riff_type = new String(arrayOfByte1, "ascii");
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getFilePointer() throws IOException {
/*  91 */     return this.root.filepointer;
/*     */   }
/*     */   
/*     */   public boolean hasNextChunk() throws IOException {
/*  95 */     if (this.lastiterator != null)
/*  96 */       this.lastiterator.finish(); 
/*  97 */     return (this.avail != 0L);
/*     */   }
/*     */   
/*     */   public RIFFReader nextChunk() throws IOException {
/* 101 */     if (this.lastiterator != null)
/* 102 */       this.lastiterator.finish(); 
/* 103 */     if (this.avail == 0L)
/* 104 */       return null; 
/* 105 */     this.lastiterator = new RIFFReader(this);
/* 106 */     return this.lastiterator;
/*     */   }
/*     */   
/*     */   public String getFormat() {
/* 110 */     return this.fourcc;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 114 */     return this.riff_type;
/*     */   }
/*     */   
/*     */   public long getSize() {
/* 118 */     return this.ckSize;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/* 122 */     if (this.avail == 0L) {
/* 123 */       return -1;
/*     */     }
/* 125 */     int i = this.stream.read();
/* 126 */     if (i == -1) {
/* 127 */       this.avail = 0L;
/* 128 */       return -1;
/*     */     } 
/* 130 */     this.avail--;
/* 131 */     this.filepointer++;
/* 132 */     return i;
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 136 */     if (this.avail == 0L) {
/* 137 */       return -1;
/*     */     }
/* 139 */     if (paramInt2 > this.avail) {
/* 140 */       int j = this.stream.read(paramArrayOfbyte, paramInt1, (int)this.avail);
/* 141 */       if (j != -1)
/* 142 */         this.filepointer += j; 
/* 143 */       this.avail = 0L;
/* 144 */       return j;
/*     */     } 
/* 146 */     int i = this.stream.read(paramArrayOfbyte, paramInt1, paramInt2);
/* 147 */     if (i == -1) {
/* 148 */       this.avail = 0L;
/* 149 */       return -1;
/*     */     } 
/* 151 */     this.avail -= i;
/* 152 */     this.filepointer += i;
/* 153 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfbyte) throws IOException {
/* 158 */     readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 162 */     if (paramInt2 < 0)
/* 163 */       throw new IndexOutOfBoundsException(); 
/* 164 */     while (paramInt2 > 0) {
/* 165 */       int i = read(paramArrayOfbyte, paramInt1, paramInt2);
/* 166 */       if (i < 0)
/* 167 */         throw new EOFException(); 
/* 168 */       if (i == 0)
/* 169 */         Thread.yield(); 
/* 170 */       paramInt1 += i;
/* 171 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/* 177 */     if (paramLong <= 0L || this.avail == 0L) {
/* 178 */       return 0L;
/*     */     }
/*     */     
/* 181 */     long l = Math.min(paramLong, this.avail);
/* 182 */     while (l > 0L) {
/*     */ 
/*     */       
/* 185 */       long l1 = Math.min(this.stream.skip(l), l);
/* 186 */       if (l1 == 0L) {
/*     */         
/* 188 */         Thread.yield();
/* 189 */         if (this.stream.read() == -1) {
/* 190 */           this.avail = 0L;
/*     */           break;
/*     */         } 
/* 193 */         l1 = 1L;
/*     */       } 
/* 195 */       l -= l1;
/* 196 */       this.avail -= l1;
/* 197 */       this.filepointer += l1;
/*     */     } 
/* 199 */     return paramLong - l;
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() {
/* 204 */     return (int)this.avail;
/*     */   }
/*     */   
/*     */   public void finish() throws IOException {
/* 208 */     if (this.avail != 0L) {
/* 209 */       skip(this.avail);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String readString(int paramInt) throws IOException {
/*     */     byte[] arrayOfByte;
/*     */     try {
/* 217 */       arrayOfByte = new byte[paramInt];
/* 218 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 219 */       throw new IOException("Length too big", outOfMemoryError);
/*     */     } 
/* 221 */     readFully(arrayOfByte);
/* 222 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/* 223 */       if (arrayOfByte[b] == 0) {
/* 224 */         return new String(arrayOfByte, 0, b, "ascii");
/*     */       }
/*     */     } 
/* 227 */     return new String(arrayOfByte, "ascii");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() throws IOException {
/* 232 */     int i = read();
/* 233 */     if (i < 0)
/* 234 */       throw new EOFException(); 
/* 235 */     return (byte)i;
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() throws IOException {
/* 240 */     int i = read();
/* 241 */     int j = read();
/* 242 */     if (i < 0)
/* 243 */       throw new EOFException(); 
/* 244 */     if (j < 0)
/* 245 */       throw new EOFException(); 
/* 246 */     return (short)(i | j << 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/* 251 */     int i = read();
/* 252 */     int j = read();
/* 253 */     int k = read();
/* 254 */     int m = read();
/* 255 */     if (i < 0)
/* 256 */       throw new EOFException(); 
/* 257 */     if (j < 0)
/* 258 */       throw new EOFException(); 
/* 259 */     if (k < 0)
/* 260 */       throw new EOFException(); 
/* 261 */     if (m < 0)
/* 262 */       throw new EOFException(); 
/* 263 */     return i + (j << 8) | k << 16 | m << 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/* 268 */     long l1 = read();
/* 269 */     long l2 = read();
/* 270 */     long l3 = read();
/* 271 */     long l4 = read();
/* 272 */     long l5 = read();
/* 273 */     long l6 = read();
/* 274 */     long l7 = read();
/* 275 */     long l8 = read();
/* 276 */     if (l1 < 0L)
/* 277 */       throw new EOFException(); 
/* 278 */     if (l2 < 0L)
/* 279 */       throw new EOFException(); 
/* 280 */     if (l3 < 0L)
/* 281 */       throw new EOFException(); 
/* 282 */     if (l4 < 0L)
/* 283 */       throw new EOFException(); 
/* 284 */     if (l5 < 0L)
/* 285 */       throw new EOFException(); 
/* 286 */     if (l6 < 0L)
/* 287 */       throw new EOFException(); 
/* 288 */     if (l7 < 0L)
/* 289 */       throw new EOFException(); 
/* 290 */     if (l8 < 0L)
/* 291 */       throw new EOFException(); 
/* 292 */     return l1 | l2 << 8L | l3 << 16L | l4 << 24L | l5 << 32L | l6 << 40L | l7 << 48L | l8 << 56L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int readUnsignedByte() throws IOException {
/* 298 */     int i = read();
/* 299 */     if (i < 0)
/* 300 */       throw new EOFException(); 
/* 301 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() throws IOException {
/* 306 */     int i = read();
/* 307 */     int j = read();
/* 308 */     if (i < 0)
/* 309 */       throw new EOFException(); 
/* 310 */     if (j < 0)
/* 311 */       throw new EOFException(); 
/* 312 */     return i | j << 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() throws IOException {
/* 317 */     long l1 = read();
/* 318 */     long l2 = read();
/* 319 */     long l3 = read();
/* 320 */     long l4 = read();
/* 321 */     if (l1 < 0L)
/* 322 */       throw new EOFException(); 
/* 323 */     if (l2 < 0L)
/* 324 */       throw new EOFException(); 
/* 325 */     if (l3 < 0L)
/* 326 */       throw new EOFException(); 
/* 327 */     if (l4 < 0L)
/* 328 */       throw new EOFException(); 
/* 329 */     return l1 + (l2 << 8L) | l3 << 16L | l4 << 24L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 334 */     finish();
/* 335 */     if (this == this.root)
/* 336 */       this.stream.close(); 
/* 337 */     this.stream = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/RIFFReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */