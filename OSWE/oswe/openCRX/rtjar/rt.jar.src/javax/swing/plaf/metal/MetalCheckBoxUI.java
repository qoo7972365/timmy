/*    */ package javax.swing.plaf.metal;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetalCheckBoxUI
/*    */   extends MetalRadioButtonUI
/*    */ {
/* 60 */   private static final Object METAL_CHECK_BOX_UI_KEY = new Object();
/*    */ 
/*    */   
/*    */   private static final String propertyPrefix = "CheckBox.";
/*    */ 
/*    */   
/*    */   private boolean defaults_initialized = false;
/*    */ 
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 70 */     AppContext appContext = AppContext.getAppContext();
/*    */     
/* 72 */     MetalCheckBoxUI metalCheckBoxUI = (MetalCheckBoxUI)appContext.get(METAL_CHECK_BOX_UI_KEY);
/* 73 */     if (metalCheckBoxUI == null) {
/* 74 */       metalCheckBoxUI = new MetalCheckBoxUI();
/* 75 */       appContext.put(METAL_CHECK_BOX_UI_KEY, metalCheckBoxUI);
/*    */     } 
/* 77 */     return metalCheckBoxUI;
/*    */   }
/*    */   
/*    */   public String getPropertyPrefix() {
/* 81 */     return "CheckBox.";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void installDefaults(AbstractButton paramAbstractButton) {
/* 88 */     super.installDefaults(paramAbstractButton);
/* 89 */     if (!this.defaults_initialized) {
/* 90 */       this.icon = UIManager.getIcon(getPropertyPrefix() + "icon");
/* 91 */       this.defaults_initialized = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 96 */     super.uninstallDefaults(paramAbstractButton);
/* 97 */     this.defaults_initialized = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalCheckBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */