/*     */ package com.sun.xml.internal.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.internal.ws.addressing.WsaTubeHelperImpl;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.WsaTubeHelperImpl;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.internal.ws.message.stream.OutboundStreamHeader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum AddressingVersion
/*     */ {
/*  59 */   W3C("http://www.w3.org/2005/08/addressing", "wsa", "<EndpointReference xmlns=\"http://www.w3.org/2005/08/addressing\">\n    <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>\n</EndpointReference>", "http://www.w3.org/2006/05/addressing/wsdl", "http://www.w3.org/2006/05/addressing/wsdl", "http://www.w3.org/2005/08/addressing/anonymous", "http://www.w3.org/2005/08/addressing/none", new EPR((Class)W3CEndpointReference.class, "Address", "ServiceName", "EndpointName", "InterfaceName", new QName("http://www.w3.org/2005/08/addressing", "Metadata", "wsa"), "ReferenceParameters", null))
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getActionMismatchLocalName()
/*     */     {
/*  76 */       return "ActionMismatch";
/*     */     }
/*     */     
/*     */     public boolean isReferenceParameter(String localName) {
/*  80 */       return localName.equals("ReferenceParameters");
/*     */     }
/*     */ 
/*     */     
/*     */     public WsaTubeHelper getWsaHelper(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/*  85 */       return (WsaTubeHelper)new WsaTubeHelperImpl(wsdlPort, seiModel, binding);
/*     */     }
/*     */ 
/*     */     
/*     */     String getMapRequiredLocalName() {
/*  90 */       return "MessageAddressingHeaderRequired";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMapRequiredText() {
/*  95 */       return "A required header representing a Message Addressing Property is not present";
/*     */     }
/*     */     
/*     */     String getInvalidAddressLocalName() {
/*  99 */       return "InvalidAddress";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidMapLocalName() {
/* 104 */       return "InvalidAddressingHeader";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getInvalidMapText() {
/* 109 */       return "A header representing a Message Addressing Property is not valid and the message cannot be processed";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidCardinalityLocalName() {
/* 114 */       return "InvalidCardinality";
/*     */     }
/*     */     
/*     */     Header createReferenceParameterHeader(XMLStreamBuffer mark, String nsUri, String localName) {
/* 118 */       return (Header)new OutboundReferenceParameterHeader(mark, nsUri, localName);
/*     */     }
/*     */     
/*     */     String getIsReferenceParameterLocalName() {
/* 122 */       return "IsReferenceParameter";
/*     */     }
/*     */     
/*     */     String getWsdlAnonymousLocalName() {
/* 126 */       return "Anonymous";
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 130 */       return "wsa";
/*     */     }
/*     */     
/*     */     public String getWsdlPrefix() {
/* 134 */       return "wsaw";
/*     */     }
/*     */     
/*     */     public Class<? extends WebServiceFeature> getFeatureClass() {
/* 138 */       return (Class)AddressingFeature.class;
/*     */     }
/*     */   },
/* 141 */   MEMBER("http://schemas.xmlsoap.org/ws/2004/08/addressing", "wsa", "<EndpointReference xmlns=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\">\n    <Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</Address>\n</EndpointReference>", "http://schemas.xmlsoap.org/ws/2004/08/addressing", "http://schemas.xmlsoap.org/ws/2004/08/addressing/policy", "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous", "", new EPR((Class)MemberSubmissionEndpointReference.class, "Address", "ServiceName", "PortName", "PortType", MemberSubmissionAddressingConstants.MEX_METADATA, "ReferenceParameters", "ReferenceProperties"))
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getActionMismatchLocalName()
/*     */     {
/* 157 */       return "InvalidMessageInformationHeader";
/*     */     }
/*     */     
/*     */     public boolean isReferenceParameter(String localName) {
/* 161 */       return (localName.equals("ReferenceParameters") || localName.equals("ReferenceProperties"));
/*     */     }
/*     */ 
/*     */     
/*     */     public WsaTubeHelper getWsaHelper(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 166 */       return (WsaTubeHelper)new WsaTubeHelperImpl(wsdlPort, seiModel, binding);
/*     */     }
/*     */ 
/*     */     
/*     */     String getMapRequiredLocalName() {
/* 171 */       return "MessageInformationHeaderRequired";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMapRequiredText() {
/* 176 */       return "A required message information header, To, MessageID, or Action, is not present.";
/*     */     }
/*     */     
/*     */     String getInvalidAddressLocalName() {
/* 180 */       return getInvalidMapLocalName();
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidMapLocalName() {
/* 185 */       return "InvalidMessageInformationHeader";
/*     */     }
/*     */ 
/*     */     
/*     */     public String getInvalidMapText() {
/* 190 */       return "A message information header is not valid and the message cannot be processed.";
/*     */     }
/*     */ 
/*     */     
/*     */     String getInvalidCardinalityLocalName() {
/* 195 */       return getInvalidMapLocalName();
/*     */     }
/*     */     
/*     */     Header createReferenceParameterHeader(XMLStreamBuffer mark, String nsUri, String localName) {
/* 199 */       return (Header)new OutboundStreamHeader(mark, nsUri, localName);
/*     */     }
/*     */     
/*     */     String getIsReferenceParameterLocalName() {
/* 203 */       return "";
/*     */     }
/*     */     
/*     */     String getWsdlAnonymousLocalName() {
/* 207 */       return "";
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 211 */       return "wsa";
/*     */     }
/*     */     
/*     */     public String getWsdlPrefix() {
/* 215 */       return "wsaw";
/*     */     }
/*     */     
/*     */     public Class<? extends WebServiceFeature> getFeatureClass() {
/* 219 */       return (Class)MemberSubmissionAddressingFeature.class;
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String nsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String wsdlNsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final EPR eprType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String policyNsUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final String anonymousUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final String noneUri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WSEndpointReference anonymousEpr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName toTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName fromTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName replyToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName messageIDTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName relatesToTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName mapRequiredTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionMismatchTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName actionNotSupportedTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String actionNotSupportedText;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidMapTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidCardinalityTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName invalidAddressTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName problemHeaderQNameTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName problemActionTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName faultDetailTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName fault_missingAddressInEpr;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlActionTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlExtensionTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName wsdlAnonymousTag;
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName isReferenceParameterTag;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String EXTENDED_FAULT_NAMESPACE = "http://jax-ws.dev.java.net/addressing/fault";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String UNSET_OUTPUT_ACTION = "http://jax-ws.dev.java.net/addressing/output-action-not-set";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String UNSET_INPUT_ACTION = "http://jax-ws.dev.java.net/addressing/input-action-not-set";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final QName fault_duplicateAddressInEpr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 387 */     fault_duplicateAddressInEpr = new QName("http://jax-ws.dev.java.net/addressing/fault", "DuplicateAddressInEpr", "wsa");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AddressingVersion(String nsUri, String prefix, String anonymousEprString, String wsdlNsUri, String policyNsUri, String anonymousUri, String noneUri, EPR eprType) {
/* 394 */     this.nsUri = nsUri;
/* 395 */     this.wsdlNsUri = wsdlNsUri;
/* 396 */     this.policyNsUri = policyNsUri;
/* 397 */     this.anonymousUri = anonymousUri;
/* 398 */     this.noneUri = noneUri;
/* 399 */     this.toTag = new QName(nsUri, "To", prefix);
/* 400 */     this.fromTag = new QName(nsUri, "From", prefix);
/* 401 */     this.replyToTag = new QName(nsUri, "ReplyTo", prefix);
/* 402 */     this.faultToTag = new QName(nsUri, "FaultTo", prefix);
/* 403 */     this.actionTag = new QName(nsUri, "Action", prefix);
/* 404 */     this.messageIDTag = new QName(nsUri, "MessageID", prefix);
/* 405 */     this.relatesToTag = new QName(nsUri, "RelatesTo", prefix);
/*     */     
/* 407 */     this.mapRequiredTag = new QName(nsUri, getMapRequiredLocalName(), prefix);
/* 408 */     this.actionMismatchTag = new QName(nsUri, getActionMismatchLocalName(), prefix);
/* 409 */     this.actionNotSupportedTag = new QName(nsUri, "ActionNotSupported", prefix);
/* 410 */     this.actionNotSupportedText = "The \"%s\" cannot be processed at the receiver";
/* 411 */     this.invalidMapTag = new QName(nsUri, getInvalidMapLocalName(), prefix);
/* 412 */     this.invalidAddressTag = new QName(nsUri, getInvalidAddressLocalName(), prefix);
/* 413 */     this.invalidCardinalityTag = new QName(nsUri, getInvalidCardinalityLocalName(), prefix);
/* 414 */     this.faultDetailTag = new QName(nsUri, "FaultDetail", prefix);
/*     */     
/* 416 */     this.problemHeaderQNameTag = new QName(nsUri, "ProblemHeaderQName", prefix);
/* 417 */     this.problemActionTag = new QName(nsUri, "ProblemAction", prefix);
/*     */     
/* 419 */     this.fault_missingAddressInEpr = new QName(nsUri, "MissingAddressInEPR", prefix);
/* 420 */     this.isReferenceParameterTag = new QName(nsUri, getIsReferenceParameterLocalName(), prefix);
/*     */     
/* 422 */     this.wsdlActionTag = new QName(wsdlNsUri, "Action", prefix);
/* 423 */     this.wsdlExtensionTag = new QName(wsdlNsUri, "UsingAddressing", prefix);
/* 424 */     this.wsdlAnonymousTag = new QName(wsdlNsUri, getWsdlAnonymousLocalName(), prefix);
/*     */ 
/*     */     
/*     */     try {
/* 428 */       this.anonymousEpr = new WSEndpointReference(new ByteArrayInputStream(anonymousEprString.getBytes("UTF-8")), this);
/* 429 */     } catch (XMLStreamException e) {
/* 430 */       throw new Error(e);
/* 431 */     } catch (UnsupportedEncodingException e) {
/* 432 */       throw new Error(e);
/*     */     } 
/* 434 */     this.eprType = eprType;
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
/*     */   public static AddressingVersion fromNsUri(String nsUri) {
/* 455 */     if (nsUri.equals(W3C.nsUri)) {
/* 456 */       return W3C;
/*     */     }
/* 458 */     if (nsUri.equals(MEMBER.nsUri)) {
/* 459 */       return MEMBER;
/*     */     }
/* 461 */     return null;
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
/*     */   @Nullable
/*     */   public static AddressingVersion fromBinding(WSBinding binding) {
/* 475 */     if (binding.isFeatureEnabled(AddressingFeature.class)) {
/* 476 */       return W3C;
/*     */     }
/* 478 */     if (binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class)) {
/* 479 */       return MEMBER;
/*     */     }
/* 481 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddressingVersion fromPort(WSDLPort port) {
/* 491 */     if (port == null) {
/* 492 */       return null;
/*     */     }
/* 494 */     WebServiceFeature wsf = port.getFeature(AddressingFeature.class);
/* 495 */     if (wsf == null) {
/* 496 */       wsf = port.getFeature(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 498 */     if (wsf == null) {
/* 499 */       return null;
/*     */     }
/* 501 */     return fromFeature(wsf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNsUri() {
/* 512 */     return this.nsUri;
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
/*     */ 
/*     */   
/*     */   public final String getNoneUri() {
/* 545 */     return this.noneUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAnonymousUri() {
/* 555 */     return this.anonymousUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultFaultAction() {
/* 564 */     return this.nsUri + "/fault";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddressingVersion fromFeature(WebServiceFeature af) {
/* 634 */     if (af.getID().equals("http://www.w3.org/2005/08/addressing/module"))
/* 635 */       return W3C; 
/* 636 */     if (af.getID().equals("http://java.sun.com/xml/ns/jaxws/2004/08/addressing")) {
/* 637 */       return MEMBER;
/*     */     }
/* 639 */     return null;
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
/*     */   @NotNull
/*     */   public static WebServiceFeature getFeature(String nsUri, boolean enabled, boolean required) {
/* 655 */     if (nsUri.equals(W3C.policyNsUri))
/* 656 */       return (WebServiceFeature)new AddressingFeature(enabled, required); 
/* 657 */     if (nsUri.equals(MEMBER.policyNsUri)) {
/* 658 */       return (WebServiceFeature)new MemberSubmissionAddressingFeature(enabled, required);
/*     */     }
/* 660 */     throw new WebServiceException("Unsupported namespace URI: " + nsUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static AddressingVersion fromSpecClass(Class<? extends EndpointReference> eprClass) {
/* 668 */     if (eprClass == W3CEndpointReference.class)
/* 669 */       return W3C; 
/* 670 */     if (eprClass == MemberSubmissionEndpointReference.class)
/* 671 */       return MEMBER; 
/* 672 */     throw new WebServiceException("Unsupported EPR type: " + eprClass);
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
/*     */   public static boolean isRequired(WebServiceFeature wsf) {
/* 685 */     if (wsf.getID().equals("http://www.w3.org/2005/08/addressing/module"))
/* 686 */       return ((AddressingFeature)wsf).isRequired(); 
/* 687 */     if (wsf.getID().equals("http://java.sun.com/xml/ns/jaxws/2004/08/addressing")) {
/* 688 */       return ((MemberSubmissionAddressingFeature)wsf).isRequired();
/*     */     }
/* 690 */     throw new WebServiceException("WebServiceFeature not an Addressing feature: " + wsf.getID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRequired(WSBinding binding) {
/* 701 */     AddressingFeature af = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/* 702 */     if (af != null)
/* 703 */       return af.isRequired(); 
/* 704 */     MemberSubmissionAddressingFeature msaf = (MemberSubmissionAddressingFeature)binding.getFeature(MemberSubmissionAddressingFeature.class);
/* 705 */     if (msaf != null) {
/* 706 */       return msaf.isRequired();
/*     */     }
/* 708 */     return false;
/*     */   } abstract String getActionMismatchLocalName(); public abstract boolean isReferenceParameter(String paramString);
/*     */   public abstract WsaTubeHelper getWsaHelper(WSDLPort paramWSDLPort, SEIModel paramSEIModel, WSBinding paramWSBinding);
/*     */   abstract String getMapRequiredLocalName();
/*     */   public abstract String getMapRequiredText();
/*     */   abstract String getInvalidAddressLocalName();
/*     */   abstract String getInvalidMapLocalName();
/*     */   public abstract String getInvalidMapText();
/*     */   abstract String getInvalidCardinalityLocalName();
/*     */   abstract String getWsdlAnonymousLocalName();
/*     */   public static boolean isEnabled(WSBinding binding) {
/* 719 */     return (binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class) || binding
/* 720 */       .isFeatureEnabled(AddressingFeature.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getPrefix();
/*     */ 
/*     */   
/*     */   public abstract String getWsdlPrefix();
/*     */ 
/*     */   
/*     */   public abstract Class<? extends WebServiceFeature> getFeatureClass();
/*     */ 
/*     */   
/*     */   abstract Header createReferenceParameterHeader(XMLStreamBuffer paramXMLStreamBuffer, String paramString1, String paramString2);
/*     */ 
/*     */   
/*     */   abstract String getIsReferenceParameterLocalName();
/*     */   
/*     */   public static final class EPR
/*     */   {
/*     */     public EPR(Class<? extends EndpointReference> eprClass, String address, String serviceName, String portName, String portTypeName, QName wsdlMetadata, String referenceParameters, String referenceProperties) {
/* 741 */       this.eprClass = eprClass;
/* 742 */       this.address = address;
/* 743 */       this.serviceName = serviceName;
/* 744 */       this.portName = portName;
/* 745 */       this.portTypeName = portTypeName;
/* 746 */       this.referenceParameters = referenceParameters;
/* 747 */       this.referenceProperties = referenceProperties;
/* 748 */       this.wsdlMetadata = wsdlMetadata;
/*     */     }
/*     */     
/*     */     public final Class<? extends EndpointReference> eprClass;
/*     */     public final String address;
/*     */     public final String serviceName;
/*     */     public final String portName;
/*     */     public final String portTypeName;
/*     */     public final String referenceParameters;
/*     */     public final QName wsdlMetadata;
/*     */     public final String referenceProperties;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/AddressingVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */