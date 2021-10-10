/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.EnvelopeImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*    */ import javax.xml.soap.Name;
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
/*    */ public class Envelope1_1Impl
/*    */   extends EnvelopeImpl
/*    */ {
/*    */   public Envelope1_1Impl(SOAPDocumentImpl ownerDoc, String prefix) {
/* 41 */     super(ownerDoc, (Name)NameImpl.createEnvelope1_1Name(prefix));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Envelope1_1Impl(SOAPDocumentImpl ownerDoc, String prefix, boolean createHeader, boolean createBody) throws SOAPException {
/* 49 */     super(ownerDoc, 
/*    */         
/* 51 */         NameImpl.createEnvelope1_1Name(prefix), createHeader, createBody);
/*    */   }
/*    */ 
/*    */   
/*    */   protected NameImpl getBodyName(String prefix) {
/* 56 */     return NameImpl.createBody1_1Name(prefix);
/*    */   }
/*    */   
/*    */   protected NameImpl getHeaderName(String prefix) {
/* 60 */     return NameImpl.createHeader1_1Name(prefix);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_1/Envelope1_1Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */