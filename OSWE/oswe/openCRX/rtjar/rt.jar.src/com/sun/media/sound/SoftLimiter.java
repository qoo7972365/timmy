/*     */ package com.sun.media.sound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftLimiter
/*     */   implements SoftAudioProcessor
/*     */ {
/*  35 */   float lastmax = 0.0F;
/*  36 */   float gain = 1.0F;
/*     */   float[] temp_bufferL;
/*     */   float[] temp_bufferR;
/*     */   boolean mix = false;
/*     */   SoftAudioBuffer bufferL;
/*     */   SoftAudioBuffer bufferR;
/*     */   SoftAudioBuffer bufferLout;
/*     */   SoftAudioBuffer bufferRout;
/*     */   float controlrate;
/*     */   
/*     */   public void init(float paramFloat1, float paramFloat2) {
/*  47 */     this.controlrate = paramFloat2;
/*     */   }
/*     */   
/*     */   public void setInput(int paramInt, SoftAudioBuffer paramSoftAudioBuffer) {
/*  51 */     if (paramInt == 0)
/*  52 */       this.bufferL = paramSoftAudioBuffer; 
/*  53 */     if (paramInt == 1)
/*  54 */       this.bufferR = paramSoftAudioBuffer; 
/*     */   }
/*     */   
/*     */   public void setOutput(int paramInt, SoftAudioBuffer paramSoftAudioBuffer) {
/*  58 */     if (paramInt == 0)
/*  59 */       this.bufferLout = paramSoftAudioBuffer; 
/*  60 */     if (paramInt == 1)
/*  61 */       this.bufferRout = paramSoftAudioBuffer; 
/*     */   }
/*     */   
/*     */   public void setMixMode(boolean paramBoolean) {
/*  65 */     this.mix = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public void globalParameterControlChange(int[] paramArrayOfint, long paramLong1, long paramLong2) {}
/*     */ 
/*     */   
/*  72 */   double silentcounter = 0.0D;
/*     */   
/*     */   public void processAudio() {
/*  75 */     if (this.bufferL.isSilent() && (this.bufferR == null || this.bufferR
/*  76 */       .isSilent())) {
/*  77 */       this.silentcounter += (1.0F / this.controlrate);
/*     */       
/*  79 */       if (this.silentcounter > 60.0D) {
/*  80 */         if (!this.mix) {
/*  81 */           this.bufferLout.clear();
/*  82 */           if (this.bufferRout != null) this.bufferRout.clear(); 
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     } else {
/*  87 */       this.silentcounter = 0.0D;
/*     */     } 
/*  89 */     float[] arrayOfFloat1 = this.bufferL.array();
/*  90 */     float[] arrayOfFloat2 = (this.bufferR == null) ? null : this.bufferR.array();
/*  91 */     float[] arrayOfFloat3 = this.bufferLout.array();
/*     */     
/*  93 */     float[] arrayOfFloat4 = (this.bufferRout == null) ? null : this.bufferRout.array();
/*     */     
/*  95 */     if (this.temp_bufferL == null || this.temp_bufferL.length < arrayOfFloat1.length)
/*  96 */       this.temp_bufferL = new float[arrayOfFloat1.length]; 
/*  97 */     if (arrayOfFloat2 != null && (
/*  98 */       this.temp_bufferR == null || this.temp_bufferR.length < arrayOfFloat2.length)) {
/*  99 */       this.temp_bufferR = new float[arrayOfFloat2.length];
/*     */     }
/* 101 */     float f1 = 0.0F;
/* 102 */     int i = arrayOfFloat1.length;
/*     */     
/* 104 */     if (arrayOfFloat2 == null) {
/* 105 */       for (byte b = 0; b < i; b++) {
/* 106 */         if (arrayOfFloat1[b] > f1)
/* 107 */           f1 = arrayOfFloat1[b]; 
/* 108 */         if (-arrayOfFloat1[b] > f1)
/* 109 */           f1 = -arrayOfFloat1[b]; 
/*     */       } 
/*     */     } else {
/* 112 */       for (byte b = 0; b < i; b++) {
/* 113 */         if (arrayOfFloat1[b] > f1)
/* 114 */           f1 = arrayOfFloat1[b]; 
/* 115 */         if (arrayOfFloat2[b] > f1)
/* 116 */           f1 = arrayOfFloat2[b]; 
/* 117 */         if (-arrayOfFloat1[b] > f1)
/* 118 */           f1 = -arrayOfFloat1[b]; 
/* 119 */         if (-arrayOfFloat2[b] > f1) {
/* 120 */           f1 = -arrayOfFloat2[b];
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     float f2 = this.lastmax;
/* 125 */     this.lastmax = f1;
/* 126 */     if (f2 > f1) {
/* 127 */       f1 = f2;
/*     */     }
/* 129 */     float f3 = 1.0F;
/* 130 */     if (f1 > 0.99F) {
/* 131 */       f3 = 0.99F / f1;
/*     */     } else {
/* 133 */       f3 = 1.0F;
/*     */     } 
/* 135 */     if (f3 > this.gain) {
/* 136 */       f3 = (f3 + this.gain * 9.0F) / 10.0F;
/*     */     }
/* 138 */     float f4 = (f3 - this.gain) / i;
/* 139 */     if (this.mix) {
/* 140 */       if (arrayOfFloat2 == null) {
/* 141 */         for (byte b = 0; b < i; b++) {
/* 142 */           this.gain += f4;
/* 143 */           float f5 = arrayOfFloat1[b];
/* 144 */           float f6 = this.temp_bufferL[b];
/* 145 */           this.temp_bufferL[b] = f5;
/* 146 */           arrayOfFloat3[b] = arrayOfFloat3[b] + f6 * this.gain;
/*     */         } 
/*     */       } else {
/* 149 */         for (byte b = 0; b < i; b++) {
/* 150 */           this.gain += f4;
/* 151 */           float f5 = arrayOfFloat1[b];
/* 152 */           float f6 = arrayOfFloat2[b];
/* 153 */           float f7 = this.temp_bufferL[b];
/* 154 */           float f8 = this.temp_bufferR[b];
/* 155 */           this.temp_bufferL[b] = f5;
/* 156 */           this.temp_bufferR[b] = f6;
/* 157 */           arrayOfFloat3[b] = arrayOfFloat3[b] + f7 * this.gain;
/* 158 */           arrayOfFloat4[b] = arrayOfFloat4[b] + f8 * this.gain;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 163 */     } else if (arrayOfFloat2 == null) {
/* 164 */       for (byte b = 0; b < i; b++) {
/* 165 */         this.gain += f4;
/* 166 */         float f5 = arrayOfFloat1[b];
/* 167 */         float f6 = this.temp_bufferL[b];
/* 168 */         this.temp_bufferL[b] = f5;
/* 169 */         arrayOfFloat3[b] = f6 * this.gain;
/*     */       } 
/*     */     } else {
/* 172 */       for (byte b = 0; b < i; b++) {
/* 173 */         this.gain += f4;
/* 174 */         float f5 = arrayOfFloat1[b];
/* 175 */         float f6 = arrayOfFloat2[b];
/* 176 */         float f7 = this.temp_bufferL[b];
/* 177 */         float f8 = this.temp_bufferR[b];
/* 178 */         this.temp_bufferL[b] = f5;
/* 179 */         this.temp_bufferR[b] = f6;
/* 180 */         arrayOfFloat3[b] = f7 * this.gain;
/* 181 */         arrayOfFloat4[b] = f8 * this.gain;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 186 */     this.gain = f3;
/*     */   }
/*     */   
/*     */   public void processControlLogic() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftLimiter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */