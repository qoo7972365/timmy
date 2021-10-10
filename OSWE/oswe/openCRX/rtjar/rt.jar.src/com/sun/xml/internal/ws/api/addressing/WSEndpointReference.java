/*      */ package com.sun.xml.internal.ws.api.addressing;
/*      */ 
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferResult;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferSource;
/*      */ import com.sun.xml.internal.stream.buffer.sax.SAXBufferProcessor;
/*      */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferProcessor;
/*      */ import com.sun.xml.internal.stream.buffer.stax.StreamWriterBufferCreator;
/*      */ import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil;
/*      */ import com.sun.xml.internal.ws.addressing.WSEPRExtension;
/*      */ import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;
/*      */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*      */ import com.sun.xml.internal.ws.api.message.Header;
/*      */ import com.sun.xml.internal.ws.api.message.HeaderList;
/*      */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*      */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*      */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*      */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*      */ import com.sun.xml.internal.ws.spi.ProviderImpl;
/*      */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*      */ import com.sun.xml.internal.ws.util.DOMUtil;
/*      */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*      */ import com.sun.xml.internal.ws.util.xml.XMLStreamWriterFilter;
/*      */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*      */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*      */ import java.io.InputStream;
/*      */ import java.io.StringWriter;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.xml.bind.JAXBContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import javax.xml.ws.Dispatch;
/*      */ import javax.xml.ws.EndpointReference;
/*      */ import javax.xml.ws.Service;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import javax.xml.ws.WebServiceFeature;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.helpers.XMLFilterImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class WSEndpointReference
/*      */   implements WSDLExtension
/*      */ {
/*      */   private final XMLStreamBuffer infoset;
/*      */   private final AddressingVersion version;
/*      */   @NotNull
/*      */   private Header[] referenceParameters;
/*      */   @NotNull
/*      */   private String address;
/*      */   @NotNull
/*      */   private QName rootElement;
/*      */   
/*      */   public WSEndpointReference(EndpointReference epr, AddressingVersion version) {
/*      */     try {
/*  119 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  120 */       epr.writeTo((Result)new XMLStreamBufferResult(xsb));
/*  121 */       this.infoset = (XMLStreamBuffer)xsb;
/*  122 */       this.version = version;
/*  123 */       this.rootElement = new QName("EndpointReference", version.nsUri);
/*  124 */       parse();
/*  125 */     } catch (XMLStreamException e) {
/*  126 */       throw new WebServiceException(ClientMessages.FAILED_TO_PARSE_EPR(epr), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(EndpointReference epr) {
/*  138 */     this(epr, AddressingVersion.fromSpecClass((Class)epr.getClass()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(XMLStreamBuffer infoset, AddressingVersion version) {
/*      */     try {
/*  146 */       this.infoset = infoset;
/*  147 */       this.version = version;
/*  148 */       this.rootElement = new QName("EndpointReference", version.nsUri);
/*  149 */       parse();
/*  150 */     } catch (XMLStreamException e) {
/*      */       
/*  152 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(InputStream infoset, AddressingVersion version) throws XMLStreamException {
/*  160 */     this(XMLStreamReaderFactory.create(null, infoset, false), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(XMLStreamReader in, AddressingVersion version) throws XMLStreamException {
/*  168 */     this(XMLStreamBuffer.createNewBufferFromXMLStreamReader(in), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(URL address, AddressingVersion version) {
/*  175 */     this(address.toExternalForm(), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(URI address, AddressingVersion version) {
/*  182 */     this(address.toString(), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(String address, AddressingVersion version) {
/*  189 */     this.infoset = createBufferFromAddress(address, version);
/*  190 */     this.version = version;
/*  191 */     this.address = address;
/*  192 */     this.rootElement = new QName("EndpointReference", version.nsUri);
/*  193 */     this.referenceParameters = (Header[])EMPTY_ARRAY;
/*      */   }
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromAddress(String address, AddressingVersion version) {
/*      */     try {
/*  198 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  199 */       StreamWriterBufferCreator w = new StreamWriterBufferCreator(xsb);
/*  200 */       w.writeStartDocument();
/*  201 */       w.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*      */       
/*  203 */       w.writeNamespace(version.getPrefix(), version.nsUri);
/*  204 */       w.writeStartElement(version.getPrefix(), version.eprType.address, version.nsUri);
/*  205 */       w.writeCharacters(address);
/*  206 */       w.writeEndElement();
/*  207 */       w.writeEndElement();
/*  208 */       w.writeEndDocument();
/*  209 */       w.close();
/*  210 */       return (XMLStreamBuffer)xsb;
/*  211 */     } catch (XMLStreamException e) {
/*      */       
/*  213 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable List<Element> referenceParameters) {
/*  232 */     this(version, address, service, port, portType, metadata, wsdlAddress, null, referenceParameters, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable List<Element> referenceParameters, @Nullable Collection<EPRExtension> extns, @Nullable Map<QName, String> attributes) {
/*  251 */     this(createBufferFromData(version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, (String)null, extns, attributes), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable String wsdlTargetNamepsace, @Nullable List<Element> referenceParameters, @Nullable List<Element> elements, @Nullable Map<QName, String> attributes) {
/*  273 */     this(
/*  274 */         createBufferFromData(version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamepsace, elements, attributes), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromData(AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable List<Element> elements, @Nullable Map<QName, String> attributes) {
/*  281 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*      */     
/*      */     try {
/*  284 */       writer.writeStartDocument();
/*  285 */       writer.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*  286 */       writer.writeNamespace(version.getPrefix(), version.nsUri);
/*      */       
/*  288 */       writePartialEPRInfoset(writer, version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace, attributes);
/*      */ 
/*      */ 
/*      */       
/*  292 */       if (elements != null) {
/*  293 */         for (Element e : elements) {
/*  294 */           DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */         }
/*      */       }
/*      */       
/*  298 */       writer.writeEndElement();
/*  299 */       writer.writeEndDocument();
/*  300 */       writer.flush();
/*      */       
/*  302 */       return (XMLStreamBuffer)writer.getXMLStreamBuffer();
/*  303 */     } catch (XMLStreamException e) {
/*  304 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromData(AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable Collection<EPRExtension> extns, @Nullable Map<QName, String> attributes) {
/*  311 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*      */     
/*      */     try {
/*  314 */       writer.writeStartDocument();
/*  315 */       writer.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*  316 */       writer.writeNamespace(version.getPrefix(), version.nsUri);
/*      */       
/*  318 */       writePartialEPRInfoset(writer, version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace, attributes);
/*      */ 
/*      */ 
/*      */       
/*  322 */       if (extns != null) {
/*  323 */         for (EPRExtension e : extns) {
/*  324 */           XMLStreamReaderToXMLStreamWriter c = new XMLStreamReaderToXMLStreamWriter();
/*  325 */           XMLStreamReader r = e.readAsXMLStreamReader();
/*  326 */           c.bridge(r, (XMLStreamWriter)writer);
/*  327 */           XMLStreamReaderFactory.recycle(r);
/*      */         } 
/*      */       }
/*      */       
/*  331 */       writer.writeEndElement();
/*  332 */       writer.writeEndDocument();
/*  333 */       writer.flush();
/*      */       
/*  335 */       return (XMLStreamBuffer)writer.getXMLStreamBuffer();
/*  336 */     } catch (XMLStreamException e) {
/*  337 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writePartialEPRInfoset(StreamWriterBufferCreator writer, AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable Map<QName, String> attributes) throws XMLStreamException {
/*  344 */     if (attributes != null) {
/*  345 */       for (Map.Entry<QName, String> entry : attributes.entrySet()) {
/*  346 */         QName qname = entry.getKey();
/*  347 */         writer.writeAttribute(qname.getPrefix(), qname.getNamespaceURI(), qname.getLocalPart(), entry.getValue());
/*      */       } 
/*      */     }
/*      */     
/*  351 */     writer.writeStartElement(version.getPrefix(), version.eprType.address, version.nsUri);
/*  352 */     writer.writeCharacters(address);
/*  353 */     writer.writeEndElement();
/*      */     
/*  355 */     if (referenceParameters != null && referenceParameters.size() > 0) {
/*  356 */       writer.writeStartElement(version.getPrefix(), version.eprType.referenceParameters, version.nsUri);
/*  357 */       for (Element e : referenceParameters) {
/*  358 */         DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */       }
/*  360 */       writer.writeEndElement();
/*      */     } 
/*      */     
/*  363 */     switch (version) {
/*      */       case W3C:
/*  365 */         writeW3CMetaData(writer, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace);
/*      */         break;
/*      */       
/*      */       case MEMBER:
/*  369 */         writeMSMetaData(writer, service, port, portType, metadata);
/*  370 */         if (wsdlAddress != null) {
/*      */ 
/*      */           
/*  373 */           writer.writeStartElement(MemberSubmissionAddressingConstants.MEX_METADATA.getPrefix(), MemberSubmissionAddressingConstants.MEX_METADATA
/*  374 */               .getLocalPart(), MemberSubmissionAddressingConstants.MEX_METADATA
/*  375 */               .getNamespaceURI());
/*  376 */           writer.writeStartElement(MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getPrefix(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION
/*  377 */               .getLocalPart(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION
/*  378 */               .getNamespaceURI());
/*  379 */           writer.writeAttribute("Dialect", "http://schemas.xmlsoap.org/wsdl/");
/*      */ 
/*      */           
/*  382 */           writeWsdl(writer, service, wsdlAddress);
/*      */           
/*  384 */           writer.writeEndElement();
/*  385 */           writer.writeEndElement();
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isEmty(QName qname) {
/*  393 */     return (qname == null || qname.toString().trim().length() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeW3CMetaData(StreamWriterBufferCreator writer, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace) throws XMLStreamException {
/*  404 */     if (isEmty(service) && isEmty(port) && isEmty(portType) && metadata == null) {
/*      */       return;
/*      */     }
/*      */     
/*  408 */     writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.wsdlMetadata
/*  409 */         .getLocalPart(), AddressingVersion.W3C.nsUri);
/*  410 */     writer.writeNamespace(AddressingVersion.W3C.getWsdlPrefix(), AddressingVersion.W3C.wsdlNsUri);
/*      */ 
/*      */     
/*  413 */     if (wsdlAddress != null) {
/*  414 */       writeWsdliLocation(writer, service, wsdlAddress, wsdlTargetNamespace);
/*      */     }
/*      */ 
/*      */     
/*  418 */     if (portType != null) {
/*  419 */       writer.writeStartElement("wsam", AddressingVersion.W3C.eprType.portTypeName, "http://www.w3.org/2007/05/addressing/metadata");
/*      */ 
/*      */       
/*  422 */       writer.writeNamespace("wsam", "http://www.w3.org/2007/05/addressing/metadata");
/*      */       
/*  424 */       String portTypePrefix = portType.getPrefix();
/*  425 */       if (portTypePrefix == null || portTypePrefix.equals(""))
/*      */       {
/*  427 */         portTypePrefix = "wsns";
/*      */       }
/*  429 */       writer.writeNamespace(portTypePrefix, portType.getNamespaceURI());
/*  430 */       writer.writeCharacters(portTypePrefix + ":" + portType.getLocalPart());
/*  431 */       writer.writeEndElement();
/*      */     } 
/*  433 */     if (service != null)
/*      */     {
/*  435 */       if (!service.getNamespaceURI().equals("") && !service.getLocalPart().equals("")) {
/*  436 */         writer.writeStartElement("wsam", AddressingVersion.W3C.eprType.serviceName, "http://www.w3.org/2007/05/addressing/metadata");
/*      */ 
/*      */         
/*  439 */         writer.writeNamespace("wsam", "http://www.w3.org/2007/05/addressing/metadata");
/*      */         
/*  441 */         String servicePrefix = service.getPrefix();
/*  442 */         if (servicePrefix == null || servicePrefix.equals(""))
/*      */         {
/*  444 */           servicePrefix = "wsns";
/*      */         }
/*  446 */         writer.writeNamespace(servicePrefix, service.getNamespaceURI());
/*  447 */         if (port != null) {
/*  448 */           writer.writeAttribute(AddressingVersion.W3C.eprType.portName, port.getLocalPart());
/*      */         }
/*  450 */         writer.writeCharacters(servicePrefix + ":" + service.getLocalPart());
/*  451 */         writer.writeEndElement();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  461 */     if (metadata != null) {
/*  462 */       for (Element e : metadata) {
/*  463 */         DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */       }
/*      */     }
/*  466 */     writer.writeEndElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeWsdliLocation(StreamWriterBufferCreator writer, QName service, String wsdlAddress, String wsdlTargetNamespace) throws XMLStreamException {
/*  478 */     String wsdliLocation = "";
/*  479 */     if (wsdlTargetNamespace != null) {
/*  480 */       wsdliLocation = wsdlTargetNamespace + " ";
/*  481 */     } else if (service != null) {
/*  482 */       wsdliLocation = service.getNamespaceURI() + " ";
/*      */     } else {
/*  484 */       throw new WebServiceException("WSDL target Namespace cannot be resolved");
/*      */     } 
/*  486 */     wsdliLocation = wsdliLocation + wsdlAddress;
/*  487 */     writer.writeNamespace("wsdli", "http://www.w3.org/ns/wsdl-instance");
/*      */     
/*  489 */     writer.writeAttribute("wsdli", "http://www.w3.org/ns/wsdl-instance", "wsdlLocation", wsdliLocation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeMSMetaData(StreamWriterBufferCreator writer, QName service, QName port, QName portType, List<Element> metadata) throws XMLStreamException {
/*  501 */     if (portType != null) {
/*      */       
/*  503 */       writer.writeStartElement(AddressingVersion.MEMBER.getPrefix(), AddressingVersion.MEMBER.eprType.portTypeName, AddressingVersion.MEMBER.nsUri);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  508 */       String portTypePrefix = portType.getPrefix();
/*  509 */       if (portTypePrefix == null || portTypePrefix.equals(""))
/*      */       {
/*  511 */         portTypePrefix = "wsns";
/*      */       }
/*  513 */       writer.writeNamespace(portTypePrefix, portType.getNamespaceURI());
/*  514 */       writer.writeCharacters(portTypePrefix + ":" + portType.getLocalPart());
/*  515 */       writer.writeEndElement();
/*      */     } 
/*      */     
/*  518 */     if (service != null && 
/*  519 */       !service.getNamespaceURI().equals("") && !service.getLocalPart().equals("")) {
/*  520 */       writer.writeStartElement(AddressingVersion.MEMBER.getPrefix(), AddressingVersion.MEMBER.eprType.serviceName, AddressingVersion.MEMBER.nsUri);
/*      */ 
/*      */       
/*  523 */       String servicePrefix = service.getPrefix();
/*  524 */       if (servicePrefix == null || servicePrefix.equals(""))
/*      */       {
/*  526 */         servicePrefix = "wsns";
/*      */       }
/*  528 */       writer.writeNamespace(servicePrefix, service.getNamespaceURI());
/*  529 */       if (port != null) {
/*  530 */         writer.writeAttribute(AddressingVersion.MEMBER.eprType.portName, port
/*  531 */             .getLocalPart());
/*      */       }
/*  533 */       writer.writeCharacters(servicePrefix + ":" + service.getLocalPart());
/*  534 */       writer.writeEndElement();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeWsdl(StreamWriterBufferCreator writer, QName service, String wsdlAddress) throws XMLStreamException {
/*  541 */     writer.writeStartElement("wsdl", WSDLConstants.QNAME_DEFINITIONS
/*  542 */         .getLocalPart(), "http://schemas.xmlsoap.org/wsdl/");
/*      */     
/*  544 */     writer.writeNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/");
/*  545 */     writer.writeStartElement("wsdl", WSDLConstants.QNAME_IMPORT
/*  546 */         .getLocalPart(), "http://schemas.xmlsoap.org/wsdl/");
/*      */     
/*  548 */     writer.writeAttribute("namespace", service.getNamespaceURI());
/*  549 */     writer.writeAttribute("location", wsdlAddress);
/*  550 */     writer.writeEndElement();
/*  551 */     writer.writeEndElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static WSEndpointReference create(@Nullable EndpointReference epr) {
/*  565 */     if (epr != null) {
/*  566 */       return new WSEndpointReference(epr);
/*      */     }
/*  568 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull URI newAddress) {
/*  576 */     return createWithAddress(newAddress.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull URL newAddress) {
/*  583 */     return createWithAddress(newAddress.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull final String newAddress) {
/*  603 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  604 */     XMLFilterImpl filter = new XMLFilterImpl() {
/*      */         private boolean inAddress = false;
/*      */         
/*      */         public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/*  608 */           if (localName.equals("Address") && uri.equals(WSEndpointReference.this.version.nsUri)) {
/*  609 */             this.inAddress = true;
/*      */           }
/*  611 */           super.startElement(uri, localName, qName, atts);
/*      */         }
/*      */ 
/*      */         
/*      */         public void characters(char[] ch, int start, int length) throws SAXException {
/*  616 */           if (!this.inAddress) {
/*  617 */             super.characters(ch, start, length);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void endElement(String uri, String localName, String qName) throws SAXException {
/*  623 */           if (this.inAddress) {
/*  624 */             super.characters(newAddress.toCharArray(), 0, newAddress.length());
/*      */           }
/*  626 */           this.inAddress = false;
/*  627 */           super.endElement(uri, localName, qName);
/*      */         }
/*      */       };
/*  630 */     filter.setContentHandler((ContentHandler)xsb.createFromSAXBufferCreator());
/*      */     try {
/*  632 */       this.infoset.writeTo(filter, false);
/*  633 */     } catch (SAXException e) {
/*  634 */       throw new AssertionError(e);
/*      */     } 
/*      */     
/*  637 */     return new WSEndpointReference((XMLStreamBuffer)xsb, this.version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public EndpointReference toSpec() {
/*  650 */     return ProviderImpl.INSTANCE.readEndpointReference(asSource("EndpointReference"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T extends EndpointReference> T toSpec(Class<T> clazz) {
/*  660 */     return (T)EndpointReferenceUtil.transform(clazz, toSpec());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T> T getPort(@NotNull Service jaxwsService, @NotNull Class<T> serviceEndpointInterface, WebServiceFeature... features) {
/*  676 */     return (T)jaxwsService.getPort(toSpec(), serviceEndpointInterface, features);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T> Dispatch<T> createDispatch(@NotNull Service jaxwsService, @NotNull Class<T> type, @NotNull Service.Mode mode, WebServiceFeature... features) {
/*  695 */     return jaxwsService.createDispatch(toSpec(), type, mode, features);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Dispatch<Object> createDispatch(@NotNull Service jaxwsService, @NotNull JAXBContext context, @NotNull Service.Mode mode, WebServiceFeature... features) {
/*  714 */     return jaxwsService.createDispatch(toSpec(), context, mode, features);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public AddressingVersion getVersion() {
/*  721 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public String getAddress() {
/*  728 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnonymous() {
/*  735 */     return this.address.equals(this.version.anonymousUri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNone() {
/*  743 */     return this.address.equals(this.version.noneUri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parse() throws XMLStreamException {
/*  753 */     StreamReaderBufferProcessor xsr = this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */     
/*  756 */     if (xsr.getEventType() == 7) {
/*  757 */       xsr.nextTag();
/*      */     }
/*  759 */     assert xsr.getEventType() == 1;
/*      */     
/*  761 */     String rootLocalName = xsr.getLocalName();
/*  762 */     if (!xsr.getNamespaceURI().equals(this.version.nsUri)) {
/*  763 */       throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(this.version.nsUri, xsr
/*  764 */             .getNamespaceURI()));
/*      */     }
/*      */     
/*  767 */     this.rootElement = new QName(xsr.getNamespaceURI(), rootLocalName);
/*      */ 
/*      */     
/*  770 */     List<Header> marks = null;
/*      */     
/*  772 */     while (xsr.nextTag() == 1) {
/*  773 */       String localName = xsr.getLocalName();
/*  774 */       if (this.version.isReferenceParameter(localName)) {
/*      */         XMLStreamBuffer mark;
/*  776 */         while ((mark = xsr.nextTagAndMark()) != null) {
/*  777 */           if (marks == null) {
/*  778 */             marks = new ArrayList<>();
/*      */           }
/*      */ 
/*      */           
/*  782 */           marks.add(this.version.createReferenceParameterHeader(mark, xsr
/*  783 */                 .getNamespaceURI(), xsr.getLocalName()));
/*  784 */           XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */         }  continue;
/*      */       } 
/*  787 */       if (localName.equals("Address")) {
/*  788 */         if (this.address != null) {
/*  789 */           throw new InvalidAddressingHeaderException(new QName(this.version.nsUri, rootLocalName), AddressingVersion.fault_duplicateAddressInEpr);
/*      */         }
/*  791 */         this.address = xsr.getElementText().trim(); continue;
/*      */       } 
/*  793 */       XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     if (marks == null) {
/*  800 */       this.referenceParameters = (Header[])EMPTY_ARRAY;
/*      */     } else {
/*  802 */       this.referenceParameters = marks.<Header>toArray(new Header[marks.size()]);
/*      */     } 
/*      */     
/*  805 */     if (this.address == null) {
/*  806 */       throw new InvalidAddressingHeaderException(new QName(this.version.nsUri, rootLocalName), this.version.fault_missingAddressInEpr);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReader read(@NotNull final String localName) throws XMLStreamException {
/*  820 */     return (XMLStreamReader)new StreamReaderBufferProcessor(this.infoset)
/*      */       {
/*      */         protected void processElement(String prefix, String uri, String _localName, boolean inScope) {
/*  823 */           if (this._depth == 0) {
/*  824 */             _localName = localName;
/*      */           }
/*  826 */           super.processElement(prefix, uri, _localName, WSEndpointReference.this.isInscope(WSEndpointReference.this.infoset, this._depth));
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private boolean isInscope(XMLStreamBuffer buffer, int depth) {
/*  832 */     return (buffer.getInscopeNamespaces().size() > 0 && depth == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Source asSource(@NotNull String localName) {
/*  844 */     return new SAXSource((XMLReader)new SAXBufferProcessorImpl(localName), new InputSource());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(@NotNull String localName, ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*  859 */     SAXBufferProcessorImpl p = new SAXBufferProcessorImpl(localName);
/*  860 */     p.setContentHandler(contentHandler);
/*  861 */     p.setErrorHandler(errorHandler);
/*  862 */     p.process(this.infoset, fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(@NotNull final String localName, @NotNull XMLStreamWriter w) throws XMLStreamException {
/*  873 */     this.infoset.writeToXMLStreamWriter((XMLStreamWriter)new XMLStreamWriterFilter(w)
/*      */         {
/*      */           private boolean root = true;
/*      */ 
/*      */ 
/*      */           
/*      */           public void writeStartDocument() throws XMLStreamException {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void writeStartDocument(String encoding, String version) throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           public void writeStartDocument(String version) throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           public void writeEndDocument() throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           private String override(String ln) {
/*  893 */             if (this.root) {
/*  894 */               this.root = false;
/*  895 */               return localName;
/*      */             } 
/*  897 */             return ln;
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String localName) throws XMLStreamException {
/*  902 */             super.writeStartElement(override(localName));
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/*  907 */             super.writeStartElement(namespaceURI, override(localName));
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/*  912 */             super.writeStartElement(prefix, override(localName), namespaceURI);
/*      */           }
/*      */         }true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Header createHeader(QName rootTagName) {
/*  931 */     return (Header)new EPRHeader(rootTagName, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParametersToList(HeaderList outbound) {
/*  943 */     for (Header header : this.referenceParameters) {
/*  944 */       outbound.add(header);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParametersToList(MessageHeaders outbound) {
/*  953 */     for (Header header : this.referenceParameters) {
/*  954 */       outbound.add(header);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParameters(HeaderList headers) {
/*  962 */     if (headers != null) {
/*  963 */       Header[] hs = new Header[this.referenceParameters.length + headers.size()];
/*  964 */       System.arraycopy(this.referenceParameters, 0, hs, 0, this.referenceParameters.length);
/*  965 */       int i = this.referenceParameters.length;
/*  966 */       for (Header h : headers) {
/*  967 */         hs[i++] = h;
/*      */       }
/*  969 */       this.referenceParameters = hs;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/*  980 */       StringWriter sw = new StringWriter();
/*  981 */       XmlUtil.newTransformer().transform(asSource("EndpointReference"), new StreamResult(sw));
/*  982 */       return sw.toString();
/*  983 */     } catch (TransformerException e) {
/*  984 */       return e.toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getName() {
/*  994 */     return this.rootElement;
/*      */   }
/*      */ 
/*      */   
/*      */   class SAXBufferProcessorImpl
/*      */     extends SAXBufferProcessor
/*      */   {
/*      */     private final String rootLocalName;
/*      */     private boolean root = true;
/*      */     
/*      */     public SAXBufferProcessorImpl(String rootLocalName) {
/* 1005 */       super(WSEndpointReference.this.infoset, false);
/* 1006 */       this.rootLocalName = rootLocalName;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void processElement(String uri, String localName, String qName, boolean inscope) throws SAXException {
/* 1011 */       if (this.root) {
/* 1012 */         this.root = false;
/*      */         
/* 1014 */         if (qName.equals(localName)) {
/* 1015 */           qName = localName = this.rootLocalName;
/*      */         } else {
/* 1017 */           localName = this.rootLocalName;
/* 1018 */           int idx = qName.indexOf(':');
/* 1019 */           qName = qName.substring(0, idx + 1) + this.rootLocalName;
/*      */         } 
/*      */       } 
/* 1022 */       super.processElement(uri, localName, qName, inscope);
/*      */     }
/*      */   }
/*      */   
/* 1026 */   private static final OutboundReferenceParameterHeader[] EMPTY_ARRAY = new OutboundReferenceParameterHeader[0];
/*      */ 
/*      */   
/*      */   private Map<QName, EPRExtension> rootEprExtensions;
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class EPRExtension
/*      */   {
/*      */     public abstract XMLStreamReader readAsXMLStreamReader() throws XMLStreamException;
/*      */ 
/*      */     
/*      */     public abstract QName getQName();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EPRExtension getEPRExtension(QName extnQName) throws XMLStreamException {
/* 1044 */     if (this.rootEprExtensions == null) {
/* 1045 */       parseEPRExtensions();
/*      */     }
/* 1047 */     return this.rootEprExtensions.get(extnQName);
/*      */   }
/*      */   @NotNull
/*      */   public Collection<EPRExtension> getEPRExtensions() throws XMLStreamException {
/* 1051 */     if (this.rootEprExtensions == null) {
/* 1052 */       parseEPRExtensions();
/*      */     }
/* 1054 */     return this.rootEprExtensions.values();
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseEPRExtensions() throws XMLStreamException {
/* 1059 */     this.rootEprExtensions = new HashMap<>();
/*      */ 
/*      */     
/* 1062 */     StreamReaderBufferProcessor xsr = this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */     
/* 1065 */     if (xsr.getEventType() == 7) {
/* 1066 */       xsr.nextTag();
/*      */     }
/* 1068 */     assert xsr.getEventType() == 1;
/*      */     
/* 1070 */     if (!xsr.getNamespaceURI().equals(this.version.nsUri)) {
/* 1071 */       throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(this.version.nsUri, xsr
/* 1072 */             .getNamespaceURI()));
/*      */     }
/*      */ 
/*      */     
/*      */     XMLStreamBuffer mark;
/*      */ 
/*      */     
/* 1079 */     while ((mark = xsr.nextTagAndMark()) != null) {
/* 1080 */       String localName = xsr.getLocalName();
/* 1081 */       String ns = xsr.getNamespaceURI();
/* 1082 */       if (this.version.nsUri.equals(ns)) {
/*      */ 
/*      */         
/* 1085 */         XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr); continue;
/*      */       } 
/* 1087 */       QName qn = new QName(ns, localName);
/* 1088 */       this.rootEprExtensions.put(qn, new WSEPRExtension(mark, qn));
/* 1089 */       XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Metadata getMetaData() {
/* 1102 */     return new Metadata();
/*      */   }
/*      */   
/*      */   public class Metadata { @Nullable
/*      */     private QName serviceName;
/*      */     @Nullable
/*      */     private QName portName;
/*      */     @Nullable
/*      */     private QName portTypeName;
/*      */     @Nullable
/*      */     private Source wsdlSource;
/*      */     @Nullable
/*      */     private String wsdliLocation;
/*      */     
/*      */     @Nullable
/*      */     public QName getServiceName() {
/* 1118 */       return this.serviceName;
/*      */     } @Nullable
/*      */     public QName getPortName() {
/* 1121 */       return this.portName;
/*      */     } @Nullable
/*      */     public QName getPortTypeName() {
/* 1124 */       return this.portTypeName;
/*      */     } @Nullable
/*      */     public Source getWsdlSource() {
/* 1127 */       return this.wsdlSource;
/*      */     } @Nullable
/*      */     public String getWsdliLocation() {
/* 1130 */       return this.wsdliLocation;
/*      */     }
/*      */     
/*      */     private Metadata() {
/*      */       try {
/* 1135 */         parseMetaData();
/* 1136 */       } catch (XMLStreamException e) {
/* 1137 */         throw new WebServiceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void parseMetaData() throws XMLStreamException {
/* 1145 */       StreamReaderBufferProcessor xsr = WSEndpointReference.this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */       
/* 1148 */       if (xsr.getEventType() == 7) {
/* 1149 */         xsr.nextTag();
/*      */       }
/* 1151 */       assert xsr.getEventType() == 1;
/* 1152 */       String rootElement = xsr.getLocalName();
/* 1153 */       if (!xsr.getNamespaceURI().equals(WSEndpointReference.this.version.nsUri)) {
/* 1154 */         throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(
/* 1155 */               WSEndpointReference.this.version.nsUri, xsr.getNamespaceURI()));
/*      */       }
/*      */ 
/*      */       
/* 1159 */       if (WSEndpointReference.this.version == AddressingVersion.W3C) {
/*      */         
/*      */         do {
/* 1162 */           if (xsr.getLocalName().equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getLocalPart())) {
/* 1163 */             String wsdlLoc = xsr.getAttributeValue("http://www.w3.org/ns/wsdl-instance", "wsdlLocation");
/* 1164 */             if (wsdlLoc != null) {
/* 1165 */               this.wsdliLocation = wsdlLoc.trim();
/*      */             }
/*      */             XMLStreamBuffer mark;
/* 1168 */             while ((mark = xsr.nextTagAndMark()) != null) {
/* 1169 */               String localName = xsr.getLocalName();
/* 1170 */               String ns = xsr.getNamespaceURI();
/* 1171 */               if (localName.equals(WSEndpointReference.this.version.eprType.serviceName)) {
/* 1172 */                 String portStr = xsr.getAttributeValue(null, WSEndpointReference.this.version.eprType.portName);
/* 1173 */                 if (this.serviceName != null) {
/* 1174 */                   throw new RuntimeException("More than one " + WSEndpointReference.this.version.eprType.serviceName + " element in EPR Metadata");
/*      */                 }
/* 1176 */                 this.serviceName = getElementTextAsQName(xsr);
/* 1177 */                 if (this.serviceName != null && portStr != null)
/* 1178 */                   this.portName = new QName(this.serviceName.getNamespaceURI(), portStr);  continue;
/*      */               } 
/* 1180 */               if (localName.equals(WSEndpointReference.this.version.eprType.portTypeName)) {
/* 1181 */                 if (this.portTypeName != null) {
/* 1182 */                   throw new RuntimeException("More than one " + WSEndpointReference.this.version.eprType.portTypeName + " element in EPR Metadata");
/*      */                 }
/* 1184 */                 this.portTypeName = getElementTextAsQName(xsr); continue;
/* 1185 */               }  if (ns.equals("http://schemas.xmlsoap.org/wsdl/") && localName
/* 1186 */                 .equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/* 1187 */                 this.wsdlSource = (Source)new XMLStreamBufferSource(mark); continue;
/*      */               } 
/* 1189 */               XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 1194 */           else if (!xsr.getLocalName().equals(rootElement)) {
/* 1195 */             XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */           }
/*      */         
/* 1198 */         } while (XMLStreamReaderUtil.nextElementContent((XMLStreamReader)xsr) == 1);
/*      */         
/* 1200 */         if (this.wsdliLocation != null) {
/* 1201 */           String wsdlLocation = this.wsdliLocation.trim();
/* 1202 */           wsdlLocation = wsdlLocation.substring(this.wsdliLocation.lastIndexOf(" "));
/* 1203 */           this.wsdlSource = new StreamSource(wsdlLocation);
/*      */         } 
/* 1205 */       } else if (WSEndpointReference.this.version == AddressingVersion.MEMBER) {
/*      */         do {
/* 1207 */           String localName = xsr.getLocalName();
/* 1208 */           String ns = xsr.getNamespaceURI();
/*      */           
/* 1210 */           if (localName.equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getLocalPart()) && ns
/* 1211 */             .equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getNamespaceURI())) {
/* 1212 */             while (xsr.nextTag() == 1) {
/*      */               XMLStreamBuffer mark;
/* 1214 */               while ((mark = xsr.nextTagAndMark()) != null) {
/* 1215 */                 localName = xsr.getLocalName();
/* 1216 */                 ns = xsr.getNamespaceURI();
/* 1217 */                 if (ns.equals("http://schemas.xmlsoap.org/wsdl/") && localName
/* 1218 */                   .equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/* 1219 */                   this.wsdlSource = (Source)new XMLStreamBufferSource(mark); continue;
/*      */                 } 
/* 1221 */                 XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */               }
/*      */             
/*      */             } 
/* 1225 */           } else if (localName.equals(WSEndpointReference.this.version.eprType.serviceName)) {
/* 1226 */             String portStr = xsr.getAttributeValue(null, WSEndpointReference.this.version.eprType.portName);
/* 1227 */             this.serviceName = getElementTextAsQName(xsr);
/* 1228 */             if (this.serviceName != null && portStr != null) {
/* 1229 */               this.portName = new QName(this.serviceName.getNamespaceURI(), portStr);
/*      */             }
/* 1231 */           } else if (localName.equals(WSEndpointReference.this.version.eprType.portTypeName)) {
/* 1232 */             this.portTypeName = getElementTextAsQName(xsr);
/*      */           
/*      */           }
/* 1235 */           else if (!xsr.getLocalName().equals(rootElement)) {
/* 1236 */             XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */           }
/*      */         
/* 1239 */         } while (XMLStreamReaderUtil.nextElementContent((XMLStreamReader)xsr) == 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     private QName getElementTextAsQName(StreamReaderBufferProcessor xsr) throws XMLStreamException {
/* 1244 */       String text = xsr.getElementText().trim();
/* 1245 */       String prefix = XmlUtil.getPrefix(text);
/* 1246 */       String name = XmlUtil.getLocalPart(text);
/* 1247 */       if (name != null) {
/* 1248 */         if (prefix != null) {
/* 1249 */           String ns = xsr.getNamespaceURI(prefix);
/* 1250 */           if (ns != null) {
/* 1251 */             return new QName(ns, name, prefix);
/*      */           }
/*      */         } else {
/* 1254 */           return new QName(null, name);
/*      */         } 
/*      */       }
/* 1257 */       return null;
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/WSEndpointReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */