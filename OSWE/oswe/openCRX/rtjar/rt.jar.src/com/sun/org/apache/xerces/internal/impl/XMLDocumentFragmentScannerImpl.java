/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.AugmentationsImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLAttributesIteratorImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.xml.internal.stream.XMLBufferListener;
/*      */ import com.sun.xml.internal.stream.XMLEntityStorage;
/*      */ import com.sun.xml.internal.stream.dtd.DTDGrammarUtil;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLDocumentFragmentScannerImpl
/*      */   extends XMLScanner
/*      */   implements XMLDocumentScanner, XMLComponent, XMLEntityHandler, XMLBufferListener
/*      */ {
/*      */   protected int fElementAttributeLimit;
/*      */   protected int fXMLNameLimit;
/*      */   protected ExternalSubsetResolver fExternalSubsetResolver;
/*      */   protected static final int SCANNER_STATE_START_OF_MARKUP = 21;
/*      */   protected static final int SCANNER_STATE_CONTENT = 22;
/*      */   protected static final int SCANNER_STATE_PI = 23;
/*      */   protected static final int SCANNER_STATE_DOCTYPE = 24;
/*      */   protected static final int SCANNER_STATE_XML_DECL = 25;
/*      */   protected static final int SCANNER_STATE_ROOT_ELEMENT = 26;
/*      */   protected static final int SCANNER_STATE_COMMENT = 27;
/*      */   protected static final int SCANNER_STATE_REFERENCE = 28;
/*      */   protected static final int SCANNER_STATE_ATTRIBUTE = 29;
/*      */   protected static final int SCANNER_STATE_ATTRIBUTE_VALUE = 30;
/*      */   protected static final int SCANNER_STATE_END_OF_INPUT = 33;
/*      */   protected static final int SCANNER_STATE_TERMINATED = 34;
/*      */   protected static final int SCANNER_STATE_CDATA = 35;
/*      */   protected static final int SCANNER_STATE_TEXT_DECL = 36;
/*      */   protected static final int SCANNER_STATE_CHARACTER_DATA = 37;
/*      */   protected static final int SCANNER_STATE_START_ELEMENT_TAG = 38;
/*      */   protected static final int SCANNER_STATE_END_ELEMENT_TAG = 39;
/*      */   protected static final int SCANNER_STATE_CHAR_REFERENCE = 40;
/*      */   protected static final int SCANNER_STATE_BUILT_IN_REFS = 41;
/*      */   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
/*      */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */   protected static final String STANDARD_URI_CONFORMANT = "http://apache.org/xml/features/standard-uri-conformant";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   static final String EXTERNAL_ACCESS_DEFAULT = "all";
/*  175 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://xml.org/sax/features/namespaces", "http://xml.org/sax/features/validation", "http://apache.org/xml/features/scanner/notify-builtin-refs", "http://apache.org/xml/features/scanner/notify-char-refs", "report-cdata-event" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  184 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[] { Boolean.TRUE, null, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  193 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null, null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   private static final char[] cdata = new char[] { '[', 'C', 'D', 'A', 'T', 'A', '[' };
/*  209 */   static final char[] xmlDecl = new char[] { '<', '?', 'x', 'm', 'l' };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_SCANNER_STATE = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_DISPATCHER = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final boolean DEBUG_START_END_ELEMENT = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final boolean DEBUG_NEXT = false;
/*      */ 
/*      */   
/*      */   protected static final boolean DEBUG = false;
/*      */ 
/*      */   
/*      */   protected static final boolean DEBUG_COALESCE = false;
/*      */ 
/*      */   
/*      */   protected XMLDocumentHandler fDocumentHandler;
/*      */ 
/*      */   
/*      */   protected int fScannerLastState;
/*      */ 
/*      */   
/*      */   protected XMLEntityStorage fEntityStore;
/*      */ 
/*      */   
/*  243 */   protected int[] fEntityStack = new int[4];
/*      */ 
/*      */   
/*      */   protected int fMarkupDepth;
/*      */ 
/*      */   
/*      */   protected boolean fEmptyElement;
/*      */ 
/*      */   
/*      */   protected boolean fReadingAttributes = false;
/*      */ 
/*      */   
/*      */   protected int fScannerState;
/*      */ 
/*      */   
/*      */   protected boolean fInScanContent = false;
/*      */ 
/*      */   
/*      */   protected boolean fLastSectionWasCData = false;
/*      */ 
/*      */   
/*      */   protected boolean fLastSectionWasEntityReference = false;
/*      */   
/*      */   protected boolean fLastSectionWasCharacterData = false;
/*      */   
/*      */   protected boolean fHasExternalDTD;
/*      */   
/*      */   protected boolean fStandaloneSet;
/*      */   
/*      */   protected boolean fStandalone;
/*      */   
/*      */   protected String fVersion;
/*      */   
/*      */   protected QName fCurrentElement;
/*      */   
/*  278 */   protected ElementStack fElementStack = new ElementStack();
/*  279 */   protected ElementStack2 fElementStack2 = new ElementStack2();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String fPITarget;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   protected XMLString fPIData = new XMLString();
/*      */ 
/*      */   
/*      */   protected boolean fNotifyBuiltInRefs = false;
/*      */   
/*      */   protected boolean fSupportDTD = true;
/*      */   
/*      */   protected boolean fReplaceEntityReferences = true;
/*      */   
/*      */   protected boolean fSupportExternalEntities = false;
/*      */   
/*      */   protected boolean fReportCdataEvent = false;
/*      */   
/*      */   protected boolean fIsCoalesce = false;
/*      */   
/*  306 */   protected String fDeclaredEncoding = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fDisallowDoctype = false;
/*      */ 
/*      */ 
/*      */   
/*  314 */   protected String fAccessExternalDTD = "all";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fStrictURI;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Driver fDriver;
/*      */ 
/*      */ 
/*      */   
/*  328 */   protected Driver fContentDriver = createContentDriver();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  333 */   protected QName fElementQName = new QName();
/*      */ 
/*      */   
/*  336 */   protected QName fAttributeQName = new QName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  343 */   protected XMLAttributesIteratorImpl fAttributes = new XMLAttributesIteratorImpl();
/*      */ 
/*      */ 
/*      */   
/*  347 */   protected XMLString fTempString = new XMLString();
/*      */ 
/*      */   
/*  350 */   protected XMLString fTempString2 = new XMLString();
/*      */ 
/*      */   
/*  353 */   private String[] fStrings = new String[3];
/*      */ 
/*      */   
/*  356 */   protected XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*      */ 
/*      */   
/*  359 */   protected XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
/*      */ 
/*      */ 
/*      */   
/*  363 */   protected XMLStringBuffer fContentBuffer = new XMLStringBuffer();
/*      */ 
/*      */   
/*  366 */   private final char[] fSingleChar = new char[1];
/*  367 */   private String fCurrentEntityName = null;
/*      */ 
/*      */   
/*      */   protected boolean fScanToEnd = false;
/*      */   
/*  372 */   protected DTDGrammarUtil dtdGrammarUtil = null;
/*      */   
/*      */   protected boolean fAddDefaultAttr = false;
/*      */   
/*      */   protected boolean foundBuiltInRefs = false;
/*      */   
/*      */   static final short MAX_DEPTH_LIMIT = 5;
/*      */   
/*      */   static final short ELEMENT_ARRAY_LENGTH = 200;
/*      */   
/*      */   static final short MAX_POINTER_AT_A_DEPTH = 4;
/*      */   
/*      */   static final boolean DEBUG_SKIP_ALGORITHM = false;
/*  385 */   String[] fElementArray = new String[200];
/*      */   
/*  387 */   short fLastPointerLocation = 0;
/*  388 */   short fElementPointer = 0;
/*      */   
/*  390 */   short[][] fPointerInfo = new short[5][4];
/*      */   
/*      */   protected String fElementRawname;
/*      */   
/*      */   protected boolean fShouldSkip = false;
/*      */   protected boolean fAdd = false;
/*      */   protected boolean fSkip = false;
/*  397 */   private Augmentations fTempAugmentations = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fUsebuffer;
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
/*  418 */     this.fEntityManager.setEntityHandler(this);
/*  419 */     this.fEntityManager.startEntity(false, "$fragment$", inputSource, false, true);
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
/*      */   public boolean scanDocument(boolean complete) throws IOException, XNIException {
/*  440 */     this.fEntityManager.setEntityHandler(this);
/*      */ 
/*      */     
/*  443 */     int event = next();
/*      */     do {
/*  445 */       switch (event) {
/*      */         case 7:
/*      */         case 1:
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/*  454 */           this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*  455 */           this.fDocumentHandler.characters(getCharacterData(), null);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 6:
/*      */           break;
/*      */         
/*      */         case 9:
/*  463 */           this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*      */           break;
/*      */         
/*      */         case 3:
/*  467 */           this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*  468 */           this.fDocumentHandler.processingInstruction(getPITarget(), getPIData(), null);
/*      */           break;
/*      */         case 5:
/*  471 */           this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*  472 */           this.fDocumentHandler.comment(getCharacterData(), null);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 11:
/*      */           break;
/*      */         
/*      */         case 12:
/*  480 */           this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*  481 */           this.fDocumentHandler.startCDATA(null);
/*      */           
/*  483 */           this.fDocumentHandler.characters(getCharacterData(), null);
/*  484 */           this.fDocumentHandler.endCDATA(null);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 14:
/*      */         case 15:
/*      */         case 13:
/*      */         case 10:
/*      */         case 2:
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  501 */           throw new InternalError("processing event: " + event);
/*      */       } 
/*      */ 
/*      */       
/*  505 */       event = next();
/*      */     }
/*  507 */     while (event != 8 && complete);
/*      */     
/*  509 */     if (event == 8) {
/*  510 */       this.fDocumentHandler.endDocument(null);
/*  511 */       return false;
/*      */     } 
/*      */     
/*  514 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getElementQName() {
/*  521 */     if (this.fScannerLastState == 2) {
/*  522 */       this.fElementQName.setValues(this.fElementStack.getLastPoppedElement());
/*      */     }
/*  524 */     return this.fElementQName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int next() throws IOException, XNIException {
/*  532 */     return this.fDriver.next();
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
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  557 */     super.reset(componentManager);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  566 */     this.fReportCdataEvent = componentManager.getFeature("report-cdata-event", true);
/*  567 */     this.fSecurityManager = (XMLSecurityManager)componentManager.getProperty("http://apache.org/xml/properties/security-manager", null);
/*  568 */     this.fNotifyBuiltInRefs = componentManager.getFeature("http://apache.org/xml/features/scanner/notify-builtin-refs", false);
/*      */     
/*  570 */     Object resolver = componentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver", null);
/*  571 */     this.fExternalSubsetResolver = (resolver instanceof ExternalSubsetResolver) ? (ExternalSubsetResolver)resolver : null;
/*      */ 
/*      */ 
/*      */     
/*  575 */     this.fReadingAttributes = false;
/*      */ 
/*      */     
/*  578 */     this.fSupportExternalEntities = true;
/*  579 */     this.fReplaceEntityReferences = true;
/*  580 */     this.fIsCoalesce = false;
/*      */ 
/*      */     
/*  583 */     setScannerState(22);
/*  584 */     setDriver(this.fContentDriver);
/*      */ 
/*      */ 
/*      */     
/*  588 */     XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)componentManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", null);
/*  589 */     this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */     
/*  591 */     this.fStrictURI = componentManager.getFeature("http://apache.org/xml/features/standard-uri-conformant", false);
/*      */     
/*  593 */     resetCommon();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager propertyManager) {
/*  600 */     super.reset(propertyManager);
/*      */ 
/*      */ 
/*      */     
/*  604 */     this.fNamespaces = ((Boolean)propertyManager.getProperty("javax.xml.stream.isNamespaceAware")).booleanValue();
/*  605 */     this.fNotifyBuiltInRefs = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  610 */     Boolean bo = (Boolean)propertyManager.getProperty("javax.xml.stream.isReplacingEntityReferences");
/*  611 */     this.fReplaceEntityReferences = bo.booleanValue();
/*  612 */     bo = (Boolean)propertyManager.getProperty("javax.xml.stream.isSupportingExternalEntities");
/*  613 */     this.fSupportExternalEntities = bo.booleanValue();
/*  614 */     Boolean cdata = (Boolean)propertyManager.getProperty("http://java.sun.com/xml/stream/properties/report-cdata-event");
/*  615 */     if (cdata != null)
/*  616 */       this.fReportCdataEvent = cdata.booleanValue(); 
/*  617 */     Boolean coalesce = (Boolean)propertyManager.getProperty("javax.xml.stream.isCoalescing");
/*  618 */     if (coalesce != null)
/*  619 */       this.fIsCoalesce = coalesce.booleanValue(); 
/*  620 */     this.fReportCdataEvent = this.fIsCoalesce ? false : (this.fReportCdataEvent);
/*      */ 
/*      */     
/*  623 */     this.fReplaceEntityReferences = this.fIsCoalesce ? true : this.fReplaceEntityReferences;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)propertyManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager");
/*  633 */     this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */     
/*  635 */     this.fSecurityManager = (XMLSecurityManager)propertyManager.getProperty("http://apache.org/xml/properties/security-manager");
/*  636 */     resetCommon();
/*      */   }
/*      */ 
/*      */   
/*      */   void resetCommon() {
/*  641 */     this.fMarkupDepth = 0;
/*  642 */     this.fCurrentElement = null;
/*  643 */     this.fElementStack.clear();
/*  644 */     this.fHasExternalDTD = false;
/*  645 */     this.fStandaloneSet = false;
/*  646 */     this.fStandalone = false;
/*  647 */     this.fInScanContent = false;
/*      */     
/*  649 */     this.fShouldSkip = false;
/*  650 */     this.fAdd = false;
/*  651 */     this.fSkip = false;
/*      */     
/*  653 */     this.fEntityStore = this.fEntityManager.getEntityStore();
/*  654 */     this.dtdGrammarUtil = null;
/*      */     
/*  656 */     if (this.fSecurityManager != null) {
/*  657 */       this.fElementAttributeLimit = this.fSecurityManager.getLimit(XMLSecurityManager.Limit.ELEMENT_ATTRIBUTE_LIMIT);
/*  658 */       this.fXMLNameLimit = this.fSecurityManager.getLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT);
/*      */     } else {
/*  660 */       this.fElementAttributeLimit = 0;
/*  661 */       this.fXMLNameLimit = XMLSecurityManager.Limit.MAX_NAME_LIMIT.defaultValue();
/*      */     } 
/*  663 */     this.fLimitAnalyzer = this.fEntityManager.fLimitAnalyzer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedFeatures() {
/*  672 */     return (String[])RECOGNIZED_FEATURES.clone();
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
/*  693 */     super.setFeature(featureId, state);
/*      */ 
/*      */     
/*  696 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/*  697 */       String feature = featureId.substring("http://apache.org/xml/features/".length());
/*  698 */       if (feature.equals("scanner/notify-builtin-refs")) {
/*  699 */         this.fNotifyBuiltInRefs = state;
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
/*  711 */     return (String[])RECOGNIZED_PROPERTIES.clone();
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
/*  732 */     super.setProperty(propertyId, value);
/*      */ 
/*      */     
/*  735 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/*  736 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*  737 */       if (suffixLength == "internal/entity-manager".length() && propertyId
/*  738 */         .endsWith("internal/entity-manager")) {
/*  739 */         this.fEntityManager = (XMLEntityManager)value;
/*      */         return;
/*      */       } 
/*  742 */       if (suffixLength == "internal/entity-resolver".length() && propertyId
/*  743 */         .endsWith("internal/entity-resolver")) {
/*  744 */         this.fExternalSubsetResolver = (value instanceof ExternalSubsetResolver) ? (ExternalSubsetResolver)value : null;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  752 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/*  753 */       String property = propertyId.substring("http://apache.org/xml/properties/".length());
/*  754 */       if (property.equals("internal/entity-manager")) {
/*  755 */         this.fEntityManager = (XMLEntityManager)value;
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  761 */     if (propertyId.equals("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager")) {
/*      */       
/*  763 */       XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)value;
/*  764 */       this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
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
/*      */   public Boolean getFeatureDefault(String featureId) {
/*  779 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/*  780 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  781 */         return FEATURE_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  784 */     return null;
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
/*  797 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/*  798 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  799 */         return PROPERTY_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  802 */     return null;
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
/*      */   public void setDocumentHandler(XMLDocumentHandler documentHandler) {
/*  815 */     this.fDocumentHandler = documentHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDocumentHandler getDocumentHandler() {
/*  822 */     return this.fDocumentHandler;
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
/*      */   public void startEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/*  850 */     if (this.fEntityDepth == this.fEntityStack.length) {
/*  851 */       int[] entityarray = new int[this.fEntityStack.length * 2];
/*  852 */       System.arraycopy(this.fEntityStack, 0, entityarray, 0, this.fEntityStack.length);
/*  853 */       this.fEntityStack = entityarray;
/*      */     } 
/*  855 */     this.fEntityStack[this.fEntityDepth] = this.fMarkupDepth;
/*      */     
/*  857 */     super.startEntity(name, identifier, encoding, augs);
/*      */ 
/*      */     
/*  860 */     if (this.fStandalone && this.fEntityStore.isEntityDeclInExternalSubset(name)) {
/*  861 */       reportFatalError("MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     if (this.fDocumentHandler != null && !this.fScanningAttribute && 
/*  868 */       !name.equals("[xml]")) {
/*  869 */       this.fDocumentHandler.startGeneralEntity(name, identifier, encoding, augs);
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
/*      */   public void endEntity(String name, Augmentations augs) throws IOException, XNIException {
/*  895 */     super.endEntity(name, augs);
/*      */ 
/*      */     
/*  898 */     if (this.fMarkupDepth != this.fEntityStack[this.fEntityDepth]) {
/*  899 */       reportFatalError("MarkupEntityMismatch", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  904 */     if (this.fDocumentHandler != null && !this.fScanningAttribute && 
/*  905 */       !name.equals("[xml]")) {
/*  906 */       this.fDocumentHandler.endGeneralEntity(name, augs);
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
/*      */   protected Driver createContentDriver() {
/*  921 */     return new FragmentContentDriver();
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
/*      */   protected void scanXMLDeclOrTextDecl(boolean scanningTextDecl) throws IOException, XNIException {
/*  948 */     scanXMLDeclOrTextDecl(scanningTextDecl, this.fStrings);
/*  949 */     this.fMarkupDepth--;
/*      */ 
/*      */     
/*  952 */     String version = this.fStrings[0];
/*  953 */     String encoding = this.fStrings[1];
/*  954 */     String standalone = this.fStrings[2];
/*  955 */     this.fDeclaredEncoding = encoding;
/*      */     
/*  957 */     this.fStandaloneSet = (standalone != null);
/*  958 */     this.fStandalone = (this.fStandaloneSet && standalone.equals("yes"));
/*      */ 
/*      */     
/*  961 */     this.fEntityManager.setStandalone(this.fStandalone);
/*      */ 
/*      */ 
/*      */     
/*  965 */     if (this.fDocumentHandler != null) {
/*  966 */       if (scanningTextDecl) {
/*  967 */         this.fDocumentHandler.textDecl(version, encoding, null);
/*      */       } else {
/*  969 */         this.fDocumentHandler.xmlDecl(version, encoding, standalone, null);
/*      */       } 
/*      */     }
/*      */     
/*  973 */     if (version != null) {
/*  974 */       this.fEntityScanner.setVersion(version);
/*  975 */       this.fEntityScanner.setXMLVersion(version);
/*      */     } 
/*      */     
/*  978 */     if (encoding != null && !this.fEntityScanner.getCurrentEntity().isEncodingExternallySpecified()) {
/*  979 */       this.fEntityScanner.setEncoding(encoding);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPITarget() {
/*  985 */     return this.fPITarget;
/*      */   }
/*      */   
/*      */   public XMLStringBuffer getPIData() {
/*  989 */     return this.fContentBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLString getCharacterData() {
/*  994 */     if (this.fUsebuffer) {
/*  995 */       return this.fContentBuffer;
/*      */     }
/*  997 */     return this.fTempString;
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
/*      */   protected void scanPIData(String target, XMLStringBuffer data) throws IOException, XNIException {
/* 1014 */     super.scanPIData(target, data);
/*      */ 
/*      */     
/* 1017 */     this.fPITarget = target;
/*      */     
/* 1019 */     this.fMarkupDepth--;
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
/*      */   protected void scanComment() throws IOException, XNIException {
/* 1033 */     this.fContentBuffer.clear();
/* 1034 */     scanComment(this.fContentBuffer);
/*      */     
/* 1036 */     this.fUsebuffer = true;
/* 1037 */     this.fMarkupDepth--;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getComment() {
/* 1043 */     return this.fContentBuffer.toString();
/*      */   }
/*      */   
/*      */   void addElement(String rawname) {
/* 1047 */     if (this.fElementPointer < 200) {
/*      */       
/* 1049 */       this.fElementArray[this.fElementPointer] = rawname;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1062 */       if (this.fElementStack.fDepth < 5) {
/* 1063 */         short column = storePointerForADepth(this.fElementPointer);
/* 1064 */         if (column > 0) {
/* 1065 */           short pointer = getElementPointer((short)this.fElementStack.fDepth, (short)(column - 1));
/*      */ 
/*      */           
/* 1068 */           if (rawname == this.fElementArray[pointer]) {
/* 1069 */             this.fShouldSkip = true;
/* 1070 */             this.fLastPointerLocation = pointer;
/*      */             
/* 1072 */             resetPointer((short)this.fElementStack.fDepth, column);
/* 1073 */             this.fElementArray[this.fElementPointer] = null;
/*      */             return;
/*      */           } 
/* 1076 */           this.fShouldSkip = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1080 */       this.fElementPointer = (short)(this.fElementPointer + 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void resetPointer(short depth, short column) {
/* 1086 */     this.fPointerInfo[depth][column] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   short storePointerForADepth(short elementPointer) {
/* 1091 */     short depth = (short)this.fElementStack.fDepth;
/*      */ 
/*      */ 
/*      */     
/* 1095 */     for (short i = 0; i < 4; i = (short)(i + 1)) {
/*      */       
/* 1097 */       if (canStore(depth, i)) {
/* 1098 */         this.fPointerInfo[depth][i] = elementPointer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1107 */         return i;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1112 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean canStore(short depth, short column) {
/* 1119 */     return (this.fPointerInfo[depth][column] == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   short getElementPointer(short depth, short column) {
/* 1127 */     return this.fPointerInfo[depth][column];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean skipFromTheBuffer(String rawname) throws IOException {
/* 1133 */     if (this.fEntityScanner.skipString(rawname)) {
/* 1134 */       char c = (char)this.fEntityScanner.peekChar();
/*      */ 
/*      */       
/* 1137 */       if (c == ' ' || c == '/' || c == '>') {
/* 1138 */         this.fElementRawname = rawname;
/* 1139 */         return true;
/*      */       } 
/* 1141 */       return false;
/*      */     } 
/*      */     
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean skipQElement(String rawname) throws IOException {
/* 1149 */     int c = this.fEntityScanner.getChar(rawname.length());
/*      */     
/* 1151 */     if (XMLChar.isName(c)) {
/* 1152 */       return false;
/*      */     }
/* 1154 */     return this.fEntityScanner.skipString(rawname);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean skipElement() throws IOException {
/* 1160 */     if (!this.fShouldSkip) return false;
/*      */     
/* 1162 */     if (this.fLastPointerLocation != 0) {
/*      */       
/* 1164 */       String rawname = this.fElementArray[this.fLastPointerLocation + 1];
/* 1165 */       if (rawname != null && skipFromTheBuffer(rawname)) {
/* 1166 */         this.fLastPointerLocation = (short)(this.fLastPointerLocation + 1);
/*      */ 
/*      */ 
/*      */         
/* 1170 */         return true;
/*      */       } 
/*      */       
/* 1173 */       this.fLastPointerLocation = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     return (this.fShouldSkip && skipElement((short)0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean skipElement(short column) throws IOException {
/* 1187 */     short depth = (short)this.fElementStack.fDepth;
/*      */     
/* 1189 */     if (depth > 5) {
/* 1190 */       return this.fShouldSkip = false;
/*      */     }
/* 1192 */     for (short i = column; i < 4; i = (short)(i + 1)) {
/* 1193 */       short pointer = getElementPointer(depth, i);
/*      */       
/* 1195 */       if (pointer == 0) {
/* 1196 */         return this.fShouldSkip = false;
/*      */       }
/*      */       
/* 1199 */       if (this.fElementArray[pointer] != null && skipFromTheBuffer(this.fElementArray[pointer])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1205 */         this.fLastPointerLocation = pointer;
/* 1206 */         return this.fShouldSkip = true;
/*      */       } 
/*      */     } 
/* 1209 */     return this.fShouldSkip = false;
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
/*      */   protected boolean scanStartElement() throws IOException, XNIException {
/* 1241 */     if (this.fSkip && !this.fAdd) {
/*      */ 
/*      */ 
/*      */       
/* 1245 */       QName name = this.fElementStack.getNext();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1252 */       this.fSkip = this.fEntityScanner.skipString(name.rawname);
/*      */       
/* 1254 */       if (this.fSkip) {
/*      */ 
/*      */ 
/*      */         
/* 1258 */         this.fElementStack.push();
/* 1259 */         this.fElementQName = name;
/*      */       } else {
/*      */         
/* 1262 */         this.fElementStack.reposition();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1272 */     if (!this.fSkip || this.fAdd) {
/*      */       
/* 1274 */       this.fElementQName = this.fElementStack.nextElement();
/*      */       
/* 1276 */       if (this.fNamespaces) {
/* 1277 */         this.fEntityScanner.scanQName(this.fElementQName, XMLScanner.NameType.ELEMENTSTART);
/*      */       } else {
/* 1279 */         String name = this.fEntityScanner.scanName(XMLScanner.NameType.ELEMENTSTART);
/* 1280 */         this.fElementQName.setValues(null, name, name, null);
/*      */       } 
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
/* 1293 */     if (this.fAdd)
/*      */     {
/* 1295 */       this.fElementStack.matchElement(this.fElementQName);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1300 */     this.fCurrentElement = this.fElementQName;
/*      */     
/* 1302 */     String rawname = this.fElementQName.rawname;
/*      */     
/* 1304 */     this.fEmptyElement = false;
/*      */     
/* 1306 */     this.fAttributes.removeAllAttributes();
/*      */     
/* 1308 */     checkDepth(rawname);
/* 1309 */     if (!seekCloseOfStartTag()) {
/* 1310 */       this.fReadingAttributes = true;
/* 1311 */       this.fAttributeCacheUsedCount = 0;
/* 1312 */       this.fStringBufferIndex = 0;
/* 1313 */       this.fAddDefaultAttr = true;
/*      */       while (true) {
/* 1315 */         scanAttribute(this.fAttributes);
/* 1316 */         if (this.fSecurityManager != null && !this.fSecurityManager.isNoLimit(this.fElementAttributeLimit) && this.fAttributes
/* 1317 */           .getLength() > this.fElementAttributeLimit) {
/* 1318 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ElementAttributeLimit", new Object[] { rawname, 
/*      */                 
/* 1320 */                 Integer.valueOf(this.fElementAttributeLimit) }, (short)2);
/*      */         }
/*      */ 
/*      */         
/* 1324 */         if (seekCloseOfStartTag()) {
/* 1325 */           this.fReadingAttributes = false; break;
/*      */         } 
/*      */       } 
/* 1328 */     }  if (this.fEmptyElement) {
/*      */       
/* 1330 */       this.fMarkupDepth--;
/*      */ 
/*      */       
/* 1333 */       if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 1334 */         reportFatalError("ElementEntityMismatch", new Object[] { this.fCurrentElement.rawname });
/*      */       }
/*      */ 
/*      */       
/* 1338 */       if (this.fDocumentHandler != null) {
/* 1339 */         this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1349 */       this.fElementStack.popElement();
/*      */     }
/*      */     else {
/*      */       
/* 1353 */       if (this.dtdGrammarUtil != null)
/* 1354 */         this.dtdGrammarUtil.startElement(this.fElementQName, this.fAttributes); 
/* 1355 */       if (this.fDocumentHandler != null)
/*      */       {
/*      */ 
/*      */         
/* 1359 */         this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, null);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1365 */     return this.fEmptyElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean seekCloseOfStartTag() throws IOException, XNIException {
/* 1375 */     boolean sawSpace = this.fEntityScanner.skipSpaces();
/*      */ 
/*      */     
/* 1378 */     int c = this.fEntityScanner.peekChar();
/* 1379 */     if (c == 62) {
/* 1380 */       this.fEntityScanner.scanChar(null);
/* 1381 */       return true;
/* 1382 */     }  if (c == 47) {
/* 1383 */       this.fEntityScanner.scanChar(null);
/* 1384 */       if (!this.fEntityScanner.skipChar(62, XMLScanner.NameType.ELEMENTEND)) {
/* 1385 */         reportFatalError("ElementUnterminated", new Object[] { this.fElementQName.rawname });
/*      */       }
/*      */       
/* 1388 */       this.fEmptyElement = true;
/* 1389 */       return true;
/* 1390 */     }  if (!isValidNameStartChar(c) || !sawSpace)
/*      */     {
/*      */       
/* 1393 */       if (!isValidNameStartHighSurrogate(c) || !sawSpace) {
/* 1394 */         reportFatalError("ElementUnterminated", new Object[] { this.fElementQName.rawname });
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1399 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasAttributes() {
/* 1403 */     return (this.fAttributes.getLength() > 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLAttributesIteratorImpl getAttributeIterator() {
/* 1483 */     if (this.dtdGrammarUtil != null && this.fAddDefaultAttr) {
/* 1484 */       this.dtdGrammarUtil.addDTDDefaultAttrs(this.fElementQName, this.fAttributes);
/* 1485 */       this.fAddDefaultAttr = false;
/*      */     } 
/* 1487 */     return this.fAttributes;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean standaloneSet() {
/* 1492 */     return this.fStandaloneSet;
/*      */   }
/*      */   
/*      */   public boolean isStandAlone() {
/* 1496 */     return this.fStandalone;
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
/*      */   protected void scanAttribute(XMLAttributes attributes) throws IOException, XNIException {
/* 1521 */     if (this.fNamespaces) {
/* 1522 */       this.fEntityScanner.scanQName(this.fAttributeQName, XMLScanner.NameType.ATTRIBUTENAME);
/*      */     } else {
/* 1524 */       String name = this.fEntityScanner.scanName(XMLScanner.NameType.ATTRIBUTENAME);
/* 1525 */       this.fAttributeQName.setValues(null, name, name, null);
/*      */     } 
/*      */ 
/*      */     
/* 1529 */     this.fEntityScanner.skipSpaces();
/* 1530 */     if (!this.fEntityScanner.skipChar(61, XMLScanner.NameType.ATTRIBUTE)) {
/* 1531 */       reportFatalError("EqRequiredInAttribute", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*      */     }
/*      */     
/* 1534 */     this.fEntityScanner.skipSpaces();
/*      */     
/* 1536 */     int attIndex = 0;
/*      */     
/* 1538 */     boolean isVC = (this.fHasExternalDTD && !this.fStandalone);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1546 */     XMLString tmpStr = getString();
/*      */     
/* 1548 */     scanAttributeValue(tmpStr, this.fTempString2, this.fAttributeQName.rawname, attributes, attIndex, isVC, this.fCurrentElement.rawname, false);
/*      */ 
/*      */ 
/*      */     
/* 1552 */     int oldLen = attributes.getLength();
/*      */     
/* 1554 */     attIndex = attributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1559 */     if (oldLen == attributes.getLength()) {
/* 1560 */       reportFatalError("AttributeNotUnique", new Object[] { this.fCurrentElement.rawname, this.fAttributeQName.rawname });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1567 */     attributes.setValue(attIndex, null, tmpStr);
/*      */ 
/*      */ 
/*      */     
/* 1571 */     attributes.setSpecified(attIndex, true);
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
/*      */   protected int scanContent(XMLStringBuffer content) throws IOException, XNIException {
/* 1590 */     this.fTempString.length = 0;
/* 1591 */     int c = this.fEntityScanner.scanContent(this.fTempString);
/* 1592 */     content.append(this.fTempString);
/* 1593 */     this.fTempString.length = 0;
/* 1594 */     if (c == 13) {
/*      */ 
/*      */       
/* 1597 */       this.fEntityScanner.scanChar(null);
/* 1598 */       content.append((char)c);
/* 1599 */       c = -1;
/* 1600 */     } else if (c == 93) {
/*      */ 
/*      */       
/* 1603 */       content.append((char)this.fEntityScanner.scanChar(null));
/*      */ 
/*      */ 
/*      */       
/* 1607 */       this.fInScanContent = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1612 */       if (this.fEntityScanner.skipChar(93, null)) {
/* 1613 */         content.append(']');
/* 1614 */         while (this.fEntityScanner.skipChar(93, null)) {
/* 1615 */           content.append(']');
/*      */         }
/* 1617 */         if (this.fEntityScanner.skipChar(62, null)) {
/* 1618 */           reportFatalError("CDEndInContent", null);
/*      */         }
/*      */       } 
/* 1621 */       this.fInScanContent = false;
/* 1622 */       c = -1;
/*      */     } 
/* 1624 */     if (this.fDocumentHandler == null || content.length > 0);
/*      */ 
/*      */     
/* 1627 */     return c;
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
/*      */   protected boolean scanCDATASection(XMLStringBuffer contentBuffer, boolean complete) throws IOException, XNIException {
/* 1648 */     if (this.fDocumentHandler != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1654 */     while (this.fEntityScanner.scanData("]]>", contentBuffer)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1683 */       int c = this.fEntityScanner.peekChar();
/* 1684 */       if (c != -1 && isInvalidLiteral(c)) {
/* 1685 */         if (XMLChar.isHighSurrogate(c)) {
/*      */ 
/*      */           
/* 1688 */           scanSurrogates(contentBuffer);
/*      */         } else {
/* 1690 */           reportFatalError("InvalidCharInCDSect", new Object[] {
/* 1691 */                 Integer.toString(c, 16) });
/* 1692 */           this.fEntityScanner.scanChar(null);
/*      */         } 
/*      */       }
/*      */       
/* 1696 */       if (this.fDocumentHandler != null);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1701 */     this.fMarkupDepth--;
/*      */     
/* 1703 */     if (this.fDocumentHandler == null || contentBuffer.length > 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1708 */     if (this.fDocumentHandler != null);
/*      */ 
/*      */ 
/*      */     
/* 1712 */     return true;
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
/*      */   protected int scanEndElement() throws IOException, XNIException {
/* 1734 */     QName endElementName = this.fElementStack.popElement();
/*      */     
/* 1736 */     String rawname = endElementName.rawname;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1748 */     if (!this.fEntityScanner.skipString(endElementName.rawname)) {
/* 1749 */       reportFatalError("ETagRequired", new Object[] { rawname });
/*      */     }
/*      */ 
/*      */     
/* 1753 */     this.fEntityScanner.skipSpaces();
/* 1754 */     if (!this.fEntityScanner.skipChar(62, XMLScanner.NameType.ELEMENTEND)) {
/* 1755 */       reportFatalError("ETagUnterminated", new Object[] { rawname });
/*      */     }
/*      */     
/* 1758 */     this.fMarkupDepth--;
/*      */ 
/*      */     
/* 1761 */     this.fMarkupDepth--;
/*      */ 
/*      */     
/* 1764 */     if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
/* 1765 */       reportFatalError("ElementEntityMismatch", new Object[] { rawname });
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
/* 1777 */     if (this.fDocumentHandler != null)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1782 */       this.fDocumentHandler.endElement(endElementName, null);
/*      */     }
/* 1784 */     if (this.dtdGrammarUtil != null) {
/* 1785 */       this.dtdGrammarUtil.endElement(endElementName);
/*      */     }
/* 1787 */     return this.fMarkupDepth;
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
/*      */   protected void scanCharReference() throws IOException, XNIException {
/* 1801 */     this.fStringBuffer2.clear();
/* 1802 */     int ch = scanCharReferenceValue(this.fStringBuffer2, null);
/* 1803 */     this.fMarkupDepth--;
/* 1804 */     if (ch != -1)
/*      */     {
/*      */       
/* 1807 */       if (this.fDocumentHandler != null) {
/* 1808 */         if (this.fNotifyCharRefs) {
/* 1809 */           this.fDocumentHandler.startGeneralEntity(this.fCharRefLiteral, null, null, null);
/*      */         }
/* 1811 */         Augmentations augs = null;
/* 1812 */         if (this.fValidation && ch <= 32) {
/* 1813 */           if (this.fTempAugmentations != null) {
/* 1814 */             this.fTempAugmentations.removeAllItems();
/*      */           } else {
/*      */             
/* 1817 */             this.fTempAugmentations = new AugmentationsImpl();
/*      */           } 
/* 1819 */           augs = this.fTempAugmentations;
/* 1820 */           augs.putItem("CHAR_REF_PROBABLE_WS", Boolean.TRUE);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1825 */         if (this.fNotifyCharRefs) {
/* 1826 */           this.fDocumentHandler.endGeneralEntity(this.fCharRefLiteral, null);
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
/*      */   protected void scanEntityReference(XMLStringBuffer content) throws IOException, XNIException {
/* 1844 */     String name = this.fEntityScanner.scanName(XMLScanner.NameType.REFERENCE);
/* 1845 */     if (name == null) {
/* 1846 */       reportFatalError("NameRequiredInReference", null);
/*      */       return;
/*      */     } 
/* 1849 */     if (!this.fEntityScanner.skipChar(59, XMLScanner.NameType.REFERENCE)) {
/* 1850 */       reportFatalError("SemicolonRequiredInReference", new Object[] { name });
/*      */     }
/* 1852 */     if (this.fEntityStore.isUnparsedEntity(name)) {
/* 1853 */       reportFatalError("ReferenceToUnparsedEntity", new Object[] { name });
/*      */     }
/* 1855 */     this.fMarkupDepth--;
/* 1856 */     this.fCurrentEntityName = name;
/*      */ 
/*      */     
/* 1859 */     if (name == fAmpSymbol) {
/* 1860 */       handleCharacter('&', fAmpSymbol, content);
/* 1861 */       this.fScannerState = 41; return;
/*      */     } 
/* 1863 */     if (name == fLtSymbol) {
/* 1864 */       handleCharacter('<', fLtSymbol, content);
/* 1865 */       this.fScannerState = 41; return;
/*      */     } 
/* 1867 */     if (name == fGtSymbol) {
/* 1868 */       handleCharacter('>', fGtSymbol, content);
/* 1869 */       this.fScannerState = 41; return;
/*      */     } 
/* 1871 */     if (name == fQuotSymbol) {
/* 1872 */       handleCharacter('"', fQuotSymbol, content);
/* 1873 */       this.fScannerState = 41; return;
/*      */     } 
/* 1875 */     if (name == fAposSymbol) {
/* 1876 */       handleCharacter('\'', fAposSymbol, content);
/* 1877 */       this.fScannerState = 41;
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1884 */     boolean isEE = this.fEntityStore.isExternalEntity(name);
/* 1885 */     if ((isEE && !this.fSupportExternalEntities) || (!isEE && !this.fReplaceEntityReferences) || this.foundBuiltInRefs) {
/* 1886 */       this.fScannerState = 28;
/*      */       
/*      */       return;
/*      */     } 
/* 1890 */     if (!this.fEntityStore.isDeclaredEntity(name)) {
/*      */       
/* 1892 */       if (!this.fSupportDTD && this.fReplaceEntityReferences) {
/* 1893 */         reportFatalError("EntityNotDeclared", new Object[] { name });
/*      */         
/*      */         return;
/*      */       } 
/* 1897 */       if (this.fHasExternalDTD && !this.fStandalone) {
/* 1898 */         if (this.fValidation) {
/* 1899 */           this.fErrorReporter.reportError(this.fEntityScanner, "http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { name }, (short)1);
/*      */         }
/*      */       } else {
/* 1902 */         reportFatalError("EntityNotDeclared", new Object[] { name });
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1908 */     this.fEntityManager.startEntity(true, name, false);
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
/*      */   void checkDepth(String elementName) {
/* 1921 */     this.fLimitAnalyzer.addValue(XMLSecurityManager.Limit.MAX_ELEMENT_DEPTH_LIMIT, elementName, this.fElementStack.fDepth);
/* 1922 */     if (this.fSecurityManager.isOverLimit(XMLSecurityManager.Limit.MAX_ELEMENT_DEPTH_LIMIT, this.fLimitAnalyzer)) {
/* 1923 */       this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/* 1924 */       reportFatalError("MaxElementDepthLimit", new Object[] { elementName, 
/* 1925 */             Integer.valueOf(this.fLimitAnalyzer.getTotalValue(XMLSecurityManager.Limit.MAX_ELEMENT_DEPTH_LIMIT)), 
/* 1926 */             Integer.valueOf(this.fSecurityManager.getLimit(XMLSecurityManager.Limit.MAX_ELEMENT_DEPTH_LIMIT)), "maxElementDepth" });
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
/*      */   private void handleCharacter(char c, String entity, XMLStringBuffer content) throws XNIException {
/* 1945 */     this.foundBuiltInRefs = true;
/* 1946 */     checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, 1);
/* 1947 */     content.append(c);
/* 1948 */     if (this.fDocumentHandler != null) {
/* 1949 */       this.fSingleChar[0] = c;
/* 1950 */       if (this.fNotifyBuiltInRefs) {
/* 1951 */         this.fDocumentHandler.startGeneralEntity(entity, null, null, null);
/*      */       }
/* 1953 */       this.fTempString.setValues(this.fSingleChar, 0, 1);
/*      */ 
/*      */       
/* 1956 */       if (this.fNotifyBuiltInRefs) {
/* 1957 */         this.fDocumentHandler.endGeneralEntity(entity, null);
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
/*      */   protected final void setScannerState(int state) {
/* 1971 */     this.fScannerState = state;
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
/*      */   protected final void setDriver(Driver driver) {
/* 1988 */     this.fDriver = driver;
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
/*      */   protected String getScannerStateName(int state) {
/* 2003 */     switch (state) { case 24:
/* 2004 */         return "SCANNER_STATE_DOCTYPE";
/* 2005 */       case 26: return "SCANNER_STATE_ROOT_ELEMENT";
/* 2006 */       case 21: return "SCANNER_STATE_START_OF_MARKUP";
/* 2007 */       case 27: return "SCANNER_STATE_COMMENT";
/* 2008 */       case 23: return "SCANNER_STATE_PI";
/* 2009 */       case 22: return "SCANNER_STATE_CONTENT";
/* 2010 */       case 28: return "SCANNER_STATE_REFERENCE";
/* 2011 */       case 33: return "SCANNER_STATE_END_OF_INPUT";
/* 2012 */       case 34: return "SCANNER_STATE_TERMINATED";
/* 2013 */       case 35: return "SCANNER_STATE_CDATA";
/* 2014 */       case 36: return "SCANNER_STATE_TEXT_DECL";
/* 2015 */       case 29: return "SCANNER_STATE_ATTRIBUTE";
/* 2016 */       case 30: return "SCANNER_STATE_ATTRIBUTE_VALUE";
/* 2017 */       case 38: return "SCANNER_STATE_START_ELEMENT_TAG";
/* 2018 */       case 39: return "SCANNER_STATE_END_ELEMENT_TAG";
/* 2019 */       case 37: return "SCANNER_STATE_CHARACTER_DATA"; }
/*      */ 
/*      */     
/* 2022 */     return "??? (" + state + ')';
/*      */   }
/*      */ 
/*      */   
/*      */   public String getEntityName() {
/* 2027 */     return this.fCurrentEntityName;
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
/*      */   public String getDriverName(Driver driver) {
/* 2047 */     return "null";
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
/*      */   String checkAccess(String systemId, String allowedProtocols) throws IOException {
/* 2059 */     String baseSystemId = this.fEntityScanner.getBaseSystemId();
/* 2060 */     String expandedSystemId = XMLEntityManager.expandSystemId(systemId, baseSystemId, this.fStrictURI);
/* 2061 */     return SecuritySupport.checkAccess(expandedSystemId, allowedProtocols, "all");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final class Element
/*      */   {
/*      */     public QName qname;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] fRawname;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element(QName qname, Element next) {
/* 2095 */       this.qname.setValues(qname);
/* 2096 */       this.fRawname = qname.rawname.toCharArray();
/* 2097 */       this.next = next;
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
/*      */   protected class ElementStack2
/*      */   {
/* 2114 */     protected QName[] fQName = new QName[20];
/*      */ 
/*      */     
/*      */     protected int fDepth;
/*      */ 
/*      */     
/*      */     protected int fCount;
/*      */ 
/*      */     
/*      */     protected int fPosition;
/*      */ 
/*      */     
/*      */     protected int fMark;
/*      */ 
/*      */     
/*      */     protected int fLastDepth;
/*      */ 
/*      */     
/*      */     public ElementStack2() {
/* 2133 */       for (int i = 0; i < this.fQName.length; i++) {
/* 2134 */         this.fQName[i] = new QName();
/*      */       }
/* 2136 */       this.fMark = this.fPosition = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void resize() {
/* 2147 */       int oldLength = this.fQName.length;
/* 2148 */       QName[] tmp = new QName[oldLength * 2];
/* 2149 */       System.arraycopy(this.fQName, 0, tmp, 0, oldLength);
/* 2150 */       this.fQName = tmp;
/*      */       
/* 2152 */       for (int i = oldLength; i < this.fQName.length; i++) {
/* 2153 */         this.fQName[i] = new QName();
/*      */       }
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
/*      */     
/*      */     public boolean matchElement(QName element) {
/* 2175 */       boolean match = false;
/* 2176 */       if (this.fLastDepth > this.fDepth && this.fDepth <= 2)
/*      */       {
/*      */ 
/*      */         
/* 2180 */         if (element.rawname == (this.fQName[this.fDepth]).rawname) {
/* 2181 */           XMLDocumentFragmentScannerImpl.this.fAdd = false;
/*      */ 
/*      */           
/* 2184 */           this.fMark = this.fDepth - 1;
/*      */           
/* 2186 */           this.fPosition = this.fMark + 1;
/* 2187 */           match = true;
/*      */           
/* 2189 */           this.fCount--;
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 2198 */           XMLDocumentFragmentScannerImpl.this.fAdd = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2203 */       this.fLastDepth = this.fDepth++;
/* 2204 */       return match;
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
/*      */     public QName nextElement() {
/* 2217 */       if (this.fCount == this.fQName.length) {
/* 2218 */         XMLDocumentFragmentScannerImpl.this.fShouldSkip = false;
/* 2219 */         XMLDocumentFragmentScannerImpl.this.fAdd = false;
/*      */ 
/*      */ 
/*      */         
/* 2223 */         return this.fQName[--this.fCount];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2228 */       return this.fQName[this.fCount++];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public QName getNext() {
/* 2238 */       if (this.fPosition == this.fCount) {
/* 2239 */         this.fPosition = this.fMark;
/*      */       }
/* 2241 */       return this.fQName[this.fPosition++];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int popElement() {
/* 2247 */       return this.fDepth--;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 2253 */       this.fLastDepth = 0;
/* 2254 */       this.fDepth = 0;
/* 2255 */       this.fCount = 0;
/* 2256 */       this.fPosition = this.fMark = 1;
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
/*      */   protected class ElementStack
/*      */   {
/*      */     protected QName[] fElements;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2276 */     protected int[] fInt = new int[20];
/*      */ 
/*      */     
/*      */     protected int fDepth;
/*      */ 
/*      */     
/*      */     protected int fCount;
/*      */ 
/*      */     
/*      */     protected int fPosition;
/*      */ 
/*      */     
/*      */     protected int fMark;
/*      */ 
/*      */     
/*      */     protected int fLastDepth;
/*      */ 
/*      */ 
/*      */     
/*      */     public ElementStack() {
/* 2296 */       this.fElements = new QName[20];
/* 2297 */       for (int i = 0; i < this.fElements.length; i++) {
/* 2298 */         this.fElements[i] = new QName();
/*      */       }
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
/*      */ 
/*      */     
/*      */     public QName pushElement(QName element) {
/* 2321 */       if (this.fDepth == this.fElements.length) {
/* 2322 */         QName[] array = new QName[this.fElements.length * 2];
/* 2323 */         System.arraycopy(this.fElements, 0, array, 0, this.fDepth);
/* 2324 */         this.fElements = array;
/* 2325 */         for (int i = this.fDepth; i < this.fElements.length; i++) {
/* 2326 */           this.fElements[i] = new QName();
/*      */         }
/*      */       } 
/* 2329 */       this.fElements[this.fDepth].setValues(element);
/* 2330 */       return this.fElements[this.fDepth++];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public QName getNext() {
/* 2340 */       if (this.fPosition == this.fCount) {
/* 2341 */         this.fPosition = this.fMark;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2349 */       return this.fElements[this.fPosition];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void push() {
/* 2359 */       this.fInt[++this.fDepth] = this.fPosition++;
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
/*      */     public boolean matchElement(QName element) {
/* 2375 */       boolean match = false;
/* 2376 */       if (this.fLastDepth > this.fDepth && this.fDepth <= 3)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 2381 */         if (element.rawname == (this.fElements[this.fDepth - 1]).rawname) {
/* 2382 */           XMLDocumentFragmentScannerImpl.this.fAdd = false;
/*      */ 
/*      */           
/* 2385 */           this.fMark = this.fDepth - 1;
/*      */           
/* 2387 */           this.fPosition = this.fMark;
/* 2388 */           match = true;
/*      */           
/* 2390 */           this.fCount--;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2401 */           XMLDocumentFragmentScannerImpl.this.fAdd = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2409 */       if (match) {
/*      */         
/* 2411 */         this.fInt[this.fDepth] = this.fPosition++;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2417 */         this.fInt[this.fDepth] = this.fCount - 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2422 */       if (this.fCount == this.fElements.length) {
/* 2423 */         XMLDocumentFragmentScannerImpl.this.fSkip = false;
/* 2424 */         XMLDocumentFragmentScannerImpl.this.fAdd = false;
/*      */         
/* 2426 */         reposition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2433 */         return false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2443 */       this.fLastDepth = this.fDepth;
/* 2444 */       return match;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public QName nextElement() {
/* 2455 */       if (XMLDocumentFragmentScannerImpl.this.fSkip) {
/* 2456 */         this.fDepth++;
/*      */         
/* 2458 */         return this.fElements[this.fCount++];
/* 2459 */       }  if (this.fDepth == this.fElements.length) {
/* 2460 */         QName[] array = new QName[this.fElements.length * 2];
/* 2461 */         System.arraycopy(this.fElements, 0, array, 0, this.fDepth);
/* 2462 */         this.fElements = array;
/* 2463 */         for (int i = this.fDepth; i < this.fElements.length; i++) {
/* 2464 */           this.fElements[i] = new QName();
/*      */         }
/*      */       } 
/*      */       
/* 2468 */       return this.fElements[this.fDepth++];
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
/*      */     public QName popElement() {
/* 2485 */       if (XMLDocumentFragmentScannerImpl.this.fSkip || XMLDocumentFragmentScannerImpl.this.fAdd)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 2490 */         return this.fElements[this.fInt[this.fDepth--]];
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2495 */       return this.fElements[--this.fDepth];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void reposition() {
/* 2505 */       for (int i = 2; i <= this.fDepth; i++) {
/* 2506 */         this.fElements[i - 1] = this.fElements[this.fInt[i]];
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {
/* 2517 */       this.fDepth = 0;
/* 2518 */       this.fLastDepth = 0;
/* 2519 */       this.fCount = 0;
/* 2520 */       this.fPosition = this.fMark = 1;
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
/*      */     public QName getLastPoppedElement() {
/* 2533 */       return this.fElements[this.fDepth];
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
/*      */   protected static interface Driver
/*      */   {
/*      */     int next() throws IOException, XNIException;
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
/*      */   protected class FragmentContentDriver
/*      */     implements Driver
/*      */   {
/*      */     private void startOfMarkup() throws IOException {
/* 2601 */       XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
/* 2602 */       int ch = XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar();
/*      */       
/* 2604 */       if (XMLDocumentFragmentScannerImpl.this.isValidNameStartChar(ch) || XMLDocumentFragmentScannerImpl.this.isValidNameStartHighSurrogate(ch)) {
/* 2605 */         XMLDocumentFragmentScannerImpl.this.setScannerState(38);
/*      */       } else {
/* 2607 */         switch (ch) {
/*      */           case 63:
/* 2609 */             XMLDocumentFragmentScannerImpl.this.setScannerState(23);
/* 2610 */             XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(ch, null);
/*      */             return;
/*      */           
/*      */           case 33:
/* 2614 */             XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(ch, null);
/* 2615 */             if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(45, null)) {
/* 2616 */               if (!XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(45, XMLScanner.NameType.COMMENT)) {
/* 2617 */                 XMLDocumentFragmentScannerImpl.this.reportFatalError("InvalidCommentStart", null);
/*      */               }
/*      */               
/* 2620 */               XMLDocumentFragmentScannerImpl.this.setScannerState(27);
/* 2621 */             } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipString(XMLDocumentFragmentScannerImpl.cdata)) {
/* 2622 */               XMLDocumentFragmentScannerImpl.this.setScannerState(35);
/* 2623 */             } else if (!scanForDoctypeHook()) {
/* 2624 */               XMLDocumentFragmentScannerImpl.this.reportFatalError("MarkupNotRecognizedInContent", null);
/*      */             } 
/*      */             return;
/*      */ 
/*      */           
/*      */           case 47:
/* 2630 */             XMLDocumentFragmentScannerImpl.this.setScannerState(39);
/* 2631 */             XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(ch, XMLScanner.NameType.ELEMENTEND);
/*      */             return;
/*      */         } 
/*      */         
/* 2635 */         XMLDocumentFragmentScannerImpl.this.reportFatalError("MarkupNotRecognizedInContent", null);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void startOfContent() throws IOException {
/* 2643 */       if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(60, null)) {
/* 2644 */         XMLDocumentFragmentScannerImpl.this.setScannerState(21);
/* 2645 */       } else if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(38, XMLScanner.NameType.REFERENCE)) {
/* 2646 */         XMLDocumentFragmentScannerImpl.this.setScannerState(28);
/*      */       } else {
/*      */         
/* 2649 */         XMLDocumentFragmentScannerImpl.this.setScannerState(37);
/*      */       } 
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
/*      */     public void decideSubState() throws IOException {
/* 2668 */       while (XMLDocumentFragmentScannerImpl.this.fScannerState == 22 || XMLDocumentFragmentScannerImpl.this.fScannerState == 21) {
/*      */         
/* 2670 */         switch (XMLDocumentFragmentScannerImpl.this.fScannerState) {
/*      */           
/*      */           case 22:
/* 2673 */             startOfContent();
/*      */ 
/*      */ 
/*      */           
/*      */           case 21:
/* 2678 */             startOfMarkup();
/*      */         } 
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int next() throws IOException, XNIException {
/*      */       try {
/*      */         while (true) {
/*      */           int ch;
/*      */           int c;
/* 2714 */           switch (XMLDocumentFragmentScannerImpl.this.fScannerState) {
/*      */             case 22:
/* 2716 */               ch = XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar();
/* 2717 */               if (ch == 60)
/* 2718 */               { XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null);
/* 2719 */                 XMLDocumentFragmentScannerImpl.this.setScannerState(21); }
/* 2720 */               else { if (ch == 38) {
/* 2721 */                   XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(XMLScanner.NameType.REFERENCE);
/* 2722 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(28);
/*      */                   
/*      */                   break;
/*      */                 } 
/* 2726 */                 XMLDocumentFragmentScannerImpl.this.setScannerState(37);
/*      */                 break; }
/*      */             
/*      */ 
/*      */             
/*      */             case 21:
/* 2732 */               startOfMarkup();
/*      */               break;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2740 */           if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 2741 */             XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/*      */             
/* 2743 */             if (XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData) {
/*      */ 
/*      */ 
/*      */               
/* 2747 */               if (XMLDocumentFragmentScannerImpl.this.fScannerState != 35 && XMLDocumentFragmentScannerImpl.this.fScannerState != 28 && XMLDocumentFragmentScannerImpl.this.fScannerState != 37)
/*      */               {
/* 2749 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = false;
/* 2750 */                 return 4;
/*      */               
/*      */               }
/*      */             
/*      */             }
/* 2755 */             else if (XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData || XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2760 */               if (XMLDocumentFragmentScannerImpl.this.fScannerState != 35 && XMLDocumentFragmentScannerImpl.this.fScannerState != 28 && XMLDocumentFragmentScannerImpl.this.fScannerState != 37) {
/*      */ 
/*      */                 
/* 2763 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData = false;
/* 2764 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = false;
/* 2765 */                 return 4;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2775 */           switch (XMLDocumentFragmentScannerImpl.this.fScannerState) {
/*      */             
/*      */             case 7:
/* 2778 */               return 7;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 38:
/* 2784 */               XMLDocumentFragmentScannerImpl.this.fEmptyElement = XMLDocumentFragmentScannerImpl.this.scanStartElement();
/*      */               
/* 2786 */               if (XMLDocumentFragmentScannerImpl.this.fEmptyElement) {
/* 2787 */                 XMLDocumentFragmentScannerImpl.this.setScannerState(39);
/*      */               } else {
/*      */                 
/* 2790 */                 XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/*      */               } 
/* 2792 */               return 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 37:
/* 2801 */               XMLDocumentFragmentScannerImpl.this.fUsebuffer = (XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData);
/*      */ 
/*      */               
/* 2804 */               if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce && (XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData)) {
/* 2805 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = false;
/* 2806 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData = false;
/* 2807 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = true;
/* 2808 */                 XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/*      */               } else {
/*      */                 
/* 2811 */                 XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 2816 */               XMLDocumentFragmentScannerImpl.this.fTempString.length = 0;
/* 2817 */               c = XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanContent(XMLDocumentFragmentScannerImpl.this.fTempString);
/*      */ 
/*      */ 
/*      */               
/* 2821 */               if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(60, null)) {
/*      */                 
/* 2823 */                 if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(47, XMLScanner.NameType.ELEMENTEND)) {
/*      */                   
/* 2825 */                   XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
/* 2826 */                   XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = false;
/* 2827 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(39);
/*      */                 }
/* 2829 */                 else if (XMLChar.isNameStart(XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar())) {
/* 2830 */                   XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
/* 2831 */                   XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = false;
/* 2832 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(38);
/*      */                 } else {
/* 2834 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(21);
/*      */                   
/* 2836 */                   if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 2837 */                     XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/* 2838 */                     XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = true;
/* 2839 */                     XMLDocumentFragmentScannerImpl.this.fContentBuffer.append(XMLDocumentFragmentScannerImpl.this.fTempString);
/* 2840 */                     XMLDocumentFragmentScannerImpl.this.fTempString.length = 0;
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                 } 
/* 2845 */                 if (XMLDocumentFragmentScannerImpl.this.fUsebuffer) {
/* 2846 */                   XMLDocumentFragmentScannerImpl.this.fContentBuffer.append(XMLDocumentFragmentScannerImpl.this.fTempString);
/* 2847 */                   XMLDocumentFragmentScannerImpl.this.fTempString.length = 0;
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 2852 */                 if (XMLDocumentFragmentScannerImpl.this.dtdGrammarUtil != null && XMLDocumentFragmentScannerImpl.this.dtdGrammarUtil.isIgnorableWhiteSpace(XMLDocumentFragmentScannerImpl.this.fContentBuffer))
/*      */                 {
/* 2854 */                   return 6;
/*      */                 }
/* 2856 */                 return 4;
/*      */               } 
/*      */               
/* 2859 */               XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2864 */               XMLDocumentFragmentScannerImpl.this.fContentBuffer.append(XMLDocumentFragmentScannerImpl.this.fTempString);
/* 2865 */               XMLDocumentFragmentScannerImpl.this.fTempString.length = 0;
/*      */               
/* 2867 */               if (c == 13) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2873 */                 XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null);
/* 2874 */                 XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/* 2875 */                 XMLDocumentFragmentScannerImpl.this.fContentBuffer.append((char)c);
/* 2876 */                 c = -1;
/* 2877 */               } else if (c == 93) {
/*      */ 
/*      */                 
/* 2880 */                 XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/* 2881 */                 XMLDocumentFragmentScannerImpl.this.fContentBuffer.append((char)XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null));
/*      */ 
/*      */ 
/*      */                 
/* 2885 */                 XMLDocumentFragmentScannerImpl.this.fInScanContent = true;
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2890 */                 if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(93, null)) {
/* 2891 */                   XMLDocumentFragmentScannerImpl.this.fContentBuffer.append(']');
/* 2892 */                   while (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(93, null)) {
/* 2893 */                     XMLDocumentFragmentScannerImpl.this.fContentBuffer.append(']');
/*      */                   }
/* 2895 */                   if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(62, null)) {
/* 2896 */                     XMLDocumentFragmentScannerImpl.this.reportFatalError("CDEndInContent", null);
/*      */                   }
/*      */                 } 
/* 2899 */                 c = -1;
/* 2900 */                 XMLDocumentFragmentScannerImpl.this.fInScanContent = false;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               while (true) {
/* 2907 */                 if (c == 60) {
/* 2908 */                   XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null);
/* 2909 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(21);
/*      */                   break;
/*      */                 } 
/* 2912 */                 if (c == 38) {
/* 2913 */                   XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(XMLScanner.NameType.REFERENCE);
/* 2914 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(28);
/*      */                   break;
/*      */                 } 
/* 2917 */                 if (c != -1 && XMLDocumentFragmentScannerImpl.this.isInvalidLiteral(c)) {
/* 2918 */                   if (XMLChar.isHighSurrogate(c)) {
/*      */                     
/* 2920 */                     XMLDocumentFragmentScannerImpl.this.scanSurrogates(XMLDocumentFragmentScannerImpl.this.fContentBuffer);
/* 2921 */                     XMLDocumentFragmentScannerImpl.this.setScannerState(22); break;
/*      */                   } 
/* 2923 */                   XMLDocumentFragmentScannerImpl.this.reportFatalError("InvalidCharInContent", new Object[] {
/*      */                         
/* 2925 */                         Integer.toString(c, 16) });
/* 2926 */                   XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null);
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */                 
/* 2931 */                 c = XMLDocumentFragmentScannerImpl.this.scanContent(XMLDocumentFragmentScannerImpl.this.fContentBuffer);
/*      */ 
/*      */                 
/* 2934 */                 if (!XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 2935 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 2946 */               if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 2947 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = true;
/*      */                 continue;
/*      */               } 
/* 2950 */               if (XMLDocumentFragmentScannerImpl.this.dtdGrammarUtil != null && XMLDocumentFragmentScannerImpl.this.dtdGrammarUtil.isIgnorableWhiteSpace(XMLDocumentFragmentScannerImpl.this.fContentBuffer))
/*      */               {
/* 2952 */                 return 6;
/*      */               }
/* 2954 */               return 4;
/*      */ 
/*      */ 
/*      */             
/*      */             case 39:
/* 2959 */               if (XMLDocumentFragmentScannerImpl.this.fEmptyElement) {
/*      */                 
/* 2961 */                 XMLDocumentFragmentScannerImpl.this.fEmptyElement = false;
/* 2962 */                 XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/*      */ 
/*      */                 
/* 2965 */                 return (XMLDocumentFragmentScannerImpl.this.fMarkupDepth == 0 && elementDepthIsZeroHook()) ? 2 : 2;
/*      */               } 
/* 2967 */               if (XMLDocumentFragmentScannerImpl.this.scanEndElement() == 0)
/*      */               {
/* 2969 */                 if (elementDepthIsZeroHook())
/*      */                 {
/*      */ 
/*      */                   
/* 2973 */                   return 2;
/*      */                 }
/*      */               }
/*      */               
/* 2977 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 2978 */               return 2;
/*      */ 
/*      */             
/*      */             case 27:
/* 2982 */               XMLDocumentFragmentScannerImpl.this.scanComment();
/* 2983 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 2984 */               return 5;
/*      */ 
/*      */ 
/*      */             
/*      */             case 23:
/* 2989 */               XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/*      */ 
/*      */ 
/*      */               
/* 2993 */               XMLDocumentFragmentScannerImpl.this.scanPI(XMLDocumentFragmentScannerImpl.this.fContentBuffer);
/* 2994 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 2995 */               return 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 35:
/* 3004 */               if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce && (XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData)) {
/* 3005 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData = true;
/* 3006 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = false;
/* 3007 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = false;
/*      */               } else {
/*      */                 
/* 3010 */                 XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/*      */               } 
/* 3012 */               XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/*      */               
/* 3014 */               XMLDocumentFragmentScannerImpl.this.scanCDATASection(XMLDocumentFragmentScannerImpl.this.fContentBuffer, true);
/* 3015 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3023 */               if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 3024 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData = true;
/*      */                 continue;
/*      */               } 
/* 3027 */               if (XMLDocumentFragmentScannerImpl.this.fReportCdataEvent) {
/* 3028 */                 return 12;
/*      */               }
/* 3030 */               return 4;
/*      */ 
/*      */ 
/*      */             
/*      */             case 28:
/* 3035 */               XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
/* 3036 */               XMLDocumentFragmentScannerImpl.this.foundBuiltInRefs = false;
/*      */ 
/*      */ 
/*      */               
/* 3040 */               if (XMLDocumentFragmentScannerImpl.this.fIsCoalesce && (XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData || XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData)) {
/*      */ 
/*      */                 
/* 3043 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = true;
/* 3044 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCData = false;
/* 3045 */                 XMLDocumentFragmentScannerImpl.this.fLastSectionWasCharacterData = false;
/*      */               } else {
/*      */                 
/* 3048 */                 XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/*      */               } 
/* 3050 */               XMLDocumentFragmentScannerImpl.this.fUsebuffer = true;
/*      */               
/* 3052 */               if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipChar(35, XMLScanner.NameType.REFERENCE)) {
/* 3053 */                 XMLDocumentFragmentScannerImpl.this.scanCharReferenceValue(XMLDocumentFragmentScannerImpl.this.fContentBuffer, null);
/* 3054 */                 XMLDocumentFragmentScannerImpl.this.fMarkupDepth--;
/* 3055 */                 if (!XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 3056 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3057 */                   return 4;
/*      */                 } 
/*      */               } else {
/*      */                 
/* 3061 */                 XMLDocumentFragmentScannerImpl.this.scanEntityReference(XMLDocumentFragmentScannerImpl.this.fContentBuffer);
/*      */ 
/*      */                 
/* 3064 */                 if (XMLDocumentFragmentScannerImpl.this.fScannerState == 41 && !XMLDocumentFragmentScannerImpl.this.fIsCoalesce) {
/* 3065 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3066 */                   return 4;
/*      */                 } 
/*      */ 
/*      */                 
/* 3070 */                 if (XMLDocumentFragmentScannerImpl.this.fScannerState == 36) {
/* 3071 */                   XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = true;
/*      */                   
/*      */                   continue;
/*      */                 } 
/* 3075 */                 if (XMLDocumentFragmentScannerImpl.this.fScannerState == 28) {
/* 3076 */                   XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3077 */                   if (XMLDocumentFragmentScannerImpl.this.fReplaceEntityReferences && XMLDocumentFragmentScannerImpl.this.fEntityStore.isDeclaredEntity(XMLDocumentFragmentScannerImpl.this.fCurrentEntityName)) {
/*      */                     continue;
/*      */                   }
/*      */                   
/* 3081 */                   return 9;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 3086 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3087 */               XMLDocumentFragmentScannerImpl.this.fLastSectionWasEntityReference = true;
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case 36:
/* 3093 */               if (XMLDocumentFragmentScannerImpl.this.fEntityScanner.skipString("<?xml")) {
/* 3094 */                 XMLDocumentFragmentScannerImpl.this.fMarkupDepth++;
/*      */ 
/*      */                 
/* 3097 */                 if (XMLDocumentFragmentScannerImpl.this.isValidNameChar(XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar())) {
/* 3098 */                   XMLDocumentFragmentScannerImpl.this.fStringBuffer.clear();
/* 3099 */                   XMLDocumentFragmentScannerImpl.this.fStringBuffer.append("xml");
/*      */                   
/* 3101 */                   if (XMLDocumentFragmentScannerImpl.this.fNamespaces) {
/* 3102 */                     while (XMLDocumentFragmentScannerImpl.this.isValidNCName(XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar())) {
/* 3103 */                       XMLDocumentFragmentScannerImpl.this.fStringBuffer.append((char)XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null));
/*      */                     }
/*      */                   } else {
/* 3106 */                     while (XMLDocumentFragmentScannerImpl.this.isValidNameChar(XMLDocumentFragmentScannerImpl.this.fEntityScanner.peekChar())) {
/* 3107 */                       XMLDocumentFragmentScannerImpl.this.fStringBuffer.append((char)XMLDocumentFragmentScannerImpl.this.fEntityScanner.scanChar(null));
/*      */                     }
/*      */                   } 
/* 3110 */                   String target = XMLDocumentFragmentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentFragmentScannerImpl.this.fStringBuffer.ch, XMLDocumentFragmentScannerImpl.this.fStringBuffer.offset, XMLDocumentFragmentScannerImpl.this.fStringBuffer.length);
/* 3111 */                   XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/* 3112 */                   XMLDocumentFragmentScannerImpl.this.scanPIData(target, XMLDocumentFragmentScannerImpl.this.fContentBuffer);
/*      */                 
/*      */                 }
/*      */                 else {
/*      */ 
/*      */                   
/* 3118 */                   XMLDocumentFragmentScannerImpl.this.scanXMLDeclOrTextDecl(true);
/*      */                 } 
/*      */               } 
/*      */               
/* 3122 */               XMLDocumentFragmentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/* 3123 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/*      */               continue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 26:
/* 3132 */               if (scanRootElementHook()) {
/* 3133 */                 XMLDocumentFragmentScannerImpl.this.fEmptyElement = true;
/*      */                 
/* 3135 */                 return 1;
/*      */               } 
/* 3137 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3138 */               return 1;
/*      */             
/*      */             case 40:
/* 3141 */               XMLDocumentFragmentScannerImpl.this.fContentBuffer.clear();
/* 3142 */               XMLDocumentFragmentScannerImpl.this.scanCharReferenceValue(XMLDocumentFragmentScannerImpl.this.fContentBuffer, null);
/* 3143 */               XMLDocumentFragmentScannerImpl.this.fMarkupDepth--;
/* 3144 */               XMLDocumentFragmentScannerImpl.this.setScannerState(22);
/* 3145 */               return 4;
/*      */           }  break;
/*      */         } 
/* 3148 */         throw new XNIException("Scanner State " + XMLDocumentFragmentScannerImpl.this.fScannerState + " not Recognized ");
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 3153 */       catch (EOFException e) {
/* 3154 */         endOfFileHook(e);
/* 3155 */         return -1;
/*      */       } 
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
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean scanForDoctypeHook() throws IOException, XNIException {
/* 3179 */       return false;
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
/*      */     protected boolean elementDepthIsZeroHook() throws IOException, XNIException {
/* 3197 */       return false;
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
/*      */     protected boolean scanRootElementHook() throws IOException, XNIException {
/* 3214 */       return false;
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
/*      */     protected void endOfFileHook(EOFException e) throws IOException, XNIException {
/* 3229 */       if (XMLDocumentFragmentScannerImpl.this.fMarkupDepth != 0) {
/* 3230 */         XMLDocumentFragmentScannerImpl.this.reportFatalError("PrematureEOF", null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void pr(String str) {
/* 3238 */     System.out.println(str);
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
/*      */   protected XMLString getString() {
/* 3253 */     if (this.fAttributeCacheUsedCount < this.initialCacheCount || this.fAttributeCacheUsedCount < this.attributeValueCache.size()) {
/* 3254 */       return this.attributeValueCache.get(this.fAttributeCacheUsedCount++);
/*      */     }
/* 3256 */     XMLString str = new XMLString();
/* 3257 */     this.fAttributeCacheUsedCount++;
/* 3258 */     this.attributeValueCache.add(str);
/* 3259 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh() {
/* 3268 */     refresh(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refresh(int refreshPosition) {
/* 3279 */     if (this.fReadingAttributes) {
/* 3280 */       this.fAttributes.refresh();
/*      */     }
/* 3282 */     if (this.fScannerState == 37) {
/*      */ 
/*      */       
/* 3285 */       this.fContentBuffer.append(this.fTempString);
/*      */       
/* 3287 */       this.fTempString.length = 0;
/* 3288 */       this.fUsebuffer = true;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLDocumentFragmentScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */