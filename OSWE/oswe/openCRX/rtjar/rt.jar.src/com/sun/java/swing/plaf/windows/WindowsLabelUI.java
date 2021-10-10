/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicLabelUI;
/*     */ import sun.awt.AppContext;
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
/*     */ 
/*     */ public class WindowsLabelUI
/*     */   extends BasicLabelUI
/*     */ {
/*  56 */   private static final Object WINDOWS_LABEL_UI_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  62 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  64 */     WindowsLabelUI windowsLabelUI = (WindowsLabelUI)appContext.get(WINDOWS_LABEL_UI_KEY);
/*  65 */     if (windowsLabelUI == null) {
/*  66 */       windowsLabelUI = new WindowsLabelUI();
/*  67 */       appContext.put(WINDOWS_LABEL_UI_KEY, windowsLabelUI);
/*     */     } 
/*  69 */     return windowsLabelUI;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintEnabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/*  74 */     int i = paramJLabel.getDisplayedMnemonicIndex();
/*     */     
/*  76 */     if (WindowsLookAndFeel.isMnemonicHidden() == true) {
/*  77 */       i = -1;
/*     */     }
/*     */     
/*  80 */     paramGraphics.setColor(paramJLabel.getForeground());
/*  81 */     SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintDisabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/*  87 */     int i = paramJLabel.getDisplayedMnemonicIndex();
/*     */     
/*  89 */     if (WindowsLookAndFeel.isMnemonicHidden() == true) {
/*  90 */       i = -1;
/*     */     }
/*  92 */     if (UIManager.getColor("Label.disabledForeground") instanceof Color && 
/*  93 */       UIManager.getColor("Label.disabledShadow") instanceof Color) {
/*  94 */       paramGraphics.setColor(UIManager.getColor("Label.disabledShadow"));
/*  95 */       SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1 + 1, paramInt2 + 1);
/*     */ 
/*     */       
/*  98 */       paramGraphics.setColor(UIManager.getColor("Label.disabledForeground"));
/*  99 */       SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
/*     */     }
/*     */     else {
/*     */       
/* 103 */       Color color = paramJLabel.getBackground();
/* 104 */       paramGraphics.setColor(color.brighter());
/* 105 */       SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1 + 1, paramInt2 + 1);
/*     */       
/* 107 */       paramGraphics.setColor(color.darker());
/* 108 */       SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsLabelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */