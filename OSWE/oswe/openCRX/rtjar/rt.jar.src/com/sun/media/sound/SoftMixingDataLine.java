/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.BooleanControl;
/*     */ import javax.sound.sampled.Control;
/*     */ import javax.sound.sampled.DataLine;
/*     */ import javax.sound.sampled.FloatControl;
/*     */ import javax.sound.sampled.Line;
/*     */ import javax.sound.sampled.LineEvent;
/*     */ import javax.sound.sampled.LineListener;
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
/*     */ public abstract class SoftMixingDataLine
/*     */   implements DataLine
/*     */ {
/*  49 */   public static final FloatControl.Type CHORUS_SEND = new FloatControl.Type("Chorus Send")
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */   
/*     */   protected static final class AudioFloatInputStreamResampler
/*     */     extends AudioFloatInputStream
/*     */   {
/*     */     private final AudioFloatInputStream ais;
/*     */     
/*     */     private final AudioFormat targetFormat;
/*     */     
/*     */     private float[] skipbuffer;
/*     */     private SoftAbstractResampler resampler;
/*  64 */     private final float[] pitch = new float[1];
/*     */     
/*     */     private final float[] ibuffer2;
/*     */     
/*     */     private final float[][] ibuffer;
/*     */     
/*  70 */     private float ibuffer_index = 0.0F;
/*     */     
/*  72 */     private int ibuffer_len = 0;
/*     */     
/*  74 */     private int nrofchannels = 0;
/*     */     
/*     */     private float[][] cbuffer;
/*     */     
/*  78 */     private final int buffer_len = 512;
/*     */     
/*     */     private final int pad;
/*     */     
/*     */     private final int pad2;
/*     */     
/*  84 */     private final float[] ix = new float[1];
/*     */     
/*  86 */     private final int[] ox = new int[1];
/*     */     
/*  88 */     private float[][] mark_ibuffer = (float[][])null;
/*     */     
/*  90 */     private float mark_ibuffer_index = 0.0F;
/*     */     
/*  92 */     private int mark_ibuffer_len = 0;
/*     */ 
/*     */     
/*     */     public AudioFloatInputStreamResampler(AudioFloatInputStream param1AudioFloatInputStream, AudioFormat param1AudioFormat) {
/*  96 */       this.ais = param1AudioFloatInputStream;
/*  97 */       AudioFormat audioFormat = param1AudioFloatInputStream.getFormat();
/*  98 */       this
/*     */ 
/*     */         
/* 101 */         .targetFormat = new AudioFormat(audioFormat.getEncoding(), param1AudioFormat.getSampleRate(), audioFormat.getSampleSizeInBits(), audioFormat.getChannels(), audioFormat.getFrameSize(), param1AudioFormat.getSampleRate(), audioFormat.isBigEndian());
/* 102 */       this.nrofchannels = this.targetFormat.getChannels();
/* 103 */       Object object = param1AudioFormat.getProperty("interpolation");
/* 104 */       if (object != null && object instanceof String) {
/* 105 */         String str = (String)object;
/* 106 */         if (str.equalsIgnoreCase("point"))
/* 107 */           this.resampler = new SoftPointResampler(); 
/* 108 */         if (str.equalsIgnoreCase("linear"))
/* 109 */           this.resampler = new SoftLinearResampler2(); 
/* 110 */         if (str.equalsIgnoreCase("linear1"))
/* 111 */           this.resampler = new SoftLinearResampler(); 
/* 112 */         if (str.equalsIgnoreCase("linear2"))
/* 113 */           this.resampler = new SoftLinearResampler2(); 
/* 114 */         if (str.equalsIgnoreCase("cubic"))
/* 115 */           this.resampler = new SoftCubicResampler(); 
/* 116 */         if (str.equalsIgnoreCase("lanczos"))
/* 117 */           this.resampler = new SoftLanczosResampler(); 
/* 118 */         if (str.equalsIgnoreCase("sinc"))
/* 119 */           this.resampler = new SoftSincResampler(); 
/*     */       } 
/* 121 */       if (this.resampler == null) {
/* 122 */         this.resampler = new SoftLinearResampler2();
/*     */       }
/* 124 */       this.pitch[0] = audioFormat.getSampleRate() / param1AudioFormat.getSampleRate();
/* 125 */       this.pad = this.resampler.getPadding();
/* 126 */       this.pad2 = this.pad * 2;
/* 127 */       this.ibuffer = new float[this.nrofchannels][512 + this.pad2];
/* 128 */       this.ibuffer2 = new float[this.nrofchannels * 512];
/* 129 */       this.ibuffer_index = (512 + this.pad);
/* 130 */       this.ibuffer_len = 512;
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 134 */       return 0;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 138 */       this.ais.close();
/*     */     }
/*     */     
/*     */     public AudioFormat getFormat() {
/* 142 */       return this.targetFormat;
/*     */     }
/*     */     
/*     */     public long getFrameLength() {
/* 146 */       return -1L;
/*     */     }
/*     */     
/*     */     public void mark(int param1Int) {
/* 150 */       this.ais.mark((int)(param1Int * this.pitch[0]));
/* 151 */       this.mark_ibuffer_index = this.ibuffer_index;
/* 152 */       this.mark_ibuffer_len = this.ibuffer_len;
/* 153 */       if (this.mark_ibuffer == null) {
/* 154 */         this.mark_ibuffer = new float[this.ibuffer.length][(this.ibuffer[0]).length];
/*     */       }
/* 156 */       for (byte b = 0; b < this.ibuffer.length; b++) {
/* 157 */         float[] arrayOfFloat1 = this.ibuffer[b];
/* 158 */         float[] arrayOfFloat2 = this.mark_ibuffer[b];
/* 159 */         for (byte b1 = 0; b1 < arrayOfFloat2.length; b1++) {
/* 160 */           arrayOfFloat2[b1] = arrayOfFloat1[b1];
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean markSupported() {
/* 166 */       return this.ais.markSupported();
/*     */     }
/*     */ 
/*     */     
/*     */     private void readNextBuffer() throws IOException {
/* 171 */       if (this.ibuffer_len == -1)
/*     */         return; 
/*     */       int i;
/* 174 */       for (i = 0; i < this.nrofchannels; i++) {
/* 175 */         float[] arrayOfFloat = this.ibuffer[i];
/* 176 */         int j = this.ibuffer_len + this.pad2; int k; byte b1;
/* 177 */         for (k = this.ibuffer_len, b1 = 0; k < j; k++, b1++) {
/* 178 */           arrayOfFloat[b1] = arrayOfFloat[k];
/*     */         }
/*     */       } 
/*     */       
/* 182 */       this.ibuffer_index -= this.ibuffer_len;
/*     */       
/* 184 */       this.ibuffer_len = this.ais.read(this.ibuffer2);
/* 185 */       if (this.ibuffer_len >= 0) {
/* 186 */         while (this.ibuffer_len < this.ibuffer2.length) {
/* 187 */           i = this.ais.read(this.ibuffer2, this.ibuffer_len, this.ibuffer2.length - this.ibuffer_len);
/*     */           
/* 189 */           if (i == -1)
/*     */             break; 
/* 191 */           this.ibuffer_len += i;
/*     */         } 
/* 193 */         Arrays.fill(this.ibuffer2, this.ibuffer_len, this.ibuffer2.length, 0.0F);
/* 194 */         this.ibuffer_len /= this.nrofchannels;
/*     */       } else {
/* 196 */         Arrays.fill(this.ibuffer2, 0, this.ibuffer2.length, 0.0F);
/*     */       } 
/*     */       
/* 199 */       i = this.ibuffer2.length;
/* 200 */       for (byte b = 0; b < this.nrofchannels; b++) {
/* 201 */         float[] arrayOfFloat = this.ibuffer[b];
/* 202 */         for (int j = b, k = this.pad2; j < i; j += this.nrofchannels, k++) {
/* 203 */           arrayOfFloat[k] = this.ibuffer2[j];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 211 */       if (this.cbuffer == null || (this.cbuffer[0]).length < param1Int2 / this.nrofchannels) {
/* 212 */         this.cbuffer = new float[this.nrofchannels][param1Int2 / this.nrofchannels];
/*     */       }
/* 214 */       if (this.ibuffer_len == -1)
/* 215 */         return -1; 
/* 216 */       if (param1Int2 < 0)
/* 217 */         return 0; 
/* 218 */       int i = param1Int2 / this.nrofchannels;
/* 219 */       int j = 0;
/* 220 */       int k = this.ibuffer_len;
/* 221 */       while (i > 0) {
/* 222 */         if (this.ibuffer_len >= 0) {
/* 223 */           if (this.ibuffer_index >= (this.ibuffer_len + this.pad))
/* 224 */             readNextBuffer(); 
/* 225 */           k = this.ibuffer_len + this.pad;
/*     */         } 
/*     */         
/* 228 */         if (this.ibuffer_len < 0) {
/* 229 */           k = this.pad2;
/* 230 */           if (this.ibuffer_index >= k) {
/*     */             break;
/*     */           }
/*     */         } 
/* 234 */         if (this.ibuffer_index < 0.0F)
/*     */           break; 
/* 236 */         int m = j;
/* 237 */         for (byte b1 = 0; b1 < this.nrofchannels; b1++) {
/* 238 */           this.ix[0] = this.ibuffer_index;
/* 239 */           this.ox[0] = j;
/* 240 */           float[] arrayOfFloat = this.ibuffer[b1];
/* 241 */           this.resampler.interpolate(arrayOfFloat, this.ix, k, this.pitch, 0.0F, this.cbuffer[b1], this.ox, param1Int2 / this.nrofchannels);
/*     */         } 
/*     */         
/* 244 */         this.ibuffer_index = this.ix[0];
/* 245 */         j = this.ox[0];
/* 246 */         i -= j - m;
/*     */       } 
/* 248 */       for (byte b = 0; b < this.nrofchannels; b++) {
/* 249 */         byte b1 = 0;
/* 250 */         float[] arrayOfFloat = this.cbuffer[b]; int m;
/* 251 */         for (m = b; m < param1ArrayOffloat.length; m += this.nrofchannels) {
/* 252 */           param1ArrayOffloat[m] = arrayOfFloat[b1++];
/*     */         }
/*     */       } 
/* 255 */       return param1Int2 - i * this.nrofchannels;
/*     */     }
/*     */     
/*     */     public void reset() throws IOException {
/* 259 */       this.ais.reset();
/* 260 */       if (this.mark_ibuffer == null)
/*     */         return; 
/* 262 */       this.ibuffer_index = this.mark_ibuffer_index;
/* 263 */       this.ibuffer_len = this.mark_ibuffer_len;
/* 264 */       for (byte b = 0; b < this.ibuffer.length; b++) {
/* 265 */         float[] arrayOfFloat1 = this.mark_ibuffer[b];
/* 266 */         float[] arrayOfFloat2 = this.ibuffer[b];
/* 267 */         for (byte b1 = 0; b1 < arrayOfFloat2.length; b1++) {
/* 268 */           arrayOfFloat2[b1] = arrayOfFloat1[b1];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 275 */       if (param1Long > 0L)
/* 276 */         return 0L; 
/* 277 */       if (this.skipbuffer == null)
/* 278 */         this.skipbuffer = new float[1024 * this.targetFormat.getFrameSize()]; 
/* 279 */       float[] arrayOfFloat = this.skipbuffer;
/* 280 */       long l = param1Long;
/* 281 */       while (l > 0L) {
/* 282 */         int i = read(arrayOfFloat, 0, (int)Math.min(l, this.skipbuffer.length));
/*     */         
/* 284 */         if (i < 0) {
/* 285 */           if (l == param1Long)
/* 286 */             return i; 
/*     */           break;
/*     */         } 
/* 289 */         l -= i;
/*     */       } 
/* 291 */       return param1Long - l;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class Gain
/*     */     extends FloatControl
/*     */   {
/*     */     private Gain() {
/* 301 */       super(FloatControl.Type.MASTER_GAIN, -80.0F, 6.0206F, 0.625F, -1, 0.0F, "dB", "Minimum", "", "Maximum");
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(float param1Float) {
/* 306 */       super.setValue(param1Float);
/* 307 */       SoftMixingDataLine.this.calcVolume();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Mute
/*     */     extends BooleanControl {
/*     */     private Mute() {
/* 314 */       super(BooleanControl.Type.MUTE, false, "True", "False");
/*     */     }
/*     */     
/*     */     public void setValue(boolean param1Boolean) {
/* 318 */       super.setValue(param1Boolean);
/* 319 */       SoftMixingDataLine.this.calcVolume();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ApplyReverb
/*     */     extends BooleanControl {
/*     */     private ApplyReverb() {
/* 326 */       super(BooleanControl.Type.APPLY_REVERB, false, "True", "False");
/*     */     }
/*     */     
/*     */     public void setValue(boolean param1Boolean) {
/* 330 */       super.setValue(param1Boolean);
/* 331 */       SoftMixingDataLine.this.calcVolume();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Balance
/*     */     extends FloatControl
/*     */   {
/*     */     private Balance() {
/* 339 */       super(FloatControl.Type.BALANCE, -1.0F, 1.0F, 0.0078125F, -1, 0.0F, "", "Left", "Center", "Right");
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(float param1Float) {
/* 344 */       super.setValue(param1Float);
/* 345 */       SoftMixingDataLine.this.calcVolume();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Pan
/*     */     extends FloatControl
/*     */   {
/*     */     private Pan() {
/* 353 */       super(FloatControl.Type.PAN, -1.0F, 1.0F, 0.0078125F, -1, 0.0F, "", "Left", "Center", "Right");
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(float param1Float) {
/* 358 */       super.setValue(param1Float);
/* 359 */       SoftMixingDataLine.this.balance_control.setValue(param1Float);
/*     */     }
/*     */     
/*     */     public float getValue() {
/* 363 */       return SoftMixingDataLine.this.balance_control.getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ReverbSend
/*     */     extends FloatControl
/*     */   {
/*     */     private ReverbSend() {
/* 371 */       super(FloatControl.Type.REVERB_SEND, -80.0F, 6.0206F, 0.625F, -1, -80.0F, "dB", "Minimum", "", "Maximum");
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(float param1Float) {
/* 376 */       super.setValue(param1Float);
/* 377 */       SoftMixingDataLine.this.balance_control.setValue(param1Float);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ChorusSend
/*     */     extends FloatControl
/*     */   {
/*     */     private ChorusSend() {
/* 385 */       super(SoftMixingDataLine.CHORUS_SEND, -80.0F, 6.0206F, 0.625F, -1, -80.0F, "dB", "Minimum", "", "Maximum");
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(float param1Float) {
/* 390 */       super.setValue(param1Float);
/* 391 */       SoftMixingDataLine.this.balance_control.setValue(param1Float);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 396 */   private final Gain gain_control = new Gain();
/*     */   
/* 398 */   private final Mute mute_control = new Mute();
/*     */   
/* 400 */   private final Balance balance_control = new Balance();
/*     */   
/* 402 */   private final Pan pan_control = new Pan();
/*     */   
/* 404 */   private final ReverbSend reverbsend_control = new ReverbSend();
/*     */   
/* 406 */   private final ChorusSend chorussend_control = new ChorusSend();
/*     */   
/* 408 */   private final ApplyReverb apply_reverb = new ApplyReverb();
/*     */   
/*     */   private final Control[] controls;
/*     */   
/* 412 */   float leftgain = 1.0F;
/*     */   
/* 414 */   float rightgain = 1.0F;
/*     */   
/* 416 */   float eff1gain = 0.0F;
/*     */   
/* 418 */   float eff2gain = 0.0F;
/*     */   
/* 420 */   List<LineListener> listeners = new ArrayList<>();
/*     */ 
/*     */   
/*     */   final Object control_mutex;
/*     */ 
/*     */   
/*     */   SoftMixingMixer mixer;
/*     */ 
/*     */   
/*     */   DataLine.Info info;
/*     */ 
/*     */   
/*     */   SoftMixingDataLine(SoftMixingMixer paramSoftMixingMixer, DataLine.Info paramInfo) {
/* 433 */     this.mixer = paramSoftMixingMixer;
/* 434 */     this.info = paramInfo;
/* 435 */     this.control_mutex = paramSoftMixingMixer.control_mutex;
/*     */     
/* 437 */     this.controls = new Control[] { this.gain_control, this.mute_control, this.balance_control, this.pan_control, this.reverbsend_control, this.chorussend_control, this.apply_reverb };
/*     */ 
/*     */     
/* 440 */     calcVolume();
/*     */   }
/*     */   
/*     */   final void calcVolume() {
/* 444 */     synchronized (this.control_mutex) {
/* 445 */       double d = Math.pow(10.0D, this.gain_control.getValue() / 20.0D);
/* 446 */       if (this.mute_control.getValue())
/* 447 */         d = 0.0D; 
/* 448 */       this.leftgain = (float)d;
/* 449 */       this.rightgain = (float)d;
/* 450 */       if (this.mixer.getFormat().getChannels() > 1) {
/*     */         
/* 452 */         double d1 = this.balance_control.getValue();
/* 453 */         if (d1 > 0.0D) {
/* 454 */           this.leftgain = (float)(this.leftgain * (1.0D - d1));
/*     */         } else {
/* 456 */           this.rightgain = (float)(this.rightgain * (1.0D + d1));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     this.eff1gain = (float)Math.pow(10.0D, this.reverbsend_control.getValue() / 20.0D);
/* 462 */     this.eff2gain = (float)Math.pow(10.0D, this.chorussend_control.getValue() / 20.0D);
/*     */     
/* 464 */     if (!this.apply_reverb.getValue()) {
/* 465 */       this.eff1gain = 0.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   final void sendEvent(LineEvent paramLineEvent) {
/* 470 */     if (this.listeners.size() == 0) {
/*     */       return;
/*     */     }
/* 473 */     LineListener[] arrayOfLineListener = this.listeners.<LineListener>toArray(new LineListener[this.listeners.size()]);
/* 474 */     for (LineListener lineListener : arrayOfLineListener) {
/* 475 */       lineListener.update(paramLineEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void addLineListener(LineListener paramLineListener) {
/* 480 */     synchronized (this.control_mutex) {
/* 481 */       this.listeners.add(paramLineListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void removeLineListener(LineListener paramLineListener) {
/* 486 */     synchronized (this.control_mutex) {
/* 487 */       this.listeners.add(paramLineListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Line.Info getLineInfo() {
/* 492 */     return this.info;
/*     */   }
/*     */   
/*     */   public final Control getControl(Control.Type paramType) {
/* 496 */     if (paramType != null) {
/* 497 */       for (byte b = 0; b < this.controls.length; b++) {
/* 498 */         if (this.controls[b].getType() == paramType) {
/* 499 */           return this.controls[b];
/*     */         }
/*     */       } 
/*     */     }
/* 503 */     throw new IllegalArgumentException("Unsupported control type : " + paramType);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Control[] getControls() {
/* 508 */     return Arrays.<Control>copyOf(this.controls, this.controls.length);
/*     */   }
/*     */   
/*     */   public final boolean isControlSupported(Control.Type paramType) {
/* 512 */     if (paramType != null) {
/* 513 */       for (byte b = 0; b < this.controls.length; b++) {
/* 514 */         if (this.controls[b].getType() == paramType) {
/* 515 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 519 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract void processControlLogic();
/*     */   
/*     */   protected abstract void processAudioLogic(SoftAudioBuffer[] paramArrayOfSoftAudioBuffer);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftMixingDataLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */