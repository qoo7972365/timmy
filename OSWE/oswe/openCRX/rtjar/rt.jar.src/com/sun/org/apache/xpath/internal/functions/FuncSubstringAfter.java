/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.utils.XMLString;
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
/*    */ public class FuncSubstringAfter
/*    */   extends Function2Args
/*    */ {
/*    */   static final long serialVersionUID = -8119731889862512194L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 49 */     XMLString s1 = this.m_arg0.execute(xctxt).xstr();
/* 50 */     XMLString s2 = this.m_arg1.execute(xctxt).xstr();
/* 51 */     int index = s1.indexOf(s2);
/*    */     
/* 53 */     return (-1 == index) ? XString.EMPTYSTRING : (XString)s1
/*    */       
/* 55 */       .substring(index + s2.length());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncSubstringAfter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */