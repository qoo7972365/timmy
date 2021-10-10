/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
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
/*     */ public final class WaveFileReader
/*     */   extends SunFileReader
/*     */ {
/*     */   private static final int MAX_READ_LENGTH = 12;
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*  75 */     AudioFileFormat audioFileFormat = getFMT(paramInputStream, true);
/*     */ 
/*     */     
/*  78 */     paramInputStream.reset();
/*  79 */     return audioFileFormat;
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
/*  94 */     InputStream inputStream = paramURL.openStream();
/*  95 */     AudioFileFormat audioFileFormat = null;
/*     */     try {
/*  97 */       audioFileFormat = getFMT(inputStream, false);
/*     */     } finally {
/*  99 */       inputStream.close();
/*     */     } 
/* 101 */     return audioFileFormat;
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
/* 116 */     AudioFileFormat audioFileFormat = null;
/* 117 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*     */     
/*     */     try {
/* 120 */       audioFileFormat = getFMT(fileInputStream, false);
/*     */     } finally {
/* 122 */       fileInputStream.close();
/*     */     } 
/*     */     
/* 125 */     return audioFileFormat;
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
/*     */   public AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 150 */     AudioFileFormat audioFileFormat = getFMT(paramInputStream, true);
/*     */ 
/*     */ 
/*     */     
/* 154 */     return new AudioInputStream(paramInputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
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
/* 170 */     InputStream inputStream = paramURL.openStream();
/* 171 */     AudioFileFormat audioFileFormat = null;
/*     */     try {
/* 173 */       audioFileFormat = getFMT(inputStream, false);
/*     */     } finally {
/* 175 */       if (audioFileFormat == null) {
/* 176 */         inputStream.close();
/*     */       }
/*     */     } 
/* 179 */     return new AudioInputStream(inputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
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
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 195 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 196 */     AudioFileFormat audioFileFormat = null;
/*     */     
/*     */     try {
/* 199 */       audioFileFormat = getFMT(fileInputStream, false);
/*     */     } finally {
/* 201 */       if (audioFileFormat == null) {
/* 202 */         fileInputStream.close();
/*     */       }
/*     */     } 
/* 205 */     return new AudioInputStream(fileInputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
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
/*     */   private AudioFileFormat getFMT(InputStream paramInputStream, boolean paramBoolean) throws UnsupportedAudioFileException, IOException {
/* 217 */     int i1, i = 0;
/*     */     
/* 219 */     int j = 0;
/* 220 */     short s = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     AudioFormat.Encoding encoding = null;
/*     */     
/* 228 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*     */     
/* 230 */     if (paramBoolean) {
/* 231 */       dataInputStream.mark(12);
/*     */     }
/*     */     
/* 234 */     int k = dataInputStream.readInt();
/* 235 */     int m = rllong(dataInputStream);
/* 236 */     int n = dataInputStream.readInt();
/*     */     
/* 238 */     if (m <= 0) {
/* 239 */       m = -1;
/* 240 */       i1 = -1;
/*     */     } else {
/* 242 */       i1 = m + 8;
/*     */     } 
/*     */     
/* 245 */     if (k != 1380533830 || n != 1463899717) {
/*     */       
/* 247 */       if (paramBoolean) {
/* 248 */         dataInputStream.reset();
/*     */       }
/* 250 */       throw new UnsupportedAudioFileException("not a WAVE file");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/* 258 */         int i4 = dataInputStream.readInt();
/* 259 */         i += true;
/* 260 */         if (i4 == 1718449184) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 265 */         j = rllong(dataInputStream);
/* 266 */         i += true;
/* 267 */         if (j % 2 > 0) j++; 
/* 268 */         i += dataInputStream.skipBytes(j);
/*     */       } 
/* 270 */     } catch (EOFException eOFException) {
/*     */       
/* 272 */       throw new UnsupportedAudioFileException("Not a valid WAV file");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     j = rllong(dataInputStream);
/* 278 */     i += 4;
/*     */ 
/*     */     
/* 281 */     int i2 = i + j;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     s = rlshort(dataInputStream); i += 2;
/*     */     
/* 288 */     if (s == 1) {
/* 289 */       encoding = AudioFormat.Encoding.PCM_SIGNED;
/* 290 */     } else if (s == 6) {
/* 291 */       encoding = AudioFormat.Encoding.ALAW;
/* 292 */     } else if (s == 7) {
/* 293 */       encoding = AudioFormat.Encoding.ULAW;
/*     */     } else {
/*     */       
/* 296 */       throw new UnsupportedAudioFileException("Not a supported WAV file");
/*     */     } 
/*     */     
/* 299 */     short s1 = rlshort(dataInputStream); i += 2;
/* 300 */     if (s1 <= 0) {
/* 301 */       throw new UnsupportedAudioFileException("Invalid number of channels");
/*     */     }
/*     */ 
/*     */     
/* 305 */     long l1 = rllong(dataInputStream); i += 4;
/*     */ 
/*     */     
/* 308 */     long l2 = rllong(dataInputStream); i += 4;
/*     */ 
/*     */     
/* 311 */     short s2 = rlshort(dataInputStream); i += 2;
/*     */ 
/*     */     
/* 314 */     short s3 = rlshort(dataInputStream); i += 2;
/* 315 */     if (s3 <= 0) {
/* 316 */       throw new UnsupportedAudioFileException("Invalid bitsPerSample");
/*     */     }
/*     */ 
/*     */     
/* 320 */     if (s3 == 8 && encoding.equals(AudioFormat.Encoding.PCM_SIGNED)) {
/* 321 */       encoding = AudioFormat.Encoding.PCM_UNSIGNED;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     if (j % 2 != 0) j++;
/*     */ 
/*     */ 
/*     */     
/* 334 */     if (i2 > i) {
/* 335 */       i += dataInputStream.skipBytes(i2 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 340 */     i = 0;
/*     */     try {
/*     */       while (true) {
/* 343 */         int i4 = dataInputStream.readInt();
/* 344 */         i += 4;
/* 345 */         if (i4 == 1684108385) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 350 */         int i5 = rllong(dataInputStream); i += 4;
/* 351 */         if (i5 % 2 > 0) i5++; 
/* 352 */         i += dataInputStream.skipBytes(i5);
/*     */       } 
/* 354 */     } catch (EOFException eOFException) {
/*     */       
/* 356 */       throw new UnsupportedAudioFileException("Not a valid WAV file");
/*     */     } 
/*     */ 
/*     */     
/* 360 */     int i3 = rllong(dataInputStream); i += 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     AudioFormat audioFormat = new AudioFormat(encoding, (float)l1, s3, s1, calculatePCMFrameSize(s3, s1), (float)l1, false);
/*     */ 
/*     */     
/* 370 */     return new WaveFileFormat(AudioFileFormat.Type.WAVE, i1, audioFormat, i3 / audioFormat
/*     */ 
/*     */         
/* 373 */         .getFrameSize());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */