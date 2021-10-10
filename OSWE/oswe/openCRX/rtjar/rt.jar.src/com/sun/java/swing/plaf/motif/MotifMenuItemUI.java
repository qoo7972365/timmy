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
/*     */ import javax.swing.plaf.basic.BasicMenuItemUI;
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
/*     */ 
/*     */ public class MotifMenuItemUI
/*     */   extends BasicMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  51 */     return new MotifMenuItemUI();
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  55 */     super.installListeners();
/*  56 */     this.changeListener = createChangeListener(this.menuItem);
/*  57 */     this.menuItem.addChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/*  61 */     super.uninstallListeners();
/*  62 */     this.menuItem.removeChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener(JComponent paramJComponent) {
/*  66 */     return new ChangeHandler();
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/*  70 */     return new MouseInputHandler();
/*     */   }
/*     */   
/*     */   protected class ChangeHandler
/*     */     implements ChangeListener {
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  76 */       JMenuItem jMenuItem = (JMenuItem)param1ChangeEvent.getSource();
/*  77 */       LookAndFeel.installProperty(jMenuItem, "borderPainted", 
/*  78 */           Boolean.valueOf((jMenuItem.isArmed() || jMenuItem.isSelected())));
/*     */     } }
/*     */   
/*     */   protected class MouseInputHandler implements MouseInputListener {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  85 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  86 */       menuSelectionManager.setSelectedPath(MotifMenuItemUI.this.getPath());
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  90 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  91 */       JMenuItem jMenuItem = (JMenuItem)param1MouseEvent.getComponent();
/*  92 */       Point point = param1MouseEvent.getPoint();
/*  93 */       if (point.x >= 0 && point.x < jMenuItem.getWidth() && point.y >= 0 && point.y < jMenuItem
/*  94 */         .getHeight()) {
/*  95 */         menuSelectionManager.clearSelectedPath();
/*  96 */         jMenuItem.doClick(0);
/*     */       } else {
/*  98 */         menuSelectionManager.processMouseEvent(param1MouseEvent);
/*     */       } 
/*     */     }
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 104 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */