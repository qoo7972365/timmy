/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.ContentType;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;
/*     */ import com.sun.xml.internal.ws.api.fastinfoset.FastInfosetFeature;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.pipe.SOAPBindingCodec;
/*     */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.client.ContentNegotiation;
/*     */ import com.sun.xml.internal.ws.protocol.soap.MessageCreationException;
/*     */ import com.sun.xml.internal.ws.resources.StreamingMessages;
/*     */ import com.sun.xml.internal.ws.server.UnsupportedMediaException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public class SOAPBindingCodec
/*     */   extends MimeCodec
/*     */   implements SOAPBindingCodec
/*     */ {
/*     */   public static final String UTF8_ENCODING = "utf-8";
/*     */   public static final String DEFAULT_ENCODING = "utf-8";
/*     */   private boolean isFastInfosetDisabled;
/*     */   private boolean useFastInfosetForEncoding;
/*     */   private boolean ignoreContentNegotiationProperty;
/*     */   private final StreamSOAPCodec xmlSoapCodec;
/*     */   private final Codec fiSoapCodec;
/*     */   private final MimeCodec xmlMtomCodec;
/*     */   private final MimeCodec xmlSwaCodec;
/*     */   private final MimeCodec fiSwaCodec;
/*     */   private final String xmlMimeType;
/*     */   private final String fiMimeType;
/*     */   private final String xmlAccept;
/*     */   private final String connegXmlAccept;
/*     */   
/*     */   public StreamSOAPCodec getXMLCodec() {
/* 134 */     return this.xmlSoapCodec;
/*     */   }
/*     */   
/*     */   private ContentTypeImpl setAcceptHeader(Packet p, ContentTypeImpl c) {
/*     */     String _accept;
/* 139 */     if (!this.ignoreContentNegotiationProperty && p.contentNegotiation != ContentNegotiation.none) {
/* 140 */       _accept = this.connegXmlAccept;
/*     */     } else {
/* 142 */       _accept = this.xmlAccept;
/*     */     } 
/* 144 */     c.setAcceptHeader(_accept);
/* 145 */     return c;
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec(WSFeatureList features) {
/* 149 */     this(features, Codecs.createSOAPEnvelopeXmlCodec(features));
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec(WSFeatureList features, StreamSOAPCodec xmlSoapCodec) {
/* 153 */     super(WebServiceFeatureList.getSoapVersion(features), features);
/*     */     
/* 155 */     this.xmlSoapCodec = xmlSoapCodec;
/* 156 */     this.xmlMimeType = xmlSoapCodec.getMimeType();
/*     */     
/* 158 */     this.xmlMtomCodec = new MtomCodec(this.version, xmlSoapCodec, features);
/*     */     
/* 160 */     this.xmlSwaCodec = new SwACodec(this.version, features, (Codec)xmlSoapCodec);
/*     */ 
/*     */     
/* 163 */     String clientAcceptedContentTypes = xmlSoapCodec.getMimeType() + ", " + this.xmlMtomCodec.getMimeType();
/*     */     
/* 165 */     WebServiceFeature fi = features.get(FastInfosetFeature.class);
/* 166 */     this.isFastInfosetDisabled = (fi != null && !fi.isEnabled());
/* 167 */     if (!this.isFastInfosetDisabled) {
/* 168 */       this.fiSoapCodec = getFICodec(xmlSoapCodec, this.version);
/* 169 */       if (this.fiSoapCodec != null) {
/* 170 */         this.fiMimeType = this.fiSoapCodec.getMimeType();
/* 171 */         this.fiSwaCodec = new SwACodec(this.version, features, this.fiSoapCodec);
/* 172 */         this.connegXmlAccept = this.fiMimeType + ", " + clientAcceptedContentTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         WebServiceFeature select = features.get(SelectOptimalEncodingFeature.class);
/* 181 */         if (select != null) {
/* 182 */           this.ignoreContentNegotiationProperty = true;
/* 183 */           if (select.isEnabled()) {
/*     */             
/* 185 */             if (fi != null) {
/* 186 */               this.useFastInfosetForEncoding = true;
/*     */             }
/*     */             
/* 189 */             clientAcceptedContentTypes = this.connegXmlAccept;
/*     */           } else {
/* 191 */             this.isFastInfosetDisabled = true;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 196 */         this.isFastInfosetDisabled = true;
/* 197 */         this.fiSwaCodec = null;
/* 198 */         this.fiMimeType = "";
/* 199 */         this.connegXmlAccept = clientAcceptedContentTypes;
/* 200 */         this.ignoreContentNegotiationProperty = true;
/*     */       } 
/*     */     } else {
/*     */       
/* 204 */       this.fiSoapCodec = this.fiSwaCodec = null;
/* 205 */       this.fiMimeType = "";
/* 206 */       this.connegXmlAccept = clientAcceptedContentTypes;
/* 207 */       this.ignoreContentNegotiationProperty = true;
/*     */     } 
/*     */     
/* 210 */     this.xmlAccept = clientAcceptedContentTypes;
/*     */     
/* 212 */     if (WebServiceFeatureList.getSoapVersion(features) == null)
/* 213 */       throw new WebServiceException("Expecting a SOAP binding but found "); 
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/* 217 */     return null;
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 221 */     ContentType toAdapt = getEncoder(packet).getStaticContentType(packet);
/* 222 */     return setAcceptHeader(packet, (ContentTypeImpl)toAdapt);
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 226 */     preEncode(packet);
/* 227 */     ContentType ct = getEncoder(packet).encode(packet, out);
/* 228 */     ct = setAcceptHeader(packet, (ContentTypeImpl)ct);
/* 229 */     postEncode();
/* 230 */     return ct;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 234 */     preEncode(packet);
/* 235 */     ContentType ct = getEncoder(packet).encode(packet, buffer);
/* 236 */     ct = setAcceptHeader(packet, (ContentTypeImpl)ct);
/* 237 */     postEncode();
/* 238 */     return ct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preEncode(Packet p) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postEncode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preDecode(Packet p) {
/* 260 */     if (p.contentNegotiation == null) {
/* 261 */       this.useFastInfosetForEncoding = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void postDecode(Packet p) {
/* 269 */     p.setFastInfosetDisabled(this.isFastInfosetDisabled);
/* 270 */     if (this.features.isEnabled(MTOMFeature.class)) p.checkMtomAcceptable();
/*     */     
/* 272 */     MTOMFeature mtomFeature = (MTOMFeature)this.features.get(MTOMFeature.class);
/* 273 */     if (mtomFeature != null) {
/* 274 */       p.setMtomFeature(mtomFeature);
/*     */     }
/* 276 */     if (!this.useFastInfosetForEncoding) {
/* 277 */       this.useFastInfosetForEncoding = p.getFastInfosetAcceptable(this.fiMimeType).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 283 */     if (contentType == null) {
/* 284 */       contentType = this.xmlMimeType;
/*     */     }
/* 286 */     packet.setContentType((ContentType)new ContentTypeImpl(contentType));
/* 287 */     preDecode(packet);
/*     */     try {
/* 289 */       if (isMultipartRelated(contentType))
/*     */       
/* 291 */       { super.decode(in, contentType, packet); }
/* 292 */       else if (isFastInfoset(contentType))
/* 293 */       { if (!this.ignoreContentNegotiationProperty && packet.contentNegotiation == ContentNegotiation.none) {
/* 294 */           throw noFastInfosetForDecoding();
/*     */         }
/* 296 */         this.useFastInfosetForEncoding = true;
/* 297 */         this.fiSoapCodec.decode(in, contentType, packet); }
/*     */       else
/* 299 */       { this.xmlSoapCodec.decode(in, contentType, packet); } 
/* 300 */     } catch (RuntimeException we) {
/* 301 */       if (we instanceof com.sun.xml.internal.ws.api.message.ExceptionHasMessage || we instanceof UnsupportedMediaException) {
/* 302 */         throw we;
/*     */       }
/* 304 */       throw new MessageCreationException(this.version, new Object[] { we });
/*     */     } 
/*     */     
/* 307 */     postDecode(packet);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 311 */     if (contentType == null) {
/* 312 */       throw new UnsupportedMediaException();
/*     */     }
/*     */     
/* 315 */     preDecode(packet);
/*     */     try {
/* 317 */       if (isMultipartRelated(contentType))
/* 318 */       { super.decode(in, contentType, packet); }
/* 319 */       else if (isFastInfoset(contentType))
/* 320 */       { if (packet.contentNegotiation == ContentNegotiation.none) {
/* 321 */           throw noFastInfosetForDecoding();
/*     */         }
/* 323 */         this.useFastInfosetForEncoding = true;
/* 324 */         this.fiSoapCodec.decode(in, contentType, packet); }
/*     */       else
/* 326 */       { this.xmlSoapCodec.decode(in, contentType, packet); } 
/* 327 */     } catch (RuntimeException we) {
/* 328 */       if (we instanceof com.sun.xml.internal.ws.api.message.ExceptionHasMessage || we instanceof UnsupportedMediaException) {
/* 329 */         throw we;
/*     */       }
/* 331 */       throw new MessageCreationException(this.version, new Object[] { we });
/*     */     } 
/*     */     
/* 334 */     postDecode(packet);
/*     */   }
/*     */   
/*     */   public SOAPBindingCodec copy() {
/* 338 */     return new SOAPBindingCodec(this.features, (StreamSOAPCodec)this.xmlSoapCodec.copy());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
/* 344 */     String rootContentType = mpp.getRootPart().getContentType();
/* 345 */     boolean isMTOM = isApplicationXopXml(rootContentType);
/* 346 */     packet.setMtomRequest(Boolean.valueOf(isMTOM));
/* 347 */     if (isMTOM) {
/* 348 */       this.xmlMtomCodec.decode(mpp, packet);
/* 349 */     } else if (isFastInfoset(rootContentType)) {
/* 350 */       if (packet.contentNegotiation == ContentNegotiation.none) {
/* 351 */         throw noFastInfosetForDecoding();
/*     */       }
/* 353 */       this.useFastInfosetForEncoding = true;
/* 354 */       this.fiSwaCodec.decode(mpp, packet);
/* 355 */     } else if (isXml(rootContentType)) {
/* 356 */       this.xmlSwaCodec.decode(mpp, packet);
/*     */     } else {
/*     */       
/* 359 */       throw new IOException("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMultipartRelated(String contentType) {
/* 365 */     return compareStrings(contentType, "multipart/related");
/*     */   }
/*     */   
/*     */   private boolean isApplicationXopXml(String contentType) {
/* 369 */     return compareStrings(contentType, "application/xop+xml");
/*     */   }
/*     */   
/*     */   private boolean isXml(String contentType) {
/* 373 */     return compareStrings(contentType, this.xmlMimeType);
/*     */   }
/*     */   
/*     */   private boolean isFastInfoset(String contentType) {
/* 377 */     if (this.isFastInfosetDisabled) return false;
/*     */     
/* 379 */     return compareStrings(contentType, this.fiMimeType);
/*     */   }
/*     */   
/*     */   private boolean compareStrings(String a, String b) {
/* 383 */     if (a.length() >= b.length()) if (b
/* 384 */         .equalsIgnoreCase(a
/* 385 */           .substring(0, b
/* 386 */             .length())));
/*     */     
/*     */     return false;
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
/*     */   private Codec getEncoder(Packet p) {
/* 427 */     if (!this.ignoreContentNegotiationProperty) {
/* 428 */       if (p.contentNegotiation == ContentNegotiation.none) {
/*     */ 
/*     */         
/* 431 */         this.useFastInfosetForEncoding = false;
/* 432 */       } else if (p.contentNegotiation == ContentNegotiation.optimistic) {
/*     */         
/* 434 */         this.useFastInfosetForEncoding = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 440 */     if (this.useFastInfosetForEncoding) {
/* 441 */       Message message = p.getMessage();
/* 442 */       if (message == null || message.getAttachments().isEmpty() || this.features.isEnabled(MTOMFeature.class)) {
/* 443 */         return this.fiSoapCodec;
/*     */       }
/* 445 */       return this.fiSwaCodec;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 450 */     if (p.getBinding() == null && 
/* 451 */       this.features != null) {
/* 452 */       p.setMtomFeature((MTOMFeature)this.features.get(MTOMFeature.class));
/*     */     }
/*     */ 
/*     */     
/* 456 */     if (p.shouldUseMtom()) {
/* 457 */       return this.xmlMtomCodec;
/*     */     }
/*     */     
/* 460 */     Message m = p.getMessage();
/* 461 */     if (m == null || m.getAttachments().isEmpty()) {
/* 462 */       return (Codec)this.xmlSoapCodec;
/*     */     }
/* 464 */     return this.xmlSwaCodec;
/*     */   }
/*     */   
/*     */   private RuntimeException noFastInfosetForDecoding() {
/* 468 */     return new RuntimeException(StreamingMessages.FASTINFOSET_DECODING_NOT_ACCEPTED());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codec getFICodec(StreamSOAPCodec soapCodec, SOAPVersion version) {
/*     */     try {
/* 476 */       Class<?> c = Class.forName("com.sun.xml.internal.ws.encoding.fastinfoset.FastInfosetStreamSOAPCodec");
/* 477 */       Method m = c.getMethod("create", new Class[] { StreamSOAPCodec.class, SOAPVersion.class });
/* 478 */       return (Codec)m.invoke(null, new Object[] { soapCodec, version });
/* 479 */     } catch (Exception e) {
/*     */       
/* 481 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/SOAPBindingCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */