/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XPathFactory
/*    */ {
/*    */   private static boolean xalanInstalled;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       Class<?> clazz = ClassLoaderUtils.loadClass("com.sun.org.apache.xpath.internal.compiler.FunctionTable", XPathFactory.class);
/* 38 */       if (clazz != null) {
/* 39 */         xalanInstalled = true;
/*    */       }
/* 41 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static synchronized boolean isXalanInstalled() {
/* 47 */     return xalanInstalled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static XPathFactory newInstance() {
/* 54 */     if (!isXalanInstalled()) {
/* 55 */       return new JDKXPathFactory();
/*    */     }
/*    */     
/* 58 */     if (XalanXPathAPI.isInstalled()) {
/* 59 */       return new XalanXPathFactory();
/*    */     }
/*    */ 
/*    */     
/* 63 */     return new JDKXPathFactory();
/*    */   }
/*    */   
/*    */   public abstract XPathAPI newXPathAPI();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/XPathFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */