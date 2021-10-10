/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PropertyGetterBase
/*    */   implements PropertyGetter
/*    */ {
/*    */   protected Class type;
/*    */   
/*    */   public Class getType() {
/* 38 */     return this.type;
/*    */   }
/*    */   
/*    */   public static boolean getterPattern(Method method) {
/* 42 */     if (!method.getReturnType().equals(void.class) && (method
/* 43 */       .getParameterTypes() == null || (method
/* 44 */       .getParameterTypes()).length == 0)) {
/* 45 */       if (method.getName().startsWith("get") && method
/* 46 */         .getName().length() > 3) {
/* 47 */         return true;
/*    */       }
/* 49 */       if (method.getReturnType().equals(boolean.class) && method
/* 50 */         .getName().startsWith("is") && method
/* 51 */         .getName().length() > 2) {
/* 52 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/PropertyGetterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */