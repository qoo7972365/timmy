/*    */ package com.sun.xml.internal.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.MessageFactoryImpl;
/*    */ import com.sun.xml.internal.messaging.saaj.soap.MessageImpl;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.soap.MimeHeaders;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPMessage;
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
/*    */ public class SOAPMessageFactory1_2Impl
/*    */   extends MessageFactoryImpl
/*    */ {
/*    */   public SOAPMessage createMessage() throws SOAPException {
/* 44 */     return (SOAPMessage)new Message1_2Impl();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SOAPMessage createMessage(boolean isFastInfoset, boolean acceptFastInfoset) throws SOAPException {
/* 50 */     return (SOAPMessage)new Message1_2Impl(isFastInfoset, acceptFastInfoset);
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPMessage createMessage(MimeHeaders headers, InputStream in) throws IOException, SOAPExceptionImpl {
/* 55 */     if (headers == null) {
/* 56 */       headers = new MimeHeaders();
/*    */     }
/*    */     
/* 59 */     if (getContentType(headers) == null) {
/* 60 */       headers.setHeader("Content-Type", "application/soap+xml");
/*    */     }
/*    */     
/* 63 */     MessageImpl msg = new Message1_2Impl(headers, in);
/* 64 */     msg.setLazyAttachments(this.lazyAttachments);
/* 65 */     return (SOAPMessage)msg;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/ver1_2/SOAPMessageFactory1_2Impl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */