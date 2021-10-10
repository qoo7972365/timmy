/*     */ package com.sun.xml.internal.ws.server.sei;
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
/*     */ public abstract class MessageFiller
/*     */ {
/*     */   protected final int methodPos;
/*     */   
/*     */   protected MessageFiller(int methodPos) {
/*  62 */     this.methodPos = methodPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentFiller
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
/*  80 */       super(param.getIndex());
/*  81 */       this.param = param;
/*  82 */       this.getter = getter;
/*  83 */       this.mimeType = param.getBinding().getMimeType();
/*     */       try {
/*  85 */         this.contentIdPart = URLEncoder.encode(param.getPartName(), "UTF-8") + '=';
/*  86 */       } catch (UnsupportedEncodingException e) {
/*  87 */         throw new WebServiceException(e);
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
/* 101 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 102 */       if (DataHandler.class.isAssignableFrom(type) || Source.class.isAssignableFrom(type))
/* 103 */         return new MessageFiller.DataHandlerFiller(param, getter); 
/* 104 */       if (byte[].class == type)
/* 105 */         return new MessageFiller.ByteArrayFiller(param, getter); 
/* 106 */       if (MessageFiller.isXMLMimeType(param.getBinding().getMimeType())) {
/* 107 */         return new MessageFiller.JAXBFiller(param, getter);
/*     */       }
/* 109 */       return new MessageFiller.DataHandlerFiller(param, getter);
/*     */     }
/*     */ 
/*     */     
/*     */     String getContentId() {
/* 114 */       return this.contentIdPart + UUID.randomUUID() + "@jaxws.sun.com";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayFiller extends AttachmentFiller {
/*     */     protected ByteArrayFiller(ParameterImpl param, ValueGetter getter) {
/* 120 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 124 */       String contentId = getContentId();
/* 125 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 126 */       if (obj != null) {
/* 127 */         ByteArrayAttachment byteArrayAttachment = new ByteArrayAttachment(contentId, (byte[])obj, this.mimeType);
/* 128 */         msg.getAttachments().add((Attachment)byteArrayAttachment);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DataHandlerFiller extends AttachmentFiller {
/*     */     protected DataHandlerFiller(ParameterImpl param, ValueGetter getter) {
/* 135 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 139 */       String contentId = getContentId();
/* 140 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 141 */       DataHandler dh = (obj instanceof DataHandler) ? (DataHandler)obj : new DataHandler(obj, this.mimeType);
/* 142 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(contentId, dh);
/* 143 */       msg.getAttachments().add((Attachment)dataHandlerAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class JAXBFiller extends AttachmentFiller {
/*     */     protected JAXBFiller(ParameterImpl param, ValueGetter getter) {
/* 149 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 153 */       String contentId = getContentId();
/* 154 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 155 */       JAXBAttachment jAXBAttachment = new JAXBAttachment(contentId, obj, this.param.getXMLBridge(), this.mimeType);
/* 156 */       msg.getAttachments().add((Attachment)jAXBAttachment);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends MessageFiller
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueGetter getter;
/*     */     
/*     */     public Header(int methodPos, XMLBridge bridge, ValueGetter getter) {
/* 168 */       super(methodPos);
/* 169 */       this.bridge = bridge;
/* 170 */       this.getter = getter;
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 174 */       Object value = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 175 */       msg.getHeaders().add(Headers.create(this.bridge, value));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 180 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract void fillIn(Object[] paramArrayOfObject, Object paramObject, Message paramMessage);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/MessageFiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */