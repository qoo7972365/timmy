/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicToolBarSeparatorUI;
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
/*     */ public class WindowsToolBarSeparatorUI
/*     */   extends BasicToolBarSeparatorUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  47 */     return new WindowsToolBarSeparatorUI();
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  51 */     Dimension dimension = ((JToolBar.Separator)paramJComponent).getSeparatorSize();
/*     */     
/*  53 */     if (dimension != null) {
/*  54 */       dimension = dimension.getSize();
/*     */     } else {
/*  56 */       dimension = new Dimension(6, 6);
/*  57 */       XPStyle xPStyle = XPStyle.getXP();
/*  58 */       if (xPStyle != null) {
/*  59 */         boolean bool = (((JSeparator)paramJComponent).getOrientation() == 1) ? true : false;
/*  60 */         TMSchema.Part part = bool ? TMSchema.Part.TP_SEPARATOR : TMSchema.Part.TP_SEPARATORVERT;
/*  61 */         XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, part);
/*  62 */         dimension.width = skin.getWidth();
/*  63 */         dimension.height = skin.getHeight();
/*     */       } 
/*     */       
/*  66 */       if (((JSeparator)paramJComponent).getOrientation() == 1) {
/*  67 */         dimension.height = 0;
/*     */       } else {
/*  69 */         dimension.width = 0;
/*     */       } 
/*     */     } 
/*  72 */     return dimension;
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  76 */     Dimension dimension = getPreferredSize(paramJComponent);
/*  77 */     if (((JSeparator)paramJComponent).getOrientation() == 1) {
/*  78 */       return new Dimension(dimension.width, 32767);
/*     */     }
/*  80 */     return new Dimension(32767, dimension.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  85 */     boolean bool = (((JSeparator)paramJComponent).getOrientation() == 1) ? true : false;
/*  86 */     Dimension dimension = paramJComponent.getSize();
/*     */     
/*  88 */     XPStyle xPStyle = XPStyle.getXP();
/*  89 */     if (xPStyle != null) {
/*  90 */       TMSchema.Part part = bool ? TMSchema.Part.TP_SEPARATOR : TMSchema.Part.TP_SEPARATORVERT;
/*  91 */       XPStyle.Skin skin = xPStyle.getSkin(paramJComponent, part);
/*     */       
/*  93 */       boolean bool1 = bool ? ((dimension.width - skin.getWidth()) / 2) : false;
/*  94 */       boolean bool2 = bool ? false : ((dimension.height - skin.getHeight()) / 2);
/*  95 */       int i = bool ? skin.getWidth() : dimension.width;
/*  96 */       int j = bool ? dimension.height : skin.getHeight();
/*  97 */       skin.paintSkin(paramGraphics, bool1, bool2, i, j, null);
/*     */     } else {
/*     */       
/* 100 */       Color color1 = paramGraphics.getColor();
/*     */       
/* 102 */       UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*     */       
/* 104 */       Color color2 = uIDefaults.getColor("ToolBar.shadow");
/* 105 */       Color color3 = uIDefaults.getColor("ToolBar.highlight");
/*     */       
/* 107 */       if (bool) {
/* 108 */         int i = dimension.width / 2 - 1;
/* 109 */         paramGraphics.setColor(color2);
/* 110 */         paramGraphics.drawLine(i, 2, i, dimension.height - 2);
/*     */         
/* 112 */         paramGraphics.setColor(color3);
/* 113 */         paramGraphics.drawLine(i + 1, 2, i + 1, dimension.height - 2);
/*     */       } else {
/* 115 */         int i = dimension.height / 2 - 1;
/* 116 */         paramGraphics.setColor(color2);
/* 117 */         paramGraphics.drawLine(2, i, dimension.width - 2, i);
/* 118 */         paramGraphics.setColor(color3);
/* 119 */         paramGraphics.drawLine(2, i + 1, dimension.width - 2, i + 1);
/*     */       } 
/* 121 */       paramGraphics.setColor(color1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsToolBarSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */