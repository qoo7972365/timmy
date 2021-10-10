/*    */ package com.sun.xml.internal.ws.wsdl;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionBasedOperationSignature
/*    */ {
/*    */   private final String action;
/*    */   private final QName payloadQName;
/*    */   
/*    */   public ActionBasedOperationSignature(@NotNull String action, @NotNull QName payloadQName) {
/* 42 */     this.action = action;
/* 43 */     this.payloadQName = payloadQName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 48 */     if (this == o) return true; 
/* 49 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 51 */     ActionBasedOperationSignature that = (ActionBasedOperationSignature)o;
/*    */     
/* 53 */     if (!this.action.equals(that.action)) return false; 
/* 54 */     if (!this.payloadQName.equals(that.payloadQName)) return false;
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 61 */     int result = this.action.hashCode();
/* 62 */     result = 31 * result + this.payloadQName.hashCode();
/* 63 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/ActionBasedOperationSignature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */