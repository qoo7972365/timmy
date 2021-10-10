/*    */ package javax.swing.plaf.basic;
/*    */ 
/*    */ import javax.swing.JComponent;
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
/*    */ public class BasicCheckBoxUI
/*    */   extends BasicRadioButtonUI
/*    */ {
/* 54 */   private static final Object BASIC_CHECK_BOX_UI_KEY = new Object();
/*    */ 
/*    */   
/*    */   private static final String propertyPrefix = "CheckBox.";
/*    */ 
/*    */ 
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 62 */     AppContext appContext = AppContext.getAppContext();
/*    */     
/* 64 */     BasicCheckBoxUI basicCheckBoxUI = (BasicCheckBoxUI)appContext.get(BASIC_CHECK_BOX_UI_KEY);
/* 65 */     if (basicCheckBoxUI == null) {
/* 66 */       basicCheckBoxUI = new BasicCheckBoxUI();
/* 67 */       appContext.put(BASIC_CHECK_BOX_UI_KEY, basicCheckBoxUI);
/*    */     } 
/* 69 */     return basicCheckBoxUI;
/*    */   }
/*    */   
/*    */   public String getPropertyPrefix() {
/* 73 */     return "CheckBox.";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicCheckBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */