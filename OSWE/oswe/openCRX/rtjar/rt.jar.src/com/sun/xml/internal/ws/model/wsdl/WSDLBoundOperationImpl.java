/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPart;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.jws.WebParam;
/*     */ import javax.jws.soap.SOAPBinding;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLBoundOperationImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements EditableWSDLBoundOperation
/*     */ {
/*     */   private final QName name;
/*     */   private final Map<String, ParameterBinding> inputParts;
/*     */   private final Map<String, ParameterBinding> outputParts;
/*     */   private final Map<String, ParameterBinding> faultParts;
/*     */   private final Map<String, String> inputMimeTypes;
/*     */   private final Map<String, String> outputMimeTypes;
/*     */   private final Map<String, String> faultMimeTypes;
/*     */   private boolean explicitInputSOAPBodyParts = false;
/*     */   private boolean explicitOutputSOAPBodyParts = false;
/*     */   private boolean explicitFaultSOAPBodyParts = false;
/*     */   private Boolean emptyInputBody;
/*     */   private Boolean emptyOutputBody;
/*     */   private Boolean emptyFaultBody;
/*     */   private final Map<String, EditableWSDLPart> inParts;
/*     */   private final Map<String, EditableWSDLPart> outParts;
/*     */   private final List<EditableWSDLBoundFault> wsdlBoundFaults;
/*     */   private EditableWSDLOperation operation;
/*     */   private String soapAction;
/*     */   private WSDLBoundOperation.ANONYMOUS anonymous;
/*     */   private final EditableWSDLBoundPortType owner;
/*     */   private SOAPBinding.Style style;
/*     */   private String reqNamespace;
/*     */   private String respNamespace;
/*     */   private QName requestPayloadName;
/*     */   private QName responsePayloadName;
/*     */   private boolean emptyRequestPayload;
/*     */   private boolean emptyResponsePayload;
/*     */   private Map<QName, ? extends EditableWSDLMessage> messages;
/*     */   
/*     */   public WSDLBoundOperationImpl(XMLStreamReader xsr, EditableWSDLBoundPortType owner, QName name) {
/*  86 */     super(xsr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     this.style = SOAPBinding.Style.DOCUMENT; this.name = name; this.inputParts = new HashMap<>(); this.outputParts = new HashMap<>(); this.faultParts = new HashMap<>(); this.inputMimeTypes = new HashMap<>(); this.outputMimeTypes = new HashMap<>(); this.faultMimeTypes = new HashMap<>(); this.inParts = new HashMap<>(); this.outParts = new HashMap<>(); this.wsdlBoundFaults = new ArrayList<>(); this.owner = owner;
/*     */   }
/* 304 */   public QName getName() { return this.name; } public String getSOAPAction() { return this.soapAction; } public void setSoapAction(String soapAction) { this.soapAction = (soapAction != null) ? soapAction : ""; } public EditableWSDLPart getPart(String partName, WebParam.Mode mode) { if (mode == WebParam.Mode.IN) return this.inParts.get(partName);  if (mode == WebParam.Mode.OUT) return this.outParts.get(partName);  return null; } public void addPart(EditableWSDLPart part, WebParam.Mode mode) { if (mode == WebParam.Mode.IN) { this.inParts.put(part.getName(), part); } else if (mode == WebParam.Mode.OUT) { this.outParts.put(part.getName(), part); }  } public Map<String, ParameterBinding> getInputParts() { return this.inputParts; } public Map<String, ParameterBinding> getOutputParts() { return this.outputParts; } public Map<String, ParameterBinding> getFaultParts() { return this.faultParts; } public Map<String, ? extends EditableWSDLPart> getInParts() { return Collections.unmodifiableMap(this.inParts); } public Map<String, ? extends EditableWSDLPart> getOutParts() { return Collections.unmodifiableMap(this.outParts); } @NotNull public List<? extends EditableWSDLBoundFault> getFaults() { return this.wsdlBoundFaults; } public void setStyle(SOAPBinding.Style style) { this.style = style; }
/*     */   public void addFault(@NotNull EditableWSDLBoundFault fault) { this.wsdlBoundFaults.add(fault); }
/*     */   public ParameterBinding getInputBinding(String part) { if (this.emptyInputBody == null) if (this.inputParts.get(" ") != null) { this.emptyInputBody = Boolean.valueOf(true); } else { this.emptyInputBody = Boolean.valueOf(false); }   ParameterBinding block = this.inputParts.get(part); if (block == null) { if (this.explicitInputSOAPBodyParts || this.emptyInputBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; }
/*     */   public ParameterBinding getOutputBinding(String part) { if (this.emptyOutputBody == null) if (this.outputParts.get(" ") != null) { this.emptyOutputBody = Boolean.valueOf(true); } else { this.emptyOutputBody = Boolean.valueOf(false); }   ParameterBinding block = this.outputParts.get(part); if (block == null) { if (this.explicitOutputSOAPBodyParts || this.emptyOutputBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; }
/*     */   public ParameterBinding getFaultBinding(String part) { if (this.emptyFaultBody == null) if (this.faultParts.get(" ") != null) { this.emptyFaultBody = Boolean.valueOf(true); } else { this.emptyFaultBody = Boolean.valueOf(false); }   ParameterBinding block = this.faultParts.get(part); if (block == null) { if (this.explicitFaultSOAPBodyParts || this.emptyFaultBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; }
/* 309 */   public String getMimeTypeForInputPart(String part) { return this.inputMimeTypes.get(part); } public String getMimeTypeForOutputPart(String part) { return this.outputMimeTypes.get(part); } public String getMimeTypeForFaultPart(String part) { return this.faultMimeTypes.get(part); } public EditableWSDLOperation getOperation() { return this.operation; } public EditableWSDLBoundPortType getBoundPortType() { return this.owner; } public void setInputExplicitBodyParts(boolean b) { this.explicitInputSOAPBodyParts = b; } public void setOutputExplicitBodyParts(boolean b) { this.explicitOutputSOAPBodyParts = b; } public void setFaultExplicitBodyParts(boolean b) { this.explicitFaultSOAPBodyParts = b; } @Nullable public QName getRequestPayloadName() { if (this.emptyRequestPayload) {
/* 310 */       return null;
/*     */     }
/* 312 */     if (this.requestPayloadName != null) {
/* 313 */       return this.requestPayloadName;
/*     */     }
/* 315 */     if (this.style.equals(SOAPBinding.Style.RPC)) {
/* 316 */       String ns = (getRequestNamespace() != null) ? getRequestNamespace() : this.name.getNamespaceURI();
/* 317 */       this.requestPayloadName = new QName(ns, this.name.getLocalPart());
/* 318 */       return this.requestPayloadName;
/*     */     } 
/* 320 */     QName inMsgName = this.operation.getInput().getMessage().getName();
/* 321 */     EditableWSDLMessage message = this.messages.get(inMsgName);
/* 322 */     for (EditableWSDLPart part : message.parts()) {
/* 323 */       ParameterBinding binding = getInputBinding(part.getName());
/* 324 */       if (binding.isBody()) {
/* 325 */         this.requestPayloadName = part.getDescriptor().name();
/* 326 */         return this.requestPayloadName;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 331 */     this.emptyRequestPayload = true;
/*     */ 
/*     */     
/* 334 */     return null; }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getResponsePayloadName() {
/* 339 */     if (this.emptyResponsePayload) {
/* 340 */       return null;
/*     */     }
/* 342 */     if (this.responsePayloadName != null) {
/* 343 */       return this.responsePayloadName;
/*     */     }
/* 345 */     if (this.style.equals(SOAPBinding.Style.RPC)) {
/* 346 */       String ns = (getResponseNamespace() != null) ? getResponseNamespace() : this.name.getNamespaceURI();
/* 347 */       this.responsePayloadName = new QName(ns, this.name.getLocalPart() + "Response");
/* 348 */       return this.responsePayloadName;
/*     */     } 
/* 350 */     QName outMsgName = this.operation.getOutput().getMessage().getName();
/* 351 */     EditableWSDLMessage message = this.messages.get(outMsgName);
/* 352 */     for (EditableWSDLPart part : message.parts()) {
/* 353 */       ParameterBinding binding = getOutputBinding(part.getName());
/* 354 */       if (binding.isBody()) {
/* 355 */         this.responsePayloadName = part.getDescriptor().name();
/* 356 */         return this.responsePayloadName;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 361 */     this.emptyResponsePayload = true;
/*     */ 
/*     */     
/* 364 */     return null;
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
/*     */   public String getRequestNamespace() {
/* 379 */     return (this.reqNamespace != null) ? this.reqNamespace : this.name.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public void setRequestNamespace(String ns) {
/* 383 */     this.reqNamespace = ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResponseNamespace() {
/* 394 */     return (this.respNamespace != null) ? this.respNamespace : this.name.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public void setResponseNamespace(String ns) {
/* 398 */     this.respNamespace = ns;
/*     */   }
/*     */   
/*     */   EditableWSDLBoundPortType getOwner() {
/* 402 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeze(EditableWSDLModel parent) {
/* 412 */     this.messages = parent.getMessages();
/* 413 */     this.operation = this.owner.getPortType().get(this.name.getLocalPart());
/* 414 */     for (EditableWSDLBoundFault bf : this.wsdlBoundFaults) {
/* 415 */       bf.freeze(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAnonymous(WSDLBoundOperation.ANONYMOUS anonymous) {
/* 420 */     this.anonymous = anonymous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundOperation.ANONYMOUS getAnonymous() {
/* 428 */     return this.anonymous;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLBoundOperationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */