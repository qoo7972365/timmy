/*    */ package com.sun.beans.finder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SignatureException
/*    */   extends RuntimeException
/*    */ {
/*    */   SignatureException(Throwable paramThrowable) {
/* 29 */     super(paramThrowable);
/*    */   }
/*    */   
/*    */   NoSuchMethodException toNoSuchMethodException(String paramString) {
/* 33 */     Throwable throwable = getCause();
/* 34 */     if (throwable instanceof NoSuchMethodException) {
/* 35 */       return (NoSuchMethodException)throwable;
/*    */     }
/* 37 */     NoSuchMethodException noSuchMethodException = new NoSuchMethodException(paramString);
/* 38 */     noSuchMethodException.initCause(throwable);
/* 39 */     return noSuchMethodException;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/SignatureException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */