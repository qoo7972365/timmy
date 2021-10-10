/*    */ package javax.swing.plaf.nimbus;
/*    */ 
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JComponent;
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
/*    */ class ComboBoxEditableState
/*    */   extends State
/*    */ {
/*    */   ComboBoxEditableState() {
/* 33 */     super("Editable");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     return (paramJComponent instanceof JComboBox && ((JComboBox)paramJComponent).isEditable());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ComboBoxEditableState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */