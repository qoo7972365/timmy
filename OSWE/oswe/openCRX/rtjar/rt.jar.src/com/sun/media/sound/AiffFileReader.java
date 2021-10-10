/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AiffFileReader
/*     */   extends SunFileReader
/*     */ {
/*     */   private static final int MAX_READ_LENGTH = 8;
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*  76 */     AudioFileFormat audioFileFormat = getCOMM(paramInputStream, true);
/*     */ 
/*     */     
/*  79 */     paramInputStream.reset();
/*  80 */     return audioFileFormat;
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
/*     */   public AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/*  95 */     AudioFileFormat audioFileFormat = null;
/*  96 */     InputStream inputStream = paramURL.openStream();
/*     */     try {
/*  98 */       audioFileFormat = getCOMM(inputStream, false);
/*     */     } finally {
/* 100 */       inputStream.close();
/*     */     } 
/* 102 */     return audioFileFormat;
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
/*     */   public AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 117 */     AudioFileFormat audioFileFormat = null;
/* 118 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*     */     
/*     */     try {
/* 121 */       audioFileFormat = getCOMM(fileInputStream, false);
/*     */     } finally {
/* 123 */       fileInputStream.close();
/*     */     } 
/*     */     
/* 126 */     return audioFileFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 153 */     AudioFileFormat audioFileFormat = getCOMM(paramInputStream, true);
/*     */ 
/*     */ 
/*     */     
/* 157 */     return new AudioInputStream(paramInputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
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
/*     */   public AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 173 */     InputStream inputStream = paramURL.openStream();
/* 174 */     AudioFileFormat audioFileFormat = null;
/*     */     try {
/* 176 */       audioFileFormat = getCOMM(inputStream, false);
/*     */     } finally {
/* 178 */       if (audioFileFormat == null) {
/* 179 */         inputStream.close();
/*     */       }
/*     */     } 
/* 182 */     return new AudioInputStream(inputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
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
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 200 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 201 */     AudioFileFormat audioFileFormat = null;
/*     */     
/*     */     try {
/* 204 */       audioFileFormat = getCOMM(fileInputStream, false);
/*     */     } finally {
/* 206 */       if (audioFileFormat == null) {
/* 207 */         fileInputStream.close();
/*     */       }
/*     */     } 
/* 210 */     return new AudioInputStream(fileInputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat getCOMM(InputStream paramInputStream, boolean paramBoolean) throws UnsupportedAudioFileException, IOException {
/*     */     int i1;
/* 218 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*     */     
/* 220 */     if (paramBoolean) {
/* 221 */       dataInputStream.mark(8);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     int i = 0;
/* 228 */     int j = 0;
/* 229 */     AudioFormat audioFormat = null;
/*     */ 
/*     */     
/* 232 */     int k = dataInputStream.readInt();
/*     */ 
/*     */     
/* 235 */     if (k != 1179603533) {
/*     */       
/* 237 */       if (paramBoolean) {
/* 238 */         dataInputStream.reset();
/*     */       }
/* 240 */       throw new UnsupportedAudioFileException("not an AIFF file");
/*     */     } 
/*     */     
/* 243 */     int m = dataInputStream.readInt();
/* 244 */     int n = dataInputStream.readInt();
/* 245 */     i += true;
/*     */ 
/*     */     
/* 248 */     if (m <= 0) {
/* 249 */       m = -1;
/* 250 */       i1 = -1;
/*     */     } else {
/* 252 */       i1 = m + 8;
/*     */     } 
/*     */ 
/*     */     
/* 256 */     boolean bool1 = false;
/*     */     
/* 258 */     if (n == 1095321155) {
/* 259 */       bool1 = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 264 */     boolean bool2 = false;
/* 265 */     while (!bool2) {
/*     */       int i4, i5; float f; AudioFormat.Encoding encoding;
/* 267 */       int i6, i7, i8, i2 = dataInputStream.readInt();
/* 268 */       int i3 = dataInputStream.readInt();
/* 269 */       i += true;
/*     */       
/* 271 */       byte b = 0;
/*     */ 
/*     */       
/* 274 */       switch (i2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1129270605:
/* 282 */           if ((!bool1 && i3 < 18) || (bool1 && i3 < 22)) {
/* 283 */             throw new UnsupportedAudioFileException("Invalid AIFF/COMM chunksize");
/*     */           }
/*     */           
/* 286 */           i4 = dataInputStream.readUnsignedShort();
/* 287 */           if (i4 <= 0) {
/* 288 */             throw new UnsupportedAudioFileException("Invalid number of channels");
/*     */           }
/* 290 */           dataInputStream.readInt();
/* 291 */           i5 = dataInputStream.readUnsignedShort();
/* 292 */           if (i5 < 1 || i5 > 32) {
/* 293 */             throw new UnsupportedAudioFileException("Invalid AIFF/COMM sampleSize");
/*     */           }
/* 295 */           f = (float)read_ieee_extended(dataInputStream);
/* 296 */           b += true;
/*     */ 
/*     */ 
/*     */           
/* 300 */           encoding = AudioFormat.Encoding.PCM_SIGNED;
/*     */           
/* 302 */           if (bool1) {
/* 303 */             int i9 = dataInputStream.readInt(); b += true;
/* 304 */             switch (i9) {
/*     */               case 1313820229:
/* 306 */                 encoding = AudioFormat.Encoding.PCM_SIGNED;
/*     */                 break;
/*     */               case 1970037111:
/* 309 */                 encoding = AudioFormat.Encoding.ULAW;
/* 310 */                 i5 = 8;
/*     */                 break;
/*     */               default:
/* 313 */                 throw new UnsupportedAudioFileException("Invalid AIFF encoding");
/*     */             } 
/*     */           } 
/* 316 */           i6 = calculatePCMFrameSize(i5, i4);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 321 */           audioFormat = new AudioFormat(encoding, f, i5, i4, i6, f, true);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1397968452:
/* 329 */           i7 = dataInputStream.readInt();
/* 330 */           i8 = dataInputStream.readInt();
/* 331 */           b += true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 344 */           if (i3 < m) {
/* 345 */             j = i3 - b;
/*     */           } else {
/*     */             
/* 348 */             j = m - i + b;
/*     */           } 
/* 350 */           bool2 = true;
/*     */           break;
/*     */       } 
/* 353 */       i += b;
/*     */       
/* 355 */       if (!bool2) {
/* 356 */         i4 = i3 - b;
/* 357 */         if (i4 > 0) {
/* 358 */           i += dataInputStream.skipBytes(i4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 363 */     if (audioFormat == null) {
/* 364 */       throw new UnsupportedAudioFileException("missing COMM chunk");
/*     */     }
/* 366 */     AudioFileFormat.Type type = bool1 ? AudioFileFormat.Type.AIFC : AudioFileFormat.Type.AIFF;
/*     */     
/* 368 */     return new AiffFileFormat(type, i1, audioFormat, j / audioFormat.getFrameSize());
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
/*     */   private void write_ieee_extended(DataOutputStream paramDataOutputStream, double paramDouble) throws IOException {
/* 381 */     char c = '䀎';
/* 382 */     double d = paramDouble;
/*     */ 
/*     */ 
/*     */     
/* 386 */     while (d < 44000.0D) {
/* 387 */       d *= 2.0D;
/* 388 */       c--;
/*     */     } 
/* 390 */     paramDataOutputStream.writeShort(c);
/* 391 */     paramDataOutputStream.writeInt((int)d << 16);
/* 392 */     paramDataOutputStream.writeInt(0);
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
/*     */   private double read_ieee_extended(DataInputStream paramDataInputStream) throws IOException {
/* 405 */     double d1 = 0.0D;
/* 406 */     int i = 0;
/* 407 */     long l1 = 0L, l2 = 0L;
/*     */     
/* 409 */     double d2 = 3.4028234663852886E38D;
/*     */ 
/*     */     
/* 412 */     i = paramDataInputStream.readUnsignedShort();
/*     */     
/* 414 */     long l3 = paramDataInputStream.readUnsignedShort();
/* 415 */     long l4 = paramDataInputStream.readUnsignedShort();
/* 416 */     l1 = l3 << 16L | l4;
/*     */     
/* 418 */     l3 = paramDataInputStream.readUnsignedShort();
/* 419 */     l4 = paramDataInputStream.readUnsignedShort();
/* 420 */     l2 = l3 << 16L | l4;
/*     */     
/* 422 */     if (i == 0 && l1 == 0L && l2 == 0L) {
/* 423 */       d1 = 0.0D;
/*     */     }
/* 425 */     else if (i == 32767) {
/* 426 */       d1 = d2;
/*     */     } else {
/* 428 */       i -= 16383;
/* 429 */       i -= 31;
/* 430 */       d1 = l1 * Math.pow(2.0D, i);
/* 431 */       i -= 32;
/* 432 */       d1 += l2 * Math.pow(2.0D, i);
/*     */     } 
/*     */ 
/*     */     
/* 436 */     return d1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AiffFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */