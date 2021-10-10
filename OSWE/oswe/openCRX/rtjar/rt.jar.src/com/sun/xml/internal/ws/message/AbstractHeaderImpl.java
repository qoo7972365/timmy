/*     */ package com.sun.xml.internal.ws.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.BridgeContext;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractHeaderImpl
/*     */   implements Header
/*     */ {
/*     */   public final <T> T readAsJAXB(Bridge<T> bridge, BridgeContext context) throws JAXBException {
/*  65 */     return readAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*     */     try {
/*  70 */       return (T)unmarshaller.unmarshal(readHeader());
/*  71 */     } catch (Exception e) {
/*  72 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/*     */     try {
/*  78 */       return (T)bridge.unmarshal(readHeader());
/*  79 */     } catch (XMLStreamException e) {
/*  80 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/*     */     try {
/*  86 */       return (T)bridge.unmarshal(readHeader(), null);
/*  87 */     } catch (XMLStreamException e) {
/*  88 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
/*  96 */     XMLStreamReader xsr = readHeader();
/*  97 */     WSEndpointReference epr = new WSEndpointReference(xsr, expected);
/*  98 */     XMLStreamReaderFactory.recycle(xsr);
/*  99 */     return epr;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
/* 104 */     String v = getAttribute(soapVersion.nsUri, "mustUnderstand");
/* 105 */     if (v == null || !parseBool(v)) return true;
/*     */     
/* 107 */     if (roles == null) return true;
/*     */ 
/*     */     
/* 110 */     return !roles.contains(getRole(soapVersion));
/*     */   }
/*     */   @NotNull
/*     */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 114 */     String v = getAttribute(soapVersion.nsUri, soapVersion.roleAttributeName);
/* 115 */     if (v == null)
/* 116 */       v = soapVersion.implicitRole; 
/* 117 */     return v;
/*     */   }
/*     */   
/*     */   public boolean isRelay() {
/* 121 */     String v = getAttribute(SOAPVersion.SOAP_12.nsUri, "relay");
/* 122 */     if (v == null) return false; 
/* 123 */     return parseBool(v);
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 127 */     return getAttribute(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean parseBool(String value) {
/* 136 */     if (value.length() == 0) {
/* 137 */       return false;
/*     */     }
/* 139 */     char ch = value.charAt(0);
/* 140 */     return (ch == 't' || ch == '1');
/*     */   }
/*     */   
/*     */   public String getStringContent() {
/*     */     try {
/* 145 */       XMLStreamReader xsr = readHeader();
/* 146 */       xsr.nextTag();
/* 147 */       return xsr.getElementText();
/* 148 */     } catch (XMLStreamException e) {
/* 149 */       return null;
/*     */     } 
/*     */   }
/*     */   
/* 153 */   protected static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/AbstractHeaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */