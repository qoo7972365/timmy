/*     */ package com.sun.xml.internal.stream.buffer.sax;
/*     */ 
/*     */ import com.sun.xml.internal.stream.buffer.AbstractProcessor;
/*     */ import com.sun.xml.internal.stream.buffer.AttributesHolder;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.LocatorImpl;
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
/*     */ public class SAXBufferProcessor
/*     */   extends AbstractProcessor
/*     */   implements XMLReader
/*     */ {
/*  59 */   protected EntityResolver _entityResolver = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   protected DTDHandler _dtdHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected ContentHandler _contentHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected ErrorHandler _errorHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected LexicalHandler _lexicalHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _namespacePrefixesFeature = false;
/*     */ 
/*     */   
/*  86 */   protected AttributesHolder _attributes = new AttributesHolder();
/*     */   
/*  88 */   protected String[] _namespacePrefixes = new String[16];
/*     */   
/*     */   protected int _namespacePrefixesIndex;
/*  91 */   protected int[] _namespaceAttributesStartingStack = new int[16];
/*  92 */   protected int[] _namespaceAttributesStack = new int[16];
/*     */ 
/*     */   
/*     */   protected int _namespaceAttributesStackIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor() {}
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor(XMLStreamBuffer buffer) {
/* 103 */     setXMLStreamBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 112 */     setXMLStreamBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 117 */     if (name.equals("http://xml.org/sax/features/namespaces"))
/* 118 */       return true; 
/* 119 */     if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/* 120 */       return this._namespacePrefixesFeature; 
/* 121 */     if (name.equals("http://xml.org/sax/features/external-general-entities"))
/* 122 */       return true; 
/* 123 */     if (name.equals("http://xml.org/sax/features/external-parameter-entities"))
/* 124 */       return true; 
/* 125 */     if (name.equals("http://xml.org/sax/features/string-interning")) {
/* 126 */       return this._stringInterningFeature;
/*     */     }
/* 128 */     throw new SAXNotRecognizedException("Feature not supported: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 135 */     if (name.equals("http://xml.org/sax/features/namespaces")) {
/* 136 */       if (!value) {
/* 137 */         throw new SAXNotSupportedException(name + ":" + value);
/*     */       }
/* 139 */     } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
/* 140 */       this._namespacePrefixesFeature = value;
/* 141 */     } else if (!name.equals("http://xml.org/sax/features/external-general-entities")) {
/*     */       
/* 143 */       if (!name.equals("http://xml.org/sax/features/external-parameter-entities"))
/*     */       {
/* 145 */         if (name.equals("http://xml.org/sax/features/string-interning")) {
/* 146 */           if (value != this._stringInterningFeature) {
/* 147 */             throw new SAXNotSupportedException(name + ":" + value);
/*     */           }
/*     */         } else {
/* 150 */           throw new SAXNotRecognizedException("Feature not supported: " + name);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 157 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/* 158 */       return getLexicalHandler();
/*     */     }
/* 160 */     throw new SAXNotRecognizedException("Property not recognized: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 166 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/* 167 */       if (value instanceof LexicalHandler) {
/* 168 */         setLexicalHandler((LexicalHandler)value);
/*     */       } else {
/* 170 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*     */       } 
/*     */     } else {
/* 173 */       throw new SAXNotRecognizedException("Property not recognized: " + name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) {
/* 178 */     this._entityResolver = resolver;
/*     */   }
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 182 */     return this._entityResolver;
/*     */   }
/*     */   
/*     */   public void setDTDHandler(DTDHandler handler) {
/* 186 */     this._dtdHandler = handler;
/*     */   }
/*     */   
/*     */   public DTDHandler getDTDHandler() {
/* 190 */     return this._dtdHandler;
/*     */   }
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) {
/* 194 */     this._contentHandler = handler;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/* 198 */     return this._contentHandler;
/*     */   }
/*     */   
/*     */   public void setErrorHandler(ErrorHandler handler) {
/* 202 */     this._errorHandler = handler;
/*     */   }
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 206 */     return this._errorHandler;
/*     */   }
/*     */   
/*     */   public void setLexicalHandler(LexicalHandler handler) {
/* 210 */     this._lexicalHandler = handler;
/*     */   }
/*     */   
/*     */   public LexicalHandler getLexicalHandler() {
/* 214 */     return this._lexicalHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource input) throws IOException, SAXException {
/* 219 */     process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(String systemId) throws IOException, SAXException {
/* 224 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer) throws SAXException {
/* 234 */     setXMLStreamBuffer(buffer);
/* 235 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer, boolean produceFragmentEvent) throws SAXException {
/* 246 */     setXMLStreamBuffer(buffer);
/* 247 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) {
/* 257 */     setBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 268 */     if (!produceFragmentEvent && this._treeCount > 1)
/* 269 */       throw new IllegalStateException("Can't write a forest to a full XML infoset"); 
/* 270 */     setBuffer(buffer, produceFragmentEvent);
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
/*     */   public final void process() throws SAXException {
/* 289 */     if (!this._fragmentMode) {
/* 290 */       LocatorImpl nullLocator = new LocatorImpl();
/* 291 */       nullLocator.setSystemId(this._buffer.getSystemId());
/* 292 */       nullLocator.setLineNumber(-1);
/* 293 */       nullLocator.setColumnNumber(-1);
/* 294 */       this._contentHandler.setDocumentLocator(nullLocator);
/*     */       
/* 296 */       this._contentHandler.startDocument();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 301 */     while (this._treeCount > 0) {
/* 302 */       String prefix, uri, localName, str2, str1, str3; int item = readEiiState();
/* 303 */       switch (item) {
/*     */         case 1:
/* 305 */           processDocument();
/* 306 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 17:
/*     */           return;
/*     */         case 3:
/* 312 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope());
/* 313 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 4:
/* 317 */           prefix = readStructureString();
/* 318 */           str2 = readStructureString();
/* 319 */           str3 = readStructureString();
/* 320 */           processElement(str2, str3, getQName(prefix, str3), isInscope());
/* 321 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 5:
/* 325 */           uri = readStructureString();
/* 326 */           str1 = readStructureString();
/* 327 */           processElement(uri, str1, str1, isInscope());
/* 328 */           this._treeCount--;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 6:
/* 333 */           localName = readStructureString();
/* 334 */           processElement("", localName, localName, isInscope());
/* 335 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 12:
/* 339 */           processCommentAsCharArraySmall();
/*     */           continue;
/*     */         case 13:
/* 342 */           processCommentAsCharArrayMedium();
/*     */           continue;
/*     */         case 14:
/* 345 */           processCommentAsCharArrayCopy();
/*     */           continue;
/*     */         case 15:
/* 348 */           processComment(readContentString());
/*     */           continue;
/*     */         case 16:
/* 351 */           processProcessingInstruction(readStructureString(), readStructureString());
/*     */           continue;
/*     */       } 
/* 354 */       throw reportFatalError("Illegal state for DIIs: " + item);
/*     */     } 
/*     */ 
/*     */     
/* 358 */     if (!this._fragmentMode)
/* 359 */       this._contentHandler.endDocument(); 
/*     */   }
/*     */   
/*     */   private void processCommentAsCharArraySmall() throws SAXException {
/* 363 */     int length = readStructure();
/* 364 */     int start = readContentCharactersBuffer(length);
/* 365 */     processComment(this._contentCharactersBuffer, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SAXParseException reportFatalError(String msg) throws SAXException {
/* 374 */     SAXParseException spe = new SAXParseException(msg, null);
/* 375 */     if (this._errorHandler != null)
/* 376 */       this._errorHandler.fatalError(spe); 
/* 377 */     return spe;
/*     */   }
/*     */   
/*     */   private boolean isInscope() {
/* 381 */     return (this._buffer.getInscopeNamespaces().size() > 0);
/*     */   } private void processDocument() throws SAXException {
/*     */     int item;
/*     */     while (true) {
/*     */       String prefix, uri, localName, str2, str1, str3;
/* 386 */       item = readEiiState();
/* 387 */       switch (item) {
/*     */         case 3:
/* 389 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope());
/*     */           continue;
/*     */         
/*     */         case 4:
/* 393 */           prefix = readStructureString();
/* 394 */           str2 = readStructureString();
/* 395 */           str3 = readStructureString();
/* 396 */           processElement(str2, str3, getQName(prefix, str3), isInscope());
/*     */           continue;
/*     */         
/*     */         case 5:
/* 400 */           uri = readStructureString();
/* 401 */           str1 = readStructureString();
/* 402 */           processElement(uri, str1, str1, isInscope());
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 6:
/* 407 */           localName = readStructureString();
/* 408 */           processElement("", localName, localName, isInscope());
/*     */           continue;
/*     */         
/*     */         case 12:
/* 412 */           processCommentAsCharArraySmall();
/*     */           continue;
/*     */         case 13:
/* 415 */           processCommentAsCharArrayMedium();
/*     */           continue;
/*     */         case 14:
/* 418 */           processCommentAsCharArrayCopy();
/*     */           continue;
/*     */         case 15:
/* 421 */           processComment(readContentString());
/*     */           continue;
/*     */         case 16:
/* 424 */           processProcessingInstruction(readStructureString(), readStructureString()); continue;
/*     */         case 17:
/*     */           return;
/*     */       }  break;
/*     */     } 
/* 429 */     throw reportFatalError("Illegal state for child of DII: " + item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processElement(String uri, String localName, String qName, boolean inscope) throws SAXException {
/* 435 */     boolean hasAttributes = false;
/* 436 */     boolean hasNamespaceAttributes = false;
/* 437 */     int item = peekStructure();
/* 438 */     Set<String> prefixSet = inscope ? new HashSet<>() : Collections.<String>emptySet();
/* 439 */     if ((item & 0xF0) == 64) {
/* 440 */       cacheNamespacePrefixStartingIndex();
/* 441 */       hasNamespaceAttributes = true;
/* 442 */       item = processNamespaceAttributes(item, inscope, prefixSet);
/*     */     } 
/* 444 */     if (inscope) {
/* 445 */       readInscopeNamespaces(prefixSet);
/*     */     }
/*     */     
/* 448 */     if ((item & 0xF0) == 48) {
/* 449 */       hasAttributes = true;
/* 450 */       processAttributes(item);
/*     */     } 
/*     */     
/* 453 */     this._contentHandler.startElement(uri, localName, qName, (Attributes)this._attributes);
/*     */     
/* 455 */     if (hasAttributes)
/* 456 */       this._attributes.clear();  do {
/*     */       String p, u, ln; int length; char[] ch; String s; CharSequence c; String str3, str2;
/*     */       int start;
/*     */       String str1, str4;
/* 460 */       item = readEiiState();
/* 461 */       switch (item) {
/*     */         case 3:
/* 463 */           processElement(readStructureString(), readStructureString(), readStructureString(), false);
/*     */           break;
/*     */         
/*     */         case 4:
/* 467 */           p = readStructureString();
/* 468 */           str3 = readStructureString();
/* 469 */           str4 = readStructureString();
/* 470 */           processElement(str3, str4, getQName(p, str4), false);
/*     */           break;
/*     */         
/*     */         case 5:
/* 474 */           u = readStructureString();
/* 475 */           str2 = readStructureString();
/* 476 */           processElement(u, str2, str2, false);
/*     */           break;
/*     */         
/*     */         case 6:
/* 480 */           ln = readStructureString();
/* 481 */           processElement("", ln, ln, false);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 7:
/* 486 */           length = readStructure();
/* 487 */           start = readContentCharactersBuffer(length);
/* 488 */           this._contentHandler.characters(this._contentCharactersBuffer, start, length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 8:
/* 493 */           length = readStructure16();
/* 494 */           start = readContentCharactersBuffer(length);
/* 495 */           this._contentHandler.characters(this._contentCharactersBuffer, start, length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 9:
/* 500 */           ch = readContentCharactersCopy();
/*     */           
/* 502 */           this._contentHandler.characters(ch, 0, ch.length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 10:
/* 507 */           s = readContentString();
/* 508 */           this._contentHandler.characters(s.toCharArray(), 0, s.length());
/*     */           break;
/*     */ 
/*     */         
/*     */         case 11:
/* 513 */           c = (CharSequence)readContentObject();
/* 514 */           str1 = c.toString();
/* 515 */           this._contentHandler.characters(str1.toCharArray(), 0, str1.length());
/*     */           break;
/*     */         
/*     */         case 12:
/* 519 */           processCommentAsCharArraySmall();
/*     */           break;
/*     */         case 13:
/* 522 */           processCommentAsCharArrayMedium();
/*     */           break;
/*     */         case 14:
/* 525 */           processCommentAsCharArrayCopy();
/*     */           break;
/*     */         case 104:
/* 528 */           processComment(readContentString());
/*     */           break;
/*     */         case 16:
/* 531 */           processProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/*     */           break;
/*     */         default:
/* 536 */           throw reportFatalError("Illegal state for child of EII: " + item);
/*     */       } 
/* 538 */     } while (item != 17);
/*     */     
/* 540 */     this._contentHandler.endElement(uri, localName, qName);
/*     */     
/* 542 */     if (hasNamespaceAttributes) {
/* 543 */       processEndPrefixMapping();
/*     */     }
/*     */   }
/*     */   
/*     */   private void readInscopeNamespaces(Set<String> prefixSet) throws SAXException {
/* 548 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/* 549 */       String key = fixNull(e.getKey());
/*     */       
/* 551 */       if (!prefixSet.contains(key)) {
/* 552 */         processNamespaceAttribute(key, e.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 559 */     if (s == null) return ""; 
/* 560 */     return s;
/*     */   }
/*     */   private void processCommentAsCharArrayCopy() throws SAXException {
/* 563 */     char[] ch = readContentCharactersCopy();
/* 564 */     processComment(ch, 0, ch.length);
/*     */   }
/*     */   
/*     */   private void processCommentAsCharArrayMedium() throws SAXException {
/* 568 */     int length = readStructure16();
/* 569 */     int start = readContentCharactersBuffer(length);
/* 570 */     processComment(this._contentCharactersBuffer, start, length);
/*     */   }
/*     */   
/*     */   private void processEndPrefixMapping() throws SAXException {
/* 574 */     int end = this._namespaceAttributesStack[--this._namespaceAttributesStackIndex];
/*     */     
/* 576 */     int start = (this._namespaceAttributesStackIndex >= 0) ? this._namespaceAttributesStartingStack[this._namespaceAttributesStackIndex] : 0;
/*     */     
/* 578 */     for (int i = end - 1; i >= start; i--) {
/* 579 */       this._contentHandler.endPrefixMapping(this._namespacePrefixes[i]);
/*     */     }
/* 581 */     this._namespacePrefixesIndex = start;
/*     */   }
/*     */   
/*     */   private int processNamespaceAttributes(int item, boolean collectPrefixes, Set<String> prefixSet) throws SAXException {
/*     */     do {
/*     */       String prefix;
/* 587 */       switch (getNIIState(item)) {
/*     */         
/*     */         case 1:
/* 590 */           processNamespaceAttribute("", "");
/* 591 */           if (collectPrefixes) {
/* 592 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */         
/*     */         case 2:
/* 597 */           prefix = readStructureString();
/* 598 */           processNamespaceAttribute(prefix, "");
/* 599 */           if (collectPrefixes) {
/* 600 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 605 */           prefix = readStructureString();
/* 606 */           processNamespaceAttribute(prefix, readStructureString());
/* 607 */           if (collectPrefixes) {
/* 608 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 4:
/* 613 */           processNamespaceAttribute("", readStructureString());
/* 614 */           if (collectPrefixes) {
/* 615 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */         default:
/* 619 */           throw reportFatalError("Illegal state: " + item);
/*     */       } 
/* 621 */       readStructure();
/*     */       
/* 623 */       item = peekStructure();
/* 624 */     } while ((item & 0xF0) == 64);
/*     */ 
/*     */     
/* 627 */     cacheNamespacePrefixIndex();
/*     */     
/* 629 */     return item;
/*     */   }
/*     */   private void processAttributes(int item) throws SAXException {
/*     */     do {
/*     */       String p, u, ln, str2, str1, str3;
/* 634 */       switch (getAIIState(item)) {
/*     */         case 1:
/* 636 */           this._attributes.addAttributeWithQName(readStructureString(), readStructureString(), readStructureString(), readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 2:
/* 640 */           p = readStructureString();
/* 641 */           str2 = readStructureString();
/* 642 */           str3 = readStructureString();
/* 643 */           this._attributes.addAttributeWithQName(str2, str3, getQName(p, str3), readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 3:
/* 647 */           u = readStructureString();
/* 648 */           str1 = readStructureString();
/* 649 */           this._attributes.addAttributeWithQName(u, str1, str1, readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 4:
/* 653 */           ln = readStructureString();
/* 654 */           this._attributes.addAttributeWithQName("", ln, ln, readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         default:
/* 658 */           throw reportFatalError("Illegal state: " + item);
/*     */       } 
/* 660 */       readStructure();
/*     */       
/* 662 */       item = peekStructure();
/* 663 */     } while ((item & 0xF0) == 48);
/*     */   }
/*     */   
/*     */   private void processNamespaceAttribute(String prefix, String uri) throws SAXException {
/* 667 */     this._contentHandler.startPrefixMapping(prefix, uri);
/*     */     
/* 669 */     if (this._namespacePrefixesFeature)
/*     */     {
/* 671 */       if (prefix != "") {
/* 672 */         this._attributes.addAttributeWithQName("http://www.w3.org/2000/xmlns/", prefix, 
/* 673 */             getQName("xmlns", prefix), "CDATA", uri);
/*     */       } else {
/*     */         
/* 676 */         this._attributes.addAttributeWithQName("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns", "CDATA", uri);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 682 */     cacheNamespacePrefix(prefix);
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefix(String prefix) {
/* 686 */     if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/* 687 */       String[] namespaceAttributes = new String[this._namespacePrefixesIndex * 3 / 2 + 1];
/* 688 */       System.arraycopy(this._namespacePrefixes, 0, namespaceAttributes, 0, this._namespacePrefixesIndex);
/* 689 */       this._namespacePrefixes = namespaceAttributes;
/*     */     } 
/*     */     
/* 692 */     this._namespacePrefixes[this._namespacePrefixesIndex++] = prefix;
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefixIndex() {
/* 696 */     if (this._namespaceAttributesStackIndex == this._namespaceAttributesStack.length) {
/* 697 */       int[] namespaceAttributesStack = new int[this._namespaceAttributesStackIndex * 3 / 2 + 1];
/* 698 */       System.arraycopy(this._namespaceAttributesStack, 0, namespaceAttributesStack, 0, this._namespaceAttributesStackIndex);
/* 699 */       this._namespaceAttributesStack = namespaceAttributesStack;
/*     */     } 
/*     */     
/* 702 */     this._namespaceAttributesStack[this._namespaceAttributesStackIndex++] = this._namespacePrefixesIndex;
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefixStartingIndex() {
/* 706 */     if (this._namespaceAttributesStackIndex == this._namespaceAttributesStartingStack.length) {
/* 707 */       int[] namespaceAttributesStart = new int[this._namespaceAttributesStackIndex * 3 / 2 + 1];
/* 708 */       System.arraycopy(this._namespaceAttributesStartingStack, 0, namespaceAttributesStart, 0, this._namespaceAttributesStackIndex);
/* 709 */       this._namespaceAttributesStartingStack = namespaceAttributesStart;
/*     */     } 
/* 711 */     this._namespaceAttributesStartingStack[this._namespaceAttributesStackIndex] = this._namespacePrefixesIndex;
/*     */   }
/*     */   
/*     */   private void processComment(String s) throws SAXException {
/* 715 */     processComment(s.toCharArray(), 0, s.length());
/*     */   }
/*     */   
/*     */   private void processComment(char[] ch, int start, int length) throws SAXException {
/* 719 */     this._lexicalHandler.comment(ch, start, length);
/*     */   }
/*     */   
/*     */   private void processProcessingInstruction(String target, String data) throws SAXException {
/* 723 */     this._contentHandler.processingInstruction(target, data);
/*     */   }
/*     */   
/* 726 */   private static final DefaultWithLexicalHandler DEFAULT_LEXICAL_HANDLER = new DefaultWithLexicalHandler();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/sax/SAXBufferProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */