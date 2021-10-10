/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.midi.MidiChannel;
/*     */ import javax.sound.midi.VoiceStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SoftAbstractResampler
/*     */   implements SoftResampler
/*     */ {
/*     */   public abstract int getPadding();
/*     */   
/*     */   public abstract void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt);
/*     */   
/*     */   private class ModelAbstractResamplerStream
/*     */     implements SoftResamplerStreamer
/*     */   {
/*     */     AudioFloatInputStream stream;
/*     */     boolean stream_eof = false;
/*     */     int loopmode;
/*     */     boolean loopdirection = true;
/*     */     float loopstart;
/*     */     float looplen;
/*     */     float target_pitch;
/*  49 */     float[] current_pitch = new float[1];
/*     */     boolean started;
/*     */     boolean eof;
/*  52 */     int sector_pos = 0;
/*  53 */     int sector_size = 400;
/*  54 */     int sector_loopstart = -1;
/*     */     boolean markset = false;
/*  56 */     int marklimit = 0;
/*  57 */     int streampos = 0;
/*  58 */     int nrofchannels = 2;
/*     */     boolean noteOff_flag = false;
/*     */     float[][] ibuffer;
/*     */     boolean ibuffer_order = true;
/*     */     float[] sbuffer;
/*     */     int pad;
/*     */     int pad2;
/*  65 */     float[] ix = new float[1];
/*  66 */     int[] ox = new int[1];
/*  67 */     float samplerateconv = 1.0F;
/*  68 */     float pitchcorrection = 0.0F;
/*     */     
/*     */     ModelAbstractResamplerStream() {
/*  71 */       this.pad = SoftAbstractResampler.this.getPadding();
/*  72 */       this.pad2 = SoftAbstractResampler.this.getPadding() * 2;
/*  73 */       this.ibuffer = new float[2][this.sector_size + this.pad2];
/*  74 */       this.ibuffer_order = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void noteOn(MidiChannel param1MidiChannel, VoiceStatus param1VoiceStatus, int param1Int1, int param1Int2) {}
/*     */ 
/*     */     
/*     */     public void noteOff(int param1Int) {
/*  82 */       this.noteOff_flag = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void open(ModelWavetable param1ModelWavetable, float param1Float) throws IOException {
/*  88 */       this.eof = false;
/*  89 */       this.nrofchannels = param1ModelWavetable.getChannels();
/*  90 */       if (this.ibuffer.length < this.nrofchannels) {
/*  91 */         this.ibuffer = new float[this.nrofchannels][this.sector_size + this.pad2];
/*     */       }
/*     */       
/*  94 */       this.stream = param1ModelWavetable.openStream();
/*  95 */       this.streampos = 0;
/*  96 */       this.stream_eof = false;
/*  97 */       this.pitchcorrection = param1ModelWavetable.getPitchcorrection();
/*  98 */       this
/*  99 */         .samplerateconv = this.stream.getFormat().getSampleRate() / param1Float;
/* 100 */       this.looplen = param1ModelWavetable.getLoopLength();
/* 101 */       this.loopstart = param1ModelWavetable.getLoopStart();
/* 102 */       this.sector_loopstart = (int)(this.loopstart / this.sector_size);
/* 103 */       this.sector_loopstart--;
/*     */       
/* 105 */       this.sector_pos = 0;
/*     */       
/* 107 */       if (this.sector_loopstart < 0)
/* 108 */         this.sector_loopstart = 0; 
/* 109 */       this.started = false;
/* 110 */       this.loopmode = param1ModelWavetable.getLoopType();
/*     */       
/* 112 */       if (this.loopmode != 0) {
/* 113 */         this.markset = false;
/* 114 */         this.marklimit = this.nrofchannels * (int)(this.looplen + this.pad2 + 1.0F);
/*     */       } else {
/* 116 */         this.markset = true;
/*     */       } 
/*     */       
/* 119 */       this.target_pitch = this.samplerateconv;
/* 120 */       this.current_pitch[0] = this.samplerateconv;
/*     */       
/* 122 */       this.ibuffer_order = true;
/* 123 */       this.loopdirection = true;
/* 124 */       this.noteOff_flag = false;
/*     */       
/* 126 */       for (byte b = 0; b < this.nrofchannels; b++)
/* 127 */         Arrays.fill(this.ibuffer[b], this.sector_size, this.sector_size + this.pad2, 0.0F); 
/* 128 */       this.ix[0] = this.pad;
/* 129 */       this.eof = false;
/*     */       
/* 131 */       this.ix[0] = (this.sector_size + this.pad);
/* 132 */       this.sector_pos = -1;
/* 133 */       this.streampos = -this.sector_size;
/*     */       
/* 135 */       nextBuffer();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPitch(float param1Float) {
/* 144 */       this.target_pitch = (float)Math.exp((this.pitchcorrection + param1Float) * 
/* 145 */           Math.log(2.0D) / 1200.0D) * this.samplerateconv;
/*     */ 
/*     */       
/* 148 */       if (!this.started)
/* 149 */         this.current_pitch[0] = this.target_pitch; 
/*     */     }
/*     */     
/*     */     public void nextBuffer() throws IOException {
/* 153 */       if (this.ix[0] < this.pad && 
/* 154 */         this.markset) {
/*     */         
/* 156 */         this.stream.reset();
/* 157 */         this.ix[0] = this.ix[0] + (this.streampos - this.sector_loopstart * this.sector_size);
/* 158 */         this.sector_pos = this.sector_loopstart;
/* 159 */         this.streampos = this.sector_pos * this.sector_size;
/*     */ 
/*     */         
/* 162 */         this.ix[0] = this.ix[0] + this.sector_size;
/* 163 */         this.sector_pos--;
/* 164 */         this.streampos -= this.sector_size;
/* 165 */         this.stream_eof = false;
/*     */       } 
/*     */ 
/*     */       
/* 169 */       if (this.ix[0] >= (this.sector_size + this.pad) && 
/* 170 */         this.stream_eof) {
/* 171 */         this.eof = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 176 */       if (this.ix[0] >= (this.sector_size * 4 + this.pad)) {
/* 177 */         int i = (int)((this.ix[0] - (this.sector_size * 4) + this.pad) / this.sector_size);
/* 178 */         this.ix[0] = this.ix[0] - (this.sector_size * i);
/* 179 */         this.sector_pos += i;
/* 180 */         this.streampos += this.sector_size * i;
/* 181 */         this.stream.skip((this.sector_size * i));
/*     */       } 
/*     */       
/* 184 */       while (this.ix[0] >= (this.sector_size + this.pad)) {
/* 185 */         if (!this.markset && 
/* 186 */           this.sector_pos + 1 == this.sector_loopstart) {
/* 187 */           this.stream.mark(this.marklimit);
/* 188 */           this.markset = true;
/*     */         } 
/*     */         
/* 191 */         this.ix[0] = this.ix[0] - this.sector_size;
/* 192 */         this.sector_pos++;
/* 193 */         this.streampos += this.sector_size;
/*     */         int i;
/* 195 */         for (i = 0; i < this.nrofchannels; i++) {
/* 196 */           float[] arrayOfFloat = this.ibuffer[i];
/* 197 */           for (byte b = 0; b < this.pad2; b++) {
/* 198 */             arrayOfFloat[b] = arrayOfFloat[b + this.sector_size];
/*     */           }
/*     */         } 
/*     */         
/* 202 */         if (this.nrofchannels == 1) {
/* 203 */           i = this.stream.read(this.ibuffer[0], this.pad2, this.sector_size);
/*     */         } else {
/* 205 */           int j = this.sector_size * this.nrofchannels;
/* 206 */           if (this.sbuffer == null || this.sbuffer.length < j)
/* 207 */             this.sbuffer = new float[j]; 
/* 208 */           int k = this.stream.read(this.sbuffer, 0, j);
/* 209 */           if (k == -1) {
/* 210 */             i = -1;
/*     */           } else {
/* 212 */             i = k / this.nrofchannels;
/* 213 */             for (byte b = 0; b < this.nrofchannels; b++) {
/* 214 */               float[] arrayOfFloat = this.ibuffer[b];
/* 215 */               int m = b;
/* 216 */               int n = this.nrofchannels;
/* 217 */               int i1 = this.pad2;
/* 218 */               for (byte b1 = 0; b1 < i; b1++, m += n, i1++) {
/* 219 */                 arrayOfFloat[i1] = this.sbuffer[m];
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 225 */         if (i == -1) {
/* 226 */           i = 0;
/* 227 */           this.stream_eof = true;
/* 228 */           for (byte b = 0; b < this.nrofchannels; b++)
/* 229 */             Arrays.fill(this.ibuffer[b], this.pad2, this.pad2 + this.sector_size, 0.0F); 
/*     */           return;
/*     */         } 
/* 232 */         if (i != this.sector_size) {
/* 233 */           for (byte b = 0; b < this.nrofchannels; b++) {
/* 234 */             Arrays.fill(this.ibuffer[b], this.pad2 + i, this.pad2 + this.sector_size, 0.0F);
/*     */           }
/*     */         }
/* 237 */         this.ibuffer_order = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void reverseBuffers() {
/* 244 */       this.ibuffer_order = !this.ibuffer_order;
/* 245 */       for (byte b = 0; b < this.nrofchannels; b++) {
/* 246 */         float[] arrayOfFloat = this.ibuffer[b];
/* 247 */         int i = arrayOfFloat.length - 1;
/* 248 */         int j = arrayOfFloat.length / 2;
/* 249 */         for (byte b1 = 0; b1 < j; b1++) {
/* 250 */           float f = arrayOfFloat[b1];
/* 251 */           arrayOfFloat[b1] = arrayOfFloat[i - b1];
/* 252 */           arrayOfFloat[i - b1] = f;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(float[][] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 260 */       if (this.eof) {
/* 261 */         return -1;
/*     */       }
/* 263 */       if (this.noteOff_flag && (
/* 264 */         this.loopmode & 0x2) != 0 && 
/* 265 */         this.loopdirection) {
/* 266 */         this.loopmode = 0;
/*     */       }
/*     */       
/* 269 */       float f1 = (this.target_pitch - this.current_pitch[0]) / param1Int2;
/* 270 */       float[] arrayOfFloat = this.current_pitch;
/* 271 */       this.started = true;
/*     */       
/* 273 */       int[] arrayOfInt = this.ox;
/* 274 */       arrayOfInt[0] = param1Int1;
/* 275 */       int i = param1Int2 + param1Int1;
/*     */       
/* 277 */       float f2 = (this.sector_size + this.pad);
/* 278 */       if (!this.loopdirection)
/* 279 */         f2 = this.pad; 
/* 280 */       while (arrayOfInt[0] != i) {
/* 281 */         nextBuffer();
/* 282 */         if (!this.loopdirection) {
/*     */ 
/*     */ 
/*     */           
/* 286 */           if (this.streampos < this.loopstart + this.pad) {
/* 287 */             f2 = this.loopstart - this.streampos + this.pad2;
/* 288 */             if (this.ix[0] <= f2) {
/* 289 */               if ((this.loopmode & 0x4) != 0) {
/*     */                 
/* 291 */                 this.loopdirection = true;
/* 292 */                 f2 = (this.sector_size + this.pad);
/*     */                 
/*     */                 continue;
/*     */               } 
/* 296 */               this.ix[0] = this.ix[0] + this.looplen;
/* 297 */               f2 = this.pad;
/*     */               
/*     */               continue;
/*     */             } 
/*     */           } 
/* 302 */           if (this.ibuffer_order != this.loopdirection) {
/* 303 */             reverseBuffers();
/*     */           }
/* 305 */           this.ix[0] = (this.sector_size + this.pad2) - this.ix[0];
/* 306 */           f2 = (this.sector_size + this.pad2) - f2;
/* 307 */           f2++;
/*     */           
/* 309 */           float f5 = this.ix[0];
/* 310 */           int k = arrayOfInt[0];
/* 311 */           float f6 = arrayOfFloat[0];
/* 312 */           for (byte b1 = 0; b1 < this.nrofchannels; b1++) {
/* 313 */             if (param1ArrayOffloat[b1] != null) {
/* 314 */               this.ix[0] = f5;
/* 315 */               arrayOfInt[0] = k;
/* 316 */               arrayOfFloat[0] = f6;
/* 317 */               SoftAbstractResampler.this.interpolate(this.ibuffer[b1], this.ix, f2, arrayOfFloat, f1, param1ArrayOffloat[b1], arrayOfInt, i);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 322 */           this.ix[0] = (this.sector_size + this.pad2) - this.ix[0];
/* 323 */           f2--;
/* 324 */           f2 = (this.sector_size + this.pad2) - f2;
/*     */           
/* 326 */           if (this.eof) {
/* 327 */             arrayOfFloat[0] = this.target_pitch;
/* 328 */             return arrayOfInt[0] - param1Int1;
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 333 */         if (this.loopmode != 0 && (
/* 334 */           this.streampos + this.sector_size) > this.looplen + this.loopstart + this.pad) {
/* 335 */           f2 = this.loopstart + this.looplen - this.streampos + this.pad2;
/* 336 */           if (this.ix[0] >= f2) {
/* 337 */             if ((this.loopmode & 0x4) != 0 || (this.loopmode & 0x8) != 0) {
/*     */               
/* 339 */               this.loopdirection = false;
/* 340 */               f2 = this.pad;
/*     */               continue;
/*     */             } 
/* 343 */             f2 = (this.sector_size + this.pad);
/* 344 */             this.ix[0] = this.ix[0] - this.looplen;
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/* 350 */         if (this.ibuffer_order != this.loopdirection) {
/* 351 */           reverseBuffers();
/*     */         }
/* 353 */         float f3 = this.ix[0];
/* 354 */         int j = arrayOfInt[0];
/* 355 */         float f4 = arrayOfFloat[0];
/* 356 */         for (byte b = 0; b < this.nrofchannels; b++) {
/* 357 */           if (param1ArrayOffloat[b] != null) {
/* 358 */             this.ix[0] = f3;
/* 359 */             arrayOfInt[0] = j;
/* 360 */             arrayOfFloat[0] = f4;
/* 361 */             SoftAbstractResampler.this.interpolate(this.ibuffer[b], this.ix, f2, arrayOfFloat, f1, param1ArrayOffloat[b], arrayOfInt, i);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 366 */         if (this.eof) {
/* 367 */           arrayOfFloat[0] = this.target_pitch;
/* 368 */           return arrayOfInt[0] - param1Int1;
/*     */         } 
/*     */       } 
/*     */       
/* 372 */       arrayOfFloat[0] = this.target_pitch;
/* 373 */       return param1Int2;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 377 */       this.stream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SoftResamplerStreamer openStreamer() {
/* 388 */     return new ModelAbstractResamplerStream();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftAbstractResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */