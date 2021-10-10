/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.SequenceInputStream;
/*     */ import javax.sound.sampled.AudioFileFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WaveFileWriter
/*     */   extends SunFileWriter
/*     */ {
/*     */   static final int RIFF_MAGIC = 1380533830;
/*     */   static final int WAVE_MAGIC = 1463899717;
/*     */   static final int FMT_MAGIC = 1718449184;
/*     */   static final int DATA_MAGIC = 1684108385;
/*     */   static final int WAVE_FORMAT_UNKNOWN = 0;
/*     */   static final int WAVE_FORMAT_PCM = 1;
/*     */   static final int WAVE_FORMAT_ADPCM = 2;
/*     */   static final int WAVE_FORMAT_ALAW = 6;
/*     */   static final int WAVE_FORMAT_MULAW = 7;
/*     */   static final int WAVE_FORMAT_OKI_ADPCM = 16;
/*     */   static final int WAVE_FORMAT_DIGISTD = 21;
/*     */   static final int WAVE_FORMAT_DIGIFIX = 22;
/*     */   static final int WAVE_IBM_FORMAT_MULAW = 257;
/*     */   static final int WAVE_IBM_FORMAT_ALAW = 258;
/*     */   static final int WAVE_IBM_FORMAT_ADPCM = 259;
/*     */   static final int WAVE_FORMAT_DVI_ADPCM = 17;
/*     */   static final int WAVE_FORMAT_SX7383 = 7175;
/*     */   
/*     */   public WaveFileWriter() {
/*  80 */     super(new AudioFileFormat.Type[] { AudioFileFormat.Type.WAVE });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream) {
/*  89 */     AudioFileFormat.Type[] arrayOfType = new AudioFileFormat.Type[this.types.length];
/*  90 */     System.arraycopy(this.types, 0, arrayOfType, 0, this.types.length);
/*     */ 
/*     */     
/*  93 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*  94 */     AudioFormat.Encoding encoding = audioFormat.getEncoding();
/*     */     
/*  96 */     if (AudioFormat.Encoding.ALAW.equals(encoding) || AudioFormat.Encoding.ULAW
/*  97 */       .equals(encoding) || AudioFormat.Encoding.PCM_SIGNED
/*  98 */       .equals(encoding) || AudioFormat.Encoding.PCM_UNSIGNED
/*  99 */       .equals(encoding))
/*     */     {
/* 101 */       return arrayOfType;
/*     */     }
/*     */     
/* 104 */     return new AudioFileFormat.Type[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException {
/* 115 */     WaveFileFormat waveFileFormat = (WaveFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (paramAudioInputStream.getFrameLength() == -1L) {
/* 121 */       throw new IOException("stream length not specified");
/*     */     }
/*     */     
/* 124 */     return writeWaveFile(paramAudioInputStream, waveFileFormat, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException {
/* 132 */     WaveFileFormat waveFileFormat = (WaveFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */ 
/*     */     
/* 135 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/* 136 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 4096);
/* 137 */     int i = writeWaveFile(paramAudioInputStream, waveFileFormat, bufferedOutputStream);
/* 138 */     bufferedOutputStream.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (waveFileFormat.getByteLength() == -1) {
/*     */       
/* 145 */       int j = i - waveFileFormat.getHeaderSize();
/* 146 */       int k = j + waveFileFormat.getHeaderSize() - 8;
/*     */       
/* 148 */       RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
/*     */       
/* 150 */       randomAccessFile.skipBytes(4);
/* 151 */       randomAccessFile.writeInt(big2little(k));
/*     */       
/* 153 */       randomAccessFile.skipBytes(12 + WaveFileFormat.getFmtChunkSize(waveFileFormat.getWaveType()) + 4);
/* 154 */       randomAccessFile.writeInt(big2little(j));
/*     */       
/* 156 */       randomAccessFile.close();
/*     */     } 
/*     */     
/* 159 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat getAudioFileFormat(AudioFileFormat.Type paramType, AudioInputStream paramAudioInputStream) {
/*     */     int i;
/*     */     byte b;
/* 169 */     AudioFormat audioFormat1 = null;
/* 170 */     WaveFileFormat waveFileFormat = null;
/* 171 */     AudioFormat.Encoding encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/*     */     
/* 173 */     AudioFormat audioFormat2 = paramAudioInputStream.getFormat();
/* 174 */     AudioFormat.Encoding encoding2 = audioFormat2.getEncoding();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (!this.types[0].equals(paramType)) {
/* 184 */       throw new IllegalArgumentException("File type " + paramType + " not supported.");
/*     */     }
/* 186 */     byte b1 = 1;
/*     */     
/* 188 */     if (AudioFormat.Encoding.ALAW.equals(encoding2) || AudioFormat.Encoding.ULAW
/* 189 */       .equals(encoding2)) {
/*     */       
/* 191 */       encoding1 = encoding2;
/* 192 */       i = audioFormat2.getSampleSizeInBits();
/* 193 */       if (encoding2.equals(AudioFormat.Encoding.ALAW)) {
/* 194 */         b1 = 6;
/*     */       } else {
/* 196 */         b1 = 7;
/*     */       } 
/* 198 */     } else if (audioFormat2.getSampleSizeInBits() == 8) {
/* 199 */       encoding1 = AudioFormat.Encoding.PCM_UNSIGNED;
/* 200 */       i = 8;
/*     */     } else {
/* 202 */       encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/* 203 */       i = audioFormat2.getSampleSizeInBits();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     audioFormat1 = new AudioFormat(encoding1, audioFormat2.getSampleRate(), i, audioFormat2.getChannels(), audioFormat2.getFrameSize(), audioFormat2.getFrameRate(), false);
/*     */ 
/*     */     
/* 215 */     if (paramAudioInputStream.getFrameLength() != -1L) {
/*     */       
/* 217 */       b = (int)paramAudioInputStream.getFrameLength() * audioFormat2.getFrameSize() + WaveFileFormat.getHeaderSize(b1);
/*     */     } else {
/* 219 */       b = -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     waveFileFormat = new WaveFileFormat(AudioFileFormat.Type.WAVE, b, audioFormat1, (int)paramAudioInputStream.getFrameLength());
/*     */     
/* 227 */     return waveFileFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeWaveFile(InputStream paramInputStream, WaveFileFormat paramWaveFileFormat, OutputStream paramOutputStream) throws IOException {
/* 233 */     int i = 0;
/* 234 */     int j = 0;
/* 235 */     InputStream inputStream = getFileStream(paramWaveFileFormat, paramInputStream);
/* 236 */     byte[] arrayOfByte = new byte[4096];
/* 237 */     int k = paramWaveFileFormat.getByteLength();
/*     */     
/* 239 */     while ((i = inputStream.read(arrayOfByte)) >= 0) {
/*     */       
/* 241 */       if (k > 0) {
/* 242 */         if (i < k) {
/* 243 */           paramOutputStream.write(arrayOfByte, 0, i);
/* 244 */           j += i;
/* 245 */           k -= i; continue;
/*     */         } 
/* 247 */         paramOutputStream.write(arrayOfByte, 0, k);
/* 248 */         j += k;
/* 249 */         k = 0;
/*     */         
/*     */         break;
/*     */       } 
/* 253 */       paramOutputStream.write(arrayOfByte, 0, i);
/* 254 */       j += i;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getFileStream(WaveFileFormat paramWaveFileFormat, InputStream paramInputStream) throws IOException {
/* 265 */     AudioFormat audioFormat1 = paramWaveFileFormat.getFormat();
/* 266 */     int i = paramWaveFileFormat.getHeaderSize();
/* 267 */     int j = 1380533830;
/* 268 */     int k = 1463899717;
/* 269 */     int m = 1718449184;
/* 270 */     int n = WaveFileFormat.getFmtChunkSize(paramWaveFileFormat.getWaveType());
/* 271 */     short s1 = (short)paramWaveFileFormat.getWaveType();
/* 272 */     short s2 = (short)audioFormat1.getChannels();
/* 273 */     short s3 = (short)audioFormat1.getSampleSizeInBits();
/* 274 */     int i1 = (int)audioFormat1.getSampleRate();
/* 275 */     int i2 = audioFormat1.getFrameSize();
/* 276 */     int i3 = (int)audioFormat1.getFrameRate();
/* 277 */     int i4 = s2 * s3 * i1 / 8;
/* 278 */     short s4 = (short)(s3 / 8 * s2);
/* 279 */     int i5 = 1684108385;
/* 280 */     int i6 = paramWaveFileFormat.getFrameLength() * i2;
/* 281 */     int i7 = paramWaveFileFormat.getByteLength();
/* 282 */     int i8 = i6 + i - 8;
/*     */     
/* 284 */     byte[] arrayOfByte = null;
/* 285 */     ByteArrayInputStream byteArrayInputStream = null;
/* 286 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 287 */     DataOutputStream dataOutputStream = null;
/* 288 */     SequenceInputStream sequenceInputStream = null;
/*     */     
/* 290 */     AudioFormat audioFormat2 = null;
/* 291 */     AudioFormat.Encoding encoding = null;
/* 292 */     InputStream inputStream = paramInputStream;
/*     */ 
/*     */     
/* 295 */     if (paramInputStream instanceof AudioInputStream) {
/* 296 */       audioFormat2 = ((AudioInputStream)paramInputStream).getFormat();
/*     */       
/* 298 */       encoding = audioFormat2.getEncoding();
/*     */       
/* 300 */       if (AudioFormat.Encoding.PCM_SIGNED.equals(encoding) && 
/* 301 */         s3 == 8) {
/* 302 */         s1 = 1;
/*     */         
/* 304 */         inputStream = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, audioFormat2
/*     */               
/* 306 */               .getSampleRate(), audioFormat2
/* 307 */               .getSampleSizeInBits(), audioFormat2
/* 308 */               .getChannels(), audioFormat2
/* 309 */               .getFrameSize(), audioFormat2
/* 310 */               .getFrameRate(), false), (AudioInputStream)paramInputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 315 */       if (((AudioFormat.Encoding.PCM_SIGNED.equals(encoding) && audioFormat2.isBigEndian()) || (AudioFormat.Encoding.PCM_UNSIGNED
/* 316 */         .equals(encoding) && !audioFormat2.isBigEndian()) || (AudioFormat.Encoding.PCM_UNSIGNED
/* 317 */         .equals(encoding) && audioFormat2.isBigEndian())) && 
/* 318 */         s3 != 8) {
/* 319 */         s1 = 1;
/*     */         
/* 321 */         inputStream = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat2
/*     */               
/* 323 */               .getSampleRate(), audioFormat2
/* 324 */               .getSampleSizeInBits(), audioFormat2
/* 325 */               .getChannels(), audioFormat2
/* 326 */               .getFrameSize(), audioFormat2
/* 327 */               .getFrameRate(), false), (AudioInputStream)paramInputStream);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     byteArrayOutputStream = new ByteArrayOutputStream();
/* 338 */     dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*     */ 
/*     */     
/* 341 */     dataOutputStream.writeInt(j);
/* 342 */     dataOutputStream.writeInt(big2little(i8));
/* 343 */     dataOutputStream.writeInt(k);
/* 344 */     dataOutputStream.writeInt(m);
/* 345 */     dataOutputStream.writeInt(big2little(n));
/* 346 */     dataOutputStream.writeShort(big2littleShort(s1));
/* 347 */     dataOutputStream.writeShort(big2littleShort(s2));
/* 348 */     dataOutputStream.writeInt(big2little(i1));
/* 349 */     dataOutputStream.writeInt(big2little(i4));
/* 350 */     dataOutputStream.writeShort(big2littleShort(s4));
/* 351 */     dataOutputStream.writeShort(big2littleShort(s3));
/*     */     
/* 353 */     if (s1 != 1)
/*     */     {
/* 355 */       dataOutputStream.writeShort(0);
/*     */     }
/*     */     
/* 358 */     dataOutputStream.writeInt(i5);
/* 359 */     dataOutputStream.writeInt(big2little(i6));
/*     */     
/* 361 */     dataOutputStream.close();
/* 362 */     arrayOfByte = byteArrayOutputStream.toByteArray();
/* 363 */     byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 364 */     sequenceInputStream = new SequenceInputStream(byteArrayInputStream, new SunFileWriter.NoCloseInputStream(this, inputStream));
/*     */ 
/*     */     
/* 367 */     return sequenceInputStream;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */