/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.spi.AudioFileWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WaveFloatFileWriter
/*     */   extends AudioFileWriter
/*     */ {
/*     */   public AudioFileFormat.Type[] getAudioFileTypes() {
/*  47 */     return new AudioFileFormat.Type[] { AudioFileFormat.Type.WAVE };
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream) {
/*  52 */     if (!paramAudioInputStream.getFormat().getEncoding().equals(AudioFormat.Encoding.PCM_FLOAT))
/*  53 */       return new AudioFileFormat.Type[0]; 
/*  54 */     return new AudioFileFormat.Type[] { AudioFileFormat.Type.WAVE };
/*     */   }
/*     */   
/*     */   private void checkFormat(AudioFileFormat.Type paramType, AudioInputStream paramAudioInputStream) {
/*  58 */     if (!AudioFileFormat.Type.WAVE.equals(paramType)) {
/*  59 */       throw new IllegalArgumentException("File type " + paramType + " not supported.");
/*     */     }
/*  61 */     if (!paramAudioInputStream.getFormat().getEncoding().equals(AudioFormat.Encoding.PCM_FLOAT)) {
/*  62 */       throw new IllegalArgumentException("File format " + paramAudioInputStream
/*  63 */           .getFormat() + " not supported.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(AudioInputStream paramAudioInputStream, RIFFWriter paramRIFFWriter) throws IOException {
/*  69 */     RIFFWriter rIFFWriter1 = paramRIFFWriter.writeChunk("fmt ");
/*     */     
/*  71 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*  72 */     rIFFWriter1.writeUnsignedShort(3);
/*  73 */     rIFFWriter1.writeUnsignedShort(audioFormat.getChannels());
/*  74 */     rIFFWriter1.writeUnsignedInt((int)audioFormat.getSampleRate());
/*  75 */     rIFFWriter1.writeUnsignedInt(((int)audioFormat.getFrameRate() * audioFormat
/*  76 */         .getFrameSize()));
/*  77 */     rIFFWriter1.writeUnsignedShort(audioFormat.getFrameSize());
/*  78 */     rIFFWriter1.writeUnsignedShort(audioFormat.getSampleSizeInBits());
/*  79 */     rIFFWriter1.close();
/*  80 */     RIFFWriter rIFFWriter2 = paramRIFFWriter.writeChunk("data");
/*  81 */     byte[] arrayOfByte = new byte[1024];
/*     */     int i;
/*  83 */     while ((i = paramAudioInputStream.read(arrayOfByte, 0, arrayOfByte.length)) != -1)
/*  84 */       rIFFWriter2.write(arrayOfByte, 0, i); 
/*  85 */     rIFFWriter2.close();
/*     */   }
/*     */   
/*     */   private static class NoCloseOutputStream extends OutputStream {
/*     */     final OutputStream out;
/*     */     
/*     */     NoCloseOutputStream(OutputStream param1OutputStream) {
/*  92 */       this.out = param1OutputStream;
/*     */     }
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/*  96 */       this.out.write(param1Int);
/*     */     }
/*     */     
/*     */     public void flush() throws IOException {
/* 100 */       this.out.flush();
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 104 */       this.out.write(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 108 */       this.out.write(param1ArrayOfbyte);
/*     */     }
/*     */   }
/*     */   
/*     */   private AudioInputStream toLittleEndian(AudioInputStream paramAudioInputStream) {
/* 113 */     AudioFormat audioFormat1 = paramAudioInputStream.getFormat();
/*     */ 
/*     */     
/* 116 */     AudioFormat audioFormat2 = new AudioFormat(audioFormat1.getEncoding(), audioFormat1.getSampleRate(), audioFormat1.getSampleSizeInBits(), audioFormat1.getChannels(), audioFormat1.getFrameSize(), audioFormat1.getFrameRate(), false);
/*     */     
/* 118 */     return AudioSystem.getAudioInputStream(audioFormat2, paramAudioInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException {
/* 124 */     checkFormat(paramType, paramAudioInputStream);
/* 125 */     if (paramAudioInputStream.getFormat().isBigEndian())
/* 126 */       paramAudioInputStream = toLittleEndian(paramAudioInputStream); 
/* 127 */     RIFFWriter rIFFWriter = new RIFFWriter(new NoCloseOutputStream(paramOutputStream), "WAVE");
/* 128 */     write(paramAudioInputStream, rIFFWriter);
/* 129 */     int i = (int)rIFFWriter.getFilePointer();
/* 130 */     rIFFWriter.close();
/* 131 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException {
/* 136 */     checkFormat(paramType, paramAudioInputStream);
/* 137 */     if (paramAudioInputStream.getFormat().isBigEndian())
/* 138 */       paramAudioInputStream = toLittleEndian(paramAudioInputStream); 
/* 139 */     RIFFWriter rIFFWriter = new RIFFWriter(paramFile, "WAVE");
/* 140 */     write(paramAudioInputStream, rIFFWriter);
/* 141 */     int i = (int)rIFFWriter.getFilePointer();
/* 142 */     rIFFWriter.close();
/* 143 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveFloatFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */