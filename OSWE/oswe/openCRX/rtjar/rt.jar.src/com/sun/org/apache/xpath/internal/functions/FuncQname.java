/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*    */ import com.sun.org.apache.xpath.internal.objects.XString;
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
/*    */ public class FuncQname
/*    */   extends FunctionDef1Arg
/*    */ {
/*    */   static final long serialVersionUID = -1532307875532617380L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/*    */     XObject val;
/* 49 */     int context = getArg0AsNode(xctxt);
/*    */ 
/*    */     
/* 52 */     if (-1 != context) {
/*    */       
/* 54 */       DTM dtm = xctxt.getDTM(context);
/* 55 */       String qname = dtm.getNodeNameX(context);
/* 56 */       val = (null == qname) ? XString.EMPTYSTRING : new XString(qname);
/*    */     }
/*    */     else {
/*    */       
/* 60 */       val = XString.EMPTYSTRING;
/*    */     } 
/*    */     
/* 63 */     return val;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncQname.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */