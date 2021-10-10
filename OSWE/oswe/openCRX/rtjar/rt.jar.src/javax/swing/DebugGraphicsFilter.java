/*    */ package javax.swing;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.image.RGBImageFilter;
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
/*    */ class DebugGraphicsFilter
/*    */   extends RGBImageFilter
/*    */ {
/*    */   Color color;
/*    */   
/*    */   DebugGraphicsFilter(Color paramColor) {
/* 39 */     this.canFilterIndexColorModel = true;
/* 40 */     this.color = paramColor;
/*    */   }
/*    */   
/*    */   public int filterRGB(int paramInt1, int paramInt2, int paramInt3) {
/* 44 */     return this.color.getRGB() | paramInt3 & 0xFF000000;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DebugGraphicsFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */