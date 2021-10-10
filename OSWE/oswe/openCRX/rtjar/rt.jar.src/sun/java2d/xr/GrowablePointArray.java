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
/*    */ public class GrowablePointArray
/*    */   extends GrowableIntArray
/*    */ {
/*    */   private static final int POINT_SIZE = 2;
/*    */   
/*    */   public GrowablePointArray(int paramInt) {
/* 40 */     super(2, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public final int getX(int paramInt) {
/* 45 */     return this.array[getCellIndex(paramInt)];
/*    */   }
/*    */ 
/*    */   
/*    */   public final int getY(int paramInt) {
/* 50 */     return this.array[getCellIndex(paramInt) + 1];
/*    */   }
/*    */ 
/*    */   
/*    */   public final void setX(int paramInt1, int paramInt2) {
/* 55 */     this.array[getCellIndex(paramInt1)] = paramInt2;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void setY(int paramInt1, int paramInt2) {
/* 60 */     this.array[getCellIndex(paramInt1) + 1] = paramInt2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/GrowablePointArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */