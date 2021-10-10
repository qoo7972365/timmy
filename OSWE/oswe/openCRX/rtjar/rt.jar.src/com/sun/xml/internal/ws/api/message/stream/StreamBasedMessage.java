/*    */ package com.sun.xml.internal.ws.api.message.stream;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class StreamBasedMessage
/*    */ {
/*    */   public final Packet properties;
/*    */   public final AttachmentSet attachments;
/*    */   
/*    */   protected StreamBasedMessage(Packet properties) {
/* 56 */     this.properties = properties;
/* 57 */     this.attachments = (AttachmentSet)new AttachmentSetImpl();
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
/*    */   protected StreamBasedMessage(Packet properties, AttachmentSet attachments) {
/* 70 */     this.properties = properties;
/* 71 */     this.attachments = attachments;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/stream/StreamBasedMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */