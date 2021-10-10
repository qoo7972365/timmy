/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
/*      */ import com.sun.org.apache.xerces.internal.impl.io.UCSReader;
/*      */ import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
/*      */ import com.sun.org.apache.xerces.internal.util.EncodingMap;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.xml.internal.stream.Entity;
/*      */ import com.sun.xml.internal.stream.XMLBufferListener;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLEntityScanner
/*      */   implements XMLLocator
/*      */ {
/*   61 */   protected Entity.ScannedEntity fCurrentEntity = null;
/*   62 */   protected int fBufferSize = 8192;
/*      */ 
/*      */   
/*      */   protected XMLEntityManager fEntityManager;
/*      */   
/*   67 */   protected XMLSecurityManager fSecurityManager = null;
/*      */ 
/*      */   
/*   70 */   protected XMLLimitAnalyzer fLimitAnalyzer = null;
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_ENCODINGS = false;
/*      */ 
/*      */   
/*   76 */   private ArrayList<XMLBufferListener> listeners = new ArrayList<>();
/*      */   
/*   78 */   private static final boolean[] VALID_NAMES = new boolean[127];
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_BUFFER = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_SKIP_STRING = false;
/*      */ 
/*      */ 
/*      */   
/*   90 */   private static final EOFException END_OF_DOCUMENT_ENTITY = new EOFException() { private static final long serialVersionUID = 980337771224675268L;
/*      */       
/*      */       public Throwable fillInStackTrace() {
/*   93 */         return this;
/*      */       } }
/*      */   ;
/*      */   
/*   97 */   protected SymbolTable fSymbolTable = null;
/*   98 */   protected XMLErrorReporter fErrorReporter = null;
/*   99 */   int[] whiteSpaceLookup = new int[100];
/*  100 */   int whiteSpaceLen = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean whiteSpaceInfoNeeded = true;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fAllowJavaEncodings;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */ 
/*      */ 
/*      */   
/*  124 */   protected PropertyManager fPropertyManager = null;
/*      */   boolean isExternal = false;
/*      */   
/*      */   static {
/*      */     int i;
/*  129 */     for (i = 65; i <= 90; i++) {
/*  130 */       VALID_NAMES[i] = true;
/*      */     }
/*  132 */     for (i = 97; i <= 122; i++) {
/*  133 */       VALID_NAMES[i] = true;
/*      */     }
/*  135 */     for (i = 48; i <= 57; i++) {
/*  136 */       VALID_NAMES[i] = true;
/*      */     }
/*  138 */     VALID_NAMES[45] = true;
/*  139 */     VALID_NAMES[46] = true;
/*  140 */     VALID_NAMES[58] = true;
/*  141 */     VALID_NAMES[95] = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean xmlVersionSetExplicitly = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean detectingVersion = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLEntityScanner(PropertyManager propertyManager, XMLEntityManager entityManager) {
/*  166 */     this.fEntityManager = entityManager;
/*  167 */     reset(propertyManager);
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
/*      */   public final void setBufferSize(int size) {
/*  183 */     this.fBufferSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager propertyManager) {
/*  190 */     this.fSymbolTable = (SymbolTable)propertyManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*  191 */     this.fErrorReporter = (XMLErrorReporter)propertyManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*  192 */     resetCommon();
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
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/*  212 */     this.fAllowJavaEncodings = componentManager.getFeature("http://apache.org/xml/features/allow-java-encodings", false);
/*      */ 
/*      */     
/*  215 */     this.fSymbolTable = (SymbolTable)componentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*  216 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*  217 */     resetCommon();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void reset(SymbolTable symbolTable, XMLEntityManager entityManager, XMLErrorReporter reporter) {
/*  223 */     this.fCurrentEntity = null;
/*  224 */     this.fSymbolTable = symbolTable;
/*  225 */     this.fEntityManager = entityManager;
/*  226 */     this.fErrorReporter = reporter;
/*  227 */     this.fLimitAnalyzer = this.fEntityManager.fLimitAnalyzer;
/*  228 */     this.fSecurityManager = this.fEntityManager.fSecurityManager;
/*      */   }
/*      */   
/*      */   private void resetCommon() {
/*  232 */     this.fCurrentEntity = null;
/*  233 */     this.whiteSpaceLen = 0;
/*  234 */     this.whiteSpaceInfoNeeded = true;
/*  235 */     this.listeners.clear();
/*  236 */     this.fLimitAnalyzer = this.fEntityManager.fLimitAnalyzer;
/*  237 */     this.fSecurityManager = this.fEntityManager.fSecurityManager;
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
/*      */   public final String getXMLVersion() {
/*  251 */     if (this.fCurrentEntity != null) {
/*  252 */       return this.fCurrentEntity.xmlVersion;
/*      */     }
/*  254 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setXMLVersion(String xmlVersion) {
/*  265 */     this.xmlVersionSetExplicitly = true;
/*  266 */     this.fCurrentEntity.xmlVersion = xmlVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setCurrentEntity(Entity.ScannedEntity scannedEntity) {
/*  275 */     this.fCurrentEntity = scannedEntity;
/*  276 */     if (this.fCurrentEntity != null) {
/*  277 */       this.isExternal = this.fCurrentEntity.isExternal();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity.ScannedEntity getCurrentEntity() {
/*  284 */     return this.fCurrentEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getBaseSystemId() {
/*  295 */     return (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getExpandedSystemId() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSystemId(String systemId) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLineNumber() {
/*  309 */     return (this.fCurrentEntity != null) ? this.fCurrentEntity.lineNumber : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLineNumber(int line) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getColumnNumber() {
/*  323 */     return (this.fCurrentEntity != null) ? this.fCurrentEntity.columnNumber : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnNumber(int col) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getCharacterOffset() {
/*  335 */     return (this.fCurrentEntity != null) ? (this.fCurrentEntity.fTotalCountTillLastLoad + this.fCurrentEntity.position) : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getExpandedSystemId() {
/*  340 */     return (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getExpandedSystemId() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExpandedSystemId(String systemId) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getLiteralSystemId() {
/*  352 */     return (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getLiteralSystemId() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLiteralSystemId(String systemId) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getPublicId() {
/*  364 */     return (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getPublicId() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPublicId(String publicId) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVersion(String version) {
/*  378 */     this.fCurrentEntity.version = version;
/*      */   }
/*      */   
/*      */   public String getVersion() {
/*  382 */     if (this.fCurrentEntity != null)
/*  383 */       return this.fCurrentEntity.version; 
/*  384 */     return null;
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
/*      */   public final String getEncoding() {
/*  396 */     if (this.fCurrentEntity != null) {
/*  397 */       return this.fCurrentEntity.encoding;
/*      */     }
/*  399 */     return null;
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
/*      */   public final void setEncoding(String encoding) throws IOException {
/*  426 */     if (this.fCurrentEntity.stream != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  434 */       if (this.fCurrentEntity.encoding == null || 
/*  435 */         !this.fCurrentEntity.encoding.equals(encoding)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  440 */         if (this.fCurrentEntity.encoding != null && this.fCurrentEntity.encoding.startsWith("UTF-16")) {
/*  441 */           String ENCODING = encoding.toUpperCase(Locale.ENGLISH);
/*  442 */           if (ENCODING.equals("UTF-16"))
/*  443 */             return;  if (ENCODING.equals("ISO-10646-UCS-4")) {
/*  444 */             if (this.fCurrentEntity.encoding.equals("UTF-16BE")) {
/*  445 */               this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)8);
/*      */             } else {
/*  447 */               this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)4);
/*      */             } 
/*      */             return;
/*      */           } 
/*  451 */           if (ENCODING.equals("ISO-10646-UCS-2")) {
/*  452 */             if (this.fCurrentEntity.encoding.equals("UTF-16BE")) {
/*  453 */               this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)2);
/*      */             } else {
/*  455 */               this.fCurrentEntity.reader = new UCSReader(this.fCurrentEntity.stream, (short)1);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  467 */         this.fCurrentEntity.reader = createReader(this.fCurrentEntity.stream, encoding, null);
/*  468 */         this.fCurrentEntity.encoding = encoding;
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
/*      */   public final boolean isExternal() {
/*  480 */     return this.fCurrentEntity.isExternal();
/*      */   }
/*      */   
/*      */   public int getChar(int relative) throws IOException {
/*  484 */     if (arrangeCapacity(relative + 1, false)) {
/*  485 */       return this.fCurrentEntity.ch[this.fCurrentEntity.position + relative];
/*      */     }
/*  487 */     return -1;
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
/*      */   public int peekChar() throws IOException {
/*  507 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  508 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  512 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  524 */     if (this.isExternal) {
/*  525 */       return (c != 13) ? c : 10;
/*      */     }
/*  527 */     return c;
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
/*      */   protected int scanChar(XMLScanner.NameType nt) throws IOException {
/*  550 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  551 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  555 */     int offset = this.fCurrentEntity.position;
/*  556 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  557 */     if (c == 10 || (c == 13 && this.isExternal)) {
/*  558 */       this.fCurrentEntity.lineNumber++;
/*  559 */       this.fCurrentEntity.columnNumber = 1;
/*  560 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  561 */         invokeListeners(1);
/*  562 */         this.fCurrentEntity.ch[0] = (char)c;
/*  563 */         load(1, false, false);
/*  564 */         offset = 0;
/*      */       } 
/*  566 */       if (c == 13 && this.isExternal) {
/*  567 */         if (this.fCurrentEntity.ch[this.fCurrentEntity.position++] != '\n') {
/*  568 */           this.fCurrentEntity.position--;
/*      */         }
/*  570 */         c = 10;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     this.fCurrentEntity.columnNumber++;
/*  581 */     if (!this.detectingVersion) {
/*  582 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/*      */     }
/*  584 */     return c;
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
/*      */   protected String scanNmtoken() throws IOException {
/*  611 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  612 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  616 */     int offset = this.fCurrentEntity.position;
/*  617 */     boolean vc = false;
/*      */ 
/*      */     
/*      */     while (true) {
/*  621 */       char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  622 */       if (c < '') {
/*  623 */         vc = VALID_NAMES[c];
/*      */       } else {
/*  625 */         vc = XMLChar.isName(c);
/*      */       } 
/*  627 */       if (!vc)
/*      */         break; 
/*  629 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  630 */         int i = this.fCurrentEntity.position - offset;
/*  631 */         invokeListeners(i);
/*  632 */         if (i == this.fCurrentEntity.fBufferSize) {
/*      */           
/*  634 */           char[] tmp = new char[this.fCurrentEntity.fBufferSize * 2];
/*  635 */           System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */           
/*  637 */           this.fCurrentEntity.ch = tmp;
/*  638 */           this.fCurrentEntity.fBufferSize *= 2;
/*      */         } else {
/*  640 */           System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */         } 
/*      */         
/*  643 */         offset = 0;
/*  644 */         if (load(i, false, false)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*  649 */     int length = this.fCurrentEntity.position - offset;
/*  650 */     this.fCurrentEntity.columnNumber += length;
/*      */ 
/*      */     
/*  653 */     String symbol = null;
/*  654 */     if (length > 0) {
/*  655 */       symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     return symbol;
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
/*      */   protected String scanName(XMLScanner.NameType nt) throws IOException {
/*      */     String symbol;
/*  692 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  693 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  697 */     int offset = this.fCurrentEntity.position;
/*      */     
/*  699 */     if (XMLChar.isNameStart(this.fCurrentEntity.ch[offset])) {
/*  700 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  701 */         invokeListeners(1);
/*  702 */         this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[offset];
/*  703 */         offset = 0;
/*  704 */         if (load(1, false, false)) {
/*  705 */           this.fCurrentEntity.columnNumber++;
/*  706 */           symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  713 */           return symbol;
/*      */         } 
/*      */       } 
/*  716 */       boolean vc = false;
/*      */       
/*      */       while (true) {
/*  719 */         char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  720 */         if (c < '') {
/*  721 */           vc = VALID_NAMES[c];
/*      */         } else {
/*  723 */           vc = XMLChar.isName(c);
/*      */         } 
/*  725 */         if (!vc)
/*  726 */           break;  int i; if ((i = checkBeforeLoad(this.fCurrentEntity, offset, offset)) > 0) {
/*  727 */           offset = 0;
/*  728 */           if (load(i, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  734 */     int length = this.fCurrentEntity.position - offset;
/*  735 */     this.fCurrentEntity.columnNumber += length;
/*      */ 
/*      */ 
/*      */     
/*  739 */     if (length > 0) {
/*  740 */       checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*  741 */       checkEntityLimit(nt, this.fCurrentEntity, offset, length);
/*  742 */       symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */     } else {
/*  744 */       symbol = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     return symbol;
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
/*      */   protected boolean scanQName(QName qname, XMLScanner.NameType nt) throws IOException {
/*  785 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  786 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  790 */     int offset = this.fCurrentEntity.position;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  796 */     if (XMLChar.isNameStart(this.fCurrentEntity.ch[offset])) {
/*  797 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  798 */         invokeListeners(1);
/*  799 */         this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[offset];
/*  800 */         offset = 0;
/*      */         
/*  802 */         if (load(1, false, false)) {
/*  803 */           this.fCurrentEntity.columnNumber++;
/*      */ 
/*      */           
/*  806 */           String name = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
/*  807 */           qname.setValues(null, name, name, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  813 */           checkEntityLimit(nt, this.fCurrentEntity, 0, 1);
/*  814 */           return true;
/*      */         } 
/*      */       } 
/*  817 */       int index = -1;
/*  818 */       boolean vc = false;
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  823 */         char c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  824 */         if (c < '') {
/*  825 */           vc = VALID_NAMES[c];
/*      */         } else {
/*  827 */           vc = XMLChar.isName(c);
/*      */         } 
/*  829 */         if (!vc)
/*  830 */           break;  if (c == ':') {
/*  831 */           if (index != -1) {
/*      */             break;
/*      */           }
/*  834 */           index = this.fCurrentEntity.position;
/*      */           
/*  836 */           checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, index - offset);
/*      */         }  int i;
/*  838 */         if ((i = checkBeforeLoad(this.fCurrentEntity, offset, index)) > 0) {
/*  839 */           if (index != -1) {
/*  840 */             index -= offset;
/*      */           }
/*  842 */           offset = 0;
/*  843 */           if (load(i, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*  848 */       int length = this.fCurrentEntity.position - offset;
/*  849 */       this.fCurrentEntity.columnNumber += length;
/*  850 */       if (length > 0) {
/*  851 */         String prefix = null;
/*  852 */         String localpart = null;
/*  853 */         String rawname = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */         
/*  856 */         if (index != -1) {
/*  857 */           int prefixLength = index - offset;
/*      */           
/*  859 */           checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, prefixLength);
/*  860 */           prefix = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, prefixLength);
/*      */           
/*  862 */           int len = length - prefixLength - 1;
/*      */           
/*  864 */           checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, index + 1, len);
/*  865 */           localpart = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, index + 1, len);
/*      */         }
/*      */         else {
/*      */           
/*  869 */           localpart = rawname;
/*      */           
/*  871 */           checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*      */         } 
/*  873 */         qname.setValues(prefix, localpart, rawname, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  879 */         checkEntityLimit(nt, this.fCurrentEntity, offset, length);
/*  880 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  890 */     return false;
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
/*      */   protected int checkBeforeLoad(Entity.ScannedEntity entity, int offset, int nameOffset) throws IOException {
/*  906 */     int length = 0;
/*  907 */     if (++entity.position == entity.count) {
/*  908 */       length = entity.position - offset;
/*  909 */       int nameLength = length;
/*  910 */       if (nameOffset != -1) {
/*  911 */         nameOffset -= offset;
/*  912 */         nameLength = length - nameOffset;
/*      */       } else {
/*  914 */         nameOffset = offset;
/*      */       } 
/*      */       
/*  917 */       checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, entity, nameOffset, nameLength);
/*  918 */       invokeListeners(length);
/*  919 */       if (length == entity.ch.length) {
/*      */         
/*  921 */         char[] tmp = new char[entity.fBufferSize * 2];
/*  922 */         System.arraycopy(entity.ch, offset, tmp, 0, length);
/*  923 */         entity.ch = tmp;
/*  924 */         entity.fBufferSize *= 2;
/*      */       } else {
/*      */         
/*  927 */         System.arraycopy(entity.ch, offset, entity.ch, 0, length);
/*      */       } 
/*      */     } 
/*  930 */     return length;
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
/*      */   protected void checkEntityLimit(XMLScanner.NameType nt, Entity.ScannedEntity entity, int offset, int length) {
/*  943 */     if (entity == null || !entity.isGE) {
/*      */       return;
/*      */     }
/*      */     
/*  947 */     if (nt != XMLScanner.NameType.REFERENCE) {
/*  948 */       checkLimit(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT, entity, offset, length);
/*      */     }
/*  950 */     if (nt == XMLScanner.NameType.ELEMENTSTART || nt == XMLScanner.NameType.ATTRIBUTENAME) {
/*  951 */       checkNodeCount(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkNodeCount(Entity.ScannedEntity entity) {
/*  962 */     if (entity != null && entity.isGE) {
/*  963 */       checkLimit(XMLSecurityManager.Limit.ENTITY_REPLACEMENT_LIMIT, entity, 0, 1);
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
/*      */   protected void checkLimit(XMLSecurityManager.Limit limit, Entity.ScannedEntity entity, int offset, int length) {
/*  976 */     this.fLimitAnalyzer.addValue(limit, entity.name, length);
/*  977 */     if (this.fSecurityManager.isOverLimit(limit, this.fLimitAnalyzer)) {
/*  978 */       this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/*  979 */       (new Object[3])[0] = 
/*  980 */         Integer.valueOf(this.fLimitAnalyzer.getValue(limit)); (new Object[3])[1] = 
/*  981 */         Integer.valueOf(this.fSecurityManager.getLimit(limit)); (new Object[3])[2] = this.fSecurityManager.getStateLiteral(limit); (new Object[4])[0] = entity.name; (new Object[4])[1] = 
/*  982 */         Integer.valueOf(this.fLimitAnalyzer.getValue(limit)); (new Object[4])[2] = 
/*  983 */         Integer.valueOf(this.fSecurityManager.getLimit(limit)); (new Object[4])[3] = this.fSecurityManager.getStateLiteral(limit); Object[] e = (limit == XMLSecurityManager.Limit.ENTITY_REPLACEMENT_LIMIT) ? new Object[3] : new Object[4];
/*  984 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", limit.key(), e, (short)2);
/*      */     } 
/*      */     
/*  987 */     if (this.fSecurityManager.isOverLimit(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT, this.fLimitAnalyzer)) {
/*  988 */       this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/*  989 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "TotalEntitySizeLimit", new Object[] {
/*  990 */             Integer.valueOf(this.fLimitAnalyzer.getTotalValue(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT)), 
/*  991 */             Integer.valueOf(this.fSecurityManager.getLimit(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT)), this.fSecurityManager
/*  992 */             .getStateLiteral(XMLSecurityManager.Limit.TOTAL_ENTITY_SIZE_LIMIT) }, (short)2);
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
/*      */   protected int scanContent(XMLString content) throws IOException {
/* 1027 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1028 */       load(0, true, true);
/* 1029 */     } else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1030 */       invokeListeners(1);
/* 1031 */       this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
/* 1032 */       load(1, false, false);
/* 1033 */       this.fCurrentEntity.position = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1037 */     int offset = this.fCurrentEntity.position;
/* 1038 */     int c = this.fCurrentEntity.ch[offset];
/* 1039 */     int newlines = 0;
/* 1040 */     boolean counted = false;
/* 1041 */     if (c == 10 || (c == 13 && this.isExternal)) {
/*      */ 
/*      */       
/*      */       do {
/*      */ 
/*      */ 
/*      */         
/* 1048 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1049 */         if (c == 13 && this.isExternal) {
/* 1050 */           newlines++;
/* 1051 */           this.fCurrentEntity.lineNumber++;
/* 1052 */           this.fCurrentEntity.columnNumber = 1;
/* 1053 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1054 */             checkEntityLimit(null, this.fCurrentEntity, offset, newlines);
/* 1055 */             offset = 0;
/* 1056 */             this.fCurrentEntity.position = newlines;
/* 1057 */             if (load(newlines, false, true)) {
/* 1058 */               counted = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1062 */           if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
/* 1063 */             this.fCurrentEntity.position++;
/* 1064 */             offset++;
/*      */           }
/*      */           else {
/*      */             
/* 1068 */             newlines++;
/*      */           } 
/* 1070 */         } else if (c == 10) {
/* 1071 */           newlines++;
/* 1072 */           this.fCurrentEntity.lineNumber++;
/* 1073 */           this.fCurrentEntity.columnNumber = 1;
/* 1074 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1075 */             checkEntityLimit(null, this.fCurrentEntity, offset, newlines);
/* 1076 */             offset = 0;
/* 1077 */             this.fCurrentEntity.position = newlines;
/* 1078 */             if (load(newlines, false, true)) {
/* 1079 */               counted = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1084 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/* 1087 */       } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/* 1088 */       for (int i = offset; i < this.fCurrentEntity.position; i++) {
/* 1089 */         this.fCurrentEntity.ch[i] = '\n';
/*      */       }
/* 1091 */       int j = this.fCurrentEntity.position - offset;
/* 1092 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1093 */         checkEntityLimit(null, this.fCurrentEntity, offset, j);
/*      */ 
/*      */         
/* 1096 */         content.setValues(this.fCurrentEntity.ch, offset, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1103 */         return -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/* 1113 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1114 */       if (!XMLChar.isContent(c)) {
/* 1115 */         this.fCurrentEntity.position--;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1119 */     int length = this.fCurrentEntity.position - offset;
/* 1120 */     this.fCurrentEntity.columnNumber += length - newlines;
/* 1121 */     if (!counted) {
/* 1122 */       checkEntityLimit(null, this.fCurrentEntity, offset, length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1127 */     content.setValues(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */     
/* 1130 */     if (this.fCurrentEntity.position != this.fCurrentEntity.count) {
/* 1131 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */       
/* 1134 */       if (c == 13 && this.isExternal) {
/* 1135 */         c = 10;
/*      */       }
/*      */     } else {
/* 1138 */       c = -1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     return c;
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
/*      */   protected int scanLiteral(int quote, XMLString content, boolean isNSURI) throws IOException {
/* 1186 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1187 */       load(0, true, true);
/* 1188 */     } else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1189 */       invokeListeners(1);
/* 1190 */       this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
/* 1191 */       load(1, false, false);
/* 1192 */       this.fCurrentEntity.position = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1196 */     int offset = this.fCurrentEntity.position;
/* 1197 */     int c = this.fCurrentEntity.ch[offset];
/* 1198 */     int newlines = 0;
/* 1199 */     if (this.whiteSpaceInfoNeeded)
/* 1200 */       this.whiteSpaceLen = 0; 
/* 1201 */     if (c == 10 || (c == 13 && this.isExternal)) {
/*      */ 
/*      */       
/*      */       do {
/*      */ 
/*      */ 
/*      */         
/* 1208 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1209 */         if (c == 13 && this.isExternal) {
/* 1210 */           newlines++;
/* 1211 */           this.fCurrentEntity.lineNumber++;
/* 1212 */           this.fCurrentEntity.columnNumber = 1;
/* 1213 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1214 */             offset = 0;
/* 1215 */             this.fCurrentEntity.position = newlines;
/* 1216 */             if (load(newlines, false, true)) {
/*      */               break;
/*      */             }
/*      */           } 
/* 1220 */           if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
/* 1221 */             this.fCurrentEntity.position++;
/* 1222 */             offset++;
/*      */           }
/*      */           else {
/*      */             
/* 1226 */             newlines++;
/*      */           }
/*      */         
/* 1229 */         } else if (c == 10) {
/* 1230 */           newlines++;
/* 1231 */           this.fCurrentEntity.lineNumber++;
/* 1232 */           this.fCurrentEntity.columnNumber = 1;
/* 1233 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1234 */             offset = 0;
/* 1235 */             this.fCurrentEntity.position = newlines;
/* 1236 */             if (load(newlines, false, true))
/*      */             {
/*      */               break;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1248 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/* 1251 */       } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/* 1252 */       int i = 0;
/* 1253 */       for (i = offset; i < this.fCurrentEntity.position; i++) {
/* 1254 */         this.fCurrentEntity.ch[i] = '\n';
/* 1255 */         storeWhiteSpace(i);
/*      */       } 
/*      */       
/* 1258 */       int j = this.fCurrentEntity.position - offset;
/* 1259 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1260 */         content.setValues(this.fCurrentEntity.ch, offset, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1266 */         return -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1276 */     for (; this.fCurrentEntity.position < this.fCurrentEntity.count; this.fCurrentEntity.position++) {
/* 1277 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1278 */       if ((c == quote && (!this.fCurrentEntity.literal || this.isExternal)) || c == 37 || 
/*      */         
/* 1280 */         !XMLChar.isContent(c)) {
/*      */         break;
/*      */       }
/* 1283 */       if (this.whiteSpaceInfoNeeded && c == 9) {
/* 1284 */         storeWhiteSpace(this.fCurrentEntity.position);
/*      */       }
/*      */     } 
/* 1287 */     int length = this.fCurrentEntity.position - offset;
/* 1288 */     this.fCurrentEntity.columnNumber += length - newlines;
/*      */     
/* 1290 */     checkEntityLimit(null, this.fCurrentEntity, offset, length);
/* 1291 */     if (isNSURI) {
/* 1292 */       checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*      */     }
/* 1294 */     content.setValues(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */     
/* 1297 */     if (this.fCurrentEntity.position != this.fCurrentEntity.count) {
/* 1298 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */ 
/*      */       
/* 1302 */       if (c == quote && this.fCurrentEntity.literal) {
/* 1303 */         c = -1;
/*      */       }
/*      */     } else {
/* 1306 */       c = -1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1313 */     return c;
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
/*      */   private void storeWhiteSpace(int whiteSpacePos) {
/* 1326 */     if (this.whiteSpaceLen >= this.whiteSpaceLookup.length) {
/* 1327 */       int[] tmp = new int[this.whiteSpaceLookup.length + 100];
/* 1328 */       System.arraycopy(this.whiteSpaceLookup, 0, tmp, 0, this.whiteSpaceLookup.length);
/* 1329 */       this.whiteSpaceLookup = tmp;
/*      */     } 
/*      */     
/* 1332 */     this.whiteSpaceLookup[this.whiteSpaceLen++] = whiteSpacePos;
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
/*      */   protected boolean scanData(String delimiter, XMLStringBuffer buffer) throws IOException {
/* 1362 */     boolean done = false;
/* 1363 */     int delimLen = delimiter.length();
/* 1364 */     char charAt0 = delimiter.charAt(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1374 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1375 */         load(0, true, false);
/*      */       }
/*      */       
/* 1378 */       boolean bNextEntity = false;
/*      */       
/* 1380 */       while (this.fCurrentEntity.position > this.fCurrentEntity.count - delimLen && !bNextEntity) {
/*      */ 
/*      */         
/* 1383 */         System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1389 */         bNextEntity = load(this.fCurrentEntity.count - this.fCurrentEntity.position, false, false);
/* 1390 */         this.fCurrentEntity.position = 0;
/* 1391 */         this.fCurrentEntity.startPosition = 0;
/*      */       } 
/*      */       
/* 1394 */       if (this.fCurrentEntity.position > this.fCurrentEntity.count - delimLen) {
/*      */         
/* 1396 */         int i = this.fCurrentEntity.count - this.fCurrentEntity.position;
/* 1397 */         checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, this.fCurrentEntity.position, i);
/* 1398 */         buffer.append(this.fCurrentEntity.ch, this.fCurrentEntity.position, i);
/* 1399 */         this.fCurrentEntity.columnNumber += this.fCurrentEntity.count;
/* 1400 */         this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/* 1401 */         this.fCurrentEntity.position = this.fCurrentEntity.count;
/* 1402 */         this.fCurrentEntity.startPosition = this.fCurrentEntity.count;
/* 1403 */         load(0, true, false);
/* 1404 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1408 */       int offset = this.fCurrentEntity.position;
/* 1409 */       int c = this.fCurrentEntity.ch[offset];
/* 1410 */       int newlines = 0;
/* 1411 */       if (c == 10 || (c == 13 && this.isExternal)) {
/*      */ 
/*      */         
/*      */         do {
/*      */ 
/*      */ 
/*      */           
/* 1418 */           c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1419 */           if (c == 13 && this.isExternal) {
/* 1420 */             newlines++;
/* 1421 */             this.fCurrentEntity.lineNumber++;
/* 1422 */             this.fCurrentEntity.columnNumber = 1;
/* 1423 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1424 */               offset = 0;
/* 1425 */               this.fCurrentEntity.position = newlines;
/* 1426 */               if (load(newlines, false, true)) {
/*      */                 break;
/*      */               }
/*      */             } 
/* 1430 */             if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
/* 1431 */               this.fCurrentEntity.position++;
/* 1432 */               offset++;
/*      */             }
/*      */             else {
/*      */               
/* 1436 */               newlines++;
/*      */             } 
/* 1438 */           } else if (c == 10) {
/* 1439 */             newlines++;
/* 1440 */             this.fCurrentEntity.lineNumber++;
/* 1441 */             this.fCurrentEntity.columnNumber = 1;
/* 1442 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1443 */               offset = 0;
/* 1444 */               this.fCurrentEntity.position = newlines;
/* 1445 */               this.fCurrentEntity.count = newlines;
/* 1446 */               if (load(newlines, false, true)) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } else {
/* 1451 */             this.fCurrentEntity.position--;
/*      */             break;
/*      */           } 
/* 1454 */         } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/* 1455 */         for (int i = offset; i < this.fCurrentEntity.position; i++) {
/* 1456 */           this.fCurrentEntity.ch[i] = '\n';
/*      */         }
/* 1458 */         int j = this.fCurrentEntity.position - offset;
/* 1459 */         if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1460 */           checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, j);
/* 1461 */           buffer.append(this.fCurrentEntity.ch, offset, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1467 */           return true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1477 */       label84: while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/* 1478 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1479 */         if (c == charAt0) {
/*      */           
/* 1481 */           int delimOffset = this.fCurrentEntity.position - 1;
/* 1482 */           for (int i = 1; i < delimLen; i++) {
/* 1483 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1484 */               this.fCurrentEntity.position -= i;
/*      */               break label84;
/*      */             } 
/* 1487 */             c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1488 */             if (delimiter.charAt(i) != c) {
/* 1489 */               this.fCurrentEntity.position -= i;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1493 */           if (this.fCurrentEntity.position == delimOffset + delimLen) {
/* 1494 */             done = true; break;
/*      */           }  continue;
/*      */         } 
/* 1497 */         if (c == 10 || (this.isExternal && c == 13)) {
/* 1498 */           this.fCurrentEntity.position--; break;
/*      */         } 
/* 1500 */         if (XMLChar.isInvalid(c)) {
/* 1501 */           this.fCurrentEntity.position--;
/* 1502 */           int i = this.fCurrentEntity.position - offset;
/* 1503 */           this.fCurrentEntity.columnNumber += i - newlines;
/* 1504 */           checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, i);
/* 1505 */           buffer.append(this.fCurrentEntity.ch, offset, i);
/* 1506 */           return true;
/*      */         } 
/*      */       } 
/* 1509 */       int length = this.fCurrentEntity.position - offset;
/* 1510 */       this.fCurrentEntity.columnNumber += length - newlines;
/* 1511 */       checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, length);
/* 1512 */       if (done) {
/* 1513 */         length -= delimLen;
/*      */       }
/* 1515 */       buffer.append(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1523 */       if (done) {
/* 1524 */         return !done;
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
/*      */   protected boolean skipChar(int c, XMLScanner.NameType nt) throws IOException {
/* 1550 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1551 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/* 1555 */     int offset = this.fCurrentEntity.position;
/* 1556 */     int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1557 */     if (cc == c) {
/* 1558 */       this.fCurrentEntity.position++;
/* 1559 */       if (c == 10) {
/* 1560 */         this.fCurrentEntity.lineNumber++;
/* 1561 */         this.fCurrentEntity.columnNumber = 1;
/*      */       } else {
/* 1563 */         this.fCurrentEntity.columnNumber++;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1570 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1571 */       return true;
/* 1572 */     }  if (c == 10 && cc == 13 && this.isExternal) {
/*      */       
/* 1574 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1575 */         invokeListeners(1);
/* 1576 */         this.fCurrentEntity.ch[0] = (char)cc;
/* 1577 */         load(1, false, false);
/*      */       } 
/* 1579 */       this.fCurrentEntity.position++;
/* 1580 */       if (this.fCurrentEntity.ch[this.fCurrentEntity.position] == '\n') {
/* 1581 */         this.fCurrentEntity.position++;
/*      */       }
/* 1583 */       this.fCurrentEntity.lineNumber++;
/* 1584 */       this.fCurrentEntity.columnNumber = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1590 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1591 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1600 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpace(char ch) {
/* 1605 */     return (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r');
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
/*      */   protected boolean skipSpaces() throws IOException {
/* 1628 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1629 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1638 */     if (this.fCurrentEntity == null) {
/* 1639 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1643 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1644 */     int offset = this.fCurrentEntity.position - 1;
/* 1645 */     if (XMLChar.isSpace(c)) {
/*      */       do {
/* 1647 */         boolean entityChanged = false;
/*      */         
/* 1649 */         if (c == 10 || (this.isExternal && c == 13)) {
/* 1650 */           this.fCurrentEntity.lineNumber++;
/* 1651 */           this.fCurrentEntity.columnNumber = 1;
/* 1652 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1653 */             invokeListeners(1);
/* 1654 */             this.fCurrentEntity.ch[0] = (char)c;
/* 1655 */             entityChanged = load(1, true, false);
/* 1656 */             if (!entityChanged) {
/*      */ 
/*      */               
/* 1659 */               this.fCurrentEntity.position = 0;
/* 1660 */             } else if (this.fCurrentEntity == null) {
/* 1661 */               return true;
/*      */             } 
/*      */           } 
/* 1664 */           if (c == 13 && this.isExternal)
/*      */           {
/*      */             
/* 1667 */             if (this.fCurrentEntity.ch[++this.fCurrentEntity.position] != '\n') {
/* 1668 */               this.fCurrentEntity.position--;
/*      */             }
/*      */           }
/*      */         } else {
/* 1672 */           this.fCurrentEntity.columnNumber++;
/*      */         } 
/*      */ 
/*      */         
/* 1676 */         checkEntityLimit(null, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1677 */         offset = this.fCurrentEntity.position;
/*      */ 
/*      */         
/* 1680 */         if (!entityChanged) {
/* 1681 */           this.fCurrentEntity.position++;
/*      */         }
/*      */         
/* 1684 */         if (this.fCurrentEntity.position != this.fCurrentEntity.count)
/* 1685 */           continue;  load(0, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1694 */         if (this.fCurrentEntity == null) {
/* 1695 */           return true;
/*      */         
/*      */         }
/*      */       }
/* 1699 */       while (XMLChar.isSpace(c = this.fCurrentEntity.ch[this.fCurrentEntity.position]));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1705 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean arrangeCapacity(int length) throws IOException {
/* 1725 */     return arrangeCapacity(length, false);
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
/*      */   public boolean arrangeCapacity(int length, boolean changeEntity) throws IOException {
/* 1740 */     if (this.fCurrentEntity.count - this.fCurrentEntity.position >= length) {
/* 1741 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1748 */     boolean entityChanged = false;
/*      */     
/* 1750 */     while (this.fCurrentEntity.count - this.fCurrentEntity.position < length) {
/* 1751 */       if (this.fCurrentEntity.ch.length - this.fCurrentEntity.position < length) {
/* 1752 */         invokeListeners(0);
/* 1753 */         System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
/* 1754 */         this.fCurrentEntity.count -= this.fCurrentEntity.position;
/* 1755 */         this.fCurrentEntity.position = 0;
/*      */       } 
/*      */       
/* 1758 */       if (this.fCurrentEntity.count - this.fCurrentEntity.position < length) {
/* 1759 */         int pos = this.fCurrentEntity.position;
/* 1760 */         invokeListeners(pos);
/* 1761 */         entityChanged = load(this.fCurrentEntity.count, changeEntity, false);
/* 1762 */         this.fCurrentEntity.position = pos;
/* 1763 */         if (entityChanged) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1774 */     if (this.fCurrentEntity.count - this.fCurrentEntity.position >= length) {
/* 1775 */       return true;
/*      */     }
/* 1777 */     return false;
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
/*      */   protected boolean skipString(String s) throws IOException {
/* 1796 */     int length = s.length();
/*      */ 
/*      */     
/* 1799 */     if (arrangeCapacity(length, false)) {
/* 1800 */       int beforeSkip = this.fCurrentEntity.position;
/* 1801 */       int afterSkip = this.fCurrentEntity.position + length - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1808 */       int i = length - 1;
/*      */       
/* 1810 */       while (s.charAt(i--) == this.fCurrentEntity.ch[afterSkip]) {
/* 1811 */         if (afterSkip-- == beforeSkip) {
/* 1812 */           this.fCurrentEntity.position += length;
/* 1813 */           this.fCurrentEntity.columnNumber += length;
/* 1814 */           if (!this.detectingVersion) {
/* 1815 */             checkEntityLimit(null, this.fCurrentEntity, beforeSkip, length);
/*      */           }
/* 1817 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1822 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean skipString(char[] s) throws IOException {
/* 1827 */     int length = s.length;
/*      */     
/* 1829 */     if (arrangeCapacity(length, false)) {
/* 1830 */       int beforeSkip = this.fCurrentEntity.position;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1837 */       for (int i = 0; i < length; i++) {
/* 1838 */         if (this.fCurrentEntity.ch[beforeSkip++] != s[i]) {
/* 1839 */           return false;
/*      */         }
/*      */       } 
/* 1842 */       this.fCurrentEntity.position += length;
/* 1843 */       this.fCurrentEntity.columnNumber += length;
/* 1844 */       if (!this.detectingVersion) {
/* 1845 */         checkEntityLimit(null, this.fCurrentEntity, beforeSkip, length);
/*      */       }
/* 1847 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1851 */     return false;
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
/*      */   final boolean load(int offset, boolean changeEntity, boolean notify) throws IOException {
/* 1884 */     if (notify) {
/* 1885 */       invokeListeners(offset);
/*      */     }
/*      */     
/* 1888 */     this.fCurrentEntity.fTotalCountTillLastLoad += this.fCurrentEntity.fLastCount;
/*      */     
/* 1890 */     int length = this.fCurrentEntity.ch.length - offset;
/* 1891 */     if (!this.fCurrentEntity.mayReadChunks && length > 64) {
/* 1892 */       length = 64;
/*      */     }
/*      */     
/* 1895 */     int count = this.fCurrentEntity.reader.read(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */ 
/*      */     
/* 1899 */     boolean entityChanged = false;
/* 1900 */     if (count != -1) {
/* 1901 */       if (count != 0)
/*      */       {
/* 1903 */         this.fCurrentEntity.fLastCount = count;
/* 1904 */         this.fCurrentEntity.count = count + offset;
/* 1905 */         this.fCurrentEntity.position = offset;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1910 */       this.fCurrentEntity.count = offset;
/* 1911 */       this.fCurrentEntity.position = offset;
/* 1912 */       entityChanged = true;
/*      */       
/* 1914 */       if (changeEntity) {
/*      */         
/* 1916 */         this.fEntityManager.endEntity();
/*      */         
/* 1918 */         if (this.fCurrentEntity == null) {
/* 1919 */           throw END_OF_DOCUMENT_ENTITY;
/*      */         }
/*      */         
/* 1922 */         if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1923 */           load(0, true, false);
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
/* 1934 */     return entityChanged;
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
/*      */   protected Reader createReader(InputStream inputStream, String encoding, Boolean isBigEndian) throws IOException {
/* 1958 */     if (encoding == null) {
/* 1959 */       encoding = "UTF-8";
/*      */     }
/*      */ 
/*      */     
/* 1963 */     String ENCODING = encoding.toUpperCase(Locale.ENGLISH);
/* 1964 */     if (ENCODING.equals("UTF-8"))
/*      */     {
/*      */ 
/*      */       
/* 1968 */       return new UTF8Reader(inputStream, this.fCurrentEntity.fBufferSize, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
/*      */     }
/* 1970 */     if (ENCODING.equals("US-ASCII"))
/*      */     {
/*      */ 
/*      */       
/* 1974 */       return new ASCIIReader(inputStream, this.fCurrentEntity.fBufferSize, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
/*      */     }
/* 1976 */     if (ENCODING.equals("ISO-10646-UCS-4")) {
/* 1977 */       if (isBigEndian != null) {
/* 1978 */         boolean isBE = isBigEndian.booleanValue();
/* 1979 */         if (isBE) {
/* 1980 */           return new UCSReader(inputStream, (short)8);
/*      */         }
/* 1982 */         return new UCSReader(inputStream, (short)4);
/*      */       } 
/*      */       
/* 1985 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { encoding }, (short)2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1991 */     if (ENCODING.equals("ISO-10646-UCS-2")) {
/* 1992 */       if (isBigEndian != null) {
/* 1993 */         boolean isBE = isBigEndian.booleanValue();
/* 1994 */         if (isBE) {
/* 1995 */           return new UCSReader(inputStream, (short)2);
/*      */         }
/* 1997 */         return new UCSReader(inputStream, (short)1);
/*      */       } 
/*      */       
/* 2000 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { encoding }, (short)2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2008 */     boolean validIANA = XMLChar.isValidIANAEncoding(encoding);
/* 2009 */     boolean validJava = XMLChar.isValidJavaEncoding(encoding);
/* 2010 */     if (!validIANA || (this.fAllowJavaEncodings && !validJava)) {
/* 2011 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { encoding }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2023 */       encoding = "ISO-8859-1";
/*      */     } 
/*      */ 
/*      */     
/* 2027 */     String javaEncoding = EncodingMap.getIANA2JavaMapping(ENCODING);
/* 2028 */     if (javaEncoding == null) {
/* 2029 */       if (this.fAllowJavaEncodings) {
/* 2030 */         javaEncoding = encoding;
/*      */       } else {
/* 2032 */         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { encoding }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2037 */         javaEncoding = "ISO8859_1";
/*      */       }
/*      */     
/* 2040 */     } else if (javaEncoding.equals("ASCII")) {
/*      */ 
/*      */ 
/*      */       
/* 2044 */       return new ASCIIReader(inputStream, this.fBufferSize, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2054 */     return new InputStreamReader(inputStream, javaEncoding);
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
/*      */   protected Object[] getEncodingName(byte[] b4, int count) {
/* 2070 */     if (count < 2) {
/* 2071 */       return new Object[] { "UTF-8", null };
/*      */     }
/*      */ 
/*      */     
/* 2075 */     int b0 = b4[0] & 0xFF;
/* 2076 */     int b1 = b4[1] & 0xFF;
/* 2077 */     if (b0 == 254 && b1 == 255)
/*      */     {
/* 2079 */       return new Object[] { "UTF-16BE", new Boolean(true) };
/*      */     }
/* 2081 */     if (b0 == 255 && b1 == 254)
/*      */     {
/* 2083 */       return new Object[] { "UTF-16LE", new Boolean(false) };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2088 */     if (count < 3) {
/* 2089 */       return new Object[] { "UTF-8", null };
/*      */     }
/*      */ 
/*      */     
/* 2093 */     int b2 = b4[2] & 0xFF;
/* 2094 */     if (b0 == 239 && b1 == 187 && b2 == 191) {
/* 2095 */       return new Object[] { "UTF-8", null };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2100 */     if (count < 4) {
/* 2101 */       return new Object[] { "UTF-8", null };
/*      */     }
/*      */ 
/*      */     
/* 2105 */     int b3 = b4[3] & 0xFF;
/* 2106 */     if (b0 == 0 && b1 == 0 && b2 == 0 && b3 == 60)
/*      */     {
/* 2108 */       return new Object[] { "ISO-10646-UCS-4", new Boolean(true) };
/*      */     }
/* 2110 */     if (b0 == 60 && b1 == 0 && b2 == 0 && b3 == 0)
/*      */     {
/* 2112 */       return new Object[] { "ISO-10646-UCS-4", new Boolean(false) };
/*      */     }
/* 2114 */     if (b0 == 0 && b1 == 0 && b2 == 60 && b3 == 0)
/*      */     {
/*      */       
/* 2117 */       return new Object[] { "ISO-10646-UCS-4", null };
/*      */     }
/* 2119 */     if (b0 == 0 && b1 == 60 && b2 == 0 && b3 == 0)
/*      */     {
/*      */       
/* 2122 */       return new Object[] { "ISO-10646-UCS-4", null };
/*      */     }
/* 2124 */     if (b0 == 0 && b1 == 60 && b2 == 0 && b3 == 63)
/*      */     {
/*      */ 
/*      */       
/* 2128 */       return new Object[] { "UTF-16BE", new Boolean(true) };
/*      */     }
/* 2130 */     if (b0 == 60 && b1 == 0 && b2 == 63 && b3 == 0)
/*      */     {
/*      */       
/* 2133 */       return new Object[] { "UTF-16LE", new Boolean(false) };
/*      */     }
/* 2135 */     if (b0 == 76 && b1 == 111 && b2 == 167 && b3 == 148)
/*      */     {
/*      */       
/* 2138 */       return new Object[] { "CP037", null };
/*      */     }
/*      */ 
/*      */     
/* 2142 */     return new Object[] { "UTF-8", null };
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
/*      */   final void print() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerListener(XMLBufferListener listener) {
/* 2212 */     if (!this.listeners.contains(listener)) {
/* 2213 */       this.listeners.add(listener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invokeListeners(int loadPos) {
/* 2222 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 2223 */       ((XMLBufferListener)this.listeners.get(i)).refresh(loadPos);
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
/*      */   protected final boolean skipDeclSpaces() throws IOException {
/* 2252 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 2253 */       load(0, true, false);
/*      */     }
/*      */ 
/*      */     
/* 2257 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 2258 */     if (XMLChar.isSpace(c)) {
/* 2259 */       boolean external = this.fCurrentEntity.isExternal();
/*      */       do {
/* 2261 */         boolean entityChanged = false;
/*      */         
/* 2263 */         if (c == 10 || (external && c == 13)) {
/* 2264 */           this.fCurrentEntity.lineNumber++;
/* 2265 */           this.fCurrentEntity.columnNumber = 1;
/* 2266 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 2267 */             this.fCurrentEntity.ch[0] = (char)c;
/* 2268 */             entityChanged = load(1, true, false);
/* 2269 */             if (!entityChanged)
/*      */             {
/*      */               
/* 2272 */               this.fCurrentEntity.position = 0; } 
/*      */           } 
/* 2274 */           if (c == 13 && external)
/*      */           {
/*      */             
/* 2277 */             if (this.fCurrentEntity.ch[++this.fCurrentEntity.position] != '\n') {
/* 2278 */               this.fCurrentEntity.position--;
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 2290 */           this.fCurrentEntity.columnNumber++;
/*      */         } 
/*      */         
/* 2293 */         if (!entityChanged)
/* 2294 */           this.fCurrentEntity.position++; 
/* 2295 */         if (this.fCurrentEntity.position != this.fCurrentEntity.count)
/* 2296 */           continue;  load(0, true, false);
/*      */       }
/* 2298 */       while (XMLChar.isSpace(c = this.fCurrentEntity.ch[this.fCurrentEntity.position]));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2304 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2313 */     return false;
/*      */   }
/*      */   
/*      */   public XMLEntityScanner() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLEntityScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */