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
/*    */ 
/*    */ 
/*    */ public class FuncDoclocation
/*    */   extends FunctionDef1Arg
/*    */ {
/*    */   static final long serialVersionUID = 7469213946343568769L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 50 */     int whereNode = getArg0AsNode(xctxt);
/* 51 */     String fileLocation = null;
/*    */     
/* 53 */     if (-1 != whereNode) {
/*    */       
/* 55 */       DTM dtm = xctxt.getDTM(whereNode);
/*    */ 
/*    */       
/* 58 */       if (11 == dtm.getNodeType(whereNode))
/*    */       {
/* 60 */         whereNode = dtm.getFirstChild(whereNode);
/*    */       }
/*    */       
/* 63 */       if (-1 != whereNode)
/*    */       {
/* 65 */         fileLocation = dtm.getDocumentBaseURI();
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 71 */     return new XString((null != fileLocation) ? fileLocation : "");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncDoclocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */