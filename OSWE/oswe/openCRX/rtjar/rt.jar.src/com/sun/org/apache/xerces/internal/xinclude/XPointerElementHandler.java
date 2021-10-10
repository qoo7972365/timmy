/*     */ package com.sun.org.apache.xerces.internal.xinclude;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.impl.dtd.DTDGrammar;
/*     */ import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*     */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Stack;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPointerElementHandler
/*     */   implements XPointerSchema
/*     */ {
/*     */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*     */   protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*     */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*     */   protected static final String XPOINTER_SCHEMA = "http://apache.org/xml/properties/xpointer-schema";
/* 112 */   private static final String[] RECOGNIZED_FEATURES = new String[0];
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/xpointer-schema" };
/*     */ 
/*     */ 
/*     */   
/* 125 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null, null, null, null };
/*     */   
/*     */   protected XMLDocumentHandler fDocumentHandler;
/*     */   
/*     */   protected XMLDocumentSource fDocumentSource;
/*     */   
/*     */   protected XIncludeHandler fParentXIncludeHandler;
/*     */   
/*     */   protected XMLLocator fDocLocation;
/*     */   
/*     */   protected XIncludeNamespaceSupport fNamespaceContext;
/*     */   
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */   protected XMLGrammarPool fGrammarPool;
/*     */   protected XMLGrammarDescription fGrammarDesc;
/*     */   protected DTDGrammar fDTDGrammar;
/*     */   protected XMLEntityResolver fEntityResolver;
/*     */   protected ParserConfigurationSettings fSettings;
/*     */   protected StringBuffer fPointer;
/* 144 */   private int elemCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fRootDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int INITIAL_SIZE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   private boolean[] fSawInclude = new boolean[8];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   private boolean[] fSawFallback = new boolean[8];
/*     */ 
/*     */ 
/*     */   
/* 174 */   private int[] fState = new int[8];
/*     */   
/* 176 */   QName foundElement = null;
/*     */   
/*     */   boolean skip = false;
/*     */   
/*     */   String fSchemaName;
/*     */   
/*     */   String fSchemaPointer;
/*     */   
/*     */   boolean fSubResourceIdentified;
/*     */   Stack fPointerToken;
/*     */   int fCurrentTokenint;
/*     */   String fCurrentTokenString;
/*     */   int fCurrentTokenType;
/*     */   Stack ftempCurrentElement;
/*     */   int fElementCount;
/*     */   int fCurrentToken;
/*     */   boolean includeElement;
/*     */   
/*     */   public void reset() {
/* 195 */     this.elemCount = 0;
/* 196 */     this.fPointerToken = null;
/* 197 */     this.fCurrentTokenint = 0;
/* 198 */     this.fCurrentTokenString = null;
/* 199 */     this.fCurrentTokenType = 0;
/* 200 */     this.fElementCount = 0;
/* 201 */     this.fCurrentToken = 0;
/* 202 */     this.includeElement = false;
/* 203 */     this.foundElement = null;
/* 204 */     this.skip = false;
/* 205 */     this.fSubResourceIdentified = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(XMLComponentManager componentManager) throws XNIException {
/* 210 */     this.fNamespaceContext = null;
/* 211 */     this.elemCount = 0;
/* 212 */     this.fDepth = 0;
/* 213 */     this.fRootDepth = 0;
/* 214 */     this.fPointerToken = null;
/* 215 */     this.fCurrentTokenint = 0;
/* 216 */     this.fCurrentTokenString = null;
/* 217 */     this.fCurrentTokenType = 0;
/* 218 */     this.foundElement = null;
/* 219 */     this.includeElement = false;
/* 220 */     this.skip = false;
/* 221 */     this.fSubResourceIdentified = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 227 */       setErrorReporter((XMLErrorReporter)componentManager
/* 228 */           .getProperty("http://apache.org/xml/properties/internal/error-reporter"));
/*     */     }
/* 230 */     catch (XMLConfigurationException e) {
/* 231 */       this.fErrorReporter = null;
/*     */     } 
/*     */     try {
/* 234 */       this
/* 235 */         .fGrammarPool = (XMLGrammarPool)componentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
/*     */     }
/* 237 */     catch (XMLConfigurationException e) {
/* 238 */       this.fGrammarPool = null;
/*     */     } 
/*     */     try {
/* 241 */       this
/* 242 */         .fEntityResolver = (XMLEntityResolver)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
/*     */     
/*     */     }
/* 245 */     catch (XMLConfigurationException e) {
/* 246 */       this.fEntityResolver = null;
/*     */     } 
/*     */     
/* 249 */     this.fSettings = new ParserConfigurationSettings();
/*     */     
/* 251 */     Enumeration<Object> xercesFeatures = Constants.getXercesFeatures();
/* 252 */     while (xercesFeatures.hasMoreElements()) {
/* 253 */       String featureId = (String)xercesFeatures.nextElement();
/* 254 */       this.fSettings.addRecognizedFeatures(new String[] { featureId });
/*     */       try {
/* 256 */         this.fSettings.setFeature(featureId, componentManager
/*     */             
/* 258 */             .getFeature(featureId));
/*     */       }
/* 260 */       catch (XMLConfigurationException xMLConfigurationException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRecognizedFeatures() {
/* 279 */     return RECOGNIZED_FEATURES;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/* 299 */     if (this.fSettings != null) {
/* 300 */       this.fSettings.setFeature(featureId, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRecognizedProperties() {
/* 311 */     return RECOGNIZED_PROPERTIES;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/* 331 */     if (propertyId.equals("http://apache.org/xml/properties/internal/error-reporter")) {
/* 332 */       setErrorReporter((XMLErrorReporter)value);
/*     */     }
/* 334 */     if (propertyId.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
/* 335 */       this.fGrammarPool = (XMLGrammarPool)value;
/*     */     }
/* 337 */     if (propertyId.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
/* 338 */       this.fEntityResolver = (XMLEntityResolver)value;
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
/*     */ 
/*     */   
/*     */   public Boolean getFeatureDefault(String featureId) {
/* 353 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/* 354 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/* 355 */         return FEATURE_DEFAULTS[i];
/*     */       }
/*     */     } 
/* 358 */     return null;
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
/*     */   public Object getPropertyDefault(String propertyId) {
/* 371 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/* 372 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/* 373 */         return PROPERTY_DEFAULTS[i];
/*     */       }
/*     */     } 
/* 376 */     return null;
/*     */   }
/*     */   
/*     */   private void setErrorReporter(XMLErrorReporter reporter) {
/* 380 */     this.fErrorReporter = reporter;
/* 381 */     if (this.fErrorReporter != null) {
/* 382 */       this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xinclude", new XIncludeMessageFormatter());
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
/*     */   public void setDocumentHandler(XMLDocumentHandler handler) {
/* 394 */     this.fDocumentHandler = handler;
/*     */   }
/*     */   
/*     */   public XMLDocumentHandler getDocumentHandler() {
/* 398 */     return this.fDocumentHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPointerSchemaName(String schemaName) {
/* 415 */     this.fSchemaName = schemaName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXpointerSchemaName() {
/* 423 */     return this.fSchemaName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(Object parent) {
/* 431 */     this.fParentXIncludeHandler = (XIncludeHandler)parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getParent() {
/* 439 */     return this.fParentXIncludeHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXPointerSchemaPointer(String content) {
/* 446 */     this.fSchemaPointer = content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXPointerSchemaPointer() {
/* 453 */     return this.fSchemaPointer;
/*     */   }
/*     */   
/*     */   public boolean isSubResourceIndentified() {
/* 457 */     return this.fSubResourceIdentified;
/*     */   } public void getTokens() { this.fSchemaPointer = this.fSchemaPointer.substring(this.fSchemaPointer.indexOf("(") + 1, this.fSchemaPointer.length()); StringTokenizer st = new StringTokenizer(this.fSchemaPointer, "/"); Integer integerToken = null; Stack<Integer> tempPointerToken = new Stack(); if (this.fPointerToken == null)
/*     */       this.fPointerToken = new Stack();  while (st.hasMoreTokens()) { String tempToken = st.nextToken(); try { integerToken = Integer.valueOf(tempToken); tempPointerToken.push(integerToken); } catch (NumberFormatException e) { tempPointerToken.push(tempToken); }  }  while (!tempPointerToken.empty())
/*     */       this.fPointerToken.push(tempPointerToken.pop());  } public boolean hasMoreToken() { if (this.fPointerToken.isEmpty())
/*     */       return false;  return true; }
/*     */   public boolean getNextToken() { if (!this.fPointerToken.isEmpty()) { Object currentToken = this.fPointerToken.pop(); if (currentToken instanceof Integer) { this.fCurrentTokenint = ((Integer)currentToken).intValue(); this.fCurrentTokenType = 1; }
/*     */       else { this.fCurrentTokenString = ((String)currentToken).toString(); this.fCurrentTokenType = 2; }
/*     */        return true; }
/*     */      return false; }
/* 466 */   public XPointerElementHandler() { this.fPointerToken = new Stack();
/* 467 */     this.fCurrentTokenint = 0;
/* 468 */     this.fCurrentTokenString = null;
/* 469 */     this.fCurrentTokenType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 628 */     this.ftempCurrentElement = new Stack();
/* 629 */     this.fElementCount = 0; this.fDepth = 0;
/*     */     this.fRootDepth = 0;
/*     */     this.fSawFallback[this.fDepth] = false;
/*     */     this.fSawInclude[this.fDepth] = false;
/*     */     this.fSchemaName = "element"; }
/*     */   private boolean isIdAttribute(XMLAttributes attributes, Augmentations augs, int index) { Object o = augs.getItem("ID_ATTRIBUTE");
/*     */     if (o instanceof Boolean)
/*     */       return ((Boolean)o).booleanValue(); 
/* 637 */     return "ID".equals(attributes.getType(index)); } public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException { boolean requiredToken = false;
/* 638 */     if (this.fCurrentTokenType == 0)
/* 639 */       getNextToken(); 
/* 640 */     if (this.fCurrentTokenType == 1) {
/* 641 */       requiredToken = checkIntegerToken(element);
/* 642 */     } else if (this.fCurrentTokenType == 2) {
/* 643 */       requiredToken = checkStringToken(element, attributes);
/* 644 */     }  if (requiredToken && hasMoreToken())
/* 645 */       getNextToken(); 
/* 646 */     if (this.fDocumentHandler != null && this.includeElement)
/* 647 */     { this.elemCount++;
/* 648 */       this.fDocumentHandler.startElement(element, attributes, augs); }  }
/*     */   public boolean checkStringToken(QName element, XMLAttributes attributes) { QName cacheQName = null; String id = null; String rawname = null; QName attrName = new QName(); String attrType = null; String attrValue = null; int attrCount = attributes.getLength(); for (int i = 0; i < attrCount; i++) { Augmentations aaugs = attributes.getAugmentations(i); attributes.getName(i, attrName); attrType = attributes.getType(i); attrValue = attributes.getValue(i); if (attrType != null && attrValue != null && isIdAttribute(attributes, aaugs, i) && attrValue.equals(this.fCurrentTokenString)) { if (hasMoreToken()) { this.fCurrentTokenType = 0; this.fCurrentTokenString = null; return true; }  this.foundElement = element; this.includeElement = true; this.fCurrentTokenType = 0; this.fCurrentTokenString = null; this.fSubResourceIdentified = true; return true; }  }  return false; }
/*     */   public boolean checkIntegerToken(QName element) { if (!this.skip) { this.fElementCount++; if (this.fCurrentTokenint == this.fElementCount) { if (hasMoreToken()) { this.fElementCount = 0; this.fCurrentTokenType = 0; return true; }  this.foundElement = element; this.includeElement = true; this.fCurrentTokenType = 0; this.fElementCount = 0; this.fSubResourceIdentified = true; return true; }  addQName(element); this.skip = true; return false; }  return false; }
/*     */   public void addQName(QName element) { QName cacheQName = new QName(element); this.ftempCurrentElement.push(cacheQName); }
/*     */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException { getTokens(); }
/*     */   public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs) throws XNIException {}
/*     */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {} public void comment(XMLString text, Augmentations augs) throws XNIException { if (this.fDocumentHandler != null && this.includeElement)
/*     */       this.fDocumentHandler.comment(text, augs);  } public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException { if (this.fDocumentHandler != null && this.includeElement)
/* 656 */       this.fDocumentHandler.processingInstruction(target, data, augs);  } public void endElement(QName element, Augmentations augs) throws XNIException { if (this.includeElement && this.foundElement != null) {
/* 657 */       if (this.elemCount > 0) this.elemCount--; 
/* 658 */       this.fDocumentHandler.endElement(element, augs);
/* 659 */       if (this.elemCount == 0) this.includeElement = false;
/*     */     
/* 661 */     } else if (!this.ftempCurrentElement.empty()) {
/* 662 */       QName name = this.ftempCurrentElement.peek();
/* 663 */       if (name.equals(element)) {
/* 664 */         this.ftempCurrentElement.pop();
/* 665 */         this.skip = false;
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 672 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 673 */       this.fDocumentHandler.emptyElement(element, attributes, augs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startGeneralEntity(String name, XMLResourceIdentifier resId, String encoding, Augmentations augs) throws XNIException {
/* 681 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 682 */       this.fDocumentHandler.startGeneralEntity(name, resId, encoding, augs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {
/* 688 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 689 */       this.fDocumentHandler.textDecl(version, encoding, augs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void endGeneralEntity(String name, Augmentations augs) throws XNIException {
/* 695 */     if (this.fDocumentHandler != null) {
/* 696 */       this.fDocumentHandler.endGeneralEntity(name, augs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void characters(XMLString text, Augmentations augs) throws XNIException {
/* 702 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 703 */       this.fDocumentHandler.characters(text, augs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
/* 709 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 710 */       this.fDocumentHandler.ignorableWhitespace(text, augs);
/*     */     }
/*     */   }
/*     */   
/*     */   public void startCDATA(Augmentations augs) throws XNIException {
/* 715 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 716 */       this.fDocumentHandler.startCDATA(augs);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endCDATA(Augmentations augs) throws XNIException {
/* 721 */     if (this.fDocumentHandler != null && this.includeElement) {
/* 722 */       this.fDocumentHandler.endCDATA(augs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument(Augmentations augs) throws XNIException {}
/*     */   
/*     */   public void setDocumentSource(XMLDocumentSource source) {
/* 730 */     this.fDocumentSource = source;
/*     */   }
/*     */   
/*     */   public XMLDocumentSource getDocumentSource() {
/* 734 */     return this.fDocumentSource;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reportFatalError(String key) {
/* 739 */     reportFatalError(key, null);
/*     */   }
/*     */   
/*     */   protected void reportFatalError(String key, Object[] args) {
/* 743 */     if (this.fErrorReporter != null) {
/* 744 */       this.fErrorReporter.reportError(this.fDocLocation, "http://www.w3.org/TR/xinclude", key, args, (short)2);
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
/*     */ 
/*     */   
/*     */   protected boolean isRootDocument() {
/* 759 */     return (this.fParentXIncludeHandler == null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xinclude/XPointerElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */