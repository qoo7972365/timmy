/*    */ package com.sun.xml.internal.messaging.saaj.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import javax.xml.soap.MimeHeader;
/*    */ import javax.xml.soap.MimeHeaders;
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
/*    */ public class MimeHeadersUtil
/*    */ {
/*    */   public static MimeHeaders copy(MimeHeaders headers) {
/* 39 */     MimeHeaders newHeaders = new MimeHeaders();
/* 40 */     Iterator<MimeHeader> eachHeader = headers.getAllHeaders();
/* 41 */     while (eachHeader.hasNext()) {
/* 42 */       MimeHeader currentHeader = eachHeader.next();
/*    */       
/* 44 */       newHeaders.addHeader(currentHeader
/* 45 */           .getName(), currentHeader
/* 46 */           .getValue());
/*    */     } 
/* 48 */     return newHeaders;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/MimeHeadersUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */