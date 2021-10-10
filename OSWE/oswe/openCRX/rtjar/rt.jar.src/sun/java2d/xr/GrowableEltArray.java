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
/*    */ public class GrowableEltArray
/*    */   extends GrowableIntArray
/*    */ {
/*    */   private static final int ELT_SIZE = 4;
/*    */   GrowableIntArray glyphs;
/*    */   
/*    */   public GrowableEltArray(int paramInt) {
/* 40 */     super(4, paramInt);
/* 41 */     this.glyphs = new GrowableIntArray(1, paramInt * 8);
/*    */   }
/*    */   
/*    */   public final int getCharCnt(int paramInt) {
/* 45 */     return this.array[getCellIndex(paramInt) + 0];
/*    */   }
/*    */   
/*    */   public final void setCharCnt(int paramInt1, int paramInt2) {
/* 49 */     this.array[getCellIndex(paramInt1) + 0] = paramInt2;
/*    */   }
/*    */   
/*    */   public final int getXOff(int paramInt) {
/* 53 */     return this.array[getCellIndex(paramInt) + 1];
/*    */   }
/*    */   
/*    */   public final void setXOff(int paramInt1, int paramInt2) {
/* 57 */     this.array[getCellIndex(paramInt1) + 1] = paramInt2;
/*    */   }
/*    */   
/*    */   public final int getYOff(int paramInt) {
/* 61 */     return this.array[getCellIndex(paramInt) + 2];
/*    */   }
/*    */   
/*    */   public final void setYOff(int paramInt1, int paramInt2) {
/* 65 */     this.array[getCellIndex(paramInt1) + 2] = paramInt2;
/*    */   }
/*    */   
/*    */   public final int getGlyphSet(int paramInt) {
/* 69 */     return this.array[getCellIndex(paramInt) + 3];
/*    */   }
/*    */   
/*    */   public final void setGlyphSet(int paramInt1, int paramInt2) {
/* 73 */     this.array[getCellIndex(paramInt1) + 3] = paramInt2;
/*    */   }
/*    */   
/*    */   public GrowableIntArray getGlyphs() {
/* 77 */     return this.glyphs;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 81 */     this.glyphs.clear();
/* 82 */     super.clear();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/GrowableEltArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */