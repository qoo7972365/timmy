/*    */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.dom.CDATASectionImpl;
/*    */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.Text;
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
/*    */ public class CDATAImpl
/*    */   extends CDATASectionImpl
/*    */   implements Text
/*    */ {
/* 41 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap.impl", "com.sun.xml.internal.messaging.saaj.soap.impl.LocalStrings");
/*    */   
/*    */   static final String cdataUC = "<![CDATA[";
/*    */   
/*    */   static final String cdataLC = "<![cdata[";
/*    */   
/*    */   public CDATAImpl(SOAPDocumentImpl ownerDoc, String text) {
/* 48 */     super((CoreDocumentImpl)ownerDoc, text);
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 52 */     String nodeValue = getNodeValue();
/* 53 */     return nodeValue.equals("") ? null : nodeValue;
/*    */   }
/*    */   
/*    */   public void setValue(String text) {
/* 57 */     setNodeValue(text);
/*    */   }
/*    */   
/*    */   public void setParentElement(SOAPElement parent) throws SOAPException {
/* 61 */     if (parent == null) {
/* 62 */       log.severe("SAAJ0145.impl.no.null.to.parent.elem");
/* 63 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*    */     } 
/* 65 */     ((ElementImpl)parent).addNode(this);
/*    */   }
/*    */   
/*    */   public SOAPElement getParentElement() {
/* 69 */     return (SOAPElement)getParentNode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void detachNode() {
/* 74 */     Node parent = getParentNode();
/* 75 */     if (parent != null) {
/* 76 */       parent.removeChild(this);
/*    */     }
/*    */   }
/*    */   
/*    */   public void recycleNode() {
/* 81 */     detachNode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isComment() {
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/CDATAImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */