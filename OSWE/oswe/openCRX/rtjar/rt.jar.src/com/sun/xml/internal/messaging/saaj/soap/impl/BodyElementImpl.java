/*    */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPBodyElement;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import javax.xml.soap.SOAPException;
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
/*    */ public abstract class BodyElementImpl
/*    */   extends ElementImpl
/*    */   implements SOAPBodyElement
/*    */ {
/*    */   public BodyElementImpl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 43 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public BodyElementImpl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 47 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public void setParentElement(SOAPElement element) throws SOAPException {
/* 51 */     if (!(element instanceof javax.xml.soap.SOAPBody)) {
/* 52 */       log.severe("SAAJ0101.impl.parent.of.body.elem.mustbe.body");
/* 53 */       throw new SOAPException("Parent of a SOAPBodyElement has to be a SOAPBody");
/*    */     } 
/* 55 */     super.setParentElement(element);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/BodyElementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */