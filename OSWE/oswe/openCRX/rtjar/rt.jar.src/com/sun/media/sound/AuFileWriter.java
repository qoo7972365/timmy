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
/*     */ public final class AuFileWriter
/*     */   extends SunFileWriter
/*     */ {
/*     */   public static final int UNKNOWN_SIZE = -1;
/*     */   
/*     */   public AuFileWriter() {
/*  61 */     super(new AudioFileFormat.Type[] { AudioFileFormat.Type.AU });
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream) {
/*  66 */     AudioFileFormat.Type[] arrayOfType = new AudioFileFormat.Type[this.types.length];
/*  67 */     System.arraycopy(this.types, 0, arrayOfType, 0, this.types.length);
/*     */ 
/*     */     
/*  70 */     AudioFormat audioFormat = paramAudioInputStream.getFormat();
/*  71 */     AudioFormat.Encoding encoding = audioFormat.getEncoding();
/*     */     
/*  73 */     if (AudioFormat.Encoding.ALAW.equals(encoding) || AudioFormat.Encoding.ULAW
/*  74 */       .equals(encoding) || AudioFormat.Encoding.PCM_SIGNED
/*  75 */       .equals(encoding) || AudioFormat.Encoding.PCM_UNSIGNED
/*  76 */       .equals(encoding))
/*     */     {
/*  78 */       return arrayOfType;
/*     */     }
/*     */     
/*  81 */     return new AudioFileFormat.Type[0];
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
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException {
/*  94 */     AuFileFormat auFileFormat = (AuFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */     
/*  96 */     return writeAuFile(paramAudioInputStream, auFileFormat, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException {
/* 105 */     AuFileFormat auFileFormat = (AuFileFormat)getAudioFileFormat(paramType, paramAudioInputStream);
/*     */ 
/*     */     
/* 108 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/* 109 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 4096);
/* 110 */     int i = writeAuFile(paramAudioInputStream, auFileFormat, bufferedOutputStream);
/* 111 */     bufferedOutputStream.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (auFileFormat.getByteLength() == -1) {
/*     */ 
/*     */ 
/*     */       
/* 120 */       RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "rw");
/* 121 */       if (randomAccessFile.length() <= 2147483647L) {
/*     */         
/* 123 */         randomAccessFile.skipBytes(8);
/* 124 */         randomAccessFile.writeInt(i - 24);
/*     */       } 
/*     */       
/* 127 */       randomAccessFile.close();
/*     */     } 
/*     */     
/* 130 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat getAudioFileFormat(AudioFileFormat.Type paramType, AudioInputStream paramAudioInputStream) {
/*     */     int i;
/*     */     byte b;
/* 142 */     AudioFormat audioFormat1 = null;
/* 143 */     AuFileFormat auFileFormat = null;
/* 144 */     AudioFormat.Encoding encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/*     */     
/* 146 */     AudioFormat audioFormat2 = paramAudioInputStream.getFormat();
/* 147 */     AudioFormat.Encoding encoding2 = audioFormat2.getEncoding();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (!this.types[0].equals(paramType)) {
/* 158 */       throw new IllegalArgumentException("File type " + paramType + " not supported.");
/*     */     }
/*     */     
/* 161 */     if (AudioFormat.Encoding.ALAW.equals(encoding2) || AudioFormat.Encoding.ULAW
/* 162 */       .equals(encoding2)) {
/*     */       
/* 164 */       encoding1 = encoding2;
/* 165 */       i = audioFormat2.getSampleSizeInBits();
/*     */     }
/* 167 */     else if (audioFormat2.getSampleSizeInBits() == 8) {
/*     */       
/* 169 */       encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/* 170 */       i = 8;
/*     */     }
/*     */     else {
/*     */       
/* 174 */       encoding1 = AudioFormat.Encoding.PCM_SIGNED;
/* 175 */       i = audioFormat2.getSampleSizeInBits();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     audioFormat1 = new AudioFormat(encoding1, audioFormat2.getSampleRate(), i, audioFormat2.getChannels(), audioFormat2.getFrameSize(), audioFormat2.getFrameRate(), true);
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (paramAudioInputStream.getFrameLength() != -1L) {
/* 189 */       b = (int)paramAudioInputStream.getFrameLength() * audioFormat2.getFrameSize() + 24;
/*     */     } else {
/* 191 */       b = -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     auFileFormat = new AuFileFormat(AudioFileFormat.Type.AU, b, audioFormat1, (int)paramAudioInputStream.getFrameLength());
/*     */     
/* 199 */     return auFileFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getFileStream(AuFileFormat paramAuFileFormat, InputStream paramInputStream) throws IOException {
/* 207 */     AudioFormat audioFormat1 = paramAuFileFormat.getFormat();
/*     */     
/* 209 */     int i = 779316836;
/* 210 */     byte b = 24;
/* 211 */     long l1 = paramAuFileFormat.getFrameLength();
/*     */ 
/*     */     
/* 214 */     long l2 = (l1 == -1L) ? -1L : (l1 * audioFormat1.getFrameSize());
/* 215 */     if (l2 > 2147483647L) {
/* 216 */       l2 = -1L;
/*     */     }
/* 218 */     int j = paramAuFileFormat.getAuType();
/* 219 */     int k = (int)audioFormat1.getSampleRate();
/* 220 */     int m = audioFormat1.getChannels();
/*     */ 
/*     */     
/* 223 */     boolean bool = true;
/*     */     
/* 225 */     byte[] arrayOfByte = null;
/* 226 */     ByteArrayInputStream byteArrayInputStream = null;
/* 227 */     ByteArrayOutputStream byteArrayOutputStream = null;
/* 228 */     DataOutputStream dataOutputStream = null;
/* 229 */     SequenceInputStream sequenceInputStream = null;
/*     */     
/* 231 */     AudioFormat audioFormat2 = null;
/* 232 */     AudioFormat.Encoding encoding = null;
/* 233 */     InputStream inputStream = paramInputStream;
/*     */ 
/*     */ 
/*     */     
/* 237 */     inputStream = paramInputStream;
/*     */     
/* 239 */     if (paramInputStream instanceof AudioInputStream) {
/*     */ 
/*     */       
/* 242 */       audioFormat2 = ((AudioInputStream)paramInputStream).getFormat();
/* 243 */       encoding = audioFormat2.getEncoding();
/*     */ 
/*     */       
/* 246 */       if (AudioFormat.Encoding.PCM_UNSIGNED.equals(encoding) || (AudioFormat.Encoding.PCM_SIGNED
/* 247 */         .equals(encoding) && bool != audioFormat2
/* 248 */         .isBigEndian()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 254 */         inputStream = AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat2
/*     */               
/* 256 */               .getSampleRate(), audioFormat2
/* 257 */               .getSampleSizeInBits(), audioFormat2
/* 258 */               .getChannels(), audioFormat2
/* 259 */               .getFrameSize(), audioFormat2
/* 260 */               .getFrameRate(), bool), (AudioInputStream)paramInputStream);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     byteArrayOutputStream = new ByteArrayOutputStream();
/* 269 */     dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*     */ 
/*     */     
/* 272 */     if (bool) {
/* 273 */       dataOutputStream.writeInt(779316836);
/* 274 */       dataOutputStream.writeInt(b);
/* 275 */       dataOutputStream.writeInt((int)l2);
/* 276 */       dataOutputStream.writeInt(j);
/* 277 */       dataOutputStream.writeInt(k);
/* 278 */       dataOutputStream.writeInt(m);
/*     */     } else {
/* 280 */       dataOutputStream.writeInt(1684960046);
/* 281 */       dataOutputStream.writeInt(big2little(b));
/* 282 */       dataOutputStream.writeInt(big2little((int)l2));
/* 283 */       dataOutputStream.writeInt(big2little(j));
/* 284 */       dataOutputStream.writeInt(big2little(k));
/* 285 */       dataOutputStream.writeInt(big2little(m));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     dataOutputStream.close();
/* 292 */     arrayOfByte = byteArrayOutputStream.toByteArray();
/* 293 */     byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 294 */     sequenceInputStream = new SequenceInputStream(byteArrayInputStream, new SunFileWriter.NoCloseInputStream(this, inputStream));
/*     */ 
/*     */     
/* 297 */     return sequenceInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   private int writeAuFile(InputStream paramInputStream, AuFileFormat paramAuFileFormat, OutputStream paramOutputStream) throws IOException {
/* 302 */     int i = 0;
/* 303 */     int j = 0;
/* 304 */     InputStream inputStream = getFileStream(paramAuFileFormat, paramInputStream);
/* 305 */     byte[] arrayOfByte = new byte[4096];
/* 306 */     int k = paramAuFileFormat.getByteLength();
/*     */     
/* 308 */     while ((i = inputStream.read(arrayOfByte)) >= 0) {
/* 309 */       if (k > 0) {
/* 310 */         if (i < k) {
/* 311 */           paramOutputStream.write(arrayOfByte, 0, i);
/* 312 */           j += i;
/* 313 */           k -= i; continue;
/*     */         } 
/* 315 */         paramOutputStream.write(arrayOfByte, 0, k);
/* 316 */         j += k;
/* 317 */         k = 0;
/*     */         
/*     */         break;
/*     */       } 
/* 321 */       paramOutputStream.write(arrayOfByte, 0, i);
/* 322 */       j += i;
/*     */     } 
/*     */ 
/*     */     
/* 326 */     return j;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AuFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */