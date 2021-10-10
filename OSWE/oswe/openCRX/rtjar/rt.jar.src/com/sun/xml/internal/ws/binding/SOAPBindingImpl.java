/*     */ package com.sun.xml.internal.ws.binding;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SOAPBindingImpl
/*     */   extends BindingImpl
/*     */   implements SOAPBinding
/*     */ {
/*     */   public static final String X_SOAP12HTTP_BINDING = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/";
/*     */   private static final String ROLE_NONE = "http://www.w3.org/2003/05/soap-envelope/role/none";
/*     */   protected final SOAPVersion soapVersion;
/*  58 */   private Set<QName> portKnownHeaders = Collections.emptySet();
/*  59 */   private Set<QName> bindingUnderstoodHeaders = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPBindingImpl(BindingID bindingId) {
/*  67 */     this(bindingId, EMPTY_FEATURES);
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
/*     */   SOAPBindingImpl(BindingID bindingId, WebServiceFeature... features) {
/*  80 */     super(bindingId, features);
/*  81 */     this.soapVersion = bindingId.getSOAPVersion();
/*     */     
/*  83 */     setRoles(new HashSet<>());
/*     */ 
/*     */ 
/*     */     
/*  87 */     this.features.addAll((Iterable<WebServiceFeature>)bindingId.createBuiltinFeatureList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortKnownHeaders(@NotNull Set<QName> headers) {
/*  98 */     this.portKnownHeaders = headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsHeader(QName header) {
/* 107 */     return (this.serviceMode == Service.Mode.MESSAGE || this.portKnownHeaders
/* 108 */       .contains(header) || this.bindingUnderstoodHeaders
/* 109 */       .contains(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHandlerChain(List<Handler> chain) {
/* 119 */     setHandlerConfig(new HandlerConfiguration(getHandlerConfig().getRoles(), chain));
/*     */   }
/*     */   
/*     */   protected void addRequiredRoles(Set<String> roles) {
/* 123 */     roles.addAll(this.soapVersion.requiredRoles);
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 127 */     return getHandlerConfig().getRoles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoles(Set<String> roles) {
/* 136 */     if (roles == null) {
/* 137 */       roles = new HashSet<>();
/*     */     }
/* 139 */     if (roles.contains("http://www.w3.org/2003/05/soap-envelope/role/none")) {
/* 140 */       throw new WebServiceException(ClientMessages.INVALID_SOAP_ROLE_NONE());
/*     */     }
/* 142 */     addRequiredRoles(roles);
/* 143 */     setHandlerConfig(new HandlerConfiguration(roles, getHandlerConfig()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMTOMEnabled() {
/* 151 */     return isFeatureEnabled((Class)MTOMFeature.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMTOMEnabled(boolean b) {
/* 158 */     this.features.setMTOMEnabled(b);
/*     */   }
/*     */   
/*     */   public SOAPFactory getSOAPFactory() {
/* 162 */     return this.soapVersion.getSOAPFactory();
/*     */   }
/*     */   
/*     */   public MessageFactory getMessageFactory() {
/* 166 */     return this.soapVersion.getMessageFactory();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/binding/SOAPBindingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */