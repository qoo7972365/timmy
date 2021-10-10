/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingPropertySet;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.OneWayFeature;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.message.RelatesToHeader;
/*     */ import com.sun.xml.internal.ws.message.StringHeader;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddressingUtils
/*     */ {
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action) {
/*  53 */     fillRequestAddressingHeaders(headers, packet, av, sv, oneway, action, false);
/*     */   }
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, Packet packet, AddressingVersion av, SOAPVersion sv, boolean oneway, String action, boolean mustUnderstand) {
/*  56 */     fillCommonAddressingHeaders(headers, packet, av, sv, action, mustUnderstand);
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (!oneway) {
/*  61 */       WSEndpointReference epr = av.anonymousEpr;
/*  62 */       if (headers.get(av.replyToTag, false) == null) {
/*  63 */         headers.add(epr.createHeader(av.replyToTag));
/*     */       }
/*     */ 
/*     */       
/*  67 */       if (headers.get(av.faultToTag, false) == null) {
/*  68 */         headers.add(epr.createHeader(av.faultToTag));
/*     */       }
/*     */ 
/*     */       
/*  72 */       if (packet.getMessage().getHeaders().get(av.messageIDTag, false) == null && 
/*  73 */         headers.get(av.messageIDTag, false) == null) {
/*  74 */         StringHeader stringHeader = new StringHeader(av.messageIDTag, Message.generateMessageID());
/*  75 */         headers.add((Header)stringHeader);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void fillRequestAddressingHeaders(MessageHeaders headers, WSDLPort wsdlPort, WSBinding binding, Packet packet) {
/*  82 */     if (binding == null) {
/*  83 */       throw new IllegalArgumentException(AddressingMessages.NULL_BINDING());
/*     */     }
/*     */     
/*  86 */     if (binding.isFeatureEnabled(SuppressAutomaticWSARequestHeadersFeature.class)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  91 */     MessageHeaders hl = packet.getMessage().getHeaders();
/*  92 */     String action = getAction(hl, binding.getAddressingVersion(), binding.getSOAPVersion());
/*  93 */     if (action != null) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     AddressingVersion addressingVersion = binding.getAddressingVersion();
/*     */     
/*  99 */     WsaTubeHelper wsaHelper = addressingVersion.getWsaHelper(wsdlPort, null, binding);
/*     */ 
/*     */     
/* 102 */     String effectiveInputAction = wsaHelper.getEffectiveInputAction(packet);
/* 103 */     if (effectiveInputAction == null || (effectiveInputAction.equals("") && binding.getSOAPVersion() == SOAPVersion.SOAP_11)) {
/* 104 */       throw new WebServiceException(ClientMessages.INVALID_SOAP_ACTION());
/*     */     }
/* 106 */     boolean oneway = !packet.expectReply.booleanValue();
/* 107 */     if (wsdlPort != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 112 */       if (!oneway && packet.getMessage() != null && packet.getWSDLOperation() != null) {
/* 113 */         WSDLBoundOperation wbo = wsdlPort.getBinding().get(packet.getWSDLOperation());
/* 114 */         if (wbo != null && wbo.getAnonymous() == WSDLBoundOperation.ANONYMOUS.prohibited) {
/* 115 */           throw new WebServiceException(AddressingMessages.WSAW_ANONYMOUS_PROHIBITED());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 120 */     OneWayFeature oneWayFeature = (OneWayFeature)binding.getFeature(OneWayFeature.class);
/* 121 */     AddressingPropertySet addressingPropertySet = (AddressingPropertySet)packet.getSatellite(AddressingPropertySet.class);
/* 122 */     oneWayFeature = (addressingPropertySet == null) ? oneWayFeature : new OneWayFeature(addressingPropertySet, addressingVersion);
/*     */     
/* 124 */     if (oneWayFeature == null || !oneWayFeature.isEnabled()) {
/*     */       
/* 126 */       fillRequestAddressingHeaders(headers, packet, addressingVersion, binding.getSOAPVersion(), oneway, effectiveInputAction, AddressingVersion.isRequired(binding));
/*     */     } else {
/*     */       
/* 129 */       fillRequestAddressingHeaders(headers, packet, addressingVersion, binding.getSOAPVersion(), oneWayFeature, oneway, effectiveInputAction);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getAction(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 134 */     if (av == null) {
/* 135 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 138 */     String action = null;
/* 139 */     Header h = getFirstHeader(headers, av.actionTag, true, sv);
/* 140 */     if (h != null) {
/* 141 */       action = h.getStringContent();
/*     */     }
/*     */     
/* 144 */     return action;
/*     */   }
/*     */   
/*     */   public static WSEndpointReference getFaultTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 148 */     if (av == null) {
/* 149 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 152 */     Header h = getFirstHeader(headers, av.faultToTag, true, sv);
/* 153 */     WSEndpointReference faultTo = null;
/* 154 */     if (h != null) {
/*     */       try {
/* 156 */         faultTo = h.readAsEPR(av);
/* 157 */       } catch (XMLStreamException e) {
/* 158 */         throw new WebServiceException(AddressingMessages.FAULT_TO_CANNOT_PARSE(), e);
/*     */       } 
/*     */     }
/*     */     
/* 162 */     return faultTo;
/*     */   }
/*     */   
/*     */   public static String getMessageID(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 166 */     if (av == null) {
/* 167 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 170 */     Header h = getFirstHeader(headers, av.messageIDTag, true, sv);
/* 171 */     String messageId = null;
/* 172 */     if (h != null) {
/* 173 */       messageId = h.getStringContent();
/*     */     }
/*     */     
/* 176 */     return messageId;
/*     */   }
/*     */   public static String getRelatesTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/* 179 */     if (av == null) {
/* 180 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 183 */     Header h = getFirstHeader(headers, av.relatesToTag, true, sv);
/* 184 */     String relatesTo = null;
/* 185 */     if (h != null) {
/* 186 */       relatesTo = h.getStringContent();
/*     */     }
/*     */     
/* 189 */     return relatesTo;
/*     */   } public static WSEndpointReference getReplyTo(@NotNull MessageHeaders headers, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/*     */     WSEndpointReference replyTo;
/* 192 */     if (av == null) {
/* 193 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 196 */     Header h = getFirstHeader(headers, av.replyToTag, true, sv);
/*     */     
/* 198 */     if (h != null) {
/*     */       try {
/* 200 */         replyTo = h.readAsEPR(av);
/* 201 */       } catch (XMLStreamException e) {
/* 202 */         throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*     */       } 
/*     */     } else {
/* 205 */       replyTo = av.anonymousEpr;
/*     */     } 
/*     */     
/* 208 */     return replyTo;
/*     */   } public static String getTo(MessageHeaders headers, AddressingVersion av, SOAPVersion sv) {
/*     */     String to;
/* 211 */     if (av == null) {
/* 212 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 215 */     Header h = getFirstHeader(headers, av.toTag, true, sv);
/*     */     
/* 217 */     if (h != null) {
/* 218 */       to = h.getStringContent();
/*     */     } else {
/* 220 */       to = av.anonymousUri;
/*     */     } 
/*     */     
/* 223 */     return to;
/*     */   }
/*     */   
/*     */   public static Header getFirstHeader(MessageHeaders headers, QName name, boolean markUnderstood, SOAPVersion sv) {
/* 227 */     if (sv == null) {
/* 228 */       throw new IllegalArgumentException(AddressingMessages.NULL_SOAP_VERSION());
/*     */     }
/*     */     
/* 231 */     Iterator<Header> iter = headers.getHeaders(name.getNamespaceURI(), name.getLocalPart(), markUnderstood);
/* 232 */     while (iter.hasNext()) {
/* 233 */       Header h = iter.next();
/* 234 */       if (h.getRole(sv).equals(sv.implicitRole)) {
/* 235 */         return h;
/*     */       }
/*     */     } 
/*     */     
/* 239 */     return null;
/*     */   }
/*     */   
/*     */   private static void fillRequestAddressingHeaders(@NotNull MessageHeaders headers, @NotNull Packet packet, @NotNull AddressingVersion av, @NotNull SOAPVersion sv, @NotNull OneWayFeature oneWayFeature, boolean oneway, @NotNull String action) {
/* 243 */     if (!oneway && !oneWayFeature.isUseAsyncWithSyncInvoke() && Boolean.TRUE.equals(packet.isSynchronousMEP)) {
/* 244 */       fillRequestAddressingHeaders(headers, packet, av, sv, oneway, action);
/*     */     } else {
/* 246 */       fillCommonAddressingHeaders(headers, packet, av, sv, action, false);
/*     */       
/* 248 */       boolean isMessageIdAdded = false;
/*     */ 
/*     */ 
/*     */       
/* 252 */       if (headers.get(av.replyToTag, false) == null) {
/* 253 */         WSEndpointReference replyToEpr = oneWayFeature.getReplyTo();
/* 254 */         if (replyToEpr != null) {
/* 255 */           headers.add(replyToEpr.createHeader(av.replyToTag));
/*     */           
/* 257 */           if (packet.getMessage().getHeaders().get(av.messageIDTag, false) == null) {
/*     */             
/* 259 */             String newID = (oneWayFeature.getMessageId() == null) ? Message.generateMessageID() : oneWayFeature.getMessageId();
/* 260 */             headers.add((Header)new StringHeader(av.messageIDTag, newID));
/* 261 */             isMessageIdAdded = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 267 */       String messageId = oneWayFeature.getMessageId();
/* 268 */       if (!isMessageIdAdded && messageId != null) {
/* 269 */         headers.add((Header)new StringHeader(av.messageIDTag, messageId));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 274 */       if (headers.get(av.faultToTag, false) == null) {
/* 275 */         WSEndpointReference faultToEpr = oneWayFeature.getFaultTo();
/* 276 */         if (faultToEpr != null) {
/* 277 */           headers.add(faultToEpr.createHeader(av.faultToTag));
/*     */           
/* 279 */           if (headers.get(av.messageIDTag, false) == null) {
/* 280 */             headers.add((Header)new StringHeader(av.messageIDTag, Message.generateMessageID()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 286 */       if (oneWayFeature.getFrom() != null) {
/* 287 */         headers.addOrReplace(oneWayFeature.getFrom().createHeader(av.fromTag));
/*     */       }
/*     */ 
/*     */       
/* 291 */       if (oneWayFeature.getRelatesToID() != null) {
/* 292 */         headers.addOrReplace((Header)new RelatesToHeader(av.relatesToTag, oneWayFeature.getRelatesToID()));
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
/*     */   private static void fillCommonAddressingHeaders(MessageHeaders headers, Packet packet, @NotNull AddressingVersion av, @NotNull SOAPVersion sv, @NotNull String action, boolean mustUnderstand) {
/* 307 */     if (packet == null) {
/* 308 */       throw new IllegalArgumentException(AddressingMessages.NULL_PACKET());
/*     */     }
/*     */     
/* 311 */     if (av == null) {
/* 312 */       throw new IllegalArgumentException(AddressingMessages.NULL_ADDRESSING_VERSION());
/*     */     }
/*     */     
/* 315 */     if (sv == null) {
/* 316 */       throw new IllegalArgumentException(AddressingMessages.NULL_SOAP_VERSION());
/*     */     }
/*     */     
/* 319 */     if (action == null && !sv.httpBindingId.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/")) {
/* 320 */       throw new IllegalArgumentException(AddressingMessages.NULL_ACTION());
/*     */     }
/*     */ 
/*     */     
/* 324 */     if (headers.get(av.toTag, false) == null) {
/* 325 */       StringHeader h = new StringHeader(av.toTag, packet.endpointAddress.toString());
/* 326 */       headers.add((Header)h);
/*     */     } 
/*     */ 
/*     */     
/* 330 */     if (action != null) {
/* 331 */       packet.soapAction = action;
/* 332 */       if (headers.get(av.actionTag, false) == null) {
/*     */ 
/*     */         
/* 335 */         StringHeader h = new StringHeader(av.actionTag, action, sv, mustUnderstand);
/* 336 */         headers.add((Header)h);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/AddressingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */