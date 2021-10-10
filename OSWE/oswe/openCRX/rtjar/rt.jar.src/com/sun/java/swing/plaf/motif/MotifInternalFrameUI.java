/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifInternalFrameUI
/*     */   extends BasicInternalFrameUI
/*     */ {
/*     */   Color color;
/*     */   Color highlight;
/*     */   Color shadow;
/*     */   MotifInternalFrameTitlePane titlePane;
/*     */   @Deprecated
/*     */   protected KeyStroke closeMenuKey;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  75 */     return new MotifInternalFrameUI((JInternalFrame)paramJComponent);
/*     */   }
/*     */   
/*     */   public MotifInternalFrameUI(JInternalFrame paramJInternalFrame) {
/*  79 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  83 */     super.installUI(paramJComponent);
/*  84 */     setColors((JInternalFrame)paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  88 */     Border border = this.frame.getBorder();
/*  89 */     this.frame.setLayout(this.internalFrameLayout = createLayoutManager());
/*  90 */     if (border == null || border instanceof javax.swing.plaf.UIResource) {
/*  91 */       this.frame.setBorder(new MotifBorders.InternalFrameBorder(this.frame));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installKeyboardActions() {
/*  97 */     super.installKeyboardActions();
/*     */ 
/*     */     
/* 100 */     this.closeMenuKey = KeyStroke.getKeyStroke(27, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 105 */     LookAndFeel.uninstallBorder(this.frame);
/* 106 */     this.frame.setLayout((LayoutManager)null);
/* 107 */     this.internalFrameLayout = null;
/*     */   }
/*     */   
/*     */   private JInternalFrame getFrame() {
/* 111 */     return this.frame;
/*     */   }
/*     */   
/*     */   public JComponent createNorthPane(JInternalFrame paramJInternalFrame) {
/* 115 */     this.titlePane = new MotifInternalFrameTitlePane(paramJInternalFrame);
/* 116 */     return this.titlePane;
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 120 */     return Toolkit.getDefaultToolkit().getScreenSize();
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 124 */     super.uninstallKeyboardActions();
/* 125 */     if (isKeyBindingRegistered()) {
/* 126 */       JInternalFrame.JDesktopIcon jDesktopIcon = this.frame.getDesktopIcon();
/* 127 */       SwingUtilities.replaceUIActionMap(jDesktopIcon, null);
/* 128 */       SwingUtilities.replaceUIInputMap(jDesktopIcon, 2, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupMenuOpenKey() {
/* 134 */     super.setupMenuOpenKey();
/* 135 */     ActionMap actionMap = SwingUtilities.getUIActionMap(this.frame);
/* 136 */     if (actionMap != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 141 */       actionMap.put("showSystemMenu", new AbstractAction() {
/*     */             public void actionPerformed(ActionEvent param1ActionEvent) {
/* 143 */               MotifInternalFrameUI.this.titlePane.showSystemMenu();
/*     */             }
/*     */             public boolean isEnabled() {
/* 146 */               return MotifInternalFrameUI.this.isKeyBindingActive();
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setupMenuCloseKey() {
/* 153 */     ActionMap actionMap1 = SwingUtilities.getUIActionMap(this.frame);
/* 154 */     if (actionMap1 != null) {
/* 155 */       actionMap1.put("hideSystemMenu", new AbstractAction() {
/*     */             public void actionPerformed(ActionEvent param1ActionEvent) {
/* 157 */               MotifInternalFrameUI.this.titlePane.hideSystemMenu();
/*     */             }
/*     */             public boolean isEnabled() {
/* 160 */               return MotifInternalFrameUI.this.isKeyBindingActive();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 167 */     JInternalFrame.JDesktopIcon jDesktopIcon = this.frame.getDesktopIcon();
/*     */     
/* 169 */     InputMap inputMap = SwingUtilities.getUIInputMap(jDesktopIcon, 2);
/* 170 */     if (inputMap == null) {
/*     */       
/* 172 */       Object[] arrayOfObject = (Object[])UIManager.get("DesktopIcon.windowBindings");
/* 173 */       if (arrayOfObject != null) {
/* 174 */         inputMap = LookAndFeel.makeComponentInputMap(jDesktopIcon, arrayOfObject);
/*     */         
/* 176 */         SwingUtilities.replaceUIInputMap(jDesktopIcon, 2, inputMap);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 181 */     ActionMap actionMap2 = SwingUtilities.getUIActionMap(jDesktopIcon);
/* 182 */     if (actionMap2 == null) {
/* 183 */       actionMap2 = new ActionMapUIResource();
/* 184 */       actionMap2.put("hideSystemMenu", new AbstractAction()
/*     */           {
/*     */             public void actionPerformed(ActionEvent param1ActionEvent) {
/* 187 */               JInternalFrame.JDesktopIcon jDesktopIcon = MotifInternalFrameUI.this.getFrame().getDesktopIcon();
/*     */               
/* 189 */               MotifDesktopIconUI motifDesktopIconUI = (MotifDesktopIconUI)jDesktopIcon.getUI();
/* 190 */               motifDesktopIconUI.hideSystemMenu();
/*     */             }
/*     */             public boolean isEnabled() {
/* 193 */               return MotifInternalFrameUI.this.isKeyBindingActive();
/*     */             }
/*     */           });
/* 196 */       SwingUtilities.replaceUIActionMap(jDesktopIcon, actionMap2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void activateFrame(JInternalFrame paramJInternalFrame) {
/* 203 */     super.activateFrame(paramJInternalFrame);
/* 204 */     setColors(paramJInternalFrame);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deactivateFrame(JInternalFrame paramJInternalFrame) {
/* 209 */     setColors(paramJInternalFrame);
/* 210 */     super.deactivateFrame(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   void setColors(JInternalFrame paramJInternalFrame) {
/* 214 */     if (paramJInternalFrame.isSelected()) {
/* 215 */       this.color = UIManager.getColor("InternalFrame.activeTitleBackground");
/*     */     } else {
/* 217 */       this.color = UIManager.getColor("InternalFrame.inactiveTitleBackground");
/*     */     } 
/* 219 */     this.highlight = this.color.brighter();
/* 220 */     this.shadow = this.color.darker().darker();
/* 221 */     this.titlePane.setColors(this.color, this.highlight, this.shadow);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifInternalFrameUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */