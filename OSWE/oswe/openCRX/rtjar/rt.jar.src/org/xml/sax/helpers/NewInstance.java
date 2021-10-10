/*    */ package org.xml.sax.helpers;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NewInstance
/*    */ {
/*    */   private static final String DEFAULT_PACKAGE = "com.sun.org.apache.xerces.internal";
/*    */   
/*    */   static Object newInstance(ClassLoader classLoader, String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
/*    */     Class<?> driverClass;
/* 71 */     boolean internal = false;
/* 72 */     if (System.getSecurityManager() != null && 
/* 73 */       className != null && className.startsWith("com.sun.org.apache.xerces.internal")) {
/* 74 */       internal = true;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 79 */     if (classLoader == null || internal) {
/* 80 */       driverClass = Class.forName(className);
/*    */     } else {
/* 82 */       driverClass = classLoader.loadClass(className);
/*    */     } 
/* 84 */     return driverClass.newInstance();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/helpers/NewInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */