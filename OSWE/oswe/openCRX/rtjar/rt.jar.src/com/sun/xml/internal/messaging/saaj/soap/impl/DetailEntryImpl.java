/*    */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.DetailEntry;
/*    */ import javax.xml.soap.Name;
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
/*    */ public abstract class DetailEntryImpl
/*    */   extends ElementImpl
/*    */   implements DetailEntry
/*    */ {
/*    */   public DetailEntryImpl(SOAPDocumentImpl ownerDoc, Name qname) {
/* 38 */     super(ownerDoc, qname);
/*    */   }
/*    */   public DetailEntryImpl(SOAPDocumentImpl ownerDoc, QName qname) {
/* 41 */     super(ownerDoc, qname);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/DetailEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */