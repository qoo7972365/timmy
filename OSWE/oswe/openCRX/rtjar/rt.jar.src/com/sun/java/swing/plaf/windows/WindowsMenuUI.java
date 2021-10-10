/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
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
/*     */ public class WindowsMenuUI
/*     */   extends BasicMenuUI
/*     */ {
/*     */   protected Integer menuBarHeight;
/*     */   protected boolean hotTrackingOn;
/*     */   
/*  53 */   final WindowsMenuItemUIAccessor accessor = new WindowsMenuItemUIAccessor()
/*     */     {
/*     */       public JMenuItem getMenuItem()
/*     */       {
/*  57 */         return WindowsMenuUI.this.menuItem;
/*     */       }
/*     */       
/*     */       public TMSchema.State getState(JMenuItem param1JMenuItem) {
/*  61 */         TMSchema.State state = param1JMenuItem.isEnabled() ? TMSchema.State.NORMAL : TMSchema.State.DISABLED;
/*     */         
/*  63 */         ButtonModel buttonModel = param1JMenuItem.getModel();
/*  64 */         if (buttonModel.isArmed() || buttonModel.isSelected()) {
/*  65 */           state = param1JMenuItem.isEnabled() ? TMSchema.State.PUSHED : TMSchema.State.DISABLEDPUSHED;
/*     */         }
/*  67 */         else if (buttonModel.isRollover() && ((JMenu)param1JMenuItem)
/*  68 */           .isTopLevelMenu()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  73 */           TMSchema.State state1 = state;
/*  74 */           state = param1JMenuItem.isEnabled() ? TMSchema.State.HOT : TMSchema.State.DISABLEDHOT;
/*     */ 
/*     */           
/*  77 */           for (MenuElement menuElement : ((JMenuBar)param1JMenuItem.getParent()).getSubElements()) {
/*  78 */             if (((JMenuItem)menuElement).isSelected()) {
/*  79 */               state = state1;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  86 */         if (!((JMenu)param1JMenuItem).isTopLevelMenu()) {
/*  87 */           if (state == TMSchema.State.PUSHED) {
/*  88 */             state = TMSchema.State.HOT;
/*  89 */           } else if (state == TMSchema.State.DISABLEDPUSHED) {
/*  90 */             state = TMSchema.State.DISABLEDHOT;
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         if (((JMenu)param1JMenuItem).isTopLevelMenu() && WindowsMenuItemUI.isVistaPainting() && 
/*  98 */           !WindowsMenuBarUI.isActive(param1JMenuItem)) {
/*  99 */           state = TMSchema.State.DISABLED;
/*     */         }
/*     */         
/* 102 */         return state;
/*     */       }
/*     */       
/*     */       public TMSchema.Part getPart(JMenuItem param1JMenuItem) {
/* 106 */         return ((JMenu)param1JMenuItem).isTopLevelMenu() ? TMSchema.Part.MP_BARITEM : TMSchema.Part.MP_POPUPITEM;
/*     */       }
/*     */     };
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 111 */     return new WindowsMenuUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 115 */     super.installDefaults();
/* 116 */     if (!WindowsLookAndFeel.isClassicWindows()) {
/* 117 */       this.menuItem.setRolloverEnabled(true);
/*     */     }
/*     */     
/* 120 */     this.menuBarHeight = Integer.valueOf(UIManager.getInt("MenuBar.height"));
/*     */     
/* 122 */     Object object = UIManager.get("MenuBar.rolloverEnabled");
/* 123 */     this.hotTrackingOn = (object instanceof Boolean) ? ((Boolean)object).booleanValue() : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintBackground(Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/* 131 */     if (WindowsMenuItemUI.isVistaPainting()) {
/* 132 */       WindowsMenuItemUI.paintBackground(this.accessor, paramGraphics, paramJMenuItem, paramColor);
/*     */       
/*     */       return;
/*     */     } 
/* 136 */     JMenu jMenu = (JMenu)paramJMenuItem;
/* 137 */     ButtonModel buttonModel = jMenu.getModel();
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (WindowsLookAndFeel.isClassicWindows() || 
/* 142 */       !jMenu.isTopLevelMenu() || (
/* 143 */       XPStyle.getXP() != null && (buttonModel.isArmed() || buttonModel.isSelected()))) {
/*     */       
/* 145 */       super.paintBackground(paramGraphics, jMenu, paramColor);
/*     */       
/*     */       return;
/*     */     } 
/* 149 */     Color color1 = paramGraphics.getColor();
/* 150 */     int i = jMenu.getWidth();
/* 151 */     int j = jMenu.getHeight();
/*     */     
/* 153 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 154 */     Color color2 = uIDefaults.getColor("controlLtHighlight");
/* 155 */     Color color3 = uIDefaults.getColor("controlShadow");
/*     */     
/* 157 */     paramGraphics.setColor(jMenu.getBackground());
/* 158 */     paramGraphics.fillRect(0, 0, i, j);
/*     */     
/* 160 */     if (jMenu.isOpaque()) {
/* 161 */       if (buttonModel.isArmed() || buttonModel.isSelected()) {
/*     */         
/* 163 */         paramGraphics.setColor(color3);
/* 164 */         paramGraphics.drawLine(0, 0, i - 1, 0);
/* 165 */         paramGraphics.drawLine(0, 0, 0, j - 2);
/*     */         
/* 167 */         paramGraphics.setColor(color2);
/* 168 */         paramGraphics.drawLine(i - 1, 0, i - 1, j - 2);
/* 169 */         paramGraphics.drawLine(0, j - 2, i - 1, j - 2);
/* 170 */       } else if (buttonModel.isRollover() && buttonModel.isEnabled()) {
/*     */         
/* 172 */         boolean bool = false;
/* 173 */         MenuElement[] arrayOfMenuElement = ((JMenuBar)jMenu.getParent()).getSubElements();
/* 174 */         for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 175 */           if (((JMenuItem)arrayOfMenuElement[b]).isSelected()) {
/* 176 */             bool = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 180 */         if (!bool) {
/* 181 */           if (XPStyle.getXP() != null) {
/* 182 */             paramGraphics.setColor(this.selectionBackground);
/* 183 */             paramGraphics.fillRect(0, 0, i, j);
/*     */           } else {
/*     */             
/* 186 */             paramGraphics.setColor(color2);
/* 187 */             paramGraphics.drawLine(0, 0, i - 1, 0);
/* 188 */             paramGraphics.drawLine(0, 0, 0, j - 2);
/*     */             
/* 190 */             paramGraphics.setColor(color3);
/* 191 */             paramGraphics.drawLine(i - 1, 0, i - 1, j - 2);
/* 192 */             paramGraphics.drawLine(0, j - 2, i - 1, j - 2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 197 */     paramGraphics.setColor(color1);
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
/*     */   protected void paintText(Graphics paramGraphics, JMenuItem paramJMenuItem, Rectangle paramRectangle, String paramString) {
/* 211 */     if (WindowsMenuItemUI.isVistaPainting()) {
/* 212 */       WindowsMenuItemUI.paintText(this.accessor, paramGraphics, paramJMenuItem, paramRectangle, paramString);
/*     */       return;
/*     */     } 
/* 215 */     JMenu jMenu = (JMenu)paramJMenuItem;
/* 216 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/* 217 */     Color color = paramGraphics.getColor();
/*     */ 
/*     */     
/* 220 */     boolean bool = buttonModel.isRollover();
/* 221 */     if (bool && jMenu.isTopLevelMenu()) {
/* 222 */       MenuElement[] arrayOfMenuElement = ((JMenuBar)jMenu.getParent()).getSubElements();
/* 223 */       for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 224 */         if (((JMenuItem)arrayOfMenuElement[b]).isSelected()) {
/* 225 */           bool = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 231 */     if ((buttonModel.isSelected() && (WindowsLookAndFeel.isClassicWindows() || 
/* 232 */       !jMenu.isTopLevelMenu())) || (
/* 233 */       XPStyle.getXP() != null && (bool || buttonModel
/* 234 */       .isArmed() || buttonModel
/* 235 */       .isSelected()))) {
/* 236 */       paramGraphics.setColor(this.selectionForeground);
/*     */     }
/*     */     
/* 239 */     WindowsGraphicsUtils.paintText(paramGraphics, paramJMenuItem, paramRectangle, paramString, 0);
/*     */     
/* 241 */     paramGraphics.setColor(color);
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/* 245 */     return new WindowsMouseInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class WindowsMouseInputHandler
/*     */     extends BasicMenuUI.MouseInputHandler
/*     */   {
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 255 */       super.mouseEntered(param1MouseEvent);
/*     */       
/* 257 */       JMenu jMenu = (JMenu)param1MouseEvent.getSource();
/* 258 */       if (WindowsMenuUI.this.hotTrackingOn && jMenu.isTopLevelMenu() && jMenu.isRolloverEnabled()) {
/* 259 */         jMenu.getModel().setRollover(true);
/* 260 */         WindowsMenuUI.this.menuItem.repaint();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 265 */       super.mouseExited(param1MouseEvent);
/*     */       
/* 267 */       JMenu jMenu = (JMenu)param1MouseEvent.getSource();
/* 268 */       ButtonModel buttonModel = jMenu.getModel();
/* 269 */       if (jMenu.isRolloverEnabled()) {
/* 270 */         buttonModel.setRollover(false);
/* 271 */         WindowsMenuUI.this.menuItem.repaint();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Dimension getPreferredMenuItemSize(JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, int paramInt) {
/* 281 */     Dimension dimension = super.getPreferredMenuItemSize(paramJComponent, paramIcon1, paramIcon2, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     if (paramJComponent instanceof JMenu && ((JMenu)paramJComponent).isTopLevelMenu() && this.menuBarHeight != null && dimension.height < this.menuBarHeight
/* 288 */       .intValue())
/*     */     {
/* 290 */       dimension.height = this.menuBarHeight.intValue();
/*     */     }
/*     */     
/* 293 */     return dimension;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */