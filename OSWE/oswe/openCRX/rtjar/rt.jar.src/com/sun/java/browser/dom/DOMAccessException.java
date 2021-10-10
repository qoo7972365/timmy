/*    */ package com.sun.java.browser.dom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DOMAccessException
/*    */   extends Exception
/*    */ {
/*    */   private Throwable ex;
/*    */   private String msg;
/*    */   
/*    */   public DOMAccessException() {
/* 35 */     this(null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMAccessException(String paramString) {
/* 46 */     this(null, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMAccessException(Exception paramException) {
/* 56 */     this(paramException, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMAccessException(Exception paramException, String paramString) {
/* 67 */     this.ex = paramException;
/* 68 */     this.msg = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 76 */     return this.msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 84 */     return this.ex;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/browser/dom/DOMAccessException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */