/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.sound.midi.Instrument;
/*     */ import javax.sound.midi.MidiChannel;
/*     */ import javax.sound.midi.Patch;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.midi.SoundbankResource;
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
/*     */ public abstract class ModelAbstractOscillator
/*     */   implements ModelOscillator, ModelOscillatorStream, Soundbank
/*     */ {
/*  43 */   protected float pitch = 6000.0F;
/*     */   
/*     */   protected float samplerate;
/*     */   
/*     */   protected MidiChannel channel;
/*     */   protected VoiceStatus voice;
/*     */   protected int noteNumber;
/*     */   protected int velocity;
/*     */   protected boolean on = false;
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public void close() throws IOException {}
/*     */   
/*     */   public void noteOff(int paramInt) {
/*  58 */     this.on = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void noteOn(MidiChannel paramMidiChannel, VoiceStatus paramVoiceStatus, int paramInt1, int paramInt2) {
/*  63 */     this.channel = paramMidiChannel;
/*  64 */     this.voice = paramVoiceStatus;
/*  65 */     this.noteNumber = paramInt1;
/*  66 */     this.velocity = paramInt2;
/*  67 */     this.on = true;
/*     */   }
/*     */   
/*     */   public int read(float[][] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException {
/*  71 */     return -1;
/*     */   }
/*     */   
/*     */   public MidiChannel getChannel() {
/*  75 */     return this.channel;
/*     */   }
/*     */   
/*     */   public VoiceStatus getVoice() {
/*  79 */     return this.voice;
/*     */   }
/*     */   
/*     */   public int getNoteNumber() {
/*  83 */     return this.noteNumber;
/*     */   }
/*     */   
/*     */   public int getVelocity() {
/*  87 */     return this.velocity;
/*     */   }
/*     */   
/*     */   public boolean isOn() {
/*  91 */     return this.on;
/*     */   }
/*     */   
/*     */   public void setPitch(float paramFloat) {
/*  95 */     this.pitch = paramFloat;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  99 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public void setSampleRate(float paramFloat) {
/* 103 */     this.samplerate = paramFloat;
/*     */   }
/*     */   
/*     */   public float getSampleRate() {
/* 107 */     return this.samplerate;
/*     */   }
/*     */   
/*     */   public float getAttenuation() {
/* 111 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public int getChannels() {
/* 115 */     return 1;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 119 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   public Patch getPatch() {
/* 123 */     return new Patch(0, 0);
/*     */   }
/*     */   
/*     */   public ModelOscillatorStream open(float paramFloat) {
/*     */     ModelAbstractOscillator modelAbstractOscillator;
/*     */     try {
/* 129 */       modelAbstractOscillator = (ModelAbstractOscillator)getClass().newInstance();
/* 130 */     } catch (InstantiationException instantiationException) {
/* 131 */       throw new IllegalArgumentException(instantiationException);
/* 132 */     } catch (IllegalAccessException illegalAccessException) {
/* 133 */       throw new IllegalArgumentException(illegalAccessException);
/*     */     } 
/* 135 */     modelAbstractOscillator.setSampleRate(paramFloat);
/* 136 */     modelAbstractOscillator.init();
/* 137 */     return modelAbstractOscillator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPerformer getPerformer() {
/* 142 */     ModelPerformer modelPerformer = new ModelPerformer();
/* 143 */     modelPerformer.getOscillators().add(this);
/* 144 */     return modelPerformer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelInstrument getInstrument() {
/* 150 */     SimpleInstrument simpleInstrument = new SimpleInstrument();
/* 151 */     simpleInstrument.setName(getName());
/* 152 */     simpleInstrument.add(getPerformer());
/* 153 */     simpleInstrument.setPatch(getPatch());
/* 154 */     return simpleInstrument;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Soundbank getSoundBank() {
/* 160 */     SimpleSoundbank simpleSoundbank = new SimpleSoundbank();
/* 161 */     simpleSoundbank.addInstrument(getInstrument());
/* 162 */     return simpleSoundbank;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 166 */     return getName();
/*     */   }
/*     */   
/*     */   public Instrument getInstrument(Patch paramPatch) {
/* 170 */     ModelInstrument modelInstrument = getInstrument();
/* 171 */     Patch patch = modelInstrument.getPatch();
/* 172 */     if (patch.getBank() != paramPatch.getBank())
/* 173 */       return null; 
/* 174 */     if (patch.getProgram() != paramPatch.getProgram())
/* 175 */       return null; 
/* 176 */     if (patch instanceof ModelPatch && paramPatch instanceof ModelPatch && (
/* 177 */       (ModelPatch)patch).isPercussion() != ((ModelPatch)paramPatch)
/* 178 */       .isPercussion()) {
/* 179 */       return null;
/*     */     }
/*     */     
/* 182 */     return modelInstrument;
/*     */   }
/*     */   
/*     */   public Instrument[] getInstruments() {
/* 186 */     return new Instrument[] { getInstrument() };
/*     */   }
/*     */   
/*     */   public SoundbankResource[] getResources() {
/* 190 */     return new SoundbankResource[0];
/*     */   }
/*     */   
/*     */   public String getVendor() {
/* 194 */     return null;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 198 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelAbstractOscillator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */