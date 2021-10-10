/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiEvent;
/*     */ import javax.sound.midi.MidiMessage;
/*     */ import javax.sound.midi.MidiSystem;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Receiver;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.Track;
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
/*     */ public final class SoftMidiAudioFileReader
/*     */   extends AudioFileReader
/*     */ {
/*  56 */   public static final AudioFileFormat.Type MIDI = new AudioFileFormat.Type("MIDI", "mid");
/*  57 */   private static AudioFormat format = new AudioFormat(44100.0F, 16, 2, true, false);
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(Sequence paramSequence) throws UnsupportedAudioFileException, IOException {
/*  62 */     long l1 = paramSequence.getMicrosecondLength() / 1000000L;
/*  63 */     long l2 = (long)(format.getFrameRate() * (float)(l1 + 4L));
/*  64 */     return new AudioFileFormat(MIDI, format, (int)l2);
/*     */   }
/*     */   
/*     */   public AudioInputStream getAudioInputStream(Sequence paramSequence) throws UnsupportedAudioFileException, IOException {
/*     */     Receiver receiver;
/*  69 */     SoftSynthesizer softSynthesizer = new SoftSynthesizer();
/*     */ 
/*     */     
/*     */     try {
/*  73 */       audioInputStream = softSynthesizer.openStream(format, (Map<String, Object>)null);
/*  74 */       receiver = softSynthesizer.getReceiver();
/*  75 */     } catch (MidiUnavailableException midiUnavailableException) {
/*  76 */       throw new IOException(midiUnavailableException.toString());
/*     */     } 
/*  78 */     float f = paramSequence.getDivisionType();
/*  79 */     Track[] arrayOfTrack = paramSequence.getTracks();
/*  80 */     int[] arrayOfInt = new int[arrayOfTrack.length];
/*  81 */     int i = 500000;
/*  82 */     int j = paramSequence.getResolution();
/*  83 */     long l1 = 0L;
/*  84 */     long l2 = 0L;
/*     */     while (true) {
/*  86 */       MidiEvent midiEvent = null;
/*  87 */       byte b = -1;
/*  88 */       for (byte b1 = 0; b1 < arrayOfTrack.length; b1++) {
/*  89 */         int k = arrayOfInt[b1];
/*  90 */         Track track = arrayOfTrack[b1];
/*  91 */         if (k < track.size()) {
/*  92 */           MidiEvent midiEvent1 = track.get(k);
/*  93 */           if (midiEvent == null || midiEvent1.getTick() < midiEvent.getTick()) {
/*  94 */             midiEvent = midiEvent1;
/*  95 */             b = b1;
/*     */           } 
/*     */         } 
/*     */       } 
/*  99 */       if (b == -1)
/*     */         break; 
/* 101 */       arrayOfInt[b] = arrayOfInt[b] + 1;
/* 102 */       long l = midiEvent.getTick();
/* 103 */       if (f == 0.0F) {
/* 104 */         l2 += (l - l1) * i / j;
/*     */       } else {
/* 106 */         l2 = (long)(l * 1000000.0D * f / j);
/* 107 */       }  l1 = l;
/* 108 */       MidiMessage midiMessage = midiEvent.getMessage();
/* 109 */       if (midiMessage instanceof MetaMessage) {
/* 110 */         if (f == 0.0F && (
/* 111 */           (MetaMessage)midiMessage).getType() == 81) {
/* 112 */           byte[] arrayOfByte = ((MetaMessage)midiMessage).getData();
/* 113 */           if (arrayOfByte.length < 3) {
/* 114 */             throw new UnsupportedAudioFileException();
/*     */           }
/* 116 */           i = (arrayOfByte[0] & 0xFF) << 16 | (arrayOfByte[1] & 0xFF) << 8 | arrayOfByte[2] & 0xFF;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 121 */       receiver.send(midiMessage, l2);
/*     */     } 
/*     */ 
/*     */     
/* 125 */     long l3 = l2 / 1000000L;
/* 126 */     long l4 = (long)(audioInputStream.getFormat().getFrameRate() * (float)(l3 + 4L));
/* 127 */     AudioInputStream audioInputStream = new AudioInputStream(audioInputStream, audioInputStream.getFormat(), l4);
/* 128 */     return audioInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/* 134 */     paramInputStream.mark(200);
/*     */     
/*     */     try {
/* 137 */       sequence = MidiSystem.getSequence(paramInputStream);
/* 138 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 139 */       paramInputStream.reset();
/* 140 */       throw new UnsupportedAudioFileException();
/* 141 */     } catch (IOException iOException) {
/* 142 */       paramInputStream.reset();
/* 143 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 145 */     return getAudioInputStream(sequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/*     */     try {
/* 152 */       sequence = MidiSystem.getSequence(paramURL);
/* 153 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 154 */       throw new UnsupportedAudioFileException();
/* 155 */     } catch (IOException iOException) {
/* 156 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 158 */     return getAudioFileFormat(sequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/*     */     try {
/* 165 */       sequence = MidiSystem.getSequence(paramFile);
/* 166 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 167 */       throw new UnsupportedAudioFileException();
/* 168 */     } catch (IOException iOException) {
/* 169 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 171 */     return getAudioFileFormat(sequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/*     */     try {
/* 178 */       sequence = MidiSystem.getSequence(paramURL);
/* 179 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 180 */       throw new UnsupportedAudioFileException();
/* 181 */     } catch (IOException iOException) {
/* 182 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 184 */     return getAudioInputStream(sequence);
/*     */   }
/*     */   
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/* 189 */     if (!paramFile.getName().toLowerCase().endsWith(".mid")) {
/* 190 */       throw new UnsupportedAudioFileException();
/*     */     }
/*     */     try {
/* 193 */       sequence = MidiSystem.getSequence(paramFile);
/* 194 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 195 */       throw new UnsupportedAudioFileException();
/* 196 */     } catch (IOException iOException) {
/* 197 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 199 */     return getAudioInputStream(sequence);
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*     */     Sequence sequence;
/* 205 */     paramInputStream.mark(200);
/*     */     
/*     */     try {
/* 208 */       sequence = MidiSystem.getSequence(paramInputStream);
/* 209 */     } catch (InvalidMidiDataException invalidMidiDataException) {
/* 210 */       paramInputStream.reset();
/* 211 */       throw new UnsupportedAudioFileException();
/* 212 */     } catch (IOException iOException) {
/* 213 */       paramInputStream.reset();
/* 214 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 216 */     return getAudioFileFormat(sequence);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMidiAudioFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */