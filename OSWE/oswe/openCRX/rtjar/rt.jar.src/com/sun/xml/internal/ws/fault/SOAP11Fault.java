/*     */ package com.sun.xml.internal.ws.fault;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "", propOrder = {"faultcode", "faultstring", "faultactor", "detail"})
/*     */ @XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
/*     */ class SOAP11Fault
/*     */   extends SOAPFaultBuilder
/*     */ {
/*     */   @XmlElement(namespace = "")
/*     */   private QName faultcode;
/*     */   @XmlElement(namespace = "")
/*     */   private String faultstring;
/*     */   @XmlElement(namespace = "")
/*     */   private String faultactor;
/*     */   @XmlElement(namespace = "")
/*     */   private DetailType detail;
/*     */   
/*     */   SOAP11Fault() {}
/*     */   
/*     */   SOAP11Fault(QName code, String reason, String actor, Element detailObject) {
/* 100 */     this.faultcode = code;
/* 101 */     this.faultstring = reason;
/* 102 */     this.faultactor = actor;
/* 103 */     if (detailObject != null) {
/* 104 */       if ((detailObject.getNamespaceURI() == null || ""
/* 105 */         .equals(detailObject.getNamespaceURI())) && "detail".equals(detailObject.getLocalName())) {
/* 106 */         this.detail = new DetailType();
/* 107 */         for (Element detailEntry : DOMUtil.getChildElements(detailObject)) {
/* 108 */           this.detail.getDetails().add(detailEntry);
/*     */         }
/*     */       } else {
/* 111 */         this.detail = new DetailType(detailObject);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   SOAP11Fault(SOAPFault fault) {
/* 117 */     this.faultcode = fault.getFaultCodeAsQName();
/* 118 */     this.faultstring = fault.getFaultString();
/* 119 */     this.faultactor = fault.getFaultActor();
/* 120 */     if (fault.getDetail() != null) {
/* 121 */       this.detail = new DetailType();
/* 122 */       Iterator<Element> iter = fault.getDetail().getDetailEntries();
/* 123 */       while (iter.hasNext()) {
/* 124 */         Element fd = iter.next();
/* 125 */         this.detail.getDetails().add(fd);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   QName getFaultcode() {
/* 131 */     return this.faultcode;
/*     */   }
/*     */   
/*     */   void setFaultcode(QName faultcode) {
/* 135 */     this.faultcode = faultcode;
/*     */   }
/*     */ 
/*     */   
/*     */   String getFaultString() {
/* 140 */     return this.faultstring;
/*     */   }
/*     */   
/*     */   void setFaultstring(String faultstring) {
/* 144 */     this.faultstring = faultstring;
/*     */   }
/*     */   
/*     */   String getFaultactor() {
/* 148 */     return this.faultactor;
/*     */   }
/*     */   
/*     */   void setFaultactor(String faultactor) {
/* 152 */     this.faultactor = faultactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DetailType getDetail() {
/* 160 */     return this.detail;
/*     */   }
/*     */   
/*     */   void setDetail(DetailType detail) {
/* 164 */     this.detail = detail;
/*     */   }
/*     */   
/*     */   protected Throwable getProtocolException() {
/*     */     try {
/* 169 */       SOAPFault fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault(this.faultstring, this.faultcode);
/* 170 */       fault.setFaultActor(this.faultactor);
/* 171 */       if (this.detail != null) {
/* 172 */         Detail d = fault.addDetail();
/* 173 */         for (Element det : this.detail.getDetails()) {
/* 174 */           Node n = fault.getOwnerDocument().importNode(det, true);
/* 175 */           d.appendChild(n);
/*     */         } 
/*     */       } 
/* 178 */       return (Throwable)new ServerSOAPFaultException(fault);
/* 179 */     } catch (SOAPException e) {
/* 180 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/fault/SOAP11Fault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */