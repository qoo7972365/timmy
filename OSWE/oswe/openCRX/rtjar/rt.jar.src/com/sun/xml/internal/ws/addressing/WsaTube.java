/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.WsaTubeHelperImpl;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.internal.ws.message.FaultDetailHeader;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.AddressingFeature;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WsaTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   @NotNull
/*     */   protected final WSDLPort wsdlPort;
/*     */   protected final WSBinding binding;
/*     */   final WsaTubeHelper helper;
/*     */   @NotNull
/*     */   protected final AddressingVersion addressingVersion;
/*     */   protected final SOAPVersion soapVersion;
/*     */   private final boolean addressingRequired;
/*     */   
/*     */   public WsaTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  84 */     super(next);
/*  85 */     this.wsdlPort = wsdlPort;
/*  86 */     this.binding = binding;
/*  87 */     addKnownHeadersToBinding(binding);
/*  88 */     this.addressingVersion = binding.getAddressingVersion();
/*  89 */     this.soapVersion = binding.getSOAPVersion();
/*  90 */     this.helper = getTubeHelper();
/*  91 */     this.addressingRequired = AddressingVersion.isRequired(binding);
/*     */   }
/*     */   
/*     */   public WsaTube(WsaTube that, TubeCloner cloner) {
/*  95 */     super(that, cloner);
/*  96 */     this.wsdlPort = that.wsdlPort;
/*  97 */     this.binding = that.binding;
/*  98 */     this.helper = that.helper;
/*  99 */     this.addressingVersion = that.addressingVersion;
/* 100 */     this.soapVersion = that.soapVersion;
/* 101 */     this.addressingRequired = that.addressingRequired;
/*     */   }
/*     */   
/*     */   private void addKnownHeadersToBinding(WSBinding binding) {
/* 105 */     for (AddressingVersion addrVersion : AddressingVersion.values()) {
/* 106 */       binding.addKnownHeader(addrVersion.actionTag);
/* 107 */       binding.addKnownHeader(addrVersion.faultDetailTag);
/* 108 */       binding.addKnownHeader(addrVersion.faultToTag);
/* 109 */       binding.addKnownHeader(addrVersion.fromTag);
/* 110 */       binding.addKnownHeader(addrVersion.messageIDTag);
/* 111 */       binding.addKnownHeader(addrVersion.relatesToTag);
/* 112 */       binding.addKnownHeader(addrVersion.replyToTag);
/* 113 */       binding.addKnownHeader(addrVersion.toTag);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 119 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   protected WsaTubeHelper getTubeHelper() {
/* 123 */     if (this.binding.isFeatureEnabled(AddressingFeature.class))
/* 124 */       return new WsaTubeHelperImpl(this.wsdlPort, null, this.binding); 
/* 125 */     if (this.binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class))
/*     */     {
/* 127 */       return (WsaTubeHelper)new WsaTubeHelperImpl(this.wsdlPort, null, this.binding);
/*     */     }
/*     */     
/* 130 */     throw new WebServiceException(AddressingMessages.ADDRESSING_NOT_ENABLED(getClass().getSimpleName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Packet validateInboundHeaders(Packet packet) {
/*     */     SOAPFault soapFault;
/*     */     FaultDetailHeader s11FaultDetailHeader;
/*     */     try {
/* 144 */       checkMessageAddressingProperties(packet);
/* 145 */       return packet;
/* 146 */     } catch (InvalidAddressingHeaderException e) {
/* 147 */       LOGGER.log(Level.WARNING, this.addressingVersion
/* 148 */           .getInvalidMapText() + ", Problem header:" + e.getProblemHeader() + ", Reason: " + e.getSubsubcode(), (Throwable)e);
/* 149 */       soapFault = this.helper.createInvalidAddressingHeaderFault(e, this.addressingVersion);
/* 150 */       s11FaultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getProblemHeader());
/* 151 */     } catch (MissingAddressingHeaderException e) {
/* 152 */       LOGGER.log(Level.WARNING, this.addressingVersion.getMapRequiredText() + ", Problem header:" + e.getMissingHeaderQName(), (Throwable)e);
/* 153 */       soapFault = this.helper.newMapRequiredFault(e);
/* 154 */       s11FaultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getMissingHeaderQName());
/*     */     } 
/*     */     
/* 157 */     if (soapFault != null) {
/*     */       
/* 159 */       if (this.wsdlPort != null && packet.getMessage().isOneWay(this.wsdlPort)) {
/* 160 */         return packet.createServerResponse(null, this.wsdlPort, null, this.binding);
/*     */       }
/*     */       
/* 163 */       Message m = Messages.create(soapFault);
/* 164 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 165 */         m.getHeaders().add((Header)s11FaultDetailHeader);
/*     */       }
/*     */       
/* 168 */       return packet.createServerResponse(m, this.wsdlPort, null, this.binding);
/*     */     } 
/*     */     
/* 171 */     return packet;
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
/*     */   protected void checkMessageAddressingProperties(Packet packet) {
/* 187 */     checkCardinality(packet);
/*     */   }
/*     */   
/*     */   final boolean isAddressingEngagedOrRequired(Packet packet, WSBinding binding) {
/* 191 */     if (AddressingVersion.isRequired(binding)) {
/* 192 */       return true;
/*     */     }
/* 194 */     if (packet == null) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (packet.getMessage() == null) {
/* 198 */       return false;
/*     */     }
/* 200 */     if (packet.getMessage().getHeaders() != null) {
/* 201 */       return false;
/*     */     }
/* 203 */     String action = AddressingUtils.getAction(packet
/* 204 */         .getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*     */     
/* 206 */     if (action == null) {
/* 207 */       return true;
/*     */     }
/* 209 */     return true;
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
/*     */   protected void checkCardinality(Packet packet) {
/* 227 */     Message message = packet.getMessage();
/* 228 */     if (message == null) {
/* 229 */       if (this.addressingRequired) {
/* 230 */         throw new WebServiceException(AddressingMessages.NULL_MESSAGE());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 235 */     Iterator<Header> hIter = message.getHeaders().getHeaders(this.addressingVersion.nsUri, true);
/*     */     
/* 237 */     if (!hIter.hasNext()) {
/*     */       
/* 239 */       if (this.addressingRequired)
/*     */       {
/* 241 */         throw new MissingAddressingHeaderException(this.addressingVersion.actionTag, packet);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 247 */     boolean foundFrom = false;
/* 248 */     boolean foundTo = false;
/* 249 */     boolean foundReplyTo = false;
/* 250 */     boolean foundFaultTo = false;
/* 251 */     boolean foundAction = false;
/* 252 */     boolean foundMessageId = false;
/* 253 */     boolean foundRelatesTo = false;
/* 254 */     QName duplicateHeader = null;
/*     */     
/* 256 */     while (hIter.hasNext()) {
/* 257 */       Header h = hIter.next();
/*     */ 
/*     */       
/* 260 */       if (!isInCurrentRole(h, this.binding)) {
/*     */         continue;
/*     */       }
/*     */       
/* 264 */       String local = h.getLocalPart();
/* 265 */       if (local.equals(this.addressingVersion.fromTag.getLocalPart())) {
/* 266 */         if (foundFrom) {
/* 267 */           duplicateHeader = this.addressingVersion.fromTag;
/*     */           break;
/*     */         } 
/* 270 */         foundFrom = true; continue;
/* 271 */       }  if (local.equals(this.addressingVersion.toTag.getLocalPart())) {
/* 272 */         if (foundTo) {
/* 273 */           duplicateHeader = this.addressingVersion.toTag;
/*     */           break;
/*     */         } 
/* 276 */         foundTo = true; continue;
/* 277 */       }  if (local.equals(this.addressingVersion.replyToTag.getLocalPart())) {
/* 278 */         if (foundReplyTo) {
/* 279 */           duplicateHeader = this.addressingVersion.replyToTag;
/*     */           break;
/*     */         } 
/* 282 */         foundReplyTo = true;
/*     */         try {
/* 284 */           h.readAsEPR(this.addressingVersion);
/* 285 */         } catch (XMLStreamException e) {
/* 286 */           throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*     */         }  continue;
/* 288 */       }  if (local.equals(this.addressingVersion.faultToTag.getLocalPart())) {
/* 289 */         if (foundFaultTo) {
/* 290 */           duplicateHeader = this.addressingVersion.faultToTag;
/*     */           break;
/*     */         } 
/* 293 */         foundFaultTo = true;
/*     */         try {
/* 295 */           h.readAsEPR(this.addressingVersion);
/* 296 */         } catch (XMLStreamException e) {
/* 297 */           throw new WebServiceException(AddressingMessages.FAULT_TO_CANNOT_PARSE(), e);
/*     */         }  continue;
/* 299 */       }  if (local.equals(this.addressingVersion.actionTag.getLocalPart())) {
/* 300 */         if (foundAction) {
/* 301 */           duplicateHeader = this.addressingVersion.actionTag;
/*     */           break;
/*     */         } 
/* 304 */         foundAction = true; continue;
/* 305 */       }  if (local.equals(this.addressingVersion.messageIDTag.getLocalPart())) {
/* 306 */         if (foundMessageId) {
/* 307 */           duplicateHeader = this.addressingVersion.messageIDTag;
/*     */           break;
/*     */         } 
/* 310 */         foundMessageId = true; continue;
/* 311 */       }  if (local.equals(this.addressingVersion.relatesToTag.getLocalPart())) {
/* 312 */         foundRelatesTo = true; continue;
/* 313 */       }  if (local.equals(this.addressingVersion.faultDetailTag.getLocalPart())) {
/*     */         continue;
/*     */       }
/*     */       
/* 317 */       System.err.println(AddressingMessages.UNKNOWN_WSA_HEADER());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (duplicateHeader != null) {
/* 323 */       throw new InvalidAddressingHeaderException(duplicateHeader, this.addressingVersion.invalidCardinalityTag);
/*     */     }
/*     */ 
/*     */     
/* 327 */     boolean engaged = foundAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     if (engaged || this.addressingRequired)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageId, foundRelatesTo);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isInCurrentRole(Header header, WSBinding binding) {
/* 352 */     if (binding == null)
/* 353 */       return true; 
/* 354 */     return ((SOAPBinding)binding).getRoles().contains(header.getRole(this.soapVersion));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final WSDLBoundOperation getWSDLBoundOperation(Packet packet) {
/* 360 */     if (this.wsdlPort == null)
/* 361 */       return null; 
/* 362 */     QName opName = packet.getWSDLOperation();
/* 363 */     if (opName != null)
/* 364 */       return this.wsdlPort.getBinding().get(opName); 
/* 365 */     return null;
/*     */   }
/*     */   
/*     */   protected void validateSOAPAction(Packet packet) {
/* 369 */     String gotA = AddressingUtils.getAction(packet
/* 370 */         .getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*     */     
/* 372 */     if (gotA == null)
/* 373 */       throw new WebServiceException(AddressingMessages.VALIDATION_SERVER_NULL_ACTION()); 
/* 374 */     if (packet.soapAction != null && !packet.soapAction.equals("\"\"") && !packet.soapAction.equals("\"" + gotA + "\"")) {
/* 375 */       throw new InvalidAddressingHeaderException(this.addressingVersion.actionTag, this.addressingVersion.actionMismatchTag);
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
/*     */   protected abstract void validateAction(Packet paramPacket);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {
/* 400 */     if (!foundAction)
/* 401 */       throw new MissingAddressingHeaderException(this.addressingVersion.actionTag, packet); 
/* 402 */     validateSOAPAction(packet);
/*     */   }
/* 404 */   private static final Logger LOGGER = Logger.getLogger(WsaTube.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */