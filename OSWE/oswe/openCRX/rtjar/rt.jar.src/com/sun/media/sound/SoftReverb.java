/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftReverb
/*     */   implements SoftAudioProcessor
/*     */ {
/*     */   private float roomsize;
/*     */   private float damp;
/*     */   
/*     */   private static final class Delay
/*     */   {
/*     */     private float[] delaybuffer;
/*  41 */     private int rovepos = 0;
/*     */     
/*     */     Delay() {
/*  44 */       this.delaybuffer = null;
/*     */     }
/*     */     
/*     */     public void setDelay(int param1Int) {
/*  48 */       if (param1Int == 0) {
/*  49 */         this.delaybuffer = null;
/*     */       } else {
/*  51 */         this.delaybuffer = new float[param1Int];
/*  52 */       }  this.rovepos = 0;
/*     */     }
/*     */     
/*     */     public void processReplace(float[] param1ArrayOffloat) {
/*  56 */       if (this.delaybuffer == null)
/*     */         return; 
/*  58 */       int i = param1ArrayOffloat.length;
/*  59 */       int j = this.delaybuffer.length;
/*  60 */       int k = this.rovepos;
/*     */       
/*  62 */       for (byte b = 0; b < i; b++) {
/*  63 */         float f = param1ArrayOffloat[b];
/*  64 */         param1ArrayOffloat[b] = this.delaybuffer[k];
/*  65 */         this.delaybuffer[k] = f;
/*  66 */         if (++k == j)
/*  67 */           k = 0; 
/*     */       } 
/*  69 */       this.rovepos = k;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AllPass
/*     */   {
/*     */     private final float[] delaybuffer;
/*     */     private final int delaybuffersize;
/*  77 */     private int rovepos = 0;
/*     */     private float feedback;
/*     */     
/*     */     AllPass(int param1Int) {
/*  81 */       this.delaybuffer = new float[param1Int];
/*  82 */       this.delaybuffersize = param1Int;
/*     */     }
/*     */     
/*     */     public void setFeedBack(float param1Float) {
/*  86 */       this.feedback = param1Float;
/*     */     }
/*     */     
/*     */     public void processReplace(float[] param1ArrayOffloat) {
/*  90 */       int i = param1ArrayOffloat.length;
/*  91 */       int j = this.delaybuffersize;
/*  92 */       int k = this.rovepos;
/*  93 */       for (byte b = 0; b < i; b++) {
/*  94 */         float f1 = this.delaybuffer[k];
/*  95 */         float f2 = param1ArrayOffloat[b];
/*  96 */         param1ArrayOffloat[b] = f1 - f2;
/*  97 */         this.delaybuffer[k] = f2 + f1 * this.feedback;
/*  98 */         if (++k == j)
/*  99 */           k = 0; 
/*     */       } 
/* 101 */       this.rovepos = k;
/*     */     }
/*     */     
/*     */     public void processReplace(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2) {
/* 105 */       int i = param1ArrayOffloat1.length;
/* 106 */       int j = this.delaybuffersize;
/* 107 */       int k = this.rovepos;
/* 108 */       for (byte b = 0; b < i; b++) {
/* 109 */         float f1 = this.delaybuffer[k];
/* 110 */         float f2 = param1ArrayOffloat1[b];
/* 111 */         param1ArrayOffloat2[b] = f1 - f2;
/* 112 */         this.delaybuffer[k] = f2 + f1 * this.feedback;
/* 113 */         if (++k == j)
/* 114 */           k = 0; 
/*     */       } 
/* 116 */       this.rovepos = k;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Comb
/*     */   {
/*     */     private final float[] delaybuffer;
/*     */     private final int delaybuffersize;
/* 124 */     private int rovepos = 0;
/*     */     private float feedback;
/* 126 */     private float filtertemp = 0.0F;
/* 127 */     private float filtercoeff1 = 0.0F;
/* 128 */     private float filtercoeff2 = 1.0F;
/*     */     
/*     */     Comb(int param1Int) {
/* 131 */       this.delaybuffer = new float[param1Int];
/* 132 */       this.delaybuffersize = param1Int;
/*     */     }
/*     */     
/*     */     public void setFeedBack(float param1Float) {
/* 136 */       this.feedback = param1Float;
/* 137 */       this.filtercoeff2 = (1.0F - this.filtercoeff1) * param1Float;
/*     */     }
/*     */     
/*     */     public void processMix(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2) {
/* 141 */       int i = param1ArrayOffloat1.length;
/* 142 */       int j = this.delaybuffersize;
/* 143 */       int k = this.rovepos;
/* 144 */       float f1 = this.filtertemp;
/* 145 */       float f2 = this.filtercoeff1;
/* 146 */       float f3 = this.filtercoeff2;
/* 147 */       for (byte b = 0; b < i; b++) {
/* 148 */         float f = this.delaybuffer[k];
/*     */         
/* 150 */         f1 = f * f3 + f1 * f2;
/*     */         
/* 152 */         param1ArrayOffloat2[b] = param1ArrayOffloat2[b] + f;
/* 153 */         this.delaybuffer[k] = param1ArrayOffloat1[b] + f1;
/* 154 */         if (++k == j)
/* 155 */           k = 0; 
/*     */       } 
/* 157 */       this.filtertemp = f1;
/* 158 */       this.rovepos = k;
/*     */     }
/*     */     
/*     */     public void processReplace(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2) {
/* 162 */       int i = param1ArrayOffloat1.length;
/* 163 */       int j = this.delaybuffersize;
/* 164 */       int k = this.rovepos;
/* 165 */       float f1 = this.filtertemp;
/* 166 */       float f2 = this.filtercoeff1;
/* 167 */       float f3 = this.filtercoeff2;
/* 168 */       for (byte b = 0; b < i; b++) {
/* 169 */         float f = this.delaybuffer[k];
/*     */         
/* 171 */         f1 = f * f3 + f1 * f2;
/*     */         
/* 173 */         param1ArrayOffloat2[b] = f;
/* 174 */         this.delaybuffer[k] = param1ArrayOffloat1[b] + f1;
/* 175 */         if (++k == j)
/* 176 */           k = 0; 
/*     */       } 
/* 178 */       this.filtertemp = f1;
/* 179 */       this.rovepos = k;
/*     */     }
/*     */     
/*     */     public void setDamp(float param1Float) {
/* 183 */       this.filtercoeff1 = param1Float;
/* 184 */       this.filtercoeff2 = (1.0F - this.filtercoeff1) * this.feedback;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 189 */   private float gain = 1.0F;
/*     */   private Delay delay;
/*     */   private Comb[] combL;
/*     */   private Comb[] combR;
/*     */   private AllPass[] allpassL;
/*     */   private AllPass[] allpassR;
/*     */   private float[] input;
/*     */   private float[] out;
/*     */   private float[] pre1;
/*     */   private float[] pre2;
/*     */   private float[] pre3;
/*     */   private boolean denormal_flip = false;
/*     */   private boolean mix = true;
/*     */   private SoftAudioBuffer inputA;
/*     */   private SoftAudioBuffer left;
/*     */   private SoftAudioBuffer right;
/*     */   private boolean dirty = true;
/*     */   private float dirty_roomsize;
/*     */   private float dirty_damp;
/*     */   private float dirty_predelay;
/*     */   private float dirty_gain;
/*     */   private float samplerate;
/*     */   private boolean light = true;
/*     */   
/*     */   public void init(float paramFloat1, float paramFloat2) {
/* 214 */     this.samplerate = paramFloat1;
/*     */     
/* 216 */     double d = paramFloat1 / 44100.0D;
/*     */ 
/*     */     
/* 219 */     byte b1 = 23;
/*     */     
/* 221 */     this.delay = new Delay();
/*     */     
/* 223 */     this.combL = new Comb[8];
/* 224 */     this.combR = new Comb[8];
/* 225 */     this.combL[0] = new Comb((int)(d * 1116.0D));
/* 226 */     this.combR[0] = new Comb((int)(d * (1116 + b1)));
/* 227 */     this.combL[1] = new Comb((int)(d * 1188.0D));
/* 228 */     this.combR[1] = new Comb((int)(d * (1188 + b1)));
/* 229 */     this.combL[2] = new Comb((int)(d * 1277.0D));
/* 230 */     this.combR[2] = new Comb((int)(d * (1277 + b1)));
/* 231 */     this.combL[3] = new Comb((int)(d * 1356.0D));
/* 232 */     this.combR[3] = new Comb((int)(d * (1356 + b1)));
/* 233 */     this.combL[4] = new Comb((int)(d * 1422.0D));
/* 234 */     this.combR[4] = new Comb((int)(d * (1422 + b1)));
/* 235 */     this.combL[5] = new Comb((int)(d * 1491.0D));
/* 236 */     this.combR[5] = new Comb((int)(d * (1491 + b1)));
/* 237 */     this.combL[6] = new Comb((int)(d * 1557.0D));
/* 238 */     this.combR[6] = new Comb((int)(d * (1557 + b1)));
/* 239 */     this.combL[7] = new Comb((int)(d * 1617.0D));
/* 240 */     this.combR[7] = new Comb((int)(d * (1617 + b1)));
/*     */     
/* 242 */     this.allpassL = new AllPass[4];
/* 243 */     this.allpassR = new AllPass[4];
/* 244 */     this.allpassL[0] = new AllPass((int)(d * 556.0D));
/* 245 */     this.allpassR[0] = new AllPass((int)(d * (556 + b1)));
/* 246 */     this.allpassL[1] = new AllPass((int)(d * 441.0D));
/* 247 */     this.allpassR[1] = new AllPass((int)(d * (441 + b1)));
/* 248 */     this.allpassL[2] = new AllPass((int)(d * 341.0D));
/* 249 */     this.allpassR[2] = new AllPass((int)(d * (341 + b1)));
/* 250 */     this.allpassL[3] = new AllPass((int)(d * 225.0D));
/* 251 */     this.allpassR[3] = new AllPass((int)(d * (225 + b1)));
/*     */     
/* 253 */     for (byte b2 = 0; b2 < this.allpassL.length; b2++) {
/* 254 */       this.allpassL[b2].setFeedBack(0.5F);
/* 255 */       this.allpassR[b2].setFeedBack(0.5F);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     globalParameterControlChange(new int[] { 129 }, 0L, 4L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInput(int paramInt, SoftAudioBuffer paramSoftAudioBuffer) {
/* 264 */     if (paramInt == 0)
/* 265 */       this.inputA = paramSoftAudioBuffer; 
/*     */   }
/*     */   
/*     */   public void setOutput(int paramInt, SoftAudioBuffer paramSoftAudioBuffer) {
/* 269 */     if (paramInt == 0)
/* 270 */       this.left = paramSoftAudioBuffer; 
/* 271 */     if (paramInt == 1)
/* 272 */       this.right = paramSoftAudioBuffer; 
/*     */   }
/*     */   
/*     */   public void setMixMode(boolean paramBoolean) {
/* 276 */     this.mix = paramBoolean;
/*     */   }
/*     */   
/*     */   private boolean silent = true;
/*     */   
/*     */   public void processAudio() {
/* 282 */     boolean bool = this.inputA.isSilent();
/* 283 */     if (!bool)
/* 284 */       this.silent = false; 
/* 285 */     if (this.silent) {
/*     */       
/* 287 */       if (!this.mix) {
/* 288 */         this.left.clear();
/* 289 */         this.right.clear();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 294 */     float[] arrayOfFloat1 = this.inputA.array();
/* 295 */     float[] arrayOfFloat2 = this.left.array();
/* 296 */     float[] arrayOfFloat3 = (this.right == null) ? null : this.right.array();
/*     */     
/* 298 */     int i = arrayOfFloat1.length;
/* 299 */     if (this.input == null || this.input.length < i) {
/* 300 */       this.input = new float[i];
/*     */     }
/* 302 */     float f = this.gain * 0.018F / 2.0F;
/*     */     
/* 304 */     this.denormal_flip = !this.denormal_flip;
/* 305 */     if (this.denormal_flip) {
/* 306 */       for (byte b = 0; b < i; b++)
/* 307 */         this.input[b] = arrayOfFloat1[b] * f + 1.0E-20F; 
/*     */     } else {
/* 309 */       for (byte b = 0; b < i; b++)
/* 310 */         this.input[b] = arrayOfFloat1[b] * f - 1.0E-20F; 
/*     */     } 
/* 312 */     this.delay.processReplace(this.input);
/*     */     
/* 314 */     if (this.light && arrayOfFloat3 != null) {
/*     */       
/* 316 */       if (this.pre1 == null || this.pre1.length < i) {
/*     */         
/* 318 */         this.pre1 = new float[i];
/* 319 */         this.pre2 = new float[i];
/* 320 */         this.pre3 = new float[i];
/*     */       } 
/*     */       int j;
/* 323 */       for (j = 0; j < this.allpassL.length; j++) {
/* 324 */         this.allpassL[j].processReplace(this.input);
/*     */       }
/* 326 */       this.combL[0].processReplace(this.input, this.pre3);
/* 327 */       this.combL[1].processReplace(this.input, this.pre3);
/*     */       
/* 329 */       this.combL[2].processReplace(this.input, this.pre1);
/* 330 */       for (j = 4; j < this.combL.length - 2; j += 2) {
/* 331 */         this.combL[j].processMix(this.input, this.pre1);
/*     */       }
/* 333 */       this.combL[3].processReplace(this.input, this.pre2);
/* 334 */       for (j = 5; j < this.combL.length - 2; j += 2) {
/* 335 */         this.combL[j].processMix(this.input, this.pre2);
/*     */       }
/* 337 */       if (!this.mix) {
/*     */         
/* 339 */         Arrays.fill(arrayOfFloat3, 0.0F);
/* 340 */         Arrays.fill(arrayOfFloat2, 0.0F);
/*     */       } 
/* 342 */       for (j = this.combR.length - 2; j < this.combR.length; j++)
/* 343 */         this.combR[j].processMix(this.input, arrayOfFloat3); 
/* 344 */       for (j = this.combL.length - 2; j < this.combL.length; j++) {
/* 345 */         this.combL[j].processMix(this.input, arrayOfFloat2);
/*     */       }
/* 347 */       for (j = 0; j < i; j++)
/*     */       {
/* 349 */         float f1 = this.pre1[j] - this.pre2[j];
/* 350 */         float f2 = this.pre3[j];
/* 351 */         arrayOfFloat2[j] = arrayOfFloat2[j] + f2 + f1;
/* 352 */         arrayOfFloat3[j] = arrayOfFloat3[j] + f2 - f1;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 357 */       if (this.out == null || this.out.length < i) {
/* 358 */         this.out = new float[i];
/*     */       }
/* 360 */       if (arrayOfFloat3 != null) {
/* 361 */         if (!this.mix)
/* 362 */           Arrays.fill(arrayOfFloat3, 0.0F); 
/* 363 */         this.allpassR[0].processReplace(this.input, this.out); byte b1;
/* 364 */         for (b1 = 1; b1 < this.allpassR.length; b1++)
/* 365 */           this.allpassR[b1].processReplace(this.out); 
/* 366 */         for (b1 = 0; b1 < this.combR.length; b1++) {
/* 367 */           this.combR[b1].processMix(this.out, arrayOfFloat3);
/*     */         }
/*     */       } 
/* 370 */       if (!this.mix)
/* 371 */         Arrays.fill(arrayOfFloat2, 0.0F); 
/* 372 */       this.allpassL[0].processReplace(this.input, this.out); byte b;
/* 373 */       for (b = 1; b < this.allpassL.length; b++)
/* 374 */         this.allpassL[b].processReplace(this.out); 
/* 375 */       for (b = 0; b < this.combL.length; b++) {
/* 376 */         this.combL[b].processMix(this.out, arrayOfFloat2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     if (bool) {
/* 385 */       this.silent = true;
/* 386 */       for (byte b = 0; b < i; b++) {
/*     */         
/* 388 */         float f1 = arrayOfFloat2[b];
/* 389 */         if (f1 > 1.0E-10D || f1 < -1.0E-10D) {
/*     */           
/* 391 */           this.silent = false;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void globalParameterControlChange(int[] paramArrayOfint, long paramLong1, long paramLong2) {
/* 401 */     if (paramArrayOfint.length == 1 && 
/* 402 */       paramArrayOfint[0] == 129)
/*     */     {
/* 404 */       if (paramLong1 == 0L) {
/* 405 */         if (paramLong2 == 0L) {
/*     */ 
/*     */           
/* 408 */           this.dirty_roomsize = 1.1F;
/* 409 */           this.dirty_damp = 5000.0F;
/* 410 */           this.dirty_predelay = 0.0F;
/* 411 */           this.dirty_gain = 4.0F;
/* 412 */           this.dirty = true;
/*     */         } 
/* 414 */         if (paramLong2 == 1L) {
/*     */ 
/*     */           
/* 417 */           this.dirty_roomsize = 1.3F;
/* 418 */           this.dirty_damp = 5000.0F;
/* 419 */           this.dirty_predelay = 0.0F;
/* 420 */           this.dirty_gain = 3.0F;
/* 421 */           this.dirty = true;
/*     */         } 
/* 423 */         if (paramLong2 == 2L) {
/*     */ 
/*     */           
/* 426 */           this.dirty_roomsize = 1.5F;
/* 427 */           this.dirty_damp = 5000.0F;
/* 428 */           this.dirty_predelay = 0.0F;
/* 429 */           this.dirty_gain = 2.0F;
/* 430 */           this.dirty = true;
/*     */         } 
/* 432 */         if (paramLong2 == 3L) {
/*     */           
/* 434 */           this.dirty_roomsize = 1.8F;
/* 435 */           this.dirty_damp = 24000.0F;
/* 436 */           this.dirty_predelay = 0.02F;
/* 437 */           this.dirty_gain = 1.5F;
/* 438 */           this.dirty = true;
/*     */         } 
/* 440 */         if (paramLong2 == 4L) {
/*     */ 
/*     */           
/* 443 */           this.dirty_roomsize = 1.8F;
/* 444 */           this.dirty_damp = 24000.0F;
/* 445 */           this.dirty_predelay = 0.03F;
/* 446 */           this.dirty_gain = 1.5F;
/* 447 */           this.dirty = true;
/*     */         } 
/* 449 */         if (paramLong2 == 8L) {
/*     */           
/* 451 */           this.dirty_roomsize = 1.3F;
/* 452 */           this.dirty_damp = 2500.0F;
/* 453 */           this.dirty_predelay = 0.0F;
/* 454 */           this.dirty_gain = 6.0F;
/* 455 */           this.dirty = true;
/*     */         } 
/* 457 */       } else if (paramLong1 == 1L) {
/* 458 */         this.dirty_roomsize = (float)Math.exp((paramLong2 - 40L) * 0.025D);
/* 459 */         this.dirty = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processControlLogic() {
/* 467 */     if (this.dirty) {
/* 468 */       this.dirty = false;
/* 469 */       setRoomSize(this.dirty_roomsize);
/* 470 */       setDamp(this.dirty_damp);
/* 471 */       setPreDelay(this.dirty_predelay);
/* 472 */       setGain(this.dirty_gain);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRoomSize(float paramFloat) {
/* 477 */     this.roomsize = 1.0F - 0.17F / paramFloat;
/*     */     
/* 479 */     for (byte b = 0; b < this.combL.length; b++) {
/* 480 */       (this.combL[b]).feedback = this.roomsize;
/* 481 */       (this.combR[b]).feedback = this.roomsize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPreDelay(float paramFloat) {
/* 486 */     this.delay.setDelay((int)(paramFloat * this.samplerate));
/*     */   }
/*     */   
/*     */   public void setGain(float paramFloat) {
/* 490 */     this.gain = paramFloat;
/*     */   }
/*     */   
/*     */   public void setDamp(float paramFloat) {
/* 494 */     double d1 = (paramFloat / this.samplerate) * 6.283185307179586D;
/* 495 */     double d2 = 2.0D - Math.cos(d1);
/* 496 */     this.damp = (float)(d2 - Math.sqrt(d2 * d2 - 1.0D));
/* 497 */     if (this.damp > 1.0F)
/* 498 */       this.damp = 1.0F; 
/* 499 */     if (this.damp < 0.0F) {
/* 500 */       this.damp = 0.0F;
/*     */     }
/*     */     
/* 503 */     for (byte b = 0; b < this.combL.length; b++) {
/* 504 */       this.combL[b].setDamp(this.damp);
/* 505 */       this.combR[b].setDamp(this.damp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLightMode(boolean paramBoolean) {
/* 512 */     this.light = paramBoolean;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftReverb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */