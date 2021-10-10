/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLAttributesImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.xml.internal.stream.Entity;
/*      */ import com.sun.xml.internal.stream.StaxErrorReporter;
/*      */ import com.sun.xml.internal.stream.XMLEntityStorage;
/*      */ import com.sun.xml.internal.stream.dtd.nonvalidating.DTDGrammar;
/*      */ import com.sun.xml.internal.stream.dtd.nonvalidating.XMLNotationDecl;
/*      */ import com.sun.xml.internal.stream.events.EntityDeclarationImpl;
/*      */ import com.sun.xml.internal.stream.events.NotationDeclarationImpl;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.NoSuchElementException;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLStreamReaderImpl
/*      */   implements XMLStreamReader
/*      */ {
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String READER_IN_DEFINED_STATE = "http://java.sun.com/xml/stream/properties/reader-in-defined-state";
/*   86 */   private SymbolTable fSymbolTable = new SymbolTable();
/*      */ 
/*      */   
/*   89 */   protected XMLDocumentScannerImpl fScanner = new XMLNSDocumentScannerImpl();
/*      */ 
/*      */ 
/*      */   
/*   93 */   protected NamespaceContextWrapper fNamespaceContextWrapper = new NamespaceContextWrapper((NamespaceSupport)this.fScanner.getNamespaceContext());
/*   94 */   protected XMLEntityManager fEntityManager = new XMLEntityManager();
/*   95 */   protected StaxErrorReporter fErrorReporter = new StaxErrorReporter();
/*      */ 
/*      */ 
/*      */   
/*   99 */   protected XMLEntityScanner fEntityScanner = null;
/*      */ 
/*      */   
/*  102 */   protected XMLInputSource fInputSource = null;
/*      */   
/*  104 */   protected PropertyManager fPropertyManager = null;
/*      */   
/*      */   private int fEventType;
/*      */   
/*      */   static final boolean DEBUG = false;
/*      */   
/*      */   private boolean fReuse = true;
/*      */   
/*      */   private boolean fReaderInDefinedState = true;
/*      */   private boolean fBindNamespaces = true;
/*  114 */   private String fDTDDecl = null;
/*  115 */   private String versionStr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReaderImpl(InputStream inputStream, PropertyManager props) throws XMLStreamException {
/*  123 */     init(props);
/*      */     
/*  125 */     XMLInputSource inputSource = new XMLInputSource(null, null, null, inputStream, null);
/*      */     
/*  127 */     setInputSource(inputSource);
/*      */   }
/*      */   
/*      */   public XMLDocumentScannerImpl getScanner() {
/*  131 */     System.out.println("returning scanner");
/*  132 */     return this.fScanner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReaderImpl(String systemid, PropertyManager props) throws XMLStreamException {
/*  140 */     init(props);
/*      */     
/*  142 */     XMLInputSource inputSource = new XMLInputSource(null, systemid, null);
/*      */     
/*  144 */     setInputSource(inputSource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReaderImpl(InputStream inputStream, String encoding, PropertyManager props) throws XMLStreamException {
/*  155 */     init(props);
/*      */     
/*  157 */     XMLInputSource inputSource = new XMLInputSource(null, null, null, new BufferedInputStream(inputStream), encoding);
/*      */     
/*  159 */     setInputSource(inputSource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReaderImpl(Reader reader, PropertyManager props) throws XMLStreamException {
/*  168 */     init(props);
/*      */ 
/*      */     
/*  171 */     XMLInputSource inputSource = new XMLInputSource(null, null, null, new BufferedReader(reader), null);
/*      */     
/*  173 */     setInputSource(inputSource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReaderImpl(XMLInputSource inputSource, PropertyManager props) throws XMLStreamException {
/*  182 */     init(props);
/*      */     
/*  184 */     setInputSource(inputSource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInputSource(XMLInputSource inputSource) throws XMLStreamException {
/*  195 */     this.fReuse = false;
/*      */ 
/*      */     
/*      */     try {
/*  199 */       this.fScanner.setInputSource(inputSource);
/*      */       
/*  201 */       if (this.fReaderInDefinedState) {
/*  202 */         this.fEventType = this.fScanner.next();
/*  203 */         if (this.versionStr == null) {
/*  204 */           this.versionStr = getVersion();
/*      */         }
/*  206 */         if (this.fEventType == 7 && this.versionStr != null && this.versionStr.equals("1.1")) {
/*  207 */           switchToXML11Scanner();
/*      */         }
/*      */       }
/*      */     
/*  211 */     } catch (IOException ex) {
/*  212 */       throw new XMLStreamException(ex);
/*  213 */     } catch (XNIException ex) {
/*  214 */       throw new XMLStreamException(ex.getMessage(), getLocation(), ex.getException());
/*      */     } 
/*      */   }
/*      */   
/*      */   void init(PropertyManager propertyManager) throws XMLStreamException {
/*  219 */     this.fPropertyManager = propertyManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     propertyManager.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
/*      */     
/*  228 */     propertyManager.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
/*      */     
/*  230 */     propertyManager.setProperty("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
/*      */     
/*  232 */     reset();
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
/*      */   public boolean canReuse() {
/*  246 */     return this.fReuse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  253 */     this.fReuse = true;
/*  254 */     this.fEventType = 0;
/*      */     
/*  256 */     this.fEntityManager.reset(this.fPropertyManager);
/*      */     
/*  258 */     this.fScanner.reset(this.fPropertyManager);
/*      */ 
/*      */     
/*  261 */     this.fDTDDecl = null;
/*  262 */     this.fEntityScanner = this.fEntityManager.getEntityScanner();
/*      */ 
/*      */     
/*  265 */     this.fReaderInDefinedState = ((Boolean)this.fPropertyManager.getProperty("http://java.sun.com/xml/stream/properties/reader-in-defined-state")).booleanValue();
/*  266 */     this.fBindNamespaces = ((Boolean)this.fPropertyManager.getProperty("javax.xml.stream.isNamespaceAware")).booleanValue();
/*  267 */     this.versionStr = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws XMLStreamException {
/*  277 */     this.fReuse = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCharacterEncodingScheme() {
/*  285 */     return this.fScanner.getCharacterEncodingScheme();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnNumber() {
/*  294 */     return this.fEntityScanner.getColumnNumber();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/*  301 */     return this.fEntityScanner.getEncoding();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEventType() {
/*  308 */     return this.fEventType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLineNumber() {
/*  315 */     return this.fEntityScanner.getLineNumber();
/*      */   }
/*      */   
/*      */   public String getLocalName() {
/*  319 */     if (this.fEventType == 1 || this.fEventType == 2)
/*      */     {
/*  321 */       return (this.fScanner.getElementQName()).localpart;
/*      */     }
/*  323 */     if (this.fEventType == 9) {
/*  324 */       return this.fScanner.getEntityName();
/*      */     }
/*  326 */     throw new IllegalStateException("Method getLocalName() cannot be called for " + 
/*  327 */         getEventTypeString(this.fEventType) + " event.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceURI() {
/*  335 */     if (this.fEventType == 1 || this.fEventType == 2) {
/*  336 */       return (this.fScanner.getElementQName()).uri;
/*      */     }
/*  338 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPIData() {
/*  346 */     if (this.fEventType == 3) {
/*  347 */       return this.fScanner.getPIData().toString();
/*      */     }
/*  349 */     throw new IllegalStateException("Current state of the parser is " + getEventTypeString(this.fEventType) + " But Expected state is " + '\003');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPITarget() {
/*  358 */     if (this.fEventType == 3) {
/*  359 */       return this.fScanner.getPITarget();
/*      */     }
/*  361 */     throw new IllegalStateException("Current state of the parser is " + getEventTypeString(this.fEventType) + " But Expected state is " + '\003');
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
/*      */   public String getPrefix() {
/*  373 */     if (this.fEventType == 1 || this.fEventType == 2) {
/*  374 */       String prefix = (this.fScanner.getElementQName()).prefix;
/*  375 */       return (prefix == null) ? "" : prefix;
/*      */     } 
/*  377 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getTextCharacters() {
/*  386 */     if (this.fEventType == 4 || this.fEventType == 5 || this.fEventType == 12 || this.fEventType == 6)
/*      */     {
/*  388 */       return (this.fScanner.getCharacterData()).ch;
/*      */     }
/*  390 */     throw new IllegalStateException("Current state = " + getEventTypeString(this.fEventType) + " is not among the states " + 
/*  391 */         getEventTypeString(4) + " , " + 
/*  392 */         getEventTypeString(5) + " , " + getEventTypeString(12) + " , " + 
/*  393 */         getEventTypeString(6) + " valid for getTextCharacters() ");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextLength() {
/*  401 */     if (this.fEventType == 4 || this.fEventType == 5 || this.fEventType == 12 || this.fEventType == 6)
/*      */     {
/*  403 */       return (this.fScanner.getCharacterData()).length;
/*      */     }
/*  405 */     throw new IllegalStateException("Current state = " + getEventTypeString(this.fEventType) + " is not among the states " + 
/*  406 */         getEventTypeString(4) + " , " + 
/*  407 */         getEventTypeString(5) + " , " + getEventTypeString(12) + " , " + 
/*  408 */         getEventTypeString(6) + " valid for getTextLength() ");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextStart() {
/*  417 */     if (this.fEventType == 4 || this.fEventType == 5 || this.fEventType == 12 || this.fEventType == 6) {
/*  418 */       return (this.fScanner.getCharacterData()).offset;
/*      */     }
/*  420 */     throw new IllegalStateException("Current state = " + getEventTypeString(this.fEventType) + " is not among the states " + 
/*  421 */         getEventTypeString(4) + " , " + 
/*  422 */         getEventTypeString(5) + " , " + getEventTypeString(12) + " , " + 
/*  423 */         getEventTypeString(6) + " valid for getTextStart() ");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  431 */     if (this.fEventType == 3)
/*  432 */       return this.fScanner.getPIData().toString(); 
/*  433 */     if (this.fEventType == 5)
/*  434 */       return this.fScanner.getComment(); 
/*  435 */     if (this.fEventType == 1 || this.fEventType == 2)
/*  436 */       return (this.fScanner.getElementQName()).localpart; 
/*  437 */     if (this.fEventType == 4) {
/*  438 */       return this.fScanner.getCharacterData().toString();
/*      */     }
/*  440 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  449 */     String version = this.fEntityScanner.getXMLVersion();
/*      */     
/*  451 */     return ("1.0".equals(version) && !this.fEntityScanner.xmlVersionSetExplicitly) ? null : version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAttributes() {
/*  458 */     return (this.fScanner.getAttributeIterator().getLength() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasName() {
/*  463 */     if (this.fEventType == 1 || this.fEventType == 2) {
/*  464 */       return true;
/*      */     }
/*  466 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasNext() throws XMLStreamException {
/*  476 */     if (this.fEventType == -1) return false;
/*      */ 
/*      */     
/*  479 */     return (this.fEventType != 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasValue() {
/*  486 */     if (this.fEventType == 1 || this.fEventType == 2 || this.fEventType == 9 || this.fEventType == 3 || this.fEventType == 5 || this.fEventType == 4)
/*      */     {
/*      */       
/*  489 */       return true;
/*      */     }
/*  491 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEndElement() {
/*  500 */     return (this.fEventType == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStandalone() {
/*  507 */     return this.fScanner.isStandAlone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStartElement() {
/*  514 */     return (this.fEventType == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWhiteSpace() {
/*  524 */     if (isCharacters() || this.fEventType == 12) {
/*  525 */       char[] ch = getTextCharacters();
/*  526 */       int start = getTextStart();
/*  527 */       int end = start + getTextLength();
/*  528 */       for (int i = start; i < end; i++) {
/*  529 */         if (!XMLChar.isSpace(ch[i])) {
/*  530 */           return false;
/*      */         }
/*      */       } 
/*  533 */       return true;
/*      */     } 
/*  535 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int next() throws XMLStreamException {
/*  545 */     if (!hasNext()) {
/*  546 */       if (this.fEventType != -1) {
/*  547 */         throw new NoSuchElementException("END_DOCUMENT reached: no more elements on the stream.");
/*      */       }
/*  549 */       throw new XMLStreamException("Error processing input source. The input stream is not complete.");
/*      */     } 
/*      */     
/*      */     try {
/*  553 */       this.fEventType = this.fScanner.next();
/*      */       
/*  555 */       if (this.versionStr == null) {
/*  556 */         this.versionStr = getVersion();
/*      */       }
/*      */       
/*  559 */       if (this.fEventType == 7 && this.versionStr != null && this.versionStr
/*      */         
/*  561 */         .equals("1.1")) {
/*  562 */         switchToXML11Scanner();
/*      */       }
/*      */       
/*  565 */       if (this.fEventType == 4 || this.fEventType == 9 || this.fEventType == 3 || this.fEventType == 5 || this.fEventType == 12)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  570 */         this.fEntityScanner.checkNodeCount(this.fEntityScanner.fCurrentEntity);
/*      */       }
/*      */       
/*  573 */       return this.fEventType;
/*  574 */     } catch (IOException ex) {
/*      */ 
/*      */       
/*  577 */       if (this.fScanner.fScannerState == 46) {
/*  578 */         Boolean isValidating = (Boolean)this.fPropertyManager.getProperty("javax.xml.stream.isValidating");
/*      */         
/*  580 */         if (isValidating != null && 
/*  581 */           !isValidating.booleanValue()) {
/*      */           
/*  583 */           this.fEventType = 11;
/*  584 */           this.fScanner.setScannerState(43);
/*  585 */           this.fScanner.setDriver(this.fScanner.fPrologDriver);
/*  586 */           if (this.fDTDDecl == null || this.fDTDDecl
/*  587 */             .length() == 0) {
/*  588 */             this.fDTDDecl = "<!-- Exception scanning External DTD Subset.  True contents of DTD cannot be determined.  Processing will continue as XMLInputFactory.IS_VALIDATING == false. -->";
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  594 */           return 11;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  599 */       throw new XMLStreamException(ex.getMessage(), getLocation(), ex);
/*  600 */     } catch (XNIException ex) {
/*  601 */       throw new XMLStreamException(ex
/*  602 */           .getMessage(), 
/*  603 */           getLocation(), ex
/*  604 */           .getException());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void switchToXML11Scanner() throws IOException {
/*  610 */     int oldEntityDepth = this.fScanner.fEntityDepth;
/*  611 */     NamespaceContext oldNamespaceContext = this.fScanner.fNamespaceContext;
/*      */     
/*  613 */     this.fScanner = new XML11NSDocumentScannerImpl();
/*      */ 
/*      */     
/*  616 */     this.fScanner.reset(this.fPropertyManager);
/*  617 */     this.fScanner.setPropertyManager(this.fPropertyManager);
/*  618 */     this.fEntityScanner = this.fEntityManager.getEntityScanner();
/*  619 */     this.fEntityManager.fCurrentEntity.mayReadChunks = true;
/*  620 */     this.fScanner.setScannerState(7);
/*      */     
/*  622 */     this.fScanner.fEntityDepth = oldEntityDepth;
/*  623 */     this.fScanner.fNamespaceContext = oldNamespaceContext;
/*  624 */     this.fEventType = this.fScanner.next();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final String getEventTypeString(int eventType) {
/*  630 */     switch (eventType) {
/*      */       case 1:
/*  632 */         return "START_ELEMENT";
/*      */       case 2:
/*  634 */         return "END_ELEMENT";
/*      */       case 3:
/*  636 */         return "PROCESSING_INSTRUCTION";
/*      */       case 4:
/*  638 */         return "CHARACTERS";
/*      */       case 5:
/*  640 */         return "COMMENT";
/*      */       case 7:
/*  642 */         return "START_DOCUMENT";
/*      */       case 8:
/*  644 */         return "END_DOCUMENT";
/*      */       case 9:
/*  646 */         return "ENTITY_REFERENCE";
/*      */       case 10:
/*  648 */         return "ATTRIBUTE";
/*      */       case 11:
/*  650 */         return "DTD";
/*      */       case 12:
/*  652 */         return "CDATA";
/*      */       case 6:
/*  654 */         return "SPACE";
/*      */     } 
/*  656 */     return "UNKNOWN_EVENT_TYPE, " + String.valueOf(eventType);
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
/*      */   public int getAttributeCount() {
/*  671 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  672 */       return this.fScanner.getAttributeIterator().getLength();
/*      */     }
/*  674 */     throw new IllegalStateException("Current state is not among the states " + 
/*  675 */         getEventTypeString(1) + " , " + 
/*  676 */         getEventTypeString(10) + "valid for getAttributeCount()");
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
/*      */   public QName getAttributeName(int index) {
/*  689 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  690 */       return convertXNIQNametoJavaxQName(this.fScanner.getAttributeIterator().getQualifiedName(index));
/*      */     }
/*  692 */     throw new IllegalStateException("Current state is not among the states " + 
/*  693 */         getEventTypeString(1) + " , " + 
/*  694 */         getEventTypeString(10) + "valid for getAttributeName()");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeLocalName(int index) {
/*  705 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  706 */       return this.fScanner.getAttributeIterator().getLocalName(index);
/*      */     }
/*  708 */     throw new IllegalStateException();
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
/*      */   public String getAttributeNamespace(int index) {
/*  720 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  721 */       return this.fScanner.getAttributeIterator().getURI(index);
/*      */     }
/*  723 */     throw new IllegalStateException("Current state is not among the states " + 
/*  724 */         getEventTypeString(1) + " , " + 
/*  725 */         getEventTypeString(10) + "valid for getAttributeNamespace()");
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
/*      */   public String getAttributePrefix(int index) {
/*  739 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  740 */       return this.fScanner.getAttributeIterator().getPrefix(index);
/*      */     }
/*  742 */     throw new IllegalStateException("Current state is not among the states " + 
/*  743 */         getEventTypeString(1) + " , " + 
/*  744 */         getEventTypeString(10) + "valid for getAttributePrefix()");
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
/*      */   public QName getAttributeQName(int index) {
/*  757 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*      */       
/*  759 */       String localName = this.fScanner.getAttributeIterator().getLocalName(index);
/*  760 */       String uri = this.fScanner.getAttributeIterator().getURI(index);
/*  761 */       return new QName(uri, localName);
/*      */     } 
/*  763 */     throw new IllegalStateException("Current state is not among the states " + 
/*  764 */         getEventTypeString(1) + " , " + 
/*  765 */         getEventTypeString(10) + "valid for getAttributeQName()");
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
/*      */   public String getAttributeType(int index) {
/*  778 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  779 */       return this.fScanner.getAttributeIterator().getType(index);
/*      */     }
/*  781 */     throw new IllegalStateException("Current state is not among the states " + 
/*  782 */         getEventTypeString(1) + " , " + 
/*  783 */         getEventTypeString(10) + "valid for getAttributeType()");
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
/*      */   public String getAttributeValue(int index) {
/*  797 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  798 */       return this.fScanner.getAttributeIterator().getValue(index);
/*      */     }
/*  800 */     throw new IllegalStateException("Current state is not among the states " + 
/*  801 */         getEventTypeString(1) + " , " + 
/*  802 */         getEventTypeString(10) + "valid for getAttributeValue()");
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
/*      */   public String getAttributeValue(String namespaceURI, String localName) {
/*  815 */     if (this.fEventType == 1 || this.fEventType == 10) {
/*  816 */       XMLAttributesImpl attributes = this.fScanner.getAttributeIterator();
/*  817 */       if (namespaceURI == null) {
/*  818 */         return attributes.getValue(attributes.getIndexByLocalName(localName));
/*      */       }
/*  820 */       return this.fScanner.getAttributeIterator().getValue(
/*  821 */           (namespaceURI.length() == 0) ? null : namespaceURI, localName);
/*      */     } 
/*      */ 
/*      */     
/*  825 */     throw new IllegalStateException("Current state is not among the states " + 
/*  826 */         getEventTypeString(1) + " , " + 
/*  827 */         getEventTypeString(10) + "valid for getAttributeValue()");
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
/*      */   public String getElementText() throws XMLStreamException {
/*  841 */     if (getEventType() != 1) {
/*  842 */       throw new XMLStreamException("parser must be on START_ELEMENT to read next text", 
/*  843 */           getLocation());
/*      */     }
/*  845 */     int eventType = next();
/*  846 */     StringBuffer content = new StringBuffer();
/*  847 */     while (eventType != 2) {
/*  848 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*      */ 
/*      */ 
/*      */         
/*  852 */         content.append(getText());
/*  853 */       } else if (eventType != 3 && eventType != 5) {
/*      */ 
/*      */         
/*  856 */         if (eventType == 8)
/*  857 */           throw new XMLStreamException("unexpected end of document when reading element text content"); 
/*  858 */         if (eventType == 1) {
/*  859 */           throw new XMLStreamException("elementGetText() function expects text only elment but START_ELEMENT was encountered.", 
/*  860 */               getLocation());
/*      */         }
/*  862 */         throw new XMLStreamException("Unexpected event type " + eventType, 
/*  863 */             getLocation());
/*      */       } 
/*  865 */       eventType = next();
/*      */     } 
/*  867 */     return content.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Location getLocation() {
/*  878 */     return new Location() {
/*  879 */         String _systemId = XMLStreamReaderImpl.this.fEntityScanner.getExpandedSystemId();
/*  880 */         String _publicId = XMLStreamReaderImpl.this.fEntityScanner.getPublicId();
/*  881 */         int _offset = XMLStreamReaderImpl.this.fEntityScanner.getCharacterOffset();
/*  882 */         int _columnNumber = XMLStreamReaderImpl.this.fEntityScanner.getColumnNumber();
/*  883 */         int _lineNumber = XMLStreamReaderImpl.this.fEntityScanner.getLineNumber();
/*      */         public String getLocationURI() {
/*  885 */           return this._systemId;
/*      */         }
/*      */         
/*      */         public int getCharacterOffset() {
/*  889 */           return this._offset;
/*      */         }
/*      */         
/*      */         public int getColumnNumber() {
/*  893 */           return this._columnNumber;
/*      */         }
/*      */         
/*      */         public int getLineNumber() {
/*  897 */           return this._lineNumber;
/*      */         }
/*      */         
/*      */         public String getPublicId() {
/*  901 */           return this._publicId;
/*      */         }
/*      */         
/*      */         public String getSystemId() {
/*  905 */           return this._systemId;
/*      */         }
/*      */         
/*      */         public String toString() {
/*  909 */           StringBuffer sbuffer = new StringBuffer();
/*  910 */           sbuffer.append("Line number = " + getLineNumber());
/*  911 */           sbuffer.append("\n");
/*  912 */           sbuffer.append("Column number = " + getColumnNumber());
/*  913 */           sbuffer.append("\n");
/*  914 */           sbuffer.append("System Id = " + getSystemId());
/*  915 */           sbuffer.append("\n");
/*  916 */           sbuffer.append("Public Id = " + getPublicId());
/*  917 */           sbuffer.append("\n");
/*  918 */           sbuffer.append("Location Uri= " + getLocationURI());
/*  919 */           sbuffer.append("\n");
/*  920 */           sbuffer.append("CharacterOffset = " + getCharacterOffset());
/*  921 */           sbuffer.append("\n");
/*  922 */           return sbuffer.toString();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getName() {
/*  932 */     if (this.fEventType == 1 || this.fEventType == 2) {
/*  933 */       return convertXNIQNametoJavaxQName(this.fScanner.getElementQName());
/*      */     }
/*  935 */     throw new IllegalStateException("Illegal to call getName() when event type is " + 
/*  936 */         getEventTypeString(this.fEventType) + ". Valid states are " + 
/*  937 */         getEventTypeString(1) + ", " + 
/*  938 */         getEventTypeString(2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamespaceContext getNamespaceContext() {
/*  947 */     return this.fNamespaceContextWrapper;
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
/*      */   public int getNamespaceCount() {
/*  961 */     if (this.fEventType == 1 || this.fEventType == 2 || this.fEventType == 13) {
/*  962 */       return this.fScanner.getNamespaceContext().getDeclaredPrefixCount();
/*      */     }
/*  964 */     throw new IllegalStateException("Current event state is " + getEventTypeString(this.fEventType) + " is not among the states " + 
/*  965 */         getEventTypeString(1) + ", " + 
/*  966 */         getEventTypeString(2) + ", " + 
/*  967 */         getEventTypeString(13) + " valid for getNamespaceCount().");
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
/*      */   public String getNamespacePrefix(int index) {
/*  981 */     if (this.fEventType == 1 || this.fEventType == 2 || this.fEventType == 13) {
/*      */       
/*  983 */       String prefix = this.fScanner.getNamespaceContext().getDeclaredPrefixAt(index);
/*  984 */       return prefix.equals("") ? null : prefix;
/*      */     } 
/*      */     
/*  987 */     throw new IllegalStateException("Current state " + getEventTypeString(this.fEventType) + " is not among the states " + 
/*  988 */         getEventTypeString(1) + ", " + 
/*  989 */         getEventTypeString(2) + ", " + 
/*  990 */         getEventTypeString(13) + " valid for getNamespacePrefix().");
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
/*      */   public String getNamespaceURI(int index) {
/* 1003 */     if (this.fEventType == 1 || this.fEventType == 2 || this.fEventType == 13)
/*      */     {
/* 1005 */       return this.fScanner.getNamespaceContext().getURI(this.fScanner.getNamespaceContext().getDeclaredPrefixAt(index));
/*      */     }
/*      */     
/* 1008 */     throw new IllegalStateException("Current state " + getEventTypeString(this.fEventType) + " is not among the states " + 
/* 1009 */         getEventTypeString(1) + ", " + 
/* 1010 */         getEventTypeString(2) + ", " + 
/* 1011 */         getEventTypeString(13) + " valid for getNamespaceURI().");
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
/*      */   public Object getProperty(String name) throws IllegalArgumentException {
/* 1023 */     if (name == null) throw new IllegalArgumentException(); 
/* 1024 */     if (this.fPropertyManager != null) {
/* 1025 */       if (name.equals("javax.xml.stream.notations"))
/* 1026 */         return getNotationDecls(); 
/* 1027 */       if (name.equals("javax.xml.stream.entities")) {
/* 1028 */         return getEntityDecls();
/*      */       }
/* 1030 */       return this.fPropertyManager.getProperty(name);
/*      */     } 
/* 1032 */     return null;
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
/*      */   public String getText() {
/* 1045 */     if (this.fEventType == 4 || this.fEventType == 5 || this.fEventType == 12 || this.fEventType == 6)
/*      */     {
/*      */ 
/*      */       
/* 1049 */       return this.fScanner.getCharacterData().toString(); } 
/* 1050 */     if (this.fEventType == 9) {
/* 1051 */       String name = this.fScanner.getEntityName();
/* 1052 */       if (name != null) {
/* 1053 */         if (this.fScanner.foundBuiltInRefs) {
/* 1054 */           return this.fScanner.getCharacterData().toString();
/*      */         }
/* 1056 */         XMLEntityStorage entityStore = this.fEntityManager.getEntityStore();
/* 1057 */         Entity en = entityStore.getEntity(name);
/* 1058 */         if (en == null)
/* 1059 */           return null; 
/* 1060 */         if (en.isExternal()) {
/* 1061 */           return ((Entity.ExternalEntity)en).entityLocation.getExpandedSystemId();
/*      */         }
/* 1063 */         return ((Entity.InternalEntity)en).text;
/*      */       } 
/* 1065 */       return null;
/*      */     } 
/* 1067 */     if (this.fEventType == 11) {
/* 1068 */       if (this.fDTDDecl != null) {
/* 1069 */         return this.fDTDDecl;
/*      */       }
/* 1071 */       XMLStringBuffer tmpBuffer = this.fScanner.getDTDDecl();
/* 1072 */       this.fDTDDecl = tmpBuffer.toString();
/* 1073 */       return this.fDTDDecl;
/*      */     } 
/* 1075 */     throw new IllegalStateException("Current state " + getEventTypeString(this.fEventType) + " is not among the states" + 
/* 1076 */         getEventTypeString(4) + ", " + 
/* 1077 */         getEventTypeString(5) + ", " + 
/* 1078 */         getEventTypeString(12) + ", " + 
/* 1079 */         getEventTypeString(6) + ", " + 
/* 1080 */         getEventTypeString(9) + ", " + 
/* 1081 */         getEventTypeString(11) + " valid for getText() ");
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
/*      */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/* 1094 */     if (type != this.fEventType)
/* 1095 */       throw new XMLStreamException("Event type " + getEventTypeString(type) + " specified did not match with current parser event " + 
/* 1096 */           getEventTypeString(this.fEventType)); 
/* 1097 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI())) {
/* 1098 */       throw new XMLStreamException("Namespace URI " + namespaceURI + " specified did not match with current namespace URI");
/*      */     }
/* 1100 */     if (localName != null && !localName.equals(getLocalName())) {
/* 1101 */       throw new XMLStreamException("LocalName " + localName + " specified did not match with current local name");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 1144 */     if (target == null) {
/* 1145 */       throw new NullPointerException("target char array can't be null");
/*      */     }
/*      */     
/* 1148 */     if (targetStart < 0 || length < 0 || sourceStart < 0 || targetStart >= target.length || targetStart + length > target.length)
/*      */     {
/* 1150 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1155 */     int copiedLength = 0;
/*      */     
/* 1157 */     int available = getTextLength() - sourceStart;
/* 1158 */     if (available < 0) {
/* 1159 */       throw new IndexOutOfBoundsException("sourceStart is greater thannumber of characters associated with this event");
/*      */     }
/*      */     
/* 1162 */     if (available < length) {
/* 1163 */       copiedLength = available;
/*      */     } else {
/* 1165 */       copiedLength = length;
/*      */     } 
/*      */     
/* 1168 */     System.arraycopy(getTextCharacters(), getTextStart() + sourceStart, target, targetStart, copiedLength);
/* 1169 */     return copiedLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasText() {
/* 1178 */     if (this.fEventType == 4 || this.fEventType == 5 || this.fEventType == 12)
/* 1179 */       return ((this.fScanner.getCharacterData()).length > 0); 
/* 1180 */     if (this.fEventType == 9) {
/* 1181 */       String name = this.fScanner.getEntityName();
/* 1182 */       if (name != null) {
/* 1183 */         if (this.fScanner.foundBuiltInRefs) {
/* 1184 */           return true;
/*      */         }
/* 1186 */         XMLEntityStorage entityStore = this.fEntityManager.getEntityStore();
/* 1187 */         Entity en = entityStore.getEntity(name);
/* 1188 */         if (en == null)
/* 1189 */           return false; 
/* 1190 */         if (en.isExternal()) {
/* 1191 */           return (((Entity.ExternalEntity)en).entityLocation.getExpandedSystemId() != null);
/*      */         }
/* 1193 */         return (((Entity.InternalEntity)en).text != null);
/*      */       } 
/*      */       
/* 1196 */       return false;
/*      */     } 
/* 1198 */     if (this.fEventType == 11) {
/* 1199 */       return this.fScanner.fSeenDoctypeDecl;
/*      */     }
/* 1201 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeSpecified(int index) {
/* 1212 */     if (this.fEventType == 1 || this.fEventType == 10) {
/* 1213 */       return this.fScanner.getAttributeIterator().isSpecified(index);
/*      */     }
/* 1215 */     throw new IllegalStateException("Current state is not among the states " + 
/* 1216 */         getEventTypeString(1) + " , " + 
/* 1217 */         getEventTypeString(10) + "valid for isAttributeSpecified()");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCharacters() {
/* 1226 */     return (this.fEventType == 4);
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
/*      */   public int nextTag() throws XMLStreamException {
/* 1241 */     int eventType = next();
/* 1242 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && 
/* 1243 */       isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1249 */       eventType = next();
/*      */     }
/*      */     
/* 1252 */     if (eventType != 1 && eventType != 2) {
/* 1253 */       throw new XMLStreamException("found: " + 
/* 1254 */           getEventTypeString(eventType) + ", expected " + 
/* 1255 */           getEventTypeString(1) + " or " + 
/* 1256 */           getEventTypeString(2), 
/* 1257 */           getLocation());
/*      */     }
/*      */     
/* 1260 */     return eventType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean standaloneSet() {
/* 1269 */     return this.fScanner.standaloneSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName convertXNIQNametoJavaxQName(QName qname) {
/* 1277 */     if (qname == null) return null;
/*      */     
/* 1279 */     if (qname.prefix == null) {
/* 1280 */       return new QName(qname.uri, qname.localpart);
/*      */     }
/* 1282 */     return new QName(qname.uri, qname.localpart, qname.prefix);
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
/*      */   public String getNamespaceURI(String prefix) {
/* 1300 */     if (prefix == null) throw new IllegalArgumentException("prefix cannot be null.");
/*      */ 
/*      */     
/* 1303 */     return this.fScanner.getNamespaceContext().getURI(this.fSymbolTable.addSymbol(prefix));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setPropertyManager(PropertyManager propertyManager) {
/* 1308 */     this.fPropertyManager = propertyManager;
/*      */     
/* 1310 */     this.fScanner.setProperty("stax-properties", propertyManager);
/* 1311 */     this.fScanner.setPropertyManager(propertyManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyManager getPropertyManager() {
/* 1318 */     return this.fPropertyManager;
/*      */   }
/*      */   
/*      */   static void pr(String str) {
/* 1322 */     System.out.println(str);
/*      */   }
/*      */   
/*      */   protected List getEntityDecls() {
/* 1326 */     if (this.fEventType == 11) {
/* 1327 */       XMLEntityStorage entityStore = this.fEntityManager.getEntityStore();
/* 1328 */       ArrayList<EntityDeclarationImpl> list = null;
/* 1329 */       if (entityStore.hasEntities()) {
/* 1330 */         EntityDeclarationImpl decl = null;
/* 1331 */         list = new ArrayList(entityStore.getEntitySize());
/* 1332 */         Enumeration<String> enu = entityStore.getEntityKeys();
/* 1333 */         while (enu.hasMoreElements()) {
/* 1334 */           String key = enu.nextElement();
/* 1335 */           Entity en = entityStore.getEntity(key);
/* 1336 */           decl = new EntityDeclarationImpl();
/* 1337 */           decl.setEntityName(key);
/* 1338 */           if (en.isExternal()) {
/* 1339 */             decl.setXMLResourceIdentifier(((Entity.ExternalEntity)en).entityLocation);
/* 1340 */             decl.setNotationName(((Entity.ExternalEntity)en).notation);
/*      */           } else {
/*      */             
/* 1343 */             decl.setEntityReplacementText(((Entity.InternalEntity)en).text);
/* 1344 */           }  list.add(decl);
/*      */         } 
/*      */       } 
/* 1347 */       return list;
/*      */     } 
/* 1349 */     return null;
/*      */   }
/*      */   
/*      */   protected List getNotationDecls() {
/* 1353 */     if (this.fEventType == 11) {
/* 1354 */       if (this.fScanner.fDTDScanner == null) return null; 
/* 1355 */       DTDGrammar grammar = ((XMLDTDScannerImpl)this.fScanner.fDTDScanner).getGrammar();
/* 1356 */       if (grammar == null) return null; 
/* 1357 */       List<XMLNotationDecl> notations = grammar.getNotationDecls();
/*      */       
/* 1359 */       Iterator<XMLNotationDecl> it = notations.iterator();
/* 1360 */       ArrayList<NotationDeclarationImpl> list = new ArrayList();
/* 1361 */       while (it.hasNext()) {
/* 1362 */         XMLNotationDecl ni = it.next();
/* 1363 */         if (ni != null) {
/* 1364 */           list.add(new NotationDeclarationImpl(ni));
/*      */         }
/*      */       } 
/* 1367 */       return list;
/*      */     } 
/* 1369 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLStreamReaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */