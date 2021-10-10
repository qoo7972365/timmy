/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.ContentType;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.developer.StreamingAttachmentFeature;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.util.Iterator;
/*     */ import java.util.UUID;
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
/*     */ 
/*     */ 
/*     */ abstract class MimeCodec
/*     */   implements Codec
/*     */ {
/*     */   public static final String MULTIPART_RELATED_MIME_TYPE = "multipart/related";
/*     */   protected Codec mimeRootCodec;
/*     */   protected final SOAPVersion version;
/*     */   protected final WSFeatureList features;
/*     */   
/*     */   protected MimeCodec(SOAPVersion version, WSFeatureList f) {
/*  71 */     this.version = version;
/*  72 */     this.features = f;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  76 */     return "multipart/related";
/*     */   }
/*     */   
/*     */   protected Codec getMimeRootCodec(Packet packet) {
/*  80 */     return this.mimeRootCodec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/*  86 */     Message msg = packet.getMessage();
/*  87 */     if (msg == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     ContentTypeImpl ctImpl = (ContentTypeImpl)getStaticContentType(packet);
/*  91 */     String boundary = ctImpl.getBoundary();
/*  92 */     boolean hasAttachments = (boundary != null);
/*  93 */     Codec rootCodec = getMimeRootCodec(packet);
/*  94 */     if (hasAttachments) {
/*  95 */       writeln("--" + boundary, out);
/*  96 */       ContentType ct = rootCodec.getStaticContentType(packet);
/*  97 */       String ctStr = (ct != null) ? ct.getContentType() : rootCodec.getMimeType();
/*  98 */       writeln("Content-Type: " + ctStr, out);
/*  99 */       writeln(out);
/*     */     } 
/* 101 */     ContentType primaryCt = rootCodec.encode(packet, out);
/*     */     
/* 103 */     if (hasAttachments) {
/* 104 */       writeln(out);
/*     */       
/* 106 */       for (Attachment att : msg.getAttachments()) {
/* 107 */         writeln("--" + boundary, out);
/*     */ 
/*     */         
/* 110 */         String cid = att.getContentId();
/* 111 */         if (cid != null && cid.length() > 0 && cid.charAt(0) != '<')
/* 112 */           cid = '<' + cid + '>'; 
/* 113 */         writeln("Content-Id:" + cid, out);
/* 114 */         writeln("Content-Type: " + att.getContentType(), out);
/* 115 */         writeCustomMimeHeaders(att, out);
/* 116 */         writeln("Content-Transfer-Encoding: binary", out);
/* 117 */         writeln(out);
/* 118 */         att.writeTo(out);
/* 119 */         writeln(out);
/*     */       } 
/* 121 */       writeAsAscii("--" + boundary, out);
/* 122 */       writeAsAscii("--", out);
/*     */     } 
/*     */     
/* 125 */     return hasAttachments ? ctImpl : primaryCt;
/*     */   }
/*     */   
/*     */   private void writeCustomMimeHeaders(Attachment att, OutputStream out) throws IOException {
/* 129 */     if (att instanceof AttachmentEx) {
/* 130 */       Iterator<AttachmentEx.MimeHeader> allMimeHeaders = ((AttachmentEx)att).getMimeHeaders();
/* 131 */       while (allMimeHeaders.hasNext()) {
/* 132 */         AttachmentEx.MimeHeader mh = allMimeHeaders.next();
/* 133 */         String name = mh.getName();
/*     */         
/* 135 */         if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Id".equalsIgnoreCase(name)) {
/* 136 */           writeln(name + ": " + mh.getValue(), out);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 143 */     ContentType ct = (ContentType)packet.getInternalContentType();
/* 144 */     if (ct != null) return ct; 
/* 145 */     Message msg = packet.getMessage();
/* 146 */     boolean hasAttachments = !msg.getAttachments().isEmpty();
/* 147 */     Codec rootCodec = getMimeRootCodec(packet);
/*     */     
/* 149 */     if (hasAttachments) {
/* 150 */       String boundary = "uuid:" + UUID.randomUUID().toString();
/* 151 */       String boundaryParameter = "boundary=\"" + boundary + "\"";
/*     */ 
/*     */       
/* 154 */       String messageContentType = "multipart/related; type=\"" + rootCodec.getMimeType() + "\"; " + boundaryParameter;
/*     */       
/* 156 */       ContentTypeImpl impl = new ContentTypeImpl(messageContentType, packet.soapAction, null);
/* 157 */       impl.setBoundary(boundary);
/* 158 */       impl.setBoundaryParameter(boundaryParameter);
/* 159 */       packet.setContentType((ContentType)impl);
/* 160 */       return impl;
/*     */     } 
/* 162 */     ct = rootCodec.getStaticContentType(packet);
/* 163 */     packet.setContentType((ContentType)ct);
/* 164 */     return ct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MimeCodec(MimeCodec that) {
/* 172 */     this.version = that.version;
/* 173 */     this.features = that.features;
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 177 */     MimeMultipartParser parser = new MimeMultipartParser(in, contentType, (StreamingAttachmentFeature)this.features.get(StreamingAttachmentFeature.class));
/* 178 */     decode(parser, packet);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 182 */     throw new UnsupportedOperationException();
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
/*     */   public static void writeln(String s, OutputStream out) throws IOException {
/* 194 */     writeAsAscii(s, out);
/* 195 */     writeln(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeAsAscii(String s, OutputStream out) throws IOException {
/* 202 */     int len = s.length();
/* 203 */     for (int i = 0; i < len; i++)
/* 204 */       out.write((byte)s.charAt(i)); 
/*     */   }
/*     */   
/*     */   public static void writeln(OutputStream out) throws IOException {
/* 208 */     out.write(13);
/* 209 */     out.write(10);
/*     */   }
/*     */   
/*     */   protected abstract void decode(MimeMultipartParser paramMimeMultipartParser, Packet paramPacket) throws IOException;
/*     */   
/*     */   public abstract MimeCodec copy();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/MimeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */