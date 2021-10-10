/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*    */ import com.sun.org.apache.xml.internal.dtm.DTMIterator;
/*    */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XNumber;
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
/*    */ public class FuncSum
/*    */   extends FunctionOneArg
/*    */ {
/*    */   static final long serialVersionUID = -2719049259574677519L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 51 */     DTMIterator nodes = this.m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
/* 52 */     double sum = 0.0D;
/*    */     
/*    */     int pos;
/* 55 */     while (-1 != (pos = nodes.nextNode())) {
/*    */       
/* 57 */       DTM dtm = nodes.getDTM(pos);
/* 58 */       XMLString s = dtm.getStringValue(pos);
/*    */       
/* 60 */       if (null != s)
/* 61 */         sum += s.toDouble(); 
/*    */     } 
/* 63 */     nodes.detach();
/*    */     
/* 65 */     return new XNumber(sum);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncSum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */