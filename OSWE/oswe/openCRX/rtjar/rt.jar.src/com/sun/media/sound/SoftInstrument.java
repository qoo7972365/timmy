/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.Instrument;
/*    */ import javax.sound.midi.MidiChannel;
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
/*    */ public final class SoftInstrument
/*    */   extends Instrument
/*    */ {
/*    */   private SoftPerformer[] performers;
/*    */   private ModelPerformer[] modelperformers;
/*    */   private final Object data;
/*    */   private final ModelInstrument ins;
/*    */   
/*    */   public SoftInstrument(ModelInstrument paramModelInstrument) {
/* 43 */     super(paramModelInstrument.getSoundbank(), paramModelInstrument.getPatch(), paramModelInstrument.getName(), paramModelInstrument
/* 44 */         .getDataClass());
/* 45 */     this.data = paramModelInstrument.getData();
/* 46 */     this.ins = paramModelInstrument;
/* 47 */     initPerformers(paramModelInstrument.getPerformers());
/*    */   }
/*    */ 
/*    */   
/*    */   public SoftInstrument(ModelInstrument paramModelInstrument, ModelPerformer[] paramArrayOfModelPerformer) {
/* 52 */     super(paramModelInstrument.getSoundbank(), paramModelInstrument.getPatch(), paramModelInstrument.getName(), paramModelInstrument
/* 53 */         .getDataClass());
/* 54 */     this.data = paramModelInstrument.getData();
/* 55 */     this.ins = paramModelInstrument;
/* 56 */     initPerformers(paramArrayOfModelPerformer);
/*    */   }
/*    */   
/*    */   private void initPerformers(ModelPerformer[] paramArrayOfModelPerformer) {
/* 60 */     this.modelperformers = paramArrayOfModelPerformer;
/* 61 */     this.performers = new SoftPerformer[paramArrayOfModelPerformer.length];
/* 62 */     for (byte b = 0; b < paramArrayOfModelPerformer.length; b++) {
/* 63 */       this.performers[b] = new SoftPerformer(paramArrayOfModelPerformer[b]);
/*    */     }
/*    */   }
/*    */   
/*    */   public ModelDirector getDirector(MidiChannel paramMidiChannel, ModelDirectedPlayer paramModelDirectedPlayer) {
/* 68 */     return this.ins.getDirector(this.modelperformers, paramMidiChannel, paramModelDirectedPlayer);
/*    */   }
/*    */   
/*    */   public ModelInstrument getSourceInstrument() {
/* 72 */     return this.ins;
/*    */   }
/*    */   
/*    */   public Object getData() {
/* 76 */     return this.data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SoftPerformer getPerformer(int paramInt) {
/* 85 */     return this.performers[paramInt];
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftInstrument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */