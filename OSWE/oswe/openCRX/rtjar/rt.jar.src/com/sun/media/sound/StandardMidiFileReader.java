/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MidiFileFormat;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.spi.MidiFileReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StandardMidiFileReader
/*     */   extends MidiFileReader
/*     */ {
/*     */   private static final int MThd_MAGIC = 1297377380;
/*     */   private static final int bisBufferSize = 1024;
/*     */   
/*     */   public MidiFileFormat getMidiFileFormat(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/*  62 */     return getMidiFileFormatFromStream(paramInputStream, -1, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private MidiFileFormat getMidiFileFormatFromStream(InputStream paramInputStream, int paramInt, SMFParser paramSMFParser) throws InvalidMidiDataException, IOException {
/*     */     DataInputStream dataInputStream;
/*     */     short s;
/*     */     float f;
/*     */     int i;
/*  71 */     byte b = 16;
/*  72 */     byte b1 = -1;
/*     */ 
/*     */     
/*  75 */     if (paramInputStream instanceof DataInputStream) {
/*  76 */       dataInputStream = (DataInputStream)paramInputStream;
/*     */     } else {
/*  78 */       dataInputStream = new DataInputStream(paramInputStream);
/*     */     } 
/*  80 */     if (paramSMFParser == null) {
/*  81 */       dataInputStream.mark(b);
/*     */     } else {
/*  83 */       paramSMFParser.stream = dataInputStream;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  92 */       int j = dataInputStream.readInt();
/*  93 */       if (j != 1297377380)
/*     */       {
/*  95 */         throw new InvalidMidiDataException("not a valid MIDI file");
/*     */       }
/*     */ 
/*     */       
/*  99 */       int k = dataInputStream.readInt() - 6;
/* 100 */       s = dataInputStream.readShort();
/* 101 */       short s1 = dataInputStream.readShort();
/* 102 */       short s2 = dataInputStream.readShort();
/*     */ 
/*     */       
/* 105 */       if (s2 > 0) {
/*     */         
/* 107 */         f = 0.0F;
/* 108 */         i = s2;
/*     */       } else {
/*     */         
/* 111 */         int m = -1 * (s2 >> 8);
/* 112 */         switch (m) {
/*     */           case 24:
/* 114 */             f = 24.0F;
/*     */             break;
/*     */           case 25:
/* 117 */             f = 25.0F;
/*     */             break;
/*     */           case 29:
/* 120 */             f = 29.97F;
/*     */             break;
/*     */           case 30:
/* 123 */             f = 30.0F;
/*     */             break;
/*     */           default:
/* 126 */             throw new InvalidMidiDataException("Unknown frame code: " + m);
/*     */         } 
/*     */         
/* 129 */         i = s2 & 0xFF;
/*     */       } 
/* 131 */       if (paramSMFParser != null) {
/*     */         
/* 133 */         dataInputStream.skip(k);
/* 134 */         paramSMFParser.tracks = s1;
/*     */       } 
/*     */     } finally {
/*     */       
/* 138 */       if (paramSMFParser == null) {
/* 139 */         dataInputStream.reset();
/*     */       }
/*     */     } 
/* 142 */     return new MidiFileFormat(s, f, i, paramInt, b1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MidiFileFormat getMidiFileFormat(URL paramURL) throws InvalidMidiDataException, IOException {
/* 148 */     InputStream inputStream = paramURL.openStream();
/* 149 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);
/* 150 */     MidiFileFormat midiFileFormat = null;
/*     */     try {
/* 152 */       midiFileFormat = getMidiFileFormat(bufferedInputStream);
/*     */     } finally {
/* 154 */       bufferedInputStream.close();
/*     */     } 
/* 156 */     return midiFileFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public MidiFileFormat getMidiFileFormat(File paramFile) throws InvalidMidiDataException, IOException {
/* 161 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 162 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 1024);
/*     */ 
/*     */     
/* 165 */     long l = paramFile.length();
/* 166 */     if (l > 2147483647L) {
/* 167 */       l = -1L;
/*     */     }
/* 169 */     MidiFileFormat midiFileFormat = null;
/*     */     try {
/* 171 */       midiFileFormat = getMidiFileFormatFromStream(bufferedInputStream, (int)l, null);
/*     */     } finally {
/* 173 */       bufferedInputStream.close();
/*     */     } 
/* 175 */     return midiFileFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sequence getSequence(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/* 180 */     SMFParser sMFParser = new SMFParser();
/* 181 */     MidiFileFormat midiFileFormat = getMidiFileFormatFromStream(paramInputStream, -1, sMFParser);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     if (midiFileFormat.getType() != 0 && midiFileFormat.getType() != 1) {
/* 187 */       throw new InvalidMidiDataException("Invalid or unsupported file type: " + midiFileFormat.getType());
/*     */     }
/*     */ 
/*     */     
/* 191 */     Sequence sequence = new Sequence(midiFileFormat.getDivisionType(), midiFileFormat.getResolution());
/*     */ 
/*     */     
/* 194 */     for (byte b = 0; b < sMFParser.tracks && 
/* 195 */       sMFParser.nextTrack(); b++) {
/* 196 */       sMFParser.readTrack(sequence.createTrack());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     return sequence;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Sequence getSequence(URL paramURL) throws InvalidMidiDataException, IOException {
/* 207 */     InputStream inputStream = paramURL.openStream();
/* 208 */     inputStream = new BufferedInputStream(inputStream, 1024);
/* 209 */     Sequence sequence = null;
/*     */     try {
/* 211 */       sequence = getSequence(inputStream);
/*     */     } finally {
/* 213 */       inputStream.close();
/*     */     } 
/* 215 */     return sequence;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sequence getSequence(File paramFile) throws InvalidMidiDataException, IOException {
/* 220 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/* 221 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 1024);
/* 222 */     Sequence sequence = null;
/*     */     try {
/* 224 */       sequence = getSequence(bufferedInputStream);
/*     */     } finally {
/* 226 */       bufferedInputStream.close();
/*     */     } 
/* 228 */     return sequence;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/StandardMidiFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */