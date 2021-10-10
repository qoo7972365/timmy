/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.util.QNameMap;
/*     */ import com.sun.xml.internal.ws.util.exception.LocatableWebServiceException;
/*     */ import java.util.List;
/*     */ import javax.jws.WebParam;
/*     */ import javax.jws.soap.SOAPBinding;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public final class WSDLBoundPortTypeImpl
/*     */   extends AbstractFeaturedObjectImpl
/*     */   implements EditableWSDLBoundPortType
/*     */ {
/*     */   private final QName name;
/*     */   private final QName portTypeName;
/*     */   private EditableWSDLPortType portType;
/*     */   private BindingID bindingId;
/*     */   @NotNull
/*     */   private final EditableWSDLModel owner;
/*  60 */   private final QNameMap<EditableWSDLBoundOperation> bindingOperations = new QNameMap();
/*     */ 
/*     */   
/*     */   private QNameMap<EditableWSDLBoundOperation> payloadMap;
/*     */ 
/*     */   
/*     */   private EditableWSDLBoundOperation emptyPayloadOperation;
/*     */ 
/*     */   
/*     */   private SOAPBinding.Style style;
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundPortTypeImpl(XMLStreamReader xsr, @NotNull EditableWSDLModel owner, QName name, QName portTypeName) {
/*  74 */     super(xsr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     this.style = SOAPBinding.Style.DOCUMENT; this.owner = owner; this.name = name; this.portTypeName = portTypeName; owner.addBinding(this);
/*     */   }
/* 131 */   public QName getName() { return this.name; } @NotNull public EditableWSDLModel getOwner() { return this.owner; } public EditableWSDLBoundOperation get(QName operationName) { return (EditableWSDLBoundOperation)this.bindingOperations.get(operationName); } public void put(QName opName, EditableWSDLBoundOperation ptOp) { this.bindingOperations.put(opName, ptOp); } public void setStyle(SOAPBinding.Style style) { this.style = style; }
/*     */   public QName getPortTypeName() { return this.portTypeName; }
/*     */   public EditableWSDLPortType getPortType() { return this.portType; }
/*     */   public Iterable<EditableWSDLBoundOperation> getBindingOperations() { return this.bindingOperations.values(); }
/* 135 */   public BindingID getBindingId() { return (this.bindingId == null) ? (BindingID)BindingID.SOAP11_HTTP : this.bindingId; } public void setBindingId(BindingID bindingId) { this.bindingId = bindingId; } public SOAPBinding.Style getStyle() { return this.style; }
/*     */ 
/*     */   
/*     */   public boolean isRpcLit() {
/* 139 */     return (SOAPBinding.Style.RPC == this.style);
/*     */   }
/*     */   
/*     */   public boolean isDoclit() {
/* 143 */     return (SOAPBinding.Style.DOCUMENT == this.style);
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
/*     */   public ParameterBinding getBinding(QName operation, String part, WebParam.Mode mode) {
/* 156 */     EditableWSDLBoundOperation op = get(operation);
/* 157 */     if (op == null)
/*     */     {
/* 159 */       return null;
/*     */     }
/* 161 */     if (WebParam.Mode.IN == mode || WebParam.Mode.INOUT == mode) {
/* 162 */       return op.getInputBinding(part);
/*     */     }
/* 164 */     return op.getOutputBinding(part);
/*     */   }
/*     */   
/*     */   public EditableWSDLBoundOperation getOperation(String namespaceUri, String localName) {
/* 168 */     if (namespaceUri == null && localName == null) {
/* 169 */       return this.emptyPayloadOperation;
/*     */     }
/* 171 */     return (EditableWSDLBoundOperation)this.payloadMap.get((namespaceUri == null) ? "" : namespaceUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeze() {
/* 176 */     this.portType = this.owner.getPortType(this.portTypeName);
/* 177 */     if (this.portType == null) {
/* 178 */       throw new LocatableWebServiceException(
/* 179 */           ClientMessages.UNDEFINED_PORT_TYPE(this.portTypeName), new Locator[] { getLocation() });
/*     */     }
/* 181 */     this.portType.freeze();
/*     */     
/* 183 */     for (EditableWSDLBoundOperation op : this.bindingOperations.values()) {
/* 184 */       op.freeze(this.owner);
/*     */     }
/*     */     
/* 187 */     freezePayloadMap();
/* 188 */     this.owner.finalizeRpcLitBinding(this);
/*     */   }
/*     */   
/*     */   private void freezePayloadMap() {
/* 192 */     if (this.style == SOAPBinding.Style.RPC) {
/* 193 */       this.payloadMap = new QNameMap();
/* 194 */       for (EditableWSDLBoundOperation op : this.bindingOperations.values()) {
/* 195 */         this.payloadMap.put(op.getRequestPayloadName(), op);
/*     */       }
/*     */     } else {
/* 198 */       this.payloadMap = new QNameMap();
/*     */       
/* 200 */       for (EditableWSDLBoundOperation op : this.bindingOperations.values()) {
/* 201 */         QName name = op.getRequestPayloadName();
/* 202 */         if (name == null) {
/*     */           
/* 204 */           this.emptyPayloadOperation = op;
/*     */           
/*     */           continue;
/*     */         } 
/* 208 */         this.payloadMap.put(name, op);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLBoundPortTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */