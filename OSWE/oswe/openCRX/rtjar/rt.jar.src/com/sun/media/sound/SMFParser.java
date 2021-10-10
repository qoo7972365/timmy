/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiEvent;
/*     */ import javax.sound.midi.SysexMessage;
/*     */ import javax.sound.midi.Track;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SMFParser
/*     */ {
/*     */   private static final int MTrk_MAGIC = 1297379947;
/*     */   private static final boolean STRICT_PARSER = false;
/*     */   private static final boolean DEBUG = false;
/*     */   int tracks;
/*     */   DataInputStream stream;
/* 248 */   private int trackLength = 0;
/* 249 */   private byte[] trackData = null;
/* 250 */   private int pos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readUnsigned() throws IOException {
/* 256 */     return this.trackData[this.pos++] & 0xFF;
/*     */   }
/*     */   
/*     */   private void read(byte[] paramArrayOfbyte) throws IOException {
/* 260 */     System.arraycopy(this.trackData, this.pos, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/* 261 */     this.pos += paramArrayOfbyte.length;
/*     */   }
/*     */   
/*     */   private long readVarInt() throws IOException {
/* 265 */     long l = 0L;
/* 266 */     int i = 0;
/*     */     while (true) {
/* 268 */       i = this.trackData[this.pos++] & 0xFF;
/* 269 */       l = (l << 7L) + (i & 0x7F);
/* 270 */       if ((i & 0x80) == 0)
/* 271 */         return l; 
/*     */     } 
/*     */   }
/*     */   private int readIntFromStream() throws IOException {
/*     */     try {
/* 276 */       return this.stream.readInt();
/* 277 */     } catch (EOFException eOFException) {
/* 278 */       throw new EOFException("invalid MIDI file");
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean nextTrack() throws IOException, InvalidMidiDataException {
/*     */     int i;
/* 284 */     this.trackLength = 0;
/*     */     
/*     */     do {
/* 287 */       if (this.stream.skipBytes(this.trackLength) != this.trackLength)
/*     */       {
/* 289 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 293 */       i = readIntFromStream();
/* 294 */       this.trackLength = readIntFromStream();
/* 295 */     } while (i != 1297379947);
/*     */     
/* 297 */     if (this.trackLength < 0) {
/* 298 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 303 */       this.trackData = new byte[this.trackLength];
/* 304 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 305 */       throw new IOException("Track length too big", outOfMemoryError);
/*     */     } 
/*     */     
/*     */     try {
/* 309 */       this.stream.readFully(this.trackData);
/* 310 */     } catch (EOFException eOFException) {
/*     */       
/* 312 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 316 */     this.pos = 0;
/* 317 */     return true;
/*     */   }
/*     */   
/*     */   private boolean trackFinished() {
/* 321 */     return (this.pos >= this.trackLength);
/*     */   }
/*     */ 
/*     */   
/*     */   void readTrack(Track paramTrack) throws IOException, InvalidMidiDataException {
/*     */     try {
/* 327 */       long l = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       int i = 0;
/* 333 */       boolean bool = false;
/*     */       
/* 335 */       while (!trackFinished() && !bool) {
/*     */         FastShortMessage fastShortMessage; SysexMessage sysexMessage1; MetaMessage metaMessage1; int n; byte[] arrayOfByte1; SysexMessage sysexMessage2; int i1, i2; byte[] arrayOfByte2;
/*     */         MetaMessage metaMessage2;
/* 338 */         int j = -1;
/* 339 */         int k = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 344 */         l += readVarInt();
/*     */ 
/*     */         
/* 347 */         int m = readUnsigned();
/*     */         
/* 349 */         if (m >= 128) {
/* 350 */           i = m;
/*     */         } else {
/* 352 */           j = m;
/*     */         } 
/*     */         
/* 355 */         switch (i & 0xF0) {
/*     */           
/*     */           case 128:
/*     */           case 144:
/*     */           case 160:
/*     */           case 176:
/*     */           case 224:
/* 362 */             if (j == -1) {
/* 363 */               j = readUnsigned();
/*     */             }
/* 365 */             k = readUnsigned();
/* 366 */             fastShortMessage = new FastShortMessage(i | j << 8 | k << 16);
/*     */             break;
/*     */           
/*     */           case 192:
/*     */           case 208:
/* 371 */             if (j == -1) {
/* 372 */               j = readUnsigned();
/*     */             }
/* 374 */             fastShortMessage = new FastShortMessage(i | j << 8);
/*     */             break;
/*     */           
/*     */           case 240:
/* 378 */             switch (i) {
/*     */               
/*     */               case 240:
/*     */               case 247:
/* 382 */                 n = (int)readVarInt();
/* 383 */                 arrayOfByte1 = new byte[n];
/* 384 */                 read(arrayOfByte1);
/*     */                 
/* 386 */                 sysexMessage2 = new SysexMessage();
/* 387 */                 sysexMessage2.setMessage(i, arrayOfByte1, n);
/* 388 */                 sysexMessage1 = sysexMessage2;
/*     */                 break;
/*     */ 
/*     */               
/*     */               case 255:
/* 393 */                 i1 = readUnsigned();
/* 394 */                 i2 = (int)readVarInt();
/*     */                 
/*     */                 try {
/* 397 */                   arrayOfByte2 = new byte[i2];
/* 398 */                 } catch (OutOfMemoryError outOfMemoryError) {
/* 399 */                   throw new IOException("Meta length too big", outOfMemoryError);
/*     */                 } 
/*     */                 
/* 402 */                 read(arrayOfByte2);
/*     */                 
/* 404 */                 metaMessage2 = new MetaMessage();
/* 405 */                 metaMessage2.setMessage(i1, arrayOfByte2, i2);
/* 406 */                 metaMessage1 = metaMessage2;
/* 407 */                 if (i1 == 47)
/*     */                 {
/* 409 */                   bool = true;
/*     */                 }
/*     */                 break;
/*     */             } 
/* 413 */             throw new InvalidMidiDataException("Invalid status byte: " + i);
/*     */ 
/*     */           
/*     */           default:
/* 417 */             throw new InvalidMidiDataException("Invalid status byte: " + i);
/*     */         } 
/* 419 */         paramTrack.add(new MidiEvent(metaMessage1, l));
/*     */       } 
/* 421 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/*     */ 
/*     */       
/* 424 */       throw new EOFException("invalid MIDI file");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SMFParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */