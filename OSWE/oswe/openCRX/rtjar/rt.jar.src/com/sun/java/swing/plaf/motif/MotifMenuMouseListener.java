/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.MenuSelectionManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MotifMenuMouseListener
/*    */   extends MouseAdapter
/*    */ {
/*    */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 37 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */   public void mouseReleased(MouseEvent paramMouseEvent) {
/* 40 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */   public void mouseEntered(MouseEvent paramMouseEvent) {
/* 43 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 46 */     MenuSelectionManager.defaultManager().processMouseEvent(paramMouseEvent);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifMenuMouseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */