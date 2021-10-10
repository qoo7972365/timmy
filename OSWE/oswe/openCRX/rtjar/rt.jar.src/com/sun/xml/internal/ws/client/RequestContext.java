/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BaseDistributedPropertySet;
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.internal.api.message.DistributedPropertySet;
/*     */ import com.oracle.webservices.internal.api.message.MessageContext;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.PropertySet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.transport.Headers;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RequestContext
/*     */   extends BaseDistributedPropertySet
/*     */ {
/*  88 */   private static final Logger LOGGER = Logger.getLogger(RequestContext.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static ContentNegotiation defaultContentNegotiation = ContentNegotiation.obtainFromSystemProperty();
/*     */   
/*     */   @NotNull
/*     */   private EndpointAddress endpointAddress;
/*     */   
/*     */   public void addSatellite(@NotNull PropertySet satellite) {
/* 104 */     addSatellite((PropertySet)satellite);
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
/*     */   @Property({"javax.xml.ws.service.endpoint.address"})
/*     */   public String getEndPointAddressString() {
/* 124 */     return (this.endpointAddress != null) ? this.endpointAddress.toString() : null;
/*     */   }
/*     */   
/*     */   public void setEndPointAddressString(String s) {
/* 128 */     if (s == null) {
/* 129 */       throw new IllegalArgumentException();
/*     */     }
/* 131 */     this.endpointAddress = EndpointAddress.create(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndpointAddress(@NotNull EndpointAddress epa) {
/* 136 */     this.endpointAddress = epa;
/*     */   }
/*     */   @NotNull
/*     */   public EndpointAddress getEndpointAddress() {
/* 140 */     return this.endpointAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public ContentNegotiation contentNegotiation = defaultContentNegotiation; private String soapAction;
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.client.ContentNegotiation"})
/*     */   public String getContentNegotiationString() {
/* 151 */     return this.contentNegotiation.toString();
/*     */   }
/*     */   private Boolean soapActionUse;
/*     */   public void setContentNegotiationString(String s) {
/* 155 */     if (s == null) {
/* 156 */       this.contentNegotiation = ContentNegotiation.none;
/*     */     } else {
/*     */       try {
/* 159 */         this.contentNegotiation = ContentNegotiation.valueOf(s);
/* 160 */       } catch (IllegalArgumentException e) {
/*     */         
/* 162 */         this.contentNegotiation = ContentNegotiation.none;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.soap.http.soapaction.uri"})
/*     */   public String getSoapAction() {
/* 196 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public void setSoapAction(String sAction) {
/* 200 */     this.soapAction = sAction;
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
/*     */   @Property({"javax.xml.ws.soap.http.soapaction.use"})
/*     */   public Boolean getSoapActionUse() {
/* 214 */     return this.soapActionUse;
/*     */   }
/*     */   
/*     */   public void setSoapActionUse(Boolean sActionUse) {
/* 218 */     this.soapActionUse = sActionUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RequestContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestContext(RequestContext that) {
/* 231 */     for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)that.asMapLocal().entrySet()) {
/* 232 */       if (!propMap.containsKey(entry.getKey())) {
/* 233 */         asMap().put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/* 236 */     this.endpointAddress = that.endpointAddress;
/* 237 */     this.soapAction = that.soapAction;
/* 238 */     this.soapActionUse = that.soapActionUse;
/* 239 */     this.contentNegotiation = that.contentNegotiation;
/* 240 */     that.copySatelliteInto((DistributedPropertySet)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 248 */     if (supports(key)) {
/* 249 */       return super.get(key);
/*     */     }
/*     */     
/* 252 */     return asMap().get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 262 */     if (supports(key)) {
/* 263 */       return super.put(key, value);
/*     */     }
/*     */     
/* 266 */     return asMap().put(key, value);
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
/*     */   public void fill(Packet packet, boolean isAddressingEnabled) {
/* 281 */     if (this.endpointAddress != null) {
/* 282 */       packet.endpointAddress = this.endpointAddress;
/*     */     }
/* 284 */     packet.contentNegotiation = this.contentNegotiation;
/* 285 */     fillSOAPAction(packet, isAddressingEnabled);
/* 286 */     mergeRequestHeaders(packet);
/*     */     
/* 288 */     Set<String> handlerScopeNames = new HashSet<>();
/*     */     
/* 290 */     copySatelliteInto((MessageContext)packet);
/*     */ 
/*     */     
/* 293 */     for (String key : asMapLocal().keySet()) {
/*     */ 
/*     */       
/* 296 */       if (!supportsLocal(key)) {
/* 297 */         handlerScopeNames.add(key);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 302 */       if (!propMap.containsKey(key)) {
/* 303 */         Object value = asMapLocal().get(key);
/* 304 */         if (packet.supports(key)) {
/*     */           
/* 306 */           packet.put(key, value); continue;
/*     */         } 
/* 308 */         packet.invocationProperties.put(key, value);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 313 */     if (!handlerScopeNames.isEmpty()) {
/* 314 */       packet.getHandlerScopePropertyNames(false).addAll(handlerScopeNames);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeRequestHeaders(Packet packet) {
/* 322 */     Headers packetHeaders = (Headers)packet.invocationProperties.get("javax.xml.ws.http.request.headers");
/*     */     
/* 324 */     Map<String, List<String>> myHeaders = (Map<String, List<String>>)asMap().get("javax.xml.ws.http.request.headers");
/* 325 */     if (packetHeaders != null && myHeaders != null) {
/*     */       
/* 327 */       for (Map.Entry<String, List<String>> entry : myHeaders.entrySet()) {
/* 328 */         String key = entry.getKey();
/* 329 */         if (key != null && key.trim().length() != 0) {
/* 330 */           List<String> listFromPacket = (List<String>)packetHeaders.get(key);
/*     */           
/* 332 */           if (listFromPacket != null) {
/* 333 */             listFromPacket.addAll(entry.getValue());
/*     */             continue;
/*     */           } 
/* 336 */           packetHeaders.put(key, myHeaders.get(key));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 341 */       asMap().put("javax.xml.ws.http.request.headers", packetHeaders);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillSOAPAction(Packet packet, boolean isAddressingEnabled) {
/* 346 */     boolean p = packet.packetTakesPriorityOverRequestContext;
/* 347 */     String localSoapAction = p ? packet.soapAction : this.soapAction;
/* 348 */     Boolean localSoapActionUse = p ? (Boolean)packet.invocationProperties.get("javax.xml.ws.soap.http.soapaction.use") : this.soapActionUse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     if (((localSoapActionUse != null && localSoapActionUse.booleanValue()) || (localSoapActionUse == null && isAddressingEnabled)) && 
/* 356 */       localSoapAction != null) {
/* 357 */       packet.soapAction = localSoapAction;
/*     */     }
/*     */ 
/*     */     
/* 361 */     if (!isAddressingEnabled && (localSoapActionUse == null || !localSoapActionUse.booleanValue()) && localSoapAction != null) {
/* 362 */       LOGGER.warning("BindingProvider.SOAPACTION_URI_PROPERTY is set in the RequestContext but is ineffective, Either set BindingProvider.SOAPACTION_USE_PROPERTY to true or enable AddressingFeature");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestContext copy() {
/* 368 */     return new RequestContext(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 373 */     return propMap;
/*     */   }
/*     */   
/* 376 */   private static final BasePropertySet.PropertyMap propMap = parse(RequestContext.class);
/*     */ 
/*     */   
/*     */   protected boolean mapAllowsAdditionalProperties() {
/* 380 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/RequestContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */