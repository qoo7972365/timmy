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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicCheckBoxMenuItemUI
/*    */   extends BasicMenuItemUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 46 */     return new BasicCheckBoxMenuItemUI();
/*    */   }
/*    */   
/*    */   protected String getPropertyPrefix() {
/* 50 */     return "CheckBoxMenuItem";
/*    */   }
/*    */   
/*    */   public void processMouseEvent(JMenuItem paramJMenuItem, MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {
/* 54 */     Point point = paramMouseEvent.getPoint();
/* 55 */     if (point.x >= 0 && point.x < paramJMenuItem.getWidth() && point.y >= 0 && point.y < paramJMenuItem
/* 56 */       .getHeight()) {
/* 57 */       if (paramMouseEvent.getID() == 502)
/* 58 */       { paramMenuSelectionManager.clearSelectedPath();
/* 59 */         paramJMenuItem.doClick(0); }
/*    */       else
/* 61 */       { paramMenuSelectionManager.setSelectedPath(paramArrayOfMenuElement); } 
/* 62 */     } else if (paramJMenuItem.getModel().isArmed()) {
/* 63 */       MenuElement[] arrayOfMenuElement = new MenuElement[paramArrayOfMenuElement.length - 1]; byte b;
/*    */       int i;
/* 65 */       for (b = 0, i = paramArrayOfMenuElement.length - 1; b < i; b++)
/* 66 */         arrayOfMenuElement[b] = paramArrayOfMenuElement[b]; 
/* 67 */       paramMenuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicCheckBoxMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */