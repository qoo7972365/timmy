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
/*    */ 
/*    */ public class SynthCheckBoxMenuItemUI
/*    */   extends SynthMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 53 */     return new SynthCheckBoxMenuItemUI();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 61 */     return "CheckBoxMenuItem";
/*    */   }
/*    */ 
/*    */   
/*    */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 66 */     paramSynthContext.getPainter().paintCheckBoxMenuItemBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 67 */         .getWidth(), paramJComponent.getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 76 */     paramSynthContext.getPainter().paintCheckBoxMenuItemBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthCheckBoxMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */