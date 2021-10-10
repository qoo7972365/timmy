/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.KeyEventPostProcessor;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.KeyEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRootPaneUI;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.SunToolkit;
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
/*     */ public class WindowsRootPaneUI
/*     */   extends BasicRootPaneUI
/*     */ {
/*  73 */   private static final WindowsRootPaneUI windowsRootPaneUI = new WindowsRootPaneUI();
/*  74 */   static final AltProcessor altProcessor = new AltProcessor();
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  77 */     return windowsRootPaneUI;
/*     */   }
/*     */   
/*     */   static class AltProcessor implements KeyEventPostProcessor {
/*     */     static boolean altKeyPressed = false;
/*     */     static boolean menuCanceledOnPress = false;
/*  83 */     static JRootPane root = null;
/*  84 */     static Window winAncestor = null;
/*     */ 
/*     */     
/*     */     void altPressed(KeyEvent param1KeyEvent) {
/*  88 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  89 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  90 */       if (arrayOfMenuElement.length > 0 && !(arrayOfMenuElement[0] instanceof javax.swing.plaf.basic.ComboPopup)) {
/*  91 */         menuSelectionManager.clearSelectedPath();
/*  92 */         menuCanceledOnPress = true;
/*  93 */         param1KeyEvent.consume();
/*  94 */       } else if (arrayOfMenuElement.length > 0) {
/*  95 */         menuCanceledOnPress = false;
/*  96 */         WindowsLookAndFeel.setMnemonicHidden(false);
/*  97 */         WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/*  98 */         param1KeyEvent.consume();
/*     */       } else {
/* 100 */         menuCanceledOnPress = false;
/* 101 */         WindowsLookAndFeel.setMnemonicHidden(false);
/* 102 */         WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/* 103 */         JMenuBar jMenuBar = (root != null) ? root.getJMenuBar() : null;
/* 104 */         if (jMenuBar == null && winAncestor instanceof JFrame) {
/* 105 */           jMenuBar = ((JFrame)winAncestor).getJMenuBar();
/*     */         }
/* 107 */         JMenu jMenu = (jMenuBar != null) ? jMenuBar.getMenu(0) : null;
/* 108 */         if (jMenu != null) {
/* 109 */           param1KeyEvent.consume();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     void altReleased(KeyEvent param1KeyEvent) {
/* 115 */       if (menuCanceledOnPress) {
/* 116 */         WindowsLookAndFeel.setMnemonicHidden(true);
/* 117 */         WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 122 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 123 */       if ((menuSelectionManager.getSelectedPath()).length == 0) {
/*     */ 
/*     */         
/* 126 */         JMenuBar jMenuBar = (root != null) ? root.getJMenuBar() : null;
/* 127 */         if (jMenuBar == null && winAncestor instanceof JFrame) {
/* 128 */           jMenuBar = ((JFrame)winAncestor).getJMenuBar();
/*     */         }
/* 130 */         JMenu jMenu = (jMenuBar != null) ? jMenuBar.getMenu(0) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 140 */         boolean bool = false;
/* 141 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 142 */         if (toolkit instanceof SunToolkit) {
/*     */           
/* 144 */           Component component = AWTAccessor.getKeyEventAccessor().getOriginalSource(param1KeyEvent);
/*     */           
/* 146 */           bool = (SunToolkit.getContainingWindow(component) != winAncestor || param1KeyEvent.getWhen() <= ((SunToolkit)toolkit).getWindowDeactivationTime(winAncestor)) ? true : false;
/*     */         } 
/*     */         
/* 149 */         if (jMenu != null && !bool) {
/* 150 */           MenuElement[] arrayOfMenuElement = new MenuElement[2];
/* 151 */           arrayOfMenuElement[0] = jMenuBar;
/* 152 */           arrayOfMenuElement[1] = jMenu;
/* 153 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/* 154 */         } else if (!WindowsLookAndFeel.isMnemonicHidden()) {
/* 155 */           WindowsLookAndFeel.setMnemonicHidden(true);
/* 156 */           WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/*     */         }
/*     */       
/* 159 */       } else if (menuSelectionManager.getSelectedPath()[0] instanceof javax.swing.plaf.basic.ComboPopup) {
/* 160 */         WindowsLookAndFeel.setMnemonicHidden(true);
/* 161 */         WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean postProcessKeyEvent(KeyEvent param1KeyEvent) {
/* 168 */       if (param1KeyEvent.isConsumed() && param1KeyEvent.getKeyCode() != 18) {
/*     */ 
/*     */ 
/*     */         
/* 172 */         altKeyPressed = false;
/* 173 */         return false;
/*     */       } 
/* 175 */       if (param1KeyEvent.getKeyCode() == 18) {
/* 176 */         root = SwingUtilities.getRootPane(param1KeyEvent.getComponent());
/*     */         
/* 178 */         winAncestor = (root == null) ? null : SwingUtilities.getWindowAncestor(root);
/*     */         
/* 180 */         if (param1KeyEvent.getID() == 401) {
/* 181 */           if (!altKeyPressed) {
/* 182 */             altPressed(param1KeyEvent);
/*     */           }
/* 184 */           altKeyPressed = true;
/* 185 */           return true;
/* 186 */         }  if (param1KeyEvent.getID() == 402) {
/* 187 */           if (altKeyPressed) {
/* 188 */             altReleased(param1KeyEvent);
/*     */           } else {
/*     */             
/* 191 */             MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 192 */             MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 193 */             if (arrayOfMenuElement.length <= 0) {
/* 194 */               WindowsLookAndFeel.setMnemonicHidden(true);
/* 195 */               WindowsGraphicsUtils.repaintMnemonicsInWindow(winAncestor);
/*     */             } 
/*     */           } 
/* 198 */           altKeyPressed = false;
/*     */         } 
/* 200 */         root = null;
/* 201 */         winAncestor = null;
/*     */       } else {
/* 203 */         altKeyPressed = false;
/*     */       } 
/* 205 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsRootPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */