/*    */ package java.awt;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class GridBagLayoutInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4899416460737170217L;
/*    */   int width;
/*    */   int height;
/*    */   int startx;
/*    */   int starty;
/*    */   int[] minWidth;
/*    */   int[] minHeight;
/*    */   double[] weightX;
/*    */   double[] weightY;
/*    */   boolean hasBaseline;
/*    */   short[] baselineType;
/*    */   int[] maxAscent;
/*    */   int[] maxDescent;
/*    */   
/*    */   GridBagLayoutInfo(int paramInt1, int paramInt2) {
/* 68 */     this.width = paramInt1;
/* 69 */     this.height = paramInt2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean hasConstantDescent(int paramInt) {
/* 77 */     return 
/* 78 */       ((this.baselineType[paramInt] & 1 << Component.BaselineResizeBehavior.CONSTANT_DESCENT.ordinal()) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean hasBaseline(int paramInt) {
/* 85 */     return (this.hasBaseline && this.baselineType[paramInt] != 0);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GridBagLayoutInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */