/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ 
/*    */ public final class ModelStandardDirector
/*    */   implements ModelDirector
/*    */ {
/*    */   private final ModelPerformer[] performers;
/*    */   private final ModelDirectedPlayer player;
/*    */   private boolean noteOnUsed = false;
/*    */   private boolean noteOffUsed = false;
/*    */   
/*    */   public ModelStandardDirector(ModelPerformer[] paramArrayOfModelPerformer, ModelDirectedPlayer paramModelDirectedPlayer) {
/* 44 */     this.performers = Arrays.<ModelPerformer>copyOf(paramArrayOfModelPerformer, paramArrayOfModelPerformer.length);
/* 45 */     this.player = paramModelDirectedPlayer;
/* 46 */     for (ModelPerformer modelPerformer : this.performers) {
/* 47 */       if (modelPerformer.isReleaseTriggered()) {
/* 48 */         this.noteOffUsed = true;
/*    */       } else {
/* 50 */         this.noteOnUsed = true;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public void noteOff(int paramInt1, int paramInt2) {
/* 59 */     if (!this.noteOffUsed)
/*    */       return; 
/* 61 */     for (byte b = 0; b < this.performers.length; b++) {
/* 62 */       ModelPerformer modelPerformer = this.performers[b];
/* 63 */       if (modelPerformer.getKeyFrom() <= paramInt1 && modelPerformer.getKeyTo() >= paramInt1 && 
/* 64 */         modelPerformer.getVelFrom() <= paramInt2 && modelPerformer.getVelTo() >= paramInt2 && 
/* 65 */         modelPerformer.isReleaseTriggered()) {
/* 66 */         this.player.play(b, null);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void noteOn(int paramInt1, int paramInt2) {
/* 74 */     if (!this.noteOnUsed)
/*    */       return; 
/* 76 */     for (byte b = 0; b < this.performers.length; b++) {
/* 77 */       ModelPerformer modelPerformer = this.performers[b];
/* 78 */       if (modelPerformer.getKeyFrom() <= paramInt1 && modelPerformer.getKeyTo() >= paramInt1 && 
/* 79 */         modelPerformer.getVelFrom() <= paramInt2 && modelPerformer.getVelTo() >= paramInt2 && 
/* 80 */         !modelPerformer.isReleaseTriggered())
/* 81 */         this.player.play(b, null); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelStandardDirector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */