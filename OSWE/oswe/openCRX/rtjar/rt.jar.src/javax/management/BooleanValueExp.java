/*    */ package javax.management;
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
/*    */ class BooleanValueExp
/*    */   extends QueryEval
/*    */   implements ValueExp
/*    */ {
/*    */   private static final long serialVersionUID = 7754922052666594581L;
/*    */   private boolean val = false;
/*    */   
/*    */   BooleanValueExp(boolean paramBoolean) {
/* 49 */     this.val = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   BooleanValueExp(Boolean paramBoolean) {
/* 54 */     this.val = paramBoolean.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Boolean getValue() {
/* 60 */     return Boolean.valueOf(this.val);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return String.valueOf(this.val);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ValueExp apply(ObjectName paramObjectName) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
/* 84 */     return this;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setMBeanServer(MBeanServer paramMBeanServer) {
/* 89 */     super.setMBeanServer(paramMBeanServer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/BooleanValueExp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */