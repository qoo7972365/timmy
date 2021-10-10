/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.sound.sampled.AudioFormat;
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
/*     */ 
/*     */ public abstract class AudioFloatInputStream
/*     */ {
/*     */   private static class BytaArrayAudioFloatInputStream
/*     */     extends AudioFloatInputStream
/*     */   {
/*  49 */     private int pos = 0;
/*  50 */     private int markpos = 0;
/*     */     
/*     */     private final AudioFloatConverter converter;
/*     */     private final AudioFormat format;
/*     */     private final byte[] buffer;
/*     */     private final int buffer_offset;
/*     */     private final int buffer_len;
/*     */     private final int framesize_pc;
/*     */     
/*     */     BytaArrayAudioFloatInputStream(AudioFloatConverter param1AudioFloatConverter, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/*  60 */       this.converter = param1AudioFloatConverter;
/*  61 */       this.format = param1AudioFloatConverter.getFormat();
/*  62 */       this.buffer = param1ArrayOfbyte;
/*  63 */       this.buffer_offset = param1Int1;
/*  64 */       this.framesize_pc = this.format.getFrameSize() / this.format.getChannels();
/*  65 */       this.buffer_len = param1Int2 / this.framesize_pc;
/*     */     }
/*     */ 
/*     */     
/*     */     public AudioFormat getFormat() {
/*  70 */       return this.format;
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/*  74 */       return this.buffer_len;
/*     */     }
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/*  78 */       if (param1ArrayOffloat == null)
/*  79 */         throw new NullPointerException(); 
/*  80 */       if (param1Int1 < 0 || param1Int2 < 0 || param1Int2 > param1ArrayOffloat.length - param1Int1)
/*  81 */         throw new IndexOutOfBoundsException(); 
/*  82 */       if (this.pos >= this.buffer_len)
/*  83 */         return -1; 
/*  84 */       if (param1Int2 == 0)
/*  85 */         return 0; 
/*  86 */       if (this.pos + param1Int2 > this.buffer_len)
/*  87 */         param1Int2 = this.buffer_len - this.pos; 
/*  88 */       this.converter.toFloatArray(this.buffer, this.buffer_offset + this.pos * this.framesize_pc, param1ArrayOffloat, param1Int1, param1Int2);
/*     */       
/*  90 */       this.pos += param1Int2;
/*  91 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/*  95 */       if (this.pos >= this.buffer_len)
/*  96 */         return -1L; 
/*  97 */       if (param1Long <= 0L)
/*  98 */         return 0L; 
/*  99 */       if (this.pos + param1Long > this.buffer_len)
/* 100 */         param1Long = (this.buffer_len - this.pos); 
/* 101 */       this.pos = (int)(this.pos + param1Long);
/* 102 */       return param1Long;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 106 */       return this.buffer_len - this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */     
/*     */     public void mark(int param1Int) {
/* 113 */       this.markpos = this.pos;
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 117 */       return true;
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 121 */       this.pos = this.markpos;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DirectAudioFloatInputStream
/*     */     extends AudioFloatInputStream
/*     */   {
/*     */     private final AudioInputStream stream;
/*     */     private AudioFloatConverter converter;
/*     */     private final int framesize_pc;
/*     */     private byte[] buffer;
/*     */     
/*     */     DirectAudioFloatInputStream(AudioInputStream param1AudioInputStream) {
/* 134 */       this.converter = AudioFloatConverter.getConverter(param1AudioInputStream.getFormat());
/* 135 */       if (this.converter == null) {
/* 136 */         AudioFormat audioFormat2, audioFormat1 = param1AudioInputStream.getFormat();
/*     */ 
/*     */         
/* 139 */         AudioFormat[] arrayOfAudioFormat = AudioSystem.getTargetFormats(AudioFormat.Encoding.PCM_SIGNED, audioFormat1);
/*     */         
/* 141 */         if (arrayOfAudioFormat.length != 0) {
/* 142 */           audioFormat2 = arrayOfAudioFormat[0];
/*     */         } else {
/* 144 */           float f1 = audioFormat1.getSampleRate();
/* 145 */           int i = audioFormat1.getSampleSizeInBits();
/* 146 */           int j = audioFormat1.getFrameSize();
/* 147 */           float f2 = audioFormat1.getFrameRate();
/* 148 */           i = 16;
/* 149 */           j = audioFormat1.getChannels() * i / 8;
/* 150 */           f2 = f1;
/*     */ 
/*     */ 
/*     */           
/* 154 */           audioFormat2 = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, f1, i, audioFormat1.getChannels(), j, f2, false);
/*     */         } 
/*     */ 
/*     */         
/* 158 */         param1AudioInputStream = AudioSystem.getAudioInputStream(audioFormat2, param1AudioInputStream);
/* 159 */         this.converter = AudioFloatConverter.getConverter(param1AudioInputStream.getFormat());
/*     */       } 
/* 161 */       this
/* 162 */         .framesize_pc = param1AudioInputStream.getFormat().getFrameSize() / param1AudioInputStream.getFormat().getChannels();
/* 163 */       this.stream = param1AudioInputStream;
/*     */     }
/*     */     
/*     */     public AudioFormat getFormat() {
/* 167 */       return this.stream.getFormat();
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/* 171 */       return this.stream.getFrameLength();
/*     */     }
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 175 */       int i = param1Int2 * this.framesize_pc;
/* 176 */       if (this.buffer == null || this.buffer.length < i)
/* 177 */         this.buffer = new byte[i]; 
/* 178 */       int j = this.stream.read(this.buffer, 0, i);
/* 179 */       if (j == -1)
/* 180 */         return -1; 
/* 181 */       this.converter.toFloatArray(this.buffer, param1ArrayOffloat, param1Int1, j / this.framesize_pc);
/* 182 */       return j / this.framesize_pc;
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 186 */       long l1 = param1Long * this.framesize_pc;
/* 187 */       long l2 = this.stream.skip(l1);
/* 188 */       if (l2 == -1L)
/* 189 */         return -1L; 
/* 190 */       return l2 / this.framesize_pc;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 194 */       return this.stream.available() / this.framesize_pc;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 198 */       this.stream.close();
/*     */     }
/*     */     
/*     */     public void mark(int param1Int) {
/* 202 */       this.stream.mark(param1Int * this.framesize_pc);
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 206 */       return this.stream.markSupported();
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 210 */       this.stream.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static AudioFloatInputStream getInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 216 */     return new DirectAudioFloatInputStream(
/* 217 */         AudioSystem.getAudioInputStream(paramURL));
/*     */   }
/*     */ 
/*     */   
/*     */   public static AudioFloatInputStream getInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 222 */     return new DirectAudioFloatInputStream(
/* 223 */         AudioSystem.getAudioInputStream(paramFile));
/*     */   }
/*     */ 
/*     */   
/*     */   public static AudioFloatInputStream getInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 228 */     return new DirectAudioFloatInputStream(
/* 229 */         AudioSystem.getAudioInputStream(paramInputStream));
/*     */   }
/*     */ 
/*     */   
/*     */   public static AudioFloatInputStream getInputStream(AudioInputStream paramAudioInputStream) {
/* 234 */     return new DirectAudioFloatInputStream(paramAudioInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AudioFloatInputStream getInputStream(AudioFormat paramAudioFormat, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 240 */     AudioFloatConverter audioFloatConverter = AudioFloatConverter.getConverter(paramAudioFormat);
/* 241 */     if (audioFloatConverter != null) {
/* 242 */       return new BytaArrayAudioFloatInputStream(audioFloatConverter, paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 245 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     
/* 247 */     long l = (paramAudioFormat.getFrameSize() == -1) ? -1L : (paramInt2 / paramAudioFormat.getFrameSize());
/* 248 */     AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, paramAudioFormat, l);
/* 249 */     return getInputStream(audioInputStream);
/*     */   }
/*     */   
/*     */   public abstract AudioFormat getFormat();
/*     */   
/*     */   public abstract long getFrameLength();
/*     */   
/*     */   public abstract int read(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public final int read(float[] paramArrayOffloat) throws IOException {
/* 259 */     return read(paramArrayOffloat, 0, paramArrayOffloat.length);
/*     */   }
/*     */   
/*     */   public final float read() throws IOException {
/* 263 */     float[] arrayOfFloat = new float[1];
/* 264 */     int i = read(arrayOfFloat, 0, 1);
/* 265 */     if (i == -1 || i == 0)
/* 266 */       return 0.0F; 
/* 267 */     return arrayOfFloat[0];
/*     */   }
/*     */   
/*     */   public abstract long skip(long paramLong) throws IOException;
/*     */   
/*     */   public abstract int available() throws IOException;
/*     */   
/*     */   public abstract void close() throws IOException;
/*     */   
/*     */   public abstract void mark(int paramInt);
/*     */   
/*     */   public abstract boolean markSupported();
/*     */   
/*     */   public abstract void reset() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AudioFloatInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */