/*      */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*      */ 
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.istack.internal.SAXParseException2;
/*      */ import com.sun.xml.internal.bind.IDResolver;
/*      */ import com.sun.xml.internal.bind.api.AccessorException;
/*      */ import com.sun.xml.internal.bind.api.ClassResolver;
/*      */ import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;
/*      */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*      */ import com.sun.xml.internal.bind.v2.runtime.AssociationMap;
/*      */ import com.sun.xml.internal.bind.v2.runtime.Coordinator;
/*      */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*      */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.bind.UnmarshalException;
/*      */ import javax.xml.bind.ValidationEvent;
/*      */ import javax.xml.bind.ValidationEventHandler;
/*      */ import javax.xml.bind.ValidationEventLocator;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.helpers.LocatorImpl;
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
/*      */ public final class UnmarshallingContext
/*      */   extends Coordinator
/*      */   implements NamespaceContext, ValidationEventHandler, ErrorHandler, XmlVisitor, XmlVisitor.TextPredictor
/*      */ {
/*   82 */   private static final Logger logger = Logger.getLogger(UnmarshallingContext.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   private final State root;
/*      */ 
/*      */   
/*      */   private State current;
/*      */ 
/*      */   
/*      */   private static final LocatorEx DUMMY_INSTANCE;
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*   97 */     LocatorImpl loc = new LocatorImpl();
/*   98 */     loc.setPublicId(null);
/*   99 */     loc.setSystemId(null);
/*  100 */     loc.setLineNumber(-1);
/*  101 */     loc.setColumnNumber(-1);
/*  102 */     DUMMY_INSTANCE = new LocatorExWrapper(loc);
/*      */   }
/*      */   @NotNull
/*  105 */   private LocatorEx locator = DUMMY_INSTANCE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object result;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JaxBeanInfo expectedType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IDResolver idResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isUnmarshalInProgress = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean aborted = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final UnmarshallerImpl parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AssociationMap assoc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInplaceMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InfosetScanner scanner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object currentElement;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NamespaceContext environmentNamespaceContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassResolver classResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassLoader classLoader;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   private static volatile int errorsCounter = 10;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<Class, Factory> factories;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Patcher[] patchers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int patchersLen;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] nsBind;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nsLen;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Scope[] scopes;
/*      */ 
/*      */ 
/*      */   
/*      */   private int scopeTop;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class State
/*      */   {
/*      */     private Loader loader;
/*      */ 
/*      */ 
/*      */     
/*      */     private Receiver receiver;
/*      */ 
/*      */ 
/*      */     
/*      */     private Intercepter intercepter;
/*      */ 
/*      */ 
/*      */     
/*      */     private Object target;
/*      */ 
/*      */ 
/*      */     
/*      */     private Object backup;
/*      */ 
/*      */ 
/*      */     
/*      */     private int numNsDecl;
/*      */ 
/*      */ 
/*      */     
/*      */     private String elementDefaultValue;
/*      */ 
/*      */ 
/*      */     
/*      */     private State prev;
/*      */ 
/*      */ 
/*      */     
/*      */     private State next;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean nil = false;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean mixed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UnmarshallingContext getContext() {
/*  277 */       return UnmarshallingContext.this;
/*      */     }
/*      */ 
/*      */     
/*      */     private State(State prev) {
/*  282 */       this.prev = prev;
/*  283 */       if (prev != null) {
/*  284 */         prev.next = this;
/*  285 */         if (prev.mixed)
/*  286 */           this.mixed = true; 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void push() {
/*  291 */       if (UnmarshallingContext.logger.isLoggable(Level.FINEST)) {
/*  292 */         UnmarshallingContext.logger.log(Level.FINEST, "State.push");
/*      */       }
/*  294 */       if (this.next == null) {
/*  295 */         assert UnmarshallingContext.this.current == this;
/*  296 */         this.next = new State(this);
/*      */       } 
/*  298 */       this.nil = false;
/*  299 */       State n = this.next;
/*  300 */       n.numNsDecl = UnmarshallingContext.this.nsLen;
/*  301 */       UnmarshallingContext.this.current = n;
/*      */     }
/*      */     
/*      */     private void pop() {
/*  305 */       if (UnmarshallingContext.logger.isLoggable(Level.FINEST)) {
/*  306 */         UnmarshallingContext.logger.log(Level.FINEST, "State.pop");
/*      */       }
/*  308 */       assert this.prev != null;
/*  309 */       this.loader = null;
/*  310 */       this.nil = false;
/*  311 */       this.mixed = false;
/*  312 */       this.receiver = null;
/*  313 */       this.intercepter = null;
/*  314 */       this.elementDefaultValue = null;
/*  315 */       this.target = null;
/*  316 */       UnmarshallingContext.this.current = this.prev;
/*  317 */       this.next = null;
/*      */     }
/*      */     
/*      */     public boolean isMixed() {
/*  321 */       return this.mixed;
/*      */     }
/*      */     
/*      */     public Object getTarget() {
/*  325 */       return this.target;
/*      */     }
/*      */     
/*      */     public void setLoader(Loader loader) {
/*  329 */       if (loader instanceof StructureLoader)
/*  330 */         this.mixed = !((StructureLoader)loader).getBeanInfo().hasElementOnlyContentModel(); 
/*  331 */       this.loader = loader;
/*      */     }
/*      */     
/*      */     public void setReceiver(Receiver receiver) {
/*  335 */       this.receiver = receiver;
/*      */     }
/*      */     
/*      */     public State getPrev() {
/*  339 */       return this.prev;
/*      */     }
/*      */     
/*      */     public void setIntercepter(Intercepter intercepter) {
/*  343 */       this.intercepter = intercepter;
/*      */     }
/*      */     
/*      */     public void setBackup(Object backup) {
/*  347 */       this.backup = backup;
/*      */     }
/*      */     
/*      */     public void setTarget(Object target) {
/*  351 */       this.target = target;
/*      */     }
/*      */     
/*      */     public Object getBackup() {
/*  355 */       return this.backup;
/*      */     }
/*      */     
/*      */     public boolean isNil() {
/*  359 */       return this.nil;
/*      */     }
/*      */     
/*      */     public void setNil(boolean nil) {
/*  363 */       this.nil = nil;
/*      */     }
/*      */     
/*      */     public Loader getLoader() {
/*  367 */       return this.loader;
/*      */     }
/*      */     
/*      */     public String getElementDefaultValue() {
/*  371 */       return this.elementDefaultValue;
/*      */     }
/*      */     
/*      */     public void setElementDefaultValue(String elementDefaultValue) {
/*  375 */       this.elementDefaultValue = elementDefaultValue;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Factory
/*      */   {
/*      */     private final Object factorInstance;
/*      */     
/*      */     private final Method method;
/*      */     
/*      */     public Factory(Object factorInstance, Method method) {
/*  387 */       this.factorInstance = factorInstance;
/*  388 */       this.method = method;
/*      */     }
/*      */     
/*      */     public Object createInstance() throws SAXException {
/*      */       try {
/*  393 */         return this.method.invoke(this.factorInstance, new Object[0]);
/*  394 */       } catch (IllegalAccessException e) {
/*  395 */         UnmarshallingContext.getInstance().handleError(e, false);
/*  396 */       } catch (InvocationTargetException e) {
/*  397 */         UnmarshallingContext.getInstance().handleError(e, false);
/*      */       } 
/*  399 */       return null;
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
/*      */   public void reset(InfosetScanner scanner, boolean isInplaceMode, JaxBeanInfo expectedType, IDResolver idResolver) {
/*  418 */     this.scanner = scanner;
/*  419 */     this.isInplaceMode = isInplaceMode;
/*  420 */     this.expectedType = expectedType;
/*  421 */     this.idResolver = idResolver;
/*      */   }
/*      */   
/*      */   public JAXBContextImpl getJAXBContext() {
/*  425 */     return this.parent.context;
/*      */   }
/*      */   
/*      */   public State getCurrentState() {
/*  429 */     return this.current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Loader selectRootLoader(State state, TagName tag) throws SAXException {
/*      */     try {
/*  441 */       Loader l = getJAXBContext().selectRootLoader(state, tag);
/*  442 */       if (l != null) return l;
/*      */       
/*  444 */       if (this.classResolver != null) {
/*  445 */         Class<?> clazz = this.classResolver.resolveElementName(tag.uri, tag.local);
/*  446 */         if (clazz != null) {
/*  447 */           JAXBContextImpl enhanced = getJAXBContext().createAugmented(clazz);
/*  448 */           JaxBeanInfo<?> bi = enhanced.getBeanInfo(clazz);
/*  449 */           return bi.getLoader(enhanced, true);
/*      */         } 
/*      */       } 
/*  452 */     } catch (RuntimeException e) {
/*  453 */       throw e;
/*  454 */     } catch (Exception e) {
/*  455 */       handleError(e);
/*      */     } 
/*      */     
/*  458 */     return null;
/*      */   }
/*      */   public void setFactories(Object factoryInstances) { this.factories.clear(); if (factoryInstances == null) return;  if (factoryInstances instanceof Object[]) { for (Object factory : (Object[])factoryInstances) addFactory(factory);  } else { addFactory(factoryInstances); }  }
/*      */   private void addFactory(Object factory) { for (Method m : factory.getClass().getMethods()) { if (m.getName().startsWith("create")) if ((m.getParameterTypes()).length <= 0) { Class<?> type = m.getReturnType(); this.factories.put(type, new Factory(factory, m)); }   }  }
/*  462 */   public void startDocument(LocatorEx locator, NamespaceContext nsContext) throws SAXException { if (locator != null) this.locator = locator;  this.environmentNamespaceContext = nsContext; this.result = null; this.current = this.root; this.patchersLen = 0; this.aborted = false; this.isUnmarshalInProgress = true; this.nsLen = 0; if (this.expectedType != null) { this.root.loader = EXPECTED_TYPE_ROOT_LOADER; } else { this.root.loader = DEFAULT_ROOT_LOADER; }  this.idResolver.startDocument(this); } public void startElement(TagName tagName) throws SAXException { pushCoordinator(); try { _startElement(tagName); } finally { popCoordinator(); }  } private void _startElement(TagName tagName) throws SAXException { if (this.assoc != null) this.currentElement = this.scanner.getCurrentElement();  Loader h = this.current.loader; this.current.push(); h.childElement(this.current, tagName); assert this.current.loader != null; this.current.loader.startElement(this.current, tagName); } public void clearStates() { State last = this.current;
/*  463 */     for (; last.next != null; last = last.next);
/*  464 */     while (last.prev != null) {
/*  465 */       last.loader = null;
/*  466 */       last.nil = false;
/*  467 */       last.receiver = null;
/*  468 */       last.intercepter = null;
/*  469 */       last.elementDefaultValue = null;
/*  470 */       last.target = null;
/*  471 */       last = last.prev;
/*  472 */       last.next.prev = null;
/*  473 */       last.next = null;
/*      */     } 
/*  475 */     this.current = last; }
/*      */   public void text(CharSequence pcdata) throws SAXException { pushCoordinator(); try { if (this.current.elementDefaultValue != null && pcdata.length() == 0) pcdata = this.current.elementDefaultValue;  this.current.loader.text(this.current, pcdata); } finally { popCoordinator(); }  }
/*      */   public final void endElement(TagName tagName) throws SAXException { pushCoordinator(); try { State child = this.current; child.loader.leaveElement(child, tagName); Object target = child.target; Receiver recv = child.receiver; Intercepter intercepter = child.intercepter; child.pop(); if (intercepter != null)
/*      */         target = intercepter.intercept(this.current, target);  if (recv != null)
/*      */         recv.receive(this.current, target);  } finally { popCoordinator(); }  }
/*      */   public void endDocument() throws SAXException { runPatchers(); this.idResolver.endDocument(); this.isUnmarshalInProgress = false; this.currentElement = null; this.locator = DUMMY_INSTANCE; this.environmentNamespaceContext = null; assert this.root == this.current; }
/*  481 */   @Deprecated public boolean expectText() { return this.current.loader.expectText; } public UnmarshallingContext(UnmarshallerImpl _parent, AssociationMap assoc) { this.factories = (Map)new HashMap<>();
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
/*  794 */     this.patchers = null;
/*  795 */     this.patchersLen = 0;
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
/*  879 */     this.nsBind = new String[16];
/*  880 */     this.nsLen = 0;
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
/* 1021 */     this.scopes = new Scope[16];
/*      */ 
/*      */ 
/*      */     
/* 1025 */     this.scopeTop = 0;
/*      */ 
/*      */     
/* 1028 */     for (int i = 0; i < this.scopes.length; i++)
/* 1029 */       this.scopes[i] = new Scope<>(this);  this.parent = _parent; this.assoc = assoc; this.root = this.current = new State(null); } @Deprecated public XmlVisitor.TextPredictor getPredictor() { return this; }
/*      */   public UnmarshallingContext getContext() { return this; }
/*      */   public Object getResult() throws UnmarshalException { if (this.isUnmarshalInProgress)
/*      */       throw new IllegalStateException();  if (!this.aborted)
/*      */       return this.result;  throw new UnmarshalException((String)null); }
/*      */   void clearResult() { if (this.isUnmarshalInProgress)
/*      */       throw new IllegalStateException();  this.result = null; }
/*      */   public Object createInstance(Class<?> clazz) throws SAXException { if (!this.factories.isEmpty()) { Factory factory = this.factories.get(clazz); if (factory != null)
/*      */         return factory.createInstance();  }  return ClassFactory.create(clazz); }
/*      */   public Object createInstance(JaxBeanInfo beanInfo) throws SAXException { if (!this.factories.isEmpty()) { Factory factory = this.factories.get(beanInfo.jaxbType); if (factory != null)
/*      */         return factory.createInstance();  }  try { return beanInfo.createInstance(this); } catch (IllegalAccessException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); } catch (InvocationTargetException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); } catch (InstantiationException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); }  return null; }
/*      */   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException { ValidationEventHandler eventHandler = this.parent.getEventHandler(); boolean recover = eventHandler.handleEvent(event); if (!recover)
/*      */       this.aborted = true;  if (!canRecover || !recover)
/*      */       throw new SAXParseException2(event.getMessage(), this.locator, new UnmarshalException(event.getMessage(), event.getLinkedException()));  }
/*      */   public boolean handleEvent(ValidationEvent event) { try { boolean recover = this.parent.getEventHandler().handleEvent(event); if (!recover)
/*      */         this.aborted = true;  return recover; }
/*      */     catch (RuntimeException re) { return false; }
/*      */      }
/*      */   public void handleError(Exception e) throws SAXException { handleError(e, true); }
/* 1048 */   public void startScope(int frameSize) { this.scopeTop += frameSize;
/*      */ 
/*      */     
/* 1051 */     if (this.scopeTop >= this.scopes.length) {
/* 1052 */       Scope[] s = new Scope[Math.max(this.scopeTop + 1, this.scopes.length * 2)];
/* 1053 */       System.arraycopy(this.scopes, 0, s, 0, this.scopes.length);
/* 1054 */       for (int i = this.scopes.length; i < s.length; i++)
/* 1055 */         s[i] = new Scope<>(this); 
/* 1056 */       this.scopes = s;
/*      */     }  } public void handleError(Exception e, boolean canRecover) throws SAXException { handleEvent((ValidationEvent)new ValidationEventImpl(1, e.getMessage(), this.locator.getLocation(), e), canRecover); }
/*      */   public void handleError(String msg) { handleEvent((ValidationEvent)new ValidationEventImpl(1, msg, this.locator.getLocation())); }
/*      */   protected ValidationEventLocator getLocation() { return this.locator.getLocation(); }
/*      */   public LocatorEx getLocator() { return this.locator; }
/*      */   public void errorUnresolvedIDREF(Object bean, String idref, LocatorEx loc) throws SAXException { handleEvent((ValidationEvent)new ValidationEventImpl(1, Messages.UNRESOLVED_IDREF.format(new Object[] { idref }, ), loc.getLocation()), true); }
/*      */   public void addPatcher(Patcher job) { if (this.patchers == null)
/*      */       this.patchers = new Patcher[32];  if (this.patchers.length == this.patchersLen) { Patcher[] buf = new Patcher[this.patchersLen * 2]; System.arraycopy(this.patchers, 0, buf, 0, this.patchersLen); this.patchers = buf; }
/*      */      this.patchers[this.patchersLen++] = job; }
/*      */   private void runPatchers() throws SAXException { if (this.patchers != null)
/*      */       for (int i = 0; i < this.patchersLen; i++) { this.patchers[i].run(); this.patchers[i] = null; }
/*      */         }
/*      */   public String addToIdTable(String id) throws SAXException { Object o = this.current.target; if (o == null)
/*      */       o = this.current.prev.target;  this.idResolver.bind(id, o); return id; }
/*      */   public Callable getObjectFromId(String id, Class targetType) throws SAXException { return this.idResolver.resolve(id, targetType); }
/*      */   public void endScope(int frameSize) throws SAXException { 
/* 1072 */     try { for (; frameSize > 0; frameSize--, this.scopeTop--)
/* 1073 */         this.scopes[this.scopeTop].finish();  }
/* 1074 */     catch (AccessorException e)
/* 1075 */     { handleError((Exception)e);
/*      */ 
/*      */ 
/*      */       
/* 1079 */       for (; frameSize > 0; frameSize--)
/* 1080 */         this.scopes[this.scopeTop--] = new Scope<>(this);  }  } public void startPrefixMapping(String prefix, String uri) { if (this.nsBind.length == this.nsLen) { String[] n = new String[this.nsLen * 2]; System.arraycopy(this.nsBind, 0, n, 0, this.nsLen); this.nsBind = n; }  this.nsBind[this.nsLen++] = prefix; this.nsBind[this.nsLen++] = uri; }
/*      */   public void endPrefixMapping(String prefix) { this.nsLen -= 2; }
/*      */   private String resolveNamespacePrefix(String prefix) { if (prefix.equals("xml")) return "http://www.w3.org/XML/1998/namespace";  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (prefix.equals(this.nsBind[i])) return this.nsBind[i + 1];  }  if (this.environmentNamespaceContext != null) return this.environmentNamespaceContext.getNamespaceURI(prefix.intern());  if (prefix.equals("")) return "";  return null; }
/*      */   public String[] getNewlyDeclaredPrefixes() { return getPrefixList(this.current.prev.numNsDecl); }
/*      */   public String[] getAllDeclaredPrefixes() { return getPrefixList(0); }
/*      */   private String[] getPrefixList(int startIndex) { int size = (this.current.numNsDecl - startIndex) / 2; String[] r = new String[size]; for (int i = 0; i < r.length; i++) r[i] = this.nsBind[startIndex + i * 2];  return r; }
/*      */   public Iterator<String> getPrefixes(String uri) { return Collections.<String>unmodifiableList(getAllPrefixesInList(uri)).iterator(); }
/*      */   private List<String> getAllPrefixesInList(String uri) { List<String> a = new ArrayList<>(); if (uri == null) throw new IllegalArgumentException();  if (uri.equals("http://www.w3.org/XML/1998/namespace")) { a.add("xml"); return a; }  if (uri.equals("http://www.w3.org/2000/xmlns/")) { a.add("xmlns"); return a; }  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1])) a.add(this.nsBind[i]);  }  return a; }
/*      */   public String getPrefix(String uri) { if (uri == null) throw new IllegalArgumentException();  if (uri.equals("http://www.w3.org/XML/1998/namespace")) return "xml";  if (uri.equals("http://www.w3.org/2000/xmlns/")) return "xmlns";  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1]))
/*      */         return this.nsBind[i];  }  if (this.environmentNamespaceContext != null)
/*      */       return this.environmentNamespaceContext.getPrefix(uri);  return null; }
/*      */   public String getNamespaceURI(String prefix) { if (prefix == null)
/*      */       throw new IllegalArgumentException();  if (prefix.equals("xmlns"))
/*      */       return "http://www.w3.org/2000/xmlns/";  return resolveNamespacePrefix(prefix); }
/* 1094 */   public Scope getScope(int offset) { return this.scopes[this.scopeTop - offset]; }
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
/* 1105 */   private static final Loader DEFAULT_ROOT_LOADER = new DefaultRootLoader();
/* 1106 */   private static final Loader EXPECTED_TYPE_ROOT_LOADER = new ExpectedTypeRootLoader();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DefaultRootLoader
/*      */     extends Loader
/*      */     implements Receiver
/*      */   {
/*      */     private DefaultRootLoader() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 1119 */       Loader loader = state.getContext().selectRootLoader(state, ea);
/* 1120 */       if (loader != null) {
/* 1121 */         state.loader = loader;
/* 1122 */         state.receiver = this;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1128 */       JaxBeanInfo beanInfo = XsiTypeLoader.parseXsiType(state, ea, null);
/* 1129 */       if (beanInfo == null) {
/*      */         
/* 1131 */         reportUnexpectedChildElement(ea, false);
/*      */         
/*      */         return;
/*      */       } 
/* 1135 */       state.loader = beanInfo.getLoader(null, false);
/* 1136 */       state.prev.backup = new JAXBElement(ea.createQName(), Object.class, null);
/* 1137 */       state.receiver = this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<QName> getExpectedChildElements() {
/* 1142 */       return UnmarshallingContext.getInstance().getJAXBContext().getValidRootNames();
/*      */     }
/*      */ 
/*      */     
/*      */     public void receive(UnmarshallingContext.State state, Object o) {
/* 1147 */       if (state.backup != null) {
/* 1148 */         ((JAXBElement)state.backup).setValue(o);
/* 1149 */         o = state.backup;
/*      */       } 
/* 1151 */       if (state.nil) {
/* 1152 */         ((JAXBElement)o).setNil(true);
/*      */       }
/* 1154 */       (state.getContext()).result = o;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ExpectedTypeRootLoader
/*      */     extends Loader
/*      */     implements Receiver
/*      */   {
/*      */     private ExpectedTypeRootLoader() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void childElement(UnmarshallingContext.State state, TagName ea) {
/* 1169 */       UnmarshallingContext context = state.getContext();
/*      */ 
/*      */       
/* 1172 */       QName qn = new QName(ea.uri, ea.local);
/* 1173 */       state.prev.target = new JAXBElement(qn, context.expectedType.jaxbType, null, null);
/* 1174 */       state.receiver = this;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1179 */       state.loader = new XsiNilLoader(context.expectedType.getLoader(null, true));
/*      */     }
/*      */ 
/*      */     
/*      */     public void receive(UnmarshallingContext.State state, Object o) {
/* 1184 */       JAXBElement e = (JAXBElement)state.target;
/* 1185 */       e.setValue(o);
/* 1186 */       state.getContext().recordOuterPeer(e);
/* 1187 */       (state.getContext()).result = e;
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
/*      */   public void recordInnerPeer(Object innerPeer) {
/* 1202 */     if (this.assoc != null) {
/* 1203 */       this.assoc.addInner(this.currentElement, innerPeer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getInnerPeer() {
/* 1214 */     if (this.assoc != null && this.isInplaceMode) {
/* 1215 */       return this.assoc.getInnerPeer(this.currentElement);
/*      */     }
/* 1217 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recordOuterPeer(Object outerPeer) {
/* 1228 */     if (this.assoc != null) {
/* 1229 */       this.assoc.addOuter(this.currentElement, outerPeer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getOuterPeer() {
/* 1240 */     if (this.assoc != null && this.isInplaceMode) {
/* 1241 */       return this.assoc.getOuterPeer(this.currentElement);
/*      */     }
/* 1243 */     return null;
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
/*      */   public String getXMIMEContentType() {
/* 1262 */     Object t = this.current.target;
/* 1263 */     if (t == null) return null; 
/* 1264 */     return getJAXBContext().getXMIMEContentType(t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UnmarshallingContext getInstance() {
/* 1272 */     return (UnmarshallingContext)Coordinator._getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<QName> getCurrentExpectedElements() {
/* 1282 */     pushCoordinator();
/*      */     try {
/* 1284 */       State s = getCurrentState();
/* 1285 */       Loader l = s.loader;
/* 1286 */       return (l != null) ? l.getExpectedChildElements() : null;
/*      */     } finally {
/* 1288 */       popCoordinator();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<QName> getCurrentExpectedAttributes() {
/* 1299 */     pushCoordinator();
/*      */     try {
/* 1301 */       State s = getCurrentState();
/* 1302 */       Loader l = s.loader;
/* 1303 */       return (l != null) ? l.getExpectedAttributes() : null;
/*      */     } finally {
/* 1305 */       popCoordinator();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StructureLoader getStructureLoader() {
/* 1315 */     if (this.current.loader instanceof StructureLoader) {
/* 1316 */       return (StructureLoader)this.current.loader;
/*      */     }
/* 1318 */     return null;
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
/*      */   public boolean shouldErrorBeReported() throws SAXException {
/* 1333 */     if (logger.isLoggable(Level.FINEST)) {
/* 1334 */       return true;
/*      */     }
/* 1336 */     if (errorsCounter >= 0) {
/* 1337 */       errorsCounter--;
/* 1338 */       if (errorsCounter == 0)
/* 1339 */         handleEvent((ValidationEvent)new ValidationEventImpl(0, Messages.ERRORS_LIMIT_EXCEEDED.format(new Object[0]), 
/* 1340 */               getLocator().getLocation(), null), true); 
/*      */     } 
/* 1342 */     return (errorsCounter >= 0);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/UnmarshallingContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */