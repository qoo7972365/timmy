/*    */ package com.sun.org.apache.xpath.internal.functions;
/*    */ 
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
/*    */ public class FuncGenerateId
/*    */   extends FunctionDef1Arg
/*    */ {
/*    */   static final long serialVersionUID = 973544842091724273L;
/*    */   
/*    */   public XObject execute(XPathContext xctxt) throws TransformerException {
/* 49 */     int which = getArg0AsNode(xctxt);
/*    */     
/* 51 */     if (-1 != which)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       return new XString("N" + Integer.toHexString(which).toUpperCase());
/*    */     }
/*    */     
/* 62 */     return XString.EMPTYSTRING;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/functions/FuncGenerateId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */