/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
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
/*     */ 
/*     */ 
/*     */ public class MotifCheckBoxMenuItemUI
/*     */   extends BasicCheckBoxMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  50 */     return new MotifCheckBoxMenuItemUI();
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  54 */     super.installListeners();
/*  55 */     this.changeListener = createChangeListener(this.menuItem);
/*  56 */     this.menuItem.addChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/*  60 */     super.uninstallListeners();
/*  61 */     this.menuItem.removeChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener(JComponent paramJComponent) {
/*  65 */     return new ChangeHandler();
/*     */   }
/*     */   
/*     */   protected class ChangeHandler implements ChangeListener {
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  70 */       JMenuItem jMenuItem = (JMenuItem)param1ChangeEvent.getSource();
/*  71 */       LookAndFeel.installProperty(jMenuItem, "borderPainted", Boolean.valueOf(jMenuItem.isArmed()));
/*     */     }
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/*  76 */     return new MouseInputHandler();
/*     */   }
/*     */   
/*     */   protected class MouseInputHandler implements MouseInputListener {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  83 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  84 */       menuSelectionManager.setSelectedPath(MotifCheckBoxMenuItemUI.this.getPath());
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  88 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  89 */       JMenuItem jMenuItem = (JMenuItem)param1MouseEvent.getComponent();
/*  90 */       Point point = param1MouseEvent.getPoint();
/*  91 */       if (point.x >= 0 && point.x < jMenuItem.getWidth() && point.y >= 0 && point.y < jMenuItem
/*  92 */         .getHeight()) {
/*  93 */         menuSelectionManager.clearSelectedPath();
/*  94 */         jMenuItem.doClick(0);
/*     */       } else {
/*  96 */         menuSelectionManager.processMouseEvent(param1MouseEvent);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 102 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifCheckBoxMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */