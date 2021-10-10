/*    */ package com.sun.org.apache.xpath.internal.operations;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*    */ import com.sun.org.apache.xpath.internal.Expression;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XNodeSet;
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
/*    */ public class VariableSafeAbsRef
/*    */   extends Variable
/*    */ {
/*    */   static final long serialVersionUID = -9174661990819967452L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt, boolean destructiveOK) throws TransformerException {
/* 63 */     XNodeSet xns = (XNodeSet)super.execute(xctxt, destructiveOK);
/* 64 */     DTMManager dtmMgr = xctxt.getDTMManager();
/* 65 */     int context = xctxt.getContextNode();
/* 66 */     if (dtmMgr.getDTM(xns.getRoot()).getDocument() != dtmMgr
/* 67 */       .getDTM(context).getDocument()) {
/*    */       
/* 69 */       Expression expr = (Expression)xns.getContainedIter();
/* 70 */       xns = (XNodeSet)expr.asIterator(xctxt, context);
/*    */     } 
/* 72 */     return xns;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/operations/VariableSafeAbsRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */