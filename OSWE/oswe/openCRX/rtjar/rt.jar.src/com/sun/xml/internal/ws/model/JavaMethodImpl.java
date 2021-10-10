/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.MEP;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.soap.SOAPBinding;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.model.soap.SOAPBindingImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.wsdl.ActionBasedOperationSignature;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebMethod;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Action;
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
/*     */ 
/*     */ 
/*     */ public final class JavaMethodImpl
/*     */   implements JavaMethod
/*     */ {
/*  59 */   private String inputAction = "";
/*  60 */   private String outputAction = "";
/*  61 */   private final List<CheckedExceptionImpl> exceptions = new ArrayList<>();
/*     */   private final Method method;
/*  63 */   final List<ParameterImpl> requestParams = new ArrayList<>();
/*  64 */   final List<ParameterImpl> responseParams = new ArrayList<>();
/*  65 */   private final List<ParameterImpl> unmReqParams = Collections.unmodifiableList(this.requestParams);
/*  66 */   private final List<ParameterImpl> unmResParams = Collections.unmodifiableList(this.responseParams);
/*     */   
/*     */   private SOAPBinding binding;
/*     */   
/*     */   private MEP mep;
/*     */   
/*     */   private QName operationName;
/*     */   
/*     */   private WSDLBoundOperation wsdlOperation;
/*     */   
/*     */   final AbstractSEIModelImpl owner;
/*     */   
/*     */   private final Method seiMethod;
/*     */   private QName requestPayloadName;
/*     */   private String soapAction;
/*     */   
/*     */   public JavaMethodImpl(AbstractSEIModelImpl owner, Method method, Method seiMethod, MetadataReader metadataReader) {
/*  83 */     this.owner = owner;
/*  84 */     this.method = method;
/*  85 */     this.seiMethod = seiMethod;
/*  86 */     setWsaActions(metadataReader);
/*     */   }
/*     */   
/*     */   private void setWsaActions(MetadataReader metadataReader) {
/*  90 */     Action action = (metadataReader != null) ? (Action)metadataReader.getAnnotation(Action.class, this.seiMethod) : this.seiMethod.<Action>getAnnotation(Action.class);
/*  91 */     if (action != null) {
/*  92 */       this.inputAction = action.input();
/*  93 */       this.outputAction = action.output();
/*     */     } 
/*     */ 
/*     */     
/*  97 */     WebMethod webMethod = (metadataReader != null) ? (WebMethod)metadataReader.getAnnotation(WebMethod.class, this.seiMethod) : this.seiMethod.<WebMethod>getAnnotation(WebMethod.class);
/*  98 */     this.soapAction = "";
/*  99 */     if (webMethod != null)
/* 100 */       this.soapAction = webMethod.action(); 
/* 101 */     if (!this.soapAction.equals(""))
/*     */     {
/* 103 */       if (this.inputAction.equals("")) {
/*     */         
/* 105 */         this.inputAction = this.soapAction;
/* 106 */       } else if (!this.inputAction.equals(this.soapAction)) {
/*     */       
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionBasedOperationSignature getOperationSignature() {
/* 115 */     QName qname = getRequestPayloadName();
/* 116 */     if (qname == null) qname = new QName("", ""); 
/* 117 */     return new ActionBasedOperationSignature(getInputAction(), qname);
/*     */   }
/*     */   
/*     */   public SEIModel getOwner() {
/* 121 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getMethod() {
/* 130 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method getSEIMethod() {
/* 139 */     return this.seiMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MEP getMEP() {
/* 146 */     return this.mep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMEP(MEP mep) {
/* 154 */     this.mep = mep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPBinding getBinding() {
/* 161 */     if (this.binding == null)
/* 162 */       return (SOAPBinding)new SOAPBindingImpl(); 
/* 163 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setBinding(SOAPBinding binding) {
/* 170 */     this.binding = binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundOperation getOperation() {
/* 181 */     return this.wsdlOperation;
/*     */   }
/*     */   
/*     */   public void setOperationQName(QName name) {
/* 185 */     this.operationName = name;
/*     */   }
/*     */   
/*     */   public QName getOperationQName() {
/* 189 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getName() : this.operationName;
/*     */   }
/*     */   
/*     */   public String getSOAPAction() {
/* 193 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getSOAPAction() : this.soapAction;
/*     */   }
/*     */   
/*     */   public String getOperationName() {
/* 197 */     return this.operationName.getLocalPart();
/*     */   }
/*     */   
/*     */   public String getRequestMessageName() {
/* 201 */     return getOperationName();
/*     */   }
/*     */   
/*     */   public String getResponseMessageName() {
/* 205 */     if (this.mep.isOneWay())
/* 206 */       return null; 
/* 207 */     return getOperationName() + "Response";
/*     */   }
/*     */   
/*     */   public void setRequestPayloadName(QName n) {
/* 211 */     this.requestPayloadName = n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getRequestPayloadName() {
/* 218 */     return (this.wsdlOperation != null) ? this.wsdlOperation.getRequestPayloadName() : this.requestPayloadName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getResponsePayloadName() {
/* 225 */     return (this.mep == MEP.ONE_WAY) ? null : this.wsdlOperation.getResponsePayloadName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getRequestParameters() {
/* 232 */     return this.unmReqParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getResponseParameters() {
/* 239 */     return this.unmResParams;
/*     */   }
/*     */   
/*     */   void addParameter(ParameterImpl p) {
/* 243 */     if (p.isIN() || p.isINOUT()) {
/* 244 */       assert !this.requestParams.contains(p);
/* 245 */       this.requestParams.add(p);
/*     */     } 
/*     */     
/* 248 */     if (p.isOUT() || p.isINOUT()) {
/*     */       
/* 250 */       assert !this.responseParams.contains(p);
/* 251 */       this.responseParams.add(p);
/*     */     } 
/*     */   }
/*     */   
/*     */   void addRequestParameter(ParameterImpl p) {
/* 256 */     if (p.isIN() || p.isINOUT()) {
/* 257 */       this.requestParams.add(p);
/*     */     }
/*     */   }
/*     */   
/*     */   void addResponseParameter(ParameterImpl p) {
/* 262 */     if (p.isOUT() || p.isINOUT()) {
/* 263 */       this.responseParams.add(p);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInputParametersCount() {
/* 274 */     int count = 0;
/* 275 */     for (ParameterImpl param : this.requestParams) {
/* 276 */       if (param.isWrapperStyle()) {
/* 277 */         count += ((WrapperParameter)param).getWrapperChildren().size(); continue;
/*     */       } 
/* 279 */       count++;
/*     */     } 
/*     */ 
/*     */     
/* 283 */     for (ParameterImpl param : this.responseParams) {
/* 284 */       if (param.isWrapperStyle()) {
/* 285 */         for (ParameterImpl wc : ((WrapperParameter)param).getWrapperChildren()) {
/* 286 */           if (!wc.isResponse() && wc.isOUT())
/* 287 */             count++; 
/*     */         }  continue;
/*     */       } 
/* 290 */       if (!param.isResponse() && param.isOUT()) {
/* 291 */         count++;
/*     */       }
/*     */     } 
/*     */     
/* 295 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addException(CheckedExceptionImpl ce) {
/* 302 */     if (!this.exceptions.contains(ce)) {
/* 303 */       this.exceptions.add(ce);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckedExceptionImpl getCheckedException(Class exceptionClass) {
/* 312 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 313 */       if (ce.getExceptionClass() == exceptionClass)
/* 314 */         return ce; 
/*     */     } 
/* 316 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CheckedExceptionImpl> getCheckedExceptions() {
/* 324 */     return Collections.unmodifiableList(this.exceptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInputAction() {
/* 329 */     return this.inputAction;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOutputAction() {
/* 334 */     return this.outputAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckedExceptionImpl getCheckedException(TypeReference detailType) {
/* 344 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 345 */       TypeInfo actual = ce.getDetailType();
/* 346 */       if (actual.tagName.equals(detailType.tagName) && actual.type == detailType.type) {
/* 347 */         return ce;
/*     */       }
/*     */     } 
/* 350 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAsync() {
/* 360 */     return this.mep.isAsync;
/*     */   }
/*     */   
/*     */   void freeze(WSDLPort portType) {
/* 364 */     this.wsdlOperation = portType.getBinding().get(new QName(portType.getBinding().getPortType().getName().getNamespaceURI(), getOperationName()));
/*     */     
/* 366 */     if (this.wsdlOperation == null) {
/* 367 */       throw new WebServiceException("Method " + this.seiMethod.getName() + " is exposed as WebMethod, but there is no corresponding wsdl operation with name " + this.operationName + " in the wsdl:portType" + portType.getBinding().getPortType().getName());
/*     */     }
/*     */ 
/*     */     
/* 371 */     if (this.inputAction.equals("")) {
/* 372 */       this.inputAction = this.wsdlOperation.getOperation().getInput().getAction();
/* 373 */     } else if (!this.inputAction.equals(this.wsdlOperation.getOperation().getInput().getAction())) {
/*     */       
/* 375 */       LOGGER.warning("Input Action on WSDL operation " + this.wsdlOperation.getName().getLocalPart() + " and @Action on its associated Web Method " + this.seiMethod.getName() + " did not match and will cause problems in dispatching the requests");
/*     */     } 
/* 377 */     if (!this.mep.isOneWay()) {
/* 378 */       if (this.outputAction.equals("")) {
/* 379 */         this.outputAction = this.wsdlOperation.getOperation().getOutput().getAction();
/*     */       }
/* 381 */       for (CheckedExceptionImpl ce : this.exceptions) {
/* 382 */         if (ce.getFaultAction().equals("")) {
/* 383 */           QName detailQName = (ce.getDetailType()).tagName;
/* 384 */           WSDLFault wsdlfault = this.wsdlOperation.getOperation().getFault(detailQName);
/* 385 */           if (wsdlfault == null) {
/*     */             
/* 387 */             LOGGER.warning("Mismatch between Java model and WSDL model found, For wsdl operation " + this.wsdlOperation
/* 388 */                 .getName() + ",There is no matching wsdl fault with detail QName " + 
/* 389 */                 (ce.getDetailType()).tagName);
/* 390 */             ce.setFaultAction(ce.getDefaultFaultAction()); continue;
/*     */           } 
/* 392 */           ce.setFaultAction(wsdlfault.getAction());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   final void fillTypes(List<TypeInfo> types) {
/* 400 */     fillTypes(this.requestParams, types);
/* 401 */     fillTypes(this.responseParams, types);
/*     */     
/* 403 */     for (CheckedExceptionImpl ce : this.exceptions) {
/* 404 */       types.add(ce.getDetailType());
/*     */     }
/*     */   }
/*     */   
/*     */   private void fillTypes(List<ParameterImpl> params, List<TypeInfo> types) {
/* 409 */     for (ParameterImpl p : params) {
/* 410 */       p.fillTypes(types);
/*     */     }
/*     */   }
/*     */   
/* 414 */   private static final Logger LOGGER = Logger.getLogger(JavaMethodImpl.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/JavaMethodImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */