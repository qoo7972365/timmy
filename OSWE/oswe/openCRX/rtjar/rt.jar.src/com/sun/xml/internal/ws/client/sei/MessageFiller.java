/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.Headers;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.message.ByteArrayAttachment;
/*     */ import com.sun.xml.internal.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.internal.ws.message.JAXBAttachment;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.UUID;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MessageFiller
/*     */ {
/*     */   protected final int methodPos;
/*     */   
/*     */   protected MessageFiller(int methodPos) {
/*  63 */     this.methodPos = methodPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class AttachmentFiller
/*     */     extends MessageFiller
/*     */   {
/*     */     protected final ParameterImpl param;
/*     */     
/*     */     protected final ValueGetter getter;
/*     */     
/*     */     protected final String mimeType;
/*     */     
/*     */     private final String contentIdPart;
/*     */ 
/*     */     
/*     */     protected AttachmentFiller(ParameterImpl param, ValueGetter getter) {
/*  81 */       super(param.getIndex());
/*  82 */       this.param = param;
/*  83 */       this.getter = getter;
/*  84 */       this.mimeType = param.getBinding().getMimeType();
/*     */       try {
/*  86 */         this.contentIdPart = URLEncoder.encode(param.getPartName(), "UTF-8") + '=';
/*  87 */       } catch (UnsupportedEncodingException e) {
/*  88 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static MessageFiller createAttachmentFiller(ParameterImpl param, ValueGetter getter) {
/* 102 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 103 */       if (DataHandler.class.isAssignableFrom(type) || Source.class.isAssignableFrom(type))
/* 104 */         return new MessageFiller.DataHandlerFiller(param, getter); 
/* 105 */       if (byte[].class == type)
/* 106 */         return new MessageFiller.ByteArrayFiller(param, getter); 
/* 107 */       if (MessageFiller.isXMLMimeType(param.getBinding().getMimeType())) {
/* 108 */         return new MessageFiller.JAXBFiller(param, getter);
/*     */       }
/* 110 */       return new MessageFiller.DataHandlerFiller(param, getter);
/*     */     }
/*     */ 
/*     */     
/*     */     String getContentId() {
/* 115 */       return this.contentIdPart + UUID.randomUUID() + "@jaxws.sun.com";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayFiller extends AttachmentFiller {
/*     */     protected ByteArrayFiller(ParameterImpl param, ValueGetter getter) {
/* 121 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 124 */       String contentId = getContentId();
/* 125 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 126 */       ByteArrayAttachment byteArrayAttachment = new ByteArrayAttachment(contentId, (byte[])obj, this.mimeType);
/* 127 */       msg.getAttachments().add((Attachment)byteArrayAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DataHandlerFiller extends AttachmentFiller {
/*     */     protected DataHandlerFiller(ParameterImpl param, ValueGetter getter) {
/* 133 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 136 */       String contentId = getContentId();
/* 137 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 138 */       DataHandler dh = (obj instanceof DataHandler) ? (DataHandler)obj : new DataHandler(obj, this.mimeType);
/* 139 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(contentId, dh);
/* 140 */       msg.getAttachments().add((Attachment)dataHandlerAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class JAXBFiller extends AttachmentFiller {
/*     */     protected JAXBFiller(ParameterImpl param, ValueGetter getter) {
/* 146 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 149 */       String contentId = getContentId();
/* 150 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 151 */       JAXBAttachment jAXBAttachment = new JAXBAttachment(contentId, obj, this.param.getXMLBridge(), this.mimeType);
/* 152 */       msg.getAttachments().add((Attachment)jAXBAttachment);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Header
/*     */     extends MessageFiller
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueGetter getter;
/*     */     
/*     */     protected Header(int methodPos, XMLBridge bridge, ValueGetter getter) {
/* 164 */       super(methodPos);
/* 165 */       this.bridge = bridge;
/* 166 */       this.getter = getter;
/*     */     }
/*     */     
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 170 */       Object value = this.getter.get(methodArgs[this.methodPos]);
/* 171 */       msg.getHeaders().add(Headers.create(this.bridge, value));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 176 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   abstract void fillIn(Object[] paramArrayOfObject, Message paramMessage);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/MessageFiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */