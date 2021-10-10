/*     */ package com.sun.xml.internal.ws.message.stream;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.Base64Data;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.encoding.DataSourceStreamingDataHandler;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayDataSource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ public class StreamAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final String contentId;
/*     */   private final String contentType;
/*     */   private final ByteArrayBuffer byteArrayBuffer;
/*     */   private final byte[] data;
/*     */   private final int len;
/*     */   
/*     */   public StreamAttachment(ByteArrayBuffer buffer, String contentId, String contentType) {
/*  59 */     this.contentId = contentId;
/*  60 */     this.contentType = contentType;
/*  61 */     this.byteArrayBuffer = buffer;
/*  62 */     this.data = this.byteArrayBuffer.getRawData();
/*  63 */     this.len = this.byteArrayBuffer.size();
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  67 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  71 */     return this.contentType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asByteArray() {
/*  77 */     return this.byteArrayBuffer.toByteArray();
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  81 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, 0, this.len, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*  85 */     return new StreamSource(new ByteArrayInputStream(this.data, 0, this.len));
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/*  89 */     return this.byteArrayBuffer.newInputStream();
/*     */   }
/*     */   
/*     */   public Base64Data asBase64Data() {
/*  93 */     Base64Data base64Data = new Base64Data();
/*  94 */     base64Data.set(this.data, this.len, this.contentType);
/*  95 */     return base64Data;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/*  99 */     this.byteArrayBuffer.writeTo(os);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 103 */     AttachmentPart part = saaj.createAttachmentPart();
/* 104 */     part.setRawContentBytes(this.data, 0, this.len, getContentType());
/* 105 */     part.setContentId(this.contentId);
/* 106 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/stream/StreamAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */