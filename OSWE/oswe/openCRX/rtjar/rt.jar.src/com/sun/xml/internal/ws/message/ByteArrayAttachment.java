/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.encoding.DataSourceStreamingDataHandler;
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
/*     */ public final class ByteArrayAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final String contentId;
/*     */   private byte[] data;
/*     */   private int start;
/*     */   private final int len;
/*     */   private final String mimeType;
/*     */   
/*     */   public ByteArrayAttachment(@NotNull String contentId, byte[] data, int start, int len, String mimeType) {
/*  57 */     this.contentId = contentId;
/*  58 */     this.data = data;
/*  59 */     this.start = start;
/*  60 */     this.len = len;
/*  61 */     this.mimeType = mimeType;
/*     */   }
/*     */   
/*     */   public ByteArrayAttachment(@NotNull String contentId, byte[] data, String mimeType) {
/*  65 */     this(contentId, data, 0, data.length, mimeType);
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  69 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  73 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*  77 */     if (this.start != 0 || this.len != this.data.length) {
/*     */       
/*  79 */       byte[] exact = new byte[this.len];
/*  80 */       System.arraycopy(this.data, this.start, exact, 0, this.len);
/*  81 */       this.start = 0;
/*  82 */       this.data = exact;
/*     */     } 
/*  84 */     return this.data;
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  88 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, this.start, this.len, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*  92 */     return new StreamSource(asInputStream());
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/*  96 */     return new ByteArrayInputStream(this.data, this.start, this.len);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 100 */     os.write(asByteArray());
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 104 */     AttachmentPart part = saaj.createAttachmentPart();
/* 105 */     part.setDataHandler(asDataHandler());
/* 106 */     part.setContentId(this.contentId);
/* 107 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/ByteArrayAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */