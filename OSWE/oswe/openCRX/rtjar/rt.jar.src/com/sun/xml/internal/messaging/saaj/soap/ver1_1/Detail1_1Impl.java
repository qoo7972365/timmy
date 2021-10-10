/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.impl.DetailImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Detail1_1Impl
/*    */   extends DetailImpl
/*    */ {
/*    */   public Detail1_1Impl(SOAPDocumentImpl ownerDoc, String prefix) {
/* 43 */     super(ownerDoc, NameImpl.createDetail1_1Name(prefix));
/*    */   }
/*    */   public Detail1_1Impl(SOAPDocumentImpl ownerDoc) {
/* 46 */     super(ownerDoc, NameImpl.createDetail1_1Name());
/*    */   }
/*    */   protected DetailEntry createDetailEntry(Name name) {
/* 49 */     return (DetailEntry)new DetailEntry1_1Impl((SOAPDocumentImpl)
/* 50 */         getOwnerDocument(), name);
/*    */   }
/*    */   
/*    */   protected DetailEntry createDetailEntry(QName name) {
/* 54 */     return (DetailEntry)new DetailEntry1_1Impl((SOAPDocumentImpl)
/* 55 */         getOwnerDocument(), name);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_1/Detail1_1Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */