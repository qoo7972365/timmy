/*    */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*    */ import java.util.logging.Level;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFaultElement;
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
/*    */ public abstract class FaultElementImpl
/*    */   extends ElementImpl
/*    */   implements SOAPFaultElement
/*    */ {
/*    */   protected FaultElementImpl(SOAPDocumentImpl ownerDoc, NameImpl qname) {
/* 43 */     super(ownerDoc, (Name)qname);
/*    */   }
/*    */   
/*    */   protected FaultElementImpl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 47 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   protected abstract boolean isStandardFaultElement();
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 53 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName
/*    */           
/* 55 */           .getLocalPart(), newName
/* 56 */           .getLocalPart() });
/* 57 */     throw new SOAPException("Cannot change name for " + this.elementQName
/* 58 */         .getLocalPart() + " to " + newName
/* 59 */         .getLocalPart());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/FaultElementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */