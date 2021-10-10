/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.spi.AudioFileWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class SunFileWriter
/*     */   extends AudioFileWriter
/*     */ {
/*     */   protected static final int bufferSize = 16384;
/*     */   protected static final int bisBufferSize = 4096;
/*     */   final AudioFileFormat.Type[] types;
/*     */   
/*     */   SunFileWriter(AudioFileFormat.Type[] paramArrayOfType) {
/*  63 */     this.types = paramArrayOfType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AudioFileFormat.Type[] getAudioFileTypes() {
/*  73 */     AudioFileFormat.Type[] arrayOfType = new AudioFileFormat.Type[this.types.length];
/*  74 */     System.arraycopy(this.types, 0, arrayOfType, 0, this.types.length);
/*  75 */     return arrayOfType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int rllong(DataInputStream paramDataInputStream) throws IOException {
/* 100 */     int n = 0;
/*     */     
/* 102 */     n = paramDataInputStream.readInt();
/*     */     
/* 104 */     int i = (n & 0xFF) << 24;
/* 105 */     int j = (n & 0xFF00) << 8;
/* 106 */     int k = (n & 0xFF0000) >> 8;
/* 107 */     int m = (n & 0xFF000000) >>> 24;
/*     */     
/* 109 */     n = i | j | k | m;
/*     */     
/* 111 */     return n;
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
/*     */   final int big2little(int paramInt) {
/* 124 */     int i = (paramInt & 0xFF) << 24;
/* 125 */     int j = (paramInt & 0xFF00) << 8;
/* 126 */     int k = (paramInt & 0xFF0000) >> 8;
/* 127 */     int m = (paramInt & 0xFF000000) >>> 24;
/*     */     
/* 129 */     paramInt = i | j | k | m;
/*     */     
/* 131 */     return paramInt;
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
/*     */   final short rlshort(DataInputStream paramDataInputStream) throws IOException {
/* 143 */     short s = 0;
/*     */ 
/*     */     
/* 146 */     s = paramDataInputStream.readShort();
/*     */     
/* 148 */     short s1 = (short)((s & 0xFF) << 8);
/* 149 */     short s2 = (short)((s & 0xFF00) >>> 8);
/*     */     
/* 151 */     s = (short)(s1 | s2);
/*     */     
/* 153 */     return s;
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
/*     */   final short big2littleShort(short paramShort) {
/* 166 */     short s1 = (short)((paramShort & 0xFF) << 8);
/* 167 */     short s2 = (short)((paramShort & 0xFF00) >>> 8);
/*     */     
/* 169 */     paramShort = (short)(s1 | s2);
/*     */     
/* 171 */     return paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final class NoCloseInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final InputStream in;
/*     */ 
/*     */     
/*     */     NoCloseInputStream(InputStream param1InputStream) {
/* 183 */       this.in = param1InputStream;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 188 */       return this.in.read();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 193 */       return this.in.read(param1ArrayOfbyte);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 198 */       return this.in.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 203 */       return this.in.skip(param1Long);
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 208 */       return this.in.available();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mark(int param1Int) {
/* 218 */       this.in.mark(param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset() throws IOException {
/* 223 */       this.in.reset();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean markSupported() {
/* 228 */       return this.in.markSupported();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SunFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */