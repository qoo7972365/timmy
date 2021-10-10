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
/*    */ public class GrowableRectArray
/*    */   extends GrowableIntArray
/*    */ {
/*    */   private static final int RECT_SIZE = 4;
/*    */   
/*    */   public GrowableRectArray(int paramInt) {
/* 38 */     super(4, paramInt);
/*    */   }
/*    */   
/*    */   public final void pushRectValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 42 */     int i = this.size;
/* 43 */     this.size += 4;
/*    */     
/* 45 */     if (this.size >= this.array.length) {
/* 46 */       growArray();
/*    */     }
/*    */     
/* 49 */     this.array[i] = paramInt1;
/* 50 */     this.array[i + 1] = paramInt2;
/* 51 */     this.array[i + 2] = paramInt3;
/* 52 */     this.array[i + 3] = paramInt4;
/*    */   }
/*    */   
/*    */   public final void setX(int paramInt1, int paramInt2) {
/* 56 */     this.array[getCellIndex(paramInt1)] = paramInt2;
/*    */   }
/*    */   
/*    */   public final void setY(int paramInt1, int paramInt2) {
/* 60 */     this.array[getCellIndex(paramInt1) + 1] = paramInt2;
/*    */   }
/*    */   
/*    */   public final void setWidth(int paramInt1, int paramInt2) {
/* 64 */     this.array[getCellIndex(paramInt1) + 2] = paramInt2;
/*    */   }
/*    */   
/*    */   public final void setHeight(int paramInt1, int paramInt2) {
/* 68 */     this.array[getCellIndex(paramInt1) + 3] = paramInt2;
/*    */   }
/*    */   
/*    */   public final int getX(int paramInt) {
/* 72 */     return this.array[getCellIndex(paramInt)];
/*    */   }
/*    */   
/*    */   public final int getY(int paramInt) {
/* 76 */     return this.array[getCellIndex(paramInt) + 1];
/*    */   }
/*    */   
/*    */   public final int getWidth(int paramInt) {
/* 80 */     return this.array[getCellIndex(paramInt) + 2];
/*    */   }
/*    */   
/*    */   public final int getHeight(int paramInt) {
/* 84 */     return this.array[getCellIndex(paramInt) + 3];
/*    */   }
/*    */   
/*    */   public final void translateRects(int paramInt1, int paramInt2) {
/* 88 */     for (byte b = 0; b < getSize(); b++) {
/* 89 */       setX(b, getX(b) + paramInt1);
/* 90 */       setY(b, getY(b) + paramInt2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/GrowableRectArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */