/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import javax.swing.AbstractButton;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import sun.awt.AppContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MotifCheckBoxUI
/*    */   extends MotifRadioButtonUI
/*    */ {
/* 50 */   private static final Object MOTIF_CHECK_BOX_UI_KEY = new Object();
/*    */ 
/*    */   
/*    */   private static final String propertyPrefix = "CheckBox.";
/*    */ 
/*    */   
/*    */   private boolean defaults_initialized = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 61 */     AppContext appContext = AppContext.getAppContext();
/*    */     
/* 63 */     MotifCheckBoxUI motifCheckBoxUI = (MotifCheckBoxUI)appContext.get(MOTIF_CHECK_BOX_UI_KEY);
/* 64 */     if (motifCheckBoxUI == null) {
/* 65 */       motifCheckBoxUI = new MotifCheckBoxUI();
/* 66 */       appContext.put(MOTIF_CHECK_BOX_UI_KEY, motifCheckBoxUI);
/*    */     } 
/* 68 */     return motifCheckBoxUI;
/*    */   }
/*    */   
/*    */   public String getPropertyPrefix() {
/* 72 */     return "CheckBox.";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void installDefaults(AbstractButton paramAbstractButton) {
/* 79 */     super.installDefaults(paramAbstractButton);
/* 80 */     if (!this.defaults_initialized) {
/* 81 */       this.icon = UIManager.getIcon(getPropertyPrefix() + "icon");
/* 82 */       this.defaults_initialized = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 87 */     super.uninstallDefaults(paramAbstractButton);
/* 88 */     this.defaults_initialized = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifCheckBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */