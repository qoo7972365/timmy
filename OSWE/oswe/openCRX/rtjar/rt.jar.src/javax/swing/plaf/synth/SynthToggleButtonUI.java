/*    */ package javax.swing.plaf.synth;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.AbstractButton;
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
/*    */ public class SynthToggleButtonUI
/*    */   extends SynthButtonUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 52 */     return new SynthToggleButtonUI();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 60 */     return "ToggleButton.";
/*    */   }
/*    */ 
/*    */   
/*    */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 65 */     if (((AbstractButton)paramJComponent).isContentAreaFilled()) {
/* 66 */       boolean bool1 = false, bool2 = false; int i = paramJComponent.getWidth(), j = paramJComponent.getHeight();
/* 67 */       SynthPainter synthPainter = paramSynthContext.getPainter();
/* 68 */       synthPainter.paintToggleButtonBackground(paramSynthContext, paramGraphics, bool1, bool2, i, j);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 78 */     paramSynthContext.getPainter().paintToggleButtonBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthToggleButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */