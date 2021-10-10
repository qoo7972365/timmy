/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftVoice
/*     */   extends VoiceStatus
/*     */ {
/*  41 */   public int exclusiveClass = 0;
/*     */   public boolean releaseTriggered = false;
/*  43 */   private int noteOn_noteNumber = 0;
/*  44 */   private int noteOn_velocity = 0;
/*  45 */   private int noteOff_velocity = 0;
/*  46 */   private int delay = 0;
/*  47 */   ModelChannelMixer channelmixer = null;
/*  48 */   double tunedKey = 0.0D;
/*  49 */   SoftTuning tuning = null;
/*  50 */   SoftChannel stealer_channel = null;
/*  51 */   ModelConnectionBlock[] stealer_extendedConnectionBlocks = null;
/*  52 */   SoftPerformer stealer_performer = null;
/*  53 */   ModelChannelMixer stealer_channelmixer = null;
/*  54 */   int stealer_voiceID = -1;
/*  55 */   int stealer_noteNumber = 0;
/*  56 */   int stealer_velocity = 0;
/*     */   boolean stealer_releaseTriggered = false;
/*  58 */   int voiceID = -1;
/*     */   boolean sustain = false;
/*     */   boolean sostenuto = false;
/*     */   boolean portamento = false;
/*     */   private final SoftFilter filter_left;
/*     */   private final SoftFilter filter_right;
/*  64 */   private final SoftProcess eg = new SoftEnvelopeGenerator();
/*  65 */   private final SoftProcess lfo = new SoftLowFrequencyOscillator();
/*  66 */   Map<String, SoftControl> objects = new HashMap<>();
/*     */   
/*     */   SoftSynthesizer synthesizer;
/*     */   SoftInstrument instrument;
/*     */   SoftPerformer performer;
/*  71 */   SoftChannel softchannel = null;
/*     */   boolean on = false;
/*     */   private boolean audiostarted = false;
/*     */   private boolean started = false;
/*     */   private boolean stopping = false;
/*  76 */   private float osc_attenuation = 0.0F;
/*     */   private ModelOscillatorStream osc_stream;
/*     */   private int osc_stream_nrofchannels;
/*  79 */   private float[][] osc_buff = new float[2][];
/*     */   private boolean osc_stream_off_transmitted = false;
/*     */   private boolean out_mixer_end = false;
/*  82 */   private float out_mixer_left = 0.0F;
/*  83 */   private float out_mixer_right = 0.0F;
/*  84 */   private float out_mixer_effect1 = 0.0F;
/*  85 */   private float out_mixer_effect2 = 0.0F;
/*  86 */   private float last_out_mixer_left = 0.0F;
/*  87 */   private float last_out_mixer_right = 0.0F;
/*  88 */   private float last_out_mixer_effect1 = 0.0F;
/*  89 */   private float last_out_mixer_effect2 = 0.0F;
/*  90 */   ModelConnectionBlock[] extendedConnectionBlocks = null;
/*     */   
/*     */   private ModelConnectionBlock[] connections;
/*  93 */   private double[] connections_last = new double[50];
/*     */   
/*  95 */   private double[][][] connections_src = new double[50][3][];
/*     */   
/*  97 */   private int[][] connections_src_kc = new int[50][3];
/*     */   
/*  99 */   private double[][] connections_dst = new double[50][];
/*     */   private boolean soundoff = false;
/* 101 */   private float lastMuteValue = 0.0F;
/* 102 */   private float lastSoloMuteValue = 0.0F;
/* 103 */   double[] co_noteon_keynumber = new double[1];
/* 104 */   double[] co_noteon_velocity = new double[1];
/* 105 */   double[] co_noteon_on = new double[1];
/* 106 */   private final SoftControl co_noteon = new SoftControl() {
/* 107 */       double[] keynumber = SoftVoice.this.co_noteon_keynumber;
/* 108 */       double[] velocity = SoftVoice.this.co_noteon_velocity;
/* 109 */       double[] on = SoftVoice.this.co_noteon_on;
/*     */       public double[] get(int param1Int, String param1String) {
/* 111 */         if (param1String == null)
/* 112 */           return null; 
/* 113 */         if (param1String.equals("keynumber"))
/* 114 */           return this.keynumber; 
/* 115 */         if (param1String.equals("velocity"))
/* 116 */           return this.velocity; 
/* 117 */         if (param1String.equals("on"))
/* 118 */           return this.on; 
/* 119 */         return null;
/*     */       }
/*     */     };
/* 122 */   private final double[] co_mixer_active = new double[1];
/* 123 */   private final double[] co_mixer_gain = new double[1];
/* 124 */   private final double[] co_mixer_pan = new double[1];
/* 125 */   private final double[] co_mixer_balance = new double[1];
/* 126 */   private final double[] co_mixer_reverb = new double[1];
/* 127 */   private final double[] co_mixer_chorus = new double[1];
/* 128 */   private final SoftControl co_mixer = new SoftControl() {
/* 129 */       double[] active = SoftVoice.this.co_mixer_active;
/* 130 */       double[] gain = SoftVoice.this.co_mixer_gain;
/* 131 */       double[] pan = SoftVoice.this.co_mixer_pan;
/* 132 */       double[] balance = SoftVoice.this.co_mixer_balance;
/* 133 */       double[] reverb = SoftVoice.this.co_mixer_reverb;
/* 134 */       double[] chorus = SoftVoice.this.co_mixer_chorus;
/*     */       public double[] get(int param1Int, String param1String) {
/* 136 */         if (param1String == null)
/* 137 */           return null; 
/* 138 */         if (param1String.equals("active"))
/* 139 */           return this.active; 
/* 140 */         if (param1String.equals("gain"))
/* 141 */           return this.gain; 
/* 142 */         if (param1String.equals("pan"))
/* 143 */           return this.pan; 
/* 144 */         if (param1String.equals("balance"))
/* 145 */           return this.balance; 
/* 146 */         if (param1String.equals("reverb"))
/* 147 */           return this.reverb; 
/* 148 */         if (param1String.equals("chorus"))
/* 149 */           return this.chorus; 
/* 150 */         return null;
/*     */       }
/*     */     };
/* 153 */   private final double[] co_osc_pitch = new double[1];
/* 154 */   private final SoftControl co_osc = new SoftControl() {
/* 155 */       double[] pitch = SoftVoice.this.co_osc_pitch;
/*     */       public double[] get(int param1Int, String param1String) {
/* 157 */         if (param1String == null)
/* 158 */           return null; 
/* 159 */         if (param1String.equals("pitch"))
/* 160 */           return this.pitch; 
/* 161 */         return null;
/*     */       }
/*     */     };
/* 164 */   private final double[] co_filter_freq = new double[1];
/* 165 */   private final double[] co_filter_type = new double[1];
/* 166 */   private final double[] co_filter_q = new double[1];
/* 167 */   private final SoftControl co_filter = new SoftControl() {
/* 168 */       double[] freq = SoftVoice.this.co_filter_freq;
/* 169 */       double[] ftype = SoftVoice.this.co_filter_type;
/* 170 */       double[] q = SoftVoice.this.co_filter_q;
/*     */       public double[] get(int param1Int, String param1String) {
/* 172 */         if (param1String == null)
/* 173 */           return null; 
/* 174 */         if (param1String.equals("freq"))
/* 175 */           return this.freq; 
/* 176 */         if (param1String.equals("type"))
/* 177 */           return this.ftype; 
/* 178 */         if (param1String.equals("q"))
/* 179 */           return this.q; 
/* 180 */         return null;
/*     */       }
/*     */     };
/*     */   SoftResamplerStreamer resampler;
/*     */   private final int nrofchannels;
/*     */   
/*     */   public SoftVoice(SoftSynthesizer paramSoftSynthesizer) {
/* 187 */     this.synthesizer = paramSoftSynthesizer;
/* 188 */     this.filter_left = new SoftFilter(paramSoftSynthesizer.getFormat().getSampleRate());
/* 189 */     this.filter_right = new SoftFilter(paramSoftSynthesizer.getFormat().getSampleRate());
/* 190 */     this.nrofchannels = paramSoftSynthesizer.getFormat().getChannels();
/*     */   }
/*     */   
/*     */   private int getValueKC(ModelIdentifier paramModelIdentifier) {
/* 194 */     if (paramModelIdentifier.getObject().equals("midi_cc")) {
/* 195 */       int i = Integer.parseInt(paramModelIdentifier.getVariable());
/* 196 */       if (i != 0 && i != 32 && 
/* 197 */         i < 120) {
/* 198 */         return i;
/*     */       }
/* 200 */     } else if (paramModelIdentifier.getObject().equals("midi_rpn")) {
/* 201 */       if (paramModelIdentifier.getVariable().equals("1"))
/* 202 */         return 120; 
/* 203 */       if (paramModelIdentifier.getVariable().equals("2"))
/* 204 */         return 121; 
/*     */     } 
/* 206 */     return -1;
/*     */   }
/*     */   
/*     */   private double[] getValue(ModelIdentifier paramModelIdentifier) {
/* 210 */     SoftControl softControl = this.objects.get(paramModelIdentifier.getObject());
/* 211 */     if (softControl == null)
/* 212 */       return null; 
/* 213 */     return softControl.get(paramModelIdentifier.getInstance(), paramModelIdentifier.getVariable());
/*     */   }
/*     */   
/*     */   private double transformValue(double paramDouble, ModelSource paramModelSource) {
/* 217 */     if (paramModelSource.getTransform() != null) {
/* 218 */       return paramModelSource.getTransform().transform(paramDouble);
/*     */     }
/* 220 */     return paramDouble;
/*     */   }
/*     */   
/*     */   private double transformValue(double paramDouble, ModelDestination paramModelDestination) {
/* 224 */     if (paramModelDestination.getTransform() != null) {
/* 225 */       return paramModelDestination.getTransform().transform(paramDouble);
/*     */     }
/* 227 */     return paramDouble;
/*     */   }
/*     */   
/*     */   private double processKeyBasedController(double paramDouble, int paramInt) {
/* 231 */     if (paramInt == -1)
/* 232 */       return paramDouble; 
/* 233 */     if (this.softchannel.keybasedcontroller_active != null && 
/* 234 */       this.softchannel.keybasedcontroller_active[this.note] != null && 
/* 235 */       this.softchannel.keybasedcontroller_active[this.note][paramInt]) {
/* 236 */       double d = this.softchannel.keybasedcontroller_value[this.note][paramInt];
/*     */       
/* 238 */       if (paramInt == 10 || paramInt == 91 || paramInt == 93)
/* 239 */         return d; 
/* 240 */       paramDouble += d * 2.0D - 1.0D;
/* 241 */       if (paramDouble > 1.0D) {
/* 242 */         paramDouble = 1.0D;
/* 243 */       } else if (paramDouble < 0.0D) {
/* 244 */         paramDouble = 0.0D;
/*     */       } 
/* 246 */     }  return paramDouble;
/*     */   }
/*     */   
/*     */   private void processConnection(int paramInt) {
/* 250 */     ModelConnectionBlock modelConnectionBlock = this.connections[paramInt];
/* 251 */     double[][] arrayOfDouble = this.connections_src[paramInt];
/* 252 */     double[] arrayOfDouble1 = this.connections_dst[paramInt];
/* 253 */     if (arrayOfDouble1 == null || Double.isInfinite(arrayOfDouble1[0])) {
/*     */       return;
/*     */     }
/* 256 */     double d = modelConnectionBlock.getScale();
/* 257 */     if (this.softchannel.keybasedcontroller_active == null) {
/* 258 */       ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 259 */       for (byte b = 0; b < arrayOfModelSource.length; b++) {
/* 260 */         d *= transformValue(arrayOfDouble[b][0], arrayOfModelSource[b]);
/* 261 */         if (d == 0.0D)
/*     */           break; 
/*     */       } 
/*     */     } else {
/* 265 */       ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 266 */       int[] arrayOfInt = this.connections_src_kc[paramInt];
/* 267 */       for (byte b = 0; b < arrayOfModelSource.length; b++) {
/* 268 */         d *= transformValue(processKeyBasedController(arrayOfDouble[b][0], arrayOfInt[b]), arrayOfModelSource[b]);
/*     */         
/* 270 */         if (d == 0.0D) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 275 */     d = transformValue(d, modelConnectionBlock.getDestination());
/* 276 */     arrayOfDouble1[0] = arrayOfDouble1[0] - this.connections_last[paramInt] + d;
/* 277 */     this.connections_last[paramInt] = d;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateTuning(SoftTuning paramSoftTuning) {
/* 282 */     this.tuning = paramSoftTuning;
/* 283 */     this.tunedKey = this.tuning.getTuning(this.note) / 100.0D;
/* 284 */     if (!this.portamento) {
/* 285 */       this.co_noteon_keynumber[0] = this.tunedKey * 0.0078125D;
/* 286 */       if (this.performer == null)
/*     */         return; 
/* 288 */       int[] arrayOfInt = this.performer.midi_connections[4];
/* 289 */       if (arrayOfInt == null)
/*     */         return; 
/* 291 */       for (byte b = 0; b < arrayOfInt.length; b++)
/* 292 */         processConnection(arrayOfInt[b]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setNote(int paramInt) {
/* 297 */     this.note = paramInt;
/* 298 */     this.tunedKey = this.tuning.getTuning(paramInt) / 100.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   void noteOn(int paramInt1, int paramInt2, int paramInt3) {
/* 303 */     this.sustain = false;
/* 304 */     this.sostenuto = false;
/* 305 */     this.portamento = false;
/*     */     
/* 307 */     this.soundoff = false;
/* 308 */     this.on = true;
/* 309 */     this.active = true;
/* 310 */     this.started = true;
/*     */ 
/*     */     
/* 313 */     this.noteOn_noteNumber = paramInt1;
/* 314 */     this.noteOn_velocity = paramInt2;
/* 315 */     this.delay = paramInt3;
/*     */     
/* 317 */     this.lastMuteValue = 0.0F;
/* 318 */     this.lastSoloMuteValue = 0.0F;
/*     */     
/* 320 */     setNote(paramInt1);
/*     */     
/* 322 */     if (this.performer.forcedKeynumber) {
/* 323 */       this.co_noteon_keynumber[0] = 0.0D;
/*     */     } else {
/* 325 */       this.co_noteon_keynumber[0] = this.tunedKey * 0.0078125D;
/* 326 */     }  if (this.performer.forcedVelocity) {
/* 327 */       this.co_noteon_velocity[0] = 0.0D;
/*     */     } else {
/* 329 */       this.co_noteon_velocity[0] = (paramInt2 * 0.0078125F);
/* 330 */     }  this.co_mixer_active[0] = 0.0D;
/* 331 */     this.co_mixer_gain[0] = 0.0D;
/* 332 */     this.co_mixer_pan[0] = 0.0D;
/* 333 */     this.co_mixer_balance[0] = 0.0D;
/* 334 */     this.co_mixer_reverb[0] = 0.0D;
/* 335 */     this.co_mixer_chorus[0] = 0.0D;
/* 336 */     this.co_osc_pitch[0] = 0.0D;
/* 337 */     this.co_filter_freq[0] = 0.0D;
/* 338 */     this.co_filter_q[0] = 0.0D;
/* 339 */     this.co_filter_type[0] = 0.0D;
/* 340 */     this.co_noteon_on[0] = 1.0D;
/*     */     
/* 342 */     this.eg.reset();
/* 343 */     this.lfo.reset();
/* 344 */     this.filter_left.reset();
/* 345 */     this.filter_right.reset();
/*     */     
/* 347 */     this.objects.put("master", (this.synthesizer.getMainMixer()).co_master);
/* 348 */     this.objects.put("eg", this.eg);
/* 349 */     this.objects.put("lfo", this.lfo);
/* 350 */     this.objects.put("noteon", this.co_noteon);
/* 351 */     this.objects.put("osc", this.co_osc);
/* 352 */     this.objects.put("mixer", this.co_mixer);
/* 353 */     this.objects.put("filter", this.co_filter);
/*     */     
/* 355 */     this.connections = this.performer.connections;
/*     */     
/* 357 */     if (this.connections_last == null || this.connections_last.length < this.connections.length)
/*     */     {
/* 359 */       this.connections_last = new double[this.connections.length];
/*     */     }
/* 361 */     if (this.connections_src == null || this.connections_src.length < this.connections.length) {
/*     */       
/* 363 */       this.connections_src = new double[this.connections.length][][];
/* 364 */       this.connections_src_kc = new int[this.connections.length][];
/*     */     } 
/* 366 */     if (this.connections_dst == null || this.connections_dst.length < this.connections.length)
/*     */     {
/* 368 */       this.connections_dst = new double[this.connections.length][]; } 
/*     */     byte b;
/* 370 */     for (b = 0; b < this.connections.length; b++) {
/* 371 */       ModelConnectionBlock modelConnectionBlock = this.connections[b];
/* 372 */       this.connections_last[b] = 0.0D;
/* 373 */       if (modelConnectionBlock.getSources() != null) {
/* 374 */         ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/* 375 */         if (this.connections_src[b] == null || (this.connections_src[b]).length < arrayOfModelSource.length) {
/*     */           
/* 377 */           this.connections_src[b] = new double[arrayOfModelSource.length][];
/* 378 */           this.connections_src_kc[b] = new int[arrayOfModelSource.length];
/*     */         } 
/* 380 */         double[][] arrayOfDouble = this.connections_src[b];
/* 381 */         int[] arrayOfInt = this.connections_src_kc[b];
/* 382 */         this.connections_src[b] = arrayOfDouble;
/* 383 */         for (byte b1 = 0; b1 < arrayOfModelSource.length; b1++) {
/* 384 */           arrayOfInt[b1] = getValueKC(arrayOfModelSource[b1].getIdentifier());
/* 385 */           arrayOfDouble[b1] = getValue(arrayOfModelSource[b1].getIdentifier());
/*     */         } 
/*     */       } 
/*     */       
/* 389 */       if (modelConnectionBlock.getDestination() != null) {
/* 390 */         this.connections_dst[b] = getValue(modelConnectionBlock.getDestination()
/* 391 */             .getIdentifier());
/*     */       } else {
/* 393 */         this.connections_dst[b] = null;
/*     */       } 
/*     */     } 
/* 396 */     for (b = 0; b < this.connections.length; b++) {
/* 397 */       processConnection(b);
/*     */     }
/* 399 */     if (this.extendedConnectionBlocks != null) {
/* 400 */       for (ModelConnectionBlock modelConnectionBlock : this.extendedConnectionBlocks) {
/* 401 */         double d = 0.0D;
/*     */         
/* 403 */         if (this.softchannel.keybasedcontroller_active == null)
/* 404 */         { for (ModelSource modelSource : modelConnectionBlock.getSources()) {
/* 405 */             double d1 = getValue(modelSource.getIdentifier())[0];
/* 406 */             ModelTransform modelTransform1 = modelSource.getTransform();
/* 407 */             if (modelTransform1 == null) {
/* 408 */               d += d1;
/*     */             } else {
/* 410 */               d += modelTransform1.transform(d1);
/*     */             } 
/*     */           }  }
/* 413 */         else { for (ModelSource modelSource : modelConnectionBlock.getSources()) {
/* 414 */             double d1 = getValue(modelSource.getIdentifier())[0];
/* 415 */             d1 = processKeyBasedController(d1, 
/* 416 */                 getValueKC(modelSource.getIdentifier()));
/* 417 */             ModelTransform modelTransform1 = modelSource.getTransform();
/* 418 */             if (modelTransform1 == null) {
/* 419 */               d += d1;
/*     */             } else {
/* 421 */               d += modelTransform1.transform(d1);
/*     */             } 
/*     */           }  }
/*     */         
/* 425 */         ModelDestination modelDestination = modelConnectionBlock.getDestination();
/* 426 */         ModelTransform modelTransform = modelDestination.getTransform();
/* 427 */         if (modelTransform != null)
/* 428 */           d = modelTransform.transform(d); 
/* 429 */         getValue(modelDestination.getIdentifier())[0] = getValue(modelDestination.getIdentifier())[0] + d;
/*     */       } 
/*     */     }
/*     */     
/* 433 */     this.eg.init(this.synthesizer);
/* 434 */     this.lfo.init(this.synthesizer);
/*     */   }
/*     */ 
/*     */   
/*     */   void setPolyPressure(int paramInt) {
/* 439 */     if (this.performer == null)
/*     */       return; 
/* 441 */     int[] arrayOfInt = this.performer.midi_connections[2];
/* 442 */     if (arrayOfInt == null)
/*     */       return; 
/* 444 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 445 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void setChannelPressure(int paramInt) {
/* 449 */     if (this.performer == null)
/*     */       return; 
/* 451 */     int[] arrayOfInt = this.performer.midi_connections[1];
/* 452 */     if (arrayOfInt == null)
/*     */       return; 
/* 454 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 455 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void controlChange(int paramInt1, int paramInt2) {
/* 459 */     if (this.performer == null)
/*     */       return; 
/* 461 */     int[] arrayOfInt = this.performer.midi_ctrl_connections[paramInt1];
/* 462 */     if (arrayOfInt == null)
/*     */       return; 
/* 464 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 465 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void nrpnChange(int paramInt1, int paramInt2) {
/* 469 */     if (this.performer == null)
/*     */       return; 
/* 471 */     int[] arrayOfInt = this.performer.midi_nrpn_connections.get(Integer.valueOf(paramInt1));
/* 472 */     if (arrayOfInt == null)
/*     */       return; 
/* 474 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 475 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void rpnChange(int paramInt1, int paramInt2) {
/* 479 */     if (this.performer == null)
/*     */       return; 
/* 481 */     int[] arrayOfInt = this.performer.midi_rpn_connections.get(Integer.valueOf(paramInt1));
/* 482 */     if (arrayOfInt == null)
/*     */       return; 
/* 484 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 485 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void setPitchBend(int paramInt) {
/* 489 */     if (this.performer == null)
/*     */       return; 
/* 491 */     int[] arrayOfInt = this.performer.midi_connections[0];
/* 492 */     if (arrayOfInt == null)
/*     */       return; 
/* 494 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 495 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void setMute(boolean paramBoolean) {
/* 499 */     this.co_mixer_gain[0] = this.co_mixer_gain[0] - this.lastMuteValue;
/* 500 */     this.lastMuteValue = paramBoolean ? -960.0F : 0.0F;
/* 501 */     this.co_mixer_gain[0] = this.co_mixer_gain[0] + this.lastMuteValue;
/*     */   }
/*     */   
/*     */   void setSoloMute(boolean paramBoolean) {
/* 505 */     this.co_mixer_gain[0] = this.co_mixer_gain[0] - this.lastSoloMuteValue;
/* 506 */     this.lastSoloMuteValue = paramBoolean ? -960.0F : 0.0F;
/* 507 */     this.co_mixer_gain[0] = this.co_mixer_gain[0] + this.lastSoloMuteValue;
/*     */   }
/*     */   
/*     */   void shutdown() {
/* 511 */     if (this.co_noteon_on[0] < -0.5D)
/*     */       return; 
/* 513 */     this.on = false;
/*     */     
/* 515 */     this.co_noteon_on[0] = -1.0D;
/*     */     
/* 517 */     if (this.performer == null)
/*     */       return; 
/* 519 */     int[] arrayOfInt = this.performer.midi_connections[3];
/* 520 */     if (arrayOfInt == null)
/*     */       return; 
/* 522 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 523 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void soundOff() {
/* 527 */     this.on = false;
/* 528 */     this.soundoff = true;
/*     */   }
/*     */   
/*     */   void noteOff(int paramInt) {
/* 532 */     if (!this.on)
/*     */       return; 
/* 534 */     this.on = false;
/*     */     
/* 536 */     this.noteOff_velocity = paramInt;
/*     */     
/* 538 */     if (this.softchannel.sustain) {
/* 539 */       this.sustain = true;
/*     */       return;
/*     */     } 
/* 542 */     if (this.sostenuto) {
/*     */       return;
/*     */     }
/* 545 */     this.co_noteon_on[0] = 0.0D;
/*     */     
/* 547 */     if (this.performer == null)
/*     */       return; 
/* 549 */     int[] arrayOfInt = this.performer.midi_connections[3];
/* 550 */     if (arrayOfInt == null)
/*     */       return; 
/* 552 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 553 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void redamp() {
/* 557 */     if (this.co_noteon_on[0] > 0.5D)
/*     */       return; 
/* 559 */     if (this.co_noteon_on[0] < -0.5D) {
/*     */       return;
/*     */     }
/* 562 */     this.sustain = true;
/* 563 */     this.co_noteon_on[0] = 1.0D;
/*     */     
/* 565 */     if (this.performer == null)
/*     */       return; 
/* 567 */     int[] arrayOfInt = this.performer.midi_connections[3];
/* 568 */     if (arrayOfInt == null)
/*     */       return; 
/* 570 */     for (byte b = 0; b < arrayOfInt.length; b++)
/* 571 */       processConnection(arrayOfInt[b]); 
/*     */   }
/*     */   
/*     */   void processControlLogic() {
/* 575 */     if (this.stopping) {
/* 576 */       this.active = false;
/* 577 */       this.stopping = false;
/* 578 */       this.audiostarted = false;
/* 579 */       this.instrument = null;
/* 580 */       this.performer = null;
/* 581 */       this.connections = null;
/* 582 */       this.extendedConnectionBlocks = null;
/* 583 */       this.channelmixer = null;
/* 584 */       if (this.osc_stream != null) {
/*     */         try {
/* 586 */           this.osc_stream.close();
/* 587 */         } catch (IOException iOException) {}
/*     */       }
/*     */ 
/*     */       
/* 591 */       if (this.stealer_channel != null) {
/* 592 */         this.stealer_channel.initVoice(this, this.stealer_performer, this.stealer_voiceID, this.stealer_noteNumber, this.stealer_velocity, 0, this.stealer_extendedConnectionBlocks, this.stealer_channelmixer, this.stealer_releaseTriggered);
/*     */ 
/*     */ 
/*     */         
/* 596 */         this.stealer_releaseTriggered = false;
/* 597 */         this.stealer_channel = null;
/* 598 */         this.stealer_performer = null;
/* 599 */         this.stealer_voiceID = -1;
/* 600 */         this.stealer_noteNumber = 0;
/* 601 */         this.stealer_velocity = 0;
/* 602 */         this.stealer_extendedConnectionBlocks = null;
/* 603 */         this.stealer_channelmixer = null;
/*     */       } 
/*     */     } 
/* 606 */     if (this.started) {
/* 607 */       this.audiostarted = true;
/*     */       
/* 609 */       ModelOscillator modelOscillator = this.performer.oscillators[0];
/*     */       
/* 611 */       this.osc_stream_off_transmitted = false;
/* 612 */       if (modelOscillator instanceof ModelWavetable) {
/*     */         try {
/* 614 */           this.resampler.open((ModelWavetable)modelOscillator, this.synthesizer
/* 615 */               .getFormat().getSampleRate());
/* 616 */           this.osc_stream = this.resampler;
/* 617 */         } catch (IOException iOException) {}
/*     */       }
/*     */       else {
/*     */         
/* 621 */         this.osc_stream = modelOscillator.open(this.synthesizer.getFormat().getSampleRate());
/*     */       } 
/* 623 */       this.osc_attenuation = modelOscillator.getAttenuation();
/* 624 */       this.osc_stream_nrofchannels = modelOscillator.getChannels();
/* 625 */       if (this.osc_buff == null || this.osc_buff.length < this.osc_stream_nrofchannels) {
/* 626 */         this.osc_buff = new float[this.osc_stream_nrofchannels][];
/*     */       }
/* 628 */       if (this.osc_stream != null) {
/* 629 */         this.osc_stream.noteOn(this.softchannel, this, this.noteOn_noteNumber, this.noteOn_velocity);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 634 */     if (this.audiostarted) {
/* 635 */       double d1; if (this.portamento) {
/* 636 */         double d5 = this.tunedKey - this.co_noteon_keynumber[0] * 128.0D;
/* 637 */         double d6 = Math.abs(d5);
/* 638 */         if (d6 < 1.0E-10D) {
/* 639 */           this.co_noteon_keynumber[0] = this.tunedKey * 0.0078125D;
/* 640 */           this.portamento = false;
/*     */         } else {
/* 642 */           if (d6 > this.softchannel.portamento_time) {
/* 643 */             d5 = Math.signum(d5) * this.softchannel.portamento_time;
/*     */           }
/* 645 */           this.co_noteon_keynumber[0] = this.co_noteon_keynumber[0] + d5 * 0.0078125D;
/*     */         } 
/*     */         
/* 648 */         int[] arrayOfInt = this.performer.midi_connections[4];
/* 649 */         if (arrayOfInt == null)
/*     */           return; 
/* 651 */         for (byte b = 0; b < arrayOfInt.length; b++) {
/* 652 */           processConnection(arrayOfInt[b]);
/*     */         }
/*     */       } 
/* 655 */       this.eg.processControlLogic();
/* 656 */       this.lfo.processControlLogic();
/*     */       int i;
/* 658 */       for (i = 0; i < this.performer.ctrl_connections.length; i++) {
/* 659 */         processConnection(this.performer.ctrl_connections[i]);
/*     */       }
/* 661 */       this.osc_stream.setPitch((float)this.co_osc_pitch[0]);
/*     */       
/* 663 */       i = (int)this.co_filter_type[0];
/*     */ 
/*     */       
/* 666 */       if (this.co_filter_freq[0] == 13500.0D) {
/* 667 */         d1 = 19912.126958213175D;
/*     */       } else {
/* 669 */         d1 = 440.0D * Math.exp((this.co_filter_freq[0] - 6900.0D) * 
/*     */             
/* 671 */             Math.log(2.0D) / 1200.0D);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 680 */       double d2 = this.co_filter_q[0] / 10.0D;
/* 681 */       this.filter_left.setFilterType(i);
/* 682 */       this.filter_left.setFrequency(d1);
/* 683 */       this.filter_left.setResonance(d2);
/* 684 */       this.filter_right.setFilterType(i);
/* 685 */       this.filter_right.setFrequency(d1);
/* 686 */       this.filter_right.setResonance(d2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 691 */       float f = (float)Math.exp((-this.osc_attenuation + this.co_mixer_gain[0]) * 
/* 692 */           Math.log(10.0D) / 200.0D);
/*     */       
/* 694 */       if (this.co_mixer_gain[0] <= -960.0D) {
/* 695 */         f = 0.0F;
/*     */       }
/* 697 */       if (this.soundoff) {
/* 698 */         this.stopping = true;
/* 699 */         f = 0.0F;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 706 */       this.volume = (int)(Math.sqrt(f) * 128.0D);
/*     */ 
/*     */ 
/*     */       
/* 710 */       double d3 = this.co_mixer_pan[0] * 0.001D;
/*     */       
/* 712 */       if (d3 < 0.0D) {
/* 713 */         d3 = 0.0D;
/* 714 */       } else if (d3 > 1.0D) {
/* 715 */         d3 = 1.0D;
/*     */       } 
/* 717 */       if (d3 == 0.5D) {
/* 718 */         this.out_mixer_left = f * 0.70710677F;
/* 719 */         this.out_mixer_right = this.out_mixer_left;
/*     */       } else {
/* 721 */         this.out_mixer_left = f * (float)Math.cos(d3 * Math.PI * 0.5D);
/* 722 */         this.out_mixer_right = f * (float)Math.sin(d3 * Math.PI * 0.5D);
/*     */       } 
/*     */       
/* 725 */       double d4 = this.co_mixer_balance[0] * 0.001D;
/* 726 */       if (d4 != 0.5D) {
/* 727 */         if (d4 > 0.5D) {
/* 728 */           this.out_mixer_left = (float)(this.out_mixer_left * (1.0D - d4) * 2.0D);
/*     */         } else {
/* 730 */           this.out_mixer_right = (float)(this.out_mixer_right * d4 * 2.0D);
/*     */         } 
/*     */       }
/* 733 */       if (this.synthesizer.reverb_on) {
/* 734 */         this.out_mixer_effect1 = (float)(this.co_mixer_reverb[0] * 0.001D);
/* 735 */         this.out_mixer_effect1 *= f;
/*     */       } else {
/* 737 */         this.out_mixer_effect1 = 0.0F;
/* 738 */       }  if (this.synthesizer.chorus_on) {
/* 739 */         this.out_mixer_effect2 = (float)(this.co_mixer_chorus[0] * 0.001D);
/* 740 */         this.out_mixer_effect2 *= f;
/*     */       } else {
/* 742 */         this.out_mixer_effect2 = 0.0F;
/* 743 */       }  this.out_mixer_end = (this.co_mixer_active[0] < 0.5D);
/*     */       
/* 745 */       if (!this.on && 
/* 746 */         !this.osc_stream_off_transmitted) {
/* 747 */         this.osc_stream_off_transmitted = true;
/* 748 */         if (this.osc_stream != null) {
/* 749 */           this.osc_stream.noteOff(this.noteOff_velocity);
/*     */         }
/*     */       } 
/*     */     } 
/* 753 */     if (this.started) {
/* 754 */       this.last_out_mixer_left = this.out_mixer_left;
/* 755 */       this.last_out_mixer_right = this.out_mixer_right;
/* 756 */       this.last_out_mixer_effect1 = this.out_mixer_effect1;
/* 757 */       this.last_out_mixer_effect2 = this.out_mixer_effect2;
/* 758 */       this.started = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void mixAudioStream(SoftAudioBuffer paramSoftAudioBuffer1, SoftAudioBuffer paramSoftAudioBuffer2, SoftAudioBuffer paramSoftAudioBuffer3, float paramFloat1, float paramFloat2) {
/* 766 */     int i = paramSoftAudioBuffer1.getSize();
/* 767 */     if (paramFloat1 < 1.0E-9D && paramFloat2 < 1.0E-9D)
/*     */       return; 
/* 769 */     if (paramSoftAudioBuffer3 != null && this.delay != 0) {
/*     */       
/* 771 */       if (paramFloat1 == paramFloat2) {
/* 772 */         float[] arrayOfFloat1 = paramSoftAudioBuffer2.array();
/* 773 */         float[] arrayOfFloat2 = paramSoftAudioBuffer1.array();
/* 774 */         byte b = 0; int j;
/* 775 */         for (j = this.delay; j < i; j++)
/* 776 */           arrayOfFloat1[j] = arrayOfFloat1[j] + arrayOfFloat2[b++] * paramFloat2; 
/* 777 */         arrayOfFloat1 = paramSoftAudioBuffer3.array();
/* 778 */         for (j = 0; j < this.delay; j++)
/* 779 */           arrayOfFloat1[j] = arrayOfFloat1[j] + arrayOfFloat2[b++] * paramFloat2; 
/*     */       } else {
/* 781 */         float f1 = paramFloat1;
/* 782 */         float f2 = (paramFloat2 - paramFloat1) / i;
/* 783 */         float[] arrayOfFloat1 = paramSoftAudioBuffer2.array();
/* 784 */         float[] arrayOfFloat2 = paramSoftAudioBuffer1.array();
/* 785 */         byte b = 0; int j;
/* 786 */         for (j = this.delay; j < i; j++) {
/* 787 */           f1 += f2;
/* 788 */           arrayOfFloat1[j] = arrayOfFloat1[j] + arrayOfFloat2[b++] * f1;
/*     */         } 
/* 790 */         arrayOfFloat1 = paramSoftAudioBuffer3.array();
/* 791 */         for (j = 0; j < this.delay; j++) {
/* 792 */           f1 += f2;
/* 793 */           arrayOfFloat1[j] = arrayOfFloat1[j] + arrayOfFloat2[b++] * f1;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 799 */     else if (paramFloat1 == paramFloat2) {
/* 800 */       float[] arrayOfFloat1 = paramSoftAudioBuffer2.array();
/* 801 */       float[] arrayOfFloat2 = paramSoftAudioBuffer1.array();
/* 802 */       for (byte b = 0; b < i; b++)
/* 803 */         arrayOfFloat1[b] = arrayOfFloat1[b] + arrayOfFloat2[b] * paramFloat2; 
/*     */     } else {
/* 805 */       float f1 = paramFloat1;
/* 806 */       float f2 = (paramFloat2 - paramFloat1) / i;
/* 807 */       float[] arrayOfFloat1 = paramSoftAudioBuffer2.array();
/* 808 */       float[] arrayOfFloat2 = paramSoftAudioBuffer1.array();
/* 809 */       for (byte b = 0; b < i; b++) {
/* 810 */         f1 += f2;
/* 811 */         arrayOfFloat1[b] = arrayOfFloat1[b] + arrayOfFloat2[b] * f1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void processAudioLogic(SoftAudioBuffer[] paramArrayOfSoftAudioBuffer) {
/* 819 */     if (!this.audiostarted) {
/*     */       return;
/*     */     }
/* 822 */     int i = paramArrayOfSoftAudioBuffer[0].getSize();
/*     */     
/*     */     try {
/* 825 */       this.osc_buff[0] = paramArrayOfSoftAudioBuffer[10].array();
/* 826 */       if (this.nrofchannels != 1)
/* 827 */         this.osc_buff[1] = paramArrayOfSoftAudioBuffer[11].array(); 
/* 828 */       int j = this.osc_stream.read(this.osc_buff, 0, i);
/* 829 */       if (j == -1) {
/* 830 */         this.stopping = true;
/*     */         return;
/*     */       } 
/* 833 */       if (j != i) {
/* 834 */         Arrays.fill(this.osc_buff[0], j, i, 0.0F);
/* 835 */         if (this.nrofchannels != 1) {
/* 836 */           Arrays.fill(this.osc_buff[1], j, i, 0.0F);
/*     */         }
/*     */       } 
/* 839 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */     
/* 843 */     SoftAudioBuffer softAudioBuffer1 = paramArrayOfSoftAudioBuffer[0];
/* 844 */     SoftAudioBuffer softAudioBuffer2 = paramArrayOfSoftAudioBuffer[1];
/* 845 */     SoftAudioBuffer softAudioBuffer3 = paramArrayOfSoftAudioBuffer[2];
/* 846 */     SoftAudioBuffer softAudioBuffer4 = paramArrayOfSoftAudioBuffer[6];
/* 847 */     SoftAudioBuffer softAudioBuffer5 = paramArrayOfSoftAudioBuffer[7];
/*     */     
/* 849 */     SoftAudioBuffer softAudioBuffer6 = paramArrayOfSoftAudioBuffer[3];
/* 850 */     SoftAudioBuffer softAudioBuffer7 = paramArrayOfSoftAudioBuffer[4];
/* 851 */     SoftAudioBuffer softAudioBuffer8 = paramArrayOfSoftAudioBuffer[5];
/* 852 */     SoftAudioBuffer softAudioBuffer9 = paramArrayOfSoftAudioBuffer[8];
/* 853 */     SoftAudioBuffer softAudioBuffer10 = paramArrayOfSoftAudioBuffer[9];
/*     */     
/* 855 */     SoftAudioBuffer softAudioBuffer11 = paramArrayOfSoftAudioBuffer[10];
/* 856 */     SoftAudioBuffer softAudioBuffer12 = paramArrayOfSoftAudioBuffer[11];
/*     */     
/* 858 */     if (this.osc_stream_nrofchannels == 1) {
/* 859 */       softAudioBuffer12 = null;
/*     */     }
/* 861 */     if (!Double.isInfinite(this.co_filter_freq[0])) {
/* 862 */       this.filter_left.processAudio(softAudioBuffer11);
/* 863 */       if (softAudioBuffer12 != null) {
/* 864 */         this.filter_right.processAudio(softAudioBuffer12);
/*     */       }
/*     */     } 
/* 867 */     if (this.nrofchannels == 1) {
/* 868 */       this.out_mixer_left = (this.out_mixer_left + this.out_mixer_right) / 2.0F;
/* 869 */       mixAudioStream(softAudioBuffer11, softAudioBuffer1, softAudioBuffer6, this.last_out_mixer_left, this.out_mixer_left);
/* 870 */       if (softAudioBuffer12 != null) {
/* 871 */         mixAudioStream(softAudioBuffer12, softAudioBuffer1, softAudioBuffer6, this.last_out_mixer_left, this.out_mixer_left);
/*     */       }
/*     */     }
/* 874 */     else if (softAudioBuffer12 == null && this.last_out_mixer_left == this.last_out_mixer_right && this.out_mixer_left == this.out_mixer_right) {
/*     */ 
/*     */ 
/*     */       
/* 878 */       mixAudioStream(softAudioBuffer11, softAudioBuffer3, softAudioBuffer8, this.last_out_mixer_left, this.out_mixer_left);
/*     */     }
/*     */     else {
/*     */       
/* 882 */       mixAudioStream(softAudioBuffer11, softAudioBuffer1, softAudioBuffer6, this.last_out_mixer_left, this.out_mixer_left);
/* 883 */       if (softAudioBuffer12 != null) {
/* 884 */         mixAudioStream(softAudioBuffer12, softAudioBuffer2, softAudioBuffer7, this.last_out_mixer_right, this.out_mixer_right);
/*     */       } else {
/*     */         
/* 887 */         mixAudioStream(softAudioBuffer11, softAudioBuffer2, softAudioBuffer7, this.last_out_mixer_right, this.out_mixer_right);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 892 */     if (softAudioBuffer12 == null) {
/* 893 */       mixAudioStream(softAudioBuffer11, softAudioBuffer4, softAudioBuffer9, this.last_out_mixer_effect1, this.out_mixer_effect1);
/*     */       
/* 895 */       mixAudioStream(softAudioBuffer11, softAudioBuffer5, softAudioBuffer10, this.last_out_mixer_effect2, this.out_mixer_effect2);
/*     */     } else {
/*     */       
/* 898 */       mixAudioStream(softAudioBuffer11, softAudioBuffer4, softAudioBuffer9, this.last_out_mixer_effect1 * 0.5F, this.out_mixer_effect1 * 0.5F);
/*     */       
/* 900 */       mixAudioStream(softAudioBuffer11, softAudioBuffer5, softAudioBuffer10, this.last_out_mixer_effect2 * 0.5F, this.out_mixer_effect2 * 0.5F);
/*     */       
/* 902 */       mixAudioStream(softAudioBuffer12, softAudioBuffer4, softAudioBuffer9, this.last_out_mixer_effect1 * 0.5F, this.out_mixer_effect1 * 0.5F);
/*     */       
/* 904 */       mixAudioStream(softAudioBuffer12, softAudioBuffer5, softAudioBuffer10, this.last_out_mixer_effect2 * 0.5F, this.out_mixer_effect2 * 0.5F);
/*     */     } 
/*     */ 
/*     */     
/* 908 */     this.last_out_mixer_left = this.out_mixer_left;
/* 909 */     this.last_out_mixer_right = this.out_mixer_right;
/* 910 */     this.last_out_mixer_effect1 = this.out_mixer_effect1;
/* 911 */     this.last_out_mixer_effect2 = this.out_mixer_effect2;
/*     */     
/* 913 */     if (this.out_mixer_end)
/* 914 */       this.stopping = true; 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftVoice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */