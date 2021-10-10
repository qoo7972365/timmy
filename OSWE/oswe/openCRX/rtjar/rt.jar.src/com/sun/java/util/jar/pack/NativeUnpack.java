/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.jar.JarOutputStream;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.ZipEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeUnpack
/*     */ {
/*     */   private long unpackerPtr;
/*     */   private BufferedInputStream in;
/*     */   private int _verbose;
/*     */   private long _byteCount;
/*     */   private int _segCount;
/*     */   private int _fileCount;
/*     */   private long _estByteLimit;
/*     */   private int _estSegLimit;
/*     */   private int _estFileLimit;
/*  78 */   private int _prevPercent = -1;
/*     */   
/*  80 */   private final CRC32 _crc32 = new CRC32();
/*  81 */   private byte[] _buf = new byte[16384];
/*     */   
/*     */   private UnpackerImpl _p200;
/*     */   
/*     */   private PropMap _props;
/*     */ 
/*     */   
/*     */   static {
/*  89 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  92 */             System.loadLibrary("unpack");
/*  93 */             return null;
/*     */           }
/*     */         });
/*  96 */     initIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   NativeUnpack(UnpackerImpl paramUnpackerImpl) {
/* 101 */     this._p200 = paramUnpackerImpl;
/* 102 */     this._props = paramUnpackerImpl.props;
/* 103 */     paramUnpackerImpl._nunp = this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object currentInstance() {
/* 108 */     UnpackerImpl unpackerImpl = (UnpackerImpl)Utils.getTLGlobals();
/* 109 */     return (unpackerImpl == null) ? null : unpackerImpl._nunp;
/*     */   }
/*     */   
/*     */   private synchronized long getUnpackerPtr() {
/* 113 */     return this.unpackerPtr;
/*     */   }
/*     */ 
/*     */   
/*     */   private long readInputFn(ByteBuffer paramByteBuffer, long paramLong) throws IOException {
/* 118 */     if (this.in == null) return 0L; 
/* 119 */     long l1 = (paramByteBuffer.capacity() - paramByteBuffer.position());
/* 120 */     assert paramLong <= l1;
/* 121 */     long l2 = 0L;
/* 122 */     byte b = 0;
/* 123 */     while (l2 < paramLong) {
/* 124 */       b++;
/*     */       
/* 126 */       int i = this._buf.length;
/* 127 */       if (i > l1 - l2)
/* 128 */         i = (int)(l1 - l2); 
/* 129 */       int j = this.in.read(this._buf, 0, i);
/* 130 */       if (j <= 0)
/* 131 */         break;  l2 += j;
/* 132 */       assert l2 <= l1;
/*     */       
/* 134 */       paramByteBuffer.put(this._buf, 0, j);
/*     */     } 
/* 136 */     if (this._verbose > 1)
/* 137 */       Utils.log.fine("readInputFn(" + paramLong + "," + l1 + ") => " + l2 + " steps=" + b); 
/* 138 */     if (l1 > 100L) {
/* 139 */       this._estByteLimit = this._byteCount + l1;
/*     */     } else {
/* 141 */       this._estByteLimit = (this._byteCount + l2) * 20L;
/*     */     } 
/* 143 */     this._byteCount += l2;
/* 144 */     updateProgress();
/* 145 */     return l2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateProgress() {
/* 152 */     double d1 = this._segCount;
/* 153 */     if (this._estByteLimit > 0L && this._byteCount > 0L)
/* 154 */       d1 += this._byteCount / this._estByteLimit; 
/* 155 */     double d2 = this._fileCount;
/*     */ 
/*     */     
/* 158 */     double d3 = 0.33D * d1 / Math.max(this._estSegLimit, 1) + 0.67D * d2 / Math.max(this._estFileLimit, 1);
/* 159 */     int i = (int)Math.round(100.0D * d3);
/* 160 */     if (i > 100) i = 100; 
/* 161 */     if (i > this._prevPercent) {
/* 162 */       this._prevPercent = i;
/* 163 */       this._props.setInteger("unpack.progress", i);
/* 164 */       if (this._verbose > 0)
/* 165 */         Utils.log.info("progress = " + i); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void copyInOption(String paramString) {
/* 170 */     String str = this._props.getProperty(paramString);
/* 171 */     if (this._verbose > 0)
/* 172 */       Utils.log.info("set " + paramString + "=" + str); 
/* 173 */     if (str != null) {
/* 174 */       boolean bool = setOption(paramString, str);
/* 175 */       if (!bool) {
/* 176 */         Utils.log.warning("Invalid option " + paramString + "=" + str);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void run(InputStream paramInputStream, JarOutputStream paramJarOutputStream, ByteBuffer paramByteBuffer) throws IOException {
/* 182 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream);
/* 183 */     this.in = bufferedInputStream;
/* 184 */     this._verbose = this._props.getInteger("com.sun.java.util.jar.pack.verbose");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     boolean bool = "keep".equals(this._props.getProperty("com.sun.java.util.jar.pack.unpack.modification.time", "0")) ? false : this._props.getTime("com.sun.java.util.jar.pack.unpack.modification.time");
/*     */     
/* 191 */     copyInOption("com.sun.java.util.jar.pack.verbose");
/* 192 */     copyInOption("unpack.deflate.hint");
/* 193 */     if (!bool)
/* 194 */       copyInOption("com.sun.java.util.jar.pack.unpack.modification.time"); 
/* 195 */     updateProgress();
/*     */     
/*     */     while (true) {
/* 198 */       long l1 = start(paramByteBuffer, 0L);
/* 199 */       this._byteCount = this._estByteLimit = 0L;
/* 200 */       this._segCount++;
/* 201 */       int i = (int)(l1 >>> 32L);
/* 202 */       int j = (int)(l1 >>> 0L);
/*     */ 
/*     */       
/* 205 */       this._estSegLimit = this._segCount + i;
/* 206 */       double d = (this._fileCount + j);
/* 207 */       this._estFileLimit = (int)(d * this._estSegLimit / this._segCount);
/*     */ 
/*     */ 
/*     */       
/* 211 */       int[] arrayOfInt = { 0, 0, 0, 0 };
/*     */       
/* 213 */       Object[] arrayOfObject = { arrayOfInt, null, null, null };
/*     */       
/* 215 */       while (getNextFile(arrayOfObject)) {
/*     */         
/* 217 */         String str = (String)arrayOfObject[1];
/* 218 */         long l3 = (arrayOfInt[0] << 32L) + (arrayOfInt[1] << 32L >>> 32L);
/*     */ 
/*     */         
/* 221 */         long l4 = bool ? bool : arrayOfInt[2];
/*     */         
/* 223 */         boolean bool1 = (arrayOfInt[3] != 0) ? true : false;
/* 224 */         ByteBuffer byteBuffer1 = (ByteBuffer)arrayOfObject[2];
/* 225 */         ByteBuffer byteBuffer2 = (ByteBuffer)arrayOfObject[3];
/* 226 */         writeEntry(paramJarOutputStream, str, l4, l3, bool1, byteBuffer1, byteBuffer2);
/*     */         
/* 228 */         this._fileCount++;
/* 229 */         updateProgress();
/*     */       } 
/* 231 */       paramByteBuffer = getUnusedInput();
/* 232 */       long l2 = finish();
/* 233 */       if (this._verbose > 0)
/* 234 */         Utils.log.info("bytes consumed = " + l2); 
/* 235 */       if (paramByteBuffer == null && 
/* 236 */         !Utils.isPackMagic(Utils.readMagic(bufferedInputStream))) {
/*     */         break;
/*     */       }
/* 239 */       if (this._verbose > 0 && 
/* 240 */         paramByteBuffer != null) {
/* 241 */         Utils.log.info("unused input = " + paramByteBuffer);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void run(InputStream paramInputStream, JarOutputStream paramJarOutputStream) throws IOException {
/* 247 */     run(paramInputStream, paramJarOutputStream, null);
/*     */   }
/*     */ 
/*     */   
/*     */   void run(File paramFile, JarOutputStream paramJarOutputStream) throws IOException {
/* 252 */     ByteBuffer byteBuffer = null;
/* 253 */     try (FileInputStream null = new FileInputStream(paramFile)) {
/* 254 */       run(fileInputStream, paramJarOutputStream, byteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEntry(JarOutputStream paramJarOutputStream, String paramString, long paramLong1, long paramLong2, boolean paramBoolean, ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws IOException {
/* 262 */     int i = (int)paramLong2;
/* 263 */     if (i != paramLong2) {
/* 264 */       throw new IOException("file too large: " + paramLong2);
/*     */     }
/* 266 */     CRC32 cRC32 = this._crc32;
/*     */     
/* 268 */     if (this._verbose > 1) {
/* 269 */       Utils.log.fine("Writing entry: " + paramString + " size=" + i + (paramBoolean ? " deflated" : ""));
/*     */     }
/*     */     
/* 272 */     if (this._buf.length < i) {
/* 273 */       int k = i;
/* 274 */       while (k < this._buf.length) {
/* 275 */         k <<= 1;
/* 276 */         if (k <= 0) {
/* 277 */           k = i;
/*     */           break;
/*     */         } 
/*     */       } 
/* 281 */       this._buf = new byte[k];
/*     */     } 
/* 283 */     assert this._buf.length >= i;
/*     */     
/* 285 */     int j = 0;
/* 286 */     if (paramByteBuffer1 != null) {
/* 287 */       int k = paramByteBuffer1.capacity();
/* 288 */       paramByteBuffer1.get(this._buf, j, k);
/* 289 */       j += k;
/*     */     } 
/* 291 */     if (paramByteBuffer2 != null) {
/* 292 */       int k = paramByteBuffer2.capacity();
/* 293 */       paramByteBuffer2.get(this._buf, j, k);
/* 294 */       j += k;
/*     */     } 
/* 296 */     while (j < i) {
/*     */       
/* 298 */       int k = this.in.read(this._buf, j, i - j);
/* 299 */       if (k <= 0) throw new IOException("EOF at end of archive"); 
/* 300 */       j += k;
/*     */     } 
/*     */     
/* 303 */     ZipEntry zipEntry = new ZipEntry(paramString);
/* 304 */     zipEntry.setTime(paramLong1 * 1000L);
/*     */     
/* 306 */     if (i == 0) {
/* 307 */       zipEntry.setMethod(0);
/* 308 */       zipEntry.setSize(0L);
/* 309 */       zipEntry.setCrc(0L);
/* 310 */       zipEntry.setCompressedSize(0L);
/* 311 */     } else if (!paramBoolean) {
/* 312 */       zipEntry.setMethod(0);
/* 313 */       zipEntry.setSize(i);
/* 314 */       zipEntry.setCompressedSize(i);
/* 315 */       cRC32.reset();
/* 316 */       cRC32.update(this._buf, 0, i);
/* 317 */       zipEntry.setCrc(cRC32.getValue());
/*     */     } else {
/* 319 */       zipEntry.setMethod(8);
/* 320 */       zipEntry.setSize(i);
/*     */     } 
/*     */     
/* 323 */     paramJarOutputStream.putNextEntry(zipEntry);
/*     */     
/* 325 */     if (i > 0) {
/* 326 */       paramJarOutputStream.write(this._buf, 0, i);
/*     */     }
/* 328 */     paramJarOutputStream.closeEntry();
/* 329 */     if (this._verbose > 0) Utils.log.info("Writing " + Utils.zeString(zipEntry)); 
/*     */   }
/*     */   
/*     */   private static synchronized native void initIDs();
/*     */   
/*     */   private synchronized native long start(ByteBuffer paramByteBuffer, long paramLong);
/*     */   
/*     */   private synchronized native boolean getNextFile(Object[] paramArrayOfObject);
/*     */   
/*     */   private synchronized native ByteBuffer getUnusedInput();
/*     */   
/*     */   private synchronized native long finish();
/*     */   
/*     */   protected synchronized native boolean setOption(String paramString1, String paramString2);
/*     */   
/*     */   protected synchronized native String getOption(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/NativeUnpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */