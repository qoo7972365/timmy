/*    */ package com.oracle.webservices.internal.api.message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadOnlyPropertyException
/*    */   extends IllegalArgumentException
/*    */ {
/*    */   private final String propertyName;
/*    */   
/*    */   public ReadOnlyPropertyException(String propertyName) {
/* 38 */     super(propertyName + " is a read-only property.");
/* 39 */     this.propertyName = propertyName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPropertyName() {
/* 46 */     return this.propertyName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/message/ReadOnlyPropertyException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */