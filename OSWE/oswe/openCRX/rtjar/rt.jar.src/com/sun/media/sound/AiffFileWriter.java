/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.SequenceInputStream;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AiffFileWriter
/*     */   extends SunFileWriter
/*     */ {
/*     */   private static final int DOUBLE_MANTISSA_LENGTH = 52;
/*     */   private static final int DOUBLE_EXPONENT_LENGTH = 11;
/*     */   private static final long DOUBLE_SIGN_MASK = -9223372036854775808L;
/*     */   private static final long DOUBLE_EXPONENT_MASK = 9218868437227405312L;
/*     */   private static final long DOUBLE_MANTISSA_MASK = 4503599627370495L;
/*     */   private static final int DOUBLE_EXPONENT_OFFSET = 1023;
/*     */   private static final int EXTENDED_EXPONENT_OFFSET = 16383;
/*     */   private static final int EXTENDED_MANTISSA_LENGTH = 63;
/*     */   private static final int EXTENDED_EXPONENT_LENGTH = 15;
/*     */   private static final long EXTENDED_INTEGER_MASK = -9223372036854775808L;
/*     */   
/*     */   public AiffFileWriter() {
/*  59 */     super(new AudioFileFormat.Type[] { AudioFileFormat.Type.AIFF });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream) {
/*  67 */     AudioFileFormat.Type[] arrayOfType = new AudioFileFormat.Type[this.types.length];
/*  68 */     System.arraycopy(this.types, 0, arrayOfType, 0, this.types.length);
/*     */ 
/*     */     
/*  71 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*  72 */     AudioFormat.Encoding encoding = audioFormat.getEncoding();
/*     */     
/*  74 */     if (AudioFormat.Encoding.ALAW.equals(encoding) || AudioFormat.Encoding.ULAW
/*  75 */       .equals(encoding) || AudioFormat.Encoding.PCM_SIGNED
/*  76 */       .equals(encoding) || AudioFormat.Encoding.PCM_UNSIGNED
/*  77 */       .equals(encoding))
/*     */     {
/*  79 */       return arrayOfType;
/*     */     }
/*     */     
/*  82 */     return new AudioFileFormat.Type[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException {
/*  93 */     AiffFileFormat aiffFileFormat = (AiffFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */ 
/*     */     
/*  96 */     if (paramAudioInputStream.getFrameLength() == -1L) {
/*  97 */       throw new IOException("stream length not specified");
/*     */     }
/*     */     
/* 100 */     return writeAiffFile(paramAudioInputStream, aiffFileFormat, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException {
/* 108 */     AiffFileFormat aiffFileFormat = (AiffFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */ 
/*     */     
/* 111 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/* 112 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 4096);
/* 113 */     int i = writeAiffFile(paramAudioInputStream, aiffFileFormat, bufferedOutputStream);
/* 114 */     bufferedOutputStream.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (aiffFileFormat.getByteLength() == -1) {
/*     */ 
/*     */ 
/*     */       
/* 123 */       int j = aiffFileFormat.getFormat().getChannels() * aiffFileFormat.getFormat().getSampleSizeInBits();
/*     */       
/* 125 */       int k = i;
/* 126 */       int m = k - aiffFileFormat.getHeaderSize() + 16;
/* 127 */       long l = (m - 16);
/* 128 */       int n = (int)(l * 8L / j);
/*     */       
/* 130 */       RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
/*     */       
/* 132 */       randomAccessFile.skipBytes(4);
/* 133 */       randomAccessFile.writeInt(k - 8);
/*     */       
/* 135 */       randomAccessFile.skipBytes(4 + aiffFileFormat.getFverChunkSize() + 4 + 4 + 2);
/*     */       
/* 137 */       randomAccessFile.writeInt(n);
/*     */       
/* 139 */       randomAccessFile.skipBytes(16);
/* 140 */       randomAccessFile.writeInt(m - 8);
/*     */       
/* 142 */       randomAccessFile.close();
/*     */     } 
/*     */     
/* 145 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat getAudioFileFormat(AudioFileFormat.Type paramType, AudioInputStream paramAudioInputStream) {
/*     */     int i;
/*     */     byte b;
/* 157 */     AudioFormat audioFormat1 = null;
/* 158 */     AiffFileFormat aiffFileFormat = null;
/* 159 */     AudioFormat.Encoding encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/*     */     
/* 161 */     AudioFormat audioFormat2 = paramAudioInputStream.getFormat();
/* 162 */     AudioFormat.Encoding encoding2 = audioFormat2.getEncoding();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     boolean bool = false;
/*     */     
/* 173 */     if (!this.types[0].equals(paramType)) {
/* 174 */       throw new IllegalArgumentException("File type " + paramType + " not supported.");
/*     */     }
/*     */     
/* 177 */     if (AudioFormat.Encoding.ALAW.equals(encoding2) || AudioFormat.Encoding.ULAW
/* 178 */       .equals(encoding2)) {
/*     */       
/* 180 */       if (audioFormat2.getSampleSizeInBits() == 8) {
/*     */         
/* 182 */         encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/* 183 */         i = 16;
/* 184 */         bool = true;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 189 */         throw new IllegalArgumentException("Encoding " + encoding2 + " supported only for 8-bit data.");
/*     */       } 
/* 191 */     } else if (audioFormat2.getSampleSizeInBits() == 8) {
/*     */       
/* 193 */       encoding1 = AudioFormat.Encoding.PCM_UNSIGNED;
/* 194 */       i = 8;
/*     */     }
/*     */     else {
/*     */       
/* 198 */       encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/* 199 */       i = audioFormat2.getSampleSizeInBits();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     audioFormat1 = new AudioFormat(encoding1, audioFormat2.getSampleRate(), i, audioFormat2.getChannels(), audioFormat2.getFrameSize(), audioFormat2.getFrameRate(), true);
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (paramAudioInputStream.getFrameLength() != -1L) {
/* 213 */       if (bool) {
/* 214 */         b = (int)paramAudioInputStream.getFrameLength() * audioFormat2.getFrameSize() * 2 + 54;
/*     */       } else {
/* 216 */         b = (int)paramAudioInputStream.getFrameLength() * audioFormat2.getFrameSize() + 54;
/*     */       } 
/*     */     } else {
/* 219 */       b = -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     aiffFileFormat = new AiffFileFormat(AudioFileFormat.Type.AIFF, b, audioFormat1, (int)paramAudioInputStream.getFrameLength());
/*     */     
/* 227 */     return aiffFileFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeAiffFile(InputStream paramInputStream, AiffFileFormat paramAiffFileFormat, OutputStream paramOutputStream) throws IOException {
/* 233 */     int i = 0;
/* 234 */     int j = 0;
/* 235 */     InputStream inputStream = getFileStream(paramAiffFileFormat, paramInputStream);
/* 236 */     byte[] arrayOfByte = new byte[4096];
/* 237 */     int k = paramAiffFileFormat.getByteLength();
/*     */     
/* 239 */     while ((i = inputStream.read(arrayOfByte)) >= 0) {
/* 240 */       if (k > 0) {
/* 241 */         if (i < k) {
/* 242 */           paramOutputStream.write(arrayOfByte, 0, i);
/* 243 */           j += i;
/* 244 */           k -= i; continue;
/*     */         } 
/* 246 */         paramOutputStream.write(arrayOfByte, 0, k);
/* 247 */         j += k;
/* 248 */         k = 0;
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 253 */       paramOutputStream.write(arrayOfByte, 0, i);
/* 254 */       j += i;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getFileStream(AiffFileFormat paramAiffFileFormat, InputStream paramInputStream) throws IOException {
/* 265 */     AudioFormat audioFormat1 = paramAiffFileFormat.getFormat();
/* 266 */     AudioFormat audioFormat2 = null;
/* 267 */     AudioFormat.Encoding encoding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     int i = paramAiffFileFormat.getHeaderSize();
/*     */ 
/*     */     
/* 275 */     int j = paramAiffFileFormat.getFverChunkSize();
/*     */     
/* 277 */     int k = paramAiffFileFormat.getCommChunkSize();
/* 278 */     int m = -1;
/* 279 */     int n = -1;
/*     */     
/* 281 */     int i1 = paramAiffFileFormat.getSsndChunkOffset();
/* 282 */     short s1 = (short)audioFormat1.getChannels();
/* 283 */     short s2 = (short)audioFormat1.getSampleSizeInBits();
/* 284 */     int i2 = s1 * s2;
/* 285 */     int i3 = paramAiffFileFormat.getFrameLength();
/* 286 */     long l = -1L;
/* 287 */     if (i3 != -1) {
/* 288 */       l = i3 * i2 / 8L;
/* 289 */       n = (int)l + 16;
/* 290 */       m = (int)l + i;
/*     */     } 
/* 292 */     float f = audioFormat1.getSampleRate();
/* 293 */     int i4 = 1313820229;
/*     */     
/* 295 */     byte[] arrayOfByte = null;
/* 296 */     ByteArrayInputStream byteArrayInputStream = null;
/* 297 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 298 */     DataOutputStream dataOutputStream = null;
/* 299 */     SequenceInputStream sequenceInputStream = null;
/* 300 */     InputStream inputStream = paramInputStream;
/*     */ 
/*     */ 
/*     */     
/* 304 */     if (paramInputStream instanceof AudioInputStream) {
/*     */       
/* 306 */       audioFormat2 = ((AudioInputStream)paramInputStream).getFormat();
/* 307 */       encoding = audioFormat2.getEncoding();
/*     */ 
/*     */ 
/*     */       
/* 311 */       if (AudioFormat.Encoding.PCM_UNSIGNED.equals(encoding) || (AudioFormat.Encoding.PCM_SIGNED
/* 312 */         .equals(encoding) && !audioFormat2.isBigEndian())) {
/*     */ 
/*     */         
/* 315 */         inputStream = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat2
/*     */               
/* 317 */               .getSampleRate(), audioFormat2
/* 318 */               .getSampleSizeInBits(), audioFormat2
/* 319 */               .getChannels(), audioFormat2
/* 320 */               .getFrameSize(), audioFormat2
/* 321 */               .getFrameRate(), true), (AudioInputStream)paramInputStream);
/*     */ 
/*     */       
/*     */       }
/* 325 */       else if (AudioFormat.Encoding.ULAW.equals(encoding) || AudioFormat.Encoding.ALAW
/* 326 */         .equals(encoding)) {
/*     */         
/* 328 */         if (audioFormat2.getSampleSizeInBits() != 8) {
/* 329 */           throw new IllegalArgumentException("unsupported encoding");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 336 */         inputStream = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat2
/*     */               
/* 338 */               .getSampleRate(), audioFormat2
/* 339 */               .getSampleSizeInBits() * 2, audioFormat2
/* 340 */               .getChannels(), audioFormat2
/* 341 */               .getFrameSize() * 2, audioFormat2
/* 342 */               .getFrameRate(), true), (AudioInputStream)paramInputStream);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     byteArrayOutputStream = new ByteArrayOutputStream();
/* 351 */     dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*     */ 
/*     */     
/* 354 */     dataOutputStream.writeInt(1179603533);
/* 355 */     dataOutputStream.writeInt(m - 8);
/* 356 */     dataOutputStream.writeInt(1095321158);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     dataOutputStream.writeInt(1129270605);
/* 365 */     dataOutputStream.writeInt(k - 8);
/* 366 */     dataOutputStream.writeShort(s1);
/* 367 */     dataOutputStream.writeInt(i3);
/* 368 */     dataOutputStream.writeShort(s2);
/* 369 */     write_ieee_extended(dataOutputStream, f);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     dataOutputStream.writeInt(1397968452);
/* 378 */     dataOutputStream.writeInt(n - 8);
/*     */ 
/*     */ 
/*     */     
/* 382 */     dataOutputStream.writeInt(0);
/* 383 */     dataOutputStream.writeInt(0);
/*     */ 
/*     */ 
/*     */     
/* 387 */     dataOutputStream.close();
/* 388 */     arrayOfByte = byteArrayOutputStream.toByteArray();
/* 389 */     byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */     
/* 391 */     sequenceInputStream = new SequenceInputStream(byteArrayInputStream, new SunFileWriter.NoCloseInputStream(this, inputStream));
/*     */ 
/*     */     
/* 394 */     return sequenceInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write_ieee_extended(DataOutputStream paramDataOutputStream, float paramFloat) throws IOException {
/* 428 */     long l1 = Double.doubleToLongBits(paramFloat);
/*     */     
/* 430 */     long l2 = (l1 & Long.MIN_VALUE) >> 63L;
/*     */     
/* 432 */     long l3 = (l1 & 0x7FF0000000000000L) >> 52L;
/*     */     
/* 434 */     long l4 = l1 & 0xFFFFFFFFFFFFFL;
/*     */     
/* 436 */     long l5 = l3 - 1023L + 16383L;
/*     */     
/* 438 */     long l6 = l4 << 11L;
/*     */     
/* 440 */     long l7 = l2 << 15L;
/* 441 */     short s = (short)(int)(l7 | l5);
/* 442 */     long l8 = Long.MIN_VALUE | l6;
/*     */     
/* 444 */     paramDataOutputStream.writeShort(s);
/* 445 */     paramDataOutputStream.writeLong(l8);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AiffFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */