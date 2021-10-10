/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
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
/*    */ final class ArgumentList
/*    */ {
/*    */   private final Expression _arg;
/*    */   private final ArgumentList _rest;
/*    */   
/*    */   public ArgumentList(Expression arg, ArgumentList rest) {
/* 35 */     this._arg = arg;
/* 36 */     this._rest = rest;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return (this._rest == null) ? this._arg
/* 41 */       .toString() : (this._arg
/* 42 */       .toString() + ", " + this._rest.toString());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/ArgumentList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */