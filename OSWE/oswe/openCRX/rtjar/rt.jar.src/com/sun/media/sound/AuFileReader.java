/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
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
/*     */ public final class AuFileReader
/*     */   extends SunFileReader
/*     */ {
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*     */     byte b2;
/*  74 */     AudioFormat audioFormat = null;
/*  75 */     AuFileFormat auFileFormat = null;
/*  76 */     byte b1 = 28;
/*  77 */     boolean bool1 = false;
/*  78 */     int i = -1;
/*  79 */     int j = -1;
/*  80 */     int k = -1;
/*  81 */     int m = -1;
/*  82 */     int n = -1;
/*  83 */     int i1 = -1;
/*  84 */     int i2 = -1;
/*  85 */     int i3 = -1;
/*     */     
/*  87 */     int i4 = 0;
/*  88 */     boolean bool2 = false;
/*  89 */     AudioFormat.Encoding encoding = null;
/*     */     
/*  91 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*     */     
/*  93 */     dataInputStream.mark(b1);
/*     */     
/*  95 */     i = dataInputStream.readInt();
/*     */     
/*  97 */     if (i != 779316836 || i == 779314176 || i == 1684960046 || i == 6583086) {
/*     */ 
/*     */ 
/*     */       
/* 101 */       dataInputStream.reset();
/* 102 */       throw new UnsupportedAudioFileException("not an AU file");
/*     */     } 
/*     */     
/* 105 */     if (i == 779316836 || i == 779314176) {
/* 106 */       bool1 = true;
/*     */     }
/*     */     
/* 109 */     j = (bool1 == true) ? dataInputStream.readInt() : rllong(dataInputStream); bool2 += true;
/* 110 */     k = (bool1 == true) ? dataInputStream.readInt() : rllong(dataInputStream); bool2 += true;
/* 111 */     m = (bool1 == true) ? dataInputStream.readInt() : rllong(dataInputStream); bool2 += true;
/* 112 */     n = (bool1 == true) ? dataInputStream.readInt() : rllong(dataInputStream); bool2 += true;
/* 113 */     i3 = (bool1 == true) ? dataInputStream.readInt() : rllong(dataInputStream); bool2 += true;
/* 114 */     if (i3 <= 0) {
/* 115 */       dataInputStream.reset();
/* 116 */       throw new UnsupportedAudioFileException("Invalid number of channels");
/*     */     } 
/*     */     
/* 119 */     i1 = n;
/*     */     
/* 121 */     switch (m) {
/*     */       case 1:
/* 123 */         encoding = AudioFormat.Encoding.ULAW;
/* 124 */         b2 = 8;
/*     */         break;
/*     */       case 27:
/* 127 */         encoding = AudioFormat.Encoding.ALAW;
/* 128 */         b2 = 8;
/*     */         break;
/*     */       
/*     */       case 2:
/* 132 */         encoding = AudioFormat.Encoding.PCM_SIGNED;
/* 133 */         b2 = 8;
/*     */         break;
/*     */       case 3:
/* 136 */         encoding = AudioFormat.Encoding.PCM_SIGNED;
/* 137 */         b2 = 16;
/*     */         break;
/*     */       case 4:
/* 140 */         encoding = AudioFormat.Encoding.PCM_SIGNED;
/*     */         
/* 142 */         b2 = 24;
/*     */         break;
/*     */       case 5:
/* 145 */         encoding = AudioFormat.Encoding.PCM_SIGNED;
/*     */         
/* 147 */         b2 = 32;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 175 */         dataInputStream.reset();
/* 176 */         throw new UnsupportedAudioFileException("not a valid AU file");
/*     */     } 
/*     */     
/* 179 */     i2 = calculatePCMFrameSize(b2, i3);
/*     */     
/* 181 */     if (k < 0) {
/* 182 */       i4 = -1;
/*     */     } else {
/*     */       
/* 185 */       i4 = k / i2;
/*     */     } 
/*     */     
/* 188 */     audioFormat = new AudioFormat(encoding, n, b2, i3, i2, i1, bool1);
/*     */ 
/*     */     
/* 191 */     auFileFormat = new AuFileFormat(AudioFileFormat.Type.AU, k + j, audioFormat, i4);
/*     */ 
/*     */     
/* 194 */     dataInputStream.reset();
/* 195 */     return auFileFormat;
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
/*     */   public AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 212 */     InputStream inputStream = null;
/* 213 */     BufferedInputStream bufferedInputStream = null;
/* 214 */     AudioFileFormat audioFileFormat = null;
/* 215 */     Object object = null;
/*     */     
/* 217 */     inputStream = paramURL.openStream();
/*     */     
/*     */     try {
/* 220 */       bufferedInputStream = new BufferedInputStream(inputStream, 4096);
/*     */       
/* 222 */       audioFileFormat = getAudioFileFormat(bufferedInputStream);
/*     */     } finally {
/* 224 */       inputStream.close();
/*     */     } 
/*     */     
/* 227 */     return audioFileFormat;
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
/*     */   public AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 243 */     FileInputStream fileInputStream = null;
/* 244 */     BufferedInputStream bufferedInputStream = null;
/* 245 */     AudioFileFormat audioFileFormat = null;
/* 246 */     Object object = null;
/*     */     
/* 248 */     fileInputStream = new FileInputStream(paramFile);
/*     */     
/*     */     try {
/* 251 */       bufferedInputStream = new BufferedInputStream(fileInputStream, 4096);
/* 252 */       audioFileFormat = getAudioFileFormat(bufferedInputStream);
/*     */     } finally {
/* 254 */       fileInputStream.close();
/*     */     } 
/*     */     
/* 257 */     return audioFileFormat;
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
/* 282 */     DataInputStream dataInputStream = null;
/*     */     
/* 284 */     AudioFileFormat audioFileFormat = null;
/* 285 */     AudioFormat audioFormat = null;
/*     */ 
/*     */     
/* 288 */     audioFileFormat = getAudioFileFormat(paramInputStream);
/*     */ 
/*     */ 
/*     */     
/* 292 */     audioFormat = audioFileFormat.getFormat();
/*     */     
/* 294 */     dataInputStream = new DataInputStream(paramInputStream);
/*     */ 
/*     */ 
/*     */     
/* 298 */     dataInputStream.readInt();
/* 299 */     int i = (audioFormat.isBigEndian() == true) ? dataInputStream.readInt() : rllong(dataInputStream);
/* 300 */     dataInputStream.skipBytes(i - 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     return new AudioInputStream(dataInputStream, audioFormat, audioFileFormat.getFrameLength());
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
/*     */   public AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 323 */     InputStream inputStream = null;
/* 324 */     BufferedInputStream bufferedInputStream = null;
/* 325 */     Object object = null;
/*     */     
/* 327 */     inputStream = paramURL.openStream();
/* 328 */     AudioInputStream audioInputStream = null;
/*     */     try {
/* 330 */       bufferedInputStream = new BufferedInputStream(inputStream, 4096);
/* 331 */       audioInputStream = getAudioInputStream(bufferedInputStream);
/*     */     } finally {
/* 333 */       if (audioInputStream == null) {
/* 334 */         inputStream.close();
/*     */       }
/*     */     } 
/* 337 */     return audioInputStream;
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
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 354 */     FileInputStream fileInputStream = null;
/* 355 */     BufferedInputStream bufferedInputStream = null;
/* 356 */     Object object = null;
/*     */     
/* 358 */     fileInputStream = new FileInputStream(paramFile);
/* 359 */     AudioInputStream audioInputStream = null;
/*     */     
/*     */     try {
/* 362 */       bufferedInputStream = new BufferedInputStream(fileInputStream, 4096);
/* 363 */       audioInputStream = getAudioInputStream(bufferedInputStream);
/*     */     } finally {
/* 365 */       if (audioInputStream == null) {
/* 366 */         fileInputStream.close();
/*     */       }
/*     */     } 
/*     */     
/* 370 */     return audioInputStream;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AuFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */