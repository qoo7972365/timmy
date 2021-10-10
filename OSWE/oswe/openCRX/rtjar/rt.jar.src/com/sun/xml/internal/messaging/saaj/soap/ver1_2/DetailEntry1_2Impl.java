/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.DetailEntryImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.ElementImpl;
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
/*    */ public class DetailEntry1_2Impl
/*    */   extends DetailEntryImpl
/*    */ {
/*    */   public DetailEntry1_2Impl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 43 */     super(ownerDoc, qname);
/*    */   }
/*    */   
/*    */   public DetailEntry1_2Impl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 47 */     super(ownerDoc, qname);
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 52 */     DetailEntryImpl copy = new DetailEntry1_2Impl((SOAPDocumentImpl)getOwnerDocument(), newName);
/* 53 */     return replaceElementWithSOAPElement((Element)this, (ElementImpl)copy);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_2/DetailEntry1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */