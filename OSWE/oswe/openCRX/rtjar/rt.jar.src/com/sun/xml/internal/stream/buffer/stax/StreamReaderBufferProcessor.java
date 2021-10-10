/*      */ package com.sun.xml.internal.stream.buffer.stax;
/*      */ 
/*      */ import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx;
/*      */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*      */ import com.sun.xml.internal.stream.buffer.AbstractCreatorProcessor;
/*      */ import com.sun.xml.internal.stream.buffer.AbstractProcessor;
/*      */ import com.sun.xml.internal.stream.buffer.AttributesHolder;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferMark;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StreamReaderBufferProcessor
/*      */   extends AbstractProcessor
/*      */   implements XMLStreamReaderEx
/*      */ {
/*      */   private static final int CACHE_SIZE = 16;
/*   63 */   protected ElementStackEntry[] _stack = new ElementStackEntry[16];
/*      */ 
/*      */ 
/*      */   
/*      */   protected ElementStackEntry _stackTop;
/*      */ 
/*      */   
/*      */   protected int _depth;
/*      */ 
/*      */   
/*   73 */   protected String[] _namespaceAIIsPrefix = new String[16];
/*   74 */   protected String[] _namespaceAIIsNamespaceName = new String[16];
/*      */   
/*      */   protected int _namespaceAIIsEnd;
/*      */   
/*   78 */   protected InternalNamespaceContext _nsCtx = new InternalNamespaceContext();
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _eventType;
/*      */ 
/*      */ 
/*      */   
/*      */   protected AttributesHolder _attributeCache;
/*      */ 
/*      */ 
/*      */   
/*      */   protected CharSequence _charSequence;
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _characters;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _textOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _textLen;
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _piTarget;
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _piData;
/*      */ 
/*      */   
/*      */   private static final int PARSING = 1;
/*      */ 
/*      */   
/*      */   private static final int PENDING_END_DOCUMENT = 2;
/*      */ 
/*      */   
/*      */   private static final int COMPLETED = 3;
/*      */ 
/*      */   
/*      */   private int _completionState;
/*      */ 
/*      */ 
/*      */   
/*      */   public StreamReaderBufferProcessor() {
/*  127 */     for (int i = 0; i < this._stack.length; i++) {
/*  128 */       this._stack[i] = new ElementStackEntry();
/*      */     }
/*      */     
/*  131 */     this._attributeCache = new AttributesHolder();
/*      */   }
/*      */   
/*      */   public StreamReaderBufferProcessor(XMLStreamBuffer buffer) throws XMLStreamException {
/*  135 */     this();
/*  136 */     setXMLStreamBuffer(buffer);
/*      */   }
/*      */   
/*      */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) throws XMLStreamException {
/*  140 */     setBuffer(buffer, buffer.isFragment());
/*      */     
/*  142 */     this._completionState = 1;
/*  143 */     this._namespaceAIIsEnd = 0;
/*  144 */     this._characters = null;
/*  145 */     this._charSequence = null;
/*  146 */     this._eventType = 7;
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
/*      */   public XMLStreamBuffer nextTagAndMark() throws XMLStreamException {
/*      */     while (true) {
/*  163 */       int s = peekStructure();
/*  164 */       if ((s & 0xF0) == 32) {
/*      */         
/*  166 */         Map<String, String> inscope = new HashMap<>(this._namespaceAIIsEnd);
/*      */         
/*  168 */         for (int i = 0; i < this._namespaceAIIsEnd; i++) {
/*  169 */           inscope.put(this._namespaceAIIsPrefix[i], this._namespaceAIIsNamespaceName[i]);
/*      */         }
/*  171 */         XMLStreamBufferMark mark = new XMLStreamBufferMark(inscope, (AbstractCreatorProcessor)this);
/*  172 */         next();
/*  173 */         return (XMLStreamBuffer)mark;
/*  174 */       }  if ((s & 0xF0) == 16) {
/*      */         
/*  176 */         readStructure();
/*      */         
/*  178 */         XMLStreamBufferMark mark = new XMLStreamBufferMark(new HashMap<>(this._namespaceAIIsEnd), (AbstractCreatorProcessor)this);
/*  179 */         next();
/*  180 */         return (XMLStreamBuffer)mark;
/*      */       } 
/*      */       
/*  183 */       if (next() == 2)
/*  184 */         return null; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object getProperty(String name) {
/*  189 */     return null;
/*      */   }
/*      */   public int next() throws XMLStreamException {
/*      */     int eiiState;
/*  193 */     switch (this._completionState) {
/*      */       case 3:
/*  195 */         throw new XMLStreamException("Invalid State");
/*      */       case 2:
/*  197 */         this._namespaceAIIsEnd = 0;
/*  198 */         this._completionState = 3;
/*  199 */         return this._eventType = 8;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  207 */     switch (this._eventType) {
/*      */       case 2:
/*  209 */         if (this._depth > 1) {
/*  210 */           this._depth--;
/*      */ 
/*      */           
/*  213 */           popElementStack(this._depth); break;
/*  214 */         }  if (this._depth == 1) {
/*  215 */           this._depth--;
/*      */         }
/*      */         break;
/*      */     } 
/*  219 */     this._characters = null;
/*  220 */     this._charSequence = null; while (true) {
/*      */       String uri, localName, prefix;
/*  222 */       eiiState = readEiiState();
/*  223 */       switch (eiiState) {
/*      */         case 1:
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 3:
/*  229 */           uri = readStructureString();
/*  230 */           localName = readStructureString();
/*  231 */           prefix = getPrefixFromQName(readStructureString());
/*      */           
/*  233 */           processElement(prefix, uri, localName, isInscope(this._depth));
/*  234 */           return this._eventType = 1;
/*      */         
/*      */         case 4:
/*  237 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope(this._depth));
/*  238 */           return this._eventType = 1;
/*      */         case 5:
/*  240 */           processElement((String)null, readStructureString(), readStructureString(), isInscope(this._depth));
/*  241 */           return this._eventType = 1;
/*      */         case 6:
/*  243 */           processElement((String)null, (String)null, readStructureString(), isInscope(this._depth));
/*  244 */           return this._eventType = 1;
/*      */         case 7:
/*  246 */           this._textLen = readStructure();
/*  247 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  248 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  250 */           return this._eventType = 4;
/*      */         case 8:
/*  252 */           this._textLen = readStructure16();
/*  253 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  254 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  256 */           return this._eventType = 4;
/*      */         case 9:
/*  258 */           this._characters = readContentCharactersCopy();
/*  259 */           this._textLen = this._characters.length;
/*  260 */           this._textOffset = 0;
/*      */           
/*  262 */           return this._eventType = 4;
/*      */         case 10:
/*  264 */           this._eventType = 4;
/*  265 */           this._charSequence = readContentString();
/*      */           
/*  267 */           return this._eventType = 4;
/*      */         case 11:
/*  269 */           this._eventType = 4;
/*  270 */           this._charSequence = (CharSequence)readContentObject();
/*      */           
/*  272 */           return this._eventType = 4;
/*      */         case 12:
/*  274 */           this._textLen = readStructure();
/*  275 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  276 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  278 */           return this._eventType = 5;
/*      */         case 13:
/*  280 */           this._textLen = readStructure16();
/*  281 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  282 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  284 */           return this._eventType = 5;
/*      */         case 14:
/*  286 */           this._characters = readContentCharactersCopy();
/*  287 */           this._textLen = this._characters.length;
/*  288 */           this._textOffset = 0;
/*      */           
/*  290 */           return this._eventType = 5;
/*      */         case 15:
/*  292 */           this._charSequence = readContentString();
/*      */           
/*  294 */           return this._eventType = 5;
/*      */         case 16:
/*  296 */           this._piTarget = readStructureString();
/*  297 */           this._piData = readStructureString();
/*      */           
/*  299 */           return this._eventType = 3;
/*      */         case 17:
/*  301 */           if (this._depth > 1)
/*      */           {
/*  303 */             return this._eventType = 2; } 
/*  304 */           if (this._depth == 1) {
/*      */             
/*  306 */             if (this._fragmentMode && 
/*  307 */               --this._treeCount == 0) {
/*  308 */               this._completionState = 2;
/*      */             }
/*  310 */             return this._eventType = 2;
/*      */           } 
/*      */ 
/*      */           
/*  314 */           this._namespaceAIIsEnd = 0;
/*  315 */           this._completionState = 3;
/*  316 */           return this._eventType = 8;
/*      */       }  break;
/*      */     } 
/*  319 */     throw new XMLStreamException("Internal XSB error: Invalid State=" + eiiState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/*  326 */     if (type != this._eventType) {
/*  327 */       throw new XMLStreamException("");
/*      */     }
/*  329 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI())) {
/*  330 */       throw new XMLStreamException("");
/*      */     }
/*  332 */     if (localName != null && !localName.equals(getLocalName())) {
/*  333 */       throw new XMLStreamException("");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getElementTextTrim() throws XMLStreamException {
/*  339 */     return getElementText().trim();
/*      */   }
/*      */   
/*      */   public final String getElementText() throws XMLStreamException {
/*  343 */     if (this._eventType != 1) {
/*  344 */       throw new XMLStreamException("");
/*      */     }
/*      */     
/*  347 */     next();
/*  348 */     return getElementText(true);
/*      */   }
/*      */   
/*      */   public final String getElementText(boolean startElementRead) throws XMLStreamException {
/*  352 */     if (!startElementRead) {
/*  353 */       throw new XMLStreamException("");
/*      */     }
/*      */     
/*  356 */     int eventType = getEventType();
/*  357 */     StringBuilder content = new StringBuilder();
/*  358 */     while (eventType != 2) {
/*  359 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*      */ 
/*      */ 
/*      */         
/*  363 */         content.append(getText());
/*  364 */       } else if (eventType != 3 && eventType != 5) {
/*      */ 
/*      */         
/*  367 */         if (eventType == 8)
/*  368 */           throw new XMLStreamException(""); 
/*  369 */         if (eventType == 1) {
/*  370 */           throw new XMLStreamException("");
/*      */         }
/*  372 */         throw new XMLStreamException("");
/*      */       } 
/*  374 */       eventType = next();
/*      */     } 
/*  376 */     return content.toString();
/*      */   }
/*      */   
/*      */   public final int nextTag() throws XMLStreamException {
/*  380 */     next();
/*  381 */     return nextTag(true);
/*      */   }
/*      */   
/*      */   public final int nextTag(boolean currentTagRead) throws XMLStreamException {
/*  385 */     int eventType = getEventType();
/*  386 */     if (!currentTagRead) {
/*  387 */       eventType = next();
/*      */     }
/*  389 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && 
/*  390 */       isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5)
/*      */     {
/*      */ 
/*      */       
/*  394 */       eventType = next();
/*      */     }
/*  396 */     if (eventType != 1 && eventType != 2) {
/*  397 */       throw new XMLStreamException("");
/*      */     }
/*  399 */     return eventType;
/*      */   }
/*      */   
/*      */   public final boolean hasNext() {
/*  403 */     return (this._eventType != 8);
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws XMLStreamException {}
/*      */   
/*      */   public final boolean isStartElement() {
/*  410 */     return (this._eventType == 1);
/*      */   }
/*      */   
/*      */   public final boolean isEndElement() {
/*  414 */     return (this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final boolean isCharacters() {
/*  418 */     return (this._eventType == 4);
/*      */   }
/*      */   
/*      */   public final boolean isWhiteSpace() {
/*  422 */     if (isCharacters() || this._eventType == 12) {
/*  423 */       char[] ch = getTextCharacters();
/*  424 */       int start = getTextStart();
/*  425 */       int length = getTextLength();
/*  426 */       for (int i = start; i < length; i++) {
/*  427 */         char c = ch[i];
/*  428 */         if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
/*  429 */           return false; 
/*      */       } 
/*  431 */       return true;
/*      */     } 
/*  433 */     return false;
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(String namespaceURI, String localName) {
/*  437 */     if (this._eventType != 1) {
/*  438 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  441 */     if (namespaceURI == null)
/*      */     {
/*      */       
/*  444 */       namespaceURI = "";
/*      */     }
/*      */     
/*  447 */     return this._attributeCache.getValue(namespaceURI, localName);
/*      */   }
/*      */   
/*      */   public final int getAttributeCount() {
/*  451 */     if (this._eventType != 1) {
/*  452 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  455 */     return this._attributeCache.getLength();
/*      */   }
/*      */   
/*      */   public final QName getAttributeName(int index) {
/*  459 */     if (this._eventType != 1) {
/*  460 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  463 */     String prefix = this._attributeCache.getPrefix(index);
/*  464 */     String localName = this._attributeCache.getLocalName(index);
/*  465 */     String uri = this._attributeCache.getURI(index);
/*  466 */     return new QName(uri, localName, prefix);
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getAttributeNamespace(int index) {
/*  471 */     if (this._eventType != 1) {
/*  472 */       throw new IllegalStateException("");
/*      */     }
/*  474 */     return fixEmptyString(this._attributeCache.getURI(index));
/*      */   }
/*      */   
/*      */   public final String getAttributeLocalName(int index) {
/*  478 */     if (this._eventType != 1) {
/*  479 */       throw new IllegalStateException("");
/*      */     }
/*  481 */     return this._attributeCache.getLocalName(index);
/*      */   }
/*      */   
/*      */   public final String getAttributePrefix(int index) {
/*  485 */     if (this._eventType != 1) {
/*  486 */       throw new IllegalStateException("");
/*      */     }
/*  488 */     return fixEmptyString(this._attributeCache.getPrefix(index));
/*      */   }
/*      */   
/*      */   public final String getAttributeType(int index) {
/*  492 */     if (this._eventType != 1) {
/*  493 */       throw new IllegalStateException("");
/*      */     }
/*  495 */     return this._attributeCache.getType(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(int index) {
/*  499 */     if (this._eventType != 1) {
/*  500 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  503 */     return this._attributeCache.getValue(index);
/*      */   }
/*      */   
/*      */   public final boolean isAttributeSpecified(int index) {
/*  507 */     return false;
/*      */   }
/*      */   
/*      */   public final int getNamespaceCount() {
/*  511 */     if (this._eventType == 1 || this._eventType == 2) {
/*  512 */       return this._stackTop.namespaceAIIsEnd - this._stackTop.namespaceAIIsStart;
/*      */     }
/*      */     
/*  515 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespacePrefix(int index) {
/*  519 */     if (this._eventType == 1 || this._eventType == 2) {
/*  520 */       return this._namespaceAIIsPrefix[this._stackTop.namespaceAIIsStart + index];
/*      */     }
/*      */     
/*  523 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI(int index) {
/*  527 */     if (this._eventType == 1 || this._eventType == 2) {
/*  528 */       return this._namespaceAIIsNamespaceName[this._stackTop.namespaceAIIsStart + index];
/*      */     }
/*      */     
/*  531 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI(String prefix) {
/*  535 */     return this._nsCtx.getNamespaceURI(prefix);
/*      */   }
/*      */   
/*      */   public final NamespaceContextEx getNamespaceContext() {
/*  539 */     return this._nsCtx;
/*      */   }
/*      */   
/*      */   public final int getEventType() {
/*  543 */     return this._eventType;
/*      */   }
/*      */   
/*      */   public final String getText() {
/*  547 */     if (this._characters != null) {
/*  548 */       String s = new String(this._characters, this._textOffset, this._textLen);
/*  549 */       this._charSequence = s;
/*  550 */       return s;
/*  551 */     }  if (this._charSequence != null) {
/*  552 */       return this._charSequence.toString();
/*      */     }
/*  554 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final char[] getTextCharacters() {
/*  559 */     if (this._characters != null)
/*  560 */       return this._characters; 
/*  561 */     if (this._charSequence != null) {
/*      */ 
/*      */       
/*  564 */       this._characters = this._charSequence.toString().toCharArray();
/*  565 */       this._textLen = this._characters.length;
/*  566 */       this._textOffset = 0;
/*  567 */       return this._characters;
/*      */     } 
/*  569 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextStart() {
/*  574 */     if (this._characters != null)
/*  575 */       return this._textOffset; 
/*  576 */     if (this._charSequence != null) {
/*  577 */       return 0;
/*      */     }
/*  579 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextLength() {
/*  584 */     if (this._characters != null)
/*  585 */       return this._textLen; 
/*  586 */     if (this._charSequence != null) {
/*  587 */       return this._charSequence.length();
/*      */     }
/*  589 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/*  595 */     if (this._characters == null) {
/*  596 */       if (this._charSequence != null) {
/*  597 */         this._characters = this._charSequence.toString().toCharArray();
/*  598 */         this._textLen = this._characters.length;
/*  599 */         this._textOffset = 0;
/*      */       } else {
/*  601 */         throw new IllegalStateException("");
/*      */       } 
/*      */     }
/*      */     try {
/*  605 */       int remaining = this._textLen - sourceStart;
/*  606 */       int len = (remaining > length) ? length : remaining;
/*  607 */       sourceStart += this._textOffset;
/*  608 */       System.arraycopy(this._characters, sourceStart, target, targetStart, len);
/*  609 */       return len;
/*  610 */     } catch (IndexOutOfBoundsException e) {
/*  611 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private class CharSequenceImpl implements CharSequence {
/*      */     private final int _offset;
/*      */     private final int _length;
/*      */     
/*      */     CharSequenceImpl(int offset, int length) {
/*  620 */       this._offset = offset;
/*  621 */       this._length = length;
/*      */     }
/*      */     
/*      */     public int length() {
/*  625 */       return this._length;
/*      */     }
/*      */     
/*      */     public char charAt(int index) {
/*  629 */       if (index >= 0 && index < StreamReaderBufferProcessor.this._textLen) {
/*  630 */         return StreamReaderBufferProcessor.this._characters[StreamReaderBufferProcessor.this._textOffset + index];
/*      */       }
/*  632 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSequence subSequence(int start, int end) {
/*  637 */       int length = end - start;
/*  638 */       if (end < 0 || start < 0 || end > length || start > end) {
/*  639 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */       
/*  642 */       return new CharSequenceImpl(this._offset + start, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  647 */       return new String(StreamReaderBufferProcessor.this._characters, this._offset, this._length);
/*      */     }
/*      */   }
/*      */   
/*      */   public final CharSequence getPCDATA() {
/*  652 */     if (this._characters != null)
/*  653 */       return new CharSequenceImpl(this._textOffset, this._textLen); 
/*  654 */     if (this._charSequence != null) {
/*  655 */       return this._charSequence;
/*      */     }
/*  657 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getEncoding() {
/*  662 */     return "UTF-8";
/*      */   }
/*      */   
/*      */   public final boolean hasText() {
/*  666 */     return (this._characters != null || this._charSequence != null);
/*      */   }
/*      */   
/*      */   public final Location getLocation() {
/*  670 */     return new DummyLocation();
/*      */   }
/*      */   
/*      */   public final boolean hasName() {
/*  674 */     return (this._eventType == 1 || this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final QName getName() {
/*  678 */     return this._stackTop.getQName();
/*      */   }
/*      */   
/*      */   public final String getLocalName() {
/*  682 */     return this._stackTop.localName;
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI() {
/*  686 */     return this._stackTop.uri;
/*      */   }
/*      */   
/*      */   public final String getPrefix() {
/*  690 */     return this._stackTop.prefix;
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getVersion() {
/*  695 */     return "1.0";
/*      */   }
/*      */   
/*      */   public final boolean isStandalone() {
/*  699 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean standaloneSet() {
/*  703 */     return false;
/*      */   }
/*      */   
/*      */   public final String getCharacterEncodingScheme() {
/*  707 */     return "UTF-8";
/*      */   }
/*      */   
/*      */   public final String getPITarget() {
/*  711 */     if (this._eventType == 3) {
/*  712 */       return this._piTarget;
/*      */     }
/*  714 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getPIData() {
/*  718 */     if (this._eventType == 3) {
/*  719 */       return this._piData;
/*      */     }
/*  721 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   protected void processElement(String prefix, String uri, String localName, boolean inscope) {
/*  725 */     pushElementStack();
/*  726 */     this._stackTop.set(prefix, uri, localName);
/*      */     
/*  728 */     this._attributeCache.clear();
/*      */     
/*  730 */     int item = peekStructure();
/*  731 */     if ((item & 0xF0) == 64 || inscope)
/*      */     {
/*      */       
/*  734 */       item = processNamespaceAttributes(item, inscope);
/*      */     }
/*  736 */     if ((item & 0xF0) == 48) {
/*  737 */       processAttributes(item);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isInscope(int depth) {
/*  742 */     return (this._buffer.getInscopeNamespaces().size() > 0 && depth == 0);
/*      */   }
/*      */   
/*      */   private void resizeNamespaceAttributes() {
/*  746 */     String[] namespaceAIIsPrefix = new String[this._namespaceAIIsEnd * 2];
/*  747 */     System.arraycopy(this._namespaceAIIsPrefix, 0, namespaceAIIsPrefix, 0, this._namespaceAIIsEnd);
/*  748 */     this._namespaceAIIsPrefix = namespaceAIIsPrefix;
/*      */     
/*  750 */     String[] namespaceAIIsNamespaceName = new String[this._namespaceAIIsEnd * 2];
/*  751 */     System.arraycopy(this._namespaceAIIsNamespaceName, 0, namespaceAIIsNamespaceName, 0, this._namespaceAIIsEnd);
/*  752 */     this._namespaceAIIsNamespaceName = namespaceAIIsNamespaceName;
/*      */   }
/*      */   
/*      */   private int processNamespaceAttributes(int item, boolean inscope) {
/*  756 */     this._stackTop.namespaceAIIsStart = this._namespaceAIIsEnd;
/*  757 */     Set<String> prefixSet = inscope ? new HashSet<>() : Collections.<String>emptySet();
/*      */     
/*  759 */     while ((item & 0xF0) == 64) {
/*  760 */       if (this._namespaceAIIsEnd == this._namespaceAIIsPrefix.length) {
/*  761 */         resizeNamespaceAttributes();
/*      */       }
/*      */       
/*  764 */       switch (getNIIState(item)) {
/*      */         
/*      */         case 1:
/*  767 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = ""; this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = "";
/*      */           
/*  769 */           if (inscope) {
/*  770 */             prefixSet.add("");
/*      */           }
/*      */           break;
/*      */         
/*      */         case 2:
/*  775 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = readStructureString();
/*  776 */           if (inscope) {
/*  777 */             prefixSet.add(this._namespaceAIIsPrefix[this._namespaceAIIsEnd]);
/*      */           }
/*  779 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = "";
/*      */           break;
/*      */         
/*      */         case 3:
/*  783 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = readStructureString();
/*  784 */           if (inscope) {
/*  785 */             prefixSet.add(this._namespaceAIIsPrefix[this._namespaceAIIsEnd]);
/*      */           }
/*  787 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = readStructureString();
/*      */           break;
/*      */         
/*      */         case 4:
/*  791 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = "";
/*  792 */           if (inscope) {
/*  793 */             prefixSet.add("");
/*      */           }
/*  795 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = readStructureString();
/*      */           break;
/*      */       } 
/*  798 */       readStructure();
/*      */       
/*  800 */       item = peekStructure();
/*      */     } 
/*      */     
/*  803 */     if (inscope) {
/*  804 */       for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/*  805 */         String key = fixNull(e.getKey());
/*      */         
/*  807 */         if (!prefixSet.contains(key)) {
/*  808 */           if (this._namespaceAIIsEnd == this._namespaceAIIsPrefix.length) {
/*  809 */             resizeNamespaceAttributes();
/*      */           }
/*  811 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = key;
/*  812 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = e.getValue();
/*      */         } 
/*      */       } 
/*      */     }
/*  816 */     this._stackTop.namespaceAIIsEnd = this._namespaceAIIsEnd;
/*      */     
/*  818 */     return item;
/*      */   }
/*      */   
/*      */   private static String fixNull(String s) {
/*  822 */     if (s == null) return ""; 
/*  823 */     return s;
/*      */   }
/*      */   private void processAttributes(int item) {
/*      */     do {
/*      */       String uri, localName, prefix;
/*  828 */       switch (getAIIState(item)) {
/*      */         case 1:
/*  830 */           uri = readStructureString();
/*  831 */           localName = readStructureString();
/*  832 */           prefix = getPrefixFromQName(readStructureString());
/*  833 */           this._attributeCache.addAttributeWithPrefix(prefix, uri, localName, readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         case 2:
/*  837 */           this._attributeCache.addAttributeWithPrefix(readStructureString(), readStructureString(), readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         case 3:
/*  841 */           this._attributeCache.addAttributeWithPrefix("", readStructureString(), readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         case 4:
/*  844 */           this._attributeCache.addAttributeWithPrefix("", "", readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         default:
/*  848 */           assert false : "Internal XSB Error: wrong attribute state, Item=" + item; break;
/*      */       } 
/*  850 */       readStructure();
/*      */       
/*  852 */       item = peekStructure();
/*  853 */     } while ((item & 0xF0) == 48);
/*      */   }
/*      */   
/*      */   private void pushElementStack() {
/*  857 */     if (this._depth == this._stack.length) {
/*      */       
/*  859 */       ElementStackEntry[] tmp = this._stack;
/*  860 */       this._stack = new ElementStackEntry[this._stack.length * 3 / 2 + 1];
/*  861 */       System.arraycopy(tmp, 0, this._stack, 0, tmp.length);
/*  862 */       for (int i = tmp.length; i < this._stack.length; i++) {
/*  863 */         this._stack[i] = new ElementStackEntry();
/*      */       }
/*      */     } 
/*      */     
/*  867 */     this._stackTop = this._stack[this._depth++];
/*      */   }
/*      */ 
/*      */   
/*      */   private void popElementStack(int depth) {
/*  872 */     this._stackTop = this._stack[depth - 1];
/*      */     
/*  874 */     this._namespaceAIIsEnd = (this._stack[depth]).namespaceAIIsStart;
/*      */   }
/*      */ 
/*      */   
/*      */   private final class ElementStackEntry
/*      */   {
/*      */     String prefix;
/*      */     
/*      */     String uri;
/*      */     
/*      */     String localName;
/*      */     
/*      */     QName qname;
/*      */     
/*      */     int namespaceAIIsStart;
/*      */     
/*      */     int namespaceAIIsEnd;
/*      */ 
/*      */     
/*      */     private ElementStackEntry() {}
/*      */ 
/*      */     
/*      */     public void set(String prefix, String uri, String localName) {
/*  897 */       this.prefix = prefix;
/*  898 */       this.uri = uri;
/*  899 */       this.localName = localName;
/*  900 */       this.qname = null;
/*      */       
/*  902 */       this.namespaceAIIsStart = this.namespaceAIIsEnd = StreamReaderBufferProcessor.this._namespaceAIIsEnd;
/*      */     }
/*      */     
/*      */     public QName getQName() {
/*  906 */       if (this.qname == null) {
/*  907 */         this.qname = new QName(fixNull(this.uri), this.localName, fixNull(this.prefix));
/*      */       }
/*  909 */       return this.qname;
/*      */     }
/*      */     
/*      */     private String fixNull(String s) {
/*  913 */       return (s == null) ? "" : s;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class InternalNamespaceContext implements NamespaceContextEx { private InternalNamespaceContext() {}
/*      */     
/*      */     public String getNamespaceURI(String prefix) {
/*  920 */       if (prefix == null) {
/*  921 */         throw new IllegalArgumentException("Prefix cannot be null");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  929 */       if (StreamReaderBufferProcessor.this._stringInterningFeature) {
/*  930 */         prefix = prefix.intern();
/*      */ 
/*      */         
/*  933 */         for (int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1; i >= 0; i--) {
/*  934 */           if (prefix == StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i]) {
/*  935 */             return StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[i];
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  940 */         for (int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1; i >= 0; i--) {
/*  941 */           if (prefix.equals(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i])) {
/*  942 */             return StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[i];
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  948 */       if (prefix.equals("xml"))
/*  949 */         return "http://www.w3.org/XML/1998/namespace"; 
/*  950 */       if (prefix.equals("xmlns")) {
/*  951 */         return "http://www.w3.org/2000/xmlns/";
/*      */       }
/*      */       
/*  954 */       return null;
/*      */     }
/*      */     
/*      */     public String getPrefix(String namespaceURI) {
/*  958 */       Iterator<String> i = getPrefixes(namespaceURI);
/*  959 */       if (i.hasNext()) {
/*  960 */         return i.next();
/*      */       }
/*  962 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator getPrefixes(final String namespaceURI) {
/*  967 */       if (namespaceURI == null) {
/*  968 */         throw new IllegalArgumentException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/*  971 */       if (namespaceURI.equals("http://www.w3.org/XML/1998/namespace"))
/*  972 */         return Collections.<String>singletonList("xml").iterator(); 
/*  973 */       if (namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
/*  974 */         return Collections.<String>singletonList("xmlns").iterator();
/*      */       }
/*      */       
/*  977 */       return new Iterator() {
/*  978 */           private int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1;
/*      */           private boolean requireFindNext = true;
/*      */           private String p;
/*      */           
/*      */           private String findNext() {
/*  983 */             while (this.i >= 0) {
/*      */               
/*  985 */               if (namespaceURI.equals(StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.i]))
/*      */               {
/*      */                 
/*  988 */                 if (StreamReaderBufferProcessor.InternalNamespaceContext.this.getNamespaceURI(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.i]).equals(StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.i]))
/*      */                 {
/*  990 */                   return this.p = StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.i];
/*      */                 }
/*      */               }
/*  993 */               this.i--;
/*      */             } 
/*  995 */             return this.p = null;
/*      */           }
/*      */           
/*      */           public boolean hasNext() {
/*  999 */             if (this.requireFindNext) {
/* 1000 */               findNext();
/* 1001 */               this.requireFindNext = false;
/*      */             } 
/* 1003 */             return (this.p != null);
/*      */           }
/*      */           
/*      */           public Object next() {
/* 1007 */             if (this.requireFindNext) {
/* 1008 */               findNext();
/*      */             }
/* 1010 */             this.requireFindNext = true;
/*      */             
/* 1012 */             if (this.p == null) {
/* 1013 */               throw new NoSuchElementException();
/*      */             }
/*      */             
/* 1016 */             return this.p;
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1020 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private class BindingImpl implements NamespaceContextEx.Binding {
/*      */       final String _prefix;
/*      */       final String _namespaceURI;
/*      */       
/*      */       BindingImpl(String prefix, String namespaceURI) {
/* 1030 */         this._prefix = prefix;
/* 1031 */         this._namespaceURI = namespaceURI;
/*      */       }
/*      */       
/*      */       public String getPrefix() {
/* 1035 */         return this._prefix;
/*      */       }
/*      */       
/*      */       public String getNamespaceURI() {
/* 1039 */         return this._namespaceURI;
/*      */       }
/*      */     }
/*      */     
/*      */     public Iterator<NamespaceContextEx.Binding> iterator() {
/* 1044 */       return new Iterator<NamespaceContextEx.Binding>() {
/* 1045 */           private final int end = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1;
/* 1046 */           private int current = this.end;
/*      */           private boolean requireFindNext = true;
/*      */           private NamespaceContextEx.Binding namespace;
/*      */           
/*      */           private NamespaceContextEx.Binding findNext() {
/* 1051 */             while (this.current >= 0) {
/* 1052 */               String prefix = StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.current];
/*      */ 
/*      */ 
/*      */               
/* 1056 */               int i = this.end;
/* 1057 */               for (; i > this.current && 
/* 1058 */                 !prefix.equals(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i]); i--);
/*      */ 
/*      */ 
/*      */               
/* 1062 */               if (i == this.current--)
/*      */               {
/* 1064 */                 return this.namespace = new StreamReaderBufferProcessor.InternalNamespaceContext.BindingImpl(prefix, StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.current]);
/*      */               }
/*      */             } 
/* 1067 */             return this.namespace = null;
/*      */           }
/*      */           
/*      */           public boolean hasNext() {
/* 1071 */             if (this.requireFindNext) {
/* 1072 */               findNext();
/* 1073 */               this.requireFindNext = false;
/*      */             } 
/* 1075 */             return (this.namespace != null);
/*      */           }
/*      */           
/*      */           public NamespaceContextEx.Binding next() {
/* 1079 */             if (this.requireFindNext) {
/* 1080 */               findNext();
/*      */             }
/* 1082 */             this.requireFindNext = true;
/*      */             
/* 1084 */             if (this.namespace == null) {
/* 1085 */               throw new NoSuchElementException();
/*      */             }
/*      */             
/* 1088 */             return this.namespace;
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1092 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */     } }
/*      */   
/*      */   private class DummyLocation implements Location { private DummyLocation() {}
/*      */     
/*      */     public int getLineNumber() {
/* 1100 */       return -1;
/*      */     }
/*      */     
/*      */     public int getColumnNumber() {
/* 1104 */       return -1;
/*      */     }
/*      */     
/*      */     public int getCharacterOffset() {
/* 1108 */       return -1;
/*      */     }
/*      */     
/*      */     public String getPublicId() {
/* 1112 */       return null;
/*      */     }
/*      */     
/*      */     public String getSystemId() {
/* 1116 */       return StreamReaderBufferProcessor.this._buffer.getSystemId();
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixEmptyString(String s) {
/* 1122 */     if (s.length() == 0) return null; 
/* 1123 */     return s;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/stax/StreamReaderBufferProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */