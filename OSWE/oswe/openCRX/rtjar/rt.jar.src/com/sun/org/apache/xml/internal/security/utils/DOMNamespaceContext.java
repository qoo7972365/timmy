/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ import org.w3c.dom.Attr;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NamedNodeMap;
/*    */ import org.w3c.dom.Node;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DOMNamespaceContext
/*    */   implements NamespaceContext
/*    */ {
/* 40 */   private Map<String, String> namespaceMap = new HashMap<>();
/*    */   
/*    */   public DOMNamespaceContext(Node paramNode) {
/* 43 */     addNamespaces(paramNode);
/*    */   }
/*    */   
/*    */   public String getNamespaceURI(String paramString) {
/* 47 */     return this.namespaceMap.get(paramString);
/*    */   }
/*    */   
/*    */   public String getPrefix(String paramString) {
/* 51 */     for (String str1 : this.namespaceMap.keySet()) {
/* 52 */       String str2 = this.namespaceMap.get(str1);
/* 53 */       if (str2.equals(paramString)) {
/* 54 */         return str1;
/*    */       }
/*    */     } 
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   public Iterator<String> getPrefixes(String paramString) {
/* 61 */     return this.namespaceMap.keySet().iterator();
/*    */   }
/*    */   
/*    */   private void addNamespaces(Node paramNode) {
/* 65 */     if (paramNode.getParentNode() != null) {
/* 66 */       addNamespaces(paramNode.getParentNode());
/*    */     }
/* 68 */     if (paramNode instanceof Element) {
/* 69 */       Element element = (Element)paramNode;
/* 70 */       NamedNodeMap namedNodeMap = element.getAttributes();
/* 71 */       for (byte b = 0; b < namedNodeMap.getLength(); b++) {
/* 72 */         Attr attr = (Attr)namedNodeMap.item(b);
/* 73 */         if ("xmlns".equals(attr.getPrefix()))
/* 74 */           this.namespaceMap.put(attr.getLocalName(), attr.getValue()); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/DOMNamespaceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */