/*      */ package com.sun.xml.internal.fastinfoset.sax;
/*      */ 
/*      */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.internal.fastinfoset.Decoder;
/*      */ import com.sun.xml.internal.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.internal.fastinfoset.EncodingConstants;
/*      */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithmState;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.internal.fastinfoset.util.CharArrayString;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.sax.EncodingAlgorithmContentHandler;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.sax.FastInfosetReader;
/*      */ import com.sun.xml.internal.org.jvnet.fastinfoset.sax.PrimitiveTypeContentHandler;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXNotSupportedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAXDocumentParser
/*      */   extends Decoder
/*      */   implements FastInfosetReader
/*      */ {
/*   76 */   private static final Logger logger = Logger.getLogger(SAXDocumentParser.class.getName());
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class LexicalHandlerImpl
/*      */     implements LexicalHandler
/*      */   {
/*      */     private LexicalHandlerImpl() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void comment(char[] ch, int start, int end) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startDTD(String name, String publicId, String systemId) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endDTD() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startEntity(String name) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endEntity(String name) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void startCDATA() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void endCDATA() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DeclHandlerImpl
/*      */     implements DeclHandler
/*      */   {
/*      */     private DeclHandlerImpl() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void elementDecl(String name, String model) throws SAXException {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {}
/*      */ 
/*      */     
/*      */     public void internalEntityDecl(String name, String value) throws SAXException {}
/*      */ 
/*      */     
/*      */     public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {}
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean _namespacePrefixesFeature = false;
/*      */   
/*      */   protected EntityResolver _entityResolver;
/*      */   
/*      */   protected DTDHandler _dtdHandler;
/*      */   
/*      */   protected ContentHandler _contentHandler;
/*      */   
/*      */   protected ErrorHandler _errorHandler;
/*      */   
/*      */   protected LexicalHandler _lexicalHandler;
/*      */   
/*      */   protected DeclHandler _declHandler;
/*      */   
/*      */   protected EncodingAlgorithmContentHandler _algorithmHandler;
/*      */   
/*      */   protected PrimitiveTypeContentHandler _primitiveHandler;
/*      */   
/*  155 */   protected BuiltInEncodingAlgorithmState builtInAlgorithmState = new BuiltInEncodingAlgorithmState();
/*      */ 
/*      */   
/*      */   protected AttributesHolder _attributes;
/*      */   
/*  160 */   protected int[] _namespacePrefixes = new int[16];
/*      */   
/*      */   protected int _namespacePrefixesIndex;
/*      */   
/*      */   protected boolean _clearAttributes = false;
/*      */ 
/*      */   
/*      */   public SAXDocumentParser() {
/*  168 */     DefaultHandler handler = new DefaultHandler();
/*  169 */     this._attributes = new AttributesHolder(this._registeredEncodingAlgorithms);
/*      */     
/*  171 */     this._entityResolver = handler;
/*  172 */     this._dtdHandler = handler;
/*  173 */     this._contentHandler = handler;
/*  174 */     this._errorHandler = handler;
/*  175 */     this._lexicalHandler = new LexicalHandlerImpl();
/*  176 */     this._declHandler = new DeclHandlerImpl();
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*  180 */     this._clearAttributes = false;
/*  181 */     this._attributes.clear();
/*  182 */     this._namespacePrefixesIndex = 0;
/*      */     
/*  184 */     if (this._v != null) {
/*  185 */       this._v.prefix.clearCompletely();
/*      */     }
/*  187 */     this._duplicateAttributeVerifier.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  194 */     if (name.equals("http://xml.org/sax/features/namespaces"))
/*  195 */       return true; 
/*  196 */     if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/*  197 */       return this._namespacePrefixesFeature; 
/*  198 */     if (name.equals("http://xml.org/sax/features/string-interning") || name
/*  199 */       .equals("http://jvnet.org/fastinfoset/parser/properties/string-interning")) {
/*  200 */       return getStringInterning();
/*      */     }
/*  202 */     throw new SAXNotRecognizedException(
/*  203 */         CommonResourceBundle.getInstance().getString("message.featureNotSupported") + name);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  209 */     if (name.equals("http://xml.org/sax/features/namespaces")) {
/*  210 */       if (!value) {
/*  211 */         throw new SAXNotSupportedException(name + ":" + value);
/*      */       }
/*  213 */     } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
/*  214 */       this._namespacePrefixesFeature = value;
/*  215 */     } else if (name.equals("http://xml.org/sax/features/string-interning") || name
/*  216 */       .equals("http://jvnet.org/fastinfoset/parser/properties/string-interning")) {
/*  217 */       setStringInterning(value);
/*      */     } else {
/*  219 */       throw new SAXNotRecognizedException(
/*  220 */           CommonResourceBundle.getInstance().getString("message.featureNotSupported") + name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  226 */     if (name.equals("http://xml.org/sax/properties/lexical-handler"))
/*  227 */       return getLexicalHandler(); 
/*  228 */     if (name.equals("http://xml.org/sax/properties/declaration-handler"))
/*  229 */       return getDeclHandler(); 
/*  230 */     if (name.equals("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies"))
/*  231 */       return getExternalVocabularies(); 
/*  232 */     if (name.equals("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms"))
/*  233 */       return getRegisteredEncodingAlgorithms(); 
/*  234 */     if (name.equals("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler"))
/*  235 */       return getEncodingAlgorithmContentHandler(); 
/*  236 */     if (name.equals("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler")) {
/*  237 */       return getPrimitiveTypeContentHandler();
/*      */     }
/*  239 */     throw new SAXNotRecognizedException(CommonResourceBundle.getInstance()
/*  240 */         .getString("message.propertyNotRecognized", new Object[] { name }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/*  246 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/*  247 */       if (value instanceof LexicalHandler) {
/*  248 */         setLexicalHandler((LexicalHandler)value);
/*      */       } else {
/*  250 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*      */       } 
/*  252 */     } else if (name.equals("http://xml.org/sax/properties/declaration-handler")) {
/*  253 */       if (value instanceof DeclHandler) {
/*  254 */         setDeclHandler((DeclHandler)value);
/*      */       } else {
/*  256 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*      */       } 
/*  258 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies")) {
/*  259 */       if (value instanceof Map) {
/*  260 */         setExternalVocabularies((Map)value);
/*      */       } else {
/*  262 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/external-vocabularies");
/*      */       } 
/*  264 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms")) {
/*  265 */       if (value instanceof Map) {
/*  266 */         setRegisteredEncodingAlgorithms((Map)value);
/*      */       } else {
/*  268 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/registered-encoding-algorithms");
/*      */       } 
/*  270 */     } else if (name.equals("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler")) {
/*  271 */       if (value instanceof EncodingAlgorithmContentHandler) {
/*  272 */         setEncodingAlgorithmContentHandler((EncodingAlgorithmContentHandler)value);
/*      */       } else {
/*  274 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler");
/*      */       } 
/*  276 */     } else if (name.equals("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler")) {
/*  277 */       if (value instanceof PrimitiveTypeContentHandler) {
/*  278 */         setPrimitiveTypeContentHandler((PrimitiveTypeContentHandler)value);
/*      */       } else {
/*  280 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler");
/*      */       } 
/*  282 */     } else if (name.equals("http://jvnet.org/fastinfoset/parser/properties/buffer-size")) {
/*  283 */       if (value instanceof Integer) {
/*  284 */         setBufferSize(((Integer)value).intValue());
/*      */       } else {
/*  286 */         throw new SAXNotSupportedException("http://jvnet.org/fastinfoset/parser/properties/buffer-size");
/*      */       } 
/*      */     } else {
/*  289 */       throw new SAXNotRecognizedException(CommonResourceBundle.getInstance()
/*  290 */           .getString("message.propertyNotRecognized", new Object[] { name }));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setEntityResolver(EntityResolver resolver) {
/*  295 */     this._entityResolver = resolver;
/*      */   }
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*  299 */     return this._entityResolver;
/*      */   }
/*      */   
/*      */   public void setDTDHandler(DTDHandler handler) {
/*  303 */     this._dtdHandler = handler;
/*      */   }
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*  307 */     return this._dtdHandler;
/*      */   }
/*      */   public void setContentHandler(ContentHandler handler) {
/*  310 */     this._contentHandler = handler;
/*      */   }
/*      */   
/*      */   public ContentHandler getContentHandler() {
/*  314 */     return this._contentHandler;
/*      */   }
/*      */   
/*      */   public void setErrorHandler(ErrorHandler handler) {
/*  318 */     this._errorHandler = handler;
/*      */   }
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*  322 */     return this._errorHandler;
/*      */   }
/*      */   
/*      */   public void parse(InputSource input) throws IOException, SAXException {
/*      */     try {
/*  327 */       InputStream s = input.getByteStream();
/*  328 */       if (s == null) {
/*  329 */         String systemId = input.getSystemId();
/*  330 */         if (systemId == null) {
/*  331 */           throw new SAXException(CommonResourceBundle.getInstance().getString("message.inputSource"));
/*      */         }
/*  333 */         parse(systemId);
/*      */       } else {
/*  335 */         parse(s);
/*      */       } 
/*  337 */     } catch (FastInfosetException e) {
/*  338 */       logger.log(Level.FINE, "parsing error", (Throwable)e);
/*  339 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void parse(String systemId) throws IOException, SAXException {
/*      */     try {
/*  345 */       systemId = SystemIdResolver.getAbsoluteURI(systemId);
/*  346 */       parse((new URL(systemId)).openStream());
/*  347 */     } catch (FastInfosetException e) {
/*  348 */       logger.log(Level.FINE, "parsing error", (Throwable)e);
/*  349 */       throw new SAXException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void parse(InputStream s) throws IOException, FastInfosetException, SAXException {
/*  359 */     setInputStream(s);
/*  360 */     parse();
/*      */   }
/*      */   
/*      */   public void setLexicalHandler(LexicalHandler handler) {
/*  364 */     this._lexicalHandler = handler;
/*      */   }
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/*  368 */     return this._lexicalHandler;
/*      */   }
/*      */   
/*      */   public void setDeclHandler(DeclHandler handler) {
/*  372 */     this._declHandler = handler;
/*      */   }
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/*  376 */     return this._declHandler;
/*      */   }
/*      */   
/*      */   public void setEncodingAlgorithmContentHandler(EncodingAlgorithmContentHandler handler) {
/*  380 */     this._algorithmHandler = handler;
/*      */   }
/*      */   
/*      */   public EncodingAlgorithmContentHandler getEncodingAlgorithmContentHandler() {
/*  384 */     return this._algorithmHandler;
/*      */   }
/*      */   
/*      */   public void setPrimitiveTypeContentHandler(PrimitiveTypeContentHandler handler) {
/*  388 */     this._primitiveHandler = handler;
/*      */   }
/*      */   
/*      */   public PrimitiveTypeContentHandler getPrimitiveTypeContentHandler() {
/*  392 */     return this._primitiveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void parse() throws FastInfosetException, IOException {
/*  399 */     if (this._octetBuffer.length < this._bufferSize) {
/*  400 */       this._octetBuffer = new byte[this._bufferSize];
/*      */     }
/*      */     
/*      */     try {
/*  404 */       reset();
/*  405 */       decodeHeader();
/*  406 */       if (this._parseFragments)
/*  407 */       { processDIIFragment(); }
/*      */       else
/*  409 */       { processDII(); } 
/*  410 */     } catch (RuntimeException e) {
/*      */       try {
/*  412 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, e));
/*  413 */       } catch (Exception exception) {}
/*      */       
/*  415 */       resetOnError();
/*      */       
/*  417 */       throw new FastInfosetException(e);
/*  418 */     } catch (FastInfosetException e) {
/*      */       try {
/*  420 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, (Exception)e));
/*  421 */       } catch (Exception exception) {}
/*      */       
/*  423 */       resetOnError();
/*  424 */       throw e;
/*  425 */     } catch (IOException e) {
/*      */       try {
/*  427 */         this._errorHandler.fatalError(new SAXParseException(e.getClass().getName(), null, e));
/*  428 */       } catch (Exception exception) {}
/*      */       
/*  430 */       resetOnError();
/*  431 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/*      */     try {
/*  437 */       this._contentHandler.startDocument();
/*  438 */     } catch (SAXException e) {
/*  439 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */     
/*  442 */     this._b = read();
/*  443 */     if (this._b > 0) {
/*  444 */       processDIIOptionalProperties();
/*      */     }
/*      */ 
/*      */     
/*  448 */     boolean firstElementHasOccured = false;
/*  449 */     boolean documentTypeDeclarationOccured = false;
/*  450 */     while (!this._terminate || !firstElementHasOccured) {
/*  451 */       QualifiedName qn; String system_identifier, public_identifier; this._b = read();
/*  452 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 0:
/*  454 */           processEII(this._elementNameTable._array[this._b], false);
/*  455 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 1:
/*  458 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*  459 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 2:
/*  462 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*  463 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         case 3:
/*  466 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*  467 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 5:
/*  471 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */               
/*  473 */               .getNext());
/*  474 */           this._elementNameTable.add(qn);
/*  475 */           processEII(qn, ((this._b & 0x40) > 0));
/*  476 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 4:
/*  480 */           processEIIWithNamespaces();
/*  481 */           firstElementHasOccured = true;
/*      */           continue;
/*      */         
/*      */         case 20:
/*  485 */           if (documentTypeDeclarationOccured) {
/*  486 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.secondOccurenceOfDTDII"));
/*      */           }
/*  488 */           documentTypeDeclarationOccured = true;
/*      */ 
/*      */           
/*  491 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/*  493 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/*  495 */           this._b = read();
/*  496 */           while (this._b == 225) {
/*  497 */             switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */               case 0:
/*  499 */                 if (this._addToTable) {
/*  500 */                   this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */                 }
/*      */                 break;
/*      */               case 2:
/*  504 */                 throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  510 */             this._b = read();
/*      */           } 
/*  512 */           if ((this._b & 0xF0) != 240) {
/*  513 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingInstructionIIsNotTerminatedCorrectly"));
/*      */           }
/*  515 */           if (this._b == 255) {
/*  516 */             this._terminate = true;
/*      */           }
/*      */           
/*  519 */           if (this._notations != null) this._notations.clear(); 
/*  520 */           if (this._unparsedEntities != null) this._unparsedEntities.clear();
/*      */           
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 18:
/*  528 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  531 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  534 */           this._doubleTerminate = true;
/*      */         case 22:
/*  536 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  539 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  544 */     while (!this._terminate) {
/*  545 */       this._b = read();
/*  546 */       switch (DecoderStateTables.DII(this._b)) {
/*      */         case 18:
/*  548 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  551 */           processProcessingII();
/*      */           continue;
/*      */         case 23:
/*  554 */           this._doubleTerminate = true;
/*      */         case 22:
/*  556 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  559 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingDII"));
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  564 */       this._contentHandler.endDocument();
/*  565 */     } catch (SAXException e) {
/*  566 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processDIIFragment() throws FastInfosetException, IOException {
/*      */     try {
/*  572 */       this._contentHandler.startDocument();
/*  573 */     } catch (SAXException e) {
/*  574 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */     
/*  577 */     this._b = read();
/*  578 */     if (this._b > 0) {
/*  579 */       processDIIOptionalProperties();
/*      */     }
/*      */     
/*  582 */     while (!this._terminate) {
/*  583 */       QualifiedName qn; boolean addToTable; int index; String entity_reference_name, system_identifier, public_identifier; this._b = read();
/*  584 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  586 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  589 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  592 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  595 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  599 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */               
/*  601 */               .getNext());
/*  602 */           this._elementNameTable.add(qn);
/*  603 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  607 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         case 6:
/*  610 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  612 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 7:
/*  615 */           this._octetBufferLength = read() + 3;
/*  616 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 8:
/*  619 */           this
/*      */ 
/*      */             
/*  622 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  624 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 9:
/*  627 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  629 */           decodeUtf16StringAsCharBuffer();
/*  630 */           if ((this._b & 0x10) > 0) {
/*  631 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  635 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  636 */           } catch (SAXException e) {
/*  637 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 10:
/*  641 */           this._octetBufferLength = read() + 3;
/*  642 */           decodeUtf16StringAsCharBuffer();
/*  643 */           if ((this._b & 0x10) > 0) {
/*  644 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  648 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  649 */           } catch (SAXException e) {
/*  650 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 11:
/*  654 */           this
/*      */ 
/*      */             
/*  657 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  659 */           decodeUtf16StringAsCharBuffer();
/*  660 */           if ((this._b & 0x10) > 0) {
/*  661 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  665 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  666 */           } catch (SAXException e) {
/*  667 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 12:
/*  672 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  675 */           this._identifier = (this._b & 0x2) << 6;
/*  676 */           this._b = read();
/*  677 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  679 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  681 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  683 */           if (addToTable) {
/*  684 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  688 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  689 */           } catch (SAXException e) {
/*  690 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/*  696 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  699 */           this._identifier = (this._b & 0x2) << 6;
/*  700 */           this._b = read();
/*  701 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  703 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  705 */           processCIIEncodingAlgorithm(addToTable);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/*  710 */           index = this._b & 0xF;
/*      */           try {
/*  712 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  715 */           catch (SAXException e) {
/*  716 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/*  722 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*      */           try {
/*  725 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  728 */           catch (SAXException e) {
/*  729 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 16:
/*  737 */           index = ((this._b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */ 
/*      */           
/*      */           try {
/*  741 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  744 */           catch (SAXException e) {
/*  745 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 17:
/*  753 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */ 
/*      */           
/*      */           try {
/*  757 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/*  760 */           catch (SAXException e) {
/*  761 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 18:
/*  766 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/*  769 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/*  773 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */ 
/*      */           
/*  776 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/*  778 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  788 */             this._contentHandler.skippedEntity(entity_reference_name);
/*  789 */           } catch (SAXException e) {
/*  790 */             throw new FastInfosetException("processUnexpandedEntityReferenceII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 23:
/*  795 */           this._doubleTerminate = true;
/*      */         case 22:
/*  797 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/*  800 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  805 */       this._contentHandler.endDocument();
/*  806 */     } catch (SAXException e) {
/*  807 */       throw new FastInfosetException("processDII", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties() throws FastInfosetException, IOException {
/*  813 */     if (this._b == 32) {
/*  814 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/*  818 */     if ((this._b & 0x40) > 0) {
/*  819 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  826 */     if ((this._b & 0x20) > 0) {
/*  827 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/*  830 */     if ((this._b & 0x10) > 0) {
/*  831 */       decodeNotations();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  841 */     if ((this._b & 0x8) > 0) {
/*  842 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     if ((this._b & 0x4) > 0) {
/*  853 */       decodeCharacterEncodingScheme();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     if ((this._b & 0x2) > 0) {
/*  861 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     if ((this._b & 0x1) > 0) {
/*  869 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/*  878 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/*  879 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameOfEIINotInScope"));
/*      */     }
/*      */     
/*  882 */     if (hasAttributes) {
/*  883 */       processAIIs();
/*      */     }
/*      */     
/*      */     try {
/*  887 */       this._contentHandler.startElement(name.namespaceName, name.localName, name.qName, (Attributes)this._attributes);
/*  888 */     } catch (SAXException e) {
/*  889 */       logger.log(Level.FINE, "processEII error", e);
/*  890 */       throw new FastInfosetException("processEII", e);
/*      */     } 
/*      */     
/*  893 */     if (this._clearAttributes) {
/*  894 */       this._attributes.clear();
/*  895 */       this._clearAttributes = false;
/*      */     } 
/*      */     
/*  898 */     while (!this._terminate) {
/*  899 */       QualifiedName qn; boolean addToTable; int index; String entity_reference_name, system_identifier, public_identifier; this._b = read();
/*  900 */       switch (DecoderStateTables.EII(this._b)) {
/*      */         case 0:
/*  902 */           processEII(this._elementNameTable._array[this._b], false);
/*      */           continue;
/*      */         case 1:
/*  905 */           processEII(this._elementNameTable._array[this._b & 0x1F], true);
/*      */           continue;
/*      */         case 2:
/*  908 */           processEII(decodeEIIIndexMedium(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         case 3:
/*  911 */           processEII(decodeEIIIndexLarge(), ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 5:
/*  915 */           qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */               
/*  917 */               .getNext());
/*  918 */           this._elementNameTable.add(qn);
/*  919 */           processEII(qn, ((this._b & 0x40) > 0));
/*      */           continue;
/*      */         
/*      */         case 4:
/*  923 */           processEIIWithNamespaces();
/*      */           continue;
/*      */         case 6:
/*  926 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  928 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 7:
/*  931 */           this._octetBufferLength = read() + 3;
/*  932 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 8:
/*  935 */           this
/*      */ 
/*      */             
/*  938 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  940 */           processUtf8CharacterString();
/*      */           continue;
/*      */         case 9:
/*  943 */           this._octetBufferLength = (this._b & 0x1) + 1;
/*      */           
/*  945 */           decodeUtf16StringAsCharBuffer();
/*  946 */           if ((this._b & 0x10) > 0) {
/*  947 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  951 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  952 */           } catch (SAXException e) {
/*  953 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 10:
/*  957 */           this._octetBufferLength = read() + 3;
/*  958 */           decodeUtf16StringAsCharBuffer();
/*  959 */           if ((this._b & 0x10) > 0) {
/*  960 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  964 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  965 */           } catch (SAXException e) {
/*  966 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         case 11:
/*  970 */           this
/*      */ 
/*      */             
/*  973 */             ._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */           
/*  975 */           decodeUtf16StringAsCharBuffer();
/*  976 */           if ((this._b & 0x10) > 0) {
/*  977 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/*  981 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/*  982 */           } catch (SAXException e) {
/*  983 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 12:
/*  988 */           addToTable = ((this._b & 0x10) > 0);
/*      */ 
/*      */           
/*  991 */           this._identifier = (this._b & 0x2) << 6;
/*  992 */           this._b = read();
/*  993 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/*  995 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/*  997 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  999 */           if (addToTable) {
/* 1000 */             this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */           }
/*      */           
/*      */           try {
/* 1004 */             this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/* 1005 */           } catch (SAXException e) {
/* 1006 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 13:
/* 1012 */           addToTable = ((this._b & 0x10) > 0);
/*      */           
/* 1014 */           this._identifier = (this._b & 0x2) << 6;
/* 1015 */           this._b = read();
/* 1016 */           this._identifier |= (this._b & 0xFC) >> 2;
/*      */           
/* 1018 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(this._b);
/*      */           
/* 1020 */           processCIIEncodingAlgorithm(addToTable);
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/* 1025 */           index = this._b & 0xF;
/*      */           try {
/* 1027 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1030 */           catch (SAXException e) {
/* 1031 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 15:
/* 1037 */           index = ((this._b & 0x3) << 8 | read()) + 16;
/*      */           
/*      */           try {
/* 1040 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1043 */           catch (SAXException e) {
/* 1044 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 16:
/* 1052 */           index = ((this._b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */ 
/*      */           
/*      */           try {
/* 1056 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1059 */           catch (SAXException e) {
/* 1060 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 17:
/* 1068 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */ 
/*      */           
/*      */           try {
/* 1072 */             this._contentHandler.characters(this._characterContentChunkTable._array, this._characterContentChunkTable._offset[index], this._characterContentChunkTable._length[index]);
/*      */           
/*      */           }
/* 1075 */           catch (SAXException e) {
/* 1076 */             throw new FastInfosetException("processCII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 18:
/* 1081 */           processCommentII();
/*      */           continue;
/*      */         case 19:
/* 1084 */           processProcessingII();
/*      */           continue;
/*      */         
/*      */         case 21:
/* 1088 */           entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */ 
/*      */           
/* 1091 */           system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */           
/* 1093 */           public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1103 */             this._contentHandler.skippedEntity(entity_reference_name);
/* 1104 */           } catch (SAXException e) {
/* 1105 */             throw new FastInfosetException("processUnexpandedEntityReferenceII", e);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 23:
/* 1110 */           this._doubleTerminate = true;
/*      */         case 22:
/* 1112 */           this._terminate = true;
/*      */           continue;
/*      */       } 
/* 1115 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     } 
/*      */ 
/*      */     
/* 1119 */     this._terminate = this._doubleTerminate;
/* 1120 */     this._doubleTerminate = false;
/*      */     
/*      */     try {
/* 1123 */       this._contentHandler.endElement(name.namespaceName, name.localName, name.qName);
/* 1124 */     } catch (SAXException e) {
/* 1125 */       throw new FastInfosetException("processEII", e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf8CharacterString() throws FastInfosetException, IOException {
/* 1130 */     if ((this._b & 0x10) > 0) {
/* 1131 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/* 1132 */       int charactersOffset = this._characterContentChunkTable._arrayIndex;
/* 1133 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, charactersOffset);
/* 1134 */       this._characterContentChunkTable.add(this._charBufferLength);
/*      */       try {
/* 1136 */         this._contentHandler.characters(this._characterContentChunkTable._array, charactersOffset, this._charBufferLength);
/* 1137 */       } catch (SAXException e) {
/* 1138 */         throw new FastInfosetException("processCII", e);
/*      */       } 
/*      */     } else {
/* 1141 */       decodeUtf8StringAsCharBuffer();
/*      */       try {
/* 1143 */         this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/* 1144 */       } catch (SAXException e) {
/* 1145 */         throw new FastInfosetException("processCII", e);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected final void processEIIWithNamespaces() throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/* 1151 */     boolean hasAttributes = ((this._b & 0x40) > 0);
/*      */     
/* 1153 */     this._clearAttributes = this._namespacePrefixesFeature;
/*      */     
/* 1155 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/* 1156 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */     
/* 1159 */     String prefix = "", namespaceName = "";
/* 1160 */     int start = this._namespacePrefixesIndex;
/* 1161 */     int b = read();
/* 1162 */     while ((b & 0xFC) == 204) {
/* 1163 */       if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/* 1164 */         int[] namespaceAIIs = new int[this._namespacePrefixesIndex * 3 / 2 + 1];
/* 1165 */         System.arraycopy(this._namespacePrefixes, 0, namespaceAIIs, 0, this._namespacePrefixesIndex);
/* 1166 */         this._namespacePrefixes = namespaceAIIs;
/*      */       } 
/*      */       
/* 1169 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/* 1173 */           prefix = namespaceName = "";
/* 1174 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = -1; this._namespaceNameIndex = this._prefixIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/* 1179 */           prefix = "";
/* 1180 */           namespaceName = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false);
/*      */           
/* 1182 */           this._prefixIndex = this._namespacePrefixes[this._namespacePrefixesIndex++] = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1187 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/* 1188 */           namespaceName = "";
/*      */           
/* 1190 */           this._namespaceNameIndex = -1;
/* 1191 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 1196 */           prefix = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/* 1197 */           namespaceName = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true);
/*      */           
/* 1199 */           this._namespacePrefixes[this._namespacePrefixesIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */       
/* 1203 */       this._prefixTable.pushScope(this._prefixIndex, this._namespaceNameIndex);
/*      */       
/* 1205 */       if (this._namespacePrefixesFeature)
/*      */       {
/* 1207 */         if (prefix != "") {
/* 1208 */           this._attributes.addAttribute(new QualifiedName("xmlns", "http://www.w3.org/2000/xmlns/", prefix), namespaceName);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1214 */           this._attributes.addAttribute(EncodingConstants.DEFAULT_NAMESPACE_DECLARATION, namespaceName);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1220 */         this._contentHandler.startPrefixMapping(prefix, namespaceName);
/* 1221 */       } catch (SAXException e) {
/* 1222 */         throw new IOException("processStartNamespaceAII");
/*      */       } 
/*      */       
/* 1225 */       b = read();
/*      */     } 
/* 1227 */     if (b != 240) {
/* 1228 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/* 1230 */     int end = this._namespacePrefixesIndex;
/*      */     
/* 1232 */     this._b = read();
/* 1233 */     switch (DecoderStateTables.EII(this._b)) {
/*      */       case 0:
/* 1235 */         processEII(this._elementNameTable._array[this._b], hasAttributes);
/*      */         break;
/*      */       case 2:
/* 1238 */         processEII(decodeEIIIndexMedium(), hasAttributes);
/*      */         break;
/*      */       case 3:
/* 1241 */         processEII(decodeEIIIndexLarge(), hasAttributes);
/*      */         break;
/*      */       
/*      */       case 5:
/* 1245 */         qn = decodeLiteralQualifiedName(this._b & 0x3, this._elementNameTable
/*      */             
/* 1247 */             .getNext());
/* 1248 */         this._elementNameTable.add(qn);
/* 1249 */         processEII(qn, hasAttributes);
/*      */         break;
/*      */       
/*      */       default:
/* 1253 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */     } 
/*      */     
/*      */     try {
/* 1257 */       for (int i = end - 1; i >= start; i--) {
/* 1258 */         int prefixIndex = this._namespacePrefixes[i];
/* 1259 */         this._prefixTable.popScope(prefixIndex);
/* 1260 */         prefix = (prefixIndex > 0) ? this._prefixTable.get(prefixIndex - 1) : ((prefixIndex == -1) ? "" : "xml");
/*      */         
/* 1262 */         this._contentHandler.endPrefixMapping(prefix);
/*      */       } 
/* 1264 */       this._namespacePrefixesIndex = start;
/* 1265 */     } catch (SAXException e) {
/* 1266 */       throw new IOException("processStartNamespaceAII");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processAIIs() throws FastInfosetException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iconst_1
/*      */     //   2: putfield _clearAttributes : Z
/*      */     //   5: aload_0
/*      */     //   6: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   9: dup
/*      */     //   10: getfield _currentIteration : I
/*      */     //   13: iconst_1
/*      */     //   14: iadd
/*      */     //   15: dup_x1
/*      */     //   16: putfield _currentIteration : I
/*      */     //   19: ldc 2147483647
/*      */     //   21: if_icmpne -> 31
/*      */     //   24: aload_0
/*      */     //   25: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   28: invokevirtual clear : ()V
/*      */     //   31: aload_0
/*      */     //   32: invokevirtual read : ()I
/*      */     //   35: istore_2
/*      */     //   36: iload_2
/*      */     //   37: invokestatic AII : (I)I
/*      */     //   40: tableswitch default -> 215, 0 -> 80, 1 -> 93, 2 -> 124, 3 -> 164, 4 -> 207, 5 -> 202
/*      */     //   80: aload_0
/*      */     //   81: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   84: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   87: iload_2
/*      */     //   88: aaload
/*      */     //   89: astore_1
/*      */     //   90: goto -> 231
/*      */     //   93: iload_2
/*      */     //   94: bipush #31
/*      */     //   96: iand
/*      */     //   97: bipush #8
/*      */     //   99: ishl
/*      */     //   100: aload_0
/*      */     //   101: invokevirtual read : ()I
/*      */     //   104: ior
/*      */     //   105: bipush #64
/*      */     //   107: iadd
/*      */     //   108: istore #4
/*      */     //   110: aload_0
/*      */     //   111: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   114: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   117: iload #4
/*      */     //   119: aaload
/*      */     //   120: astore_1
/*      */     //   121: goto -> 231
/*      */     //   124: iload_2
/*      */     //   125: bipush #15
/*      */     //   127: iand
/*      */     //   128: bipush #16
/*      */     //   130: ishl
/*      */     //   131: aload_0
/*      */     //   132: invokevirtual read : ()I
/*      */     //   135: bipush #8
/*      */     //   137: ishl
/*      */     //   138: ior
/*      */     //   139: aload_0
/*      */     //   140: invokevirtual read : ()I
/*      */     //   143: ior
/*      */     //   144: sipush #8256
/*      */     //   147: iadd
/*      */     //   148: istore #4
/*      */     //   150: aload_0
/*      */     //   151: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   154: getfield _array : [Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   157: iload #4
/*      */     //   159: aaload
/*      */     //   160: astore_1
/*      */     //   161: goto -> 231
/*      */     //   164: aload_0
/*      */     //   165: iload_2
/*      */     //   166: iconst_3
/*      */     //   167: iand
/*      */     //   168: aload_0
/*      */     //   169: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   172: invokevirtual getNext : ()Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   175: invokevirtual decodeLiteralQualifiedName : (ILcom/sun/xml/internal/fastinfoset/QualifiedName;)Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   178: astore_1
/*      */     //   179: aload_1
/*      */     //   180: aload_0
/*      */     //   181: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   184: pop
/*      */     //   185: sipush #256
/*      */     //   188: invokevirtual createAttributeValues : (I)V
/*      */     //   191: aload_0
/*      */     //   192: getfield _attributeNameTable : Lcom/sun/xml/internal/fastinfoset/util/QualifiedNameArray;
/*      */     //   195: aload_1
/*      */     //   196: invokevirtual add : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;)V
/*      */     //   199: goto -> 231
/*      */     //   202: aload_0
/*      */     //   203: iconst_1
/*      */     //   204: putfield _doubleTerminate : Z
/*      */     //   207: aload_0
/*      */     //   208: iconst_1
/*      */     //   209: putfield _terminate : Z
/*      */     //   212: goto -> 950
/*      */     //   215: new java/io/IOException
/*      */     //   218: dup
/*      */     //   219: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   222: ldc 'message.decodingAIIs'
/*      */     //   224: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   227: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   230: athrow
/*      */     //   231: aload_1
/*      */     //   232: getfield prefixIndex : I
/*      */     //   235: ifle -> 273
/*      */     //   238: aload_0
/*      */     //   239: getfield _prefixTable : Lcom/sun/xml/internal/fastinfoset/util/PrefixArray;
/*      */     //   242: getfield _currentInScope : [I
/*      */     //   245: aload_1
/*      */     //   246: getfield prefixIndex : I
/*      */     //   249: iaload
/*      */     //   250: aload_1
/*      */     //   251: getfield namespaceNameIndex : I
/*      */     //   254: if_icmpeq -> 273
/*      */     //   257: new com/sun/xml/internal/org/jvnet/fastinfoset/FastInfosetException
/*      */     //   260: dup
/*      */     //   261: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   264: ldc 'message.AIIqNameNotInScope'
/*      */     //   266: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   269: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   272: athrow
/*      */     //   273: aload_0
/*      */     //   274: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   277: aload_1
/*      */     //   278: getfield attributeHash : I
/*      */     //   281: aload_1
/*      */     //   282: getfield attributeId : I
/*      */     //   285: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   288: aload_0
/*      */     //   289: invokevirtual read : ()I
/*      */     //   292: istore_2
/*      */     //   293: iload_2
/*      */     //   294: invokestatic NISTRING : (I)I
/*      */     //   297: tableswitch default -> 934, 0 -> 360, 1 -> 403, 2 -> 447, 3 -> 516, 4 -> 559, 5 -> 603, 6 -> 672, 7 -> 753, 8 -> 813, 9 -> 836, 10 -> 874, 11 -> 921
/*      */     //   360: aload_0
/*      */     //   361: iload_2
/*      */     //   362: bipush #7
/*      */     //   364: iand
/*      */     //   365: iconst_1
/*      */     //   366: iadd
/*      */     //   367: putfield _octetBufferLength : I
/*      */     //   370: aload_0
/*      */     //   371: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   374: astore_3
/*      */     //   375: iload_2
/*      */     //   376: bipush #64
/*      */     //   378: iand
/*      */     //   379: ifle -> 391
/*      */     //   382: aload_0
/*      */     //   383: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   386: aload_3
/*      */     //   387: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   390: pop
/*      */     //   391: aload_0
/*      */     //   392: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   395: aload_1
/*      */     //   396: aload_3
/*      */     //   397: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   400: goto -> 950
/*      */     //   403: aload_0
/*      */     //   404: aload_0
/*      */     //   405: invokevirtual read : ()I
/*      */     //   408: bipush #9
/*      */     //   410: iadd
/*      */     //   411: putfield _octetBufferLength : I
/*      */     //   414: aload_0
/*      */     //   415: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   418: astore_3
/*      */     //   419: iload_2
/*      */     //   420: bipush #64
/*      */     //   422: iand
/*      */     //   423: ifle -> 435
/*      */     //   426: aload_0
/*      */     //   427: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   430: aload_3
/*      */     //   431: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   434: pop
/*      */     //   435: aload_0
/*      */     //   436: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   439: aload_1
/*      */     //   440: aload_3
/*      */     //   441: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   444: goto -> 950
/*      */     //   447: aload_0
/*      */     //   448: aload_0
/*      */     //   449: invokevirtual read : ()I
/*      */     //   452: bipush #24
/*      */     //   454: ishl
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual read : ()I
/*      */     //   459: bipush #16
/*      */     //   461: ishl
/*      */     //   462: ior
/*      */     //   463: aload_0
/*      */     //   464: invokevirtual read : ()I
/*      */     //   467: bipush #8
/*      */     //   469: ishl
/*      */     //   470: ior
/*      */     //   471: aload_0
/*      */     //   472: invokevirtual read : ()I
/*      */     //   475: ior
/*      */     //   476: sipush #265
/*      */     //   479: iadd
/*      */     //   480: putfield _octetBufferLength : I
/*      */     //   483: aload_0
/*      */     //   484: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   487: astore_3
/*      */     //   488: iload_2
/*      */     //   489: bipush #64
/*      */     //   491: iand
/*      */     //   492: ifle -> 504
/*      */     //   495: aload_0
/*      */     //   496: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   499: aload_3
/*      */     //   500: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   503: pop
/*      */     //   504: aload_0
/*      */     //   505: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   508: aload_1
/*      */     //   509: aload_3
/*      */     //   510: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   513: goto -> 950
/*      */     //   516: aload_0
/*      */     //   517: iload_2
/*      */     //   518: bipush #7
/*      */     //   520: iand
/*      */     //   521: iconst_1
/*      */     //   522: iadd
/*      */     //   523: putfield _octetBufferLength : I
/*      */     //   526: aload_0
/*      */     //   527: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   530: astore_3
/*      */     //   531: iload_2
/*      */     //   532: bipush #64
/*      */     //   534: iand
/*      */     //   535: ifle -> 547
/*      */     //   538: aload_0
/*      */     //   539: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   542: aload_3
/*      */     //   543: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   546: pop
/*      */     //   547: aload_0
/*      */     //   548: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   551: aload_1
/*      */     //   552: aload_3
/*      */     //   553: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   556: goto -> 950
/*      */     //   559: aload_0
/*      */     //   560: aload_0
/*      */     //   561: invokevirtual read : ()I
/*      */     //   564: bipush #9
/*      */     //   566: iadd
/*      */     //   567: putfield _octetBufferLength : I
/*      */     //   570: aload_0
/*      */     //   571: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   574: astore_3
/*      */     //   575: iload_2
/*      */     //   576: bipush #64
/*      */     //   578: iand
/*      */     //   579: ifle -> 591
/*      */     //   582: aload_0
/*      */     //   583: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   586: aload_3
/*      */     //   587: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   590: pop
/*      */     //   591: aload_0
/*      */     //   592: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   595: aload_1
/*      */     //   596: aload_3
/*      */     //   597: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   600: goto -> 950
/*      */     //   603: aload_0
/*      */     //   604: aload_0
/*      */     //   605: invokevirtual read : ()I
/*      */     //   608: bipush #24
/*      */     //   610: ishl
/*      */     //   611: aload_0
/*      */     //   612: invokevirtual read : ()I
/*      */     //   615: bipush #16
/*      */     //   617: ishl
/*      */     //   618: ior
/*      */     //   619: aload_0
/*      */     //   620: invokevirtual read : ()I
/*      */     //   623: bipush #8
/*      */     //   625: ishl
/*      */     //   626: ior
/*      */     //   627: aload_0
/*      */     //   628: invokevirtual read : ()I
/*      */     //   631: ior
/*      */     //   632: sipush #265
/*      */     //   635: iadd
/*      */     //   636: putfield _octetBufferLength : I
/*      */     //   639: aload_0
/*      */     //   640: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   643: astore_3
/*      */     //   644: iload_2
/*      */     //   645: bipush #64
/*      */     //   647: iand
/*      */     //   648: ifle -> 660
/*      */     //   651: aload_0
/*      */     //   652: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   655: aload_3
/*      */     //   656: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   659: pop
/*      */     //   660: aload_0
/*      */     //   661: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   664: aload_1
/*      */     //   665: aload_3
/*      */     //   666: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   669: goto -> 950
/*      */     //   672: iload_2
/*      */     //   673: bipush #64
/*      */     //   675: iand
/*      */     //   676: ifle -> 683
/*      */     //   679: iconst_1
/*      */     //   680: goto -> 684
/*      */     //   683: iconst_0
/*      */     //   684: istore #4
/*      */     //   686: aload_0
/*      */     //   687: iload_2
/*      */     //   688: bipush #15
/*      */     //   690: iand
/*      */     //   691: iconst_4
/*      */     //   692: ishl
/*      */     //   693: putfield _identifier : I
/*      */     //   696: aload_0
/*      */     //   697: invokevirtual read : ()I
/*      */     //   700: istore_2
/*      */     //   701: aload_0
/*      */     //   702: dup
/*      */     //   703: getfield _identifier : I
/*      */     //   706: iload_2
/*      */     //   707: sipush #240
/*      */     //   710: iand
/*      */     //   711: iconst_4
/*      */     //   712: ishr
/*      */     //   713: ior
/*      */     //   714: putfield _identifier : I
/*      */     //   717: aload_0
/*      */     //   718: iload_2
/*      */     //   719: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   722: aload_0
/*      */     //   723: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   726: astore_3
/*      */     //   727: iload #4
/*      */     //   729: ifeq -> 741
/*      */     //   732: aload_0
/*      */     //   733: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   736: aload_3
/*      */     //   737: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   740: pop
/*      */     //   741: aload_0
/*      */     //   742: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   745: aload_1
/*      */     //   746: aload_3
/*      */     //   747: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   750: goto -> 950
/*      */     //   753: iload_2
/*      */     //   754: bipush #64
/*      */     //   756: iand
/*      */     //   757: ifle -> 764
/*      */     //   760: iconst_1
/*      */     //   761: goto -> 765
/*      */     //   764: iconst_0
/*      */     //   765: istore #4
/*      */     //   767: aload_0
/*      */     //   768: iload_2
/*      */     //   769: bipush #15
/*      */     //   771: iand
/*      */     //   772: iconst_4
/*      */     //   773: ishl
/*      */     //   774: putfield _identifier : I
/*      */     //   777: aload_0
/*      */     //   778: invokevirtual read : ()I
/*      */     //   781: istore_2
/*      */     //   782: aload_0
/*      */     //   783: dup
/*      */     //   784: getfield _identifier : I
/*      */     //   787: iload_2
/*      */     //   788: sipush #240
/*      */     //   791: iand
/*      */     //   792: iconst_4
/*      */     //   793: ishr
/*      */     //   794: ior
/*      */     //   795: putfield _identifier : I
/*      */     //   798: aload_0
/*      */     //   799: iload_2
/*      */     //   800: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   803: aload_0
/*      */     //   804: aload_1
/*      */     //   805: iload #4
/*      */     //   807: invokevirtual processAIIEncodingAlgorithm : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Z)V
/*      */     //   810: goto -> 950
/*      */     //   813: aload_0
/*      */     //   814: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   817: aload_1
/*      */     //   818: aload_0
/*      */     //   819: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   822: getfield _array : [Ljava/lang/String;
/*      */     //   825: iload_2
/*      */     //   826: bipush #63
/*      */     //   828: iand
/*      */     //   829: aaload
/*      */     //   830: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   833: goto -> 950
/*      */     //   836: iload_2
/*      */     //   837: bipush #31
/*      */     //   839: iand
/*      */     //   840: bipush #8
/*      */     //   842: ishl
/*      */     //   843: aload_0
/*      */     //   844: invokevirtual read : ()I
/*      */     //   847: ior
/*      */     //   848: bipush #64
/*      */     //   850: iadd
/*      */     //   851: istore #4
/*      */     //   853: aload_0
/*      */     //   854: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   857: aload_1
/*      */     //   858: aload_0
/*      */     //   859: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   862: getfield _array : [Ljava/lang/String;
/*      */     //   865: iload #4
/*      */     //   867: aaload
/*      */     //   868: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   871: goto -> 950
/*      */     //   874: iload_2
/*      */     //   875: bipush #15
/*      */     //   877: iand
/*      */     //   878: bipush #16
/*      */     //   880: ishl
/*      */     //   881: aload_0
/*      */     //   882: invokevirtual read : ()I
/*      */     //   885: bipush #8
/*      */     //   887: ishl
/*      */     //   888: ior
/*      */     //   889: aload_0
/*      */     //   890: invokevirtual read : ()I
/*      */     //   893: ior
/*      */     //   894: sipush #8256
/*      */     //   897: iadd
/*      */     //   898: istore #4
/*      */     //   900: aload_0
/*      */     //   901: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   904: aload_1
/*      */     //   905: aload_0
/*      */     //   906: getfield _attributeValueTable : Lcom/sun/xml/internal/fastinfoset/util/StringArray;
/*      */     //   909: getfield _array : [Ljava/lang/String;
/*      */     //   912: iload #4
/*      */     //   914: aaload
/*      */     //   915: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   918: goto -> 950
/*      */     //   921: aload_0
/*      */     //   922: getfield _attributes : Lcom/sun/xml/internal/fastinfoset/sax/AttributesHolder;
/*      */     //   925: aload_1
/*      */     //   926: ldc ''
/*      */     //   928: invokevirtual addAttribute : (Lcom/sun/xml/internal/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   931: goto -> 950
/*      */     //   934: new java/io/IOException
/*      */     //   937: dup
/*      */     //   938: invokestatic getInstance : ()Lcom/sun/xml/internal/fastinfoset/CommonResourceBundle;
/*      */     //   941: ldc 'message.decodingAIIValue'
/*      */     //   943: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   946: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   949: athrow
/*      */     //   950: aload_0
/*      */     //   951: getfield _terminate : Z
/*      */     //   954: ifeq -> 31
/*      */     //   957: aload_0
/*      */     //   958: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   961: aload_0
/*      */     //   962: getfield _duplicateAttributeVerifier : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   965: getfield _poolHead : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   968: putfield _poolCurrent : Lcom/sun/xml/internal/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   971: aload_0
/*      */     //   972: aload_0
/*      */     //   973: getfield _doubleTerminate : Z
/*      */     //   976: putfield _terminate : Z
/*      */     //   979: aload_0
/*      */     //   980: iconst_0
/*      */     //   981: putfield _doubleTerminate : Z
/*      */     //   984: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1275	-> 0
/*      */     //   #1277	-> 5
/*      */     //   #1278	-> 24
/*      */     //   #1283	-> 31
/*      */     //   #1284	-> 36
/*      */     //   #1286	-> 80
/*      */     //   #1287	-> 90
/*      */     //   #1290	-> 93
/*      */     //   #1292	-> 110
/*      */     //   #1293	-> 121
/*      */     //   #1297	-> 124
/*      */     //   #1299	-> 150
/*      */     //   #1300	-> 161
/*      */     //   #1303	-> 164
/*      */     //   #1305	-> 172
/*      */     //   #1303	-> 175
/*      */     //   #1306	-> 179
/*      */     //   #1307	-> 191
/*      */     //   #1308	-> 199
/*      */     //   #1310	-> 202
/*      */     //   #1312	-> 207
/*      */     //   #1314	-> 212
/*      */     //   #1316	-> 215
/*      */     //   #1319	-> 231
/*      */     //   #1320	-> 257
/*      */     //   #1323	-> 273
/*      */     //   #1327	-> 288
/*      */     //   #1328	-> 293
/*      */     //   #1330	-> 360
/*      */     //   #1331	-> 370
/*      */     //   #1332	-> 375
/*      */     //   #1333	-> 382
/*      */     //   #1336	-> 391
/*      */     //   #1337	-> 400
/*      */     //   #1339	-> 403
/*      */     //   #1340	-> 414
/*      */     //   #1341	-> 419
/*      */     //   #1342	-> 426
/*      */     //   #1345	-> 435
/*      */     //   #1346	-> 444
/*      */     //   #1348	-> 447
/*      */     //   #1349	-> 456
/*      */     //   #1350	-> 464
/*      */     //   #1351	-> 472
/*      */     //   #1353	-> 483
/*      */     //   #1354	-> 488
/*      */     //   #1355	-> 495
/*      */     //   #1358	-> 504
/*      */     //   #1359	-> 513
/*      */     //   #1361	-> 516
/*      */     //   #1362	-> 526
/*      */     //   #1363	-> 531
/*      */     //   #1364	-> 538
/*      */     //   #1367	-> 547
/*      */     //   #1368	-> 556
/*      */     //   #1370	-> 559
/*      */     //   #1371	-> 570
/*      */     //   #1372	-> 575
/*      */     //   #1373	-> 582
/*      */     //   #1376	-> 591
/*      */     //   #1377	-> 600
/*      */     //   #1379	-> 603
/*      */     //   #1380	-> 612
/*      */     //   #1381	-> 620
/*      */     //   #1382	-> 628
/*      */     //   #1384	-> 639
/*      */     //   #1385	-> 644
/*      */     //   #1386	-> 651
/*      */     //   #1389	-> 660
/*      */     //   #1390	-> 669
/*      */     //   #1393	-> 672
/*      */     //   #1395	-> 686
/*      */     //   #1396	-> 696
/*      */     //   #1397	-> 701
/*      */     //   #1399	-> 717
/*      */     //   #1401	-> 722
/*      */     //   #1402	-> 727
/*      */     //   #1403	-> 732
/*      */     //   #1406	-> 741
/*      */     //   #1407	-> 750
/*      */     //   #1411	-> 753
/*      */     //   #1413	-> 767
/*      */     //   #1414	-> 777
/*      */     //   #1415	-> 782
/*      */     //   #1417	-> 798
/*      */     //   #1419	-> 803
/*      */     //   #1420	-> 810
/*      */     //   #1423	-> 813
/*      */     //   #1425	-> 833
/*      */     //   #1428	-> 836
/*      */     //   #1431	-> 853
/*      */     //   #1433	-> 871
/*      */     //   #1437	-> 874
/*      */     //   #1440	-> 900
/*      */     //   #1442	-> 918
/*      */     //   #1445	-> 921
/*      */     //   #1446	-> 931
/*      */     //   #1448	-> 934
/*      */     //   #1451	-> 950
/*      */     //   #1454	-> 957
/*      */     //   #1456	-> 971
/*      */     //   #1457	-> 979
/*      */     //   #1458	-> 984
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   90	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   110	14	4	i	I
/*      */     //   121	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   150	14	4	i	I
/*      */     //   161	3	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   179	23	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   375	28	3	value	Ljava/lang/String;
/*      */     //   419	28	3	value	Ljava/lang/String;
/*      */     //   488	28	3	value	Ljava/lang/String;
/*      */     //   531	28	3	value	Ljava/lang/String;
/*      */     //   575	28	3	value	Ljava/lang/String;
/*      */     //   644	28	3	value	Ljava/lang/String;
/*      */     //   686	67	4	addToTable	Z
/*      */     //   727	26	3	value	Ljava/lang/String;
/*      */     //   767	46	4	addToTable	Z
/*      */     //   853	21	4	index	I
/*      */     //   900	21	4	index	I
/*      */     //   231	719	1	name	Lcom/sun/xml/internal/fastinfoset/QualifiedName;
/*      */     //   0	985	0	this	Lcom/sun/xml/internal/fastinfoset/sax/SAXDocumentParser;
/*      */     //   36	949	2	b	I
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processCommentII() throws FastInfosetException, IOException {
/*      */     CharArray ca;
/* 1461 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1463 */         if (this._addToTable) {
/* 1464 */           this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */         }
/*      */         
/*      */         try {
/* 1468 */           this._lexicalHandler.comment(this._charBuffer, 0, this._charBufferLength);
/* 1469 */         } catch (SAXException e) {
/* 1470 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 1474 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       case 1:
/* 1476 */         ca = this._v.otherString.get(this._integer);
/*      */         
/*      */         try {
/* 1479 */           this._lexicalHandler.comment(ca.ch, ca.start, ca.length);
/* 1480 */         } catch (SAXException e) {
/* 1481 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */       case 3:
/*      */         try {
/* 1486 */           this._lexicalHandler.comment(this._charBuffer, 0, 0);
/* 1487 */         } catch (SAXException e) {
/* 1488 */           throw new FastInfosetException("processCommentII", e);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/* 1495 */     String data, target = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/* 1497 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1499 */         data = new String(this._charBuffer, 0, this._charBufferLength);
/* 1500 */         if (this._addToTable) {
/* 1501 */           this._v.otherString.add((CharArray)new CharArrayString(data));
/*      */         }
/*      */         try {
/* 1504 */           this._contentHandler.processingInstruction(target, data);
/* 1505 */         } catch (SAXException e) {
/* 1506 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 1510 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       case 1:
/*      */         try {
/* 1513 */           this._contentHandler.processingInstruction(target, this._v.otherString.get(this._integer).toString());
/* 1514 */         } catch (SAXException e) {
/* 1515 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */       case 3:
/*      */         try {
/* 1520 */           this._contentHandler.processingInstruction(target, "");
/* 1521 */         } catch (SAXException e) {
/* 1522 */           throw new FastInfosetException("processProcessingII", e);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processCIIEncodingAlgorithm(boolean addToTable) throws FastInfosetException, IOException {
/* 1529 */     if (this._identifier < 9) {
/* 1530 */       if (this._primitiveHandler != null) {
/* 1531 */         processCIIBuiltInEncodingAlgorithmAsPrimitive();
/* 1532 */       } else if (this._algorithmHandler != null) {
/* 1533 */         Object array = processBuiltInEncodingAlgorithmAsObject();
/*      */         
/*      */         try {
/* 1536 */           this._algorithmHandler.object(null, this._identifier, array);
/* 1537 */         } catch (SAXException e) {
/* 1538 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } else {
/* 1541 */         StringBuffer buffer = new StringBuffer();
/* 1542 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/*      */         
/*      */         try {
/* 1545 */           this._contentHandler.characters(buffer.toString().toCharArray(), 0, buffer.length());
/* 1546 */         } catch (SAXException e) {
/* 1547 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } 
/*      */       
/* 1551 */       if (addToTable) {
/* 1552 */         StringBuffer buffer = new StringBuffer();
/* 1553 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/* 1554 */         this._characterContentChunkTable.add(buffer.toString().toCharArray(), buffer.length());
/*      */       } 
/* 1556 */     } else if (this._identifier == 9) {
/*      */       
/* 1558 */       this._octetBufferOffset -= this._octetBufferLength;
/* 1559 */       decodeUtf8StringIntoCharBuffer();
/*      */       
/*      */       try {
/* 1562 */         this._lexicalHandler.startCDATA();
/* 1563 */         this._contentHandler.characters(this._charBuffer, 0, this._charBufferLength);
/* 1564 */         this._lexicalHandler.endCDATA();
/* 1565 */       } catch (SAXException e) {
/* 1566 */         throw new FastInfosetException(e);
/*      */       } 
/*      */       
/* 1569 */       if (addToTable) {
/* 1570 */         this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*      */       }
/* 1572 */     } else if (this._identifier >= 32 && this._algorithmHandler != null) {
/* 1573 */       String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1574 */       if (URI == null) {
/* 1575 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance()
/* 1576 */             .getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/*      */       
/* 1579 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1580 */       if (ea != null) {
/* 1581 */         Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */         try {
/* 1583 */           this._algorithmHandler.object(URI, this._identifier, data);
/* 1584 */         } catch (SAXException e) {
/* 1585 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } else {
/*      */         try {
/* 1589 */           this._algorithmHandler.octets(URI, this._identifier, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1590 */         } catch (SAXException e) {
/* 1591 */           throw new FastInfosetException(e);
/*      */         } 
/*      */       } 
/* 1594 */       if (addToTable)
/* 1595 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.addToTableNotSupported")); 
/*      */     } else {
/* 1597 */       if (this._identifier >= 32)
/*      */       {
/* 1599 */         throw new EncodingAlgorithmException(
/* 1600 */             CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1605 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processCIIBuiltInEncodingAlgorithmAsPrimitive() throws FastInfosetException, IOException {
/*      */     try {
/*      */       int length;
/* 1612 */       switch (this._identifier) {
/*      */         case 0:
/*      */         case 1:
/* 1615 */           this._primitiveHandler.bytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           return;
/*      */         
/*      */         case 2:
/* 1619 */           length = BuiltInEncodingAlgorithmFactory.shortEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1620 */           if (length > this.builtInAlgorithmState.shortArray.length) {
/* 1621 */             short[] array = new short[length * 3 / 2 + 1];
/* 1622 */             System.arraycopy(this.builtInAlgorithmState.shortArray, 0, array, 0, this.builtInAlgorithmState.shortArray.length);
/*      */             
/* 1624 */             this.builtInAlgorithmState.shortArray = array;
/*      */           } 
/*      */           
/* 1627 */           BuiltInEncodingAlgorithmFactory.shortEncodingAlgorithm
/* 1628 */             .decodeFromBytesToShortArray(this.builtInAlgorithmState.shortArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1630 */           this._primitiveHandler.shorts(this.builtInAlgorithmState.shortArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 3:
/* 1634 */           length = BuiltInEncodingAlgorithmFactory.intEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1635 */           if (length > this.builtInAlgorithmState.intArray.length) {
/* 1636 */             int[] array = new int[length * 3 / 2 + 1];
/* 1637 */             System.arraycopy(this.builtInAlgorithmState.intArray, 0, array, 0, this.builtInAlgorithmState.intArray.length);
/*      */             
/* 1639 */             this.builtInAlgorithmState.intArray = array;
/*      */           } 
/*      */           
/* 1642 */           BuiltInEncodingAlgorithmFactory.intEncodingAlgorithm
/* 1643 */             .decodeFromBytesToIntArray(this.builtInAlgorithmState.intArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1645 */           this._primitiveHandler.ints(this.builtInAlgorithmState.intArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 4:
/* 1649 */           length = BuiltInEncodingAlgorithmFactory.longEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1650 */           if (length > this.builtInAlgorithmState.longArray.length) {
/* 1651 */             long[] array = new long[length * 3 / 2 + 1];
/* 1652 */             System.arraycopy(this.builtInAlgorithmState.longArray, 0, array, 0, this.builtInAlgorithmState.longArray.length);
/*      */             
/* 1654 */             this.builtInAlgorithmState.longArray = array;
/*      */           } 
/*      */           
/* 1657 */           BuiltInEncodingAlgorithmFactory.longEncodingAlgorithm
/* 1658 */             .decodeFromBytesToLongArray(this.builtInAlgorithmState.longArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1660 */           this._primitiveHandler.longs(this.builtInAlgorithmState.longArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 5:
/* 1664 */           length = BuiltInEncodingAlgorithmFactory.booleanEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength, this._octetBuffer[this._octetBufferStart] & 0xFF);
/* 1665 */           if (length > this.builtInAlgorithmState.booleanArray.length) {
/* 1666 */             boolean[] array = new boolean[length * 3 / 2 + 1];
/* 1667 */             System.arraycopy(this.builtInAlgorithmState.booleanArray, 0, array, 0, this.builtInAlgorithmState.booleanArray.length);
/*      */             
/* 1669 */             this.builtInAlgorithmState.booleanArray = array;
/*      */           } 
/*      */           
/* 1672 */           BuiltInEncodingAlgorithmFactory.booleanEncodingAlgorithm
/* 1673 */             .decodeFromBytesToBooleanArray(this.builtInAlgorithmState.booleanArray, 0, length, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */ 
/*      */           
/* 1676 */           this._primitiveHandler.booleans(this.builtInAlgorithmState.booleanArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 6:
/* 1680 */           length = BuiltInEncodingAlgorithmFactory.floatEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1681 */           if (length > this.builtInAlgorithmState.floatArray.length) {
/* 1682 */             float[] array = new float[length * 3 / 2 + 1];
/* 1683 */             System.arraycopy(this.builtInAlgorithmState.floatArray, 0, array, 0, this.builtInAlgorithmState.floatArray.length);
/*      */             
/* 1685 */             this.builtInAlgorithmState.floatArray = array;
/*      */           } 
/*      */           
/* 1688 */           BuiltInEncodingAlgorithmFactory.floatEncodingAlgorithm
/* 1689 */             .decodeFromBytesToFloatArray(this.builtInAlgorithmState.floatArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1691 */           this._primitiveHandler.floats(this.builtInAlgorithmState.floatArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 7:
/* 1695 */           length = BuiltInEncodingAlgorithmFactory.doubleEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1696 */           if (length > this.builtInAlgorithmState.doubleArray.length) {
/* 1697 */             double[] array = new double[length * 3 / 2 + 1];
/* 1698 */             System.arraycopy(this.builtInAlgorithmState.doubleArray, 0, array, 0, this.builtInAlgorithmState.doubleArray.length);
/*      */             
/* 1700 */             this.builtInAlgorithmState.doubleArray = array;
/*      */           } 
/*      */           
/* 1703 */           BuiltInEncodingAlgorithmFactory.doubleEncodingAlgorithm
/* 1704 */             .decodeFromBytesToDoubleArray(this.builtInAlgorithmState.doubleArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1706 */           this._primitiveHandler.doubles(this.builtInAlgorithmState.doubleArray, 0, length);
/*      */           return;
/*      */         
/*      */         case 8:
/* 1710 */           length = BuiltInEncodingAlgorithmFactory.uuidEncodingAlgorithm.getPrimtiveLengthFromOctetLength(this._octetBufferLength);
/* 1711 */           if (length > this.builtInAlgorithmState.longArray.length) {
/* 1712 */             long[] array = new long[length * 3 / 2 + 1];
/* 1713 */             System.arraycopy(this.builtInAlgorithmState.longArray, 0, array, 0, this.builtInAlgorithmState.longArray.length);
/*      */             
/* 1715 */             this.builtInAlgorithmState.longArray = array;
/*      */           } 
/*      */           
/* 1718 */           BuiltInEncodingAlgorithmFactory.uuidEncodingAlgorithm
/* 1719 */             .decodeFromBytesToLongArray(this.builtInAlgorithmState.longArray, 0, this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */           
/* 1721 */           this._primitiveHandler.uuids(this.builtInAlgorithmState.longArray, 0, length);
/*      */           return;
/*      */         case 9:
/* 1724 */           throw new UnsupportedOperationException("CDATA");
/*      */       } 
/* 1726 */       throw new FastInfosetException(CommonResourceBundle.getInstance()
/* 1727 */           .getString("message.unsupportedAlgorithm", new Object[] { Integer.valueOf(this._identifier) }));
/*      */     }
/* 1729 */     catch (SAXException e) {
/* 1730 */       throw new FastInfosetException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processAIIEncodingAlgorithm(QualifiedName name, boolean addToTable) throws FastInfosetException, IOException {
/* 1736 */     if (this._identifier < 9)
/* 1737 */     { if (this._primitiveHandler != null || this._algorithmHandler != null) {
/* 1738 */         Object data = processBuiltInEncodingAlgorithmAsObject();
/* 1739 */         this._attributes.addAttributeWithAlgorithmData(name, null, this._identifier, data);
/*      */       } else {
/* 1741 */         StringBuffer buffer = new StringBuffer();
/* 1742 */         processBuiltInEncodingAlgorithmAsCharacters(buffer);
/* 1743 */         this._attributes.addAttribute(name, buffer.toString());
/*      */       }  }
/* 1745 */     else if (this._identifier >= 32 && this._algorithmHandler != null)
/* 1746 */     { String URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1747 */       if (URI == null) {
/* 1748 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance()
/* 1749 */             .getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/*      */       
/* 1752 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1753 */       if (ea != null) {
/* 1754 */         Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1755 */         this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, data);
/*      */       } else {
/* 1757 */         byte[] data = new byte[this._octetBufferLength];
/* 1758 */         System.arraycopy(this._octetBuffer, this._octetBufferStart, data, 0, this._octetBufferLength);
/* 1759 */         this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, data);
/*      */       }  }
/* 1761 */     else { if (this._identifier >= 32)
/*      */       {
/* 1763 */         throw new EncodingAlgorithmException(
/* 1764 */             CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported")); } 
/* 1765 */       if (this._identifier == 9) {
/* 1766 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1771 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved")); }
/*      */ 
/*      */     
/* 1774 */     if (addToTable) {
/* 1775 */       this._attributeValueTable.add(this._attributes.getValue(this._attributes.getIndex(name.qName)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processBuiltInEncodingAlgorithmAsCharacters(StringBuffer buffer) throws FastInfosetException, IOException {
/* 1782 */     Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */     
/* 1784 */     BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier).convertToCharacters(array, buffer);
/*      */   }
/*      */   
/*      */   protected final Object processBuiltInEncodingAlgorithmAsObject() throws FastInfosetException, IOException {
/* 1788 */     return BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier)
/* 1789 */       .decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/sax/SAXDocumentParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */