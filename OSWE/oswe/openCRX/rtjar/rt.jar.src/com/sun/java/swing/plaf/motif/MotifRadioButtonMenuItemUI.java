/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifRadioButtonMenuItemUI
/*     */   extends BasicRadioButtonMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     return new MotifRadioButtonMenuItemUI();
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  60 */     super.installListeners();
/*  61 */     this.changeListener = createChangeListener(this.menuItem);
/*  62 */     this.menuItem.addChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/*  66 */     super.uninstallListeners();
/*  67 */     this.menuItem.removeChangeListener(this.changeListener);
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener(JComponent paramJComponent) {
/*  71 */     return new ChangeHandler();
/*     */   }
/*     */   
/*     */   protected class ChangeHandler implements ChangeListener, Serializable {
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  76 */       JMenuItem jMenuItem = (JMenuItem)param1ChangeEvent.getSource();
/*  77 */       LookAndFeel.installProperty(jMenuItem, "borderPainted", Boolean.valueOf(jMenuItem.isArmed()));
/*     */     }
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/*  82 */     return new MouseInputHandler();
/*     */   }
/*     */   
/*     */   protected class MouseInputHandler implements MouseInputListener {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  89 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  90 */       menuSelectionManager.setSelectedPath(MotifRadioButtonMenuItemUI.this.getPath());
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  94 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  95 */       JMenuItem jMenuItem = (JMenuItem)param1MouseEvent.getComponent();
/*  96 */       Point point = param1MouseEvent.getPoint();
/*  97 */       if (point.x >= 0 && point.x < jMenuItem.getWidth() && point.y >= 0 && point.y < jMenuItem
/*  98 */         .getHeight()) {
/*  99 */         menuSelectionManager.clearSelectedPath();
/* 100 */         jMenuItem.doClick(0);
/*     */       } else {
/* 102 */         menuSelectionManager.processMouseEvent(param1MouseEvent);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 108 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifRadioButtonMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */