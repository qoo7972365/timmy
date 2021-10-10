/*    */ package com.sun.xml.internal.ws.message.source;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*    */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*    */ import com.sun.xml.internal.ws.message.stream.PayloadStreamReaderMessage;
/*    */ import com.sun.xml.internal.ws.streaming.SourceReaderFactory;
/*    */ import javax.xml.transform.Source;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PayloadSourceMessage
/*    */   extends PayloadStreamReaderMessage
/*    */ {
/*    */   public PayloadSourceMessage(@Nullable MessageHeaders headers, @NotNull Source payload, @NotNull AttachmentSet attSet, @NotNull SOAPVersion soapVersion) {
/* 52 */     super(headers, SourceReaderFactory.createSourceReader(payload, true), attSet, soapVersion);
/*    */   }
/*    */ 
/*    */   
/*    */   public PayloadSourceMessage(Source s, SOAPVersion soapVer) {
/* 57 */     this(null, s, (AttachmentSet)new AttachmentSetImpl(), soapVer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/source/PayloadSourceMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */