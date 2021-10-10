/*    */ package javax.xml.bind;
/*    */ 
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class GetPropertyAction
/*    */   implements PrivilegedAction<String>
/*    */ {
/*    */   private final String propertyName;
/*    */   
/*    */   public GetPropertyAction(String propertyName) {
/* 38 */     this.propertyName = propertyName;
/*    */   }
/*    */   
/*    */   public String run() {
/* 42 */     return System.getProperty(this.propertyName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/GetPropertyAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */