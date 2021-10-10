/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifMenuUI
/*     */   extends BasicMenuUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  49 */     return new MotifMenuUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChangeListener createChangeListener(JComponent paramJComponent) {
/*  66 */     return new MotifChangeHandler((JMenu)paramJComponent, this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean popupIsOpen(JMenu paramJMenu, MenuElement[] paramArrayOfMenuElement) {
/*  71 */     JPopupMenu jPopupMenu = paramJMenu.getPopupMenu();
/*     */     
/*  73 */     for (int i = paramArrayOfMenuElement.length - 1; i >= 0; i--) {
/*  74 */       if (paramArrayOfMenuElement[i].getComponent() == jPopupMenu)
/*  75 */         return true; 
/*     */     } 
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/*  81 */     return new MouseInputHandler();
/*     */   }
/*     */   
/*     */   public class MotifChangeHandler extends BasicMenuUI.ChangeHandler {
/*     */     public MotifChangeHandler(JMenu param1JMenu, MotifMenuUI param1MotifMenuUI1) {
/*  86 */       super(param1JMenu, param1MotifMenuUI1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  91 */       JMenuItem jMenuItem = (JMenuItem)param1ChangeEvent.getSource();
/*  92 */       if (jMenuItem.isArmed() || jMenuItem.isSelected()) {
/*  93 */         jMenuItem.setBorderPainted(true);
/*     */       } else {
/*     */         
/*  96 */         jMenuItem.setBorderPainted(false);
/*     */       } 
/*     */       
/*  99 */       super.stateChanged(param1ChangeEvent);
/*     */     } }
/*     */   
/*     */   protected class MouseInputHandler implements MouseInputListener {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 106 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 107 */       JMenu jMenu = (JMenu)param1MouseEvent.getComponent();
/* 108 */       if (jMenu.isEnabled()) {
/* 109 */         if (jMenu.isTopLevelMenu()) {
/* 110 */           if (jMenu.isSelected()) {
/* 111 */             menuSelectionManager.clearSelectedPath();
/*     */           } else {
/* 113 */             Container container = jMenu.getParent();
/* 114 */             if (container != null && container instanceof javax.swing.JMenuBar) {
/* 115 */               MenuElement[] arrayOfMenuElement1 = new MenuElement[2];
/* 116 */               arrayOfMenuElement1[0] = (MenuElement)container;
/* 117 */               arrayOfMenuElement1[1] = jMenu;
/* 118 */               menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 123 */         MenuElement[] arrayOfMenuElement = MotifMenuUI.this.getPath();
/* 124 */         if (arrayOfMenuElement.length > 0) {
/* 125 */           MenuElement[] arrayOfMenuElement1 = new MenuElement[arrayOfMenuElement.length + 1];
/* 126 */           System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, arrayOfMenuElement.length);
/* 127 */           arrayOfMenuElement1[arrayOfMenuElement.length] = jMenu.getPopupMenu();
/* 128 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 135 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 136 */       JMenuItem jMenuItem = (JMenuItem)param1MouseEvent.getComponent();
/* 137 */       Point point = param1MouseEvent.getPoint();
/* 138 */       if (point.x < 0 || point.x >= jMenuItem.getWidth() || point.y < 0 || point.y >= jMenuItem
/* 139 */         .getHeight())
/* 140 */         menuSelectionManager.processMouseEvent(param1MouseEvent); 
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 146 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */