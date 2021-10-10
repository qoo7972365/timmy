/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ public final class WSDLModelImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements EditableWSDLModel
/*     */ {
/*  58 */   private final Map<QName, EditableWSDLMessage> messages = new HashMap<>();
/*  59 */   private final Map<QName, EditableWSDLPortType> portTypes = new HashMap<>();
/*  60 */   private final Map<QName, EditableWSDLBoundPortType> bindings = new HashMap<>();
/*  61 */   private final Map<QName, EditableWSDLService> services = new LinkedHashMap<>();
/*     */   
/*     */   private PolicyMap policyMap;
/*     */   
/*  65 */   private final Map<QName, EditableWSDLBoundPortType> unmBindings = Collections.unmodifiableMap(this.bindings);
/*     */ 
/*     */   
/*     */   public WSDLModelImpl(@NotNull String systemId) {
/*  69 */     super(systemId, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLModelImpl() {
/*  76 */     super(null, -1);
/*     */   }
/*     */   
/*     */   public void addMessage(EditableWSDLMessage msg) {
/*  80 */     this.messages.put(msg.getName(), msg);
/*     */   }
/*     */   
/*     */   public EditableWSDLMessage getMessage(QName name) {
/*  84 */     return this.messages.get(name);
/*     */   }
/*     */   
/*     */   public void addPortType(EditableWSDLPortType pt) {
/*  88 */     this.portTypes.put(pt.getName(), pt);
/*     */   }
/*     */   
/*     */   public EditableWSDLPortType getPortType(QName name) {
/*  92 */     return this.portTypes.get(name);
/*     */   }
/*     */   
/*     */   public void addBinding(EditableWSDLBoundPortType boundPortType) {
/*  96 */     assert !this.bindings.containsValue(boundPortType);
/*  97 */     this.bindings.put(boundPortType.getName(), boundPortType);
/*     */   }
/*     */   
/*     */   public EditableWSDLBoundPortType getBinding(QName name) {
/* 101 */     return this.bindings.get(name);
/*     */   }
/*     */   
/*     */   public void addService(EditableWSDLService svc) {
/* 105 */     this.services.put(svc.getName(), svc);
/*     */   }
/*     */   
/*     */   public EditableWSDLService getService(QName name) {
/* 109 */     return this.services.get(name);
/*     */   }
/*     */   
/*     */   public Map<QName, EditableWSDLMessage> getMessages() {
/* 113 */     return this.messages;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, EditableWSDLPortType> getPortTypes() {
/* 117 */     return this.portTypes;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, ? extends EditableWSDLBoundPortType> getBindings() {
/* 121 */     return this.unmBindings;
/*     */   }
/*     */   @NotNull
/*     */   public Map<QName, EditableWSDLService> getServices() {
/* 125 */     return this.services;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getFirstServiceName() {
/* 132 */     if (this.services.isEmpty())
/* 133 */       return null; 
/* 134 */     return ((EditableWSDLService)this.services.values().iterator().next()).getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableWSDLBoundPortType getBinding(QName serviceName, QName portName) {
/* 145 */     EditableWSDLService service = this.services.get(serviceName);
/* 146 */     if (service != null) {
/* 147 */       EditableWSDLPort port = service.get(portName);
/* 148 */       if (port != null)
/* 149 */         return port.getBinding(); 
/*     */     } 
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public void finalizeRpcLitBinding(EditableWSDLBoundPortType boundPortType) {
/* 155 */     assert boundPortType != null;
/* 156 */     QName portTypeName = boundPortType.getPortTypeName();
/* 157 */     if (portTypeName == null)
/*     */       return; 
/* 159 */     WSDLPortType pt = (WSDLPortType)this.portTypes.get(portTypeName);
/* 160 */     if (pt == null)
/*     */       return; 
/* 162 */     for (EditableWSDLBoundOperation bop : boundPortType.getBindingOperations()) {
/* 163 */       WSDLOperation pto = pt.get(bop.getName().getLocalPart());
/* 164 */       WSDLMessage inMsgName = pto.getInput().getMessage();
/* 165 */       if (inMsgName == null)
/*     */         continue; 
/* 167 */       EditableWSDLMessage inMsg = this.messages.get(inMsgName.getName());
/* 168 */       int bodyindex = 0;
/* 169 */       if (inMsg != null) {
/* 170 */         for (EditableWSDLPart part : inMsg.parts()) {
/* 171 */           String name = part.getName();
/* 172 */           ParameterBinding pb = bop.getInputBinding(name);
/* 173 */           if (pb.isBody()) {
/* 174 */             part.setIndex(bodyindex++);
/* 175 */             part.setBinding(pb);
/* 176 */             bop.addPart(part, WebParam.Mode.IN);
/*     */           } 
/*     */         } 
/*     */       }
/* 180 */       bodyindex = 0;
/* 181 */       if (pto.isOneWay())
/*     */         continue; 
/* 183 */       WSDLMessage outMsgName = pto.getOutput().getMessage();
/* 184 */       if (outMsgName == null)
/*     */         continue; 
/* 186 */       EditableWSDLMessage outMsg = this.messages.get(outMsgName.getName());
/* 187 */       if (outMsg != null) {
/* 188 */         for (EditableWSDLPart part : outMsg.parts()) {
/* 189 */           String name = part.getName();
/* 190 */           ParameterBinding pb = bop.getOutputBinding(name);
/* 191 */           if (pb.isBody()) {
/* 192 */             part.setIndex(bodyindex++);
/* 193 */             part.setBinding(pb);
/* 194 */             bop.addPart(part, WebParam.Mode.OUT);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 207 */     return this.policyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolicyMap(PolicyMap policyMap) {
/* 215 */     this.policyMap = policyMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void freeze() {
/* 222 */     for (EditableWSDLService service : this.services.values()) {
/* 223 */       service.freeze(this);
/*     */     }
/* 225 */     for (EditableWSDLBoundPortType bp : this.bindings.values()) {
/* 226 */       bp.freeze();
/*     */     }
/*     */     
/* 229 */     for (EditableWSDLPortType pt : this.portTypes.values())
/* 230 */       pt.freeze(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLModelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */