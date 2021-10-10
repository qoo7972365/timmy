/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.ContentType;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.client.ContentNegotiation;
/*     */ import com.sun.xml.internal.ws.encoding.xml.XMLCodec;
/*     */ import com.sun.xml.internal.ws.encoding.xml.XMLMessage;
/*     */ import com.sun.xml.internal.ws.resources.StreamingMessages;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.activation.DataSource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLHTTPBindingCodec
/*     */   extends MimeCodec
/*     */ {
/*     */   private static final String BASE_ACCEPT_VALUE = "*";
/*     */   private static final String APPLICATION_FAST_INFOSET_MIME_TYPE = "application/fastinfoset";
/*     */   private boolean useFastInfosetForEncoding;
/*     */   private final Codec xmlCodec;
/*     */   private final Codec fiCodec;
/*  91 */   private static final String xmlAccept = null;
/*     */ 
/*     */   
/*     */   private static final String fiXmlAccept = "application/fastinfoset, *";
/*     */ 
/*     */ 
/*     */   
/*     */   private ContentTypeImpl setAcceptHeader(Packet p, ContentType c) {
/*  99 */     ContentTypeImpl ctImpl = (ContentTypeImpl)c;
/* 100 */     if (p.contentNegotiation == ContentNegotiation.optimistic || p.contentNegotiation == ContentNegotiation.pessimistic) {
/*     */       
/* 102 */       ctImpl.setAcceptHeader("application/fastinfoset, *");
/*     */     } else {
/* 104 */       ctImpl.setAcceptHeader(xmlAccept);
/*     */     } 
/* 106 */     p.setContentType((ContentType)ctImpl);
/* 107 */     return ctImpl;
/*     */   }
/*     */   
/*     */   public XMLHTTPBindingCodec(WSFeatureList f) {
/* 111 */     super(SOAPVersion.SOAP_11, f);
/*     */     
/* 113 */     this.xmlCodec = (Codec)new XMLCodec(f);
/*     */     
/* 115 */     this.fiCodec = getFICodec();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/* 126 */     if (packet.getInternalMessage() instanceof XMLMessage.MessageDataSource) {
/* 127 */       XMLMessage.MessageDataSource mds = (XMLMessage.MessageDataSource)packet.getInternalMessage();
/* 128 */       if (mds.hasUnconsumedDataSource()) {
/* 129 */         ContentType contentType = getStaticContentType(mds);
/* 130 */         return (contentType != null) ? 
/* 131 */           setAcceptHeader(packet, contentType) : null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 136 */     ContentType ct = super.getStaticContentType(packet);
/* 137 */     return (ct != null) ? 
/* 138 */       setAcceptHeader(packet, ct) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 144 */     if (packet.getInternalMessage() instanceof XMLMessage.MessageDataSource) {
/* 145 */       XMLMessage.MessageDataSource mds = (XMLMessage.MessageDataSource)packet.getInternalMessage();
/* 146 */       if (mds.hasUnconsumedDataSource()) {
/* 147 */         return setAcceptHeader(packet, encode(mds, out));
/*     */       }
/*     */     } 
/* 150 */     return setAcceptHeader(packet, super.encode(packet, out));
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 164 */     if (packet.contentNegotiation == null) {
/* 165 */       this.useFastInfosetForEncoding = false;
/*     */     }
/* 167 */     if (contentType == null) {
/* 168 */       this.xmlCodec.decode(in, contentType, packet);
/* 169 */     } else if (isMultipartRelated(contentType)) {
/* 170 */       packet.setMessage((Message)new XMLMessage.XMLMultiPart(contentType, in, this.features));
/* 171 */     } else if (isFastInfoset(contentType)) {
/* 172 */       if (this.fiCodec == null) {
/* 173 */         throw new RuntimeException(StreamingMessages.FASTINFOSET_NO_IMPLEMENTATION());
/*     */       }
/*     */       
/* 176 */       this.useFastInfosetForEncoding = true;
/* 177 */       this.fiCodec.decode(in, contentType, packet);
/* 178 */     } else if (isXml(contentType)) {
/* 179 */       this.xmlCodec.decode(in, contentType, packet);
/*     */     } else {
/* 181 */       packet.setMessage((Message)new XMLMessage.UnknownContent(contentType, in));
/*     */     } 
/*     */     
/* 184 */     if (!this.useFastInfosetForEncoding) {
/* 185 */       this.useFastInfosetForEncoding = isFastInfosetAcceptable(packet.acceptableMimeTypes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeCodec copy() {
/* 196 */     return new XMLHTTPBindingCodec(this.features);
/*     */   }
/*     */   
/*     */   private boolean isMultipartRelated(String contentType) {
/* 200 */     return compareStrings(contentType, "multipart/related");
/*     */   }
/*     */   
/*     */   private boolean isXml(String contentType) {
/* 204 */     return (compareStrings(contentType, "application/xml") || 
/* 205 */       compareStrings(contentType, "text/xml") || (
/* 206 */       compareStrings(contentType, "application/") && contentType.toLowerCase().indexOf("+xml") != -1));
/*     */   }
/*     */   
/*     */   private boolean isFastInfoset(String contentType) {
/* 210 */     return compareStrings(contentType, "application/fastinfoset");
/*     */   }
/*     */   
/*     */   private boolean compareStrings(String a, String b) {
/* 214 */     if (a.length() >= b.length()) if (b
/* 215 */         .equalsIgnoreCase(a
/* 216 */           .substring(0, b
/* 217 */             .length()))); 
/*     */     return false;
/*     */   }
/*     */   private boolean isFastInfosetAcceptable(String accept) {
/* 221 */     if (accept == null) return false;
/*     */     
/* 223 */     StringTokenizer st = new StringTokenizer(accept, ",");
/* 224 */     while (st.hasMoreTokens()) {
/* 225 */       String token = st.nextToken().trim();
/* 226 */       if (token.equalsIgnoreCase("application/fastinfoset")) {
/* 227 */         return true;
/*     */       }
/*     */     } 
/* 230 */     return false;
/*     */   }
/*     */   
/*     */   private ContentType getStaticContentType(XMLMessage.MessageDataSource mds) {
/* 234 */     String contentType = mds.getDataSource().getContentType();
/* 235 */     boolean isFastInfoset = XMLMessage.isFastInfoset(contentType);
/*     */     
/* 237 */     if (!requiresTransformationOfDataSource(isFastInfoset, this.useFastInfosetForEncoding))
/*     */     {
/* 239 */       return new ContentTypeImpl(contentType);
/*     */     }
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ContentType encode(XMLMessage.MessageDataSource mds, OutputStream out) {
/*     */     try {
/* 247 */       boolean isFastInfoset = XMLMessage.isFastInfoset(mds
/* 248 */           .getDataSource().getContentType());
/* 249 */       DataSource ds = transformDataSource(mds.getDataSource(), isFastInfoset, this.useFastInfosetForEncoding, this.features);
/*     */ 
/*     */       
/* 252 */       InputStream is = ds.getInputStream();
/* 253 */       byte[] buf = new byte[1024];
/*     */       int count;
/* 255 */       while ((count = is.read(buf)) != -1) {
/* 256 */         out.write(buf, 0, count);
/*     */       }
/* 258 */       return new ContentTypeImpl(ds.getContentType());
/* 259 */     } catch (IOException ioe) {
/* 260 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Codec getMimeRootCodec(Packet p) {
/* 271 */     if (p.contentNegotiation == ContentNegotiation.none) {
/*     */ 
/*     */       
/* 274 */       this.useFastInfosetForEncoding = false;
/* 275 */     } else if (p.contentNegotiation == ContentNegotiation.optimistic) {
/*     */       
/* 277 */       this.useFastInfosetForEncoding = true;
/*     */     } 
/*     */     
/* 280 */     return (this.useFastInfosetForEncoding && this.fiCodec != null) ? this.fiCodec : this.xmlCodec;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean requiresTransformationOfDataSource(boolean isFastInfoset, boolean useFastInfoset) {
/* 285 */     return ((isFastInfoset && !useFastInfoset) || (!isFastInfoset && useFastInfoset));
/*     */   }
/*     */ 
/*     */   
/*     */   public static DataSource transformDataSource(DataSource in, boolean isFastInfoset, boolean useFastInfoset, WSFeatureList f) {
/*     */     try {
/* 291 */       if (isFastInfoset && !useFastInfoset) {
/*     */         
/* 293 */         Codec codec = new XMLHTTPBindingCodec(f);
/* 294 */         Packet p = new Packet();
/* 295 */         codec.decode(in.getInputStream(), in.getContentType(), p);
/*     */         
/* 297 */         p.getMessage().getAttachments();
/* 298 */         codec.getStaticContentType(p);
/*     */         
/* 300 */         ByteArrayBuffer bos = new ByteArrayBuffer();
/* 301 */         ContentType ct = codec.encode(p, (OutputStream)bos);
/* 302 */         return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());
/* 303 */       }  if (!isFastInfoset && useFastInfoset) {
/*     */         
/* 305 */         Codec codec = new XMLHTTPBindingCodec(f);
/* 306 */         Packet p = new Packet();
/* 307 */         codec.decode(in.getInputStream(), in.getContentType(), p);
/*     */         
/* 309 */         p.contentNegotiation = ContentNegotiation.optimistic;
/* 310 */         p.getMessage().getAttachments();
/* 311 */         codec.getStaticContentType(p);
/*     */         
/* 313 */         ByteArrayBuffer bos = new ByteArrayBuffer();
/* 314 */         ContentType ct = codec.encode(p, (OutputStream)bos);
/* 315 */         return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());
/*     */       } 
/* 317 */     } catch (Exception ex) {
/* 318 */       throw new WebServiceException(ex);
/*     */     } 
/*     */     
/* 321 */     return in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codec getFICodec() {
/*     */     try {
/* 329 */       Class<?> c = Class.forName("com.sun.xml.internal.ws.encoding.fastinfoset.FastInfosetCodec");
/* 330 */       Method m = c.getMethod("create", new Class[0]);
/* 331 */       return (Codec)m.invoke(null, new Object[0]);
/* 332 */     } catch (Exception e) {
/* 333 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/XMLHTTPBindingCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */