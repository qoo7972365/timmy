/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.MenuElement;
/*    */ import javax.swing.MenuSelectionManager;
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
/*    */ public class BasicRadioButtonMenuItemUI
/*    */   extends BasicMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 43 */     return new BasicRadioButtonMenuItemUI();
/*    */   }
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 47 */     return "RadioButtonMenuItem";
/*    */   }
/*    */   
/*    */   public void processMouseEvent(JMenuItem paramJMenuItem, MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {
/* 51 */     Point point = paramMouseEvent.getPoint();
/* 52 */     if (point.x >= 0 && point.x < paramJMenuItem.getWidth() && point.y >= 0 && point.y < paramJMenuItem
/* 53 */       .getHeight()) {
/* 54 */       if (paramMouseEvent.getID() == 502)
/* 55 */       { paramMenuSelectionManager.clearSelectedPath();
/* 56 */         paramJMenuItem.doClick(0);
/* 57 */         paramJMenuItem.setArmed(false); }
/*    */       else
/* 59 */       { paramMenuSelectionManager.setSelectedPath(paramArrayOfMenuElement); } 
/* 60 */     } else if (paramJMenuItem.getModel().isArmed()) {
/* 61 */       MenuElement[] arrayOfMenuElement = new MenuElement[paramArrayOfMenuElement.length - 1]; byte b;
/*    */       int i;
/* 63 */       for (b = 0, i = paramArrayOfMenuElement.length - 1; b < i; b++)
/* 64 */         arrayOfMenuElement[b] = paramArrayOfMenuElement[b]; 
/* 65 */       paramMenuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicRadioButtonMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */