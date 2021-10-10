/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicTextPaneUI;
/*    */ import javax.swing.text.Caret;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MotifTextPaneUI
/*    */   extends BasicTextPaneUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 53 */     return new MotifTextPaneUI();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Caret createCaret() {
/* 65 */     return MotifTextUI.createCaret();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifTextPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */