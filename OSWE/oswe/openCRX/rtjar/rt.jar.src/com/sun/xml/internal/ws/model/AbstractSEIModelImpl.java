/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.DatabindingModeFeature;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.internal.bind.api.TypeReference;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPart;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.db.DatabindingImpl;
/*     */ import com.sun.xml.internal.ws.developer.JAXBContextFactory;
/*     */ import com.sun.xml.internal.ws.developer.UsesJAXBContextFeature;
/*     */ import com.sun.xml.internal.ws.resources.ModelerMessages;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSEIModelImpl
/*     */   implements SEIModel
/*     */ {
/*     */   private List<Class> additionalClasses;
/*     */   private Pool.Marshaller marshallers;
/*     */   protected JAXBRIContext jaxbContext;
/*     */   protected BindingContext bindingContext;
/*     */   private String wsdlLocation;
/*     */   private QName serviceName;
/*     */   private QName portName;
/*     */   private QName portTypeName;
/*     */   private Map<Method, JavaMethodImpl> methodToJM;
/*     */   private Map<QName, JavaMethodImpl> nameToJM;
/*     */   private Map<QName, JavaMethodImpl> wsdlOpToJM;
/*     */   private List<JavaMethodImpl> javaMethods;
/*     */   private final Map<TypeReference, Bridge> bridgeMap;
/*     */   private final Map<TypeInfo, XMLBridge> xmlBridgeMap;
/*     */   protected final QName emptyBodyName;
/*     */   private String targetNamespace;
/*     */   private List<String> knownNamespaceURIs;
/*     */   private WSDLPort port;
/*     */   private final WebServiceFeatureList features;
/*     */   private Databinding databinding;
/*     */   BindingID bindingId;
/*     */   protected Class contractClass;
/*     */   protected Class endpointClass;
/*     */   protected ClassLoader classLoader;
/*     */   protected WSBinding wsBinding;
/*     */   protected BindingInfo databindingInfo;
/*     */   protected String defaultSchemaNamespaceSuffix;
/*     */   
/*     */   protected AbstractSEIModelImpl(WebServiceFeatureList features) {
/* 495 */     this.additionalClasses = (List)new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     this.methodToJM = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 511 */     this.nameToJM = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 515 */     this.wsdlOpToJM = new HashMap<>();
/*     */     
/* 517 */     this.javaMethods = new ArrayList<>();
/* 518 */     this.bridgeMap = new HashMap<>();
/* 519 */     this.xmlBridgeMap = new HashMap<>();
/* 520 */     this.emptyBodyName = new QName("");
/* 521 */     this.targetNamespace = "";
/* 522 */     this.knownNamespaceURIs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 529 */     this.classLoader = null;
/*     */     this.features = features;
/*     */     this.databindingInfo = new BindingInfo();
/*     */     this.databindingInfo.setSEIModel(this);
/* 533 */   } private static final Logger LOGGER = Logger.getLogger(AbstractSEIModelImpl.class.getName());
/*     */   
/*     */   void postProcess() {
/*     */     if (this.jaxbContext != null)
/*     */       return; 
/*     */     populateMaps();
/*     */     createJAXBContext();
/*     */   }
/*     */   
/*     */   public void freeze(WSDLPort port) {
/*     */     this.port = port;
/*     */     for (JavaMethodImpl m : this.javaMethods) {
/*     */       m.freeze(port);
/*     */       putOp(m.getOperationQName(), m);
/*     */     } 
/*     */     if (this.databinding != null)
/*     */       ((DatabindingImpl)this.databinding).freeze(port); 
/*     */   }
/*     */   
/*     */   public Pool.Marshaller getMarshallerPool() {
/*     */     return this.marshallers;
/*     */   }
/*     */   
/*     */   public JAXBContext getJAXBContext() {
/*     */     JAXBContext jc = this.bindingContext.getJAXBContext();
/*     */     if (jc != null)
/*     */       return jc; 
/*     */     return (JAXBContext)this.jaxbContext;
/*     */   }
/*     */   
/*     */   public BindingContext getBindingContext() {
/*     */     return this.bindingContext;
/*     */   }
/*     */   
/*     */   public List<String> getKnownNamespaceURIs() {
/*     */     return this.knownNamespaceURIs;
/*     */   }
/*     */   
/*     */   public final Bridge getBridge(TypeReference type) {
/*     */     Bridge b = this.bridgeMap.get(type);
/*     */     assert b != null;
/*     */     return b;
/*     */   }
/*     */   
/*     */   public final XMLBridge getXMLBridge(TypeInfo type) {
/*     */     XMLBridge b = this.xmlBridgeMap.get(type);
/*     */     assert b != null;
/*     */     return b;
/*     */   }
/*     */   
/*     */   private void createJAXBContext() {
/*     */     final List<TypeInfo> types = getAllTypeInfos();
/*     */     final List<Class<?>> cls = new ArrayList<>(types.size() + this.additionalClasses.size());
/*     */     cls.addAll((Collection)this.additionalClasses);
/*     */     for (TypeInfo type : types)
/*     */       cls.add((Class)type.type); 
/*     */     try {
/*     */       this.bindingContext = AccessController.<BindingContext>doPrivileged(new PrivilegedExceptionAction<BindingContext>() {
/*     */             public BindingContext run() throws Exception {
/*     */               if (AbstractSEIModelImpl.LOGGER.isLoggable(Level.FINEST))
/*     */                 AbstractSEIModelImpl.LOGGER.log(Level.FINEST, "Creating JAXBContext with classes={0} and types={1}", new Object[] { this.val$cls, this.val$types }); 
/*     */               UsesJAXBContextFeature f = (UsesJAXBContextFeature)AbstractSEIModelImpl.this.features.get(UsesJAXBContextFeature.class);
/*     */               DatabindingModeFeature dmf = (DatabindingModeFeature)AbstractSEIModelImpl.this.features.get(DatabindingModeFeature.class);
/*     */               JAXBContextFactory factory = (f != null) ? f.getFactory() : null;
/*     */               if (factory == null)
/*     */                 factory = JAXBContextFactory.DEFAULT; 
/*     */               AbstractSEIModelImpl.this.databindingInfo.properties().put(JAXBContextFactory.class.getName(), factory);
/*     */               if (dmf != null) {
/*     */                 if (AbstractSEIModelImpl.LOGGER.isLoggable(Level.FINE))
/*     */                   AbstractSEIModelImpl.LOGGER.log(Level.FINE, "DatabindingModeFeature in SEI specifies mode: {0}", dmf.getMode()); 
/*     */                 AbstractSEIModelImpl.this.databindingInfo.setDatabindingMode(dmf.getMode());
/*     */               } 
/*     */               if (f != null)
/*     */                 AbstractSEIModelImpl.this.databindingInfo.setDatabindingMode("glassfish.jaxb"); 
/*     */               AbstractSEIModelImpl.this.databindingInfo.setClassLoader(AbstractSEIModelImpl.this.classLoader);
/*     */               AbstractSEIModelImpl.this.databindingInfo.contentClasses().addAll(cls);
/*     */               AbstractSEIModelImpl.this.databindingInfo.typeInfos().addAll(types);
/*     */               AbstractSEIModelImpl.this.databindingInfo.properties().put("c14nSupport", Boolean.FALSE);
/*     */               AbstractSEIModelImpl.this.databindingInfo.setDefaultNamespace(AbstractSEIModelImpl.this.getDefaultSchemaNamespace());
/*     */               BindingContext bc = BindingContextFactory.create(AbstractSEIModelImpl.this.databindingInfo);
/*     */               if (AbstractSEIModelImpl.LOGGER.isLoggable(Level.FINE))
/*     */                 AbstractSEIModelImpl.LOGGER.log(Level.FINE, "Created binding context: " + bc.getClass().getName()); 
/*     */               return bc;
/*     */             }
/*     */           });
/*     */       createBondMap(types);
/*     */     } catch (PrivilegedActionException e) {
/*     */       throw new WebServiceException(ModelerMessages.UNABLE_TO_CREATE_JAXB_CONTEXT(), e);
/*     */     } 
/*     */     this.knownNamespaceURIs = new ArrayList<>();
/*     */     for (String namespace : this.bindingContext.getKnownNamespaceURIs()) {
/*     */       if (namespace.length() > 0 && !namespace.equals("http://www.w3.org/2001/XMLSchema") && !namespace.equals("http://www.w3.org/XML/1998/namespace"))
/*     */         this.knownNamespaceURIs.add(namespace); 
/*     */     } 
/*     */     this.marshallers = new Pool.Marshaller((JAXBContext)this.jaxbContext);
/*     */   }
/*     */   
/*     */   private List<TypeInfo> getAllTypeInfos() {
/*     */     List<TypeInfo> types = new ArrayList<>();
/*     */     Collection<JavaMethodImpl> methods = this.methodToJM.values();
/*     */     for (JavaMethodImpl m : methods)
/*     */       m.fillTypes(types); 
/*     */     return types;
/*     */   }
/*     */   
/*     */   private void createBridgeMap(List<TypeReference> types) {
/*     */     for (TypeReference type : types) {
/*     */       Bridge bridge = this.jaxbContext.createBridge(type);
/*     */       this.bridgeMap.put(type, bridge);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createBondMap(List<TypeInfo> types) {
/*     */     for (TypeInfo type : types) {
/*     */       XMLBridge binding = this.bindingContext.createBridge(type);
/*     */       this.xmlBridgeMap.put(type, binding);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isKnownFault(QName name, Method method) {
/*     */     JavaMethodImpl m = getJavaMethod(method);
/*     */     for (CheckedExceptionImpl ce : m.getCheckedExceptions()) {
/*     */       if ((ce.getDetailType()).tagName.equals(name))
/*     */         return true; 
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCheckedException(Method m, Class ex) {
/*     */     JavaMethodImpl jm = getJavaMethod(m);
/*     */     for (CheckedExceptionImpl ce : jm.getCheckedExceptions()) {
/*     */       if (ce.getExceptionClass().equals(ex))
/*     */         return true; 
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public JavaMethodImpl getJavaMethod(Method method) {
/*     */     return this.methodToJM.get(method);
/*     */   }
/*     */   
/*     */   public JavaMethodImpl getJavaMethod(QName name) {
/*     */     return this.nameToJM.get(name);
/*     */   }
/*     */   
/*     */   public JavaMethod getJavaMethodForWsdlOperation(QName operationName) {
/*     */     return this.wsdlOpToJM.get(operationName);
/*     */   }
/*     */   
/*     */   public QName getQNameForJM(JavaMethodImpl jm) {
/*     */     for (QName key : this.nameToJM.keySet()) {
/*     */       JavaMethodImpl jmethod = this.nameToJM.get(key);
/*     */       if (jmethod.getOperationName().equals(jm.getOperationName()))
/*     */         return key; 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public final Collection<JavaMethodImpl> getJavaMethods() {
/*     */     return Collections.unmodifiableList(this.javaMethods);
/*     */   }
/*     */   
/*     */   void addJavaMethod(JavaMethodImpl jm) {
/*     */     if (jm != null)
/*     */       this.javaMethods.add(jm); 
/*     */   }
/*     */   
/*     */   private List<ParameterImpl> applyRpcLitParamBinding(JavaMethodImpl method, WrapperParameter wrapperParameter, WSDLBoundPortType boundPortType, WebParam.Mode mode) {
/*     */     QName opName = new QName(boundPortType.getPortTypeName().getNamespaceURI(), method.getOperationName());
/*     */     WSDLBoundOperation bo = boundPortType.get(opName);
/*     */     Map<Integer, ParameterImpl> bodyParams = new HashMap<>();
/*     */     List<ParameterImpl> unboundParams = new ArrayList<>();
/*     */     List<ParameterImpl> attachParams = new ArrayList<>();
/*     */     for (ParameterImpl param : wrapperParameter.wrapperChildren) {
/*     */       String partName = param.getPartName();
/*     */       if (partName == null)
/*     */         continue; 
/*     */       ParameterBinding paramBinding = boundPortType.getBinding(opName, partName, mode);
/*     */       if (paramBinding != null) {
/*     */         if (mode == WebParam.Mode.IN) {
/*     */           param.setInBinding(paramBinding);
/*     */         } else if (mode == WebParam.Mode.OUT || mode == WebParam.Mode.INOUT) {
/*     */           param.setOutBinding(paramBinding);
/*     */         } 
/*     */         if (paramBinding.isUnbound()) {
/*     */           unboundParams.add(param);
/*     */           continue;
/*     */         } 
/*     */         if (paramBinding.isAttachment()) {
/*     */           attachParams.add(param);
/*     */           continue;
/*     */         } 
/*     */         if (paramBinding.isBody()) {
/*     */           if (bo != null) {
/*     */             WSDLPart p = bo.getPart(param.getPartName(), mode);
/*     */             if (p != null) {
/*     */               bodyParams.put(Integer.valueOf(p.getIndex()), param);
/*     */               continue;
/*     */             } 
/*     */             bodyParams.put(Integer.valueOf(bodyParams.size()), param);
/*     */             continue;
/*     */           } 
/*     */           bodyParams.put(Integer.valueOf(bodyParams.size()), param);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     wrapperParameter.clear();
/*     */     for (int i = 0; i < bodyParams.size(); i++) {
/*     */       ParameterImpl p = bodyParams.get(Integer.valueOf(i));
/*     */       wrapperParameter.addWrapperChild(p);
/*     */     } 
/*     */     for (ParameterImpl p : unboundParams)
/*     */       wrapperParameter.addWrapperChild(p); 
/*     */     return attachParams;
/*     */   }
/*     */   
/*     */   void put(QName name, JavaMethodImpl jm) {
/*     */     this.nameToJM.put(name, jm);
/*     */   }
/*     */   
/*     */   void put(Method method, JavaMethodImpl jm) {
/*     */     this.methodToJM.put(method, jm);
/*     */   }
/*     */   
/*     */   void putOp(QName opName, JavaMethodImpl jm) {
/*     */     this.wsdlOpToJM.put(opName, jm);
/*     */   }
/*     */   
/*     */   public String getWSDLLocation() {
/*     */     return this.wsdlLocation;
/*     */   }
/*     */   
/*     */   void setWSDLLocation(String location) {
/*     */     this.wsdlLocation = location;
/*     */   }
/*     */   
/*     */   public QName getServiceQName() {
/*     */     return this.serviceName;
/*     */   }
/*     */   
/*     */   public WSDLPort getPort() {
/*     */     return this.port;
/*     */   }
/*     */   
/*     */   public QName getPortName() {
/*     */     return this.portName;
/*     */   }
/*     */   
/*     */   public QName getPortTypeName() {
/*     */     return this.portTypeName;
/*     */   }
/*     */   
/*     */   void setServiceQName(QName name) {
/*     */     this.serviceName = name;
/*     */   }
/*     */   
/*     */   void setPortName(QName name) {
/*     */     this.portName = name;
/*     */   }
/*     */   
/*     */   void setPortTypeName(QName name) {
/*     */     this.portTypeName = name;
/*     */   }
/*     */   
/*     */   void setTargetNamespace(String namespace) {
/*     */     this.targetNamespace = namespace;
/*     */   }
/*     */   
/*     */   public String getTargetNamespace() {
/*     */     return this.targetNamespace;
/*     */   }
/*     */   
/*     */   String getDefaultSchemaNamespace() {
/*     */     String defaultNamespace = getTargetNamespace();
/*     */     if (this.defaultSchemaNamespaceSuffix == null)
/*     */       return defaultNamespace; 
/*     */     if (!defaultNamespace.endsWith("/"))
/*     */       defaultNamespace = defaultNamespace + "/"; 
/*     */     return defaultNamespace + this.defaultSchemaNamespaceSuffix;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getBoundPortTypeName() {
/*     */     assert this.portName != null;
/*     */     return new QName(this.portName.getNamespaceURI(), this.portName.getLocalPart() + "Binding");
/*     */   }
/*     */   
/*     */   public void addAdditionalClasses(Class... additionalClasses) {
/*     */     for (Class cls : additionalClasses)
/*     */       this.additionalClasses.add(cls); 
/*     */   }
/*     */   
/*     */   public Databinding getDatabinding() {
/*     */     return this.databinding;
/*     */   }
/*     */   
/*     */   public void setDatabinding(Databinding wsRuntime) {
/*     */     this.databinding = wsRuntime;
/*     */   }
/*     */   
/*     */   public WSBinding getWSBinding() {
/*     */     return this.wsBinding;
/*     */   }
/*     */   
/*     */   public Class getContractClass() {
/*     */     return this.contractClass;
/*     */   }
/*     */   
/*     */   public Class getEndpointClass() {
/*     */     return this.endpointClass;
/*     */   }
/*     */   
/*     */   protected abstract void populateMaps();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/AbstractSEIModelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */