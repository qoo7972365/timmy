/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.midi.spi.SoundbankReader;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
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
/*     */ public final class AudioFileSoundbankReader
/*     */   extends SoundbankReader
/*     */ {
/*     */   public Soundbank getSoundbank(URL paramURL) throws InvalidMidiDataException, IOException {
/*     */     try {
/*  51 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(paramURL);
/*  52 */       Soundbank soundbank = getSoundbank(audioInputStream);
/*  53 */       audioInputStream.close();
/*  54 */       return soundbank;
/*  55 */     } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
/*  56 */       return null;
/*  57 */     } catch (IOException iOException) {
/*  58 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*  64 */     paramInputStream.mark(512);
/*     */     
/*  66 */     try { AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(paramInputStream);
/*  67 */       Soundbank soundbank = getSoundbank(audioInputStream);
/*  68 */       if (soundbank != null)
/*  69 */         return soundbank;  }
/*  70 */     catch (UnsupportedAudioFileException unsupportedAudioFileException) {  }
/*  71 */     catch (IOException iOException) {}
/*     */     
/*  73 */     paramInputStream.reset();
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(AudioInputStream paramAudioInputStream) throws InvalidMidiDataException, IOException {
/*     */     try {
/*     */       byte[] arrayOfByte;
/*  81 */       if (paramAudioInputStream.getFrameLength() == -1L) {
/*  82 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */         
/*  84 */         byte[] arrayOfByte1 = new byte[1024 - 1024 % paramAudioInputStream.getFormat().getFrameSize()];
/*     */         int i;
/*  86 */         while ((i = paramAudioInputStream.read(arrayOfByte1)) != -1) {
/*  87 */           byteArrayOutputStream.write(arrayOfByte1, 0, i);
/*     */         }
/*  89 */         paramAudioInputStream.close();
/*  90 */         arrayOfByte = byteArrayOutputStream.toByteArray();
/*     */       } else {
/*     */         
/*  93 */         arrayOfByte = new byte[(int)(paramAudioInputStream.getFrameLength() * paramAudioInputStream.getFormat().getFrameSize())];
/*  94 */         (new DataInputStream(paramAudioInputStream)).readFully(arrayOfByte);
/*     */       } 
/*     */       
/*  97 */       ModelByteBufferWavetable modelByteBufferWavetable = new ModelByteBufferWavetable(new ModelByteBuffer(arrayOfByte), paramAudioInputStream.getFormat(), -4800.0F);
/*  98 */       ModelPerformer modelPerformer = new ModelPerformer();
/*  99 */       modelPerformer.getOscillators().add(modelByteBufferWavetable);
/*     */       
/* 101 */       SimpleSoundbank simpleSoundbank = new SimpleSoundbank();
/* 102 */       SimpleInstrument simpleInstrument = new SimpleInstrument();
/* 103 */       simpleInstrument.add(modelPerformer);
/* 104 */       simpleSoundbank.addInstrument(simpleInstrument);
/* 105 */       return simpleSoundbank;
/* 106 */     } catch (Exception exception) {
/* 107 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(File paramFile) throws InvalidMidiDataException, IOException {
/*     */     try {
/* 114 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(paramFile);
/* 115 */       audioInputStream.close();
/*     */       
/* 117 */       ModelByteBufferWavetable modelByteBufferWavetable = new ModelByteBufferWavetable(new ModelByteBuffer(paramFile, 0L, paramFile.length()), -4800.0F);
/* 118 */       ModelPerformer modelPerformer = new ModelPerformer();
/* 119 */       modelPerformer.getOscillators().add(modelByteBufferWavetable);
/* 120 */       SimpleSoundbank simpleSoundbank = new SimpleSoundbank();
/* 121 */       SimpleInstrument simpleInstrument = new SimpleInstrument();
/* 122 */       simpleInstrument.add(modelPerformer);
/* 123 */       simpleSoundbank.addInstrument(simpleInstrument);
/* 124 */       return simpleSoundbank;
/* 125 */     } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
/* 126 */       return null;
/* 127 */     } catch (IOException iOException) {
/* 128 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AudioFileSoundbankReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */