/*     */ package com.sun.xml.internal.ws.encoding.fastinfoset;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.internal.fastinfoset.vocab.ParserVocabulary;
/*     */ import com.sun.xml.internal.fastinfoset.vocab.SerializerVocabulary;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSource;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
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
/*     */ public class FastInfosetCodec
/*     */   implements Codec
/*     */ {
/*     */   private static final int DEFAULT_INDEXED_STRING_SIZE_LIMIT = 32;
/*     */   private static final int DEFAULT_INDEXED_STRING_MEMORY_LIMIT = 4194304;
/*     */   private StAXDocumentParser _parser;
/*     */   private StAXDocumentSerializer _serializer;
/*     */   private final boolean _retainState;
/*     */   private final ContentType _contentType;
/*     */   
/*     */   FastInfosetCodec(boolean retainState) {
/*  70 */     this._retainState = retainState;
/*  71 */     this._contentType = retainState ? (ContentType)new ContentTypeImpl("application/vnd.sun.stateful.fastinfoset") : (ContentType)new ContentTypeImpl("application/fastinfoset");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/*  76 */     return this._contentType.getContentType();
/*     */   }
/*     */   
/*     */   public Codec copy() {
/*  80 */     return new FastInfosetCodec(this._retainState);
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/*  84 */     return this._contentType;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/*  88 */     Message message = packet.getMessage();
/*  89 */     if (message != null && message.hasPayload()) {
/*  90 */       XMLStreamWriter writer = getXMLStreamWriter(out);
/*     */       try {
/*  92 */         writer.writeStartDocument();
/*  93 */         packet.getMessage().writePayloadTo(writer);
/*  94 */         writer.writeEndDocument();
/*  95 */         writer.flush();
/*  96 */       } catch (XMLStreamException e) {
/*  97 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return this._contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/*     */     Message message;
/* 113 */     in = hasSomeData(in);
/* 114 */     if (in != null) {
/* 115 */       message = Messages.createUsingPayload((Source)new FastInfosetSource(in), SOAPVersion.SOAP_11);
/*     */     } else {
/*     */       
/* 118 */       message = Messages.createEmpty(SOAPVersion.SOAP_11);
/*     */     } 
/*     */     
/* 121 */     packet.setMessage(message);
/*     */   }
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet response) {
/* 125 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private XMLStreamWriter getXMLStreamWriter(OutputStream out) {
/* 129 */     if (this._serializer != null) {
/* 130 */       this._serializer.setOutputStream(out);
/* 131 */       return (XMLStreamWriter)this._serializer;
/*     */     } 
/* 133 */     return (XMLStreamWriter)(this._serializer = createNewStreamWriter(out, this._retainState));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastInfosetCodec create() {
/* 143 */     return create(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FastInfosetCodec create(boolean retainState) {
/* 154 */     return new FastInfosetCodec(retainState);
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
/*     */   static StAXDocumentSerializer createNewStreamWriter(OutputStream out, boolean retainState) {
/* 166 */     return createNewStreamWriter(out, retainState, 32, 4194304);
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
/*     */   static StAXDocumentSerializer createNewStreamWriter(OutputStream out, boolean retainState, int indexedStringSizeLimit, int stringsMemoryLimit) {
/* 179 */     StAXDocumentSerializer serializer = new StAXDocumentSerializer(out);
/* 180 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       SerializerVocabulary vocabulary = new SerializerVocabulary();
/* 188 */       serializer.setVocabulary(vocabulary);
/* 189 */       serializer.setMinAttributeValueSize(0);
/* 190 */       serializer.setMaxAttributeValueSize(indexedStringSizeLimit);
/* 191 */       serializer.setMinCharacterContentChunkSize(0);
/* 192 */       serializer.setMaxCharacterContentChunkSize(indexedStringSizeLimit);
/* 193 */       serializer.setAttributeValueMapMemoryLimit(stringsMemoryLimit);
/* 194 */       serializer.setCharacterContentChunkMapMemoryLimit(stringsMemoryLimit);
/*     */     } 
/* 196 */     return serializer;
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
/*     */   static StAXDocumentParser createNewStreamReader(InputStream in, boolean retainState) {
/* 208 */     StAXDocumentParser parser = new StAXDocumentParser(in);
/* 209 */     parser.setStringInterning(true);
/* 210 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       ParserVocabulary vocabulary = new ParserVocabulary();
/* 218 */       parser.setVocabulary(vocabulary);
/*     */     } 
/* 220 */     return parser;
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
/*     */   static StAXDocumentParser createNewStreamReaderRecyclable(InputStream in, boolean retainState) {
/* 232 */     StAXDocumentParser parser = new FastInfosetStreamReaderRecyclable(in);
/* 233 */     parser.setStringInterning(true);
/* 234 */     parser.setForceStreamClose(true);
/* 235 */     if (retainState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       ParserVocabulary vocabulary = new ParserVocabulary();
/* 243 */       parser.setVocabulary(vocabulary);
/*     */     } 
/* 245 */     return parser;
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
/*     */   private static InputStream hasSomeData(InputStream in) throws IOException {
/* 258 */     if (in != null && 
/* 259 */       in.available() < 1) {
/* 260 */       if (!in.markSupported()) {
/* 261 */         in = new BufferedInputStream(in);
/*     */       }
/* 263 */       in.mark(1);
/* 264 */       if (in.read() != -1) {
/* 265 */         in.reset();
/*     */       } else {
/* 267 */         in = null;
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     return in;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/fastinfoset/FastInfosetCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */