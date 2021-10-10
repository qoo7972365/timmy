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
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AttributeValue
/*    */   extends Expression
/*    */ {
/*    */   public static final AttributeValue create(SyntaxTreeNode parent, String text, Parser parser) {
/*    */     AttributeValue result;
/* 37 */     if (text.indexOf('{') != -1) {
/* 38 */       result = new AttributeValueTemplate(text, parser, parent);
/*    */     }
/* 40 */     else if (text.indexOf('}') != -1) {
/* 41 */       result = new AttributeValueTemplate(text, parser, parent);
/*    */     } else {
/*    */       
/* 44 */       result = new SimpleAttributeValue(text);
/* 45 */       result.setParser(parser);
/* 46 */       result.setParent(parent);
/*    */     } 
/* 48 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/AttributeValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */