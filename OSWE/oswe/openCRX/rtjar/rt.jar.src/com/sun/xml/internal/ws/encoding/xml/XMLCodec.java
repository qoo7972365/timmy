/*     */ package com.sun.xml.internal.ws.encoding.xml;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class XMLCodec
/*     */   implements Codec
/*     */ {
/*     */   public static final String XML_APPLICATION_MIME_TYPE = "application/xml";
/*     */   public static final String XML_TEXT_MIME_TYPE = "text/xml";
/*  53 */   private static final ContentType contentType = (ContentType)new ContentTypeImpl("text/xml");
/*     */ 
/*     */   
/*     */   private WSFeatureList features;
/*     */ 
/*     */   
/*     */   public XMLCodec(WSFeatureList f) {
/*  60 */     this.features = f;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  64 */     return "application/xml";
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/*  68 */     return contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/*  73 */     String encoding = (String)packet.invocationProperties.get("com.sun.jaxws.rest.contenttype");
/*     */     
/*  75 */     XMLStreamWriter writer = null;
/*     */     
/*  77 */     if (encoding != null && encoding.length() > 0) {
/*  78 */       writer = XMLStreamWriterFactory.create(out, encoding);
/*     */     } else {
/*  80 */       writer = XMLStreamWriterFactory.create(out);
/*     */     } 
/*     */     
/*     */     try {
/*  84 */       if (packet.getMessage().hasPayload()) {
/*  85 */         writer.writeStartDocument();
/*  86 */         packet.getMessage().writePayloadTo(writer);
/*  87 */         writer.flush();
/*     */       } 
/*  89 */     } catch (XMLStreamException e) {
/*  90 */       throw new WebServiceException(e);
/*     */     } 
/*  92 */     return contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Codec copy() {
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 105 */     Message message = XMLMessage.create(contentType, in, this.features);
/* 106 */     packet.setMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/xml/XMLCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */