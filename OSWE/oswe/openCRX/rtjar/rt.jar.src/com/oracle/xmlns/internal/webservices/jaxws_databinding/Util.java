/*    */ package com.oracle.xmlns.internal.webservices.jaxws_databinding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.model.RuntimeModelerException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Util
/*    */ {
/*    */   static String nullSafe(String value) {
/* 38 */     return (value == null) ? "" : value;
/*    */   }
/*    */   
/*    */   static <T> T nullSafe(T value, T defaultValue) {
/* 42 */     return (value == null) ? defaultValue : value;
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends Enum> T nullSafe(Enum value, T defaultValue) {
/* 47 */     return (value == null) ? defaultValue : (T)Enum.valueOf(defaultValue.getClass(), value.toString());
/*    */   }
/*    */   
/*    */   public static Class<?> findClass(String className) {
/*    */     try {
/* 52 */       return Class.forName(className);
/* 53 */     } catch (ClassNotFoundException e) {
/* 54 */       throw new RuntimeModelerException("runtime.modeler.external.metadata.generic", new Object[] { e });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/xmlns/internal/webservices/jaxws_databinding/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */