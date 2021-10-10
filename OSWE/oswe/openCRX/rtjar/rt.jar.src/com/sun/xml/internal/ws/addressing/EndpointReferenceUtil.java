/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferSource;
/*     */ import com.sun.xml.internal.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointReferenceUtil
/*     */ {
/*     */   public static <T extends EndpointReference> T transform(Class<T> clazz, @NotNull EndpointReference epr) {
/*  60 */     assert epr != null;
/*  61 */     if (clazz.isAssignableFrom(W3CEndpointReference.class)) {
/*  62 */       if (epr instanceof W3CEndpointReference)
/*  63 */         return (T)epr; 
/*  64 */       if (epr instanceof MemberSubmissionEndpointReference) {
/*  65 */         return (T)toW3CEpr((MemberSubmissionEndpointReference)epr);
/*     */       }
/*  67 */     } else if (clazz.isAssignableFrom(MemberSubmissionEndpointReference.class)) {
/*  68 */       if (epr instanceof W3CEndpointReference)
/*  69 */         return (T)toMSEpr((W3CEndpointReference)epr); 
/*  70 */       if (epr instanceof MemberSubmissionEndpointReference) {
/*  71 */         return (T)epr;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  76 */     throw new WebServiceException("Unknwon EndpointReference: " + epr.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   private static W3CEndpointReference toW3CEpr(MemberSubmissionEndpointReference msEpr) {
/*  81 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*  82 */     w3cMetadataWritten = false;
/*     */     try {
/*  84 */       writer.writeStartDocument();
/*  85 */       writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "EndpointReference", AddressingVersion.W3C.nsUri);
/*     */       
/*  87 */       writer.writeNamespace(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.nsUri);
/*     */ 
/*     */       
/*  90 */       writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.address, AddressingVersion.W3C.nsUri);
/*     */ 
/*     */       
/*  93 */       writer.writeCharacters(msEpr.addr.uri);
/*  94 */       writer.writeEndElement();
/*     */       
/*  96 */       if ((msEpr.referenceProperties != null && msEpr.referenceProperties.elements.size() > 0) || (msEpr.referenceParameters != null && msEpr.referenceParameters.elements
/*  97 */         .size() > 0)) {
/*     */         
/*  99 */         writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "ReferenceParameters", AddressingVersion.W3C.nsUri);
/*     */ 
/*     */         
/* 102 */         if (msEpr.referenceProperties != null) {
/* 103 */           for (Element e : msEpr.referenceProperties.elements) {
/* 104 */             DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */           }
/*     */         }
/*     */         
/* 108 */         if (msEpr.referenceParameters != null) {
/* 109 */           for (Element e : msEpr.referenceParameters.elements) {
/* 110 */             DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */           }
/*     */         }
/* 113 */         writer.writeEndElement();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       Element wsdlElement = null;
/*     */       
/* 159 */       if (msEpr.elements != null && msEpr.elements.size() > 0) {
/* 160 */         for (Element e : msEpr.elements) {
/* 161 */           if (e.getNamespaceURI().equals(MemberSubmissionAddressingConstants.MEX_METADATA.getNamespaceURI()) && e
/* 162 */             .getLocalName().equals(MemberSubmissionAddressingConstants.MEX_METADATA.getLocalPart())) {
/* 163 */             NodeList nl = e.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", WSDLConstants.QNAME_DEFINITIONS
/* 164 */                 .getLocalPart());
/* 165 */             if (nl != null) {
/* 166 */               wsdlElement = (Element)nl.item(0);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 172 */       if (wsdlElement != null) {
/* 173 */         DOMUtil.serializeNode(wsdlElement, (XMLStreamWriter)writer);
/*     */       }
/*     */       
/* 176 */       if (w3cMetadataWritten) {
/* 177 */         writer.writeEndElement();
/*     */       }
/*     */ 
/*     */       
/* 181 */       if (msEpr.elements != null && msEpr.elements.size() > 0) {
/* 182 */         for (Element e : msEpr.elements) {
/* 183 */           if (!e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") || e
/* 184 */             .getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart()));
/*     */ 
/*     */           
/* 187 */           DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       writer.writeEndElement();
/* 195 */       writer.writeEndDocument();
/* 196 */       writer.flush();
/* 197 */     } catch (XMLStreamException e) {
/* 198 */       throw new WebServiceException(e);
/*     */     } 
/* 200 */     return new W3CEndpointReference((Source)new XMLStreamBufferSource((XMLStreamBuffer)writer.getXMLStreamBuffer()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean w3cMetadataWritten = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MemberSubmissionEndpointReference toMSEpr(W3CEndpointReference w3cEpr) {
/* 213 */     DOMResult result = new DOMResult();
/* 214 */     w3cEpr.writeTo(result);
/* 215 */     Node eprNode = result.getNode();
/* 216 */     Element e = DOMUtil.getFirstElementChild(eprNode);
/* 217 */     if (e == null) {
/* 218 */       return null;
/*     */     }
/*     */     
/* 221 */     MemberSubmissionEndpointReference msEpr = new MemberSubmissionEndpointReference();
/*     */     
/* 223 */     NodeList nodes = e.getChildNodes();
/* 224 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 225 */       if (nodes.item(i).getNodeType() == 1) {
/* 226 */         Element child = (Element)nodes.item(i);
/* 227 */         if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child
/* 228 */           .getLocalName().equals(AddressingVersion.W3C.eprType.address)) {
/* 229 */           if (msEpr.addr == null) {
/* 230 */             msEpr.addr = new MemberSubmissionEndpointReference.Address();
/*     */           }
/* 232 */           msEpr.addr.uri = XmlUtil.getTextForNode(child);
/*     */         }
/* 234 */         else if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child
/* 235 */           .getLocalName().equals("ReferenceParameters")) {
/* 236 */           NodeList refParams = child.getChildNodes();
/* 237 */           for (int j = 0; j < refParams.getLength(); j++) {
/* 238 */             if (refParams.item(j).getNodeType() == 1) {
/* 239 */               if (msEpr.referenceParameters == null) {
/* 240 */                 msEpr.referenceParameters = new MemberSubmissionEndpointReference.Elements();
/* 241 */                 msEpr.referenceParameters.elements = new ArrayList();
/*     */               } 
/* 243 */               msEpr.referenceParameters.elements.add((Element)refParams.item(j));
/*     */             } 
/*     */           } 
/* 246 */         } else if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child
/* 247 */           .getLocalName().equals(AddressingVersion.W3C.eprType.wsdlMetadata.getLocalPart())) {
/* 248 */           NodeList metadata = child.getChildNodes();
/* 249 */           String wsdlLocation = child.getAttributeNS("http://www.w3.org/ns/wsdl-instance", "wsdlLocation");
/*     */           
/* 251 */           Element wsdlDefinitions = null;
/* 252 */           for (int j = 0; j < metadata.getLength(); j++) {
/* 253 */             Node node = metadata.item(j);
/* 254 */             if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */               
/* 258 */               Element elm = (Element)node;
/* 259 */               if ((elm.getNamespaceURI().equals(AddressingVersion.W3C.wsdlNsUri) || elm
/* 260 */                 .getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata")) && elm
/* 261 */                 .getLocalName().equals(AddressingVersion.W3C.eprType.serviceName)) {
/* 262 */                 msEpr.serviceName = new MemberSubmissionEndpointReference.ServiceNameType();
/* 263 */                 msEpr.serviceName.portName = elm.getAttribute(AddressingVersion.W3C.eprType.portName);
/*     */                 
/* 265 */                 String service = elm.getTextContent();
/* 266 */                 String prefix = XmlUtil.getPrefix(service);
/* 267 */                 String name = XmlUtil.getLocalPart(service);
/*     */ 
/*     */                 
/* 270 */                 if (name != null)
/*     */                 
/*     */                 { 
/*     */                   
/* 274 */                   if (prefix != null) {
/* 275 */                     String ns = elm.lookupNamespaceURI(prefix);
/* 276 */                     if (ns != null) {
/* 277 */                       msEpr.serviceName.name = new QName(ns, name, prefix);
/*     */                     }
/*     */                   } else {
/* 280 */                     msEpr.serviceName.name = new QName(null, name);
/*     */                   } 
/* 282 */                   msEpr.serviceName.attributes = getAttributes(elm); } 
/* 283 */               } else if ((elm.getNamespaceURI().equals(AddressingVersion.W3C.wsdlNsUri) || elm
/* 284 */                 .getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata")) && elm
/* 285 */                 .getLocalName().equals(AddressingVersion.W3C.eprType.portTypeName)) {
/* 286 */                 msEpr.portTypeName = new MemberSubmissionEndpointReference.AttributedQName();
/*     */                 
/* 288 */                 String portType = elm.getTextContent();
/* 289 */                 String prefix = XmlUtil.getPrefix(portType);
/* 290 */                 String name = XmlUtil.getLocalPart(portType);
/*     */ 
/*     */                 
/* 293 */                 if (name != null)
/*     */                 
/*     */                 { 
/*     */                   
/* 297 */                   if (prefix != null) {
/* 298 */                     String ns = elm.lookupNamespaceURI(prefix);
/* 299 */                     if (ns != null) {
/* 300 */                       msEpr.portTypeName.name = new QName(ns, name, prefix);
/*     */                     }
/*     */                   } else {
/* 303 */                     msEpr.portTypeName.name = new QName(null, name);
/*     */                   } 
/* 305 */                   msEpr.portTypeName.attributes = getAttributes(elm); } 
/* 306 */               } else if (elm.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") && elm
/* 307 */                 .getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/* 308 */                 wsdlDefinitions = elm;
/*     */               }
/*     */               else {
/*     */                 
/* 312 */                 if (msEpr.elements == null) {
/* 313 */                   msEpr.elements = new ArrayList();
/*     */                 }
/* 315 */                 msEpr.elements.add(elm);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 320 */           Document doc = DOMUtil.createDom();
/* 321 */           Element mexEl = doc.createElementNS(MemberSubmissionAddressingConstants.MEX_METADATA.getNamespaceURI(), MemberSubmissionAddressingConstants.MEX_METADATA
/* 322 */               .getPrefix() + ":" + MemberSubmissionAddressingConstants.MEX_METADATA
/* 323 */               .getLocalPart());
/* 324 */           Element metadataEl = doc.createElementNS(MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getNamespaceURI(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION
/* 325 */               .getPrefix() + ":" + MemberSubmissionAddressingConstants.MEX_METADATA_SECTION
/* 326 */               .getLocalPart());
/* 327 */           metadataEl.setAttribute("Dialect", "http://schemas.xmlsoap.org/wsdl/");
/*     */           
/* 329 */           if (wsdlDefinitions == null && wsdlLocation != null && !wsdlLocation.equals("")) {
/* 330 */             wsdlLocation = wsdlLocation.trim();
/* 331 */             String wsdlTns = wsdlLocation.substring(0, wsdlLocation.indexOf(' '));
/* 332 */             wsdlLocation = wsdlLocation.substring(wsdlLocation.indexOf(' ') + 1);
/* 333 */             Element wsdlEl = doc.createElementNS("http://schemas.xmlsoap.org/wsdl/", "wsdl:" + WSDLConstants.QNAME_DEFINITIONS
/*     */                 
/* 335 */                 .getLocalPart());
/* 336 */             Element wsdlImportEl = doc.createElementNS("http://schemas.xmlsoap.org/wsdl/", "wsdl:" + WSDLConstants.QNAME_IMPORT
/*     */                 
/* 338 */                 .getLocalPart());
/* 339 */             wsdlImportEl.setAttribute("namespace", wsdlTns);
/* 340 */             wsdlImportEl.setAttribute("location", wsdlLocation);
/* 341 */             wsdlEl.appendChild(wsdlImportEl);
/* 342 */             metadataEl.appendChild(wsdlEl);
/* 343 */           } else if (wsdlDefinitions != null) {
/* 344 */             metadataEl.appendChild(wsdlDefinitions);
/*     */           } 
/* 346 */           mexEl.appendChild(metadataEl);
/*     */           
/* 348 */           if (msEpr.elements == null) {
/* 349 */             msEpr.elements = new ArrayList();
/*     */           }
/* 351 */           msEpr.elements.add(mexEl);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 356 */           if (msEpr.elements == null) {
/* 357 */             msEpr.elements = new ArrayList();
/*     */           }
/* 359 */           msEpr.elements.add(child);
/*     */         }
/*     */       
/* 362 */       } else if (nodes.item(i).getNodeType() == 2) {
/* 363 */         Node n = nodes.item(i);
/* 364 */         if (msEpr.attributes == null) {
/* 365 */           msEpr.attributes = new HashMap<>();
/* 366 */           String prefix = fixNull(n.getPrefix());
/* 367 */           String ns = fixNull(n.getNamespaceURI());
/* 368 */           String localName = n.getLocalName();
/* 369 */           msEpr.attributes.put(new QName(ns, localName, prefix), n.getNodeValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     return msEpr;
/*     */   }
/*     */   
/*     */   private static Map<QName, String> getAttributes(Node node) {
/* 378 */     Map<QName, String> attribs = null;
/*     */     
/* 380 */     NamedNodeMap nm = node.getAttributes();
/* 381 */     for (int i = 0; i < nm.getLength(); i++) {
/* 382 */       if (attribs == null) {
/* 383 */         attribs = new HashMap<>();
/*     */       }
/* 385 */       Node n = nm.item(i);
/* 386 */       String prefix = fixNull(n.getPrefix());
/* 387 */       String ns = fixNull(n.getNamespaceURI());
/* 388 */       String localName = n.getLocalName();
/* 389 */       if (!prefix.equals("xmlns") && (prefix.length() != 0 || !localName.equals("xmlns")))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 394 */         if (!localName.equals(AddressingVersion.W3C.eprType.portName))
/* 395 */           attribs.put(new QName(ns, localName, prefix), n.getNodeValue()); 
/*     */       }
/*     */     } 
/* 398 */     return attribs;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 404 */     if (s == null) {
/* 405 */       return "";
/*     */     }
/* 407 */     return s;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/EndpointReferenceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */