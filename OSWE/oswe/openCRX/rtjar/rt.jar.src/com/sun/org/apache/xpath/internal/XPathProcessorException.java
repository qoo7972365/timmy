/*    */ package com.sun.org.apache.xpath.internal;
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
/*    */ public class XPathProcessorException
/*    */   extends XPathException
/*    */ {
/*    */   static final long serialVersionUID = 1215509418326642603L;
/*    */   
/*    */   public XPathProcessorException(String message) {
/* 41 */     super(message);
/*    */   }
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
/*    */   public XPathProcessorException(String message, Exception e) {
/* 54 */     super(message, e);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/XPathProcessorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */