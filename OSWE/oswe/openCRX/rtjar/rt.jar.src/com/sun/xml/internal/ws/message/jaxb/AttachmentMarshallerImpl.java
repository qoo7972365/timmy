/*     */ package com.sun.xml.internal.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.message.DataHandlerAttachment;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
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
/*     */ final class AttachmentMarshallerImpl
/*     */   extends AttachmentMarshaller
/*     */ {
/*  52 */   private static final Logger LOGGER = Logger.getLogger(AttachmentMarshallerImpl.class);
/*     */   
/*     */   private AttachmentSet attachments;
/*     */   
/*     */   public AttachmentMarshallerImpl(AttachmentSet attachemnts) {
/*  57 */     this.attachments = attachemnts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cleanup() {
/*  64 */     this.attachments = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName) {
/*  70 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName) {
/*  76 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String addSwaRefAttachment(DataHandler data) {
/*  81 */     String cid = encodeCid(null);
/*  82 */     DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, data);
/*  83 */     this.attachments.add((Attachment)dataHandlerAttachment);
/*  84 */     cid = "cid:" + cid;
/*  85 */     return cid;
/*     */   }
/*     */   
/*     */   private String encodeCid(String ns) {
/*  89 */     String cid = "example.jaxws.sun.com";
/*  90 */     String name = UUID.randomUUID() + "@";
/*  91 */     if (ns != null && ns.length() > 0) {
/*     */       try {
/*  93 */         URI uri = new URI(ns);
/*  94 */         cid = uri.toURL().getHost();
/*  95 */       } catch (URISyntaxException e) {
/*  96 */         if (LOGGER.isLoggable(Level.INFO)) {
/*  97 */           LOGGER.log(Level.INFO, null, e);
/*     */         }
/*  99 */         return null;
/* 100 */       } catch (MalformedURLException e) {
/*     */         try {
/* 102 */           cid = URLEncoder.encode(ns, "UTF-8");
/* 103 */         } catch (UnsupportedEncodingException e1) {
/* 104 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */     }
/* 108 */     return name + cid;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/jaxb/AttachmentMarshallerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */