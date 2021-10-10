/*    */ package com.sun.org.apache.xml.internal.security.utils;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*    */ import org.w3c.dom.Element;
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
/*    */ @Deprecated
/*    */ public abstract class ElementCheckerImpl
/*    */   implements ElementChecker
/*    */ {
/*    */   public boolean isNamespaceElement(Node paramNode, String paramString1, String paramString2) {
/* 34 */     if (paramNode == null || paramString2 != paramNode
/* 35 */       .getNamespaceURI() || !paramNode.getLocalName().equals(paramString1)) {
/* 36 */       return false;
/*    */     }
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class InternedNsChecker
/*    */     extends ElementCheckerImpl
/*    */   {
/*    */     public void guaranteeThatElementInCorrectSpace(ElementProxy param1ElementProxy, Element param1Element) throws XMLSecurityException {
/* 48 */       String str1 = param1ElementProxy.getBaseLocalName();
/* 49 */       String str2 = param1ElementProxy.getBaseNamespace();
/*    */       
/* 51 */       String str3 = param1Element.getLocalName();
/* 52 */       String str4 = param1Element.getNamespaceURI();
/* 53 */       if (str2 != str4 || 
/* 54 */         !str1.equals(str3)) {
/* 55 */         Object[] arrayOfObject = { str4 + ":" + str3, str2 + ":" + str1 };
/*    */         
/* 57 */         throw new XMLSecurityException("xml.WrongElement", arrayOfObject);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class FullChecker
/*    */     extends ElementCheckerImpl
/*    */   {
/*    */     public void guaranteeThatElementInCorrectSpace(ElementProxy param1ElementProxy, Element param1Element) throws XMLSecurityException {
/* 68 */       String str1 = param1ElementProxy.getBaseLocalName();
/* 69 */       String str2 = param1ElementProxy.getBaseNamespace();
/*    */       
/* 71 */       String str3 = param1Element.getLocalName();
/* 72 */       String str4 = param1Element.getNamespaceURI();
/* 73 */       if (!str2.equals(str4) || 
/* 74 */         !str1.equals(str3)) {
/* 75 */         Object[] arrayOfObject = { str4 + ":" + str3, str2 + ":" + str1 };
/*    */         
/* 77 */         throw new XMLSecurityException("xml.WrongElement", arrayOfObject);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static class EmptyChecker extends ElementCheckerImpl {
/*    */     public void guaranteeThatElementInCorrectSpace(ElementProxy param1ElementProxy, Element param1Element) throws XMLSecurityException {}
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/ElementCheckerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */