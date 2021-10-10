/*    */ package javax.swing.plaf;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.beans.ConstructorProperties;
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
/*    */ public class ColorUIResource
/*    */   extends Color
/*    */   implements UIResource
/*    */ {
/*    */   @ConstructorProperties({"red", "green", "blue"})
/*    */   public ColorUIResource(int paramInt1, int paramInt2, int paramInt3) {
/* 52 */     super(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   public ColorUIResource(int paramInt) {
/* 56 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public ColorUIResource(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 60 */     super(paramFloat1, paramFloat2, paramFloat3);
/*    */   }
/*    */   
/*    */   public ColorUIResource(Color paramColor) {
/* 64 */     super(paramColor.getRGB(), ((paramColor.getRGB() & 0xFF000000) != -16777216));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/ColorUIResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */