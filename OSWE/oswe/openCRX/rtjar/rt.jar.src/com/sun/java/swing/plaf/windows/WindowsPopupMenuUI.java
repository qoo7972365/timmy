/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Window;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.Popup;
/*     */ import javax.swing.PopupFactory;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicPopupMenuUI;
/*     */ import sun.swing.StringUIClientPropertyKey;
/*     */ import sun.swing.SwingUtilities2;
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
/*     */ public class WindowsPopupMenuUI
/*     */   extends BasicPopupMenuUI
/*     */ {
/*  61 */   static MnemonicListener mnemonicListener = null;
/*  62 */   static final Object GUTTER_OFFSET_KEY = new StringUIClientPropertyKey("GUTTER_OFFSET_KEY");
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  66 */     return new WindowsPopupMenuUI();
/*     */   }
/*     */   
/*     */   public void installListeners() {
/*  70 */     super.installListeners();
/*  71 */     if (!UIManager.getBoolean("Button.showMnemonics") && mnemonicListener == null) {
/*     */ 
/*     */       
/*  74 */       mnemonicListener = new MnemonicListener();
/*  75 */       MenuSelectionManager.defaultManager()
/*  76 */         .addChangeListener(mnemonicListener);
/*     */     } 
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
/*     */   public Popup getPopup(JPopupMenu paramJPopupMenu, int paramInt1, int paramInt2) {
/*  91 */     PopupFactory popupFactory = PopupFactory.getSharedInstance();
/*  92 */     return popupFactory.getPopup(paramJPopupMenu.getInvoker(), paramJPopupMenu, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static class MnemonicListener implements ChangeListener {
/*  96 */     JRootPane repaintRoot = null;
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  99 */       MenuSelectionManager menuSelectionManager = (MenuSelectionManager)param1ChangeEvent.getSource();
/* 100 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 101 */       if (arrayOfMenuElement.length == 0) {
/* 102 */         if (!WindowsLookAndFeel.isMnemonicHidden()) {
/*     */           
/* 104 */           WindowsLookAndFeel.setMnemonicHidden(true);
/* 105 */           if (this.repaintRoot != null) {
/*     */             
/* 107 */             Window window = SwingUtilities.getWindowAncestor(this.repaintRoot);
/* 108 */             WindowsGraphicsUtils.repaintMnemonicsInWindow(window);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 112 */         Component component = (Component)arrayOfMenuElement[0];
/* 113 */         if (component instanceof JPopupMenu) component = ((JPopupMenu)component).getInvoker(); 
/* 114 */         this.repaintRoot = SwingUtilities.getRootPane(component);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getTextOffset(JComponent paramJComponent) {
/* 126 */     int i = -1;
/*     */     
/* 128 */     Object object = paramJComponent.getClientProperty(SwingUtilities2.BASICMENUITEMUI_MAX_TEXT_OFFSET);
/* 129 */     if (object instanceof Integer) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       i = ((Integer)object).intValue();
/* 135 */       int j = 0;
/* 136 */       Component component = paramJComponent.getComponent(0);
/* 137 */       if (component != null) {
/* 138 */         j = component.getX();
/*     */       }
/* 140 */       i += j;
/*     */     } 
/* 142 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getSpanBeforeGutter() {
/* 151 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getSpanAfterGutter() {
/* 160 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getGutterWidth() {
/* 169 */     int i = 2;
/* 170 */     XPStyle xPStyle = XPStyle.getXP();
/* 171 */     if (xPStyle != null) {
/* 172 */       XPStyle.Skin skin = xPStyle.getSkin(null, TMSchema.Part.MP_POPUPGUTTER);
/* 173 */       i = skin.getWidth();
/*     */     } 
/* 175 */     return i;
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
/*     */   private static boolean isLeftToRight(JComponent paramJComponent) {
/* 187 */     boolean bool = true;
/* 188 */     for (int i = paramJComponent.getComponentCount() - 1; i >= 0 && bool; i--)
/*     */     {
/* 190 */       bool = paramJComponent.getComponent(i).getComponentOrientation().isLeftToRight();
/*     */     }
/* 192 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 197 */     XPStyle xPStyle = XPStyle.getXP();
/* 198 */     if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/* 199 */       XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.MP_POPUPBACKGROUND);
/* 200 */       skin.paintSkin(paramGraphics, 0, 0, paramJComponent.getWidth(), paramJComponent.getHeight(), TMSchema.State.NORMAL);
/* 201 */       int i = getTextOffset(paramJComponent);
/* 202 */       if (i >= 0 && 
/*     */         
/* 204 */         isLeftToRight(paramJComponent)) {
/* 205 */         skin = xPStyle.getSkin(paramJComponent, TMSchema.Part.MP_POPUPGUTTER);
/* 206 */         int j = getGutterWidth();
/*     */         
/* 208 */         int k = i - getSpanAfterGutter() - j;
/* 209 */         paramJComponent.putClientProperty(GUTTER_OFFSET_KEY, 
/* 210 */             Integer.valueOf(k));
/* 211 */         Insets insets = paramJComponent.getInsets();
/* 212 */         skin.paintSkin(paramGraphics, k, insets.top, j, paramJComponent
/* 213 */             .getHeight() - insets.bottom - insets.top, TMSchema.State.NORMAL);
/*     */       
/*     */       }
/* 216 */       else if (paramJComponent.getClientProperty(GUTTER_OFFSET_KEY) != null) {
/* 217 */         paramJComponent.putClientProperty(GUTTER_OFFSET_KEY, (Object)null);
/*     */       } 
/*     */     } else {
/*     */       
/* 221 */       super.paint(paramGraphics, paramJComponent);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsPopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */