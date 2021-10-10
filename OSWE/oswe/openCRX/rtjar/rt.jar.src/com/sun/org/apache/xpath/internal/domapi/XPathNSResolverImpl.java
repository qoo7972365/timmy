/*    */ package com.sun.org.apache.xpath.internal.domapi;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.xpath.XPathNSResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class XPathNSResolverImpl
/*    */   extends PrefixResolverDefault
/*    */   implements XPathNSResolver
/*    */ {
/*    */   public XPathNSResolverImpl(Node xpathExpressionContext) {
/* 53 */     super(xpathExpressionContext);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String lookupNamespaceURI(String prefix) {
/* 60 */     return getNamespaceForPrefix(prefix);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xpath/internal/domapi/XPathNSResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */