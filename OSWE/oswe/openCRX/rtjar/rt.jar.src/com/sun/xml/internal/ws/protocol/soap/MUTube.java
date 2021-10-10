/*     */ package com.sun.xml.internal.ws.protocol.soap;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.message.DOMHeader;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
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
/*     */ abstract class MUTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private static final String MU_FAULT_DETAIL_LOCALPART = "NotUnderstood";
/*  60 */   private static final QName MU_HEADER_DETAIL = new QName(SOAPVersion.SOAP_12.nsUri, "NotUnderstood");
/*     */   
/*  62 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.soap.decoder");
/*     */   
/*     */   private static final String MUST_UNDERSTAND_FAULT_MESSAGE_STRING = "One or more mandatory SOAP header blocks not understood";
/*     */   
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   protected SOAPBindingImpl binding;
/*     */   
/*     */   protected MUTube(WSBinding binding, Tube next) {
/*  71 */     super(next);
/*     */     
/*  73 */     if (!(binding instanceof javax.xml.ws.soap.SOAPBinding)) {
/*  74 */       throw new WebServiceException("MUPipe should n't be used for bindings other than SOAP.");
/*     */     }
/*     */     
/*  77 */     this.binding = (SOAPBindingImpl)binding;
/*  78 */     this.soapVersion = binding.getSOAPVersion();
/*     */   }
/*     */   
/*     */   protected MUTube(MUTube that, TubeCloner cloner) {
/*  82 */     super(that, cloner);
/*  83 */     this.binding = that.binding;
/*  84 */     this.soapVersion = that.soapVersion;
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
/*     */   public final Set<QName> getMisUnderstoodHeaders(MessageHeaders headers, Set<String> roles, Set<QName> handlerKnownHeaders) {
/*  97 */     return headers.getNotUnderstoodHeaders(roles, handlerKnownHeaders, (WSBinding)this.binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final SOAPFaultException createMUSOAPFaultException(Set<QName> notUnderstoodHeaders) {
/*     */     try {
/* 108 */       SOAPFault fault = this.soapVersion.getSOAPFactory().createFault("One or more mandatory SOAP header blocks not understood", this.soapVersion.faultCodeMustUnderstand);
/*     */ 
/*     */       
/* 111 */       fault.setFaultString("MustUnderstand headers:" + notUnderstoodHeaders + " are not understood");
/*     */       
/* 113 */       return new SOAPFaultException(fault);
/* 114 */     } catch (SOAPException e) {
/* 115 */       throw new WebServiceException(e);
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
/*     */   final Message createMUSOAPFaultMessage(Set<QName> notUnderstoodHeaders) {
/*     */     try {
/* 130 */       String faultString = "One or more mandatory SOAP header blocks not understood";
/* 131 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 132 */         faultString = "MustUnderstand headers:" + notUnderstoodHeaders + " are not understood";
/*     */       }
/* 134 */       Message muFaultMessage = SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, faultString, this.soapVersion.faultCodeMustUnderstand);
/*     */ 
/*     */       
/* 137 */       if (this.soapVersion == SOAPVersion.SOAP_12) {
/* 138 */         addHeader(muFaultMessage, notUnderstoodHeaders);
/*     */       }
/* 140 */       return muFaultMessage;
/* 141 */     } catch (SOAPException e) {
/* 142 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addHeader(Message m, Set<QName> notUnderstoodHeaders) throws SOAPException {
/* 147 */     for (QName qname : notUnderstoodHeaders) {
/* 148 */       SOAPElement soapEl = SOAPVersion.SOAP_12.getSOAPFactory().createElement(MU_HEADER_DETAIL);
/* 149 */       soapEl.addNamespaceDeclaration("abc", qname.getNamespaceURI());
/* 150 */       soapEl.setAttribute("qname", "abc:" + qname.getLocalPart());
/* 151 */       DOMHeader dOMHeader = new DOMHeader((Element)soapEl);
/* 152 */       m.getHeaders().add((Header)dOMHeader);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/protocol/soap/MUTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */