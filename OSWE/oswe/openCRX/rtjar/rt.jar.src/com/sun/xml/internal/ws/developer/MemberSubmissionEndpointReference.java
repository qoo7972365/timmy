/*     */ package com.sun.xml.internal.ws.developer;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "EndpointReference", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */ @XmlType(name = "EndpointReferenceType", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */ public final class MemberSubmissionEndpointReference
/*     */   extends EndpointReference
/*     */   implements MemberSubmissionAddressingConstants
/*     */ {
/*  67 */   private static final ContextClassloaderLocal<JAXBContext> msjc = new ContextClassloaderLocal<JAXBContext>()
/*     */     {
/*     */       protected JAXBContext initialValue() throws Exception {
/*  70 */         return MemberSubmissionEndpointReference.getMSJaxbContext();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   @XmlElement(name = "Address", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Address addr;
/*     */   
/*     */   @XmlElement(name = "ReferenceProperties", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Elements referenceProperties;
/*     */   
/*     */   @XmlElement(name = "ReferenceParameters", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Elements referenceParameters;
/*     */   
/*     */   @XmlElement(name = "PortType", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public AttributedQName portTypeName;
/*     */ 
/*     */   
/*     */   public MemberSubmissionEndpointReference(@NotNull Source source) {
/*  89 */     if (source == null) {
/*  90 */       throw new WebServiceException("Source parameter can not be null on constructor");
/*     */     }
/*     */     
/*     */     try {
/*  94 */       Unmarshaller unmarshaller = ((JAXBContext)msjc.get()).createUnmarshaller();
/*  95 */       MemberSubmissionEndpointReference epr = (MemberSubmissionEndpointReference)unmarshaller.unmarshal(source, MemberSubmissionEndpointReference.class).getValue();
/*     */       
/*  97 */       this.addr = epr.addr;
/*  98 */       this.referenceProperties = epr.referenceProperties;
/*  99 */       this.referenceParameters = epr.referenceParameters;
/* 100 */       this.portTypeName = epr.portTypeName;
/* 101 */       this.serviceName = epr.serviceName;
/* 102 */       this.attributes = epr.attributes;
/* 103 */       this.elements = epr.elements;
/* 104 */     } catch (JAXBException e) {
/* 105 */       throw new WebServiceException("Error unmarshalling MemberSubmissionEndpointReference ", e);
/* 106 */     } catch (ClassCastException e) {
/* 107 */       throw new WebServiceException("Source did not contain MemberSubmissionEndpointReference", e);
/*     */     } 
/*     */   } @XmlElement(name = "ServiceName", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public ServiceNameType serviceName; @XmlAnyAttribute
/*     */   public Map<QName, String> attributes; @XmlAnyElement
/*     */   public List<Element> elements; protected static final String MSNS = "http://schemas.xmlsoap.org/ws/2004/08/addressing"; public MemberSubmissionEndpointReference() {} public void writeTo(Result result) {
/*     */     try {
/* 114 */       Marshaller marshaller = ((JAXBContext)msjc.get()).createMarshaller();
/*     */       
/* 116 */       marshaller.marshal(this, result);
/* 117 */     } catch (JAXBException e) {
/* 118 */       throw new WebServiceException("Error marshalling W3CEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toWSDLSource() {
/* 128 */     Element wsdlElement = null;
/*     */     
/* 130 */     for (Element elem : this.elements) {
/* 131 */       if (elem.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") && elem
/* 132 */         .getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/* 133 */         wsdlElement = elem;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return new DOMSource(wsdlElement);
/*     */   }
/*     */ 
/*     */   
/*     */   private static JAXBContext getMSJaxbContext() {
/*     */     try {
/* 143 */       return JAXBContext.newInstance(new Class[] { MemberSubmissionEndpointReference.class });
/* 144 */     } catch (JAXBException e) {
/* 145 */       throw new WebServiceException("Error creating JAXBContext for MemberSubmissionEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @XmlType(name = "address", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public static class Address {
/*     */     @XmlValue
/*     */     public String uri;
/*     */     @XmlAnyAttribute
/*     */     public Map<QName, String> attributes;
/*     */   }
/*     */   
/*     */   @XmlType(name = "elements", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public static class Elements {
/*     */     @XmlAnyElement
/*     */     public List<Element> elements;
/*     */   }
/*     */   
/*     */   public static class AttributedQName {
/*     */     @XmlValue
/*     */     public QName name;
/*     */     @XmlAnyAttribute
/*     */     public Map<QName, String> attributes;
/*     */   }
/*     */   
/*     */   public static class ServiceNameType extends AttributedQName {
/*     */     @XmlAttribute(name = "PortName")
/*     */     public String portName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/MemberSubmissionEndpointReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */