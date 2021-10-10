/*    */ package com.sun.org.apache.xalan.internal.xsltc.runtime.output;
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
/*    */ class StringOutputBuffer
/*    */   implements OutputBuffer
/*    */ {
/* 34 */   private StringBuffer _buffer = new StringBuffer();
/*    */ 
/*    */   
/*    */   public String close() {
/* 38 */     return this._buffer.toString();
/*    */   }
/*    */   
/*    */   public OutputBuffer append(String s) {
/* 42 */     this._buffer.append(s);
/* 43 */     return this;
/*    */   }
/*    */   
/*    */   public OutputBuffer append(char[] s, int from, int to) {
/* 47 */     this._buffer.append(s, from, to);
/* 48 */     return this;
/*    */   }
/*    */   
/*    */   public OutputBuffer append(char ch) {
/* 52 */     this._buffer.append(ch);
/* 53 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/output/StringOutputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */