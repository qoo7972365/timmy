/*    */ package javax.swing.plaf.metal;
/*    */ 
/*    */ import java.beans.PropertyChangeEvent;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicTextFieldUI;
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
/*    */ public class MetalTextFieldUI
/*    */   extends BasicTextFieldUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 53 */     return new MetalTextFieldUI();
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
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 65 */     super.propertyChange(paramPropertyChangeEvent);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalTextFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */