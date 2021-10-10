/*     */ package sun.audio;
/*     */ 
/*     */ import com.sun.media.sound.DataPusher;
/*     */ import com.sun.media.sound.Toolkit;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.MetaEventListener;
/*     */ import javax.sound.midi.MetaMessage;
/*     */ import javax.sound.midi.MidiFileFormat;
/*     */ import javax.sound.midi.MidiSystem;
/*     */ import javax.sound.midi.MidiUnavailableException;
/*     */ import javax.sound.midi.Sequencer;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.LineUnavailableException;
/*     */ import javax.sound.sampled.Mixer;
/*     */ import javax.sound.sampled.SourceDataLine;
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
/*     */ public final class AudioDevice
/*     */ {
/*     */   private boolean DEBUG = false;
/*     */   private Hashtable clipStreams;
/*     */   private Vector infos;
/*     */   private boolean playing = false;
/*  70 */   private Mixer mixer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final AudioDevice device = new AudioDevice();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioDevice() {
/*  85 */     this.clipStreams = new Hashtable<>();
/*  86 */     this.infos = new Vector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void startSampled(AudioInputStream paramAudioInputStream, InputStream paramInputStream) throws UnsupportedAudioFileException, LineUnavailableException {
/*  94 */     Info info = null;
/*  95 */     DataPusher dataPusher = null;
/*  96 */     DataLine.Info info1 = null;
/*  97 */     SourceDataLine sourceDataLine = null;
/*     */ 
/*     */     
/* 100 */     paramAudioInputStream = Toolkit.getPCMConvertedAudioInputStream(paramAudioInputStream);
/*     */     
/* 102 */     if (paramAudioInputStream == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 108 */     info1 = new DataLine.Info(SourceDataLine.class, paramAudioInputStream.getFormat());
/* 109 */     if (!AudioSystem.isLineSupported(info1)) {
/*     */       return;
/*     */     }
/* 112 */     sourceDataLine = (SourceDataLine)AudioSystem.getLine(info1);
/* 113 */     dataPusher = new DataPusher(sourceDataLine, paramAudioInputStream);
/*     */     
/* 115 */     info = new Info(null, paramInputStream, dataPusher);
/* 116 */     this.infos.addElement(info);
/*     */     
/* 118 */     dataPusher.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void startMidi(InputStream paramInputStream1, InputStream paramInputStream2) throws InvalidMidiDataException, MidiUnavailableException {
/* 125 */     Sequencer sequencer = null;
/* 126 */     Info info = null;
/*     */     
/* 128 */     sequencer = MidiSystem.getSequencer();
/* 129 */     sequencer.open();
/*     */     try {
/* 131 */       sequencer.setSequence(paramInputStream1);
/* 132 */     } catch (IOException iOException) {
/* 133 */       throw new InvalidMidiDataException(iOException.getMessage());
/*     */     } 
/*     */     
/* 136 */     info = new Info(sequencer, paramInputStream2, null);
/*     */     
/* 138 */     this.infos.addElement(info);
/*     */ 
/*     */     
/* 141 */     sequencer.addMetaEventListener(info);
/*     */     
/* 143 */     sequencer.start();
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
/*     */   public synchronized void openChannel(InputStream paramInputStream) {
/* 155 */     if (this.DEBUG) {
/* 156 */       System.out.println("AudioDevice: openChannel");
/* 157 */       System.out.println("input stream =" + paramInputStream);
/*     */     } 
/*     */     
/* 160 */     Info info = null;
/*     */ 
/*     */     
/* 163 */     for (byte b = 0; b < this.infos.size(); b++) {
/* 164 */       info = this.infos.elementAt(b);
/* 165 */       if (info.in == paramInputStream) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     AudioInputStream audioInputStream = null;
/*     */     
/* 174 */     if (paramInputStream instanceof AudioStream) {
/*     */       
/* 176 */       if (((AudioStream)paramInputStream).midiformat != null) {
/*     */ 
/*     */         
/*     */         try {
/* 180 */           startMidi(((AudioStream)paramInputStream).stream, paramInputStream);
/* 181 */         } catch (Exception exception) {
/*     */           
/*     */           return;
/*     */         }
/*     */       
/* 186 */       } else if (((AudioStream)paramInputStream).ais != null) {
/*     */ 
/*     */         
/*     */         try {
/* 190 */           startSampled(((AudioStream)paramInputStream).ais, paramInputStream);
/* 191 */         } catch (Exception exception) {
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 196 */     } else if (paramInputStream instanceof AudioDataStream) {
/* 197 */       if (paramInputStream instanceof ContinuousAudioDataStream) {
/*     */         
/*     */         try {
/* 200 */           AudioInputStream audioInputStream1 = new AudioInputStream(paramInputStream, (((AudioDataStream)paramInputStream).getAudioData()).format, -1L);
/*     */           
/* 202 */           startSampled(audioInputStream1, paramInputStream);
/* 203 */         } catch (Exception exception) {
/*     */           
/*     */           return;
/*     */         } 
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/* 211 */           AudioInputStream audioInputStream1 = new AudioInputStream(paramInputStream, (((AudioDataStream)paramInputStream).getAudioData()).format, (((AudioDataStream)paramInputStream).getAudioData()).buffer.length);
/* 212 */           startSampled(audioInputStream1, paramInputStream);
/* 213 */         } catch (Exception exception) {
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 218 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream, 1024);
/*     */ 
/*     */       
/*     */       try {
/*     */         try {
/* 223 */           audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
/* 224 */         } catch (IOException iOException) {
/*     */           return;
/*     */         } 
/*     */         
/* 228 */         startSampled(audioInputStream, paramInputStream);
/*     */       }
/* 230 */       catch (UnsupportedAudioFileException unsupportedAudioFileException) {
/*     */ 
/*     */         
/*     */         try {
/*     */           try {
/* 235 */             MidiFileFormat midiFileFormat = MidiSystem.getMidiFileFormat(bufferedInputStream);
/* 236 */           } catch (IOException iOException) {
/*     */             return;
/*     */           } 
/*     */           
/* 240 */           startMidi(bufferedInputStream, paramInputStream);
/*     */         
/*     */         }
/* 243 */         catch (InvalidMidiDataException invalidMidiDataException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 249 */           AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.ULAW, 8000.0F, 8, 1, 1, 8000.0F, true);
/*     */           
/*     */           try {
/* 252 */             AudioInputStream audioInputStream1 = new AudioInputStream(bufferedInputStream, audioFormat, -1L);
/*     */             
/* 254 */             startSampled(audioInputStream1, paramInputStream);
/* 255 */           } catch (UnsupportedAudioFileException unsupportedAudioFileException1) {
/*     */             return;
/* 257 */           } catch (LineUnavailableException lineUnavailableException) {
/*     */             
/*     */             return;
/*     */           } 
/* 261 */         } catch (MidiUnavailableException midiUnavailableException) {
/*     */           
/*     */           return;
/*     */         }
/*     */       
/*     */       }
/* 267 */       catch (LineUnavailableException lineUnavailableException) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 274 */     notify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void closeChannel(InputStream paramInputStream) {
/* 283 */     if (this.DEBUG) {
/* 284 */       System.out.println("AudioDevice.closeChannel");
/*     */     }
/*     */     
/* 287 */     if (paramInputStream == null) {
/*     */       return;
/*     */     }
/*     */     
/* 291 */     for (byte b = 0; b < this.infos.size(); b++) {
/*     */       
/* 293 */       Info info = this.infos.elementAt(b);
/*     */       
/* 295 */       if (info.in == paramInputStream)
/*     */       {
/* 297 */         if (info.sequencer != null) {
/*     */           
/* 299 */           info.sequencer.stop();
/*     */           
/* 301 */           this.infos.removeElement(info);
/*     */         }
/* 303 */         else if (info.datapusher != null) {
/*     */           
/* 305 */           info.datapusher.stop();
/* 306 */           this.infos.removeElement(info);
/*     */         } 
/*     */       }
/*     */     } 
/* 310 */     notify();
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
/*     */   public synchronized void open() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/* 344 */     if (this.DEBUG) {
/* 345 */       System.out.println("exiting play()");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void closeStreams() {
/* 356 */     for (byte b = 0; b < this.infos.size(); b++) {
/*     */       
/* 358 */       Info info = this.infos.elementAt(b);
/*     */       
/* 360 */       if (info.sequencer != null) {
/*     */         
/* 362 */         info.sequencer.stop();
/* 363 */         info.sequencer.close();
/* 364 */         this.infos.removeElement(info);
/*     */       }
/* 366 */       else if (info.datapusher != null) {
/*     */         
/* 368 */         info.datapusher.stop();
/* 369 */         this.infos.removeElement(info);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 374 */     if (this.DEBUG) {
/* 375 */       System.err.println("Audio Device: Streams all closed.");
/*     */     }
/*     */     
/* 378 */     this.clipStreams = new Hashtable<>();
/* 379 */     this.infos = new Vector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int openChannels() {
/* 386 */     return this.infos.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setVerbose(boolean paramBoolean) {
/* 393 */     this.DEBUG = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final class Info
/*     */     implements MetaEventListener
/*     */   {
/*     */     final Sequencer sequencer;
/*     */ 
/*     */     
/*     */     final InputStream in;
/*     */ 
/*     */     
/*     */     final DataPusher datapusher;
/*     */ 
/*     */     
/*     */     Info(Sequencer param1Sequencer, InputStream param1InputStream, DataPusher param1DataPusher) {
/* 411 */       this.sequencer = param1Sequencer;
/* 412 */       this.in = param1InputStream;
/* 413 */       this.datapusher = param1DataPusher;
/*     */     }
/*     */     
/*     */     public void meta(MetaMessage param1MetaMessage) {
/* 417 */       if (param1MetaMessage.getType() == 47 && this.sequencer != null)
/* 418 */         this.sequencer.close(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/audio/AudioDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */