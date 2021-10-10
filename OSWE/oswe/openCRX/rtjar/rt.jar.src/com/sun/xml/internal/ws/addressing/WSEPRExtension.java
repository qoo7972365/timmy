/*    */ package com.sun.xml.internal.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ public class WSEPRExtension
/*    */   extends WSEndpointReference.EPRExtension
/*    */ {
/*    */   XMLStreamBuffer xsb;
/*    */   final QName qname;
/*    */   
/*    */   public WSEPRExtension(XMLStreamBuffer xsb, QName qname) {
/* 45 */     this.xsb = xsb;
/* 46 */     this.qname = qname;
/*    */   }
/*    */ 
/*    */   
/*    */   public XMLStreamReader readAsXMLStreamReader() throws XMLStreamException {
/* 51 */     return (XMLStreamReader)this.xsb.readAsXMLStreamReader();
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 55 */     return this.qname;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WSEPRExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */