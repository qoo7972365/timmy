/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.encoding.soap.SOAP12Constants;
/*    */ import com.sun.xml.internal.ws.encoding.soap.SOAPConstants;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ public class PayloadElementSniffer
/*    */   extends DefaultHandler
/*    */ {
/*    */   private boolean bodyStarted;
/*    */   private QName payloadQName;
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 53 */     if (this.bodyStarted) {
/* 54 */       this.payloadQName = new QName(uri, localName);
/*    */       
/* 56 */       throw new SAXException("Payload element found, interrupting the parsing process.");
/*    */     } 
/*    */ 
/*    */     
/* 60 */     if (equalsQName(uri, localName, SOAPConstants.QNAME_SOAP_BODY) || 
/* 61 */       equalsQName(uri, localName, SOAP12Constants.QNAME_SOAP_BODY)) {
/* 62 */       this.bodyStarted = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean equalsQName(String uri, String localName, QName qname) {
/* 68 */     return (qname.getLocalPart().equals(localName) && qname
/* 69 */       .getNamespaceURI().equals(uri));
/*    */   }
/*    */   
/*    */   public QName getPayloadQName() {
/* 73 */     return this.payloadQName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/PayloadElementSniffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */