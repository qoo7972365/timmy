/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.Module;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.server.WSEndpointImpl;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamWriterFilter;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EPRSDDocumentFilter
/*     */   implements SDDocumentFilter
/*     */ {
/*     */   private final WSEndpointImpl<?> endpoint;
/*     */   List<BoundEndpoint> beList;
/*     */   
/*     */   public EPRSDDocumentFilter(@NotNull WSEndpointImpl<?> endpoint) {
/*  60 */     this.endpoint = endpoint;
/*     */   }
/*     */   @Nullable
/*     */   private WSEndpointImpl<?> getEndpoint(String serviceName, String portName) {
/*  64 */     if (serviceName == null || portName == null)
/*  65 */       return null; 
/*  66 */     if (this.endpoint.getServiceName().getLocalPart().equals(serviceName) && this.endpoint.getPortName().getLocalPart().equals(portName)) {
/*  67 */       return this.endpoint;
/*     */     }
/*  69 */     if (this.beList == null) {
/*     */       
/*  71 */       Module module = (Module)this.endpoint.getContainer().getSPI(Module.class);
/*  72 */       if (module != null) {
/*  73 */         this.beList = module.getBoundEndpoints();
/*     */       } else {
/*  75 */         this.beList = Collections.emptyList();
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     for (BoundEndpoint be : this.beList) {
/*  80 */       WSEndpoint wse = be.getEndpoint();
/*  81 */       if (wse.getServiceName().getLocalPart().equals(serviceName) && wse.getPortName().getLocalPart().equals(portName)) {
/*  82 */         return (WSEndpointImpl)wse;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamWriter filter(SDDocument doc, XMLStreamWriter w) throws XMLStreamException, IOException {
/*  91 */     if (!doc.isWSDL()) {
/*  92 */       return w;
/*     */     }
/*     */     
/*  95 */     return (XMLStreamWriter)new XMLStreamWriterFilter(w)
/*     */       {
/*     */         private boolean eprExtnFilterON = false;
/*     */         private boolean portHasEPR = false;
/*  99 */         private int eprDepth = -1;
/*     */         
/* 101 */         private String serviceName = null;
/*     */         private boolean onService = false;
/* 103 */         private int serviceDepth = -1;
/*     */         
/* 105 */         private String portName = null;
/*     */         private boolean onPort = false;
/* 107 */         private int portDepth = -1;
/*     */         
/*     */         private String portAddress;
/*     */         private boolean onPortAddress = false;
/*     */         
/*     */         private void handleStartElement(String localName, String namespaceURI) throws XMLStreamException {
/* 113 */           resetOnElementFlags();
/* 114 */           if (this.serviceDepth >= 0) {
/* 115 */             this.serviceDepth++;
/*     */           }
/* 117 */           if (this.portDepth >= 0) {
/* 118 */             this.portDepth++;
/*     */           }
/* 120 */           if (this.eprDepth >= 0) {
/* 121 */             this.eprDepth++;
/*     */           }
/*     */           
/* 124 */           if (namespaceURI.equals(WSDLConstants.QNAME_SERVICE.getNamespaceURI()) && localName.equals(WSDLConstants.QNAME_SERVICE.getLocalPart())) {
/* 125 */             this.onService = true;
/* 126 */             this.serviceDepth = 0;
/* 127 */           } else if (namespaceURI.equals(WSDLConstants.QNAME_PORT.getNamespaceURI()) && localName.equals(WSDLConstants.QNAME_PORT.getLocalPart())) {
/* 128 */             if (this.serviceDepth >= 1) {
/* 129 */               this.onPort = true;
/* 130 */               this.portDepth = 0;
/*     */             } 
/* 132 */           } else if (namespaceURI.equals("http://www.w3.org/2005/08/addressing") && localName.equals("EndpointReference")) {
/* 133 */             if (this.serviceDepth >= 1 && this.portDepth >= 1) {
/* 134 */               this.portHasEPR = true;
/* 135 */               this.eprDepth = 0;
/*     */             } 
/* 137 */           } else if ((namespaceURI.equals(WSDLConstants.NS_SOAP_BINDING_ADDRESS.getNamespaceURI()) || namespaceURI.equals(WSDLConstants.NS_SOAP12_BINDING_ADDRESS.getNamespaceURI())) && localName
/* 138 */             .equals("address") && this.portDepth == 1) {
/* 139 */             this.onPortAddress = true;
/*     */           } 
/* 141 */           WSEndpointImpl wSEndpointImpl = EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName);
/*     */ 
/*     */           
/* 144 */           if (wSEndpointImpl != null && 
/* 145 */             this.eprDepth == 1 && !namespaceURI.equals("http://www.w3.org/2005/08/addressing"))
/*     */           {
/* 147 */             this.eprExtnFilterON = true;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         private void resetOnElementFlags() {
/* 160 */           if (this.onService) {
/* 161 */             this.onService = false;
/*     */           }
/* 163 */           if (this.onPort) {
/* 164 */             this.onPort = false;
/*     */           }
/* 166 */           if (this.onPortAddress) {
/* 167 */             this.onPortAddress = false;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private void writeEPRExtensions(Collection<WSEndpointReference.EPRExtension> eprExtns) throws XMLStreamException {
/* 174 */           if (eprExtns != null) {
/* 175 */             for (WSEndpointReference.EPRExtension e : eprExtns) {
/* 176 */               XMLStreamReaderToXMLStreamWriter c = new XMLStreamReaderToXMLStreamWriter();
/* 177 */               XMLStreamReader r = e.readAsXMLStreamReader();
/* 178 */               c.bridge(r, this.writer);
/* 179 */               XMLStreamReaderFactory.recycle(r);
/*     */             } 
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 186 */           handleStartElement(localName, namespaceURI);
/* 187 */           if (!this.eprExtnFilterON) {
/* 188 */             super.writeStartElement(prefix, localName, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 194 */           handleStartElement(localName, namespaceURI);
/* 195 */           if (!this.eprExtnFilterON) {
/* 196 */             super.writeStartElement(namespaceURI, localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String localName) throws XMLStreamException {
/* 202 */           if (!this.eprExtnFilterON) {
/* 203 */             super.writeStartElement(localName);
/*     */           }
/*     */         }
/*     */         
/*     */         private void handleEndElement() throws XMLStreamException {
/* 208 */           resetOnElementFlags();
/*     */           
/* 210 */           if (this.portDepth == 0)
/*     */           {
/* 212 */             if (!this.portHasEPR && EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName) != null) {
/*     */ 
/*     */               
/* 215 */               this.writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "EndpointReference", AddressingVersion.W3C.nsUri);
/* 216 */               this.writer.writeNamespace(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.nsUri);
/* 217 */               this.writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.address, AddressingVersion.W3C.nsUri);
/* 218 */               this.writer.writeCharacters(this.portAddress);
/* 219 */               this.writer.writeEndElement();
/* 220 */               writeEPRExtensions(EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName).getEndpointReferenceExtensions());
/* 221 */               this.writer.writeEndElement();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/* 226 */           if (this.eprDepth == 0) {
/* 227 */             if (this.portHasEPR && EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName) != null) {
/* 228 */               writeEPRExtensions(EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName).getEndpointReferenceExtensions());
/*     */             }
/* 230 */             this.eprExtnFilterON = false;
/*     */           } 
/*     */           
/* 233 */           if (this.serviceDepth >= 0) {
/* 234 */             this.serviceDepth--;
/*     */           }
/* 236 */           if (this.portDepth >= 0) {
/* 237 */             this.portDepth--;
/*     */           }
/* 239 */           if (this.eprDepth >= 0) {
/* 240 */             this.eprDepth--;
/*     */           }
/*     */           
/* 243 */           if (this.serviceDepth == -1) {
/* 244 */             this.serviceName = null;
/*     */           }
/* 246 */           if (this.portDepth == -1) {
/* 247 */             this.portHasEPR = false;
/* 248 */             this.portAddress = null;
/* 249 */             this.portName = null;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEndElement() throws XMLStreamException {
/* 255 */           handleEndElement();
/* 256 */           if (!this.eprExtnFilterON) {
/* 257 */             super.writeEndElement();
/*     */           }
/*     */         }
/*     */         
/*     */         private void handleAttribute(String localName, String value) {
/* 262 */           if (localName.equals("name")) {
/* 263 */             if (this.onService) {
/* 264 */               this.serviceName = value;
/* 265 */               this.onService = false;
/* 266 */             } else if (this.onPort) {
/* 267 */               this.portName = value;
/* 268 */               this.onPort = false;
/*     */             } 
/*     */           }
/* 271 */           if (localName.equals("location") && this.onPortAddress) {
/* 272 */             this.portAddress = value;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 280 */           handleAttribute(localName, value);
/* 281 */           if (!this.eprExtnFilterON) {
/* 282 */             super.writeAttribute(prefix, namespaceURI, localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 288 */           handleAttribute(localName, value);
/* 289 */           if (!this.eprExtnFilterON) {
/* 290 */             super.writeAttribute(namespaceURI, localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 296 */           handleAttribute(localName, value);
/* 297 */           if (!this.eprExtnFilterON) {
/* 298 */             super.writeAttribute(localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 305 */           if (!this.eprExtnFilterON) {
/* 306 */             super.writeEmptyElement(namespaceURI, localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 312 */           if (!this.eprExtnFilterON) {
/* 313 */             super.writeNamespace(prefix, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 319 */           if (!this.eprExtnFilterON) {
/* 320 */             super.setNamespaceContext(context);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 326 */           if (!this.eprExtnFilterON) {
/* 327 */             super.setDefaultNamespace(uri);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 333 */           if (!this.eprExtnFilterON) {
/* 334 */             super.setPrefix(prefix, uri);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 340 */           if (!this.eprExtnFilterON) {
/* 341 */             super.writeProcessingInstruction(target, data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 347 */           if (!this.eprExtnFilterON) {
/* 348 */             super.writeEmptyElement(prefix, localName, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeCData(String data) throws XMLStreamException {
/* 354 */           if (!this.eprExtnFilterON) {
/* 355 */             super.writeCData(data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeCharacters(String text) throws XMLStreamException {
/* 361 */           if (!this.eprExtnFilterON) {
/* 362 */             super.writeCharacters(text);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeComment(String data) throws XMLStreamException {
/* 368 */           if (!this.eprExtnFilterON) {
/* 369 */             super.writeComment(data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeDTD(String dtd) throws XMLStreamException {
/* 375 */           if (!this.eprExtnFilterON) {
/* 376 */             super.writeDTD(dtd);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 382 */           if (!this.eprExtnFilterON) {
/* 383 */             super.writeDefaultNamespace(namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String localName) throws XMLStreamException {
/* 389 */           if (!this.eprExtnFilterON) {
/* 390 */             super.writeEmptyElement(localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEntityRef(String name) throws XMLStreamException {
/* 396 */           if (!this.eprExtnFilterON) {
/* 397 */             super.writeEntityRef(name);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 403 */           if (!this.eprExtnFilterON) {
/* 404 */             super.writeProcessingInstruction(target);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 411 */           if (!this.eprExtnFilterON)
/* 412 */             super.writeCharacters(text, start, len); 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/EPRSDDocumentFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */