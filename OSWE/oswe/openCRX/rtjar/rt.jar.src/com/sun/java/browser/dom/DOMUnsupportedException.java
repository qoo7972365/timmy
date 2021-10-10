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
/*    */ 
/*    */ public class DOMUnsupportedException
/*    */   extends Exception
/*    */ {
/*    */   private Throwable ex;
/*    */   private String msg;
/*    */   
/*    */   public DOMUnsupportedException() {
/* 36 */     this(null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMUnsupportedException(String paramString) {
/* 46 */     this(null, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DOMUnsupportedException(Exception paramException) {
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
/*    */   public DOMUnsupportedException(Exception paramException, String paramString) {
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


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/browser/dom/DOMUnsupportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */