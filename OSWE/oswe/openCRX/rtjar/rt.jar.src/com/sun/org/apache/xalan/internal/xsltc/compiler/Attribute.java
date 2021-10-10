/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*    */ 
/*    */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
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
/*    */ final class Attribute
/*    */   extends Instruction
/*    */ {
/*    */   private QName _name;
/*    */   
/*    */   public void display(int indent) {
/* 36 */     indent(indent);
/* 37 */     Util.println("Attribute " + this._name);
/* 38 */     displayContents(indent + 4);
/*    */   }
/*    */   
/*    */   public void parseContents(Parser parser) {
/* 42 */     this._name = parser.getQName(getAttribute("name"));
/* 43 */     parseChildren(parser);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */