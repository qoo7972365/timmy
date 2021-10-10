/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import java.util.Stack;
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
/*    */ public final class StringStack
/*    */   extends Stack
/*    */ {
/*    */   static final long serialVersionUID = -1506910875640317898L;
/*    */   
/*    */   public String peekString() {
/* 35 */     return (String)peek();
/*    */   }
/*    */   
/*    */   public String popString() {
/* 39 */     return (String)pop();
/*    */   }
/*    */   
/*    */   public String pushString(String val) {
/* 43 */     return (String)push((E)val);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/util/StringStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */