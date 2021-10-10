/*      */ package com.sun.xml.internal.ws.wsdl.writer;
/*      */ 
/*      */ import com.oracle.webservices.internal.api.databinding.WSDLResolver;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.Util;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ComplexType;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Element;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ExplicitGroup;
/*      */ import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalElement;
/*      */ import com.sun.xml.internal.txw2.TXW;
/*      */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*      */ import com.sun.xml.internal.txw2.output.ResultFactory;
/*      */ import com.sun.xml.internal.txw2.output.TXWResult;
/*      */ import com.sun.xml.internal.txw2.output.XmlSerializer;
/*      */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*      */ import com.sun.xml.internal.ws.api.WSBinding;
/*      */ import com.sun.xml.internal.ws.api.model.CheckedException;
/*      */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*      */ import com.sun.xml.internal.ws.api.model.MEP;
/*      */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*      */ import com.sun.xml.internal.ws.api.model.soap.SOAPBinding;
/*      */ import com.sun.xml.internal.ws.api.server.Container;
/*      */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGenExtnContext;
/*      */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*      */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*      */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*      */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*      */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*      */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*      */ import com.sun.xml.internal.ws.policy.jaxws.PolicyWSDLGeneratorExtension;
/*      */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*      */ import com.sun.xml.internal.ws.spi.db.BindingHelper;
/*      */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*      */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*      */ import com.sun.xml.internal.ws.util.RuntimeVersion;
/*      */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Binding;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.BindingOperationType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Definitions;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Fault;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.FaultType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Import;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Message;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Operation;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.ParamType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Part;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Port;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.PortType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Service;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.Types;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.BodyType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.Header;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.SOAPAddress;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.SOAPBinding;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap.SOAPFault;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.Body;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.BodyType;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.Header;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPAddress;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPBinding;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.soap12.SOAPFault;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.xsd.Import;
/*      */ import com.sun.xml.internal.ws.wsdl.writer.document.xsd.Schema;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMResult;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import org.w3c.dom.Document;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSDLGenerator
/*      */ {
/*      */   private JAXWSOutputSchemaResolver resolver;
/*  122 */   private WSDLResolver wsdlResolver = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractSEIModelImpl model;
/*      */ 
/*      */ 
/*      */   
/*      */   private Definitions serviceDefinitions;
/*      */ 
/*      */ 
/*      */   
/*      */   private Definitions portDefinitions;
/*      */ 
/*      */ 
/*      */   
/*      */   private Types types;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DOT_WSDL = ".wsdl";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RESPONSE = "Response";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PARAMETERS = "parameters";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RESULT = "parameters";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String UNWRAPPABLE_RESULT = "result";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
/*      */ 
/*      */   
/*      */   private static final String XSD_PREFIX = "xsd";
/*      */ 
/*      */   
/*      */   private static final String SOAP11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
/*      */ 
/*      */   
/*      */   private static final String SOAP12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";
/*      */ 
/*      */   
/*      */   private static final String SOAP_PREFIX = "soap";
/*      */ 
/*      */   
/*      */   private static final String SOAP12_PREFIX = "soap12";
/*      */ 
/*      */   
/*      */   private static final String TNS_PREFIX = "tns";
/*      */ 
/*      */   
/*      */   private static final String DOCUMENT = "document";
/*      */ 
/*      */   
/*      */   private static final String RPC = "rpc";
/*      */ 
/*      */   
/*      */   private static final String LITERAL = "literal";
/*      */ 
/*      */   
/*      */   private static final String REPLACE_WITH_ACTUAL_URL = "REPLACE_WITH_ACTUAL_URL";
/*      */ 
/*      */   
/*  199 */   private Set<QName> processedExceptions = new HashSet<>();
/*      */   
/*      */   private WSBinding binding;
/*      */   private String wsdlLocation;
/*      */   private String portWSDLID;
/*      */   private String schemaPrefix;
/*      */   private WSDLGeneratorExtension extension;
/*      */   List<WSDLGeneratorExtension> extensionHandlers;
/*  207 */   private String endpointAddress = "REPLACE_WITH_ACTUAL_URL";
/*      */ 
/*      */   
/*      */   private Container container;
/*      */ 
/*      */   
/*      */   private final Class implType;
/*      */ 
/*      */   
/*      */   private boolean inlineSchemas;
/*      */ 
/*      */   
/*      */   private final boolean disableXmlSecurity;
/*      */ 
/*      */ 
/*      */   
/*      */   public WSDLGenerator(AbstractSEIModelImpl model, WSDLResolver wsdlResolver, WSBinding binding, Container container, Class implType, boolean inlineSchemas, WSDLGeneratorExtension... extensions) {
/*  224 */     this(model, wsdlResolver, binding, container, implType, inlineSchemas, false, extensions);
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
/*      */   
/*      */   public WSDLGenerator(AbstractSEIModelImpl model, WSDLResolver wsdlResolver, WSBinding binding, Container container, Class implType, boolean inlineSchemas, boolean disableXmlSecurity, WSDLGeneratorExtension... extensions) {
/*  240 */     this.model = model;
/*  241 */     this.resolver = new JAXWSOutputSchemaResolver();
/*  242 */     this.wsdlResolver = wsdlResolver;
/*  243 */     this.binding = binding;
/*  244 */     this.container = container;
/*  245 */     this.implType = implType;
/*  246 */     this.extensionHandlers = new ArrayList<>();
/*  247 */     this.inlineSchemas = inlineSchemas;
/*  248 */     this.disableXmlSecurity = disableXmlSecurity;
/*      */ 
/*      */     
/*  251 */     register(new W3CAddressingWSDLGeneratorExtension());
/*  252 */     register(new W3CAddressingMetadataWSDLGeneratorExtension());
/*  253 */     register((WSDLGeneratorExtension)new PolicyWSDLGeneratorExtension());
/*      */     
/*  255 */     if (container != null) {
/*  256 */       WSDLGeneratorExtension[] wsdlGeneratorExtensions = (WSDLGeneratorExtension[])container.getSPI(WSDLGeneratorExtension[].class);
/*  257 */       if (wsdlGeneratorExtensions != null) {
/*  258 */         for (WSDLGeneratorExtension wsdlGeneratorExtension : wsdlGeneratorExtensions) {
/*  259 */           register(wsdlGeneratorExtension);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*  264 */     for (WSDLGeneratorExtension w : extensions) {
/*  265 */       register(w);
/*      */     }
/*  267 */     this.extension = new WSDLGeneratorExtensionFacade(this.extensionHandlers.<WSDLGeneratorExtension>toArray(new WSDLGeneratorExtension[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEndpointAddress(String address) {
/*  277 */     this.endpointAddress = address;
/*      */   }
/*      */   
/*      */   protected String mangleName(String name) {
/*  281 */     return BindingHelper.mangleNameToClassName(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doGeneration() {
/*  289 */     XmlSerializer portWriter = null;
/*  290 */     String fileName = mangleName(this.model.getServiceQName().getLocalPart());
/*  291 */     Result result = this.wsdlResolver.getWSDL(fileName + ".wsdl");
/*  292 */     this.wsdlLocation = result.getSystemId();
/*  293 */     XmlSerializer serviceWriter = new CommentFilter(ResultFactory.createSerializer(result));
/*  294 */     if (this.model.getServiceQName().getNamespaceURI().equals(this.model.getTargetNamespace())) {
/*  295 */       portWriter = serviceWriter;
/*  296 */       this.schemaPrefix = fileName + "_";
/*      */     } else {
/*  298 */       String wsdlName = mangleName(this.model.getPortTypeName().getLocalPart());
/*  299 */       if (wsdlName.equals(fileName))
/*  300 */         wsdlName = wsdlName + "PortType"; 
/*  301 */       Holder<String> absWSDLName = new Holder();
/*  302 */       absWSDLName.value = wsdlName + ".wsdl";
/*  303 */       result = this.wsdlResolver.getAbstractWSDL(absWSDLName);
/*      */       
/*  305 */       if (result != null) {
/*  306 */         this.portWSDLID = result.getSystemId();
/*  307 */         if (this.portWSDLID.equals(this.wsdlLocation)) {
/*  308 */           portWriter = serviceWriter;
/*      */         } else {
/*  310 */           portWriter = new CommentFilter(ResultFactory.createSerializer(result));
/*      */         } 
/*      */       } else {
/*  313 */         this.portWSDLID = (String)absWSDLName.value;
/*      */       } 
/*  315 */       this.schemaPrefix = (new File(this.portWSDLID)).getName();
/*  316 */       int idx = this.schemaPrefix.lastIndexOf('.');
/*  317 */       if (idx > 0)
/*  318 */         this.schemaPrefix = this.schemaPrefix.substring(0, idx); 
/*  319 */       this.schemaPrefix = mangleName(this.schemaPrefix) + "_";
/*      */     } 
/*  321 */     generateDocument(serviceWriter, portWriter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CommentFilter
/*      */     implements XmlSerializer
/*      */   {
/*      */     final XmlSerializer serializer;
/*      */     
/*  331 */     private static final String VERSION_COMMENT = " Generated by JAX-WS RI (http://jax-ws.java.net). RI's version is " + RuntimeVersion.VERSION + ". ";
/*      */ 
/*      */     
/*      */     CommentFilter(XmlSerializer serializer) {
/*  335 */       this.serializer = serializer;
/*      */     }
/*      */ 
/*      */     
/*      */     public void startDocument() {
/*  340 */       this.serializer.startDocument();
/*  341 */       comment(new StringBuilder(VERSION_COMMENT));
/*  342 */       text(new StringBuilder("\n"));
/*      */     }
/*      */ 
/*      */     
/*      */     public void beginStartTag(String uri, String localName, String prefix) {
/*  347 */       this.serializer.beginStartTag(uri, localName, prefix);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*  352 */       this.serializer.writeAttribute(uri, localName, prefix, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeXmlns(String prefix, String uri) {
/*  357 */       this.serializer.writeXmlns(prefix, uri);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endStartTag(String uri, String localName, String prefix) {
/*  362 */       this.serializer.endStartTag(uri, localName, prefix);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endTag() {
/*  367 */       this.serializer.endTag();
/*      */     }
/*      */ 
/*      */     
/*      */     public void text(StringBuilder text) {
/*  372 */       this.serializer.text(text);
/*      */     }
/*      */ 
/*      */     
/*      */     public void cdata(StringBuilder text) {
/*  377 */       this.serializer.cdata(text);
/*      */     }
/*      */ 
/*      */     
/*      */     public void comment(StringBuilder comment) {
/*  382 */       this.serializer.comment(comment);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endDocument() {
/*  387 */       this.serializer.endDocument();
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush() {
/*  392 */       this.serializer.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateDocument(XmlSerializer serviceStream, XmlSerializer portStream) {
/*  398 */     this.serviceDefinitions = (Definitions)TXW.create(Definitions.class, serviceStream);
/*  399 */     this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/", "");
/*  400 */     this.serviceDefinitions._namespace("http://www.w3.org/2001/XMLSchema", "xsd");
/*  401 */     this.serviceDefinitions.targetNamespace(this.model.getServiceQName().getNamespaceURI());
/*  402 */     this.serviceDefinitions._namespace(this.model.getServiceQName().getNamespaceURI(), "tns");
/*  403 */     if (this.binding.getSOAPVersion() == SOAPVersion.SOAP_12) {
/*  404 */       this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/soap12/", "soap12");
/*      */     } else {
/*  406 */       this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/soap/", "soap");
/*  407 */     }  this.serviceDefinitions.name(this.model.getServiceQName().getLocalPart());
/*  408 */     WSDLGenExtnContext serviceCtx = new WSDLGenExtnContext((TypedXmlWriter)this.serviceDefinitions, (SEIModel)this.model, this.binding, this.container, this.implType);
/*  409 */     this.extension.start(serviceCtx);
/*  410 */     if (serviceStream != portStream && portStream != null) {
/*      */       
/*  412 */       this.portDefinitions = (Definitions)TXW.create(Definitions.class, portStream);
/*  413 */       this.portDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/", "");
/*  414 */       this.portDefinitions._namespace("http://www.w3.org/2001/XMLSchema", "xsd");
/*  415 */       if (this.model.getTargetNamespace() != null) {
/*  416 */         this.portDefinitions.targetNamespace(this.model.getTargetNamespace());
/*  417 */         this.portDefinitions._namespace(this.model.getTargetNamespace(), "tns");
/*      */       } 
/*      */       
/*  420 */       String schemaLoc = relativize(this.portWSDLID, this.wsdlLocation);
/*  421 */       Import _import = this.serviceDefinitions._import().namespace(this.model.getTargetNamespace());
/*  422 */       _import.location(schemaLoc);
/*  423 */     } else if (portStream != null) {
/*      */       
/*  425 */       this.portDefinitions = this.serviceDefinitions;
/*      */     } else {
/*      */       
/*  428 */       String schemaLoc = relativize(this.portWSDLID, this.wsdlLocation);
/*  429 */       Import _import = this.serviceDefinitions._import().namespace(this.model.getTargetNamespace());
/*  430 */       _import.location(schemaLoc);
/*      */     } 
/*  432 */     this.extension.addDefinitionsExtension((TypedXmlWriter)this.serviceDefinitions);
/*      */     
/*  434 */     if (this.portDefinitions != null) {
/*  435 */       generateTypes();
/*  436 */       generateMessages();
/*  437 */       generatePortType();
/*      */     } 
/*  439 */     generateBinding();
/*  440 */     generateService();
/*      */     
/*  442 */     this.extension.end(serviceCtx);
/*  443 */     this.serviceDefinitions.commit();
/*  444 */     if (this.portDefinitions != null && this.portDefinitions != this.serviceDefinitions) {
/*  445 */       this.portDefinitions.commit();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateTypes() {
/*  453 */     this.types = this.portDefinitions.types();
/*  454 */     if (this.model.getBindingContext() != null) {
/*  455 */       if (this.inlineSchemas && this.model.getBindingContext().getClass().getName().indexOf("glassfish") == -1) {
/*  456 */         this.resolver.nonGlassfishSchemas = new ArrayList<>();
/*      */       }
/*      */       try {
/*  459 */         this.model.getBindingContext().generateSchema(this.resolver);
/*  460 */       } catch (IOException e) {
/*      */         
/*  462 */         throw new WebServiceException(e.getMessage());
/*      */       } 
/*      */     } 
/*  465 */     if (this.resolver.nonGlassfishSchemas != null) {
/*  466 */       TransformerFactory tf = XmlUtil.newTransformerFactory(!this.disableXmlSecurity);
/*      */       try {
/*  468 */         Transformer t = tf.newTransformer();
/*  469 */         for (DOMResult xsd : this.resolver.nonGlassfishSchemas) {
/*  470 */           Document doc = (Document)xsd.getNode();
/*  471 */           SAXResult sax = new SAXResult(new TXWContentHandler((TypedXmlWriter)this.types));
/*  472 */           t.transform(new DOMSource(doc.getDocumentElement()), sax);
/*      */         } 
/*  474 */       } catch (TransformerConfigurationException e) {
/*  475 */         throw new WebServiceException(e.getMessage(), e);
/*  476 */       } catch (TransformerException e) {
/*  477 */         throw new WebServiceException(e.getMessage(), e);
/*      */       } 
/*      */     } 
/*  480 */     generateWrappers();
/*      */   }
/*      */   
/*      */   void generateWrappers() {
/*  484 */     List<WrapperParameter> wrappers = new ArrayList<>();
/*  485 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  486 */       if (method.getBinding().isRpcLit())
/*  487 */         continue;  for (ParameterImpl p : method.getRequestParameters()) {
/*  488 */         if (p instanceof WrapperParameter && 
/*  489 */           WrapperComposite.class.equals((((WrapperParameter)p).getTypeInfo()).type)) {
/*  490 */           wrappers.add((WrapperParameter)p);
/*      */         }
/*      */       } 
/*      */       
/*  494 */       for (ParameterImpl p : method.getResponseParameters()) {
/*  495 */         if (p instanceof WrapperParameter && 
/*  496 */           WrapperComposite.class.equals((((WrapperParameter)p).getTypeInfo()).type)) {
/*  497 */           wrappers.add((WrapperParameter)p);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  502 */     if (wrappers.isEmpty())
/*  503 */       return;  HashMap<String, Schema> xsds = new HashMap<>();
/*  504 */     for (WrapperParameter wp : wrappers) {
/*  505 */       String tns = wp.getName().getNamespaceURI();
/*  506 */       Schema xsd = xsds.get(tns);
/*  507 */       if (xsd == null) {
/*  508 */         xsd = this.types.schema();
/*  509 */         xsd.targetNamespace(tns);
/*  510 */         xsds.put(tns, xsd);
/*      */       } 
/*  512 */       Element e = (Element)xsd._element(Element.class);
/*  513 */       e._attribute("name", wp.getName().getLocalPart());
/*  514 */       e.type(wp.getName());
/*  515 */       ComplexType ct = (ComplexType)xsd._element(ComplexType.class);
/*  516 */       ct._attribute("name", wp.getName().getLocalPart());
/*  517 */       ExplicitGroup sq = ct.sequence();
/*  518 */       for (ParameterImpl p : wp.getWrapperChildren()) {
/*  519 */         if (p.getBinding().isBody()) {
/*  520 */           LocalElement le = sq.element();
/*  521 */           le._attribute("name", p.getName().getLocalPart());
/*  522 */           TypeInfo typeInfo = p.getItemType();
/*  523 */           boolean repeatedElement = false;
/*  524 */           if (typeInfo == null) {
/*  525 */             typeInfo = p.getTypeInfo();
/*      */           } else {
/*  527 */             repeatedElement = true;
/*      */           } 
/*  529 */           QName type = this.model.getBindingContext().getTypeName(typeInfo);
/*  530 */           le.type(type);
/*  531 */           if (repeatedElement) {
/*  532 */             le.minOccurs(0);
/*  533 */             le.maxOccurs("unbounded");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateMessages() {
/*  544 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  545 */       generateSOAPMessages(method, method.getBinding());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateSOAPMessages(JavaMethodImpl method, SOAPBinding binding) {
/*  555 */     boolean isDoclit = binding.isDocLit();
/*      */     
/*  557 */     Message message = this.portDefinitions.message().name(method.getRequestMessageName());
/*  558 */     this.extension.addInputMessageExtension((TypedXmlWriter)message, (JavaMethod)method);
/*      */     
/*  560 */     BindingContext jaxbContext = this.model.getBindingContext();
/*  561 */     boolean unwrappable = true;
/*  562 */     for (ParameterImpl param : method.getRequestParameters()) {
/*  563 */       if (isDoclit) {
/*  564 */         if (isHeaderParameter(param)) {
/*  565 */           unwrappable = false;
/*      */         }
/*  567 */         Part part1 = message.part().name(param.getPartName());
/*  568 */         part1.element(param.getName()); continue;
/*      */       } 
/*  570 */       if (param.isWrapperStyle()) {
/*  571 */         for (ParameterImpl childParam : ((WrapperParameter)param).getWrapperChildren()) {
/*  572 */           Part part1 = message.part().name(childParam.getPartName());
/*  573 */           part1.type(jaxbContext.getTypeName(childParam.getXMLBridge().getTypeInfo()));
/*      */         }  continue;
/*      */       } 
/*  576 */       Part part = message.part().name(param.getPartName());
/*  577 */       part.element(param.getName());
/*      */     } 
/*      */ 
/*      */     
/*  581 */     if (method.getMEP() != MEP.ONE_WAY) {
/*  582 */       message = this.portDefinitions.message().name(method.getResponseMessageName());
/*  583 */       this.extension.addOutputMessageExtension((TypedXmlWriter)message, (JavaMethod)method);
/*      */       
/*  585 */       for (ParameterImpl param : method.getResponseParameters()) {
/*  586 */         if (isDoclit) {
/*  587 */           Part part1 = message.part().name(param.getPartName());
/*  588 */           part1.element(param.getName());
/*      */           continue;
/*      */         } 
/*  591 */         if (param.isWrapperStyle()) {
/*  592 */           for (ParameterImpl childParam : ((WrapperParameter)param).getWrapperChildren()) {
/*  593 */             Part part1 = message.part().name(childParam.getPartName());
/*  594 */             part1.type(jaxbContext.getTypeName(childParam.getXMLBridge().getTypeInfo()));
/*      */           }  continue;
/*      */         } 
/*  597 */         Part part = message.part().name(param.getPartName());
/*  598 */         part.element(param.getName());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  603 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  604 */       QName tagName = (exception.getDetailType()).tagName;
/*  605 */       String messageName = exception.getMessageName();
/*  606 */       QName messageQName = new QName(this.model.getTargetNamespace(), messageName);
/*  607 */       if (this.processedExceptions.contains(messageQName))
/*      */         continue; 
/*  609 */       message = this.portDefinitions.message().name(messageName);
/*      */       
/*  611 */       this.extension.addFaultMessageExtension((TypedXmlWriter)message, (JavaMethod)method, (CheckedException)exception);
/*  612 */       Part part = message.part().name("fault");
/*  613 */       part.element(tagName);
/*  614 */       this.processedExceptions.add(messageQName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generatePortType() {
/*  623 */     PortType portType = this.portDefinitions.portType().name(this.model.getPortTypeName().getLocalPart());
/*  624 */     this.extension.addPortTypeExtension((TypedXmlWriter)portType);
/*  625 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  626 */       Operation operation = portType.operation().name(method.getOperationName());
/*  627 */       generateParameterOrder(operation, method);
/*  628 */       this.extension.addOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  629 */       switch (method.getMEP()) {
/*      */         
/*      */         case REQUEST_RESPONSE:
/*  632 */           generateInputMessage(operation, method);
/*      */           
/*  634 */           generateOutputMessage(operation, method);
/*      */           break;
/*      */         case ONE_WAY:
/*  637 */           generateInputMessage(operation, method);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  643 */       for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  644 */         QName messageName = new QName(this.model.getTargetNamespace(), exception.getMessageName());
/*  645 */         FaultType paramType = operation.fault().message(messageName).name(exception.getMessageName());
/*  646 */         this.extension.addOperationFaultExtension((TypedXmlWriter)paramType, (JavaMethod)method, (CheckedException)exception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isWrapperStyle(JavaMethodImpl method) {
/*  657 */     if (method.getRequestParameters().size() > 0) {
/*  658 */       ParameterImpl param = method.getRequestParameters().iterator().next();
/*  659 */       return param.isWrapperStyle();
/*      */     } 
/*  661 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRpcLit(JavaMethodImpl method) {
/*  670 */     return (method.getBinding().getStyle() == SOAPBinding.Style.RPC);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateParameterOrder(Operation operation, JavaMethodImpl method) {
/*  679 */     if (method.getMEP() == MEP.ONE_WAY)
/*      */       return; 
/*  681 */     if (isRpcLit(method)) {
/*  682 */       generateRpcParameterOrder(operation, method);
/*      */     } else {
/*  684 */       generateDocumentParameterOrder(operation, method);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateRpcParameterOrder(Operation operation, JavaMethodImpl method) {
/*  694 */     StringBuilder paramOrder = new StringBuilder();
/*  695 */     Set<String> partNames = new HashSet<>();
/*  696 */     List<ParameterImpl> sortedParams = sortMethodParameters(method);
/*  697 */     int i = 0;
/*  698 */     for (ParameterImpl parameter : sortedParams) {
/*  699 */       if (parameter.getIndex() >= 0) {
/*  700 */         String partName = parameter.getPartName();
/*  701 */         if (!partNames.contains(partName)) {
/*  702 */           if (i++ > 0)
/*  703 */             paramOrder.append(' '); 
/*  704 */           paramOrder.append(partName);
/*  705 */           partNames.add(partName);
/*      */         } 
/*      */       } 
/*      */     } 
/*  709 */     if (i > 1) {
/*  710 */       operation.parameterOrder(paramOrder.toString());
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
/*      */   protected void generateDocumentParameterOrder(Operation operation, JavaMethodImpl method) {
/*  722 */     StringBuilder paramOrder = new StringBuilder();
/*  723 */     Set<String> partNames = new HashSet<>();
/*  724 */     List<ParameterImpl> sortedParams = sortMethodParameters(method);
/*      */     
/*  726 */     int i = 0;
/*  727 */     for (ParameterImpl parameter : sortedParams) {
/*      */       
/*  729 */       if (parameter.getIndex() < 0) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  734 */       String partName = parameter.getPartName();
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
/*  750 */       if (!partNames.contains(partName)) {
/*  751 */         if (i++ > 0)
/*  752 */           paramOrder.append(' '); 
/*  753 */         paramOrder.append(partName);
/*  754 */         partNames.add(partName);
/*      */       } 
/*      */     } 
/*  757 */     if (i > 1) {
/*  758 */       operation.parameterOrder(paramOrder.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<ParameterImpl> sortMethodParameters(JavaMethodImpl method) {
/*  768 */     Set<ParameterImpl> paramSet = new HashSet<>();
/*  769 */     List<ParameterImpl> sortedParams = new ArrayList<>();
/*  770 */     if (isRpcLit(method)) {
/*  771 */       for (ParameterImpl parameterImpl : method.getRequestParameters()) {
/*  772 */         if (parameterImpl instanceof WrapperParameter) {
/*  773 */           paramSet.addAll(((WrapperParameter)parameterImpl).getWrapperChildren()); continue;
/*      */         } 
/*  775 */         paramSet.add(parameterImpl);
/*      */       } 
/*      */       
/*  778 */       for (ParameterImpl parameterImpl : method.getResponseParameters()) {
/*  779 */         if (parameterImpl instanceof WrapperParameter) {
/*  780 */           paramSet.addAll(((WrapperParameter)parameterImpl).getWrapperChildren()); continue;
/*      */         } 
/*  782 */         paramSet.add(parameterImpl);
/*      */       } 
/*      */     } else {
/*      */       
/*  786 */       paramSet.addAll(method.getRequestParameters());
/*  787 */       paramSet.addAll(method.getResponseParameters());
/*      */     } 
/*  789 */     Iterator<ParameterImpl> params = paramSet.iterator();
/*  790 */     if (paramSet.isEmpty())
/*  791 */       return sortedParams; 
/*  792 */     ParameterImpl param = params.next();
/*  793 */     sortedParams.add(param);
/*      */ 
/*      */     
/*  796 */     for (int i = 1; i < paramSet.size(); i++) {
/*  797 */       param = params.next(); int pos;
/*  798 */       for (pos = 0; pos < i; pos++) {
/*  799 */         ParameterImpl sortedParam = sortedParams.get(pos);
/*  800 */         if (param.getIndex() == sortedParam.getIndex() && param instanceof WrapperParameter) {
/*      */           break;
/*      */         }
/*  803 */         if (param.getIndex() < sortedParam.getIndex()) {
/*      */           break;
/*      */         }
/*      */       } 
/*  807 */       sortedParams.add(pos, param);
/*      */     } 
/*  809 */     return sortedParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isBodyParameter(ParameterImpl parameter) {
/*  818 */     ParameterBinding paramBinding = parameter.getBinding();
/*  819 */     return paramBinding.isBody();
/*      */   }
/*      */   
/*      */   protected boolean isHeaderParameter(ParameterImpl parameter) {
/*  823 */     ParameterBinding paramBinding = parameter.getBinding();
/*  824 */     return paramBinding.isHeader();
/*      */   }
/*      */   
/*      */   protected boolean isAttachmentParameter(ParameterImpl parameter) {
/*  828 */     ParameterBinding paramBinding = parameter.getBinding();
/*  829 */     return paramBinding.isAttachment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateBinding() {
/*  837 */     Binding newBinding = this.serviceDefinitions.binding().name(this.model.getBoundPortTypeName().getLocalPart());
/*  838 */     this.extension.addBindingExtension((TypedXmlWriter)newBinding);
/*  839 */     newBinding.type(this.model.getPortTypeName());
/*  840 */     boolean first = true;
/*  841 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  842 */       if (first) {
/*  843 */         SOAPBinding sBinding = method.getBinding();
/*  844 */         SOAPVersion soapVersion = sBinding.getSOAPVersion();
/*  845 */         if (soapVersion == SOAPVersion.SOAP_12)
/*  846 */         { SOAPBinding soapBinding = newBinding.soap12Binding();
/*  847 */           soapBinding.transport(this.binding.getBindingId().getTransport());
/*  848 */           if (sBinding.getStyle().equals(SOAPBinding.Style.DOCUMENT)) {
/*  849 */             soapBinding.style("document");
/*      */           } else {
/*  851 */             soapBinding.style("rpc");
/*      */           }  }
/*  853 */         else { SOAPBinding soapBinding = newBinding.soapBinding();
/*  854 */           soapBinding.transport(this.binding.getBindingId().getTransport());
/*  855 */           if (sBinding.getStyle().equals(SOAPBinding.Style.DOCUMENT)) {
/*  856 */             soapBinding.style("document");
/*      */           } else {
/*  858 */             soapBinding.style("rpc");
/*      */           }  }
/*  860 */          first = false;
/*      */       } 
/*  862 */       if (this.binding.getBindingId().getSOAPVersion() == SOAPVersion.SOAP_12) {
/*  863 */         generateSOAP12BindingOperation(method, newBinding); continue;
/*      */       } 
/*  865 */       generateBindingOperation(method, newBinding);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateBindingOperation(JavaMethodImpl method, Binding binding) {
/*  870 */     BindingOperationType operation = binding.operation().name(method.getOperationName());
/*  871 */     this.extension.addBindingOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  872 */     String targetNamespace = this.model.getTargetNamespace();
/*  873 */     QName requestMessage = new QName(targetNamespace, method.getOperationName());
/*  874 */     List<ParameterImpl> bodyParams = new ArrayList<>();
/*  875 */     List<ParameterImpl> headerParams = new ArrayList<>();
/*  876 */     splitParameters(bodyParams, headerParams, method.getRequestParameters());
/*  877 */     SOAPBinding soapBinding = method.getBinding();
/*  878 */     operation.soapOperation().soapAction(soapBinding.getSOAPAction());
/*      */ 
/*      */     
/*  881 */     StartWithExtensionsType startWithExtensionsType = operation.input();
/*  882 */     this.extension.addBindingOperationInputExtension((TypedXmlWriter)startWithExtensionsType, (JavaMethod)method);
/*  883 */     BodyType body = (BodyType)startWithExtensionsType._element(Body.class);
/*  884 */     boolean isRpc = soapBinding.getStyle().equals(SOAPBinding.Style.RPC);
/*  885 */     if (soapBinding.getUse() == SOAPBinding.Use.LITERAL) {
/*  886 */       body.use("literal");
/*  887 */       if (headerParams.size() > 0) {
/*  888 */         if (bodyParams.size() > 0) {
/*  889 */           ParameterImpl param = bodyParams.iterator().next();
/*  890 */           if (isRpc) {
/*  891 */             StringBuilder parts = new StringBuilder();
/*  892 */             int i = 0;
/*  893 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  894 */               if (i++ > 0)
/*  895 */                 parts.append(' '); 
/*  896 */               parts.append(parameter.getPartName());
/*      */             } 
/*  898 */             body.parts(parts.toString());
/*      */           } else {
/*  900 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/*  903 */           body.parts("");
/*      */         } 
/*  905 */         generateSOAPHeaders((TypedXmlWriter)startWithExtensionsType, headerParams, requestMessage);
/*      */       } 
/*  907 */       if (isRpc) {
/*  908 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } else {
/*      */       
/*  912 */       throw new WebServiceException("encoded use is not supported");
/*      */     } 
/*      */     
/*  915 */     if (method.getMEP() != MEP.ONE_WAY) {
/*      */       
/*  917 */       bodyParams.clear();
/*  918 */       headerParams.clear();
/*  919 */       splitParameters(bodyParams, headerParams, method.getResponseParameters());
/*  920 */       StartWithExtensionsType startWithExtensionsType1 = operation.output();
/*  921 */       this.extension.addBindingOperationOutputExtension((TypedXmlWriter)startWithExtensionsType1, (JavaMethod)method);
/*  922 */       body = (BodyType)startWithExtensionsType1._element(Body.class);
/*  923 */       body.use("literal");
/*  924 */       if (headerParams.size() > 0) {
/*  925 */         StringBuilder parts = new StringBuilder();
/*  926 */         if (bodyParams.size() > 0) {
/*  927 */           ParameterImpl param = bodyParams.iterator().hasNext() ? bodyParams.iterator().next() : null;
/*  928 */           if (param != null) {
/*  929 */             if (isRpc) {
/*  930 */               int i = 0;
/*  931 */               for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  932 */                 if (i++ > 0) {
/*  933 */                   parts.append(" ");
/*      */                 }
/*  935 */                 parts.append(parameter.getPartName());
/*      */               } 
/*      */             } else {
/*  938 */               parts = new StringBuilder(param.getPartName());
/*      */             } 
/*      */           }
/*      */         } 
/*  942 */         body.parts(parts.toString());
/*  943 */         QName responseMessage = new QName(targetNamespace, method.getResponseMessageName());
/*  944 */         generateSOAPHeaders((TypedXmlWriter)startWithExtensionsType1, headerParams, responseMessage);
/*      */       } 
/*  946 */       if (isRpc) {
/*  947 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } 
/*  950 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  951 */       Fault fault = operation.fault().name(exception.getMessageName());
/*  952 */       this.extension.addBindingOperationFaultExtension((TypedXmlWriter)fault, (JavaMethod)method, (CheckedException)exception);
/*  953 */       SOAPFault soapFault = ((SOAPFault)fault._element(SOAPFault.class)).name(exception.getMessageName());
/*  954 */       soapFault.use("literal");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateSOAP12BindingOperation(JavaMethodImpl method, Binding binding) {
/*  959 */     BindingOperationType operation = binding.operation().name(method.getOperationName());
/*  960 */     this.extension.addBindingOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  961 */     String targetNamespace = this.model.getTargetNamespace();
/*  962 */     QName requestMessage = new QName(targetNamespace, method.getOperationName());
/*  963 */     ArrayList<ParameterImpl> bodyParams = new ArrayList<>();
/*  964 */     ArrayList<ParameterImpl> headerParams = new ArrayList<>();
/*  965 */     splitParameters(bodyParams, headerParams, method.getRequestParameters());
/*  966 */     SOAPBinding soapBinding = method.getBinding();
/*      */     
/*  968 */     String soapAction = soapBinding.getSOAPAction();
/*  969 */     if (soapAction != null) {
/*  970 */       operation.soap12Operation().soapAction(soapAction);
/*      */     }
/*      */ 
/*      */     
/*  974 */     StartWithExtensionsType startWithExtensionsType = operation.input();
/*  975 */     this.extension.addBindingOperationInputExtension((TypedXmlWriter)startWithExtensionsType, (JavaMethod)method);
/*  976 */     BodyType body = (BodyType)startWithExtensionsType._element(Body.class);
/*  977 */     boolean isRpc = soapBinding.getStyle().equals(SOAPBinding.Style.RPC);
/*  978 */     if (soapBinding.getUse().equals(SOAPBinding.Use.LITERAL)) {
/*  979 */       body.use("literal");
/*  980 */       if (headerParams.size() > 0) {
/*  981 */         if (bodyParams.size() > 0) {
/*  982 */           ParameterImpl param = bodyParams.iterator().next();
/*  983 */           if (isRpc) {
/*  984 */             StringBuilder parts = new StringBuilder();
/*  985 */             int i = 0;
/*  986 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  987 */               if (i++ > 0)
/*  988 */                 parts.append(' '); 
/*  989 */               parts.append(parameter.getPartName());
/*      */             } 
/*  991 */             body.parts(parts.toString());
/*      */           } else {
/*  993 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/*  996 */           body.parts("");
/*      */         } 
/*  998 */         generateSOAP12Headers((TypedXmlWriter)startWithExtensionsType, headerParams, requestMessage);
/*      */       } 
/* 1000 */       if (isRpc) {
/* 1001 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } else {
/*      */       
/* 1005 */       throw new WebServiceException("encoded use is not supported");
/*      */     } 
/*      */     
/* 1008 */     if (method.getMEP() != MEP.ONE_WAY) {
/*      */       
/* 1010 */       bodyParams.clear();
/* 1011 */       headerParams.clear();
/* 1012 */       splitParameters(bodyParams, headerParams, method.getResponseParameters());
/* 1013 */       StartWithExtensionsType startWithExtensionsType1 = operation.output();
/* 1014 */       this.extension.addBindingOperationOutputExtension((TypedXmlWriter)startWithExtensionsType1, (JavaMethod)method);
/* 1015 */       body = (BodyType)startWithExtensionsType1._element(Body.class);
/* 1016 */       body.use("literal");
/* 1017 */       if (headerParams.size() > 0) {
/* 1018 */         if (bodyParams.size() > 0) {
/* 1019 */           ParameterImpl param = bodyParams.iterator().next();
/* 1020 */           if (isRpc) {
/* 1021 */             StringBuilder parts = new StringBuilder();
/* 1022 */             int i = 0;
/* 1023 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/* 1024 */               if (i++ > 0) {
/* 1025 */                 parts.append(" ");
/*      */               }
/* 1027 */               parts.append(parameter.getPartName());
/*      */             } 
/* 1029 */             body.parts(parts.toString());
/*      */           } else {
/* 1031 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/* 1034 */           body.parts("");
/*      */         } 
/* 1036 */         QName responseMessage = new QName(targetNamespace, method.getResponseMessageName());
/* 1037 */         generateSOAP12Headers((TypedXmlWriter)startWithExtensionsType1, headerParams, responseMessage);
/*      */       } 
/* 1039 */       if (isRpc) {
/* 1040 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } 
/* 1043 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/* 1044 */       Fault fault = operation.fault().name(exception.getMessageName());
/* 1045 */       this.extension.addBindingOperationFaultExtension((TypedXmlWriter)fault, (JavaMethod)method, (CheckedException)exception);
/* 1046 */       SOAPFault soapFault = ((SOAPFault)fault._element(SOAPFault.class)).name(exception.getMessageName());
/* 1047 */       soapFault.use("literal");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void splitParameters(List<ParameterImpl> bodyParams, List<ParameterImpl> headerParams, List<ParameterImpl> params) {
/* 1052 */     for (ParameterImpl parameter : params) {
/* 1053 */       if (isBodyParameter(parameter)) {
/* 1054 */         bodyParams.add(parameter); continue;
/*      */       } 
/* 1056 */       headerParams.add(parameter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateSOAPHeaders(TypedXmlWriter writer, List<ParameterImpl> parameters, QName message) {
/* 1063 */     for (ParameterImpl headerParam : parameters) {
/* 1064 */       Header header = (Header)writer._element(Header.class);
/* 1065 */       header.message(message);
/* 1066 */       header.part(headerParam.getPartName());
/* 1067 */       header.use("literal");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void generateSOAP12Headers(TypedXmlWriter writer, List<ParameterImpl> parameters, QName message) {
/* 1073 */     for (ParameterImpl headerParam : parameters) {
/* 1074 */       Header header = (Header)writer._element(Header.class);
/* 1075 */       header.message(message);
/*      */ 
/*      */       
/* 1078 */       header.part(headerParam.getPartName());
/* 1079 */       header.use("literal");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateService() {
/* 1087 */     QName portQName = this.model.getPortName();
/* 1088 */     QName serviceQName = this.model.getServiceQName();
/* 1089 */     Service service = this.serviceDefinitions.service().name(serviceQName.getLocalPart());
/* 1090 */     this.extension.addServiceExtension((TypedXmlWriter)service);
/* 1091 */     Port port = service.port().name(portQName.getLocalPart());
/* 1092 */     port.binding(this.model.getBoundPortTypeName());
/* 1093 */     this.extension.addPortExtension((TypedXmlWriter)port);
/* 1094 */     if (this.model.getJavaMethods().isEmpty()) {
/*      */       return;
/*      */     }
/* 1097 */     if (this.binding.getBindingId().getSOAPVersion() == SOAPVersion.SOAP_12) {
/* 1098 */       SOAPAddress address = (SOAPAddress)port._element(SOAPAddress.class);
/* 1099 */       address.location(this.endpointAddress);
/*      */     } else {
/* 1101 */       SOAPAddress address = (SOAPAddress)port._element(SOAPAddress.class);
/* 1102 */       address.location(this.endpointAddress);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateInputMessage(Operation operation, JavaMethodImpl method) {
/* 1107 */     ParamType paramType = operation.input();
/* 1108 */     this.extension.addOperationInputExtension((TypedXmlWriter)paramType, (JavaMethod)method);
/*      */     
/* 1110 */     paramType.message(new QName(this.model.getTargetNamespace(), method.getRequestMessageName()));
/*      */   }
/*      */   
/*      */   protected void generateOutputMessage(Operation operation, JavaMethodImpl method) {
/* 1114 */     ParamType paramType = operation.output();
/* 1115 */     this.extension.addOperationOutputExtension((TypedXmlWriter)paramType, (JavaMethod)method);
/*      */     
/* 1117 */     paramType.message(new QName(this.model.getTargetNamespace(), method.getResponseMessageName()));
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
/*      */   public Result createOutputFile(String namespaceUri, String suggestedFileName) throws IOException {
/*      */     String schemaLoc;
/* 1130 */     if (namespaceUri == null) {
/* 1131 */       return null;
/*      */     }
/*      */     
/* 1134 */     Holder<String> fileNameHolder = new Holder();
/* 1135 */     fileNameHolder.value = this.schemaPrefix + suggestedFileName;
/* 1136 */     Result result = this.wsdlResolver.getSchemaOutput(namespaceUri, fileNameHolder);
/*      */ 
/*      */ 
/*      */     
/* 1140 */     if (result == null) {
/* 1141 */       schemaLoc = (String)fileNameHolder.value;
/*      */     } else {
/* 1143 */       schemaLoc = relativize(result.getSystemId(), this.wsdlLocation);
/* 1144 */     }  boolean isEmptyNs = namespaceUri.trim().equals("");
/* 1145 */     if (!isEmptyNs) {
/* 1146 */       Import _import = this.types.schema()._import();
/* 1147 */       _import.namespace(namespaceUri);
/* 1148 */       _import.schemaLocation(schemaLoc);
/*      */     } 
/* 1150 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private Result createInlineSchema(String namespaceUri, String suggestedFileName) throws IOException {
/* 1155 */     if (namespaceUri.equals("")) {
/* 1156 */       return null;
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
/* 1168 */     TXWResult tXWResult = new TXWResult((TypedXmlWriter)this.types);
/* 1169 */     tXWResult.setSystemId("");
/*      */     
/* 1171 */     return (Result)tXWResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String relativize(String uri, String baseUri) {
/*      */     try {
/* 1195 */       assert uri != null;
/*      */       
/* 1197 */       if (baseUri == null) return uri;
/*      */       
/* 1199 */       URI theUri = new URI(Util.escapeURI(uri));
/* 1200 */       URI theBaseUri = new URI(Util.escapeURI(baseUri));
/*      */       
/* 1202 */       if (theUri.isOpaque() || theBaseUri.isOpaque()) {
/* 1203 */         return uri;
/*      */       }
/* 1205 */       if (!Util.equalsIgnoreCase(theUri.getScheme(), theBaseUri.getScheme()) || 
/* 1206 */         !Util.equal(theUri.getAuthority(), theBaseUri.getAuthority())) {
/* 1207 */         return uri;
/*      */       }
/* 1209 */       String uriPath = theUri.getPath();
/* 1210 */       String basePath = theBaseUri.getPath();
/*      */ 
/*      */       
/* 1213 */       if (!basePath.endsWith("/")) {
/* 1214 */         basePath = Util.normalizeUriPath(basePath);
/*      */       }
/*      */       
/* 1217 */       if (uriPath.equals(basePath)) {
/* 1218 */         return ".";
/*      */       }
/* 1220 */       String relPath = calculateRelativePath(uriPath, basePath);
/*      */       
/* 1222 */       if (relPath == null)
/* 1223 */         return uri; 
/* 1224 */       StringBuilder relUri = new StringBuilder();
/* 1225 */       relUri.append(relPath);
/* 1226 */       if (theUri.getQuery() != null)
/* 1227 */         relUri.append('?').append(theUri.getQuery()); 
/* 1228 */       if (theUri.getFragment() != null) {
/* 1229 */         relUri.append('#').append(theUri.getFragment());
/*      */       }
/* 1231 */       return relUri.toString();
/* 1232 */     } catch (URISyntaxException e) {
/* 1233 */       throw new InternalError("Error escaping one of these uris:\n\t" + uri + "\n\t" + baseUri);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String calculateRelativePath(String uri, String base) {
/* 1238 */     if (base == null) {
/* 1239 */       return null;
/*      */     }
/* 1241 */     if (uri.startsWith(base)) {
/* 1242 */       return uri.substring(base.length());
/*      */     }
/* 1244 */     return "../" + calculateRelativePath(uri, Util.getParentUriPath(base));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class JAXWSOutputSchemaResolver
/*      */     extends SchemaOutputResolver
/*      */   {
/* 1253 */     ArrayList<DOMResult> nonGlassfishSchemas = null;
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
/*      */     public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
/* 1265 */       return WSDLGenerator.this.inlineSchemas ? ((this.nonGlassfishSchemas != null) ? 
/* 1266 */         nonGlassfishSchemaResult(namespaceUri, suggestedFileName) : WSDLGenerator.this.createInlineSchema(namespaceUri, suggestedFileName)) : WSDLGenerator.this
/*      */         
/* 1268 */         .createOutputFile(namespaceUri, suggestedFileName);
/*      */     }
/*      */     
/*      */     private Result nonGlassfishSchemaResult(String namespaceUri, String suggestedFileName) throws IOException {
/* 1272 */       DOMResult result = new DOMResult();
/* 1273 */       result.setSystemId("");
/* 1274 */       this.nonGlassfishSchemas.add(result);
/* 1275 */       return result;
/*      */     }
/*      */   }
/*      */   
/*      */   private void register(WSDLGeneratorExtension h) {
/* 1280 */     this.extensionHandlers.add(h);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/WSDLGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */