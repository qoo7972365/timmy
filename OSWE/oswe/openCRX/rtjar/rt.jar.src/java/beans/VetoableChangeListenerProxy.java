/*    */ package java.beans;
/*    */ 
/*    */ import java.util.EventListenerProxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VetoableChangeListenerProxy
/*    */   extends EventListenerProxy<VetoableChangeListener>
/*    */   implements VetoableChangeListener
/*    */ {
/*    */   private final String propertyName;
/*    */   
/*    */   public VetoableChangeListenerProxy(String paramString, VetoableChangeListener paramVetoableChangeListener) {
/* 60 */     super(paramVetoableChangeListener);
/* 61 */     this.propertyName = paramString;
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
/*    */   public void vetoableChange(PropertyChangeEvent paramPropertyChangeEvent) throws PropertyVetoException {
/* 73 */     getListener().vetoableChange(paramPropertyChangeEvent);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 82 */     return this.propertyName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/VetoableChangeListenerProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */