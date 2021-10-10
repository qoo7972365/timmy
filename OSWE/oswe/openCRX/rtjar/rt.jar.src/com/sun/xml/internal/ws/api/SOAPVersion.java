/*     */ package com.sun.xml.internal.ws.api;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyle;
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyleFeature;
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SOAPVersion
/*     */ {
/*  76 */   SOAP_11("http://schemas.xmlsoap.org/wsdl/soap/http", "http://schemas.xmlsoap.org/soap/envelope/", "text/xml", "http://schemas.xmlsoap.org/soap/actor/next", "actor", "SOAP 1.1 Protocol", new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand"), "Client", "Server", 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     Collections.singleton("http://schemas.xmlsoap.org/soap/actor/next")),
/*     */   
/*  86 */   SOAP_12("http://www.w3.org/2003/05/soap/bindings/HTTP/", "http://www.w3.org/2003/05/soap-envelope", "application/soap+xml", "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver", "role", "SOAP 1.2 Protocol", new QName("http://www.w3.org/2003/05/soap-envelope", "MustUnderstand"), "Sender", "Receiver", new HashSet<>(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       Arrays.asList(new String[] { "http://www.w3.org/2003/05/soap-envelope/role/next", "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver" })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String httpBindingId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String nsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String contentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeMustUnderstand;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MessageFactory saajMessageFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SOAPFactory saajSoapFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String saajFactoryString;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String implicitRole;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<String> implicitRoleSet;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<String> requiredRoles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String roleAttributeName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeClient;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultCodeServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPVersion(String httpBindingId, String nsUri, String contentType, String implicitRole, String roleAttributeName, String saajFactoryString, QName faultCodeMustUnderstand, String faultCodeClientLocalName, String faultCodeServerLocalName, Set<String> requiredRoles) {
/* 167 */     this.httpBindingId = httpBindingId;
/* 168 */     this.nsUri = nsUri;
/* 169 */     this.contentType = contentType;
/* 170 */     this.implicitRole = implicitRole;
/* 171 */     this.implicitRoleSet = Collections.singleton(implicitRole);
/* 172 */     this.roleAttributeName = roleAttributeName;
/* 173 */     this.saajFactoryString = saajFactoryString;
/*     */     try {
/* 175 */       this.saajMessageFactory = MessageFactory.newInstance(saajFactoryString);
/* 176 */       this.saajSoapFactory = SOAPFactory.newInstance(saajFactoryString);
/* 177 */     } catch (SOAPException e) {
/* 178 */       throw new Error(e);
/* 179 */     } catch (NoSuchMethodError e) {
/*     */       
/* 181 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 182 */       x.initCause(e);
/* 183 */       throw x;
/*     */     } 
/* 185 */     this.faultCodeMustUnderstand = faultCodeMustUnderstand;
/* 186 */     this.requiredRoles = requiredRoles;
/* 187 */     this.faultCodeClient = new QName(nsUri, faultCodeClientLocalName);
/* 188 */     this.faultCodeServer = new QName(nsUri, faultCodeServerLocalName);
/*     */   }
/*     */   
/*     */   public SOAPFactory getSOAPFactory() {
/*     */     try {
/* 193 */       return SAAJFactory.getSOAPFactory(this.saajFactoryString);
/* 194 */     } catch (SOAPException e) {
/* 195 */       throw new Error(e);
/* 196 */     } catch (NoSuchMethodError e) {
/*     */       
/* 198 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 199 */       x.initCause(e);
/* 200 */       throw x;
/*     */     } 
/*     */   }
/*     */   
/*     */   public MessageFactory getMessageFactory() {
/*     */     try {
/* 206 */       return SAAJFactory.getMessageFactory(this.saajFactoryString);
/* 207 */     } catch (SOAPException e) {
/* 208 */       throw new Error(e);
/* 209 */     } catch (NoSuchMethodError e) {
/*     */       
/* 211 */       LinkageError x = new LinkageError("You are loading old SAAJ from " + Which.which(MessageFactory.class));
/* 212 */       x.initCause(e);
/* 213 */       throw x;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 218 */     return this.httpBindingId;
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
/*     */   public static SOAPVersion fromHttpBinding(String binding) {
/* 233 */     if (binding == null) {
/* 234 */       return SOAP_11;
/*     */     }
/* 236 */     if (binding.equals(SOAP_12.httpBindingId)) {
/* 237 */       return SOAP_12;
/*     */     }
/* 239 */     return SOAP_11;
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
/*     */   public static SOAPVersion fromNsUri(String nsUri) {
/* 253 */     if (nsUri.equals(SOAP_12.nsUri)) {
/* 254 */       return SOAP_12;
/*     */     }
/* 256 */     return SOAP_11;
/*     */   }
/*     */   
/*     */   public static SOAPVersion from(EnvelopeStyleFeature f) {
/* 260 */     EnvelopeStyle.Style[] style = f.getStyles();
/* 261 */     if (style.length != 1) throw new IllegalArgumentException("The EnvelopingFeature must has exactly one Enveloping.Style"); 
/* 262 */     return from(style[0]);
/*     */   }
/*     */   
/*     */   public static SOAPVersion from(EnvelopeStyle.Style style) {
/* 266 */     switch (style) { case SOAP11:
/* 267 */         return SOAP_11;
/* 268 */       case SOAP12: return SOAP_12; }
/*     */     
/* 270 */     return SOAP_11;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnvelopeStyleFeature toFeature() {
/* 275 */     return SOAP_11.equals(this) ? new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP11 }) : new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP12 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/SOAPVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */