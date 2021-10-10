/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDDescription;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.xml.internal.stream.Entity;
/*      */ import com.sun.xml.internal.stream.StaxXMLInputSource;
/*      */ import com.sun.xml.internal.stream.dtd.DTDGrammarUtil;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.util.NoSuchElementException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLDocumentScannerImpl
/*      */   extends XMLDocumentFragmentScannerImpl
/*      */ {
/*      */   protected static final int SCANNER_STATE_XML_DECL = 42;
/*      */   protected static final int SCANNER_STATE_PROLOG = 43;
/*      */   protected static final int SCANNER_STATE_TRAILING_MISC = 44;
/*      */   protected static final int SCANNER_STATE_DTD_INTERNAL_DECLS = 45;
/*      */   protected static final int SCANNER_STATE_DTD_EXTERNAL = 46;
/*      */   protected static final int SCANNER_STATE_DTD_EXTERNAL_DECLS = 47;
/*      */   protected static final int SCANNER_STATE_NO_SUCH_ELEMENT_EXCEPTION = 48;
/*      */   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
/*      */   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*      */   protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
/*      */   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
/*      */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   protected static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
/*  130 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/disallow-doctype-decl" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[] { Boolean.TRUE, Boolean.FALSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validation-manager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  160 */   protected XMLDTDScanner fDTDScanner = null;
/*      */ 
/*      */   
/*      */   protected ValidationManager fValidationManager;
/*      */ 
/*      */   
/*  166 */   protected XMLStringBuffer fDTDDecl = null;
/*      */ 
/*      */   
/*      */   protected boolean fReadingDTD = false;
/*      */ 
/*      */   
/*      */   protected boolean fAddedListener = false;
/*      */ 
/*      */   
/*      */   protected String fDoctypeName;
/*      */ 
/*      */   
/*      */   protected String fDoctypePublicId;
/*      */ 
/*      */   
/*      */   protected String fDoctypeSystemId;
/*      */ 
/*      */   
/*  184 */   protected NamespaceContext fNamespaceContext = new NamespaceSupport();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fLoadExternalDTD = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fSeenDoctypeDecl;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fScanEndElement;
/*      */ 
/*      */ 
/*      */   
/*  203 */   protected XMLDocumentFragmentScannerImpl.Driver fXMLDeclDriver = new XMLDeclDriver();
/*      */ 
/*      */   
/*  206 */   protected XMLDocumentFragmentScannerImpl.Driver fPrologDriver = new PrologDriver();
/*      */ 
/*      */   
/*  209 */   protected XMLDocumentFragmentScannerImpl.Driver fDTDDriver = null;
/*      */ 
/*      */   
/*  212 */   protected XMLDocumentFragmentScannerImpl.Driver fTrailingMiscDriver = new TrailingMiscDriver();
/*  213 */   protected int fStartPos = 0;
/*  214 */   protected int fEndPos = 0;
/*      */ 
/*      */   
/*      */   protected boolean fSeenInternalSubset = false;
/*      */   
/*  219 */   private String[] fStrings = new String[3];
/*      */ 
/*      */   
/*  222 */   private XMLInputSource fExternalSubsetSource = null;
/*      */ 
/*      */   
/*  225 */   private final XMLDTDDescription fDTDDescription = new XMLDTDDescription(null, null, null, null, null);
/*      */   
/*  227 */   private static final char[] DOCTYPE = new char[] { 'D', 'O', 'C', 'T', 'Y', 'P', 'E' };
/*  228 */   private static final char[] COMMENTSTRING = new char[] { '-', '-' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInputSource(XMLInputSource inputSource) throws IOException {
/*  251 */     this.fEntityManager.setEntityHandler(this);
/*      */     
/*  253 */     this.fEntityManager.startDocumentEntity(inputSource);
/*      */     
/*  255 */     setScannerState(7);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScannetState() {
/*  262 */     return this.fScannerState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager propertyManager) {
/*  269 */     super.reset(propertyManager);
/*      */     
/*  271 */     this.fDoctypeName = null;
/*  272 */     this.fDoctypePublicId = null;
/*  273 */     this.fDoctypeSystemId = null;
/*  274 */     this.fSeenDoctypeDecl = false;
/*  275 */     this.fNamespaceContext.reset();
/*  276 */     this.fSupportDTD = ((Boolean)propertyManager.getProperty("javax.xml.stream.supportDTD")).booleanValue();
/*      */ 
/*      */     
/*  279 */     this.fLoadExternalDTD = !((Boolean)propertyManager.getProperty("http://java.sun.com/xml/stream/properties/ignore-external-dtd")).booleanValue();
/*  280 */     setScannerState(7);
/*  281 */     setDriver(this.fXMLDeclDriver);
/*  282 */     this.fSeenInternalSubset = false;
/*  283 */     if (this.fDTDScanner != null) {
/*  284 */       ((XMLDTDScannerImpl)this.fDTDScanner).reset(propertyManager);
/*      */     }
/*  286 */     this.fEndPos = 0;
/*  287 */     this.fStartPos = 0;
/*  288 */     if (this.fDTDDecl != null) {
/*  289 */       this.fDTDDecl.clear();
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
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  311 */     super.reset(componentManager);
/*      */ 
/*      */     
/*  314 */     this.fDoctypeName = null;
/*  315 */     this.fDoctypePublicId = null;
/*  316 */     this.fDoctypeSystemId = null;
/*  317 */     this.fSeenDoctypeDecl = false;
/*  318 */     this.fExternalSubsetSource = null;
/*      */ 
/*      */     
/*  321 */     this.fLoadExternalDTD = componentManager.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);
/*  322 */     this.fDisallowDoctype = componentManager.getFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
/*      */     
/*  324 */     this.fNamespaces = componentManager.getFeature("http://xml.org/sax/features/namespaces", true);
/*      */     
/*  326 */     this.fSeenInternalSubset = false;
/*      */     
/*  328 */     this.fDTDScanner = (XMLDTDScanner)componentManager.getProperty("http://apache.org/xml/properties/internal/dtd-scanner");
/*      */     
/*  330 */     this.fValidationManager = (ValidationManager)componentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager", null);
/*      */     
/*      */     try {
/*  333 */       this.fNamespaceContext = (NamespaceContext)componentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context");
/*      */     }
/*  335 */     catch (XMLConfigurationException xMLConfigurationException) {}
/*  336 */     if (this.fNamespaceContext == null) {
/*  337 */       this.fNamespaceContext = new NamespaceSupport();
/*      */     }
/*  339 */     this.fNamespaceContext.reset();
/*      */     
/*  341 */     this.fEndPos = 0;
/*  342 */     this.fStartPos = 0;
/*  343 */     if (this.fDTDDecl != null) {
/*  344 */       this.fDTDDecl.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     setScannerState(42);
/*  351 */     setDriver(this.fXMLDeclDriver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedFeatures() {
/*  362 */     String[] featureIds = super.getRecognizedFeatures();
/*  363 */     int length = (featureIds != null) ? featureIds.length : 0;
/*  364 */     String[] combinedFeatureIds = new String[length + RECOGNIZED_FEATURES.length];
/*  365 */     if (featureIds != null) {
/*  366 */       System.arraycopy(featureIds, 0, combinedFeatureIds, 0, featureIds.length);
/*      */     }
/*  368 */     System.arraycopy(RECOGNIZED_FEATURES, 0, combinedFeatureIds, length, RECOGNIZED_FEATURES.length);
/*  369 */     return combinedFeatureIds;
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
/*      */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/*  390 */     super.setFeature(featureId, state);
/*      */ 
/*      */     
/*  393 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/*  394 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/*      */       
/*  396 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/*  397 */         .endsWith("nonvalidating/load-external-dtd")) {
/*  398 */         this.fLoadExternalDTD = state;
/*      */         return;
/*      */       } 
/*  401 */       if (suffixLength == "disallow-doctype-decl".length() && featureId
/*  402 */         .endsWith("disallow-doctype-decl")) {
/*  403 */         this.fDisallowDoctype = state;
/*      */         return;
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
/*      */   public String[] getRecognizedProperties() {
/*  416 */     String[] propertyIds = super.getRecognizedProperties();
/*  417 */     int length = (propertyIds != null) ? propertyIds.length : 0;
/*  418 */     String[] combinedPropertyIds = new String[length + RECOGNIZED_PROPERTIES.length];
/*  419 */     if (propertyIds != null) {
/*  420 */       System.arraycopy(propertyIds, 0, combinedPropertyIds, 0, propertyIds.length);
/*      */     }
/*  422 */     System.arraycopy(RECOGNIZED_PROPERTIES, 0, combinedPropertyIds, length, RECOGNIZED_PROPERTIES.length);
/*  423 */     return combinedPropertyIds;
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
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/*  444 */     super.setProperty(propertyId, value);
/*      */ 
/*      */     
/*  447 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/*  448 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*      */       
/*  450 */       if (suffixLength == "internal/dtd-scanner".length() && propertyId
/*  451 */         .endsWith("internal/dtd-scanner")) {
/*  452 */         this.fDTDScanner = (XMLDTDScanner)value;
/*      */       }
/*  454 */       if (suffixLength == "internal/namespace-context".length() && propertyId
/*  455 */         .endsWith("internal/namespace-context") && 
/*  456 */         value != null) {
/*  457 */         this.fNamespaceContext = (NamespaceContext)value;
/*      */       }
/*      */       return;
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
/*      */   public Boolean getFeatureDefault(String featureId) {
/*  477 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/*  478 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  479 */         return FEATURE_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  482 */     return super.getFeatureDefault(featureId);
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
/*      */   public Object getPropertyDefault(String propertyId) {
/*  495 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/*  496 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  497 */         return PROPERTY_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  500 */     return super.getPropertyDefault(propertyId);
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
/*      */   public void startEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/*  526 */     super.startEntity(name, identifier, encoding, augs);
/*      */ 
/*      */     
/*  529 */     this.fEntityScanner.registerListener(this);
/*      */ 
/*      */     
/*  532 */     if (!name.equals("[xml]") && this.fEntityScanner.isExternal())
/*      */     {
/*  534 */       if (augs == null || !((Boolean)augs.getItem("ENTITY_SKIPPED")).booleanValue()) {
/*  535 */         setScannerState(36);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  541 */     if (this.fDocumentHandler != null && name.equals("[xml]")) {
/*  542 */       this.fDocumentHandler.startDocument(this.fEntityScanner, encoding, this.fNamespaceContext, null);
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
/*      */   public void endEntity(String name, Augmentations augs) throws IOException, XNIException {
/*  559 */     super.endEntity(name, augs);
/*      */     
/*  561 */     if (name.equals("[xml]"))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  566 */       if (this.fMarkupDepth == 0 && this.fDriver == this.fTrailingMiscDriver) {
/*      */         
/*  568 */         setScannerState(34);
/*      */       }
/*      */       else {
/*      */         
/*  572 */         throw new EOFException();
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
/*      */   
/*      */   public XMLStringBuffer getDTDDecl() {
/*  585 */     Entity entity = this.fEntityScanner.getCurrentEntity();
/*  586 */     this.fDTDDecl.append(((Entity.ScannedEntity)entity).ch, this.fStartPos, this.fEndPos - this.fStartPos);
/*  587 */     if (this.fSeenInternalSubset)
/*  588 */       this.fDTDDecl.append("]>"); 
/*  589 */     return this.fDTDDecl;
/*      */   }
/*      */   
/*      */   public String getCharacterEncodingScheme() {
/*  593 */     return this.fDeclaredEncoding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int next() throws IOException, XNIException {
/*  602 */     return this.fDriver.next();
/*      */   }
/*      */ 
/*      */   
/*      */   public NamespaceContext getNamespaceContext() {
/*  607 */     return this.fNamespaceContext;
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
/*      */   protected XMLDocumentFragmentScannerImpl.Driver createContentDriver() {
/*  620 */     return new ContentDriver();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean scanDoctypeDecl(boolean supportDTD) throws IOException, XNIException {
/*  629 */     if (!this.fEntityScanner.skipSpaces()) {
/*  630 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ROOT_ELEMENT_TYPE_IN_DOCTYPEDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  635 */     this.fDoctypeName = this.fEntityScanner.scanName(XMLScanner.NameType.DOCTYPE);
/*  636 */     if (this.fDoctypeName == null) {
/*  637 */       reportFatalError("MSG_ROOT_ELEMENT_TYPE_REQUIRED", null);
/*      */     }
/*      */ 
/*      */     
/*  641 */     if (this.fEntityScanner.skipSpaces()) {
/*  642 */       scanExternalID(this.fStrings, false);
/*  643 */       this.fDoctypeSystemId = this.fStrings[0];
/*  644 */       this.fDoctypePublicId = this.fStrings[1];
/*  645 */       this.fEntityScanner.skipSpaces();
/*      */     } 
/*      */     
/*  648 */     this.fHasExternalDTD = (this.fDoctypeSystemId != null);
/*      */ 
/*      */     
/*  651 */     if (supportDTD && !this.fHasExternalDTD && this.fExternalSubsetResolver != null) {
/*  652 */       this.fDTDDescription.setValues(null, null, this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
/*  653 */       this.fDTDDescription.setRootName(this.fDoctypeName);
/*  654 */       this.fExternalSubsetSource = this.fExternalSubsetResolver.getExternalSubset(this.fDTDDescription);
/*  655 */       this.fHasExternalDTD = (this.fExternalSubsetSource != null);
/*      */     } 
/*      */ 
/*      */     
/*  659 */     if (supportDTD && this.fDocumentHandler != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  665 */       if (this.fExternalSubsetSource == null) {
/*  666 */         this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fDoctypePublicId, this.fDoctypeSystemId, null);
/*      */       } else {
/*      */         
/*  669 */         this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fExternalSubsetSource.getPublicId(), this.fExternalSubsetSource.getSystemId(), null);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  674 */     boolean internalSubset = true;
/*  675 */     if (!this.fEntityScanner.skipChar(91, null)) {
/*  676 */       internalSubset = false;
/*  677 */       this.fEntityScanner.skipSpaces();
/*  678 */       if (!this.fEntityScanner.skipChar(62, null)) {
/*  679 */         reportFatalError("DoctypedeclUnterminated", new Object[] { this.fDoctypeName });
/*      */       }
/*  681 */       this.fMarkupDepth--;
/*      */     } 
/*  683 */     return internalSubset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEndDTDScanState() {
/*  692 */     setScannerState(43);
/*  693 */     setDriver(this.fPrologDriver);
/*  694 */     this.fEntityManager.setEntityHandler(this);
/*  695 */     this.fReadingDTD = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getScannerStateName(int state) {
/*  701 */     switch (state) { case 42:
/*  702 */         return "SCANNER_STATE_XML_DECL";
/*  703 */       case 43: return "SCANNER_STATE_PROLOG";
/*  704 */       case 44: return "SCANNER_STATE_TRAILING_MISC";
/*  705 */       case 45: return "SCANNER_STATE_DTD_INTERNAL_DECLS";
/*  706 */       case 46: return "SCANNER_STATE_DTD_EXTERNAL";
/*  707 */       case 47: return "SCANNER_STATE_DTD_EXTERNAL_DECLS"; }
/*      */     
/*  709 */     return super.getScannerStateName(state);
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
/*      */ 
/*      */   
/*      */   protected final class XMLDeclDriver
/*      */     implements XMLDocumentFragmentScannerImpl.Driver
/*      */   {
/*      */     public int next() throws IOException, XNIException {
/*  743 */       XMLDocumentScannerImpl.this.setScannerState(43);
/*  744 */       XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fPrologDriver);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  749 */         if (XMLDocumentScannerImpl.this.fEntityScanner.skipString(XMLDocumentFragmentScannerImpl.xmlDecl)) {
/*  750 */           XMLDocumentScannerImpl.this.fMarkupDepth++;
/*      */ 
/*      */           
/*  753 */           if (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
/*  754 */             XMLDocumentScannerImpl.this.fStringBuffer.clear();
/*  755 */             XMLDocumentScannerImpl.this.fStringBuffer.append("xml");
/*  756 */             while (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
/*  757 */               XMLDocumentScannerImpl.this.fStringBuffer.append((char)XMLDocumentScannerImpl.this.fEntityScanner.scanChar(null));
/*      */             }
/*  759 */             String target = XMLDocumentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentScannerImpl.this.fStringBuffer.ch, XMLDocumentScannerImpl.this.fStringBuffer.offset, XMLDocumentScannerImpl.this.fStringBuffer.length);
/*      */             
/*  761 */             XMLDocumentScannerImpl.this.fContentBuffer.clear();
/*  762 */             XMLDocumentScannerImpl.this.scanPIData(target, XMLDocumentScannerImpl.this.fContentBuffer);
/*      */             
/*  764 */             XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/*      */             
/*  766 */             return 3;
/*      */           } 
/*      */ 
/*      */           
/*  770 */           XMLDocumentScannerImpl.this.scanXMLDeclOrTextDecl(false);
/*      */           
/*  772 */           XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/*  773 */           return 7;
/*      */         } 
/*      */ 
/*      */         
/*  777 */         XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/*      */ 
/*      */         
/*  780 */         return 7;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  790 */       catch (EOFException e) {
/*  791 */         XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
/*  792 */         return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final class PrologDriver
/*      */     implements XMLDocumentFragmentScannerImpl.Driver
/*      */   {
/*      */     public int next() throws IOException, XNIException {
/*      */       try {
/*      */         do {
/*  832 */           switch (XMLDocumentScannerImpl.this.fScannerState) {
/*      */             case 43:
/*  834 */               XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
/*  835 */               if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60, null)) {
/*  836 */                 XMLDocumentScannerImpl.this.setScannerState(21); break;
/*  837 */               }  if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(38, XMLScanner.NameType.REFERENCE)) {
/*  838 */                 XMLDocumentScannerImpl.this.setScannerState(28); break;
/*      */               } 
/*  840 */               XMLDocumentScannerImpl.this.setScannerState(22);
/*      */               break;
/*      */ 
/*      */ 
/*      */             
/*      */             case 21:
/*  846 */               XMLDocumentScannerImpl.this.fMarkupDepth++;
/*  847 */               if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()) || XMLDocumentScannerImpl.this
/*  848 */                 .isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
/*  849 */                 XMLDocumentScannerImpl.this.setScannerState(26);
/*  850 */                 XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fContentDriver);
/*      */                 
/*  852 */                 return XMLDocumentScannerImpl.this.fContentDriver.next();
/*  853 */               }  if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33, null)) {
/*  854 */                 if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45, null)) {
/*  855 */                   if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45, null)) {
/*  856 */                     XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
/*      */                   }
/*      */                   
/*  859 */                   XMLDocumentScannerImpl.this.setScannerState(27); break;
/*  860 */                 }  if (XMLDocumentScannerImpl.this.fEntityScanner.skipString(XMLDocumentScannerImpl.DOCTYPE)) {
/*  861 */                   XMLDocumentScannerImpl.this.setScannerState(24);
/*  862 */                   Entity entity = XMLDocumentScannerImpl.this.fEntityScanner.getCurrentEntity();
/*  863 */                   if (entity instanceof Entity.ScannedEntity) {
/*  864 */                     XMLDocumentScannerImpl.this.fStartPos = ((Entity.ScannedEntity)entity).position;
/*      */                   }
/*  866 */                   XMLDocumentScannerImpl.this.fReadingDTD = true;
/*  867 */                   if (XMLDocumentScannerImpl.this.fDTDDecl == null)
/*  868 */                     XMLDocumentScannerImpl.this.fDTDDecl = new XMLStringBuffer(); 
/*  869 */                   XMLDocumentScannerImpl.this.fDTDDecl.append("<!DOCTYPE");
/*      */                   break;
/*      */                 } 
/*  872 */                 XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", null);
/*      */                 break;
/*      */               } 
/*  875 */               if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63, null)) {
/*  876 */                 XMLDocumentScannerImpl.this.setScannerState(23); break;
/*      */               } 
/*  878 */               XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", null);
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*  884 */         } while (XMLDocumentScannerImpl.this.fScannerState == 43 || XMLDocumentScannerImpl.this.fScannerState == 21);
/*      */         
/*  886 */         switch (XMLDocumentScannerImpl.this.fScannerState) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 27:
/*  899 */             XMLDocumentScannerImpl.this.scanComment();
/*  900 */             XMLDocumentScannerImpl.this.setScannerState(43);
/*  901 */             return 5;
/*      */ 
/*      */ 
/*      */           
/*      */           case 23:
/*  906 */             XMLDocumentScannerImpl.this.fContentBuffer.clear();
/*  907 */             XMLDocumentScannerImpl.this.scanPI(XMLDocumentScannerImpl.this.fContentBuffer);
/*  908 */             XMLDocumentScannerImpl.this.setScannerState(43);
/*  909 */             return 3;
/*      */ 
/*      */           
/*      */           case 24:
/*  913 */             if (XMLDocumentScannerImpl.this.fDisallowDoctype) {
/*  914 */               XMLDocumentScannerImpl.this.reportFatalError("DoctypeNotAllowed", null);
/*      */             }
/*      */             
/*  917 */             if (XMLDocumentScannerImpl.this.fSeenDoctypeDecl) {
/*  918 */               XMLDocumentScannerImpl.this.reportFatalError("AlreadySeenDoctype", null);
/*      */             }
/*  920 */             XMLDocumentScannerImpl.this.fSeenDoctypeDecl = true;
/*      */ 
/*      */ 
/*      */             
/*  924 */             if (XMLDocumentScannerImpl.this.scanDoctypeDecl(XMLDocumentScannerImpl.this.fSupportDTD)) {
/*      */               
/*  926 */               XMLDocumentScannerImpl.this.setScannerState(45);
/*  927 */               XMLDocumentScannerImpl.this.fSeenInternalSubset = true;
/*  928 */               if (XMLDocumentScannerImpl.this.fDTDDriver == null) {
/*  929 */                 XMLDocumentScannerImpl.this.fDTDDriver = new XMLDocumentScannerImpl.DTDDriver();
/*      */               }
/*  931 */               XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fContentDriver);
/*      */               
/*  933 */               return XMLDocumentScannerImpl.this.fDTDDriver.next();
/*      */             } 
/*      */             
/*  936 */             if (XMLDocumentScannerImpl.this.fSeenDoctypeDecl) {
/*  937 */               Entity entity = XMLDocumentScannerImpl.this.fEntityScanner.getCurrentEntity();
/*  938 */               if (entity instanceof Entity.ScannedEntity) {
/*  939 */                 XMLDocumentScannerImpl.this.fEndPos = ((Entity.ScannedEntity)entity).position;
/*      */               }
/*  941 */               XMLDocumentScannerImpl.this.fReadingDTD = false;
/*      */             } 
/*      */ 
/*      */             
/*  945 */             if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null) {
/*  946 */               if ((XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || 
/*  947 */                 !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
/*  948 */                 if (XMLDocumentScannerImpl.this.fSupportDTD) {
/*  949 */                   XMLDocumentScannerImpl.this.setScannerState(46);
/*      */                 } else {
/*  951 */                   XMLDocumentScannerImpl.this.setScannerState(43);
/*      */                 } 
/*      */                 
/*  954 */                 XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fContentDriver);
/*  955 */                 if (XMLDocumentScannerImpl.this.fDTDDriver == null) {
/*  956 */                   XMLDocumentScannerImpl.this.fDTDDriver = new XMLDocumentScannerImpl.DTDDriver();
/*      */                 }
/*      */                 
/*  959 */                 return XMLDocumentScannerImpl.this.fDTDDriver.next();
/*      */               }
/*      */             
/*  962 */             } else if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null && (
/*  963 */               XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || 
/*  964 */               !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
/*      */               
/*  966 */               XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
/*  967 */               XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
/*  968 */               if (XMLDocumentScannerImpl.this.fSupportDTD) {
/*  969 */                 XMLDocumentScannerImpl.this.setScannerState(47);
/*      */               } else {
/*  971 */                 XMLDocumentScannerImpl.this.setScannerState(43);
/*  972 */               }  XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fContentDriver);
/*  973 */               if (XMLDocumentScannerImpl.this.fDTDDriver == null)
/*  974 */                 XMLDocumentScannerImpl.this.fDTDDriver = new XMLDocumentScannerImpl.DTDDriver(); 
/*  975 */               return XMLDocumentScannerImpl.this.fDTDDriver.next();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  986 */             if (XMLDocumentScannerImpl.this.fDTDScanner != null) {
/*  987 */               XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(null);
/*      */             }
/*  989 */             XMLDocumentScannerImpl.this.setScannerState(43);
/*  990 */             return 11;
/*      */ 
/*      */           
/*      */           case 22:
/*  994 */             XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInProlog", null);
/*  995 */             XMLDocumentScannerImpl.this.fEntityScanner.scanChar(null);
/*      */           
/*      */           case 28:
/*  998 */             XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInProlog", null);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       } catch (EOFException e) {
/* 1014 */         XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
/*      */         
/* 1016 */         return -1;
/*      */       } 
/*      */ 
/*      */       
/* 1020 */       return -1;
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
/*      */   protected final class DTDDriver
/*      */     implements XMLDocumentFragmentScannerImpl.Driver
/*      */   {
/*      */     public int next() throws IOException, XNIException {
/* 1045 */       dispatch(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1053 */       if (XMLDocumentScannerImpl.this.fPropertyManager != null) {
/* 1054 */         XMLDocumentScannerImpl.this.dtdGrammarUtil = new DTDGrammarUtil(((XMLDTDScannerImpl)XMLDocumentScannerImpl.this.fDTDScanner).getGrammar(), XMLDocumentScannerImpl.this.fSymbolTable, XMLDocumentScannerImpl.this.fNamespaceContext);
/*      */       }
/*      */       
/* 1057 */       return 11;
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
/*      */     public boolean dispatch(boolean complete) throws IOException, XNIException {
/* 1074 */       XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(null);
/*      */       try {
/*      */         boolean again;
/* 1077 */         XMLResourceIdentifierImpl resourceIdentifier = new XMLResourceIdentifierImpl();
/* 1078 */         if (XMLDocumentScannerImpl.this.fDTDScanner == null) {
/*      */           
/* 1080 */           if (XMLDocumentScannerImpl.this.fEntityManager.getEntityScanner() instanceof XML11EntityScanner) {
/* 1081 */             XMLDocumentScannerImpl.this.fDTDScanner = new XML11DTDScannerImpl();
/*      */           } else {
/*      */             
/* 1084 */             XMLDocumentScannerImpl.this.fDTDScanner = new XMLDTDScannerImpl();
/*      */           } 
/* 1086 */           ((XMLDTDScannerImpl)XMLDocumentScannerImpl.this.fDTDScanner).reset(XMLDocumentScannerImpl.this.fPropertyManager);
/*      */         } 
/*      */         
/* 1089 */         XMLDocumentScannerImpl.this.fDTDScanner.setLimitAnalyzer(XMLDocumentScannerImpl.this.fLimitAnalyzer); do {
/*      */           boolean moreToScan; XMLInputSource xmlInputSource; boolean completeDTD; Entity entity; StaxXMLInputSource staxInputSource; boolean bool1;
/* 1091 */           again = false;
/* 1092 */           switch (XMLDocumentScannerImpl.this.fScannerState) {
/*      */             case 45:
/* 1094 */               moreToScan = false;
/* 1095 */               if (!XMLDocumentScannerImpl.this.fDTDScanner.skipDTD(XMLDocumentScannerImpl.this.fSupportDTD)) {
/*      */ 
/*      */                 
/* 1098 */                 boolean bool = true;
/*      */                 
/* 1100 */                 moreToScan = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDInternalSubset(bool, XMLDocumentScannerImpl.this.fStandalone, (XMLDocumentScannerImpl.this.fHasExternalDTD && XMLDocumentScannerImpl.this.fLoadExternalDTD));
/*      */               } 
/* 1102 */               entity = XMLDocumentScannerImpl.this.fEntityScanner.getCurrentEntity();
/* 1103 */               if (entity instanceof Entity.ScannedEntity) {
/* 1104 */                 XMLDocumentScannerImpl.this.fEndPos = ((Entity.ScannedEntity)entity).position;
/*      */               }
/* 1106 */               XMLDocumentScannerImpl.this.fReadingDTD = false;
/* 1107 */               if (!moreToScan) {
/*      */                 
/* 1109 */                 if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(93, null)) {
/* 1110 */                   XMLDocumentScannerImpl.this.reportFatalError("DoctypedeclNotClosed", new Object[] { this.this$0.fDoctypeName });
/*      */                 }
/* 1112 */                 XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
/* 1113 */                 if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(62, null)) {
/* 1114 */                   XMLDocumentScannerImpl.this.reportFatalError("DoctypedeclUnterminated", new Object[] { this.this$0.fDoctypeName });
/*      */                 }
/* 1116 */                 XMLDocumentScannerImpl.this.fMarkupDepth--;
/*      */                 
/* 1118 */                 if (!XMLDocumentScannerImpl.this.fSupportDTD) {
/*      */ 
/*      */                   
/* 1121 */                   XMLDocumentScannerImpl.this.fEntityStore = XMLDocumentScannerImpl.this.fEntityManager.getEntityStore();
/* 1122 */                   XMLDocumentScannerImpl.this.fEntityStore.reset();
/*      */                 
/*      */                 }
/* 1125 */                 else if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null && (XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD)) {
/* 1126 */                   XMLDocumentScannerImpl.this.setScannerState(46);
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */                 
/* 1131 */                 XMLDocumentScannerImpl.this.setEndDTDScanState();
/* 1132 */                 return true;
/*      */               } 
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 46:
/* 1149 */               resourceIdentifier.setValues(XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null, null);
/* 1150 */               xmlInputSource = null;
/* 1151 */               staxInputSource = XMLDocumentScannerImpl.this.fEntityManager.resolveEntityAsPerStax(resourceIdentifier);
/*      */ 
/*      */               
/* 1154 */               if (!staxInputSource.hasResolver()) {
/* 1155 */                 String accessError = XMLDocumentScannerImpl.this.checkAccess(XMLDocumentScannerImpl.this.fDoctypeSystemId, XMLDocumentScannerImpl.this.fAccessExternalDTD);
/* 1156 */                 if (accessError != null) {
/* 1157 */                   XMLDocumentScannerImpl.this.reportFatalError("AccessExternalDTD", new Object[] { SecuritySupport.sanitizePath(this.this$0.fDoctypeSystemId), accessError });
/*      */                 }
/*      */               } 
/* 1160 */               xmlInputSource = staxInputSource.getXMLInputSource();
/* 1161 */               XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(xmlInputSource);
/* 1162 */               if (XMLDocumentScannerImpl.this.fEntityScanner.fCurrentEntity != null) {
/* 1163 */                 XMLDocumentScannerImpl.this.setScannerState(47);
/*      */               } else {
/* 1165 */                 XMLDocumentScannerImpl.this.setScannerState(43);
/*      */               } 
/* 1167 */               again = true;
/*      */               break;
/*      */ 
/*      */ 
/*      */             
/*      */             case 47:
/* 1173 */               completeDTD = true;
/* 1174 */               bool1 = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(completeDTD);
/* 1175 */               if (!bool1) {
/* 1176 */                 XMLDocumentScannerImpl.this.setEndDTDScanState();
/* 1177 */                 return true;
/*      */               } 
/*      */               break;
/*      */ 
/*      */             
/*      */             case 43:
/* 1183 */               XMLDocumentScannerImpl.this.setEndDTDScanState();
/* 1184 */               completeDTD = true; return completeDTD;
/*      */             
/*      */             default:
/* 1187 */               throw new XNIException("DTDDriver#dispatch: scanner state=" + XMLDocumentScannerImpl.this.fScannerState + " (" + XMLDocumentScannerImpl.this.getScannerStateName(XMLDocumentScannerImpl.this.fScannerState) + ')');
/*      */           } 
/*      */         
/* 1190 */         } while (complete || again);
/*      */ 
/*      */       
/*      */       }
/* 1194 */       catch (EOFException e) {
/* 1195 */         e.printStackTrace();
/* 1196 */         XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
/* 1197 */         return false;
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1203 */         XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
/*      */       } 
/*      */       
/* 1206 */       return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ContentDriver
/*      */     extends XMLDocumentFragmentScannerImpl.FragmentContentDriver
/*      */   {
/*      */     protected boolean scanForDoctypeHook() throws IOException, XNIException {
/* 1243 */       if (XMLDocumentScannerImpl.this.fEntityScanner.skipString(XMLDocumentScannerImpl.DOCTYPE)) {
/* 1244 */         XMLDocumentScannerImpl.this.setScannerState(24);
/*      */         
/* 1246 */         return true;
/*      */       } 
/* 1248 */       return false;
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
/*      */     protected boolean elementDepthIsZeroHook() throws IOException, XNIException {
/* 1268 */       XMLDocumentScannerImpl.this.setScannerState(44);
/* 1269 */       XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fTrailingMiscDriver);
/* 1270 */       return true;
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
/*      */     protected boolean scanRootElementHook() throws IOException, XNIException {
/* 1289 */       if (XMLDocumentScannerImpl.this.scanStartElement()) {
/* 1290 */         XMLDocumentScannerImpl.this.setScannerState(44);
/* 1291 */         XMLDocumentScannerImpl.this.setDriver(XMLDocumentScannerImpl.this.fTrailingMiscDriver);
/* 1292 */         return true;
/*      */       } 
/* 1294 */       return false;
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
/*      */     protected void endOfFileHook(EOFException e) throws IOException, XNIException {
/* 1308 */       XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resolveExternalSubsetAndRead() throws IOException, XNIException {
/* 1317 */       XMLDocumentScannerImpl.this.fDTDDescription.setValues(null, null, XMLDocumentScannerImpl.this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), null);
/* 1318 */       XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fElementQName.rawname);
/* 1319 */       XMLInputSource src = XMLDocumentScannerImpl.this.fExternalSubsetResolver.getExternalSubset(XMLDocumentScannerImpl.this.fDTDDescription);
/*      */       
/* 1321 */       if (src != null) {
/* 1322 */         XMLDocumentScannerImpl.this.fDoctypeName = XMLDocumentScannerImpl.this.fElementQName.rawname;
/* 1323 */         XMLDocumentScannerImpl.this.fDoctypePublicId = src.getPublicId();
/* 1324 */         XMLDocumentScannerImpl.this.fDoctypeSystemId = src.getSystemId();
/*      */         
/* 1326 */         if (XMLDocumentScannerImpl.this.fDocumentHandler != null)
/*      */         {
/*      */           
/* 1329 */           XMLDocumentScannerImpl.this.fDocumentHandler.doctypeDecl(XMLDocumentScannerImpl.this.fDoctypeName, XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, null);
/*      */         }
/*      */         try {
/* 1332 */           XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(src);
/* 1333 */           while (XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(true));
/*      */         } finally {
/* 1335 */           XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final class TrailingMiscDriver
/*      */     implements XMLDocumentFragmentScannerImpl.Driver
/*      */   {
/*      */     public int next() throws IOException, XNIException {
/* 1359 */       if (XMLDocumentScannerImpl.this.fEmptyElement) {
/* 1360 */         XMLDocumentScannerImpl.this.fEmptyElement = false;
/* 1361 */         return 2;
/*      */       } 
/*      */       try {
/*      */         int ch;
/* 1365 */         if (XMLDocumentScannerImpl.this.fScannerState == 34)
/* 1366 */           return 8; 
/*      */         do {
/* 1368 */           switch (XMLDocumentScannerImpl.this.fScannerState) {
/*      */             
/*      */             case 44:
/* 1371 */               XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
/*      */ 
/*      */               
/* 1374 */               if (XMLDocumentScannerImpl.this.fScannerState == 34) {
/* 1375 */                 return 8;
/*      */               }
/* 1377 */               if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60, null)) {
/* 1378 */                 XMLDocumentScannerImpl.this.setScannerState(21); break;
/*      */               } 
/* 1380 */               XMLDocumentScannerImpl.this.setScannerState(22);
/*      */               break;
/*      */ 
/*      */             
/*      */             case 21:
/* 1385 */               XMLDocumentScannerImpl.this.fMarkupDepth++;
/* 1386 */               if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63, null)) {
/* 1387 */                 XMLDocumentScannerImpl.this.setScannerState(23); break;
/* 1388 */               }  if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33, null)) {
/* 1389 */                 XMLDocumentScannerImpl.this.setScannerState(27); break;
/* 1390 */               }  if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(47, null)) {
/* 1391 */                 XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null); break;
/*      */               } 
/* 1393 */               if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar()) || XMLDocumentScannerImpl.this
/* 1394 */                 .isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
/* 1395 */                 XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
/*      */                 
/* 1397 */                 XMLDocumentScannerImpl.this.scanStartElement();
/* 1398 */                 XMLDocumentScannerImpl.this.setScannerState(22); break;
/*      */               } 
/* 1400 */               XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", null);
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/* 1406 */         } while (XMLDocumentScannerImpl.this.fScannerState == 21 || XMLDocumentScannerImpl.this.fScannerState == 44);
/*      */ 
/*      */ 
/*      */         
/* 1410 */         switch (XMLDocumentScannerImpl.this.fScannerState) {
/*      */           case 23:
/* 1412 */             XMLDocumentScannerImpl.this.fContentBuffer.clear();
/* 1413 */             XMLDocumentScannerImpl.this.scanPI(XMLDocumentScannerImpl.this.fContentBuffer);
/* 1414 */             XMLDocumentScannerImpl.this.setScannerState(44);
/* 1415 */             return 3;
/*      */           
/*      */           case 27:
/* 1418 */             if (!XMLDocumentScannerImpl.this.fEntityScanner.skipString(XMLDocumentScannerImpl.COMMENTSTRING)) {
/* 1419 */               XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
/*      */             }
/* 1421 */             XMLDocumentScannerImpl.this.scanComment();
/* 1422 */             XMLDocumentScannerImpl.this.setScannerState(44);
/* 1423 */             return 5;
/*      */           
/*      */           case 22:
/* 1426 */             ch = XMLDocumentScannerImpl.this.fEntityScanner.peekChar();
/* 1427 */             if (ch == -1) {
/* 1428 */               XMLDocumentScannerImpl.this.setScannerState(34);
/* 1429 */               return 8;
/*      */             } 
/* 1431 */             XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInTrailingMisc", null);
/*      */             
/* 1433 */             XMLDocumentScannerImpl.this.fEntityScanner.scanChar(null);
/* 1434 */             XMLDocumentScannerImpl.this.setScannerState(44);
/* 1435 */             return 4;
/*      */ 
/*      */ 
/*      */           
/*      */           case 28:
/* 1440 */             XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInTrailingMisc", null);
/*      */             
/* 1442 */             XMLDocumentScannerImpl.this.setScannerState(44);
/* 1443 */             return 9;
/*      */ 
/*      */ 
/*      */           
/*      */           case 34:
/* 1448 */             XMLDocumentScannerImpl.this.setScannerState(48);
/*      */             
/* 1450 */             return 8;
/*      */           
/*      */           case 48:
/* 1453 */             throw new NoSuchElementException("No more events to be parsed");
/*      */         } 
/* 1455 */         throw new XNIException("Scanner State " + XMLDocumentScannerImpl.this.fScannerState + " not Recognized ");
/*      */       
/*      */       }
/* 1458 */       catch (EOFException e) {
/*      */ 
/*      */ 
/*      */         
/* 1462 */         if (XMLDocumentScannerImpl.this.fMarkupDepth != 0) {
/* 1463 */           XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", null);
/* 1464 */           return -1;
/*      */         } 
/*      */ 
/*      */         
/* 1468 */         XMLDocumentScannerImpl.this.setScannerState(34);
/*      */ 
/*      */         
/* 1471 */         return 8;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh(int refreshPosition) {
/* 1488 */     super.refresh(refreshPosition);
/* 1489 */     if (this.fReadingDTD) {
/* 1490 */       Entity entity = this.fEntityScanner.getCurrentEntity();
/* 1491 */       if (entity instanceof Entity.ScannedEntity) {
/* 1492 */         this.fEndPos = ((Entity.ScannedEntity)entity).position;
/*      */       }
/* 1494 */       this.fDTDDecl.append(((Entity.ScannedEntity)entity).ch, this.fStartPos, this.fEndPos - this.fStartPos);
/* 1495 */       this.fStartPos = refreshPosition;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLDocumentScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */