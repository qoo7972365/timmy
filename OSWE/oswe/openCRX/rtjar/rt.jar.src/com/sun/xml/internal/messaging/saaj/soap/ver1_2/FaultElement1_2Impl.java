/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.ElementImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.FaultElementImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import org.w3c.dom.Element;
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
/*    */ public class FaultElement1_2Impl
/*    */   extends FaultElementImpl
/*    */ {
/*    */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/* 45 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 49 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public FaultElement1_2Impl(SOAPDocumentImpl ownerDoc, String localName) {
/* 53 */     super(ownerDoc, NameImpl.createSOAP12Name(localName));
/*    */   }
/*    */   
/*    */   protected boolean isStandardFaultElement() {
/* 57 */     String localName = this.elementQName.getLocalPart();
/* 58 */     if (localName.equalsIgnoreCase("code") || localName
/* 59 */       .equalsIgnoreCase("reason") || localName
/* 60 */       .equalsIgnoreCase("node") || localName
/* 61 */       .equalsIgnoreCase("role")) {
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 68 */     if (!isStandardFaultElement()) {
/*    */       
/* 70 */       FaultElement1_2Impl copy = new FaultElement1_2Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/* 71 */       return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*    */     } 
/* 73 */     return super.setElementQName(newName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/* 78 */     log.severe("SAAJ0408.ver1_2.no.encodingStyle.in.fault.child");
/* 79 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on a Fault child element");
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 84 */     if (name.getLocalName().equals("encodingStyle") && name
/* 85 */       .getURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 86 */       setEncodingStyle(value);
/*    */     }
/* 88 */     return super.addAttribute(name, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 93 */     if (name.getLocalPart().equals("encodingStyle") && name
/* 94 */       .getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 95 */       setEncodingStyle(value);
/*    */     }
/* 97 */     return super.addAttribute(name, value);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_2/FaultElement1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */