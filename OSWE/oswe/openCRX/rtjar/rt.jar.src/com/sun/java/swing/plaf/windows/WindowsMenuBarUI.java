/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.HierarchyEvent;
/*     */ import java.awt.event.HierarchyListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuBarUI;
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
/*     */ public class WindowsMenuBarUI
/*     */   extends BasicMenuBarUI
/*     */ {
/*  58 */   private WindowListener windowListener = null;
/*  59 */   private HierarchyListener hierarchyListener = null;
/*  60 */   private Window window = null;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new WindowsMenuBarUI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  68 */     uninstallWindowListener();
/*  69 */     if (this.hierarchyListener != null) {
/*  70 */       this.menuBar.removeHierarchyListener(this.hierarchyListener);
/*  71 */       this.hierarchyListener = null;
/*     */     } 
/*  73 */     super.uninstallListeners();
/*     */   }
/*     */   private void installWindowListener() {
/*  76 */     if (this.windowListener == null) {
/*  77 */       Container container = this.menuBar.getTopLevelAncestor();
/*  78 */       if (container instanceof Window) {
/*  79 */         this.window = (Window)container;
/*  80 */         this.windowListener = new WindowAdapter()
/*     */           {
/*     */             public void windowActivated(WindowEvent param1WindowEvent) {
/*  83 */               WindowsMenuBarUI.this.menuBar.repaint();
/*     */             }
/*     */             
/*     */             public void windowDeactivated(WindowEvent param1WindowEvent) {
/*  87 */               WindowsMenuBarUI.this.menuBar.repaint();
/*     */             }
/*     */           };
/*  90 */         ((Window)container).addWindowListener(this.windowListener);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void uninstallWindowListener() {
/*  95 */     if (this.windowListener != null && this.window != null) {
/*  96 */       this.window.removeWindowListener(this.windowListener);
/*     */     }
/*  98 */     this.window = null;
/*  99 */     this.windowListener = null;
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 103 */     if (WindowsLookAndFeel.isOnVista()) {
/* 104 */       installWindowListener();
/* 105 */       this.hierarchyListener = new HierarchyListener()
/*     */         {
/*     */           public void hierarchyChanged(HierarchyEvent param1HierarchyEvent) {
/* 108 */             if ((param1HierarchyEvent.getChangeFlags() & 0x2L) != 0L)
/*     */             {
/* 110 */               if (WindowsMenuBarUI.this.menuBar.isDisplayable()) {
/* 111 */                 WindowsMenuBarUI.this.installWindowListener();
/*     */               } else {
/* 113 */                 WindowsMenuBarUI.this.uninstallWindowListener();
/*     */               } 
/*     */             }
/*     */           }
/*     */         };
/* 118 */       this.menuBar.addHierarchyListener(this.hierarchyListener);
/*     */     } 
/* 120 */     super.installListeners();
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 124 */     super.installKeyboardActions();
/* 125 */     ActionMap actionMap = SwingUtilities.getUIActionMap(this.menuBar);
/* 126 */     if (actionMap == null) {
/* 127 */       actionMap = new ActionMapUIResource();
/* 128 */       SwingUtilities.replaceUIActionMap(this.menuBar, actionMap);
/*     */     } 
/* 130 */     actionMap.put("takeFocus", new TakeFocus());
/*     */   }
/*     */   
/*     */   private static class TakeFocus
/*     */     extends AbstractAction
/*     */   {
/*     */     private TakeFocus() {}
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 139 */       JMenuBar jMenuBar = (JMenuBar)param1ActionEvent.getSource();
/* 140 */       JMenu jMenu = jMenuBar.getMenu(0);
/* 141 */       if (jMenu != null) {
/*     */         
/* 143 */         MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 144 */         MenuElement[] arrayOfMenuElement = new MenuElement[2];
/* 145 */         arrayOfMenuElement[0] = jMenuBar;
/* 146 */         arrayOfMenuElement[1] = jMenu;
/* 147 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*     */ 
/*     */         
/* 150 */         WindowsLookAndFeel.setMnemonicHidden(false);
/* 151 */         WindowsLookAndFeel.repaintRootPane(jMenuBar);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 158 */     XPStyle xPStyle = XPStyle.getXP();
/* 159 */     if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/*     */       
/* 161 */       XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.MP_BARBACKGROUND);
/* 162 */       int i = paramJComponent.getWidth();
/* 163 */       int j = paramJComponent.getHeight();
/* 164 */       TMSchema.State state = isActive(paramJComponent) ? TMSchema.State.ACTIVE : TMSchema.State.INACTIVE;
/* 165 */       skin.paintSkin(paramGraphics, 0, 0, i, j, state);
/*     */     } else {
/* 167 */       super.paint(paramGraphics, paramJComponent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isActive(JComponent paramJComponent) {
/* 177 */     JRootPane jRootPane = paramJComponent.getRootPane();
/* 178 */     if (jRootPane != null) {
/* 179 */       Container container = jRootPane.getParent();
/* 180 */       if (container instanceof Window) {
/* 181 */         return ((Window)container).isActive();
/*     */       }
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsMenuBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */