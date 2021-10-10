/*    */ package com.sun.media.sound;
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
/*    */ public final class SF2LayerRegion
/*    */   extends SF2Region
/*    */ {
/*    */   SF2Sample sample;
/*    */   
/*    */   public SF2Sample getSample() {
/* 37 */     return this.sample;
/*    */   }
/*    */   
/*    */   public void setSample(SF2Sample paramSF2Sample) {
/* 41 */     this.sample = paramSF2Sample;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SF2LayerRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */