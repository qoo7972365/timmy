/*    */ package com.sun.xml.internal.ws.api.message.stream;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLStreamReaderMessage
/*    */   extends StreamBasedMessage
/*    */ {
/*    */   public final XMLStreamReader msg;
/*    */   
/*    */   public XMLStreamReaderMessage(Packet properties, XMLStreamReader msg) {
/* 53 */     super(properties);
/* 54 */     this.msg = msg;
/*    */   }
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
/*    */   public XMLStreamReaderMessage(Packet properties, AttachmentSet attachments, XMLStreamReader msg) {
/* 71 */     super(properties, attachments);
/* 72 */     this.msg = msg;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/stream/XMLStreamReaderMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */