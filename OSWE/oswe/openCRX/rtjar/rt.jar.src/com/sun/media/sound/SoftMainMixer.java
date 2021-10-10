/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import javax.sound.midi.MidiMessage;
/*      */ import javax.sound.midi.Patch;
/*      */ import javax.sound.midi.ShortMessage;
/*      */ import javax.sound.sampled.AudioInputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SoftMainMixer
/*      */ {
/*      */   public static final int CHANNEL_LEFT = 0;
/*      */   public static final int CHANNEL_RIGHT = 1;
/*      */   public static final int CHANNEL_MONO = 2;
/*      */   public static final int CHANNEL_DELAY_LEFT = 3;
/*      */   public static final int CHANNEL_DELAY_RIGHT = 4;
/*      */   public static final int CHANNEL_DELAY_MONO = 5;
/*      */   public static final int CHANNEL_EFFECT1 = 6;
/*      */   public static final int CHANNEL_EFFECT2 = 7;
/*      */   public static final int CHANNEL_DELAY_EFFECT1 = 8;
/*      */   public static final int CHANNEL_DELAY_EFFECT2 = 9;
/*      */   public static final int CHANNEL_LEFT_DRY = 10;
/*      */   public static final int CHANNEL_RIGHT_DRY = 11;
/*      */   public static final int CHANNEL_SCRATCH1 = 12;
/*      */   public static final int CHANNEL_SCRATCH2 = 13;
/*      */   
/*      */   private class SoftChannelMixerContainer
/*      */   {
/*      */     ModelChannelMixer mixer;
/*      */     SoftAudioBuffer[] buffers;
/*      */     
/*      */     private SoftChannelMixerContainer() {}
/*      */   }
/*      */   boolean active_sensing_on = false;
/*   71 */   private long msec_last_activity = -1L;
/*      */   private boolean pusher_silent = false;
/*   73 */   private int pusher_silent_count = 0;
/*   74 */   private long sample_pos = 0L;
/*      */   boolean readfully = true;
/*      */   private final Object control_mutex;
/*      */   private SoftSynthesizer synth;
/*   78 */   private float samplerate = 44100.0F;
/*   79 */   private int nrofchannels = 2;
/*   80 */   private SoftVoice[] voicestatus = null;
/*      */   private SoftAudioBuffer[] buffers;
/*      */   private SoftReverb reverb;
/*      */   private SoftAudioProcessor chorus;
/*      */   private SoftAudioProcessor agc;
/*   85 */   private long msec_buffer_len = 0L;
/*   86 */   private int buffer_len = 0;
/*   87 */   TreeMap<Long, Object> midimessages = new TreeMap<>();
/*   88 */   private int delay_midievent = 0;
/*   89 */   private int max_delay_midievent = 0;
/*   90 */   double last_volume_left = 1.0D;
/*   91 */   double last_volume_right = 1.0D;
/*   92 */   private double[] co_master_balance = new double[1];
/*   93 */   private double[] co_master_volume = new double[1];
/*   94 */   private double[] co_master_coarse_tuning = new double[1];
/*   95 */   private double[] co_master_fine_tuning = new double[1];
/*      */   private AudioInputStream ais;
/*   97 */   private Set<SoftChannelMixerContainer> registeredMixers = null;
/*   98 */   private Set<ModelChannelMixer> stoppedMixers = null;
/*   99 */   private SoftChannelMixerContainer[] cur_registeredMixers = null;
/*  100 */   SoftControl co_master = new SoftControl()
/*      */     {
/*  102 */       double[] balance = SoftMainMixer.this.co_master_balance;
/*  103 */       double[] volume = SoftMainMixer.this.co_master_volume;
/*  104 */       double[] coarse_tuning = SoftMainMixer.this.co_master_coarse_tuning;
/*  105 */       double[] fine_tuning = SoftMainMixer.this.co_master_fine_tuning;
/*      */       
/*      */       public double[] get(int param1Int, String param1String) {
/*  108 */         if (param1String == null)
/*  109 */           return null; 
/*  110 */         if (param1String.equals("balance"))
/*  111 */           return this.balance; 
/*  112 */         if (param1String.equals("volume"))
/*  113 */           return this.volume; 
/*  114 */         if (param1String.equals("coarse_tuning"))
/*  115 */           return this.coarse_tuning; 
/*  116 */         if (param1String.equals("fine_tuning"))
/*  117 */           return this.fine_tuning; 
/*  118 */         return null;
/*      */       }
/*      */     };
/*      */   
/*      */   private void processSystemExclusiveMessage(byte[] paramArrayOfbyte) {
/*  123 */     synchronized (this.synth.control_mutex) {
/*  124 */       activity();
/*      */ 
/*      */       
/*  127 */       if ((paramArrayOfbyte[1] & 0xFF) == 126) {
/*  128 */         int i = paramArrayOfbyte[2] & 0xFF;
/*  129 */         if (i == 127 || i == this.synth.getDeviceID()) {
/*  130 */           int k; SoftTuning softTuning; int m; SoftChannel[] arrayOfSoftChannel; byte b; int j = paramArrayOfbyte[3] & 0xFF;
/*      */           
/*  132 */           switch (j) {
/*      */             case 8:
/*  134 */               k = paramArrayOfbyte[4] & 0xFF;
/*  135 */               switch (k) {
/*      */ 
/*      */                 
/*      */                 case 1:
/*  139 */                   softTuning = this.synth.getTuning(new Patch(0, paramArrayOfbyte[5] & 0xFF));
/*      */                   
/*  141 */                   softTuning.load(paramArrayOfbyte);
/*      */                   break;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 4:
/*      */                 case 5:
/*      */                 case 6:
/*      */                 case 7:
/*  151 */                   softTuning = this.synth.getTuning(new Patch(paramArrayOfbyte[5] & 0xFF, paramArrayOfbyte[6] & 0xFF));
/*      */                   
/*  153 */                   softTuning.load(paramArrayOfbyte);
/*      */                   break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 8:
/*      */                 case 9:
/*  162 */                   softTuning = new SoftTuning(paramArrayOfbyte);
/*  163 */                   m = (paramArrayOfbyte[5] & 0xFF) * 16384 + (paramArrayOfbyte[6] & 0xFF) * 128 + (paramArrayOfbyte[7] & 0xFF);
/*      */                   
/*  165 */                   arrayOfSoftChannel = this.synth.channels;
/*  166 */                   for (b = 0; b < arrayOfSoftChannel.length; b++) {
/*  167 */                     if ((m & 1 << b) != 0) {
/*  168 */                       (arrayOfSoftChannel[b]).tuning = softTuning;
/*      */                     }
/*      */                   } 
/*      */                   break;
/*      */               } 
/*      */               
/*      */               break;
/*      */             case 9:
/*  176 */               k = paramArrayOfbyte[4] & 0xFF;
/*  177 */               switch (k) {
/*      */                 case 1:
/*  179 */                   this.synth.setGeneralMidiMode(1);
/*  180 */                   reset();
/*      */                   break;
/*      */                 case 2:
/*  183 */                   this.synth.setGeneralMidiMode(0);
/*  184 */                   reset();
/*      */                   break;
/*      */                 case 3:
/*  187 */                   this.synth.setGeneralMidiMode(2);
/*  188 */                   reset();
/*      */                   break;
/*      */               } 
/*      */               
/*      */               break;
/*      */             
/*      */             case 10:
/*  195 */               k = paramArrayOfbyte[4] & 0xFF;
/*  196 */               switch (k) {
/*      */                 case 1:
/*  198 */                   if (this.synth.getGeneralMidiMode() == 0)
/*  199 */                     this.synth.setGeneralMidiMode(1); 
/*  200 */                   this.synth.voice_allocation_mode = 1;
/*  201 */                   reset();
/*      */                   break;
/*      */                 case 2:
/*  204 */                   this.synth.setGeneralMidiMode(0);
/*  205 */                   this.synth.voice_allocation_mode = 0;
/*  206 */                   reset();
/*      */                   break;
/*      */                 case 3:
/*  209 */                   this.synth.voice_allocation_mode = 0;
/*      */                   break;
/*      */                 case 4:
/*  212 */                   this.synth.voice_allocation_mode = 1;
/*      */                   break;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         } 
/*      */       } 
/*  226 */       if ((paramArrayOfbyte[1] & 0xFF) == 127) {
/*  227 */         int i = paramArrayOfbyte[2] & 0xFF;
/*  228 */         if (i == 127 || i == this.synth.getDeviceID()) {
/*  229 */           int k, n; SoftTuning softTuning; int arrayOfInt1[], m; byte b1; SoftVoice[] arrayOfSoftVoice1; int i2, arrayOfInt2[], i1, i3; SoftChannel[] arrayOfSoftChannel; byte b2; SoftChannel softChannel1; int i5; SoftVoice[] arrayOfSoftVoice2; int i4, i6; SoftChannel softChannel2; int arrayOfInt3[], i7, i8; long[] arrayOfLong1, arrayOfLong2; byte b3; int j = paramArrayOfbyte[3] & 0xFF;
/*      */           
/*  231 */           switch (j) {
/*      */             
/*      */             case 4:
/*  234 */               k = paramArrayOfbyte[4] & 0xFF;
/*  235 */               switch (k) {
/*      */                 case 1:
/*      */                 case 2:
/*      */                 case 3:
/*      */                 case 4:
/*  240 */                   n = (paramArrayOfbyte[5] & Byte.MAX_VALUE) + (paramArrayOfbyte[6] & Byte.MAX_VALUE) * 128;
/*      */                   
/*  242 */                   if (k == 1) {
/*  243 */                     setVolume(n); break;
/*  244 */                   }  if (k == 2) {
/*  245 */                     setBalance(n); break;
/*  246 */                   }  if (k == 3) {
/*  247 */                     setFineTuning(n); break;
/*  248 */                   }  if (k == 4)
/*  249 */                     setCoarseTuning(n); 
/*      */                   break;
/*      */                 case 5:
/*  252 */                   b1 = 5;
/*  253 */                   i3 = paramArrayOfbyte[b1++] & 0xFF;
/*  254 */                   i5 = paramArrayOfbyte[b1++] & 0xFF;
/*  255 */                   i6 = paramArrayOfbyte[b1++] & 0xFF;
/*  256 */                   arrayOfInt3 = new int[i3];
/*  257 */                   for (i8 = 0; i8 < i3; i8++) {
/*  258 */                     int i9 = paramArrayOfbyte[b1++] & 0xFF;
/*  259 */                     int i10 = paramArrayOfbyte[b1++] & 0xFF;
/*  260 */                     arrayOfInt3[i8] = i9 * 128 + i10;
/*      */                   } 
/*  262 */                   i8 = (paramArrayOfbyte.length - 1 - b1) / (i5 + i6);
/*      */                   
/*  264 */                   arrayOfLong1 = new long[i8];
/*  265 */                   arrayOfLong2 = new long[i8];
/*  266 */                   for (b3 = 0; b3 < i8; b3++) {
/*  267 */                     arrayOfLong2[b3] = 0L; byte b;
/*  268 */                     for (b = 0; b < i5; b++) {
/*  269 */                       arrayOfLong1[b3] = arrayOfLong1[b3] * 128L + (paramArrayOfbyte[b1++] & 0xFF);
/*      */                     }
/*  271 */                     for (b = 0; b < i6; b++) {
/*  272 */                       arrayOfLong2[b3] = arrayOfLong2[b3] * 128L + (paramArrayOfbyte[b1++] & 0xFF);
/*      */                     }
/*      */                   } 
/*      */                   
/*  276 */                   globalParameterControlChange(arrayOfInt3, arrayOfLong1, arrayOfLong2);
/*      */                   break;
/*      */               } 
/*      */ 
/*      */               
/*      */               break;
/*      */             
/*      */             case 8:
/*  284 */               k = paramArrayOfbyte[4] & 0xFF;
/*  285 */               switch (k) {
/*      */ 
/*      */                 
/*      */                 case 2:
/*  289 */                   softTuning = this.synth.getTuning(new Patch(0, paramArrayOfbyte[5] & 0xFF));
/*      */                   
/*  291 */                   softTuning.load(paramArrayOfbyte);
/*  292 */                   arrayOfSoftVoice1 = this.synth.getVoices();
/*  293 */                   for (i3 = 0; i3 < arrayOfSoftVoice1.length; i3++) {
/*  294 */                     if ((arrayOfSoftVoice1[i3]).active && 
/*  295 */                       (arrayOfSoftVoice1[i3]).tuning == softTuning) {
/*  296 */                       arrayOfSoftVoice1[i3].updateTuning(softTuning);
/*      */                     }
/*      */                   } 
/*      */                   break;
/*      */ 
/*      */                 
/*      */                 case 7:
/*  303 */                   softTuning = this.synth.getTuning(new Patch(paramArrayOfbyte[5] & 0xFF, paramArrayOfbyte[6] & 0xFF));
/*      */                   
/*  305 */                   softTuning.load(paramArrayOfbyte);
/*  306 */                   arrayOfSoftVoice1 = this.synth.getVoices();
/*  307 */                   for (i3 = 0; i3 < arrayOfSoftVoice1.length; i3++) {
/*  308 */                     if ((arrayOfSoftVoice1[i3]).active && 
/*  309 */                       (arrayOfSoftVoice1[i3]).tuning == softTuning) {
/*  310 */                       arrayOfSoftVoice1[i3].updateTuning(softTuning);
/*      */                     }
/*      */                   } 
/*      */                   break;
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 8:
/*      */                 case 9:
/*  319 */                   softTuning = new SoftTuning(paramArrayOfbyte);
/*  320 */                   i2 = (paramArrayOfbyte[5] & 0xFF) * 16384 + (paramArrayOfbyte[6] & 0xFF) * 128 + (paramArrayOfbyte[7] & 0xFF);
/*      */                   
/*  322 */                   arrayOfSoftChannel = this.synth.channels;
/*  323 */                   for (i5 = 0; i5 < arrayOfSoftChannel.length; i5++) {
/*  324 */                     if ((i2 & 1 << i5) != 0)
/*  325 */                       (arrayOfSoftChannel[i5]).tuning = softTuning; 
/*  326 */                   }  arrayOfSoftVoice2 = this.synth.getVoices();
/*  327 */                   for (i6 = 0; i6 < arrayOfSoftVoice2.length; i6++) {
/*  328 */                     if ((arrayOfSoftVoice2[i6]).active && (
/*  329 */                       i2 & 1 << (arrayOfSoftVoice2[i6]).channel) != 0) {
/*  330 */                       arrayOfSoftVoice2[i6].updateTuning(softTuning);
/*      */                     }
/*      */                   } 
/*      */                   break;
/*      */               } 
/*      */               
/*      */               break;
/*      */             case 9:
/*  338 */               k = paramArrayOfbyte[4] & 0xFF;
/*  339 */               switch (k) {
/*      */                 
/*      */                 case 1:
/*  342 */                   arrayOfInt1 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  343 */                   arrayOfInt2 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  344 */                   b2 = 0;
/*  345 */                   for (i4 = 6; i4 < paramArrayOfbyte.length - 1; i4 += 2) {
/*  346 */                     arrayOfInt1[b2] = paramArrayOfbyte[i4] & 0xFF;
/*  347 */                     arrayOfInt2[b2] = paramArrayOfbyte[i4 + 1] & 0xFF;
/*  348 */                     b2++;
/*      */                   } 
/*  350 */                   i4 = paramArrayOfbyte[5] & 0xFF;
/*  351 */                   softChannel2 = this.synth.channels[i4];
/*  352 */                   softChannel2.mapChannelPressureToDestination(arrayOfInt1, arrayOfInt2);
/*      */                   break;
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 2:
/*  358 */                   arrayOfInt1 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  359 */                   arrayOfInt2 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  360 */                   b2 = 0;
/*  361 */                   for (i4 = 6; i4 < paramArrayOfbyte.length - 1; i4 += 2) {
/*  362 */                     arrayOfInt1[b2] = paramArrayOfbyte[i4] & 0xFF;
/*  363 */                     arrayOfInt2[b2] = paramArrayOfbyte[i4 + 1] & 0xFF;
/*  364 */                     b2++;
/*      */                   } 
/*  366 */                   i4 = paramArrayOfbyte[5] & 0xFF;
/*  367 */                   softChannel2 = this.synth.channels[i4];
/*  368 */                   softChannel2.mapPolyPressureToDestination(arrayOfInt1, arrayOfInt2);
/*      */                   break;
/*      */ 
/*      */ 
/*      */                 
/*      */                 case 3:
/*  374 */                   arrayOfInt1 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  375 */                   arrayOfInt2 = new int[(paramArrayOfbyte.length - 7) / 2];
/*  376 */                   b2 = 0;
/*  377 */                   for (i4 = 7; i4 < paramArrayOfbyte.length - 1; i4 += 2) {
/*  378 */                     arrayOfInt1[b2] = paramArrayOfbyte[i4] & 0xFF;
/*  379 */                     arrayOfInt2[b2] = paramArrayOfbyte[i4 + 1] & 0xFF;
/*  380 */                     b2++;
/*      */                   } 
/*  382 */                   i4 = paramArrayOfbyte[5] & 0xFF;
/*  383 */                   softChannel2 = this.synth.channels[i4];
/*  384 */                   i7 = paramArrayOfbyte[6] & 0xFF;
/*  385 */                   softChannel2.mapControlToDestination(i7, arrayOfInt1, arrayOfInt2);
/*      */                   break;
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */ 
/*      */ 
/*      */             
/*      */             case 10:
/*  396 */               k = paramArrayOfbyte[4] & 0xFF;
/*  397 */               switch (k) {
/*      */                 case 1:
/*  399 */                   m = paramArrayOfbyte[5] & 0xFF;
/*  400 */                   i1 = paramArrayOfbyte[6] & 0xFF;
/*  401 */                   softChannel1 = this.synth.channels[m];
/*  402 */                   for (i4 = 7; i4 < paramArrayOfbyte.length - 1; i4 += 2) {
/*  403 */                     int i9 = paramArrayOfbyte[i4] & 0xFF;
/*  404 */                     i7 = paramArrayOfbyte[i4 + 1] & 0xFF;
/*  405 */                     softChannel1.controlChangePerNote(i1, i9, i7);
/*      */                   } 
/*      */                   break;
/*      */               } 
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processMessages(long paramLong) {
/*  424 */     Iterator<Map.Entry> iterator = this.midimessages.entrySet().iterator();
/*  425 */     while (iterator.hasNext()) {
/*  426 */       Map.Entry entry = iterator.next();
/*  427 */       if (((Long)entry.getKey()).longValue() >= paramLong + this.msec_buffer_len)
/*      */         return; 
/*  429 */       long l = ((Long)entry.getKey()).longValue() - paramLong;
/*  430 */       this.delay_midievent = (int)(l * this.samplerate / 1000000.0D + 0.5D);
/*  431 */       if (this.delay_midievent > this.max_delay_midievent)
/*  432 */         this.delay_midievent = this.max_delay_midievent; 
/*  433 */       if (this.delay_midievent < 0)
/*  434 */         this.delay_midievent = 0; 
/*  435 */       processMessage(entry.getValue());
/*  436 */       iterator.remove();
/*      */     } 
/*  438 */     this.delay_midievent = 0;
/*      */   } void processAudioBuffers() {
/*      */     double d1;
/*      */     double d2;
/*      */     SoftChannelMixerContainer[] arrayOfSoftChannelMixerContainer;
/*  443 */     if (this.synth.weakstream != null && this.synth.weakstream.silent_samples != 0L) {
/*      */       
/*  445 */       this.sample_pos += this.synth.weakstream.silent_samples;
/*  446 */       this.synth.weakstream.silent_samples = 0L;
/*      */     } 
/*      */     
/*  449 */     for (byte b = 0; b < this.buffers.length; b++) {
/*  450 */       if (b != 3 && b != 4 && b != 5 && b != 8 && b != 9)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  455 */         this.buffers[b].clear();
/*      */       }
/*      */     } 
/*  458 */     if (!this.buffers[3].isSilent())
/*      */     {
/*  460 */       this.buffers[0].swap(this.buffers[3]);
/*      */     }
/*  462 */     if (!this.buffers[4].isSilent())
/*      */     {
/*  464 */       this.buffers[1].swap(this.buffers[4]);
/*      */     }
/*  466 */     if (!this.buffers[5].isSilent())
/*      */     {
/*  468 */       this.buffers[2].swap(this.buffers[5]);
/*      */     }
/*  470 */     if (!this.buffers[8].isSilent())
/*      */     {
/*  472 */       this.buffers[6].swap(this.buffers[8]);
/*      */     }
/*  474 */     if (!this.buffers[9].isSilent())
/*      */     {
/*  476 */       this.buffers[7].swap(this.buffers[9]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  485 */     synchronized (this.control_mutex) {
/*      */       
/*  487 */       long l = (long)(this.sample_pos * 1000000.0D / this.samplerate);
/*      */       
/*  489 */       processMessages(l);
/*      */       
/*  491 */       if (this.active_sensing_on)
/*      */       {
/*      */ 
/*      */         
/*  495 */         if (l - this.msec_last_activity > 1000000L) {
/*  496 */           this.active_sensing_on = false;
/*  497 */           for (SoftChannel softChannel : this.synth.channels) {
/*  498 */             softChannel.allSoundOff();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  503 */       for (byte b1 = 0; b1 < this.voicestatus.length; b1++) {
/*  504 */         if ((this.voicestatus[b1]).active)
/*  505 */           this.voicestatus[b1].processControlLogic(); 
/*  506 */       }  this.sample_pos += this.buffer_len;
/*      */       
/*  508 */       double d3 = this.co_master_volume[0];
/*  509 */       d1 = d3;
/*  510 */       d2 = d3;
/*      */       
/*  512 */       double d4 = this.co_master_balance[0];
/*  513 */       if (d4 > 0.5D) {
/*  514 */         d1 *= (1.0D - d4) * 2.0D;
/*      */       } else {
/*  516 */         d2 *= d4 * 2.0D;
/*      */       } 
/*  518 */       this.chorus.processControlLogic();
/*  519 */       this.reverb.processControlLogic();
/*  520 */       this.agc.processControlLogic();
/*      */       
/*  522 */       if (this.cur_registeredMixers == null && 
/*  523 */         this.registeredMixers != null) {
/*  524 */         this
/*  525 */           .cur_registeredMixers = new SoftChannelMixerContainer[this.registeredMixers.size()];
/*  526 */         this.registeredMixers.toArray(this.cur_registeredMixers);
/*      */       } 
/*      */ 
/*      */       
/*  530 */       arrayOfSoftChannelMixerContainer = this.cur_registeredMixers;
/*  531 */       if (arrayOfSoftChannelMixerContainer != null && 
/*  532 */         arrayOfSoftChannelMixerContainer.length == 0) {
/*  533 */         arrayOfSoftChannelMixerContainer = null;
/*      */       }
/*      */     } 
/*      */     
/*  537 */     if (arrayOfSoftChannelMixerContainer != null) {
/*      */ 
/*      */       
/*  540 */       SoftAudioBuffer softAudioBuffer1 = this.buffers[0];
/*  541 */       SoftAudioBuffer softAudioBuffer2 = this.buffers[1];
/*  542 */       SoftAudioBuffer softAudioBuffer3 = this.buffers[2];
/*  543 */       SoftAudioBuffer softAudioBuffer4 = this.buffers[3];
/*  544 */       SoftAudioBuffer softAudioBuffer5 = this.buffers[4];
/*  545 */       SoftAudioBuffer softAudioBuffer6 = this.buffers[5];
/*      */       
/*  547 */       int j = this.buffers[0].getSize();
/*      */       
/*  549 */       float[][] arrayOfFloat1 = new float[this.nrofchannels][];
/*  550 */       float[][] arrayOfFloat2 = new float[this.nrofchannels][];
/*  551 */       arrayOfFloat2[0] = softAudioBuffer1.array();
/*  552 */       if (this.nrofchannels != 1) {
/*  553 */         arrayOfFloat2[1] = softAudioBuffer2.array();
/*      */       }
/*  555 */       for (SoftChannelMixerContainer softChannelMixerContainer : arrayOfSoftChannelMixerContainer) {
/*      */ 
/*      */ 
/*      */         
/*  559 */         this.buffers[0] = softChannelMixerContainer.buffers[0];
/*  560 */         this.buffers[1] = softChannelMixerContainer.buffers[1];
/*  561 */         this.buffers[2] = softChannelMixerContainer.buffers[2];
/*  562 */         this.buffers[3] = softChannelMixerContainer.buffers[3];
/*  563 */         this.buffers[4] = softChannelMixerContainer.buffers[4];
/*  564 */         this.buffers[5] = softChannelMixerContainer.buffers[5];
/*      */         
/*  566 */         this.buffers[0].clear();
/*  567 */         this.buffers[1].clear();
/*  568 */         this.buffers[2].clear();
/*      */         
/*  570 */         if (!this.buffers[3].isSilent())
/*      */         {
/*  572 */           this.buffers[0].swap(this.buffers[3]);
/*      */         }
/*  574 */         if (!this.buffers[4].isSilent())
/*      */         {
/*  576 */           this.buffers[1].swap(this.buffers[4]);
/*      */         }
/*  578 */         if (!this.buffers[5].isSilent())
/*      */         {
/*  580 */           this.buffers[2].swap(this.buffers[5]);
/*      */         }
/*      */         
/*  583 */         arrayOfFloat1[0] = this.buffers[0].array();
/*  584 */         if (this.nrofchannels != 1) {
/*  585 */           arrayOfFloat1[1] = this.buffers[1].array();
/*      */         }
/*  587 */         boolean bool = false; byte b1;
/*  588 */         for (b1 = 0; b1 < this.voicestatus.length; b1++) {
/*  589 */           if ((this.voicestatus[b1]).active && 
/*  590 */             (this.voicestatus[b1]).channelmixer == softChannelMixerContainer.mixer) {
/*  591 */             this.voicestatus[b1].processAudioLogic(this.buffers);
/*  592 */             bool = true;
/*      */           } 
/*      */         } 
/*  595 */         if (!this.buffers[2].isSilent()) {
/*      */           
/*  597 */           float[] arrayOfFloat3 = this.buffers[2].array();
/*  598 */           float[] arrayOfFloat4 = this.buffers[0].array();
/*  599 */           if (this.nrofchannels != 1) {
/*  600 */             float[] arrayOfFloat = this.buffers[1].array();
/*  601 */             for (byte b2 = 0; b2 < j; b2++) {
/*  602 */               float f = arrayOfFloat3[b2];
/*  603 */               arrayOfFloat4[b2] = arrayOfFloat4[b2] + f;
/*  604 */               arrayOfFloat[b2] = arrayOfFloat[b2] + f;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  609 */             for (byte b2 = 0; b2 < j; b2++) {
/*  610 */               arrayOfFloat4[b2] = arrayOfFloat4[b2] + arrayOfFloat3[b2];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  615 */         if (!softChannelMixerContainer.mixer.process(arrayOfFloat1, 0, j)) {
/*  616 */           synchronized (this.control_mutex) {
/*  617 */             this.registeredMixers.remove(softChannelMixerContainer);
/*  618 */             this.cur_registeredMixers = null;
/*      */           } 
/*      */         }
/*      */         
/*  622 */         for (b1 = 0; b1 < arrayOfFloat1.length; b1++) {
/*  623 */           float[] arrayOfFloat3 = arrayOfFloat1[b1];
/*  624 */           float[] arrayOfFloat4 = arrayOfFloat2[b1];
/*  625 */           for (byte b2 = 0; b2 < j; b2++) {
/*  626 */             arrayOfFloat4[b2] = arrayOfFloat4[b2] + arrayOfFloat3[b2];
/*      */           }
/*      */         } 
/*  629 */         if (!bool) {
/*  630 */           synchronized (this.control_mutex) {
/*  631 */             if (this.stoppedMixers != null && 
/*  632 */               this.stoppedMixers.contains(softChannelMixerContainer)) {
/*  633 */               this.stoppedMixers.remove(softChannelMixerContainer);
/*  634 */               softChannelMixerContainer.mixer.stop();
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  642 */       this.buffers[0] = softAudioBuffer1;
/*  643 */       this.buffers[1] = softAudioBuffer2;
/*  644 */       this.buffers[2] = softAudioBuffer3;
/*  645 */       this.buffers[3] = softAudioBuffer4;
/*  646 */       this.buffers[4] = softAudioBuffer5;
/*  647 */       this.buffers[5] = softAudioBuffer6;
/*      */     } 
/*      */     
/*      */     int i;
/*  651 */     for (i = 0; i < this.voicestatus.length; i++) {
/*  652 */       if ((this.voicestatus[i]).active && 
/*  653 */         (this.voicestatus[i]).channelmixer == null)
/*  654 */         this.voicestatus[i].processAudioLogic(this.buffers); 
/*      */     } 
/*  656 */     if (!this.buffers[2].isSilent()) {
/*      */       
/*  658 */       float[] arrayOfFloat1 = this.buffers[2].array();
/*  659 */       float[] arrayOfFloat2 = this.buffers[0].array();
/*  660 */       int j = this.buffers[0].getSize();
/*  661 */       if (this.nrofchannels != 1) {
/*  662 */         float[] arrayOfFloat = this.buffers[1].array();
/*  663 */         for (byte b1 = 0; b1 < j; b1++) {
/*  664 */           float f = arrayOfFloat1[b1];
/*  665 */           arrayOfFloat2[b1] = arrayOfFloat2[b1] + f;
/*  666 */           arrayOfFloat[b1] = arrayOfFloat[b1] + f;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  671 */         for (byte b1 = 0; b1 < j; b1++) {
/*  672 */           arrayOfFloat2[b1] = arrayOfFloat2[b1] + arrayOfFloat1[b1];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  678 */     if (this.synth.chorus_on) {
/*  679 */       this.chorus.processAudio();
/*      */     }
/*  681 */     if (this.synth.reverb_on) {
/*  682 */       this.reverb.processAudio();
/*      */     }
/*  684 */     if (this.nrofchannels == 1) {
/*  685 */       d1 = (d1 + d2) / 2.0D;
/*      */     }
/*      */     
/*  688 */     if (this.last_volume_left != d1 || this.last_volume_right != d2) {
/*  689 */       float[] arrayOfFloat1 = this.buffers[0].array();
/*  690 */       float[] arrayOfFloat2 = this.buffers[1].array();
/*  691 */       int j = this.buffers[0].getSize();
/*      */ 
/*      */ 
/*      */       
/*  695 */       float f1 = (float)(this.last_volume_left * this.last_volume_left);
/*  696 */       float f2 = (float)((d1 * d1 - f1) / j); byte b1;
/*  697 */       for (b1 = 0; b1 < j; b1++) {
/*  698 */         f1 += f2;
/*  699 */         arrayOfFloat1[b1] = arrayOfFloat1[b1] * f1;
/*      */       } 
/*  701 */       if (this.nrofchannels != 1) {
/*  702 */         f1 = (float)(this.last_volume_right * this.last_volume_right);
/*  703 */         f2 = (float)((d2 * d2 - f1) / j);
/*  704 */         for (b1 = 0; b1 < j; b1++) {
/*  705 */           f1 += f2;
/*  706 */           arrayOfFloat2[b1] = (float)(arrayOfFloat2[b1] * d2);
/*      */         } 
/*      */       } 
/*  709 */       this.last_volume_left = d1;
/*  710 */       this.last_volume_right = d2;
/*      */     
/*      */     }
/*  713 */     else if (d1 != 1.0D || d2 != 1.0D) {
/*  714 */       float[] arrayOfFloat1 = this.buffers[0].array();
/*  715 */       float[] arrayOfFloat2 = this.buffers[1].array();
/*  716 */       int j = this.buffers[0].getSize();
/*      */       
/*  718 */       float f = (float)(d1 * d1); byte b1;
/*  719 */       for (b1 = 0; b1 < j; b1++)
/*  720 */         arrayOfFloat1[b1] = arrayOfFloat1[b1] * f; 
/*  721 */       if (this.nrofchannels != 1) {
/*  722 */         f = (float)(d2 * d2);
/*  723 */         for (b1 = 0; b1 < j; b1++) {
/*  724 */           arrayOfFloat2[b1] = arrayOfFloat2[b1] * f;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  730 */     if (this.buffers[0].isSilent() && this.buffers[1]
/*  731 */       .isSilent()) {
/*      */ 
/*      */ 
/*      */       
/*  735 */       synchronized (this.control_mutex) {
/*  736 */         i = this.midimessages.size();
/*      */       } 
/*      */       
/*  739 */       if (i == 0) {
/*      */         
/*  741 */         this.pusher_silent_count++;
/*  742 */         if (this.pusher_silent_count > 5) {
/*      */           
/*  744 */           this.pusher_silent_count = 0;
/*  745 */           synchronized (this.control_mutex) {
/*  746 */             this.pusher_silent = true;
/*  747 */             if (this.synth.weakstream != null) {
/*  748 */               this.synth.weakstream.setInputStream(null);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  754 */       this.pusher_silent_count = 0;
/*      */     } 
/*  756 */     if (this.synth.agc_on) {
/*  757 */       this.agc.processAudio();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void activity() {
/*  764 */     long l = 0L;
/*  765 */     if (this.pusher_silent) {
/*      */       
/*  767 */       this.pusher_silent = false;
/*  768 */       if (this.synth.weakstream != null) {
/*      */         
/*  770 */         this.synth.weakstream.setInputStream(this.ais);
/*  771 */         l = this.synth.weakstream.silent_samples;
/*      */       } 
/*      */     } 
/*  774 */     this.msec_last_activity = (long)((this.sample_pos + l) * 1000000.0D / this.samplerate);
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopMixer(ModelChannelMixer paramModelChannelMixer) {
/*  779 */     if (this.stoppedMixers == null)
/*  780 */       this.stoppedMixers = new HashSet<>(); 
/*  781 */     this.stoppedMixers.add(paramModelChannelMixer);
/*      */   }
/*      */   
/*      */   public void registerMixer(ModelChannelMixer paramModelChannelMixer) {
/*  785 */     if (this.registeredMixers == null)
/*  786 */       this.registeredMixers = new HashSet<>(); 
/*  787 */     SoftChannelMixerContainer softChannelMixerContainer = new SoftChannelMixerContainer();
/*  788 */     softChannelMixerContainer.buffers = new SoftAudioBuffer[6];
/*  789 */     for (byte b = 0; b < softChannelMixerContainer.buffers.length; b++) {
/*  790 */       softChannelMixerContainer.buffers[b] = new SoftAudioBuffer(this.buffer_len, this.synth
/*  791 */           .getFormat());
/*      */     }
/*  793 */     softChannelMixerContainer.mixer = paramModelChannelMixer;
/*  794 */     this.registeredMixers.add(softChannelMixerContainer);
/*  795 */     this.cur_registeredMixers = null;
/*      */   }
/*      */   
/*      */   public SoftMainMixer(SoftSynthesizer paramSoftSynthesizer) {
/*  799 */     this.synth = paramSoftSynthesizer;
/*      */     
/*  801 */     this.sample_pos = 0L;
/*      */     
/*  803 */     this.co_master_balance[0] = 0.5D;
/*  804 */     this.co_master_volume[0] = 1.0D;
/*  805 */     this.co_master_coarse_tuning[0] = 0.5D;
/*  806 */     this.co_master_fine_tuning[0] = 0.5D;
/*      */     
/*  808 */     this.msec_buffer_len = (long)(1000000.0D / paramSoftSynthesizer.getControlRate());
/*  809 */     this.samplerate = paramSoftSynthesizer.getFormat().getSampleRate();
/*  810 */     this.nrofchannels = paramSoftSynthesizer.getFormat().getChannels();
/*      */ 
/*      */     
/*  813 */     int i = (int)(paramSoftSynthesizer.getFormat().getSampleRate() / paramSoftSynthesizer.getControlRate());
/*      */     
/*  815 */     this.buffer_len = i;
/*      */     
/*  817 */     this.max_delay_midievent = i;
/*      */     
/*  819 */     this.control_mutex = paramSoftSynthesizer.control_mutex;
/*  820 */     this.buffers = new SoftAudioBuffer[14];
/*  821 */     for (byte b = 0; b < this.buffers.length; b++) {
/*  822 */       this.buffers[b] = new SoftAudioBuffer(i, paramSoftSynthesizer.getFormat());
/*      */     }
/*  824 */     this.voicestatus = paramSoftSynthesizer.getVoices();
/*      */     
/*  826 */     this.reverb = new SoftReverb();
/*  827 */     this.chorus = new SoftChorus();
/*  828 */     this.agc = new SoftLimiter();
/*      */     
/*  830 */     float f1 = paramSoftSynthesizer.getFormat().getSampleRate();
/*  831 */     float f2 = paramSoftSynthesizer.getControlRate();
/*  832 */     this.reverb.init(f1, f2);
/*  833 */     this.chorus.init(f1, f2);
/*  834 */     this.agc.init(f1, f2);
/*      */     
/*  836 */     this.reverb.setLightMode(paramSoftSynthesizer.reverb_light);
/*      */     
/*  838 */     this.reverb.setMixMode(true);
/*  839 */     this.chorus.setMixMode(true);
/*  840 */     this.agc.setMixMode(false);
/*      */     
/*  842 */     this.chorus.setInput(0, this.buffers[7]);
/*  843 */     this.chorus.setOutput(0, this.buffers[0]);
/*  844 */     if (this.nrofchannels != 1)
/*  845 */       this.chorus.setOutput(1, this.buffers[1]); 
/*  846 */     this.chorus.setOutput(2, this.buffers[6]);
/*      */     
/*  848 */     this.reverb.setInput(0, this.buffers[6]);
/*  849 */     this.reverb.setOutput(0, this.buffers[0]);
/*  850 */     if (this.nrofchannels != 1) {
/*  851 */       this.reverb.setOutput(1, this.buffers[1]);
/*      */     }
/*  853 */     this.agc.setInput(0, this.buffers[0]);
/*  854 */     if (this.nrofchannels != 1)
/*  855 */       this.agc.setInput(1, this.buffers[1]); 
/*  856 */     this.agc.setOutput(0, this.buffers[0]);
/*  857 */     if (this.nrofchannels != 1) {
/*  858 */       this.agc.setOutput(1, this.buffers[1]);
/*      */     }
/*  860 */     InputStream inputStream = new InputStream()
/*      */       {
/*  862 */         private final SoftAudioBuffer[] buffers = SoftMainMixer.this.buffers;
/*  863 */         private final int nrofchannels = SoftMainMixer.this
/*  864 */           .synth.getFormat().getChannels();
/*  865 */         private final int buffersize = this.buffers[0].getSize();
/*      */ 
/*      */         
/*  868 */         private final byte[] bbuffer = new byte[this.buffersize * SoftMainMixer.this.synth.getFormat().getSampleSizeInBits() / 8 * this.nrofchannels];
/*      */         
/*  870 */         private int bbuffer_pos = 0;
/*  871 */         private final byte[] single = new byte[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void fillBuffer() {
/*  880 */           SoftMainMixer.this.processAudioBuffers();
/*  881 */           for (byte b = 0; b < this.nrofchannels; b++)
/*  882 */             this.buffers[b].get(this.bbuffer, b); 
/*  883 */           this.bbuffer_pos = 0;
/*      */         }
/*      */         
/*      */         public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/*  887 */           int i = this.bbuffer.length;
/*  888 */           int j = param1Int1 + param1Int2;
/*  889 */           int k = param1Int1;
/*  890 */           byte[] arrayOfByte = this.bbuffer;
/*  891 */           while (param1Int1 < j) {
/*  892 */             if (available() == 0) {
/*  893 */               fillBuffer(); continue;
/*      */             } 
/*  895 */             int m = this.bbuffer_pos;
/*  896 */             while (param1Int1 < j && m < i)
/*  897 */               param1ArrayOfbyte[param1Int1++] = arrayOfByte[m++]; 
/*  898 */             this.bbuffer_pos = m;
/*  899 */             if (!SoftMainMixer.this.readfully) {
/*  900 */               return param1Int1 - k;
/*      */             }
/*      */           } 
/*  903 */           return param1Int2;
/*      */         }
/*      */         
/*      */         public int read() throws IOException {
/*  907 */           int i = read(this.single);
/*  908 */           if (i == -1)
/*  909 */             return -1; 
/*  910 */           return this.single[0] & 0xFF;
/*      */         }
/*      */         
/*      */         public int available() {
/*  914 */           return this.bbuffer.length - this.bbuffer_pos;
/*      */         }
/*      */         
/*      */         public void close() {
/*  918 */           SoftMainMixer.this.synth.close();
/*      */         }
/*      */       };
/*      */     
/*  922 */     this.ais = new AudioInputStream(inputStream, paramSoftSynthesizer.getFormat(), -1L);
/*      */   }
/*      */ 
/*      */   
/*      */   public AudioInputStream getInputStream() {
/*  927 */     return this.ais;
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  932 */     SoftChannel[] arrayOfSoftChannel = this.synth.channels;
/*  933 */     for (byte b = 0; b < arrayOfSoftChannel.length; b++) {
/*  934 */       arrayOfSoftChannel[b].allSoundOff();
/*  935 */       arrayOfSoftChannel[b].resetAllControllers(true);
/*      */       
/*  937 */       if (this.synth.getGeneralMidiMode() == 2)
/*  938 */       { if (b == 9) {
/*  939 */           arrayOfSoftChannel[b].programChange(0, 15360);
/*      */         } else {
/*  941 */           arrayOfSoftChannel[b].programChange(0, 15488);
/*      */         }  }
/*  943 */       else { arrayOfSoftChannel[b].programChange(0, 0); }
/*      */     
/*  945 */     }  setVolume(16383);
/*  946 */     setBalance(8192);
/*  947 */     setCoarseTuning(8192);
/*  948 */     setFineTuning(8192);
/*      */     
/*  950 */     globalParameterControlChange(new int[] { 129 }, new long[] { 0L }, new long[] { 4L });
/*      */ 
/*      */     
/*  953 */     globalParameterControlChange(new int[] { 130 }, new long[] { 0L }, new long[] { 2L });
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVolume(int paramInt) {
/*  958 */     synchronized (this.control_mutex) {
/*  959 */       this.co_master_volume[0] = paramInt / 16384.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBalance(int paramInt) {
/*  964 */     synchronized (this.control_mutex) {
/*  965 */       this.co_master_balance[0] = paramInt / 16384.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setFineTuning(int paramInt) {
/*  970 */     synchronized (this.control_mutex) {
/*  971 */       this.co_master_fine_tuning[0] = paramInt / 16384.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setCoarseTuning(int paramInt) {
/*  976 */     synchronized (this.control_mutex) {
/*  977 */       this.co_master_coarse_tuning[0] = paramInt / 16384.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getVolume() {
/*  982 */     synchronized (this.control_mutex) {
/*  983 */       return (int)(this.co_master_volume[0] * 16384.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getBalance() {
/*  988 */     synchronized (this.control_mutex) {
/*  989 */       return (int)(this.co_master_balance[0] * 16384.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getFineTuning() {
/*  994 */     synchronized (this.control_mutex) {
/*  995 */       return (int)(this.co_master_fine_tuning[0] * 16384.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getCoarseTuning() {
/* 1000 */     synchronized (this.control_mutex) {
/* 1001 */       return (int)(this.co_master_coarse_tuning[0] * 16384.0D);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void globalParameterControlChange(int[] paramArrayOfint, long[] paramArrayOflong1, long[] paramArrayOflong2) {
/* 1007 */     if (paramArrayOfint.length == 0) {
/*      */       return;
/*      */     }
/* 1010 */     synchronized (this.control_mutex) {
/*      */ 
/*      */ 
/*      */       
/* 1014 */       if (paramArrayOfint[0] == 129) {
/* 1015 */         for (byte b = 0; b < paramArrayOflong2.length; b++) {
/* 1016 */           this.reverb.globalParameterControlChange(paramArrayOfint, paramArrayOflong1[b], paramArrayOflong2[b]);
/*      */         }
/*      */       }
/*      */       
/* 1020 */       if (paramArrayOfint[0] == 130) {
/* 1021 */         for (byte b = 0; b < paramArrayOflong2.length; b++) {
/* 1022 */           this.chorus.globalParameterControlChange(paramArrayOfint, paramArrayOflong1[b], paramArrayOflong2[b]);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processMessage(Object paramObject) {
/* 1032 */     if (paramObject instanceof byte[])
/* 1033 */       processMessage((byte[])paramObject); 
/* 1034 */     if (paramObject instanceof MidiMessage)
/* 1035 */       processMessage((MidiMessage)paramObject); 
/*      */   }
/*      */   
/*      */   public void processMessage(MidiMessage paramMidiMessage) {
/* 1039 */     if (paramMidiMessage instanceof ShortMessage) {
/* 1040 */       ShortMessage shortMessage = (ShortMessage)paramMidiMessage;
/* 1041 */       processMessage(shortMessage.getChannel(), shortMessage.getCommand(), shortMessage
/* 1042 */           .getData1(), shortMessage.getData2());
/*      */       return;
/*      */     } 
/* 1045 */     processMessage(paramMidiMessage.getMessage());
/*      */   }
/*      */   public void processMessage(byte[] paramArrayOfbyte) {
/*      */     boolean bool1, bool2;
/* 1049 */     int i = 0;
/* 1050 */     if (paramArrayOfbyte.length > 0) {
/* 1051 */       i = paramArrayOfbyte[0] & 0xFF;
/*      */     }
/* 1053 */     if (i == 240) {
/* 1054 */       processSystemExclusiveMessage(paramArrayOfbyte);
/*      */       
/*      */       return;
/*      */     } 
/* 1058 */     int j = i & 0xF0;
/* 1059 */     int k = i & 0xF;
/*      */ 
/*      */ 
/*      */     
/* 1063 */     if (paramArrayOfbyte.length > 1) {
/* 1064 */       bool1 = paramArrayOfbyte[1] & 0xFF;
/*      */     } else {
/* 1066 */       bool1 = false;
/* 1067 */     }  if (paramArrayOfbyte.length > 2) {
/* 1068 */       bool2 = paramArrayOfbyte[2] & 0xFF;
/*      */     } else {
/* 1070 */       bool2 = false;
/*      */     } 
/* 1072 */     processMessage(k, j, bool1, bool2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void processMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1077 */     synchronized (this.synth.control_mutex) {
/* 1078 */       activity();
/*      */     } 
/*      */     
/* 1081 */     if (paramInt2 == 240) {
/* 1082 */       int i = paramInt2 | paramInt1;
/* 1083 */       switch (i) {
/*      */         case 254:
/* 1085 */           synchronized (this.synth.control_mutex) {
/* 1086 */             this.active_sensing_on = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/* 1095 */     SoftChannel[] arrayOfSoftChannel = this.synth.channels;
/* 1096 */     if (paramInt1 >= arrayOfSoftChannel.length)
/*      */       return; 
/* 1098 */     SoftChannel softChannel = arrayOfSoftChannel[paramInt1];
/*      */     
/* 1100 */     switch (paramInt2) {
/*      */       case 144:
/* 1102 */         if (this.delay_midievent != 0) {
/* 1103 */           softChannel.noteOn(paramInt3, paramInt4, this.delay_midievent); break;
/*      */         } 
/* 1105 */         softChannel.noteOn(paramInt3, paramInt4);
/*      */         break;
/*      */       case 128:
/* 1108 */         softChannel.noteOff(paramInt3, paramInt4);
/*      */         break;
/*      */       case 160:
/* 1111 */         softChannel.setPolyPressure(paramInt3, paramInt4);
/*      */         break;
/*      */       case 176:
/* 1114 */         softChannel.controlChange(paramInt3, paramInt4);
/*      */         break;
/*      */       case 192:
/* 1117 */         softChannel.programChange(paramInt3);
/*      */         break;
/*      */       case 208:
/* 1120 */         softChannel.setChannelPressure(paramInt3);
/*      */         break;
/*      */       case 224:
/* 1123 */         softChannel.setPitchBend(paramInt3 + paramInt4 * 128);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMicrosecondPosition() {
/* 1132 */     if (this.pusher_silent)
/*      */     {
/* 1134 */       if (this.synth.weakstream != null)
/*      */       {
/* 1136 */         return (long)((this.sample_pos + this.synth.weakstream.silent_samples) * 1000000.0D / this.samplerate);
/*      */       }
/*      */     }
/*      */     
/* 1140 */     return (long)(this.sample_pos * 1000000.0D / this.samplerate);
/*      */   }
/*      */   
/*      */   public void close() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMainMixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */