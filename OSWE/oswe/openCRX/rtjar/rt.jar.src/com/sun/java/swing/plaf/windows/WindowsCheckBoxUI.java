/*    */ package com.sun.java.swing.plaf.windows;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsCheckBoxUI
/*    */   extends WindowsRadioButtonUI
/*    */ {
/* 54 */   private static final Object WINDOWS_CHECK_BOX_UI_KEY = new Object();
/*    */ 
/*    */   
/*    */   private static final String propertyPrefix = "CheckBox.";
/*    */ 
/*    */   
/*    */   private boolean defaults_initialized = false;
/*    */ 
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 64 */     AppContext appContext = AppContext.getAppContext();
/*    */     
/* 66 */     WindowsCheckBoxUI windowsCheckBoxUI = (WindowsCheckBoxUI)appContext.get(WINDOWS_CHECK_BOX_UI_KEY);
/* 67 */     if (windowsCheckBoxUI == null) {
/* 68 */       windowsCheckBoxUI = new WindowsCheckBoxUI();
/* 69 */       appContext.put(WINDOWS_CHECK_BOX_UI_KEY, windowsCheckBoxUI);
/*    */     } 
/* 71 */     return windowsCheckBoxUI;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPropertyPrefix() {
/* 76 */     return "CheckBox.";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void installDefaults(AbstractButton paramAbstractButton) {
/* 83 */     super.installDefaults(paramAbstractButton);
/* 84 */     if (!this.defaults_initialized) {
/* 85 */       this.icon = UIManager.getIcon(getPropertyPrefix() + "icon");
/* 86 */       this.defaults_initialized = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 91 */     super.uninstallDefaults(paramAbstractButton);
/* 92 */     this.defaults_initialized = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsCheckBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */