/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.Header;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
/*     */ import com.sun.xml.internal.org.jvnet.mimepull.MIMEPart;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.internal.ws.developer.StreamingAttachmentFeature;
/*     */ import com.sun.xml.internal.ws.developer.StreamingDataHandler;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayDataSource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MimeMultipartParser
/*     */ {
/*     */   private final String start;
/*     */   private final MIMEMessage message;
/*     */   private Attachment root;
/*     */   private ContentTypeImpl contentType;
/*  73 */   private final Map<String, Attachment> attachments = new HashMap<>();
/*     */   
/*     */   private boolean gotAll;
/*     */   
/*     */   public MimeMultipartParser(InputStream in, String cType, StreamingAttachmentFeature feature) {
/*  78 */     this.contentType = new ContentTypeImpl(cType);
/*     */ 
/*     */     
/*  81 */     String boundary = this.contentType.getBoundary();
/*  82 */     if (boundary == null || boundary.equals("")) {
/*  83 */       throw new WebServiceException("MIME boundary parameter not found" + this.contentType);
/*     */     }
/*  85 */     this
/*  86 */       .message = (feature != null) ? new MIMEMessage(in, boundary, feature.getConfig()) : new MIMEMessage(in, boundary);
/*     */ 
/*     */ 
/*     */     
/*  90 */     String st = this.contentType.getRootId();
/*  91 */     if (st != null && st.length() > 2 && st.charAt(0) == '<' && st.charAt(st.length() - 1) == '>') {
/*  92 */       st = st.substring(1, st.length() - 1);
/*     */     }
/*  94 */     this.start = st;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Attachment getRootPart() {
/* 107 */     if (this.root == null) {
/* 108 */       this.root = (Attachment)new PartAttachment((this.start != null) ? this.message.getPart(this.start) : this.message.getPart(0));
/*     */     }
/* 110 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<String, Attachment> getAttachmentParts() {
/* 119 */     if (!this.gotAll) {
/* 120 */       MIMEPart rootPart = (this.start != null) ? this.message.getPart(this.start) : this.message.getPart(0);
/* 121 */       List<MIMEPart> parts = this.message.getAttachments();
/* 122 */       for (MIMEPart part : parts) {
/* 123 */         if (part != rootPart) {
/* 124 */           String cid = part.getContentId();
/* 125 */           if (!this.attachments.containsKey(cid)) {
/* 126 */             PartAttachment attach = new PartAttachment(part);
/* 127 */             this.attachments.put(attach.getContentId(), attach);
/*     */           } 
/*     */         } 
/*     */       } 
/* 131 */       this.gotAll = true;
/*     */     } 
/* 133 */     return this.attachments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Attachment getAttachmentPart(String contentId) throws IOException {
/*     */     PartAttachment partAttachment;
/* 145 */     Attachment attach = this.attachments.get(contentId);
/* 146 */     if (attach == null) {
/* 147 */       MIMEPart part = this.message.getPart(contentId);
/* 148 */       partAttachment = new PartAttachment(part);
/* 149 */       this.attachments.put(contentId, partAttachment);
/*     */     } 
/* 151 */     return (Attachment)partAttachment;
/*     */   }
/*     */   
/*     */   static class PartAttachment
/*     */     implements AttachmentEx {
/*     */     final MIMEPart part;
/*     */     byte[] buf;
/*     */     private StreamingDataHandler streamingDataHandler;
/*     */     
/*     */     PartAttachment(MIMEPart part) {
/* 161 */       this.part = part;
/*     */     }
/*     */     @NotNull
/*     */     public String getContentId() {
/* 165 */       return this.part.getContentId();
/*     */     }
/*     */     @NotNull
/*     */     public String getContentType() {
/* 169 */       return this.part.getContentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] asByteArray() {
/* 174 */       if (this.buf == null) {
/* 175 */         ByteArrayBuffer baf = new ByteArrayBuffer();
/*     */         try {
/* 177 */           baf.write(this.part.readOnce());
/* 178 */         } catch (IOException ioe) {
/* 179 */           throw new WebServiceException(ioe);
/*     */         } finally {
/* 181 */           if (baf != null) {
/*     */             try {
/* 183 */               baf.close();
/* 184 */             } catch (IOException ex) {
/* 185 */               Logger.getLogger(MimeMultipartParser.class.getName()).log(Level.FINE, (String)null, ex);
/*     */             } 
/*     */           }
/*     */         } 
/* 189 */         this.buf = baf.toByteArray();
/*     */       } 
/* 191 */       return this.buf;
/*     */     }
/*     */ 
/*     */     
/*     */     public DataHandler asDataHandler() {
/* 196 */       if (this.streamingDataHandler == null) {
/* 197 */         this
/* 198 */           .streamingDataHandler = (this.buf != null) ? new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.buf, getContentType())) : new MIMEPartStreamingDataHandler(this.part);
/*     */       }
/*     */       
/* 201 */       return (DataHandler)this.streamingDataHandler;
/*     */     }
/*     */ 
/*     */     
/*     */     public Source asSource() {
/* 206 */       return (this.buf != null) ? new StreamSource(new ByteArrayInputStream(this.buf)) : new StreamSource(this.part
/*     */           
/* 208 */           .read());
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream asInputStream() {
/* 213 */       return (this.buf != null) ? new ByteArrayInputStream(this.buf) : this.part
/* 214 */         .read();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeTo(OutputStream os) throws IOException {
/* 219 */       if (this.buf != null) {
/* 220 */         os.write(this.buf);
/*     */       } else {
/* 222 */         InputStream in = this.part.read();
/* 223 */         byte[] temp = new byte[8192];
/*     */         int len;
/* 225 */         while ((len = in.read(temp)) != -1) {
/* 226 */           os.write(temp, 0, len);
/*     */         }
/* 228 */         in.close();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 234 */       saaj.createAttachmentPart().setDataHandler(asDataHandler());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<AttachmentEx.MimeHeader> getMimeHeaders() {
/* 241 */       final Iterator<? extends Header> ih = this.part.getAllHeaders().iterator();
/* 242 */       return new Iterator<AttachmentEx.MimeHeader>()
/*     */         {
/*     */           public boolean hasNext() {
/* 245 */             return ih.hasNext();
/*     */           }
/*     */ 
/*     */           
/*     */           public AttachmentEx.MimeHeader next() {
/* 250 */             final Header hdr = ih.next();
/* 251 */             return new AttachmentEx.MimeHeader()
/*     */               {
/*     */                 public String getValue() {
/* 254 */                   return hdr.getValue();
/*     */                 }
/*     */                 
/*     */                 public String getName() {
/* 258 */                   return hdr.getName();
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 265 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */   
/*     */   public ContentTypeImpl getContentType() {
/* 272 */     return this.contentType;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/MimeMultipartParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */