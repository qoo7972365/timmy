/*    */ package javax.swing.plaf.synth;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
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
/*    */ public class SynthCheckBoxUI
/*    */   extends SynthRadioButtonUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 52 */     return new SynthCheckBoxUI();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 60 */     return "CheckBox.";
/*    */   }
/*    */ 
/*    */   
/*    */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 65 */     paramSynthContext.getPainter().paintCheckBoxBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 66 */         .getWidth(), paramJComponent.getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 75 */     paramSynthContext.getPainter().paintCheckBoxBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthCheckBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */