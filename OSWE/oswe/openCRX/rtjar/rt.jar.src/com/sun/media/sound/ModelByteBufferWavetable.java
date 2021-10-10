/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.AudioSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModelByteBufferWavetable
/*     */   implements ModelWavetable
/*     */ {
/*     */   private class Buffer8PlusInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final boolean bigendian;
/*     */     private final int framesize_pc;
/*  45 */     int pos = 0;
/*  46 */     int pos2 = 0;
/*  47 */     int markpos = 0;
/*  48 */     int markpos2 = 0;
/*     */     
/*     */     Buffer8PlusInputStream() {
/*  51 */       this.framesize_pc = ModelByteBufferWavetable.this.format.getFrameSize() / ModelByteBufferWavetable.this.format.getChannels();
/*  52 */       this.bigendian = ModelByteBufferWavetable.this.format.isBigEndian();
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  56 */       int i = available();
/*  57 */       if (i <= 0)
/*  58 */         return -1; 
/*  59 */       if (param1Int2 > i)
/*  60 */         param1Int2 = i; 
/*  61 */       byte[] arrayOfByte1 = ModelByteBufferWavetable.this.buffer.array();
/*  62 */       byte[] arrayOfByte2 = ModelByteBufferWavetable.this.buffer8.array();
/*  63 */       this.pos = (int)(this.pos + ModelByteBufferWavetable.this.buffer.arrayOffset());
/*  64 */       this.pos2 = (int)(this.pos2 + ModelByteBufferWavetable.this.buffer8.arrayOffset());
/*  65 */       if (this.bigendian) {
/*  66 */         int j; for (j = 0; j < param1Int2; j += this.framesize_pc + 1) {
/*  67 */           System.arraycopy(arrayOfByte1, this.pos, param1ArrayOfbyte, j, this.framesize_pc);
/*  68 */           System.arraycopy(arrayOfByte2, this.pos2, param1ArrayOfbyte, j + this.framesize_pc, 1);
/*  69 */           this.pos += this.framesize_pc;
/*  70 */           this.pos2++;
/*     */         } 
/*     */       } else {
/*  73 */         int j; for (j = 0; j < param1Int2; j += this.framesize_pc + 1) {
/*  74 */           System.arraycopy(arrayOfByte2, this.pos2, param1ArrayOfbyte, j, 1);
/*  75 */           System.arraycopy(arrayOfByte1, this.pos, param1ArrayOfbyte, j + 1, this.framesize_pc);
/*  76 */           this.pos += this.framesize_pc;
/*  77 */           this.pos2++;
/*     */         } 
/*     */       } 
/*  80 */       this.pos = (int)(this.pos - ModelByteBufferWavetable.this.buffer.arrayOffset());
/*  81 */       this.pos2 = (int)(this.pos2 - ModelByteBufferWavetable.this.buffer8.arrayOffset());
/*  82 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/*  86 */       int i = available();
/*  87 */       if (i <= 0)
/*  88 */         return -1L; 
/*  89 */       if (param1Long > i)
/*  90 */         param1Long = i; 
/*  91 */       this.pos = (int)(this.pos + param1Long / (this.framesize_pc + 1) * this.framesize_pc);
/*  92 */       this.pos2 = (int)(this.pos2 + param1Long / (this.framesize_pc + 1));
/*  93 */       return super.skip(param1Long);
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/*  97 */       return read(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 101 */       byte[] arrayOfByte = new byte[1];
/* 102 */       int i = read(arrayOfByte, 0, 1);
/* 103 */       if (i == -1)
/* 104 */         return -1; 
/* 105 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 109 */       return true;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 113 */       return (int)ModelByteBufferWavetable.this.buffer.capacity() + (int)ModelByteBufferWavetable.this.buffer8.capacity() - this.pos - this.pos2;
/*     */     }
/*     */     
/*     */     public synchronized void mark(int param1Int) {
/* 117 */       this.markpos = this.pos;
/* 118 */       this.markpos2 = this.pos2;
/*     */     }
/*     */     
/*     */     public synchronized void reset() throws IOException {
/* 122 */       this.pos = this.markpos;
/* 123 */       this.pos2 = this.markpos2;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 128 */   private float loopStart = -1.0F;
/* 129 */   private float loopLength = -1.0F;
/*     */   private final ModelByteBuffer buffer;
/* 131 */   private ModelByteBuffer buffer8 = null;
/* 132 */   private AudioFormat format = null;
/* 133 */   private float pitchcorrection = 0.0F;
/* 134 */   private float attenuation = 0.0F;
/* 135 */   private int loopType = 0;
/*     */   
/*     */   public ModelByteBufferWavetable(ModelByteBuffer paramModelByteBuffer) {
/* 138 */     this.buffer = paramModelByteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelByteBufferWavetable(ModelByteBuffer paramModelByteBuffer, float paramFloat) {
/* 143 */     this.buffer = paramModelByteBuffer;
/* 144 */     this.pitchcorrection = paramFloat;
/*     */   }
/*     */   
/*     */   public ModelByteBufferWavetable(ModelByteBuffer paramModelByteBuffer, AudioFormat paramAudioFormat) {
/* 148 */     this.format = paramAudioFormat;
/* 149 */     this.buffer = paramModelByteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelByteBufferWavetable(ModelByteBuffer paramModelByteBuffer, AudioFormat paramAudioFormat, float paramFloat) {
/* 154 */     this.format = paramAudioFormat;
/* 155 */     this.buffer = paramModelByteBuffer;
/* 156 */     this.pitchcorrection = paramFloat;
/*     */   }
/*     */   
/*     */   public void set8BitExtensionBuffer(ModelByteBuffer paramModelByteBuffer) {
/* 160 */     this.buffer8 = paramModelByteBuffer;
/*     */   }
/*     */   
/*     */   public ModelByteBuffer get8BitExtensionBuffer() {
/* 164 */     return this.buffer8;
/*     */   }
/*     */   
/*     */   public ModelByteBuffer getBuffer() {
/* 168 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public AudioFormat getFormat() {
/* 172 */     if (this.format == null) {
/* 173 */       if (this.buffer == null)
/* 174 */         return null; 
/* 175 */       InputStream inputStream = this.buffer.getInputStream();
/* 176 */       AudioFormat audioFormat = null;
/*     */       try {
/* 178 */         audioFormat = AudioSystem.getAudioFileFormat(inputStream).getFormat();
/* 179 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*     */       try {
/* 183 */         inputStream.close();
/* 184 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 187 */       return audioFormat;
/*     */     } 
/* 189 */     return this.format;
/*     */   }
/*     */   
/*     */   public AudioFloatInputStream openStream() {
/* 193 */     if (this.buffer == null)
/* 194 */       return null; 
/* 195 */     if (this.format == null) {
/* 196 */       InputStream inputStream = this.buffer.getInputStream();
/* 197 */       AudioInputStream audioInputStream = null;
/*     */       try {
/* 199 */         audioInputStream = AudioSystem.getAudioInputStream(inputStream);
/* 200 */       } catch (Exception exception) {
/*     */         
/* 202 */         return null;
/*     */       } 
/* 204 */       return AudioFloatInputStream.getInputStream(audioInputStream);
/*     */     } 
/* 206 */     if (this.buffer.array() == null) {
/* 207 */       return AudioFloatInputStream.getInputStream(new AudioInputStream(this.buffer
/* 208 */             .getInputStream(), this.format, this.buffer
/* 209 */             .capacity() / this.format.getFrameSize()));
/*     */     }
/* 211 */     if (this.buffer8 != null && (
/* 212 */       this.format.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED) || this.format
/* 213 */       .getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED))) {
/* 214 */       Buffer8PlusInputStream buffer8PlusInputStream = new Buffer8PlusInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       AudioFormat audioFormat = new AudioFormat(this.format.getEncoding(), this.format.getSampleRate(), this.format.getSampleSizeInBits() + 8, this.format.getChannels(), this.format.getFrameSize() + 1 * this.format.getChannels(), this.format.getFrameRate(), this.format.isBigEndian());
/*     */ 
/*     */       
/* 225 */       AudioInputStream audioInputStream = new AudioInputStream(buffer8PlusInputStream, audioFormat, this.buffer.capacity() / this.format.getFrameSize());
/* 226 */       return AudioFloatInputStream.getInputStream(audioInputStream);
/*     */     } 
/*     */     
/* 229 */     return AudioFloatInputStream.getInputStream(this.format, this.buffer.array(), 
/* 230 */         (int)this.buffer.arrayOffset(), (int)this.buffer.capacity());
/*     */   }
/*     */   
/*     */   public int getChannels() {
/* 234 */     return getFormat().getChannels();
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelOscillatorStream open(float paramFloat) {
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAttenuation() {
/* 244 */     return this.attenuation;
/*     */   }
/*     */   
/*     */   public void setAttenuation(float paramFloat) {
/* 248 */     this.attenuation = paramFloat;
/*     */   }
/*     */   
/*     */   public float getLoopLength() {
/* 252 */     return this.loopLength;
/*     */   }
/*     */   
/*     */   public void setLoopLength(float paramFloat) {
/* 256 */     this.loopLength = paramFloat;
/*     */   }
/*     */   
/*     */   public float getLoopStart() {
/* 260 */     return this.loopStart;
/*     */   }
/*     */   
/*     */   public void setLoopStart(float paramFloat) {
/* 264 */     this.loopStart = paramFloat;
/*     */   }
/*     */   
/*     */   public void setLoopType(int paramInt) {
/* 268 */     this.loopType = paramInt;
/*     */   }
/*     */   
/*     */   public int getLoopType() {
/* 272 */     return this.loopType;
/*     */   }
/*     */   
/*     */   public float getPitchcorrection() {
/* 276 */     return this.pitchcorrection;
/*     */   }
/*     */   
/*     */   public void setPitchcorrection(float paramFloat) {
/* 280 */     this.pitchcorrection = paramFloat;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelByteBufferWavetable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */