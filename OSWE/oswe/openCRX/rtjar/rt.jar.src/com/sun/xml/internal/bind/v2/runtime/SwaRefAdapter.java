/*    */ package com.sun.xml.internal.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*    */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*    */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SwaRefAdapter
/*    */   extends XmlAdapter<String, DataHandler>
/*    */ {
/*    */   public DataHandler unmarshal(String cid) {
/* 60 */     AttachmentUnmarshaller au = (UnmarshallingContext.getInstance()).parent.getAttachmentUnmarshaller();
/*    */     
/* 62 */     return au.getAttachmentAsDataHandler(cid);
/*    */   }
/*    */   
/*    */   public String marshal(DataHandler data) {
/* 66 */     if (data == null) return null; 
/* 67 */     AttachmentMarshaller am = (XMLSerializer.getInstance()).attachmentMarshaller;
/*    */     
/* 69 */     return am.addSwaRefAttachment(data);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/SwaRefAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */