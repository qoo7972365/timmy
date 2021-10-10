/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PipedInputStream;
/*     */ import java.io.PipedOutputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiEvent;
/*     */ import javax.sound.midi.Sequence;
/*     */ import javax.sound.midi.ShortMessage;
/*     */ import javax.sound.midi.SysexMessage;
/*     */ import javax.sound.midi.Track;
/*     */ import javax.sound.midi.spi.MidiFileWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StandardMidiFileWriter
/*     */   extends MidiFileWriter
/*     */ {
/*     */   private static final int MThd_MAGIC = 1297377380;
/*     */   private static final int MTrk_MAGIC = 1297379947;
/*     */   private static final int ONE_BYTE = 1;
/*     */   private static final int TWO_BYTE = 2;
/*     */   private static final int SYSEX = 3;
/*     */   private static final int META = 4;
/*     */   private static final int ERROR = 5;
/*     */   private static final int IGNORE = 6;
/*     */   private static final int MIDI_TYPE_0 = 0;
/*     */   private static final int MIDI_TYPE_1 = 1;
/*     */   private static final int bufferSize = 16384;
/*     */   private DataOutputStream tddos;
/*  79 */   private static final int[] types = new int[] { 0, 1 };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long mask = 127L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getMidiFileTypes() {
/*  89 */     int[] arrayOfInt = new int[types.length];
/*  90 */     System.arraycopy(types, 0, arrayOfInt, 0, types.length);
/*  91 */     return arrayOfInt;
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
/*     */   public int[] getMidiFileTypes(Sequence paramSequence) {
/*     */     int[] arrayOfInt;
/* 104 */     Track[] arrayOfTrack = paramSequence.getTracks();
/*     */     
/* 106 */     if (arrayOfTrack.length == 1) {
/* 107 */       arrayOfInt = new int[2];
/* 108 */       arrayOfInt[0] = 0;
/* 109 */       arrayOfInt[1] = 1;
/*     */     } else {
/* 111 */       arrayOfInt = new int[1];
/* 112 */       arrayOfInt[0] = 1;
/*     */     } 
/*     */     
/* 115 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public boolean isFileTypeSupported(int paramInt) {
/* 119 */     for (byte b = 0; b < types.length; b++) {
/* 120 */       if (paramInt == types[b]) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public int write(Sequence paramSequence, int paramInt, OutputStream paramOutputStream) throws IOException {
/* 128 */     byte[] arrayOfByte = null;
/*     */     
/* 130 */     int i = 0;
/* 131 */     long l = 0L;
/*     */     
/* 133 */     if (!isFileTypeSupported(paramInt, paramSequence)) {
/* 134 */       throw new IllegalArgumentException("Could not write MIDI file");
/*     */     }
/*     */     
/* 137 */     InputStream inputStream = getFileStream(paramInt, paramSequence);
/* 138 */     if (inputStream == null) {
/* 139 */       throw new IllegalArgumentException("Could not write MIDI file");
/*     */     }
/* 141 */     arrayOfByte = new byte[16384];
/*     */     
/* 143 */     while ((i = inputStream.read(arrayOfByte)) >= 0) {
/* 144 */       paramOutputStream.write(arrayOfByte, 0, i);
/* 145 */       l += i;
/*     */     } 
/*     */     
/* 148 */     return (int)l;
/*     */   }
/*     */   
/*     */   public int write(Sequence paramSequence, int paramInt, File paramFile) throws IOException {
/* 152 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/* 153 */     int i = write(paramSequence, paramInt, fileOutputStream);
/* 154 */     fileOutputStream.close();
/* 155 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getFileStream(int paramInt, Sequence paramSequence) throws IOException {
/*     */     int j;
/* 162 */     Track[] arrayOfTrack = paramSequence.getTracks();
/* 163 */     byte b1 = 0;
/* 164 */     byte b2 = 14;
/* 165 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/* 169 */     PipedOutputStream pipedOutputStream = null;
/* 170 */     DataOutputStream dataOutputStream = null;
/* 171 */     PipedInputStream pipedInputStream = null;
/*     */     
/* 173 */     InputStream[] arrayOfInputStream = null;
/* 174 */     InputStream inputStream = null;
/* 175 */     SequenceInputStream sequenceInputStream = null;
/*     */ 
/*     */     
/* 178 */     if (paramInt == 0) {
/* 179 */       if (arrayOfTrack.length != 1) {
/* 180 */         return null;
/*     */       }
/* 182 */     } else if (paramInt == 1) {
/* 183 */       if (arrayOfTrack.length < 1) {
/* 184 */         return null;
/*     */       }
/*     */     }
/* 187 */     else if (arrayOfTrack.length == 1) {
/* 188 */       paramInt = 0;
/* 189 */     } else if (arrayOfTrack.length > 1) {
/* 190 */       paramInt = 1;
/*     */     } else {
/* 192 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     arrayOfInputStream = new InputStream[arrayOfTrack.length];
/* 201 */     byte b3 = 0; byte b4;
/* 202 */     for (b4 = 0; b4 < arrayOfTrack.length; b4++) {
/*     */       try {
/* 204 */         arrayOfInputStream[b3] = writeTrack(arrayOfTrack[b4], paramInt);
/* 205 */         b3++;
/* 206 */       } catch (InvalidMidiDataException invalidMidiDataException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (b3 == 1) {
/* 214 */       inputStream = arrayOfInputStream[0];
/* 215 */     } else if (b3 > 1) {
/* 216 */       inputStream = arrayOfInputStream[0];
/* 217 */       for (b4 = 1; b4 < arrayOfTrack.length; b4++) {
/*     */ 
/*     */         
/* 220 */         if (arrayOfInputStream[b4] != null) {
/* 221 */           inputStream = new SequenceInputStream(inputStream, arrayOfInputStream[b4]);
/*     */         }
/*     */       } 
/*     */     } else {
/* 225 */       throw new IllegalArgumentException("invalid MIDI data in sequence");
/*     */     } 
/*     */ 
/*     */     
/* 229 */     pipedOutputStream = new PipedOutputStream();
/* 230 */     dataOutputStream = new DataOutputStream(pipedOutputStream);
/* 231 */     pipedInputStream = new PipedInputStream(pipedOutputStream);
/*     */ 
/*     */     
/* 234 */     dataOutputStream.writeInt(1297377380);
/*     */ 
/*     */     
/* 237 */     dataOutputStream.writeInt(b2 - 8);
/*     */ 
/*     */     
/* 240 */     if (paramInt == 0) {
/* 241 */       dataOutputStream.writeShort(0);
/*     */     } else {
/*     */       
/* 244 */       dataOutputStream.writeShort(1);
/*     */     } 
/*     */ 
/*     */     
/* 248 */     dataOutputStream.writeShort((short)b3);
/*     */ 
/*     */     
/* 251 */     float f = paramSequence.getDivisionType();
/* 252 */     if (f == 0.0F) {
/* 253 */       j = paramSequence.getResolution();
/* 254 */     } else if (f == 24.0F) {
/* 255 */       j = -6144;
/* 256 */       j += paramSequence.getResolution() & 0xFF;
/* 257 */     } else if (f == 25.0F) {
/* 258 */       j = -6400;
/* 259 */       j += paramSequence.getResolution() & 0xFF;
/* 260 */     } else if (f == 29.97F) {
/* 261 */       j = -7424;
/* 262 */       j += paramSequence.getResolution() & 0xFF;
/* 263 */     } else if (f == 30.0F) {
/* 264 */       j = -7680;
/* 265 */       j += paramSequence.getResolution() & 0xFF;
/*     */     } else {
/*     */       
/* 268 */       return null;
/*     */     } 
/* 270 */     dataOutputStream.writeShort(j);
/*     */ 
/*     */     
/* 273 */     sequenceInputStream = new SequenceInputStream(pipedInputStream, inputStream);
/* 274 */     dataOutputStream.close();
/*     */     
/* 276 */     i = b1 + b2;
/* 277 */     return sequenceInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getType(int paramInt) {
/* 285 */     if ((paramInt & 0xF0) == 240) {
/* 286 */       switch (paramInt) {
/*     */         case 240:
/*     */         case 247:
/* 289 */           return 3;
/*     */         case 255:
/* 291 */           return 4;
/*     */       } 
/* 293 */       return 6;
/*     */     } 
/*     */     
/* 296 */     switch (paramInt & 0xF0) {
/*     */       case 128:
/*     */       case 144:
/*     */       case 160:
/*     */       case 176:
/*     */       case 224:
/* 302 */         return 2;
/*     */       case 192:
/*     */       case 208:
/* 305 */         return 1;
/*     */     } 
/* 307 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeVarInt(long paramLong) throws IOException {
/* 313 */     byte b1 = 1;
/* 314 */     byte b2 = 63;
/*     */     
/* 316 */     for (; b2 > 0 && (paramLong & 127L << b2) == 0L; b2 -= 7);
/*     */     
/* 318 */     while (b2 > 0) {
/* 319 */       this.tddos.writeByte((int)((paramLong & 127L << b2) >> b2 | 0x80L));
/* 320 */       b2 -= 7;
/* 321 */       b1++;
/*     */     } 
/* 323 */     this.tddos.writeByte((int)(paramLong & 0x7FL));
/* 324 */     return b1;
/*     */   }
/*     */   
/*     */   private InputStream writeTrack(Track paramTrack, int paramInt) throws IOException, InvalidMidiDataException {
/* 328 */     int i = 0;
/* 329 */     boolean bool = false;
/* 330 */     int j = paramTrack.size();
/* 331 */     PipedOutputStream pipedOutputStream = new PipedOutputStream();
/* 332 */     DataOutputStream dataOutputStream = new DataOutputStream(pipedOutputStream);
/* 333 */     PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
/*     */     
/* 335 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 336 */     this.tddos = new DataOutputStream(byteArrayOutputStream);
/* 337 */     ByteArrayInputStream byteArrayInputStream = null;
/*     */     
/* 339 */     SequenceInputStream sequenceInputStream = null;
/*     */     
/* 341 */     long l1 = 0L;
/* 342 */     long l2 = 0L;
/* 343 */     long l3 = 0L;
/* 344 */     int k = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     for (byte b = 0; b < j; b++) {
/* 350 */       int i1, i2, i3; MidiEvent midiEvent = paramTrack.get(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       byte[] arrayOfByte = null;
/* 358 */       ShortMessage shortMessage = null;
/* 359 */       MetaMessage metaMessage = null;
/* 360 */       SysexMessage sysexMessage = null;
/*     */ 
/*     */ 
/*     */       
/* 364 */       l3 = midiEvent.getTick();
/* 365 */       l2 = midiEvent.getTick() - l1;
/* 366 */       l1 = midiEvent.getTick();
/*     */ 
/*     */       
/* 369 */       int m = midiEvent.getMessage().getStatus();
/* 370 */       int n = getType(m);
/*     */       
/* 372 */       switch (n) {
/*     */         case 1:
/* 374 */           shortMessage = (ShortMessage)midiEvent.getMessage();
/* 375 */           i1 = shortMessage.getData1();
/* 376 */           i += writeVarInt(l2);
/*     */           
/* 378 */           if (m != k) {
/* 379 */             k = m;
/* 380 */             this.tddos.writeByte(m); i++;
/*     */           } 
/* 382 */           this.tddos.writeByte(i1); i++;
/*     */           break;
/*     */         
/*     */         case 2:
/* 386 */           shortMessage = (ShortMessage)midiEvent.getMessage();
/* 387 */           i1 = shortMessage.getData1();
/* 388 */           i2 = shortMessage.getData2();
/*     */           
/* 390 */           i += writeVarInt(l2);
/* 391 */           if (m != k) {
/* 392 */             k = m;
/* 393 */             this.tddos.writeByte(m); i++;
/*     */           } 
/* 395 */           this.tddos.writeByte(i1); i++;
/* 396 */           this.tddos.writeByte(i2); i++;
/*     */           break;
/*     */         
/*     */         case 3:
/* 400 */           sysexMessage = (SysexMessage)midiEvent.getMessage();
/* 401 */           i3 = sysexMessage.getLength();
/* 402 */           arrayOfByte = sysexMessage.getMessage();
/* 403 */           i += writeVarInt(l2);
/*     */ 
/*     */           
/* 406 */           k = m;
/* 407 */           this.tddos.writeByte(arrayOfByte[0]); i++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 413 */           i += writeVarInt((arrayOfByte.length - 1));
/*     */ 
/*     */ 
/*     */           
/* 417 */           this.tddos.write(arrayOfByte, 1, arrayOfByte.length - 1);
/* 418 */           i += arrayOfByte.length - 1;
/*     */           break;
/*     */         
/*     */         case 4:
/* 422 */           metaMessage = (MetaMessage)midiEvent.getMessage();
/* 423 */           i3 = metaMessage.getLength();
/* 424 */           arrayOfByte = metaMessage.getMessage();
/* 425 */           i += writeVarInt(l2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 433 */           k = m;
/* 434 */           this.tddos.write(arrayOfByte, 0, arrayOfByte.length);
/* 435 */           i += arrayOfByte.length;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 6:
/*     */         case 5:
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 447 */           throw new InvalidMidiDataException("internal file writer error");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 455 */     dataOutputStream.writeInt(1297379947);
/* 456 */     dataOutputStream.writeInt(i);
/* 457 */     i += 8;
/*     */ 
/*     */     
/* 460 */     byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
/* 461 */     sequenceInputStream = new SequenceInputStream(pipedInputStream, byteArrayInputStream);
/* 462 */     dataOutputStream.close();
/* 463 */     this.tddos.close();
/*     */     
/* 465 */     return sequenceInputStream;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/StandardMidiFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */