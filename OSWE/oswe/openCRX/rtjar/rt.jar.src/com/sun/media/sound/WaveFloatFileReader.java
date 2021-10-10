/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ import javax.sound.sampled.spi.AudioFileReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WaveFloatFileReader
/*     */   extends AudioFileReader
/*     */ {
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/*  52 */     paramInputStream.mark(200);
/*     */     
/*     */     try {
/*  55 */       audioFileFormat = internal_getAudioFileFormat(paramInputStream);
/*     */     } finally {
/*  57 */       paramInputStream.reset();
/*     */     } 
/*  59 */     return audioFileFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat internal_getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*  65 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/*  66 */     if (!rIFFReader.getFormat().equals("RIFF"))
/*  67 */       throw new UnsupportedAudioFileException(); 
/*  68 */     if (!rIFFReader.getType().equals("WAVE")) {
/*  69 */       throw new UnsupportedAudioFileException();
/*     */     }
/*  71 */     boolean bool1 = false;
/*  72 */     boolean bool2 = false;
/*     */     
/*  74 */     int i = 1;
/*  75 */     long l = 1L;
/*  76 */     int j = 1;
/*  77 */     int k = 1;
/*     */     
/*  79 */     while (rIFFReader.hasNextChunk()) {
/*  80 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*     */       
/*  82 */       if (rIFFReader1.getFormat().equals("fmt ")) {
/*  83 */         bool1 = true;
/*     */         
/*  85 */         int m = rIFFReader1.readUnsignedShort();
/*  86 */         if (m != 3)
/*  87 */           throw new UnsupportedAudioFileException(); 
/*  88 */         i = rIFFReader1.readUnsignedShort();
/*  89 */         l = rIFFReader1.readUnsignedInt();
/*  90 */         rIFFReader1.readUnsignedInt();
/*  91 */         j = rIFFReader1.readUnsignedShort();
/*  92 */         k = rIFFReader1.readUnsignedShort();
/*     */       } 
/*  94 */       if (rIFFReader1.getFormat().equals("data")) {
/*  95 */         bool2 = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 100 */     if (!bool1)
/* 101 */       throw new UnsupportedAudioFileException(); 
/* 102 */     if (!bool2) {
/* 103 */       throw new UnsupportedAudioFileException();
/*     */     }
/* 105 */     AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, (float)l, k, i, j, (float)l, false);
/*     */ 
/*     */     
/* 108 */     return new AudioFileFormat(AudioFileFormat.Type.WAVE, audioFormat, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 117 */     AudioFileFormat audioFileFormat = getAudioFileFormat(paramInputStream);
/* 118 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/* 119 */     if (!rIFFReader.getFormat().equals("RIFF"))
/* 120 */       throw new UnsupportedAudioFileException(); 
/* 121 */     if (!rIFFReader.getType().equals("WAVE"))
/* 122 */       throw new UnsupportedAudioFileException(); 
/* 123 */     while (rIFFReader.hasNextChunk()) {
/* 124 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/* 125 */       if (rIFFReader1.getFormat().equals("data")) {
/* 126 */         return new AudioInputStream(rIFFReader1, audioFileFormat.getFormat(), rIFFReader1
/* 127 */             .getSize());
/*     */       }
/*     */     } 
/* 130 */     throw new UnsupportedAudioFileException();
/*     */   }
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/* 135 */     InputStream inputStream = paramURL.openStream();
/*     */     
/*     */     try {
/* 138 */       audioFileFormat = getAudioFileFormat(new BufferedInputStream(inputStream));
/*     */     } finally {
/* 140 */       inputStream.close();
/*     */     } 
/* 142 */     return audioFileFormat;
/*     */   }
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/* 147 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*     */     
/*     */     try {
/* 150 */       audioFileFormat = getAudioFileFormat(new BufferedInputStream(fileInputStream));
/*     */     } finally {
/* 152 */       fileInputStream.close();
/*     */     } 
/* 154 */     return audioFileFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 159 */     return getAudioInputStream(new BufferedInputStream(paramURL.openStream()));
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 164 */     return getAudioInputStream(new BufferedInputStream(new FileInputStream(paramFile)));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveFloatFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */