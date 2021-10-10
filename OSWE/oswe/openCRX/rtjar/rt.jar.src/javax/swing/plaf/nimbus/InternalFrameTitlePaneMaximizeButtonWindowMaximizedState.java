/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JInternalFrame;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InternalFrameTitlePaneMaximizeButtonWindowMaximizedState
/*    */   extends State
/*    */ {
/*    */   InternalFrameTitlePaneMaximizeButtonWindowMaximizedState() {
/* 33 */     super("WindowMaximized");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     Container container = paramJComponent;
/* 39 */     while (container.getParent() != null && 
/* 40 */       !(container instanceof JInternalFrame))
/*    */     {
/*    */       
/* 43 */       container = container.getParent();
/*    */     }
/* 45 */     if (container instanceof JInternalFrame) {
/* 46 */       return ((JInternalFrame)container).isMaximum();
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InternalFrameTitlePaneMaximizeButtonWindowMaximizedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */