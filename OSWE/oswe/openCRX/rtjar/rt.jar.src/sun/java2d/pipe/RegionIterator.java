/*    */ package sun.java2d.pipe;
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
/*    */ public class RegionIterator
/*    */ {
/*    */   Region region;
/*    */   int curIndex;
/*    */   int numXbands;
/*    */   
/*    */   RegionIterator(Region paramRegion) {
/* 40 */     this.region = paramRegion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RegionIterator createCopy() {
/* 49 */     RegionIterator regionIterator = new RegionIterator(this.region);
/* 50 */     regionIterator.curIndex = this.curIndex;
/* 51 */     regionIterator.numXbands = this.numXbands;
/* 52 */     return regionIterator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copyStateFrom(RegionIterator paramRegionIterator) {
/* 61 */     if (this.region != paramRegionIterator.region) {
/* 62 */       throw new InternalError("region mismatch");
/*    */     }
/* 64 */     this.curIndex = paramRegionIterator.curIndex;
/* 65 */     this.numXbands = paramRegionIterator.numXbands;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean nextYRange(int[] paramArrayOfint) {
/* 75 */     this.curIndex += this.numXbands * 2;
/* 76 */     this.numXbands = 0;
/* 77 */     if (this.curIndex >= this.region.endIndex) {
/* 78 */       return false;
/*    */     }
/* 80 */     paramArrayOfint[1] = this.region.bands[this.curIndex++];
/* 81 */     paramArrayOfint[3] = this.region.bands[this.curIndex++];
/* 82 */     this.numXbands = this.region.bands[this.curIndex++];
/* 83 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean nextXBand(int[] paramArrayOfint) {
/* 93 */     if (this.numXbands <= 0) {
/* 94 */       return false;
/*    */     }
/* 96 */     this.numXbands--;
/* 97 */     paramArrayOfint[0] = this.region.bands[this.curIndex++];
/* 98 */     paramArrayOfint[2] = this.region.bands[this.curIndex++];
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/RegionIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */