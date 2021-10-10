/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.Status;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.xml.internal.stream.Entity;
/*      */ import com.sun.xml.internal.stream.XMLEntityStorage;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import javax.xml.stream.events.XMLEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class XMLScanner
/*      */   implements XMLComponent
/*      */ {
/*      */   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
/*      */   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
/*      */   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
/*      */   protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   protected static final boolean DEBUG_ATTR_NORMALIZATION = false;
/*      */   
/*      */   public enum NameType
/*      */   {
/*  122 */     ATTRIBUTE("attribute"),
/*  123 */     ATTRIBUTENAME("attribute name"),
/*  124 */     COMMENT("comment"),
/*  125 */     DOCTYPE("doctype"),
/*  126 */     ELEMENTSTART("startelement"),
/*  127 */     ELEMENTEND("endelement"),
/*  128 */     ENTITY("entity"),
/*  129 */     NOTATION("notation"),
/*  130 */     PI("pi"),
/*  131 */     REFERENCE("reference");
/*      */     final String literal;
/*      */     
/*      */     NameType(String literal) {
/*  135 */       this.literal = literal;
/*      */     }
/*      */     
/*      */     String literal() {
/*  139 */       return this.literal;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fNeedNonNormalizedValue = false;
/*      */ 
/*      */   
/*  147 */   protected ArrayList<XMLString> attributeValueCache = new ArrayList<>();
/*  148 */   protected ArrayList<XMLStringBuffer> stringBufferCache = new ArrayList<>();
/*  149 */   protected int fStringBufferIndex = 0;
/*      */   protected boolean fAttributeCacheInitDone = false;
/*  151 */   protected int fAttributeCacheUsedCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fValidation = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fNamespaces;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fNotifyCharRefs = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fParserSettings = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  176 */   protected PropertyManager fPropertyManager = null;
/*      */ 
/*      */   
/*      */   protected SymbolTable fSymbolTable;
/*      */ 
/*      */   
/*      */   protected XMLErrorReporter fErrorReporter;
/*      */ 
/*      */   
/*  185 */   protected XMLEntityManager fEntityManager = null;
/*      */ 
/*      */   
/*  188 */   protected XMLEntityStorage fEntityStore = null;
/*      */ 
/*      */   
/*  191 */   protected XMLSecurityManager fSecurityManager = null;
/*      */ 
/*      */   
/*  194 */   protected XMLLimitAnalyzer fLimitAnalyzer = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLEvent fEvent;
/*      */ 
/*      */ 
/*      */   
/*  202 */   protected XMLEntityScanner fEntityScanner = null;
/*      */ 
/*      */   
/*      */   protected int fEntityDepth;
/*      */ 
/*      */   
/*  208 */   protected String fCharRefLiteral = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fScanningAttribute;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fReportEntity;
/*      */ 
/*      */   
/*  219 */   protected static final String fVersionSymbol = "version".intern();
/*      */ 
/*      */   
/*  222 */   protected static final String fEncodingSymbol = "encoding".intern();
/*      */ 
/*      */   
/*  225 */   protected static final String fStandaloneSymbol = "standalone".intern();
/*      */ 
/*      */   
/*  228 */   protected static final String fAmpSymbol = "amp".intern();
/*      */ 
/*      */   
/*  231 */   protected static final String fLtSymbol = "lt".intern();
/*      */ 
/*      */   
/*  234 */   protected static final String fGtSymbol = "gt".intern();
/*      */ 
/*      */   
/*  237 */   protected static final String fQuotSymbol = "quot".intern();
/*      */ 
/*      */   
/*  240 */   protected static final String fAposSymbol = "apos".intern();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  251 */   private XMLString fString = new XMLString();
/*      */ 
/*      */   
/*  254 */   private XMLStringBuffer fStringBuffer = new XMLStringBuffer();
/*      */ 
/*      */   
/*  257 */   private XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
/*      */ 
/*      */   
/*  260 */   private XMLStringBuffer fStringBuffer3 = new XMLStringBuffer();
/*      */ 
/*      */   
/*  263 */   protected XMLResourceIdentifierImpl fResourceIdentifier = new XMLResourceIdentifierImpl();
/*  264 */   int initialCacheCount = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  280 */     this.fParserSettings = componentManager.getFeature("http://apache.org/xml/features/internal/parser-settings", true);
/*      */     
/*  282 */     if (!this.fParserSettings) {
/*      */       
/*  284 */       init();
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  290 */     this.fSymbolTable = (SymbolTable)componentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*  291 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*  292 */     this.fEntityManager = (XMLEntityManager)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager");
/*  293 */     this.fSecurityManager = (XMLSecurityManager)componentManager.getProperty("http://apache.org/xml/properties/security-manager");
/*      */ 
/*      */     
/*  296 */     this.fEntityStore = this.fEntityManager.getEntityStore();
/*      */ 
/*      */     
/*  299 */     this.fValidation = componentManager.getFeature("http://xml.org/sax/features/validation", false);
/*  300 */     this.fNamespaces = componentManager.getFeature("http://xml.org/sax/features/namespaces", true);
/*  301 */     this.fNotifyCharRefs = componentManager.getFeature("http://apache.org/xml/features/scanner/notify-char-refs", false);
/*      */     
/*  303 */     init();
/*      */   }
/*      */   
/*      */   protected void setPropertyManager(PropertyManager propertyManager) {
/*  307 */     this.fPropertyManager = propertyManager;
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
/*      */   public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
/*  320 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/*      */       
/*  322 */       String property = propertyId.substring("http://apache.org/xml/properties/".length());
/*  323 */       if (property.equals("internal/symbol-table")) {
/*  324 */         this.fSymbolTable = (SymbolTable)value;
/*  325 */       } else if (property.equals("internal/error-reporter")) {
/*  326 */         this.fErrorReporter = (XMLErrorReporter)value;
/*  327 */       } else if (property.equals("internal/entity-manager")) {
/*  328 */         this.fEntityManager = (XMLEntityManager)value;
/*      */       } 
/*      */     } 
/*      */     
/*  332 */     if (propertyId.equals("http://apache.org/xml/properties/security-manager")) {
/*  333 */       this.fSecurityManager = (XMLSecurityManager)value;
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
/*      */   public void setFeature(String featureId, boolean value) throws XMLConfigurationException {
/*  349 */     if ("http://xml.org/sax/features/validation".equals(featureId)) {
/*  350 */       this.fValidation = value;
/*  351 */     } else if ("http://apache.org/xml/features/scanner/notify-char-refs".equals(featureId)) {
/*  352 */       this.fNotifyCharRefs = value;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(String featureId) throws XMLConfigurationException {
/*  362 */     if ("http://xml.org/sax/features/validation".equals(featureId))
/*  363 */       return this.fValidation; 
/*  364 */     if ("http://apache.org/xml/features/scanner/notify-char-refs".equals(featureId)) {
/*  365 */       return this.fNotifyCharRefs;
/*      */     }
/*  367 */     throw new XMLConfigurationException(Status.NOT_RECOGNIZED, featureId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reset() {
/*  376 */     init();
/*      */ 
/*      */     
/*  379 */     this.fValidation = true;
/*  380 */     this.fNotifyCharRefs = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager propertyManager) {
/*  385 */     init();
/*      */     
/*  387 */     this.fSymbolTable = (SymbolTable)propertyManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*      */     
/*  389 */     this.fErrorReporter = (XMLErrorReporter)propertyManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*      */     
/*  391 */     this.fEntityManager = (XMLEntityManager)propertyManager.getProperty("http://apache.org/xml/properties/internal/entity-manager");
/*  392 */     this.fEntityStore = this.fEntityManager.getEntityStore();
/*  393 */     this.fEntityScanner = this.fEntityManager.getEntityScanner();
/*  394 */     this.fSecurityManager = (XMLSecurityManager)propertyManager.getProperty("http://apache.org/xml/properties/security-manager");
/*      */ 
/*      */ 
/*      */     
/*  398 */     this.fValidation = false;
/*  399 */     this.fNotifyCharRefs = false;
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
/*      */   protected void scanXMLDeclOrTextDecl(boolean scanningTextDecl, String[] pseudoAttributeValues) throws IOException, XNIException {
/*  433 */     String version = null;
/*  434 */     String encoding = null;
/*  435 */     String standalone = null;
/*      */ 
/*      */     
/*  438 */     int STATE_VERSION = 0;
/*  439 */     int STATE_ENCODING = 1;
/*  440 */     int STATE_STANDALONE = 2;
/*  441 */     int STATE_DONE = 3;
/*  442 */     int state = 0;
/*      */     
/*  444 */     boolean dataFoundForTarget = false;
/*  445 */     boolean sawSpace = this.fEntityScanner.skipSpaces();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  453 */     Entity.ScannedEntity currEnt = this.fEntityManager.getCurrentEntity();
/*  454 */     boolean currLiteral = currEnt.literal;
/*  455 */     currEnt.literal = false;
/*  456 */     while (this.fEntityScanner.peekChar() != 63) {
/*  457 */       dataFoundForTarget = true;
/*  458 */       String name = scanPseudoAttribute(scanningTextDecl, this.fString);
/*  459 */       switch (state) {
/*      */         case 0:
/*  461 */           if (name.equals(fVersionSymbol)) {
/*  462 */             if (!sawSpace) {
/*  463 */               reportFatalError(scanningTextDecl ? "SpaceRequiredBeforeVersionInTextDecl" : "SpaceRequiredBeforeVersionInXMLDecl", null);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  468 */             version = this.fString.toString();
/*  469 */             state = 1;
/*  470 */             if (!versionSupported(version)) {
/*  471 */               reportFatalError("VersionNotSupported", new Object[] { version });
/*      */             }
/*      */ 
/*      */             
/*  475 */             if (version.equals("1.1")) {
/*  476 */               Entity.ScannedEntity top = this.fEntityManager.getTopLevelEntity();
/*  477 */               if (top != null && (top.version == null || top.version.equals("1.0"))) {
/*  478 */                 reportFatalError("VersionMismatch", null);
/*      */               }
/*  480 */               this.fEntityManager.setScannerVersion((short)2);
/*      */             }  break;
/*      */           } 
/*  483 */           if (name.equals(fEncodingSymbol)) {
/*  484 */             if (!scanningTextDecl) {
/*  485 */               reportFatalError("VersionInfoRequired", null);
/*      */             }
/*  487 */             if (!sawSpace) {
/*  488 */               reportFatalError(scanningTextDecl ? "SpaceRequiredBeforeEncodingInTextDecl" : "SpaceRequiredBeforeEncodingInXMLDecl", null);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  493 */             encoding = this.fString.toString();
/*  494 */             state = scanningTextDecl ? 3 : 2; break;
/*      */           } 
/*  496 */           if (scanningTextDecl) {
/*  497 */             reportFatalError("EncodingDeclRequired", null); break;
/*      */           } 
/*  499 */           reportFatalError("VersionInfoRequired", null);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/*  505 */           if (name.equals(fEncodingSymbol)) {
/*  506 */             if (!sawSpace) {
/*  507 */               reportFatalError(scanningTextDecl ? "SpaceRequiredBeforeEncodingInTextDecl" : "SpaceRequiredBeforeEncodingInXMLDecl", null);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  512 */             encoding = this.fString.toString();
/*  513 */             state = scanningTextDecl ? 3 : 2;
/*      */             break;
/*      */           } 
/*  516 */           if (!scanningTextDecl && name.equals(fStandaloneSymbol)) {
/*  517 */             if (!sawSpace) {
/*  518 */               reportFatalError("SpaceRequiredBeforeStandalone", null);
/*      */             }
/*      */             
/*  521 */             standalone = this.fString.toString();
/*  522 */             state = 3;
/*  523 */             if (!standalone.equals("yes") && !standalone.equals("no"))
/*  524 */               reportFatalError("SDDeclInvalid", new Object[] { standalone }); 
/*      */             break;
/*      */           } 
/*  527 */           reportFatalError("EncodingDeclRequired", null);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/*  532 */           if (name.equals(fStandaloneSymbol)) {
/*  533 */             if (!sawSpace) {
/*  534 */               reportFatalError("SpaceRequiredBeforeStandalone", null);
/*      */             }
/*      */             
/*  537 */             standalone = this.fString.toString();
/*  538 */             state = 3;
/*  539 */             if (!standalone.equals("yes") && !standalone.equals("no"))
/*  540 */               reportFatalError("SDDeclInvalid", new Object[] { standalone }); 
/*      */             break;
/*      */           } 
/*  543 */           reportFatalError("SDDeclNameInvalid", null);
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  548 */           reportFatalError("NoMorePseudoAttributes", null);
/*      */           break;
/*      */       } 
/*  551 */       sawSpace = this.fEntityScanner.skipSpaces();
/*      */     } 
/*      */     
/*  554 */     if (currLiteral) {
/*  555 */       currEnt.literal = true;
/*      */     }
/*      */     
/*  558 */     if (scanningTextDecl && state != 3) {
/*  559 */       reportFatalError("MorePseudoAttributes", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  564 */     if (scanningTextDecl) {
/*  565 */       if (!dataFoundForTarget && encoding == null) {
/*  566 */         reportFatalError("EncodingDeclRequired", null);
/*      */       }
/*      */     }
/*  569 */     else if (!dataFoundForTarget && version == null) {
/*  570 */       reportFatalError("VersionInfoRequired", null);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  575 */     if (!this.fEntityScanner.skipChar(63, null)) {
/*  576 */       reportFatalError("XMLDeclUnterminated", null);
/*      */     }
/*  578 */     if (!this.fEntityScanner.skipChar(62, null)) {
/*  579 */       reportFatalError("XMLDeclUnterminated", null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  584 */     pseudoAttributeValues[0] = version;
/*  585 */     pseudoAttributeValues[1] = encoding;
/*  586 */     pseudoAttributeValues[2] = standalone;
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
/*      */   protected String scanPseudoAttribute(boolean scanningTextDecl, XMLString value) throws IOException, XNIException {
/*  609 */     String name = scanPseudoAttributeName();
/*      */ 
/*      */     
/*  612 */     if (name == null) {
/*  613 */       reportFatalError("PseudoAttrNameExpected", null);
/*      */     }
/*  615 */     this.fEntityScanner.skipSpaces();
/*  616 */     if (!this.fEntityScanner.skipChar(61, null)) {
/*  617 */       reportFatalError(scanningTextDecl ? "EqRequiredInTextDecl" : "EqRequiredInXMLDecl", new Object[] { name });
/*      */     }
/*      */     
/*  620 */     this.fEntityScanner.skipSpaces();
/*  621 */     int quote = this.fEntityScanner.peekChar();
/*  622 */     if (quote != 39 && quote != 34) {
/*  623 */       reportFatalError(scanningTextDecl ? "QuoteRequiredInTextDecl" : "QuoteRequiredInXMLDecl", new Object[] { name });
/*      */     }
/*      */     
/*  626 */     this.fEntityScanner.scanChar(NameType.ATTRIBUTE);
/*  627 */     int c = this.fEntityScanner.scanLiteral(quote, value, false);
/*  628 */     if (c != quote) {
/*  629 */       this.fStringBuffer2.clear();
/*      */       while (true) {
/*  631 */         this.fStringBuffer2.append(value);
/*  632 */         if (c != -1) {
/*  633 */           if (c == 38 || c == 37 || c == 60 || c == 93) {
/*  634 */             this.fStringBuffer2.append((char)this.fEntityScanner.scanChar(NameType.ATTRIBUTE));
/*  635 */           } else if (XMLChar.isHighSurrogate(c)) {
/*  636 */             scanSurrogates(this.fStringBuffer2);
/*  637 */           } else if (isInvalidLiteral(c)) {
/*  638 */             String key = scanningTextDecl ? "InvalidCharInTextDecl" : "InvalidCharInXMLDecl";
/*      */             
/*  640 */             reportFatalError(key, new Object[] {
/*  641 */                   Integer.toString(c, 16) });
/*  642 */             this.fEntityScanner.scanChar(null);
/*      */           } 
/*      */         }
/*  645 */         c = this.fEntityScanner.scanLiteral(quote, value, false);
/*  646 */         if (c == quote)
/*  647 */         { this.fStringBuffer2.append(value);
/*  648 */           value.setValues(this.fStringBuffer2); break; } 
/*      */       } 
/*  650 */     }  if (!this.fEntityScanner.skipChar(quote, null)) {
/*  651 */       reportFatalError(scanningTextDecl ? "CloseQuoteMissingInTextDecl" : "CloseQuoteMissingInXMLDecl", new Object[] { name });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  657 */     return name;
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
/*      */   private String scanPseudoAttributeName() throws IOException, XNIException {
/*  669 */     int ch = this.fEntityScanner.peekChar();
/*  670 */     switch (ch) {
/*      */       case 118:
/*  672 */         if (this.fEntityScanner.skipString(fVersionSymbol)) {
/*  673 */           return fVersionSymbol;
/*      */         }
/*      */         break;
/*      */       case 101:
/*  677 */         if (this.fEntityScanner.skipString(fEncodingSymbol)) {
/*  678 */           return fEncodingSymbol;
/*      */         }
/*      */         break;
/*      */       case 115:
/*  682 */         if (this.fEntityScanner.skipString(fStandaloneSymbol)) {
/*  683 */           return fStandaloneSymbol;
/*      */         }
/*      */         break;
/*      */     } 
/*  687 */     return null;
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
/*      */   protected void scanPI(XMLStringBuffer data) throws IOException, XNIException {
/*  707 */     this.fReportEntity = false;
/*  708 */     String target = this.fEntityScanner.scanName(NameType.PI);
/*  709 */     if (target == null) {
/*  710 */       reportFatalError("PITargetRequired", null);
/*      */     }
/*      */ 
/*      */     
/*  714 */     scanPIData(target, data);
/*  715 */     this.fReportEntity = true;
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
/*      */   protected void scanPIData(String target, XMLStringBuffer data) throws IOException, XNIException {
/*  741 */     if (target.length() == 3) {
/*  742 */       char c0 = Character.toLowerCase(target.charAt(0));
/*  743 */       char c1 = Character.toLowerCase(target.charAt(1));
/*  744 */       char c2 = Character.toLowerCase(target.charAt(2));
/*  745 */       if (c0 == 'x' && c1 == 'm' && c2 == 'l') {
/*  746 */         reportFatalError("ReservedPITarget", null);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  751 */     if (!this.fEntityScanner.skipSpaces()) {
/*  752 */       if (this.fEntityScanner.skipString("?>")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  757 */       reportFatalError("SpaceRequiredInPI", null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  764 */     if (this.fEntityScanner.scanData("?>", data)) {
/*      */       do {
/*  766 */         int c = this.fEntityScanner.peekChar();
/*  767 */         if (c == -1)
/*  768 */           continue;  if (XMLChar.isHighSurrogate(c)) {
/*  769 */           scanSurrogates(data);
/*  770 */         } else if (isInvalidLiteral(c)) {
/*  771 */           reportFatalError("InvalidCharInPI", new Object[] {
/*  772 */                 Integer.toHexString(c) });
/*  773 */           this.fEntityScanner.scanChar(null);
/*      */         }
/*      */       
/*  776 */       } while (this.fEntityScanner.scanData("?>", data));
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
/*      */   protected void scanComment(XMLStringBuffer text) throws IOException, XNIException {
/*  800 */     text.clear();
/*  801 */     while (this.fEntityScanner.scanData("--", text)) {
/*  802 */       int c = this.fEntityScanner.peekChar();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  807 */       if (c != -1) {
/*  808 */         if (XMLChar.isHighSurrogate(c)) {
/*  809 */           scanSurrogates(text); continue;
/*      */         } 
/*  811 */         if (isInvalidLiteral(c)) {
/*  812 */           reportFatalError("InvalidCharInComment", new Object[] {
/*  813 */                 Integer.toHexString(c) });
/*  814 */           this.fEntityScanner.scanChar(NameType.COMMENT);
/*      */         } 
/*      */       } 
/*      */     } 
/*  818 */     if (!this.fEntityScanner.skipChar(62, NameType.COMMENT)) {
/*  819 */       reportFatalError("DashDashInComment", null);
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
/*      */   protected void scanAttributeValue(XMLString value, XMLString nonNormalizedValue, String atName, XMLAttributes attributes, int attrIndex, boolean checkEntities, String eleName, boolean isNSURI) throws IOException, XNIException {
/*  848 */     XMLStringBuffer stringBuffer = null;
/*      */     
/*  850 */     int quote = this.fEntityScanner.peekChar();
/*  851 */     if (quote != 39 && quote != 34) {
/*  852 */       reportFatalError("OpenQuoteExpected", new Object[] { eleName, atName });
/*      */     }
/*      */     
/*  855 */     this.fEntityScanner.scanChar(NameType.ATTRIBUTE);
/*  856 */     int entityDepth = this.fEntityDepth;
/*      */     
/*  858 */     int c = this.fEntityScanner.scanLiteral(quote, value, isNSURI);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  863 */     if (this.fNeedNonNormalizedValue) {
/*  864 */       this.fStringBuffer2.clear();
/*  865 */       this.fStringBuffer2.append(value);
/*      */     } 
/*  867 */     if (this.fEntityScanner.whiteSpaceLen > 0) {
/*  868 */       normalizeWhitespace(value);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  873 */     if (c != quote) {
/*  874 */       this.fScanningAttribute = true;
/*  875 */       stringBuffer = getStringBuffer();
/*  876 */       stringBuffer.clear();
/*      */       while (true) {
/*  878 */         stringBuffer.append(value);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  883 */         if (c == 38) {
/*  884 */           this.fEntityScanner.skipChar(38, NameType.REFERENCE);
/*  885 */           if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  886 */             this.fStringBuffer2.append('&');
/*      */           }
/*  888 */           if (this.fEntityScanner.skipChar(35, NameType.REFERENCE)) {
/*  889 */             int ch; if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  890 */               this.fStringBuffer2.append('#');
/*      */             }
/*      */             
/*  893 */             if (this.fNeedNonNormalizedValue) {
/*  894 */               ch = scanCharReferenceValue(stringBuffer, this.fStringBuffer2);
/*      */             } else {
/*  896 */               ch = scanCharReferenceValue(stringBuffer, null);
/*      */             } 
/*  898 */             if (ch != -1);
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/*  906 */             String entityName = this.fEntityScanner.scanName(NameType.ENTITY);
/*  907 */             if (entityName == null) {
/*  908 */               reportFatalError("NameRequiredInReference", null);
/*  909 */             } else if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  910 */               this.fStringBuffer2.append(entityName);
/*      */             } 
/*  912 */             if (!this.fEntityScanner.skipChar(59, NameType.REFERENCE)) {
/*  913 */               reportFatalError("SemicolonRequiredInReference", new Object[] { entityName });
/*      */             }
/*  915 */             else if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  916 */               this.fStringBuffer2.append(';');
/*      */             } 
/*  918 */             if (resolveCharacter(entityName, stringBuffer)) {
/*  919 */               checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, 1);
/*      */             }
/*  921 */             else if (this.fEntityStore.isExternalEntity(entityName)) {
/*  922 */               reportFatalError("ReferenceToExternalEntity", new Object[] { entityName });
/*      */             } else {
/*      */               
/*  925 */               if (!this.fEntityStore.isDeclaredEntity(entityName))
/*      */               {
/*  927 */                 if (checkEntities) {
/*  928 */                   if (this.fValidation) {
/*  929 */                     this.fErrorReporter.reportError(this.fEntityScanner, "http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[] { entityName }, (short)1);
/*      */                   
/*      */                   }
/*      */                 }
/*      */                 else {
/*      */                   
/*  935 */                   reportFatalError("EntityNotDeclared", new Object[] { entityName });
/*      */                 } 
/*      */               }
/*      */               
/*  939 */               this.fEntityManager.startEntity(true, entityName, true);
/*      */             }
/*      */           
/*      */           } 
/*  943 */         } else if (c == 60) {
/*  944 */           reportFatalError("LessthanInAttValue", new Object[] { eleName, atName });
/*      */           
/*  946 */           this.fEntityScanner.scanChar(null);
/*  947 */           if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  948 */             this.fStringBuffer2.append((char)c);
/*      */           }
/*  950 */         } else if (c == 37 || c == 93) {
/*  951 */           this.fEntityScanner.scanChar(null);
/*  952 */           stringBuffer.append((char)c);
/*  953 */           if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  954 */             this.fStringBuffer2.append((char)c);
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  960 */         else if (c == 10 || c == 13) {
/*  961 */           this.fEntityScanner.scanChar(null);
/*  962 */           stringBuffer.append(' ');
/*  963 */           if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  964 */             this.fStringBuffer2.append('\n');
/*      */           }
/*  966 */         } else if (c != -1 && XMLChar.isHighSurrogate(c)) {
/*  967 */           this.fStringBuffer3.clear();
/*  968 */           if (scanSurrogates(this.fStringBuffer3)) {
/*  969 */             stringBuffer.append(this.fStringBuffer3);
/*  970 */             if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  971 */               this.fStringBuffer2.append(this.fStringBuffer3);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  979 */         else if (c != -1 && isInvalidLiteral(c)) {
/*  980 */           reportFatalError("InvalidCharInAttValue", new Object[] { eleName, atName, 
/*  981 */                 Integer.toString(c, 16) });
/*  982 */           this.fEntityScanner.scanChar(null);
/*  983 */           if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  984 */             this.fStringBuffer2.append((char)c);
/*      */           }
/*      */         } 
/*  987 */         c = this.fEntityScanner.scanLiteral(quote, value, isNSURI);
/*  988 */         if (entityDepth == this.fEntityDepth && this.fNeedNonNormalizedValue) {
/*  989 */           this.fStringBuffer2.append(value);
/*      */         }
/*  991 */         if (this.fEntityScanner.whiteSpaceLen > 0) {
/*  992 */           normalizeWhitespace(value);
/*      */         }
/*      */         
/*  995 */         if (c == quote && entityDepth == this.fEntityDepth)
/*  996 */         { stringBuffer.append(value);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1001 */           value.setValues(stringBuffer);
/* 1002 */           this.fScanningAttribute = false; break; } 
/*      */       } 
/* 1004 */     }  if (this.fNeedNonNormalizedValue) {
/* 1005 */       nonNormalizedValue.setValues(this.fStringBuffer2);
/*      */     }
/*      */     
/* 1008 */     int cquote = this.fEntityScanner.scanChar(NameType.ATTRIBUTE);
/* 1009 */     if (cquote != quote) {
/* 1010 */       reportFatalError("CloseQuoteExpected", new Object[] { eleName, atName });
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
/*      */   protected boolean resolveCharacter(String entityName, XMLStringBuffer stringBuffer) {
/* 1027 */     if (entityName == fAmpSymbol) {
/* 1028 */       stringBuffer.append('&');
/* 1029 */       return true;
/* 1030 */     }  if (entityName == fAposSymbol) {
/* 1031 */       stringBuffer.append('\'');
/* 1032 */       return true;
/* 1033 */     }  if (entityName == fLtSymbol) {
/* 1034 */       stringBuffer.append('<');
/* 1035 */       return true;
/* 1036 */     }  if (entityName == fGtSymbol) {
/* 1037 */       checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, 1);
/* 1038 */       stringBuffer.append('>');
/* 1039 */       return true;
/* 1040 */     }  if (entityName == fQuotSymbol) {
/* 1041 */       checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, 1);
/* 1042 */       stringBuffer.append('"');
/* 1043 */       return true;
/*      */     } 
/* 1045 */     return false;
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
/*      */   protected void scanExternalID(String[] identifiers, boolean optionalSystemId) throws IOException, XNIException {
/* 1062 */     String systemId = null;
/* 1063 */     String publicId = null;
/* 1064 */     if (this.fEntityScanner.skipString("PUBLIC")) {
/* 1065 */       if (!this.fEntityScanner.skipSpaces()) {
/* 1066 */         reportFatalError("SpaceRequiredAfterPUBLIC", null);
/*      */       }
/* 1068 */       scanPubidLiteral(this.fString);
/* 1069 */       publicId = this.fString.toString();
/*      */       
/* 1071 */       if (!this.fEntityScanner.skipSpaces() && !optionalSystemId) {
/* 1072 */         reportFatalError("SpaceRequiredBetweenPublicAndSystem", null);
/*      */       }
/*      */     } 
/*      */     
/* 1076 */     if (publicId != null || this.fEntityScanner.skipString("SYSTEM")) {
/* 1077 */       if (publicId == null && !this.fEntityScanner.skipSpaces()) {
/* 1078 */         reportFatalError("SpaceRequiredAfterSYSTEM", null);
/*      */       }
/* 1080 */       int quote = this.fEntityScanner.peekChar();
/* 1081 */       if (quote != 39 && quote != 34) {
/* 1082 */         if (publicId != null && optionalSystemId) {
/*      */ 
/*      */           
/* 1085 */           identifiers[0] = null;
/* 1086 */           identifiers[1] = publicId;
/*      */           return;
/*      */         } 
/* 1089 */         reportFatalError("QuoteRequiredInSystemID", null);
/*      */       } 
/* 1091 */       this.fEntityScanner.scanChar(null);
/* 1092 */       XMLString ident = this.fString;
/* 1093 */       if (this.fEntityScanner.scanLiteral(quote, ident, false) != quote) {
/* 1094 */         this.fStringBuffer.clear();
/*      */         while (true) {
/* 1096 */           this.fStringBuffer.append(ident);
/* 1097 */           int c = this.fEntityScanner.peekChar();
/* 1098 */           if (XMLChar.isMarkup(c) || c == 93) {
/* 1099 */             this.fStringBuffer.append((char)this.fEntityScanner.scanChar(null));
/* 1100 */           } else if (c != -1 && isInvalidLiteral(c)) {
/* 1101 */             reportFatalError("InvalidCharInSystemID", new Object[] {
/* 1102 */                   Integer.toString(c, 16) });
/*      */           } 
/* 1104 */           if (this.fEntityScanner.scanLiteral(quote, ident, false) == quote)
/* 1105 */           { this.fStringBuffer.append(ident);
/* 1106 */             ident = this.fStringBuffer; break; } 
/*      */         } 
/* 1108 */       }  systemId = ident.toString();
/* 1109 */       if (!this.fEntityScanner.skipChar(quote, null)) {
/* 1110 */         reportFatalError("SystemIDUnterminated", null);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1115 */     identifiers[0] = systemId;
/* 1116 */     identifiers[1] = publicId;
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
/*      */   protected boolean scanPubidLiteral(XMLString literal) throws IOException, XNIException {
/* 1141 */     int quote = this.fEntityScanner.scanChar(null);
/* 1142 */     if (quote != 39 && quote != 34) {
/* 1143 */       reportFatalError("QuoteRequiredInPublicID", null);
/* 1144 */       return false;
/*      */     } 
/*      */     
/* 1147 */     this.fStringBuffer.clear();
/*      */     
/* 1149 */     boolean skipSpace = true;
/* 1150 */     boolean dataok = true;
/*      */     while (true) {
/* 1152 */       int c = this.fEntityScanner.scanChar(null);
/* 1153 */       if (c == 32 || c == 10 || c == 13) {
/* 1154 */         if (!skipSpace) {
/*      */           
/* 1156 */           this.fStringBuffer.append(' ');
/* 1157 */           skipSpace = true;
/*      */         }  continue;
/* 1159 */       }  if (c == quote) {
/* 1160 */         if (skipSpace)
/*      */         {
/* 1162 */           this.fStringBuffer.length--;
/*      */         }
/* 1164 */         literal.setValues(this.fStringBuffer); break;
/*      */       } 
/* 1166 */       if (XMLChar.isPubid(c)) {
/* 1167 */         this.fStringBuffer.append((char)c);
/* 1168 */         skipSpace = false; continue;
/* 1169 */       }  if (c == -1) {
/* 1170 */         reportFatalError("PublicIDUnterminated", null);
/* 1171 */         return false;
/*      */       } 
/* 1173 */       dataok = false;
/* 1174 */       reportFatalError("InvalidCharInPublicID", new Object[] {
/* 1175 */             Integer.toHexString(c)
/*      */           });
/*      */     } 
/* 1178 */     return dataok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void normalizeWhitespace(XMLString value) {
/* 1187 */     int i = 0;
/* 1188 */     int j = 0;
/* 1189 */     int[] buff = this.fEntityScanner.whiteSpaceLookup;
/* 1190 */     int buffLen = this.fEntityScanner.whiteSpaceLen;
/* 1191 */     int end = value.offset + value.length;
/* 1192 */     while (i < buffLen) {
/* 1193 */       j = buff[i];
/* 1194 */       if (j < end) {
/* 1195 */         value.ch[j] = ' ';
/*      */       }
/* 1197 */       i++;
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
/*      */   public void startEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {
/* 1226 */     this.fEntityDepth++;
/*      */     
/* 1228 */     this.fEntityScanner = this.fEntityManager.getEntityScanner();
/* 1229 */     this.fEntityStore = this.fEntityManager.getEntityStore();
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
/* 1245 */     this.fEntityDepth--;
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
/*      */   protected int scanCharReferenceValue(XMLStringBuffer buf, XMLStringBuffer buf2) throws IOException, XNIException {
/* 1268 */     int initLen = buf.length;
/*      */     
/* 1270 */     boolean hex = false;
/* 1271 */     if (this.fEntityScanner.skipChar(120, NameType.REFERENCE)) {
/* 1272 */       if (buf2 != null) buf2.append('x'); 
/* 1273 */       hex = true;
/* 1274 */       this.fStringBuffer3.clear();
/* 1275 */       boolean digit = true;
/*      */       
/* 1277 */       int c = this.fEntityScanner.peekChar();
/* 1278 */       digit = ((c >= 48 && c <= 57) || (c >= 97 && c <= 102) || (c >= 65 && c <= 70));
/*      */ 
/*      */       
/* 1281 */       if (digit) {
/* 1282 */         if (buf2 != null) buf2.append((char)c); 
/* 1283 */         this.fEntityScanner.scanChar(NameType.REFERENCE);
/* 1284 */         this.fStringBuffer3.append((char)c);
/*      */         
/*      */         do {
/* 1287 */           c = this.fEntityScanner.peekChar();
/* 1288 */           digit = ((c >= 48 && c <= 57) || (c >= 97 && c <= 102) || (c >= 65 && c <= 70));
/*      */ 
/*      */           
/* 1291 */           if (!digit)
/* 1292 */             continue;  if (buf2 != null) buf2.append((char)c); 
/* 1293 */           this.fEntityScanner.scanChar(NameType.REFERENCE);
/* 1294 */           this.fStringBuffer3.append((char)c);
/*      */         }
/* 1296 */         while (digit);
/*      */       } else {
/* 1298 */         reportFatalError("HexdigitRequiredInCharRef", null);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1304 */       this.fStringBuffer3.clear();
/* 1305 */       boolean digit = true;
/*      */       
/* 1307 */       int c = this.fEntityScanner.peekChar();
/* 1308 */       digit = (c >= 48 && c <= 57);
/* 1309 */       if (digit) {
/* 1310 */         if (buf2 != null) buf2.append((char)c); 
/* 1311 */         this.fEntityScanner.scanChar(NameType.REFERENCE);
/* 1312 */         this.fStringBuffer3.append((char)c);
/*      */         
/*      */         do {
/* 1315 */           c = this.fEntityScanner.peekChar();
/* 1316 */           digit = (c >= 48 && c <= 57);
/* 1317 */           if (!digit)
/* 1318 */             continue;  if (buf2 != null) buf2.append((char)c); 
/* 1319 */           this.fEntityScanner.scanChar(NameType.REFERENCE);
/* 1320 */           this.fStringBuffer3.append((char)c);
/*      */         }
/* 1322 */         while (digit);
/*      */       } else {
/* 1324 */         reportFatalError("DigitRequiredInCharRef", null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1329 */     if (!this.fEntityScanner.skipChar(59, NameType.REFERENCE)) {
/* 1330 */       reportFatalError("SemicolonRequiredInCharRef", null);
/*      */     }
/* 1332 */     if (buf2 != null) buf2.append(';');
/*      */ 
/*      */     
/* 1335 */     int value = -1;
/*      */     try {
/* 1337 */       value = Integer.parseInt(this.fStringBuffer3.toString(), hex ? 16 : 10);
/*      */ 
/*      */ 
/*      */       
/* 1341 */       if (isInvalid(value)) {
/* 1342 */         StringBuffer errorBuf = new StringBuffer(this.fStringBuffer3.length + 1);
/* 1343 */         if (hex) errorBuf.append('x'); 
/* 1344 */         errorBuf.append(this.fStringBuffer3.ch, this.fStringBuffer3.offset, this.fStringBuffer3.length);
/* 1345 */         reportFatalError("InvalidCharRef", new Object[] { errorBuf
/* 1346 */               .toString() });
/*      */       } 
/* 1348 */     } catch (NumberFormatException e) {
/*      */ 
/*      */       
/* 1351 */       StringBuffer errorBuf = new StringBuffer(this.fStringBuffer3.length + 1);
/* 1352 */       if (hex) errorBuf.append('x'); 
/* 1353 */       errorBuf.append(this.fStringBuffer3.ch, this.fStringBuffer3.offset, this.fStringBuffer3.length);
/* 1354 */       reportFatalError("InvalidCharRef", new Object[] { errorBuf
/* 1355 */             .toString() });
/*      */     } 
/*      */ 
/*      */     
/* 1359 */     if (!XMLChar.isSupplemental(value)) {
/* 1360 */       buf.append((char)value);
/*      */     } else {
/*      */       
/* 1363 */       buf.append(XMLChar.highSurrogate(value));
/* 1364 */       buf.append(XMLChar.lowSurrogate(value));
/*      */     } 
/*      */ 
/*      */     
/* 1368 */     if (this.fNotifyCharRefs && value != -1) {
/* 1369 */       String literal = "#" + (hex ? "x" : "") + this.fStringBuffer3.toString();
/* 1370 */       if (!this.fScanningAttribute) {
/* 1371 */         this.fCharRefLiteral = literal;
/*      */       }
/*      */     } 
/*      */     
/* 1375 */     if (this.fEntityScanner.fCurrentEntity.isGE) {
/* 1376 */       checkEntityLimit(false, this.fEntityScanner.fCurrentEntity.name, buf.length - initLen);
/*      */     }
/* 1378 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isInvalid(int value) {
/* 1384 */     return XMLChar.isInvalid(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isInvalidLiteral(int value) {
/* 1391 */     return XMLChar.isInvalid(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isValidNameChar(int value) {
/* 1398 */     return XMLChar.isName(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isValidNCName(int value) {
/* 1405 */     return XMLChar.isNCName(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isValidNameStartChar(int value) {
/* 1412 */     return XMLChar.isNameStart(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isValidNameStartHighSurrogate(int value) {
/* 1420 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean versionSupported(String version) {
/* 1424 */     return (version.equals("1.0") || version.equals("1.1"));
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
/*      */   protected boolean scanSurrogates(XMLStringBuffer buf) throws IOException, XNIException {
/* 1439 */     int high = this.fEntityScanner.scanChar(null);
/* 1440 */     int low = this.fEntityScanner.peekChar();
/* 1441 */     if (!XMLChar.isLowSurrogate(low)) {
/* 1442 */       reportFatalError("InvalidCharInContent", new Object[] {
/* 1443 */             Integer.toString(high, 16) });
/* 1444 */       return false;
/*      */     } 
/* 1446 */     this.fEntityScanner.scanChar(null);
/*      */ 
/*      */     
/* 1449 */     int c = XMLChar.supplemental((char)high, (char)low);
/*      */ 
/*      */     
/* 1452 */     if (isInvalid(c)) {
/* 1453 */       reportFatalError("InvalidCharInContent", new Object[] {
/* 1454 */             Integer.toString(c, 16) });
/* 1455 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1459 */     buf.append((char)high);
/* 1460 */     buf.append((char)low);
/*      */     
/* 1462 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reportFatalError(String msgId, Object[] args) throws XNIException {
/* 1472 */     this.fErrorReporter.reportError(this.fEntityScanner, "http://www.w3.org/TR/1998/REC-xml-19980210", msgId, args, (short)2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/* 1480 */     this.fEntityScanner = null;
/*      */     
/* 1482 */     this.fEntityDepth = 0;
/* 1483 */     this.fReportEntity = true;
/* 1484 */     this.fResourceIdentifier.clear();
/*      */     
/* 1486 */     if (!this.fAttributeCacheInitDone) {
/* 1487 */       for (int i = 0; i < this.initialCacheCount; i++) {
/* 1488 */         this.attributeValueCache.add(new XMLString());
/* 1489 */         this.stringBufferCache.add(new XMLStringBuffer());
/*      */       } 
/* 1491 */       this.fAttributeCacheInitDone = true;
/*      */     } 
/* 1493 */     this.fStringBufferIndex = 0;
/* 1494 */     this.fAttributeCacheUsedCount = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   XMLStringBuffer getStringBuffer() {
/* 1499 */     if (this.fStringBufferIndex < this.initialCacheCount || this.fStringBufferIndex < this.stringBufferCache.size()) {
/* 1500 */       return this.stringBufferCache.get(this.fStringBufferIndex++);
/*      */     }
/* 1502 */     XMLStringBuffer tmpObj = new XMLStringBuffer();
/* 1503 */     this.fStringBufferIndex++;
/* 1504 */     this.stringBufferCache.add(tmpObj);
/* 1505 */     return tmpObj;
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
/*      */   void checkEntityLimit(boolean isPEDecl, String entityName, XMLString buffer) {
/* 1517 */     checkEntityLimit(isPEDecl, entityName, buffer.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkEntityLimit(boolean isPEDecl, String entityName, int len) {
/* 1527 */     if (this.fLimitAnalyzer == null) {
/* 1528 */       this.fLimitAnalyzer = this.fEntityManager.fLimitAnalyzer;
/*      */     }
/* 1530 */     if (isPEDecl) {
/* 1531 */       this.fLimitAnalyzer.addValue(XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT, "%" + entityName, len);
/* 1532 */       if (this.fSecurityManager.isOverLimit(XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT, this.fLimitAnalyzer)) {
/* 1533 */         this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/* 1534 */         reportFatalError("MaxEntitySizeLimit", new Object[] { "%" + entityName, 
/* 1535 */               Integer.valueOf(this.fLimitAnalyzer.getValue(XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT)), 
/* 1536 */               Integer.valueOf(this.fSecurityManager.getLimit(XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT)), this.fSecurityManager
/* 1537 */               .getStateLiteral(XMLSecurityManager.Limit.PARAMETER_ENTITY_SIZE_LIMIT) });
/*      */       } 
/*      */     } else {
/* 1540 */       this.fLimitAnalyzer.addValue(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT, entityName, len);
/* 1541 */       if (this.fSecurityManager.isOverLimit(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT, this.fLimitAnalyzer)) {
/* 1542 */         this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/* 1543 */         reportFatalError("MaxEntitySizeLimit", new Object[] { entityName, 
/* 1544 */               Integer.valueOf(this.fLimitAnalyzer.getValue(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT)), 
/* 1545 */               Integer.valueOf(this.fSecurityManager.getLimit(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT)), this.fSecurityManager
/* 1546 */               .getStateLiteral(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT) });
/*      */       } 
/*      */     } 
/* 1549 */     if (this.fSecurityManager.isOverLimit(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT, this.fLimitAnalyzer)) {
/* 1550 */       this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/* 1551 */       reportFatalError("TotalEntitySizeLimit", new Object[] {
/* 1552 */             Integer.valueOf(this.fLimitAnalyzer.getTotalValue(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT)), 
/* 1553 */             Integer.valueOf(this.fSecurityManager.getLimit(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT)), this.fSecurityManager
/* 1554 */             .getStateLiteral(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT)
/*      */           });
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */