/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.MidiChannel;
/*    */ import javax.sound.midi.Patch;
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ModelMappedInstrument
/*    */   extends ModelInstrument
/*    */ {
/*    */   private final ModelInstrument ins;
/*    */   
/*    */   public ModelMappedInstrument(ModelInstrument paramModelInstrument, Patch paramPatch) {
/* 41 */     super(paramModelInstrument.getSoundbank(), paramPatch, paramModelInstrument.getName(), paramModelInstrument.getDataClass());
/* 42 */     this.ins = paramModelInstrument;
/*    */   }
/*    */   
/*    */   public Object getData() {
/* 46 */     return this.ins.getData();
/*    */   }
/*    */   
/*    */   public ModelPerformer[] getPerformers() {
/* 50 */     return this.ins.getPerformers();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelDirector getDirector(ModelPerformer[] paramArrayOfModelPerformer, MidiChannel paramMidiChannel, ModelDirectedPlayer paramModelDirectedPlayer) {
/* 55 */     return this.ins.getDirector(paramArrayOfModelPerformer, paramMidiChannel, paramModelDirectedPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelChannelMixer getChannelMixer(MidiChannel paramMidiChannel, AudioFormat paramAudioFormat) {
/* 60 */     return this.ins.getChannelMixer(paramMidiChannel, paramAudioFormat);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelMappedInstrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */