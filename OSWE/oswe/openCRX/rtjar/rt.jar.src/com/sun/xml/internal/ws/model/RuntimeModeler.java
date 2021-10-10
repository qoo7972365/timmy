/*      */ package com.sun.xml.internal.ws.model;
/*      */ 
/*      */ import com.oracle.webservices.internal.api.EnvelopeStyle;
/*      */ import com.oracle.webservices.internal.api.EnvelopeStyleFeature;
/*      */ import com.oracle.webservices.internal.api.databinding.DatabindingMode;
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.localization.Localizable;
/*      */ import com.sun.xml.internal.ws.api.BindingID;
/*      */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*      */ import com.sun.xml.internal.ws.api.WSBinding;
/*      */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*      */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*      */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*      */ import com.sun.xml.internal.ws.api.model.ExceptionType;
/*      */ import com.sun.xml.internal.ws.api.model.MEP;
/*      */ import com.sun.xml.internal.ws.api.model.Parameter;
/*      */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.internal.ws.api.model.soap.SOAPBinding;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLInput;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPart;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*      */ import com.sun.xml.internal.ws.model.soap.SOAPBindingImpl;
/*      */ import com.sun.xml.internal.ws.resources.ModelerMessages;
/*      */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*      */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*      */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*      */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.rmi.RemoteException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.logging.Logger;
/*      */ import javax.jws.Oneway;
/*      */ import javax.jws.WebMethod;
/*      */ import javax.jws.WebParam;
/*      */ import javax.jws.WebResult;
/*      */ import javax.jws.WebService;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.bind.annotation.XmlSeeAlso;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.ws.Action;
/*      */ import javax.xml.ws.AsyncHandler;
/*      */ import javax.xml.ws.BindingType;
/*      */ import javax.xml.ws.FaultAction;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.RequestWrapper;
/*      */ import javax.xml.ws.Response;
/*      */ import javax.xml.ws.ResponseWrapper;
/*      */ import javax.xml.ws.WebFault;
/*      */ import javax.xml.ws.soap.MTOM;
/*      */ import javax.xml.ws.soap.MTOMFeature;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RuntimeModeler
/*      */ {
/*      */   private final WebServiceFeatureList features;
/*      */   private BindingID bindingId;
/*      */   private WSBinding wsBinding;
/*      */   private final Class portClass;
/*      */   private AbstractSEIModelImpl model;
/*      */   private SOAPBindingImpl defaultBinding;
/*      */   private String packageName;
/*      */   private String targetNamespace;
/*      */   private boolean isWrapped = true;
/*      */   private ClassLoader classLoader;
/*      */   private final WSDLPort binding;
/*      */   private QName serviceName;
/*      */   private QName portName;
/*      */   private Set<Class> classUsesWebMethod;
/*      */   private DatabindingConfig config;
/*      */   private MetadataReader metadataReader;
/*      */   public static final String PD_JAXWS_PACKAGE_PD = ".jaxws.";
/*      */   public static final String JAXWS_PACKAGE_PD = "jaxws.";
/*      */   public static final String RESPONSE = "Response";
/*      */   public static final String RETURN = "return";
/*      */   public static final String BEAN = "Bean";
/*      */   public static final String SERVICE = "Service";
/*      */   public static final String PORT = "Port";
/*  116 */   public static final Class HOLDER_CLASS = Holder.class;
/*  117 */   public static final Class<RemoteException> REMOTE_EXCEPTION_CLASS = RemoteException.class;
/*  118 */   public static final Class<RuntimeException> RUNTIME_EXCEPTION_CLASS = RuntimeException.class;
/*  119 */   public static final Class<Exception> EXCEPTION_CLASS = Exception.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DecapitalizeExceptionBeanProperties = "com.sun.xml.internal.ws.api.model.DecapitalizeExceptionBeanProperties";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SuppressDocLitWrapperGeneration = "com.sun.xml.internal.ws.api.model.SuppressDocLitWrapperGeneration";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DocWrappeeNamespapceQualified = "com.sun.xml.internal.ws.api.model.DocWrappeeNamespapceQualified";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RuntimeModeler(@NotNull DatabindingConfig config) {
/*  149 */     this.portClass = (config.getEndpointClass() != null) ? config.getEndpointClass() : config.getContractClass();
/*  150 */     this.serviceName = config.getMappingInfo().getServiceName();
/*  151 */     this.binding = config.getWsdlPort();
/*  152 */     this.classLoader = config.getClassLoader();
/*  153 */     this.portName = config.getMappingInfo().getPortName();
/*  154 */     this.config = config;
/*  155 */     this.wsBinding = config.getWSBinding();
/*  156 */     this.metadataReader = config.getMetadataReader();
/*  157 */     this.targetNamespace = config.getMappingInfo().getTargetNamespace();
/*  158 */     if (this.metadataReader == null) this.metadataReader = new ReflectAnnotationReader(); 
/*  159 */     if (this.wsBinding != null) {
/*  160 */       this.bindingId = this.wsBinding.getBindingId();
/*  161 */       if (config.getFeatures() != null) this.wsBinding.getFeatures().mergeFeatures(config.getFeatures(), false); 
/*  162 */       if (this.binding != null) this.wsBinding.getFeatures().mergeFeatures((Iterable)this.binding.getFeatures(), false); 
/*  163 */       this.features = WebServiceFeatureList.toList((Iterable)this.wsBinding.getFeatures());
/*      */     } else {
/*  165 */       this.bindingId = config.getMappingInfo().getBindingID();
/*  166 */       this.features = WebServiceFeatureList.toList(config.getFeatures());
/*  167 */       if (this.binding != null) this.bindingId = this.binding.getBinding().getBindingId(); 
/*  168 */       if (this.bindingId == null) this.bindingId = getDefaultBindingID(); 
/*  169 */       if (!this.features.contains(MTOMFeature.class)) {
/*  170 */         MTOM mtomAn = getAnnotation(this.portClass, MTOM.class);
/*  171 */         if (mtomAn != null) this.features.add(WebServiceFeatureList.getFeature((Annotation)mtomAn)); 
/*      */       } 
/*  173 */       if (!this.features.contains(EnvelopeStyleFeature.class)) {
/*  174 */         EnvelopeStyle es = getAnnotation(this.portClass, EnvelopeStyle.class);
/*  175 */         if (es != null) this.features.add(WebServiceFeatureList.getFeature((Annotation)es)); 
/*      */       } 
/*  177 */       this.wsBinding = this.bindingId.createBinding((WSFeatureList)this.features);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BindingID getDefaultBindingID() {
/*  182 */     BindingType bt = getAnnotation(this.portClass, BindingType.class);
/*  183 */     if (bt != null) return BindingID.parse(bt.value()); 
/*  184 */     SOAPVersion ver = WebServiceFeatureList.getSoapVersion((WSFeatureList)this.features);
/*  185 */     boolean mtomEnabled = this.features.isEnabled(MTOMFeature.class);
/*  186 */     if (SOAPVersion.SOAP_12.equals(ver)) {
/*  187 */       return mtomEnabled ? (BindingID)BindingID.SOAP12_HTTP_MTOM : (BindingID)BindingID.SOAP12_HTTP;
/*      */     }
/*  189 */     return mtomEnabled ? (BindingID)BindingID.SOAP11_HTTP_MTOM : (BindingID)BindingID.SOAP11_HTTP;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClassLoader(ClassLoader classLoader) {
/*  198 */     this.classLoader = classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortName(QName portName) {
/*  207 */     this.portName = portName;
/*      */   }
/*      */   
/*      */   private <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> T) {
/*  211 */     return (T)this.metadataReader.getAnnotation(T, clazz);
/*      */   }
/*      */   
/*      */   private <T extends Annotation> T getAnnotation(Method method, Class<T> T) {
/*  215 */     return (T)this.metadataReader.getAnnotation(T, method);
/*      */   }
/*      */   
/*      */   private Annotation[] getAnnotations(Method method) {
/*  219 */     return this.metadataReader.getAnnotations(method);
/*      */   }
/*      */   
/*      */   private Annotation[] getAnnotations(Class<?> c) {
/*  223 */     return this.metadataReader.getAnnotations(c);
/*      */   }
/*      */   private Annotation[][] getParamAnnotations(Method method) {
/*  226 */     return this.metadataReader.getParameterAnnotations(method);
/*      */   }
/*      */ 
/*      */   
/*  230 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractSEIModelImpl buildRuntimeModel() {
/*  240 */     this.model = new SOAPSEIModel(this.features);
/*  241 */     this.model.contractClass = this.config.getContractClass();
/*  242 */     this.model.endpointClass = this.config.getEndpointClass();
/*  243 */     this.model.classLoader = this.classLoader;
/*  244 */     this.model.wsBinding = this.wsBinding;
/*  245 */     this.model.databindingInfo.setWsdlURL(this.config.getWsdlURL());
/*  246 */     this.model.databindingInfo.properties().putAll(this.config.properties());
/*  247 */     if (this.model.contractClass == null) this.model.contractClass = this.portClass; 
/*  248 */     if (this.model.endpointClass == null && !this.portClass.isInterface()) this.model.endpointClass = this.portClass; 
/*  249 */     Class<?> seiClass = this.portClass;
/*  250 */     this.metadataReader.getProperties(this.model.databindingInfo.properties(), this.portClass);
/*  251 */     WebService webService = getAnnotation(this.portClass, WebService.class);
/*  252 */     if (webService == null) {
/*  253 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { this.portClass
/*  254 */             .getCanonicalName() });
/*      */     }
/*  256 */     Class<?> seiFromConfig = configEndpointInterface();
/*  257 */     if (webService.endpointInterface().length() > 0 || seiFromConfig != null) {
/*  258 */       if (seiFromConfig != null) {
/*  259 */         seiClass = seiFromConfig;
/*      */       } else {
/*  261 */         seiClass = getClass(webService.endpointInterface(), ModelerMessages.localizableRUNTIME_MODELER_CLASS_NOT_FOUND(webService.endpointInterface()));
/*      */       } 
/*  263 */       this.model.contractClass = seiClass;
/*  264 */       this.model.endpointClass = this.portClass;
/*  265 */       WebService seiService = getAnnotation(seiClass, WebService.class);
/*  266 */       if (seiService == null) {
/*  267 */         throw new RuntimeModelerException("runtime.modeler.endpoint.interface.no.webservice", new Object[] { webService
/*  268 */               .endpointInterface() });
/*      */       }
/*      */ 
/*      */       
/*  272 */       SOAPBinding sbPortClass = getAnnotation(this.portClass, SOAPBinding.class);
/*  273 */       SOAPBinding sbSei = getAnnotation(seiClass, SOAPBinding.class);
/*  274 */       if (sbPortClass != null && (
/*  275 */         sbSei == null || sbSei.style() != sbPortClass.style() || sbSei.use() != sbPortClass.use())) {
/*  276 */         logger.warning(ServerMessages.RUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL("@SOAPBinding", this.portClass.getName(), seiClass.getName()));
/*      */       }
/*      */     } 
/*      */     
/*  280 */     if (this.serviceName == null)
/*  281 */       this.serviceName = getServiceName(this.portClass, this.metadataReader); 
/*  282 */     this.model.setServiceQName(this.serviceName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     if (this.portName == null) this.portName = getPortName(this.portClass, this.metadataReader, this.serviceName.getNamespaceURI()); 
/*  299 */     this.model.setPortName(this.portName);
/*      */ 
/*      */     
/*  302 */     DatabindingMode dbm2 = getAnnotation(this.portClass, DatabindingMode.class);
/*  303 */     if (dbm2 != null) this.model.databindingInfo.setDatabindingMode(dbm2.value());
/*      */     
/*  305 */     processClass(seiClass);
/*  306 */     if (this.model.getJavaMethods().size() == 0)
/*  307 */       throw new RuntimeModelerException("runtime.modeler.no.operations", new Object[] { this.portClass
/*  308 */             .getName() }); 
/*  309 */     this.model.postProcess();
/*      */ 
/*      */ 
/*      */     
/*  313 */     this.config.properties().put(BindingContext.class.getName(), this.model.bindingContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  319 */     if (this.binding != null)
/*  320 */       this.model.freeze(this.binding); 
/*  321 */     return this.model;
/*      */   }
/*      */   
/*      */   private Class configEndpointInterface() {
/*  325 */     if (this.config.getEndpointClass() == null || this.config
/*  326 */       .getEndpointClass().isInterface()) return null; 
/*  327 */     return this.config.getContractClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class getClass(String className, Localizable errorMessage) {
/*      */     try {
/*  339 */       if (this.classLoader == null) {
/*  340 */         return Thread.currentThread().getContextClassLoader().loadClass(className);
/*      */       }
/*  342 */       return this.classLoader.loadClass(className);
/*  343 */     } catch (ClassNotFoundException e) {
/*  344 */       throw new RuntimeModelerException(errorMessage);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean noWrapperGen() {
/*  349 */     Object o = this.config.properties().get("com.sun.xml.internal.ws.api.model.SuppressDocLitWrapperGeneration");
/*  350 */     return (o != null && o instanceof Boolean) ? ((Boolean)o).booleanValue() : false;
/*      */   }
/*      */   
/*      */   private Class getRequestWrapperClass(String className, Method method, QName reqElemName) {
/*  354 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  356 */       return loader.loadClass(className);
/*  357 */     } catch (ClassNotFoundException e) {
/*  358 */       if (noWrapperGen()) return WrapperComposite.class; 
/*  359 */       logger.fine("Dynamically creating request wrapper Class " + className);
/*  360 */       return WrapperBeanGenerator.createRequestWrapperBean(className, method, reqElemName, loader);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Class getResponseWrapperClass(String className, Method method, QName resElemName) {
/*  365 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  367 */       return loader.loadClass(className);
/*  368 */     } catch (ClassNotFoundException e) {
/*  369 */       if (noWrapperGen()) return WrapperComposite.class; 
/*  370 */       logger.fine("Dynamically creating response wrapper bean Class " + className);
/*  371 */       return WrapperBeanGenerator.createResponseWrapperBean(className, method, resElemName, loader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Class getExceptionBeanClass(String className, Class exception, String name, String namespace) {
/*  377 */     boolean decapitalizeExceptionBeanProperties = true;
/*  378 */     Object o = this.config.properties().get("com.sun.xml.internal.ws.api.model.DecapitalizeExceptionBeanProperties");
/*  379 */     if (o != null && o instanceof Boolean) decapitalizeExceptionBeanProperties = ((Boolean)o).booleanValue(); 
/*  380 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  382 */       return loader.loadClass(className);
/*  383 */     } catch (ClassNotFoundException e) {
/*  384 */       logger.fine("Dynamically creating exception bean Class " + className);
/*  385 */       return WrapperBeanGenerator.createExceptionBean(className, exception, this.targetNamespace, name, namespace, loader, decapitalizeExceptionBeanProperties);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void determineWebMethodUse(Class<Object> clazz) {
/*  390 */     if (clazz == null)
/*      */       return; 
/*  392 */     if (!clazz.isInterface()) {
/*  393 */       if (clazz == Object.class) {
/*      */         return;
/*      */       }
/*  396 */       for (Method method : clazz.getMethods()) {
/*  397 */         if (method.getDeclaringClass() == clazz) {
/*      */           
/*  399 */           WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  400 */           if (webMethod != null && !webMethod.exclude()) {
/*  401 */             this.classUsesWebMethod.add(clazz); break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  406 */     determineWebMethodUse(clazz.getSuperclass());
/*      */   }
/*      */   
/*      */   void processClass(Class<?> clazz) {
/*  410 */     this.classUsesWebMethod = (Set)new HashSet<>();
/*  411 */     determineWebMethodUse(clazz);
/*  412 */     WebService webService = getAnnotation(clazz, WebService.class);
/*  413 */     QName portTypeName = getPortTypeName(clazz, this.targetNamespace, this.metadataReader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  419 */     this.packageName = "";
/*  420 */     if (clazz.getPackage() != null) {
/*  421 */       this.packageName = clazz.getPackage().getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     this.targetNamespace = portTypeName.getNamespaceURI();
/*  428 */     this.model.setPortTypeName(portTypeName);
/*  429 */     this.model.setTargetNamespace(this.targetNamespace);
/*  430 */     this.model.defaultSchemaNamespaceSuffix = this.config.getMappingInfo().getDefaultSchemaNamespaceSuffix();
/*  431 */     this.model.setWSDLLocation(webService.wsdlLocation());
/*      */     
/*  433 */     SOAPBinding soapBinding = getAnnotation(clazz, SOAPBinding.class);
/*  434 */     if (soapBinding != null) {
/*  435 */       if (soapBinding.style() == SOAPBinding.Style.RPC && soapBinding.parameterStyle() == SOAPBinding.ParameterStyle.BARE) {
/*  436 */         throw new RuntimeModelerException("runtime.modeler.invalid.soapbinding.parameterstyle", new Object[] { soapBinding, clazz });
/*      */       }
/*      */ 
/*      */       
/*  440 */       this.isWrapped = (soapBinding.parameterStyle() == SOAPBinding.ParameterStyle.WRAPPED);
/*      */     } 
/*  442 */     this.defaultBinding = createBinding(soapBinding);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  462 */     for (Method method : clazz.getMethods()) {
/*  463 */       if (clazz.isInterface() || (
/*  464 */         method.getDeclaringClass() != Object.class && (
/*  465 */         !getBooleanSystemProperty("com.sun.xml.internal.ws.legacyWebMethod").booleanValue() ? 
/*  466 */         !isWebMethodBySpec(method, clazz) : 
/*      */ 
/*      */         
/*  469 */         !isWebMethod(method))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  475 */         processMethod(method);
/*      */       }
/*      */     } 
/*  478 */     XmlSeeAlso xmlSeeAlso = getAnnotation(clazz, XmlSeeAlso.class);
/*  479 */     if (xmlSeeAlso != null) {
/*  480 */       this.model.addAdditionalClasses(xmlSeeAlso.value());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isWebMethodBySpec(Method method, Class clazz) {
/*  496 */     int modifiers = method.getModifiers();
/*  497 */     boolean staticFinal = (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
/*      */     
/*  499 */     assert Modifier.isPublic(modifiers);
/*  500 */     assert !clazz.isInterface();
/*      */     
/*  502 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  503 */     if (webMethod != null) {
/*  504 */       if (webMethod.exclude()) {
/*  505 */         return false;
/*      */       }
/*  507 */       if (staticFinal) {
/*  508 */         throw new RuntimeModelerException(ModelerMessages.localizableRUNTIME_MODELER_WEBMETHOD_MUST_BE_NONSTATICFINAL(method));
/*      */       }
/*  510 */       return true;
/*      */     } 
/*      */     
/*  513 */     if (staticFinal) {
/*  514 */       return false;
/*      */     }
/*      */     
/*  517 */     Class<?> declClass = method.getDeclaringClass();
/*  518 */     return (getAnnotation(declClass, WebService.class) != null);
/*      */   }
/*      */   
/*      */   private boolean isWebMethod(Method method) {
/*  522 */     int modifiers = method.getModifiers();
/*  523 */     if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
/*  524 */       return false;
/*      */     }
/*  526 */     Class<?> clazz = method.getDeclaringClass();
/*  527 */     boolean declHasWebService = (getAnnotation(clazz, WebService.class) != null);
/*  528 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  529 */     if (webMethod != null && !webMethod.exclude() && declHasWebService)
/*  530 */       return true; 
/*  531 */     return (declHasWebService && !this.classUsesWebMethod.contains(clazz));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPBindingImpl createBinding(SOAPBinding soapBinding) {
/*  540 */     SOAPBindingImpl rtSOAPBinding = new SOAPBindingImpl();
/*  541 */     SOAPBinding.Style style = (soapBinding != null) ? soapBinding.style() : SOAPBinding.Style.DOCUMENT;
/*  542 */     rtSOAPBinding.setStyle(style);
/*  543 */     assert this.bindingId != null;
/*  544 */     this.model.bindingId = this.bindingId;
/*  545 */     SOAPVersion soapVersion = this.bindingId.getSOAPVersion();
/*  546 */     rtSOAPBinding.setSOAPVersion(soapVersion);
/*  547 */     return rtSOAPBinding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getNamespace(@NotNull String packageName) {
/*      */     String[] tokens;
/*  557 */     if (packageName.length() == 0) {
/*  558 */       return null;
/*      */     }
/*  560 */     StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
/*      */     
/*  562 */     if (tokenizer.countTokens() == 0) {
/*  563 */       tokens = new String[0];
/*      */     } else {
/*  565 */       tokens = new String[tokenizer.countTokens()];
/*  566 */       for (int j = tokenizer.countTokens() - 1; j >= 0; j--) {
/*  567 */         tokens[j] = tokenizer.nextToken();
/*      */       }
/*      */     } 
/*  570 */     StringBuilder namespace = new StringBuilder("http://");
/*  571 */     for (int i = 0; i < tokens.length; i++) {
/*  572 */       if (i != 0)
/*  573 */         namespace.append('.'); 
/*  574 */       namespace.append(tokens[i]);
/*      */     } 
/*  576 */     namespace.append('/');
/*  577 */     return namespace.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isServiceException(Class<?> exception) {
/*  586 */     return (EXCEPTION_CLASS.isAssignableFrom(exception) && 
/*  587 */       !RUNTIME_EXCEPTION_CLASS.isAssignableFrom(exception) && !REMOTE_EXCEPTION_CLASS.isAssignableFrom(exception));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processMethod(Method method) {
/*      */     JavaMethodImpl javaMethod;
/*  596 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  597 */     if (webMethod != null && webMethod.exclude()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  617 */     String methodName = method.getName();
/*  618 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*      */ 
/*      */     
/*  621 */     if (isOneway) {
/*  622 */       for (Class<?> exception : method.getExceptionTypes()) {
/*  623 */         if (isServiceException(exception)) {
/*  624 */           throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.checked.exceptions", new Object[] { this.portClass
/*  625 */                 .getCanonicalName(), methodName, exception.getName() });
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  632 */     if (method.getDeclaringClass() == this.portClass) {
/*  633 */       javaMethod = new JavaMethodImpl(this.model, method, method, this.metadataReader);
/*      */     } else {
/*      */       try {
/*  636 */         Method tmpMethod = this.portClass.getMethod(method.getName(), method
/*  637 */             .getParameterTypes());
/*  638 */         javaMethod = new JavaMethodImpl(this.model, tmpMethod, method, this.metadataReader);
/*  639 */       } catch (NoSuchMethodException e) {
/*  640 */         throw new RuntimeModelerException("runtime.modeler.method.not.found", new Object[] { method
/*  641 */               .getName(), this.portClass.getName() });
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  648 */     MEP mep = getMEP(method);
/*  649 */     javaMethod.setMEP(mep);
/*      */     
/*  651 */     String action = null;
/*      */ 
/*      */     
/*  654 */     String operationName = method.getName();
/*  655 */     if (webMethod != null) {
/*  656 */       action = webMethod.action();
/*      */       
/*  658 */       operationName = (webMethod.operationName().length() > 0) ? webMethod.operationName() : operationName;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  663 */     if (this.binding != null) {
/*  664 */       WSDLBoundOperation bo = this.binding.getBinding().get(new QName(this.targetNamespace, operationName));
/*  665 */       if (bo != null) {
/*  666 */         WSDLInput wsdlInput = bo.getOperation().getInput();
/*  667 */         String wsaAction = wsdlInput.getAction();
/*  668 */         if (wsaAction != null && !wsdlInput.isDefaultAction()) {
/*  669 */           action = wsaAction;
/*      */         } else {
/*  671 */           action = bo.getSOAPAction();
/*      */         } 
/*      */       } 
/*      */     } 
/*  675 */     javaMethod.setOperationQName(new QName(this.targetNamespace, operationName));
/*  676 */     SOAPBinding methodBinding = getAnnotation(method, SOAPBinding.class);
/*  677 */     if (methodBinding != null && methodBinding.style() == SOAPBinding.Style.RPC) {
/*  678 */       logger.warning(ModelerMessages.RUNTIMEMODELER_INVALID_SOAPBINDING_ON_METHOD(methodBinding, method.getName(), method.getDeclaringClass().getName()));
/*  679 */     } else if (methodBinding == null && !method.getDeclaringClass().equals(this.portClass)) {
/*  680 */       methodBinding = getAnnotation(method.getDeclaringClass(), SOAPBinding.class);
/*  681 */       if (methodBinding != null && methodBinding.style() == SOAPBinding.Style.RPC && methodBinding.parameterStyle() == SOAPBinding.ParameterStyle.BARE) {
/*  682 */         throw new RuntimeModelerException("runtime.modeler.invalid.soapbinding.parameterstyle", new Object[] { methodBinding, method
/*  683 */               .getDeclaringClass() });
/*      */       }
/*      */     } 
/*      */     
/*  687 */     if (methodBinding != null && this.defaultBinding.getStyle() != methodBinding.style()) {
/*  688 */       throw new RuntimeModelerException("runtime.modeler.soapbinding.conflict", new Object[] { methodBinding
/*  689 */             .style(), method.getName(), this.defaultBinding.getStyle() });
/*      */     }
/*      */     
/*  692 */     boolean methodIsWrapped = this.isWrapped;
/*  693 */     SOAPBinding.Style style = this.defaultBinding.getStyle();
/*  694 */     if (methodBinding != null) {
/*  695 */       SOAPBindingImpl mySOAPBinding = createBinding(methodBinding);
/*  696 */       style = mySOAPBinding.getStyle();
/*  697 */       if (action != null)
/*  698 */         mySOAPBinding.setSOAPAction(action); 
/*  699 */       methodIsWrapped = methodBinding.parameterStyle().equals(SOAPBinding.ParameterStyle.WRAPPED);
/*      */       
/*  701 */       javaMethod.setBinding((SOAPBinding)mySOAPBinding);
/*      */     } else {
/*  703 */       SOAPBindingImpl sb = new SOAPBindingImpl((SOAPBinding)this.defaultBinding);
/*  704 */       if (action != null) {
/*  705 */         sb.setSOAPAction(action);
/*      */       } else {
/*  707 */         String defaults = (SOAPVersion.SOAP_11 == sb.getSOAPVersion()) ? "" : null;
/*  708 */         sb.setSOAPAction(defaults);
/*      */       } 
/*  710 */       javaMethod.setBinding((SOAPBinding)sb);
/*      */     } 
/*  712 */     if (!methodIsWrapped) {
/*  713 */       processDocBareMethod(javaMethod, operationName, method);
/*  714 */     } else if (style.equals(SOAPBinding.Style.DOCUMENT)) {
/*  715 */       processDocWrappedMethod(javaMethod, methodName, operationName, method);
/*      */     } else {
/*      */       
/*  718 */       processRpcMethod(javaMethod, methodName, operationName, method);
/*      */     } 
/*  720 */     this.model.addJavaMethod(javaMethod);
/*      */   }
/*      */   
/*      */   private MEP getMEP(Method m) {
/*  724 */     if (getAnnotation(m, Oneway.class) != null) {
/*  725 */       return MEP.ONE_WAY;
/*      */     }
/*  727 */     if (Response.class.isAssignableFrom(m.getReturnType()))
/*  728 */       return MEP.ASYNC_POLL; 
/*  729 */     if (Future.class.isAssignableFrom(m.getReturnType())) {
/*  730 */       return MEP.ASYNC_CALLBACK;
/*      */     }
/*  732 */     return MEP.REQUEST_RESPONSE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processDocWrappedMethod(JavaMethodImpl javaMethod, String methodName, String operationName, Method method) {
/*      */     String requestClassName, responseClassName;
/*  744 */     boolean methodHasHeaderParams = false;
/*  745 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*  746 */     RequestWrapper reqWrapper = getAnnotation(method, RequestWrapper.class);
/*  747 */     ResponseWrapper resWrapper = getAnnotation(method, ResponseWrapper.class);
/*  748 */     String beanPackage = this.packageName + ".jaxws.";
/*  749 */     if (this.packageName == null || this.packageName.length() == 0) {
/*  750 */       beanPackage = "jaxws.";
/*      */     }
/*      */     
/*  753 */     if (reqWrapper != null && reqWrapper.className().length() > 0) {
/*  754 */       requestClassName = reqWrapper.className();
/*      */     } else {
/*  756 */       requestClassName = beanPackage + capitalize(method.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  761 */     if (resWrapper != null && resWrapper.className().length() > 0) {
/*  762 */       responseClassName = resWrapper.className();
/*      */     } else {
/*  764 */       responseClassName = beanPackage + capitalize(method.getName()) + "Response";
/*      */     } 
/*      */     
/*  767 */     String reqName = operationName;
/*  768 */     String reqNamespace = this.targetNamespace;
/*  769 */     String reqPartName = "parameters";
/*  770 */     if (reqWrapper != null) {
/*  771 */       if (reqWrapper.targetNamespace().length() > 0)
/*  772 */         reqNamespace = reqWrapper.targetNamespace(); 
/*  773 */       if (reqWrapper.localName().length() > 0)
/*  774 */         reqName = reqWrapper.localName(); 
/*      */       try {
/*  776 */         if (reqWrapper.partName().length() > 0)
/*  777 */           reqPartName = reqWrapper.partName(); 
/*  778 */       } catch (LinkageError linkageError) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  783 */     QName reqElementName = new QName(reqNamespace, reqName);
/*  784 */     javaMethod.setRequestPayloadName(reqElementName);
/*  785 */     Class requestClass = getRequestWrapperClass(requestClassName, method, reqElementName);
/*      */     
/*  787 */     Class responseClass = null;
/*  788 */     String resName = operationName + "Response";
/*  789 */     String resNamespace = this.targetNamespace;
/*  790 */     QName resElementName = null;
/*  791 */     String resPartName = "parameters";
/*  792 */     if (!isOneway) {
/*  793 */       if (resWrapper != null) {
/*  794 */         if (resWrapper.targetNamespace().length() > 0)
/*  795 */           resNamespace = resWrapper.targetNamespace(); 
/*  796 */         if (resWrapper.localName().length() > 0)
/*  797 */           resName = resWrapper.localName(); 
/*      */         try {
/*  799 */           if (resWrapper.partName().length() > 0)
/*  800 */             resPartName = resWrapper.partName(); 
/*  801 */         } catch (LinkageError linkageError) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  806 */       resElementName = new QName(resNamespace, resName);
/*  807 */       responseClass = getResponseWrapperClass(responseClassName, method, resElementName);
/*      */     } 
/*      */     
/*  810 */     TypeInfo typeRef = new TypeInfo(reqElementName, requestClass, new Annotation[0]);
/*      */     
/*  812 */     typeRef.setNillable(false);
/*  813 */     WrapperParameter requestWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.IN, 0);
/*      */     
/*  815 */     requestWrapper.setPartName(reqPartName);
/*  816 */     requestWrapper.setBinding(ParameterBinding.BODY);
/*  817 */     javaMethod.addParameter(requestWrapper);
/*  818 */     WrapperParameter responseWrapper = null;
/*  819 */     if (!isOneway) {
/*  820 */       typeRef = new TypeInfo(resElementName, responseClass, new Annotation[0]);
/*  821 */       typeRef.setNillable(false);
/*  822 */       responseWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.OUT, -1);
/*  823 */       javaMethod.addParameter(responseWrapper);
/*  824 */       responseWrapper.setBinding(ParameterBinding.BODY);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     WebResult webResult = getAnnotation(method, WebResult.class);
/*  831 */     XmlElement xmlElem = getAnnotation(method, XmlElement.class);
/*  832 */     QName resultQName = getReturnQName(method, webResult, xmlElem);
/*  833 */     Class<?> returnType = method.getReturnType();
/*  834 */     boolean isResultHeader = false;
/*  835 */     if (webResult != null) {
/*  836 */       isResultHeader = webResult.header();
/*  837 */       methodHasHeaderParams = (isResultHeader || methodHasHeaderParams);
/*  838 */       if (isResultHeader && xmlElem != null) {
/*  839 */         throw new RuntimeModelerException("@XmlElement cannot be specified on method " + method + " as the return value is bound to header", new Object[0]);
/*      */       }
/*  841 */       if (resultQName.getNamespaceURI().length() == 0 && webResult.header())
/*      */       {
/*  843 */         resultQName = new QName(this.targetNamespace, resultQName.getLocalPart());
/*      */       }
/*      */     } 
/*      */     
/*  847 */     if (javaMethod.isAsync()) {
/*  848 */       returnType = getAsyncReturnType(method, returnType);
/*  849 */       resultQName = new QName("return");
/*      */     } 
/*  851 */     resultQName = qualifyWrappeeIfNeeded(resultQName, resNamespace);
/*  852 */     if (!isOneway && returnType != null && !returnType.getName().equals("void")) {
/*  853 */       Annotation[] rann = getAnnotations(method);
/*  854 */       if (resultQName.getLocalPart() != null) {
/*  855 */         TypeInfo rTypeReference = new TypeInfo(resultQName, returnType, rann);
/*  856 */         this.metadataReader.getProperties(rTypeReference.properties(), method);
/*  857 */         rTypeReference.setGenericType(method.getGenericReturnType());
/*  858 */         ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/*  859 */         if (isResultHeader) {
/*  860 */           returnParameter.setBinding(ParameterBinding.HEADER);
/*  861 */           javaMethod.addParameter(returnParameter);
/*      */         } else {
/*  863 */           returnParameter.setBinding(ParameterBinding.BODY);
/*  864 */           responseWrapper.addWrapperChild(returnParameter);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  870 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*  871 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/*  872 */     Annotation[][] pannotations = getParamAnnotations(method);
/*  873 */     int pos = 0;
/*  874 */     for (Class<?> clazzType : parameterTypes) {
/*  875 */       String partName = null;
/*  876 */       String paramName = "arg" + pos;
/*      */       
/*  878 */       boolean isHeader = false;
/*      */       
/*  880 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */ 
/*      */ 
/*      */         
/*  884 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/*  886 */         if (isHolder && 
/*  887 */           clazzType == Holder.class) {
/*  888 */           clazzType = (Class)Utils.REFLECTION_NAVIGATOR.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/*      */         
/*  891 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/*  892 */         WebParam webParam = null;
/*  893 */         xmlElem = null;
/*  894 */         for (Annotation annotation : pannotations[pos]) {
/*  895 */           if (annotation.annotationType() == WebParam.class) {
/*  896 */             webParam = (WebParam)annotation;
/*  897 */           } else if (annotation.annotationType() == XmlElement.class) {
/*  898 */             xmlElem = (XmlElement)annotation;
/*      */           } 
/*      */         } 
/*  901 */         QName paramQName = getParameterQName(method, webParam, xmlElem, paramName);
/*  902 */         if (webParam != null) {
/*  903 */           isHeader = webParam.header();
/*  904 */           methodHasHeaderParams = (isHeader || methodHasHeaderParams);
/*  905 */           if (isHeader && xmlElem != null) {
/*  906 */             throw new RuntimeModelerException("@XmlElement cannot be specified on method " + method + " parameter that is bound to header", new Object[0]);
/*      */           }
/*  908 */           if (webParam.partName().length() > 0) {
/*  909 */             partName = webParam.partName();
/*      */           } else {
/*  911 */             partName = paramQName.getLocalPart();
/*  912 */           }  if (isHeader && paramQName.getNamespaceURI().equals("")) {
/*  913 */             paramQName = new QName(this.targetNamespace, paramQName.getLocalPart());
/*      */           }
/*  915 */           paramMode = webParam.mode();
/*  916 */           if (isHolder && paramMode == WebParam.Mode.IN)
/*  917 */             paramMode = WebParam.Mode.INOUT; 
/*      */         } 
/*  919 */         paramQName = qualifyWrappeeIfNeeded(paramQName, reqNamespace);
/*  920 */         typeRef = new TypeInfo(paramQName, clazzType, pannotations[pos]);
/*      */         
/*  922 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/*  923 */         typeRef.setGenericType(genericParameterTypes[pos]);
/*  924 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/*      */         
/*  926 */         if (isHeader) {
/*  927 */           param.setBinding(ParameterBinding.HEADER);
/*  928 */           javaMethod.addParameter(param);
/*  929 */           param.setPartName(partName);
/*      */         } else {
/*  931 */           param.setBinding(ParameterBinding.BODY);
/*  932 */           if (paramMode != WebParam.Mode.OUT) {
/*  933 */             requestWrapper.addWrapperChild(param);
/*      */           }
/*  935 */           if (paramMode != WebParam.Mode.IN) {
/*  936 */             if (isOneway) {
/*  937 */               throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.out.parameters", new Object[] { this.portClass
/*  938 */                     .getCanonicalName(), methodName });
/*      */             }
/*  940 */             responseWrapper.addWrapperChild(param);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  948 */     if (methodHasHeaderParams) {
/*  949 */       resPartName = "result";
/*      */     }
/*  951 */     if (responseWrapper != null)
/*  952 */       responseWrapper.setPartName(resPartName); 
/*  953 */     processExceptions(javaMethod, method);
/*      */   }
/*      */   
/*      */   private QName qualifyWrappeeIfNeeded(QName resultQName, String ns) {
/*  957 */     Object o = this.config.properties().get("com.sun.xml.internal.ws.api.model.DocWrappeeNamespapceQualified");
/*  958 */     boolean qualified = (o != null && o instanceof Boolean) ? ((Boolean)o).booleanValue() : false;
/*  959 */     if (qualified && (
/*  960 */       resultQName.getNamespaceURI() == null || "".equals(resultQName.getNamespaceURI()))) {
/*  961 */       return new QName(ns, resultQName.getLocalPart());
/*      */     }
/*      */     
/*  964 */     return resultQName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processRpcMethod(JavaMethodImpl javaMethod, String methodName, String operationName, Method method) {
/*      */     QName resultQName;
/*  976 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  983 */     Map<Integer, ParameterImpl> resRpcParams = new TreeMap<>();
/*  984 */     Map<Integer, ParameterImpl> reqRpcParams = new TreeMap<>();
/*      */ 
/*      */     
/*  987 */     String reqNamespace = this.targetNamespace;
/*  988 */     String respNamespace = this.targetNamespace;
/*      */     
/*  990 */     if (this.binding != null && SOAPBinding.Style.RPC.equals(this.binding.getBinding().getStyle())) {
/*  991 */       QName opQName = new QName(this.binding.getBinding().getPortTypeName().getNamespaceURI(), operationName);
/*  992 */       WSDLBoundOperation op = this.binding.getBinding().get(opQName);
/*  993 */       if (op != null) {
/*      */         
/*  995 */         if (op.getRequestNamespace() != null) {
/*  996 */           reqNamespace = op.getRequestNamespace();
/*      */         }
/*      */ 
/*      */         
/* 1000 */         if (op.getResponseNamespace() != null) {
/* 1001 */           respNamespace = op.getResponseNamespace();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1006 */     QName reqElementName = new QName(reqNamespace, operationName);
/* 1007 */     javaMethod.setRequestPayloadName(reqElementName);
/* 1008 */     QName resElementName = null;
/* 1009 */     if (!isOneway) {
/* 1010 */       resElementName = new QName(respNamespace, operationName + "Response");
/*      */     }
/*      */     
/* 1013 */     Class<WrapperComposite> wrapperType = WrapperComposite.class;
/* 1014 */     TypeInfo typeRef = new TypeInfo(reqElementName, wrapperType, new Annotation[0]);
/* 1015 */     WrapperParameter requestWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.IN, 0);
/* 1016 */     requestWrapper.setInBinding(ParameterBinding.BODY);
/* 1017 */     javaMethod.addParameter(requestWrapper);
/* 1018 */     WrapperParameter responseWrapper = null;
/* 1019 */     if (!isOneway) {
/* 1020 */       typeRef = new TypeInfo(resElementName, wrapperType, new Annotation[0]);
/* 1021 */       responseWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.OUT, -1);
/* 1022 */       responseWrapper.setOutBinding(ParameterBinding.BODY);
/* 1023 */       javaMethod.addParameter(responseWrapper);
/*      */     } 
/*      */     
/* 1026 */     Class<?> returnType = method.getReturnType();
/* 1027 */     String resultName = "return";
/* 1028 */     String resultTNS = this.targetNamespace;
/* 1029 */     String resultPartName = resultName;
/* 1030 */     boolean isResultHeader = false;
/* 1031 */     WebResult webResult = getAnnotation(method, WebResult.class);
/*      */     
/* 1033 */     if (webResult != null) {
/* 1034 */       isResultHeader = webResult.header();
/* 1035 */       if (webResult.name().length() > 0)
/* 1036 */         resultName = webResult.name(); 
/* 1037 */       if (webResult.partName().length() > 0) {
/* 1038 */         resultPartName = webResult.partName();
/* 1039 */         if (!isResultHeader)
/* 1040 */           resultName = resultPartName; 
/*      */       } else {
/* 1042 */         resultPartName = resultName;
/* 1043 */       }  if (webResult.targetNamespace().length() > 0)
/* 1044 */         resultTNS = webResult.targetNamespace(); 
/* 1045 */       isResultHeader = webResult.header();
/*      */     } 
/*      */     
/* 1048 */     if (isResultHeader) {
/* 1049 */       resultQName = new QName(resultTNS, resultName);
/*      */     } else {
/* 1051 */       resultQName = new QName(resultName);
/*      */     } 
/* 1053 */     if (javaMethod.isAsync()) {
/* 1054 */       returnType = getAsyncReturnType(method, returnType);
/*      */     }
/*      */     
/* 1057 */     if (!isOneway && returnType != null && returnType != void.class) {
/* 1058 */       Annotation[] rann = getAnnotations(method);
/* 1059 */       TypeInfo rTypeReference = new TypeInfo(resultQName, returnType, rann);
/* 1060 */       this.metadataReader.getProperties(rTypeReference.properties(), method);
/* 1061 */       rTypeReference.setGenericType(method.getGenericReturnType());
/* 1062 */       ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/* 1063 */       returnParameter.setPartName(resultPartName);
/* 1064 */       if (isResultHeader) {
/* 1065 */         returnParameter.setBinding(ParameterBinding.HEADER);
/* 1066 */         javaMethod.addParameter(returnParameter);
/* 1067 */         rTypeReference.setGlobalElement(true);
/*      */       } else {
/* 1069 */         ParameterBinding rb = getBinding(operationName, resultPartName, false, WebParam.Mode.OUT);
/* 1070 */         returnParameter.setBinding(rb);
/* 1071 */         if (rb.isBody())
/* 1072 */         { rTypeReference.setGlobalElement(false);
/* 1073 */           WSDLPart p = getPart(new QName(this.targetNamespace, operationName), resultPartName, WebParam.Mode.OUT);
/* 1074 */           if (p == null) {
/* 1075 */             resRpcParams.put(Integer.valueOf(resRpcParams.size() + 10000), returnParameter);
/*      */           } else {
/* 1077 */             resRpcParams.put(Integer.valueOf(p.getIndex()), returnParameter);
/*      */           }  }
/* 1079 */         else { javaMethod.addParameter(returnParameter); }
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1085 */     Class<?>[] parameterTypes = method.getParameterTypes();
/* 1086 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/* 1087 */     Annotation[][] pannotations = getParamAnnotations(method);
/* 1088 */     int pos = 0;
/* 1089 */     for (Class<?> clazzType : parameterTypes) {
/* 1090 */       String paramName = "";
/* 1091 */       String paramNamespace = "";
/* 1092 */       String partName = "";
/* 1093 */       boolean isHeader = false;
/*      */       
/* 1095 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */         QName paramQName;
/*      */ 
/*      */         
/* 1099 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/* 1101 */         if (isHolder && 
/* 1102 */           clazzType == Holder.class) {
/* 1103 */           clazzType = (Class)Utils.REFLECTION_NAVIGATOR.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/* 1105 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/* 1106 */         for (Annotation annotation : pannotations[pos]) {
/* 1107 */           if (annotation.annotationType() == WebParam.class) {
/* 1108 */             WebParam webParam = (WebParam)annotation;
/* 1109 */             paramName = webParam.name();
/* 1110 */             partName = webParam.partName();
/* 1111 */             isHeader = webParam.header();
/* 1112 */             WebParam.Mode mode = webParam.mode();
/* 1113 */             paramNamespace = webParam.targetNamespace();
/* 1114 */             if (isHolder && mode == WebParam.Mode.IN)
/* 1115 */               mode = WebParam.Mode.INOUT; 
/* 1116 */             paramMode = mode;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1121 */         if (paramName.length() == 0) {
/* 1122 */           paramName = "arg" + pos;
/*      */         }
/* 1124 */         if (partName.length() == 0) {
/* 1125 */           partName = paramName;
/* 1126 */         } else if (!isHeader) {
/* 1127 */           paramName = partName;
/*      */         } 
/* 1129 */         if (partName.length() == 0) {
/* 1130 */           partName = paramName;
/*      */         }
/*      */ 
/*      */         
/* 1134 */         if (!isHeader) {
/*      */           
/* 1136 */           paramQName = new QName("", paramName);
/*      */         } else {
/* 1138 */           if (paramNamespace.length() == 0)
/* 1139 */             paramNamespace = this.targetNamespace; 
/* 1140 */           paramQName = new QName(paramNamespace, paramName);
/*      */         } 
/* 1142 */         typeRef = new TypeInfo(paramQName, clazzType, pannotations[pos]);
/*      */         
/* 1144 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/* 1145 */         typeRef.setGenericType(genericParameterTypes[pos]);
/* 1146 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/* 1147 */         param.setPartName(partName);
/*      */         
/* 1149 */         if (paramMode == WebParam.Mode.INOUT) {
/* 1150 */           ParameterBinding pb = getBinding(operationName, partName, isHeader, WebParam.Mode.IN);
/* 1151 */           param.setInBinding(pb);
/* 1152 */           pb = getBinding(operationName, partName, isHeader, WebParam.Mode.OUT);
/* 1153 */           param.setOutBinding(pb);
/*      */         }
/* 1155 */         else if (isHeader) {
/* 1156 */           typeRef.setGlobalElement(true);
/* 1157 */           param.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1159 */           ParameterBinding pb = getBinding(operationName, partName, false, paramMode);
/* 1160 */           param.setBinding(pb);
/*      */         } 
/*      */         
/* 1163 */         if (param.getInBinding().isBody())
/* 1164 */         { typeRef.setGlobalElement(false);
/* 1165 */           if (!param.isOUT()) {
/* 1166 */             WSDLPart p = getPart(new QName(this.targetNamespace, operationName), partName, WebParam.Mode.IN);
/* 1167 */             if (p == null) {
/* 1168 */               reqRpcParams.put(Integer.valueOf(reqRpcParams.size() + 10000), param);
/*      */             } else {
/* 1170 */               reqRpcParams.put(Integer.valueOf(p.getIndex()), param);
/*      */             } 
/*      */           } 
/* 1173 */           if (!param.isIN()) {
/* 1174 */             if (isOneway) {
/* 1175 */               throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.out.parameters", new Object[] { this.portClass
/* 1176 */                     .getCanonicalName(), methodName });
/*      */             }
/* 1178 */             WSDLPart p = getPart(new QName(this.targetNamespace, operationName), partName, WebParam.Mode.OUT);
/* 1179 */             if (p == null) {
/* 1180 */               resRpcParams.put(Integer.valueOf(resRpcParams.size() + 10000), param);
/*      */             } else {
/* 1182 */               resRpcParams.put(Integer.valueOf(p.getIndex()), param);
/*      */             } 
/*      */           }  }
/* 1185 */         else { javaMethod.addParameter(param); }
/*      */       
/*      */       } 
/* 1188 */     }  for (ParameterImpl p : reqRpcParams.values())
/* 1189 */       requestWrapper.addWrapperChild(p); 
/* 1190 */     for (ParameterImpl p : resRpcParams.values())
/* 1191 */       responseWrapper.addWrapperChild(p); 
/* 1192 */     processExceptions(javaMethod, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processExceptions(JavaMethodImpl javaMethod, Method method) {
/* 1202 */     Action actionAnn = getAnnotation(method, Action.class);
/* 1203 */     FaultAction[] faultActions = new FaultAction[0];
/* 1204 */     if (actionAnn != null)
/* 1205 */       faultActions = actionAnn.fault(); 
/* 1206 */     for (Class<?> exception : method.getExceptionTypes()) {
/*      */ 
/*      */       
/* 1209 */       if (EXCEPTION_CLASS.isAssignableFrom(exception))
/*      */       {
/* 1211 */         if (!RUNTIME_EXCEPTION_CLASS.isAssignableFrom(exception) && !REMOTE_EXCEPTION_CLASS.isAssignableFrom(exception)) {
/*      */           Class<?> exceptionBean;
/*      */           
/*      */           Annotation[] anns;
/*      */           
/* 1216 */           WebFault webFault = getAnnotation(exception, WebFault.class);
/* 1217 */           Method faultInfoMethod = getWSDLExceptionFaultInfo(exception);
/* 1218 */           ExceptionType exceptionType = ExceptionType.WSDLException;
/* 1219 */           String namespace = this.targetNamespace;
/* 1220 */           String name = exception.getSimpleName();
/* 1221 */           String beanPackage = this.packageName + ".jaxws.";
/* 1222 */           if (this.packageName.length() == 0)
/* 1223 */             beanPackage = "jaxws."; 
/* 1224 */           String className = beanPackage + name + "Bean";
/* 1225 */           String messageName = exception.getSimpleName();
/* 1226 */           if (webFault != null) {
/* 1227 */             if (webFault.faultBean().length() > 0)
/* 1228 */               className = webFault.faultBean(); 
/* 1229 */             if (webFault.name().length() > 0)
/* 1230 */               name = webFault.name(); 
/* 1231 */             if (webFault.targetNamespace().length() > 0)
/* 1232 */               namespace = webFault.targetNamespace(); 
/* 1233 */             if (webFault.messageName().length() > 0)
/* 1234 */               messageName = webFault.messageName(); 
/*      */           } 
/* 1236 */           if (faultInfoMethod == null) {
/* 1237 */             exceptionBean = getExceptionBeanClass(className, exception, name, namespace);
/* 1238 */             exceptionType = ExceptionType.UserDefined;
/* 1239 */             anns = getAnnotations(exceptionBean);
/*      */           } else {
/* 1241 */             exceptionBean = faultInfoMethod.getReturnType();
/* 1242 */             anns = getAnnotations(faultInfoMethod);
/*      */           } 
/* 1244 */           QName faultName = new QName(namespace, name);
/* 1245 */           TypeInfo typeRef = new TypeInfo(faultName, exceptionBean, anns);
/* 1246 */           CheckedExceptionImpl checkedException = new CheckedExceptionImpl(javaMethod, exception, typeRef, exceptionType);
/*      */           
/* 1248 */           checkedException.setMessageName(messageName);
/* 1249 */           for (FaultAction fa : faultActions) {
/* 1250 */             if (fa.className().equals(exception) && !fa.value().equals("")) {
/* 1251 */               checkedException.setFaultAction(fa.value());
/*      */               break;
/*      */             } 
/*      */           } 
/* 1255 */           javaMethod.addException(checkedException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Method getWSDLExceptionFaultInfo(Class<?> exception) {
/* 1268 */     if (getAnnotation(exception, WebFault.class) == null)
/* 1269 */       return null; 
/*      */     try {
/* 1271 */       return exception.getMethod("getFaultInfo", new Class[0]);
/* 1272 */     } catch (NoSuchMethodException e) {
/* 1273 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processDocBareMethod(JavaMethodImpl javaMethod, String operationName, Method method) {
/* 1286 */     String resultName = operationName + "Response";
/* 1287 */     String resultTNS = this.targetNamespace;
/* 1288 */     String resultPartName = null;
/* 1289 */     boolean isResultHeader = false;
/* 1290 */     WebResult webResult = getAnnotation(method, WebResult.class);
/* 1291 */     if (webResult != null) {
/* 1292 */       if (webResult.name().length() > 0)
/* 1293 */         resultName = webResult.name(); 
/* 1294 */       if (webResult.targetNamespace().length() > 0)
/* 1295 */         resultTNS = webResult.targetNamespace(); 
/* 1296 */       resultPartName = webResult.partName();
/* 1297 */       isResultHeader = webResult.header();
/*      */     } 
/*      */     
/* 1300 */     Class<?> returnType = method.getReturnType();
/* 1301 */     Type gReturnType = method.getGenericReturnType();
/* 1302 */     if (javaMethod.isAsync()) {
/* 1303 */       returnType = getAsyncReturnType(method, returnType);
/*      */     }
/*      */     
/* 1306 */     if (returnType != null && !returnType.getName().equals("void")) {
/* 1307 */       Annotation[] rann = getAnnotations(method);
/* 1308 */       if (resultName != null) {
/* 1309 */         QName responseQName = new QName(resultTNS, resultName);
/* 1310 */         TypeInfo rTypeReference = new TypeInfo(responseQName, returnType, rann);
/* 1311 */         rTypeReference.setGenericType(gReturnType);
/* 1312 */         this.metadataReader.getProperties(rTypeReference.properties(), method);
/* 1313 */         ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/*      */         
/* 1315 */         if (resultPartName == null || resultPartName.length() == 0) {
/* 1316 */           resultPartName = resultName;
/*      */         }
/* 1318 */         returnParameter.setPartName(resultPartName);
/* 1319 */         if (isResultHeader) {
/* 1320 */           returnParameter.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1322 */           ParameterBinding rb = getBinding(operationName, resultPartName, false, WebParam.Mode.OUT);
/* 1323 */           returnParameter.setBinding(rb);
/*      */         } 
/* 1325 */         javaMethod.addParameter(returnParameter);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1330 */     Class<?>[] parameterTypes = method.getParameterTypes();
/* 1331 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/* 1332 */     Annotation[][] pannotations = getParamAnnotations(method);
/* 1333 */     int pos = 0;
/* 1334 */     for (Class<?> clazzType : parameterTypes) {
/* 1335 */       String paramName = operationName;
/* 1336 */       String partName = null;
/* 1337 */       String requestNamespace = this.targetNamespace;
/* 1338 */       boolean isHeader = false;
/*      */ 
/*      */       
/* 1341 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */ 
/*      */ 
/*      */         
/* 1345 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/* 1347 */         if (isHolder && 
/* 1348 */           clazzType == Holder.class) {
/* 1349 */           clazzType = (Class)Utils.REFLECTION_NAVIGATOR.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/*      */         
/* 1352 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/* 1353 */         for (Annotation annotation : pannotations[pos]) {
/* 1354 */           if (annotation.annotationType() == WebParam.class) {
/* 1355 */             WebParam webParam = (WebParam)annotation;
/* 1356 */             paramMode = webParam.mode();
/* 1357 */             if (isHolder && paramMode == WebParam.Mode.IN)
/* 1358 */               paramMode = WebParam.Mode.INOUT; 
/* 1359 */             isHeader = webParam.header();
/* 1360 */             if (isHeader)
/* 1361 */               paramName = "arg" + pos; 
/* 1362 */             if (paramMode == WebParam.Mode.OUT && !isHeader)
/* 1363 */               paramName = operationName + "Response"; 
/* 1364 */             if (webParam.name().length() > 0)
/* 1365 */               paramName = webParam.name(); 
/* 1366 */             partName = webParam.partName();
/* 1367 */             if (!webParam.targetNamespace().equals("")) {
/* 1368 */               requestNamespace = webParam.targetNamespace();
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1374 */         QName requestQName = new QName(requestNamespace, paramName);
/* 1375 */         if (!isHeader && paramMode != WebParam.Mode.OUT) javaMethod.setRequestPayloadName(requestQName);
/*      */         
/* 1377 */         TypeInfo typeRef = new TypeInfo(requestQName, clazzType, pannotations[pos]);
/*      */ 
/*      */         
/* 1380 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/* 1381 */         typeRef.setGenericType(genericParameterTypes[pos]);
/* 1382 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/* 1383 */         if (partName == null || partName.length() == 0) {
/* 1384 */           partName = paramName;
/*      */         }
/* 1386 */         param.setPartName(partName);
/* 1387 */         if (paramMode == WebParam.Mode.INOUT) {
/* 1388 */           ParameterBinding pb = getBinding(operationName, partName, isHeader, WebParam.Mode.IN);
/* 1389 */           param.setInBinding(pb);
/* 1390 */           pb = getBinding(operationName, partName, isHeader, WebParam.Mode.OUT);
/* 1391 */           param.setOutBinding(pb);
/*      */         }
/* 1393 */         else if (isHeader) {
/* 1394 */           param.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1396 */           ParameterBinding pb = getBinding(operationName, partName, false, paramMode);
/* 1397 */           param.setBinding(pb);
/*      */         } 
/*      */         
/* 1400 */         javaMethod.addParameter(param);
/*      */       } 
/* 1402 */     }  validateDocBare(javaMethod);
/* 1403 */     processExceptions(javaMethod, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateDocBare(JavaMethodImpl javaMethod) {
/* 1413 */     int numInBodyBindings = 0;
/* 1414 */     for (Parameter param : javaMethod.getRequestParameters()) {
/* 1415 */       if (param.getBinding().equals(ParameterBinding.BODY) && param.isIN()) {
/* 1416 */         numInBodyBindings++;
/*      */       }
/* 1418 */       if (numInBodyBindings > 1) {
/* 1419 */         throw new RuntimeModelerException(ModelerMessages.localizableNOT_A_VALID_BARE_METHOD(this.portClass.getName(), javaMethod.getMethod().getName()));
/*      */       }
/*      */     } 
/*      */     
/* 1423 */     int numOutBodyBindings = 0;
/* 1424 */     for (Parameter param : javaMethod.getResponseParameters()) {
/* 1425 */       if (param.getBinding().equals(ParameterBinding.BODY) && param.isOUT()) {
/* 1426 */         numOutBodyBindings++;
/*      */       }
/* 1428 */       if (numOutBodyBindings > 1) {
/* 1429 */         throw new RuntimeModelerException(ModelerMessages.localizableNOT_A_VALID_BARE_METHOD(this.portClass.getName(), javaMethod.getMethod().getName()));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private Class getAsyncReturnType(Method method, Class<?> returnType) {
/* 1435 */     if (Response.class.isAssignableFrom(returnType)) {
/* 1436 */       Type ret = method.getGenericReturnType();
/* 1437 */       return (Class)Utils.REFLECTION_NAVIGATOR.erasure(((ParameterizedType)ret).getActualTypeArguments()[0]);
/*      */     } 
/* 1439 */     Type[] types = method.getGenericParameterTypes();
/* 1440 */     Class[] params = method.getParameterTypes();
/* 1441 */     int i = 0;
/* 1442 */     for (Class<?> cls : params) {
/* 1443 */       if (AsyncHandler.class.isAssignableFrom(cls)) {
/* 1444 */         return (Class)Utils.REFLECTION_NAVIGATOR.erasure(((ParameterizedType)types[i]).getActualTypeArguments()[0]);
/*      */       }
/* 1446 */       i++;
/*      */     } 
/*      */     
/* 1449 */     return returnType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String capitalize(String name) {
/* 1458 */     if (name == null || name.length() == 0) {
/* 1459 */       return name;
/*      */     }
/* 1461 */     char[] chars = name.toCharArray();
/* 1462 */     chars[0] = Character.toUpperCase(chars[0]);
/* 1463 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass) {
/* 1475 */     return getServiceName(implClass, (MetadataReader)null);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, boolean isStandard) {
/* 1479 */     return getServiceName(implClass, null, isStandard);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, MetadataReader reader) {
/* 1483 */     return getServiceName(implClass, reader, true);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, MetadataReader reader, boolean isStandard) {
/* 1487 */     if (implClass.isInterface()) {
/* 1488 */       throw new RuntimeModelerException("runtime.modeler.cannot.get.serviceName.from.interface", new Object[] { implClass
/* 1489 */             .getCanonicalName() });
/*      */     }
/*      */     
/* 1492 */     String name = implClass.getSimpleName() + "Service";
/* 1493 */     String packageName = "";
/* 1494 */     if (implClass.getPackage() != null) {
/* 1495 */       packageName = implClass.getPackage().getName();
/*      */     }
/* 1497 */     WebService webService = getAnnotation(WebService.class, implClass, reader);
/* 1498 */     if (isStandard && webService == null) {
/* 1499 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implClass
/* 1500 */             .getCanonicalName() });
/*      */     }
/* 1502 */     if (webService != null && webService.serviceName().length() > 0) {
/* 1503 */       name = webService.serviceName();
/*      */     }
/* 1505 */     String targetNamespace = getNamespace(packageName);
/* 1506 */     if (webService != null && webService.targetNamespace().length() > 0) {
/* 1507 */       targetNamespace = webService.targetNamespace();
/* 1508 */     } else if (targetNamespace == null) {
/* 1509 */       throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { implClass
/* 1510 */             .getName() });
/*      */     } 
/* 1512 */     return new QName(targetNamespace, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, String targetNamespace) {
/* 1522 */     return getPortName(implClass, (MetadataReader)null, targetNamespace);
/*      */   }
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, String targetNamespace, boolean isStandard) {
/* 1526 */     return getPortName(implClass, null, targetNamespace, isStandard);
/*      */   }
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, MetadataReader reader, String targetNamespace) {
/* 1530 */     return getPortName(implClass, reader, targetNamespace, true);
/*      */   }
/*      */   public static QName getPortName(Class<?> implClass, MetadataReader reader, String targetNamespace, boolean isStandard) {
/*      */     String name;
/* 1534 */     WebService webService = getAnnotation(WebService.class, implClass, reader);
/* 1535 */     if (isStandard && webService == null) {
/* 1536 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implClass
/* 1537 */             .getCanonicalName() });
/*      */     }
/*      */     
/* 1540 */     if (webService != null && webService.portName().length() > 0) {
/* 1541 */       name = webService.portName();
/* 1542 */     } else if (webService != null && webService.name().length() > 0) {
/* 1543 */       name = webService.name() + "Port";
/*      */     } else {
/* 1545 */       name = implClass.getSimpleName() + "Port";
/*      */     } 
/*      */     
/* 1548 */     if (targetNamespace == null) {
/* 1549 */       if (webService != null && webService.targetNamespace().length() > 0) {
/* 1550 */         targetNamespace = webService.targetNamespace();
/*      */       } else {
/* 1552 */         String packageName = null;
/* 1553 */         if (implClass.getPackage() != null) {
/* 1554 */           packageName = implClass.getPackage().getName();
/*      */         }
/* 1556 */         if (packageName != null) {
/* 1557 */           targetNamespace = getNamespace(packageName);
/*      */         }
/* 1559 */         if (targetNamespace == null) {
/* 1560 */           throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { implClass
/* 1561 */                 .getName() });
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1567 */     return new QName(targetNamespace, name);
/*      */   }
/*      */   
/*      */   static <A extends Annotation> A getAnnotation(Class<A> t, Class<?> cls, MetadataReader reader) {
/* 1571 */     return (reader == null) ? cls.<A>getAnnotation(t) : (A)reader.getAnnotation(t, cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass) {
/* 1580 */     return getPortTypeName(implOrSeiClass, null, null);
/*      */   }
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass, MetadataReader metadataReader) {
/* 1584 */     return getPortTypeName(implOrSeiClass, null, metadataReader);
/*      */   }
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass, String tns, MetadataReader reader) {
/* 1588 */     assert implOrSeiClass != null;
/* 1589 */     WebService webService = getAnnotation(WebService.class, implOrSeiClass, reader);
/* 1590 */     Class<?> clazz = implOrSeiClass;
/* 1591 */     if (webService == null) {
/* 1592 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implOrSeiClass
/* 1593 */             .getCanonicalName() });
/*      */     }
/* 1595 */     if (!implOrSeiClass.isInterface()) {
/* 1596 */       String epi = webService.endpointInterface();
/* 1597 */       if (epi.length() > 0) {
/*      */         try {
/* 1599 */           clazz = Thread.currentThread().getContextClassLoader().loadClass(epi);
/* 1600 */         } catch (ClassNotFoundException e) {
/* 1601 */           throw new RuntimeModelerException("runtime.modeler.class.not.found", new Object[] { epi });
/*      */         } 
/* 1603 */         WebService ws = getAnnotation(WebService.class, clazz, reader);
/* 1604 */         if (ws == null) {
/* 1605 */           throw new RuntimeModelerException("runtime.modeler.endpoint.interface.no.webservice", new Object[] { webService
/* 1606 */                 .endpointInterface() });
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1611 */     webService = getAnnotation(WebService.class, clazz, reader);
/* 1612 */     String name = webService.name();
/* 1613 */     if (name.length() == 0) {
/* 1614 */       name = clazz.getSimpleName();
/*      */     }
/* 1616 */     if (tns == null || "".equals(tns.trim())) tns = webService.targetNamespace(); 
/* 1617 */     if (tns.length() == 0)
/* 1618 */       tns = getNamespace(clazz.getPackage().getName()); 
/* 1619 */     if (tns == null) {
/* 1620 */       throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { clazz.getName() });
/*      */     }
/* 1622 */     return new QName(tns, name);
/*      */   }
/*      */   
/*      */   private ParameterBinding getBinding(String operation, String part, boolean isHeader, WebParam.Mode mode) {
/* 1626 */     if (this.binding == null) {
/* 1627 */       if (isHeader) {
/* 1628 */         return ParameterBinding.HEADER;
/*      */       }
/* 1630 */       return ParameterBinding.BODY;
/*      */     } 
/* 1632 */     QName opName = new QName(this.binding.getBinding().getPortType().getName().getNamespaceURI(), operation);
/* 1633 */     return this.binding.getBinding().getBinding(opName, part, mode);
/*      */   }
/*      */   
/*      */   private WSDLPart getPart(QName opName, String partName, WebParam.Mode mode) {
/* 1637 */     if (this.binding != null) {
/* 1638 */       WSDLBoundOperation bo = this.binding.getBinding().get(opName);
/* 1639 */       if (bo != null)
/* 1640 */         return bo.getPart(partName, mode); 
/*      */     } 
/* 1642 */     return null;
/*      */   }
/*      */   
/*      */   private static Boolean getBooleanSystemProperty(final String prop) {
/* 1646 */     return AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Boolean run() {
/* 1649 */             String value = System.getProperty(prop);
/* 1650 */             return (value != null) ? Boolean.valueOf(value) : Boolean.FALSE;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private static QName getReturnQName(Method method, WebResult webResult, XmlElement xmlElem) {
/* 1657 */     String webResultName = null;
/* 1658 */     if (webResult != null && webResult.name().length() > 0) {
/* 1659 */       webResultName = webResult.name();
/*      */     }
/* 1661 */     String xmlElemName = null;
/* 1662 */     if (xmlElem != null && !xmlElem.name().equals("##default")) {
/* 1663 */       xmlElemName = xmlElem.name();
/*      */     }
/* 1665 */     if (xmlElemName != null && webResultName != null && !xmlElemName.equals(webResultName)) {
/* 1666 */       throw new RuntimeModelerException("@XmlElement(name)=" + xmlElemName + " and @WebResult(name)=" + webResultName + " are different for method " + method, new Object[0]);
/*      */     }
/* 1668 */     String localPart = "return";
/* 1669 */     if (webResultName != null) {
/* 1670 */       localPart = webResultName;
/* 1671 */     } else if (xmlElemName != null) {
/* 1672 */       localPart = xmlElemName;
/*      */     } 
/*      */     
/* 1675 */     String webResultNS = null;
/* 1676 */     if (webResult != null && webResult.targetNamespace().length() > 0) {
/* 1677 */       webResultNS = webResult.targetNamespace();
/*      */     }
/* 1679 */     String xmlElemNS = null;
/* 1680 */     if (xmlElem != null && !xmlElem.namespace().equals("##default")) {
/* 1681 */       xmlElemNS = xmlElem.namespace();
/*      */     }
/* 1683 */     if (xmlElemNS != null && webResultNS != null && !xmlElemNS.equals(webResultNS)) {
/* 1684 */       throw new RuntimeModelerException("@XmlElement(namespace)=" + xmlElemNS + " and @WebResult(targetNamespace)=" + webResultNS + " are different for method " + method, new Object[0]);
/*      */     }
/* 1686 */     String ns = "";
/* 1687 */     if (webResultNS != null) {
/* 1688 */       ns = webResultNS;
/* 1689 */     } else if (xmlElemNS != null) {
/* 1690 */       ns = xmlElemNS;
/*      */     } 
/*      */     
/* 1693 */     return new QName(ns, localPart);
/*      */   }
/*      */   
/*      */   private static QName getParameterQName(Method method, WebParam webParam, XmlElement xmlElem, String paramDefault) {
/* 1697 */     String webParamName = null;
/* 1698 */     if (webParam != null && webParam.name().length() > 0) {
/* 1699 */       webParamName = webParam.name();
/*      */     }
/* 1701 */     String xmlElemName = null;
/* 1702 */     if (xmlElem != null && !xmlElem.name().equals("##default")) {
/* 1703 */       xmlElemName = xmlElem.name();
/*      */     }
/* 1705 */     if (xmlElemName != null && webParamName != null && !xmlElemName.equals(webParamName)) {
/* 1706 */       throw new RuntimeModelerException("@XmlElement(name)=" + xmlElemName + " and @WebParam(name)=" + webParamName + " are different for method " + method, new Object[0]);
/*      */     }
/* 1708 */     String localPart = paramDefault;
/* 1709 */     if (webParamName != null) {
/* 1710 */       localPart = webParamName;
/* 1711 */     } else if (xmlElemName != null) {
/* 1712 */       localPart = xmlElemName;
/*      */     } 
/*      */     
/* 1715 */     String webParamNS = null;
/* 1716 */     if (webParam != null && webParam.targetNamespace().length() > 0) {
/* 1717 */       webParamNS = webParam.targetNamespace();
/*      */     }
/* 1719 */     String xmlElemNS = null;
/* 1720 */     if (xmlElem != null && !xmlElem.namespace().equals("##default")) {
/* 1721 */       xmlElemNS = xmlElem.namespace();
/*      */     }
/* 1723 */     if (xmlElemNS != null && webParamNS != null && !xmlElemNS.equals(webParamNS)) {
/* 1724 */       throw new RuntimeModelerException("@XmlElement(namespace)=" + xmlElemNS + " and @WebParam(targetNamespace)=" + webParamNS + " are different for method " + method, new Object[0]);
/*      */     }
/* 1726 */     String ns = "";
/* 1727 */     if (webParamNS != null) {
/* 1728 */       ns = webParamNS;
/* 1729 */     } else if (xmlElemNS != null) {
/* 1730 */       ns = xmlElemNS;
/*      */     } 
/*      */     
/* 1733 */     return new QName(ns, localPart);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/RuntimeModeler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */