/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataHandlerAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final DataHandler dh;
/*     */   private final String contentId;
/*     */   String contentIdNoAngleBracket;
/*     */   
/*     */   public DataHandlerAttachment(@NotNull String contentId, @NotNull DataHandler dh) {
/*  57 */     this.dh = dh;
/*  58 */     this.contentId = contentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentId() {
/*  63 */     if (this.contentIdNoAngleBracket == null) {
/*  64 */       this.contentIdNoAngleBracket = this.contentId;
/*  65 */       if (this.contentIdNoAngleBracket != null && this.contentIdNoAngleBracket.charAt(0) == '<')
/*  66 */         this.contentIdNoAngleBracket = this.contentIdNoAngleBracket.substring(1, this.contentIdNoAngleBracket.length() - 1); 
/*     */     } 
/*  68 */     return this.contentIdNoAngleBracket;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  72 */     return this.dh.getContentType();
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*     */     try {
/*  77 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/*  78 */       this.dh.writeTo(os);
/*  79 */       return os.toByteArray();
/*  80 */     } catch (IOException e) {
/*  81 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  86 */     return this.dh;
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*     */     try {
/*  91 */       return new StreamSource(this.dh.getInputStream());
/*  92 */     } catch (IOException e) {
/*  93 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/*     */     try {
/*  99 */       return this.dh.getInputStream();
/* 100 */     } catch (IOException e) {
/* 101 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 106 */     this.dh.writeTo(os);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 110 */     AttachmentPart part = saaj.createAttachmentPart();
/* 111 */     part.setDataHandler(this.dh);
/* 112 */     part.setContentId(this.contentId);
/* 113 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/DataHandlerAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */