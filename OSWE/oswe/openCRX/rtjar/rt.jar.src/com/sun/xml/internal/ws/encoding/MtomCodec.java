/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.ContentType;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.Base64Data;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamWriterEx;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.developer.SerializationFeature;
/*     */ import com.sun.xml.internal.ws.developer.StreamingDataHandler;
/*     */ import com.sun.xml.internal.ws.message.MimeAttachmentSet;
/*     */ import com.sun.xml.internal.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.internal.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayDataSource;
/*     */ import com.sun.xml.internal.ws.util.xml.NamespaceContextExAdaper;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderFilter;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamWriterFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MtomCodec
/*     */   extends MimeCodec
/*     */ {
/*     */   public static final String XOP_XML_MIME_TYPE = "application/xop+xml";
/*     */   public static final String XOP_LOCALNAME = "Include";
/*     */   public static final String XOP_NAMESPACEURI = "http://www.w3.org/2004/08/xop/include";
/*     */   private final StreamSOAPCodec codec;
/*     */   private final MTOMFeature mtomFeature;
/*     */   private final SerializationFeature sf;
/*     */   private static final String DECODED_MESSAGE_CHARSET = "decodedMessageCharset";
/*     */   
/*     */   MtomCodec(SOAPVersion version, StreamSOAPCodec codec, WSFeatureList features) {
/*  94 */     super(version, features);
/*  95 */     this.codec = codec;
/*  96 */     this.sf = (SerializationFeature)features.get(SerializationFeature.class);
/*  97 */     MTOMFeature mtom = (MTOMFeature)features.get(MTOMFeature.class);
/*  98 */     if (mtom == null) {
/*  99 */       this.mtomFeature = new MTOMFeature();
/*     */     } else {
/* 101 */       this.mtomFeature = mtom;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 111 */     return getStaticContentTypeStatic(packet, this.version);
/*     */   }
/*     */   
/*     */   public static ContentType getStaticContentTypeStatic(Packet packet, SOAPVersion version) {
/* 115 */     ContentType ct = (ContentType)packet.getInternalContentType();
/* 116 */     if (ct != null) return ct;
/*     */     
/* 118 */     String uuid = UUID.randomUUID().toString();
/* 119 */     String boundary = "uuid:" + uuid;
/* 120 */     String rootId = "<rootpart*" + uuid + "@example.jaxws.sun.com>";
/* 121 */     String soapActionParameter = SOAPVersion.SOAP_11.equals(version) ? null : createActionParameter(packet);
/*     */     
/* 123 */     String boundaryParameter = "boundary=\"" + boundary + "\"";
/* 124 */     String messageContentType = "multipart/related;start=\"" + rootId + "\";type=\"" + "application/xop+xml" + "\";" + boundaryParameter + ";start-info=\"" + version.contentType + ((soapActionParameter == null) ? "" : soapActionParameter) + "\"";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (SOAPVersion.SOAP_11.equals(version)) {  } else {  }  ContentTypeImpl ctImpl = new ContentTypeImpl(messageContentType, null, null);
/*     */ 
/*     */     
/* 135 */     ctImpl.setBoundary(boundary);
/* 136 */     ctImpl.setRootId(rootId);
/* 137 */     packet.setContentType((ContentType)ctImpl);
/* 138 */     return ctImpl;
/*     */   }
/*     */   
/*     */   private static String createActionParameter(Packet packet) {
/* 142 */     return (packet.soapAction != null) ? (";action=\\\"" + packet.soapAction + "\\\"") : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 147 */     ContentTypeImpl ctImpl = (ContentTypeImpl)getStaticContentType(packet);
/* 148 */     String boundary = ctImpl.getBoundary();
/* 149 */     String rootId = ctImpl.getRootId();
/*     */     
/* 151 */     if (packet.getMessage() != null) {
/*     */       try {
/* 153 */         String encoding = getPacketEncoding(packet);
/* 154 */         packet.invocationProperties.remove("decodedMessageCharset");
/*     */         
/* 156 */         String actionParameter = getActionParameter(packet, this.version);
/* 157 */         String soapXopContentType = getSOAPXopContentType(encoding, this.version, actionParameter);
/*     */         
/* 159 */         writeln("--" + boundary, out);
/* 160 */         writeMimeHeaders(soapXopContentType, rootId, out);
/*     */ 
/*     */         
/* 163 */         List<ByteArrayBuffer> mtomAttachments = new ArrayList<>();
/*     */         
/* 165 */         MtomStreamWriterImpl writer = new MtomStreamWriterImpl(XMLStreamWriterFactory.create(out, encoding), mtomAttachments, boundary, this.mtomFeature);
/*     */         
/* 167 */         packet.getMessage().writeTo((XMLStreamWriter)writer);
/* 168 */         XMLStreamWriterFactory.recycle((XMLStreamWriter)writer);
/* 169 */         writeln(out);
/*     */         
/* 171 */         for (ByteArrayBuffer bos : mtomAttachments) {
/* 172 */           bos.write(out);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 177 */         writeNonMtomAttachments(packet.getMessage().getAttachments(), out, boundary);
/*     */ 
/*     */ 
/*     */         
/* 181 */         writeAsAscii("--" + boundary, out);
/* 182 */         writeAsAscii("--", out);
/*     */       }
/* 184 */       catch (XMLStreamException e) {
/* 185 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 190 */     return ctImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSOAPXopContentType(String encoding, SOAPVersion version, String actionParameter) {
/* 195 */     return "application/xop+xml;charset=" + encoding + ";type=\"" + version.contentType + actionParameter + "\"";
/*     */   }
/*     */   
/*     */   public static String getActionParameter(Packet packet, SOAPVersion version) {
/* 199 */     return (version == SOAPVersion.SOAP_11) ? "" : createActionParameter(packet);
/*     */   }
/*     */   
/*     */   public static class ByteArrayBuffer
/*     */   {
/*     */     final String contentId;
/*     */     private final DataHandler dh;
/*     */     private final String boundary;
/*     */     
/*     */     ByteArrayBuffer(@NotNull DataHandler dh, String b) {
/* 209 */       this.dh = dh;
/* 210 */       String cid = null;
/* 211 */       if (dh instanceof StreamingDataHandler) {
/* 212 */         StreamingDataHandler sdh = (StreamingDataHandler)dh;
/* 213 */         if (sdh.getHrefCid() != null)
/* 214 */           cid = sdh.getHrefCid(); 
/*     */       } 
/* 216 */       this.contentId = (cid != null) ? cid : MtomCodec.encodeCid();
/* 217 */       this.boundary = b;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(OutputStream os) throws IOException {
/* 222 */       MimeCodec.writeln("--" + this.boundary, os);
/* 223 */       MtomCodec.writeMimeHeaders(this.dh.getContentType(), this.contentId, os);
/* 224 */       this.dh.writeTo(os);
/* 225 */       MimeCodec.writeln(os);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void writeMimeHeaders(String contentType, String contentId, OutputStream out) throws IOException {
/* 230 */     String cid = contentId;
/* 231 */     if (cid != null && cid.length() > 0 && cid.charAt(0) != '<')
/* 232 */       cid = '<' + cid + '>'; 
/* 233 */     writeln("Content-Id: " + cid, out);
/* 234 */     writeln("Content-Type: " + contentType, out);
/* 235 */     writeln("Content-Transfer-Encoding: binary", out);
/* 236 */     writeln(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeNonMtomAttachments(AttachmentSet attachments, OutputStream out, String boundary) throws IOException {
/* 245 */     for (Attachment att : attachments) {
/*     */       
/* 247 */       DataHandler dh = att.asDataHandler();
/* 248 */       if (dh instanceof StreamingDataHandler) {
/* 249 */         StreamingDataHandler sdh = (StreamingDataHandler)dh;
/*     */         
/* 251 */         if (sdh.getHrefCid() != null) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 256 */       writeln("--" + boundary, out);
/* 257 */       writeMimeHeaders(att.getContentType(), att.getContentId(), out);
/* 258 */       att.writeTo(out);
/* 259 */       writeln(out);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 265 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public MtomCodec copy() {
/* 270 */     return new MtomCodec(this.version, (StreamSOAPCodec)this.codec.copy(), this.features);
/*     */   }
/*     */   
/*     */   private static String encodeCid() {
/* 274 */     String cid = "example.jaxws.sun.com";
/* 275 */     String name = UUID.randomUUID() + "@";
/* 276 */     return name + cid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 283 */     String charset = null;
/* 284 */     String ct = mpp.getRootPart().getContentType();
/* 285 */     if (ct != null) {
/* 286 */       charset = (new ContentTypeImpl(ct)).getCharSet();
/*     */     }
/* 288 */     if (charset != null && !Charset.isSupported(charset)) {
/* 289 */       throw new UnsupportedMediaException(charset);
/*     */     }
/*     */     
/* 292 */     if (charset != null) {
/* 293 */       packet.invocationProperties.put("decodedMessageCharset", charset);
/*     */     } else {
/* 295 */       packet.invocationProperties.remove("decodedMessageCharset");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     MtomXMLStreamReaderEx mtomXMLStreamReaderEx = new MtomXMLStreamReaderEx(mpp, XMLStreamReaderFactory.create(null, mpp.getRootPart().asInputStream(), charset, true));
/*     */ 
/*     */     
/* 304 */     packet.setMessage(this.codec.decode((XMLStreamReader)mtomXMLStreamReaderEx, (AttachmentSet)new MimeAttachmentSet(mpp)));
/* 305 */     packet.setMtomFeature(this.mtomFeature);
/* 306 */     packet.setContentType((ContentType)mpp.getContentType());
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPacketEncoding(Packet packet) {
/* 311 */     if (this.sf != null && this.sf.getEncoding() != null) {
/* 312 */       return this.sf.getEncoding().equals("") ? "utf-8" : this.sf.getEncoding();
/*     */     }
/* 314 */     return determinePacketEncoding(packet);
/*     */   }
/*     */   
/*     */   public static String determinePacketEncoding(Packet packet) {
/* 318 */     if (packet != null && packet.endpoint != null) {
/*     */       
/* 320 */       String charset = (String)packet.invocationProperties.get("decodedMessageCharset");
/* 321 */       return (charset == null) ? "utf-8" : charset;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 326 */     return "utf-8";
/*     */   }
/*     */   
/*     */   public static class MtomStreamWriterImpl extends XMLStreamWriterFilter implements XMLStreamWriterEx, MtomStreamWriter, HasEncoding {
/*     */     private final List<MtomCodec.ByteArrayBuffer> mtomAttachments;
/*     */     private final String boundary;
/*     */     private final MTOMFeature myMtomFeature;
/*     */     
/*     */     public MtomStreamWriterImpl(XMLStreamWriter w, List<MtomCodec.ByteArrayBuffer> mtomAttachments, String b, MTOMFeature myMtomFeature) {
/* 335 */       super(w);
/* 336 */       this.mtomAttachments = mtomAttachments;
/* 337 */       this.boundary = b;
/* 338 */       this.myMtomFeature = myMtomFeature;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeBinary(byte[] data, int start, int len, String contentType) throws XMLStreamException {
/* 344 */       if (this.myMtomFeature.getThreshold() > len) {
/* 345 */         writeCharacters(DatatypeConverterImpl._printBase64Binary(data, start, len));
/*     */         return;
/*     */       } 
/* 348 */       MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(new DataHandler((DataSource)new ByteArrayDataSource(data, start, len, contentType)), this.boundary);
/* 349 */       writeBinary(bab);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeBinary(DataHandler dataHandler) throws XMLStreamException {
/* 355 */       writeBinary(new MtomCodec.ByteArrayBuffer(dataHandler, this.boundary));
/*     */     }
/*     */ 
/*     */     
/*     */     public OutputStream writeBinary(String contentType) throws XMLStreamException {
/* 360 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePCDATA(CharSequence data) throws XMLStreamException {
/* 365 */       if (data == null)
/*     */         return; 
/* 367 */       if (data instanceof Base64Data) {
/* 368 */         Base64Data binaryData = (Base64Data)data;
/* 369 */         writeBinary(binaryData.getDataHandler());
/*     */         return;
/*     */       } 
/* 372 */       writeCharacters(data.toString());
/*     */     }
/*     */     
/*     */     private void writeBinary(MtomCodec.ByteArrayBuffer bab) {
/*     */       try {
/* 377 */         this.mtomAttachments.add(bab);
/* 378 */         String prefix = this.writer.getPrefix("http://www.w3.org/2004/08/xop/include");
/* 379 */         if (prefix == null || !prefix.equals("xop")) {
/* 380 */           this.writer.setPrefix("xop", "http://www.w3.org/2004/08/xop/include");
/* 381 */           this.writer.writeNamespace("xop", "http://www.w3.org/2004/08/xop/include");
/*     */         } 
/* 383 */         this.writer.writeStartElement("http://www.w3.org/2004/08/xop/include", "Include");
/* 384 */         this.writer.writeAttribute("href", "cid:" + bab.contentId);
/* 385 */         this.writer.writeEndElement();
/* 386 */         this.writer.flush();
/* 387 */       } catch (XMLStreamException e) {
/* 388 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getProperty(String name) throws IllegalArgumentException {
/* 395 */       if (name.equals("sjsxp-outputstream") && this.writer instanceof Map) {
/* 396 */         Object obj = ((Map)this.writer).get("sjsxp-outputstream");
/* 397 */         if (obj != null) {
/* 398 */           return obj;
/*     */         }
/*     */       } 
/* 401 */       return super.getProperty(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttachmentMarshaller getAttachmentMarshaller() {
/* 411 */       return new AttachmentMarshaller()
/*     */         {
/*     */ 
/*     */           
/*     */           public String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName)
/*     */           {
/* 417 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(data, MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 418 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 419 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName) {
/* 425 */             if (MtomCodec.MtomStreamWriterImpl.this.myMtomFeature.getThreshold() > length) {
/* 426 */               return null;
/*     */             }
/* 428 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(new DataHandler((DataSource)new ByteArrayDataSource(data, offset, length, mimeType)), MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 429 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 430 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */           
/*     */           public String addSwaRefAttachment(DataHandler data) {
/* 435 */             MtomCodec.ByteArrayBuffer bab = new MtomCodec.ByteArrayBuffer(data, MtomCodec.MtomStreamWriterImpl.this.boundary);
/* 436 */             MtomCodec.MtomStreamWriterImpl.this.mtomAttachments.add(bab);
/* 437 */             return "cid:" + bab.contentId;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isXOPPackage() {
/* 442 */             return true;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public List<MtomCodec.ByteArrayBuffer> getMtomAttachments() {
/* 448 */       return this.mtomAttachments;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getEncoding() {
/* 453 */       return XMLStreamWriterUtil.getEncoding(this.writer);
/*     */     }
/*     */     
/*     */     private static class MtomNamespaceContextEx implements NamespaceContextEx {
/*     */       private final NamespaceContext nsContext;
/*     */       
/*     */       public MtomNamespaceContextEx(NamespaceContext nsContext) {
/* 460 */         this.nsContext = nsContext;
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<NamespaceContextEx.Binding> iterator() {
/* 465 */         throw new UnsupportedOperationException();
/*     */       }
/*     */ 
/*     */       
/*     */       public String getNamespaceURI(String prefix) {
/* 470 */         return this.nsContext.getNamespaceURI(prefix);
/*     */       }
/*     */ 
/*     */       
/*     */       public String getPrefix(String namespaceURI) {
/* 475 */         return this.nsContext.getPrefix(namespaceURI);
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator getPrefixes(String namespaceURI) {
/* 480 */         return this.nsContext.getPrefixes(namespaceURI);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public NamespaceContextEx getNamespaceContext() {
/* 486 */       NamespaceContext nsContext = this.writer.getNamespaceContext();
/* 487 */       return new MtomNamespaceContextEx(nsContext);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MtomXMLStreamReaderEx
/*     */     extends XMLStreamReaderFilter
/*     */     implements XMLStreamReaderEx
/*     */   {
/*     */     private final MimeMultipartParser mimeMP;
/*     */     
/*     */     private boolean xopReferencePresent = false;
/*     */     
/*     */     private Base64Data base64AttData;
/*     */     
/*     */     private char[] base64EncodedText;
/*     */     private String xopHref;
/*     */     
/*     */     public MtomXMLStreamReaderEx(MimeMultipartParser mimeMP, XMLStreamReader reader) {
/* 506 */       super(reader);
/* 507 */       this.mimeMP = mimeMP;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSequence getPCDATA() throws XMLStreamException {
/* 512 */       if (this.xopReferencePresent) {
/* 513 */         return (CharSequence)this.base64AttData;
/*     */       }
/* 515 */       return this.reader.getText();
/*     */     }
/*     */ 
/*     */     
/*     */     public NamespaceContextEx getNamespaceContext() {
/* 520 */       return (NamespaceContextEx)new NamespaceContextExAdaper(this.reader.getNamespaceContext());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getElementTextTrim() throws XMLStreamException {
/* 525 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextLength() {
/* 530 */       if (this.xopReferencePresent) {
/* 531 */         return this.base64AttData.length();
/*     */       }
/* 533 */       return this.reader.getTextLength();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextStart() {
/* 538 */       if (this.xopReferencePresent) {
/* 539 */         return 0;
/*     */       }
/* 541 */       return this.reader.getTextStart();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEventType() {
/* 546 */       if (this.xopReferencePresent)
/* 547 */         return 4; 
/* 548 */       return super.getEventType();
/*     */     }
/*     */ 
/*     */     
/*     */     public int next() throws XMLStreamException {
/* 553 */       int event = this.reader.next();
/* 554 */       if (event == 1 && this.reader.getLocalName().equals("Include") && this.reader.getNamespaceURI().equals("http://www.w3.org/2004/08/xop/include")) {
/*     */         
/* 556 */         String href = this.reader.getAttributeValue(null, "href");
/*     */         try {
/* 558 */           this.xopHref = href;
/* 559 */           Attachment att = getAttachment(href);
/* 560 */           if (att != null) {
/* 561 */             DataHandler dh = att.asDataHandler();
/* 562 */             if (dh instanceof StreamingDataHandler) {
/* 563 */               ((StreamingDataHandler)dh).setHrefCid(att.getContentId());
/*     */             }
/* 565 */             this.base64AttData = new Base64Data();
/* 566 */             this.base64AttData.set(dh);
/*     */           } 
/* 568 */           this.xopReferencePresent = true;
/* 569 */         } catch (IOException e) {
/* 570 */           throw new WebServiceException(e);
/*     */         } 
/*     */         
/* 573 */         XMLStreamReaderUtil.nextElementContent(this.reader);
/* 574 */         return 4;
/*     */       } 
/* 576 */       if (this.xopReferencePresent) {
/* 577 */         this.xopReferencePresent = false;
/* 578 */         this.base64EncodedText = null;
/* 579 */         this.xopHref = null;
/*     */       } 
/* 581 */       return event;
/*     */     }
/*     */     
/*     */     private String decodeCid(String cid) {
/*     */       try {
/* 586 */         cid = URLDecoder.decode(cid, "utf-8");
/* 587 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */       
/* 590 */       return cid;
/*     */     }
/*     */     
/*     */     private Attachment getAttachment(String cid) throws IOException {
/* 594 */       if (cid.startsWith("cid:"))
/* 595 */         cid = cid.substring(4, cid.length()); 
/* 596 */       if (cid.indexOf('%') != -1) {
/* 597 */         cid = decodeCid(cid);
/* 598 */         return this.mimeMP.getAttachmentPart(cid);
/*     */       } 
/* 600 */       return this.mimeMP.getAttachmentPart(cid);
/*     */     }
/*     */ 
/*     */     
/*     */     public char[] getTextCharacters() {
/* 605 */       if (this.xopReferencePresent) {
/* 606 */         char[] chars = new char[this.base64AttData.length()];
/* 607 */         this.base64AttData.writeTo(chars, 0);
/* 608 */         return chars;
/*     */       } 
/* 610 */       return this.reader.getTextCharacters();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 615 */       if (this.xopReferencePresent) {
/* 616 */         if (target == null) {
/* 617 */           throw new NullPointerException("target char array can't be null");
/*     */         }
/*     */         
/* 620 */         if (targetStart < 0 || length < 0 || sourceStart < 0 || targetStart >= target.length || targetStart + length > target.length)
/*     */         {
/* 622 */           throw new IndexOutOfBoundsException();
/*     */         }
/*     */         
/* 625 */         int textLength = this.base64AttData.length();
/* 626 */         if (sourceStart > textLength) {
/* 627 */           throw new IndexOutOfBoundsException();
/*     */         }
/* 629 */         if (this.base64EncodedText == null) {
/* 630 */           this.base64EncodedText = new char[this.base64AttData.length()];
/* 631 */           this.base64AttData.writeTo(this.base64EncodedText, 0);
/*     */         } 
/*     */         
/* 634 */         int copiedLength = Math.min(textLength - sourceStart, length);
/* 635 */         System.arraycopy(this.base64EncodedText, sourceStart, target, targetStart, copiedLength);
/* 636 */         return copiedLength;
/*     */       } 
/* 638 */       return this.reader.getTextCharacters(sourceStart, target, targetStart, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getText() {
/* 643 */       if (this.xopReferencePresent) {
/* 644 */         return this.base64AttData.toString();
/*     */       }
/* 646 */       return this.reader.getText();
/*     */     }
/*     */     
/*     */     protected boolean isXopReference() throws XMLStreamException {
/* 650 */       return this.xopReferencePresent;
/*     */     }
/*     */     
/*     */     protected String getXopHref() {
/* 654 */       return this.xopHref;
/*     */     }
/*     */     
/*     */     public MimeMultipartParser getMimeMultipartParser() {
/* 658 */       return this.mimeMP;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/MtomCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */