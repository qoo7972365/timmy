/*    */ package sun.java2d.jules;
/*    */ 
/*    */ import sun.java2d.xr.GrowableIntArray;
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
/*    */ class TileTrapContainer
/*    */ {
/*    */   int tileAlpha;
/*    */   GrowableIntArray traps;
/*    */   
/*    */   public TileTrapContainer(GrowableIntArray paramGrowableIntArray) {
/* 35 */     this.traps = paramGrowableIntArray;
/*    */   }
/*    */   
/*    */   public void setTileAlpha(int paramInt) {
/* 39 */     this.tileAlpha = paramInt;
/*    */   }
/*    */   
/*    */   public int getTileAlpha() {
/* 43 */     return this.tileAlpha;
/*    */   }
/*    */   
/*    */   public GrowableIntArray getTraps() {
/* 47 */     return this.traps;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/TileTrapContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */