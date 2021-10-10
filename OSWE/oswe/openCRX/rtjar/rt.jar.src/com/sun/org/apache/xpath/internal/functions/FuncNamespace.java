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
/*    */ public class FuncNamespace
/*    */   extends FunctionDef1Arg
/*    */ {
/*    */   static final long serialVersionUID = -4695674566722321237L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/*    */     String s;
/* 49 */     int context = getArg0AsNode(xctxt);
/*    */ 
/*    */     
/* 52 */     if (context != -1) {
/*    */       
/* 54 */       DTM dtm = xctxt.getDTM(context);
/* 55 */       int t = dtm.getNodeType(context);
/* 56 */       if (t == 1) {
/*    */         
/* 58 */         s = dtm.getNamespaceURI(context);
/*    */       }
/* 60 */       else if (t == 2) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 66 */         s = dtm.getNodeName(context);
/* 67 */         if (s.startsWith("xmlns:") || s.equals("xmlns")) {
/* 68 */           return XString.EMPTYSTRING;
/*    */         }
/* 70 */         s = dtm.getNamespaceURI(context);
/*    */       } else {
/*    */         
/* 73 */         return XString.EMPTYSTRING;
/*    */       } 
/*    */     } else {
/* 76 */       return XString.EMPTYSTRING;
/*    */     } 
/* 78 */     return (null == s) ? XString.EMPTYSTRING : new XString(s);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncNamespace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */