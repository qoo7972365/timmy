/*    */ package com.sun.xml.internal.txw2;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TxwException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public TxwException(String message) {
/* 33 */     super(message);
/*    */   }
/*    */   
/*    */   public TxwException(Throwable cause) {
/* 37 */     super(cause);
/*    */   }
/*    */   
/*    */   public TxwException(String message, Throwable cause) {
/* 41 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/TxwException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */