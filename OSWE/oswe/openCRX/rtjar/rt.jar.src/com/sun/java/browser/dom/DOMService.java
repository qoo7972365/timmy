/*    */ package com.sun.java.browser.dom;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import sun.security.action.GetPropertyAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DOMService
/*    */ {
/*    */   public static DOMService getService(Object paramObject) throws DOMUnsupportedException {
/*    */     try {
/* 46 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("com.sun.java.browser.dom.DOMServiceProvider"));
/*    */ 
/*    */       
/* 49 */       DOMService.class; Class<?> clazz = Class.forName("sun.plugin.dom.DOMService");
/*    */       
/* 51 */       return (DOMService)clazz.newInstance();
/*    */     }
/* 53 */     catch (Throwable throwable) {
/*    */       
/* 55 */       throw new DOMUnsupportedException(throwable.toString());
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract Object invokeAndWait(DOMAction paramDOMAction) throws DOMAccessException;
/*    */   
/*    */   public abstract void invokeLater(DOMAction paramDOMAction);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/browser/dom/DOMService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */