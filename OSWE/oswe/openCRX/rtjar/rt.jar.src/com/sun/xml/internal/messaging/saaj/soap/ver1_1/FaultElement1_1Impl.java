/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.ElementImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.FaultElementImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class FaultElement1_1Impl
/*    */   extends FaultElementImpl
/*    */ {
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/* 43 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 47 */     super(ownerDoc, qname);
/*    */   }
/*    */ 
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, String localName) {
/* 52 */     super(ownerDoc, NameImpl.createFaultElement1_1Name(localName));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FaultElement1_1Impl(SOAPDocumentImpl ownerDoc, String localName, String prefix) {
/* 58 */     super(ownerDoc, 
/* 59 */         NameImpl.createFaultElement1_1Name(localName, prefix));
/*    */   }
/*    */   
/*    */   protected boolean isStandardFaultElement() {
/* 63 */     String localName = this.elementQName.getLocalPart();
/* 64 */     if (localName.equalsIgnoreCase("faultcode") || localName
/* 65 */       .equalsIgnoreCase("faultstring") || localName
/* 66 */       .equalsIgnoreCase("faultactor")) {
/* 67 */       return true;
/*    */     }
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 73 */     if (!isStandardFaultElement()) {
/*    */       
/* 75 */       FaultElement1_1Impl copy = new FaultElement1_1Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/* 76 */       return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*    */     } 
/* 78 */     return super.setElementQName(newName);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_1/FaultElement1_1Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */