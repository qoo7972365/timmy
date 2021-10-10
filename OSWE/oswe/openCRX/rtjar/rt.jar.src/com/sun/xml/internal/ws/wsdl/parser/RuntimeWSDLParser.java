/*      */ package com.sun.xml.internal.ws.wsdl.parser;
/*      */ 
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.xml.internal.stream.buffer.AbstractCreatorProcessor;
/*      */ import com.sun.xml.internal.stream.buffer.MutableXMLStreamBuffer;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferMark;
/*      */ import com.sun.xml.internal.stream.buffer.stax.StreamReaderBufferCreator;
/*      */ import com.sun.xml.internal.ws.api.BindingID;
/*      */ import com.sun.xml.internal.ws.api.BindingIDFactory;
/*      */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*      */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*      */ import com.sun.xml.internal.ws.api.WSDLLocator;
/*      */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*      */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLDescriptorKind;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPartDescriptor;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPart;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*      */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*      */ import com.sun.xml.internal.ws.api.policy.PolicyResolverFactory;
/*      */ import com.sun.xml.internal.ws.api.server.Container;
/*      */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*      */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*      */ import com.sun.xml.internal.ws.api.wsdl.parser.MetaDataResolver;
/*      */ import com.sun.xml.internal.ws.api.wsdl.parser.MetadataResolverFactory;
/*      */ import com.sun.xml.internal.ws.api.wsdl.parser.ServiceDescriptor;
/*      */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*      */ import com.sun.xml.internal.ws.api.wsdl.parser.XMLEntityResolver;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLBoundFaultImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLBoundOperationImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLFaultImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLInputImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLMessageImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLModelImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLOperationImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLOutputImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLPartDescriptorImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLPartImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLPortImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLPortTypeImpl;
/*      */ import com.sun.xml.internal.ws.model.wsdl.WSDLServiceImpl;
/*      */ import com.sun.xml.internal.ws.policy.jaxws.PolicyWSDLParserExtension;
/*      */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*      */ import com.sun.xml.internal.ws.resources.WsdlmodelMessages;
/*      */ import com.sun.xml.internal.ws.streaming.SourceReaderFactory;
/*      */ import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;
/*      */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*      */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*      */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Logger;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import javax.xml.ws.Service;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.SAXException;
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
/*      */ public class RuntimeWSDLParser
/*      */ {
/*      */   private final EditableWSDLModel wsdlDoc;
/*      */   private String targetNamespace;
/*  113 */   private final Set<String> importedWSDLs = new HashSet<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private final XMLEntityResolver resolver;
/*      */ 
/*      */   
/*      */   private final PolicyResolver policyResolver;
/*      */ 
/*      */   
/*      */   private final WSDLParserExtension extensionFacade;
/*      */ 
/*      */   
/*      */   private final WSDLParserExtensionContextImpl context;
/*      */ 
/*      */   
/*      */   List<WSDLParserExtension> extensions;
/*      */ 
/*      */   
/*  132 */   Map<String, String> wsdldef_nsdecl = new HashMap<>();
/*  133 */   Map<String, String> service_nsdecl = new HashMap<>();
/*  134 */   Map<String, String> port_nsdecl = new HashMap<>();
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
/*      */   public static WSDLModel parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  148 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, Service.class, PolicyResolverFactory.create(), extensions);
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
/*      */   public static WSDLModel parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  163 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, serviceClass, PolicyResolverFactory.create(), extensions);
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
/*      */   public static WSDLModel parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, @NotNull PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  178 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, Service.class, policyResolver, extensions);
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
/*      */   public static WSDLModel parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, @NotNull PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  194 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, serviceClass, policyResolver, false, extensions);
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
/*      */   public static WSDLModel parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, @NotNull PolicyResolver policyResolver, boolean isUseStreamFromEntityResolverWrapper, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*      */     XMLEntityResolver.Parser parser;
/*  211 */     assert resolver != null;
/*      */     
/*  213 */     RuntimeWSDLParser wsdlParser = new RuntimeWSDLParser(wsdlSource.getSystemId(), new EntityResolverWrapper(resolver, isUseStreamFromEntityResolverWrapper), isClientSide, container, policyResolver, extensions);
/*      */     
/*      */     try {
/*  216 */       parser = wsdlParser.resolveWSDL(wsdlLoc, wsdlSource, serviceClass);
/*  217 */       if (!hasWSDLDefinitions(parser.parser)) {
/*  218 */         throw new XMLStreamException(ClientMessages.RUNTIME_WSDLPARSER_INVALID_WSDL(parser.systemId, WSDLConstants.QNAME_DEFINITIONS, parser.parser
/*  219 */               .getName(), parser.parser.getLocation()));
/*      */       }
/*  221 */     } catch (XMLStreamException e) {
/*      */       
/*  223 */       if (wsdlLoc == null)
/*  224 */         throw e; 
/*  225 */       return tryWithMex(wsdlParser, wsdlLoc, resolver, isClientSide, container, e, serviceClass, policyResolver, extensions);
/*      */     }
/*  227 */     catch (IOException e) {
/*      */       
/*  229 */       if (wsdlLoc == null)
/*  230 */         throw e; 
/*  231 */       return tryWithMex(wsdlParser, wsdlLoc, resolver, isClientSide, container, e, serviceClass, policyResolver, extensions);
/*      */     } 
/*  233 */     wsdlParser.extensionFacade.start(wsdlParser.context);
/*  234 */     wsdlParser.parseWSDL(parser, false);
/*  235 */     wsdlParser.wsdlDoc.freeze();
/*  236 */     wsdlParser.extensionFacade.finished(wsdlParser.context);
/*  237 */     wsdlParser.extensionFacade.postFinished(wsdlParser.context);
/*      */     
/*  239 */     if (wsdlParser.wsdlDoc.getServices().isEmpty()) {
/*  240 */       throw new WebServiceException(ClientMessages.WSDL_CONTAINS_NO_SERVICE(wsdlLoc));
/*      */     }
/*  242 */     return (WSDLModel)wsdlParser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private static WSDLModel tryWithMex(@NotNull RuntimeWSDLParser wsdlParser, @NotNull URL wsdlLoc, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Throwable e, Class serviceClass, PolicyResolver policyResolver, WSDLParserExtension... extensions) throws SAXException, XMLStreamException {
/*  246 */     ArrayList<Throwable> exceptions = new ArrayList<>();
/*      */     try {
/*  248 */       WSDLModel wsdlModel = wsdlParser.parseUsingMex(wsdlLoc, resolver, isClientSide, container, serviceClass, policyResolver, extensions);
/*  249 */       if (wsdlModel == null) {
/*  250 */         throw new WebServiceException(ClientMessages.FAILED_TO_PARSE(wsdlLoc.toExternalForm(), e.getMessage()), e);
/*      */       }
/*  252 */       return wsdlModel;
/*  253 */     } catch (URISyntaxException e1) {
/*  254 */       exceptions.add(e);
/*  255 */       exceptions.add(e1);
/*  256 */     } catch (IOException e1) {
/*  257 */       exceptions.add(e);
/*  258 */       exceptions.add(e1);
/*      */     } 
/*  260 */     throw new InaccessibleWSDLException(exceptions);
/*      */   }
/*      */ 
/*      */   
/*      */   private WSDLModel parseUsingMex(@NotNull URL wsdlLoc, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, PolicyResolver policyResolver, WSDLParserExtension[] extensions) throws IOException, SAXException, XMLStreamException, URISyntaxException {
/*  265 */     MetaDataResolver mdResolver = null;
/*  266 */     ServiceDescriptor serviceDescriptor = null;
/*  267 */     RuntimeWSDLParser wsdlParser = null;
/*      */ 
/*      */     
/*  270 */     for (MetadataResolverFactory resolverFactory : ServiceFinder.find(MetadataResolverFactory.class)) {
/*  271 */       mdResolver = resolverFactory.metadataResolver(resolver);
/*  272 */       serviceDescriptor = mdResolver.resolve(wsdlLoc.toURI());
/*      */       
/*  274 */       if (serviceDescriptor != null)
/*      */         break; 
/*      */     } 
/*  277 */     if (serviceDescriptor != null) {
/*  278 */       List<? extends Source> wsdls = serviceDescriptor.getWSDLs();
/*  279 */       wsdlParser = new RuntimeWSDLParser(wsdlLoc.toExternalForm(), new MexEntityResolver(wsdls), isClientSide, container, policyResolver, extensions);
/*  280 */       wsdlParser.extensionFacade.start(wsdlParser.context);
/*      */       
/*  282 */       for (Source src : wsdls) {
/*  283 */         String systemId = src.getSystemId();
/*  284 */         XMLEntityResolver.Parser parser = wsdlParser.resolver.resolveEntity(null, systemId);
/*  285 */         wsdlParser.parseWSDL(parser, false);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  290 */     if ((mdResolver == null || serviceDescriptor == null) && (wsdlLoc.getProtocol().equals("http") || wsdlLoc.getProtocol().equals("https")) && wsdlLoc.getQuery() == null) {
/*  291 */       String urlString = wsdlLoc.toExternalForm();
/*  292 */       urlString = urlString + "?wsdl";
/*  293 */       wsdlLoc = new URL(urlString);
/*  294 */       wsdlParser = new RuntimeWSDLParser(wsdlLoc.toExternalForm(), new EntityResolverWrapper(resolver), isClientSide, container, policyResolver, extensions);
/*  295 */       wsdlParser.extensionFacade.start(wsdlParser.context);
/*  296 */       XMLEntityResolver.Parser parser = resolveWSDL(wsdlLoc, new StreamSource(wsdlLoc.toExternalForm()), serviceClass);
/*  297 */       wsdlParser.parseWSDL(parser, false);
/*      */     } 
/*      */     
/*  300 */     if (wsdlParser == null) {
/*  301 */       return null;
/*      */     }
/*  303 */     wsdlParser.wsdlDoc.freeze();
/*  304 */     wsdlParser.extensionFacade.finished(wsdlParser.context);
/*  305 */     wsdlParser.extensionFacade.postFinished(wsdlParser.context);
/*  306 */     return (WSDLModel)wsdlParser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private static boolean hasWSDLDefinitions(XMLStreamReader reader) {
/*  310 */     XMLStreamReaderUtil.nextElementContent(reader);
/*  311 */     return reader.getName().equals(WSDLConstants.QNAME_DEFINITIONS);
/*      */   }
/*      */   
/*      */   public static WSDLModel parse(XMLEntityResolver.Parser wsdl, XMLEntityResolver resolver, boolean isClientSide, Container container, PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  315 */     assert resolver != null;
/*  316 */     RuntimeWSDLParser parser = new RuntimeWSDLParser(wsdl.systemId.toExternalForm(), resolver, isClientSide, container, policyResolver, extensions);
/*  317 */     parser.extensionFacade.start(parser.context);
/*  318 */     parser.parseWSDL(wsdl, false);
/*  319 */     parser.wsdlDoc.freeze();
/*  320 */     parser.extensionFacade.finished(parser.context);
/*  321 */     parser.extensionFacade.postFinished(parser.context);
/*  322 */     return (WSDLModel)parser.wsdlDoc;
/*      */   }
/*      */   
/*      */   public static WSDLModel parse(XMLEntityResolver.Parser wsdl, XMLEntityResolver resolver, boolean isClientSide, Container container, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  326 */     assert resolver != null;
/*  327 */     RuntimeWSDLParser parser = new RuntimeWSDLParser(wsdl.systemId.toExternalForm(), resolver, isClientSide, container, PolicyResolverFactory.create(), extensions);
/*  328 */     parser.extensionFacade.start(parser.context);
/*  329 */     parser.parseWSDL(wsdl, false);
/*  330 */     parser.wsdlDoc.freeze();
/*  331 */     parser.extensionFacade.finished(parser.context);
/*  332 */     parser.extensionFacade.postFinished(parser.context);
/*  333 */     return (WSDLModel)parser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private RuntimeWSDLParser(@NotNull String sourceLocation, XMLEntityResolver resolver, boolean isClientSide, Container container, PolicyResolver policyResolver, WSDLParserExtension... extensions) {
/*  337 */     this.wsdlDoc = (sourceLocation != null) ? (EditableWSDLModel)new WSDLModelImpl(sourceLocation) : (EditableWSDLModel)new WSDLModelImpl();
/*  338 */     this.resolver = resolver;
/*  339 */     this.policyResolver = policyResolver;
/*  340 */     this.extensions = new ArrayList<>();
/*  341 */     this.context = new WSDLParserExtensionContextImpl(this.wsdlDoc, isClientSide, container, policyResolver);
/*      */     
/*  343 */     boolean isPolicyExtensionFound = false;
/*  344 */     for (WSDLParserExtension e : extensions) {
/*  345 */       if (e instanceof com.sun.xml.internal.ws.api.wsdl.parser.PolicyWSDLParserExtension)
/*  346 */         isPolicyExtensionFound = true; 
/*  347 */       register(e);
/*      */     } 
/*      */ 
/*      */     
/*  351 */     if (!isPolicyExtensionFound)
/*  352 */       register((WSDLParserExtension)new PolicyWSDLParserExtension()); 
/*  353 */     register(new MemberSubmissionAddressingWSDLParserExtension());
/*  354 */     register(new W3CAddressingWSDLParserExtension());
/*  355 */     register(new W3CAddressingMetadataWSDLParserExtension());
/*      */     
/*  357 */     this.extensionFacade = new WSDLParserExtensionFacade(this.extensions.<WSDLParserExtension>toArray(new WSDLParserExtension[0]));
/*      */   }
/*      */   
/*      */   private XMLEntityResolver.Parser resolveWSDL(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, Class<Service> serviceClass) throws IOException, SAXException, XMLStreamException {
/*  361 */     String systemId = wsdlSource.getSystemId();
/*      */     
/*  363 */     XMLEntityResolver.Parser parser = this.resolver.resolveEntity(null, systemId);
/*  364 */     if (parser == null && wsdlLoc != null) {
/*  365 */       String exForm = wsdlLoc.toExternalForm();
/*  366 */       parser = this.resolver.resolveEntity(null, exForm);
/*      */       
/*  368 */       if (parser == null && serviceClass != null) {
/*  369 */         URL ru = serviceClass.getResource(".");
/*  370 */         if (ru != null) {
/*  371 */           String ruExForm = ru.toExternalForm();
/*  372 */           if (exForm.startsWith(ruExForm)) {
/*  373 */             parser = this.resolver.resolveEntity(null, exForm.substring(ruExForm.length()));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  378 */     if (parser == null) {
/*      */ 
/*      */ 
/*      */       
/*  382 */       if (isKnownReadableSource(wsdlSource)) {
/*  383 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlSource));
/*  384 */       } else if (wsdlLoc != null) {
/*  385 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlLoc, serviceClass));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  391 */       if (parser == null) {
/*  392 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlSource));
/*      */       }
/*      */     } 
/*  395 */     return parser;
/*      */   }
/*      */   
/*      */   private boolean isKnownReadableSource(Source wsdlSource) {
/*  399 */     if (wsdlSource instanceof StreamSource) {
/*  400 */       return (((StreamSource)wsdlSource).getInputStream() != null || ((StreamSource)wsdlSource)
/*  401 */         .getReader() != null);
/*      */     }
/*  403 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private XMLStreamReader createReader(@NotNull Source src) throws XMLStreamException {
/*  408 */     return (XMLStreamReader)new TidyXMLStreamReader(SourceReaderFactory.createSourceReader(src, true), null);
/*      */   }
/*      */   
/*      */   private void parseImport(@NotNull URL wsdlLoc) throws XMLStreamException, IOException, SAXException {
/*  412 */     String systemId = wsdlLoc.toExternalForm();
/*  413 */     XMLEntityResolver.Parser parser = this.resolver.resolveEntity(null, systemId);
/*  414 */     if (parser == null) {
/*  415 */       parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlLoc));
/*      */     }
/*  417 */     parseWSDL(parser, true);
/*      */   }
/*      */   
/*      */   private void parseWSDL(XMLEntityResolver.Parser parser, boolean imported) throws XMLStreamException, IOException, SAXException {
/*  421 */     XMLStreamReader reader = parser.parser;
/*      */ 
/*      */     
/*      */     try {
/*  425 */       if (parser.systemId != null && !this.importedWSDLs.add(parser.systemId.toExternalForm())) {
/*      */         return;
/*      */       }
/*  428 */       if (reader.getEventType() == 7)
/*  429 */         XMLStreamReaderUtil.nextElementContent(reader); 
/*  430 */       if (WSDLConstants.QNAME_DEFINITIONS.equals(reader.getName())) {
/*  431 */         readNSDecl(this.wsdldef_nsdecl, reader);
/*      */       }
/*  433 */       if (reader.getEventType() != 8 && reader.getName().equals(WSDLConstants.QNAME_SCHEMA) && 
/*  434 */         imported) {
/*      */         
/*  436 */         LOGGER.warning(WsdlmodelMessages.WSDL_IMPORT_SHOULD_BE_WSDL(parser.systemId));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  442 */       String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/*      */       
/*  444 */       String oldTargetNamespace = this.targetNamespace;
/*  445 */       this.targetNamespace = tns;
/*      */       
/*  447 */       while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*      */         
/*  449 */         if (reader.getEventType() == 8) {
/*      */           break;
/*      */         }
/*  452 */         QName name = reader.getName();
/*  453 */         if (WSDLConstants.QNAME_IMPORT.equals(name)) {
/*  454 */           parseImport(parser.systemId, reader); continue;
/*  455 */         }  if (WSDLConstants.QNAME_MESSAGE.equals(name)) {
/*  456 */           parseMessage(reader); continue;
/*  457 */         }  if (WSDLConstants.QNAME_PORT_TYPE.equals(name)) {
/*  458 */           parsePortType(reader); continue;
/*  459 */         }  if (WSDLConstants.QNAME_BINDING.equals(name)) {
/*  460 */           parseBinding(reader); continue;
/*  461 */         }  if (WSDLConstants.QNAME_SERVICE.equals(name)) {
/*  462 */           parseService(reader); continue;
/*      */         } 
/*  464 */         this.extensionFacade.definitionsElements(reader);
/*      */       } 
/*      */       
/*  467 */       this.targetNamespace = oldTargetNamespace;
/*      */     } finally {
/*  469 */       this.wsdldef_nsdecl = new HashMap<>();
/*  470 */       reader.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void parseService(XMLStreamReader reader) {
/*  475 */     this.service_nsdecl.putAll(this.wsdldef_nsdecl);
/*  476 */     readNSDecl(this.service_nsdecl, reader);
/*      */     
/*  478 */     String serviceName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  479 */     WSDLServiceImpl wSDLServiceImpl = new WSDLServiceImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, serviceName));
/*  480 */     this.extensionFacade.serviceAttributes((EditableWSDLService)wSDLServiceImpl, reader);
/*  481 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  482 */       QName name = reader.getName();
/*  483 */       if (WSDLConstants.QNAME_PORT.equals(name)) {
/*  484 */         parsePort(reader, (EditableWSDLService)wSDLServiceImpl);
/*  485 */         if (reader.getEventType() != 2)
/*  486 */           XMLStreamReaderUtil.next(reader); 
/*      */         continue;
/*      */       } 
/*  489 */       this.extensionFacade.serviceElements((EditableWSDLService)wSDLServiceImpl, reader);
/*      */     } 
/*      */     
/*  492 */     this.wsdlDoc.addService((EditableWSDLService)wSDLServiceImpl);
/*  493 */     this.service_nsdecl = new HashMap<>();
/*      */   }
/*      */   
/*      */   private void parsePort(XMLStreamReader reader, EditableWSDLService service) {
/*  497 */     this.port_nsdecl.putAll(this.service_nsdecl);
/*  498 */     readNSDecl(this.port_nsdecl, reader);
/*      */     
/*  500 */     String portName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  501 */     String binding = ParserUtil.getMandatoryNonEmptyAttribute(reader, "binding");
/*      */     
/*  503 */     QName bindingName = ParserUtil.getQName(reader, binding);
/*  504 */     QName portQName = new QName(service.getName().getNamespaceURI(), portName);
/*  505 */     WSDLPortImpl wSDLPortImpl = new WSDLPortImpl(reader, service, portQName, bindingName);
/*      */     
/*  507 */     this.extensionFacade.portAttributes((EditableWSDLPort)wSDLPortImpl, reader);
/*      */ 
/*      */     
/*  510 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  511 */       QName name = reader.getName();
/*  512 */       if (SOAPConstants.QNAME_ADDRESS.equals(name) || SOAPConstants.QNAME_SOAP12ADDRESS.equals(name)) {
/*  513 */         String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*  514 */         if (location != null) {
/*      */           try {
/*  516 */             wSDLPortImpl.setAddress(new EndpointAddress(location));
/*  517 */           } catch (URISyntaxException uRISyntaxException) {}
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  522 */         XMLStreamReaderUtil.next(reader); continue;
/*  523 */       }  if (AddressingVersion.W3C.nsUri.equals(name.getNamespaceURI()) && "EndpointReference"
/*  524 */         .equals(name.getLocalPart())) {
/*      */         try {
/*  526 */           StreamReaderBufferCreator creator = new StreamReaderBufferCreator(new MutableXMLStreamBuffer());
/*  527 */           XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(this.port_nsdecl, (AbstractCreatorProcessor)creator);
/*  528 */           creator.createElementFragment(reader, false);
/*      */           
/*  530 */           WSEndpointReference wsepr = new WSEndpointReference((XMLStreamBuffer)xMLStreamBufferMark, AddressingVersion.W3C);
/*      */           
/*  532 */           wSDLPortImpl.setEPR(wsepr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  538 */           if (reader.getEventType() == 2 && reader.getName().equals(WSDLConstants.QNAME_PORT))
/*      */             break; 
/*  540 */         } catch (XMLStreamException e) {
/*  541 */           throw new WebServiceException(e);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  545 */       this.extensionFacade.portElements((EditableWSDLPort)wSDLPortImpl, reader);
/*      */     } 
/*      */     
/*  548 */     if (wSDLPortImpl.getAddress() == null) {
/*      */       try {
/*  550 */         wSDLPortImpl.setAddress(new EndpointAddress(""));
/*  551 */       } catch (URISyntaxException uRISyntaxException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  556 */     service.put(portQName, (EditableWSDLPort)wSDLPortImpl);
/*  557 */     this.port_nsdecl = new HashMap<>();
/*      */   }
/*      */   
/*      */   private void parseBinding(XMLStreamReader reader) {
/*  561 */     String bindingName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  562 */     String portTypeName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "type");
/*  563 */     if (bindingName == null || portTypeName == null) {
/*      */ 
/*      */ 
/*      */       
/*  567 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       
/*      */       return;
/*      */     } 
/*  571 */     WSDLBoundPortTypeImpl wSDLBoundPortTypeImpl = new WSDLBoundPortTypeImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, bindingName), ParserUtil.getQName(reader, portTypeName));
/*  572 */     this.extensionFacade.bindingAttributes((EditableWSDLBoundPortType)wSDLBoundPortTypeImpl, reader);
/*      */     
/*  574 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  575 */       QName name = reader.getName();
/*  576 */       if (WSDLConstants.NS_SOAP_BINDING.equals(name)) {
/*  577 */         String transport = reader.getAttributeValue(null, "transport");
/*  578 */         wSDLBoundPortTypeImpl.setBindingId(createBindingId(transport, SOAPVersion.SOAP_11));
/*      */         
/*  580 */         String style = reader.getAttributeValue(null, "style");
/*      */         
/*  582 */         if (style != null && style.equals("rpc")) {
/*  583 */           wSDLBoundPortTypeImpl.setStyle(SOAPBinding.Style.RPC);
/*      */         } else {
/*  585 */           wSDLBoundPortTypeImpl.setStyle(SOAPBinding.Style.DOCUMENT);
/*      */         } 
/*  587 */         goToEnd(reader); continue;
/*  588 */       }  if (WSDLConstants.NS_SOAP12_BINDING.equals(name)) {
/*  589 */         String transport = reader.getAttributeValue(null, "transport");
/*  590 */         wSDLBoundPortTypeImpl.setBindingId(createBindingId(transport, SOAPVersion.SOAP_12));
/*      */         
/*  592 */         String style = reader.getAttributeValue(null, "style");
/*  593 */         if (style != null && style.equals("rpc")) {
/*  594 */           wSDLBoundPortTypeImpl.setStyle(SOAPBinding.Style.RPC);
/*      */         } else {
/*  596 */           wSDLBoundPortTypeImpl.setStyle(SOAPBinding.Style.DOCUMENT);
/*      */         } 
/*  598 */         goToEnd(reader); continue;
/*  599 */       }  if (WSDLConstants.QNAME_OPERATION.equals(name)) {
/*  600 */         parseBindingOperation(reader, (EditableWSDLBoundPortType)wSDLBoundPortTypeImpl); continue;
/*      */       } 
/*  602 */       this.extensionFacade.bindingElements((EditableWSDLBoundPortType)wSDLBoundPortTypeImpl, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static BindingID createBindingId(String transport, SOAPVersion soapVersion) {
/*  608 */     if (!transport.equals("http://schemas.xmlsoap.org/soap/http")) {
/*  609 */       for (BindingIDFactory f : ServiceFinder.find(BindingIDFactory.class)) {
/*  610 */         BindingID bindingId = f.create(transport, soapVersion);
/*  611 */         if (bindingId != null) {
/*  612 */           return bindingId;
/*      */         }
/*      */       } 
/*      */     }
/*  616 */     return soapVersion.equals(SOAPVersion.SOAP_11) ? (BindingID)BindingID.SOAP11_HTTP : (BindingID)BindingID.SOAP12_HTTP;
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseBindingOperation(XMLStreamReader reader, EditableWSDLBoundPortType binding) {
/*  621 */     String bindingOpName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  622 */     if (bindingOpName == null) {
/*      */ 
/*      */       
/*  625 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       
/*      */       return;
/*      */     } 
/*  629 */     QName opName = new QName(binding.getPortTypeName().getNamespaceURI(), bindingOpName);
/*  630 */     WSDLBoundOperationImpl wSDLBoundOperationImpl = new WSDLBoundOperationImpl(reader, binding, opName);
/*  631 */     binding.put(opName, (EditableWSDLBoundOperation)wSDLBoundOperationImpl);
/*  632 */     this.extensionFacade.bindingOperationAttributes((EditableWSDLBoundOperation)wSDLBoundOperationImpl, reader);
/*      */     
/*  634 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  635 */       QName name = reader.getName();
/*  636 */       String style = null;
/*  637 */       if (WSDLConstants.QNAME_INPUT.equals(name)) {
/*  638 */         parseInputBinding(reader, (EditableWSDLBoundOperation)wSDLBoundOperationImpl);
/*  639 */       } else if (WSDLConstants.QNAME_OUTPUT.equals(name)) {
/*  640 */         parseOutputBinding(reader, (EditableWSDLBoundOperation)wSDLBoundOperationImpl);
/*  641 */       } else if (WSDLConstants.QNAME_FAULT.equals(name)) {
/*  642 */         parseFaultBinding(reader, (EditableWSDLBoundOperation)wSDLBoundOperationImpl);
/*  643 */       } else if (SOAPConstants.QNAME_OPERATION.equals(name) || SOAPConstants.QNAME_SOAP12OPERATION
/*  644 */         .equals(name)) {
/*  645 */         style = reader.getAttributeValue(null, "style");
/*  646 */         String soapAction = reader.getAttributeValue(null, "soapAction");
/*      */         
/*  648 */         if (soapAction != null) {
/*  649 */           wSDLBoundOperationImpl.setSoapAction(soapAction);
/*      */         }
/*  651 */         goToEnd(reader);
/*      */       } else {
/*  653 */         this.extensionFacade.bindingOperationElements((EditableWSDLBoundOperation)wSDLBoundOperationImpl, reader);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  659 */       if (style != null) {
/*  660 */         if (style.equals("rpc")) {
/*  661 */           wSDLBoundOperationImpl.setStyle(SOAPBinding.Style.RPC); continue;
/*      */         } 
/*  663 */         wSDLBoundOperationImpl.setStyle(SOAPBinding.Style.DOCUMENT); continue;
/*      */       } 
/*  665 */       wSDLBoundOperationImpl.setStyle(binding.getStyle());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseInputBinding(XMLStreamReader reader, EditableWSDLBoundOperation bindingOp) {
/*  671 */     boolean bodyFound = false;
/*  672 */     this.extensionFacade.bindingOperationInputAttributes(bindingOp, reader);
/*  673 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  674 */       QName name = reader.getName();
/*  675 */       if ((SOAPConstants.QNAME_BODY.equals(name) || SOAPConstants.QNAME_SOAP12BODY.equals(name)) && !bodyFound) {
/*  676 */         bodyFound = true;
/*  677 */         bindingOp.setInputExplicitBodyParts(parseSOAPBodyBinding(reader, bindingOp, BindingMode.INPUT));
/*  678 */         goToEnd(reader); continue;
/*  679 */       }  if (SOAPConstants.QNAME_HEADER.equals(name) || SOAPConstants.QNAME_SOAP12HEADER.equals(name)) {
/*  680 */         parseSOAPHeaderBinding(reader, bindingOp.getInputParts()); continue;
/*  681 */       }  if (MIMEConstants.QNAME_MULTIPART_RELATED.equals(name)) {
/*  682 */         parseMimeMultipartBinding(reader, bindingOp, BindingMode.INPUT); continue;
/*      */       } 
/*  684 */       this.extensionFacade.bindingOperationInputElements(bindingOp, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseOutputBinding(XMLStreamReader reader, EditableWSDLBoundOperation bindingOp) {
/*  690 */     boolean bodyFound = false;
/*  691 */     this.extensionFacade.bindingOperationOutputAttributes(bindingOp, reader);
/*  692 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  693 */       QName name = reader.getName();
/*  694 */       if ((SOAPConstants.QNAME_BODY.equals(name) || SOAPConstants.QNAME_SOAP12BODY.equals(name)) && !bodyFound) {
/*  695 */         bodyFound = true;
/*  696 */         bindingOp.setOutputExplicitBodyParts(parseSOAPBodyBinding(reader, bindingOp, BindingMode.OUTPUT));
/*  697 */         goToEnd(reader); continue;
/*  698 */       }  if (SOAPConstants.QNAME_HEADER.equals(name) || SOAPConstants.QNAME_SOAP12HEADER.equals(name)) {
/*  699 */         parseSOAPHeaderBinding(reader, bindingOp.getOutputParts()); continue;
/*  700 */       }  if (MIMEConstants.QNAME_MULTIPART_RELATED.equals(name)) {
/*  701 */         parseMimeMultipartBinding(reader, bindingOp, BindingMode.OUTPUT); continue;
/*      */       } 
/*  703 */       this.extensionFacade.bindingOperationOutputElements(bindingOp, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseFaultBinding(XMLStreamReader reader, EditableWSDLBoundOperation bindingOp) {
/*  709 */     String faultName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  710 */     WSDLBoundFaultImpl wSDLBoundFaultImpl = new WSDLBoundFaultImpl(reader, faultName, bindingOp);
/*  711 */     bindingOp.addFault((EditableWSDLBoundFault)wSDLBoundFaultImpl);
/*      */     
/*  713 */     this.extensionFacade.bindingOperationFaultAttributes((EditableWSDLBoundFault)wSDLBoundFaultImpl, reader);
/*      */     
/*  715 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2)
/*  716 */       this.extensionFacade.bindingOperationFaultElements((EditableWSDLBoundFault)wSDLBoundFaultImpl, reader); 
/*      */   }
/*      */   
/*      */   private enum BindingMode
/*      */   {
/*  721 */     INPUT, OUTPUT, FAULT; }
/*      */   
/*      */   private static boolean parseSOAPBodyBinding(XMLStreamReader reader, EditableWSDLBoundOperation op, BindingMode mode) {
/*  724 */     String namespace = reader.getAttributeValue(null, "namespace");
/*  725 */     if (mode == BindingMode.INPUT) {
/*  726 */       op.setRequestNamespace(namespace);
/*  727 */       return parseSOAPBodyBinding(reader, op.getInputParts());
/*      */     } 
/*      */     
/*  730 */     op.setResponseNamespace(namespace);
/*  731 */     return parseSOAPBodyBinding(reader, op.getOutputParts());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean parseSOAPBodyBinding(XMLStreamReader reader, Map<String, ParameterBinding> parts) {
/*  738 */     String partsString = reader.getAttributeValue(null, "parts");
/*  739 */     if (partsString != null) {
/*  740 */       List<String> partsList = XmlUtil.parseTokenList(partsString);
/*  741 */       if (partsList.isEmpty()) {
/*  742 */         parts.put(" ", ParameterBinding.BODY);
/*      */       } else {
/*  744 */         for (String part : partsList) {
/*  745 */           parts.put(part, ParameterBinding.BODY);
/*      */         }
/*      */       } 
/*  748 */       return true;
/*      */     } 
/*  750 */     return false;
/*      */   }
/*      */   
/*      */   private static void parseSOAPHeaderBinding(XMLStreamReader reader, Map<String, ParameterBinding> parts) {
/*  754 */     String part = reader.getAttributeValue(null, "part");
/*      */     
/*  756 */     if (part == null || part.equals("")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  763 */     parts.put(part, ParameterBinding.HEADER);
/*  764 */     goToEnd(reader);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void parseMimeMultipartBinding(XMLStreamReader reader, EditableWSDLBoundOperation op, BindingMode mode) {
/*  769 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  770 */       QName name = reader.getName();
/*  771 */       if (MIMEConstants.QNAME_PART.equals(name)) {
/*  772 */         parseMIMEPart(reader, op, mode); continue;
/*      */       } 
/*  774 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void parseMIMEPart(XMLStreamReader reader, EditableWSDLBoundOperation op, BindingMode mode) {
/*  780 */     boolean bodyFound = false;
/*  781 */     Map<String, ParameterBinding> parts = null;
/*  782 */     if (mode == BindingMode.INPUT) {
/*  783 */       parts = op.getInputParts();
/*  784 */     } else if (mode == BindingMode.OUTPUT) {
/*  785 */       parts = op.getOutputParts();
/*  786 */     } else if (mode == BindingMode.FAULT) {
/*  787 */       parts = op.getFaultParts();
/*      */     } 
/*  789 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  790 */       QName name = reader.getName();
/*  791 */       if (SOAPConstants.QNAME_BODY.equals(name) && !bodyFound) {
/*  792 */         bodyFound = true;
/*  793 */         parseSOAPBodyBinding(reader, op, mode);
/*  794 */         XMLStreamReaderUtil.next(reader); continue;
/*  795 */       }  if (SOAPConstants.QNAME_HEADER.equals(name)) {
/*  796 */         bodyFound = true;
/*  797 */         parseSOAPHeaderBinding(reader, parts);
/*  798 */         XMLStreamReaderUtil.next(reader); continue;
/*  799 */       }  if (MIMEConstants.QNAME_CONTENT.equals(name)) {
/*  800 */         String part = reader.getAttributeValue(null, "part");
/*  801 */         String type = reader.getAttributeValue(null, "type");
/*  802 */         if (part == null || type == null) {
/*  803 */           XMLStreamReaderUtil.skipElement(reader);
/*      */           continue;
/*      */         } 
/*  806 */         ParameterBinding sb = ParameterBinding.createAttachment(type);
/*  807 */         if (parts != null && sb != null && part != null)
/*  808 */           parts.put(part, sb); 
/*  809 */         XMLStreamReaderUtil.next(reader); continue;
/*      */       } 
/*  811 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void parseImport(@Nullable URL baseURL, XMLStreamReader reader) throws IOException, SAXException, XMLStreamException {
/*      */     URL importURL;
/*  819 */     String importLocation = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*      */     
/*  821 */     if (baseURL != null) {
/*  822 */       importURL = new URL(baseURL, importLocation);
/*      */     } else {
/*  824 */       importURL = new URL(importLocation);
/*  825 */     }  parseImport(importURL);
/*  826 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  827 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortType(XMLStreamReader reader) {
/*  832 */     String portTypeName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  833 */     if (portTypeName == null) {
/*      */ 
/*      */       
/*  836 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       return;
/*      */     } 
/*  839 */     WSDLPortTypeImpl wSDLPortTypeImpl = new WSDLPortTypeImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, portTypeName));
/*  840 */     this.extensionFacade.portTypeAttributes((EditableWSDLPortType)wSDLPortTypeImpl, reader);
/*  841 */     this.wsdlDoc.addPortType((EditableWSDLPortType)wSDLPortTypeImpl);
/*  842 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  843 */       QName name = reader.getName();
/*  844 */       if (WSDLConstants.QNAME_OPERATION.equals(name)) {
/*  845 */         parsePortTypeOperation(reader, (EditableWSDLPortType)wSDLPortTypeImpl); continue;
/*      */       } 
/*  847 */       this.extensionFacade.portTypeElements((EditableWSDLPortType)wSDLPortTypeImpl, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void parsePortTypeOperation(XMLStreamReader reader, EditableWSDLPortType portType) {
/*  854 */     String operationName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  855 */     if (operationName == null) {
/*      */ 
/*      */       
/*  858 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       
/*      */       return;
/*      */     } 
/*  862 */     QName operationQName = new QName(portType.getName().getNamespaceURI(), operationName);
/*  863 */     WSDLOperationImpl wSDLOperationImpl = new WSDLOperationImpl(reader, portType, operationQName);
/*  864 */     this.extensionFacade.portTypeOperationAttributes((EditableWSDLOperation)wSDLOperationImpl, reader);
/*  865 */     String parameterOrder = ParserUtil.getAttribute(reader, "parameterOrder");
/*  866 */     wSDLOperationImpl.setParameterOrder(parameterOrder);
/*  867 */     portType.put(operationName, (EditableWSDLOperation)wSDLOperationImpl);
/*  868 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  869 */       QName name = reader.getName();
/*  870 */       if (name.equals(WSDLConstants.QNAME_INPUT)) {
/*  871 */         parsePortTypeOperationInput(reader, (EditableWSDLOperation)wSDLOperationImpl); continue;
/*  872 */       }  if (name.equals(WSDLConstants.QNAME_OUTPUT)) {
/*  873 */         parsePortTypeOperationOutput(reader, (EditableWSDLOperation)wSDLOperationImpl); continue;
/*  874 */       }  if (name.equals(WSDLConstants.QNAME_FAULT)) {
/*  875 */         parsePortTypeOperationFault(reader, (EditableWSDLOperation)wSDLOperationImpl); continue;
/*      */       } 
/*  877 */       this.extensionFacade.portTypeOperationElements((EditableWSDLOperation)wSDLOperationImpl, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void parsePortTypeOperationFault(XMLStreamReader reader, EditableWSDLOperation operation) {
/*  884 */     String msg = ParserUtil.getMandatoryNonEmptyAttribute(reader, "message");
/*  885 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  886 */     String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  887 */     WSDLFaultImpl wSDLFaultImpl = new WSDLFaultImpl(reader, name, msgName, operation);
/*  888 */     operation.addFault((EditableWSDLFault)wSDLFaultImpl);
/*  889 */     this.extensionFacade.portTypeOperationFaultAttributes((EditableWSDLFault)wSDLFaultImpl, reader);
/*  890 */     this.extensionFacade.portTypeOperationFault(operation, reader);
/*  891 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  892 */       this.extensionFacade.portTypeOperationFaultElements((EditableWSDLFault)wSDLFaultImpl, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortTypeOperationInput(XMLStreamReader reader, EditableWSDLOperation operation) {
/*  897 */     String msg = ParserUtil.getMandatoryNonEmptyAttribute(reader, "message");
/*  898 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  899 */     String name = ParserUtil.getAttribute(reader, "name");
/*  900 */     WSDLInputImpl wSDLInputImpl = new WSDLInputImpl(reader, name, msgName, operation);
/*  901 */     operation.setInput((EditableWSDLInput)wSDLInputImpl);
/*  902 */     this.extensionFacade.portTypeOperationInputAttributes((EditableWSDLInput)wSDLInputImpl, reader);
/*  903 */     this.extensionFacade.portTypeOperationInput(operation, reader);
/*  904 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  905 */       this.extensionFacade.portTypeOperationInputElements((EditableWSDLInput)wSDLInputImpl, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortTypeOperationOutput(XMLStreamReader reader, EditableWSDLOperation operation) {
/*  910 */     String msg = ParserUtil.getAttribute(reader, "message");
/*  911 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  912 */     String name = ParserUtil.getAttribute(reader, "name");
/*  913 */     WSDLOutputImpl wSDLOutputImpl = new WSDLOutputImpl(reader, name, msgName, operation);
/*  914 */     operation.setOutput((EditableWSDLOutput)wSDLOutputImpl);
/*  915 */     this.extensionFacade.portTypeOperationOutputAttributes((EditableWSDLOutput)wSDLOutputImpl, reader);
/*  916 */     this.extensionFacade.portTypeOperationOutput(operation, reader);
/*  917 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  918 */       this.extensionFacade.portTypeOperationOutputElements((EditableWSDLOutput)wSDLOutputImpl, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parseMessage(XMLStreamReader reader) {
/*  923 */     String msgName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  924 */     WSDLMessageImpl wSDLMessageImpl = new WSDLMessageImpl(reader, new QName(this.targetNamespace, msgName));
/*  925 */     this.extensionFacade.messageAttributes((EditableWSDLMessage)wSDLMessageImpl, reader);
/*  926 */     int partIndex = 0;
/*  927 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  928 */       QName name = reader.getName();
/*  929 */       if (WSDLConstants.QNAME_PART.equals(name)) {
/*  930 */         String part = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  931 */         String desc = null;
/*  932 */         int index = reader.getAttributeCount();
/*  933 */         WSDLDescriptorKind kind = WSDLDescriptorKind.ELEMENT;
/*  934 */         for (int i = 0; i < index; i++) {
/*  935 */           QName descName = reader.getAttributeName(i);
/*  936 */           if (descName.getLocalPart().equals("element")) {
/*  937 */             kind = WSDLDescriptorKind.ELEMENT;
/*  938 */           } else if (descName.getLocalPart().equals("type")) {
/*  939 */             kind = WSDLDescriptorKind.TYPE;
/*      */           } 
/*  941 */           if (descName.getLocalPart().equals("element") || descName.getLocalPart().equals("type")) {
/*  942 */             desc = reader.getAttributeValue(i);
/*      */             break;
/*      */           } 
/*      */         } 
/*  946 */         if (desc != null) {
/*  947 */           WSDLPartImpl wSDLPartImpl = new WSDLPartImpl(reader, part, partIndex, (WSDLPartDescriptor)new WSDLPartDescriptorImpl(reader, ParserUtil.getQName(reader, desc), kind));
/*  948 */           wSDLMessageImpl.add((EditableWSDLPart)wSDLPartImpl);
/*      */         } 
/*  950 */         if (reader.getEventType() != 2)
/*  951 */           goToEnd(reader);  continue;
/*      */       } 
/*  953 */       this.extensionFacade.messageElements((EditableWSDLMessage)wSDLMessageImpl, reader);
/*      */     } 
/*      */     
/*  956 */     this.wsdlDoc.addMessage((EditableWSDLMessage)wSDLMessageImpl);
/*  957 */     if (reader.getEventType() != 2)
/*  958 */       goToEnd(reader); 
/*      */   }
/*      */   
/*      */   private static void goToEnd(XMLStreamReader reader) {
/*  962 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  963 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamReader createReader(URL wsdlLoc) throws IOException, XMLStreamException {
/*  973 */     return createReader(wsdlLoc, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamReader createReader(URL wsdlLoc, Class<Service> serviceClass) throws IOException, XMLStreamException {
/*      */     InputStream stream;
/*      */     try {
/*  984 */       stream = wsdlLoc.openStream();
/*  985 */     } catch (IOException io) {
/*      */ 
/*      */       
/*  988 */       if (serviceClass != null)
/*  989 */       { WSDLLocator locator = (WSDLLocator)ContainerResolver.getInstance().getContainer().getSPI(WSDLLocator.class);
/*  990 */         if (locator != null)
/*  991 */         { String exForm = wsdlLoc.toExternalForm();
/*  992 */           URL ru = serviceClass.getResource(".");
/*  993 */           String loc = wsdlLoc.getPath();
/*  994 */           if (ru != null) {
/*  995 */             String ruExForm = ru.toExternalForm();
/*  996 */             if (exForm.startsWith(ruExForm)) {
/*  997 */               loc = exForm.substring(ruExForm.length());
/*      */             }
/*      */           } 
/* 1000 */           wsdlLoc = locator.locateWSDL(serviceClass, loc);
/* 1001 */           if (wsdlLoc != null)
/* 1002 */           { stream = new FilterInputStream(wsdlLoc.openStream())
/*      */               {
/*      */                 boolean closed;
/*      */                 
/*      */                 public void close() throws IOException {
/* 1007 */                   if (!this.closed) {
/* 1008 */                     this.closed = true;
/* 1009 */                     byte[] buf = new byte[8192];
/* 1010 */                     while (read(buf) != -1);
/* 1011 */                     super.close();
/*      */                   }
/*      */                 
/*      */                 }
/*      */               }; }
/*      */           
/*      */           else
/*      */           
/* 1019 */           { throw io; }  } else { throw io; }  } else { throw io; }
/*      */     
/*      */     } 
/*      */     
/* 1023 */     return (XMLStreamReader)new TidyXMLStreamReader(XMLStreamReaderFactory.create(wsdlLoc.toExternalForm(), stream, false), stream);
/*      */   }
/*      */ 
/*      */   
/*      */   private void register(WSDLParserExtension e) {
/* 1028 */     this.extensions.add(new FoolProofParserExtension(e));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readNSDecl(Map<String, String> ns_map, XMLStreamReader reader) {
/* 1039 */     if (reader.getNamespaceCount() > 0) {
/* 1040 */       for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 1041 */         ns_map.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1046 */   private static final Logger LOGGER = Logger.getLogger(RuntimeWSDLParser.class.getName());
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/RuntimeWSDLParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */