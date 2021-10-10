/*    */ package javax.swing.plaf.metal;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicLabelUI;
/*    */ import sun.awt.AppContext;
/*    */ import sun.swing.SwingUtilities2;
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
/*    */ public class MetalLabelUI
/*    */   extends BasicLabelUI
/*    */ {
/* 55 */   protected static MetalLabelUI metalLabelUI = new MetalLabelUI();
/*    */   
/* 57 */   private static final Object METAL_LABEL_UI_KEY = new Object();
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 60 */     if (System.getSecurityManager() != null) {
/* 61 */       AppContext appContext = AppContext.getAppContext();
/*    */       
/* 63 */       MetalLabelUI metalLabelUI = (MetalLabelUI)appContext.get(METAL_LABEL_UI_KEY);
/* 64 */       if (metalLabelUI == null) {
/* 65 */         metalLabelUI = new MetalLabelUI();
/* 66 */         appContext.put(METAL_LABEL_UI_KEY, metalLabelUI);
/*    */       } 
/* 68 */       return metalLabelUI;
/*    */     } 
/* 70 */     return metalLabelUI;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void paintDisabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/* 82 */     int i = paramJLabel.getDisplayedMnemonicIndex();
/* 83 */     paramGraphics.setColor(UIManager.getColor("Label.disabledForeground"));
/* 84 */     SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalLabelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */