/*    */ package sun.java2d.xr;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaskTile
/*    */ {
/* 40 */   GrowableRectArray rects = new GrowableRectArray(128);
/* 41 */   DirtyRegion dirtyArea = new DirtyRegion();
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculateDirtyAreas() {
/* 46 */     for (byte b = 0; b < this.rects.getSize(); b++) {
/* 47 */       int i = this.rects.getX(b);
/* 48 */       int j = this.rects.getY(b);
/* 49 */       this.dirtyArea.growDirtyRegion(i, j, i + this.rects
/* 50 */           .getWidth(b), j + this.rects
/* 51 */           .getHeight(b));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void reset() {
/* 56 */     this.rects.clear();
/* 57 */     this.dirtyArea.clear();
/*    */   }
/*    */   
/*    */   public void translate(int paramInt1, int paramInt2) {
/* 61 */     if (this.rects.getSize() > 0) {
/* 62 */       this.dirtyArea.translate(paramInt1, paramInt2);
/*    */     }
/* 64 */     this.rects.translateRects(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public GrowableRectArray getRects() {
/* 68 */     return this.rects;
/*    */   }
/*    */   
/*    */   public DirtyRegion getDirtyArea() {
/* 72 */     return this.dirtyArea;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/MaskTile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */