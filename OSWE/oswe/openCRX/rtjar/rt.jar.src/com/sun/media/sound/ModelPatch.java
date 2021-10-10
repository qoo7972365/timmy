/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.Patch;
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
/*    */ public final class ModelPatch
/*    */   extends Patch
/*    */ {
/*    */   private boolean percussion = false;
/*    */   
/*    */   public ModelPatch(int paramInt1, int paramInt2) {
/* 41 */     super(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public ModelPatch(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 45 */     super(paramInt1, paramInt2);
/* 46 */     this.percussion = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean isPercussion() {
/* 50 */     return this.percussion;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelPatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */