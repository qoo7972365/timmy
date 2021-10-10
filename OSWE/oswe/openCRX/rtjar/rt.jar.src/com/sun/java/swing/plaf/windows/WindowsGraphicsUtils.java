/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsGraphicsUtils
/*     */ {
/*     */   public static void paintText(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle, String paramString, int paramInt) {
/*  59 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramAbstractButton, paramGraphics);
/*     */     
/*  61 */     int i = paramAbstractButton.getDisplayedMnemonicIndex();
/*     */     
/*  63 */     if (WindowsLookAndFeel.isMnemonicHidden() == true) {
/*  64 */       i = -1;
/*     */     }
/*     */     
/*  67 */     XPStyle xPStyle = XPStyle.getXP();
/*  68 */     if (xPStyle != null && !(paramAbstractButton instanceof javax.swing.JMenuItem)) {
/*  69 */       paintXPText(paramAbstractButton, paramGraphics, paramRectangle.x + paramInt, paramRectangle.y + fontMetrics
/*  70 */           .getAscent() + paramInt, paramString, i);
/*     */     } else {
/*     */       
/*  73 */       paintClassicText(paramAbstractButton, paramGraphics, paramRectangle.x + paramInt, paramRectangle.y + fontMetrics
/*  74 */           .getAscent() + paramInt, paramString, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paintClassicText(AbstractButton paramAbstractButton, Graphics paramGraphics, int paramInt1, int paramInt2, String paramString, int paramInt3) {
/*  81 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/*     */ 
/*     */     
/*  84 */     Color color = paramAbstractButton.getForeground();
/*  85 */     if (buttonModel.isEnabled()) {
/*     */       
/*  87 */       if ((!(paramAbstractButton instanceof javax.swing.JMenuItem) || !buttonModel.isArmed()) && (!(paramAbstractButton instanceof javax.swing.JMenu) || (
/*  88 */         !buttonModel.isSelected() && !buttonModel.isRollover())))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  94 */         paramGraphics.setColor(paramAbstractButton.getForeground());
/*     */       }
/*  96 */       SwingUtilities2.drawStringUnderlineCharAt(paramAbstractButton, paramGraphics, paramString, paramInt3, paramInt1, paramInt2);
/*     */     } else {
/*  98 */       color = UIManager.getColor("Button.shadow");
/*  99 */       Color color1 = UIManager.getColor("Button.disabledShadow");
/* 100 */       if (buttonModel.isArmed()) {
/* 101 */         color = UIManager.getColor("Button.disabledForeground");
/*     */       } else {
/* 103 */         if (color1 == null) {
/* 104 */           color1 = paramAbstractButton.getBackground().darker();
/*     */         }
/* 106 */         paramGraphics.setColor(color1);
/* 107 */         SwingUtilities2.drawStringUnderlineCharAt(paramAbstractButton, paramGraphics, paramString, paramInt3, paramInt1 + 1, paramInt2 + 1);
/*     */       } 
/*     */       
/* 110 */       if (color == null) {
/* 111 */         color = paramAbstractButton.getBackground().brighter();
/*     */       }
/* 113 */       paramGraphics.setColor(color);
/* 114 */       SwingUtilities2.drawStringUnderlineCharAt(paramAbstractButton, paramGraphics, paramString, paramInt3, paramInt1, paramInt2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintXPText(AbstractButton paramAbstractButton, Graphics paramGraphics, int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 120 */     TMSchema.Part part = WindowsButtonUI.getXPButtonType(paramAbstractButton);
/* 121 */     TMSchema.State state = WindowsButtonUI.getXPButtonState(paramAbstractButton);
/* 122 */     paintXPText(paramAbstractButton, part, state, paramGraphics, paramInt1, paramInt2, paramString, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintXPText(AbstractButton paramAbstractButton, TMSchema.Part paramPart, TMSchema.State paramState, Graphics paramGraphics, int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 127 */     XPStyle xPStyle = XPStyle.getXP();
/* 128 */     if (xPStyle == null) {
/*     */       return;
/*     */     }
/* 131 */     Color color = paramAbstractButton.getForeground();
/*     */     
/* 133 */     if (color instanceof javax.swing.plaf.UIResource) {
/* 134 */       color = xPStyle.getColor(paramAbstractButton, paramPart, paramState, TMSchema.Prop.TEXTCOLOR, paramAbstractButton.getForeground());
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (paramPart == TMSchema.Part.TP_BUTTON && paramState == TMSchema.State.DISABLED) {
/* 139 */         Color color1 = xPStyle.getColor(paramAbstractButton, paramPart, TMSchema.State.NORMAL, TMSchema.Prop.TEXTCOLOR, paramAbstractButton
/* 140 */             .getForeground());
/* 141 */         if (color.equals(color1)) {
/* 142 */           color = xPStyle.getColor(paramAbstractButton, TMSchema.Part.BP_PUSHBUTTON, paramState, TMSchema.Prop.TEXTCOLOR, color);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 148 */       TMSchema.TypeEnum typeEnum = xPStyle.getTypeEnum(paramAbstractButton, paramPart, paramState, TMSchema.Prop.TEXTSHADOWTYPE);
/*     */       
/* 150 */       if (typeEnum == TMSchema.TypeEnum.TST_SINGLE || typeEnum == TMSchema.TypeEnum.TST_CONTINUOUS) {
/*     */         
/* 152 */         Color color1 = xPStyle.getColor(paramAbstractButton, paramPart, paramState, TMSchema.Prop.TEXTSHADOWCOLOR, Color.black);
/*     */         
/* 154 */         Point point = xPStyle.getPoint(paramAbstractButton, paramPart, paramState, TMSchema.Prop.TEXTSHADOWOFFSET);
/* 155 */         if (point != null) {
/* 156 */           paramGraphics.setColor(color1);
/* 157 */           SwingUtilities2.drawStringUnderlineCharAt(paramAbstractButton, paramGraphics, paramString, paramInt3, paramInt1 + point.x, paramInt2 + point.y);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 164 */     paramGraphics.setColor(color);
/* 165 */     SwingUtilities2.drawStringUnderlineCharAt(paramAbstractButton, paramGraphics, paramString, paramInt3, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static boolean isLeftToRight(Component paramComponent) {
/* 169 */     return paramComponent.getComponentOrientation().isLeftToRight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void repaintMnemonicsInWindow(Window paramWindow) {
/* 177 */     if (paramWindow == null || !paramWindow.isShowing()) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     Window[] arrayOfWindow = paramWindow.getOwnedWindows();
/* 182 */     for (byte b = 0; b < arrayOfWindow.length; b++) {
/* 183 */       repaintMnemonicsInWindow(arrayOfWindow[b]);
/*     */     }
/*     */     
/* 186 */     repaintMnemonicsInContainer(paramWindow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void repaintMnemonicsInContainer(Container paramContainer) {
/* 195 */     for (byte b = 0; b < paramContainer.getComponentCount(); b++) {
/* 196 */       Component component = paramContainer.getComponent(b);
/* 197 */       if (component != null && component.isVisible())
/*     */       {
/*     */         
/* 200 */         if (component instanceof AbstractButton && ((AbstractButton)component)
/* 201 */           .getMnemonic() != 0) {
/* 202 */           component.repaint();
/*     */         }
/* 204 */         else if (component instanceof JLabel && ((JLabel)component)
/* 205 */           .getDisplayedMnemonic() != 0) {
/* 206 */           component.repaint();
/*     */         
/*     */         }
/* 209 */         else if (component instanceof Container) {
/* 210 */           repaintMnemonicsInContainer((Container)component);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsGraphicsUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */