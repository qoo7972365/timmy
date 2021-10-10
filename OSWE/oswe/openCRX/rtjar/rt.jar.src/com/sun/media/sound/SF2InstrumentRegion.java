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
/*    */ public final class SF2InstrumentRegion
/*    */   extends SF2Region
/*    */ {
/*    */   SF2Layer layer;
/*    */   
/*    */   public SF2Layer getLayer() {
/* 37 */     return this.layer;
/*    */   }
/*    */   
/*    */   public void setLayer(SF2Layer paramSF2Layer) {
/* 41 */     this.layer = paramSF2Layer;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SF2InstrumentRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */