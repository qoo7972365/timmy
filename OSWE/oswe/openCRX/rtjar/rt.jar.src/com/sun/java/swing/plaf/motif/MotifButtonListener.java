/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.plaf.basic.BasicButtonListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MotifButtonListener
/*    */   extends BasicButtonListener
/*    */ {
/*    */   public MotifButtonListener(AbstractButton paramAbstractButton) {
/* 43 */     super(paramAbstractButton);
/*    */   }
/*    */   
/*    */   protected void checkOpacity(AbstractButton paramAbstractButton) {
/* 47 */     paramAbstractButton.setOpaque(false);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifButtonListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */