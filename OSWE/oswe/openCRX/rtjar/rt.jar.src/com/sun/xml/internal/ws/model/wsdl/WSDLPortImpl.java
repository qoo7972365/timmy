/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.util.exception.LocatableWebServiceException;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLPortImpl
/*     */   extends AbstractFeaturedObjectImpl
/*     */   implements EditableWSDLPort
/*     */ {
/*     */   private final QName name;
/*     */   private EndpointAddress address;
/*     */   private final QName bindingName;
/*     */   private final EditableWSDLService owner;
/*     */   private WSEndpointReference epr;
/*     */   private EditableWSDLBoundPortType boundPortType;
/*     */   
/*     */   public WSDLPortImpl(XMLStreamReader xsr, EditableWSDLService owner, QName name, QName binding) {
/*  66 */     super(xsr);
/*  67 */     this.owner = owner;
/*  68 */     this.name = name;
/*  69 */     this.bindingName = binding;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  73 */     return this.name;
/*     */   }
/*     */   
/*     */   public QName getBindingName() {
/*  77 */     return this.bindingName;
/*     */   }
/*     */   
/*     */   public EndpointAddress getAddress() {
/*  81 */     return this.address;
/*     */   }
/*     */   
/*     */   public EditableWSDLService getOwner() {
/*  85 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddress(EndpointAddress address) {
/*  92 */     assert address != null;
/*  93 */     this.address = address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEPR(@NotNull WSEndpointReference epr) {
/* 100 */     assert epr != null;
/* 101 */     addExtension((WSDLExtension)epr);
/* 102 */     this.epr = epr;
/*     */   }
/*     */   @Nullable
/*     */   public WSEndpointReference getEPR() {
/* 106 */     return this.epr;
/*     */   }
/*     */   
/*     */   public EditableWSDLBoundPortType getBinding() {
/* 110 */     return this.boundPortType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeze(EditableWSDLModel root) {
/* 115 */     this.boundPortType = root.getBinding(this.bindingName);
/* 116 */     if (this.boundPortType == null) {
/* 117 */       throw new LocatableWebServiceException(
/* 118 */           ClientMessages.UNDEFINED_BINDING(this.bindingName), new Locator[] { getLocation() });
/*     */     }
/* 120 */     if (this.features == null)
/* 121 */       this.features = new WebServiceFeatureList(); 
/* 122 */     this.features.setParentFeaturedObject((WSDLFeaturedObject)this.boundPortType);
/* 123 */     this.notUnderstoodExtensions.addAll(this.boundPortType.getNotUnderstoodExtensions());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLPortImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */