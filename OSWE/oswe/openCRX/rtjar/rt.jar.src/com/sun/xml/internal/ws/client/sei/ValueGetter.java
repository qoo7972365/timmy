/*    */ package com.sun.xml.internal.ws.client.sei;
/*    */ 
/*    */ import javax.xml.ws.Holder;
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
/*    */ enum ValueGetter
/*    */ {
/* 51 */   PLAIN {
/*    */     Object get(Object parameter) {
/* 53 */       return parameter;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   HOLDER {
/*    */     Object get(Object parameter) {
/* 66 */       if (parameter == null)
/*    */       {
/* 68 */         return null; } 
/* 69 */       return ((Holder)parameter).value;
/*    */     }
/*    */   };
/*    */   
/*    */   abstract Object get(Object paramObject);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/ValueGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */