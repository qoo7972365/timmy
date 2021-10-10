/*    */ package com.sun.org.apache.xpath.internal.jaxp;
/*    */ 
/*    */ import com.sun.org.apache.xalan.internal.res.XSLMessages;
/*    */ import com.sun.org.apache.xml.internal.utils.QName;
/*    */ import com.sun.org.apache.xpath.internal.VariableStack;
/*    */ import com.sun.org.apache.xpath.internal.XPathContext;
/*    */ import com.sun.org.apache.xpath.internal.objects.XObject;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.transform.TransformerException;
/*    */ import javax.xml.xpath.XPathVariableResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXPVariableStack
/*    */   extends VariableStack
/*    */ {
/*    */   private final XPathVariableResolver resolver;
/*    */   
/*    */   public JAXPVariableStack(XPathVariableResolver resolver) {
/* 47 */     this.resolver = resolver;
/*    */   }
/*    */ 
/*    */   
/*    */   public XObject getVariableOrParam(XPathContext xctxt, QName qname) throws TransformerException, IllegalArgumentException {
/* 52 */     if (qname == null) {
/*    */ 
/*    */       
/* 55 */       String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Variable qname" });
/*    */ 
/*    */       
/* 58 */       throw new IllegalArgumentException(fmsg);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 63 */     QName name = new QName(qname.getNamespace(), qname.getLocalPart());
/* 64 */     Object varValue = this.resolver.resolveVariable(name);
/* 65 */     if (varValue == null) {
/* 66 */       String fmsg = XSLMessages.createXPATHMessage("ER_RESOLVE_VARIABLE_RETURNS_NULL", new Object[] { name
/*    */             
/* 68 */             .toString() });
/* 69 */       throw new TransformerException(fmsg);
/*    */     } 
/* 71 */     return XObject.create(varValue, xctxt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/jaxp/JAXPVariableStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */