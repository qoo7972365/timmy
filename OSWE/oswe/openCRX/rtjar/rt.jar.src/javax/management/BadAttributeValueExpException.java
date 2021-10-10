/*    */ package javax.management;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BadAttributeValueExpException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -3105272988410493376L;
/*    */   private Object val;
/*    */   
/*    */   public BadAttributeValueExpException(Object paramObject) {
/* 59 */     this.val = (paramObject == null) ? null : paramObject.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return "BadAttributeValueException: " + this.val;
/*    */   }
/*    */   
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 71 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 72 */     Object object = getField.get("val", (Object)null);
/*    */     
/* 74 */     if (object == null) {
/* 75 */       this.val = null;
/* 76 */     } else if (object instanceof String) {
/* 77 */       this.val = object;
/* 78 */     } else if (System.getSecurityManager() == null || object instanceof Long || object instanceof Integer || object instanceof Float || object instanceof Double || object instanceof Byte || object instanceof Short || object instanceof Boolean) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 86 */       this.val = object.toString();
/*    */     } else {
/* 88 */       this.val = System.identityHashCode(object) + "@" + object.getClass().getName();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/BadAttributeValueExpException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */