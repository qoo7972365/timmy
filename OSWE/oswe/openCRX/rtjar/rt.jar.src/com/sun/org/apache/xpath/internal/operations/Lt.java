/*    */ package com.sun.org.apache.xpath.internal.operations;
/*    */ 
/*    */ import com.sun.org.apache.xpath.internal.objects.XBoolean;
/*    */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*    */ import javax.xml.transform.TransformerException;
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
/*    */ public class Lt
/*    */   extends Operation
/*    */ {
/*    */   static final long serialVersionUID = 3388420509289359422L;
/*    */   
/*    */   public XObject operate(XObject left, XObject right) throws TransformerException {
/* 49 */     return left.lessThan(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/operations/Lt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */