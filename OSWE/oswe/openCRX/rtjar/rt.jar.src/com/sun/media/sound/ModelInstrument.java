/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.Instrument;
/*     */ import javax.sound.midi.MidiChannel;
/*     */ import javax.sound.midi.Patch;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.sampled.AudioFormat;
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
/*     */ public abstract class ModelInstrument
/*     */   extends Instrument
/*     */ {
/*     */   protected ModelInstrument(Soundbank paramSoundbank, Patch paramPatch, String paramString, Class<?> paramClass) {
/*  54 */     super(paramSoundbank, paramPatch, paramString, paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelDirector getDirector(ModelPerformer[] paramArrayOfModelPerformer, MidiChannel paramMidiChannel, ModelDirectedPlayer paramModelDirectedPlayer) {
/*  59 */     return new ModelStandardIndexedDirector(paramArrayOfModelPerformer, paramModelDirectedPlayer);
/*     */   }
/*     */   
/*     */   public ModelPerformer[] getPerformers() {
/*  63 */     return new ModelPerformer[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelChannelMixer getChannelMixer(MidiChannel paramMidiChannel, AudioFormat paramAudioFormat) {
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Patch getPatchAlias() {
/*  73 */     Patch patch = getPatch();
/*  74 */     int i = patch.getProgram();
/*  75 */     int j = patch.getBank();
/*  76 */     if (j != 0)
/*  77 */       return patch; 
/*  78 */     boolean bool = false;
/*  79 */     if (getPatch() instanceof ModelPatch)
/*  80 */       bool = ((ModelPatch)getPatch()).isPercussion(); 
/*  81 */     if (bool) {
/*  82 */       return new Patch(15360, i);
/*     */     }
/*  84 */     return new Patch(15488, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] getKeys() {
/*  91 */     String[] arrayOfString = new String[128];
/*  92 */     for (ModelPerformer modelPerformer : getPerformers()) {
/*  93 */       for (int i = modelPerformer.getKeyFrom(); i <= modelPerformer.getKeyTo(); i++) {
/*  94 */         if (i >= 0 && i < 128 && arrayOfString[i] == null) {
/*  95 */           String str = modelPerformer.getName();
/*  96 */           if (str == null)
/*  97 */             str = "untitled"; 
/*  98 */           arrayOfString[i] = str;
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean[] getChannels() {
/* 108 */     boolean bool = false;
/* 109 */     if (getPatch() instanceof ModelPatch) {
/* 110 */       bool = ((ModelPatch)getPatch()).isPercussion();
/*     */     }
/*     */     
/* 113 */     if (bool) {
/* 114 */       boolean[] arrayOfBoolean1 = new boolean[16];
/* 115 */       for (byte b1 = 0; b1 < arrayOfBoolean1.length; b1++)
/* 116 */         arrayOfBoolean1[b1] = false; 
/* 117 */       arrayOfBoolean1[9] = true;
/* 118 */       return arrayOfBoolean1;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     int i = getPatch().getBank();
/* 123 */     if (i >> 7 == 120 || i >> 7 == 121) {
/* 124 */       boolean[] arrayOfBoolean1 = new boolean[16];
/* 125 */       for (byte b1 = 0; b1 < arrayOfBoolean1.length; b1++)
/* 126 */         arrayOfBoolean1[b1] = true; 
/* 127 */       return arrayOfBoolean1;
/*     */     } 
/*     */     
/* 130 */     boolean[] arrayOfBoolean = new boolean[16];
/* 131 */     for (byte b = 0; b < arrayOfBoolean.length; b++)
/* 132 */       arrayOfBoolean[b] = true; 
/* 133 */     arrayOfBoolean[9] = false;
/* 134 */     return arrayOfBoolean;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelInstrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */