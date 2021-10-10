/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.DesktopManager;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsInternalFrameUI
/*     */   extends BasicInternalFrameUI
/*     */ {
/*  50 */   XPStyle xp = XPStyle.getXP();
/*     */   
/*     */   public void installDefaults() {
/*  53 */     super.installDefaults();
/*     */     
/*  55 */     if (this.xp != null) {
/*  56 */       this.frame.setBorder(new XPBorder());
/*     */     } else {
/*  58 */       this.frame.setBorder(UIManager.getBorder("InternalFrame.border"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  63 */     super.installUI(paramJComponent);
/*     */     
/*  65 */     LookAndFeel.installProperty(paramJComponent, "opaque", (this.xp == null) ? Boolean.TRUE : Boolean.FALSE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallDefaults() {
/*  70 */     this.frame.setBorder(null);
/*  71 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  75 */     return new WindowsInternalFrameUI((JInternalFrame)paramJComponent);
/*     */   }
/*     */   
/*     */   public WindowsInternalFrameUI(JInternalFrame paramJInternalFrame) {
/*  79 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   protected DesktopManager createDesktopManager() {
/*  83 */     return new WindowsDesktopManager();
/*     */   }
/*     */   
/*     */   protected JComponent createNorthPane(JInternalFrame paramJInternalFrame) {
/*  87 */     this.titlePane = new WindowsInternalFrameTitlePane(paramJInternalFrame);
/*  88 */     return this.titlePane;
/*     */   }
/*     */   
/*     */   private class XPBorder extends AbstractBorder {
/*  92 */     private XPStyle.Skin leftSkin = WindowsInternalFrameUI.this.xp.getSkin(WindowsInternalFrameUI.this.frame, TMSchema.Part.WP_FRAMELEFT);
/*  93 */     private XPStyle.Skin rightSkin = WindowsInternalFrameUI.this.xp.getSkin(WindowsInternalFrameUI.this.frame, TMSchema.Part.WP_FRAMERIGHT);
/*  94 */     private XPStyle.Skin bottomSkin = WindowsInternalFrameUI.this.xp.getSkin(WindowsInternalFrameUI.this.frame, TMSchema.Part.WP_FRAMEBOTTOM);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 103 */       TMSchema.State state = ((JInternalFrame)param1Component).isSelected() ? TMSchema.State.ACTIVE : TMSchema.State.INACTIVE;
/* 104 */       byte b = (WindowsInternalFrameUI.this.titlePane != null) ? (WindowsInternalFrameUI.this.titlePane.getSize()).height : 0;
/*     */       
/* 106 */       this.bottomSkin.paintSkin(param1Graphics, 0, param1Int4 - this.bottomSkin.getHeight(), param1Int3, this.bottomSkin
/* 107 */           .getHeight(), state);
/*     */ 
/*     */       
/* 110 */       this.leftSkin.paintSkin(param1Graphics, 0, b - 1, this.leftSkin
/* 111 */           .getWidth(), param1Int4 - b - this.bottomSkin.getHeight() + 2, state);
/*     */ 
/*     */       
/* 114 */       this.rightSkin.paintSkin(param1Graphics, param1Int3 - this.rightSkin.getWidth(), b - 1, this.rightSkin
/* 115 */           .getWidth(), param1Int4 - b - this.bottomSkin.getHeight() + 2, state);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 121 */       param1Insets.top = 4;
/* 122 */       param1Insets.left = this.leftSkin.getWidth();
/* 123 */       param1Insets.right = this.rightSkin.getWidth();
/* 124 */       param1Insets.bottom = this.bottomSkin.getHeight();
/*     */       
/* 126 */       return param1Insets;
/*     */     }
/*     */     
/*     */     public boolean isBorderOpaque() {
/* 130 */       return true;
/*     */     }
/*     */     
/*     */     private XPBorder() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsInternalFrameUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */