/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.encoding.DataSourceStreamingDataHandler;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ public final class JAXBAttachment
/*     */   implements Attachment, DataSource
/*     */ {
/*     */   private final String contentId;
/*     */   private final String mimeType;
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   
/*     */   public JAXBAttachment(@NotNull String contentId, Object jaxbObject, XMLBridge bridge, String mimeType) {
/*  58 */     this.contentId = contentId;
/*  59 */     this.jaxbObject = jaxbObject;
/*  60 */     this.bridge = bridge;
/*  61 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentId() {
/*  66 */     return this.contentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  71 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] asByteArray() {
/*  76 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/*  78 */       writeTo((OutputStream)bab);
/*  79 */     } catch (IOException e) {
/*  80 */       throw new WebServiceException(e);
/*     */     } 
/*  82 */     return bab.getRawData();
/*     */   }
/*     */ 
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  87 */     return (DataHandler)new DataSourceStreamingDataHandler(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Source asSource() {
/*  92 */     return new StreamSource(asInputStream());
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream asInputStream() {
/*  97 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/*  99 */       writeTo((OutputStream)bab);
/* 100 */     } catch (IOException e) {
/* 101 */       throw new WebServiceException(e);
/*     */     } 
/* 103 */     return bab.newInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/*     */     try {
/* 109 */       this.bridge.marshal(this.jaxbObject, os, null, null);
/* 110 */     } catch (JAXBException e) {
/* 111 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 117 */     AttachmentPart part = saaj.createAttachmentPart();
/* 118 */     part.setDataHandler(asDataHandler());
/* 119 */     part.setContentId(this.contentId);
/* 120 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 125 */     return asInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/* 130 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 135 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/JAXBAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */