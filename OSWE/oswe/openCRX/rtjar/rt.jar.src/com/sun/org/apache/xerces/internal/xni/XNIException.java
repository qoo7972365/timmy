/*    */ package com.sun.org.apache.xerces.internal.xni;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XNIException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 9019819772686063775L;
/*    */   private Exception fException;
/*    */   
/*    */   public XNIException(String message) {
/* 60 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XNIException(Exception exception) {
/* 69 */     super(exception.getMessage());
/* 70 */     this.fException = exception;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XNIException(String message, Exception exception) {
/* 80 */     super(message);
/* 81 */     this.fException = exception;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Exception getException() {
/* 90 */     return this.fException;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 94 */     return this.fException;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/XNIException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */