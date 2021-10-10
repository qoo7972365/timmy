/*      */ package com.sun.media.sound;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.sound.midi.MidiChannel;
/*      */ import javax.sound.midi.Patch;
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
/*      */ public final class SoftChannel
/*      */   implements MidiChannel, ModelDirectedPlayer
/*      */ {
/*   44 */   private static boolean[] dontResetControls = new boolean[128]; private static final int RPN_NULL_VALUE = 16383;
/*      */   static {
/*   46 */     for (byte b = 0; b < dontResetControls.length; b++) {
/*   47 */       dontResetControls[b] = false;
/*      */     }
/*   49 */     dontResetControls[0] = true;
/*   50 */     dontResetControls[32] = true;
/*   51 */     dontResetControls[7] = true;
/*   52 */     dontResetControls[8] = true;
/*   53 */     dontResetControls[10] = true;
/*   54 */     dontResetControls[11] = true;
/*   55 */     dontResetControls[91] = true;
/*   56 */     dontResetControls[92] = true;
/*   57 */     dontResetControls[93] = true;
/*   58 */     dontResetControls[94] = true;
/*   59 */     dontResetControls[95] = true;
/*   60 */     dontResetControls[70] = true;
/*   61 */     dontResetControls[71] = true;
/*   62 */     dontResetControls[72] = true;
/*   63 */     dontResetControls[73] = true;
/*   64 */     dontResetControls[74] = true;
/*   65 */     dontResetControls[75] = true;
/*   66 */     dontResetControls[76] = true;
/*   67 */     dontResetControls[77] = true;
/*   68 */     dontResetControls[78] = true;
/*   69 */     dontResetControls[79] = true;
/*   70 */     dontResetControls[120] = true;
/*   71 */     dontResetControls[121] = true;
/*   72 */     dontResetControls[122] = true;
/*   73 */     dontResetControls[123] = true;
/*   74 */     dontResetControls[124] = true;
/*   75 */     dontResetControls[125] = true;
/*   76 */     dontResetControls[126] = true;
/*   77 */     dontResetControls[127] = true;
/*      */     
/*   79 */     dontResetControls[6] = true;
/*   80 */     dontResetControls[38] = true;
/*   81 */     dontResetControls[96] = true;
/*   82 */     dontResetControls[97] = true;
/*   83 */     dontResetControls[98] = true;
/*   84 */     dontResetControls[99] = true;
/*   85 */     dontResetControls[100] = true;
/*   86 */     dontResetControls[101] = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*   91 */   private int rpn_control = 16383;
/*   92 */   private int nrpn_control = 16383;
/*   93 */   double portamento_time = 1.0D;
/*   94 */   int[] portamento_lastnote = new int[128];
/*   95 */   int portamento_lastnote_ix = 0;
/*      */   private boolean portamento = false;
/*      */   private boolean mono = false;
/*      */   private boolean mute = false;
/*      */   private boolean solo = false;
/*      */   private boolean solomute = false;
/*      */   private final Object control_mutex;
/*      */   private int channel;
/*      */   private SoftVoice[] voices;
/*      */   private int bank;
/*      */   private int program;
/*      */   private SoftSynthesizer synthesizer;
/*      */   private SoftMainMixer mainmixer;
/*  108 */   private int[] polypressure = new int[128];
/*  109 */   private int channelpressure = 0;
/*  110 */   private int[] controller = new int[128];
/*      */   private int pitchbend;
/*  112 */   private double[] co_midi_pitch = new double[1];
/*  113 */   private double[] co_midi_channel_pressure = new double[1];
/*  114 */   SoftTuning tuning = new SoftTuning();
/*  115 */   int tuning_bank = 0;
/*  116 */   int tuning_program = 0;
/*  117 */   SoftInstrument current_instrument = null;
/*  118 */   ModelChannelMixer current_mixer = null;
/*  119 */   ModelDirector current_director = null;
/*      */ 
/*      */   
/*  122 */   int cds_control_number = -1;
/*  123 */   ModelConnectionBlock[] cds_control_connections = null;
/*  124 */   ModelConnectionBlock[] cds_channelpressure_connections = null;
/*  125 */   ModelConnectionBlock[] cds_polypressure_connections = null;
/*      */   boolean sustain = false;
/*  127 */   boolean[][] keybasedcontroller_active = (boolean[][])null;
/*  128 */   double[][] keybasedcontroller_value = (double[][])null;
/*      */   
/*      */   private class MidiControlObject implements SoftControl {
/*  131 */     double[] pitch = SoftChannel.this.co_midi_pitch;
/*  132 */     double[] channel_pressure = SoftChannel.this.co_midi_channel_pressure;
/*  133 */     double[] poly_pressure = new double[1];
/*      */     
/*      */     public double[] get(int param1Int, String param1String) {
/*  136 */       if (param1String == null)
/*  137 */         return null; 
/*  138 */       if (param1String.equals("pitch"))
/*  139 */         return this.pitch; 
/*  140 */       if (param1String.equals("channel_pressure"))
/*  141 */         return this.channel_pressure; 
/*  142 */       if (param1String.equals("poly_pressure"))
/*  143 */         return this.poly_pressure; 
/*  144 */       return null;
/*      */     }
/*      */     
/*      */     private MidiControlObject() {} }
/*  148 */   private SoftControl[] co_midi = new SoftControl[128];
/*      */   private double[][] co_midi_cc_cc;
/*  150 */   private SoftControl co_midi_cc; Map<Integer, int[]> co_midi_rpn_rpn_i; Map<Integer, double[]> co_midi_rpn_rpn; private SoftControl co_midi_rpn; Map<Integer, int[]> co_midi_nrpn_nrpn_i; Map<Integer, double[]> co_midi_nrpn_nrpn; private SoftControl co_midi_nrpn; private int[] lastVelocity; private int prevVoiceID; private boolean firstVoice; private int voiceNo; private int play_noteNumber; private int play_velocity; private int play_delay; private boolean play_releasetriggered; private static int restrict7Bit(int paramInt) { if (paramInt < 0) return 0;  if (paramInt > 127) return 127;  return paramInt; } private static int restrict14Bit(int paramInt) { if (paramInt < 0) return 0;  if (paramInt > 16256) return 16256;  return paramInt; } public SoftChannel(SoftSynthesizer paramSoftSynthesizer, int paramInt) { for (byte b = 0; b < this.co_midi.length; b++) {
/*  151 */       this.co_midi[b] = new MidiControlObject();
/*      */     }
/*      */ 
/*      */     
/*  155 */     this.co_midi_cc_cc = new double[128][1];
/*  156 */     this.co_midi_cc = new SoftControl() {
/*  157 */         double[][] cc = SoftChannel.this.co_midi_cc_cc;
/*      */         public double[] get(int param1Int, String param1String) {
/*  159 */           if (param1String == null)
/*  160 */             return null; 
/*  161 */           return this.cc[Integer.parseInt(param1String)];
/*      */         }
/*      */       };
/*  164 */     this.co_midi_rpn_rpn_i = (Map)new HashMap<>();
/*  165 */     this.co_midi_rpn_rpn = (Map)new HashMap<>();
/*  166 */     this.co_midi_rpn = new SoftControl() {
/*  167 */         Map<Integer, double[]> rpn = SoftChannel.this.co_midi_rpn_rpn;
/*      */         public double[] get(int param1Int, String param1String) {
/*  169 */           if (param1String == null)
/*  170 */             return null; 
/*  171 */           int i = Integer.parseInt(param1String);
/*  172 */           double[] arrayOfDouble = this.rpn.get(Integer.valueOf(i));
/*  173 */           if (arrayOfDouble == null) {
/*  174 */             arrayOfDouble = new double[1];
/*  175 */             this.rpn.put(Integer.valueOf(i), arrayOfDouble);
/*      */           } 
/*  177 */           return arrayOfDouble;
/*      */         }
/*      */       };
/*  180 */     this.co_midi_nrpn_nrpn_i = (Map)new HashMap<>();
/*  181 */     this.co_midi_nrpn_nrpn = (Map)new HashMap<>();
/*  182 */     this.co_midi_nrpn = new SoftControl() {
/*  183 */         Map<Integer, double[]> nrpn = SoftChannel.this.co_midi_nrpn_nrpn;
/*      */         public double[] get(int param1Int, String param1String) {
/*  185 */           if (param1String == null)
/*  186 */             return null; 
/*  187 */           int i = Integer.parseInt(param1String);
/*  188 */           double[] arrayOfDouble = this.nrpn.get(Integer.valueOf(i));
/*  189 */           if (arrayOfDouble == null) {
/*  190 */             arrayOfDouble = new double[1];
/*  191 */             this.nrpn.put(Integer.valueOf(i), arrayOfDouble);
/*      */           } 
/*  193 */           return arrayOfDouble;
/*      */         }
/*      */       };
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  619 */     this.lastVelocity = new int[128];
/*      */     
/*  621 */     this.firstVoice = true;
/*  622 */     this.voiceNo = 0;
/*  623 */     this.play_noteNumber = 0;
/*  624 */     this.play_velocity = 0;
/*  625 */     this.play_delay = 0;
/*  626 */     this.play_releasetriggered = false; this.channel = paramInt; this.voices = paramSoftSynthesizer.getVoices(); this.synthesizer = paramSoftSynthesizer; this.mainmixer = paramSoftSynthesizer.getMainMixer(); this.control_mutex = paramSoftSynthesizer.control_mutex; resetAllControllers(true); }
/*      */   private int findFreeVoice(int paramInt) { if (paramInt == -1) return -1;  int i; for (i = paramInt; i < this.voices.length; i++) { if (!(this.voices[i]).active) return i;  }  i = this.synthesizer.getVoiceAllocationMode(); if (i == 1) { int j = this.channel; byte b2; for (b2 = 0; b2 < this.voices.length; b2++) { if ((this.voices[b2]).stealer_channel == null) if (j == 9) { j = (this.voices[b2]).channel; } else if ((this.voices[b2]).channel != 9 && (this.voices[b2]).channel > j) { j = (this.voices[b2]).channel; }   }  b2 = -1; SoftVoice softVoice1 = null; byte b3; for (b3 = 0; b3 < this.voices.length; b3++) { if ((this.voices[b3]).channel == j && (this.voices[b3]).stealer_channel == null && !(this.voices[b3]).on) { if (softVoice1 == null) { softVoice1 = this.voices[b3]; b2 = b3; }  if ((this.voices[b3]).voiceID < softVoice1.voiceID) { softVoice1 = this.voices[b3]; b2 = b3; }  }  }  if (b2 == -1) for (b3 = 0; b3 < this.voices.length; b3++) { if ((this.voices[b3]).channel == j && (this.voices[b3]).stealer_channel == null) { if (softVoice1 == null) { softVoice1 = this.voices[b3]; b2 = b3; }  if ((this.voices[b3]).voiceID < softVoice1.voiceID) { softVoice1 = this.voices[b3]; b2 = b3; }  }  }   return b2; }  byte b = -1; SoftVoice softVoice = null; byte b1; for (b1 = 0; b1 < this.voices.length; b1++) { if ((this.voices[b1]).stealer_channel == null && !(this.voices[b1]).on) { if (softVoice == null) { softVoice = this.voices[b1]; b = b1; }  if ((this.voices[b1]).voiceID < softVoice.voiceID) { softVoice = this.voices[b1]; b = b1; }  }  }  if (b == -1) for (b1 = 0; b1 < this.voices.length; b1++) { if ((this.voices[b1]).stealer_channel == null) { if (softVoice == null) { softVoice = this.voices[b1]; b = b1; }  if ((this.voices[b1]).voiceID < softVoice.voiceID) { softVoice = this.voices[b1]; b = b1; }  }  }   return b; }
/*      */   void initVoice(SoftVoice paramSoftVoice, SoftPerformer paramSoftPerformer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ModelConnectionBlock[] paramArrayOfModelConnectionBlock, ModelChannelMixer paramModelChannelMixer, boolean paramBoolean) { if (paramSoftVoice.active) { paramSoftVoice.stealer_channel = this; paramSoftVoice.stealer_performer = paramSoftPerformer; paramSoftVoice.stealer_voiceID = paramInt1; paramSoftVoice.stealer_noteNumber = paramInt2; paramSoftVoice.stealer_velocity = paramInt3; paramSoftVoice.stealer_extendedConnectionBlocks = paramArrayOfModelConnectionBlock; paramSoftVoice.stealer_channelmixer = paramModelChannelMixer; paramSoftVoice.stealer_releaseTriggered = paramBoolean; for (byte b = 0; b < this.voices.length; b++) { if ((this.voices[b]).active && (this.voices[b]).voiceID == paramSoftVoice.voiceID)
/*      */           this.voices[b].soundOff();  }  return; }  paramSoftVoice.extendedConnectionBlocks = paramArrayOfModelConnectionBlock; paramSoftVoice.channelmixer = paramModelChannelMixer; paramSoftVoice.releaseTriggered = paramBoolean; paramSoftVoice.voiceID = paramInt1; paramSoftVoice.tuning = this.tuning; paramSoftVoice.exclusiveClass = paramSoftPerformer.exclusiveClass; paramSoftVoice.softchannel = this; paramSoftVoice.channel = this.channel; paramSoftVoice.bank = this.bank; paramSoftVoice.program = this.program; paramSoftVoice.instrument = this.current_instrument; paramSoftVoice.performer = paramSoftPerformer; paramSoftVoice.objects.clear(); paramSoftVoice.objects.put("midi", this.co_midi[paramInt2]); paramSoftVoice.objects.put("midi_cc", this.co_midi_cc); paramSoftVoice.objects.put("midi_rpn", this.co_midi_rpn); paramSoftVoice.objects.put("midi_nrpn", this.co_midi_nrpn); paramSoftVoice.noteOn(paramInt2, paramInt3, paramInt4); paramSoftVoice.setMute(this.mute); paramSoftVoice.setSoloMute(this.solomute); if (paramBoolean)
/*  630 */       return;  if (this.controller[84] != 0) { paramSoftVoice.co_noteon_keynumber[0] = this.tuning.getTuning(this.controller[84]) / 100.0D * 0.0078125D; paramSoftVoice.portamento = true; controlChange(84, 0); } else if (this.portamento) { if (this.mono) { if (this.portamento_lastnote[0] != -1) { paramSoftVoice.co_noteon_keynumber[0] = this.tuning.getTuning(this.portamento_lastnote[0]) / 100.0D * 0.0078125D; paramSoftVoice.portamento = true; controlChange(84, 0); }  this.portamento_lastnote[0] = paramInt2; } else if (this.portamento_lastnote_ix != 0) { this.portamento_lastnote_ix--; paramSoftVoice.co_noteon_keynumber[0] = this.tuning.getTuning(this.portamento_lastnote[this.portamento_lastnote_ix]) / 100.0D * 0.0078125D; paramSoftVoice.portamento = true; }  }  } public void play(int paramInt, ModelConnectionBlock[] paramArrayOfModelConnectionBlock) { int i = this.play_noteNumber;
/*  631 */     int j = this.play_velocity;
/*  632 */     int k = this.play_delay;
/*  633 */     boolean bool = this.play_releasetriggered;
/*      */     
/*  635 */     SoftPerformer softPerformer = this.current_instrument.getPerformer(paramInt);
/*      */     
/*  637 */     if (this.firstVoice) {
/*  638 */       this.firstVoice = false;
/*  639 */       if (softPerformer.exclusiveClass != 0) {
/*  640 */         int m = softPerformer.exclusiveClass;
/*  641 */         for (byte b = 0; b < this.voices.length; b++) {
/*  642 */           if ((this.voices[b]).active && (this.voices[b]).channel == this.channel && (this.voices[b]).exclusiveClass == m)
/*      */           {
/*  644 */             if (!softPerformer.selfNonExclusive || (this.voices[b]).note != i) {
/*  645 */               this.voices[b].shutdown();
/*      */             }
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  651 */     this.voiceNo = findFreeVoice(this.voiceNo);
/*      */     
/*  653 */     if (this.voiceNo == -1) {
/*      */       return;
/*      */     }
/*  656 */     initVoice(this.voices[this.voiceNo], softPerformer, this.prevVoiceID, i, j, k, paramArrayOfModelConnectionBlock, this.current_mixer, bool); } public void noteOn(int paramInt1, int paramInt2) { noteOn(paramInt1, paramInt2, 0); }
/*      */   void noteOn(int paramInt1, int paramInt2, int paramInt3) { paramInt1 = restrict7Bit(paramInt1); paramInt2 = restrict7Bit(paramInt2); noteOn_internal(paramInt1, paramInt2, paramInt3); if (this.current_mixer != null) this.current_mixer.noteOn(paramInt1, paramInt2);  }
/*      */   private void noteOn_internal(int paramInt1, int paramInt2, int paramInt3) { if (paramInt2 == 0) { noteOff_internal(paramInt1, 64); return; }  synchronized (this.control_mutex) { if (this.sustain) { this.sustain = false; for (byte b = 0; b < this.voices.length; b++) { if (((this.voices[b]).sustain || (this.voices[b]).on) && (this.voices[b]).channel == this.channel && (this.voices[b]).active && (this.voices[b]).note == paramInt1) { (this.voices[b]).sustain = false; (this.voices[b]).on = true; this.voices[b].noteOff(0); }  }  this.sustain = true; }  this.mainmixer.activity(); if (this.mono) { if (this.portamento) { boolean bool = false; for (byte b = 0; b < this.voices.length; b++) { if ((this.voices[b]).on && (this.voices[b]).channel == this.channel && (this.voices[b]).active && !(this.voices[b]).releaseTriggered) { (this.voices[b]).portamento = true; this.voices[b].setNote(paramInt1); bool = true; }  }  if (bool) { this.portamento_lastnote[0] = paramInt1; return; }  }  if (this.controller[84] != 0) { boolean bool = false; for (byte b = 0; b < this.voices.length; b++) { if ((this.voices[b]).on && (this.voices[b]).channel == this.channel && (this.voices[b]).active && (this.voices[b]).note == this.controller[84] && !(this.voices[b]).releaseTriggered) { (this.voices[b]).portamento = true; this.voices[b].setNote(paramInt1); bool = true; }  }  controlChange(84, 0); if (bool) return;  }  }  if (this.mono) allNotesOff();  if (this.current_instrument == null) { this.current_instrument = this.synthesizer.findInstrument(this.program, this.bank, this.channel); if (this.current_instrument == null) return;  if (this.current_mixer != null) this.mainmixer.stopMixer(this.current_mixer);  this.current_mixer = this.current_instrument.getSourceInstrument().getChannelMixer(this, this.synthesizer.getFormat()); if (this.current_mixer != null) this.mainmixer.registerMixer(this.current_mixer);  this.current_director = this.current_instrument.getDirector(this, this); applyInstrumentCustomization(); }  this.prevVoiceID = this.synthesizer.voiceIDCounter++; this.firstVoice = true; this.voiceNo = 0; int i = (int)Math.round(this.tuning.getTuning(paramInt1) / 100.0D); this.play_noteNumber = paramInt1; this.play_velocity = paramInt2; this.play_delay = paramInt3; this.play_releasetriggered = false; this.lastVelocity[paramInt1] = paramInt2; this.current_director.noteOn(i, paramInt2); }  }
/*      */   public void noteOff(int paramInt1, int paramInt2) { paramInt1 = restrict7Bit(paramInt1); paramInt2 = restrict7Bit(paramInt2); noteOff_internal(paramInt1, paramInt2); if (this.current_mixer != null) this.current_mixer.noteOff(paramInt1, paramInt2);  }
/*      */   private void noteOff_internal(int paramInt1, int paramInt2) { synchronized (this.control_mutex) { if (!this.mono && this.portamento && this.portamento_lastnote_ix != 127) { this.portamento_lastnote[this.portamento_lastnote_ix] = paramInt1; this.portamento_lastnote_ix++; }  this.mainmixer.activity(); int i; for (i = 0; i < this.voices.length; i++) { if ((this.voices[i]).on && (this.voices[i]).channel == this.channel && (this.voices[i]).note == paramInt1 && !(this.voices[i]).releaseTriggered) this.voices[i].noteOff(paramInt2);  if ((this.voices[i]).stealer_channel == this && (this.voices[i]).stealer_noteNumber == paramInt1) { SoftVoice softVoice = this.voices[i]; softVoice.stealer_releaseTriggered = false; softVoice.stealer_channel = null; softVoice.stealer_performer = null; softVoice.stealer_voiceID = -1; softVoice.stealer_noteNumber = 0; softVoice.stealer_velocity = 0; softVoice.stealer_extendedConnectionBlocks = null; softVoice.stealer_channelmixer = null; }  }  if (this.current_instrument == null) { this.current_instrument = this.synthesizer.findInstrument(this.program, this.bank, this.channel); if (this.current_instrument == null) return;  if (this.current_mixer != null) this.mainmixer.stopMixer(this.current_mixer);  this.current_mixer = this.current_instrument.getSourceInstrument().getChannelMixer(this, this.synthesizer.getFormat()); if (this.current_mixer != null) this.mainmixer.registerMixer(this.current_mixer);  this.current_director = this.current_instrument.getDirector(this, this); applyInstrumentCustomization(); }  this.prevVoiceID = this.synthesizer.voiceIDCounter++; this.firstVoice = true; this.voiceNo = 0; i = (int)Math.round(this.tuning.getTuning(paramInt1) / 100.0D); this.play_noteNumber = paramInt1; this.play_velocity = this.lastVelocity[paramInt1]; this.play_releasetriggered = true; this.play_delay = 0; this.current_director.noteOff(i, paramInt2); }  }
/*  661 */   public void noteOff(int paramInt) { if (paramInt < 0 || paramInt > 127)
/*  662 */       return;  noteOff_internal(paramInt, 64); }
/*      */ 
/*      */   
/*      */   public void setPolyPressure(int paramInt1, int paramInt2) {
/*  666 */     paramInt1 = restrict7Bit(paramInt1);
/*  667 */     paramInt2 = restrict7Bit(paramInt2);
/*      */     
/*  669 */     if (this.current_mixer != null) {
/*  670 */       this.current_mixer.setPolyPressure(paramInt1, paramInt2);
/*      */     }
/*  672 */     synchronized (this.control_mutex) {
/*  673 */       this.mainmixer.activity();
/*  674 */       this.co_midi[paramInt1].get(0, "poly_pressure")[0] = paramInt2 * 0.0078125D;
/*  675 */       this.polypressure[paramInt1] = paramInt2;
/*  676 */       for (byte b = 0; b < this.voices.length; b++) {
/*  677 */         if ((this.voices[b]).active && (this.voices[b]).note == paramInt1)
/*  678 */           this.voices[b].setPolyPressure(paramInt2); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getPolyPressure(int paramInt) {
/*  684 */     synchronized (this.control_mutex) {
/*  685 */       return this.polypressure[paramInt];
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setChannelPressure(int paramInt) {
/*  690 */     paramInt = restrict7Bit(paramInt);
/*  691 */     if (this.current_mixer != null)
/*  692 */       this.current_mixer.setChannelPressure(paramInt); 
/*  693 */     synchronized (this.control_mutex) {
/*  694 */       this.mainmixer.activity();
/*  695 */       this.co_midi_channel_pressure[0] = paramInt * 0.0078125D;
/*  696 */       this.channelpressure = paramInt;
/*  697 */       for (byte b = 0; b < this.voices.length; b++) {
/*  698 */         if ((this.voices[b]).active)
/*  699 */           this.voices[b].setChannelPressure(paramInt); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getChannelPressure() {
/*  705 */     synchronized (this.control_mutex) {
/*  706 */       return this.channelpressure;
/*      */     } 
/*      */   }
/*      */   
/*      */   void applyInstrumentCustomization() {
/*  711 */     if (this.cds_control_connections == null && this.cds_channelpressure_connections == null && this.cds_polypressure_connections == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  717 */     ModelInstrument modelInstrument = this.current_instrument.getSourceInstrument();
/*  718 */     ModelPerformer[] arrayOfModelPerformer1 = modelInstrument.getPerformers();
/*  719 */     ModelPerformer[] arrayOfModelPerformer2 = new ModelPerformer[arrayOfModelPerformer1.length];
/*  720 */     for (byte b = 0; b < arrayOfModelPerformer2.length; b++) {
/*  721 */       ModelPerformer modelPerformer1 = arrayOfModelPerformer1[b];
/*  722 */       ModelPerformer modelPerformer2 = new ModelPerformer();
/*  723 */       modelPerformer2.setName(modelPerformer1.getName());
/*  724 */       modelPerformer2.setExclusiveClass(modelPerformer1.getExclusiveClass());
/*  725 */       modelPerformer2.setKeyFrom(modelPerformer1.getKeyFrom());
/*  726 */       modelPerformer2.setKeyTo(modelPerformer1.getKeyTo());
/*  727 */       modelPerformer2.setVelFrom(modelPerformer1.getVelFrom());
/*  728 */       modelPerformer2.setVelTo(modelPerformer1.getVelTo());
/*  729 */       modelPerformer2.getOscillators().addAll(modelPerformer1.getOscillators());
/*  730 */       modelPerformer2.getConnectionBlocks().addAll(modelPerformer1
/*  731 */           .getConnectionBlocks());
/*  732 */       arrayOfModelPerformer2[b] = modelPerformer2;
/*      */ 
/*      */       
/*  735 */       List<ModelConnectionBlock> list = modelPerformer2.getConnectionBlocks();
/*      */       
/*  737 */       if (this.cds_control_connections != null) {
/*  738 */         String str = Integer.toString(this.cds_control_number);
/*  739 */         Iterator<ModelConnectionBlock> iterator = list.iterator();
/*  740 */         while (iterator.hasNext()) {
/*  741 */           ModelConnectionBlock modelConnectionBlock = iterator.next();
/*  742 */           ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/*  743 */           boolean bool = false;
/*  744 */           if (arrayOfModelSource != null) {
/*  745 */             for (byte b2 = 0; b2 < arrayOfModelSource.length; b2++) {
/*  746 */               ModelSource modelSource = arrayOfModelSource[b2];
/*  747 */               if ("midi_cc".equals(modelSource.getIdentifier().getObject()) && str
/*  748 */                 .equals(modelSource.getIdentifier().getVariable())) {
/*  749 */                 bool = true;
/*      */               }
/*      */             } 
/*      */           }
/*  753 */           if (bool)
/*  754 */             iterator.remove(); 
/*      */         } 
/*  756 */         for (byte b1 = 0; b1 < this.cds_control_connections.length; b1++) {
/*  757 */           list.add(this.cds_control_connections[b1]);
/*      */         }
/*      */       } 
/*  760 */       if (this.cds_polypressure_connections != null) {
/*  761 */         Iterator<ModelConnectionBlock> iterator = list.iterator();
/*  762 */         while (iterator.hasNext()) {
/*  763 */           ModelConnectionBlock modelConnectionBlock = iterator.next();
/*  764 */           ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/*  765 */           boolean bool = false;
/*  766 */           if (arrayOfModelSource != null) {
/*  767 */             for (byte b2 = 0; b2 < arrayOfModelSource.length; b2++) {
/*  768 */               ModelSource modelSource = arrayOfModelSource[b2];
/*  769 */               if ("midi".equals(modelSource.getIdentifier().getObject()) && "poly_pressure"
/*  770 */                 .equals(modelSource
/*  771 */                   .getIdentifier().getVariable())) {
/*  772 */                 bool = true;
/*      */               }
/*      */             } 
/*      */           }
/*  776 */           if (bool)
/*  777 */             iterator.remove(); 
/*      */         } 
/*  779 */         for (byte b1 = 0; b1 < this.cds_polypressure_connections.length; b1++) {
/*  780 */           list.add(this.cds_polypressure_connections[b1]);
/*      */         }
/*      */       } 
/*      */       
/*  784 */       if (this.cds_channelpressure_connections != null) {
/*  785 */         Iterator<ModelConnectionBlock> iterator = list.iterator();
/*  786 */         while (iterator.hasNext()) {
/*  787 */           ModelConnectionBlock modelConnectionBlock = iterator.next();
/*  788 */           ModelSource[] arrayOfModelSource = modelConnectionBlock.getSources();
/*  789 */           boolean bool = false;
/*  790 */           if (arrayOfModelSource != null) {
/*  791 */             for (byte b2 = 0; b2 < arrayOfModelSource.length; b2++) {
/*  792 */               ModelIdentifier modelIdentifier = arrayOfModelSource[b2].getIdentifier();
/*  793 */               if ("midi".equals(modelIdentifier.getObject()) && "channel_pressure"
/*  794 */                 .equals(modelIdentifier.getVariable())) {
/*  795 */                 bool = true;
/*      */               }
/*      */             } 
/*      */           }
/*  799 */           if (bool)
/*  800 */             iterator.remove(); 
/*      */         } 
/*  802 */         for (byte b1 = 0; b1 < this.cds_channelpressure_connections.length; b1++) {
/*  803 */           list.add(this.cds_channelpressure_connections[b1]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  808 */     this.current_instrument = new SoftInstrument(modelInstrument, arrayOfModelPerformer2);
/*      */   }
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
/*      */   private ModelConnectionBlock[] createModelConnections(ModelIdentifier paramModelIdentifier, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  826 */     ArrayList<ModelConnectionBlock> arrayList = new ArrayList();
/*      */     
/*  828 */     for (byte b = 0; b < paramArrayOfint1.length; b++) {
/*  829 */       int i = paramArrayOfint1[b];
/*  830 */       int j = paramArrayOfint2[b];
/*  831 */       if (i == 0) {
/*  832 */         final double scale = ((j - 64) * 100);
/*  833 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(paramModelIdentifier, false, false, 0), d, new ModelDestination(new ModelIdentifier("osc", "pitch")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  841 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*      */       
/*  844 */       if (i == 1) {
/*  845 */         ModelConnectionBlock modelConnectionBlock; final double scale = (j / 64.0D - 1.0D) * 9600.0D;
/*      */         
/*  847 */         if (d > 0.0D) {
/*  848 */           modelConnectionBlock = new ModelConnectionBlock(new ModelSource(paramModelIdentifier, true, false, 0), -d, new ModelDestination(ModelDestination.DESTINATION_FILTER_FREQ));
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/*  857 */           modelConnectionBlock = new ModelConnectionBlock(new ModelSource(paramModelIdentifier, false, false, 0), d, new ModelDestination(ModelDestination.DESTINATION_FILTER_FREQ));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  866 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*  868 */       if (i == 2) {
/*  869 */         final double scale = j / 64.0D;
/*  870 */         ModelTransform modelTransform = new ModelTransform() {
/*  871 */             double s = scale;
/*      */             public double transform(double param1Double) {
/*  873 */               if (this.s < 1.0D) {
/*  874 */                 param1Double = this.s + param1Double * (1.0D - this.s);
/*  875 */               } else if (this.s > 1.0D) {
/*  876 */                 param1Double = 1.0D + param1Double * (this.s - 1.0D);
/*      */               } else {
/*  878 */                 return 0.0D;
/*  879 */               }  return -(0.4166666666666667D / Math.log(10.0D)) * Math.log(param1Double);
/*      */             }
/*      */           };
/*      */         
/*  883 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(paramModelIdentifier, modelTransform), -960.0D, new ModelDestination(ModelDestination.DESTINATION_GAIN));
/*      */ 
/*      */         
/*  886 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*      */       
/*  889 */       if (i == 3) {
/*  890 */         final double scale = (j / 64.0D - 1.0D) * 9600.0D;
/*  891 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_LFO1, false, true, 0), new ModelSource(paramModelIdentifier, false, false, 0), d, new ModelDestination(ModelDestination.DESTINATION_PITCH));
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
/*  903 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*  905 */       if (i == 4) {
/*  906 */         final double scale = j / 128.0D * 2400.0D;
/*  907 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_LFO1, false, true, 0), new ModelSource(paramModelIdentifier, false, false, 0), d, new ModelDestination(ModelDestination.DESTINATION_FILTER_FREQ));
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
/*  919 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*  921 */       if (i == 5) {
/*  922 */         final double scale = j / 127.0D;
/*      */         
/*  924 */         ModelTransform modelTransform = new ModelTransform() {
/*  925 */             double s = scale;
/*      */             public double transform(double param1Double) {
/*  927 */               return -(0.4166666666666667D / Math.log(10.0D)) * 
/*  928 */                 Math.log(1.0D - param1Double * this.s);
/*      */             }
/*      */           };
/*      */         
/*  932 */         ModelConnectionBlock modelConnectionBlock = new ModelConnectionBlock(new ModelSource(ModelSource.SOURCE_LFO1, false, false, 0), new ModelSource(paramModelIdentifier, modelTransform), -960.0D, new ModelDestination(ModelDestination.DESTINATION_GAIN));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  941 */         arrayList.add(modelConnectionBlock);
/*      */       } 
/*      */     } 
/*      */     
/*  945 */     return arrayList.<ModelConnectionBlock>toArray(new ModelConnectionBlock[arrayList.size()]);
/*      */   }
/*      */   
/*      */   public void mapPolyPressureToDestination(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  949 */     this.current_instrument = null;
/*  950 */     if (paramArrayOfint1.length == 0) {
/*  951 */       this.cds_polypressure_connections = null;
/*      */       return;
/*      */     } 
/*  954 */     this
/*  955 */       .cds_polypressure_connections = createModelConnections(new ModelIdentifier("midi", "poly_pressure"), paramArrayOfint1, paramArrayOfint2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapChannelPressureToDestination(int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  961 */     this.current_instrument = null;
/*  962 */     if (paramArrayOfint1.length == 0) {
/*  963 */       this.cds_channelpressure_connections = null;
/*      */       return;
/*      */     } 
/*  966 */     this
/*  967 */       .cds_channelpressure_connections = createModelConnections(new ModelIdentifier("midi", "channel_pressure"), paramArrayOfint1, paramArrayOfint2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapControlToDestination(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  974 */     if ((paramInt < 1 || paramInt > 31) && (paramInt < 64 || paramInt > 95)) {
/*      */       
/*  976 */       this.cds_control_connections = null;
/*      */       
/*      */       return;
/*      */     } 
/*  980 */     this.current_instrument = null;
/*  981 */     this.cds_control_number = paramInt;
/*  982 */     if (paramArrayOfint1.length == 0) {
/*  983 */       this.cds_control_connections = null;
/*      */       return;
/*      */     } 
/*  986 */     this
/*  987 */       .cds_control_connections = createModelConnections(new ModelIdentifier("midi_cc", 
/*  988 */           Integer.toString(paramInt)), paramArrayOfint1, paramArrayOfint2);
/*      */   }
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
/*      */   public void controlChangePerNote(int paramInt1, int paramInt2, int paramInt3) {
/* 1014 */     if (this.keybasedcontroller_active == null) {
/* 1015 */       this.keybasedcontroller_active = new boolean[128][];
/* 1016 */       this.keybasedcontroller_value = new double[128][];
/*      */     } 
/* 1018 */     if (this.keybasedcontroller_active[paramInt1] == null) {
/* 1019 */       this.keybasedcontroller_active[paramInt1] = new boolean[128];
/* 1020 */       Arrays.fill(this.keybasedcontroller_active[paramInt1], false);
/* 1021 */       this.keybasedcontroller_value[paramInt1] = new double[128];
/* 1022 */       Arrays.fill(this.keybasedcontroller_value[paramInt1], 0.0D);
/*      */     } 
/*      */     
/* 1025 */     if (paramInt3 == -1) {
/* 1026 */       this.keybasedcontroller_active[paramInt1][paramInt2] = false;
/*      */     } else {
/* 1028 */       this.keybasedcontroller_active[paramInt1][paramInt2] = true;
/* 1029 */       this.keybasedcontroller_value[paramInt1][paramInt2] = paramInt3 / 128.0D;
/*      */     } 
/*      */     
/* 1032 */     if (paramInt2 < 120) {
/* 1033 */       for (byte b = 0; b < this.voices.length; b++)
/* 1034 */       { if ((this.voices[b]).active)
/* 1035 */           this.voices[b].controlChange(paramInt2, -1);  } 
/* 1036 */     } else if (paramInt2 == 120) {
/* 1037 */       for (byte b = 0; b < this.voices.length; b++)
/* 1038 */       { if ((this.voices[b]).active)
/* 1039 */           this.voices[b].rpnChange(1, -1);  } 
/* 1040 */     } else if (paramInt2 == 121) {
/* 1041 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1042 */         if ((this.voices[b]).active)
/* 1043 */           this.voices[b].rpnChange(2, -1); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getControlPerNote(int paramInt1, int paramInt2) {
/* 1049 */     if (this.keybasedcontroller_active == null)
/* 1050 */       return -1; 
/* 1051 */     if (this.keybasedcontroller_active[paramInt1] == null)
/* 1052 */       return -1; 
/* 1053 */     if (!this.keybasedcontroller_active[paramInt1][paramInt2])
/* 1054 */       return -1; 
/* 1055 */     return (int)(this.keybasedcontroller_value[paramInt1][paramInt2] * 128.0D);
/*      */   }
/*      */   
/*      */   public void controlChange(int paramInt1, int paramInt2) {
/* 1059 */     paramInt1 = restrict7Bit(paramInt1);
/* 1060 */     paramInt2 = restrict7Bit(paramInt2);
/* 1061 */     if (this.current_mixer != null) {
/* 1062 */       this.current_mixer.controlChange(paramInt1, paramInt2);
/*      */     }
/* 1064 */     synchronized (this.control_mutex) {
/* 1065 */       double d; int i; boolean bool; switch (paramInt1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 5:
/* 1076 */           d = -Math.asin(paramInt2 / 128.0D * 2.0D - 1.0D) / Math.PI + 0.5D;
/* 1077 */           d = Math.pow(100000.0D, d) / 100.0D;
/*      */           
/* 1079 */           d /= 100.0D;
/* 1080 */           d *= 1000.0D;
/* 1081 */           d /= this.synthesizer.getControlRate();
/* 1082 */           this.portamento_time = d;
/*      */           break;
/*      */         case 6:
/*      */         case 38:
/*      */         case 96:
/*      */         case 97:
/* 1088 */           i = 0;
/* 1089 */           if (this.nrpn_control != 16383) {
/* 1090 */             int[] arrayOfInt = this.co_midi_nrpn_nrpn_i.get(Integer.valueOf(this.nrpn_control));
/* 1091 */             if (arrayOfInt != null)
/* 1092 */               i = arrayOfInt[0]; 
/*      */           } 
/* 1094 */           if (this.rpn_control != 16383) {
/* 1095 */             int[] arrayOfInt = this.co_midi_rpn_rpn_i.get(Integer.valueOf(this.rpn_control));
/* 1096 */             if (arrayOfInt != null) {
/* 1097 */               i = arrayOfInt[0];
/*      */             }
/*      */           } 
/* 1100 */           if (paramInt1 == 6) {
/* 1101 */             i = (i & 0x7F) + (paramInt2 << 7);
/* 1102 */           } else if (paramInt1 == 38) {
/* 1103 */             i = (i & 0x3F80) + paramInt2;
/* 1104 */           } else if (paramInt1 == 96 || paramInt1 == 97) {
/* 1105 */             char c = '\001';
/* 1106 */             if (this.rpn_control == 2 || this.rpn_control == 3 || this.rpn_control == 4)
/* 1107 */               c = ''; 
/* 1108 */             if (paramInt1 == 96)
/* 1109 */               i += c; 
/* 1110 */             if (paramInt1 == 97) {
/* 1111 */               i -= c;
/*      */             }
/*      */           } 
/* 1114 */           if (this.nrpn_control != 16383)
/* 1115 */             nrpnChange(this.nrpn_control, i); 
/* 1116 */           if (this.rpn_control != 16383) {
/* 1117 */             rpnChange(this.rpn_control, i);
/*      */           }
/*      */           break;
/*      */         case 64:
/* 1121 */           bool = (paramInt2 >= 64);
/* 1122 */           if (this.sustain != bool) {
/* 1123 */             this.sustain = bool;
/* 1124 */             if (!bool) {
/* 1125 */               for (byte b2 = 0; b2 < this.voices.length; b2++) {
/* 1126 */                 if ((this.voices[b2]).active && (this.voices[b2]).sustain && (this.voices[b2]).channel == this.channel) {
/*      */                   
/* 1128 */                   (this.voices[b2]).sustain = false;
/* 1129 */                   if (!(this.voices[b2]).on) {
/* 1130 */                     (this.voices[b2]).on = true;
/* 1131 */                     this.voices[b2].noteOff(0);
/*      */                   } 
/*      */                 } 
/*      */               }  break;
/*      */             } 
/* 1136 */             for (byte b1 = 0; b1 < this.voices.length; b1++) {
/* 1137 */               if ((this.voices[b1]).active && (this.voices[b1]).channel == this.channel) {
/* 1138 */                 this.voices[b1].redamp();
/*      */               }
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         case 65:
/* 1144 */           this.portamento = (paramInt2 >= 64);
/* 1145 */           this.portamento_lastnote[0] = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1150 */           this.portamento_lastnote_ix = 0;
/*      */           break;
/*      */         case 66:
/* 1153 */           bool = (paramInt2 >= 64);
/* 1154 */           if (bool) {
/* 1155 */             for (byte b1 = 0; b1 < this.voices.length; b1++) {
/* 1156 */               if ((this.voices[b1]).active && (this.voices[b1]).on && (this.voices[b1]).channel == this.channel)
/*      */               {
/* 1158 */                 (this.voices[b1]).sostenuto = true;
/*      */               }
/*      */             } 
/*      */           }
/* 1162 */           if (!bool) {
/* 1163 */             for (byte b1 = 0; b1 < this.voices.length; b1++) {
/* 1164 */               if ((this.voices[b1]).active && (this.voices[b1]).sostenuto && (this.voices[b1]).channel == this.channel) {
/*      */                 
/* 1166 */                 (this.voices[b1]).sostenuto = false;
/* 1167 */                 if (!(this.voices[b1]).on) {
/* 1168 */                   (this.voices[b1]).on = true;
/* 1169 */                   this.voices[b1].noteOff(0);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */           break;
/*      */         case 98:
/* 1176 */           this.nrpn_control = (this.nrpn_control & 0x3F80) + paramInt2;
/* 1177 */           this.rpn_control = 16383;
/*      */           break;
/*      */         case 99:
/* 1180 */           this.nrpn_control = (this.nrpn_control & 0x7F) + (paramInt2 << 7);
/* 1181 */           this.rpn_control = 16383;
/*      */           break;
/*      */         case 100:
/* 1184 */           this.rpn_control = (this.rpn_control & 0x3F80) + paramInt2;
/* 1185 */           this.nrpn_control = 16383;
/*      */           break;
/*      */         case 101:
/* 1188 */           this.rpn_control = (this.rpn_control & 0x7F) + (paramInt2 << 7);
/* 1189 */           this.nrpn_control = 16383;
/*      */           break;
/*      */         case 120:
/* 1192 */           allSoundOff();
/*      */           break;
/*      */         case 121:
/* 1195 */           resetAllControllers((paramInt2 == 127));
/*      */           break;
/*      */         case 122:
/* 1198 */           localControl((paramInt2 >= 64));
/*      */           break;
/*      */         case 123:
/* 1201 */           allNotesOff();
/*      */           break;
/*      */         case 124:
/* 1204 */           setOmni(false);
/*      */           break;
/*      */         case 125:
/* 1207 */           setOmni(true);
/*      */           break;
/*      */         case 126:
/* 1210 */           if (paramInt2 == 1)
/* 1211 */             setMono(true); 
/*      */           break;
/*      */         case 127:
/* 1214 */           setMono(false);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1221 */       this.co_midi_cc_cc[paramInt1][0] = paramInt2 * 0.0078125D;
/*      */       
/* 1223 */       if (paramInt1 == 0) {
/* 1224 */         this.bank = paramInt2 << 7;
/*      */         
/*      */         return;
/*      */       } 
/* 1228 */       if (paramInt1 == 32) {
/* 1229 */         this.bank = (this.bank & 0x3F80) + paramInt2;
/*      */         
/*      */         return;
/*      */       } 
/* 1233 */       this.controller[paramInt1] = paramInt2;
/* 1234 */       if (paramInt1 < 32) {
/* 1235 */         this.controller[paramInt1 + 32] = 0;
/*      */       }
/* 1237 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1238 */         if ((this.voices[b]).active)
/* 1239 */           this.voices[b].controlChange(paramInt1, paramInt2); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getController(int paramInt) {
/* 1245 */     synchronized (this.control_mutex) {
/*      */ 
/*      */       
/* 1248 */       return this.controller[paramInt] & 0x7F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void tuningChange(int paramInt) {
/* 1253 */     tuningChange(0, paramInt);
/*      */   }
/*      */   
/*      */   public void tuningChange(int paramInt1, int paramInt2) {
/* 1257 */     synchronized (this.control_mutex) {
/* 1258 */       this.tuning = this.synthesizer.getTuning(new Patch(paramInt1, paramInt2));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void programChange(int paramInt) {
/* 1263 */     programChange(this.bank, paramInt);
/*      */   }
/*      */   
/*      */   public void programChange(int paramInt1, int paramInt2) {
/* 1267 */     paramInt1 = restrict14Bit(paramInt1);
/* 1268 */     paramInt2 = restrict7Bit(paramInt2);
/* 1269 */     synchronized (this.control_mutex) {
/* 1270 */       this.mainmixer.activity();
/* 1271 */       if (this.bank != paramInt1 || this.program != paramInt2) {
/*      */         
/* 1273 */         this.bank = paramInt1;
/* 1274 */         this.program = paramInt2;
/* 1275 */         this.current_instrument = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getProgram() {
/* 1281 */     synchronized (this.control_mutex) {
/* 1282 */       return this.program;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPitchBend(int paramInt) {
/* 1287 */     paramInt = restrict14Bit(paramInt);
/* 1288 */     if (this.current_mixer != null)
/* 1289 */       this.current_mixer.setPitchBend(paramInt); 
/* 1290 */     synchronized (this.control_mutex) {
/* 1291 */       this.mainmixer.activity();
/* 1292 */       this.co_midi_pitch[0] = paramInt * 6.103515625E-5D;
/* 1293 */       this.pitchbend = paramInt;
/* 1294 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1295 */         if ((this.voices[b]).active)
/* 1296 */           this.voices[b].setPitchBend(paramInt); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public int getPitchBend() {
/* 1301 */     synchronized (this.control_mutex) {
/* 1302 */       return this.pitchbend;
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
/*      */ 
/*      */   
/*      */   public void nrpnChange(int paramInt1, int paramInt2) {
/* 1316 */     if (this.synthesizer.getGeneralMidiMode() == 0) {
/* 1317 */       if (paramInt1 == 136)
/* 1318 */         controlChange(76, paramInt2 >> 7); 
/* 1319 */       if (paramInt1 == 137)
/* 1320 */         controlChange(77, paramInt2 >> 7); 
/* 1321 */       if (paramInt1 == 138)
/* 1322 */         controlChange(78, paramInt2 >> 7); 
/* 1323 */       if (paramInt1 == 160)
/* 1324 */         controlChange(74, paramInt2 >> 7); 
/* 1325 */       if (paramInt1 == 161)
/* 1326 */         controlChange(71, paramInt2 >> 7); 
/* 1327 */       if (paramInt1 == 227)
/* 1328 */         controlChange(73, paramInt2 >> 7); 
/* 1329 */       if (paramInt1 == 228)
/* 1330 */         controlChange(75, paramInt2 >> 7); 
/* 1331 */       if (paramInt1 == 230) {
/* 1332 */         controlChange(72, paramInt2 >> 7);
/*      */       }
/* 1334 */       if (paramInt1 >> 7 == 24)
/* 1335 */         controlChangePerNote(paramInt1 % 128, 120, paramInt2 >> 7); 
/* 1336 */       if (paramInt1 >> 7 == 26)
/* 1337 */         controlChangePerNote(paramInt1 % 128, 7, paramInt2 >> 7); 
/* 1338 */       if (paramInt1 >> 7 == 28)
/* 1339 */         controlChangePerNote(paramInt1 % 128, 10, paramInt2 >> 7); 
/* 1340 */       if (paramInt1 >> 7 == 29)
/* 1341 */         controlChangePerNote(paramInt1 % 128, 91, paramInt2 >> 7); 
/* 1342 */       if (paramInt1 >> 7 == 30) {
/* 1343 */         controlChangePerNote(paramInt1 % 128, 93, paramInt2 >> 7);
/*      */       }
/*      */     } 
/* 1346 */     int[] arrayOfInt = this.co_midi_nrpn_nrpn_i.get(Integer.valueOf(paramInt1));
/* 1347 */     double[] arrayOfDouble = this.co_midi_nrpn_nrpn.get(Integer.valueOf(paramInt1));
/* 1348 */     if (arrayOfInt == null) {
/* 1349 */       arrayOfInt = new int[1];
/* 1350 */       this.co_midi_nrpn_nrpn_i.put(Integer.valueOf(paramInt1), arrayOfInt);
/*      */     } 
/* 1352 */     if (arrayOfDouble == null) {
/* 1353 */       arrayOfDouble = new double[1];
/* 1354 */       this.co_midi_nrpn_nrpn.put(Integer.valueOf(paramInt1), arrayOfDouble);
/*      */     } 
/* 1356 */     arrayOfInt[0] = paramInt2;
/* 1357 */     arrayOfDouble[0] = arrayOfInt[0] * 6.103515625E-5D;
/*      */     
/* 1359 */     for (byte b = 0; b < this.voices.length; b++) {
/* 1360 */       if ((this.voices[b]).active) {
/* 1361 */         this.voices[b].nrpnChange(paramInt1, arrayOfInt[0]);
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
/*      */   
/*      */   public void rpnChange(int paramInt1, int paramInt2) {
/* 1375 */     if (paramInt1 == 3) {
/* 1376 */       this.tuning_program = paramInt2 >> 7 & 0x7F;
/* 1377 */       tuningChange(this.tuning_bank, this.tuning_program);
/*      */     } 
/* 1379 */     if (paramInt1 == 4) {
/* 1380 */       this.tuning_bank = paramInt2 >> 7 & 0x7F;
/*      */     }
/*      */     
/* 1383 */     int[] arrayOfInt = this.co_midi_rpn_rpn_i.get(Integer.valueOf(paramInt1));
/* 1384 */     double[] arrayOfDouble = this.co_midi_rpn_rpn.get(Integer.valueOf(paramInt1));
/* 1385 */     if (arrayOfInt == null) {
/* 1386 */       arrayOfInt = new int[1];
/* 1387 */       this.co_midi_rpn_rpn_i.put(Integer.valueOf(paramInt1), arrayOfInt);
/*      */     } 
/* 1389 */     if (arrayOfDouble == null) {
/* 1390 */       arrayOfDouble = new double[1];
/* 1391 */       this.co_midi_rpn_rpn.put(Integer.valueOf(paramInt1), arrayOfDouble);
/*      */     } 
/* 1393 */     arrayOfInt[0] = paramInt2;
/* 1394 */     arrayOfDouble[0] = arrayOfInt[0] * 6.103515625E-5D;
/*      */     
/* 1396 */     for (byte b = 0; b < this.voices.length; b++) {
/* 1397 */       if ((this.voices[b]).active)
/* 1398 */         this.voices[b].rpnChange(paramInt1, arrayOfInt[0]); 
/*      */     } 
/*      */   }
/*      */   public void resetAllControllers() {
/* 1402 */     resetAllControllers(false);
/*      */   }
/*      */   
/*      */   public void resetAllControllers(boolean paramBoolean) {
/* 1406 */     synchronized (this.control_mutex) {
/* 1407 */       this.mainmixer.activity();
/*      */       byte b;
/* 1409 */       for (b = 0; b < ''; b++) {
/* 1410 */         setPolyPressure(b, 0);
/*      */       }
/* 1412 */       setChannelPressure(0);
/* 1413 */       setPitchBend(8192);
/* 1414 */       for (b = 0; b < ''; b++) {
/* 1415 */         if (!dontResetControls[b]) {
/* 1416 */           controlChange(b, 0);
/*      */         }
/*      */       } 
/* 1419 */       controlChange(71, 64);
/* 1420 */       controlChange(72, 64);
/* 1421 */       controlChange(73, 64);
/* 1422 */       controlChange(74, 64);
/* 1423 */       controlChange(75, 64);
/* 1424 */       controlChange(76, 64);
/* 1425 */       controlChange(77, 64);
/* 1426 */       controlChange(78, 64);
/*      */       
/* 1428 */       controlChange(8, 64);
/* 1429 */       controlChange(11, 127);
/* 1430 */       controlChange(98, 127);
/* 1431 */       controlChange(99, 127);
/* 1432 */       controlChange(100, 127);
/* 1433 */       controlChange(101, 127);
/*      */ 
/*      */       
/* 1436 */       if (paramBoolean) {
/*      */         
/* 1438 */         this.keybasedcontroller_active = (boolean[][])null;
/* 1439 */         this.keybasedcontroller_value = (double[][])null;
/*      */         
/* 1441 */         controlChange(7, 100);
/* 1442 */         controlChange(10, 64);
/* 1443 */         controlChange(91, 40);
/*      */         Iterator<Integer> iterator;
/* 1445 */         for (iterator = this.co_midi_rpn_rpn.keySet().iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/*      */           
/* 1447 */           if (i != 3 && i != 4)
/* 1448 */             rpnChange(i, 0);  }
/*      */         
/* 1450 */         for (iterator = this.co_midi_nrpn_nrpn.keySet().iterator(); iterator.hasNext(); ) { int i = ((Integer)iterator.next()).intValue();
/* 1451 */           nrpnChange(i, 0); }
/* 1452 */          rpnChange(0, 256);
/* 1453 */         rpnChange(1, 8192);
/* 1454 */         rpnChange(2, 8192);
/* 1455 */         rpnChange(5, 64);
/*      */         
/* 1457 */         this.tuning_bank = 0;
/* 1458 */         this.tuning_program = 0;
/* 1459 */         this.tuning = new SoftTuning();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void allNotesOff() {
/* 1467 */     if (this.current_mixer != null)
/* 1468 */       this.current_mixer.allNotesOff(); 
/* 1469 */     synchronized (this.control_mutex) {
/* 1470 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1471 */         if ((this.voices[b]).on && (this.voices[b]).channel == this.channel && !(this.voices[b]).releaseTriggered)
/*      */         {
/* 1473 */           this.voices[b].noteOff(0); } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void allSoundOff() {
/* 1479 */     if (this.current_mixer != null)
/* 1480 */       this.current_mixer.allSoundOff(); 
/* 1481 */     synchronized (this.control_mutex) {
/* 1482 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1483 */         if ((this.voices[b]).on && (this.voices[b]).channel == this.channel)
/* 1484 */           this.voices[b].soundOff(); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean localControl(boolean paramBoolean) {
/* 1489 */     return false;
/*      */   }
/*      */   
/*      */   public void setMono(boolean paramBoolean) {
/* 1493 */     if (this.current_mixer != null)
/* 1494 */       this.current_mixer.setMono(paramBoolean); 
/* 1495 */     synchronized (this.control_mutex) {
/* 1496 */       allNotesOff();
/* 1497 */       this.mono = paramBoolean;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getMono() {
/* 1502 */     synchronized (this.control_mutex) {
/* 1503 */       return this.mono;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setOmni(boolean paramBoolean) {
/* 1508 */     if (this.current_mixer != null)
/* 1509 */       this.current_mixer.setOmni(paramBoolean); 
/* 1510 */     allNotesOff();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getOmni() {
/* 1515 */     return false;
/*      */   }
/*      */   
/*      */   public void setMute(boolean paramBoolean) {
/* 1519 */     if (this.current_mixer != null)
/* 1520 */       this.current_mixer.setMute(paramBoolean); 
/* 1521 */     synchronized (this.control_mutex) {
/* 1522 */       this.mute = paramBoolean;
/* 1523 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1524 */         if ((this.voices[b]).active && (this.voices[b]).channel == this.channel)
/* 1525 */           this.voices[b].setMute(paramBoolean); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean getMute() {
/* 1530 */     synchronized (this.control_mutex) {
/* 1531 */       return this.mute;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSolo(boolean paramBoolean) {
/* 1536 */     if (this.current_mixer != null) {
/* 1537 */       this.current_mixer.setSolo(paramBoolean);
/*      */     }
/* 1539 */     synchronized (this.control_mutex) {
/* 1540 */       this.solo = paramBoolean;
/*      */       
/* 1542 */       boolean bool = false;
/* 1543 */       for (SoftChannel softChannel : this.synthesizer.channels) {
/* 1544 */         if (softChannel.solo) {
/* 1545 */           bool = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1550 */       if (!bool) {
/* 1551 */         for (SoftChannel softChannel : this.synthesizer.channels) {
/* 1552 */           softChannel.setSoloMute(false);
/*      */         }
/*      */         return;
/*      */       } 
/* 1556 */       for (SoftChannel softChannel : this.synthesizer.channels) {
/* 1557 */         softChannel.setSoloMute(!softChannel.solo);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSoloMute(boolean paramBoolean) {
/* 1564 */     synchronized (this.control_mutex) {
/* 1565 */       if (this.solomute == paramBoolean)
/*      */         return; 
/* 1567 */       this.solomute = paramBoolean;
/* 1568 */       for (byte b = 0; b < this.voices.length; b++) {
/* 1569 */         if ((this.voices[b]).active && (this.voices[b]).channel == this.channel)
/* 1570 */           this.voices[b].setSoloMute(this.solomute); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   public boolean getSolo() {
/* 1575 */     synchronized (this.control_mutex) {
/* 1576 */       return this.solo;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */