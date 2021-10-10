/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftJitterCorrector
/*     */   extends AudioInputStream
/*     */ {
/*     */   private static class JitterStream
/*     */     extends InputStream
/*     */   {
/*  43 */     static int MAX_BUFFER_SIZE = 1048576;
/*     */     
/*     */     boolean active = true;
/*     */     Thread thread;
/*     */     AudioInputStream stream;
/*  48 */     int writepos = 0;
/*  49 */     int readpos = 0;
/*     */     byte[][] buffers;
/*  51 */     private final Object buffers_mutex = new Object();
/*     */ 
/*     */     
/*  54 */     int w_count = 1000;
/*  55 */     int w_min_tol = 2;
/*  56 */     int w_max_tol = 10;
/*  57 */     int w = 0;
/*  58 */     int w_min = -1;
/*     */     
/*  60 */     int bbuffer_pos = 0;
/*  61 */     int bbuffer_max = 0;
/*  62 */     byte[] bbuffer = null;
/*     */     
/*     */     public byte[] nextReadBuffer() {
/*  65 */       synchronized (this.buffers_mutex) {
/*  66 */         if (this.writepos > this.readpos) {
/*  67 */           int i = this.writepos - this.readpos;
/*  68 */           if (i < this.w_min) {
/*  69 */             this.w_min = i;
/*     */           }
/*  71 */           int j = this.readpos;
/*  72 */           this.readpos++;
/*  73 */           return this.buffers[j % this.buffers.length];
/*     */         } 
/*  75 */         this.w_min = -1;
/*  76 */         this.w = this.w_count - 1;
/*     */       } 
/*     */       while (true) {
/*     */         try {
/*  80 */           Thread.sleep(1L);
/*  81 */         } catch (InterruptedException interruptedException) {
/*     */           
/*  83 */           return null;
/*     */         } 
/*  85 */         synchronized (this.buffers_mutex) {
/*  86 */           if (this.writepos > this.readpos) {
/*  87 */             this.w = 0;
/*  88 */             this.w_min = -1;
/*  89 */             this.w = this.w_count - 1;
/*  90 */             int i = this.readpos;
/*  91 */             this.readpos++;
/*  92 */             return this.buffers[i % this.buffers.length];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte[] nextWriteBuffer() {
/*  99 */       synchronized (this.buffers_mutex) {
/* 100 */         return this.buffers[this.writepos % this.buffers.length];
/*     */       } 
/*     */     }
/*     */     
/*     */     public void commit() {
/* 105 */       synchronized (this.buffers_mutex) {
/* 106 */         this.writepos++;
/* 107 */         if (this.writepos - this.readpos > this.buffers.length) {
/* 108 */           int i = this.writepos - this.readpos + 10;
/* 109 */           i = Math.max(this.buffers.length * 2, i);
/* 110 */           this.buffers = new byte[i][(this.buffers[0]).length];
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     JitterStream(AudioInputStream param1AudioInputStream, int param1Int1, int param1Int2) {
/* 117 */       this.w_count = 10 * param1Int1 / param1Int2;
/* 118 */       if (this.w_count < 100)
/* 119 */         this.w_count = 100; 
/* 120 */       this.buffers = new byte[param1Int1 / param1Int2 + 10][param1Int2];
/*     */       
/* 122 */       this.bbuffer_max = MAX_BUFFER_SIZE / param1Int2;
/* 123 */       this.stream = param1AudioInputStream;
/*     */ 
/*     */       
/* 126 */       Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run() {
/* 129 */             AudioFormat audioFormat = SoftJitterCorrector.JitterStream.this.stream.getFormat();
/* 130 */             int i = (SoftJitterCorrector.JitterStream.this.buffers[0]).length;
/* 131 */             int j = i / audioFormat.getFrameSize();
/*     */             
/* 133 */             long l1 = (long)(j * 1.0E9D / audioFormat.getSampleRate());
/* 134 */             long l2 = System.nanoTime();
/* 135 */             long l3 = l2 + l1;
/* 136 */             int k = 0; while (true) {
/*     */               int m;
/* 138 */               synchronized (SoftJitterCorrector.JitterStream.this) {
/* 139 */                 if (!SoftJitterCorrector.JitterStream.this.active) {
/*     */                   break;
/*     */                 }
/*     */               } 
/* 143 */               synchronized (SoftJitterCorrector.JitterStream.this.buffers) {
/* 144 */                 m = SoftJitterCorrector.JitterStream.this.writepos - SoftJitterCorrector.JitterStream.this.readpos;
/* 145 */                 if (!k) {
/* 146 */                   SoftJitterCorrector.JitterStream.this.w++;
/* 147 */                   if (SoftJitterCorrector.JitterStream.this.w_min != Integer.MAX_VALUE && 
/* 148 */                     SoftJitterCorrector.JitterStream.this.w == SoftJitterCorrector.JitterStream.this.w_count) {
/* 149 */                     k = 0;
/* 150 */                     if (SoftJitterCorrector.JitterStream.this.w_min < SoftJitterCorrector.JitterStream.this.w_min_tol) {
/* 151 */                       k = (SoftJitterCorrector.JitterStream.this.w_min_tol + SoftJitterCorrector.JitterStream.this.w_max_tol) / 2 - SoftJitterCorrector.JitterStream.this.w_min;
/*     */                     }
/*     */                     
/* 154 */                     if (SoftJitterCorrector.JitterStream.this.w_min > SoftJitterCorrector.JitterStream.this.w_max_tol) {
/* 155 */                       k = (SoftJitterCorrector.JitterStream.this.w_min_tol + SoftJitterCorrector.JitterStream.this.w_max_tol) / 2 - SoftJitterCorrector.JitterStream.this.w_min;
/*     */                     }
/*     */                     
/* 158 */                     SoftJitterCorrector.JitterStream.this.w = 0;
/* 159 */                     SoftJitterCorrector.JitterStream.this.w_min = Integer.MAX_VALUE;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               
/* 164 */               while (m > SoftJitterCorrector.JitterStream.this.bbuffer_max) {
/* 165 */                 synchronized (SoftJitterCorrector.JitterStream.this.buffers) {
/* 166 */                   m = SoftJitterCorrector.JitterStream.this.writepos - SoftJitterCorrector.JitterStream.this.readpos;
/*     */                 } 
/* 168 */                 synchronized (SoftJitterCorrector.JitterStream.this) {
/* 169 */                   if (!SoftJitterCorrector.JitterStream.this.active)
/*     */                     break; 
/*     */                 } 
/*     */                 try {
/* 173 */                   Thread.sleep(1L);
/* 174 */                 } catch (InterruptedException interruptedException) {}
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 179 */               if (k < 0) {
/* 180 */                 k++;
/*     */               } else {
/* 182 */                 byte[] arrayOfByte = SoftJitterCorrector.JitterStream.this.nextWriteBuffer();
/*     */                 try {
/* 184 */                   int n = 0;
/* 185 */                   while (n != arrayOfByte.length) {
/* 186 */                     int i1 = SoftJitterCorrector.JitterStream.this.stream.read(arrayOfByte, n, arrayOfByte.length - n);
/*     */                     
/* 188 */                     if (i1 < 0)
/* 189 */                       throw new EOFException(); 
/* 190 */                     if (i1 == 0)
/* 191 */                       Thread.yield(); 
/* 192 */                     n += i1;
/*     */                   } 
/* 194 */                 } catch (IOException iOException) {}
/*     */ 
/*     */                 
/* 197 */                 SoftJitterCorrector.JitterStream.this.commit();
/*     */               } 
/*     */               
/* 200 */               if (k > 0) {
/* 201 */                 k--;
/* 202 */                 l3 = System.nanoTime() + l1;
/*     */                 continue;
/*     */               } 
/* 205 */               long l = l3 - System.nanoTime();
/* 206 */               if (l > 0L) {
/*     */                 try {
/* 208 */                   Thread.sleep(l / 1000000L);
/* 209 */                 } catch (InterruptedException interruptedException) {}
/*     */               }
/*     */ 
/*     */               
/* 213 */               l3 += l1;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 218 */       this.thread = new Thread(runnable);
/* 219 */       this.thread.setDaemon(true);
/* 220 */       this.thread.setPriority(10);
/* 221 */       this.thread.start();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 225 */       synchronized (this) {
/* 226 */         this.active = false;
/*     */       } 
/*     */       try {
/* 229 */         this.thread.join();
/* 230 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */       
/* 233 */       this.stream.close();
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 237 */       byte[] arrayOfByte = new byte[1];
/* 238 */       if (read(arrayOfByte) == -1)
/* 239 */         return -1; 
/* 240 */       return arrayOfByte[0] & 0xFF;
/*     */     }
/*     */     
/*     */     public void fillBuffer() {
/* 244 */       this.bbuffer = nextReadBuffer();
/* 245 */       this.bbuffer_pos = 0;
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 249 */       if (this.bbuffer == null)
/* 250 */         fillBuffer(); 
/* 251 */       int i = this.bbuffer.length;
/* 252 */       int j = param1Int1 + param1Int2;
/* 253 */       while (param1Int1 < j) {
/* 254 */         if (available() == 0) {
/* 255 */           fillBuffer(); continue;
/*     */         } 
/* 257 */         byte[] arrayOfByte = this.bbuffer;
/* 258 */         int k = this.bbuffer_pos;
/* 259 */         while (param1Int1 < j && k < i)
/* 260 */           param1ArrayOfbyte[param1Int1++] = arrayOfByte[k++]; 
/* 261 */         this.bbuffer_pos = k;
/*     */       } 
/*     */       
/* 264 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public int available() {
/* 268 */       return this.bbuffer.length - this.bbuffer_pos;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SoftJitterCorrector(AudioInputStream paramAudioInputStream, int paramInt1, int paramInt2) {
/* 274 */     super(new JitterStream(paramAudioInputStream, paramInt1, paramInt2), paramAudioInputStream
/* 275 */         .getFormat(), paramAudioInputStream.getFrameLength());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftJitterCorrector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */