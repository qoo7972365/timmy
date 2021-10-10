/*    */ package javax.swing.plaf.nimbus;
/*    */ 
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
/*    */ 
/*    */ class TableHeaderRendererSortedState
/*    */   extends State
/*    */ {
/*    */   TableHeaderRendererSortedState() {
/* 33 */     super("Sorted");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 38 */     String str = (String)paramJComponent.getClientProperty("Table.sortOrder");
/* 39 */     return (str != null && ("ASCENDING".equals(str) || "DESCENDING".equals(str)));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/TableHeaderRendererSortedState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */