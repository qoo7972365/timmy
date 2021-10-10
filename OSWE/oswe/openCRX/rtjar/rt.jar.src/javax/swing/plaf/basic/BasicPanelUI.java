/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.PanelUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicPanelUI
/*     */   extends PanelUI
/*     */ {
/*     */   private static PanelUI panelUI;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  47 */     if (panelUI == null) {
/*  48 */       panelUI = new BasicPanelUI();
/*     */     }
/*  50 */     return panelUI;
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  54 */     JPanel jPanel = (JPanel)paramJComponent;
/*  55 */     super.installUI(jPanel);
/*  56 */     installDefaults(jPanel);
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  60 */     JPanel jPanel = (JPanel)paramJComponent;
/*  61 */     uninstallDefaults(jPanel);
/*  62 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installDefaults(JPanel paramJPanel) {
/*  66 */     LookAndFeel.installColorsAndFont(paramJPanel, "Panel.background", "Panel.foreground", "Panel.font");
/*     */ 
/*     */ 
/*     */     
/*  70 */     LookAndFeel.installBorder(paramJPanel, "Panel.border");
/*  71 */     LookAndFeel.installProperty(paramJPanel, "opaque", Boolean.TRUE);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(JPanel paramJPanel) {
/*  75 */     LookAndFeel.uninstallBorder(paramJPanel);
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  88 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  89 */     Border border = paramJComponent.getBorder();
/*  90 */     if (border instanceof AbstractBorder) {
/*  91 */       return ((AbstractBorder)border).getBaseline(paramJComponent, paramInt1, paramInt2);
/*     */     }
/*  93 */     return -1;
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
/*     */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 106 */     super.getBaselineResizeBehavior(paramJComponent);
/* 107 */     Border border = paramJComponent.getBorder();
/* 108 */     if (border instanceof AbstractBorder) {
/* 109 */       return ((AbstractBorder)border).getBaselineResizeBehavior(paramJComponent);
/*     */     }
/* 111 */     return Component.BaselineResizeBehavior.OTHER;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicPanelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */