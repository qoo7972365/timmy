/*      */ package com.sun.xml.internal.bind.v2.runtime;
/*      */ 
/*      */ import com.sun.istack.internal.SAXException2;
/*      */ import com.sun.xml.internal.bind.CycleRecoverable;
/*      */ import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
/*      */ import com.sun.xml.internal.bind.util.ValidationEventLocatorExImpl;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.MTOMXmlOutput;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.XmlOutput;
/*      */ import com.sun.xml.internal.bind.v2.runtime.property.Property;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntData;
/*      */ import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.activation.MimeType;
/*      */ import javax.xml.bind.DatatypeConverter;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.ValidationEvent;
/*      */ import javax.xml.bind.ValidationEventHandler;
/*      */ import javax.xml.bind.ValidationEventLocator;
/*      */ import javax.xml.bind.annotation.DomHandler;
/*      */ import javax.xml.bind.annotation.XmlNs;
/*      */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*      */ import javax.xml.bind.helpers.NotIdentifiableEventImpl;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.sax.SAXResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XMLSerializer
/*      */   extends Coordinator
/*      */ {
/*      */   public final JAXBContextImpl grammar;
/*      */   private XmlOutput out;
/*      */   public final NameList nameList;
/*      */   public final int[] knownUri2prefixIndexMap;
/*      */   private final NamespaceContextImpl nsContext;
/*      */   private NamespaceContextImpl.Element nse;
/*  134 */   ThreadLocal<Property> currentProperty = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean textHasAlreadyPrinted = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean seenRoot = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private final MarshallerImpl marshaller;
/*      */ 
/*      */ 
/*      */   
/*  151 */   private final Set<Object> idReferencedObjects = new HashSet();
/*      */ 
/*      */   
/*  154 */   private final Set<Object> objectsWithId = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   private final CollisionCheckStack<Object> cycleDetectionStack = new CollisionCheckStack();
/*      */ 
/*      */ 
/*      */   
/*      */   private String schemaLocation;
/*      */ 
/*      */   
/*      */   private String noNsSchemaLocation;
/*      */ 
/*      */   
/*      */   private Transformer identityTransformer;
/*      */ 
/*      */   
/*      */   private ContentHandlerAdaptor contentHandlerAdapter;
/*      */ 
/*      */   
/*      */   private boolean fragment;
/*      */ 
/*      */   
/*      */   private Base64Data base64Data;
/*      */ 
/*      */   
/*  182 */   private final IntData intData = new IntData();
/*      */   public AttachmentMarshaller attachmentMarshaller;
/*      */   private MimeType expectedMimeType;
/*      */   
/*      */   XMLSerializer(MarshallerImpl _owner) {
/*  187 */     this.marshaller = _owner;
/*  188 */     this.grammar = this.marshaller.context;
/*  189 */     this.nsContext = new NamespaceContextImpl(this);
/*  190 */     this.nameList = this.marshaller.context.nameList;
/*  191 */     this.knownUri2prefixIndexMap = new int[this.nameList.namespaceURIs.length];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inlineBinaryFlag;
/*      */   
/*      */   private QName schemaType;
/*      */ 
/*      */   
/*      */   public Base64Data getCachedBase64DataInstance() {
/*  202 */     return new Base64Data();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getIdFromObject(Object identifiableObject) throws SAXException, JAXBException {
/*  209 */     return this.grammar.getBeanInfo(identifiableObject, true).getId(identifiableObject, this);
/*      */   }
/*      */   
/*      */   private void handleMissingObjectError(String fieldName) throws SAXException, IOException, XMLStreamException {
/*  213 */     reportMissingObjectError(fieldName);
/*      */ 
/*      */     
/*  216 */     endNamespaceDecls((Object)null);
/*  217 */     endAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reportError(ValidationEvent ve) throws SAXException {
/*      */     ValidationEventHandler handler;
/*      */     try {
/*  225 */       handler = this.marshaller.getEventHandler();
/*  226 */     } catch (JAXBException e) {
/*  227 */       throw new SAXException2(e);
/*      */     } 
/*      */     
/*  230 */     if (!handler.handleEvent(ve)) {
/*  231 */       if (ve.getLinkedException() instanceof Exception) {
/*  232 */         throw new SAXException2((Exception)ve.getLinkedException());
/*      */       }
/*  234 */       throw new SAXException2(ve.getMessage());
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
/*      */   public final void reportError(String fieldName, Throwable t) throws SAXException {
/*  246 */     ValidationEventImpl validationEventImpl = new ValidationEventImpl(1, t.getMessage(), getCurrentLocation(fieldName), t);
/*  247 */     reportError((ValidationEvent)validationEventImpl);
/*      */   }
/*      */   
/*      */   public void startElement(Name tagName, Object outerPeer) {
/*  251 */     startElement();
/*  252 */     this.nse.setTagName(tagName, outerPeer);
/*      */   }
/*      */   
/*      */   public void startElement(String nsUri, String localName, String preferredPrefix, Object outerPeer) {
/*  256 */     startElement();
/*  257 */     int idx = this.nsContext.declareNsUri(nsUri, preferredPrefix, false);
/*  258 */     this.nse.setTagName(idx, localName, outerPeer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElementForce(String nsUri, String localName, String forcedPrefix, Object outerPeer) {
/*  266 */     startElement();
/*  267 */     int idx = this.nsContext.force(nsUri, forcedPrefix);
/*  268 */     this.nse.setTagName(idx, localName, outerPeer);
/*      */   }
/*      */   
/*      */   public void endNamespaceDecls(Object innerPeer) throws IOException, XMLStreamException {
/*  272 */     this.nsContext.collectionMode = false;
/*  273 */     this.nse.startElement(this.out, innerPeer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endAttributes() throws SAXException, IOException, XMLStreamException {
/*  281 */     if (!this.seenRoot) {
/*  282 */       this.seenRoot = true;
/*  283 */       if (this.schemaLocation != null || this.noNsSchemaLocation != null) {
/*  284 */         int p = this.nsContext.getPrefixIndex("http://www.w3.org/2001/XMLSchema-instance");
/*  285 */         if (this.schemaLocation != null)
/*  286 */           this.out.attribute(p, "schemaLocation", this.schemaLocation); 
/*  287 */         if (this.noNsSchemaLocation != null) {
/*  288 */           this.out.attribute(p, "noNamespaceSchemaLocation", this.noNsSchemaLocation);
/*      */         }
/*      */       } 
/*      */     } 
/*  292 */     this.out.endStartTag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endElement() throws SAXException, IOException, XMLStreamException {
/*  300 */     this.nse.endElement(this.out);
/*  301 */     this.nse = this.nse.pop();
/*  302 */     this.textHasAlreadyPrinted = false;
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, String data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  306 */     if (this.seenRoot) {
/*  307 */       this.textHasAlreadyPrinted = false;
/*  308 */       this.nse = this.nse.push();
/*  309 */       this.out.beginStartTag(tagName);
/*  310 */       this.out.endStartTag();
/*  311 */       if (data != null)
/*      */         try {
/*  313 */           this.out.text(data, false);
/*  314 */         } catch (IllegalArgumentException e) {
/*  315 */           throw new IllegalArgumentException(Messages.ILLEGAL_CONTENT.format(new Object[] { fieldName, e.getMessage() }));
/*      */         }  
/*  317 */       this.out.endTag(tagName);
/*  318 */       this.nse = this.nse.pop();
/*      */     }
/*      */     else {
/*      */       
/*  322 */       startElement(tagName, (Object)null);
/*  323 */       endNamespaceDecls((Object)null);
/*  324 */       endAttributes();
/*      */       try {
/*  326 */         this.out.text(data, false);
/*  327 */       } catch (IllegalArgumentException e) {
/*  328 */         throw new IllegalArgumentException(Messages.ILLEGAL_CONTENT.format(new Object[] { fieldName, e.getMessage() }));
/*      */       } 
/*  330 */       endElement();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, Pcdata data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  335 */     if (this.seenRoot) {
/*  336 */       this.textHasAlreadyPrinted = false;
/*  337 */       this.nse = this.nse.push();
/*  338 */       this.out.beginStartTag(tagName);
/*  339 */       this.out.endStartTag();
/*  340 */       if (data != null)
/*  341 */         this.out.text(data, false); 
/*  342 */       this.out.endTag(tagName);
/*  343 */       this.nse = this.nse.pop();
/*      */     }
/*      */     else {
/*      */       
/*  347 */       startElement(tagName, (Object)null);
/*  348 */       endNamespaceDecls((Object)null);
/*  349 */       endAttributes();
/*  350 */       this.out.text(data, false);
/*  351 */       endElement();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void leafElement(Name tagName, int data, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  356 */     this.intData.reset(data);
/*  357 */     leafElement(tagName, (Pcdata)this.intData, fieldName);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void text(String text, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  386 */     if (text == null) {
/*  387 */       reportMissingObjectError(fieldName);
/*      */       
/*      */       return;
/*      */     } 
/*  391 */     this.out.text(text, this.textHasAlreadyPrinted);
/*  392 */     this.textHasAlreadyPrinted = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void text(Pcdata text, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  401 */     if (text == null) {
/*  402 */       reportMissingObjectError(fieldName);
/*      */       
/*      */       return;
/*      */     } 
/*  406 */     this.out.text(text, this.textHasAlreadyPrinted);
/*  407 */     this.textHasAlreadyPrinted = true;
/*      */   }
/*      */   
/*      */   public void attribute(String uri, String local, String value) throws SAXException {
/*      */     int prefix;
/*  412 */     if (uri.length() == 0) {
/*      */       
/*  414 */       prefix = -1;
/*      */     } else {
/*  416 */       prefix = this.nsContext.getPrefixIndex(uri);
/*      */     } 
/*      */     
/*      */     try {
/*  420 */       this.out.attribute(prefix, local, value);
/*  421 */     } catch (IOException e) {
/*  422 */       throw new SAXException2(e);
/*  423 */     } catch (XMLStreamException e) {
/*  424 */       throw new SAXException2(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void attribute(Name name, CharSequence value) throws IOException, XMLStreamException {
/*  431 */     this.out.attribute(name, value.toString());
/*      */   }
/*      */   
/*      */   public NamespaceContext2 getNamespaceContext() {
/*  435 */     return (NamespaceContext2)this.nsContext;
/*      */   }
/*      */ 
/*      */   
/*      */   public String onID(Object owner, String value) {
/*  440 */     this.objectsWithId.add(owner);
/*  441 */     return value;
/*      */   }
/*      */   
/*      */   public String onIDREF(Object obj) throws SAXException {
/*      */     String id;
/*      */     try {
/*  447 */       id = getIdFromObject(obj);
/*  448 */     } catch (JAXBException e) {
/*  449 */       reportError((String)null, (Throwable)e);
/*  450 */       return null;
/*      */     } 
/*  452 */     this.idReferencedObjects.add(obj);
/*  453 */     if (id == null) {
/*  454 */       reportError((ValidationEvent)new NotIdentifiableEventImpl(1, Messages.NOT_IDENTIFIABLE
/*      */             
/*  456 */             .format(new Object[0]), (ValidationEventLocator)new ValidationEventLocatorImpl(obj)));
/*      */     }
/*      */     
/*  459 */     return id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void childAsRoot(Object obj) throws JAXBException, IOException, SAXException, XMLStreamException {
/*  467 */     JaxBeanInfo<Object> beanInfo = this.grammar.getBeanInfo(obj, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  472 */     this.cycleDetectionStack.pushNocheck(obj);
/*      */     
/*  474 */     boolean lookForLifecycleMethods = beanInfo.lookForLifecycleMethods();
/*  475 */     if (lookForLifecycleMethods) {
/*  476 */       fireBeforeMarshalEvents(beanInfo, obj);
/*      */     }
/*      */     
/*  479 */     beanInfo.serializeRoot(obj, this);
/*      */     
/*  481 */     if (lookForLifecycleMethods) {
/*  482 */       fireAfterMarshalEvents(beanInfo, obj);
/*      */     }
/*      */     
/*  485 */     this.cycleDetectionStack.pop();
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
/*      */   private Object pushObject(Object obj, String fieldName) throws SAXException {
/*  501 */     if (!this.cycleDetectionStack.push(obj)) {
/*  502 */       return obj;
/*      */     }
/*      */     
/*  505 */     if (obj instanceof CycleRecoverable) {
/*  506 */       obj = ((CycleRecoverable)obj).onCycleDetected(new CycleRecoverable.Context() {
/*      */             public Marshaller getMarshaller() {
/*  508 */               return (Marshaller)XMLSerializer.this.marshaller;
/*      */             }
/*      */           });
/*  511 */       if (obj != null) {
/*      */ 
/*      */ 
/*      */         
/*  515 */         this.cycleDetectionStack.pop();
/*  516 */         return pushObject(obj, fieldName);
/*      */       } 
/*  518 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  522 */     reportError((ValidationEvent)new ValidationEventImpl(1, Messages.CYCLE_IN_MARSHALLER
/*      */           
/*  524 */           .format(new Object[] { this.cycleDetectionStack.getCycleString()
/*  525 */             }, ), getCurrentLocation(fieldName), null));
/*      */     
/*  527 */     return null;
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
/*      */   public final void childAsSoleContent(Object child, String fieldName) throws SAXException, IOException, XMLStreamException {
/*  546 */     if (child == null) {
/*  547 */       handleMissingObjectError(fieldName);
/*      */     } else {
/*  549 */       JaxBeanInfo<Object> beanInfo; child = pushObject(child, fieldName);
/*  550 */       if (child == null) {
/*      */         
/*  552 */         endNamespaceDecls((Object)null);
/*  553 */         endAttributes();
/*  554 */         this.cycleDetectionStack.pop();
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  559 */         beanInfo = this.grammar.getBeanInfo(child, true);
/*  560 */       } catch (JAXBException e) {
/*  561 */         reportError(fieldName, (Throwable)e);
/*      */         
/*  563 */         endNamespaceDecls((Object)null);
/*  564 */         endAttributes();
/*  565 */         this.cycleDetectionStack.pop();
/*      */         
/*      */         return;
/*      */       } 
/*  569 */       boolean lookForLifecycleMethods = beanInfo.lookForLifecycleMethods();
/*  570 */       if (lookForLifecycleMethods) {
/*  571 */         fireBeforeMarshalEvents(beanInfo, child);
/*      */       }
/*      */       
/*  574 */       beanInfo.serializeURIs(child, this);
/*  575 */       endNamespaceDecls(child);
/*  576 */       beanInfo.serializeAttributes(child, this);
/*  577 */       endAttributes();
/*  578 */       beanInfo.serializeBody(child, this);
/*      */       
/*  580 */       if (lookForLifecycleMethods) {
/*  581 */         fireAfterMarshalEvents(beanInfo, child);
/*      */       }
/*      */       
/*  584 */       this.cycleDetectionStack.pop();
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
/*      */   public final void childAsXsiType(Object child, String fieldName, JaxBeanInfo<Object> expected, boolean nillable) throws SAXException, IOException, XMLStreamException {
/*  613 */     if (child == null) {
/*  614 */       handleMissingObjectError(fieldName);
/*      */     } else {
/*  616 */       child = pushObject(child, fieldName);
/*  617 */       if (child == null) {
/*  618 */         endNamespaceDecls((Object)null);
/*  619 */         endAttributes();
/*      */         
/*      */         return;
/*      */       } 
/*  623 */       boolean asExpected = (child.getClass() == expected.jaxbType);
/*  624 */       JaxBeanInfo<Object> actual = expected;
/*  625 */       QName actualTypeName = null;
/*      */       
/*  627 */       if (asExpected && actual.lookForLifecycleMethods()) {
/*  628 */         fireBeforeMarshalEvents(actual, child);
/*      */       }
/*      */       
/*  631 */       if (!asExpected) {
/*      */         try {
/*  633 */           actual = this.grammar.getBeanInfo(child, true);
/*  634 */           if (actual.lookForLifecycleMethods()) {
/*  635 */             fireBeforeMarshalEvents(actual, child);
/*      */           }
/*  637 */         } catch (JAXBException e) {
/*  638 */           reportError(fieldName, (Throwable)e);
/*  639 */           endNamespaceDecls((Object)null);
/*  640 */           endAttributes();
/*      */           return;
/*      */         } 
/*  643 */         if (actual == expected) {
/*  644 */           asExpected = true;
/*      */         } else {
/*  646 */           actualTypeName = actual.getTypeName(child);
/*  647 */           if (actualTypeName == null) {
/*  648 */             reportError((ValidationEvent)new ValidationEventImpl(1, Messages.SUBSTITUTED_BY_ANONYMOUS_TYPE
/*      */                   
/*  650 */                   .format(new Object[] {
/*  651 */                       expected.jaxbType.getName(), child
/*  652 */                       .getClass().getName(), actual.jaxbType
/*  653 */                       .getName()
/*  654 */                     }, ), getCurrentLocation(fieldName)));
/*      */           } else {
/*      */             
/*  657 */             getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*  658 */             getNamespaceContext().declareNamespace(actualTypeName.getNamespaceURI(), null, false);
/*      */           } 
/*      */         } 
/*      */       } 
/*  662 */       actual.serializeURIs(child, this);
/*      */       
/*  664 */       if (nillable) {
/*  665 */         getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*      */       }
/*      */       
/*  668 */       endNamespaceDecls(child);
/*  669 */       if (!asExpected) {
/*  670 */         attribute("http://www.w3.org/2001/XMLSchema-instance", "type", 
/*  671 */             DatatypeConverter.printQName(actualTypeName, getNamespaceContext()));
/*      */       }
/*      */       
/*  674 */       actual.serializeAttributes(child, this);
/*  675 */       boolean nilDefined = actual.isNilIncluded();
/*  676 */       if (nillable && !nilDefined) {
/*  677 */         attribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "true");
/*      */       }
/*      */       
/*  680 */       endAttributes();
/*  681 */       actual.serializeBody(child, this);
/*      */       
/*  683 */       if (actual.lookForLifecycleMethods()) {
/*  684 */         fireAfterMarshalEvents(actual, child);
/*      */       }
/*      */       
/*  687 */       this.cycleDetectionStack.pop();
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
/*      */   private void fireAfterMarshalEvents(JaxBeanInfo beanInfo, Object currentTarget) {
/*  702 */     if (beanInfo.hasAfterMarshalMethod()) {
/*  703 */       Method m = (beanInfo.getLifecycleMethods()).afterMarshal;
/*  704 */       fireMarshalEvent(currentTarget, m);
/*      */     } 
/*      */ 
/*      */     
/*  708 */     Marshaller.Listener externalListener = this.marshaller.getListener();
/*  709 */     if (externalListener != null) {
/*  710 */       externalListener.afterMarshal(currentTarget);
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
/*      */   private void fireBeforeMarshalEvents(JaxBeanInfo beanInfo, Object currentTarget) {
/*  726 */     if (beanInfo.hasBeforeMarshalMethod()) {
/*  727 */       Method m = (beanInfo.getLifecycleMethods()).beforeMarshal;
/*  728 */       fireMarshalEvent(currentTarget, m);
/*      */     } 
/*      */ 
/*      */     
/*  732 */     Marshaller.Listener externalListener = this.marshaller.getListener();
/*  733 */     if (externalListener != null) {
/*  734 */       externalListener.beforeMarshal(currentTarget);
/*      */     }
/*      */   }
/*      */   
/*      */   private void fireMarshalEvent(Object target, Method m) {
/*      */     try {
/*  740 */       m.invoke(target, new Object[] { this.marshaller });
/*  741 */     } catch (Exception e) {
/*      */       
/*  743 */       throw new IllegalStateException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void attWildcardAsURIs(Map<QName, String> attributes, String fieldName) {
/*  748 */     if (attributes == null)
/*  749 */       return;  for (Map.Entry<QName, String> e : attributes.entrySet()) {
/*  750 */       QName n = e.getKey();
/*  751 */       String nsUri = n.getNamespaceURI();
/*  752 */       if (nsUri.length() > 0) {
/*  753 */         String p = n.getPrefix();
/*  754 */         if (p.length() == 0) p = null; 
/*  755 */         this.nsContext.declareNsUri(nsUri, p, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void attWildcardAsAttributes(Map<QName, String> attributes, String fieldName) throws SAXException {
/*  761 */     if (attributes == null)
/*  762 */       return;  for (Map.Entry<QName, String> e : attributes.entrySet()) {
/*  763 */       QName n = e.getKey();
/*  764 */       attribute(n.getNamespaceURI(), n.getLocalPart(), e.getValue());
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
/*      */   public final void writeXsiNilTrue() throws SAXException, IOException, XMLStreamException {
/*  779 */     getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*  780 */     endNamespaceDecls((Object)null);
/*  781 */     attribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "true");
/*  782 */     endAttributes();
/*      */   }
/*      */   
/*      */   public <E> void writeDom(E element, DomHandler<E, ?> domHandler, Object parentBean, String fieldName) throws SAXException {
/*  786 */     Source source = domHandler.marshal(element, this);
/*  787 */     if (this.contentHandlerAdapter == null)
/*  788 */       this.contentHandlerAdapter = new ContentHandlerAdaptor(this); 
/*      */     try {
/*  790 */       getIdentityTransformer().transform(source, new SAXResult(this.contentHandlerAdapter));
/*  791 */     } catch (TransformerException e) {
/*  792 */       reportError(fieldName, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Transformer getIdentityTransformer() {
/*  797 */     if (this.identityTransformer == null)
/*  798 */       this.identityTransformer = JAXBContextImpl.createTransformer(this.grammar.disableSecurityProcessing); 
/*  799 */     return this.identityTransformer;
/*      */   }
/*      */   
/*      */   public void setPrefixMapper(NamespacePrefixMapper prefixMapper) {
/*  803 */     this.nsContext.setPrefixMapper(prefixMapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startDocument(XmlOutput out, boolean fragment, String schemaLocation, String noNsSchemaLocation) throws IOException, SAXException, XMLStreamException {
/*      */     MTOMXmlOutput mTOMXmlOutput;
/*  815 */     pushCoordinator();
/*  816 */     this.nsContext.reset();
/*  817 */     this.nse = this.nsContext.getCurrent();
/*  818 */     if (this.attachmentMarshaller != null && this.attachmentMarshaller.isXOPPackage())
/*  819 */       mTOMXmlOutput = new MTOMXmlOutput(out); 
/*  820 */     this.out = (XmlOutput)mTOMXmlOutput;
/*  821 */     this.objectsWithId.clear();
/*  822 */     this.idReferencedObjects.clear();
/*  823 */     this.textHasAlreadyPrinted = false;
/*  824 */     this.seenRoot = false;
/*  825 */     this.schemaLocation = schemaLocation;
/*  826 */     this.noNsSchemaLocation = noNsSchemaLocation;
/*  827 */     this.fragment = fragment;
/*  828 */     this.inlineBinaryFlag = false;
/*  829 */     this.expectedMimeType = null;
/*  830 */     this.cycleDetectionStack.reset();
/*      */     
/*  832 */     mTOMXmlOutput.startDocument(this, fragment, this.knownUri2prefixIndexMap, this.nsContext);
/*      */   }
/*      */   
/*      */   public void endDocument() throws IOException, SAXException, XMLStreamException {
/*  836 */     this.out.endDocument(this.fragment);
/*      */   }
/*      */   
/*      */   public void close() {
/*  840 */     this.out = null;
/*  841 */     clearCurrentProperty();
/*  842 */     popCoordinator();
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
/*      */   public void addInscopeBinding(String nsUri, String prefix) {
/*  855 */     this.nsContext.put(nsUri, prefix);
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
/*      */   public String getXMIMEContentType() {
/*  869 */     String v = this.grammar.getXMIMEContentType(this.cycleDetectionStack.peek());
/*  870 */     if (v != null) return v;
/*      */ 
/*      */     
/*  873 */     if (this.expectedMimeType != null) {
/*  874 */       return this.expectedMimeType.toString();
/*      */     }
/*  876 */     return null;
/*      */   }
/*      */   
/*      */   private void startElement() {
/*  880 */     this.nse = this.nse.push();
/*      */     
/*  882 */     if (!this.seenRoot) {
/*      */       
/*  884 */       if (this.grammar.getXmlNsSet() != null) {
/*  885 */         for (XmlNs xmlNs : this.grammar.getXmlNsSet()) {
/*  886 */           this.nsContext.declareNsUri(xmlNs
/*  887 */               .namespaceURI(), 
/*  888 */               (xmlNs.prefix() == null) ? "" : xmlNs.prefix(), 
/*  889 */               (xmlNs.prefix() != null));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*  894 */       String[] knownUris = this.nameList.namespaceURIs;
/*  895 */       for (int i = 0; i < knownUris.length; i++) {
/*  896 */         this.knownUri2prefixIndexMap[i] = this.nsContext.declareNsUri(knownUris[i], null, this.nameList.nsUriCannotBeDefaulted[i]);
/*      */       }
/*      */ 
/*      */       
/*  900 */       String[] uris = this.nsContext.getPrefixMapper().getPreDeclaredNamespaceUris();
/*  901 */       if (uris != null)
/*  902 */         for (String uri : uris) {
/*  903 */           if (uri != null) {
/*  904 */             this.nsContext.declareNsUri(uri, null, false);
/*      */           }
/*      */         }  
/*  907 */       String[] pairs = this.nsContext.getPrefixMapper().getPreDeclaredNamespaceUris2();
/*  908 */       if (pairs != null) {
/*  909 */         for (int j = 0; j < pairs.length; j += 2) {
/*  910 */           String prefix = pairs[j];
/*  911 */           String nsUri = pairs[j + 1];
/*  912 */           if (prefix != null && nsUri != null)
/*      */           {
/*      */ 
/*      */             
/*  916 */             this.nsContext.put(nsUri, prefix);
/*      */           }
/*      */         } 
/*      */       }
/*  920 */       if (this.schemaLocation != null || this.noNsSchemaLocation != null) {
/*  921 */         this.nsContext.declareNsUri("http://www.w3.org/2001/XMLSchema-instance", "xsi", true);
/*      */       }
/*      */     } 
/*      */     
/*  925 */     this.nsContext.collectionMode = true;
/*  926 */     this.textHasAlreadyPrinted = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MimeType setExpectedMimeType(MimeType expectedMimeType) {
/*  936 */     MimeType old = this.expectedMimeType;
/*  937 */     this.expectedMimeType = expectedMimeType;
/*  938 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setInlineBinaryFlag(boolean value) {
/*  947 */     boolean old = this.inlineBinaryFlag;
/*  948 */     this.inlineBinaryFlag = value;
/*  949 */     return old;
/*      */   }
/*      */   
/*      */   public boolean getInlineBinaryFlag() {
/*  953 */     return this.inlineBinaryFlag;
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
/*      */   public QName setSchemaType(QName st) {
/*  968 */     QName old = this.schemaType;
/*  969 */     this.schemaType = st;
/*  970 */     return old;
/*      */   }
/*      */   
/*      */   public QName getSchemaType() {
/*  974 */     return this.schemaType;
/*      */   }
/*      */   
/*      */   public void setObjectIdentityCycleDetection(boolean val) {
/*  978 */     this.cycleDetectionStack.setUseIdentity(val);
/*      */   }
/*      */   public boolean getObjectIdentityCycleDetection() {
/*  981 */     return this.cycleDetectionStack.getUseIdentity();
/*      */   }
/*      */ 
/*      */   
/*      */   void reconcileID() throws SAXException {
/*  986 */     this.idReferencedObjects.removeAll(this.objectsWithId);
/*      */     
/*  988 */     for (Object idObj : this.idReferencedObjects) {
/*      */       try {
/*  990 */         String id = getIdFromObject(idObj);
/*  991 */         reportError((ValidationEvent)new NotIdentifiableEventImpl(1, Messages.DANGLING_IDREF
/*      */               
/*  993 */               .format(new Object[] { id }, ), (ValidationEventLocator)new ValidationEventLocatorImpl(idObj)));
/*      */       }
/*  995 */       catch (JAXBException jAXBException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     this.idReferencedObjects.clear();
/* 1002 */     this.objectsWithId.clear();
/*      */   }
/*      */   
/*      */   public boolean handleError(Exception e) {
/* 1006 */     return handleError(e, this.cycleDetectionStack.peek(), (String)null);
/*      */   }
/*      */   
/*      */   public boolean handleError(Exception e, Object source, String fieldName) {
/* 1010 */     return handleEvent((ValidationEvent)new ValidationEventImpl(1, e
/*      */ 
/*      */           
/* 1013 */           .getMessage(), (ValidationEventLocator)new ValidationEventLocatorExImpl(source, fieldName), e));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleEvent(ValidationEvent event) {
/*      */     try {
/* 1020 */       return this.marshaller.getEventHandler().handleEvent(event);
/* 1021 */     } catch (JAXBException e) {
/*      */       
/* 1023 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void reportMissingObjectError(String fieldName) throws SAXException {
/* 1028 */     reportError((ValidationEvent)new ValidationEventImpl(1, Messages.MISSING_OBJECT
/*      */           
/* 1030 */           .format(new Object[] { fieldName
/* 1031 */             }, ), getCurrentLocation(fieldName), new NullPointerException()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void errorMissingId(Object obj) throws SAXException {
/* 1039 */     reportError((ValidationEvent)new ValidationEventImpl(1, Messages.MISSING_ID
/*      */           
/* 1041 */           .format(new Object[] { obj }, ), (ValidationEventLocator)new ValidationEventLocatorImpl(obj)));
/*      */   }
/*      */ 
/*      */   
/*      */   public ValidationEventLocator getCurrentLocation(String fieldName) {
/* 1046 */     return (ValidationEventLocator)new ValidationEventLocatorExImpl(this.cycleDetectionStack.peek(), fieldName);
/*      */   }
/*      */   
/*      */   protected ValidationEventLocator getLocation() {
/* 1050 */     return getCurrentLocation((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Property getCurrentProperty() {
/* 1058 */     return this.currentProperty.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearCurrentProperty() {
/* 1065 */     if (this.currentProperty != null) {
/* 1066 */       this.currentProperty.remove();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XMLSerializer getInstance() {
/* 1075 */     return (XMLSerializer)Coordinator._getInstance();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/XMLSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */