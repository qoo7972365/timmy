/*     */ package com.sun.xml.internal.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferSource;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.util.Set;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StreamHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   protected final XMLStreamBuffer _mark;
/*     */   protected boolean _isMustUnderstand;
/*     */   @NotNull
/*     */   protected String _role;
/*     */   protected boolean _isRelay;
/*     */   protected String _localName;
/*     */   protected String _namespaceURI;
/*     */   private final FinalArrayList<Attribute> attributes;
/*     */   
/*     */   protected static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */     final String localName;
/*     */     final String value;
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/*  90 */       this.nsUri = StreamHeader.fixNull(nsUri);
/*  91 */       this.localName = localName;
/*  92 */       this.value = value;
/*     */     }
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
/*     */   protected StreamHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 116 */     assert reader != null && mark != null;
/* 117 */     this._mark = mark;
/* 118 */     this._localName = reader.getLocalName();
/* 119 */     this._namespaceURI = reader.getNamespaceURI();
/* 120 */     this.attributes = processHeaderAttributes(reader);
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
/*     */   protected StreamHeader(XMLStreamReader reader) throws XMLStreamException {
/* 132 */     this._localName = reader.getLocalName();
/* 133 */     this._namespaceURI = reader.getNamespaceURI();
/* 134 */     this.attributes = processHeaderAttributes(reader);
/*     */     
/* 136 */     this._mark = XMLStreamBuffer.createNewBufferFromXMLStreamReader(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
/* 141 */     if (!this._isMustUnderstand) return true;
/*     */     
/* 143 */     if (roles == null) {
/* 144 */       return true;
/*     */     }
/*     */     
/* 147 */     return !roles.contains(this._role);
/*     */   }
/*     */   @NotNull
/*     */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 151 */     assert this._role != null;
/* 152 */     return this._role;
/*     */   }
/*     */   
/*     */   public boolean isRelay() {
/* 156 */     return this._isRelay;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 160 */     return this._namespaceURI;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 164 */     return this._localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 168 */     if (this.attributes != null)
/* 169 */       for (int i = this.attributes.size() - 1; i >= 0; i--) {
/* 170 */         Attribute a = (Attribute)this.attributes.get(i);
/* 171 */         if (a.localName.equals(localName) && a.nsUri.equals(nsUri)) {
/* 172 */           return a.value;
/*     */         }
/*     */       }  
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 182 */     return (XMLStreamReader)this._mark.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 186 */     if (this._mark.getInscopeNamespaces().size() > 0) {
/* 187 */       this._mark.writeToXMLStreamWriter(w, true);
/*     */     } else {
/* 189 */       this._mark.writeToXMLStreamWriter(w);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 197 */       TransformerFactory tf = XmlUtil.newTransformerFactory();
/* 198 */       Transformer t = tf.newTransformer();
/* 199 */       XMLStreamBufferSource source = new XMLStreamBufferSource(this._mark);
/* 200 */       DOMResult result = new DOMResult();
/* 201 */       t.transform((Source)source, result);
/* 202 */       Node d = result.getNode();
/* 203 */       if (d.getNodeType() == 9)
/* 204 */         d = d.getFirstChild(); 
/* 205 */       SOAPHeader header = saaj.getSOAPHeader();
/* 206 */       if (header == null)
/* 207 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 208 */       Node node = header.getOwnerDocument().importNode(d, true);
/* 209 */       header.appendChild(node);
/* 210 */     } catch (Exception e) {
/* 211 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 216 */     this._mark.writeTo(contentHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
/* 227 */     return new WSEndpointReference(this._mark, expected);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 236 */     if (s == null) return ""; 
/* 237 */     return s;
/*     */   }
/*     */   
/*     */   protected abstract FinalArrayList<Attribute> processHeaderAttributes(XMLStreamReader paramXMLStreamReader);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/stream/StreamHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */