/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.ContentType;
/*     */ import com.oracle.webservices.internal.impl.encoding.StreamDecoderImpl;
/*     */ import com.oracle.webservices.internal.impl.internalspi.encoding.StreamDecoder;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.developer.SerializationFeature;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.internal.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.internal.ws.protocol.soap.VersionMismatchException;
/*     */ import com.sun.xml.internal.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public abstract class StreamSOAPCodec
/*     */   implements StreamSOAPCodec, RootOnlyCodec
/*     */ {
/*     */   private static final String SOAP_ENVELOPE = "Envelope";
/*     */   private static final String SOAP_HEADER = "Header";
/*     */   private static final String SOAP_BODY = "Body";
/*     */   private final SOAPVersion soapVersion;
/*     */   protected final SerializationFeature serializationFeature;
/*     */   private final StreamDecoder streamDecoder;
/*     */   private static final String DECODED_MESSAGE_CHARSET = "decodedMessageCharset";
/*     */   
/*     */   StreamSOAPCodec(SOAPVersion soapVersion) {
/*  94 */     this(soapVersion, null);
/*     */   }
/*     */   
/*     */   StreamSOAPCodec(WSBinding binding) {
/*  98 */     this(binding.getSOAPVersion(), (SerializationFeature)binding.getFeature(SerializationFeature.class));
/*     */   }
/*     */   
/*     */   StreamSOAPCodec(WSFeatureList features) {
/* 102 */     this(WebServiceFeatureList.getSoapVersion(features), (SerializationFeature)features.get(SerializationFeature.class));
/*     */   }
/*     */   
/*     */   private StreamSOAPCodec(SOAPVersion soapVersion, @Nullable SerializationFeature sf) {
/* 106 */     this.soapVersion = soapVersion;
/* 107 */     this.serializationFeature = sf;
/* 108 */     this.streamDecoder = selectStreamDecoder();
/*     */   }
/*     */   
/*     */   private StreamDecoder selectStreamDecoder() {
/* 112 */     Iterator<StreamDecoder> iterator = ServiceFinder.find(StreamDecoder.class).iterator(); if (iterator.hasNext()) { StreamDecoder sd = iterator.next();
/* 113 */       return sd; }
/*     */ 
/*     */     
/* 116 */     return (StreamDecoder)new StreamDecoderImpl();
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 120 */     return getContentType(packet);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/* 124 */     if (packet.getMessage() != null) {
/* 125 */       String encoding = getPacketEncoding(packet);
/* 126 */       packet.invocationProperties.remove("decodedMessageCharset");
/* 127 */       XMLStreamWriter writer = XMLStreamWriterFactory.create(out, encoding);
/*     */       try {
/* 129 */         packet.getMessage().writeTo(writer);
/* 130 */         writer.flush();
/* 131 */       } catch (XMLStreamException e) {
/* 132 */         throw new WebServiceException(e);
/*     */       } 
/* 134 */       XMLStreamWriterFactory.recycle(writer);
/*     */     } 
/* 136 */     return getContentType(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 145 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 151 */     decode(in, contentType, packet, (AttachmentSet)new AttachmentSetImpl());
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
/*     */   private static boolean isContentTypeSupported(String ct, List<String> expected) {
/* 163 */     for (String contentType : expected) {
/* 164 */       if (ct.contains(contentType)) {
/* 165 */         return true;
/*     */       }
/*     */     } 
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final Message decode(@NotNull XMLStreamReader reader) {
/* 179 */     return decode(reader, (AttachmentSet)new AttachmentSetImpl());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public final Message decode(XMLStreamReader reader, @NotNull AttachmentSet attachmentSet) {
/* 194 */     return decode(this.soapVersion, reader, attachmentSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Message decode(SOAPVersion soapVersion, XMLStreamReader reader, @NotNull AttachmentSet attachmentSet) {
/* 199 */     if (reader.getEventType() != 1)
/* 200 */       XMLStreamReaderUtil.nextElementContent(reader); 
/* 201 */     XMLStreamReaderUtil.verifyReaderState(reader, 1);
/* 202 */     if ("Envelope".equals(reader.getLocalName()) && !soapVersion.nsUri.equals(reader.getNamespaceURI())) {
/* 203 */       throw new VersionMismatchException(soapVersion, new Object[] { soapVersion.nsUri, reader.getNamespaceURI() });
/*     */     }
/* 205 */     XMLStreamReaderUtil.verifyTag(reader, soapVersion.nsUri, "Envelope");
/* 206 */     return (Message)new StreamMessage(soapVersion, reader, attachmentSet);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 210 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final StreamSOAPCodec copy() {
/* 214 */     return this;
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet, AttachmentSet att) throws IOException {
/* 218 */     List<String> expectedContentTypes = getExpectedContentTypes();
/* 219 */     if (contentType != null && !isContentTypeSupported(contentType, expectedContentTypes)) {
/* 220 */       throw new UnsupportedMediaException(contentType, expectedContentTypes);
/*     */     }
/* 222 */     ContentType pct = packet.getInternalContentType();
/* 223 */     ContentTypeImpl cti = (pct != null && pct instanceof ContentTypeImpl) ? (ContentTypeImpl)pct : new ContentTypeImpl(contentType);
/*     */     
/* 225 */     String charset = cti.getCharSet();
/* 226 */     if (charset != null && !Charset.isSupported(charset)) {
/* 227 */       throw new UnsupportedMediaException(charset);
/*     */     }
/* 229 */     if (charset != null) {
/* 230 */       packet.invocationProperties.put("decodedMessageCharset", charset);
/*     */     } else {
/* 232 */       packet.invocationProperties.remove("decodedMessageCharset");
/*     */     } 
/* 234 */     packet.setMessage(this.streamDecoder.decode(in, charset, att, this.soapVersion));
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response, AttachmentSet att) {
/* 238 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(SOAPVersion version) {
/* 245 */     if (version == null)
/*     */     {
/* 247 */       throw new IllegalArgumentException(); } 
/* 248 */     switch (version) {
/*     */       case SOAP_11:
/* 250 */         return new StreamSOAP11Codec();
/*     */       case SOAP_12:
/* 252 */         return new StreamSOAP12Codec();
/*     */     } 
/* 254 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(WSFeatureList features) {
/* 262 */     SOAPVersion version = WebServiceFeatureList.getSoapVersion(features);
/* 263 */     if (version == null)
/*     */     {
/* 265 */       throw new IllegalArgumentException(); } 
/* 266 */     switch (version) {
/*     */       case SOAP_11:
/* 268 */         return new StreamSOAP11Codec(features);
/*     */       case SOAP_12:
/* 270 */         return new StreamSOAP12Codec(features);
/*     */     } 
/* 272 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StreamSOAPCodec create(WSBinding binding) {
/* 282 */     SOAPVersion version = binding.getSOAPVersion();
/* 283 */     if (version == null)
/*     */     {
/* 285 */       throw new IllegalArgumentException(); } 
/* 286 */     switch (version) {
/*     */       case SOAP_11:
/* 288 */         return new StreamSOAP11Codec(binding);
/*     */       case SOAP_12:
/* 290 */         return new StreamSOAP12Codec(binding);
/*     */     } 
/* 292 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPacketEncoding(Packet packet) {
/* 298 */     if (this.serializationFeature != null && this.serializationFeature.getEncoding() != null) {
/* 299 */       return this.serializationFeature.getEncoding().equals("") ? "utf-8" : this.serializationFeature
/* 300 */         .getEncoding();
/*     */     }
/*     */     
/* 303 */     if (packet != null && packet.endpoint != null) {
/*     */       
/* 305 */       String charset = (String)packet.invocationProperties.get("decodedMessageCharset");
/* 306 */       return (charset == null) ? "utf-8" : charset;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return "utf-8";
/*     */   }
/*     */   
/*     */   protected ContentTypeImpl.Builder getContenTypeBuilder(Packet packet) {
/* 315 */     ContentTypeImpl.Builder b = new ContentTypeImpl.Builder();
/* 316 */     String encoding = getPacketEncoding(packet);
/* 317 */     if ("utf-8".equalsIgnoreCase(encoding)) {
/* 318 */       b.contentType = getDefaultContentType();
/* 319 */       b.charset = "utf-8";
/* 320 */       return b;
/*     */     } 
/* 322 */     b.contentType = getMimeType() + " ;charset=" + encoding;
/* 323 */     b.charset = encoding;
/* 324 */     return b;
/*     */   }
/*     */   
/*     */   protected abstract ContentType getContentType(Packet paramPacket);
/*     */   
/*     */   protected abstract String getDefaultContentType();
/*     */   
/*     */   protected abstract List<String> getExpectedContentTypes();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/StreamSOAPCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */