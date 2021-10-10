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
/*    */ class SliderTrackArrowShapeState
/*    */   extends State
/*    */ {
/*    */   SliderTrackArrowShapeState() {
/* 33 */     super("ArrowShape");
/*    */   }
/*    */   
/*    */   protected boolean isInState(JComponent paramJComponent) {
/* 37 */     return (paramJComponent.getClientProperty("Slider.paintThumbArrowShape") == Boolean.TRUE);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/SliderTrackArrowShapeState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */