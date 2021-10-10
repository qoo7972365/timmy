/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDScanner;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.xml.internal.stream.dtd.nonvalidating.DTDGrammar;
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
/*      */ public class XMLDTDScannerImpl
/*      */   extends XMLScanner
/*      */   implements XMLDTDScanner, XMLComponent, XMLEntityHandler
/*      */ {
/*      */   protected static final int SCANNER_STATE_END_OF_INPUT = 0;
/*      */   protected static final int SCANNER_STATE_TEXT_DECL = 1;
/*      */   protected static final int SCANNER_STATE_MARKUP_DECL = 2;
/*   97 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://xml.org/sax/features/validation", "http://apache.org/xml/features/scanner/notify-char-refs" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[] { null, Boolean.FALSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_SCANNER_STATE = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   public XMLDTDHandler fDTDHandler = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLDTDContentModelHandler fDTDContentModelHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int fScannerState;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fStandalone;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fSeenExternalDTD;
/*      */ 
/*      */   
/*      */   protected boolean fSeenExternalPE;
/*      */ 
/*      */   
/*      */   private boolean fStartDTDCalled;
/*      */ 
/*      */   
/*  159 */   private XMLAttributesImpl fAttributes = new XMLAttributesImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   private int[] fContentStack = new int[5];
/*      */ 
/*      */   
/*      */   private int fContentDepth;
/*      */ 
/*      */   
/*  171 */   private int[] fPEStack = new int[5];
/*      */ 
/*      */ 
/*      */   
/*  175 */   private boolean[] fPEReport = new boolean[5];
/*      */ 
/*      */ 
/*      */   
/*      */   private int fPEDepth;
/*      */ 
/*      */ 
/*      */   
/*      */   private int fMarkUpDepth;
/*      */ 
/*      */   
/*      */   private int fExtEntityDepth;
/*      */ 
/*      */   
/*      */   private int fIncludeSectDepth;
/*      */ 
/*      */   
/*  192 */   private String[] fStrings = new String[3];
/*      */ 
/*      */   
/*  195 */   private XMLString fString = new XMLString();
/*      */ 
/*      */   
/*  198 */   private XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*      */ 
/*      */   
/*  201 */   private XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
/*      */ 
/*      */   
/*  204 */   private XMLString fLiteral = new XMLString();
/*      */ 
/*      */   
/*  207 */   private XMLString fLiteral2 = new XMLString();
/*      */ 
/*      */   
/*  210 */   private String[] fEnumeration = new String[5];
/*      */ 
/*      */   
/*      */   private int fEnumerationCount;
/*      */ 
/*      */   
/*  216 */   private XMLStringBuffer fIgnoreConditionalBuffer = new XMLStringBuffer(128);
/*      */ 
/*      */   
/*  219 */   DTDGrammar nvGrammarInfo = null;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean nonValidatingMode = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDScannerImpl() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDScannerImpl(SymbolTable symbolTable, XMLErrorReporter errorReporter, XMLEntityManager entityManager) {
/*  233 */     this.fSymbolTable = symbolTable;
/*  234 */     this.fErrorReporter = errorReporter;
/*  235 */     this.fEntityManager = entityManager;
/*  236 */     entityManager.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
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
/*      */   public void setInputSource(XMLInputSource inputSource) throws IOException {
/*  251 */     if (inputSource == null) {
/*      */       
/*  253 */       if (this.fDTDHandler != null) {
/*  254 */         this.fDTDHandler.startDTD(null, null);
/*  255 */         this.fDTDHandler.endDTD(null);
/*      */       } 
/*  257 */       if (this.nonValidatingMode) {
/*  258 */         this.nvGrammarInfo.startDTD(null, null);
/*  259 */         this.nvGrammarInfo.endDTD(null);
/*      */       } 
/*      */       return;
/*      */     } 
/*  263 */     this.fEntityManager.setEntityHandler(this);
/*  264 */     this.fEntityManager.startDTDEntity(inputSource);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLimitAnalyzer(XMLLimitAnalyzer limitAnalyzer) {
/*  269 */     this.fLimitAnalyzer = limitAnalyzer;
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
/*      */   public boolean scanDTDExternalSubset(boolean complete) throws IOException, XNIException {
/*  288 */     this.fEntityManager.setEntityHandler(this);
/*  289 */     if (this.fScannerState == 1) {
/*  290 */       this.fSeenExternalDTD = true;
/*  291 */       boolean textDecl = scanTextDecl();
/*  292 */       if (this.fScannerState == 0) {
/*  293 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  298 */       setScannerState(2);
/*  299 */       if (textDecl && !complete) {
/*  300 */         return true;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     do {
/*  306 */       if (!scanDecls(complete)) {
/*  307 */         return false;
/*      */       }
/*  309 */     } while (complete);
/*      */ 
/*      */     
/*  312 */     return true;
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
/*      */   public boolean scanDTDInternalSubset(boolean complete, boolean standalone, boolean hasExternalSubset) throws IOException, XNIException {
/*  343 */     this.fEntityScanner = this.fEntityManager.getEntityScanner();
/*  344 */     this.fEntityManager.setEntityHandler(this);
/*  345 */     this.fStandalone = standalone;
/*      */     
/*  347 */     if (this.fScannerState == 1) {
/*      */       
/*  349 */       if (this.fDTDHandler != null) {
/*  350 */         this.fDTDHandler.startDTD(this.fEntityScanner, null);
/*  351 */         this.fStartDTDCalled = true;
/*      */       } 
/*      */       
/*  354 */       if (this.nonValidatingMode) {
/*  355 */         this.fStartDTDCalled = true;
/*  356 */         this.nvGrammarInfo.startDTD(this.fEntityScanner, null);
/*      */       } 
/*      */       
/*  359 */       setScannerState(2);
/*      */     } 
/*      */     
/*      */     do {
/*  363 */       if (!scanDecls(complete)) {
/*      */         
/*  365 */         if (this.fDTDHandler != null && !hasExternalSubset) {
/*  366 */           this.fDTDHandler.endDTD(null);
/*      */         }
/*  368 */         if (this.nonValidatingMode && !hasExternalSubset) {
/*  369 */           this.nvGrammarInfo.endDTD(null);
/*      */         }
/*      */         
/*  372 */         setScannerState(1);
/*      */         
/*  374 */         this.fLimitAnalyzer.reset(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT);
/*  375 */         this.fLimitAnalyzer.reset(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT);
/*  376 */         return false;
/*      */       } 
/*  378 */     } while (complete);
/*      */ 
/*      */     
/*  381 */     return true;
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
/*      */   public boolean skipDTD(boolean supportDTD) throws IOException {
/*  394 */     if (supportDTD) {
/*  395 */       return false;
/*      */     }
/*  397 */     this.fStringBuffer.clear();
/*  398 */     while (this.fEntityScanner.scanData("]", this.fStringBuffer)) {
/*  399 */       int c = this.fEntityScanner.peekChar();
/*  400 */       if (c != -1) {
/*  401 */         if (XMLChar.isHighSurrogate(c)) {
/*  402 */           scanSurrogates(this.fStringBuffer);
/*      */         }
/*  404 */         if (isInvalidLiteral(c)) {
/*  405 */           reportFatalError("InvalidCharInDTD", new Object[] {
/*  406 */                 Integer.toHexString(c) });
/*  407 */           this.fEntityScanner.scanChar(null);
/*      */         } 
/*      */       } 
/*      */     } 
/*  411 */     this.fEntityScanner.fCurrentEntity.position--;
/*  412 */     return true;
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
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  427 */     super.reset(componentManager);
/*  428 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  434 */     super.reset();
/*  435 */     init();
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager props) {
/*  440 */     setPropertyManager(props);
/*  441 */     super.reset(props);
/*  442 */     init();
/*  443 */     this.nonValidatingMode = true;
/*      */     
/*  445 */     this.nvGrammarInfo = new DTDGrammar(this.fSymbolTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedFeatures() {
/*  453 */     return (String[])RECOGNIZED_FEATURES.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedProperties() {
/*  462 */     return (String[])RECOGNIZED_PROPERTIES.clone();
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
/*      */   public Boolean getFeatureDefault(String featureId) {
/*  475 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/*  476 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/*  477 */         return FEATURE_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  480 */     return null;
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
/*  493 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/*  494 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/*  495 */         return PROPERTY_DEFAULTS[i];
/*      */       }
/*      */     } 
/*  498 */     return null;
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
/*      */   public void setDTDHandler(XMLDTDHandler dtdHandler) {
/*  511 */     this.fDTDHandler = dtdHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDHandler getDTDHandler() {
/*  520 */     return this.fDTDHandler;
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
/*      */   public void setDTDContentModelHandler(XMLDTDContentModelHandler dtdContentModelHandler) {
/*  534 */     this.fDTDContentModelHandler = dtdContentModelHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDTDContentModelHandler getDTDContentModelHandler() {
/*  543 */     return this.fDTDContentModelHandler;
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
/*      */   public void startEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/*  570 */     super.startEntity(name, identifier, encoding, augs);
/*      */     
/*  572 */     boolean dtdEntity = name.equals("[dtd]");
/*  573 */     if (dtdEntity) {
/*      */       
/*  575 */       if (this.fDTDHandler != null && !this.fStartDTDCalled) {
/*  576 */         this.fDTDHandler.startDTD(this.fEntityScanner, null);
/*      */       }
/*  578 */       if (this.fDTDHandler != null) {
/*  579 */         this.fDTDHandler.startExternalSubset(identifier, null);
/*      */       }
/*  581 */       this.fEntityManager.startExternalSubset();
/*  582 */       this.fEntityStore.startExternalSubset();
/*  583 */       this.fExtEntityDepth++;
/*      */     }
/*  585 */     else if (name.charAt(0) == '%') {
/*  586 */       pushPEStack(this.fMarkUpDepth, this.fReportEntity);
/*  587 */       if (this.fEntityScanner.isExternal()) {
/*  588 */         this.fExtEntityDepth++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  593 */     if (this.fDTDHandler != null && !dtdEntity && this.fReportEntity) {
/*  594 */       this.fDTDHandler.startParameterEntity(name, identifier, encoding, null);
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
/*      */   public void endEntity(String name, Augmentations augs) throws XNIException, IOException {
/*  611 */     super.endEntity(name, augs);
/*      */ 
/*      */ 
/*      */     
/*  615 */     if (this.fScannerState == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  619 */     boolean reportEntity = this.fReportEntity;
/*  620 */     if (name.startsWith("%")) {
/*  621 */       reportEntity = peekReportEntity();
/*      */       
/*  623 */       int startMarkUpDepth = popPEStack();
/*      */ 
/*      */       
/*  626 */       if (startMarkUpDepth == 0 && startMarkUpDepth < this.fMarkUpDepth)
/*      */       {
/*  628 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ILL_FORMED_PARAMETER_ENTITY_WHEN_USED_IN_DECL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)2);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  633 */       if (startMarkUpDepth != this.fMarkUpDepth) {
/*  634 */         reportEntity = false;
/*  635 */         if (this.fValidation)
/*      */         {
/*      */           
/*  638 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ImproperDeclarationNesting", new Object[] { name }, (short)1);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  644 */       if (this.fEntityScanner.isExternal()) {
/*  645 */         this.fExtEntityDepth--;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  650 */     boolean dtdEntity = name.equals("[dtd]");
/*  651 */     if (this.fDTDHandler != null && !dtdEntity && reportEntity) {
/*  652 */       this.fDTDHandler.endParameterEntity(name, null);
/*      */     }
/*      */ 
/*      */     
/*  656 */     if (dtdEntity) {
/*  657 */       if (this.fIncludeSectDepth != 0) {
/*  658 */         reportFatalError("IncludeSectUnterminated", null);
/*      */       }
/*  660 */       this.fScannerState = 0;
/*      */       
/*  662 */       this.fEntityManager.endExternalSubset();
/*  663 */       this.fEntityStore.endExternalSubset();
/*      */       
/*  665 */       if (this.fDTDHandler != null) {
/*  666 */         this.fDTDHandler.endExternalSubset(null);
/*  667 */         this.fDTDHandler.endDTD(null);
/*      */       } 
/*  669 */       this.fExtEntityDepth--;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  679 */     if (augs != null && Boolean.TRUE.equals(augs.getItem("LAST_ENTITY")) && (this.fMarkUpDepth != 0 || this.fExtEntityDepth != 0 || this.fIncludeSectDepth != 0))
/*      */     {
/*  681 */       throw new EOFException();
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
/*      */   protected final void setScannerState(int state) {
/*  695 */     this.fScannerState = state;
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
/*      */   private static String getScannerStateName(int state) {
/*  719 */     return "??? (" + state + ')';
/*      */   }
/*      */ 
/*      */   
/*      */   protected final boolean scanningInternalSubset() {
/*  724 */     return (this.fExtEntityDepth == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startPE(String name, boolean literal) throws IOException, XNIException {
/*  735 */     int depth = this.fPEDepth;
/*  736 */     String pName = "%" + name;
/*  737 */     if (this.fValidation && !this.fEntityStore.isDeclaredEntity(pName)) {
/*  738 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { name }, (short)1);
/*      */     }
/*      */     
/*  741 */     this.fEntityManager.startEntity(false, this.fSymbolTable.addSymbol(pName), literal);
/*      */ 
/*      */ 
/*      */     
/*  745 */     if (depth != this.fPEDepth && this.fEntityScanner.isExternal()) {
/*  746 */       scanTextDecl();
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
/*      */   protected final boolean scanTextDecl() throws IOException, XNIException {
/*  766 */     boolean textDecl = false;
/*  767 */     if (this.fEntityScanner.skipString("<?xml")) {
/*  768 */       this.fMarkUpDepth++;
/*      */ 
/*      */       
/*  771 */       if (isValidNameChar(this.fEntityScanner.peekChar())) {
/*  772 */         this.fStringBuffer.clear();
/*  773 */         this.fStringBuffer.append("xml");
/*  774 */         while (isValidNameChar(this.fEntityScanner.peekChar())) {
/*  775 */           this.fStringBuffer.append((char)this.fEntityScanner.scanChar(null));
/*      */         }
/*      */         
/*  778 */         String target = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
/*      */ 
/*      */         
/*  781 */         scanPIData(target, this.fString);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  787 */         String version = null;
/*  788 */         String encoding = null;
/*      */         
/*  790 */         scanXMLDeclOrTextDecl(true, this.fStrings);
/*  791 */         textDecl = true;
/*  792 */         this.fMarkUpDepth--;
/*      */         
/*  794 */         version = this.fStrings[0];
/*  795 */         encoding = this.fStrings[1];
/*      */         
/*  797 */         this.fEntityScanner.setEncoding(encoding);
/*      */ 
/*      */         
/*  800 */         if (this.fDTDHandler != null) {
/*  801 */           this.fDTDHandler.textDecl(version, encoding, null);
/*      */         }
/*      */       } 
/*      */     } 
/*  805 */     this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/*      */     
/*  807 */     return textDecl;
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
/*      */   protected final void scanPIData(String target, XMLString data) throws IOException, XNIException {
/*  823 */     this.fMarkUpDepth--;
/*      */ 
/*      */     
/*  826 */     if (this.fDTDHandler != null) {
/*  827 */       this.fDTDHandler.processingInstruction(target, data, null);
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
/*      */   protected final void scanComment() throws IOException, XNIException {
/*  843 */     this.fReportEntity = false;
/*  844 */     scanComment(this.fStringBuffer);
/*  845 */     this.fMarkUpDepth--;
/*      */ 
/*      */     
/*  848 */     if (this.fDTDHandler != null) {
/*  849 */       this.fDTDHandler.comment(this.fStringBuffer, null);
/*      */     }
/*  851 */     this.fReportEntity = true;
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
/*      */   protected final void scanElementDecl() throws IOException, XNIException {
/*  868 */     this.fReportEntity = false;
/*  869 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/*  870 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ELEMENTDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  875 */     String name = this.fEntityScanner.scanName(XMLScanner.NameType.ELEMENTSTART);
/*  876 */     if (name == null) {
/*  877 */       reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ELEMENTDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  882 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/*  883 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_CONTENTSPEC_IN_ELEMENTDECL", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  888 */     if (this.fDTDContentModelHandler != null) {
/*  889 */       this.fDTDContentModelHandler.startContentModel(name, null);
/*      */     }
/*  891 */     String contentModel = null;
/*  892 */     this.fReportEntity = true;
/*  893 */     if (this.fEntityScanner.skipString("EMPTY")) {
/*  894 */       contentModel = "EMPTY";
/*      */       
/*  896 */       if (this.fDTDContentModelHandler != null) {
/*  897 */         this.fDTDContentModelHandler.empty(null);
/*      */       }
/*      */     }
/*  900 */     else if (this.fEntityScanner.skipString("ANY")) {
/*  901 */       contentModel = "ANY";
/*      */       
/*  903 */       if (this.fDTDContentModelHandler != null) {
/*  904 */         this.fDTDContentModelHandler.any(null);
/*      */       }
/*      */     } else {
/*      */       
/*  908 */       if (!this.fEntityScanner.skipChar(40, null)) {
/*  909 */         reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[] { name });
/*      */       }
/*      */       
/*  912 */       if (this.fDTDContentModelHandler != null) {
/*  913 */         this.fDTDContentModelHandler.startGroup(null);
/*      */       }
/*  915 */       this.fStringBuffer.clear();
/*  916 */       this.fStringBuffer.append('(');
/*  917 */       this.fMarkUpDepth++;
/*  918 */       skipSeparator(false, !scanningInternalSubset());
/*      */ 
/*      */       
/*  921 */       if (this.fEntityScanner.skipString("#PCDATA")) {
/*  922 */         scanMixed(name);
/*      */       } else {
/*      */         
/*  925 */         scanChildren(name);
/*      */       } 
/*  927 */       contentModel = this.fStringBuffer.toString();
/*      */     } 
/*      */ 
/*      */     
/*  931 */     if (this.fDTDContentModelHandler != null) {
/*  932 */       this.fDTDContentModelHandler.endContentModel(null);
/*      */     }
/*      */     
/*  935 */     this.fReportEntity = false;
/*  936 */     skipSeparator(false, !scanningInternalSubset());
/*      */     
/*  938 */     if (!this.fEntityScanner.skipChar(62, null)) {
/*  939 */       reportFatalError("ElementDeclUnterminated", new Object[] { name });
/*      */     }
/*  941 */     this.fReportEntity = true;
/*  942 */     this.fMarkUpDepth--;
/*      */ 
/*      */     
/*  945 */     if (this.fDTDHandler != null) {
/*  946 */       this.fDTDHandler.elementDecl(name, contentModel, null);
/*      */     }
/*  948 */     if (this.nonValidatingMode) this.nvGrammarInfo.elementDecl(name, contentModel, null);
/*      */   
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
/*      */   private final void scanMixed(String elName) throws IOException, XNIException {
/*  967 */     String childName = null;
/*      */     
/*  969 */     this.fStringBuffer.append("#PCDATA");
/*      */     
/*  971 */     if (this.fDTDContentModelHandler != null) {
/*  972 */       this.fDTDContentModelHandler.pcdata(null);
/*      */     }
/*  974 */     skipSeparator(false, !scanningInternalSubset());
/*  975 */     while (this.fEntityScanner.skipChar(124, null)) {
/*  976 */       this.fStringBuffer.append('|');
/*      */       
/*  978 */       if (this.fDTDContentModelHandler != null) {
/*  979 */         this.fDTDContentModelHandler.separator((short)0, null);
/*      */       }
/*      */       
/*  982 */       skipSeparator(false, !scanningInternalSubset());
/*      */       
/*  984 */       childName = this.fEntityScanner.scanName(XMLScanner.NameType.ENTITY);
/*  985 */       if (childName == null) {
/*  986 */         reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_MIXED_CONTENT", new Object[] { elName });
/*      */       }
/*      */       
/*  989 */       this.fStringBuffer.append(childName);
/*      */       
/*  991 */       if (this.fDTDContentModelHandler != null) {
/*  992 */         this.fDTDContentModelHandler.element(childName, null);
/*      */       }
/*  994 */       skipSeparator(false, !scanningInternalSubset());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1000 */     if (this.fEntityScanner.skipString(")*")) {
/* 1001 */       this.fStringBuffer.append(")*");
/*      */       
/* 1003 */       if (this.fDTDContentModelHandler != null) {
/* 1004 */         this.fDTDContentModelHandler.endGroup(null);
/* 1005 */         this.fDTDContentModelHandler.occurrence((short)3, null);
/*      */       }
/*      */     
/*      */     }
/* 1009 */     else if (childName != null) {
/* 1010 */       reportFatalError("MixedContentUnterminated", new Object[] { elName });
/*      */     
/*      */     }
/* 1013 */     else if (this.fEntityScanner.skipChar(41, null)) {
/* 1014 */       this.fStringBuffer.append(')');
/*      */       
/* 1016 */       if (this.fDTDContentModelHandler != null) {
/* 1017 */         this.fDTDContentModelHandler.endGroup(null);
/*      */       }
/*      */     } else {
/*      */       
/* 1021 */       reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[] { elName });
/*      */     } 
/*      */     
/* 1024 */     this.fMarkUpDepth--;
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
/*      */   private final void scanChildren(String elName) throws IOException, XNIException {
/* 1046 */     this.fContentDepth = 0;
/* 1047 */     pushContentStack(0);
/* 1048 */     int currentOp = 0;
/*      */     
/*      */     while (true) {
/* 1051 */       while (this.fEntityScanner.skipChar(40, null)) {
/* 1052 */         this.fMarkUpDepth++;
/* 1053 */         this.fStringBuffer.append('(');
/*      */         
/* 1055 */         if (this.fDTDContentModelHandler != null) {
/* 1056 */           this.fDTDContentModelHandler.startGroup(null);
/*      */         }
/*      */         
/* 1059 */         pushContentStack(currentOp);
/* 1060 */         currentOp = 0;
/* 1061 */         skipSeparator(false, !scanningInternalSubset());
/*      */       } 
/*      */       
/* 1064 */       skipSeparator(false, !scanningInternalSubset());
/* 1065 */       String childName = this.fEntityScanner.scanName(XMLScanner.NameType.ELEMENTSTART);
/* 1066 */       if (childName == null) {
/* 1067 */         reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[] { elName });
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1072 */       if (this.fDTDContentModelHandler != null) {
/* 1073 */         this.fDTDContentModelHandler.element(childName, null);
/*      */       }
/* 1075 */       this.fStringBuffer.append(childName);
/* 1076 */       int c = this.fEntityScanner.peekChar();
/* 1077 */       if (c == 63 || c == 42 || c == 43) {
/*      */         
/* 1079 */         if (this.fDTDContentModelHandler != null) {
/*      */           short oc;
/* 1081 */           if (c == 63) {
/* 1082 */             oc = 2;
/*      */           }
/* 1084 */           else if (c == 42) {
/* 1085 */             oc = 3;
/*      */           } else {
/*      */             
/* 1088 */             oc = 4;
/*      */           } 
/* 1090 */           this.fDTDContentModelHandler.occurrence(oc, null);
/*      */         } 
/* 1092 */         this.fEntityScanner.scanChar(null);
/* 1093 */         this.fStringBuffer.append((char)c);
/*      */       } 
/*      */       while (true) {
/* 1096 */         skipSeparator(false, !scanningInternalSubset());
/* 1097 */         c = this.fEntityScanner.peekChar();
/* 1098 */         if (c == 44 && currentOp != 124) {
/* 1099 */           currentOp = c;
/*      */           
/* 1101 */           if (this.fDTDContentModelHandler != null) {
/* 1102 */             this.fDTDContentModelHandler.separator((short)1, null);
/*      */           }
/*      */           
/* 1105 */           this.fEntityScanner.scanChar(null);
/* 1106 */           this.fStringBuffer.append(',');
/*      */           break;
/*      */         } 
/* 1109 */         if (c == 124 && currentOp != 44) {
/* 1110 */           currentOp = c;
/*      */           
/* 1112 */           if (this.fDTDContentModelHandler != null) {
/* 1113 */             this.fDTDContentModelHandler.separator((short)0, null);
/*      */           }
/*      */           
/* 1116 */           this.fEntityScanner.scanChar(null);
/* 1117 */           this.fStringBuffer.append('|');
/*      */           break;
/*      */         } 
/* 1120 */         if (c != 41) {
/* 1121 */           reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[] { elName });
/*      */         }
/*      */ 
/*      */         
/* 1125 */         if (this.fDTDContentModelHandler != null) {
/* 1126 */           this.fDTDContentModelHandler.endGroup(null);
/*      */         }
/*      */         
/* 1129 */         currentOp = popContentStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1136 */         if (this.fEntityScanner.skipString(")?")) {
/* 1137 */           this.fStringBuffer.append(")?");
/*      */           
/* 1139 */           if (this.fDTDContentModelHandler != null) {
/* 1140 */             short oc = 2;
/* 1141 */             this.fDTDContentModelHandler.occurrence(oc, null);
/*      */           }
/*      */         
/* 1144 */         } else if (this.fEntityScanner.skipString(")+")) {
/* 1145 */           this.fStringBuffer.append(")+");
/*      */           
/* 1147 */           if (this.fDTDContentModelHandler != null) {
/* 1148 */             short oc = 4;
/* 1149 */             this.fDTDContentModelHandler.occurrence(oc, null);
/*      */           }
/*      */         
/* 1152 */         } else if (this.fEntityScanner.skipString(")*")) {
/* 1153 */           this.fStringBuffer.append(")*");
/*      */           
/* 1155 */           if (this.fDTDContentModelHandler != null) {
/* 1156 */             short oc = 3;
/* 1157 */             this.fDTDContentModelHandler.occurrence(oc, null);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1162 */           this.fEntityScanner.scanChar(null);
/* 1163 */           this.fStringBuffer.append(')');
/*      */         } 
/* 1165 */         this.fMarkUpDepth--;
/* 1166 */         if (this.fContentDepth == 0) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1170 */       skipSeparator(false, !scanningInternalSubset());
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
/*      */   protected final void scanAttlistDecl() throws IOException, XNIException {
/* 1187 */     this.fReportEntity = false;
/* 1188 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1189 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ATTLISTDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1194 */     String elName = this.fEntityScanner.scanName(XMLScanner.NameType.ELEMENTSTART);
/* 1195 */     if (elName == null) {
/* 1196 */       reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ATTLISTDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1201 */     if (this.fDTDHandler != null) {
/* 1202 */       this.fDTDHandler.startAttlist(elName, null);
/*      */     }
/*      */ 
/*      */     
/* 1206 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/*      */       
/* 1208 */       if (this.fEntityScanner.skipChar(62, null)) {
/*      */ 
/*      */         
/* 1211 */         if (this.fDTDHandler != null) {
/* 1212 */           this.fDTDHandler.endAttlist(null);
/*      */         }
/* 1214 */         this.fMarkUpDepth--;
/*      */         
/*      */         return;
/*      */       } 
/* 1218 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTRIBUTE_NAME_IN_ATTDEF", new Object[] { elName });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1224 */     while (!this.fEntityScanner.skipChar(62, null)) {
/* 1225 */       String name = this.fEntityScanner.scanName(XMLScanner.NameType.ATTRIBUTENAME);
/* 1226 */       if (name == null) {
/* 1227 */         reportFatalError("AttNameRequiredInAttDef", new Object[] { elName });
/*      */       }
/*      */ 
/*      */       
/* 1231 */       if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1232 */         reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTTYPE_IN_ATTDEF", new Object[] { elName, name });
/*      */       }
/*      */ 
/*      */       
/* 1236 */       String type = scanAttType(elName, name);
/*      */ 
/*      */       
/* 1239 */       if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1240 */         reportFatalError("MSG_SPACE_REQUIRED_BEFORE_DEFAULTDECL_IN_ATTDEF", new Object[] { elName, name });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1245 */       String defaultType = scanAttDefaultDecl(elName, name, type, this.fLiteral, this.fLiteral2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1251 */       String[] enumr = null;
/* 1252 */       if ((this.fDTDHandler != null || this.nonValidatingMode) && 
/* 1253 */         this.fEnumerationCount != 0) {
/* 1254 */         enumr = new String[this.fEnumerationCount];
/* 1255 */         System.arraycopy(this.fEnumeration, 0, enumr, 0, this.fEnumerationCount);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1262 */       if (defaultType != null && (defaultType.equals("#REQUIRED") || defaultType
/* 1263 */         .equals("#IMPLIED"))) {
/* 1264 */         if (this.fDTDHandler != null) {
/* 1265 */           this.fDTDHandler.attributeDecl(elName, name, type, enumr, defaultType, null, null, null);
/*      */         }
/*      */         
/* 1268 */         if (this.nonValidatingMode) {
/* 1269 */           this.nvGrammarInfo.attributeDecl(elName, name, type, enumr, defaultType, null, null, null);
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1275 */         if (this.fDTDHandler != null) {
/* 1276 */           this.fDTDHandler.attributeDecl(elName, name, type, enumr, defaultType, this.fLiteral, this.fLiteral2, null);
/*      */         }
/*      */         
/* 1279 */         if (this.nonValidatingMode) {
/* 1280 */           this.nvGrammarInfo.attributeDecl(elName, name, type, enumr, defaultType, this.fLiteral, this.fLiteral2, null);
/*      */         }
/*      */       } 
/*      */       
/* 1284 */       skipSeparator(false, !scanningInternalSubset());
/*      */     } 
/*      */ 
/*      */     
/* 1288 */     if (this.fDTDHandler != null) {
/* 1289 */       this.fDTDHandler.endAttlist(null);
/*      */     }
/* 1291 */     this.fMarkUpDepth--;
/* 1292 */     this.fReportEntity = true;
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
/*      */   private final String scanAttType(String elName, String atName) throws IOException, XNIException {
/* 1322 */     String type = null;
/* 1323 */     this.fEnumerationCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1329 */     if (this.fEntityScanner.skipString("CDATA"))
/* 1330 */     { type = "CDATA"; }
/*      */     
/* 1332 */     else if (this.fEntityScanner.skipString("IDREFS"))
/* 1333 */     { type = "IDREFS"; }
/*      */     
/* 1335 */     else if (this.fEntityScanner.skipString("IDREF"))
/* 1336 */     { type = "IDREF"; }
/*      */     
/* 1338 */     else if (this.fEntityScanner.skipString("ID"))
/* 1339 */     { type = "ID"; }
/*      */     
/* 1341 */     else if (this.fEntityScanner.skipString("ENTITY"))
/* 1342 */     { type = "ENTITY"; }
/*      */     
/* 1344 */     else if (this.fEntityScanner.skipString("ENTITIES"))
/* 1345 */     { type = "ENTITIES"; }
/*      */     
/* 1347 */     else if (this.fEntityScanner.skipString("NMTOKENS"))
/* 1348 */     { type = "NMTOKENS"; }
/*      */     
/* 1350 */     else if (this.fEntityScanner.skipString("NMTOKEN"))
/* 1351 */     { type = "NMTOKEN"; }
/*      */     else
/* 1353 */     { if (this.fEntityScanner.skipString("NOTATION"))
/* 1354 */       { type = "NOTATION";
/*      */         
/* 1356 */         if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1357 */           reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_IN_NOTATIONTYPE", new Object[] { elName, atName });
/*      */         }
/*      */ 
/*      */         
/* 1361 */         int i = this.fEntityScanner.scanChar(null);
/* 1362 */         if (i != 40) {
/* 1363 */           reportFatalError("MSG_OPEN_PAREN_REQUIRED_IN_NOTATIONTYPE", new Object[] { elName, atName });
/*      */         }
/*      */         
/* 1366 */         this.fMarkUpDepth++;
/*      */         while (true)
/* 1368 */         { skipSeparator(false, !scanningInternalSubset());
/* 1369 */           String aName = this.fEntityScanner.scanName(XMLScanner.NameType.ATTRIBUTENAME);
/* 1370 */           if (aName == null) {
/* 1371 */             reportFatalError("MSG_NAME_REQUIRED_IN_NOTATIONTYPE", new Object[] { elName, atName });
/*      */           }
/*      */           
/* 1374 */           ensureEnumerationSize(this.fEnumerationCount + 1);
/* 1375 */           this.fEnumeration[this.fEnumerationCount++] = aName;
/* 1376 */           skipSeparator(false, !scanningInternalSubset());
/* 1377 */           i = this.fEntityScanner.scanChar(null);
/* 1378 */           if (i != 124)
/* 1379 */           { if (i != 41) {
/* 1380 */               reportFatalError("NotationTypeUnterminated", new Object[] { elName, atName });
/*      */             }
/*      */             
/* 1383 */             this.fMarkUpDepth--;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1413 */             return type; }  }  }  type = "ENUMERATION"; int c = this.fEntityScanner.scanChar(null); if (c != 40) reportFatalError("AttTypeRequiredInAttDef", new Object[] { elName, atName });  this.fMarkUpDepth++; while (true) { skipSeparator(false, !scanningInternalSubset()); String token = this.fEntityScanner.scanNmtoken(); if (token == null) reportFatalError("MSG_NMTOKEN_REQUIRED_IN_ENUMERATION", new Object[] { elName, atName });  ensureEnumerationSize(this.fEnumerationCount + 1); this.fEnumeration[this.fEnumerationCount++] = token; skipSeparator(false, !scanningInternalSubset()); c = this.fEntityScanner.scanChar(null); if (c != 124) { if (c != 41) reportFatalError("EnumerationUnterminated", new Object[] { elName, atName });  this.fMarkUpDepth--; return type; }  }  }  return type;
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
/*      */   protected final String scanAttDefaultDecl(String elName, String atName, String type, XMLString defaultVal, XMLString nonNormalizedDefaultVal) throws IOException, XNIException {
/* 1434 */     String defaultType = null;
/* 1435 */     this.fString.clear();
/* 1436 */     defaultVal.clear();
/* 1437 */     if (this.fEntityScanner.skipString("#REQUIRED")) {
/* 1438 */       defaultType = "#REQUIRED";
/*      */     }
/* 1440 */     else if (this.fEntityScanner.skipString("#IMPLIED")) {
/* 1441 */       defaultType = "#IMPLIED";
/*      */     } else {
/*      */       
/* 1444 */       if (this.fEntityScanner.skipString("#FIXED")) {
/* 1445 */         defaultType = "#FIXED";
/*      */         
/* 1447 */         if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1448 */           reportFatalError("MSG_SPACE_REQUIRED_AFTER_FIXED_IN_DEFAULTDECL", new Object[] { elName, atName });
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1453 */       boolean isVC = (!this.fStandalone && (this.fSeenExternalDTD || this.fSeenExternalPE));
/* 1454 */       scanAttributeValue(defaultVal, nonNormalizedDefaultVal, atName, this.fAttributes, 0, isVC, elName, false);
/*      */     } 
/*      */     
/* 1457 */     return defaultType;
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
/*      */   private final void scanEntityDecl() throws IOException, XNIException {
/* 1479 */     boolean isPEDecl = false;
/* 1480 */     boolean sawPERef = false;
/* 1481 */     this.fReportEntity = false;
/* 1482 */     if (this.fEntityScanner.skipSpaces()) {
/* 1483 */       if (!this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE)) {
/* 1484 */         isPEDecl = false;
/*      */       }
/* 1486 */       else if (skipSeparator(true, !scanningInternalSubset())) {
/*      */         
/* 1488 */         isPEDecl = true;
/*      */       }
/* 1490 */       else if (scanningInternalSubset()) {
/* 1491 */         reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL", null);
/*      */         
/* 1493 */         isPEDecl = true;
/*      */       }
/* 1495 */       else if (this.fEntityScanner.peekChar() == 37) {
/*      */         
/* 1497 */         skipSeparator(false, !scanningInternalSubset());
/* 1498 */         isPEDecl = true;
/*      */       } else {
/*      */         
/* 1501 */         sawPERef = true;
/*      */       }
/*      */     
/* 1504 */     } else if (scanningInternalSubset() || !this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE)) {
/*      */       
/* 1506 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL", null);
/*      */       
/* 1508 */       isPEDecl = false;
/*      */     }
/* 1510 */     else if (this.fEntityScanner.skipSpaces()) {
/*      */       
/* 1512 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_PERCENT_IN_PEDECL", null);
/*      */       
/* 1514 */       isPEDecl = false;
/*      */     } else {
/*      */       
/* 1517 */       sawPERef = true;
/*      */     } 
/* 1519 */     if (sawPERef) {
/*      */       while (true) {
/* 1521 */         String peName = this.fEntityScanner.scanName(XMLScanner.NameType.REFERENCE);
/* 1522 */         if (peName == null) {
/* 1523 */           reportFatalError("NameRequiredInPEReference", null);
/*      */         }
/* 1525 */         else if (!this.fEntityScanner.skipChar(59, XMLScanner.NameType.REFERENCE)) {
/* 1526 */           reportFatalError("SemicolonRequiredInPEReference", new Object[] { peName });
/*      */         }
/*      */         else {
/*      */           
/* 1530 */           startPE(peName, false);
/*      */         } 
/* 1532 */         this.fEntityScanner.skipSpaces();
/* 1533 */         if (!this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE))
/*      */           break; 
/* 1535 */         if (!isPEDecl) {
/* 1536 */           if (skipSeparator(true, !scanningInternalSubset())) {
/* 1537 */             isPEDecl = true;
/*      */             break;
/*      */           } 
/* 1540 */           isPEDecl = this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1546 */     String name = this.fEntityScanner.scanName(XMLScanner.NameType.ENTITY);
/* 1547 */     if (name == null) {
/* 1548 */       reportFatalError("MSG_ENTITY_NAME_REQUIRED_IN_ENTITYDECL", null);
/*      */     }
/*      */ 
/*      */     
/* 1552 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1553 */       reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1558 */     scanExternalID(this.fStrings, false);
/* 1559 */     String systemId = this.fStrings[0];
/* 1560 */     String publicId = this.fStrings[1];
/*      */     
/* 1562 */     if (isPEDecl && systemId != null) {
/* 1563 */       this.fSeenExternalPE = true;
/*      */     }
/*      */     
/* 1566 */     String notation = null;
/*      */     
/* 1568 */     boolean sawSpace = skipSeparator(true, !scanningInternalSubset());
/* 1569 */     if (!isPEDecl && this.fEntityScanner.skipString("NDATA")) {
/*      */       
/* 1571 */       if (!sawSpace) {
/* 1572 */         reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NDATA_IN_UNPARSED_ENTITYDECL", new Object[] { name });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1577 */       if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1578 */         reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_UNPARSED_ENTITYDECL", new Object[] { name });
/*      */       }
/*      */       
/* 1581 */       notation = this.fEntityScanner.scanName(XMLScanner.NameType.NOTATION);
/* 1582 */       if (notation == null) {
/* 1583 */         reportFatalError("MSG_NOTATION_NAME_REQUIRED_FOR_UNPARSED_ENTITYDECL", new Object[] { name });
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1589 */     if (systemId == null) {
/* 1590 */       scanEntityValue(name, isPEDecl, this.fLiteral, this.fLiteral2);
/*      */ 
/*      */       
/* 1593 */       this.fStringBuffer.clear();
/* 1594 */       this.fStringBuffer2.clear();
/* 1595 */       this.fStringBuffer.append(this.fLiteral.ch, this.fLiteral.offset, this.fLiteral.length);
/* 1596 */       this.fStringBuffer2.append(this.fLiteral2.ch, this.fLiteral2.offset, this.fLiteral2.length);
/*      */     } 
/*      */ 
/*      */     
/* 1600 */     skipSeparator(false, !scanningInternalSubset());
/*      */ 
/*      */     
/* 1603 */     if (!this.fEntityScanner.skipChar(62, null)) {
/* 1604 */       reportFatalError("EntityDeclUnterminated", new Object[] { name });
/*      */     }
/* 1606 */     this.fMarkUpDepth--;
/*      */ 
/*      */     
/* 1609 */     if (isPEDecl) {
/* 1610 */       name = "%" + name;
/*      */     }
/* 1612 */     if (systemId != null) {
/* 1613 */       String baseSystemId = this.fEntityScanner.getBaseSystemId();
/* 1614 */       if (notation != null) {
/* 1615 */         this.fEntityStore.addUnparsedEntity(name, publicId, systemId, baseSystemId, notation);
/*      */       } else {
/*      */         
/* 1618 */         this.fEntityStore.addExternalEntity(name, publicId, systemId, baseSystemId);
/*      */       } 
/*      */       
/* 1621 */       if (this.fDTDHandler != null) {
/*      */         
/* 1623 */         this.fResourceIdentifier.setValues(publicId, systemId, baseSystemId, XMLEntityManager.expandSystemId(systemId, baseSystemId));
/*      */         
/* 1625 */         if (notation != null) {
/* 1626 */           this.fDTDHandler.unparsedEntityDecl(name, this.fResourceIdentifier, notation, null);
/*      */         }
/*      */         else {
/*      */           
/* 1630 */           this.fDTDHandler.externalEntityDecl(name, this.fResourceIdentifier, null);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1635 */       this.fEntityStore.addInternalEntity(name, this.fStringBuffer.toString());
/* 1636 */       if (this.fDTDHandler != null) {
/* 1637 */         this.fDTDHandler.internalEntityDecl(name, this.fStringBuffer, this.fStringBuffer2, null);
/*      */       }
/*      */     } 
/* 1640 */     this.fReportEntity = true;
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
/*      */   protected final void scanEntityValue(String entityName, boolean isPEDecl, XMLString value, XMLString nonNormalizedValue) throws IOException, XNIException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   4: aconst_null
/*      */     //   5: invokevirtual scanChar : (Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)I
/*      */     //   8: istore #5
/*      */     //   10: iload #5
/*      */     //   12: bipush #39
/*      */     //   14: if_icmpeq -> 32
/*      */     //   17: iload #5
/*      */     //   19: bipush #34
/*      */     //   21: if_icmpeq -> 32
/*      */     //   24: aload_0
/*      */     //   25: ldc_w 'OpenQuoteMissingInDecl'
/*      */     //   28: aconst_null
/*      */     //   29: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   32: aload_0
/*      */     //   33: getfield fEntityDepth : I
/*      */     //   36: istore #6
/*      */     //   38: aload_0
/*      */     //   39: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   42: astore #7
/*      */     //   44: aload_0
/*      */     //   45: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   48: astore #8
/*      */     //   50: iconst_0
/*      */     //   51: istore #9
/*      */     //   53: aload_0
/*      */     //   54: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   57: ifnonnull -> 71
/*      */     //   60: aload_0
/*      */     //   61: aload_0
/*      */     //   62: getfield fEntityManager : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityManager;
/*      */     //   65: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   68: putfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   71: aload_0
/*      */     //   72: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   75: aload_1
/*      */     //   76: invokevirtual startEntity : (Ljava/lang/String;)V
/*      */     //   79: aload_0
/*      */     //   80: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   83: iload #5
/*      */     //   85: aload_0
/*      */     //   86: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   89: iconst_0
/*      */     //   90: invokevirtual scanLiteral : (ILcom/sun/org/apache/xerces/internal/xni/XMLString;Z)I
/*      */     //   93: iload #5
/*      */     //   95: if_icmpeq -> 687
/*      */     //   98: aload_0
/*      */     //   99: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   102: invokevirtual clear : ()V
/*      */     //   105: aload_0
/*      */     //   106: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   109: invokevirtual clear : ()V
/*      */     //   112: iconst_0
/*      */     //   113: istore #9
/*      */     //   115: aload_0
/*      */     //   116: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   119: getfield length : I
/*      */     //   122: istore #10
/*      */     //   124: aload_0
/*      */     //   125: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   128: aload_0
/*      */     //   129: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   132: invokevirtual append : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   135: aload_0
/*      */     //   136: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   139: aload_0
/*      */     //   140: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   143: invokevirtual append : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   146: aload_0
/*      */     //   147: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   150: bipush #38
/*      */     //   152: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   155: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   158: ifeq -> 321
/*      */     //   161: aload_0
/*      */     //   162: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   165: bipush #35
/*      */     //   167: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   170: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   173: ifeq -> 202
/*      */     //   176: aload_0
/*      */     //   177: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   180: ldc_w '&#'
/*      */     //   183: invokevirtual append : (Ljava/lang/String;)V
/*      */     //   186: aload_0
/*      */     //   187: aload_0
/*      */     //   188: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   191: aload_0
/*      */     //   192: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   195: invokevirtual scanCharReferenceValue : (Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;)I
/*      */     //   198: pop
/*      */     //   199: goto -> 599
/*      */     //   202: aload_0
/*      */     //   203: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   206: bipush #38
/*      */     //   208: invokevirtual append : (C)V
/*      */     //   211: aload_0
/*      */     //   212: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   215: bipush #38
/*      */     //   217: invokevirtual append : (C)V
/*      */     //   220: aload_0
/*      */     //   221: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   224: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   227: invokevirtual scanName : (Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Ljava/lang/String;
/*      */     //   230: astore #11
/*      */     //   232: aload #11
/*      */     //   234: ifnonnull -> 248
/*      */     //   237: aload_0
/*      */     //   238: ldc_w 'NameRequiredInReference'
/*      */     //   241: aconst_null
/*      */     //   242: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   245: goto -> 266
/*      */     //   248: aload_0
/*      */     //   249: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   252: aload #11
/*      */     //   254: invokevirtual append : (Ljava/lang/String;)V
/*      */     //   257: aload_0
/*      */     //   258: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   261: aload #11
/*      */     //   263: invokevirtual append : (Ljava/lang/String;)V
/*      */     //   266: aload_0
/*      */     //   267: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   270: bipush #59
/*      */     //   272: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   275: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   278: ifne -> 300
/*      */     //   281: aload_0
/*      */     //   282: ldc_w 'SemicolonRequiredInReference'
/*      */     //   285: iconst_1
/*      */     //   286: anewarray java/lang/Object
/*      */     //   289: dup
/*      */     //   290: iconst_0
/*      */     //   291: aload #11
/*      */     //   293: aastore
/*      */     //   294: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   297: goto -> 318
/*      */     //   300: aload_0
/*      */     //   301: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   304: bipush #59
/*      */     //   306: invokevirtual append : (C)V
/*      */     //   309: aload_0
/*      */     //   310: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   313: bipush #59
/*      */     //   315: invokevirtual append : (C)V
/*      */     //   318: goto -> 599
/*      */     //   321: aload_0
/*      */     //   322: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   325: bipush #37
/*      */     //   327: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   330: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   333: ifeq -> 482
/*      */     //   336: aload_0
/*      */     //   337: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   340: bipush #37
/*      */     //   342: invokevirtual append : (C)V
/*      */     //   345: aload_0
/*      */     //   346: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   349: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   352: invokevirtual scanName : (Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Ljava/lang/String;
/*      */     //   355: astore #11
/*      */     //   357: aload #11
/*      */     //   359: ifnonnull -> 372
/*      */     //   362: aload_0
/*      */     //   363: ldc 'NameRequiredInPEReference'
/*      */     //   365: aconst_null
/*      */     //   366: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   369: goto -> 446
/*      */     //   372: aload_0
/*      */     //   373: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   376: bipush #59
/*      */     //   378: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   381: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   384: ifne -> 405
/*      */     //   387: aload_0
/*      */     //   388: ldc 'SemicolonRequiredInPEReference'
/*      */     //   390: iconst_1
/*      */     //   391: anewarray java/lang/Object
/*      */     //   394: dup
/*      */     //   395: iconst_0
/*      */     //   396: aload #11
/*      */     //   398: aastore
/*      */     //   399: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   402: goto -> 446
/*      */     //   405: aload_0
/*      */     //   406: invokevirtual scanningInternalSubset : ()Z
/*      */     //   409: ifeq -> 428
/*      */     //   412: aload_0
/*      */     //   413: ldc_w 'PEReferenceWithinMarkup'
/*      */     //   416: iconst_1
/*      */     //   417: anewarray java/lang/Object
/*      */     //   420: dup
/*      */     //   421: iconst_0
/*      */     //   422: aload #11
/*      */     //   424: aastore
/*      */     //   425: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   428: aload_0
/*      */     //   429: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   432: aload #11
/*      */     //   434: invokevirtual append : (Ljava/lang/String;)V
/*      */     //   437: aload_0
/*      */     //   438: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   441: bipush #59
/*      */     //   443: invokevirtual append : (C)V
/*      */     //   446: aload_0
/*      */     //   447: aload #11
/*      */     //   449: iconst_1
/*      */     //   450: invokevirtual startPE : (Ljava/lang/String;Z)V
/*      */     //   453: aload_0
/*      */     //   454: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   457: invokevirtual skipSpaces : ()Z
/*      */     //   460: pop
/*      */     //   461: aload_0
/*      */     //   462: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   465: bipush #37
/*      */     //   467: getstatic com/sun/org/apache/xerces/internal/impl/XMLScanner$NameType.REFERENCE : Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;
/*      */     //   470: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   473: ifne -> 479
/*      */     //   476: goto -> 599
/*      */     //   479: goto -> 336
/*      */     //   482: aload_0
/*      */     //   483: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   486: invokevirtual peekChar : ()I
/*      */     //   489: istore #11
/*      */     //   491: iload #11
/*      */     //   493: invokestatic isHighSurrogate : (I)Z
/*      */     //   496: ifeq -> 514
/*      */     //   499: iinc #9, 1
/*      */     //   502: aload_0
/*      */     //   503: aload_0
/*      */     //   504: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   507: invokevirtual scanSurrogates : (Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;)Z
/*      */     //   510: pop
/*      */     //   511: goto -> 599
/*      */     //   514: aload_0
/*      */     //   515: iload #11
/*      */     //   517: invokevirtual isInvalidLiteral : (I)Z
/*      */     //   520: ifeq -> 554
/*      */     //   523: aload_0
/*      */     //   524: ldc_w 'InvalidCharInLiteral'
/*      */     //   527: iconst_1
/*      */     //   528: anewarray java/lang/Object
/*      */     //   531: dup
/*      */     //   532: iconst_0
/*      */     //   533: iload #11
/*      */     //   535: invokestatic toHexString : (I)Ljava/lang/String;
/*      */     //   538: aastore
/*      */     //   539: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   542: aload_0
/*      */     //   543: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   546: aconst_null
/*      */     //   547: invokevirtual scanChar : (Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)I
/*      */     //   550: pop
/*      */     //   551: goto -> 599
/*      */     //   554: iload #11
/*      */     //   556: iload #5
/*      */     //   558: if_icmpne -> 570
/*      */     //   561: iload #6
/*      */     //   563: aload_0
/*      */     //   564: getfield fEntityDepth : I
/*      */     //   567: if_icmpeq -> 599
/*      */     //   570: aload_0
/*      */     //   571: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   574: iload #11
/*      */     //   576: i2c
/*      */     //   577: invokevirtual append : (C)V
/*      */     //   580: aload_0
/*      */     //   581: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   584: iload #11
/*      */     //   586: i2c
/*      */     //   587: invokevirtual append : (C)V
/*      */     //   590: aload_0
/*      */     //   591: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   594: aconst_null
/*      */     //   595: invokevirtual scanChar : (Lcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)I
/*      */     //   598: pop
/*      */     //   599: aload_0
/*      */     //   600: iload_2
/*      */     //   601: aload_1
/*      */     //   602: aload_0
/*      */     //   603: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   606: getfield length : I
/*      */     //   609: iload #10
/*      */     //   611: isub
/*      */     //   612: iload #9
/*      */     //   614: iadd
/*      */     //   615: invokevirtual checkEntityLimit : (ZLjava/lang/String;I)V
/*      */     //   618: aload_0
/*      */     //   619: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   622: iload #5
/*      */     //   624: aload_0
/*      */     //   625: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   628: iconst_0
/*      */     //   629: invokevirtual scanLiteral : (ILcom/sun/org/apache/xerces/internal/xni/XMLString;Z)I
/*      */     //   632: iload #5
/*      */     //   634: if_icmpne -> 112
/*      */     //   637: aload_0
/*      */     //   638: iload_2
/*      */     //   639: aload_1
/*      */     //   640: aload_0
/*      */     //   641: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   644: getfield length : I
/*      */     //   647: invokevirtual checkEntityLimit : (ZLjava/lang/String;I)V
/*      */     //   650: aload_0
/*      */     //   651: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   654: aload_0
/*      */     //   655: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   658: invokevirtual append : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   661: aload_0
/*      */     //   662: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   665: aload_0
/*      */     //   666: getfield fString : Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   669: invokevirtual append : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   672: aload_0
/*      */     //   673: getfield fStringBuffer : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   676: astore #7
/*      */     //   678: aload_0
/*      */     //   679: getfield fStringBuffer2 : Lcom/sun/org/apache/xerces/internal/util/XMLStringBuffer;
/*      */     //   682: astore #8
/*      */     //   684: goto -> 695
/*      */     //   687: aload_0
/*      */     //   688: iload_2
/*      */     //   689: aload_1
/*      */     //   690: aload #7
/*      */     //   692: invokevirtual checkEntityLimit : (ZLjava/lang/String;Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   695: aload_3
/*      */     //   696: aload #7
/*      */     //   698: invokevirtual setValues : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   701: aload #4
/*      */     //   703: aload #8
/*      */     //   705: invokevirtual setValues : (Lcom/sun/org/apache/xerces/internal/xni/XMLString;)V
/*      */     //   708: aload_0
/*      */     //   709: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   712: ifnull -> 744
/*      */     //   715: iload_2
/*      */     //   716: ifeq -> 733
/*      */     //   719: aload_0
/*      */     //   720: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   723: getstatic com/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit.PARAMETER_ENTITY_SIZE_LIMIT : Lcom/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit;
/*      */     //   726: aload_1
/*      */     //   727: invokevirtual endEntity : (Lcom/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit;Ljava/lang/String;)V
/*      */     //   730: goto -> 744
/*      */     //   733: aload_0
/*      */     //   734: getfield fLimitAnalyzer : Lcom/sun/org/apache/xerces/internal/utils/XMLLimitAnalyzer;
/*      */     //   737: getstatic com/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit.GENERAL_ENTITY_SIZE_LIMIT : Lcom/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit;
/*      */     //   740: aload_1
/*      */     //   741: invokevirtual endEntity : (Lcom/sun/org/apache/xerces/internal/utils/XMLSecurityManager$Limit;Ljava/lang/String;)V
/*      */     //   744: aload_0
/*      */     //   745: getfield fEntityScanner : Lcom/sun/org/apache/xerces/internal/impl/XMLEntityScanner;
/*      */     //   748: iload #5
/*      */     //   750: aconst_null
/*      */     //   751: invokevirtual skipChar : (ILcom/sun/org/apache/xerces/internal/impl/XMLScanner$NameType;)Z
/*      */     //   754: ifne -> 765
/*      */     //   757: aload_0
/*      */     //   758: ldc_w 'CloseQuoteMissingInDecl'
/*      */     //   761: aconst_null
/*      */     //   762: invokevirtual reportFatalError : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   765: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1658	-> 0
/*      */     //   #1659	-> 10
/*      */     //   #1660	-> 24
/*      */     //   #1663	-> 32
/*      */     //   #1665	-> 38
/*      */     //   #1666	-> 44
/*      */     //   #1667	-> 50
/*      */     //   #1668	-> 53
/*      */     //   #1669	-> 60
/*      */     //   #1671	-> 71
/*      */     //   #1673	-> 79
/*      */     //   #1674	-> 98
/*      */     //   #1675	-> 105
/*      */     //   #1678	-> 112
/*      */     //   #1679	-> 115
/*      */     //   #1680	-> 124
/*      */     //   #1681	-> 135
/*      */     //   #1682	-> 146
/*      */     //   #1683	-> 161
/*      */     //   #1684	-> 176
/*      */     //   #1685	-> 186
/*      */     //   #1688	-> 202
/*      */     //   #1689	-> 211
/*      */     //   #1690	-> 220
/*      */     //   #1691	-> 232
/*      */     //   #1692	-> 237
/*      */     //   #1696	-> 248
/*      */     //   #1697	-> 257
/*      */     //   #1699	-> 266
/*      */     //   #1700	-> 281
/*      */     //   #1704	-> 300
/*      */     //   #1705	-> 309
/*      */     //   #1707	-> 318
/*      */     //   #1709	-> 321
/*      */     //   #1711	-> 336
/*      */     //   #1712	-> 345
/*      */     //   #1713	-> 357
/*      */     //   #1714	-> 362
/*      */     //   #1717	-> 372
/*      */     //   #1718	-> 387
/*      */     //   #1722	-> 405
/*      */     //   #1723	-> 412
/*      */     //   #1726	-> 428
/*      */     //   #1727	-> 437
/*      */     //   #1729	-> 446
/*      */     //   #1733	-> 453
/*      */     //   #1734	-> 461
/*      */     //   #1735	-> 476
/*      */     //   #1736	-> 479
/*      */     //   #1739	-> 482
/*      */     //   #1740	-> 491
/*      */     //   #1741	-> 499
/*      */     //   #1742	-> 502
/*      */     //   #1744	-> 514
/*      */     //   #1745	-> 523
/*      */     //   #1746	-> 535
/*      */     //   #1745	-> 539
/*      */     //   #1747	-> 542
/*      */     //   #1752	-> 554
/*      */     //   #1753	-> 570
/*      */     //   #1754	-> 580
/*      */     //   #1755	-> 590
/*      */     //   #1758	-> 599
/*      */     //   #1759	-> 618
/*      */     //   #1760	-> 637
/*      */     //   #1761	-> 650
/*      */     //   #1762	-> 661
/*      */     //   #1763	-> 672
/*      */     //   #1764	-> 678
/*      */     //   #1765	-> 684
/*      */     //   #1766	-> 687
/*      */     //   #1768	-> 695
/*      */     //   #1769	-> 701
/*      */     //   #1770	-> 708
/*      */     //   #1771	-> 715
/*      */     //   #1772	-> 719
/*      */     //   #1774	-> 733
/*      */     //   #1778	-> 744
/*      */     //   #1779	-> 757
/*      */     //   #1781	-> 765
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   232	86	11	eName	Ljava/lang/String;
/*      */     //   357	122	11	peName	Ljava/lang/String;
/*      */     //   491	108	11	c	I
/*      */     //   124	560	10	offset	I
/*      */     //   0	766	0	this	Lcom/sun/org/apache/xerces/internal/impl/XMLDTDScannerImpl;
/*      */     //   0	766	1	entityName	Ljava/lang/String;
/*      */     //   0	766	2	isPEDecl	Z
/*      */     //   0	766	3	value	Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   0	766	4	nonNormalizedValue	Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   10	756	5	quote	I
/*      */     //   38	728	6	entityDepth	I
/*      */     //   44	722	7	literal	Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   50	716	8	literal2	Lcom/sun/org/apache/xerces/internal/xni/XMLString;
/*      */     //   53	713	9	countChar	I
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
/*      */   private final void scanNotationDecl() throws IOException, XNIException {
/* 1796 */     this.fReportEntity = false;
/* 1797 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1798 */       reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_NOTATIONDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1803 */     String name = this.fEntityScanner.scanName(XMLScanner.NameType.NOTATION);
/* 1804 */     if (name == null) {
/* 1805 */       reportFatalError("MSG_NOTATION_NAME_REQUIRED_IN_NOTATIONDECL", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1810 */     if (!skipSeparator(true, !scanningInternalSubset())) {
/* 1811 */       reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_NAME_IN_NOTATIONDECL", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1816 */     scanExternalID(this.fStrings, true);
/* 1817 */     String systemId = this.fStrings[0];
/* 1818 */     String publicId = this.fStrings[1];
/* 1819 */     String baseSystemId = this.fEntityScanner.getBaseSystemId();
/*      */     
/* 1821 */     if (systemId == null && publicId == null) {
/* 1822 */       reportFatalError("ExternalIDorPublicIDRequired", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1827 */     skipSeparator(false, !scanningInternalSubset());
/*      */ 
/*      */     
/* 1830 */     if (!this.fEntityScanner.skipChar(62, null)) {
/* 1831 */       reportFatalError("NotationDeclUnterminated", new Object[] { name });
/*      */     }
/* 1833 */     this.fMarkUpDepth--;
/*      */     
/* 1835 */     this.fResourceIdentifier.setValues(publicId, systemId, baseSystemId, XMLEntityManager.expandSystemId(systemId, baseSystemId));
/* 1836 */     if (this.nonValidatingMode) this.nvGrammarInfo.notationDecl(name, this.fResourceIdentifier, null);
/*      */     
/* 1838 */     if (this.fDTDHandler != null)
/*      */     {
/*      */       
/* 1841 */       this.fDTDHandler.notationDecl(name, this.fResourceIdentifier, null);
/*      */     }
/* 1843 */     this.fReportEntity = true;
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
/*      */   private final void scanConditionalSect(int currPEDepth) throws IOException, XNIException {
/* 1866 */     this.fReportEntity = false;
/* 1867 */     skipSeparator(false, !scanningInternalSubset());
/*      */     
/* 1869 */     if (this.fEntityScanner.skipString("INCLUDE")) {
/* 1870 */       skipSeparator(false, !scanningInternalSubset());
/* 1871 */       if (currPEDepth != this.fPEDepth && this.fValidation) {
/* 1872 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)1);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1878 */       if (!this.fEntityScanner.skipChar(91, null)) {
/* 1879 */         reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
/*      */       }
/*      */       
/* 1882 */       if (this.fDTDHandler != null) {
/* 1883 */         this.fDTDHandler.startConditional((short)0, null);
/*      */       }
/*      */       
/* 1886 */       this.fIncludeSectDepth++;
/*      */       
/* 1888 */       this.fReportEntity = true;
/*      */     } else {
/* 1890 */       if (this.fEntityScanner.skipString("IGNORE")) {
/* 1891 */         skipSeparator(false, !scanningInternalSubset());
/* 1892 */         if (currPEDepth != this.fPEDepth && this.fValidation) {
/* 1893 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[] { this.fEntityManager.fCurrentEntity.name }, (short)1);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1899 */         if (this.fDTDHandler != null) {
/* 1900 */           this.fDTDHandler.startConditional((short)1, null);
/*      */         }
/*      */         
/* 1903 */         if (!this.fEntityScanner.skipChar(91, null)) {
/* 1904 */           reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
/*      */         }
/* 1906 */         this.fReportEntity = true;
/* 1907 */         int initialDepth = ++this.fIncludeSectDepth;
/* 1908 */         if (this.fDTDHandler != null) {
/* 1909 */           this.fIgnoreConditionalBuffer.clear();
/*      */         }
/*      */         while (true) {
/* 1912 */           while (this.fEntityScanner.skipChar(60, null)) {
/* 1913 */             if (this.fDTDHandler != null) {
/* 1914 */               this.fIgnoreConditionalBuffer.append('<');
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1920 */             if (this.fEntityScanner.skipChar(33, null)) {
/* 1921 */               if (this.fEntityScanner.skipChar(91, null)) {
/* 1922 */                 if (this.fDTDHandler != null) {
/* 1923 */                   this.fIgnoreConditionalBuffer.append("![");
/*      */                 }
/* 1925 */                 this.fIncludeSectDepth++; continue;
/*      */               } 
/* 1927 */               if (this.fDTDHandler != null) {
/* 1928 */                 this.fIgnoreConditionalBuffer.append("!");
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/* 1933 */           if (this.fEntityScanner.skipChar(93, null)) {
/* 1934 */             if (this.fDTDHandler != null) {
/* 1935 */               this.fIgnoreConditionalBuffer.append(']');
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1940 */             if (this.fEntityScanner.skipChar(93, null)) {
/* 1941 */               if (this.fDTDHandler != null) {
/* 1942 */                 this.fIgnoreConditionalBuffer.append(']');
/*      */               }
/* 1944 */               while (this.fEntityScanner.skipChar(93, null)) {
/*      */                 
/* 1946 */                 if (this.fDTDHandler != null) {
/* 1947 */                   this.fIgnoreConditionalBuffer.append(']');
/*      */                 }
/*      */               } 
/* 1950 */               if (this.fEntityScanner.skipChar(62, null)) {
/* 1951 */                 if (this.fIncludeSectDepth-- == initialDepth) {
/* 1952 */                   this.fMarkUpDepth--;
/*      */                   
/* 1954 */                   if (this.fDTDHandler != null) {
/* 1955 */                     this.fLiteral.setValues(this.fIgnoreConditionalBuffer.ch, 0, this.fIgnoreConditionalBuffer.length - 2);
/*      */                     
/* 1957 */                     this.fDTDHandler.ignoredCharacters(this.fLiteral, null);
/* 1958 */                     this.fDTDHandler.endConditional(null);
/*      */                   }  return;
/*      */                 } 
/* 1961 */                 if (this.fDTDHandler != null) {
/* 1962 */                   this.fIgnoreConditionalBuffer.append('>');
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1968 */           int c = this.fEntityScanner.scanChar(null);
/* 1969 */           if (this.fScannerState == 0) {
/* 1970 */             reportFatalError("IgnoreSectUnterminated", null);
/*      */             return;
/*      */           } 
/* 1973 */           if (this.fDTDHandler != null) {
/* 1974 */             this.fIgnoreConditionalBuffer.append((char)c);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1980 */       reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
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
/*      */   protected final boolean scanDecls(boolean complete) throws IOException, XNIException {
/* 2000 */     skipSeparator(false, true);
/* 2001 */     boolean again = true;
/*      */     
/* 2003 */     while (again && this.fScannerState == 2) {
/* 2004 */       again = complete;
/* 2005 */       if (this.fEntityScanner.skipChar(60, null)) {
/* 2006 */         this.fMarkUpDepth++;
/* 2007 */         if (this.fEntityScanner.skipChar(63, null)) {
/* 2008 */           this.fStringBuffer.clear();
/* 2009 */           scanPI(this.fStringBuffer);
/* 2010 */           this.fMarkUpDepth--;
/*      */         }
/* 2012 */         else if (this.fEntityScanner.skipChar(33, null)) {
/* 2013 */           if (this.fEntityScanner.skipChar(45, null)) {
/* 2014 */             if (!this.fEntityScanner.skipChar(45, null)) {
/* 2015 */               reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
/*      */             } else {
/*      */               
/* 2018 */               scanComment();
/*      */             }
/*      */           
/* 2021 */           } else if (this.fEntityScanner.skipString("ELEMENT")) {
/* 2022 */             scanElementDecl();
/*      */           }
/* 2024 */           else if (this.fEntityScanner.skipString("ATTLIST")) {
/* 2025 */             scanAttlistDecl();
/*      */           }
/* 2027 */           else if (this.fEntityScanner.skipString("ENTITY")) {
/* 2028 */             scanEntityDecl();
/*      */           }
/* 2030 */           else if (this.fEntityScanner.skipString("NOTATION")) {
/* 2031 */             scanNotationDecl();
/*      */           }
/* 2033 */           else if (this.fEntityScanner.skipChar(91, null) && 
/* 2034 */             !scanningInternalSubset()) {
/* 2035 */             scanConditionalSect(this.fPEDepth);
/*      */           } else {
/*      */             
/* 2038 */             this.fMarkUpDepth--;
/* 2039 */             reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2044 */           this.fMarkUpDepth--;
/* 2045 */           reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null);
/*      */         }
/*      */       
/* 2048 */       } else if (this.fIncludeSectDepth > 0 && this.fEntityScanner.skipChar(93, null)) {
/*      */         
/* 2050 */         if (!this.fEntityScanner.skipChar(93, null) || 
/* 2051 */           !this.fEntityScanner.skipChar(62, null)) {
/* 2052 */           reportFatalError("IncludeSectUnterminated", null);
/*      */         }
/*      */         
/* 2055 */         if (this.fDTDHandler != null) {
/* 2056 */           this.fDTDHandler.endConditional(null);
/*      */         }
/*      */         
/* 2059 */         this.fIncludeSectDepth--;
/* 2060 */         this.fMarkUpDepth--;
/*      */       } else {
/* 2062 */         if (scanningInternalSubset() && this.fEntityScanner
/* 2063 */           .peekChar() == 93)
/*      */         {
/* 2065 */           return false;
/*      */         }
/* 2067 */         if (!this.fEntityScanner.skipSpaces())
/*      */         {
/*      */ 
/*      */           
/* 2071 */           reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", null); } 
/*      */       } 
/* 2073 */       skipSeparator(false, true);
/*      */     } 
/* 2075 */     return (this.fScannerState != 0);
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
/*      */   private boolean skipSeparator(boolean spaceRequired, boolean lookForPERefs) throws IOException, XNIException {
/* 2096 */     int depth = this.fPEDepth;
/* 2097 */     boolean sawSpace = this.fEntityScanner.skipSpaces();
/* 2098 */     if (!lookForPERefs || !this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE)) {
/* 2099 */       return (!spaceRequired || sawSpace || depth != this.fPEDepth);
/*      */     }
/*      */     while (true) {
/* 2102 */       String name = this.fEntityScanner.scanName(XMLScanner.NameType.ENTITY);
/* 2103 */       if (name == null) {
/* 2104 */         reportFatalError("NameRequiredInPEReference", null);
/*      */       }
/* 2106 */       else if (!this.fEntityScanner.skipChar(59, XMLScanner.NameType.REFERENCE)) {
/* 2107 */         reportFatalError("SemicolonRequiredInPEReference", new Object[] { name });
/*      */       } 
/*      */       
/* 2110 */       startPE(name, false);
/* 2111 */       this.fEntityScanner.skipSpaces();
/* 2112 */       if (!this.fEntityScanner.skipChar(37, XMLScanner.NameType.REFERENCE)) {
/* 2113 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void pushContentStack(int c) {
/* 2122 */     if (this.fContentStack.length == this.fContentDepth) {
/* 2123 */       int[] newStack = new int[this.fContentDepth * 2];
/* 2124 */       System.arraycopy(this.fContentStack, 0, newStack, 0, this.fContentDepth);
/* 2125 */       this.fContentStack = newStack;
/*      */     } 
/* 2127 */     this.fContentStack[this.fContentDepth++] = c;
/*      */   }
/*      */   
/*      */   private final int popContentStack() {
/* 2131 */     return this.fContentStack[--this.fContentDepth];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void pushPEStack(int depth, boolean report) {
/* 2139 */     if (this.fPEStack.length == this.fPEDepth) {
/* 2140 */       int[] newIntStack = new int[this.fPEDepth * 2];
/* 2141 */       System.arraycopy(this.fPEStack, 0, newIntStack, 0, this.fPEDepth);
/* 2142 */       this.fPEStack = newIntStack;
/*      */       
/* 2144 */       boolean[] newBooleanStack = new boolean[this.fPEDepth * 2];
/* 2145 */       System.arraycopy(this.fPEReport, 0, newBooleanStack, 0, this.fPEDepth);
/* 2146 */       this.fPEReport = newBooleanStack;
/*      */     } 
/*      */     
/* 2149 */     this.fPEReport[this.fPEDepth] = report;
/* 2150 */     this.fPEStack[this.fPEDepth++] = depth;
/*      */   }
/*      */ 
/*      */   
/*      */   private final int popPEStack() {
/* 2155 */     return this.fPEStack[--this.fPEDepth];
/*      */   }
/*      */ 
/*      */   
/*      */   private final boolean peekReportEntity() {
/* 2160 */     return this.fPEReport[this.fPEDepth - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void ensureEnumerationSize(int size) {
/* 2168 */     if (this.fEnumeration.length == size) {
/* 2169 */       String[] newEnum = new String[size * 2];
/* 2170 */       System.arraycopy(this.fEnumeration, 0, newEnum, 0, size);
/* 2171 */       this.fEnumeration = newEnum;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/* 2178 */     this.fStartDTDCalled = false;
/* 2179 */     this.fExtEntityDepth = 0;
/* 2180 */     this.fIncludeSectDepth = 0;
/* 2181 */     this.fMarkUpDepth = 0;
/* 2182 */     this.fPEDepth = 0;
/*      */     
/* 2184 */     this.fStandalone = false;
/* 2185 */     this.fSeenExternalDTD = false;
/* 2186 */     this.fSeenExternalPE = false;
/*      */ 
/*      */     
/* 2189 */     setScannerState(1);
/*      */ 
/*      */     
/* 2192 */     this.fLimitAnalyzer = this.fEntityManager.fLimitAnalyzer;
/* 2193 */     this.fSecurityManager = this.fEntityManager.fSecurityManager;
/*      */   }
/*      */   
/*      */   public DTDGrammar getGrammar() {
/* 2197 */     return this.nvGrammarInfo;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLDTDScannerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */