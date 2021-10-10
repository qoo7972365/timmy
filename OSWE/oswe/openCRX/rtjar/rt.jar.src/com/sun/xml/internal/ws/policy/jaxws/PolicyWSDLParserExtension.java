/*     */ package com.sun.xml.internal.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLObject;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import com.sun.xml.internal.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import com.sun.xml.internal.ws.policy.PolicyException;
/*     */ import com.sun.xml.internal.ws.policy.PolicyMap;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.PolicySourceModelContext;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import com.sun.xml.internal.ws.resources.PolicyMessages;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public final class PolicyWSDLParserExtension
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   enum HandlerType
/*     */   {
/*  76 */     PolicyUri, AnonymousPolicyId;
/*     */   }
/*     */   
/*     */   static final class PolicyRecordHandler {
/*     */     String handler;
/*     */     PolicyWSDLParserExtension.HandlerType type;
/*     */     
/*     */     PolicyRecordHandler(PolicyWSDLParserExtension.HandlerType type, String handler) {
/*  84 */       this.type = type;
/*  85 */       this.handler = handler;
/*     */     }
/*     */     
/*     */     PolicyWSDLParserExtension.HandlerType getType() {
/*  89 */       return this.type;
/*     */     }
/*     */     
/*     */     String getHandler() {
/*  93 */       return this.handler;
/*     */     }
/*     */   }
/*     */   
/*  97 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyWSDLParserExtension.class);
/*     */ 
/*     */   
/* 100 */   private static final StringBuffer AnonymnousPolicyIdPrefix = new StringBuffer("#__anonymousPolicy__ID");
/*     */ 
/*     */   
/*     */   private int anonymousPoliciesCount;
/*     */   
/* 105 */   private final SafePolicyReader policyReader = new SafePolicyReader();
/*     */ 
/*     */   
/* 108 */   private SafePolicyReader.PolicyRecord expandQueueHead = null;
/*     */ 
/*     */   
/* 111 */   private Map<String, SafePolicyReader.PolicyRecord> policyRecordsPassedBy = null;
/*     */   
/* 113 */   private Map<String, PolicySourceModel> anonymousPolicyModels = null;
/*     */ 
/*     */   
/* 116 */   private List<String> unresolvedUris = null;
/*     */ 
/*     */   
/* 119 */   private final LinkedList<String> urisNeeded = new LinkedList<>();
/* 120 */   private final Map<String, PolicySourceModel> modelsNeeded = new HashMap<>();
/*     */ 
/*     */   
/* 123 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4ServiceMap = null;
/* 124 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4PortMap = null;
/* 125 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4PortTypeMap = null;
/* 126 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingMap = null;
/* 127 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BoundOperationMap = null;
/* 128 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4OperationMap = null;
/* 129 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4MessageMap = null;
/* 130 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4InputMap = null;
/* 131 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4OutputMap = null;
/* 132 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4FaultMap = null;
/* 133 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingInputOpMap = null;
/* 134 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingOutputOpMap = null;
/* 135 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingFaultOpMap = null;
/*     */   
/* 137 */   private PolicyMapBuilder policyBuilder = new PolicyMapBuilder();
/*     */   
/*     */   private boolean isPolicyProcessed(String policyUri) {
/* 140 */     return this.modelsNeeded.containsKey(policyUri);
/*     */   }
/*     */   
/*     */   private void addNewPolicyNeeded(String policyUri, PolicySourceModel policyModel) {
/* 144 */     if (!this.modelsNeeded.containsKey(policyUri)) {
/* 145 */       this.modelsNeeded.put(policyUri, policyModel);
/* 146 */       this.urisNeeded.addFirst(policyUri);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<String, PolicySourceModel> getPolicyModels() {
/* 151 */     return this.modelsNeeded;
/*     */   }
/*     */   
/*     */   private Map<String, SafePolicyReader.PolicyRecord> getPolicyRecordsPassedBy() {
/* 155 */     if (null == this.policyRecordsPassedBy) {
/* 156 */       this.policyRecordsPassedBy = new HashMap<>();
/*     */     }
/* 158 */     return this.policyRecordsPassedBy;
/*     */   }
/*     */   
/*     */   private Map<String, PolicySourceModel> getAnonymousPolicyModels() {
/* 162 */     if (null == this.anonymousPolicyModels) {
/* 163 */       this.anonymousPolicyModels = new HashMap<>();
/*     */     }
/* 165 */     return this.anonymousPolicyModels;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4ServiceMap() {
/* 169 */     if (null == this.handlers4ServiceMap) {
/* 170 */       this.handlers4ServiceMap = new HashMap<>();
/*     */     }
/* 172 */     return this.handlers4ServiceMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4PortMap() {
/* 176 */     if (null == this.handlers4PortMap) {
/* 177 */       this.handlers4PortMap = new HashMap<>();
/*     */     }
/* 179 */     return this.handlers4PortMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4PortTypeMap() {
/* 183 */     if (null == this.handlers4PortTypeMap) {
/* 184 */       this.handlers4PortTypeMap = new HashMap<>();
/*     */     }
/* 186 */     return this.handlers4PortTypeMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingMap() {
/* 190 */     if (null == this.handlers4BindingMap) {
/* 191 */       this.handlers4BindingMap = new HashMap<>();
/*     */     }
/* 193 */     return this.handlers4BindingMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4OperationMap() {
/* 197 */     if (null == this.handlers4OperationMap) {
/* 198 */       this.handlers4OperationMap = new HashMap<>();
/*     */     }
/* 200 */     return this.handlers4OperationMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BoundOperationMap() {
/* 204 */     if (null == this.handlers4BoundOperationMap) {
/* 205 */       this.handlers4BoundOperationMap = new HashMap<>();
/*     */     }
/* 207 */     return this.handlers4BoundOperationMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4MessageMap() {
/* 211 */     if (null == this.handlers4MessageMap) {
/* 212 */       this.handlers4MessageMap = new HashMap<>();
/*     */     }
/* 214 */     return this.handlers4MessageMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4InputMap() {
/* 218 */     if (null == this.handlers4InputMap) {
/* 219 */       this.handlers4InputMap = new HashMap<>();
/*     */     }
/* 221 */     return this.handlers4InputMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4OutputMap() {
/* 225 */     if (null == this.handlers4OutputMap) {
/* 226 */       this.handlers4OutputMap = new HashMap<>();
/*     */     }
/* 228 */     return this.handlers4OutputMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4FaultMap() {
/* 232 */     if (null == this.handlers4FaultMap) {
/* 233 */       this.handlers4FaultMap = new HashMap<>();
/*     */     }
/* 235 */     return this.handlers4FaultMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingInputOpMap() {
/* 239 */     if (null == this.handlers4BindingInputOpMap) {
/* 240 */       this.handlers4BindingInputOpMap = new HashMap<>();
/*     */     }
/* 242 */     return this.handlers4BindingInputOpMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingOutputOpMap() {
/* 246 */     if (null == this.handlers4BindingOutputOpMap) {
/* 247 */       this.handlers4BindingOutputOpMap = new HashMap<>();
/*     */     }
/* 249 */     return this.handlers4BindingOutputOpMap;
/*     */   }
/*     */   
/*     */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingFaultOpMap() {
/* 253 */     if (null == this.handlers4BindingFaultOpMap) {
/* 254 */       this.handlers4BindingFaultOpMap = new HashMap<>();
/*     */     }
/* 256 */     return this.handlers4BindingFaultOpMap;
/*     */   }
/*     */   
/*     */   private List<String> getUnresolvedUris(boolean emptyListNeeded) {
/* 260 */     if (null == this.unresolvedUris || emptyListNeeded) {
/* 261 */       this.unresolvedUris = new LinkedList<>();
/*     */     }
/* 263 */     return this.unresolvedUris;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void policyRecToExpandQueue(SafePolicyReader.PolicyRecord policyRec) {
/* 269 */     if (null == this.expandQueueHead) {
/* 270 */       this.expandQueueHead = policyRec;
/*     */     } else {
/* 272 */       this.expandQueueHead = this.expandQueueHead.insert(policyRec);
/*     */     } 
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
/*     */   private PolicyRecordHandler readSinglePolicy(SafePolicyReader.PolicyRecord policyRec, boolean inner) {
/* 285 */     PolicyRecordHandler handler = null;
/* 286 */     String policyId = policyRec.policyModel.getPolicyId();
/* 287 */     if (policyId == null) {
/* 288 */       policyId = policyRec.policyModel.getPolicyName();
/*     */     }
/* 290 */     if (policyId != null) {
/* 291 */       handler = new PolicyRecordHandler(HandlerType.PolicyUri, policyRec.getUri());
/* 292 */       getPolicyRecordsPassedBy().put(policyRec.getUri(), policyRec);
/* 293 */       policyRecToExpandQueue(policyRec);
/* 294 */     } else if (inner) {
/* 295 */       String anonymousId = AnonymnousPolicyIdPrefix.append(this.anonymousPoliciesCount++).toString();
/* 296 */       handler = new PolicyRecordHandler(HandlerType.AnonymousPolicyId, anonymousId);
/* 297 */       getAnonymousPolicyModels().put(anonymousId, policyRec.policyModel);
/* 298 */       if (null != policyRec.unresolvedURIs) {
/* 299 */         getUnresolvedUris(false).addAll(policyRec.unresolvedURIs);
/*     */       }
/*     */     } 
/* 302 */     return handler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addHandlerToMap(Map<WSDLObject, Collection<PolicyRecordHandler>> map, WSDLObject key, PolicyRecordHandler handler) {
/* 308 */     if (map.containsKey(key)) {
/* 309 */       ((Collection<PolicyRecordHandler>)map.get(key)).add(handler);
/*     */     } else {
/* 311 */       Collection<PolicyRecordHandler> newSet = new LinkedList<>();
/* 312 */       newSet.add(handler);
/* 313 */       map.put(key, newSet);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getBaseUrl(String policyUri) {
/* 318 */     if (null == policyUri) {
/* 319 */       return null;
/*     */     }
/*     */     
/* 322 */     int fragmentIdx = policyUri.indexOf('#');
/* 323 */     return (fragmentIdx == -1) ? policyUri : policyUri.substring(0, fragmentIdx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processReferenceUri(String policyUri, WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/* 334 */     if (null == policyUri || policyUri.length() == 0) {
/*     */       return;
/*     */     }
/* 337 */     if ('#' != policyUri.charAt(0)) {
/* 338 */       getUnresolvedUris(false).add(policyUri);
/*     */     }
/*     */     
/* 341 */     addHandlerToMap(map, element, new PolicyRecordHandler(HandlerType.PolicyUri, 
/*     */ 
/*     */           
/* 344 */           SafePolicyReader.relativeToAbsoluteUrl(policyUri, reader.getLocation().getSystemId())));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean processSubelement(WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/* 349 */     if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.PolicyReference) {
/* 350 */       processReferenceUri(this.policyReader.readPolicyReferenceElement(reader), element, reader, map);
/* 351 */       return true;
/* 352 */     }  if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/*     */       
/* 354 */       PolicyRecordHandler handler = readSinglePolicy(this.policyReader
/* 355 */           .readPolicyElement(reader, 
/*     */             
/* 357 */             (null == reader.getLocation().getSystemId()) ? "" : reader
/* 358 */             .getLocation().getSystemId()), true);
/*     */       
/* 360 */       if (null != handler) {
/* 361 */         addHandlerToMap(map, element, handler);
/*     */       }
/* 363 */       return true;
/*     */     } 
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   private void processAttributes(WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/* 369 */     String[] uriArray = getPolicyURIsFromAttr(reader);
/* 370 */     if (null != uriArray) {
/* 371 */       for (String policyUri : uriArray) {
/* 372 */         processReferenceUri(policyUri, element, reader, map);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/* 379 */     LOGGER.entering();
/* 380 */     boolean result = processSubelement((WSDLObject)port, reader, getHandlers4PortMap());
/* 381 */     LOGGER.exiting();
/* 382 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portAttributes(EditableWSDLPort port, XMLStreamReader reader) {
/* 387 */     LOGGER.entering();
/* 388 */     processAttributes((WSDLObject)port, reader, getHandlers4PortMap());
/* 389 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
/* 394 */     LOGGER.entering();
/* 395 */     boolean result = processSubelement((WSDLObject)service, reader, getHandlers4ServiceMap());
/* 396 */     LOGGER.exiting();
/* 397 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serviceAttributes(EditableWSDLService service, XMLStreamReader reader) {
/* 402 */     LOGGER.entering();
/* 403 */     processAttributes((WSDLObject)service, reader, getHandlers4ServiceMap());
/* 404 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 410 */     LOGGER.entering();
/* 411 */     if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/* 412 */       readSinglePolicy(this.policyReader
/* 413 */           .readPolicyElement(reader, 
/*     */             
/* 415 */             (null == reader.getLocation().getSystemId()) ? "" : reader
/* 416 */             .getLocation().getSystemId()), false);
/*     */       
/* 418 */       LOGGER.exiting();
/* 419 */       return true;
/*     */     } 
/* 421 */     LOGGER.exiting();
/* 422 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 427 */     LOGGER.entering();
/* 428 */     boolean result = processSubelement((WSDLObject)binding, reader, getHandlers4BindingMap());
/* 429 */     LOGGER.exiting();
/* 430 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingAttributes(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 435 */     LOGGER.entering();
/* 436 */     processAttributes((WSDLObject)binding, reader, getHandlers4BindingMap());
/* 437 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
/* 442 */     LOGGER.entering();
/* 443 */     boolean result = processSubelement((WSDLObject)portType, reader, getHandlers4PortTypeMap());
/* 444 */     LOGGER.exiting();
/* 445 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeAttributes(EditableWSDLPortType portType, XMLStreamReader reader) {
/* 450 */     LOGGER.entering();
/* 451 */     processAttributes((WSDLObject)portType, reader, getHandlers4PortTypeMap());
/* 452 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 457 */     LOGGER.entering();
/* 458 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4OperationMap());
/* 459 */     LOGGER.exiting();
/* 460 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationAttributes(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 465 */     LOGGER.entering();
/* 466 */     processAttributes((WSDLObject)operation, reader, getHandlers4OperationMap());
/* 467 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation boundOperation, XMLStreamReader reader) {
/* 472 */     LOGGER.entering();
/* 473 */     boolean result = processSubelement((WSDLObject)boundOperation, reader, getHandlers4BoundOperationMap());
/* 474 */     LOGGER.exiting();
/* 475 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationAttributes(EditableWSDLBoundOperation boundOperation, XMLStreamReader reader) {
/* 480 */     LOGGER.entering();
/* 481 */     processAttributes((WSDLObject)boundOperation, reader, getHandlers4BoundOperationMap());
/* 482 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 487 */     LOGGER.entering();
/* 488 */     boolean result = processSubelement((WSDLObject)msg, reader, getHandlers4MessageMap());
/* 489 */     LOGGER.exiting();
/* 490 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void messageAttributes(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 495 */     LOGGER.entering();
/* 496 */     processAttributes((WSDLObject)msg, reader, getHandlers4MessageMap());
/* 497 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
/* 502 */     LOGGER.entering();
/* 503 */     boolean result = processSubelement((WSDLObject)input, reader, getHandlers4InputMap());
/* 504 */     LOGGER.exiting();
/* 505 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {
/* 510 */     LOGGER.entering();
/* 511 */     processAttributes((WSDLObject)input, reader, getHandlers4InputMap());
/* 512 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
/* 518 */     LOGGER.entering();
/* 519 */     boolean result = processSubelement((WSDLObject)output, reader, getHandlers4OutputMap());
/* 520 */     LOGGER.exiting();
/* 521 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {
/* 526 */     LOGGER.entering();
/* 527 */     processAttributes((WSDLObject)output, reader, getHandlers4OutputMap());
/* 528 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
/* 534 */     LOGGER.entering();
/* 535 */     boolean result = processSubelement((WSDLObject)fault, reader, getHandlers4FaultMap());
/* 536 */     LOGGER.exiting();
/* 537 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {
/* 542 */     LOGGER.entering();
/* 543 */     processAttributes((WSDLObject)fault, reader, getHandlers4FaultMap());
/* 544 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 549 */     LOGGER.entering();
/* 550 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4BindingInputOpMap());
/* 551 */     LOGGER.exiting();
/* 552 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationInputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 557 */     LOGGER.entering();
/* 558 */     processAttributes((WSDLObject)operation, reader, getHandlers4BindingInputOpMap());
/* 559 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 565 */     LOGGER.entering();
/* 566 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4BindingOutputOpMap());
/* 567 */     LOGGER.exiting();
/* 568 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationOutputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 573 */     LOGGER.entering();
/* 574 */     processAttributes((WSDLObject)operation, reader, getHandlers4BindingOutputOpMap());
/* 575 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 580 */     LOGGER.entering();
/* 581 */     boolean result = processSubelement((WSDLObject)fault, reader, getHandlers4BindingFaultOpMap());
/* 582 */     LOGGER.exiting(Boolean.valueOf(result));
/* 583 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationFaultAttributes(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 588 */     LOGGER.entering();
/* 589 */     processAttributes((WSDLObject)fault, reader, getHandlers4BindingFaultOpMap());
/* 590 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   private PolicyMapBuilder getPolicyMapBuilder() {
/* 595 */     if (null == this.policyBuilder) {
/* 596 */       this.policyBuilder = new PolicyMapBuilder();
/*     */     }
/* 598 */     return this.policyBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<String> getPolicyURIs(Collection<PolicyRecordHandler> handlers, PolicySourceModelContext modelContext) throws PolicyException {
/* 603 */     Collection<String> result = new ArrayList<>(handlers.size());
/*     */     
/* 605 */     for (PolicyRecordHandler handler : handlers) {
/* 606 */       String policyUri = handler.handler;
/* 607 */       if (HandlerType.AnonymousPolicyId == handler.type) {
/* 608 */         PolicySourceModel policyModel = getAnonymousPolicyModels().get(policyUri);
/* 609 */         policyModel.expand(modelContext);
/* 610 */         while (getPolicyModels().containsKey(policyUri)) {
/* 611 */           policyUri = AnonymnousPolicyIdPrefix.append(this.anonymousPoliciesCount++).toString();
/*     */         }
/* 613 */         getPolicyModels().put(policyUri, policyModel);
/*     */       } 
/* 615 */       result.add(policyUri);
/*     */     } 
/* 617 */     return result;
/*     */   }
/*     */   
/*     */   private boolean readExternalFile(String fileUrl) {
/* 621 */     InputStream ios = null;
/* 622 */     XMLStreamReader reader = null;
/*     */     try {
/* 624 */       URL xmlURL = new URL(fileUrl);
/* 625 */       ios = xmlURL.openStream();
/* 626 */       reader = XmlUtil.newXMLInputFactory(true).createXMLStreamReader(ios);
/* 627 */       while (reader.hasNext()) {
/* 628 */         if (reader.isStartElement() && NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/* 629 */           readSinglePolicy(this.policyReader.readPolicyElement(reader, fileUrl), false);
/*     */         }
/* 631 */         reader.next();
/*     */       } 
/* 633 */       return true;
/* 634 */     } catch (IOException ioe) {
/* 635 */       return false;
/* 636 */     } catch (XMLStreamException xmlse) {
/* 637 */       return false;
/*     */     } finally {
/* 639 */       PolicyUtils.IO.closeResource(reader);
/* 640 */       PolicyUtils.IO.closeResource(ios);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 646 */     LOGGER.entering(new Object[] { context });
/*     */     
/* 648 */     if (null != this.expandQueueHead) {
/* 649 */       List<String> externalUris = getUnresolvedUris(false);
/* 650 */       getUnresolvedUris(true);
/* 651 */       LinkedList<String> baseUnresolvedUris = new LinkedList<>();
/* 652 */       for (SafePolicyReader.PolicyRecord currentRec = this.expandQueueHead; null != currentRec; currentRec = currentRec.next) {
/* 653 */         baseUnresolvedUris.addFirst(currentRec.getUri());
/*     */       }
/* 655 */       getUnresolvedUris(false).addAll(baseUnresolvedUris);
/* 656 */       this.expandQueueHead = null;
/* 657 */       getUnresolvedUris(false).addAll(externalUris);
/*     */     } 
/*     */     
/* 660 */     while (!getUnresolvedUris(false).isEmpty()) {
/* 661 */       List<String> urisToBeSolvedList = getUnresolvedUris(false);
/* 662 */       getUnresolvedUris(true);
/* 663 */       for (String currentUri : urisToBeSolvedList) {
/* 664 */         if (!isPolicyProcessed(currentUri)) {
/* 665 */           SafePolicyReader.PolicyRecord prefetchedRecord = getPolicyRecordsPassedBy().get(currentUri);
/* 666 */           if (null == prefetchedRecord) {
/* 667 */             if (this.policyReader.getUrlsRead().contains(getBaseUrl(currentUri))) {
/* 668 */               LOGGER.logSevereException((Throwable)new PolicyException(PolicyMessages.WSP_1014_CAN_NOT_FIND_POLICY(currentUri))); continue;
/*     */             } 
/* 670 */             if (readExternalFile(getBaseUrl(currentUri))) {
/* 671 */               getUnresolvedUris(false).add(currentUri);
/*     */             }
/*     */             continue;
/*     */           } 
/* 675 */           if (null != prefetchedRecord.unresolvedURIs) {
/* 676 */             getUnresolvedUris(false).addAll(prefetchedRecord.unresolvedURIs);
/*     */           }
/* 678 */           addNewPolicyNeeded(currentUri, prefetchedRecord.policyModel);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 683 */     PolicySourceModelContext modelContext = PolicySourceModelContext.createContext();
/* 684 */     for (String policyUri : this.urisNeeded) {
/* 685 */       PolicySourceModel sourceModel = this.modelsNeeded.get(policyUri);
/*     */       try {
/* 687 */         sourceModel.expand(modelContext);
/* 688 */         modelContext.addModel(new URI(policyUri), sourceModel);
/* 689 */       } catch (URISyntaxException e) {
/* 690 */         LOGGER.logSevereException(e);
/* 691 */       } catch (PolicyException e) {
/* 692 */         LOGGER.logSevereException((Throwable)e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 702 */       HashSet<BuilderHandlerMessageScope> messageSet = new HashSet<>();
/* 703 */       for (EditableWSDLService service : context.getWSDLModel().getServices().values()) {
/* 704 */         if (getHandlers4ServiceMap().containsKey(service)) {
/* 705 */           getPolicyMapBuilder().registerHandler(new BuilderHandlerServiceScope(
/* 706 */                 getPolicyURIs(getHandlers4ServiceMap().get(service), modelContext), 
/* 707 */                 getPolicyModels(), service, service
/*     */                 
/* 709 */                 .getName()));
/*     */         }
/*     */ 
/*     */         
/* 713 */         for (EditableWSDLPort port : service.getPorts()) {
/* 714 */           if (getHandlers4PortMap().containsKey(port)) {
/* 715 */             getPolicyMapBuilder().registerHandler(new BuilderHandlerEndpointScope(
/*     */                   
/* 717 */                   getPolicyURIs(getHandlers4PortMap().get(port), modelContext), 
/* 718 */                   getPolicyModels(), port, port
/*     */                   
/* 720 */                   .getOwner().getName(), port
/* 721 */                   .getName()));
/*     */           }
/* 723 */           if (null != port
/* 724 */             .getBinding()) {
/*     */             
/* 726 */             if (getHandlers4BindingMap().containsKey(port.getBinding())) {
/* 727 */               getPolicyMapBuilder()
/* 728 */                 .registerHandler(new BuilderHandlerEndpointScope(
/*     */                     
/* 730 */                     getPolicyURIs(getHandlers4BindingMap().get(port.getBinding()), modelContext), 
/* 731 */                     getPolicyModels(), port
/* 732 */                     .getBinding(), service
/* 733 */                     .getName(), port
/* 734 */                     .getName()));
/*     */             }
/*     */             
/* 737 */             if (getHandlers4PortTypeMap().containsKey(port.getBinding().getPortType())) {
/* 738 */               getPolicyMapBuilder()
/* 739 */                 .registerHandler(new BuilderHandlerEndpointScope(
/*     */                     
/* 741 */                     getPolicyURIs(getHandlers4PortTypeMap().get(port.getBinding().getPortType()), modelContext), 
/* 742 */                     getPolicyModels(), port
/* 743 */                     .getBinding().getPortType(), service
/* 744 */                     .getName(), port
/* 745 */                     .getName()));
/*     */             }
/*     */ 
/*     */             
/* 749 */             for (EditableWSDLBoundOperation boundOperation : port.getBinding().getBindingOperations()) {
/*     */               
/* 751 */               EditableWSDLOperation operation = boundOperation.getOperation();
/* 752 */               QName operationName = new QName(boundOperation.getBoundPortType().getName().getNamespaceURI(), boundOperation.getName().getLocalPart());
/*     */ 
/*     */               
/* 755 */               if (getHandlers4BoundOperationMap().containsKey(boundOperation)) {
/* 756 */                 getPolicyMapBuilder()
/* 757 */                   .registerHandler(new BuilderHandlerOperationScope(
/*     */                       
/* 759 */                       getPolicyURIs(getHandlers4BoundOperationMap().get(boundOperation), modelContext), 
/* 760 */                       getPolicyModels(), boundOperation, service
/*     */                       
/* 762 */                       .getName(), port
/* 763 */                       .getName(), operationName));
/*     */               }
/*     */ 
/*     */               
/* 767 */               if (getHandlers4OperationMap().containsKey(operation)) {
/* 768 */                 getPolicyMapBuilder()
/* 769 */                   .registerHandler(new BuilderHandlerOperationScope(
/*     */                       
/* 771 */                       getPolicyURIs(getHandlers4OperationMap().get(operation), modelContext), 
/* 772 */                       getPolicyModels(), operation, service
/*     */                       
/* 774 */                       .getName(), port
/* 775 */                       .getName(), operationName));
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 780 */               EditableWSDLInput input = operation.getInput();
/* 781 */               if (null != input) {
/* 782 */                 EditableWSDLMessage inputMsg = input.getMessage();
/* 783 */                 if (inputMsg != null && getHandlers4MessageMap().containsKey(inputMsg)) {
/* 784 */                   messageSet.add(new BuilderHandlerMessageScope(
/* 785 */                         getPolicyURIs(
/* 786 */                           getHandlers4MessageMap().get(inputMsg), modelContext), 
/* 787 */                         getPolicyModels(), inputMsg, BuilderHandlerMessageScope.Scope.InputMessageScope, service
/*     */ 
/*     */                         
/* 790 */                         .getName(), port
/* 791 */                         .getName(), operationName, null));
/*     */                 }
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 798 */               if (getHandlers4BindingInputOpMap().containsKey(boundOperation)) {
/* 799 */                 getPolicyMapBuilder()
/* 800 */                   .registerHandler(new BuilderHandlerMessageScope(
/*     */                       
/* 802 */                       getPolicyURIs(getHandlers4BindingInputOpMap().get(boundOperation), modelContext), 
/* 803 */                       getPolicyModels(), boundOperation, BuilderHandlerMessageScope.Scope.InputMessageScope, service
/*     */ 
/*     */                       
/* 806 */                       .getName(), port
/* 807 */                       .getName(), operationName, null));
/*     */               }
/*     */ 
/*     */               
/* 811 */               if (null != input && 
/* 812 */                 getHandlers4InputMap().containsKey(input)) {
/* 813 */                 getPolicyMapBuilder()
/* 814 */                   .registerHandler(new BuilderHandlerMessageScope(
/*     */                       
/* 816 */                       getPolicyURIs(getHandlers4InputMap().get(input), modelContext), 
/* 817 */                       getPolicyModels(), input, BuilderHandlerMessageScope.Scope.InputMessageScope, service
/*     */ 
/*     */                       
/* 820 */                       .getName(), port
/* 821 */                       .getName(), operationName, null));
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 827 */               EditableWSDLOutput output = operation.getOutput();
/* 828 */               if (null != output) {
/* 829 */                 EditableWSDLMessage outputMsg = output.getMessage();
/* 830 */                 if (outputMsg != null && getHandlers4MessageMap().containsKey(outputMsg)) {
/* 831 */                   messageSet.add(new BuilderHandlerMessageScope(
/* 832 */                         getPolicyURIs(
/* 833 */                           getHandlers4MessageMap().get(outputMsg), modelContext), 
/* 834 */                         getPolicyModels(), outputMsg, BuilderHandlerMessageScope.Scope.OutputMessageScope, service
/*     */ 
/*     */                         
/* 837 */                         .getName(), port
/* 838 */                         .getName(), operationName, null));
/*     */                 }
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 845 */               if (getHandlers4BindingOutputOpMap().containsKey(boundOperation)) {
/* 846 */                 getPolicyMapBuilder()
/* 847 */                   .registerHandler(new BuilderHandlerMessageScope(
/*     */                       
/* 849 */                       getPolicyURIs(getHandlers4BindingOutputOpMap().get(boundOperation), modelContext), 
/* 850 */                       getPolicyModels(), boundOperation, BuilderHandlerMessageScope.Scope.OutputMessageScope, service
/*     */ 
/*     */                       
/* 853 */                       .getName(), port
/* 854 */                       .getName(), operationName, null));
/*     */               }
/*     */ 
/*     */               
/* 858 */               if (null != output && 
/* 859 */                 getHandlers4OutputMap().containsKey(output)) {
/* 860 */                 getPolicyMapBuilder()
/* 861 */                   .registerHandler(new BuilderHandlerMessageScope(
/*     */                       
/* 863 */                       getPolicyURIs(getHandlers4OutputMap().get(output), modelContext), 
/* 864 */                       getPolicyModels(), output, BuilderHandlerMessageScope.Scope.OutputMessageScope, service
/*     */ 
/*     */                       
/* 867 */                       .getName(), port
/* 868 */                       .getName(), operationName, null));
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 874 */               for (EditableWSDLBoundFault boundFault : boundOperation.getFaults()) {
/* 875 */                 EditableWSDLFault fault = boundFault.getFault();
/*     */ 
/*     */ 
/*     */                 
/* 879 */                 if (fault == null) {
/* 880 */                   LOGGER.warning(PolicyMessages.WSP_1021_FAULT_NOT_BOUND(boundFault.getName()));
/*     */                   
/*     */                   continue;
/*     */                 } 
/* 884 */                 EditableWSDLMessage faultMessage = fault.getMessage();
/* 885 */                 QName faultName = new QName(boundOperation.getBoundPortType().getName().getNamespaceURI(), boundFault.getName());
/*     */                 
/* 887 */                 if (faultMessage != null && getHandlers4MessageMap().containsKey(faultMessage)) {
/* 888 */                   messageSet.add(new BuilderHandlerMessageScope(
/*     */                         
/* 890 */                         getPolicyURIs(getHandlers4MessageMap().get(faultMessage), modelContext), 
/* 891 */                         getPolicyModels(), new WSDLBoundFaultContainer((WSDLBoundFault)boundFault, (WSDLBoundOperation)boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service
/*     */ 
/*     */                         
/* 894 */                         .getName(), port
/* 895 */                         .getName(), operationName, faultName));
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/* 900 */                 if (getHandlers4FaultMap().containsKey(fault)) {
/* 901 */                   messageSet.add(new BuilderHandlerMessageScope(
/*     */                         
/* 903 */                         getPolicyURIs(getHandlers4FaultMap().get(fault), modelContext), 
/* 904 */                         getPolicyModels(), new WSDLBoundFaultContainer((WSDLBoundFault)boundFault, (WSDLBoundOperation)boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service
/*     */ 
/*     */                         
/* 907 */                         .getName(), port
/* 908 */                         .getName(), operationName, faultName));
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/* 913 */                 if (getHandlers4BindingFaultOpMap().containsKey(boundFault)) {
/* 914 */                   messageSet.add(new BuilderHandlerMessageScope(
/*     */                         
/* 916 */                         getPolicyURIs(getHandlers4BindingFaultOpMap().get(boundFault), modelContext), 
/* 917 */                         getPolicyModels(), new WSDLBoundFaultContainer((WSDLBoundFault)boundFault, (WSDLBoundOperation)boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service
/*     */ 
/*     */                         
/* 920 */                         .getName(), port
/* 921 */                         .getName(), operationName, faultName));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 934 */       for (BuilderHandlerMessageScope scopeHandler : messageSet) {
/* 935 */         getPolicyMapBuilder().registerHandler(scopeHandler);
/*     */       }
/* 937 */     } catch (PolicyException e) {
/* 938 */       LOGGER.logSevereException((Throwable)e);
/*     */     } 
/*     */ 
/*     */     
/* 942 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {
/*     */     PolicyMap effectiveMap;
/* 950 */     EditableWSDLModel wsdlModel = context.getWSDLModel();
/*     */     
/*     */     try {
/* 953 */       if (context.isClientSide()) {
/* 954 */         effectiveMap = context.getPolicyResolver().resolve(new PolicyResolver.ClientContext(this.policyBuilder.getPolicyMap(new com.sun.xml.internal.ws.policy.PolicyMapMutator[0]), context.getContainer()));
/*     */       } else {
/* 956 */         effectiveMap = context.getPolicyResolver().resolve(new PolicyResolver.ServerContext(this.policyBuilder.getPolicyMap(new com.sun.xml.internal.ws.policy.PolicyMapMutator[0]), context.getContainer(), null, new com.sun.xml.internal.ws.policy.PolicyMapMutator[0]));
/* 957 */       }  wsdlModel.setPolicyMap(effectiveMap);
/* 958 */     } catch (PolicyException e) {
/* 959 */       LOGGER.logSevereException((Throwable)e);
/* 960 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1007_POLICY_EXCEPTION_WHILE_FINISHING_PARSING_WSDL(), e));
/*     */     } 
/*     */     try {
/* 963 */       PolicyUtil.configureModel((WSDLModel)wsdlModel, effectiveMap);
/* 964 */     } catch (PolicyException e) {
/* 965 */       LOGGER.logSevereException((Throwable)e);
/* 966 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1012_FAILED_CONFIGURE_WSDL_MODEL(), e));
/*     */     } 
/* 968 */     LOGGER.exiting();
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
/*     */   private String[] getPolicyURIsFromAttr(XMLStreamReader reader) {
/* 980 */     StringBuilder policyUriBuffer = new StringBuilder();
/* 981 */     for (NamespaceVersion version : NamespaceVersion.values()) {
/* 982 */       String value = reader.getAttributeValue(version.toString(), XmlToken.PolicyUris.toString());
/* 983 */       if (value != null) {
/* 984 */         policyUriBuffer.append(value).append(" ");
/*     */       }
/*     */     } 
/* 987 */     return (policyUriBuffer.length() > 0) ? policyUriBuffer.toString().split("[\\n ]+") : null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/policy/jaxws/PolicyWSDLParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */