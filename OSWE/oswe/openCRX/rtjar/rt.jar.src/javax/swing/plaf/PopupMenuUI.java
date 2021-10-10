/*    */ package javax.swing.plaf;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JPopupMenu;
/*    */ import javax.swing.Popup;
/*    */ import javax.swing.PopupFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PopupMenuUI
/*    */   extends ComponentUI
/*    */ {
/*    */   public boolean isPopupTrigger(MouseEvent paramMouseEvent) {
/* 45 */     return paramMouseEvent.isPopupTrigger();
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
/*    */ 
/*    */   
/*    */   public Popup getPopup(JPopupMenu paramJPopupMenu, int paramInt1, int paramInt2) {
/* 59 */     PopupFactory popupFactory = PopupFactory.getSharedInstance();
/*    */     
/* 61 */     return popupFactory.getPopup(paramJPopupMenu.getInvoker(), paramJPopupMenu, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/PopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */